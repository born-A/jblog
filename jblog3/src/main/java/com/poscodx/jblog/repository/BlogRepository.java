package com.poscodx.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;

	public void insert(String id) {
		String title = id + "님의 블로그";
		String logo = id + "님의 로고";
		sqlSession.insert("blog.insert", Map.of("id", id, "testTitle", title, "testLogo", logo));
	}

	public BlogVo findById(String id) {
		return sqlSession.selectOne("blog.findById", id);
	}

	public List<BlogVo> findAll() {
		return sqlSession.selectList("blog.findAll");
	}

	public int update(BlogVo vo) {
		return sqlSession.update("blog.update", vo);
	}
}
