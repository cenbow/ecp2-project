package com.ecp.back.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ecp.entity.CustomerLevel;
import com.ecp.entity.CustomerLevelRule;
import com.ecp.entity.CustomerType;
import com.ecp.service.back.ICustomerLevelRuleService;
import com.ecp.service.back.ICustomerLevelService;
import com.ecp.service.back.ICustomerTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Class: CustomerLevelRuleController
 * 		客户级别规则Controller类
 * @author srd 
 */
@Controller
@RequestMapping("/back/customer-level-rule")
public class CustomerLevelRuleController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Resource(name="customerLevelRuleServiceBean")
	private ICustomerLevelRuleService customerLevelRuleService;
	
	@Resource(name="customerLevelServiceBean")
	private ICustomerLevelService customerLevelService;
	
	@Resource(name="customerTypeServiceBean")
	private ICustomerTypeService customerTypeService;
	
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
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("deleted", 1);//deleted=1:默认（未删除）deleted=2:已删除
		List<Map<String, Object>> levelRuleList = customerLevelRuleService.getList(map);
		PageInfo<Map<String, Object>> pagehelper = new PageInfo<Map<String, Object>>(levelRuleList);
		
		mav.addObject("pagehelper", pagehelper);
		
		List<CustomerType> customerTypeList = customerTypeService.selectAll();
		mav.addObject("customerTypeList", customerTypeList);
		
		if(customerTypeList!=null && !customerTypeList.isEmpty()){
			List<CustomerLevel> customerLevelList = customerLevelService.getList(customerTypeList.get(0).getId());
			mav.addObject("customerLevelList", customerLevelList);
		}
		
		if(clickPageBtn!=null && clickPageBtn){
			mav.setViewName(StaticConstants.CUSTOMER_LEVEL_RULE_MANAGE_TABLE_PAGE);
		}else{
			mav.setViewName(StaticConstants.CUSTOMER_LEVEL_RULE_MANAGE_PAGE);
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
			CustomerLevelRule customerLevelRule = customerLevelRuleService.selectByPrimaryKey(id);
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("customerLevelRule", customerLevelRule);
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
	 * @param rule
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response, CustomerLevelRule rule) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		if(userBean!=null){
			int rows = customerLevelRuleService.insertSelective(rule);
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
	 * @param rule
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> updateById(HttpServletRequest request, HttpServletResponse response, CustomerLevelRule rule) {
		
		try {
			int rows = customerLevelRuleService.updateByPrimaryKeySelective(rule);
			if(rows>0){
				return RequestResultUtil.getResultUpdateSuccess();
			}
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
		int rows = customerLevelRuleService.deleteByPrimaryKey(id);
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
		int rows = customerLevelRuleService.logicDelById(id);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
}
