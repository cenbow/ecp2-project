// JavaScript Document
//支持Enter键登录
document.onkeydown = function(e) {
	if (!e)
		e = window.event;
	if ((e.keyCode || e.which) == 13) {
		var obtnLogin = document.getElementById("submit_btn")
		obtnLogin.focus();
	}
}

$(function() {
	// 提交表单
	$('#submit_btn').click(function() {
		util.loading();
		var myReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; // 邮件正则
		if ($('#username').val() == '') {
			util.loaded();
			util.message('登录名还没填呢！');
			$('#username').focus();
		}else if ($('#password').val() == '') {
			util.loaded();
			util.message('密码还没填呢！');
			$('#password').focus();
		} else if ($('#kaptcha').val() == '') {
			util.loaded();
			util.message('验证码还没填呢！');
			$('#kaptcha').focus();
		} else {
			
			var username = $('#username').val();
			var password = $('#password').val();
			
			var publicKey = $('#public-key').val();  //获取PUBLIC_KEY
			var encrypt = new JSEncrypt();
		    encrypt.setPublicKey(publicKey);
		    
		    $('#encrypt-username').val(encrypt.encrypt(username));
		    $('#encrypt-password').val(encrypt.encrypt(password));
			
			$('#login_form').submit();
			/*// window.location.href="index.html";
			var username = $('#username').val();
			var password = $('#password').val();
			var kaptcha = $('#kaptcha').val();
			// ajax提交表单，#login_form为表单的ID。
			// 如：$('#login_form').ajaxSubmit(function(data) { ... });
			var url = "login";
			var params = {
					'username' : username,
					'password' : password,
					'kaptcha' : kaptcha
				};
			$.post(url, params, function(res){
				console.log(res);
				if (res != null) {
					var obj = $.parseJSON(res);
					if (obj.result_code == "success") {
						util.message('登录成功咯！  正在为您跳转...', 'back/main', 'success');
					} else {
						util.message(obj.result_err_msg);
					}
				}
			});*/
		}
	});
});