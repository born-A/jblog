package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public void join(UserVo vo) {
		userRepository.insert(vo);
		blogRepository.insert(vo.getId());
		categoryRepository.insert(vo.getId());
	}

	public UserVo getUser(String id, String password) {
		return userRepository.findByIdAndPassword(id,password);
	}

	public UserVo getUser(String id) {
		return userRepository.findById(id);
	}
}