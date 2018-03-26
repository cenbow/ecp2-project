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
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="tabs-container" id="tabs-check-cycle">
						<ul class="nav nav-tabs" id="top_tab">
							<li class="active" onclick="javascript:resetFun();"><a data-toggle="tab" href="#tab-check-cycle-item"
								aria-expanded="true">考核周期列表</a></li>
							<li class="hide" id="edit-check-cycle-li"><a
								data-toggle="tab" href="#tab-check-cycle-edit" aria-expanded="false">添加/编辑</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-check-cycle-item" class="tab-pane active">
								<div class="container-fluid" style="margin-top: 20px;">
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
														<button type="button" class="btn btn-default btn-primary" id="add-check-cycle-btn">添加考核周期</button>
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
							<div id="tab-check-cycle-edit" class="tab-pane">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														考核周期信息
													</h3>
												</div>
												<div class="panel-body">
													<form class="form-horizontal" id="save-form">
														<input type="hidden" id="check-cycle-id" name="id" value="" />
														<div class="form-group">
															<label class="col-sm-2 control-label">周期名称</label>
															<div class="col-sm-10">
																<input type="text" id="check-cycle-name" name="cycleName"
																			class="form-control" placeholder="周期名称" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-2 control-label">年度名称</label>
															<div class="col-md-10">
																<input type="text" id="check-cycle-year-name" name="yearName"
																			class="datetimepicker datetime form-control"
																			readonly="readonly" placeholder="年度名称"
																			onClick="WdatePicker({dateFmt:'yyyy'})" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-2 control-label">时间段计法</label>
															<div class="col-md-10">
																<!-- <input type="text" id="check-cycle-cal-type" name="calType"
																	class="form-control" placeholder="时间段计法" /> -->
																<select class="form-control" id="check-cycle-cal-type" name="calType">
																	<option value="4">年</option>
																	<option value="3">季</option>
																	<option value="2">月</option>
																	<option value="1">天</option>
																</select>
															</div>
														</div>
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">开始时间</label>
															<div class="col-sm-10">
																<input type="text" id="check-cycle-start-date-str"
																			name="startDateStr"
																			class="datetimepicker datetime form-control"
																			readonly="readonly" placeholder="开始时间"
																			onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
																			<!-- onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" -->
															</div>
														</div>
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">结束时间</label>
															<div class="col-sm-10">
																<input type="text" id="check-cycle-end-date-str"
																			name="endDateStr"
																			class="datetimepicker datetime form-control"
																			readonly="readonly" placeholder="结束时间"
																			onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
																			<!-- onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" -->
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">&nbsp;</label>
															<div class="col-sm-10">
																<button type="button" class="btn btn-primary"
																	id="save-submit-btn">保存</button>
															</div>
														</div>
													</form>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<%@ include file="../../../common/headJs.jsp"%>
		<script type="text/javascript" src="static/js/check_cycle.js"></script>
</body>
</html>
