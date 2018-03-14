package com.ecp.service.back;

import java.util.List;
import java.util.Map;

import com.ecp.bean.CategoryAttrBean;
import com.ecp.entity.Attribute;
import com.ecp.entity.AttributeValue;
import com.ecp.entity.CategoryAttr;
import com.ecp.entity.CategoryAttrValue;
import com.ecp.service.IBaseService;

public interface ICategoryAttrService extends IBaseService<CategoryAttr, Long> {	
	
	//读取某类目的所有属性列表
	public List<CategoryAttrBean> getCategoryAttrListByCid(Long cid);
	
	//读取某类目的所有属性列表
	public List<Map<String,String>> getCategoryAttrListByCid1(Long cid);
	
	public List<CategoryAttr> findByCid(Long cid);  //test function
	
	/**
	 * 保存类目属性（添加或修改）
	 * @param attribute
	 * @param categoryAttr
	 * @return
	 */
	public int saveCategoryAttr(Attribute attribute, CategoryAttr categoryAttr);  //保存类目属性
	
	/**
	 * @Description //读取类目属性值列表
	 * @param cid
	 * @param attrId
	 * @return
	 * 	 每个map对象是一个值列表信息，结构如下：
	 * 		key		value
	 *      -------------
	 * 		cid:
	 * 		attr_id: 
	 * 		value_id:
	 * 		value_name:
	 * 		-------------
	 * 		
	 */
	public List<Map<String,String>> getCategoryAttrValList(Long cid,Long attrId); 
	
	/**
	 * 保存类目属性值（添加或修改）
	 * @param attrValue
	 * @param categoryAttrValue
	 * @return
	 */
	public int saveCategoryAttrValue(AttributeValue attrValue, CategoryAttrValue categoryAttrValue);
	
	/**
	 * 根据属性ID查询类目属性表
	 * @param attrId
	 * @return
	 */
	public CategoryAttr getByAttrId(Long attrId);
	
	/**
	 * 根据属性ID删除属性
	 * @param attrId
	 * @return
	 */
	public int delCategoryAttr(Long attrId);
	
	/**
	 * 根据属性值ID删除属性值
	 * @param valueId
	 * @return
	 */
	public int delCategoryAttrVal(Long valueId);
	
}
