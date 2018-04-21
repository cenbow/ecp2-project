package com.ecp.back.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ecp.back.commons.RoleCodeConstants;
import com.ecp.bean.AccountItemType;
import com.ecp.bean.UserBean;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.AccountCompany;
import com.ecp.entity.AccountPersonal;
import com.ecp.entity.Orders;
import com.ecp.entity.UserExtends;
import com.ecp.service.front.IAccountCompanyService;
import com.ecp.service.front.IAccountPersonalService;
import com.ecp.service.front.IAgentBindService;
import com.ecp.service.front.IContractItemsService;
import com.ecp.service.front.IOrderItemService;
import com.ecp.service.front.IOrderService;
import com.ecp.service.front.IUserAgentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Copyright (c) 2018 by [个人或者公司信息]
 * @ClassName:     MarketFeeController.java
 * @Description:   市场费用管理 
 * 
 * @author:        lenovo
 * @version:       V1.0  
 * @Date:          2018年3月21日 下午4:42:59 
 */
@Controller
@RequestMapping("/back/marketfee")
public class MarketFeeController {
	private final Logger log = Logger.getLogger(getClass());
	
	private static final String RESPONSE_THYMELEAF_BACK = "back/thymeleaf/marketfee/";
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
	IAgentBindService agentBindService;  //代理商绑定服务
	@Autowired
	IContractItemsService contractItemsService;  //合同详情服务
	

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
		
		List<Map<String,Object>> addedList=getContractAndMarketFeeAmount(orderList);
		
