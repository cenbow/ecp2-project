package com.ecp.service.front;

import java.math.BigDecimal;
import java.util.List;

import com.ecp.entity.AccountCompany;
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
	* @param @param orderNo	订单No
	* @param @param itemTypeList 帐薄条目类型列表.
	* @param @return    设定文件 
	* @return List<AccountCompany>    公司帐薄条目 
	* @throws 
	*/
	public List<AccountCompany> getItemsByOrder(long orderId,String orderNo,List<Integer> itemTypeList);
	
	/** 
	* @Title: addAccountItem 
	* @Description: 增加帐薄条目 
	* @param @param orderId  订单自增ID
	* @param @param orderNo  订单No
	* @param @param itemType 帐薄条目类型
	* @param @param amount	 金额
	* @param @return     
	* @return int    返回类型 
	* @throws 
	*/
	public int addAccountItem(long orderId,String orderNo, int itemType,BigDecimal amount); 
	
	
}
