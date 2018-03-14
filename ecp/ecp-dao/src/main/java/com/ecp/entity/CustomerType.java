package com.ecp.entity;

import javax.persistence.*;

@Table(name = "customer_type")
public class CustomerType {
    /**
     * 客户类型表自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 类型名称
     */
    @Column(name = "type_name")
    private String typeName;

    /**
     * 类型编码
     */
    @Column(name = "type_code")
    private String typeCode;

    /**
     * 备注
     */
    private String comment;

    /**
     * 获取客户类型表自增ID
     *
     * @return id - 客户类型表自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置客户类型表自增ID
     *
     * @param id 客户类型表自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取类型名称
     *
     * @return type_name - 类型名称
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 设置类型名称
     *
     * @param typeName 类型名称
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    /**
     * 获取类型编码
     *
     * @return type_code - 类型编码
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * 设置类型编码
     *
     * @param typeCode 类型编码
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    /**
     * 获取备注
     *
     * @return comment - 备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置备注
     *
     * @param comment 备注
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", typeName=").append(typeName);
        sb.append(", typeCode=").append(typeCode);
        sb.append(", comment=").append(comment);
        sb.append("]");
        return sb.toString();
    }
}