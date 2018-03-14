package com.ecp.back.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ecp.back.commons.StaticConstants;
import com.ecp.bean.CategoryAttrBean;
import com.ecp.bean.PageBean;
import com.ecp.bean.UserBean;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.Attribute;
import com.ecp.entity.AttributeValue;
import com.ecp.entity.CategoryAttr;
import com.ecp.entity.CategoryAttrValue;
import com.ecp.service.back.IAttributeService;
import com.ecp.service.back.IAttributeValueService;
import com.ecp.service.back.ICategoryAttrService;
import com.ecp.service.back.ICategoryAttrValueService;
import com.ecp.service.back.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName CategoryAttrController
 * @Description 类目、属性、类目属性值 维护控制器
 * @author Administrator
 * @Date 2017年5月5日 下午4:34:20
 * @version 1.0.0
 */
@Controller
@RequestMapping("/back/attr")
public class CategoryAttrController {
	final String RESPONSE_THYMELEAF = "back/thymeleaf/";
	final String RESPONSE_JSP = "jsp/";

	@Resource(name = "categoryServiceBean")
	ICategoryService categoryService;

	@Resource(name = "categoryAttrServiceBean")
	ICategoryAttrService categoryAttrService;

	@Resource(name="attributeServiceBean")
	IAttributeService attributeService;
	@Resource(name="categoryAttrValueServiceBean")
	ICategoryAttrValueService categoryAttrValService;
	@Resource(name="attributeValueServiceBean")
	IAttributeValueService attributeValueService;
	
	/**
	 * @Description 导航至--->类目树
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/categorytree", method = RequestMethod.GET)
	public ModelAndView categoryAttr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		List<Map<String, Object>> categoryList = categoryService.getAllCategory();
		mav.addObject("categoryListJson", JSON.toJSONString(categoryList));
		mav.setViewName(StaticConstants.CATEGORY_ATTR_MANAGE_PAGE);
		return mav;
	}

	/**
	 * @Description 导根据类目ID查询类目属性列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getCategoryAttrItem")
	public ModelAndView categoryAttrEditor(HttpServletRequest request, HttpServletResponse response, PageBean pageBean,
			String pagehelperFun, Long categoryId) {

		ModelAndView mav = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean) subject.getPrincipal();

		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		// 查询相关叶子结点的属性
		List<CategoryAttrBean> cateAttrList = categoryAttrService.getCategoryAttrListByCid(categoryId);
		PageInfo<CategoryAttrBean> pagehelper = new PageInfo<CategoryAttrBean>(cateAttrList);

		mav.addObject("pagehelper", pagehelper);

		mav.setViewName(StaticConstants.CATEGORY_ATTR_MANAGE_TABLE_PAGE);

		mav.addObject("pagehelperFun", pagehelperFun);
		return mav;
	}

	/**
	 * @Description 导航至--->增加类目属性
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value = "/addcategoryattr/{cid}", method = RequestMethod.GET)
	public String addCategoryAttr(@PathVariable("cid") long cid, Model model) {

		// model.addAttribute("categoryAttrList",cateAttrList);
		model.addAttribute("cid", cid); // 传递cid信息

		return RESPONSE_THYMELEAF + "addcategoryattr";
	}*/

	/**
	 * @Description 导根据类目ID和类目属性属性ID查询类目属性值列表
	 * @param cid
	 *            类目ID
	 * @param attr_id
	 *            类目属性ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getCategoryAttrValItem")
	public ModelAndView categoryAttrValEdit(HttpServletRequest request, HttpServletResponse response, PageBean pageBean,
			String pagehelperFun, Long categoryId, Long attributeId) {

		ModelAndView mav = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean) subject.getPrincipal();

		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		// 自服务层获取类目-属性-属性值 列表
		List<Map<String, String>> categoryAttrValList = this.categoryAttrService.getCategoryAttrValList(categoryId, attributeId);
		PageInfo<Map<String, String>> pagehelper = new PageInfo<Map<String, String>>(categoryAttrValList);

		mav.addObject("pagehelper", pagehelper);
		mav.setViewName(StaticConstants.CATEGORY_ATTR_VALUE_MANAGE_TABLE_PAGE);

		mav.addObject("pagehelperFun", pagehelperFun);
		return mav;
	}

	/**
	 * 响应ajax请求 未用
	 * 
	 * @Description 加载类目树数据
	 * @param model
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/loadcategorytree", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public Object LoadCategoryAttr(Model model) {
	 * 
	 * List<Map<String, Object>> categoryList =
	 * categoryService.getAllCategory();
	 * 
	 * return categoryList; }
	 */

