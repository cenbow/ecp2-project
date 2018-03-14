<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>群发消息</title>
<%@ include file="../common/headCss.jsp"%>
<style type="text/css">
.topbar-item {
	display: inline-block;
	width: 130px;
	margin-bottom: 15px;
}

.card-contant>div {
	border: 1px solid #eee;
}

.tab_cont_cover {
	width: 48%;
	margin-right: 1%;
	border: 1px dashed #ccc;
	display: inline-block;
	text-align: center;
}

.tab_cont_cover .media_cover {
	padding: 60px 0;
}

.tab_cont_cover:hover {
	border-color: #999;
}
</style>
</head>
<body>
	<br>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="tabbable" id="tabs-853942">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#panel-699704" data-toggle="tab">新建群发消息</a>
						</li>
						<li><a href="#panel-563107" data-toggle="tab">已发送</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="panel-699704">
							<br>
							<div class="container-fluid">
								<div class="row">
									<div class="col-md-12">
										<div class="top-bar ">
											<div class="topbar-item">
												<label for="name">群发对象</label> <select class="form-control">
													<option value="0">全部用户</option>
													<option value="1">按标签选择</option>
												</select>
											</div>
											<div class="topbar-item topbar-item2" style="display: none;">
												<select class="form-control">
													<option>星标用户</option>
												</select>
											</div>
											<div class="topbar-item">
												<label for="name">性别</label> <select class="form-control">
													<option>全部</option>
													<option>男</option>
													<option>女</option>
												</select>
											</div>
											<div class="topbar-item">
												<label for="name">群发地区</label> <select class="form-control">
													<option>国家</option>
													<option>中国</option>
													<option>中国台湾</option>
													<option>中国澳门</option>
													<option>中国香港</option>
													<option>美国</option>
													<option>澳大利亚</option>
													<option>马来西亚</option>
													<option>...</option>
												</select>
											</div>
										</div>
										<div class="con">
											<ul id="myTab" class="nav nav-tabs">
												<li class="active"><a href="#card1" data-toggle="tab">图文</a>
												</li>
												<li><a href="#card2" data-toggle="tab">文字</a></li>
												<li><a href="#card3" data-toggle="tab">图片</a></li>
												<li><a href="#card4" data-toggle="tab">语言</a></li>
												<li><a href="#card5" data-toggle="tab">视频</a></li>
											</ul>
											<div id="myTabContent" class="tab-content card-contant">
												<div class="tab-pane fade in active" id="card1">
													<div class="panel-body">
														<div class="tab_cont_cover ">
															<div class="media_cover">
																<a href="javascript:;" data-toggle="modal"
																	data-target="#myModal"> <span
																	class="glyphicon glyphicon-plus" aria-hidden="true"></span>
																	从素材中选择
																</a>
															</div>
														</div>
														<div class="tab_cont_cover ">
															<div class="media_cover">
																<a href="javascript:;" data-toggle="modal"
																	data-target="#myModal"> <span
																	class="glyphicon glyphicon-plus" aria-hidden="true"></span>
																	新建图文消息
																</a>
															</div>
														</div>
													</div>

												</div>
												<div class="tab-pane fade" id="card2">
													<div class="panel-body">

														<textarea name="" rows="" cols="" autofocus="autofocus"
															style="height: 138px; outline-style: none; border: 0;">	</textarea>
													</div>

												</div>
												<div class="tab-pane fade" id="card3">
													<div class="panel-body">
														<div class="tab_cont_cover ">
															<div class="media_cover">
																<a href="javascript:;" data-toggle="modal"
																	data-target="#myModal"> <span
																	class="glyphicon glyphicon-plus" aria-hidden="true"></span>
																	从素材中选择
																</a>
															</div>
														</div>
														<div class="tab_cont_cover ">
															<div class="media_cover">
																<a href="javascript:;" data-toggle="modal"
																	data-target="#myModal"> <span
																	class="glyphicon glyphicon-plus" aria-hidden="true"></span>
																	上传图片
																</a>
															</div>
														</div>
													</div>
												</div>
												<div class="tab-pane fade" id="card4">
													<div class="panel-body">
														<div class="tab_cont_cover ">
															<div class="media_cover">
																<a href="javascript:;" data-toggle="modal"
																	data-target="#myModal"> <span
																	class="glyphicon glyphicon-plus" aria-hidden="true"></span>
																	从素材中选择
																</a>
															</div>
														</div>
														<div class="tab_cont_cover ">
															<div class="media_cover">
																<a href="javascript:;" data-toggle="modal"
																	data-target="#myModal"> <span
																	class="glyphicon glyphicon-plus" aria-hidden="true"></span>
																	上传语音
																</a>
															</div>
														</div>
													</div>
												</div>
												<div class="tab-pane fade" id="card5">
													<div class="panel-body">
														<div class="tab_cont_cover ">
															<div class="media_cover">
																<a href="javascript:;" data-toggle="modal"
																	data-target="#myModal"> <span
																	class="glyphicon glyphicon-plus" aria-hidden="true"></span>
																	从素材中选择
																</a>
															</div>
														</div>
														<div class="tab_cont_cover ">
															<div class="media_cover">
																<a href="javascript:;" data-toggle="modal"
																	data-target="#myModal"> <span
																	class="glyphicon glyphicon-plus" aria-hidden="true"></span>
																	新建视频
																</a>
															</div>
														</div>
													</div>
												</div>

											</div>
											<br>
											<p class="padding-top-sm">
												<button class="btn btn-success">群发</button>
											</p>

										</div>

									</div>
								</div>
							</div>
							<!-- 选择素材对话框 -->
							<div class="modal fade" tabindex="-1" role="dialog" id="myModal">
								<div class="modal-dialog" role="document" id="">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title">选择素材</h4>
										</div>
										<div class="modal-body" style="min-height: 400px;">
											<div class="modal-body-top">
												<div class="row">
													<div class="col-md-6">
														<form class="bs-example bs-example-form" role="form">

															<div class="input-group">

																<input type="text" class="form-control"
																	placeholder="标题/作者/摘要"> <span
																	class="input-group-btn">
																	<button class="btn btn-default" type="button">搜索</button>
																</span>
															</div>
															<br>

														</form>
													</div>
													<div class="col-md-6 clearfix">
														<button class="btn btn-primary pull-right" type="button">新建图文消息</button>
													</div>
												</div>
											</div>
											<hr>
											暂无素材

										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">取消</button>
											<button type="button" class="btn btn-primary">确定</button>
										</div>
									</div>

								</div>

							</div>
						</div>
						<div class="tab-pane" id="panel-563107">
							<br>
							<div class="row clearfix">
								<div class="col-md-12 column">
									<div class="panel panel-info">
										<div class="panel-heading">
											<h3 class="panel-title">已发送</h3>
										</div>
										<div class="panel-body">
											<div class="row clearfix">
												<div class="col-md-12 column">暂无消息</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../common/headJs.jsp"%>
	<script>
		$('.top-bar select').change(function() {

			if ($(this).val() == 1) {
				$('.topbar-item2').show();
			} else {
				$('.topbar-item2').hide();
			}
		})
	</script>
</body>
</html>