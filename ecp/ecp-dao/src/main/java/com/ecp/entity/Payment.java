package com.ecp.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Payment {
    /**
     * 回款记录表自增ID
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
     * 合同ID
     */
    @Column(name = "contract_id")
    private Long contractId;

    /**
     * 合同号
     */
    @Column(name = "contract_no")
    private String contractNo;

    /**
     * 金额
     */
    @Column(name = "payment_money")
    private BigDecimal paymentMoney;

    /**
     * 回款时间
     */
    @Column(name = "payment_time")
    private Date paymentTime;

    /**
     * 备注
     */
    private String comment;

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
     * 获取回款记录表自增ID
     *
     * @return id - 回款记录表自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置回款记录表自增ID
     *
     * @param id 回款记录表自增ID
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
     * 获取合同号
     *
     * @return contract_no - 合同号
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * 设置合同号
     *
     * @param contractNo 合同号
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    /**
     * 获取金额
     *
     * @return payment_money - 金额
     */
    public BigDecimal getPaymentMoney() {
        return paymentMoney;
    }

    /**
     * 设置金额
     *
     * @param paymentMoney 金额
     */
    public void setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    /**
     * 获取回款时间
     *
     * @return payment_time - 回款时间
     */
    public Date getPaymentTime() {
        return paymentTime;
    }

    /**
     * 设置回款时间
     *
     * @param paymentTime 回款时间
     */
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * 获取备注
     *
     * @return comment - 备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置备注
     *
     * @param comment 备注
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
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
        sb.append(", contractId=").append(contractId);
        sb.append(", contractNo=").append(contractNo);
        sb.append(", paymentMoney=").append(paymentMoney);
        sb.append(", paymentTime=").append(paymentTime);
        sb.append(", comment=").append(comment);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append("]");
        return sb.toString();
    }
}