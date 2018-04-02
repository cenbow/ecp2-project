package com.ecp.service.front;

import java.util.List;
import java.util.Map;

import com.ecp.entity.Contract;
import com.ecp.service.IBaseService;


/**
 * @ClassName IContractService
 * @Description 合同-业务层
 * @author Administrator
 * @Date 2017年6月28日 下午5:02:50
 * @version 1.0.0
 */
public interface IContractService extends IBaseService<Contract, Long> {
	
	/**
	 * @Description 获取合同号
	 * @return 合同号
	 */
	public String getContractNo();
	
	/**
	 * @Description 查询合同
	 * @param timeCond  时间条件
	 * @param dealStateCond  处理状态条件
	 * @param searchTypeValue  搜索类型
	 * @param condValue  搜索值
	 * @return
	 */
	public List<Map<String,Object>> selectContract(int timeCond,int dealStateCond,int searchTypeValue,String condValue);
	
	/**
	 * 查询合同，用于提成的计算
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectContract(Map<String, Object> params);
	
}
