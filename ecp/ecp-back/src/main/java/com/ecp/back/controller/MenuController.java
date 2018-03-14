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
import com.ecp.back.commons.StaticConstants;
import com.ecp.bean.UserBean;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.Menu;
import com.ecp.service.back.IMenuService;

/**
 * Class: MenuController 菜单管理Controller类
 * 
 * @author srd
 * @version 1.0 $Date: 2017年7月11日 下午5:20:46
 */
@Controller
@RequestMapping("/back/menu")
public class MenuController {

	private final Logger log = Logger.getLogger(getClass());

	@Resource(name="menuServiceBean")
	private IMenuService menuService;//菜单权限

	/**
	 * 方法功能：查询列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectItems")
	public ModelAndView selectLinkItems(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();

		List<Menu> menuList = menuService.getListAllByAsc();
		log.info("List:" + menuList);
		mav.addObject("menuList", menuList);

		String menuListJson = JSON.toJSONString(menuList);
		log.info("menu list json string:" + menuListJson);
		mav.addObject("menuListJson", menuListJson);

		mav.setViewName(StaticConstants.MENU_MANAGE_TABLE_PAGE);

		return mav;
	}

	/**
	 * 方法功能：查询要修改的信息
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/selectUpdateById")
	@ResponseBody
	public Map<String, Object> selectUpdateById(HttpServletRequest request, HttpServletResponse response, Long id) {
		try {
			Menu menu = menuService.selectByPrimaryKey(id);
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("menu", menu);
			return respM;
		} catch (Exception e) {
			log.error("查询异常", e);
			return RequestResultUtil.getResultSelectWarn();
		}
	}

	/**
	 * 方法功能：添加
	 * 
	 * @param request
	 * @param response
	 * @param menu
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response,
			Menu menu) {

		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		if (userBean != null) {
			int rows = menuService.insertSelective(menu);
			if (rows > 0) {
				return RequestResultUtil.getResultAddSuccess();
			}
		}
		return RequestResultUtil.getResultAddWarn();
	}

	/**
	 * 方法功能：修改
	 * 
	 * @param request
	 * @param response
	 * @param menu
	 * @return
	 */
	@RequestMapping("/updateById")
	@ResponseBody
	public Map<String, Object> updateById(HttpServletRequest request, HttpServletResponse response, Menu menu) {

		try {
			int rows = menuService.updateByPrimaryKeySelective(menu);
			if (rows > 0) {
				return RequestResultUtil.getResultUpdateSuccess();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return RequestResultUtil.getResultUpdateWarn();
	}

	/**
	 * 方法功能：删除
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public Map<String, Object> deleteById(HttpServletRequest request, HttpServletResponse response, Long id) {
		int rows = menuService.deleteById(id);
		if (rows > 0) {
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}

}
