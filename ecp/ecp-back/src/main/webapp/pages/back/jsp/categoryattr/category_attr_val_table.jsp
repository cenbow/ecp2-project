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
						<button type="button" id="add-btn" class="btn btn-default btn-primary" onclick="javascript:addCategoryAttrVal();">添加属性值</button>
					</h3>
				</div>
				<div class="panel-body">
					<table class="table table-striped table-bordered table-hover " id="category-attr-value-table"
						style="width: 100%;"center">
						<thead style="width: 98%; padding-top: 80px;">
							<tr>
								<th>
									<input type="checkbox" name="checkbox" id="attr-val-all-checkbox" onclick="javascript:checkAttrValAll(this);">
									<label for="attr-val-all-checkbox">ALL</label>
								</th>
								<th>ID</th>
								<th>值名称</th>
								<th>排序</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pagehelper.list}" var="categoryAttrVal">
								<tr>
									<td>
										<input type="checkbox" name="checkbox2" id="attr-value-td-${categoryAttrVal.value_id}" onclick="javascript:checkAttrValOne();" value="${categoryAttrVal.value_id}">
									</td>
									<td>${categoryAttrVal.value_id}</td>
									<td>${categoryAttrVal.value_name}</td>
									<td>${categoryAttrVal.sort_number}</td>
									<td class="center ">
										<div style="text-align: center;; height: auto;">
											<button class="btn btn-default btn-primary"
												onclick="javascript:selectAttrValDetails(${categoryAttrVal.value_id});">编辑属性值</button>
											<button class="btn btn-default btn-danger"
												onclick="javascript:deleteCategoryAttrVal(${categoryAttrVal.value_id});">删除</button>
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

	<script type="text/javascript">
		//在此处执行加载数据的函数
		$(function() {

		});
		
		/**
		 * 点击添加属性值按钮执行
		 *		函数功能：打开添加属性值对话框
		 */
		function addCategoryAttrVal(){
			resetAttrValueForm();
			$("#attr-value-modal-title").text("添加属性值");
			var cid = $("#category-id").val();
			$("#attr-value-form-cid").val(cid);
			var attrId = $("#attribute-id").val();
			$("#attr-value-form-attr-id").val(attrId);
			$('#edit-category-attr-value-modal').modal('show');
		}
		
	</script>
