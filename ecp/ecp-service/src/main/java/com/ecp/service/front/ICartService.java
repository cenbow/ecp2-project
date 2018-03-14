package com.ecp.service.front;

import java.util.List;

import com.ecp.entity.Favourite;
import com.ecp.service.IBaseService;

public interface ICartService extends IBaseService<Favourite,Integer>{	
	
	/**
	 * @Description 将sku加入cart
	 * @param itemId  spu id
	 * @param skuId   sku id
	 * @param quantity  数量
	 * @param userId    用户 id
	 * @return 返回所插入的购物车条目id
	 */
	public int addToCart(int itemId,int skuId,int quantity,int userId);
	
	/**
	 * @Description 获取指定用户购物车中商品数量
	 * @param userId
	 */
	public int  getItemNumByUserId(long userId);
	
	/**
	 * @Description 获取指定用户购物车条目列表
	 * @param userId
	 * @param status
	 * @return
	 */
	public List<Favourite> getCartItemByUserId(long userId,byte status);
	
}
