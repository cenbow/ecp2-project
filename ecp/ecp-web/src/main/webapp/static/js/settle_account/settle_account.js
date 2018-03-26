var OPERATE_ADD = "add", OPERATE_EDIT = "edit"; // 用户地址操作类型常量
var g_userAddressOperate = ""; // add | edit 用户地址操作类型

/*
 * 加载用户地址列表
 */

function loadAddressTableFun() {
	var parms = null;
	url = BASE_CONTEXT_PATH + "/front/useraddress/show";
	$("#userAddressInofTable").load(url, parms, function() {
		setAddressAndPerson();
	});
}

/*
 * 获取默认地址信息
 */
function getSelectedAddress() {
	var addr = "";
	// 迭代地址选择checkbox
	$(".addrSelector").each(function(index, val) {
		if (this.checked) {
			addr = $(this).attr("data-address");
			return addr;
		}
	});
	return addr;

}

/*
 * 获取默认地址信息
 */
function getSelectedPerson() {
	var person = "";
	// 迭代地址选择checkbox
	$(".addrSelector").each(function(index, val) {
		if (this.checked) {
			person = $(this).attr("data-person");
			return person;
		}
	});
	return person;
}

/*
 * 设置地址信息及用户信息
 */
function setAddressAndPerson() {
	var addr = getSelectedAddress();
	var person = getSelectedPerson();
	$("#selected-address").text(addr);
	$("#selected-person").text(person);
}

/* 地址选择-click处理函数 */
function selectorFunc(e) {
	$(".addrSelector").each(function(index, val) {
		if (e != this) {
			this.checked = false;
		}
	});

	setAddressAndPerson();
}

/* reset dialog */
function resetDialog() {
	$("#user-address-form")[0].reset();
	resetDistPickerDeep();
	
}

/*
 * 设置需要编辑的地址id 参数： addrObj:js地址对象
 */
function setEditValue(addrObj) {
	$("#editId").val(addrObj.id);
	
	//set distpicker
	$("#province").val(addrObj.provinceName);
	$("#province").trigger("change");
	$("#city").val(addrObj.cityName);
	$("#city").trigger("change");
	$("#district").val(addrObj.countyName);
	
	//required fields
	$("#contactPerson").val(addrObj.contactPerson);
	$("#contactPhone").val(addrObj.contactPhone);
	$("#fullAddress").val(addrObj.fullAddress);
	
	
	//area code
	$("#provinceCode").val(addrObj.provinceCode);  //市级编码
	$("#provinceName").val(addrObj.provinceName);
	
	$("#cityCode").val(addrObj.cityCode); //市级编码
	$("#cityName").val(addrObj.cityName);
	
	$("#countyCode").val(addrObj.countyCode); //县区级编码
	$("#countyName").val(addrObj.countyName);
	
	//non required fields
	$("#contactTel").val(addrObj.contactTel);  //固定电话
	$("#contactEmail").val(addrObj.contactEmail);  //联系邮箱
	
}

/**
 * 编辑之前初始地区选择器（用被编辑地址中的地区信息）
 * @param provinceName
 * @param cityName
 * @param districtName
 * @returns
 */
function setDistPicker(provinceName,cityName,districtName){
	$('#distpicker').distpicker({
	    province: provinceName,
	    city: cityName,
	    district: districtName
	});
}


/* 编辑地址 */
function editFunc(e) {
	var addrId = $(e).attr("data-bind");

	// 获取当前地址对象（AJAX方式,返回需要编辑的送货地址（JSON格式））
	getEditAddr(addrId);
}

/*
 * 采用AJAX请求，获取指定地址
 */
