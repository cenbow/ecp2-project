package com.ecp.back.controller;

import java.util.ArrayList;
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
import com.ecp.bean.PageBean;
import com.ecp.bean.UserBean;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.Menu;
import com.ecp.entity.Role;
import com.ecp.entity.RolePermission;
import com.ecp.service.back.IMenuService;
import com.ecp.service.back.IPermissionService;
import com.ecp.service.back.IRolePermissionService;
import com.ecp.service.back.IRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Class: UserController
 * 		用户角色Controller类
 * @author srd 
 * @version 1.0 $Date: 2017年5月7日 下午4:16:02
 */
@Controller
@RequestMapping("/back/role")
public class RoleController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Resource(name="roleServiceBean")
	private IRoleService roleService;
	@Resource(name="permissionServiceBean")
	private IPermissionService permissionService;
	@Resource(name="menuServiceBean")
	private IMenuService menuService;
	@Resource(name="rolePermissionServiceBean")
	private IRolePermissionService rolePermissionService;
	
	/**
	 * 方法功能：查询列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectItems")
	public ModelAndView selectLinkItem(HttpServletRequest request, HttpServletResponse response, Boolean clickPageBtn, PageBean pageBean, String pagehelperFun) {
		ModelAndView mav = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<Role> roleList = roleService.selectAll();
		PageInfo<Role> pagehelper = new PageInfo<Role>(roleList);
		
		/*List<Permission> permsList = permissionService.selectAll();
		mav.addObject("permsList", permsList);*/
		List<Menu> menuList = menuService.selectAll();
		mav.addObject("menuListJson", JSON.toJSONString(menuList));
		
		mav.addObject("pagehelper", pagehelper);
		
		if(clickPageBtn!=null && clickPageBtn){
			mav.setViewName(StaticConstants.ROLE_MANAGE_TABLE_PAGE);
		}else{
			mav.setViewName(StaticConstants.ROLE_MANAGE_PAGE);
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
	@RequestMapping("/selectUpdateById")
	@ResponseBody
	public Map<String, Object> selectUpdateById(HttpServletRequest request, HttpServletResponse response, Long id) {
		try {
			Role role = roleService.selectByPrimaryKey(id);
			List<RolePermission> rolePermsList = rolePermissionService.getByRoleId(id);
			List<Long> menuIds = new ArrayList<Long>();
			for(RolePermission rp : rolePermsList){
				menuIds.add(rp.getPermissionId());
			}
			List<Menu> menuList = menuService.getByMenuIds(menuIds);
			
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("role", role);
			respM.put("menuList", menuList);
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
	 * @param user
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response, Role role, String menuPermissionIds) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		if(userBean!=null){
			int rows = roleService.add(role, menuPermissionIds);
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
	 * @param role
	 * @return
	 */
	@RequestMapping("/updateById")
	@ResponseBody
	public Map<String, Object> updateById(HttpServletRequest request, HttpServletResponse response, Role role, String menuPermissionIds) {
		
		try {
			int rows = roleService.modify(role, menuPermissionIds);
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
	@RequestMapping("/deleteById")
	@ResponseBody
	public Map<String, Object> deleteById(HttpServletRequest request, HttpServletResponse response, Long id) {
		int rows = roleService.del(id);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
}
