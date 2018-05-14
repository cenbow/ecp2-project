<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div class="modal fade" id="add-check-cycle-modal" role="dialog"
	aria-labelledby="addCheckCycleModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:80%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h5 class="modal-title" id="addCheckCycleModalLabel">增加考核周期 </h5>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" id="add-check-cycle-form">
					<div class="form-group">
						<label for="" class="col-sm-2 control-label">&nbsp;</label>
						<div class="col-sm-10">
							<input type="text" id="check-cycle-year" name="yearName"
										class="datetimepicker datetime form-control"
										readonly="readonly" placeholder="年"
										onClick="WdatePicker({dateFmt:'yyyy'})" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label checkbox"><input type="checkbox" class="checkbox-all" id="" name="" /><b>全选</b></label>
						<div class="col-sm-10 checkbox curr-checkbox-all" id="half-a-year">
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="halfAYear-1-上半年-1" />上半年</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="halfAYear-2-下半年-2" />下半年</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label checkbox"><input type="checkbox" class="checkbox-all" id="" name="" /><b>全选</b></label>
						<div class="col-sm-10 checkbox curr-checkbox-all" id="quarter">
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="quarter-1-一季度-3" />一季度</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="quarter-2-二季度-4" />二季度</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="quarter-3-三季度-5" />三季度</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="quarter-4-四季度-6" />四季度</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label checkbox"><input type="checkbox" class="checkbox-all" id="" name="" /><b>全选</b></label>
						<div class="col-sm-10 checkbox curr-checkbox-all" id="month">
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="month-1-一月-7" />一月</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="month-2-二月-8" />二月</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="month-3-三月-9" />三月</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="month-4-四月-10" />四月</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="month-5-五月-11" />五月</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="month-6-六月-12" />六月</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="month-7-七月-13" />七月</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="month-8-八月-14" />八月</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="month-9-九月-15" />九月</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="month-10-十月-16" />十月</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="month-11-十一月-17" />十一月</label>
							<label><input type="checkbox" class="checkbox-one" id="" name="" value="month-12-十二月-18" />十二月</label>
						</div>
					</div>

				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default"
					data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary"
					id="save-check-cycle-btn">保存</button>
			</div>
		</div>

	</div>

</div>
<script type="text/javascript">

$("#check-cycle-year").val(currYear);//设置当前年

/**
 * 绑定全选复选框click事件
 * 		当前全选复选框为选择状态时，设置单个复选框为选择状态；否则设置单个复选框非选择状态
 */
$(".checkbox-all").on("click", function(){
	var flag = $(this).prop("checked");
	$(this).parent().parent().find(".curr-checkbox-all").find(".checkbox-one").each(function(){
		$(this).prop("checked",flag);
	});
})
/**
 * 绑定单个复选框click事件
 * 		当前单个复选框全部选择后，设置全选复选框为选择状态；否则设置全选复选框非选择状态
 */
$("#add-check-cycle-form .checkbox-one").on("click", function(){
	var flag = false;
	$(this).parent().parent().find(".checkbox-one").each(function(){
		flag = $(this).prop("checked");
		if(!flag){
			return false;
		}
	});
	$(this).parent().parent().parent().find(".checkbox-all").prop("checked", flag);
})
/**
 * 点击对话框中的保存按钮时执行
 * 		保存选择的内容到DB
 */
$("#save-check-cycle-btn").on("click", function(){
	var yearName = $("#check-cycle-year").val();
	var cycleArr = new Array();
	//$(".checkbox-one").each(function(){
	$("#add-check-cycle-form .curr-checkbox-all input[type='checkbox']:checked").each(function(){
		var currChckbox = $(this).val();
		cycleArr.push(currChckbox);
	});
	
	var cycleArrJSON = JSON.stringify(cycleArr);
	
	var url = "back/check-cycle/insert";
	var params = {"yearName": yearName, "cycleArrJSON":cycleArrJSON}
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				//操作成功后重新加载当前菜单内容
				//reloadInfoFun();
				var href = "back/check-cycle/select-items?pagehelperFun=clickPageBtnRequestFun&yearName="+yearName;
				parent.window.iframeLoading(href);//调用主页面中的在iframe中加载内容的方法
			}else{
				util.message(obj.result_err_msg);
			}
		}
	});
})
</script>
