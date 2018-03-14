package com.ecp.service.impl.front;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecp.dao.ContractAttributeMapper;
import com.ecp.entity.ContractAttribute;
import com.ecp.service.front.IContractAttributeService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class ContractAttributeServiceImpl extends AbstractBaseService<ContractAttribute, Long> implements IContractAttributeService {

	private ContractAttributeMapper contractAttributeMapper;

	/**
	 * @param 
	 * set方式注入
	 */	
	
	public void setContractAttributeMapper(ContractAttributeMapper contractAttributeMapper) {
		this.contractAttributeMapper=contractAttributeMapper;
		this.setMapper(contractAttributeMapper);
	}

	@Override
	public List<ContractAttribute> selectAttributesByTemplateId(long templateId) {
		ContractAttribute record=new ContractAttribute();
		record.setTemplateId(templateId);
		
		return contractAttributeMapper.select(record);
		
		
	}

	@Override
	public List<Map<String, String>> selectAttrValByContractId(long contractId) {
		
		return contractAttributeMapper.selectAttrValByContractId(contractId); 
	}
	

}
