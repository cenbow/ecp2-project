package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

public class Linkman {
    /**
     * 联系人表自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别(1:男;2:女)
     */
    private Byte sex;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 部门
     */
    private String department;

    /**
     * 部门描述
     */
    @Column(name = "department_desc")
    private String departmentDesc;

    /**
     * 职位
     */
    private String position;

    /**
     * 文化程度
     */
    @Column(name = "edu_level")
    private String eduLevel;

    /**
     * 关系程度
     */
    @Column(name = "rel_level")
    private String relLevel;

    /**
     * 态度
     */
    private String attitude;

    /**
     * 毕业学校
     */
    private String schooltag;

    /**
     * 工作地址
     */
    @Column(name = "work_address")
    private String workAddress;

    /**
     * 工作邮编
     */
    @Column(name = "work_postcode")
    private String workPostcode;

    /**
     * 工作电话
     */
    @Column(name = "work_tel")
    private String workTel;

    /**
     * 工作传真
     */
    @Column(name = "work_fax")
    private String workFax;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 家庭邮编
     */
    private String postcode;

    /**
     * 家庭电话
     */
    private String tel;

    /**
     * 移动电话
     */
    private String mobile;

    /**
     * 倾向品牌
     */
    private String tendency;

    /**
     * QQ或微信
     */
    private String qqwechat;

    /**
     * 订单ID
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
     * 获取联系人表自增ID
     *
     * @return id - 联系人表自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置联系人表自增ID
     *
     * @param id 联系人表自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取性别(1:男;2:女)
     *
     * @return sex - 性别(1:男;2:女)
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * 设置性别(1:男;2:女)
     *
     * @param sex 性别(1:男;2:女)
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * 获取出生日期
     *
     * @return birthday - 出生日期
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置出生日期
     *
     * @param birthday 出生日期
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取部门
     *
     * @return department - 部门
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 设置部门
     *
     * @param department 部门
     */
    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    /**
     * 获取部门描述
     *
     * @return department_desc - 部门描述
     */
    public String getDepartmentDesc() {
        return departmentDesc;
    }

    /**
     * 设置部门描述
     *
     * @param departmentDesc 部门描述
     */
    public void setDepartmentDesc(String departmentDesc) {
        this.departmentDesc = departmentDesc == null ? null : departmentDesc.trim();
    }

    /**
     * 获取职位
     *
     * @return position - 职位
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置职位
     *
     * @param position 职位
     */
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    /**
     * 获取文化程度
     *
     * @return edu_level - 文化程度
     */
    public String getEduLevel() {
        return eduLevel;
    }

    /**
     * 设置文化程度
     *
     * @param eduLevel 文化程度
     */
    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel == null ? null : eduLevel.trim();
    }

    /**
     * 获取关系程度
     *
     * @return rel_level - 关系程度
     */
    public String getRelLevel() {
        return relLevel;
    }

    /**
     * 设置关系程度
     *
     * @param relLevel 关系程度
     */
    public void setRelLevel(String relLevel) {
        this.relLevel = relLevel == null ? null : relLevel.trim();
    }

    /**
     * 获取态度
     *
     * @return attitude - 态度
     */
    public String getAttitude() {
        return attitude;
    }

    /**
     * 设置态度
     *
     * @param attitude 态度
     */
    public void setAttitude(String attitude) {
        this.attitude = attitude == null ? null : attitude.trim();
    }

    /**
     * 获取毕业学校
     *
     * @return schooltag - 毕业学校
     */
    public String getSchooltag() {
        return schooltag;
    }

    /**
     * 设置毕业学校
     *
     * @param schooltag 毕业学校
     */
    public void setSchooltag(String schooltag) {
        this.schooltag = schooltag == null ? null : schooltag.trim();
    }

    /**
     * 获取工作地址
     *
     * @return work_address - 工作地址
     */
    public String getWorkAddress() {
        return workAddress;
    }

    /**
     * 设置工作地址
     *
     * @param workAddress 工作地址
     */
    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress == null ? null : workAddress.trim();
    }

    /**
     * 获取工作邮编
     *
     * @return work_postcode - 工作邮编
     */
    public String getWorkPostcode() {
        return workPostcode;
    }

    /**
     * 设置工作邮编
     *
     * @param workPostcode 工作邮编
     */
    public void setWorkPostcode(String workPostcode) {
        this.workPostcode = workPostcode == null ? null : workPostcode.trim();
    }

    /**
     * 获取工作电话
     *
     * @return work_tel - 工作电话
     */
    public String getWorkTel() {
        return workTel;
    }

    /**
     * 设置工作电话
     *
     * @param workTel 工作电话
     */
    public void setWorkTel(String workTel) {
        this.workTel = workTel == null ? null : workTel.trim();
    }

    /**
     * 获取工作传真
     *
     * @return work_fax - 工作传真
     */
    public String getWorkFax() {
        return workFax;
    }

    /**
     * 设置工作传真
     *
     * @param workFax 工作传真
     */
    public void setWorkFax(String workFax) {
        this.workFax = workFax == null ? null : workFax.trim();
    }

    /**
     * 获取电子邮箱
     *
     * @return email - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取家庭住址
     *
     * @return address - 家庭住址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置家庭住址
     *
     * @param address 家庭住址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取家庭邮编
     *
     * @return postcode - 家庭邮编
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * 设置家庭邮编
     *
     * @param postcode 家庭邮编
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    /**
     * 获取家庭电话
     *
     * @return tel - 家庭电话
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置家庭电话
     *
     * @param tel 家庭电话
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * 获取移动电话
     *
     * @return mobile - 移动电话
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置移动电话
     *
     * @param mobile 移动电话
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取倾向品牌
     *
     * @return tendency - 倾向品牌
     */
    public String getTendency() {
        return tendency;
    }

    /**
     * 设置倾向品牌
     *
     * @param tendency 倾向品牌
     */
    public void setTendency(String tendency) {
        this.tendency = tendency == null ? null : tendency.trim();
    }

    /**
     * 获取QQ或微信
     *
     * @return qqwechat - QQ或微信
     */
    public String getQqwechat() {
        return qqwechat;
    }

    /**
     * 设置QQ或微信
     *
     * @param qqwechat QQ或微信
     */
    public void setQqwechat(String qqwechat) {
        this.qqwechat = qqwechat == null ? null : qqwechat.trim();
    }

    /**
     * 获取订单ID
     *
     * @return order_id - 订单ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     *
     * @param orderId 订单ID
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
        sb.append(", sex=").append(sex);
        sb.append(", birthday=").append(birthday);
        sb.append(", department=").append(department);
        sb.append(", departmentDesc=").append(departmentDesc);
        sb.append(", position=").append(position);
        sb.append(", eduLevel=").append(eduLevel);
        sb.append(", relLevel=").append(relLevel);
        sb.append(", attitude=").append(attitude);
        sb.append(", schooltag=").append(schooltag);
        sb.append(", workAddress=").append(workAddress);
        sb.append(", workPostcode=").append(workPostcode);
        sb.append(", workTel=").append(workTel);
        sb.append(", workFax=").append(workFax);
        sb.append(", email=").append(email);
        sb.append(", address=").append(address);
        sb.append(", postcode=").append(postcode);
        sb.append(", tel=").append(tel);
        sb.append(", mobile=").append(mobile);
        sb.append(", tendency=").append(tendency);
        sb.append(", qqwechat=").append(qqwechat);
        sb.append(", orderId=").append(orderId);
        sb.append(", deleted=").append(deleted);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}