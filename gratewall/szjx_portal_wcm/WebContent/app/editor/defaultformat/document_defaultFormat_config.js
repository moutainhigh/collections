DefaultFormat_ControlPanel = {
	"d_kee":[
	/*保留所有*/		"d_keepall",
	/*保留列表*/		"d_keepul",
	/*保留表格*/		"d_keeptable",
	/*保留图片*/		"d_keepimg",
	/*保留对象*/		"d_keepobject",
	/*保留超链接*/		"d_keepa"
	],
	"d_del":[
	/*去除空行*/			"d_delline",
		/*去除所有属性*/		"d_delallattr",
	/*去除多余空格*/		"d_delspace",
		/*去除样式属性*/		"d_delstyle",
	/*去除隐藏域*/			"d_delhidden",
		/*去除事件属性*/		"d_delon"
	],
	"d_add":[
	/*首行缩进2字符*/		"d_addindent",
	/*两端对齐*/			"d_addjustify",
	/*<BR>转<P>*/			"d_addbr2p",
	/*单线表格*/			"d_addtablebc"
	],
	"d_inp":[
	/*段前*/				"d_inpmargintop",
	/*段后*/				"d_inpmarginbottom",
	/*行距*/				"d_inplineheight"
	],
	"d_sel":{
	/*字体*/				"d_selfontname":1,
	/*字号*/				"d_selfontsize":1
	}
};
DefaultFormat_Lang = {
	"d_keepall"		: "保留所有",
	"d_keepul"		: "保留列表",
	"d_keeptable"	: "保留表格",
	"d_keepimg"		: "保留图片",
	"d_keepobject"	: "保留对象",
	"d_keepa"		: "保留超链接",

	"d_delline"		: "去除空行",
	"d_delspace"	: "去除多余空格",
	"d_delhidden"	: "去除隐藏域",
	"d_delallattr"	: "去除所有属性",
	"d_delstyle"	: "去除样式属性",
	"d_delon"		: "去除事件属性",

	"d_addindent"	: "首行缩进2字符",
	"d_addjustify"	: "两端对齐",
	"d_addbr2p"		: "&lt;BR&gt;转&lt;P&gt;",
	"d_addtablebc"	: "单线表格",

	"d_inpmargintop"	: "段前",
	"d_inpmarginbottom"	: "段后",
	"d_inplineheight"	: "行距",

	"d_selfontname"		: "字体",
	"d_selfontsize"		: "字号"
}
var lang = {
	"FontNameItem"		: ["宋体", "黑体", "楷体", "仿宋", "隶书", "幼圆", "Arial", "Arial Black", "Arial Narrow", "Brush Script MT", "Century Gothic", "Comic Sans MS", "Courier", "Courier New", "MS Sans Serif", "Script", "System", "Times New Roman", "Verdana", "Wide Latin", "Wingdings"],
	"FontName"			: "--系统字体--",
	"FontSizeItem"		: [ ["42pt","初号"], ["36pt","小初"], ["26pt","一号"], ["24pt","小一"], ["22pt","二号"], ["18pt","小二"], ["16pt","三号"], ["15pt","小三"], ["14pt","四号"], ["12pt","小四"], ["10.5pt","五号"], ["9pt","小五"], ["7.5pt","六号"], ["6.5pt","小六"], ["5.5pt","七号"], ["5pt","八号"], ["5pt","5"], ["5.5pt","5.5"], ["6.5pt","6.5"], ["7.5pt","7.5"], ["8pt","8"], ["9pt","9"], ["10pt","10"], ["10.5pt","10.5"], ["11pt","11"], ["12pt","12"], ["14pt","14"], ["16pt","16"], ["18pt","18"], ["20pt","20"], ["22pt","22"], ["24pt","24"], ["26pt","26"], ["28pt","28"], ["36pt","36"], ["48pt","48"], ["72pt","72"] ],
	"FontSize"			: "--系统字号--"
}
function $(el) {
	if (typeof el == 'string')
		el = document.getElementById(el) || document.getElementsByName(el)[0];
	return el;
}
function getParameter(_sName, _sQuery){
	if(_sName ==null ||_sName=='undefined') 
		return '';
	var query = _sQuery || location.search;
	if(query == null || query.length==0) return '';
	var arr = query.substring(1).split('&');
	_sName = _sName.toUpperCase();
	for (var i=0,n=arr.length; i<n; i++){
		if(arr[i].toUpperCase().indexOf(_sName+'=')==0){
			return arr[i].substring(_sName.length + 1);
		}
	}
	return '';
}
