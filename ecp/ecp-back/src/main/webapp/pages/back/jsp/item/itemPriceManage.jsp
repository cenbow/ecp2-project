<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>商品管理</title>
<%@ include file="../../../common/headCss.jsp"%>

<script type="text/javascript">
	window.UEDITOR_HOME_URL = "/ecp-back/tools/ueditor/"; //一定要用这句话，否则你需要去ueditor.config.js修改路径的配置信息
</script>

<script type="text/javascript" charset="utf-8"
	src="tools/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="tools/ueditor/ueditor.all.min.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8"
	src="tools/ueditor/lang/zh-cn/zh-cn.js"></script>
<!-- 日期工具 -->
<script type="text/javascript" src="static/calendar/WdatePicker.js"></script>

<%@ include file="../../../common/headJs.jsp"%>

<script type="text/javascript" src="static/jquery/ajaxFileUpload.js"></script>

</head>
<body class="gray-bg">
<div class="container-fluid" style="margin-top: 20px;">
					<div class="row clearfix">
						<div class="col-md-12 column">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="tabs-container" id="tabs-243687">
						<ul class="nav nav-tabs" id="top_tab_item_manage">
							<li class="active" onclick="javascript:clickItemTab();"><a data-toggle="tab" href="#tab-1"
								aria-expanded="true"> 商品列表</a></li>
							<li class="hide" id="edit-item-li"><a
								data-toggle="tab" href="#tab-2" aria-expanded="false">编辑商品</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														查询面板
													</h3>
												</div>
												<div class="panel-body" id="">
													<form class="form-horizontal" id="search-form">
														<div class="form-group">
															<label class="col-md-2 control-label">类目</label>
															<div class="col-md-3">
																<select class="form-control" id="search-cate-first" name="" onchange="javascript:getCategoryByPidFun(1);">
																	<option value='' selected='selected'>---请选择一级类目---</option>
																	<c:forEach items="${categoryList}" var="category">
																		<c:if test="${category.lev==1}">
																			<option value="${category.cid}" level="${category.lev}">${category.cName}</option>
																		</c:if>
																		<%-- <c:if test="${category.lev==2}">
																			<option value="${category.cid}" level="${category.lev}">&nbsp;&nbsp;&nbsp;&nbsp;┠&nbsp;&nbsp;${category.cName}</option>
																		</c:if>
																		<c:if test="${category.lev==3}">
																			<option value="${category.cid}" level="${category.lev}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┗ &nbsp;&nbsp;${category.cName}</option>
																		</c:if>
																		<c:if test="${category.lev!=1 && category.lev!=2 && category.lev!=3}">
																			<option value="${category.cid}" level="${category.lev}">${category.cName}</option>
																		</c:if> --%>
																	</c:forEach>
																</select>
															</div>
															<div class="col-md-3">
																<select class="form-control" id="search-cate-second" name="" onchange="javascript:getCategoryByPidFun(2);">
																	<option value='' selected='selected'>---请选择二级类目---</option>
																</select>
															</div>
															<div class="col-md-4">
																<select class="form-control" id="search-cate-third" name="" onchange="javascript:getCategoryByPidFun(3);">
																	<option value='' selected='selected'>---请选择三级类目---</option>
																</select>
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-2 control-label">关键字</label>
															<div class="col-md-10">
																<input type="text" id="search-keywords" name="search-keywords" class="form-control" placeholder="类目名称 / 品牌名称 / 商品名称">
															</div>
														</div>
														<div class="form-group">
															<label for="name" class="col-sm-2 control-label">&nbsp;</label>
															<div class="col-sm-10">
																<button type="button" class="btn btn-default btn-primary" id="search-submit-btn">查询</button>
																<button type="button" class="btn btn-default btn-warning" id="search-reset-btn">重置</button>
															</div>
														</div>
													</form>
												</div>
											</div>
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														商品列表
													</h3>
												</div>
												<div class="panel-body">
													<div class="panel panel-default">
														<div class="panel-body">
															<button type="button" class="btn btn-danger" id="batch-delete-btn">批量删除</button>
															<button type="button" class="btn btn-default btn-warning" id="batch-shelve-btn">批量上架</button>
															<button type="button" class="btn btn-danger" id="batch-unshelve-btn">批量下架</button>
														</div>
													</div>
													<div class="panel panel-default">
														<div id="item-div" style="margin: 20px">
															<%@ include file="itemManageTable.jsp"%>
														</div>
													</div>
													
												</div>
											</div>
											
										</div>
									</div>
								</div>
							</div>
							<div id="tab-2" class="tab-pane">
								<%@ include file="editItemPrice.jsp"%>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>
</div>
</div>

	<script type="text/javascript" src="static/js/itemPriceManage.js"></script>
	<script type="text/javascript">
	/*
	 * 根据父ID查询类目
	 * 		参数2：当前是第n级类目的change事件
	 */
	function getCategoryByPidFun(index){
		var htmlStr = "<option value='' selected='selected'>---请选择二级类目---</option>";
		var id = "";
		var appendId = "";
		if(index==1){
			id = "#search-cate-first";
			appendId = "#search-cate-second";
			htmlStr = "<option value='' selected='selected'>---请选择二级类目---</option>";
		}else if(index==2){
			id = "#search-cate-second";
			appendId = "#search-cate-third";
			htmlStr = "<option value='' selected='selected'>---请选择三级类目---</option>";
		}else if(index==3){
			return false;
		}
		var pid = $(id).val();
		var url = "back/category/selectByPid";
		var params = {"pid":pid};
		//util.loading();
		$.post(url, params, function(res){
			console.log(res);
			if(res!=null && res!=""){
				var obj = $.parseJSON(res);
				if(obj.result_code=="success"){
					$(appendId).empty();
					var categoryList = obj.categoryList;
					console.log(JSON.stringify(categoryList));
					if(categoryList!=null){
						
						$.each(categoryList,function(i,n){
						    htmlStr += "<option value='"+this.cid+"'>"+this.cName+"</option>";//创建类目HTML字符串
						});
						$(appendId).append(htmlStr);
					}else{
						util.message("查询类目列表为null");
					}
				}else{
					util.message(obj.result_err_msg);
				}
			}
			
		});
	}
	</script>
</body>
</html>