package com.ecp.service.impl.front;

import org.springframework.stereotype.Service;

import com.ecp.dao.ContractTemplateMapper;
import com.ecp.entity.ContractTemplate;
import com.ecp.service.front.IContractTemplateService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class ContractTemplateServiceImpl extends AbstractBaseService<ContractTemplate, Long> implements IContractTemplateService {

	private ContractTemplateMapper contractTemplateMapper;

	/**
	 * @param the mapper to set
	 * set方式注入
	 */	
	
	public void setContractTemplateMapper(ContractTemplateMapper contractTemplateMapper) {
		this.contractTemplateMapper=contractTemplateMapper;
		this.setMapper(contractTemplateMapper);
	}
	

}
