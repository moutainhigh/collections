/* 
 *  视频选件常用js函数
 */

/**
 * 在新窗口中打开页面.
 * @param mypage 页面或绝对URL
 * @param myname 窗口名称
 * @param w 宽度	
 * @param h 高度
 * @param scroll 当内容超过窗体时, 是否显示滚动条.
 */
 Ext.ns("Ajax.Request");
function newWindow(mypage, myname, w, h, scroll){
	var nLeftPos = (screen.width) ? (screen.width-w)/2 : 0;
	var nTopPos = (screen.height) ? (screen.height-h)/2 : 0;
	var sFeature = 'location=no,resizable=no,menubar=no,status=yes,titlebar=no,toolbar=no,border=0';
	sFeature += ',scrollbars=' + (scroll?'yes':'no') + ',height=' + h + ',width=' + w + ',top=' + nTopPos + ',left=' + nLeftPos;
	var win = window.open(mypage, myname, sFeature);
	try {
		win.focus();
	} catch (exception) {}
}

function testMAS(){
	new Ajax.Request("./testURL.jsp", {
		method: 'post',
		onSuccess: function(transport) { 
			var string = transport.responseText;
			//alert("11111"+string+"11111"+typeof(string)+string.length);
			if(string.indexOf("false")>=0){
				window.clearInterval(gVideoSessionId);
				if(gVideoSessionId){
					alert("TRS视频服务(MAS)没有启动或启动错误,请检查无误后重试！");
				}
				gVideoSessionId = null;
			}
	   },
	   onFailure: onError
	});
}

function onError(){
			alert("TRS视频服务没有启动或启动错误，或网络连接中断，请检查无误后重试");
}
var gVideoSessionId;
function test(){
	gVideoSessionId=window.setInterval(testMAS,10000); 

}
test();
