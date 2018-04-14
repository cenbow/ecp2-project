<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>
	/* biaoti */
	.fieldset {
		position: relative;
		border: 1px solid #ccc;
		padding: 10px;
		margin: 10px 0;
	}
	
	.fieldset .legend {
		position: absolute;
		left: 80px;
		top: -15px;
		padding: 0 10px;
		background: #fff;
		font-weight: 700;
		font-size: 18px;
	}
</style>

<%-- <div class=" fieldset" id="category-brand">
	<div class=" legend">品牌</div>
	<div id="brand-body">
		<div class="form-group">
			<label class="col-sm-2 control-label">品牌</label>
			<div class="col-sm-10">
				<select class="form-control" id="brand" name="brand">
					<option value="0">请选择</option>
					<c:forEach items="${brandMapList}" var="brandMap">
						<option value="${brandMap.brand_id}">${brandMap.brand_name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
</div>
<br> --%>

<div class=" fieldset" id="item-attr">
	<div class=" legend" onclick="javascript:alert(getItemAttr());">商品属性</div>
	<div id="item-attr-body">
		<%-- <c:if test="${not empty brandMapList}">
			<div class="form-group">
				<label class="col-sm-2 control-label">品牌</label>
				<div class="col-sm-10">
					<select class="form-control" id="brand" name="brand">
						<option value="0">请选择</option>
						<c:forEach items="${brandMapList}" var="brandMap">
							<option value="${brandMap.brand_id}">${brandMap.brand_name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</c:if> --%>
		
		<c:forEach items="${attrList}" var="attr">
			<c:if test="${not empty attr.valList}">
				<c:if test="${attr.attr_type==1}"><!-- 1=关键属性;2=不可变属性;3=可变属性;4=销售属性 -->
					<div class="form-group">
						<label class="col-sm-2 control-label">${attr.attr_name}</label>
						<div class="col-sm-10">
							<c:forEach items="${attr.valList}" var="val">
								<label><input type="checkbox" id="" name="itemAttr" value="${attr.attr_id}:${val.value_id}" />&nbsp;${val.value_name}</label>&nbsp;&nbsp;&nbsp;
							</c:forEach>
						</div>
					</div>
				</c:if>
			</c:if>
		</c:forEach>
	</div>
</div>
<br>

<div class=" fieldset" id="sale-attr">
	<div class=" legend" onclick="javascript:alert(getSaleAttr());">销售属性</div>
	<div id="sale-attr-body">
		<c:forEach items="${attrList}" var="attr">
			<c:if test="${attr.attr_type==4}"><!-- 1=关键属性;2=不可变属性;3=可变属性;4=销售属性 -->
				<div class="form-group" id="attr-${attr.attr_id}">
					<!-- 属性ID -->
					<input type="hidden" id="attr-id-${attr.attr_id}" name="attrId" value="${attr.attr_id}" />
					<!-- 属性名称 -->
					<input type="hidden" id="attr-name-${attr.attr_id}" value="${attr.attr_name}" />
					
					<label class="col-sm-2 control-label">${attr.attr_name}</label>
					<div class="col-sm-10">
						<c:forEach items="${attr.valList}" var="val">
							<label>
								<!-- 属性值ID -->
								<input type="hidden" id="attr-val-id-${val.value_id}" value="${val.value_id}" />
								<!-- 属性值名称 -->
								<input type="hidden" id="attr-val-name-${val.value_id}" value="${val.value_name}" />
								
								<input type="checkbox" id="" name="attrValId" onclick="javascript:createSku();" value="${val.value_id}" />
								&nbsp;${val.value_name}
							</label>
							&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
</div>
<br>

<div id="sku-div">
	<%@ include file="skuPage.jsp"%>
</div>

<script>
/* 如果商品属性为空时不显示标题 */
var itemAttrBody = $("#item-attr-body").html().trim();
console.log("itemAttrBody:"+itemAttrBody);
if(itemAttrBody==null || itemAttrBody==""){
	$("#item-attr").remove();
}
/* 如果销售属性为空时不显示标题，且不显示sku标题 */
var saleAttrBody = $("#sale-attr-body").html().trim();
console.log("saleAttrBody:"+saleAttrBody);
if(saleAttrBody==null || saleAttrBody==""){
	$("#sale-attr").remove();
	$("#sku").remove();
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
	
	/* 获取所有属性的ID */
	var attrIds = new Array();
	$("#sale-attr-body input[name='attrId']").each(function(){
		attrIds.push($(this).val());
	});
	console.log("销售属性id:"+attrIds);
	/* 遍历并获取每个属性的ID */
	for(var i=0; i<attrIds.length; i++){
		//console.log("属性第"+i+"次循环");
		var attrId = attrIds[i];//属性的ID
		var attrName = $("#attr-name-"+attrId).val();
		
		/* 获取所有属性值的ID */
		$("#attr-"+attrId+" input[name='attrValId']:checked").each(function(){
			attrArr.push(attrId+":"+$(this).val());
		});
	}
	console.log("销售属性:"+attrArr);
	return attrArr;
}

/*
 * 创建sku
 */
function createSku(){
	
	var sale_attr_arr = getSaleAttr();
	if(sale_attr_arr==null || sale_attr_arr.length<=0){
		showOpenDefaultSpecBtn(0);
		showOpenDefaultShortSpecBtn(0);
	}else{
		hideOpenDefaultSpecBtn(0);
		hideOpenDefaultShortSpecBtn(0);
	}
	
	g_is_save_sku = true;//是否保存sku(skuPage.jsp)
	//debugger;
	/* 获取所有属性的ID */
	var attrIds = new Array();
	$("#sale-attr-body input[name='attrId']").each(function(){
		attrIds.push($(this).val());
	});
	//console.log("attrIds:"+attrIds);
	var arr = new Array();
	//var attrs = new Array();
	/* 遍历并获取每个属性的ID */
	for(var i=0; i<attrIds.length; i++){
		//console.log("属性第"+i+"次循环");
		var attrId = attrIds[i];//属性的ID
		var attrName = $("#attr-name-"+attrId).val();
		
		/* 获取所有属性值的ID */
		var attrValIds = new Array();
		$("#attr-"+attrId+" input[name='attrValId']:checked").each(function(){
			attrValIds.push($(this).val());
		});
		console.log(attrName+":"+attrValIds);
		var attrVals = new Array();
		/* 遍历并获取每个属性值的ID */
		for(var n=0; n<attrValIds.length; n++){
			//console.log("属性值第"+i+"次循环");
			var arrValId = attrValIds[n];//属性的ID
			var arrValName = $("#attr-val-name-"+arrValId).val();
			var obj = new Object();
			obj.arrValId = arrValId;
			obj.arrValName = arrValName;
			attrVals.push(obj);
		}
		//console.log("attrVals:"+JSON.stringify(attrVals));
		var obj = new Object();
		obj.attrId = attrId;
		obj.attrName = attrName;
		obj.attrVals = attrVals;
		arr.push(obj);
	}
	console.log("arr:"+JSON.stringify(arr));
	
	
	var attrvalArr = new Array();//用于保存获取笛卡尔乘积的集合
	var tab_heads = new Array();//用于保存表格head内容
	//debugger;
	for(var j=0; j<arr.length; j++){
		var obj = arr[j];
		if(obj.attrVals.length>0){
			attrvalArr.push(obj.attrVals);
			tab_heads.push(obj);
		}
		delete obj.attrVals;
	}
	console.log("笛卡尔积："+JSON.stringify(attrvalArr));
	var tab_bodys = multiCartesian(attrvalArr);
	console.log("tab_heads:"+JSON.stringify(tab_heads));
	console.log("tab_bodys:"+JSON.stringify(tab_bodys));
	
	var headHtml = createHeadHtml(tab_heads);
	console.log("headHtml:"+JSON.stringify(headHtml));
	var bodyHtml = createBodyHtml(tab_bodys);
	console.log("bodyHtml:"+JSON.stringify(bodyHtml));
	$("#sku-head").html(headHtml);
	$("#sku-body").html(bodyHtml);
	
	
	
	
	
	/* var htmlHead = "<tr>";
	var htmlBody = "<tr>";
	
	htmlHead += "</tr>";
	htmlBody += "</tr>";
	$("#sku-head").html(createHeadHtml(arr));
	$("#sku-body").html(createBodyHtml(arr)); */
}

function createHeadHtml(arr){
	var htmlHead = "<tr>";
	for(var i=0; i<arr.length; i++){
		htmlHead += "<th id='"+arr[i].attrId+"'>"
		+"<input type='hidden' id='head-"+arr[i].attrId+"' name='saleAttrHead' value='"+arr[i].attrId+"' />"
		+arr[i].attrName
		+"</th>";
	}
	/* htmlHead += "<th id=''>硬成本价格</th>";
	htmlHead += "<th id=''>成本价格</th>"; */
	htmlHead += "<th id=''>预算价格</th>";
	htmlHead += "<th id=''>最高价格</th>";
	htmlHead += "<th id=''>最低价格</th>";
	htmlHead += "<th id=''>商城价格</th>";
	htmlHead += "<th id=''>体积</th>";
	htmlHead += "<th id=''>重量</th>";
	htmlHead += "<th id=''>简单规格</th>";
	htmlHead += "<th id=''>标准规格</th>";
	htmlHead += "</tr>";
	return htmlHead;
}
function createBodyHtml(arr){
	var guidePrice = $("#guide-price").val();//商城指导价格，预算价格
	var highestPrice = $("#highest-price").val();//最高价格
	var lowestPrice = $("#lowest-price").val();//最低价格
	var marketPrice = $("#market-price").val();//市场价格，商城价格
	/* var marketPrice2 = $("#market-price2").val();//成本价格
	var hardCostPrice = $("#hard-cost-price").val();//硬成本价格 */
	var volume = $("#volume").val();//商品体积
	var weight = $("#weight").val();//商品毛重
	var checkInput = "onkeydown='javascript:var keynum = window.event ? event.keyCode : event.which;if((keynum>=48 && keynum<=57) || (keynum>=96 && keynum<=105) || (keynum>=37 && keynum<=40) || keynum==110 || keynum==190 || keynum==8 || keynum==9 || keynum==46){return true;}return false;'";
	var htmlBody = ""
	for(var i=0; i<arr.length; i++){
		//$("#sku-body").append(createBodyHtml(tab_bodys));
		var tds = arr[i];
		htmlBody += "<tr>";
		for(var j=0; j<tds.length; j++){
			htmlBody += "<td id='"+tds[j].arrValId+"'>"
			+"<input type='hidden' id='body-"+tds[j].arrValId+"' name='saleAttrBody' value='"+tds[j].arrValId+"' />"
			+tds[j].arrValName
			+"</td>";
		}
		/* htmlBody += "<td id=''>"
		htmlBody += "<input type='hidden' id='sku-price-id-"+i+"' value='' />"
		htmlBody += "<input type='text' id='hard-cost-price-"+i+"' "+checkInput+" onkeyup='javascript:checkInput(this, event);' maxlength='10' value='"+hardCostPrice+"' style='width:80px;' placeholder='硬成本价格' />"
		htmlBody += "</td>";
		htmlBody += "<td id=''>"
		//htmlBody += "<input type='hidden' id='sku-price-id-"+i+"' value='' />"
		htmlBody += "<input type='text' id='cost-price-"+i+"' "+checkInput+" onkeyup='javascript:checkInput(this, event);' maxlength='10' value='"+marketPrice2+"' style='width:80px;' placeholder='成本价' />"
		htmlBody += "</td>"; */
		htmlBody += "<td id=''>"
		htmlBody += "<input type='hidden' id='sku-price-id-"+i+"' value='' />"
		htmlBody += "<input type='text' id='market-price-"+i+"' "+checkInput+" onkeyup='javascript:checkInput(this, event);' maxlength='10' value='"+guidePrice+"' style='width:80px;' placeholder='预算价格' />"
		htmlBody += "</td>";
		htmlBody += "<td id=''>"
		htmlBody += "<input type='text' id='highest-price-"+i+"' "+checkInput+" onkeyup='javascript:checkInput(this, event);' maxlength='10' value='"+highestPrice+"' style='width:80px;' placeholder='最高价格' />"
		htmlBody += "</td>";
		htmlBody += "<td id=''>"
		htmlBody += "<input type='text' id='lowest-price-"+i+"' "+checkInput+" onkeyup='javascript:checkInput(this, event);' maxlength='10' value='"+lowestPrice+"' style='width:80px;' placeholder='最低价格' />"
		htmlBody += "</td>";
		htmlBody += "<td id=''>"
		htmlBody += "<input type='text' id='sell-price-"+i+"' "+checkInput+" onkeyup='javascript:checkInput(this, event);' maxlength='10' value='"+marketPrice+"' style='width:80px;' placeholder='商城价格' />"
		htmlBody += "</td>";
		htmlBody += "<td id=''>"
		htmlBody += "<input type='hidden' id='sku-id-"+i+"' value='' />"
		htmlBody += "<input type='text' id='volume-"+i+"' "+checkInput+" onkeyup='javascript:checkInput(this, event);' maxlength='10' value='"+volume+"' style='width:80px;' placeholder='体积' />"
		htmlBody += "</td>";
		htmlBody += "<td id=''>"
		htmlBody += "<input type='text' id='weight-"+i+"' "+checkInput+" onkeyup='javascript:checkInput(this, event);' maxlength='10' value='"+weight+"' style='width:80px;' placeholder='重量' />"
		htmlBody += "</td>";
		htmlBody += "<td id=''>"
		htmlBody += "<input type='hidden' id='sku-short-spec-"+i+"' value='' />"
		htmlBody += "<button type='button' id='short-spec-"+i+"' onclick='javascript:openShortSpecModel(0, "+i+");'>编辑</button>"
		htmlBody += "</td>";
		htmlBody += "<td id=''>"
		htmlBody += "<input type='hidden' id='sku-spec-"+i+"' value='' />"
		htmlBody += "<button type='button' id='spec-"+i+"' onclick='javascript:openSpecModel(0, "+i+");'>编辑</button>"
		htmlBody += "</td>";
		htmlBody += "</tr>";
	}
	
	return htmlBody;
}

/**
 * 检查输入的字符
 */
function checkInput(obj, e){
	var realkey = String.fromCharCode(e.which);
	console.log(e.keyCode+","+realkey);
	var val = obj.value;
	var patrn = /^([\d]+|([\d]+[.]?|[\d]+[.]?[\d]+))$/;//第一位不为.的整数或小数
	if(patrn.test(val)){
		return true;
	}
	obj.value = "";
}

function createHtmlTd(currArr){
	var htmlBody = "";
	for(var n=0; n<currArr.length; n++){
		if(n==0){
			htmlBody += "<tr>";
		}
		console.log(JSON.stringify("key:"+currArr[n].key+", text:"+currArr[n].valText));
		htmlBody += "<td>"+currArr[n].valText+"</td>";
	}
	return htmlBody;
}

/**
 * 获取SKU信息
 */
function getSkuInfo(){
	var sku = new Array();//sku集合
	var skuPrice = new Array();//sku价格集合
	//获取sku head
	var attrIds = new Array();
	$("#sku-table #sku-head tr input[name='saleAttrHead']").each(function(){
		attrIds.push($(this).val());
	});
	console.log("attrIds:"+attrIds);
	var isReturn = false;
	//获取sku body tr
	$("#sku-table #sku-body tr").each(function(index){
		//获取sku body attrVal
		var attributes = "";
		//$("#sku-table #sku-body tr input[name='saleAttrBody']").each(function(i){
			$(this).find("input[name='saleAttrBody']").each(function(i){
			console.log("coll 第n次循环："+i);
			var attrId = attrIds[i];
			var attrValId = $(this).val();
			attributes += attrId+":"+attrValId+",";
		});
		attributes = attributes.substring(0, attributes.length-1);
		var skuId = $("#sku-id-"+index).val();//skuId
		var skuSpec = $("#sku-spec-"+index).val();//标准sku规格
		var skuShortSpec = $("#sku-short-spec-"+index).val();//简单sku规格
		/* var skuPriceId = $("#sku-price-id-"+index).val();//skuPriceId
		var hardCostPrice = $("#hard-cost-price-"+index).val();//硬成本价格
		if(hardCostPrice==null || hardCostPrice==""){
			util.message("第 "+index+" 行硬成本价格不能为空!");
			isReturn = true;
			return false;
		}
		var costPrice = $("#cost-price-"+index).val();//成本价
		if(costPrice==null || costPrice==""){
			util.message("第 "+index+" 行成本价不能为空!");
			isReturn = true;
			return false;
		}
		var marketPrice = $("#market-price-"+index).val();//指导价
		if(marketPrice==null || marketPrice==""){
			util.message("第 "+index+" 行预算价格不能为空!");
			isReturn = true;
			return false;
		}
		var highestPrice = $("#highest-price-"+index).val();//最高价格
		if(highestPrice==null || highestPrice==""){
			util.message("第 "+index+" 行最高价格不能为空!");
			isReturn = true;
			return false;
		}
		var lowestPrice = $("#lowest-price-"+index).val();//最低价格
		if(lowestPrice==null || lowestPrice==""){
			util.message("第 "+index+" 行最低价格不能为空!");
			isReturn = true;
			return false;
		}
		var sellPrice = $("#sell-price-"+index).val();//销售价
		if(sellPrice==null || sellPrice==""){
			util.message("第 "+index+" 行商城价格不能为空!");
			isReturn = true;
			return false;
		} */
		var volume = $("#volume-"+index).val();//体积
		if(volume==null || volume==""){
			util.message("第 "+index+" 行体积不能为空!");
			isReturn = true;
			return false;
		}
		var weight = $("#weight-"+index).val();//重量
		if(weight==null || weight==""){
			util.message("第 "+index+" 行重量不能为空!");
			isReturn = true;
			return false;
		}
		
		//判断简单sku规格不能为空
		if(skuShortSpec==null || skuShortSpec==""){
			util.message("第 "+index+" 行简单SKU规格不能为空!");
			isReturn = true;
			return false;
		}
		
		var skuO = new Object();
		skuO.skuId = skuId;
		skuO.attributes = attributes;
		skuO.volume = volume;
		skuO.weight = weight;
		skuO.skuSpec = skuSpec;
		skuO.skuShortSpec = skuShortSpec;
		sku.push(skuO);
		
		/* var skuPriceO = new Object();
		skuPriceO.id = skuPriceId;
		skuPriceO.hardCostPrice = hardCostPrice;
		skuPriceO.costPrice = costPrice;
		skuPriceO.marketPrice = marketPrice;
		skuPriceO.highestPrice = highestPrice;
		skuPriceO.lowestPrice = lowestPrice;
		skuPriceO.sellPrice = sellPrice;
		skuPrice.push(skuPriceO); */
	});
	if(isReturn){
		return false;
	}
	console.log("sku:"+JSON.stringify(sku));
	console.log("sku price:"+JSON.stringify(skuPrice));
	var obj = new Object();
	obj.sku = sku;
	obj.skuPrice = skuPrice;
	return obj;
}





	(function() {
		dwn = function(a) {
			document.writeln(a + "<br />")
		};
		//笛卡尔积  
		var Cartesian = function(a, b) {
			var ret = [];
			for (var i = 0; i < a.length; i++) {
				for (var j = 0; j < b.length; j++) {
					ret.push(ft(a[i], b[j]));
				}
			}
			return ret;
		}
		var ft = function(a, b) {
			/* if (!(a instanceof Array))
				a = [ a ];
			var ret = Array.call(null, a);
			ret.push(b); */
			var ret = new Array();  
			ret.push(a);  
			ret.push(b);
			return ret;
		}
		//多个一起做笛卡尔积  
		multiCartesian = function(data) {
			var len = data.length;
			if (len == 0){
				return [];
			}else if (len == 1){
				//return data[0];
				var arr = new Array();
				var tempData = data[0];
				var length = tempData.length;
				for(var j=0; j<length; j++){
					var temp = new Array();
					temp.push(tempData[j]);
					arr.push(temp);
				}
				return arr;
			}else {
				var r = data[0];
				for (var i = 1; i < len; i++) {
					r = Cartesian(r, data[i]);
				}
				return r;
			}
		}
	})();
</script>