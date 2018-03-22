package com.ecp.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "account_personal")
public class AccountPersonal {
    /**
     * 个人帐薄自增ID
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
    private BigDecimal amount;

    /**
     * 类型（1、业绩；2、市场费；3、四项费用；4、销售滞纳金；5、价差；）
     */
    private Integer type;

    /**
     * 时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作员ID
     */
    @Column(name = "operator_id")
    private Long operatorId;

    /**
     * 操作员姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

    private String comment;

    /**
     * 获取个人帐薄自增ID
     *
     * @return id - 个人帐薄自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置个人帐薄自增ID
     *
     * @param id 个人帐薄自增ID
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
     * @return amount - 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置金额
     *
     * @param amount 金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取类型（1、业绩；2、市场费；3、四项费用；4、销售滞纳金；5、价差；）
     *
     * @return type - 类型（1、业绩；2、市场费；3、四项费用；4、销售滞纳金；5、价差；）
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型（1、业绩；2、市场费；3、四项费用；4、销售滞纳金；5、价差；）
     *
     * @param type 类型（1、业绩；2、市场费；3、四项费用；4、销售滞纳金；5、价差；）
     */
    public void setType(Integer type) {
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
     * @return operator_id - 操作员ID
     */
    public Long getOperatorId() {
        return operatorId;
    }

    /**
     * 设置操作员ID
     *
     * @param operatorId 操作员ID
     */
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * 获取操作员姓名
     *
     * @return operator_name - 操作员姓名
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置操作员姓名
     *
     * @param operatorName 操作员姓名
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    /**
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
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
        sb.append(", itemId=").append(itemId);
        sb.append(", skuId=").append(skuId);
        sb.append(", bindUserId=").append(bindUserId);
        sb.append(", amount=").append(amount);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", operatorId=").append(operatorId);
        sb.append(", operatorName=").append(operatorName);
        sb.append(", comment=").append(comment);
        sb.append("]");
        return sb.toString();
    }
}