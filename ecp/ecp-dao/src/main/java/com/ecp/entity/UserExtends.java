package com.ecp.entity;

import java.math.BigDecimal;
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
     * 是否删除（1-未删除，2-删除，默认1）
     */
    private Integer deleted;

    /**
     * 客户全称
     */
    @Column(name = "chinese_name")
    private String chineseName;

    /**
     * 客户简称
     */
    @Column(name = "chinese_short_name")
    private String chineseShortName;

    /**
     * 英文全称
     */
    @Column(name = "english_name")
    private String englishName;

    /**
     * 英文简称
     */
    @Column(name = "english_short_name")
    private String englishShortName;

    /**
     * 客户类别
     */
    @Column(name = "customer_type")
    private String customerType;

    /**
     * 所在国家
     */
    private String country;

    /**
     * 所在省份
     */
    private String province;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 所在区县
     */
    private String area;

    /**
     * 补充区县
     */
    @Column(name = "added_area")
    private String addedArea;

    /**
     * 主行业
     */
    @Column(name = "main_trade")
    private String mainTrade;

    /**
     * 一级子行业
     */
    @Column(name = "first_trade")
    private String firstTrade;

    /**
     * 二级子行业
     */
    @Column(name = "second_trade")
    private String secondTrade;

    /**
     * 办公地址
     */
    @Column(name = "office_address")
    private String officeAddress;

    /**
     * 邮政编码
     */
    @Column(name = "office_postcode")
    private String officePostcode;

    /**
     * 办公电话
     */
    @Column(name = "office_tel")
    private String officeTel;

    /**
     * 办公邮箱
     */
    @Column(name = "office_email")
    private String officeEmail;

    /**
     * 注册名称
     */
    @Column(name = "register_name")
    private String registerName;

    /**
     * 注册资金（万元）
     */
    @Column(name = "register_fund")
    private BigDecimal registerFund;

    /**
     * 注册地址
     */
    @Column(name = "register_address")
    private String registerAddress;

    /**
     * 注册邮编
     */
    @Column(name = "register_postcode")
    private String registerPostcode;

    /**
     * 成立时间
     */
    @Column(name = "register_date")
    private Date registerDate;

    /**
     * 客户规模
     */
    @Column(name = "customer_size")
    private String customerSize;

    /**
     * 客户人员
     */
    @Column(name = "customer_number")
    private String customerNumber;

    /**
     * 经济类型
     */
    @Column(name = "economic_type")
    private String economicType;

    /**
     * 出资方式
     */
    @Column(name = "investment_ways")
    private String investmentWays;

    /**
     * 公司税号
     */
    @Column(name = "company_number")
    private String companyNumber;

    /**
     * 是否上市（1：是；2：否）
     */
    @Column(name = "is_listing")
    private Byte isListing;

    /**
     * 是否跨国（1：是；2：否）
     */
    @Column(name = "is_transnational")
    private Byte isTransnational;

    /**
     * 是否集团客户（1：是；2：否）
     */
    @Column(name = "is_bloc")
    private Byte isBloc;

    /**
     * 是否总部（1：是；2：否）
     */
    @Column(name = "is_headquarters")
    private Byte isHeadquarters;

    /**
     * 公司网址
     */
    @Column(name = "company_website")
    private String companyWebsite;

    /**
     * 客户简介
     */
    @Column(name = "customer_intro")
    private String customerIntro;

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

    /**
     * 获取是否删除（1-未删除，2-删除，默认1）
     *
     * @return deleted - 是否删除（1-未删除，2-删除，默认1）
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 设置是否删除（1-未删除，2-删除，默认1）
     *
     * @param deleted 是否删除（1-未删除，2-删除，默认1）
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取客户全称
     *
     * @return chinese_name - 客户全称
     */
    public String getChineseName() {
        return chineseName;
    }

    /**
     * 设置客户全称
     *
     * @param chineseName 客户全称
     */
    public void setChineseName(String chineseName) {
        this.chineseName = chineseName == null ? null : chineseName.trim();
    }

    /**
     * 获取客户简称
     *
     * @return chinese_short_name - 客户简称
     */
    public String getChineseShortName() {
        return chineseShortName;
    }

    /**
     * 设置客户简称
     *
     * @param chineseShortName 客户简称
     */
    public void setChineseShortName(String chineseShortName) {
        this.chineseShortName = chineseShortName == null ? null : chineseShortName.trim();
    }

    /**
     * 获取英文全称
     *
     * @return english_name - 英文全称
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * 设置英文全称
     *
     * @param englishName 英文全称
     */
    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    /**
     * 获取英文简称
     *
     * @return english_short_name - 英文简称
     */
    public String getEnglishShortName() {
        return englishShortName;
    }

    /**
     * 设置英文简称
     *
     * @param englishShortName 英文简称
     */
    public void setEnglishShortName(String englishShortName) {
        this.englishShortName = englishShortName == null ? null : englishShortName.trim();
    }

    /**
     * 获取客户类别
     *
     * @return customer_type - 客户类别
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * 设置客户类别
     *
     * @param customerType 客户类别
     */
    public void setCustomerType(String customerType) {
        this.customerType = customerType == null ? null : customerType.trim();
    }

    /**
     * 获取所在国家
     *
     * @return country - 所在国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置所在国家
     *
     * @param country 所在国家
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    /**
     * 获取所在省份
     *
     * @return province - 所在省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置所在省份
     *
     * @param province 所在省份
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取所在城市
     *
     * @return city - 所在城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置所在城市
     *
     * @param city 所在城市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 获取所在区县
     *
     * @return area - 所在区县
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置所在区县
     *
     * @param area 所在区县
     */
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * 获取补充区县
     *
     * @return added_area - 补充区县
     */
    public String getAddedArea() {
        return addedArea;
    }

    /**
     * 设置补充区县
     *
     * @param addedArea 补充区县
     */
    public void setAddedArea(String addedArea) {
        this.addedArea = addedArea == null ? null : addedArea.trim();
    }

    /**
     * 获取主行业
     *
     * @return main_trade - 主行业
     */
    public String getMainTrade() {
        return mainTrade;
    }

    /**
     * 设置主行业
     *
     * @param mainTrade 主行业
     */
    public void setMainTrade(String mainTrade) {
        this.mainTrade = mainTrade == null ? null : mainTrade.trim();
    }

    /**
     * 获取一级子行业
     *
     * @return first_trade - 一级子行业
     */
    public String getFirstTrade() {
        return firstTrade;
    }

    /**
     * 设置一级子行业
     *
     * @param firstTrade 一级子行业
     */
    public void setFirstTrade(String firstTrade) {
        this.firstTrade = firstTrade == null ? null : firstTrade.trim();
    }

    /**
     * 获取二级子行业
     *
     * @return second_trade - 二级子行业
     */
    public String getSecondTrade() {
        return secondTrade;
    }

    /**
     * 设置二级子行业
     *
     * @param secondTrade 二级子行业
     */
    public void setSecondTrade(String secondTrade) {
        this.secondTrade = secondTrade == null ? null : secondTrade.trim();
    }

    /**
     * 获取办公地址
     *
     * @return office_address - 办公地址
     */
    public String getOfficeAddress() {
        return officeAddress;
    }

    /**
     * 设置办公地址
     *
     * @param officeAddress 办公地址
     */
    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress == null ? null : officeAddress.trim();
    }

    /**
     * 获取邮政编码
     *
     * @return office_postcode - 邮政编码
     */
    public String getOfficePostcode() {
        return officePostcode;
    }

    /**
     * 设置邮政编码
     *
     * @param officePostcode 邮政编码
     */
    public void setOfficePostcode(String officePostcode) {
        this.officePostcode = officePostcode == null ? null : officePostcode.trim();
    }

    /**
     * 获取办公电话
     *
     * @return office_tel - 办公电话
     */
    public String getOfficeTel() {
        return officeTel;
    }

    /**
     * 设置办公电话
     *
     * @param officeTel 办公电话
     */
    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel == null ? null : officeTel.trim();
    }

    /**
     * 获取办公邮箱
     *
     * @return office_email - 办公邮箱
     */
    public String getOfficeEmail() {
        return officeEmail;
    }

    /**
     * 设置办公邮箱
     *
     * @param officeEmail 办公邮箱
     */
    public void setOfficeEmail(String officeEmail) {
        this.officeEmail = officeEmail == null ? null : officeEmail.trim();
    }

    /**
     * 获取注册名称
     *
     * @return register_name - 注册名称
     */
    public String getRegisterName() {
        return registerName;
    }

    /**
     * 设置注册名称
     *
     * @param registerName 注册名称
     */
    public void setRegisterName(String registerName) {
        this.registerName = registerName == null ? null : registerName.trim();
    }

    /**
     * 获取注册资金（万元）
     *
     * @return register_fund - 注册资金（万元）
     */
    public BigDecimal getRegisterFund() {
        return registerFund;
    }

    /**
     * 设置注册资金（万元）
     *
     * @param registerFund 注册资金（万元）
     */
    public void setRegisterFund(BigDecimal registerFund) {
        this.registerFund = registerFund;
    }

    /**
     * 获取注册地址
     *
     * @return register_address - 注册地址
     */
    public String getRegisterAddress() {
        return registerAddress;
    }

    /**
     * 设置注册地址
     *
     * @param registerAddress 注册地址
     */
    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress == null ? null : registerAddress.trim();
    }

    /**
     * 获取注册邮编
     *
     * @return register_postcode - 注册邮编
     */
    public String getRegisterPostcode() {
        return registerPostcode;
    }

    /**
     * 设置注册邮编
     *
     * @param registerPostcode 注册邮编
     */
    public void setRegisterPostcode(String registerPostcode) {
        this.registerPostcode = registerPostcode == null ? null : registerPostcode.trim();
    }

    /**
     * 获取成立时间
     *
     * @return register_date - 成立时间
     */
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * 设置成立时间
     *
     * @param registerDate 成立时间
     */
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * 获取客户规模
     *
     * @return customer_size - 客户规模
     */
    public String getCustomerSize() {
        return customerSize;
    }

    /**
     * 设置客户规模
     *
     * @param customerSize 客户规模
     */
    public void setCustomerSize(String customerSize) {
        this.customerSize = customerSize == null ? null : customerSize.trim();
    }

    /**
     * 获取客户人员
     *
     * @return customer_number - 客户人员
     */
    public String getCustomerNumber() {
        return customerNumber;
    }

    /**
     * 设置客户人员
     *
     * @param customerNumber 客户人员
     */
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber == null ? null : customerNumber.trim();
    }

    /**
     * 获取经济类型
     *
     * @return economic_type - 经济类型
     */
    public String getEconomicType() {
        return economicType;
    }

    /**
     * 设置经济类型
     *
     * @param economicType 经济类型
     */
    public void setEconomicType(String economicType) {
        this.economicType = economicType == null ? null : economicType.trim();
    }

    /**
     * 获取出资方式
     *
     * @return investment_ways - 出资方式
     */
    public String getInvestmentWays() {
        return investmentWays;
    }

    /**
     * 设置出资方式
     *
     * @param investmentWays 出资方式
     */
    public void setInvestmentWays(String investmentWays) {
        this.investmentWays = investmentWays == null ? null : investmentWays.trim();
    }

    /**
     * 获取公司税号
     *
     * @return company_number - 公司税号
     */
    public String getCompanyNumber() {
        return companyNumber;
    }

    /**
     * 设置公司税号
     *
     * @param companyNumber 公司税号
     */
    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber == null ? null : companyNumber.trim();
    }

    /**
     * 获取是否上市（1：是；2：否）
     *
     * @return is_listing - 是否上市（1：是；2：否）
     */
    public Byte getIsListing() {
        return isListing;
    }

    /**
     * 设置是否上市（1：是；2：否）
     *
     * @param isListing 是否上市（1：是；2：否）
     */
    public void setIsListing(Byte isListing) {
        this.isListing = isListing;
    }

    /**
     * 获取是否跨国（1：是；2：否）
     *
     * @return is_transnational - 是否跨国（1：是；2：否）
     */
    public Byte getIsTransnational() {
        return isTransnational;
    }

    /**
     * 设置是否跨国（1：是；2：否）
     *
     * @param isTransnational 是否跨国（1：是；2：否）
     */
    public void setIsTransnational(Byte isTransnational) {
        this.isTransnational = isTransnational;
    }

    /**
     * 获取是否集团客户（1：是；2：否）
     *
     * @return is_bloc - 是否集团客户（1：是；2：否）
     */
    public Byte getIsBloc() {
        return isBloc;
    }

    /**
     * 设置是否集团客户（1：是；2：否）
     *
     * @param isBloc 是否集团客户（1：是；2：否）
     */
    public void setIsBloc(Byte isBloc) {
        this.isBloc = isBloc;
    }

    /**
     * 获取是否总部（1：是；2：否）
     *
     * @return is_headquarters - 是否总部（1：是；2：否）
     */
    public Byte getIsHeadquarters() {
        return isHeadquarters;
    }

    /**
     * 设置是否总部（1：是；2：否）
     *
     * @param isHeadquarters 是否总部（1：是；2：否）
     */
    public void setIsHeadquarters(Byte isHeadquarters) {
        this.isHeadquarters = isHeadquarters;
    }

    /**
     * 获取公司网址
     *
     * @return company_website - 公司网址
     */
    public String getCompanyWebsite() {
        return companyWebsite;
    }

    /**
     * 设置公司网址
     *
     * @param companyWebsite 公司网址
     */
    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite == null ? null : companyWebsite.trim();
    }

    /**
     * 获取客户简介
     *
     * @return customer_intro - 客户简介
     */
    public String getCustomerIntro() {
        return customerIntro;
    }

    /**
     * 设置客户简介
     *
     * @param customerIntro 客户简介
     */
    public void setCustomerIntro(String customerIntro) {
        this.customerIntro = customerIntro == null ? null : customerIntro.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", extendId=").append(extendId);
        sb.append(", userId=").append(userId);
        sb.append(", businessLicencePicSrc=").append(businessLicencePicSrc);
        sb.append(", taxRegistrationCertificatePicSrc=").append(taxRegistrationCertificatePicSrc);
        sb.append(", organizationPicSrc=").append(organizationPicSrc);
        sb.append(", companyName=").append(companyName);
        sb.append(", contactEmail=").append(contactEmail);
        sb.append(", artificialPersonName=").append(artificialPersonName);
        sb.append(", contactPhone=").append(contactPhone);
        sb.append(", contactAddress=").append(contactAddress);
        sb.append(", accountState=").append(accountState);
        sb.append(", createDt=").append(createDt);
        sb.append(", deleted=").append(deleted);
        sb.append(", chineseName=").append(chineseName);
        sb.append(", chineseShortName=").append(chineseShortName);
        sb.append(", englishName=").append(englishName);
        sb.append(", englishShortName=").append(englishShortName);
        sb.append(", customerType=").append(customerType);
        sb.append(", country=").append(country);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", area=").append(area);
        sb.append(", addedArea=").append(addedArea);
        sb.append(", mainTrade=").append(mainTrade);
        sb.append(", firstTrade=").append(firstTrade);
        sb.append(", secondTrade=").append(secondTrade);
        sb.append(", officeAddress=").append(officeAddress);
        sb.append(", officePostcode=").append(officePostcode);
        sb.append(", officeTel=").append(officeTel);
        sb.append(", officeEmail=").append(officeEmail);
        sb.append(", registerName=").append(registerName);
        sb.append(", registerFund=").append(registerFund);
        sb.append(", registerAddress=").append(registerAddress);
        sb.append(", registerPostcode=").append(registerPostcode);
        sb.append(", registerDate=").append(registerDate);
        sb.append(", customerSize=").append(customerSize);
        sb.append(", customerNumber=").append(customerNumber);
        sb.append(", economicType=").append(economicType);
        sb.append(", investmentWays=").append(investmentWays);
        sb.append(", companyNumber=").append(companyNumber);
        sb.append(", isListing=").append(isListing);
        sb.append(", isTransnational=").append(isTransnational);
        sb.append(", isBloc=").append(isBloc);
        sb.append(", isHeadquarters=").append(isHeadquarters);
        sb.append(", companyWebsite=").append(companyWebsite);
        sb.append(", customerIntro=").append(customerIntro);
        sb.append("]");
        return sb.toString();
    }
}