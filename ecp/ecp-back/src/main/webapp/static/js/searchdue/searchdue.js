
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
	
	//欠款状态
	var option1=$("#select-total-pay-flag option:selected");
	var totalPayFlag=option1.val();
	
	parms.totalPayFlag=totalPayFlag;
	
	loadOrder(parms,null); //加载页面
}

function search_normal() {
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
 * 加载指定订单的回款页面
 * @param url
 * @param orderId
 * @param orderNo
 * @param urserId  所绑定的内部用户ID(条件)
 * @param roleId   所绑定用户角色ID(条件)
 * @returns
 */
function showOrderFee(that){
	var url = BASE_CONTEXT_PATH +REQUEST_MAPPING+ "/showfee"; //需要提交的 url
	
	var orderId = $(that).attr("data-id");  		//订单id(PK)
	var orderNo = $(that).attr("data-orderid");  	//订单号
	
	//所选用户及角色
	var option=$("#select-user-role option:selected");
	var userId=option.attr("data-bind-userid");
	var roleId=option.attr("data-bind-roleid");
	
	//var contractState=$(that).attr("data-contractState"); //合同状态
	
	var params = {	"orderId":orderId, 
					"orderNo":orderNo,
					"userId":userId,
					"roleId":roleId
				 };
	
	$("#edit-body").load(url,params, function(){		
		$("#edit-tab").removeClass("hide");
		$("#edit-tab-title").text("订单回款列表");
		$('#tabs-14933 a[href="#panel-602679"]').tab('show');
	});	
}

/**
 * 加载所有订单的回款页面
 * @param url
 * @param userId 所绑定的内部用户ID(条件)
 * @param roleId 所绑定用户角色ID(条件)
 * @returns
 */
function showAllOrderFee(){
	var url = BASE_CONTEXT_PATH + REQUEST_MAPPING+"/showallfee"; //需要提交的 url
	
	//console.log("debug show order fee!");
	var parms=new Object(); //生成参数对象
	
	//分页数据
	/*parms.pageNum=$("#pageNum").val();
	parms.pageSize=$("#pageSize").val();*/
	
	//分页条件暂时无效
	parms.pageNum=0;
	parms.pageSize=0;
	
	
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
	
	//欠款状态
	var option1=$("#select-total-pay-flag option:selected");
	var totalPayFlag=option1.val();
	
	parms.totalPayFlag=totalPayFlag;
	
	$("#edit-body").load(url,parms, function(){		
		$("#edit-tab").removeClass("hide");
		$("#edit-tab-title").text("回款列表");
		$('#tabs-14933 a[href="#panel-602679"]').tab('show');
	});	
}

//------------------查询界面更新------------------------

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

function updateUISearchCondValue(condValue){
	$("#searchCond").val(condValue);
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

/**
 * 根据回传的值:置欠款状态下拉框当前选项
 * @returns
 */
function updateTotalPayFlag(){
	var totalPayFlag=ret_totalPayFlag;
	
	if(totalPayFlag==-1){
		$("#select-total-pay-flag option").eq(0).attr("selected",true);
	}
	else{
		$("#select-total-pay-flag option").each(function(){
			if($(this).val()==totalPayFlag){
				$(this).attr("selected",true);
				return;
			}
			
		});		
	}	
}

//----------------设置数据及数据准备--------------------

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

/*
 * 对查询条件进行有效性校验
 * 如果成功则返回 true; 否则返回false
 */
function validateSearchCond(){
	
	var condType=$("#ordertime-cond").val();  //查询条件类型
	var condStr=$("#searchCond").val();		  //查询条件
	
	//区域值
	var provinceName=$("#provinceName").val();
	var cityName=$("#cityName").val();
	var countyName=$("#countyName").val();
	
	//(1)无效的情况1
	//没有选择查询条件:未选择区域,未输入查询条件
	/*
	if(isBlank(condStr) && isBlank(provinceName) && isBlank(cityName) && isBlank(countyName)){
		return false;
	}
	
	if(condType==0 && !(isBlank(condStr))){
		return false;
	}*/
	
	return true;
	
}


//------------page loaded ready----------
$(function() {
	//-----------INITIALIZE------------
	resetDistPickerDeep();
	
	updateUIArea();
	updateUserRole();
	updateTotalPayFlag();
	
	updateUIDealState(g_dealstate_cond);
	updateUIOrderTime(g_ordertime_cond);
	updateUISearchCond(g_searchTypeValue);
	updateUISearchCondValue(g_condValue);
	
	//----------event binding--------------

	/*
	 * 订单列表中【回款】按钮:click event binding  显示此订单下的回款列表
	 */
	$(".show-order-fee").on("click", function(e) {
		
		showOrderFee(this);
		
		//如果此合同已经执行完毕后,不可以再录入四项回款		
		//TODO 是否加入此业务规则约束?
		//TODO 采有枚举类型对合同的状态进行描述.		
		/*if(!contractState==6){  //如果是未建合同状态
			//TODO
		}
		else{
			//util.message("此合同已经执行完毕,不可以再录入四项回款！");
		}*/
	});
	
	//回款列表(所有)
	$("#btn-all-fee-list").on("click",function(){
		showAllOrderFee();
	});
	
	
	/* 搜索按钮 -click */
	$(".start-search").on("click",function(){
		/*
		var condType=$("#search-cond").val();
		var condStr=$("#searchCond").val();
		
		
		 if(!validateSearchCond()){
			util.message("请选择查询类型或是输入查询条件!",null,"info");
			return;
		}
		else{
			search();
		}
		 
		
		if(condType==0 || $.isBlank(condStr)){
			return;
		}
		else{
			search_normal();
			
		}
		*/
		
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
		$(".start-search").trigger("click");
	});
	
	//当选择欠款状态时,自动查询
	$("#select-total-pay-flag").on("change",function(){
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
	
	
	//------------------分页（页码导航）------------------
	/*
	 * 【分页】导航： 当点击页号时读取需要导航到的页码及每页记录数（pageNum,pageSize）
	 * data-bind:pageNum-pageSize形式 
	 * 		如果data-bind为空字符串，则不做动作 
	 * 		当用户点击某页时，则发送与筛选相同的请求
	 */

	$(".pagination li a").on('click', function(e) {
		console.log($(this).attr("data-bind"));
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
	
	//--------------条件选择 下拉菜单显示--------------
	
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