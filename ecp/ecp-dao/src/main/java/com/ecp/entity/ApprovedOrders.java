package com.ecp.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "trade_approved_orders")
public class ApprovedOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "after_service")
    private Boolean afterService;

    @Column(name = "approve_status")
    private String approveStatus;

    @Column(name = "approv_time")
    private Date approvTime;

    private Long auditorid;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "cancel_time")
    private Date cancelTime;

    @Column(name = "central_purchasing")
    private Byte centralPurchasing;

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

    private Boolean delay;

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

    private Boolean evaluate;

    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    private BigDecimal freight;

    @Column(name = "full_address")
    private String fullAddress;

    private Long integral;

    @Column(name = "integral_discount")
    private BigDecimal integralDiscount;

    private Boolean invoice;

    @Column(name = "invoice_title")
    private String invoiceTitle;

    @Column(name = "lock_time")
    private Date lockTime;

    private Boolean locked;

    @Column(name = "logistics_company")
    private String logisticsCompany;

    @Column(name = "logistics_no")
    private String logisticsNo;

    private String memo;

    private String mobile;

    private String name;

    @Column(name = "order_finsh_time")
    private Date orderFinshTime;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_jfs_key")
    private String orderJfsKey;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "order_type")
    private Boolean orderType;

    private Integer paid;

    @Column(name = "parent_order_id")
    private Long parentOrderId;

    @Column(name = "pay_period")
    private Integer payPeriod;

    @Column(name = "payment_method")
    private Boolean paymentMethod;

    @Column(name = "payment_price")
    private BigDecimal paymentPrice;

    @Column(name = "payment_time")
    private Date paymentTime;

    @Column(name = "payment_type")
    private Boolean paymentType;

    private String phone;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "province_id")
    private Long provinceId;

    private Boolean refund;

    @Column(name = "refund_time")
    private Date refundTime;

    @Column(name = "reject_reason")
    private String rejectReason;

    @Column(name = "seller_evaluate")
    private Boolean sellerEvaluate;

    @Column(name = "sell_id")
    private Long sellId;

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
    public Boolean getAfterService() {
        return afterService;
    }

    /**
     * @param afterService
     */
    public void setAfterService(Boolean afterService) {
        this.afterService = afterService;
    }

    /**
     * @return approve_status
     */
    public String getApproveStatus() {
        return approveStatus;
    }

    /**
     * @param approveStatus
     */
    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus == null ? null : approveStatus.trim();
    }

    /**
     * @return approv_time
     */
    public Date getApprovTime() {
        return approvTime;
    }

    /**
     * @param approvTime
     */
    public void setApprovTime(Date approvTime) {
        this.approvTime = approvTime;
    }

    /**
     * @return auditorid
     */
    public Long getAuditorid() {
        return auditorid;
    }

    /**
     * @param auditorid
     */
    public void setAuditorid(Long auditorid) {
        this.auditorid = auditorid;
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
     * @return central_purchasing
     */
    public Byte getCentralPurchasing() {
        return centralPurchasing;
    }

    /**
     * @param centralPurchasing
     */
    public void setCentralPurchasing(Byte centralPurchasing) {
        this.centralPurchasing = centralPurchasing;
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
    public Boolean getDelay() {
        return delay;
    }

    /**
     * @param delay
     */
    public void setDelay(Boolean delay) {
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
    public Boolean getEvaluate() {
        return evaluate;
    }

    /**
     * @param evaluate
     */
    public void setEvaluate(Boolean evaluate) {
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
    public Boolean getInvoice() {
        return invoice;
    }

    /**
     * @param invoice
     */
    public void setInvoice(Boolean invoice) {
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
    public Boolean getLocked() {
        return locked;
    }

    /**
     * @param locked
     */
    public void setLocked(Boolean locked) {
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
     * @return order_finsh_time
     */
    public Date getOrderFinshTime() {
        return orderFinshTime;
    }

    /**
     * @param orderFinshTime
     */
    public void setOrderFinshTime(Date orderFinshTime) {
        this.orderFinshTime = orderFinshTime;
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
    public Boolean getOrderType() {
        return orderType;
    }

    /**
     * @param orderType
     */
    public void setOrderType(Boolean orderType) {
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
    public Long getParentOrderId() {
        return parentOrderId;
    }

    /**
     * @param parentOrderId
     */
    public void setParentOrderId(Long parentOrderId) {
        this.parentOrderId = parentOrderId;
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
    public Boolean getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod
     */
    public void setPaymentMethod(Boolean paymentMethod) {
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
    public Boolean getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType
     */
    public void setPaymentType(Boolean paymentType) {
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
    public Boolean getRefund() {
        return refund;
    }

    /**
     * @param refund
     */
    public void setRefund(Boolean refund) {
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
     * @return reject_reason
     */
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     * @param rejectReason
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
    }

    /**
     * @return seller_evaluate
     */
    public Boolean getSellerEvaluate() {
        return sellerEvaluate;
    }

    /**
     * @param sellerEvaluate
     */
    public void setSellerEvaluate(Boolean sellerEvaluate) {
        this.sellerEvaluate = sellerEvaluate;
    }

    /**
     * @return sell_id
     */
    public Long getSellId() {
        return sellId;
    }

    /**
     * @param sellId
     */
    public void setSellId(Long sellId) {
        this.sellId = sellId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", afterService=").append(afterService);
        sb.append(", approveStatus=").append(approveStatus);
        sb.append(", approvTime=").append(approvTime);
        sb.append(", auditorid=").append(auditorid);
        sb.append(", buyerId=").append(buyerId);
        sb.append(", cancelTime=").append(cancelTime);
        sb.append(", centralPurchasing=").append(centralPurchasing);
        sb.append(", changePaymentPriceTime=").append(changePaymentPriceTime);
        sb.append(", cityId=").append(cityId);
        sb.append(", confirmReceiptTime=").append(confirmReceiptTime);
        sb.append(", countyId=").append(countyId);
        sb.append(", createTime=").append(createTime);
        sb.append(", delay=").append(delay);
        sb.append(", delayOverTime=").append(delayOverTime);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", deliverTime=").append(deliverTime);
        sb.append(", detailAddress=").append(detailAddress);
        sb.append(", discountAmount=").append(discountAmount);
        sb.append(", email=").append(email);
        sb.append(", evaluate=").append(evaluate);
        sb.append(", exchangeRate=").append(exchangeRate);
        sb.append(", freight=").append(freight);
        sb.append(", fullAddress=").append(fullAddress);
        sb.append(", integral=").append(integral);
        sb.append(", integralDiscount=").append(integralDiscount);
        sb.append(", invoice=").append(invoice);
        sb.append(", invoiceTitle=").append(invoiceTitle);
        sb.append(", lockTime=").append(lockTime);
        sb.append(", locked=").append(locked);
        sb.append(", logisticsCompany=").append(logisticsCompany);
        sb.append(", logisticsNo=").append(logisticsNo);
        sb.append(", memo=").append(memo);
        sb.append(", mobile=").append(mobile);
        sb.append(", name=").append(name);
        sb.append(", orderFinshTime=").append(orderFinshTime);
        sb.append(", orderId=").append(orderId);
        sb.append(", orderJfsKey=").append(orderJfsKey);
        sb.append(", orderTime=").append(orderTime);
        sb.append(", orderType=").append(orderType);
        sb.append(", paid=").append(paid);
        sb.append(", parentOrderId=").append(parentOrderId);
        sb.append(", payPeriod=").append(payPeriod);
        sb.append(", paymentMethod=").append(paymentMethod);
        sb.append(", paymentPrice=").append(paymentPrice);
        sb.append(", paymentTime=").append(paymentTime);
        sb.append(", paymentType=").append(paymentType);
        sb.append(", phone=").append(phone);
        sb.append(", promoCode=").append(promoCode);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", refund=").append(refund);
        sb.append(", refundTime=").append(refundTime);
        sb.append(", rejectReason=").append(rejectReason);
        sb.append(", sellerEvaluate=").append(sellerEvaluate);
        sb.append(", sellId=").append(sellId);
        sb.append(", shipmentType=").append(shipmentType);
        sb.append(", state=").append(state);
        sb.append(", totalDiscount=").append(totalDiscount);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", yn=").append(yn);
        sb.append("]");
        return sb.toString();
    }
}