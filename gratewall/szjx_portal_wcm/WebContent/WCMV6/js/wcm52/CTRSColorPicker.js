//中文
function CTRSColorPicker_openPicker(_sElName){
	var elColorHidden = document.all("id_Color_"+_sElName);
	if(elColorHidden == null){
		CTRSAction_alert("Exception");
		return;
	}

/*	var nPosX = document.body.scrollLeft + window.event.clientX;
	var nPosY = document.body.scrollTop  + window.event.clientY;

	var sColor = showModalDialog('../wcm_use/colorpicker.htm',window,'status:no;dialogLeft:'+nPosX+';dialogTop:'+nPosY+';dialogWidth:260px;dialogHeight:200px');
*/
	var args =  new Object();
	args.value = elColorHidden.value;
	
	var sColor = 
	showModalDialog('../js/wcm52/color_select/colordialog.html', args,'status:no;dialogWidth:370px;dialogHeight:240px') 

	if(sColor == null || sColor.length<=0){
		return;
	}
	elColorHidden.value = sColor;
	document.all("tdColorDisplay").bgColor = sColor;
}

function CTRSColorPicker_create(_sElName, _sInitColor){
	if(this.getHelper() == null){
		this.init();
	}

	var sInitColor = _sInitColor || "#000000";

	var sHTML = ""
			+"<TABLE BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"0\" STYLE=\"WIDTH:16;HEIGHT:16;cursor:hand\">" 
			+"<TR><TD><IMG SRC=\"../js/wcm52/color_select/icon_colorpicker.gif\" WIDTH=\"16\" HEIGHT=\"12\" alt=\"设定颜色\" onclick=\"TRSColorPicker.openPicker('"+_sElName+"');\"></TD></TR>"
			+"<input type=\"hidden\" name=\""+_sElName+"\" id=\"id_Color_"+_sElName+"\">"
			+"<TR><TD Name=\"tdColorDisplay\" ID=\"tdColorDisplay\" BGCOLOR=\""+sInitColor+"\" STYLE=\"WIDTH:16;HEIGHT:4\"></TD></TR>" 
			+"</TABLE>";

	document.write(sHTML);

	var elColorHidden = document.all("id_Color_"+_sElName);
	if(elColorHidden == null){
		CTRSAction_alert("Exception");
		return;
	}

	elColorHidden.value = _sInitColor || "";
}

function CTRSColorPicker_init(){
	if(this.getHelper() != null)return;

	var sHTML = ""
			+"<OBJECT id=\"dlgHelper\" CLASSID=\"clsid:3050f819-98b5-11cf-bb82-00aa00bdce0b\" width=\"0px\" height=\"0px\">" 
			+"</OBJECT> ";
	document.write(sHTML);
}

function CTRSColorPicker_getHelper(){
	if(this.Helper == null)
		this.Helper = document.all("dlgHelper");
	
	return this.Helper;
}

function CTRSColorPicker(){
//Define Properties
	this.Helper		= null;

//Define Method
	this.create		= CTRSColorPicker_create;
	this.init		= CTRSColorPicker_init;
	this.getHelper	= CTRSColorPicker_getHelper;
	this.openPicker = CTRSColorPicker_openPicker;
}

var TRSColorPicker = new CTRSColorPicker();
