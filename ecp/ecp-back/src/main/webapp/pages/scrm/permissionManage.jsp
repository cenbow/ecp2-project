<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>权限管理</title>
<%@ include file="../common/headCss.jsp"%>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="tabs-container" id="tabs-243687">
						<ul class="nav nav-tabs" id="top_tab">
							<li class="active" onclick="javascript:updateTabTitle();"><a data-toggle="tab" href="#tab-1"
								aria-expanded="true">权限列表</a></li>
							<li class="" onclick="javascript:resetFun();"><a data-toggle="tab" id="edit-tab" href="#tab-2"
								aria-expanded="false">新增权限</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<div class="card-content">
									<div class="table-responsive"
										style="background: #fff; margin-top: 10px; border: 1px solid #e5e5e5;">
										<div id="dataTables-example_wrapper"
											class="dataTables_wrapper form-inline" role="grid">
											<div id="item-div" style="margin: 20px">
												<%@ include file="permissionManageTable.jsp" %>
											</div>
											
										</div>

									</div>

								</div>
							</div>
							<div id="tab-2" class="tab-pane">

								<div class="tabs-container" style="margin-top: 20px;">
									<div class="modal-dialog" style="width: 100%;">
										<div class="modal-content animated fadeIn">
											<div class="modal-header">
												<h4 class="modal-title">权限信息</h4>
											</div>
											<div class="modal-body">
												<form class="form-horizontal" id="save-form">
													<input type="hidden" id="permission-id" name="permissionId" value="" />
													<div class="form-group">
														<label class="col-sm-2 control-label">权限名称</label>

														<div class="col-sm-10">
															<input type="text" id="permission-name" name="permissionName" class="form-control">
														</div>
													</div>
													<div class="form-group">
														<label class="col-sm-2 control-label">权限描述</label>

														<div class="col-sm-10">
															<input type="text" id="permission-des" name="permissionDes" class="form-control">
														</div>
													</div>
													<div class="form-group">
														<label class="col-sm-2 control-label">&nbsp;</label>
														<div class="col-sm-10">
															<button type="button" class="btn btn-primary" id="save-submit-btn">保存</button>
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

		<%@ include file="../common/headJs.jsp"%>
		<script type="text/javascript" src="static/js/permissionManage.js"></script>
		
</body>
</html>