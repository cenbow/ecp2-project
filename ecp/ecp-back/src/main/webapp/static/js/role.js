
//$(function(){
	
	bootstrapValidateFun();//启用验证
	
//});

/*
 * bootstrap验证
 */
function bootstrapValidateFun(){
	/*
	 * bootstrapValidator验证
	 */
	$("#save-form").bootstrapValidator({
	    message: "This value is not valid",
	    feedbackIcons: {
	        valid: "glyphicon glyphicon-ok",
	        invalid: "glyphicon glyphicon-remove",
	        validating: "glyphicon glyphicon-refresh"
	    },
	    
	    fields: {
	    	roleName: {
	            validators: {
	                notEmpty: {
	                    message: "角色名称不能为空"
	                },
	                stringLength: {
                        max: 30,
                        message: '长度不能超过30个字符'
                    }
	            }
	        },
	    }
	}).on('success.form.bv',function(e){
	    e.preventDefault();
	    saveFun();//验证通过保存内容
	});
}

/*
 * 查看详细信息
 */
function selectDetails(id){
	resetFun();
	var url = "back/role/selectUpdateById";
	var params = {"id":id};
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var resp = $.parseJSON(res);
			if(resp.result_code=="success"){
				$("#edit-role-li").removeClass("hide");
				var role =resp.role;
				$("#role-id").val(role.roleId);//ID
				$("#role-code").val(role.roleCode);//角色编码
				$("#role-name").val(role.roleName);//角色名称
				$("#role-description").val(role.roleDescription);//角色描述
				
				var menuList = resp.menuList;
				var treeObj = $.fn.zTree.getZTreeObj("menu-perms-ztree");//获取菜单zTree对象
				$.each(menuList, function(){
					var nodes = treeObj.getNodesByParam("menuId", this.menuId, null);//获取需要设置的zTree对象节点（集合[{}]）
					var currNode = nodes[0];
					//console.log("被选择的节点："+JSON.stringify(nodes));
					//console.log("被选择的第0个节点："+JSON.stringify(currNode));
					if(currNode.isParent){//如果是父节点，选择时不联动
						treeObj.expandNode(nodes[0], true, false, false);//如果是父节点，则展开此节点
						treeObj.checkNode(nodes[0], true, false, true);//选择当前节点时不联动操作
					}else{//如果不是父节点时，选择时联动
						treeObj.checkNode(nodes[0], true, true, true);//选择当前节点时联动操作
					}
				});
				
				$('#tabs-243687 a[href="#tab-2"]').tab('show');
				return;
			}
		}
		util.message("查询异常");
		
	});
}

/*
 * 保存内容提交
 */
$("#save-submit-btn").click(function(){
	$("#save-form").submit();
});
/*
 * 保存内容
 */
function saveFun(){
	var url = null;
	var id = $("#role-id").val();
	if(id==null || id==""){
		url = "back/role/insert";
	}else{
		url = "back/role/updateById";
	}
	
	//util.loading();
	$("#save-form").ajaxSubmit({
		type:"post",
		url:url,
		data:{
			"menuPermissionIds" : menuPermsArr.unique3().toString(),
		},
		success : function(res) {
			console.log(res);
			if(res!=null){
				var obj = $.parseJSON(res);
				if(obj.result_code=="success"){
					//操作成功后重新加载当前菜单内容
					reloadInfoFun();
				}else{
					util.message(obj.result_err_msg);
				}
			}
		},
	});
}

/*
 * 删除信息AJAX请求（物理删除）
 */
function deleteInfoAjaxRequest(id){
	var url = "back/role/deleteById";
	var params = {"id":id};
	//util.loading();
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null && res!=""){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				reloadInfoFun();
			}else{
				util.message(obj.result_err_msg);
			}
		}
		
	});
}

/*
 * 单个删除
 */
function deleteInfoFun(id){
	util.delConfirm("确认删除？", id, "deleteInfoAjaxRequest");
}

/*
 * 重新加载当前菜单内容
 */
function reloadInfoFun(){
	//操作成功后重新加载
	var href = "back/role/selectItems?pagehelperFun=clickPageBtnRequestFun";
	parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
}

/*
 * 点击页面中的页码执行此函数
 * 		函数功能：根据页码数请求当前页内容
 */
function clickPageBtnRequestFun(params){
	var action = "back/role/selectItems";
	params.clickPageBtn = true;
	//util.loading();
	$("#item-div").load(action, params, function(){
	});
}

/*
 * 点击列表中某个复选框时，全选或反选
 */
function checkOne(){
    
    var flag = true;
    $("#role-table tbody input[type='checkbox']").each(function(){
    	if(!$(this).prop("checked")){
    		flag = false;
    	}
    });
    if(flag){
    	$("#role-table thead input[type='checkbox']").prop('checked', true);
    }else{
    	$("#role-table thead input[type='checkbox']").prop('checked', false);
    }
}
/*
 * 点击列表中All复选框时，列表全选或反选
 */
function checkAll(obj){
	$("#role-table tbody input[type='checkbox']").prop('checked', $(obj).prop('checked'));
}

/**
 * 点击添加按钮显示编辑选项卡
 */
$("#add-role-btn").click(function(){
	$("#edit-role-li").removeClass("hide");
	$('#tabs-243687 a[href="#tab-2"]').tab('show');
});

/*
 * 重置form表单
 */
function resetFun(){
	$("#save-form").data('bootstrapValidator').destroy();//销毁bootstrapValidator验证
	bootstrapValidateFun();//启用验证
	//$('#save-form')[0].reset();
	$(":input","#save-form")  
	 .not(":button, :submit, :reset")  
	 .val("")
	 //.removeAttr("checked")  
	 .removeAttr("selected");
	$.fn.zTree.init($("#menu-perms-ztree"), setting, zNodes);
	menuPermsArr.length = 0;
	$("#edit-role-li").addClass("hide");
}

/*
 * 日期转字符串格式函数
 * 调用方法：date.format('yyyy-MM-dd HH:mm:ss');
 */
Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "h+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}