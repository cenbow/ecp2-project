package com.ecp.back.controller;

import java.io.IOException;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.ecp.back.commons.SessionConstants;
import com.ecp.back.commons.StaticConstants;
import com.ecp.bean.PageBean;
import com.ecp.bean.UserBean;
import com.ecp.common.util.FileUploadUtil;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.Brand;
import com.ecp.service.back.IBrandService;
import com.ecp.service.back.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Class: BrandController.java
 * 		用户等级BrandController类
 * @author srd 
 * @version 1.0 $Date: 2017年5月20日 下午2:16:02
 */
@Controller
@RequestMapping("/back/brand")
public class BrandController {

	private final Logger log = Logger.getLogger(getClass());
	
	//@Autowired
	@Resource(name="brandServiceBean")
	private IBrandService iBrandService;
	
	@Resource(name="categoryServiceBean")
	private ICategoryService iCategoryService;// 类目

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
		List<Brand> brandList = iBrandService.getAll();
		PageInfo<Brand> pagehelper = new PageInfo<Brand>(brandList);
		
		mav.addObject("pagehelper", pagehelper);
		
		if(clickPageBtn!=null && clickPageBtn){
			mav.setViewName(StaticConstants.BRAND_MANAGE_TABLE_PAGE);
		}else{
			mav.setViewName(StaticConstants.BRAND_MANAGE_PAGE);
		}
		
		mav.addObject("pagehelperFun", pagehelperFun);
		return mav;
	}
	
	/**
	 * 根据类目查询品牌
	 * @param request
	 * @param response
	 * @param cid
	 * @return
	 */
	@RequestMapping("/selectByCid")
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
			Brand brand = iBrandService.selectByPrimaryKey(id);
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("brand", brand);
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
	 * @param brand
	 * @return
	 */
	@RequestMapping("/insert")
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
	}
	
	/**
	 * 方法功能：修改
	 * @param request
	 * @param response
	 * @param brand
	 * @return
	 */
	@RequestMapping("/updateById")
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
	}
	
	/**
	 * 方法功能：删除（逻辑删除）
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public Map<String, Object> deleteById(HttpServletRequest request, HttpServletResponse response, Long id) {
		int rows = iBrandService.deleteById(id);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
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
	private boolean processUploadFile(HttpServletRequest request, Brand brand){
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
	}
	
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
	private void deleteUploadFile(HttpServletRequest request, Brand brand) throws Exception{
		if(StringUtils.isNotBlank(brand.getBrandLogoUrl())){
			/*删除已上传的logo图*/
			FileUploadUtil.deleteFile(request, brand.getBrandLogoUrl());
			brand.setBrandLogoUrl("");
		}
	}
	
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
	@RequestMapping("/deleteUploadFileById")
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
	}
	
}