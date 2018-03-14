package com.ecp.service.back;

import com.ecp.entity.ItemAttrValue;
import com.ecp.service.IBaseService;

public interface IItemAttrValueService extends IBaseService<ItemAttrValue, Long> {
	
	/**
	 * 根据商品ID删除
	 * @param itemId
	 * @return
	 */
	public int deleteByItemId(Long itemId);
}
