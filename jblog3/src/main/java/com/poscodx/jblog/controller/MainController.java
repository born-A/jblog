package com.poscodx.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.vo.BlogVo;

@Controller
public class MainController {
	@Autowired
	private BlogService blogService;
	
	@RequestMapping("/")
	public String index(Model model) {
		List<BlogVo> blogList = blogService.getBlogs();
		model.addAttribute("list", blogList);
		return "main/index";
	}
}
