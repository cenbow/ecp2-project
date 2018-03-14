package com.ecp.back.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecp.back.commons.SessionConstants;
import com.ecp.back.commons.StaticConstants;
import com.ecp.bean.UserBean;
import com.ecp.entity.Menu;
import com.ecp.service.back.IUserService;

@Controller
public class MainController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Resource(name="userServiceBean")
	private IUserService userService;

	
	/**
	 * 登录成功进入SCRM系统首页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/back/main")
	public String goScrmMain(HttpServletRequest request, HttpServletResponse response, String error) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		request.getSession().setAttribute(SessionConstants.USER, user);
		
		List<Menu> menuList = user.getMenuList();
		request.setAttribute("menuList", menuList);
		log.info("用户 "+user.getNickname()+" 的菜单权限："+menuList);
		
		log.info("进入系统首页："+StaticConstants.MAIN);
		return StaticConstants.MAIN;
	}
	
}
