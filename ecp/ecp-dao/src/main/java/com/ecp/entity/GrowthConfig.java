package com.ecp.entity;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "user_growth_config")
public class GrowthConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "growth_value")
    private BigDecimal growthValue;

    private Long type;

    @Column(name = "user_level")
    private Integer userLevel;

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
     * @return growth_value
     */
    public BigDecimal getGrowthValue() {
        return growthValue;
    }

    /**
     * @param growthValue
     */
    public void setGrowthValue(BigDecimal growthValue) {
        this.growthValue = growthValue;
    }

    /**
     * @return type
     */
    public Long getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Long type) {
        this.type = type;
    }

    /**
     * @return user_level
     */
    public Integer getUserLevel() {
        return userLevel;
    }

    /**
     * @param userLevel
     */
    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
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
		return "GrowthConfig [id=" + id + ", growthValue=" + growthValue + ", type=" + type + ", userLevel=" + userLevel
				+ ", deleted=" + deleted + "]";
	}
	
}