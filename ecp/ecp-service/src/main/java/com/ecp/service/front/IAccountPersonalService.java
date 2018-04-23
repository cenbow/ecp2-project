package com.ecp.service.front;

import java.util.List;
import java.util.Map;

import com.ecp.entity.AccountPersonal;
import com.ecp.service.IBaseService;

/**
 * Copyright (c) 2018 by [个人或者公司信息]
 * @ClassName:     AccountPersonalService.java
 * @Description:   个人帐薄管理 
 * 
 * @author:        lenovo
 * @version:       V1.0  
 * @Date:          2018年3月21日 下午5:58:04 
 */
public interface IAccountPersonalService extends IBaseService<AccountPersonal, Long> {
	
	
	/** 
	* @Title: getItemsByOrder 
	* @Description: 根据订单及帐薄条目类型 查询 
	* @param @param orderId	订单自增ID
	* @param @param itemTypeList 帐薄条目类型列表.
	* @param @return    设定文件 
	* @return List<AccountPersonal>    个人帐薄条目 
	* @throws 
	*/
	public List<AccountPersonal> getItemsByOrder(long orderId,List<Integer> itemTypeList);
	
	
	
	public List<AccountPersonal> getItemsByOrderAndBindUser( long orderId, 
			    List<Integer> itemTypeList, 
			    long userId,
			    List<Long> roleIdList);
	
	
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
public List<AccountPersonal> selectItems(
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
		* @Description: TODO(这里用一句话描述这个方法的作用) 
		* @param @param accountItem
		* @param @return     
		* @return int    返回类型 
		* @throws 
	*/
	public int addAccountItem(AccountPersonal accountItem); 
	
	/**
	 * 查询业绩
	 * @param order_id
	 * @param role_code_list
	 * @return
	 */
	public List<Map<String, Object>> selectPerformanceMap(Map<String, Object> params);
	
	/** 
		* @Title: logicDeleteByCompanyItemId 
		* @Description: 根据所引用的公司分录ID删除个人帐薄中的分录 
		* @param @param companyItemId
		* @param @return     
		* @return int    返回类型 
		* @throws 
	*/
	public int logicDeleteByCompanyItemId(long companyItemId);
	
	//根据公司分录ID查询所对应的用户(在个人帐薄中查询)
	public List<Map<String,Object>> getUserByAccountCompanyId(long accountCompanyId);
	
}
