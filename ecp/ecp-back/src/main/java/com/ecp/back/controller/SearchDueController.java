package com.ecp.back.controller;

import java.math.BigDecimal;
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
import com.ecp.entity.Orders;
import com.ecp.entity.Role;
import com.ecp.entity.UserExtends;
import com.ecp.service.back.IRoleService;
import com.ecp.service.back.IUserService;
import com.ecp.service.front.IAccountCompanyService;
import com.ecp.service.front.IAccountPersonalService;
import com.ecp.service.front.IAgentBindService;
import com.ecp.service.front.IContractItemsService;
import com.ecp.service.front.IContractService;
import com.ecp.service.front.IOrderItemService;
import com.ecp.service.front.IOrderService;
import com.ecp.service.front.IUserAgentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


/**
	* Copyright (c) 2017 by Hz
	* @ClassName:     SearchDueController.java
	* @Description:   欠款查询
	* 
	* @author:        lenovo
	* @version:       V1.0  
	* @Date:          2018年4月2日 下午4:21:25 
*/
@Controller
@RequestMapping("/back/searchdue")
public class SearchDueController {
	private final Logger log = Logger.getLogger(getClass());
	
	private static final String RESPONSE_THYMELEAF_BACK = "back/thymeleaf/searchdue/";
	private static final int PAGE_SIZE = 8;
	
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
	@Autowired
	IContractService contractService;  //合同服务
	@Autowired
	IContractItemsService contractItemsService;  //合同服务
	

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
							  int totalPayFlag,
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
		
		//查询并分页		
		PageHelper.startPage(pageNum, pageSize); // PageHelper
		//确定用户的查询范围(代理商范围)						
		List<Map<String,Object>> orderList = orderService.selectOrder(
											 orderTimeCond,dealStateCond,
											 searchTypeValue,condValue,
											 provinceName,cityName,countyName,
											 agentIdList,totalPayFlag);  //查询订单
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(orderList);// (使用了拦截器或是AOP进行查询的再次处理)
		
		List<Map<String,Object>> userRoleList=getUserRoles();
		model.addAttribute("userRoleList", userRoleList);  //查询用户角色列表
		
		model.addAttribute("pageInfo", pageInfo);  //分页
		
		
		List<Map<String,Object>> addedList=getContractAndPayAmount(orderList);
		
		model.addAttribute("orderList", addedList); //列表
		
		//回传区域条件及用户/角色
		model.addAttribute("provinceName", provinceName);
		model.addAttribute("cityName", cityName);
		model.addAttribute("countyName", countyName);
		model.addAttribute("userId", userId);
		model.addAttribute("roleId", roleId);
		
		model.addAttribute("totalPayFlag", totalPayFlag);  //回传是否欠款条件
		
		//获取合同总金额,回款总金额
		Map<String,Object> amountMap=getScopeAmount(orderTimeCond,
				   dealStateCond,				   
				   searchTypeValue, condValue,							  
				   provinceName, cityName, countyName,
				   userId, roleId);	
		
		model.addAttribute("contractAmount", amountMap.get("contractAmount"));
		model.addAttribute("payAmount", amountMap.get("payAmount"));
		
