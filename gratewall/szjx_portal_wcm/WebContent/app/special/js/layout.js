Ext.ns("wcm.special.layout");
wcm.special.layout=function(config){
	wcm.special.layout.superclass.constructor.apply(this, arguments);
	Ext.apply(this, config);
	this.init();
};
Ext.extend(wcm.special.layout, wcm.util.Observable, {
	init : function(){
		this.addEvents(
			/**
			*@event beforeload
			*before send request for load data.
			*/
			'beforeload',
			/**
			*@event beforerender
			*after get the data from server and before render the html to page.
			*/
			'beforerender',
			/**
			*@event render
			*render the html to page.
			*/
			'render',
			/**
			*@event afterrender
			*after render the html to page.
			*/
			'afterrender',
			/**
			*@event beforesave
			*before post the data to server.
			*/
			'beforesave',
			/**
			*@event save
			*after save the channel data to server, and then save other datas.
			*/
			'save',
			/**
			*@event aftersave
			*after while all datas saved.
			*/
			'aftersave',
			
			'afteredit',

			'afterdelete',
			'beforemark',
			'aftermark',
			'beforeadd',
			'afteradd',
			'beforeremove',
			'afterremove',
			'beforemove',
			'aftermove'
		);
	},
	/**
	*判断指定的元素是否为布局对象
	*/
	isLayout : function(id){
		if(!$(id)) return false;
		return Element.hasClassName(id, this.LAYOUT_CLASS_NAME);
	},
	/**
	*判断指定的元素是否为布局列对象
	*/
	isColumn : function(id){
		if(!$(id)) return false;
		return Element.hasClassName(id, this.COLUMN_CLASS_NAME);
	},
	positionMapping : {
		"Top" : 'first',
		"Bottom" : 'last',
		"Before" : 'previous',
		"After" : 'next'
	},
	/**
	*在指定的元素[容器、布局或布局列]下添加新的布局
	*@id	添加布局时，需要用到的关联元素id，一般情况下，是在id后面添加新的布局
	*@position	可选值为：Top,Bottom,Before,After
	*/
	addLayout : function(id, position){
		position = position || "After";
		if(!$(id)) return;
		if(this.fireEvent('beforeadd', id, position) === false) return;
		var caller = this;
		wcm.CrashBoarder.get('layout-select').show({
			title : '布局选择页面',
			src : 'layout/layout_select.jsp',
			maskable:true,
			width:'570px',
			height:'320px',
			callback : function(params){
				var dom = $(id);
				new Insertion[position](dom, params.content);
				var newLayout = Element[caller.positionMapping[position]](dom);
				// 设置当前布局的ID
				caller.setLayoutId(newLayout);
				caller.fireEvent('afteradd', newLayout.getAttribute("id"));
			}
		});
	},
	/**
	*删除指定的布局
	*@layoutId	当前将要删除的布局id
	*/
	removeLayout : function(layoutId){
		var layout = $(layoutId);
		if(!layout) return;
		if(this.fireEvent('beforeremove', layoutId) === false) return;
		Element.remove(layout);
		this.fireEvent('afterremove', layoutId);
	},
	removeColumn : function(columnId){
		var column = $(columnId);
		if(!column) return;
		var layout = this.getLayout(column);
		var parent = column.parentNode;
		// 如果是最后一列，则直接删除该布局
		if(Element.first(parent) == Element.last(parent)){
			this.removeLayout(layout);
		}else{//不是最后一列
			var _nRatioType=layout.getAttribute(this.ATTR_RATIOTYPE);
			var _sRatios = layout.getAttribute(this.ATTR_RATIO).split(":");
			var info=this._getInfoOfCol(column);
			_sRatios.splice(info.colNum - 1, 1);
			// 发送服务
			var option={
				RatioType:_nRatioType,
				Ratio: _sRatios.join(this.RATIO_SEPERATE)
			}
			var caller = this;
			// 发送服务
			if(this.fireEvent('beforeremove', columnId) === false) return;
			BasicDataHelper.Call('wcm61_layout', 'getLayoutHtml',option, false, function(oTransport, oJson){
				var num=caller._getInfoOfCol(column).colNum;
				caller.updateLayout(layout, {content:oTransport.responseText,deleteCol:num});				
				caller.fireEvent('afterremove', columnId);
			});
		}
	},
	getTRSArgs : function(dom){
		var attributes = dom.attributes;
		var args = {};
		for(var index = 0; index < attributes.length; index++){
			var node = attributes[index];
			if(node.specified){
				var nodeName = node.nodeName;
				if(nodeName.startsWith('trs-')){
					args[nodeName.substring('trs-'.length)] = node.nodeValue;
				}
			}
		}
		return args;
	},
	setTRSArgs : function(dom, args){
		for(name in args){
			if(!Ext.isString(args[name])) continue;
			dom.setAttribute('trs-'+name, args[name]);
			try{
				dom.style[name.camelize0()] = args[name];
			}catch(error){
				//alert(error.message);
			}
		}
	},
	/**
	*修改指定的布局
	*layoutId	当前需要修改的布局id
	*/
	editLayout:function(layoutId){
		// 获取选中的布局
		var layout = $(layoutId);
		if(!layout)return;
		if(this.fireEvent('beforeedit', layoutId) === false) return;
		var caller = this;
		// 获取布局的比例信息
		var ratio = layout.getAttribute(this.ATTR_RATIO);
		var ratioType = layout.getAttribute(this.ATTR_RATIOTYPE);
		wcm.CrashBoarder.get('layout-modify').show({
			title : '布局修改页面',
			src : 'layout/layout_modify.jsp',
			params:{
				Ratio:ratio,
				RatioType:ratioType,
				pageWidth : layout.offsetWidth
			},
			maskable:true,
			width:'500px',
			height:'200px',
			callback : function(params){
				caller.updateLayout(layout, params);
				caller.fireEvent('afteredit', layoutId);
			}
		});
	},
	/**
	*修改指定的布局
	*layoutId	当前需要修改的布局id
	*/
	editLayoutStyle:function(layoutId){
		// 获取选中的布局
		var layout = $(layoutId);
		if(!layout)return;
		if(this.fireEvent('beforeedit', layoutId) === false) return;
		var caller = this;
		var params = this.getTRSArgs(layout);
		// 获取布局的比例信息
		wcm.CrashBoarder.get('layout-edit-style').show({
			title : '修改布局高级属性',
			src : 'style_properties_addedit.jsp',
			params : params,
			maskable:true,
			width:'820px',
			height:'390px',
			callback : function(params){
				if(!params.changed) return;
				caller.setTRSArgs(layout, params.data);
				caller.fireEvent('afteredit', layoutId);
			}
		});
	},
	/**
	*给某一个布局对象设置id，将同时设置子对象的id
	*/
	setLayoutId : function(layout, id){
		//设置布局的id
		var id = id || "part_"+(++this.ID);
		layout.setAttribute('id', id);

		//设置列的id
		var columns = this.getColumns(layout);
		for (var i = 0; i < columns.length; i++){
			var col = columns[i];
			col.id = id + "_" + new Date().getTime()+(++this.ID);
		}
	},
	/**
	*标识指定的元素对象
	*id	当前需要标识的元素id
	*scrolltoview 是否让当前对象呈现在可视化区域
	*/
	mark : function(id, scrolltoview){
		var dom = $(id);
		if(!dom || Element.hasClassName(dom, this.MARKED_CLASS_NAME)) return;
		this.fireEvent('beforemark', id);
		Element.addClassName(dom, this.MARKED_CLASS_NAME);
		if(scrolltoview && PageController) //dom.scrollIntoView(true);
			PageController.getTopWin().scrollInToView(dom,window);
		this.MARKED_ID = id;
		this.fireEvent('aftermark', id);
	},
	/**
	*删除当前已经标识的对象
	*/
	unmark : function(){
		if(this.MARKED_ID && $(this.MARKED_ID)){
			Element.removeClassName(this.MARKED_ID, this.MARKED_CLASS_NAME);
			this.MARKED_ID = null;
		}
	},
	/**
	*获取布局列的容器，用于存储资源对象
	*/
	getContainerOfColumn : function(colElem){
		return Element.first($(colElem));
	},
	/**
	* 更新布局，在删除布局中的列和修改布局时调用
	*/
	updateLayout:function(layout, params){
		var newLayout=wcm.util.getElemFromString(params.content);
		var orignLayout=layout;
		var sNewRatio=newLayout.getAttribute(this.ATTR_RATIO);
		var sOrigRatio = orignLayout.getAttribute(this.ATTR_RATIO);
		var nOrignColumn = sOrigRatio.split(this.RATIO_SEPERATE).length;
		// 替换列中的内容
		for(var i=1;i<=nOrignColumn;i++){
			var num=i;
			// 如果删除的列小于当前列，则将后一列的内容添加到当前列的布局中
			// 原来布局的当前列与其内容均被删除
			if(params.deleteCol<=i){
				num++;
			}
			var sContent=this.getContentOfColumn(num,orignLayout);
			this.addContentToColumn(i,newLayout,sContent);
		}
		// 原来的ID设置回去
		this.setLayoutId(newLayout, orignLayout.id);
		this.setTRSArgs(newLayout, this.getTRSArgs(orignLayout));
		// 将新元素添加到HTML内容中
		orignLayout.parentNode.replaceChild(newLayout, orignLayout);
	},
	/**
	* 从当前元素中获取到它所在的布局
	*/
	getLayout : function(id){
		var dom = $(id);
		return Element.find(dom, null, this.LAYOUT_CLASS_NAME);
	},
	/*
	* 获取某一列的内容，这里的列是指页面结构中的列
	*/
	getContentOfColumn:function(_column,_elem){
		var el=this._getElemOfColumn(_column,_elem).elem;
		return el?el.innerHTML:"";
	},
	/**
	* 在布局中的第几列添加内容
	*/
	addContentToColumn:function(_column,_elem,_content){
		var sRatio = this.getLayout(_elem).getAttribute(this.ATTR_RATIO);
		var colNumOfAll = sRatio.split(this.RATIO_SEPERATE).length;
		// 获取_elem的第_column列对应的元素
		var el=this._getElemOfColumn(_column,_elem).elem;
		if(el && _content && _content.length>0)
			el.innerHTML=_content;
	},
	/**
	* 获取HTML列元素的信息，例如该列在页面结构中属于第几列，该列的比例值等
	*/
	_getInfoOfCol:function (_elem){
		// 为空或者不是列
		_elem = $(_elem);
		if(!_elem || !Element.hasClassName(_elem,this.COLUMN_CLASS_NAME))
			return null;
		// 获取列所在的布局
		var layout=this.getLayout(_elem);
		var sRatio = layout.getAttribute(this.ATTR_RATIO);
		var colNumOfChar = this._getColNumOfChar(sRatio);
		var columns = this.getColumns(layout);
		// 获取当前在HTML结构中是第几列
		var nCol = new RegExp(/.*c_(\d)/).exec(_elem.className)[1];
		// 如果没有自适应列或者在自适应列前面
		var oInfo={};
		if(colNumOfChar<0 || nCol<colNumOfChar){
			oInfo={colNum:nCol,colValue:sRatio.split(this.RATIO_SEPERATE)[nCol-1]};
		}else{// 在自适应列之后
			var nNum = sRatio.split(this.RATIO_SEPERATE).length-nCol+colNumOfChar;
			oInfo={colNum:nNum,colValue:sRatio.split(this.RATIO_SEPERATE)[nNum-1]};
		}
		return oInfo;
	},
	/**
	*
	*获取页面结构中第几列的信息，例如第一列对应的HTML元素，比例值等
	*/
	_getElemOfColumn:function (_nCol,_layout){
		if(!_layout)return {};
		var sRatio = _layout.getAttribute(this.ATTR_RATIO);
		var sRatios = sRatio.split(this.RATIO_SEPERATE);
		if(sRatios.length<_nCol)return {};
		var columns = this.getColumns(_layout);
		var colNumOfChar = this._getColNumOfChar(sRatio);
		var el=null;
		// 如果没有自适应列，或者自适应列在当前列的后面
		if(colNumOfChar<0 || _nCol<colNumOfChar){
			el=columns[_nCol-1];
		}else{// 当前列在自适应列的后面
			el=columns[sRatios.length+colNumOfChar-_nCol-1];
		}
		return {elem:el,ratioValue:sRatios[_nCol-1]}
	},
	/*
	* 获取自适应列所在的列数
	*/
	_getColNumOfChar:function (_sRatio){
		var temp = _sRatio.substring(0,_sRatio.indexOf(this.ADAPTIVE_CHAR)+1);
		return temp.length>0?temp.split(this.RATIO_SEPERATE).length:-1;
	},
	/**
	* 获取布局的列
	* @ 返回一个数组，这个数据中的元素全部都是该布局的列元素
	*/
	getColumns:function(layout){
		if(!layout)return null;
		var columns=[];
		var elem = Element.first(layout);
		// 先找到第一个为列的DIV
		while(!this.isColumn(elem)){
			elem = Element.first(elem);
		}
		// 遍历兄弟节点找到所有的列元素
		while(elem){
			if(this.isColumn(elem))
				columns.push(elem);
			elem=Element.next(elem);
		}
		return columns;
	},
	/**
	*	树的粘贴操作
	*	@需要传入源布局ID和目标布局ID，粘贴位置	
	**/
	move : function(srcElem,tagElem,position){
		position = position || "After";
		srcElem = $(srcElem);
		tagElem = $(tagElem);
		if(this.fireEvent('beforemove', srcElem,tagElem) === false) return;
		new Insertion[position](tagElem, srcElem.outerHTML);
		var parentElem = srcElem.parentNode;
		Element.remove(srcElem);
		// 只能剪切一次
		PageController.getTopWin().PageController.clipboard = {};
		this.fireEvent('aftermove', parentElem,tagElem);
	}
});

