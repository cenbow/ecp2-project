<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-striped table-bordered table-hover " id="sales-target-table"
	style="width: 100%;"center">
	<thead style="width: 98%; padding-top: 80px;">
		<tr role="row">
			<!-- <th><input type="checkbox" name="checkbox" id="checkbox" onclick="javascript:checkAll(this);">
				<label for="checkbox">ALL</label>
			</th> -->
			<!-- <th>ID</th> -->
			<th>人员（角色）</th>
			<th>考核年度</th>
			<th>周期名称</th>
			<th>指标比例</th>
			<th>指标金额</th>
			<th>利润金额</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pagehelper.list}" var="target">
			<tr>
				<%-- <td>
					<input type="checkbox" name="checkbox2" id="sales-target-td-${target.id}" onclick="javascript:checkOne();" value="${target.id}">
				</td> --%>
				<%-- <td>${target.id}</td> --%>
				<td>${target.username}（${target.role_name}）</td>
				<td>${target.year_name}</td>
				<td>${target.cycle_name}</td>
				<td>${target.target_rate}%</td>
				<td>￥${target.target_amount}</td>
				<td>￥${target.profit_amount}</td>
				<td class="center ">
					<div style="text-align: center;; height: auto;"
						class="datagrid-cell datagrid-cell-c1-action">
						
						<button class="btn btn-default delet_btn"
							onclick="javascript:selectDetails(${target.id});">详情</button>	
						<button class="btn btn-default delet_btn"
							onclick="javascript:deleteInfoFun(${target.id});">删除</button>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="../../../common/pagehelper.jsp"%><!-- 分页页面 -->
