	//-------------------TO SERVER-----------------------
	/*
	 * 绑定签约代理商与OS/IS  (传递方式1)
	 * @RequestMapping(value = "/bindsales")
		@ResponseBody
		public Object BindSalesToAgent(@RequestBody string parms, Model model)
	 */
	function bindAgentAndSales(agentId,saleIdList) {
		var urlStr = BASE_CONTEXT_PATH + "/back/agent-bind/binduser"; // 需要提交的url
																		
		var params=new Object();  //生成参数对象.
		
		params.agentId=agentId;
		params.saleIdList=saleIdList;
		

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
						util.message(obj.result_msg);
						var urlTemp = obj.result_msg;						
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
	
	/*
	 * 绑定签约代理商与OS/IS 提交参数方式二(经典模式) 备用 @RequestMapping(value = "/bindsales")
	 * @ResponseBody public Object BindSalesToAgent(long agentId,String
	 * saleIdList, Model model)
	 */
	function bindAgentAndSales1(agentId,saleIdList) {
		var urlStr = BASE_CONTEXT_PATH + "/back/agent-bind/bindsales1"; // 需要提交的 url
																		
		var params=new Object();
		
		params.agentId=agentId;
		params.saleIdList=saleIdList;
		

		$.ajax({
			type : "POST", // 提交方式 get/post
			url : urlStr,
			// contentType : "application/json", //
			// saleIdList参数以JSON字符串方式传递.此时此处需要屏蔽.
			// dataType : "html", //表示返回值类型，不必须,如果返回的是面页，则必须
			data : {
				"agentId":agentId,
				"saleIdList":JSON.stringify(saleIdList)
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
				/* 弹出jqXHR对象的信息 */
				util.message("AJAX请求时发生错误!");
				/*alert(jqXHR.responseText);
				alert(jqXHR.status);
				alert(jqXHR.readyState);
				alert(jqXHR.statusText);*/
				/* 弹出其他两个参数的信息 */
				/*alert(textStatus);
				alert(errorThrown);*/

			}
		});
	}
	
	// ----------------click handler------------------------
	
	// 外部销售列表项选择框:click handler
	// TODO
	function osItem_Check_Click_handler(e){
		console.log($(e).attr("bind-id"));
		$("#oslist").find('.check-os').not(e).prop("checked", false);
	}
	
	// 内部销售列表项选择框:click handler
	// TODO
	function isItem_Check_Click_handler(e){
		//console.log($(e).attr("bind-id"));
		$("#islist").find('.check-is').not(e).prop("checked", false);	
		
	}
	
	// -----------------data prepare-------------------------
	/*
	 * 获取已经选定 外部销售人员 列表ID 返回array类型.其中的每个元素为一个选定的USERID
	 */
	function getChoicedOS(){
		var idArr=new Array();
		$(".check-os").each(function(index){
			var userId=$(this).attr("bind-id");
			var checkStatus=$(this).is(':checked');
			if(checkStatus==true){
				idArr.push(userId);				
				}			
			});
		
		return idArr;
	}
	
	/*
	 * 获取已经选定 内部销售人员 列表ID 返回array类型.其中的每个元素为一个选定的USERID
	 */
	function getChoicedIS(){
		var idArr=new Array();
		$(".check-is").each(function(index){
			var userId=$(this).attr("bind-id");
			var checkStatus=$(this).is(':checked');
			if(checkStatus==true){
				idArr.push(userId);				
				}			
			});
		return idArr;
	}
	
	
	//----------------- page loaded ready------------------
	$(function(){
		console.log("debug!");
		// 绑定按钮:click event process bind
		$("#btn-bind").on('click',function(e){
			var agentId=$("#curr-agent").val();
			
			var userArr=new Array();
			var idArr=null;
			
			idArr=getChoicedOS();
			for(i=0;i<idArr.length;i++){
				userArr.push(idArr[i]);
			}
			
			idArr=getChoicedIS();
			for(i=0;i<idArr.length;i++){
				userArr.push(idArr[i]);
			}		
		
			if(idArr.length==0){
				util.message("请先选择外部销售与内部销售!");
				return;
			}
			bindAgentAndSales(agentId,userArr);
			
		});
		
		/*
		 * $("#btn-bind1").on('click',function(e){ var arr=new Array();
		 * arr.push(1); arr.push(2); bindAgentAndSales1(1,arr); });
		 */
		
		
		
		// 事件绑定,所传送的参数必须为this.
		$('.check-os').on('click',function(){osItem_Check_Click_handler(this);});
		$('.check-is').on('click',function(){isItem_Check_Click_handler(this);});
		
		
	});