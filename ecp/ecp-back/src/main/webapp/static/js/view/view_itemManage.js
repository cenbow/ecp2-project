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

//售后服务
var after_service = new UE.ui.Editor(editorOption);  
after_service.render("after-service");

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
	        /*guidePrice: {
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
	        highestPrice: {
	        	validators: {
	                notEmpty: {
	                    message: "最高价格不能为空"
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
	        lowestPrice: {
	        	validators: {
	                notEmpty: {
	                    message: "最低价格不能为空"
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
	        hardCostPrice: {
	        	validators: {
	                notEmpty: {
	                    message: "硬成本价格不能为空"
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
	        },*/
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

/**
 * 点击查看详细信息按钮时执行，获取当前类目下属性和属性值，成功后请求获取商品信息
 */
function selectDetails(id, cid){
	resetFun();
	getAttrAndValueFun(id, cid);//查询属性和属性值
	//ajaxRequestGetItemInfo(id);//ajax请求获取商品信息
}

/**
 * 加载品牌、属性和属性值，未用，此函数在页面中
 */
/*function getAttrAndValueFun(id, cid){
	var url = "back/brand/selectByCid";
	var params = {"cid": cid};
	$.post(url, params, function(ret){//查询品牌
		var obj = $.parseJSON(ret);
		if(obj.result_code=="success"){
			var brandList = obj.brandList;
			console.log(JSON.stringify(brandList));
			if(brandList!=null){
				$("#brand").empty();
				var htmlStr = "<option value='0'>请选择</option>";
				$.each(brandList,function(i,n){
					htmlStr += "<option value='"+this.brand_id+"'>"+this.brand_name+"</option>";
				});
				$("#brand").append(htmlStr);
			}else{
				util.message("类目品牌为空！");
			}
			
			var url = "back/category/selectBrandAndAttr";//查询品牌、属性和属性值
			var params = {"cid": cid};
			$("#attr-page").load(url, params, function(){
				console.log("加载属性页面完成");
				//$('#tabs-edit-item a[href="#tab-7"]').tab('show');
				if(id!=null && id!=undefined){
					console.log("ajax请求获取商品信息");
					ajaxRequestGetItemInfo(id);//ajax请求获取商品信息
				}
			});
		}
	});
	
};*/

/**
 * ajax请求获取商品信息
 */
