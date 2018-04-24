//============加载页面通用部分===============
/* 
	功能描述：加载页
	参数说明：containerId 窗口id
		   url:请求加载的页面地址
		   parms:参数
		   callbackFunc:加载成功后的回调函数
 */
function loadPage(containerId, url, parms, callbackFunc) {
	var parms = parms;
	url = BASE_CONTEXT_PATH + url;
	if (callbackFunc == null) {
		callbackFunc = defaultCallbackFunc();
	}	
	//console.log("loadpage---url:"+url);
	$(containerId).load(url, parms, callbackFunc);
}

/* 默认的回调函数 */
function defaultCallbackFunc() {
	// alert("load page success!");
}
