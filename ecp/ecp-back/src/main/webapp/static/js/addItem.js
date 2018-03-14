/***************************************************************************
 * 实例化编辑器
 **************************************************************************/

// 实例化编辑器
// 建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
//window.UEDITOR_HOME_URL = '/aida/tools/'; //一定要用这句话，否则你需要去ueditor.config.js修改路径的配置信息

//alert("home_url:"+UEDITOR_CONFIG.UEDITOR_HOME_URL);

//实例化编辑器并设置编辑器高度和宽度
var editorOption = {
		initialFrameHeight : 200,
		//initialFrameWidth : 300,
		enableAutoSave: false, //启用自动保存
        catchRemoteImageEnable: false, //远程图片抓取,当开启本插件时所有不符合本地域名的图片都将被抓取成为本地服务器上的图片
		toolbars : [ 
			[
				'fullscreen', //全屏
				'source', //源代码
				'|',
				'undo', //撤销
		        'redo', //重做
		        '|',
		        'bold', //加粗
		        'italic', //斜体
		        'underline', //下划线
		        'strikethrough', //删除线
		        'fontborder', //字符边框
		        'superscript', //上标
		        'subscript', //下标
		        'removeformat', //清除格式
		        'formatmatch', //格式刷
		        'autotypeset', //自动排版
		        'blockquote', //引用
		        'pasteplain', //纯文本粘贴模式
		        '|',
		        'forecolor', //字体颜色
		        'backcolor', //背景色
		        'insertorderedlist', //有序列表
		        'insertunorderedlist', //无序列表
		        'selectall', //全选
		        'cleardoc', //清空文档
		        '|',
		        'rowspacingtop', //段前距
		        'rowspacingbottom', //段后距
		        'lineheight', //行间距
		        '|',
		        'customstyle', //自定义标题
		        'paragraph', //段落格式
		        'fontfamily', //字体
		        'fontsize', //字号
		        '|',
		        'directionalityltr', //从左向右输入
		        'directionalityrtl', //从右向左输入
		        'indent', //首行缩进
		        '|',
		        'justifyleft', //居左对齐
		        'justifycenter', //居中对齐
		        'justifyright', //居右对齐
		        'justifyjustify', //两端对齐
		        '|',
		        'touppercase', //字母大写
		        'tolowercase', //字母小写
		        '|',
		        'link', //超链接
		        'unlink', //取消链接
				'anchor', //锚点
				'|',
				'imagenone', //默认
		        'imageleft', //左浮动
		        'imageright', //右浮动
		        'imagecenter', //居中
		        '|',
		        'simpleupload', //单图上传
		        'insertimage', //多图上传
		        'emotion', //表情
		        'scrawl', //涂鸦
		        //'insertvideo', //视频
		        //'music', //音乐
		        //'attachment', //附件
		        'map', //Baidu地图
		        //'gmap', //Google地图
		        'insertframe', //插入Iframe
		        'insertcode', //代码语言
		        //'webapp', //百度应用
		        'pagebreak', //分页
		        'template', //模板
		        'background', //背景
		        '|',
		        'horizontal', //分隔线
		        'time', //时间
		        'date', //日期
		        'spechars', //特殊字符
		        //'snapscreen', //截图
		        //'wordimage', //图片转存
		        '|',
		        'inserttable', //插入表格
		        'deletetable', //删除表格
		        'insertparagraphbeforetable', //"表格前插入行"
		        //'insertrow', //前插入行
		        'deleterow', //删除行
		        //'insertcol', //前插入列
		        'deletecol', //删除列
		        'mergecells', //合并多个单元格
		        'mergeright', //右合并单元格
		        'mergedown', //下合并单元格
		        'splittocells', //完全拆分单元格
		        'splittorows', //拆分成行
		        'splittocols', //拆分成列
		        /*'charts', // 图表
		        '|',
		        'edittip ', //编辑提示
		        'deletecaption', //删除表格标题
		        'inserttitle', //插入标题
		        'edittable', //表格属性
		        'edittd', //单元格属性*/
		        '|',
		        'print', //打印
		        'preview', //预览
		        'searchreplace', //查询替换
		        'drafts', // 从草稿箱加载
		        'help', //帮助
		    ]
		]
	};

