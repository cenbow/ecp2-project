<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>- 主页示例</title>
<%@ include file="../common/headCss.jsp"%>
<link rel="stylesheet"
	href="pages/shopdmp/lib/ztree/css/metroStyle/metroStyle.css"
	type="text/css">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="tabs-container" id="tabs-243687">
						<ul class="nav nav-tabs" id="top_tab">
							<li class="active" onclick="javascript:updateTabTitle();"><a data-toggle="tab" href="#tab-1"
								aria-expanded="true">组织关系列表</a></li>
							<li class="" onclick="javascript:resetFun();"><a data-toggle="tab" id="edit-tab" href="#tab-2"
								aria-expanded="false">新增</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<div class="card-content">
									<div class="table-responsive"
										style="background: #fff; margin-top: 30px; border: 1px solid #e5e5e5;">
										<div id="dataTables-example_wrapper"
											class="dataTables_wrapper form-inline" role="grid">
											<div class="modal-header">
												<div class="col-xs-10  clearfix">
													<form class="form-horizontal" role="form" id="search-form">
														<div class="form-group fl " style="width: 100%">
															<select class="dropdown fl" id="filter-cond-name"
																style="border: 1px solid #cfdadd; height: 30px; font-size: 14px;"
																tabindex="7">
																<option value="organizeName">组织名称</option>
															</select> <input type="text" style="width: 360px;"
																class="form-control" id="filter-cond-val" 
																placeholder="请输入关键字">
															<button type="button" class="btn btn-primary"
																id="filter-submit-btn">查询</button>
														</div>
													</form>
												</div>
											</div>

											<div class="panel-body" id="item-div">
												<%@ include file="organizationManageTable.jsp" %>
											</div>
										</div>
									</div>

								</div>

							</div>
							<div id="tab-2" class="tab-pane">

								<div class="panel-body">
									<form class="form-horizontal" id="save-form" method="post">
										<input type="hidden" id="organize-id" name="organizeId" value="" />
										<div class="form-group">
											<label class="col-sm-2 control-label">组织名称</label>
											<div class="col-sm-10">
												<input type="text" class="form-control" id="organize-name" name="organizeName" value="" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">上级组织</label>
											<div class="col-sm-10">
												<select class="form-control" id="p-id" name="pid">
													<option value="0">请选择</option>
													<c:forEach items="${organizeRootList}" var="organize">
														<option value="${organize.organizeId}">${organize.organizeName}</option>
													</c:forEach>
												</select>
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

		<%@ include file="../common/headJs.jsp"%>
		<script type="text/javascript" src="static/js/organizeManage.js"></script>

		<!-- <script src="pages/shopdmp/js/bootstrap.min.js?v=3.3.6"></script>
		<script type="text/javascript"
			src="pages/shopdmp/lib/ztree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript"
			src="pages/shopdmp/lib/ztree/js/jquery.ztree.excheck.js"></script>
		<SCRIPT type="text/javascript">
			var setting = {
				check : {
					enable : true
				},
				data : {
					simpleData : {
						enable : true
					}
				}
			};
			var zNodes = [ {
				id : 1,
				pId : 0,
				name : "功能管理",
				open : true
			}, {
				id : 2,
				pId : 1,
				name : "会员管理",
				open : true
			},

			{
				id : 6,
				pId : 0,
				name : "报表",
				open : true
			}, {
				id : 7,
				pId : 6,
				name : "用户画像",
				open : true
			},

			{
				id : 20,
				pId : 0,
				name : "配置管理",
				open : true
			}, {
				id : 21,
				pId : 20,
				name : "渠道管理",
				open : true
			}, {
				id : 22,
				pId : 0,
				name : "中诚动力",
				open : true
			}, {
				id : 23,
				pId : 22,
				name : "技术部",
				open : true
			}, {
				id : 24,
				pId : 22,
				name : "UI设计",
				open : true
			}, {
				id : 25,
				pId : 22,
				name : "UE设计",
				open : true
			}, {
				id : 26,
				pId : 22,
				name : "销售部",
				open : true
			},

			];
			//$(document).ready(function() {
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			//});
		</SCRIPT> -->
</body>
</html>