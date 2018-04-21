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
//---------------------对话框打开/关闭----------------------
/**
 * 打开增加市场费用窗口
 * @returns
 */
function openAddmarketFeeDialog() {
	//modal-container-306690
	//console.log("debug!");
	$('#modal-container-306690').modal({
		backdrop : 'static',
		keyboard : false
	});
}

/* close dialog :增加市场费用窗口*/
function closeAddmarketFeeDialog() {	
	$("#modal-container-306690").modal("hide");
}


/*加载市场费用列表*/
function loadmarketFeeTable(){
	var url = BASE_CONTEXT_PATH + "/back/marketfee/table"; //需要提交的 url
	var orderId = curr_orderId;  //订单id(PK)
	var orderNo = curr_orderNo;  //订单号
	
	var parms=new Object();
	parms.orderId=orderId;
	parms.orderNo=orderNo;
	
	$("#marketfee-table").load(url,parms,null);
	
}


//--------------------page loaded ready-----------------------
$(function(){
	loadmarketFeeTable();  //动态加载市场费用列表.
	
	//---------------click event process binding------------------------
	/*$('.modi-market-fee').on('click',function(){
		console.log("modify market fee");
	})
	
	$('.del-market-fee').on('click',function(){
		console.log("delete market fee");
	})*/
	
	//增加市场费用button: click event
	$('#btn-add-market-fee').on('click',function(){
		//console.log("btn add market fee clicked!");
		var url=BASE_CONTEXT_PATH+'/back/marketfee/loadadddialog';
		var params=new Object();
		params.orderId=curr_orderId;
		//动态加载增加dialog
		$(".edit-marketfee-dialog").load(url,params,function(){
			//加载完毕后自动打开增加对话框.
			openAddmarketFeeDialog();
			
			//根据订单的记帐状态  全选/全不选 所绑定的用户.
			if(curr_accountStatus==null  || curr_accountStatus==1){
				$(".bind-user").prop("checked",true);
				//置所有用户的选择状态
				setBindUserCheckStatus(true);
			}
			else{  //accountStatus==2;
				$(".bind-user").prop("checked",false);
				//置所有用户的选择状态
				setBindUserCheckStatus(false);
			}
		});
		
	})
	
	
});