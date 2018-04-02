package com.ecp.dao;

import java.util.List;
import java.util.Map;

import com.ecp.entity.SalesTarget;

import tk.mybatis.mapper.common.Mapper;

public interface SalesTargetMapper extends Mapper<SalesTarget> {
	
	/**
	 * 根据Map中的条件查询销售指标集合
	 * 
	 * @param params
	 * @return
	 * 		返回List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getListMap(Map<String, Object> params);
	
	/**
	 * 查询销售指标
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectSalesTargetMap(Map<String, Object> params);
	
}