//==============通用函数===================

/*用于判定是否为空*/
(function($){
	$.isBlank = function(obj){
	return(!obj || $.trim(obj) === "");
		  };
})(jQuery);


/*
	重新加载代理商页面
 */
function reloadPage() {
	//生成参数对象
	var parms=new Object();
	
	//分页数据
	parms.pageNum=$("#pageNum").val();
	parms.pageSize=$("#pageSize").val();
	
	//查询类型，查询条件值
	parms.searchTypeValue=g_searchTypeValue;
	parms.condValue=g_condValue;
	
	parms.provinceName=ret_provinceName;
	parms.cityName=ret_cityName;
	parms.countyName=ret_countyName;
	
	//用户/角色条件	
	parms.userId=ret_userId;
	parms.roleId=ret_roleId;
	
	//欠款状态
	parms.auditStatus=ret_auditStatus;
	
	loadUserAgent(parms,null); // 加载页面
}

/**
 * 根据用户的输入条件查询签约客户（包括分页数据）
 * @returns
 */
function search(){
	console.log("start search!");
	//生成参数对象
	var parms=new Object();
	
	//分页数据
	parms.pageNum=$("#pageNum").val();
	parms.pageSize=$("#pageSize").val();
	
	//查询类型，查询条件值
	var condType=$("#ordertime-cond").val();
	var condStr=$("#searchCond").val();	
	parms.searchTypeValue=condType;
	parms.condValue=condStr;
	
	setSearchParams(parms);
	
	console.log(parms);
	loadUserAgent(parms,null); // 加载页面
}


/**
 * 设置查询参数
 * @param parms
 * @returns
 */
function setSearchParams(parms){
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
	var option1=$("#select-audit-status option:selected");
	var auditStatus=option1.val();
	parms.auditStatus=auditStatus;
}

// ===================设置代理商:状态====================
function setAgentState(agentId,  state) {
	var url = BASE_CONTEXT_PATH+"/back/myagent/setstate"; // 需要提交的 url
	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		// dataType: "application/json",
		data : {
			'agentId' : agentId,			
			'state' : state
		},
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					util.message(obj.result_msg);
					reloadPage();
				} else {
					util.message(obj.result_msg);
				}
			}
		}

	});
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

//----------------------更新UI-----------------------
/**
 * 查询类型菜单更新
 * 根据回传的条件类型更新界面  
 * @param orderTimeCond  条件类型
 * @returns
 */
