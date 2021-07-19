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
