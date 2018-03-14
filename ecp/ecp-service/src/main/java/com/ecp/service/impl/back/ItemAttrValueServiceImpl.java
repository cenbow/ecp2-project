package com.ecp.service.impl.back;

import org.springframework.stereotype.Service;

import com.ecp.bean.DeletedType;
import com.ecp.dao.ItemAttrValueMapper;
import com.ecp.entity.ItemAttrValue;
import com.ecp.service.back.IItemAttrValueService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("itemAttrValueServiceBean")
public class ItemAttrValueServiceImpl extends AbstractBaseService<ItemAttrValue, Long> implements IItemAttrValueService {

	private ItemAttrValueMapper itemAttrValueMapper;

	/**
	 * @param itemAttrValueMapper the itemAttrValueMapper to set
	 * set方式注入
	 */
	public void setItemAttrValueMapper(ItemAttrValueMapper itemAttrValueMapper) {
		this.itemAttrValueMapper = itemAttrValueMapper;
		this.setMapper(itemAttrValueMapper);
	}

	/**
	 * @see com.ecp.service.back.IItemAttrValueService#deleteByItemId(Long)
	 * 根据商品ID删除（物理删除）
	 */
	@Override
	public int deleteByItemId(Long itemId) {
		Example example = new Example(ItemAttrValue.class);
		example.createCriteria().andEqualTo("itemId", itemId);
		ItemAttrValue attrVal = new ItemAttrValue();
		attrVal.setDeleted(DeletedType.YES);
		return itemAttrValueMapper.updateByExampleSelective(attrVal, example);
	}

}
