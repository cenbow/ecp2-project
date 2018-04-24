package com.ecp.service.impl.front;

import java.math.BigDecimal;
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

	@Override
	public BigDecimal selectContractPayPriceTotal(Map<String, Object> params) {
		return contractItemsMapper.selectContractPayPriceTotal(params);
	}

	@Override
	public List<Map<String, Object>> selectContractItems(Map<String, Object> params) {
		return contractItemsMapper.selectContractItems(params);
	}

	@Override
	public BigDecimal getContractAmountByNo(String contractNo) {
		return contractItemsMapper.getContractAmountByNo(contractNo);
	}

	@Override
	public BigDecimal searchContractAmount(int orderTimeCond, int dealStateCond, int searchTypeValue, String condValue,
			String provinceName, String cityName, String countyName, List<Map<String, Object>> agentIdList,int totalPayFlag) {
		return contractItemsMapper.searchContractAmount( -orderTimeCond,  dealStateCond,  searchTypeValue,  condValue,
				 provinceName,  cityName,  countyName, agentIdList,totalPayFlag);
	}

}
