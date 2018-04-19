//-----------------------通用函数-----------------------

/*
 * 用于判定是否为空
 */
function isBlank(obj) {
	return (!obj || $.trim(obj) === "");
}

// --------------------绑定关系-------------------
/**
 * 函数功能：打开绑定选项卡
 * 
 * @param id
 * @returns
 */
function showAgentBindOIS(that) {
	var agentId=$(that).attr("data-bind");
	var agentCompanyName=$(that).attr("data-company-name");
	var url = BASE_CONTEXT_PATH + "/back/agent-bind/bind/" + agentId;

	$("#edit-body").load(url, function() {
		$("#edit-tab").removeClass("hide");
		$("#edit-tab-title").text("绑定OS/IS");
		$('#tabs-14933 a[href="#panel-602679"]').tab('show');
		
		
		$("#agent-company-name").text(agentCompanyName);
	});
}

/**
 * 批量绑定
 * @param 
 * @returns
 */
function showAgentBatchBindOIS() {
	//var agentId=$(that).attr("data-bind");
	//var agentCompanyName=$(that).attr("data-company-name");
	var url = BASE_CONTEXT_PATH + "/back/agent-bind/showbatchbind";

	$("#edit-body").load(url, function() {
		$("#edit-tab").removeClass("hide");
		$("#edit-tab-title").text("绑定OS/IS");
		$('#tabs-14933 a[href="#panel-602679"]').tab('show');
		
		
		$("#agent-company-name").text("批量绑定");
	});
}



// -----------------------查询相关------------------------
/*
 * 重新加载页面
 * 
 * 采用上次的查询条件类型及条件值
 */
function reloadPage() {
	// 生成参数对象
	var parms = new Object();
	// 分页数据
	parms.pageNum = $("#pageNum").val();
	parms.pageSize = $("#pageSize").val();
	// 查询类型，查询条件值
	parms.searchTypeValue = g_searchTypeValue;
	parms.condValue = g_condValue;

	// 区域条件
	var provinceName = $("#provinceName").val();
	var cityName = $("#cityName").val();
	var countyName = $("#countyName").val();
	parms.provinceName = provinceName;
	parms.cityName = cityName;
	parms.countyName = countyName;

	loadUserAgent(parms, null); // 加载页面
}

/**
 * 根据用户的查询条件类型,条件值查询（包括分页数据）
 * 
 * @returns
 */
function search() {
	// 生成参数对象
	var parms = new Object();

	// 分页数据
	parms.pageNum = $("#pageNum").val();
	parms.pageSize = $("#pageSize").val();

	// 查询类型，查询条件值
	var condType = $("#ordertime-cond").val();
	var condStr = $("#searchCond").val();
	// 将条件类型,条件值,区域条件赋值给查询参数.
	parms.searchTypeValue = condType;
	parms.condValue = condStr;

	// 区域条件
	var provinceName = $("#provinceName").val();
	var cityName = $("#cityName").val();
	var countyName = $("#countyName").val();
	parms.provinceName = provinceName;
	parms.cityName = cityName;
	parms.countyName = countyName;

	loadUserAgent(parms, null); // 加载页面
}

// ===============分配帐户对话框 open/close==================
/*
 * 显示分配帐户窗口
 */
/*
 * function displayWindow() { $('#modal-container-273078').modal({ backdrop :
 * 'static', keyboard : false }); }
 */

/* 关闭分配帐户窗口 */
/*
 * function closeWindow() { $("#modal-container-273078").modal("hide"); }
 */

// --------------------查询界面更新----------------------
/**
 * 设置查询条件类型:类型名称及值
 * 
 * @param selectedTxt
 *            条件类型名称
 * @param value
 *            条件类型值
 * @returns
 */
function setOrderTimeCond(selectedTxt, value) {
	$("#ordertime-cond").text(selectedTxt);
	$("#ordertime-cond").val(value);
}

/**
 * popup菜单更新(查询条件类型菜单) 根据回传的"查询条件类型值"更新界面
 * 
 * @param orderTimeCond
 *            查询条件类型值
 * @returns
 */
function updateUIOrderTime(orderTimeCond) {
	// 纵向菜单
	$(".time-list li").each(function() {

		$(this).removeClass("curr");
		$(this).find('i').hide();

		if ($(this).val() == orderTimeCond) {
			$(this).addClass("curr");
			$(this).find('i').show();
			// 设置处理状态当前
			var selectedTxt = $(this).find('a').text();
			var value = $(this).val();
			setOrderTimeCond(selectedTxt, value);
		}
	});
}

/**
 * 根据回传的"区域"数据更新区域选择界面
 * 
 * @returns
 */
function updateUIArea() {
	$("#province").val(ret_provinceName);
	$("#province").trigger("change");

	$("#city").val(ret_cityName);
	$("#city").trigger("change");

	$("#district").val(ret_countyName);
}

// --------------------区域操作-----------------------
/**
 * 复位地区列表
 * 
 * @returns
 */
function resetDistPickerDeep() {
	var distpicker = $('#distpicker');
	distpicker.distpicker('reset', true);
}

/**
 * 同步更新地区(省,市,县)编码、名称
 * 
 * @returns
 */
