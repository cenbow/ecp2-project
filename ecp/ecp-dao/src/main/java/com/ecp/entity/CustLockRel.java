package com.ecp.entity;

import javax.persistence.*;

@Table(name = "cust_lock_rel")
public class CustLockRel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户ID
     */
    @Column(name = "cust_id")
    private Long custId;

    /**
     * 所在区域
     */
    private String area;

    /**
     * 核心渠道
     */
    private String channel;

    /**
     * 主从标志，默认O/S
     */
    @Column(name = "master_slave_flag")
    private Byte masterSlaveFlag;

    /**
     * 产品类目ID
     */
    @Column(name = "item_cat_id")
    private Long itemCatId;

    /**
     * OS/IS/产品经理
     */
    @Column(name = "bind_user_id")
    private Long bindUserId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;
    
    
    /**
     * 是否删除
     */
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
     * 获取客户ID
     *
     * @return cust_id - 客户ID
     */
    public Long getCustId() {
        return custId;
    }

    /**
     * 设置客户ID
     *
     * @param custId 客户ID
     */
    public void setCustId(Long custId) {
        this.custId = custId;
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
     * 获取主从标志，默认O/S
     *
     * @return master_slave_flag - 主从标志，默认O/S
     */
    public Byte getMasterSlaveFlag() {
        return masterSlaveFlag;
    }

    /**
     * 设置主从标志，默认O/S
     *
     * @param masterSlaveFlag 主从标志，默认O/S
     */
    public void setMasterSlaveFlag(Byte masterSlaveFlag) {
        this.masterSlaveFlag = masterSlaveFlag;
    }

    /**
     * 获取产品类目ID
     *
     * @return item_cat_id - 产品类目ID
     */
    public Long getItemCatId() {
        return itemCatId;
    }

    /**
     * 设置产品类目ID
     *
     * @param itemCatId 产品类目ID
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
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", custId=").append(custId);
        sb.append(", area=").append(area);
        sb.append(", channel=").append(channel);
        sb.append(", masterSlaveFlag=").append(masterSlaveFlag);
        sb.append(", itemCatId=").append(itemCatId);
        sb.append(", bindUserId=").append(bindUserId);
        sb.append(", roleId=").append(roleId);
        sb.append("]");
        return sb.toString();
    }
}