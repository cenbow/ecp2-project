package com.ecp.service.front;

import java.util.List;
import java.util.Map;

import com.ecp.entity.CustLockRel;
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
	* @Title: addBindAgentToSale 
	* @Description: 增加绑定关系:绑定"签约客户"-"指定的用户". 
	* @param @param agentId 签约客户ID
	* @param @param userId  OS/IS ID
	* @param @return 插入记录个数    
	* @return int    返回类型 
	* @throws 
	*/
	public int addBindAgentToUser(long agentId,long userId,long roleId); 
	
}
