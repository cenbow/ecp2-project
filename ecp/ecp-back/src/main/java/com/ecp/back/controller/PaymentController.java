package com.ecp.back.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecp.bean.AccountItemType;
import com.ecp.bean.UserBean;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.AccountCompany;
import com.ecp.entity.Orders;
import com.ecp.entity.UserExtends;
import com.ecp.service.front.IAccountCompanyService;
import com.ecp.service.front.IAccountPersonalService;
import com.ecp.service.front.IContractItemsService;
import com.ecp.service.front.IOrderItemService;
import com.ecp.service.front.IOrderService;
import com.ecp.service.front.IUserAgentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Copyright (c) 2018 by [个人或者公司信息]
 * @ClassName:     PaymentController.java
 * @Description:   回款管理 
 * 
 * @author:        lenovo
 * @version:       V1.0  
 * @Date:          2018年3月21日 下午4:42:59 
 */
@Controller
@RequestMapping("/back/payment")
public class PaymentController {
	private final Logger log = Logger.getLogger(getClass());
	
	private static final String RESPONSE_THYMELEAF_BACK = "back/thymeleaf/payment/";
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
	@Autowired
	IContractItemsService contractItemsService;  //合同条目
	

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
		//List<Map<String,Object>> orderList = orderService.selectOrder(orderTimeCond,dealStateCond,searchTypeValue,condValue);  //查询订单
		List<Map<String,Object>> orderList = orderService.selectOrders(null,orderTimeCond,dealStateCond,searchTypeValue,condValue);  //查询订单
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(orderList);// (使用了拦截器或是AOP进行查询的再次处理)
		
