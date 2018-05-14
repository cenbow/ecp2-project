<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="modal fade" id="add-sales-target-modal" role="dialog"
	aria-labelledby="addCheckCycleModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:80%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h5 class="modal-title" id="addCheckCycleModalLabel">增加考核指标 </h5>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" id="add-sales-target-form">
					<div class="form-group">
						<div class="col-sm-2">
						&nbsp;
						</div>
						<div class="col-sm-3">
							<select class="form-control" id="add-sales-target-check-cycle-id" name="" data-trigger="manual" data-container="body" data-toggle="popover" data-placement="bottom" 
			data-content="请选择考核周期！">
								<option value="">请选择考核周期</option>
								<c:forEach items="${checkCycleList}" var="checkCycle">
									<option value="${checkCycle.id}">${checkCycle.yearName}&nbsp;-&nbsp;年度</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-3">
							<input type="hidden" id="add-sales-target-user-id" name="userId" value="" />
							<input type="hidden" id="add-sales-target-role-id" name="roleId" value="" />
							<select class="form-control" id="add-sales-target-is-os" data-trigger="manual" data-container="body" data-toggle="popover" data-placement="bottom" 
			data-content="请选择人员（角色）！">
								<option value="">请选择人员（角色）</option>
								<c:forEach items="${userList}" var="user">
									<option value="${user.id}-${user.role_id}">${user.nickname}（${user.role_name}）</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-4">
							<button type="button" class="btn btn-primary" id="creat-sales-target-html" disabled="disabled">生成考核指标</button>
						</div>
					</div>
					<div class="form-group" id="load-sales-target-table">
						<!-- 此处加载考核指标列表页面 -->
					</div>

				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default"
					data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal"
					id="save-sales-target-btn" disabled="disabled">保存</button>
			</div>
		</div>

	</div>

</div>
<script>
$(function () { 
	//$("[data-toggle='popover']").popover();
	//$("#creat-sales-target-html").popover('show');
});
</script>
<script type="text/javascript">

/**
 * 绑定考核周期的change事件，用于显示提示信息
 */
$("#add-sales-target-check-cycle-id").on("change", function(){
	var targetIsOs = $("#add-sales-target-is-os").val();
	var val = $(this).val();
	if(val==null || val==""){
		$("#add-sales-target-check-cycle-id").popover('show');
	}else{
		$("#add-sales-target-check-cycle-id").popover('hide');
	}
	if(val==null || val=="" || targetIsOs==null || targetIsOs==""){
		$("#creat-sales-target-html").attr("disabled", true);
	}else{
		$("#creat-sales-target-html").attr("disabled", false);
	}
});
/**
 * 绑定人员（角色）的change事件，用于显示提示信息
 */
$("#add-sales-target-is-os").on("change", function(){
	var checkCycleId = $("#add-sales-target-check-cycle-id").val();
	var val = $(this).val();
	if(val==null || val=="" ){
		$("#add-sales-target-is-os").popover('show');
	}else{
		$("#add-sales-target-is-os").popover('hide');
	}
	if(checkCycleId==null || checkCycleId=="" || val==null || val==""){
		$("#creat-sales-target-html").attr("disabled", true);
	}else{
		$("#creat-sales-target-html").attr("disabled", false);
	}
});

/**
 * 点击生成考核指标html
 */
$("#creat-sales-target-html").on("click", function(){
	
	var checkCycleId = $("#add-sales-target-check-cycle-id").val();
	var userId = $("#add-sales-target-user-id").val();
	var roleId = $("#add-sales-target-role-id").val();
	
	if(checkCycleId==null || checkCycleId==""){
		//util.message("请选择考核周期！");
		$("#add-sales-target-check-cycle-id").popover('show');
		return false;
	}else{
		$("#add-sales-target-check-cycle-id").popover('hide');
	}
	
	if(userId==null || userId=="" || roleId==null || roleId==""){
		//util.message("请选择人员（角色）！");
		$("#add-sales-target-is-os").popover('show');
		return false;
	}else{
		$("#add-sales-target-is-os").popover('hide');
	}
	
	var url = "back/sales-target/check-exist";
	var params = {"checkCycleId":checkCycleId, "userId":userId, "roleId":roleId};
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				//操作成功后重新加载当前菜单内容
				ajaxCreateHtml(checkCycleId, userId, roleId);
			}else{
				util.message(obj.result_err_msg);
			}
		}
	});
	
});
/**
 * AJAX请求创建考核指标HTML并返回创建考核指标HTML页面
 */
function ajaxCreateHtml(checkCycleId, userId, roleId){
	var checkCycleText = $("#add-sales-target-check-cycle-id").find("option:selected").text();
	var userText = $("#add-sales-target-is-os").find("option:selected").text();
	
	var url = "back/sales-target/create-sales-target-table";
	var params = {"checkCycleId":checkCycleId, "userId":userId, "roleId":roleId};
	$("#load-sales-target-table").load(url, params, function(){
		$("#sales-target-table-title").html("<b>考核周期：</b>"+checkCycleText+" <b>人员角色：</b>"+userText);
		$("#save-sales-target-btn").attr("disabled", false);//设置对话框中保存按钮为可用
		console.log("加载完成");
		//openAddSalesTargetDialog();
	});
}

/**
 * 点击对话框中的保存按钮时执行
 * 		保存选择的内容到DB
 */
$("#save-sales-target-btn").on("click", function(){
	var targetArr = new Array();
	$("#load-sales-target-table .check-cycle-id").each(function(index){
		var checkCycleId = $(this).val();
		var targetRate = $("#load-sales-target-table .target-rate").eq(index).val();
		var targetAmount = $("#load-sales-target-table .target-amount").eq(index).val();
		console.log("第"+(index+1)+"条数据："+"checkCycleId："+checkCycleId+"; targetRate："+targetRate+"; targetAmount："+targetAmount);
		if(targetRate==null || targetRate==""){
			targetRate = 0;
		}
		if(targetAmount==null || targetAmount==""){
			targetAmount = 0.00;
		}
		var obj = new Object();
		obj.checkCycleId = checkCycleId;
		obj.targetRate = targetRate;
		obj.targetAmount = targetAmount;
		targetArr.push(obj);
	});
	
	var targetArrJSON = JSON.stringify(targetArr);
	console.log(targetArrJSON);
	
	var checkCycleId = $("#add-sales-target-check-cycle-id").val();
	var userId = $("#add-sales-target-user-id").val();
	var roleId = $("#add-sales-target-role-id").val();
	
	var url = "back/sales-target/insert";
	var params = {"checkCycleId":checkCycleId, "userId":userId, "roleId":roleId, "targetArrJSON":targetArrJSON};
	
	$.post(url, params, function(res){
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
	});
})
/**
 * 绑定增加考核指标的用户角色change事件
 */
$("#add-sales-target-is-os").on("change", function(){
	var userIdRoleId = $(this).val();
	console.log("value:"+userIdRoleId);
	if(userIdRoleId!=null && userIdRoleId!=""){
		var arr = userIdRoleId.split("-");// 在每个减号(-)处进行分解
		var userId = arr[0];
		var roleId = arr[1];
		$("#add-sales-target-user-id").val(userId);
		$("#add-sales-target-role-id").val(roleId);
	}else{
		$("#add-sales-target-user-id").val("");
		$("#add-sales-target-role-id").val("");
	}
});
</script>
