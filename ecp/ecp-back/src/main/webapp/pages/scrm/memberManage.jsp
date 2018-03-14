<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>- 主页示例</title>
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
								aria-expanded="true"> 会员列表</a></li>
							<li class="" onclick="javascript:resetFun();"><a data-toggle="tab" id="edit-tab" href="#tab-2"
								aria-expanded="false">新增会员</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<div class="card-content">
									<div class="table-responsive"
										style="background: #fff; margin-top: 10px; border: 1px solid #e5e5e5;">
										<div id="dataTables-example_wrapper"
											class="dataTables_wrapper form-inline" role="grid">
											<div class="modal-header">
												<!-- -->
												<div class="col-xs-10  clearfix">
													<form class="form-horizontal" role="form" id="search-form">
														<div class="form-group fl" style="width: 100%">
															<div>
																<select class="dropdown fl" id="filter-cond-name"
																	style="border: 1px solid #cfdadd; height: 30px; font-size: 14px;"
																	tabindex="7">
																	<option value="" selected>全部</option>
																	<option value="member_id">id</option>
																	<option value="username">用户名</option>
																	<option value="phone">手机</option>
																	<!-- <option value="level">会员等级</option> -->
																	<option value="email">电子邮箱</option>

																</select> <input type="text" style="width: 260px;"
																	class="form-control" id="filter-cond-val" 
																	placeholder="请输入关键字">
																<button type="button" class="btn btn-primary"
																	id="filter-submit-btn">查询</button>
																<button type="button" class="btn btn-default"
																	id="search-submit-btn">统计结果 : <b id="filter-total-s">${pagehelper.total}</b></button>
																<button type="button" class="btn btn-primary"
																	id="search-submit-btn" onclick="javascript:batchDeleteInfoFun();">放入垃圾站</button>
																<button type="button" class="btn btn-primary"
																	id="search-submit-btn">导入</button>
																<button type="button" class="btn btn-primary"
																	id="search-submit-btn">导出</button>

															</div>

														</div>
													</form>
												</div>
											</div>

											<div id="item-div" style="margin: 20px">
												<%@ include file="memberManageTable.jsp" %>
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
												<h4 class="modal-title">会员信息</h4>
											</div>
											<div class="modal-body">
												<form class="form-horizontal" id="save-form">
													<input type="hidden" id="member-id" name="memberId" value="" />
													<div class="form-group">
														<label class="col-sm-2 control-label">用户名</label>

														<div class="col-sm-10">
															<input type="text" id="user-name" name="username" class="form-control">
														</div>
													</div>
													<div class="form-group">
														<label class="col-sm-2 control-label">昵称</label>

														<div class="col-sm-10">
															<input type="text" id="nick-name" name="nickName" class="form-control">
														</div>
													</div>
													<div class="form-group">
														<label class="col-sm-2 control-label">手机</label>

														<div class="col-sm-10">
															<input type="tel" class="form-control" id="phone" name="phone" />
														</div>
													</div>
													<div class="form-group">
														<label class="col-sm-2 control-label">会员等级</label>
														<div class="col-sm-10">
															<select class="form-control" id="level-id" name="levelId">
																<c:forEach items="${levelList}" var="level">
																	<option value="${level.levelId}">${level.levelName}</option>
																</c:forEach>
																<!-- <option>普通会员</option>
																<option>白金会员</option>
																<option>黄金会员</option>
																<option>钻石vip会员</option> -->
															</select>
														</div>
													</div>
													<div class="form-group">
														<label class="col-sm-2 control-label">会员来源</label>
														<div class="col-sm-10">
															<select class="form-control" id="organize-id" name="organizeId">
																<!-- <option>总店</option>
																<option>分店</option>
																<option>关注微信</option> -->
																<c:forEach items="${organizeList}" var="organize">
																	<option value="${organize.organizeId}">${organize.organizeName}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													<div class="form-group">
														<label class="col-sm-2 control-label">电子邮箱</label>

														<div class="col-sm-10">
															<input type="email" class="form-control" id="email" name="email" />
														</div>
													</div>
													<div class="form-group">
														<label class="col-sm-2 control-label">城市</label>

														<div class="col-sm-10">
															<input type="text" class="form-control" id="city" name="city" />
														</div>
													</div>
													<!-- <div class="form-group">
														<label class="col-sm-2 control-label">性别</label>
														<div class="col-sm-10">
															<select class="form-control" name="account">
																<option>普通会员</option>
																<option>白金会员</option>
																<option>黄金会员</option>
																<option>钻石vip会员</option>
															</select>
														</div>
													</div> -->

													<div class="form-group">
														<label class="col-sm-2 control-label">性别</label>
														<div class="col-sm-10">
															<select class="form-control" id="sex" name="sex">
																<option value="0">男</option>
																<option value="1">女</option>
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
				</div>
			</div>
		</div>

		<%@ include file="../common/headJs.jsp"%>
		<script type="text/javascript" src="static/js/memberManage.js"></script>
		
</body>
</html>