<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<div class="fieldset" id="sku">
	<div class="legend" onclick="getSkuInfo();" title="选择销售属性自动创建SKU">SKU</div>
	<div id="sku-content">
		<table class="table table-striped table-bordered table-hover " id="sku-table"
	style="width: 100%;"center">
	<thead id="sku-head" style="width: 98%; padding-top: 80px;">
		
	</thead>
	<tbody id="sku-body">
		
	</tbody>
</table>
	</div>
</div>
<!-- <script>
/* 如果商品属性为空时不显示标题 */
var itemAttrBody = $("#item-attr-body").html().trim();
console.log("itemAttrBody:"+itemAttrBody);
if(itemAttrBody==null || itemAttrBody==""){
	$("#item-attr").remove();
}
/* 如果销售属性为空时不显示标题 */
var saleAttrBody = $("#sale-attr-body").html().trim();
console.log("saleAttrBody:"+saleAttrBody);
if(saleAttrBody==null || saleAttrBody==""){
	$("#sale-attr").remove();
}

/*
 * 获取商品属性和属性值
 */
function getItemAttr(){
	var attrArr = new Array();
	//商品属性
	$("#item-attr input[name='itemAttr']:checked").each(function(){
		attrArr.push($(this).val());
	});
	console.log("商品属性："+attrArr);
	return attrArr;
}
/*
 * 获取销售属性和属性值
 */
function getSaleAttr(){
	var attrArr = new Array();
	//销售属性
	$("#sale-attr input[name='saleAttr']:checked").each(function(){
		attrArr.push($(this).val());
	});
	console.log("销售属性："+attrArr);
	return attrArr;
}
</script> -->
<!-- 标准sku规格部分 start -->
<!-- 用于保存默认sku规格 -->
<div id="edit-default-sku-spec-div" class="hide">
	<input type="hidden" id="default-sku-spec" value="" />
</div>
<button type="button" class="btn btn-primary" id="edit-default-sku-spec-btn" onclick="javascript:openDefaultSpecModel(0);">编辑默认sku规格</button>
<!-- sku规格对话框 -->
<div class="modal fade" id="sku-spec-modal" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:80%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title" id="">SKU规格</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" id="sku-spec-form">				
					<div class="form-group">
						<div class="col-sm-12">
							<button type="button" class="btn btn-primary" id="add-spec-group-btn">增加规格</button>
							<button type="button" class="btn btn-primary" id="get-spec-btn">获取规格</button>
							<button type="button" class="btn btn-primary" id="set-spec-btn">设置规格</button>
						</div>
					</div>
					<div class="form-group" id="spec-item">
						<!-- 此处为sku规格列表区域 -->
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" id="">关闭</button>
				<button type="button" class="btn btn-primary hide" id="spec-submit-btn">保存</button>
				<button type="button" class="btn btn-primary" id="default-spec-submit-btn" onclick="javascript:saveDefaultSpec(0);">保存默认sku规格</button>
			</div>
		</div>
	</div>
</div>
<!-- 标准sku规格部分 end -->
<!-- 获取sku规格对话框 -->
<div class="modal fade" id="get-sku-spec-modal" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:50%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title" id="">获取SKU规格</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" id="get-sku-spec-form">
					<div class="form-group">				
						<label class="col-sm-2 control-label">规格内容<b style="color:red;">&nbsp;*</b></label>
						<div class="col-sm-10">
							<input type="text" id="get-sku-spec-input" name=""
								class="form-control" placeholder="SKU规格字符串">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" id="">关闭</button>
				<button type="button" class="btn btn-primary" id="get-spec-content-btn">复制到剪贴板</button>
			</div>
		</div>
	</div>
</div>
<!-- 获取sku规格部分 end -->
<!-- 设置sku规格对话框 -->
<div class="modal fade" id="set-sku-spec-modal" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:50%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title" id="">设置SKU规格</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" id="set-sku-spec-form">
					<div class="form-group">				
						<label class="col-sm-2 control-label">规格内容<b style="color:red;">&nbsp;*</b></label>
						<div class="col-sm-10">
							<input type="text" id="set-sku-spec-input" name=""
								class="form-control" placeholder="输入SKU规格字符串">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" id="">关闭</button>
				<button type="button" class="btn btn-primary" id="set-spec-content-btn">生成SKU规格</button>
			</div>
		</div>
	</div>
