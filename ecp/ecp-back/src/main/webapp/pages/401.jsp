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
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>- 401 页面</title>
<meta name="keywords" content="">
<meta name="description" content="">

<link rel="shortcut icon" href="favicon.ico">
<link href="pages/shopdmp/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="pages/shopdmp/css/font-awesome.css?v=4.4.0" rel="stylesheet">

<link href="pages/shopdmp/css/animate.css" rel="stylesheet">
<link href="pages/shopdmp/css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">


	<div class="middle-box text-center animated fadeInDown">
		<h1>401</h1>
		<h3 class="font-bold">未授权！</h3>

		<div class="error-desc">
			请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。
			<!-- <form class="form-inline m-t" role="form">
				<div class="form-group">
					<input type="email" class="form-control"
						placeholder="请输入您需要查找的内容 …">
				</div>
				<button type="submit" class="btn btn-primary">搜索</button>
			</form> -->
		</div>
	</div>

	<!-- 全局js -->
	<script src="pages/shopdmp/js/jquery.min.js?v=2.1.4"></script>
	<script src="pages/shopdmp/js/bootstrap.min.js?v=3.3.6"></script>

</body>
</html>