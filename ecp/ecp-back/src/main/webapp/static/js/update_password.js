
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
	$("#update-form").bootstrapValidator({
	    message: "This value is not valid",
	    feedbackIcons: {
	        valid: "glyphicon glyphicon-ok",
	        invalid: "glyphicon glyphicon-remove",
	        validating: "glyphicon glyphicon-refresh"
	    },
	    
	    fields: {
	        password: {
	            validators: {
	                notEmpty: {
	                    message: "原密码不能为空"
	                },
	                stringLength: {
                        max: 20,
                        message: '长度不能超过20个字符'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                        url: "back/user/checkPasswordValid",//验证地址
                        message: "原密码输入错误",//提示消息
                        delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST'//请求方式
                        /**自定义提交数据，默认值提交当前input value
                         *  data: {
								username: $("#username").val(),
                           }
                         */
                    },
	            }
	        },
	        newPassword: {
	            validators: {
	                notEmpty: {
	                    message: "新密码不能为空"
	                },
	                stringLength: {
                        max: 20,
                        message: '长度不能超过20个字符'
                    },
                    different: {//不能和原密码相同
                        field: 'password',//需要进行比较的input name值
                        message: '不能和原密码相同'
                    },
                    identical: {//相同
                        field: 'reNewPassword',
                        message: '两次密码不一致'
                    },
	            }
	        },
	        reNewPassword: {
	            validators: {
	                notEmpty: {
	                    message: "新密码不能为空"
	                },
	                stringLength: {
                        max: 20,
                        message: '长度不能超过20个字符'
                    },
                    identical: {//相同
                        field: 'newPassword',
                        message: '两次密码不一致'
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
 * 保存内容提交
 */
$("#save-submit-btn").click(function(){
	$("#update-form").submit();
});
/*
 * 保存内容
 */
function saveFun(){
	//util.loading();
	$("#update-form").ajaxSubmit({
		type:"post",
		url:"back/user/updatePassword",
		data:{
		},
		success : function(res) {
			console.log(res);
			if(res!=null){
				var obj = $.parseJSON(res);
				if(obj.result_code=="success"){
					util.message("修改密码成功，请重新登录！", "logout");
				}else{
					util.message(obj.result_err_msg);
				}
			}
		},
	});
}

/*
 * 重置form表单
 */
function resetFun(){
	$("#update-form").data('bootstrapValidator').destroy();//销毁bootstrapValidator验证
	bootstrapValidateFun();//启用验证
	//$('#update-form')[0].reset();
	$(":input","#update-form")  
	 .not(":button, :submit, :reset")  
	 .val("")
	 //.removeAttr("checked")  
	 .removeAttr("selected");
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