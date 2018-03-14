//$(function(){
	/*
	 * ajax请求通用部分
	 */
	$.ajaxSetup({
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		complete:function(XMLHttpRequest,textStatus){
			//show_loaded();
			var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus，
			if(sessionstatus!=null && sessionstatus=="timeout"){
				console.log("登录超时，重新登录！");
				//关闭所有model
				/*$('.modal').map(function() {
				    $(this).modal('hide');
				});*/
				//提示，并自动跳转
				//show_msg('登录超时，请重新登录！','login');
			}
		},
		error:function(){
			console.log("ajax request error !");
		}
	 });
//});