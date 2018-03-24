
//------------------UI 交互------------------------
/**
 * 打开增加联系人窗口
 * @returns
 */
function openAddLinkmanDialog() {
	//modal-container-306690	
	console.log("debug!");
	
	$("#orderId").val(curr_orderId);  //置当前orderId
	
	$('#modal-container-306690').modal({
		backdrop : 'static',
		keyboard : false
	});
}

/* close dialog :增加联系人窗口*/
function closeAddLinkmanDialog() {	
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
	
	$("#linkman-table").load(url,parms,null);
	
}

//------------------业务操作----------------------
/**
 * 保存联系人条目:增加
 * @returns
 */
function addLinkmanItem(){
	var url = BASE_CONTEXT_PATH + "/back/linkman/add"; // 需要提交的url
	console.log("form commit:"+url);
	$("#linkman-form").ajaxSubmit({
		type : "post",
		url : url,
		success : function(res) {
			console.log(res);
			if (res != null) {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {					
					reloadLinkmanTable();
					closeAddLinkmanDialog();
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
	
	//---------------click event process binding------------------------
	$('.modi-linkman').on('click',function(){
		console.log("modify linkman");
	})
	
	$('.del-linkman').on('click',function(){
		console.log("delete linkman");
	})
	
	//增加联系人button: click event
	$('#btn-add-linkman').on('click',function(){openAddLinkmanDialog();})
	
	//保存联系人条目button:click event
	$('#btn-save-linkman').on('click',function(){addLinkmanItem();})
	
	
});