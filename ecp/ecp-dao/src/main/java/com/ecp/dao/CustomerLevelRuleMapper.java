package com.ecp.dao;

import java.util.List;
import java.util.Map;

import com.ecp.entity.CustomerLevelRule;
import tk.mybatis.mapper.common.Mapper;

public interface CustomerLevelRuleMapper extends Mapper<CustomerLevelRule> {
	
	/**
	 * 根据Map中的条件查询
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getListMap(Map<String, Object> params);
	
}