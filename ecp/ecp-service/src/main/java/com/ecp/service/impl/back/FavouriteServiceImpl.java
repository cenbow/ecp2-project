package com.ecp.service.impl.back;

import org.springframework.stereotype.Service;

import com.ecp.bean.DeletedType;
import com.ecp.dao.FavouriteMapper;
import com.ecp.entity.Favourite;
import com.ecp.service.back.IFavouriteService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("favouriteServiceBean")
public class FavouriteServiceImpl extends AbstractBaseService<Favourite, Long> implements IFavouriteService {
	
	FavouriteMapper favouriteMapper;
	
	/**
	 * @param mapper
	 * the mapper to set set方式注入
	 */	
	public void setFavouriteMapper(FavouriteMapper favouriteMapper) { 
		this.favouriteMapper=favouriteMapper;
		this.setMapper(favouriteMapper);
	}
	
	/**
	 * @see com.ecp.service.back.IFavouriteService#deleteByItemId(long)
	 * 删除购物车中的商品（逻辑删除）
	 */
	@Override
	public int deleteByItemId(long itemId) {
		Example example = new Example(Favourite.class);
		example.createCriteria().andEqualTo("itemId", itemId);
		Favourite favourite = new Favourite();
		favourite.setDeleted(DeletedType.YES);
		return favouriteMapper.updateByExampleSelective(favourite, example);
	}

}
