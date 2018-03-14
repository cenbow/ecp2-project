package com.ecp.back.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ecp.back.commons.StaticConstants;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.Brand;
import com.ecp.entity.Category;
import com.ecp.entity.CategoryBrand;
import com.ecp.service.back.IBrandService;
import com.ecp.service.back.ICategoryBrandService;
import com.ecp.service.back.ICategoryService;

/**
 * Class: CategoryBrandController.java
 * 		类目品牌CategoryBrandController类
 * @author srd 
 * @version 1.0 $Date: 2017年5月20日 下午2:16:02
 */
@Controller
@RequestMapping("/back/category-brand")
public class CategoryBrandController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Resource(name="brandServiceBean")
	private IBrandService iBrandService;//品牌
	
	@Resource(name="categoryServiceBean")
	private ICategoryService iCategoryService;// 类目
	
	@Resource(name="categoryBrandServiceBean")
	private ICategoryBrandService iCategoryBrandService;// 类目品牌

	/**
	 * 获取类目列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getCategotyItems")
	public ModelAndView goBrandPage(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		
		List<Category> categoryList = iCategoryService.getAll(null);
		mav.addObject("ztreeNodes", JSON.toJSONString(categoryList));
		
		List<Brand> brandList = iBrandService.getAll();
		mav.addObject("brandList", brandList);
		
		mav.setViewName(StaticConstants.CATEGORY_BRAND_MANAGE_PAGE);
		
		return mav;
	}
	
	/**
	 * 根据类目ID查询品牌（初始化已选择和未选择的品牌）
	 * @param request
	 * @param response
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/selectBrandByCid")
	@ResponseBody
	public Map<String, Object> selectBrandByCid(HttpServletRequest request, HttpServletResponse response, Long categoryId) {
		try {
			List<Brand> brandList = iBrandService.getAll();//查询所有品牌
			List<Map<String, Object>> brandMapList = iBrandService.getBrandByCategoryId(categoryId);//查询当前类目的品牌
			
			for(Map<String, Object> map : brandMapList){
				String brand_id = map.get("brand_id").toString();
				if(StringUtils.isNotBlank(brand_id)){
					Long brandId = Long.valueOf(brand_id);
					Iterator<Brand> ite = brandList.iterator();
					while (ite.hasNext()) {
						Brand temp = (Brand) ite.next();
						if(temp.getBrandId().equals(brandId)){
							ite.remove();
							break;
						}
					}
				}
			}
			
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("noBrandList", brandList);
			respM.put("yesBrandMapList", brandMapList);
			return respM;
		} catch (Exception e) {
			log.error("查询异常", e);
			return RequestResultUtil.getResultSelectWarn();
		}
	}
	
	/**
	 * 保存类目品牌
	 * @param request
	 * @param response
	 * @param secondLevCid
	 * @param thirdLevCid
	 * @param brandListJson
	 * @return
	 */
	@RequestMapping("/saveCategoryBrand")
	@ResponseBody
	public Map<String, Object> saveCategoryBrand(HttpServletRequest request, HttpServletResponse response, Long secondLevCid, Long thirdLevCid, String brandListJson) {
		try {
			
			int rows = iCategoryBrandService.saveCategoryBrand(secondLevCid, thirdLevCid, brandListJson);
			if(rows>0){
				return RequestResultUtil.getResultSaveSuccess();
			}
		} catch (Exception e) {
			log.error("查询异常", e);
		}
		return RequestResultUtil.getResultSaveWarn();
	}
	
}