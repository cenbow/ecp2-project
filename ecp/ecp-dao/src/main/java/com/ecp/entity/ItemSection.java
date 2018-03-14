package com.ecp.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "item_section")
public class ItemSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_price_end")
    private BigDecimal basePriceEnd;

    @Column(name = "base_price_start")
    private BigDecimal basePriceStart;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "iterval_type")
    private String itervalType;

    private Long itemid;

    @Column(name = "market_price_end")
    private BigDecimal marketPriceEnd;

    @Column(name = "market_price_start")
    private BigDecimal marketPriceStart;

    @Column(name = "quoted_price_end")
    private BigDecimal quotedPriceEnd;

    @Column(name = "auoted_price_start")
    private BigDecimal auotedPriceStart;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "volume_end")
    private BigDecimal volumeEnd;

    @Column(name = "volume_start")
    private BigDecimal volumeStart;

    @Column(name = "weight_end")
    private BigDecimal weightEnd;

    @Column(name = "weight_start")
    private BigDecimal weightStart;

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
     * @return base_price_end
     */
    public BigDecimal getBasePriceEnd() {
        return basePriceEnd;
    }

    /**
     * @param basePriceEnd
     */
    public void setBasePriceEnd(BigDecimal basePriceEnd) {
        this.basePriceEnd = basePriceEnd;
    }

    /**
     * @return base_price_start
     */
    public BigDecimal getBasePriceStart() {
        return basePriceStart;
    }

    /**
     * @param basePriceStart
     */
    public void setBasePriceStart(BigDecimal basePriceStart) {
        this.basePriceStart = basePriceStart;
    }

    /**
     * @return create_by
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return iterval_type
     */
    public String getItervalType() {
        return itervalType;
    }

    /**
     * @param itervalType
     */
    public void setItervalType(String itervalType) {
        this.itervalType = itervalType == null ? null : itervalType.trim();
    }

    /**
     * @return itemid
     */
    public Long getItemid() {
        return itemid;
    }

    /**
     * @param itemid
     */
    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    /**
     * @return market_price_end
     */
    public BigDecimal getMarketPriceEnd() {
        return marketPriceEnd;
    }

    /**
     * @param marketPriceEnd
     */
    public void setMarketPriceEnd(BigDecimal marketPriceEnd) {
        this.marketPriceEnd = marketPriceEnd;
    }

    /**
     * @return market_price_start
     */
    public BigDecimal getMarketPriceStart() {
        return marketPriceStart;
    }

    /**
     * @param marketPriceStart
     */
    public void setMarketPriceStart(BigDecimal marketPriceStart) {
        this.marketPriceStart = marketPriceStart;
    }

    /**
     * @return quoted_price_end
     */
    public BigDecimal getQuotedPriceEnd() {
        return quotedPriceEnd;
    }

    /**
     * @param quotedPriceEnd
     */
    public void setQuotedPriceEnd(BigDecimal quotedPriceEnd) {
        this.quotedPriceEnd = quotedPriceEnd;
    }

    /**
     * @return auoted_price_start
     */
    public BigDecimal getAuotedPriceStart() {
        return auotedPriceStart;
    }

    /**
     * @param auotedPriceStart
     */
    public void setAuotedPriceStart(BigDecimal auotedPriceStart) {
        this.auotedPriceStart = auotedPriceStart;
    }

    /**
     * @return update_by
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * @return update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return volume_end
     */
    public BigDecimal getVolumeEnd() {
        return volumeEnd;
    }

    /**
     * @param volumeEnd
     */
    public void setVolumeEnd(BigDecimal volumeEnd) {
        this.volumeEnd = volumeEnd;
    }

    /**
     * @return volume_start
     */
    public BigDecimal getVolumeStart() {
        return volumeStart;
    }

    /**
     * @param volumeStart
     */
    public void setVolumeStart(BigDecimal volumeStart) {
        this.volumeStart = volumeStart;
    }

    /**
     * @return weight_end
     */
    public BigDecimal getWeightEnd() {
        return weightEnd;
    }

    /**
     * @param weightEnd
     */
    public void setWeightEnd(BigDecimal weightEnd) {
        this.weightEnd = weightEnd;
    }

    /**
     * @return weight_start
     */
    public BigDecimal getWeightStart() {
        return weightStart;
    }

    /**
     * @param weightStart
     */
    public void setWeightStart(BigDecimal weightStart) {
        this.weightStart = weightStart;
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
		return "ItemSection [id=" + id + ", basePriceEnd=" + basePriceEnd + ", basePriceStart=" + basePriceStart
				+ ", createBy=" + createBy + ", createDate=" + createDate + ", itervalType=" + itervalType + ", itemid="
				+ itemid + ", marketPriceEnd=" + marketPriceEnd + ", marketPriceStart=" + marketPriceStart
				+ ", quotedPriceEnd=" + quotedPriceEnd + ", auotedPriceStart=" + auotedPriceStart + ", updateBy="
				+ updateBy + ", updateDate=" + updateDate + ", volumeEnd=" + volumeEnd + ", volumeStart=" + volumeStart
				+ ", weightEnd=" + weightEnd + ", weightStart=" + weightStart + ", deleted=" + deleted + "]";
	}
}