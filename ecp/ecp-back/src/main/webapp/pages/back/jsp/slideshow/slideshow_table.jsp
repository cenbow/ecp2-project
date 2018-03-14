<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-striped table-bordered table-hover " id="slideshow-table"
	style="width: 100%;"center">
	<thead style="width: 98%; padding-top: 80px;">
		<tr role="row">
			<th><input type="checkbox" name="checkbox" id="checkbox" onclick="javascript:checkAll(this);">
				<label for="checkbox">ALL</label>
			</th>
			<th>ID</th>
			<th>名称</th>
			<th>轮播图</th>
			<th>类型</th>
			<th>是否显示</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${pagehelper.list}" var="slideshow">
			<tr>
				<td><input type="checkbox" name="checkbox2" id="slideshow-td-${slideshow.id}" onclick="javascript:checkOne();" value="${slideshow.id}"></td>
				<td>${slideshow.id}</td>
				<td>${slideshow.title}</td>
				<td><img alt="${slideshow.imgUrl}" src="${slideshow.imgUrl}" style="height:50px;" /></td>
				<td>
					<c:if test="${empty slideshow.type && slideshow.type!=1 && slideshow.type!=2}">未知</c:if>
					<c:if test="${slideshow.type==2}">三级类目</c:if>
					<c:if test="${slideshow.type==1}">商品</c:if>
				</td>
				<td>
					<input type="hidden" id="slideshow-showed-${slideshow.id}" value="${slideshow.showed}" />
					<c:if test="${empty slideshow.showed && slideshow.showed!=1 && slideshow.showed!=2}">
						<button type="button" class="btn btn-default btn-warning" id="slideshow-showed-yes-${slideshow.id}" onclick="javascript:updateShowedStatus(${slideshow.id}, 1);">是</button>	
						<button type="button" class="btn btn-danger" id="slideshow-showed-no-${slideshow.id}" onclick="javascript:updateShowedStatus(slideshow.id}, 2);">否</button>
					</c:if>
					<c:if test="${slideshow.showed==1}">
						<button type="button" class="btn btn-default btn-warning" id="slideshow-showed-yes-${slideshow.id}" onclick="javascript:updateShowedStatus(${slideshow.id}, 1);" disabled="disabled">是</button>	
						<button type="button" class="btn btn-danger" id="slideshow-showed-no-${slideshow.id}" onclick="javascript:updateShowedStatus(${slideshow.id}, 2);">否</button>
					</c:if>
					<c:if test="${slideshow.showed==2}">
						<button type="button" class="btn btn-default btn-warning" id="slideshow-showed-yes-${slideshow.id}" onclick="javascript:updateShowedStatus(${slideshow.id}, 1);">是</button>	
						<button type="button" class="btn btn-danger" id="slideshow-showed-no-${slideshow.id}" onclick="javascript:updateShowedStatus(${slideshow.id}, 2);" disabled="disabled">否</button>
					</c:if>
				</td>
				<td class="center ">
					<div style="text-align: center;; height: auto;"
						class="datagrid-cell datagrid-cell-c1-action">
						
						<button class="btn btn-default btn-info"
							onclick="javascript:selectDetails(${slideshow.id});">详情</button>	
						<c:if test="${not empty slideshow.showed && slideshow.showed==1}">
							<button type="button" class="btn btn-danger" id="slideshow-del-btn-${slideshow.id}" title="已显示的推荐不能删除" onclick="javascript:deleteInfoFun(${slideshow.id});" disabled="disabled">删除</button>
						</c:if>
						<c:if test="${empty slideshow.showed || slideshow.showed!=1}">
							<button type="button" class="btn btn-danger" id="slideshow-del-btn-${slideshow.id}" onclick="javascript:deleteInfoFun(${slideshow.id});">删除</button>
						</c:if>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="../../../common/pagehelper.jsp"%><!-- 分页页面 -->
