package com.ecp.back.controller;

import java.util.ArrayList;
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

import com.ecp.back.commons.RoleCodeConstants;
import com.ecp.bean.AccountItemType;
import com.ecp.bean.UserBean;
import com.ecp.entity.AccountCompany;
import com.ecp.entity.AccountPersonal;
import com.ecp.entity.Orders;
import com.ecp.entity.Role;
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
	* @Date:          2018年3月27日 上午10:09:26 
*/
@Controller
@RequestMapping("/back/searchorder")
public class SearchOrderController {
	private final Logger log = Logger.getLogger(getClass());
	
	private static final String RESPONSE_THYMELEAF_BACK = "back/thymeleaf/searchorder/";
	private static final int PAGE_SIZE = 8;
	private static final String PERSPECTIVE_TYPE_COMPANY="company";
	private static final String PERSPECTIVE_TYPE_PERSONAL="personal";
	private static final int PERSPECTIVE_VALUE_COMPANY=1;
	private static final int PERSPECTIVE_VALUE_PERSONAL=2;
	
	private static final byte COMPANY_FEE_FLAG_TRUE=1;
	private static final byte COMPANY_FEE_FLAG_FALSE=0;
	
	
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
		* @Title: order_table 
		* @Description: 查询订单,并返回订单列表 
		* @param @param orderTimeCond 	订单时间
		* @param @param dealStateCond 	订单处理状态
		* @param @param pageNum			页号
		* @param @param pageSize		页大小
		* @param @param searchTypeValue 搜索类型
		* @param @param condValue		搜索条件值
		* @param @param provinceName	省份名称
		* @param @param cityName		城市名称
		* @param @param countyName		区(县)名称
		* @param @param userId			用户ID
		* @param @param roleId			角色ID
		* @param @param perspectiveType	视角类型
		* @param @param perspectiveValie视角值
		* @param @param model
		* @param @return     
		* @return String    返回类型 
		* @throws 
	*/
	@RequestMapping(value = "/ordertable")
	public String order_table(int orderTimeCond,
							  int dealStateCond,
							  Integer pageNum, 
							  Integer pageSize,
							  Integer searchTypeValue,
							  String condValue,							  
							  String provinceName,
							  String cityName,
							  String countyName,
							  long userId,
							  long roleId,
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
		
		List<Map<String,Object>> agentIdList=getSearchScope(userId,roleId);  //确认登录用户所查询的代理商范围
		// 分页:Start		
		PageHelper.startPage(pageNum, pageSize); // PageHelper
		

		//确定用户的查询范围(代理商范围)						
		List<Map<String,Object>> orderList = orderService.selectOrder(orderTimeCond,
											 dealStateCond,
											 searchTypeValue,condValue,
											 provinceName,cityName,countyName,
											 agentIdList);  //查询订单
		
		//分页:End		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(orderList);// (使用了拦截器或是AOP进行查询的再次处理)
		
		List<Map<String,Object>> userRoleList=getUserRoles();
		model.addAttribute("userRoleList", userRoleList);  //查询用户角色列表
		
		model.addAttribute("pageInfo", pageInfo);  //分页		
		model.addAttribute("orderList", orderList); //列表
		
		//回传区域条件及用户/角色
		model.addAttribute("provinceName", provinceName);
		model.addAttribute("cityName", cityName);
		model.addAttribute("countyName", countyName);
		model.addAttribute("userId", userId);
		model.addAttribute("roleId", roleId);
				
		return RESPONSE_THYMELEAF_BACK + "order_table";
	}
	
