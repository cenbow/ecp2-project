package com.ecp.bean;

/**
 * 客户邀请函url
 * @author srd
 *
 */
public class RequestServerUrlBean {

	private Long urlId;
	/**
	 * 创建人id
	 */
	private Long loginId;
	/**
	 * 客户姓名
	 */
	private String customerName;
	/**
     * 客户邀请函url
     */
    private String invitationUrl;
    
    /**
     * 默认构造函数
     */
    public RequestServerUrlBean() {
		super();
	}
	/**
	 * 构造函数
	 * @param loginId
	 * @param customerName
	 * @param serverUrl
	 */
    public RequestServerUrlBean(Long urlId, Long loginId, String customerName, String invitationUrl) {
		super();
		this.urlId = urlId;
		this.loginId = loginId;
		this.customerName = customerName;
		this.invitationUrl = invitationUrl;
	}
    
	/**
	 * 获取当前时间毫秒数的long型数字+五位随机数
	 * @return
	 */
	public Long getUrlId() {
		return urlId;
	}
	
	public void setUrlId(Long urlId) {
		this.urlId = urlId;
	}
	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getInvitationUrl() {
		return invitationUrl;
	}

	public void setInvitationUrl(String invitationUrl) {
		this.invitationUrl = invitationUrl;
	}
	
	@Override
	public String toString() {
		return "RequestServerUrlBean [loginId=" + loginId + ", customerName=" + customerName + ", invitationUrl=" + invitationUrl + "]";
	}

}
