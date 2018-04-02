
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
	    	roleId: {
	            validators: {
	                notEmpty: {
	                    message: "请选择角色"
	                },
	            }
	        },
	    	completionRate: {
	            validators: {
	                notEmpty: {
	                    message: "完成比例不能为空"
	                },
	                stringLength: {
                        max: 20,
                        message: '长度不能超过20个字符'
                    },
	            }
	        },
	        pushmoneyRate: {
	            validators: {
	                notEmpty: {
	                    message: "提成比例不能为空"
	                },
	                stringLength: {
                        max: 20,
                        message: '长度不能超过20个字符'
                    },
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
	var url = "back/pushmoney-config/select-update";
	var params = {"id":id};
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var resp = $.parseJSON(res);
			if(resp.result_code=="success"){
				$("#edit-pushmoney-config-li").removeClass("hide");
				var config =resp.pushmoneyConfig;
				$("#config-id").val(config.id);//ID
				$("#role-id").val(config.roleId);//角色ID
				$("#config-completion-rate").val(config.completionRate);//销售额完成比例
				$("#config-pushmoney-rate").val(config.pushmoneyRate);//提成比例
				$("#config-comment").val(config.comment);//备注
				
				$('#tabs-pushmoney-config a[href="#tab-pushmoney-config-edit"]').tab('show');
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
	var id = $("#level-id").val();
	if(id==null || id==""){
		url = "back/pushmoney-config/insert";
	}else{
		url = "back/pushmoney-config/update";
	}
	
	//util.loading();
	$("#save-form").ajaxSubmit({
		type:"post",
		url:url,
		data:{
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
 * 删除信息AJAX请求（逻辑删除）
 */
function deleteInfoAjaxRequest(id){
	var url = "back/pushmoney-config/delete";
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
	var href = "back/pushmoney-config/select-items?pagehelperFun=clickPageBtnRequestFun";
	parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
}

/*
 * 点击页面中的页码执行此函数
 * 		函数功能：根据页码数请求当前页内容
 */
function clickPageBtnRequestFun(params){
	var action = "back/pushmoney-config/select-items";
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
    $("#pushmoney-config-table tbody input[type='checkbox']").each(function(){
    	if(!$(this).prop("checked")){
    		flag = false;
    	}
    });
    if(flag){
    	$("#pushmoney-config-table thead input[type='checkbox']").prop('checked', true);
    }else{
    	$("#pushmoney-config-table thead input[type='checkbox']").prop('checked', false);
    }
}
/*
 * 点击列表中All复选框时，列表全选或反选
 */
function checkAll(obj){
	$("#pushmoney-config-table tbody input[type='checkbox']").prop('checked', $(obj).prop('checked'));
}

/**
 * 点击添加按钮显示添加编辑选项卡
 */
$("#add-pushmoney-config-btn").click(function(){
	$("#edit-pushmoney-config-li").removeClass("hide");
	$('#tabs-pushmoney-config a[href="#tab-pushmoney-config-edit"]').tab('show');
});

/*
 * 重置form表单
 */
function resetFun(){
	$("#save-form").data('bootstrapValidator').destroy();//销毁bootstrapValidator验证
	bootstrapValidateFun();//启用验证
	//$('#save-form')[0].reset();
	$(":input","#save-form")  
	 .not(":button, :submit, :reset, :checkbox, :radio")  
	 .val("")
	 .removeAttr("checked");  
	 //.removeAttr("selected");
	$("#edit-pushmoney-config-li").addClass("hide");
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