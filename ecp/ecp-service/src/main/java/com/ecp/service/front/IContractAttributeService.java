package com.ecp.service.front;

import java.util.List;
import java.util.Map;

import com.ecp.entity.ContractAttribute;
import com.ecp.service.IBaseService;


/**
 * @ClassName IContractAttributeService
 * @Description 合同属性
 * @author Administrator
 * @Date 2017年6月28日 下午5:08:53
 * @version 1.0.0
 */
public interface IContractAttributeService extends IBaseService<ContractAttribute, Long> {
	/**
	 * @Description 根据合同模板选择合同属性
	 * @param templateId  模板ID（PK）
	 * @return  属性列表
	 */
	public List<ContractAttribute> selectAttributesByTemplateId(long templateId);
	
	/**
	 * @Description 根据合同ID（pk）获取此合同的属性/属性值列表
	 * @param contractId 合同ID
	 * @return
	 */
	public List<Map<String,String>> selectAttrValByContractId(long contractId);
	
}
