
/*用于判定是否为空*/
(function($){
	$.isBlank = function(obj){
	return(!obj || $.trim(obj) === "");
		  };
})(jQuery);


//----------------------查询-订单-----------------------
/**
 * 根据用户的输入条件查询（包括分页数据）
 * @returns
 */
function search(){
	var parms=new Object(); //生成参数对象
	//分数据
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
	
	//区域条件
	var provinceName=$("#provinceName").val();
	var cityName=$("#cityName").val();
	var countyName=$("#countyName").val();
	
	parms.provinceName=provinceName;
	parms.cityName=cityName;
	parms.countyName=countyName;
	
	//用户/角色条件
	var option=$("#select-user-role option:selected");
	var userId=option.attr("data-bind-userid");
	var roleId=option.attr("data-bind-roleid");
	
	parms.userId=userId;
	parms.roleId=roleId;
	
	loadOrder(parms,null); // 加载页面
}

/*功能: 加载当前页
 * 	采用回传的参数进行查询.
 * 	所回传的参数保存在页面 order_table.html中
 */
function loadCurrentPage(){
	var parms=new Object(); //生成参数对象
	//分页数据
	parms.pageNum=$("#pageNum").val();
	parms.pageSize=$("#pageSize").val();
	
	/*
	 	var g_ordertime_cond = [[${orderTimeCond}]];  			//回传的时间条件 
		var g_dealstate_cond=[[${dealStateCond}]];    		//回传的合同处理状态条件
		var g_searchTypeValue = [[${searchTypeValue}]];  	//查询类型值
		var g_condValue=[[${condValue}]]; 					//查询条件值
		
		var ret_provinceName=[[${provinceName}]];  		 	//回传的区域条件
		var ret_cityName=[[${cityName}]];
		var ret_countyName=[[${countyName}]];
		
		var ret_userId=[[${userId}]];					 	//回传的角色条件
		var ret_roleId=[[${roleId}]];
	 */
	
	//时间段、订单状态
	var dealStateCond=g_dealstate_cond;
	var orderTimeCond=g_ordertime_cond; 
	
	parms.dealStateCond=dealStateCond;
	parms.orderTimeCond=orderTimeCond;
	
	//搜索类型，搜索条件值
	var condType=g_searchTypeValue;
	var condStr=g_condValue;
	
	parms.searchTypeValue=condType;
	parms.condValue=condStr;
	
	//区域条件
	var provinceName=ret_provinceName;
	var cityName=ret_cityName;
	var countyName=ret_countyName;
	
	parms.provinceName=provinceName;
	parms.cityName=cityName;
	parms.countyName=countyName;
	
	//用户/角色条件
	//var option=$("#select-user-role option:selected");
	var userId=ret_userId;
	var roleId=ret_roleId;
	
	parms.userId=userId;
	parms.roleId=roleId;
	
	
	
	loadOrder(parms,null); // 加载页面
}

function search_normal() {
	search();
}

//--------------------区域操作-----------------------
/**
 * 复位地区列表
 * @returns
 */
function resetDistPickerDeep(){
	 var distpicker = $('#distpicker');
	 distpicker.distpicker('reset', true);
}

/**
 * 同步更新地区(省,市,县)编码、名称
 * @returns
 */
function syncUpdateHiddenAreaCode(){
	
	//省级名称及编码
	//console.log("debug");
	var code= $("#province").find("option:selected").attr("data-code");  //省级编码、名称
	var name= $("#province").find("option:selected").val();
	//console.log("name:"+name);
	$("#provinceCode").val(code);
	$("#provinceName").val(name);	
	//console.log("debug end");
	
	
	//市级名称及编码
	code= $("#city").find("option:selected").attr("data-code");  //市级编码、名称
	name= $("#city").find("option:selected").val();
	$("#cityCode").val(code);
	$("#cityName").val(name);
	
	
	//区县级名称及编码
	code= $("#district").find("option:selected").attr("data-code");  //县区级编码、名称
	name= $("#district").find("option:selected").val();
	console.log("countyName:"+name);
	$("#countyCode").val(code);
	$("#countyName").val(name);
}

