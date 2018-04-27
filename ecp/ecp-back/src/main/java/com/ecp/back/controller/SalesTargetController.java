package com.ecp.back.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.ecp.back.commons.StaticConstants;
import com.ecp.bean.PageBean;
import com.ecp.bean.UserBean;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.CheckCycle;
import com.ecp.entity.SalesTarget;
import com.ecp.service.back.ICheckCycleService;
import com.ecp.service.back.ISalesTargetService;
import com.ecp.service.back.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Class: SalesTargetController
 * 		销售指标Controller类
 * @author srd 
 */
@Controller
@RequestMapping("/back/sales-target")
public class SalesTargetController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Resource(name="salesTargetServiceBean")
	private ISalesTargetService salesTargetService;
	@Resource(name="checkCycleServiceBean")
	private ICheckCycleService checkCycleService;
	@Resource(name="userServiceBean")
	private IUserService userService;
	
	/**
	 * 方法功能：查询列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/select-items")
	public ModelAndView selectLinkItem(HttpServletRequest request, HttpServletResponse response, Boolean clickPageBtn, PageBean pageBean, String pagehelperFun, String searchYearName, String searchUserId, String searchRoleId) {
		ModelAndView mav = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		if(StringUtils.isBlank(searchUserId)){
			searchUserId = null;
		}
		
		List<Long> roleIdList = new ArrayList<>();
		if(StringUtils.isNotBlank(searchRoleId)){
			roleIdList.add(Long.parseLong(searchRoleId));
		}else{
			roleIdList = null;
		}
		
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		Map<String, Object> map = new HashMap<>();
		map.put("year_name", searchYearName);//考核年度
		map.put("user_id", searchUserId);//用户
		map.put("role_id_list", roleIdList);//角色
		List<Map<String, Object>> salesTargetList = salesTargetService.getList(map);
		PageInfo<Map<String, Object>> pagehelper = new PageInfo<>(salesTargetList);
		
		mav.addObject("pagehelper", pagehelper);
		
		/*List<CheckCycle> checkCycleList = checkCycleService.selectAll();//查询考核周期
		mav.addObject("checkCycleList", checkCycleList);*/
		List<Map<String, Object>> userList = userService.getISAndOS();//查询IS和OS
		mav.addObject("userList", userList);
		
		if(clickPageBtn!=null && clickPageBtn){
			mav.setViewName(StaticConstants.SALES_TARGET_MANAGE_TABLE_PAGE);
		}else{
			mav.setViewName(StaticConstants.SALES_TARGET_MANAGE_PAGE);
		}
		
		mav.addObject("pagehelperFun", pagehelperFun);
		return mav;
	}
	
	/**
	 * 方法功能：查询要修改的信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/select-update")
	@ResponseBody
	public Map<String, Object> selectUpdateById(HttpServletRequest request, HttpServletResponse response, Long id) {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("sales_target_id", id);
			List<Map<String, Object>> salesTargetList = salesTargetService.selectSalesTargetMap(params);
			
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			if(salesTargetList!=null && !salesTargetList.isEmpty()){
				respM.put("salesTarget", salesTargetList.get(0));
				return respM;
			}
		} catch (Exception e) {
			log.error("查询异常", e);
		}
		return RequestResultUtil.getResultSelectWarn();
	}
	
	/**
	 * 方法功能：添加
	 * @param request
	 * @param response
	 * @param salesTarget
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response, Long checkCycleId, Long userId, Long roleId, String targetArrJSON) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		if(userBean!=null){

			List<SalesTarget> salesTargetList = this.getSalesTargetData(checkCycleId, userId, roleId);
			
			List<Map> mapList = JSONArray.parseArray(targetArrJSON, Map.class);
			
			boolean isContinue = true;
			for(SalesTarget temp : salesTargetList){
				for(Map map : mapList){
					String tempCheckCycleId = map.get("checkCycleId").toString();
					String tempTargetRate = map.get("targetRate").toString();
					String tempTargetAmount = map.get("targetAmount").toString();
					if(StringUtils.isNotBlank(tempCheckCycleId) && StringUtils.isNotBlank(tempTargetRate) && StringUtils.isNotBlank(tempTargetAmount)){
						if(temp.getCheckCycleId().equals(Long.parseLong(tempCheckCycleId))){
							temp.setTargetRate(tempTargetRate);
							temp.setTargetAmount(new BigDecimal(tempTargetAmount));
							break;
						}
					}else{
						isContinue = false;
					}
				}
				if(!isContinue){
					break;
				}
			}
			
			if(!isContinue){
				RequestResultUtil.getResultFail("参数错误");
			}
			
			int rows = salesTargetService.save(salesTargetList);
			if(rows>0){
				return RequestResultUtil.getResultAddSuccess();
			}
		}
		return RequestResultUtil.getResultAddWarn();
	}
	
	/**
	 * 方法功能：修改
	 * @param request
	 * @param response
	 * @param salesTarget
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> updateById(HttpServletRequest request, HttpServletResponse response, SalesTarget salesTarget) {
		
		try {
			
			/*Long salesTargetId = salesTarget.getId();
			SalesTarget temp = salesTargetService.selectByPrimaryKey(salesTargetId);
			Long cycleId = temp.getCheckCycleId();
			Long checkCycleId = salesTarget.getCheckCycleId();

			if(cycleId!=null && checkCycleId!=null && cycleId==checkCycleId){
				
			}else{
				if(checkCycleId>0){
					CheckCycle checkCycle = checkCycleService.selectByPrimaryKey(checkCycleId);
					salesTarget.setYearName(checkCycle.getYearName());
					salesTarget.setCycleName(checkCycle.getCycleName());
					salesTarget.setCalType(checkCycle.getCalType());
					salesTarget.setStartDate(checkCycle.getStartDate());
					salesTarget.setEndDate(checkCycle.getEndDate());
				}
			}*/
			
			int rows = salesTargetService.updateByPrimaryKeySelective(salesTarget);
			if(rows>0){
				return RequestResultUtil.getResultUpdateSuccess();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return RequestResultUtil.getResultUpdateWarn();
	}
	
	/**
	 * 方法功能：物理删除全年
	 * @param request
	 * @param response
	 * @param yearName
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/delete-all-year")
	@ResponseBody
	public Map<String, Object> deleteById(HttpServletRequest request, HttpServletResponse response, String yearName, Long userId, Long roleId) {
		SalesTarget target = new SalesTarget();
		target.setYearName(yearName);
		target.setUserId(userId);
		target.setRoleId(roleId);
		int rows = salesTargetService.delete(target);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
	/**
	 * 方法功能：物理删除
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> deleteById(HttpServletRequest request, HttpServletResponse response, Long id) {
		int rows = salesTargetService.deleteByPrimaryKey(id);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
	/**
	 * 方法功能：逻辑删除
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/logicDelById")
	@ResponseBody
	public Map<String, Object> logicDelById(HttpServletRequest request, HttpServletResponse response, Long id) {
		int rows = salesTargetService.logicDelById(id);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
	/**
	 * load并打开增加考核周期对话框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/load-add-sales-target-dialog")
	public ModelAndView loadAddSalesTargetDialog(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		
		List<CheckCycle> checkCycleList = checkCycleService.getListByPid(0l);//查询全年考核周期
		mav.addObject("checkCycleList", checkCycleList);
		List<Map<String, Object>> userList = userService.getISAndOS();//查询IS和OS
		mav.addObject("userList", userList);
		
		mav.setViewName(StaticConstants.ADD_SALES_TARGET_DIALOG_PAGE);
		return mav;
	}
	
	/**
	 * 创建考核指标table并显示
	 * @param request
	 * @param response
	 * @param checkCycleId
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/create-sales-target-table")
	public ModelAndView createSalesTargetTable(HttpServletRequest request, HttpServletResponse response, Long checkCycleId, Long userId, Long roleId) {
		ModelAndView mav = new ModelAndView();
		
		List<SalesTarget> salesTargetList = this.getSalesTargetData(checkCycleId, userId, roleId);
		mav.addObject("salesTargetList", salesTargetList);
		
		/*List<CheckCycle> checkCycleList = checkCycleService.getListByPid(0l);//查询全年考核周期
		mav.addObject("checkCycleList", checkCycleList);
		List<Map<String, Object>> userList = userService.getISAndOS();//查询IS和OS
		mav.addObject("userList", userList);*/
		
		mav.setViewName(StaticConstants.LOAD_SALES_TARGET_TABLE_PAGE);
		return mav;
	}
	
	/**
	 * 获取考核指标数据
	 * @param checkCycleId
	 * @param userId
	 * @param roleId
	 * @return
	 */
	private List<SalesTarget> getSalesTargetData(Long checkCycleId, Long userId, Long roleId){
		
		List<SalesTarget> salesTargetList = new ArrayList<>();
		
		CheckCycle cycle = checkCycleService.selectByPrimaryKey(checkCycleId);
		SalesTarget temp = this.createSalesTargetEntity(userId, roleId,  this.getTargetRate(cycle.getCycleName()), new BigDecimal("0"), checkCycleId, cycle.getCycleName(), cycle.getYearName(), cycle.getCalType(), cycle.getStartDate(), cycle.getEndDate(), cycle.getPid(), cycle.getSort());
		salesTargetList.add(temp);
		List<CheckCycle> cycleList = checkCycleService.getListByPid(cycle.getId());
		for(CheckCycle cycleTemp : cycleList){
			temp = this.createSalesTargetEntity(userId, roleId, this.getTargetRate(cycleTemp.getCycleName()), new BigDecimal("0"), cycleTemp.getId(), cycleTemp.getCycleName(), cycleTemp.getYearName(), cycleTemp.getCalType(), cycleTemp.getStartDate(), cycleTemp.getEndDate(), cycleTemp.getPid(), cycleTemp.getSort());
			salesTargetList.add(temp);
		}
		
		return salesTargetList;
	}
	
	/**
	 * 获取考核指标比例
	 * @param cycleName
	 * @return
	 */
	private String getTargetRate(String cycleName){
		BigDecimal hundred = new BigDecimal("100");
		BigDecimal two = new BigDecimal("2");
		BigDecimal four = new BigDecimal("4");
		BigDecimal twelve = new BigDecimal("12");
		BigDecimal rate = new BigDecimal("0.00");   
		if(cycleName.indexOf("全年")!=-1){
			rate = hundred;
			rate = rate.setScale(2, BigDecimal.ROUND_HALF_UP);
			return rate.toString();
		}
		if(cycleName.indexOf("半年")!=-1){
			rate = hundred.divide(two, 2, BigDecimal.ROUND_HALF_UP);
			return rate.toString();
		}
		if(cycleName.indexOf("季")!=-1){
			rate = hundred.divide(four, 2, BigDecimal.ROUND_HALF_UP);
			return rate.toString();
		}
		if(cycleName.indexOf("月")!=-1){
			rate = hundred.divide(twelve, 2, BigDecimal.ROUND_HALF_UP);
			return rate.toString();
		}
		return rate.toString();
	}
	
	/**
	 * 创建考核指标实体类
	 * @param userId
	 * @param roleId
	 * @param checkCycleId
	 * @param cycleName
	 * @param yearName
	 * @param calType
	 * @param startDate
	 * @param endDate
	 * @param pid
	 * @param sort
	 * @return
	 */
	private SalesTarget createSalesTargetEntity(Long userId, Long roleId, String targetRate, BigDecimal targetAmount, Long checkCycleId, String cycleName, String yearName, Byte calType, Date startDate, Date endDate, Long pid, Integer sort){
		SalesTarget target = new SalesTarget(userId, roleId, targetRate, targetAmount, checkCycleId, cycleName, yearName, calType, startDate, endDate, pid, sort);
		return target;
	}
	
}
