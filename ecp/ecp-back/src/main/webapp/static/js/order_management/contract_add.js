//===============变量定义=================
var g_orderItemId = 0;

/* 设置orderItemId */
function setOrderItemId(orderItemId) {
	g_orderItemId = orderItemId;
}

function getOrderItemId() {
	return g_orderItemId;
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

/**
 * 设置价格限制信息
 * @param orderItem
 * @returns
 */
function setDialogPriceLimitInfo(orderItem){
	console.log('debug----------');
	$("#price-limit-info").text('(最低限价:'+orderItem.lowest_price+'---'+'最高限价:'+orderItem.highest_price+')');
}

/* reset dialog */
function resetDialog() {
	$("#dialog-form")[0].reset();
}

// ===============合同条目================

/* 获取合同行项目列表 */
function getOrderItemList() {
	return g_orderItemList;
}

/*
 * 显示折减对话框
 */
function displayDiscountDialog(e) {
	var orderItemId = $(e).attr("data-bind"); // get orderItem'id
	setOrderItemId(orderItemId);

	var sku_name = $("#sku_name_" + orderItemId).html(); // get sku_name and set dialog's title
	setDialogTitle("折减金额(" + sku_name + ")");
	
	var orderItem=getOrderItemById(orderItemId);   //当前订单条目
	setDialogPriceLimitInfo(orderItem);
	
	

	resetDialog();
	showDialog();
}

/**
 * 根据订单条目ID获取相应的条目
 * @param id
 * @returns  如果查询到则返回条目对象,否则返回null
 */
function getOrderItemById(id){
	var orderItemList = getOrderItemList();
	var index = -1;
	for (var i = 0; i < orderItemList.length; i++) {
		if (orderItemList[i].id == id) {
			index = i;
			break;
		}
	}
	if(index!=-1)
		return orderItemList[index];
	else
		return null
}

/*
 * 初始化订单条目中的折减字段为0
 */
function initOrderItemDiscount() {
	var orderItemList = getOrderItemList();
	for (var i = 0; i < orderItemList.length; i++) {
		orderItemList[i].discount_price = 0;
		orderItemList[i].pay_price = orderItemList[i].primitive_price;
		orderItemList[i].pay_price_total = orderItemList[i].primitive_price
				* orderItemList[i].num;
	}
}



/*
 * 计算订单条目小计 params: id:order item id discount:折减金额
 */
function calcAmount(id, discount) {
	var orderItemList = getOrderItemList();
	var index = -1;
	for (var i = 0; i < orderItemList.length; i++) {
		if (orderItemList[i].id == id) {
			index = i;
			break;
		}
	}
	orderItemList[index].discount_price = discount;
	orderItemList[index].pay_price = orderItemList[index].primitive_price
			- discount;
	orderItemList[index].pay_price_total = orderItemList[index].pay_price
			* orderItemList[index].num;
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
function displayOrderTtem(id) {
	var orderItemList = getOrderItemList();
	var index = -1;
	for (var i = 0; i < orderItemList.length; i++) {
		if (orderItemList[i].id == id) {
			index = i;
			break;
		}
	}

	$("#" + "discount_price_" + id).html(
			'￥' + orderItemList[index].discount_price.toFixed(2)); // 折减
	$("#" + "pay_price_" + id).html(
			'￥' + orderItemList[index].pay_price.toFixed(2)); // 成交价格
	$("#" + "amount_" + id)
			.html(
					'￥'
							+ (orderItemList[index].pay_price * orderItemList[index].num)
									.toFixed(2)); // 小计

}

/*
 * 计算所有订单条目合计
 */
function calcSumAmount() {
	var orderItemList = getOrderItemList();
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
	$("input[name='guarantee_period']").eq(0).attr("checked", true); //设置质保方式为:原厂标准质保  
	hideOrShowGuaranteeItem(false)  //用户质保条款定制隐藏
	$("input[name='pay_type']").eq(0).attr("checked", true);  //设置付款方式1为默认
	hideOrShowPayType(2,false);  //付款方式2条款显示
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
		} else if (item.attrName == 'pay_type') {
			item.attrValue = getPayType();
		} else {
			item.attrValue = $("#" + item.attrName).val();
		}
	});

	return contractAttrList;
}

/* 合同预览 */
function contractPreview() {
	var url = BASE_CONTEXT_PATH + "/back/contract/preview"; // 需要提交的 url

	var contractAttrList = getContractAttrList(); // 合同属性列表
	var contractAttrValList = getContractAttrValue(contractAttrList); // 合同属性值

	var orderItemList = getOrderItemList(); // 订单列表行

	var form = document.createElement("form"); // 生成表单
	form.id = "test";
	form.name = "test";
	form.target = "_blank";
	document.body.appendChild(form);

	// 请求参数
	var attrVals = generateHideElement("contractAttrVals", JSON
			.stringify(contractAttrValList)), orderItems = generateHideElement(
			"orderItems", JSON.stringify(orderItemList));
	form.appendChild(attrVals); // 合同属性列表
	form.appendChild(orderItems); // 订单列表行

	// 发送请求
	form.method = "post";
	form.action = url;
	form.submit();
}

/* 合同确认 */
function contractCreate() {
	var url = BASE_CONTEXT_PATH + "/back/contract/create"; // 需要提交的 url

	var contractAttrList = getContractAttrList(); // 合同属性列表
	var contractAttrValList = getContractAttrValue(contractAttrList); // 合同属性值

	var orderItemList = getOrderItemList(); // 订单列表行

	var form = document.createElement("form"); // 生成表单
	form.id = "test";
	form.name = "test";
	form.target = "_blank";
	document.body.appendChild(form);

	// 请求参数
	var attrVals = generateHideElement("contractAttrVals", JSON.stringify(contractAttrValList)), 
		orderItems = generateHideElement("orderItems", JSON.stringify(orderItemList));
	
	form.appendChild(attrVals); // 合同属性列表
	form.appendChild(orderItems); // 订单列表行

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
					search();//重新加载当前页列表
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





// ==================页面-READY-初始化======================
$(function() {

	initOrderItemDiscount();
	displaySumAmount(calcSumAmount());
	initContractAttr(); // 初始化合同属性
	
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
			calcAmount(getOrderItemId(), discount);
			displayOrderTtem(getOrderItemId());
			displaySumAmount(calcSumAmount());
			reCalcContractSpace();  //重新计算合同留白
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

	/* 【合同预览】 按钮 click process */
	$(".btn-preview").on('click', function(e) {
		// alert("质保期："+getGuaranteePeriod());
		// alert("付款类型:"+getPayType());
		contractPreview();

	});

	/* 【合同确认】 按钮 click process */
	$(".btn-create").on('click', function(e) {
		$(this).attr('disabled',true); 
		contractCreate();
	});
	
	/* 折减对话框 -输入金额-enter */
	$("#dialog-form").on("keydown",function(event){		
		if(event.keyCode==13){
			//console.log("debug!");
			$("#btnConfirm").trigger("click");
		}
	})
	
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