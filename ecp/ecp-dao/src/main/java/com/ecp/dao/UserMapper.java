package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.User;

import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
	
	/**
	 * 查询IS和OS
	 * @return
	 */
	public List<Map<String, Object>> getISAndOS();
	
	public List<Map<String, Object>> getUsersByParentId(@Param("pid") long pid);
	
}