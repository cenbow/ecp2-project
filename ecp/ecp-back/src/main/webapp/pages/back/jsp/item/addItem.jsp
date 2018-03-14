<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加商品</title>
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
	<div class="tabs-container" id="tabs-add-item">
		<ul class="nav nav-tabs" id="top_tab_item_manage">
			<li class="active" onclick="javascript:clickSelectCateTab();"><a data-toggle="tab" href="#tab-3"
				aria-expanded="true">选择类目</a></li>
			<li class="hide" id="item-info-li"><a
				data-toggle="tab" href="#tab-4" aria-expanded="false">商品信息</a></li>
		</ul>
		<div class="tab-content">
			<div id="tab-3" class="tab-pane active">
				<div class="card-content">
					<div class="table-responsive"
						style="background: #fff; margin-top: 10px; border: 1px solid #e5e5e5;">
						<div id="dataTables-example_wrapper"
							class="dataTables_wrapper form-inline" role="grid">
							<div id="" style="margin: 20px">
								<style>
	/* 类目 */
	.item-category li {
		list-style: none;
		cursor: pointer;
		padding: 3px 10px;
	}
	
	.item-category li.current {
		font-weight: 700;
		color: #000;
		background: #ccc;
	}
	
	.item-category ul {
		border: 1px solid #999;
		height: 104px;
		overflow-y: auto;
		padding: 0;
	}
	/* biaoti */
	/* .fieldset {
		position: relative;
		border: 1px solid #ccc;
		padding: 10px;
		margin: 10px 0;
	}
	
	.fieldset .legend {
		position: absolute;
		left: 80px;
		top: -15px;
		padding: 0 10px;
		background: #fff;
		font-weight: 700;
		font-size: 18px;
	} */
	</style>
	
								<div class="container-fluid">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">选择类目</h3>
												</div>
												<div class="panel-body item-category">
													<div class="col-sm-4">
														<ul id="first-category">
															<c:forEach items="${categoryList}" var="category">
																<li id="first-${category.cid}" class=""
																	onclick="javascript:selectCategoryByPid(${category.cid}, 1);">${category.cName}<b
																	class="pull-right">></b></li>
															</c:forEach>
														</ul>
													</div>
													<div class="col-sm-4">
														<ul id="second-category">
	
														</ul>
													</div>
													<div class="col-sm-4">
														<ul id="third-category">
	
														</ul>
													</div>
												</div>
												<div class="panel-footer">
													<div class="" id="current-select-category">
														当前选择的类目： <b id="first"></b> <b id="second"></b> <b
															id="third"></b>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
	
								<div>
									<label class="col-sm-2 control-label">&nbsp;</label>
									<div class="col-sm-10" id="">
										<button type="button" class="btn btn-default btn-primary"
											id="next-step-btn" disabled="disabled">下一步</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="tab-4" class="tab-pane">
				<div class="container-fluid" style="margin-top: 20px;">
					<div class="row clearfix">
						<div class="col-md-12 column">
							<form class="form-horizontal">
								<div class="form-group">
									<div class="col-sm-12" id="user-selected-category">
										<!-- 显示用户选择类目 -->
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="container-fluid" style="margin-top: 20px;">
					<div class="row clearfix">
						<div class="col-md-12 column">
							<div class="tabs-container" id="tabs-edit-item">
								<ul class="nav nav-tabs" id="top_tab">
									<li class="active"><a data-toggle="tab" href="#tab-5"
										aria-expanded="false">商品基本信息</a></li>
									<li class=""><a data-toggle="tab" href="#tab-6"
										aria-expanded="false">商品图片</a></li>
									<li class=""><a data-toggle="tab" href="#tab-7"
										aria-expanded="false">属性和SKU</a></li>
									<li class=""><a data-toggle="tab" href="#tab-8"
										aria-expanded="false">商品详情</a></li>
									<li class=""><a data-toggle="tab" href="#tab-9"
										aria-expanded="false">售后服务</a></li>
								</ul>
								<div class="tab-content">
									<div id="tab-5" class="tab-pane active">
										<div class="container-fluid" style="margin-top: 20px;">
											<div class="row clearfix">
												<div class="col-md-12 column">
													<div class="panel panel-default">
														<div class="panel-heading">
															<h3 class="panel-title">商品基本信息</h3>
														</div>
														<div class="panel-body">
															<form class="form-horizontal" id="save-form">
																<!-- 商品ID -->
																<input type="hidden" id="item-id" name="itemId" value="" />
																<!-- 商品类目ID -->
																<input type="hidden" id="item-cid" name="cid" value="" />
																<div class="form-group">
																	<label class="col-sm-2 control-label">品牌<b style="color:red;">&nbsp;*</b></label>
																	<div class="col-sm-10">
																		<select class="form-control" id="brand" name="brand">
																			<option value="0">请选择</option>
																		</select>
																	</div>
																</div>
																<div class="form-group">
																	<label class="col-sm-2 control-label">商品名称<b style="color:red;">&nbsp;*</b></label>
																	<div class="col-sm-10">
																		<input type="text" id="item-name" name="itemName"
																			class="form-control" placeholder="商品名称" />
																	</div>
																</div>
																<div class="form-group">
																	<label class="col-sm-2 control-label">型号<b style="color:red;">&nbsp;*</b></label>
																	<div class="col-sm-10">
																		<input type="text" id="item-model" name="model"
																			class="form-control" placeholder="型号" />
																	</div>
																</div>
																<div class="form-group">
																	<label class="col-sm-2 control-label">关键字<b style="color:red;">&nbsp;*</b></label>
																	<div class="col-sm-10">
																		<input type="text" id="keywords" name="keywords"
																			class="form-control" placeholder="关键字" />
																	</div>
																</div>
																<!-- <div class="form-group">
															<label class="col-sm-2 control-label">商品简介</label>
															<div class="col-sm-10">
																<input type="tel" class="form-control" id="introduction"
																	name="introduction" placeholder="商品简介" />
															</div>
														</div> -->
																<div class="form-group">
																	<label class="col-sm-2 control-label">预算价格<b style="color:red;">&nbsp;*</b></label>
																	<div class="col-sm-10">
																		<input type="text" id="guide-price" name="guidePrice"
																			class="form-control" placeholder="预算价格">
																	</div>
																</div>
																<div class="form-group">
																	<label class="col-sm-2 control-label">商城价格<b style="color:red;">&nbsp;*</b></label>
																	<div class="col-sm-10">
																		<input type="text" id="market-price" name="marketPrice"
																			class="form-control" placeholder="商城价格">
																	</div>
																</div>
																<div class="form-group">
																	<label class="col-sm-2 control-label">成本价格<b style="color:red;">&nbsp;*</b></label>
																	<div class="col-sm-10">
																		<input type="text" id="market-price2" name="marketPrice2"
																			class="form-control" placeholder="成本价格">
																	</div>
																</div>
																<div class="form-group">
																	<label class="col-sm-2 control-label">库存数量<b style="color:red;">&nbsp;*</b></label>
																	<div class="col-sm-10">
																		<input type="text" id="inventory" name="inventory"
																			class="form-control" placeholder="库存数量">
																	</div>
																</div>
																<div class="form-group">
																	<label class="col-sm-2 control-label">商品产地<b style="color:red;">&nbsp;*</b></label>
																	<div class="col-sm-10">
																		<input type="text" id="origin" name="origin"
																			class="form-control" placeholder="商品产地">
																	</div>
																</div>
																<div class="form-group">
																	<label class="col-sm-2 control-label">包装清单<b style="color:red;">&nbsp;*</b></label>
																	<div class="col-sm-10">
																		<input type="text" id="packing-list" name="packingList"
																			class="form-control" placeholder="包装清单">
																	</div>
																</div>
																<div class="form-group">
																	<label class="col-sm-2 control-label">商品体积<b style="color:red;">&nbsp;*</b></label>
																	<div class="col-sm-10">
																		<input type="text" id="volume" name="volume"
																			class="form-control" value="0" placeholder="商品体积">
																	</div>
																</div>
																<div class="form-group">
																	<label class="col-sm-2 control-label">商品毛重<b style="color:red;">&nbsp;*</b></label>
				
																	<div class="col-sm-10">
																		<input type="text" id="weight" name="weight"
																			class="form-control" value="0" placeholder="商品毛重">
																	</div>
																</div>
																<div class="form-group">
																	<label class="col-sm-2 control-label">重量单位</label>
				
																	<div class="col-sm-10">
																		<input type="text" id="weight-unit" name="weightUnit"
																			class="form-control" placeholder="重量单位">
																	</div>
																</div>
																<div class="form-group">
																	<label class="col-sm-2 control-label">创建时间</label>
																	<div class="col-sm-10">
																		<input type="text" id="create-time-str"
																			name="createtimestr"
																			class="datetimepicker datetime form-control"
																			readonly="readonly" placeholder="创建时间"
																			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
																	</div>
																</div>
																<!-- <div class="form-group">
																	<label class="col-md-2 control-label">描述</label>
																	<div class="col-md-10 ">
																		<script id="item-ueditor" type="text/plain"
																			style="width: 100%; height: 500px;"></script>
																	</div>
																</div> -->
															</form>
														</div>
														<div class="panel-footer">商品基本信息</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div id="tab-6" class="tab-pane">
										<div class="container-fluid" style="margin-top: 20px;">
											<div class="row clearfix">
												<div class="col-md-12 column">
													<div class="panel panel-default">
														<div class="panel-heading">
															<h3 class="panel-title">商品图片</h3>
														</div>
														<div class="panel-body">
															<form class="form-horizontal" id="">
																<div class="form-group">
																	<label for="name" class="col-sm-2 control-label">&nbsp;</label>
																	<div class="col-sm-10" style="color:red;">注：请上传&nbsp;450*450&nbsp;的图片！</div>
																</div>
																<div class="form-group">
																	<label class="col-md-2 control-label">缩略图<b style="color:red;">&nbsp;*</b></label>
																	<div class="col-md-5 ">
																		<input type="file" id="picture-url" name="pictureImg"
																			onchange="javascript:validateImgFileSizeFun(this);"
																			accept="image/*" multiple="multiple" /> <input
																			type="hidden" id="brand-logo-url" name="pictureUrl" />
																	</div>
																</div>
																<div class="form-group">
																	<label for="name" class="col-sm-2 control-label">&nbsp;</label>
																	<div class="col-sm-10" id="thumbnail-portrait"></div>
																</div>
															</form>
														</div>
														<div class="panel-footer">商品图片</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div id="tab-7" class="tab-pane">
										<div class="container-fluid" style="margin-top: 20px;">
											<div class="row clearfix">
												<div class="col-md-12 column">
													<div class="panel panel-default">
														<div class="panel-heading">
															<h3 class="panel-title">属性和SKU</h3>
														</div>
														<div class="panel-body">
															<form class="form-horizontal" id="">
																<div class="form-group">
																	<div class="col-sm-12" id="">
																		<div id="attr-page">
																			<%-- <%@ include file="attrPage.jsp"%> --%>
																		</div>
																		<%-- sku页面在属性页面中
															<div id="sku-page">
																<%@ include file="skuPage.jsp"%>
															</div> --%>
																	</div>
																</div>
															</form>
														</div>
														<div class="panel-footer">属性和SKU</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div id="tab-8" class="tab-pane">
										<div class="container-fluid" style="margin-top: 20px;">
											<div class="row clearfix">
												<div class="col-md-12 column">
													<div class="panel panel-default">
														<div class="panel-heading">
															<h3 class="panel-title">商品详情</h3>
														</div>
														<div class="panel-body">
															<form class="form-horizontal" id="">
																<div class="form-group">
																	<label class="col-md-2 control-label">描述</label>
																	<div class="col-md-10 ">
																		<script id="item-ueditor" name="itemUeditor" type="text/plain"
																			style="width: 100%; height: 500px;"></script>
																	</div>
																</div>
															</form>
														</div>
														<div class="panel-footer">商品详情</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div id="tab-9" class="tab-pane">
										<div class="container-fluid" style="margin-top: 20px;">
											<div class="row clearfix">
												<div class="col-md-12 column">
													<div class="panel panel-default">
														<div class="panel-heading">
															<h3 class="panel-title">售后服务</h3>
														</div>
														<div class="panel-body">
															<form class="form-horizontal" id="">
																<div class="form-group">
																	<label class="col-md-2 control-label">售后服务</label>
																	<div class="col-md-10 ">
																		<script id="after-service" name="afterService" type="text/plain"
																			style="width: 100%; height: 500px;"></script>
																	</div>
																</div>
															</form>
														</div>
														<div class="panel-footer">售后服务</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row clearfix">
						<div class="col-md-12 column">
							<!-- <div class="form-group">
								<label for="name" class="col-sm-2 control-label">&nbsp;</label>
								<div class="col-sm-10">
									<button type="button" class="btn btn-primary delet_btn">保存</button>
								</div>
							</div> -->
							<button type="button" class="btn btn-default btn-primary btn-block" id="save-submit-btn">保存</button>
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="static/js/addItem.js"></script>
	<script>
	/*
	 * 点击下一步按钮时执行，显示添加商品基本信息选项卡
	 */
	$("#next-step-btn").click(function(){
		var userSelectedCategoryHtml = $("#current-select-category").html();
		console.log(userSelectedCategoryHtml);
		$("#user-selected-category").html(userSelectedCategoryHtml);
		
		$("#item-info-li").removeClass("hide");//显示商品信息选项卡
		var cid = $("#item-cid").val();
		var url = "back/brand/selectByCid";
		var params = {"cid": cid};
		$.post(url, params, function(ret){//查询品牌
			var obj = $.parseJSON(ret);
			if(obj.result_code=="success"){
				var brandList = obj.brandList;
				console.log(JSON.stringify(brandList));
				if(brandList!=null){
					$("#brand").empty();
					var htmlStr = "<option value='0'>请选择</option>";
					$.each(brandList,function(i,n){
						htmlStr += "<option value='"+this.brand_id+"'>"+this.brand_name+"</option>";
					});
					$("#brand").append(htmlStr);
				}else{
					util.message("类目品牌为空！");
				}
			}
		});
		
		url = "back/category/selectBrandAndAttr";//查询品牌、属性和属性值
		$("#attr-page").load(url, params, function(){
			console.log("加载属性页面完成");
			$('#tabs-add-item a[href="#tab-4"]').tab('show');
		});
		
	});
	</script>
</body>
</html>