var imgleft = 0;
var imgtop = 0;
function initImgLT(){
	//\u8ba1\u7b97\u7248\u9762\u56fe\u7684(left,top)\u5750\u6807,\u4e3a\u8ba1\u7b97maparea\u7684\u5750\u6807\u505a\u51c6\u5907
	//\u53ef\u80fd\u9700\u8981\u4e9b\u7ec6\u5fae\u7684\u8c03\u6574
	imgleft = 0;
	imgtop = 0;
	var img = document.getElementById("mapimg");
	var imgparent = mapimg.parentNode;	
	while(typeof imgparent == "object" && imgparent.tagName.toUpperCase() != "BODY"){
		imgleft += imgparent.offsetLeft - imgparent.style.borderLeft;
		imgtop += imgparent.offsetTop - imgparent.style.borderTop;
		imgparent = imgparent.parentNode;
	}
	imgleft -= IMG_OFFSET_LEFT;
	imgtop -= IMG_OFFSET_TOP;
}

$(document).ready(function() {
	initImgLT();	
	$("table[@class=tableNode]").click(function(){			
		$("div[@id=tableContent]").hide();
		$(this).find("div[@id=tableContent]").show();
		var mapAreaData = $(this).find("div[@id=MapAreaData]");			
		$("#pagepicmap").html(mapAreaData.html());	
		$("#pagepicmap > Area").hover(
			function(){			
				var crds = $(this).attr("coords").split(",");			
				var regiondiv = $("#regiondiv")
				regiondiv.width($(this).width());
				regiondiv.height($(this).height());
				regiondiv.css({left:(imgleft+parseInt(crds[0]))+"px"});
				regiondiv.css({top:(imgtop+parseInt(crds[1]))+"px"});
				regiondiv.show();				
			},
			function(){
				$("#regiondiv").hide();
			}
		);

		
		$("#mapimg").attr({src:mapAreaData.attr("_picsrc")});
	});
	
	if(!$("#mapimg").attr("src")||$("#mapimg").attr("src").indexOf("loading")!=-1){
		var mapAreaData = $("#MapAreaData");
		$("#mapimg").attr({src:mapAreaData.attr("_picsrc")});
		$("#pagepicmap").html(mapAreaData.html());
		$("#pagepicmap > Area").hover(
			function(){		
				var _self = $(this);
				var crds = _self.attr("coords").split(",");			
				var regiondiv = $("#regiondiv")
				regiondiv.width(_self.width());
				regiondiv.height(_self.height());
				regiondiv.css({left:(imgleft+parseInt(crds[0]))+"px"});
				regiondiv.css({top:(imgtop+parseInt(crds[1]))+"px"});				
				regiondiv.show();
			},
			function(){
				$("#regiondiv").hide();
			}
		);
		//*/	
	
		$("div[@id=tableContent]").hide();		
		$("#tableContent").show();		
	};		
	initHistoryNav();
	// comment out if need.
	if(PRINT_NUM > PAGE_SIZE){
		initQuickPageNavPanel();
	}
});

$(window).resize(function(){	
	initImgLT();
});

//===================
//\u5bfc\u822a\u76f8\u5173

var PRINT_NUM = 30; //\u62a5\u7eb8\u7248\u9762\u6570,\u6839\u636e\u5b9e\u9645\u60c5\u51b5\u8bbe\u7f6e
var PAGE_SIZE = 10; //\u6bcf\u9875\u663e\u793a\u7248\u9762\u6570.\u4e0e\u6a21\u677f\u4e2d<TRS_CHANNELS pagesize="xx">\u7684pagesize\u4e00\u81f4
var PAGE_NAME = "default.htm";//\u4e3a\u907f\u514d[/wcmdemo/page/]\u8fd9\u6837\u7684path\u51fa\u9519,\u6307\u5b9a\u6587\u4ef6\u540d,\u4e0e\u6a21\u677f\u4e2d\u5206\u9875\u7f6e\u6807\u4e00\u81f4.

/*
 *	\u521d\u59cb\u5316\u7248\u9762\u8df3\u8f6c\u9762\u677f,\u6a21\u677f\u4e2d<div id="quickpagenav_panel"></div>
 */