/*
* 布局对象
*/
var Layout = new wcm.special.layout({
	/*
	* 保存当前标识的ID
	*/
	MARKED_ID :null,
	/*
	* 标识相关的CLSS名
	*/
	MARKED_CLASS_NAME :"mark",
	/*
	* 容器的CLSS名
	*/
	WRAP_CLASS_NAME:"trs_wrap",
	/*
	* 布局的CLSS名
	*/
	LAYOUT_CLASS_NAME:"trs_layout",
	/*
	* 布局中列的CLSS名
	*/
	COLUMN_CLASS_NAME:"trs_column",
	/*
	* 布局中作为比例值的属性名
	*/
	ATTR_RATIO:"_ratio",
	/*
	* 布局中作为比例类型的属性名
	*/
	ATTR_RATIOTYPE:"_ratiotype",
	/*
	* 自适应列的值
	*/
	ADAPTIVE_CHAR:"*",
	/*
	* 比例分隔符
	*/
	RATIO_SEPERATE:":",
	/*
	* 布局中分隔符的class名
	*/
	C_SEP:"c_sep",
	/*
	* 布局的ID相关值
	*/
	ID:0
});

/*
*在插入布局之前获取最大的布局ID
*/
Layout.on("beforeadd",function(){
	if(this.ID!=0)
		return;
	var oLayouts = document.getElementsByClassName("trs_layout");
	for(var i=0;i<oLayouts.length;i++){
		var tempId=parseInt(oLayouts[i].id.match(/\d+/)[0]);
		if(tempId>this.ID)
			this.ID=tempId;
	}
});


