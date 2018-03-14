package com.ecp.service.front;

import java.util.List;
import java.util.Map;

import com.ecp.entity.ItemAttrValue;
import com.ecp.service.IBaseService;

public interface IItemAttrService extends IBaseService<ItemAttrValue, Long> {	
	
	/**
	 * @Description 	读取 item-属性值 列表
	 * @param itemId  	商品id
	 * @param attrId	属性id
	 * @return
	 * 	 每个map对象是一个值列表信息，结构如下：
	 * 		key			value
	 *      ------------------
	 * 		item_id:
	 * 		attr_id: 
	 * 		value_id:
	 * 		value_name:
	 * 		------------------
	 * 		
	 */
	public List<Map<String,String>> getItemAttrValList(Long itemId,Long attrId); 
	
	
	
}
