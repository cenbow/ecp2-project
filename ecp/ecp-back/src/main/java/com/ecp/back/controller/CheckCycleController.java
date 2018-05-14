package com.ecp.back.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ecp.back.commons.StaticConstants;
import com.ecp.bean.PageBean;
import com.ecp.bean.UserBean;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.CheckCycle;
import com.ecp.entity.SalesTarget;
import com.ecp.service.back.ICheckCycleService;
import com.ecp.service.back.ISalesTargetService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Class: CheckCycleController
 * 		考核周期Controller类
 * @author srd 
 */
@Controller
@RequestMapping("/back/check-cycle")
public class CheckCycleController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Resource(name="checkCycleServiceBean")
	private ICheckCycleService checkCycleService;
	@Resource(name="salesTargetServiceBean")
	private ISalesTargetService salesTargetService;
	
	/**
	 * 方法功能：查询列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/select-items")
	public ModelAndView selectLinkItem(HttpServletRequest request, HttpServletResponse response, Boolean clickPageBtn, PageBean pageBean, String pagehelperFun, String yearName) {
		ModelAndView mav = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		if(StringUtils.isBlank(yearName)){
			Calendar now = Calendar.getInstance();
			int currYear = now.get(Calendar.YEAR);
	        System.out.println("年: " + currYear); 
			yearName = String.valueOf(currYear);
		}
		
		mav.addObject("yearName", yearName);
		
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<CheckCycle> cycleList = checkCycleService.getListByYearName(yearName);
		PageInfo<CheckCycle> pagehelper = new PageInfo<>(cycleList);
		
		mav.addObject("pagehelper", pagehelper);
		
		if(clickPageBtn!=null && clickPageBtn){
			mav.setViewName(StaticConstants.CHECK_CYCLE_MANAGE_TABLE_PAGE);
		}else{
			mav.setViewName(StaticConstants.CHECK_CYCLE_MANAGE_PAGE);
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
			CheckCycle checkCycle = checkCycleService.selectByPrimaryKey(id);
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("checkCycle", checkCycle);
			return respM;
		} catch (Exception e) {
			log.error("查询异常", e);
			return RequestResultUtil.getResultSelectWarn();
		}
	}
	
	/**
	 * 方法功能：添加
	 * @param request
	 * @param response
	 * @param checkCycle
	 * @return
	 */
	/*@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response, CheckCycle checkCycle, String startDateStr, String endDateStr) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		if(userBean!=null){
			try {
				yyyy-MM-dd HH:mm:ss
				if(StringUtils.isNotBlank(startDateStr)){
					Date startDate = DateUtils.parseDate(startDateStr, "yyyy-MM-dd");
					checkCycle.setStartDate(startDate);
				}
				if(StringUtils.isNotBlank(endDateStr)){
					Date endDate = DateUtils.parseDate(endDateStr, "yyyy-MM-dd");
					checkCycle.setEndDate(endDate);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int rows = checkCycleService.insertSelective(checkCycle);
			if(rows>0){
				return RequestResultUtil.getResultAddSuccess();
			}
		}
		return RequestResultUtil.getResultAddWarn();
	}*/
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response, String yearName, String cycleArrJSON) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		if(userBean!=null){
			/*try {
				yyyy-MM-dd HH:mm:ss
				if(StringUtils.isNotBlank(startDateStr)){
					Date startDate = DateUtils.parseDate(startDateStr, "yyyy-MM-dd");
					checkCycle.setStartDate(startDate);
				}
				if(StringUtils.isNotBlank(endDateStr)){
					Date endDate = DateUtils.parseDate(endDateStr, "yyyy-MM-dd");
					checkCycle.setEndDate(endDate);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}*/
			if(StringUtils.isBlank(yearName)){
				Calendar now = Calendar.getInstance();
				int currYear = now.get(Calendar.YEAR);
		        System.out.println("年: " + currYear); 
				yearName = String.valueOf(currYear);
			}
			int rows = checkCycleService.save(yearName, cycleArrJSON);
			if(rows>0){
				return RequestResultUtil.getResultAddSuccess();
			}
			if(rows==-1){
				return RequestResultUtil.getResultFail(yearName+" 年度考核周期已存在，不能重复增加。");
			}
		}
		return RequestResultUtil.getResultAddWarn();
	}
	
	/**
	 * 方法功能：修改
	 * @param request
	 * @param response
	 * @param checkCycle
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> updateById(HttpServletRequest request, HttpServletResponse response, CheckCycle checkCycle, String startDateStr, String endDateStr) {
		
		try {
			/*yyyy-MM-dd HH:mm:ss*/
			if(StringUtils.isNotBlank(startDateStr)){
				Date startDate = DateUtils.parseDate(startDateStr, "yyyy-MM-dd");
				checkCycle.setStartDate(startDate);
			}
			if(StringUtils.isNotBlank(endDateStr)){
				Date endDate = DateUtils.parseDate(endDateStr, "yyyy-MM-dd");
				checkCycle.setEndDate(endDate);
			}
			
			int rows = checkCycleService.updateByPrimaryKeySelective(checkCycle);
			if(rows>0){
				return RequestResultUtil.getResultUpdateSuccess();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return RequestResultUtil.getResultUpdateWarn();
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
		CheckCycle cycle = checkCycleService.selectByPrimaryKey(id);
		if(cycle!=null){
			String yearName = cycle.getYearName();
			if(StringUtils.isNotBlank(yearName)){
				SalesTarget target = new SalesTarget();
				target.setYearName(yearName);
				List<SalesTarget> targetList = salesTargetService.select(target);
				if(targetList!=null && !targetList.isEmpty()){
					return RequestResultUtil.getResultFail(yearName+" 年 "+cycle.getCycleName()+" 考核指标已创建，不允许删除考核周期！");
				}
			}
			
			int rows = checkCycleService.deleteByPrimaryKey(id);
			if(rows>0){
				return RequestResultUtil.getResultDeleteSuccess();
			}
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
		int rows = checkCycleService.logicDelById(id);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
	/**
	 * 根据年度名称删除
	 * @param request
	 * @param response
	 * @param yearName
	 * @return
	 */
	@RequestMapping("/deleteByYearName")
	@ResponseBody
	public Map<String, Object> deleteByYearName(HttpServletRequest request, HttpServletResponse response, String yearName) {
		SalesTarget target = new SalesTarget();
		target.setYearName(yearName);
		List<SalesTarget> targetList = salesTargetService.select(target);
		if(targetList!=null && !targetList.isEmpty()){
			return RequestResultUtil.getResultFail(yearName+" 年考核指标已创建，不允许删除考核周期！");
		}
		
		CheckCycle cycle = new CheckCycle();
		cycle.setYearName(yearName);
		int rows = checkCycleService.delete(cycle);
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
	@RequestMapping("/load-add-check-cycle-dialog")
	public ModelAndView loadAddCheckCycleDialog(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(StaticConstants.ADD_CHECK_CYCLE_DIALOG_PAGE);
		return mav;
	}
	
}