Ext.ns("wcm.special.design.tree");
wcm.special.design.tree={
	sJsonTemplate : [
						'{',
							'id:"{0}",',
							'type:"{1}",',
							'children:[{2}],',
							'name:"{3}"',
						'}'].join(""),
	/*
	* 初始化树
	*/
	init : function(){
		WRAPS=getTrsWraps();
		if(WRAPS.length==0)
			return null;
		var childrenJson =[];
		for(var i=0;i<WRAPS.length;i++){
			childrenJson.push(this.formWrap(WRAPS[i]));
		}
		return "["+childrenJson.join(",")+"]";
	},
	/*
	* 生成容器JSON
	*/
	formWrap : function(_wrap){
		_wrap.id = _wrap.id || ("wrap_"+(new Date().getTime()+(++Layout.ID)));
		//var arrLayouts=_wrap.childNodes;
		var arrLayouts = document.getElementsByClassName("trs_layout", _wrap);
		var childrenJson =[];
		for(var i=0;i<arrLayouts.length;i++){
			// TODO 如果不是布局,间隔DIV被过滤，如果需要在树节点中显示该DIV，则去掉过滤逻辑
			if(!Element.hasClassName(arrLayouts[i],Layout.LAYOUT_CLASS_NAME)){
				continue;
			}
			childrenJson.push(this.formLayout(arrLayouts[i]));
		}
		return String.format(this.sJsonTemplate, _wrap.id,"wrap",childrenJson.join(","));
	},
	/*
	* 生成布局JSON
	*/
	formLayout : function(_layout){
		_layout.id = _layout.id || ("part_"+(++Layout.ID));
		var ratios=_layout.getAttribute("_ratio").split(":");
		var childrenJson =[];
		for(var i=0;i<ratios.length;i++){
			var tempElem = Layout._getElemOfColumn(i+1,_layout).elem;
			childrenJson.push(this.formCol(_layout.id,tempElem));
		}
		return String.format(this.sJsonTemplate, _layout.id,"layout",childrenJson.join(","));
	},
	/*
	* 生成列JSON
	*/
	formCol : function(layoutid,_col){
		// TODO 列中可以存放布局用
		_col.id =_col.id ||(layoutid + "_" + new Date().getTime()+(++Layout.ID));
		var children =getWidgetsOfCol(_col);
		var childrenJson =[];
		for(var i=0; children && i<children.length;i++){
			childrenJson.push(this.formWidget(children[i]));
		}
		return String.format(this.sJsonTemplate, _col.id,"column",childrenJson.join(","));
	},
	/*
	* 生成资源JSON
	*/
	formWidget : function(_rec){
		return String.format(this.sJsonTemplate, _rec.id,"widget","",_rec.getAttribute('wname'));
	}
}

