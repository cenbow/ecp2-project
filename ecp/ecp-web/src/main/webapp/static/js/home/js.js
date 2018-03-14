/**
 * 
 * @authors Wang
 * @date 2017-6-16
 * @version
 */
//==============通用函数===================

/*用于判定是否为空*/
(function($){
	$.isBlank = function(obj){
	return(!obj || $.trim(obj) === "");
		  };
})(jQuery);

//==================动态加载购物车============

/* 加载购物车 */
function loadQuickCart() {
	var url = "/front/cart/quickcart";
	var parms = null;
	var callbackFunc = loadQuickCartSuccess; // 加载完毕后 显示数量及金额
	var containerId = "#popover-content";	
	loadPage(containerId, url, parms, callbackFunc);
}

/* 显示购物车商品数量及总金额 */
function loadQuickCartSuccess() {
	displayCartItemNum();
	displayCartItemTotalPrice();
	
}

/*显示购物车中商品数量*/
function displayCartItemNum(){
	$("#itemNum").text(g_cartItemNum);
}

/*显示购物车中总价格*/
function displayCartItemTotalPrice(){
	$("#cartItemTotalPrice").text(g_cartItemTotalPrice);
}

var Index ={

		switchProNav : function(that){
			$(that).parent(".floorGuide").children(".guideItem").removeClass("active");
			$(that).addClass("active");
		},

		//文字滚动
		autoScroll : function(top){
			var height = $("#scrollContent").height(),
				pos = $("#scrollContent").offset(),
				setTop = pos.top,
				isOver = false;

			var scroll = function(){
				$("#scrollContent").offset({top:setTop});
				setTop -= 1;
				if(setTop <= (pos.top - height)){
					setTop = pos.top + height;
				}
				if(!isOver){
					setTimeout(scroll, 50);
				}
			}
			scroll();

			$("#scrollContent").hover(function(){
				isOver = true;
			},function(){
				isOver = false;
				setTimeout(scroll, 50);
			});
		}
	};

/* page loaded ready */
$(function() {
	
	$("a").focus(function(){this.blur()});
	$("#homeBanner").carousel();  //播放幻灯片

	// 购物车
	$("#myCart").hover(
		function() { // hover in
			console.log("triggered,load cart items");
			//$(".dropdownCart").stop(true, true).slideDown(); // 下拉
			$("#myCart .cart").addClass("hover");
			$(".dropdownCart").show();
			loadQuickCart();
			
		}, 
		function() { // hover out
			//$(".dropdownCart").stop(true, false).delay(0).slideUp();
			$("#myCart .cart").removeClass("hover");
			$(".dropdownCart").hide();
		}
	);

	// 分类
	$("#sort .all-sort").hover(function() {
		$("#classification").slideDown();
	}, function() {
		$("#classification").stop(true, true).slideUp();
		$(".item-sub").hide();
	});

	$(".firstClass").each(function(index) {
		$(this).hover(function() {
			$(".item-sub").hide();
			$("#details").show();
			$("#classList" + index).show();
		}, function() {

		});
	});

	$("#classList1").hover(function() {
		$("#detail").show();
		$("#classList1").show();
	}, function() {
		$("#classList1").hide();
	});

	$(".footerContent .row>div").each(function() {
		$(this).height($(".footerContent .row").height())
	});
	
	Index.autoScroll(0);

	$(".company-img").on("click",function(){
		var text = $("#scrollContent").offset();
		text.top-=10;
		$("#scrollContent").offset({top:text.top});
		var text1 = $("#scrollContent").height();
		console.log(text.top + ","+text1);
	});
	
	
	//==============业务逻辑=============
	
	/* 打开购物车 */
	$("#btnCart").on("click",function(){
		var url = BASE_CONTEXT_PATH + "/front/personalcenter/cart";
		window.open(url,"_blank");  
	});
	
	/* 关键字查询按钮-click 处理*/
	$("#searchbutton").on("click",function(){
		var keywords=$("#searchbox").val();
		var form=$("#searchform");
		
		//当输入不空时提交表单
		if(!$.isBlank(keywords)){
			//alert("debug");
			form.submit();  
		}
		else{
			return false;
		}
	});
	

});




