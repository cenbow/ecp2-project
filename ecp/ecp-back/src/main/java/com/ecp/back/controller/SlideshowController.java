package com.ecp.back.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import com.ecp.back.commons.StaticConstants;
import com.ecp.bean.DeletedType;
import com.ecp.bean.PageBean;
import com.ecp.bean.UserBean;
import com.ecp.common.util.FileUploadUtil;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.Category;
import com.ecp.entity.SlideshowSetting;
import com.ecp.service.back.ICategoryService;
import com.ecp.service.back.ISlideshowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Class: SlideshowController.java
 * 		推荐管理SlideshowController类
 * @author srd 
 * @version 1.0 $Date: 2017年9月20日 下午8:16:02
 */
@Controller
@RequestMapping("/back/slideshow")
public class SlideshowController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Resource(name="slideshowServiceBean")
	private ISlideshowService slideshowService;//轮播图
	
	@Resource(name="categoryServiceBean")
	private ICategoryService iCategoryService;//类目
	
	List<Category> resultList = new ArrayList<Category>();//用户保存类目排序结果

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
		List<SlideshowSetting> slideshowList = slideshowService.getAll();
		PageInfo<SlideshowSetting> pagehelper = new PageInfo<SlideshowSetting>(slideshowList);
		
		mav.addObject("pagehelper", pagehelper);
		
		//类目
		List<Category> categoryList = iCategoryService.getAll(null);
		//List<Category> categoryList = iCategoryService.selectByLev(3);
		resultList.clear();
		for(int i=0; i<categoryList.size(); i++){
			Category category = categoryList.get(i);
			if(category.getParentCid()==0){
				resultList.add(category);
				sortList(categoryList,category.getCid());
			}
		}
		mav.addObject("categoryList", resultList);
		
		if(clickPageBtn!=null && clickPageBtn){
			mav.setViewName(StaticConstants.SLIDESHOW_MANAGE_TABLE_PAGE);
		}else{
			mav.setViewName(StaticConstants.SLIDESHOW_MANAGE_PAGE);
		}
		
		mav.addObject("pagehelperFun", pagehelperFun);
		return mav;
	}
	
	/**
	 * List排序
	 * @param categoryList
	 * @param cid
	 */
	private void sortList(List<Category> categoryList,Long cid) {  
        for(Category category :categoryList){
            if(category.getParentCid().equals(cid)){
                resultList.add(category);
                sortList(categoryList,category.getCid());
            }
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
			SlideshowSetting slideshow = slideshowService.selectByPrimaryKey(id);
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("slideshow", slideshow);
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
	 * @param slideshow
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response, SlideshowSetting slideshow) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		
		//处理上传文件
		if(!this.processUploadFile(request, slideshow)){
			return RequestResultUtil.getResultUploadWarn();
		}
		
		if(userBean!=null){
			slideshow.setDeleted(DeletedType.NO);
			slideshow.setCreated(new Date());
			slideshow.setUpdated(new Date());
			
			int rows = slideshowService.insertSelective(slideshow);
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
	 * @param slideshow
	 * @return
	 */
	@RequestMapping("/updateById")
	@ResponseBody
	public Map<String, Object> updateById(HttpServletRequest request, HttpServletResponse response, SlideshowSetting slideshow) {
		
		try {
			//处理上传文件
			if(!this.processUploadFile(request, slideshow)){
				return RequestResultUtil.getResultUploadWarn();
			}
			
			slideshow.setUpdated(new Date());
			int rows = slideshowService.updateByPrimaryKeySelective(slideshow);
			if(rows>0){
				return RequestResultUtil.getResultUpdateSuccess();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return RequestResultUtil.getResultUpdateWarn();
	}
	
	/**
	 * 方法功能：修改是否显示状态
	 * @param request
	 * @param response
	 * @param slideshowId
	 * @param showed
	 * @return
	 */
	@RequestMapping("/updateShowedStatusById")
	@ResponseBody
	public Map<String, Object> updateShowedStatusById(HttpServletRequest request, HttpServletResponse response, Long slideshowId, Integer showed) {
		
		try {
			if((slideshowId==null || slideshowId<=0) || (showed==null && showed<=0)){
				RequestResultUtil.getResultUpdateWarn("ID或显示状态为空！");
			}
			SlideshowSetting slideshow = new SlideshowSetting();
			slideshow.setId(slideshowId);
			slideshow.setShowed(showed);
			int rows = slideshowService.updateByPrimaryKeySelective(slideshow);
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
		int rows = slideshowService.deleteById(id);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
	/**
	 * 方法功能：处理上传文件
	 * @param request
	 * @param slideshow
	 * @return
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年1月11日 下午6:09:34</p>
	 */
	private boolean processUploadFile(HttpServletRequest request, SlideshowSetting slideshow){
		boolean flag = false;
		try {
			//获取上传轮播图
			String backImgPath = FileUploadUtil.getFile2Upload(request, "back slideshow img", "slideshow");
			if(StringUtils.isNotBlank(backImgPath)){
				if(!FileUploadUtil.deleteFile(request, slideshow.getImgUrl())){
					log.error("文件不存在或已删除 logo图路径："+slideshow.getImgUrl());
				}
				slideshow.setImgUrl(backImgPath);
			}
			flag = true;
		} catch (IOException e) {
			log.error("上传文件异常", e);
		} catch (Exception e) {
			log.error("删除上传文件异常", e);
		}
		return flag;
	}
	
}