Ext.ns('wcm.util');
/*
* 从HTML代码中获取对象
*/
wcm.util.getElemFromString=function(_sHtml){
	var newEl = document.createElement("div");
	newEl.innerHTML=_sHtml;
	return Element.first(newEl);
}

// 获取列的一级子节点 资源
//TODO 若布局嵌套需要修改
function getWidgetsOfCol(_col){
	return document.getElementsByClassName("trs-widget", _col);
}

// 获取存放布局的容器
function getTrsWraps(){
	return document.getElementsByClassName(Layout.WRAP_CLASS_NAME);
}

wcm.special.layout.LayoutController = {
	/**
	*页面加载完成时，需要触发的初始化处理
	*/
	init : function(){
		WRAPS = getTrsWraps();
	},	
	/**
	*页面卸载时，需要触发的销毁处理
	*/
	destroy : function(){
	}	
};
wcm.special.layout.LayoutUI = Layout;


	/*
	*mark layout when move mouse,and show the operator panel
	*there many opers in this operator panel
	*/
wcm.special.layout.identity=new wcm.util.Draggable();
Ext.apply(wcm.special.layout.identity,{
	init : function(){
		Ext.EventManager.on(document, 'mousedown', this.handleMouseDown, this);
		Ext.EventManager.on(document, 'mousemove', this.handleMouseMove_layout, this);
	},
	handleMouseMove_layout : function(e){
		if(this.dragging || wcm.util.Draggable.dragging)return;
		//在拖动资源块时，不进行标识
		if(wcm.util.special.Dragger.dragging)return;
		this.dragEl = this.findDragEl(e);
		wcm.special.layout.OperUI.identify(this.dragEl);
	},
	findDragEl : function(event){
		var dom = Event.element(event.browserEvent);
		var layout = Element.find(dom, null, 'trs_layout');
		var widgetIdentifyEl=Element.find(dom, null, 'widget-instance-identify');
		var layoutIdentifyEl=Element.find(dom, null, wcm.special.layout.OperUI.id);
		if(!layout && (widgetIdentifyEl || layoutIdentifyEl)){
			return this.dragEl;
		}
		return layout;
	}
});
Event.observe(window, 'load', function(){
	wcm.special.layout.identity.init();
	wcm.special.layout.dragger.init();
	wcm.special.layout.OperUI.init();
});