function getEditAddr(addrId) {
	var url = BASE_CONTEXT_PATH + "/front/useraddress/get";

	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		// dataType: "application/json",
		data : {
			'id' : addrId,
		},
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			console.log(res);
			if (res != null && res != "") {
				// console.log(res);
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					// util.message(obj.result_msg);
					var addrObj = $.parseJSON(obj.result_msg);
					// show edit dialog
					resetDialog();					
					setEditValue(addrObj);
					setDialogTitleAndOperate('修改-收货地址', OPERATE_EDIT); // 设置为编辑操作
					//setDistPicker('河北省','石家庄市','长安区');  //设置地区选择器
					showDialog(); // 显示编辑对话框

				} else {
					util.message(obj.result_err_msg);
					return "";
				}
			}
		}

	});
}

/*
 * 增加用户收货地址-保存 （ajax请求）
 */
function saveFun() {
	var url = null;

	if (g_userAddressOperate == OPERATE_ADD)
		url = BASE_CONTEXT_PATH + "/front/useraddress/insert";
	else
		url = BASE_CONTEXT_PATH + "/front/useraddress/update";

	// util.loading();
	$("#user-address-form").ajaxSubmit({
		type : "post",
		url : url,
		success : function(res) {
			console.log(res);
			if (res != null) {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					//util.message(obj.result_msg);
					loadAddressTableFun(); // 操作成功后重新加载用户地址列表

				} else {
					util.message(obj.result_err_msg);
				}
			}
		},
	});
}

/*
 * 采用ajax 删除用户地址
 */
function deleteInfoAjaxRequest(id) {
	var url = BASE_CONTEXT_PATH + "/front/useraddress/delete"; // 需要提交的 url

	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		// dataType: "application/json",
		data : {
			'id' : id,
		},
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					//util.message(obj.result_msg);
					loadAddressTableFun(); // 操作成功后重新加载用户地址列表
				} else {
					util.message(obj.result_err_msg);
				}
			}
		}

	});
}

/*
 * 采用ajax 设置默认用户地址
 */
function setDefaultAjaxRequest(id) {
	var url = BASE_CONTEXT_PATH + "/front/useraddress/setdefault"; // 需要提交的 url

	$.ajax({
		type : "post", // 提交方式 get/post
		url : url, // 需要提交的 url
		// dataType: "application/json",
		data : {
			'id' : id,
		},
		success : function(res) { // data 保存提交后返回的数据，一般为 json 数据
			console.log(res);
			if (res != null && res != "") {
				var obj = $.parseJSON(res);
				if (obj.result_code == "success") {
					//util.message(obj.result_msg);
					loadAddressTableFun(); // 操作成功后重新加载用户地址列表
				} else {
					util.message(obj.result_err_msg);
				}
			}
		}

	});
}

/*
 * 设置默认地址
 */
function setDefaultFunc(e) {
	var addrId = $(e).attr("data-bind"); // 新的默认地址ID
	var isDefault = $(e).attr("data-is-default");
	if (isDefault == 1) { // 如果已经是默认地址，则提示用户。
		//util.message("已经是默认地址！");
	} else // 设置默认地址
	{
		setDefaultAjaxRequest(addrId);
	}
}

/*
 * delete user address ajax
 */
function deleteFunc(e) {
	var userAddressId = $(e).attr("data-bind");
	util.delConfirm("确认删除？", userAddressId, "deleteInfoAjaxRequest");

	// 设置参数
	/*
	 * var params = {"loginName":loginName, "password":password,
	 * "agentId":agentId };
	 * 
	 * //util.loading(); //此种方式更加的简洁 $.post(url, params, function(res){
	 * console.log(res); if(res!=null && res!=""){ var obj = $.parseJSON(res);
	 * if(obj.result_code=="success"){ util.message(obj.result_msg); }else{
	 * util.message(obj.result_err_msg); } }
	 * 
	 * });
	 */
}

/*
 * 显示增加用户地址对话框
 */
function showDialog() {
	$('#modal-container-273078').modal({
		backdrop : 'static',
		keyboard : false
	});
}

/* close dialog */
function closeDialog() {
	$("#modal-container-273078").modal("hide");
}

/* set dialog's title and operate */
function setDialogTitleAndOperate(title, operate) {
	$("#myModalLabel").text(title);
	g_userAddressOperate = operate;
}

