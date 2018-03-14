package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "item_sku_picture")
public class SkuPicture {
    @Id
    @Column(name = "picture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pictureId;

    private Date created;

    private Date modified;

    @Column(name = "picture_status")
    private Byte pictureStatus;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "sort_number")
    private Byte sortNumber;

    /**
     * @return picture_id
     */
    public Long getPictureId() {
        return pictureId;
    }

    /**
     * @param pictureId
     */
    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
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

    /**
     * @return picture_status
     */
    public Byte getPictureStatus() {
        return pictureStatus;
    }

    /**
     * @param pictureStatus
     */
    public void setPictureStatus(Byte pictureStatus) {
        this.pictureStatus = pictureStatus;
    }

    /**
     * @return picture_url
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * @param pictureUrl
     */
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
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
    public Byte getSortNumber() {
        return sortNumber;
    }

    /**
     * @param sortNumber
     */
    public void setSortNumber(Byte sortNumber) {
        this.sortNumber = sortNumber;
    }

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

	@Override
	public String toString() {
		return "SkuPicture [pictureId=" + pictureId + ", created=" + created + ", modified=" + modified
				+ ", pictureStatus=" + pictureStatus + ", pictureUrl=" + pictureUrl + ", skuId=" + skuId
				+ ", sortNumber=" + sortNumber + ", deleted=" + deleted + "]";
	}
}