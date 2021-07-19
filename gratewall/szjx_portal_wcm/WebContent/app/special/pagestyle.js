Ext.ns('wcm.special.toolbar');


/**
*页面风格顶部工具栏的相关处理
*/
(function(){
	wcm.special.design.PageStyle = {
		/**
		*获取可视化设计页面中已经使用的风格
		*/
		get : function(){
			return PageController.getMainWin().PageController.getPageStyleId();
		},
		/**
		*设置可视化设计页面中将使用的风格
		*/
		set : function(nPageStyleId, sCssFilePath){
			PageController.getMainWin().PageController.setPageStyle(nPageStyleId, sCssFilePath);
		}
	};

	var rendered;
	var container = 'styleList';

	wcm.special.toolbar.PageStyle = {
		/**
		*首次渲染关于风格的html信息
		*/
		render : function(relatedEl){
			rendered = true;
			new Insertion.Bottom(document.body, '<div id="'+container+'" class="styleList" style="display:none;" ignore="1"></div><iframe frameborder="0" class="styleList-shield" src="'+Ext.blankUrl+'"></iframe>');
			Position.clone(relatedEl, container, {setWidth:false, setHeight:false, offsetTop:relatedEl.offsetHeight-1});
			Element.show(container);
			Position.clone(container, Element.next($(container)));
			Element.hide(container);
			Element.out(container, function(event){
				Element.hide(container);
				Element.hide(Element.next($(container)));				
				Element.removeClassName(relatedEl, 'toolbaritemhover');
			});
			var caller = this;
			Event.observe(container, 'click', function(event){
				//Event.stop(event);
				var el = Event.element(event);
				if(Element.hasClassName(el, 'morePageStyle')){
					caller.selectPageStyle(wcm.special.design.PageStyle.get());
					return;
				}
				if(el.tagName != 'INPUT') return;
				wcm.special.design.PageStyle.set(el.value, el.getAttribute("cssFile"));
				Element.hide(container);
				Element.hide(Element.next($(container)));
				Element.removeClassName(relatedEl, 'toolbaritemhover');
			});
		},
		selectPageStyle : function(selectedIds, fCallBack){
			wcm.CrashBoarder.get('pagesytle_select').show({
				title : '页面风格列表',
				src : 'style/pagestyle_select_list.html?selectedId=' + selectedIds,
				width:'630px',
				height:'450px',
				callback : function(_json){
					if(fCallBack){
						fCallBack();
					}else{
						wcm.special.design.PageStyle.set(_json.StyleId, _json.CssFile);
						wcm.CrashBoarder.get('pagesytle_select').close();
					}
				}
			});	
		},
		/**
		*显示关于风格的html信息
		*/
		show : function(relatedEl){
			if(!rendered){
				this.render(relatedEl);
			}

			var nPageStyleId = wcm.special.design.PageStyle.get();
			//load the pagestyle
			BasicDataHelper.call('wcm61_pagestyle', 'sQuery', {pagesize : 6, orderby : 'CrTime desc', selectedids : nPageStyleId}, true, function(transport, json){
				Element.update(container, transport.responseText);
				var el = $('cbx_'+nPageStyleId);
				if(el) el.checked = true;
			});
			Element.show(container);
			Element.show(Element.next($(container)));
		},
		/**
		*将页面风格注册到顶部工具栏中
		*/
		register : function(){
			wcm.special.design.ToolBar.addItems({
				oprKey : 'setStyle',
				desc : '风格',
				cls : 'setStyle',
				order : 3,
				over : function(dom){
					wcm.special.toolbar.PageStyle.show(dom);
				},
				out : function(dom){
					Element.hide(container);
					Element.hide(Element.next($(container)));
				},
				cmd : function(){
					Element.toggle(container);
					Element.toggle(Element.next($(container)));
				}
			});		
		}
	}
})();


/*
*将页面的风格注册到顶部工具栏中
*/
wcm.special.toolbar.PageStyle.register();