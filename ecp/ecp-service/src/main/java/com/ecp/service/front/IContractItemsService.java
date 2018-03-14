package com.ecp.service.front;

import java.util.List;
import java.util.Map;

import com.ecp.entity.ContractItems;
import com.ecp.service.IBaseService;


/**
 * @ClassName IContractService
 * @Description 合同行条目-业务层
 * @author Administrator
 * @Date 2017年6月28日 下午5:02:50
 * @version 1.0.0
 */
public interface IContractItemsService extends IBaseService<ContractItems, Long> {
	
	/**
	 * @Description 根据合同编号读取合同商品条目
	 * @param contractNo
	 * @return
	 */
	public List<Map<String,Object>> selectItemsByContractNo(String contractNo);
}
