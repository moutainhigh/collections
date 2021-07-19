(function(){
	var Ext = {version: '2.2'};
	var ua = navigator.userAgent.toLowerCase();
	var isStrict = document.compatMode == "CSS1Compat",
		isOpera = ua.indexOf("opera") > -1,
		isChrome = ua.indexOf("chrome") > -1,
		isSafari = (/webkit|khtml/).test(ua),
		isSafari3 = isSafari && ua.indexOf('webkit/5') != -1,
		isIE = !isOpera && ua.indexOf("msie") > -1,
		isIE7 = !isOpera && ua.indexOf("msie 7") > -1,
		isIE8 = !isOpera && (ua.indexOf("msie 8") > -1 || ua.indexOf("msie 9") > -1),
		isIE6 = !isOpera && !isIE7 && ua.indexOf("msie 6") > -1,
		isGecko = !isSafari && ua.indexOf("gecko") > -1,
		isGecko2 = isGecko && ua.indexOf("firefox/2") > -1,	
		isGecko3 = !isSafari && ua.indexOf("rv:1.9") > -1,
		isSecure = window.location.href.toLowerCase().indexOf("https") === 0;
	    // remove css image flicker
		if(isIE && !isIE7){
	        try{
	            document.execCommand("BackgroundImageCache", false, true);
	        }catch(e){}
	    }
	var vars = ['isStrict', 'isOpera', 'isChrome', 'isSafari', 'isSafari3', 'isIE', 'isIE7', 'isIE8', 'isIE6', 'isGecko', 'isGecko2', 'isGecko3', 'isSecure'];
	for(var i=0;i<vars.length;i++)
		Ext[vars[i]] = eval([vars[i]]);
	function defExtCss(){
		var inited, initExtCssThreadId = -1;
	    window.initExtCss = function(){
	        var bd = document.body;
	        if(!bd){ return false; }
			clearInterval(initExtCssThreadId);
			if(bd.getAttribute('_cssrender') || inited) return true;
			inited = true;
			var cls = [];
			if(Ext.isIE)cls.push("ext-ie");
			if(Ext.isIE6)cls.push("ext-ie6");
			if(Ext.isIE7)cls.push("ext-ie7");
			if(Ext.isIE8)cls.push("ext-ie8");
			if(Ext.isIE9)cls.push("ext-ie9");

			if(Ext.isGecko)cls.push("ext-gecko");
			if(Ext.isGecko2)cls.push("ext-gecko2");
			if(Ext.isGecko3)cls.push("ext-gecko3");

			if(Ext.isOpera)cls.push("ext-opera");
			if(Ext.isSafari)cls.push("ext-safari");
	        if(Ext.isStrict){
	            var p = bd.parentNode;
	            if(p){
	                p.className += ' ext-strict';
	            }
	        }
			cls.push('res-'+screen.width+"x"+screen.height);
			bd.className += " " + cls.join(' ');
			bd.setAttribute('_cssrender', 1);
	        return true;
	    }
		if(!window.initExtCss()){
			initExtCssThreadId = setInterval(initExtCss, 50);	
		}
	};
	defExtCss();
})();