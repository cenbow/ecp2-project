<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户</title>
<%@ include file="../../../common/headCss.jsp"%>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="tabs-container" id="tabs-243687">
						<ul class="nav nav-tabs" id="top_tab">
							<li class="active" onclick="javascript:resetFun();"><a data-toggle="tab" href="#tab-1"
								aria-expanded="true">用户列表</a></li>
							<li class="hide" id="edit-user-li"><a
								data-toggle="tab" href="#tab-2" aria-expanded="false">添加/编辑</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														用户列表
													</h3>
												</div>
												<div class="panel-body">
												<div class="panel panel-default">
													<div class="panel-body">
														<button type="button" class="btn btn-default btn-primary" id="add-user-btn">添加用户</button>
													</div>
												</div>
												<div class="panel panel-default">
													<div class="panel-body">
														<div id="item-div" style="margin: 20px">
															<%@ include file="user_table.jsp"%>
														</div>
													</div>
												</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<%-- <div class="card-content">
									<div class="table-responsive"
										style="background: #fff; margin-top: 10px; border: 1px solid #e5e5e5;">
										<div id="dataTables-example_wrapper"
											class="dataTables_wrapper form-inline" role="grid">
											<div class="modal-header">
												<div id="item-div" style="margin: 20px">
													<%@ include file="user_table.jsp"%>
												</div>
											</div>
										</div>
									</div>
								</div> --%>
							</div>
							<div id="tab-2" class="tab-pane">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														用户信息
													</h3>
												</div>
												<div class="panel-body">
													<form class="form-horizontal" id="save-form">
														<input type="hidden" id="user-id" name="id" value="" />
														<div class="form-group">
															<label class="col-sm-2 control-label">昵称</label>
															<div class="col-sm-10">
																<input type="text" id="nickname" name="nickname"
																	class="form-control" placeholder="昵称" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-2 control-label">用户名</label>
															<div class="col-md-10">
																<input type="text" id="username" name="username"
																	class="form-control" placeholder="用户名" />
															</div>
														</div>
														<!-- <div class="form-group">
															<label for="name" class="col-sm-2 control-label">密码</label>
															<div class="col-sm-10">
																<input type="password" id="password" name="password"
																	class="form-control" placeholder="密码" />
															</div>
														</div> -->
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">&nbsp;</label>
															<div class="col-sm-10">
																<b style="color:red;">默认密码：123456，首次登录系统请先修改密码！</b>
															</div>
														</div>
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">角色</label>
															<div class="col-sm-10" id="role-item">
																<%-- <ul>
																	<c:forEach items="${roleList}" var="role">
																		<li>
																			<label><input type="checkbox" id="role-${role.roleId}" name="role" value="${role.roleId}" />&nbsp;&nbsp;&nbsp;${role.roleName}</label>
																		</li>
																	</c:forEach>
																</ul> --%>
																<c:forEach items="${roleList}" var="role">
																			<label><input type="checkbox" id="role-${role.roleId}" name="role" value="${role.roleId}" />&nbsp;&nbsp;&nbsp;${role.roleName}</label>
																			<br>
																</c:forEach>
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
		<script type="text/javascript" src="static/js/user.js"></script>
</body>
</html>