wcm.special.layout.OperUI = function(){
	var el;
	var simpleMenu;
	var opers = [];
	/*
	*资源块
	*/
	var sTemplate = [
		'<div id="{0}" class="{0}" style="display:none;" ignore4Instance="1">',
			'<div class="header-desc" id="{0}-desc" title="按住这里进行布局拖动"></div>',
			'<div class="header-oper-bg">',
				'<div class="header-oper" title="点击这里，进行布局的操作">',
					'<div class="oper-desc">布局操作</div>',
				'</div>',
			'</div>',
		'</div>',
		'<iframe frameborder="0" class="layout-oper-shield" src="'+Ext.blankUrl+'"></iframe>'
	].join("");
	var sLayoutIdentifyId = 'layout-identify';
	//兼容多浏览器动态获取css中定义的属性
	var getCurrentStyle = function (obj, cssproperty,csspropertyNS){
		if(obj.style[cssproperty]){
			return obj.style[cssproperty];
		}
		if(obj.currentStyle) {
			return obj.currentStyle[cssproperty];
		}else if (document.defaultView.getComputedStyle(obj, null)) {
			var currentStyle = document.defaultView.getComputedStyle(obj, null);
			var value = currentStyle.getPropertyValue(csspropertyNS);
			if(!value){
				value = currentStyle[cssproperty];
			}
			return value;
		}else if (window.getComputedStyle) {
			var currentStyle = window.getComputedStyle(obj, "");
			return currentStyle.getPropertyValue(csspropertyNS);
		}
	}
	// init the element
	var getPosition = function(event){
		var dom = Event.element(event);
		var doc = dom.ownerDocument;
		var win = doc.parentWindow || doc.defaultView;
		var frameElement = win.frameElement;
		var topOffset = Position.getPageInTop(frameElement);
		var pageOffsetX = event.clientX;
		var pageOffsetY = event.clientY;
		return [topOffset[0] + pageOffsetX, topOffset[1] + pageOffsetY];
	};
	return {
		id:sLayoutIdentifyId,
		init : function(){
			if($(sLayoutIdentifyId)) return;
			new Insertion.Bottom(document.body, String.format(sTemplate, sLayoutIdentifyId));
			//init the action for the header.
			Event.observe(sLayoutIdentifyId, 'click', function(event){
				var dom = Event.element(event);
				dom = wcm.util.ElementFinder.findElement(dom, null, 'header-oper');
				if(!dom) return;
				if(!simpleMenu) {
					simpleMenu = new com.trs.menu.SimpleMenu({sBaseCls : 'layout-oper-menu'});
				}
				var position = getPosition(event);
				var opersLessOne = [];
				//当展开的不是结构树时去除第五个
				for(var i=0;i<opers.length;i++){
					if(i<5){
						opersLessOne[i] = opers[i];
					}else if(i == 5){
						continue;
					}else{
						opersLessOne[i-1] = opers[i];
					}
				}
				//兼容浏览器获取iframe
				var doc;
				if (document.all){
					doc = window.parent.frames["TreeNav"].document;
				}else{
					doc = window.parent.document.getElementById("TreeNav").contentDocument;
				}
				var dom = doc.getElementById("accor-panel-body-page-structure");
				//判断结构树是否显示，若当前显示的为结构树则显示定位到结构树
				if(dom.innerHTML!="" && getCurrentStyle(dom,"display")!="none"){
					simpleMenu.show(opers, {el:el,x:position[0],y:position[1]});
				}else{
					simpleMenu.show(opersLessOne, {el:el,x:position[0],y:position[1]});
				}
			});
		},
		identify : function(dom){
			if(dom == el) return;
			this.unIdentify();
			if(!dom){
				el=null;
				return;
			}
			el=dom;
			var layoutIdentifyEl = $(sLayoutIdentifyId);
			//var offsetLeft = parseInt(dom.offsetWidth, 10);
			Position.clone(dom, layoutIdentifyEl, {offsetLeft:2, offsetTop:2, setWidth:false, setHeight:false});
			Element.show(layoutIdentifyEl);
			Position.clone(layoutIdentifyEl, Element.next($(layoutIdentifyEl)));
			Element.show(Element.next($(layoutIdentifyEl)));
			this.el=dom;
			Element.addClassName(dom,"layout_mouseover");
			//this.dealWithColumn(dom);
		},
		unIdentify : function(){
			if(!el)return;
			if($(sLayoutIdentifyId)) {
				Element.hide(sLayoutIdentifyId);
				Element.hide(Element.next($(sLayoutIdentifyId)));
			}
			Element.removeClassName(el,"layout_mouseover");
			//取消标识时需要重置
			el=null;
		},
		dealWithColumn : function(dom){
			var firstCol_Cl=""
			var aRatios = dom.getAttribute("_ratio").split(":");
			if(aRatios.length==1){
				Element.addClassName(Element.first(dom),"first_column");
				Element.addClassName(Element.last(dom),"last_column");
			}
			if(aRatios.indexOf("*")==0){
				Element.addClassName(Element.first(dom),"last_column");
			}else if(aRatios.indexOf("*")==-1){
				Element.addClassName(Element.first(dom),"first_column");
				Element.addClassName(Element.last(dom),"last_column");
			}else{
				Element.addClassName(Element.first(dom),"first_column");
				var colEl = Layout._getElemOfColumn(aRatios.indexOf("*")+1,dom).elem;
				Element.addClassName(colEl,"last_column");
			}
		},
		/**
		*给布局注册相应的操作
		*/
		addOpers : function(_opers){
			for (var i = 0; i < _opers.length; i++){
				opers.push(_opers[i]);
			}
		},
		destroy : function(){
			if(simpleMenu) simpleMenu.destroy();
			simpleMenu = null;
			el = null;
		}
	}
}();

