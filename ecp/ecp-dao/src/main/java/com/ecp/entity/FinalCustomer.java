package com.ecp.entity;

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
        sb.append(", city=").append(city);
        sb.append(", area=").append(area);
        sb.append(", addedArea=").append(addedArea);
        sb.append(", paymentAddress=").append(paymentAddress);
        sb.append(", deliveryAddress=").append(deliveryAddress);
        sb.append(", installAddress=").append(installAddress);
        sb.append(", linkmanName=").append(linkmanName);
        sb.append(", linkmanPhone=").append(linkmanPhone);
        sb.append("]");
        return sb.toString();
    }
}