function initQuickPageNavPanel(){	
	var sHtml = "\u5171&nbsp;<span style='font-size:16px;color:#9f0000;text-decoration:underline;padding:2px'>" + PRINT_NUM +"</span>&nbsp\u7248&nbsp;&nbsp";
	var currRange = calPrintRange();
	sHtml += "\u73b0\u5728\u662f\u7b2c&nbsp;<span style='font-size:16px;color:#9f0000;text-decoration:underline;padding:2px'>" + currRange[0] + "</span>&nbsp;\u5230&nbsp;";
	sHtml += "<span style='font-size:16px;color:#9f0000;text-decoration:underline;padding:2px'>" + currRange[1] + "</span>&nbsp;\u7248";
	sHtml += "&nbsp;&nbsp;&nbsp;&nbsp;\u5230\u7b2c&nbsp;<input id='print2jump' type='text' style='width:20px;height:18px' />&nbsp;\u7248&nbsp;<button style='width:25px;heigth:16px' onclick='quickJump2Print()'>GO</button>"
	$("#quickpagenav_panel").html(sHtml);
}

/*
 *	\u8ba1\u7b97\u5f53\u524d\u9875\u7248\u9762\u8303\u56f4
 */
function calPrintRange(){
	var pagename = window.location.pathname;
	pagename = pagename.substr(pagename.lastIndexOf("/")+1);
	pagename = pagename.substring(0,pagename.indexOf("."));
	var ix = pagename.lastIndexOf("_");
	if(ix == -1){
		return [1,PAGE_SIZE];
	}

	var currPage = pagename.substring(ix+1,pagename.lastIndexOf("."));
	var startPrint = PAGE_SIZE * parseInt(currPage);
	if(startPrint+PAGE_SIZE <= PRINT_NUM){
		return [startPrint+1,startPrint+PAGE_SIZE];		
	}
	
	return [startPrint+1,PRINT_NUM];
}

/*
 *	\u5feb\u901f\u7248\u9762\u8df3\u8f6c
 */
function quickJump2Print(){
	var printNumber = parseInt(document.getElementById("print2jump").value);
	if(isNaN(printNumber)){
		alert("\u8bf7\u8f93\u5165\u4e00\u4e2a\u6709\u6548\u7684\u6570\u5b57!");
		return false;
	}

	if(printNumber <=0 || printNumber > PRINT_NUM){
		alert("\u8f93\u5165\u7684\u7248\u9762\u5e8f\u53f7\u65e0\u6548!\u8bf7\u8f93\u5165[1--" + PRINT_NUM + "]\u8303\u56f4\u5185\u7684\u6570\u5b57");
		return false;
	}
	
	var currPage = calPrintRange();	
	if(printNumber >= currPage[0] && printNumber <= currPage[1]){//\u672c\u9875\u7248\u9762\u8df3\u8f6c
		var temp = $("table[@class=tableNode]");		
		$(temp[printNumber-currPage[0]]).click();	
		temp = null;
	}else{//\u8de8\u9875\u7248\u9762\u8df3\u8f6c	
		var pageName = window.location.pathname;		
		pageName = pageName.substring(pageName.lastIndexOf("/")+1,pageName.length);
		/*!
		 * \u7531\u4e8eWCM\u9ed8\u8ba4\u8bbf\u95ee\u680f\u76ee\u6982\u89c8\u53ef\u4ee5\u6ca1\u6709\u6587\u4ef6\u540d
		 * \u5373\u51fa\u73b0:[/wcmdemo/page/]\u8fd9\u6837\u7684path
		 * \u4e3a\u907f\u514d\u51fa\u9519,\u9700\u8981\u6307\u5b9a\u8fd9\u4e2a\u6587\u4ef6\u540d
		 */
		if(pageName.indexOf(".") == -1){
			pageName = PAGE_NAME;
		}
		var parts = pageName.split(".");
		var name = parts[0];
		var sufix = parts[1];
		var ix = name.lastIndexOf("_");
		if(ix != -1){
			name = name.substring(0,ix);
		}

		var pageNum = Math.ceil(printNumber / PAGE_SIZE);
		if(pageNum > 1){
			name += "_" + (pageNum - 1);
		}

		pageName = "./" + name + "." + sufix;		
		pageName += "?PrintNumber=" + (PAGE_SIZE+printNumber-pageNum*PAGE_SIZE);
		window.location.assign(pageName);		
	}	
}

