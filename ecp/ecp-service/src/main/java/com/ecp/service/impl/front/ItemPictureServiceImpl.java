package com.ecp.service.impl.front;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecp.bean.DeletedType;
import com.ecp.dao.ItemPictureMapper;
import com.ecp.entity.ItemPicture;
import com.ecp.service.front.IItemPictureService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class ItemPictureServiceImpl extends AbstractBaseService<ItemPicture, Long> implements IItemPictureService {
	
	ItemPictureMapper itemPictureMapper;
	/**
	 * @param mapper
	 * the mapper to set set方式注入
	 */	
	public void setItemPictureMapper(ItemPictureMapper itemPictureMapper) {
		this.itemPictureMapper=itemPictureMapper;
		this.setMapper(itemPictureMapper);
	}
	@Override
	public List<ItemPicture> getItemPictureByItemId(Long itemId){
		//通过一个对象查询
		ItemPicture itemPict=new ItemPicture();
		itemPict.setItemId(itemId);
		itemPict.setDeleted(DeletedType.NO);  //未删除标记
		return itemPictureMapper.select(itemPict);			
	}		
	

}
