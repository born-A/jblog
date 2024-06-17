package com.poscodx.jblog.repository;

import java.util.List;
import java.util.Map;

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
	
	public List<PostVo> findByCategoryNoAndId(Long categoryNo, String id) {
		return sqlSession.selectList("post.findByCategoryNoAndId", Map.of("id", id, "categoryNo", categoryNo));
	}

	public List<PostVo> findByBlogId(String id) {
		return sqlSession.selectList("post.findByBlogId", id);
	}
	
	
}
