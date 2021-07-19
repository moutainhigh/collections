(function( $, factory ){
	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',  'layout/jazz.RowLayout', 'layout/jazz.ColumnLayout'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.borderlayout
	 * @description border布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 */	
    $.widget("jazz.borderlayout", $.jazz.containerlayout, {
		
    	/** @lends jazz.borderlayout */
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutconfig} 布局需要的配置参数
         */
        layout: function(cthis, container, layoutconfig) {
        	//列布局时，禁止容器出现滚动条
        	container.css('overflow', 'hidden');

        	//获取容器的宽度和高度
        	this.cwidth = container.width(); this.cheight = container.height();
        	
        	//获取border布局下的全部子元素
        	var childs = container.find("div[region]");
        	
        	if(childs.length > 5){
        		jazz.error("border布局定义错误, 只能定义 east south west north center 五个部分，且只能定义一次！"); return false;
        	}
        	
        	//布局刷新时，重新初始化对象
        	//存储布局对象
        	this.bobject = {north: "", west: "", center: "", east: "", south: ""};
        	//布局中的region设置, 只能为north west center east south中的一个, 且不能重复出现
        	//this.regionnumber用于记录数量，避免重复出现
        	this.regionnumber = {north: 0, west: 0, center: 0, east: 0, south: 0};

        	//纵向  north center south, 对应行布局
        	//this.by 存放行布局用的高度，例：  ["20%", "*", "300"], yn 记录纵向元素的数量
        	this.by = []; var yn = 0;   
        	//横向  west center east, 对应列布局
        	//this.bx 存放列布局用的宽度，例： ["200", "*", "200"], xn 记录横向元素的数量
        	this.bx = []; var xn = 0;        	
        	
        	var $this = this, f = false;
        	
        	$.each(childs, function(i, obj){
        		var $obj = $(obj);
        		var region = $obj.attr("region");
        		
        		if(region == "center"){ f = true; }
        		
        		if(region){
        			region = $.trim(region);
        			$this.regionnumber[region] += 1;
        			if($this.regionnumber[region] > 1){
        				jazz.error("border布局的region属性设置错误, region=\""+region+"\", 重复设置！"); return false;
        			}
        			
        			if(" east west ".indexOf(region) >= 0){
        				$this.bobject[region] = {index: i, object: $obj, width: $obj.attr("width")};
        				xn += 1;
        			}else if(" north south ".indexOf(region) >= 0){
        				$this.bobject[region] = {index: i, object: $obj, height: $obj.attr("height")};
        				yn += 1;
        			}else if(" center ".indexOf(region) >= 0){
        				//自适应宽度和高度，不需要设置width和height
        				$this.bobject[region] = {index: i, object: $obj};
        				yn += 1; xn += 1;
        			}
        		}else{
        			jazz.error("border布局的region属性设置错误, region=\"east\" region=\"south\" region=\"west\" region=\"north\" region=\"center\""); return false;
        		}
        	});
        	
        	if(!f){
        		jazz.error("border布局的regsion属性设置错误, 必须要有region=\"center\""); return false;
        	}
        	
        	//将各部分region数量放入layoutconfig中，在调用列、行布局时，可用于判断各部分是否被定义
        	layoutconfig["regionnumber"] = this.regionnumber;
        	//将region对象放入layoutconfig中，在调用列、行布局时使用
        	layoutconfig["bobject"] = this.bobject;
        	
        	//在border布局中，north和south只需要设置高度即可，宽度会自适应容器大小
        	//west和east只需要设置宽度即可，高度会自适应
        	//如果不设置north south高度   west east宽度，取默认的宽度和高度
        	var defaultheight = "150", defaultwidth = "200";

        	var centerObj = this.bobject.center["object"];
        	//只存在center时
        	if(yn==1 && xn==1){
        		centerObj.css({border: 0, margin: 0, padding: 0, width: "100%", height: "100%"});
        	}
        	//如果大于1，说明存在列布局, 列布局控制 west center east
        	if(xn > 1){
        		//在center区域上部追加一个div， 用于存放 west center east，并按这个顺序存放。
        		this.colcontainerObj = $('<div style="overflow: hidden; height: 100%;">');
        		centerObj.before(this.colcontainerObj);
        		var westObj = this.bobject.west["object"], eastObj = this.bobject.east["object"];
        		if(westObj){
        			this.colcontainerObj.append(westObj);
        			var _width = this.bobject.west["width"];
        			if(!_width){ _width = defaultwidth; }
        			$this.bx.push(_width);
        		}
        		
        		this.colcontainerObj.append(centerObj);
        		//center区域的宽度
        		this.bx.push("*");
        		
        		if(eastObj){
        			this.colcontainerObj.append(eastObj);
        			var _width = this.bobject.east["width"];
        			if(!_width){ _width = defaultwidth; }
        			$this.bx.push(_width);        			
        		}
        	}
        	if(yn > 1){
        		var northObj = this.bobject.north["object"], southObj = this.bobject.south["object"];
        		if(northObj){
        			//通过索引值判断north是否为0，也就是是否在最上边，如果不在最上边则需要移动north区域的位置
        			if(this.bobject.north["index"] != 0){
        				container.prepend(northObj);
        			}
    				var _height = this.bobject.north["height"];
    				if(!_height){ _height = defaultheight; }
    				$this.by.push(_height);
        		}
        		
        		//center区域的高度
        		$this.by.push("*");
        		
        		if(southObj){
        			//判断当前south是否为最后一个元素，如果不是则将其移动到最后位置
        			var t = container.children("div:last-child");
        			if(" south ".indexOf(t.attr("region")) < 0){
        				container.append(southObj);
        			}
    				var _height = this.bobject.south["height"];
    				if(!_height){ _height = defaultheight; }
    				$this.by.push(_height);        			
        		}

        		//调用行布局
        		this._rowLayout(cthis, container, layoutconfig);
        	}
        	if(xn > 1){
        		//调用列布局
        		this._columnLayout(cthis, container, layoutconfig);
        	}

        	//监听父容器大小改变
        	this.$cthis = cthis; this.$container = container; this.$layoutconfig = layoutconfig; this.$xn = xn; this.$yn = yn;
        },
        
        /**
         * @desc 调用列布局
         */
        _columnLayout: function(cthis, container, layoutconfig, f){
    		var _col = jazz.layout.column;
    		container[_col]();
    		layoutconfig["width"] = this.bx;
    		layoutconfig["isborderlayout"] = true;
    		this.obj_c = container.data(_col);
    		this.obj_c.layout(cthis, this.colcontainerObj, layoutconfig);
    		//除了中间区域外，还包含其他区域
//    		if(this.$xn > 1 && f==true){
//    			this.colcontainerObj.notifyResize();
//    		}
        },
        
        /**
         * @desc 调用行布局
         */
        _rowLayout: function(cthis, container, layoutconfig){
    		var _row = jazz.layout.row;
    		container[_row]();
    		layoutconfig["height"] = this.by;
    		layoutconfig["isborderlayout"] = true;
    		//中间区域包裹，列布局的对象
    		layoutconfig["centerobject"] = this.colcontainerObj;
    		this.obj_r = container.data(_row);
    		this.obj_r.layout(cthis, container, layoutconfig);	
        },
        
        /**
         * @desc 改变border布局的大小
         */
        resizeBorder: function(){
        	if(this.$yn > 1){ 
        		this._rowLayout(this.$cthis, this.$container, this.$layoutconfig);
        	}
    		if(this.$xn > 1){
    			this._columnLayout(this.$cthis, this.$container, this.$layoutconfig, true);
    		}
        	var ui = {};
        	if(this.regionnumber["north"] == 1){
        		ui["north"] = {"height": this.bobject.north.object.height(), "width": this.bobject.north.object.width()};
        	}
        	if(this.regionnumber["south"] == 1){
        		ui["south"] = {"height": this.bobject.south.object.height(), "width": this.bobject.south.object.width()};
        	}
        	if(this.regionnumber["center"] == 1){
        		ui["center"] = {"height": this.bobject.center.object.height(), "width": this.bobject.center.object.width()};
        	}
        	if(this.regionnumber["west"] == 1){
        		ui["west"] = {"height": this.bobject.west.object.height(), "width": this.bobject.west.object.width()};
        	}
        	if(this.regionnumber["east"] == 1){
        		ui["east"] = {"height": this.bobject.east.object.height(), "width": this.bobject.east.object.width()};
        	}
        	this.$cthis._event("borderresize", null, ui);      	
        },
        
		/**
         * @desc 刷新布局
         * @param {componentObject} 当前组件的this对象
         * @param {layoutcontainer} 需要渲染的容器对象
         * @param {layoutconfig} 布局需要的配置参数
         */        
        reflashLayout: function(componentObject, layoutcontainer, layoutconfig){
        	this.resizeBorder();
        },
        
        /**
         * @desc 布局中区域的打开关闭的控制,只控制border布局 column布局 row布局
         * @param region 区域 border布局 
         *        region为east west north south
         * @param showSize 显示大小
         */
        layoutSwitch: function(region, showSize){
        	if("east"==region || "west"==region){
        		this.$container.data(jazz.layout.column).layoutSwitch(region, this.$xn, showSize);
        	}else if("north"==region || "south"==region){
        		this.$container.data(jazz.layout.row).layoutSwitch(region, this.$yn, showSize);
        	}
        },
        
        /**
         * @desc 设置布局自身容器的overflow样式
         */        
        reflashLayoutBefore: function(){
        	if(this.$yn > 1){
        		this.obj_r.reflashLayoutBefore();
        	}
    		if(this.$xn > 1){
    			this.obj_c.reflashLayoutBefore();
    		}        	
        },
        
        /**
         * @desc 还原布局自身容器的overflow样式
         */
        reflashLayoutAfter: function(){
        	if(this.$yn > 1){
        		this.obj_r.reflashLayoutAfter();
        	}
    		if(this.$xn > 1){
    			this.obj_c.reflashLayoutAfter();
    		}         	
        }
    });
});
