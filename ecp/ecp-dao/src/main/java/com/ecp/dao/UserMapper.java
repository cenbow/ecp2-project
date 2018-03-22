package com.ecp.dao;

import java.util.List;
import java.util.Map;

import com.ecp.entity.User;

import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
	
	/**
	 * 查询IS和OS
	 * @return
	 */
	public List<Map<String, Object>> getISAndOS();
	
}