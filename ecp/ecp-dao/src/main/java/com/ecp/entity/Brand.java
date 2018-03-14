package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "item_brand")
public class Brand {
    @Id
    @Column(name = "brand_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;

    @Column(name = "brand_key")
    private String brandKey;

    @Column(name = "brand_logo_url")
    private String brandLogoUrl;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "brand_status")
    private Integer brandStatus;

    private Date created;

    private Date modified;
    
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
     * @return brand_id
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * @param brandId
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * @return brand_key
     */
    public String getBrandKey() {
        return brandKey;
    }

    /**
     * @param brandKey
     */
    public void setBrandKey(String brandKey) {
        this.brandKey = brandKey == null ? null : brandKey.trim();
    }

    /**
     * @return brand_logo_url
     */
    public String getBrandLogoUrl() {
        return brandLogoUrl;
    }

    /**
     * @param brandLogoUrl
     */
    public void setBrandLogoUrl(String brandLogoUrl) {
        this.brandLogoUrl = brandLogoUrl == null ? null : brandLogoUrl.trim();
    }

    /**
     * @return brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * @param brandName
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    /**
     * @return brand_status
     */
    public Integer getBrandStatus() {
        return brandStatus;
    }

    /**
     * @param brandStatus
     */
    public void setBrandStatus(Integer brandStatus) {
        this.brandStatus = brandStatus;
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

	@Override
	public String toString() {
		return "Brand [brandId=" + brandId + ", brandKey=" + brandKey + ", brandLogoUrl=" + brandLogoUrl
				+ ", brandName=" + brandName + ", brandStatus=" + brandStatus + ", created=" + created + ", modified="
				+ modified + ", deleted=" + deleted + "]";
	}

}