
//$(function(){
	
	bootstrapValidateFun();//启用验证
	
	/*
	 * 根据条件查询列表
	 */
	$("#filter-submit-btn").click(function(){
		var params = new Object();
		params.pagehelperFun="filterItem";
		filterItem(params);
	});
	
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
function selectDetails(memberId){
	resetFun();
	var url = "front/member/selectUpdateById";
	var params = {"memberId":memberId};
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var resp = $.parseJSON(res);
			if(resp.result_code=="success"){
				var member =resp.member;
				$("#member-id").val(member.memberId);//ID
				$("#user-name").val(member.username);//用户名
				$("#nick-name").val(member.nickname);//昵称
				$("#phone").val(member.phone);//手机
				$("#level-id").val(member.levelId);//会员等级
				$("#organize-id").val(member.organizeId);//会员来源
				$("#email").val(member.email);//电子邮箱
				$("#city").val(member.city);//城市
				$("#sex").val(member.sex);//性别
				
				$("#edit-tab").text("编辑会员");//修改选项卡标题为编辑会员
				
				$('#tabs-243687 a[href="#tab-2"]').tab('show');
				return;
			}
		}
		util.message("查询异常");
		
	});
}

/*
 * 点击会员列表选项卡时把编辑会员选项卡标题改为新增会员
 */
function updateTabTitle(){
	resetFun();
	$("#edit-tab").text("新增会员");//修改选项卡标题为编辑会员
}

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
	var memberId = $("#member-id").val();
	if(memberId==null || memberId==""){
		url = "front/member/insert";
	}else{
		url = "front/member/updateById";
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
					//$("#update-menu-modal-div").modal("hide");//隐藏修改菜单对话框
					//util.message(obj.result_msg);
					//window.location.href="loginSubmit";
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
 * 删除信息AJAX请求（物理删除）
 */
function deleteInfoAjaxRequest(id){
	var url = "front/member/deleteById";
	var params = {"memberId":id};
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
 * 批量删除
 */
function batchDeleteInfoFun(){
	var idArr = new Array();
    $("#member-table tbody input[type='checkbox']:checked").each(function(){
    	idArr.push($(this).val());
    });
    if(idArr.length<=0){
    	util.message("请选择列表中的内容！");
    	return;
    }
	util.delConfirm("确认选中删除？", 0, "batchDeleteInfoAjaxRequest");
}
/*
 * 批量删除信息AJAX请求（逻辑删除）
 */
function batchDeleteInfoAjaxRequest(){
	
	var idArr = new Array();
    $("#member-table tbody input[type='checkbox']:checked").each(function(){
    	idArr.push($(this).val());
    });
    var url = "front/member/deleteByIds";
	var params = {"memberIds":idArr.toString()};
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
 * 重新加载当前菜单内容
 */
function reloadInfoFun(){
	//操作成功后重新加载
	var href = "front/member/selectItem?pagehelperFun=clickPageBtnRequestFun";
	parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
}

/*
 * 过滤列表（根据条件内容）
 */
function filterItem(params){
	params.condName = $("#filter-cond-name").val();
	params.condVal = $("#filter-cond-val").val();
	
	clickPageBtnRequestFun(params);
}

/*
 * 点击页面中的页码执行此函数
 * 		函数功能：根据页码数请求当前页内容
 */
function clickPageBtnRequestFun(params){
	var action = "front/member/selectItem";
	params.clickPageBtn = true;
	//util.loading();
	$("#item-div").load(action, params, function(){
		var totals = $("#total-s").val();
		$("#filter-total-s").text(totals);
	});
}

/*
 * 点击列表中某个复选框时，全选或反选
 */
function checkOne(){
    
    var flag = true;
    $("#member-table tbody input[type='checkbox']").each(function(){
    	if(!$(this).prop("checked")){
    		flag = false;
    	}
    });
    if(flag){
    	$("#member-table thead input[type='checkbox']").prop('checked', true);
    }else{
    	$("#member-table thead input[type='checkbox']").prop('checked', false);
    }
}
/*
 * 点击列表中All复选框时，列表全选或反选
 */
function checkAll(obj){
	$("#member-table tbody input[type='checkbox']").prop('checked', $(obj).prop('checked'));
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
	$("#link-img-portrait").html("");
}

/*
 * 点击列表中的用户画像时，显示用户详细信息
 */
function showUserDetail(){
	var href = "front/userIcon/selectItem?pagehelperFun=clickPageBtnRequestFun&showUserDetail=true";
	parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
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