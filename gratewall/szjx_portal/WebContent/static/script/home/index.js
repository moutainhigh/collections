$(function() {
	var  tipFunction;
	
	var height = jazz.util.windowHeight();
	
	if(height > 768){
		height = 768;
	}
	
	$("#allcontent").height(height);
	$("#contentpanel").height(height);
	$("#contentpanel > .jazz-panel-content").height(height);
	$($("#contentpanel > .jazz-panel-content").children()[1]).layout({
		layout:"fit"
	});
	
	$('#gnlb').layout({
		layout:"fit"
	});
	
	$('.datecontent').layout({
		layout:"fit"
	});
	
	$('#messagecontent').layout({
		layout:"fit"
	});
	
	var mainparams = {
		url:rootPath+"/home/loadAppForIndex.do?random="+Math.random(),
		callback : function(data,obj,res) {
			if(!!data.data){
				if(data.data.length > 0){
					initMain(data);
					
					if(data.data.length <= 8){
						$(".right-footer").hide();
					}
					
					createMessage();
					
					createDateList()
					
					initDate();
					
					initIconButton();
					
					tipTitle();
				}else{
					window.location.href=rootPath+"/page/home/permission.jsp";
				}
			}
		}
	};
	$.DataAdapter.submit(mainparams);
	
});

function initMain(data){
	$('#gnlb').icon({
		name : "applist",
		title : '主要业务',
		titledisplay : false,
		width: 700,               //icon组件高
		height: 350,             //icon组件宽度
		isshowpaginator: false,          //是否显示分页条
		showborder: false, 
		iscontroller: false,
		cols: 4,                        //显示列数
		data:data, 
		iconrender: function(container, data){ 
			var url = rootPath+data.systemImgUrl;
			if(!!url && url.indexOf(":") > 0){
					url = rootPath+"/page/common/file_read.jsp?fileName="+data.systemImgUrl;
			}
			var str = '<div style="height:163px;width:163px;position:absolute;left:6px;top:0;"><img width="163px" height="163px" border="0px" src="'+url+'"></div>';
			return str;
		},
		click : function(e, ui) {
			/*if(ui.systemCode == "SM"){
				window.open(rootPath+ui.integratedUrl+"?appID="+ui.pkSysIntegration+"&appCode="+ui.systemCode);
			}else{
				window.open(ui.integratedUrl+"?appID="+ui.pkSysIntegration+"&appCode="+ui.systemCode);
			}*/
			window.open(ui.integratedUrl+"?appID="+ui.pkSysIntegration+"&appCode="+ui.systemCode);
		}			
	});
	
	updateMain();
}

function updateMain(){
	$('#gnlb').icon("updateCount",rootPath+"/home/updateAppForIndex.do?random="+Math.random(),null);
}

function initMessage(){
	var params = {
		url:rootPath+"/home/getMessage.do?random="+Math.random(), 
		callback : function(data,obj,res) {
			if(!!data.data){
				if(data.data.length > 0){
					if(tipFunction.messageCount != data.data.length){
						if(tipFunction.messageCount != 0){
							tipFunction.show();
						}
						tipFunction.messageCount = data.data.length;
						var htmlcontent = "";
						$.each(data.data,function(i,content){
							htmlcontent += "<li title='"+content.title+"'>"+content.title+"</li>";
						});
						$(".messagelist").html(htmlcontent);
						$(".messagelist").myScroll();
					}
				}
			}
		}
	};
	$.DataAdapter.submit(params);
}



function createMessage(){
	$("<div id='messagewindow'></div>").appendTo("body");
	var win = $("#messagewindow");
	win.window({
		name : "messagewindow",
		title : "消息列表",
		titleicon : "../../static/images/other/notepad-.png",
		minimizable : true, //是否显示最小化按钮
		showborder : true, //true显示窗体边框    false不显示窗体边
        width: 700, 
        height: 450, 
        modal:true,
		frameurl : "",
		framename : "messagelist"
	});
}

function messageMore(){
	var messagewindow = $("#messagewindow").data("window");
	if(!messagewindow) {
		createMessage();
		messagewindow = $("#messagewindow").data("window");
	}
	messagewindow.open();
	$("#messagewindow").window('option','frameurl',rootPath+"/page/home/message_list.jsp");
}

function createDateList(){
	$("<div id='datewindow'></div>").appendTo("body");
	var win = $("#datewindow");
	win.window({
		name : "datewindow",
		title : "新增日程",
		titleicon : "../../static/images/other/notepad-.png",
		minimizable : true, //是否显示最小化按钮
		showborder : true, //true显示窗体边框    false不显示窗体边
        width: 600, 
        height: 300, 
        modal:true,
		frameurl : "",
		framename : "datelist"
	});
}

function addDate(){
	var datewindow = $("#datewindow").data("window");
	if(!datewindow) {
		createDateList();
		datewindow = $("#datewindow").data("window");
	}
	datewindow.open();
	$("#datewindow").window('option','frameurl',rootPath+"/page/home/date_add.jsp");
}

function leave(){
	$("#datewindow").data("window").close();
}

function onselect(e, date){
	initDateContent(date['date'].toString());
	var newdate = date['date'].toString().split("-");
	$("#txtyear").text(newdate[0]+"年");
	$("#txtmonth").text(parseInt(newdate[1])+"月");
	$("#txtdate").text(parseInt(newdate[2]));
}

