<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>销售指标</title>
<%@ include file="../../../common/headCss.jsp"%>
<!-- 日期工具 -->
<script type="text/javascript" src="static/calendar/WdatePicker.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="container-fluid">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">
								销售指标列表
							</h3>
						</div>
						<div class="panel-body">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row clearfix">
									<div class="col-md-2 column">
										<button type="button" class="btn btn-default btn-primary" id="add-sales-target-btn">添加销售指标</button>
									</div>
									<div class="col-md-2 column">
										<input type="text" id="search-check-cycle-year" name="searchYearName" title="考核年度"
													class="datetimepicker datetime form-control"
													readonly="readonly" placeholder="考核年度"
													onClick="WdatePicker({dateFmt:'yyyy'})" />
													<!-- onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> -->
									</div>
									<div class="col-md-3 column">
										<input type="hidden" id="search-user-id" name="userId" value="" />
										<input type="hidden" id="search-role-id" name="roleId" value="" />
										<select class="form-control" id="search-is-os" title="人员（角色）">
											<option value="">—— 人员（角色） ——</option>
											<c:forEach items="${userList}" var="user">
												<option value="${user.id}-${user.role_id}">${user.nickname}（${user.role_name}）</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-2 column">
										<button type="button" class="btn btn-default btn-primary" id="search-sales-target-btn">查询</button>
										<button type="button" class="btn btn-default btn-primary" id="del-sales-target-btn">删除</button>
									</div>
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-body">
								<div id="item-div" style="margin: 20px">
									<%@ include file="sales_target_table.jsp"%>
								</div>
							</div>
						</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

<%@ include file="../../../common/headJs.jsp"%>

<div id="load-add-dialog-div">
<!-- 此处加载增加考核指标对话框 -->
</div>
<div id="load-edit-dialog-div">
<!-- 此处加载编辑考核指标对话框 -->
<%@ include file="edit_sales_target_dialog.jsp"%>
</div>
<script type="text/javascript">
	var currYear = ${searchYearName};
	$("#search-check-cycle-year").val(currYear);
</script>
<script type="text/javascript" src="static/js/sales_target.js"></script>
<script type="text/javascript">
/**
 * 点击删除按钮
 */
$("#del-sales-target-btn").on("click", function(){
	var yearName = $("#search-check-cycle-year").val();
	if(yearName==null || yearName==""){
		util.message("请选择考核年度！");
		return false;
	}
	var userId = $("#search-user-id").val();
	var roleId = $("#search-role-id").val();
	if(userId==null || userId=="" || roleId==null || roleId==""){
		util.message("请选择人员（角色）！");
		return false;
	}
	util.delConfirm("确认删除？", 1, "deleteSalesTargetAjax");
});
/**
 * 根据条件删除考核指标ajax请求
 * @param id
 * @returns
 */
function deleteSalesTargetAjax(id){
	var yearName = $("#search-check-cycle-year").val();
	if(yearName==null || yearName==""){
		util.message("请选择考核年度！");
		return false;
	}
	var userId = $("#search-user-id").val();
	var roleId = $("#search-role-id").val();
	if(userId==null || userId=="" || roleId==null || roleId==""){
		util.message("请选择人员（角色）！");
		return false;
	}
	var url = "back/sales-target/delete-all-year";
	var params = {"yearName":yearName, "userId":userId, "roleId":roleId};
	//util.loading();
	$.post(url, params, function(res){
		console.log(res);
		if(res!=null && res!=""){
			var obj = $.parseJSON(res);
			if(obj.result_code=="success"){
				reloadInfoFun();
			}else{
				util.message(obj.result_err_msg);
			}
		}
		
	});
}
</script>
</body>
</html>
