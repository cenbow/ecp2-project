package com.ecp.service.impl.front;

import org.springframework.stereotype.Service;

import com.ecp.dao.ContractAttrValueMapper;
import com.ecp.entity.ContractAttrValue;
import com.ecp.service.front.IContractAttrValueService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class ContractAttrValueServiceImpl extends AbstractBaseService<ContractAttrValue, Long> implements IContractAttrValueService {

	private ContractAttrValueMapper contractAttrValueMapper;

	/**
	 * @param the mapper to set
	 * set方式注入
	 */	
	
	public void setContractAttrValueMapper(ContractAttrValueMapper contractAttrValueMapper) {
		this.contractAttrValueMapper=contractAttrValueMapper;
		this.setMapper(contractAttrValueMapper);
	}
	

}
