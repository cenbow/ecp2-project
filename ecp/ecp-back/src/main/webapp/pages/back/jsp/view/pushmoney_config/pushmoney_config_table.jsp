<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-striped table-bordered table-hover " id="pushmoney-config-table"
	style="width: 100%;"center">
	<thead style="width: 98%; padding-top: 80px;">
		<tr role="row">
			<th><input type="checkbox" name="checkbox" id="checkbox" onclick="javascript:checkAll(this);">
				<label for="checkbox">ALL</label>
			</th>
			<th>ID</th>
			<th>角色</th>
			<th>销售额完成比例（%）</th>
			<th>提成比例（%）</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pagehelper.list}" var="config">
			<tr>
				<td>
					<input type="checkbox" name="checkbox2" id="pushmoney-config-td-${config.id}" onclick="javascript:checkOne();" value="${config.id}">
				</td>
				<td>${config.id}</td>
				<td>${config.role_name}</td>
				<td>>=${config.completion_rate}</td>
				<td>${config.pushmoney_rate}</td>
				<td class="center ">
					<div style="text-align: center;; height: auto;"
						class="datagrid-cell datagrid-cell-c1-action">
						
						<button class="btn btn-default delet_btn"
							onclick="javascript:selectDetails(${config.id});">详情</button>	
						<%-- <button class="btn btn-default delet_btn"
							onclick="javascript:deleteInfoFun(${config.id});">删除</button> --%>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="../../../../common/pagehelper.jsp"%><!-- 分页页面 -->
