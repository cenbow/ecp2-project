package com.ecp.service.front;

import java.util.List;
import java.util.Map;

import com.ecp.bean.CategoryAttrBean;
import com.ecp.entity.CategoryAttr;
import com.ecp.service.IBaseService;

public interface ICategoryAttrService extends IBaseService<CategoryAttr, Long> {	
	
	
	/**
	 * @Description 读取类目的所有属性
	 * @param cid
	 * @return
	 */
	public List<CategoryAttrBean> getCategoryAttrListByCid(Long cid);
	
	
	/**
	 * @Description 按cid及属性类型读取类目属性列表
	 * @param cid  类目id
	 * @param attr_type  1:关键属性;2:不变属性；3：可变属性；4：销售属性
	 * @return
	 */
	public List<CategoryAttrBean> getCategoryAttrByCidAndType(Long cid,int attr_type);
	
	
	/**
	 * @Description 读取某类目的所有属性
	 * @param cid
	 * @return
	 */
	public List<Map<String,String>> getCategoryAttrListByCid1(Long cid);
	
	public List<CategoryAttr> findByCid(Long cid);  //test function
	
	/**
	 * @Description 保存类目属性
	 * @param cateAttrBean
	 */
	public void saveCategoryAttr(CategoryAttrBean cateAttrBean);  
	
	/**
	 * @Description //读取类目属性值列表
	 * @param cid
	 * @param attrId
	 * @return
	 * 	 每个map对象是一个值列表信息，结构如下：
	 * 		key			value
	 *      ------------------
	 * 		cid:
	 * 		attr_id: 
	 * 		value_id:
	 * 		value_name:
	 * 		------------------
	 * 		
	 */
	public List<Map<String,String>> getCategoryAttrValList(Long cid,Long attrId); 
	
	/**
	 * @Description 保存 类目-属性值
	 * @param cid  类目id
	 * @param attrId  类目属性id
	 * @param valueName   值名称
	 */
	public void saveCategoryAttrValue(Long cid,Long attrId,String valueName);
	
}
