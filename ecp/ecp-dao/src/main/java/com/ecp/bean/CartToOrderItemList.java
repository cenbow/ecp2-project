package com.ecp.bean;

import java.util.List;

/**
 * @ClassName CartToOrderItemList
 * @Description 用于接收 购物车--->结算--->订单 数据 
 * @author Administrator
 * @Date 2017年6月26日 上午10:38:26
 * @version 1.0.0
 */
public class CartToOrderItemList {
private long addrId;  //订单中用户收货地址ID
private String buyerComment;  //订单备注
private List<AddSkuToOrderBean> cartItemList;  //购物车中所选商品
private String memo; //订单备注

public List<AddSkuToOrderBean> getCartItemList() {
	return cartItemList;
}

public void setCartItemList(List<AddSkuToOrderBean> cartItemList) {
	this.cartItemList = cartItemList;
}

public long getAddrId() {
	return addrId;
}

public void setAddrId(long addrId) {
	this.addrId = addrId;
}

public String getBuyerComment() {
	return buyerComment;
}

public void setBuyerComment(String buyerComment) {
	this.buyerComment = buyerComment;
}

public String getMemo() {
	return memo;
}

public void setMemo(String memo) {
	this.memo = memo;
}
}
