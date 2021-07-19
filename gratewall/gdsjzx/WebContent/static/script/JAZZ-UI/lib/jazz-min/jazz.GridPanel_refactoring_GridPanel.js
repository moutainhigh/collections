(function( $, factory ){
	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 
		         'jazz.Paginator', 
		         'jazz.Loading', 
		         'jazz.DataFormat', 
		         'jazz.Panel'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){

(function ($, undefined) {
	
	//暂开辟gird空间，放置表格特性功能(如可编辑表格、可分组表格、合计功能表格等等)
	jazz.namespace("grid");
	/**
	 * @version 1.0
	 * @name jazz.gridpanel
	 * @description 表格类
	 * @constructor
	 * @extends jazz.panel
	 */	
    $.widget("jazz.gridpanel", $.jazz.panel,  {
        options: /** @lends jazz.gridpanel# */ {

        	/**
        	 * @desc 组件类型
        	 * @type 'String'
        	 */
        	vtype: 'gridpanel',
        	/**
             * @type String
             * @desc gridpanel组件标识名称
             * @default null
			 */
        	name: null,
        	/*width height title titledisplay 属性继承自panel*/
			/**
             * @type String
             * @desc 数据请求地址url
             * @default null
			 */
			dataurl: null, 
			/**
             * @type json
             * @desc 数据请求地址url的参数 {key: value, key2: value2……}
             * @default null
			 */			
			dataurlparams: null,
			/**
             * @type boolean
             * @desc 整个表格是否为可编辑表格（true可编辑/false不可编辑）
             * @default false 
			 */
			iseditable: false,
			/**
             * @type Boolean
             * @desc 是否显式显示可编辑组件
             * @default false
			 */
			isshoweditcell: false,
			/**
             * @type String
             * @desc 表格可编辑类型cell/row/column/table
             * @default "cell"
			 */
			editortype: "cell",
        	/**
        	 * type boolean
        	 * @desc 是否显示选择框
        	 * @default true
        	 */        	
        	isshowselecthelper: true,  
        	/**
        	 * @type boolean
        	 * @desc 否显示工具条， true显示  false不显示
        	 * @default true
        	 */
        	isshowtoolbar: true,  
        	/**
        	 * @type boolean
        	 * @desc 是否显示分页条
        	 * @default true
        	 */
        	isshowpaginator: true,         	
        	/**
        	 * @type String
        	 * @desc gridpanel组件显示视图，card/table
        	 * @default table
        	 */
        	defaultview: "table",
        	/**
        	 * @type boolean
        	 * @desc 是否显示行号
        	 * @default true
        	 */
        	lineno: true,
        	/**
        	 * @type number
        	 * @desc 序号列宽度
        	 * @default 30
        	 */
        	linenowidth: 30,
        	/**
        	 * @type number
        	 * @desc gridpanel表格线类型（0实线，1虚线，2点线）
        	 * @default 0实线
        	 */
        	linetype: 0,
        	/**
        	 * @type number
        	 * @desc gridpanel表格线样式（0无，1横纵，2横，3纵）
        	 * @default 1横纵
        	 */
        	linestyle: 1,
        	/**
             * @type Boolean
             * @desc 控制表格行是否允许选中开关（true/false）
             * @default true
             */
            rowselectable: true,
            /**
             * @type Number
             * @desc 组件的选中类型（0不选择, 1单选框, 2多选框）
             * @default 2多选框
             */
            selecttype: 2,
            /**
             * @function
             * @desc gridpanel数据加载时，自定义数据处理（例如自定义行、列等）
             * @{event} 事件载体
             * @{data} gridpanel组件加载的数据，为json数据{"data":rowsdata}
             * @default null
             */
            datarender: null,
            /**
             * @function
             * @desc 初始化gridpanel组件加载数据完成后，执行回调
             * @{event} 事件载体
             * @{data} gridpanel组件加载的数据，为json数据{"data":rowsdata,"paginationInfo":pagination}
             * @default null
             */
            dataloadcomplete: null,
            /**
             * @type json
             * @desc 数据查询请求参数 {key: value, key2: value2……}
             * @default {}
			 */	
            queryparams: {},
            /**
             * @type json
             * @desc 数据排序请求参数
             * @default {}
			 */	
            sortparams: {},
            /**
             * @type Array
             * @desc gridpanel组件元素内容
             * @default null
             */
            items: null,
            /**
             * @type Boolean
             * @desc 是否对各分组数据进行合计（true/false）
             * @default false
             */
            isgroupsummary:false,
            /**
             * @type Boolean
             * @desc 是否对当前页全部数据进行合计（true/false）
             * @default false
             */
			ispagesummary:false,
			/**
             * @type String
             * @desc 指定数据集中分组字段列，（规则待定，暂时前台据此字段列进行分组排序，未传给后台，建议应该是后台分组排序）
             * @default null
			 */
			groupfield:null,
			/**
             * @type String
             * @desc 分组数据行的展示内容字段列（可以配合datarender自定义使用）
             * @default null
			 */
  			grouptitlefield:null,
  			/**
             * @type Boolean
             * @desc 分组数据行是否展开（true/false）
             * @default true
             */
			isgroupexpand:true
        },
        /** @lends jazz.gridpanel */
        /**
         * @desc 创建gridpanel组件
         * @private
         */ 
        _create: function () {
        	//0. 在gridpanel创建前，保存原html,以待gridpanel组件销毁后重新创建使用
        	this.originHtml = this.element[0].outerHTML;
        	
        	//1.调用jazz.panel的create方法创建gridpanel的框架
            this._super();
            //2.设置gridpanel的样式
        	this.content.css('overflow', 'hidden');
        },
		/**
         * @desc 初始化gridpanel组件
         * @private
         */ 
        _init : function(){
        	this._super();
        	if(!this.options.items || this.options.items.length==0){
	        	this._createContent(this.content);
        	}
        	
            //5.缓存gridpanel子组件操作对象(注意：无论vtype、widget形式时，子组件并未创建完成)
        	this.gtoolbar = this.getChildrenComponentByVtype("toolbar");
	        this.gcolumn = this.getChildrenComponentByVtype("gridcolumn");
	        this.gtable = this.getChildrenComponentByVtype("gridtable");
	        this.gcard = this.getChildrenComponentByVtype("gridcard");
	        this.gpaginator = this.getChildrenComponentByVtype("paginator");
        	
	        //注入表格扩展特性
	        this._extendGridpanelFunctions();
	        
	        //设置子组件隐藏与显示、布局
	        this._settingGridpanelComponentView();
            
            this.paginationInfo = {};
            this.rows = [];
            
            //girdpenel获取数据，并且循环判断是否需要reload子组件数据渲染
            this.initLoadData();
        },
        _extendGridpanelFunctions: function(){
	        //可编辑表格特性
        	var gtable = this.gtable.data("gridtable");
        	if(gtable&&gtable._isGridtableEditable()){
        		$.extend(this,jazz.grid.editorgridpanel);
        	}
        	console.log(this);
        },
        /**
         * @desc 设置gridpanel子组件隐藏与显示、布局
         * @private
         * @example this._settingGridpanelComponentView();
         */
        _settingGridpanelComponentView: function(){
        	//6.设置子组件隐藏与显示、布局
	        if(this.options.isshowtoolbar==false||this.options.isshowtoolbar=="false"){
            	if(this.gtoolbar){
	            	this.gtoolbar.css('display', 'none');
            	}
            }
            if(this.options.isshowpaginator==false||this.options.isshowpaginator=="false"){
            	if(this.gpaginator){
	            	this.gpaginator.css('display', 'none');
            	}
            }
	        //7.gridpanel组件pagearea高度设置，分两种情况：
            //7.1 gridpanel设置layout：fit和height属性值时,pagearea使用fit布局
            //7.2 gridpanel非fit布局且无height值时，pagearea不fit布局，随卡片和表格内容扩充
            if(parseInt(this.options.height)>0||this.options.layout=="fit"){
	            var toolbarheight = 0;
	           	if(this.gtoolbar){
	           		if(this.gtoolbar.css('display')!='none'){
	           			toolbarheight = this.gtoolbar.outerHeight(true)||0;
	           		}
	           	}
	           	var gridcolumnheight = 0;
	           	if(this.gcolumn){
	           		if(this.gcolumn.css('display')!='none'){
	           			gridcolumnheight = this.gcolumn.outerHeight(true)||0;
	           		}
	           	}
	        	var paginatorheight = 0;
	           	if(this.gpaginator){
	           		if(this.gpaginator.css('display')!='none'){
	           			paginatorheight = this.gpaginator.outerHeight(true)||0;
	           		}
	           	}
	           	var pagearea = this.element.find(".jazz-pagearea");
				if(pagearea[0]){
		        	var h = 0;
		            if(this.options.defaultview=="card"){
		        		h = this.content.height()-toolbarheight-paginatorheight;
			        	pagearea.height(h);
		            }else{
		        		h = this.content.height()-toolbarheight-gridcolumnheight-paginatorheight;
			        	pagearea.height(h);
		            }
				}
            }

            //8.gridpanel表格和卡片视图隐藏、显示
            if(this.options.defaultview=="card"){
	        	if(this.gcolumn){
		        	this.gcolumn.hide();
	        	}
	        	if(this.gtable){
		        	this.gtable.hide();
	        	}
            }else{
	        	if(this.gcard){
		        	this.gcard.hide();
	        	}
            }
        },

        /**
         * @desc 获取options属性对象, 提供给子类使用
         * @return object 
         * @private 
         */
        _getGridOptions: function(){
        	return this.options;
        },
        /**
         * @desc 获取工具条对象
         * @return object  
         * @private  
         */            
        getToolbar: function(){
        	return this.gtoolbar;
        },
        /**
         * @desc 获取表头对象
         * @return object 
         * @private  
         */       
        getGridColumn: function(){
        	return this.gcolumn;
        },        
        /**
         * @desc 获取表格对象
         * @return object 
         * @private  
         */        
        getGridTable: function(){
	        return this.gtable;
        },
		/**
         * @desc 获取卡片对象
         * @return object  
         * @private  
         */
        getGridCard: function(){
        	return this.gcard;
        },
        /**
         * @desc 获取翻页条对象
         * @return object  
         * @private  
         */        
        getPaginator: function(){
        	return this.gpaginator;        	
        },
        /**
         * @desc 重新计算gridpanel表格和卡片区域宽度，
         * 建议该方法在以下情况下调用：
         * 1.gridpanel组件在隐藏元素内创建，并且其表头每列宽度不是绝对值设定时
         * @public
         * @example $('div[name="gridpanel"]').gridpanel('recalculateGridpanelWidth');
         */
        recalculateGridpanelWidth: function(){
        	if(this.gcolumn){
				//根据预定义的列宽，计算每列宽度。缓存自定义宽度值和实际计算值this.columnWidth
			    this.gcolumn.gridcolumn("calculateGridcolumnWidth");
			    //为表格总宽度和各列宽度赋值
			    this.gcolumn.gridcolumn("settingGridcolumnWidth");
        	}
        	if(this.gtable){
				this.gtable.gridtable('calculateGridtableWidth');
        	}
        	if(this.gcard){
        		this.gcard.gridcard("calculateGridcardWidth");
        	}
        },
       /**
		 * @desc 根据定义列名(name属性值)显示隐藏列
		 * @param {columnnanme} 列名name
		 * @throws :无法匹配该name表格列
		 * @example $('div[name="gridpanel"]').gridpanel('showColumn', 'sex');
		 */
        showColumn: function(columnname){
        	this.gcolumn.gridcolumn('showColumn',columnname);
        },
        /**
		 * @desc 根据定义列名(name属性值)隐藏表格列
		 * @param {columnnanme} 列名name
		 * @throws :无法匹配该name表格列
		 * @example $('div[name="gridpanel"]').gridpanel('showColumn', 'sex');
		 */
        hideColumn: function(columnname){
        	this.gcolumn.gridcolumn('hideColumn',columnname);
        },
        showHeader: function(){
        	var display = $this.gcard.css("display");
            if(display=="none"){
            }
        },
        hideHeader: function(){
        	var display = $this.gcard.css("display");
            if(display!="none"){
            	
            }
        },
        /**
		 *@desc 根据视图标识(defaultview属性值)切换视图
		 *@param {viewname} 视图标识(card/table)
		 *@return undefined
		*@example $('div[name="gridpanel"]').gridpanel('showView', 'card');
		*/
        showView : function(viewname){
            var $this = this;
            
            //切换表格和卡片的时候，需要注意gridcolumn的hideheader是否显示
            var hideheader = $this.gcolumn.data("gridcolumn").options["hideheader"];
            var gridcolumnheight = 0;
           	if(!hideheader||hideheader=="false"){
       			gridcolumnheight = $this.gcolumn.outerHeight(true)||0;
           	}
            var display="";
            if(viewname =="card"){
            	display = $this.gcard.css("display");
            	if(display=="none"){
            		//gridpanel使用fit布局或设定高度时，pagearea使用fit布局固定高度，
            		//（pagearea而不是根据卡片和表格内容自扩充高度），此时pagearea切换表格和卡片时，需要计算高度
            		if(parseInt($this.options.height)>0||$this.options.layout=="fit"){
		            	//$this.gcard.parent().height($this.gcard.parent().height()+$this.gcolumn.outerHeight(true));
		            	$this.gcard.parent().height($this.gcard.parent().height()+gridcolumnheight);
            		}
            		$this.gcard.parent().css({overflow: 'auto'});
            		if(!hideheader||hideheader=="false"){
		       			$this.gcolumn.hide();
		           	}
	                $this.gtable.hide();
	                $this.gcard.show();
            	}
            }else{
            	display = $this.gtable.css("display");
            	if(display=="none"){
            		if(parseInt($this.options.height)>0||$this.options.layout=="fit"){
		            	$this.gcard.parent().height($this.gcard.parent().height()-gridcolumnheight);
            		}
            		$this.gcard.parent().css({overflow: 'hidden'});
            		if(!hideheader||hideheader=="false"){
		       			$this.gcolumn.show();
		           	}
	                $this.gtable.show();
	                $this.gcard.hide();
            	}
            }
        },
        /**
         * @desc 修改gridpanel子组件的options属性(暂时未使用)
         * @param {vtype} 子组件vtype类型
         * @param {key} 修改option的属性name值
         * @param {value} 修改option的value值
         * @private
         * @example $("#gridpanel").gridpanel("setCompOption",vtype,key,value);
         */
        setCompOption: function(vtype,key,value){
        	
        },
        /**
         * @desc gridpanel组件的销毁
         * @public
         * @example $("#gridpanel").gridpanel("destroyComp");
         */
        destroyComp: function(){
        	//销毁gridpanel包含的子组件
        	if(this.gtoolbar){
	        	var toolbar = this.gtoolbar.data('toolbar');
				toolbar.destroy();
        	}
        	if(this.gcolumn){
				var gridcolumn = this.gcolumn.data('gridcolumn');
				gridcolumn.destroy();
        	}
        	if(this.gtable){
				var gridtable = this.gtable.data('gridtable');
				gridtable.destroy();
        	}
        	if(this.gcard){
				var gridcard = this.gcard.data('gridcard');
				gridcard.destroy();
        	}
        	if(this.gpaginator){
				var paginator = this.gpaginator.data('paginator');
				paginator.destroy();
        	}
			//替换为原待渲染html内容
			this.element.replaceWith(this.originHtml);
        	this.destroy();
        },
        /**
         * @desc 据行坐标或者列坐标使单元格变为可编辑表格
         * @augments {rowIndex}{colIndex}行坐标，列坐标
         * @public
         * @example $("#gridpanel").gridpanel("setEditorCell",1,2);
         */
        setEditorCell: function(rowIndex,colIndex,editState){
        	var rowIndex = parseInt(rowIndex);
        	var colIndex = parseInt(colIndex);
        	var editState = editState==true||editState=="true";
        	if(rowIndex>0 || colIndex>0){
        		if(this.gtable){
        			this.gtable.gridtable('setEditorCell',rowIndex,colIndex,editState);
        		}
        	}
        },
        /**
         * 新增可编辑行
         */
        /*addEidtorRow: function(insertPosition){
    		 // 考虑如何维护新添加行的数据
    		 // 数据中的内容，都有那些数据？
    		 // rowuuid,各个td
        	if(this.gtable){
        		// 新增加的数据的数据项如何确定有以下两种情况：
        		// 1.当可编辑表格有原始数据的时候，参照原始数据，并将各数据项设置为null,rowuuid赋随机值
        		// 2.若无原始数据，则按照定义列name值添加数据项，并设置为null,rowuuid赋随机值
        		var data = {};
        		if(this.rows.length>0){
        			var copyrow = this.rows[0];
        			for(var key in copyrow ){
        				//初始值都设置为null
        				data[key] = null;
        			}
        		}else{
        			if(this.gcolumn){
		    			var gridcolumn = this.gcolumn.data("gridcolumn");
		    			cols = gridcolumn.cols;
		    			var columnname = "";
			            for (var j = 0, len = cols.length; j < len; j++) {
			            	columnname = cols[j]['columnname'];
			            	data[columnname] = null;
			            }
					}
        		}
        		data["eidtorstate"] = "editing";
        		data["rowuuid"] = Math.uuid(32);
				this.gtable.gridtable('addEidtorRow',data,insertPosition);
	        	//将新增的编辑数据放入到原始数据中
	        	this.rows.push(data);
        	}
        },*/
		/**
		 * @desc 添加行记录
		 * @param {data} 行数据数组 [{key1: value1, key2: value2, ……}]
		 * @public
		 * @example $("#gridpanel").gridpanel("addRow",data);
		 */
        addRow: function(data) {
        	//调用gridtable和gridcard各自addRow方法，实现的是追加数据，而不是重新加载
        	if(data){
				this._setRowUUID(data);
        		//1.为this.rows拼接data
		       	this.rows = this.rows.concat(data);
		       	data = $.extend(true,[],data);
        		//2.分别修改表格和卡片
        		var $this = this;
	    		this._code2DataRenderGridpanel(data,
		    		function(rowdata){
		    			//3.对待渲染数据进行datarender和数据类型（datatype、dataformat）转换进行处理
		    			$this._processGridpanelData(rowdata);
						if($this.gtable){
				        	var gridtable = $this.gtable.data('gridtable');
				            gridtable.addRow(rowdata);
			        	}
						if($this.gcard){
			        		var gridcard = $this.gcard.data('gridcard');
				            gridcard.addCard(rowdata);
			        	}
		    		}
	    		);
        	}
        },
         /**
         * @desc 该方法直接使用data数据的rowuuid更新,以解决没有指定key主键及没有行号rowindex的情况
         * @param {data} 待更新数据
         * @public
         * @example $("#gridpanel").gridpanel("updateRow",data);
         */
	    updateRow : function(data){
	    	var rowuuid = data["rowuuid"];
	    	if(!rowuuid){return null;}
	    	this._updateTableAndCardById(data,rowuuid,"rowuuid");
	    },
	    /**
         * @desc 根据在gridcolumn中定义的主键列的值，更新行数据，（注意：若无定义key主键列，该方法无法使用）
         * @param {id} String 设置key=true列的主键值
         * @param {data} 根据主键待更新的行数据
         * @public
         * @example $("#gridpanel").gridpanel("updateRowById",{name:james,age:18...},"001");
         */
	    updateRowById : function(data, id){
	    	if(!id){
				return null;	
			}
			var keyColumn = this._getGridcolumnKeyCode();
			if(!keyColumn){
				return null;	
			}
			this._updateTableAndCardById(data,id,keyColumn);
	    },
	    /**
         * @desc gridpanel更新指定的表格行和卡片,提示：该方法在无定义key=true主键列时使用
         * @param {data} 更新数据
         * @param {rowIndex} 表格行或卡片当前页索引顺序
         * @public
         * @example $("#gridpanel").gridpanel("updateRow",{name:james,age:18...},2);
         */
	    updateRowByIndex : function(data, rowIndex){
	    	var id = this._getRowUUIDByRowIndex(rowIndex);
	    	if(!id){return null;}
	    	
	    	 this._updateTableAndCardById(data,id,"rowuuid");
	    },
	    _updateTableAndCardById: function(data,id,keyColumn){
	    	var row = null;
    		for(var i=0,len=this.rows.length;i<len;i++){
    			if(this.rows[i][keyColumn]==id){
    				for(var key in data){
		    			this.rows[i][key] = data[key];
		    		}
		    		row = $.extend(true,{},this.rows[i]);
		    		break;
    			}
    		}
    		if(!row){
				return null;	
			}
    		//转化成数组
    		var rowarr = [];
    		rowarr.push(row);
    		var $this = this;
    		this._code2DataRenderGridpanel(rowarr,
	    		function(rowdata){
	    			//3.对待渲染数据进行datarender和数据类型（datatype、dataformat）转换进行处理
		    		$this._processGridpanelData(rowdata);
					if($this.gtable){
			        	var gridtable = $this.gtable.data('gridtable');
			            gridtable.updateRowById(rowdata[0],rowdata[0]["rowuuid"]);
		        	}
					if($this.gcard){
		        		var gridcard = $this.gcard.data('gridcard');
			            gridcard.updateCardById(rowdata[0],rowdata[0]["rowuuid"]);
		        	}
	    		}
    		);
	    },
	    _code2DataRenderGridpanel: function(rowsdata,fn){
	    	var $this = this;
	    	var cols=null,toCodeColumn=null;
			if($this.gcolumn){
    			var gridcolumn = $this.gcolumn.data("gridcolumn");
    			cols = gridcolumn.cols;
    			toCodeColumn = gridcolumn.toCodeColumn;
    			toCodeColumn = $this._combinedStaticAndUrlValueSet(toCodeColumn);
			}
			if(toCodeColumn&&toCodeColumn.length>0){
				//表示需要做代码集转换（代码转换后执行以后步骤）
				$this._code2DataComplete(100, 5000, rowsdata,toCodeColumn, function(data){
					fn(data);
				});
			}else{
    			//数据都转换完毕后，交由gridtable和gridcard进行创建渲染表格和卡片
				fn(rowsdata);
			}
	    },
        
        /**
         * @desc 获取全部数据
         * @return {Array}
         * @public
         * @example $("#gridpanel").gridpanel("getAllData");
         */
        getAllData : function(){
        	//gridpanel维护的就是卡片和表格统一的数据
            return this.rows || [];
        },   
        
        /**
         * @deprecated
         * @desc 获取指定索引行的JSON数据,注意该方法被getRowDataByIndex替代，若是想要在加载完gridpanel后获取数据，使用dataloadcomplete属性
         * @param {rowIndex} 获取数据的行号
         * @example $("#gridpanel").gridpanel("getRow",1);
         */
        getRow: function(rowIndex){
        	rowIndex = parseInt(rowIndex)+1;
        	var data = this.getRowDataByIndex(rowIndex);
        	return data;
        },
        
        /**
         * @desc 获得有效的行的长度
         * @return number
         * @example $("#gridpanel").gridpanel("getRowLength");
         */
        getRowLength : function() {
            return this.rows ? this.rows.length : 0;
        },
       	
        /**
         * @desc 根据在gridcolumn中定义的主键列的值，获取行数据，（注意：若无定义key主键列，该方法无法使用）
         * @param {id} String 设置key=true列的主键值
         * @return data 根据主键获取行数据
         * @public
         * @example $("#gridpanel").gridpanel("getRowDataById","001");
         */
        getRowDataById: function(id){
        	if(!id){
				return null;	
			}
			var keyColumn = this._getGridcolumnKeyCode();
			if(!keyColumn){
				return null;	
			}
        	var data = null;
            var ids = [];
			ids.push(id);
			data = this._getRowdataByKey(ids,keyColumn,"singleobject");
        	return data;
        },
        /**
         * @desc 根据"序号列"的行索引获取行数据，（提示：通常若无定义key主键列时使用该方法）
         * @param {index}
         * @return data 根据index获取的行数据
         * @public
         * @example $("#gridpanel").gridpanel("getRowDataByIndex",7);
         */
        getRowDataByIndex: function(index){
        	var id = this._getRowUUIDByRowIndex(index);
	    	if(!id){return null;}
        	
        	var $this = this;
    		var data=null;
        	if(id){
				var ids = [];
				ids.push(id);
				data = $this._getRowdataByKey(ids,"rowuuid","singleobject");
        	}
        	return data;
        },
        /**
         * @desc 根据rowIndex行号获取rowuuid，以便获取该行显示的rowdata数据
         * （该方法是针对于gridtable而言，由表格行号确认rowuuid；卡片暂不提供此功能）
         * @param {index}
         * @private
         */
        _getRowUUIDByRowIndex: function(index){
        	var $this = this;
    		var id = null;
        	if($this.gtable){
        		var tds = $this.gtable.find("td.jazz-grid-cell-no");
        		for(var i=0,len=tds.length;i<len;i++){
        			var lineno = $(tds[i]).html();
        			if(parseInt(lineno)==parseInt(index)){
        				id = $(tds[i]).parent().attr("id");
        				break;
        			}
        		}
        	}
        	return id;
        },
        /**
         * @desc 根据gridcolumn中定义的key=true的列获取rowuuid，以便获取该行显示的rowdata数据
         * @param {index}
         * @private
         */
        _getRowUUIDByKeyCode: function(dataId){
        	var keyColumn = this._getGridcolumnKeyCode();
			if(!keyColumn){
				return null;
			}
            var ids = [],rowuuid=null;
			ids.push(dataId);
			var data = this._getRowdataByKey(ids,keyColumn,"singleobject");
        	if(data){
        		rowuuid = data["rowuuid"];
        	}
        	return rowuuid;
        },
        
        /**
         * @deprecated
         * @desc 该方法直接根据rowuuid删除数据，以解决没有指定key主键及没有行号rowindex的情况
         * @param {data} 待删除的数据
         */
        removeRow: function(data){
    		//this.removeRowByIndex(index);
    		var rowuuid = data["rowuuid"];
	    	if(!rowuuid){return null;}
	    	var data = this._removeTableAndCardById(rowuuid,"rowuuid");
        	return data;
        },
        /**
         * @desc 根据在gridcolumn中定义的主键列的值，删除行数据，（注意：若无定义key主键列，该方法无法使用）
         * @param {id} String 设置key=true列的主键值
         * @return data 根据主键删除的行数据
         * @public
         * @example $("#gridpanel").gridpanel("removeRowById","001");
         */
        removeRowById: function(id){
        	if(!id){
				return null;	
			}
			var keyColumn = this._getGridcolumnKeyCode();
			if(!keyColumn){
				return null;
			}
            var data = this._removeTableAndCardById(id,keyColumn);
        	return data;
        },
        /**
         * @desc 根据"序号列"的行索引删除行数据，（提示：通常若无定义key主键列时使用该方法）
         * @param {index}
         * @return data 根据index删除的行数据
         * @public
         * @example $("#gridpanel").gridpanel("removeRowByIndex",7);
         */
        removeRowByIndex: function(index){
        	var id = this._getRowUUIDByRowIndex(index);
	    	if(!id){return null;}
	    	
	    	var data = this._removeTableAndCardById(id,"rowuuid");
        	return data;
        },
        _removeTableAndCardById: function(id,keyColumn){
        	if(!id){
        		return null;
        	}
			//2.维护共享数据this.rows
			var data = this._removeRowdataByKey(id,keyColumn);
			if(data&&data["rowuuid"]){
				//1.分别删除表格和卡片
				if(this.gtable){
		        	var gridtable = this.gtable.data('gridtable');
		            gridtable.removeRowById(data["rowuuid"]);
	        	}
				if(this.gcard){
	        		var gridcard = this.gcard.data('gridcard');
		            gridcard.removeCardById(data["rowuuid"]);
	        	}
			}
			return data;
        },
        /**
         * @desc 返回当前可编辑表格已编辑数据
         * @example $("#gridpanel").gridpanel("getUpdatedRowData");
         */
        /*getUpdatedRowData: function(){
        	var that = this;
        	var rows = that.rows;
        	var data = [],state="",temp=null;
        	for(var i=0,len=rows.length;i<len;i++){
        		state = rows[i]["eidtorstate"];
        		if(state){
        			temp={};
        			$.extend(true,temp,rows[i]);
        			data.push(temp);
        		}
        	}
        	return data || [];
        },*/
    	/**
         * @deprecated
         * @desc 返回当前选中的所有数据(今后该方法请使用getSelectedRowData代替)
         * @example $("#gridpanel").gridpanel("getSelection");
         */
        getSelection: function(){
        	var $this = this;
        	var data =null;
        	data = $this.getSelectedRowData();
        	return data || [];
        },
    	/**
         * @desc 返回当前选中的所有数据，替代getSelection方法
         * @return {Array}
         * @public
         * @example $("#gridpanel").gridpanel("getSelectedRowData");
         */
        getSelectedRowData: function(){
        	var $this = this;
        	var data =null;
        	//1.获取rowuuid,要考虑表格和卡片是否同时存在的情况
			var rowuuids = $this._getSelectedRowId();
        	data = $this._getRowdataByKey(rowuuids,"rowuuid") || [];
        	if(data){
	        	//为data获取lineNo，只考虑表格行号，不考虑卡片的index
	        	var trId="",no="";
	        	for (var i=0,len=data.length; i<len; i++) {
					if($this.gtable){
						trId = data[i]["rowuuid"];
						no = $this.gtable.find("#"+trId+" td.jazz-grid-cell-no").html();
						data[i]["lineNo"]=no || "";
		        	}else{
		        		data[i]["lineNo"]="";
		        	}
	            }
        	}
        	return data;
        },
        _getSelectedRowId: function(){
        	var $this = this;
        	var idArray = [],id=null;
        	if($this.gtable){
				var trs = $this.gtable.find("tr[aria-selected=true]");
				for(var i=0,len=trs.length;i<len;i++){
					id = $(trs[i]).attr("id");
					if(id){
						idArray.push(id);
					}
				}
        	}else if($this.gcard){
        		var cards = $this.gcard.find('div.jazz-grid-cardcell[aria-selected=true]');
				for(var i=0,len=cards.length;i<len;i++){
					id = $(cards[i]).attr("id");
					if(id){
						idArray.push(id);
					}
				}
        	}
        	return idArray;
        },
        /**
         * @desc 根据ids,key从gridpanel缓存的this.rows中获取数据
         * @param {ids} 主键值数组
         * @param {key} 标识主键的name,(key=true的主键列name或者rowuuid)
         * @param {returnDataType} 返回data数据的类型，是单个json对象或者是json数组
         * @return data 返回匹配的数据
         * @private
         */
    	_getRowdataByKey: function(ids,key,returnDataType){
    		var $this = this,data=[];
    		if(!ids||ids.length==0||!key){
    			return null;
    		}
    		var num = ids.length;
			for (var i=0,len=$this.rows.length; i<len; i++) {
				if(num==0){
					break;
				}
				for(var j=0; j<ids.length; j++){
					if($this.rows[i][key]==ids[j]){
	            		data.push($this.rows[i]);
	            		num = num-1;
	            		break;
	            	}
				}
            }
        	return returnDataType=="singleobject"&&data.length==1?data[0]:data;
    	},
    	/**
         * @desc 根据id,key从gridpanel缓存的this.rows中删除数据
         * @param {id} 主键值
         * @param {key} 标识主键的name,(key=true的主键列name或者rowuuid)
         * @return data 返回匹配删除的数据
         * @private
         */
        _removeRowdataByKey: function(id,key){
    		var $this = this,data=null;
    		if(!id||!key){return data;}
    		
			for (var i = 0,len=$this.rows.length; i<len; i++) {
            	if($this.rows[i][key]==id){
            		data = $this.rows[i];
            		delete $this.rows[i];
            		break;
            	}
            }
        	for(var j=0, ln=$this.rows.length; j<ln; j++){
        		if(!$this.rows[j]){
        			$this.rows.splice(j, 1);
        		}
        	}
        	return data;
    	},
    	/**
    	 * @desc 根据表格行或者卡片id属性值获取单行或者卡片被选中的数据
    	 * @param {id} 被选中表格行或者卡片的id属性值
    	 * @return data 被选中表格或者卡片对应gridpanel的数据
    	 * @private
    	 * @example this.getSelectedRowDataById(id);
    	 */
     	getSelectedRowDataById: function(id){
        	if(!id){
				return null;	
			}
        	var data = null;
        	var ids = [];
        	ids.push(id);
            data = this._getRowdataByKey(ids,"rowuuid","singleobject");
        	return data;
        },
    	/**
		 * @deprecated
		 * @desc 根据ID选中行，该方法被selectRowById替代
		 * @param {id} 代表行数据的id主键
		 */
        selectRow : function(id){
        	this.selectRowById(id);
        },
        /**
         * @deprecated
         * @desc 根据ID取消选中行，该方法被selectRowById替代
         * @param {id} 代表行数据的id主键
         */
        unselectRow: function(id) {
        	this.unselectRowById(id);
        },
        /**
		 * @desc 根据ID选中行，该方法替代原selectRow(id)，注意：在gridcolumn中表头没有表明key=true列时，该方法不能被使用
		 * @param {id} 主键（在gridcolumn中表头属性表明key=true列）
		 * @public
		 * @example $("#gridpanel").gridpanel("selectRowById",id);
		 */
        selectRowById: function(id){
        	if(!id){return ;}
        	var rowuuid = this._getRowUUIDByKeyCode(id);
        	if(rowuuid){
	    		//1.分别执行表格和卡片选中
	        	if(this.gtable){
	        		var gridtable = this.gtable.data('gridtable');
	        		gridtable.selectRow(rowuuid);
	        	}
	        	if(this.gcard){
	        		var gridcard = this.gcard.data('gridcard');
	        		gridcard.selectRow(rowuuid);
	        	}
        	}
        },
        /**
		 * @desc 根据“序号列”序号值选中行，提示：通常在gridcolumn中表头没有表明key=true列时使用该方法
		 * @param {index} “序号列”序号值
		 * @public
		 * @example $("#gridpanel").gridpanel("selectRowByIndex",index);
		 */
        selectRowByIndex: function(index){
        	
        },
        /**
		 * @desc 根据ID取消选中行，该方法替代原unselectRow(id)，注意：在gridcolumn中表头没有表明key=true列时，该方法不能被使用
		 * @param {id} 主键（在gridcolumn中表头属性表明key=true列）
		 * @public
		 * @example $("#gridpanel").gridpanel("unselectRowById",id);
		 */
        unselectRowById: function(id){
        	if(!id){return ;}
        	var rowuuid = this._getRowUUIDByKeyCode(id);
        	if(rowuuid){
        		//1.分别执行表格和卡片取消选中
	        	if(this.gtable){
	        		var gridtable = this.gtable.data('gridtable');
	        		gridtable.unselectRow(rowuuid);
	        	}
	        	if(this.gcard){
	        		var gridcard = this.gcard.data('gridcard');
	        		gridcard.unselectRow(rowuuid);
	        	}
        	}
        },
        /**
		 * @desc 根据“序号列”序号值取消选中行，提示：通常在gridcolumn中表头没有表明key=true列时，使用该方法
		 * @param {index} “序号列”序号值
		 * @public
		 * @example $("#gridpanel").gridpanel("unselectRowByIndex",index);
		 */
        unselectRowByIndex: function(index){
        	
        },
        /**
		 * @desc 选中全部行
		 * @public
         * @example $("#gridpanel").gridpanel("selectAllRows");
		 */
		selectAllRows: function(){
			if(this.gtable){
        		var gridtable = this.gtable.data('gridtable');
        		gridtable.selectAllRows();
        	}
			if(this.gcard){
        		var gridcard = this.gcard.data('gridcard');
        		gridcard.selectAllRows();
        	}
		},
        /**
		 * @desc 取消选中全部行
		 * @public
         * @example $("#gridpanel").gridpanel("unselectAllRows");
		 */
        unselectAllRows: function() {
        	if(this.gtable){
        		var gridtable = this.gtable.data('gridtable');
        		gridtable.unselectAllRows();
        	}
        	if(this.gcard){
        		var gridcard = this.gcard.data('gridcard');
        		gridcard.unselectAllRows();
        	}
        },
        
        /**
         * @desc gridpanel组件统一请求渲染卡片和表格的数据
         * @private
         * @example this.initLoadData();
         */
        initLoadData: function () {
            var $this = this;
            //确定$this.paginationInfo的值
            if($this.options.isshowpaginator||$this.options.isshowpaginator=="true"){
	            if($this.gpaginator){
	            	var paginator = $this.gpaginator.data("paginator");
	            	var pagerows = 0;
	            	if(paginator){
	            		pagerows = paginator.options["pagerows"]||0;
	            	}else{
	            		pagerows = $this.gpaginator.attr("pagerows")||0;
	            	}
            		$this.paginationInfo["pagerows"] = parseInt(pagerows);
	            }
            }
            if ($this.options.dataurl) {
	            //添加加载缓冲组件
		       	$this.element.find(".jazz-pagearea").loading();
		       	
	            var params = {
	            	url: $this.options.dataurl,
	            	params: $this.options.dataurlparams,
	            	pageparams: $this.paginationInfo,
	            	showloading: false,
	    	        callback: function(responseText,$this){
	    	        	$this.successLoadData("init",responseText,null,null);
	    	        }
	            };    
	            $.DataAdapter.submit(params, $this);
            }
        },
        /**
         * @desc gridpanel初始化创建、查询、排序、翻页等获取数据后渲染表格卡片
         * @param {loadDataType} 获取渲染数据的逻辑情景，如：“init”
         * @param {responseText} ajax获取返回数据
         * @param {requestdatacomplete} 渲染数据回调函数
         * @param {isRebindPaginator} 是否再次绑定分页条（初始化时绑定，翻页时不再绑定）
         * @private
         * @example this.successLoadData("init",responseText,function(data){...},true);
         */
        successLoadData: function(loadDataType,responseText,requestdatacomplete,isRebindPaginator) {
        	var $this = this;
        	if(responseText && typeof(responseText) == 'object'){
        		var dataObj = responseText['data'];
        		if(dataObj){
        			$this.paginationInfo['page'] = dataObj["page"] || 1;
        			$this.paginationInfo['pagerows'] = dataObj["pagerows"] || 10;
        			$this.paginationInfo['totalrows'] = dataObj["totalrows"] || 0;
        			//缓存gridpanel的数据this.rows
        			$this.rows = dataObj["rows"];
        			
        			if($this.rows){
        				
        				//处理$this.rows,增加rowuuid值，作为tr或者card元素的id属性值
        				$this._setRowUUID($this.rows);
        				
        				//克隆rowsdata作为代码集和自定义datarender转换使用
        				var rowsdata = $.extend(true,[],$this.rows);
        				//2.进行代码集转换
	        			//注意：a.使用this.gridcolumn创建完成后获取toCodeColumn
	        			//（但是vtype形式创建gridpanel时，有可能在ajax获取数据后，gridcolumn尚未创建完成）
	        			//b.若是不用a中方式获取代码集，则可以通过gridpanel的options[content]或
	        			//者vtype=gridcolumn的子元素获取代码集
	        			
	        			var cols=null,toCodeColumn=null;
	        			if($this.gcolumn){
		        			var gridcolumn = $this.gcolumn.data("gridcolumn");
		        			cols = gridcolumn.cols;
		        			toCodeColumn = gridcolumn.toCodeColumn;
		        			toCodeColumn = $this._combinedStaticAndUrlValueSet(toCodeColumn);
	        			}
	        			if(toCodeColumn&&toCodeColumn.length>0){
	        				//表示需要做代码集转换（代码转换后执行以后步骤）
	        				$this._code2DataComplete(100, 5000, rowsdata,toCodeColumn, function(data){
	        					if(loadDataType=="init"){
			        				$this.renderGridpanelData(data);
		        				}else{
		        					$this.requestLoadData(data,requestdatacomplete,isRebindPaginator);
		        				}
	        				});
	        			}else{
		        			//数据都转换完毕后，交由gridtable和gridcard进行创建渲染表格和卡片
	        				if(loadDataType=="init"){
		        				$this.renderGridpanelData(rowsdata);
	        				}else{
	        					$this.requestLoadData(rowsdata,requestdatacomplete,isRebindPaginator);
	        				}
	        			}
	            	}
        		}
        	}
        	//隐藏loading组件
        	try{
        		$this.element.find(".jazz-pagearea").loading("hide");
        	}catch(e){
        		jazz.log(e);
        	}
        },
        /**
         * @descr 为获取的表格数据设置uuid标识,作为gridpanel操作数据的唯一展示值（rowindex/id--->rowuuid--->data）
         * @param {data} 待加载的表格数据数组
         * @private
         * @example this._setRowUUID();
         */
        _setRowUUID: function(data){
        	var $this = this;
			if(data){
				for(var i=0,len=data.length;i<len;i++){
					data[i]["rowuuid"] = Math.uuid(32);
				}
			}
		},
		/**
		 * @desc 获取gridcolumn设置的key=true的主键列
		 * @return keyColumn 主键列名name
		 * @private
		 * @example this._getGridcolumnKeyCode();
		 */
        _getGridcolumnKeyCode: function(){
        	var $this = this;
        	var keyColumn =null;
        	if($this.gcolumn){
    			var gridcolumn = $this.gcolumn.data("gridcolumn");
    			keyColumn = gridcolumn.keyCode;
			}
        	return keyColumn;
        },
        /**
         *@desc 对待渲染数据进行datarender和数据类型（datatype、dataformat）转换进行处理
         *@param {rowsdata} 待渲染json数据数组
         *@private
         *@example this._processGridpanelData();
         */
        _processGridpanelData: function(rowsdata){
        	//1.按照datatype、dataformat处理数据类型
        	if(this.gcolumn){
    			var gridcolumn = this.gcolumn.data("gridcolumn");
    			cols = gridcolumn.cols;
	            var data=null,columnname="",datatype="",dataformat="";
	            for (var i = 0, rowslen = rowsdata.length; i < rowslen; i++) {
	            	data = rowsdata[i];
		            for (var j = 0, len = cols.length; j < len; j++) {
		            	columnname = cols[j]['columnname'];
		            	datatype = cols[j]['datatype'];
		            	dataformat = cols[j]['dataformat'];
		            	if(datatype && data[columnname]!=undefined){
		            		data[columnname] = jazz.util.parseDataByDataFormat({"cellvalue":data[columnname],"datatype":datatype,"dataformat":dataformat});
		            	}
		            }
	            }
			}
        	//2.进行datarender函数，进行rowsdata数据修改、增加
			this._event("datarender",null,{"data":rowsdata});
        },
        
        renderGridpanelData: function(rowsdata){
        	var $this = this;
			//对待渲染数据进行datarender和数据类型（datatype、dataformat）转换进行处理
		    $this._processGridpanelData(rowsdata);
        	
        	//一、数据渲染表格和卡片
			//1.判断子组件是否创建完成,带创建完成后渲染
			//2.需要循环执行这个过程，settimeout
        	var delay=100,timeout=5000;
			if($this.gtable){
	        	var gridtable = $this.gtable.data('gridtable');
	        	if(gridtable){
		            gridtable.renderGridtableData(rowsdata,true);
	        	}else{
	        		loopReloadData($this.gtable,'gridtable',0);
	        	}
        	}
        	if($this.gcard){
	        	var gridcard = $this.gcard.data('gridcard');
	        	if(gridcard){
		            gridcard.renderGridcardData(rowsdata,true);
	        	}else{
	        		loopReloadData($this.gcard,'gridcard',0);
	        	}
        	}
        	
        	//二、绑定分页条
        	//if($this.options.isshowpaginator||$this.options.isshowpaginator=="true"){
        		if($this.gpaginator){
        			var paginator = $this.gpaginator.data('paginator');
        			var a = $this.paginationInfo;
        			if(paginator){
	            		paginator.updatePage({"page":a['page'],"pagerows":a['pagerows'],"totalrecords": a['totalrows']});
        			}else{
        				loopReloadData($this.gpaginator,'paginator',0);
        			}
        		}
        	//}
        	//三、是否创建可编辑组件，并显式显示可编辑组件
        	/*var isshoweditcell = $this.options.isshoweditcell;
        	if(isshoweditcell){
        		if($this.gtable){
		        	var gridtable = $this.gtable.data('gridtable');
		            gridtable.createEditorCell();
	        	}
        	}*/
        	//四、执行回调dataloadcomplete函数
        	$this._event("dataloadcomplete",null,{"data":$this.rows,"paginationInfo":$this.paginationInfo});
        	
        	//loopReloadData循环校验是否需要渲染gridtable或者gridcard数据
        	function loopReloadData(obj,dataname,count) {
        		var compObject = obj.data(dataname);
        		//组件创建完成
            	if(compObject){
            		if(dataname=="gridtable"){
            			compObject.renderGridtableData(rowsdata,true);
            		}else if(dataname=="gridcard"){
            			compObject.renderGridcardData(rowsdata,true);
            		}else if(dataname=="paginator"){
            			compObject.updatePage({"page":$this.paginationInfo['page'],"pagerows":$this.paginationInfo['pagerows'],"totalrecords": $this.paginationInfo['totalrows']});
            		}
            	}else{
                	if (count * delay <= timeout) {
		                count++;
	                	setTimeout(function() {
		                    loopReloadData(obj,dataname,count);
		                }, delay);
	                }
                }
            }
        },
        _combinedStaticAndUrlValueSet: function(codeColumn){
			if(!codeColumn || !codeColumn.length>0){
				return [];
			}
			var columnname = null;
			var valueset = null;
			var datatype = null;
			var tempArray = [];
			for(var i=0,len=codeColumn.length;i<len;i++){
				var temp = codeColumn[i];
				columnname = temp.columnname;
				valueset = temp.valueset;
				datatype = temp.datatype;
				if(valueset){
					if(valueset.indexOf('[')==0&&valueset.lastIndexOf(']')==valueset.length-1){
						valueset = eval('('+valueset+')');
						tempArray.push({'columnname':columnname,'valueset':'','resultset':valueset,'datasettype':'static','datatype':datatype});
	            	}else {
	            		if(valueset.indexOf('{')!=-1||valueset.indexOf('{')!=-1){
	            			//初步限制url获取数据集数据格式
	            		}else{
		            		tempArray.push({'columnname':columnname,'valueset':valueset,'resultset':null,'datasettype':'url','datatype':datatype});
	            		}
	            	}
				}
			}
			return tempArray;
		},
		/**
		 * @desc gridpanel代码集转换
		 * @param {valuesetColumn} 代码集json数组[{columnname:'',resultset:{}}]
		 * @param {rowsData} 待转换代码集的数据
		 * @private
		 * @example this._code2data(valuesetColumn, rowsData);
		 */
        _code2data : function(valuesetColumn, rowsData){
            //var rows = $.extend(true,[],rowsData);
        	var row = null;
        	var codeColumn=null;
            var columnName="";
            var resultset=null;
            var datatype =null;
            var oldText =null;
            for(var j = 0, len = rowsData.length; j < len; j++ ){
        		row = rowsData[j];
        		for (var i = 0; i < valuesetColumn.length; i++) {
        			codeColumn = valuesetColumn[i];
	            	resultset = codeColumn['resultset'];
	            	if(resultset){
		            	columnName = codeColumn['columnname'];
	            		oldText = row[columnName];
	            		//区别处理下拉框和下拉树
		            	datatype = codeColumn['datatype'];
	            		if(datatype=="comboxfield"){
		            		for(var n=0;n<resultset.length;n++){
		            			if(oldText==resultset[n].value){
		            				row[columnName] = resultset[n].text;
		            			}
		            		}
	            		}else if(datatype=="comboxtreefield"){
	            			for(var n=0;n<resultset.length;n++){
		            			if(oldText==resultset[n].id){
		            				row[columnName] = resultset[n].name;
		            			}
		            		}
	            		}
	            	}
        		}
            }
            
            return rowsData;
        },
        /**
         * @desc 依据代码集渲染表格数据
         * @param {delay}	延时执行时间
         * @param {timeout} 超时时间
         * @param {rowsData} 待代码转名称的表格数据
         * @param {toCodeColumn} 待获取代码集记录数组
         * @param {callback} 转换代码集执行回调
         * @private
         * @example this._code2DataComplete(delay, timeout, rowsData,toCodeColumn,callback)
         */
        _code2DataComplete : function(delay, timeout, rowsData,toCodeColumn,callback) {
            var count = 0,
                $this = this,
                datatranslated;//翻译后的数据
            if(rowsData){
        		codeToDataComplete(toCodeColumn,0);
            }
            
            function codeToDataComplete(valuesetColumn,index) {
            	if(!valuesetColumn || valuesetColumn.length==0){
            		return;
            	}
            	if(index == valuesetColumn.length){
            		datatranslated = $this._code2data(valuesetColumn, rowsData);
            		callback.call($this, datatranslated);
                }else if(index < valuesetColumn.length){
                	var datasettype = valuesetColumn[index]['datasettype'];
                	var resultset = valuesetColumn[index]['resultset'];
                	if(datasettype=="static"){
                		count = 0;
                		index = index +1;
	                    codeToDataComplete(valuesetColumn,index);
                	}else if(datasettype=="url" && resultset){
                		count = 0;
                		index = index +1;
	                    codeToDataComplete(valuesetColumn,index);
                	}else if(datasettype=="url" && !resultset){
		            	var url = valuesetColumn[index]['valueset'];
		                var data = G.getPageDataSetCache(url);
		                if(data && data.status == 'success'){
		                	data = data['data'];
		                	if(data[0]["vtype"] || false){
		                		//dataurl="/bjgs_djgz/dictionary/queryData.do?dicId=CD01" 形式定义
		                		var d = data[0]['data'] || false;
		                		if(d){
		                			data = d;		                			
		                		}else{
		                			data = [];
		                		}
		                	}
		                	//else{ dataurl="[{},{}]"形式定义  }
		                	valuesetColumn[index]['resultset'] = data;
		                    count = 0;
		                    index = index +1;
		                    codeToDataComplete(valuesetColumn,index);
		                }else{
		                	if (count * delay >= timeout) {
			                    count = 0;
			                    index = index +1;
			                    codeToDataComplete(valuesetColumn,index);
			                }else{
				                count++;
			                	setTimeout(function() {
				                    codeToDataComplete(valuesetColumn,index);
				                }, delay);
			                }
		                }
                	}
                }
            }
        },
        /**
         * @desc 获取gridpanel组件数据查询请求参数
         * @return {} object
         * @public
         * @example this.getqueryparams();
         */
        getqueryparams: function(){
        	return this.options.queryparams || {};
        },
        
        /**
         * @desc gridpanel组件根据表单提交内容查询
         * @params {comps} 组件集合
         * @params {fn} 查询回调函数
         * @public
         * @example $('div[name="gridpanel"]').gridpanel('query', ['xxxformpanel'],function(data,paginationinfo){...});
         */
        query: function(comps,fn){
        	if($.isArray(comps)){
        		var a = {};
        		a['url'] = this.options.dataurl;
        		a['components'] = comps;
        		this.options.queryparams = $.DataAdapter.query(a);
    			this._loadGirdpanelData("query",fn);
        	}
        },
        /**
         * @desc gridpanel重新加载数据，返回首页
         * @params {fn} 回调函数 
         * @public
         * @example $('div[name="gridpanel"]').gridpanel('reload',function(data,paginationinfo){...});
         */
        reload: function(data,fn) {
        	this._loadGirdpanelData("reload",fn,data);
        },
        /**
         * @desc gridpanel重新加载当前页数据
         * @params {fn} 回调函数 
         * @public
         * @example $('div[name="gridpanel"]').gridpanel('reloadCurrentPage',function(data,paginationinfo){...});
         */
        reloadCurrentPage: function(fn) {
        	this._loadGirdpanelData("reloadcurrentpage",fn);
        },
        /**
         * @desc gridpanel提供翻页API接口
         * @param {n} 跳转至的页数
         * @example $('div[name="gridpanel"]').gridpanel('goPage',pageIndex);
         */
        goPage: function(n) {
        	if(!n){
        		return false;
        	}
    		this.paginationInfo['page'] = n;
    		this._loadGirdpanelData();
        },
        _loadGirdpanelData: function(t,fn,data){
        	this.rows = null;
        	this.resetQueryDataParams(t);
    		this.requestPageData(t,fn,data);
        },
        /**
         * @desc gridpanel统一分页请求数据
         * @param {falg} 不同逻辑数据获取（查询、重载、排序）的标记
         * @param {fn} 数据重载后执行回调
         * @private
         * @example this.requestPageData('query',flag,requestdatacomplete);
         */
        requestPageData: function (flag,fn,reloadJsonData) {
            var $this = this;
            var url = $this.options.dataurl,
            	urlparams = $this.options.dataurlparams,
            	qparams = $this.options.queryparams,
            	pginfo = $this.paginationInfo, 
            	sparams = $this.options.sortparams;
            	
            //特别处理reload静态数据加载
            if(flag=="reload"){
            	url = reloadJsonData || $this.options.dataurl;
            }
            	
            if (url) {
            	//添加加载缓冲组件
		       	$this.element.find(".jazz-pagearea").loading();
            	
            	if(typeof url =="object"){
            		//dataurl为json静态数据
            		$this.successLoadData("",url,fn,true);
            	}else{
            		//dataurl为字符串地址
		            var data = $.extend({}, pginfo,sparams);
		            var params = {
		            	url: url,
		            	params: urlparams,
		            	pageparams: data,
		            	queryparams: qparams,
		            	showloading: false,
		    	        callback: function(resopnseData,that){
		    	        	var t = (flag!='paginator');
		    	        	that.successLoadData("",resopnseData,fn,t);
		    	        }
		            };    
		            $.DataAdapter.submit(params, $this);
            	}
            }
        },
        
        /**
         * @desc 通过回调函数加载数据，并渲染卡片和表格数据
         * @param {data} 返回数据
         * @param {fn} 重载数据后执行回调
         * @param {flag} 重新更新绑定分页条标识
         * @private
         * @example this.successLoadData(responseText, $this,requestdatacomplete,isRebindPaginator);
         */
        requestLoadData: function(data,fn,flag){
        	var that = this;
        	//一、对待渲染数据进行datarender和数据类型（datatype、dataformat）转换进行处理
		    that._processGridpanelData(data);
        	if(that.gtable){
        		that.gtable.gridtable("renderGridtableData",data);
        	}
			if(that.gcard){
        		that.gcard.gridcard("renderGridcardData",data);
        	}
        	//二、是否创建可编辑组件，并显式显示可编辑组件
        	/*var isshoweditcell = that.options.isshoweditcell;
        	if(isshoweditcell){
        		if(that.gtable){
		        	var gridtable = that.gtable.data('gridtable');
		            gridtable.createEditorCell();
	        	}
        	}*/
        	//reload/query/reloadcurrentpage/重新更新绑定分页条，更新状态
        	//paginator 不再重新绑定更新状态
        	var a = that.paginationInfo;
        	if(flag){
            	if(that.options.isshowpaginator && that.gpaginator){
            		that.gpaginator.paginator("updatePage",{"page":a['page'],"pagerows":a['pagerows'],"totalrecords": a['totalrows']});
            	}
        	}
        	if(fn && $.isFunction(fn)){
	        	fn.call(that,that.rows,a);
        	}
        },
         /**
         * @desc gridpanel获取数据重新设置（dataurl、dataurlparams、sortparams、queryparams）
         * @private
         */
        resetQueryDataParams: function(flag) {
        	switch (flag){
        		case 'reload':
        			this.options.sortparams['sortName']='';
		       		this.options.sortparams['sortFlag']='';
		        	this.options.queryparams = {};
		        	this.paginationInfo['page'] = 1;
		        	this.paginationInfo['totalrows'] = 0;
	        		if(this.gcolumn){
	        			this.gcolumn.gridcolumn('clearColumnStatus');
	        		}
        			break;
        		case 'reloadcurrentpage':
        			this.paginationInfo['totalrows'] = 0;
        			break;
        		case 'query':
        			this.options.sortparams['sortName']='';
		       		this.options.sortparams['sortFlag']='';
		        	this.paginationInfo['page'] = 1;
		        	this.paginationInfo['totalrows'] = 0;
	        		if(this.gcolumn){
	        			this.gcolumn.gridcolumn('clearColumnStatus');
	        		}
        			break;
        		case 'paginator':
        			this.options.sortparams['sortName']='';
		       		this.options.sortparams['sortFlag']='';
	        		if(this.gcolumn){
	        			this.gcolumn.gridcolumn('clearColumnStatus');
	        		}
        			break;
        	}
        },
        /**
         * @desc gridpanel绑定paginator分页点击响应事件
         * @param {page} 页码
         * @param {pagerows} 当前页显示条数
         * @private
         * @example this.successLoadData(page,pagerows);
         */
        bindPaginatorClickEvent: function(page,pagerows){
        	this.paginationInfo['page'] = page;
        	this.paginationInfo['pagerows'] = pagerows;
    		this.resetQueryDataParams("paginator");
	        this.requestPageData("paginator");
        },
        
        /**
         * @desc 动态修改gridpanel组件options属性
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         */
        _setOption: function(key, value){
        	//1.通过修改options的dataurl/dataurlparams/queryparams/sortparams
        	//结合gopage方法就可以实现gridpanel更新数据
        	switch(key){
	        	case 'dataurl':
	        		this.paginationInfo['totalrows'] = 0;
	        		break;
	        	case 'dataurlparams':
	        		this.paginationInfo['totalrows'] = 0;
	        		break;
	        	case 'queryparams':
	        		this.paginationInfo['totalrows'] = 0;
	        		break;
        	}
        	this._super(key, value);
        }
        
    });
})(jQuery);


(function ($) {
	/**
	 * @version 1.0
	 * @name jazz.gridcolumn
	 * @description 表格头类
	 * @constructor
	 * @extends jazz.boxComponent
	 */
	$.widget("jazz.gridcolumn", $.jazz.boxComponent,  {
		
			options:/** @lends jazz.gridcolumn# */ {
				/**
	        	 * @desc 组件类型
	        	 * @type 'String'
	        	 */
				vtype: "gridcolumn",
	        	/**
	             * @type String
	             * @desc gridcolumn组件标识名称
	             * @default null
				 */
            	name: null,
            	/**
	             * @type boolean
	             * @desc 表格列是否可以拖动改变宽度
	             * @default false
				 */
            	resizecolumn: true,
            	/**
	             * @type boolean
	             * @desc 表头是否可以折行
	             * @default false
				 */
            	wordbreak: false,
            	/**
	             * @type boolean
	             * @desc 是否隐藏girdpanel表头
	             * @default false
				 */
            	hideheader: false,
            	/**
	             * @type String
	             * @deprecated
	             * @desc gridcolumn组件表格头列定义（该属性已过期，请使用columnheader）
	             * @default null
				 */
            	content: null,
            	/**
	             * @type String
	             * @desc gridcolumn组件表格头列定义
	             * @default null
				 */
            	columnheader: null
			},
	
			_create: function(){
				this.options.columnheader = this.options.content || "";
				this.options.content = null;
				this._super();
				
				//1.设置gridcolumn样式
				this.compId  = this.getCompId();
				var el = this.element;
				el.attr("id",this.compId+'_gridcolumn').addClass("jazz-gridcolumn").css({'width':'100%', 'overflow': 'hidden'});
				//1.缓存gridcolumn定义options内容
				if(el.children()&&el.children().length>0){
	            	this.tempcolumnheader = el.children();
	            	el.children().remove();
				}else{
					if(this.options["columnheader"]){
						this.tempcolumnheader = $(this.options["columnheader"]);
					}
				}
				//2.生成girdcolumn dom结构
				var tableclass = "jazz-grid-column-table";
				if(this.options.wordbreak||this.options.wordbreak=="true"){
					tableclass += " jazz-grid-column-table-wordbreak";
				}
				var div = '<div id="'+this.compId+'_columns" class="jazz-grid-columns" style="display: block;">'
					    + '<table id="'+this.compId+'_table" class="'+tableclass+'" cellspacing="0" cellpadding="0" border="0">'
					    + '<thead><tr><th style="width:0px;height:0px;"></th></tr></thead><tbody></tbody></table></div>';
				el.append(div);
				this.columns = $('#'+this.compId+'_columns');
				this.table = $('#'+this.compId+'_table');
				this.tbody = this.table.children('tbody');				
				this.thead = this.table.children('thead');//使用thead控制表格各列宽度
			},
			
			_init: function(){
				this._super();
				//解析生成gridcolumn options
				this._createGridColumnOptions();
				this._createColumns();
				//调用全选表格
				this._selectAllTableRows();
	            //调整表格的宽度
	            if (this.options.resizecolumn) {
	                this._bindMoveColumnEvent();
	            }
	            //sort排序事件
				this._bindColumnSortEvent();
				//重新计算splitter的高度
				//注意当拖动改变宽度的时候，需要再次计算赋值
				this._calculateColumnSplitterHeight();
				
				if(this.options.hideheader==true||this.options.hideheader=="true"){
					this.element.hide();
				}
			},
			_createGridColumnOptions: function(){
				//1. 注意：this.getParentComponent()方法获取父组件必须在_init方法中使用
				// 因为，对于复合组件而言，子组件在执行完_create()方法后，才添加到vtypetree中的
				this.options.lineno = true;
				this.options.linenowidth = 30;
				this.options.isshowselecthelper= true;
				this.options.selecttype = 2;
				
				var vtypeparent = this.getParentComponent();
				if(vtypeparent&&vtypeparent.attr("vtype")=="gridpanel"){
					this.gridpanel = $(vtypeparent).data('gridpanel');
					var gridoptions = this.gridpanel.options;
					
					this.options.lineno = gridoptions.lineno;
					this.options.linenowidth =  gridoptions.linenowidth;
					this.options.isshowselecthelper=  gridoptions.isshowselecthelper;
					this.options.selecttype =  gridoptions.selecttype;
				}
				//2.解析gridcolumn表格头元素headers
				if(this.tempcolumnheader){
					var headers = [];
	            	$.each(this.tempcolumnheader, function(i, content){
	            		var t = [], str = '';
	            		$.each($(content).children(), function(j, objs){
	            			var obj = $(objs);
	            			str = {text: obj.attr("text"), textalign: obj.attr("textalign"), name: obj.attr("name")
	            				  ,visible: obj.attr("visible"), width: obj.attr("width")
	            				  ,datatype: obj.attr("datatype"), dataformat: obj.attr("dataformat")
	            				  ,textdisplaytype: obj.attr("textdisplaytype"),rowspan: obj.attr("rowspan")
	            				  ,valueset: obj.attr("dataurl"), key: obj.attr("key"), colspan: obj.attr("colspan")
	            				  ,sort: obj.attr("sort"),iseditable: obj.attr("iseditable")
	            				  ,issummary: obj.attr("issummary"),summarytype: obj.attr("summarytype"),summaryrender: obj.attr("summaryrender")
	            				  };
	            			t.push(str);
	            		});
	            		headers.push(t);
	            	});
	            	this.options.header = headers;
				}
			},
			/**
			 * @desc 重新计算设置splitter的高度，解决splitter不同浏览器下height：100%问题
			 * @private
			 */
			_calculateColumnSplitterHeight: function(){
				var splitterObj = this.tbody.find(".jazz-grid-column-splitter");
				$.each(splitterObj, function(i, obj) {
					var tdHeight = $(obj).parents('.jazz-grid-headerCell').height()||24;
                    $(obj).height(tdHeight);
                });
			},
			
			/**
			 * @desc 创建columns
			 * @private
			 */
			_createColumns: function(){
				var lineno = this.options.lineno;
				var isshowselecthelper = this.options.isshowselecthelper;
				var selecttype = this.options.selecttype;
				var header2='', colIndex = 0;
	            
	            this.columnWidth = []; //记录每一列的宽度
	            this.cols = []; //存储td显示的有效列名称,以后gridtable中使用到的列属性都加到这里面
	            this.toCodeColumn = [];//存储代码集列[{columnName:'',valueset:''}]
		       	
		        var headers = this.options.header, 
		            headrows = headers.length;  //表头共有几行
		            
		        var nums = 0;
		        if(this.options.lineno == true || this.options.lineno == 'true'){ //序号列
        			nums += 1;
        		}
        		if((this.options.isshowselecthelper == true || this.options.isshowselecthelper == 'true')&& 
        				(this.options.selecttype != 0 || this.options.selecttype != '0')){
        			nums += 1;
        		}
        		
		        var cellColumnIndexArray = this._getCellColumnIndexArray();
		       	
		       	
			    var headcell = null,cover = null,colindex = null,celltype = null,celldata = null;
			    for (var i = 0; i < cellColumnIndexArray.length; i++) {
			    	header2 += '<tr><th></th>';
			    	
			    	//遍历cellColumnIndexArray每一个数组元素，即遍历生成每一表头行
			    	for(var j=0,len=cellColumnIndexArray[i].length;j<len;j++){
			    		headcell = cellColumnIndexArray[i][j];
			    		cover = headcell['cover'];
			    		colindex = headcell['colindex'];
			    		celltype = headcell['celltype'];
			    		celldata = headcell['celldata'];
			    		
			    		if(cover=='combined'){
			    			continue;
			    		}
			    		if(celltype){//表格头特殊列；序号列、checkbox列或者radio列
			        		if(celltype=='lineno'){
			        			header2 += '<td index="'+colindex+'" class="jazz-grid-headerCell" rowspan="'+headrows+'">'
		        				    + '<div class="jazz-grid-headerCell-inner" style="text-align:center;">序号</div></td>';
		        				    
		        				/*header2 +='<td index="'+colindex+'" class="jazz-grid-headerCell" rowspan="'+headrows+'">'
		        				    + '<div class="jazz-grid-headerCell-outer">'
		        				    + '<div class="jazz-grid-headerCell-inner">序号</div><div class="jazz-grid-column-splitter"></div></td>';*/
			        		}
			        		if(celltype=='select'){
			        			if(selecttype == 2 || selecttype == '2'){
			        				header2 += '<td index="'+colindex+'" class="jazz-grid-headerCell" rowspan="'+headrows+'">'
			        					    +'<div class="jazz-grid-headerCell-box">'
			        					    +'<input type="checkbox" name="'+this.compId+'_box" id="'+this.compId+'_box" />'
			        					    +'</div>'
			        					    +'</td>';
			        			}else{
			        				header2 += '<td class="jazz-grid-headerCell">&nbsp;</td>';
			        			}
			        		}
			    		}else{
			    			if(!celldata){
			    				continue;
			    			}
			            	
			            	if(celldata.name){
				            	
			            		//存储key==true
				            	if(celldata.key == true || celldata.key == 'true'){
				            		this.keyCode = celldata.name.replace(/^\s+(\w+)\s+$/g, "$1");
				            	}
				            	//存储需要显示在td上的列的字段名,并按显示列排序，以便gridtable加载数据使用
			                	this.cols[colindex-nums] ={'columnname':celldata.name,'sort':celldata.sort,'index':colindex,
			                		'datatype':celldata.datatype,'dataformat':celldata.dataformat,'textalign':celldata.textalign,
			                		'iseditable':celldata.iseditable,
			                		'issummary': celldata.issummary,'summarytype': celldata.summarytype,'summaryrender': celldata.summaryrender};
			            		//this.cols.push({'columnname':celldata.name,'sort':celldata.sort,'index':colindex,'columnrender':celldata.columnrender});
			                	//存储需要代码集列
			                	this.toCodeColumn.push({'columnname':celldata.name,'valueset':celldata.valueset,'datatype':celldata.datatype});
			                	
			                	//进行每列定义的宽度值，此处不进行实际px值转换
			                	var width = celldata.width,visible = celldata.visible;
			                	this.columnWidth[colindex-nums]={'columnname':celldata.name,'columnwidth':celldata.width,'visible':visible};
			            	}
			                
			                //if (celldata.visible != false && celldata.visible != "false"){
			                	var _colspan = '', _rowspan = '', _align = '',_bottomclass='',_name='',_text=''; 
			                	if(celldata.rowspan){
			                		_rowspan = ' rowspan="'+celldata.rowspan+'"';
			                	}
			                	if(celldata.colspan){
			                		_colspan = ' colspan="'+celldata.colspan+'"'; 
			                	}
			                	if(celldata.textalign){
			                		_align = ' style="text-align: '+celldata.textalign+'"';
			                	}
			                	if(celldata.text){
			                		_text = celldata.text;
			                	}
			                	if(celldata.name){
			                		_bottomclass = ' jazz-grid-bottomCell';
			                		_name = celldata.name;
			                	}
		            			header2 +='<td index="'+colindex+'" name="'+_name+'" class="jazz-grid-headerCell'+_bottomclass+'" '+_rowspan+_colspan+'>'
		        				    + '<div class="jazz-grid-headerCell-outer">'
		        				   // + '<div class="jazz-grid-headerCell-inner" '+_align+'>'+ _text+ '</div><div class="jazz-grid-column-splitter"></div></td>';
		        				    /**
		        				     * 去掉原<td>内部的div
		        				     */
		        				    + '<div class="jazz-grid-headerCell-inner" '+_align+'>'+ _text+ '</div><div class="jazz-grid-column-splitter"></div></td>';
			                //}
			    		}
			    	}
			    	header2 += '</tr>';
			    }
			    //当gridpanel在隐藏元素中时，headerWidth的宽度为0，这样表格和卡片就不能正常展开计算宽度
				//var headerWidth = this.columns.width();
			    //根据预定义的列宽，计算每列宽度。缓存自定义宽度值和实际计算值this.columnWidth
			    this.calculateGridcolumnWidth();
			    //生成thead DOM结构,只生成dom暂时宽度不赋值
			    this._createTheadDom();
			    //为表格总宽度和各列宽度赋值
			    this.settingGridcolumnWidth();
			    
				this.table.append(header2);	
			},
			/**
			 * @desc 根据预定义的列宽，计算每列宽度。缓存自定义宽度值和实际计算值this.columnWidth
			 * @private
			 * @example this.calculateGridcolumnWidth();
			 */
			calculateGridcolumnWidth: function(){
				var width_table = this.columns.width();
				//除去序号和选择框列宽度，再进行分配表格列宽
				if((this.options.isshowselecthelper == true || this.options.isshowselecthelper == 'true') 
	            	&& (this.options.selecttype != 0 || this.options.selecttype != '0')){
                    width_table -= 24;
	            }	
				if(this.options.lineno == true || this.options.lineno == 'true'){
					var linowidth = this.options.linenowidth?parseInt(this.options.linenowidth):30;
					width_table -= linowidth;
				}
	        	var re = /^[0-9]+.?[0-9]*$/;
	        	var fixedColumnWidth = 0;
	        	var exceptFixedColumnWidth = 0;
	        	var percentColumnWidth = 0;
	        	var hasPercentWidth = false;
	        	var autoWidthNums = 0;
				
				for(var i=0,m=this.columnWidth.length; i<m; i++){
	        		var temp = this.columnWidth[i];
	        		var colwidth = temp['columnwidth'];
	        		
	        		if(!colwidth || $.trim(colwidth) == '*'){
	        			autoWidthNums++;
	        		}else{
		        		if(colwidth.indexOf('px')!=-1||(re.test(colwidth)&&colwidth.indexOf('%')==-1)){
		        			fixedColumnWidth += parseFloat(colwidth);
		        			//记录实际值
		        			temp['_columnwidth'] = parseFloat(colwidth);
		        		}
		        		if(!hasPercentWidth&&colwidth.indexOf('%')!=-1){
		        			hasPercentWidth = true;
		        		}
	        		}
	        	}
	        	exceptFixedColumnWidth = width_table-fixedColumnWidth;
				
				if(hasPercentWidth){
	        		//exceptFixedColumnWidth = width_table-fixedColumnWidth;
					//循环计算百分比定义的列宽
					for(var i=0,m=this.columnWidth.length; i<m; i++){
		        		var temp = this.columnWidth[i];
		        		var colwidth = temp['columnwidth'];
		        		if(colwidth&&colwidth.indexOf('%')!=-1){
		        			var a = colwidth.substring(0, colwidth.indexOf('%'));
		        			var b = Math.round((a/100)*exceptFixedColumnWidth);
		        			//记录实际值
	        				temp['_columnwidth'] = b;
			        		//temp['_columnwidth'] = b+"px";//将原%数值改为px数值
			        		percentColumnWidth += b;
		        		}
		        	}
	        	}
	        	
	        	if(autoWidthNums>0){
	        		var leftColumnWidth = exceptFixedColumnWidth - percentColumnWidth;
	        		for(var i=0,m=this.columnWidth.length; i<m; i++){
		        		var temp = this.columnWidth[i];
		        		var colwidth = temp['columnwidth'];
		        		if(!colwidth || $.trim(colwidth) == '*'){
		        			//记录实际值
	        				temp['_columnwidth'] = Math.round(leftColumnWidth/autoWidthNums);
		        		}
		        	}
	        	}
			},
			/**
			 * @desc 生成colgroup DOM结构,只生成dom暂时宽度不赋值
			 * @private 
			 */
			_createTheadDom: function(){
				//2.生成thead DOM结构，以控制表格列宽度
				var linowidth="";
				var thead_th="";
	            if((this.options.isshowselecthelper == true || this.options.isshowselecthelper == 'true') 
	            	&& (this.options.selecttype != 0 || this.options.selecttype != '0')){
	            	thead_th += '<th style="width:24px;height:0px;" name="selecthelper"></th>';// class="jazz-grid-colgroup-col"
	            }	
				if(this.options.lineno == true || this.options.lineno == 'true'){
					linowidth = this.options.linenowidth?parseInt(this.options.linenowidth)+"px":"30px";
					thead_th += '<th style="width:'+linowidth+';height:0px;" name="rowlineno"></th>';//class="jazz-grid-colgroup-col" 
				}
	        	for(var i=0, len=this.columnWidth.length; i<len; i++){
			    	var temp = this.columnWidth[i];
			    	//var visible = temp['visible'];
			    	var colname = temp['columnname'];
			    	thead_th += '<th style="height:0px;" name="'+colname+'"></th>';//class="jazz-grid-colgroup-col" 
			    }
			    this.thead.find('tr').append(thead_th);
			},
			/**
			 * @desc 为表格总宽度和各列宽度赋值
			 * @private
			 */
			settingGridcolumnWidth: function(){
				//3.为表格总宽度和各列宽度赋值
				this.tableWidth = 0,linowidth=0;
			    var columnAllwidth = this.columns.width() - jazz.scrollWidth;
	            if((this.options.isshowselecthelper == true || this.options.isshowselecthelper == 'true') 
	            	&& (this.options.selecttype != 0 || this.options.selecttype != '0')){
                    this.tableWidth += 24;
	            }	
				if(this.options.lineno == true || this.options.lineno == 'true'){
					linowidth = this.options.linenowidth?parseInt(this.options.linenowidth):30;
					this.tableWidth += linowidth;
				}
				for(var i=0, len=this.columnWidth.length; i<len; i++){
			    	var temp = this.columnWidth[i];
			    	var width = temp['_columnwidth'];//计算后的实际宽度
			    	var visible = temp['visible'];
			    	var colname = temp['columnname'];
			    	if(visible==false||visible=="false"){
			    		width = 0;
			    	}
			    	//表格的宽度是否需要根据visible属性发生变化
			    	this.tableWidth += width;
			    	this.thead.find('th[name="'+colname+'"]').css("width",width+"px");
			    }
			    
			    /**
			     * <table></table>样式类里设置width:0px; 
			     * <thead>中的
			     * <tr><td style="width:0;height:0"></td><td style="border-width: 0px 0px 0px 1px;width:100px"></td>……通过改变td中的宽度，控制表格的宽度。
			     */
			    
			    //this.table.width(this.tableWidth);
			},
			_getCellColumnIndexArray: function(){
				var headers = this.options.header, 
		            headrows = headers.length;  //表头共有几行
		       	
		        //将表头转化成m*n的一个二维数组，每个二维数组元素中存放着四个值{colindex:0,cover:false，celltype:null,celldata:null};    
		       	//确定列数
		        var columnnums=0;
		        var haslineno=false,hasselectbox=false;
        		if(this.options.lineno == true || this.options.lineno == 'true'){ //序号列
        			columnnums += 1;
        			haslineno=true;
        		}
        		if((this.options.isshowselecthelper == true || this.options.isshowselecthelper == 'true')&& (this.options.selecttype != 0 || this.options.selecttype != '0')){
        			columnnums += 1;
        			hasselectbox=true;
        		}
		       	for (var m = 0; m < headrows; m++) {
		       		for (var n = 0, len = headers[m].length; n < len; n++) {
		       			if(headers[m][n]['name']){
		       				columnnums=columnnums+1;
		       			}
		       		}
		       	}
		       	var cellColumnIndexArray=[];
		       	for (var m = 0; m < headrows; m++) {
		       		cellColumnIndexArray[m]=[];
		       		for (var n = 0; n < columnnums; n++) {
		       			cellColumnIndexArray[m][n]={colindex:0,cover:false,celldata:null};
		       		}
		       	}
		       	//若是有序号列
		       	if(hasselectbox){
		       		for(var j=0;j<headrows;j++){
		       			if(j==0){
		       				cellColumnIndexArray[j][0]['colindex']=0;
       						cellColumnIndexArray[j][0]['cover']=true;
       						cellColumnIndexArray[j][0]['celltype']='select';
		       			}else{
	       					cellColumnIndexArray[j][0]['cover']='combined';
		       			}
       				}
		       	}
		       	//若是复选或单选选择列
		       	if(haslineno&&hasselectbox){
		       		for(var j=0;j<headrows;j++){
		       			if(j==0){
			       			cellColumnIndexArray[j][1]['colindex']=1;
	       					cellColumnIndexArray[j][1]['cover']=true;
	       					cellColumnIndexArray[j][1]['celltype']='lineno';
		       			}else{
	       					cellColumnIndexArray[j][1]['cover']='combined';
		       			}
       				}
		       	}else if(!hasselectbox&&haslineno){
		       		for(var j=0;j<headrows;j++){
       					if(j==0){
			       			cellColumnIndexArray[j][0]['colindex']=0;
	       					cellColumnIndexArray[j][0]['cover']=true;
	       					cellColumnIndexArray[j][0]['celltype']='lineno';
		       			}else{
	       					cellColumnIndexArray[j][0]['cover']='combined';
		       			}
       				}
		       	}
		        //确定单元格最终列坐标
		       	for (var m = 0; m < headrows; m++) {
		       		for (var n = 0, len = headers[m].length; n < len; n++) {
		       			var obj = headers[m][n];
		       			var rowspan = parseInt(obj['rowspan'])||1;
		       			var colspan = parseInt(obj['colspan'])||1;
		       			
		       			//遍历cellColumnIndexArray[m]确定cover：false的格子，即为td的坐标
		       			var positionIndex=0;
		       			for(var k=0;k<cellColumnIndexArray[m].length;k++){
		       				if(cellColumnIndexArray[m][k]['cover']==false){
		       					positionIndex=k;
		       					cellColumnIndexArray[m][k]['cover'] = true;
		       					cellColumnIndexArray[m][k]['colindex'] = k+colspan-1;
		       					cellColumnIndexArray[m][k]['celldata'] = obj;
		       					break;
		       				}
		       			}
		       			
		       			//kaolv当是visible=false的情况，他没有rowspan、colspan的时候
		       			
		       			if(rowspan>1){
		       				for(var j=m+1;j<m+rowspan;j++){
		       					cellColumnIndexArray[j][positionIndex]['cover']='combined';
		       				}
		       			}
		       			
		       			if(colspan>1){
		       				for(var j=positionIndex+1;j<positionIndex+colspan;j++){
		       					cellColumnIndexArray[m][j]['cover']='combined';
		       				}
		       			}
		       		}
		       	}
		       	return cellColumnIndexArray;
			},
			
			/**
			 * @desc 选中表格的全部记录
			 */
			_selectAllTableRows: function(){
				var $this = this;
				$('#'+this.compId+'_box').off('click.box').on('click.box', function(){
					if($this.gridpanel){
						if($(this).prop('checked')){
							$this.gridpanel.selectAllRows();
						}else{
							$this.gridpanel.unselectAllRows();
						}
					}
				});
			},	
			
			/**
			 * @desc 列头移动事件
			 */
			_bindMoveColumnEvent: function(){
				var $this = this, $tbody = this.tbody.find('td');
				$tbody.off('mousedown.splitter').on('mousedown.splitter', function(e){
					  var target = e.target, $target = $(target);
					  //点击到拖动区域时，出现拖动线
					  if($target.is('.jazz-grid-column-splitter')){
						    //当前的td对象
						    $this.spliderTd = $target.closest('td');
						    $this.x1 = e.pageX;
						    
						    //计算拖拽条的高度
						    var splitHeight = $this.columns.height() + $this._getTableObj().element.height();
						    
						    $this.grid_proxy = $('<div class="jazz-grid-proxy" style="display:none"></div>').appendTo(document.body);
						    $this.grid_proxy.css({display: 'block', top: $this.columns.offset().top, left: $this.x1, height: splitHeight});
						    if (!$.browser.mozilla) {
			                    $(document).on("selectstart", function(){return false; });
			                }else{
			                    $("body").css("-moz-user-select", "none");
			                }

							$(document).off('mousemove.splitter').on('mousemove.splitter', function(e){
								if($this.grid_proxy){
									$this.x2 = e.pageX;
									$this.grid_proxy.css({top: $this.columns.offset().top, left: $this.x2, height: splitHeight});
								}
							});
							
							$(document).off('mouseup.splitter').on('mouseup.splitter', function(e){
								if($this.grid_proxy){
									$this.grid_proxy.remove();
									var w = 0;
									if($this.x2 != 0){
										w = $this.x2 - $this.x1;   //移动距离
									}
									
									$this.x2 = 0; //计算后，将移动距离清零
									var index = $this.spliderTd.attr('index');     //列索引 
							        var obj = $this.thead.find("th").eq((parseInt(index)+1)); //列th对象
							        
							        var tableWidth = $this.table.width();          //表宽度
							        var colWidth = obj.width();                    //获取索引列宽度
							        var newColWidth = colWidth+w;
							        
							        //新的列跨度不小,如果小于给默认值
							        var dfWidth = 30;
							        if(newColWidth < dfWidth){
							        	newColWidth = dfWidth;
							        	w = -(colWidth - newColWidth);
							        	$this.grid_proxy.css({top: $this.columns.offset().top, left: $this.x1 + w, height: splitHeight});
							        }
							        
							        obj.width(newColWidth);
							        var newTableWidth = tableWidth + w;
							        //$this.table.width(newTableWidth);

							        var tableObj = $this._getTableObj();
							        var childrenObj = tableObj.thead.find('th').eq((parseInt(index)+1));
							        childrenObj.width(newColWidth);
							        //tableObj.table.width(newTableWidth);
								}
								
				                if (!$.browser.mozilla) {
				                    $(document).off("selectstart");
				                }else{
				                    $("body").css("-moz-user-select", "auto");
				                }
				                
				                $(document).off('mousemove.splitter mouseup.splitter');
				                
				                //拖动完毕后，修改splitter的高度
				                $this._calculateColumnSplitterHeight();
							});							
							
					  }
				});
			},
			_bindColumnSortEvent: function(){
				var $this = this;
				//只为class包含jazz-grid-bottomCell
				var el = this.tbody.find('td.jazz-grid-bottomCell');
				el.off('click.sortcell').on('click.sortcell',function(e){
					var colindex = $(this).attr('index');
					var colname = $(this).attr('name');
					var sortFlag='asc';
					var sortable=false;
					if(!colname){
						return;
					}
					for(var i=0,len=$this.cols.length;i<len;i++){
						if(colname==$this.cols[i]['columnname']){
							sortable = $this.cols[i]['sort']==true||$this.cols[i]['sort']=='true';
							break;
						}
					}
					if(sortable){
						el.filter("[name!="+colname+"]").removeClass('jazz-grid-asc jazz-grid-desc').find('span.jazz-grid-sortIcon').remove();
						if($(this).find('span.jazz-grid-sortIcon').length==0){
							$(this).find('div.jazz-grid-headerCell-inner').append('<span class="jazz-grid-sortIcon"></span>');
						}
						if($(this).hasClass('jazz-grid-asc')){
							$(this).removeClass('jazz-grid-asc').addClass('jazz-grid-desc');
							sortFlag='desc';
						}else if($(this).hasClass('jazz-grid-desc')){
							$(this).removeClass('jazz-grid-desc').addClass('jazz-grid-asc');
						}else{
							$(this).addClass('jazz-grid-asc');
						}
						//3.在绑定函数中调用gridtable的刷新数据的方法
						if(colname){
							//1.同步gridpanel的sortparams
							$this.gridpanel.options.sortparams["sortName"] = colname;
							$this.gridpanel.options.sortparams["sortFlag"] = sortFlag;
							//2.调用gridpanel请求新数据
							$this.gridpanel.requestPageData();
						}
					}
				});
			},
			
			_getTableObj: function(){
				if(!this.gridTableObj){
					var grid = this.getParentComponent();
			        if(grid){
						if(!grid.gridTableObj){
							var t = $(grid)['gridpanel']('getGridTable');
							grid.gridTableObj = t.data('gridtable');
						}
			        }
			        this.gridTableObj = grid.gridTableObj;
				}
		        return this.gridTableObj;
			},
			hideColumn: function(columnname){
				if(!columnname){return;}
				var el = this.thead.find('th[name="'+columnname+'"]');
				if(el.length>0){
					el.css('width','0px');
					var gridTableObj = this._getTableObj();
					if(gridTableObj){
						$(gridTableObj['thead']).find('th[name="'+columnname+'"]').css('width','0px');
					}
				}
			},
			showColumn: function(columnname){
				if(!columnname){return;}
				for(var i=0, len=this.columnWidth.length; i<len; i++){
			    	var temp = this.columnWidth[i];
			    	var width = temp['_columnwidth'];//计算后的实际宽度
			    	var colname = temp['columnname'];
			    	if(colname==columnname){
			    		this.thead.find('th[name="'+colname+'"]').css("width",width+"px");
			    		var gridTableObj = this._getTableObj();
						if(gridTableObj){
							$(gridTableObj['thead']).find('th[name="'+colname+'"]').css("width",width+"px");
						}
			    	}
			    }
			},
			clearColumnStatus: function(){
				var $this = this;
				//只为class包含jazz-grid-bottomCell
				var el = this.tbody.find('td.jazz-grid-bottomCell');
				if(el.length>0){
					el.removeClass('jazz-grid-asc jazz-grid-desc').find('span.jazz-grid-sortIcon').remove();
				}
				var ckbox = this.tbody.find("input:checkbox");
				if(ckbox.length>0){
		        	ckbox.attr("checked", false);
	        	}
			}
		
	});
	
})(jQuery);

(function ($) {
	/**
	 * @version 1.0
	 * @name jazz.gridtable
	 * @description 表格内容类
	 * @constructor
	 * @extends jazz.boxComponent
	 */
	$.widget("jazz.gridtable", $.jazz.boxComponent, {
		options: {
			/**
			 * @type 'String'
			 * @desc 组件类型
			 */
			vtype: "gridtable",
			/**
			 * @type String
			 * @desc gridtable组件标识名称
			 * @default null
			 */
        	name: null,
        	/**
			 * @type object
			 * @desc 自定义行渲染函数
			 * @param {event} 事件载体
			 * @param {data} 渲染表格数据data和表格行元素，为json格式{"data":rows,"rowEl",trobjs}
			 * @default null
			 */
        	rowrender: null,
        	/**
			 * @type boolean
			 * @desc 当数据条数小于pagerows时，是否补充空行
			 * @default false
			 */
        	isfixrow: false,
        	/**
			 * @type string
			 * @desc 新增行插入位置
			 * @default first
			 */
			addrowposition: "first",
        	/**
			 * @type object
			 * @desc 行选择事件
			 * @param {event} 事件载体
			 * @param {data} 选中行数据
			 * @default null
			 */
        	rowselect: null,
        	/**
			 * @type object
			 * @desc 行取消选择事件
			 * @param {event} 事件载体
			 * @param {data} 选中行数据
			 * @default null
			 */
        	rowunselect: null,
        	/**
			 * @type object
			 * @desc 行双击事件
			 * @param {event} 事件载体
			 * @param {data} 选中行数据
			 * @default null
			 */
        	rowdblclick: null
		},
		
		/**
		 * @desc 创建组件
		 * @private
		 */
		_create: function(){
			this._super();
			
			this.compId = this.getCompId();
			var el = this.element;
			el.attr('id', this.compId+'_gridtable').addClass('jazz-datatable');
			var div = '<div id="'+this.compId+'_tables" class="jazz-grid-tables" style="display: block;">'
				    + '<table id="'+this.compId+'_table" class="jazz-grid-data-table" cellspacing="0" cellpadding="0" border="0">'
				    + '<thead><tr></tr></thead><tbody></tbody></table></div>';
			el.append(div);
	
			this.columns = $('#'+this.compId+'_tables');
			this.table = $('#'+this.compId+'_table');
			this.thead = this.table.children('thead');//通过thead控制表格各列宽度
			this.tbody = this.table.children('tbody');
		},
		
		/**
		 * @desc 初始化
		 * @private
		 */
		_init: function(){
			this._super();
			
			//1.设置gridtable样式
			this._createGridTableOptions();
			//2.//设置表格线条样式
			if(this.options.linetype&&(this.options.linetype!=0||this.options.linetype!="0")){
				this.table.addClass("jazz-grid-table-linetype"+this.options.linetype);
			}
			if(this.options.linestyle&&(this.options.linestyle!=1||this.options.linestyle!="1")){
				this.table.addClass("jazz-grid-table-linestyle"+this.options.linestyle);
			}
			//3.设置表格宽度
			this.calculateGridtableWidth();
			//4.表格横向滚动事件
			this._bindTableHorizonScrollEvent();
			//5.绑定表格所需要的事件
			//this._bindGridTableSelectionEvent();
			this._bindGridTableEvent();
			
		},
		_bindGridTableEvent: function(){
			/***************************分支处理gridtable要被覆盖处理的特性*********start**********/
			//扩展jazz.gridtable的可编辑表格特性功能
			if(this._isGridtableEditable()){
				//可编辑表格的处理(对相应方法的覆盖)
				$.extend(this,jazz.grid.editorgridtable);
			}
			//扩展jazz.gridtable的分组和合计表格特性功能
			//由于分组中可能会有合计功能，所以在分组时就不再重复绑定合计功能
			if(this.options.groupfield){
				$.extend(this,jazz.grid.groupgridtable);
				this._bindGroupGridTableEvent();
				this._bindSummaryGridTableEvent();
			}else{
				if(this.options.isgroupsummary||this.options.ispagesummary){
					$.extend(this,jazz.grid.summarygridtable);
					this._bindSummaryGridTableEvent();
				}				
			}
			/***************************分支处理gridtable要被覆盖处理的特性*********end**********/
			//绑定表格响应事件
			this._bindGridTableSelectionEvent();
		},
		_isGridtableEditable: function(){
			var iseditable = false;
			if(this.options.iseditable){
				iseditable = true;
			}else {
	            for (var j = 0, len = this.cols.length; j < len; j++) {
	            	if(this.cols[j]['iseditable']){
	            		iseditable = true;
	            		break;
	            	}
	            }
			}
			return iseditable;
		},
		_createGridTableOptions: function(){
			this.options.lineno = true;
			this.options.isshowselecthelper= true;
			this.options.selecttype = 2;
			this.options.linetype = 0;
			this.options.linestyle = 1;
			this.options.isshowpaginator = true;
			this.options.rowselectable = true;
			this.options.isshoweditcell = false;
			this.options.isshoweditcell = false;
			this.options.editortype = "cell";
			this.options.iseditable = false;
			this.options.isgroupsummary = false;
			this.options.ispagesummary = false;
			this.options.groupfield = null;
			this.options.grouptitlefield = null;
			this.options.isgroupexpand = true;
			
			var parentobj = this.getParentComponent();
			if(parentobj&&parentobj.length>0){
				this.gridpanel = $(parentobj).data('gridpanel');
				var gridoptions = this.gridpanel.options;
				
				this.options.lineno = gridoptions.lineno;
				this.options.isshowselecthelper= gridoptions.isshowselecthelper;
				this.options.selecttype = gridoptions.selecttype;
				this.options.linetype = gridoptions.linetype;
				this.options.linestyle = gridoptions.linestyle;
				this.options.isshowpaginator = gridoptions.isshowpaginator;
				this.options.rowselectable = gridoptions.rowselectable;
				this.options.isshoweditcell = gridoptions.isshoweditcell;
				this.options.editortype = gridoptions.editortype;
				this.options.iseditable = gridoptions.iseditable;
				this.options.isgroupsummary = gridoptions.isgroupsummary;
				this.options.ispagesummary = gridoptions.ispagesummary;
				this.options.groupfield = gridoptions.groupfield;
				this.options.grouptitlefield = gridoptions.grouptitlefield;
				this.options.isgroupexpand = gridoptions.isgroupexpand;
				//将gridtable包装到pagearea中，并为pagearea进行fit布局
				var el = this.element;
				var pagearea = parentobj.find(".jazz-pagearea");
				if(pagearea&&pagearea.length>0){
					el.appendTo(pagearea);
					if(parseInt(gridoptions.height)>0||gridoptions.layout=="fit"){
						
		        	}else{
		        		this.columns.css("height","auto");
		        	}
				}else{
					el.wrap('<div class="jazz-pagearea"></div>');
					pagearea = el.parent();
					if(parseInt(gridoptions.height)>0||gridoptions.layout=="fit"){
		        		pagearea.layout({layout: 'fit'});
						pagearea.css({overflow: 'hidden'});
		        	}else{
		        		this.columns.css("height","auto");
		        	}
				}
				//3.获取gridcolumn对象
				this.gridcolumn = parentobj.find("div[vtype=gridcolumn]").data("gridcolumn");
	            this.cols = this.gridcolumn.cols || [];//保存显示列信息名称
			}
		},
		/**
		 * @desc 设置gridtable的宽度
		 * @private
		 */
		calculateGridtableWidth: function(){
			if(this.gridcolumn){
				this.thead.children().remove();
				this.thead.append(this.gridcolumn.thead[0].innerHTML);
				
				/**
				 * this.table设置样式css 中width:0px
				 * 只是通过改变thead中th宽度就可以改变列宽
				 */
				//this.table.width(this.gridcolumn.tableWidth);
            }
		},
		/**
		 * @desc 表格横向滚动条事件（表格和表头一致横向滚动）
		 * @private
		 * @example this._bindTableHorizonScrollEvent();
		 */
		_bindTableHorizonScrollEvent: function(){
			var that = this;
			var el = that.element;
			var gridtables = el.find("div.jazz-grid-tables");
			var gridcolumns = that.gridcolumn.columns;
			gridtables.off("scroll.gridtable").on("scroll.gridtable",function(){
				var scrollwidth = $(this).scrollLeft();
				gridcolumns.css("margin-left",-scrollwidth);
			});
		},
        
        /**
         * @desc 获取当前表格显示数据区域
         * @private
         */
        _drawDataLine : function() {
            var $this = this,
            dataLength = this._trNumber(),
            rowLength = 0,
        	n = 0; //偶行
        	
        	var pageNo=10;//当前每页显示条数
        	if(this.gridpanel&&this.gridpanel.paginationInfo){
        		pageNo = this.gridpanel.paginationInfo["pagerows"]||10;
        	}
            
            if (dataLength < pageNo) {
                rowLength = pageNo - dataLength;
            }
            if (dataLength%2 != 0) {  //用于判断最后一条记录是奇偶行
            	n = 1; //奇行
            }
            var trHtml = "", showLine = this.options.lineno;
            for (var i=0, len=rowLength; i<len; i++) {
                
            	if((n+1+i)%2 === 0){
                	cls = 'jazz-gridtable-even';
                }else{
                	cls = 'jazz-gridtable-odd';
                }
                
                trHtml += '<tr index="'+(dataLength+i)+'" class="nodata '+cls+'">';
                trHtml +='<th style="height:20px;"></th>';
	            if (this._isSelectHelper() && this._isSelectType()) {
	                 /**
	                  * 去掉原<td>内部的div
	                  */
	                 //trHtml += "<td class='jazz-grid-cell'><div class='jazz-grid-cell-inner'>&nbsp;</div></td>";
	            	trHtml += "<td class='jazz-grid-cell'>&nbsp;</td>";
	            }
                if (showLine) {
                    //trHtml += "<td class='jazz-grid-cell'><div class='jazz-grid-cell-inner'>&nbsp;</div></td>";
                	trHtml += "<td class='jazz-grid-cell'>&nbsp;</td>";
                }
                
                for (var j=0, l=this.cols.length; j<l; j++) {
                    //trHtml += "<td class='jazz-grid-cell'><div class='jazz-grid-cell-inner'>&nbsp;</div></td>";
                	trHtml += "<td class='jazz-grid-cell'>&nbsp;</td>";
                }
                trHtml += "</tr>";
            }
            if(rowLength>0){
            	this.tbody.append(trHtml);
            }
        },        
        /**
         * @desc 动态拼接成<tr>……</tr>字符串
         * @param {rowObj}  行对象
         * @param {index}   index从某一整数值递增的值，计算行号
         * @param {lineIndex} 初始行索引
         * @returns HTMl(String)
         */
        _insertRowHtml: function(rowObj, index, lineIndex){
        	var $this = this;
        	var data = rowObj;
        	var lineno = this.options.lineno;//是否显示行号
        	
        	//1.计算行号
        	var page=1;//当前页码
        	var pagerows=0;//当前每页显示条数
        	if(this.gridpanel&&this.gridpanel.paginationInfo){
        		page = this.gridpanel.paginationInfo["page"]||1;
        		pagerows = this.gridpanel.paginationInfo["pagerows"]||0;
        	}
    		lineIndex = index + lineIndex + 1 + ((page-1) * pagerows);
            
    		//2.计算奇偶行
            var cls = '';
            if((lineIndex)%2 == 0){
            	cls = 'jazz-gridtable-even';
            }else{
            	cls = 'jazz-gridtable-odd';
            }
            var rowHtml = '<tr index="'+(lineIndex-1)+'" class="jazz-grid-row '+cls+' " id="'+data["rowuuid"]+'">';
            rowHtml +='<th style="height:20px;"></th>';
            //4.判断是否显示checkbox radio
            var b = '';
            if (this._isSelectHelper() && this._isSelectType()) {
                if ($this._isMultipleSelection()) {
                	b = 'checkbox';
                }else if ($this._isSingleSelection()) {
                	b = 'radio';
                }
                //rowHtml += '<td class="jazz-grid-cell"><div class="jazz-grid-cell-box"><input type="'+b+'"/></div></td>';
                rowHtml += '<td class="jazz-grid-cell jazz-grid-cell-box"><input type="'+b+'"/></td>';
            }
			//5.添加行号，根据当前页码数递增
            if(lineno){
            	rowHtml += '<td class="jazz-grid-cell jazz-grid-cell-no">' + lineIndex + '</td>';
            }
            //6.生成内容td
            var columnname="",stm="",textalign="",title="";
            for (var j = 0, len = this.cols.length; j < len; j++) {
            	columnname = this.cols[j]['columnname'];
            	textalign = this.cols[j]['textalign'] || "";
            	title = "";
            	stm = "&nbsp;";
        		if(columnname&&data[columnname]!=undefined){
        			if(data[columnname].toString().indexOf('<')!=0){
        				title = data[columnname] ||"";
        			}
        			/**
        			 * 去掉原<td>内部的div
        			 */
        			//stm = '<div class="jazz-grid-cell-inner" title='+title+'>'+data[columnname]+'</div>';
        			stm = data[columnname];
        		}
        		if(textalign){
        			textalign = "text-align-"+textalign;
        		}
	            rowHtml += '<td class="jazz-grid-cell '+textalign+'" title='+title+'>'+stm+'</td>';	
            }
            return rowHtml;
        },
        
        /**
         * @desc 判断是否显示checkbox/radio选择框
         * @return {boolean}
         * @private
         */        
        _isSelectHelper: function(){
        	var isshowselecthelper = this.options.isshowselecthelper;
        	return (isshowselecthelper == true || isshowselecthelper == 'true');
        },
        
        /**
         * @desc 判断是否为0
         * @return {boolean}
         * @private
         */        
        _isSelectType: function(){
        	var selecttype = this.options.selecttype;
        	return (selecttype != 0 || selecttype != '0');
        },
        
        /**
         * @desc 判断是否是单选
         * @return {boolean}
         * @private
         */
        _isSingleSelection: function() {
        	var selecttype = this.options.selecttype;
            return (selecttype == 1 || selecttype == '1');
        },        
        
        /**
         * @desc 判断是否是多选
         * @return {boolean}
         * @private
         */
        _isMultipleSelection: function() {
        	var selecttype = this.options.selecttype;
            return (selecttype == 2 || selecttype == '2');
        }, 
        
        /**
         * @desc 加载table数据
         * @param {rows} 待渲染的表格数据
         * @private
         */
        _renderData : function(rows) {
            if(!rows){
                return;
            }
            var lineIndex = 0;
            var rowHtml = '';
            
        	this._trObject().remove();
        	
            lineIndex = this._trNumber();
            for (var i = 0; i < rows.length; i++) {
            	rowHtml += this._insertRowHtml(rows[i], i, lineIndex);
            }
            this.tbody.append(rowHtml);
            
            //渲染数据后，执行rowrender(rowdata,rowElement),提供操作tr回调
            this._event("rowrender",null,{"data":rows,"rowEl":this.tbody.find("tr")});
        },
        /**
         * @desc gridpanel加载数据后调用渲染表格
         * @param {rowsdata} 加载后的数据
         * @private
         */
        renderGridtableData: function(rowsdata){
        	if(rowsdata){
	            this._renderData(rowsdata);
	            this._updateIndex();
        	}
        },
		/**
         * @desc 绑定表格选择事件（单击和双击）
         * @private
         * @example this._bindGridTableSelectionEvent();
         */
        _bindGridTableSelectionEvent: function(){
			var that = this;
			this.tbody.on("click.td", "td.jazz-grid-cell", null, function(e) {
				if($(this).parent().hasClass("nodata")) {return;}
	        	if($(e.target).is('a')){return;}
	        	that._onRowClickEvent(e,$(this),false);
	        }).on("dblclick.td", "td.jazz-grid-cell", null, function(e) {
	        	if($(this).parent().hasClass("nodata")) {return;}
	        	if($(e.target).is('a')){return;}
	        	that._onRowClickEvent(e,$(this),true);
			});
		},
		_onRowClickEvent : function(e,tdEl,dbclick){
			var that = this;
			var targetrow = $(tdEl).parent();
			var rowselectable = that.options.rowselectable || that.options.rowselectable=="true";
			if(rowselectable){
        		if(dbclick){
        			that._onRowClick(e, targetrow, true);//双击
        		}else{
		        	setTimeout(function(){ that._onRowClick(e, targetrow, false); },100);
        		}
        	}else{
        		if($(e.target).is('.jazz-grid-cell-box :input')){
        			that._onRowClick(e, targetrow, false);//选择框勾选都是相当于单击事件
        		}
        	}
		},
		/**
         * @desc 表格点击事件处理，并处理与gridcard卡片交互
         * @param {event} 点击事件event对象
         * @param {targetrow} 表格行或卡片所对应的数据
         * @param {flag} 表明点击是单击还是双击事件,true:双击，false:单击
         * @private
         * @example this._onRowClick();
         */
        _onRowClick: function(event, targetRow, flag) {
            var row = $(targetRow);
            var isSelected = row.hasClass('jazz-row-selected');
            if(this._isMultipleSelection()){
            	if(flag){//双击
            		this._selectRow(row, true, event, flag);
            	}else{//单击
                	//多选，单击效果与checkbox框一致
                	if(isSelected){
                		//取消当前选择行、卡片
                		this._unselectRow(row, true, event);
                	}else{
                		//选中当前选择行、卡片
                		this._selectRow(row, true, event, flag);
                	}
            	}
            }else {
            	if(flag){//双击
            		this._selectRow(row, true, event, flag);
            	}else{//单击
                	//默认处理都是单选，单击效果与radio一致
                	if(isSelected){
                		this._selectRow(row, true, event, flag);
                	}else{
                		//取消其他已选中的行、卡片；然后选中当前行、卡片
                		this._unselectAllRows();
                		this._selectRow(row, true, event, flag);
                	}
            	}
            }
        },
		
        /**
         * @desc 选中指定行数据
         * @param {row} 当前选中表格行
         * @param {isCallback} 是否执行点击卡片回调函数
         * @param {event} event对象
         * @param {flag} 单击或双击标识
         * @private
         * @example this._selectRow(row, isCallback, event, flag)
         */
        _selectRow: function(row, isCallback, event, flag) {
        	var $this = this;
        	var id = row.attr('id');
        	//处理选择行的状态
            row.addClass('jazz-row-selected').attr('aria-selected', true);
            //处理单选和多选两种情况
        	if($this._isMultipleSelection()){
        		if(row.find("input:checkbox")){
	                row.find("input:checkbox").attr('checked', true);
	            }
	            //全选，所有数据行被选中
	            if($this.tbody.find("input:checked").length == $this.tbody.find("input:checkbox").length ){
	            	var thead = $this.gridcolumn.tbody;
	               	thead.find("input:checkbox").attr("checked", true);
	            }
        	}else{
	            if(row.find("input:radio")){
	                row.find("input:radio").attr('checked', true);
	                row.siblings().find("input:radio").attr('checked', false);
	            }
        	}
        	
        	if($this.gridpanel.gcard){
        		var card = $this.gridpanel.gcard.find("#"+id);
        		card.removeClass('jazz-gridtable-hover')
	                .addClass('jazz-gridtable-highlight')
	                .attr('aria-selected', true);
	            if($this._isMultipleSelection()){
	        		if(card.find("input.jazz-card-checkbox")){
		                card.find("input.jazz-card-checkbox").attr('checked', true);
		            }
	        	}else{
		            if(card.find("input.jazz-card-radio")){
		                card.find("input.jazz-card-radio").attr('checked', true);
		                card.siblings().find("input.jazz-card-radio").attr('checked', false);
		            }
	        	}
        	}
        	
        	//处理单击和双击回调函数
        	if(isCallback) {
        		//由gridpanel统一获取this.rows共享数据
        		var data = {};
        		data = this.gridpanel.getSelectedRowDataById(id);
            	if(flag == true){
            		this._event("rowdblclick",event,data);
            	}else{
            		this._event("rowselect",event,data);
            	}
            }
        },
        /**
         * @desc 取消表格行的选中
         * @param {row} 当前选中表格行
         * @param {isCallback} 是否执行点击卡片回调函数
         * @param {event} event对象
         * @private
         * @example this._unselectRow(row, isCallback,event)
         */
        _unselectRow: function(row, isCallback,event) {
        	var $this = this;
            var id = row.attr('id'); 
            row.removeClass('jazz-row-selected').attr('aria-selected', false);
        	var thead = $this.gridcolumn.tbody;
        	
            //只有多选的时候才能取消选中
        	if($this._isMultipleSelection()){
        		if(row.find("input:checkbox")){
	                row.find("input:checkbox").attr('checked', false);
	            }
	            if($this.tbody.find("input:checked").length != $this.tbody.find("input:checkbox").length ){
	               thead.find("input:checkbox").attr("checked", false);
	            }
        	}
        	if($this.gridpanel.gcard){
	        	var card = $this.gridpanel.gcard.find("#"+id);
        		card.removeClass('jazz-gridtable-highlight')
	                .attr('aria-selected', false);
	            if($this._isMultipleSelection()){
	        		if(card.find("input.jazz-card-checkbox")){
		                card.find("input.jazz-card-checkbox").attr('checked', false);
		            }
	        	}else{
		            if(card.find("input.jazz-card-radio")){
		                card.find("input.jazz-card-radio").attr('checked', true);
		                card.siblings().find("input.jazz-card-radio").attr('checked', false);
		            }
	        	}
        	}
        	
        	//处理单击和双击回调函数
        	if(isCallback) {
        		//由gridpanel统一获取this.rows共享数据
        		var data = {};
        		data = this.gridpanel.getSelectedRowDataById(id);
        		this._event("rowunselect",event,data);
            }
        },
        _selectAllRows: function(){
        	this.tbody.find("tr.jazz-grid-row").addClass('jazz-row-selected').attr('aria-selected', true);
        	if(this._isMultipleSelection()){
	        	this.tbody.find("input:checkbox").attr("checked", true);
	        	var thead = this.gridcolumn.tbody;
	        	thead.find("input:checkbox").attr("checked", true);
        	}
        	if(this.gridpanel.gcard){
	        	var gridcard = this.gridpanel.gcard.data("gridcard");
	        	var cardContent = gridcard["cardContent"];
	        	cardContent.find("div.jazz-grid-cardcell").addClass('jazz-gridtable-highlight').attr('aria-selected', true);
	            cardContent.find("input.jazz-card-checkbox").attr('checked', true);
        	}
        	//由gridpanel统一获取this.rows共享数据
		},
        _unselectAllRows: function() {
        	this.tbody.find("tr.jazz-grid-row").removeClass('jazz-row-selected').attr('aria-selected', false);
        	if(this._isMultipleSelection()){
	        	this.tbody.find("input:checkbox").attr("checked", false);
	        	var thead = this.gridcolumn.tbody;
	        	thead.find("input:checkbox").attr("checked", false);
        	}
        	
        	if(this.gridpanel.gcard){
        		var gridcard = this.gridpanel.gcard.data("gridcard");
        		var cardContent = gridcard["cardContent"];
	        	cardContent.find("div.jazz-grid-cardcell").removeClass('jazz-gridtable-highlight').attr('aria-selected', false);
	            cardContent.find("input.jazz-card-checkbox").attr('checked', false);
	            cardContent.find("input.jazz-card-radio").attr('checked', false);
        	}
        	//由gridpanel统一获取this.rows共享数据
        },
		
        /**
         * 返回tr对象
         * @returns
         */
        _trObject: function(){
        	var obj = this.tbody.children("tr");
        	return obj;
        },
        
        /**
         * @desc 返回当前tr的个数
         * @returns
         */
        _trNumber: function(){
        	var obj = this._trObject();
        	if(obj){
        		return obj.length;
        	}else{
        		return 0;
        	}
        },        
        
        /**
         * @desc 修改tr索引
         */
        _updateIndex : function() {
        	if (this.options.lineno) {//如果当前显示行号时，才对行号进行更新
            	var n = 0;
            	if(this.options.isshowpaginator){
            		var p=1;//当前页码
		        	var r=10;//当前每页显示条数
		        	if(this.gridpanel&&this.gridpanel.paginationInfo){
		        		p = this.gridpanel.paginationInfo["page"]||1;
		        		r = this.gridpanel.paginationInfo["pagerows"]||10;
		        	}
            		n = (p-1)*r;
            	}
            	var trObjs = this.tbody.children(".jazz-grid-row");
                $.each(trObjs, function(i, obj) {//this._trObject()
                    $(this).children('td.jazz-grid-cell-no').text(i+1+n);
                });
            }
            //是否补空行
            if (this.options.isfixrow) {
            	this._drawDataLine();
            }
        },
		/**
		 * @desc 添加行记录
		 * @param {rowObj} 行数据对象 {key1: value1, key2: value2, ……}
		 */
        addRow: function(data) {
        	//将新增数据代码转换名称后添加到表格中
        	if(!data){return;}
        	
        	var rowHtml = "";
        	var lineIndex = this._trNumber();
        	for(var i=0;i<data.length;i++){
        		//循环追加tr行数据html
        		rowHtml += this._insertRowHtml(data[i], i, lineIndex);
        	}
        	var trobj = null
        	if(rowHtml){
	        	//this.tbody.append(rowHtml);
	        	trobj = $(rowHtml).appendTo(this.tbody);
        	}
        	//渲染数据后，执行rowrender(rowdata,rowElement),提供操作tr回调
            this._event("rowrender",null,{"data":data,"rowEl":trobj});
        },
        /**
		 * @desc 根据行id更新表格行数据
		 * @param {data} 待更新行数据{}
		 * @param {id} gridtable行id
		 * @example  $('#gridtable').gridtable('updateRowById',data, id);
		 */
	    updateRowById : function(data, id){
	    	if(!data || !id){
	            return;
	        }
	        //定位要修改表格行，替换掉
	        var trobj = this.tbody.find("#"+id);
	        var isselected = trobj.hasClass("jazz-row-selected");
	        
	        var rowIndex = trobj.find(".jazz-grid-cell-no").html();
    		var rowHtml = this._insertRowHtml(data, 0, (parseInt(rowIndex)-1));
    		trobj.replaceWith(rowHtml);
    		//若是该修改行，是被勾选的即jazz-row-selected样式类，可以考虑修改后继续保持选中状态
    		var newtrobj = this.tbody.find("#"+id);
    		if(isselected){
    			newtrobj.addClass('jazz-row-selected').attr('aria-selected', true);
    			newtrobj.find("input:checkbox").attr('checked', true);
    			var thead = this.gridcolumn.tbody;
	        	if(this._isMultipleSelection()){
		            if(this.tbody.find("input:checked").length == this.tbody.find("input:checkbox").length){
		               thead.find("input:checkbox").attr("checked", true);
		            }
	        	}
    		}
    		//渲染数据后，执行rowrender(rowdata,rowElement),提供操作tr回调
            this._event("rowrender",null,{"data":data,"rowEl":newtrobj});
            
            //更新行数据时抛出cellvaluechange.summaryrow事件，修改summary 合计行数据
			if(this.options.isgroupsummary||this.options.ispagesummary){
				this.tbody.trigger("cellvaluechange.summaryrow",[id]);
			}
	    },
        /**
	     * @desc 根据表格行ID删除数据行
	     * @param {id} 行id值
	     * @private 
	     * @example  $('#gridtable').gridtable('removeRowById',id);
	     */
        removeRowById: function(id) {
            this.tbody.find('#'+id).remove();
            //表格行删除的时候，若是全部数据都已被删除，考虑回显表格选中状态
            //例如：处理全选，以后考虑抽取出公共状态处理方法
            if(this.tbody.children().length==0 || this.tbody.find("tr[id]").length==0){
            	if(this._isMultipleSelection()){
		        	var thead = this.gridcolumn.tbody;
		        	thead.find("input:checkbox").attr("checked", false);
	        	}
            }
            this._updateIndex();
            
            //删除行数据时抛出cellvaluechange.summaryrow事件，修改summary 合计行数据
			if(this.options.isgroupsummary||this.options.ispagesummary){
				this.tbody.trigger("cellvaluechange.summaryrow",[id]);
			}
        },
        
		/**
		 * @desc 根据ID选中行(暴露接口方式选中行)
		 * @private
		 * @example 
		 */
        selectRow : function(id){
        	var $this = this;
        	var row = this.tbody.find("#"+id);
        	//处理选择行的状态
            row.addClass('jazz-row-selected').attr('aria-selected', true);
            //处理单选和多选两种情况
            var thead = $this.gridcolumn.tbody;
        	if($this._isMultipleSelection()){
        		if(row.find("input:checkbox")){
	                row.find("input:checkbox").attr('checked', true);
	            }
	            if($this.tbody.find("input:checked").length == $this.tbody.find("input:checkbox").length){
	               thead.find("input:checkbox").attr("checked", true);
	            }
        	}else{
	            if(row.find("input:radio")){
	                row.find("input:radio").attr('checked', true);
	                row.siblings().find("input:radio").attr('checked', false);
	            }
        	}
        },
        /**
		 * @desc 根据ID取消行选中(暴露接口方式取消选中行)
		 * @private
		 * @example
		 */
        unselectRow : function(id){
        	var $this = this;
        	var row = this.tbody.find("#"+id);
            row.removeClass('jazz-row-selected').attr('aria-selected', false);
            
        	var thead = $this.gridcolumn.tbody;
        	
            //只有多选的时候才能取消选中
        	if($this._isMultipleSelection()){
        		if(row.find("input:checkbox")){
	                row.find("input:checkbox").attr('checked', false);
	            }
	            if($this.tbody.find("input:checked").length != $this.tbody.find("input:checkbox").length){
	               thead.find("input:checkbox").attr("checked", false);
	            }
        	}
        },
        /**
		 * @desc 全部选中(暴露接口方式全部选中)
		 * @private
		 * @example 
		 */
        selectAllRows: function(){
			var $this = this; 
			$this.tbody.find("tr.jazz-grid-row").addClass('jazz-row-selected').attr('aria-selected', true);
			if($this._isMultipleSelection()){
				$this.tbody.find("input:checkbox").attr("checked", true);
			}
			var thead = $this.gridcolumn.tbody;
        	thead.find("input:checkbox").attr("checked", true);
		},
        /**
		 * @desc 全部取消选中(暴露接口方式全部取消选中)
		 * @private
		 * @example
		 */
        unselectAllRows: function() {
        	var $this = this;
        	$this.tbody.find("tr.jazz-grid-row").removeClass('jazz-row-selected').attr('aria-selected', false);
        	if($this._isMultipleSelection()){
	        	$this.tbody.find("input:checkbox").attr("checked", false);
        	}
        	var thead = $this.gridcolumn.tbody;
        	thead.find("input:checkbox").attr("checked", false);
        }
	});
	
})(jQuery);

(function ($) {
	/**
	 * @version 1.0
	 * @name jazz.gridcard
	 * @description 卡片类
	 * @constructor
	 * @extends jazz.boxComponent
	 */
	$.widget("jazz.gridcard", $.jazz.boxComponent,  {
		options: /** @lends jazz.gridcard# */ {
        	/**
			 * @type 'String'
			 * @desc 组件类型
			 */
			vtype: "gridcard",
			/**
			 * @type String
			 * @desc gridtable组件标识名称
			 * @default null
			 */
        	name: null,
        	/**
			 * @type number
			 * @desc 卡片宽度
			 * @default 205
			 */
            width: null,
            /**
			 * @type number
			 * @desc 卡片高度
			 * @default 90
			 */
            height: null,
            /**
			 * @type number
			 * @desc 卡片外边距
			 * @default 10
			 */
            margin: "10px",
            /**
			 * @type string
			 * @desc 卡片样式类名
			 * @default jazz-grid-qh-cardcell
			 */
            cardclass: "jazz-grid-qh-cardcell",
            /**
			 * @type object
			 * @desc 自定义卡片渲染函数
			 * @param {data} 行数据rowdata
			 * @default null
			 */
            html: function(data){
			        		var html = '<div style="width:100%;height:100%">'
			        			+ '<div class="jazz-grid-cardcell-i"><div class="jazz-grid-bblb-img"></div></div>'
			        			+ '<div class="jazz-grid-qh-c jazz-grid-bblb-c">'
			        			+ '<div class="jazz-grid-qh-c-h1">'+ data.name +'</div>'
			               	 	+ '<div class="jazz-grid-qh-c-date">' + data.org + '</div>'
			               	 	+ '<div class="jazz-grid-cardcell-title" style="display:none;">'+ data.name +'</div>'
			                	+ '</div></div>';
			        	return html;
			        },
			/**
			 * @type boolean
			 * @desc 是否显示新增卡片
			 * @default true
			 */
			isaddcard: true,
			/**
			 * @type string
			 * @desc 新增卡片放置位置
			 * @default first
			 */
			addcardposition: "first",
			/**
			 * @type object
			 * @desc 新增卡片渲染事件
			 * @default null
			 */
			addcardrender: null,
			/**
			 * @type object
			 * @desc 新增卡片双击事件
			 * @default null
			 */
			addcardclick:null,
			/**
			 * @type object
			 * @desc 卡片选择事件
			 * @param {event} 事件载体
			 * @param {data} 选中卡片数据
			 * @default null
			 */
        	cardselect: null,
        	/**
			 * @type object
			 * @desc 卡片取消选择事件
			 * @param {event} 事件载体
			 * @param {data} 选中卡片数据
			 * @default null
			 */
        	cardunselect: null,
        	/**
			 * @type object
			 * @desc 卡片双击事件
			 * @param {event} 事件载体
			 * @param {data} 选中卡片数据
			 * @default null
			 */
        	carddblclick: null
        },
        _create: function(){
        	this._super();
        	
            this.compId = this.getCompId();
			var el = this.element;
			el.attr('id', this.compId+'_gridcard').addClass('jazz-gridcard');
            this.cardContent = $('<div id="'+ this.compId +'_cardcontent" style="height:100%;"></div>').appendTo(el);
            
            //保存key列的名称
            //this.keyCode = null;
        },
        _init: function(){
        	this._super();
        	
        	//1.准备卡片初始化创建数据
        	this._createGridCardOptions();
			//2.绑定卡片点击事件
            this._bindGridcardSelectionEvent();
            //3.重排卡片视图的卡片布局
            this._calCardStyle();
            this._cardResize();
            //4.是否保留”新增卡片“功能
            if(this._isCardAddBtn()){
                this._addCardNewIcon();
            }
        },
        _createGridCardOptions: function(){
			this.options.isshowselecthelper= true;
			this.options.selecttype = 2;
			this.options.isshowpaginator = true;
			this.options.rowselectable = true;
			
			var parentobj = this.getParentComponent();
			if(parentobj&&parentobj.length>0){
				this.gridpanel = parentobj.data('gridpanel');
				var gridoptions = this.gridpanel.options;
				
				this.options.isshowselecthelper= gridoptions.isshowselecthelper;
				this.options.selecttype = gridoptions.selecttype;
				this.options.isshowpaginator = gridoptions.isshowpaginator;
				this.options.rowselectable = gridoptions.rowselectable;
				//将gridtable包装到pagearea中，并为pagearea进行fit布局
				var el = this.element;
				var pagearea = parentobj.find(".jazz-pagearea");
				if(pagearea&&pagearea.length>0){
					el.appendTo(pagearea);
					if(parseInt(gridoptions.height)>0||gridoptions.layout=="fit"){
		        		
		        	}else{
		        		this.cardContent.css("height","auto");
		        	}
				}else{
					el.wrap('<div class="jazz-pagearea" ></div>');
					pagearea = el.parent();
					if(parseInt(gridoptions.height)>0||gridoptions.layout=="fit"){
		        		pagearea.layout({layout: 'fit'});
						pagearea.css({overflow: 'hidden'});
		        	}else{
		        		this.cardContent.css("height","auto");
		        	}
				}
				if(gridoptions.defaultview=="card"){
	        		pagearea.css({overflow: 'auto'});
	        	}
	            this.gridcolumn = parentobj.find("div[vtype=gridcolumn]").data("gridcolumn");
				this.gridtable = parentobj.find("div[vtype=gridtable]").data("gridtable");
	            if(this.gridcolumn){
	            	this.cols = this.gridcolumn.cols; 
	            }
			}
		},
        /**
         * @desc 当gridpanel包含在隐藏元素内，无法确定宽度时，显示gridpanel需要重新计算卡片宽度
         * @private
         */
        calculateGridcardWidth: function(){
        	this._calCardStyle();
            this._cardResize();
        },
        /**
         * @desc 获取卡片options属性中设置样式
         * @private
         */
        _getcardSizeStyle: function(){
            var style = "";
            var	width = this.options.width,
            	height = this.options.height,
            	margin = this.options.margin || "10px";
            if(width && height){
            	style=" style='width:"+width+";height:"+height+";margin:"+margin+";'";
            }
            return style;
        },
        /**
         * @desc gridpanel加载数据调用渲染卡片
         * @param {rowsdata} 加载的数据
         * @private
         */
        renderGridcardData: function(rowsdata){
        	if(rowsdata){
	            this._renderData(rowsdata);
        	}
        },
        /**
         * @desc  根据rowdata渲染创建卡片
         * @param {rowsData} 渲染卡片数据
         * @private
         * @example this._renderData(rowsData);
         */
        _renderData: function(rowsData){
            var $this = this,
                rows = rowsData;
                
			//1.渲染卡片数据的时候，首先删除之前数据
        	$this.cardContent.children().remove();
        	//2.添加卡片按钮
            if($this._isCardAddBtn() && !$this._hasCardAddBtn()){
                $this._addCardNewIcon();
            }
            //3.数据渲染
            if(rows) {
                //渲染卡片，包含3个部分（内容区、选择框区域、卡片tools区）
                //1.选择框区域
                var functionHtml ="";
	            if($this.options.isshowselecthelper == true || $this.options.isshowselecthelper == 'true'){
                	if($this.options.selecttype=="1"){//单选
                		functionHtml = '<div class="jazz-grid-cardcell-functionarea" ><div class="jazz-grid-cardcell-functionarea-radio"><input class="jazz-card-checkbox" type="radio" style="width:16px;height:16px;" /></div></div>';
                	}else if($this.options.selecttype=="2"){//复选
                		functionHtml = '<div class="jazz-grid-cardcell-functionarea" ><div class="jazz-grid-cardcell-functionarea-checkbox"><input class="jazz-card-checkbox" type="checkbox" style="width:16px;height:16px;" /></div></div>';
                	}
                }
                var cardmodel = $this.options.html;
                var style = $this._getcardSizeStyle();
                var cardclass = $this.options.cardclass;
                var cardHtml = "",cellcontent="";
                for(var i = 0, len = rows.length; i < len; i++) {
                	
                	cardHtml = '<div class="jazz-grid-cardcell '+cardclass+'" '+style+' id="'+rows[i]["rowuuid"]+'">';
                	//cardHtml = '<div class="jazz-grid-cardcell jazz-grid-qh-cardcell" id="'+rows[i]["rowuuid"]+'">';
	            	cellcontent = $this._customopration(cardmodel,rows[i]) || "";
	            	
                    cardHtml = cardHtml + cellcontent;
                    cardHtml = cardHtml + functionHtml;
                    
                    //固定卡片的tools定义区内容row["@toolsopration"]
                    var stm = rows[i]["@toolsopration"];
                    if(stm){
			            stm = "<div class='jazz-grid-cardcell-tools' >"+ stm + "</div>";
		            }else{
		            	stm="";
		            }
                    cardHtml = cardHtml + stm + "</div>" ;
                    $(cardHtml).appendTo($this.cardContent);
                }
            }
        },
        _isCardAddBtn : function(){
            return ((this.options.isaddcard == false)||(this.options.isaddcard == "false") ? false : true );
        },

        _hasCardAddBtn : function(){
            return this.cardContent.find('.jazz-card-new').length == 0 ? false : true;
        },

        _addCardNewIcon : function(){
            var $this = this,
                $cardContent = $this.cardContent,
                addcardposition = $this.options.addcardposition,
                addcardrender = $this.options.addcardrender,
                addcardclick = $this.options.addcardclick;
            
            var cardHtml = "";
            if(addcardrender){
                cardHtml = $this._customopration(addcardrender);
            }else{
                cardHtml = $this._createCardNewIcon();
            }
            if(addcardposition && addcardposition === "first" && ($cardContent.find('div.jazz-grid-cardcell:first').length > 0)){
                $cardContent.find('div.jazz-grid-cardcell:first').before(cardHtml);
            }else{
                $cardContent.append(cardHtml);
            }
            if(addcardclick){
                $cardContent.find('div.jazz-card-new').on('click', function(e){
                	$this._event("addcardclick", e);
                });
            }
        },

        _createCardNewIcon : function(){
            var $this = this;
            if(!$this.cardCell){
                $this._calCardStyle();
            }
           	var cardHeight = $this.cardCell.cardHeight;
          	var style = $this._getcardSizeStyle();
            var cardclass = $this.options.cardclass;
            var addcard = '<div class="jazz-grid-cardcell '+cardclass+'" '+style+'><div class="jazz-card-new" style="height:'+cardHeight+'px;"></div></div>';
            return addcard; 
        },
        /**
         * @desc 计算卡片的位置
         * @private
         * @example this._calCardStyle();
         */
        _calCardStyle: function(){
            var $this = this,
            	style = $this._getcardSizeStyle(),
            	cardclass = $this.options.cardclass,
            	card ="<div class='jazz-grid-cardcell "+cardclass+"' "+style+"></div>",
            	cardOuterwidth = $(card).outerWidth(true),
            	cardWidth = $(card).width(),
            	cardHeight = $(card).height(),
            	cardMargin = $(card).outerWidth(true)-$(card).width();
            	
            var cardContentWidth = this.element.parent().width();
            var total = Math.floor(cardContentWidth / (cardOuterwidth+2)),
                cardContainerWidth = total * (cardOuterwidth+2);
            
            //计算后的卡片相关参数
            $this.cardCell = {
                total: total, //每行卡片数
                cardWidth: cardWidth, //卡片宽度
                cardHeight: cardHeight, //卡片高度
                cardMargin: cardMargin,  //卡片横向间距
                cardContainerWidth: cardContainerWidth
            };
        },
        
         /**
         * @desc 卡片布局方法
         * @param {flag} 是否已经计算卡片位置参数_calCardStyle
         * @private
         * @example this._cardResize();
         */
        _cardResize: function(flag){
            if(!this.cardCell){
                return;
            }
            var $this = this,
                cardCell = $this.cardCell,
                cardContainerWidth = cardCell.cardContainerWidth,
                total = cardCell.total;
            if(flag){
            	total = Math.floor($this.element.parent().width() / (cardCell.cardWidth + 22)),//一行放几个卡片
                cardContainerWidth = total * (cardCell.cardWidth + 22);
            }
            
            $this.cardContent.css({
            	'width': cardContainerWidth+'px',
            	'margin': '0 auto'
            });
        },
        /**
         * @desc 判断是否是单选
         * @return {boolean}
         * @private
         */
        _isSingleSelection: function() {
        	var selecttype = this.options.selecttype;
            return (selecttype == 1 || selecttype == '1');
        },        
        /**
         * @desc 判断是否是多选
         * @return {boolean}
         * @private
         */
        _isMultipleSelection: function() {
        	var selecttype = this.options.selecttype;
            return (selecttype == 2 || selecttype == '2');
        },
        /**
         * @desc 绑定gridcard点击选中事件
         * @private
         * @example this._bindGridcardSelectionEvent()
         */
        _bindGridcardSelectionEvent: function() {
            var $this = this;
            
            //卡片点击选择事件委托绑定给this.cardContent
            //1.处理gridtable与gridcard事件选择交互
            //2.卡片checkbox的处理
			var selector = "div.jazz-grid-cardcell";
            this.cardContent.on('mouseover.gridcard',  selector, null, function(e) {
                var element = $(this);
                if(!element.hasClass('jazz-gridtable-highlight')) {
                    element.addClass('jazz-gridtable-hover');
                }
                //鼠标移入卡片立即绑定tooltip
                var tipObj = element.find('div.jazz-grid-qh-c-h1');
                if(tipObj.size()>0){
	            	var tip = element.find(".jazz-grid-cardcell-title").text();
		        	tipObj.tooltip({
		        		iconclass: 'jazz-tooltip-default-icon',
		        		content: tip
		        	})
                }
                //element.find(".jazz-grid-cardcell-tools").css("display","block");
            })
            .on('mouseout.gridcard', selector, null, function() {
                var element = $(this);
                if(!element.hasClass('jazz-gridtable-highlight')) {
                    element.removeClass('jazz-gridtable-hover');
                }
                //element.find(".jazz-grid-cardcell-tools").css("display","none");
            }).on('click.gridcard', selector, null, function(e) {
               	if($(e.target).is(':input,div.jazz-grid-cardcell-functionarea-checkbox,div.jazz-grid-cardcell-functionarea-radio')){
                    return;
                }
                if($(e.target).is('a')||$(e.target).parent().is('div.jazz-grid-cardcell-tools')){
                	return;
                }
                if($(this).hasClass("jazz-card-new")){return;}
                if($(this).children().hasClass("jazz-card-new")){return;}
                
                var that = this;
                if($this.options.rowselectable || $this.options.rowselectable=="true"){
	                setTimeout(function(){
	                    $this._onCardClick(e, that, false);
	                },200);
                }
            }).on('dblclick.gridcard', selector, null, function(e) {
                if($(e.target).is(':input, div.jazz-grid-cardcell-functionarea-checkbox,div.jazz-grid-cardcell-functionarea-radio')){
                    return;
                }
                if($(e.target).is('a')||$(e.target).parent().is('div.jazz-grid-cardcell-tools')){
                	return;
                }
                if($(this).hasClass("jazz-card-new")){return;}
                if($(this).children().hasClass("jazz-card-new")){return;}
                $this._onCardClick(e, this, true);
            }).on("click.cardcheckbox", "input.jazz-card-checkbox", null, function(e){
                //多选框事件和 click.grid事件相同
            	//当组件允许多选时才起作用，否则不再起作用，（要求组件使用者在自定义卡片的时候，注意checkbox与rowselecttype搭配）
            	if($this._isMultipleSelection()){
	                var row = e.target.parentNode.parentNode.parentNode;
	                if($(this).attr("onclick")){
	                	
	                }else{
		                if (this.checked) {
		                    $this._selectCard($(row), true, e);
		                }else{
		                    $this._unselectCard($(row), true, e);
		                }
	                }
            	}else{
            		alert("组件选中类型非多选，无法触发复选框事件响应。");
            	}
            });
        },
        /**
         * @desc 卡片点击事件处理，并处理与gridtable表格交互
         * @param {event} 点击事件event对象
         * @param {targetCard} 表格行或卡片所对应的数据
         * @param {flag} 表明点击是单击还是双击事件,true:双击，false:单击
         * @private
         * @example this._onCardClick(event, targetCard, flag);
         */
        _onCardClick: function(event, targetCard, flag) {
        	//不采用ctrl键进行多选操作
            var card = $(targetCard);
            var isSelected = card.hasClass('jazz-gridtable-highlight');
            
            if(this._isMultipleSelection()){
            	if(flag){//双击
            		this._selectCard(card, true, event, flag);
            	}else{//单击
                	//多选，单击效果与checkbox框一致
                	if(isSelected){
                		//取消当前选择行、卡片
                		this._unselectCard(card, true, event);
                	}else{
                		//选中当前选择行、卡片
                		this._selectCard(card, true, event, flag);
                	}
            	}
            }else {
            	if(flag){//双击
            		this._selectCard(card, true, event, flag);
            	}else{//单击
                	//默认处理都是单选，单击效果与radio一致
                	if(isSelected){
                		//已选中，则返回
                		return;
                	}else{
                		//取消其他已选中的行、卡片；然后选中当前行、卡片
                		this._unselectAllCards();
                		this._selectCard(card, true, event, flag);
                	}
            	}
            }
        },
        /**
         * @desc 返回选中卡片的坐标
         * @param {card} 选中卡片数据
         * @return {index} 卡片的坐标
         * @private
         * @example this._getCardIndex(card)
         */
        _getCardIndex: function(card) {
            var index = -1;
            index = this.cardContent.find("div.jazz-grid-cardcell").index(card);
            if(this._isCardAddBtn()){
                if(this.options.addcardposition && this.options.addcardposition === "first"){
                    index -= 1;
                }
            }
            return index;
        },
        
        /**
         * @desc 选中指定行数据
         * @param {card} 当前选中卡片
         * @param {isCallback} 是否执行点击卡片回调函数
         * @param {event} event对象
         * @param {flag} 单击或双击标识
         * @private
         * @example this._selectCard(row)
         */
        _selectCard: function(row, isCallback, event, flag) {
        	var id = row.attr("id");
            //执行卡片和表格选中
            row.removeClass('jazz-gridtable-hover')
                .addClass('jazz-gridtable-highlight')
                .attr('aria-selected', true);
            if(row.find("input.jazz-card-checkbox")){
                row.find("input.jazz-card-checkbox").attr('checked', true);
            }
            if(row.find("input.jazz-card-radio")){
                row.find("input.jazz-card-radio").attr('checked', true);
            }
            if(this.gridtable){
            	//卡片视图下,同步修改表格中对应数据的选中状态
            	var tbody = this.gridtable.tbody;
            	var thead = this.gridcolumn.tbody;
            	var tablerow = tbody.find("#"+id);
                tablerow.addClass('jazz-row-selected')
                    .attr('aria-selected', true);
                if(tablerow.find("input:checkbox")){
	                tablerow.find("input:checkbox").attr('checked', true);
	            }
	            if(tablerow.find("input:radio")){
	                tablerow.find("input:radio").attr('checked', true);
	                tablerow.siblings().find("input:radio").attr('checked', false);
	            }
                if(tbody.find("input:checked").length == tbody.find("input:checkbox").length){
	               thead.find("input:checkbox").attr("checked", true);
	            }
            } 
            //处理单击和双击回调函数
        	if(isCallback) {
        		//由gridpanel统一获取this.rows共享数据
        		var data = {};
        		data = this.gridpanel.getSelectedRowDataById(id);
        		
        		if(flag == true){
        			this._event("carddblclick",event,data);
            	}else{
            		this._event("cardselect",event,data);
            	}
            }
        },
        /**
         * @desc 取消卡片的选中
         * @param {row} 当前选中卡片
         * @param {isCallback} 是否执行点击卡片回调函数
         * @param {event} event对象
         * @private
         * @example this._selectRow(row)
         */
        _unselectCard: function(row, isCallback,event) {
        	var id = row.attr("id");
            row.removeClass('jazz-gridtable-highlight').attr('aria-selected', "false");
            
            if(row.find("input.jazz-card-checkbox")){
                row.find("input.jazz-card-checkbox").attr('checked', false);
            }
            if(row.find("input.jazz-card-radio")){
                row.find("input.jazz-card-radio").attr('checked', false);
            }
            if(this.gridtable){
            	//卡片视图下,同步修改表格中对应数据的选中状态
            	var tbody = this.gridtable.tbody;
            	var thead = this.gridcolumn.tbody;
            	var tablerow = tbody.find("#"+id);
                tablerow.removeClass('jazz-row-selected')
                    .attr('aria-selected', false);
                if(tablerow.find("input:checkbox")){
	                tablerow.find("input:checkbox").attr('checked', false);
	            }
	            if(tablerow.find("input:radio")){
	                tablerow.find("input:radio").attr('checked', false);
	            }
                if(tbody.find("input:checked").length != tbody.find("input:checkbox").length){
	               thead.find("input:checkbox").attr("checked", false);
	            }
            } 
            //处理单击和双击回调函数
        	if(isCallback) {
        		//由gridpanel统一获取this.rows共享数据
        		var data = {};
        		data = this.gridpanel.getSelectedRowDataById(id);
        		this._event("cardunselect",event,data);
            }
        },
        _selectAllCards: function(){
        	this.cardContent.find("div.jazz-grid-cardcell").addClass('jazz-gridtable-highlight').attr('aria-selected', true);
            if(this._isMultipleSelection()){
	            this.cardContent.find("input.jazz-card-checkbox").attr('checked', true);
            }
        	
        	if(this.gridtable){
        		var tbody = this.gridtable.tbody;
            	var thead = this.gridcolumn.tbody;
            	
            	tbody.find("tr").addClass('jazz-row-selected').attr('aria-selected', true);
            	if(this._isMultipleSelection()){
		        	tbody.find("input:checkbox").attr("checked", true);
		        	thead.find("input:checkbox").attr("checked", true);
	        	}
        	}
		},
        _unselectAllCards: function() {
        	this.cardContent.find("div.jazz-grid-cardcell").removeClass('jazz-gridtable-highlight').attr('aria-selected', false);
            this.cardContent.find("input.jazz-card-checkbox").attr('checked', false);
            this.cardContent.find("input.jazz-card-radio").attr('checked', false);
        	
        	if(this.gridtable){
        		var tbody = this.gridtable.tbody;
            	var thead = this.gridcolumn.tbody;
            	
            	tbody.find("tr").removeClass('jazz-row-selected').attr('aria-selected', false);
            	if(this._isMultipleSelection()){
		        	tbody.find("input:checkbox").attr("checked", false);
		        	thead.find("input:checkbox").attr("checked", false);
	        	}
        	}
        },
       
		/**
		 * @desc 添加卡片记录(暴露接口方式添加卡片记录)
		 * @param {data} 新增卡片数据数组 [{key1: value1, key2: value2, ……}]
		 */
        addCard : function(data){
        	//data 数据数组，循环追加card卡片html
        	if(!data){return;}
        	var cardHtml = this._creatCardHtml(data);
            if(cardHtml){
	            if(this.options.addcardposition == "last"){
	                this.cardContent.find("div.jazz-grid-cardcell:last").before(cardHtml);
	            }else{
	            	this.cardContent.append(cardHtml);
	            }
            }
        },
        
        _creatCardHtml: function(rows){
            var $this = this;
            
            if(rows) {
                //渲染卡片，包含3个部分（内容区、选择框区域、卡片tools区）
                //1.选择框区域
                var functionHtml ="";
	            if($this.options.isshowselecthelper == true || $this.options.isshowselecthelper == 'true'){
                	if($this.options.selecttype=="1"){//单选
                		functionHtml = '<div class="jazz-grid-cardcell-functionarea" ><div class="jazz-grid-cardcell-functionarea-radio"><input class="jazz-card-checkbox" type="radio" style="width:16px;height:16px;" /></div></div>';
                	}else if($this.options.selecttype=="2"){//复选
                		functionHtml = '<div class="jazz-grid-cardcell-functionarea" ><div class="jazz-grid-cardcell-functionarea-checkbox"><input class="jazz-card-checkbox" type="checkbox" style="width:16px;height:16px;" /></div></div>';
                	}
                }
                var cardmodel = $this.options.html;
                var style = $this._getcardSizeStyle();
                var cardclass = $this.options.cardclass;
                var cardHtml = "";
                for(var i = 0, len = rows.length; i < len; i++) {
                	
                	cardHtml += '<div class="jazz-grid-cardcell '+cardclass+'" '+style+' id="'+rows[i]["rowuuid"]+'">';
                	//cardHtml += '<div class="jazz-grid-cardcell jazz-grid-qh-cardcell" id="'+rows[i]["rowuuid"]+'">';
                    cellcontent = $this._customopration(cardmodel,rows[i]) || "";
                    
                    cardHtml = cardHtml + cellcontent;
                    cardHtml = cardHtml + functionHtml;
                    
                    //固定卡片的tools定义区内容row["@toolsopration"]
                    var stm = rows[i]["@toolsopration"];
                    if(stm){
			            stm = "<div class='jazz-grid-cardcell-tools' >"+ stm + "</div>";
		            }else{
		            	stm="";
		            }
                    cardHtml = cardHtml + stm + "</div>" ;
                }
                return cardHtml;
            }
        },
	    /**
		 * @desc 根据卡片id更新卡片数据(暴露接口方式更新卡片记录)
		 * @param {data} 待更新行数据{}
		 * @param {id} gridcard卡片id
		 * @example  $('#gridcard').gridcard('updateCardById',data, id);
		 */
	    updateCardById : function(data, id){
	    	
	    	if(!data || !id){
	            return;
	        }
            //将data包一层数组中
            var carddata = [];
        	carddata.push(data);
            
	        var cardobj = this.cardContent.find("#"+id);
    		var cardHtml = this._creatCardHtml(carddata);
    		if(cardHtml){
	    		cardobj.replaceWith(cardHtml);
    		}
	    },
        /**
	     * @desc 根据卡片id属性值(暴露接口方式删除卡片记录)
	     * @param {id} id主键
	     * @example 
	     */
        removeCardById: function(id) {
            this.cardContent.find("#"+id).remove();
        },
        /**
		 * @desc 根据卡片id属性值(暴露接口方式选中卡片)
		 * @private
		 */
        selectRow : function(id){
        	if(!id){return false;};
        	var $this = this;
        	var card = this.cardContent.find("#"+id);
        	
        	//处理选择行的状态
        	//执行卡片和表格选中
            card.removeClass('jazz-gridtable-hover')
                .addClass('jazz-gridtable-highlight')
                .attr('aria-selected', true);
            if($this._isMultipleSelection()){
            	if(card.find("input.jazz-card-checkbox")){
	                card.find("input.jazz-card-checkbox").attr('checked', true);
	            }
            }else{
            	if(card.find("input.jazz-card-radio")){
	                card.find("input.jazz-card-radio").attr('checked', true);
	                card.siblings().find("input.jazz-card-radio").attr('checked', false);
	            }
            }
        },
        /**
		 * @desc 根据卡片id属性值(暴露接口方式取消选中卡片)
		 * @private
		 */
        unselectRow : function(id){
        	if(!id){return false;};
        	var $this = this;
        	var card = this.cardContent.find("#"+id);
        	//只有多选的时候才能取消选中
            card.removeClass('jazz-gridtable-highlight').attr('aria-selected', true);
            if($this._isMultipleSelection()){
            	if(card.find("input.jazz-card-checkbox")){
	                card.find("input.jazz-card-checkbox").attr('checked', false);
	            }
            }
        },
        /**
		 * @desc 全部取消选中(暴露接口方式全部取消选中)
		 * @private
		 */
        unselectAllRows: function() {
        	var $this = this;
        	
        	this.cardContent.find("div.jazz-grid-cardcell").removeClass('jazz-gridtable-highlight').attr('aria-selected', false);
        	if($this._isMultipleSelection()){
	            this.cardContent.find("input.jazz-card-checkbox").attr('checked', false);
        	}
        },
        /**
		 * @desc 全部选中(暴露接口方式全部选中)
		 * @private
		 */
        selectAllRows: function(){
        	var $this = this;
        	
        	this.cardContent.find("div.jazz-grid-cardcell").addClass('jazz-gridtable-highlight').attr('aria-selected', true);
        	if($this._isMultipleSelection()){
	            this.cardContent.find("input.jazz-card-checkbox").attr('checked', true);
        	}
		}
		
	});
	
})(jQuery);

(function ($) {
	
	jazz.grid.editorgridpanel =  {
        /**
         * @desc 据行坐标或者列坐标使单元格变为可编辑表格
         * @augments {rowIndex}{colIndex}行坐标，列坐标
         * @public
         * @example $("#gridpanel").gridpanel("setEditorCell",1,2);
         */
        setEditorCell: function(rowIndex,colIndex,editState){
        	var rowIndex = parseInt(rowIndex);
        	var colIndex = parseInt(colIndex);
        	var editState = editState==true||editState=="true";
        	if(rowIndex>0 || colIndex>0){
        		if(this.gtable){
        			this.gtable.gridtable('setEditorCell',rowIndex,colIndex,editState);
        		}
        	}
        },
        /**
         * 新增可编辑行
         */
        addEidtorRow: function(insertPosition){
    		/**
    		 * 考虑如何维护新添加行的数据
    		 * 数据中的内容，都有那些数据？
    		 * rowuuid,各个td
    		 */
        	if(this.gtable){
        		/**
        		 * 新增加的数据的数据项如何确定有以下两种情况：
        		 * 1.当可编辑表格有原始数据的时候，参照原始数据，并将各数据项设置为null,rowuuid赋随机值
        		 * 2.若无原始数据，则按照定义列name值添加数据项，并设置为null,rowuuid赋随机值
        		 */
        		var data = {};
        		if(this.rows.length>0){
        			var copyrow = this.rows[0];
        			for(var key in copyrow ){
        				//初始值都设置为null
        				data[key] = null;
        			}
        		}else{
        			if(this.gcolumn){
		    			var gridcolumn = this.gcolumn.data("gridcolumn");
		    			cols = gridcolumn.cols;
		    			var columnname = "";
			            for (var j = 0, len = cols.length; j < len; j++) {
			            	columnname = cols[j]['columnname'];
			            	data[columnname] = null;
			            }
					}
        		}
        		data["eidtorstate"] = "editing";
        		data["rowuuid"] = Math.uuid(32);
				this.gtable.gridtable('addEidtorRow',data,insertPosition);
	        	//将新增的编辑数据放入到原始数据中
	        	this.rows.push(data);
        	}
        },
        /**
         * @desc 返回当前可编辑表格已编辑数据
         * @example $("#gridpanel").gridpanel("getUpdatedRowData");
         */
        getUpdatedRowData: function(){
        	var that = this;
        	var rows = that.rows;
        	var data = [],state="",temp=null;
        	for(var i=0,len=rows.length;i<len;i++){
        		state = rows[i]["eidtorstate"];
        		if(state){
        			temp={};
        			$.extend(true,temp,rows[i]);
        			data.push(temp);
        		}
        	}
        	return data || [];
        },
        renderGridpanelData: function(rowsdata){
        	var $this = this;
			//对待渲染数据进行datarender和数据类型（datatype、dataformat）转换进行处理
		    $this._processGridpanelData(rowsdata);
        	
        	//一、数据渲染表格和卡片
			//1.判断子组件是否创建完成,带创建完成后渲染
			//2.需要循环执行这个过程，settimeout
        	var delay=100,timeout=5000;
			if($this.gtable){
	        	var gridtable = $this.gtable.data('gridtable');
	        	if(gridtable){
		            gridtable.renderGridtableData(rowsdata,true);
	        	}else{
	        		loopReloadData($this.gtable,'gridtable',0);
	        	}
        	}
        	if($this.gcard){
	        	var gridcard = $this.gcard.data('gridcard');
	        	if(gridcard){
		            gridcard.renderGridcardData(rowsdata,true);
	        	}else{
	        		loopReloadData($this.gcard,'gridcard',0);
	        	}
        	}
        	
        	//二、绑定分页条
        	//if($this.options.isshowpaginator||$this.options.isshowpaginator=="true"){
        		if($this.gpaginator){
        			var paginator = $this.gpaginator.data('paginator');
        			var a = $this.paginationInfo;
        			if(paginator){
	            		paginator.updatePage({"page":a['page'],"pagerows":a['pagerows'],"totalrecords": a['totalrows']});
        			}else{
        				loopReloadData($this.gpaginator,'paginator',0);
        			}
        		}
        	//}
        	//三、是否创建可编辑组件，并显式显示可编辑组件
        	var isshoweditcell = $this.options.isshoweditcell;
        	if(isshoweditcell){
        		if($this.gtable){
		        	var gridtable = $this.gtable.data('gridtable');
		            gridtable.createEditorCell();
	        	}
        	}
        	//四、执行回调dataloadcomplete函数
        	$this._event("dataloadcomplete",null,{"data":$this.rows,"paginationInfo":$this.paginationInfo});
        	
        	//loopReloadData循环校验是否需要渲染gridtable或者gridcard数据
        	function loopReloadData(obj,dataname,count) {
        		var compObject = obj.data(dataname);
        		//组件创建完成
            	if(compObject){
            		if(dataname=="gridtable"){
            			compObject.renderGridtableData(rowsdata,true);
            		}else if(dataname=="gridcard"){
            			compObject.renderGridcardData(rowsdata,true);
            		}else if(dataname=="paginator"){
            			compObject.updatePage({"page":$this.paginationInfo['page'],"pagerows":$this.paginationInfo['pagerows'],"totalrecords": $this.paginationInfo['totalrows']});
            		}
            	}else{
                	if (count * delay <= timeout) {
		                count++;
	                	setTimeout(function() {
		                    loopReloadData(obj,dataname,count);
		                }, delay);
	                }
                }
            }
        },
        /**
         * @desc 通过回调函数加载数据，并渲染卡片和表格数据
         * @param {data} 返回数据
         * @param {fn} 重载数据后执行回调
         * @param {flag} 重新更新绑定分页条标识
         * @private
         * @example this.successLoadData(responseText, $this,requestdatacomplete,isRebindPaginator);
         */
        requestLoadData: function(data,fn,flag){
        	var that = this;
        	//一、对待渲染数据进行datarender和数据类型（datatype、dataformat）转换进行处理
		    that._processGridpanelData(data);
        	if(that.gtable){
        		that.gtable.gridtable("renderGridtableData",data);
        	}
			if(that.gcard){
        		that.gcard.gridcard("renderGridcardData",data);
        	}
        	//二、是否创建可编辑组件，并显式显示可编辑组件
        	var isshoweditcell = that.options.isshoweditcell;
        	if(isshoweditcell){
        		if(that.gtable){
		        	var gridtable = that.gtable.data('gridtable');
		            gridtable.createEditorCell();
	        	}
        	}
        	//reload/query/reloadcurrentpage/重新更新绑定分页条，更新状态
        	//paginator 不再重新绑定更新状态
        	var a = that.paginationInfo;
        	if(flag){
            	if(that.options.isshowpaginator && that.gpaginator){
            		that.gpaginator.paginator("updatePage",{"page":a['page'],"pagerows":a['pagerows'],"totalrecords": a['totalrows']});
            	}
        	}
        	if(fn && $.isFunction(fn)){
	        	fn.call(that,that.rows,a);
        	}
        }
    };
})(jQuery);

(function ($) {
	
	jazz.grid.editorgridtable =  {
		
        /**
         * @desc 覆写gridtable 同方法
         */
        _insertRowHtml: function(rowObj, index, lineIndex){
        	var $this = this;
        	var data = rowObj;
        	var lineno = this.options.lineno;//是否显示行号
        	//1.计算行号
        	var page=1;//当前页码
        	var pagerows=0;//当前每页显示条数
        	if(this.gridpanel&&this.gridpanel.paginationInfo){
        		page = this.gridpanel.paginationInfo["page"]||1;
        		pagerows = this.gridpanel.paginationInfo["pagerows"]||0;
        	}
    		lineIndex = index + lineIndex + 1 + ((page-1) * pagerows);
            
    		//2.计算奇偶行
            var cls = '';
            if((lineIndex)%2 == 0){
            	cls = 'jazz-gridtable-even';
            }else{
            	cls = 'jazz-gridtable-odd';
            }
            var rowHtml = '<tr index="'+(lineIndex-1)+'" class="jazz-grid-row '+cls+' " id="'+data["rowuuid"]+'">';
            rowHtml +='<th style="height:20px;"></th>';
            //4.判断是否显示checkbox radio
            var b = '';
            if (this._isSelectHelper() && this._isSelectType()) {
                if ($this._isMultipleSelection()) {
                	b = 'checkbox';
                }else if ($this._isSingleSelection()) {
                	b = 'radio';
                }
                //rowHtml += '<td class="jazz-grid-cell"><div class="jazz-grid-cell-box"><input type="'+b+'"/></div></td>';
                rowHtml += '<td class="jazz-grid-cell jazz-grid-cell-box"><input type="'+b+'"/></td>';
            }
			//5.添加行号，根据当前页码数递增
            if(lineno){
            	rowHtml += '<td class="jazz-grid-cell jazz-grid-cell-no">' + lineIndex + '</td>';
            }
            //6.生成内容td
            var iseditable = false;
            if(this.gridpanel&&this.gridpanel.paginationInfo){
        		iseditable = this.gridpanel.options["iseditable"];
        	}
        	
            var columnname="",stm="",textalign="",columneditor=null,title="";
            for (var j = 0, len = this.cols.length; j < len; j++) {
            	columnname = this.cols[j]['columnname'];
            	textalign = this.cols[j]['textalign'] || "";
            	columneditor = this.cols[j]['iseditable'];
            	
            	title = "";
            	stm = "&nbsp;";
        		if(columnname&&data[columnname]!=undefined){
        			if(data[columnname].toString().indexOf('<')!=0){
        				title = data[columnname] ||"";
        			}
        			/**
        			 * 去掉原<td>内部的div
        			 */
        			//stm = '<div class="jazz-grid-cell-inner" title='+title+'>'+data[columnname]+'</div>';
        			stm = data[columnname];
        		}
        		if(textalign){
        			textalign = "text-align-"+textalign;
        		}
        		if(iseditable){
        			columneditor = columneditor==false||columneditor=="false"?"":"celleditable";
        		}else{
        			columneditor = columneditor==true||columneditor=="true"?"celleditable":"";
        		}
	            rowHtml += '<td class="jazz-grid-cell '+textalign+' '+columneditor+'" title='+title+'>'+stm+'</td>';	
            }
            return rowHtml;
        },

        /**
         * @desc 绑定表格选择事件（单击和双击）
         * @private
         * @example this._bindGridTableSelectionEvent();
         */
        _bindGridTableSelectionEvent: function(){
			var that = this;
			this.tbody.on("click.td", "td.jazz-grid-cell", null, function(e) {
				if($(this).parent().hasClass("nodata")) {return;}
	        	if($(e.target).is('a')){return;}
	        	that._onRowClickEvent(e,$(this),false);
	        }).on("dblclick.td", "td.jazz-grid-cell", null, function(e) {
	        	if($(this).parent().hasClass("nodata")) {return;}
	        	if($(e.target).is('a')){return;}
	        	that._onRowClickEvent(e,$(this),true);
			});
			
			$(document).on("mousedown.editorgrid", function(e){
				//1.即要排除鼠标点到pretd本身和内部子元素情况
				var target = $(e.target);
				if(!that.pretd || !that.pretd[0]){
					return;
				}
				if(that.pretd[0]==target[0] || that.pretd.find(target)[0]){
					return;
				}
				//1.若显式显示可编辑单元格，则不销毁可编辑td中的可编辑组件
				//而只是处理值改变情况
	        	var isshoweditcell = that.options.isshoweditcell;
				if(isshoweditcell){
					that._showeditcellChange(e);
				}else{
					//3.编辑组件赋值和状态清除
					that._editorChange(e);
				}
			});
		},
		_onRowClickEvent : function(e,tdEl,dbclick){
			var $this = this;
			var targetrow = $(tdEl).parent();
			var editortype = $this.options.editortype;
			var rowselectable = $this.options.rowselectable || $this.options.rowselectable=="true";
			
			//表格单击事件处理规范：
			//1.点击普通单元格响应选中、取消选中、双击事件
			//2.点击可编辑单元格响应可编辑组件创建事件，但不触发单击选中、取消选中、双击事件
			//3.对于editortype==row时需要特殊处理，因为点击该行单元格都需要初始化可编辑组件
			if(tdEl.hasClass("celleditable")){//可编辑td
        		//可编辑单元格td，无论单击还是双击都不触发rowselect,rowunselect,rowdbclick事件
        		//只是增加选中行样式，并且再次点击也不会去掉行选中样式
        		if(dbclick){
        			if(rowselectable){
		        		$this._shiftToRowSelectedStyle(targetrow);
        			}
					$this._editTdClick(e, targetrow, tdEl);
        		}else{
	        		setTimeout(function(){
	        			if(rowselectable){
			        		$this._shiftToRowSelectedStyle(targetrow);
	        			}
						$this._editTdClick(e, targetrow, tdEl);
		            },100);
        		}
			}else{//不可编辑td
				if(rowselectable){
	        		if(dbclick){
	        			$this._onRowClick(e, targetrow, true);//双击
	        		}else{
			        	setTimeout(function(){ $this._onRowClick(e, targetrow, false); },100);
	        		}
	        	}else{
	        		if($(e.target).is('.jazz-grid-cell-box :input')){
	        			$this._onRowClick(e, targetrow, false);//选择框勾选都是相当于单击事件
	        		}
	        	}
				if(editortype=="row"){
					$this._editTdClick(e, targetrow, tdEl);
				}
			}
		},
        _shiftToRowSelectedStyle: function(row){
        	var $this = this;
        	var id = row.attr('id');
        	//处理选择行的状态
            row.addClass('jazz-row-selected').attr('aria-selected', true);
            //处理单选和多选两种情况
        	if($this._isMultipleSelection()){
        		if(row.find("input:checkbox")){
	                row.find("input:checkbox").attr('checked', true);
	            }
	            //全选，所有数据行被选中
	            if($this.tbody.find("input:checked").length == $this.tbody.find("input:checkbox").length ){
	            	var thead = $this.gridcolumn.tbody;
	               	thead.find("input:checkbox").attr("checked", true);
	            }
        	}else{
	            if(row.find("input:radio")){
	                row.find("input:radio").attr('checked', true);
	                row.siblings().find("input:radio").attr('checked', false);
	            }
        	}
        	
        	if($this.gridpanel.gcard){
        		var card = $this.gridpanel.gcard.find("#"+id);
        		card.removeClass('jazz-gridtable-hover')
	                .addClass('jazz-gridtable-highlight')
	                .attr('aria-selected', true);
	            if($this._isMultipleSelection()){
	        		if(card.find("input.jazz-card-checkbox")){
		                card.find("input.jazz-card-checkbox").attr('checked', true);
		            }
	        	}else{
		            if(card.find("input.jazz-card-radio")){
		                card.find("input.jazz-card-radio").attr('checked', true);
		                card.siblings().find("input.jazz-card-radio").attr('checked', false);
		            }
	        	}
        	}
        },
        _resetPretdComponent: function(){
        	var that = this;
    		that.pretd = null;
    		that.pretdoption = null;
			that.prevtype = null;
			that.preheadname = null;
			that.preid = null;
			that.predata = null;
        },
        _reValuePretdComponent: function(event, tr, td){
			var that = this;
			var comp = td.children();
			var vtype = comp.attr("vtype") || "";
			var index = td.index() || -1;
			var headname = that.thead.find("th").eq(index).attr("name");
			var id = tr.attr("id");
			var data = that.gridpanel.getSelectedRowDataById(id);
			var headerCells = that.gridcolumn.options.header;
			var tdoption = that._getDefinedColumn(headerCells,headname);
			
			that.pretd = td;
			that.pretdoption = tdoption;
			that.compobject = comp.data(vtype);
			that.prevtype = vtype;
			that.preheadname = headname;
			that.preid = id;
			that.predata = data;
        },
        _editTdClick: function(event, tr, td) {
        	var that = this;
        	var editortype = that.options.editortype;
        	if(editortype=="row"){
        		if(that.pretd&&that.pretd[0] == td[0]){
        			return;
        		}else{
        			//1.校验该行是否已经初始化可编辑组件
        			if(tr.find("div[vtype]").length>0){
        				if(td.hasClass("celleditable")){
        					that._reValuePretdComponent(event, tr, td);
        				}else{
        					//that._resetPretdComponent();
        				}
        			}else{
        				//初始化一整行的可编辑组件
        				$(tr).find("td.celleditable").each(function(i){
		        			that._createCellEditorComponent(event, tr, $(this));
		        		});
		        		var defaultTD = $(tr).find("td.celleditable:first");
		        		that._reValuePretdComponent(event, tr, defaultTD);
        			}
        		}
        	}else if(editortype=="column"){
        		if(that.pretd&&that.pretd[0] == td[0]){
        			return;
        		}else{
        			if(td.find("div[vtype]").length>0){
        				if(td.hasClass("celleditable")){
        					that._reValuePretdComponent(event, tr, td);
        				}else{
        					that._resetPretdComponent();
        				}
        			}else{
        				var index = td.index();
		        		that.tbody.find("tr").each(function(i){
		        			var obj = $(this).children().eq(index);
		        			that._createCellEditorComponent(event, $(this), obj);
		        		});
		        		var defaultTR = that.tbody.find("tr:first");
		        		var defaultTD = defaultTR.children().eq(index);
		        		that._reValuePretdComponent(event, defaultTR, defaultTD);
        			}
        		}
        	}else {//editortype==cell||undefined||null
        		if(that.pretd&&that.pretd[0] == td[0]){
        			return;
        		}else{
        			that._createCellEditorComponent(event, tr, td);
        			that._reValuePretdComponent(event, tr, td);
					
        			//对于editortype=="row/column"时，不设置焦点
					var inputObj = td.find("input[class='jazz-field-comp-input']"); 
					if(inputObj[0]){
						inputObj.focus();
					}
        		}
        	}
        },
        _createCellEditorComponent: function(event, tr, td){
        	var that = this;
        	var editortype = that.options.editortype;
    		var comp = td.children();
			var vtype = comp.attr("vtype") || "";
			if(!vtype){
				//清空td全部内容
				td.empty();
				//从thead中找出该列的name值，以便在header中定位到定义列
				var index = td.index() || -1;
				var headname = that.thead.find("th").eq(index).attr("name");
				
				//对当前<div class="jazz-grid-cell-inner">添加可编辑的样式
				//利用<div>创建可编辑组件
				td.addClass("jazz-grid-cell-editing");
				comp = $("<div style='margin:0px;padding:0px;'>").appendTo(td);
				
				//创建组件默认为textfield
				var vtype = "textfield", options = {width: td.width(), height: td.height()};
				var headerCells = that.gridcolumn.options.header;
				if(headerCells){
					var tdoption = that._getDefinedColumn(headerCells,headname);
					if(tdoption){
						//1.将列定义的所有属性全部拷贝给可编辑组件options
						$.extend(true,options, tdoption);
						options["name"]="";
						vtype = tdoption["datatype"]||"textfield";
						//2.单独处理具有dataurl的组件（暂时规定下拉框和下拉树）
						if(vtype=="comboxtreefield"||vtype=="comboxfield"){
							var compdataurl = tdoption["valueset"];
							if(compdataurl){
								//url分为两种情况，一是请求数据地址，二是直接数据
								if(compdataurl.trim().indexOf("[")===0){
									compdataurl = eval("("+compdataurl+")");
								}
								$.extend(options, {dataurl: compdataurl});
							}
						}
					}
				}
				//创建组件，获取创建完的组件对象
				comp[vtype](options);
				var compobject = comp.data(vtype);
				
				//二、创建可编辑组件后，将td中的值赋值给创建的组件
				//1.从该行对应的数据中，获取该td所对应的原始值（即未进行代码转换的原始值）
				var id = tr.attr("id");
				var data = that.gridpanel.getSelectedRowDataById(id);
				
				var value = data&&data[headname] ? data[headname]: "";
				compobject.setValue(value);
			}
        },
        _editorChange: function(e){
        	var that = this;
        	
        	//1.获取组件的最新值
			var headname = that.preheadname ||"";
			var newText = that.compobject.getText() ||"";
			var newValue = that.compobject.getValue() ||"";
			var oldValue = that.predata[headname] ||"";
			
			//2.修改原始数据rowdata
			if((!oldValue&&!newValue)||newValue==oldValue){	
				//值相同的时候不必修改rowdata原始数据
			}else{
				//值不同需要修改rowdata原始数据对应的值
				that.predata[headname] = newValue || "";
				that.predata["eidtorstate"] = "editing";
				//标识编辑图标
				that.pretd.addClass("jazz-editgrid-state");
				
				var predataid = "";
				predataid = that.preid;
			}
			
        	//3.根据可编辑类型不同，销毁已创建可编辑组件的时机也是不同
        	var editortype = that.options.editortype;
        	var target = $(e.target);
        	if(editortype=="row"){
        		//当鼠标点到该行的时候不予销毁，只是赋值
        		//否则销毁可编辑组件，并赋值
        		var prerow = that.pretd.parent();
        		if(prerow.has(target).length>0){
        			
        		}else{
        			//该行所有可编辑组件进行销毁
        			$(prerow).find("td.celleditable").each(function(i){
						var comp = $(this).find("div[vtype]");
						var v = "";
						if(comp[0]){
							var vtype = comp.attr("vtype");
							var compobject = comp.data(vtype);
							v = compobject.getText() ||"";
						}
						$(this).empty();
						
						//1.按照datatype、dataformat处理数据类型
						var index = $(this).index() || -1;
						var headname = that.thead.find("th").eq(index).attr("name");
						var headerCells = that.gridcolumn.options.header;
						var tdoption = that._getDefinedColumn(headerCells,headname);
						var datatype=tdoption["datatype"];
						var dataformat=tdoption["dataformat"];
						if(datatype && v!=undefined){
		            		v = jazz.util.parseDataByDataFormat({"cellvalue":v,"datatype":datatype,"dataformat":dataformat});
		            	}
        				$(this).removeClass("jazz-grid-cell-editing").text(v).attr("title", v);
        				//对于editortype==row/column，不添加jazz-grid-cell-edited样式
	        		});
	        		//4.重置pretd
		    		that._resetPretdComponent();
        		}
        	}else if(editortype=="column"){
        		//当鼠标点到该列的时候不予销毁，只是赋值
        		//否则销毁可编辑组件，并赋值
        		var index = that.pretd.index();
        		var td = target.closest("td");
        		if(td[0] && that.tbody.find(td).length>0 &&td.index()==index){
        			//表明是同列
        		}else{
	        		that.tbody.find("tr").each(function(i){
	        			var tdobj = $(this).children().eq(index);
	        			var comp = tdobj.find("div[vtype]");
						var v = "";
						if(comp[0]){
							var vtype = comp.attr("vtype");
							var compobject = comp.data(vtype);
							v = compobject.getText() ||"";
						}
						tdobj.empty();
						
						//1.按照datatype、dataformat处理数据类型
						var headname = that.thead.find("th").eq(index).attr("name");
						var headerCells = that.gridcolumn.options.header;
						var tdoption = that._getDefinedColumn(headerCells,headname);
						var datatype=tdoption["datatype"];
						var dataformat=tdoption["dataformat"];
						if(datatype && v!=undefined){
		            		v = jazz.util.parseDataByDataFormat({"cellvalue":v,"datatype":datatype,"dataformat":dataformat});
		            	}
        				tdobj.removeClass("jazz-grid-cell-editing").text(v).attr("title", v);
	        		});
	        		//4.重置pretd
		    		that._resetPretdComponent();
        		}
        	}else{
        		//1.按照datatype、dataformat处理数据类型
				var datatype=that.pretdoption["datatype"];
				var dataformat=that.pretdoption["dataformat"];
				if(datatype && newText!=undefined){
            		newText = jazz.util.parseDataByDataFormat({"cellvalue":newText,"datatype":datatype,"dataformat":dataformat});
            	}
        		that.pretd.empty().removeClass("jazz-grid-cell-editing").text(newText).attr("title", newText);
				that.tbody.find(".jazz-grid-cell-edited").removeClass("jazz-grid-cell-edited");
				that.pretd.addClass("jazz-grid-cell-edited");
				//4.重置pretd
		    	that._resetPretdComponent();
        	}
        	//cell值改变时抛出cellvaluechange.summaryrow事件，修改summary 合计行数据
			if(this.options.isgroupsummary||this.options.ispagesummary){
				if(predataid){//表明td中的值发生改变了
		        	console.log("#######################"+predataid);
		        	that.tbody.trigger("cellvaluechange.summaryrow",[predataid]);
	        	}
			}				
		},
		/**
		 * @desc 处理显式显示可编辑组件的值改变情况
		 * @param {event}
		 * @private
		 */
		_showeditcellChange: function(event){
        	var that = this;
        	//1.获取组件的最新值
			var headname = that.preheadname ||"";
			var newText = that.compobject.getText() ||"";
			var newValue = that.compobject.getValue() ||"";
			var oldValue = that.predata[headname] ||"";

			//2.修改原始数据rowdata
			if((!oldValue&&!newValue)||newValue==oldValue){	
				//值相同的时候不必修改rowdata原始数据
			}else{
				//值不同需要修改rowdata原始数据对应的值
				//1.按照datatype、dataformat处理数据类型
				var datatype=that.pretdoption["datatype"];
				var dataformat=that.pretdoption["dataformat"];
				if(datatype && newValue!=undefined){
            		newValue = jazz.util.parseDataByDataFormat({"cellvalue":newValue,"datatype":datatype,"dataformat":dataformat});
            	}
				that.predata[headname] = newValue || "";
				that.predata["eidtorstate"] = "editing";
				//标识编辑图标
				//that.pretd.addClass("jazz-editgrid-state");
			}
    		that.pretd.attr("title", newText);
			//4.重置pretd
	    	that._resetPretdComponent();
		},
		_getDefinedColumn: function(cells,name){
			var $this = this;
			var opt = null;
			if($.isArray(cells)){
				for(var i =0,len=cells.length;i<len;i++){
					var cell = cells[i];
					if($.isArray(cell)){
						opt = $this._getDefinedColumn(cell,name);
					}else{
						if(name==cell["name"]){
							opt = cell;
							break;
						}
					}
				}
			}
			return opt;
		},
		/**
         * @desc 表格点击事件处理，并处理与gridcard卡片交互
         * @param {event} 点击事件event对象
         * @param {targetrow} 表格行或卡片所对应的数据
         * @param {flag} 表明点击是单击还是双击事件,true:双击，false:单击
         * @private
         * @example this._onRowClick();
         */
        _onRowClick: function(event, targetRow, flag) {
            var row = $(targetRow);
            var isSelected = row.hasClass('jazz-row-selected');
            if(this._isMultipleSelection()){
            	if(flag){//双击
            		this._selectRow(row, true, event, flag);
            	}else{//单击
                	//多选，单击效果与checkbox框一致
                	if(isSelected){
                		//取消当前选择行、卡片
                		this._unselectRow(row, true, event);
                	}else{
                		//选中当前选择行、卡片
                		this._selectRow(row, true, event, flag);
                	}
            	}
            }else {
            	if(flag){//双击
            		this._selectRow(row, true, event, flag);
            	}else{//单击
                	//默认处理都是单选，单击效果与radio一致
                	if(isSelected){
                		this._selectRow(row, true, event, flag);
                	}else{
                		//取消其他已选中的行、卡片；然后选中当前行、卡片
                		this._unselectAllRows();
                		this._selectRow(row, true, event, flag);
                	}
            	}
            }
        },
		
        /**
         * @desc 据行坐标或者列坐标使单元格变为可编辑表格
         * @param {rowIndex}行坐标
         * @param {colIndex}列坐标
         * @param {editState}是否可编辑状态
         */
        setEditorCell: function(rowIndex,colIndex,editState){
        	//其实都是根据行，再找列
        	/*var trObj = 
        	if(){
        		
        	}else{
        		
        	}*/
        	
        },
        /**
         * @desc 创建整个可编辑表格中的可编辑组件
         */
        createEditorCell: function(){
        	var that = this;
        	$.each(this._trObject(), function(i, obj) {
        		var tr = $(this);
        		tr.find("td.celleditable").each(function(i){
        			var td = $(this);
					that._createCellEditorComponent(null, tr, td);
				});
            });
        },
        /**
         * @desc 新增可编辑行
         * @param {data} 新增编辑行数据
         * @param {insertPosition} 新增编辑行行坐标
         */
        addEidtorRow: function(data,insertPosition){
        	var rowHtml = "";
        	var p = parseInt(insertPosition);
        	var lineIndex = this._trNumber();
        	if(p===0||insertPosition=="first"){//首行
        		rowHtml = this._insertRowHtml(data, 0, 0);
        		$(rowHtml).insertBefore(this.tbody.children().first());
        		this._updateIndex();
        	}else if(p>0&&p<lineIndex){//中间行
        		rowHtml = this._insertRowHtml(data, 0, 0);
        		$(rowHtml).insertBefore(this.tbody.children().eq(p));
        		this._updateIndex();
        	}else{//尾行
        		rowHtml = this._insertRowHtml(data, 0, lineIndex);
        		$(rowHtml).appendTo(this.tbody);
        	}
        }
	};
	
})(jQuery);

(function($){
	/**
	 * @desc 扩展gridtable分组功能
	 */
	jazz.grid.groupgridtable = {
		
		_bindGroupGridTableEvent:function(){
			var that = this;
			this.tbody.on("click.tr", "tr.jazz-grid-group-row", null, function(e) {
				//是否分组合计还是当前页合计
	            var isgroupsummary = that.options.isgroupsummary;
	            var ispagesummary = that.options.ispagesummary;
				if($(this).hasClass('jazz-grid-group-expand')){
					$(this).removeClass('jazz-grid-group-expand').addClass('jazz-grid-group-collapse');
					if(isgroupsummary){
						$(this).nextUntil(".jazz-grid-groupsummary-row").show();
					}else{
						if(ispagesummary){
							//如果当前分组行是最后一个分组行行则
							if($(this)[0]==that.tbody.find("tr.jazz-grid-group-row").last()[0]){
								$(this).nextUntil(".jazz-grid-pagesummary-row").show();
							}else{
								$(this).nextUntil(".jazz-grid-group-row").show();
							}
						}else{
							$(this).nextUntil(".jazz-grid-group-row").show();
						}
					}
				}else if($(this).hasClass('jazz-grid-group-collapse')){
					$(this).removeClass('jazz-grid-group-collapse').addClass('jazz-grid-group-expand');
					if(isgroupsummary){
						$(this).nextUntil(".jazz-grid-groupsummary-row").hide();
					}else{
						if(ispagesummary){
							//如果当前分组行是最后一个分组行行则
							if($(this)[0]==that.tbody.find("tr.jazz-grid-group-row").last()[0]){
								$(this).nextUntil(".jazz-grid-pagesummary-row").hide();
							}else{
								$(this).nextUntil(".jazz-grid-group-row").hide();
							}
						}else{
							$(this).nextUntil(".jazz-grid-group-row").hide();
						}
					}
				}
	        });
		},
		_bindSummaryGridTableEvent:function(){
			//表格进行合计统计时，凡涉及到数据行数据变化的操作，
			//均抛出cellvaluechange event事件，然后this.tbody接收，以此重新计算合计行
			//注意的地方：
			//1.现对引起数据变化的操作都是进行统一处理，即重新对所有分组或当前页数据进行合计计算
			//2.如上统一处理，在只发生单个cell 值改变情况下，都要重新计算所有合计，效率不好
			//3.涉及到数据变化的操作有：行增加，删除，修改；可编辑表格的数据的变化
			var that = this;
			this.tbody.on("cellvaluechange.summaryrow", function(event) {
				//1.若是有合计行需要计算合计行的数据
           		 that._computeGroupAndPageSummartResult();
	        });
		},
		/**
         * @desc 加载table数据
         * @param {rows} 待渲染的表格数据
         * @private
         */
        _renderData : function(rows) {
            if(!rows){
                return;
            }
            var groupfieldId = this.options.groupfield;
            var groupfieldTitle = this.options.grouptitlefield;
            //是否分组合计还是当前页合计
            var isgroupsummary = this.options.isgroupsummary;
            var ispagesummary = this.options.ispagesummary;            
            //1.此时数据都已经按照分组字段排好序了（后台对数据进行处理，前台只是展示处理）
            //以后也可以将排序暴露出去
            rows.sort(function(a,b){
            	return parseInt(a[groupfieldId])-parseInt(b[groupfieldId]);
            });
            //2.生成分组tr dom结构html
            var lineIndex = 0;
            var rowHtml = [];
        	this._trObject().remove();        	
            lineIndex = this._trNumber();
            var groupId,grouptitle;
            for (var i = 0; i < rows.length; i++) {
            	if(i==0){
            		groupId = rows[i][groupfieldId];
	            	grouptitle = rows[i][groupfieldTitle]||"";
            		rowHtml.push(this._getGroupRowHtml(groupId,grouptitle));
            	}
            	if(groupId == rows[i][groupfieldId]){
            		
            	}else{
	            	//拼接分组合计html dom结构
	            	if(isgroupsummary){
	            		rowHtml.push(this._getSummaryRowHtml("groupsummary"));
	            	}
            		groupId = rows[i][groupfieldId];
            		grouptitle = rows[i][groupfieldTitle]||"";
            		rowHtml.push(this._getGroupRowHtml(groupId,grouptitle));
            	}
            	rowHtml.push(this._insertRowHtml(rows[i], i, lineIndex));
            }
            //当前页最后一个分组拼接分组合计html dom结构
        	if(isgroupsummary){
        		rowHtml.push(this._getSummaryRowHtml("groupsummary"));
        	}
            //拼接当前页合计html dom结构
            if(ispagesummary){
        		rowHtml.push(this._getSummaryRowHtml("pagesummary"));
        	}
            this.tbody.append(rowHtml.join(""));
            
            //3.初始化时展开还是收起分组数据行
            var isexpand = this.options.isgroupexpand;
            if(!isexpand){
            	this.tbody.children("tr.jazz-grid-group-row").each(function(){
            		if(isgroupsummary){
						$(this).nextUntil(".jazz-grid-groupsummary-row").hide();
					}else{
						if(ispagesummary){
							//如果当前分组行是最后一个分组行行则
							if($(this)[0]==that.tbody.find("tr.jazz-grid-group-row").last()[0]){
								$(this).nextUntil(".jazz-grid-pagesummary-row").hide();
							}else{
								$(this).nextUntil(".jazz-grid-group-row").hide();
							}
						}else{
							$(this).nextUntil(".jazz-grid-group-row").hide();
						}
					}
            	});;
            }
            //4.若是有合计行需要计算合计行的数据
            this._computeGroupAndPageSummartResult(rows);
            /*if(isgroupsummary){
            	var that = this;
            	this.tbody.find("tr.jazz-grid-group-row").each(function(i){
            		var siblings = $(this).nextUntil(".jazz-grid-group-row");
            		//获取组数据groupdata
            		var groupdata = [],data;
            		$.each( siblings, function(j, obj){
					  var id = $(obj).attr("id");
					  if(id){
					  	data = that.gridpanel.getSelectedRowDataById(id);
					  	groupdata.push(data);
					  }
					});
            		//根据组数据，计算组合计---groupdata无值的时候，交由_computeSummaryResult处理
					var summaryrow = siblings.filter("tr.jazz-grid-groupsummary-row");
			        that._computeSummaryResult(groupdata,summaryrow);
            	});
            }
            if(ispagesummary){
            	var summaryrow = this.tbody.find("tr.jazz-grid-pagesummary-row");
            	this._computeSummaryResult(rows,summaryrow);
            }*/
            //4.渲染数据后，执行rowrender(rowdata,rowElement),提供操作tr回调
            var trObjs = this.tbody.children(".jazz-grid-row");
            this._event("rowrender",null,{"data":rows,"rowEl":trObjs});//this.tbody.find("tr")
        },
        
        _getGroupRowHtml: function(groupId,grouptitle){
        	var groupId = groupId || "";
        	var isexpand = this.options.isgroupexpand;
        	var expandclass = isexpand?"jazz-grid-group-collapse":"jazz-grid-group-expand";
            var rowHtml = '<tr class="jazz-grid-group-row '+expandclass+'" groupid="'+groupId+'" >';
            rowHtml +='<th style="height:20px;"></th>';
            var colspan_num=0;
            if (this._isSelectHelper() && this._isSelectType()) {
            	colspan_num++;
            }
            if(this.options.lineno){
            	colspan_num++;
            }
            colspan_num+=this.cols.length;
            rowHtml +='<td class="jazz-grid-cell" colspan="'+colspan_num
            		+'"><div class="jazz-grid-group-title">'+grouptitle+'</div></td></tr>';
            return rowHtml;
        },
        _computeGroupAndPageSummartResult: function(rows){
        	var that = this;
        	//是否分组合计还是当前页合计
            var isgroupsummary = this.options.isgroupsummary;
            var ispagesummary = this.options.ispagesummary;
        	if(isgroupsummary){
            	this.tbody.find("tr.jazz-grid-group-row").each(function(i){
            		var siblings = $(this).nextUntil(".jazz-grid-group-row");
            		//获取组数据groupdata
            		var groupdata = [],data;
            		$.each( siblings, function(j, obj){
					  var id = $(obj).attr("id");
					  if(id){
					  	data = that.gridpanel.getSelectedRowDataById(id);
					  	groupdata.push(data);
					  }
					});
            		//根据组数据，计算组合计---groupdata无值的时候，交由_computeSummaryResult处理
					var summaryrow = siblings.filter("tr.jazz-grid-groupsummary-row");
			        that._computeSummaryResult(groupdata,summaryrow);
            	});
            }
            if(ispagesummary){
            	var summaryrow = this.tbody.find("tr.jazz-grid-pagesummary-row");
            	if(!rows){
            		rows = this.gridpanel.getAllData();
            	}
            	this._computeSummaryResult(rows,summaryrow);
            }
        },
        /**
         * @desc 计算分组或者当前页数据合计值，提供了count/sum/max/min/average/自定义合计函数方式。
         * 		 并且，提供summaryrender 设置合计计算结果展示格式
         * @param {rows} 待合计数据行数据
         * @param {summaryrow} 待合计的分组行或者表格总合计jquery dom对象
         */
       	_computeSummaryResult: function(rows,summaryrow){
        	
        	var columnname="",issummary="",result=0,value;
            for (var j = 0, len = this.cols.length; j < len; j++) {
            	issummary = this.cols[j]['issummary'];
	            if(issummary){
	            	columnname = this.cols[j]['columnname'];
	            	summarytype = this.cols[j]['summarytype'];
	            	summaryrender = this.cols[j]['summaryrender'];
	            	
	            	var result = this._getSummaryTypeResult(columnname,rows,summarytype);
	            	result = this._getSummaryRenderResult(result,rows,summaryrender);
					var index = this.thead.find("th[name='"+columnname+"']").index();
					summaryrow.children().eq(index).text(result);
	            }
            }
        },
        _getSummaryTypeResult: function(columnname,rows,type){
        	if (type) {
        		var result;
        		if (type.indexOf("(") != -1) {
					result = this._customopration(type,rows);
					return result;
				}
				var temp = [],value;
				for (var i = 0; i < rows.length; i++) {
	        		value = rows[i][columnname];
	            	temp.push(value);
	            }
	            switch (type) {
	                case 'count':
	                    return rows.length;
	                case 'min':
	                	var x = Math.min.apply(null, temp)
	                    return Math.min.apply(null, temp);
	                case 'max':
	                    return Math.max.apply(null, temp);
	                case 'sum':
	                    return eval(temp.join('+'));
	                case 'average':
	                	var count = temp.length;
	                	var sum = eval(temp.join('+'));
	                	if(count>0){
	                		return Math.round(sum/count);
	                	}else{
	                		return 0;
	                	}
	                default:
	                    return '';
	            }
	        }else {
	        	return '';
	        }
        },
        _getSummaryRenderResult: function(result,rows,format){
        	if (format) {
        		if (format.indexOf("(") != -1) {
					result = this._customopration(format,result,rows);
					return result;
				}
	        }else {
	        	return result;
	        }
        },
        
        /*_computeSummaryResult: function(rows,summaryrow){
        	//暂时简单求和处理，之后再做和EXT那样的合计函数
        	var columnname="",issummary="",result=0,value;
            for (var j = 0, len = this.cols.length; j < len; j++) {
            	columnname = this.cols[j]['columnname'];
            	issummary = this.cols[j]['issummary'];
	            if(issummary){
		            result=0,value=null;
		        	for (var i = 0; i < rows.length; i++) {
		        		value = rows[i][columnname];
		            	result = result + parseInt(value);
		            }
					var index = this.thead.find("th[name='"+columnname+"']").index();
					summaryrow.children().eq(index).text(result);
	            }
            }
        },*/
        
        
        /**
         * @desc 拼接合计行html dom结构
         * @param {summary} 值为"groupsummary"或"pagesummary"
         * 表明是分组合计还是当前页全部数据合计
         */
        _getSummaryRowHtml: function(summary){
            var rowHtml = '<tr class="jazz-grid-'+summary+'-row">';
            rowHtml +='<th style="height:20px;"></th>';
            
            var isselect = this._isSelectHelper() && this._isSelectType();
            var islineno = this.options.lineno;
            var hj = summary=="groupsummary"?"合计":"总合计";
            if(isselect&&islineno){
            	rowHtml += '<td class="jazz-grid-cell jazz-grid-cell-box" colspan="2">'+hj+'</td>';
            	rowHtml += '<td class="jazz-grid-cell jazz-grid-cell-no" style="display:none;"></td>';
            }else{
            	if(isselect){
            		rowHtml += '<td class="jazz-grid-cell jazz-grid-cell-box" colspan="2">'+hj+'</td>';
            	}
            	if(islineno){
            		rowHtml += '<td class="jazz-grid-cell jazz-grid-cell-no">'+hj+'</td>';
            	}
            }
            var textalign;
            for (var j = 0, len = this.cols.length; j < len; j++) {
            	textalign = this.cols[j]['textalign'] || "";
        		if(textalign){
        			textalign = "text-align-"+textalign;
        		}
	            rowHtml += '<td class="jazz-grid-cell '+textalign+'">&nbsp;</td>';	
            }
            return rowHtml;
        },
        /**
		 * @desc 添加行记录
		 * @param {rowObj} 行数据对象 {key1: value1, key2: value2, ……}
		 */
        addRow: function(data) {
        	
        	if(!data){return;}
        	
        	var rowHtml = "";
        	var lineIndex = this._trNumber();
        	for(var i=0;i<data.length;i++){
        		//循环追加tr行数据html
        		rowHtml += this._insertRowHtml(data[i], i, lineIndex);
        	}
        	var trobj = null
        	if(rowHtml){
	        	//this.tbody.append(rowHtml);
	        	//trobj = $(rowHtml).appendTo(this.tbody);
        		//这个渲染操作暂时如下处理（将新增行添加到首行），待优化
        		trobj = $(rowHtml).prependTo(this.tbody);
        	}
        	//渲染数据后，执行rowrender(rowdata,rowElement),提供操作tr回调
            this._event("rowrender",null,{"data":data,"rowEl":trobj});
            
            
            //进行分组与合计处理
            //处理原则：
            //1.通过groupfield进行匹配,将新增数据行添加到相应的组最后一行
            //2.若是没有这个组则新建一个组将之归入组中，测试要注意组为null ""空字符串 情况
            var isgroupsummary = this.options.isgroupsummary;
            var groupfieldId = this.options.groupfield;
            var groupfieldTitle = this.options.grouptitlefield;
            var id="",groupId="",trrow,targetgroup,grouprows,groupLastRow;
            for(var i=0;i<data.length;i++){
            	id = data[i]["rowuuid"];
            	groupId = data[i][groupfieldId];
            	grouptitle = data[i][groupfieldTitle];
            	
            	trrow = this.tbody.find("#"+id);
            	targetgroup = this.tbody.find("tr[groupid='"+groupId+"']");
            	if(targetgroup[0]){
            		grouprows = targetgroup.nextUntil("tr.jazz-grid-group-row");
            		groupLastRow = grouprows.filter("tr.jazz-grid-row:last");
            		groupLastRow.after(trrow);
            	}else {
            		//重新在tbody中新建一个组
            		var groupTrHtml = "";
            		groupTrHtml +=  this._getGroupRowHtml(groupId,grouptitle);
            		if(isgroupsummary){
	            		groupTrHtml +=  this._getSummaryRowHtml("groupsummary");
	            	}
            		var groupTrs = $(groupTrHtml).prependTo(this.tbody);
            		groupTrs.filter("tr.jazz-grid-group-row").after(trrow);
            	}
            }
            //cell值改变时抛出cellvaluechange.summaryrow事件，修改summary 合计行数据
			if(this.options.isgroupsummary||this.options.ispagesummary){
				this.tbody.trigger("cellvaluechange.summaryrow");
			}
        }
		
	};
})(jQuery);

(function($){
	/**
	 * @desc 扩展gridtable分组功能
	 */
	jazz.grid.summarygridtable = {
		
		_bindSummaryGridTableEvent:function(){
			//如何响应单元格值发生变化时，合计行对应变化
			//可以让可编辑单元格抛出editorvaluechange event事件，然后
			//由summarygridtable对象接收，以此重新计算合计行
			var that = this;
			this.tbody.off("cellvaluechange.summaryrow").on("cellvaluechange.summaryrow", function(e) {
				
				var value = e.realvalue;
				
				alert(value);
	        });
		},
		
		/**
         * @desc 加载table数据
         * @param {rows} 待渲染的表格数据
         * @private
         */
        _renderData : function(rows) {
            if(!rows){
                return;
            }
            var lineIndex = 0;
            var rowHtml = [];
        	this._trObject().remove();
            lineIndex = this._trNumber();
            for (var i = 0; i < rows.length; i++) {
            	rowHtml.push(this._insertRowHtml(rows[i], i, lineIndex));
            }
            //1.合计行在表格的最后一行，并且只是对当前页数据进行合计
            rowHtml.push(this._getSummaryRowHtml("pagesummary"));
            this.tbody.append(rowHtml.join(""));
            //2.初始化时计算合计行数据
            var summaryrow = this.tbody.find("tr.jazz-grid-pagesummary-row");
            this._computeSummaryResult(rows,summaryrow);
            //3.渲染数据后，执行rowrender(rowdata,rowElement),提供操作tr回调
            var trObjs = this.tbody.children(".jazz-grid-row");
            this._event("rowrender",null,{"data":rows,"rowEl":trObjs});//this.tbody.find("tr")
        },
        
         /**
         * @desc 计算分组或者当前页数据合计值，提供了count/sum/max/min/average/自定义合计函数方式。
         * 		 并且，提供summaryrender 设置合计计算结果展示格式
         * @param {rows} 待合计数据行数据
         * @param {summaryrow} 待合计的分组行或者表格总合计jquery dom对象
         */
       	_computeSummaryResult: function(rows,summaryrow){
        	
        	var columnname="",issummary="",result=0,value;
            for (var j = 0, len = this.cols.length; j < len; j++) {
            	issummary = this.cols[j]['issummary'];
	            if(issummary){
	            	columnname = this.cols[j]['columnname'];
	            	summarytype = this.cols[j]['summarytype'];
	            	summaryrender = this.cols[j]['summaryrender'];
	            	
	            	var result = this._getSummaryTypeResult(columnname,rows,summarytype);
	            	result = this._getSummaryRenderResult(result,rows,summaryrender);
					var index = this.thead.find("th[name='"+columnname+"']").index();
					summaryrow.children().eq(index).text(result);
	            }
            }
        },
        _getSummaryTypeResult: function(columnname,rows,type){
        	if (type) {
        		var result;
        		if (type.indexOf("(") != -1) {
					result = this._customopration(type,rows);
					return result;
				}
				var temp = [],value;
				for (var i = 0; i < rows.length; i++) {
	        		value = rows[i][columnname];
	            	temp.push(value);
	            }
	            switch (type) {
	                case 'count':
	                    return rows.length;
	                case 'min':
	                    return Math.min.apply(null, temp);
	                case 'max':
	                    return Math.max.apply(null, temp);
	                case 'sum':
	                    return eval(temp.join('+'));
	                case 'average':
	                	var count = temp.length;
	                	var sum = eval(temp.join('+'));
	                	if(count>0){
	                		return Math.round(sum/count);
	                	}else{
	                		return 0;
	                	}
	                default:
	                    return '';
	            }
	        }else {
	        	return '';
	        }
        },
        _getSummaryRenderResult: function(result,rows,format){
        	if (format) {
        		if (format.indexOf("(") != -1) {
					result = this._customopration(format,result,rows);
					return result;
				}
	        }else {
	        	return result;
	        }
        },
        /*_computeSummaryResult: function(rows,summaryrow){
        	//暂时简单求和处理，之后再做和EXT那样的合计函数
        	var columnname="",issummary="",result=0,value;
            for (var j = 0, len = this.cols.length; j < len; j++) {
            	columnname = this.cols[j]['columnname'];
            	issummary = this.cols[j]['issummary'];
	            if(issummary){
		            result=0,value=null;
		        	for (var i = 0; i < rows.length; i++) {
		        		value = rows[i][columnname];
		            	result = result + parseInt(value);
		            }
					var index = this.thead.find("th[name='"+columnname+"']").index();
					summaryrow.children().eq(index).text(result);
	            }
            }
        },*/
        /**
         * @desc 拼接合计行html dom结构
         * @param {summary} 值为"groupsummary"或"pagesummary"
         * 表明是分组合计还是当前页全部数据合计
         */
        _getSummaryRowHtml: function(summary){
            var rowHtml = '<tr class="jazz-grid-'+summary+'-row">';
            rowHtml +='<th style="height:20px;"></th>';
            
            var isselect = this._isSelectHelper() && this._isSelectType();
            var islineno = this.options.lineno;
            var hj = summary=="groupsummary"?"合计":"总合计";
            if(isselect&&islineno){
            	rowHtml += '<td class="jazz-grid-cell jazz-grid-cell-box" colspan="2">'+hj+'</td>';
            	rowHtml += '<td class="jazz-grid-cell jazz-grid-cell-no" style="display:none;"></td>';
            }else{
            	if(isselect){
            		rowHtml += '<td class="jazz-grid-cell jazz-grid-cell-box" colspan="2">'+hj+'</td>';
            	}
            	if(islineno){
            		rowHtml += '<td class="jazz-grid-cell jazz-grid-cell-no">'+hj+'</td>';
            	}
            }
            var textalign;
            for (var j = 0, len = this.cols.length; j < len; j++) {
            	textalign = this.cols[j]['textalign'] || "";
        		if(textalign){
        			textalign = "text-align-"+textalign;
        		}
	            rowHtml += '<td class="jazz-grid-cell '+textalign+'">&nbsp;</td>';	
            }
            return rowHtml;
        }
		
	};
})(jQuery);

});
