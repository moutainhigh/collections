function writeIframe(){
	var strIframe = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=gb2312'><style>"+
    "*{font-size: 12px; font-family: 宋体}"+
    ".bg{  color: "+ WebCalendar.lightColor +"; cursor: default; background-color: "+ WebCalendar.darkColor +";}"+
    "table#tableWeek td{ color: "+ WebCalendar.lightColor +";}"+
    "table#tableDay  td{ font-weight: bold;}"+
    "td#meizzYearHead, td#meizzYearMonth{color: "+ WebCalendar.wordColor +"}"+
    ".out { text-align: center; border-top: 1px solid "+ WebCalendar.DarkBorder +"; border-left: 1px solid "+ WebCalendar.DarkBorder +";"+
    "border-right: 1px solid "+ WebCalendar.lightColor +"; border-bottom: 1px solid "+ WebCalendar.lightColor +";}"+
    ".over{ text-align: center; border-top: 1px solid #FFFFFF; border-left: 1px solid #FFFFFF;"+
    "border-bottom: 1px solid "+ WebCalendar.DarkBorder +"; border-right: 1px solid "+ WebCalendar.DarkBorder +"}"+
    "input{ border: 1px solid "+ WebCalendar.darkColor +"; padding-top: 1px; height: 18; cursor: hand;"+
    "       color:"+ WebCalendar.wordColor +"; background-color: "+ WebCalendar.btnBgColor +"}"+
    "</style></head><body onselectstart='return false' style='margin: 0px' oncontextmenu='return false'>";
  if (WebCalendar.drag){ 
  	strIframe += "<scr"+"ipt language=javascript>"+
    "var drag=false, cx=0, cy=0, o = parent.WebCalendar.calendar; function document.onmousemove(){"+
    "if(parent.WebCalendar.drag && drag){if(o.style.left=='')o.style.left=0; if(o.style.top=='')o.style.top=0;"+
    "o.style.left = parseInt(o.style.left) + window.event.clientX-cx;"+
    "o.style.top  = parseInt(o.style.top)  + window.event.clientY-cy;}}"+
    "function dragStart(){cx=window.event.clientX; cy=window.event.clientY; drag=true;}</scr"+"ipt>"
  }
  strIframe += "<select name=tmpYearSelect id=tmpYearSelect onblur='parent.hiddenSelect(this)' style='z-index:1;position:absolute;top:3;left:18;display:none'"+
    										" onchange='parent.WebCalendar.thisYear =this.value; parent.hiddenSelect(this); parent.writeCalendar();'></select>"+
    					 "<select name=tmpMonthSelect id=tmpMonthSelect onblur='parent.hiddenSelect(this)' style='z-index:1; position:absolute;top:3;left:74;display:none'"+
    										" onchange='parent.WebCalendar.thisMonth=this.value; parent.hiddenSelect(this); parent.writeCalendar();'></select>"+
							 "<select name=tmpHourSelect id=tmpHourSelect onblur='parent.hiddenSelect(this)' style='z-index:1; position:absolute;top:145;left:43;display:none'"+
    										" onchange='parent.WebCalendar.thisHour=this.value; parent.hiddenSelect(this); parent.writeCalendar();'></select>"+
    					 "<select name=tmpMinuteSelect id=tmpMinuteSelect onblur='parent.hiddenSelect(this)' style='z-index:1; position:absolute;top:145;left:88;display:none'"+
    										" onchange='parent.WebCalendar.thisMinutes=this.value; parent.hiddenSelect(this); parent.writeCalendar();'></select>"+
    "<table id=tableMain class=bg border=0 cellspacing=2 cellpadding=0 width=142 height=187 style='table-layout:fixed;'>"+
    "<tr><td width=140 height=19 bgcolor='"+ WebCalendar.lightColor +"'>"+
    "    <table width=140 id=tableHead border=0 cellspacing=1 cellpadding=0><tr align=center>"+
    "    <td width=15 height=19 class=bg title='向前翻 1 月&#13;快捷键：←' style='cursor: hand' onclick='parent.prevM()'><b>&lt;</b></td>"+
    "    <td width=60 id=meizzYearHead  title='点击此处选择年份' onclick='parent.funYearSelect(parseInt(this.innerText, 10))'"+
    "        onmouseover='this.bgColor=parent.WebCalendar.darkColor; this.style.color=parent.WebCalendar.lightColor'"+
    "        onmouseout='this.bgColor=parent.WebCalendar.lightColor; this.style.color=parent.WebCalendar.wordColor'></td>"+
    "    <td width=50 id=meizzYearMonth title='点击此处选择月份' onclick='parent.funMonthSelect(parseInt(this.innerText, 10))'"+
    "        onmouseover='this.bgColor=parent.WebCalendar.darkColor; this.style.color=parent.WebCalendar.lightColor'"+
    "        onmouseout='this.bgColor=parent.WebCalendar.lightColor; this.style.color=parent.WebCalendar.wordColor'></td>"+
    "    <td width=15 class=bg title='向后翻 1 月&#13;快捷键：→' onclick='parent.nextM()' style='cursor: hand'><b>&gt;</b></td></tr></table>"+
    "</td></tr><tr><td height=18><table id=tableWeek border=1 width=140 cellpadding=0 cellspacing=0 ";
  if (WebCalendar.drag){
  	strIframe += "onmousedown='dragStart()' onmouseup='drag=false' onmouseout='drag=false'";
  }
  strIframe += " borderColorLight='"+ WebCalendar.darkColor +"' borderColorDark='"+ WebCalendar.lightColor +"'>"+
    "    <tr align=center height=18><td>日</td><td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td></tr></table>"+
    "</td></tr><tr><td valign=bottom bgcolor='"+ WebCalendar.lightColor +"'>"+"<table id=tableDay style='border-collapse:collapse;' width=140 border=0 cellspacing=1 cellpadding=0>";
  for(var x=0; x<5; x++){ 
  	strIframe += "<tr height=19>";
  	for(var y=0; y<7; y++){
  		strIframe += "<td class=out id='meizzDay"+ (x*7+y) +"'></td>";
    }
  	strIframe += "</tr>";
  }
  strIframe += "<tr align=center height=19>";
  for(var x=35; x<37; x++) strIframe += "<td class=out id='meizzDay"+ x +"'></td>";
  if(WebCalendar.formattype==5){//2007-12-19  zhouyi
	  strIframe +="<td colspan=2 id=meizzHourHead title='点击此处选择小时' onclick='parent.funHourSelect(parseInt(this.innerText, 10))'"+
	    "        onmouseover='this.bgColor=parent.WebCalendar.darkColor; this.style.color=parent.WebCalendar.lightColor'"+
	    "        onmouseout='this.bgColor=parent.WebCalendar.lightColor; this.style.color=parent.WebCalendar.wordColor'></td>";
	  strIframe +="<td colspan=3 id=meizzMinuteHead title='点击此处选择分钟' onclick='parent.funMinuteSelect(parseInt(this.innerText, 10))'"+
	    "        onmouseover='this.bgColor=parent.WebCalendar.darkColor; this.style.color=parent.WebCalendar.lightColor'"+
	    "        onmouseout='this.bgColor=parent.WebCalendar.lightColor; this.style.color=parent.WebCalendar.wordColor'></td>";
	}
  strIframe+="</tr>";
  strIframe +="<tr height=19><td colspan=5 align=center class=out><input class=bg name=today id=today type=button value='当前时间' onfocus='this.blur()' style='cursor: hand; width: 100%; height: 100%; border: 0; font-weight:bold' title='当前时间'"+
    "    onclick=\"parent.returnDate(new Date().getDate() +'/'+ (new Date().getMonth() +1) +'/'+ new Date().getFullYear()+'/'+ new Date().getHours()+'/'+ new Date().getMinutes())\"></td>";
  strIframe +="<td colspan=2 class=out title='关闭'><input class=bg style='cursor: hand; padding-top: 4px; width: 100%; height: 100%; border: 0; font-weight:bold' onfocus='this.blur()'"+
         " type=button value='关闭' onclick='parent.hiddenCalendar()'></td></tr></table>"+"</td></tr></table></body></html>";
  with(WebCalendar.iframe){
      document.writeln(strIframe);
      document.close();
      for(var i=0; i<37; i++){
          WebCalendar.dayObj[i] = eval("meizzDay"+ i);
          WebCalendar.dayObj[i].onmouseover = dayMouseOver;
          WebCalendar.dayObj[i].onmouseout  = dayMouseOut;
          WebCalendar.dayObj[i].onclick     = returnDate;
      }
      
      // 按键处理
      document.onkeydown = parent.calendar_onkeydown;
  }
}

