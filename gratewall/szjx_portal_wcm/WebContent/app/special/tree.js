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
	refreshTree : function(json){
		var dom = $('tree');
		dom.innerHTML=ViewTree.init(json);
		if(!com.trs.tree.TreeNav.loaded){
			com.trs.tree.TreeNav.initTree();
		}else{
			com.trs.tree.TreeNav._initChildrenNodes(dom, dom, dom.getAttribute("SName"));
		}
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
	},
	/**
	*页面加载完成时，需要触发的初始化处理
	*/
	init : function(){
		var json = this.getTopWin().lastTreeJsonData;
		if(json){
			this.refreshTree(json);
		}

		Ext.ns('com.trs.menu.SimpleMenu');
		com.trs.menu.SimpleMenu = this.getTopWin().com.trs.menu.SimpleMenu;
	},	
	/**
	*页面卸载时，需要触发的销毁处理
	*/
	destroy : function(){
	}	
};
Event.observe(window, 'load', function(){
	PageController.init();
});
Event.observe(window, 'unload', function(){
	PageController.destroy();
});

/*
* 通过JSON生成操作树
*/
Ext.ns("ViewTree");
var ViewTree={
	/*
	*  树中各节点的类型
	*/
	TYPE_WRAP:"wrap",
	TYPE_LAYOUT:"layout",
	TYPE_COLUMN:"column",
	TYPE_WIDGET:"widget",
	
	/*
	*  树节点中的前缀
	*/
	PRE_CLASS_WRAP:"container",
	PRE_CLASS_LAYOUT:"layout",
	PRE_CLASS_COLUMN:"column",
	PRE_CLASS_WIDGET:"widget",
	/*
	* 模板
	*/
	sHtmlTemplate:["<div isRoot='{4}' type='{0}' id='{1}' classPre='{2}'>",
		"<a href='#'>",
			"{3}",
		"</a>",
		"</div>"].join(""),
	/*
	*  json初始化生成树结构
	*/
	init:function(json){
		if(!json || json=="")
			return "";
		var type=this.getNodeTypeByJson(json);
		if(type==this.TYPE_WRAP){
			return this.drawWrap(json);
		}else if(type==this.TYPE_LAYOUT){
			return this.drawLayout(json);
		}else if(type==this.TYPE_COLUMN){
			return this.drawColumn(json);
		}else if(type==this.TYPE_WIDGET){
			return this.drawWidget(json);
		}
		return "";
	},
	/*
	* 生成容器结构HTML代码
	*/
	drawWrap : function(_json){
		var sHtml="";
		if(typeof(_json)=='string')_json=eval("("+_json+")");
		if(!Array.isArray(_json))_json = [_json];
		for(var i=0;i<_json.length;i++){
			if(!_json[i]) continue;
			sHtml+=String.format(this.sHtmlTemplate,_json[i].type,_json[i].id,this.PRE_CLASS_WRAP,"布局容器"+"["+(i+1)+"]","true");
			if(_json[i].children && _json[i].children.length>0){
				sHtml+="<ul>";
				sHtml+=this.drawLayout(_json[i].children);
				sHtml+="</ul>";
			}
		}
		return sHtml;
	},
	/*
	* 生成布局结构HTML代码
	*/
	drawLayout :function(_json){
		var sHtml="";
		for(var i=0;i<_json.length;i++){
			if(!_json[i]) continue;
			sHtml+=String.format(this.sHtmlTemplate,_json[i].type,_json[i].id,this.PRE_CLASS_LAYOUT,"布局"+(i+1));
			if(_json[i].children && _json[i].children.length>0){
				sHtml+="<ul>";
				sHtml+=this.drawColumn(_json[i].children);
				sHtml+="</ul>";
			}
		}
		return sHtml;
	},
	/*
	* 生成列结构HTML代码
	*/
	drawColumn:function(_json){
		var sHtml="";
		for(var i =0;i<_json.length;i++){
			if(!_json[i]) continue;
			sHtml+=String.format(this.sHtmlTemplate,_json[i].type,_json[i].id,this.PRE_CLASS_COLUMN,String.format("第{0}列",(i+1)));
			if(_json[i].children && _json[i].children.length>0){
				sHtml+="<ul>";
				sHtml+=this.drawWidget(_json[i].children);
				sHtml+="</ul>";
			}
		}
		return sHtml;
	},
	/*
	* 生成资源结构HTML代码
	*/
	drawWidget:function(_json){
		var sHtml="";
		for(var i=0;i<_json.length;i++){
			if(!_json[i]) continue;
			if(_json[i].type==this.TYPE_WIDGET){
				var aId = _json[i].id.split("-");
				sHtml+=String.format(this.sHtmlTemplate,_json[i].type,_json[i].id,this.PRE_CLASS_WIDGET,_json[i].name+"["+aId[aId.length-1]+"]");
			}else if(_json[i].type==this.TYPE_LAYOUT)
				sHtml+=this.drawLayout([_json[i]]);
		}
		return sHtml;
	},
	/*
	*	获取更新节点的类型
	*/
	getNodeTypeByJson:function(json){
		if(typeof(json)=="string")json=eval("("+json+")");
		if(json[0])
			return json[0].type;
		else
			return json.type;
	}
}

