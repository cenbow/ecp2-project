package com.ecp.dao;

import java.util.List;
import java.util.Map;

import com.ecp.entity.AccountPersonal;

import tk.mybatis.mapper.common.Mapper;

public interface AccountPersonalMapper extends Mapper<AccountPersonal> {
	
	/**
	 * 根据条件查询业绩
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectPerformanceMap(Map<String, Object> params);
}