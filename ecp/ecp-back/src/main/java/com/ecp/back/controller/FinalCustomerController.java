package com.ecp.back.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.FinalCustomer;
import com.ecp.entity.Linkman;
import com.ecp.service.front.IFinalCustomerService;
import com.ecp.service.front.IOrderItemService;
import com.ecp.service.front.IOrderService;
import com.ecp.service.front.IUserAgentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Copyright (c) 2018 by [个人或者公司信息]
 * @ClassName:     FinalCustomerController.java
 * @Description:   (订单)最终用户管理  
 * 
 * @author:        lenovo
 * @version:       V1.0  
 * @Date:          2018年3月24日 上午12:00:53 
 */
@Controller
@RequestMapping("/back/finalcustomer")
public class FinalCustomerController {
	private final Logger log = Logger.getLogger(getClass());
	
	private static final String RESPONSE_THYMELEAF_BACK = "back/thymeleaf/finalcustomer/";
	private static final int PAGE_SIZE = 8;

	

	@Autowired
	IOrderService orderService;  //订单服务
	@Autowired
	IOrderItemService orderItemService; //订单条目
	@Autowired
	IUserAgentService userAgentService; //代理商
	@Autowired
	IFinalCustomerService finalCustomerService; //最终用户
	

	/**
	 * @Description 显示-订单列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/show")
	public String order_show(Model model) {
		return RESPONSE_THYMELEAF_BACK + "order_show";
	}
	
	/**
	 * @Description 订单查询列表
	 * @param orderTimeCond  订单时间条件
	 * @param dealStateCond  订单处理状态条件
	 * @param pageNum		  页号
	 * @param pageSize		 页大小
	 * @param searchTypeValue 搜索类型
	 * @param condValue		  搜索条件值
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ordertable")
	public String order_table(int orderTimeCond,
								int dealStateCond,
								Integer pageNum, 
								Integer pageSize,
								Integer searchTypeValue,
								String condValue,
								Model model) {
		if(pageNum==null || pageNum==0)
		{
			pageNum=1;
			pageSize=PAGE_SIZE;
		}
		
		//置默认值(搜索)
		if(searchTypeValue==null){
			searchTypeValue=0;
			condValue="";
		}
		
		//回传查询条件
		model.addAttribute("orderTimeCond", orderTimeCond);
		model.addAttribute("dealStateCond", dealStateCond);
		
		//搜索条件类型、搜索条件值
		model.addAttribute("searchTypeValue", searchTypeValue);  	//查询字段值
		model.addAttribute("condValue", condValue);  				//查询条件值
		
		// 查询 并分页		
		PageHelper.startPage(pageNum, pageSize); // PageHelper			

		//List<Map<String,Object>> orderList = orderService.selectAllOrderByOrderTimeAndDealState(orderTimeCond,dealStateCond);
		
		List<Map<String,Object>> orderList = orderService.selectOrder(orderTimeCond,dealStateCond,searchTypeValue,condValue);  //查询订单
		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(orderList);// (使用了拦截器或是AOP进行查询的再次处理)
		
		model.addAttribute("pageInfo", pageInfo);  //分页
		model.addAttribute("orderList", orderList); //列表
		
		
		return RESPONSE_THYMELEAF_BACK + "order_table";
	}
	
	/** 
	* @Title: showLinkmanEditUI 
	* @Description: 显示最终用户编辑界面 
	* @param @param orderId 订单自增ID
	* @param @param orderNo 订单NO
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(value="/edit")
	public String showFinalCustomerEditUI(long orderId,String orderNo,Model model){
		
		//查询此订单下的最终用户
		List<FinalCustomer> finalCustomerList=finalCustomerService.getFinalCustomerByOrder(orderId);
		
		//回传参数
		model.addAttribute("finalCustomerList",finalCustomerList);		
		model.addAttribute("orderId", orderId);
		model.addAttribute("orderNo",orderNo);
		
		return RESPONSE_THYMELEAF_BACK + "finalCustomer_edit";
	}
	
	
	@RequestMapping(value="/table")
	public String showFinalCustomerTable(long orderId,String orderNo,Model model){
		
		//查询此订单下的最终用户
		List<FinalCustomer> finalCustomerList=finalCustomerService.getFinalCustomerByOrder(orderId);
		
		//回传参数
		model.addAttribute("finalCustomerList",finalCustomerList);
		
		
		return RESPONSE_THYMELEAF_BACK + "finalCustomer_table";
	}
	
	@RequestMapping(value="/add")
	@ResponseBody
	public Object addFinalCustomer(FinalCustomer finalCustomer, Model model){
		
		//增加最终用户
		int row =finalCustomerService.addFinalCustomer(finalCustomer);
		
		if (row>0){
			return RequestResultUtil.getResultAddSuccess();
		}
		else
			return RequestResultUtil.getResultAddWarn();
	}
	

}