</div>
<!-- 设置sku规格部分 end -->
<script type="text/javascript">
/**
 * 点击sku规格对话框中的获取规格按钮时执行
 * 		函数功能：打开获取sku规格对话框并把sku规格字符串设置到input中
 */
$("#get-spec-btn").click(function(){
	var skuSpecJson = getSkuSpec();
	if(skuSpecJson!=null && skuSpecJson!=""){
		$("#get-sku-spec-input").val(skuSpecJson);
		$('#get-sku-spec-modal').modal('show');
	}
});
/**
 * 点击获取sku规格对话框中的复制到剪贴板按钮时执行
 * 		函数功能：把input中的sku规格字符串内容copy到剪贴板
 */
$("#get-spec-content-btn").click(function(){
	$("#get-sku-spec-input").focus();
	$("#get-sku-spec-input").select();
	var bool = document.execCommand("copy"); // 执行浏览器复制命令
	if(bool){
		$('#get-sku-spec-modal').modal('hide');
		util.message("已复制到剪贴板！");
	}else{
		util.message("复制到剪贴板失败，请手动复制！");
	}
});
/**
 * 点击sku规格对话框中的设置规格按钮时执行
 * 		函数功能：打开设置sku规格对话框
 */
$("#set-spec-btn").click(function(){
	$("#set-sku-spec-input").val("");
		$('#set-sku-spec-modal').modal('show');
});
/**
 * 点击设置sku规格对话框中的设置sku规格按钮时执行
 * 		函数功能：根据用户输入的sku规格内容生成sku规格
 */
$("#set-spec-content-btn").click(function(){
	var skuSpecJson = $("#set-sku-spec-input").val();
	if(skuSpecJson!=null && skuSpecJson!=""){
		try{
			$("#spec-item").empty();//清空sku规格对话框中的sku规格内容
			var skuSpecObj = $.parseJSON(skuSpecJson);
			var html = "";
			$.each(skuSpecObj, function(index){
				html += createSpecGroup(g_group_num, this);
				g_group_num++;//记录sku规格个数，添加一个sku规格分组后把个数+1
			});
			$("#spec-item").append(html);
			$('#set-sku-spec-modal').modal('hide');
			return false;
		}catch(e){
			 console.log(e.name + ": " + e.message);
			 util.message(e.name + ": " + e.message+"，SKU规格内容格式不正确！");
		}
		
	}else{
		util.message("SKU规格内容不能为空！");
	}
	
});
</script>
<!-- 标准sku规格js部分 -->
<script type="text/javascript">
var g_is_save_sku = false;//是否重新保存sku(skuPage.jsp)

var g_group_num = 0;//记录sku规格个数
var g_sku_id = 0;//sku的ID，>0时，表示是修改，保存时直接根据skuId直接更新数据库；<=0时，表示是添加，保存时在js中保存，添加商品随sku一起添加
var g_sku_index = 0;//记录当前编辑的sku是哪一个

/**
 * 显示打开默认sku规格对话框按钮
 */
function showOpenDefaultSpecBtn(skuId){
	$("#edit-default-sku-spec-btn").removeClass("hide");//显示打开默认sku规格对话框按钮
	$("#edit-default-sku-spec-btn").attr("onclick", "javascript:openDefaultSpecModel("+skuId+");");
	$("#spec-submit-btn").addClass("hide");//隐藏sku规格对话框中保存按钮
	$("#default-spec-submit-btn").removeClass("hide");////显示sku规格对话框中保存默认sku规格按钮
	$("#default-spec-submit-btn").attr("onclick", "javascript:saveDefaultSpec("+skuId+");");
}
/**
 * 隐藏打开默认sku规格对话框按钮
 */
