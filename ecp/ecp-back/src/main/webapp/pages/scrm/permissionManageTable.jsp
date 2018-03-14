<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-striped table-bordered table-hover " id="permission-table"
	style="width: 100%;"center">
	<thead style="width: 98%; padding-top: 80px;">
		<tr role="row">
			<th><input type="checkbox" name="checkbox" id="checkbox" onclick="javascript:checkAll(this);">
				<label for="checkbox">ALL</label>
			</th>
			<th>ID</th>
			<th>权限名称</th>
			<th>权限描述</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pagehelper.list}" var="permission">
			<tr>
				<td><input type="checkbox" name="checkbox2" id="user-td-${permission.permissionId}" onclick="javascript:checkOne();" value="${permission.permissionId}"></td>
				<td>${permission.permissionId}</td>
				<td>${permission.permissionName}</td>
				<td>${permission.permissionDes}</td>
				<td class="center ">
					<div style="text-align: center;; height: auto;"
						class="datagrid-cell datagrid-cell-c1-action">
						
						<button class="btn btn-default delet_btn"
							onclick="javascript:selectDetails(${permission.permissionId});">角色详情</button>	
						<button class="btn btn-default delet_btn"
							onclick="javascript:deleteInfoFun(${permission.permissionId});">删除</button>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="../common/pagehelper.jsp"%><!-- 分页页面 -->
