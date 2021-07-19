/*!
 * Copyright 2011, trs
 * 
 *
 * Date: 2011-1-26
 */
(function($){
	$.widget( "ui.imgbutton",$.fn.extend($.ui.button.prototype,{
		_resetButton : function(){
			if ( this.type === "input" ) {
				if ( this.options.label ) {
					this.element.val( this.options.label );
				}
				return;
			}
			var typeClasses = "ui-button-icons-only ui-button-icon-only ui-button-text-icons ui-button-text-icon-primary ui-button-text-icon-secondary ui-button-text-only";
			var buttonElement = this.buttonElement.removeClass( typeClasses ),
				buttonText = $( "<span></span>" )
					.addClass( "ui-button-text" )
					.html( this.options.label )
					.appendTo( buttonElement.empty() )
					.text(),
				icons = this.options.icons;
				
			buttonElement.addClass( "ui-button-text-icons");
			buttonElement.prepend( "<span class='ui-button-icon-primary ui-icon '></span>" );
			buttonElement.append( "<span class='ui-button-icon-secondary ui-icon '></span>" );
			if ( !this.options.text ) {
				buttonElement
					.addClass( "ui-button-icons-only" )
					.removeClass( "ui-button-text-icons ui-button-text-icon-primary ui-button-text-icon-secondary" );
				if ( !this.hasTitle ) {
					buttonElement.attr( "title", buttonText );
				}
			}
		}
	}));
	
	$.widget( "ui.morebutton", $.fn.extend($.ui.button.prototype,{
		_resetButton : function(){
			if ( this.type === "input" ) {
				if ( this.options.label ) {
					this.element.val( this.options.label );
				}
				return;
			}
			var typeClasses = "ui-button-icons-only ui-button-icon-only ui-button-text-icons ui-button-text-icon-primary ui-button-text-icon-secondary ui-button-text-only";
			var buttonElement = this.buttonElement.removeClass( typeClasses ),
				buttonText = $( "<span></span>" )
					.addClass( "ui-button-text" )
					.html( this.options.label )
					.appendTo( buttonElement.empty() )
					.text(),
				icons = this.options.icons;
				
			buttonElement.addClass( "ui-button-text-icons");
			buttonElement.prepend( "<span class='ui-button-icon-primary ui-icon '></span>" );
			buttonElement.append( "<span class='ui-button-icon-secondary-more ui-icon '></span>" );
			if ( !this.options.text ) {
				buttonElement
					.addClass( "ui-button-icons-only" )
					.removeClass( "ui-button-text-icons ui-button-text-icon-primary ui-button-text-icon-secondary" );
				if ( !this.hasTitle ) {
					buttonElement.attr( "title", buttonText );
				}
			}
			var fn = buttonElement.get(0).onclick;

			
			buttonElement.get(0).onclick = function(event){
				event = event||window.event;
				var dom = event.target || event.srcElement;
				if($(dom).hasClass("ui-button-icon-secondary-more")){
					//TODO
					var moreJDom = $(dom.parentNode).next(".ui-btn-more:eq(0)");
					moreJDom.addClass("ui_btn_more_menu");

					var bubblePanel = new $.BubblePanel(moreJDom.get(0));
					var x = parseInt(event.x||event.offsetX, 10) || 0;
					var y = parseInt(event.y||event.offsetY, 10) || 0;
					bubblePanel.bubble([x,y], function(_Point){
						return [_Point[0], _Point[1]];
					});
					moreJDom.bind('mouseover', function(event){
						var dom = event.target || event.srcElement;
						var target = findMenuItem(dom);
						if(target==null)return;
						$(target).addClass('item_active');
					});
					moreJDom.bind('mouseout', function(event){
						var dom = event.target || event.srcElement;
						var target = findMenuItem(dom);
						if(target==null)return;
						$(target).removeClass('item_active');
					});
				}else{
					fn.call();
				}
				
			}
			var findMenuItem = function(dom){
				if($(dom).hasClass("ui-btn-more-item"))return dom;
				return null;
			}
		}

	}));



})(jQuery);


