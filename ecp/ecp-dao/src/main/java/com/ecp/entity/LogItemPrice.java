package com.ecp.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "log_item_price")
public class LogItemPrice {
    /**
     * 日志表自增ID
     */
    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

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
     * 时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改前价格
     */
    @Column(name = "before_price")
    private BigDecimal beforePrice;

    /**
     * 修改后价格
     */
    @Column(name = "after_price")
    private BigDecimal afterPrice;

    /**
     * 修改人
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 修改人姓名
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
     * 获取修改后价格
     *
     * @return after_price - 修改后价格
     */
    public BigDecimal getAfterPrice() {
        return afterPrice;
    }

    /**
     * 设置修改后价格
     *
     * @param afterPrice 修改后价格
     */
    public void setAfterPrice(BigDecimal afterPrice) {
        this.afterPrice = afterPrice;
    }

    /**
     * 获取修改人
     *
     * @return user_id - 修改人
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置修改人
     *
     * @param userId 修改人
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取修改人姓名
     *
     * @return user_name - 修改人姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置修改人姓名
     *
     * @param userName 修改人姓名
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
        sb.append(", itemId=").append(itemId);
        sb.append(", itemName=").append(itemName);
        sb.append(", createTime=").append(createTime);
        sb.append(", beforePrice=").append(beforePrice);
        sb.append(", afterPrice=").append(afterPrice);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append("]");
        return sb.toString();
    }
}