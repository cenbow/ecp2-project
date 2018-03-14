<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>权限</title>
<%@ include file="../../../common/headCss.jsp"%>
<link rel="stylesheet"
	href="static/ztree/css/metroStyle/metroStyle.css"
	type="text/css">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="tabs-container" id="tabs-243687">
						<ul class="nav nav-tabs" id="top_tab">
							<li class="active"><a data-toggle="tab" href="#tab-1"
								aria-expanded="true">类目列表</a></li>
							<li class="" onclick="javascript:resetFun();"><a data-toggle="tab" id="edit-tab" href="#tab-2"
								aria-expanded="false">新增/编辑</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<div class="card-content">
									<div class="table-responsive"
										style="background: #fff; margin-top: 30px; border: 1px solid #e5e5e5;">
										<div id="dataTables-example_wrapper"
											class="dataTables_wrapper form-inline" role="grid">
											<div class="modal-header">
												<div class="col-xs-10  clearfix">
													<button type="button" id="add-btn" onclick="javascript:add(0, '默认为根节点', 1);">新增</button>
													<button type="button" id="edit-btn" onclick="javascript:edit(0);">编辑</button>
													<button type="button" id="del-btn" onclick="javascript:del(0);">删除</button>
												</div>
											</div>

											<div class="panel-body" id="item-div">
												<ul id="category-ztree" class="ztree"></ul>
											</div>
										</div>
									</div>

								</div>

							</div>
							<div id="tab-2" class="tab-pane">

								<div class="panel-body">
									<form class="form-horizontal" id="save-form" method="post">
										<input type="hidden" id="category-id" name="cid" value="" />
										<div class="form-group">
											<label class="col-sm-2 control-label">名称</label>
											<div class="col-sm-10">
												<input type="text" class="form-control" id="category-name" name="cName" value="" />
											</div>
										</div>
										<!-- <div class="form-group">
											<label class="col-sm-2 control-label">级别</label>
											<div class="col-sm-10">
												<input type="text" class="form-control" id="category-level" name="level" value="" />
											</div>
										</div> -->
										<input type="hidden" class="form-control" id="category-level" name="level" value="0" placeholder="级别" />
										<div class="form-group">
											<label class="col-sm-2 control-label">父类型</label>
											<div class="col-sm-10">
												<select class="form-control" id="category-parentid" name="parentCid">
													<option value="0">请选择父类型（默认为根节点）</option>
													<c:forEach items="${categoryList}" var="category">
														<option value="${category.cid}">${category.cName}</option>
													</c:forEach>
												</select>
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

		<%@ include file="../../../common/headJs.jsp"%>
		<script type="text/javascript" src="static/js/categoryManage.js"></script>

		<!-- <script src="pages/ecp/js/bootstrap.min.js?v=3.3.6"></script> -->
		<script type="text/javascript"
			src="static/ztree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript"
			src="static/ztree/js/jquery.ztree.excheck.js"></script>
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
					//onCheck: zTreeOnCheck,//checkbox / radio 被勾选 或 取消勾选的事件回调函数
					onClick: zTreeOnClick,//节点被点击的事件回调函数
				},
			};
			/*
			 * checkbox / radio 被勾选 或 取消勾选的事件回调函数
			 */
			/* function zTreeOnCheck(event, treeId, treeNode) {
				
				var treeObj = $.fn.zTree.getZTreeObj("category-ztree");
			    var sNodes = treeObj.getSelectedNodes();
			    console.log("sNodes:"+JSON.stringify(sNodes));
			    if (sNodes.length > 0) {
			    	var node = sNodes[0].getParentNode();
			    	console.log("node:"+JSON.stringify(node));
			    	var nodeArr = node.children;
			    	var isChecked = true;
			    	for(var i=0; i<nodeArr.length; i++){
			    		console.log("nodeArr["+i+"]:"+nodeArr[i].checked);
			    	
			    		if(!nodeArr[i].checked){
			    			isChecked = false;
			    			break;
			    		}
			    	}
			    	if(isChecked){
			    		idArr.push(node.id);
			    	}else{
			    		for(var i=0; i<idArr.length; i++){
				    		if(idArr[i]==node.id){
				    			idArr.splice(i, 1);
					    		break;
				    		}
				    	}
			    	}
			    }
			    
				console.log("treeId:"+treeId);
				console.log("check:"+JSON.stringify(treeNode));
			    console.log(treeNode.id + ", " + treeNode.name + "," + treeNode.checked);
			    //console.log("children:"+JSON.stringify(treeNode.children)+", length:"+treeNode.children.length);
			    console.log("isParent:"+treeNode.isParent);
			    
			    if(treeNode.checked){
			    	idArr.push(treeNode.id);
			    	addNodeId(treeNode);
			    }else{
			    	for(var i=0; i<idArr.length; i++){
			    		if(idArr[i]==treeNode.id){
			    			idArr.splice(i, 1);
				    		break;
			    		}
			    	}
			    	removeNodeId(treeNode);
			    }
			    
			    console.log(idArr);
			}; */
			/*
			 * 添加节点ID
			 */
			/* function addNodeId(treeNode){
		    	if(treeNode.isParent){
			    	var childArr = treeNode.children;
			    	for(var n=0; n<childArr.length; n++){
			    		idArr.push(childArr[n].id);
			    		if(childArr[n].isParent){
			    			addNodeId(childArr[n]);
			    		}
			    	}
			    }
			} */
			/*
			 * 删除节点ID
			 */
			/* function removeNodeId(treeNode){
		    	if(treeNode.isParent){
			    	var childArr = treeNode.children;
			    	for(var n=0; n<childArr.length; n++){
			    		for(var i=0; i<idArr.length; i++){
			    			if(idArr[i]==childArr[n].id){
				    			idArr.splice(i, 1);
					    		break;
				    		}
				    	}
			    		if(childArr[n].isParent){
			    			removeNodeId(childArr[n]);
			    		}
			    	}
			    }
			} */
			
			/*
			 * 节点被点击的事件回调函数
			 */
			function zTreeOnClick(event, treeId, treeNode) {
				
				console.log("click:"+JSON.stringify(treeNode));
				console.log(treeNode.cid + ", " + treeNode.cName + ", " + treeNode.parentCid);
			    //$("#add-btn").attr("onclick", "javascript:add(&quot;"+treeNode.id+"&quot;);");
				$("#add-btn").attr("onclick", "javascript:add("+treeNode.cid+", '"+treeNode.cName+"', "+treeNode.level+");");//修改添加按钮click事件
				$("#edit-btn").attr("onclick", "javascript:edit("+treeNode.cid+", '"+treeNode.cName+"', '"+treeNode.parentCid+"', "+treeNode.level+");");//修改编辑按钮click事件
				$("#del-btn").attr("onclick", "javascript:del("+treeNode.cid+", '"+treeNode.cName+"', '"+treeNode.parentCid+"');");//修改删除按钮click事件
			};
			var zNodes = ${categoryListJson};
			console.log("zNodes:"+JSON.stringify(zNodes));
			/* var zNodes = [ {
				id : 1,
				pId : 0,
				name : "功能管理",
				open : true
			}, {
				id : 2,
				pId : 1,
				name : "会员管理",
				open : true
			},

			{
				id : 6,
				pId : 0,
				name : "报表",
				open : true
			}, {
				id : 7,
				pId : 6,
				name : "用户画像",
				open : true
			},

			{
				id : 20,
				pId : 0,
				name : "配置管理",
				open : true
			}, {
				id : 21,
				pId : 20,
				name : "渠道管理",
				open : true
			}, {
				id : 22,
				pId : 0,
				name : "中诚动力",
				open : true
			}, {
				id : 23,
				pId : 22,
				name : "技术部",
				open : true
			}, {
				id : 24,
				pId : 22,
				name : "UI设计",
				open : true
			}, {
				id : 25,
				pId : 22,
				name : "UE设计",
				open : true
			}, {
				id : 26,
				pId : 22,
				name : "销售部",
				open : true
			},

			]; */
			//$(document).ready(function() {
			$.fn.zTree.init($("#category-ztree"), setting, zNodes);
			
			//});
		</SCRIPT>
</body>
</html>