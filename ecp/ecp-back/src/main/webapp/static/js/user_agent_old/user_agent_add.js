/*
 * 上传前预览图片
 */
function preview(file) {
	// alert(file.id);
	if (isAllowUploadFile(file, 5120, '上传logo图不能大于5M！')) {
		showPreview(file, file.id + '_prev');
		// $("#save-content-submit-btn").attr("disabled", false);
	} else {
		// $("#save-content-submit-btn").attr("disabled", true);
	}
}

/* 保存内容 */
function saveFun() {
	var url = null;

	url = BASE_CONTEXT_PATH + "/back/agent/insert";

	// util.loading();
	$("#user-agent-form").ajaxSubmit({
		type : "post",
		url : url,
		success : function(res) {
			console.log(res);
			if (res != null) {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					// $("#update-menu-modal-div").modal("hide");//隐藏修改菜单对话框
					//util.message(obj.result_msg);
					// window.location.href="loginSubmit";					
					// reloadInfoFun();
					//window.opener.reloadPage();  //刷新父窗口中的网页  操作成功后重新加载当前菜单内容
					//window.close();//关闭当前窗窗口
					
					resetTab();//重置选项卡
					reloadPage();//重新加载列表
				} else {
					util.message(obj.result_err_msg);
				}
			}
		},
	});
}





//---------------------------区域操作-----------------------------
/**
 * 复位地区列表
 * @returns
 */
function resetDistPickerDeep(){
	 var $distpicker = $('#distpicker');
	 $distpicker.distpicker('reset', true);
}

/**
 * 同步更新地区编码、名称
 * @returns
 */
function syncUpdateHiddenAreaCode(){
	console.log("debug");
	var code= $("#province").find("option:selected").attr("data-code");  //省级编码、名称
	var name= $("#province").find("option:selected").val();
	console.log("name:"+name);
	$("#provinceCode").val(code);
	$("#provinceName").val(name);
	
	console.log("debug end");
	
	
	code= $("#city").find("option:selected").attr("data-code");  //市级编码、名称
	name= $("#city").find("option:selected").val();
	$("#cityCode").val(code);
	$("#cityName").val(name);
	
	
	
	code= $("#district").find("option:selected").attr("data-code");  //县区级编码、名称
	name= $("#district").find("option:selected").val();
	$("#countyCode").val(code);
	$("#countyName").val(name);
	
	
}

//页面ready
$(function() {
	resetDistPickerDeep();
	//syncUpdateHiddenAreaCode();  //初始化三级地址编码及三级地址名称字段(hidden类型)
	
	/* 保存签约用户 */
	$("#btnSave").on('click', function(e) {
		saveFun();
	});
	
	
	//==================地区选择====================
	/*
	 *当“省份”变化时，将code赋值给value; 
	 */
	$("#province").on("change",function(){
		syncUpdateHiddenAreaCode();
	});
	
	/*
	 *当“市级”变化时，将ode赋值给value; 
	 */
	$("#city").on("change",function(){
		syncUpdateHiddenAreaCode();
	});
	
	/*
	 *当“县级”变化时，将code赋值给value; 
	 */
	$("#district").on("change",function(){
		syncUpdateHiddenAreaCode();
	});
	
	

});