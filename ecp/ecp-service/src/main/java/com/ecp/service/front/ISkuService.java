package com.ecp.service.front;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecp.bean.SkuPriceBean;
import com.ecp.entity.Sku;
import com.ecp.service.IBaseService;

public interface ISkuService extends IBaseService<Sku, Long> {	
	
	
	
	/**
	 * @Description 根据指定SPU下的sku
	 * @param itemId   SPU id
	 * @param sku_type 1:主sku   2:非主sku 
	 * @return
	 */
	public List<SkuPriceBean> getSkuByIdAndType(Long itemId,int sku_type);	
	
	/**
	 * @Description 根据sku attributes 查询 sku
	 * @param itemId
	 * @param skuAttribute
	 * @return
	 */
	public List<SkuPriceBean> getSkuByIdAndAttr(Long itemId,String skuAttribute);
	
	
	public SkuPriceBean getSkuBySkuId(long skuId);
	
	
	/**
	 * @Description 获取sku商品介绍
	 * @param skuId
	 * @return
	 */
	public List<Map<String,String>> getSkuIntroduce(long skuId);
	
}
