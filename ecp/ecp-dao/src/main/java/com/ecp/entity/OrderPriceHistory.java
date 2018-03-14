package com.ecp.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "trade_order_price_history")
public class OrderPriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "after_freight")
    private BigDecimal afterFreight;

    @Column(name = "after_payment_price")
    private BigDecimal afterPaymentPrice;

    @Column(name = "after_total_discount")
    private BigDecimal afterTotalDiscount;

    @Column(name = "after_total_price")
    private BigDecimal afterTotalPrice;

    @Column(name = "before_freight")
    private BigDecimal beforeFreight;

    @Column(name = "before_payment_price")
    private BigDecimal beforePaymentPrice;

    @Column(name = "before_total_discount")
    private BigDecimal beforeTotalDiscount;

    @Column(name = "before_total_price")
    private BigDecimal beforeTotalPrice;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "operation_user")
    private String operationUser;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "seller_id")
    private Long sellerId;

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
     * @return after_freight
     */
    public BigDecimal getAfterFreight() {
        return afterFreight;
    }

    /**
     * @param afterFreight
     */
    public void setAfterFreight(BigDecimal afterFreight) {
        this.afterFreight = afterFreight;
    }

    /**
     * @return after_payment_price
     */
    public BigDecimal getAfterPaymentPrice() {
        return afterPaymentPrice;
    }

    /**
     * @param afterPaymentPrice
     */
    public void setAfterPaymentPrice(BigDecimal afterPaymentPrice) {
        this.afterPaymentPrice = afterPaymentPrice;
    }

    /**
     * @return after_total_discount
     */
    public BigDecimal getAfterTotalDiscount() {
        return afterTotalDiscount;
    }

    /**
     * @param afterTotalDiscount
     */
    public void setAfterTotalDiscount(BigDecimal afterTotalDiscount) {
        this.afterTotalDiscount = afterTotalDiscount;
    }

    /**
     * @return after_total_price
     */
    public BigDecimal getAfterTotalPrice() {
        return afterTotalPrice;
    }

    /**
     * @param afterTotalPrice
     */
    public void setAfterTotalPrice(BigDecimal afterTotalPrice) {
        this.afterTotalPrice = afterTotalPrice;
    }

    /**
     * @return before_freight
     */
    public BigDecimal getBeforeFreight() {
        return beforeFreight;
    }

    /**
     * @param beforeFreight
     */
    public void setBeforeFreight(BigDecimal beforeFreight) {
        this.beforeFreight = beforeFreight;
    }

    /**
     * @return before_payment_price
     */
    public BigDecimal getBeforePaymentPrice() {
        return beforePaymentPrice;
    }

    /**
     * @param beforePaymentPrice
     */
    public void setBeforePaymentPrice(BigDecimal beforePaymentPrice) {
        this.beforePaymentPrice = beforePaymentPrice;
    }

    /**
     * @return before_total_discount
     */
    public BigDecimal getBeforeTotalDiscount() {
        return beforeTotalDiscount;
    }

    /**
     * @param beforeTotalDiscount
     */
    public void setBeforeTotalDiscount(BigDecimal beforeTotalDiscount) {
        this.beforeTotalDiscount = beforeTotalDiscount;
    }

    /**
     * @return before_total_price
     */
    public BigDecimal getBeforeTotalPrice() {
        return beforeTotalPrice;
    }

    /**
     * @param beforeTotalPrice
     */
    public void setBeforeTotalPrice(BigDecimal beforeTotalPrice) {
        this.beforeTotalPrice = beforeTotalPrice;
    }

    /**
     * @return buyer_id
     */
    public Long getBuyerId() {
        return buyerId;
    }

    /**
     * @param buyerId
     */
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
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
     * @return operation_user
     */
    public String getOperationUser() {
        return operationUser;
    }

    /**
     * @param operationUser
     */
    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser == null ? null : operationUser.trim();
    }

    /**
     * @return order_id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return seller_id
     */
    public Long getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId
     */
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
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
		return "OrderPriceHistory [id=" + id + ", afterFreight=" + afterFreight + ", afterPaymentPrice="
				+ afterPaymentPrice + ", afterTotalDiscount=" + afterTotalDiscount + ", afterTotalPrice="
				+ afterTotalPrice + ", beforeFreight=" + beforeFreight + ", beforePaymentPrice=" + beforePaymentPrice
				+ ", beforeTotalDiscount=" + beforeTotalDiscount + ", beforeTotalPrice=" + beforeTotalPrice
				+ ", buyerId=" + buyerId + ", createTime=" + createTime + ", operationUser=" + operationUser
				+ ", orderId=" + orderId + ", sellerId=" + sellerId + ", deleted=" + deleted + "]";
	}
}