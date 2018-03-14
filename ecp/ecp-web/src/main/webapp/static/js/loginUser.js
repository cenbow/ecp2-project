
$(function(){
	
	// bootstrapValidateFun();//启用验证
});
bootstrapValidateFun();// 启用验证

/*
 * bootstrap验证
 */
function bootstrapValidateFun(){
	/*
	 * bootstrapValidator验证
	 */
	$("#customerForm").bootstrapValidator({
	    message: "This value is not valid",
	    feedbackIcons: {
	        valid: "glyphicon glyphicon-ok",
	        invalid: "glyphicon glyphicon-remove",
	        validating: "glyphicon glyphicon-refresh"
	    },
	    
	    fields: {
	    	loginName: {
	            validators: {
	                notEmpty: {
	                    message: "登录名不能为空"
	                },
	                stringLength: {
                        max: 20,
                        message: '长度不能超过20个字符'
                    }
	            }
	        },
	        loginPass: {
	            validators: {
	                notEmpty: {
	                    message: "登录密码不能为空"
	                },
	                regexp: {
		                regexp: "^[\u4e00-\u9fa5A-Za-z0-9_\\s+\\\\\/]+$",
		                message: "请勿输入特殊符号"
	                },
	                stringLength: {
                        max: 30,
                        message: '长度不能超过30个字符'
                    }
	            }
	        },
	        email: {
	            validators: {
	                notEmpty: {
	                    message: "电子邮箱账号不能为空"
	                },
	            }
	        },
	    }
	}).on('success.form.bv',function(e){
	    e.preventDefault();
	    saveFun();// 验证通过保存内容
	});
}

/*
 * 保存内容提交
 */
$("#save-submit-btn").click(function(){
	$("#customerForm").submit();
});
/*
 * 保存内容
 */
function saveFun(){
	
	show_loading();
	$("#customerForm").ajaxSubmit({
		type:"POST",
   		data:{
   		},
   		url:'front/user/saveOrUpdate',
   		dataType:'json',
   		success:function(data){
   			if(data){
   				show_msg("保存成功！", "front/user/manager");
   			}else{
   				show_err_msg("此用户名已经存在！");
   				return;
   			}
   			
   		},
   		error:function(){
   			show_err_msg("请求异常！！！");
   		}
	});
}

function addUser(customerId,type){
   	$('#tabs-243687 a[href="#panel-909746"]').tab('show');
		$("#customerId").val(customerId);
   	if(type==0){
   		resetFun();
   	}else{
   		$.ajax({
       		type:"POST",
       		url:'front/customer/detail',
       		data:{
       			customerId:customerId
       		},
       		dataType:'json',
       		success:function(res){
       			if(res!=null){
       				var name=res.loginName;
           			searchUserByName(name);
       			}
       		},
       		error:function(){
       			show_err_msg("请求异常！！！");
       		}
   		})
   	}
}
   
function searchUserByName(loginName){
   	
   	$.ajax({
   		type:"POST",
   		url:'front/user/search',
   		data:{
   			loginName:loginName
   		},
   		dataType:'json',
   		success:function(data){
   			if(data.success){
   				$("#loginId").val(data.loginUser.loginId);
   				$("#loginName").val(data.loginUser.loginName);
   				$("#loginPass").val(data.loginUser.loginPass);
   				$("#loginPass").attr("readonly","readonly");
   				$("#loginName").attr("readonly","readonly");
   				$("#email").val(data.loginUser.email);
   			}
   		}
   	});
}
   
/*
 * 重置form表单
 */
function resetFun(){
   	console.log("reset");
   	$("#customerForm").data('bootstrapValidator').destroy();// 销毁bootstrapValidator验证
   	bootstrapValidateFun();// 启用验证
   	$('#customerForm')[0].reset();
   	/*
	 * $(":input","#customerForm") .not(":button, :submit, :reset") .val("")
	 * //.removeAttr("checked") .removeAttr("selected"); 
	 */
}

/*
 * 日期转字符串格式函数 调用方法：date.format('yyyy-MM-dd HH:mm:ss');
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