package com.ecp.service.impl.front;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecp.dao.ItemAttrValueMapper;
import com.ecp.entity.ItemAttrValue;
import com.ecp.service.front.IItemAttrService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class ItemAttrServiceImpl extends AbstractBaseService<ItemAttrValue, Long> implements IItemAttrService {
	
	ItemAttrValueMapper itemAttrValueMapper;
	
	/**
	 * @param mapper
	 * the mapper to set set方式注入
	 */	
	public void setItemAttrValueMapper(ItemAttrValueMapper itemAttrValueMapper) {
		this.itemAttrValueMapper=itemAttrValueMapper;
		this.setMapper(itemAttrValueMapper);
	}
	
	
	@Override
	public List<Map<String, String>> getItemAttrValList(Long itemId, Long attrId) {		
		return itemAttrValueMapper.getItemAttrValList(itemId, attrId);
	}
	
}
