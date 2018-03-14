//===============变量定义=================
var g_ContractItemId = 0;

/* 设置orderItemId */
function setContractItemId(itemId) {
	g_ContractItemId = itemId;
}

function getContractItemId() {
	return g_ContractItemId;
}

function getContractAttrList() {
	return g_contractAttrList;
}

// ==================对话框通用部分=START=================
/*
 * 显示增加用户地址对话框
 */
function showDialog() {
	
	$('#modal-container-273078').modal({
		backdrop : 'static',
		keyboard : false
	});
	
	
	
}

/* close dialog */
function closeDialog() {
	$("#modal-container-273078").modal("hide");
}

/* set dialog's title */
function setDialogTitle(title) {
	$("#myModalLabel").text(title);
}

/* reset dialog */
function resetDialog() {
	$("#dialog-form")[0].reset();
}

// ===============合同条目================

/* 获取合同行项目列表 */
function getContractItemList() {
	return g_contractItemList;
}

/*
 * 显示折减对话框
 */
function displayDiscountDialog(e) {
	var itemId = $(e).attr("data-bind"); // get orderItem'id
	setContractItemId(itemId);

	var sku_name = $("#sku_name_" + itemId).html(); // get sku_name and
															// set dialog's
															// title
	setDialogTitle("折减金额(" + sku_name + ")");

	resetDialog();
	showDialog();
}

/*
 * 计算订单条目小计 params: id:order item id discount:折减金额
 */
function calcAmount(id, discount) {
	var itemList = getContractItemList();
	var index = -1;
	for (var i = 0; i < itemList.length; i++) {
		if (itemList[i].id == id) {
			index = i;
			break;
		}
	}
	itemList[index].discount_price = discount;
	itemList[index].pay_price = itemList[index].primitive_price	- discount;
	itemList[index].pay_price_total = itemList[index].pay_price	* itemList[index].num;
}

/**
 * 更新合同条目折减
 * @param itemId  合同条目id(pk)
 * @param discount 折减金额
 * @returns
 */
function updateContractItem(itemId,discount){
	var url = BASE_CONTEXT_PATH + "/back/contract/itemupdate"; // 需要提交的 url

	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		// dataType: "application/json",
		data : {
			'itemId' : itemId,
			'discount':discount
		},
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					util.message(obj.result_msg); //显示删除成功能对话框，此处省略
				} else {
					util.message(obj.result_err_msg);
				}
			}
		}

	});
}

//==============当总金额重新计算时，重新计算合同细则=============
function reCalcContractSpace(){
	//payment_percent_delivery
	//payment_percent_signed
	$("#payment_percent_delivery").trigger("blur");
	$("#payment_percent_signed").trigger("blur");
}

/*
 * 更新 (UI) params: id:order item id
 */
function displayContractItem(id) {
	var orderItemList = getContractItemList();
	var index = -1;
	for (var i = 0; i < orderItemList.length; i++) {
		if (orderItemList[i].id == id) {
			index = i;
			break;
		}
	}

	$("#" + "discount_price_" + id).html('￥' + orderItemList[index].discount_price.toFixed(2)); // 折减
	$("#" + "pay_price_" + id).html('￥' + orderItemList[index].pay_price.toFixed(2)); // 成交价格
	$("#" + "amount_" + id).html('￥'+ (orderItemList[index].pay_price * orderItemList[index].num).toFixed(2)); // 小计

}

/*
 * 计算所有订单条目合计
 */
function calcSumAmount() {
	var orderItemList = getContractItemList();
	var sumAmount = 0.00;
	for (var i = 0; i < orderItemList.length; i++) {
		sumAmount = sumAmount + orderItemList[i].pay_price
				* orderItemList[i].num;
	}
	return sumAmount;
}

/*
 * 显示总计
 */
function displaySumAmount(sumAmount) {
	$("#sumAmount").text(sumAmount.toFixed(2));
}

// ===============合同属性==================

/*
 * return:返回付款类型
 */
function getPayType() {
	var val = 0;
	$("input[name='pay_type']").each(function(index, elem) {
		if (this.checked) {
			val = $(this).val();
			return false;
		}
	});
	return val;
}

/*
 * return:返回质保期
 * 
 */