function calendar_onkeydown()
{
	var win = WebCalendar.iframe.window;
	switch(win.event.keyCode){
		case 27 : hiddenCalendar(); break;
    	case 37 : prevM(); break; 
    	case 38 : prevY(); break; 
    	case 39 : nextM(); break; 
    	case 40 : nextY(); break;
    	case 84 : win.document.getElementById('today').click(); break;
    }
    
    win.event.returnValue= false;
}


//初始化日历的设置
function WebCalendar() {
	this.formattype = 5; 				//1 代表 2009-09-30 , 2 代表 2009/09/30 , 3 代表 20090930 , 4 代表 2009年09月30日 , 5 代表 2005-07-13 14:51:30
  this.dateNameObject = null;			//保存Date所在的Object
  //1 代表 鼠标事件在Text  	<input type='text' name='date' onfocus="javascript:calendar('date');">
  //2 代表 鼠标事件在Image  	<img src="search_results_view.gif"  onclick="javascript:calendar(document.getElementById('date1'))"> 
  this.clickimage = 2;
  this.oldmousedown = null;
  this.daysMonth  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
  this.day        = new Array(39);            //定义日历展示用的数组
  this.dayObj     = new Array(39);            //定义日期展示控件数组
  this.dateStyle  = null;                     //保存格式化后日期数组
  this.objExport  = null;                     //日历回传的显示控件
  this.eventSrc   = null;                     //日历显示的触发控件
  this.inputDate  = null;                     //转化外的输入的日期(d/m/yyyy)
  this.thisYear   = new Date().getFullYear(); //定义年的变量的初始值
  this.thisMonth  = new Date().getMonth()+ 1; //定义月的变量的初始值
  this.thisDay    = new Date().getDate();     //定义日的变量的初始值
  this.thisHour   = new Date().getHours();    //定义小时的变量初始值
  this.thisMinutes= new Date().getMinutes();  //定义分钟的变量初始值
  this.thisSecond = "00";                     //默认秒数
  this.today      = this.thisDay +"/"+ this.thisMonth +"/"+ this.thisYear;   //今天(d/m/yyyy)
  this.currentTime= this.today + " " + this.thisHour +":"+ this.thisMinutes;   //当前时间(d/m/yyyy 时:分)
  this.iframe     = null;			 //日历的 iframe 载体
  this.calendar   = null;			 //日历的层
  this.dateReg    = null;          //日历格式验证的正则式
  this.yearFall   = 50;           //定义年下拉框的年差值
  this.format     = ""; 			//回传日期的格式   
  this.drag       = true;         //是否允许拖动
  this.darkColor  = "#84C1FF";    //控件的暗色
  this.lightColor = "#FFFFFF";    //控件的亮色
  this.btnBgColor = "#ededed";    //控件的按钮背景色
  this.wordColor  = "#000000";    //控件的文字颜色
  this.wordDark   = "#DCDCDC";    //控件的暗文字颜色
  this.dayBgColor = "#F5F5FA";    //日期数字背景色
  this.todayColor = "#CCCC00";    //今天在日历上的标示背景色
  this.DarkBorder = "#D4D0C8";    //日期显示的立体表达色
}

