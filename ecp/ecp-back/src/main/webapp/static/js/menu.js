
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
	    	menuName: {
	            validators: {
	                notEmpty: {
	                    message: "名称不能为空"
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
		    menuDescription: {
	            validators: {
	                notEmpty: {
	                    message: "描述不能为空"
	                },
	                regexp: {
		                regexp: "^[\u4e00-\u9fa5A-Za-z0-9_\\s+\\\\\/]+$",
		                message: "请勿输入特殊符号"
	                },
	                stringLength: {
	                    max: 100,
	                    message: '长度不能超过100个字符'
	                }
	            }
	        },
	        menuUrl: {
	            validators: {
	                /*notEmpty: {
	                    message: "链接不能为空"
	                },*/
	                regexp: {
		                regexp: "^[A-Za-z\\\\\/?=-]+$", 
		                message: "请输入正确的链接"
	                },
	                stringLength: {
	                    max: 200,
	                    message: '长度不能超过200个字符'
	                }
	            }
	        },
	        sortNum: {
	            validators: {
	                notEmpty: {
	                    message: "排序不能为空"
	                },
	                regexp: {
	                    regexp: /^[0-9]*$/,
	                    message: "只能输入数字"
	                },
	                stringLength: {
	                    max: 10,
	                    message: '长度不能超过10个字符'
	                }
	            }
	        },
	        /*menuDescription: {
	            validators: {
	                notEmpty: {
	                    message: "标题不能为空"
	                },
	                regexp: {
		                regexp: "^[\u4e00-\u9fa5A-Za-z0-9_\\s+\\\\\/]+$",
		                message: "请勿输入特殊符号"
	                },
	                stringLength: {
                        max: 5,
                        message: '长度不能超过5个字符'
                    }
	                regexp: {
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
	                }
	            }
	        },
	        linkUrl: {
	            validators: {
	                notEmpty: {
	                    message: "链接不能为空"
	                },
	                regexp: {
		                regexp: /[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\.?/, 
		                message: "请输入正确的URL"
	                },
	                stringLength: {
                        max: 280,
                        message: '长度不能超过280个字符'
                    }
	            }
	        },*/
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
	$("#save-form").submit();
});
/*
 * 保存内容
 */
function saveFun(){
	var url = null;
	var id = $("#menu-id").val();
	if(id==null || id==""){
		url = "back/menu/insert";
	}else{
		url = "back/menu/updateById";
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
					reloadInfoFun();
				}else{
					util.message(obj.result_err_msg);
				}
			}
		},
	});
}

/*
 * 删除信息AJAX请求
 */
function deleteInfoAjaxRequest(id){
	var url = "back/menu/deleteById";
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

//var idArr = new Array();//保存用户选择checkbox的id，用于删除

/*
 * 点击删除按钮时执行
 */
function del(id, name, pid){
	if(id==0){
		util.message("请选择菜单");
	}else{
		util.delConfirm("确认删除？", id, "deleteInfoAjaxRequest");
	}
}

/*
 * 重新加载当前菜单内容
 */
function reloadInfoFun(){
	//操作成功后重新加载
	var href = "back/menu/selectItems";
	parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
}

/*
 * 点击添加按钮时执行，打开添加选项卡
 */
function add(id, name, pid){
	resetFun();
	$("#edit-menu-li").removeClass("hide");
	$("#parent-id").val(id);//父类型ID
	
	$('#tabs-243687 a[href="#tab-2"]').tab('show');
}

/*
 * 点击编辑按钮时执行，打开编辑选项卡
 */
function edit(id, name, icon, description, pid, url, sort){
	if(id==0){
		util.message("请选择要修改的菜单");
		return;
	}
	resetFun();
	$("#edit-menu-li").removeClass("hide");
	if((icon && icon!="undefined") || icon>0){
		$("#menu-icon").val(icon);//菜单图标样式
	}
	$("#menu-id").val(id);//ID
	$("#menu-name").val(name);//名称
	
	$("#menu-description").val(description);//描述
	$("#parent-id").val(pid);//父类型ID
	$("#menu-url").val(url);//URL
	$("#sort-num").val(sort);//排序
	
	$('#tabs-243687 a[href="#tab-2"]').tab('show');
}

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
	$("#edit-menu-li").addClass("hide");
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