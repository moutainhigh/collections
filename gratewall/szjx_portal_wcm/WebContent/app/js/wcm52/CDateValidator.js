function CDateValidator(){	
}

CDateValidator.inherits(CBaseValidator);
CDateValidator.registerMethod("validate", CDateValidator_validate);
CDateValidator.registerMethod("isLeapYear", CDateValidator_isLeapYear);
CDateValidator.registerMethod("validateDateFormat", CDateValidator_validateDateFormat);

function CDateValidator_validate(){
	if(this.oElement == null){
		this.oElement = arguments[0];
	}
	if(!this.superMethod("validate")){
		return false;
	}
	this.sErrorInfo	+= (this.sErrorInfo.length>1?"\n":"");
	return this.validateDateFormat(this.oElement.value, this.oElement.DateFormat);
}

function CDateValidator_isLeapYear(year){
	if((year%4==0&&year%100!=0)||(year%400==0)){
		return true;
	}
	this.sErrorInfo += "[" + year + "] " + (wcm.LANG.WCM52_ALERT_39 || "不是闰年！");
	return false;
}

function CDateValidator_validateDateFormat(_sDate, format){
	_sDate = TRSString.trim(_sDate);
	var nLen = _sDate.length;
	if(nLen==0)return true;
	
	var regexp,value,index, sDesc;
	var year,month,day;
	var iyear,imonth,iday;
	var fmt,regfmt,ordfmt,fmtDesc;
	var dateArray;

	if(_sDate==null){
		this.sErrorInfo += wcm.LANG.WCM52_ALERT_41 || "对不起，您指定了无效的日期格式！";
		return false;
	}
	if(format==null || format==""){
		this.sErrorInfo += wcm.LANG.WCM52_ALERT_41 || "对不起，您指定了无效的日期格式！";
		return false;
	}
	
	value = _sDate;

	fmt=new Array("yyyy/mm/dd",
				  "mm/dd/yyyy",
				  "dd/mm/yyyy",
				  "yyyy-mm-dd",
				  "mm-dd-yyyy",
				  "dd-mm-yyyy");
	fmtDesc=new Array("yyyy/MM/dd(As:2002/12/24)",
				  "MM/dd/yyyy(As:12/24/2002)",
				  "dd/MM/yyyy(As:24/12/2002)",
				  "yyyy-MM-dd(As:2002-12-24)",
				  "MM-dd-yyyy(As:12-24-2002)",
				  "dd-MM-yyyy(As:24-12-2002)");				  

	regfmt=new Array("^([0-9]{4})\/([0-9]{2})\/([0-9]{2})$",
					"^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$",
					"^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$",
					"^([0-9]{4})\-([0-9]{2})\-([0-9]{2})$",
					"^([0-9]{2})\-([0-9]{2})\-([0-9]{4})$",
					"^([0-9]{2})\-([0-9]{2})\-([0-9]{4})$");

	ordfmt=new Array("123","312","321", "123","312","321");

	format=format.toLowerCase();
	for(index=0;index<fmt.length;index++){
		if(format==fmt[index]){ 
			regexp = new RegExp(regfmt[index]);
			sDesc  = fmtDesc[index];
			iyear=parseInt(ordfmt[index].charAt(0));
			imonth=parseInt(ordfmt[index].charAt(1));
			iday=parseInt(ordfmt[index].charAt(2));
			break;
		}
	}

	if(index==fmt.length){
		this.sErrorInfo += wcm.LANG.WCM52_ALERT_42 || "您设定的日期格式有误！不支持这种日期格式！";
		return false;
	}

	if(regexp.test(value)){
		dateArray=value.match(regexp);
		year=dateArray[iyear];
		month=dateArray[imonth];
		day=dateArray[iday];
		//CTRSAction_alert("The Date you have filled is:\nYear:"+year+"\nMonth:"+month+"\nDay:"+day);
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
					this.sErrorInfo += wcm.LANG.WCM52_ALERT_48 || "闰年的二月有 29 天！";
					return false;
				}	
			}//end 2 month
			if((month==4||month==6||month==9||month==11)&&(day>30)){
				this.sErrorInfo += wcm.LANG.WCM52_ALERT_49 || "当前月份最多只有30天！";
				return false;
			}//end 30days
		}//end day
	}else{
		this.sErrorInfo += String.format("您输入的日期[{0}] 不匹配合法的日期格式！ \n 一个正确的日期格式应当为：{1}",  _sDate ,sDesc);
		return false;
	}//end formate
	return true;
}