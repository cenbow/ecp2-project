package com.ecp.service.impl.front;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecp.bean.AddSkuToOrderBean;
import com.ecp.dao.OrderItemsMapper;
import com.ecp.entity.OrderItems;
import com.ecp.service.front.IOrderItemService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class OrderItemServiceImpl extends AbstractBaseService<OrderItems, Long> implements IOrderItemService {
	
	OrderItemsMapper orderItemsMapper; 
	
	
	/**
	 * @param mapper
	 * the mapper to set set方式注入
	 */	
	public void setOrderItemsMapper(OrderItemsMapper orderItemsMapper) { 
		this.orderItemsMapper=orderItemsMapper;
		this.setMapper(orderItemsMapper);
	}

	@Override
	public void addItemIntoOrder(List<AddSkuToOrderBean> itemList, String orderId) {
		for(AddSkuToOrderBean item:itemList){
			OrderItems record=new OrderItems();
			
			record.setOrderId(orderId);  //订单号
			record.setCid(item.getCid());  //类目id
			record.setItemId(item.getItemId()); //spu id
			record.setSkuId(item.getSkuId());  //sku id
			record.setSkuName(item.getSkuName());  //sku name			
			record.setPrimitivePrice(item.getSkuPrice());  //原始价（未折减价格）
			record.setNum(item.getSkuNum());  //SKU数量
			//计算此SKU的金额
			BigDecimal num=new BigDecimal(item.getSkuNum());
			record.setPayPriceTotal(item.getSkuPrice().multiply(num));
			
			
			record.setCreateTime(new Date());
			
			
			
			orderItemsMapper.insert(record);
			
		}
		
	}

	@Override
	public List<Map<String,String>> selectItemsByOrderId(String orderId) {
		
		return orderItemsMapper.selectItemsByOrderId(orderId);
	
	}

	@Override
	public void addItemIntoOrder(List<AddSkuToOrderBean> itemList, List<HashMap<String, Object>> skuAppendAttrList,String orderId) {
		
		for(int i=0;i<itemList.size();i++){
			AddSkuToOrderBean item=itemList.get(i);
			
			OrderItems record=new OrderItems();
			
			record.setOrderId(orderId);  //订单号
			record.setCid(item.getCid());  //类目id
			record.setItemId(item.getItemId()); //spu id
			record.setSkuId(item.getSkuId());  //sku id
			record.setSkuName(item.getSkuName());  //sku name			
			record.setPrimitivePrice(item.getSkuPrice());  //原始价（未折减价格）
			record.setNum(item.getSkuNum());  //SKU数量
			//计算此SKU的金额
			BigDecimal num=new BigDecimal(item.getSkuNum());
			record.setPayPriceTotal(item.getSkuPrice().multiply(num));
			
			//加入二期所增加的属性			
			record.setLowestPrice((BigDecimal)skuAppendAttrList.get(i).get("lowestPrice"));
			record.setHighestPrice((BigDecimal)skuAppendAttrList.get(i).get("highestPrice"));
			record.setHardCostPrice((BigDecimal)skuAppendAttrList.get(i).get("hardCostPrice"));
			record.setIsPlanProduct((Byte)skuAppendAttrList.get(i).get("isPlanProduct"));
			
			//记录插入时间
			record.setCreateTime(new Date());
			
			
			orderItemsMapper.insert(record);
			
			
		}
		
		for(AddSkuToOrderBean item:itemList){
			
			
		}
		
	}

}
