<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-striped table-bordered table-hover " id="role-table"
	style="width: 100%;"center">
	<thead style="width: 98%; padding-top: 80px;">
		<tr role="row">
			<th><input type="checkbox" name="checkbox" id="checkbox" onclick="javascript:checkAll(this);">
				<label for="checkbox">ALL</label>
			</th>
			<th>ID</th>
			<th>角色编码</th>
			<th>角色名称</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pagehelper.list}" var="role">
			<tr>
				<td><input type="checkbox" name="checkbox2" id="user-td-${role.roleId}" onclick="javascript:checkOne();" value="${role.roleId}"></td>
				<td>${role.roleId}</td>
				<td>${role.roleCode}</td>
				<td>${role.roleName}</td>
				<td class="center ">
					<div style="text-align: center;; height: auto;"
						class="datagrid-cell datagrid-cell-c1-action">
						<button class="btn btn-default delet_btn"
							onclick="javascript:selectDetails(${role.roleId});">编辑</button>	
						<button class="btn btn-default delet_btn"
							onclick="javascript:deleteInfoFun(${role.roleId});">删除</button>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="../../../common/pagehelper.jsp"%><!-- 分页页面 -->
