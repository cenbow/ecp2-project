package com.ecp.dao;

import java.util.List;
import java.util.Map;

import com.ecp.entity.AttributeValue;

import tk.mybatis.mapper.common.Mapper;

public interface AttributeValueMapper extends Mapper<AttributeValue> {
	/**
	 * 根据map中的条件查询属性值
	 * @param map
	 * @return
	 * 		返回list
	 */
	List<Map<String, Object>> selectByCondition(Map<String, Object> map);

}