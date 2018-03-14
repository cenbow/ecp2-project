<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>公众号配置</title>
<%@ include file="../common/headCss.jsp"%>
</head>
<body>
	<br>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">公众号配置</h3>
					</div>
					<div class="panel-body">
						<div class="row clearfix">
							<div class="col-md-12 column">
								<form class="form-horizontal" role="form">
									<div class="form-group">
										<label for="inputEmail3" class="col-sm-2 control-label">AppID(应用ID)</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="app-id" placeholder="AppID(应用ID)" />
										</div>
									</div>
									<div class="form-group">
										<label for="inputPassword3" class="col-sm-2 control-label">AppSecret(应用密钥)</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="app-secret" placeholder="AppSecret(应用密钥)" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-sm-offset-2 col-sm-10">
											<button type="button" class="btn btn-primary">保存</button>
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

</body>
</html>