//商品详情（描述）
var detail = new UE.ui.Editor(editorOption);  
detail.render("item-ueditor");
//var ue = UE.getEditor('item-ueditor', editorOption);
//售后服务
var after_service = new UE.ui.Editor(editorOption);  
after_service.render("after-service");
//var ue2 = UE.getEditor('after-service', editorOption);
//$(function(){
	
	bootstrapValidateFun();//启用验证
	
//});

/*
 * bootstrap验证
 */
function bootstrapValidateFun(){
	/*
	 * bootstrapValidator验证
	 */
	$("#save-form").bootstrapValidator({
	    message: "This value is not valid",
	    feedbackIcons: {
	        valid: "glyphicon glyphicon-ok",
	        invalid: "glyphicon glyphicon-remove",
	        validating: "glyphicon glyphicon-refresh"
	    },
	    
	    fields: {
	        itemName: {
	            validators: {
	                notEmpty: {
	                    message: "商品名称不能为空"
	                },
	                /*regexp: {
		                regexp: "^[\u4e00-\u9fa5A-Za-z0-9_\\s+\-\.()（）]+$",
		                message: "请勿输入特殊符号"
	                },*/
	                stringLength: {
                        max: 100,
                        message: '长度不能超过100个字符'
                    }
	            }
	        },
	        model: {
	            validators: {
	                notEmpty: {
	                    message: "型号不能为空"
	                },
	                /*regexp: {
		                regexp: "^[\u4e00-\u9fa5A-Za-z0-9_\\s+\-\.()]+$",
		                message: "请勿输入特殊符号"
	                },*/
	                stringLength: {
                        max: 100,
                        message: '长度不能超过50个字符'
                    }
	            }
	        },
	        keywords: {
	        	validators: {
	                notEmpty: {
	                    message: "关键字不能为空"
	                },
	                regexp: {
		                regexp: "^[\u4e00-\u9fa5A-Za-z0-9_\\s+\\\\\/]+$",
		                message: "请勿输入特殊符号"
	                },
	                stringLength: {
                        max: 700,
                        message: '长度不能超过700个字符'
                    }
	            }
	        },
	        guidePrice: {
	        	validators: {
	                notEmpty: {
	                    message: "商城指导价格不能为空"
	                },
	                regexp: {
		                regexp: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
		                message: "请输入正确价格"
	                },
	                stringLength: {
                        max: 10,
                        message: '长度不能超过10个字符'
                    }
	            }
	        },
	        marketPrice: {
	        	validators: {
	                notEmpty: {
	                    message: "市场价格不能为空"
	                },
	                regexp: {
		                regexp: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
		                message: "请输入正确价格"
	                },
	                stringLength: {
                        max: 10,
                        message: '长度不能超过10个字符'
                    }
	            }
	        },
	        marketPrice2: {
	        	validators: {
	                notEmpty: {
	                    message: "成本价格不能为空"
	                },
	                regexp: {
		                regexp: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
		                message: "请输入正确价格"
	                },
	                stringLength: {
                        max: 10,
                        message: '长度不能超过10个字符'
                    }
	            }
	        },
	        inventory: {
	        	validators: {
	                notEmpty: {
	                    message: "库存不能为空"
	                },
	                regexp: {
		                regexp: "^[0-9]*[1-9][0-9]*$",
		                message: "请输入正整数"
	                },
	                stringLength: {
                        max: 10,
                        message: '长度不能超过10个字符'
                    }
	            }
	        },
	        origin: {
	        	validators: {
	                notEmpty: {
	                    message: "商品产地不能为空"
	                },
	                /*regexp: {
		                regexp: "^\\d+(\\.\\d+)?$",
		                message: "请输入正数"
	                },*/
	                stringLength: {
                        max: 50,
                        message: '长度不能超过50个字符'
                    }
	            }
	        },
	        packingList: {
	        	validators: {
	                notEmpty: {
	                    message: "包装清单不能为空"
	                },
	                /*regexp: {
		                regexp: "^\\d+(\\.\\d+)?$",
		                message: "请输入正数"
	                },*/
	                stringLength: {
                        max: 300,
                        message: '长度不能超过300个字符'
                    }
	            }
	        },
	        volume: {
	        	validators: {
	                notEmpty: {
	                    message: "商品体积不能为空"
	                },
	                regexp: {
		                regexp: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
		                message: "请输入正确体积"
	                },
	                stringLength: {
                        max: 15,
                        message: '长度不能超过15个字符'
                    }
	            }
	        },
	        weight: {
	        	validators: {
	                notEmpty: {
	                    message: "商品毛重不能为空"
	                },
	                regexp: {
		                regexp: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/,
		                message: "请输入正确重量"
	                },
	                stringLength: {
                        max: 10,
                        message: '长度不能超过10个字符'
                    }
	            }
	        },
	        
	    }
	}).on('success.form.bv',function(e){
	    e.preventDefault();
	    saveFun();//验证通过保存内容
	});
}

