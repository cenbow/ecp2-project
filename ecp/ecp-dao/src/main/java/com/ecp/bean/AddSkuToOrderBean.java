package com.ecp.bean;

import java.math.BigDecimal;

public class AddSkuToOrderBean {
	private long id;  //购物车商品条目id
	private long cid;
	private long itemId;
	private long skuId;
	private String skuPicture;  //sku图片地址
	private String skuName;  //sku名称
	private BigDecimal skuPrice;  //sku单价
	private int skuNum;  //数量
	
	public long getCid() {
		return cid;
	}
	public void setCid(long cid) {
		this.cid = cid;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public long getSkuId() {
		return skuId;
	}
	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public BigDecimal getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(BigDecimal skuPrice) {
		this.skuPrice = skuPrice;
	}
	public int getSkuNum() {
		return skuNum;
	}
	public void setSkuNum(int skuNum) {
		this.skuNum = skuNum;
	}
	public String getSkuPicture() {
		return skuPicture;
	}
	public void setSkuPicture(String skuPicture) {
		this.skuPicture = skuPicture;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	
}
