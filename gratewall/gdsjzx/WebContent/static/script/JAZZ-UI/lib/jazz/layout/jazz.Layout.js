(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 
		         'layout/jazz.AutoLayout',
		         'layout/jazz.ColumnLayout',
		         'layout/jazz.FitLayout',
		         'layout/jazz.RowLayout',
		         'layout/jazz.TableLayout',
		         'layout/jazz.BorderLayout',
		         "jazz.BoxComponent"], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.layout
	 * @description 调用布局的工具类, 可以脱离容器直接调用布局。
	 * @constructor
	 * @extends jazz.boxComponent
	 */
    $.widget("jazz.layout", $.jazz.boxComponent, {

    	/** @lends jazz.layout */
    	options: /** @lends jazz.layout# */ {
    		
        	/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default layout
    		 */
    		vtype: "layout",

			/**
			 *@type String
			 *@desc 容器指定的布局类型
			 *@default 'auto'
			 */    		
    		layout: "auto",
    		
			/**
			 *@type String
			 *@desc 布局的配置参数
			 *@default {}
			 */
    		layoutconfig: {}
    	},
    	
		/** @lends jazz.layout */
		/**
		 * @desc 创建组件
		 * @private
		 */    	
    	_create: function(){
    		this._super();
    		//this.layoutcontainer在boxComponent中调用，动态刷新布局
    		this.layoutcontainer = this.element;
    	},
    	
		/**
		 * @desc 初始化
		 * @private
		 */       	
    	_init: function(){
    		this._super();
        	var layoutvtype = jazz.layout[this.options.layout];
        	//如此获取obj是为了this.element转化为相应的layouttype的jquery布局对象
        	var obj = this.layoutcontainer[layoutvtype]();
        	//利用jquery的data方式取出缓存jquery对象，为了调用该对象的方法而使用。
        	this.layoutobject = obj.data(layoutvtype);
        	this.layoutobject.layout(this, this.layoutcontainer, this.options.layoutconfig);
    	},
        
		/**
         * @desc 高度	 
		 * @private
         */		
		_resizeHeight: function(){
			this._height();
			//确保青海项目对panel中的content内容区定义布局时能够刷新子组件
			this._reflashLayout();
			this._reflashChildHeight();
		},		
		
		/**
         * @desc 宽度	 
		 * @private
         */		
		_resizeWidth: function(){
			this._width();
			//确保青海项目对panel中的content内容区定义布局时能够刷新子组件
			this._reflashLayout();
			this._reflashChildWidth();
		},
    	
    	/**
         * @desc 动态改变属性
         * @param {key} 对象的属性名称
         * @param {value} 对象的属性值
		 * @private
         */
        _setOption: function(key, value){
			if(key == "layoutconfig" && typeof value == "object"){
    			this.options.layoutconfig = value;
            	try{
            		this.layoutobject.data(jazz.layout[this.options.layout]).layout(this.cthis, this.layoutcontainer, this.options.layoutconfig);
            	}catch(e){
            		jazz.error('错误的修改布局！！！');
            	}	
			}
			this._super(key, value);
        },
        
        /**
         * @desc获得布局的容器对象
         */
        getLayoutobject: function(){
        	return this.layoutobject;
        },

		/**
         * @desc 刷新布局
         * @param {componentObject} 当前组件的this对象
         * @param {layoutcontainer} 需要渲染的容器对象
         * @param {layoutconfig} 布局需要的配置参数
         */        
        reflashLayout: function(componentObject, layoutcontainer, layoutconfig){
			if(this.layoutobject){
				this.layoutobject.reflashLayout(this, this.layoutcontainer, this.options.layoutconfig);				
			}
        },
        
        /**
         * @desc 设置布局自身容器的overflow样式
         */        
        reflashLayoutBefore: function(){
        	this.layoutobject.reflashLayoutBefore();
        },
        
        /**
         * @desc 恢复布局自身容器的overflow样式
         */        
        reflashLayoutAfter: function(){
        	this.layoutobject.reflashLayoutAfter(); 
        }          
    });
});