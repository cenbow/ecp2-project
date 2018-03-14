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
function setSearchCond(brands, category) {
	// 置隐藏表单中的值
	$("#brandCond").val(brands);
	$("#categoryCond").val(category);
}

/*
 * 通过隐藏表单进行提交
 */
function search_normal() {
	setSearchCond(g_params.brands, g_params.category);
	$("#testform").submit();

	g_params.brands = "";
	g_params.category = "";
	g_searchCondArr = new Array();

}

/*
 * 显示查询条件 (bread crumb)
 */
function displaySearchCond(searchCond) {
	// var searchCond=$("#searchCond").val();
	var condItemArr = new Array();
	if (searchCond != "")
		condItemArr = searchCond.split(",");
	// alert(condItemArr.length);
	for (var i = 0; i < condItemArr.length; i++) {

		var condName = "";
		var condArr = condItemArr[i].split(":");

		if (condArr[0] == '品牌') {
			condName = "brand";
		} else {
			condName = "category";
		}

		var condStr = "<span class='crumb-select-item'" + " data-bind='"
				+ condArr[2] + "'" + " data-cond-name='" + condName + "'"
				+ " data-cond-value='" + condArr[1] + "'" + "><a href='/'><b>"
				+ condArr[0] + ":" + "</b><em>" + condArr[1]
				+ "</em><i class='icon-remove'></i></a></span>"
		$(".shaixuan-tj").children().last().after(condStr);
	}
}

/*
 * 查询条件（bread crumb）
 */
function initSearchCond(searchCond) {
	var condItemArr = new Array();
	if (searchCond != "")
		condItemArr = searchCond.split(",");
	for (var i = 0; i < condItemArr.length; i++) {
		g_searchCondArr.push(condItemArr[i]);
	}

}

/*
 * 加入查询条件
 */
function addSearchCond(condName, condValue, condId) {
	g_searchCondArr.push(condName + ":" + condValue + ":" + condId);
	$("#searchCond").val(g_searchCondArr.toString());
}

/*
 * 移除查询条件
 */
function removeSearchCond(condName, condValue, condId) {
	var condStr = condName + ":" + condValue + ":" + condId;
	g_searchCondArr.remove(condStr);
	$("#searchCond").val(g_searchCondArr.toString());

}

// 常量定义
var COND_NAME_BRAND = "brand";
var COND_NAME_CATEGORY = "category";

// 全局量定义
var g_params = null; // 查询条件JSON对象
var g_searchCondArr = new Array(); // 查询条件
g_params = new Object();

// 初始化查询条件的值
g_params.brands = $("#brandCond").val();
g_params.category = $("#categoryCond").val();

$(function() {

	displaySearchCond($("#searchCond").val()); // 显示查询条件
	initSearchCond($("#searchCond").val()); // 初始化查询条件

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
	 * 移除条件
	 */
	$(".shaixuan-tj span.crumb-select-item").on(
			'click',
			function(event) {
				event.preventDefault();

				if ($(this).attr("data-cond-name") == COND_NAME_BRAND) { // 如果是品牌条件
					g_params.brands = "";
					removeSearchCond("品牌", $(this).attr("data-cond-value"), $(
							this).attr("data-bind"))
				}
				if ($(this).attr("data-cond-name") == COND_NAME_CATEGORY) { // 如果是类目条件
					g_params.category = "";
					removeSearchCond("类目", $(this).attr("data-cond-value"), $(
							this).attr("data-bind"))
				}

				$(this).remove();

				resetPageInfo();
				search_normal();

			});

	// 当点击品牌时,获取品牌的id 增加条件
	$(".brand").on(
			'click',
			function(e) {
				if (g_params.brands == "") {
					g_params.brands = $(this).attr('data-bind');
				} else {
					g_params.brands = g_params.brands + ","
							+ $(this).attr('data-bind');
				}

				addSearchCond("品牌", $(this).attr('data-brand-name'), $(this)
						.attr('data-bind'));
				// alert(g_searchCondArr.toString());

				resetPageInfo();
				search_normal();
			});

	// 当点击类目条件时，获取相应的值 增加条件
	$(".category").on(
			'click',
			function(e) {
				if (g_params.category == "") {
					g_params.category = $(this).attr('data-bind');
				} else {
					g_params.category = g_params.category + ","
							+ $(this).attr('data-bind');
				}

				addSearchCond("类目", $(this).attr('data-category-name'), $(this)
						.attr('data-bind'));

				// alert(g_searchCondArr.toString());

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