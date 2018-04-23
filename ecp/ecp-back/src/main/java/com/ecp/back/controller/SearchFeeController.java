package com.ecp.back.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecp.back.commons.RoleCodeConstants;
import com.ecp.bean.AccountItemType;
import com.ecp.bean.DeletedType;
import com.ecp.bean.UserBean;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.AccountCompany;
import com.ecp.entity.AccountPersonal;
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
	* Copyright (c) 2017 by Hz
	* @ClassName:     SearchFeeController.java
	* @Description:   费用查询 
	* 
	* @author:        lenovo
	* @version:       V1.0  
	* @Date:          2018年4月23日 上午12:23:24 
*/
@Controller
@RequestMapping("/back/fee")
public class SearchFeeController {
	private final Logger log = Logger.getLogger(getClass());
	
	private static final String RESPONSE_THYMELEAF_BACK = "back/thymeleaf/searchfee/";
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
	IAgentBindService agentBindService;  //客户绑定服务
	@Autowired
	IUserService userService;  //用户服务
	@Autowired
	IRoleService roleService;  //角色服务
	

	/**
	 * @Description 显示-费用查询页面(选项卡部分)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/show")
	public String searchFeeMain(Model model) {
		return RESPONSE_THYMELEAF_BACK + "searchfee_main";
	}
	
	/** 
		* @Title: loadSearchFeeFrame 
		* @Description: 显示四项费用"查询框架" 
		* @param @param model
		* @param @return     
		* @return String    返回类型 
		* @throws 
	*/
	@RequestMapping(value = "/loadsearch")
	public String loadSearchFeeFrame(Model model) {
		//根据不同的用户查询
		//查询IS/OS用户
		List<Map<String,Object>> userList=getISAndOSUser();
		model.addAttribute("userList", userList);
		
		return RESPONSE_THYMELEAF_BACK + "fee_search";
	}
	
	/** 
		* @Title: searchFourFee 
		* @Description: 查询四项费用并返回列表 
		* @param @param startDateYear
		* @param @param startDateMonth
		* @param @param endDateYear
		* @param @param endDateMonth
		* @param @param userId
		* @param @param model
		* @param @return     
		* @return String    返回类型 
		* @throws 
	*/
	@RequestMapping(value = "/feetable")
	public String searchFourFeeTable(String startDateYear,
								String startDateMonth,
								String endDateYear,
								String endDateMonth,
								long	userId,
								Integer pageNum, 
								Integer pageSize,
								
								Model model) {
		
		searchFourFee(
				 startDateYear,
				 startDateMonth,
				 endDateYear,
				 endDateMonth,
				 userId,
				 pageNum,
				 pageSize,
				 model);
		
		model.addAttribute("startDateYear", startDateYear);
		model.addAttribute("startDateMonth", startDateMonth);
		model.addAttribute("endDateYear", endDateYear);
		model.addAttribute("endDateMonth", endDateMonth);
		model.addAttribute("userId", userId);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageSize", pageSize);
		
		return RESPONSE_THYMELEAF_BACK + "fee_table";
	}
	
