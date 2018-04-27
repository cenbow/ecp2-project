<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="modal fade" id="edit-pushmoney-config-modal" role="dialog"
	aria-labelledby="addCheckCycleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h5 class="modal-title" id="addCheckCycleModalLabel">编辑提成比例配置 </h5>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" id="save-form">
					<input type="hidden" id="config-id" name="id" value="" />
					<div class="form-group">
						<label for="name" class="col-sm-4 control-label">角色</label>
						<div class="col-sm-8" id="">
							<select class="form-control" id="role-id" name="roleId">
								<c:forEach items="${roleList}" var="role">
									<option value="${role.roleId}">${role.roleName}</option>
								</c:forEach>	
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">销售额完成比例（%）</label>
						<div class="col-sm-8">
							<input type="text" id="config-completion-rate" name="completionRate"
								class="form-control" placeholder="销售额完成比例（%）" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">提成比例（%）</label>
						<div class="col-md-8">
							<input type="text" id="config-pushmoney-rate" name="pushmoneyRate"
								class="form-control" placeholder="提成比例（%）" />
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-4 control-label">备注</label>
						<div class="col-sm-8">
							<input type="text" id="config-comment" name="comment"
								class="form-control" placeholder="备注" />
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
</script>
