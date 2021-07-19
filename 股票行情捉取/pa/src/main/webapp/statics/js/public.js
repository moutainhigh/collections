function getRootPath_web() {
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	//获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}

var path = getRootPath_web();
document.write('<link rel=\"stylesheet\" type=\"text/css\" href=\"'+path+'/statics/css/global.css\">');
document.write('<link rel="stylesheet" type="text/css" href='+path+'/statics/bt/css/bootstrap.min.css>');
document.write('<script type="text/javascript" src='+path+'/statics/js/jquery.js></script>');
document.write('<script type="text/javascript" src='+path+'/statics/bt/js/bootstrap.min.js></script>');
document.write('<script type="text/javascript" src='+path+'/statics/js/lyUtils.js></script>');




function getPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	return result;
}

var path = getPath();




function GetUrlRelativePath(){
　　var url = document.location.toString();
　　var arrUrl = url.split("//");

　　var start = arrUrl[1].indexOf("/");
　　var relUrl = arrUrl[1].substring(start);//stop省略，截取从start开始到结尾的所有字符

　　if(relUrl.indexOf("?") != -1){
　　　　relUrl = relUrl.split("?")[0];
　　}
　　return relUrl;
}



function showPopover(target, msg) {
	target.attr("data-original-title", msg);
	$('[data-toggle="tooltip"]').tooltip();
	target.tooltip('show');
	target.focus();
	//2秒后消失提示框
	var id = setTimeout(
		function() {
			target.attr("data-original-title", "");
			target.tooltip('hide');
		}, 2000
	);
}




var storage=window.localStorage;

var nowurl = document.URL;
var fromurl = document.referrer;

function setURL(url){
	storage.setItem("visitURL", url);
}
function getURL(){
	var url = storage.getItem("visitURL");
	return url;
}




//https://www.cnblogs.com/zhanghongjie/p/8548371.html
/*var timeid = window.setInterval("checkSession()", 30000);
checkSession = function() {
	var url = GetUrlRelativePath();
	var logURl  = path+ "/pages/login.html";
	if(url==logURl){
		return;
	}
	$.ajax({
		url :path+ "/loginCheck.do?rand="+Math.random(),
		type : "POST",
		dataType : "json",
		success : function(result) {
			if (result != true) {
				window.clearInterval(timeid);
					alert("由于您长时间没有操作, session已过期, 请重新登录");
					window.location.href = logURl;
			}
		}
	});

};*/