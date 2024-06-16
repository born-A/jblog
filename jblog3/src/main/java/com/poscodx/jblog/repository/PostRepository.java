package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	private SqlSession sqlSession;

	public int insert(PostVo vo) {
		return sqlSession.insert("post.insert", vo);
	}

	public List<PostVo> findAll() {
		return sqlSession.selectList("post.findAll");
	}

	public PostVo findByNo(Long postNo) {
		return sqlSession.selectOne("post.findByNo", postNo);
	}
	
	public List<PostVo> findByCategoryNo(Long categoryNo) {
		return sqlSession.selectList("post.findByCategoryNo", categoryNo);
	}
	
	
}
