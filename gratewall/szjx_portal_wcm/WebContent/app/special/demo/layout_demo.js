Ext.ns("Design.Layout");
Design.Layout=function(config){
	Design.Layout.superclass.constructor.apply(this, arguments);
	Ext.apply(this, config);
	this.init();
};
Ext.extend(Design.Layout, wcm.util.Observable, {
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
			
			'afterdelete'
		);
	},
	add:function(){
		wcm.CrashBoarder.get('layout-select').show({
			title : '布局选择页面',
			src : '../layout/layout_select.jsp',
			maskable:true,
			width:'570px',
			height:'320px',
			callback : this.insert.bind(this)
		});
	},
	insert:function(_parames){
		if(this.fireEvent('beforerender') === false) return;
		// 获取选择的元素
		var newEl = __getElemFromString(_parames.content);
		// 设置当前布局的ID
		newEl.setAttribute("id","part_"+(this.ID++));
		var elem = this.getCurrLayout();
		if(!elem){
			// TODO 获取当前的容器
			var currWrap = WRAPS[0];
			this.append(newEl,currWrap);
		}else{
			_insertAfter(newEl,elem);
		}
		this.fireEvent('afterrender',[newEl]);
	},
	append : function (_newEl,_tarEl){
		var tarEl =$(_tarEl);
		var newEl = $(_newEl);
		if(!tarEl || !newEl)
			return ;
		if(Element.hasClassName(tarEl,this.WRAP_CLASS_NAME)){
			tarEl.appendChild(newEl);
		}
	// TODO 撤销重做堆栈更新
	},
	removeMark : function(){
		if(!this.MARKED_ELEM)
			return;
		if(Element.hasClassName(this.MARKED_ELEM,this.LAYOUT_MARKED)){
			Element.removeClassName(this.MARKED_ELEM,this.LAYOUT_MARKED);
			this.MARKED_ELEM=null;
		}
	},
	mark : function (elem){
		elem = $(elem)
		if(!elem)
			return;
		if(Element.hasClassName(elem,this.LAYOUT_MARKED))
			return;
		if(elem.tagName=="DIV" && (Element.hasClassName(elem,this.LAYOUT_CLASS_NAME) ||
			Element.hasClassName(elem.parentNode,this.LAYOUT_CLASS_NAME))){
			// 删除其它的布局标识
			this.removeMark();
			Element.addClassName(elem,this.LAYOUT_MARKED);
			this.MARKED_ELEM = elem;
		}
	},
	getCurrLayout : function(elem){
		elem = elem || this.MARKED_ELEM;
		if(!elem)
			return null;
		while(!Element.hasClassName(elem,this.LAYOUT_CLASS_NAME)){
			if(elem.tagName=="BODY")
				return null;
			elem=elem.parentNode;
		}
		return elem;
	},
	deleteElem : function(){
		var currLayout=this.getCurrLayout();
		if(!currLayout)
			return;
		// 如果是布局本身
		if(currLayout==this.MARKED_ELEM){
			Element.remove(this.MARKED_ELEM);
			this.fireEvent('afterdelete');
		}else if(this.MARKED_ELEM.className.match(/c_\d+/)){// 如果是列
			// 最后一列
			if(this.MARKED_ELEM.parentNode.childNodes.length==1){
				Element.remove(currLayout);
				this.fireEvent('afterdelete');
			}else{//不是最后一列
				var _nRatioType=currLayout.getAttribute(this.ATTR_RATIOTYPE);
				var _sRatios = currLayout.getAttribute(this.ATTR_RATIO).split(":");
				var info=this._getInfoOfCol(this.MARKED_ELEM);
				_sRatios.remove(info.colValue);
				// 发送服务
				var option={
					RatioType:_nRatioType,
					Ratio: _sRatios.join(":")
				}
				// 发送服务
				BasicDataHelper.Call('wcm61_layout', 'getLayoutHtml',option, false, function(oTransport, oJson){
					this.deleteCallBack(oTransport.responseText);
				}.bind(this));
			}
		}else if(this.MARKED_ELEM.className.match(/resource/)){//如果是资源块
			Element.remove(this.MARKED_ELEM);
			this.fireEvent('afterdelete');
		}
	},
	deleteCallBack:function(_content){
		var num=this._getInfoOfCol(this.MARKED_ELEM).colNum;
		this.modifyCallBack({content:_content,deleteCol:num});
	},
	modify:function (){
		// 获取选中的布局
		var layout = this.getCurrLayout();
		if(!layout){
			return;
		}
		// 获取布局的比例信息
		var ratio = layout.getAttribute(this.ATTR_RATIO);
		var ratioType = layout.getAttribute(this.ATTR_RATIOTYPE);
		wcm.CrashBoarder.get('layout-modify').show({
			title : '布局修改页面',
			src : '../layout/layout_modify.jsp',
			params:{
				Ratio:ratio,
				RatioType:ratioType
			},
			maskable:true,
			width:'500px',
			height:'200px',
			callback : this.modifyCallBack.bind(this)
		});
	},
	modifyCallBack:function(params){
		var newLayout=__getElemFromString(params.content);
		var orignLayout=this.getCurrLayout();
		var sNewRatio=newLayout.getAttribute(this.ATTR_RATIO);
		var sOrigRatio = orignLayout.getAttribute(this.ATTR_RATIO);
		var nOrignColumn = sOrigRatio.split(":").length;
		// 替换列中的内容
		for(var i=1;i<=nOrignColumn;i++){
			var num=i;
			if(params.deleteCol<=i){
				num++;
			}
			var sContent=this.getContentOfColumn(num,this.getCurrLayout());
			this.addContentToColumn(i,newLayout,sContent);
		}
		newLayout.setAttribute("id",orignLayout.id);
		// 将新元素添加到HTML内容中
		orignLayout.parentNode.replaceChild(newLayout, orignLayout);
		this.fireEvent('afterdelete');
		//标识新的布局
		this.mark(newLayout);
	},
	// 获取某列的内容
	getContentOfColumn:function(_column,_elem){
		var el=this._getElemOfColumn(_column,_elem).elem;
		return el?el.innerHTML:"";
	},
	// 在某列处添加内容
	addContentToColumn:function(_column,_elem,_content){
		var sRatio = this.getCurrLayout(_elem).getAttribute(this.ATTR_RATIO);
		var colNumOfAll = sRatio.split(":").length;
		// 获取_elem的第_column列对应的元素
		var el=this._getElemOfColumn(_column,_elem).elem;
		if(el && _content && _content.length>0)
			el.innerHTML=_content;
	},
	_getInfoOfCol:function (_elem){
		if(!_elem || !_elem.className.match(/c_\d/))
			return null;
		var layout=this.getCurrLayout(_elem);
		var sRatio = layout.getAttribute(this.ATTR_RATIO);
		var colNumOfChar = this._getColNumOfChar(sRatio);
		var columns = this.getColumns(layout);
		var nCol = 0;
		for(var i=0;i<columns.length;i++){
			if(!isColumn(columns[i]))
				continue;
			nCol++;
			if(columns[i]==_elem){
				break;
			}
		}
		// 如果没有自适应列
		var oInfo={};
		if(colNumOfChar<0 || nCol<colNumOfChar){
			oInfo={colNum:nCol,colValue:sRatio.split(":")[nCol-1]};
		}else{
			var nNum = sRatio.split(":").length-nCol+colNumOfChar;
			oInfo={colNum:nNum,colValue:sRatio.split(":")[nNum-1]};
		}
		return oInfo;
	},
	//获取某列的信息,例如第一列对应的HTML元素,对应的比例信息等
	// 这是从比例信息中的列 对应到HTML代码
	_getElemOfColumn:function (_nCol,_layout){
		var columns = this.getColumns(_layout);
		var sRatio = _layout.getAttribute(this.ATTR_RATIO);
		var sRatios = sRatio.split(":");
		if(sRatios.length<_nCol){
			return {};
		}
		var colNumOfChar = this._getColNumOfChar(sRatio);
		var el=null;
		if(colNumOfChar<0 || _nCol<colNumOfChar){
			for(var i=0,j=0;i<columns.length;i++){
				// 不是列元素
				if(!isColumn(columns[i]))
					continue;
				if(_nCol==++j){
					el=columns[i];
					break;
				}
			}
		}else{
			for(var i=0,j=0;i<columns.length;i++){
				// 不是列元素
				if(!isColumn(columns[i]))
					continue;
				if((sRatios.length+colNumOfChar-_nCol)==++j){
					el=columns[i];
					break;
				}
			}
		}
		return {elem:el,ratioValue:sRatios[_nCol-1]}
	},
	// 获取自适应列所在的列数
	_getColNumOfChar:function (_sRatio){
		var temp = _sRatio.substring(0,_sRatio.indexOf("*")+1);
		return temp.length>0?temp.split(":").length:-1;
	},
	getColumns:function(layout){
		layout=layout || this.MARKED_ELEM;
		var columns=[];
		if(!layout)
			return null;
		while(layout){
			for(var i=0;i<layout.childNodes.length;i++){
				if(isColumn(layout.childNodes[i]))
					columns.push(layout.childNodes[i]);
			}
			var child=null;
			if(columns.length==0){
				for(var i=0;i<layout.childNodes.length;i++){
					if(layout.childNodes[i].tagName=="DIV"){
						child=layout.childNodes[i];
						break;
					}
				}
			}else{
				break;
			}
			layout = child;
		}
		return columns;
	}
});

