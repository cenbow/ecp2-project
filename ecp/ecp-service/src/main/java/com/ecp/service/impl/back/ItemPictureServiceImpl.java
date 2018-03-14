package com.ecp.service.impl.back;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecp.bean.DeletedType;
import com.ecp.dao.ItemPictureMapper;
import com.ecp.entity.ItemPicture;
import com.ecp.service.back.IItemPictureService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("itemPictureServiceBean")
public class ItemPictureServiceImpl extends AbstractBaseService<ItemPicture, Long> implements IItemPictureService {

	private ItemPictureMapper itemPictureMapper;

	/**
	 * @param itemPictureMapper the itemPictureMapper to set
	 * set方式注入
	 */
	public void setItemPictureMapper(ItemPictureMapper itemPictureMapper) {
		this.itemPictureMapper = itemPictureMapper;
		this.setMapper(itemPictureMapper);
	}

	/**
	 * @see com.ecp.service.back.IItemPictureService#getByItemId(java.lang.Long)
	 * 根据商品ID查询商品图片
	 */
	@Override
	public List<ItemPicture> getByItemId(Long itemId) {
		Example example = new Example(ItemPicture.class);
		example.createCriteria().andEqualTo("itemId", itemId).andEqualTo("deleted", DeletedType.NO);
		return itemPictureMapper.selectByExample(example);
	}

	/**
	 * @see com.ecp.service.back.IItemPictureService#deleteByItemId(java.lang.Long)
	 * 根据商品ID删除商品图片
	 */
	@Override
	public int deleteByItemId(Long itemId) {
		Example example = new Example(ItemPicture.class);
		example.createCriteria().andEqualTo("itemId", itemId);
		return itemPictureMapper.deleteByExample(example);
	}

}
