<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>推荐管理</title>
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
								aria-expanded="true">推荐列表</a></li>
							<li class="hide" id="edit-recommend-li"><a
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
														推荐列表
													</h3>
												</div>
												<div class="panel-body">
												<div class="panel panel-default">
													<div class="panel-body">
														<button type="button" class="btn btn-default btn-primary" id="add-recommend-btn">添加推荐</button>
													</div>
												</div>
												<div class="panel panel-default">
													<div class="panel-body">
														<div id="item-div" style="margin: 20px">
															<%@ include file="recommend_table.jsp"%>
														</div>
													</div>
												</div>
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
														推荐信息
													</h3>
												</div>
												<div class="panel-body">
													<form class="form-horizontal" id="save-form">
														<input type="hidden" id="recommend-id" name="id" value="" />
														<div class="form-group">
															<label class="col-sm-2 control-label">名称<b style="color:red;">&nbsp;*</b></label>
															<div class="col-sm-10">
																<input type="text" id="recommend-name" name="name"
																	class="form-control" placeholder="名称" />
															</div>
														</div>
														<div class="form-group" id="recommend-type">
															<label for="name" class="col-sm-2 control-label">类型<b style="color:red;">&nbsp;*</b></label>
															<div class="col-sm-3">
																<label onclick="javascript:selectType(2);"><input type="radio" name="type" value="2" checked="checked" />&nbsp;&nbsp;类目</label>
															</div>
															<div class="col-sm-3">
																<label onclick="javascript:selectType(1);"><input type="radio" name="type" value="1" />&nbsp;&nbsp;商品</label>
															</div>
														</div>
														<div class="form-group" id="recommend-showed">
															<label class="col-md-2 control-label">是否显示<b style="color:red;">&nbsp;*</b></label>
															<div class="col-sm-3">
																<label><input type="radio" name="showed" value="1" checked="checked" />&nbsp;&nbsp;是</label>
															</div>
															<div class="col-sm-3">
																<label><input type="radio" name="showed" value="2" />&nbsp;&nbsp;否</label>
															</div>
														</div>
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">URL<b style="color:red;">&nbsp;*</b></label>
															<div class="col-sm-5">
																<input type="text" class="form-control" id="recommend-url" name="url" value="" placeholder="链接" readonly="readonly" />
															</div>
															<div class="col-sm-5" id="recommend-category">
																<select class="form-control" id="catetory-select">
																	<option value="0">请选择类目</option>
																	<c:forEach items="${categoryList}" var="category">
																		<c:if test="${category.lev==1}">
																			<option value="${category.cid}" level="${category.lev}">&nbsp;&nbsp;┏&nbsp;&nbsp;${category.cName}</option>
																		</c:if>
																		<c:if test="${category.lev==2}">
																			<option value="${category.cid}" level="${category.lev}">&nbsp;&nbsp;&nbsp;&nbsp;┠&nbsp;&nbsp;${category.cName}</option>
																		</c:if>
																		<c:if test="${category.lev==3}">
																			<option value="${category.cid}" level="${category.lev}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┗ &nbsp;&nbsp;${category.cName}</option>
																		</c:if>
																		<c:if test="${category.lev!=1 && category.lev!=2 && category.lev!=3}">
																			<option value="${category.cid}" level="${category.lev}">${category.cName}</option>
																		</c:if>
																	</c:forEach>
																</select>
															</div>
															<div class="col-sm-5 hide" id="recommend-item">
																<button type="button" class="btn btn-primary" title="选择商品" id="selected-item-btn" onclick="javascript:openItemDialog();">选择商品&nbsp;&nbsp;&nbsp;》</button>
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
		<script type="text/javascript" src="static/js/recommend.js"></script>
		
		<%@ include file="../common/item_dialog.jsp"%><!-- 商品对话框 -->
</body>
</html>
