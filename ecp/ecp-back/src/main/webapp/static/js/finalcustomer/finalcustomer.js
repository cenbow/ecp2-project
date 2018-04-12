
//------------------UI 交互------------------------
/**
 * 打开增加最终用户窗口
 * @returns
 */
function openAddFinalCustomerDialog() {
	$("#orderId").val(curr_orderId);  //置当前orderId
	
	$('#modal-container-306690').modal({
		backdrop : 'static',
		keyboard : false
	});
	
	//保存:最终用户条目button:click event
	$('#btn-save-finalcustomer').on('click',function(){addFinalCustomerItem();});
}

/**
 * 打开修改(详情)最终用户窗口
 * @returns
 */
function openDetailFinalCustomerDialog() {
	$('#modal-container-306690').modal({
		backdrop : 'static',
		keyboard : false
	});
	//保存:最终用户条目button:click event
	$('#btn-save-finalcustomer').on('click',function(){saveDetailFinalCustomerItem();});
}

/* close dialog :增加/修改 最终用户窗口*/
function closeFinalCustomerDialog() {	
	$("#modal-container-306690").modal("hide");
}

/*重新加载最终用户编辑页面*/
function reloadFinalCustomerTable(){
	var url = BASE_CONTEXT_PATH + "/back/finalcustomer/table"; //需要提交的 url
	
	var orderId = curr_orderId;  //订单id(PK)
	var orderNo = curr_orderNo;  //订单号
	
	var parms=new Object();
	parms.orderId=orderId;
	parms.orderNo=orderNo;
	
	$("#finalcustomer-table").load(url,parms,function(){
		bindClickEvent();
	});
	
}

function bindClickEvent(){
	$('.modi-finalcustomer').on('click',
			function(){
				var url=BASE_CONTEXT_PATH + "/back/finalcustomer/loaddetaildialog";
				var id=$(this).attr("bind-id");
				
				var parm=new Object();
				parm.finalCustomerId=id;
				
				$("#edit-dialog").load(url,parm,openDetailFinalCustomerDialog);			
			}
		);
		
	$('.del-finalcustomer').on('click',function(){
		var id=$(this).attr("bind-id");
		delFinalCustomerItem(id);
	});
}

//------------------业务操作----------------------
/**
 * 保存最终用户条目:增加
 * @returns
 */
function addFinalCustomerItem(){
	var url = BASE_CONTEXT_PATH + "/back/finalcustomer/add"; // 需要提交的url
	console.log("form commit:"+url);
	$("#finalcustomer-form").ajaxSubmit({
		type : "post",
		url : url,
		success : function(res) {
			console.log(res);
			if (res != null) {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {					
					reloadFinalCustomerTable();
					closeFinalCustomerDialog();
				} else {
					util.message(obj.result_msg);
				}
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			util.message("AJAX请求时发生错误!");
			/* 弹出jqXHR对象的信息 */
			console.log(jqXHR.responseText);
			console.log(jqXHR.status);
			console.log(jqXHR.readyState);
			console.log(jqXHR.statusText);
			/* 弹出其他两个参数的信息 */
			console.log(textStatus);
			console.log(errorThrown);

		}
		
	});
	
}

/**
 * 删除:最终联系人
 * @param id
 * @returns
 */
function delFinalCustomerItem(id){
	util.delConfirm("确认删除？", id, "deleteFinalCustomerAjaxRequest");
}

/*
 * 删除信息AJAX请求（逻辑删除 deleted=2）
 */
function deleteFinalCustomerAjaxRequest(id){
	var url = BASE_CONTEXT_PATH+"/back/finalcustomer/del";
	var params = {"finalCustomerId":id};
	//util.loading();
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null && res!=""){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				reloadFinalCustomerTable();
			}else{
				util.message(obj.result_err_msg);
			}
		}
		
	});
}


/**
 * 保存最终用户条目:修改
 * @returns
 */
          
function saveDetailFinalCustomerItem(){
	var url = BASE_CONTEXT_PATH + "/back/finalcustomer/savedetail"; // 需要提交的url
	$("#finalcustomer-form").ajaxSubmit({
		type : "post",
		url : url,
		success : function(res) {
			//console.log(res);
			if (res != null) {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {					
					reloadFinalCustomerTable();
					closeFinalCustomerDialog();
				} else {
					util.message(obj.result_msg);
				}
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			util.message("AJAX请求时发生错误!");
			/* 弹出jqXHR对象的信息 */
			console.log(jqXHR.responseText);
			console.log(jqXHR.status);
			console.log(jqXHR.readyState);
			console.log(jqXHR.statusText);
			/* 弹出其他两个参数的信息 */
			console.log(textStatus);
			console.log(errorThrown);
		}
		
	});
}

//--------------------page loaded ready-----------------------
$(function(){
	
	//-------------initialize------------
	reloadFinalCustomerTable();
	
	
	//---------------click event process binding--------------
	
	//增加最终用户button: click event  显示增加对话框
	$('#btn-add-finalcustomer').on('click',
		function(){
			//openAddFinalCustomerDialog();
			var url=BASE_CONTEXT_PATH + "/back/finalcustomer/loadadddialog";
			$("#edit-dialog").load(url,null,openAddFinalCustomerDialog);
		}
	);

	
	
});