	/** 
		* @Title: getSearchScope 
		* @Description: 获取指定用户的查询范围 
		* @param @param userId
		* @param @param roleId
		* @param @return  如果返回null,则为查询所有   
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private List<Map<String,Object>> getSearchScope(long userId,long roleId){
		//确定用户的查询范围(代理商范围)
		List<Map<String,Object>> agentIdList=null;
		if(userId!=0 && roleId!=0)  //选择了某个用户
			agentIdList=agentBindService.getAgentIdListByBindedUser(userId,roleId);
		else{
			//(1)根据登录用户的角色判定是否查询所有.如果是ADMIN/经理,则查询所有
			boolean searchAll=needSearchAll();
			if(!searchAll){
				long loginUserId=getLoginUserId();
				agentIdList=agentBindService.getAgentIdListByBindedUser(loginUserId);
			}			
		}
		return agentIdList;
	}
	
	//
	/** 
		* @Title: getUserRoles 
		* @Description: 查询当前登录用户:可以查询的用户及角色.
		* 				如果是OS/IS登录,则只是自己的角色及用户名称(可能有多个,如某人可能即是OS也是IS);
		* 				如果是经理级别则显示所有的OS/IS用户名及角色名 
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private List<Map<String,Object>>  getUserRoles(){
		//取得当前用户角色列表
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		List<Role> roleList=user.getRoleList();
		
		//查询是否为经理级别
		for(int i=0;i<roleList.size();i++){
			Role role=roleList.get(i);
			if(role.getRoleCode()==null || role.getRoleCode().equals("")){
				continue;
			}
			switch(role.getRoleCode()){
			case RoleCodeConstants.ADMIN:
			case RoleCodeConstants.MANAGER:
			case RoleCodeConstants.BUSSMAN:
			case RoleCodeConstants.SALEMAN:	
				//查询所有的OS/IS列表
				List<String> parms= new ArrayList<String>();
				parms.add(RoleCodeConstants.OS);
				parms.add(RoleCodeConstants.IS);
				return agentBindService.getUsersByRoleCode(parms);
			default:
				break;				
			}
		}
		
		//查询是否为OS/IS级别
		//查询是否为经理级别
		for(int i=0;i<roleList.size();i++){
			Role role=roleList.get(i);
			if(role.getRoleCode()==null || role.getRoleCode().equals("")){
				continue;
			}
			
			switch(role.getRoleCode()){
			case RoleCodeConstants.OS:
			case RoleCodeConstants.IS:
			{
				//查询此用户所对应的OS/IS角色				
				List<String> parms= new ArrayList<String>();
				parms.add(RoleCodeConstants.OS);
				parms.add(RoleCodeConstants.IS);
				return agentBindService.getUsersByUserIdAndRoleCode(user.getId(), parms);
			}
			default:
				break;
			}
		}
		
		return new ArrayList<Map<String,Object>>();
		
	}
	
	
	/** 
		* @Title: needSearchAll 
		* @Description: 根据登录用户的角色查询:是否查询所有
		* @param @return     
		* @return boolean    返回类型 
		* @throws 
	*/
	private boolean needSearchAll(){
		//取得当前用户角色列表
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		List<Role> roleList=user.getRoleList();
		
		//查询是否为经理级别
		for(int i=0;i<roleList.size();i++){
			Role role=roleList.get(i);
			if(role.getRoleCode()==null || role.getRoleCode().equals("")){
				continue;
			}
			switch(role.getRoleCode()){
			case RoleCodeConstants.ADMIN:
			case RoleCodeConstants.MANAGER:
			case RoleCodeConstants.BUSSMAN:
			case RoleCodeConstants.SALEMAN:				
				return true;
			default:
				break;				
			}
		}
		
		//查询是否为OS/IS级别
		//查询是否为经理级别
		for(int i=0;i<roleList.size();i++){
			Role role=roleList.get(i);
			if(role.getRoleCode()==null || role.getRoleCode().equals("")){
				continue;
			}
			
			switch(role.getRoleCode()){
			case RoleCodeConstants.OS:
			case RoleCodeConstants.IS:
			{
				return false;
			}
			default:
				break;
			}
		}
		
		return false;	
		
	}
	
	/** 
		* @Title: getLoginUserId 
		* @Description: 获取登录用户的ID 
		* @param @return     
		* @return long    返回类型 
		* @throws 
	*/
	private long getLoginUserId(){
		//取得当前用户角色列表
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		return user.getId();
	}
	
	
	/** 
		* @Title: showFee 
		* @Description: 显示指定定单的费用列表 
		* @param @param orderId 订单自增ID
		* @param @param orderNo 订单NO
		* @param @param model
		* @param @return     
		* @return String    返回类型 
		* @throws 
	*/
	@RequestMapping(value="/showfee")
	public String showOrderFee(long orderId,
								String orderNo,
								long userId,
								long roleId,
								String perspectiveType,
								int perspectiveValue, 
								Model model){
		
		if(perspectiveValue==PERSPECTIVE_VALUE_COMPANY){  //公司视角
			
			List<Map<String,Object>> accountCompanyList=searchOrderBindFeeCompany(orderId,orderNo,userId,roleId);  //查询绑定的费用
			model.addAttribute("accountItemList",accountCompanyList);
			
			if(userId!=0 && roleId!=0){ //特定用户角色				
				List<Map<String,Object>> accountCompanyMarketList=searchOrderUnbindFeeCompany(orderId,orderNo,userId,roleId);
				model.addAttribute("marketFeeList", accountCompanyMarketList);
			}
			
		}
		else{  //个人视角
			List<Map<String,Object>> accountPersonalList=searchOrderFeePersonal(orderId,orderNo,userId,roleId);
			model.addAttribute("accountItemList",accountPersonalList);
		}			
				
		model.addAttribute("orderId", orderId);
		model.addAttribute("orderNo",orderNo);
		
		return RESPONSE_THYMELEAF_BACK + "fee_show";
	}
	
	
	
