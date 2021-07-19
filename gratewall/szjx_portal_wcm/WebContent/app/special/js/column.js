Ext.ns("wcm.special.column");
wcm.special.column.dragger=new wcm.util.Draggable();
Ext.apply(wcm.special.column.dragger,{
	minWidth : 50,
	handleMouseOver : function(e){
		if(this.dragging || wcm.util.Draggable.dragging)
			return;
		if(!Element.hasClassName(this.dragEl,"trs_column"))
			this.dragEl = this.findDragEl(e);
	},
	findDragEl : function(event){
		var dom = Event.element(event.browserEvent);
		var column = Element.find(dom, null, 'trs_column');
		if(Element.hasClassName(dom,"c-box") || Element.hasClassName(dom,"trs_column")){
			return column;
		}
		return null;
	},
	onDragStart : function(x, y, e){
		Event.stop(e.browserEvent);
		if(!this.dragEl)
			this.dragEl = this.findDragEl(e);
		var dragEl = this.dragEl;
		if(!dragEl) return;
		// 获取布局信息
		this.layout = DraggerMgr.getCurrLayout(dragEl);
		// 获取左右的列信息
		this.getColumns(e);
		// 保存初始左右列的宽度
		this.orignWidth = {
			left:this.leftCol?this.leftCol.offsetWidth:null,
			right:this.rightCol?this.rightCol.offsetWidth:null
		}
	},
	onDrag : function(x, y, e){
		if(!this.dragEl || !this.leftCol || !this.rightCol)
			return;
		//计算鼠标移动距离
		var distanceX = x-this.lastPointer[0];
		var layout = this.layout;
		//如果宽度小于最小的宽度，则不能再缩小
		if(this.orignWidth.left+distanceX<this.minWidth || this.orignWidth.right-distanceX<this.minWidth)
			return;
		//如果不是自适应列
		if(!DraggerMgr.isAdaptiveCol(this.leftCol,layout))
			this.leftCol.style.width =this.orignWidth.left+distanceX+"px";
		//如果不是自适应列
		if(!DraggerMgr.isAdaptiveCol(this.rightCol,layout))
			this.rightCol.style.width =this.orignWidth.right-distanceX+"px";
	},
	onDragEnd : function(x, y, e){
		if(!this.dragEl || !this.leftCol || !this.rightCol)
			return;
		var layout = this.layout;
		//修改布局上的比例值
		var ratios = layout.getAttribute(Layout.ATTR_RATIO).split(Layout.RATIO_SEPERATE);
		if(ratios[this.leftColNum-1]!=Layout.ADAPTIVE_CHAR){
			// 如果是百分比
			if(layout.getAttribute(Layout.ATTR_RATIOTYPE)==2){
				ratios[this.leftColNum-1]=parseInt(this.leftCol.offsetWidth/layout.offsetWidth*100);
			}else{// 如果是固定比
				ratios[this.leftColNum-1]=parseInt(this.leftCol.offsetWidth);
			}
		}
		if(ratios[this.rightColNum-1]!=Layout.ADAPTIVE_CHAR){
			// 如果是百分比
			if(layout.getAttribute(Layout.ATTR_RATIOTYPE)==2){
				ratios[this.rightColNum-1]=parseInt(this.rightCol.offsetWidth/layout.offsetWidth*100);
			}else{// 如果是固定比
				ratios[this.rightColNum-1]=parseInt(this.rightCol.offsetWidth);
			}
		}
		layout.setAttribute(Layout.ATTR_RATIO,ratios.join(Layout.RATIO_SEPERATE));
		
		//触发保存操作和撤销操作
		//todo...此处应该想办法做事件调用，否则都要找到此处代码进行修改
		PageController.getStateHandler().saveState();
		PageController.refreshTree();
	},
	getColumns : function(event){
		if(!this.dragEl) return;
		var xy = this.getXY(event);
		var page = Position.cumulativeOffset(this.dragEl);
		// 如果鼠标位置在元素的右边
		if((page[0]+this.dragEl.offsetWidth/2)<xy[0]){
			this.leftCol = this.dragEl;
			this.rightCol = this.getRightCol(this.leftCol);
		}else{// 如果在左边
			this.rightCol = this.dragEl;
			this.leftCol = this.getLeftCol(this.rightCol);
		}
	},
	//已知左边的列，计算右边的列
	getRightCol : function(leftCol){
		if(!leftCol) return null;
		var colNum = this.layout.getAttribute(Layout.ATTR_RATIO).split(Layout.RATIO_SEPERATE).length;
		var leftColNum = DraggerMgr.getColNum(leftCol);
		this.leftColNum = parseInt(leftColNum);
		if(this.leftColNum==colNum) return null;
		this.rightColNum = this.leftColNum+1;
		return DraggerMgr.getColElem(this.leftColNum+1, this.layout);
	},
	// 已知右边的列，计算左边的列
	getLeftCol : function(rightCol){
		if(!rightCol) return null;
		var colNum = this.layout.getAttribute(Layout.ATTR_RATIO).split(Layout.RATIO_SEPERATE).length;
		var rightColNum = DraggerMgr.getColNum(rightCol);
		this.rightColNum = parseInt(rightColNum);
		if(this.rightColNum==1) return null;
		this.leftColNum = this.rightColNum-1;
		return DraggerMgr.getColElem(this.rightColNum-1, this.layout);
	}
});


Ext.ns("wcm.special.column.draggerMgr");
Ext.apply(wcm.special.column.draggerMgr,{
	getCurrLayout : function(el){
		return Layout.getLayout(el);
	},
	getColNum : function(currColumn){
		return Layout._getInfoOfCol(currColumn).colNum;
	},
	getColElem : function(num,layout){
		return Layout._getElemOfColumn(num,layout).elem;
	},
	isAdaptiveCol : function(currCol,currLayout){
		var ratio  = currLayout.getAttribute(Layout.ATTR_RATIO);
		var charNum = Layout._getColNumOfChar(ratio)
		return charNum==this.getColNum(currCol);
	}
});

var DraggerMgr =wcm.special.column.draggerMgr;

Event.observe(window, 'load', function(){
	wcm.special.column.dragger.init();
});