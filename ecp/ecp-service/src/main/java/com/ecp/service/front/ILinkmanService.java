package com.ecp.service.front;

import java.util.List;

import com.ecp.entity.Linkman;
import com.ecp.service.IBaseService;



/**
 * Copyright (c) 2018 by [个人或者公司信息]
 * @ClassName:     ILinkmanService.java
 * @Description:   订单-联系人管理 
 * 
 * @author:        lenovo
 * @version:       V1.0  
 * @Date:          2018年3月21日 下午5:06:31 
 */
public interface ILinkmanService extends IBaseService<Linkman, Long> {
	
	
	/** 
	* @Title: getItemsByOrder 
	* @Description: 查询此订单下的联系人 
	* @param @param orderId 订单自增ID
	* @param @param orderNo 订单No
	* @param @return    设定文件 
	* @return List<Linkman>    返回类型 
	* @throws 
	*/
	public List<Linkman> getLinkmanByOrder(long orderId);
	
	/** 
	* @Title: addLinkman 
	* @Description: 增加联系人 
	* @param @param linkman
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int addLinkman(Linkman linkman);	
	
}
