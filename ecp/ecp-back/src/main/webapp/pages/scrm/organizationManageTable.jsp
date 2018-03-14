<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-hover table-bordered">
	<thead>
		<tr>
			<th>组织名称</th>
			<th>添加子</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${organizeBeanList}" var="organize">
			<tr>
				<td style="width: 30%;">
					<c:if test="${not empty organize.childList}">
						<button type="button" onclick="javascript:displayChildNode(${organize.organizeId});">显示/隐藏</button>
					</c:if>
					&nbsp;&nbsp;${organize.organizeName}
				</td>
				<td style="width: 30%;">
					<button type="button" onclick="javascript:addOrganizeChild(${organize.organizeId});">+</button>
				</td>
				<td style="width: 40%;">
					<button type="button" onclick="javascript:selectDetails(${organize.organizeId});">编辑</button>
					<button type="button" onclick="javascript:deleteInfoFun(${organize.organizeId});">删除</button>
				</td>
			</tr>
			<c:if test="${not empty organize.childList}">
				<tr id="child-tr-${organize.organizeId}" class="hide">
					<td colspan="3">
						<table class="table table-hover table-bordered">
							<tbody>
								<c:forEach items="${organize.childList}" var="child">
									<tr>
										<td style="width: 30%;">${child.organizeName}</td>
										<td style="width: 30%;">&nbsp;</td>
										<td style="width: 40%;">
											<button type="button" onclick="javascript:selectDetails(${child.organizeId});">编辑</button>
											<button type="button" onclick="javascript:deleteInfoFun(${child.organizeId});">删除</button>
										</td>
									</tr>
								</c:forEach>
							<tbody>
						</table>
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</tbody>
</table>
<%@include file="../common/pagehelper.jsp"%><!-- 分页页面 -->