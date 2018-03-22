package com.ecp.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "trade_order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activites_detail_id")
    private Long activitesDetailId;

    @Column(name = "area_id")
    private Long areaId;

    private Long cid;

    @Column(name = "contract_no")
    private String contractNo;

    @Column(name = "coupon_discount")
    private BigDecimal couponDiscount;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "delivery_type")
    private Byte deliveryType;

    private Long integral;

    @Column(name = "integral_discount")
    private BigDecimal integralDiscount;

    @Column(name = "item_id")
    private Long itemId;

    private Integer num;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "pay_price")
    private BigDecimal payPrice;

    @Column(name = "pay_price_total")
    private BigDecimal payPriceTotal;

    @Column(name = "primitive_price")
    private BigDecimal primitivePrice;

    @Column(name = "promotion_discount")
    private BigDecimal promotionDiscount;

    @Column(name = "promotion_id")
    private String promotionId;

    @Column(name = "promotion_type")
    private String promotionType;

    @Column(name = "shop_freight_template_id")
    private Long shopFreightTemplateId;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "sku_name")
    private String skuName;

    @Column(name = "update_time")
    private Date updateTime;

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
     * 硬成本
     */
    @Column(name = "hard_cost_price")
    private BigDecimal hardCostPrice;

    /**
     * 是否有是方案性产品(1:是;2:否)
     */
    @Column(name = "is_plan_product")
    private Byte isPlanProduct;

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
     * @return activites_detail_id
     */
    public Long getActivitesDetailId() {
        return activitesDetailId;
    }

    /**
     * @param activitesDetailId
     */
    public void setActivitesDetailId(Long activitesDetailId) {
        this.activitesDetailId = activitesDetailId;
    }

    /**
     * @return area_id
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * @param areaId
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**
     * @return cid
     */
    public Long getCid() {
        return cid;
    }

    /**
     * @param cid
     */
    public void setCid(Long cid) {
        this.cid = cid;
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
     * @return delivery_type
     */
    public Byte getDeliveryType() {
        return deliveryType;
    }

    /**
     * @param deliveryType
     */
    public void setDeliveryType(Byte deliveryType) {
        this.deliveryType = deliveryType;
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
     * @return num
     */
    public Integer getNum() {
        return num;
    }

    /**
     * @param num
     */
    public void setNum(Integer num) {
        this.num = num;
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
     * @return pay_price
     */
    public BigDecimal getPayPrice() {
        return payPrice;
    }

    /**
     * @param payPrice
     */
    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    /**
     * @return pay_price_total
     */
    public BigDecimal getPayPriceTotal() {
        return payPriceTotal;
    }

    /**
     * @param payPriceTotal
     */
    public void setPayPriceTotal(BigDecimal payPriceTotal) {
        this.payPriceTotal = payPriceTotal;
    }

    /**
     * @return primitive_price
     */
    public BigDecimal getPrimitivePrice() {
        return primitivePrice;
    }

    /**
     * @param primitivePrice
     */
    public void setPrimitivePrice(BigDecimal primitivePrice) {
        this.primitivePrice = primitivePrice;
    }

    /**
     * @return promotion_discount
     */
    public BigDecimal getPromotionDiscount() {
        return promotionDiscount;
    }

    /**
     * @param promotionDiscount
     */
    public void setPromotionDiscount(BigDecimal promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }

    /**
     * @return promotion_id
     */
    public String getPromotionId() {
        return promotionId;
    }

    /**
     * @param promotionId
     */
    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId == null ? null : promotionId.trim();
    }

    /**
     * @return promotion_type
     */
    public String getPromotionType() {
        return promotionType;
    }

    /**
     * @param promotionType
     */
    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType == null ? null : promotionType.trim();
    }

    /**
     * @return shop_freight_template_id
     */
    public Long getShopFreightTemplateId() {
        return shopFreightTemplateId;
    }

    /**
     * @param shopFreightTemplateId
     */
    public void setShopFreightTemplateId(Long shopFreightTemplateId) {
        this.shopFreightTemplateId = shopFreightTemplateId;
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
     * @return sku_name
     */
    public String getSkuName() {
        return skuName;
    }

    /**
     * @param skuName
     */
    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
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
     * 获取硬成本
     *
     * @return hard_cost_price - 硬成本
     */
    public BigDecimal getHardCostPrice() {
        return hardCostPrice;
    }

    /**
     * 设置硬成本
     *
     * @param hardCostPrice 硬成本
     */
    public void setHardCostPrice(BigDecimal hardCostPrice) {
        this.hardCostPrice = hardCostPrice;
    }

    /**
     * 获取是否有是方案性产品(1:是;2:否)
     *
     * @return is_plan_product - 是否有是方案性产品(1:是;2:否)
     */
    public Byte getIsPlanProduct() {
        return isPlanProduct;
    }

    /**
     * 设置是否有是方案性产品(1:是;2:否)
     *
     * @param isPlanProduct 是否有是方案性产品(1:是;2:否)
     */
    public void setIsPlanProduct(Byte isPlanProduct) {
        this.isPlanProduct = isPlanProduct;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", activitesDetailId=").append(activitesDetailId);
        sb.append(", areaId=").append(areaId);
        sb.append(", cid=").append(cid);
        sb.append(", contractNo=").append(contractNo);
        sb.append(", couponDiscount=").append(couponDiscount);
        sb.append(", createTime=").append(createTime);
        sb.append(", deliveryType=").append(deliveryType);
        sb.append(", integral=").append(integral);
        sb.append(", integralDiscount=").append(integralDiscount);
        sb.append(", itemId=").append(itemId);
        sb.append(", num=").append(num);
        sb.append(", orderId=").append(orderId);
        sb.append(", payPrice=").append(payPrice);
        sb.append(", payPriceTotal=").append(payPriceTotal);
        sb.append(", primitivePrice=").append(primitivePrice);
        sb.append(", promotionDiscount=").append(promotionDiscount);
        sb.append(", promotionId=").append(promotionId);
        sb.append(", promotionType=").append(promotionType);
        sb.append(", shopFreightTemplateId=").append(shopFreightTemplateId);
        sb.append(", skuId=").append(skuId);
        sb.append(", skuName=").append(skuName);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", highestPrice=").append(highestPrice);
        sb.append(", lowestPrice=").append(lowestPrice);
        sb.append(", hardCostPrice=").append(hardCostPrice);
        sb.append(", isPlanProduct=").append(isPlanProduct);
        sb.append("]");
        return sb.toString();
    }
}