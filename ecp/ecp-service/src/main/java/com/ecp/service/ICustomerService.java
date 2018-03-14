package com.ecp.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ecp.entity.User;

/**
 * 客户管理service接口
 * @author srd
 *
 */
public interface ICustomerService extends IBaseService<User, Long>{
	
	/**
	 * @param loginId 登录用户ID
	 * @return
	 * 根据登录用户ID分页查询其下的客户列表
	 */
	public List<User> getCustomerPageInfo(Long loginId);
	
	/**
	 * 上传EXCEL文件
	 * @param request
	 * @return
	 */
	public String uploadExcelFile(HttpServletRequest request);
	
	/**
	 * 解析上传的EXCEL文件
	 * @param map
	 * @return
	 */
	public Map<String, Object> parseUploadExcelFile(Map<String, Object> map);
	
	/**
	 * 保存上传的excel内容到数据库
	 * @param loginId
	 * @param customerListListJson
	 * @return
	 */
	public Map<String, Object> saveExcelInfoToDB(Long loginId, String customerListListJson);
	
	/**
	 * @param loginName
	 * @return
	 * 通过loginName查询客户资料   登录名具有是唯一性
	 */
	public User getCustomerByLoginName(String loginName);
}
