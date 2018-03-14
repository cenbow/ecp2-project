/**
 * 加入购物车(ajax)
 * @param itemId  item's id
 * @returns
 */
function addIntoCart(itemId){
	var url = BASE_CONTEXT_PATH + "/front/cart/favouritetocart"; // 需要提交的 url

	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		// dataType: "application/json",
		data : {
			'itemId':itemId			
		},
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					util.message("成功加入到购物车！");
					//util.message(obj.result_msg); //显示加入对话框，此处省略
					//loadCart(); // 操作成功后重新加载购物车列表，					
				} else {
					util.message(obj.result_err_msg);
				}
			}
		}

	});
}

/**
 * 批量加入购物车
 * @param itemIds  关注商品（itemid）列表
 * @returns
 */
function addIntoCartBatch(itemIds){
	var url = BASE_CONTEXT_PATH + "/front/cart/favouritetocartbatch"; // 需要提交的 url

	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		contentType : "application/json", // 必须有
		// dataType: "application/json",
		data : JSON.stringify(itemIds),
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					util.message("成功加入到购物车！");
					//util.message(obj.result_msg); //显示加入对话框，此处省略
					//loadFavourite(); // 操作成功后重新加载页面					
				} else {
					util.message(obj.result_err_msg);
				}
			}
		}

	});
}


/**
 * 批量取消关注
 * @param favouriteId
 * @returns
 */
function cancelFavouriteBatch(favouriteIds){
	var url = BASE_CONTEXT_PATH + "/front/favourite/cancelfavouritebatch"; // 需要提交的 url
	console.log("debug cancel batch");
	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		contentType : "application/json", // 必须有
		// dataType: "application/json",
		data : JSON.stringify(favouriteIds),
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					//util.message("成功加入到购物车！");
					//util.message(obj.result_msg); //显示加入对话框，此处省略
					loadFavourite(); // 操作成功后重新加载页面					
				} else {
					util.message(obj.result_err_msg);
				}
			}
		}

	});
}

/**
 * 功能：获取批量加购物车各条目商品ID（spuid）
 * @returns  spuid array
 */
function getSelectedItemIds(){
	var spuIdArr = new Array();
	
	// 扫描用户己选SKU
	$(".favouriteSelector").each(function(index, elem) {
		if (this.checked) { // 对于己选商品
			var spuId= $(this).attr("data-item-id");
			spuIdArr.push(spuId);
		}

	}); // end of 
	
	return spuIdArr;
}

/**
 * 功能：获取批量取消关注各条目ID（favourite id）
 * @returns  favourite'id array
 */
function getSelectedFavouriteIds(){
	var favIdArr = new Array();

	// 扫描用户己选SKU
	$(".favouriteSelector").each(function(index, elem) {
		if (this.checked) { // 对于己选商品
			var favId= $(this).attr("data-id");
			console.log(favId);
			favIdArr.push(favId);
		}

	}); // end of .favouriteSelector
	
	return favIdArr;
}



/* page loaded ready*/
$(function() {

	/* 【全选】按钮 -click */
	$("#selectAll").on('click', function(e) {
		if (this.checked) {
			$(".favouriteSelector").each(function(index, elem) {
				this.checked = true;
			});
		} else {
			$(".favouriteSelector").each(function(index, elem) {
				this.checked = false;
			});
		} // end of else
	});
	
	
	
	/*【加入购物车】-click*/
	$(".addIntoCart").on('click',function(e){
		var itemId=$(this).attr("data-item-id");
		console.log("itemId:"+itemId);
		addIntoCart(itemId);
	});
	
	/*【批量加入购物车】-click*/
	$("#addIntoCartBatch").on('click',function(e){
		var spuIdArr=getSelectedItemIds();
		if(spuIdArr.length>0){
			console.log("add into cart batch!");	
			addIntoCartBatch(spuIdArr);
		}	
		
	});
	
	/*【批量取消关注】-click*/
	$("#cancelFavouriteBatch").on('click',function(e){
		
		var favIdArr=getSelectedFavouriteIds();
		if(favIdArr.length>0){
			cancelFavouriteBatch(favIdArr);
		}
	});
	

});