package com.poscodx.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import  com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PostRepository postRepository;

	public BlogVo getBlog(String id) {
		return blogRepository.findById(id);
	}

	public void createCategory(CategoryVo vo, String id) {
		vo.setId(id);
		categoryRepository.insert(vo);
	}

	public List<CategoryVo> getCategoriesById(String id) {
		return categoryRepository.findListById(id);
	}

	public void createPost(PostVo vo) {
		postRepository.insert(vo);
		
	}

	public List<BlogVo> getBlogs() {
		return blogRepository.findAll();
	}

	public List<PostVo> getPosts() {
		return postRepository.findAll();
	}

	public void update(String id, String title, String url) {
		BlogVo vo = blogRepository.findById(id);
		vo.setTitle(title);
		vo.setLogo(url);
		
		blogRepository.update(vo);
	}

	public Map<Long, Integer> getCountPost(List<CategoryVo> categoryList, String id) {
		Map<Long, Integer> map = new HashMap<>();
		for(CategoryVo vo : categoryList) {
			Integer count = postRepository.findByCategoryNoAndId(vo.getNo(), id).size();
			map.put(vo.getNo(), count);	
		}
		return map;
	}

	public void deleteCategory(Long categoryNo) {
		categoryRepository.deleteByNo(categoryNo);
	}

	public List<PostVo> getPostsByCategoryAndId(Long categoryNo, String id) {
		List<PostVo> postList = postRepository.findByCategoryNoAndId(categoryNo, id);
		for (PostVo vo : postList) {
		    vo.setContents(vo.getContents().replaceAll(">", "&gt;")
		                                  .replaceAll("<", "&lt;")
		                                  .replaceAll("\n", "<br/>"));
		}
		return postList;
	}

	public PostVo getPostByNo(Long postNo) {
		PostVo vo = postRepository.findByNo(postNo);
		vo.setContents(vo.getContents().replaceAll(">", "&gt;")
                .replaceAll("<", "&lt;")
                .replaceAll("\n", "<br/>"));
		
		return vo;
	}

	public List<PostVo> getPostsByBlogId(String id) {
		List<PostVo> postList = postRepository.findByBlogId(id);
		for (PostVo vo : postList) {
		    vo.setContents(vo.getContents().replaceAll(">", "&gt;")
		                                  .replaceAll("<", "&lt;")
		                                  .replaceAll("\n", "<br/>"));
		}
		return postList;
	}
	
}