	/** 
		* @Title: searchFourFee 
		* @Description: 根据起止日期及用户ID号查询四项费用+其它费用.
		* @param @param startDateYear
		* @param @param startDateMonth
		* @param @param endDateYear
		* @param @param endDateMonth
		* @param @param userId
		* @param @param pageNum
		* @param @param pageSize
		* @param @param model
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private List<Map<String,Object>> searchFourFee(
			String startDateYear,
			String startDateMonth,
			String endDateYear,
			String endDateMonth,
			long	userId,
			Integer pageNum,
			Integer pageSize,
			Model model){
		
		if(pageNum==null || pageNum==0)
		{
			pageNum=1;
			pageSize=PAGE_SIZE;
		}
		
		//费用类型
		List<Integer> itemTypeList=new ArrayList<Integer>();
		itemTypeList.add(AccountItemType.COMMUNICATION_FEE);
		itemTypeList.add(AccountItemType.ENTERTAINMENT_FEE);
		itemTypeList.add(AccountItemType.TRANSPORTATION_FEE);
		itemTypeList.add(AccountItemType.TRAVEL_EXPENSE_FEE);
		itemTypeList.add(AccountItemType.OTHER_FEE);
		
		// 查询 并分页		
		PageHelper.startPage(pageNum, pageSize); // PageHelper
		//查询公司帐薄
		List<Map<String,Object>> accountList=accountCompanyService.getItemsByDateAndUser(startDateYear,
				 startDateMonth,
				 endDateYear,
				 endDateMonth,
				 userId, itemTypeList);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(accountList);// (使用了拦截器或是AOP进行查询的再次处理)
		
		//根据帐薄条目查询费用归属
		List<Map<String,Object>> accountCompanyList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<accountList.size();i++){
			Map<String,Object> accountItem=new HashMap<String,Object>();
			
			
			Long bindUserId=(Long)accountList.get(i).get("bind_user_id");
			//Long roleId=(Long)accountList.get(i).get("role_id");
			
			String bindUserName="";
			//String bindUserRole="";
			
			if(bindUserId==null || bindUserId==0){		
				bindUserName="公司内部";
				//bindUserRole="";
			}
			else{
				bindUserName=userService.selectByPrimaryKey(bindUserId).getUsername();
				//bindUserRole=roleService.selectByPrimaryKey(roleId).getRoleName();
			}
			
			accountItem.put("bindUserName", bindUserName);
			//accountItem.put("bindUserRole", bindUserRole);
			accountItem.put("accountItem", accountList.get(i));
						
			accountCompanyList.add(accountItem);
			
		}
		
		getItemsSum( startDateYear,
				 startDateMonth,
				 endDateYear,
				 endDateMonth,
				 userId,
				 itemTypeList,
				 model);  //
		model.addAttribute("accountCompanyList", accountCompanyList);
		model.addAttribute("pageInfo", pageInfo);  //分页
		
		return accountCompanyList;
	}
	
	//分录条目求和
	private void getItemsSum(String startDateYear,
			String startDateMonth,
			String endDateYear,
			String endDateMonth,
			long	userId,
			List<Integer> itemTypeList,
			Model model
			){
		List<Map<String,Object>> accountSumList=accountCompanyService.getItemsSumByDateAndUser(startDateYear,
				 startDateMonth,
				 endDateYear,
				 endDateMonth,
				 userId, itemTypeList);
		model.addAttribute("amountSum", accountSumList.get(0).get("amountSum"));
		
		List<Map<String,Object>> accountGroupSumList=accountCompanyService.getItemsGroupSumByDateAndUser(startDateYear,
				 startDateMonth,
				 endDateYear,
				 endDateMonth,
				 userId, itemTypeList);
		model.addAttribute("amountGroupSum", accountGroupSumList);
		
	}
	
	
	/** 
		* @Title: loadAddFourfeeDialog 
		* @Description: 加载增加四项费用对话框 
		* @param @param model
		* @param @return     
		* @return String    返回类型 
		* @throws 
	*/
	@RequestMapping(value = "/loadadddialog")
	public String loadAddFourfeeDialog(Model model){
		//查询所有的OS及IS角色的用户(采用ID去重).  //getUsersByRoleCode
		List<Map<String,Object>> userList=getISAndOSUser();
		model.addAttribute("userList", userList);
		
		return RESPONSE_THYMELEAF_BACK + "add_fourfee_dialog"; 
	}
	
	@RequestMapping(value = "/loadmodidialog")
	public String loadModiFourfeeDialog(long accountItemId,Model model){
		//读取当前编辑的分录条目ID.
		AccountCompany accItem=accountCompanyService.selectByPrimaryKey(accountItemId);		
		model.addAttribute("accountItem", accItem);
		
		//查询所有的OS及IS角色的用户(采用ID去重).  //getUsersByRoleCode
		List<Map<String,Object>> userList=getISAndOSUser();
		model.addAttribute("userList", userList);
		
		
		return RESPONSE_THYMELEAF_BACK + "modi_fourfee_dialog"; 
	}
	
	@RequestMapping(value="/modi")
	@ResponseBody
	public Object modiAccountItem(@RequestBody String parms, Model model){
		
		//记入帐薄
		JSONObject parm=JSON.parseObject(parms);
		
		//(1)被修改的公司帐薄分录ID
		long modifiedAccountItemId=parm.getLongValue("accountItemId");
		deleteAccountItem(modifiedAccountItemId);  //删除被修改的分录
		
		//(2)插入新的分录(公司帐薄与个人帐薄)
		//新的费用归属
		long bindUserId=parm.getLongValue("bindUserId");
		
		if(bindUserId==0) {  //内部费用,只记公司帐薄
			long accountItemId =keepAccountCompany(parms);  	//记公司帐薄			
			return RequestResultUtil.getResultAddSuccess();			
			//return RequestResultUtil.getResultAddWarn();
		}
		else{
			long companyItemId =keepAccountCompany(parms);  	//记公司帐薄
			int row2=keepAccountPersonal(parms,companyItemId);	//记个人帐薄
			if (row2>0){
				return RequestResultUtil.getResultAddSuccess();
			}
			else
				return RequestResultUtil.getResultAddWarn();
		}
	}
	
