<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>菜单</title>
<%@ include file="../../../common/headCss.jsp"%>
<link rel="stylesheet"
	href="static/ztree/css/metroStyle/metroStyle.css"
	type="text/css">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="tabs-container" id="tabs-243687">
						<ul class="nav nav-tabs" id="top_tab">
							<li class="active" onclick="javascript:resetFun();"><a data-toggle="tab" href="#tab-1"
								aria-expanded="true">菜单列表</a></li>
							<li class="hide" id="edit-menu-li"><a data-toggle="tab" id="edit-tab" href="#tab-2"
								aria-expanded="false">新增/编辑</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<div class="container-fluid"  style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														<button type="button" id="add-btn" class="btn btn-default btn-primary" onclick="javascript:add(0, '默认为根节点', 0);">新增</button>
														<button type="button" id="edit-btn" class="btn btn-default btn-info" onclick="javascript:edit(0);">编辑</button>
														<button type="button" id="del-btn" class="btn btn-default btn-danger" onclick="javascript:del(0);">删除</button>
													</h3>
												</div>
												<div class="panel-body" id="item-div">
													<ul id="menu-ztree" class="ztree"></ul>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-2" class="tab-pane">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														菜单信息
													</h3>
												</div>
												<div class="panel-body">
													<form class="form-horizontal" id="save-form" method="post">
														<input type="hidden" id="menu-id" name="menuId" value="" />
														<div class="form-group">
															<label class="col-sm-2 control-label">名称</label>
															<div class="col-sm-10">
																<input type="text" class="form-control" id="menu-name" name="menuName" value="" placeholder="名称" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">图标样式</label>
															<div class="col-sm-10">
																<input type="text" class="form-control" id="menu-icon" name="menuIcon" value="" placeholder="图标样式" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">描述</label>
															<div class="col-sm-10">
																<input type="text" class="form-control" id="menu-description" name="menuDescription" value="" placeholder="描述" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">上级菜单</label>
															<div class="col-sm-10">
																<select class="form-control" id="parent-id" name="parentId">
																	<option value="0">请选择上级菜单（默认为根节点）</option>
																	<c:forEach items="${menuList}" var="menu">
																		<option value="${menu.menuId}">${menu.menuName}</option>
																	</c:forEach>
																</select>
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">链接</label>
															<div class="col-sm-10">
																<input type="text" class="form-control" id="menu-url" name="menuUrl" value="" placeholder="菜单链接" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">排序</label>
															<div class="col-sm-10">
																<input type="text" class="form-control" id="sort-num" name="sortNum" value="" placeholder="排序" />
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
		</div>

		<%@ include file="../../../common/headJs.jsp"%>
		<script type="text/javascript" src="static/js/menu.js"></script>

		<!-- <script src="pages/ecp/js/bootstrap.min.js?v=3.3.6"></script> -->
		<script type="text/javascript"
			src="static/ztree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript"
			src="static/ztree/js/jquery.ztree.excheck.js"></script>
		<SCRIPT type="text/javascript">
			
			var setting = {
				check: {
					enable: false,
					chkStyle: "checkbox",
				},
				data: {
					key: {
						name: "menuName"
					},
					simpleData: {
						enable:true,
						idKey: "menuId",
						pIdKey: "parentId",
						rootPId: 0
					}
				},
				callback: {
					onClick: zTreeOnClick,//节点被点击的事件回调函数
				},
			};
			
			/*
			 * 节点被点击的事件回调函数
			 */
			function zTreeOnClick(event, treeId, treeNode) {
				console.log("click:"+JSON.stringify(treeNode));
				console.log(treeNode.menuId + ", " + treeNode.menuName + ", " + treeNode.parentId);
			    //$("#add-btn").attr("onclick", "javascript:add(&quot;"+treeNode.id+"&quot;);");
				$("#add-btn").attr("onclick", "javascript:add("+treeNode.menuId+", '"+treeNode.menuName+"', '"+treeNode.parentId+"');");//修改添加按钮click事件
				$("#edit-btn").attr("onclick", "javascript:edit("+treeNode.menuId+", '"+treeNode.menuName+"', '"+treeNode.menuIcon+"', '"+treeNode.menuDescription+"', '"+treeNode.parentId+"', '"+treeNode.menuUrl+"', '"+treeNode.sortNum+"');");//修改编辑按钮click事件
				$("#del-btn").attr("onclick", "javascript:del("+treeNode.menuId+", '"+treeNode.menuName+"', '"+treeNode.parentId+"');");//修改删除按钮click事件
				
				
			}
			
			var zNodes = ${menuListJson};
			console.log("zNodes:"+JSON.stringify(zNodes));
			
			//$(document).ready(function() {
			$.fn.zTree.init($("#menu-ztree"), setting, zNodes);
			
			//});
		</SCRIPT>
</body>
</html>