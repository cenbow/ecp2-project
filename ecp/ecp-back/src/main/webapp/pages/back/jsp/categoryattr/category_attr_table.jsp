<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="container-fluid" style="margin-top: 20px;">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						<button type="button" id="add-btn" class="btn btn-default btn-primary" onclick="javascript:addCategoryAttr();">添加属性</button>
					</h3>
				</div>
				<div class="panel-body">
					<table class="table table-striped table-bordered table-hover "
						id="category-attr-table" style="width: 100%;"center">
						<thead style="width: 98%; padding-top: 80px;">
							<tr>
								<th>
									<input type="checkbox" name="checkbox" id="attr-all-checkbox" onclick="javascript:checkAttrAll(this);">
									<label for="attr-all-checkbox">ALL</label>
								</th>
								<th>ID</th>
								<th>属性名称</th>
								<th>类型</th>
								<th>是否必填</th>
								<th>是否多选</th>
								<th>排序号</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pagehelper.list}" var="categoryAttr">
								<tr>
									<td>
										<input type="checkbox" name="checkbox2" id="attr-td-${categoryAttr.attr_id}" onclick="javascript:checkAttrOne();" value="${categoryAttr.attr_id}">
									</td>
									<td>${categoryAttr.attr_id}</td>
									<td id="attr-name-${categoryAttr.attr_id}">${categoryAttr.attr_name}</td>
									<td>
										<c:if test="${empty categoryAttr.attr_type || categoryAttr.attr_type==null || categoryAttr.attr_type==''}">
											未知
										</c:if>
										<c:if test="${not empty categoryAttr.attr_type && categoryAttr.attr_type==1}">
											关键属性
										</c:if>
										<c:if test="${not empty categoryAttr.attr_type && categoryAttr.attr_type==2}">
											不可变属性
										</c:if>
										<c:if test="${not empty categoryAttr.attr_type && categoryAttr.attr_type==3}">
											可变属性
										</c:if>
										<c:if test="${not empty categoryAttr.attr_type && categoryAttr.attr_type==4}">
											销售属性
										</c:if>
									</td>
									<td>
										<c:if test="${empty categoryAttr.option_type || categoryAttr.option_type==null || categoryAttr.option_type==''}">
											未知
										</c:if>
										<c:if test="${not empty categoryAttr.option_type && categoryAttr.option_type==1}">
											必填
										</c:if>
										<c:if test="${not empty categoryAttr.option_type && categoryAttr.option_type==2}">
											非必填
										</c:if>
									</td>
									<td>
										<c:if test="${empty categoryAttr.input_type || categoryAttr.input_type==null || categoryAttr.input_type==''}">
											未知
										</c:if>
										<c:if test="${not empty categoryAttr.input_type && categoryAttr.input_type==1}">
											单选
										</c:if>
										<c:if test="${not empty categoryAttr.input_type && categoryAttr.input_type==2}">
											多选
										</c:if>
										<c:if test="${not empty categoryAttr.input_type && categoryAttr.input_type==3}">
											可输入
										</c:if>
									</td>
									<td>${categoryAttr.sort_number}</td>
									<td class="center ">
										<div style="text-align: center;; height: auto;">
											<button class="btn btn-default btn-primary"
												onclick="javascript:selectCategoryAttrValItem(${categoryAttr.cid}, ${categoryAttr.attr_id});">编辑属性值</button>
											<button class="btn btn-default btn-info"
												onclick="javascript:selectAttrDetails(${categoryAttr.cid}, ${categoryAttr.attr_id});">编辑属性</button>
											<button class="btn btn-default btn-danger"
												onclick="javascript:deleteCategoryAttr(${categoryAttr.cid}, ${categoryAttr.attr_id});">删除</button>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="../../../common/pagehelper.jsp"%><!-- 分页页面 -->

<!-- 用于保存用户选择属性的ID，用于分页查询 -->
<input type="hidden" id="attribute-id" value="" />

	<script type="text/javascript">
		//在此处执行加载数据的函数
		$(function() {

		});
		
		/*
		 * 编辑类目属性和类目属性值信息
		 * 		函数功能：判断用户选择是否选择类目，如果已选择则根据类目ID查询类目属性，如果未选择则提示用户
		 */
		function selectCategoryAttrValItem(cid, attrId){
			$("#cate-attr-val-item-li").removeClass("hide");//显示类目属性值列表选项卡
			var attrName = $("#attr-name-"+attrId).text();
			$("#third-category-attr-name").text("   >   "+attrName);
			$("#attribute-id").val(attrId);
			$('#tabs-819447 a[href="#panel-355190"]').tab('show');//显示编辑类目属性值选项卡
			var params = new Object();
			selectCategoryAttrValItemByPagehelper(params);
		}
		
		/**
		 * 点击类目属性值页面中的页码执行此函数
		 * 		函数功能：根据页码数请求当前页内容
		 */
		function selectCategoryAttrValItemByPagehelper(params){
			var cid = $("#category-id").val();
			var attrId = $("#attribute-id").val();
			if(attrId==null || attrId==""){
				util.message("请选择属性！");
				return;
			}
			var url = "back/attr/getCategoryAttrValItem";
			params.categoryId = cid;
			params.attributeId = attrId;
			params.pagehelperFun = "selectCategoryAttrValItemByPagehelper";
			//util.loading();
			$("#category-attr-value-item").load(url, params, function(){
				console.log("类目属性值列表加载完成！");
			});
		}
		
		/**
		 * 点击添加属性按钮执行
		 *		函数功能：打开添加属性对话框
		 */
		function addCategoryAttr(){
			resetAttrForm();
			$("#attr-modal-title").text("添加属性");
			var cid = $("#category-id").val();
			$("#attr-form-cid").val(cid);
			$('#edit-category-attr-modal').modal('show');
		}
		
		/*
		 * 重置
		 */
		function resetCateAttrValTab(){
			$("#third-category-attr-name").text("");//清空用户选择的类目属性
			$("#category-attr-value-item").empty();//清空类目属性值列表
			$("#cate-attr-val-item-li").addClass("hide");//隐藏类目属性值列表选项卡
		}
		
	</script>
