package com.ecp.back.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecp.back.commons.RoleCodeConstants;
import com.ecp.bean.AccountItemType;
import com.ecp.bean.ContractStateType;
import com.ecp.bean.UserBean;
import com.ecp.entity.AccountCompany;
import com.ecp.entity.CustLockRel;
import com.ecp.entity.PushmoneyConfig;
import com.ecp.entity.Role;
import com.ecp.entity.UserExtends;
import com.ecp.service.back.IPushmoneyConfigService;
import com.ecp.service.back.IRoleService;
import com.ecp.service.back.ISalesTargetService;
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

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName OrderManagementController
 * @Description 订单管理(后台)
 * @author Administrator
 * @Date 2017年6月27日 上午9:55:24
 * @version 1.0.0
 */
@Controller
@RequestMapping("/back/performance")
public class PerformanceController {
	private static final String RESPONSE_THYMELEAF_BACK = "back/thymeleaf/performance/";
	private static final String RESPONSE_THYMELEAF_BACK_PERFORMANCE_COMPANY = "back/thymeleaf/performance/performance_company/";

	private final int PAGE_SIZE = 8;

	private final Logger log = Logger.getLogger(getClass());

	@Autowired
	IOrderService orderService; // 订单服务
	@Autowired
	IOrderItemService orderItemService; // 订单条目
	@Autowired
	IUserAgentService userAgentService; // 代理商
	@Autowired
	IAccountPersonalService accountPersonalService;
	@Resource(name="salesTargetServiceBean")
	private ISalesTargetService salesTargetService;//销售指标
	@Resource(name="userServiceBean")
	private IUserService userService;
	@Autowired
	IAgentBindService agentBindService;  //代理商绑定
	@Autowired
	IContractService contractService;
	@Autowired
	IContractItemsService contractItemsService;
	@Autowired
	IAccountCompanyService accountCompanyService;
	@Resource(name="pushmoneyConfigServiceBean")
	private IPushmoneyConfigService pushmoneyConfigService;
	@Resource(name="roleServiceBean")
	private IRoleService roleService;

	/**
	 * @Description 显示-订单列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/show")
	public String order_show(Model model) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		List<Map<String, Object>> agentIdList = null;
		
		agentIdList = this.judgeRole(user, model, null, null);
		
		//return RESPONSE_THYMELEAF_BACK + "order_show";
		return RESPONSE_THYMELEAF_BACK + "performance";
	}

	/**
	 * @Description 订单查询
	 * @param orderTimeCond
	 *            订单时间条件
	 * @param dealStateCond
	 *            订单处理状态条件
	 * @param pageNum
	 *            页号
	 * @param pageSize
	 *            页大小
	 * @param searchTypeValue
	 *            搜索类型
	 * @param condValue
	 *            搜索条件值
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ordertable")
	public String order_table(int orderTimeCond, int dealStateCond, Integer pageNum, Integer pageSize,
			Integer searchTypeValue, String condValue, Model model, Long userId, Long roleId, String provinceName, String cityName, String countyName) {
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
			pageSize = PAGE_SIZE;
		}

		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		List<Map<String, Object>> agentIdList = null;
		
		/*List<Role> roleList = user.getRoleList();
		boolean isISOrOS = true;
		for(Role role : roleList){
			String roleCode = role.getRoleCode();
			if(StringUtils.isNotBlank(roleCode)){
				if(roleCode.equalsIgnoreCase(RoleCodeConstants.ADMIN) || roleCode.equalsIgnoreCase(RoleCodeConstants.MANAGER) || roleCode.equalsIgnoreCase(RoleCodeConstants.BUSSMAN) || roleCode.equalsIgnoreCase(RoleCodeConstants.SALEMAN)){
					isISOrOS = false;
					break;
				}
			}
		}
		
		if(isISOrOS){
			List<Map<String, Object>> userList = userService.getById(user.getId());
			model.addAttribute("userList", userList);
			List<Role> tempRoleList = new ArrayList<>();
			for(Role role : roleList){
				String roleCode = role.getRoleCode();
				if(StringUtils.isNotBlank(roleCode)){
					if(roleCode.equalsIgnoreCase(RoleCodeConstants.IS) || roleCode.equalsIgnoreCase(RoleCodeConstants.OS)){
						tempRoleList.add(role);
					}
				}
			}
			model.addAttribute("roleList", tempRoleList);
			
			Example example = new Example(CustLockRel.class);
			if(roleList.size()>1){
				example.createCriteria().andEqualTo("bindUserId", user.getId());
			}else{
				example.createCriteria().andEqualTo("bindUserId", user.getId()).andEqualTo("roleId", roleList.get(0).getRoleId());
			}
			List<CustLockRel> tempList = agentBindService.selectByExample(example);
			for(CustLockRel temp : tempList){
				if(agentIdList==null){
					agentIdList = new ArrayList<>();
				}
				Map<String, Object> tempMap = new HashMap<>();
				Long agentId = temp.getCustId();
				if(agentId!=null && agentId>0){
					tempMap.put("cust_id", agentId);
					agentIdList.add(tempMap);
				}else{
					tempMap.put("cust_id", null);
				}
			}
		}else{
			List<Map<String, Object>> userList = userService.getISAndOSUser();
			model.addAttribute("userList", userList);
		}
		
		if(userId==null && roleId==null){
			
		}else{
			Example example = new Example(CustLockRel.class);
			if(userId==null){
				example.createCriteria().andEqualTo("roleId", roleId);
			}else if(roleId==null){
				example.createCriteria().andEqualTo("bindUserId", userId);
			}else{
				example.createCriteria().andEqualTo("bindUserId", userId).andEqualTo("roleId", roleId);
			}
			List<CustLockRel> tempList = agentBindService.selectByExample(example);
			for(CustLockRel temp : tempList){
				if(agentIdList==null){
					agentIdList = new ArrayList<>();
				}
				Map<String, Object> tempMap = new HashMap<>();
				Long agentId = temp.getCustId();
				if(agentId!=null && agentId>0){
					tempMap.put("cust_id", agentId);
					agentIdList.add(tempMap);
				}else{
					tempMap.put("cust_id", null);
				}
			}
		}*/
		agentIdList = this.judgeRole(user, model, userId, roleId);
		
		// 置默认值(搜索)
		if (searchTypeValue == null) {
			searchTypeValue = 0;
			condValue = "";
		}

		// 回传查询条件
		model.addAttribute("orderTimeCond", orderTimeCond);
		model.addAttribute("dealStateCond", dealStateCond);

		// 搜索条件类型、搜索条件值
		model.addAttribute("searchTypeValue", searchTypeValue); // 查询字段值
		model.addAttribute("condValue", condValue); // 查询条件值

		//回传区域条件及用户/角色
		model.addAttribute("provinceName", provinceName);
		model.addAttribute("cityName", cityName);
		model.addAttribute("countyName", countyName);
		model.addAttribute("userId", userId);
		model.addAttribute("roleId", roleId);
		
		// 查询 并分页
		PageHelper.startPage(pageNum, pageSize); // PageHelper

		// List<Map<String,Object>> orderList =
		// orderService.selectAllOrderByOrderTimeAndDealState(orderTimeCond,dealStateCond);

