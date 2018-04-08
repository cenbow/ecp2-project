/*用于判定是否为空*/
(function($) {
	$.isBlank = function(obj) {
		return (!obj || $.trim(obj) === "");
	};
})(jQuery);

/**
 * 根据回传的条件值（处理状态）更新界面
 * 
 * @param dealStateCond
 *            处理状态条件值
 * @returns
 */
function updateUIDealState(dealStateCond) {
	// 横向菜单
	$(".extra-l li").each(function() {
		$(this).find('a').removeClass("curr");
		// console.log("dealStatecond:"+dealStateCond);
		// console.log("li val:"+$(this).val());
		if ($(this).val() == dealStateCond) {
			$(this).find('a').addClass("curr");
		}
	});

	// 纵向菜单
	$(".state-list li").each(function() {
		$(this).removeClass("curr");
		$(this).find('i').hide();
		if ($(this).val() == dealStateCond) {
			$(this).addClass("curr");
			$(this).find('i').show();
			// 设置处理状态当前
			var selectedTxt = $(this).find('a').text();
			var value = $(this).val();

			// console.log("state value:"+value);
			setDealStateCond(selectedTxt, value);

		}
	});
}

/**
 * 纵向时间菜单更新 根据回传的条件值（订单时间）更新界面
 * 
 * @param orderTimeCond
 *            订单时间条件值
 * @returns
 */
function updateUIOrderTime(orderTimeCond) {
	// 纵向菜单
	$(".time-list li").each(function() {
		$(this).removeClass("curr");
		$(this).find('i').hide();
		if ($(this).val() == orderTimeCond) {
			$(this).addClass("curr");
			$(this).find('i').show();
			// 设置处理状态当前
			var selectedTxt = $(this).find('a').text();
			var value = $(this).val();
			setOrderTimeCond(selectedTxt, value);
		}
	});
}

/**
 * 纵向时间菜单更新 根据回传的条件值（订单时间）更新界面
 * 
 * @param orderTimeCond
 *            订单时间条件值
 * @returns
 */
function updateUISearchCond(condType) {
	// 纵向菜单
	$(".search-list li").each(function() {
		$(this).removeClass("curr");
		$(this).find('i').hide();
		if ($(this).val() == condType) {
			$(this).addClass("curr");
			$(this).find('i').show();
			// 设置处理状态当前
			var selectedTxt = $(this).find('a').text();
			var value = $(this).val();
			setSearchCond(selectedTxt, value);
		}
	});
}

/**
 * 设置当前处理状态名称及值
 * 
 * @param selectedTxt
 *            处理状态名称
 * @param value
 *            处理状态值
 * @returns
 */
function setDealStateCond(selectedTxt, value) {
	$("#dealstate-cond").text(selectedTxt);
	$("#dealstate-cond").val(value);
}

/**
 * 设置当前订单时间名称及值
 * 
 * @param selectedTxt
 *            订单时间条件名称
 * @param value
 *            订单时间条件值
 * @returns
 */
function setOrderTimeCond(selectedTxt, value) {
	$("#ordertime-cond").text(selectedTxt);
	$("#ordertime-cond").val(value);
}

/**
 * 设置搜索类型名称及值
 * 
 * @param selectedTxt
 * @param value
 * @returns
 */
function setSearchCond(selectedTxt, value) {
	$("#search-cond").text(selectedTxt);
	$("#search-cond").val(value);
}

/**
 * 根据用户的输入条件查询（包括分页数据）
 * 
 * @returns
 */
function search() {
	parms = new Object(); // 生成参数对象
	// 分页数据
	parms.pageNum = $("#pageNum").val();
	parms.pageSize = $("#pageSize").val();
	
	//alert(parms.pageNum);

	// 时间段、订单状态
	var dealStateCond = $("#dealstate-cond").val();
	var orderTimeCond = $("#ordertime-cond").val();

	parms.dealStateCond = dealStateCond;
	parms.timeCond = orderTimeCond;

	// 搜索类型，搜索条件值
	var condType = $("#search-cond").val();
	var condStr = $("#searchCond").val();

	parms.searchTypeValue = condType;
	parms.condValue = condStr;

	loadContract(parms, null); // 加载页面
}

/**
 * 设置合同状态  （ajax）
 * @param orderId  订单id(pk)
 * @param contractId 合同ID(pk)
 * @param status  合同状态
 * @returns
 */
function setContractStatus(orderId, contractId, status, bindUserJSON){
	var url = BASE_CONTEXT_PATH + "/back/contract/setstatus"; // 需要提交的 url

	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		// dataType: "application/json",
		data : {
			'orderId' : orderId,
			'contractId':contractId,
			'status':status,
			'bindUserJSON':bindUserJSON
		},
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					util.message(obj.result_msg); //显示更新成功能对话框
					search_normal();  //重新加载页面
					
				} else {
					util.message(obj.result_err_msg);
				}
			}
		}

	});
}


/* 查询-合同 */
function search_normal() {
	// $("#searchform").submit(); //提交表单
	search();
}

