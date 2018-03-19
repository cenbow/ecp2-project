<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>客户级别</title>
<%@ include file="../../../common/headCss.jsp"%>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="tabs-container" id="tabs-customer-level">
						<ul class="nav nav-tabs" id="top_tab">
							<li class="active" onclick="javascript:resetFun();"><a data-toggle="tab" href="#tab-customer-level-item"
								aria-expanded="true">客户级别列表</a></li>
							<li class="hide" id="edit-customer-level-li"><a
								data-toggle="tab" href="#tab-customer-level-edit" aria-expanded="false">添加/编辑</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-customer-level-item" class="tab-pane active">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														客户级别列表
													</h3>
												</div>
												<div class="panel-body">
												<div class="panel panel-default">
													<div class="panel-body">
														<button type="button" class="btn btn-default btn-primary" id="add-customer-level-btn">添加客户级别</button>
													</div>
												</div>
												<div class="panel panel-default">
													<div class="panel-body">
														<div id="item-div" style="margin: 20px">
															<%@ include file="customer_level_table.jsp"%>
														</div>
													</div>
												</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-customer-level-edit" class="tab-pane">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														客户级别信息
													</h3>
												</div>
												<div class="panel-body">
													<form class="form-horizontal" id="save-form">
														<input type="hidden" id="level-id" name="id" value="" />
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">客户类型</label>
															<div class="col-sm-10" id="">
																<select class="form-control" id="customer-type-id" name="customerTypeId">
																	<c:forEach items="${customerTypeList}" var="type">
																		<option value="${type.id}">${type.typeName}</option>
																	</c:forEach>	
																</select>
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">名称</label>
															<div class="col-sm-10">
																<input type="text" id="level-name" name="levelName"
																	class="form-control" placeholder="名称" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-2 control-label">编码</label>
															<div class="col-md-10">
																<input type="text" id="level-code" name="levelCode"
																	class="form-control" placeholder="编码" />
															</div>
														</div>
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">备注</label>
															<div class="col-sm-10">
																<input type="text" id="level-comment" name="comment"
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
		<script type="text/javascript" src="static/js/customer_level.js"></script>
</body>
</html>
