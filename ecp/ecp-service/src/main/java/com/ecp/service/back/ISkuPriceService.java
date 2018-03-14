package com.ecp.service.back;

import java.util.List;

import com.ecp.entity.SkuPrice;
import com.ecp.service.IBaseService;

public interface ISkuPriceService extends IBaseService<SkuPrice, Long> {
	
	/**
	 * 根据itemId查询SKU价格
	 * @param itemId
	 * @return
	 */
	public List<SkuPrice> getByItemId(Long itemId);
	
	/**
	 * 根据itemId删除SKU价格
	 * @param itemId
	 * @return
	 */
	public int deleteByItemId(Long itemId);
	
}
