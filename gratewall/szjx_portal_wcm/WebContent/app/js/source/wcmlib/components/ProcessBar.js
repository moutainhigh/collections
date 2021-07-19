/*wcm.ProcessBar*/
Ext.ns('wcm.ProcessBar');
Ext.ns("wcm.LANG");
(function(){
	var actualTop = window.$MsgCenter?$MsgCenter.getActualTop():window;
	if(actualTop && actualTop.ProcessBar) {
		if(actualTop.ProcessBar.isExtend){
			window.ProcessBar = wcm.ProcessBar = actualTop.ProcessBar;
			return;
		}
	}
	var m_sTemplate = [
		'<div class="processbar_coverall"></div>',
		'<div class="processbar_table_outer">',
			'<table class="processbar_table" border=1 bordercolor=#000000 ',
				'bordercolordark=#ffffff cellspacing=0 cellpadding=0 style="font-size:12px">',
				'<tr><td height=22 align=center bgcolor="#BBBBBB" style="font-size:14px;">',
					 wcm.LANG['WCM_WELCOME'] || '欢迎您使用TRS WCM系统' ,'</td></tr>',
				'<tr>',
					'<td align=center bgcolor=#DDDDDD>',
						String.format(wcm.LANG['NOW_DOING_WAIT'] || '正在{0},请耐心等候.', '<span id=processbar_thing>{0}</span>'),
						'<br><br>', wcm.LANG['TIME_WAIT_DESC'] || '等待时间：', 
						'<span style="color:red;"><span id=processbar_waitingTime>0</span>',
						wcm.LANG['TIME_UNIT_SECOND'] || '秒',
						'</span>',
						'<div style="padding:10px;">如果系统长时间无法响应,请<a href="#" id="pb_closer" onclick="ProcessBar.close();return false;">',
						'<span style=\"color:red;\">点击这里</span></a>返回主页面</div>',
					'</td>',
				'</tr>',
			'</table>',
		'</div>'
	].join('');
	var m_intervalProcessbar = 0;
	var m_sTitle = '';
	ProcessBar = wcm.ProcessBar = {
		isExtend : true,
		init : function(title){
			m_sTitle = title;
		},
		addState : function(){
		},
		next : function(title){
			Element.update('processbar_thing', title);
		},
		start : function(title){
			var processbar = $('processbar');
			if(!processbar){
				processbar = document.createElement('DIV');
				processbar.id = 'processbar';
				document.body.appendChild(processbar);
			}
			processbar.innerHTML = String.format(m_sTemplate, title || m_sTitle || '');
			var lStart = new Date().getTime();
			if(m_intervalProcessbar){
				clearInterval(m_intervalProcessbar);
			}
			m_intervalProcessbar = setInterval(function(){
				var lNow = new Date().getTime();
				var elisTime = ((lNow - lStart) * 1.00 / 1000 + '');
				var arr = elisTime.split('');
				arr[0] = arr[0] || '0';
				arr[1] = arr[1] || '.';
				arr[2] = arr[2] || '0';
				arr[3] = arr[3] || '0';
				arr[4] = arr[4] || '0';
				document.getElementById('processbar_waitingTime').innerHTML = arr.join('');
			}, 100);
		},
		exit : function(){
			var processbar = $('processbar');
			if(!processbar)return;
			processbar.innerHTML = '';
			document.body.removeChild(processbar);
			clearInterval(m_intervalProcessbar);
		},
		close :function(){
			ProcessBar.exit();
		}
	};
	if(actualTop == window)return;
	for(var name in ProcessBar){
		ProcessBar[name] = wcm.ProcessBar[name] = function(){
			var actualTop = $MsgCenter.getActualTop();
			var ProcessBar0 = actualTop.ProcessBar || ProcessBar;
			return ProcessBar0[this].apply(ProcessBar0, arguments);
		}.bind(name);
	}
})();