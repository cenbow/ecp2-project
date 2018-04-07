package com.ecp.service.front;

import java.util.List;
import java.util.Map;

import com.ecp.entity.User;
import com.ecp.entity.UserExtends;
import com.ecp.service.IBaseService;


/**
 * @ClassName IUserAgentService
 * @Description 签约客户-业务层
 * @author Administrator
 * @Date 2017年6月17日 下午1:50:03
 * @version 1.0.0
 */
public interface IUserAgentService extends IBaseService<UserExtends, Long> {
	
	
	/**
	 * @Description 增加签约客户
	 * @param agent 签约客户对象
	 * @return 影响的行数
	 */
	public int  addUserAgent(UserExtends agent);
	
	/**
	 * @Description 获取所有签约用户列表
	 * @return
	 */
	public List<UserExtends> getAllUserAgent();
	
	/**
	 * @Description 根据用户ID获取代理商信息
	 * @param userId  用户ID
	 * @return  如果找到则返回相应对象，否则返回null;
	 */
	public UserExtends getUserAgentByUserId(long userId);
	
	/**
	 * @Description 根据所选字段值、输入的条件进行查询
	 * @param searchTypeValue  字段
	 * @param condValue 查询字符串
	 * @return
	 */
	public List<UserExtends> searchUserAgent(int searchTypeValue,String condValue);
	
	/**
	 * @Description 根据所选字段值、输入的条件进行查询
	 * @param searchTypeValue  字段
	 * @param condValue 查询字符串
	 * @return
	 */
	public List<UserExtends> searchUserAgent(int searchTypeValue,String condValue,String provinceName,String cityName,String countyName);
	
	/** 
		* @Title: getUsersByAgentId 
		* @Description: 根据代理商ID查询此代理商下的所有用户 
		* @param @param agentId
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	public List<Map<String,Object>> getUsersByAgentId(long agentId);
	
	
	
}
