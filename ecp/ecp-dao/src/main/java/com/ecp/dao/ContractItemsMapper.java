package com.ecp.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
	
	/** 
		* @Title: getContractAmountByNo 
		* @Description: 根据合同NO查询合同额 
		* @param @param contractId
		* @param @return     
		* @return BigDecimal    返回类型 
		* @throws 
	*/
	public BigDecimal getContractAmountByNo(@Param("contractNo") String contractNo);
	
	public BigDecimal searchContractAmount(@Param("orderTimeCond") int orderTimeCond, 
			@Param("dealStateCond") int dealStateCond, 
			@Param("searchTypeValue") int searchTypeValue,
			@Param("condValue") String condValue, 
			@Param("provinceName") String provinceName, 
			@Param("cityName") String cityName, 
			@Param("countyName") String countyName,
			@Param("agentIdList") List<Map<String, Object>> agentIdList);
	
}