function syncUpdateHiddenAreaCode() {

	// 省级名称及编码
	// console.log("debug");
	var code = $("#province").find("option:selected").attr("data-code"); // 省级编码、名称
	var name = $("#province").find("option:selected").val();
	// console.log("name:"+name);
	$("#provinceCode").val(code);
	$("#provinceName").val(name);
	// console.log("debug end");

	// 市级名称及编码
	code = $("#city").find("option:selected").attr("data-code"); // 市级编码、名称
	name = $("#city").find("option:selected").val();
	$("#cityCode").val(code);
	$("#cityName").val(name);

	// 区县级名称及编码
	code = $("#district").find("option:selected").attr("data-code"); // 县区级编码、名称
	name = $("#district").find("option:selected").val();
	console.log("countyName:" + name);
	$("#countyCode").val(code);
	$("#countyName").val(name);
}

/*
 * 对查询条件进行有效性校验 如果成功则返回 true; 否则返回false
 */
function validateSearchCond() {

	var condType = $("#ordertime-cond").val(); // 查询条件类型
	var condStr = $("#searchCond").val(); // 查询条件

	// 区域值
	var provinceName = $("#provinceName").val();
	var cityName = $("#cityName").val();
	var countyName = $("#countyName").val();

	// (1)无效的情况1
	// 没有选择查询条件:未选择区域,未输入查询条件

	if (isBlank(condStr) && isBlank(provinceName) && isBlank(cityName)
			&& isBlank(countyName)) {
		return false;
	}

	if (condType == 0 && !(isBlank(condStr))) {
		return false;
	}

	return true;

}

// -----------------------批量解绑-----------------------
/**
 * @Description 批量解除绑定
 * @param id
 *            此参数无效.
 * @returns
 */
function batchUnbindOkBtnClick(id) {
	// 所送的条件与查询的条件相同.但不传输页号.
	// 生成参数对象
	var parms = new Object();
	// 分页数据
	parms.pageNum = $("#pageNum").val();
	parms.pageSize = $("#pageSize").val();
	// 查询类型，查询条件值
	parms.searchTypeValue = g_searchTypeValue;
	parms.condValue = g_condValue;

	// 区域条件
	parms.provinceName = ret_provinceName;
	parms.cityName = ret_cityName;
	parms.countyName = ret_countyName;

	var urlStr = BASE_CONTEXT_PATH + "/back/agent-bind/batchunbind"; // 需要提交的url

	$.ajax({
		type : "POST", // 提交方式 get/post
		url : urlStr,
		// contentType : "application/json", //
		// 如果采用json格式传送所有参数时必须有,如果采普通js对象传送时,则不可以加此参数
		// dataType : "html", //表示返回值类型，不必须,如果返回的是面页，则必须
		data : parms,
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			// console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
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




// --------------------PAGE LOADED READY-----------------
$(function() {
	resetDistPickerDeep();
	updateUIOrderTime(g_searchTypeValue);
	updateUIArea();

	/*
	 * 查询类型(下拉菜单): hover: SHOW/HIDE
	 */
	$(".ordertime-cont").hover(function() {
		$(".time-list").show();
	}, function() {
		$(".time-list").hide();
	});

	/*
	 * 查询条件类型（popup菜单）: click event process
	 */
	$(".time-list li").on("click", function() {
		// 取得查询条件类型值及名称
		var selectedTxt = $(this).find('a').text();
		var value = $(this).val();

		setOrderTimeCond(selectedTxt, value); // 设置查询条件类型
		updateUIOrderTime(value); // 更新查询条件类型显示(打上对勾)

		// 如果查询条件类型为第一项:请选择查询条件 时刷新列表
		if (value == 0) {
			search();
		}

		// $(this).blur(null);
	});

	/*
	 * 查询按钮:click event process
	 */
	$(".start-search").on("click", function() {

		if (!validateSearchCond()) {
			util.message("请选择查询类型或是输入查询条件!", null, "info");
			return;
		} else {
			search();
		}

	});

	/*
	 * 条件输入框: press enter key
	 */

	$("#searchCond").on("keydown", function(event) {
		if (event.keyCode == 13) {
			$(".start-search").trigger("click");

		}
	});

	// --------------地区选择---------------
	/*
	 * 省份:change 当“省份”变化时，将code赋值给value;
	 */
	$("#province").on("change", function() {
		syncUpdateHiddenAreaCode();
	});

	/*
	 * 当“市级”变化时，将ode赋值给value;
	 */
	$("#city").on("change", function() {
		syncUpdateHiddenAreaCode();
	});

	/*
	 * 当“县级”变化时，将code赋值给value;
	 */
	$("#district").on("change", function() {
		syncUpdateHiddenAreaCode();
	});

	/*
	 * 批量绑定
	 */
	$("#batch-bind").on("click", function() {
		//(1)显示批量绑定的界面.
		//(2)而后选择OS/IS
		//(3)绑定后刷新页面.
		showAgentBatchBindOIS();
		
	})

	/*
	 * 批量解绑
	 */
	$("#batch-unbind").on("click", function() {
		util.delConfirm("确认批量绑定.", 0, "batchUnbindOkBtnClick");
	})

	// ------------------------翻页-----------------------

	/**
	 * 分页导航： pager-click-event 当点击页号时读取需要导航到的页码及每页记录数（pageNum,pageSize）
	 * 
	 * data-bind:pageNum(页号)-pageSize(页大小)形式.如果data-bind为空字符串，则不做动作
	 * 当用户点击某页时，则发送与筛选相同的请求
	 */

	$(".pagination li a").on('click', function(e) {
		// alert($(this).attr("data-bind"));
		var dataBind = $(this).attr("data-bind");
		// 当dataBind有数据时处理
		if (dataBind != "") {
			var pageArr = dataBind.split("-");
			// 置隐藏表单数据
			$("#pageNum").val(pageArr[0]);
			$("#pageSize").val(pageArr[1]);
			reloadPage(); // 发送请求
		}

	});

}); // end of page loaded
