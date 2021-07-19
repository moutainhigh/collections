$(function() {
	var entityNo = getUrlParam("entityNo");
	var priPid = getUrlParam("priPid"); // 当前系统主键
	// 获取传递过来的参数，进行初始化请求
	if (entityNo != null) {
		$("#jbxxGrid").gridpanel("hideColumn", "modfydate");
		$("#jbxxGrid").gridpanel("showColumn", "approvedate");
		queryHistory(entityNo, priPid);
	}
});
function queryUrl() {
	$('#jbxxGrid').gridpanel('option', 'dataurl',
			rootPath + '/datacompre/queryZzjgDm.do');
	$('#jbxxGrid').gridpanel('query', [ 'formpanel' ]);
}

function queryHistory(entityNo, priPid) {
	$('#jbxxGrid').gridpanel(
			'option',
			'dataurl',
			rootPath + '/blackent/detail.do?flag=' + entityNo + '&priPid=' + priPid);
	$('#jbxxGrid').gridpanel('query', [ 'formpanel' ]);
}

function reset() {
	$("#jbxxGrid").formpanel('reset');
}

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}

// 数据渲染函数
function renderdata(data) {
	for (var i = 0; i < data.length; i++) {
		data[i]["@toolsopration"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="callcustom()">'
				+ data[i]["name"] + '</a></div>';
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="callcustom()">'
				+ data[i]["name"] + '</a></div>';
	}
	return data;
}

function closeshow() {
	$("#formpanel").hide();
}

/*
 * 
 * 添加事件处理程序
 * 
 * @param object object 要添加事件处理程序的元素
 * 
 * @param string type 事件名称，如click
 * 
 * @param function handler
 * 事件处理程序，可以直接以匿名函数的形式给定，或者给一个已经定义的函数名。匿名函数方式给定的事件处理程序在IE6 IE7
 * IE8中可以移除，在标准浏览器中无法移除。
 * 
 * @param boolean remove 是否是移除的事件，本参数是为简化下面的removeEvent函数而写的，对添加事件处理程序不起任何作用
 * 
 */

function addEvent(object, type, handler, remove) {
	if (typeof object != 'object' || typeof handler != 'function')
		return;
	try {
		object[remove ? 'removeEventListener' : 'addEventListener'](type,
				handler, false);
	} catch (e) {

		var xc = '_' + type;

		object[xc] = object[xc] || [];

		if (remove) {

			var l = object[xc].length;

			for (var i = 0; i < l; i++) {

				if (object[xc][i].toString() === handler.toString())
					object[xc].splice(i, 1);

			}

		} else {

			var l = object[xc].length;

			var exists = false;

			for (var i = 0; i < l; i++) {

				if (object[xc][i].toString() === handler.toString())
					exists = true;

			}

			if (!exists)
				object[xc].push(handler);

		}

		object['on' + type] = function() {

			var l = object[xc].length;

			for (var i = 0; i < l; i++) {

				object[xc][i].apply(object, arguments);

			}

		}

	}

}

/*
 * 
 * 移除事件处理程序
 * 
 */

function removeEvent(object, type, handler) {

	addEvent(object, type, handler, true);

}
var i = 1000;
// 延迟加载数据
function timedata() {
	setTimeout("handler()", i);
}

$(function() {
	addEvent(window, 'load', timedata);
})

$(document).ready(function() {

	// $("#formpanel").formpanel('option', 'readonly', true);

	// 行间间距缩小
	$('.jazz-textfield-comp').css("height", "10px");
	// 详细信息的字体大小和宽度
	$('.jazz-field-comp-label').css({
		width : "130px",
		"font-size" : "12px"
	});
	// parent.setWinHeight(1000);
});




