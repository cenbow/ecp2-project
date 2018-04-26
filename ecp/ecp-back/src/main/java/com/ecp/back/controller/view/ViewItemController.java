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

import com.ecp.back.commons.StaticConstants;
import com.ecp.bean.PageBean;
import com.ecp.bean.UserBean;
import com.ecp.common.util.FileUploadUtil;
import com.ecp.common.util.RequestResultUtil;
import com.ecp.entity.Category;
import com.ecp.entity.Item;
import com.ecp.entity.ItemPicture;
import com.ecp.entity.Sku;
import com.ecp.entity.SkuPicture;
import com.ecp.entity.SkuPrice;
import com.ecp.service.back.IAttributeService;
import com.ecp.service.back.IAttributeValueService;
import com.ecp.service.back.IBrandService;
import com.ecp.service.back.ICategoryService;
import com.ecp.service.back.IItemPictureService;
import com.ecp.service.back.IItemService;
import com.ecp.service.back.ISkuPictureService;
import com.ecp.service.back.ISkuPriceService;
import com.ecp.service.back.ISkuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Class: ItemController
 * 		查看商品Controller类
 * @author srd 
 * @version 1.0 $Date: 2017年5月7日 下午5:16:02
 */
@Controller
@RequestMapping("/back/view/item")
public class ViewItemController {

	private final Logger log = Logger.getLogger(getClass());
	
	@Resource(name="itemServiceBean")
	private IItemService iItemService;//商品
	@Resource(name="itemPictureServiceBean")
	private IItemPictureService itemPictureService;//商品图片
	
	@Resource(name="skuServiceBean")
	private ISkuService skuService;//SKU
	@Resource(name="skuPictureServiceBean")
	private ISkuPictureService skuPictureService;//SKU图片
	@Resource(name="skuPriceServiceBean")
	private ISkuPriceService skuPriceService;//SKU价格
	
	@Resource(name="brandServiceBean")
	private IBrandService iBrandService;//品牌
	@Resource(name="categoryServiceBean")
	private ICategoryService iCategoryService;//类目
	@Resource(name="attributeServiceBean")
	private IAttributeService iAttributeService;//类目的属性
	@Resource(name="attributeValueServiceBean")
	private IAttributeValueService iAttributeValueService;//类目的属性值
	
	List<Category> resultList = new ArrayList<Category>();//用户保存类目排序结果
	
	/**
	 * 方法功能：进入添加商品页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addItem")
	public ModelAndView addItem(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		//类目
		List<Category> categoryList = iCategoryService.selectByPid(0l);
		mav.addObject("categoryList", categoryList);
		
		mav.setViewName(StaticConstants.ADD_ITEM_PAGE);
		return mav;
	}
	
	/**
	 * 方法功能：查询列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectItems")
	public ModelAndView selectLinkItem(HttpServletRequest request, HttpServletResponse response, Boolean clickPageBtn, PageBean pageBean, String pagehelperFun, Long search_cate_first, Long search_cate_second, Long search_cate_third, String search_keywords) {
		ModelAndView mav = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		Map<String, Object> param = new HashMap<>();
		if(search_cate_first!=null && search_cate_first>0){
			param.put("search_cate_first", search_cate_first);
		}
		if(search_cate_second!=null && search_cate_second>0){
			param.put("search_cate_second", search_cate_second);
		}
		if(search_cate_third!=null && search_cate_third>0){
			param.put("search_cate_third", search_cate_third);
		}
		if(StringUtils.isNotBlank(search_keywords)){
			param.put("search_keywords", search_keywords);
		}
		
		PageHelper.startPage(pageBean.getPageNum(), 10);
		List<Map<String, Object>> itemList = iItemService.selectItemsByCondition(param);
		PageInfo<Map<String, Object>> pagehelper = new PageInfo<>(itemList);
		mav.addObject("pagehelper", pagehelper);
		
		List<Category> firstCategoryList = iCategoryService.selectByLev(1);
		mav.addObject("firstCategoryList", firstCategoryList);
		
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
		
		/*//品牌
		List<Brand> brandList = iBrandService.selectAll();
		mav.addObject("brandList", brandList);
		
		//属性
		List<Attribute> attrList = iAttributeService.selectAll();
		mav.addObject("attrList", attrList);
		
		//属性值
		List<AttributeValue> attrValueList = iAttributeValueService.selectAll();
		mav.addObject("attrValueList", attrValueList);*/
		
		if(clickPageBtn!=null && clickPageBtn){
			mav.setViewName(StaticConstants.VIEW_ITEM_MANAGE_TABLE_PAGE);
		}else{
			mav.setViewName(StaticConstants.VIEW_ITEM_MANAGE_PAGE);
		}
		
