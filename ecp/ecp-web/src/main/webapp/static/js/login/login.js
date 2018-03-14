var BASE_CONTEXT_PATH = $('meta[name=context-path]').attr("content");
BASE_CONTEXT_PATH = BASE_CONTEXT_PATH.substr(0, BASE_CONTEXT_PATH.length - 1);

/* AJAX登录 */
function login() {

	var url = BASE_CONTEXT_PATH + "/login/agent/login"; // 登录地址
	var mainPageUrl = BASE_CONTEXT_PATH + "/front/home/home"; // 主页地址
	
	var publicKey=$('#publicKey').val();  //获取PUBLIC_KEY
	
	var data = [];
    data['loginName'] = $('#loginName').val();
    data['password'] = $('#password').val();
	
	
	var encrypt = new JSEncrypt();
    encrypt.setPublicKey(publicKey);
    // ajax请求发送的数据对象
    var sendData = new Object();
    // 将data数组赋给ajax对象
    for (var key in data) {
        sendData[key] = encrypt.encrypt(data[key]);
    }
	

	$.ajax({
		type : "post",
		data: sendData,
        /*dataType: 'json',*/
        /*contentType: 'application/json; charset=utf-8',*/
		url : url,
		success : function(res) {
			console.log(res);
			if (res != null) {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					// util.message(obj.result_msg);
					self.location.href = mainPageUrl;
				} else {
					// util.message(obj.result_err_msg);
					util.message("用户名称或口令错误！");
				}
			}
		},
	});
}

$(function() {
	// 登录按钮
	$("#btn-login").on("click", function() {
		login();
	});

	// 在口令输入框按下回车键后,开始登录
	$("#password").on('keyup', function(event) {
		if (event.keyCode == 13)
			login();
	});

});