		model.addAttribute("pageInfo", pageInfo);  //分页
		model.addAttribute("orderList", orderList); //列表
		
		
		return RESPONSE_THYMELEAF_BACK + "order_table";
	}
	
	
	
	/** 
	* @Title: showPaymentFeeEditUI 
	* @Description: 显示回款编辑界面 
	* @param @param id  订单自增ID
	* @param @param orderId 订单NO
	* @param @param model
	* @param @return    设定文件 
	* @return String    回款编辑UI
	* @throws 
	*/
	@RequestMapping(value="/edit")
	public String showPaymentEditUI(long orderId,String orderNo,Model model){
		
		//费用类型
		List<Integer> itemTypeList=new ArrayList<Integer>();		
		itemTypeList.add(AccountItemType.PAYMENT);  //回款
		
		//查询公司帐薄
		List<AccountCompany> accountCompanyList=accountCompanyService.getItemsByOrder(orderId, orderNo, itemTypeList);
		model.addAttribute("accountCompanyList",accountCompanyList);
		
		//回款金额
		BigDecimal payAmount=accountCompanyService.getAmountByOrderId(orderId, AccountItemType.PAYMENT);  //计算此订单下的回款合计
		model.addAttribute("payAmount",payAmount);
		
		
		//查询订单合同金额, 回款合计, 订单欠款
		//根据合同ID查询:合同金额(应收款)
		//合同额
		Orders order=orderService.selectByPrimaryKey(orderId);
		BigDecimal contractAmount=new BigDecimal(0);  
		if(order.getContractNo()!=null){
			contractAmount=contractItemsService.getContractAmountByNo(order.getContractNo());
		}
		model.addAttribute("contractAmount", contractAmount);
		
		
		
		//回传参数
				
		model.addAttribute("orderId", orderId);
		model.addAttribute("orderNo",orderNo);
		
		return RESPONSE_THYMELEAF_BACK + "payment_edit";
	}
	
	
	/** 
		* @Title: showPaymentTable 
		* @Description: 加载订单回款列表 
		* @param @param orderId  主键(自增)
		* @param @param orderNo	 订单号
		* @param @param model
		* @param @return     
		* @return String    返回类型 
		* @throws 
	*/
	@RequestMapping(value="/table")
	public String showPaymentTable(long orderId,String orderNo,Model model){
		
		//费用类型
		List<Integer> itemTypeList=new ArrayList<Integer>();
		itemTypeList.add(AccountItemType.PAYMENT);  //回款
		
		
		//查询公司帐薄
		List<AccountCompany> accountCompanyList=accountCompanyService.getItemsByOrder(orderId, orderNo, itemTypeList);
		model.addAttribute("accountCompanyList",accountCompanyList);

		//回款金额
		BigDecimal payAmount=accountCompanyService.getAmountByOrderId(orderId, AccountItemType.PAYMENT);  //计算此订单下的回款合计
		model.addAttribute("payAmount",payAmount);
		
		
		//查询订单合同金额, 回款合计, 订单欠款
		//根据合同ID查询:合同金额(应收款)
		//合同额
		Orders order=orderService.selectByPrimaryKey(orderId);
		BigDecimal contractAmount=new BigDecimal(0);  
		if(order.getContractNo()!=null){
			contractAmount=contractItemsService.getContractAmountByNo(order.getContractNo());
		}
		model.addAttribute("contractAmount", contractAmount);
		
		
		return RESPONSE_THYMELEAF_BACK + "payment_table";
	}
	
	@RequestMapping(value="/add")
	@ResponseBody
	public Object addPaymentItem(long orderId,String orderNo,int itemType,BigDecimal amount,String comment, Model model){
		
		AccountCompany accountCompanyItem=new AccountCompany();
		accountCompanyItem.setOrderId(orderId);
		accountCompanyItem.setOrderNo(orderNo);
		accountCompanyItem.setType(itemType);
		accountCompanyItem.setAmount(amount);
		accountCompanyItem.setComment(comment);	
		accountCompanyItem.setCreateTime(new Date());
		
		long agentId=searchAgentByOrder(orderId);
		accountCompanyItem.setCustId(agentId);  //代理商ID
		
		//加入userid与roleId均为0;
		accountCompanyItem.setBindUserId((long)0);
		accountCompanyItem.setRoleId((long)0);
		
		//操作员信息
		UserBean user=getLoginUser();
		accountCompanyItem.setOperatorId(user.getId());
		accountCompanyItem.setOperatorName(user.getNickname());
		
		//记入公司帐薄
		int row =accountCompanyService.addAccountItem(accountCompanyItem);
		
		//回款不记个人帐薄
		if (row>0){
			
			updateOrderTotalPayFlag(orderId,orderNo);  //更新订单付全款标志.
			
			return RequestResultUtil.getResultAddSuccess();
		}
		else
			return RequestResultUtil.getResultAddWarn();
	}
	
	
	/** 
		* @Title: updateOrderTotalPayFlag 
		* @Description: 更新订单是否已经全款标志
		* @param @param orderId
		* @param @param orderNo     
		* @return void    返回类型 
		* @throws 
	*/
	private void updateOrderTotalPayFlag(long orderId,String orderNo){
		final byte TOTAL_PAY_FLAG_YES=1;  //全款;
		final byte TOTAL_PAY_FLAG_NO=0;	  //未全款
		//计算此订单下的回款合计
		BigDecimal payAmount=accountCompanyService.getAmountByOrderId(orderId, AccountItemType.PAYMENT);
		
		Orders order=orderService.selectByPrimaryKey(orderId);
		BigDecimal contractAmount=new BigDecimal(0);  
		if(order.getContractNo()!=null){
			contractAmount=contractItemsService.getContractAmountByNo(order.getContractNo());
		}
		
		//如果合同金额>0
		if (contractAmount.compareTo(new BigDecimal(0))==1){
			if(payAmount.compareTo(contractAmount)==0 || payAmount.compareTo(contractAmount)==1)
			{
				Orders rec=new Orders();
				rec.setId(orderId);
				rec.setTotalPayFlag(TOTAL_PAY_FLAG_YES);
				orderService.updateByPrimaryKeySelective(rec);
			}
		}
		
	}
	
	/** 
	* @Title: searchAgentByOrder 
	* @Description: 根据订单查询下单代理商 
	* @param @param orderId
	* @param @return    设定文件 
	* @return long      如果查询到代理商则返回代理商ID,否则返回0 
	* @throws 
	*/
	private long searchAgentByOrder(long orderId){
		//(1)先查询订单
		Orders order=orderService.selectByPrimaryKey(orderId);
		
		//(2)根据主帐号可以查询所在的企业
		UserExtends agent=userAgentService.getUserAgentByUserId(order.getBuyerId());
		long agentId=0;
		if(agent!=null)
			agentId=agent.getExtendId();
		
		return agentId;
	}
	
	/** 
	* @Title: getLoginUserId 
	* @Description: 获取登录用户的ID 
	* 				可取得当前登录用户的角色列表
	* @param @return     
	* @return long    返回类型 
	* @throws 
	*/
	private UserBean getLoginUser(){		
		Subject subject = SecurityUtils.getSubject(); 
		UserBean user = (UserBean)subject.getPrincipal();
		return user;
	} 
	

}