/**
*CrashBoard
*/
(function(host){
var 
guid = 0,
cache = {},
isSecure = location.href.toLowerCase().indexOf("https") === 0,
blankUrl = isSecure ? "javascript:false" : '',
isStrict = document.compatMode == "CSS1Compat",
ua = navigator.userAgent.toLowerCase(),
isIE6 = ua.indexOf("opera") == -1 && ua.indexOf("msie 6") > -1,
btnTemplate = [
	'<div class="wcm-btn wcm-btn-left {2}" id="{0}" btn="{0}">',
		'<div class="wcm-btn-right">',
			'<div class="wcm-btn-center">',
				'<a class="wcm-btn-text" href="#" onfocus="this.blur();">{1}</a>',
			'</div>',
		'</div>',
	'</div>'
].join(""),
template = [
	'<div class="wcm-cbd" id="{0}" style="visibility:hidden;">',
		'<div class="header l" id="header-{0}"><div class="r"><div class="c">',
			'<div class="spt"></div>',
			'<div class="title" id="dialogTitle-{0}">{1}</div>',
			'<div class="tools" id="tools-{0}">',
				'<a class="close" href="#" id="close-{0}"></a>',
			'</div>',
		'</div></div></div>',
		'<div class="body l"><div class="r"><div class="c">',
			'<table border=0 cellspacing=0 cellpadding=0 class="cb-table">',
				'<tr><td id="content-{0}">',
				'<iframe src="{2}" id="frm-{0}" style="height:100%;width:100%;"',
				' frameborder="0" onload="__CrashBoardContentLoaded__(\'{0}\', this);"></iframe>',
				'</td></tr>',
				'<tr style="display:{5}">',
					'<td class="buttons" id="buttons-{0}" style="text-align:center;"></td>',
				'</tr>',
			'</table>',
		'</div></div></div>',
		'<div class="footer l"><div class="r"><div class="c"></div></div></div>',
	'</div>'
].join("");

function $cb(cfg){
	cfg = apply({
		id : 'cb-' + (++guid),
		title : 'wcm窗口'
	}, cfg);
	apply(this, cfg);
	cache[cfg.id] = this;
}
$cb.prototype = {
	getEl : function(id){
		return $(id || this.id);
	},
	show : function(){
		var t = this;
		if(!t.rendered){
			t.rendered = true;

			//append html
			var sHtml = format(template, t.id, t.title, t.url);
			var div = document.createElement('DIV');
			document.body.appendChild(div);
			div.innerHTML = sHtml;
			var cbEle = t.getEl();

			//set bounds
			if(t.width) cbEle.style.width = t.width;
			if(t.height) t.getEl('content-' + t.id).style.height = t.height;
			var docEle = isStrict ? document.documentElement : document.body;
			var left = (docEle.clientWidth - cbEle.offsetWidth) / 2 + docEle.scrollLeft + 'px';
			var top = (docEle.clientHeight - cbEle.offsetHeight) / 2 + docEle.scrollTop + 'px';
			cbEle.style.left = t.left || left;
			cbEle.style.top = t.top || top;
			cbEle.style.zIndex = 999 + guid;
			cbEle.style.visibility = 'visible';
			cbEle.style.display = '';

			//bind events
			if(this.draggable !== false) drag(cbEle, t.getEl('header-' + t.id), this.maskable);
			t.getEl('close-' + t.id).onclick = function(){t.close.apply(t, arguments)};
			t.getEl('buttons-' + t.id).onclick = function(){t.onClick.apply(t, arguments)};
		}
		this.getEl().style.display = '';
		this.showShield();
	},
	renderBtns : function(btns){
		this.btns = btns = btns || [];
		var aHtml = [];
		for(var i = 0; i < btns.length; i++){
			var btn = btns[i]; btn.id = btn.id || ('btn-'+(++guid)); btns[btn.id] = btn;
			aHtml.push(format(btnTemplate, btn.id, btn.text, btn.extraCls||""));
		}
		this.getEl('buttons-' + this.id).innerHTML = aHtml.join("");
	},
	hide : function(){
		this.getEl().style.display = 'none';
		this.hideShield();
	},
	close : function(){
		try{
			var t = this;
			t.hide();
			t.getEl("frm-" + t.id).src = blankUrl;
			t.getEl("content-" + t.id).innerHTML = '';
			t.getEl("close-" + t.id).onclick = null;
			t.getEl("buttons-" + t.id).onclick = null;
			t.destroyShield();
			delete cache[t.id];
			dom = t.getEl();
			dom.parentNode.parentNode.removeChild(dom.parentNode);
			dom = null;
		}catch(err){}
	},
	onClick : function(event){
		var t = this;
		event = window.event || event;
		var dom = event.srcElement || event.target;
		var btnId, btnsId='buttons-' + t.id;
		while(dom && dom.id != btnsId){
			btnId = dom.getAttribute("btn");
			if(btnId)break;
			dom = dom.parentNode;
		}
		if(t.btns[btnId] && t.btns[btnId].cmd){
			if(t.btns[btnId].cmd.call(t, t.btns[btnId]) !== false){
				t.close();
			}
		}
	},
	initShield : function(){
		if(!this.maskable && !isIE6) return;
		if($(this.id + '-shld')) return;
		var dom = document.createElement('iframe');
		dom.src = blankUrl;
		dom.style.display = 'none';
		dom.style.border = 0;
		dom.frameBorder = 0;
		dom.className = 'wcm-panel-shield';
		dom.style.zIndex = this.getEl().style.zIndex - 1;
		dom.id = this.id + '-shld';
		document.body.appendChild(dom);
	},
	showShield : function(){
		if(!this.maskable && !isIE6) return;
		this.initShield();
		var dom = this.getEl();
		var oStyle = $(this.id + '-shld').style;
		if(!this.maskable){
			oStyle.left = (parseInt(dom.style.left, 10) )+"px";
			oStyle.top = (parseInt(dom.style.top, 10) + 1)+"px";
			oStyle.width = (dom.offsetWidth - 4)+"px";
			oStyle.height = (dom.offsetHeight - 4)+"px";
		}	
		oStyle.display = '';
	},
	hideShield : function(){
		if(!this.maskable && !isIE6) return;
		$(this.id + '-shld').style.display = 'none';
	},
	destroyShield : function(){
		if(!this.maskable && !isIE6) return;
		var dom = $(this.id + '-shld');
		if(!dom) return;
		dom.parentNode.removeChild(dom);
	}
};
function $(id){
	return document.getElementById(id);
}
function apply(o, c){
	if(o && c && typeof c == 'object'){
		for(var p in c){
			o[p] = c[p];
		}
	}
	return o;
};
function format(format){
	var args = Array.prototype.slice.call(arguments, 1);
	return format.replace(/\{(\d+)\}/g, function(m, i){
		return args[i]||"";
	});
}
function $toQueryStr(params){
	var rst = [];
	for(var nm in params){
		var v = params[nm], type = typeof v;
		if(type!='string' && type!='number' && type!='boolean')continue;
		rst.push(nm, '=', encodeURIComponent(params[nm]), '&');
	}
	return rst.join('');
}
var getStyle = function(){
	return window.getComputedStyle ? function(el, style){
		var cs = window.getComputedStyle(el, "");
		return cs ? cs[style] : null;
	} : function(el, style){
		return el.style[style] || el.currentStyle[style];
	}
}();
function drag(o, p, maskable){
	var id = o.id;
	p.onmousedown=function(a){
		var frm = $('frm-' + id);
		if(frm) frm.style.visibility = 'hidden';
		var sld = $(id + '-shld');
		var d=document;if(!a)a=window.event;
		var l=parseInt(getStyle(o,'left'),10),t=parseInt(getStyle(o,'top'),10);
		var x=a.pageX?a.pageX:a.clientX,y=a.pageY?a.pageY:a.clientY;
		if(p.setCapture)p.setCapture();
		else if(window.captureEvents)window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
		d.onmousemove=function(a){
			if(!a)a=window.event;
			if(!a.pageX)a.pageX=a.clientX;
			if(!a.pageY)a.pageY=a.clientY;
			var tx=a.pageX-x+l,ty=a.pageY-y+t;
			o.style.left=tx+"px";
			o.style.top=ty+"px";
			if(!maskable && sld){
				sld.style.left=(tx)+"px";
				sld.style.top=(ty+1)+"px";
			}
		}
		d.onmouseup=function(){
			if(frm) frm.style.visibility = 'visible';
			if(p.releaseCapture)p.releaseCapture();
			else if(window.releaseEvents)window.releaseEvents(Event.MOUSEMOVE|Event.MOUSEUP);
			d.onmousemove=null;
			d.onmouseup=null;
		}
    }
}
__CrashBoardContentLoaded__ = function(id, frm){
	var cb = cache[id];
	if(!cb) return;
	try{
		var win = frm.contentWindow;
		cb.renderBtns((win.m_cbCfg || {}).btns);
		if(!win.init)return;
	}catch(err){
		return;
	}
	win.init(cb.params, cb);
}
//expose the global host context
host.CrashBoard = function(cfg){
	cfg = apply({appendParamsToUrl : true}, cfg);
	var cjoin = cfg.url.indexOf('?')==-1 ? '?' : '&';
	if(cfg.appendParamsToUrl){
		cfg.url = cfg.url + cjoin + $toQueryStr(cfg.params);
	}
	return cache[cfg.id] || (new $cb(cfg));
}
})(jQuery);

