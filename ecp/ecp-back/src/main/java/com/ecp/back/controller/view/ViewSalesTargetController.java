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
import com.ecp.entity.CheckCycle;
import com.ecp.entity.Role;
import com.ecp.entity.SalesTarget;
import com.ecp.service.back.ICheckCycleService;
import com.ecp.service.back.ISalesTargetService;
import com.ecp.service.back.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Class: SalesTargetController
 * 		查看销售指标Controller类
 * @author srd 
 */
@Controller
@RequestMapping("/back/view/sales-target")
public class ViewSalesTargetController {

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
	public ModelAndView selectLinkItem(HttpServletRequest request, HttpServletResponse response, Boolean clickPageBtn, PageBean pageBean, String pagehelperFun) {
		ModelAndView mav = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();

		Map<String, Object> map = new HashMap<>();
		
		boolean isISOrOS = false;
		List<Long> roleIdList = new ArrayList<>();
		List<Role> roleList = user.getRoleList();
		for(Role role : roleList){
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
		//map.put("deleted", 1);//deleted=1:默认（未删除）deleted=2:已删除
		List<Map<String, Object>> salesTargetList = salesTargetService.getList(map);
		PageInfo<Map<String, Object>> pagehelper = new PageInfo<Map<String, Object>>(salesTargetList);
		
		mav.addObject("pagehelper", pagehelper);
		
		List<CheckCycle> checkCycleList = checkCycleService.selectAll();//查询考核周期
		mav.addObject("checkCycleList", checkCycleList);
		List<Map<String, Object>> userList = userService.getISAndOS();//查询IS和OS
		mav.addObject("userList", userList);
		
		if(clickPageBtn!=null && clickPageBtn){
			mav.setViewName(StaticConstants.VIEW_SALES_TARGET_MANAGE_TABLE_PAGE);
		}else{
			mav.setViewName(StaticConstants.VIEW_SALES_TARGET_MANAGE_PAGE);
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
			SalesTarget salesTarget = salesTargetService.selectByPrimaryKey(id);
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("salesTarget", salesTarget);
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
	 * @param salesTarget
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response, SalesTarget salesTarget) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		if(userBean!=null){
			Long checkCycleId = salesTarget.getCheckCycleId();
			if(checkCycleId!=null && checkCycleId>0){
				CheckCycle checkCycle = checkCycleService.selectByPrimaryKey(checkCycleId);
				salesTarget.setYearName(checkCycle.getYearName());
				salesTarget.setCycleName(checkCycle.getCycleName());
				salesTarget.setCalType(checkCycle.getCalType());
				salesTarget.setStartDate(checkCycle.getStartDate());
				salesTarget.setEndDate(checkCycle.getEndDate());
			}
			
			int rows = salesTargetService.insertSelective(salesTarget);
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
			
			Long salesTargetId = salesTarget.getId();
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
			}
			
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
	
}
