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
	
	/**
	 * 查询所有IS/OS，根据ID去重
	 * @return
	 */
	public List<Map<String, Object>> getISAndOSUser();
	
	/**
	 * 根据userId查询用户
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getById(Long userId);
	
}