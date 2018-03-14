package com.ecp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.bean.SkuPriceBean;
import com.ecp.entity.Sku;

import tk.mybatis.mapper.common.Mapper;

public interface SkuMapper extends Mapper<Sku> {
	
	List<SkuPriceBean> getSkuByIdAndType(@Param("itemId") Long itemId,@Param("skuType") int skuType);
	
	List<SkuPriceBean> getSkuByIdAndAttr(@Param("itemId") Long itemId,@Param("skuAttribute") String skuAttribute);
	
	public SkuPriceBean getSkuBySkuId(@Param("skuId") long skuId);
	
	/**
	 * @Description 根据skuid获取sku介绍
	 * @param skuId
	 * @return
	 */
	public List<Map<String,String>> getSkuIntroduce(@Param("skuId") long skuId);
	
}