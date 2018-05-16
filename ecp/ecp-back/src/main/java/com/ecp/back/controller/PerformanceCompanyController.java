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
 * @ClassName PerformanceCompanyController
 * @Description 公司业绩
 * @author Administrator
 * @Date 2017年6月27日 上午9:55:24
 * @version 1.0.0
 */
@Controller
@RequestMapping("/back/performance-company")
public class PerformanceCompanyController {
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
	 * @Description 显示
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/show")
	public String showPerformanceCompany(Model model) {
		return RESPONSE_THYMELEAF_BACK_PERFORMANCE_COMPANY + "performance_company";
	}

	/**
	 * 获取公司业绩列表
	 * @param model
	 * @param request
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/get-performance-company")
	public String getPerformanceCompany(Model model, HttpServletRequest request, String startDate, String endDate) {

		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		String startDateYear = null;//开始时间年
		String startDateMonth = null;//开始时间月
		String endDateYear = null;//结束时间年
		String endDateMonth = null;//结束时间月
		
		if(StringUtils.isNotBlank(startDate)){
			String[] startDateArr = startDate.split("-");
			startDateYear = startDateArr[0];//开始时间年
			startDateMonth = startDateArr[1];//开始时间月
		}
		if(StringUtils.isNotBlank(endDate)){
			String[] endDateArr = endDate.split("-");
			endDateYear = endDateArr[0];//结束时间年
			endDateMonth = endDateArr[1];//结束时间月
		}
		
		//费用类型
		List<Integer> itemTypeList=new ArrayList<>();
		itemTypeList.add(AccountItemType.PERFORMANCE_FEE);//业绩
		
		List<Map<String, Object>> performanceList = accountCompanyService.getItemsByDate(startDateYear, startDateMonth, endDateYear, endDateMonth, itemTypeList);

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
		
		model.addAttribute("performanceList", performanceList);
		model.addAttribute("performanceTotalAmount", performanceTotalAmount);
		model.addAttribute("orderTotalAmount", orderTotalAmount);
		model.addAttribute("contractTotalAmount", contractTotalAmount);
		
		return RESPONSE_THYMELEAF_BACK_PERFORMANCE_COMPANY + "performance_table";
	}
	
	/**
	 * 获取最低限价利润列表
	 * @param model
	 * @param request
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/get-lowest-price-profit")
	public String getLowestPriceProfit(Model model, HttpServletRequest request, String startDate, String endDate) {

		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		String startDateYear = null;//开始时间年
		String startDateMonth = null;//开始时间月
		String endDateYear = null;//结束时间年
		String endDateMonth = null;//结束时间月
		
		if(StringUtils.isNotBlank(startDate)){
			String[] startDateArr = startDate.split("-");
			startDateYear = startDateArr[0];//开始时间年
			startDateMonth = startDateArr[1];//开始时间月
		}
		if(StringUtils.isNotBlank(endDate)){
			String[] endDateArr = endDate.split("-");
			endDateYear = endDateArr[0];//结束时间年
			endDateMonth = endDateArr[1];//结束时间月
		}
		
		//费用类型
		List<Integer> itemTypeList=new ArrayList<>();
		itemTypeList.add(AccountItemType.PRICE_DIFFERENCE_FEE);//最低限价差价
		
		List<Map<String, Object>> performanceList = accountCompanyService.getItemsByDate(startDateYear, startDateMonth, endDateYear, endDateMonth, itemTypeList);

		BigDecimal lowestPriceDifferTotalAmount = new BigDecimal("0.00");//最低限价差价总金额
		BigDecimal orderTotalAmount = new BigDecimal("0.00");//订单总金额
		BigDecimal contractTotalAmount = new BigDecimal("0.00");//合同总金额
		BigDecimal marketFeeTotalAmount = new BigDecimal("0.00");//市场费总金额
		BigDecimal profitAmount = new BigDecimal("0.00");//利润金额
		
		for(Map<String, Object> temp : performanceList){
			String amount = temp.get("amount").toString();
			if(StringUtils.isNotBlank(amount)){
				lowestPriceDifferTotalAmount = lowestPriceDifferTotalAmount.add(new BigDecimal(amount));
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
			
			String orderId = temp.get("order_id").toString();
			if(StringUtils.isNotBlank(orderId)){
				BigDecimal marketFee = accountCompanyService.getAmountByOrderId(Long.parseLong(orderId), AccountItemType.MARKET_FEE);//查询市场费
				temp.put("marketFee", marketFee);
				marketFeeTotalAmount = marketFeeTotalAmount.add(marketFee);
			}else{
				temp.put("marketFee", "0.00");
			}
			
		}
		
		//费用类型
		List<Integer> accountItemTypeList=new ArrayList<>();
		accountItemTypeList.add(AccountItemType.TRANSPORTATION_FEE);//交通费
		accountItemTypeList.add(AccountItemType.COMMUNICATION_FEE);//通讯费
		accountItemTypeList.add(AccountItemType.ENTERTAINMENT_FEE);//招待费
		accountItemTypeList.add(AccountItemType.TRAVEL_EXPENSE_FEE);//差旅费
		accountItemTypeList.add(AccountItemType.OTHER_FEE);//其它费用
		
		BigDecimal fourFeeTotalAmount = accountCompanyService.getItemsSumByDateOrUser(startDateYear, startDateMonth, endDateYear, endDateMonth, null, accountItemTypeList);
		model.addAttribute("fourFeeTotalAmount", fourFeeTotalAmount);
		int itemSize = 0;//最低限价列表条目数量，用于页面合并单元格
		if(performanceList!=null && !performanceList.isEmpty()){
			itemSize = performanceList.size();
		}
		model.addAttribute("itemSize", itemSize);
		
		model.addAttribute("performanceList", performanceList);
		model.addAttribute("lowestPriceDifferTotalAmount", lowestPriceDifferTotalAmount);
		model.addAttribute("orderTotalAmount", orderTotalAmount);
		model.addAttribute("contractTotalAmount", contractTotalAmount);
		model.addAttribute("marketFeeTotalAmount", marketFeeTotalAmount);
		
		profitAmount = lowestPriceDifferTotalAmount.subtract(marketFeeTotalAmount);//差价减四项费
		profitAmount = profitAmount.subtract(fourFeeTotalAmount);//差价减四项费再减四项费
		
		model.addAttribute("profitAmount", profitAmount);
		
		return RESPONSE_THYMELEAF_BACK_PERFORMANCE_COMPANY + "lowest_price_profit_table";
	}
	
	/**
	 * 获取硬成本价利润列表
	 * @param model
	 * @param request
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/get-hard-cost-price-profit")
	public String getHardCostPriceProfit(Model model, HttpServletRequest request, String startDate, String endDate) {

		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		String startDateYear = null;//开始时间年
		String startDateMonth = null;//开始时间月
		String endDateYear = null;//结束时间年
		String endDateMonth = null;//结束时间月
		
		if(StringUtils.isNotBlank(startDate)){
			String[] startDateArr = startDate.split("-");
			startDateYear = startDateArr[0];//开始时间年
			startDateMonth = startDateArr[1];//开始时间月
		}
		if(StringUtils.isNotBlank(endDate)){
			String[] endDateArr = endDate.split("-");
			endDateYear = endDateArr[0];//结束时间年
			endDateMonth = endDateArr[1];//结束时间月
		}
		
		//费用类型
		List<Integer> itemTypeList=new ArrayList<>();
		itemTypeList.add(AccountItemType.NET_PROFIT_FEE);//硬成本差价
		
		List<Map<String, Object>> performanceList = accountCompanyService.getItemsByDate(startDateYear, startDateMonth, endDateYear, endDateMonth, itemTypeList);

		BigDecimal hardCostPriceDifferTotalAmount = new BigDecimal("0.00");//硬成本价差价总金额
		BigDecimal orderTotalAmount = new BigDecimal("0.00");//订单总金额
		BigDecimal contractTotalAmount = new BigDecimal("0.00");//合同总金额
		BigDecimal marketFeeTotalAmount = new BigDecimal("0.00");//市场费总金额
		BigDecimal profitAmount = new BigDecimal("0.00");//利润金额
		
		for(Map<String, Object> temp : performanceList){
			String amount = temp.get("amount").toString();
			if(StringUtils.isNotBlank(amount)){
				hardCostPriceDifferTotalAmount = hardCostPriceDifferTotalAmount.add(new BigDecimal(amount));
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
			
			String orderId = temp.get("order_id").toString();
			if(StringUtils.isNotBlank(orderId)){
				BigDecimal marketFee = accountCompanyService.getAmountByOrderId(Long.parseLong(orderId), AccountItemType.MARKET_FEE);//查询市场费
				temp.put("marketFee", marketFee);
				marketFeeTotalAmount = marketFeeTotalAmount.add(marketFee);
			}else{
				temp.put("marketFee", "0.00");
			}
			
		}
		
		//费用类型
		List<Integer> accountItemTypeList=new ArrayList<>();
		accountItemTypeList.add(AccountItemType.TRANSPORTATION_FEE);//交通费
		accountItemTypeList.add(AccountItemType.COMMUNICATION_FEE);//通讯费
		accountItemTypeList.add(AccountItemType.ENTERTAINMENT_FEE);//招待费
		accountItemTypeList.add(AccountItemType.TRAVEL_EXPENSE_FEE);//差旅费
		accountItemTypeList.add(AccountItemType.OTHER_FEE);//其它费用
		
		BigDecimal fourFeeTotalAmount = accountCompanyService.getItemsSumByDateOrUser(startDateYear, startDateMonth, endDateYear, endDateMonth, null, accountItemTypeList);
		model.addAttribute("fourFeeTotalAmount", fourFeeTotalAmount);
		int itemSize = 0;//最低限价列表条目数量，用于页面合并单元格
		if(performanceList!=null && !performanceList.isEmpty()){
			itemSize = performanceList.size();
		}
		model.addAttribute("itemSize", itemSize);
		
		model.addAttribute("performanceList", performanceList);
		model.addAttribute("hardCostPriceDifferTotalAmount", hardCostPriceDifferTotalAmount);
		model.addAttribute("orderTotalAmount", orderTotalAmount);
		model.addAttribute("contractTotalAmount", contractTotalAmount);
		model.addAttribute("marketFeeTotalAmount", marketFeeTotalAmount);
		
		profitAmount = hardCostPriceDifferTotalAmount.subtract(marketFeeTotalAmount);//差价减四项费
		profitAmount = profitAmount.subtract(fourFeeTotalAmount);//差价减四项费再减四项费
		
		model.addAttribute("profitAmount", profitAmount);
		
		return RESPONSE_THYMELEAF_BACK_PERFORMANCE_COMPANY + "hard_cost_price_profit_table";
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
