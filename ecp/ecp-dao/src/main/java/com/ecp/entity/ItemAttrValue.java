package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "item_attr_value_item")
public class ItemAttrValue {
    @Id
    @Column(name = "attr_id")
    private Long attrId;

    @Column(name = "attr_type")
    private Integer attrType;

    private Date created;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id")
    private Long itemId;

    private Date modified;

    @Column(name = "sort_number")
    private Integer sortNumber;

    private Integer status;

    @Column(name = "value_id")
    private Long valueId;

    /**
     * @return attr_id
     */
    public Long getAttrId() {
        return attrId;
    }

    /**
     * @param attrId
     */
    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    /**
     * @return attr_type
     */
    public Integer getAttrType() {
        return attrType;
    }

    /**
     * @param attrType
     */
    public void setAttrType(Integer attrType) {
        this.attrType = attrType;
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
     * @return item_id
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * @param itemId
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
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
     * @return sort_number
     */
    public Integer getSortNumber() {
        return sortNumber;
    }

    /**
     * @param sortNumber
     */
    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return value_id
     */
    public Long getValueId() {
        return valueId;
    }

    /**
     * @param valueId
     */
    public void setValueId(Long valueId) {
        this.valueId = valueId;
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
		return "ItemAttrValue [attrId=" + attrId + ", attrType=" + attrType + ", created=" + created + ", id=" + id
				+ ", itemId=" + itemId + ", modified=" + modified + ", sortNumber=" + sortNumber + ", status=" + status
				+ ", valueId=" + valueId + ", deleted=" + deleted + "]";
	}
}