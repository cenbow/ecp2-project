package com.ecp.entity;

import javax.persistence.*;

@Table(name = "user_role")
public class UserRole {
    /**
     * 用户角色关系表自增ID
     */
    @Id
    @Column(name = "user_role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;

    /**
     * 引用用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 引用角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 获取用户角色关系表自增ID
     *
     * @return user_role_id - 用户角色关系表自增ID
     */
    public Long getUserRoleId() {
        return userRoleId;
    }

    /**
     * 设置用户角色关系表自增ID
     *
     * @param userRoleId 用户角色关系表自增ID
     */
    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    /**
     * 获取引用用户ID
     *
     * @return user_id - 引用用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置引用用户ID
     *
     * @param userId 引用用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取引用角色ID
     *
     * @return role_id - 引用角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置引用角色ID
     *
     * @param roleId 引用角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
		return "UserRole [userRoleId=" + userRoleId + ", userId=" + userId + ", roleId=" + roleId + ", deleted="
				+ deleted + "]";
	}
}