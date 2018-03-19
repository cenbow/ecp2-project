package com.ecp.dao;

import java.util.List;
import java.util.Map;

import com.ecp.entity.CustomerLevel;

import tk.mybatis.mapper.common.Mapper;

public interface CustomerLevelMapper extends Mapper<CustomerLevel> {
	
	/**
	 * 根据Map中的条件查询
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getListMap(Map<String, Object> params);
	
}