/*
 * 根据父ID查询类目
 * 		参数1：父ID
 * 		参数2：第n级类目
 */
function selectCategoryByPid(pid, index){
	
	index++;
	var url = "back/category/selectByPid";
	var params = {"pid":pid};
	//util.loading();
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null && res!=""){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				var categoryList = obj.categoryList;
				console.log(JSON.stringify(categoryList));
				if(categoryList!=null){
					var htmlStr = "";
					$.each(obj.categoryList,function(i,n){
					    //console.log("索引"+i+"对应的值"+n);
					    htmlStr += createCategoryHtmlStr(this, index);//创建类目HTML字符串
					});
					console.log(htmlStr);
					if(index==2){
				    	$("#second-category").html(htmlStr);
				    	$("#third-category").html("");
				    	bindClickEven(2);//给二级类目绑定点击事件，选择后把当前选择的突出显示
				    }else if(index==3){
				    	$("#third-category").html(htmlStr);
				    	bindClickEven(3);//给三级类目绑定点击事件，选择后把当前选择的突出显示
				    }else{
				    	console.log("异常");
				    }
				}else{
					util.message("查询类目列表为null");
				}
			}else{
				util.message(obj.result_err_msg);
			}
		}
		
	});
}
/*
 * 创建类目HTML字符串
 */
function createCategoryHtmlStr(category, index){	//debugger;
	var htmlStr = "";
	if(index>=3){
		htmlStr += "<li id='third-"+category.cid+"' onclick='javascript:loadAttrPage("+category.cid+", &apos;"+category.cName+"&apos;)'>";
		htmlStr += category.cName;
	}else{
		htmlStr += "<li id='second-"+category.cid+"' onclick='javascript:selectCategoryByPid("+category.cid+", "+index+");'>";
		htmlStr += category.cName + "<b class='pull-right'>></b>";
	}
	
	htmlStr += "</li>";
	return htmlStr;
}

/*
 * 加载属性页面
 */
function loadAttrPage(cid, cname){
	//alert("选择的类目是 "+cname);
	$("#item-cid").val(cid);
	/*var url = "back/category/selectBrandAndAttr";//查询品牌、属性和属性值
	var params = {"cid": cid};
	$("#attr-page").load(url, params, function(){
		console.log("加载属性页面完成");
	});*/
}

/*
 * 给类目绑定点击事件，选择后把当前选择的突出显示
 */
function bindClickEven(index){
	$("#next-step-btn").attr("disabled", true);
	if(index==1){
		$(".item-category ul#first-category").on("click", "li", function(){
			console.log("click first category");
			$(".item-category ul#first-category li").removeClass("current");
			$(this).addClass("current");
			$("#current-select-category #first").text($(this).text().replace(">", ""));
			$("#current-select-category #second").text("");
			$("#current-select-category #third").text("");
		});
	}else if(index==2){
		$(".item-category ul#second-category").on("click", "li", function(){
			console.log("click second category");
			$(".item-category ul#second-category li").removeClass("current");
			$(this).addClass("current");
			$("#current-select-category #second").text("   >   "+$(this).text().replace(">", ""));
			$("#current-select-category #third").text("");
		});
	}else if(index==3){
		$(".item-category ul#third-category").on("click", "li", function(){
			console.log("click third category");
			$(".item-category ul#third-category li").removeClass("current");
			$(this).addClass("current");
			$("#current-select-category #third").text("   >   "+$(this).text().replace(">", ""));
			$("#next-step-btn").attr("disabled", false);
		});
	}else{
		console.log("设置当前选中异常");
	}
}
bindClickEven(1);//给一级类目绑定点击事件，选择后把当前选择的突出显示

