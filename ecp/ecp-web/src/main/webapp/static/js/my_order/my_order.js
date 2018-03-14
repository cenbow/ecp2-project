//=================CRUD==========================

/**
 * 在页面中将订单删除
 * @param id  订单id(primary id)
 * @returns
 */
function removeDeletedOrder(id){
	var tbody=$("#tb-"+id);
	tbody.remove();
}

/**
 * 功能： 删除订单(ajax请求) 
 * @param id 订单id（primary key）
 * @returns  
 */
function deleteOrder(id) {
	var url = BASE_CONTEXT_PATH + "/front/order/delete"; // 需要提交的 url

	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		// dataType: "application/json",
		data : {
			'id' : id,
		},
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					// util.message(obj.result_msg); //显示删除成功能对话框，此处省略
					//loadOrder(); // 操作成功后重新加载购物车列表
					removeDeletedOrder(id);
				} else {
					util.message(obj.result_err_msg);
				}
			}
		}

	});
}

/**
 * 取消订单
 * @param id 订单id(primary key)
 * @returns
 */
function cancelOrder(id){
	var url = BASE_CONTEXT_PATH + "/front/order/cancel"; // 需要提交的 url

	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		// dataType: "application/json",
		data : {
			'id' : id,
		},
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					// util.message(obj.result_msg); //显示删除成功能对话框，此处省略
					sendRequest(); // 操作成功后重新加载					
				} else {
					util.message(obj.result_err_msg);
				}
			}
		}

	});
}


/**
 * 恢复订单
 * @param id 订单id(primary key)
 * @returns
 */
function buyAgainOrder(id){
	var url = BASE_CONTEXT_PATH + "/front/order/buyagain"; // 需要提交的 url

	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		// dataType: "application/json",
		data : {
			'id' : id,
		},
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					// util.message(obj.result_msg); //显示删除成功能对话框，此处省略
					sendRequest(); // 操作成功后重新加载					
				} else {
					util.message(obj.result_err_msg);
				}
			}
		}

	});
}

/**
 * 设置合同状态  （ajax）
 * @param orderId  订单id(pk)
 * @param contractId 合同ID(pk)
 * @param status  合同状态
 * @returns
 */
function setContractStatus(orderId,contractId,status){
	var url = BASE_CONTEXT_PATH + "/back/contract/frontsetstatus"; // 需要提交的 url

	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		// dataType: "application/json",
		data : {
			'orderId' : orderId,
			'contractId':contractId,
			'status':status
		},
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					util.message(obj.result_msg); //显示更新成功能对话框
					sendRequest();  //重新加载页面
					
				} else {
					util.message(obj.result_err_msg);
				}
			}
		}

	});
}

/**
 * 根据回传的条件值（处理状态）更新界面
 * @param dealStateCond  处理状态条件值
 * @returns
 */
function updateUIDealState(dealStateCond){
	//横向菜单  
	$(".extra-l li").each(function(){
		$(this).find('a').removeClass("curr");
		//console.log("dealStatecond:"+dealStateCond);
		//console.log("li val:"+$(this).val());
		if($(this).val()==dealStateCond){
			$(this).find('a').addClass("curr");
		}
	});
	
	//纵向菜单
	$(".state-list li").each(function(){
		$(this).removeClass("curr");
		$(this).find('i').hide();
		if($(this).val()==dealStateCond){
			$(this).addClass("curr");
			$(this).find('i').show();
			//设置处理状态当前
			var selectedTxt=$(this).find('a').text();
			var value=$(this).val();
			
			//console.log("state value:"+value);
			setDealStateCond(selectedTxt,value);
			
			
		}
	});
}

/**
 * 纵向时间菜单更新
 * 根据回传的条件值（订单时间）更新界面  
 * @param orderTimeCond  订单时间条件值
 * @returns
 */
