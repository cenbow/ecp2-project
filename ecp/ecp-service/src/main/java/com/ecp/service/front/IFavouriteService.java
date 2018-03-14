package com.ecp.service.front;

import java.util.List;

import com.ecp.bean.FavouriteBean;
import com.ecp.bean.FavouriteStatisticBean;
import com.ecp.entity.UserFavorite;
import com.ecp.service.IBaseService;

public interface IFavouriteService extends IBaseService<UserFavorite, Long> {	
	
	
	/**
	 * @Description 加入到收藏
	 * @param itemId  SPU id 
	 * @param userId  user id
	 */
	/**
	 * @Description 加入到收藏
	 * @param itemId  SPU id 
	 * @param userId  user id
	 * @return 如果加入成功则返回插入影响的行数
	 */
	public int addToFavourite(long favouriteId,long userId);
	
	/**
	 * @Description 获取指定用户收藏
	 * @param userId
	 * @return
	 */
	public List<FavouriteBean> getFavouritesByUserId(long userId);
	
	/**
	 * @Description 获取指定用户收藏统计信息
	 * @param userId
	 * @return
	 */
	public List<FavouriteStatisticBean> getFavouriteStatistic(long userId);
	
	/**
	 * @Description 
	 * @param spuId  商品id
	 * @return true:已关注; false:未关注
	 */
	/**
	 * @Description 对于指定用户，查询商品是否已关注
	 * @param favouriteId
	 * @param userId
	 * @return
	 */
	public boolean isUserFavourite(long favouriteId,  long userId);
	
}
