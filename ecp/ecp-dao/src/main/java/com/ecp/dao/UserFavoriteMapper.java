package com.ecp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecp.bean.FavouriteBean;
import com.ecp.bean.FavouriteStatisticBean;
import com.ecp.entity.UserFavorite;

import tk.mybatis.mapper.common.Mapper;

public interface UserFavoriteMapper extends Mapper<UserFavorite> {

	public List<FavouriteBean> getFavouritesByUserId(@Param("userId") long userId);

	public List<FavouriteStatisticBean> getFavouriteStatistic(@Param("userId") long userId);

}