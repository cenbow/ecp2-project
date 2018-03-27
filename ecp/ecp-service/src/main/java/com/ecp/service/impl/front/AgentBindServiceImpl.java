package com.ecp.service.impl.front;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecp.dao.CustLockRelMapper;
import com.ecp.entity.CustLockRel;
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


	@Override
	public List<Map<String, Object>> getUsersByRoleCode(List<String> roleCodeList) {
		return custLockRelMapper.getUsersByRoleCode(roleCodeList);
	}

	

}
