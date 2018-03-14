
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
	    	name: {
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
	        }
	        /*linkTitle: {
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
 * 查看详细信息
 */
/*function selectDetails(id){
	var url = "back/category/selectUpdateById";
	var params = {"id":id};
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var resp = $.parseJSON(res);
			if(resp.result_code=="success"){
				var category =resp.category;
				$("#category-id").val(category.id);//ID
				$("#category-name").val(category.name);//名称
				$("#category-parentid").val(category.pid);//父类型ID
				
				$("#edit-tab").text("编辑");//修改选项卡标题为编辑
				
				$('#tabs-243687 a[href="#tab-2"]').tab('show');
				return;
			}
		}
		util.message("查询异常");
		
	});
}*/

/*
 * 保存内容提交
 */
$("#save-submit-btn").click(function(){
	//$("#save-form").submit();
	saveFun();
});
/*
 * 保存内容
 */
function saveFun(){
	var url = null;
	var id = $("#category-id").val();
	if(id==null || id==""){
		url = "back/category/insert";
	}else{
		url = "back/category/updateById";
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
	var url = "back/category/deleteById";
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
function del(id, name, parentid){
	if(id==0){
		util.message("请选择类型");
	}else{
		util.delConfirm("确认删除？", id, "deleteInfoAjaxRequest");
	}
}

/*
 * 重新加载当前菜单内容
 */
function reloadInfoFun(){
	//操作成功后重新加载
	var href = "back/category/selectItems";
	parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
	$("#items-div").load();
}

/*
 * 点击页面中的页码执行此函数
 * 		函数功能：根据页码数请求当前页内容
 */
function clickPageBtnRequestFun(params){
	var action = "back/category-brand/getBrandItemsByCategoryId";
	//util.loading();
	$("#item-div").load(action, params, function(){
		//TODO 加载完成后的操作
	});
}

/*
 * 点击添加按钮时执行，打开添加选项卡
 */
function add(id, name, level){
	resetFun();
	$("#category-parentid").val(id);//父类型ID
	$("#category-level").val((level+1));//级别
	//$("#category-parentname").val(name);//父类型名称
	
	$('#tabs-243687 a[href="#tab-2"]').tab('show');
}

/*
 * 点击编辑按钮时执行，打开编辑选项卡
 */
function edit(id, name, parentid, level){
	resetFun();
	if(id==0){
		alert("请选择要修改的商品类型");
		return;
	}
	$("#category-id").val(id);//父类型名称
	$("#category-name").val(name);//父类型名称
	$("#category-level").val(level);//级别
	$("#category-parentid").val(parentid);//父类型ID
	
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