		List<Map<String, Object>> orderList = orderService.selectOrder(orderTimeCond, dealStateCond, searchTypeValue, condValue, provinceName, cityName, countyName, agentIdList); // 查询订单

		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(orderList);// (使用了拦截器或是AOP进行查询的再次处理)
		orderList = pageInfo.getList();
		
		BigDecimal orderTotalAmount = new BigDecimal("0.00");//订单总金额
		BigDecimal contractTotalAmount = new BigDecimal("0.00");//合同总金额
		for(Map<String, Object> order : orderList){
			String orderId = order.get("id").toString();
			String totalPrice = order.get("total_price").toString();
			//String contractState = order.get("contract_state").toString();
			Object contractNoTemp = order.get("contract_no");
			String contractNo = null;
			if(contractNoTemp!=null){
				contractNo = contractNoTemp.toString();
			}
			/*if(StringUtils.isNotBlank(orderId)){
				if(StringUtils.isNotBlank(contractState) && Integer.parseInt(contractState)==ContractStateType.FINISHED){
					Map<String, Object> params = new HashMap<>();
					params.put("order_id", orderId);
					//params.put("account_type", AccountItemType.PERFORMANCE_FEE);
					//List<Map<String, Object>> accountCompanyList = accountCompanyService.selectAccountCompanyMap(params);
					List<Map<String, Object>> performanceList = accountPersonalService.selectPerformanceMap(params);
					BigDecimal performanceTotalPrice = new BigDecimal("0.00"); 
					for(Map<String, Object> temp : performanceList){
						String amount = temp.get("amount").toString();
						if(StringUtils.isNotBlank(amount)){
							performanceTotalPrice = performanceTotalPrice.add(new BigDecimal(amount));
						}
					}
					order.put("order_total_amount", performanceTotalPrice);
					performanceTotalAmount = performanceTotalAmount.add(performanceTotalPrice);
				}else{
					order.put("order_total_amount", "0.00");
				}
			}*/
			if(StringUtils.isNotBlank(contractNo)){
				BigDecimal contractAmount = contractItemsService.getContractAmountByNo(contractNo);
				order.put("contract_amount", contractAmount);
				contractTotalAmount = contractTotalAmount.add(contractAmount);
			}else{
				order.put("contract_amount", "0.00");
			}
			
			if(StringUtils.isNotBlank(totalPrice)){
				orderTotalAmount = orderTotalAmount.add(new BigDecimal(totalPrice));
			}
			
		}

		model.addAttribute("orderTotalAmount", orderTotalAmount); //订单统计
		model.addAttribute("contractTotalAmount", contractTotalAmount); //业绩统计

		model.addAttribute("pageInfo", pageInfo); // 分页
		model.addAttribute("orderList", orderList); // 列表

