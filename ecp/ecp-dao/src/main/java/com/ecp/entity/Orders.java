package com.ecp.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "trade_orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "after_service")
    private Byte afterService;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "cancel_time")
    private Date cancelTime;

    @Column(name = "change_payment_price_time")
    private Date changePaymentPriceTime;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "confirm_receipt_time")
    private Date confirmReceiptTime;

    @Column(name = "county_id")
    private Long countyId;

    @Column(name = "create_time")
    private Date createTime;

    private Byte delay;

    @Column(name = "delay_over_time")
    private Date delayOverTime;

    @Column(name = "delete_time")
    private Date deleteTime;

    private Integer deleted;

    @Column(name = "deliver_time")
    private Date deliverTime;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    private String email;

    private Byte evaluate;

    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    private BigDecimal freight;

    @Column(name = "full_address")
    private String fullAddress;

    private Long integral;

    @Column(name = "integral_discount")
    private BigDecimal integralDiscount;

    private Byte invoice;

    @Column(name = "invoice_title")
    private String invoiceTitle;

    @Column(name = "lock_time")
    private Date lockTime;

    private Byte locked;

    @Column(name = "logistics_company")
    private String logisticsCompany;

    @Column(name = "logistics_no")
    private String logisticsNo;

    @Column(name = "logistics_remark")
    private String logisticsRemark;

    private String memo;

    private String mobile;

    private String name;

    @Column(name = "order_finish_time")
    private Date orderFinishTime;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_jfs_key")
    private String orderJfsKey;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "order_type")
    private Byte orderType;

    private Integer paid;

    @Column(name = "parent_order_id")
    private String parentOrderId;

    @Column(name = "pay_period")
    private Integer payPeriod;

    @Column(name = "payment_method")
    private Byte paymentMethod;

    @Column(name = "payment_price")
    private BigDecimal paymentPrice;

    @Column(name = "payment_time")
    private Date paymentTime;

    @Column(name = "payment_type")
    private Byte paymentType;

    private String phone;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "province_id")
    private Long provinceId;

    private Byte refund;

    @Column(name = "refund_time")
    private Date refundTime;

    @Column(name = "seller_evaluate")
    private Byte sellerEvaluate;

    @Column(name = "shipment_type")
    private Byte shipmentType;

    private Integer state;

    @Column(name = "total_discount")
    private BigDecimal totalDiscount;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "update_time")
    private Date updateTime;

    private Integer yn;

    @Column(name = "contract_no")
    private String contractNo;

    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "contract_state")
    private Byte contractState;

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
     * @return after_service
     */
    public Byte getAfterService() {
        return afterService;
    }

    /**
     * @param afterService
     */
    public void setAfterService(Byte afterService) {
        this.afterService = afterService;
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
     * @return cancel_time
     */
    public Date getCancelTime() {
        return cancelTime;
    }

    /**
     * @param cancelTime
     */
    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    /**
     * @return change_payment_price_time
     */
    public Date getChangePaymentPriceTime() {
        return changePaymentPriceTime;
    }

    /**
     * @param changePaymentPriceTime
     */
    public void setChangePaymentPriceTime(Date changePaymentPriceTime) {
        this.changePaymentPriceTime = changePaymentPriceTime;
    }

    /**
     * @return city_id
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * @param cityId
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * @return confirm_receipt_time
     */
    public Date getConfirmReceiptTime() {
        return confirmReceiptTime;
    }

    /**
     * @param confirmReceiptTime
     */
    public void setConfirmReceiptTime(Date confirmReceiptTime) {
        this.confirmReceiptTime = confirmReceiptTime;
    }

    /**
     * @return county_id
     */
    public Long getCountyId() {
        return countyId;
    }

    /**
     * @param countyId
     */
    public void setCountyId(Long countyId) {
        this.countyId = countyId;
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
     * @return delay
     */
    public Byte getDelay() {
        return delay;
    }

    /**
     * @param delay
     */
    public void setDelay(Byte delay) {
        this.delay = delay;
    }

    /**
     * @return delay_over_time
     */
    public Date getDelayOverTime() {
        return delayOverTime;
    }

    /**
     * @param delayOverTime
     */
    public void setDelayOverTime(Date delayOverTime) {
        this.delayOverTime = delayOverTime;
    }

    /**
     * @return delete_time
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * @param deleteTime
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

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

    /**
     * @return deliver_time
     */
    public Date getDeliverTime() {
        return deliverTime;
    }

    /**
     * @param deliverTime
     */
    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    /**
     * @return detail_address
     */
    public String getDetailAddress() {
        return detailAddress;
    }

    /**
     * @param detailAddress
     */
    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress == null ? null : detailAddress.trim();
    }

    /**
     * @return discount_amount
     */
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    /**
     * @param discountAmount
     */
    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * @return evaluate
     */
    public Byte getEvaluate() {
        return evaluate;
    }

    /**
     * @param evaluate
     */
    public void setEvaluate(Byte evaluate) {
        this.evaluate = evaluate;
    }

    /**
     * @return exchange_rate
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param exchangeRate
     */
    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * @return freight
     */
    public BigDecimal getFreight() {
        return freight;
    }

    /**
     * @param freight
     */
    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    /**
     * @return full_address
     */
    public String getFullAddress() {
        return fullAddress;
    }

    /**
     * @param fullAddress
     */
    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress == null ? null : fullAddress.trim();
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
     * @return invoice
     */
    public Byte getInvoice() {
        return invoice;
    }

    /**
     * @param invoice
     */
    public void setInvoice(Byte invoice) {
        this.invoice = invoice;
    }

    /**
     * @return invoice_title
     */
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    /**
     * @param invoiceTitle
     */
    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
    }

    /**
     * @return lock_time
     */
    public Date getLockTime() {
        return lockTime;
    }

    /**
     * @param lockTime
     */
    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    /**
     * @return locked
     */
    public Byte getLocked() {
        return locked;
    }

    /**
     * @param locked
     */
    public void setLocked(Byte locked) {
        this.locked = locked;
    }

    /**
     * @return logistics_company
     */
    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    /**
     * @param logisticsCompany
     */
    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany == null ? null : logisticsCompany.trim();
    }

    /**
     * @return logistics_no
     */
    public String getLogisticsNo() {
        return logisticsNo;
    }

    /**
     * @param logisticsNo
     */
    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
    }

    /**
     * @return logistics_remark
     */
    public String getLogisticsRemark() {
        return logisticsRemark;
    }

    /**
     * @param logisticsRemark
     */
    public void setLogisticsRemark(String logisticsRemark) {
        this.logisticsRemark = logisticsRemark == null ? null : logisticsRemark.trim();
    }

    /**
     * @return memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return order_finish_time
     */
    public Date getOrderFinishTime() {
        return orderFinishTime;
    }

    /**
     * @param orderFinishTime
     */
    public void setOrderFinishTime(Date orderFinishTime) {
        this.orderFinishTime = orderFinishTime;
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
     * @return order_jfs_key
     */
    public String getOrderJfsKey() {
        return orderJfsKey;
    }

    /**
     * @param orderJfsKey
     */
    public void setOrderJfsKey(String orderJfsKey) {
        this.orderJfsKey = orderJfsKey == null ? null : orderJfsKey.trim();
    }

    /**
     * @return order_time
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * @param orderTime
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * @return order_type
     */
    public Byte getOrderType() {
        return orderType;
    }

    /**
     * @param orderType
     */
    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    /**
     * @return paid
     */
    public Integer getPaid() {
        return paid;
    }

    /**
     * @param paid
     */
    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    /**
     * @return parent_order_id
     */
    public String getParentOrderId() {
        return parentOrderId;
    }

    /**
     * @param parentOrderId
     */
    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = parentOrderId == null ? null : parentOrderId.trim();
    }

    /**
     * @return pay_period
     */
    public Integer getPayPeriod() {
        return payPeriod;
    }

    /**
     * @param payPeriod
     */
    public void setPayPeriod(Integer payPeriod) {
        this.payPeriod = payPeriod;
    }

    /**
     * @return payment_method
     */
    public Byte getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod
     */
    public void setPaymentMethod(Byte paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * @return payment_price
     */
    public BigDecimal getPaymentPrice() {
        return paymentPrice;
    }

    /**
     * @param paymentPrice
     */
    public void setPaymentPrice(BigDecimal paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    /**
     * @return payment_time
     */
    public Date getPaymentTime() {
        return paymentTime;
    }

    /**
     * @param paymentTime
     */
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * @return payment_type
     */
    public Byte getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType
     */
    public void setPaymentType(Byte paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * @return promo_code
     */
    public String getPromoCode() {
        return promoCode;
    }

    /**
     * @param promoCode
     */
    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode == null ? null : promoCode.trim();
    }

    /**
     * @return province_id
     */
    public Long getProvinceId() {
        return provinceId;
    }

    /**
     * @param provinceId
     */
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * @return refund
     */
    public Byte getRefund() {
        return refund;
    }

    /**
     * @param refund
     */
    public void setRefund(Byte refund) {
        this.refund = refund;
    }

    /**
     * @return refund_time
     */
    public Date getRefundTime() {
        return refundTime;
    }

    /**
     * @param refundTime
     */
    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    /**
     * @return seller_evaluate
     */
    public Byte getSellerEvaluate() {
        return sellerEvaluate;
    }

    /**
     * @param sellerEvaluate
     */
    public void setSellerEvaluate(Byte sellerEvaluate) {
        this.sellerEvaluate = sellerEvaluate;
    }

    /**
     * @return shipment_type
     */
    public Byte getShipmentType() {
        return shipmentType;
    }

    /**
     * @param shipmentType
     */
    public void setShipmentType(Byte shipmentType) {
        this.shipmentType = shipmentType;
    }

    /**
     * @return state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @return total_discount
     */
    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    /**
     * @param totalDiscount
     */
    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    /**
     * @return total_price
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
     * @return yn
     */
    public Integer getYn() {
        return yn;
    }

    /**
     * @param yn
     */
    public void setYn(Integer yn) {
        this.yn = yn;
    }

    /**
     * @return contract_no
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * @param contractNo
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    /**
     * @return contract_id
     */
    public Long getContractId() {
        return contractId;
    }

    /**
     * @param contractId
     */
    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    /**
     * @return contract_state
     */
    public Byte getContractState() {
        return contractState;
    }

    /**
     * @param contractState
     */
    public void setContractState(Byte contractState) {
        this.contractState = contractState;
    }

	@Override
	public String toString() {
		return "Orders [id=" + id + ", afterService=" + afterService + ", buyerId=" + buyerId + ", cancelTime="
				+ cancelTime + ", changePaymentPriceTime=" + changePaymentPriceTime + ", cityId=" + cityId
				+ ", confirmReceiptTime=" + confirmReceiptTime + ", countyId=" + countyId + ", createTime=" + createTime
				+ ", delay=" + delay + ", delayOverTime=" + delayOverTime + ", deleteTime=" + deleteTime + ", deleted="
				+ deleted + ", deliverTime=" + deliverTime + ", detailAddress=" + detailAddress + ", discountAmount="
				+ discountAmount + ", email=" + email + ", evaluate=" + evaluate + ", exchangeRate=" + exchangeRate
				+ ", freight=" + freight + ", fullAddress=" + fullAddress + ", integral=" + integral
				+ ", integralDiscount=" + integralDiscount + ", invoice=" + invoice + ", invoiceTitle=" + invoiceTitle
				+ ", lockTime=" + lockTime + ", locked=" + locked + ", logisticsCompany=" + logisticsCompany
				+ ", logisticsNo=" + logisticsNo + ", logisticsRemark=" + logisticsRemark + ", memo=" + memo
				+ ", mobile=" + mobile + ", name=" + name + ", orderFinishTime=" + orderFinishTime + ", orderId="
				+ orderId + ", orderJfsKey=" + orderJfsKey + ", orderTime=" + orderTime + ", orderType=" + orderType
				+ ", paid=" + paid + ", parentOrderId=" + parentOrderId + ", payPeriod=" + payPeriod
				+ ", paymentMethod=" + paymentMethod + ", paymentPrice=" + paymentPrice + ", paymentTime=" + paymentTime
				+ ", paymentType=" + paymentType + ", phone=" + phone + ", promoCode=" + promoCode + ", provinceId="
				+ provinceId + ", refund=" + refund + ", refundTime=" + refundTime + ", sellerEvaluate="
				+ sellerEvaluate + ", shipmentType=" + shipmentType + ", state=" + state + ", totalDiscount="
				+ totalDiscount + ", totalPrice=" + totalPrice + ", updateTime=" + updateTime + ", yn=" + yn
				+ ", contractNo=" + contractNo + ", contractId=" + contractId + ", contractState=" + contractState
				+ "]";
	}

}