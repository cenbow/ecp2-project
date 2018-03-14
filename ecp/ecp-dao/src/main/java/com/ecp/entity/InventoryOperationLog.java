package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "trade_inventory_operation_log")
public class InventoryOperationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date created;

    private String description;

    @Column(name = "inventory_change")
    private Integer inventoryChange;

    @Column(name = "occupied_inventory_change")
    private Integer occupiedInventoryChange;

    @Column(name = "operate_key")
    private Long operateKey;

    @Column(name = "operate_type")
    private Byte operateType;

    private String operator;

    @Column(name = "sku_id")
    private Long skuId;

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
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return inventory_change
     */
    public Integer getInventoryChange() {
        return inventoryChange;
    }

    /**
     * @param inventoryChange
     */
    public void setInventoryChange(Integer inventoryChange) {
        this.inventoryChange = inventoryChange;
    }

    /**
     * @return occupied_inventory_change
     */
    public Integer getOccupiedInventoryChange() {
        return occupiedInventoryChange;
    }

    /**
     * @param occupiedInventoryChange
     */
    public void setOccupiedInventoryChange(Integer occupiedInventoryChange) {
        this.occupiedInventoryChange = occupiedInventoryChange;
    }

    /**
     * @return operate_key
     */
    public Long getOperateKey() {
        return operateKey;
    }

    /**
     * @param operateKey
     */
    public void setOperateKey(Long operateKey) {
        this.operateKey = operateKey;
    }

    /**
     * @return operate_type
     */
    public Byte getOperateType() {
        return operateType;
    }

    /**
     * @param operateType
     */
    public void setOperateType(Byte operateType) {
        this.operateType = operateType;
    }

    /**
     * @return operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
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
		return "InventoryOperationLog [id=" + id + ", created=" + created + ", description=" + description
				+ ", inventoryChange=" + inventoryChange + ", occupiedInventoryChange=" + occupiedInventoryChange
				+ ", operateKey=" + operateKey + ", operateType=" + operateType + ", operator=" + operator + ", skuId="
				+ skuId + ", deleted=" + deleted + "]";
	}
	
}