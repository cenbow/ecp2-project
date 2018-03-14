package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

public class Performance {
    /**
     * 业绩表自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户ID
     */
    @Column(name = "cust_id")
    private Long custId;

    /**
     * 订单ID
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 商品ID
     */
    @Column(name = "item_id")
    private Long itemId;

    /**
     * SKU_ID
     */
    @Column(name = "sku_id")
    private Long skuId;

    /**
     * 跟单人ID
     */
    @Column(name = "bind_user_id")
    private Long bindUserId;

    /**
     * 金额
     */
    private Long money;

    /**
     * 类型（1、业绩；2、市场费；3、四项费用；4、销售滞纳金；5、价差；）
     */
    private Byte type;

    /**
     * 时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作员ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 操作员姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 获取业绩表自增ID
     *
     * @return id - 业绩表自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置业绩表自增ID
     *
     * @param id 业绩表自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取客户ID
     *
     * @return cust_id - 客户ID
     */
    public Long getCustId() {
        return custId;
    }

    /**
     * 设置客户ID
     *
     * @param custId 客户ID
     */
    public void setCustId(Long custId) {
        this.custId = custId;
    }

    /**
     * 获取订单ID
     *
     * @return order_id - 订单ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     *
     * @param orderId 订单ID
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取订单号
     *
     * @return order_no - 订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单号
     *
     * @param orderNo 订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * 获取商品ID
     *
     * @return item_id - 商品ID
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * 设置商品ID
     *
     * @param itemId 商品ID
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
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
     * 获取跟单人ID
     *
     * @return bind_user_id - 跟单人ID
     */
    public Long getBindUserId() {
        return bindUserId;
    }

    /**
     * 设置跟单人ID
     *
     * @param bindUserId 跟单人ID
     */
    public void setBindUserId(Long bindUserId) {
        this.bindUserId = bindUserId;
    }

    /**
     * 获取金额
     *
     * @return money - 金额
     */
    public Long getMoney() {
        return money;
    }

    /**
     * 设置金额
     *
     * @param money 金额
     */
    public void setMoney(Long money) {
        this.money = money;
    }

    /**
     * 获取类型（1、业绩；2、市场费；3、四项费用；4、销售滞纳金；5、价差；）
     *
     * @return type - 类型（1、业绩；2、市场费；3、四项费用；4、销售滞纳金；5、价差；）
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置类型（1、业绩；2、市场费；3、四项费用；4、销售滞纳金；5、价差；）
     *
     * @param type 类型（1、业绩；2、市场费；3、四项费用；4、销售滞纳金；5、价差；）
     */
    public void setType(Byte type) {
        this.type = type;
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
     * 获取操作员ID
     *
     * @return user_id - 操作员ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置操作员ID
     *
     * @param userId 操作员ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取操作员姓名
     *
     * @return user_name - 操作员姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置操作员姓名
     *
     * @param userName 操作员姓名
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
        sb.append(", id=").append(id);
        sb.append(", custId=").append(custId);
        sb.append(", orderId=").append(orderId);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", itemId=").append(itemId);
        sb.append(", skuId=").append(skuId);
        sb.append(", bindUserId=").append(bindUserId);
        sb.append(", money=").append(money);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append("]");
        return sb.toString();
    }
}