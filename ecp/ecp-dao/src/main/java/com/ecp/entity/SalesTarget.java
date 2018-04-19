package com.ecp.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sales_target")
public class SalesTarget {
    /**
     * 销售指标表自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 指标比例
     */
    @Column(name = "target_rate")
    private String targetRate;

    /**
     * 指标金额
     */
    @Column(name = "target_amount")
    private BigDecimal targetAmount;

    /**
     * 考核周期条目ID
     */
    @Column(name = "check_cycle_id")
    private Long checkCycleId;

    /**
     * 周期名
     */
    @Column(name = "cycle_name")
    private String cycleName;

    /**
     * 年度名称
     */
    @Column(name = "year_name")
    private String yearName;

    /**
     * 时间段计法（对于周期的定义可以采用三种计时方式：1：day；2：month；3：year）
     */
    @Column(name = "cal_type")
    private Byte calType;

    /**
     * 开始时间
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 结束时间
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 考核周期父ID
     */
    private Long pid;

    /**
     * 考核周期排序
     */
    private Integer sort;

    /**
     * 获取销售指标表自增ID
     *
     * @return id - 销售指标表自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置销售指标表自增ID
     *
     * @param id 销售指标表自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取角色
     *
     * @return role_id - 角色
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色
     *
     * @param roleId 角色
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取指标比例
     *
     * @return target_rate - 指标比例
     */
    public String getTargetRate() {
        return targetRate;
    }

    /**
     * 设置指标比例
     *
     * @param targetRate 指标比例
     */
    public void setTargetRate(String targetRate) {
        this.targetRate = targetRate == null ? null : targetRate.trim();
    }

    /**
     * 获取指标金额
     *
     * @return target_amount - 指标金额
     */
    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    /**
     * 设置指标金额
     *
     * @param targetAmount 指标金额
     */
    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    /**
     * 获取考核周期条目ID
     *
     * @return check_cycle_id - 考核周期条目ID
     */
    public Long getCheckCycleId() {
        return checkCycleId;
    }

    /**
     * 设置考核周期条目ID
     *
     * @param checkCycleId 考核周期条目ID
     */
    public void setCheckCycleId(Long checkCycleId) {
        this.checkCycleId = checkCycleId;
    }

    /**
     * 获取周期名
     *
     * @return cycle_name - 周期名
     */
    public String getCycleName() {
        return cycleName;
    }

    /**
     * 设置周期名
     *
     * @param cycleName 周期名
     */
    public void setCycleName(String cycleName) {
        this.cycleName = cycleName == null ? null : cycleName.trim();
    }

    /**
     * 获取年度名称
     *
     * @return year_name - 年度名称
     */
    public String getYearName() {
        return yearName;
    }

    /**
     * 设置年度名称
     *
     * @param yearName 年度名称
     */
    public void setYearName(String yearName) {
        this.yearName = yearName == null ? null : yearName.trim();
    }

    /**
     * 获取时间段计法（对于周期的定义可以采用三种计时方式：1：day；2：month；3：year）
     *
     * @return cal_type - 时间段计法（对于周期的定义可以采用三种计时方式：1：day；2：month；3：year）
     */
    public Byte getCalType() {
        return calType;
    }

    /**
     * 设置时间段计法（对于周期的定义可以采用三种计时方式：1：day；2：month；3：year）
     *
     * @param calType 时间段计法（对于周期的定义可以采用三种计时方式：1：day；2：month；3：year）
     */
    public void setCalType(Byte calType) {
        this.calType = calType;
    }

    /**
     * 获取开始时间
     *
     * @return start_date - 开始时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置开始时间
     *
     * @param startDate 开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取结束时间
     *
     * @return end_date - 结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置结束时间
     *
     * @param endDate 结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取考核周期父ID
     *
     * @return pid - 考核周期父ID
     */
    public Long getPid() {
        return pid;
    }

    /**
     * 设置考核周期父ID
     *
     * @param pid 考核周期父ID
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * 获取考核周期排序
     *
     * @return sort - 考核周期排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置考核周期排序
     *
     * @param sort 考核周期排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", roleId=").append(roleId);
        sb.append(", targetRate=").append(targetRate);
        sb.append(", targetAmount=").append(targetAmount);
        sb.append(", checkCycleId=").append(checkCycleId);
        sb.append(", cycleName=").append(cycleName);
        sb.append(", yearName=").append(yearName);
        sb.append(", calType=").append(calType);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", pid=").append(pid);
        sb.append(", sort=").append(sort);
        sb.append("]");
        return sb.toString();
    }
}