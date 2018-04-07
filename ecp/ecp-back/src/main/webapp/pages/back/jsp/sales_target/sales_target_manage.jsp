<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="tabs-container" id="tabs-sales-target">
						<ul class="nav nav-tabs" id="top_tab">
							<li class="active" onclick="javascript:resetFun();"><a data-toggle="tab" href="#tab-sales-target-item"
								aria-expanded="true">销售指标列表</a></li>
							<li class="hide" id="edit-sales-target-li"><a
								data-toggle="tab" href="#tab-sales-target-edit" aria-expanded="false">添加/编辑</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-sales-target-item" class="tab-pane active">
								<div class="container-fluid" style="margin-top: 20px;">
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
														<button type="button" class="btn btn-default btn-primary" id="add-sales-target-btn">添加销售指标</button>
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
							<div id="tab-sales-target-edit" class="tab-pane">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														销售指标信息
													</h3>
												</div>
												<div class="panel-body">
													<form class="form-horizontal" id="save-form">
														<input type="hidden" id="sales-target-id" name="id" value="" />
														<div class="form-group">
															<label class="col-sm-2 control-label">考核周期</label>
															<div class="col-sm-10">
																<!-- <input type="text" id="sales-target-check-cycle-id" name="checkCycleId"
																	class="form-control" placeholder="考核周期" /> -->
																<select class="form-control" id="sales-target-check-cycle-id" name="checkCycleId">
																	<option value="">请选择考核周期</option>
																	<c:forEach items="${checkCycleList}" var="checkCycle">
																		<option value="${checkCycle.id}">${checkCycle.yearName}&nbsp;-&nbsp;${checkCycle.cycleName}</option>
																	</c:forEach>
																</select>
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">IS/OS</label>
															<div class="col-sm-10">
																<!-- <input type="text" id="sales-target-user-id" name="userId"
																	class="form-control" placeholder="IS/OS" /> -->
																<input type="hidden" id="user-id" name="userId" value="" />
																<input type="hidden" id="role-id" name="roleId" value="" />
																<select class="form-control" id="sales-target-is-os">
																	<option value="">请选择IS/OS</option>
																	<c:forEach items="${userList}" var="user">
																		<option value="${user.id}-${user.role_id}">${user.role_name}&nbsp;-&nbsp;${user.nickname}</option>
																	</c:forEach>
																</select>
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">指标比例（%）</label>
															<div class="col-sm-10">
																<input type="text" id="sales-target-rate" name="targetRate"
																	class="form-control" placeholder="指标比例" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">指标金额（￥）</label>
															<div class="col-sm-10">
																<input type="text" id="sales-target-amount" name="targetAmount"
																	class="form-control" placeholder="指标金额" />
															</div>
														</div>
														<!-- <div class="form-group">
															<label class="col-sm-2 control-label">周期名称</label>
															<div class="col-sm-10">
																<input type="text" id="sales-target-name" name="cycleName"
																	class="form-control" placeholder="周期" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-2 control-label">年度名称</label>
															<div class="col-md-10">
																<input type="text" id="sales-target-year-name" name="yearName"
																	class="form-control" placeholder="年度名称" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-2 control-label">时间段计法</label>
															<div class="col-md-10">
																<input type="text" id="sales-target-cal-type" name="calType"
																	class="form-control" placeholder="时间段计法" />
																<select class="form-control" id="sales-target-cal-type" name="calType">
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
																<input type="text" id="sales-target-start-date-str"
																			name="startDateStr"
																			class="datetimepicker datetime form-control"
																			readonly="readonly" placeholder="开始时间"
																			onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
																			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
															</div>
														</div>
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">结束时间</label>
															<div class="col-sm-10">
																<input type="text" id="sales-target-end-date-str"
																			name="endDateStr"
																			class="datetimepicker datetime form-control"
																			readonly="readonly" placeholder="结束时间"
																			onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
																			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
															</div>
														</div> -->
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
		<script type="text/javascript" src="static/js/sales_target.js"></script>
</body>
</html>
