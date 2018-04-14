package com.ecp.back.controller.view;

import java.util.ArrayList;
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

import com.ecp.back.commons.RoleCodeConstants;
import com.ecp.back.commons.StaticConstants;
import com.ecp.bean.PageBean;
import com.ecp.bean.UserBean;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.PushmoneyConfig;
import com.ecp.entity.Role;
import com.ecp.service.back.IPushmoneyConfigService;
import com.ecp.service.back.IRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Class: PushmoneyConfigController
 * 		查看提成比例配置Controller类
 * @author srd 
 */
@Controller
@RequestMapping("/back/view/pushmoney-config")
public class ViewPushmoneyConfigController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Resource(name="pushmoneyConfigServiceBean")
	private IPushmoneyConfigService pushmoneyConfigService;
	
	@Resource(name="roleServiceBean")
	private IRoleService roleService;
	
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
		
		Map<String, Object> map = new HashMap<>();
		
		boolean isISOrOS = false;
		List<Long> roleIdList = new ArrayList<>();
		List<Role> currUserRoleList = user.getRoleList();
		for(Role role : currUserRoleList){
			Long roleId = role.getRoleId();
			String roleCode = role.getRoleCode();
			if(StringUtils.isNotBlank(roleCode) && (roleCode.equalsIgnoreCase(RoleCodeConstants.IS) || roleCode.equalsIgnoreCase(RoleCodeConstants.OS))){
				if(roleId!=null && roleId>0){
					roleIdList.add(roleId);
					isISOrOS = true;
				}
			}
		}
		if(isISOrOS){
			map.put("user_id", user.getId());
			map.put("role_id_list", roleIdList);
		}else{
			map.put("user_id", null);
			map.put("role_id_list", null);
		}
		
		PageHelper.startPage(pageBean.getPageNum(), 10000);
		List<Map<String, Object>> levelList = pushmoneyConfigService.getList(map);
		PageInfo<Map<String, Object>> pagehelper = new PageInfo<>(levelList);
		
		mav.addObject("pagehelper", pagehelper);

		List<Role> roleList = roleService.getByCode(RoleCodeConstants.IS);
		List<Role> tempList = roleService.getByCode(RoleCodeConstants.OS);
		if(tempList!=null && !tempList.isEmpty()){
			roleList.add(tempList.get(0));
		}
		mav.addObject("roleList", roleList);
		
		if(clickPageBtn!=null && clickPageBtn){
			mav.setViewName(StaticConstants.VIEW_PUSHMONEY_CONFIG_MANAGE_TABLE_PAGE);
		}else{
			mav.setViewName(StaticConstants.VIEW_PUSHMONEY_CONFIG_MANAGE_PAGE);
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
			PushmoneyConfig pushmoneyConfig = pushmoneyConfigService.selectByPrimaryKey(id);
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("pushmoneyConfig", pushmoneyConfig);
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
	 * @param pushmoneyConfig
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response, PushmoneyConfig pushmoneyConfig) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		if(userBean!=null){
			int rows = pushmoneyConfigService.insertSelective(pushmoneyConfig);
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
	 * @param pushmoneyConfig
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> updateById(HttpServletRequest request, HttpServletResponse response, PushmoneyConfig pushmoneyConfig) {
		
		try {
			int rows = pushmoneyConfigService.updateByPrimaryKeySelective(pushmoneyConfig);
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
		int rows = pushmoneyConfigService.deleteByPrimaryKey(id);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
}
