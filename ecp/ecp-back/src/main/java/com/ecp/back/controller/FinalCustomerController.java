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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecp.back.commons.RoleCodeConstants;
import com.ecp.bean.DeletedType;
import com.ecp.bean.UserBean;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.FinalCustomer;
import com.ecp.entity.Role;
import com.ecp.service.back.IRoleService;
import com.ecp.service.back.IUserService;
import com.ecp.service.front.IAgentBindService;
import com.ecp.service.front.IFinalCustomerService;
import com.ecp.service.front.IOrderItemService;
import com.ecp.service.front.IOrderService;
import com.ecp.service.front.IUserAgentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.util.StringUtil;

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
		
		//确认登录用户所查询的代理商范围
		List<Map<String,Object>> agentIdList=getSearchScope(userId,roleId);  
		
		//确定查询订单范围
		List<Map<String,Object>> orderIdList=null;  
		if(searchTypeValue==8  && !StringUtil.isEmpty(condValue)){  //最终用户组织名称条件
			List<Map<String,Object>> tempList=finalCustomerService.getFinalCustomerByOrganizationName(condValue);
			orderIdList=new ArrayList<>();
			for(Map<String,Object> finalCustomer:tempList){
				Map<String,Object> tempMap=new HashMap<>();
				tempMap.put("order_id",finalCustomer.get("order_id"));
				orderIdList.add(tempMap);
			}
		}
		
		// 查询 并分页		
		PageHelper.startPage(pageNum, pageSize); // PageHelper			

		//List<Map<String,Object>> orderList = orderService.selectAllOrderByOrderTimeAndDealState(orderTimeCond,dealStateCond);
		
		//List<Map<String,Object>> orderList = orderService.selectOrder(orderTimeCond,dealStateCond,searchTypeValue,condValue);  //查询订单
		List<Map<String,Object>> orderList = orderService.selectOrderByOrderScope(
				 orderTimeCond,dealStateCond,
				 searchTypeValue,condValue,
				 provinceName,cityName,countyName,
				 agentIdList,orderIdList);  //查询订单
		
		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(orderList);// (使用了拦截器或是AOP进行查询的再次处理)
		
		List<Map<String,Object>> userRoleList=getUserRoles();
		model.addAttribute("userRoleList", userRoleList);  //查询用户角色列表
		
		model.addAttribute("pageInfo", pageInfo);  //返回分页信息		
		model.addAttribute("orderList", orderList); //列表
		//在订单中加入最终用户信息
		for(Map<String,Object> order:orderList){
			List<FinalCustomer> finalCustomerList=finalCustomerService.getFinalCustomerByOrder((long)order.get("id"));
			order.put("finalCustomerList", finalCustomerList);
		}
		
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
			if(!searchAll){  //非admin用户
				long loginUserId=getLoginUserId();
				agentIdList=agentBindService.getAgentIdListByBindedUser(loginUserId);
			}			
		}
		return agentIdList;
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
		setFinalCustomerToModel(orderId,model);
		
		//回传参数
		model.addAttribute("orderId", orderId);
		model.addAttribute("orderNo",orderNo);
		
		return RESPONSE_THYMELEAF_BACK + "finalcustomer_edit";
	}
	
	
	@RequestMapping(value="/table")
	public String showFinalCustomerTable(long orderId,String orderNo,Model model){
		setFinalCustomerToModel(orderId,model);
		return RESPONSE_THYMELEAF_BACK + "finalcustomer_table";
	}
	
	
	/** 
	* @Title: loadAddDialog 
	* @Description: 加载:ADD最终用户 DIALOG 
	* @param @param model
	* @param @return     
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping(value="/loadadddialog")
	public String loadAddDialog(Model model){			
		return RESPONSE_THYMELEAF_BACK + "add_finalcustomer_dialog";
	}
	
	/** 
		* @Title: loadDetailDialog 
		* @Description: 加载:修改最终用户DIALOG 
		* @param @param finalCustomerId
		* @param @param model
		* @param @return     
		* @return String    返回类型 
		* @throws 
	*/
	@RequestMapping(value="/loaddetaildialog")
	public String loadDetailDialog(long finalCustomerId, Model model){
		//读取当前需要编辑的联系人信息,并返回数据
		FinalCustomer finalCustomer=finalCustomerService.selectByPrimaryKey(finalCustomerId);
		model.addAttribute("finalCustomer", finalCustomer);
		return RESPONSE_THYMELEAF_BACK + "detail_finalcustomer_dialog";
	}
	
	
	private void setFinalCustomerToModel(long orderId,Model model){
		//查询此订单下的最终用户
		List<FinalCustomer> finalCustomerList=finalCustomerService.getFinalCustomerByOrder(orderId);
				
		//回传参数
		model.addAttribute("finalCustomerList",finalCustomerList);
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
	
	@RequestMapping(value="/del")
	@ResponseBody
	public Object deleteFinalCustomer(long finalCustomerId, Model model){
		
		//修改:最终用户
		FinalCustomer finalCustomer =new FinalCustomer();
		finalCustomer.setId(finalCustomerId);
		finalCustomer.setDeleted((byte)DeletedType.YES);
		int row=finalCustomerService.updateByPrimaryKeySelective(finalCustomer);
		
		if (row>0){
			return RequestResultUtil.getResultUpdateSuccess();
		}
		else
			return RequestResultUtil.getResultUpdateWarn();
	}
	
	
	
	@RequestMapping(value="/savedetail")
	@ResponseBody
	public Object saveDetailLinkman(FinalCustomer finalCustomer, Model model){
		//修改联系人
		int row =finalCustomerService.updateByPrimaryKeySelective(finalCustomer);
		if (row>0){
			return RequestResultUtil.getResultUpdateSuccess();
		}
		else
			return RequestResultUtil.getResultUpdateWarn();
	}
	

}
