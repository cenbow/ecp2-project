<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>角色</title>
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
							<li class="active" onclick="javascript:resetFun();"><a data-toggle="tab" href="#tab-1"
								aria-expanded="true">角色列表</a></li>
							<li class="hide" id="edit-role-li"><a
								data-toggle="tab" href="#tab-2" aria-expanded="false">添加/编辑</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">
														角色列表
													</h3>
												</div>
												<div class="panel-body">
												<div class="panel panel-default">
													<div class="panel-body">
														<button type="button" id="add-role-btn" class="btn btn-default btn-primary">添加角色</button>
													</div>
												</div>
												<div class="panel panel-default">
													<div class="panel-body">
														<div id="item-div" style="margin: 20px">
															<%@ include file="role_table.jsp"%>
														</div>
													</div>
												</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<%-- <div class="card-content">
									<div class="table-responsive"
										style="background: #fff; margin-top: 10px; border: 1px solid #e5e5e5;">
										<div id="dataTables-example_wrapper"
											class="dataTables_wrapper form-inline" role="grid">
											<div class="modal-header">
												<div id="item-div" style="margin: 20px">
													<%@ include file="role_table.jsp"%>
												</div>
											</div>
										</div>
									</div>
								</div> --%>
							</div>
							<div id="tab-2" class="tab-pane">
								<div class="container-fluid" style="margin-top: 20px;">
									<div class="row clearfix">
										<div class="col-md-12 column">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h3 class="panel-title">角色信息</h3>
												</div>
												<div class="panel-body">
													<form class="form-horizontal" id="save-form">
														<input type="hidden" id="role-id" name="roleId" value="" />
														<div class="form-group">
															<label class="col-sm-2 control-label">角色编码</label>
															<div class="col-sm-5">
																<input type="text" id="role-code" name="roleCode"
																	class="form-control" placeholder="选择角色编码/输入角色编码" />
															</div>
															<div class="col-sm-5">
																<select class="form-control" id="choose-role-code">
																	<option value="">--请选择角色编码--</option>
																	<option value="ADMIN">系统管理员</option>
																	<option value="MANAGER">总经理</option>
																	<option value="BUSSMAN">商务经理</option>
																	<option value="SALEMAN">销售经理</option>
																	<option value="OS">外部销售</option>
																	<option value="IS">内部销售</option>
																</select>
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">角色名称</label>
															<div class="col-sm-10">
																<input type="text" id="role-name" name="roleName"
																	class="form-control" placeholder="角色名称" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-2 control-label">角色描述</label>
															<div class="col-md-10">
																<input type="text" id="role-description"
																	name="roleDescription" class="form-control"
																	placeholder="角色描述" />
															</div>
														</div>
														<div class="form-group">
															<label class="col-md-2 control-label">菜单权限</label>
															<div class="col-md-10" id="menu-perms">
																<ul id="menu-perms-ztree" class="ztree"></ul>
															</div>
														</div>
														<div class="form-group">
															<label class="col-sm-2 control-label">&nbsp;</label>
															<div class="col-sm-10">
																<button type="button" class="btn btn-primary"
																	id="save-submit-btn">保存</button>
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
		<script type="text/javascript" src="static/js/role.js"></script>
		
		<script type="text/javascript"
			src="static/ztree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript"
			src="static/ztree/js/jquery.ztree.excheck.js"></script>
		<SCRIPT type="text/javascript">
			var menuPermsArr = new Array();
			var setting = {
				check: {
					enable: true,
					chkStyle: "checkbox",
				},
				data: {
					key: {
						name: "menuName"
					},
					simpleData: {
						enable:true,
						idKey: "menuId",
						pIdKey: "parentId",
						rootPId: 0
					}
				},
				callback: {
					onCheck: zTreeOnCheck,//checkbox / radio 被勾选 或 取消勾选的事件回调函数
					onClick: zTreeOnClick,//节点被点击的事件回调函数
				},
			};
			/*
			 * checkbox / radio 被勾选 或 取消勾选的事件回调函数
			 */
			function zTreeOnCheck(event, treeId, treeNode) {
				console.log("check:"+JSON.stringify(treeNode));
				//menuPermsArr = menuPermsArr.unique3();
				var checked = treeNode.checked;
				
				var menuId = treeNode.menuId;
				
				
				var pid = treeNode.parentId;
				if(pid!=null && pid>0){
					if(checked){
						menuPermsArr.push(pid);
					}else{
						menuPermsArr.remove3(pid);
					}
				}
				
				try{
					var isParent = treeNode.isParent;
					if(isParent){
						var children = treeNode.children;
						var len = children.length;
						for(var i=0; i<len; i++){
							var menu = children[i];
							var id = menu.menuId;
							var parent_id = menu.parentId;
							var flag = menu.checked;
							
							if(id!=null && id!=null && id>0){
								if(flag){
									menuPermsArr.push(id);
									menuPermsArr.push(menuId);
								}else{
									menuPermsArr.remove3(id);
									menuPermsArr.remove3(menuId);
								}
							}
						}
					}else{
						if(menuId!=null && menuId>0){
							if(checked){
								menuPermsArr.push(menuId);
							}else{
								menuPermsArr.remove3(menuId);
							}
						}
					}
				}catch(err){
					console.log("捕捉到例外，开始执行catch块语句 --->");
				 	console.log("错误名称: " + err.name+" ---> ");
					console.log("错误信息: " + err.message+" ---> ");
				}
				
				console.log("菜单权限："+menuPermsArr);
				
				console.log("去重后的菜单权限："+menuPermsArr.unique3());
				
				/* var treeObj = $.fn.zTree.getZTreeObj("category-ztree");
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
			    
			    console.log(idArr); */
			};
			
			/*
			 * 节点被点击的事件回调函数
			 */
			function zTreeOnClick(event, treeId, treeNode) {
				console.log("click:"+JSON.stringify(treeNode));
				console.log(treeNode.menuId + ", " + treeNode.menuName + ", " + treeNode.parentId);
			    //$("#add-btn").attr("onclick", "javascript:add(&quot;"+treeNode.id+"&quot;);");
				$("#add-btn").attr("onclick", "javascript:add("+treeNode.menuId+", '"+treeNode.menuName+"', '"+treeNode.parentId+"');");//修改添加按钮click事件
				$("#edit-btn").attr("onclick", "javascript:edit("+treeNode.menuId+", '"+treeNode.menuName+"', '"+treeNode.menuDescription+"', '"+treeNode.parentId+"', '"+treeNode.menuUrl+"', '"+treeNode.sortNum+"');");//修改编辑按钮click事件
				$("#del-btn").attr("onclick", "javascript:del("+treeNode.menuId+", '"+treeNode.menuName+"', '"+treeNode.parentId+"');");//修改删除按钮click事件
				
				
			}
			
			var zNodes = ${menuListJson};
			console.log("menuPermsListJson:"+JSON.stringify(zNodes));
			
			//$(document).ready(function() {
			var menuPermsObj = $.fn.zTree.init($("#menu-perms-ztree"), setting, zNodes);
			
			//});
			
			
			/*
			 * 数组去重
			 * 		调用方法：array.unique3();
			 */
			Array.prototype.unique3 = function(){
					var res = [];
					var json = {};
					for(var i = 0; i < this.length; i++){
						if(!json[this[i]]){
							res.push(this[i]);
							json[this[i]] = 1;
						}
					}
					return res;
				}
			/*
			 * 数组中删除指定元素
			 * 		调用方法：array.remove3("str");
			 * 		参数：要删除的对象
			 */
			Array.prototype.remove3 = function(str){
				 	if(str==null || str==""){
				 		return false;
				 	}
				 	var flag = false;
					for(var i=0; i<this.length; i++){
						if(this[i]==str){
							this.splice(i,1);
							flag = true;
							break;
						}
					}
					return flag;
				}
			/**
			 * 角色编码选择输入change事件
			 */
			$("#choose-role-code").on("change", function(){
				var roleCode = $(this).val();
				if(roleCode!=null && roleCode!=""){
					$("#role-code").val(roleCode);
				}else{
					$("#role-code").val("");
				}
			});
		</SCRIPT>
		
</body>
</html>