function hideOpenDefaultSpecBtn(skuId){
	$("#edit-default-sku-spec-btn").addClass("hide");//显示打开默认sku规格对话框按钮
	$("#edit-default-sku-spec-btn").attr("onclick", "javascript:openDefaultSpecModel("+skuId+");");
	$("#spec-submit-btn").removeClass("hide");//隐藏sku规格对话框中保存按钮
	$("#default-spec-submit-btn").addClass("hide");////显示sku规格对话框中保存默认sku规格按钮
	$("#default-spec-submit-btn").attr("onclick", "javascript:saveDefaultSpec("+skuId+");");
	$("#default-sku-spec").val("");
}

/**
 * 打开编辑sku规格对话框
 */
function openSpecModel(skuId, index){
	g_group_num=0;//记录sku规格个数，添加一个sku规格分组后把个数+1
	console.log("skuId:"+skuId);
	g_sku_id = skuId;//sku的ID，>0时，表示是修改，保存时直接根据skuId直接更新数据库；<=0时，表示是添加，保存时在js中保存，添加商品随sku一起添加
	g_sku_index = index;//记录当前编辑的sku是哪一个
	$("#spec-item").empty();//清空sku规格对话框中的sku规格内容
	if(skuId<=0){
		//TODO
		//<=0时，表示是添加，不查询数据库中sku规格；保存时在js中保存，添加商品随sku一起添加
		var skuSpec = $("#sku-spec-"+index).val();
		if(skuSpec!=null && skuSpec!=""){
			var skuSpecObj = $.parseJSON(skuSpec);
			var html = "";
			$.each(skuSpecObj, function(index){
				html += createSpecGroup(g_group_num, this);
				g_group_num++;//记录sku规格个数，添加一个sku规格分组后把个数+1
			});
			$("#spec-item").append(html);
		}
	}else{
		//TODO
		//>0时，表示是修改，先查询数据库中sku规格并在对话框中显示；保存时直接根据skuId修改更新数据库
		ajaxGetSkuSpec(skuId);
	}
	$('#sku-spec-modal').modal('show');
}
/**
 * 点击sku对话框中的保存按钮时，保存sku规格
 */
$("#spec-submit-btn").click(function(){
	var skuSpec = getSkuSpec();//获取sku规格信息
	if(!skuSpec){
		return false;
	}
	if(skuSpec==null || skuSpec=="" || skuSpec=="[]"){
		console.log("保存sku规格为空");
	}else{
		if(g_sku_id<=0){//添加sku
			$("#sku-spec-"+g_sku_index).val(skuSpec);
		}else{//编辑sku
			ajaxPostSaveSkuSpec(g_sku_id, skuSpec);
		}
	}
	
	g_group_num=0;
	$('#sku-spec-modal').modal('hide');
});
/**
 * 打开默认sku规格对话框
 */
function openDefaultSpecModel(skuId){
	g_group_num=0;//记录sku规格个数，添加一个sku规格分组后把个数+1
	console.log("skuId:"+skuId);
	$("#spec-item").empty();//清空sku规格对话框中的sku规格内容
	if(skuId<=0){
		var skuSpec = $("#default-sku-spec").val();
		if(skuSpec!=null && skuSpec!=""){
			var skuSpecObj = $.parseJSON(skuSpec);
			var html = "";
			$.each(skuSpecObj, function(index){
				html += createSpecGroup(g_group_num, this);
				g_group_num++;//记录sku规格个数，添加一个sku规格分组后把个数+1
			});
			$("#spec-item").append(html);
		}
		
	}else{
		//TODO
		//>0时，表示是修改，先查询数据库中sku规格并在对话框中显示；保存时直接根据skuId修改更新数据库
		ajaxGetSkuSpec(skuId);
	}
	$('#sku-spec-modal').modal('show');
}
/**
 * 保存默认sku规格
 */
