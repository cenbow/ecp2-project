package com.ecp.service.front;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ecp.entity.AccountCompany;
import com.ecp.service.IBaseService;



/**
 * Copyright (c) 2018 by [个人或者公司信息]
 * @ClassName:     IAccountCompanyService.java
 * @Description:   公司帐薄管理 
 * 
 * @author:        lenovo
 * @version:       V1.0  
 * @Date:          2018年3月21日 下午5:06:31 
 */
public interface IAccountCompanyService extends IBaseService<AccountCompany, Long> {
	
	
	/** 
	* @Title: getItemsByOrder 
	* @Description: 根据订单及帐薄条目类型 查询 
	* @param @param orderId	订单自增ID
	* @param @param orderNo	订单No
	* @param @param itemTypeList 帐薄条目类型列表.
	* @param @return    设定文件 
	* @return List<AccountCompany>    公司帐薄条目 
	* @throws 
	*/
	public List<AccountCompany> getItemsByOrder(long orderId,String orderNo,List<Integer> itemTypeList);
	
	
	/** 
		* @Title: getItemsByOrderAndBindUser 
		* @Description: TODO(这里用一句话描述这个方法的作用) 
		* @param @param orderId
		* @param @param itemTypeList
		* @param @param userId    如果uerId=0,则为查询所有.按订单来查询
		* 						    如果userId!=0,则按user来查询
		* @param @param roleIdList
		* @param @return     
		* @return List<AccountCompany>    返回类型 
		* @throws 
	*/
	public List<AccountCompany> getItemsByOrderAndBindUser(long orderId,List<Integer> itemTypeList,long userId,List<Long> roleIdList);
	
	
	/** 
		* @Title: selectItems 
		* @Description: 查询费用 
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
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	public List<AccountCompany> selectItems(
			 int orderTimeCond,
			 int dealStateCond,
			 int searchTypeValue,String condValue,
			 String provinceName,String cityName,String countyName,
			 List<Map<String,Object>> agentIdList,
			 List<Integer>itemTypeList,
			 long bindedUserId,
			 List<Long> roleIdList
			 );
	
	/** 
	* @Title: addAccountItem 
	* @Description: 增加公司帐条目 
	* @param @param orderId  订单自增ID
	* @param @param orderNo  订单No
	* @param @param itemType 帐薄条目类型
	* @param @param amount	 金额
	* @param @param comment	 备注
	* @param @return     
	* @return int    返回类型 
	* @throws 
	*/
	public int addAccountItem(AccountCompany accountItem); 
	
	/**
	 * 根据Map参数查询公司账本
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectAccountCompanyMap(Map<String, Object> params);
	/** 
		* @Title: getAmountByOrderId 
		* @Description: 查询指定订单的某分录类型的SUM 
		* @param @param orderId
		* @param @param accountItemType
		* @param @return     
		* @return BigDecimal    返回类型 
		* @throws 
	*/
	public BigDecimal getAmountByOrderId(long orderId,int accountItemType);
	
	
	/** 
		* @Title: searchAccountItemAmount 
		* @Description: 查询范围内的指定分类类型的总金额 
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
	public BigDecimal searchAccountItemAmount(
			 int orderTimeCond,
			 int dealStateCond,
			 int searchTypeValue,String condValue,
			 String provinceName,String cityName,String countyName,
			 List<Map<String,Object>> agentIdList,
			 List<Integer>itemTypeList,
			 long bindedUserId,
			 List<Long> roleIdList
			 );
	
}
