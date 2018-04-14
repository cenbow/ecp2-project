<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
												<!-- 当前商品类目ID（要修改的商品类目ID） -->
												<input type="hidden" id="curr-item-cid" name="" value="" />
												<div class="form-group">
													<label class="col-sm-2 control-label">商品类目<b style="color:red;">&nbsp;*</b></label>
													<div class="col-sm-10">
														<select class="form-control" id="item-cid" name="cid">
															<%-- <c:forEach items="${categoryList}" var="category">
																<option value="${category.cid}">${category.cName}</option>
															</c:forEach> --%>
															<c:forEach items="${categoryList}" var="category">
																<c:if test="${category.lev==1}">
																	<option value="${category.cid}" level="${category.lev}">&nbsp;&nbsp;┏&nbsp;&nbsp;${category.cName}</option>
																</c:if>
																<c:if test="${category.lev==2}">
																	<option value="${category.cid}" level="${category.lev}">&nbsp;&nbsp;&nbsp;&nbsp;┠&nbsp;&nbsp;${category.cName}</option>
																</c:if>
																<c:if test="${category.lev==3}">
																	<option value="${category.cid}" level="${category.lev}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┗ &nbsp;&nbsp;${category.cName}</option>
																</c:if>
																<c:if test="${category.lev!=1 && category.lev!=2 && category.lev!=3}">
																	<option value="${category.cid}" level="${category.lev}">${category.cName}</option>
																</c:if>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">品牌<b style="color:red;">&nbsp;*</b></label>
													<div class="col-sm-10">
														<select class="form-control" id="brand" name="brand">
															<option value="0">请选择</option>
														</select>
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">是否方案性产品<b style="color:red;">&nbsp;*</b></label>
													<div class="col-sm-10">
														<select class="form-control" id="is-plan-product" name="isPlanProduct">
															<option value="2">否</option>
															<option value="1">是</option>
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
													<label class="col-sm-2 control-label">最高价格<b style="color:red;">&nbsp;*</b></label>
													<div class="col-sm-10">
														<input type="text" id="highest-price" name="highestPrice"
															class="form-control" placeholder="最高价格">
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">最低价格<b style="color:red;">&nbsp;*</b></label>
													<div class="col-sm-10">
														<input type="text" id="lowest-price" name="lowestPrice"
															class="form-control" placeholder="最低价格">
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">商城价格<b style="color:red;">&nbsp;*</b></label>
													<div class="col-sm-10">
														<input type="text" id="market-price" name="marketPrice"
															class="form-control" placeholder="商城价格">
													</div>
												</div>
												<!-- <div class="form-group">
													<label class="col-sm-2 control-label">成本价格<b style="color:red;">&nbsp;*</b></label>
													<div class="col-sm-10">
														<input type="text" id="market-price2" name="marketPrice2"
															class="form-control" placeholder="成本价格">
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">硬成本价格<b style="color:red;">&nbsp;*</b></label>
													<div class="col-sm-10">
														<input type="text" id="hard-cost-price" name="hardCostPrice"
															class="form-control" placeholder="硬成本价格">
													</div>
												</div> -->
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
															class="form-control" placeholder="商品体积">
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label">商品毛重<b style="color:red;">&nbsp;*</b></label>
													<div class="col-sm-10">
														<input type="text" id="weight" name="weight"
															class="form-control" placeholder="商品毛重">
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
														<script id="item-ueditor" type="text/plain"
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
														<script id="after-service" type="text/plain"
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
	<!-- <div class="row clearfix">
		<div class="col-md-12 column">
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">&nbsp;</label>
				<div class="col-sm-10">
					<button type="button" class="btn btn-primary delet_btn">保存</button>
				</div>
			</div>
			<button type="button" class="btn btn-default btn-primary btn-block" id="save-submit-btn">保存</button>
		</div>
	</div> -->
</div>
<!-- <script type="text/javascript" src="static/js/addItem.js"></script> -->
<script type="text/javascript">
/**
 * 绑定类目父节点的change事件
 */
$("#item-cid").bind("change",function(){
	var curr_item_cid = $("#curr-item-cid").val();
    //获取自定义属性的值
    var lev = $(this).find("option:selected").attr("level");
    console.log("level:"+lev);
	if(lev!=3){
		$(this).val(curr_item_cid);
		util.message("请选择三级类目！");
	}else{
		changeItemCategory();
	}
});
/**
 * 加载品牌、属性和属性值，不包括sku价格
 */
function getAttrAndValueFun(id, cid){
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
			
			var url = "back/category/getBrandAndAttrSkuPrice";//查询品牌、属性和属性值
			var params = {"cid": cid};
			$("#attr-page").load(url, params, function(){
				console.log("加载属性页面完成");
				//$('#tabs-edit-item a[href="#tab-7"]').tab('show');
				if(id!=null && id!=undefined){
					console.log("ajax请求获取商品信息");
					ajaxRequestGetItemInfo(id);//ajax请求获取商品信息
				}
			});
		}
	});
	
};
</script>
