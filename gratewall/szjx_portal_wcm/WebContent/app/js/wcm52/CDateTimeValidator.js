function CDateTimeValidator(){	
}

CDateTimeValidator.inherits(CBaseValidator);
CDateTimeValidator.registerMethod("validate", CDateTimeValidator_validate);
CDateTimeValidator.registerMethod("isLeapYear", CDateTimeValidator_isLeapYear);
CDateTimeValidator.registerMethod("validateDateFormat", CDateTimeValidator_validateDateFormat);

function CDateTimeValidator_validate(){
	if(this.oElement == null){
		this.oElement = arguments[0];
	}
	if(!this.superMethod("validate")){
		return false;
	}
	this.sErrorInfo	+= (this.sErrorInfo.length>1?"\n":"");
	return this.validateDateFormat(this.oElement.value, this.oElement.DateFormat);
}

function CDateTimeValidator_isLeapYear(year){
	if((year%4==0&&year%100!=0)||(year%400==0)){
		return true;
	}
	this.sErrorInfo += String.format("[{0}] 不是闰年！",year);
	return false;
}

function CDateTimeValidator_validateDateFormat(_sDate, format){
	_sDate = TRSString.trim(_sDate);
	var nLen = _sDate.length;
	if(nLen==0)return true;
	
	var regexp,value,index, sDesc;
	var year,month,day,hour,minute;
	var iyear,imonth,iday,ihour,iminute;
	var fmt,regfmt,ordfmt,fmtDesc;
	var dateArray;
	var format_arr;
	var formats = new Array(); //保存符合的格式

	if(_sDate==null){
		this.sErrorInfo += wcm.LANG.WCM52_ALERT_40 || "对不起，您输入的信息为空！";
		return false;
	}
	if(format==null || format==""){
		this.sErrorInfo += wcm.LANG.WCM52_ALERT_41 || "对不起，您指定了无效的日期格式！";
		return false;
	}
	
	value = _sDate;

	fmt=new Array("yyyy/mm/dd hh:mm",
				  "mm/dd/yyyy hh:mm",
				  "dd/mm/yyyy hh:mm",
				  "yyyy-mm-dd hh:mm",
				  "mm-dd-yyyy hh:mm",
				  "dd-mm-yyyy hh:mm",
		
				  "yyyy/mm/dd",
				  "mm/dd/yyyy",
				  "dd/mm/yyyy",
				  "yyyy-mm-dd",
				  "mm-dd-yyyy",
				  "dd-mm-yyyy");
	fmtDesc=new Array("yyyy/MM/dd HH:mm(As:2002/12/24 11:34)",
				  "MM/dd/yyyy HH:mm(As:12/24/2002 11:34)",
				  "dd/MM/yyyy HH:mm(As:24/12/2002 11:34)",
				  "yyyy-MM-dd HH:mm(As:2002-12-24 11:34)",
				  "MM-dd-yyyy HH:mm(As:12-24-2002 11:34)",
				  "dd-MM-yyyy HH:mm(As:24-12-2002 11:34)",
		
				  "yyyy/MM/dd(As:2002/12/24)",
				  "MM/dd/yyyy(As:12/24/2002)",
				  "dd/MM/yyyy(As:24/12/2002)",
				  "yyyy-MM-dd(As:2002-12-24)",
				  "MM-dd-yyyy(As:12-24-2002)",
				  "dd-MM-yyyy(As:24-12-2002)");

	regfmt=new Array("^([0-9]{4})\/([0-9]{1,2})\/([0-9]{1,2}) ([0-9]{1,2}):([0-9]{1,2})$",
					"^([0-9]{1,2})\/([0-9]{1,2})\/([0-9]{4}) ([0-9]{1,2}):([0-9]{1,2})$",
					"^([0-9]{1,2})\/([0-9]{1,2})\/([0-9]{4}) ([0-9]{1,2}):([0-9]{1,2})$",
					"^([0-9]{4})\-([0-9]{1,2})\-([0-9]{1,2}) ([0-9]{1,2}):([0-9]{1,2})$",
					"^([0-9]{1,2})\-([0-9]{1,2})\-([0-9]{4}) ([0-9]{1,2}):([0-9]{1,2})$",
					"^([0-9]{1,2})\-([0-9]{1,2})\-([0-9]{4}) ([0-9]{1,2}):([0-9]{1,2})$",
		
					"^([0-9]{4})\/([0-9]{1,2})\/([0-9]{1,2})$",
					"^([0-9]{1,2})\/([0-9]{1,2})\/([0-9]{4})$",
					"^([0-9]{1,2})\/([0-9]{1,2})\/([0-9]{4})$",
					"^([0-9]{4})\-([0-9]{1,2})\-([0-9]{1,2})$",
					"^([0-9]{1,2})\-([0-9]{1,2})\-([0-9]{4})$",
					"^([0-9]{1,2})\-([0-9]{1,2})\-([0-9]{4})$");

	ordfmt=new Array("12345","31245","32145", "12345","31245","32145",
					 "12300","31200","32100", "12300","31200","32100");

	format=format.toLowerCase();
	format_arr = format.split(",");
	for(index=0;index<fmt.length;index++){
		for(var j=0; j<format_arr.length; j++) {
			if(format_arr[j]==fmt[index]){
				formats.push(index);
			}
		}
	}

	if(formats.length==0){
		this.sErrorInfo += "您设定的日期格式有误！不支持这种日期格式！";
		return false;
	}

	for(var i=0; i<formats.length; i++) {
		index = formats[i];
		regexp = new RegExp(regfmt[index]);
		if(sDesc == null || sDesc.length == 0) {
			sDesc = fmtDesc[index];
		} else {
			sDesc += String.format(" 或是{0}",fmtDesc[index]);
		}
		iyear=parseInt(ordfmt[index].charAt(0));
		imonth=parseInt(ordfmt[index].charAt(1));
		iday=parseInt(ordfmt[index].charAt(2));
		ihour=parseInt(ordfmt[index].charAt(3));
		iminute=parseInt(ordfmt[index].charAt(4));

		if(regexp.test(value)){
			dateArray=value.match(regexp);
			year=dateArray[iyear];
			month=dateArray[imonth];
			day=dateArray[iday];
			hour=dateArray[ihour];
			minute=dateArray[iminute];
			//CTRSAction_alert("The Date you have filled is:\nYear:"+year+"\nMonth:"+month+"\nDay:"+day+"\nHour:"+hour+"\nMinute:"+minute);
			if(year<1949){
				this.sErrorInfo += wcm.LANG.WCM52_ALERT_44 || "[年] 必须大于 1949！";
				return false;
			}			
			if(month<0||month>12){
				this.sErrorInfo += wcm.LANG.WCM52_ALERT_45 || "[月] 必须在 1-12 之间！";
				return false;
			}//end month
			if(day<0||day>31){
				this.sErrorInfo += wcm.LANG.WCM52_ALERT_46 || "[日] 必须在 1-31 之间！";
				return false;
			}else{ 
				if(month==2){ 
					if(this.isLeapYear(year)&&day>29){
						this.sErrorInfo += wcm.LANG.WCM52_ALERT_47 || "闰年的二月最多只有29天！";
						return false;
					}
					if(!this.isLeapYear(year)&&day>28){
						this.sErrorInfo += wcm.LANG.WCM52_ALERT_48 || "闰年的二月有 1到28 天！";
						return false;
					}	
				}//end 2 month
				if((month==4||month==6||month==9||month==11)&&(day>30)){
					this.sErrorInfo += wcm.LANG.WCM52_ALERT_49 || "当前月份最多只有30天！";
					return false;
				}//end 30days
			}//end day
			if(hour<0||hour>24){
				this.sErrorInfo += wcm.LANG.WCM52_ALERT_50 || "[时] 必须在 1-24 之间！";
				return false;
			}//end hour
			if(minute<0||minute>60){
				this.sErrorInfo += wcm.LANG.WCM52_ALERT_51 || "[分] 必须在 1-60 之间！";
				return false;
			}//end minute
			return true;
		}else if(i == formats.length-1){
			this.sErrorInfo += String.format("您输入的日期[{0}] 不匹配合法的日期格式！ \n 一个正确的日期格式应当为：{1}", _sDate, sDesc);
			return false;
		}//end formate
	}
	return true;
}