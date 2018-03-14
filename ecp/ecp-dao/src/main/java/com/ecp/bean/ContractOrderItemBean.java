package com.ecp.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName ContractOrderItemBean
 * @Description 用于生成合同（合同中商品列表）
 * @author Administrator
 * @Date 2017年6月29日 下午3:53:19
 * @version 1.0.0
 */
public class ContractOrderItemBean implements Serializable  {

	/**
	 * @Field @serialVersionUID : TODO(这里用一句话描述这个类的作用)
	 */
	private static final long serialVersionUID = -2000803613749093426L;
	/*
	 "create_time":"2017-06-27T17:45:12.000+08:00",
					"picture_url":"/static/images/iphone_index.png",
					"item_id":1,
					"num":2,
					"sku_id":2,
					"picture_id":2,
					"picture_status":1,
					"primitive_price":222,
					"sort_number":1,
					"sku_name":"Apple8新品颜色:RED版本:V2","id":59,
					"order_id":"1000000937189443",
					"cid":13,
					"discount_price":0,
					"pay_price":222,
					"pay_price_total":444}, 
	 */
	private Long item_id;
	private Integer num;
	private Long sku_id;
	private BigDecimal primitive_price;
	private String sku_name;
	private String order_id;
	private Long cid;
	private BigDecimal discount_price;
	private BigDecimal pay_price;
	private BigDecimal pay_price_total;
	
	private Date create_time;
	private String picture_url;
	private Long picture_id;
	private Integer picture_status;
	
	public Long getItemId() {
		return item_id;
	}
	public void setItemId(Long item_id) {
		this.item_id = item_id;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Long getSkuId() {
		return sku_id;
	}
	public void setSkuId(Long sku_id) {
		this.sku_id = sku_id;
	}
	public BigDecimal getPrimitivePrice() {
		return primitive_price;
	}
	public void setPrimitivePrice(BigDecimal primitive_price) {
		this.primitive_price = primitive_price;
	}
	public String getSkuName() {
		return sku_name;
	}
	public void setSkuName(String sku_name) {
		this.sku_name = sku_name;
	}
	public String getOrderId() {
		return order_id;
	}
	public void setOrderId(String order_id) {
		this.order_id = order_id;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public BigDecimal getDiscountPrice() {
		return discount_price;
	}
	public void setDiscountPrice(BigDecimal discount_price) {
		this.discount_price = discount_price;
	}
	public BigDecimal getPayPrice() {
		return pay_price;
	}
	public void setPayPrice(BigDecimal pay_price) {
		this.pay_price = pay_price;
	}
	public BigDecimal getPayPriceTotal() {
		return pay_price_total;
	}
	public void setPayPriceTotal(BigDecimal pay_price_total) {
		this.pay_price_total = pay_price_total;
	}
	
	public String getPictureUrl() {
		return picture_url;
	}
	public void setPictureUrl(String picture_url) {
		this.picture_url = picture_url;
	}
	public Long getPictureId() {
		return picture_id;
	}
	public void setPictureId(Long picture_id) {
		this.picture_id = picture_id;
	}
	public Integer getPictureStatus() {
		return picture_status;
	}
	public void setPictureStatus(Integer picture_status) {
		this.picture_status = picture_status;
	}
	public Date getCreateTime() {
		return create_time;
	}
	public void setCreateTime(Date create_time) {
		this.create_time = create_time;
	}
	
	@Override
	public String toString() {
		return "ContractOrderItemBean [item_id=" + item_id + ", num=" + num + ", sku_id=" + sku_id
				+ ", primitive_price=" + primitive_price + ", sku_name=" + sku_name + ", order_id=" + order_id
				+ ", cid=" + cid + ", discount_price=" + discount_price + ", pay_price=" + pay_price
				+ ", pay_price_total=" + pay_price_total + ", create_time=" + create_time + ", picture_url="
				+ picture_url + ", picture_id=" + picture_id + ", picture_status=" + picture_status + "]";
	}
	
}
