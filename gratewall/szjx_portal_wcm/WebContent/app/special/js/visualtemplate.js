Ext.ns('wcm.special.design');

/**
*用于管理可视化设计页面中的可视化html内容
*/
wcm.special.design.VisualTemplateHelper = function(){
	var el = document.createElement("div");
	return {
		/**
		*渲染可视化设计页面的内容
		*/
		renderVisualTemplate : function(sHtml){
			/*
			*确保页面一定存在wcm-mustExistId，这个节点将作为撤销重做的容器
			*如果不存在，则自动创建
			*/
			if(!/wcm-mustExistId/.test(sHtml)){
				sHtml = sHtml.replace(/(<body[^>]*>)((?:.|\n)*)(<\/body>)/i, "$1<div id='wcm-mustExistId'>$2</div>$3");
			}

			//使可视化设计页面在body底部引入相应的资源文件
			var sPageIncludeResource = $('pageincluderesource').value;
			sHtml = sHtml.replace(/(<\/body>)/i, sPageIncludeResource + "$1");

			//写入可视化内容
			var doc = this.getMainWin().document;
			doc.open();
			doc.write(sHtml || "");
			doc.close();
		},
		getVisualTemplate : function(){
			var sBodyInnerHtml = this.unparseWidgetInstance(Element.first(document.body).innerHTML);
			var sHtml = document.documentElement.outerHTML;
			var regExp = /(<body[^>]+>)(?:.|\n)*<\/body>(?:.|\n)*<\/html>/i;
			return sHtml.replace(regExp, "$1"+sBodyInnerHtml+"</body></html>");
		},
		/**
		*将资源实例替换成特殊字符，以便服务器端解析
		*/
		unparseWidgetInstance : function(sHtml){
			var widgetInstanceMgr = wcm.special.widget.InstanceMgr;
			el.innerHTML = sHtml;
			var doms = [];
			var all = el.getElementsByTagName("*");
			for (var i = 0; i < all.length; i++){
				if(widgetInstanceMgr.isInstance(all[i])){
					doms.push(all[i]);
				}
			}
			for (var i = 0; i < doms.length; i++){
				var aIdInfo = doms[i].id.split("-");
				doms[i].innerHTML = "@!@--"+aIdInfo[aIdInfo.length-1]+"--@!@";
			}
			return el.innerHTML;
		}
	};
}();