/*
 * \u4eceurl\u4e2d\u5f97\u5230\u8981\u8df3\u8f6c\u7684\u7248\u9762\u7f16\u53f7(\u5047\u5b9aurl\u4e2d\u53ea\u6709\u8fd9\u4e00\u4e2a\u68c0\u7d22\u5b57\u7b26);
 */
function getPrintNumber(){
	var printNumber = window.location.search;	
	if(printNumber.indexOf("PrintNumber=") != -1){
		return parseInt(printNumber.split("=")[1]) - 1;
	}

	return 0;
}

var MILLSECOND_OF_DAY = 86400000; //24*60*60*100,\u4e00\u5929\u65f6\u95f4\u7684\u6beb\u79d2\u6570
var WEEK_DAY = ["\u661f\u671f\u65e5","\u661f\u671f\u4e00","\u661f\u671f\u4e8c","\u661f\u671f\u4e09","\u661f\u671f\u56db","\u661f\u671f\u4e94","\u661f\u671f\u516d"];

/*
 * \u521d\u59cb\u5316\u4e0a\u4e00\u671f,\u4e0b\u4e00\u671f\u7684\u54cd\u5e94\u64cd\u4f5c;\u671f\u6570
 */
function initHistoryNav(){	
	var currHistoryDay = calCurrDay();
	var sHistoryNav = currHistoryDay.getFullYear() + "\u5e74" + (currHistoryDay.getMonth()+1) + "\u6708" + currHistoryDay.getDate() + "\u65e5&nbsp;&nbsp;&nbsp;&nbsp;";
	sHistoryNav += WEEK_DAY[currHistoryDay.getDay()];
	sHistoryNav += "&nbsp;&nbsp;";
	sHistoryNav += makeEditionNumberDesc();
	sHistoryNav += "&nbsp;&nbsp;&nbsp;&nbsp;";
	sHistoryNav += "<a href='#' onclick='javascript:lastEdition();return false;'>\u4e0a\u4e00\u671f</a>&nbsp;&nbsp;&nbsp;&nbsp;";

	var now = new Date();
	var diff = (now.getTime() - currHistoryDay.getTime() - MILLSECOND_OF_DAY);
	if(diff <= 100){		
		if(diff >= 0){
			var latestUrl = window.location.href.replace("/history/"+makeDirFromDate(currHistoryDay),"");
			sHistoryNav += "<a href='" + latestUrl +"' target='_self'>\u4e0b\u4e00\u671f</a>";
		}
	}else{
		sHistoryNav += "<a href='#' onclick='javascript:nextEdition();return false;'>\u4e0b\u4e00\u671f</a>";
	}

	sHistoryNav += "&nbsp;&nbsp;&nbsp;&nbsp;";	
	$("#historynav_panel").html(sHistoryNav);
	
	var calendarImg = $("#img_calendar");
	calendarImg.click(function(){		
			showCalendar();
			return true;
		}
	);
	calendarImg.hover(function(){		
			this.style.background = "red";
		},
		function(){
			this.style.background = "";
		}
	);
	calendarImg = null;
}

/*!
 *  \u6570\u636e\u4e2d\u5e94\u8be5\u6709\u4e00\u4e2a\u5c5e\u6027\u6807\u8bc6\u672c\u671f\u62a5\u7eb8\u662f\u603b\u7b2c\u51e0\u671f,\u59821-32-12726-6
 *	\u8be5\u6570\u636e\u901a\u8fc7\u7f6e\u6807\u53d1\u5e03.(<span id="editionNumber"><TRS_XXX /></span>);
 *
 *	\u9700\u8981\u6839\u636e\u5b9e\u9645\u60c5\u51b5\u4fee\u6b63\u8be5\u65b9\u6cd5,\u5982\u65e0\u671f\u6570\u663e\u793a,\u53ef\u5ffd\u7565.
 *  
 */
function makeEditionNumberDesc(){	
	var editionNumber = $("#editionNumber").text().split("-")[2];		
	if(editionNumber && parseInt(editionNumber) > 0){
		return "\u7b2c<span style='color:#9f0000;font-weight:bold'>" + editionNumber + "</span>\u671f";
	}

	return "";	
}

/*
 * \u4e0a\u4e00\u671f
 */
function lastEdition(){	
	var lastDay = new Date();
	lastDay.setTime(calCurrDay().getTime() - MILLSECOND_OF_DAY);
	go2Edition(lastDay);	
}

/*
 * \u4e0b\u4e00\u671f
 */
