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
import com.ecp.entity.Brand;
import com.ecp.entity.Category;
import com.ecp.entity.Recommend;
import com.ecp.service.back.ICategoryService;
import com.ecp.service.back.IRecommendService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Class: RecommendController.java
 * 		推荐管理RecommendController类
 * @author srd 
 * @version 1.0 $Date: 2017年9月20日 下午8:16:02
 */
@Controller
@RequestMapping("/back/recommend")
public class RecommendController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Resource(name="recommendServiceBean")
	private IRecommendService recommendService;//推荐
	
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
		List<Recommend> recommendList = recommendService.getAll();
		PageInfo<Recommend> pagehelper = new PageInfo<Recommend>(recommendList);
		
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
			mav.setViewName(StaticConstants.RECOMMEND_MANAGE_TABLE_PAGE);
		}else{
			mav.setViewName(StaticConstants.RECOMMEND_MANAGE_PAGE);
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
			Recommend recommend = recommendService.selectByPrimaryKey(id);
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("recommend", recommend);
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
	 * @param recommend
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response, Recommend recommend) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		
		if(userBean!=null){
			recommend.setDeleted(DeletedType.NO);
			recommend.setCreated(new Date());
			recommend.setUpdated(new Date());
			
			int rows = recommendService.insertSelective(recommend);
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
	 * @param recommend
	 * @return
	 */
	@RequestMapping("/updateById")
	@ResponseBody
	public Map<String, Object> updateById(HttpServletRequest request, HttpServletResponse response, Recommend recommend) {
		
		try {
			recommend.setUpdated(new Date());
			int rows = recommendService.updateByPrimaryKeySelective(recommend);
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
	 * @param recommendId
	 * @param showed
	 * @return
	 */
	@RequestMapping("/updateShowedStatusById")
	@ResponseBody
	public Map<String, Object> updateShowedStatusById(HttpServletRequest request, HttpServletResponse response, Long recommendId, Integer showed) {
		
		try {
			if((recommendId==null || recommendId<=0) || (showed==null && showed<=0)){
				RequestResultUtil.getResultUpdateWarn("ID或显示状态为空！");
			}
			Recommend recommend = new Recommend();
			recommend.setId(recommendId);
			recommend.setShowed(showed);
			int rows = recommendService.updateByPrimaryKeySelective(recommend);
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
		int rows = recommendService.deleteById(id);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
}