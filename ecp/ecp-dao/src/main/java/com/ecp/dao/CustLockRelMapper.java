package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.CustLockRel;
import com.ecp.entity.User;

import tk.mybatis.mapper.common.Mapper;

public interface CustLockRelMapper extends Mapper<CustLockRel> {
	public List<Map<String,Object>> getSalesByAgentId(@Param("agentId") long agentId,@Param("roleCode")String roleCode);
	public List<Map<String,Object>> getUsersByRoleName(@Param("roleName") String roleName);
	public List<Map<String, Object>> getUsersByRoleCode(@Param("roleCodeList") List<String> roleCodeList);
	public List<Map<String, Object>> getUsersByUserIdAndRoleCode(@Param("userId") long userId, @Param("roleCodeList") List<String> roleCodeList) ;
	
	public List<Map<String, Object>> getAgentIdListByBindedUserRoleId(@Param("userId") long userId,@Param("roleId") long roleId);
	public List<Map<String, Object>> getAgentIdListByBindedUserId(@Param("userId") long userId); 
	
	public List<Map<String, Object>> getSalesByAgentIdAndRoleCodes(@Param("agentId") long agentId, @Param("roleCodeList") List<String> roleCodeList);
	
	public List<Map<String, Object>> getAgentByUserIdListAndRoleId(@Param("userList") List<User> userList, @Param("roleId") long roleId);
	
	public int deleteByAgentId(@Param("agentIdList") List<Long> agentIdList);
}