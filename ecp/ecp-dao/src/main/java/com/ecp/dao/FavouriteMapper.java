package com.ecp.dao;

import org.apache.ibatis.annotations.Param;

import com.ecp.entity.Favourite;

import tk.mybatis.mapper.common.Mapper;

public interface FavouriteMapper extends Mapper<Favourite> {
	public int addQuqntityById(@Param("id") int id, @Param("quantity") int quantity);  //对指定的sku增加数量
	
	/**
	 * @Description 根据用户ID获取SPU个数
	 * @param userId
	 * @return
	 */
	public int getItemNumByUserId(@Param("userId") long userId);
}