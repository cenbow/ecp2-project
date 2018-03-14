package com.ecp.service.back;

import java.util.List;

import com.ecp.entity.Sku;
import com.ecp.service.IBaseService;

public interface ISkuService extends IBaseService<Sku, Long> {
	
	/**
	 * 根据商品ID查询商品SKU
	 * @param itemId
	 * @return
	 */
	public List<Sku> getByItemId(Long itemId);
	
	/**
	 * 根据商品ID删除商品SKU
	 * @param itemId
	 * @return
	 */
	public int deleteByItemId(Long itemId);
	
}
