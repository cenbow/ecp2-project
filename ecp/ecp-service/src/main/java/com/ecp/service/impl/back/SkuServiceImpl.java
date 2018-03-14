package com.ecp.service.impl.back;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecp.bean.DeletedType;
import com.ecp.dao.SkuMapper;
import com.ecp.entity.Sku;
import com.ecp.service.back.ISkuService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("skuServiceBean")
public class SkuServiceImpl extends AbstractBaseService<Sku, Long> implements ISkuService {

	private SkuMapper skuMapper;

	/**
	 * @param skuMapper the skuMapper to set
	 * set方式注入
	 */
	public void setSkuMapper(SkuMapper skuMapper) {
		this.skuMapper = skuMapper;
		this.setMapper(skuMapper);
	}

	/**
	 * @see com.ecp.service.back.ISkuService#getByItemId(java.lang.Long)
	 * 根据商品ID查询商品SKU
	 */
	@Override
	public List<Sku> getByItemId(Long itemId) {
		Example example = new Example(Sku.class);
		example.createCriteria().andEqualTo("itemId", itemId).andEqualTo("deleted", DeletedType.NO);
		return skuMapper.selectByExample(example);
	}

	/**
	 * @see com.ecp.service.back.ISkuService#deleteByItemId(java.lang.Long)
	 * 根据商品ID删除商品SKU
	 */
	@Override
	public int deleteByItemId(Long itemId) {
		Example example = new Example(Sku.class);
		example.createCriteria().andEqualTo("itemId", itemId);
		return skuMapper.deleteByExample(example);
	}

}
