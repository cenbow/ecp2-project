package com.ecp.service.front;

import java.util.List;
import java.util.Map;

import com.ecp.entity.CustLockRel;
import com.ecp.entity.User;
import com.ecp.entity.UserExtends;
import com.ecp.service.IBaseService;


/**
 * @ClassName IAgentBindService
 * @Description 签约客户-绑定
 * @author Administrator
 * @Date 2018年3月18日
 * @version 1.0.0
 */
public interface IAgentBindService extends IBaseService<CustLockRel, Long> {
	
	/** 
	* @Title: getSalesByAgentId 
	* @Description: 查询代理商所绑定的OS或IS user列表 
	* @param @param agentId 代理商ID
	* @param @param roleCode 角色编码(OS/IS角色编码)
	* @param @return  用户列表
	* @return List<Map<String,Object>>    返回类型
	* @throws 
	*/
	public List<Map<String,Object>> getSalesByAgentId(long agentId,String roleCode);
	
	
	
	/** 
		* @Title: getSalesByAgentIdAndRoleCodes 
		* @Description: 查询特定代理商的 指定角色的内部用户 
		* @param @param agentId	代理商ID
		* @param @param roleCodeList 角色列表
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	public List<Map<String,Object>> getSalesByAgentIdAndRoleCodes(long agentId, List<String> roleCodeList);
	
	/** 
	* @Title: getSales 
	* @Description: 查询指定角色名称的所有用户 
	* @param @param roleName 角色名称
	* @param @return    指定角色的用户列表
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String,Object>> getUsersByRoleName(String roleName);
	
	
	/** 
		* @Title: getSalesByRoleCode 
		* @Description: 根据角色编码选择用户 
		* @param @param roleCodeList 角色编码列表  
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	public List<Map<String,Object>> getUsersByRoleCode(List<String> roleCodeList);
	
	
	
	/** 
		* @Title: getUsersByUserIdAndRoleCode 
		* @Description: 根据角色编码,及用户ID 选择用户 
		* @param @param userId
		* @param @param roleCodeList
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	public List<Map<String,Object>> getUsersByUserIdAndRoleCode(long userId,List<String> roleCodeList);
	
	
	/** 
		* @Title: getAgentIdListByBindedUser 
		* @Description: 获取指定用户所绑定的代理商ID列表 
		* @param @param userId
		* @param @param roleId
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	public List<Map<String,Object>> getAgentIdListByBindedUser(long userId,long roleId);
	/** 
		* @Title: getAgentIdListByBindedUser 
		* @Description: 获取指定用户所绑定的代理商ID列表 
		* @param @param userId
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	public List<Map<String,Object>> getAgentIdListByBindedUser(long userId);
	
	
	/** 
	* @Title: addBindAgentToSale 
	* @Description: 增加绑定关系:绑定"签约客户"-"指定的用户". 
	* @param @param agentId 签约客户ID
	* @param @param userId  OS/IS ID
	* @param @return 插入记录个数    
	* @return int    返回类型 
	* @throws 
	*/
	public int addBindAgentToUser(long agentId,long userId,long roleId);
	
	/** 
		* @Title: hasBind 
		* @Description: TODO(这里用一句话描述这个方法的作用) 
		* @param @param agentId
		* @param @param userId
		* @param @param roleId
		* @param @return     
		* @return boolean    返回类型 
		* @throws 
	*/
	public boolean hasBind(long agentId,long userId,long roleId);
	
	/** 
		* @Title: getAgentByUserIdListAndRoleCode 
		* @Description: 根据用户列表及角色ID进行查询 
		* @param @param userList
		* @param @param roleId
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	public List<Map<String,Object>> getAgentByUserIdListAndRoleId(List<User> userList,long roleId);
	
	/** 
		* @Title: deleteByAgentId 
		* @Description: TODO(这里用一句话描述这个方法的作用) 
		* @param @param agentList
		* @param @return     
		* @return int    返回类型 
		* @throws 
	*/
	public int deleteByAgentId(List<Long> agentIdList);
	
}
