package com.ecp.web.front;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ecp.bean.CategoryAttrBean;
import com.ecp.entity.Category;
import com.ecp.service.front.ICategoryAttrService;
import com.ecp.service.front.ICategoryService;

/**
 * @ClassName CategoryAttrController
 * @Description 类目、属性、类目属性值 维护控制器
 * @author Administrator
 * @Date 2017年5月5日 下午4:34:20
 * @version 1.0.0
 */
@Controller
@RequestMapping("/front/home")
public class CategoryAttrController {
	final String RESPONSE_THYMELEAF = "thymeleaf/front/";
	final String RESPONSE_JSP = "jsps/";

	@Autowired
	ICategoryService categoryService;

	@Autowired
	ICategoryAttrService categoryAttrService;

	
	/**
	 * @Description 导航至--->类目树
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/categorytree", method = RequestMethod.GET)
	public String categoryAttr(Model model) {
		return RESPONSE_THYMELEAF + "categorytree";
	}

	/**
	 * @Description 导航至--->类目属性编辑
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/categoryattreditor/{cid}", method = RequestMethod.GET)
	public String categoryAttrEditor(@PathVariable("cid") long cid, Model model) {
		// 查询相关叶子结点的属性
		List<CategoryAttrBean> cateAttrList = categoryAttrService.getCategoryAttrListByCid(cid);

		// test code
		// List<CategoryAttr> cateAttrList=categoryAttrService.findByCid(cid);
		// categoryAttrService.getCategoryAttrListByCid1(cid);

		model.addAttribute("categoryAttrList", cateAttrList);
		model.addAttribute("cid", cid); // 传递cid信息

		return RESPONSE_THYMELEAF + "categoryattreditor";
	}

	/**
	 * @Description 导航至--->增加类目属性
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addcategoryattr/{cid}", method = RequestMethod.GET)
	public String addCategoryAttr(@PathVariable("cid") long cid, Model model) {

		// model.addAttribute("categoryAttrList",cateAttrList);
		model.addAttribute("cid", cid); // 传递cid信息

		return RESPONSE_THYMELEAF + "addcategoryattr";
	}

	/**
	 * @Description 保存-类目属性
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/savecategoryattr", method = RequestMethod.POST)
	public String saveCategoryAttr(CategoryAttrBean cateAttrBean, Model model) {

		System.out.println("debug-----cid=" + cateAttrBean.getCid());
		System.out.println("debug-----attr_type=" + cateAttrBean.getAttr_type());
		
		//TODO 在此处对数据的有效性验证  ADD VALIDATION CODE HERE
		categoryAttrService.saveCategoryAttr(cateAttrBean);  

		return RESPONSE_THYMELEAF + "savecategoryattrok";
	}
	
	
	/**
	 * @Description 导航--->属性值编辑页面
	 * @param cid  类目ID
	 * @param attr_id  类目属性ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cate_attr_val_edit", method = RequestMethod.GET)
	public String categoryAttrValEdit(@RequestParam("cid") long cid,
									  @RequestParam("attr_id") long attr_id,
									  Model model) {

		 	 
		 //TODO   此处模拟
		 //以下语句为stub
		 //List<Map<String,String>> categoryAttrValList=new ArrayList<Map<String,String>>();
		 
		 //自服务层获取类目-属性-属性值 列表
		 List<Map<String,String>> categoryAttrValList=this.categoryAttrService.getCategoryAttrValList(cid, attr_id);
		 
		 model.addAttribute("categoryAttrValList",categoryAttrValList);
		 model.addAttribute("cid",cid);
		 model.addAttribute("attr_id",attr_id);

		return RESPONSE_THYMELEAF + "CategoryAttrValEdit";
	}

	
	/**
	 * 响应ajax请求
	 * 
	 * @Description 加载类目树数据
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loadcategorytree", method = RequestMethod.POST)
	@ResponseBody
	public Object LoadCategoryAttr(Model model) {

		List<Category> categoryList = categoryService.getAllCategory();

		return categoryList;
	}

		
	//saveCategoryAttrValue
	@RequestMapping(value = "/saveCategoryAttrValue", method = RequestMethod.POST)
	@ResponseBody
	public Object saveCategoryAttrValue(HttpServletRequest request, Model model) {

		String valueName = request.getParameter("valueName");
		long cid=Long.parseLong(request.getParameter("cid"));
		long attrId=Long.parseLong(request.getParameter("attrId"));
		
		//TODO save valueName into db
		categoryAttrService.saveCategoryAttrValue(cid, attrId, valueName);
		
		
		//返回状态数据
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", "保存成功!");
		jsonObject.put("status", "success");

		return jsonObject;
	}


}
