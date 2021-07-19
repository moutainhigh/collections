
Ext.ns('wcm.special.toolbar');


/**
*广告选件相关处理
*/
(function(){
	//广告选件在设计页面引入的脚本id前缀，以及广告选件div元素的id标识
	var advertScriptId = "advert-script";

	//广告选件div元素的属性标识
	var advertAttrName = 'advert-ids';

	wcm.special.design.Advertisement = {
		/**
		*获取广告选件的当前配置
		*/
		getConfig : function(){
			return window.trsad_config || {};
		},
		/**
		*判断广告选件是否开启了
		*/
		enabled : function(){
			return this.getConfig()['enable'] == true;
		},
		/**
		*获取可视化设计页面中已经使用的广告
		*/
		get : function(){
			var doc = PageController.getMainWin().document;
			var dom = doc.getElementById(advertScriptId);
			if(!dom) return;
			return dom.getAttribute(advertAttrName) || 0;
		},
		/**
		*设置可视化设计页面中将使用的广告
		*/
		set : function(aIds, aScriptUrl){
			var doc = PageController.getMainWin().document;
			var dom = doc.getElementById(advertScriptId);
			if(!dom){
				//创建控制元素div
				dom = doc.createElement("div");
				dom.id = advertScriptId;
				Element.first(doc.body).appendChild(dom);
			}
			//设置当前正要设置的广告id
			dom.setAttribute(advertAttrName, aIds.join(","));

			return;

			var doc = PageController.getMainWin().document;
			var dom = doc.getElementById(advertScriptId);
			if(dom){
				//移除已经不再使用的广告js
				var oldIds = dom.getAttribute(advertAttrName) || "";
				var aOldIds = oldIds.split(",");
				for(var index = 0; index < aOldIds.length; index++){
					var id = advertScriptId + "-" + aOldIds[index];
					var script = doc.getElementById(id);
					if(!script) continue;
					if(!aIds.include(aOldIds[index])){
						Element.remove(script);
					}
				}				
			}else{
				//创建控制元素div
				dom = doc.createElement("div");
				dom.id = advertScriptId;
				Element.first(doc.body).appendChild(dom);
			}
			//设置当前正要设置的广告id
			dom.setAttribute(advertAttrName, aIds.join(","));

			//加载指定的广告js
			var head = doc.getElementsByTagName("head")[0];
			for(var index = 0; index < aIds.length; index++){
				var id = advertScriptId + "-" + aIds[index];
				if(doc.getElementById(id)) continue;
				var script = doc.createElement('script');
				script.id = id;
				script.type = 'text/javascript';
				script.setAttribute("defer", "true");
				script.setAttribute("src", aScriptUrl[index]);
				head.appendChild(script);
			}				
		}
	};

	var rendered;
	var container = 'advert-menu';
	var template = [
		'<div id="', container, '" class="advert-menu" ignore="1">',
			'<a href="#" cmd="set">维护页面广告</a>',
			'<a href="#" id="advert-status" cmd="enableEffect">开启广告效果</a>',
		'</div>'
	].join("");

	/**
	*广告选件顶部工具栏的相关处理
	*/
	wcm.special.toolbar.Advertisement = {
		/**
		*开启广告选件的运行效果
		*/
		enableEffect : function(){
			Element.update('advert-status', '关闭广告效果');
			$('advert-status').setAttribute('cmd', 'disableEffect');
		},
		/**
		*关闭广告选件的运行效果
		*/
		disableEffect : function(){
			Element.update('advert-status', '开启广告效果');
			$('advert-status').setAttribute('cmd', 'enableEffect');
		},
		/**
		*设置可视化设计页面中将使用的广告
		*/
		set : function(){
			var ids = wcm.special.design.Advertisement.get();
			this.select({
				selectedIds : ids,
				callback : function(ids, jsFiles){
					//这里需要对ids为""时，做处理
					wcm.special.design.Advertisement.set(ids.split(","), jsFiles.split(","));
				}
			});
		},
		/**
		*选择系统中的广告
		*json:{selectedIds: '4,3', callback : fn}
		*/
		select : function(json){			
			var strsAdCon = wcm.special.design.Advertisement.getConfig()['root_path'];
			if(strsAdCon==null)return;
			var nStrLen = strsAdCon.length;
			if(strsAdCon.charAt(nStrLen-1)!='/'){
				strsAdCon = strsAdCon + '/';
			}

			var sWcmurl = "http://"+window.location.host+"/wcm/app/template/adintrs_intoTemp.jsp";

			// 打开广告位选择页面
			wcm.CrashBoarder.get('adintrs_slt').show({
				width:'600px',
				height:'400px',
				title : '选择广告',
				src : 'document/dialog_window.html',
				params : {
					URL : 7,//要跳转到广告位选择页面的URL序号值
					WCMURL : sWcmurl, //广告位的处理页面
					AdLocationIds : json.selectedIds//页面中已经添加的广告位的Id
				},
				callback : function(params){
					// 广告位脚本的js(使用逗号分隔开)
					var sResult = params.result;
					// 广告位id(多个Id使用逗号分隔开)
					var sAdLocationIds = params.adLocationIds;

					//广告对应的js信息
					//alert("广告脚本的js序列："+sResult);

					//将返回的广告位地址中的变量做替换操作
					sResult = sResult.replace(/\$\{admanage_root_path\}/g, strsAdCon + "adintrs/");
					if(json['callback']){
						json['callback'](sAdLocationIds, sResult);
					}
				}
			});
		},
		/**
		*渲染关于广告的html信息
		*/
		render : function(relatedEl){
			rendered = true;
			new Insertion.Bottom(document.body, template);
			Position.clone(relatedEl, container, {setWidth:false, setHeight:false, offsetTop:relatedEl.offsetHeight-1});
			Element.out(container, function(event){
				Element.hide(container);
				Element.removeClassName(relatedEl, 'toolbaritemhover');
			});
			Event.observe(container, 'click', function(event){
				Event.stop(event);
				var el = Event.element(event);
				el = Element.find(el, 'cmd');
				if(!el) return;
				var cmd = el.getAttribute('cmd');
				if(!wcm.special.toolbar.Advertisement[cmd]) return;
				wcm.special.toolbar.Advertisement[cmd]();
				Element.hide(container);
				Element.removeClassName(relatedEl, 'toolbaritemhover');
			});
		},
		/**
		*显示关于风格的html信息
		*/
		show : function(relatedEl){
			if(!rendered){
				this.render(relatedEl);
			}
			Element.show(container);
		},
		/*
		*将广告选件注册到顶部工具栏中
		*/
		register : function(){
			wcm.special.design.ToolBar.addItems({
				oprKey : 'advertisement',
				desc : '广告',
				cls : 'advertisement',
				order : 7,
				disabled : function(){
					return !wcm.special.design.Advertisement.enabled();
				},
				over : function(dom){
					wcm.special.toolbar.Advertisement.show(dom);
				},
				out : function(dom){
					Element.hide(container);
				},
				cmd : function(){
					Element.toggle(container);
				}
			});
		}
	}
})();


/*
*将广告选件注册到顶部工具栏中
*/
wcm.special.toolbar.Advertisement.register();
