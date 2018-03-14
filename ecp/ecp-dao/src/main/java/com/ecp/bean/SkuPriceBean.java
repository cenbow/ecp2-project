package com.ecp.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class SkuPriceBean implements Serializable {
	/**
	 * @Field @serialVersionUID : TODO(这里用一句话描述这个类的作用)
	 */
	private static final long serialVersionUID = 1L;

	private String attributes;
	private long item_id;
	private long sku_id;
	private BigDecimal volume;
	private BigDecimal weight;
	private BigDecimal sell_price;
	private BigDecimal market_price;  //市场价
	
	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public long getItem_id() {
		return item_id;
	}

	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}

	public long getSku_id() {
		return sku_id;
	}

	public void setSku_id(long sku_id) {
		this.sku_id = sku_id;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getSell_price() {
		return sell_price;
	}

	public void setSell_price(BigDecimal sell_price) {
		this.sell_price = sell_price;
	}

	public BigDecimal getMarket_price() {
		return market_price;
	}

	public void setMarket_price(BigDecimal market_price) {
		this.market_price = market_price;
	}

}
