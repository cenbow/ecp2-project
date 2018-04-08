<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <link rel="shortcut icon" href="pages/favicon.ico"> -->
<%@ include file="common/headCss.jsp"%>
<link href="static/css/main.css" rel="stylesheet">
<script type="text/javascript">
/*
 * 点击左侧菜单时，在右侧iframe中加载相关内容
 */
function iframeLoading(href){
	util.loading();
	$("#J_iframe").attr("src", href);
	//iframe加载完毕以后执行的事件
	$("#J_iframe").load(function(){  
		console.log('加载完毕');
		util.loaded();
	});
}
/*
 * 点击左侧菜单时，在右侧iframe中加载相关内容完成后执行
 */
function iframeLoaded(){
	window.frames['J_iframe'].iframeLoaded();
}

</script>
<style type="text/css">
body {
     overflow-x : hidden;   /* 去掉横条 */
     overflow-y : auto;   /* 去掉竖条 */
}
</style>
</head>

<body style="overflow: hidden">
	<!--左侧导航部分-->
	<%@ include file="leftmenu.jsp" %>
    <div class="rightSide">
        <div class="navbar navbar-default navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-collapse collapse" role="navigation">
                    <!-- <ul class="nav navbar-nav">
                        <li><a href="#">-</a></li>
                    </ul> -->
                    <div class="navbar-form navbar-left">
                        <button class="btn btn-xs btn-default"> = </button>
                    </div>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-user fa-fw"></i> <b>${user.nickname}</b><strong class="caret"></strong></a>
                            <ul class="dropdown-menu">
                                <li><a href="javascript:void(0);" onclick="javascript:iframeLoading('pages/back/jsp/user/update_password.jsp');">修改密码</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="logout">退出登录</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="mainContent">
				<iframe  id="J_iframe" name="J_iframe" src="pages/welcome.jsp" width="100%" height="100%" scrolling="yes"
					frameborder="0" seamless></iframe>
        </div>
    </div>

	<%@ include file="common/headJs.jsp"%>
	<script src="//cdn.bootcss.com/jQuery-slimScroll/1.3.6/jquery.slimscroll.min.js"></script>
	<script type="text/javascript">
		var leftHeight = 0;
		//固定菜单栏
		$(document).ready(function () {
			leftHeight = $(window).height();
			console.log("height:"+leftHeight);
			$(".leftSide .panel-group").css("height", leftHeight-50);
			setScroll();
		});
		
		/**
		 * 初始化滚动条
		 */
		function setScroll(){
			console.log("setScroll");
			$(".leftSide .panel-group").slimScroll({
				width: 'auto', //可滚动区域宽度
		        height: leftHeight-50, //可滚动区域高度
		        size: '10px', //组件宽度
		        color: '#000', //滚动条颜色
		        position: 'right', //组件位置：left/right
		        distance: '0px', //组件与侧边之间的距离
		        start: 'top', //默认滚动位置：top/bottom
		        opacity: .4, //滚动条透明度
		        alwaysVisible: false, //是否 始终显示组件
		        disableFadeOut: true, //是否 鼠标经过可滚动区域时显示组件，离开时隐藏组件
		        railVisible: true, //是否 显示轨道
		        railColor: '#333', //轨道颜色
		        railOpacity: .2, //轨道透明度
		        railDraggable: true, //是否 滚动条可拖动
		        railClass: 'slimScrollRail', //轨道div类名 
		        barClass: 'slimScrollBar', //滚动条div类名
		        wrapperClass: 'slimScrollDiv', //外包div类名
		        allowPageScroll: false, //是否 使用滚轮到达顶端/底端时，滚动窗口
		        wheelStep: 20, //滚轮滚动量
		        touchScrollStep: 200, //滚动量当用户使用手势
		        borderRadius: '7px', //滚动条圆角
		        railBorderRadius: '7px' //轨道圆角 
			});
		}
		
		$(window).on("resize",setScroll);
	</script>
</body>
</html>