/*
*布局操作注册
*/
wcm.special.layout.OperUI.addOpers([
	{
		oprKey : 'editLayout',
		desc : "修改布局",
		iconCls : 'edit',
		cmd : function(args){
			wcm.special.layout.LayoutUI.editLayout(args.el);
		}
	},
	{
		oprKey : 'editLayoutStyle',
		desc : "修改布局高级属性",
		iconCls : 'edit',
		cmd : function(args){
			wcm.special.layout.LayoutUI.editLayoutStyle(args.el);
		}
	},
	{
		oprKey : 'addLayoutInPrevious',
		desc : "在前面插入布局",
		iconCls : 'insertBefore',
		cmd : function(args){
			wcm.special.layout.LayoutUI.addLayout(args.el,'Before');
		}
	},
	{
		oprKey : 'addLayoutInNext',
		desc : "在后面插入布局",
		iconCls : 'insertAfter',
		cmd : function(args){
			wcm.special.layout.LayoutUI.addLayout(args.el,'After');
		}
	},
	{
		oprKey : 'markLayout',
		desc : "标记当前布局",
		iconCls : 'mark',
		cmd : function(args){
			wcm.special.layout.LayoutUI.mark(args.el,true);
		}
	},
	{
		oprKey : 'NavigateToTree',
		desc : "定位到结构树",
		iconCls : 'NavigateToTree',
		cmd : function(args){
			PageController.getTreeWin().focusNode(args.el.id);
		}
	},
	{
		oprKey : 'deleteLayout',
		desc : "删除布局",
		iconCls : 'delete',
		cmd : function(args){
			wcm.special.layout.LayoutUI.removeLayout(args.el);
		}
	}
]);



	/*
	*drag layout when mousedown and event target is operator panel
	*/
