package com.ecp.service.impl.back;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecp.bean.DeletedType;
import com.ecp.dao.SkuPriceMapper;
import com.ecp.entity.Sku;
import com.ecp.entity.SkuPrice;
import com.ecp.service.back.ISkuPriceService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("skuPriceServiceBean")
public class SkuPriceServiceImpl extends AbstractBaseService<SkuPrice, Long> implements ISkuPriceService {

	private SkuPriceMapper skuPriceMapper;

	/**
	 * @param skuPriceMapper the skuPriceMapper to set
	 * set方式注入
	 */
	public void setSkuPriceMapper(SkuPriceMapper skuPriceMapper) {
		this.skuPriceMapper = skuPriceMapper;
		this.setMapper(skuPriceMapper);
	}

	/**
	 * @see com.ecp.service.back.ISkuPriceService#getByItemId(java.lang.Long)
	 * 根据itemId查询SKU价格
	 */
	@Override
	public List<SkuPrice> getByItemId(Long itemId) {
		Example example = new Example(Sku.class);
		example.createCriteria().andEqualTo("itemId", itemId).andEqualTo("deleted", DeletedType.NO);
		return skuPriceMapper.selectByExample(example);
	}

	/**
	 * @see com.ecp.service.back.ISkuPriceService#deleteByItemId(java.lang.Long)
	 * 根据itemId删除SKU价格
	 */
	@Override
	public int deleteByItemId(Long itemId) {
		Example example = new Example(Sku.class);
		example.createCriteria().andEqualTo("itemId", itemId);
		return skuPriceMapper.deleteByExample(example);
	}

}
