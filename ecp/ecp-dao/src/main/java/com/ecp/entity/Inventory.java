package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "trade_inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_user")
    private String createUser;

    private Date created;

    private Date modified;

    @Column(name = "occupied_inventory")
    private Integer occupiedInventory;

    @Column(name = "sku_id")
    private Long skuId;

    private Byte state;

    @Column(name = "total_inventory")
    private Integer totalInventory;

    @Column(name = "update_user")
    private String updateUser;

    private Byte yn;

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
     * @return create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * @return created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return modified
     */
    public Date getModified() {
        return modified;
    }

    /**
     * @param modified
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * @return occupied_inventory
     */
    public Integer getOccupiedInventory() {
        return occupiedInventory;
    }

    /**
     * @param occupiedInventory
     */
    public void setOccupiedInventory(Integer occupiedInventory) {
        this.occupiedInventory = occupiedInventory;
    }

    /**
     * @return sku_id
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * @param skuId
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * @return state
     */
    public Byte getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * @return total_inventory
     */
    public Integer getTotalInventory() {
        return totalInventory;
    }

    /**
     * @param totalInventory
     */
    public void setTotalInventory(Integer totalInventory) {
        this.totalInventory = totalInventory;
    }

    /**
     * @return update_user
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * @return yn
     */
    public Byte getYn() {
        return yn;
    }

    /**
     * @param yn
     */
    public void setYn(Byte yn) {
        this.yn = yn;
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
		return "Inventory [id=" + id + ", createUser=" + createUser + ", created=" + created + ", modified=" + modified
				+ ", occupiedInventory=" + occupiedInventory + ", skuId=" + skuId + ", state=" + state
				+ ", totalInventory=" + totalInventory + ", updateUser=" + updateUser + ", yn=" + yn + ", deleted="
				+ deleted + "]";
	}
	
}