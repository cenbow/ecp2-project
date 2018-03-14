package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "recommend")
public class Recommend {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 推荐名称（默认是类目名称）
     */
    private String name;

    /**
     * 点击推荐名称跳转的链接
     */
    private String url;

    /**
     * 类型：1=商品；2=类目；默认=1
     */
    private Integer type;

    /**
     * 是否在前端显示（1-显示，2-不显示，默认1）
     */
    private Integer showed;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 是否删除（1-未删除，2-删除，默认1）
     */
    private Integer deleted;

    /**
     * 获取自增ID
     *
     * @return id - 自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置自增ID
     *
     * @param id 自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取推荐名称（默认是类目名称）
     *
     * @return name - 推荐名称（默认是类目名称）
     */
    public String getName() {
        return name;
    }

    /**
     * 设置推荐名称（默认是类目名称）
     *
     * @param name 推荐名称（默认是类目名称）
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取点击推荐名称跳转的链接
     *
     * @return url - 点击推荐名称跳转的链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置点击推荐名称跳转的链接
     *
     * @param url 点击推荐名称跳转的链接
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取类型：1=商品；2=类目；默认=1
     *
     * @return type - 类型：1=商品；2=类目；默认=1
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型：1=商品；2=类目；默认=1
     *
     * @param type 类型：1=商品；2=类目；默认=1
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取是否在前端显示（1-显示，2-不显示，默认1）
     *
     * @return showed - 是否在前端显示（1-显示，2-不显示，默认1）
     */
    public Integer getShowed() {
        return showed;
    }

    /**
     * 设置是否在前端显示（1-显示，2-不显示，默认1）
     *
     * @param showed 是否在前端显示（1-显示，2-不显示，默认1）
     */
    public void setShowed(Integer showed) {
        this.showed = showed;
    }

    /**
     * 获取创建时间
     *
     * @return created - 创建时间
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 设置创建时间
     *
     * @param created 创建时间
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * 获取更新时间
     *
     * @return updated - 更新时间
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * 设置更新时间
     *
     * @param updated 更新时间
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", url=").append(url);
        sb.append(", type=").append(type);
        sb.append(", showed=").append(showed);
        sb.append(", created=").append(created);
        sb.append(", updated=").append(updated);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}