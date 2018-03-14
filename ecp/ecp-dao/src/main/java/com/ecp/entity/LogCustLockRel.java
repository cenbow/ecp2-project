package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "log_cust_lock_rel")
public class LogCustLockRel {
    /**
     * 日志表自增ID
     */
    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    /**
     * 客户
     */
    @Column(name = "cust_id")
    private Long custId;

    /**
     * 客户姓名
     */
    @Column(name = "cust_name")
    private String custName;

    /**
     * 所在区域
     */
    private String area;

    /**
     * 核心渠道
     */
    private String channel;

    /**
     * 主从标志
     */
    @Column(name = "master_slave_flag")
    private Byte masterSlaveFlag;

    /**
     * 产品类目
     */
    @Column(name = "item_cat_id")
    private Long itemCatId;

    /**
     * OS/IS/产品经理
     */
    @Column(name = "bind_user_id")
    private Long bindUserId;

    /**
     * 时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作人
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 操作人姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 获取日志表自增ID
     *
     * @return log_id - 日志表自增ID
     */
    public Long getLogId() {
        return logId;
    }

    /**
     * 设置日志表自增ID
     *
     * @param logId 日志表自增ID
     */
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    /**
     * 获取客户
     *
     * @return cust_id - 客户
     */
    public Long getCustId() {
        return custId;
    }

    /**
     * 设置客户
     *
     * @param custId 客户
     */
    public void setCustId(Long custId) {
        this.custId = custId;
    }

    /**
     * 获取客户姓名
     *
     * @return cust_name - 客户姓名
     */
    public String getCustName() {
        return custName;
    }

    /**
     * 设置客户姓名
     *
     * @param custName 客户姓名
     */
    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    /**
     * 获取所在区域
     *
     * @return area - 所在区域
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置所在区域
     *
     * @param area 所在区域
     */
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * 获取核心渠道
     *
     * @return channel - 核心渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置核心渠道
     *
     * @param channel 核心渠道
     */
    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    /**
     * 获取主从标志
     *
     * @return master_slave_flag - 主从标志
     */
    public Byte getMasterSlaveFlag() {
        return masterSlaveFlag;
    }

    /**
     * 设置主从标志
     *
     * @param masterSlaveFlag 主从标志
     */
    public void setMasterSlaveFlag(Byte masterSlaveFlag) {
        this.masterSlaveFlag = masterSlaveFlag;
    }

    /**
     * 获取产品类目
     *
     * @return item_cat_id - 产品类目
     */
    public Long getItemCatId() {
        return itemCatId;
    }

    /**
     * 设置产品类目
     *
     * @param itemCatId 产品类目
     */
    public void setItemCatId(Long itemCatId) {
        this.itemCatId = itemCatId;
    }

    /**
     * 获取OS/IS/产品经理
     *
     * @return bind_user_id - OS/IS/产品经理
     */
    public Long getBindUserId() {
        return bindUserId;
    }

    /**
     * 设置OS/IS/产品经理
     *
     * @param bindUserId OS/IS/产品经理
     */
    public void setBindUserId(Long bindUserId) {
        this.bindUserId = bindUserId;
    }

    /**
     * 获取时间
     *
     * @return create_time - 时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置时间
     *
     * @param createTime 时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取操作人
     *
     * @return user_id - 操作人
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置操作人
     *
     * @param userId 操作人
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取操作人姓名
     *
     * @return user_name - 操作人姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置操作人姓名
     *
     * @param userName 操作人姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", custId=").append(custId);
        sb.append(", custName=").append(custName);
        sb.append(", area=").append(area);
        sb.append(", channel=").append(channel);
        sb.append(", masterSlaveFlag=").append(masterSlaveFlag);
        sb.append(", itemCatId=").append(itemCatId);
        sb.append(", bindUserId=").append(bindUserId);
        sb.append(", createTime=").append(createTime);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append("]");
        return sb.toString();
    }
}