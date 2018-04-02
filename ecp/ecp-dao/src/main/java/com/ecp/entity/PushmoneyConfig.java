package com.ecp.entity;

import javax.persistence.*;

@Table(name = "pushmoney_config")
public class PushmoneyConfig {
    /**
     * 提成配置表自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 销售额完成比例
     */
    @Column(name = "completion_rate")
    private Integer completionRate;

    /**
     * 提成比例
     */
    @Column(name = "pushmoney_rate")
    private Integer pushmoneyRate;

    /**
     * 备注
     */
    private String comment;

    /**
     * 获取提成配置表自增ID
     *
     * @return id - 提成配置表自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置提成配置表自增ID
     *
     * @param id 提成配置表自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取销售额完成比例
     *
     * @return completion_rate - 销售额完成比例
     */
    public Integer getCompletionRate() {
        return completionRate;
    }

    /**
     * 设置销售额完成比例
     *
     * @param completionRate 销售额完成比例
     */
    public void setCompletionRate(Integer completionRate) {
        this.completionRate = completionRate;
    }

    /**
     * 获取提成比例
     *
     * @return pushmoney_rate - 提成比例
     */
    public Integer getPushmoneyRate() {
        return pushmoneyRate;
    }

    /**
     * 设置提成比例
     *
     * @param pushmoneyRate 提成比例
     */
    public void setPushmoneyRate(Integer pushmoneyRate) {
        this.pushmoneyRate = pushmoneyRate;
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
        sb.append(", completionRate=").append(completionRate);
        sb.append(", pushmoneyRate=").append(pushmoneyRate);
        sb.append(", comment=").append(comment);
        sb.append("]");
        return sb.toString();
    }
}