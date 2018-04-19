
//------------------UI 交互------------------------
/**
 * 打开增加四项费用窗口
 * @returns
 */
function openAddFourFeeDialog() {
	//modal-container-306690
	console.log("debug!");
	$('#modal-container-306690').modal({
		backdrop : 'static',
		keyboard : false
	});
}

/* close dialog :增加四项费用窗口*/
function closeAddFourFeeDialog() {	
	$("#modal-container-306690").modal("hide");
}

/*重新加载四项费用编辑页面*/
function reloadFourFeeTable(){
	var url = BASE_CONTEXT_PATH + "/back/fourfee/table"; //需要提交的 url
	var orderId = curr_orderId;  //订单id(PK)
	var orderNo = curr_orderNo;  //订单号
	
	var parms=new Object();
	parms.orderId=orderId;
	parms.orderNo=orderNo;
	
	$("#fourfee-table").load(url,parms,null);
	
}

//------------------业务操作----------------------
/**
 * 保存四项费用条目:增加
 * @returns
 */
function addFourFeeItem(){
	var urlStr = BASE_CONTEXT_PATH + "/back/fourfee/add"; // 需要提交的url
	
	var params=new Object();  //生成参数对象.
	
	
	//自对话框中取得所输入的值
	var itemType=$('#accountItemType').val();
	var amount=$('#amount').val();
	var comment=$('#comment').val();
	
	var bindUserId=$("#belongUserRole option:selected").attr("data-bind-user-id");
	var roleId=$("#belongUserRole option:selected").attr("data-bind-role-id");
	
	params.orderId=curr_orderId;
	params.orderNo=curr_orderNo;	
	params.itemType=$('#accountItemType').val();  //类型
	params.amount=$('#amount').val();		//金额
	params.comment=$('#comment').val();	  	//备注
	
	
	
	params.bindUserId=bindUserId;
	params.roleId=roleId;	
	

	$.ajax({
		type : "POST", // 提交方式 get/post
		url : urlStr,
		contentType : "application/json", // 如果采用json格式传送所有参数时必须有,如果采普通js对象传送时,则不可以加此参数
		//dataType : "html", //表示返回值类型，不必须,如果返回的是面页，则必须
		data :JSON.stringify(params),
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			//console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					//util.message(obj.result_msg);
					//var urlTemp = obj.result_msg;
					reloadFourFeeTable();
					closeAddFourFeeDialog();
					
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


//-------------------------------------------
$(function(){
	
	//---------------click event process binding------------------------
	$('.modi-four-fee').on('click',function(){
		console.log("modify four fee");
	})
	
	$('.del-four-fee').on('click',function(){
		console.log("delete four fee");
	})
	
	//增加四项费用button: click event
	$('#btn-add-four-fee').on('click',function(){
		//console.log("btn add four fee clicked!");
		openAddFourFeeDialog();
	})
	
	//保存费用条目button:click event
	$('#btn-save-four-fee').on('click',function(){		
		addFourFeeItem();
		//closeAddFourFeeDialog();
	})
	
	
});