package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.Category;

import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper extends Mapper<Category> {
	
	/**
	 * @Description 通过关键字查询类目
	 * @param keywords 关键字列表
	 * @return  类目列表
	 */
	public List<Category> getCategoryByKeywords(@Param("keywords") List<String> keywords);
	
	public List<Map<String, Object>> getAllCategory();
	
}