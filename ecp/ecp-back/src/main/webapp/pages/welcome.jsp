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

<title>welcome</title>
<meta name="keywords" content="">
<meta name="description" content="">

<link rel="shortcut icon" href="favicon.ico">
<link href="pages/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="pages/css/font-awesome.css?v=4.4.0" rel="stylesheet">

<link href="pages/css/animate.css" rel="stylesheet">
<link href="pages/css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">


	<div class="middle-box text-center animated fadeInDown">
		<h2>WELCOME</h2>
		<h3 class="font-bold">WELCOME!</h3>
	</div>

	<!-- 全局js -->
	<script src="pages/js/jquery.min.js?v=2.1.4"></script>
	<script src="pages/js/bootstrap.min.js?v=3.3.6"></script>

</body>
</html>