	private List<User> getAllUser(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deleted", 1);//deleted=1:默认（未删除）deleted=2:已删除
		map.put("type", 1);//type=1:默认（后台管理用户）type=2:前端访问用户
		List<User> userList = userService.getList(map);	
		return userList;
	}
	
	private List<Map<String,Object>> getISAndOSUser(){
		List<Map<String,Object>> userList=userService.getISAndOSUser();
		return userList;
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
	/*@RequestMapping(value = "/ordertable")
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
	}*/
	
	
	
	
	
	
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
	/*@RequestMapping(value="/edit")
	public String showFourFeeEditUI(long orderId,String orderNo,Model model){
		
		List<Map<String,Object>> accountCompanyList=searchOrderFourFee(orderId,orderNo);
		
		//查询与此订单相关的企业,而后查询与此企业相关的OS/IS人员列表.		
		//(4)根据企业与OS/IS的绑定关系查询所绑定的客服		
		long agentId=searchAgentByOrder(orderId);		
		List<Map<String,Object>> osList=agentBindService.getSalesByAgentId(agentId, RoleCodeConstants.OS);
		List<Map<String,Object>> isList=agentBindService.getSalesByAgentId(agentId, RoleCodeConstants.IS);		
		
		
		//回传参数
		model.addAttribute("accountCompanyList",accountCompanyList);
		model.addAttribute("osList",osList);
		model.addAttribute("isList",isList);
		
		model.addAttribute("orderId", orderId);
		model.addAttribute("orderNo",orderNo);
		
		return RESPONSE_THYMELEAF_BACK + "fourfee_edit";
	}*/
	
	/** 
	* @Title: searchAgentByOrder 
	* @Description: 根据订单查询下单代理商 
	* @param @param orderId
	* @param @return    设定文件 
	* @return long      如果查询到代理商则返回代理商ID,否则返回0 
	* @throws 
	*/
	/*private long searchAgentByOrder(long orderId){
		//(1)先查询订单
		Orders order=orderService.selectByPrimaryKey(orderId);
		
		//(3)根据主帐号可以查询所在的企业
		UserExtends agent=userAgentService.getUserAgentByUserId(order.getBuyerId());
		long agentId=0;
		if(agent!=null)
			agentId=agent.getExtendId();
		
		return agentId;
	}*/
	
	
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
	/*@RequestMapping(value="/table")
	public String showFourFeeTable(long orderId,String orderNo,Model model){
		
		List<Map<String,Object>> accountCompanyList=searchOrderFourFee(orderId,orderNo);
		
		//回传参数
		model.addAttribute("accountCompanyList",accountCompanyList);	
		
		
		return RESPONSE_THYMELEAF_BACK + "fourfee_table";
	}*/
	
	
	/** 
	* @Title: searchOrderFourFee 
	* @Description: 查询订单的四项费用 
	* @param @param orderId
	* @param @param orderNo
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	/*private List<Map<String,Object>> searchOrderFourFee(long orderId,String orderNo){
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
	}*/
	
	
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
	public Object addAccountItem(@RequestBody String parms, Model model){
		
		//记入帐薄
		JSONObject parm=JSON.parseObject(parms);
		
		//费用归属
		long bindUserId=parm.getLongValue("bindUserId");
		
				
		if(bindUserId==0) {  //内部费用,只记公司帐薄
			long accountItemId =keepAccountCompany(parms);  	//记公司帐薄			
			return RequestResultUtil.getResultAddSuccess();			
			//return RequestResultUtil.getResultAddWarn();
		}
		else{
			long companyItemId =keepAccountCompany(parms);  	//记公司帐薄
			int row2=keepAccountPersonal(parms,companyItemId);	//记个人帐薄
			if (row2>0){
				return RequestResultUtil.getResultAddSuccess();
			}
			else
				return RequestResultUtil.getResultAddWarn();
		}
	}
	
	/** 
		* @Title: deleteAccountItem 
		* @Description: 删除公司帐薄分录条目 
		* @param @param accountItemId  公司帐薄分录ID
		* @param @param model
		* @param @return     
		* @return Object    返回类型 
		* @throws 
	*/
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object deleteAccountItem(long accountItemId, Model model){
		deleteAccountItem(accountItemId);
		
		
		return RequestResultUtil.getResultUpdateSuccess();
	}
	
	private void deleteAccountItem(long accountItemId){
		//自公司帐薄中删除分录条目
		AccountCompany rec1=new AccountCompany();
		rec1.setId(accountItemId);
		rec1.setDeleted((byte)DeletedType.YES);
		accountCompanyService.updateByPrimaryKeySelective(rec1);
		
		//自个人帐薄中删除分录条目(个人分录会引用公司分录)
		accountPersonalService.logicDeleteByCompanyItemId(accountItemId);
	}
	
	
	
	/** 
		* @Title: keepAccountCompany 
		* @Description: 记入公司帐薄 
		* @param @param parms
		* @param @return     
		* @return long   返回公司帐薄分录ID
		* @throws 
	*/
	private long keepAccountCompany(String parms){
		final byte COMPANY_FEE_FLAG_TRUE=1;
		final byte COMPANY_FEE_FLAG_FALSE=0;
		//参数:long orderId,String orderNo,int itemType,BigDecimal amount,String comment,long bindUserId,long roleId
		AccountCompany accountItem =new AccountCompany();
		JSONObject parm=JSON.parseObject(parms);
		
		/*accountItem.setOrderId(parm.getLongValue("orderId"));
		accountItem.setOrderNo(parm.getString("orderNo"));*/
		
		//费用类型,金额,备注
		accountItem.setType(parm.getIntValue("itemType"));  		
		accountItem.setAmount(parm.getBigDecimal("amount"));
		accountItem.setComment(parm.getString("comment"));		
		
		//费用归属人
		long bindUserId=parm.getLongValue("bindUserId");
		accountItem.setBindUserId(parm.getLongValue("bindUserId"));
		accountItem.setRoleId((long)0);
		
		//费用归属期间
		String period=parm.getString("period");
		String[] tempArr=period.split("-");
		String year=tempArr[0];
		String month=tempArr[1];
		accountItem.setYear(year);
		accountItem.setMonth(month);
		
		//记公司帐户时:置为公司内部费用.
		if(bindUserId==0){
			accountItem.setCompanyFeeFlag(COMPANY_FEE_FLAG_TRUE);			
		}
		
		//操作员信息
		UserBean user=getLoginUser();
		accountItem.setOperatorId(user.getId());
		accountItem.setOperatorName(user.getNickname());
		
		
		accountItem.setCreateTime(new Date());
		
		//记入公司帐薄
		int row =accountCompanyService.addAccountItem(accountItem);
		
		return accountItem.getId();
	}
	
	/** 
		* @Title: keepAccountPersonal 
		* @Description: 记入个人帐薄
		* @param @param parms
		* @param @param accountCompanyId 公司帐薄分录引用
		* @param @return     
		* @return int    返回类型 
		* @throws 
	*/
	private int keepAccountPersonal(String parms,long accountCompanyItemId){
		//参数:long orderId,String orderNo,int itemType,BigDecimal amount,String comment,long bindUserId,long roleId
		AccountPersonal accountItem =new AccountPersonal();
		
		JSONObject parm=JSON.parseObject(parms);
		
		//费用类型,金额,备注
		accountItem.setType(parm.getIntValue("itemType"));
		accountItem.setAmount(parm.getBigDecimal("amount"));
		accountItem.setComment(parm.getString("comment"));
		
		//设置引用
		accountItem.setAccountCompanyId(accountCompanyItemId);
		
		//归属用户及角色
		accountItem.setBindUserId(parm.getLongValue("bindUserId"));
		accountItem.setRoleId((long)0);
		
		
		//费用归属期间
		String period=parm.getString("period");
		String[] tempArr=period.split("-");
		String year=tempArr[0];
		String month=tempArr[1];
		accountItem.setYear(year);
		accountItem.setMonth(month);
		
		/*long agentId=searchAgentByOrder(parm.getLongValue("orderId"));
		accountItem.setCustId(agentId);  //代理商ID
*/		
		//操作员信息
		UserBean user=getLoginUser();
		accountItem.setOperatorId(user.getId());
		accountItem.setOperatorName(user.getNickname());
		
		
		accountItem.setCreateTime(new Date());
		
		//记入个人帐薄
		int row =accountPersonalService.addAccountItem(accountItem);
		
		return row;
	}
	
	
	
	
	/** 
	* @Title: getLoginUserId 
	* @Description: 获取登录用户的ID 
	* @param @return     
	* @return long    返回类型 
	* @throws 
	*/
	private UserBean getLoginUser(){
		//取得当前用户角色列表
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		return user;
		
	} 
	
	
	

}
