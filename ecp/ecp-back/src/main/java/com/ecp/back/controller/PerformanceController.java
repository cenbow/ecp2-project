package com.ecp.back.controller;

import java.math.BigDecimal;
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
import com.ecp.bean.UserBean;
import com.ecp.entity.AccountCompany;
import com.ecp.entity.CustLockRel;
import com.ecp.entity.Orders;
import com.ecp.entity.PushmoneyConfig;
import com.ecp.entity.Role;
import com.ecp.entity.User;
import com.ecp.entity.UserExtends;
import com.ecp.service.back.IPushmoneyConfigService;
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
	final String RESPONSE_THYMELEAF_BACK = "back/thymeleaf/performance/";

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
			Integer searchTypeValue, String condValue, Model model, Long userId, Long roleId) {
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
			pageSize = PAGE_SIZE;
		}

		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		List<Long> agentIdList = null;
		
		if(userId==null && roleId==null){
			List<Role> roleList = user.getRoleList();
			boolean isISOrOS = false;
			for(Role role : roleList){
				String roleCode = role.getRoleCode();
				if(StringUtils.isNotBlank(roleCode)){
					if(roleCode.equalsIgnoreCase(RoleCodeConstants.IS) || roleCode.equalsIgnoreCase(RoleCodeConstants.OS)){
						isISOrOS = true;
						User tempUser = userService.selectByPrimaryKey(user.getId());
						List<User> userList = new ArrayList<>();
						userList.add(tempUser);
						model.addAttribute("userList", userList);
						model.addAttribute("roleList", roleList);
						
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
							Long agentId = temp.getCustId();
							if(agentId!=null && agentId>0){
								agentIdList.add(agentId);
							}
						}
						break;
					}else if(roleCode.equalsIgnoreCase(RoleCodeConstants.ADMIN) || roleCode.equalsIgnoreCase(RoleCodeConstants.MANAGER) || roleCode.equalsIgnoreCase(RoleCodeConstants.BUSSMAN) || roleCode.equalsIgnoreCase(RoleCodeConstants.SALEMAN)){
						isISOrOS = false;
					}
				}
			}
			
			if(!isISOrOS){
				Example example = new Example(User.class);
				example.createCriteria().andEqualTo("deleted", 1).andEqualTo("type", 1).andNotEqualTo("username", "admin");
				List<User> userList = userService.selectByExample(example);
				model.addAttribute("userList", userList);
			}
		}else{
			Example example = new Example(CustLockRel.class);
			if(userId==null){
				example.createCriteria().andEqualTo("roleId", roleId);
			}else if(roleId==null){
				example.createCriteria().andEqualTo("bindUserId", user.getId());
			}else{
				example.createCriteria().andEqualTo("bindUserId", user.getId()).andEqualTo("roleId", roleId);
			}
			List<CustLockRel> tempList = agentBindService.selectByExample(example);
			for(CustLockRel temp : tempList){
				if(agentIdList==null){
					agentIdList = new ArrayList<>();
				}
				Long agentId = temp.getCustId();
				if(agentId!=null && agentId>0){
					agentIdList.add(agentId);
				}
			}
		}
		
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

		// 查询 并分页
		PageHelper.startPage(pageNum, pageSize); // PageHelper

		// List<Map<String,Object>> orderList =
		// orderService.selectAllOrderByOrderTimeAndDealState(orderTimeCond,dealStateCond);

		List<Map<String, Object>> orderList = orderService.selectOrders(agentIdList, orderTimeCond, dealStateCond, searchTypeValue, condValue); // 查询订单

		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(orderList);// (使用了拦截器或是AOP进行查询的再次处理)

		model.addAttribute("pageInfo", pageInfo); // 分页
		model.addAttribute("orderList", orderList); // 列表

		return RESPONSE_THYMELEAF_BACK + "order_table";
	}

	/**
	 * @Description 订单详情（后台）
	 * @param id
	 *            订单主键（pk）
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public String order_detail(Long id, Model model, HttpServletRequest request) {

		model.addAttribute("orderId", id); // 向订单详细table传递参数

		return RESPONSE_THYMELEAF_BACK + "order_detail";
	}

	/**
	 * @Description 合同详情（后台）
	 * @param id
	 *            合同id（pk）
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/contractdetail")
	public String order_contract_detail(Long id, Model model, HttpServletRequest request) {

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
	public String order_detail_table(Long id, Model model, HttpServletRequest request) {
		// return "forward:"+RESPONSE_FRONT + "order/"+"detailtable";
		// (1)读取订单
		Orders order = orderService.selectByPrimaryKey(id);
		// (2)读取订单商品列表
		List<Map<String, String>> orderItems = orderItemService.selectItemsByOrderId(order.getOrderId());
		// (3)收货人信息(此信息已经保存至订单中)
		// (4)代理商信息
		// UserExtends
		// agent=userAgentService.selectByPrimaryKey(order.getBuyerId());
		UserExtends agent = userAgentService.getUserAgentByUserId(order.getBuyerId());

		model.addAttribute("order", order);
		model.addAttribute("orderItems", orderItems);
		model.addAttribute("agent", agent);

		return RESPONSE_THYMELEAF_BACK + "order_detail_table";
	}

	/*
	 * =============================================================以下是业绩相关内容===
	 * ==========================================================
	 */

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
	public String getPerformance(Model model, HttpServletRequest request, Long orderId, String fullYear, Long userId, Long roleId) {

		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();

		List<String> roleCodeList = new ArrayList<>();
		//if(userId==null && roleId==null){
			List<Role> roleList = user.getRoleList();
			
			boolean isISOrOS = false;
			for(Role role : roleList){
				String roleCode = role.getRoleCode();
				if(StringUtils.isNotBlank(roleCode)){
					if(roleCode.equalsIgnoreCase(RoleCodeConstants.IS) || roleCode.equalsIgnoreCase(RoleCodeConstants.OS)){
						isISOrOS = true;
						break;
					}else if(roleCode.equalsIgnoreCase(RoleCodeConstants.ADMIN) || roleCode.equalsIgnoreCase(RoleCodeConstants.MANAGER) || roleCode.equalsIgnoreCase(RoleCodeConstants.BUSSMAN) || roleCode.equalsIgnoreCase(RoleCodeConstants.SALEMAN)){
						isISOrOS = false;
					}
				}
			}
			
			if(isISOrOS){
				for(Role role : roleList){
					String roleCode = role.getRoleCode();
					if(StringUtils.isNotBlank(roleCode)){
						roleCodeList.add(roleCode);
					}
				}
				userId = user.getId();
			}
		/*}else{
			
			if(userId==null){
				
			}else if(roleId==null){
				
			}else{
				
			}
		}*/
		
		
		
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
		params.put("user_id", userId);
		params.put("role_code_list", roleCodeList);
		params.put("start_time", startTime);
		params.put("end_time", endTime);
		List<Map<String, Object>> performanceList = accountPersonalService.selectPerformanceMap(params);

		model.addAttribute("performanceList", performanceList);

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
	public String getSalesProgress(Model model, HttpServletRequest request, Long orderId, String fullYear) {

		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		List<Role> roleList = user.getRoleList();
		
		boolean isISOrOS = false;
		for(Role role : roleList){
			String roleCode = role.getRoleCode();
			if(StringUtils.isNotBlank(roleCode)){
				if(roleCode.equalsIgnoreCase(RoleCodeConstants.IS) || roleCode.equalsIgnoreCase(RoleCodeConstants.OS)){
					isISOrOS = true;
					break;
				}else if(roleCode.equalsIgnoreCase(RoleCodeConstants.ADMIN) || roleCode.equalsIgnoreCase(RoleCodeConstants.MANAGER) || roleCode.equalsIgnoreCase(RoleCodeConstants.BUSSMAN) || roleCode.equalsIgnoreCase(RoleCodeConstants.SALEMAN)){
					isISOrOS = false;
				}
			}
		}
		
		Long userId = null;
		List<Long> roleIdList = new ArrayList<>();
		if(isISOrOS){
			for(Role role : roleList){
				Long roleId = role.getRoleId();
				if(roleId!=null && roleId>0){
					roleIdList.add(roleId);
				}
			}
			userId = user.getId();
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("user_id", userId);
		params.put("role_id_list", roleIdList);
		params.put("year_name", fullYear);
		List<Map<String, Object>> salesProgressList = salesTargetService.selectSalesTargetMap(params);
		
		Iterator<Map<String, Object>> it = salesProgressList.iterator();
		while (it.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) it.next();
			String startTime = map.get("start_date").toString();
			String endTime = map.get("end_date").toString();
			Map<String, Object> tempParams = new HashMap<>();
			//tempParams.put("order_no", orderId);
			tempParams.put("start_time", startTime);
			tempParams.put("end_time", endTime);
			BigDecimal totalPrice = contractItemsService.selectContractPayPriceTotal(tempParams);
			map.put("total_price", totalPrice);
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
	public String getPushmoney(Model model, HttpServletRequest request, String fullYear, Long userId, Long roleId, String startTime, String endTime, BigDecimal completionRate) {

		//查询该用户角色下的代理商
		Example example = new Example(CustLockRel.class);
		example.createCriteria().andEqualTo("bindUserId", userId).andEqualTo("roleId", roleId);
		List<CustLockRel> tempList = agentBindService.selectByExample(example);
		List<Long> agentIdList = new ArrayList<>();
		for(CustLockRel temp : tempList){
			Long agentId = temp.getCustId();
			if(agentId!=null && agentId>0){
				agentIdList.add(agentId);
			}
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("agent_id_list", agentIdList);
		params.put("start_time", startTime);
		params.put("end_time", endTime);
		
		List<Map<String, Object>> contractItemsList = contractItemsService.selectContractItems(params);
		model.addAttribute("contractItemsList", contractItemsList);
		
		List<Map<String, Object>> contractList = contractService.selectContract(params);
		Iterator<Map<String, Object>> it = contractList.iterator();
		while (it.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) it.next();
			String orderId = map.get("order_id").toString();
			String orderNo = map.get("order_no").toString();
			if(StringUtils.isNotBlank(orderId) && StringUtils.isNotBlank(orderNo)){
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
					
					List<AccountCompany> accountCompanyList = accountCompanyService.getItemsByOrder(Long.parseLong(orderId), orderNo, itemTypeList);
					
					for(AccountCompany temp : accountCompanyList){
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

					map.put("communication_fee", communication_fee);//通讯费
					map.put("entertainment_fee", entertainment_fee);//招待费
					map.put("transportation_fee", transportation_fee);//交通费
					map.put("travel_expense_fee", travel_expense_fee);//差旅费
					map.put("market_fee", market_fee);//市场费
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("计算四项费用和市场费用异常");
				}
				
				//差价
				AccountCompany temp = new AccountCompany();
				temp.setOrderId(Long.parseLong(orderId));
				temp.setOrderNo(orderNo);
				temp.setType(AccountItemType.PRICE_DIFFERENCE_FEE);
				List<AccountCompany> accountCompanyList = accountCompanyService.select(temp);
				if(accountCompanyList!=null && !accountCompanyList.isEmpty()){
					temp = accountCompanyList.get(0);
				}
				BigDecimal price_difference = temp.getAmount();//差价
				BigDecimal tax = new BigDecimal("0.83");//增值税后
				BigDecimal pushmoney = null;//提成
				//毛利=（订单总金额-订单市场费用-订单中商品最低限价总额）* （1-17%）
				pushmoney = price_difference.subtract(market_fee);
				pushmoney = pushmoney.multiply(tax);
				//提成基数=销售毛利-四项费用
				pushmoney = pushmoney.subtract(communication_fee);//通讯费
				pushmoney = pushmoney.subtract(entertainment_fee);//招待费
				pushmoney = pushmoney.subtract(transportation_fee);//交通费
				pushmoney = pushmoney.subtract(travel_expense_fee);//差旅费
				
				//completionRate
				example = new Example(PushmoneyConfig.class);
				example.createCriteria().andEqualTo("roleId", roleId);
				example.setOrderByClause("completion_rate desc");
				List<PushmoneyConfig> pushmoneyConfigList = pushmoneyConfigService.selectByExample(example);
				double pushmoneyRate = 0d;//提成比例
				for(PushmoneyConfig tempEntity : pushmoneyConfigList){
					Integer tempCompletionRate = tempEntity.getCompletionRate();
					Integer tempPushmontyRate = tempEntity.getPushmoneyRate();
					if(tempCompletionRate!=null && tempPushmontyRate!=null){
						BigDecimal temp1 = new BigDecimal(tempCompletionRate);
						double temp2 = tempPushmontyRate.doubleValue();
						if(completionRate!=null){
							if(completionRate.compareTo(temp1)==0 || completionRate.compareTo(temp1)==1){
								pushmoneyRate = temp2/100;
								break;
							}
						}
					}
				}
				
				BigDecimal pushmoneyPrice = pushmoney.multiply(BigDecimal.valueOf(pushmoneyRate));//提成金额
				pushmoneyPrice = pushmoneyPrice.setScale(2);//提成金额保留两位小数
				
				map.put("pushmoney_price", pushmoneyPrice);
				
				/*List<Map<String, Object>> tempList1 = new ArrayList<>();
				for(Map<String, Object> temp : contractItemsList){
					String order_no = temp.get("order_id").toString();
					if(orderNo.equalsIgnoreCase(order_no)){
						tempList1.add(temp);
					}
				}
				map.put("contractItemsList", tempList1);*/
			}
			
		}
		model.addAttribute("contractList", contractList);
		
		return RESPONSE_THYMELEAF_BACK + "pushmoney_table";
	}
	
}
