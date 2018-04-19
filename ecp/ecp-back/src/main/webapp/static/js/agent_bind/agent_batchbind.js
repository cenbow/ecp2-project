	/**
	 * 重置选项卡
	 */
	function resetTab(){
		$('#tabs-14933 a[href="#panel-150383"]').tab('show');
		$("#edit-body").empty();
		$("#edit-tab").addClass("hide");
	}

	//-------------------TO SERVER-----------------------
	/*
	 * 绑定签约代理商与OS/IS  (传递方式1)
	 * @RequestMapping(value = "/bindsales")
		@ResponseBody
		public Object BindSalesToAgent(@RequestBody string parms, Model model)
	 */
	function batchBindAgentAndSales(searchCond,userList) {
		var urlStr = BASE_CONTEXT_PATH + "/back/agent-bind/batchbind"; // 需要提交的url
																		
		var params=new Object();  //生成参数对象.
		
		params.userList=userList;  //所选择定的OS及IS
		params.searchCond=searchCond;  //绑定企业的查询条件
		

		$.ajax({
			type : "POST", // 提交方式 get/post
			url : urlStr,
			contentType : "application/json", // 必须有,如果采用json格式传送参数时
			// dataType : "html", //表示返回值类型，不必须,如果返回的是面页，则必须
			data : JSON.stringify(params),
			success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
				console.log(res);
				if (res != null && res != "") {
					var obj = $.parseJSON(res);
					if (obj.result_code == "success") {
						//util.message(obj.result_msg);
						resetTab();
						reloadPage();  //重新加载页面.
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
	
	
	
	// ----------------click handler------------------------
	
	// 外部销售列表项选择框:click handler
	// TODO
	function osItem_Check_Click_handler(e){
		//console.log($(e).attr("bind-id"));
		$("#oslist").find('.check-os').not(e).prop("checked", false);
	}
	
	// 内部销售列表项选择框:click handler
	// TODO
	function isItem_Check_Click_handler(e){
		//console.log($(e).attr("bind-id"));
		$("#islist").find('.check-is').not(e).prop("checked", false);	
		
	}
	
	//-----------------------AUX function--------------------
	function getCurrAgentId(){
		var agentId=$("#curr-agent").val();
		return agentId;
	}
	
	// -----------------data prepare-------------------------
	
	/**
	 * 获取已经选定人员列表
	 * @param selector  选择器;   .check-os   .check-is
	 * @returns Array of User
	 * 		User:
	 * 			userId
	 * 			roleId
	 */
	function getChoicedUser(selector){
		var userArr=new Array();
		$(selector).each(function(index){
			
			var userId=$(this).attr("bind-id");
			var roleId=$(this).attr("role-id");
			
			var checkStatus=$(this).is(':checked');
			if(checkStatus==true){
				var user=new Object();
				user.userId=userId;
				user.roleId=roleId;
				userArr.push(user);				
				}			
			});
		
		return userArr;
	}
	
	
	//----------------- page loaded ready------------------
	$(function(){
		
		// 绑定按钮:click event process bind
		$("#btn-batchbind").on('click',function(e){
			
			//准备绑定的用户
			var CLASS_NAME_CHECK_OS=".check-os";
			var CLASS_NAME_CHECK_IS=".check-is";	
			
			
			var agentId=getCurrAgentId();
			
			var userArr=new Array();
			var tempArr=null;
			
			//获取已经选定的外部销售人员列表(userId,roleId)
			tempArr=getChoicedUser(CLASS_NAME_CHECK_OS);
			for(i=0;i<tempArr.length;i++){
				userArr.push(tempArr[i]);
			}
			
			//获取已经选定的内部销售人员列表(userId,roleId)
			tempArr=getChoicedUser(CLASS_NAME_CHECK_IS);
			for(i=0;i<tempArr.length;i++){
				userArr.push(tempArr[i]);
			}		
			if(userArr.length==0){
				util.message("请先选择外部销售与内部销售!");
				return;
			}
			
			//准备代理商查询范围
			var searchParms = new Object();			
			// 查询类型，查询条件值
			searchParms.searchTypeValue = g_searchTypeValue;
			searchParms.condValue = g_condValue;

			// 区域条件
			searchParms.provinceName = ret_provinceName;
			searchParms.cityName = ret_cityName;
			searchParms.countyName = ret_countyName;
			
			batchBindAgentAndSales(searchParms,userArr);
			
		});
		
		
		// 事件绑定,所传送的参数必须为this.
		$('.check-os').on('click',function(){osItem_Check_Click_handler(this);});
		$('.check-is').on('click',function(){isItem_Check_Click_handler(this);});
		
		//showAgentBindedUser();  //显示代理商所绑定的用户列表
		
		
	});