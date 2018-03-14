package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_extends")
public class UserExtends {
    @Id
    @Column(name = "extend_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long extendId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "business_licence_pic_src")
    private String businessLicencePicSrc;

    @Column(name = "tax_registration_certificate_pic_src")
    private String taxRegistrationCertificatePicSrc;

    @Column(name = "organization_pic_src")
    private String organizationPicSrc;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "artificial_person_name")
    private String artificialPersonName;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_address")
    private String contactAddress;

    @Column(name = "account_state")
    private Integer accountState;

    @Column(name = "create_dt")
    private Date createDt;

    /**
     * @return extend_id
     */
    public Long getExtendId() {
        return extendId;
    }

    /**
     * @param extendId
     */
    public void setExtendId(Long extendId) {
        this.extendId = extendId;
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

    /**
     * @return business_licence_pic_src
     */
    public String getBusinessLicencePicSrc() {
        return businessLicencePicSrc;
    }

    /**
     * @param businessLicencePicSrc
     */
    public void setBusinessLicencePicSrc(String businessLicencePicSrc) {
        this.businessLicencePicSrc = businessLicencePicSrc == null ? null : businessLicencePicSrc.trim();
    }

    /**
     * @return tax_registration_certificate_pic_src
     */
    public String getTaxRegistrationCertificatePicSrc() {
        return taxRegistrationCertificatePicSrc;
    }

    /**
     * @param taxRegistrationCertificatePicSrc
     */
    public void setTaxRegistrationCertificatePicSrc(String taxRegistrationCertificatePicSrc) {
        this.taxRegistrationCertificatePicSrc = taxRegistrationCertificatePicSrc == null ? null : taxRegistrationCertificatePicSrc.trim();
    }

    /**
     * @return organization_pic_src
     */
    public String getOrganizationPicSrc() {
        return organizationPicSrc;
    }

    /**
     * @param organizationPicSrc
     */
    public void setOrganizationPicSrc(String organizationPicSrc) {
        this.organizationPicSrc = organizationPicSrc == null ? null : organizationPicSrc.trim();
    }

    /**
     * @return company_name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * @return contact_email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail == null ? null : contactEmail.trim();
    }

    /**
     * @return artificial_person_name
     */
    public String getArtificialPersonName() {
        return artificialPersonName;
    }

    /**
     * @param artificialPersonName
     */
    public void setArtificialPersonName(String artificialPersonName) {
        this.artificialPersonName = artificialPersonName == null ? null : artificialPersonName.trim();
    }

    /**
     * @return contact_phone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * @param contactPhone
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    /**
     * @return contact_address
     */
    public String getContactAddress() {
        return contactAddress;
    }

    /**
     * @param contactAddress
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress == null ? null : contactAddress.trim();
    }

    /**
     * @return account_state
     */
    public Integer getAccountState() {
        return accountState;
    }

    /**
     * @param accountState
     */
    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
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
		return "UserExtends [extendId=" + extendId + ", userId=" + userId + ", businessLicencePicSrc="
				+ businessLicencePicSrc + ", taxRegistrationCertificatePicSrc=" + taxRegistrationCertificatePicSrc
				+ ", organizationPicSrc=" + organizationPicSrc + ", companyName=" + companyName + ", contactEmail="
				+ contactEmail + ", artificialPersonName=" + artificialPersonName + ", contactPhone=" + contactPhone
				+ ", contactAddress=" + contactAddress + ", accountState=" + accountState + ", createDt=" + createDt
				+ ", deleted=" + deleted + "]";
	}
}