(function($){
$.BubblePanel = function(el, fid){
	var ua = navigator.userAgent.toLowerCase();
	var isIE = ua.indexOf("opera") == -1 && ua.indexOf("msie") > -1;
	var jqueryEl = $(el);
	if(isIE){
		var arr = el.getElementsByTagName("*");
		for(var i=0;i<arr.length;i++)
			arr[i].setAttribute('unselectable', 'on');
	}
	jqueryEl.bind('click blur', function(event){
		hide(event);
	});
	var contains = function(t, p){
		while(p && p.tagName!='BODY'){
			if(t==p)return true;
			p = p.parentNode;
		}
		return false;
	}
	var hide = function(ev){
		if(ev.type=='blur' && contains(jqueryEl[0], ev.blurTarget))return;
		var el = jqueryEl[0];
		//BpShieldMgr.hideShield(el);
		el.style.display = 'none';
	}
	this.bubble = function(p, map, render){
		var el = jqueryEl[0];
		if(map)p = map.apply(el, [p]);
		var ost = el.style;
		if(render){
			render.apply(el, [p]);
		}else if(p){
			ost.left = p[0] + 'px';
			ost.top = p[1] + 'px';
		}
		ost.display = '';
		//BpShieldMgr.showShield(el);
		jqueryEl.focus();
	};
	
};
function isIE6(){
	return $.browser.msie && ($.browser.version==6.0);
}
})(jQuery);