$(function() {
	// ================INITIALIZE====================
	updateUIDealState(g_dealstate_cond);
	updateUIOrderTime(g_ordertime_cond);
	updateUISearchCond(g_searchTypeValue);

	// ================条件选择 下拉菜单显示===================
	// 订单时间条件(下拉菜单)
	$(".ordertime-cont").hover(function() {
		$(".time-list").show();
	}, function() {
		$(".time-list").hide();
	});

	// 订单处理状态条件（下拉菜单）
	$(".deal-state-cont").hover(function() {
		$(".state-list").show();
	}, function() {
		$(".state-list").hide();
	});

	// 查询条件（下拉菜单）
	$(".search-cont").hover(function() {
		$(".search-list").show();
	}, function() {
		$(".search-list").hide();
	});

	// ==================菜单选择（CLICK）=================
	// 选择时间条件（下拉菜单）
	$(".time-list li").on("click", function() {
		var selectedTxt = $(this).find('a').text();
		var value = $(this).val();
		setOrderTimeCond(selectedTxt, value);
		$(this).blur();
		search_normal();

	});

	// 合同处理条件（下拉菜单）
	$(".state-list li").on("click", function() {
		var selectedTxt = $(this).find('a').text();
		var value = $(this).val();
		setDealStateCond(selectedTxt, value);
		$(this).blur();
		search_normal();

	});

	// 横向菜单（状态）
	$(".extra-l li").on("click", function() {
		var selectedTxt = $(this).find('a').text();
		var value = $(this).val();
		setDealStateCond(selectedTxt, value);
		$(this).blur();
		search_normal();
	});

	// 搜索条件（下拉菜单）
	$(".search-list li").on("click", function() {
		var selectedTxt = $(this).find('a').text();
		var value = $(this).val();
		setSearchCond(selectedTxt, value);
		updateUISearchCond(value);
		$(this).blur();
		var condType = $("#search-cond").val();
		if (condType == 0) { // 如果没有选择条件，则进行刷新
			search_normal();
		}
	});

	// ===================搜索按钮=====================
	/* 搜索按钮 -click */
	$(".start-search").on("click", function() {
		var condType = $("#search-cond").val();
		var condStr = $("#searchCond").val();

		if (condType == 0 || $.isBlank(condStr)) {
			return;
		} else {
			search_normal();

		}

	});

	/* 当在条件输入框按下enter时 */
	$("#searchCond").on("keydown", function(event) {
		if (event.keyCode == 13) {
			$(".start-search").trigger("click");

		}
	});
	
	
	//==============合同状态操作=============
	/*合同【确认】*/
	$('.contract-confirm').on("click",function(e){
		var orderId=$(this).attr("data-order-id");
		var contractId=$(this).attr("data-contract-id");
		var status=$(this).attr("data-contract-status");
		//alert("debug");
		if(status==4){  //如甲方已经确认
			status=3;
			setContractStatus(orderId, contractId, status, null);
		}
		else{
			util.message("执行此操作时，合同当前状态需为：甲方己确认！");
		}
			
	});
	
	/*合同【执行】*/
	$('.contract-execute').on("click",function(e){
		var orderId=$(this).attr("data-order-id");
		var contractId=$(this).attr("data-contract-id");
		var status=$(this).attr("data-contract-status");
		
		if(status==3){  //如乙方（LM）已经确认
			status=5;  //执行状态
			setContractStatus(orderId, contractId, status, null);
		}
		else{
			util.message("执行此操作时，合同当前状态需为：乙方己确认！");
		}
	});
	
	/*合同【完成】*/
	$('.contract-finish').on("click",function(e){
		var orderId=$(this).attr("data-order-id");
		var contractId=$(this).attr("data-contract-id");
		var status=$(this).attr("data-contract-status");
		
		if(status==5){  //如乙方（LM）已经确认
			status=6;  //执行完毕
			loadChooseAccountUserPage(orderId, contractId);//加载选择计费用户对话框页面，并打开对话框
			//TODO
			//setContractStatus(orderId, contractId, status, bindUserJSON);
		}
		else{
			util.message("执行此操作时，合同当前状态需为：执行状态！");
		}
	});
	
	/**
	 * 加载选择计费用户对话框页面，并打开对话框
	 * @param orderId
	 * @returns
	 */
	function loadChooseAccountUserPage(orderId, contractId){
		var url = BASE_CONTEXT_PATH + "/back/contract/getBindUserList"; // 需要提交的 url
		var params = {"orderId": orderId, "contractId":contractId};
		$("#load-choose-account-user-dialog").load(url, params, function(){
			openChooseAccountUserDialog();//打开选择计费用户对话框
		});
	}
	
	/**
	 * 打开选择计费用户对话框
	 */
	function openChooseAccountUserDialog() {
		$('#choose-account-user-dialog').modal({
			backdrop : 'static',
			keyboard : false
		});
	}

	/**
	 * 关闭选择计费用户对话框
	 * @returns
	 */
	function closeChooseAccountUserDialog() {	
		$("#choose-account-user-dialog").modal("hide");
	}

	// ==============分页通用================
	/*
	 * 【分页】导航： 当点击页号时读取需要导航到的页码及每页记录数（pageNum,pageSize）
	 * data-bind:pageNum-pageSize形式 如果data-bind为空字符串，则不做动作 当用户点击某页时，则发送与筛选相同的请求
	 */

	$(".pagination li a").on('click', function(e) {
		// alert($(this).attr("data-bind"));
		var dataBind = $(this).attr("data-bind");
		// 当dataBind有数据时处理
		if (dataBind != "") {
			var pageArr = new Array();
			pageArr = dataBind.split("-");
			// 置隐藏表单数据
			$("#pageNum").val(pageArr[0]);
			$("#pageSize").val(pageArr[1]);
			// 发送请求
			search_normal();
		}

	});
	
	//将2017-07-21格式时间转换为2017年07月21日格式
	$(".contract-time").each(function(){
		var oldFmtDate=$(this).text();
		if(oldFmtDate=="" || oldFmtDate==null){
			$(this).html("&nbsp;年&nbsp;月&nbsp;日");
		}else{
			var dateArr = oldFmtDate.split(" ");  
			var cnFmtDate=getCNFormatDate(dateArr[0]);
			$(this).text(cnFmtDate+' '+dateArr[1]);	
		}
		
	});

}); // end of $();