function saveDefaultSpec(skuId){
	var skuSpec = getSkuSpec();//获取sku规格信息
	if(!skuSpec){
		return false;
	}
	if(skuSpec==null || skuSpec=="" || skuSpec=="[]"){
		console.log("保存默认sku规格为空");
	}else{
		if(skuId<=0){//添加sku
			$("#default-sku-spec").val(skuSpec);
		}else{//编辑sku
			ajaxPostSaveSkuSpec(skuId, skuSpec);
		}
	}
	
	g_group_num=0;
	$('#sku-spec-modal').modal('hide');
}

/**
 * ajax请求获取sku规格
 */
function ajaxGetSkuSpec(skuId){
	var url = "back/item/selectSkuSpecBySkuId";
	var params = {"skuId": skuId};
	$.get(url, params, function(res){
		console.log(res);
		if(res!=null){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				var skuSpec = obj.skuSpec;
				if(skuSpec!=null && skuSpec!=""){
					var skuSpecObj = $.parseJSON(skuSpec);
					var html = "";
					$.each(skuSpecObj, function(index){
						html += createSpecGroup(g_group_num, this);
						g_group_num++;//记录sku规格个数，添加一个sku规格分组后把个数+1
					});
					$("#spec-item").append(html);
					return false;
				}else{
					console.log("sku规格为空");
				}
				
			}else{
				util.message("查询失败！");
			}
		}
	});
}
/**
 * ajax请求保存sku规格
 */
function ajaxPostSaveSkuSpec(skuId, skuSpec){
	var url = "back/item/updateSkuSpecBySkuId";
	var params = {"skuId": skuId, "skuSpec": skuSpec};
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				util.message(obj.result_msg);
			}else{
				util.message(obj.result_err_msg);
			}
		}
	});
}
/**
 * 设置默认sku规格保存时html
 */
/* function setDefaultSkuSpecHtml(){
	var html = "<input type='hidden' id='sku-spec-0' value='' />";
	$("#edit-default-sku-spec-div").html(html);
} */
/**
 * 移除默认sku规格保存html
 */
/* function removeDefaultSkuSpecHtml(){
	$("#edit-default-sku-spec-div").html("");
} */
/**
 * 添加规格分组
 */
$("#add-spec-group-btn").click(function(){
	var html = createSpecGroup(g_group_num, null);
	g_group_num++;//记录sku规格个数，添加一个sku规格分组后把个数+1
	console.log(html);
	$("#spec-item").append(html);
	//addSpec(g_group_num+1);
});
/**
 * 创建sku规格分组html字符串
 */
function createSpecGroup(groupNum, skuSpec){
	/* [
			{
				"spec_group":"主体",
				"spec":[
							{"spec_key":"品牌",	"spec_val":"apple"},
							{"spec_key":"型号",	"spec_val":"a9"}
						]
						
			},
			{
				"spec_group":"基本信息",
				"spec":[
							{"spec_key":"机身长度（mm）",	"spec_val":"151.9"},
							{"spec_key":"机身重量（g）",	"spec_val":"175"}
						]
						
			}
	] */
	var specHtml = "";
	var spec_group = "";
	var spec = null;
	if(skuSpec==null){
		specHtml = createSpecHtml(null);
	}else{
		spec_group = skuSpec.spec_group;
		spec = skuSpec.spec;
		if(spec!=null){
			for(var i=0; i<spec.length; i++){
				specHtml += createSpecHtml(spec[i]);
			}
		}
	}
	var random = Math.floor(Math.random()*89999)+1000;
	var spec_group_id = guid()+"-"+random;
	var html = ''
		+ '<div class="col-sm-4 spec-group-div" id="'+spec_group_id+'">'
		+ '<div class="panel panel-default">'
		+ '<div class="panel-heading">'
		+ '<h3 class="panel-title">'
		+ '<input type="text" id="input-spec-group-'+spec_group_id+'" value="'+spec_group+'" placeholder="规格分组名称" >'
		+ '&nbsp;<button type="button" title="增加规格" onclick="javascript:addSpec(&quot;'+spec_group_id+'&quot;);">+</button>'
		+ '&nbsp;<button type="button" title="删除规格分组" onclick="javascript:removeSpecGroup(&quot;'+spec_group_id+'&quot;);">x</button>'
		+ '</h3>'
		+ '</div>'
		+ '<div class="panel-body spec-div" id="group-value-'+spec_group_id+'">'
		+ specHtml
		+ '</div>'
		+ '</div>'
		+ '</div>';
	return html;
}
/**
 * 移除sku规格分组html字符串
 */
