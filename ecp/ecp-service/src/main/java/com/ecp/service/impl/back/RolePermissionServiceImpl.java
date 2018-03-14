package com.ecp.service.impl.back;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecp.dao.RolePermissionMapper;
import com.ecp.entity.RolePermission;
import com.ecp.service.back.IRolePermissionService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("rolePermissionServiceBean")
public class RolePermissionServiceImpl extends AbstractBaseService<RolePermission, Long> implements IRolePermissionService {

	private RolePermissionMapper rolePermisstionMapper;

	/**
	 * @param rolePermisstionMapper the rolePermisstionMapper to set
	 * set方式注入
	 */
	public void setRolePermissionMapper(RolePermissionMapper rolePermisstionMapper) {
		this.rolePermisstionMapper = rolePermisstionMapper;
		this.setMapper(rolePermisstionMapper);
	}

	/**
	 * @see com.ecp.service.back.IRolePermissionService#getByRoleId(java.lang.Long)
	 * 根据角色ID查询角色权限关系表（此处权限为菜单权限，权限的ID为菜单表的ID）
	 */
	@Override
	public List<RolePermission> getByRoleId(Long roleId) {
		Example example = new Example(RolePermission.class);
		example.createCriteria().andEqualTo("roleId", roleId);
		return rolePermisstionMapper.selectByExample(example);
	}

	/**
	 * @see com.ecp.service.back.IRolePermissionService#deleteByRoleId(java.lang.Long)
	 * 根据角色ID删除角色权限关系表（此处权限为菜单权限，权限的ID为菜单表的ID）
	 */
	@Override
	public int deleteByRoleId(Long roleId) {
		Example example = new Example(RolePermission.class);
		example.createCriteria().andEqualTo("roleId", roleId);
		return rolePermisstionMapper.deleteByExample(example);
	}

	/**
	 * @see com.ecp.service.back.IRolePermissionService#getByRoleIds(java.util.List)
	 * 根据角色ID集合查询角色权限关系表（此处权限为菜单权限，权限的ID为菜单表的ID）
	 */
	@Override
	public List<RolePermission> getByRoleIds(List<Long> roleIds) {
		return rolePermisstionMapper.getByRoleIds(roleIds);
	}

	/**
	 * @see com.ecp.service.back.IRolePermissionService#deleteByPermissionId(java.lang.Long)
	 * 根据菜单权限删除 角色权限关系表
	 */
	@Override
	public int deleteByPermissionId(Long menuId) {
		Example example = new Example(RolePermission.class);
		example.createCriteria().andEqualTo("permissionId", menuId);
		return rolePermisstionMapper.deleteByExample(example);
	}

}
