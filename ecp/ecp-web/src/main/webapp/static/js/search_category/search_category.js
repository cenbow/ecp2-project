var BASE_CONTEXT_PATH = $('meta[name=context-path]').attr("content");
BASE_CONTEXT_PATH = BASE_CONTEXT_PATH.substr(0, BASE_CONTEXT_PATH.length - 1);

/*
 * function search_ajax() { $.ajax({
 * 
 * url : BASE_CONTEXT_PATH + '/front/search/categorycond', type : 'post',
 * contentType : 'application/json', // 这句不加出现415错误:Unsupported Media Type data :
 * JSON.stringify(g_params), // 以json字符串方式传递 success : function(data) {
 * console.log("success..."); }, error : function(data) {
 * console.log("error..."); } }); }
 */

Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val)
			return i;
	}
	return -1;
};

Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};

/* 置页号为0 */
function resetPageInfo() {
	$("#pageNum").val('0');
	$("#pageSize").val('0');
}

/* 置查询条件值 */
function setSearchCond(attrs, brands) {
	// alert("attrs length:"+attrs.length);
	// alert("brands length:"+brands.length);
	// 置隐藏表单中的值
	$("#attrCond").val(attrs.join(","));
	$("#brandCond").val(brands.join(","));
}

/*
 * 显示查询条件 (bread crumb)
 */
function displaySearchCond_crumb(searchCond) {
	var condItemArr = g_searchCondArr;
	// alert(g_searchCondArr.length);
	for (var i = 0; i < condItemArr.length; i++) {

		var condName = "";
		var condVal = "";
		var condArr = condItemArr[i].split(":");

		var dataBind = "";
		if (condArr[0] == COND_NAME_BRAND) {
			dataBind = " data-bind=" + "'" + condArr[2] + "'";
			condName = "品牌";
			condVal = condArr[1];
		} else {
			dataBind = " data-bind=" + "'" + condArr[3] + ":" + condArr[4]
					+ "'";
			condName = condArr[1];
			condVal = condArr[2];
		}

		var condStr = "<span class='crumb-select-item'" + " data-search-bind='"
				+ condItemArr[i] + "'" + dataBind + "><a href='/'><b>"
				+ condName + ":" + "</b><em>" + condVal
				+ "</em><i class='icon-remove'></i></a></span>"
		$(".shaixuan-tj").children().last().after(condStr);
	}
}

/*
 * 初始化查询条件（bread crumb）
 */
function initSearchCond_crumb(searchCond) {
	var condItemArr = new Array();
	if (searchCond != "")
		condItemArr = searchCond.split(",");
	for (var i = 0; i < condItemArr.length; i++) {
		g_searchCondArr.push(condItemArr[i]);
	}

}

/*
 * 初始化用户已经选择的筛选条件
 */
function initSearchCond_selected() {
	g_params.cid = $("#cid").val();

	var selectedBrands = $("#brandCond").val();
	if (selectedBrands != "") {
		g_params.brands = selectedBrands.split(",");
	}

	var selectedAttrs = $("#attrCond").val();
	if (selectedAttrs != "") {
		g_params.attrs = selectedAttrs.split(",");
	}

}

/*
 * 加入查询条件
 */
function addSearchCond_crumb(condStr) {
	g_searchCondArr.push(condStr);
	$("#searchCond").val(g_searchCondArr.toString());
}

/*
 * 移除查询条件（bread crumb）
 */
function removeSearchCond_crumb(condStr) {
	g_searchCondArr.remove(condStr);
	$("#searchCond").val(g_searchCondArr.toString());

}

/*
 * 通过表单提交
 */
function search_normal() {
	setSearchCond(g_params.attrs, g_params.brands); // 置隐藏表单中的查询条件值
	$("#testform").submit(); // 提交表单

	// 置条件变量为空
	g_params.brands = new Array();
	g_params.attrs = new Array();

}

// 常量定义
var COND_NAME_BRAND = "brand";
var COND_NAME_ATTR = "attr";

// 全局变量定义
var g_searchCondArr = new Array(); // 查询条件(bread crumb)
var g_params = new Object(); // 查询参数对象 //查询条件JSON对象
// 初始化查询条件的值
g_params.cid = 0;
g_params.brands = new Array();
g_params.attrs = new Array();

$(function() {

	initSearchCond_selected(); // 初始化用户己选择筛选条件

	initSearchCond_crumb($("#searchCond").val()); // 初始化查询条件 for crumb
	displaySearchCond_crumb($("#searchCond").val()); // 显示查询条件 for crumb

	/*
	 * 当用户MOUSE移动到已经选择的条件上时（crumb-面包渣）
	 */
	$(".shaixuan-tj span.crumb-select-item").hover(function(event) {
		if (event.type == 'mouseenter') {
			$(this).addClass("crumb-select-itemon");
		} else {
			$(this).removeClass("crumb-select-itemon");
		}
	});

	/*
	 * 移除条件 bread crumb,同时移除用户已选筛选条件
	 */
	$(".shaixuan-tj span.crumb-select-item").on('click', function(event) {
		event.preventDefault();

		var condArr = $(this).attr("data-search-bind").split(":");

		if (condArr[0] == COND_NAME_BRAND) { // 如果是类目条件
			g_params.brands.remove($(this).attr("data-bind"));
		} else {
			g_params.attrs.remove($(this).attr("data-bind"));
		}

		removeSearchCond_crumb($(this).attr("data-search-bind"))

		$(this).remove();

		resetPageInfo();
		search_normal();

	});

	// 当点击品牌时,获取品牌的id
	$(".brand").on('click', function(e) {
		g_params.brands.push($(this).attr('data-bind'));
		addSearchCond_crumb($(this).attr("data-search-cond"));
		resetPageInfo(); // 重置页号

		search_normal(); // 查询
	});

	// 当点击属性条件时，获取相应的值
	$(".attrValue").on('click', function(e) {
		g_params.attrs.push($(this).attr('data-bind'));
		addSearchCond_crumb($(this).attr("data-search-cond"));

		resetPageInfo();
		search_normal();
	});

	/*
	 * 分页导航： 当点击页号时读取需要导航到的页码及每页记录数（pageNum,pageSize）
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
			// 发送请求
			search_normal();
		}

	});

});