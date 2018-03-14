package com.ecp.bean;

import java.math.BigDecimal;

/**
 * @ClassName FavouriteBean
 * @Description 用于显示用户收藏
 * @author Administrator
 * @Date 2017年5月27日 下午8:15:32
 * @version 1.0.0
 */
public class FavouriteBean {
	private long id;
	private long itemId;
	private String itemName;
	private BigDecimal sellPrice;
	private String pictureUrl;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public BigDecimal getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	

}
