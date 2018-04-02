package com.ecp.service.impl.front;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ecp.dao.ContractMapper;
import com.ecp.entity.Contract;
import com.ecp.entity.User;
import com.ecp.service.front.IContractService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service
public class ContractServiceImpl extends AbstractBaseService<Contract, Long> implements IContractService {

	private ContractMapper contractMapper;

	/**
	 * @param  the mapper to set
	 * set方式注入
	 */	
	
	public void setContractMapper(ContractMapper contractMapper) {
		this.contractMapper=contractMapper;
		this.setMapper(contractMapper);
	}
	
	public String getContractNo(){
		final String CONTRACT_PREFIX="LM-";  //合同前缀
		final int SERIAL_POS=3;  //合同序号索引
		final String SERIAL_LENGTH="4";  //序号格式化后长度
		//获取当前时间
		Date currentTime=new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-");
		String dateString = formatter.format(currentTime);
		//查询条件
		String cond=CONTRACT_PREFIX+dateString;
		
		
		String contractNo="";  //新生成的合同号
		
		//获取合同表中指定月份最大的合同号
		String maxContractNo=contractMapper.getMaxContractNo(cond);
		if(StringUtils.isNotBlank(maxContractNo)){
			String[] strArray = maxContractNo.split("-");
			String oldSerialStr=strArray[SERIAL_POS];
			
			int newSerial=Integer.parseInt(oldSerialStr)+1;	  //新的序列号		
			
			String newSerialStr = String.format("%0"+SERIAL_LENGTH+"d", newSerial);
			contractNo=cond+ newSerialStr; 
		}
		else{
			contractNo=cond+"0001";
		}
		
		return contractNo;
	}

	@Override
	public List<Map<String, Object>> selectContract(int timeCond, int dealStateCond, int searchTypeValue,
			String condValue) {
		
		return contractMapper.selectContractByCond(-timeCond,dealStateCond,searchTypeValue,condValue);
	}

	@Override
	public List<Map<String, Object>> selectContract(Map<String, Object> params) {
		return contractMapper.selectContract(params);
	}
	
	
	
	

}
