var lang = {
"FontNameItem"			: ["宋体", "黑体", "楷体", "仿宋", "隶书", "幼圆", "Arial", "Arial Black", "Arial Narrow", "Brush Script MT", "Century Gothic", "Comic Sans MS", "Courier", "Courier New", "MS Sans Serif", "Script", "System", "Times New Roman", "Verdana", "Wide Latin", "Wingdings"],
"FontName"				: "--系统字体--",
"FontSizeItem"			: [ ["42pt","初号"], ["36pt","小初"], ["26pt","一号"], ["24pt","小一"], ["22pt","二号"], ["18pt","小二"], ["16pt","三号"], ["15pt","小三"], ["14pt","四号"], ["12pt","小四"], ["10.5pt","五号"], ["9pt","小五"], ["7.5pt","六号"], ["6.5pt","小六"], ["5.5pt","七号"], ["5pt","八号"], ["5pt","5"], ["5.5pt","5.5"], ["6.5pt","6.5"], ["7.5pt","7.5"], ["8pt","8"], ["9pt","9"], ["10pt","10"], ["10.5pt","10.5"], ["11pt","11"], ["12pt","12"], ["14pt","14"], ["16pt","16"], ["18pt","18"], ["20pt","20"], ["22pt","22"], ["24pt","24"], ["26pt","26"], ["28pt","28"], ["36pt","36"], ["48pt","48"], ["72pt","72"] ],
"FontSize"				: "--系统字号--"
}
function $(id){
	if(typeof id != 'string')return id;
	return document.getElementById(id);
}
var CookieUtil = {
	get : function(name){
		var cookieName = encodeURIComponent(name) + "=",
			cookieStart = document.cookie.indexOf(cookieName),
			cookieValue = null;
		if(cookieStart > -1) {
			var cookieEnd = document.cookie.indexOf(";",cookieStart);
			if(cookieEnd == -1) {
				cookieEnd = document.cookie.length;
			}
			//cookieValue = encodeURIComponent(document.cookie.substring(cookieStart + cookieName.length,cookieEnd));
			//cookieValue = decodeURI(cookieValue);
			cookieValue = decodeURI(document.cookie.substring(cookieStart + cookieName.length,cookieEnd));
		}
		return cookieValue;
	},
	set : function(name,value,/*expires,path,*/domain,secure){
		var cookieText = encodeURIComponent(name) + "=" + encodeURIComponent(value);
		//设置cookie过期时间为一个月
		var expires = new Date();
		expires.setTime(expires.getTime() + (24 * 60 * 60 * 1000 * 30));
		cookieText += "; expires=" + expires.toUTCString();
		var path = "/wcm/app/";
		cookieText += "; path=" + path;
		if(domain) {
			cookieText += "; domain=" + domain;
		}
		if(secure) {
			cookieText += "; secure";
		}
		document.cookie = cookieText;
	},
	unset : function(name,path,domain,secure){
		this.set(name,"",new Date(0),path,domain,secure);
	}
};