/*
*  树的操作对象，例如实现树节剪切时变灰等
*/
ViewTree.treeMgr={
	cutCls : "cut_status",
	copyCls : "copy_status",
	cut : function(el){
		this.removeCutStatu();
		el = $(el);
		PageController.getTopWin().PageController.clipboard={
			elemId : el.id,
			oper : "cut",
			type : el.getAttribute("type")
		}
		Element.addClassName(el,this.cutCls);
	},
	copy : function(el){
		this.removeCutStatu();
		el = $(el);
		PageController.getTopWin().PageController.clipboard={
			elemId : el.id,
			oper : "copy",
			type : el.getAttribute("type")
		}
	},
	removeCutStatu : function(){
		var clipboard = PageController.getTopWin().PageController.clipboard;
		if(clipboard.elemId && clipboard.oper == "cut"){
			Element.removeClassName(clipboard.elemId,this.cutCls);
			PageController.getTopWin().PageController.clipboard = {};
		}
	},
	canPaste : function(_currType){
		var clipboard = PageController.getTopWin().PageController.clipboard;
		if(!clipboard.elemId){
			return false;
		}
		var bPaste = false;
		switch($(clipboard.elemId).getAttribute("type")){
			case ViewTree.TYPE_WIDGET:
				if(_currType==ViewTree.TYPE_WIDGET || _currType==ViewTree.TYPE_COLUMN)
					bPaste =true;
				break;
			case ViewTree.TYPE_LAYOUT:
				if(_currType ==ViewTree.TYPE_LAYOUT || _currType==ViewTree.TYPE_WRAP)
					bPaste = true;
				break;
		}
		// 刷新了树后，导致剪切目标状态发生变化
		//var cutElem = document.getElementsByClassName(this.cutCls);
		//if(!cutElem ||cutElem.length<=0)
		//	return false;
		return bPaste;
	},
	doAfterPaste : function(){
		if(PageController.getTopWin().PageController.clipboard.oper == "cut"){
			PageController.getTopWin().PageController.clipboard={};
		}
	}
}

var TreeNav=com.trs.tree.TreeNav;
TreeNav.initTreeBySelf = true;

/*
* 点击树节点时标记其对应的页面元素
*/
com.trs.tree.TreeNav.doActionOnClickA = function(_event,_elElementA){
	var div = _elElementA.parentNode;
	var id = div.id;
	var type = div.getAttribute("type");
	switch(type){
		case ViewTree.TYPE_LAYOUT :
		case ViewTree.TYPE_COLUMN :
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			ui.mark(id, true);
			break;
		case ViewTree.TYPE_WIDGET :
			var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			ui.mark(id, true);
			break;
	}
	Event.stop(_event);
}


