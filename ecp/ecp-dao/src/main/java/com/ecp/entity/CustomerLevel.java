package com.ecp.entity;

import javax.persistence.*;

@Table(name = "customer_level")
public class CustomerLevel {
    /**
     * 客户级别表自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户类型
     */
    @Column(name = "customer_type_id")
    private Long customerTypeId;

    /**
     * 级别名称
     */
    @Column(name = "level_name")
    private String levelName;

    /**
     * 级别编码
     */
    @Column(name = "level_code")
    private String levelCode;

    /**
     * 备注
     */
    private String comment;

    /**
     * 获取客户级别表自增ID
     *
     * @return id - 客户级别表自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置客户级别表自增ID
     *
     * @param id 客户级别表自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取客户类型
     *
     * @return customer_type_id - 客户类型
     */
    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    /**
     * 设置客户类型
     *
     * @param customerTypeId 客户类型
     */
    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    /**
     * 获取级别名称
     *
     * @return level_name - 级别名称
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * 设置级别名称
     *
     * @param levelName 级别名称
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    /**
     * 获取级别编码
     *
     * @return level_code - 级别编码
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * 设置级别编码
     *
     * @param levelCode 级别编码
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode == null ? null : levelCode.trim();
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
        sb.append(", levelName=").append(levelName);
        sb.append(", levelCode=").append(levelCode);
        sb.append(", comment=").append(comment);
        sb.append("]");
        return sb.toString();
    }
}