var WebCalendar = new WebCalendar();


//主调函数
function calendar(dateName, formattype)
{
	if( dateName.disabled || dateName.readOnly ){
		return;
	}
	
	// 先关闭窗口
	hiddenCalendar();
	
    //var e = window.event.srcElement;		//获取鼠标事件所在的Object 
    var e=null;
    if (WebCalendar.clickimage == 1){
    	e = window.event.srcElement;
    	WebCalendar.dateNameObject = window.event.srcElement;
    }else{
    	e = dateName;
    	WebCalendar.dateNameObject = dateName;	
    }
    
    // 格式
    if( formattype != null ){
    	WebCalendar.formattype = formattype;
    }
    
    // 生成Iframe
    WebCalendar.calendar = getObjectById("innerWindow");
    WebCalendar.calendar.style.width = 144;
    WebCalendar.calendar.style.height = 187;
    
    WebCalendar.calendar.innerHTML = "<iframe name=innerWindowIframe scrolling=no frameborder=0 width='100%' height='100%'>";
    WebCalendar.iframe = window.frames("innerWindowIframe");
    
    writeIframe();
    WebCalendar.eventSrc = e;
 	if (arguments.length == 0){
 		WebCalendar.objExport = e;
	}else{ 
		WebCalendar.objExport = eval(arguments[0]);
	}
    WebCalendar.iframe.tableWeek.style.cursor = WebCalendar.drag ? "move" : "default";
 	
    var o = WebCalendar.calendar.style; 
    o.display = ""; 
    var needHeight = parseInt(o.height) + getElementPos(WebCalendar.dateNameObject).y + WebCalendar.dateNameObject.offsetHeight;
    if(document.body.offsetHeight < needHeight){
    	testResizeFrame(null, needHeight);
    }
    WebCalendar.iframe.document.body.focus();
    setInnerWindowPosition( WebCalendar.dateNameObject );
    //alert("left "+o.left+"  top "+o.top); 
    
    ////判断日期格式,取得正则表达式
	if(WebCalendar.formattype==1) {
		WebCalendar.dateReg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
	}else if(WebCalendar.formattype==2) {
		WebCalendar.dateReg =/^(\d{1,4})(\/|\/)(\d{1,2})\2(\d{1,2})$/; 
	}else if(WebCalendar.formattype==3) {
		WebCalendar.dateReg =/^(\d{1,4})(|\/)(\d{1,2})\2(\d{1,2})$/; 
	}else if(WebCalendar.formattype==4) {
		WebCalendar.dateReg =/^(\d{1,4})(年|\/)(\d{1,2})(月|\/)(\d{1,2})(日|\/)$/;	
	}else if(WebCalendar.formattype==5) {
		WebCalendar.dateReg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	}else{	    
		WebCalendar.dateReg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/; 
	}
    
    try{
        if (WebCalendar.objExport.value.trim() != ""){
        	//2005-07-02 解决日历输入框,的数据取得格式的问题
            WebCalendar.dateStyle = WebCalendar.objExport.value.trim().match(WebCalendar.dateReg);                             
            if (WebCalendar.dateStyle == null){
                WebCalendar.thisYear   = new Date().getFullYear();
                WebCalendar.thisMonth  = new Date().getMonth()+ 1;
                WebCalendar.thisDay    = new Date().getDate();
                WebCalendar.thisHour   = new Date().getHours();
                WebCalendar.thisMinutes= new Date().getMinutes();
                
                // alert("原文本框里的日期有错误！\n可能与你定义的显示时分有冲突！");
                writeCalendar();
                return false;
            }else{
            	if (WebCalendar.formattype!=4){	//处理中文分隔符
	                WebCalendar.thisYear   = parseInt(WebCalendar.dateStyle[1], 10);
	                WebCalendar.thisMonth  = parseInt(WebCalendar.dateStyle[3], 10);
	                WebCalendar.thisDay    = parseInt(WebCalendar.dateStyle[4], 10);
	                WebCalendar.thisHour   = isNaN(parseInt(WebCalendar.dateStyle[5], 10))?new Date().getHours():parseInt(WebCalendar.dateStyle[5], 10);
	                WebCalendar.thisMinutes= isNaN(parseInt(WebCalendar.dateStyle[6], 10))?new Date().getMinutes():parseInt(WebCalendar.dateStyle[6], 10);
	                WebCalendar.inputDate  = parseInt(WebCalendar.thisDay, 10) +"/"+ parseInt(WebCalendar.thisMonth, 10) +"/"+parseInt(WebCalendar.thisYear, 10) +"/"+parseInt(WebCalendar.thisHour, 10) +"/"+parseInt(WebCalendar.thisMinutes, 10); 
	                writeCalendar();
            	}else{
            	 	  WebCalendar.thisYear   = parseInt(WebCalendar.dateStyle[1], 10);
	                WebCalendar.thisMonth  = parseInt(WebCalendar.dateStyle[3], 10);
	                WebCalendar.thisDay    = parseInt(WebCalendar.dateStyle[5], 10);
	                WebCalendar.inputDate  = parseInt(WebCalendar.thisDay, 10) +"/"+ parseInt(WebCalendar.thisMonth, 10) +"/"+parseInt(WebCalendar.thisYear, 10); 
	                writeCalendar();
            	}
            }
        }else {
        	writeCalendar();
        }
    } catch(e){
    	writeCalendar();
    }

	//鼠标离开自动隐藏
	WebCalendar.oldmousedown = document.onmousedown;
	document.onmousedown = hiddenCalendar;
}


