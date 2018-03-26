package com.ecp.back.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.ecp.service.back.ICheckCycleService;
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
	
	/**
	 * 方法功能：查询列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/select-items")
	public ModelAndView selectLinkItem(HttpServletRequest request, HttpServletResponse response, Boolean clickPageBtn, PageBean pageBean, String pagehelperFun) {
		ModelAndView mav = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		//Map<String, Object> map = new HashMap<String, Object>();
		//map.put("deleted", 1);//deleted=1:默认（未删除）deleted=2:已删除
		List<CheckCycle> cycleList = checkCycleService.selectAll();
		PageInfo<CheckCycle> pagehelper = new PageInfo<CheckCycle>(cycleList);
		
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
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response, CheckCycle checkCycle, String startDateStr, String endDateStr) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		if(userBean!=null){
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
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int rows = checkCycleService.insertSelective(checkCycle);
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
		int rows = checkCycleService.deleteByPrimaryKey(id);
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
		int rows = checkCycleService.logicDelById(id);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
}