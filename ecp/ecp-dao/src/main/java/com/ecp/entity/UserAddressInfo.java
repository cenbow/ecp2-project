package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "delivery_useful_address_info")
public class UserAddressInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attach_address")
    private String attachAddress;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_tel")
    private String contactTel;

    @Column(name = "county_code")
    private String countyCode;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "emergency_person")
    private String emergencyPerson;

    @Column(name = "emergency_phone")
    private String emergencyPhone;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "is_default")
    private Byte isDefault;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "province_code")
    private String provinceCode;

    @Column(name = "town_code")
    private String townCode;

    @Column(name = "updte_time")
    private Date updteTime;

    @Column(name = "village_code")
    private String villageCode;

    private Byte yn;

    @Column(name = "province_name")
    private String provinceName;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "county_name")
    private String countyName;

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
     * @return attach_address
     */
    public String getAttachAddress() {
        return attachAddress;
    }

    /**
     * @param attachAddress
     */
    public void setAttachAddress(String attachAddress) {
        this.attachAddress = attachAddress == null ? null : attachAddress.trim();
    }

    /**
     * @return buyer_id
     */
    public Long getBuyerId() {
        return buyerId;
    }

    /**
     * @param buyerId
     */
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    /**
     * @return city_code
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * @param cityCode
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
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
     * @return contact_person
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * @param contactPerson
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
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
     * @return contact_tel
     */
    public String getContactTel() {
        return contactTel;
    }

    /**
     * @param contactTel
     */
    public void setContactTel(String contactTel) {
        this.contactTel = contactTel == null ? null : contactTel.trim();
    }

    /**
     * @return county_code
     */
    public String getCountyCode() {
        return countyCode;
    }

    /**
     * @param countyCode
     */
    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode == null ? null : countyCode.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return emergency_person
     */
    public String getEmergencyPerson() {
        return emergencyPerson;
    }

    /**
     * @param emergencyPerson
     */
    public void setEmergencyPerson(String emergencyPerson) {
        this.emergencyPerson = emergencyPerson == null ? null : emergencyPerson.trim();
    }

    /**
     * @return emergency_phone
     */
    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    /**
     * @param emergencyPhone
     */
    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone == null ? null : emergencyPhone.trim();
    }

    /**
     * @return full_address
     */
    public String getFullAddress() {
        return fullAddress;
    }

    /**
     * @param fullAddress
     */
    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress == null ? null : fullAddress.trim();
    }

    /**
     * @return is_default
     */
    public Byte getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault
     */
    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return postal_code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode == null ? null : postalCode.trim();
    }

    /**
     * @return province_code
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * @param provinceCode
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    /**
     * @return town_code
     */
    public String getTownCode() {
        return townCode;
    }

    /**
     * @param townCode
     */
    public void setTownCode(String townCode) {
        this.townCode = townCode == null ? null : townCode.trim();
    }

    /**
     * @return updte_time
     */
    public Date getUpdteTime() {
        return updteTime;
    }

    /**
     * @param updteTime
     */
    public void setUpdteTime(Date updteTime) {
        this.updteTime = updteTime;
    }

    /**
     * @return village_code
     */
    public String getVillageCode() {
        return villageCode;
    }

    /**
     * @param villageCode
     */
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode == null ? null : villageCode.trim();
    }

    /**
     * @return yn
     */
    public Byte getYn() {
        return yn;
    }

    /**
     * @param yn
     */
    public void setYn(Byte yn) {
        this.yn = yn;
    }

    /**
     * @return province_name
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * @param provinceName
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    /**
     * @return city_name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    /**
     * @return county_name
     */
    public String getCountyName() {
        return countyName;
    }

    /**
     * @param countyName
     */
    public void setCountyName(String countyName) {
        this.countyName = countyName == null ? null : countyName.trim();
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
		return "UserAddressInfo [id=" + id + ", attachAddress=" + attachAddress + ", buyerId=" + buyerId + ", cityCode="
				+ cityCode + ", contactEmail=" + contactEmail + ", contactPerson=" + contactPerson + ", contactPhone="
				+ contactPhone + ", contactTel=" + contactTel + ", countyCode=" + countyCode + ", createTime="
				+ createTime + ", emergencyPerson=" + emergencyPerson + ", emergencyPhone=" + emergencyPhone
				+ ", fullAddress=" + fullAddress + ", isDefault=" + isDefault + ", postalCode=" + postalCode
				+ ", provinceCode=" + provinceCode + ", townCode=" + townCode + ", updteTime=" + updteTime
				+ ", villageCode=" + villageCode + ", yn=" + yn + ", provinceName=" + provinceName + ", cityName="
				+ cityName + ", countyName=" + countyName + ", deleted=" + deleted + "]";
	}
}