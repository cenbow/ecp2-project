package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "slideshow_setting")
public class SlideshowSetting {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 轮播图的图片路径
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 轮播图的title
     */
    private String title;

    /**
     * 类型：1=商品；2=类目；默认=1
     */
    private Integer type;

    /**
     * 点击轮播图跳转的链接
     */
    private String url;

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
     * 获取轮播图的图片路径
     *
     * @return img_url - 轮播图的图片路径
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 设置轮播图的图片路径
     *
     * @param imgUrl 轮播图的图片路径
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    /**
     * 获取轮播图的title
     *
     * @return title - 轮播图的title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置轮播图的title
     *
     * @param title 轮播图的title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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
     * 获取点击轮播图跳转的链接
     *
     * @return url - 点击轮播图跳转的链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置点击轮播图跳转的链接
     *
     * @param url 点击轮播图跳转的链接
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", title=").append(title);
        sb.append(", type=").append(type);
        sb.append(", url=").append(url);
        sb.append(", showed=").append(showed);
        sb.append(", created=").append(created);
        sb.append(", updated=").append(updated);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}