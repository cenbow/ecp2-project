<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	/*
	 * 点击页码时调用此函数
	 */
	function loadSelectPageFun(action, params) {
		$("#content_div_id").load(action, params);
	}
</script>
<div class="panel-footer">
	<!-- 页数 -->
	<input type="hidden" id="pageNum" value="${pagehelper.pageNum }" />
	<!-- 总条数 -->
	<input type="hidden" id="total-s" value="${pagehelper.total}" />
	<div class="message">
		共<i class="blue">${pagehelper.total}</i>条记录，当前显示第&nbsp;<i class="blue">${pagehelper.pageNum}/${pagehelper.pages}</i>&nbsp;页
	</div>
	<!-- 分页 -->
	<div class="row clearfix">
		<div class="col-md-12 column">
			<ul class="pagination pull-right">
				<!-- <li><a href="#">&laquo;</a></li> -->
				<c:if test="${!pagehelper.isFirstPage && pagehelper.total>0}">
					<li><a
						href="javascript:${pagehelperFun}({'pageNum':1,'pageSize':${pagehelper.pageSize},'pagehelperFun':'${pagehelperFun}'});">首页<!-- &lt;&lt; --></a></li>
					<li><a
						href="javascript:${pagehelperFun}({'pageNum':${pagehelper.prePage},'pageSize':${pagehelper.pageSize},'pagehelperFun':'${pagehelperFun}'});">上一页<!-- &lt; --></a></li>
				</c:if>
				<c:forEach items="${pagehelper.navigatepageNums}"
					var="navigatepageNum">

					<c:if test="${navigatepageNum==pagehelper.pageNum}">
						<li class="active"><a
							href="javascript:${pagehelperFun}({'pageNum':${navigatepageNum},'pageSize':${pagehelper.pageSize},'pagehelperFun':'${pagehelperFun}'});">${navigatepageNum}</a></li>
					</c:if>
					<c:if test="${navigatepageNum!=pagehelper.pageNum}">
						<li><a
							href="javascript:${pagehelperFun}({'pageNum':${navigatepageNum},'pageSize':${pagehelper.pageSize},'pagehelperFun':'${pagehelperFun}'});">${navigatepageNum}</a></li>
					</c:if>
				</c:forEach>
				<c:if test="${!pagehelper.isLastPage && pagehelper.total>0}">
					<li><a
						href="javascript:${pagehelperFun}({'pageNum':${pagehelper.nextPage},'pageSize':${pagehelper.pageSize},'pagehelperFun':'${pagehelperFun}'});">下一页<!-- &gt; --></a></li>
					<li><a
						href="javascript:${pagehelperFun}({'pageNum':${pagehelper.pages},'pageSize':${pagehelper.pageSize},'pagehelperFun':'${pagehelperFun}'});">尾页<!-- &gt;&gt; --></a></li>
				</c:if>
			</ul>
		</div>
	</div>
</div>