function ajaxRequestGetItemInfo(id){
	var url = "back/view/item/selectUpdateById";
	var params = {"id":id};
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var resp = $.parseJSON(res);
			if(resp.result_code=="success"){
				$("#edit-item-li").removeClass("hide");//显示编辑商品选项卡
				
				var item =resp.item;
				$("#item-id").val(item.itemId);//ID
				
				$("#curr-item-cid").val(item.cid);//用于保存当前商品的类目
				$("#item-cid").val(item.cid);//商品三级类目
				
				$("#brand").val(item.brand);//品牌
				
				$("#is-plan-product").val(item.isPlanProduct);//是否方案性产品
				
				$("#item-name").val(item.itemName);//商品名称
				$("#item-model").val(item.model);//型号
				$("#keywords").val(item.keywords);//关键字
				//$("#introduction").val(item.introduction);//商品简介	
				$("#guide-price").val(item.guidePrice);//商城指导价格
				$("#highest-price").val(item.highestPrice);//最高价格
				$("#lowest-price").val(item.lowestPrice);//最低价格
				$("#market-price").val(item.marketPrice);//市场价格
				/*$("#market-price2").val(item.marketPrice2);//成本价格
				$("#hard-cost-price").val(item.hardCostPrice);//硬成本价格*/
				$("#inventory").val(item.inventory);//库存数量
				$("#origin").val(item.origin);//商品产地
				$("#packing-list").val(item.packingList);//包装清单
				$("#volume").val(item.volume);//商品体积
				$("#weight").val(item.weight);//商品毛重
				$("#weight-unit").val(item.weightUnit);//重量单位
				$("#create-time-str").val(new Date(item.created).format('yyyy-MM-dd HH:mm:ss'));//创建时间
				
				var describe = item.describeUrl;
				if(describe!=null && describe!=""){
					setContent("item-ueditor", describe);//商品详情（描述）
				}
				
				var afterService = item.afterService;
				if(afterService!=null && afterService!=""){
					setContent("after-service", afterService);//售后服务
				}
				
				var pictureList =resp.pictureList;
				console.log("商品图片："+JSON.stringify(pictureList));
				$("#thumbnail-portrait").empty();
				$.each(pictureList, function(){
					$("#thumbnail-portrait").append("<img id='' name='' src='"+this.pictureUrl+"' style='height:100px;' />&nbsp;&nbsp;");//图片
				});
				
				var attributes = item.attributes;//商品属性
				var attrs = attributes.split(",");
				$("#item-attr input[name='itemAttr']").each(function(){
					for(var i=0; i<attrs.length; i++){
						var currAttr = $(this).val();
						var selectAttr = attrs[i];//属性:属性值
						if(currAttr==selectAttr){
							$(this).prop("checked", true);
						}
					}
				});
				
				var attrSale = item.attrSale;//销售属性
				var attrSales = attrSale.split(",");
				$("#sale-attr input[name='attrValId']").each(function(){
					for(var i=0; i<attrSales.length; i++){
						var currAttr = $(this).val();
						var selectAttr = attrSales[i];//属性:属性值
						var attrVal = selectAttr.split(":")[1];//属性值
						if(currAttr==attrVal){
							$(this).prop("checked", true);
						}
					}
				});
				
				createSku();
				g_is_save_sku = false;//是否保存sku(skuPage.jsp)
				//debugger;
				var skuList = resp.skuList;//SKU
				var skuPriceList = resp.skuPriceList;//SKU价格
				for(var i=0; i<skuList.length; i++){
					var sku = skuList[i];
					for(var j=0; j<skuPriceList.length; j++){
						var skuPrice = skuPriceList[j];
						if(sku.skuId==skuPrice.skuId){
							sku.skuPriceId = skuPrice.id;
							sku.hardCostPrice = skuPrice.hardCostPrice;
							sku.costPrice = skuPrice.costPrice;
							sku.marketPrice = skuPrice.marketPrice;
							sku.highestPrice = skuPrice.highestPrice;
							sku.lowestPrice = skuPrice.lowestPrice;
							sku.sellPrice = skuPrice.sellPrice;
						}
					}
					$("#sku-id-"+i).val(sku.skuId);//skuId
					$("#sku-price-id-"+i).val(sku.skuPriceId);//skuPriceId
					/*$("#hard-cost-price-"+i).val(sku.hardCostPrice);//硬成本价格
					$("#cost-price-"+i).val(sku.costPrice);//成本价*/
					$("#market-price-"+i).val(sku.marketPrice);//指导价
					$("#highest-price-"+i).val(sku.highestPrice);//最高价格
					$("#lowest-price-"+i).val(sku.lowestPrice);//最低价格
					$("#sell-price-"+i).val(sku.sellPrice);//销售价
					$("#volume-"+i).val(sku.volume);//体积
					$("#weight-"+i).val(sku.weight);//重量
					$("#sku-short-spec-"+i).val(sku.skuShortSpec);//简单sku规格
					$("#short-spec-"+i).attr("onclick", "javascript:openShortSpecModel("+sku.skuId+", "+i+");");//设置简单sku规格点击事件
					$("#spec-"+i).attr("onclick", "javascript:openSpecModel("+sku.skuId+", "+i+");");//设置标准sku规格点击事件
				}
				
				//如果没有销售属性或未选择销售属性
				var sale_attr_arr = getSaleAttr();
				if(sale_attr_arr==null || sale_attr_arr.length<=0){
					showOpenDefaultSpecBtn(skuList[0].skuId);
					showOpenDefaultShortSpecBtn(skuList[0].skuId);
				}else{
					hideOpenDefaultSpecBtn(0);
					hideOpenDefaultShortSpecBtn(0);
				}
				/*var skuHtml = $("#sku #sku-body").html().trim();
				if(skuHtml==null || skuHtml==""){
					showOpenDefaultSpecBtn();
				}*/
				
				$('#tabs-243687 a[href="#tab-2"]').tab('show');
				$('#tabs-edit-item a[href="#tab-5"]').tab('show');
				
				var status = item.itemStatus;
				if(status!=null && status==4){
					$("#save-submit-btn").attr("disabled", true);//设置保存按钮无效
					$("#save-submit-btn").attr("title", "已上架的商品不能保存编辑");//设置保存按钮title提示
					
					$("#spec-submit-btn").attr("disabled", true);//设置sku规格对话框中保存按钮无效
					$("#spec-submit-btn").attr("title", "已上架的商品不能保存编辑SKU规格");//设置sku规格对话框中保存按钮title提示
				}else{
					$("#save-submit-btn").attr("disabled", false);//设置保存按钮有效
					
					$("#spec-submit-btn").attr("disabled", false);//设置sku规格对话框中保存按钮有效
				}
				return;
			}
		}
		util.message("查询异常");
		
	});
}

