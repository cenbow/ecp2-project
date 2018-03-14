package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mall_banner")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "banner_link")
    private String bannerLink;

    @Column(name = "banner_type")
    private Byte bannerType;

    @Column(name = "banner_url")
    private String bannerUrl;

    private Date created;

    private Integer integral;

    private Date modified;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "sort_number")
    private Short sortNumber;

    @Column(name = "start_time")
    private Date startTime;

    private Byte status;

    @Column(name = "theme_id")
    private Long themeId;

    private String title;
    
    @Column(name = "deleted")
    private Integer deleted;//是否删除（1-未删除，2-删除，默认1）

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
     * @return banner_link
     */
    public String getBannerLink() {
        return bannerLink;
    }

    /**
     * @param bannerLink
     */
    public void setBannerLink(String bannerLink) {
        this.bannerLink = bannerLink == null ? null : bannerLink.trim();
    }

    /**
     * @return banner_type
     */
    public Byte getBannerType() {
        return bannerType;
    }

    /**
     * @param bannerType
     */
    public void setBannerType(Byte bannerType) {
        this.bannerType = bannerType;
    }

    /**
     * @return banner_url
     */
    public String getBannerUrl() {
        return bannerUrl;
    }

    /**
     * @param bannerUrl
     */
    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl == null ? null : bannerUrl.trim();
    }

    /**
     * @return created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return integral
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * @param integral
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * @return modified
     */
    public Date getModified() {
        return modified;
    }

    /**
     * @param modified
     */
    public void setModified(Date modified) {
        this.modified = modified;
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
     * @return sort_number
     */
    public Short getSortNumber() {
        return sortNumber;
    }

    /**
     * @param sortNumber
     */
    public void setSortNumber(Short sortNumber) {
        this.sortNumber = sortNumber;
    }

    /**
     * @return start_time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * @return theme_id
     */
    public Long getThemeId() {
        return themeId;
    }

    /**
     * @param themeId
     */
    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

	@Override
	public String toString() {
		return "Banner [id=" + id + ", bannerLink=" + bannerLink + ", bannerType=" + bannerType + ", bannerUrl="
				+ bannerUrl + ", created=" + created + ", integral=" + integral + ", modified=" + modified + ", skuId="
				+ skuId + ", sortNumber=" + sortNumber + ", startTime=" + startTime + ", status=" + status
				+ ", themeId=" + themeId + ", title=" + title + ", deleted=" + deleted + "]";
	}

}