package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_personal_info")
public class PersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String add1;

    private String add2;

    private String add3;

    private String address;

    private String birthday;

    private String blood;

    @Column(name = "create_dt")
    private Date createDt;

    @Column(name = "deleted_flag")
    private String deletedFlag;

    private String evaluate;

    private String hobby;

    @Column(name = "home_page")
    private String homePage;

    private String income;

    @Column(name = "last_upd_dt")
    private Date lastUpdDt;

    @Column(name = "nike_name")
    private String nikeName;

    private String origin;

    private String sex;

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
     * @return add1
     */
    public String getAdd1() {
        return add1;
    }

    /**
     * @param add1
     */
    public void setAdd1(String add1) {
        this.add1 = add1 == null ? null : add1.trim();
    }

    /**
     * @return add2
     */
    public String getAdd2() {
        return add2;
    }

    /**
     * @param add2
     */
    public void setAdd2(String add2) {
        this.add2 = add2 == null ? null : add2.trim();
    }

    /**
     * @return add3
     */
    public String getAdd3() {
        return add3;
    }

    /**
     * @param add3
     */
    public void setAdd3(String add3) {
        this.add3 = add3 == null ? null : add3.trim();
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * @return birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    /**
     * @return blood
     */
    public String getBlood() {
        return blood;
    }

    /**
     * @param blood
     */
    public void setBlood(String blood) {
        this.blood = blood == null ? null : blood.trim();
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
     * @return evaluate
     */
    public String getEvaluate() {
        return evaluate;
    }

    /**
     * @param evaluate
     */
    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate == null ? null : evaluate.trim();
    }

    /**
     * @return hobby
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * @param hobby
     */
    public void setHobby(String hobby) {
        this.hobby = hobby == null ? null : hobby.trim();
    }

    /**
     * @return home_page
     */
    public String getHomePage() {
        return homePage;
    }

    /**
     * @param homePage
     */
    public void setHomePage(String homePage) {
        this.homePage = homePage == null ? null : homePage.trim();
    }

    /**
     * @return income
     */
    public String getIncome() {
        return income;
    }

    /**
     * @param income
     */
    public void setIncome(String income) {
        this.income = income == null ? null : income.trim();
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
     * @return nike_name
     */
    public String getNikeName() {
        return nikeName;
    }

    /**
     * @param nikeName
     */
    public void setNikeName(String nikeName) {
        this.nikeName = nikeName == null ? null : nikeName.trim();
    }

    /**
     * @return origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin
     */
    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
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
		return "PersonalInfo [id=" + id + ", add1=" + add1 + ", add2=" + add2 + ", add3=" + add3 + ", address="
				+ address + ", birthday=" + birthday + ", blood=" + blood + ", createDt=" + createDt + ", deletedFlag="
				+ deletedFlag + ", evaluate=" + evaluate + ", hobby=" + hobby + ", homePage=" + homePage + ", income="
				+ income + ", lastUpdDt=" + lastUpdDt + ", nikeName=" + nikeName + ", origin=" + origin + ", sex=" + sex
				+ ", userId=" + userId + ", deleted=" + deleted + "]";
	}
	
}