function removeSpecGroup(groupId){
	$("#"+groupId).remove();
}
/**
 * 添加sku规格html字符串
 */
function addSpec(groupId){
	var html = createSpecHtml(null);
	console.log(html);
	$("#group-value-"+groupId).append(html);
}
/**
 * 创建sku规格html字符串
 */
function createSpecHtml(spec){
	var spec_key = "";
	var spec_val = "";
	if(spec!=null){
		spec_key = spec.spec_key;
		spec_val = spec.spec_val;
	}
	var time = new Date().getTime();
	var random = Math.floor(Math.random()*89999)+1000;
	var spec_item_id = guid();
	var html = ''
		+ '<div class="input-spec-item" id="'+spec_item_id+'">'
		+ '<div class="col-sm-5">'
		+ '<input type="text" id="input-spec-key-'+spec_item_id+'" value="'+spec_key+'" placeholder="规格属性" size="15" >'
		+ '</div>'
		+ '<div class="col-sm-5">'
		+ '<input type="text" id="input-spec-val-'+spec_item_id+'" value="'+spec_val+'" placeholder="规格属性值" size="15" >'
		+ '</div>'
		+ '<div class="col-sm-2">'
		+ '<button type="button" title="删除规格" onclick="javascript:removeSpec(&quot;'+spec_item_id+'&quot;);">x</button>'
		+ '</div>'
		+ '<br><br>'
		+ '</div>';
	return html;
}
//用于生成uuid
function S4() {
    return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}
function guid() {
    return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
}
/**
 * 移除sku规格html字符串
 */
function removeSpec(id){
	$("#"+id).remove();
}
/**
 * 获取sku规格
 */
function getSkuSpec(){
	var isReturn = true;
	var spec_group_Arr = new Array();
	$("#spec-item .spec-group-div").each(function(index){
		//var spec_group = $("#input-spec-group-"+index).val();
		var spec_group_id = $(this).attr("id");
		console.log("spec_group_id:"+spec_group_id);
		var spec_group = $("#input-spec-group-"+spec_group_id).val();
		console.log("spec_group id:"+spec_group);
		console.log("第 "+index+" 个规格分组的分组名称:"+spec_group);
		if(spec_group==null || spec_group==""){
			util.message("第 "+index+" 个规格分组的分组名称不能为空!");
			isReturn = false;
			return false;
		}
		var specIsReturn = false;
		var spec_arr = new Array();
		$(this).find(".spec-div").find(".input-spec-item").each(function(currIndex){
			var randomId = $(this).attr("id");
			console.log("spec id:"+randomId);
			var spec_key = $("#input-spec-key-"+randomId).val();
			console.log("第 "+index+" 个规格分组的第 "+currIndex+" 个规格属性:"+spec_key);
			if(spec_key==null || spec_key==""){
				util.message("第 "+index+" 个规格分组的第 "+currIndex+" 个规格属性不能为空!");
				isReturn = false;
				specIsReturn = true;
				return false;
			}
			var spec_val = $("#input-spec-val-"+randomId).val();
			console.log("第 "+index+" 个规格分组的第 "+currIndex+" 个规格属性值:"+spec_val);
			if(spec_val==null || spec_val==""){
				util.message("第 "+index+" 个规格分组的第 "+currIndex+" 个规格属性值不能为空!");
				isReturn = false;
				specIsReturn = true;
				return false;
			}
			var spec_obj = new Object();
			spec_obj.spec_key = spec_key;
			spec_obj.spec_val = spec_val;
			spec_arr.push(spec_obj);
		});
		if(specIsReturn){
			return false;
		}
		var spec_group_obj = new Object();
		spec_group_obj.spec_group = spec_group;
		spec_group_obj.spec = spec_arr;
		spec_group_Arr.push(spec_group_obj);
		console.log("spec_group_Arr:"+JSON.stringify(spec_group_Arr));
	});
	if(!isReturn){
		return false
	}
	var specJson = JSON.stringify(spec_group_Arr);
	console.log("sku规格:"+specJson);
	return specJson;
}
</script>
<!-- 标准sku规格js部分 end -->

