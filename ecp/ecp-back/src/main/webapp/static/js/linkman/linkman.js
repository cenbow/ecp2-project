
//------------------UI 交互------------------------
/**
 * 打开增加联系人窗口
 * @returns
 */
function openAddLinkmanDialog() {
	$("#orderId").val(curr_orderId);  //置当前orderId
	
	$('#modal-container-306690').modal({
		backdrop : 'static',
		keyboard : false
	});
	
	//保存联系人条目button:click event
	$('#btn-save-linkman').on('click',function(){addLinkmanItem();});
}

/**
 * 打开修改(详情)联系人窗口
 * @returns
 */
function openDetailLinkmanDialog() {
	$('#modal-container-306690').modal({
		backdrop : 'static',
		keyboard : false
	});
	//保存联系人条目button:click event
	$('#btn-save-linkman').on('click',function(){saveDetailLinkmanItem();});
}



/* close dialog :增加/修改  联系人窗口*/
function closeLinkmanDialog() {	
	$("#modal-container-306690").modal("hide");
}

/*重新加载联系人编辑页面*/
function reloadLinkmanTable(){
	var url = BASE_CONTEXT_PATH + "/back/linkman/table"; //需要提交的 url
	var orderId = curr_orderId;  //订单id(PK)
	var orderNo = curr_orderNo;  //订单号
	
	var parms=new Object();
	parms.orderId=orderId;
	parms.orderNo=orderNo;
	
	$("#linkman-table").load(url,parms,function(){
		bindClickEvent();
	});
	
}

function bindClickEvent(){
	$('.modi-linkman').on('click',
			function(){
				var url=BASE_CONTEXT_PATH + "/back/linkman/loaddetaildialog";
				console.log("test");
				var curr_linkmanId=$(this).attr("bind-id");
				
				var parm=new Object();
				parm.linkmanId=curr_linkmanId;
				
				$("#edit-dialog").load(url,parm,openDetailLinkmanDialog);			
			}
		);
		
	$('.del-linkman').on('click',function(){
		var linkmanId=$(this).attr("bind-id");
		delLinkmanItem(linkmanId);
	});
}



//------------------业务操作----------------------
/**
 * 保存联系人条目:增加
 * @returns
 */
function addLinkmanItem(){
	var url = BASE_CONTEXT_PATH + "/back/linkman/add"; // 需要提交的url
	//console.log("form commit:"+url);
	$("#linkman-form").ajaxSubmit({
		type : "post",
		url : url,
		success : function(res) {
			console.log(res);
			if (res != null) {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {					
					reloadLinkmanTable();  //刷新指定订单下的联系人列表
					loadCurrentPage();  //刷新订单列表下的联系人信息
					closeLinkmanDialog();
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
 * 删除联系人
 * @param linkmanId
 * @returns
 */
function delLinkmanItem(linkmanId){
	util.delConfirm("确认删除？", linkmanId, "deleteLinkmanAjaxRequest");
}

/*
 * 删除信息AJAX请求（逻辑删除 deleted=2）
 */
function deleteLinkmanAjaxRequest(id){
	var url = BASE_CONTEXT_PATH+"/back/linkman/del";
	var params = {"linkmanId":id};
	//util.loading();
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null && res!=""){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				reloadLinkmanTable();
				loadCurrentPage();
			}else{
				util.message(obj.result_err_msg);
			}
		}
		
	});
}


/**
 * 保存联系人条目:修改联系人
 * @returns
 */
          
function saveDetailLinkmanItem(){
	var url = BASE_CONTEXT_PATH + "/back/linkman/savedetail"; // 需要提交的url
	//console.log("form commit:"+url);
	$("#linkman-form").ajaxSubmit({
		type : "post",
		url : url,
		success : function(res) {
			console.log(res);
			if (res != null) {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {					
					reloadLinkmanTable();
					loadCurrentPage();
					closeLinkmanDialog();
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
	reloadLinkmanTable();
	
	//---------------click event process binding--------------
	//增加联系人button: click event
	$('#btn-add-linkman').on('click',
		function(){
			var url=BASE_CONTEXT_PATH + "/back/linkman/loadadddialog";
			$("#edit-dialog").load(url,null,openAddLinkmanDialog);			
		}
	);
	
	
	
	
});