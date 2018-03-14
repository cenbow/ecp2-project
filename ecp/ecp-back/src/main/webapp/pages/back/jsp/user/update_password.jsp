<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改密码</title>
<%@ include file="../../../common/headCss.jsp"%>
</head>
<body class="gray-bg">
	<div class="container-fluid" style="margin-top: 20px;">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">修改密码</h3>
					</div>
					<div class="panel-body">
						<form class="form-horizontal" id="update-form">
							<div class="form-group">
								<label class="col-sm-2 control-label">原密码</label>
								<div class="col-sm-10">
									<input type="text" id="password" name="password"
										class="form-control" placeholder="原密码" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label">新密码</label>
								<div class="col-md-10">
									<input type="text" id="new-password" name="newPassword"
										class="form-control" placeholder="新密码" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label">确认新密码</label>
								<div class="col-md-10">
									<input type="text" id="re-new-password" name="reNewPassword"
										class="form-control" placeholder="确认新密码" />
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
	<%@ include file="../../../common/headJs.jsp"%>
	<script type="text/javascript" src="static/js/update_password.js"></script>
</body>
</html>
