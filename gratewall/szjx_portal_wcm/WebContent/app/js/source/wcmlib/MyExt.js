var MyExt = Ext = (function(){
    var ua = navigator.userAgent.toLowerCase();
	var isStrict = document.compatMode == "CSS1Compat",
	isOpera = ua.indexOf("opera") > -1,
	isSafari = (/webkit|khtml/).test(ua),
	isSafari3 = isSafari && ua.indexOf('webkit/5') != -1,
	isIE = !isOpera && ua.indexOf("msie") > -1,
	isIE7 = !isOpera && ua.indexOf("msie 7") > -1,
	isGecko = !isSafari && ua.indexOf("gecko") > -1,
	isGecko3 = !isSafari && ua.indexOf("rv:1.9") > -1,
	isBorderBox = isIE && !isStrict,
	isWindows = (ua.indexOf("windows") != -1 || ua.indexOf("win32") != -1),
	isMac = (ua.indexOf("macintosh") != -1 || ua.indexOf("mac os x") != -1),
	isAir = (ua.indexOf("adobeair") != -1),
	isLinux = (ua.indexOf("linux") != -1),
	isSecure = window.location.href.toLowerCase().indexOf("https") === 0;

	// remove css image flicker
	if(isIE && !isIE7){
		try{
			document.execCommand("BackgroundImageCache", false, true);
		}catch(e){}
	}
	return {
        isOpera : isOpera,
        isSafari : isSafari,
        isSafari3 : isSafari3,
        isSafari2 : isSafari && !isSafari3,
        isIE : isIE,
        isIE6 : isIE && !isIE7,
        isIE7 : isIE7,
        isGecko : isGecko,
        isGecko2 : isGecko && !isGecko3,
        isGecko3 : isGecko3,
        isBorderBox : isBorderBox,
        isLinux : isLinux,
        isWindows : isWindows,
        isMac : isMac,
        isAir : isAir,
        useShims : ((isIE && !isIE7) || (isMac && isGecko && !isGecko3)),
		isStrict : isStrict,
        isSecure : isSecure,
        emptyFn : function(){},
		ns : function(){
            var a=arguments, o=null, i, j, d, rt;
            for (i=0; i<a.length; ++i) {
                d=a[i].split(".");
                rt = d[0];
                eval('if (typeof ' + rt + ' == "undefined"){' + rt + ' = {};} o = ' + rt + ';');
                for (j=1; j<d.length; ++j) {
                    o[d[j]]=o[d[j]] || {};
                    o=o[d[j]];
                }
            }
        },
		applyIf : function(o, c){
			if(o && c){
				for(var p in c){
					if(typeof o[p] == "undefined"){ o[p] = c[p]; }
				}
			}
			return o;
		},
		apply : function(o, c){
			if(o && c){
				for(var p in c){
					o[p] = c[p];
				}
			}
			return o;
		},
		$break : {},
		isDebug : function(){
			return (window.WCMConstants && WCMConstants.DEBUG) 
						|| !!getParameter('isdebug', top.location.search);
		},
		errorMsg : function(_Error){
			if(Ext.isIE){
				var oCaller = arguments.caller;			
				var tmpStack = [];
				while(oCaller&&oCaller.callee){
					tmpStack.push(oCaller.callee);
					oCaller = oCaller.caller;
					if (!oCaller||tmpStack.include(oCaller.callee)) {
						break;
					}
				}
				return _Error.message + '\n' + tmpStack.join('\n------------------------\n');
			}
			else{
				return _Error.message + '\n' + _Error.stack;
			}
		},
		isArray : function(v){
			return v && typeof v.length == 'number' && typeof v.splice == 'function';
		},
		isFunction : function(_object){
			return typeof _object=="function";
		},
		isString : function(_object){
			return (typeof _object=="string")||
				((_object!=null)&&(_object.constructor!=null)&&(_object.constructor==String||_object.constructor.toString().trim().indexOf('function String()')==0));
		},
		isNumber : function(_object){
			return (typeof _object=="number")||
				((_object!=null)&&(_object.constructor!=null)&&(_object.constructor==Number||_object.constructor.toString().trim().indexOf('function Number()')==0));
		},
		isBoolean : function(_object){
			return (typeof _object=="boolean")||
				((_object!=null)&&(_object.constructor!=null)&&(_object.constructor==Boolean||_object.constructor.toString().trim().indexOf('function Boolean()')==0));
		},
		isSimpType : function(_object){
			return this.isString(_object) || this.isNumber(_object) || this.isBoolean(_object);
		}
	};
})();