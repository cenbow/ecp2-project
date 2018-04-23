<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>提成配置</title>
<%@ include file="../../../common/headCss.jsp"%>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="container-fluid">
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
								<div class="row clearfix">
									<div class="col-sm-2">
										<button type="button" class="btn btn-default btn-primary" id="add-pushmoney-config-btn">添加提成配置</button>
									</div>
									<div class="col-sm-2">
										<select class="form-control" id="search-role-id" name="" title="角色">
											<option value="">—— 角色 ——</option>
											<c:forEach items="${roleList}" var="role">
												<option value="${role.roleId}">${role.roleName}</option>
											</c:forEach>	
										</select>
									</div>
									<div class="col-md-2 column">
										<button type="button" class="btn btn-default btn-primary" id="search-pushmoney-config-btn">查询</button>
									</div>
								</div>
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
		
<div id="load-edit-dialog-div">
<!-- 此处加载编辑提成比例配置对话框 -->
<%@ include file="edit_pushmoney_config_dialog.jsp"%>
</div>

		<%@ include file="../../../common/headJs.jsp"%>
		<script type="text/javascript" src="static/js/pushmoney_config.js"></script>
</body>
</html>