/**
*导航树右键菜单处理
*/
wcm.special.tree.OperMgr.addOpers([
	{
		type : 'wrap',
		oprKey : 'addLayoutInFirstInWrap',
		desc : "在最前面插入布局",
		iconCls : 'add',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			ui.addLayout(args['nodeId'], 'Top');
		}
	},
	{
		type : 'wrap',
		oprKey : 'addLayoutInLastInWrap',
		desc : "在最后面插入布局",
		iconCls : 'add',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			ui.addLayout(args['nodeId'], 'Bottom');
		}
	},{
		type : 'wrap',
		isSeparator : true
	},{
		type : 'wrap',
		oprKey : 'pasteLayoutInFirstInWrap',
		desc : "粘贴布局到最前面",
		iconCls : 'paste',
		cls : function(){
			if(!ViewTree.treeMgr.canPaste("layout")){
				return 'display-none';
			}
		},
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			var clipboard = PageController.getTopWin().PageController.clipboard;
			ui.move(clipboard.elemId,args['nodeId'], 'Top');
		}
	},
	{
		type : 'wrap',
		oprKey : 'pasteLayoutInLastInWrap',
		desc : "粘贴布局到最后面",
		iconCls : 'paste',
		cls : function(){
			if(!ViewTree.treeMgr.canPaste("layout")){
				return 'display-none';
			}
		},
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			var clipboard = PageController.getTopWin().PageController.clipboard;
			ui.move(clipboard.elemId,args['nodeId'], 'Bottom');
		}
	},
	{
		type : 'layout',
		oprKey : 'editLayout',
		desc : "修改布局",
		iconCls : 'edit',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			ui.editLayout(args['nodeId']);
		}
	},
	{
		type : 'layout',
		oprKey : 'editLayoutStyle',
		desc : "修改布局高级属性",
		iconCls : 'edit',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			ui.editLayoutStyle(args['nodeId']);
		}
	},
	{
		type : 'layout',
		oprKey : 'addLayoutInPrevious',
		desc : "在前面插入布局",
		iconCls : 'insertBefore',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			ui.addLayout(args['nodeId'], 'Before');
		}
	},
	{
		type : 'layout',
		oprKey : 'addLayoutInNext',
		desc : "在后面插入布局",
		iconCls : 'insertAfter',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			ui.addLayout(args['nodeId'], 'After');
		}
	},
	{
		type : 'layout',
		oprKey : 'markLayout',
		desc : "标记当前布局",
		iconCls : 'mark',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			ui.mark(args['nodeId'], true);
		}
	},
	{
		type : 'layout',
		oprKey : 'deleteLayout',
		desc : "删除布局",
		iconCls : 'delete',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			ui.removeLayout(args['nodeId']);
		}
	},
	{
		type : 'layout',
		isSeparator : true
	},
	{
		type : 'layout',
		oprKey : 'pasteLayoutInPrevious',
		desc : "粘贴布局到前面",
		iconCls : 'pasteBefore',
		cls : function(){
			if(!ViewTree.treeMgr.canPaste("layout")){
				return 'display-none';
			}
		},
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			var clipboard = PageController.getTopWin().PageController.clipboard;
			ui.move(clipboard.elemId,args['nodeId'],'Before');
		}
	},
	{
		type : 'layout',
		oprKey : 'pasteLayoutInNext',
		desc : "粘贴布局到后面",
		iconCls : 'pasteAfter',
		cls : function(){
			if(!ViewTree.treeMgr.canPaste("layout")){
				return 'display-none';
			}
		},
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			var clipboard = PageController.getTopWin().PageController.clipboard;
			ui.move(clipboard.elemId,args['nodeId'],'After');
		}
	},
	{
		type : 'layout',
		oprKey : 'cutLayout',
		desc : "剪切布局",
		iconCls : 'cut',
		cmd : function(args){
			ViewTree.treeMgr.cut(args['nodeId']);
		}
	},
	{
		type : 'column',
		oprKey : 'addWidgetInFirstInColumn',
		desc : "在最前面插入资源",
		iconCls : 'insertBefore',
		cmd : function(args){
			var nodeId = args['nodeId'];
			var layoutUI = PageController.getMainWin().wcm.special.layout.LayoutUI;
			var dom = layoutUI.getContainerOfColumn(nodeId);
			var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			ui.add(dom, 'Top');
		}
	},
	{
		type : 'column',
		oprKey : 'addWidgetInLastInColumn',
		desc : "在最后面插入资源",
		iconCls : 'insertAfter',
		cmd : function(args){
			var nodeId = args['nodeId'];
			var layoutUI = PageController.getMainWin().wcm.special.layout.LayoutUI;
			var dom = layoutUI.getContainerOfColumn(nodeId);
			var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			ui.add(dom, 'Bottom');
		}
	},
	{
		type : 'column',
		oprKey : 'markColumn',
		desc : "标识当前列",
		iconCls : 'mark',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			ui.mark(args['nodeId'], true);
		}
	},
	{
		type : 'column',
		oprKey : 'deleteColumn',
		desc : "删除当前列",
		iconCls : 'delete',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.layout.LayoutUI;
			ui.removeColumn(args['nodeId']);
		}
	},
	{
		type : 'column',
		isSeparator : true
	},
	{
		type : 'column',
		oprKey : 'pasteWidgetInFirstInColumn',
		desc : "粘贴资源到最前面",
		iconCls : 'pasteInFirst',
		cls : function(){
			if(!ViewTree.treeMgr.canPaste("column")){
				return 'display-none';
			}
		},
		cmd : function(args){
			var nodeId = args['nodeId'];
			var layoutUI = PageController.getMainWin().wcm.special.layout.LayoutUI;
			var dom = layoutUI.getContainerOfColumn(nodeId);
			var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			var clipboard = PageController.getTopWin().PageController.clipboard;
			ui.move(clipboard.elemId,dom, 'Top',clipboard.oper);
			ViewTree.treeMgr.doAfterPaste();
		}
	},
	{
		type : 'column',
		oprKey : 'pasteWidgetInLastInColumn',
		desc : "粘贴资源到最后面",
		iconCls : 'pasteInLast',
		cls : function(){
			if(!ViewTree.treeMgr.canPaste("column")){
				return 'display-none';
			}
		},
		cmd : function(args){
			var nodeId = args['nodeId'];
			var layoutUI = PageController.getMainWin().wcm.special.layout.LayoutUI;
			var dom = layoutUI.getContainerOfColumn(nodeId);
			var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			var clipboard = PageController.getTopWin().PageController.clipboard;
			ui.move(clipboard.elemId,dom, 'Bottom',clipboard.oper);
			ViewTree.treeMgr.doAfterPaste();
		}
	},
	{
		type : 'widget',
		oprKey : 'editWidget',
		desc : "修改当前资源",
		iconCls : 'edit',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			ui.edit(args['nodeId']);	
		}
	},
	{
		type : 'widget',
		oprKey : 'addWidgetInPrevious',
		desc : "在前面插入资源",
		iconCls : 'insertBefore',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			ui.add(args['nodeId'], 'Before');	
		}
	},
	{
		type : 'widget',
		oprKey : 'addWidgetInNext',
		desc : "在后面插入资源",
		iconCls : 'insertAfter',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			ui.add(args['nodeId'], 'After');	
		}
	},
	{
		type : 'widget',
		oprKey : 'markWidget',
		desc : "标识当前资源",
		iconCls : 'mark',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			ui.mark(args['nodeId'], true);	
		}
	},
	{
		type : 'widget',
		oprKey : 'deleteWidget',
		desc : "删除当前资源",
		iconCls : 'delete',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			ui.remove(args['nodeId']);	
		}
	},
	{
		type : 'widget',
		oprKey : 'refreshWidget',
		desc : "刷新当前资源",
		iconCls : 'refresh',
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			ui.refresh(args['nodeId']);	
		}
	},{
		type : 'widget',
		isSeparator : true
	},{
		type : 'widget',
		oprKey : 'pasteWidgetInPrevious',
		desc : "粘贴资源到前面",
		iconCls : 'pasteBefore',
		cls : function(){
			if(!ViewTree.treeMgr.canPaste("widget")){
				return 'display-none';
			}
		},
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			var clipboard = PageController.getTopWin().PageController.clipboard;
			ui.move(clipboard.elemId,args['nodeId'],'Before',clipboard.oper);
			ViewTree.treeMgr.doAfterPaste();
		}
	},{
		type : 'widget',
		oprKey : 'pasteWidgetInNext',
		desc : "粘贴资源到后面",
		iconCls : 'pasteBefore',
		cls : function(){
			if(!ViewTree.treeMgr.canPaste("widget")){
				return 'display-none';
			}
		},
		cmd : function(args){
			var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			var clipboard = PageController.getTopWin().PageController.clipboard;
			ui.move(clipboard.elemId,args['nodeId'],'After',clipboard.oper);
			ViewTree.treeMgr.doAfterPaste();
		}
	},{
		type : 'widget',
		oprKey : 'copyWidget',
		desc : "复制资源",
		iconCls : 'copy',
		cmd : function(args){
			ViewTree.treeMgr.copy(args['nodeId']);
		}
	},{
		type : 'widget',
		oprKey : 'cutWidget',
		desc : "剪切资源",
		iconCls : 'cut',
		cmd : function(args){
			ViewTree.treeMgr.cut(args['nodeId']);
			//var ui = PageController.getMainWin().wcm.special.widget.InstanceMgr;
			//ui.cut(args['nodeId']);	
		}
	}
]);

