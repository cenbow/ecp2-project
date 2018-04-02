
/*用于判定是否为空*/
(function($){
	$.isBlank = function(obj){
	return(!obj || $.trim(obj) === "");
		  };
})(jQuery);

/*查询-订单 */
/**
 * 根据用户的输入条件查询（包括分页数据）
 * @returns
 */
function search(){
	parms=new Object(); //生成参数对象
	//分页数据
	parms.pageNum=$("#pageNum").val();
	parms.pageSize=$("#pageSize").val();
	
	//时间段、订单状态
	var dealStateCond=$("#dealstate-cond").val();
	var orderTimeCond=$("#ordertime-cond").val(); 
	
	parms.dealStateCond=dealStateCond;
	parms.orderTimeCond=orderTimeCond;
	
	//搜索类型，搜索条件值
	var condType=$("#search-cond").val();
	var condStr=$("#searchCond").val();
	
	parms.searchTypeValue=condType;
	parms.condValue=condStr;
	
	loadOrder(parms,null); // 加载页面
}

function search_normal() {
	//console.log("debug");
	search();
}

/**
 * 生成隐藏表单域函数
 * 
 * @param {Object}
 *            name input's name
 * @param {Object}
 *            value value:inptu's value
 */
var generateHideElement = function(name, value) {
	var tempInput = document.createElement("input");
	tempInput.type = "hidden";
	tempInput.name = name;
	tempInput.value = value;
	return tempInput;
}

/**
 * 创建form并提交,创建合同请求 *
 * 
 * @param {Object}
 *            url 提交的地址
 * @param {Object}
 *            id 订单id
 * @param {Object}
 *            orderId 订单号
 */
function createFormAndCommit(url, id, orderId) {
	/*var form = document.createElement("form");
	form.id = "test";
	form.name = "test";
	form.target="_blank";
	document.body.appendChild(form);

	// 生成隐藏表单中的内容
	var id = generateHideElement("id", id), 
		orderId = generateHideElement("orderId", orderId);

	form.appendChild(id);
	form.appendChild(orderId);

	form.method = "post";
	form.action = url;
	form.submit();*/
	var params = {"id":id, "orderId":orderId};
	$("#edit-body").load(url,params, function(){
		//TODO
		$("#edit-tab").removeClass("hide");
		$("#edit-tab-title").text("创建合同");
		$('#tabs-14933 a[href="#panel-602679"]').tab('show');
	});
}

/**
 * @param url 请求编辑合同的URL地址
 * @param id  合同URL
 * @returns
 */
