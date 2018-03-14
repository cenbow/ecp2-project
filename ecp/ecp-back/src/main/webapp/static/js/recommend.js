
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
		                regexp: "^[\u4e00-\u9fa5A-Za-z0-9_\\s+\|\\\\\/]+$",
		                message: "请勿输入特殊符号"
	                },
	                stringLength: {
                        max: 20,
                        message: '长度不能超过20个字符'
                    },
	            }
	        },
	        url: {
	            validators: {
	                notEmpty: {
	                    message: "链接不能为空"
	                },
	                stringLength: {
                        max: 100,
                        message: '长度不能超过100个字符'
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
	var url = "back/recommend/selectUpdateById";
	var params = {"id":id};
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var resp = $.parseJSON(res);
			if(resp.result_code=="success"){
				$("#edit-recommend-li").removeClass("hide");
				var recommend =resp.recommend;
				$("#recommend-id").val(recommend.id);//ID
				$("#recommend-name").val(recommend.name);//名称
				$("#recommend-type input:radio[name='type'][value='"+recommend.type+"']").prop("checked",true);//类型：1=商品；2=类目；默认=1
				selectType(recommend.type);
				$("#recommend-showed input:radio[name='type'][value='"+recommend.showed+"']").prop("checked",true);//是否在前端显示（1-显示，2-不显示，默认1）
				$("#recommend-url").val(recommend.url);//链接
				
				$('#tabs-243687 a[href="#tab-2"]').tab('show');
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
	var id = $("#recommend-id").val();
	if(id==null || id==""){
		url = "back/recommend/insert";
	}else{
		url = "back/recommend/updateById";
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
 * 删除信息AJAX请求（物理删除）
 */
function deleteInfoAjaxRequest(id){
	var url = "back/recommend/deleteById";
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
 * 批量删除
 */
/*function batchDeleteInfoFun(){
	var idArr = new Array();
    $("#user-table tbody input[type='checkbox']:checked").each(function(){
    	idArr.push($(this).val());
    });
    if(idArr.length<=0){
    	util.message("请选择列表中的内容！");
    	return;
    }
	util.delConfirm("确认选中删除？", 0, "batchDeleteInfoAjaxRequest");
}*/
/*
 * 批量删除信息AJAX请求（逻辑删除）
 */
/*function batchDeleteInfoAjaxRequest(){
	
	var idArr = new Array();
    $("#user-table tbody input[type='checkbox']:checked").each(function(){
    	idArr.push($(this).val());
    });
    var url = "front/user/deleteByIds";
	var params = {"userIds":idArr.toString()};
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
}*/

/*
 * 重新加载当前菜单内容
 */
function reloadInfoFun(){
	//操作成功后重新加载
	var href = "back/recommend/selectItems?pagehelperFun=clickPageBtnRequestFun";
	parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
}

/*
 * 点击页面中的页码执行此函数
 * 		函数功能：根据页码数请求当前页内容
 */
function clickPageBtnRequestFun(params){
	var action = "back/recommend/selectItems";
	params.clickPageBtn = true;
	//util.loading();
	$("#item-div").load(action, params, function(){
		//TODO 加载完成后的操作
	});
}

/*
 * 点击列表中某个复选框时，全选或反选
 */
function checkOne(){
    
    var flag = true;
    $("#recommend-table tbody input[type='checkbox']").each(function(){
    	if(!$(this).prop("checked")){
    		flag = false;
    	}
    });
    if(flag){
    	$("#recommend-table thead input[type='checkbox']").prop('checked', true);
    }else{
    	$("#recommend-table thead input[type='checkbox']").prop('checked', false);
    }
}
/*
 * 点击列表中All复选框时，列表全选或反选
 */
function checkAll(obj){
	$("#recommend-table tbody input[type='checkbox']").prop('checked', $(obj).prop('checked'));
}

/**
 * 点击添加按钮时显示编辑选项卡
 */
$("#add-recommend-btn").click(function(){
	$("#edit-recommend-li").removeClass("hide");
	$('#tabs-243687 a[href="#tab-2"]').tab('show');
});

/*
 * 重置form表单
 */
function resetFun(){
	$("#save-form").data('bootstrapValidator').destroy();//销毁bootstrapValidator验证
	bootstrapValidateFun();//启用验证
	//$('#save-form')[0].reset();
	$(":input","#save-form")  
	 .not(":button, :submit, :reset, :radio")  
	 .val("");
	 //.removeAttr("checked")  
	 //.removeAttr("selected");
	$("#recommend-type input:radio[name='type'][value='2']").prop("checked",true);//类型：1=商品；2=类目；默认=1
	selectType(2);
	$("#recommend-showed input:radio[name='type'][value='1']").prop("checked",true);//是否在前端显示（1-显示，2-不显示，默认1）
	$("#edit-recommend-li").addClass("hide");
}

/**
 * 选择推荐类型
 * @param type	类型：1=商品；2=类目；默认=1
 * @returns
 */
function selectType(type){
	console.log("type:"+type);
	if(type==1){
		$("#recommend-category").addClass("hide");
		$("#recommend-item").removeClass("hide");
	}else{
		$("#recommend-item").addClass("hide");
		$("#recommend-category").removeClass("hide");
		$("#catetory-select").val("0");
	}
	$("#recommend-url").val("");
}

/**
 * 绑定类目节点的change事件
 */
$("#catetory-select").bind("change",function(){
	var cid = $(this).val();
    //获取自定义属性的值
    var lev = $(this).find("option:selected").attr("level");
    console.log("level:"+lev);
	if(lev!=3){
		$("#catetory-select").val("0");
		util.message("请选择三级类目！");
	}else{
		//生成链接url
		createCategoryUrl(cid);
	}
});

/**
 * 修改显示状态（是否在前端显示（1-显示，2-不显示，默认1））
 * @returns
 */
function updateShowedStatus(recommendId, showed){
	var url = "back/recommend/updateShowedStatusById";
	var params = {"recommendId":recommendId, "showed":showed};
	//util.loading();
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null && res!=""){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				if(showed==1){
					$("#recommend-showed-yes-"+recommendId).attr("disabled", true);
					$("#recommend-showed-no-"+recommendId).attr("disabled", false);
					$("#recommend-del-btn-"+recommendId).attr("disabled", true);
					$("#recommend-del-btn-"+recommendId).attr("title", "已显示的推荐不能删除");
				}else{
					$("#recommend-showed-yes-"+recommendId).attr("disabled", false);
					$("#recommend-showed-no-"+recommendId).attr("disabled", true);
					$("#recommend-del-btn-"+recommendId).attr("disabled", false);
					$("#recommend-del-btn-"+recommendId).attr("title", "");
				}
				
			}else{
				util.message(obj.result_err_msg);
			}
		}
		
	});
}

/**
 * 创建类目url
 * @returns
 */
function createCategoryUrl(cid){
	var url = "front/search/category/"+cid;
	$("#recommend-url").val(url);
}
/**
 * 创建商品url
 * @returns
 */
function createItemUrl(itemId){
	var url = "front/product/detail/"+itemId;
	$("#recommend-url").val(url);
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