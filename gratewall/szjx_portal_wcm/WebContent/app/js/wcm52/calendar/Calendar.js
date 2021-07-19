$import('com.trs.util.Other');
$import('wcm52.calendar.CTRSCalendar_Obj');
$import('wcm52.calendar.calendar_lang.cn_utf8');
$importCSS('wcm52.calendar.calendar_style.calendar-blue');
$import('wcm52.calendar.CTRSCalendar');
$import('com.trs.dialog.Dialog');

CTRSAction_alert = function(msg){
	$alert(msg, function(){
		$dialog().hide();
	});
}

function $compareDate(dateStr1, dateStr2, dateFormat){
	dateFormat = dateFormat || "%y-%m-%d";
	date1 = Date.parseDate(dateStr1, dateFormat);
	date2 = Date.parseDate(dateStr2, dateFormat);
	return date1.getTime() - date2.getTime();
}