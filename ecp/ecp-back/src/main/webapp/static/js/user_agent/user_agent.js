//==============通用函数===================

/*用于判定是否为空*/
(function($){
	$.isBlank = function(obj){
	return(!obj || $.trim(obj) === "");
		  };
})(jQuery);


/*
	重新加载页面
 */
function reloadPage() {
	//生成参数对象
	parms=new Object();
	//分页数据
	parms.pageNum=$("#pageNum").val();
	parms.pageSize=$("#pageSize").val();
	//查询类型，查询条件值
	parms.searchTypeValue=g_searchTypeValue;
	parms.condValue=g_condValue;
	
	loadUserAgent(parms,null); // 加载页面
}

/**
 * 根据用户的输入条件查询签约客户（包括分页数据）
 * @returns
 */
function search(){
	//生成参数对象
	parms=new Object();
	//分页数据
	parms.pageNum=$("#pageNum").val();
	parms.pageSize=$("#pageSize").val();
	//查询类型，查询条件值
	var condType=$("#ordertime-cond").val();
	var condStr=$("#searchCond").val();
	
	parms.searchTypeValue=condType;
	parms.condValue=condStr;
	
	loadUserAgent(parms,null); // 加载页面
}




// ===================设置帐号状态====================
function setAccountState(agentId, userId, accountState) {
	var url = BASE_CONTEXT_PATH + "/back/agent/setstate"; // 需要提交的 url
	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		// dataType: "application/json",
		data : {
			'agentId' : agentId,
			'userId' : userId,
			'accountState' : accountState
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





//==================PAGE LOADED READY===================
$(function() {
	updateUIOrderTime(g_searchTypeValue);
	
	
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
				var url = BASE_CONTEXT_PATH + "/back/agent/showusertable?agentId="+agentId;
				$("#edit-body").load(url, function(){
					//在新的选项卡中显示代理商用户列表.
					$("#edit-tab").removeClass("hide");
					$("#edit-tab-title").text("签约客户"+"帐号列表");
					$('#tabs-14933 a[href="#panel-602679"]').tab('show');	
					
				});
				
	});
	
	// ===================设置帐号状态（有效、无效）======================
	/* 设置帐号为有效 */
	$(".set-valid").on("click", function(e) {
		var agentId = $(this).attr("data-bind");
		var userId = $(this).attr("data-user-id");
		var accountState = $(this).attr("data-account-state");
		if (userId == 0) {
			util.message("尚未分配帐号！");
		} else {
			if (accountState == 1) {
				util.message("此帐号己为有效！");
			} else {
				setAccountState(agentId, userId, 1);
			}
		}

	});

	/* 设置帐号为无效 */
	$(".set-invalid").on("click", function(e) {
		var agentId = $(this).attr("data-bind");
		var userId = $(this).attr("data-user-id");
		var accountState = $(this).attr("data-account-state");
		if (userId == 0) {
			util.message("尚未分配帐号！");
		} else {
			if (accountState == 2) {
				util.message("此帐号己为无效！");
			} else {
				setAccountState(agentId, userId, 2);
			}
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
	
	
	//选择时间条件（下拉菜单）
	$(".time-list li").on("click",function(){
		var selectedTxt=$(this).find('a').text();
		var value=$(this).val();
		setOrderTimeCond(selectedTxt,value);
		updateUIOrderTime(value);  //更新页面
		if(value==0){
			search();
		}
		
		
		//$(this).blur(null);
	});
	
	$(".start-search").on("click",function(){
		var condType=$("#ordertime-cond").val();
		var condStr=$("#searchCond").val();
		
		if(condType==0 || $.isBlank(condStr)){
			return;
		}
		else{
			search();
		}
		
	});
	
	/*当在条件输入框按下enter时*/
	$("#searchCond").on("keydown",function(event){
		if(event.keyCode==13){
			$(".start-search").trigger("click");
			
		}
	});
	
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