package com.ecp.entity;

import javax.persistence.*;

@Table(name = "commission_percentage_config")
public class CommissionPercentageConfig {
    /**
     * 提成比例配置自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 销售额完成比例
     */
    @Column(name = "sales_completed_percentage")
    private String salesCompletedPercentage;

    /**
     * 提成比例
     */
    @Column(name = "commission_percentage")
    private String commissionPercentage;

    /**
     * 备注
     */
    private String comment;

    /**
     * 获取提成比例配置自增ID
     *
     * @return id - 提成比例配置自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置提成比例配置自增ID
     *
     * @param id 提成比例配置自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取角色
     *
     * @return role_id - 角色
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色
     *
     * @param roleId 角色
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取销售额完成比例
     *
     * @return sales_completed_percentage - 销售额完成比例
     */
    public String getSalesCompletedPercentage() {
        return salesCompletedPercentage;
    }

    /**
     * 设置销售额完成比例
     *
     * @param salesCompletedPercentage 销售额完成比例
     */
    public void setSalesCompletedPercentage(String salesCompletedPercentage) {
        this.salesCompletedPercentage = salesCompletedPercentage == null ? null : salesCompletedPercentage.trim();
    }

    /**
     * 获取提成比例
     *
     * @return commission_percentage - 提成比例
     */
    public String getCommissionPercentage() {
        return commissionPercentage;
    }

    /**
     * 设置提成比例
     *
     * @param commissionPercentage 提成比例
     */
    public void setCommissionPercentage(String commissionPercentage) {
        this.commissionPercentage = commissionPercentage == null ? null : commissionPercentage.trim();
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
        sb.append(", roleId=").append(roleId);
        sb.append(", salesCompletedPercentage=").append(salesCompletedPercentage);
        sb.append(", commissionPercentage=").append(commissionPercentage);
        sb.append(", comment=").append(comment);
        sb.append("]");
        return sb.toString();
    }
}