(function(){
	var destPositionHtml = "<div class='wcm-drag-destPosition' id='wcm-drag-destPosition'></div>";

	var dragElCls = 'wcm-drag-cls';
	var getTargets = function(dom){
		var layouts = document.getElementsByClassName('trs_layout', dom);
		return layouts;
	}
	var inElement = function(x, y, element){
		var page = Position.cumulativeOffset(element);
		if(y < page[1]) return 0;
		var height = parseInt(element.offsetHeight, 10);
		if(y > page[1] + height) return 0;
		if(y < page[1] + height / 2){//资源前面
			return -1;
		}
		return 1;//资源后面
	}
	wcm.special.layout.dragger=new wcm.util.Draggable();
	Ext.apply(wcm.special.layout.dragger,{
		findDragEl : function(event){
			var dom = Event.element(event.browserEvent);
			var layoutIdentifyEl=Element.find(dom, null, wcm.special.layout.OperUI.id);
			// 如果点击的是操作头部，则不进行拖拽
			var headOperEl=Element.find(dom, null,"header-oper");
			if(!layoutIdentifyEl || headOperEl)return;
			return wcm.special.layout.OperUI.el;
		},
		onDragStart :  function(x, y, e){
			Event.stop(e.browserEvent);
			var dragEl = this.dragEl;
			//在拖动过程中创建占位元素
			var destPosition = $('wcm-drag-destPosition');
			if(!destPosition){
				new Insertion.Before(dragEl, destPositionHtml);
				destPosition = $('wcm-drag-destPosition');
			}
			destPosition.style.height = dragEl.offsetHeight-8+"px";

			//使当前拖动的布局位置绝对化
			document.body.appendChild(dragEl);
			Element.addClassName(dragEl, dragElCls);
			Element.hide(dragEl);
			Position.clone(destPosition, dragEl);
			Element.show(dragEl);
			this.targets = getTargets(Element.first(document.body));

			var mask = $("special-mask");
			if(!mask){
				mask = document.createElement("DIV");
				mask.id = "special-mask";
				document.body.appendChild(mask);
			}
			mask.style.display = "block";
		},
		onDrag : function(x, y, e){
			var dragEl = this.dragEl;
			dragEl.style.left = (x - this.deltaX) + "px";
			dragEl.style.top = (y - this.deltaY) + "px";
			var operatorEl = $(wcm.special.layout.OperUI.id);
			operatorEl.style.left=(x - this.deltaX+2) + "px";
			operatorEl.style.top=(y - this.deltaY+2) + "px";
			Position.clone(operatorEl, Element.next($(operatorEl)));
			var targets = this.targets;
			for(var index = 0; index < targets.length; index++){
				var target = targets[index];
				var position = inElement(x, y, target);
				if(position){
					if(position == -1){
						var related = target;
						target.parentNode.insertBefore($('wcm-drag-destPosition'), related);
					}else if(position == 1){
						var related = Element.next(target);
						target.parentNode.insertBefore($('wcm-drag-destPosition'), related);
					}		
					break;
				}
			}
		},
		onDragEnd : function(x, y, e){
			var destPosition = $('wcm-drag-destPosition');
			destPosition.parentNode.insertBefore(this.dragEl, destPosition);
			Element.removeClassName(this.dragEl, dragElCls);
			this.dragEl.style.width = 'auto';
			this.dragEl.style.height = 'auto';
			this.dragEl.style.zoom = 1;
			this.dragEl = null;
			Element.remove(destPosition);
			$("special-mask").style.display = "none";
			//todo...此处应该想办法做事件调用，否则都要找到此处代码进行修改
			PageController.getStateHandler().saveState();
			PageController.refreshTree();
		}
	});
})();

