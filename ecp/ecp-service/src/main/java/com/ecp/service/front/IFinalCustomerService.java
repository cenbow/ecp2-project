package com.ecp.service.front;

import java.util.List;

import com.ecp.entity.FinalCustomer;
import com.ecp.service.IBaseService;

/**
 * Copyright (c) 2018 by [个人或者公司信息]
 * @ClassName:     IFinalCustomerService.java
 * @Description:   订单-最终客户管理 
 * 
 * @author:        lenovo
 * @version:       V1.0  
 * @Date:          2018年3月23日 下午11:42:40 
 */
public interface IFinalCustomerService extends IBaseService<FinalCustomer, Long> {
	
	
	/** 
	* @Title: getItemsByOrder 
	* @Description: 查询此订单下的最终用户 
	* @param @param orderId 订单自增ID
	* @param @return    设定文件 
	* @return List<FinalCustomer>    返回类型 
	* @throws 
	*/
	public List<FinalCustomer> getFinalCustomerByOrder(long orderId);
	
	/** 
	* @Title: addFinalCustomer 
	* @Description: 增加最终客户
	* @param @param finalCustomer
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int addFinalCustomer(FinalCustomer finalCustomer);	
	
}
