<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>销售指标</title>
<%@ include file="../../../common/headCss.jsp"%>
<!-- 日期工具 -->
<script type="text/javascript" src="static/calendar/WdatePicker.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="container-fluid">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">
								销售指标列表
							</h3>
						</div>
						<div class="panel-body">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row clearfix">
									<div class="col-md-2 column">
										<button type="button" class="btn btn-default btn-primary" id="add-sales-target-btn">添加销售指标</button>
									</div>
									<div class="col-md-2 column">
										<input type="text" id="search-check-cycle-year" name="searchYearName" title="考核年度"
													class="datetimepicker datetime form-control"
													readonly="readonly" placeholder="考核年度"
													onClick="WdatePicker({dateFmt:'yyyy'})" />
													<!-- onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> -->
									</div>
									<div class="col-md-3 column">
										<input type="hidden" id="search-user-id" name="userId" value="" />
										<input type="hidden" id="search-role-id" name="roleId" value="" />
										<select class="form-control" id="search-is-os" title="人员（角色）">
											<option value="">—— 人员（角色） ——</option>
											<c:forEach items="${userList}" var="user">
												<option value="${user.id}-${user.role_id}">${user.nickname}（${user.role_name}）</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-2 column">
										<button type="button" class="btn btn-default btn-primary" id="search-sales-target-btn">查询</button>
									</div>
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-body">
								<div id="item-div" style="margin: 20px">
									<%@ include file="sales_target_table.jsp"%>
								</div>
							</div>
						</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

<div id="load-add-dialog-div">
<!-- 此处加载增加考核指标对话框 -->
</div>
<div id="load-edit-dialog-div">
<!-- 此处加载编辑考核指标对话框 -->
<%@ include file="edit_sales_target_dialog.jsp"%>
</div>

		<%@ include file="../../../common/headJs.jsp"%>
		<script type="text/javascript" src="static/js/sales_target.js"></script>
</body>
</html>
