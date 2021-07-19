Ext.ns('wcm.MsgSound');
(function(){
	var m_MsgTemplate = [
		'<object width="1" height="1"',
			'codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab" ',
			'classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000">',
		'<param value="../app/js/resource/newmsg.swf" name="movie"/>',
		'<param value="true" name="play"/>',
		'<param value="true" name="loop"/>',
		'<param value="high" name="quality"/>',
		'<param value="true" name="autostart"/>',
		'<param value="showall" name="scale"/>',
		'<param value="transparent" name="wmode"/>',
		'<param value="allowScriptAccess" name="always"/>',
		'<embed width="1" height="1" pluginspage="http://www.macromedia.com/go/flashplayer/" ',
			'swliveconnect="true" wmode="transparent" scale="SHOWALL" quality="high" ',
			'loop="false" play="true" autostart="true" allowScriptAccess="always" src="../app/js/resource/newmsg.swf" name="msgcommand" id="msgcommand"/>',
		'</object>'
	].join('');
	Ext.apply(wcm.MsgSound, {
		movie : function(movieName){
			if(Ext.isIE)return window[movieName];
			return document[movieName];
		},
		play : function(){
			var movie = wcm.MsgSound.movie("msgcommand");
			if(!wcm.MsgSound.judgeLoaded(movie)){
				if(movie){
					setTimeout(wcm.MsgSound.play, 10);
				}
				return false;
			}
			$('msgcommand').Play();
		},
		judgeLoaded : function(movie){
			if(movie) return movie.PercentLoaded()==100;
			return false;
		}
	});
	Event.observe(window, 'load', function(){
		var msgDiv = document.createElement('DIV');
		msgDiv.id = 'msgsound';
		msgDiv.style.position = 'absolute';
		msgDiv.style.top = '-10000px';
		msgDiv.style.left = '-10000px';
//		msgDiv.style.display = 'none';
		document.body.appendChild(msgDiv);
		if(Ext.isIE){
			var so = new SWFObject("../app/js/resource/newmsg.swf", "msgcommand", "100px", "100px", "9.0.28", "#FF6600");
			so.addParam("quality", "high");
			so.addParam("play", "false");
			so.addParam("loop", "false");
			so.addParam("wmode", "transparent");
			so.addParam("scale", "exactfit");
			so.addParam("allowScriptAccess", "sameDomain");
			so.write("msgsound");
			return;
		}
		msgDiv.style.top = '-1px';
		msgDiv.style.left = 0;
		msgDiv.innerHTML = m_MsgTemplate;
	});
})();