function updateUIOrderTime(orderTimeCond){
	//纵向菜单
	$(".time-list li").each(function(){
		$(this).removeClass("curr");
		$(this).find('i').hide();
		if($(this).val()==orderTimeCond){
			$(this).addClass("curr");
			$(this).find('i').show();
			//设置处理状态当前
			var selectedTxt=$(this).find('a').text();
			var value=$(this).val();
			setOrderTimeCond(selectedTxt,value);
		}
	});
}

/**
 * 设置当前处理状态名称及值
 * @param selectedTxt  处理状态名称
 * @param value  处理状态值
 * @returns
 */
function setDealStateCond(selectedTxt,value){
	$("#dealstate-cond").text(selectedTxt);
	$("#dealstate-cond").val(value);
}

/**
 * 设置当前订单时间名称及值
 * @param selectedTxt  订单时间条件名称
 * @param value  订单时间条件值
 * @returns
 */
function setOrderTimeCond(selectedTxt,value){
	$("#ordertime-cond").text(selectedTxt);
	$("#ordertime-cond").val(value);
}

function sendRequest(){
	var dealStateCond=$("#dealstate-cond").val();
	var orderTimeCond=$("#ordertime-cond").val(); 
	loadOrder(orderTimeCond,dealStateCond);
}


//===============page loaded ready==================

$(function(){
	//================INITIALIZE====================
	updateUIDealState(g_dealstate_cond);
	updateUIOrderTime(g_ordertime_cond);
	
	
	//=================回调函数配置=====================
	/*【删除】订单 -click */
	$(".order-del").on('click',function(){		
		var orderId=$(this).attr("data-id");  //获取订单ID
		var contractState=$(this).attr("contract-state");   //获取订单中合同状态
		if(contractState=='1') {
			util.delConfirm("确认删除？", orderId, "deleteOrder");
		}
		else{
			util.message("己建立合同,不可删除(只有未建合同的订单可删除)！");
		}
			
				
	});
	
	/*【取消】订单 -click */
	$(".btn-cancel").on('click',function(){		
		var orderId=$(this).attr("data-id");  //获取订单ID
		var contractState=$(this).attr("contract-state");  //获取订单合同状态
		if(contractState=='1') {
			cancelOrder(orderId);
		}
		else{
			util.message("己建立合同,不可取消(只有未建合同的订单可取消)！");
		}
				
	});
	
	/*【恢复】订单 -click */
	$(".btn-buy-again").on('click',function(){		
		var orderId=$(this).attr("data-id");  //获取订单ID
		buyAgainOrder(orderId);		
	});
	
	$(".btn-confirm").on('click',function(){		
		var orderId=$(this).attr("data-id");  //获取订单ID
		var contractId=$(this).attr("data-contract-id");
		var status=$(this).attr("data-contract-state");
		
		if(status==2){  //如尚未建立合同
			status=4;  //甲方确认
			setContractStatus(orderId,contractId,status);
		}
		else{
			util.message("尚未建立合同！");
		}
	});
	
	//================条件选择 下拉菜单显示===================
	//订单时间条件(下拉菜单)
	$(".ordertime-cont").hover(
			function() {
				$(".time-list").show();			
			}, 
			function() {
				$(".time-list").hide();		
			}
		);
	
	//订单处理状态条件（下拉菜单）
	$(".deal-state-cont").hover(
			function() {
				$(".state-list").show();
			}, 
			function() {
				$(".state-list").hide();		
			}
		);
	
	//==================菜单选择（CLICK）=========================
	//选择时间条件（下拉菜单）
	$(".time-list li").on("click",function(){
		var selectedTxt=$(this).find('a').text();
		var value=$(this).val();
		setOrderTimeCond(selectedTxt,value);
		sendRequest();		
	});
	
	//合同处理条件（下拉菜单）
	$(".state-list li").on("click",function(){
		var selectedTxt=$(this).find('a').text();
		var value=$(this).val();		
		setDealStateCond(selectedTxt,value);
		sendRequest();
		
	});
	
	$(".extra-l li").on("click",function(){
		var selectedTxt=$(this).find('a').text();
		var value=$(this).val();		
		setDealStateCond(selectedTxt,value);
		sendRequest();		
	});
	
	
})