function getGuaranteePeriod() {
	var val = 0;
	$("input[name='guarantee_period']").each(function(index, elem) {
		if (this.checked) {
			val = $(this).val();
			return false;
		}
	});
	return val;
}



/* 收集合同属性 */
function getContractAttrValue(contractAttrList) {

	/*
	 * guarantee_period delivery_time delivery_address transport_charge
	 * payment_percent_signed payment_amount_signed payment_percent_delivery
	 * payment_amount_delivery days_signed date_signed fee_rate voucher pay_type
	 */

	contractAttrList.forEach(function(item, index) {
		if (item.attrName == 'guarantee_period') {
			item.attrvalue = getGuaranteePeriod();
			item.id=$("#" + item.attrName+'_1').attr("data-id");
		} else if (item.attrName == 'pay_type') {
			item.attrValue = getPayType();
			item.id=$("#" + item.attrName+'_1').attr("data-id");
		} else {
			item.attrValue = $("#" + item.attrName).val();
			item.id=$("#" + item.attrName).attr("data-id");
		}
	});

	return contractAttrList;
}


/* 合同更新确认 */
function contractUpdate() {
	var url = BASE_CONTEXT_PATH + "/back/contract/attrupdate"; // 需要提交的 url

	var contractAttrList = getContractAttrList(); // 合同属性列表
	var contractAttrValList = getContractAttrValue(contractAttrList); // 合同属性值

	var orderItemList = getContractItemList(); // 订单列表行

	var form = document.createElement("form"); // 生成表单
	form.id = "test";
	form.name = "test";
	form.target = "_blank";
	document.body.appendChild(form);

	// 请求参数
	var attrVals = generateHideElement("contractAttrVals", JSON.stringify(contractAttrValList)); 
		
	form.appendChild(attrVals); // 合同属性列表
	

	// 发送请求
	form.method = "post";
	form.action = url;
	/*form.submit();*/
	
	$(form).ajaxSubmit({
		type:"post",
		url:url,
		success : function(res) {
			console.log(res);
			if(res!=null){
				var obj = $.parseJSON(res);
				if(obj.result_code=="success"){
					//util.message(obj.result_msg);
					//util.message("成功生成合同！");
					//window.opener.location.reload(); //刷新父窗口中的网页
					//window.opener.search_normal();  //刷新父窗口中的网页
					//window.close();//关闭当前窗窗口
					resetTab();//重置选项卡
				}else{
					util.message(obj.result_err_msg);
				}
			}
		},
	});
}

// ========================================
/*
 * 生成隐藏表单域函数 params: name:input's name value:inptu's value
 */
var generateHideElement = function(name, value) {
	var tempInput = document.createElement("input");
	tempInput.type = "hidden";
	tempInput.name = name;
	tempInput.value = value;
	return tempInput;
}


/**
 * 显示或隐藏用户质保条款
 * flag
 * 		true:显示;
 * 		false:隐藏
 */
function hideOrShowGuaranteeItem(flag){
	if(flag)
		$("#group_user_guarantee_item").show()
	else
		$("#group_user_guarantee_item").hide();  
		
}

/**
 * 显示或隐藏付款方式组
 * 
 * payType:
 * 		1:付款方式1;2:付款方式2
 * flag:
 * 		true:显示; false:隐藏
 */
function hideOrShowPayType(payType,flag){
	switch(payType)
	{
	case 1:
	  if(flag)
		  $("#group_pay_type_1").show();
	  else
		  $("#group_pay_type_1").hide();
	  break;
	case 2:
		if(flag)
			  $("#group_pay_type_2").show();
		  else
			  $("#group_pay_type_2").hide();
	  break;
	default:
	  
	}
}

/*
 * 初始化合同属性
 */
function initContractAttr() {
	if($("input[name='guarantee_period']").eq(0).attr("checked")=="checked")   //如果质保方式为:原厂标准质保  
		hideOrShowGuaranteeItem(false);  //用户质保条款定制隐藏
	else
		hideOrShowGuaranteeItem(true);  //用户质保条款定制隐藏
	
	
	if($("#pay_type_1").attr("checked")=="checked"){  //如果是付款方式1		
		//hideOrShowPayType(1,true);   //付款方式1条款show
		hideOrShowPayType(2,false);  //付款方式2条款hide
	}
	else{		
		//hideOrShowPayType(2,true);  //付款方式2条款show
		hideOrShowPayType(1,false);  //付款方式1条款hide
	}
}