/*
 * 保存内容提交
 */
$("#save-submit-btn").click(function(){
	$("#save-form").submit();
});
/*
 * 保存内容
 */
function saveFun(){
	
	var brand = $("#brand").val();
	if(brand==null || brand=="" || brand<=0){
		util.message("请选择品牌");
		return;
	}
	
	var url = "back/item/insert";
	
	/*var createTimeStr = $("#create-time-str").val();
	var createtime = null;
	if(createTimeStr==null || createTimeStr==""){
		createtime = new Date();
	}else{
		createtime = parserDate(createTimeStr);
	}
	console.log("创建时间（毫秒）："+createtime.getTime());
	var params = new Object();
	params.createstr = createtime.getTime();
	params.describeUrl = getContent("item-ueditor");//商品详情（描述）
	params.afterService = getContent("after-service");//售后服务
	console.log(getContent("after-service"));
	try{
		params.attributes = getItemAttr().toString();
		params.attrSale = getSaleAttr().toString();
	}catch(err){
		params.attributes = "";
		params.attrSale = "";
		console.log(err);
	}
	
	//sku
	var skuObj = getSkuInfo();
	var sku = skuObj.sku;
	var skuPrice = skuObj.skuPrice;
	console.log("insert sku:"+JSON.stringify(sku));
	console.log("insert sku price:"+JSON.stringify(skuPrice));
	params.skuJson = JSON.stringify(sku);
	params.skuPriceJson = JSON.stringify(skuPrice);*/
	
	//util.loading();
	/*$("#save-form").ajaxSubmit({
		type:"post",
		url:url,
		data:params,
		success : function(res) {
			console.log(res);
			if(res!=null){
				var obj = $.parseJSON(res);
				if(obj.result_code=="success"){
					//操作成功后重新加载当前菜单内容
					reloadInfoFun();
				}else{
					util.message(obj.result_err_msg);
				}
			}
		},
	});*/
	
	var params = getParams();
	console.log("params:"+JSON.stringify(params));
	if(!params){
		return false;
	}
	$.ajaxFileUpload({
		url: url, //用于文件上传的服务器端请求地址
        secureuri: false, //一般设置为false
        fileElementId: "picture-url", //文件上传空间的id属性  <input type="file" id="file" name="file" />
        dataType: "json", //返回值类型 一般设置为json
        data: params,
        success: function (res, status){  //服务器成功响应处理函数
        	console.log(res);
        	if(res!=null){
				if(res.result_code=="success"){
					//操作成功后重新加载当前菜单内容
					reloadInfoFun();
				}else{
					util.message(res.result_err_msg);
				}
			}
        },
        error: function (data, status, e){//服务器响应失败处理函数
        	console.log(e);
        }
	});
}

/**
 * 获取请求参数
 * @returns
 */
function getParams(){
	var params = new Object();
	params.itemId = $("#item-id").val();//ID
	
	params.cid = $("#item-cid").val();//商品三级类目
	
	params.brand = $("#brand").val();//品牌
	
	params.itemName = $("#item-name").val();//商品名称
	params.model = $("#item-model").val();//型号
	params.keywords = $("#keywords").val();//关键字
	params.guidePrice = $("#guide-price").val();//商城指导价格
	params.marketPrice = $("#market-price").val();//市场价格
	params.marketPrice2 = $("#market-price2").val();//成本价格
	params.inventory = $("#inventory").val();//库存数量
	params.origin = $("#origin").val();//商品产地
	params.packingList = $("#packing-list").val();//包装清单
	params.volume = $("#volume").val();//商品体积
	params.weight = $("#weight").val();//商品毛重
	params.weightUnit = $("#weight-unit").val();//重量单位
	
	var createTimeStr = $("#create-time-str").val();
	var createtime = null;
	if(createTimeStr==null || createTimeStr==""){
		createtime = new Date();
	}else{
		createtime = parserDate(createTimeStr);
	}
	console.log("创建时间（毫秒）："+createtime.getTime());
	
	params.created = createtime;//创建时间
	params.modified = new Date();//修改时间
	params.describeUrl = getContent("item-ueditor");//商品详情（描述）
	params.afterService = getContent("after-service");//售后服务
	console.log(getContent("after-service"));
	try{
		params.attributes = getItemAttr().toString();
		params.attrSale = getSaleAttr().toString();
	}catch(err){
		params.attributes = "";
		params.attrSale = "";
		console.log(err);
	}
	
	//sku
	var skuObj = getSkuInfo();
	console.log("skuObj:"+JSON.stringify(skuObj));
	if(!skuObj){
		return false;
	}
	var sku = skuObj.sku;
	var skuPrice = skuObj.skuPrice;
	console.log("insert sku:"+JSON.stringify(sku));
	console.log("insert sku price:"+JSON.stringify(skuPrice));
	params.skuJson = JSON.stringify(sku);
	params.skuPriceJson = JSON.stringify(skuPrice);
	
	params.skuShortSpec = $("#default-sku-short-spec").val();
	params.skuSpec = $("#default-sku-spec").val();
	
	return params;
}

