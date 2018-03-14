<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-striped table-bordered table-hover " id="product-table"
	style="width: 100%;"center">
	<thead style="width: 98%; padding-top: 80px;">
		<tr role="row">
			<th><input type="checkbox" name="checkbox" id="checkbox" onclick="javascript:checkAll(this);">
				<label for="checkbox">ALL</label>
			</th>
			<th>ID</th>
			<th>类目</th>
			<th>品牌</th>
			<th>名称</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pagehelper.list}" var="item">
			<tr onclick="javascript:checkOne(${item.item_id});">
				<td><input type="checkbox" name="itemCheckbox" id="item-td-${item.item_id}" value="${item.item_id}"></td>
				<td>${item.item_id}</td>
				<td>${item.c_name}</td>
				<td>${item.brand_name}</td>
				<td>${item.item_name}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<%@include file="../../../common/pagehelper.jsp"%><!-- 分页页面 -->
