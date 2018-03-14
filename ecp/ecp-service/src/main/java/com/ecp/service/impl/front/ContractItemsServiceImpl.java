package com.ecp.service.impl.front;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecp.dao.ContractItemsMapper;
import com.ecp.entity.ContractItems;
import com.ecp.service.front.IContractItemsService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class ContractItemsServiceImpl extends AbstractBaseService<ContractItems, Long> implements IContractItemsService {

	private ContractItemsMapper contractItemsMapper;

	/**
	 * @param  the mapper to set 
	 * set方式注入
	 */	
	
	public void setContractItemsMapper(ContractItemsMapper contractItemsMapper) {
		this.contractItemsMapper=contractItemsMapper;
		this.setMapper(contractItemsMapper);
	}
	
	public List<Map<String,Object>> selectItemsByContractNo(String contractNo) {
		return contractItemsMapper.selectItemsByContractNo(contractNo);
	}
	

}
