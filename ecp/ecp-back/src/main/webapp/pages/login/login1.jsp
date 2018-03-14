<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>登录</title>

<!-- CSS -->
<link rel="stylesheet" href="pages/front/assets/css/supersized.css">
<link rel="stylesheet" href="pages/front/assets/css/login.css">
<link href="pages/front/assets/css/bootstrap.min.css" rel="stylesheet">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	<script src="assets/loginjs/html5.js"></script>
<![endif]-->
<script src="pages/front/assets/loginjs/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="pages/front/assets/loginjs/jquery.form.js"></script>
<script type="text/javascript" src="pages/front/assets/loginjs/tooltips.js"></script>
<script type="text/javascript" src="pages/front/assets/loginjs/login.js"></script>
<!-- 公共js -->
<script type="text/javascript" src="static/js/common/ajaxSetup.js"></script>

</head>
<body>
	<div class="page-container">
		<div class="main_box">
			<div class="login_box">
				<div class="login_logo">
					<label for="j_title" class="tt">SCRM <!-- <img src="assets/images/logo.png"> -->
				</div>

				<div class="login_form">
					<form action="form.html" id="login_form" method="post">
						<div class="form-group">
							<label for="username" class="t">登录名：</label> 
							<input id="username" value="" type="text" class="form-control x319 in" placeholder="请输入登录名" />
						</div>
						<div class="form-group">
							<label for="password" class="t">密&nbsp;&nbsp;&nbsp;&nbsp;码1：</label> 
							<input id="password" value="" type="password" class="password form-control x319 in" placeholder="请输入密码" />
						</div>
						<div class="form-group">
							<label for="kaptcha" class="t">验证码：</label> 
							<input id="kaptcha" value="" name="j_captcha" type="text" class="form-control x164 in" placeholder="请输入验证码" /> 
							<img id="kaptchaImage" onclick="javascript:changeKaptchaImageFun();" alt="点击更换" title="看不清，点击换一张" src="kaptcha.jpg" class="m">
						</div>
						<!--<div class="form-group">
						<label class="t"></label>
						<label for="j_remember" class="m">
						<input id="j_remember" type="checkbox" value="true">&nbsp;记住登陆账号!</label>
					</div>
					-->
						<div class="form-group space">
							<label class="t"></label>
							<button type="button" id="submit_btn" class="btn btn-primary btn-lg">&nbsp;登&nbsp;录&nbsp</button>
							<input type="reset" value="&nbsp;重&nbsp;置&nbsp;" class="btn btn-default btn-lg">
						</div>
					</form>
				</div>
			</div>
			<!-- <div class="bottom">Copyright &copy; 2017  <a href="#">系统登陆</a></div>  -->
		</div>
	</div>

	<!-- Javascript -->
	<script src="pages/front/assets/loginjs/supersized.3.2.7.min.js"></script>
	<script src="pages/front/assets/loginjs/supersized-init.js"></script>
	<script src="pages/front/assets/loginjs/scripts.js"></script>
	<script type="text/javascript">
		/*
		 * 改变验证码
		 */
		function changeKaptchaImageFun() {
			console.log("改变验证码");
			$('#kaptchaImage').attr('src', 'kaptcha.jpg?' + Math.floor(Math.random() * 100));
		}
	</script>

</body>
</html>