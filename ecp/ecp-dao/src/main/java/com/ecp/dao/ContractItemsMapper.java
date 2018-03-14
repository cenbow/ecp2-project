package com.ecp.dao;

import java.util.List;
import java.util.Map;

import com.ecp.entity.ContractItems;
import tk.mybatis.mapper.common.Mapper;

public interface ContractItemsMapper extends Mapper<ContractItems> {
	
	/**
	 * @Description 根据合同id获取合同商品条目
	 * @param contractNo 合同号
	 * @return 合同商品列表
	 */
	public List<Map<String,Object>> selectItemsByContractNo(String contractNo);
}