package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "log_item_status")
public class LogItemStatus {
    /**
     * 日志表自增ID
     */
    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    /**
     * 产品ID
     */
    @Column(name = "item_id")
    private Long itemId;

    /**
     * 产品名称
     */
    @Column(name = "item_name")
    private String itemName;

    /**
     * 时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作状态（产品上架操作/下架操作）
     */
    @Column(name = "operation_status")
    private Byte operationStatus;

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
     * 获取产品ID
     *
     * @return item_id - 产品ID
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * 设置产品ID
     *
     * @param itemId 产品ID
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取产品名称
     *
     * @return item_name - 产品名称
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 设置产品名称
     *
     * @param itemName 产品名称
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
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
     * 获取操作状态（产品上架操作/下架操作）
     *
     * @return operation_status - 操作状态（产品上架操作/下架操作）
     */
    public Byte getOperationStatus() {
        return operationStatus;
    }

    /**
     * 设置操作状态（产品上架操作/下架操作）
     *
     * @param operationStatus 操作状态（产品上架操作/下架操作）
     */
    public void setOperationStatus(Byte operationStatus) {
        this.operationStatus = operationStatus;
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
        sb.append(", itemId=").append(itemId);
        sb.append(", itemName=").append(itemName);
        sb.append(", createTime=").append(createTime);
        sb.append(", operationStatus=").append(operationStatus);
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append("]");
        return sb.toString();
    }
}