//月份的下拉框
function funMonthSelect(){
    var m = isNaN(parseInt(WebCalendar.thisMonth, 10)) ? new Date().getMonth() + 1 : parseInt(WebCalendar.thisMonth);
    var e = WebCalendar.iframe.document.getElementById('tmpMonthSelect');
    for (var i=1; i<13; i++) e.options.add(new Option(i +"月", i));
    e.style.display = ""; e.value = m; e.focus(); window.status = e.style.top;
}


//年份的下拉框
function funYearSelect(){
    var n = WebCalendar.yearFall;
    var e = WebCalendar.iframe.document.getElementById('tmpYearSelect');
    var y = isNaN(parseInt(WebCalendar.thisYear, 10)) ? new Date().getFullYear() : parseInt(WebCalendar.thisYear);
        y = (y <= 1000)? 1000 : ((y >= 9999)? 9999 : y);
    var min = (y - n >= 1000) ? y - n : 1000;
    var max = (y + n <= 9999) ? y + n : 9999;
        min = (max == 9999) ? max-n*2 : min;
        max = (min == 1000) ? min+n*2 : max;
    for (var i=min; i<=max; i++) e.options.add(new Option(i +"年", i));
    e.style.display = ""; e.value = y; e.focus();
}

//小时的下拉框
function funHourSelect(){
    var m = isNaN(parseInt(WebCalendar.thisHour, 10)) ? new Date().getHours() : parseInt(WebCalendar.thisHour);
    var e = WebCalendar.iframe.document.getElementById('tmpHourSelect');
    for (var i=0; i<24; i++) e.options.add(new Option(i, i));
    e.style.display = ""; e.value = m; e.focus(); window.status = e.style.top;
}

