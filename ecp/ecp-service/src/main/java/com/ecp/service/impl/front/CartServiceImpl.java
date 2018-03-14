package com.ecp.service.impl.front;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecp.dao.FavouriteMapper;
import com.ecp.entity.Favourite;
import com.ecp.service.front.ICartService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class CartServiceImpl extends AbstractBaseService<Favourite, Integer> implements ICartService {
	
	FavouriteMapper favouriteMapper;
	
	/**
	 * @param mapper
	 * the mapper to set set方式注入
	 */	
	public void setFavouriteMapper(FavouriteMapper favouriteMapper) { 
		this.favouriteMapper=favouriteMapper;
		this.setMapper(favouriteMapper);
	}	

	@Override
	public int addToCart(int itemId, int skuId, int quantity, int userId) {
		//favouriteMapper.
		/**
		 (1)自购物车查询，如不存在，则直接插入
		 (2)如果已经存在，则进行更新。（status=1）
		 */
		Favourite record=new Favourite(); 
		record.setItemId(itemId);
		record.setSkuId(skuId);		
		record.setUserId(userId);
		record.setStatus((byte)1);
		Favourite favourite=favouriteMapper.selectOne(record);
		if(favourite==null){
			record.setQuantity(quantity);
			record.setCreatedDate(new Date());
			favouriteMapper.insert(record);
			return record.getId();
		}
		else{
			//record.setQuantity(record.getquantity);
			int id=favourite.getId();
			favouriteMapper.addQuqntityById(id, quantity);
			return id;
		}
		
		
		//favouriteMapper
		
	}

	@Override
	public int getItemNumByUserId(long userId) {
		int itemNum=favouriteMapper.getItemNumByUserId(userId);			
		return itemNum;
	}

	@Override
	public List<Favourite> getCartItemByUserId(long userId, byte status) {
		Favourite record=new Favourite();		
		record.setUserId(Integer.parseInt(Long.toString(userId)));
		record.setStatus(status);
		
		
		return favouriteMapper.select(record);
		
	}
	
	

}
