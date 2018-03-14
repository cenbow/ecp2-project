package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_audit_log")
public class UserAuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "audit_diate")
    private Date auditDiate;

    @Column(name = "audit_id")
    private Long auditId;

    @Column(name = "audit_status")
    private Integer auditStatus;

    @Column(name = "create_dt")
    private Date createDt;

    @Column(name = "deleted_flag")
    private String deletedFlag;

    @Column(name = "last_upd_dt")
    private Date lastUpdDt;

    private String remark;

    @Column(name = "user_id")
    private Long userId;

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
     * @return audit_diate
     */
    public Date getAuditDiate() {
        return auditDiate;
    }

    /**
     * @param auditDiate
     */
    public void setAuditDiate(Date auditDiate) {
        this.auditDiate = auditDiate;
    }

    /**
     * @return audit_id
     */
    public Long getAuditId() {
        return auditId;
    }

    /**
     * @param auditId
     */
    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    /**
     * @return audit_status
     */
    public Integer getAuditStatus() {
        return auditStatus;
    }

    /**
     * @param auditStatus
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * @return create_dt
     */
    public Date getCreateDt() {
        return createDt;
    }

    /**
     * @param createDt
     */
    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    /**
     * @return deleted_flag
     */
    public String getDeletedFlag() {
        return deletedFlag;
    }

    /**
     * @param deletedFlag
     */
    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag == null ? null : deletedFlag.trim();
    }

    /**
     * @return last_upd_dt
     */
    public Date getLastUpdDt() {
        return lastUpdDt;
    }

    /**
     * @param lastUpdDt
     */
    public void setLastUpdDt(Date lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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
		return "UserAuditLog [id=" + id + ", auditDiate=" + auditDiate + ", auditId=" + auditId + ", auditStatus="
				+ auditStatus + ", createDt=" + createDt + ", deletedFlag=" + deletedFlag + ", lastUpdDt=" + lastUpdDt
				+ ", remark=" + remark + ", userId=" + userId + ", deleted=" + deleted + "]";
	}
}