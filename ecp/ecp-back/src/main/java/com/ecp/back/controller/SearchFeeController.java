package com.ecp.back.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
		
		List<Map<String,Object>> agentIdList=getSearchScope(userId,roleId);
		
		// 查询 并分页		
		PageHelper.startPage(pageNum, pageSize); // PageHelper
		

		//确定用户的查询范围(代理商范围)						
		List<Map<String,Object>> orderList = orderService.selectOrder(orderTimeCond,
											 dealStateCond,
											 searchTypeValue,condValue,
											 provinceName,cityName,countyName,
											 agentIdList);  //查询订单
		
				
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
		* @Description: 查询当前登录用户可以管理的用户及角色.
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
		
		return null;
		
	}
	
	
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
		* @Title: showFeeUI 
		* @Description: 显示费用界面 
		* @param @param orderId 订单自增ID
		* @param @param orderNo 订单NO
		* @param @param model
		* @param @return     
		* @return String    返回类型 
		* @throws 
	*/
	@RequestMapping(value="/showfee")
	public String showFeeUI(long orderId,String orderNo,Model model){
		
		//自公司帐薄查询费用
		List<Map<String,Object>> accountCompanyList=searchOrderFourFee(orderId,orderNo);
		
		//查询与此订单相关的企业,而后查询与此企业相关的OS/IS人员列表.		
		//(4)根据企业与OS/IS的绑定关系查询所绑定的客户
		long agentId=searchAgentByOrder(orderId);
		List<Map<String,Object>> osList=agentBindService.getSalesByAgentId(agentId, RoleCodeConstants.OS);
		List<Map<String,Object>> isList=agentBindService.getSalesByAgentId(agentId, RoleCodeConstants.IS);		
		
		
		//回传参数
		model.addAttribute("accountCompanyList",accountCompanyList);
		model.addAttribute("osList",osList);
		model.addAttribute("isList",isList);
		
		model.addAttribute("orderId", orderId);
		model.addAttribute("orderNo",orderNo);
		
		return RESPONSE_THYMELEAF_BACK + "fee_show";
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
		* @Title: showFeeTable 
		* @Description: 显示费用列表 
		* @param @param orderId
		* @param @param orderNo
		* @param @param model
		* @param @return     
		* @return String    返回类型 
		* @throws 
	*/
	@RequestMapping(value="/table")
	public String showFeeTable(long orderId,String orderNo,Model model){
		
		List<Map<String,Object>> accountCompanyList=searchOrderFourFee(orderId,orderNo);
		
		//回传参数
		model.addAttribute("accountCompanyList",accountCompanyList);	
		
		
		return RESPONSE_THYMELEAF_BACK + "fee_table";
	}
	
	
	/** 
	* @Title: searchOrderFourFee 
	* @Description: 查询订单费用 
	* @param @param orderId 订单ID(自增)
	* @param @param orderNo	订单No
	* @param @return    
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
		itemTypeList.add(AccountItemType.MARKET_FEE);  //加入市场费
		
		
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
	
	

}
