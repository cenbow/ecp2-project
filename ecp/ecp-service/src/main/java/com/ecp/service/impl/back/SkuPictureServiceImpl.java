package com.ecp.service.impl.back;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecp.dao.SkuPictureMapper;
import com.ecp.entity.SkuPicture;
import com.ecp.service.back.ISkuPictureService;
import com.ecp.service.impl.AbstractBaseService;

@Service("skuPictureServiceBean")
public class SkuPictureServiceImpl extends AbstractBaseService<SkuPicture, Long> implements ISkuPictureService {

	private SkuPictureMapper skuPictureMapper;

	/**
	 * @param skuPictureMapper the skuPictureMapper to set
	 * set方式注入
	 */
	public void setSkuPictureMapper(SkuPictureMapper skuPictureMapper) {
		this.skuPictureMapper = skuPictureMapper;
		this.setMapper(skuPictureMapper);
	}

	/**
	 * @see com.ecp.service.back.ISkuPictureService#getBySkuIds(java.util.List)
	 * 根据skuID集合查询sku图片
	 */
	@Override
	public List<SkuPicture> getBySkuIds(List<Long> skuIds) {
		return skuPictureMapper.getBySkuIds(skuIds);
	}

	/**
	 * @see com.ecp.service.back.ISkuPictureService#updateBySkuIds(java.util.List)
	 * 根据skuID集合删除sku图片
	 */
	@Override
	public int deleteBySkuIds(List<Long> skuIds) {
		return skuPictureMapper.updateBySkuIds(skuIds);
	}

}
