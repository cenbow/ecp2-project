package com.ecp.dao;

import java.util.List;

import com.ecp.entity.Recommend;
import tk.mybatis.mapper.common.Mapper;

public interface RecommendMapper extends Mapper<Recommend> {
	
	/**
	 * 根据id集合删除（逻辑删除）
	 * @param ids
	 * @return
	 */
	public int deleteByIds(List<Long> ids);
	
}