function nextEdition(){
	var tomorrow = new Date();
	tomorrow.setTime(calCurrDay().getTime() + MILLSECOND_OF_DAY);
	go2Edition(tomorrow);
}

/*
 * \u6839\u636e\u6307\u5b9a\u7684\u65e5\u671f\u8df3\u8f6c
 */
function go2Edition(_date){
	var targetDir = makeDirFromDate(_date);
	var currUrl = window.location.href;	
	var currDir = makeDirFromDate(calCurrDay());
	if(targetDir == currDir){
		return false;
	}
	var targetUrl = "";
	if(currUrl.indexOf(currDir) != -1){
		if(targetDir == makeDirFromDate(new Date())){
			targetUrl = currUrl.replace("/history/"+currDir,"");
		}else{		
			targetUrl = currUrl.replace(currDir,targetDir);		
		}		
	}else{
		var pos = currUrl.lastIndexOf("/");
		targetUrl = currUrl.substring(0,pos) + "/history/" + targetDir + currUrl.substr(pos);
	}
	
	//test the target url,load the page if exists 
	$.ajax({
		type : 'get',
		url : targetUrl,
		async : false,
		success : function(){
			window.location.assign(targetUrl);
			return true;
		},
		error : function(){			
			var msg = ["\u6ca1\u6709"];
			msg.push(_date.getFullYear());
			msg.push("\u5e74");
			msg.push(_date.getMonth()+1);
			msg.push("\u6708");
			msg.push(_date.getDate());
			msg.push("\u65e5\u7684\u6570\u636e!");
			alert(msg.join(""));
			return false;
		}
	});

	return false;		
}

/*
 * \u6839\u636e\u6307\u5b9a\u7684\u65e5\u671f\u8ba1\u7b97\u76ee\u5f55\u540d\u79f0
 */
function makeDirFromDate(_date){	
	var dirResult = [];
	dirResult.push(_date.getFullYear());
	var temp = _date.getMonth() + 1;
	if(temp < 10){
		dirResult.push("0");
	}
	dirResult.push(temp);
	temp = _date.getDate();
	if(temp < 10){
		dirResult.push("0");
	}
	dirResult.push(temp);
	
	return dirResult.join("");
}

/*
 * \u83b7\u53d6\u5f53\u524d\u7684\u65e5\u671f(\u6839\u636eUrl\u4e2d\u7684\u76ee\u5f55\u5b57\u4e32)
 */
function calCurrDay(){
	var currDay = new Date();
	var pathname = window.location.pathname;
	var ix = pathname.indexOf("/history/");
	if(ix != -1){
		var historyDay = pathname.substr(ix+9,8);//9="/history/".length;8="20060301".length
		currDay.setYear(historyDay.substring(0,4));
		currDay.setMonth(historyDay.substring(4,6)-1);
		currDay.setDate(historyDay.substring(6,8));
	}

	return currDay;
}

function showCalendar(){
	var el = document.getElementById("historynav_panel");	
	if (window._dynarch_popupCalendar != null) {
		_dynarch_popupCalendar.hide();                 // so we hide it first.
	} else {
		// first-time call, create the calendar.
		var cal = new Calendar(0, null, 
			function(_cal,_date){			
				if(_cal.daySelected != true){
					return false;
				}

				_cal.hide()

				var t = _date.split("-");
				var d = new Date();
				d.setYear(t[0]);
				d.setMonth(t[1]-1);
				d.setDate(t[2]);				
				go2Edition(d);				
			}, 
			function(){
				cal.hide();
				_dynarch_popupCalendar=null;
			},
			false);

		// uncomment the following line to hide the week numbers
		cal.weekNumbers = false;				
		cal.showsOtherMonths = true;
		
		_dynarch_popupCalendar = cal;// remember it in the global var
		cal.setRange(2005, 2070);
		cal.create();
	}

	_dynarch_popupCalendar.setDateFormat("%Y-%m-%d");// set the specified date format
	
	_dynarch_popupCalendar.sel = el;
	_dynarch_popupCalendar.showAtElement(el, "Br"); // show the calendar

	return false;
}

function Event(){
}

Event.pointerX = function(event) {
    return event.pageX || (event.clientX +
      (document.documentElement.scrollLeft || document.body.scrollLeft));
}

Event.pointerY = function(event) {
    return event.pageY || (event.clientY +
      (document.documentElement.scrollTop || document.body.scrollTop));
}
