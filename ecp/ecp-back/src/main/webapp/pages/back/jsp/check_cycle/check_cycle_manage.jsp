<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>考核周期</title>
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
								考核周期列表
							</h3>
						</div>
						<div class="panel-body">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row clearfix">
									<div class="col-md-2 column">
										<button type="button" class="btn btn-default btn-primary" id="add-check-cycle-btn">添加考核周期</button>
									</div>
									<form id="search-check-cycle-form">
									<div class="col-md-2 column">
										<input type="text" id="search-check-cycle-year" name="searchYearName" title="考核年度"
													class="datetimepicker datetime form-control"
													readonly="readonly" placeholder="考核年度"
													onClick="WdatePicker({dateFmt:'yyyy'})" />
													<!-- onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> -->
									</div>
									<div class="col-md-2 column">
										<button type="button" class="btn btn-default btn-primary" id="search-check-cycle-btn">查询</button>
										<button type="button" class="btn btn-default btn-primary" id="delete-check-cycle-btn">删除全年</button>
									</div>
									</form>
								</div>
								
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-body">
								<div id="item-div" style="margin: 20px">
									<%@ include file="check_cycle_table.jsp"%>
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
<!-- 此处加载增加考核周期对话框 -->
</div>

		<%@ include file="../../../common/headJs.jsp"%>
		<script type="text/javascript" src="static/js/check_cycle.js"></script>
</body>
</html>
