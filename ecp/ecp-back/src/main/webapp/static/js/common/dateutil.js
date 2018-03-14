//================================
//日期，在原有日期基础上，增加days天数，默认增加1天
function addDate(date, days) {
    if (days == undefined || days == '') {
        days = 1;
    }
    var date = new Date(date);
    date.setDate(date.getDate() + days);
    var month = date.getMonth() + 1;
    var day = date.getDate();
    return date.getFullYear() + '-' + getFormatDate(month) + '-' + getFormatDate(day);
}

// 日期月份/天的显示，如果是1位数，则在前面加上'0'
function getFormatDate(arg) {
    if (arg == undefined || arg == '') {
        return '';
    }

    var re = arg + '';
    if (re.length < 2) {
        re = '0' + re;
    }

    return re;
}

/**
 * 返回中文格式日期
 * @param date  日期 2017-07-21
 * @returns  返回  2017年07月21日
 */
function getCNFormatDate(date){
	var date = new Date(date);
    /*date.setDate(date.getDate() + days);*/
    var month = date.getMonth() + 1;
    var day = date.getDate();
    return date.getFullYear() + '年' + getFormatDate(month) + '月' + getFormatDate(day)+'日';
}