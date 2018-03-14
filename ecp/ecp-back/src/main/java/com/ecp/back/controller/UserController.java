package com.ecp.back.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
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
import com.ecp.entity.Role;
import com.ecp.entity.User;
import com.ecp.entity.UserRole;
import com.ecp.service.back.IRoleService;
import com.ecp.service.back.IUserRoleService;
import com.ecp.service.back.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Class: UserController
 * 		用户Controller类
 * @author srd 
 * @version 1.0 $Date: 2017年5月7日 下午4:16:02
 */
@Controller
@RequestMapping("/back/user")
public class UserController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Resource(name="userServiceBean")
	private IUserService userService;
	@Resource(name="roleServiceBean")
	private IRoleService roleService;
	@Resource(name="userRoleServiceBean")
	private IUserRoleService userRoleService;
	
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deleted", 1);//deleted=1:默认（未删除）deleted=2:已删除
		map.put("type", 1);//type=1:默认（后台管理用户）type=2:前端访问用户
		List<User> userList = userService.getList(map);
		PageInfo<User> pagehelper = new PageInfo<User>(userList);
		
		List<Role> roleList = roleService.selectAll();
		mav.addObject("roleList", roleList);
		
		mav.addObject("pagehelper", pagehelper);
		
		if(clickPageBtn!=null && clickPageBtn){
			mav.setViewName(StaticConstants.USER_MANAGE_TABLE_PAGE);
		}else{
			mav.setViewName(StaticConstants.USER_MANAGE_PAGE);
		}
		
		mav.addObject("pagehelperFun", pagehelperFun);
		return mav;
	}
	
	/**
	 * 方法功能：查询要修改的信息
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@RequestMapping("/selectUpdateById")
	@ResponseBody
	public Map<String, Object> selectUpdateById(HttpServletRequest request, HttpServletResponse response, Long id) {
		try {
			User user = userService.selectByPrimaryKey(id);
			List<UserRole> userRoleList = userRoleService.getByUserId(id);
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("user", user);
			respM.put("userRoleList", userRoleList);
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
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response, User user, String roleIds) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		if(userBean!=null){
			user.setCreatedTime(new Date());
			user.setUpdateTime(new Date());
			user.setDeleted(1);//1：默认（未删除），2：已删除
			user.setType(1);//type=1:默认（后台管理用户）type=2:前端访问用户
			user.setPassword(DigestUtils.md5Hex(user.getUsername()+":123456"));//添加用户时，默认密码：123456
			int rows = userService.add(user, roleIds);
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
	 * @param user
	 * @return
	 */
	@RequestMapping("/updateById")
	@ResponseBody
	public Map<String, Object> updateById(HttpServletRequest request, HttpServletResponse response, User user, String roleIds) {
		
		try {
			user.setUpdateTime(new Date());
			int rows = userService.modify(user, roleIds);
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
		int rows = userService.deleteByPrimaryKey(id);
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
		int rows = userService.logicDelById(id);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @param password
	 * @param newPassword
	 * @return
	 */
	@RequestMapping("/updatePassword")
	@ResponseBody
	public Map<String, Object> updatePassword(HttpServletRequest request, HttpServletResponse response, String password, String newPassword) {
		
		try {
			Subject subject = SecurityUtils.getSubject();
			UserBean user = (UserBean)subject.getPrincipal();
			if(user!=null){
				String sessionPass = user.getPassword();//原密码
				String userPass = DigestUtils.md5Hex(user.getUsername()+":"+password);//用户输入的原密码
				String newPass = DigestUtils.md5Hex(user.getUsername()+":"+newPassword);//用户输入的新密码
				if(sessionPass.equalsIgnoreCase(userPass)){
					User temp = new User();
					temp.setId(user.getId());
					temp.setPassword(newPass);
					int rows = userService.updateByPrimaryKeySelective(temp);
					if(rows>0){
						return RequestResultUtil.getResultUpdateSuccess();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return RequestResultUtil.getResultUpdateWarn();
	}
	
	/**
	 * 验证原密码
	 * @param request
	 * @param response
	 * @param password
	 * @return
	 */
	@RequestMapping("/checkPasswordValid")
	@ResponseBody
	public Map<String, Boolean> checkPasswordValid(HttpServletRequest request, HttpServletResponse response, String password) {
		
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			Subject subject = SecurityUtils.getSubject();
			UserBean user = (UserBean)subject.getPrincipal();
			if(user!=null){
				String sessionPass = user.getPassword();//原密码
				String userPass = DigestUtils.md5Hex(user.getUsername()+":"+password);//用户输入的原密码
				if(sessionPass.equalsIgnoreCase(userPass)){
					map.put("valid", true);
					return map;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		map.put("valid", false);
		return map;
	}
	
	/**
	 * 方法功能：检查用户名是否有效
	 * @param request
	 * @param response
	 * @param username
	 * @return
	 */
	@RequestMapping("/checkUsernameValid")
	@ResponseBody
	public Map<String, Boolean> checkUsernameValid(HttpServletRequest request, HttpServletResponse response, Long userid, String username) {
		
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			if(userid!=null && userid>0){
				map.put("valid", true);
				return map;
			}
			List<User> userList = userService.getByUsername(username);
			if(userList.isEmpty()){
				map.put("valid", true);
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		map.put("valid", false);
		return map;
	}
	
}
