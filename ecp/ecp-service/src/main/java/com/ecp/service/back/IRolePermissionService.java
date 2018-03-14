package com.ecp.service.back;

import java.util.List;

import com.ecp.entity.RolePermission;
import com.ecp.service.IBaseService;

public interface IRolePermissionService extends IBaseService<RolePermission, Long> {
	
	/**
	 * 根据角色ID查询角色权限关系表（此处权限为菜单权限，权限的ID为菜单表的ID）
	 * @param roleId
	 * @return
	 */
	public List<RolePermission> getByRoleId(Long roleId);
	
	/**
	 * 根据角色ID集合查询角色权限关系表（此处权限为菜单权限，权限的ID为菜单表的ID）
	 * @param roleIds
	 * @return
	 */
	public List<RolePermission> getByRoleIds(List<Long> roleIds);
	
	/**
	 * 根据角色ID删除角色权限关系表（此处权限为菜单权限，权限的ID为菜单表的ID）
	 * @param roleId
	 * @return
	 */
	public int deleteByRoleId(Long roleId);
	
	/**
	 * 根据菜单权限删除 角色权限关系表
	 * @param menuId
	 * @return
	 */
	public int deleteByPermissionId(Long menuId);
	
}
