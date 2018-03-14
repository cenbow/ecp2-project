
// =================业务部分===============

/* 加载主页页头 */
function loadHeader() {
	var url = "/front/home/header";
	var parms = null;
	var callbackFunc = loadFooter; //header load 完毕后再load footer
	var containerId = "#header";
	loadPage(containerId, url, parms, callbackFunc);
}



/* 加载页尾 */
function loadFooter() {
	var url = "/front/home/footer";
	var parms = null;
	var callbackFunc = null;
	var containerId = "#footer";
	loadPage(containerId, url, parms, callbackFunc);
}


// =============页面加载初始化部分==================
function initPage() {
	loadHeader();
	//loadFooter();
}

// =============current page loaded ready=============
$(function() {
	initPage();
});