function updateUISearchCond(orderTimeCond){
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

function updateUISearchCondValue(condValue){
	$("#searchCond").val(condValue);
}

//function updateUISearchCond

/**
 * 根据回传的"区域"数据更新区域选择界面 
 * @returns
 */
function updateUIArea(){
	console.log("ret_provinceName:"+ret_provinceName);
	
	$("#distpicker-search #province").val(ret_provinceName);
	$("#distpicker-search #province").trigger("change");
	
	$("#distpicker-search #city").val(ret_cityName);
	$("#distpicker-search #city").trigger("change");
	
	$("#distpicker-search #district").val(ret_countyName);
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
 * 根据回传的值:置审核状态下拉框当前选项
 * @returns
 */
function updateAuditStatus(){
	var auditStatus=ret_auditStatus;
	
	if(auditStatus==0){
		$("#select-audit-status option").eq(0).attr("selected",true);
	}
	else{
		$("#select-audit-status option").each(function(){
			if($(this).val()==auditStatus){
				$(this).attr("selected",true);
				return;
			}
			
		});		
	}	
}

//--------------------区域操作-----------------------
/**
 * 复位地区列表
 * @returns
 */
function resetDistPickerDeep_search(){
	 var distpicker = $('#distpicker-search');
	 distpicker.distpicker('reset', true);
}

/**
 * 同步更新地区(省,市,县)编码、名称
 * @returns
 */
function syncUpdateHiddenAreaCode_search(distPickerId){
	
	//省级名称及编码
	//console.log("debug");
	var code= $(distPickerId+" #province").find("option:selected").attr("data-code");  //省级编码、名称
	var name= $(distPickerId+" #province").find("option:selected").val();
	//console.log("name:"+name);
	$(distPickerId+" #provinceCode").val(code);
	$(distPickerId+" #provinceName").val(name);	
	//console.log("debug end");
	
	
	//市级名称及编码
	code= $(distPickerId+" #city").find("option:selected").attr("data-code");  //市级编码、名称
	name= $(distPickerId+" #city").find("option:selected").val();
	$(distPickerId+" #cityCode").val(code);
	$(distPickerId+" #cityName").val(name);
	
	
	//区县级名称及编码
	code= $(distPickerId+" #district").find("option:selected").attr("data-code");  //县区级编码、名称
	name= $(distPickerId+" #district").find("option:selected").val();
	console.log("countyName:"+name);
	$(distPickerId+" #countyCode").val(code);
	$(distPickerId+" #countyName").val(name);
}

//==================PAGE LOADED READY===================
$(function() {
	resetDistPickerDeep_search();
	
	updateUIArea();
	updateUserRole();
	updateAuditStatus();
	
	updateUISearchCond(g_searchTypeValue);
	//updateUISearchCondValue(g_condValue);
	
	// ===================代理商:帐号管理======================
	/**
	 * 导航到代理商帐号列表界面.
	 * 传递的参数主要有:代理商ID,代理商名称
	 * 帐户管理,加载代理商帐户列表. account-management
	 */
	$(".account-management").on(
			"click",
			function(e) {
				var agentId = $(this).attr("data-bind");
				var companyName = $(this).attr("data-company-name");
				
				/*
				var contactPhone = $(this).attr("data-contact-phone");
				var artificialPersonName = $(this).attr("data-artificialPersonName");				
				var userId=$(this).attr("data-user-id");*/
				
				$("#companyName").val(companyName);  //传递当前公司名称(agent_usertable_show.html)
				
				
				//加载代理商用户列表.
				var url = BASE_CONTEXT_PATH + "/back/myagent/showusertable?agentId="+agentId;
				$("#edit-body").load(url, function(){
					//在新的选项卡中显示代理商用户列表.
					$("#edit-tab").removeClass("hide");
					$("#edit-tab-title").text("签约客户"+"帐号列表");
					$('#tabs-14933 a[href="#panel-602679"]').tab('show');	
					
				});
				
	});
	
	// ===================设置签约代理商状态:有效、无效===============
	/* 设置帐号为有效 */
	$(".set-valid").on("click", function(e) {
		var agentId = $(this).attr("data-bind");
		var accountState = $(this).attr("data-account-state");
		
		if (accountState == 1) {
			util.message("此帐号己为有效！");
		} else {
			setAgentState(agentId, 1);
		}
		

	});

	/* 设置帐号为无效 */
	$(".set-invalid").on("click", function(e) {
		var agentId = $(this).attr("data-bind");
		var accountState = $(this).attr("data-account-state");
		if (accountState == 2) {
			util.message("此帐号己为无效！");
		} else {
			setAgentState(agentId,2);
		}

	});
	
	
	
	//================条件选择 与查询===================
	//订单时间条件(下拉菜单)
	$(".ordertime-cont").hover(
			function() {
				$(".time-list").show();			
			}, 
			function() {
				$(".time-list").hide();		
			}
	);
	
	
	//选择查询类型（下拉菜单）
	$(".time-list li").on("click",function(){
		var selectedTxt=$(this).find('a').text();
		var value=$(this).val();
		setOrderTimeCond(selectedTxt,value);
		updateUISearchCond(value);  //更新页面
		if(value==0){
			search();
		}
		
		
		//$(this).blur(null);
	});
	
	$(".start-search").on("click",function(){
		/*var condType=$("#ordertime-cond").val();
		var condStr=$("#searchCond").val();
		
		if(condType==0 || $.isBlank(condStr)){
			return;
		}
		else{
			search();
		}*/
		console.log("test");
		search();
		
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
	$("#select-audit-status").on("change",function(){
		$(".start-search").trigger("click");
	});
	
	
	//--------------地区选择---------------
	/*
	 * 省份:change  当“省份”变化时，将code赋值给value; 
	 */
	$("#distpicker-search #province").on("change",function(){syncUpdateHiddenAreaCode_search("#distpicker-search");});
	
	/*
	 *当“市级”变化时，将ode赋值给value; 
	 */
	$("#distpicker-search #city").on("change",function(){syncUpdateHiddenAreaCode_search("#distpicker-search");});
	
	/*
	 *当“县级”变化时，将code赋值给value; 
	 */
	$("#distpicker-search #district").on("change",function(){syncUpdateHiddenAreaCode_search("#distpicker-search");});
	
	// ====================翻页=====================
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
			reloadPage();  // 发送请求
		}

	});
	

});   //end of page loaded