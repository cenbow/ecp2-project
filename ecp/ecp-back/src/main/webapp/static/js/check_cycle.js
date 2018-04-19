
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
	    	yearName: {
	            validators: {
	                notEmpty: {
	                    message: "年度名称不能为空"
	                },
	                regexp: {
		                regexp: "^[\u4e00-\u9fa5A-Za-z0-9_\\s+\\\\\/]+$",
		                message: "请勿输入特殊符号"
	                },
	                stringLength: {
                        max: 20,
                        message: '长度不能超过20个字符'
                    },
	            }
	        },
	        cycleName: {
	            validators: {
	                notEmpty: {
	                    message: "周期名称不能为空"
	                },
	                regexp: {
		                regexp: "^[\u4e00-\u9fa5A-Za-z0-9_\\s+\\\\\/]+$",
		                message: "请勿输入特殊符号"
	                },
	                stringLength: {
                        max: 20,
                        message: '长度不能超过20个字符'
                    },
                    /*remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                        url: "back/user/checkUsernameValid",//验证地址
                        message: "用户名已存在",//提示消息
                        delay :  1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST',//请求方式
                        //自定义提交数据，默认值提交当前input value
                           //data: {
                    			//username: $("[name='username']").val(),
                    			//userid: $("[name='id']").val(),
                           //}
                        data: function(validator) {
                        	return {
                            	//password: $('[name="passwordNameAttributeInYourForm"]').val(),
                            	//whatever: $('[name="whateverNameAttributeInYourForm"]').val()
                        		username: $("[name='username']").val(),
                    			userid: $("[name='id']").val(),
                        	};
                        }
                    },*/
	            }   
	        },
	        calType: {
	            validators: {
	                notEmpty: {
	                    message: "请选择时间段计法"
	                },
	            }
	        },
	        startDateStr: {
	            validators: {
	                notEmpty: {
	                    message: "请选择开始时间"
	                },
	            }
	        },
	        endDateStr: {
	            validators: {
	                notEmpty: {
	                    message: "请选择结束时间"
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
 * 查看详细信息
 */
function selectDetails(id){
	resetFun();
	var url = "back/check-cycle/select-update";
	var params = {"id":id};
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var resp = $.parseJSON(res);
			if(resp.result_code=="success"){
				$("#edit-check-cycle-li").removeClass("hide");
				var cycle =resp.checkCycle;
				$("#check-cycle-id").val(cycle.id);//ID
				$("#check-cycle-year-name").val(cycle.yearName);//年度名称
				$("#check-cycle-name").val(cycle.cycleName);//周期名称
				$("#check-cycle-cal-type").val(cycle.calType);//时间段计法
				$("#check-cycle-start-date-str").val(cycle.startDateStr);//开始时间
				$("#check-cycle-end-date-str").val(cycle.endDateStr);//结束时间
				
				$('#tabs-check-cycle a[href="#tab-check-cycle-edit"]').tab('show');
				return;
			}
		}
		util.message("查询异常");
		
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
	var id = $("#check-cycle-id").val();
	if(id==null || id==""){
		url = "back/check-cycle/insert";
	}else{
		url = "back/check-cycle/update";
	}
	
	//util.loading();
	$("#save-form").ajaxSubmit({
		type:"post",
		url:url,
		data:{
		},
		success : function(res) {
			console.log(res);
			if(res!=null){
				var obj = $.parseJSON(res);
				if(obj.result_code=="success"){
					//操作成功后重新加载当前菜单内容
					reloadInfoFun();
				}else{
					util.message(obj.result_err_msg);
				}
			}
		},
	});
}

/*
 * 删除信息AJAX请求（逻辑删除）
 */
function deleteInfoAjaxRequest(id){
	var url = "back/check-cycle/delete";
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

/*
 * 单个删除
 */
function deleteInfoFun(id){
	util.delConfirm("确认删除？", id, "deleteInfoAjaxRequest");
}

/*
 * 重新加载当前菜单内容
 */
function reloadInfoFun(){
	//操作成功后重新加载
	var href = "back/check-cycle/select-items?pagehelperFun=clickPageBtnRequestFun";
	parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
}

/*
 * 点击页面中的页码执行此函数
 * 		函数功能：根据页码数请求当前页内容
 */
function clickPageBtnRequestFun(params){
	var yearName = $("#search-check-cycle-year").val();
	
	var action = "back/check-cycle/select-items";
	params.clickPageBtn = true;
	params.yearName = yearName;
	//util.loading();
	$("#item-div").load(action, params, function(){
	});
}

/**
 * 点击查询按钮时执行，根据选择的时间查询考核周期
 */
$("#search-check-cycle-btn").on("click", function(){
	var params = new Object();
	params.pagehelperFun = "clickPageBtnRequestFun";
	clickPageBtnRequestFun(params);
})
$("#delete-check-cycle-btn").on("click", function(){
	var yearName = $("#search-check-cycle-year").val();
	var url = "back/check-cycle/deleteByYearName";
	var params = {"yearName":yearName};
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
})

/*
 * 点击列表中某个复选框时，全选或反选
 */
function checkOne(){
    
    var flag = true;
    $("#check-cycle-table tbody input[type='checkbox']").each(function(){
    	if(!$(this).prop("checked")){
    		flag = false;
    	}
    });
    if(flag){
    	$("#check-cycle-table thead input[type='checkbox']").prop('checked', true);
    }else{
    	$("#check-cycle-table thead input[type='checkbox']").prop('checked', false);
    }
}
/*
 * 点击列表中All复选框时，列表全选或反选
 */
function checkAll(obj){
	$("#check-cycle-table tbody input[type='checkbox']").prop('checked', $(obj).prop('checked'));
}

/**
 * 点击添加按钮显示添加编辑选项卡
 */
$("#add-check-cycle-btn").click(function(){
	//$("#edit-check-cycle-li").removeClass("hide");
	//$('#tabs-check-cycle a[href="#tab-check-cycle-edit"]').tab('show');
	var url = "back/check-cycle/load-add-check-cycle-dialog";
	$("#load-add-dialog-div").load(url, function(){
		openAddCheckCycleDialog();
	});
});

/**
 * 打开对话框
 */
function openAddCheckCycleDialog() {
	$('#add-check-cycle-modal').modal({
		backdrop : 'static',
		keyboard : false,
		show : true
	});
}

/**
 * 关闭对话框
 * @returns
 */
function closeAddCheckCycleDialog() {	
	$("#add-check-cycle-modal").modal("hide");
}

var currDate = new Date();
var currYear = currDate.getFullYear();
$("#search-check-cycle-year").val(currYear);

//$(function(){
	/*$(".checkbox-all").on("click", function(){
		var flag = $(this).prop("checked");
		console.log(flag);
		//alert($(this).parent().parent());
		$(this).parent().parent().find(".curr-checkbox-all").find(".checkbox-one").each(function(){
			console.log(flag);
			if(flag){
				$(this).prop("checked",true);
			}else{
				$(this).prop("checked",false);
			}
			
		});
	})*/
	/*
	 * 点击列表中某个复选框时，全选或反选
	 
	function checkOne(){
	    
	    var flag = true;
	    $("#check-cycle-table tbody input[type='checkbox']").each(function(){
	    	if(!$(this).prop("checked")){
	    		flag = false;
	    	}
	    });
	    if(flag){
	    	$("#check-cycle-table thead input[type='checkbox']").prop('checked', true);
	    }else{
	    	$("#check-cycle-table thead input[type='checkbox']").prop('checked', false);
	    }
	}
	
	 * 点击列表中All复选框时，列表全选或反选
	 
	function checkAll(obj){
		$("#check-cycle-table tbody input[type='checkbox']").prop('checked', $(obj).prop('checked'));
	}*/
//});

/*
 * 重置form表单
 */
function resetFun(){
	$("#save-form").data('bootstrapValidator').destroy();//销毁bootstrapValidator验证
	bootstrapValidateFun();//启用验证
	//$('#save-form')[0].reset();
	$(":input","#save-form")  
	 .not(":button, :submit, :reset, :checkbox, :radio")  
	 .val("")
	 .removeAttr("checked")  
	 .removeAttr("selected");
	$("#check-cycle-cal-type").val(4);//时间段计法
	$("#edit-check-cycle-li").addClass("hide");
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