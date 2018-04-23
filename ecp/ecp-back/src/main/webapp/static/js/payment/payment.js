//---------------通用函数--------------
/*
 * 日期转字符串格式函数
 * 调用方法：date.format('yyyy-MM-dd hh:mm:ss');
 */
Date.prototype.format = function(format) {
	var date = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S+" : this.getMilliseconds()
	};
	if (/(y+)/i.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + '')
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in date) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1,
					RegExp.$1.length == 1 ? date[k] : ("00" + date[k])
							.substr(("" + date[k]).length));
		}
	}
	return format;
}


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
function loadPaymentFeeTable(){
	var url = BASE_CONTEXT_PATH + "/back/payment/table"; //需要提交的 url
	var orderId = curr_orderId;  //订单id(PK)
	var orderNo = curr_orderNo;  //订单号
	
	//传递的参数
	var parms=new Object();
	parms.orderId=orderId;
	parms.orderNo=orderNo;
	
	$("#payment-table").load(url,parms,null);
	
}


//--------------------page loaded ready-----------------------
$(function(){
	loadPaymentFeeTable();  //动态加载订单回款列表
	
	
	//---------------click event process binding------------------------
	//增加回款button: click event
	$('#btn-add-payment').on('click',function(){
		//动态加载增加对话框
		var url=BASE_CONTEXT_PATH+"/back/payment/loadadddialog";
		$(".edit-payment-dialog").load(url,null,openAddPaymentFeeDialog);
	
	});
	
	
	
	
});