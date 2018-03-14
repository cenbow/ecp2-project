<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-striped table-bordered table-hover " id="user-icon-table"
	style="width: 100%;"center">
	<thead style="width: 98%; padding-top: 80px;">
		<tr role="row">
			<th><input type="checkbox" name="checkbox" id="checkbox" onclick="javascript:checkAll(this);">
				<label for="checkbox">ALL</label>
			</th>
			<th>ID</th>
			<th>用户名</th>
			<th>昵称</th>
			<th>手机</th>
			<th>会员等级</th>
			<th>会员渠道</th>
			<th>电子邮件</th>
			<th>注册时间</th>
			<th>城市</th>
			<th>性别</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pagehelper.list}" var="user">
			<tr>
				<td><input type="checkbox" name="checkbox2" id="checkbox-${user.user_id}" onclick="javascript:checkOne();"></td>
				<td>${user.user_id}</td>
				<td>${user.username}</td>
				<td>${user.nick_name}</td>
				<td>${user.phone}</td>
				<td>${user.level_name}</td>
				<td>${user.organize_name}</td>
				<td>${user.email}</td>
				<td>${user.create_time}</td>
				<td>${user.city}</td>
				<td><c:if test="${user.sex==0}">男</c:if> <c:if
						test="${user.sex==1}">女</c:if> <c:if
						test="${user.sex!=0 && user.sex!=1}">未知</c:if></td>
				<td class="center ">
					<div style="text-align: center;; height: auto;"
						class="datagrid-cell datagrid-cell-c1-action">
						
						<button class="btn btn-default delet_btn"
							onclick="javascript:alert('消费记录');">消费记录</button>
						<button class="btn btn-default delet_btn"
							onclick="javascript:showUserDetail();">用户画像</button>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="../common/pagehelper.jsp"%><!-- 分页页面 -->
