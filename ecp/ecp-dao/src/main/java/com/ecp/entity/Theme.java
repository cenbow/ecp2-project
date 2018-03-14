package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mall_theme")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long addressid;

    private Long cid;

    private Long clev;

    private String color;

    @Column(name = "color_b")
    private String colorB;

    private Date created;

    private Date modified;

    @Column(name = "primary_cid")
    private Long primaryCid;

    @Column(name = "sort_num")
    private Short sortNum;

    private Short status;

    @Column(name = "theme_name")
    private String themeName;

    private Long type;

    private String userid;

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
     * @return addressid
     */
    public Long getAddressid() {
        return addressid;
    }

    /**
     * @param addressid
     */
    public void setAddressid(Long addressid) {
        this.addressid = addressid;
    }

    /**
     * @return cid
     */
    public Long getCid() {
        return cid;
    }

    /**
     * @param cid
     */
    public void setCid(Long cid) {
        this.cid = cid;
    }

    /**
     * @return clev
     */
    public Long getClev() {
        return clev;
    }

    /**
     * @param clev
     */
    public void setClev(Long clev) {
        this.clev = clev;
    }

    /**
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color
     */
    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    /**
     * @return color_b
     */
    public String getColorB() {
        return colorB;
    }

    /**
     * @param colorB
     */
    public void setColorB(String colorB) {
        this.colorB = colorB == null ? null : colorB.trim();
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
     * @return primary_cid
     */
    public Long getPrimaryCid() {
        return primaryCid;
    }

    /**
     * @param primaryCid
     */
    public void setPrimaryCid(Long primaryCid) {
        this.primaryCid = primaryCid;
    }

    /**
     * @return sort_num
     */
    public Short getSortNum() {
        return sortNum;
    }

    /**
     * @param sortNum
     */
    public void setSortNum(Short sortNum) {
        this.sortNum = sortNum;
    }

    /**
     * @return status
     */
    public Short getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * @return theme_name
     */
    public String getThemeName() {
        return themeName;
    }

    /**
     * @param themeName
     */
    public void setThemeName(String themeName) {
        this.themeName = themeName == null ? null : themeName.trim();
    }

    /**
     * @return type
     */
    public Long getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Long type) {
        this.type = type;
    }

    /**
     * @return userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
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
		return "Theme [id=" + id + ", addressid=" + addressid + ", cid=" + cid + ", clev=" + clev + ", color=" + color
				+ ", colorB=" + colorB + ", created=" + created + ", modified=" + modified + ", primaryCid="
				+ primaryCid + ", sortNum=" + sortNum + ", status=" + status + ", themeName=" + themeName + ", type="
				+ type + ", userid=" + userid + ", deleted=" + deleted + "]";
	}
}