//分钟的下拉框
function funMinuteSelect(){
    var m = isNaN(parseInt(WebCalendar.thisMinutes, 10)) ? new Date().getMinutes() : parseInt(WebCalendar.thisMinutes);
    var e = WebCalendar.iframe.document.getElementById('tmpMinuteSelect');
    for (var i=0; i<60; i++) e.options.add(new Option(" "+i +" ", i));
    e.style.display = ""; e.value = m; e.focus(); window.status = e.style.top;
}


//往前翻月份
function prevM(){
    WebCalendar.thisDay = 1;
    if (WebCalendar.thisMonth==1){
        WebCalendar.thisYear--;
        WebCalendar.thisMonth=13;
    }
    WebCalendar.thisMonth--; writeCalendar();
}


//往后翻月份
function nextM(){
    WebCalendar.thisDay = 1;
    if (WebCalendar.thisMonth==12){
        WebCalendar.thisYear++;
        WebCalendar.thisMonth=0;
    }
    WebCalendar.thisMonth++; writeCalendar();
}


//往前翻 Year
function prevY(){WebCalendar.thisDay = 1; WebCalendar.thisYear--; writeCalendar();}


//往后翻 Year
function nextY(){WebCalendar.thisDay = 1; WebCalendar.thisYear++; writeCalendar();}
function hiddenSelect(e){for(var i=e.options.length; i>-1; i--)e.options.remove(i); e.style.display="none";}
function getObjectById(id){ if(document.all) return(eval("document.all."+ id)); return(eval(id)); }

function hiddenCalendar(){
	var	obj = getObjectById("innerWindow");
	if( obj != null ){
		obj.style.display = "none";
	}
	
	// 设置焦点
	if( WebCalendar.dateNameObject != null ){
		if( WebCalendar.dateNameObject.disabled != true ){
			WebCalendar.dateNameObject.focus();
		};
		
		WebCalendar.dateNameObject = null;
	}
	
	// 原始的onmousedown
	if( WebCalendar.oldmousedown != null ){
		document.onmousedown = WebCalendar.oldmousedown;
		WebCalendar.oldmousedown();
		WebCalendar.oldmousedown = null;
	}
}


//日期自动补零程序
function appendZero(n){
	return(("00"+ n).substr(("00"+ n).length-2));
}
//function String.prototype.trim(){
//	return this.replace(/(^\s*)|(\s*$)/g,"");
//}

String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g,"");
}

function dayMouseOver(){
    this.className = "over";
    this.style.backgroundColor = WebCalendar.darkColor;
    if(WebCalendar.day[this.id.substr(8)].split("/")[1] == WebCalendar.thisMonth)
    	this.style.color = WebCalendar.lightColor;
    var a = WebCalendar.day[this.id.substr(8)].split("/"); 
    var d = new Date();
    this.title = a[2] +"-"+ appendZero(a[1]) +"-"+ appendZero(a[0])+ " "+ d.getHours() +":"+ d.getMinutes();
}


function dayMouseOut(){
    this.className = "out"; 
    var d = WebCalendar.day[this.id.substr(8)], a = d.split("/");
    this.style.removeAttribute('backgroundColor');
    if(a[1] == WebCalendar.thisMonth && d != WebCalendar.today){
        if(WebCalendar.dateStyle && a[0] == parseInt(WebCalendar.dateStyle[4], 10))
					this.style.color = WebCalendar.lightColor;
		else
			this.style.color = WebCalendar.wordColor;
    }
}


