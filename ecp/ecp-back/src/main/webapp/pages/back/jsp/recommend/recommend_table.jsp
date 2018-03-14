<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-striped table-bordered table-hover " id="recommend-table"
	style="width: 100%;"center">
	<thead style="width: 98%; padding-top: 80px;">
		<tr role="row">
			<th><input type="checkbox" name="checkbox" id="checkbox" onclick="javascript:checkAll(this);">
				<label for="checkbox">ALL</label>
			</th>
			<th>ID</th>
			<th>名称</th>
			<th>类型</th>
			<th>是否显示</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pagehelper.list}" var="recommend">
			<tr>
				<td><input type="checkbox" name="checkbox2" id="recommend-td-${recommend.id}" onclick="javascript:checkOne();" value="${recommend.id}"></td>
				<td>${recommend.id}</td>
				<td>${recommend.name}</td>
				<td>
					<c:if test="${empty recommend.type && recommend.type!=1 && recommend.type!=2}">未知</c:if>
					<c:if test="${recommend.type==2}">推荐类目</c:if>
					<c:if test="${recommend.type==1}">推荐商品</c:if>
				</td>
				<td>
					<input type="hidden" id="recommend-showed-${recommend.id}" value="${recommend.showed}" />
					<c:if test="${empty recommend.showed && recommend.showed!=1 && recommend.showed!=2}">
						<button type="button" class="btn btn-default btn-warning" id="recommend-showed-yes-${recommend.id}" onclick="javascript:updateShowedStatus(${recommend.id}, 1);">是</button>	
						<button type="button" class="btn btn-danger" id="recommend-showed-no-${recommend.id}" onclick="javascript:updateShowedStatus(recommend.id}, 2);">否</button>
					</c:if>
					<c:if test="${recommend.showed==1}">
						<button type="button" class="btn btn-default btn-warning" id="recommend-showed-yes-${recommend.id}" onclick="javascript:updateShowedStatus(${recommend.id}, 1);" disabled="disabled">是</button>	
						<button type="button" class="btn btn-danger" id="recommend-showed-no-${recommend.id}" onclick="javascript:updateShowedStatus(${recommend.id}, 2);">否</button>
					</c:if>
					<c:if test="${recommend.showed==2}">
						<button type="button" class="btn btn-default btn-warning" id="recommend-showed-yes-${recommend.id}" onclick="javascript:updateShowedStatus(${recommend.id}, 1);">是</button>	
						<button type="button" class="btn btn-danger" id="recommend-showed-no-${recommend.id}" onclick="javascript:updateShowedStatus(${recommend.id}, 2);" disabled="disabled">否</button>
					</c:if>
				</td>
				<td class="center ">
					<div style="text-align: center;; height: auto;"
						class="datagrid-cell datagrid-cell-c1-action">
						
						<button class="btn btn-default btn-info"
							onclick="javascript:selectDetails(${recommend.id});">详情</button>	
						<c:if test="${not empty recommend.showed && recommend.showed==1}">
							<button type="button" class="btn btn-danger" id="recommend-del-btn-${recommend.id}" title="已显示的推荐不能删除" onclick="javascript:deleteInfoFun(${recommend.id});" disabled="disabled">删除</button>
						</c:if>
						<c:if test="${empty recommend.showed || recommend.showed!=1}">
							<button type="button" class="btn btn-danger" id="recommend-del-btn-${recommend.id}" onclick="javascript:deleteInfoFun(${recommend.id});">删除</button>
						</c:if>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="../../../common/pagehelper.jsp"%><!-- 分页页面 -->