		BigDecimal orderAmount=getOrderAmount(orderTimeCond,dealStateCond,
				 searchTypeValue,condValue,
				 provinceName,cityName,countyName,
				 agentIdList,totalPayFlag);
		model.addAttribute("orderAmount", orderAmount);	
		
		
		return RESPONSE_THYMELEAF_BACK + "order_table";
	}
	
	
	/** 
		* @Title: getOrderAmount 
		* @Description: 获取范围内订单总金额. 
		* @param @param orderTimeCond
		* @param @param dealStateCond
		* @param @param searchTypeValue
		* @param @param condValue
		* @param @param provinceName
		* @param @param cityName
		* @param @param countyName
		* @param @param agentIdList
		* @param @param totalPayFlag
		* @param @return     
		* @return BigDecimal    返回类型 
		* @throws 
	*/
	private BigDecimal getOrderAmount(int orderTimeCond,
			  int dealStateCond,
			  Integer searchTypeValue,
			  String condValue,							  
			  String provinceName,
			  String cityName,
			  String countyName,
			  List<Map<String,Object>> agentIdList,
			  int totalPayFlag){
		
		return orderService.getOrderAmount(orderTimeCond,dealStateCond,
				 searchTypeValue,condValue,
				 provinceName,cityName,countyName,
				 agentIdList,totalPayFlag);
	}
	
	
	
	
	/** 
		* @Title: getContractAndPayAmount 
		* @Description:  获取订单合同金额(应收款)及回款额
		* @param @param orderList
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private List<Map<String,Object>> getContractAndPayAmount(List<Map<String,Object>> orderList){
		List<Map<String,Object>> tmpList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<orderList.size();i++){
			Map<String,Object> compositeObj=new HashMap<String,Object>();
			
			Map<String,Object> order=orderList.get(i);
			compositeObj.put("order", order);
			
			//根据合同ID查询:合同金额(应收款)
			BigDecimal contractAmount=new BigDecimal(0);  
			if(order.get("contract_id")!=null){
				contractAmount=contractItemsService.getContractAmountByNo((String) order.get("contract_no"));
			}
			compositeObj.put("contractAmount", contractAmount);
			
			//根据订单ID:查询收款合计
			BigDecimal payAmount= accountCompanyService.getAmountByOrderId((long) order.get("id"),AccountItemType.PAYMENT);			
			compositeObj.put("payAmount", payAmount);
			
			tmpList.add(compositeObj);
		}
		
		return tmpList;
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
			if(!searchAll){  //非admin用户
				long loginUserId=getLoginUserId();
				agentIdList=agentBindService.getAgentIdListByBindedUser(loginUserId);
			}			
		}
		return agentIdList;
	}
	
	//
	/** 
		* @Title: getUserRoles 
		* @Description: 查询当前登录用户所有角色.
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
			case RoleCodeConstants.ADMIN:case RoleCodeConstants.MANAGER:
			case RoleCodeConstants.BUSSMAN:	case RoleCodeConstants.SALEMAN:	
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
			case RoleCodeConstants.OS:case RoleCodeConstants.IS:
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
		* @Description: 根据登录用户的角色:是否可查询所有代理商订单
		* @param @return
		* 				如果是经理级别,则可查询所有;
		* 				如果OS/IS则只可查询与自己所绑定代理商范围内的     
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
		* @Title: showOrderFee 
		* @Description: 显示指定定单的回款列表 
		* @param @param orderId 订单自增ID
		* @param @param orderNo 订单号
		* @param @param userId	用户ID(所有:0)
		* @param @param roleId	用户角色ID(所有:0)
		* @param @param model
		* @param @return     
		* @return String    返回类型 
		* @throws 
	*/
	@RequestMapping(value="/showfee")
	public String showOrderFee(long orderId,String orderNo,	long userId,long roleId,Model model){
			
		List<AccountCompany> accountCompanyList=searchOrderUnbindFeeCompany(orderId,orderNo,userId,roleId);  //查询回款(回款项属于非绑定项)
		model.addAttribute("accountItemList",accountCompanyList);
		
		Map<String,Object> amountMap= getOrderAmount(orderId);  //获取订单所对应合同的总金额及回款金额
		
		model.addAttribute("contractAmount", amountMap.get("contractAmount"));
		model.addAttribute("payAmount", amountMap.get("payAmount"));
		
		
		model.addAttribute("orderId", orderId);
		model.addAttribute("orderNo",orderNo);
		
		return RESPONSE_THYMELEAF_BACK + "fee_show";
	}
	
	/** 
		* @Title: getOrderAmount 
		* @Description: 查询订单的应收与己收 
		* @param @param orderId 订单ID
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private  Map<String,Object>  getOrderAmount(long orderId){
		Orders order=orderService.selectByPrimaryKey(orderId);
		//查询订单合同金额, 回款合计, 总的欠款
		//根据合同ID查询:合同金额(应收款)
		Map<String,Object> compositeObj=new HashMap<String,Object>();
		
		BigDecimal contractAmount=new BigDecimal(0);  
		if(order.getContractNo()!=null){
			contractAmount=contractItemsService.getContractAmountByNo(order.getContractNo());
		}
		compositeObj.put("contractAmount", contractAmount);
		
		//根据订单ID:查询收款合计
		BigDecimal payAmount= accountCompanyService.getAmountByOrderId(order.getId(),AccountItemType.PAYMENT);			
		compositeObj.put("payAmount", payAmount);
		
		return compositeObj;
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
	public String showAllFeeUI(int orderTimeCond, int dealStateCond,
							  int pageNum, int pageSize,
							  Integer searchTypeValue, String condValue,							  
							  String provinceName, String cityName,String countyName,
							  long userId, long roleId,
							  Model model){
		int searchType=0;
		String condStr="";
		
		//置默认值(搜索)
		if(searchTypeValue!=null){
			searchType=searchTypeValue;
			condStr=condValue;
		}
		
		//查询回款列表:范围
		List<AccountCompany> accountCompanyList = searchScopeUnbindFeeCompany(orderTimeCond,
				   dealStateCond,
				   pageNum, pageSize,
				   searchType, condStr,							  
				   provinceName, cityName, countyName,
				   userId, roleId);
		
		model.addAttribute("accountItemList", accountCompanyList);
		
		//获取合同总金额
		Map<String,Object> amountMap=getScopeAmount(orderTimeCond,
				   dealStateCond,				   
				   searchType, condStr,							  
				   provinceName, cityName, countyName,
				   userId, roleId);	
		
		model.addAttribute("contractAmount", amountMap.get("contractAmount"));
		model.addAttribute("payAmount", amountMap.get("payAmount"));
		
		
		return RESPONSE_THYMELEAF_BACK + "fee_all_show";
	}
	
	/** 
		* @Title: getScopeAmount 
		* @Description: 获取范围合同额,回款合计 
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
		* @return Map<String,Object>    返回类型 
		* @throws 
	*/
	private Map<String,Object> getScopeAmount(int orderTimeCond, int dealStateCond,			  
			  Integer searchTypeValue, String condValue,							  
			  String provinceName, String cityName,String countyName,
			  long userId, long roleId){
		
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
		long searchUserId=0;		
		
		//(2)确定用户的查询范围(代理商范围)
		List<Map<String,Object>> agentIdList=getSearchScope(userId,roleId);
		
		
		//(3)查询公司帐薄
		BigDecimal payAmount=accountCompanyService.searchAccountItemAmount(
																orderTimeCond,dealStateCond,
																searchType,	condStr,
																provinceName,cityName,countyName,
																agentIdList,														
																itemTypeList,
																searchUserId,roleIdList);
		//(3)查询合同详情.
		BigDecimal contractAmount=contractItemsService.searchContractAmount(
																orderTimeCond,dealStateCond,
																searchType,	condStr,
																provinceName,cityName,countyName,
																agentIdList);
		
		Map<String,Object> compositeObj=new HashMap<String,Object>();
		compositeObj.put("payAmount", payAmount);
		compositeObj.put("contractAmount", contractAmount);
		
		
		return compositeObj;
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
	private List<AccountCompany> searchScopeUnbindFeeCompany(int orderTimeCond,
																  int dealStateCond,
																  int pageNum,  int pageSize,
																  Integer searchTypeValue, String condValue,							  
																  String provinceName, String cityName, String countyName,
																  long userId,long roleId){
		
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
																orderTimeCond,dealStateCond,
																searchType,	condStr,
																provinceName,cityName,countyName,
																agentIdList,														
																itemTypeList,
																unbindUserId,roleIdList);
		//PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(orderList);// (使用了拦截器或是AOP进行查询的再次处理) 查询分页:结束
		return accountList;
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
		* @Title: searchOrderUnbindFeeCompany 
		* @Description: 查询非直接绑定/公司内部 费用 
		* @param @param orderId
		* @param @param orderNo
		* @param @param userId
		* @param @param roleId
		* @param @return     
		* @return List<Map<String,Object>>    返回类型 
		* @throws 
	*/
	private List<AccountCompany> searchOrderUnbindFeeCompany(long orderId,String orderNo,long userId,long roleId){
		//(1)准备查询条件
		List<Integer> itemTypeList=getItemTypeList();  	//费用类型列表:四项费用,市场费用
		
		//登录角色:系统管理员
		List<Long> roleIdList=new ArrayList<Long>();  //查询登录用户的角色列表(此条件无效)
		int searchUserId=0;  //查询非绑定费用(此条件有效,所有非绑定费用userId==0)
		
		//(2)查询公司帐薄
		List<AccountCompany> accountList=accountCompanyService.getItemsByOrderAndBindUser(orderId, itemTypeList, searchUserId, roleIdList);
		
		return accountList;
	}
	
	
	private List<Integer> getItemTypeList(){
		List<Integer> itemTypeList=new ArrayList<Integer>();		
		itemTypeList.add(AccountItemType.PAYMENT);  		//回款
		return itemTypeList;
	}

}
