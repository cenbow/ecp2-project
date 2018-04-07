package com.ecp.service.impl.back;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ecp.bean.DeletedType;
import com.ecp.dao.UserMapper;
import com.ecp.entity.Menu;
import com.ecp.entity.RolePermission;
import com.ecp.entity.User;
import com.ecp.entity.UserRole;
import com.ecp.service.back.IMenuService;
import com.ecp.service.back.IRolePermissionService;
import com.ecp.service.back.IRoleService;
import com.ecp.service.back.IUserRoleService;
import com.ecp.service.back.IUserService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("userServiceBean")
public class UserServiceImpl extends AbstractBaseService<User, Long> implements IUserService {

	private UserMapper userMapper;

	/**
	 * @param userMapper the userMapper to set
	 * set方式注入
	 */
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
		this.setMapper(userMapper);
	}
	
	@Resource(name="roleServiceBean")
	private IRoleService roleService;
	@Resource(name="userRoleServiceBean")
	private IUserRoleService userRoleService;
	@Resource(name="menuServiceBean")
	private IMenuService menuService;//菜单，此处称为菜单权限
	@Resource(name="rolePermissionServiceBean")
	private IRolePermissionService rolePermsService;

	/** 
	 * @author: srd
	 * @see com.ecp.service.IUserService#login(java.lang.String, java.lang.String)
	 * 根据用户名和密码查询
	 */
	public User login(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		List<User> userList = userMapper.select(user);
		if(userList!=null && userList.size()>0){
			return userList.get(0);
		}
		return null;
	}

	/** 
	 * (non-Javadoc)
	 * @see com.ecp.service.IUserService#getByUsername(java.lang.String)
	 * 根据用户名查询
	 */
	@Override
	public List<User> getByUsername(String username) {
		Example example=new Example(User.class);
		example.createCriteria().andEqualTo("username", username).andEqualTo("deleted", DeletedType.NO);
		List<User> list=userMapper.selectByExample(example);
		return list;
	}

	/**
	 * @see com.ecp.service.back.IUserService#logicDelById(java.lang.Long)
	 * 逻辑删除用户，物理删除用户角色关系
	 */
	@Override
	@Transactional
	public int logicDelById(Long id) {
		try {
			User user = new User();
			user.setId(id);
			user.setDeleted(2);//是否删除（1-未删除，2-删除，默认1）
			int rows = userMapper.updateByPrimaryKeySelective(user);
			if(rows>0){
				userRoleService.deleteByUserId(id);
				return rows;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

	/**
	 * @see com.ecp.service.back.IUserService#getList(java.util.Map)
	 * 查询未删除的用户
	 * 		deleted=1:默认（未删除）deleted=2:已删除
	 * 		type=1:默认（后台管理用户）type=2:前端访问用户
	 */
	@Override
	public List<User> getList(Map<String, Object> map) {
		Example example=new Example(User.class);
		example.createCriteria().andEqualTo("deleted", map.get("deleted").toString()).andEqualTo("type", map.get("type").toString());
		List<User> list=userMapper.selectByExample(example);
		return list;
	}

	/**
	 * @see com.ecp.service.back.IUserService#add(com.ecp.entity.User, java.lang.String)
	 * 添加用户和用户角色
	 */
	@Override
	@Transactional
	public int add(User user, String roleIds) {
		int rows = userMapper.insertSelective(user);
		if(rows>0){
			if(StringUtils.isNotBlank(roleIds)){
				rows = this.saveUserRole(user.getId(), roleIds);
				if(rows>0){
					return rows;
				}
			}
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

	/**
	 * @see com.ecp.service.back.IUserService#modify(com.ecp.entity.User, java.lang.String)
	 * 修改用户和用户角色
	 */
	@Override
	@Transactional
	public int modify(User user, String roleIds) {
		int rows = userMapper.updateByPrimaryKeySelective(user);
		if(rows>0){
			rows = userRoleService.deleteByUserId(user.getId());
			if(StringUtils.isNotBlank(roleIds)){
				rows = this.saveUserRole(user.getId(), roleIds);
				if(rows>0){
					return rows;
				}
			}
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}
	
	/**
	 * 保存用户角色
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	@Transactional
	private int saveUserRole(Long userId, String roleIds){
		int rows = 0;
		String[] ids = roleIds.split(",");
		int len = ids.length;
		if(len<=0){
			return 1;
		}
		for(int i=0; i<len; i++){
			String roleId = ids[i];
			if(StringUtils.isNotBlank(roleId)){
				UserRole temp = new UserRole();
				temp.setUserId(userId);
				temp.setRoleId(Long.valueOf(roleId));
				rows = userRoleService.insertSelective(temp);
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

	/**
	 * @see com.ecp.service.back.IUserService#getMenuPermissionListByUserId(java.lang.Long)
	 * 根据用户ID查询菜单权限
	 */
	@Override
	public List<Menu> getMenuPermissionListByUserId(Long userId) {
		//根据用户ID查询用户角色表，获取角色ID
		List<UserRole> list1 = userRoleService.getByUserId(userId);
		if(list1.isEmpty() || list1.size()<=0){
			return null;
		}
		List<Long> roleIds = new ArrayList<Long>();
		for(UserRole ur : list1){
			roleIds.add(ur.getRoleId());
		}
		//根据角色ID查询角色权限表，获取权限ID（此处权限为菜单权限，所以权限表为菜单表）
		List<RolePermission> list2 = rolePermsService.getByRoleIds(roleIds);
		if(list2.isEmpty() || list2.size()<=0){
			return null;
		}
		List<Long> menuIds = new ArrayList<Long>();
		for(RolePermission rp : list2){
			menuIds.add(rp.getPermissionId());
		}
		//根据权限ID查询权限，并返回
		return menuService.getByMenuIds(menuIds);
	}

	/**
	 * 查询IS和OS
	 * @see com.ecp.service.back.IUserService#getISAndOS()
	 */
	@Override
	public List<Map<String, Object>> getISAndOS() {
		return userMapper.getISAndOS();
	}

	@Override
	public List<Map<String, Object>> getISAndOSUser() {
		return userMapper.getISAndOSUser();
	}
	
	@Override
	public List<Map<String, Object>> getById(Long userId) {
		List<Map<String, Object>> userList = new ArrayList<>();
		Map<String, Object> user = userMapper.getById(userId);
		userList.add(user);
		return userList;
	}

}
