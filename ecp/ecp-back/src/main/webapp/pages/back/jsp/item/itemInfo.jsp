<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- 此页面未用 -->

<div class=" fieldset" id="item-attr">
	<div class=" legend" onclick="javascript:alert(getItemAttr());">商品属性</div>
	<div id="item-attr-body">
		<%-- <%@ include file="attrPage.jsp"%> --%>
		<div class="form-group">
			<label class="col-sm-2 control-label">品牌</label>
			<div class="col-sm-10">
				<select class="form-control" id="brand" name="brand">
					<option value="0">请选择</option>
					<c:forEach items="${brandList}" var="brand">
						<option value="${brand.brandId}">${brand.brandName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<c:forEach items="${attrList}" var="attr">
			<div class="form-group">
				<label class="col-sm-2 control-label">${attr.attr_name}</label>
				<div class="col-sm-10">
					<c:forEach items="${attr.valList}" var="val">
						<label><input type="checkbox" id="" name="itemAttr" value="${attr.attr_id}:${val.value_id}" />&nbsp;${val.value_name}</label>&nbsp;&nbsp;&nbsp;
					</c:forEach>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
<br>
<div class=" fieldset" id="sale-attr">
	<div class=" legend" onclick="javascript:alert(getSaleAttr());">销售属性</div>
	<div id="sale-attr-body">
		<c:forEach items="${attrList}" var="attr">
			<c:if test="${attr.attr_type==4}">
				<div class="form-group">
					<label class="col-sm-2 control-label">${attr.attr_name}</label>
					<div class="col-sm-10">
						<c:forEach items="${attr.valList}" var="val">
							<label><input type="checkbox" id="" name="saleAttr" value="${attr.attr_id}:${val.value_id}" />&nbsp;${val.value_name}</label>&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
</div>
<script>
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
</script>