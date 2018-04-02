<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>提成配置</title>
<%@ include file="../../../common/headCss.jsp"%>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="tabs-container" id="tabs-pushmoney-config">
						<ul class="nav nav-tabs" id="top_tab">
							<li class="active" onclick="javascript:resetFun();"><a data-toggle="tab" href="#tab-pushmoney-config-item"
								aria-expanded="true">提成配置列表</a></li>
							<li class="hide" id="edit-pushmoney-config-li"><a
								data-toggle="tab" href="#tab-pushmoney-config-edit" aria-expanded="false">添加/编辑</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-pushmoney-config-item" class="tab-pane active">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														提成配置列表
													</h3>
												</div>
												<div class="panel-body">
												<div class="panel panel-default">
													<div class="panel-body">
														<button type="button" class="btn btn-default btn-primary" id="add-pushmoney-config-btn">添加提成配置</button>
													</div>
												</div>
												<div class="panel panel-default">
													<div class="panel-body">
														<div id="item-div" style="margin: 20px">
															<%@ include file="pushmoney_config_table.jsp"%>
														</div>
													</div>
												</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-pushmoney-config-edit" class="tab-pane">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														提成配置信息
													</h3>
												</div>
												<div class="panel-body">
													<form class="form-horizontal" id="save-form">
														<input type="hidden" id="config-id" name="id" value="" />
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">角色</label>
															<div class="col-sm-10" id="">
																<select class="form-control" id="role-id" name="roleId">
																	<c:forEach items="${roleList}" var="role">
																		<option value="${role.roleId}">${role.roleName}</option>
																	</c:forEach>	
																</select>
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">销售额完成比例（%）</label>
															<div class="col-sm-10">
																<input type="text" id="config-completion-rate" name="completionRate"
																	class="form-control" placeholder="销售额完成比例（%）" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-2 control-label">提成比例（%）</label>
															<div class="col-md-10">
																<input type="text" id="config-pushmoney-rate" name="pushmoneyRate"
																	class="form-control" placeholder="提成比例（%）" />
															</div>
														</div>
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">备注</label>
															<div class="col-sm-10">
																<input type="text" id="config-comment" name="comment"
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
		<script type="text/javascript" src="static/js/pushmoney_config.js"></script>
</body>
</html>