//------------------------界面交互-----------------------

/**
 * 加载页面:编辑-最终用户
 * @param url	
 * @param orderId	order:自增ID
 * @param orderNo 订单No
 * @returns
 */
function editFinalCustomer(url,orderId,orderNo){
	
	var params = {"orderId":orderId, "orderNo":orderNo};
	$("#edit-body").load(url,params, function(){
		
		$("#edit-tab").removeClass("hide");
		$("#edit-tab-title").text("订单最终用户管理");
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
 * 根据回传的"区域"数据更新区域选择界面 
 * @returns
 */
function updateUIArea(){
	$("#province").val(ret_provinceName);
	$("#province").trigger("change");
	
	$("#city").val(ret_cityName);
	$("#city").trigger("change");
	
	$("#district").val(ret_countyName);
}

/**
 * 根据回传的值:置用户角色下拉框当前选项
 * @returns
 */
function updateUserRole(){
	var userId=ret_userId;
	var roleId=ret_roleId;
	
	if(userId==0){
		$("#select-user-role option").eq(0).attr("selected",true);
	}
	else{
		$("#select-user-role option").each(function(){
			if(($(this).attr("data-bind-userid")==userId) && ($(this).attr("data-bind-roleid")==roleId)){
				$(this).attr("selected",true);
				return;
			}
			
		});		
	}	
}

//-------------------数据交互----------------------

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


/*function sendRequest(){
	var dealStateCond=$("#dealstate-cond").val();
	var orderTimeCond=$("#ordertime-cond").val(); 
	loadOrder(orderTimeCond,dealStateCond);
}*/


//-------------------page loaded ready------------------------
$(function() {
	
	resetDistPickerDeep(); 
	
	updateUIArea();
	updateUserRole();
	
	//-----------INITIALIZE-----------
	updateUIDealState(g_dealstate_cond);
	updateUIOrderTime(g_ordertime_cond);
	updateUISearchCond(g_searchTypeValue);
	

	//----------打开最终用户列表------------
	/*
	 * 【最终用户】按钮
	 */
	$(".edit-finalcustomer").on("click", function(e) {
		var url = BASE_CONTEXT_PATH + "/back/finalcustomer/edit"; //需要提交的 url
		
		var orderId = $(this).attr("data-id");  //订单id(PK)
		var orderNo = $(this).attr("data-orderid");  //订单号
		//var contractState=$(this).attr("data-contractState"); //合同状态
		
		editFinalCustomer(url, orderId, orderNo);  //显示最终用户编辑界面.
		
	});
	
	//---------------触发查询------------------
	/* 搜索按钮 -click */
	$(".start-search").on("click",function(){
		var condType=$("#search-cond").val();
		var condStr=$("#searchCond").val();
		
		/*if(condType==0 || $.isBlank(condStr)){
			return;
		}
		else{
			search_normal();
			
		}*/
		
		search_normal();
		
	});
	
	/*当在条件输入框按下enter时*/
	$("#searchCond").on("keydown",function(event){
		if(event.keyCode==13){
			$(".start-search").trigger("click");
			
		}
	});
	
	//当选择角色时,自动查询
	$("#select-user-role").on("change",function(){
		console.log("debug: start search!");
		$(".start-search").trigger("click");
	});
	
	//--------------地区选择---------------
	/*
	 * 省份:change  当“省份”变化时，将code赋值给value; 
	 */
	$("#province").on("change",function(){syncUpdateHiddenAreaCode();});
	
	/*
	 *当“市级”变化时，将ode赋值给value; 
	 */
	$("#city").on("change",function(){syncUpdateHiddenAreaCode();});
	
	/*
	 *当“县级”变化时，将code赋值给value; 
	 */
	$("#district").on("change",function(){syncUpdateHiddenAreaCode();});
	
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

});