
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
	        brandName: {
	            validators: {
	                notEmpty: {
	                    message: "品牌名称不能为空"
	                },
	                regexp: {
		                regexp: "^[\u4e00-\u9fa5A-Za-z0-9_\\s+\|\\\\\/]+$",
		                message: "请勿输入特殊符号"
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
	var url = "back/brand/selectUpdateById";
	var params = {"id":id};
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var resp = $.parseJSON(res);
			if(resp.result_code=="success"){
				$("#edit-brand-li").removeClass("hide");
				var brand =resp.brand;
				$("#brand-id").val(brand.brandId);//ID
				$("#brand-name").val(brand.brandName);//等级名称
				$("#brand-logo-url").val(brand.brandLogoUrl);//logo图片地址
				$("#logo-img-portrait").html("<img src='"+brand.brandLogoUrl+"' style='width:100px;' />");//logo图片缩略图
				
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
	var id = $("#brand-id").val();
	if(id==null || id==""){
		url = "back/brand/insert";
	}else{
		url = "back/brand/updateById";
	}
	
	//util.loading();
	$("#save-form").ajaxSubmit({
		type:"post",
		url:url,
		success : function(res) {
			console.log(res);
			if(res!=null){
				var obj = $.parseJSON(res);
				if(obj.result_code=="success"){
					//$("#update-menu-modal-div").modal("hide");//隐藏修改菜单对话框
					//util.message(obj.result_msg);
					//window.location.href="loginSubmit";
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
	var url = "back/brand/deleteById";
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
 * 批量删除
 */
/*function batchDeleteInfoFun(){
	var idArr = new Array();
    $("#user-table tbody input[type='checkbox']:checked").each(function(){
    	idArr.push($(this).val());
    });
    if(idArr.length<=0){
    	util.message("请选择列表中的内容！");
    	return;
    }
	util.delConfirm("确认选中删除？", 0, "batchDeleteInfoAjaxRequest");
}*/
/*
 * 批量删除信息AJAX请求（逻辑删除）
 */
/*function batchDeleteInfoAjaxRequest(){
	
	var idArr = new Array();
    $("#user-table tbody input[type='checkbox']:checked").each(function(){
    	idArr.push($(this).val());
    });
    var url = "front/user/deleteByIds";
	var params = {"userIds":idArr.toString()};
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
}*/

/*
 * 重新加载当前菜单内容
 */
function reloadInfoFun(){
	//操作成功后重新加载
	var href = "back/brand/selectItems?pagehelperFun=clickPageBtnRequestFun";
	parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
}

/*
 * 点击页面中的页码执行此函数
 * 		函数功能：根据页码数请求当前页内容
 */
function clickPageBtnRequestFun(params){
	var action = "back/brand/selectItems";
	params.clickPageBtn = true;
	//util.loading();
	$("#item-div").load(action, params, function(){
		//TODO 加载完成后的操作
	});
}

/*
 * 点击列表中某个复选框时，全选或反选
 */
function checkOne(){
    
    var flag = true;
    $("#brand-table tbody input[type='checkbox']").each(function(){
    	if(!$(this).prop("checked")){
    		flag = false;
    	}
    });
    if(flag){
    	$("#brand-table thead input[type='checkbox']").prop('checked', true);
    }else{
    	$("#brand-table thead input[type='checkbox']").prop('checked', false);
    }
}
/*
 * 点击列表中All复选框时，列表全选或反选
 */
function checkAll(obj){
	$("#brand-table tbody input[type='checkbox']").prop('checked', $(obj).prop('checked'));
}

/**
 * 点击添加按钮时显示编辑选项卡
 */
$("#add-brand-btn").click(function(){
	$("#edit-brand-li").removeClass("hide");
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
	$("#edit-brand-li").addClass("hide");
}

/*
 * 确认删除已上传图片对话框
 */
function deleteImgFun(imgFlag){
	util.delConfirm("确认删除？", imgFlag, "deleteImgAjaxRequestFun");
}
/*
 * ajax请求删除已上传图片
 */
function deleteImgAjaxRequestFun(imgFlag){
	
	var id = $("#brand-id").val();//ID
	var logoUrl = $("#brand-logo-url").val();//logo路径
	if(id==null || id<=0){
		util.message("删除上传文件，ID不能为空！");
		return;
	}
	
	var url = "back/content/deleteUploadFileById";
	var params = new Object();
	params.id = id;
	
	var portraitId = null;
	if(imgFlag!=null && imgFlag=="logo"){
		if(logoUrl==null || logoUrl.length<=0){
			util.message("logo不能为空！");
			return;
		}
		params.brandLogoUrl = logoUrl;
		portraitId = "logo-img-portrait";//logo预览图的ID
	}
	
	
	
	//util.loading();
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null && res!=""){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				if(imgFlag!="attached"){
					$("#"+portraitId).html("");
				}
				util.message(obj.result_msg);
			}else{
				util.message(obj.result_err_msg);
			}
		}
		
	});
}

/*
 * 验证图片文件大小
 */
function validateImgFileSizeFun(file){
	if(isAllowUploadFile(file, 5120, '上传logo图不能大于5M！')){
		showPreview(file, 'logo-img-portrait');
		$("#save-content-submit-btn").attr("disabled", false);
	}else{
		$("#save-content-submit-btn").attr("disabled", true);
	}
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