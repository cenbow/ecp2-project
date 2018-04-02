package com.ecp.dao;

import java.math.BigDecimal;
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
	
	/**
	 * 根据条件查询合同支付总价
	 * @param params
	 * @return
	 */
	public BigDecimal selectContractPayPriceTotal(Map<String, Object> params);
	
	/**
	 * 查询合同商品列表，用于提成的计算
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectContractItems(Map<String, Object> params);
	
}