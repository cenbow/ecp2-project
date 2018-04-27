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
						<c:forEach items="${salesTargetList}" var="target">
							<tr>
								<%-- <td>${target.username}（${target.role_name}）</td> --%>
								<td>
									<input type="text" class="check-cycle-id" value="${target.checkCycleId}" />
									${target.yearName}
								</td>
								<td>${target.cycleName}</td>
								<td >
									<input type="text" class="target-rate" value="${target.targetRate}" />%
								</td>
								<td>￥
									<input type="text" class="target-amount" value="${target.targetAmount}" />
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
