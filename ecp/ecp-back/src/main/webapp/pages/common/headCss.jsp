<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>菱美信息电子商务</title>
<meta name="keywords" content="">
<meta name="description" content="">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<link href="<%=basePath%>/pages/css/bootstrap.min.css?v=3.3.6"
	rel="stylesheet">
<link href="pages/css/font-awesome.min.css?v=4.4.0"
	rel="stylesheet">
<link href="pages/css/animate.css" rel="stylesheet">
