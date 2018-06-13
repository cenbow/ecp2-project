<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>产品销售清单</title>
<%@ include file="../../../common/headCss.jsp"%>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="container-fluid" style="margin-top: 20px;">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">
								产品销售清单
							</h3>
						</div>
						<div class="panel-body">
							<div class="panel panel-default">
								<div class="panel-body">
									<div class="row clearfix">
										<div class="col-md-2 column">
											<input type="text" id="select-start-time" name="" title="开始时间"
													class="datetimepicker datetime form-control"
													readonly="readonly" placeholder="开始时间"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
													<!-- onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> -->
										</div>
										<div class="col-md-2 column">
											<input type="text" id="select-end-time" name="" title="结束时间"
													class="datetimepicker datetime form-control"
													readonly="readonly" placeholder="结束时间"
													onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
													<!-- onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> -->
										</div>
										<div class="col-md-2 column">
						  					<select class="form-control" id="select-user" onchange="javascript:functionChangeUser();" title="用户">
						  						<c:if test="${!isISOrOS}">
						  							<option value="" data-code="">—— 用户 ——</option>
						  						</c:if>
						  						<c:forEach items="${userList}" var="user">
						  							<option value="${user.id}">${user.nickname}</option>
						  						</c:forEach>
						  					</select>
										</div>
										<div class="col-md-2 column">
						   					<select class="form-control" id="select-role" title="角色">
						   						<option value="" data-code="">—— 角色 ——</option>
						   						<c:forEach items="${roleList}" var="role">
						   							<option value="${role.roleId}" data-code="${role.roleCode}">${role.roleName}</option>
						   						</c:forEach>
						   					</select>
										</div>
										<div class="col-md-2 column">
											<button type="button" class="btn btn-default btn-primary" id="select-submit-btn">查询</button>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-body">
									<div class="col-md-12 column">
										<div id="echarts-items-sales-num-total" style="width: 100%;height:400px;"></div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-body">
									<div class="col-md-12 column">
										<div id="echarts-items-sales-price-total" style="width: 100%;height:400px;"></div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-body">
									<div id="item-div" style="margin: 20px">
										<%@ include file="statsManageTable.jsp"%>
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
	<!-- 引入 echarts.js -->
    <script src="echarts/echarts.min.js"></script>
	<!-- 日期工具 -->
	<script type="text/javascript" src="static/calendar/WdatePicker.js"></script>
	<script type="text/javascript">
		/**
		 * 根据用户ID查询角色
		 */
		function functionChangeUser(){
			 var userId = $("#select-user").val();
			 if(userId==null || userId==""){
				$("#select-role").empty();
				var html = '<option value="" data-code="">—— 角色 ——</option>';
				$("#select-role").append(html);
				return false;
			 }
			 var url = "back/role/selectByUserId";
				var params = {"userId":userId};
				$.post(url, params, function(res){
					console.log(res);
					if(res!=null){
						var resp = $.parseJSON(res);
						if(resp.result_code=="success"){
							var roleList =resp.roleList;
							$("#select-role").empty();
							var html = '';//'<option value="" data-code="" selected="selected">—— 角色 ——</option>';
							//var html = '';
							$.each(roleList, function(index){
								if(index==0){
									html += '<option value="'+this.roleId+'" data-code="'+this.roleCode+'" selected="selected">'+this.roleName+'</option>'
								}else{
									html += '<option value="'+this.roleId+'" data-code="'+this.roleCode+'">'+this.roleName+'</option>'
								}
							});
							
							$("#select-role").append(html);
							
							/* var roleId=ret_roleId;
							if(roleId!=null && roleId!=""){
								$("#select-role").val(roleId);
							} */
							
							return;
						}
					}
					util.message("查询异常");
					
				});
		}
		
		var isInitEchart = false;//是否初始化Echarts
		
		/**
		 * 点击页面中的查询按钮时执行，功能：按条件查询
		 */
		$("#select-submit-btn").on("click", function(){
			var params = new Object();
			params.pagehelperFun = "searchClickPageBtnRequestFun";
			searchClickPageBtnRequestFun(params);
			isInitEchart = true;//是否初始化Echarts
		});

		/*
		 * 查询时点击页面中的页码执行此函数，查询结果分页
		 * 		函数功能：根据页码数请求当前页内容
		 */
		function searchClickPageBtnRequestFun(params){
			
			var url = "back/item-sales-stats/select-items"; // 需要提交的 url
			
			//时间条件
			var startTime=$("#select-start-time").val();
			var endTime=$("#select-end-time").val();
			//用户/角色条件
			var userId=$("#select-user").val();
			var roleId=$("#select-role").val();
			
			params.clickPageBtn = true;
			params.startTime = startTime;
			params.endTime = endTime;
			params.userId = userId;
			params.roleId = roleId;
			
			loadPageRequest(url, params);
		}
		
		/*
		 * 点击页面中的页码执行此函数，查询结果分页
		 * 		函数功能：根据页码数请求当前页内容
		 */
		function clickPageBtnRequestFun(params){
			
			var url = "back/item-sales-stats/select-items"; // 需要提交的 url
			params.clickPageBtn = true;
			
			loadPageRequest(url, params);
		}
		
		/**
		 * 加载进度列表页面
		 * @param url
		 * @param params
		 * @returns
		 */
		function loadPageRequest(url, params){
			$("#item-div").load(url, params, function(){
				console.log("加载完成");
				if(isInitEchart){
					initItemSalesNumTotalChart();
					initItemSalesPriceTotalChart();
				}
			});
		}
	</script>
	<script type="text/javascript">
		/* var g_itemNameList = ${itemNameList};
		console.log(g_itemNameList);
		var g_itemNumTotalList = ${itemNumTotalList};
		console.log(g_itemNumTotalList);
		var g_itemPriceTotalList = ${itemPriceTotalList};
		console.log(g_itemPriceTotalList); */
		
		var g_itemNameList = ${itemNameList};
		var g_itemNumTotalList = ${itemNumTotalList};
		var g_itemPriceTotalList = ${itemPriceTotalList};
		
		function initItemSalesNumTotalChart(){
			// 初始化产品销售总数量echarts实例
	        var itemsSalesNumTotalChart = echarts.init(document.getElementById('echarts-items-sales-num-total'));
	
	        // 指定图表的配置项和数据
	        var option = {
	            title: {
	                text: '产品销售总数量'
	            },
	            tooltip: {
	            	trigger: 'axis',
	                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	                },
	                formatter: '{b}<br />{a}: {c}个'
	            },
	            legend: {
	                data:['产品销售总数量']
	            },
	            xAxis: {
	                //data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
	            	data: g_itemNameList
	            },
	            yAxis: {
					type : 'value'
	            },
	            series: [{
	                name: '产品销售总数量',
	                type: 'bar',
	                //data: [5, 20, 36, 10, 10, 20]
	                data: g_itemNumTotalList
	            }]
	        };
	
	        // 使用刚指定的配置项和数据显示图表。
	        itemsSalesNumTotalChart.setOption(option);
		}
		
        
		function initItemSalesPriceTotalChart(){
			// 初始化产品销售总金额echarts实例
	        var itemsSalesPriceTotalChart = echarts.init(document.getElementById('echarts-items-sales-price-total'));
	
	        // 指定图表的配置项和数据
	        var option = {
	            title: {
	                text: '产品销售总金额'
	            },
	            tooltip: {
	            	trigger: 'axis',
	                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	                },
	                formatter: '{b}<br />{a}: {c}元'
	            },
	            legend: {
	                data:['产品销售总金额']
	            },
	            xAxis: {
	                //data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
	            	data: g_itemNameList
	            },
	            yAxis: {
					type : 'value'
	            },
	            series: [{
	                name: '产品销售总金额',
	                type: 'bar',
	                //data: ["5", "20", "36", "10", "10", "20"]
	                data: g_itemPriceTotalList
	            }]
	        };
	
	        // 使用刚指定的配置项和数据显示图表。
	        itemsSalesPriceTotalChart.setOption(option);
		}
	    $(function(){
	    	initItemSalesNumTotalChart();
			initItemSalesPriceTotalChart();
	    });
	</script>
</body>
</html>
