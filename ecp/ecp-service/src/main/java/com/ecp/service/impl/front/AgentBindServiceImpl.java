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


	/* (非 Javadoc) 
	* <p>Title: getSalesByAgentId</p> 
	* <p>Description: </p> 
	* @param agentId
	* @param roleName
	* @return 
	* @see com.ecp.service.front.IAgentBindService#getSalesByAgentId(long, java.lang.String) 
	*/
	@Override
	public List<Map<String, Object>> getSalesByAgentId(long agentId, String roleName) {
		return custLockRelMapper.getSalesByAgentId(agentId,roleName);		
	}


	/* (非 Javadoc) 
	* <p>Title: getSales</p> 
	* <p>Description: </p> 
	* @param roleName
	* @return 
	* @see com.ecp.service.front.IAgentBindService#getSales(java.lang.String) 
	*/
	@Override
	public List<Map<String, Object>> getSales(String roleName) {
		return custLockRelMapper.getSales(roleName);
		
	}


	@Override
	public int addBindAgentToUser(long agentId, long userId) {
		CustLockRel rec=new CustLockRel();
		rec.setCustId(agentId);
		rec.setBindUserId(userId);
		return custLockRelMapper.insertSelective(rec);		
		
	}

	

}
