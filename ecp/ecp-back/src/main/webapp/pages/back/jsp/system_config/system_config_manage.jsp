<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>系统配置</title>
<%@ include file="../../../common/headCss.jsp"%>

</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="container-fluid"  style="margin-top: 20px;">
					<div class="row clearfix">
						<div class="col-md-12 column">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">
										系统配置
									</h3>
								</div>
								<div class="panel-body">
									<form class="form-horizontal" id="save-form" method="post">
										<div class="form-group">
											<label class="col-sm-2 control-label">1-增值税率</label>
											<div class="col-sm-3">
												<input type="text" class="form-control" id="config-vat-rates" data-config-id="${configVatRates.id}" value="${configVatRates.configValue}" placeholder="1-增值税率" />
											</div>
											<label class="col-sm-7 control-label" style="text-align:left;font-size:150%;margin-top:-5px">%</label>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">&nbsp;</label>
											<div class="col-sm-10">
												<button type="button" class="btn btn-default btn-primary" id="config-save-btn">保存</button>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../../../common/headJs.jsp"%>
	
	<script type="text/javascript">
		
		/**
		 * 绑定保存按钮的点击事件
		 * 		功能：验证表单有效性并保存提交服务器
		 */
		$("#config-save-btn").on("click", function(){
			
			var vatRates = $("#config-vat-rates").val();
			var ret=/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
			if(ret.test(vatRates)){
				//alert("通过");
			}else{
				//alert("不通过");
				util.message("请输入最多两位小数的数字！");
				return false;
			}
			
			var objArr = new Array();
			$("input[type='text']").each(function(index){
				var configValue = $(this).val();
				var configId = $(this).attr("data-config-id");
				var obj = new Object();
				obj.id = configId;
				obj.configValue = configValue;
				objArr.push(obj);
			});
			
			var configArrJSON = JSON.stringify(objArr);
			
			var url = "back/system-config/update";
			var params = {"configArrJSON":configArrJSON};
			$.post(url, params, function(res){
				console.log(res);
				if(res!=null && res!=""){
					var obj = $.parseJSON(res);
					if(obj.result_code=="success"){
						util.message(obj.result_msg);
					}else{
						util.message(obj.result_err_msg);
					}
				}
			});
		});
		
	</script>
</body>
</html>