package com.ecp.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartItemBean {
	private long id;      //购物车中项目id
	private long itemId;  //spu id
	private long skuId;   //sku id
	private long userId;  //用户id
	private long cid;	  //类目id
	
	private String itemName;  //item name 与 sku attribute值 合成的名字
	private List<String> skuAttrValueNames;  //sku values'name
	private String skuName;  //sku'name(spu'name + sku'attr-value name)
	private String skuPicture;  //sku图片
	private BigDecimal skuPrice; //sku单价
	private BigDecimal skuWeight;  //sku单重
	private String weightUnit;  //重量单位
	private int quantity;  //sku数量
	
	
	public  CartItemBean(){
		skuAttrValueNames=new ArrayList<String>();
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
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getSkuPicture() {
		return skuPicture;
	}
	public void setSkuPicture(String skuPicture) {
		this.skuPicture = skuPicture;
	}
	public BigDecimal getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(BigDecimal skuPrice) {
		this.skuPrice = skuPrice;
	}
	public BigDecimal getSkuWeight() {
		return skuWeight;
	}
	public void setSkuWeight(BigDecimal skuWeight) {
		this.skuWeight = skuWeight;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<String> getSkuAttrValueNames() {
		return skuAttrValueNames;
	}

	public void setSkuAttrValueNames(List<String> skuAttrValueNames) {
		this.skuAttrValueNames = skuAttrValueNames;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
