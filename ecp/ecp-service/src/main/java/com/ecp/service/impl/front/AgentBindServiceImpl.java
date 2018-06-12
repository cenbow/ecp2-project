package com.ecp.service.impl.front;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecp.bean.DeletedType;
import com.ecp.dao.CustLockRelMapper;
import com.ecp.entity.CustLockRel;
import com.ecp.entity.User;
import com.ecp.service.front.IAgentBindService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class AgentBindServiceImpl extends AbstractBaseService<CustLockRel, Long> implements IAgentBindService {

	
	
	CustLockRelMapper custLockRelMapper;
	

	/**
	 * @param userMapper the mapper to set
	 * set方式注入
	 */	
	
	/**
	 * @param userMapper
	 */
	public void setCustLockRelMapper(CustLockRelMapper mapper) {
		this.custLockRelMapper=mapper;
		this.setMapper(mapper);
	}

	
	@Override
	public List<Map<String, Object>> getSalesByAgentId(long agentId, String roleCode) {
		return custLockRelMapper.getSalesByAgentId(agentId,roleCode);		
	}

	@Override
	public List<Map<String, Object>> getUsersByRoleName(String roleName) {
		return custLockRelMapper.getUsersByRoleName(roleName);
	}

	@Override
	public int addBindAgentToUser(long agentId, long userId,long roleId) {
		CustLockRel rec=new CustLockRel();
		rec.setCustId(agentId);
		rec.setBindUserId(userId);
		rec.setRoleId(roleId);
		return custLockRelMapper.insertSelective(rec);		
	}
	
	/** 
		* @Title: hasBind 
		* @Description: 判定一个用户(包含角色)是否已经绑定到相应的代理商 
		* @param @param agentId
		* @param @param userId
		* @param @param roleId
		* @param @return     
		* @return boolean    返回类型 
		* 		如果已经绑定则返回true; 
		* 		否则返回false;
		* @throws 
	*/
	@Override
	public boolean hasBind(long agentId,long userId,long roleId){
		CustLockRel rec=new CustLockRel();
		rec.setCustId(agentId);
		rec.setBindUserId(userId);
		rec.setRoleId(roleId);
		rec.setDeleted(DeletedType.NO);
		CustLockRel bindedSale=custLockRelMapper.selectOne(rec);
		if(bindedSale==null)		
			return false;
		else
			return true;
	}


	@Override
	public List<Map<String, Object>> getUsersByRoleCode(List<String> roleCodeList) {
		return custLockRelMapper.getUsersByRoleCode(roleCodeList);
	}


	@Override
	public List<Map<String, Object>> getUsersByUserIdAndRoleCode(long userId, List<String> roleCodeList) {
		return custLockRelMapper.getUsersByUserIdAndRoleCode(userId,roleCodeList);
	}


	@Override
	public List<Map<String,Object>> getAgentIdListByBindedUser(long userId, long roleId) {
		return custLockRelMapper.getAgentIdListByBindedUserRoleId(userId,roleId);
	}


	@Override
	public List<Map<String,Object>> getAgentIdListByBindedUser(long userId) {
		return custLockRelMapper.getAgentIdListByBindedUserId(userId);
	}


	@Override
	public List<Map<String, Object>> getSalesByAgentIdAndRoleCodes(long agentId, List<String> roleCodeList) {
		
		return custLockRelMapper.getSalesByAgentIdAndRoleCodes(agentId,  roleCodeList);
	}


	@Override
	public List<Map<String, Object>> getAgentByUserIdListAndRoleId(List<User> userList, long roleId) {
		return custLockRelMapper.getAgentByUserIdListAndRoleId(userList,roleId);
	}


	@Override
	public int deleteByAgentId(List<Long> agentIdList) {
		return custLockRelMapper.deleteByAgentId(agentIdList);
		
	}
	

}