		return RESPONSE_THYMELEAF_BACK + "order_table";
	}

	/**
	 * 获取业绩列表
	 * 
	 * @param model
	 * @param request
	 * @param orderId
	 * @param fullYear
	 * @return
	 */
	@RequestMapping(value = "/get-performance")
	public String getPerformance(Model model, HttpServletRequest request, String fullYear, Long userId, Long roleId, String startTime, String endTime) {

		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();

		String startDateYear = this.getYearOrMonth(startTime, 1);//开始时间年
		String startDateMonth = this.getYearOrMonth(startTime, 2);//开始时间月
		String endDateYear = this.getYearOrMonth(endTime, 1);//结束时间年
		String endDateMonth = this.getYearOrMonth(endTime, 2);//结束时间月
		//费用类型
		List<Integer> itemTypeList=new ArrayList<>();
		itemTypeList.add(AccountItemType.PERFORMANCE_FEE);//业绩
		
		List<Map<String, Object>> performanceList = accountPersonalService.getItemsByDateAndUserOrRole(startDateYear, startDateMonth, endDateYear, endDateMonth, userId, roleId, itemTypeList);

		BigDecimal performanceTotalAmount = new BigDecimal("0.00");//业绩总金额
		BigDecimal orderTotalAmount = new BigDecimal("0.00");//订单总金额
		BigDecimal contractTotalAmount = new BigDecimal("0.00");//合同总金额
		
		for(Map<String, Object> temp : performanceList){
			String amount = temp.get("amount").toString();
			if(StringUtils.isNotBlank(amount)){
				performanceTotalAmount = performanceTotalAmount.add(new BigDecimal(amount));
			}
			
			temp.put("company_name", null);
			String agentId = temp.get("cust_id").toString();
			if(StringUtils.isNotBlank(agentId)){
				UserExtends agent = userAgentService.selectByPrimaryKey(Long.parseLong(agentId));
				if(agent!=null){
					temp.put("company_name", agent.getCompanyName());
				}
			}
			
			String orderNo = temp.get("order_no").toString();
			if(StringUtils.isNotBlank(orderNo)){
				BigDecimal orderAmount = orderItemService.getOrderAmountByNo(orderNo);
				temp.put("orderAmount", orderAmount);
				orderTotalAmount = orderTotalAmount.add(orderAmount);
			}else{
				temp.put("orderAmount", "0.00");
			}
			
			
			String contractNo = temp.get("contract_no").toString();
			if(StringUtils.isNotBlank(contractNo)){
				BigDecimal contractAmount = contractItemsService.getContractAmountByNo(contractNo);
				temp.put("contractAmount", contractAmount);
				contractTotalAmount = contractTotalAmount.add(contractAmount);
			}else{
				temp.put("contractAmount", "0.00");
			}
		}
		
		/*List<Long> roleIdList = new ArrayList<>();
		if(roleId!=null && roleId>0){
			roleIdList.add(roleId);
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("user_id", userId);
		params.put("role_id_list", roleIdList);
		params.put("start_time", startTime);
		params.put("end_time", endTime);
		List<Map<String, Object>> orderList = orderService.selectOrdersMap(params);
		//List<Map<String, Object>> performanceList = accountCompanyService.selectAccountCompanyMap(params);
		
		BigDecimal performanceTotalAmount = new BigDecimal("0.00");//业绩总金额
		BigDecimal orderTotalAmount = new BigDecimal("0.00");//订单总金额
		BigDecimal contractTotalAmount = new BigDecimal("0.00");//合同总金额
		for(Map<String, Object> order : orderList){
			String tempOrderId = order.get("order_id").toString();
			String contractState = order.get("contract_status").toString();
			if(StringUtils.isNotBlank(contractState) && Integer.parseInt(contractState)==ContractStateType.FINISHED){
				List<Map<String, Object>> orderAccountCompanyList = new ArrayList<>();
				for(Map<String, Object> temp : performanceList){
					String currOrderId = temp.get("order_id").toString();
					if(StringUtils.isNotBlank(currOrderId)){
						if(tempOrderId.equalsIgnoreCase(currOrderId)){
							String amount = temp.get("amount").toString();
							if(StringUtils.isNotBlank(amount)){
								performanceTotalAmount = performanceTotalAmount.add(new BigDecimal(amount));
							}
							orderAccountCompanyList.add(temp);
						}
					}
				}
				order.put("order_account_company_list", orderAccountCompanyList);
			}else{
				order.put("order_account_company_list", new ArrayList<Map<String, Object>>());
			}

			String orderNo = order.get("order_no").toString();
			if(StringUtils.isNotBlank(orderNo)){
				BigDecimal orderAmount = orderItemService.getOrderAmountByNo(orderNo);
				order.put("orderAmount", orderAmount);
				orderTotalAmount = orderTotalAmount.add(orderAmount);
			}else{
				order.put("orderAmount", "0.00");
			}
			
			
			String contractNo = order.get("contract_no").toString();
			if(StringUtils.isNotBlank(contractNo)){
				BigDecimal contractAmount = contractItemsService.getContractAmountByNo(contractNo);
				order.put("contractAmount", contractAmount);
				contractTotalAmount = contractTotalAmount.add(contractAmount);
			}else{
				order.put("contractAmount", "0.00");
			}
			
		}
		
		model.addAttribute("orderList", orderList);*/
		model.addAttribute("performanceList", performanceList);
		model.addAttribute("performanceTotalAmount", performanceTotalAmount);
		model.addAttribute("orderTotalAmount", orderTotalAmount);
		model.addAttribute("contractTotalAmount", contractTotalAmount);

		return RESPONSE_THYMELEAF_BACK + "performance_table";
	}

	/**
	 * 获取某年第一天日期第一秒
	 * 
	 * @param year
	 *            年份
	 * @return String
	 */
	private String getCurrYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(currYearFirst);
	}

	/**
	 * 获取某年最后一天最后一秒
	 * 
	 * @param year
	 *            年份
	 * @return String
	 */
	private String getCurrYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		int dayMis=1000*60*60*24;//一天的毫秒-1  
          
        //返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。  
        long curMillisecond=currYearLast.getTime();//当天的毫秒  
        long resultMis=curMillisecond+(dayMis-1); //当天最后一秒  
        //得到我需要的时间    当天最后一秒  
        Date resultDate=new Date(resultMis);  
        
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(resultDate);
	}
	
	
	
	/**
	 * 获取销售进度
	 * 
	 * @param model
	 * @param request
	 * @param orderId
	 * @param fullYear
	 * @return
	 */
	@RequestMapping(value = "/get-sales-progress")
	public String getSalesProgress(Model model, HttpServletRequest request, Long userId, Long roleId, String fullYear) {

		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		List<Long> roleIdList = new ArrayList<>();
		if(roleId!=null){
			Role role = roleService.selectByPrimaryKey(roleId);
			roleId = role.getRoleId();
			if(roleId!=null && roleId>0){
				roleIdList.add(roleId);
			}
			
		}/*else if(userId!=null){
			List<Role> roleList = roleService.getByUserId(userId);
			for(Role temp : roleList){
				roleId = temp.getRoleId();
				if(roleId!=null && roleId>0){
					roleIdList.add(roleId);
				}
			}
		}*/
		
		Map<String, Object> params = new HashMap<>();
		params.put("user_id", userId);
		params.put("role_id_list", roleIdList);
		params.put("year_name", fullYear);
		List<Map<String, Object>> salesProgressList = salesTargetService.selectSalesTargetMap(params);
		
		Iterator<Map<String, Object>> it = salesProgressList.iterator();
		while (it.hasNext()) {
			Map<String, Object> map = it.next();
			String tempUserId1 = map.get("user_id").toString();
			String tempRoleId1 = map.get("role_id").toString();
			String tempStartTime = map.get("start_date").toString();
			String tempEndTime = map.get("end_date").toString();
			
			Long tempUserId = null;
			Long tempRoleId = null;
			if(StringUtils.isNotBlank(tempUserId1)){
				tempUserId = Long.parseLong(tempUserId1);
			}
			if(StringUtils.isNotBlank(tempRoleId1)){
				tempRoleId = Long.parseLong(tempRoleId1);
			}
			
			String startDateYear = this.getYearOrMonth(tempStartTime, 1);//开始时间年
			String startDateMonth = this.getYearOrMonth(tempStartTime, 2);//开始时间月
			String endDateYear = this.getYearOrMonth(tempEndTime, 1);//结束时间年
			String endDateMonth = this.getYearOrMonth(tempEndTime, 2);//结束时间月
			//费用类型
			List<Integer> itemTypeList=new ArrayList<>();
			itemTypeList.add(AccountItemType.PERFORMANCE_FEE);//业绩
			
			List<Map<String, Object>> performanceList = accountPersonalService.getItemsByDateAndUserOrRole(startDateYear, startDateMonth, endDateYear, endDateMonth, tempUserId, tempRoleId, itemTypeList);
			BigDecimal totalAmount = new BigDecimal("0.00");
			for(Map<String, Object> temp : performanceList){
				String amount = temp.get("amount").toString();
				if(StringUtils.isNotBlank(amount)){
					totalAmount = totalAmount.add(new BigDecimal(amount));
				}
			}
			map.put("total_amount", totalAmount);
			/*Map<String, Object> tempParams = new HashMap<>();
			//tempParams.put("order_no", orderId);
			tempParams.put("start_time", tempStartTime);
			tempParams.put("end_time", tempEndTime);
			tempParams.put("provinceName", provinceName);
			tempParams.put("cityName", cityName);
			tempParams.put("countyName", countyName);
			tempParams.put("user_id", tempUserId);
			tempParams.put("role_id", tempRoleId);
			BigDecimal totalPrice = contractItemsService.selectContractPayPriceTotal(tempParams);
			map.put("total_price", totalPrice);*/
		}

		model.addAttribute("salesProgressList", salesProgressList);

		return RESPONSE_THYMELEAF_BACK + "sales_progress_table";
	}
	
	
	/**
	 * 获取合同商品列表，计算提成
	 * @param model
	 * @param request
	 * @param fullYear
	 * @param userId
	 * @param roleId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/get-pushmoney")
	public String getPushmoney(Model model, HttpServletRequest request, String fullYear, Long userId, Long roleId, String startTime, String endTime, BigDecimal completionRate, String provinceName, String cityName, String countyName) {

		//获取提成比例列表并计算当前的提成比例
		List<Long> roleIdList = new ArrayList<>();
		roleIdList.add(roleId);
		Map<String, Object> params = new HashMap<>();
		params.put("role_id_list", roleIdList);
		List<Map<String, Object>> pushmoneyConfigList = pushmoneyConfigService.getList(params);
		double pushmoneyRate = 0d;//提成比例
		for(Map<String, Object> tempEntity : pushmoneyConfigList){
			tempEntity.put("class_active", false);
		}
		for(Map<String, Object> tempEntity : pushmoneyConfigList){
			String tempCompletionRate = tempEntity.get("completion_rate").toString();
			String tempPushmontyRate = tempEntity.get("pushmoney_rate").toString();
			if(StringUtils.isNotBlank(tempCompletionRate) && StringUtils.isNotBlank(tempPushmontyRate)){
				BigDecimal temp1 = new BigDecimal(tempCompletionRate);
				double temp2 = Double.valueOf(tempPushmontyRate);
				if(completionRate!=null){
					if(completionRate.compareTo(temp1)==0 || completionRate.compareTo(temp1)==1){
						pushmoneyRate = temp2/100;
						tempEntity.put("class_active", true);
						break;
					}
				}
			}
		}
		
		model.addAttribute("pushmoneyConfigList", pushmoneyConfigList);
		
		String startDateYear = this.getYearOrMonth(startTime, 1);//开始时间年
		String startDateMonth = this.getYearOrMonth(startTime, 2);//开始时间月
		String endDateYear = this.getYearOrMonth(endTime, 1);//结束时间年
		String endDateMonth = this.getYearOrMonth(endTime, 2);//结束时间月
		
		//获取业绩总和
		BigDecimal performanceTotalAmount = this.getPerformanceTotalAmount(startDateYear, startDateMonth, endDateYear, endDateMonth, userId, roleId);
		//获取四项费用总和
		BigDecimal fourFeeTotalAmount = this.getFourFeeTotalAmount(startDateYear, startDateMonth, endDateYear, endDateMonth, userId);
		//获取市场费用总和
		BigDecimal marketFeeTotalAmount = this.getMarketFeeTotalAmount(startDateYear, startDateMonth, endDateYear, endDateMonth, userId, roleId);
		//获取提成金额
		BigDecimal pushmoneyTotalAmount = this.getPushmoneyPrice(userId, roleId, startTime, endTime, marketFeeTotalAmount, fourFeeTotalAmount, pushmoneyRate);
		
		model.addAttribute("performance_total_amount", performanceTotalAmount);
		model.addAttribute("four_fee_total_amount", fourFeeTotalAmount);
		model.addAttribute("market_fee_total_amount", marketFeeTotalAmount);
		model.addAttribute("pushmoney_total_amount", pushmoneyTotalAmount);
		
		return RESPONSE_THYMELEAF_BACK + "pushmoney_table";
	}
	
	/**
	 * 获取业绩总金额
	 * @param startDateYear
	 * @param startDateMonth
	 * @param endDateYear
	 * @param endDateMonth
	 * @param userId
	 * @param roleId
	 * @return
	 */
	private BigDecimal getPerformanceTotalAmount(String startDateYear, String startDateMonth, String endDateYear, String endDateMonth, Long userId, Long roleId){
		
		BigDecimal performanceTotalAmount = new BigDecimal("0.00");//业绩
		try {
			//费用类型
			List<Integer> itemTypeList=new ArrayList<>();
			itemTypeList.add(AccountItemType.PERFORMANCE_FEE);//业绩
			
			List<Map<String, Object>> accountPersonalList = accountPersonalService.getItemsByDateAndUserOrRole(startDateYear, startDateMonth, endDateYear, endDateMonth, userId, roleId, itemTypeList);
			
			for(Map<String, Object> temp : accountPersonalList){
				int type = Integer.parseInt(temp.get("type").toString());
				BigDecimal amount = new BigDecimal(temp.get("amount").toString());
				switch (type) {
				case AccountItemType.PERFORMANCE_FEE://业绩
					performanceTotalAmount = performanceTotalAmount.add(amount);
					break;
				default:
					
					break;
				}
			}

			return performanceTotalAmount;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("计算四项费用总和异常");
		}
		return new BigDecimal("0.00");
	}
	
	/**
	 * 获取四项费用总和
	 * @param startDateYear
	 * @param startDateMonth
	 * @param endDateYear
	 * @param endDateMonth
	 * @param userId
	 * @return
	 */
	private BigDecimal getFourFeeTotalAmount(String startDateYear, String startDateMonth, String endDateYear, String endDateMonth, Long userId){
		
		BigDecimal fourFeeTotalAmount = new BigDecimal("0.00");//四项费用总和
		
		BigDecimal communicationFee = new BigDecimal("0.00");//通讯费
		BigDecimal entertainmentFee = new BigDecimal("0.00");//招待费
		BigDecimal transportationFee = new BigDecimal("0.00");//交通费
		BigDecimal travelExpenseFee = new BigDecimal("0.00");//差旅费
		try {
			//费用类型
			List<Integer> itemTypeList=new ArrayList<>();
			itemTypeList.add(AccountItemType.COMMUNICATION_FEE);//通讯费
			itemTypeList.add(AccountItemType.ENTERTAINMENT_FEE);//招待费
			itemTypeList.add(AccountItemType.TRANSPORTATION_FEE);//交通费
			itemTypeList.add(AccountItemType.TRAVEL_EXPENSE_FEE);//差旅费
			
			List<Map<String, Object>> accountPersonalList = accountPersonalService.getItemsByDateAndUserOrRole(startDateYear, startDateMonth, endDateYear, endDateMonth, userId, null, itemTypeList);
			
			for(Map<String, Object> temp : accountPersonalList){
				int type = Integer.parseInt(temp.get("type").toString());
				BigDecimal amount = new BigDecimal(temp.get("amount").toString());
				switch (type) {
				case AccountItemType.COMMUNICATION_FEE://通讯费
					communicationFee = communicationFee.add(amount);
					break;
				case AccountItemType.ENTERTAINMENT_FEE://招待费
					entertainmentFee = entertainmentFee.add(amount);				
					break;
				case AccountItemType.TRANSPORTATION_FEE://交通费
					transportationFee = transportationFee.add(amount);
					break;
				case AccountItemType.TRAVEL_EXPENSE_FEE://差旅费
					travelExpenseFee = travelExpenseFee.add(amount);
					break;

				default:
					
					break;
				}
			}

			fourFeeTotalAmount = fourFeeTotalAmount.add(communicationFee);
			fourFeeTotalAmount = fourFeeTotalAmount.add(entertainmentFee);
			fourFeeTotalAmount = fourFeeTotalAmount.add(transportationFee);
			fourFeeTotalAmount = fourFeeTotalAmount.add(travelExpenseFee);
			return fourFeeTotalAmount;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("计算四项费用总和异常");
		}
		return new BigDecimal("0.00");
	}
	
	/**
	 * 获取市场费用总和
	 * @param startDateYear
	 * @param startDateMonth
	 * @param endDateYear
	 * @param endDateMonth
	 * @param userId
	 * @param roleId
	 * @return
	 */
	private BigDecimal getMarketFeeTotalAmount(String startDateYear, String startDateMonth, String endDateYear, String endDateMonth, Long userId, Long roleId){
		BigDecimal marketFeeTotalAmount = new BigDecimal("0.00");//市场费用总和
		BigDecimal marketFee = new BigDecimal("0.00");//市场费
		try {
			//费用类型
			List<Integer> itemTypeList=new ArrayList<>();
			itemTypeList.add(AccountItemType.MARKET_FEE);//市场费
			
			List<Map<String, Object>> accountPersonalList = accountPersonalService.getItemsByDateAndUserOrRole(startDateYear, startDateMonth, endDateYear, endDateMonth, userId, roleId, itemTypeList);
			
			for(Map<String, Object> temp : accountPersonalList){
				int type = Integer.parseInt(temp.get("type").toString());
				BigDecimal amount = new BigDecimal(temp.get("amount").toString());
				switch (type) {
				case AccountItemType.MARKET_FEE://市场费
					marketFee = marketFee.add(amount);
					break;

				default:
					
					break;
				}
			}
			marketFeeTotalAmount = marketFeeTotalAmount.add(marketFee);
			return marketFeeTotalAmount;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("计算市场费用总和异常");
		}
		return new BigDecimal("0.00");
	}
	
	/**
	 * 获取差价总金额
	 * @param userId
	 * @param roleId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private BigDecimal getPriceDifferenceTotalAmount(Long userId, Long roleId, String startTime, String endTime){
		//差价
		String startDateYear = this.getYearOrMonth(startTime, 1);//开始时间年
		String startDateMonth = this.getYearOrMonth(startTime, 2);//开始时间月
		String endDateYear = this.getYearOrMonth(endTime, 1);//结束时间年
		String endDateMonth = this.getYearOrMonth(endTime, 2);//结束时间月
		//费用类型
		List<Integer> itemTypeList=new ArrayList<>();
		itemTypeList.add(AccountItemType.PRICE_DIFFERENCE_FEE);//差价
		List<Map<String, Object>> accountPersonalList = accountPersonalService.getItemsByDateAndUserOrRole(startDateYear, startDateMonth, endDateYear, endDateMonth, userId, roleId, itemTypeList);
		
		BigDecimal priceDifferenceTotal = new BigDecimal("0.00");//差价总金额
		
		for(Map<String, Object> temp : accountPersonalList){
			String amount = temp.get("amount").toString();
			if(StringUtils.isNotBlank(amount)){
				priceDifferenceTotal = priceDifferenceTotal.add(new BigDecimal(amount));
			}
		}
		return priceDifferenceTotal;
	}
	
	/**
	 * 获取提成金额
	 * @param userId
	 * @param roleId
	 * @param startTime
	 * @param endTime
	 * @param marketFeeTotalAmount
	 * @param fourFeeTotalAmount
	 * @param pushmoneyRate
	 * @return
	 */
	private BigDecimal getPushmoneyPrice(Long userId, Long roleId, String startTime, String endTime, BigDecimal marketFeeTotalAmount, BigDecimal fourFeeTotalAmount, double pushmoneyRate){
		//差价总和
		BigDecimal priceDifferenceTotal = this.getPriceDifferenceTotalAmount(userId, roleId, startTime, endTime);
		
		BigDecimal tax = new BigDecimal("0.83");//增值税后
		BigDecimal pushmoney = null;//提成
		//毛利=（订单总金额-订单市场费用-订单中商品最低限价总额）* （1-17%）
		pushmoney = priceDifferenceTotal.subtract(marketFeeTotalAmount);//减市场费
		pushmoney = pushmoney.multiply(tax);//乘以增值税后
		//提成基数=销售毛利-四项费用
		pushmoney = pushmoney.subtract(fourFeeTotalAmount);//减四项费用
		
		BigDecimal pushmoneyPrice = pushmoney.multiply(BigDecimal.valueOf(pushmoneyRate));//提成金额
		pushmoneyPrice = pushmoneyPrice.setScale(2,BigDecimal.ROUND_HALF_UP);//提成金额保留两位小数
		return pushmoneyPrice;
	}
	
	/**
	 * 获取年和月
	 * @param date
	 * @param type
	 * 		1：年；2：月
	 * @return
	 */
	private String getYearOrMonth(String date, int type){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date tempStartTime = null;
		try {
			tempStartTime = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = null;
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTime(tempStartTime);
		switch (type) {
		case 1:
			//获取年
			int year = calendar.get(Calendar.YEAR);
			result = ""+year;
			break;
		case 2:
			//获取月份，0表示1月份
			int month = calendar.get(Calendar.MONTH) + 1;
			if(month<10){
				result = "0"+month;
			}else{
				result = ""+month;
			}
			break;
		default:
			break;
		}
		
		return result;
	}
	
	/*==============================================================公司业绩==============================================================*/
			
	/**
	 * @Description 显示-订单列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/company/show")
	public String order_show_company(Model model) {
		return RESPONSE_THYMELEAF_BACK_PERFORMANCE_COMPANY + "order_show";
	}

	/**
	 * @Description 订单查询
	 * @param orderTimeCond
	 *            订单时间条件
	 * @param dealStateCond
	 *            订单处理状态条件
	 * @param pageNum
	 *            页号
	 * @param pageSize
	 *            页大小
	 * @param searchTypeValue
	 *            搜索类型
	 * @param condValue
	 *            搜索条件值
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/company/ordertable")
	public String order_table_company(int orderTimeCond, int dealStateCond, Integer pageNum, Integer pageSize,
			Integer searchTypeValue, String condValue, Model model, Long userId, Long roleId, String provinceName, String cityName, String countyName) {
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
			pageSize = PAGE_SIZE;
		}

		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		List<Map<String, Object>> agentIdList = null;
		
		/*List<Role> roleList = user.getRoleList();
		boolean isISOrOS = true;
		for(Role role : roleList){
			String roleCode = role.getRoleCode();
			if(StringUtils.isNotBlank(roleCode)){
				if(roleCode.equalsIgnoreCase(RoleCodeConstants.ADMIN) || roleCode.equalsIgnoreCase(RoleCodeConstants.MANAGER) || roleCode.equalsIgnoreCase(RoleCodeConstants.BUSSMAN) || roleCode.equalsIgnoreCase(RoleCodeConstants.SALEMAN)){
					isISOrOS = false;
					break;
				}
			}
		}
		
		if(isISOrOS){
			List<Map<String, Object>> userList = userService.getById(user.getId());
			model.addAttribute("userList", userList);
			List<Role> tempRoleList = new ArrayList<>();
			for(Role role : roleList){
				String roleCode = role.getRoleCode();
				if(StringUtils.isNotBlank(roleCode)){
					if(roleCode.equalsIgnoreCase(RoleCodeConstants.IS) || roleCode.equalsIgnoreCase(RoleCodeConstants.OS)){
						tempRoleList.add(role);
					}
				}
			}
			model.addAttribute("roleList", tempRoleList);
			
			Example example = new Example(CustLockRel.class);
			if(roleList.size()>1){
				example.createCriteria().andEqualTo("bindUserId", user.getId());
			}else{
				example.createCriteria().andEqualTo("bindUserId", user.getId()).andEqualTo("roleId", roleList.get(0).getRoleId());
			}
			List<CustLockRel> tempList = agentBindService.selectByExample(example);
			for(CustLockRel temp : tempList){
				if(agentIdList==null){
					agentIdList = new ArrayList<>();
				}
				Map<String, Object> tempMap = new HashMap<>();
				Long agentId = temp.getCustId();
				if(agentId!=null && agentId>0){
					tempMap.put("cust_id", agentId);
					agentIdList.add(tempMap);
				}else{
					tempMap.put("cust_id", null);
				}
			}
		}else{
			List<Map<String, Object>> userList = userService.getISAndOSUser();
			model.addAttribute("userList", userList);
		}
		
		if(userId==null && roleId==null){
			
		}else{
			Example example = new Example(CustLockRel.class);
			if(userId==null){
				example.createCriteria().andEqualTo("roleId", roleId);
			}else if(roleId==null){
				example.createCriteria().andEqualTo("bindUserId", userId);
			}else{
				example.createCriteria().andEqualTo("bindUserId", userId).andEqualTo("roleId", roleId);
			}
			List<CustLockRel> tempList = agentBindService.selectByExample(example);
			for(CustLockRel temp : tempList){
				if(agentIdList==null){
					agentIdList = new ArrayList<>();
				}
				Map<String, Object> tempMap = new HashMap<>();
				Long agentId = temp.getCustId();
				if(agentId!=null && agentId>0){
					tempMap.put("cust_id", agentId);
					agentIdList.add(tempMap);
				}else{
					tempMap.put("cust_id", null);
				}
			}
		}*/
		agentIdList = this.judgeRole(user, model, userId, roleId);
		
		// 置默认值(搜索)
		if (searchTypeValue == null) {
			searchTypeValue = 0;
			condValue = "";
		}

		// 回传查询条件
		model.addAttribute("orderTimeCond", orderTimeCond);
		model.addAttribute("dealStateCond", dealStateCond);

		// 搜索条件类型、搜索条件值
		model.addAttribute("searchTypeValue", searchTypeValue); // 查询字段值
		model.addAttribute("condValue", condValue); // 查询条件值

		//回传区域条件及用户/角色
		model.addAttribute("provinceName", provinceName);
		model.addAttribute("cityName", cityName);
		model.addAttribute("countyName", countyName);
		model.addAttribute("userId", userId);
		model.addAttribute("roleId", roleId);
		
		// 查询 并分页
		PageHelper.startPage(pageNum, pageSize); // PageHelper

		List<Map<String, Object>> orderList = orderService.selectOrder(orderTimeCond, dealStateCond, searchTypeValue, condValue, provinceName, cityName, countyName, agentIdList); // 查询订单

		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(orderList);// (使用了拦截器或是AOP进行查询的再次处理)
		orderList = pageInfo.getList();
		
		BigDecimal orderTotalAmount = new BigDecimal("0.00");//订单总金额
		BigDecimal performanceTotalAmount = new BigDecimal("0.00");//业绩总金额
		BigDecimal lowestTotalAmount = new BigDecimal("0.00");//最低限价净利润总金额
		BigDecimal hardCostTotalAmount = new BigDecimal("0.00");//硬成本价净利润总金额
		
		for(Map<String, Object> order : orderList){
			String orderId = order.get("id").toString();
			String orderNo = order.get("order_id").toString();
			String totalPrice = order.get("total_price").toString();
			String contractState = order.get("contract_state").toString();
			
			Map<String, BigDecimal> statistics = this.getAmountStatistics(order, orderId, orderNo, totalPrice, contractState);
			BigDecimal orderAmount = statistics.get("orderTotalAmount");//订单
			orderTotalAmount = orderTotalAmount.add(orderAmount);
			BigDecimal performanceAmount = statistics.get("performanceTotalAmount");//业绩
			performanceTotalAmount = performanceTotalAmount.add(performanceAmount);
			BigDecimal lowestAmount = statistics.get("lowestTotalAmount");//最低限价净利润
			lowestTotalAmount = lowestTotalAmount.add(lowestAmount);
			BigDecimal hardCostAmount = statistics.get("hardCostTotalAmount");//硬成本价净利润
			hardCostTotalAmount = hardCostTotalAmount.add(hardCostAmount);
		}
		
		model.addAttribute("orderTotalAmount", orderTotalAmount); //订单统计
		model.addAttribute("performanceTotalAmount", performanceTotalAmount); //业绩统计
		model.addAttribute("lowestTotalAmount", lowestTotalAmount); //最低限价净利润统计
		model.addAttribute("hardCostTotalAmount", hardCostTotalAmount); //硬成本价净利润统计
		
		model.addAttribute("pageInfo", pageInfo); // 分页
		model.addAttribute("orderList", orderList); // 列表

		return RESPONSE_THYMELEAF_BACK_PERFORMANCE_COMPANY + "order_table";
	}
	
	/**
	 * 获取订单金额、业绩、最低限价净利润、三成本价净利润，并put到Map中返回
	 * @param order
	 * @param orderId
	 * @param orderNo
	 * @param totalPrice
	 * @param contractState
	 * @return
	 */
	private Map<String, BigDecimal> getAmountStatistics(Map<String, Object> order, String orderId, String orderNo, String totalPrice, String contractState){
		
		BigDecimal orderTotalAmount = new BigDecimal("0.00");//订单
		BigDecimal performanceTotalAmount = new BigDecimal("0.00");//业绩
		BigDecimal lowestTotalAmount = new BigDecimal("0.00");//最低限价净利润
		BigDecimal hardCostTotalAmount = new BigDecimal("0.00");//硬成本价净利润
		
		BigDecimal feeTotal =  new BigDecimal("0.00");//四项费用和市场费用总和
		List<Map<String, Object>> accountCompanyList = null;//业绩/最低限价利润/硬成本价利润公司账本集合
		
		if(StringUtils.isNotBlank(orderId) && StringUtils.isNotBlank(orderNo)){
			if(StringUtils.isNotBlank(contractState) && Integer.parseInt(contractState)==ContractStateType.FINISHED){//合同状态不为空且为已完成
				//业绩
				Map<String, Object> params = new HashMap<>();
				params.put("order_id", orderId);
				params.put("account_type", AccountItemType.PERFORMANCE_FEE);
				accountCompanyList = accountCompanyService.selectAccountCompanyMap(params);
				BigDecimal performanceTotalPrice = new BigDecimal("0.00"); 
				for(Map<String, Object> temp : accountCompanyList){
					String amount = temp.get("amount").toString();
					if(StringUtils.isNotBlank(amount)){
						performanceTotalPrice = performanceTotalPrice.add(new BigDecimal(amount));
					}
				}
				order.put("performance_amount", performanceTotalPrice);
				performanceTotalAmount = performanceTotalAmount.add(performanceTotalPrice);
				//最低限价净利润
				params = new HashMap<>();
				params.put("order_id", orderId);
				params.put("account_type", AccountItemType.PRICE_DIFFERENCE_FEE);
				accountCompanyList = accountCompanyService.selectAccountCompanyMap(params);
				BigDecimal lowestTotalPrice = new BigDecimal("0.00"); 
				for(Map<String, Object> temp : accountCompanyList){
					String amount = temp.get("amount").toString();
					if(StringUtils.isNotBlank(amount)){
						lowestTotalPrice = lowestTotalPrice.add(new BigDecimal(amount));
					}
				}
				feeTotal = this.getOrderFee(order, Long.parseLong(orderId), orderNo);
				lowestTotalPrice = lowestTotalPrice.subtract(feeTotal);//该订单的最低限价净利润-该订单的四项费用和市场费用
				order.put("lowest_amount", lowestTotalPrice);
				lowestTotalAmount = lowestTotalAmount.add(lowestTotalPrice);
				//硬成本净利润
				params = new HashMap<>();
				params.put("order_id", orderId);
				params.put("account_type", AccountItemType.NET_PROFIT_FEE);
				accountCompanyList = accountCompanyService.selectAccountCompanyMap(params);
				BigDecimal hardCostTotalPrice = new BigDecimal("0.00"); 
				for(Map<String, Object> temp : accountCompanyList){
					String amount = temp.get("amount").toString();
					if(StringUtils.isNotBlank(amount)){
						hardCostTotalPrice = hardCostTotalPrice.add(new BigDecimal(amount));
					}
				}
				feeTotal = this.getOrderFee(order, Long.parseLong(orderId), orderNo);
				hardCostTotalPrice = hardCostTotalPrice.subtract(feeTotal);//该订单的硬成本价净利润-该订单的四项费用和市场费用
				order.put("hard_cost_amount", hardCostTotalPrice);
				hardCostTotalAmount = hardCostTotalAmount.add(hardCostTotalPrice);
			}else{
				order.put("performance_amount", "0.00");
				order.put("lowest_amount", "0.00");
				order.put("hard_cost_amount", "0.00");
			}
			
		}
		
		if(StringUtils.isNotBlank(totalPrice)){
			orderTotalAmount = orderTotalAmount.add(new BigDecimal(totalPrice));
		}
		
		Map<String, BigDecimal> map = new HashMap<>();
		map.put("orderTotalAmount", orderTotalAmount); //订单统计
		map.put("performanceTotalAmount", performanceTotalAmount); //业绩统计
		map.put("lowestTotalAmount", lowestTotalAmount); //最低限价净利润统计
		map.put("hardCostTotalAmount", hardCostTotalAmount); //硬成本价净利润统计
		return map;
	}

	/**
	 * 获取业绩列表
	 * 
	 * @param model
	 * @param request
	 * @param orderId
	 * @param fullYear
	 * @return
	 */
	@RequestMapping(value = "/company/get-account-company")
	public String getPerformanceCompany(Model model, HttpServletRequest request, Long orderId, String fullYear, String provinceName, String cityName, String countyName, int accountType) {

		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();

		String startTime = null;
		String endTime = null;
		if(StringUtils.isNotBlank(fullYear)){
			int currYear = Integer.parseInt(fullYear);
			startTime = this.getCurrYearFirst(currYear);
			System.out.println("====================== "+fullYear+"年第一天日期第一秒："+startTime);
			endTime = this.getCurrYearLast(currYear);
			System.out.println("====================== "+fullYear+"年最后一天最后一秒："+endTime);
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("order_id", orderId);
		params.put("start_time", startTime);
		params.put("end_time", endTime);
		params.put("provinceName", provinceName);
		params.put("cityName", cityName);
		params.put("countyName", countyName);
		params.put("account_type", accountType);
		
		List<Map<String, Object>> orderList = orderService.selectOrdersMap(params);
		List<Map<String, Object>> accountCompanyList = accountCompanyService.selectAccountCompanyMap(params);
		
		BigDecimal totalAmount = new BigDecimal("0.00");//业绩/最低限价/硬成本价总金额
		BigDecimal feeTotalAmount = new BigDecimal("0.00");//四项费用+市场费用总金额
		BigDecimal profitTotalAmount = new BigDecimal("0.00");//利润总金额
		
		for(int i=0; i<orderList.size(); i++){
			Map<String, Object> orderMap = orderList.get(i);
			String tempOrderId = orderMap.get("order_id").toString();
			String tempOrderNo = orderMap.get("order_no").toString();
			String contractState = orderMap.get("contract_status").toString();
			Map<String, BigDecimal> amount = this.getAccountCompanyAmount(orderMap, accountCompanyList, accountType, tempOrderId, tempOrderNo, contractState);
			BigDecimal tempTotalAmount = amount.get("total_amount");
			totalAmount = totalAmount.add(tempTotalAmount);
			BigDecimal tempFeeTotalAmount = amount.get("fee_total_amount");
			feeTotalAmount = feeTotalAmount.add(tempFeeTotalAmount);
			BigDecimal tempProfitTotalAmount = amount.get("profit_total_amount");
			profitTotalAmount = profitTotalAmount.add(tempProfitTotalAmount);
		}
		
		model.addAttribute("accountType", accountType);
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("total_amount", totalAmount);
		model.addAttribute("fee_total_amount", feeTotalAmount);
		model.addAttribute("profit_total_amount", profitTotalAmount);
		
		return RESPONSE_THYMELEAF_BACK_PERFORMANCE_COMPANY + "performance_table";
	}
	
	/**
	 * 获取业绩/最低限价/硬成本价、四项费用+市场费用、利润金额，并put到Map中返回
	 * @param orderMap
	 * @param accountCompanyList
	 * @param accountType
	 * @param tempOrderId
	 * @param tempOrderNo
	 * @return
	 */
	private Map<String, BigDecimal> getAccountCompanyAmount(Map<String, Object> orderMap, List<Map<String, Object>> accountCompanyList, int accountType, String tempOrderId, String tempOrderNo, String contractState){
		
		BigDecimal totalAmount = new BigDecimal("0.00");//业绩/最低限价/硬成本价金额
		BigDecimal feeTotalAmount = new BigDecimal("0.00");//四项费用+市场费用金额
		BigDecimal profitTotalAmount = new BigDecimal("0.00");//利润金额
		List<Map<String, Object>> orderAccountCompanyList = new ArrayList<>();//订单公司账本集合
		
		if(StringUtils.isNotBlank(tempOrderId) && StringUtils.isNotBlank(tempOrderNo)){
			if(StringUtils.isNotBlank(contractState) && Integer.parseInt(contractState)==ContractStateType.FINISHED){//合同状态不为空且为已完成
				BigDecimal profitAmount = new BigDecimal("0.00");//该订单的净利润金额
				BigDecimal fee = this.getOrderFee(orderMap, Long.parseLong(tempOrderId), tempOrderNo);
				switch (accountType) {
					case AccountItemType.PERFORMANCE_FEE://业绩
						orderAccountCompanyList = new ArrayList<>();
						for(Map<String, Object> temp : accountCompanyList){
							String currOrderId = temp.get("order_id").toString();
							if(StringUtils.isNotBlank(currOrderId)){
								if(tempOrderId.equalsIgnoreCase(currOrderId)){
									String amount = temp.get("amount").toString();
									if(StringUtils.isNotBlank(amount)){
										totalAmount = totalAmount.add(new BigDecimal(amount));
									}
									orderAccountCompanyList.add(temp);
								}
							}
							
						}
						orderMap.put("order_account_company_list", orderAccountCompanyList);
					break;
					case AccountItemType.PRICE_DIFFERENCE_FEE://差价
					case AccountItemType.NET_PROFIT_FEE://纯利润
						orderAccountCompanyList = new ArrayList<>();
						for(Map<String, Object> temp : accountCompanyList){
							String currOrderId = temp.get("order_id").toString();
							if(StringUtils.isNotBlank(currOrderId)){
								if(tempOrderId.equalsIgnoreCase(currOrderId)){
									String amount = temp.get("amount").toString();
									if(StringUtils.isNotBlank(amount)){
										BigDecimal tempAmount = new BigDecimal(amount);
										totalAmount = totalAmount.add(tempAmount);
										profitAmount = profitAmount.add(tempAmount);
									}
									orderAccountCompanyList.add(temp);
								}
							}
						}
						orderMap.put("order_account_company_list", orderAccountCompanyList);
						orderMap.put("fee_total", fee);
						feeTotalAmount = feeTotalAmount.add(fee);
						profitAmount = profitAmount.subtract(fee);
						orderMap.put("profit_amount", profitAmount);
						profitTotalAmount = profitTotalAmount.add(profitAmount);
					break;
		
				default:
					break;
				}
			}else{
				orderMap.put("communication_fee", "0.00");//通讯费
				orderMap.put("entertainment_fee", "0.00");//招待费
				orderMap.put("transportation_fee", "0.00");//交通费
				orderMap.put("travel_expense_fee", "0.00");//差旅费
				orderMap.put("market_fee", "0.00");//市场费
				
				orderMap.put("order_account_company_list", orderAccountCompanyList);
				orderMap.put("fee_total", "0.00");
				orderMap.put("profit_amount", "0.00");
			}
		}
		
		Map<String, BigDecimal> map = new HashMap<>();
		map.put("total_amount", totalAmount);
		map.put("fee_total_amount", feeTotalAmount);
		map.put("profit_total_amount", profitTotalAmount);
		return map;
	}
	
	/**
	 * 计算四项费用和市场费用，并put到Map中，返回四项费用和市场费用总和
	 * @param orderMap
	 * @param orderId
	 * @param orderNo
	 * @return
	 */
	private BigDecimal getOrderFee(Map<String, Object> orderMap, Long orderId, String orderNo){
		BigDecimal communication_fee = new BigDecimal("0.00");//通讯费
		BigDecimal entertainment_fee = new BigDecimal("0.00");//招待费
		BigDecimal transportation_fee = new BigDecimal("0.00");//交通费
		BigDecimal travel_expense_fee = new BigDecimal("0.00");//差旅费
		BigDecimal market_fee = new BigDecimal("0.00");//市场费
		try {
			//费用类型
			List<Integer> itemTypeList=new ArrayList<>();
			itemTypeList.add(AccountItemType.COMMUNICATION_FEE);//通讯费
			itemTypeList.add(AccountItemType.ENTERTAINMENT_FEE);//招待费
			itemTypeList.add(AccountItemType.TRANSPORTATION_FEE);//交通费
			itemTypeList.add(AccountItemType.TRAVEL_EXPENSE_FEE);//差旅费
			itemTypeList.add(AccountItemType.MARKET_FEE);//市场费
			
			List<AccountCompany> tempList = accountCompanyService.getItemsByOrder(orderId, orderNo, itemTypeList);
			
			for(AccountCompany temp : tempList){
				int type = temp.getType();
				BigDecimal amount = temp.getAmount();
				switch (type) {
				case AccountItemType.COMMUNICATION_FEE://通讯费
					communication_fee = communication_fee.add(amount);
					break;
				case AccountItemType.ENTERTAINMENT_FEE://招待费
					entertainment_fee = entertainment_fee.add(amount);				
					break;
				case AccountItemType.TRANSPORTATION_FEE://交通费
					transportation_fee = transportation_fee.add(amount);
					break;
				case AccountItemType.TRAVEL_EXPENSE_FEE://差旅费
					travel_expense_fee = travel_expense_fee.add(amount);
					break;
				case AccountItemType.MARKET_FEE://市场费
					market_fee = market_fee.add(amount);
					break;

				default:
					
					break;
				}
			}

			orderMap.put("communication_fee", communication_fee);//通讯费
			orderMap.put("entertainment_fee", entertainment_fee);//招待费
			orderMap.put("transportation_fee", transportation_fee);//交通费
			orderMap.put("travel_expense_fee", travel_expense_fee);//差旅费
			orderMap.put("market_fee", market_fee);//市场费
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("计算四项费用和市场费用异常");
		}
		BigDecimal fee = new BigDecimal("0.00");
		fee = fee.add(communication_fee);
		fee = fee.add(entertainment_fee);
		fee = fee.add(transportation_fee);
		fee = fee.add(travel_expense_fee);
		fee = fee.add(market_fee);
		return fee;
	}
	
	/**
	 * 暂时未用
	 * @param user
	 * @param model
	 * @param userId
	 * @param roleId
	 * @return
	 */
	private List<Map<String, Object>> judgeRole(UserBean user, Model model, Long userId, Long roleId){
		
		List<Map<String, Object>> agentIdList = null;
		
		List<Role> roleList = user.getRoleList();
		boolean isISOrOS = true;
		for(Role role : roleList){
			String roleCode = role.getRoleCode();
			if(StringUtils.isNotBlank(roleCode)){
				if(roleCode.equalsIgnoreCase(RoleCodeConstants.ADMIN) || roleCode.equalsIgnoreCase(RoleCodeConstants.MANAGER) || roleCode.equalsIgnoreCase(RoleCodeConstants.BUSSMAN) || roleCode.equalsIgnoreCase(RoleCodeConstants.SALEMAN)){
					isISOrOS = false;
					break;
				}
			}
		}
		
		if(isISOrOS){
			List<Map<String, Object>> userList = userService.getById(user.getId());
			model.addAttribute("userList", userList);
			List<Role> tempRoleList = new ArrayList<>();
			for(Role role : roleList){
				String roleCode = role.getRoleCode();
				if(StringUtils.isNotBlank(roleCode)){
					if(roleCode.equalsIgnoreCase(RoleCodeConstants.IS) || roleCode.equalsIgnoreCase(RoleCodeConstants.OS)){
						tempRoleList.add(role);
					}
				}
			}
			model.addAttribute("roleList", tempRoleList);
			
			Example example = new Example(CustLockRel.class);
			if(roleList.size()>1){
				example.createCriteria().andEqualTo("bindUserId", user.getId());
			}else{
				example.createCriteria().andEqualTo("bindUserId", user.getId()).andEqualTo("roleId", roleList.get(0).getRoleId());
			}
			List<CustLockRel> tempList = agentBindService.selectByExample(example);
			for(CustLockRel temp : tempList){
				if(agentIdList==null){
					agentIdList = new ArrayList<>();
				}
				Map<String, Object> tempMap = new HashMap<>();
				Long agentId = temp.getCustId();
				if(agentId!=null && agentId>0){
					tempMap.put("cust_id", agentId);
					agentIdList.add(tempMap);
				}else{
					tempMap.put("cust_id", null);
				}
			}
		}else{
			List<Map<String, Object>> userList = userService.getISAndOSUser();
			model.addAttribute("userList", userList);
		}
		
		if(userId==null && roleId==null){
			
		}else{
			Example example = new Example(CustLockRel.class);
			if(userId==null){
				example.createCriteria().andEqualTo("roleId", roleId);
			}else if(roleId==null){
				example.createCriteria().andEqualTo("bindUserId", userId);
			}else{
				example.createCriteria().andEqualTo("bindUserId", userId).andEqualTo("roleId", roleId);
			}
			List<CustLockRel> tempList = agentBindService.selectByExample(example);
			for(CustLockRel temp : tempList){
				if(agentIdList==null){
					agentIdList = new ArrayList<>();
				}
				Map<String, Object> tempMap = new HashMap<>();
				Long agentId = temp.getCustId();
				if(agentId!=null && agentId>0){
					tempMap.put("cust_id", agentId);
					agentIdList.add(tempMap);
				}else{
					tempMap.put("cust_id", null);
				}
			}
		}
		return agentIdList;
	}
	
	public static void main(String[] args) {
        BigDecimal decimal = new BigDecimal("1.12345");
        System.out.println(decimal);
        BigDecimal setScale = decimal.setScale(4,BigDecimal.ROUND_HALF_DOWN);
        System.out.println(setScale);
        
        BigDecimal setScale1 = decimal.setScale(4,BigDecimal.ROUND_HALF_UP);
        System.out.println(setScale1);
        BigDecimal setScale2 = decimal.setScale(2,BigDecimal.ROUND_HALF_UP);
        System.out.println(setScale2);
    }
	
}
