<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>- 主页示例</title>
<%@ include file="../common/headCss.jsp"%>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

.peopletag {
	width: 750px;
	margin: 0 auto;
	position: relative;
}

.peopletag img {
	position: absolute;
	top: 0;
	left: 50%;
	width: 130px;
	margin-left: -65px;
	height: 500px;
}

.tag-left {
	float: left;
	width: 260px;
	text-align: left;
	padding-top: 26px;
}

.tag-right {
	float: right;
	width: 260px;
	text-align: right;
	padding-top: 26px;
}

.tag-item {
	border: 1px solid #e5e5e5;
	border-radius: 3px;
	text-align: center;
	margin: 20px 60px;
	position: relative;
}

.tag-left .tag-item:before {
	content: '';
	position: absolute;
	width: 100px;
	height: 1px;
	background: #e5e5e5;
	left: 150px;
	top: 30px;
}

.tag-right .tag-item:before {
	content: '';
	position: absolute;
	width: 100px;
	height: 1px;
	background: #e5e5e5;
	right: 150px;
	top: 30px;
}

.tag-item .p-title {
	font-size: 16px;
	font-weight: 500;
	color: #333;
	line-height: 34px;
}

.tag-item p {
	font-size: 13px;
	color: #999;
	line-height: 24px;
}
</style>
</head>
<body>

	<br />
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<div class="card">
					<div class="tabs-container" id="user-icon">
						<ul class="nav nav-tabs" id="top_tab">
							<li class="active"><a data-toggle="tab" href="#tab-1"
								aria-expanded="true">用户列表</a></li>
							<li class=""><a data-toggle="tab" href="#tab-2"
								aria-expanded="false">详细信息</a></li>
						</ul>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane active">
								<div class="card-content">
									<div class="table-responsive"
										style="background: #fff; margin-top: 10px; border: 1px solid #e5e5e5;">
										<div id="dataTables-example_wrapper"
											class="dataTables_wrapper form-inline" role="grid">
											<div class="modal-header">
												<!-- -->
												<div class="col-xs-10  clearfix">
													<form class="form-horizontal" role="form" id="search-form">
														<div class="form-group fl" style="width: 100%">
															<div>
																<select class="dropdown fl"
																	style="border: 1px solid #cfdadd; height: 30px; font-size: 14px;"
																	tabindex="7">
																	<option value="5" selected>标签维度</option>
																	<option value="0">兴趣爱好</option>
																	<option value="1">偏好品类</option>
																	<option value="2">消费频次</option>
																	<option value="3">分享数量</option>
																	<option value="4">信用等级</option>
																</select> <input type="text" style="width: 180px;"
																	class="form-control" id="search-keyword" name="name"
																	placeholder="请输入关键字">
																<button type="button" class="btn btn-primary"
																	id="search-submit-btn">查询</button>
																<button type="button" class="btn btn-default"
																	id="search-submit-btn">统计结果 : 3</button>
																热门标签： <a href="#">购物</a> <a href="#">时尚</a> <a href="#">白富美</a>
																<a href="#">辣妈</a> 读书 音乐 购物
															</div>


														</div>
													</form>
												</div>
											</div>



											<div style="margin: 20px">

												<div id="item-div">
													<%@ include file="userIconTable.jsp"%>
												</div>
												<hr>
												<div class='panel-body'>
													<!-- 等级分析 -->
													<div id="level-analyze" style="width: 100%; height: 500px;"></div>
												</div>

												<hr>

												<div class='panel-body'>
													<!-- 组织关系分析 -->
													<div id="organize-analyze"
														style="width: 100%; height: 500px;"></div>
												</div>

												<hr>
												<div class='panel-body'>
													<!-- 省份分析 -->
													<div id="province-analyze"
														style="width: 100%; height: 1000px;"></div>
												</div>
											</div>
										</div>

									</div>

								</div>
							</div>

							<div id="tab-2" class="tab-pane" style="margin-top: 30px;">
								<div id="user-detail">
									<!-- Nav tabs -->
									<ul class="nav nav-tabs" role="tablist">
										<li role="presentation" class="active"><a href="#t1"
											aria-controls="home" role="tab" data-toggle="tab">用户属性</a></li>
										<li role="presentation"><a href="#t2"
											aria-controls="profile" role="tab" data-toggle="tab">商品关联</a></li>
										<li role="presentation"
											onclick="javascript:initActionLogAnalyze();"><a
											href="#t3" aria-controls="messages" role="tab"
											data-toggle="tab">行为记录</a></li>
									</ul>

									<!-- Tab panes -->
									<div class="tab-content">
										<!-- 用户属性选项卡 -->
										<div role="tabpanel" class="tab-pane active" id="t1">
											<div class="panel-body">
												<div class="peopletag">
													<div class="tag-left">
														<div class="tag-item">
															<p class="p-title">性别</p>
															<p>男</p>
														</div>
														<div class="tag-item">
															<p class="p-title">兴趣爱好</p>
															<p>干红、雷司令</p>
														</div>
														<div class="tag-item">
															<p class="p-title">职业</p>
															<p>IT</p>
														</div>
														<div class="tag-item">
															<p class="p-title">浏览</p>
															<p>干红、雷司令、起泡</p>
														</div>

													</div>
													<img src="static/images/user-icon.jpg" />
													<div class="tag-right">
														<div class="tag-item">
															<p class="p-title">年龄</p>
															<p>00后</p>
														</div>
														<div class="tag-item">
															<p class="p-title">购买</p>
															<p>干红</p>
														</div>
														<div class="tag-item">
															<p class="p-title">收藏</p>
															<p>雷司令、起泡</p>
														</div>
														<div class="tag-item">
															<p class="p-title">分享</p>
															<p>干红</p>
														</div>

													</div>

												</div>
												<!--代码部分begin-->
											</div>
										</div>
										<!-- 商品关联选项卡 -->
										<div role="tabpanel" class="tab-pane" id="t2">
											<div class="row">
												<div class="col-sm-12">
													<div class="card-content">
														<div class="table-responsive"
															style="background: #fff; margin-top: 30px; border: 1px solid #e5e5e5;">
															<div id="dataTables-example_wrapper"
																class="dataTables_wrapper form-inline" role="grid">
																<div class="modal-header">
																	<div class="col-xs-10  clearfix">
																		<form class="form-horizontal" role="form"
																			id="search-form">
																			<div class="form-group fl" style="width: 100%">
																				<div>
																					<select class="dropdown fl" id="filter-cond-name"
																						style="border: 1px solid #cfdadd; height: 30px; font-size: 14px;"
																						tabindex="7">
																						<option value="" selected>全部</option>
																						<option value="user_id">书籍</option>
																						<option value="username">数码</option>
																					</select> <input type="text" style="width: 260px;"
																						class="form-control" id="filter-cond-val"
																						placeholder="请输入关键字">
																					<button type="button" class="btn btn-primary"
																						id="filter-submit-btn">查询</button>
																				</div>

																			</div>
																		</form>
																	</div>
																</div>
																<br />
																<div class="modal-header">
																	<div class="panel panel-default">
																		<div class="panel-heading">
																			<h3 class="panel-title">用户浏览商品列表</h3>
																		</div>
																		<div class="panel-body">
																			<table
																				class="table table-striped table-bordered table-hover dataTable no-footer dataTables-example"
																				id="dataTables-example"
																				aria-describedby="dataTables-example_info">
																				<thead>
																					<tr>
																						<th>商品分类</th>
																						<th>商品名称</th>
																						<th>操作</th>
																					</tr>
																				</thead>
																				<tbody>
																					<tr class="gradeA">
																						<td>数码</td>
																						<td>苹果手机</td>
																						<td>
																							<button type="button"
																								onclick="javascript:showActionLog();">查看用户行为记录</button>
																						</td>
																					</tr>
																					<tr class="gradeC">
																						<td>电脑</td>
																						<td>Thinkpad</td>
																						<td>
																							<button type="button"
																								onclick="javascript:showActionLog();">查看用户行为记录</button>
																						</td>
																					</tr>
																					<tr class="gradeA">
																						<td>书籍</td>
																						<td>Java编程思想</td>
																						<td>
																							<button type="button"
																								onclick="javascript:showActionLog();">查看用户行为记录</button>
																						</td>
																					</tr>
																					<tr class="gradeA">
																						<td>服装</td>
																						<td>短袖</td>
																						<td>
																							<button type="button"
																								onclick="javascript:showActionLog();">查看用户行为记录</button>
																						</td>
																					</tr>
																					<tr class="gradeX">
																						<td>箱包</td>
																						<td>瑞士军刀</td>
																						<td>
																							<button type="button"
																								onclick="javascript:showActionLog();">查看用户行为记录</button>
																						</td>
																					</tr>
																					<tr class="gradeC">
																						<td>钟表</td>
																						<td>天王</td>
																						<td>
																							<button type="button"
																								onclick="javascript:showActionLog();">查看用户行为记录</button>
																						</td>
																					</tr>
																					<tr class="gradeC">
																						<td>珠宝</td>
																						<td>玉坠</td>
																						<td>
																							<button type="button"
																								onclick="javascript:showActionLog();">查看用户行为记录</button>
																						</td>
																					</tr>
																				</tbody>
																			</table>
																		</div>
																	</div>
																</div>
															</div>
														</div>

													</div>
												</div>
											</div>
										</div>
										<!-- 行为记录选项卡 -->
										<div role="tabpanel" class="tab-pane" id="t3">
											<div class="row">
												<div class="col-sm-12">

													<div class='panel-body'>

														<div id="action-log" style="width: 100%; height: 400px;"></div>
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
		</div>

		<%@ include file="../common/headJs.jsp"%>
		<script type="text/javascript" src="static/js/userIcon.js"></script>
		<script src="echarts/echarts.js"></script>
		<!-- <script src="echarts/src/echarts.js"></script> -->
		<script src="echarts/map/js/china.js"></script>

		<script type="text/javascript">
			/*
			 * 点击列表中的用户画像时，显示用户详细信息
			 */
			function showUserDetail() {
				$('#user-icon a[href="#tab-2"]').tab('show');//打开用户详细信息选项卡
			}

			/*
			 * 点击用户行为记录选项卡时，显示用户行为记录
			 */
			function showActionLog() {
				$('#user-detail a[href="#t3"]').tab('show');//打开用户行为记录选项卡
				initActionLogAnalyze();//初始化用户行为记录图表
			}

			/*
			 * 随机数
			 */
			function randomData() {
				return Math.round(Math.random() * 1000);
			}

			/*
			 * 初始化用户行为记录图表
			 */
			function initActionLogAnalyze() {

				$("#action-log").css("width", $(document).width());//设置图表展示宽度

				/* 用户行为记录部分 */
				// 基于准备好的dom，初始化echarts实例
				var actionLogChart = echarts.init(document
						.getElementById('action-log'));
				// 指定图表的配置项和数据
				/* var option = {
					title : {
						text : '会员行为记录',
						subtext : '图表内容仅供参考',
						x : 'center'
					},
					tooltip : {
						trigger : 'item',
						formatter : "{a} <br/>{b} : {c} ({d}%)"
					},
					legend : {
						orient : 'vertical',
						left : 'left',
						data : [ '访问', '购买', '收藏', '分享', '互动' ]
					},
					series : [ {
						name : '会员行为记录',
						type : 'pie',
						radius : '55%',
						center : [ '50%', '60%' ],
						data : [ {
							value : randomData(),
							name : '访问'
						}, {
							value : randomData(),
							name : '购买'
						}, {
							value : randomData(),
							name : '收藏'
						}, {
							value : randomData(),
							name : '分享'
						}, {
							value : randomData(),
							name : '互动'
						} ],
						itemStyle : {
							emphasis : {
								shadowBlur : 10,
								shadowOffsetX : 0,
								shadowColor : 'rgba(0, 0, 0, 0.5)'
							}
						}
					} ]
				}; */
				option = {
					color : [ '#3398DB' ],
					title : {
						text : '会员行为记录',
						subtext : '图表内容仅供参考',
						x : 'center'
					},
					tooltip : {
						trigger : 'axis',
						axisPointer : { // 坐标轴指示器，坐标轴触发有效
							type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
						}
					},
					/* grid: {
					    left: '3%',
					    right: '4%',
					    bottom: '3%',
					    containLabel: true
					}, */
					xAxis : [ {
						type : 'category',
						data : [ '访问', '购买', '收藏', '分享', '互动' ],
						axisTick : {
							alignWithLabel : true
						}
					} ],
					yAxis : [ {
						type : 'value'
					} ],
					series : [ {
						name : '频次',
						type : 'bar',
						barWidth : '60%',
						data : [ randomData(), randomData(), randomData(),
								randomData(), randomData() ]
					} ]
				};
				// 使用刚指定的配置项和数据显示图表。
				actionLogChart.setOption(option);
				/* end */

			}

			/* 等级分析部分 */
			// 基于准备好的dom，初始化echarts实例
			var levelAnalyzeChart = echarts.init(document
					.getElementById('level-analyze'));
			// 指定图表的配置项和数据
			var option = {
				title : {
					text : '会员等级分析',
					subtext : '图表内容仅供参考',
					x : 'center'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				legend : {
					orient : 'vertical',
					left : 'left',
					data : [ '普通会员', '白金会员', '黄金会员', '钻石vip会员' ]
				},
				series : [ {
					name : '会员等级分析',
					type : 'pie',
					radius : '75%',
					center : [ '50%', '60%' ],
					data : [ {
						value : randomData(),
						name : '普通会员'
					}, {
						value : randomData(),
						name : '白金会员'
					}, {
						value : randomData(),
						name : '黄金会员'
					}, {
						value : randomData(),
						name : '钻石vip会员'
					} ],
					itemStyle : {
						emphasis : {
							shadowBlur : 10,
							shadowOffsetX : 0,
							shadowColor : 'rgba(0, 0, 0, 0.5)'
						}
					}
				} ]
			};
			// 使用刚指定的配置项和数据显示图表。
			levelAnalyzeChart.setOption(option);
			/* end */

			/* 组织关系分析部分 */
			// 基于准备好的dom，初始化echarts实例
			var organizeAnalyzeChart = echarts.init(document
					.getElementById('organize-analyze'));
			// 指定图表的配置项和数据
			var option = {
				title : {
					text : '组织关系分析',
					subtext : '图表内容仅供参考',
					x : 'center'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				legend : {
					orient : 'vertical',
					left : 'left',
					data : [ '中诚动力', '百度', '阿里巴巴', '网易', '新浪' ]
				},
				series : [ {
					name : '组织关系分析',
					type : 'pie',
					radius : '75%',
					center : [ '50%', '60%' ],
					data : [ {
						value : randomData(),
						name : '中诚动力'
					}, {
						value : randomData(),
						name : '百度'
					}, {
						value : randomData(),
						name : '阿里巴巴'
					}, {
						value : 600,
						name : '网易'
					}, {
						value : randomData(),
						name : '新浪'
					} ],
					itemStyle : {
						emphasis : {
							shadowBlur : 10,
							shadowOffsetX : 0,
							shadowColor : 'rgba(0, 0, 0, 0.5)'
						}
					}
				} ]
			};
			// 使用刚指定的配置项和数据显示图表。
			organizeAnalyzeChart.setOption(option);
			/* end */

			/* 地域分析部分 */
			// 基于准备好的dom，初始化echarts实例
			var provinceAnalyzeChart = echarts.init(document
					.getElementById('province-analyze'));
			// 指定图表的配置项和数据
			var option = {
				title : {
					text : '地域分析',
					subtext : '图表内容仅供参考',
					x : 'center'
				},
				tooltip : {
					trigger : 'item',
				/* formatter: '{b}' */
				},
				series : [ {
					name : '中国',
					type : 'map',
					mapType : 'china',
					selectedMode : 'single',
					label : {
						normal : {
							show : true
						},
						emphasis : {
							show : true
						}
					},
					data : [ {
						name : '北京',
						value : randomData()
					}, {
						name : '天津',
						value : randomData()
					}, {
						name : '上海',
						value : randomData()
					}, {
						name : '重庆',
						value : randomData()
					}, {
						name : '河北',
						value : randomData()
					}, {
						name : '河南',
						value : randomData()
					}, {
						name : '云南',
						value : randomData()
					}, {
						name : '辽宁',
						value : randomData()
					}, {
						name : '黑龙江',
						value : randomData()
					}, {
						name : '湖南',
						value : randomData()
					}, {
						name : '安徽',
						value : randomData()
					}, {
						name : '山东',
						value : randomData()
					}, {
						name : '新疆',
						value : randomData()
					}, {
						name : '江苏',
						value : randomData()
					}, {
						name : '浙江',
						value : randomData()
					}, {
						name : '江西',
						value : randomData()
					}, {
						name : '湖北',
						value : randomData()
					}, {
						name : '广西',
						value : randomData()
					}, {
						name : '甘肃',
						value : randomData()
					}, {
						name : '山西',
						value : randomData()
					}, {
						name : '内蒙古',
						value : randomData()
					}, {
						name : '陕西',
						value : randomData()
					}, {
						name : '吉林',
						value : randomData()
					}, {
						name : '福建',
						value : randomData()
					}, {
						name : '贵州',
						value : randomData()
					}, {
						name : '广东',
						value : randomData()
					}, {
						name : '青海',
						value : randomData()
					}, {
						name : '西藏',
						value : randomData()
					}, {
						name : '四川',
						value : randomData()
					}, {
						name : '宁夏',
						value : randomData()
					}, {
						name : '海南',
						value : randomData()
					}, {
						name : '台湾',
						value : randomData()
					}, {
						name : '香港',
						value : randomData()
					}, {
						name : '澳门',
						value : randomData()
					} ]
				} ]
			};
			// 使用刚指定的配置项和数据显示图表。
			provinceAnalyzeChart.setOption(option);
			/* end */

			var isShowUserDetail = $
			{
				showUserDetail
			}
			//alert(isShowUserDetail);
			if (isShowUserDetail != null && isShowUserDetail) {
				$('#user-icon a[href="#tab-2"]').tab('show');//打开用户详细信息选项卡
			}
		</script>
</body>
</html>