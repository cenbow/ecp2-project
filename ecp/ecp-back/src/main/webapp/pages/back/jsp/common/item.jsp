<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 公共商品列表 -->
<div class="container-fluid" style="margin-top: 20px;">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						查询面板
					</h3>
				</div>
				<div class="panel-body" id="">
					<form class="form-horizontal" id="search-form">
						<div class="form-group">
							<label class="col-md-2 control-label">关键字</label>
							<div class="col-md-10">
								<input type="text" id="search-keywords" name="search-keywords" class="form-control" placeholder="类目名称 / 品牌名称 / 商品名称">
							</div>
						</div>
						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">&nbsp;</label>
							<div class="col-sm-10">
								<button type="button" class="btn btn-default btn-primary" id="search-submit-btn">查询</button>
								<button type="button" class="btn btn-default btn-warning" id="search-reset-btn">重置</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						商品列表
					</h3>
				</div>
				<div class="panel-body">
					<div class="panel panel-default">
						<div id="item-table-div" style="margin: 20px">
							<%@ include file="item_table.jsp"%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
/**
 * 打开商品列表对话框
 */
/* function openItemDialog(){
	var params = new Object();
	var action = "back/item/getItems";
	params.pagehelperFun = "filterItemFun";
	//util.loading();
	$("#common-item").load(action, params, function(){
		//加载完成
		$("#item-modal").modal("show");
	});
} */
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

/*
 * 点击页面中的页码执行此函数
 * 		函数功能：根据页码数请求当前页内容
 */
function filterItemFun(params){
	var action = "back/item/getItems";
	params.clickPageBtn = true;
	var keywords = $("#search-keywords").val();
	params.search_keywords = keywords;
	//util.loading();
	$("#item-table-div").load(action, params, function(){
		//加载完成
		console.log("加载完成");
	});
}

/**
 * 点击商品列表对话框中的保存按钮时执行
 *		功能：保存选择的商品到指定位置
 */
/* $("#save-selected-item-btn").click(function(){
	
}); */
/**
 * 获取用户选中的checkbox值
 * @returns
 */
/* function getCheckedItem(){
	var itemIds = new Array();
	$("#product-table tbody input:checkbox[name='itemCheckbox']:checked").each(function(){
		var itemId = $(this).val();
		itemIds.push(itemId);
	});
	console.log("用户选择的商品ID："+itemIds);
	return itemIds.toString();
} */

/*
 * 点击列表中某个复选框时，全选或反选
 */
function checkOne(itemId){
	$("#product-table tbody input[type='checkbox']").prop("checked", false);
    //生成链接
    $("#item-td-"+itemId).prop("checked", true);
    createItemUrl(itemId);
    $("#item-modal").modal("hide");
    /* var flag = true;
    $("#product-table tbody input[type='checkbox']").each(function(){
    	if(!$(this).prop("checked")){
    		flag = false;
    	}
    });
    if(flag){
    	$("#product-table thead input[type='checkbox']").prop('checked', true);
    }else{
    	$("#product-table thead input[type='checkbox']").prop('checked', false);
    } */
}
/*
 * 点击列表中All复选框时，列表全选或反选
 */
function checkAll(obj){
	$("#product-table tbody input[type='checkbox']").prop('checked', $(obj).prop('checked'));
}
</script>