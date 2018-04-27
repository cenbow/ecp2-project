
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
	    	/*checkCycleId: {
	            validators: {
	                notEmpty: {
	                    message: "请选择考核周期"
	                },
	            }
	        },
	        userId: {
	            validators: {
	                notEmpty: {
	                    message: "请选择IS/OS"
	                },
	            }   
	        },*/
	        targetRate: {
	            validators: {
	                notEmpty: {
	                    message: "指标比例不能为空"
	                },
	                /*regexp: {
		                regexp: "^[0-9]*[1-9][0-9]*$",
		                message: "请输入正整数"
	                },*/
	                regexp: {
		                regexp: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
		                message: "请输入正确金额"
	                },
	                stringLength: {
                        max: 20,
                        message: '长度不能超过20个字符'
                    },
	            }
	        },
	        targetAmount: {
	            validators: {
	                notEmpty: {
	                    message: "指标金额不能为空"
	                },
	                regexp: {
		                regexp: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
		                message: "请输入正确金额"
	                },
	                stringLength: {
                        max: 20,
                        message: '长度不能超过20个字符'
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
	var url = "back/sales-target/select-update";
	var params = {"id":id};
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var resp = $.parseJSON(res);
			if(resp.result_code=="success"){
				var target =resp.salesTarget;
				$("#sales-target-id").val(target.id);//ID
				$("#show-year-name").text(target.year_name);//考核年度
				$("#show-cycle-name").text(target.cycle_name);//周期名称
				$("#show-username").text(target.username);//人员
				$("#show-role-name").text(target.role_name);//角色
				$("#show-start-date").text(new Date(target.start_date).format('yyyy-MM-dd hh:mm:ss'));//开始时间
				$("#show-end-date").text(new Date(target.end_date).format('yyyy-MM-dd hh:mm:ss'));//结束时间
				$("#sales-target-rate").val(target.target_rate);//指标比例
				$("#sales-target-amount").val(target.target_amount);//指标金额
				
				$('#edit-sales-target-modal').modal({
					backdrop : 'static',
					keyboard : false
				});
				
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
	var id = $("#sales-target-id").val();
	if(id==null || id==""){
		url = "back/sales-target/insert";
	}else{
		url = "back/sales-target/update";
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
	var url = "back/sales-target/delete";
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
	var href = "back/sales-target/select-items?pagehelperFun=clickPageBtnRequestFun";
	parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
}

/*
 * 点击页面中的页码执行此函数
 * 		函数功能：根据页码数请求当前页内容
 */
function clickPageBtnRequestFun(params){
	var action = "back/sales-target/select-items";
	params.clickPageBtn = true;
	//util.loading();
	$("#item-div").load(action, params, function(){
	});
}

/*
 * 点击列表中某个复选框时，全选或反选
 */
function checkOne(){
    
    var flag = true;
    $("#sales-target-table tbody input[type='checkbox']").each(function(){
    	if(!$(this).prop("checked")){
    		flag = false;
    	}
    });
    if(flag){
    	$("#sales-target-table thead input[type='checkbox']").prop('checked', true);
    }else{
    	$("#sales-target-table thead input[type='checkbox']").prop('checked', false);
    }
}
/*
 * 点击列表中All复选框时，列表全选或反选
 */
function checkAll(obj){
	$("#sales-target-table tbody input[type='checkbox']").prop('checked', $(obj).prop('checked'));
}

/**
 * 点击添加按钮显示添加编辑选项卡
 */
$("#add-sales-target-btn").click(function(){
	//$("#edit-sales-target-li").removeClass("hide");
	//$('#tabs-sales-target a[href="#tab-sales-target-edit"]').tab('show');
	var url = "back/sales-target/load-add-sales-target-dialog";
	$("#load-add-dialog-div").load(url, function(){
		openAddSalesTargetDialog();
	});
});

/**
 * 打开对话框
 */
function openAddSalesTargetDialog() {
	$('#add-sales-target-modal').modal({
		backdrop : 'static',
		keyboard : false
	});
}

/**
 * 关闭对话框
 * @returns
 */
function closeAddSalesTargetDialog() {	
	$("#add-sales-target-modal").modal("hide");
}

/**
 * 点击查询考核指标按钮
 */
$("#search-sales-target-btn").on("click", function(){
	var params = new Object();
	params.pagehelperFun = "searchClickPageBtnRequestFun";
	searchClickPageBtnRequestFun(params);
});

//TODO 删除见jsp

/*
 * 点击页面中的页码执行此函数，查询结果分页
 * 		函数功能：根据页码数请求当前页内容
 */
function searchClickPageBtnRequestFun(params){
	var yearName = $("#search-check-cycle-year").val();
	var userId = $("#search-user-id").val();
	var roleId = $("#search-role-id").val();
	var action = "back/sales-target/select-items";
	params.clickPageBtn = true;
	params.searchYearName = yearName;
	params.searchUserId = userId;
	params.searchRoleId = roleId;
	//util.loading();
	$("#item-div").load(action, params, function(){
	});
}

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
	$("#edit-sales-target-li").addClass("hide");
}
/**
 * 绑定查询功能的用户角色change事件
 */
$("#search-is-os").on("change", function(){
	var userIdRoleId = $(this).val();
	console.log("value:"+userIdRoleId);
	if(userIdRoleId!=null && userIdRoleId!=""){
		var arr = userIdRoleId.split("-");// 在每个减号(-)处进行分解
		var userId = arr[0];
		var roleId = arr[1];
		$("#search-user-id").val(userId);
		$("#search-role-id").val(roleId);
	}else{
		$("#search-user-id").val("");
		$("#search-role-id").val("");
	}
});

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