/*
 * 获取所选地址id
 */
function getSelectedAddressId() {
	var addrId = "";
	// 迭代地址选择checkbox
	$(".addrSelector").each(function(index, val) {
		if (this.checked) {
			addrId = $(this).attr("data-id");
			return addrId;
		}
	});
	return addrId;
}

/**
 * 是否选择了地址 return: 如果选择了则返回true,否则返回false
 */
function isSelectedAddr() {
	var flag = false;
	// 迭代地址选择checkbox
	$(".addrSelector").each(function(index, val) {
		if (this.checked) {
			flag = true;
			return flag;
		}
	});
	return flag;
}

/**
 * 提交订单
 */
function commitOrder() {
	// 判定用户是否已经选择了送货地址
	var flag = isSelectedAddr();
	if (!flag) {
		util.message("请先选择送货地址！");
		return;
	} else {
		var url = BASE_CONTEXT_PATH + "/front/order/add"; // 需要提交的 url
		var addrId = getSelectedAddressId();
		var memo=$("#memo").val();
		console.log(memo);
		createFormAndCommit(url, cartItemList, addrId,memo); // 确认订单
	}
}

/**
 * 生成隐藏表单域函数
 * 
 * @param {Object}
 *            name input's name
 * @param {Object}
 *            value value:inptu's value
 */
var generateHideElement = function(name, value) {
	var tempInput = document.createElement("input");
	tempInput.type = "hidden";
	tempInput.name = name;
	tempInput.value = value;
	return tempInput;
}

/**
 * 创建form并提交 *
 * 
 * @param {Object}
 *            url 提交的地址
 * @param {Object}
 *            cartItemList 用户已选商品列表
 * @param {Object}
 *            addrId 送货地址id
 * @param {Object}
 *            memo 订单备注           
 *            
 */
function createFormAndCommit(url, cartItemList, addrId,memo) {
	var form = document.createElement("form");
	form.id = "test";
	form.name = "test";
	document.body.appendChild(form);

	// 生成隐藏表单中的内容
	// 商品列表
	cartItemList
			.forEach(function(item, index) {

				var cid = generateHideElement("cartItemList[" + index + "]."+ "cid", item.cid), 
						itemId = generateHideElement("cartItemList[" + index + "]." + "itemId", item.itemId), 
						skuId = generateHideElement("cartItemList[" + index + "]." + "skuId", item.skuId), 
						skuName = generateHideElement("cartItemList[" + index + "]." + "skuName",item.skuName), 
						skuPrice = generateHideElement("cartItemList[" + index + "]." + "skuPrice",	item.skuPrice), 
						skuPicture = generateHideElement("cartItemList[" + index + "]." + "skuPicture",	item.skuPicture), 
						skuNum = generateHideElement("cartItemList[" + index + "]." + "skuNum", item.skuNum), 
						id = generateHideElement("cartItemList[" + index + "]." + "id", item.id);

				form.appendChild(cid);
				form.appendChild(itemId);
				form.appendChild(skuId);
				form.appendChild(skuName);
				form.appendChild(skuPrice);
				form.appendChild(skuPicture);
				form.appendChild(skuNum);
				form.appendChild(id);

			});
	// 用户所选地址id
	var addrId = generateHideElement("addrId", addrId);
	form.appendChild(addrId);
	
	//订单备注
	var memo=generateHideElement("memo", memo);
	form.appendChild(memo);

	form.method = "post";
	form.action = url;
	form.submit();
}

//==================地区相关函数===================
/**
 * 复位地区列表 (用于增加用户地址时)
 * @returns
 */
function resetDistPickerDeep(){
	 var $distpicker = $('#distpicker');
	 $distpicker.distpicker('reset', true);
}

//================同步更新详细地址====================

/**
 * 同步更新详细地址
 * @returns
 */
