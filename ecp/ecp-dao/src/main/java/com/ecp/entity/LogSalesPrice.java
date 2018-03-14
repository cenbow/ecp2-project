package com.ecp.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "log_sales_price")
public class LogSalesPrice {
    /**
     * 日志表自增ID
     */
    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    /**
     * 时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 合同ID
     */
    @Column(name = "contract_id")
    private Long contractId;

    /**
     * 产品ID
     */
    @Column(name = "item_id")
    private Long itemId;

    /**
     * 产品名称
     */
    @Column(name = "item_name")
    private String itemName;

    /**
     * SKU_ID
     */
    @Column(name = "sku_id")
    private Long skuId;

    /**
     * SKU名称
     */
    @Column(name = "sku_name")
    private String skuName;

    /**
     * 销售出价
     */
    @Column(name = "sale_price")
    private Long salePrice;

    /**
     * 修改前价格
     */
    @Column(name = "before_price")
    private BigDecimal beforePrice;

    /**
     * 最低限价
     */
    @Column(name = "lowcest_price")
    private BigDecimal lowcestPrice;

    /**
     * 最高限价
     */
    @Column(name = "highest_price")
    private BigDecimal highestPrice;

    /**
     * 操作人
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 操作人姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 获取日志表自增ID
     *
     * @return log_id - 日志表自增ID
     */
    public Long getLogId() {
        return logId;
    }

    /**
     * 设置日志表自增ID
     *
     * @param logId 日志表自增ID
     */
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    /**
     * 获取时间
     *
     * @return create_time - 时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置时间
     *
     * @param createTime 时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取合同ID
     *
     * @return contract_id - 合同ID
     */
    public Long getContractId() {
        return contractId;
    }

    /**
     * 设置合同ID
     *
     * @param contractId 合同ID
     */
    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    /**
     * 获取产品ID
     *
     * @return item_id - 产品ID
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * 设置产品ID
     *
     * @param itemId 产品ID
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取产品名称
     *
     * @return item_name - 产品名称
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 设置产品名称
     *
     * @param itemName 产品名称
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    /**
     * 获取SKU_ID
     *
     * @return sku_id - SKU_ID
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * 设置SKU_ID
     *
     * @param skuId SKU_ID
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * 获取SKU名称
     *
     * @return sku_name - SKU名称
     */
    public String getSkuName() {
        return skuName;
    }

    /**
     * 设置SKU名称
     *
     * @param skuName SKU名称
     */
    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    /**
     * 获取销售出价
     *
     * @return sale_price - 销售出价
     */
    public Long getSalePrice() {
        return salePrice;
    }

    /**
     * 设置销售出价
     *
     * @param salePrice 销售出价
     */
    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * 获取修改前价格
     *
     * @return before_price - 修改前价格
     */
    public BigDecimal getBeforePrice() {
        return beforePrice;
    }

    /**
     * 设置修改前价格
     *
     * @param beforePrice 修改前价格
     */
    public void setBeforePrice(BigDecimal beforePrice) {
        this.beforePrice = beforePrice;
    }

    /**
     * 获取最低限价
     *
     * @return lowcest_price - 最低限价
     */
    public BigDecimal getLowcestPrice() {
        return lowcestPrice;
    }

    /**
     * 设置最低限价
     *
     * @param lowcestPrice 最低限价
     */
    public void setLowcestPrice(BigDecimal lowcestPrice) {
        this.lowcestPrice = lowcestPrice;
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
     * 获取操作人
     *
     * @return user_id - 操作人
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置操作人
     *
     * @param userId 操作人
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取操作人姓名
     *
     * @return user_name - 操作人姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置操作人姓名
     *
     * @param userName 操作人姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", createTime=").append(createTime);
        sb.append(", contractId=").append(contractId);
        sb.append(", itemId=").append(itemId);
        sb.append(", itemName=").append(itemName);
        sb.append(", skuId=").append(skuId);
        sb.append(", skuName=").append(skuName);
        sb.append(", salePrice=").append(salePrice);
        sb.append(", beforePrice=").append(beforePrice);
        sb.append(", lowcestPrice=").append(lowcestPrice);
        sb.append(", highestPrice=").append(highestPrice);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append("]");
        return sb.toString();
    }
}