/*
 * jQuery UI Tabpage 1.8.9
 * @TRS
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 */
(function( $, undefined ) {
	var tabPageTemplate = [
		'<div class="tab-head" id="{0}">',
			'<div class="l">',
				'<div class="r {2}" id="{0}_r">',
					'<div class="c">',
						'<div class="desc">{1}</div>',
					'</div>',
				'</div>',
			'</div>',
		'<span class="closepic"></span></div>'
	].join("");
	var menuItem = [
		'<table class="menu_item {2}" id="{0}" {2}>',
		'<tr>',
			'<td class="menu_cont" nowrap="true">{1}</td>',
		'</tr>',
		'</table>'
	].join('');
	var menuSep = [
		'<div class="menuOps_sep"></div>'
	].join('');

	$.widget( "ui.tabpage", {
		options: {
			URL : null,
			params : null,
			menu : null,
			callback : null
		},
		tabHeadBoxId : 'tab_head_box',
		ifm_Suffix : '_ifm',
		contentBoxId : 'main',
		
		menuResult : {},
		onRender : function(config){
			//用户注册菜单的操作
			
			//绑定事件
			this.initEvents();
			this.toMoreMenuHtml(config);
		},
		_create: function() {
			if(this.options.URL){
				this.openTabPage(this.options.URL,this.options.params,this.options.callback);
			}else if(this.options.menu){
				this.onRender(this.options.menu);
			}
		},
		/**
		*打开一个新的标签页
		*/
		openTabPage : function(sURL,oparms,fnCallback){
			var text = oparms.text;
			var id = oparms.id;
			this.hideOldItem();
			var ifmId = id+this.ifm_Suffix;
			var isWithClose = oparms.withClose?"withClose" :"";
			if(!$("#"+ifmId).get(0)){
				var tabItemHtml = String.format(tabPageTemplate,id,text,isWithClose);
				$("#"+this.tabHeadBoxId).append(tabItemHtml);
				this.setActiveTab($("#"+id));
				var sldDom = document.createElement("iframe");
				sldDom.id = ifmId;
				sldDom.src = sURL;
				//指定样式
				sldDom.style.width = "100%";
				sldDom.style.height = "100%";
				sldDom.frameBorder = 0;
				$("#"+this.contentBoxId).append(sldDom);
			}else{
				this.setActiveTab($("#"+id));
				$("#"+ifmId).show();
			}
			this.initItemEvents();
		},
		getHeader : function(){
			if(this.header) return $("#"+this.header);
			var dom = $("#"+this.tabHeadBoxId);
			return dom;
		},
		getMenu : function(){
			var header = this.getHeader();
			var menuItem = header.find(".menu");
			if(menuItem.get())
				return menuItem;
			return null;
		},
		// private
		initEvents : function(){
			var menu = this.getMenu();
			/**
			*点击菜单，显示更多操作
			*/
			var self = this;
			var showMenu = function(event){
				event = event || window.event;
				var element = event.target || event.srcElement;
				if(findMenuItem(element)!=null)return;
				//var p = event.getPoint();
				var x = event.x||event.offsetX + 4;
				var y = event.y||event.offsetY + 4;
				self.onShowMenu({x:x,y:y});
			}
			menu.bind('click',function(event){showMenu(event)});
		},
		/**
		*对每个新打开的标签页初始化事件
		*/
		initItemEvents : function(){
			var self = this;
			var onClick = function(e){
				//Event.stop(event);
				var dom = event.target || event.srcElement;
				var tabItem = self.findTabItem(dom);
				if(tabItem==null)return;
				if(self.isCloseOper(dom)){
					var preEle = tabItem.get(0).previousSibling;
					self.closeTabPage(tabItem);
					//需要激活前一个标签页
					tabItem = $(preEle);
				}
				self.setActiveTab(tabItem);
				self.hideOldItem();
				var ifmId = tabItem.attr("id") + self.ifm_Suffix;
				$("#"+ifmId).show();
			}
			var header = this.getHeader();
			//重新绑定事件
			header.unbind();//取消所有事件
			header.bind('click',function(event){
				onClick(event);
			});
			//聚焦处理

			
		},
		/**
		*点击切换标签页
		*/
		
		isCloseOper : function(dom){
			dom = $(dom);
			if(dom.hasClass('c')||dom.hasClass(dom, 'desc')){
				return false;
			}else if(dom.hasClass('r')&&dom.hasClass('withClose')){
				//判断是否是激活状态
				if(!dom.parent().hasClass('active')){
					return false;
				}
				return true;
			}
			return false;
		},
		/**
		*得到所有菜单操作html，有权限过滤
		*/
		toMoreMenuHtml : function(config){
			var result = [];
			var json = {};
			for (var i = 0,nLen = config.length; i < nLen; i++){
				if(result.length>0){
					result.push(menuSep);
				}
				json[config[i].id.toLowerCase()] = config[i];
				var id = config[i].id;
				var text = config[i].text;
				result.push(String.format(menuItem, id, text, this.extraCls||""));
			}
			$('#more_menubar').append(result.join(''));
			this.menuResult = {
				html : result.join(''),
				json : json
			};
			this.onClickMenuItem();
		},
		onClickMenuItem : function(){
			var aMenuResult = this.menuResult;
			$('.more_menubar').bind('click', function(event){
				var dom = event.target || event.srcElement;
				var target = findMenuItem(dom);

				if(target==null)return;
				var menuItem = aMenuResult.json[target.id];
				if(menuItem==null || !menuItem.cmd)return;
				if(menuItem.disabled)return;
				menuItem.cmd.call();
				//隐藏不应该在这里做
				$('.more_menubar').hide();
			});
			$('.more_menubar').bind('mouseover', function(event){
				var dom = event.target || event.srcElement;
				var target = findMenuItem(dom);
				if(target==null)return;
				$(target).addClass('item_active');
			});
			$('.more_menubar').bind('mouseout', function(event){
				var dom = event.target || event.srcElement;
				var target = findMenuItem(dom);
				if(target==null)return;
				$(target).removeClass('item_active');
			});
			$('.more_menubar').bind('blur',function(event){
				$('.more_menubar').hide();
			});
		},
		
		
		onShowMenu : function(position){
			//this.oContainer.style.overflow = 'visible';
			var moreDom = $('.more_menubar');
			var bubblePanel = new $.BubblePanel(moreDom.get(0));
			var x = parseInt(position["x"], 10) || 0;
			var y = parseInt(position["y"], 10) || 0;
			bubblePanel.bubble([x,y], function(_Point){
				return [_Point[0], _Point[1]];
			});
		},
		/**
		*标签页激活的处理
		*/
		setActiveTab : function(dom){
			var itemChild = dom.children(".l");
			var activeItem = this.findActiveItem();
			if(activeItem)activeItem.removeClass('active');
			if(itemChild)itemChild.addClass('active');
		},
		findActiveItem : function(){
			var header = this.getHeader();
			var item = header.find(".active");
			if(item.get())
				return item;
			return null;
		},
		/**
		*隐藏当前显示的内容
		*/
		hideOldItem : function(){
			var ifrDoms = $("#"+this.contentBoxId).children();
			for (var i = 0,nLen = ifrDoms.length; i < nLen; i++){
				//if(!Element.visible(ifrDoms[i]))continue;
				$(ifrDoms[i]).hide();
			}
		},
		/**
		*找到标签页的dom
		*/
		findTabItem : function(dom){
			var id = this.tabHeadBoxId;
			while(dom && dom.id != id){
				if($(dom).hasClass('tab-head')){
					return $(dom);
				}
				dom = dom.parentNode;
			}
			
			return null;
		},
		widget: function() {
			alert("widget");
		},

		destroy: function() {
		
		},
		/**
		*关闭标签页
		*/
		closeTabPage : function(target){
			//释放事件绑定

			target.unbind();
			//删除标签节点
			target.remove();
			//删除内容iframe
			$("#"+target.attr("id")+this.ifm_Suffix).remove();
		}
	
	});
	function findMenuItem(target){
		while(target!=null&&target.tagName.toUpperCase()!='BODY'){
			if($(target).hasClass('menu_item')){
				return target;
			}
			target = target.parentNode;
		}
		return null;
	}

}( jQuery ) );