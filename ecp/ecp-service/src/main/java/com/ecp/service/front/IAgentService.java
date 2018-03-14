package com.ecp.service.front;

import java.util.List;

import com.ecp.entity.User;
import com.ecp.service.IBaseService;

/**
 * @ClassName IAgentService
 * @Description 代理商:帐号接口（user）
 * @author Administrator
 * @Date 2017年5月18日 上午6:43:19
 * @version 1.0.0
 */
public interface IAgentService extends IBaseService<User, Long> {
	
	/**
	 * @Description 代理商-登录
	 * @param loginName
	 * @param password
	 * @return  如果登录成则返回相应的对象，否则返回null
	 */
	public User login(String loginName, String password);  
	
	/**
	 * @Description 代理商-注册
	 * @param loginName
	 * @param password
	 * @return 如果注册成功，则返回0，否则返回非0数
	 */
	public int register(String loginName,String password); 
	
	/**
	 * @Description 通过登录名读取用户列表。
	 * @param loginName
	 * @return
	 */
	public List<User> getByLoginName(String loginName);
	
	/**
	 * @Description 查询是否有相同的登录名
	 * @param loginName
	 * @return 有:true;没有:false;
	 */
	public boolean hasSameLoginName(String loginName);
	
	
}
