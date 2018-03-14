package com.ecp.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

@Table(name = "item_sku")
public class Sku {
    @Id
    @Column(name = "sku_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skuId;

    private String ad;

    private String attributes;

    private Date created;

    @Column(name = "item_id")
    private Long itemId;

    private Date modified;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "sku_status")
    private Integer skuStatus;

    @Column(name = "sku_type")
    private Integer skuType;

    private BigDecimal volume;

    private BigDecimal weight;

    @Column(name = "sku_short_spec")
    private String skuShortSpec;//简单sku规格
    
    @Column(name = "sku_spec")
    private String skuSpec;//sku规格

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
     * @return ad
     */
    public String getAd() {
        return ad;
    }

    /**
     * @param ad
     */
    public void setAd(String ad) {
        this.ad = ad == null ? null : ad.trim();
    }

    /**
     * @return attributes
     */
    public String getAttributes() {
        return attributes;
    }

    /**
     * @param attributes
     */
    public void setAttributes(String attributes) {
        this.attributes = attributes == null ? null : attributes.trim();
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
     * @return product_id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * @return sku_status
     */
    public Integer getSkuStatus() {
        return skuStatus;
    }

    /**
     * @param skuStatus
     */
    public void setSkuStatus(Integer skuStatus) {
        this.skuStatus = skuStatus;
    }

    /**
     * @return sku_type
     */
    public Integer getSkuType() {
        return skuType;
    }

    /**
     * @param skuType
     */
    public void setSkuType(Integer skuType) {
        this.skuType = skuType;
    }

    /**
     * @return volume
     */
    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * @param volume
     */
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    /**
     * @return weight
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * @param weight
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * @return sku_short_spec
     */
    public String getSkuShortSpec() {
		return skuShortSpec;
	}
    
    /**
     * @param skuShortSpec
     */
	public void setSkuShortSpec(String skuShortSpec) {
		this.skuShortSpec = StringUtils.isBlank(skuShortSpec) ? null : skuShortSpec.trim();
	}

	/**
     * @return sku_spec
     */
    public String getSkuSpec() {
        return skuSpec;
    }

    /**
     * @param skuSpec
     */
    public void setSkuSpec(String skuSpec) {
        this.skuSpec = StringUtils.isBlank(skuSpec) ? null : skuSpec.trim();
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
		return "Sku [skuId=" + skuId + ", ad=" + ad + ", attributes=" + attributes + ", created=" + created
				+ ", itemId=" + itemId + ", modified=" + modified + ", productId=" + productId + ", skuStatus="
				+ skuStatus + ", skuType=" + skuType + ", volume=" + volume + ", weight=" + weight + ", skuSpec="
				+ skuSpec + ", deleted=" + deleted + "]";
	}
}