var Layout = new Design.Layout({
	LAYOUT_MARKED :"mark",
	WRAP_CLASS_NAME:"trs_wrap",
	LAYOUT_CLASS_NAME:"trs_layout",
	ATTR_RATIO:"_ratio",
	ATTR_RATIOTYPE:"_ratiotype",
	C_SEP:"c_sep",
	WRAPS:[],
// 保存当前标识的元素
	MARKED_ELEM:null,
	ID:0
});

/*
*在插入布局之前获取最大的布局ID
*/
Layout.on("beforerender",function(){
	if(this.ID!=0)
		return;
	var oLayouts = document.getElementsByClassName("trs_layout");
	for(var i=0;i<oLayouts.length;i++){
		var tempId=oLayouts[i].id.match(/\d+/);
		if(tempId>this.ID)
			this.ID=tempId;
	}
});

/*
*
*/
Layout.on("afterrender",function(parames){
		this.removeMark();
		// 标识刚插入的布局
		this.mark(parames[0]);
		this.MARKED_ELEM = parames[0];
})

Layout.on("afterdelete",function(){
		this.MARKED_ELEM=null;
		// TODO 撤销重做堆栈更新
})



// 从字符串获取布局元素
function __getElemFromString(_sHtml){
	var newEl = document.createElement("div");
	newEl.innerHTML=_sHtml;
	var children = newEl.children;
	for(var i=0;children.length;i++){
		if(children[i].tagName=="DIV")
			return children[i];
	}
	return null;
}
function modifyCol(){
	// 获取选中的布局

}
var m_oHtml="sss";
Event.observe(window, 'load',function(){
	// 获取所有的容器
	WRAPS = getTrsWraps();
});
// 获取存放布局的容器
function getTrsWraps(){
	return document.getElementsByClassName(Layout.WRAP_CLASS_NAME);
}





// 往后插入一个布局元素
_insertAfter=function (newEl,tarElem){
	if(!tarElem)
		return;
	if(tarElem.parentNode.lastChild==tarElem){
		tarElem.parentNode.appendChild(newEl);
		// TODO 撤销重做堆栈更新
	}else{
		_insertBefore(newEl,tarElem.nextSibling);
	}
}

// 往elem前插入一个元素newEl
_insertBefore=function (newEl,tarElem){
	if(!tarElem)
		return;
	tarElem.parentNode.insertBefore(newEl,tarElem);
	// TODO 撤销重做堆栈更新
}






// 删除所有的标识
function removeAllMark(){
	for(var k=0;k<WRAPS.length;k++){
		var childrenNodes = WRAPS[k].children;
		for(var i=0;i<childrenNodes.length;i++){
			Design.Layout.removeMark();
		}
	}
}
// 创建横向分隔div
function __createR_SEP(){
	var elem = document.createElement("div");
	elem.innerHTML="<div class='r_sep'></div>";
	return elem.firstChild;
}

function isColumn(_el){
	if(_el && _el.className && _el.className.match(/c_\d+/))
		return true;
	return false;
}