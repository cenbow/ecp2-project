
/*
 * 根据条件查询列表
 */
$("#filter-submit-btn").click(function() {
	var params = new Object();
	params.pagehelperFun = "filterItem";
	filterItem(params);
});

/*
 * 过滤列表（根据条件内容）
 */
function filterItem(params) {
	params.condName = $("#filter-cond-name").val();
	params.condVal = $("#filter-cond-val").val();

	clickPageBtnRequestFun(params);
}

/*
 * 点击页面中的页码执行此函数 函数功能：根据页码数请求当前页内容
 */
function clickPageBtnRequestFun(params) {
	var action = "front/user/selectItem";
	params.clickPageBtn = true;
	// util.loading();
	$("#item-div").load(action, params);
}

/*
 * 点击列表中某个复选框时，全选或反选
 */
function checkOne(){
    
    var flag = true;
    $("#user-icon-table tbody input[type='checkbox']").each(function(){
    	if(!$(this).prop("checked")){
    		flag = false;
    	}
    });
    if(flag){
    	$("#user-icon-table thead input[type='checkbox']").prop('checked', true);
    }else{
    	$("#user-icon-table thead input[type='checkbox']").prop('checked', false);
    }
}
/*
 * 点击列表中All复选框时，列表全选或反选
 */
function checkAll(obj){
	$("#user-icon-table tbody input[type='checkbox']").prop('checked', $(obj).prop('checked'));
}

/*
 * 日期转字符串格式函数 调用方法：date.format('yyyy-MM-dd HH:mm:ss');
 */
Date.prototype.format = function(format) {
	var date = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S+" : this.getMilliseconds()
	};
	if (/(y+)/i.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + '')
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in date) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k]
					: ("00" + date[k]).substr(("" + date[k]).length));
		}
	}
	return format;
}