package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "log_system_access")
public class LogSystemAccess {
    /**
     * 日志表自增ID
     */
    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    /**
     * 时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 动作（登录/登出）
     */
    @Column(name = "action_status")
    private Byte actionStatus;

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
     * 获取动作（登录/登出）
     *
     * @return action_status - 动作（登录/登出）
     */
    public Byte getActionStatus() {
        return actionStatus;
    }

    /**
     * 设置动作（登录/登出）
     *
     * @param actionStatus 动作（登录/登出）
     */
    public void setActionStatus(Byte actionStatus) {
        this.actionStatus = actionStatus;
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
        sb.append(", createTime=").append(createTime);
        sb.append(", actionStatus=").append(actionStatus);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append("]");
        return sb.toString();
    }
}