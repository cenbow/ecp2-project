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

$(function() {

	/* 保存签约用户 */
	$("#btnSave").on('click', function(e) {
		saveFun();
	});

});