function initDate(){
	var date = new Date();
	$("#txtyear").text(date.getFullYear() + "年");
	var month = date.getMonth() + 1;
	$("#txtmonth").text( month + "月"); 
	$("#txtdate").text(date.getDate());
	var time = date.getFullYear()+"-"+month+"-"+date.getDate();
	initDateContent(time);
	
	var params = {
		url:rootPath+"/home/getScheduleCount.do?random="+Math.random(), 
		callback : function(data,obj,res) {
			if(!!data.data){
				if(data.data.length > 0){
					$.each(data.data,function(i,date){
						var time = date.creatertime.split(" ");
						var datetime = $("#date-"+time[0]).text();
						$("#date-"+time[0]).css("z-index","200");
						$("#date-"+time[0]).append("<div class='jazz-date-num' >"+date["num"]+"</div>");
					});
				}
			}
		}
	};
	$.DataAdapter.submit(params);
}

function initDateContent(date){
	var params = {
		url:rootPath+"/home/getScheduleContent.do?random="+Math.random(), 
		params: {"date":date},
		callback : function(data,obj,res) {
			if(!!data.data){
				if(data.data.length > 0){
					var infocontent = "";
					$.each(data.data,function(i,newdate){
						var time = newdate.createrTime.split(" ")[1].substring(0,5);
						infocontent += "<li><div class='inline_block datetime'>"+time+"</div><div class='inline_block datetitle' title='"+newdate["title"]+"'>"+newdate["title"]+"</div><div class='inline_block datedelete' onClick='deleteInfo(\""+newdate["pkSchedule"]+"\",this)' title='删除'>X</div></li>";
					});
				}else{
					infocontent = "<li>暂无行程安排</li>";
				}
				$("#dateinfo").html(infocontent);
				$("#dateinfo").myScroll({speed:80,rowHeight:16});
			}
		}
	};
	$.DataAdapter.submit(params);
}

function tipSchedule(){
	var params = {
		url:rootPath+"/home/getTipSchedule.do?random="+Math.random(),
		callback : function(data,obj,res) {
			if(!!data.data){
				if(data.data.length > 0){
					if(tipFunction.scheduleCount != data.data.length){
						tipFunction.show();
						//alert("日程重要提醒");
						tipFunction.scheduleCount = data.data.length;
					}
				}
			}
		}
	};
	$.DataAdapter.submit(params);
}

function deleteInfo(pkid,obj){
	$(obj).parent().remove();
	var params = {
		url:rootPath+"/home/deleteSchedule.do?random="+Math.random(), 
		params: {"pkId":pkid},
		callback : function(data,obj,res) {
		}
	};
	$.DataAdapter.submit(params);
}

function initIconButton(){
	$(".right-footer-left").click(function(){
		var flag = $("#gnlb").icon("leftside");
		if(flag){
			$(this).css("background","url(../../static/images/other/icon_left2.png) no-repeat");
		}else{
			$(this).css("background","url(../../static/images/other/icon_left.png) no-repeat");
		}
	});
	
	$(".right-footer-right").click(function(){
		var flag = $("#gnlb").icon("rightside");
		if(flag){
			$(this).css("background","url(../../static/images/other/icon_right2.png) no-repeat");
		}else{
			$(this).css("background","url(../../static/images/other/icon_right.png) no-repeat");
		}
	});
}

$.fn.myScroll = function(options){
	//默认配置
	var defaults = {
		speed:40,  //滚动速度,值越大速度越慢
		rowHeight:25 //每行的高度
	};
	var opts = $.extend({}, defaults, options);
	function marquee(obj, step){
		obj.animate({
			marginTop: '-=1'
		},0,function(){
			var s = Math.abs(parseInt($(this).css("margin-top")));
			if(s >= step){
				$(this).find("li").slice(0, 1).appendTo($(this));
				$(this).css("margin-top", 0);
			}
		});
	}
		
	var sh = opts["rowHeight"],speed = opts["speed"],_this = $(this);
	var message = setInterval(function(){
		if(_this.height()<=_this.parent().height()){
			clearInterval(message);
		}else{
			marquee(_this, sh);
		}
	}, speed);

	_this.hover(function(){
		clearInterval(message);
	},function(){
		message = setInterval(function(){
			if(_this.height()<=_this.parent().height()){
				clearInterval(message);
			}else{
				marquee(_this, sh);
			}
		}, speed);
	});
}

function tipTitle(){
	tipFunction = {
		messageCount : 0,
		scheduleCount : 0,
		time : 0,
		title : document.title,
		timer : null,
		show : function() {
			var title = tipFunction.title.replace("【　　　】", "").replace("【新消息】", "");
			tipFunction.timer = setInterval(function() {
				tipFunction.time++;
				if (tipFunction.time % 2 == 0) {
					document.title = "【新消息】" + title
				} else {
					document.title = "【　　　】" + title
				}
			}, 200);
		},
		clear : function() {
			clearInterval(tipFunction.timer);
			document.title = tipFunction.title;
		}
	};
	
	document.onclick = function() {
		tipFunction.clear();
	};
	
	initMessage();
	tipSchedule();
	
	setInterval(function(){
		initMessage();
		tipSchedule();
	},1000*60*15);
}

function compareDate(beginTime,endTime){
    var beginTimes = beginTime.substring(0, 10).split('-');
    var endTimes = endTime.substring(0, 10).split('-');
    beginTime = beginTimes[1] + '-' + beginTimes[2] + '-' + beginTimes[0] + ' ' + beginTime.substring(10, 19);
    endTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0] + ' ' + endTime.substring(10, 19);
    var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
    if (a < 0) {
        alert("endTime小!");
    } else if (a > 0) {
        alert("endTime大!");
    } else if (a == 0) {
        alert("时间相等!");
    } else {
        return 'exception'
    }
}