		mav.addObject("pagehelperFun", pagehelperFun);
		return mav;
	}
	
	/**
	 * 查询商品列表页面（对话框中显示）
	 * @param request
	 * @param response
	 * @param clickPageBtn
	 * @param pageBean
	 * @param pagehelperFun
	 * @param search_keywords
	 * @return
	 */
	@RequestMapping("/getItems")
	public ModelAndView selectItems(HttpServletRequest request, HttpServletResponse response, Boolean clickPageBtn, PageBean pageBean, String pagehelperFun, String search_keywords) {
		ModelAndView mav = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UserBean user = (UserBean)subject.getPrincipal();
		
		Map<String, Object> param = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(search_keywords)){
			param.put("search_keywords", search_keywords);
		}else{
			param = null;
		}
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<Map<String, Object>> itemList = iItemService.selectItemsByCondition(param);
		PageInfo<Map<String, Object>> pagehelper = new PageInfo<Map<String, Object>>(itemList);
		mav.addObject("pagehelper", pagehelper);
		
		if(clickPageBtn!=null && clickPageBtn){
			mav.setViewName(StaticConstants.ITEM_DIALOG_TABLE_PAGE);
		}else{
			mav.setViewName(StaticConstants.ITEM_DIALOG_PAGE);
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
			//查询商品信息
			Item item = iItemService.selectByPrimaryKey(id);
			//查询商品图片信息
			List<ItemPicture> pictureList = itemPictureService.getByItemId(id);
			
			//查询sku信息
			List<Sku> skuList = skuService.getByItemId(id);
			//查询sku价格信息
			List<SkuPrice> skuPriceList = skuPriceService.getByItemId(id);
			//查询sku图片信息
			List<Long> skuIds = new ArrayList<Long>();
			for(Sku sku : skuList){
				skuIds.add(sku.getSkuId());
			}
			List<SkuPicture> skuPictureList = skuPictureService.getBySkuIds(skuIds);
			
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			respM.put("item", item);
			respM.put("pictureList", pictureList);
			respM.put("skuList", skuList);
			respM.put("skuPriceList", skuPriceList);
			respM.put("skuPictureList", skuPictureList);
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
	 * @param item
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insertContent(HttpServletRequest request, HttpServletResponse response, Item item, String skuJson, String skuPriceJson, String skuShortSpec, String skuSpec) {
		
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean)subject.getPrincipal();
		
		log.info("insert item:"+item);
		
		if(userBean!=null){
			int rows = iItemService.saveItem(request, item, skuJson, skuPriceJson, skuShortSpec, skuSpec);
			if(rows>0){
				return RequestResultUtil.getResultAddSuccess();
			}else if(rows<0){
				return RequestResultUtil.getResultUploadWarn();
			}
		}
		return RequestResultUtil.getResultAddWarn();
	}
	
	/**
	 * 方法功能：修改
	 * @param request
	 * @param response
	 * @param item
	 * @return
	 */
	@RequestMapping("/updateById")
	@ResponseBody
	public Map<String, Object> updateById(HttpServletRequest request, HttpServletResponse response, Item item, String skuJson, String skuPriceJson, boolean isSaveSku, String skuShortSpec, String skuSpec) {
		log.info("update item:"+item);
		try {
			int rows = iItemService.updateItem(request, item, skuJson, skuPriceJson, isSaveSku, skuShortSpec, skuSpec);
			if(rows>0){
				return RequestResultUtil.getResultUpdateSuccess();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return RequestResultUtil.getResultUpdateWarn();
	}
	
	/**
	 * 方法功能：处理上传文件
	 * @param request
	 * @param 
	 * @return
	 * 		List<String>:上传成功返回路径集合
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年1月11日 下午6:09:34</p>
	 */
	private List<String> processUploadFile(HttpServletRequest request, List<ItemPicture> pictureList){
		try {
			//获取上传背景图文件
			List<String> filePathList = FileUploadUtil.getFiles2UploadPath(request, "item", "pictureImg");
			if(filePathList!=null && filePathList.size()>0){
				if(pictureList!=null && pictureList.size()>0){
					for(ItemPicture picture : pictureList){
						if(!FileUploadUtil.deleteFile(request, picture.getPictureUrl())){
							log.error("文件不存在或已删除 缩略图路径："+picture.getPictureUrl());
						}
					}
				}
				return filePathList;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.error("上传文件异常");
		return null;
	}
	
	
	/**
	 * 修改订单状态
	 * @param request
	 * @param response
	 * @param itemId
	 * @param itemStatus
	 * @return
	 */
	@RequestMapping("/updateStatusById")
	@ResponseBody
	public Map<String, Object> updateStatusById(HttpServletRequest request, HttpServletResponse response, String itemId, Integer itemStatus) {
		
		if(StringUtils.isBlank(itemId)){
			return RequestResultUtil.getResultUpdateWarn("参数为空");
		}
		
		int rows = iItemService.updateStatusBatchByIds(itemId, itemStatus);
		if(rows>0){
			return RequestResultUtil.getResultUpdateSuccess();
		}
		return RequestResultUtil.getResultUpdateWarn();
	}
	
	@RequestMapping("/updateStatusByIds")
	@ResponseBody
	public Map<String, Object> updateStatusBatchByIds(HttpServletRequest request, HttpServletResponse response, String itemIds, Integer itemStatus) {
		
		if(StringUtils.isBlank(itemIds)){
			return RequestResultUtil.getResultUpdateWarn("参数为空");
		}
		
		int rows = iItemService.updateStatusBatchByIds(itemIds, itemStatus);
		if(rows>0){
			return RequestResultUtil.getResultUpdateSuccess();
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
		int rows = iItemService.deleteByIds(id.toString());
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
	/**
	 * 方法功能：批量删除（逻辑删除）
	 * @param request
	 * @param response
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteByIds")
	@ResponseBody
	public Map<String, Object> deleteByIds(HttpServletRequest request, HttpServletResponse response, String ids) {
		int rows = iItemService.deleteByIds(ids);
		if(rows>0){
			return RequestResultUtil.getResultDeleteSuccess();
		}
		return RequestResultUtil.getResultDeleteWarn();
	}
	
	/**
	 * 查询要修改的sku规格
	 * @param request
	 * @param response
	 * @param skuId
	 * @return
	 */
	@RequestMapping("/selectSkuSpecBySkuId")
	@ResponseBody
	public Map<String, Object> selectSkuBySkuId(HttpServletRequest request, HttpServletResponse response, Long skuId) {
		try {
			Sku sku = skuService.selectByPrimaryKey(skuId);
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			if(sku!=null){
				respM.put("skuSpec", sku.getSkuSpec());
			}else{
				respM.put("skuSpec", "");
			}
			return respM;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询sku信息异常");
		}
		
		return RequestResultUtil.getResultSelectWarn();
	}
	
	/**
	 * 修改sku规格
	 * @param request
	 * @param response
	 * @param skuId
	 * @param skuSpec
	 * @return
	 */
	@RequestMapping("/updateSkuSpecBySkuId")
	@ResponseBody
	public Map<String, Object> updateSkuBySkuId(HttpServletRequest request, HttpServletResponse response, Long skuId, String skuSpec) {
		
		if(skuId==null || skuId<=0 || StringUtils.isBlank(skuSpec)){
			return RequestResultUtil.getResultUpdateWarn("参数不能为空!");
		}
		
		Sku sku = new Sku();
		sku.setSkuId(skuId);
		sku.setSkuSpec(skuSpec);
		int rows = skuService.updateByPrimaryKeySelective(sku);
		if(rows>0){
			return RequestResultUtil.getResultUpdateSuccess();
		}
		return RequestResultUtil.getResultUpdateWarn();
	}
	
	/**
	 * 查询要修改的sku规格
	 * @param request
	 * @param response
	 * @param skuId
	 * @return
	 */
	@RequestMapping("/selectSkuShortSpecBySkuId")
	@ResponseBody
	public Map<String, Object> selectSkuShortSpecBySkuId(HttpServletRequest request, HttpServletResponse response, Long skuId) {
		try {
			Sku sku = skuService.selectByPrimaryKey(skuId);
			Map<String, Object> respM = RequestResultUtil.getResultSelectSuccess();
			if(sku!=null){
				respM.put("skuShortSpec", sku.getSkuShortSpec());
			}else{
				respM.put("skuShortSpec", "");
			}
			return respM;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询sku信息异常");
		}
		
		return RequestResultUtil.getResultSelectWarn();
	}
	
	/**
	 * 修改sku简单规格
	 * @param request
	 * @param response
	 * @param skuId
	 * @param skuShortSpec
	 * @return
	 */
	@RequestMapping("/updateSkuShortSpecBySkuId")
	@ResponseBody
	public Map<String, Object> updateSkuShortSpecBySkuId(HttpServletRequest request, HttpServletResponse response, Long skuId, String skuShortSpec) {
		
		if(skuId==null || skuId<=0 || StringUtils.isBlank(skuShortSpec)){
			return RequestResultUtil.getResultUpdateWarn("参数不能为空!");
		}
		
		Sku sku = new Sku();
		sku.setSkuId(skuId);
		sku.setSkuShortSpec(skuShortSpec);
		int rows = skuService.updateByPrimaryKeySelective(sku);
		if(rows>0){
			return RequestResultUtil.getResultUpdateSuccess();
		}
		return RequestResultUtil.getResultUpdateWarn();
	}
	
}
