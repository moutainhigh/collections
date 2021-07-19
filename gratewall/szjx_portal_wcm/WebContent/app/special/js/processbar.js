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
			'<tr><td height=22 align=center bgcolor="#AAD6FE" style="font-size:14px;">',
				'欢迎您使用TRS 专题选件','</td></tr>',
			'<tr><td align=center bgcolor=#D4ECFE>',
			String.format('正在{0}请耐心等候……', '<span id=pb_ev></span>'),
			'<br><br>','等待时间：','<span style="color:red"><span id=pb_wt>0</span>','秒','</span></td></tr></table>',
		'</div>'
	].join('');
});
var ProcessBar = {
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
			//由于风格导入时间过长，测试时使用了2000多秒，故注释
			/*if(elisTime>300){
				alert("执行时间超出常规时间，" 
					+ "可能出现网络故障，请刷新后重新处理。");
				ProcessBar.close();
			}*/
		}, 100);
		$('pb_cv').style.height = document.body.scrollHeight;
	},
	exit :function(){
		Element.hide('processbar');
		clearInterval(m_itv_pb);
	},
	setCurrInfo :function(arg){
		$('pb_ev').innerHTML = arg;
	},
	close :function(){
		this.m_lStart = null;
		Element.hide('processbar');
		clearInterval(m_itv_pb);
	}
};