package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mall_notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date created;

    @Column(name = "is_recommend")
    private Byte isRecommend;

    private Date modified;

    @Column(name = "notice_type")
    private Integer noticeType;

    @Column(name = "platform_id")
    private Long platformId;

    @Column(name = "sort_num")
    private Byte sortNum;

    private Byte status;

    @Column(name = "theme_id")
    private Integer themeId;

    @Column(name = "theme_type")
    private Byte themeType;

    private String title;

    private String url;

    private String content;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return is_recommend
     */
    public Byte getIsRecommend() {
        return isRecommend;
    }

    /**
     * @param isRecommend
     */
    public void setIsRecommend(Byte isRecommend) {
        this.isRecommend = isRecommend;
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
     * @return notice_type
     */
    public Integer getNoticeType() {
        return noticeType;
    }

    /**
     * @param noticeType
     */
    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    /**
     * @return platform_id
     */
    public Long getPlatformId() {
        return platformId;
    }

    /**
     * @param platformId
     */
    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    /**
     * @return sort_num
     */
    public Byte getSortNum() {
        return sortNum;
    }

    /**
     * @param sortNum
     */
    public void setSortNum(Byte sortNum) {
        this.sortNum = sortNum;
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
    public Integer getThemeId() {
        return themeId;
    }

    /**
     * @param themeId
     */
    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    /**
     * @return theme_type
     */
    public Byte getThemeType() {
        return themeType;
    }

    /**
     * @param themeType
     */
    public void setThemeType(Byte themeType) {
        this.themeType = themeType;
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

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
		return "Notice [id=" + id + ", created=" + created + ", isRecommend=" + isRecommend + ", modified=" + modified
				+ ", noticeType=" + noticeType + ", platformId=" + platformId + ", sortNum=" + sortNum + ", status="
				+ status + ", themeId=" + themeId + ", themeType=" + themeType + ", title=" + title + ", url=" + url
				+ ", content=" + content + ", deleted=" + deleted + "]";
	}
}