<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-striped table-bordered table-hover " id="item-sales-stats-table"
	style="width: 100%;"center">
	<thead style="width: 98%; padding-top: 80px;">
		<tr role="row">
			<th>订单号</th>
			<th>商品名称</th>
			<th>商品价格</th>
			<th>商品数据</th>
			<th>商品总金额</th>
			<th>订单时间</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pagehelper.list}" var="item">
			<tr>
				<td>${item.order_id}</td>
				<td>${item.sku_name}</td>
				<td>${item.primitive_price}</td>
				<td>${item.sum_num}</td>
				<td>${item.pay_price_total}</td>
				<td>${item.create_time}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
	g_itemNameList = ${itemNameList};
	console.log(g_itemNameList);
	g_itemNumTotalList = ${itemNumTotalList};
	console.log(g_itemNumTotalList);
	g_itemPriceTotalList = ${itemPriceTotalList};
	console.log(g_itemPriceTotalList);

</script>
<%@include file="../../../common/pagehelper.jsp"%><!-- 分页页面 -->
