Event.observe(window, 'load', function(){
	if($('processbar'))return;
	var div = document.createElement('DIV');
	document.body.appendChild(div);
	div.id = 'processbar';
	div.style.display = 'none';
	div.innerHTML = [
		'<div id=pb_cv class=pb_cv></div>',
		'<div class=pb_to>',
			'<table class=pb_tb border=1 bordercolor=#000 bordercolordark=#fff ',
			' cellspacing=0 cellpadding=0 style="font-size:12px">',
			'<tr><td height=22 align=center bgcolor="#BBBBBB" style="font-size:14px;">',
				wcm.LANG['WCM_WELCOME'] || '欢迎您使用TRS WCM系统','</td></tr>',
			'<tr><td align=center bgcolor=#DDDDDD>',
			String.format("正在{0},请耐心等候.", '<span id=pb_ev></span>'),
			'<br><br>','等待时间：','<span style="color:red"><span id=pb_wt>0</span>','秒','</span>',
			'<div style=\"padding:10px;\">如果系统长时间无法响应,请<a href=\"#\" id=\"pb_closer\" onclick=\"ProcessBar.close();return false;\">',
			'<span style=\"color:red;\">点击这里</span></a>返回主页面</div>',		
			'</td></tr></table>',
		'</div>',
		'</div>'
	].join('');
});
var ProcessBar = function(){
	var m_itv_pb;//定义私有 变量的一种方式，这里需要直接使用局部变量，避免m_itv_pb成为全局变量产生变量污染
	return {
		m_lStart : 0,
		start : function(title){
			Element.show('processbar');
			document.body.scrollTop = 0;
			$('pb_ev').innerHTML = title;
			var lStart = this.m_lStart || new Date().getTime();
			this.m_lStart = lStart;
			m_itv_pb = setInterval(function(){
				var lNow = new Date().getTime();
				var elisTime = parseInt((lNow - lStart)/ 1000, 10);
				$('pb_wt').innerHTML = elisTime;
				if(elisTime>300){
					alert(wcm.LANG['EASY_SERVER_OVER'] || "执行时间超出常规时间，"
						+ wcm.LANG['EASY_SERVER_ERROR'] || "可能出现网络故障，请刷新后重新处理。");
					ProcessBar.close();
				}
			}, 100);
			$('pb_cv').style.height = document.body.scrollHeight;
		},
		exit :function(){
			Element.hide('processbar');
			clearInterval(m_itv_pb);
			m_itv_pb = null;
		},
		close :function(){
			if(!m_itv_pb) return;//尚未调用过start方法进行初始化
			this.m_lStart = null;
			Element.hide('processbar');
			clearInterval(m_itv_pb);
			m_itv_pb = null;
		}
	}
}();