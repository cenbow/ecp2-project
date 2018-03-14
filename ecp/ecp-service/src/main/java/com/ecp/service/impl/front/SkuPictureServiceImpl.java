package com.ecp.service.impl.front;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecp.bean.DeletedType;
import com.ecp.dao.SkuPictureMapper;
import com.ecp.entity.SkuPicture;
import com.ecp.service.front.ISkuPictureService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class SkuPictureServiceImpl extends AbstractBaseService<SkuPicture, Long> implements ISkuPictureService {

	SkuPictureMapper skuPictureMapper;
	
	/**
	 * @param mapper
	 * the mapper to set set方式注入
	 */	
	public void setSkuPictureMapper(SkuPictureMapper skuPictureMapper) {
		this.skuPictureMapper=skuPictureMapper;
		this.setMapper(skuPictureMapper);
	}
	
	@Override
	public List<SkuPicture> getSkuPictureById(Long skuId) {
		SkuPicture record=new SkuPicture();
		record.setSkuId(skuId);		
		record.setDeleted(DeletedType.NO);  //增加在查询SKU图片时删除条件
		return skuPictureMapper.select(record);		
	}

	

	

}