//对日历显示的数据的处理程序
function writeCalendar(){
    var y = WebCalendar.thisYear;
    var m = WebCalendar.thisMonth; 
    var d = WebCalendar.thisDay;
    var hour = WebCalendar.thisHour;
    var minute = WebCalendar.thisMinutes;
    WebCalendar.daysMonth[1] = (0==y%4 && (y%100!=0 || y%400==0)) ? 29 : 28;
    if (!(y<=9999 && y >= 1000 && parseInt(m, 10)>0 && parseInt(m, 10)<13 && parseInt(d, 10)>0)){
        alert("对不起，你输入了错误的日期！");
        WebCalendar.thisYear   = new Date().getFullYear();
        WebCalendar.thisMonth  = new Date().getMonth()+ 1;
        WebCalendar.thisDay    = new Date().getDate();
        WebCalendar.thisHour   = new Date().getHours();
        WebCalendar.thisMinutes= new Date().getMinutes();
    }
    y = WebCalendar.thisYear;
    m = WebCalendar.thisMonth;
    d = WebCalendar.thisDay;
    hour = isNaN(WebCalendar.thisHour)? new Date().getHours():WebCalendar.thisHour;
    minute = isNaN(WebCalendar.thisMinutes)? new Date().getHours():WebCalendar.thisMinutes;
    
    WebCalendar.iframe.meizzYearHead.innerText  = y +" 年";
    WebCalendar.iframe.meizzYearMonth.innerText = parseInt(m, 10) +" 月";
    
    if(WebCalendar.formattype==5){//2007-12-19  zhouyi
	    WebCalendar.iframe.meizzHourHead.innerText  = hour +" 时";
	    WebCalendar.iframe.meizzMinuteHead.innerText = minute +" 分";
	  }
    
    WebCalendar.daysMonth[1] = (0==y%4 && (y%100!=0 || y%400==0)) ? 29 : 28; //闰年二月为29天
    var w = new Date(y, m-1, 1).getDay();
    var prevDays = m==1  ? WebCalendar.daysMonth[11] : WebCalendar.daysMonth[m-2];
    //这三个 for 循环为日历赋数据源（数组 WebCalendar.day）格式是 d/m/yyyy
    for(var i=(w-1); i>=0; i--) {
        WebCalendar.day[i] = prevDays +"/"+ (parseInt(m, 10)-1) +"/"+ y +"/"+ hour +"/"+ minute;
        if(m==1) WebCalendar.day[i] = prevDays +"/"+ 12 +"/"+ (parseInt(y, 10)-1) +"/"+ hour +"/"+ minute;
        prevDays--;
    }
    for(var i=1; i<=WebCalendar.daysMonth[m-1]; i++) WebCalendar.day[i+w-1] = i +"/"+ m +"/"+ y +"/"+ hour +"/"+ minute;
    for(var i=1; i<39-w-WebCalendar.daysMonth[m-1]+1; i++){
        WebCalendar.day[WebCalendar.daysMonth[m-1]+w-1+i] = i +"/"+ (parseInt(m, 10)+1) +"/"+ y +"/"+ hour +"/"+ minute;
        if(m==12) WebCalendar.day[WebCalendar.daysMonth[m-1]+w-1+i] = i +"/"+ 1 +"/"+ (parseInt(y, 10)+1) +"/"+ hour +"/"+ minute;
    }
    
    //这个循环是根据源数组写到日历里显示
    for(var i=0; i<37; i++) {
        var a = WebCalendar.day[i].split("/");
        WebCalendar.dayObj[i].innerText    = a[0];
        WebCalendar.dayObj[i].title        = a[2] +"-"+ appendZero(a[1]) +"-"+ appendZero(a[0])+ " "+ appendZero(a[3]) +":"+ appendZero(a[4]);
        WebCalendar.dayObj[i].bgColor      = WebCalendar.dayBgColor;
        WebCalendar.dayObj[i].style.color  = WebCalendar.wordColor;        
        if ((i<10 && parseInt(WebCalendar.day[i], 10)>20) || (i>27 && parseInt(WebCalendar.day[i], 10)<12))
            WebCalendar.dayObj[i].style.color = WebCalendar.wordDark;
        if (WebCalendar.inputDate==WebCalendar.day[i])    //设置输入框里的日期在日历上的颜色
        {WebCalendar.dayObj[i].bgColor = WebCalendar.darkColor; WebCalendar.dayObj[i].style.color = WebCalendar.lightColor;}
        if (a[0]+"/"+a[1]+"/"+a[2] == WebCalendar.today)      //设置今天在日历上反应出来的颜色
        {WebCalendar.dayObj[i].bgColor = WebCalendar.todayColor; WebCalendar.dayObj[i].style.color = WebCalendar.lightColor;}
    }
}


