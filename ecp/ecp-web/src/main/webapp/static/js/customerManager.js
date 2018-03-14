
$(function(){
	
	//bootstrapValidateFun();//启用验证
});
bootstrapValidateFun();//启用验证

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
	    	cusName: {
	            validators: {
	                notEmpty: {
	                    message: "客户姓名不能为空"
	                },
	                regexp: {
	                    regexp: '^[\u4e00-\u9fa5A-Za-z0-9_\\s+\\\\\/\\.+\\(+\\)+\\*+\\/+]+$',
	                    message: "请勿输入特殊符号"
	                },
	                stringLength: {
                        max: 30,
                        message: '长度不能超过30个字符'
                    }
	            }
	        },
	        cusType: {
	            validators: {
	                notEmpty: {
	                    message: "联系人不能为空"
	                },
	                /*regexp: {
	                    regexp: '^[\u4e00-\u9fa5A-Za-z0-9_\\s+\\\\\/\\.+\\(+\\)+\\*+\\/+]+$',
	                    message: "请勿输入特殊符号"
	                },
	                regexp: {
	        			regexp: "^(([0-9]|([1-9][0-9]{0,9})))$",
	        			message: "请输入正确数额"
	        		},
	        		regexp: {
	                    regexp: /^[0-9]*$/,
	                    message: "只能输入数字"
	                },
	                regexp: {
	                    regexp: "^([1-9][0-9]*)$",
	                    message: "请输入不为0的数字"
	                }*/
	            }
	        },
	      /*  mobile: {
	            validators: {
	                notEmpty: {
	                    message: "手机号不能为空"
	                },
	                regexp: {
		                regexp: '^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$',
		                message: "请输入正确的手机号"
	                },
	            }
	        },*/
	        actDate: {
	            validators: {
	                notEmpty: {
	                    message: "日期不能为空"
	                },
	            }
	        },
	        actAddress: {
	            validators: {
	                notEmpty: {
	                    message: "活动地点不能为空"
	                },
	                stringLength: {
                        max: 30,
                        message: '长度不能超过30个字符'
                    }
	            }
	        },
	        invContent: {
	            validators: {
	                notEmpty: {
	                    message: "内容不能为空"
	                },
	                stringLength: {
                        max: 100,
                        message: '长度不能超过100个字符'
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
		url:'front/customer/saveOrUpdate',
		dataType:'json',
		success:function(data){
			show_msg("保存成功！", "front/customer/manager");
		},
		error:function(){
			show_err_msg("请求异常！！！");
		}
	});
}
/*
 * 一键删除客户
 */
function deleteAll(){
	if(confirm("确定删除所有客户？")){
		show_loading();
		$.ajax({
    		type:"POST",
    		data:{
    		},
    		url:'front/customer/deleteAll',
    		dataType:'json',
    		success:function(data){
    			show_msg("保存成功！", "front/customer/manager");
    		},
    		error:function(){
    			show_err_msg("请求异常！！！");
    		}
    	});
	}else{
		return;
	}
}

/*
 * 查看详细信息
 */
function detailCustomer(customerId){
	//show_loading();
	$.ajax({
		type:"POST",
		data:{
			customerId:customerId
		},
		url:'front/customer/detail',
		dataType:'json',
		success:function(data){
			if(data!=null){
				$("#customerId").val(data.customerId);
				$("#cusName").val(data.cusName);
				$("#cusType").val(data.cusType);
				$("#mobile").val(data.mobile);
        		
        		$('#tabs-243687 a[href="#panel-909746"]').tab('show');
			}
		},
		error:function(){
			show_err_msg("请求异常！！！");
		}
	});
}

/*
 * 删除
 */
function deleteCustomer(customerId){
	if(confirm("确定删除数据")){
		show_loading();
		$.ajax({
    		type:"POST",
    		data:{
    			customerId:customerId
    		},
    		url:'front/customer/delete',
    		dataType:'json',
    		success:function(data){
    			show_msg("保存成功！", "front/customer/manager");
    		},
    		error:function(){
    			show_err_msg("请求异常！！！");
    		}
    	});
	}else{
		return;
	}
}
/*
 * 重置form表单
 */
function resetFun(){
	console.log("reset");
	$("#customerForm").data('bootstrapValidator').destroy();//销毁bootstrapValidator验证
	bootstrapValidateFun();//启用验证
	$('#customerForm')[0].reset();
	/*$(":input","#customerForm")  
	 .not(":button, :submit, :reset, :select")  
	 .val("");
	 //.removeAttr("checked")  
	 .removeAttr("selected");*/
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