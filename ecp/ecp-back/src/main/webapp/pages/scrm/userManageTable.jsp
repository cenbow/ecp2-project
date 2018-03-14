<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-striped table-bordered table-hover " id="user-table"
	style="width: 100%;"center">
	<thead style="width: 98%; padding-top: 80px;">
		<tr role="row">
			<th><input type="checkbox" name="checkbox" id="checkbox" onclick="javascript:checkAll(this);">
				<label for="checkbox">ALL</label>
			</th>
			<th>ID</th>
			<th>用户名</th>
			<th>真实姓名</th>
			<th>昵称</th>
			<th>状态</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pagehelper.list}" var="user">
			<tr>
				<td><input type="checkbox" name="checkbox2" id="user-td-${user.userId}" onclick="javascript:checkOne();" value="${user.userId}"></td>
				<td>${user.userId}</td>
				<td>${user.username}</td>
				<td>${user.fullName}</td>
				<td>${user.nickName}</td>
				<td>
					<c:if test="${user.state==0}">
						<span class="label label-success">启用</span>
					</c:if>
					<c:if test="${user.state==1}">
						<span class="label label-warning">禁用</span>
					</c:if>
					<c:if test="${user.state!=0 && user.state!=1}">
						<span class="label label-danger">未知</span>
					</c:if>
				</td>
				<td>${user.createTime}</td>
				<td class="center ">
					<div style="text-align: center;; height: auto;"
						class="datagrid-cell datagrid-cell-c1-action">
						
						<button class="btn btn-default delet_btn"
							onclick="javascript:selectDetails(${user.userId});">用户详情</button>	
						<button class="btn btn-default delet_btn"
							onclick="javascript:deleteInfoFun(${user.userId});">删除</button>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="../common/pagehelper.jsp"%><!-- 分页页面 -->