/*
 * 根据父ID查询类目
 * 		参数1：父ID
 * 		参数2：第n级类目
 */
/*function selectCategoryByPid(pid, index){
	
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

 * 创建类目HTML字符串
 
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


 * 加载属性页面
 
function loadAttrPage(cid, cname){
	//alert("选择的类目是 "+cname);
	$("#item-cid").val(cid);
	var url = "back/category/selectBrandAndAttr";//查询品牌、属性和属性值
	var params = {"cid": cid};
	$("#attr-page").load(url, params, function(){
		console.log("加载属性页面完成");
	});
}


 * 给类目绑定点击事件，选择后把当前选择的突出显示
 
function bindClickEven(index){
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
		});
	}else{
		console.log("设置当前选中异常");
	}
}
bindClickEven(1);//给一级类目绑定点击事件，选择后把当前选择的突出显示
*/

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
	
	var url = null;
	var id = $("#item-id").val();
	if(id==null || id==""){
		util.message("此商品没有ID");
		return;
	}else{
		url = "back/view/item/updateById";
	}
	
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
	console.log("update sku:"+JSON.stringify(sku));
	console.log("update sku price:"+JSON.stringify(skuPrice));
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
	//console.log("POST参数："+decodeURI($("#save-form").serialize()));
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
	
	params.isPlanProduct = $("#is-plan-product").val();//是否方案性产品
	
	params.itemName = $("#item-name").val();//商品名称
	params.model = $("#item-model").val();//型号
	params.keywords = $("#keywords").val();//关键字
	/*params.guidePrice = $("#guide-price").val();//商城指导价格
	params.highestPrice = $("#highest-price").val();//最高价格
	params.lowestPrice = $("#lowest-price").val();//最低价格
	params.marketPrice = $("#market-price").val();//市场价格
	params.marketPrice2 = $("#market-price2").val();//成本价格
	params.hardCostPrice = $("#hard-cost-price").val();//硬成本价格*/
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
	//alert(JSON.stringify(params));
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
	console.log("update sku:"+JSON.stringify(sku));
	console.log("update sku price:"+JSON.stringify(skuPrice));
	params.skuJson = JSON.stringify(sku);
	params.skuPriceJson = JSON.stringify(skuPrice);
	
	params.isSaveSku = g_is_save_sku;//是否保存sku（默认为false）(skuPage.jsp)
	
	params.skuShortSpec = $("#default-sku-short-spec").val();
	params.skuSpec = $("#default-sku-spec").val();
	
	return params;
}

/*
 * 删除信息AJAX请求（物理删除）
 */
function deleteInfoAjaxRequest(id){
	var url = "back/view/item/deleteById";
	var params = {"id":id};
	//util.loading();
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null && res!=""){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				reloadInfoFun();
			}else{
				util.message(obj.result_err_msg);
			}
		}
		
	});
}

/*
 * 单个删除
 */
function deleteInfoFun(id){
	util.delConfirm("确认删除？", id, "deleteInfoAjaxRequest");
}

/*
 * 重新加载当前菜单内容
 */
function reloadInfoFun(){
	//操作成功后重新加载
	var href = "back/view/item/selectItems?pagehelperFun=clickPageBtnRequestFun";
	parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
}

/*
 * 点击页面中的页码执行此函数
 * 		函数功能：根据页码数请求当前页内容
 */
function clickPageBtnRequestFun(params){
	var action = "back/view/item/selectItems";
	params.clickPageBtn = true;
	//util.loading();
	$("#item-div").load(action, params, function(){
		//加载完成
	});
}

/*
 * 验证图片文件大小
 */
function validateImgFileSizeFun(file){
	if(verifyFilesSize(file, 5120, '上传缩略图不能大于5M！')){
		//showPreview(file, 'thumbnail-portrait');
		showMutiPreview(file, 'thumbnail-portrait');
		$("#save-submit-btn").attr("disabled", false);
	}else{
		$("#save-submit-btn").attr("disabled", true);
	}
}

/*
 * 点击列表中某个复选框时，全选或反选
 */
function checkOne(){
    
    var flag = true;
    $("#product-table tbody input[type='checkbox']").each(function(){
    	if(!$(this).prop("checked")){
    		flag = false;
    	}
    });
    if(flag){
    	$("#product-table thead input[type='checkbox']").prop('checked', true);
    }else{
    	$("#product-table thead input[type='checkbox']").prop('checked', false);
    }
}
/*
 * 点击列表中All复选框时，列表全选或反选
 */
