
//------------------UI 交互------------------------
/**
 * 打开增加四项费用窗口
 * @returns
 */
function openAddFourFeeDialog() {
	//modal-container-306690
	console.log("debug!");
	$('#modal-container-306690').modal({
		backdrop : 'static',
		keyboard : false
	});
}

/* close dialog :增加四项费用窗口*/
function closeAddFourFeeDialog() {	
	$("#modal-container-306690").modal("hide");
}


//-------------------------------
$(function(){
	//增加四项费用button: click event
	$('#btn-add-fourfee').on('click',function(){loadAddFourfeeDialog();});  //先加载后显示对话框
});