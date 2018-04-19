package com.ecp.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;

@Table(name = "check_cycle")
public class CheckCycle {
    /**
     * 考核周期表自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 考核周期名
     */
    @Column(name = "cycle_name")
    private String cycleName;

    /**
     * 年度名称
     */
    @Column(name = "year_name")
    private String yearName;

    /**
     * 时间段计法（对于周期的定义可以采用三种计时方式：1：day；2：month；3：quarter；4：year）
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
     * 父ID
     */
    private Long pid;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 获取考核周期表自增ID
     *
     * @return id - 考核周期表自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置考核周期表自增ID
     *
     * @param id 考核周期表自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取考核周期名
     *
     * @return cycle_name - 考核周期名
     */
    public String getCycleName() {
        return cycleName;
    }

    /**
     * 设置考核周期名
     *
     * @param cycleName 考核周期名
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
     * 获取时间段计法（对于周期的定义可以采用三种计时方式：1：day；2：month；3：quarter；4：year）
     *
     * @return cal_type - 时间段计法（对于周期的定义可以采用三种计时方式：1：day；2：month；3：quarter；4：year）
     */
    public Byte getCalType() {
        return calType;
    }

    /**
     * 设置时间段计法（对于周期的定义可以采用三种计时方式：1：day；2：month；3：quarter；4：year）
     *
     * @param calType 时间段计法（对于周期的定义可以采用三种计时方式：1：day；2：month；3：quarter；4：year）
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
    
    public String getStartDateStr(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(startDate);
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
    
    public String getEndDateStr(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(endDate);
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
     * 获取父ID
     *
     * @return pid - 父ID
     */
    public Long getPid() {
        return pid;
    }

    /**
     * 设置父ID
     *
     * @param pid 父ID
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
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