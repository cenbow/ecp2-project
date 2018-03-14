package com.ecp.dao;

import java.util.List;

import com.ecp.entity.RolePermission;

import tk.mybatis.mapper.common.Mapper;

public interface RolePermissionMapper extends Mapper<RolePermission> {
	
	/**
	 * 根据角色ID集合查询角色权限关系表（此处权限为菜单权限，权限的ID为菜单表的ID）
	 * @param roleIds
	 * @return
	 */
	public List<RolePermission> getByRoleIds(List<Long> roleIds);
	
}