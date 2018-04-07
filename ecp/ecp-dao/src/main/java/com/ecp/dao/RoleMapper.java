package com.ecp.dao;

import java.util.List;

import com.ecp.entity.Role;
import tk.mybatis.mapper.common.Mapper;

public interface RoleMapper extends Mapper<Role> {
	
	/**
	 * 根据用户ID查询角色（只查询IS/OS角色）
	 * 
	 * @param userId
	 * @return
	 */
	public List<Role> getByUserId(Long userId);
	
}