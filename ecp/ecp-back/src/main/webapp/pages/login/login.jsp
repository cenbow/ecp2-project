<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<title>- 登录</title>
<meta name="keywords" content="">
<meta name="description" content="">
<link href="pages/css/bootstrap.min.css" rel="stylesheet">
<link href="pages/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="pages/css/animate.css" rel="stylesheet">
<link href="pages/css/style.css" rel="stylesheet">
<link href="pages/css/login.css" rel="stylesheet">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<script>
	if (window.top !== window.self) {
		window.top.location = window.location;
	}
</script>

</head>

<body class="signin">
	<div class="signinpanel">
		<div class="row">
			<div class="col-sm-12">
				<form id="login_form" action="login" method="post">
					<h4 class="no-margins">登录</h4>
					<p class="m-t-md">登录到...</p>
					<input type="text" class="form-control uname" id="username" name="" placeholder="用户名" />
					<input type="password" class="form-control pword m-b" id="password" name="" placeholder="密码" />
					<input type="hidden" id="public-key" value="${publicKey}" />
					<input type="hidden" id="encrypt-username" name="username" placeholder="加密后的用户名" />
					<input type="hidden" id="encrypt-password" name="password" placeholder="加密后的密码" />
					<!-- <a href="">忘记密码了？</a> -->
					<div style="position:relative;">
						<input id="kaptcha" value="" name="kaptcha_code" type="text" class="form-control x164 in" placeholder="请输入验证码" /> 
						<img  id="kaptchaImage" class="" style="position:absolute;right:1;top:1.5;" onclick="javascript:changeKaptchaImageFun();" alt="点击更换" title="看不清，点击换一张" src="back/getValidateCode" class="m">
					</div>
					<button type="button" class="btn btn-success btn-block" id="submit_btn">登录</button>
				</form>
			</div>
		</div>
		<div class="signup-footer">
			<div class="pull-left">&copy; cheerue</div>
		</div>
	</div>
	<%@ include file="../common/headJs.jsp"%>
	<!-- jsencrypt JS前端加密 -->
	<script type="text/javascript" src="static/js/jsencrypt/jsencrypt.min.js"></script>
	<script type="text/javascript" src="static/js/login.js"></script>
	<script type="text/javascript">
		/*
		 * 改变验证码
		 */
		function changeKaptchaImageFun() {
			console.log("改变验证码");
			$('#kaptchaImage').attr('src', 'back/getValidateCode?' + Math.floor(Math.random() * 100));
			//$('#kaptchaImage').attr('src', 'back/getOperationValidateCode?' + Math.floor(Math.random() * 100));
		}
		
		$(function(){
			var error = "${error_msg}";
			console.log(error);
			if(error!=null && error!=""){
				util.message(error);
			}
		});
		
	</script>
</body>
</html>