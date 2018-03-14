package com.ecp.service.back;

import com.ecp.entity.Role;
import com.ecp.service.IBaseService;

public interface IRoleService extends IBaseService<Role, Long> {
	
	/**
	 * 添加角色和角色权限（菜单权限）
	 * @param role
	 * @param permissionIds（此处的权限是菜单权限，ID其实是菜单表的id）
	 * @return
	 */
	public int add(Role role, String permissionIds);
	
	/**
	 * 修改角色和角色权限（菜单权限）
	 * @param role
	 * @param permissionIds（此处的权限是菜单权限，ID其实是菜单表的id）
	 * @return
	 */
	public int modify(Role role, String permissionIds);
	
	/**
	 * 删除角色，同时删除角色权限表
	 * @param roleId
	 * @return
	 */
	public int del(Long roleId);
	
}
