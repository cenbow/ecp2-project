package com.ecp.entity;

import javax.persistence.*;

@Table(name = "contract_attribute")
public class ContractAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attr_name")
    private String attrName;

    @Column(name = "template_id")
    private Long templateId;

    private String comment;

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
     * @return attr_name
     */
    public String getAttrName() {
        return attrName;
    }

    /**
     * @param attrName
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName == null ? null : attrName.trim();
    }

    /**
     * @return template_id
     */
    public Long getTemplateId() {
        return templateId;
    }

    /**
     * @param templateId
     */
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    /**
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
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
		return "ContractAttribute [id=" + id + ", attrName=" + attrName + ", templateId=" + templateId + ", comment="
				+ comment + ", deleted=" + deleted + "]";
	}
	
}