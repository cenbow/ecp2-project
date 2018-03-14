package com.ecp.service.impl.back;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecp.dao.UserRoleMapper;
import com.ecp.entity.UserRole;
import com.ecp.service.back.IUserRoleService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("userRoleServiceBean")
public class UserRoleServiceImpl extends AbstractBaseService<UserRole, Long> implements IUserRoleService {

	private UserRoleMapper userRoleMapper;

	/**
	 * @param userRoleMapper the userRoleMapper to set
	 * set方式注入
	 */
	public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
		this.userRoleMapper = userRoleMapper;
		this.setMapper(userRoleMapper);
	}

	/**
	 * @see com.ecp.service.back.IUserRoleService#deleteByUserId(java.lang.Long)
	 * 根据用户ID删除用户角色关系表
	 */
	@Override
	public int deleteByUserId(Long userId) {
		Example example = new Example(UserRole.class);
		example.createCriteria().andEqualTo("userId", userId);
		UserRole userRole = new UserRole();
		userRole.setDeleted(2);//deleted 是否删除（1-未删除，2-删除，默认1）
		return userRoleMapper.updateByExampleSelective(userRole, example);
	}

	/**
	 * @see com.ecp.service.back.IUserRoleService#getByUserId(java.lang.Long)
	 * 根据用户ID查询用户角色
	 */
	@Override
	public List<UserRole> getByUserId(Long userId) {
		Example example = new Example(UserRole.class);
		example.createCriteria().andEqualTo("userId", userId).andEqualTo("deleted", 1);//deleted 是否删除（1-未删除，2-删除，默认1）
		return userRoleMapper.selectByExample(example);
	}

}
