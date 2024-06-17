package com.poscodx.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(CategoryVo vo) {
		return sqlSession.insert("category.insert", vo);
	}

	public int insert(String id) {
		return sqlSession.insert("category.insert", Map.of("name", "default", "description", "init", "id", id));
	}

	public List<CategoryVo> findAll() {
		return sqlSession.selectList("category.findAll");
	}

	public int deleteByNo(Long categoryNo) {
		return sqlSession.delete("category.deleteByNo", categoryNo);
	}

	public List<CategoryVo> findListById(String id) {
		return sqlSession.selectList("category.findListById", id);
	}
}