function syncUpdateFullAddress(){
	console.log("update full address");
	//选择select中的值
	var levelOneName=$("#province").val();
	var levelTwoName=$("#city").val();
	var levelThreeName=$("#district").val();
	
	var levelAllNam=levelOneName+levelTwoName+levelThreeName;
	
	$("#fullAddress").val(levelAllNam);
}

/**
 * 同步更新地区编码、名称
 * @returns
 */
function syncUpdateHiddenAreaCode(){
	console.log("debug");
	var code= $("#province").find("option:selected").attr("data-code");  //省级编码、名称
	var name= $("#province").find("option:selected").val();
	console.log("name:"+name);
	$("#provinceCode").val(code);
	$("#provinceName").val(name);
	
	console.log("debug end");
	
	
	code= $("#city").find("option:selected").attr("data-code");  //市级编码、名称
	name= $("#city").find("option:selected").val();
	$("#cityCode").val(code);
	$("#cityName").val(name);
	
	
	
	code= $("#district").find("option:selected").attr("data-code");  //县区级编码、名称
	name= $("#district").find("option:selected").val();
	$("#countyCode").val(code);
	$("#countyName").val(name);
	
	
}

//==================提交验证===================
/**
 * 判定用户是否选择了省级与市级
 * @returns true:己选择； false:未选择
 */
function hasSelectedProvAndCity(){
	console.log("enter cond.....");
	//util.message("test show message!");
	var provCode=$("#provinceCode").val();
	var cityCode=$("#cityCode").val();
	
	//console.log("cityCode:"+cityCode);
	
	if(!$.isBlank(provCode)  && !$.isBlank(cityCode)){
		console.log("has selected prov and city!");
		return true;
	}
	else{
		return false;
	}
}


/**
 * 必输入字段检查
 * @returns  true:己输入；false:未输入
 */
function requiredFieldInputed(){
	var contactPerson=$("#contactPerson").val();
	var fullAddress=$("#fullAddress").val();
	var contactPhone=$("#contactPhone").val();
	
	if(!$.isBlank(contactPerson)  && !$.isBlank(contactPerson) && !$.isBlank(contactPhone) ){
		return true;
	}
	else{
		return false;
	}
	
}


//===============PAGE LOADED READY=============
$(function() {
		
	loadAddressTableFun(); // 加载买家收货地址页面

	//================地址维护（CRUD）================
	
	/*
	 * 地址编辑对话框：【保存】用户地址
	 */
	$("#btnSave").on('click', function(e) {
		if(hasSelectedProvAndCity()){
			if(requiredFieldInputed()){
				closeDialog(); // close dialog(add buyer's delivery address dialog)
				saveFun();
			}
			else{
				util.message("请填写必输入字段！");
			}
			
		}
		else{
			//console.log("请选择省份、城市、区县！");
			util.message("请选择省份、城市、区县！");
		}
		
	});

	/*
	 * 【增加】用户地址
	 */
	$("#addUserAddress").on('click', function(e) {
		resetDialog(); // reset the edit dialog
		setDialogTitleAndOperate('增加-用户地址', OPERATE_ADD);
		showDialog();
		
	});
	
	//==============结算：订单提交===============
	
	/*
	 * 【提交订单】按钮 -click消息处理
	 */
	$("#commitOrder").on('click', function(e) {
		commitOrder();
	});
	
	
	//==================地区选择====================
	/*
	 *当“省份”变化时，将code赋值给value; 
	 */
	$("#province").on("change",function(){
		syncUpdateHiddenAreaCode();
		syncUpdateFullAddress();
	});
	
	/*
	 *当“市级”变化时，将ode赋值给value; 
	 */
	$("#city").on("change",function(){
		syncUpdateHiddenAreaCode();
		syncUpdateFullAddress();
	});
	
	/*
	 *当“县级”变化时，将code赋值给value; 
	 */
	$("#district").on("change",function(){
		syncUpdateHiddenAreaCode();
		syncUpdateFullAddress();
	});
	
		

	

});