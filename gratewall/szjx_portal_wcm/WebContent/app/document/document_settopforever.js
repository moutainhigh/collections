function checkDateRange(){
	var valDom = document.getElementById('TopInvalidTime');
	var sTopInvalidTime = valDom.value;
	var sInter = sTopInvalidTime.split(" ");
	var sInterDate = sInter[0].split("-");
	var sInterDateYear = sInterDate[0];
	var sInterDateMonth = sInterDate[1];
	var sInterDateDay = sInterDate[2];
	var sInterTime = sInter[1].split(":");
	var sInterTimeHour = sInterTime[0];
	var sInterTimeMinute = sInterTime[1];

	//var regExp = /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})$/;
	//if(!regExp) {
	//	return String.format("不支持的日期格式[{0}]",_sFormat.toLowerCase());
	//}
	//var result = valDom.match(regExp);
	//if(!result){
	//	return String.format("[{0}]没有匹配日期格式[{1}]",_sDate, _sFormat.toLowerCase());
	//}

	var year = sInterDateYear;
	var month = sInterDateMonth;
	var day = sInterDateDay;
	var hour = sInterTimeHour;
	var minute = sInterTimeMinute;
		
	if(month < 1 || month > 12){
		alert("月份应该为1到12的整数");
		return false;
	}
	if (day < 1 || day > 31){
		alert("每个月的天数应该为1到31的整数");
		return false;
	}
	if ((month==4 || month==6 || month==9 || month==11) && day==31){   
		alert("该月不存在31号");
		return false;
	}   
	if (month==2){   
		var isleap=(year % 4==0 && (year % 100 !=0 || year % 400==0));   
		if (day>29){   
			alert("2月最多有29天");
			return false;
		}   
		if ((day==29) && (!isleap)){   
			alert("闰年2月才有29天");
			return false;
		}   
	}
	if(hour && hour<0 || hour>23){   
		alert("小时应该是0到23的整数");
		return false;
	}   
	if(minute && minute<0 || minute>59){   
		alert("分应该是0到59的整数");
		return false;
	}
	return true;
}