package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.security.Auth;
import com.poscodx.jblog.security.AuthUser;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets|images).*}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(
		@PathVariable("id") String id,
		@PathVariable(name="categoryNo", required = false) Long categoryNo,
		@PathVariable(name="postNo", required = false) Long postNo,
		Model model
		) {
		BlogVo vo = blogService.getBlog(id);
		String title = blogService.getBlog(id).getTitle();
		List<PostVo> postList = null;
		List<CategoryVo> categoryList = blogService.getCategoriesById(id);
		PostVo post = null;
		
		if(categoryNo != null && postNo == null) {
			postList = blogService.getPostsByCategoryAndId(categoryNo, id);
		} else if(categoryNo != null && postNo != null){
			postList = blogService.getPostsByBlogId(id);
			post =blogService.getPostByNo(postNo);
		} else {
			postList = blogService.getPostsByBlogId(id);
		}
		
		model.addAttribute("list", postList);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("id", id);
		model.addAttribute("vo", vo);
		model.addAttribute("title", title);
		model.addAttribute("post", post);
		
		return "blog/main";
	}
	
	@Auth
	@RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	public String adminBasic(@AuthUser UserVo authUser, @PathVariable("id") String id, Model model) {
		if(authUser == null || !authUser.getId().equals(id)) {
			return "redirect:/";
		}
		String title = blogService.getBlog(id).getTitle();
		System.out.print(authUser.getId());
		BlogVo vo = blogService.getBlog(id);
		model.addAttribute("id", id);
		model.addAttribute("vo", vo);
		model.addAttribute("title", title);
		return "blog/admin-basic";
	}
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.POST)
	public String adminBasicUpdate(
		@PathVariable("id") String id,
		@RequestParam(value="title") String title,
		@RequestParam(value="file") MultipartFile file,
		Model model) {
		String url = fileUploadService.restore(file);
		blogService.update(id, title, url);
		return "redirect:/" + id + "/admin/basic";
	}

	@Auth
	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String adminCategory(@PathVariable("id") String id, Model model) {
		String title = blogService.getBlog(id).getTitle();
		List<CategoryVo> categoryList = blogService.getCategoriesById(id);
		Map<Long, Integer> countsMap = blogService.getCountPost(categoryList, id);
		model.addAttribute("id", id);
		model.addAttribute("list", categoryList);
		model.addAttribute("countsMap", countsMap);
		model.addAttribute("title", title);
		return "blog/admin-category";
	}
	
	@RequestMapping(value="/admin/category", method=RequestMethod.POST)
	public String adminCategory(CategoryVo vo, @PathVariable("id") String id) {
		blogService.createCategory(vo, id);
		return "redirect:/" + id +"/admin/category";
	}
	
	@RequestMapping(value="/admin/category/delete/{categoryNo}")
	public String adminCategoryDelete(@PathVariable("id") String id, @PathVariable("categoryNo") Long categoryNo) {
		blogService.deleteCategory(categoryNo);
		return "redirect:/" + id +"/admin/category";
	}
	
	@Auth
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String adminWrite(@PathVariable("id") String id, Model model) {
		String title = blogService.getBlog(id).getTitle();
		List<CategoryVo> categoryList = blogService.getCategoriesById(id);
		model.addAttribute("id", id);
		model.addAttribute("list", categoryList);
		model.addAttribute("title", title);
		return "blog/admin-write";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String adminWrite(@PathVariable("id") String id, PostVo vo) {
		blogService.createPost(vo);
		return "redirect:/" + id ;
	}
}