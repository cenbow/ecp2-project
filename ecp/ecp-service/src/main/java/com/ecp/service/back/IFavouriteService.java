package com.ecp.service.back;

import com.ecp.entity.Favourite;
import com.ecp.service.IBaseService;

public interface IFavouriteService extends IBaseService<Favourite, Long> {	
	
	/**
	 * 删除购物车中的商品（逻辑删除）
	 * @param itemId
	 * @return
	 */
	public int deleteByItemId(long itemId);
	
}
