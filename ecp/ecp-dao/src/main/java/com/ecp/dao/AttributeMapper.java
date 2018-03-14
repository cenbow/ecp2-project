package com.ecp.dao;

import java.util.List;
import java.util.Map;

import com.ecp.entity.Attribute;

import tk.mybatis.mapper.common.Mapper;

public interface AttributeMapper extends Mapper<Attribute> {
	
	/**
	 * 根据map中的条件查询属性
	 * @param cid
	 * @return
	 * 		返回map
	 */
	List<Map<String, Object>> selectByCondition(Map<String, Object> map);

	
}