/*
*布局实例操作注册,设置背景图片
*/
wcm.special.layout.OperUI.addOpers([{
	oprKey : 'setLayoutBackgroundImage',
	desc : "设置背景图片",
	iconCls : 'setLayoutBackgroundImage',
	cmd : function(args){
		var dom = args.el;
		wcm.CrashBoarder.get('setLayoutBackgroundImage-win').show({
			title : '设置布局背景图片',
			src : 'upload_background_image.html',
			width:'780px',
			height:'350px',
			maskable:true,
			params : {
				background : dom.style.background,
				backgroundRepeat : dom.style.backgroundRepeat,
				backgroundPosition : dom.style.backgroundPosition
				},
			callback : function(result){
				// 设置背景
				dom.style.background = result['background'];

				// 因为这里各个浏览器对样式backgroundImage的解析不同
				// 所以不通过backgroundImage的值判断是否有图片，而是通过上传页面进行参数传递
				var bHasImage = result['hasImage'];
				if(bHasImage){
					// 添加背景颜色透明的样式
					Element.addClassName(dom,"trs_layout_background");
				}else if(Element.hasClassName(dom,"trs_layout_background")){
					// 去掉背景颜色透明的样式
					Element.removeClassName(dom,"trs_layout_background");
				}
				wcm.special.layout.LayoutUI.fireEvent('afteredit', dom.getAttribute("Id"));
			}
		});
	}
}]);
