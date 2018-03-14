package com.ecp.entity;

import javax.persistence.*;

@Table(name = "item_sales_volume")
public class SalesVolume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "sales_volume")
    private Long salesVolume;

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
     * @return sales_volume
     */
    public Long getSalesVolume() {
        return salesVolume;
    }

    /**
     * @param salesVolume
     */
    public void setSalesVolume(Long salesVolume) {
        this.salesVolume = salesVolume;
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
		return "SalesVolume [id=" + id + ", itemId=" + itemId + ", salesVolume=" + salesVolume + ", skuId=" + skuId
				+ ", deleted=" + deleted + "]";
	}
}