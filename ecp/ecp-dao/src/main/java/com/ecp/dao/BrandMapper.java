package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.Brand;

import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand> {
	
	/**
	 * @Description 根据关键字列表查询品牌
	 * @param keywords
	 * @return
	 */
	public List<Brand> getBrandByKeywords(@Param("keywords") List<String> keywords);
	
	/**
	 * 根据map中的条件查询品牌
	 * @param map
	 * @return
	 * 		返回品牌mapList
	 */
	public List<Map<String, Object>> getBrandByCategoryid(Map<String, Object> map);
}