package com.ecp.service.back;

import java.util.List;

import com.ecp.entity.UserRole;
import com.ecp.service.IBaseService;

public interface IUserRoleService extends IBaseService<UserRole, Long> {
	
	/**
	 * 根据用户ID查询用户角色
	 * @param userId
	 * @return
	 */
	public List<UserRole> getByUserId(Long userId);
	
	/**
	 * 根据用户ID删除用户角色关系表
	 * @param userId
	 * @return
	 */
	public int deleteByUserId(Long userId);
}
