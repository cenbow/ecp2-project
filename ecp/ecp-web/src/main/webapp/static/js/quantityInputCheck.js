/* 数量检查-按键处理 */
function onkeyup_check(e) {
	if (e.value.length == 1) {
		e.value = e.value.replace(/[^1-9]/g, '1')
	} else {
		if (e.value.length == 0) {
			e.value = '1'
		} else {
			e.value = e.value.replace(/\D/g, '')
		}
	}
}

/* 数量检查-paste */
function paste_check(e) {
	if (e.value.length == 1) {
		e.value = e.value.replace(/[^1-9]/g, '1');
	} else {
		e.value = e.value.replace(/\D/g, '');
	}
}