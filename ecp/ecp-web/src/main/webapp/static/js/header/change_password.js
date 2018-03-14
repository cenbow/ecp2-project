/* reset dialog */
function resetDialog_change_password() {
	$("#change-password-form")[0].reset();
}

/*
 * 显示增加用户地址对话框
 */
function showDialog_change_password() {
	$('#modal-container-change-password').modal({
		backdrop : 'static',
		keyboard : false
	});
}

/* close dialog */
function closeDialog_change_password() {
	$("#modal-container-change-password").modal("hide");
}

/**
 * 保存新口令
 * @returns
 */
function saveNewPassword(){
	var url = BASE_CONTEXT_PATH + "/login/agent/updatepassword";

	// util.loading();
	$("#change-password-form").ajaxSubmit({
		type : "post",
		url : url,
		success : function(res) {
			console.log(res);
			if (res != null) {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					closeDialog_change_password();
					//util.message(obj.result_msg);
					util.message("成功修改口令！");

				} else {
					util.message(obj.result_err_msg);
				}
			}
		},
	});
}

const VALID_OK=0;
const VALID_REQUIRED=1;
const VALID_PASSWORD_NOT_SAME=2;

/**
 * 校验用户所输入的字段
 * @returns 如果校验成功，则返回0;否则返回错误码： 1：所需要输入的字段为空； 2：新口令两次输入不一致
 */
function validateFields(){
	var loginName=$("#loginName").val();
	var oldPassword=$("#oldPassword").val();
	var newPassword=$("#newPassword").val();
	var newPassRepeat=$("#newPassRepeat").val();
	
	if(!$.isBlank(loginName)  && !$.isBlank(oldPassword) && !$.isBlank(newPassword) && !$.isBlank(newPassRepeat) ){
		console.log("newPassword"+newPassword);
		console.log("newPasswordRepeat"+newPassRepeat);
		if(newPassword==newPassRepeat)
			return VALID_OK;
		else
			return VALID_PASSWORD_NOT_SAME;
	}
	else{
		return VALID_REQUIRED;
	}
}

$(function(){
	
	/* 显示修改用户口令对话框*/
	$("#change-password").on("click",function(){
		resetDialog_change_password();
		showDialog_change_password();
	});
	
	
	/*
	 * 地址编辑对话框：【保存】用户地址
	 */
	$("#btnSaveChangePassword").on('click', function(e) {
		var resultVal=validateFields();  //效验字段
		switch(resultVal){
			case VALID_OK:
				closeDialog_change_password(); // close dialog
				saveNewPassword();
				break;
			case VALID_REQUIRED:
				util.message("需输入必要字段！");
				break;
			case VALID_PASSWORD_NOT_SAME:
				util.message("两次输入的口令不一致！");
				break;
		}
		
	});
	
	
});