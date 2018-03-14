<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>类目品牌</title>
<%@ include file="../../../common/headCss.jsp"%>
<link rel="stylesheet"
	href="static/ztree/css/metroStyle/metroStyle.css"
	type="text/css">
<style type="text/css">
	.associate ul li,.associate ul section{
		margin-top:4px;
		margin-left:-34px;
		list-style-type: none;
		/* width:100px; */
		height:30px;
		border: 1px solid;
		border-color: #DDDDDD;
		text-align: center;
		cursor:pointer;
	}
	.associate div{
	 width:150px;
	}
	.associate p{
		text-align: center;
		border-bottom: 1px solid;
		border-color: #dddddd;
	}
	.associate ul section input[type="number"]{width:60px;}
	.associate ul section label{margin:0 3px;}
	.associate ul section{margin-top:0;border-top:0;margin-bottom:10px;}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="tabs-container" id="tabs-243687">
						<ul class="nav nav-tabs" id="top_tab">
							<li class="active" onclick="javascript:resetFun();"><a data-toggle="tab" href="#tab-1"
								aria-expanded="true">类目品牌-类目列表</a></li>
							<li class="hide" id="edit-category-brand-li"><a data-toggle="tab" href="#tab-2"
								aria-expanded="true">类目品牌-编辑品牌</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														类目品牌-类目列表
													</h3>
												</div>
												<div class="panel-body" id="item-div">
													<ul id="category-ztree" class="ztree"></ul>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-2" class="tab-pane">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														类目品牌-编辑品牌
													</h3>
												</div>
												<div class="panel-body">
													<form class="form-horizontal" id="save-form" method="post">
														<div class="form-group">
															<label class="col-sm-2 control-label" style="padding-top:0;font-weight: normal">三级类目名称：</label>
															<div class="col-sm-10">
																<b id="third-category-name"></b>
															</div>
														</div>
														<div class="form-group">
															<label for="cusName" class="col-sm-2 control-label" style="padding-top:0;font-weight: normal">编辑类目品牌：</label>
															<div class="col-sm-10">
																<!-- 二级类目ID -->
																<input type="hidden" id="second-lev-cid" value="" />
																<!-- 三级类目ID -->
																<input type="hidden" id="third-lev-cid" value="" />
																<div class="associate">
																	<div id="associate-no" style="border: 1px solid;height: 300px;border-color: #F0F0F0;overflow: auto;width: 30%;">
																		<p>未选择的品牌</p>
																		<ul>
																		    <%-- <c:forEach items="${noBrandList}" var="brand">
																				<li id="${brand.brandId}"  onclick="addBrand(this,'${brand.brandId}');"><i class="fa fa-building">${brand.brandName}</i></li>
																			</c:forEach> --%> 
																		</ul>
																	</div>
																	<div id="associate-yes" style="border: 1px solid;width: 30%;margin-top: -300px; margin-right:20%;border-color: #F0F0F0;  overflow: auto;  height: 300px;float: right;">
																		<p>已选择的品牌</p>
																		<ul>
																			<%-- <c:forEach items="${brandList}" var="brand">
																				<li id="${brand.brandId}"  onclick="delBrand(this,'${brand.brandId}');"><i class="fa fa-building">${brand.brandName}</i></li>
																				<section id="selection-${brand.brandId}">
																					<label><input type="radio" class="" id="status-yes-${brand.brandId}" name="cbrandStatus${brand.brandId}" value="1" checked="checked" />&nbsp;&nbsp;有效</label>
																					<label><input type="radio" class="" id="status-no-${brand.brandId}" name="cbrandStatus${brand.brandId}" value="2" />&nbsp;&nbsp;无效</label>
																					<label><input type="number" class="" id="sort-num-${brand.brandId}" name="sortNum${brand.brandId}" value="" placeholder="排序" /></label>
																				</section>
																			</c:forEach> --%>
																		</ul>
																	</div>
																</div>
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">&nbsp;</label>
															<div class="col-sm-10">
																<button type="button" class="btn btn-primary" id="save-submit-btn">保存</button>
															</div>
														</div>
													</form>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<%@ include file="../../../common/headJs.jsp"%>
		<!-- <script type="text/javascript" src="static/js/categoryManage.js"></script> -->

		<!-- <script src="pages/ecp/js/bootstrap.min.js?v=3.3.6"></script> -->
		<script type="text/javascript"
			src="static/ztree/js/jquery.ztree.core.js"></script>
		<SCRIPT type="text/javascript">
			
			var setting = {
				check: {
					enable: false,
					chkStyle: "checkbox",
				},
				data: {
					key: {
						name: "cName"
					},
					simpleData: {
						enable:true,
						idKey: "cid",
						pIdKey: "parentCid",
						rootPId: 0
					}
				},
				callback: {
					onClick: zTreeOnClick,//节点被点击的事件回调函数
				},
			};
			
			/*
			 * 节点被点击的事件回调函数
			 */
			function zTreeOnClick(event, treeId, treeNode) {
				
				console.log("click:"+JSON.stringify(treeNode));
				console.log(treeNode.cid + ", " + treeNode.cName + ", " + treeNode.parentCid);
			    //$("#add-btn").attr("onclick", "javascript:add(&quot;"+treeNode.id+"&quot;);");
				/* $("#add-btn").attr("onclick", "javascript:add("+treeNode.cid+", '"+treeNode.cName+"', "+treeNode.level+");");//修改添加按钮click事件
				$("#edit-btn").attr("onclick", "javascript:edit("+treeNode.cid+", '"+treeNode.cName+"', '"+treeNode.parentCid+"', "+treeNode.level+");");//修改编辑按钮click事件
				$("#del-btn").attr("onclick", "javascript:del("+treeNode.cid+", '"+treeNode.cName+"', '"+treeNode.parentCid+"');");//修改删除按钮click事件 */
				$("#third-category-name").text(treeNode.cName);
				$("#second-lev-cid").val(treeNode.parentCid);//设置二级类目ID
				$("#third-lev-cid").val(treeNode.cid);//设置三级类目ID
				if(treeNode.lev==3){
					selectBrand(treeNode.cid);
				}else{
					console.log("此目录为 "+treeNode.lev+" 级目录，只有三级目录可以设置品牌！");
				}
				
			};
			var zNodes = ${ztreeNodes};
			console.log("zNodes:"+JSON.stringify(zNodes));
			//$(document).ready(function() {
			$.fn.zTree.init($("#category-ztree"), setting, zNodes);
			
			/*
			 * 查询品牌，并初始化当前类目下已选择和未选择的品牌
			 */
			function selectBrand(cid){
				var url = "back/category-brand/selectBrandByCid";
				var params = {"categoryId":cid};
				$.get(url, params, function(ret){
					console.log(ret);
					if(ret!=null){
						var res = $.parseJSON(ret);
						if(res.result_code=="success"){
							$("#edit-category-brand-li").removeClass("hide");
							var brandList = res.noBrandList;//未选择的品牌集合
							var brandMapList = res.yesBrandMapList;//已选择的品牌集合
							//初始化未选择的品牌
							$("#associate-no").find("ul").empty();
							$.each(brandList,function(i,n){
								var html = innerHtmlNoAuth(this);
								$("#associate-no").find("ul").append(html);
							});
							//初始化已选择的品牌
							$("#associate-yes").find("ul").empty();
							$.each(brandMapList,function(i,n){
								var html = innerHtmlYesAuth(this);
								$("#associate-yes").find("ul").append(html);
							});
							//显示编辑类目品牌选项卡
							$('#tabs-243687 a[href="#tab-2"]').tab('show');
							return;
						}else{
							util.message(res.result_err_msg);
						}
					}
					util.message("查询异常");
					
				});
				
			}
			
			/*
			 * 初始化未选择的品牌HTML字符串
			 */
			function innerHtmlNoAuth(brand){
				var html = '<li id="'+brand.brandId+'"  onclick="addBrand(this,&quot;'+brand.brandId+'&quot;);"><img src="'+brand.brandLogoUrl+'" style="height:14px;">&nbsp;&nbsp;'+brand.brandName+'</i></li>';
				return html;
			}
			/*
			 * 初始化已选择的品牌HTML字符串
			 */
			function innerHtmlYesAuth(brandMap){
				var input_radio_yes_html = '';//有效radio html
				var input_radio_no_html = '';//无效radio html
				if(brandMap.cbrand_status==1){//有效
					input_radio_yes_html = '<input type="radio" class="" id="status-yes-'+brandMap.brand_id+'" name="cbrandStatus'+brandMap.brand_id+'" value="1" checked="checked" />';//有效radio html
					input_radio_no_html = '<input type="radio" class="" id="status-no-'+brandMap.brand_id+'" name="cbrandStatus'+brandMap.brand_id+'" value="2" />';//无效radio html
				}else{//无效
					input_radio_yes_html = '<input type="radio" class="" id="status-yes-'+brandMap.brand_id+'" name="cbrandStatus'+brandMap.brand_id+'" value="1" />';//有效radio html
					input_radio_no_html = '<input type="radio" class="" id="status-no-'+brandMap.brand_id+'" name="cbrandStatus'+brandMap.brand_id+'" value="2" checked="checked" />';//无效radio html
				}
				
				var htmlStr = ''
					+ '<section id="selection-'+brandMap.brand_id+'">'
					+'<input type="hidden" id="category-brand-id'+brandMap.brand_id+'" value="'+brandMap.category_brand_id+'" />'
					+'<label>'
					+input_radio_yes_html
					+'&nbsp;&nbsp;有效'
					+'</label>'
					+'<label>'
					+input_radio_no_html
					+'&nbsp;&nbsp;无效'
					+'</label>'
					+'<label>'
					+'<input type="number" class="" id="sort-num-'+brandMap.brand_id+'" name="sortNum'+brandMap.brand_id+'" value="'+brandMap.sort_num+'" placeholder="排序" />'
					+'</label>'
					+'</section>';
				var html='';
				html+='<li id="'+brandMap.brand_id+'" onclick="delBrand(this,\''+brandMap.brand_id+'\');"><img src="'+brandMap.brand_logo_url+'" style="height:14px;">&nbsp;&nbsp;'+brandMap.brand_name+'</i></li>';
				html+=htmlStr;
				return html;
			}
			
			/*
			 * 添加品牌
			 */
			function addBrand(thisObj,brandId){
				var htmlStr = ''
					+'<section id="selection-'+brandId+'">'
					+'<input type="hidden" id="category-brand-id'+brandId+'" value="" />'
					+'<label>'
					+'<input type="radio" class="" id="status-yes-'+brandId+'" name="cbrandStatus'+brandId+'" value="1" checked="checked" />'
					+'&nbsp;&nbsp;有效'
					+'</label>'
					+'<label>'
					+'<input type="radio" class="" id="status-no-'+brandId+'" name="cbrandStatus'+brandId+'" value="2" />'
					+'&nbsp;&nbsp;无效'
					+'</label>'
					+'<label>'
					+'<input type="number" class="" id="sort-num-'+brandId+'" name="sortNum'+brandId+'" value="" placeholder="排序" />'
					+'</label>'
					+'</section>';
				var html='';
				html+='<li id="'+brandId+'" onclick="delBrand(this,\''+brandId+'\');">'+$(thisObj).html()+'</i></li>';
				html+=htmlStr;
				var html=$("#associate-yes").find("ul").append(html);
				$(thisObj).remove();
			}
			/*
			 * 删除品牌
			 */
			function delBrand(thisObj,brandId){
				var html='';
				html+='<li id="'+brandId+'"  onclick="addBrand(this,\''+brandId+'\');">'+$(thisObj).html()+'</i></li>';
				var html=$("#associate-no").find("ul").append(html);
				$(thisObj).remove();
				$("#selection-"+thisObj.id).remove();
			}
			
			/*
			 * 保存已选择的品牌
			 */
			$("#save-submit-btn").click(function(){
				var arr = new Array();
				$("#associate-yes ul li").each(function(index){
					console.log("第"+index+"个， brandId:"+this.id);
					var brandId = this.id;
					var categoryBrandId = $("#selection-"+brandId+" #category-brand-id"+brandId).val();
					var cbrandStatus = $("#selection-"+brandId+" input[name='cbrandStatus"+brandId+"']:checked").val();
					var sortNum = $("#selection-"+brandId+" #sort-num-"+brandId).val();
					var obj = createCategoryBrandObj(categoryBrandId, brandId, cbrandStatus, sortNum);
					arr.push(obj);
				});
				var brandListJson = JSON.stringify(arr);
				console.log("已选择的品牌集合："+brandListJson);
				
				var url = "back/category-brand/saveCategoryBrand";
				var secondLevCid = $("#second-lev-cid").val();
				var thirdLevCid = $("#third-lev-cid").val();
				var params = {"secondLevCid":secondLevCid, "thirdLevCid":thirdLevCid, "brandListJson":brandListJson};
				$.post(url, params, function(ret){
					console.log(ret);
					if(ret!=null){
						var res = $.parseJSON(ret);
						if(res.result_code=="success"){
							reloadFun();
							return;
						}else{
							util.message(res.result_err_msg);
						}
					}
				});
			});
			
			/*
			 * 创建类目品牌对象
			 */
			function createCategoryBrandObj(categoryBrandId, brandId, cbrandStatus, sortNum){
				var obj = new Object();
				obj.categoryBrandId = categoryBrandId;
				obj.brandId = brandId;
				obj.cbrandStatus = cbrandStatus;
				obj.sortNum = sortNum;
				return obj;
			}
			
			/*
			 * 重新加载当前内容
			 */
			function reloadFun(){
				//操作成功后重新加载
				var href = "back/category-brand/getCategotyItems";
				parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
			}
			
			/*
			 * 重置
			 */
			function resetFun(){
				$("#third-category-name").text("");
				$("#associate-yes").find("ul").html("");
				$("#associate-no").find("ul").html("");
				$("#edit-category-brand-li").addClass("hide");//隐藏编辑选项卡
			}
			//});
		</SCRIPT>
</body>
</html>