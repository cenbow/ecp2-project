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

<link rel="shortcut icon" href="pages/favicon.ico">

</head>

<body>


	<div style="width:100%;height:90%;text-align:center;">
		<h2>WELCOME</h2>
		<h3><b>【&nbsp;${user.nickname}&nbsp;】</b>登录菱美电子商务平台！</h3>
	</div>

</body>
</html>