//根据日期格式等返回用户选定的日期
function returnDate() {
    if(WebCalendar.objExport){
        var returnValue;
        var a = (arguments.length==0) ? WebCalendar.day[this.id.substr(8)].split("/") : arguments[0].split("/");
        //判断日期格式,取得正则表达式和日期样本
      	if(WebCalendar.formattype==1) {
					WebCalendar.dateReg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
					WebCalendar.format = "2009-09-09";
				}else if(WebCalendar.formattype==2) {
					WebCalendar.dateReg =/^(\d{1,4})(\/|\/)(\d{1,2})\2(\d{1,2})$/;
					WebCalendar.format = "2009/09/09"; 
				}else if(WebCalendar.formattype==3) {
					WebCalendar.dateReg =/^(\d{1,4})(|\/)(\d{1,2})\2(\d{1,2})$/; 
					WebCalendar.format = "20090909";
				}else if(WebCalendar.formattype==4) {
					WebCalendar.dateReg =/^(\d{1,4})(年|\/)(\d{1,2})(月|\/)(\d{1,2})(日|\/)$/;	
					WebCalendar.format = "2009年09月09日";
				}else if(WebCalendar.formattype==5) {
					WebCalendar.dateReg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2})$/;
					WebCalendar.format = "2005-07-13 14:51";
				}else{	    
					WebCalendar.dateReg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/; 
					WebCalendar.format = "2009-09-09";
				}
		
        var d = WebCalendar.format.match(WebCalendar.dateReg);
        if(d==null){
        	alert("你设定的日期输出格式不对！\r\n\r\n请重新定义 WebCalendar.format ！"); 
        	return false;
        }
        
        //判断返回的日期格式是否要补零
        var flag = false;
        if (d[3].length==2){
        	flag = true;	
        }
        
        //2009-09-09  2009/09/09  20090909 
        if (WebCalendar.formattype==1 || WebCalendar.formattype==2 || WebCalendar.formattype==3 ){        
	        if (flag == true){
	        	returnValue = a[2] +d[2]+ appendZero(a[1]) +d[2]+ appendZero(a[0]);	
	        }else{
	        	returnValue = a[2] +d[2]+ a[1] +d[2]+ a[0];
	        }  
        }
        
        //2009年09月09日
        if (WebCalendar.formattype==4){        
	        if (flag == true){	        	
	        	returnValue = a[2] +d[2]+ appendZero(a[1]) +d[4]+ appendZero(a[0])+ d[6];	
	        }else{	        	
	        	returnValue = a[2] +d[2]+ a[1] +d[4]+ a[0]+ d[6];
	        }  
        }
        
        //2005-07-13 14:51
        if (WebCalendar.formattype==5){
        	s = WebCalendar.thisSecond;
	        if (flag == true){
	        	returnValue = a[2] +d[2]+ appendZero(a[1]) +d[2]+ appendZero(a[0])+ " "+ appendZero(a[3]) +":"+ appendZero(a[4]) +":"+ appendZero(s);	
	        }else{
	        	returnValue = a[2] +d[2]+ a[1] +d[2]+ a[0]+ " "+  a[3]  +":"+ a[4] +":"+ s;
	        } 	  
	        var display = returnValue.substring(0, returnValue.length-3);
	        returnValue = display;      
        }
        
        WebCalendar.objExport.value = returnValue;
        hiddenCalendar();
    }
}


//日期输入控件的控制函数
function makeDate(Obj){
	var y=eval("document.all."+Obj+"YearInput").value;
	var m=eval("document.all."+Obj+"MonthInput").value;
	var d=eval("document.all."+Obj+"DayInput").value;
	var hour=eval("document.all."+Obj+"HourInput").value;
	var minute=eval("document.all."+Obj+"MinuteInput").value;
	//使用 datetime 数据类型存储从 1753 年 1 月 1 日至 9999 年 12 月 31 日的日期
	if(isDate(y,m,d) && y>=1753){
		eval("document.all."+Obj).value=y+"-"+m+"-"+d;
	}else{
		if(y!=""||m!=""||d!="")
			return false;
		eval("document.all."+Obj).value = "";
	}
	return true;
}


function splitDate(Obj){
	var dt=eval("document.all."+Obj).value;
	eval("document.all."+Obj+"YearInput").value=dt.substring(0,dt.indexOf("-"));
	eval("document.all."+Obj+"MonthInput").value=dt.substring(dt.indexOf("-")+1,dt.lastIndexOf("-"));
	eval("document.all."+Obj+"DayInput").value=dt.substring(dt.lastIndexOf("-")+1,dt.indexOf(" "));
	eval("document.all."+Obj+"HourInput").value=dt.substring(dt.lastIndexOf(" ")+1,dt.indexOf(":"));
	eval("document.all."+Obj+"MinuteInput").value=dt.substring(dt.lastIndexOf(":")+1);
}


function isDate(year,month,day){
	month = month-1;
	var dt = new Date(year,month,day);
	if(year==dt.getFullYear()&&month==dt.getMonth()&&day==dt.getDate())
		return true;
	else
		return false;
}