	/**
	 * @Description 保存-类目属性
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/savecategoryattr")
	@ResponseBody
	public Map<String, Object> saveCategoryAttr(HttpServletRequest request, HttpServletResponse response, Attribute attribute, CategoryAttr categoryAttr) {

		// TODO 在此处对数据的有效性验证 ADD VALIDATION CODE HERE
		int rows = categoryAttrService.saveCategoryAttr(attribute, categoryAttr);
		
		if(rows>0){
			return RequestResultUtil.getResultSaveSuccess();
		}
		
		return RequestResultUtil.getResultSaveWarn();
	}
	
	/**
	 * 保存类目属性值
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/saveCategoryAttrValue")
	@ResponseBody
	public Map<String, Object> saveCategoryAttrValue(HttpServletRequest request, HttpServletResponse response, AttributeValue attrValue, CategoryAttrValue categoryAttrValue) {

		// TODO save valueName into db
		int rows = categoryAttrService.saveCategoryAttrValue(attrValue, categoryAttrValue);

		if(rows>0){
			return RequestResultUtil.getResultSaveSuccess();
		}
		
		return RequestResultUtil.getResultSaveWarn();
	}
	
	/**
	 * 查询要修改的类目属性
	 * @param request
	 * @param response
	 * @param attrId
	 * @return
	 */
	@RequestMapping(value = "/selectAttrUpdateById")
	@ResponseBody
	public Map<String, Object> selectAttrUpdateById(HttpServletRequest request, HttpServletResponse response, Long attrId) {

		try {
			Attribute attribute = attributeService.selectByPrimaryKey(attrId);
			CategoryAttr categoryAttr = categoryAttrService.getByAttrId(attrId);
			if(attribute!=null && categoryAttr!=null){
				Map<String, Object> resqMap = RequestResultUtil.getResultSelectSuccess();
				resqMap.put("attribute", attribute);
				resqMap.put("categoryAttr", categoryAttr);
				return resqMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return RequestResultUtil.getResultSelectWarn();
	}
	
	/**
	 * 查询要修改的类目属性值
	 * @param request
	 * @param response
	 * @param valueId
	 * @return
	 */
	@RequestMapping(value = "/selectAttrValUpdateById")
	@ResponseBody
	public Map<String, Object> selectAttrValUpdateById(HttpServletRequest request, HttpServletResponse response, Long valueId) {

		try {
			AttributeValue attrValue = attributeValueService.selectByPrimaryKey(valueId);
			CategoryAttrValue categoryAttrVal = categoryAttrValService.getByValueId(valueId);
			if(attrValue!=null && categoryAttrVal!=null){
				Map<String, Object> resqMap = RequestResultUtil.getResultSelectSuccess();
				resqMap.put("attrValue", attrValue);
				resqMap.put("categoryAttrVal", categoryAttrVal);
				return resqMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return RequestResultUtil.getResultSelectWarn();
	}
	
	/**
	 * 根据类目属性ID删除类目属性
	 * @param request
	 * @param response
	 * @param attrId
	 * @return
	 */
	@RequestMapping(value = "/delCategoryAttr")
	@ResponseBody
	public Map<String, Object> delCategoryAttr(HttpServletRequest request, HttpServletResponse response, Long attrId) {

		int rows = categoryAttrService.delCategoryAttr(attrId);

		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		
		return RequestResultUtil.getResultDeleteWarn();
	}
	
	/**
	 * 根据类目属性值ID删除类目属性值
	 * @param request
	 * @param response
	 * @param valueId
	 * @return
	 */
	@RequestMapping(value = "/delCategoryAttrValue")
	@ResponseBody
	public Map<String, Object> delCategoryAttrValue(HttpServletRequest request, HttpServletResponse response, Long valueId) {

		int rows = categoryAttrService.delCategoryAttrVal(valueId);

		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		
		return RequestResultUtil.getResultDeleteWarn();
	}

}
