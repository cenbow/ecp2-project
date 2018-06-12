package com.ecp.back.controller;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ecp.back.commons.StaticConstants;
import com.ecp.back.commons.SystemConfigConstants;
import com.ecp.bean.UserBean;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.SystemConfig;
import com.ecp.service.back.ISystemConfigService;

/**
 * Class: SystemConfigController
 * 		系统配置Controller类
 * @author srd 
 * @version 1.0 $Date: 2017年6月9日 下午4:16:02
 */
@Controller
@RequestMapping("/back/system-config")
public class SystemConfigController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Resource(name="systemConfigServiceBean")
	private ISystemConfigService systemConfigService;
	
	/**
	 * 加载系统配置页面
	 * @param request
	 * @param response
	 * @return
	 */
	/*@RequestMapping("/load-page")
	public ModelAndView loadSystemConfigPage(HttpServletRequest request, HttpServletResponse response){
		
		ModelAndView mav = new ModelAndView();
		
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		mav.setViewName(StaticConstants.ROLE_MANAGE_PAGE);
		return mav;
	}*/
	
	/**
	 * 方法功能：查询系统配置
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/select-items")
	public ModelAndView selectLinkItem(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		SystemConfig systemConfig = systemConfigService.getByConfigName(SystemConfigConstants.VAT_RATES);
		mav.addObject("configVatRates", systemConfig);
		
		mav.setViewName(StaticConstants.VAT_RATES_PAGE);
		return mav;
	}
	
	/**
	 * 修改系统配置
	 * @param request
	 * @param response
	 * @param configArrJSON
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request, HttpServletResponse response, String configArrJSON) {
		try {
			/*SystemConfig config = new SystemConfig();
			config.setId(id);
			config.setConfigValue(configValue);*/
			List<SystemConfig> configList = JSON.parseArray(configArrJSON, SystemConfig.class);
			int rows = systemConfigService.update(configList);
			if(rows>0){
				return RequestResultUtil.getResultSaveSuccess();
			}

		} catch (Exception e) {
			log.error("修改异常", e);
		}
		return RequestResultUtil.getResultSaveWarn();
	}
	
	/**
	 * 根据系统配置ID修改某名称的配置值
	 * @param request
	 * @param response
	 * @param id
	 * @param configValue
	 * @return
	 */
	/*@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request, HttpServletResponse response, Long id, String configValue) {
		try {
			SystemConfig config = new SystemConfig();
			config.setId(id);
			config.setConfigValue(configValue);
			int rows = systemConfigService.update(config);
			if(rows>0){
				return RequestResultUtil.getResultUpdateSuccess();
			}

		} catch (Exception e) {
			log.error("修改异常", e);
		}
		return RequestResultUtil.getResultUpdateWarn();
	}*/
	
}
