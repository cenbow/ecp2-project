package com.ecp.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.ecp.common.util.FileUploadUtil;
import com.ecp.dao.UserMapper;
import com.ecp.entity.User;
import com.ecp.service.ICustomerService;
import com.ecp.service.excelutil.ReadExcel;

import tk.mybatis.mapper.entity.Example;

/**
 * 客户管理service实现
 * @author srd
 *
 */
@Service
public class CustomerServiceImpl extends AbstractBaseService<User, Long> implements ICustomerService {
	
	private UserMapper customerMapper;
	
	public void setCustomerMapper(UserMapper customerMapper) {
		this.customerMapper = customerMapper;
		this.setMapper(customerMapper);
	}

	@Override
	public List<User> getCustomerPageInfo(Long loginId) {
		Example example=new Example(User.class);
		example.createCriteria().andEqualTo("loginId", loginId);
		example.setOrderByClause("customer_id DESC");
		return customerMapper.selectByExample(example);
	}
	
	/**
     * 上传EXCEL文件
     * @see com.jiayuhuxiao.service.HxLandlordService#uploadExcelFile(javax.servlet.http.HttpServletRequest)
     */
    public String uploadExcelFile(HttpServletRequest request){
    	// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得文件：
		MultipartFile excelFile = multipartRequest.getFile("excelFile");
		
		try {
			String uploadPath = FileUploadUtil.uploadFile(request, "invitation", excelFile);
			System.out.println("上传EXCEL文件成功   文件保存路径 : "+uploadPath);
			//log.info("上传EXCEL文件成功   文件保存路径 : "+uploadPath);
			return uploadPath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("上传EXCEL文件失败");
			//log.error("上传EXCEL文件失败");
			e.printStackTrace();
		}
		return null;
    }
    
    /** 
     * 解析上传的EXCEL文件
     * @see com.ecp.service.ICustomerService#parseUploadExcelFile(java.util.Map)
     */
    public Map<String, Object> parseUploadExcelFile(Map<String, Object> map){
    	Map<String, Object> respMap = new HashMap<String, Object>();
    	try {
    		respMap = ReadExcel.readExcel(map);
    		return respMap;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	respMap.put("result_code", "fail");
		respMap.put("result_err_msg", "解析excel文件失败");
		return respMap;
    }
    
    /**
     * 保存上传的excel内容到数据库
     * @see com.ecp.service.ICustomerService#saveExcelInfoToDB(java.lang.Long, java.lang.String)
     */
    @Transactional
    public Map<String, Object> saveExcelInfoToDB(Long loginId, String customerListListJson){
    	
    	Example example=new Example(User.class);
		example.createCriteria().andEqualTo("loginId", loginId);
		example.setOrderByClause("customer_id DESC");
		List<User> dbCustomerList = customerMapper.selectByExample(example);
    	
    	Map<String, Object> respMap = new HashMap<String, Object>();
    	
    	List<User> customerList = JSON.parseArray(customerListListJson, User.class); 
		int rows = 0;
		if(customerList!=null && customerList.size()>0){
			int length = customerList.size();
			for(int i=0; i<length; i++){
				
				User customer = customerList.get(i);
				/*customer.setLoginId(loginId);
				customer.setUpdateTime(new Date());*/
				customer.setUsername("test");
				
				boolean flag = false;//数据库中是否有此用户，有：true；无：false
				for(User dbCustomer : dbCustomerList){
					if(StringUtils.isNotBlank(dbCustomer.getLinkPhoneNum())){//如果数据库中的手机号相同的则更新
						if(StringUtils.isNotBlank(customer.getLinkPhoneNum()) && dbCustomer.getLinkPhoneNum().trim().equals(customer.getLinkPhoneNum().trim())){
							customer.setId(dbCustomer.getId());
							flag = true;//数据库中是否有此用户，有：true；无：false
						}
						break;
					}
				}
				if(flag){//如果数据库中的手机号相同的客户则更新
					rows = customerMapper.updateByPrimaryKeySelective(customer);
				}else{//如果没有手机号相同的客户则添加
					rows = customerMapper.insertSelective(customer);
				}
				if(rows<=0){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					respMap.put("result_code", "fail");
					respMap.put("result_err_msg", "保存或更新 "+customer.getNickname()+" 的客户信息异常");
					return respMap;
				}
			}
			
		}else{
			respMap.put("result_code", "fail");
			respMap.put("result_err_msg", "excel文件信息为空");
			return respMap;
		}
		respMap.put("result_code", "success");
		respMap.put("customerList", customerList);
		respMap.put("result_msg", "导入excel文件成功");
    	return respMap;
    }



	@Override
	public User getCustomerByLoginName(String loginName) {
		Example example=new Example(User.class);
		example.createCriteria().andEqualTo("loginName", loginName);
		if(customerMapper.selectByExample(example).size()>0){
			return customerMapper.selectByExample(example).get(0);
		}else{
			return null;
		}
	}

}
