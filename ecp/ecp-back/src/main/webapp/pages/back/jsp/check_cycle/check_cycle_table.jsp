<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-striped table-bordered table-hover " id="check-cycle-table"
	style="width: 100%;"center">
	<thead style="width: 98%; padding-top: 80px;">
		<tr role="row">
			<th><input type="checkbox" name="checkbox" id="checkbox" onclick="javascript:checkAll(this);">
				<label for="checkbox">ALL</label>
			</th>
			<th>ID</th>
			<th>年度名称</th>
			<th>周期名称</th>
			<th>时间段计法</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pagehelper.list}" var="cycle">
			<tr>
				<td>
					<input type="checkbox" name="checkbox2" id="check-cycle-td-${cycle.id}" onclick="javascript:checkOne();" value="${cycle.id}">
				</td>
				<td>${cycle.id}</td>
				<td>${cycle.yearName}</td>
				<td>${cycle.cycleName}</td>
				<td>
					<c:if test="${empty cycle.calType && (cycle.calType!=1 || cycle.calType!=2 || cycle.calType!=3 || cycle.calType!=4)}">天</c:if>
					<c:if test="${cycle.calType==1}">天</c:if>
					<c:if test="${cycle.calType==2}">月</c:if>
					<c:if test="${cycle.calType==3}">季</c:if>
					<c:if test="${cycle.calType==4}">年</c:if>
				</td>
				<td>${cycle.startDateStr}</td>
				<td>${cycle.endDateStr}</td>
				<td class="center ">
					<div style="text-align: center;; height: auto;"
						class="datagrid-cell datagrid-cell-c1-action">
						
						<button class="btn btn-default delet_btn"
							onclick="javascript:selectDetails(${cycle.id});">详情</button>	
						<button class="btn btn-default delet_btn"
							onclick="javascript:deleteInfoFun(${cycle.id});">删除</button>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="../../../common/pagehelper.jsp"%><!-- 分页页面 -->
