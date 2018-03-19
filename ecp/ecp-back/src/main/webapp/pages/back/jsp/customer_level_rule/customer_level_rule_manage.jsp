<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>客户级别规则</title>
<%@ include file="../../../common/headCss.jsp"%>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="tabs-container" id="tabs-customer-level-rule">
						<ul class="nav nav-tabs" id="top_tab">
							<li class="active" onclick="javascript:resetFun();"><a data-toggle="tab" href="#tab-customer-level-rule-item"
								aria-expanded="true">客户级别规则列表</a></li>
							<li class="hide" id="edit-customer-level-rule-li"><a
								data-toggle="tab" href="#tab-customer-level-rule-edit" aria-expanded="false">添加/编辑</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-customer-level-rule-item" class="tab-pane active">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														客户级别规则列表
													</h3>
												</div>
												<div class="panel-body">
												<div class="panel panel-default">
													<div class="panel-body">
														<button type="button" class="btn btn-default btn-primary" id="add-customer-level-rule-btn">添加客户级别规则</button>
													</div>
												</div>
												<div class="panel panel-default">
													<div class="panel-body">
														<div id="item-div" style="margin: 20px">
															<%@ include file="customer_level_rule_table.jsp"%>
														</div>
													</div>
												</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-customer-level-rule-edit" class="tab-pane">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														客户级别规则信息
													</h3>
												</div>
												<div class="panel-body">
													<form class="form-horizontal" id="save-form">
														<input type="hidden" id="level-rule-id" name="id" value="" />
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">客户类型</label>
															<div class="col-sm-10" id="">
																<select class="form-control" id="customer-type-id" name="customerTypeId" onchange="javascript:selectLevelByTypeId();">
																	<c:forEach items="${customerTypeList}" var="type">
																		<option value="${type.id}">${type.typeName}</option>
																	</c:forEach>	
																</select>
															</div>
														</div>
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">客户级别</label>
															<div class="col-sm-10" id="">
																<select class="form-control" id="customer-level-id" name="customerLevelId">
																	<c:forEach items="${customerLevelList}" var="level">
																		<option value="${level.id}">${level.levelName}</option>
																	</c:forEach>	
																</select>
															</div>
														</div>
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">客户级别规则类型</label>
															<div class="col-sm-10" id="">
																<select class="form-control" id="level-rule-type" name="ruleType">
																	<option value="1">百分比</option>
																	<option value="2">数值</option>
																</select>
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">规则内容</label>
															<div class="col-sm-10">
																<input type="text" id="level-rule-name" name="ruleName"
																	class="form-control" placeholder="规则内容" />
															</div>
														</div>
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">备注</label>
															<div class="col-sm-10">
																<input type="text" id="level-rule-comment" name="comment"
																	class="form-control" placeholder="备注" />
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
		<script type="text/javascript" src="static/js/customer_level_rule.js"></script>
</body>
</html>
