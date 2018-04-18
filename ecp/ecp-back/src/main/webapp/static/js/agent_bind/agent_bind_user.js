	//-------------------TO SERVER-----------------------
	/*
	 * 绑定签约代理商与OS/IS  (传递方式1)
	 * @RequestMapping(value = "/bindsales")
		@ResponseBody
		public Object BindSalesToAgent(@RequestBody string parms, Model model)
	 */
	function bindAgentAndSales(agentId,userList) {
		var urlStr = BASE_CONTEXT_PATH + "/back/agent-bind/binduser"; // 需要提交的url
																		
		var params=new Object();  //生成参数对象.
		
		params.agentId=agentId;
		params.userList=userList;
		

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
						//var urlTemp = obj.result_msg;
						showAgentBindedUser();  //重新加载所绑定的客户信息.
						reloadPage(); // 重新加载页面
						
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
	
	/**
	 * 解除绑定:单个代理商
	 * @param that
	 * @returns
	 */
	function agentUnbind(that){
		var relId=$(that).attr("data-bind-relid");
		//显示确认对话框.
		util.delConfirm("删除此绑定?",relId,"agentUnbindRequest");
	}

	function agentUnbindRequest(relId){
		var urlStr = BASE_CONTEXT_PATH + "/back/agent-bind/unbind"; // 需要提交的url

		$.ajax({
			type : "POST", // 提交方式 get/post
			url : urlStr,
			// contentType : "application/json", //
			// 如果采用json格式传送所有参数时必须有,如果采普通js对象传送时,则不可以加此参数
			// dataType : "html", //表示返回值类型，不必须,如果返回的是面页，则必须
			data : {"relId":relId},
			success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
				// console.log(res);
				if (res != null && res != "") {
					var obj = $.parseJSON(res);
					if (obj.result_code == "success") {
						showAgentBindedUser();  //重新加载所绑定的客户信息.
						reloadPage(); // 重新加载页面
						
					} else {
						util.message(obj.result_msg);
					}
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				util.message("AJAX请求时发生错误!");
				弹出jqXHR对象的信息
				console.log(jqXHR.responseText);
				console.log(jqXHR.status);
				console.log(jqXHR.readyState);
				console.log(jqXHR.statusText);
				弹出其他两个参数的信息
				console.log(textStatus);
				console.log(errorThrown);

			}
		});
	}
	
	/*
	 * 此函数只用于测试
	 * 绑定签约代理商与OS/IS 提交参数方式二(经典模式) 备用 @RequestMapping(value = "/bindsales")
	 * @ResponseBody public Object BindSalesToAgent(long agentId,String saleIdList, Model model)
	 */
	/*function bindAgentAndSales1(agentId,bindedUserList) {
		var urlStr = BASE_CONTEXT_PATH + "/back/agent-bind/bindsales1"; // 需要提交的 url
																		
		var params=new Object();
		
		params.agentId=agentId;
		params.saleIdList=bindedUserList;
		

		$.ajax({
			type : "POST", // 提交方式 get/post
			url : urlStr,
			// contentType : "application/json", //
			// saleIdList参数以JSON字符串方式传递.此时此处需要屏蔽.
			// dataType : "html", //表示返回值类型，不必须,如果返回的是面页，则必须
			data : {
				"agentId":agentId,
				"bindedUserList":JSON.stringify(bindedUserList)
			},
			success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
				console.log(res);
				if (res != null && res != "") {
					var obj = $.parseJSON(res);
					if (obj.result_code == "success") {
						// util.message(obj.result_msg);
						var urlTemp = obj.result_msg;						
					} else {
						util.message(obj.result_msg);
					}
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				util.message("AJAX请求时发生错误!");
				 弹出jqXHR对象的信息 
				console.log(jqXHR.responseText);
				console.log(jqXHR.status);
				console.log(jqXHR.readyState);
				console.log(jqXHR.statusText);
				 弹出其他两个参数的信息 
				console.log(textStatus);
				console.log(errorThrown);

			}
		});
	}*/
	
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
	
	
	//--------------------load page------------------------
	function loadAgentBindedUser(agentId){
		var url = BASE_CONTEXT_PATH + "/back/agent-bind/showbindedusers?agentId="+agentId;		
		$("#binded-users").load(url,function(){			
		});		
	}
	
	function showAgentBindedUser(){
		var agentId=getCurrAgentId();
		loadAgentBindedUser(agentId);
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
		$("#btn-bind").on('click',function(e){
			
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
			bindAgentAndSales(agentId,userArr);
			
		});
		
		
		// 事件绑定,所传送的参数必须为this.
		$('.check-os').on('click',function(){osItem_Check_Click_handler(this);});
		$('.check-is').on('click',function(){isItem_Check_Click_handler(this);});
		
		showAgentBindedUser();  //显示代理商所绑定的用户列表
		
		
	});