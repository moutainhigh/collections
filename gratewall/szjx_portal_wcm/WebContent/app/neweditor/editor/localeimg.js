window.isIE = (navigator.appName == "Microsoft Internet Explorer");
if(window.isIE) {
	if(navigator.userAgent.indexOf("Opera")>-1) window.isIE = null;
}
else {
	if(navigator.userAgent.indexOf("Gecko")==-1) window.isIE = null;
}
function $(id){
	if(typeof id != 'string')return id;
	return document.getElementById(id);
}
var idSeed = 0;
Ext = {version: '2.2'};
if(!window.Event){
	var Event = {};
}
Ext.apply = function(o, c, defaults){
    if(defaults){
        Ext.apply(o, defaults);
    }
    if(o && c && typeof c == 'object'){
        for(var p in c){
            o[p] = c[p];
        }
    }
    return o;
};
Ext.apply(Ext, {
	applyIf : function(o, c){
		if(o && c){
			for(var p in c){
				if(typeof o[p] == "undefined"){ o[p] = c[p]; }
			}
		}
		return o;
    },
	namespace : function(){
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
	id : function(el, prefix){
            prefix = prefix || "ext-gen";
            el = Ext.getDom(el);
            var id = prefix + (++idSeed);
            return el ? (el.id ? el.id : (el.id = id)) : id;
    },
	type : function(o){
		if(o === undefined || o === null){
			return false;
		}
		if(o.htmlElement){
			return 'element';
		}
		var t = typeof o;
		if(t == 'object' && o.nodeName) {
			switch(o.nodeType) {
				case 1: return 'element';
				case 3: return (/\S/).test(o.nodeValue) ? 'textnode' : 'whitespace';
			}
		}
		if(t == 'object' || t == 'function') {
			switch(o.constructor) {
				case Array: return 'array';
				case RegExp: return 'regexp';
			}
			if(typeof o.length == 'number' && typeof o.item == 'function') {
				return 'nodelist';
			}
		}
		return t;
    }
});
Ext.applyIf(Event, {
	observe : function(element, name, fn, b){
		fn.newFn = function(event){
			fn(event.browserEvent, event);
		};
		Ext.EventManager.on(element, name, fn.newFn);
	},
	stopObserving : function(element, name, fn){
		Ext.EventManager.un(element, name, fn.newFn || fn);
	}
});
Ext.ns = Ext.namespace;
Ext.ns("Ext", "Ext.util", "Ext.lib");
Ext.util.Event = function(obj, name){
        this.name = name;
        this.obj = obj;
        this.listeners = [];
    };
Ext.lib.Event = function() {
        var loadComplete = false;
        var listeners = [];
        var unloadListeners = [];
        var retryCount = 0;
        var onAvailStack = [];
        return {
            POLL_INTERVAL: 20,
            EL: 0,
            TYPE: 1,
            FN: 2,
            WFN: 3,
            OBJ: 3,
            ADJ_SCOPE: 4,
            _interval: null,
            startInterval: function() {
                if (!this._interval) {
                    var self = this;
                    var callback = function() {
                        self._tryPreloadAttach();
                    };
                    this._interval = setInterval(callback, this.POLL_INTERVAL);
                }
            },
            addListener: function(el, eventName, fn) {
                el = Ext.getDom(el);
                if (!el || !fn) {
                    return false;
                }
                if ("unload" == eventName) {
                    unloadListeners[unloadListeners.length] =
                    [el, eventName, fn];
                    return true;
                }
                // prevent unload errors with simple check
                var wrappedFn = function(e) {
                    return typeof Ext != 'undefined' ? fn(Ext.lib.Event.getEvent(e)) : false;
                };
                var li = [el, eventName, fn, wrappedFn];
                var index = listeners.length;
                listeners[index] = li;
                this.doAdd(el, eventName, wrappedFn, false);
                return true;
            },
            removeListener: function(el, eventName, fn) {
                var i, len;
                el = Ext.getDom(el);
                if(!fn) {
                    return this.purgeElement(el, false, eventName);
                }
                if ("unload" == eventName) {
                    for (i = 0,len = unloadListeners.length; i < len; i++) {
                        var li = unloadListeners[i];
                        if (li &&
                            li[0] == el &&
                            li[1] == eventName &&
                            li[2] == fn) {
                            unloadListeners.splice(i, 1);
                            return true;
                        }
                    }
                    return false;
                }
                var cacheItem = null;
                var index = arguments[3];
                if ("undefined" == typeof index) {
                    index = this._getCacheIndex(el, eventName, fn);
                }
                if (index >= 0) {
                    cacheItem = listeners[index];
                }
                if (!el || !cacheItem) {
                    return false;
                }
                this.doRemove(el, eventName, cacheItem[this.WFN], false);
                delete listeners[index][this.WFN];
                delete listeners[index][this.FN];
                listeners.splice(index, 1);
                return true;
            },
            getTarget: function(ev, resolveTextNode) {
                ev = ev.browserEvent || ev;
                var t = ev.target || ev.srcElement;
                return this.resolveTextNode(t);
            },
            resolveTextNode: function(node) {
                if (Ext.isSafari && node && 3 == node.nodeType) {
                    return node.parentNode;
                } else {
                    return node;
                }
            },
            getPageX: function(ev) {
                ev = ev.browserEvent || ev;
                var x = ev.pageX;
                if (!x && 0 !== x) {
                    x = ev.clientX || 0;
                    if (Ext.isIE) {
                        x += this.getScroll()[1];
                    }
                }
                return x;
            },
            getPageY: function(ev) {
                ev = ev.browserEvent || ev;
                var y = ev.pageY;
                if (!y && 0 !== y) {
                    y = ev.clientY || 0;
                    if (Ext.isIE) {
                        y += this.getScroll()[0];
                    }
                }
                return y;
            },
            getXY: function(ev) {
                ev = ev.browserEvent || ev;
                return [this.getPageX(ev), this.getPageY(ev)];
            },
            stopEvent: function(ev) {
                this.stopPropagation(ev);
                this.preventDefault(ev);
            },
            stopPropagation: function(ev) {
                ev = ev.browserEvent || ev;
                if (ev.stopPropagation) {
                    ev.stopPropagation();
                } else {
                    ev.cancelBubble = true;
                }
            },
            preventDefault: function(ev) {
                ev = ev.browserEvent || ev;
                if(ev.preventDefault) {
                    ev.preventDefault();
                } else {
                    ev.returnValue = false;
                }
            },
            getEvent: function(e) {
                var ev = e || window.event;
                if (!ev) {
                    var c = this.getEvent.caller;
                    while (c) {
                        ev = c.arguments[0];
                        if (ev && Event == ev.constructor) {
                            break;
                        }
                        c = c.caller;
                    }
                }
                return ev;
            },
            _getCacheIndex: function(el, eventName, fn) {
                for (var i = 0,len = listeners.length; i < len; ++i) {
                    var li = listeners[i];
                    if (li &&
                        li[this.FN] == fn &&
                        li[this.EL] == el &&
                        li[this.TYPE] == eventName) {
                        return i;
                    }
                }
                return -1;
            },
            elCache: {},
            getEl: function(id) {
                return document.getElementById(id);
            },
            clearCache: function() {
            }, 
            _tryPreloadAttach: function() {
                if (this.locked) {
                    return false;
                }
                this.locked = true;
                var tryAgain = !loadComplete;
                if (!tryAgain) {
                    tryAgain = (retryCount > 0);
                }
                var notAvail = [];
                for (var i = 0,len = onAvailStack.length; i < len; ++i) {
                    var item = onAvailStack[i];
                    if (item) {
                        var el = this.getEl(item.id);
                        if (el) {
                            if (!item.checkReady ||
                                loadComplete ||
                                el.nextSibling ||
                                (document && document.body)) {
                                var scope = el;
                                if (item.override) {
                                    if (item.override === true) {
                                        scope = item.obj;
                                    } else {
                                        scope = item.override;
                                    }
                                }
                                item.fn.call(scope, item.obj);
                                onAvailStack[i] = null;
                            }
                        } else {
                            notAvail.push(item);
                        }
                    }
                }
                retryCount = (notAvail.length === 0) ? 0 : retryCount - 1;
                if (tryAgain) {
                    this.startInterval();
                } else {
                    clearInterval(this._interval);
                    this._interval = null;
                }
                this.locked = false;
                return true;
            },
            purgeElement: function(el, recurse, eventName) {
                var elListeners = this.getListeners(el, eventName);
                if (elListeners) {
                    for (var i = 0,len = elListeners.length; i < len; ++i) {
                        var l = elListeners[i];
                        this.removeListener(el, l.type, l.fn);
                    }
                }
                if (recurse && el && el.childNodes) {
                    for (i = 0,len = el.childNodes.length; i < len; ++i) {
                        this.purgeElement(el.childNodes[i], recurse, eventName);
                    }
                }
            },
            getListeners: function(el, eventName) {
                var results = [], searchLists;
                if (!eventName) {
                    searchLists = [listeners, unloadListeners];
                } else if (eventName == "unload") {
                    searchLists = [unloadListeners];
                } else {
                    searchLists = [listeners];
                }
                for (var j = 0; j < searchLists.length; ++j) {
                    var searchList = searchLists[j];
                    if (searchList && searchList.length > 0) {
                        for (var i = 0,len = searchList.length; i < len; ++i) {
                            var l = searchList[i];
                            if (l && l[this.EL] === el &&
                                (!eventName || eventName === l[this.TYPE])) {
                                results.push({
                                    type:   l[this.TYPE],
                                    fn:     l[this.FN],
                                    obj:    l[this.OBJ],
                                    adjust: l[this.ADJ_SCOPE],
                                    index:  i
                                });
                            }
                        }
                    }
                }
                return (results.length) ? results : null;
            },
            getScroll: function() {
                var dd = document.documentElement, db = document.body;
                if (dd && (dd.scrollTop || dd.scrollLeft)) {
                    return [dd.scrollTop, dd.scrollLeft];
                } else if (db) {
                    return [db.scrollTop, db.scrollLeft];
                } else {
                    return [0, 0];
                }
            },
            doAdd: function () {
                if (window.addEventListener) {
                    return function(el, eventName, fn, capture) {
                        el.addEventListener(eventName, fn, (capture));
                    };
                } else if (window.attachEvent) {
                    return function(el, eventName, fn, capture) {
                        el.attachEvent("on" + eventName, fn);
                    };
                } else {
                    return function() {
                    };
                }
            }(),
            doRemove: function() {
                if (window.removeEventListener) {
                    return function (el, eventName, fn, capture) {
                        el.removeEventListener(eventName, fn, (capture));
                    };
                } else if (window.detachEvent) {
                    return function (el, eventName, fn) {
                        el.detachEvent("on" + eventName, fn);
                    };
                } else {
                    return function() {
                    };
                }
            }()
        };
    }();
var E = Ext.lib.Event;
E.on = E.addListener;
E.un = E.removeListener;
Ext.lib.Dom = {};
Ext.getDom = function(el){
		if(!el || !document){
			return null;
		}
		return el.dom ? el.dom : (typeof el == 'string' ? document.getElementById(el) : el);
};
Ext.EventManager = function(){
    var E = Ext.lib.Event;
    // fix parser confusion
    var xname = 'Ex' + 't';
    var elHash = {};
    var addListener = function(el, ename, fn, wrap, scope){
        var id = Ext.id(el);
        if(!elHash[id]){
            elHash[id] = {};
        }
        var es = elHash[id];
        if(!es[ename]){
            es[ename] = [];
        }
        var ls = es[ename];
        ls.push({
            id: id,
            ename: ename,
            fn: fn,
            wrap: wrap,
            scope: scope
        });
         E.on(el, ename, wrap);
        if(ename == "mousewheel" && el.addEventListener){ // workaround for jQuery
            el.addEventListener("DOMMouseScroll", wrap, false);
            E.on(window, 'unload', function(){
                el.removeEventListener("DOMMouseScroll", wrap, false);
            });
        }
        if(ename == "mousedown" && el == document){ // fix stopped mousedowns on the document
            Ext.EventManager.stoppedMouseDownEvent.addListener(wrap);
        }
    }
    var removeListener = function(el, ename, fn, scope){
        el = Ext.getDom(el);
        var id = Ext.id(el), es = elHash[id], wrap;
        if(es){
            var ls = es[ename], l;
            if(ls){
                for(var i = 0, len = ls.length; i < len; i++){
                    l = ls[i];
                    if(l.fn == fn && (!scope || l.scope == scope)){
                        wrap = l.wrap;
                        E.un(el, ename, wrap);
                        ls.splice(i, 1);
                        break;
                    }
                }
            }
        }
        if(ename == "mousewheel" && el.addEventListener && wrap){
            el.removeEventListener("DOMMouseScroll", wrap, false);
        }
        if(ename == "mousedown" && el == document && wrap){ // fix stopped mousedowns on the document
            Ext.EventManager.stoppedMouseDownEvent.removeListener(wrap);
        }
    }
    var createBuffered = function(h, o){
        var task = new Ext.util.DelayedTask(h);
        return function(e){
            // create new event object impl so new events don't wipe out properties
            e = new Ext.EventObjectImpl(e);
            task.delay(o.buffer, h, null, [e]);
        };
    };
    var createSingle = function(h, el, ename, fn, scope){
        return function(e){
            Ext.EventManager.removeListener(el, ename, fn, scope);
            h(e);
        };
    };
    var createDelayed = function(h, o){
        return function(e){
            // create new event object impl so new events don't wipe out properties
            e = new Ext.EventObjectImpl(e);
            setTimeout(function(){
                h(e);
            }, o.delay || 10);
        };
    };
    var listen = function(element, ename, opt, fn, scope){
        var o = (!opt || typeof opt == "boolean") ? {} : opt;
        fn = fn || o.fn; scope = scope || o.scope;
        var el = Ext.getDom(element);
        if(!el){
            throw "Error listening for \"" + ename + '\". Element "' + element + '" doesn\'t exist.';
        }
        var h = function(e){
            // prevent errors while unload occurring
            if(!window[xname]){
                return;
            }
            e = Ext.EventObject.setEvent(e);
            var t;
            if(o.delegate){
                t = e.getTarget(o.delegate, el);
                if(!t){
                    return;
                }
            }else{
                t = e.target;
            }
            if(o.stopEvent === true){
                e.stopEvent();
            }
            if(o.preventDefault === true){
               e.preventDefault();
            }
            if(o.stopPropagation === true){
                e.stopPropagation();
            }
            if(o.normalized === false){
                e = e.browserEvent;
            }
            fn.call(scope || el, e, t, o);
        };
        if(o.delay){
            h = createDelayed(h, o);
        }
        if(o.single){
            h = createSingle(h, el, ename, fn, scope);
        }
        if(o.buffer){
            h = createBuffered(h, o);
        }
        addListener(el, ename, fn, h, scope);
        return h;
    };
    var propRe = /^(?:scope|delay|buffer|single|stopEvent|preventDefault|stopPropagation|normalized|args|delegate)$/;
    var pub = {
        addListener : function(element, eventName, fn, scope, options){
            if(typeof eventName == "object"){
                var o = eventName;
                for(var e in o){
                    if(propRe.test(e)){
                        continue;
                    }
                    if(typeof o[e] == "function"){
                        // shared options
                        listen(element, e, o, o[e], o.scope);
                    }else{
                        // individual options
                        listen(element, e, o[e]);
                    }
                }
                return;
            }
            return listen(element, eventName, options, fn, scope);
        },
        removeListener : function(element, eventName, fn, scope){
            return removeListener(element, eventName, fn, scope);
        }, 
        ieDeferSrc : false,
        textResizeInterval : 50
    };
    pub.on = pub.addListener;
    pub.un = pub.removeListener;
    pub.stoppedMouseDownEvent = new Ext.util.Event();
    return pub;
}();
Ext.EventObject = function(){
    var E = Ext.lib.Event;
    // normalize button clicks
    var btnMap = Ext.isIE ? {1:0,4:1,2:2} :
                (Ext.isSafari ? {1:0,2:1,3:2} : {0:0,1:1,2:2});
    Ext.EventObjectImpl = function(e){
        if(e){
            this.setEvent(e.browserEvent || e);
        }
    };
    Ext.EventObjectImpl.prototype = {
        browserEvent : null,
        button : -1,
        shiftKey : false,
        ctrlKey : false,
        altKey : false, 
        DELETE: 46,
        setEvent : function(e){
            if(e == this || (e && e.browserEvent)){ // already wrapped
                return e;
            }
            this.browserEvent = e;
            if(e){
                // normalize buttons
                this.button = e.button ? btnMap[e.button] : (e.which ? e.which-1 : -1);
                if(e.type == 'click' && this.button == -1){
                    this.button = 0;
                }
                this.type = e.type;
                this.shiftKey = e.shiftKey;
                // mac metaKey behaves like ctrlKey
                this.ctrlKey = e.ctrlKey || e.metaKey;
                this.altKey = e.altKey;
                // in getKey these will be normalized for the mac
                this.keyCode = e.keyCode;
                this.charCode = e.charCode;
                // cache the target for the delayed and or buffered events
                this.target = E.getTarget(e);
                // same for XY
                this.xy = E.getXY(e);
            }else{
                this.button = -1;
                this.shiftKey = false;
                this.ctrlKey = false;
                this.altKey = false;
                this.keyCode = 0;
                this.charCode = 0;
                this.target = null;
                this.xy = [0, 0];
            }
            return this;
        },
        stopEvent : function(){
            if(this.browserEvent){
                if(this.browserEvent.type == 'mousedown'){
                    Ext.EventManager.stoppedMouseDownEvent.fire(this);
                }
                E.stopEvent(this.browserEvent);
            }
        },
        preventDefault : function(){
            if(this.browserEvent){
                E.preventDefault(this.browserEvent);
            }
        }, 
        stopPropagation : function(){
            if(this.browserEvent){
                if(this.browserEvent.type == 'mousedown'){
                    Ext.EventManager.stoppedMouseDownEvent.fire(this);
                }
                E.stopPropagation(this.browserEvent);
            }
        },  
        getPageX : function(){
            return this.xy[0];
        },
        getPageY : function(){
            return this.xy[1];
        },
        getXY : function(){
            return this.xy;
        },
        getTarget : function(selector, maxDepth, returnEl){
            return returnEl ? Ext.get(this.target) : this.target;
        } 
    };
    return new Ext.EventObjectImpl();
}();
Ext.ns('Element');
Ext.applyIf(Element, {
	visible: function(element) {
		element = $(element);
		if(Ext.type(element)!='element')return;
		return $(element).style.display != 'none';
	},
	show: function(element) {
		element = $(element);
		if(Ext.type(element)!='element')return;
		return element.style.display = '';//Ext.fly(element).show();
	},
	hide: function(element) {
		element = $(element);
		if(Ext.type(element)!='element')return;
		return element.style.display = 'none';//Ext.fly(element).hide();
	}
});
var FileUploadHelper = {
	validFileExt :function(_strPath,_sAllowExt,_fAlert){
		var sAllowExt = _sAllowExt;
		_strPath = _strPath || '';
		_fAlert = _fAlert||window.$alert||alert;
		if(_strPath.length<=0){
			(_fAlert)("没有选择文件");
			return false;
		}
		var validFileExtRe = new RegExp('.+\.('+_sAllowExt.split(',').join('|')+')$','ig');
		if(!validFileExtRe.test(_strPath)){
			(_fAlert)(String.format("只支持上传\"{0}\"格式的文件！",sAllowExt));
			return false;
		}
		return true;
	},
	fileUploadedAlert : function(sResponseText,_fNoErr,_fErr,_fAlert){
		if(sResponseText.match(/<!--ERROR-->/img)){
			var texts = sResponseText.split('<!--##########-->');
			_fAlert = _fAlert||window.$alert||alert;
			if(texts[0]==0){
				(_fAlert)(texts[1]);
			}
			else{
				if(window.FaultDialog){
					FaultDialog.show({
						code		: texts[0],
						message		: texts[1],
						detail		: texts[2],
						suggestion  : ''
					}, '与服务器交互时出错啦');
				}
			}
			if(_fErr)(_fErr)();
			else if(window.ProcessBar){
				ProcessBar.close();
			}
			return true;
		}
		else{
			(_fNoErr)();
		}
		return false;
	}
}
var oEditor		= window.dialogArguments.et ;
var isSSL = location.href.indexOf("https://")!=-1;
var oImage = null;
function Ok(_bUploaded){
	if(oImage==null&&!_bUploaded){
		UploadFile(function(){Ok(true);});
		return;
	}
	var bHasImage = ( oImage != null );
	
	if (!bHasImage){
		oImage = document.createElement('IMG');
		if(!sUploadFileName.match(/^(http|https|ftp):/ig)){
			oImage.setAttribute('src', WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName='+sUploadFileName) ;
			oImage.setAttribute('_fcksavedurl', WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName='+sUploadFileName) ;
			oImage.setAttribute('border', 0 ) ;
			oImage.setAttribute('UploadPic', sUploadFileName) ;
		}else{
			oImage.setAttribute('src', sUploadFileName);
			oImage.setAttribute('border', 0 );
			oImage.setAttribute('_fcksavedurl', sUploadFileName ) ;
			if($('cbIgnore') && $('cbIgnore').checked){
				oImage.setAttribute( oImage, "ignore" , "1" ) ;
			}
		}
		UpdateImage(oImage, bHasImage); 
	} 
	return true;
}

function UpdateImage( oImage, bHasImage )
{
	if($('txtAlt').value){
		oImage.setAttribute("alt"   , $('txtAlt').value ) ;
	}
	else{
		oImage.removeAttribute("alt") ;
	}
	if($('txtWidth').value){
		oImage.setAttribute("width" , $('txtWidth').value ) ;
	}
	else{
		oImage.removeAttribute("width") ;
	}
	if($('txtHeight').value){
		oImage.setAttribute("height", $('txtHeight').value ) ;
	}
	else{
		oImage.removeAttribute("height") ;
	}
	if($('txtBorder').value){
		oImage.style.borderWidth = '';
		oImage.setAttribute("border", $('txtBorder').value ) ;
	}
	else{
		oImage.removeAttribute("border") ;
		oImage.style.borderWidth = 0;
	}
	var win = null;
	if(window.opener) win = window.opener.$('editor_body_area').contentWindow;
	else win = oEditor.iframe.contentWindow;

	InsertElement(win, oImage);
}

function InsertHtml(win, sHtml){
	if(win.getSelection){
		var oSelection = win.getSelection();
		var oRange = oSelection.getRangeAt(0);
		var oFragment = oRange.createContextualFragment(sHtml) ;
		oRange.insertNode(oFragment);
	}else{
		if(win.document.selection.type.toLowerCase() != "none"){
			win.document.selection.clear() ;
		}
		win.focus();
		var oRange = win.document.selection.createRange();
		oRange.pasteHTML(sHtml);
		oRange.collapse(false);
		oRange.select();
	}
}

function InsertElement(win, dom){
	if(dom.outerHTML){
		InsertHtml(win, dom.outerHTML);
	}else if(win.getSelection){
		var oSelection = win.getSelection();
		var oRange = oSelection.getRangeAt(0);
		oRange.insertNode(dom);
	}
}

var isSSL = location.href.indexOf("https://")!=-1;
function UploadFile(_fCallBack){
	var sFileName = $("PicUpload").value;
	if( oImage && sFileName.length==0 ){
		_fCallBack();
		setTimeout(function(){
			window.close();
		},10);
		return false;
	}
	if(sFileName.match(/^(http|https|ftp):/ig)){
		sUploadFileName = sFileName;
		_fCallBack();
		setTimeout(function(){
			window.close();
		},10);
		return false;
	}
	
	if(!FileUploadHelper.validFileExt(sFileName, "jpg,gif,png,bmp")){
		return false;
	} 
	var nCount = 0;
	var lTimeout = setInterval(function(){
		if(nCount>=40){
			nCount = 1;
		}
		else{
			//$('upload_message').innerHTML = $('upload_message').innerHTML + '.';
			nCount++;
		}
	},5);
	var callBack = {
		"upload":function(_transport){
			clearInterval(lTimeout);
			//$('upload_message').innerHTML = '上传完成.';
			//Element.hide('upload_message');
			var sResponseText = _transport.responseText;
			FileUploadHelper.fileUploadedAlert(sResponseText, function(){
				var fileNames = null;
				eval("fileNames="+sResponseText);
				sUploadFileName = fileNames[0];
				_fCallBack();
				setTimeout(function(){
					top.close();
				},10);
			},function(){
				//$('btnOk').disabled = false;
			});
		}
	}
	YUIConnect.setForm('fm_pic',true,isSSL);
	try{
		YUIConnect.asyncRequest('POST', WCMConstants.WCM6_PATH + '/system/file_upload_dowith.jsp?ResponseType=2&FileParamName=PicUpload',callBack);
	}catch(err){
		//$('btnOk').disabled = false;
		clearInterval(lTimeout);
		//Element.hide('upload_message');
	}
	return false;
}
var showOptions = false;
// Shows/hides the "more options" part of the UI.
function toggleOptions(){
	var icon = $('toggleIcon');
	var link = $('toggleLink');
	var imageOptions = $('tr_imageoptions');
	if (icon && link) {
		showOptions = Element.visible(imageOptions);
		if (!showOptions) {
			icon.src = '../images/collapse.gif';
			icon.alt = link.innerHTML = '隐藏图片选项';
			Element.show(imageOptions);
			$('div_sep').style.height = '450px';
			parent.dialogHeight = '500px' ;
			parent.dialogTop = parseInt(parent.dialogTop,10)-120;
		} else {
			icon.src = '../images/expand.gif';
			icon.alt = link.innerHTML = '显示图片选项';
			Element.hide(imageOptions);
			$('div_sep').style.height = '130px';
			parent.dialogHeight = '180px' ;
			parent.dialogTop = parseInt(parent.dialogTop,10)+120;
		}
	}
}