	/** 
		* @Title: showAllFeeUI 
		* @Description: 查询指定条件的费用并返回列表视图
		* @param @param orderTimeCond
		* @param @param dealStateCond
		* @param @param pageNum
		* @param @param pageSize
		* @param @param searchTypeValue
		* @param @param condValue
		* @param @param provinceName
		* @param @param cityName
		* @param @param countyName
		* @param @param userId
		* @param @param roleId
		* @param @param model
		* @param @return     
		* @return String    返回类型 
		* @throws 
	*/
	@RequestMapping(value="/showallfee")
	public String showAllFeeUI(int orderTimeCond,
							  int dealStateCond,
							  int pageNum, 
							  int pageSize,
							  Integer searchTypeValue,
							  String condValue,							  
							  String provinceName,
							  String cityName,
							  String countyName,
							  long userId,
							  long roleId,
							  String perspectiveType,
							  int	perspectiveValue,
							  Model model){
		int searchType=0;
		String condStr="";
		
		//置默认值(搜索)
		if(searchTypeValue!=null){
			searchType=searchTypeValue;
			condStr=condValue;
		}
		
		if (perspectiveValue == PERSPECTIVE_VALUE_COMPANY) { // 公司视角
			List<Map<String, Object>> accountCompanyList = searchScopeFeeCompany( orderTimeCond,
					   dealStateCond,
					   pageNum, 
					   pageSize,
					   searchType,
					   condStr,							  
					   provinceName,
					   cityName,
					   countyName,
					   userId,
					   roleId);
			model.addAttribute("accountItemList", accountCompanyList);

			if (userId != 0 && roleId != 0) {  //特定用户
				List<Map<String, Object>> accountCompanyMarketList = searchScopeUnbindFeeCompany( orderTimeCond,
						   dealStateCond,
						   pageNum, 
						   pageSize,
						   searchType,
						   condStr,							  
						   provinceName,
						   cityName,
						   countyName,
						   userId,
						   roleId);
				model.addAttribute("marketFeeList", accountCompanyMarketList);
			}

		} else { // 个人视角
			List<Map<String, Object>> accountPersonalList = searchScopeFeePersonal( orderTimeCond,
					   dealStateCond,
					   pageNum, 
					   pageSize,
					   searchType,
					   condStr,							  
					   provinceName,
					   cityName,
					   countyName,
					   userId,
					   roleId);
			model.addAttribute("accountItemList", accountPersonalList);
		}
		
		return RESPONSE_THYMELEAF_BACK + "fee_all_show";
	}
	
	
	/** 
		* @Title: searchScopeFeeCompany 
		* @Description: 公司视角:查询费用-范围 
		* @param @param orderTimeCond
		* @param @param dealStateCond
		* @param @param pageNum
		* @param @param pageSize
		* @param @param searchTypeValue
		* @param @param condValue
		* @param @param provinceName
		* @param @param cityName
		* @param @param countyName
		* @param @param userId
		* @param @param roleId
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private List<Map<String,Object>> searchScopeFeeCompany(int orderTimeCond,
														  int dealStateCond,
														  int pageNum, 
														  int pageSize,
														  Integer searchTypeValue,
														  String condValue,							  
														  String provinceName,
														  String cityName,
														  String countyName,
														  long userId,
														  long roleId){
		int searchType=0;
		String condStr="";
		
		//置默认值(搜索)
		if(searchTypeValue!=null){
			searchType=searchTypeValue;
			condStr=condValue;
		}
		
		//(1)准备查询条件
		List<Integer> itemTypeList=getItemTypeList();  //费用类型列表:四项费用,市场费用

		List<Long> roleIdList=new ArrayList<Long>();  		//角色列表		
		if(userId!=0 && roleId!=0){ //特定角色
			roleIdList.add(roleId);
		}
		else{
			userId=-1;  //置userId条件无效.
		}
		
		//确定用户的查询范围(代理商范围)
		List<Map<String,Object>> agentIdList=getSearchScope(userId,roleId);
		
		//查询并分页:开始		
		//PageHelper.startPage(pageNum, pageSize); // PageHelper
		
		//询公司帐薄(四项费用)
		List<AccountCompany> accountList=accountCompanyService.selectItems(
																orderTimeCond,
																dealStateCond,
																searchType,
																condStr,
																provinceName,cityName,countyName,
																agentIdList,														
																itemTypeList,
																userId,
																roleIdList,-1);
		//PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(orderList);// (使用了拦截器或是AOP进行查询的再次处理) 查询分页:结束
		
		//根据帐薄条目查询费用归属
		List<Map<String,Object>> accountCompanyList=addFeeBelongCompany(accountList);
		
		return accountCompanyList;
	}
	
	/** 
		* @Title: addFeeBelongCompany 
		* @Description: 增加费用归属信息 
		* @param @param accountList
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private List<Map<String,Object>> addFeeBelongCompany(List<AccountCompany> accountList){
		//根据帐薄条目查询费用归属
		List<Map<String,Object>> accountCompanyList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<accountList.size();i++){
			Map<String,Object> accountItem=new HashMap<String,Object>();
			
			
			Long bindUserId=accountList.get(i).getBindUserId();
			Long bindRoleId=accountList.get(i).getRoleId();
			
			String bindUserName="";
			String bindUserRole="";
			
			if(bindUserId==null || bindUserId==0){
				if(accountList.get(i).getCompanyFeeFlag()==COMPANY_FEE_FLAG_TRUE){  //如果是计公司内部费用时
					bindUserName="公司内部";
					bindUserRole="不计OS/IS费用";
				}
				else{
					bindUserName="双计";
					bindUserRole="计OS/IS费用";
				}
			}
			else{
				bindUserName=userService.selectByPrimaryKey(bindUserId).getUsername();
				bindUserRole=roleService.selectByPrimaryKey(bindRoleId).getRoleName();
				
			}
			
			accountItem.put("bindUserName", bindUserName);
			accountItem.put("bindUserRole", bindUserRole);
			accountItem.put("accountItem", accountList.get(i));
						
			accountCompanyList.add(accountItem);
			
		}
		return accountCompanyList;
	}
	
	
	/** 
		* @Title: searchScopeMarketFeeCompany 
		* @Description: 公司视角-查询市场费-范围 
		* @param @param orderTimeCond
		* @param @param dealStateCond
		* @param @param pageNum
		* @param @param pageSize
		* @param @param searchTypeValue
		* @param @param condValue
		* @param @param provinceName
		* @param @param cityName
		* @param @param countyName
		* @param @param userId
		* @param @param roleId
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private List<Map<String,Object>> searchScopeUnbindFeeCompany(int orderTimeCond,
																  int dealStateCond,
																  int pageNum, 
																  int pageSize,
																  Integer searchTypeValue,
																  String condValue,							  
																  String provinceName,
																  String cityName,
																  String countyName,
																  long userId,
																  long roleId){
		
		int searchType=0;
		String condStr="";
		
		//置默认值(搜索)
		if(searchTypeValue!=null){
			searchType=searchTypeValue;
			condStr=condValue;
		}
		
		//(1)准备查询条件  费用类型列表:市场费用
		List<Integer> itemTypeList=getItemTypeList();		

		List<Long> roleIdList=null;  	//角色列表列表为空:此条件无效
		long unbindUserId=0;
		
		
		//确定用户的查询范围(代理商范围)
		List<Map<String,Object>> agentIdList=getSearchScope(userId,roleId);
		
		//查询并分页:开始		
		//PageHelper.startPage(pageNum, pageSize); // PageHelper
		//询公司帐薄
		List<AccountCompany> accountList=accountCompanyService.selectItems(
																orderTimeCond,
																dealStateCond,
																searchType,
																condStr,
																provinceName,cityName,countyName,
																agentIdList,														
																itemTypeList,
																unbindUserId,
																roleIdList,-1);
		//PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(orderList);// (使用了拦截器或是AOP进行查询的再次处理) 查询分页:结束
		
		//根据帐薄条目查询费用归属
		List<Map<String,Object>> accountCompanyList=this.addFeeBelongCompany(accountList);
		
		return accountCompanyList;
	}
	
	
	/** 
		* @Title: searchScopeFeePersonal 
		* @Description: 个人视角:查询费用-范围
		* @param @param orderTimeCond
		* @param @param dealStateCond
		* @param @param pageNum
		* @param @param pageSize
		* @param @param searchTypeValue
		* @param @param condValue
		* @param @param provinceName
		* @param @param cityName
		* @param @param countyName
		* @param @param userId
		* @param @param roleId
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private List<Map<String,Object>> searchScopeFeePersonal(int orderTimeCond,
			  int dealStateCond,
			  int pageNum, 
			  int pageSize,
			  Integer searchTypeValue,
			  String condValue,							  
			  String provinceName,
			  String cityName,
			  String countyName,
			  long userId,
			  long roleId){
		
		int searchType=0;
		String condStr="";
		
		//置默认值(搜索)
		if(searchTypeValue!=null){
			searchType=searchTypeValue;
			condStr=condValue;
		}
		
		//(1)准备查询条件 费用类型列表:四项费用,市场费用
		List<Integer> itemTypeList=getItemTypeList();		

		Long bindedUserId=userId;		
		List<Long> roleIdList=new ArrayList<Long>();  //查询登录用户的角色列表		
		if(userId==0 && roleId==0){
			//如果登录的是admin
			boolean searchAll=needSearchAll();
			if(!searchAll){
				bindedUserId=getLoginUserId();
				List<Map<String,Object>> userRoleList=getUserRoles();
				for(int i=0;i<userRoleList.size();i++){
					roleIdList.add((Long)userRoleList.get(i).get("role_id"));
				}
			}
		}
		else{
			roleIdList.add(roleId);
		}
		
		//确定用户的查询范围(代理商范围)
		List<Map<String,Object>> agentIdList=getSearchScope(userId,roleId);
		
		//查询并分页:开始		
		//PageHelper.startPage(pageNum, pageSize); // PageHelper
		//询公司帐薄
		List<AccountPersonal> accountList=accountPersonalService.selectItems(
																orderTimeCond,
																dealStateCond,
																searchType,
																condStr,
																provinceName,cityName,countyName,
																agentIdList,														
																itemTypeList,
																bindedUserId,
																roleIdList);
		//PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(orderList);// (使用了拦截器或是AOP进行查询的再次处理) 查询分页:结束
		
		//根据帐薄条目查询费用归属
		List<Map<String,Object>> accountCompanyList=addFeeBelongPersonal(accountList);
		
		return accountCompanyList;
		
	}
	
	/** 
	* @Title: searchAgentByOrder 
	* @Description: 根据订单查询下单代理商 
	* @param @param orderId
	* @param @return    设定文件 
	* @return long    返回类型 
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
	* @Title: searchOrderFourFee 
	* @Description: 查询订单绑定的费用
	* 				当是个人查询费用时,只查询自己的费用(可能多个角色)
					当是系统管理员/经理查询时, 查询所有的用户 
	* @param @param orderId 订单ID(自增)
	* @param @param orderNo	订单No
	* @param @return    
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	private List<Map<String,Object>> searchOrderBindFeeCompany(long orderId,String orderNo,long userId,long roleId){
		//(1)准备查询条件
		List<Integer> itemTypeList=getItemTypeList();  //费用类型列表:四项费用,市场费用

		//登录角色判定:系统管理员
		List<Long> roleIdList=new ArrayList<Long>();  
		//确定所查询(用户-角色范围)
		if(userId==0 && roleId==0){ //对某订单查询全部(用户-角色)
			userId=-1;  //此时用户条件无效,角色条件无效.只有order及费用类型列表有效.
		}
		else{  //对某订单查询指定的用户-角色(条件全部有效.只查询了此订单下的绑定费用)
			roleIdList.add(roleId);
		}
		
		//(2)查询公司帐薄
		List<AccountCompany> accountList=accountCompanyService.getItemsByOrderAndBindUser(orderId, itemTypeList,userId,roleIdList);
		
		//根据帐薄条目查询费用归属
		List<Map<String,Object>> accountCompanyList=addFeeBelongCompany(accountList);
		
		return accountCompanyList;
	}
	
	/** 
		* @Title: searchOrderMarketFeeCompany 
		* @Description: 查询非直接绑定/公司内部 费用 
		* @param @param orderId
		* @param @param orderNo
		* @param @param userId
		* @param @param roleId
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private List<Map<String,Object>> searchOrderUnbindFeeCompany(long orderId,String orderNo,long userId,long roleId){
		//(1)准备查询条件
		List<Integer> itemTypeList=getItemTypeList();  	//费用类型列表:四项费用,市场费用
		
		//登录角色:系统管理员
		List<Long> roleIdList=new ArrayList<Long>();  //查询登录用户的角色列表(此条件无效)
		userId=0;  //查询非绑定费用(此条件有效,所有非绑定费用userId==0)
		
		
		//(2)查询公司帐薄
		List<AccountCompany> accountList=accountCompanyService.getItemsByOrderAndBindUser(orderId, itemTypeList, userId, roleIdList);
		
		//根据帐薄条目查询费用归属
		List<Map<String,Object>> accountCompanyList=addFeeBelongCompany(accountList);
		
		return accountCompanyList;
	}
	
	
	/** 
		* @Title: searchOrderFee_personal 
		* @Description: 查询订单费用:个人帐户 
		* @param @param orderId
		* @param @param orderNo
		* @param @param userId
		* @param @param roleId
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private List<Map<String,Object>> searchOrderFeePersonal(long orderId,String orderNo,long userId,long roleId){
		//(1)准备查询条件
		List<Integer> itemTypeList=getItemTypeList();  //费用类型列表:四项费用,市场费用

		//登录用户:系统管理员,OS/IS个人帐号
		//admin:个人视角:查询全部			---userid条件无效,角色列表条件无效
		//admin:个人视角:查询特定用户-角色	---userid条件有效,角色列表条件有效
		//OS/IS:个人视角:查询全部			---userid条件有效(来自于登录用户ID),角色列表条件有效(此登录用户的所有角色-可用于查询的)
		//OS/IS:个人视角:查询特定用户-角色	---userid条件有效,角色列表条件有效
		List<Long> roleIdList=new ArrayList<Long>();  //查询登录用户的角色列表		
		if(userId==0 && roleId==0){ //查询全部
			boolean searchAll=needSearchAll();//查询登录用户是否是admin
			if(!searchAll){  //如果登录的不是admin
				userId=getLoginUserId();
				List<Map<String,Object>> userRoleList=getUserRoles();
				for(int i=0;i<userRoleList.size();i++){
					roleIdList.add((Long)userRoleList.get(i).get("role_id"));
				}
			}
		}
		else{
			roleIdList.add(roleId);
		}
		
		//(2)查询个人帐薄
		List<AccountPersonal> accountList=accountPersonalService.getItemsByOrderAndBindUser(orderId, itemTypeList,userId,roleIdList);
		
		//根据帐薄条目查询费用归属
		List<Map<String,Object>> accountCompanyList=addFeeBelongPersonal(accountList);
		
		return accountCompanyList;
	}
	
	private List<Map<String,Object>> addFeeBelongPersonal(List<AccountPersonal> accountList){
		List<Map<String,Object>> accountCompanyList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<accountList.size();i++){
			Map<String,Object> accountItem=new HashMap<String,Object>();
			
			
			Long bindUserId=accountList.get(i).getBindUserId();
			Long bindRoleId=accountList.get(i).getRoleId();
			
			String bindUserName=userService.selectByPrimaryKey(bindUserId).getUsername();
			String bindUserRole=roleService.selectByPrimaryKey(bindRoleId).getRoleName();
			
			accountItem.put("bindUserName", bindUserName);
			accountItem.put("bindUserRole", bindUserRole);
			accountItem.put("accountItem", accountList.get(i));
						
			accountCompanyList.add(accountItem);
			
		}
		
		return accountCompanyList;
	}
	
	private List<Integer> getItemTypeList(){
		List<Integer> itemTypeList=new ArrayList<Integer>();		
		itemTypeList.add(AccountItemType.COMMUNICATION_FEE);
		itemTypeList.add(AccountItemType.ENTERTAINMENT_FEE);
		itemTypeList.add(AccountItemType.TRANSPORTATION_FEE);
		itemTypeList.add(AccountItemType.TRAVEL_EXPENSE_FEE);
		itemTypeList.add(AccountItemType.MARKET_FEE);  		//市场费
		return itemTypeList;
	}

}
