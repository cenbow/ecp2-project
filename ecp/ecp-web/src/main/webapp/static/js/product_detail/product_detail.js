	var BASE_CONTEXT_PATH = $('meta[name=context-path]').attr("content");
		BASE_CONTEXT_PATH = BASE_CONTEXT_PATH.substr(0,
				BASE_CONTEXT_PATH.length - 1);
		
		//==============通用函数===================

		/*用于判定是否为空*/
		(function($){
			$.isBlank = function(obj){
			return(!obj || $.trim(obj) === "");
				  };
		})(jQuery);

		/*
			在加入购物车前
			查询用户登录状态  （采用ajax方式）
			
			return： 已经登录：true
					未登录：false
		 */
		function getLoginState() {
			var logined = false;
			var urlStr = BASE_CONTEXT_PATH + "/login/agent/loginstate"; // 需要提交的 url
			//alert("url is:"+urlStr);
			$.ajax({
				type : "post", // 提交方式 get/post
				url : urlStr,
				// dataType: "json",
				data : {},
				success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
					console.log(res);
					if (res != null) {
						var obj = $.parseJSON(res);
						//alert(obj.message=="login");
						if (obj.message == "login") {
							logined = true;
							addtoCart(); //如果已经登录，则直接加入购物车
						} else {
							displayLoginWindow(); //在此处显示窗口
						}
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					/*弹出jqXHR对象的信息*/
					alert(jqXHR.responseText);
					alert(jqXHR.status);
					alert(jqXHR.readyState);
					alert(jqXHR.statusText);
					/*弹出其他两个参数的信息*/
					alert(textStatus);
					alert(errorThrown);

				}
			});

			return logined;
		}

		/*
			查询用户登录状态  （采用ajax方式）
			return： 已经登录：true
					未登录：false
		 */
		function getLoginState_favourite() {
			var logined = false;

			var urlStr = BASE_CONTEXT_PATH + "/login/agent/loginstate"; // 需要提交的 url
			//alert("url is:"+urlStr);
			$.ajax({
				type : "post", // 提交方式 get/post
				url : urlStr,
				// dataType: "json",
				data : {},
				success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
					console.log(res);
					if (res != null) {
						var obj = $.parseJSON(res);
						//alert(obj.message=="login");
						if (obj.message == "login") {
							logined = true;
							addtoFavourite(); //如果已经登录，则直接加入我的收藏
						} else {
							displayLoginWindow(); //在此处显示窗口
						}
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					/*弹出jqXHR对象的信息*/
					alert(jqXHR.responseText);
					alert(jqXHR.status);
					alert(jqXHR.readyState);
					alert(jqXHR.statusText);
					/*弹出其他两个参数的信息*/
					alert(textStatus);
					alert(errorThrown);

				}
			});

			return logined;
		}

		/*
			加入购物车
		 */
		function addtoCart() {
			var amount = $("#amount").val(); //取得输入的数量
			var skuName = $("#itemName").text(); //获取sku名称

			$("#skuName").val(skuName);
			$("#quantity").val(amount);

			$("#addToCartForm").submit(); //提交 
		}

		/*
		 	功能：加关注
		 	
		 	AJAX提交请求
		 */
		function addtoFavourite() {
			var logined = false;
			var urlStr = BASE_CONTEXT_PATH + "/front/favourite/add"; // 需要提交的 url
			var itemId = $("#myFavourite").attr("data-id");
			//alert("url is:"+urlStr);
			$.ajax({
				type : "post", // 提交方式 get/post
				url : urlStr,
				// dataType: "json",
				data : {
					favouriteId : itemId
				},
				success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
					console.log(res);
					if (res != null) {
						var obj = $.parseJSON(res);
						//alert("message:"+obj.message);
						if (obj.message == "added") {
							util.message("成功加入收藏");
						} else {
							//displayLoginWindow(); //在此处显示窗口
							util.message("加入收藏时发生错误！");
						}
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					/*弹出jqXHR对象的信息*/
					alert(jqXHR.responseText);
					alert(jqXHR.status);
					alert(jqXHR.readyState);
					alert(jqXHR.statusText);
					/*弹出其他两个参数的信息*/
					alert(textStatus);
					alert(errorThrown);

				}
			});
		}
		
		

		/*
			显示登录窗口
		 */
		function displayLoginWindow() {
			//alert("在此处显示窗口");
			$("#modal-273078").click();
		}

		/*
			设置当前sku 属性值 为已选标记
		 */
		function setCurrSkuAttrFlag() {
			var attribute = $("#SkuAttribute").val();
			var attrArr = attribute.split(",");
			for (var i = 0; i < attrArr.length; i++) {
				//alert(attrArr[i]);
				$(".attrValue").each(function(index, val) {
					/* console.log(index, val, this);
					if (index === 1) {
						return false;
					} */

					if (attrArr[i] == $(this).attr("data-bind")) {
						$(this).addClass("selected");
						return true;
					}
				});
			}
		}

		/*
			根据item名称及销售属性修改sku的名称
			将item的名称与销售属性值组合起来
		 */
		function dispSkuName() {

			var attrPart = ""
			$(".attrValue.selected").each(function(index, val) {
				//alert("index"+index);
				if (index == 0) {
					attrPart = attrPart + $(this).text();
				} else {
					attrPart = attrPart + "|" + $(this).text();
				}

			}); 

			var itemName = $("#itemName").text();
			
			var skuName ="";  
			if(!$.isBlank(attrPart))  //如果有选定的sku时,则显示相应SKU名称(商品名称+销售属性值)
				skuName=itemName + "(" + attrPart + ")";
			else
				skuName=itemName;
			
			$("#itemName").text(skuName); //在界面显示sku 名称
		}

		/*
			根据 attribute 查询  sku
		 */
		function search_sku_normal(attrVal) {
			$("#skuAttribute").val(attrVal);
			$("#testform").submit();
		}

		/*
			关闭登录窗口
		 */
		function closeLoginWindow() {
			$("#btnClose").click();
		}

		$(function() {

			setCurrSkuAttrFlag(); //选定当前销售属性值
			dispSkuName(); //显示sku名称

			/*
				选择SKU属性时---提交
			 */
			$(".attrValue").on('click', function(e) {
				var seleAttrValue = $(this).attr("data-bind");
				var oldAttrValue = $("#skuAttribute").val();
				if (oldAttrValue.indexOf(seleAttrValue) >= 0) {

				} else {
					var attrArr = seleAttrValue.split(":");
					var pattern = attrArr[0] + ':' + '\\d+';
					var reg = new RegExp(pattern, "g"); //采用正则进行替换
					var ret = oldAttrValue.replace(reg, seleAttrValue);
					search_sku_normal(ret);
				}

			});

			/*
				用户登录(AJAX方式)
			 */
			$("#btnLogin").on("click", function(e) {
				var urlStr = BASE_CONTEXT_PATH + "/login/agent/ajaxLogin"; // 需要提交的 url

				var loginName = $('#loginName').val();
				var loginPass = $('#password').val();

				//alert("start login");
				//ajax登录
				$.ajax({
					type : "post", // 提交方式 get/post
					url : urlStr, // 需要提交的 url
					// dataType: "json",
					data : {
						'loginName' : loginName,
						'password' : loginPass
					},
					success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
						console.log(res);
						if (res != null) {
							var obj = $.parseJSON(res);
							if (obj.status == "success") {
								if (obj.message == "login") {
									closeLoginWindow();
								} else {
									util.message("用户名或口令错误");
								}

							} else {
								//show_err_msg(obj.result_err_msg);
								util.message("用户登录错误!");
							}
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
						/*弹出jqXHR对象的信息*/
						alert(jqXHR.responseText);
						alert(jqXHR.status);
						alert(jqXHR.readyState);
						alert(jqXHR.statusText);
						/*弹出其他两个参数的信息*/
						alert(textStatus);
						alert(errorThrown);
					}
				});

			});

			/*
				增加到购物车按钮---提交
			 */
			$("#cartAdd").on('click', function(e) {
				getLoginState();
			});

			/*
				增加到 我的收藏---提交				
			 */
			$("#myFavourite").on('click', function(e) {
				getLoginState_favourite();
			});

		});