package com.ecp.service.back;

import java.util.List;
import java.util.Map;

import com.ecp.entity.Menu;
import com.ecp.entity.User;
import com.ecp.service.IBaseService;

public interface IUserService extends IBaseService<User, Long> {
	
	/**
	 * 根据登录名和密码查询
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username, String password);
	
	/**
	 * 根据登录名查询
	 * @param username
	 * @return
	 */
	public List<User> getByUsername(String username);
	
	/**
	 * 逻辑删除用户，物理删除用户角色关系
	 * @param id
	 * @return
	 */
	public int logicDelById(Long id);
	
	/**
	 * 查询未删除的用户
	 * 		deleted=1:默认（未删除）deleted=2:已删除
	 * 		type=1:默认（后台管理用户）type=2:前端访问用户
	 * @return
	 */
	public List<User> getList(Map<String, Object> map);
	
	/**
	 * 添加用户和用户角色
	 * @param user
	 * @param roleIds
	 * @return
	 */
	public int add(User user, String roleIds);
	
	/**
	 * 修改用户和用户角色
	 * @param user
	 * @param roleIds
	 * @return
	 */
	public int modify(User user, String roleIds);
	
	/**
	 * 根据用户ID查询菜单权限
	 * @param userId
	 * @return
	 */
	public List<Menu> getMenuPermissionListByUserId(Long userId);
	
	/**
	 * 查询IS和OS
	 * @return
	 */
	public List<Map<String, Object>> getISAndOS();
}