/*
*	从资源设计页面定位到树节点
*/
function focusNode(divNode){
	// 0.判断树窗口是否已经展开
	var main = PageController.getTopWin().document.getElementById("main");
	if(Element.hasClassName(main,"hideNavTree"))
		Element.removeClassName(main,"hideNavTree");
	var el = $(divNode);
	// 1.展开到节点
	var oParentNade = el.parentNode;
	var nCountinueCountNotUL = 0;
	while( oParentNade != null && nCountinueCountNotUL < 5){
		if(oParentNade.tagName == 'UL'){
			nCountinueCountNotUL = 0;
			if(oParentNade.style.display == 'none'){
				var oNodeElement = oParentNade.previousSibling ;
				while(oNodeElement != null){
					if(oNodeElement.tagName && oNodeElement.tagName == "DIV")break;
					oNodeElement = oNodeElement.previousSibling ;
				}
				TreeNav._onClickFolder(oNodeElement);
			}				
		}else{
			nCountinueCountNotUL++;
		}
		oParentNade = oParentNade.parentNode;
	}
	// 2.将节点置为选中状态
	var oElementA = el.getElementsByTagName("A")[0];
	if(com.trs.tree.TreeNav.oPreSrcElementA != null){
		Element.removeClassName(com.trs.tree.TreeNav.oPreSrcElementA, "Selected");
	}
	Element.addClassName(oElementA, "Selected");
	com.trs.tree.TreeNav.oPreSrcElementA = oElementA;
	PageController.getTopWin().scrollInToView(oElementA,window);
}

