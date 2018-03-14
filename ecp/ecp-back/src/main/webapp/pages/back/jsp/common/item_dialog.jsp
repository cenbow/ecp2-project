<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 商品列表对话框 -->
<div class="modal fade" id="item-modal" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:80%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title" id="attr-modal-title">商品列表</h4>
			</div>
			<div class="modal-body" id="common-item">
				<!-- 显示公共商品列表页面 -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<!-- <button type="button" class="btn btn-primary" id="save-selected-item-btn">保存</button> -->
			</div>
		</div>
	</div>
</div>

<script>
/**
 * 打开商品列表对话框
 */
function openItemDialog(){
	var params = new Object();
	var action = "back/item/getItems";
	params.pagehelperFun = "filterItemFun";
	//util.loading();
	$("#common-item").load(action, params, function(){
		//加载完成
		$("#item-modal").modal("show");
		$('#item-modal').on('shown.bs.modal', function () {
			  // 执行一些动作...
			$("#search-keywords").focus();
		});
		
	});
	
}

</script>