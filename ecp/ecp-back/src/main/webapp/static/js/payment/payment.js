
//------------------UI 交互------------------------
/**
 * 打开增加回款窗口
 * @returns
 */
function openAddPaymentFeeDialog() {
	//modal-container-306690
	console.log("debug!");
	$('#modal-container-306690').modal({
		backdrop : 'static',
		keyboard : false
	});
}

/* close dialog :增加回款窗口*/
function closeAddPaymentFeeDialog() {	
	$("#modal-container-306690").modal("hide");
}

/*重新加载回款编辑页面*/
function reloadPaymentFeeTable(){
	var url = BASE_CONTEXT_PATH + "/back/payment/table"; //需要提交的 url
	var orderId = curr_orderId;  //订单id(PK)
	var orderNo = curr_orderNo;  //订单号
	
	var parms=new Object();
	parms.orderId=orderId;
	parms.orderNo=orderNo;
	
	$("#payment-table").load(url,parms,null);
	
}

//------------------业务操作----------------------
/**
 * 保存回款条目:增加
 * @returns
 */
function addPaymentFeeItem(){
	var urlStr = BASE_CONTEXT_PATH + "/back/payment/add"; // 需要提交的url
	
	/*var params=new Object();  //生成参数对象.
	
	params.agentId=agentId;
	params.userList=userList;*/
	
	var orderId=curr_orderId;
	var orderNo=curr_orderNo;
	
	//自对话框中取得所输入的值
	var itemType=$('#accountItemType').val();
	var amount=$('#amount').val();
	var comment=$('#comment').val();

	$.ajax({
		type : "POST", // 提交方式 get/post
		url : urlStr,
		//contentType : "application/json", // 如果采用json格式传送所有参数时必须有,如果采普通js对象传送时,则不可以加此参数
		//dataType : "html", //表示返回值类型，不必须,如果返回的是面页，则必须
		data : {
			'orderId':orderId,
			'orderNo':orderNo,
			'itemType':itemType,
			'amount':amount,
			'comment':comment
		},
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			//console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					//util.message(obj.result_msg);
					//var urlTemp = obj.result_msg;
					reloadPaymentFeeTable();
					closeAddPaymentFeeDialog();
					
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
	$('.modi-payment').on('click',function(){
		console.log("modify payment");
	})
	
	$('.del-payment').on('click',function(){
		console.log("delete payment");
	})
	
	//增加回款button: click event
	$('#btn-add-payment').on('click',function(){openAddPaymentFeeDialog();})
	
	//保存回款条目button:click event
	$('#btn-save-payment').on('click',function(){addPaymentFeeItem();})
	
	
});