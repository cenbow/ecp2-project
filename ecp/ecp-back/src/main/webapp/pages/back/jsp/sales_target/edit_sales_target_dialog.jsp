<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="modal fade" id="edit-sales-target-modal" role="dialog"
	aria-labelledby="editSalesTargetModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:80%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h5 class="modal-title" id="editSalesTargetModalLabel">增加考核指标 </h5>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" id="save-form">
					<input type="hidden" id="sales-target-id" name="id" value="" />
					<div class="form-group">
						<label class="col-sm-2 control-label">考核年度</label>
						<div class="col-sm-10" id="show-year-name">
							考核年度
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">周期名称</label>
						<div class="col-sm-10" id="show-cycle-name">
							周期名称
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">开始时间</label>
						<div class="col-sm-10" id="show-start-date">
							开始时间
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">结束时间</label>
						<div class="col-sm-10" id="show-end-date">
							结束时间
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">人员</label>
						<div class="col-sm-10" id="show-username">
							人员
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">角色</label>
						<div class="col-sm-10" id="show-role-name">
							角色
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">指标比例（%）</label>
						<div class="col-sm-10">
							<input type="text" id="sales-target-rate" name="targetRate"
								class="form-control" placeholder="指标比例" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">指标金额（元）</label>
						<div class="col-sm-10">
							<input type="text" id="sales-target-amount" name="targetAmount"
								class="form-control" placeholder="指标金额" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default"
					data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal"
					id="save-submit-btn">保存</button>
			</div>
		</div>

	</div>

</div>
<script type="text/javascript">

/**
 * 点击生成考核指标html
 */
$("#creat-sales-target-html").on("click", function(){
	var checkCycleText = $("#add-sales-target-check-cycle-id").find("option:selected").text();
	var userText = $("#add-sales-target-is-os").find("option:selected").text();
	
	var checkCycleId = $("#add-sales-target-check-cycle-id").val();
	var userId = $("#add-sales-target-user-id").val();
	var roleId = $("#add-sales-target-role-id").val();
	
	if(checkCycleId==null || checkCycleId==""){
		util.message("请选择考核周期！");
		return false;
	}
	
	if(userId==null || userId=="" || roleId==null || roleId==""){
		util.message("请选择人员（角色）！");
		return false;
	}
	
	var url = "back/sales-target/create-sales-target-table";
	var params = {"checkCycleId":checkCycleId, "userId":userId, "roleId":roleId};
	$("#load-sales-target-table").load(url, params, function(){
		$("#sales-target-table-title").html("<b>考核周期：</b>"+checkCycleText+" <b>人员角色：</b>"+userText);
		$("#save-sales-target-btn").attr("disabled", false);//设置对话框中保存按钮为可用
		console.log("加载完成");
		//openAddSalesTargetDialog();
	});
});

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
