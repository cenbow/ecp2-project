package com.ecp.back.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ecp.back.commons.RoleCodeConstants;
import com.ecp.back.commons.StaticConstants;
import com.ecp.bean.PageBean;
import com.ecp.bean.UserBean;
import com.ecp.entity.Role;
import com.ecp.service.back.IUserService;
import com.ecp.service.front.IOrderItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Class: BrandController.java
 * 		用户等级BrandController类
 * @author srd 
 * @version 1.0 $Date: 2017年5月20日 下午2:16:02
 */
@Controller
@RequestMapping("/back/item-sales-stats")
public class StatsController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Autowired
	IOrderItemService orderItemService; //订单条目
	@Resource(name="userServiceBean")
	private IUserService userService;
	
	/**
	 * 方法功能：查询列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/select-items")
	public ModelAndView selectLinkItem(HttpServletRequest request, HttpServletResponse response, Boolean clickPageBtn, PageBean pageBean, String pagehelperFun, String startTime, String endTime, Long userId, Long roleId) {
		ModelAndView mav = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		this.setUserAndRoleToModel(user, mav);//获取用户列表和角色列表并增加到model中
		
		Map<String, Object> params = new HashMap<>();
		params.put("start_time", startTime);
		params.put("end_time", endTime);
		params.put("user_id", userId);
		params.put("role_id", roleId);
		
		PageHelper.startPage(pageBean.getPageNum(), 10);
		List<Map<String, Object>> itemSalesStatsList = orderItemService.getItemSalesStats(params);
		PageInfo<Map<String, Object>> pagehelper = new PageInfo<>(itemSalesStatsList);
		
		int size = 10;
		if(itemSalesStatsList.size()<10){
			size = itemSalesStatsList.size();
		}
		List<String> itemNameList = new ArrayList<>();//商品名称集合
		List<String> itemNumTotalList = new ArrayList<>();//商品总数量集合
		List<String> itemPriceTotalList = new ArrayList<>();//商品金额量集合
		for(int i=0; i<size; i++){
			Map<String, Object> map = itemSalesStatsList.get(i);
			itemNameList.add(map.get("sku_name").toString());
			itemNumTotalList.add(map.get("sum_num").toString());
			itemPriceTotalList.add(map.get("pay_price_total").toString());
		}
		mav.addObject("itemNameList", JSON.toJSONString(itemNameList));
		mav.addObject("itemNumTotalList", JSON.toJSONString(itemNumTotalList));
		mav.addObject("itemPriceTotalList", JSON.toJSONString(itemPriceTotalList));
		
		mav.addObject("pagehelper", pagehelper);
		
		if(clickPageBtn!=null && clickPageBtn){
			mav.setViewName(StaticConstants.ITEM_SALES_STATS_TABLE_PAGE);
		}else{
			mav.setViewName(StaticConstants.ITEM_SALES_STATS_PAGE);
		}
		
		mav.addObject("pagehelperFun", pagehelperFun);
		return mav;
	}
	
	/**
	 * 获取用户列表和角色列表并增加到model中
	 * @param user
	 * @param mav
	 */
	private void setUserAndRoleToModel(UserBean user, ModelAndView mav){
		
		List<Map<String, Object>> agentIdList = null;
		
		List<Role> roleList = user.getRoleList();
		boolean isISOrOS = true;
		for(Role role : roleList){
			String roleCode = role.getRoleCode();
			if(StringUtils.isNotBlank(roleCode)){
				if(roleCode.equalsIgnoreCase(RoleCodeConstants.ADMIN) || roleCode.equalsIgnoreCase(RoleCodeConstants.MANAGER) || roleCode.equalsIgnoreCase(RoleCodeConstants.BUSSMAN) || roleCode.equalsIgnoreCase(RoleCodeConstants.SALEMAN)){
					isISOrOS = false;
					break;
				}
			}
		}
		
		if(isISOrOS){
			List<Map<String, Object>> userList = userService.getById(user.getId());
			mav.addObject("userList", userList);
			List<Role> tempRoleList = new ArrayList<>();
			for(Role role : roleList){
				String roleCode = role.getRoleCode();
				if(StringUtils.isNotBlank(roleCode)){
					if(roleCode.equalsIgnoreCase(RoleCodeConstants.IS) || roleCode.equalsIgnoreCase(RoleCodeConstants.OS)){
						tempRoleList.add(role);
					}
				}
			}
			mav.addObject("roleList", tempRoleList);
			
			/*Example example = new Example(CustLockRel.class);
			if(roleList.size()>1){
				example.createCriteria().andEqualTo("bindUserId", user.getId());
			}else{
				example.createCriteria().andEqualTo("bindUserId", user.getId()).andEqualTo("roleId", roleList.get(0).getRoleId());
			}
			List<CustLockRel> tempList = agentBindService.selectByExample(example);
			for(CustLockRel temp : tempList){
				if(agentIdList==null){
					agentIdList = new ArrayList<>();
				}
				Map<String, Object> tempMap = new HashMap<>();
				Long agentId = temp.getCustId();
				if(agentId!=null && agentId>0){
					tempMap.put("cust_id", agentId);
					agentIdList.add(tempMap);
				}else{
					tempMap.put("cust_id", null);
				}
			}*/
		}else{
			List<Map<String, Object>> userList = userService.getISAndOSUser();
			mav.addObject("userList", userList);
		}
		
		mav.addObject("isISOrOS", isISOrOS);
		
	}
	
	/**
	 * 根据类目查询品牌
	 * @param request
	 * @param response
	 * @param cid
	 * @return
	 */
	/*@RequestMapping("/selectByCid")
	@ResponseBody
	public Map<String, Object> selectByCid(HttpServletRequest request, HttpServletResponse response, Long cid) {
		try {
			List<Map<String, Object>> brandList = iBrandService.getBrandByCategoryId(cid);
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("brandList", brandList);
			return respM;
		} catch (Exception e) {
			log.error("查询异常", e);
			return RequestResultUtil.getResultSelectWarn();
		}
	}*/
	
	/**
	 * 方法功能：查询要修改的信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	/*@RequestMapping("/selectUpdateById")
	@ResponseBody
	public Map<String, Object> selectUpdateById(HttpServletRequest request, HttpServletResponse response, Long id) {
		try {
			Brand brand = iBrandService.selectByPrimaryKey(id);
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("brand", brand);
			return respM;
		} catch (Exception e) {
			log.error("查询异常", e);
			return RequestResultUtil.getResultSelectWarn();
		}
	}*/
	
	/**
	 * 方法功能：添加
	 * @param request
	 * @param response
	 * @param brand
	 * @return
	 */
	/*@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response, Brand brand) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		
		//处理上传文件
		if(!this.processUploadFile(request, brand)){
			return RequestResultUtil.getResultUploadWarn();
		}
		
		if(userBean!=null){
			brand.setCreated(new Date());
			brand.setModified(new Date());
			
			int rows = iBrandService.insertSelective(brand);
			if(rows>0){
				return RequestResultUtil.getResultAddSuccess();
			}
		}
		return RequestResultUtil.getResultAddWarn();
	}*/
	
	/**
	 * 方法功能：修改
	 * @param request
	 * @param response
	 * @param brand
	 * @return
	 */
	/*@RequestMapping("/updateById")
	@ResponseBody
	public Map<String, Object> updateById(HttpServletRequest request, HttpServletResponse response, Brand brand) {
		
		//处理上传文件
		if(!this.processUploadFile(request, brand)){
			return RequestResultUtil.getResultUploadWarn();
		}
		
		try {
			int rows = iBrandService.updateByPrimaryKeySelective(brand);
			if(rows>0){
				return RequestResultUtil.getResultUpdateSuccess();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return RequestResultUtil.getResultUpdateWarn();
	}*/
	
	/**
	 * 方法功能：删除（逻辑删除）
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	/*@RequestMapping("/deleteById")
	@ResponseBody
	public Map<String, Object> deleteById(HttpServletRequest request, HttpServletResponse response, Long id) {
		int rows = iBrandService.deleteById(id);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}*/
	
	/**
	 * 方法功能：处理上传文件
	 * @param request
	 * @param gwContent
	 * @return
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年1月11日 下午6:09:34</p>
	 */
	/*private boolean processUploadFile(HttpServletRequest request, Brand brand){
		boolean flag = false;
		try {
			//获取上传背景图文件
			String backImgPath = FileUploadUtil.getFile2Upload(request, "back logo", "logo");
			if(StringUtils.isNotBlank(backImgPath)){
				if(!FileUploadUtil.deleteFile(request, brand.getBrandLogoUrl())){
					log.error("文件不存在或已删除 logo图路径："+brand.getBrandLogoUrl());
				}
				brand.setBrandLogoUrl(backImgPath);
			}
			flag = true;
		} catch (IOException e) {
			log.error("上传文件异常", e);
		} catch (Exception e) {
			log.error("删除上传文件异常", e);
		}
		return flag;
	}*/
	
	/**
	 * 方法功能：删除上传文件
	 * @param request
	 * @param gwContent
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年1月11日 下午6:13:30</p>
	 */
	/*private void deleteUploadFile(HttpServletRequest request, Brand brand) throws Exception{
		if(StringUtils.isNotBlank(brand.getBrandLogoUrl())){
			删除已上传的logo图
			FileUploadUtil.deleteFile(request, brand.getBrandLogoUrl());
			brand.setBrandLogoUrl("");
		}
	}*/
	
	/**
	 * 方法功能：删除内容中某个图片或附件并同步数据库
	 * @param request
	 * @param response
	 * @param content
	 * @return
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年1月5日 下午4:11:25</p>
	 */
	/*@RequestMapping("/deleteUploadFileById")
	@ResponseBody
	public Map<String, Object> deleteImgById(HttpServletRequest request, HttpServletResponse response, Brand brand) {
		Map<String, Object> delImgRespMap = RequestResultUtil.getResultDeleteWarn();
		if(brand==null || brand.getBrandId()==null || brand.getBrandId()<=0){
			return delImgRespMap;
		}
		
		try {
			this.deleteUploadFile(request, brand);//删除上传文件
		} catch (Exception e) {
			log.error("删除上传文件异常", e);
			delImgRespMap.put(RequestResultUtil.RESULT_ERR_MSG, "删除文件异常或文件不存在！");
			return delImgRespMap;
		}
		brand.setModified(new Date());
		int rows = iBrandService.updateByPrimaryKeySelective(brand);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		Map<String, Object> map = RequestResultUtil.getResultUpdateWarn();
		map.put(RequestResultUtil.RESULT_ERR_MSG, "删除文件成功，同步数据库异常！");
		return map;
	}*/
	
}