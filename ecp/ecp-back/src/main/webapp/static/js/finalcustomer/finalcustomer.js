
//------------------UI 交互------------------------
/**
 * 打开增加最终用户窗口
 * @returns
 */
function openAddFinalCustomerDialog() {
	//modal-container-306690	
	console.log("debug!");
	
	$("#orderId").val(curr_orderId);  //置当前orderId
	
	$('#modal-container-306690').modal({
		backdrop : 'static',
		keyboard : false
	});
}

/* close dialog :增加最终用户窗口*/
function closeAddFinalCustomerDialog() {	
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
	
	$("#finalcustomer-table").load(url,parms,null);
	
}

//---------------------------区域操作-----------------------------
/**
 * 复位地区列表
 * @returns
 */
function resetDistPickerDeep(){
	 var $distpicker = $('#distpicker');
	 $distpicker.distpicker('reset', true);
}

/**
 * 同步更新地区编码、名称
 * @returns
 */
function syncUpdateHiddenAreaCode(){
	console.log("debug");
	var code= $("#province").find("option:selected").attr("data-code");  //省级编码、名称
	var name= $("#province").find("option:selected").val();
	console.log("name:"+name);
	$("#provinceCode").val(code);
	$("#provinceName").val(name);
	
	console.log("debug end");
	
	
	code= $("#city").find("option:selected").attr("data-code");  //市级编码、名称
	name= $("#city").find("option:selected").val();
	$("#cityCode").val(code);
	$("#cityName").val(name);
	
	
	
	code= $("#district").find("option:selected").attr("data-code");  //县区级编码、名称
	name= $("#district").find("option:selected").val();
	$("#countyCode").val(code);
	$("#countyName").val(name);
	
	
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
					closeAddFinalCustomerDialog();
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
	$('.modi-finalcustomer').on('click',function(){
		console.log("modify finalcustomer");
	})
	
	$('.del-finalcustomer').on('click',function(){
		console.log("delete finalcustomer");
	})
	
	//增加最终用户button: click event  显示增加对话框
	$('#btn-add-finalcustomer').on('click',function(){openAddFinalCustomerDialog();})
	
	//保存最终用户条目button:click event
	$('#btn-save-finalcustomer').on('click',function(){addFinalCustomerItem();})
	
	//==================地区选择====================
	/*
	 *当“省份”变化时，将code赋值给value; 
	 */
	$("#province").on("change",function(){
		syncUpdateHiddenAreaCode();
	});
	
	/*
	 *当“市级”变化时，将ode赋值给value; 
	 */
	$("#city").on("change",function(){
		syncUpdateHiddenAreaCode();
	});
	
	/*
	 *当“县级”变化时，将code赋值给value; 
	 */
	$("#district").on("change",function(){
		syncUpdateHiddenAreaCode();
	});
	
	syncUpdateHiddenAreaCode();  //初始化三级地址编码及三级地址名称字段(hidden类型)
	
	
});