function checkAll(obj){
	$("#product-table tbody input[type='checkbox']").prop('checked', $(obj).prop('checked'));
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
	
	$('#tabs-edit-item a[href="#tab-5"]').tab('show');//显示基本信息选项卡
	$("#edit-item-li").addClass("hide");//隐藏商品信息选项卡
	$('#tabs-243687 a[href="#tab-1"]').tab('show');//显示商品列表选项卡
}

/**
 * 判断是否是IE浏览器
 * @returns
 */
function isIE(){ //ie?
	if(!!window.ActiveXObject || "ActiveXObject" in window){
		return true;
	}else{
		return false; 
	}
}

/**
 * change事件
 * 		改变商品类目
 * @returns
 */
function changeItemCategory(){
	var cid = $("#item-cid").val();
	util.confirm("改变商品类目，属性和SKU信息需要重新设置，是否继续？", cid, "reloadAttrAndValuePage", "cancelChangeItemCantegory");
}

/**
 * 点击商品列表选项卡时执行
 * @returns
 */
function clickItemTab(){
	//util.confirm("显示商品列表选项卡时，当前编辑的商品信息则会被重置，是否继续？", 0, "resetFun", "selectEditItemTab");
}
/**
 * 选择添加/编辑商品选项卡
 * @returns
 */
function selectEditItemTab(){
	$('#tabs-243687 a[href="#tab-2"]').tab('show');//显示编辑商品选项卡
}

/**
 * 根据类目ID重新加载品牌、属性和属性值
 */
function reloadAttrAndValuePage(cid){
	getAttrAndValueFun(null, cid);
	/*getBrandByCid(cid);//根据类目查询品牌
	var url = "back/category/selectBrandAndAttr";//查询品牌、属性和属性值
	var params = {"cid": cid};
	$("#attr-page").load(url, params, function(){
		console.log("加载属性页面完成");
	});*/
};

/**
 * 根据类目查询品牌
 * @returns
 */
/*function getBrandByCid(cid){
	var url = "back/brand/selectByCid";
	var params = {"cid": cid};
	$.post(url, params, function(ret){//查询品牌
		var obj = $.parseJSON(ret);
		if(obj.result_code=="success"){
			var brandList = obj.brandList;
			console.log(JSON.stringify(brandList));
			if(brandList!=null){
				$("#brand").empty();
				var htmlStr = "<option value='0'>请选择</option>";
				$.each(brandList,function(i,n){
					htmlStr += "<option value='"+this.brand_id+"'>"+this.brand_name+"</option>";
				});
				$("#brand").append(htmlStr);
			}else{
				util.message("类目品牌为空！");
			}
			
			var url = "back/category/selectBrandAndAttr";//查询品牌、属性和属性值
			var params = {"cid": cid};
			$("#attr-page").load(url, params, function(){
				console.log("加载属性页面完成");
				//$('#tabs-edit-item a[href="#tab-7"]').tab('show');
				ajaxRequestGetItemInfo(id);//ajax请求获取商品信息
			});
		}
	});
}*/

/**
 * 取消改为商品原类目ID
 */
function cancelChangeItemCantegory(){
	var curr_item_cid = $("#curr-item-cid").val();
	$("#item-cid").val(curr_item_cid);
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

/**
 * 修改商品状态（4：上架，5：下架）
 * @returns
 */
function updateItemStatus(itemId, status){
	var url = "back/view/item/updateStatusById";
	var params = {"itemId":itemId, "itemStatus":status};
	//util.loading();
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null && res!=""){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				if(status==4){
					$("#item-status-up-"+itemId).attr("disabled", true);
					$("#item-status-down-"+itemId).attr("disabled", false);
					$("#item-del-btn-"+itemId).attr("disabled", true);
					$("#item-del-btn-"+itemId).attr("title", "已上架商品不能删除");
				}else{
					$("#item-status-up-"+itemId).attr("disabled", false);
					$("#item-status-down-"+itemId).attr("disabled", true);
					$("#item-del-btn-"+itemId).attr("disabled", false);
					$("#item-del-btn-"+itemId).attr("title", "");
				}
				
			}else{
				util.message(obj.result_err_msg);
			}
		}
		
	});
}

/**
 * 点击查询面板中的查询按钮时执行
 * 		功能：根据条件过滤查询商品列表
 */
