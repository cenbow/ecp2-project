<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>品牌</title>
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
								aria-expanded="true">品牌列表</a></li>
							<li class="hide" id="edit-brand-li"><a
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
														品牌列表
													</h3>
												</div>
												<div class="panel-body">
												<div class="panel panel-default">
													<div class="panel-body">
														<button type="button" class="btn btn-default btn-primary" id="add-brand-btn">添加品牌</button>
													</div>
												</div>
												<div class="panel panel-default">
													<div class="panel-body">
														<div id="item-div" style="margin: 20px">
															<%@ include file="brandManageTable.jsp"%>
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
													<%@ include file="brandManageTable.jsp"%>
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
														品牌信息
													</h3>
												</div>
												<div class="panel-body">
													<form class="form-horizontal" id="save-form">
														<input type="hidden" id="brand-id" name="brandId" value="" />
														<div class="form-group">
															<label class="col-sm-2 control-label">品牌名称<b style="color:red;">&nbsp;*</b></label>
															<div class="col-sm-10">
																<input type="text" id="brand-name" name="brandName"
																	class="form-control" placeholder="品牌名称" />
															</div>
														</div>
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">&nbsp;</label>
															<div class="col-sm-10"><b style="color:red;">注：请上传&nbsp;102*36&nbsp;的图片！</b></div>
														</div>
														<div class="form-group">
															<label class="col-md-2 control-label">品牌LOGO<b style="color:red;">&nbsp;*</b></label>
															<div class="col-md-5 ">
																<input type="file" id="brand-logo-file" name="logo"
																	onchange="javascript:validateImgFileSizeFun(this);"
																	accept="image/*" /> <input type="hidden"
																	id="brand-logo-url" name="brandLogoUrl" />
															</div>
															<!-- <div class="col-md-5">
																<button type="button" class="btn btn-primary" onclick="javascript:deleteImgFun('logo');">删除</button>
															</div> -->
														</div>
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">&nbsp;</label>
															<div class="col-sm-10" id="logo-img-portrait"></div>
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
		<script type="text/javascript" src="static/js/brandManage.js"></script>
</body>
</html>
