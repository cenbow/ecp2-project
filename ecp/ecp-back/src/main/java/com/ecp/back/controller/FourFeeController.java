package com.ecp.back.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecp.bean.AccountItemType;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.AccountCompany;
import com.ecp.entity.Orders;
import com.ecp.entity.UserExtends;
import com.ecp.service.front.IAccountCompanyService;
import com.ecp.service.front.IAccountPersonalService;
import com.ecp.service.front.IOrderItemService;
import com.ecp.service.front.IOrderService;
import com.ecp.service.front.IUserAgentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Copyright (c) 2018 by [个人或者公司信息]
 * @ClassName:     FourFeeController.java
 * @Description:   四项费用管理 
 * 
 * @author:        lenovo
 * @version:       V1.0  
 * @Date:          2018年3月21日 下午4:42:59 
 */
@Controller
@RequestMapping("/back/fourfee")
public class FourFeeController {
	private final Logger log = Logger.getLogger(getClass());
	
	private static final String RESPONSE_THYMELEAF_BACK = "back/thymeleaf/fourfee/";
	private static final int PAGE_SIZE = 8;

	

	@Autowired
	IOrderService orderService;  //订单服务
	@Autowired
	IOrderItemService orderItemService; //订单条目
	@Autowired
	IUserAgentService userAgentService; //代理商
	@Autowired
	IAccountCompanyService accountCompanyService; //公司帐户
	@Autowired
	IAccountPersonalService accountPersonalService;  //个人帐户
	

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
	 * @Description 订单查询
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
	public String order_table(int orderTimeCond,int dealStateCond,Integer pageNum, Integer pageSize,Integer searchTypeValue,String condValue,Model model) {
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
	 * @Description 订单详情（后台）
	 * @param id  订单主键（pk）
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public String order_detail(Long id,Model model,HttpServletRequest request){
		
		model.addAttribute("orderId", id);  //向订单详细table传递参数
		
		return RESPONSE_THYMELEAF_BACK + "order_detail";
	}
	
	/**
	 * @Description 合同详情（后台）
	 * @param id 合同id（pk）
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/contractdetail")
	public String order_contract_detail(Long id,Model model,HttpServletRequest request){
		
		model.addAttribute("contractId", id);
		
		return RESPONSE_THYMELEAF_BACK + "contract_detail";
	}
	
	/**
	 * @Description 订单详情模块页
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detailtable")
	public String order_detail_table(Long id,Model model,HttpServletRequest request){
		//return "forward:"+RESPONSE_FRONT + "order/"+"detailtable";
		//(1)读取订单
		Orders order=orderService.selectByPrimaryKey(id);		
		//(2)读取订单商品列表
		List<Map<String, String>> orderItems = orderItemService.selectItemsByOrderId(order.getOrderId());
		//(3)收货人信息(此信息已经保存至订单中)		
		//(4)代理商信息
		//UserExtends agent=userAgentService.selectByPrimaryKey(order.getBuyerId());
		UserExtends agent=userAgentService.getUserAgentByUserId(order.getBuyerId());
		
		model.addAttribute("order", order);
		model.addAttribute("orderItems", orderItems);
		model.addAttribute("agent", agent);
		
		return RESPONSE_THYMELEAF_BACK + "order_detail_table";
	}
	
	/** 
	* @Title: showFourFeeEditUI 
	* @Description: 显示四项费用编辑界面 
	* @param @param id  订单自增ID
	* @param @param orderId 订单NO
	* @param @param model
	* @param @return    设定文件 
	* @return String    四项费用编辑UI
	* @throws 
	*/
	@RequestMapping(value="/edit")
	public String showFourFeeEditUI(long orderId,String orderNo,Model model){
		
		//费用类型
		List<Integer> itemTypeList=new ArrayList<Integer>();
		itemTypeList.add(AccountItemType.COMMUNICATION_FEE);
		itemTypeList.add(AccountItemType.ENTERTAINMENT_FEE);
		itemTypeList.add(AccountItemType.TRANSPORTATION_FEE);
		itemTypeList.add(AccountItemType.TRAVEL_EXPENSE_FEE);
		
		//查询公司帐薄
		List<AccountCompany> accountCompanyList=accountCompanyService.getItemsByOrder(orderId, orderNo, itemTypeList);
		
		//回传参数
		model.addAttribute("accountCompanyList",accountCompanyList);		
		model.addAttribute("orderId", orderId);
		model.addAttribute("orderNo",orderNo);
		
		return RESPONSE_THYMELEAF_BACK + "fourfee_edit";
	}
	
	
	@RequestMapping(value="/table")
	public String showFourFeeTable(long orderId,String orderNo,Model model){
		
		//费用类型
		List<Integer> itemTypeList=new ArrayList<Integer>();
		itemTypeList.add(AccountItemType.COMMUNICATION_FEE);
		itemTypeList.add(AccountItemType.ENTERTAINMENT_FEE);
		itemTypeList.add(AccountItemType.TRANSPORTATION_FEE);
		itemTypeList.add(AccountItemType.TRAVEL_EXPENSE_FEE);
		
		//查询公司帐薄
		List<AccountCompany> accountCompanyList=accountCompanyService.getItemsByOrder(orderId, orderNo, itemTypeList);
		
		//回传参数
		model.addAttribute("accountCompanyList",accountCompanyList);	
		
		
		return RESPONSE_THYMELEAF_BACK + "fourfee_table";
	}
	
	
	
	
	
	
	@RequestMapping(value="/add")
	@ResponseBody
	public Object addFeeItem(long orderId,String orderNo,int itemType,BigDecimal amount, Model model){
		
		int row =accountCompanyService.addAccountItem(orderId, orderNo, itemType, amount);
		
		if (row>0){
			return RequestResultUtil.getResultAddSuccess();
		}
		else
			return RequestResultUtil.getResultAddWarn();
		
		
	}
	
	
	

}
