<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="pages/shopdmp/favicon.ico">
<%@ include file="common/headCss.jsp"%>
<link href="pages/css/style.css?v=4.1.0" rel="stylesheet">
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
</head>

<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow: hidden">
	<div id="wrapper">
		<!--左侧导航开始-->
		<div id="leftmenu-div">
			<%@ include file="leftmenu.jsp" %>
		</div>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row border-bottom">
				<ul class="nav nav-tabs">
					<li>
						<a class="navbar-minimalize minimalize-styl-2 btn btn-info "
							href="javascript:void(0);"><i class="fa fa-bars"></i> </a>
					</li>
					<li class="dropdown pull-right">
						 <a href="#" data-toggle="dropdown" class="dropdown-toggle"><i class="fa fa-user fa-fw"></i> <b>${user.nickname}</b><strong class="caret"></strong></a>
						<ul class="dropdown-menu">
							<li>
								 <a href="javascript:void(0);" onclick="javascript:iframeLoading('pages/back/jsp/user/update_password.jsp');">修改密码</a>
							</li>
							<li class="divider">
							</li>
							<li>
								 <a href="logout"><i class="fa fa-sign-out fa-fw"></i> 退出</a>
							</li>
						</ul>
					</li>
				</ul>
				<%-- <nav class="navbar navbar-static-top" role="navigation"
					style="margin-bottom: 0">
				<div class="navbar-header">
					<a class="navbar-minimalize minimalize-styl-2 btn btn-info "
						href="javascript:void(0);"><i class="fa fa-bars"></i> </a>
				</div>
				<div class="nav-footer">
					<div class='pull-right' style="font-size:14px;padding:18px 8px 0 0;">
						<a class=" waves-effect waves-dark active" href="javascript:void(0);"><i class="fa fa-user fa-fw"></i> <b>${user.nickname}</b></a>&nbsp;&nbsp;
						<a href="logout"><i class="fa fa-sign-out fa-fw"></i> 退出</a>
					
					</div>	
				</div>	
				
				</nav> --%>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe  id="J_iframe" name="J_iframe" width="100%" height="100%"
					src="pages/welcome.jsp" frameborder="0" seamless></iframe>

			</div>
		</div>
		<!--右侧部分结束-->
	</div>

	<%@ include file="common/headJs.jsp"%>
	
	<script type="text/javascript" src="static/bootstrap/3.3.5/js/bootstrap-switch.min.js"></script>
	<link href="static/bootstrap/3.3.5/css/bootstrap-switch.min.css" rel="stylesheet">
	
	<script type="text/javascript" src="pages/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script type="text/javascript" src="pages/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" id="main-js" src="pages/js/main.js"></script>
	
	<!-- <script type="text/javascript">
		/* 初始化 */
		$("#scrm-ecp-switch").bootstrapSwitch({
			onColor: 'primary',
		    offColor: 'info',
	        onText:'SCRM',  
	        offText:'ECP',
	        onSwitchChange:function(event,state){  
	            if(state==true){  
	                $(this).val("1");
	              	//加载SCRM左侧菜单，加载完成后在右侧iframe中加载welcome.jsp页面
	                util.loading();
	                $("#leftmenu-div").load("pages/scrm/leftmenu.jsp", function(){
	                	iframeLoading("pages/welcome.jsp");
	                });
	            }else{  
	                $(this).val("2");
	                //加载ECP左侧菜单，加载完成后在右侧iframe中加载welcome.jsp页面
	                util.loading();
	                $("#leftmenu-div").load("pages/ecp/leftmenu.jsp", function(){
	                	iframeLoading("pages/welcome.jsp");
	                });
	            }
	            //$("#main-js").attr("src", "pages/shopdmp/js/main.js");//重新加载main.js
	            $.getScript("pages/js/main.js");
	        }
	    });
	</script> -->
</body>
</html>