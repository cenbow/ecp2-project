package com.ecp.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ecp.entity.Menu;

public class UserBean implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String auditRemark;

    private String auditor;

    private Date createdTime;

    private Integer deleted;

    private String department;

    private String email;

    private BigDecimal growthValue;

    private String linkMan;

    private String linkPhoneNum;

    private String mobile;

    private String nickname;

    private String oldpassword;

    private Long parentId;

    private String password;

    private Integer quickType;

    private Integer securityLevel;

    private Integer status;

    private Integer type;

    private Date updateTime;

    private String username;
    
    private List<Menu> menuList;

    /**
     * 默认构造函数
     */
    public UserBean() {
		super();
	}

	/**
	 * 构造函数
	 * @param id
	 * @param auditRemark
	 * @param auditor
	 * @param createdTime
	 * @param deleted
	 * @param department
	 * @param email
	 * @param growthValue
	 * @param linkMan
	 * @param linkPhoneNum
	 * @param mobile
	 * @param nickname
	 * @param oldpassword
	 * @param parentId
	 * @param password
	 * @param quickType
	 * @param securityLevel
	 * @param status
	 * @param type
	 * @param updateTime
	 * @param username
	 */
	public UserBean(Long id, String auditRemark, String auditor, Date createdTime, Integer deleted, String department,
			String email, BigDecimal growthValue, String linkMan, String linkPhoneNum, String mobile, String nickname,
			String oldpassword, Long parentId, String password, Integer quickType, Integer securityLevel,
			Integer status, Integer type, Date updateTime, String username) {
		super();
		this.id = id;
		this.auditRemark = auditRemark;
		this.auditor = auditor;
		this.createdTime = createdTime;
		this.deleted = deleted;
		this.department = department;
		this.email = email;
		this.growthValue = growthValue;
		this.linkMan = linkMan;
		this.linkPhoneNum = linkPhoneNum;
		this.mobile = mobile;
		this.nickname = nickname;
		this.oldpassword = oldpassword;
		this.parentId = parentId;
		this.password = password;
		this.quickType = quickType;
		this.securityLevel = securityLevel;
		this.status = status;
		this.type = type;
		this.updateTime = updateTime;
		this.username = username;
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
     * @return audit_remark
     */
    public String getAuditRemark() {
        return auditRemark;
    }

    /**
     * @param auditRemark
     */
    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark == null ? null : auditRemark.trim();
    }

    /**
     * @return auditor
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * @param auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    /**
     * @return created_time
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * @return deleted
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * @param deleted
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * @return department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department
     */
    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * @return growth_value
     */
    public BigDecimal getGrowthValue() {
        return growthValue;
    }

    /**
     * @param growthValue
     */
    public void setGrowthValue(BigDecimal growthValue) {
        this.growthValue = growthValue;
    }

    /**
     * @return link_man
     */
    public String getLinkMan() {
        return linkMan;
    }

    /**
     * @param linkMan
     */
    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan == null ? null : linkMan.trim();
    }

    /**
     * @return link_phone_num
     */
    public String getLinkPhoneNum() {
        return linkPhoneNum;
    }

    /**
     * @param linkPhoneNum
     */
    public void setLinkPhoneNum(String linkPhoneNum) {
        this.linkPhoneNum = linkPhoneNum == null ? null : linkPhoneNum.trim();
    }

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * @return oldpassword
     */
    public String getOldpassword() {
        return oldpassword;
    }

    /**
     * @param oldpassword
     */
    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword == null ? null : oldpassword.trim();
    }

    /**
     * @return parent_id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return quick_type
     */
    public Integer getQuickType() {
        return quickType;
    }

    /**
     * @param quickType
     */
    public void setQuickType(Integer quickType) {
        this.quickType = quickType;
    }

    /**
     * @return security_level
     */
    public Integer getSecurityLevel() {
        return securityLevel;
    }

    /**
     * @param securityLevel
     */
    public void setSecurityLevel(Integer securityLevel) {
        this.securityLevel = securityLevel;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", auditRemark=" + auditRemark + ", auditor=" + auditor + ", createdTime="
				+ createdTime + ", deleted=" + deleted + ", department=" + department + ", email=" + email
				+ ", growthValue=" + growthValue + ", linkMan=" + linkMan + ", linkPhoneNum=" + linkPhoneNum
				+ ", mobile=" + mobile + ", nickname=" + nickname + ", oldpassword=" + oldpassword + ", parentId="
				+ parentId + ", password=" + password + ", quickType=" + quickType + ", securityLevel=" + securityLevel
				+ ", status=" + status + ", type=" + type + ", updateTime=" + updateTime + ", username=" + username
				+ ", menuList=" + menuList + "]";
	}

}