
var PageController = {
	/**
	*获取导航树页面
	*/
	getTopWin : function(){
		return top;
	},
	/**
	*获取设计页面
	*/
	getMainWin : function(){
		return this.getTopWin().$('page').contentWindow;
	},
	/**
	*执行来自跨页面的调用
	*/
	execute : function(sMethod){
		switch(sMethod){
			case 'xx' :
			case 'yy' :
				var caller = this.getStateHandler();
				caller[sMethod].apply(caller, Array.prototype.slice(arguments, 1));
				break;
		}
	}
};


var oAccordionVertical;

Event.observe(window, 'load', function(){
	oAccordionVertical = new wcm.AccordionVertical({
		container : 'accordion-vertical',
		items : [
			{
				key : 'page-structure',
				title : '页面结构',
				cmd : function(dom){
					if(dom.getAttribute('loaded')){
						return;
					}
					dom.setAttribute('loaded', "1");

					Element.update(dom, '<iframe id="PageStructureTreeNav" name="PageStructureTreeNav" style="width:100%;height:100%;" frameborder="no" src="tree.html"></iframe>');
				}
			},
			{
				key : 'datas',
				title : '可用栏目',
				cmd : function(dom){
					if(dom.getAttribute('loaded')){
						return;
					}
					dom.setAttribute('loaded', "1");

					var topWin = PageController.getTopWin();
					var src = 'channel/nav_tree_select.jsp?SiteTypes=0';
					if(topWin.nHostType == 103){
						src += "&CurrSiteId="+topWin.nHostId;
					}else if(topWin.nHostType == 101){//排除文档id
						src += "&CurrChannelId="+topWin.nHostId;
					}

					Element.update(dom, '<iframe style="width:100%;height:100%;" frameborder="no" src="'+src+'"></iframe>');
				}
			},
			{
				key : 'widgets',
				title : '可用资源',
				selected : true,
				cmd : function(dom){
					if(dom.getAttribute('loaded')){
						return;
					}
					dom.setAttribute('loaded', "1");

					var topWin = PageController.getTopWin();
					var src = 'widget/widget_mini_query.jsp?pagesize=-1&WidgetCategoryLevel=1217';
					if(topWin.nTemplateType == 2){
						src += "&WidgetType=2";
					}else{
						src += "&WidgetType=1";
					}


					Element.update(dom, '<iframe id="WidgetThumbQuery" name="WidgetThumbQuery" style="width:100%;height:100%;" frameborder="no" src="' + src + '"></iframe>');
				}
			}
		]
	});
});


function synchronizeHeight(){
	oAccordionVertical.redraw();
};