function editContract(url, id) {
	/*var form = document.createElement("form");
	form.id = "test1";
	form.name = "test1";
	form.target="_blank";
	document.body.appendChild(form);

	// 生成隐藏表单中的内容
	var id = generateHideElement("id", id); 
		

	form.appendChild(id);

	form.method = "post";
	form.action = url;
	form.submit();*/
	var params = {"id":id};
	$("#edit-body").load(url, params, function(){
		//TODO
		$("#edit-tab").removeClass("hide");
		$("#edit-tab-title").text("编辑合同");
		$('#tabs-14933 a[href="#panel-602679"]').tab('show');
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
 * 纵向时间菜单更新
 * 根据回传的条件值（订单时间）更新界面  
 * @param orderTimeCond  订单时间条件值
 * @returns
 */
function updateUISearchCond(condType){
	//纵向菜单
	$(".search-list li").each(function(){
		$(this).removeClass("curr");
		$(this).find('i').hide();
		if($(this).val()==condType){
			$(this).addClass("curr");
			$(this).find('i').show();
			//设置处理状态当前
			var selectedTxt=$(this).find('a').text();
			var value=$(this).val();
			setSearchCond(selectedTxt,value);
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

/**
 * 设置搜索类型名称及值
 * @param selectedTxt
 * @param value
 * @returns
 */
function setSearchCond(selectedTxt,value){
	$("#search-cond").text(selectedTxt);
	$("#search-cond").val(value);
}


function sendRequest(){
	var dealStateCond=$("#dealstate-cond").val();
	var orderTimeCond=$("#ordertime-cond").val(); 
	loadOrder(orderTimeCond,dealStateCond);
}


//==================page loaded ready==============
$(function() {
	//================INITIALIZE====================
	updateUIDealState(g_dealstate_cond);
	updateUIOrderTime(g_ordertime_cond);
	updateUISearchCond(g_searchTypeValue);
	

	//===================BUSINESS业务处理==============

	/*
	 * 【创建合同】按钮
	 */
	$(".create-contract").on("click", function(e) {
		var url = BASE_CONTEXT_PATH + "/back/contract/add"; // 需要提交的 url
		var id = $(this).attr("data-id");  //合同id(PK)
		var orderId = $(this).attr("data-orderid");  //合同号
		var contractState=$(this).attr("data-contractState"); //状态
		if(contractState==1){  //如果是未建合同状态
			createFormAndCommit(url, id, orderId);
		}
		else{
			util.message("已经建立合同！");
		}

		
	});
	
	/*
	 * 【编辑合同】按钮
	 */
	$(".edit-contract").on("click", function(e) {
		var url = BASE_CONTEXT_PATH + "/back/contract/edit"; // 需要提交的 url
		var id = $(this).attr("data-id");   //订单id (pk)
		var orderId = $(this).attr("data-orderid"); //订单id
		var contractId=$(this).attr("data-contract-id");
		var contractState=$(this).attr("data-contractState");  //合同状态
		
		if(contractState==2){  //如果是己建合同状态，则可进行编辑
			editContract(url,contractId);  
		}
		else{
			util.message("此状态不可编辑合同！");
		}

		
	});
	
	
	/* 搜索按钮 -click */
	$(".start-search").on("click",function(){
		var condType=$("#search-cond").val();
		var condStr=$("#searchCond").val();
		
		if(condType==0 || $.isBlank(condStr)){
			return;
		}
		else{
			search_normal();
			
		}
		
	});
	
	/*当在条件输入框按下enter时*/
	$("#searchCond").on("keydown",function(event){
		if(event.keyCode==13){
			$(".start-search").trigger("click");
			
		}
	});
	
	//======================分页（页码导航）==============

	/*
	 * 【分页】导航： 当点击页号时读取需要导航到的页码及每页记录数（pageNum,pageSize）
	 * data-bind:pageNum-pageSize形式 如果data-bind为空字符串，则不做动作 当用户点击某页时，则发送与筛选相同的请求
	 */

	$(".pagination li a").on('click', function(e) {
		// alert($(this).attr("data-bind"));
		//console.log("debug1");
		var dataBind = $(this).attr("data-bind");
		// 当dataBind有数据时处理
		if (dataBind != "") {
			var pageArr = new Array();
			pageArr = dataBind.split("-");
			// 置隐藏表单数据
			$("#pageNum").val(pageArr[0]);
			$("#pageSize").val(pageArr[1]);			
			search_normal();  // 发送请求
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
	
	//查询条件（下拉菜单）
	$(".search-cont").hover(
			function() {
				$(".search-list").show();			
			}, 
			function() {
				$(".search-list").hide();		
			}
		);
	
	//==================菜单选择（CLICK）=================
	//选择时间条件（下拉菜单）
	$(".time-list li").on("click",function(){
		var selectedTxt=$(this).find('a').text();
		var value=$(this).val();
		setOrderTimeCond(selectedTxt,value);
		$(this).blur();
		search_normal();
		
	});
	
	//合同处理条件（下拉菜单）
	$(".state-list li").on("click",function(){
		var selectedTxt=$(this).find('a').text();
		var value=$(this).val();		
		setDealStateCond(selectedTxt,value);
		$(this).blur();
		search_normal();
		
	});	
	
	//横向菜单（状态）
	$(".extra-l li").on("click",function(){
		var selectedTxt=$(this).find('a').text();
		var value=$(this).val();		
		setDealStateCond(selectedTxt,value);
		$(this).blur();
		search_normal();		
	});
	
	//搜索条件（下拉菜单）
	$(".search-list li").on("click",function(){
		var selectedTxt=$(this).find('a').text();
		var value=$(this).val();		
		setSearchCond(selectedTxt,value);
		updateUISearchCond(value);
		$(this).blur();
		var condType=$("#search-cond").val();
		if(condType==0){   //如果没有选择条件，则进行刷新
			search_normal();  
		}
	});
	
	//========================================================以下为业绩js=============================================================
	
	/**
	 * 【查看业绩】按钮
	 */
	$(".look-performance-btn").on("click", function(e) {
		var url = BASE_CONTEXT_PATH + "/back/performance/get-performance"; // 需要提交的 url
		var id = $(this).attr("data-id");   //订单id (pk)
		var orderId = $(this).attr("data-orderid"); //订单id
		var contractId=$(this).attr("data-contract-id");
		var contractState=$(this).attr("data-contractState");  //合同状态
		var params = {"orderId":id};
		loadTablePage(url, params);
		
	});
	
	/**
	 * 【今年业绩】按钮
	 */
	$("#this-year-performance-btn").on("click", function(e) {
		var url = BASE_CONTEXT_PATH + "/back/performance/get-performance"; // 需要提交的 url
		var fullYear = getFullYear();
		var userId = $("#select-user").val();
		var roleId = $("#select-role").val();
		var params = {"fullYear":fullYear, "userId":userId, "roleId":roleId};
		loadTablePage(url, params);
		
	});
	
	/**
	 * 【去年业绩】按钮
	 */
	$("#last-year-performance-btn").on("click", function(e) {
		var url = BASE_CONTEXT_PATH + "/back/performance/get-performance"; // 需要提交的 url
		var fullYear = getFullYear();
		var userId = $("#select-user").val();
		var roleId = $("#select-role").val();
		var params = {"fullYear":fullYear-1, "userId":userId, "roleId":roleId};
		loadTablePage(url, params);
		
	});
	
	/**
	 * 【前年业绩】按钮
	 */
	$("#two-years-ago-performance-btn").on("click", function(e) {
		var url = BASE_CONTEXT_PATH + "/back/performance/get-performance"; // 需要提交的 url
		var fullYear = getFullYear();
		var userId = $("#select-user").val();
		var roleId = $("#select-role").val();
		var params = {"fullYear":fullYear-2, "userId":userId, "roleId":roleId};
		loadTablePage(url, params);
		
	});
	
	/**
	 * 进度列表
	 */
	$("#sales-progress-btn").on("click", function(e) {
		var url = BASE_CONTEXT_PATH + "/back/performance/get-sales-progress"; // 需要提交的 url
		var fullYear = getFullYear();
		var userId = $("#select-user").val();
		var roleId = $("#select-role").val();
		var params = {"fullYear":fullYear, "userId":userId, "roleId":roleId};
		loadTablePage(url, params);
		
	});
	
	
	
	/**
	 * 加载业绩列表页面
	 * @param url
	 * @param params
	 * @returns
	 */
	function loadTablePage(url, params){
		$("#edit-body").load(url, params, function(){
			$("#edit-tab").removeClass("hide");
			$("#edit-tab-title").text("业绩列表");
			$('#tabs-14933 a[href="#panel-602679"]').tab('show');
		});
	}
	/**
	 * 获取当前完整年份
	 * @returns
	 */
	function getFullYear(){
		//获取完整的日期  
		var date=new Date;  
		var year=date.getFullYear(); //获取当前完整年份
		return year;
	}

});