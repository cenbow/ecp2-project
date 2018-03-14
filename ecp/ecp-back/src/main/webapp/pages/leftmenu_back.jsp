<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!--左侧导航开始-->
<nav class="navbar-default navbar-static-side" role="navigation">
	<div class="nav-close">
		<i class="fa fa-times-circle"></i>
	</div>
	<div class="sidebar-collapse">
		<ul class="nav" id="side-menu">

			<li class="nav-header">
				<div class="dropdown profile-element">
					<a data-toggle="dropdown" class="dropdown-toggle"
						href="javascript:void(0);"> <span class="clear"> <span
							class="block m-t-xs" style="font-size: 18px;"><img alt="凌美" src="static/images/lm.jpg" style="height:28px;" /> <strong class="font-bold">电子商务平台</strong>
						</span>
					</span>
					</a>
				</div>
				<div class="logo-element"><img alt="凌美信息" src="static/images/lm.jpg" style="height:28px;" /></div>
			</li>
			<li class="line dk"></li>

			<c:forEach items="${menuList}" var="menu">
				<c:if test="${menu.parentId==0}">
					<li class="hidden-folded padder m-t m-b-sm text-muted text-xs" style="width:100%;margin:0 0;">
						<a><i class="${menu.menuIcon}"></i> <span class="ng-scope">${menu.menuName}</span></a>
					</li>
					<li class="line dk"></li>
					<c:forEach items="${menuList}" var="menuChild">
						<c:if test="${menuChild.parentId!=0 && menu.menuId==menuChild.parentId}">
							<li><a class="J_menuItem" href="javascript:void(0);"
								title="${menuChild.menuName}"
								onclick="javascript:iframeLoading('${menuChild.menuUrl}');">
									<i class="${menuChild.menuIcon}"></i> <span class="nav-label">${menuChild.menuName}</span>
							</a></li>
							<li class="line dk"></li>
						</c:if>
					</c:forEach>
				</c:if>
			</c:forEach>

			

			
















			<!-- <li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
				<span class="ng-scope">功能管理</span>
			</li>
			<li class="line dk"></li>

			<li><a class="J_menuItem" href="javascript:void(0);"
				title="商品管理"
				onclick="javascript:iframeLoading('back/item/addItem');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">添加商品</span>
			</a></li>
			<li class="line dk"></li>

			<li><a class="J_menuItem" href="javascript:void(0);"
				title="商品管理"
				onclick="javascript:iframeLoading('back/item/selectItems?pagehelperFun=clickPageBtnRequestFun');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">商品管理</span>
			</a></li>

			<li class="line dk"></li>
			<li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
				<span class="ng-scope">类目</span>
			</li>
			<li class="line dk"></li>
			<li><a class="J_menuItem" href="javascript:void(0);"
				title="类目"
				onclick="javascript:iframeLoading('back/category/selectItems');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">类目</span>
			</a></li>
			<li class="line dk"></li>
			<li><a class="J_menuItem" href="javascript:void(0);"
				title="类目属性"
				onclick="javascript:iframeLoading('back/attr/categorytree');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">类目属性</span>
			</a></li>
			<li class="line dk"></li>
			<li><a class="J_menuItem" href="javascript:void(0);"
				title="类目品牌"
				onclick="javascript:iframeLoading('back/category-brand/getCategotyItems');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">类目品牌</span>
			</a></li>
			<li class="line dk"></li>
			<li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
				<span class="ng-scope">配置管理</span>
			</li>
			<li class="line dk"></li>

			<li><a class="J_menuItem" href="javascript:void(0);"
				title="品牌管理"
				onclick="javascript:iframeLoading('back/brand/selectItems?pagehelperFun=clickPageBtnRequestFun');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">品牌管理</span>
			</a></li>
			<li class="line dk"></li>
			
			<li><a class="J_menuItem" href="javascript:void(0);"
				title="用户管理"
				onclick="javascript:iframeLoading('back/user/selectItems?pagehelperFun=clickPageBtnRequestFun');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">用户管理</span>
			</a></li>
			<li class="line dk"></li>
			
			<li><a class="J_menuItem" href="javascript:void(0);"
				title="角色管理"
				onclick="javascript:iframeLoading('back/role/selectItems?pagehelperFun=clickPageBtnRequestFun');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">角色管理</span>
			</a></li>
			<li class="line dk"></li>
			
			<li><a class="J_menuItem" href="javascript:void(0);"
				title="菜单管理"
				onclick="javascript:iframeLoading('back/menu/selectItems');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">菜单管理</span>
			</a></li>
			<li class="line dk"></li> -->

		</ul>
	</div>
</nav>
<!--左侧导航结束-->