$("#search-submit-btn").click(function(){
	var params = new Object();
	params.pagehelperFun="filterItemFun";
	filterItemFun(params);
});
/**
 * 绑定查询表单的keydown事件
 */
$("#search-form").keydown(function(e){
	var curKey = e.which;
	if(curKey == 13){
		$("#search-submit-btn").click();
		return false;
	}
}); 

/**
 * 点击查询面板中的重置按钮时执行
 * 		功能：重置查询面板中的form表单
 */
$("#search-reset-btn").click(function(){
	$("#search-form")[0].reset();
});

/**
 * 点击批量删除按钮时执行
 * 		功能：批量删除商品（逻辑删除）
 */
$("#batch-delete-btn").click(function(){
	var itemIds = getCheckedItem();
	if(itemIds.length<=0){
		util.message("请选择需要删除的商品！");
		return;
	}
	deleteInfoByBatch();//批量删除确认
});

/*
 * 批量删除确认
 */
function deleteInfoByBatch(){
	util.delConfirm("确认删除？", 0, "ajaxRequestDelByBatch");
}

/**
 * 批量删除ajax请求
 * @returns
 */
function ajaxRequestDelByBatch(){
	var itemIds = getCheckedItem();
	if(itemIds.length<=0){
		util.message("请选择需要删除的商品！");
		return;
	}
	var url = "back/view/item/deleteByIds";
	var params = {"ids":itemIds};
	//util.loading();
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null && res!=""){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				reloadInfoFun();//删除成功重新刷新列表
			}else{
				util.message(obj.result_err_msg);
			}
		}
	});
}

/**
 * 点击批量上架按钮时执行
 * 		功能：批量上架商品
 * 		4=上架；5=下架
 */
$("#batch-shelve-btn").click(function(){
	var itemIds = getCheckedItem(4);
	if(itemIds==undefined || itemIds==null || itemIds==""){
		util.message("请选择需要上架的商品（不包括已上架的商品）！");
		return;
	}
	ajaxRequestUpdateStatusByBatch(itemIds, 4);//ajax请求批量修改商品上下架状态
});

/**
 * 点击批量下架按钮时执行
 * 		功能：批量下架商品
 * 		4=上架；5=下架
 */
$("#batch-unshelve-btn").click(function(){
	var itemIds = getCheckedItem(5);
	if(itemIds==undefined || itemIds==null || itemIds==""){
		util.message("请选择需要下架的商品（不包括已下架的商品）！");
		return;
	}
	ajaxRequestUpdateStatusByBatch(itemIds, 5);//ajax请求批量修改商品上下架状态
});

/**
 * ajax请求批量修改商品上下架状态
 * @returns
 */
function ajaxRequestUpdateStatusByBatch(itemIds, status){
	var url = "back/view/item/updateStatusByIds";
	var params = {"itemIds":itemIds, "itemStatus":status};
	//util.loading();
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null && res!=""){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				reloadInfoFun();//删除成功重新刷新列表
			}else{
				util.message(obj.result_err_msg);
			}
		}
	});
}

/*
 * 点击页面中的页码执行此函数
 * 		函数功能：根据页码数请求当前页内容
 */
function filterItemFun(params){
	var action = "back/view/item/selectItems";
	params.clickPageBtn = true;
	var cateFirst = $("#search-cate-first").val();
	var cateSecond = $("#search-cate-second").val();
	var cateThird = $("#search-cate-third").val();
	var keywords = $("#search-keywords").val();
	params.search_cate_first = cateFirst;
	params.search_cate_second = cateSecond;
	params.search_cate_third = cateThird;
	params.search_keywords = keywords;
	//util.loading();
	$("#item-div").load(action, params, function(){
		//加载完成
	});
}

/**
 * 获取用户选中的checkbox值
 * 4=上架；5=下架
 * @returns
 */
function getCheckedItem(status){
	var itemIds = new Array();
	$("#product-table tbody input:checkbox[name='itemCheckbox']:checked").each(function(i){
		var itemId = $(this).val();
		var currStatus = $("#item-status-"+itemId).val();
		
		if(status==undefined || status==null || status==""){//为空
			itemIds.push(itemId);
		}else{
			if(status==4 && currStatus!=4){
				itemIds.push(itemId);
			}else if(status==5 && currStatus!=5){
				itemIds.push(itemId);
			}
		}
	});
	console.log("用户选择的商品ID："+itemIds);
	return itemIds.toString();
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