<!-- 标准sku简单规格部分 start -->
<!-- 用于保存默认sku规格 -->
<div id="edit-default-sku-short-spec-div" class="hide">
	<input type="hidden" id="default-sku-short-spec" value="" />
</div>
<button type="button" class="btn btn-primary" id="edit-default-sku-short-spec-btn" onclick="javascript:openDefaultShortSpecModel(0);">编辑默认参数</button>
<!-- 简单sku规格对话框 -->
<div class="modal fade" id="sku-short-spec-modal" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:50%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title" id="">默认参数</h4>
			</div>
			<div class="modal-body">
				<div class="row clearfix">
					<div class="col-md-12 column">
						<form class="form-horizontal" id="sku-short-spec-form">				
							<div class="form-group">
								<div class="col-sm-12">
									<!-- <label class="col-sm-2 control-label">简单SKU规格<b style="color:red;">&nbsp;*</b></label> -->
									<div class="col-sm-12">
										<textarea class="form-control" id="sku-short-spec-area" rows="3" cols="" placeholder="默认参数（必填）"></textarea>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" id="">关闭</button>
				<button type="button" class="btn btn-primary hide" id="short-spec-submit-btn">保存</button>
				<button type="button" class="btn btn-primary" id="default-short-spec-submit-btn" onclick="javascript:saveDefaultShortSpec(0);">保存默认参数</button>
			</div>
		</div>
	</div>
</div>
<!-- 简单sku简单部分 end -->
<!-- 简单sku规格js部分 -->
<script type="text/javascript">

/**
 * 显示打开默认的简单sku规格对话框按钮
 */
function showOpenDefaultShortSpecBtn(skuId){
	$("#edit-default-sku-short-spec-btn").removeClass("hide");//显示打开默认的简单sku规格对话框按钮
	$("#edit-default-sku-short-spec-btn").attr("onclick", "javascript:openDefaultShortSpecModel("+skuId+");");
	$("#short-spec-submit-btn").addClass("hide");//隐藏简单sku规格对话框中保存按钮
	$("#default-short-spec-submit-btn").removeClass("hide");////显示简单sku规格对话框中保存默认简单sku规格按钮
	$("#default-short-spec-submit-btn").attr("onclick", "javascript:saveDefaultShortSpec("+skuId+");");
}
/**
 * 隐藏打开默认的简单sku规格对话框按钮
 */
function hideOpenDefaultShortSpecBtn(skuId){
	$("#edit-default-sku-short-spec-btn").addClass("hide");//显示打开默认的简单sku规格对话框按钮
	$("#edit-default-sku-short-spec-btn").attr("onclick", "javascript:openDefaultShortSpecModel("+skuId+");");
	$("#short-spec-submit-btn").removeClass("hide");//隐藏简单sku规格对话框中保存按钮
	$("#default-short-spec-submit-btn").addClass("hide");////显示简单sku规格对话框中保存默认简单sku规格按钮
	$("#default-short-spec-submit-btn").attr("onclick", "javascript:saveDefaultShortSpec("+skuId+");");
	$("#default-sku-short-spec").val("");
}

/**
 * 打开编辑简单sku规格对话框
 */
