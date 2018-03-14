package com.ecp.service.front;

import java.util.List;

import com.ecp.entity.SkuPicture;
import com.ecp.service.IBaseService;

public interface ISkuPictureService extends IBaseService<SkuPicture, Long> {	
	
	
	
	/**
	 * @Description 根据指定SPU下的sku
	 * @param itemId   SPU id
	 * @param sku_type 1:主sku   2:非主sku 
	 * @return
	 */
	public List<SkuPicture> getSkuPictureById(Long skuId);	
	
	
}
