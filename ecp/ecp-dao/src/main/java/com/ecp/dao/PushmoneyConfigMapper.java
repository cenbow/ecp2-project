package com.ecp.dao;

import java.util.List;
import java.util.Map;

import com.ecp.entity.PushmoneyConfig;

import tk.mybatis.mapper.common.Mapper;

public interface PushmoneyConfigMapper extends Mapper<PushmoneyConfig> {
	
	/**
	 * 根据Map中的条件查询
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getList(Map<String, Object> params);
	
}