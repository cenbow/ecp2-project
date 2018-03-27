package com.ecp.back.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecp.bean.AccountItemType;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.AccountCompany;
import com.ecp.entity.Orders;
import com.ecp.entity.User;
import com.ecp.entity.UserExtends;
import com.ecp.service.back.IRoleService;
import com.ecp.service.back.IUserService;
import com.ecp.service.front.IAccountCompanyService;
import com.ecp.service.front.IAccountPersonalService;
import com.ecp.service.front.IAgentBindService;
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
	
	private static final String OUTSIDE_ROLE="Outside Sales";
	private static final String INSIDE_ROLE="Inside Sales";

	

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
	IAgentBindService agentBindService;  //客户绑定服务
	@Autowired
	IUserService userService;  //用户服务
	@Autowired
	IRoleService roleService;  //角色服务
	

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
	 * @param orderTimeCond   订单时间条件
	 * @param dealStateCond   订单处理状态条件
	 * @param pageNum		    页号
	 * @param pageSize		    页大小
	 * @param searchTypeValue 搜索类型
	 * @param condValue		    搜索条件值
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
		
		List<Map<String,Object>> accountCompanyList=searchOrderFourFee(orderId,orderNo);
		
		//查询与此订单相关的企业,而后查询与此企业相关的OS/IS人员列表.		
		//(4)根据企业与OS/IS的绑定关系查询所绑定的客服		
		long agentId=searchAgentByOrder(orderId);		
		List<Map<String,Object>> osList=agentBindService.getSalesByAgentId(agentId, OUTSIDE_ROLE);
		List<Map<String,Object>> isList=agentBindService.getSalesByAgentId(agentId, INSIDE_ROLE);		
		
		
		//回传参数
		model.addAttribute("accountCompanyList",accountCompanyList);
		model.addAttribute("osList",osList);
		model.addAttribute("isList",isList);
		
		model.addAttribute("orderId", orderId);
		model.addAttribute("orderNo",orderNo);
		
		return RESPONSE_THYMELEAF_BACK + "fourfee_edit";
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
		
		//(3)根据主帐号可以查询所在的企业
		UserExtends agent=userAgentService.getUserAgentByUserId(order.getBuyerId());
		long agentId=0;
		if(agent!=null)
			agentId=agent.getExtendId();
		
		return agentId;
	}
	
	
	/** 
	* @Title: showFourFeeTable 
	* @Description: 显示四项费用列表 
	* @param @param orderId
	* @param @param orderNo
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(value="/table")
	public String showFourFeeTable(long orderId,String orderNo,Model model){
		
		List<Map<String,Object>> accountCompanyList=searchOrderFourFee(orderId,orderNo);
		
		//回传参数
		model.addAttribute("accountCompanyList",accountCompanyList);	
		
		
		return RESPONSE_THYMELEAF_BACK + "fourfee_table";
	}
	
	
	/** 
	* @Title: searchOrderFourFee 
	* @Description: 查询订单的四项费用 
	* @param @param orderId
	* @param @param orderNo
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	private List<Map<String,Object>> searchOrderFourFee(long orderId,String orderNo){
		//费用类型
		List<Integer> itemTypeList=new ArrayList<Integer>();
		itemTypeList.add(AccountItemType.COMMUNICATION_FEE);
		itemTypeList.add(AccountItemType.ENTERTAINMENT_FEE);
		itemTypeList.add(AccountItemType.TRANSPORTATION_FEE);
		itemTypeList.add(AccountItemType.TRAVEL_EXPENSE_FEE);
		
		//查询公司帐薄
		List<AccountCompany> accountList=accountCompanyService.getItemsByOrder(orderId, orderNo, itemTypeList);
		
		//根据帐薄条目查询费用归属
		List<Map<String,Object>> accountCompanyList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<accountList.size();i++){
			Map<String,Object> accountItem=new HashMap<String,Object>();
			
			
			Long bindUserId=accountList.get(i).getBindUserId();
			Long roleId=accountList.get(i).getRoleId();
			
			String bindUserName="";
			String bindUserRole="";
			
			if(bindUserId==null || bindUserId==0){		
				bindUserName="公司内部";
				bindUserRole="";
			}
			else{
				bindUserName=userService.selectByPrimaryKey(bindUserId).getUsername();
				bindUserRole=roleService.selectByPrimaryKey(roleId).getRoleName();
				
			}
			
			accountItem.put("bindUserName", bindUserName);
			accountItem.put("bindUserRole", bindUserRole);
			accountItem.put("accountItem", accountList.get(i));
						
			accountCompanyList.add(accountItem);
			
		}
		
		return accountCompanyList;
	}
	
	
	/** 
	* @Title: addFeeItem 
	* @Description: 增加费用条目记录 
	* @param @param parms
	* @param @param model
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws 
	*/
	@RequestMapping(value="/add")
	@ResponseBody
	public Object addFeeItem(@RequestBody String parms, Model model){		
		//参数:long orderId,String orderNo,int itemType,BigDecimal amount,String comment,long bindUserId,long roleId
		
		AccountCompany accountCompanyItem =new AccountCompany();
		JSONObject parm=JSON.parseObject(parms);
		accountCompanyItem.setOrderId(parm.getLongValue("orderId"));
		accountCompanyItem.setOrderNo(parm.getString("orderNo"));
		accountCompanyItem.setType(parm.getIntValue("itemType"));
		accountCompanyItem.setAmount(parm.getBigDecimal("amount"));
		accountCompanyItem.setComment(parm.getString("comment"));
		accountCompanyItem.setBindUserId(parm.getLongValue("bindUserId"));
		accountCompanyItem.setRoleId(parm.getLongValue("roleId"));
		
		accountCompanyItem.setCreateTime(new Date());
		
		//记入公司帐薄
		int row =accountCompanyService.addAccountItem(accountCompanyItem);
		
		//TODO 记入个人帐薄
		
		if (row>0){
			return RequestResultUtil.getResultAddSuccess();
		}
		else
			return RequestResultUtil.getResultAddWarn();
		
		
	}
	
	
	

}
