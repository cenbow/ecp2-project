package com.ecp.entity;

import javax.persistence.*;

@Table(name = "customer_level_rule")
public class CustomerLevelRule {
    /**
     * 客户级别-价格规则表自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户类型ID
     */
    @Column(name = "customer_type_id")
    private Long customerTypeId;

    /**
     * 客户级别ID
     */
    @Column(name = "customer_level_id")
    private Long customerLevelId;

    /**
     * 规则类型（1：百分比；2：数值）
     */
    @Column(name = "rule_type")
    private Byte ruleType;

    /**
     * 规则名称
     */
    @Column(name = "rule_name")
    private String ruleName;

    /**
     * 备注
     */
    private String comment;

    /**
     * 获取客户级别-价格规则表自增ID
     *
     * @return id - 客户级别-价格规则表自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置客户级别-价格规则表自增ID
     *
     * @param id 客户级别-价格规则表自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取客户类型ID
     *
     * @return customer_type_id - 客户类型ID
     */
    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    /**
     * 设置客户类型ID
     *
     * @param customerTypeId 客户类型ID
     */
    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    /**
     * 获取客户级别ID
     *
     * @return customer_level_id - 客户级别ID
     */
    public Long getCustomerLevelId() {
        return customerLevelId;
    }

    /**
     * 设置客户级别ID
     *
     * @param customerLevelId 客户级别ID
     */
    public void setCustomerLevelId(Long customerLevelId) {
        this.customerLevelId = customerLevelId;
    }

    /**
     * 获取规则类型（1：百分比；2：数值）
     *
     * @return rule_type - 规则类型（1：百分比；2：数值）
     */
    public Byte getRuleType() {
        return ruleType;
    }

    /**
     * 设置规则类型（1：百分比；2：数值）
     *
     * @param ruleType 规则类型（1：百分比；2：数值）
     */
    public void setRuleType(Byte ruleType) {
        this.ruleType = ruleType;
    }

    /**
     * 获取规则名称
     *
     * @return rule_name - 规则名称
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * 设置规则名称
     *
     * @param ruleName 规则名称
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", customerTypeId=").append(customerTypeId);
        sb.append(", customerLevelId=").append(customerLevelId);
        sb.append(", ruleType=").append(ruleType);
        sb.append(", ruleName=").append(ruleName);
        sb.append(", comment=").append(comment);
        sb.append("]");
        return sb.toString();
    }
}