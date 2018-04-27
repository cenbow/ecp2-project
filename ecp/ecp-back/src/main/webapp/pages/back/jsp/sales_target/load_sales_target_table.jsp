<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- 此页面是点击生成考核指标按钮时所显示的列表页面，未保存时 -->
<div class="row clearfix">
	<div class="col-md-12 column">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title" id="sales-target-table-title">
					Panel title
				</h3>
			</div>
			<div class="panel-body">
				<table class="table table-striped table-bordered table-hover " id="create-sales-target-table"
					style="width: 100%;"center">
					<thead style="width: 98%; padding-top: 80px;">
						<tr role="row">
							<!-- <th>人员（角色）</th> -->
							<th>考核年度</th>
							<th>周期名称</th>
							<th>指标比例</th>
							<th>指标金额</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${salesTargetList}" var="target" varStatus="status">
							<tr>
								<%-- <td>${target.username}（${target.role_name}）</td> --%>
								<td>
									<input type="hidden" class="check-cycle-id" id="check-cycle-id-${status.index}" data-index="${status.index}" value="${target.checkCycleId}" />
									${target.yearName}
								</td>
								<td>${target.cycleName}</td>
								<td>
									<c:if test="${target.cycleName=='全年'}">
										<input type="text" class="target-rate all-year all-year-rate" id="all-year-rate-${status.index}" data-index="${status.index}" value="${target.targetRate}" disabled="disabled" />%
									</c:if>
									<c:if test="${target.cycleName!='全年'}">
										<input type="text" class="target-rate not-all-year not-all-year-rate" id="not-all-year-rate-${status.index}" data-index="${status.index}" value="${target.targetRate}" />%
									</c:if>
								</td>
								<td>￥
									<c:if test="${target.cycleName=='全年'}">
										<input type="text" class="target-amount all-year all-year-amount" id="all-year-amount-${status.index}" data-index="${status.index}" value="${target.targetAmount}" />
									</c:if>
									<c:if test="${target.cycleName!='全年'}">
										<input type="text" class="target-amount not-all-year not-all-year-amount" id="not-all-year-amount-${status.index}" data-index="${status.index}" value="${target.targetAmount}" />
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- <div class="panel-footer">
				Panel footer
			</div> -->
		</div>
	</div>
</div>
<script>
	/**
	 * 绑定全年指标金额input事件，输入时根据比例设置对应的指标金额
	 */
	$("#create-sales-target-table .all-year-amount").on("input", function(){
		var totalAmount = $(this).val();
		$("#load-sales-target-table .check-cycle-id").each(function(index){
			if(index!=0){
				var targetRate = $("#load-sales-target-table .target-rate").eq(index).val();
				//var targetAmount = $("#load-sales-target-table .target-amount").eq(index).val();
				var rate = Number(targetRate)/100;
				var amount = Number(totalAmount);
				var targetAmount = amount*rate;
				$("#load-sales-target-table .target-amount").eq(index).val(targetAmount.toFixed(2));
			}
		});
	});
	/**
	 * 绑定非全年指标比例input事件，输入时根据输入的比例设置对应的指标金额
	 */
	$("#create-sales-target-table .not-all-year-rate").on("input", function(){
		var totalAmount = $("#create-sales-target-table .all-year-amount").val();
		var rateIndex = $(this).attr("data-index");
		var targetRate = $(this).val();
		var rate = Number(targetRate)/100;
		var amount = Number(totalAmount);
		var targetAmount = amount*rate;
		$("#not-all-year-amount-"+rateIndex).val(targetAmount.toFixed(2));
	});
	/**
	 * 绑定非全年指标金额input事件，输入时根据输入的金额设置对应的指标比例
	 */
	$("#create-sales-target-table .not-all-year-amount").on("input", function(){
		var totalAmount = $("#create-sales-target-table .all-year-amount").val();
		var amountIndex = $(this).attr("data-index");
		var targetAmount = $(this).val();
		var rate = Number(targetAmount)/Number(totalAmount);
		var targetRate = rate*100;
		$("#not-all-year-rate-"+amountIndex).val(targetRate.toFixed(2));
	});
</script>