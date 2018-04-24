package com.ecp.service.front;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ecp.entity.ContractItems;
import com.ecp.service.IBaseService;


/**
 * @ClassName IContractService
 * @Description 合同行条目-业务层
 * @author Administrator
 * @Date 2017年6月28日 下午5:02:50
 * @version 1.0.0
 */
public interface IContractItemsService extends IBaseService<ContractItems, Long> {
	
	/**
	 * @Description 根据合同编号读取合同商品条目
	 * @param contractNo
	 * @return
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
		* @Description: 根据合同号查询合同额 
		* @param @param contractNo
		* @param @return     
		* @return BigDecimal    返回类型 
		* @throws 
	*/
	public BigDecimal getContractAmountByNo(String contractNo);
	
	/** 
		* @Title: searchContractAmount 
		* @Description: 查询范围内合同总金额 
		* @param @param orderTimeCond
		* @param @param dealStateCond
		* @param @param searchTypeValue
		* @param @param condValue
		* @param @param provinceName
		* @param @param cityName
		* @param @param countyName
		* @param @param agentIdList
		* @param @param itemTypeList
		* @param @param bindedUserId
		* @param @param roleIdList
		* @param @return     
		* @return BigDecimal    返回类型 
		* @throws 
	*/
	public BigDecimal searchContractAmount(
			 int orderTimeCond,
			 int dealStateCond,
			 int searchTypeValue,String condValue,
			 String provinceName,String cityName,String countyName,
			 List<Map<String,Object>> agentIdList,int totalPayFlag);
	
}
