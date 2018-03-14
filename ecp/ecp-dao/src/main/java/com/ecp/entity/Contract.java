package com.ecp.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contract_template_id")
    private Long contractTemplateId;

    @Column(name = "contract_no")
    private String contractNo;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "confirm_date_second_party")
    private Date confirmDateSecondParty;

    @Column(name = "confirm_user_second_party")
    private Long confirmUserSecondParty;

    @Column(name = "confirm_date_first_party")
    private Date confirmDateFirstParty;

    @Column(name = "confirm_user_first_party")
    private Long confirmUserFirstParty;

    @Column(name = "confirm_status")
    private Byte confirmStatus;

    @Column(name = "contract_status")
    private Byte contractStatus;

    @Column(name = "agent_id")
    private Long agentId;

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
     * @return contract_template_id
     */
    public Long getContractTemplateId() {
        return contractTemplateId;
    }

    /**
     * @param contractTemplateId
     */
    public void setContractTemplateId(Long contractTemplateId) {
        this.contractTemplateId = contractTemplateId;
    }

    /**
     * @return contract_no
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * @param contractNo
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    /**
     * @return order_no
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * @return order_id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
     * @return create_user
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * @return confirm_date_second_party
     */
    public Date getConfirmDateSecondParty() {
        return confirmDateSecondParty;
    }

    /**
     * @param confirmDateSecondParty
     */
    public void setConfirmDateSecondParty(Date confirmDateSecondParty) {
        this.confirmDateSecondParty = confirmDateSecondParty;
    }

    /**
     * @return confirm_user_second_party
     */
    public Long getConfirmUserSecondParty() {
        return confirmUserSecondParty;
    }

    /**
     * @param confirmUserSecondParty
     */
    public void setConfirmUserSecondParty(Long confirmUserSecondParty) {
        this.confirmUserSecondParty = confirmUserSecondParty;
    }

    /**
     * @return confirm_date_first_party
     */
    public Date getConfirmDateFirstParty() {
        return confirmDateFirstParty;
    }

    /**
     * @param confirmDateFirstParty
     */
    public void setConfirmDateFirstParty(Date confirmDateFirstParty) {
        this.confirmDateFirstParty = confirmDateFirstParty;
    }

    /**
     * @return confirm_user_first_party
     */
    public Long getConfirmUserFirstParty() {
        return confirmUserFirstParty;
    }

    /**
     * @param confirmUserFirstParty
     */
    public void setConfirmUserFirstParty(Long confirmUserFirstParty) {
        this.confirmUserFirstParty = confirmUserFirstParty;
    }

    /**
     * @return confirm_status
     */
    public Byte getConfirmStatus() {
        return confirmStatus;
    }

    /**
     * @param confirmStatus
     */
    public void setConfirmStatus(Byte confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    /**
     * @return contract_status
     */
    public Byte getContractStatus() {
        return contractStatus;
    }

    /**
     * @param contractStatus
     */
    public void setContractStatus(Byte contractStatus) {
        this.contractStatus = contractStatus;
    }

    /**
     * @return agent_id
     */
    public Long getAgentId() {
        return agentId;
    }

    /**
     * @param agentId
     */
    public void setAgentId(Long agentId) {
        this.agentId = agentId;
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
		return "Contract [id=" + id + ", contractTemplateId=" + contractTemplateId + ", contractNo=" + contractNo
				+ ", orderNo=" + orderNo + ", orderId=" + orderId + ", createDate=" + createDate + ", createUser="
				+ createUser + ", confirmDateSecondParty=" + confirmDateSecondParty + ", confirmUserSecondParty="
				+ confirmUserSecondParty + ", confirmDateFirstParty=" + confirmDateFirstParty
				+ ", confirmUserFirstParty=" + confirmUserFirstParty + ", confirmStatus=" + confirmStatus
				+ ", contractStatus=" + contractStatus + ", agentId=" + agentId + ", deleted=" + deleted + "]";
	}
	
}