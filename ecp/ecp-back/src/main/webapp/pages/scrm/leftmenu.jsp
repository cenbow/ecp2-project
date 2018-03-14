<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
							class="block m-t-xs" style="font-size: 20px;"> <i
								class="fa fa-area-chart"></i> <strong class="font-bold">酒庄会员管理</strong>
						</span>
					</span>
					</a>
				</div>
				<div class="logo-element">SCRM</div>
			</li>
			<li class="line dk"></li>

			<li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
				<span class="ng-scope">功能管理</span>
			</li>
			<li class="line dk"></li>

			<li><a class="J_menuItem" href="javascript:void(0);"
				title="商品管理"
				onclick="javascript:iframeLoading('back/product/selectItems?pagehelperFun=clickPageBtnRequestFun');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">商品管理</span>
			</a></li>
			<!-- <li class="line dk"></li>
			<li><a class="J_menuItem" href="javascript:void(0);"
				title="会员等级"
				onclick="javascript:iframeLoading('front/level/selectItem?pagehelperFun=clickPageBtnRequestFun');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">会员等级</span>
			</a></li> -->

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
			<li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
				<span class="ng-scope">配置管理</span>
			</li>
			<li class="line dk"></li>

			<li><a class="J_menuItem" href="javascript:void(0);"
				title="管理员管理"
				onclick="javascript:iframeLoading('front/user/selectItem?pagehelperFun=clickPageBtnRequestFun');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">管理员管理</span>
			</a></li>
			<li class="line dk"></li>

			<li><a class="J_menuItem" href="javascript:void(0);"
				title="角色管理"
				onclick="javascript:iframeLoading('front/role/selectItem?pagehelperFun=clickPageBtnRequestFun');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">角色管理</span>
			</a></li>
			<li class="line dk"></li>

			<li><a class="J_menuItem" href="javascript:void(0);"
				title="权限管理"
				onclick="javascript:iframeLoading('front/permission/selectItem?pagehelperFun=clickPageBtnRequestFun');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">权限管理</span>
			</a></li>
			<li class="line dk"></li>

			<li><a class="J_menuItem" href="javascript:void(0);"
				title="组织管理"
				onclick="javascript:iframeLoading('front/organize/selectItem?pagehelperFun=clickPageBtnRequestFun');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">组织管理</span>
			</a></li>
			<li class="line dk"></li>
			
			<li><a class="J_menuItem" href="javascript:void(0);"
				title="公众号配置"
				onclick="javascript:iframeLoading('pages/shopdmp/scrm/wxSet.jsp');">
					<i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">公众号配置</span>
			</a></li>
			<li class="line dk"></li>

		</ul>
	</div>
</nav>
<!--左侧导航结束-->
