package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

public class Suppliers {
    /**
     * 客户表自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
     * 英文名称
     */
    @Column(name = "english_name")
    private String englishName;

    /**
     * 英文简称
     */
    @Column(name = "englise_short_name")
    private String engliseShortName;

    /**
     * 客户类别（1：签约客户；2：推广客户）
     */
    @Column(name = "customer_type")
    private Byte customerType;

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
    private String registerFund;

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
    private Integer customerNumber;

    /**
     * 经济类型
     */
    @Column(name = "economic_type")
    private Byte economicType;

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
     * 是否上市
     */
    @Column(name = "is_listing")
    private Byte isListing;

    /**
     * 是否跨国
     */
    @Column(name = "is_transnational")
    private Byte isTransnational;

    /**
     * 是否集团客户
     */
    @Column(name = "is_bloc")
    private Byte isBloc;

    /**
     * 是否总部
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
     * 获取客户表自增ID
     *
     * @return id - 客户表自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置客户表自增ID
     *
     * @param id 客户表自增ID
     */
    public void setId(Long id) {
        this.id = id;
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
     * 获取英文名称
     *
     * @return english_name - 英文名称
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * 设置英文名称
     *
     * @param englishName 英文名称
     */
    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    /**
     * 获取英文简称
     *
     * @return englise_short_name - 英文简称
     */
    public String getEngliseShortName() {
        return engliseShortName;
    }

    /**
     * 设置英文简称
     *
     * @param engliseShortName 英文简称
     */
    public void setEngliseShortName(String engliseShortName) {
        this.engliseShortName = engliseShortName == null ? null : engliseShortName.trim();
    }

    /**
     * 获取客户类别（1：签约客户；2：推广客户）
     *
     * @return customer_type - 客户类别（1：签约客户；2：推广客户）
     */
    public Byte getCustomerType() {
        return customerType;
    }

    /**
     * 设置客户类别（1：签约客户；2：推广客户）
     *
     * @param customerType 客户类别（1：签约客户；2：推广客户）
     */
    public void setCustomerType(Byte customerType) {
        this.customerType = customerType;
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
    public String getRegisterFund() {
        return registerFund;
    }

    /**
     * 设置注册资金（万元）
     *
     * @param registerFund 注册资金（万元）
     */
    public void setRegisterFund(String registerFund) {
        this.registerFund = registerFund == null ? null : registerFund.trim();
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
    public Integer getCustomerNumber() {
        return customerNumber;
    }

    /**
     * 设置客户人员
     *
     * @param customerNumber 客户人员
     */
    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    /**
     * 获取经济类型
     *
     * @return economic_type - 经济类型
     */
    public Byte getEconomicType() {
        return economicType;
    }

    /**
     * 设置经济类型
     *
     * @param economicType 经济类型
     */
    public void setEconomicType(Byte economicType) {
        this.economicType = economicType;
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
     * 获取是否上市
     *
     * @return is_listing - 是否上市
     */
    public Byte getIsListing() {
        return isListing;
    }

    /**
     * 设置是否上市
     *
     * @param isListing 是否上市
     */
    public void setIsListing(Byte isListing) {
        this.isListing = isListing;
    }

    /**
     * 获取是否跨国
     *
     * @return is_transnational - 是否跨国
     */
    public Byte getIsTransnational() {
        return isTransnational;
    }

    /**
     * 设置是否跨国
     *
     * @param isTransnational 是否跨国
     */
    public void setIsTransnational(Byte isTransnational) {
        this.isTransnational = isTransnational;
    }

    /**
     * 获取是否集团客户
     *
     * @return is_bloc - 是否集团客户
     */
    public Byte getIsBloc() {
        return isBloc;
    }

    /**
     * 设置是否集团客户
     *
     * @param isBloc 是否集团客户
     */
    public void setIsBloc(Byte isBloc) {
        this.isBloc = isBloc;
    }

    /**
     * 获取是否总部
     *
     * @return is_headquarters - 是否总部
     */
    public Byte getIsHeadquarters() {
        return isHeadquarters;
    }

    /**
     * 设置是否总部
     *
     * @param isHeadquarters 是否总部
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
        sb.append(", id=").append(id);
        sb.append(", chineseName=").append(chineseName);
        sb.append(", chineseShortName=").append(chineseShortName);
        sb.append(", englishName=").append(englishName);
        sb.append(", engliseShortName=").append(engliseShortName);
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