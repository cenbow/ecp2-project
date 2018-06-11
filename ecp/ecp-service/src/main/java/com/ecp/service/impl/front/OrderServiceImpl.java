package com.ecp.service.impl.front;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecp.bean.DeletedType;
import com.ecp.dao.OrdersMapper;
import com.ecp.entity.Orders;
import com.ecp.service.front.IOrderService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class OrderServiceImpl extends AbstractBaseService<Orders, Long> implements IOrderService {

	OrdersMapper ordersMapper;

	/**
	 * @param mapper
	 *            the mapper to set set方式注入
	 */
	public void setOrdersMapper(OrdersMapper ordersMapper) {
		this.ordersMapper = ordersMapper;
		this.setMapper(ordersMapper);
	}

	@Override
	public void createNewOrder(long buyerId, String buyerName, String orderId) {
		Orders record = new Orders();
		record.setBuyerId(buyerId);
		record.setName(buyerName);
		record.setOrderId(orderId);
		record.setCreateTime(new Date());

		ordersMapper.insert(record);
	}

	@Override
	public List<Orders> selectOrderByUserId(long buyerId) {
		Orders record = new Orders();
		record.setBuyerId(buyerId);
		record.setDeleted(DeletedType.NO); //选择未删除的记录

		return ordersMapper.select(record);

	}

	@Override
	public long getIdByOrderNo(String orderNo) {
		Orders record = new Orders();
		record.setOrderId(orderNo);
		Orders result = ordersMapper.selectOne(record);
		return result.getId();

	}

	@Override
	public List<Orders> selectOrderByOrderTimeAndDealState(long buyerId, int orderTimeCond, int dealStateCond) {
		return ordersMapper.selectOrderByOrderTimeAndDealState(buyerId,-orderTimeCond,dealStateCond);
	}

	@Override
	public Orders selectOrderByOrderNo(String orderNo) {
		Orders record=new Orders();
		record.setOrderId(orderNo);
		
		return ordersMapper.selectOne(record);
	}

	@Override
	public List<Orders> selectAllOrderByOrderTimeAndDealState(int orderTimeCond, int dealStateCond) {
		return ordersMapper.selectAllOrderByOrderTimeAndDealState(-orderTimeCond,dealStateCond);
	}

	@Override
	public List<Map<String, Object>> selectOrder(int orderTimeCond, int dealStateCond, int searchTypeValue,
			String condValue) {
		List<Map<String,Object>> resultList=null;
		switch(searchTypeValue){
		case 0:  //在订单表内进行查询			
		case 1:  
		case 2:
		case 3:
		case 4:
			resultList= ordersMapper.selectOrderBySelfField(-orderTimeCond, dealStateCond, searchTypeValue,condValue); 
			break;
		case 5:
		case 6:
		case 7:	
			resultList= ordersMapper.selectOrderByAgent(-orderTimeCond, dealStateCond, searchTypeValue,condValue);
			break;
		}
		
		return resultList;
	}

	@Override
	public List<Map<String, Object>> selectOrders(List<Long> agentIdList, int orderTimeCond, int dealStateCond,
			int searchTypeValue, String condValue) {
		return ordersMapper.selectOrders(agentIdList, -orderTimeCond, dealStateCond, searchTypeValue, condValue);
	}
	
	public List<Map<String, Object>> selectOrder(int orderTimeCond, int dealStateCond, int searchTypeValue,
			String condValue, String provinceName, String cityName, String countyName,
			List<Map<String, Object>> agentIdList) {
		return ordersMapper.selectOrder(-orderTimeCond, dealStateCond, searchTypeValue,condValue,provinceName,cityName,countyName,agentIdList);
		
	}

	@Override
	public List<Map<String, Object>> selectOrdersMap(Map<String, Object> params) {
		return ordersMapper.selectOrdersMap(params);
	}
	
	/* (非 Javadoc) 
		* <p>Title: selectOrder</p> 
		* <p>Description: 根据欠款条件查询订单</p> 
		* @param orderTimeCond
		* @param dealStateCond
		* @param searchTypeValue
		* @param condValue
		* @param provinceName
		* @param cityName
		* @param countyName
		* @param agentIdList
		* @param totalPayFlag
		* @return 
		* @see com.ecp.service.front.IOrderService#selectOrder(int, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List, int) 
	*/
	public List<Map<String, Object>> selectOrder(int orderTimeCond, int dealStateCond, int searchTypeValue,
			String condValue, String provinceName, String cityName, String countyName,
			List<Map<String, Object>> agentIdList, int totalPayFlag) {
		return ordersMapper.selectOrderDue(-orderTimeCond, dealStateCond, searchTypeValue,condValue,provinceName,cityName,countyName,agentIdList,totalPayFlag);
	}

	@Override
	public BigDecimal getOrderAmount(int orderTimeCond, int dealStateCond, Integer searchTypeValue, String condValue,
			String provinceName, String cityName, String countyName, List<Map<String, Object>> agentIdList,
			int totalPayFlag) {
		return ordersMapper.getOrderAmount(-orderTimeCond, dealStateCond, searchTypeValue,condValue,provinceName,cityName,countyName,agentIdList,totalPayFlag);
	}

	@Override
	public List<Orders> selectOrderByCondAndSubUser(long buyerId, List<Long> subList, int orderTimeCond,
			int dealStateCond) {
		return ordersMapper.selectOrderByCondAndSubUser(buyerId,subList,-orderTimeCond,dealStateCond);
	}

	@Override
	public List<Map<String, Object>> selectOrderByOrderScope(int orderTimeCond, int dealStateCond,
			int searchTypeValue, String condValue, String provinceName, String cityName, String countyName,
			List<Map<String, Object>> agentIdList,List<Map<String, Object>> orderIdList) {
		List<Map<String,Object>> resultList=null;
		return ordersMapper.selectOrderByOrderScope(-orderTimeCond, dealStateCond, searchTypeValue,condValue,provinceName,cityName,countyName,agentIdList,orderIdList);
	}

	@Override
	public int saveMarketFeeComment(long orderId, String marketFeeComment) {
		Orders record = new Orders();
		record.setId(orderId);
		record.setMarketFeeComment(marketFeeComment);
		return ordersMapper.updateByPrimaryKeySelective(record);		
	}

	@Override
	public int savePaymentComment(long orderId, String paymentComment) {
		Orders record = new Orders();
		record.setId(orderId);
		record.setPaymentComment(paymentComment);
		return ordersMapper.updateByPrimaryKeySelective(record);	
	}
	
	

}
