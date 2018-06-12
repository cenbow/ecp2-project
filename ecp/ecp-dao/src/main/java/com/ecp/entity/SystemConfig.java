package com.ecp.entity;

import javax.persistence.*;

@Table(name = "system_config")
public class SystemConfig {
    /**
     * 系统配置自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 系统配置名称
     */
    @Column(name = "config_name")
    private String configName;

    /**
     * 系统配置值
     */
    @Column(name = "config_value")
    private String configValue;

    /**
     * 备注
     */
    private String comment;

    /**
     * 获取系统配置自增ID
     *
     * @return id - 系统配置自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置系统配置自增ID
     *
     * @param id 系统配置自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取系统配置名称
     *
     * @return config_name - 系统配置名称
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * 设置系统配置名称
     *
     * @param configName 系统配置名称
     */
    public void setConfigName(String configName) {
        this.configName = configName == null ? null : configName.trim();
    }

    /**
     * 获取系统配置值
     *
     * @return config_value - 系统配置值
     */
    public String getConfigValue() {
        return configValue;
    }

    /**
     * 设置系统配置值
     *
     * @param configValue 系统配置值
     */
    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
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
        sb.append(", configName=").append(configName);
        sb.append(", configValue=").append(configValue);
        sb.append(", comment=").append(comment);
        sb.append("]");
        return sb.toString();
    }
}