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
     * 是否删除（1-未删除，2-删除，默认1）
     */
    private Integer deleted;

    /**
     * 最高限价
     */
    @Column(name = "highest_price")
    private BigDecimal highestPrice;

    /**
     * 最低限价
     */
    @Column(name = "lowest_price")
    private BigDecimal lowestPrice;

    /**
     * 硬成本价格
     */
    @Column(name = "hard_cost_price")
    private BigDecimal hardCostPrice;

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

    /**
     * 获取是否删除（1-未删除，2-删除，默认1）
     *
     * @return deleted - 是否删除（1-未删除，2-删除，默认1）
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置是否删除（1-未删除，2-删除，默认1）
     *
     * @param deleted 是否删除（1-未删除，2-删除，默认1）
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取最高限价
     *
     * @return highest_price - 最高限价
     */
    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    /**
     * 设置最高限价
     *
     * @param highestPrice 最高限价
     */
    public void setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
    }

    /**
     * 获取最低限价
     *
     * @return lowest_price - 最低限价
     */
    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    /**
     * 设置最低限价
     *
     * @param lowestPrice 最低限价
     */
    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    /**
     * 获取硬成本价格
     *
     * @return hard_cost_price - 硬成本价格
     */
    public BigDecimal getHardCostPrice() {
        return hardCostPrice;
    }

    /**
     * 设置硬成本价格
     *
     * @param hardCostPrice 硬成本价格
     */
    public void setHardCostPrice(BigDecimal hardCostPrice) {
        this.hardCostPrice = hardCostPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", areaId=").append(areaId);
        sb.append(", areaName=").append(areaName);
        sb.append(", areaNumber=").append(areaNumber);
        sb.append(", costPrice=").append(costPrice);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", itemId=").append(itemId);
        sb.append(", marketPrice=").append(marketPrice);
        sb.append(", maxNum=").append(maxNum);
        sb.append(", minNum=").append(minNum);
        sb.append(", sellPrice=").append(sellPrice);
        sb.append(", skuId=").append(skuId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", deleted=").append(deleted);
        sb.append(", highestPrice=").append(highestPrice);
        sb.append(", lowestPrice=").append(lowestPrice);
        sb.append(", hardCostPrice=").append(hardCostPrice);
        sb.append("]");
        return sb.toString();
    }
}