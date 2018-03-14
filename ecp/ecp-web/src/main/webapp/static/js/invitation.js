
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
	$("#save-form").bootstrapValidator({
	    message: "This value is not valid",
	    feedbackIcons: {
	        valid: "glyphicon glyphicon-ok",
	        invalid: "glyphicon glyphicon-remove",
	        validating: "glyphicon glyphicon-refresh"
	    },
	    
	    fields: {
	    	invTitle: {
	            validators: {
	                notEmpty: {
	                    message: "邀请函主题不能为空"
	                },
	                stringLength: {
                        max: 30,
                        message: '长度不能超过30个字符'
                    }
	            }
	        },
	    	linkMan: {
	            validators: {
	                notEmpty: {
	                    message: "联系人不能为空"
	                },
	                regexp: {
		                regexp: "^[\u4e00-\u9fa5A-Za-z0-9_\\s+\\\\\/]+$",
		                message: "请勿输入特殊符号"
	                },
	                stringLength: {
                        max: 10,
                        message: '长度不能超过10个字符'
                    }
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
	    /*    phone: {
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
	        endDate: {
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
 * 查看详细信息
 */
function selectDetails(invitationId){
	var url = "front/invitation/selectUpdateById";
	var params = {"invitationId":invitationId};
	//show_loading();
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var resp = $.parseJSON(res);
			if(resp.result_code=="success"){
				var invitation =resp.invitation;
				$("#invitation-id").val(invitation.invitationId);//邀请函ID
				$("#inv-title").val(invitation.invTitle);//邀请函主题
				$("#link-man").val(invitation.linkMan);//联系人
				$("#phone").val(invitation.phone);//电话
				
				if(invitation.actDate.indexOf("-")>0){
					var dateArray=invitation.actDate.split("-");
					$("#act-date").val(dateArray[0]);//活动日期
					$("#end-date").val(dateArray[1]);//活动日期
				}else{
					$("#act-date").val(invitation.actDate);
				}
				$("#act-address").val(invitation.actAddress);//活动地点
				$("#inv-content").val(invitation.invContent);//内容
				
				$('#tabs-243687 a[href="#panel-909746"]').tab('show');
				return;
			}
		}
		show_err_msg("查询异常");
		
	});
}

/*
 * 保存内容提交
 */
$("#save-submit-btn").click(function(){
	var actDate=$("#act-date").val()+"-"+$("#end-date").val();
	$("#act-date").val(actDate);
	$("#save-form").submit();
});
/*
 * 保存内容
 */
function saveFun(){
	var url = null;
	var invitationId = $("#invitation-id").val();
	if(invitationId==null || invitationId==""){
		url = "front/invitation/insert";
	}else{
		url = "front/invitation/updateById";
	}
	
	show_loading();
	$("#save-form").ajaxSubmit({
		type:"post",
		url:url,
		success : function(res) {			console.log(res);
			if(res!=null){
				var obj = $.parseJSON(res);
				if(obj.result_code=="success"){
					//$("#update-menu-modal-div").modal("hide");//隐藏修改菜单对话框
					//show_err_msg(obj.result_msg);
					//window.location.href="loginSubmit";
					//操作成功后重新加载当前菜单内容
					reloadInfoFun();
				}else{
					show_err_msg(obj.result_err_msg);
				}
			}
		},
	});
}

/*
 * 删除信息AJAX请求
 */
function deleteInfoAjaxRequest(invitationId){
	var url = "front/invitation/deleteById";
	var params = {"invitationId":invitationId};
	show_loading();
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null && res!=""){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				reloadInfoFun();
			}else{
				show_err_msg(obj.result_err_msg);
			}
		}
		
	});
}
/*
 * 删除
 */
function deleteInfoFun(invitationId){
	//util.delConfirm("确认删除？", invitationId, "deleteInfoAjaxRequest");
	if(confirm("确认删除？")){
		deleteInfoAjaxRequest(invitationId);
	}
}

/*
 * 重新加载当前菜单内容
 */
function reloadInfoFun(){
	//操作成功后重新加载
	var href = "front/invitation/selectItem";
	show_msg("保存成功！", href);
}

/*
 * 重置form表单
 */
function resetFun(){
	console.log("reset");
	$("#save-form").data('bootstrapValidator').destroy();//销毁bootstrapValidator验证
	bootstrapValidateFun();//启用验证
	$('#save-form')[0].reset();
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