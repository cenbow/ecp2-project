package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "item_picpdf")
public class ItemPicpdf {
    @Id
    @Column(name = "picpdf_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long picpdfId;

    private Date created;

    @Column(name = "item_id")
    private Long itemId;

    private Date modified;

    @Column(name = "picpdf_name")
    private String picpdfName;

    @Column(name = "picpdf_status")
    private Integer picpdfStatus;

    @Column(name = "picpdf_url")
    private String picpdfUrl;

    /**
     * @return picpdf_id
     */
    public Long getPicpdfId() {
        return picpdfId;
    }

    /**
     * @param picpdfId
     */
    public void setPicpdfId(Long picpdfId) {
        this.picpdfId = picpdfId;
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
     * @return item_id
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * @param itemId
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
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
     * @return picpdf_name
     */
    public String getPicpdfName() {
        return picpdfName;
    }

    /**
     * @param picpdfName
     */
    public void setPicpdfName(String picpdfName) {
        this.picpdfName = picpdfName == null ? null : picpdfName.trim();
    }

    /**
     * @return picpdf_status
     */
    public Integer getPicpdfStatus() {
        return picpdfStatus;
    }

    /**
     * @param picpdfStatus
     */
    public void setPicpdfStatus(Integer picpdfStatus) {
        this.picpdfStatus = picpdfStatus;
    }

    /**
     * @return picpdf_url
     */
    public String getPicpdfUrl() {
        return picpdfUrl;
    }

    /**
     * @param picpdfUrl
     */
    public void setPicpdfUrl(String picpdfUrl) {
        this.picpdfUrl = picpdfUrl == null ? null : picpdfUrl.trim();
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
		return "ItemPicpdf [picpdfId=" + picpdfId + ", created=" + created + ", itemId=" + itemId + ", modified="
				+ modified + ", picpdfName=" + picpdfName + ", picpdfStatus=" + picpdfStatus + ", picpdfUrl="
				+ picpdfUrl + ", deleted=" + deleted + "]";
	}
}