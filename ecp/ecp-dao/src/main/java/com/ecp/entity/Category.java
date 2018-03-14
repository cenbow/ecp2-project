package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "item_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @Column(name = "c_name")
    private String cName;

    private Date created;

    @Column(name = "has_leaf")
    private Integer hasLeaf;//是否叶子节点：1=叶子节点  2=非叶子节点

    @Column(name = "home_show")
    private Integer homeShow;

    private Integer lev;

    private Date modified;

    @Column(name = "parent_cid")
    private Long parentCid;

    @Column(name = "sort_number")
    private Integer sortNumber;

    private Integer status;
    
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
     * @return cid
     */
    public Long getCid() {
        return cid;
    }

    /**
     * @param cid
     */
    public void setCid(Long cid) {
        this.cid = cid;
    }

    /**
     * @return c_name
     */
    public String getcName() {
        return cName;
    }

    /**
     * @param cName
     */
    public void setcName(String cName) {
        this.cName = cName == null ? null : cName.trim();
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
     * 获取是否叶子节点：1=叶子节点  2=非叶子节点
     * @return has_leaf
     */
    public Integer getHasLeaf() {
        return hasLeaf;
    }

    /**
     * 设置是否叶子节点：1=叶子节点  2=非叶子节点
     * @param hasLeaf
     */
    public void setHasLeaf(Integer hasLeaf) {
        this.hasLeaf = hasLeaf;
    }

    /**
     * @return home_show
     */
    public Integer getHomeShow() {
        return homeShow;
    }

    /**
     * @param homeShow
     */
    public void setHomeShow(Integer homeShow) {
        this.homeShow = homeShow;
    }

    /**
     * @return lev
     */
    public Integer getLev() {
        return lev;
    }

    /**
     * @param lev
     */
    public void setLev(Integer lev) {
        this.lev = lev;
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
     * @return parent_cid
     */
    public Long getParentCid() {
        return parentCid;
    }

    /**
     * @param parentCid
     */
    public void setParentCid(Long parentCid) {
        this.parentCid = parentCid;
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

	@Override
	public String toString() {
		return "Category [cid=" + cid + ", cName=" + cName + ", created=" + created + ", hasLeaf=" + hasLeaf
				+ ", homeShow=" + homeShow + ", lev=" + lev + ", modified=" + modified + ", parentCid=" + parentCid
				+ ", sortNumber=" + sortNumber + ", status=" + status + ", deleted=" + deleted + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cName == null) ? 0 : cName.hashCode());
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((hasLeaf == null) ? 0 : hasLeaf.hashCode());
		result = prime * result + ((homeShow == null) ? 0 : homeShow.hashCode());
		result = prime * result + ((lev == null) ? 0 : lev.hashCode());
		result = prime * result + ((modified == null) ? 0 : modified.hashCode());
		result = prime * result + ((parentCid == null) ? 0 : parentCid.hashCode());
		result = prime * result + ((sortNumber == null) ? 0 : sortNumber.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((status == null) ? 0 : deleted.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (cName == null) {
			if (other.cName != null)
				return false;
		} else if (!cName.equals(other.cName))
			return false;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (hasLeaf == null) {
			if (other.hasLeaf != null)
				return false;
		} else if (!hasLeaf.equals(other.hasLeaf))
			return false;
		if (homeShow == null) {
			if (other.homeShow != null)
				return false;
		} else if (!homeShow.equals(other.homeShow))
			return false;
		if (lev == null) {
			if (other.lev != null)
				return false;
		} else if (!lev.equals(other.lev))
			return false;
		if (modified == null) {
			if (other.modified != null)
				return false;
		} else if (!modified.equals(other.modified))
			return false;
		if (parentCid == null) {
			if (other.parentCid != null)
				return false;
		} else if (!parentCid.equals(other.parentCid))
			return false;
		if (sortNumber == null) {
			if (other.sortNumber != null)
				return false;
		} else if (!sortNumber.equals(other.sortNumber))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		}else if (!deleted.equals(other.deleted))
			return false;
		return true;
	}
}