		model.addAttribute("pageInfo", pageInfo);  //分页
		model.addAttribute("orderList", addedList); //列表
		
		
		return RESPONSE_THYMELEAF_BACK + "order_table";
	}
	
	/** 
	* @Title: getContractAndPayAmount 
	* @Description:  获取订单合同金额(应收款)及回款额
	* @param @param orderList
	* @param @return     
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	private List<Map<String,Object>> getContractAndMarketFeeAmount(List<Map<String,Object>> orderList){
		//List<Map<String,Object>> tmpList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<orderList.size();i++){
			//Map<String,Object> compositeObj=new HashMap<String,Object>();
			
			Map<String,Object> order=orderList.get(i);
			//compositeObj.put("order", order);
			
			//根据合同ID查询:合同金额(应收款)
			BigDecimal contractAmount=new BigDecimal(0);  
			if(order.get("contract_id")!=null){
				contractAmount=contractItemsService.getContractAmountByNo((String) order.get("contract_no"));
			}
			order.put("contractAmount", contractAmount);
			
			//根据订单ID:查询收款合计
			BigDecimal payAmount= accountCompanyService.getAmountByOrderId((long) order.get("id"),AccountItemType.MARKET_FEE);			
			order.put("marketFeeAmount", payAmount);
			
			//tmpList.add(compositeObj);
		}
		
		return orderList;
	}
	
	
	
	/** 
	* @Title: showMarketFeeEditUI 
	* @Description: 显示市场费用编辑界面 
	* @param @param id  订单自增ID
	* @param @param orderId 订单NO
	* @param @param model
	* @param @return    设定文件 
	* @return String    市场费用编辑UI
	* @throws 
	*/
	@RequestMapping(value="/edit")
	public String showMarketFeeEditUI(long orderId,String orderNo,Model model){
		prepareMarketFee(orderId,orderNo,model);
		model.addAttribute("orderId", orderId);
		model.addAttribute("orderNo",orderNo);
		
		return RESPONSE_THYMELEAF_BACK + "marketfee_edit";
	}
	
	private void prepareMarketFee(long orderId,String orderNo,Model model){
		//费用类型
		List<Integer> itemTypeList=new ArrayList<>();		
		itemTypeList.add(AccountItemType.MARKET_FEE);  //市场费
		
		//查询公司帐薄
		List<AccountCompany> accountCompanyList=accountCompanyService.getItemsByOrder(orderId, orderNo, itemTypeList);
		
		//回传参数
		model.addAttribute("accountCompanyList",accountCompanyList);		
		//查询合同的记帐状态
		Orders order=orderService.selectByPrimaryKey(orderId);
		model.addAttribute("accountStatus", order.getAccountState());  //订单的记帐状态
		
		//查询与此订单相关的OS/IS
		long agentId=searchAgentByOrder(orderId);
		List<String> roleCodeList=new ArrayList<>();
		roleCodeList.add(RoleCodeConstants.OS);
		roleCodeList.add(RoleCodeConstants.IS);
		List<Map<String,Object>> bindUserList=agentBindService.getSalesByAgentIdAndRoleCodes(agentId, roleCodeList);
		
		model.addAttribute("bindUserList", bindUserList);  //所绑定的用户
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
	
	
	@RequestMapping(value="/table")
	public String showMarketFeeTable(long orderId,String orderNo,Model model){		
		//费用类型
		List<Integer> itemTypeList=new ArrayList<>();
		itemTypeList.add(AccountItemType.MARKET_FEE);  //市场费		
		//查询公司帐薄
		List<AccountCompany> accountCompanyList=accountCompanyService.getItemsByOrder(orderId, orderNo, itemTypeList);
		
		//回传参数
		model.addAttribute("accountCompanyList",accountCompanyList);	
		
		
		return RESPONSE_THYMELEAF_BACK + "marketfee_table";
	}
	
	/** 
		* @Title: addFeeItem 
		* @Description: 增加市场费(Create)
		* @param @param orderId
		* @param @param orderNo
		* @param @param itemType
		* @param @param amount
		* @param @param comment
		* @param @param bindUserList 费用归属列表.(JSON格式)
		* @param @param model
		* @param @return     
		* @return Object    返回类型 
		* @throws 
	*/
	@RequestMapping(value="/add")
	@ResponseBody
	public Object addFeeItem(long orderId,
							 String orderNo,
							 int itemType,
							 BigDecimal amount,
							 String comment,
							 String bindUserList,
							 Model model){
		final byte COMPANY_FEE_FLAG_TRUE=1;  //内部费用
		final byte COMPANY_FEE_FLAG_FALSE=0; //双计费用
		
		log.info("bindUserList:"+bindUserList);
		
		boolean selectedFlag=false;
		//查询是否选择了用户
		JSONArray userArr=JSON.parseArray(bindUserList); //解析参数:获取所要绑定的用户ID 数组.
		for(int i=0;i<userArr.size();i++){
			boolean selected=userArr.getJSONObject(i).getBooleanValue("selected");
			if(selected){
				selectedFlag=true;
				break;
			}
		}
		
		byte companyFeeFlag=COMPANY_FEE_FLAG_TRUE;
		if(selectedFlag){
			companyFeeFlag=COMPANY_FEE_FLAG_FALSE;
		}
		
		//记公司帐薄
		long companyItemId=keepAccountCompany( orderId, orderNo, itemType, amount, comment,companyFeeFlag);  
		
		
		if(selectedFlag){  //如果选择了绑定用户,则计个人帐薄
			for(int i=0;i<userArr.size();i++){
				boolean selected=userArr.getJSONObject(i).getBooleanValue("selected");
				if(selected){
					long userId=userArr.getJSONObject(i).getLongValue("id");
					long roleId=userArr.getJSONObject(i).getLongValue("role_id");
					
					int row=keepAccountPersonal( orderId, orderNo, itemType, amount, comment,userId,roleId,companyItemId);	//记个人帐薄
				}
			}
		}
		
		
		
		return RequestResultUtil.getResultAddSuccess();
		
		//return RequestResultUtil.getResultAddWarn();
	}
	
	/** 
		* @Title: keepAccountCompany 
		* @Description: 记公司帐薄 
		* @param @param orderId
		* @param @param orderNo
		* @param @param itemType
		* @param @param amount
		* @param @param comment
		* @param @return     
		* @return long    返回公司帐薄分录ID 
		* @throws 
	*/
	private long keepAccountCompany(long orderId,String orderNo,int itemType,BigDecimal amount,String comment,byte companyFeeFlag){
		AccountCompany accountItem=new AccountCompany();
		accountItem.setOrderId(orderId);
		accountItem.setOrderNo(orderNo);
		accountItem.setType(itemType);
		accountItem.setAmount(amount);
		accountItem.setComment(comment);
		accountItem.setCreateTime(new Date());
		
		long agentId=searchAgentByOrder(orderId); //代理商ID
		accountItem.setCustId(agentId);  
		
		accountItem.setCompanyFeeFlag(companyFeeFlag);
		
		//绑定用户/角色: 0/0
		accountItem.setBindUserId((long)0);
		accountItem.setRoleId((long)0);
		
		//操作员信息
		UserBean user=getLoginUser();
		accountItem.setOperatorId(user.getId());
		accountItem.setOperatorName(user.getNickname());
		
		//记入公司帐薄
		int row =accountCompanyService.addAccountItem(accountItem);
		return accountItem.getId();  
	}
	
	/** 
		* @Title: keepAccountPersonal 
		* @Description: 记入个人帐户(对绑定的OS/IS进行市场费记帐) 
		* @param @param orderId
		* @param @param orderNo
		* @param @param itemType
		* @param @param amount
		* @param @param comment
		* @param @return     
		* @return int    返回类型 
		* @throws 
	*/
	private int keepAccountPersonal(long orderId,
									String orderNo,
									int itemType,
									BigDecimal amount,
									String comment,
									long userId,
									long roleId,
									long accountCompanyId
									){
		
		//操作员信息
		UserBean user=getLoginUser();
		
		//查询代理商
		long agentId=searchAgentByOrder(orderId);
		
		//加入个人帐薄
		AccountPersonal accountItem=new AccountPersonal();
		accountItem.setOrderId(orderId);
		accountItem.setOrderNo(orderNo);
		accountItem.setType(itemType);
		accountItem.setAmount(amount);
		accountItem.setComment(comment);
		accountItem.setCreateTime(new Date());
		
		//查询代理商
		accountItem.setCustId(agentId);
		
		//写入操作员
		accountItem.setOperatorId(user.getId());
		accountItem.setOperatorName(user.getNickname());
		
		//写入绑定用户信息
		accountItem.setBindUserId(userId);
		accountItem.setRoleId(roleId);
		
		//加入公司帐薄分录ID
		accountItem.setAccountCompanyId(accountCompanyId);
		
		//记入个人帐薄
		int row =accountPersonalService.addAccountItem(accountItem);
		
		return row;
		
		
		/*
		//TODO 如果订单的account_state为特殊状态时的处理.不记入个人帐薄
		//查询与此代理商相绑定的OS/IS
		//记入个人帐薄
		int row=0;
		List<String> roleCodeList=new ArrayList<String>();
		roleCodeList.add(RoleCodeConstants.OS);
		roleCodeList.add(RoleCodeConstants.IS);
		List<Map<String,Object>>  userRoleList=  agentBindService.getSalesByAgentIdAndRoleCodes(agentId,roleCodeList);
		for(int i=0;i<userRoleList.size();i++){
			AccountPersonal accountItem=new AccountPersonal();
			
			accountItem.setOrderId(orderId);
			accountItem.setOrderNo(orderNo);
			accountItem.setType(itemType);
			accountItem.setAmount(amount);
			accountItem.setComment(comment);
			accountItem.setCreateTime(new Date());
			
			//查询代理商
			accountItem.setCustId(agentId);
			
			//写入操作员
			accountItem.setOperatorId(user.getId());
			accountItem.setOperatorName(user.getNickname());
			
			//写入绑定用户信息
			long userId=(long)userRoleList.get(i).get("id");
			long roleId=(long)userRoleList.get(i).get("role_id");
			accountItem.setBindUserId(userId);
			accountItem.setRoleId(roleId);
			
			//记入个人帐薄
			row =accountPersonalService.addAccountItem(accountItem);
		}
		*/
		
		
		
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