// ==================页面-READY-初始化======================
$(function() {

	displaySumAmount(calcSumAmount());
	initContractAttr(); // 初始化合同属性
	
	/*折减对话框显示后，折减输入框自动获取focus*/
	$('#modal-container-273078').on('shown.bs.modal', function (e) {
	    // 处理代码...
		//alert("2333");
		$("#discount")[0].focus();
	})

	/**
	 * 折减对话框【确定】按钮-click处理
	 */
	$("#btnConfirm").on('click', function(e) {
		closeDialog();
		var discountStr = $("#discount").val();
		var discount = parseInt(discountStr, 10);
		if (isNaN(discount)) {
			util.message("所输入的不是数字！");
		} else {
			calcAmount(getContractItemId(), discount);
			displayContractItem(getContractItemId());
			displaySumAmount(calcSumAmount());
			reCalcContractSpace();  //重新计算合同留白
			updateContractItem(getContractItemId(),discount); //后台更新折减
		}

	});

	/* 质保期 checkbox click处理 */
	$("input[name='guarantee_period']").on('click', function(e) {

		$("input[name='guarantee_period']").each(function(index, val) {
			(this).checked = false;
		});

		this.checked = true;
		
		if($(this).attr("id")=="guarantee_period_2")
			hideOrShowGuaranteeItem(true);
		else
			hideOrShowGuaranteeItem(false);

	});

	/* 收款方式 checkbox click处理 */
	$("input[name='pay_type']").on('click', function(e) {

		$("input[name='pay_type']").each(function(index, val) {
			(this).checked = false;
		});

		this.checked = true;
		
		if($(this).attr("id")=="pay_type_1"){
			hideOrShowPayType(1,true);
			hideOrShowPayType(2,false);
		}
		else{
			hideOrShowPayType(2,true);
			hideOrShowPayType(1,false);
		}

	});

	/* 折减对话框 -输入金额-enter */
	$("#dialog-form").on("keydown",function(event){		
		if(event.keyCode==13){
			//console.log("debug!");
			$("#btnConfirm").trigger("click");
		}
	})
	
	/* 【合同确认】 按钮 click process */
	$(".btn-update").on('click', function(e) {
		/*contractCreate();*/
		//alert("update stub!");
		contractUpdate();
	});
	
	
	//================金额计算 日期计算（易用性）===================
	//payment_percent_signed  百分比
	//sumAmount  总金额
	
	/* 付款方式1： 合同签订后付款百分比失去焦点时计算金额*/
	$("#payment_percent_signed").blur(function(){
		var percent=parseFloat($(this).val());
		var amount=$("#sumAmount").text();
		//alert("amount:"+amount);
		if(!isNaN(percent)){
			percent=percent / 100;
			var val=percent*amount;
			$("#payment_amount_signed").val(val.toFixed(2));
		}
	});
	
	
	/* 付款方式1： 发货前付款百分比失去焦点时计算金额*/
	$("#payment_percent_delivery").blur(function(){
		var percent=parseFloat($(this).val());
		var amount=$("#sumAmount").text();
		//alert("amount:"+amount);
		if(!isNaN(percent)){
			percent=percent / 100;
			var val=percent*amount;
			$("#payment_amount_delivery").val(val.toFixed(2));
			
			//计算余额
			var percent_signed=parseFloat($("#payment_percent_signed").val());
			if(!isNaN(percent_signed)){
				var balance_amount=amount-(percent_signed/100+percent)*amount;
				$("#balance_amount").val(balance_amount.toFixed(2));
			}
		}
	});
	
	//days_signed
	/*  */
	$("#days_signed").blur(function(){
		var days=parseInt($(this).val());
		var now=new Date();
		//alert("amount:"+amount);
		if(!isNaN(days)){
			//date_signed
			//var val=percent*amount;
			var val=addDate(now,days)
			$("#date_signed").val(val);
		}
	});

});