function openShortSpecModel(skuId, index){
	g_sku_id = skuId;//sku的ID，>0时，表示是修改，保存时直接根据skuId直接更新数据库；<=0时，表示是添加，保存时在js中保存，添加商品随sku一起添加
	g_sku_index = index;//记录当前编辑的sku是哪一个
	$("#sku-short-spec-area").val("");//清空简单sku规格对话框中的规格内容
	if(skuId<=0){
		//TODO
		//<=0时，表示是添加，不查询数据库中sku规格；保存时在js中保存，添加商品随sku一起添加
		var skuShortSpec = $("#sku-short-spec-"+index).val();
		if(skuShortSpec!=null && skuShortSpec!=""){
			$("#sku-short-spec-area").val(skuShortSpec);
		}
	}else{
		//TODO
		//>0时，表示是修改，先查询数据库中sku规格并在对话框中显示；保存时直接根据skuId修改更新数据库
		ajaxGetSkuShortSpec(skuId);
	}
	$('#sku-short-spec-modal').modal('show');
}
/**
 * 点击sku对话框中的保存按钮时，保存sku规格
 */
$("#short-spec-submit-btn").click(function(){
	var skuShortSpec = $("#sku-short-spec-area").val();//获取sku简单规格信息
	if(skuShortSpec==null || skuShortSpec==""){
		util.message("简单SKU规格不能为空！");
		return false;
	}
	if(skuShortSpec.length>500){
		util.message("简单SKU规格内容不能大于500个字符！");
		return false;
	}
	
	if(g_sku_id<=0){//添加sku
		$("#sku-short-spec-"+g_sku_index).val(skuShortSpec);
	}else{//编辑sku
		$("#sku-short-spec-"+g_sku_index).val(skuShortSpec);
		ajaxPostSaveSkuShortSpec(g_sku_id, skuShortSpec);
	}
	
	$('#sku-short-spec-modal').modal('hide');
});
/**
 * 打开默认sku简单规格对话框
 */
function openDefaultShortSpecModel(skuId){
	console.log("skuId:"+skuId);
	$("#sku-short-spec-area").val("");//清空简单sku规格对话框中的简单规格内容
	if(skuId<=0){
		var skuShortSpec = $("#default-sku-short-spec").val();
		if(skuShortSpec!=null && skuShortSpec!=""){
			$("#sku-short-spec-area").val(skuShortSpec);
		}
	}else{
		//TODO
		//>0时，表示是修改，先查询数据库中sku规格并在对话框中显示；保存时直接根据skuId修改更新数据库
		ajaxGetSkuShortSpec(skuId);
	}
	$('#sku-short-spec-modal').modal('show');
}
/**
 * 保存默认sku简单规格
 */
function saveDefaultShortSpec(skuId){
	var skuShortSpec = $("#sku-short-spec-area").val();//获取sku简单规格信息
	if(skuShortSpec==null || skuShortSpec==""){
		util.message("简单SKU规格不能为空！");
		return false;
	}
	if(skuShortSpec.length>500){
		util.message("简单SKU规格内容不能大于500个字符！");
		return false;
	}
	
	if(skuId<=0){//添加sku
		$("#default-sku-short-spec").val(skuShortSpec);
	}else{//编辑sku
		ajaxPostSaveSkuShortSpec(skuId, skuShortSpec);
	}
	
	$('#sku-short-spec-modal').modal('hide');
}

/**
 * ajax请求获取简单sku规格
 */
function ajaxGetSkuShortSpec(skuId){
	var url = "back/item/selectSkuShortSpecBySkuId";
	var params = {"skuId": skuId};
	$.get(url, params, function(res){
		console.log(res);
		if(res!=null){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				var skuShortSpec = obj.skuShortSpec;
				if(skuShortSpec!=null && skuShortSpec!=""){
					$("#sku-short-spec-area").val(skuShortSpec);
					return false;
				}else{
					console.log("简单sku规格为空");
				}
				
			}else{
				util.message("查询失败！");
			}
		}
	});
}
/**
 * ajax请求保存简单sku规格
 */
function ajaxPostSaveSkuShortSpec(skuId, skuShortSpec){
	var url = "back/item/updateSkuShortSpecBySkuId";
	var params = {"skuId": skuId, "skuShortSpec": skuShortSpec};
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				util.message(obj.result_msg);
			}else{
				util.message(obj.result_err_msg);
			}
		}
	});
}
</script>
<!-- 简单sku规格js部分 end -->

