$(function(){
	createDate();
});

function createDate(){
	$("<div id='adddate'></div>").appendTo("body");
	var win = $("#adddate");
	win.window({
		name : "adddate",
		title : "新增日程",
		titleicon : "../../static/images/other/notepad-.png",
		minimizable : true, //是否显示最小化按钮
		showborder : true, //true显示窗体边框    false不显示窗体边
        width: 450, 
        height: 250, 
        modal:true,
		frameurl : "",
		framename : "newdate"
	});
}

function addDate(){
	var adddatewindow = $("#adddate").data("window");
	if(!adddatewindow) {
		createDate();
		adddatewindow = $("#adddate").data("window");
	}
	adddatewindow.open();
	$("#adddate").window('option','frameurl',rootPath+"/page/home/date_add.jsp");
}

function leave(){
	$("#adddate").data("window").close();
}

function reload(){
	$("#gridpanel").gridpanel('reload');
}

