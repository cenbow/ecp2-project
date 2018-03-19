<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-striped table-bordered table-hover " id="customer-level-rule-table"
	style="width: 100%;"center">
	<thead style="width: 98%; padding-top: 80px;">
		<tr role="row">
			<th><input type="checkbox" name="checkbox" id="checkbox" onclick="javascript:checkAll(this);">
				<label for="checkbox">ALL</label>
			</th>
			<th>ID</th>
			<th>客户类型</th>
			<th>客户级别</th>
			<th>规则类型</th>
			<th>规则内容</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pagehelper.list}" var="levelRule">
			<tr>
				<td>
					<input type="checkbox" name="checkbox2" id="customer-level-rule-td-${levelRule.id}" onclick="javascript:checkOne();" value="${levelRule.id}">
				</td>
				<td>${levelRule.id}</td>
				<td>${levelRule.type_name}</td>
				<td>${levelRule.level_name}</td>
				<td>
					<c:if test="${empty levelRule.rule_type || (levelRule.rule_type!=1 && levelRule.rule_type!=2)}">未知</c:if>
					<c:if test="${levelRule.rule_type==1}">百分比</c:if>
					<c:if test="${levelRule.rule_type==2}">数值</c:if>
				</td>
				<td>
					${levelRule.rule_name}
					<c:if test="${not empty levelRule.rule_type && levelRule.rule_type==1}">%</c:if>
				</td>
				<td class="center ">
					<div style="text-align: center;; height: auto;"
						class="datagrid-cell datagrid-cell-c1-action">
						
						<button class="btn btn-default delet_btn"
							onclick="javascript:selectDetails(${levelRule.id});">详情</button>	
						<button class="btn btn-default delet_btn"
							onclick="javascript:deleteInfoFun(${levelRule.id});">删除</button>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="../../../common/pagehelper.jsp"%><!-- 分页页面 -->
