package com.ecp.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "trade_sku_price")
public class SkuPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "area_id")
    private String areaId;

    @Column(name = "area_name")
    private String areaName;

    @Column(name = "area_number")
    private Integer areaNumber;

    @Column(name = "cost_price")
    private BigDecimal costPrice;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "market_price")
    private BigDecimal marketPrice;

    @Column(name = "max_num")
    private Long maxNum;

    @Column(name = "min_num")
    private Long minNum;

    @Column(name = "sell_price")
    private BigDecimal sellPrice;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "update_user")
    private String updateUser;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return area_id
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * @param areaId
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    /**
     * @return area_name
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    /**
     * @return area_number
     */
    public Integer getAreaNumber() {
        return areaNumber;
    }

    /**
     * @param areaNumber
     */
    public void setAreaNumber(Integer areaNumber) {
        this.areaNumber = areaNumber;
    }

    /**
     * @return cost_price
     */
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**
     * @param costPrice
     */
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * @return item_id
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * @param itemId
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * @return market_price
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * @param marketPrice
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * @return max_num
     */
    public Long getMaxNum() {
        return maxNum;
    }

    /**
     * @param maxNum
     */
    public void setMaxNum(Long maxNum) {
        this.maxNum = maxNum;
    }

    /**
     * @return min_num
     */
    public Long getMinNum() {
        return minNum;
    }

    /**
     * @param minNum
     */
    public void setMinNum(Long minNum) {
        this.minNum = minNum;
    }

    /**
     * @return sell_price
     */
    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    /**
     * @param sellPrice
     */
    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * @return sku_id
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * @param skuId
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    @Column(name = "deleted")
    private Integer deleted;//是否删除（1-未删除，2-删除，默认1）

    /**
     * @return	是否删除（1-未删除，2-删除，默认1）
     */
    public Integer getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted 是否删除（1-未删除，2-删除，默认1）
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "SkuPrice [id=" + id + ", areaId=" + areaId + ", areaName=" + areaName + ", areaNumber=" + areaNumber
				+ ", costPrice=" + costPrice + ", createTime=" + createTime + ", createUser=" + createUser + ", itemId="
				+ itemId + ", marketPrice=" + marketPrice + ", maxNum=" + maxNum + ", minNum=" + minNum + ", sellPrice="
				+ sellPrice + ", skuId=" + skuId + ", updateTime=" + updateTime + ", updateUser=" + updateUser
				+ ", deleted=" + deleted + "]";
	}
}