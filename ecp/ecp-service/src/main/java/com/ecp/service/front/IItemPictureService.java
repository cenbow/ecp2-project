package com.ecp.service.front;

import java.util.List;

import com.ecp.entity.ItemPicture;
import com.ecp.service.IBaseService;

public interface IItemPictureService extends IBaseService<ItemPicture, Long> {	
	
	/**
	 * @Description 通过SPU  id  获取SPU图片列表
	 * @param itemId
	 * @return
	 */
	public List<ItemPicture> getItemPictureByItemId(Long itemId);
	
}
