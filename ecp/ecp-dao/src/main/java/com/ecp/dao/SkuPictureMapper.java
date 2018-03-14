package com.ecp.dao;

import java.util.List;

import com.ecp.entity.SkuPicture;

import tk.mybatis.mapper.common.Mapper;

public interface SkuPictureMapper extends Mapper<SkuPicture> {
	
	/**
	 * 根据skuID集合查询sku图片
	 * @param skuIds
	 * @return
	 */
	public List<SkuPicture> getBySkuIds(List<Long> skuIds);
	
	/**
	 * 根据skuID集合删除sku图片
	 * @param skuIds
	 * @return
	 */
	public int deleteBySkuIds(List<Long> skuIds);
	
	/**
	 * 根据skuID集合修改sku图片
	 * @param skuIds
	 * @return
	 */
	public int updateBySkuIds(List<Long> skuIds);
	
}