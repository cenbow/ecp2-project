<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!--左侧导航开始-->
<div class="leftSide">
        <div class="navbar-header">
            <a class="navbar-brand" tittle="菱美电子商务" href="javascript:location.reload();">
            	<img alt="菱美LOGO" src="static/images/lm.jpg">
            	<span>电子商务平台</span>
            </a>
        </div>
        <div class="panel-group" style="">
            <!-- 导航列表 -->
            <!-- <div class="panel panel-default sidelist">
                <div class="panel-heading" id="feepayMenu">
                    <h4 class="panel-title" data-parent="#accordion">
                        <a  href="#">
                            <span class="icon iconfont">&#xe627;</span>title without subtitle<span class="pull-right"><i class="icon-chevron-right"></i></span> 
                        </a>
                    </h4>
                </div>
            </div> -->

            <!-- 带二级菜单的导航 -->
            <c:forEach items="${menuList}" var="menu">
            <c:if test="${menu.parentId==0}">
	            <div class="panel panel-default sidelist">
	                <div class="panel-heading">
	                    <h4 class="panel-title" data-toggle="collapse" data-parent="#accordion" href="#collapse01-${menu.menuId}">
	                  <a>
	                    <i class="${menu.menuIcon}"></i>&nbsp;&nbsp;&nbsp;${menu.menuName}<span class="pull-right"><i class="icon-chevron-right"></i></span> 
	                  </a>
	                </h4>
	                </div>
	                
	                <div id="collapse01-${menu.menuId}" class="panel-collapse collapse subtitle">
	                <c:forEach items="${menuList}" var="menuChild">
	                <c:if test="${menuChild.parentId!=0 && menu.menuId==menuChild.parentId}">
	                    <div class="panel-body">
	                        <a href="javascript:void(0);" title="${menuChild.menuName}" onclick="javascript:iframeLoading('${menuChild.menuUrl}');"><i class="${menuChild.menuIcon}"></i>&nbsp;&nbsp;&nbsp;${menuChild.menuName}</a>
	                    </div>
	                    <!-- <div class="panel-body">
	                        <a href="#"><span class="icon iconfont">&#xe627;</span>subtitle 2</a>
	                    </div>
	                    <div class="panel-body">
	                        <a href="#"><span class="icon iconfont">&#xe627;</span>subtitle 3</a>
	                    </div> -->
	                    </c:if>
	                </c:forEach>
	                </div>
	                
	            </div>
	        </c:if>
            </c:forEach>

        </div>
    </div>

<!--左侧导航结束-->
