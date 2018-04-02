package com.ecp.service.impl.back;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ecp.dao.RoleMapper;
import com.ecp.entity.Role;
import com.ecp.entity.RolePermission;
import com.ecp.service.back.IRolePermissionService;
import com.ecp.service.back.IRoleService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("roleServiceBean")
public class RoleServiceImpl extends AbstractBaseService<Role, Long> implements IRoleService {

	private RoleMapper roleMapper;

	/**
	 * @param roleMapper the roleMapper to set
	 * set方式注入
	 */
	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
		this.setMapper(roleMapper);
	}
	
	@Resource(name="rolePermissionServiceBean")
	private IRolePermissionService rolePermsService;

	/**
	 * @see com.ecp.service.back.IRoleService#add(com.ecp.entity.Role, java.lang.String)
	 * 添加角色和角色权限（菜单权限）
	 */
	@Override
	@Transactional
	public int add(Role role, String permissionIds) {
		int rows = roleMapper.insertSelective(role);
		if(rows>0){
			if(StringUtils.isNotBlank(permissionIds)){
				rows = this.saveRolePermission(role.getRoleId(), permissionIds);
				if(rows>0){
					return rows;
				}
			}
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

	/**
	 * @see com.ecp.service.back.IRoleService#modify(com.ecp.entity.Role, java.lang.String)
	 * 修改角色和角色权限（菜单权限）
	 */
	@Override
	@Transactional
	public int modify(Role role, String permissionIds) {
		int rows = roleMapper.updateByPrimaryKeySelective(role);
		if(rows>0){
			rows = rolePermsService.deleteByRoleId(role.getRoleId());
			if(StringUtils.isNotBlank(permissionIds)){
				rows = this.saveRolePermission(role.getRoleId(), permissionIds);
				if(rows>0){
					return rows;
				}
			}
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}
	
	/**
	 * 保存角色权限（菜单权限）
	 * @param roleId
	 * @param permissionIds
	 * @return
	 */
	@Transactional
	private int saveRolePermission(Long roleId, String permissionIds){
		int rows = 0;
		String[] ids = permissionIds.split(",");
		int len = ids.length;
		if(len<=0){
			return 1;
		}
		for(int i=0; i<len; i++){
			String permsId = ids[i];//获取权限ID，此处的权限为菜单权限，用的是菜单表
			if(StringUtils.isNotBlank(permsId)){
				RolePermission temp = new RolePermission();
				temp.setRoleId(roleId);
				temp.setPermissionId(Long.valueOf(permsId));
				rows = rolePermsService.insertSelective(temp);
				if(rows>0){
					continue;
				}else{
					break;
				}
			}
		}
		if(rows>0){
			return rows;
		}
		return 0;
	}

	@Override
	@Transactional
	public int del(Long roleId) {
		try {
			int rows = roleMapper.deleteByPrimaryKey(roleId);
			if(rows>0){
				rolePermsService.deleteByRoleId(roleId);
				return rows;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

	@Override
	public List<Role> getByCode(String roleCode) {
		Example example = new Example(Role.class);
		example.createCriteria().andEqualTo("deleted", 1).andEqualTo("roleCode", roleCode);
		return roleMapper.selectByExample(example);
	}

	@Override
	public List<Role> getByUserId(Long userId) {
		return roleMapper.getByUserId(userId);
	}

}