//显示日期输入控件(输入框名称，初始日期值，是否需要日历控件)
function writeDateInput(inputName,inputValue,needCalendar){
	var dateInput = "<table cellspacing=0 cellpadding=0><tr><td><div class=cBoxnosize style='width:80'>"+
		"<input class=yearInput type=text name="+inputName+"YearInput size=4 maxlength=4 onkeydown=\"if(event.keyCode==39)event.keyCode=9;\" onpropertychange=\"if(value.length==4)this.nextSibling.nextSibling.select();\" onblur=\"if(value.search(/[^0-9]/)>-1){alert('请输入数字');this.select();}\">"+
		"-<input class=monthInput type=text name="+inputName+"MonthInput size=2 maxlength=2 onkeydown=\"if(event.keyCode==39)event.keyCode=9;if(event.keyCode==37)this.previousSibling.previousSibling.select();\" onpropertychange=\"if(value.length==2)this.nextSibling.nextSibling.select();\" onblur=\"if(value.search(/[^0-9]/)>-1){alert('请输入数字');this.select();}\">"+
		"-<input class=dayInput type=text name="+inputName+"DayInput size=2 maxlength=2 onkeydown=\"if(event.keyCode==39)event.keyCode=9;if(event.keyCode==37)this.previousSibling.previousSibling.select();\" onblur=\"if(value.search(/[^0-9]/)>-1){alert('请输入数字');this.select();}\">"+
		" "+"<input class=hourInput type=text name="+inputName+"HourInput size=2 maxlength=2 onkeydown=\"if(event.keyCode==39)event.keyCode=9;if(event.keyCode==37)this.previousSibling.previousSibling.select();\" onblur=\"if(value.search(/[^0-9]/)>-1){alert('请输入数字');this.select();}\">"+
		":"+"<input class=minuteInput type=text name="+inputName+"MinuteInput size=2 maxlength=2 onkeydown=\"if(event.keyCode==37)this.previousSibling.previousSibling.select();\" onblur=\"if(value.search(/[^0-9]/)>-1){alert('请输入数字');this.select();}\">"+		
		"<input type=hidden name="+inputName+" onpropertychange=\"splitDate('"+inputName+"')\">";
	if(needCalendar==true)
		dateInput = dateInput+"</div></td><td>&nbsp;<input type=button value=\"日期\" onclick=\"if(!makeDate('"+inputName+"'))alert('对不起，你输入了错误的日期！');calendar(document.all."+inputName+");\" class=cButton></td></tr></table>";
	else
		dateInput = dateInput+"</div></td></tr></table>";
	document.writeln(dateInput);
	if(inputValue!="")
		eval("document.all."+inputName).value=inputValue;
}


function writeDateInputs(inputName,inputId,inputValue,needCalendar){
	var dateInput = "<table><tr><td><div class=cBoxnosize style='width:80'>"+
		"<input class=yearInput type=text name="+inputId+"YearInput size=4 maxlength=4 onkeydown=\"if(event.keyCode==39)event.keyCode=9;\" onpropertychange=\"if(value.length==4)this.nextSibling.nextSibling.select();\" onblur=\"if(value.search(/[^0-9]/)>-1){alert('请输入数字');this.select();}\">"+
		"-<input class=monthInput type=text name="+inputId+"MonthInput size=2 maxlength=2 onkeydown=\"if(event.keyCode==39)event.keyCode=9;if(event.keyCode==37)this.previousSibling.previousSibling.select();\" onpropertychange=\"if(value.length==2)this.nextSibling.nextSibling.select();\" onblur=\"if(value.search(/[^0-9]/)>-1){alert('请输入数字');this.select();}\">"+
		"-<input class=dayInput type=text name="+inputName+"DayInput size=2 maxlength=2 onkeydown=\"if(event.keyCode==39)event.keyCode=9;if(event.keyCode==37)this.previousSibling.previousSibling.select();\" onblur=\"if(value.search(/[^0-9]/)>-1){alert('请输入数字');this.select();}\">"+
		" "+"<input class=hourInput type=text name="+inputName+"HourInput size=2 maxlength=2 onkeydown=\"if(event.keyCode==39)event.keyCode=9;if(event.keyCode==37)this.previousSibling.previousSibling.select();\" onblur=\"if(value.search(/[^0-9]/)>-1){alert('请输入数字');this.select();}\">"+
		":"+"<input class=minuteInput type=text name="+inputName+"MinuteInput size=2 maxlength=2 onkeydown=\"if(event.keyCode==37)this.previousSibling.previousSibling.select();\" onblur=\"if(value.search(/[^0-9]/)>-1){alert('请输入数字');this.select();}\">"+		
		"<input type=hidden name="+inputName+" id="+inputId+" onpropertychange=\"splitDate('"+inputId+"')\">";
	if(needCalendar==true)
		dateInput = dateInput+"</div></td><td><input type=button value=\"日期\" onclick=\"if(!makeDate('"+inputId+"'))alert('对不起，你输入了错误的日期！');calendar(document.all."+inputId+");\" class=cButton></td></tr></table>";
	else
		dateInput = dateInput+"</div></td></tr></table>";
	document.writeln(dateInput);
	if(inputValue!="")
		eval("document.all."+inputId).value=inputValue;
}