/*
 * 重新加载当前菜单内容
 */
function reloadInfoFun(){
	//操作成功后重新加载
	var href = "back/item/addItem";
	parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
}

/*
 * 验证图片文件大小
 */
function validateImgFileSizeFun(file){
	if(verifyFilesSize(file, 5120, '缩略图不能大于5M！')){
		//showPreview(file, 'thumbnail-portrait');
		showMutiPreview(file, 'thumbnail-portrait');
		$("#save-submit-btn").attr("disabled", false);
	}else{
		$("#save-submit-btn").attr("disabled", true);
	}
}

/**
 * 点击选择类目选项卡时执行
 * @returns
 */
function clickSelectCateTab(){
	util.confirm("显示选择类目选项卡时，当前编辑的商品信息则会被重置，是否继续？", 0, "resetFun", "selectEditItemTab");
}
/**
 * 选择添加/编辑商品选项卡
 * @returns
 */
function selectEditItemTab(){
	$('#tabs-add-item a[href="#tab-4"]').tab('show');//显示编辑商品选项卡
}

/*
 * 重置form表单
 */
function resetFun(){
	$("#save-form").data('bootstrapValidator').destroy();//销毁bootstrapValidator验证
	bootstrapValidateFun();//启用验证
	//$('#save-form')[0].reset();
	$(":input","#save-form")  
	 .not(":button, :submit, :reset")  
	 .val("")
	 //.removeAttr("checked")  
	 .removeAttr("selected");
	if(isIE()) {// 此处判断是否是IE
	    $('#picture-url').replaceWith($('#upload').clone(true));
	} else {
	    $('#picture-url').val('');
	}
	
	$("#thumbnail-portrait").empty();
	$("#attr-page").empty();
	setContent("item-ueditor", "");//商品详情（描述）
	setContent("after-service", "");//售后服务
	
	$("#second-category").html("");//设置二级类目列表为空
	$("#third-category").html("");//设置三级类目列表为空
	$(".item-category ul#first-category li").removeClass("current");//设置一级类目未选中
	$("#current-select-category #first").text("");//设置用户当前选择的一级类目为空
	$("#current-select-category #second").text("");//设置用户当前选择的二级类目为空
	$("#current-select-category #third").text("");//设置用户当前选择的三级类目为空
	$("#next-step-btn").attr("disabled", true);//设置下一步按钮为不可点击
	
	$('#tabs-edit-item a[href="#tab-5"]').tab('show');//显示基本信息选项卡
	$("#item-info-li").addClass("hide");//隐藏商品信息选项卡
	$('#tabs-add-item a[href="#tab-3"]').tab('show');//显示选择类目选项卡
	
}

/**
 * 判断是否是IE浏览器
 * @returns
 */
function isIE(){ //ie?
	if(!!window.ActiveXObject || "ActiveXObject" in window){
		console.log("浏览器是IE");
		return true;
	}else{
		console.log("浏览器不是IE");
		return false; 
	}
}

/*
 * 日期字符串转Date
 */
var parserDate = function (dateStr) {  
    var t = Date.parse(dateStr);  
    if (!isNaN(t)) {  
        return new Date(Date.parse(dateStr.replace(/-/g, "/")));  
    } else {  
        return new Date();  
    }  
}

/*
 * 日期转字符串格式函数
 * 调用方法：date.format('yyyy-MM-dd HH:mm:ss');
 */
Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "H+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}