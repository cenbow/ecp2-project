package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "final_customer")
public class FinalCustomer {
    /**
     * 最终客户表自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 客户电话
     */
    private String phone;

    /**
     * 所在国家
     */
    private String country;

    /**
     * 所在省份
     */
    private String province;

    /**
     * 省份编码
     */
    @Column(name = "province_code")
    private String provinceCode;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 城市编码
     */
    @Column(name = "city_code")
    private String cityCode;

    /**
     * 所在区县
     */
    private String county;

    /**
     * 县(区)编码
     */
    @Column(name = "county_code")
    private String countyCode;
    
    
    /**
     * 单位名称
     */
    @Column(name = "company_name")
    private String countyName;
    

    public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/**
     * 补充区县
     */
    @Column(name = "added_area")
    private String addedArea;

    /**
     * 付款地址
     */
    @Column(name = "payment_address")
    private String paymentAddress;

    /**
     * 送货地址
     */
    @Column(name = "delivery_address")
    private String deliveryAddress;

    /**
     * 装机地址
     */
    @Column(name = "install_address")
    private String installAddress;

    /**
     * 联系人
     */
    @Column(name = "linkman_name")
    private String linkmanName;

    /**
     * 联系电话
     */
    @Column(name = "linkman_phone")
    private String linkmanPhone;

    /**
     * 相关订单ID
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 是否删除（1-未删除，2-删除，默认1）
     */
    private Byte deleted;

    /**
     * 记录创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取最终客户表自增ID
     *
     * @return id - 最终客户表自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置最终客户表自增ID
     *
     * @param id 最终客户表自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取客户姓名
     *
     * @return name - 客户姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置客户姓名
     *
     * @param name 客户姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取客户电话
     *
     * @return phone - 客户电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置客户电话
     *
     * @param phone 客户电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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
     * 获取省份编码
     *
     * @return province_code - 省份编码
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * 设置省份编码
     *
     * @param provinceCode 省份编码
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
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
     * 获取城市编码
     *
     * @return city_code - 城市编码
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 设置城市编码
     *
     * @param cityCode 城市编码
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    /**
     * 获取所在区县
     *
     * @return county - 所在区县
     */
    public String getCounty() {
        return county;
    }

    /**
     * 设置所在区县
     *
     * @param county 所在区县
     */
    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    /**
     * 获取县(区)编码
     *
     * @return county_code - 县(区)编码
     */
    public String getCountyCode() {
        return countyCode;
    }

    /**
     * 设置县(区)编码
     *
     * @param countyCode 县(区)编码
     */
    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode == null ? null : countyCode.trim();
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
     * 获取付款地址
     *
     * @return payment_address - 付款地址
     */
    public String getPaymentAddress() {
        return paymentAddress;
    }

    /**
     * 设置付款地址
     *
     * @param paymentAddress 付款地址
     */
    public void setPaymentAddress(String paymentAddress) {
        this.paymentAddress = paymentAddress == null ? null : paymentAddress.trim();
    }

    /**
     * 获取送货地址
     *
     * @return delivery_address - 送货地址
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * 设置送货地址
     *
     * @param deliveryAddress 送货地址
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress == null ? null : deliveryAddress.trim();
    }

    /**
     * 获取装机地址
     *
     * @return install_address - 装机地址
     */
    public String getInstallAddress() {
        return installAddress;
    }

    /**
     * 设置装机地址
     *
     * @param installAddress 装机地址
     */
    public void setInstallAddress(String installAddress) {
        this.installAddress = installAddress == null ? null : installAddress.trim();
    }

    /**
     * 获取联系人
     *
     * @return linkman_name - 联系人
     */
    public String getLinkmanName() {
        return linkmanName;
    }

    /**
     * 设置联系人
     *
     * @param linkmanName 联系人
     */
    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName == null ? null : linkmanName.trim();
    }

    /**
     * 获取联系电话
     *
     * @return linkman_phone - 联系电话
     */
    public String getLinkmanPhone() {
        return linkmanPhone;
    }

    /**
     * 设置联系电话
     *
     * @param linkmanPhone 联系电话
     */
    public void setLinkmanPhone(String linkmanPhone) {
        this.linkmanPhone = linkmanPhone == null ? null : linkmanPhone.trim();
    }

    /**
     * 获取相关订单ID
     *
     * @return order_id - 相关订单ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置相关订单ID
     *
     * @param orderId 相关订单ID
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取是否删除（1-未删除，2-删除，默认1）
     *
     * @return deleted - 是否删除（1-未删除，2-删除，默认1）
     */
    public Byte getDeleted() {
        return deleted;
    }

    /**
     * 设置是否删除（1-未删除，2-删除，默认1）
     *
     * @param deleted 是否删除（1-未删除，2-删除，默认1）
     */
    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取记录创建时间
     *
     * @return create_time - 记录创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置记录创建时间
     *
     * @param createTime 记录创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append(", country=").append(country);
        sb.append(", province=").append(province);
        sb.append(", provinceCode=").append(provinceCode);
        sb.append(", city=").append(city);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", county=").append(county);
        sb.append(", countyCode=").append(countyCode);
        sb.append(", addedArea=").append(addedArea);
        sb.append(", paymentAddress=").append(paymentAddress);
        sb.append(", deliveryAddress=").append(deliveryAddress);
        sb.append(", installAddress=").append(installAddress);
        sb.append(", linkmanName=").append(linkmanName);
        sb.append(", linkmanPhone=").append(linkmanPhone);
        sb.append(", orderId=").append(orderId);
        sb.append(", deleted=").append(deleted);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}