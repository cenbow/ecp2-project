package com.ecp.entity;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "trade_order_items_discount")
public class OrderItemsDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_discount")
    private BigDecimal couponDiscount;

    @Column(name = "coupon_id")
    private String couponId;

    @Column(name = "coupon_type")
    private Byte couponType;

    @Column(name = "full_reduction_discount")
    private BigDecimal fullReductionDiscount;

    @Column(name = "full_reduction_id")
    private Long fullReductionId;

    @Column(name = "full_reduction_type")
    private Byte fullReductionType;

    private Long integral;

    @Column(name = "integral_discount")
    private BigDecimal integralDiscount;

    @Column(name = "integral_type")
    private Byte integralType;

    @Column(name = "markdown_discount")
    private BigDecimal markdownDiscount;

    @Column(name = "markdown_id")
    private Long markdownId;

    @Column(name = "markdown_type")
    private Byte markdownType;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "sku_id")
    private Long skuId;

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
     * @return coupon_discount
     */
    public BigDecimal getCouponDiscount() {
        return couponDiscount;
    }

    /**
     * @param couponDiscount
     */
    public void setCouponDiscount(BigDecimal couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    /**
     * @return coupon_id
     */
    public String getCouponId() {
        return couponId;
    }

    /**
     * @param couponId
     */
    public void setCouponId(String couponId) {
        this.couponId = couponId == null ? null : couponId.trim();
    }

    /**
     * @return coupon_type
     */
    public Byte getCouponType() {
        return couponType;
    }

    /**
     * @param couponType
     */
    public void setCouponType(Byte couponType) {
        this.couponType = couponType;
    }

    /**
     * @return full_reduction_discount
     */
    public BigDecimal getFullReductionDiscount() {
        return fullReductionDiscount;
    }

    /**
     * @param fullReductionDiscount
     */
    public void setFullReductionDiscount(BigDecimal fullReductionDiscount) {
        this.fullReductionDiscount = fullReductionDiscount;
    }

    /**
     * @return full_reduction_id
     */
    public Long getFullReductionId() {
        return fullReductionId;
    }

    /**
     * @param fullReductionId
     */
    public void setFullReductionId(Long fullReductionId) {
        this.fullReductionId = fullReductionId;
    }

    /**
     * @return full_reduction_type
     */
    public Byte getFullReductionType() {
        return fullReductionType;
    }

    /**
     * @param fullReductionType
     */
    public void setFullReductionType(Byte fullReductionType) {
        this.fullReductionType = fullReductionType;
    }

    /**
     * @return integral
     */
    public Long getIntegral() {
        return integral;
    }

    /**
     * @param integral
     */
    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    /**
     * @return integral_discount
     */
    public BigDecimal getIntegralDiscount() {
        return integralDiscount;
    }

    /**
     * @param integralDiscount
     */
    public void setIntegralDiscount(BigDecimal integralDiscount) {
        this.integralDiscount = integralDiscount;
    }

    /**
     * @return integral_type
     */
    public Byte getIntegralType() {
        return integralType;
    }

    /**
     * @param integralType
     */
    public void setIntegralType(Byte integralType) {
        this.integralType = integralType;
    }

    /**
     * @return markdown_discount
     */
    public BigDecimal getMarkdownDiscount() {
        return markdownDiscount;
    }

    /**
     * @param markdownDiscount
     */
    public void setMarkdownDiscount(BigDecimal markdownDiscount) {
        this.markdownDiscount = markdownDiscount;
    }

    /**
     * @return markdown_id
     */
    public Long getMarkdownId() {
        return markdownId;
    }

    /**
     * @param markdownId
     */
    public void setMarkdownId(Long markdownId) {
        this.markdownId = markdownId;
    }

    /**
     * @return markdown_type
     */
    public Byte getMarkdownType() {
        return markdownType;
    }

    /**
     * @param markdownType
     */
    public void setMarkdownType(Byte markdownType) {
        this.markdownType = markdownType;
    }

    /**
     * @return order_id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * @return order_item_id
     */
    public Long getOrderItemId() {
        return orderItemId;
    }

    /**
     * @param orderItemId
     */
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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
		return "OrderItemsDiscount [id=" + id + ", couponDiscount=" + couponDiscount + ", couponId=" + couponId
				+ ", couponType=" + couponType + ", fullReductionDiscount=" + fullReductionDiscount
				+ ", fullReductionId=" + fullReductionId + ", fullReductionType=" + fullReductionType + ", integral="
				+ integral + ", integralDiscount=" + integralDiscount + ", integralType=" + integralType
				+ ", markdownDiscount=" + markdownDiscount + ", markdownId=" + markdownId + ", markdownType="
				+ markdownType + ", orderId=" + orderId + ", orderItemId=" + orderItemId + ", skuId=" + skuId
				+ ", deleted=" + deleted + "]";
	}
}