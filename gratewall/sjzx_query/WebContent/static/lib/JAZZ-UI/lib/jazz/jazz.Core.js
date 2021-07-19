(function($){
window.undefined = window.undefined;
var jazz = {
    /**
     * The version of the framework
     * @type String
     */
    version : '1.0',
    
    /**
     * namespace
     * @param {ns} 命名空间
     */    
	namespace: function(ns) {
	     if (!ns || !ns.length) {
	        return null;
	     }
	     var levels = ns.split(".");
	     var nsobj = jazz;
	     for (var i=(levels[0] == "jazz") ? 1 : 0; i<levels.length; ++i) {
	           nsobj[levels[i]] = nsobj[levels[i]] || {};
	           nsobj = nsobj[levels[i]];
	     }
	     return nsobj;
	},

    /**
     * 日志
     * @param {info} 打印的日志内容
     */   	
	log: function(info){
		if(this.config.logger){
			return console.log('***JAZZ-UI LOG.INFO***', info);
		}else{
			return false;
		}
	}	
};

jazz.namespace("layout");

jazz.namespace("config");

jazz.namespace("util");

jazz.namespace("window");

window.jazz = jazz;

})();

(function($){

    /**
     * 内部方法，建立当前组件与上级组件的关系
     */
	$.extend(jazz, {
	    /**
	     * 组件结构树
	     * @param {element} 创建组件的对象
	     * @param {createtype} 创建组件的类型  '0' vtype形式创建, '1' 原生$方式创建
	     */
		vtypetree: function(element, createtype){
	        if(!element.data("vtypetree")){
	        	
	        	// 查找元素的parent节点
	        	var parent = element.parents("div[vtype]:first");
	        	// 如果没找到，设置为BODY
	        	if( parent.size() == 0 ){
	        		parent = $("BODY");
	        	}
	        	
	        	// 设置父节点的data中的child
	        	var parentData = parent.data("vtypetree") || {parent:"", child:{}, createtype: createtype};
	        	var childName = element.attr("name") || element.attr("id") || jazz.getId();
	        	parentData.child[childName] = element;
	        	parent.data("vtypetree", parentData);
	        	
	        	// 设置当前节点中的parent的值和vtype的值
	        	var nodeData = {parent:"", child:{}, createtype: createtype};
	        	nodeData["parent"] = parent;
	        	nodeData["vtype"] = element.attr("vtype");
	        	element.data("vtypetree", nodeData);
	            _vtype.push(nodeData['vtype']);

	        	//jazz.log("*** 树形结构如下： ***");
	        	//console.log(element.data("vtypetree"));
	        }	        
		}, 
		
	    /**
	     * 判断组件树中是否存在组件
	     * @param {element} 创建组件的对象
	     */
		_isComponentInited: function(element){
	        var nodeData = element.data("vtypetree") || {};
	        if(!!nodeData.vtype){
	            return !!element.data(nodeData.vtype);
	        }
	        nodeData = null;
	        return false;
	    },
	    
	    _parse: function( element ){
			
			jazz.vtypetree(element, "0");
		
	        /**
	         * 检查元素的代码集
	         */
	        var dataurl = element.attr("dataurl");
	        if(dataurl && !/^\s*[\[|{](.*)/.test(dataurl)){
	            return [dataurl];
	        }

	        /**
	         * 处理gridcolumn中的代码集
	         */
	        var valueSet = [];
	        if('gridcolumn' === element.attr("vtype")){
	            element.children().children().each(function(){
	            	var dataurl = $(this).attr("dataurl");
	            	if(dataurl && !/^\s*[\[|{](.*)/.test(dataurl)){
	                	valueSet.push(dataurl); 	
	                }
	            });
	            return valueSet;
	        }
	    },
	    
	    /**
	     * 初始化vtyle节点
	     * （一次只初始化一个，先把之间初始化完成后，再初始化子节点）
	     */
	    _initComponent: function( element, options ){
	        var nodeData = element.data("vtypetree") || {};
	        var el = $(element);
	        // 如果当前元素有vtype属性，
	        if(!!nodeData.vtype){
	            // 判断UI组件是否已经初始化，如果未初始化，才执行初始化。
	            // 通过vtype方式创建的组件只能是createtype==0
	            if( !jazz._isComponentInited(el) && nodeData["createtype"]=="0"){	                
	                var attrtooptions = jazz.attributeToOptions( el );
	                if(!!options){
	                	attrtooptions = $.extend(attrtooptions, options);
	                }
	                attrtooptions["createtype"] = nodeData["createtype"];
	                (el[nodeData.vtype])(attrtooptions);
	            }
	        };
	        var childNodes = nodeData.child;
	        // 如果存在childNodes，初始化子元素
	        if(!!childNodes){
	            for(var c in childNodes){
	                jazz._initComponent( childNodes[c] );
	            }
	        }
	        //调用组件创建完成时的方法
	        if(!!nodeData.vtype && jazz._isComponentInited(el) && nodeData["createtype"]=="0"){
	        	(el[nodeData.vtype])("finish");
	        	
	        	//当容器外存在滚动条时，要重新刷新子容器的宽度和高度
	        	var aaa = el.data(nodeData.vtype);
	        	var bb = aaa.element.attr("refresh");
	        	if(bb == "1"){
	        		//jazz.log("finish refresh==="+bb+" name="+aaa.options.name);
	        		aaa._reflashChildWidth();
	        		aaa._reflashChildHeight();
	        	}
	        }
	        
	    },
	    
	    /**
	     * 从body开始刷新组件
	     */
	    _bodyReflashChild: function(){
	        var nodeData = $("body").data("vtypetree") || {};
	        var childNodes = nodeData.child;
	        
	        // 如果存在childNodes，初始化子元素
	        if(!!childNodes){
	            for(var c in childNodes){
	                var childNode = childNodes[c]; 
	                var vtype = childNode.attr("vtype");
	                var el = $(childNode).data(vtype);
		    	        
    	        		el._resizeWidth();
    	        		el._resizeHeight();
		    	                 
	            }
	        }
	        
	    }	    
	});
	
    /**
     数据存储在节点中的格式
     var data = {
		    vType: "form",
		    elementType: "复合组件",
		    layout: "fit",
		    parent: obj,
		    child: {"childName1":xxx, "childName2":xxx}
		 };
     */
    var _vtype = [];

    /**
     * 通过分析vtype树,动态加载组件所需要的文件
     * 相同文件 require 只会加载一次
     * @param vtype 当前页面的vtypetree
     * @param fn 加载完文件的回调函数,通常为组件初始化
     * @private
     */
    var _getRequireFile = function (vtype, fn) {
      var vtypetree = [],
        files = [],
        filePath = jazz.config.jsFilePath || {};
      //1. vtype去重复
      $.each(vtype, function (i, el) {
        if ($.inArray(el, vtypetree) === -1) {
          vtypetree.push(el);
        }
      });
      //2. 查找对应文件
      $.each(vtypetree, function (i, v) {
        if ($.inArray(filePath[v], files) === -1) {
          files.push(filePath[v]);
        }
      });

      //3. 加载文件
      //加载成功后初始化组件
      require(files, function () {
        fn();
      });

    };
	
    /**
     * vtype树操作：解析当前元素及子元素的vtype，形成vtype树，并初始化相关组件
     */
    $.fn.parseComponent = function(options){
        //var startTime = new Date();

        var $this = $(this);
        var links = [], ret;
        
        //加if过滤的目的是避免vtype调用与$调用时，出现重复执行
        //if(!$this.data("vtypetree")){
	        // 1.解析生成vtype树
	        // 如果当前元素具有vtype属性，先解析当前的元素
	        if($this.attr("vtype")){
	            ret = jazz._parse($this);
	            if(ret && ret.length){
	                links = links.concat(ret);
	            }
	        }
	
	        // 解析元素的子元素
	        $this.find("div[vtype]").each(function(i, obj){
	            ret = jazz._parse($(this));
	            if(ret && ret.length){
	                links = links.concat(ret);
	            }
	        });
	
        //}
        if(jazz.config.isUseRequireJS === true){
        	//动态加载js文件
	        _getRequireFile(_vtype, function () {
	          // 2.根据vType树初始化相关组件（从vType树的根节点（BODY）开始，深度优先算法）
	          jazz._initComponent($this, options);
	
	          // 3. 发送请求代码集
	          if (window.G) {
	            G.processData(links, true);
	          } else {
	            //jazz.log("这是代码集组件还未初始化完成");
	          }
	        });
        }else{
        	if (window.G) {
	            G.processData(links, true);
	        } else {
	           //jazz.log("这是代码集组件还未初始化完成");
	        }
        	jazz._initComponent( $this, options );
//        	if($("body").attr("refresh")=="1"){
//debugger;
//      			jazz._bodyReflashChild( $this );
//        	
//        	}
        }
        
        //var endTime1 = new Date();
        //jazz.log( "获取vtype树耗时：" + (endTime1-startTime)+"ms" );

        // 根据vtype树初始化相关组件（从vtype树的根节点（BODY）开始，深度优先算法）
        //var endTime2 = new Date();
        //jazz.log( "初始化组件耗时：" + (endTime2-endTime1)+"ms" );

    };
    
    /**
     * vtype树操作：获取当前节点的子节点
     */
    $.fn.getChildrenComponent = function(){
        var nodeData = $(this).data("vtypetree") || {};
        return nodeData.child || {};
    };
    
    /**
     * 通知当前对象下边的组建改变大小
     */
    $.fn.notifyResize = function(){
    	var parent = $(this).parents("div[vtype]:first");
    	var name = parent.attr("name") || "";
    	if(name){
    		$.each(parent.getChildrenComponent(), function(){
    			var element = $(this);
				var vtype = element.attr("vtype");
				//修改子组件的宽度
				element.data(vtype)._resizeWidth();
				element.data(vtype)._resizeHeight();
    		});
    	}
    };
    
    /**
     * 页面初始化完成后执行
     */
    $(function(){
//    	var data1 = new Date().getTime();
    	$("BODY").parseComponent();
//    	var data2 = new Date().getTime();
    	//重新刷新组件
    	//jazz.util.refreshSize();
//	    var data3 = new Date().getTime();
	    
//	    console.log("全部时间="+(data3-data1));
//	    console.log("组件渲染="+(data2-data1));
//	    console.log("二次刷新="+(data3-data2));
    });
    
    /**
     * @desc 封装给开发者,工厂总代理
     */

    /**
       * 插件列表
       */
      var plugins = (function () {
        var ary = [], p;
        for (p in jazz.config.jsFilePath) {
          if (jazz.config.jsFilePath.hasOwnProperty(p)) {
            ary.push(p);
          }
        }
        return ary;
      })();

      /**
       * 为插件注册一个代理方法
       * 作为jquery的插件,为了保证 $('div').xxx(); 这种调用方式
       * 在插件内部, 加载真正的jazz文件,覆盖当前的同名插件
       * 这个函数理论上每个插件只执行一次, 就是为了加载对应的js文件
       */
      $.each(plugins, function (i, pluginName) {
        $.fn[pluginName] = function (options, nodes) {
          this.initflag = false;
          var that = this;
          if (!this.initflag) {
            require([jazz.config.jsFilePath[pluginName]], function () {
              this.initflag = true;
              if(pluginName === 'tree'){
                $.fn.zTree.init(that, options['setting'], options['znodes']);
              }else{
                that[pluginName](options);
              }
            });
          } else {
        	   jazz.log(' something unreachable unless error');
          }
        };
      });
    
})(jQuery);
(function($){
	
	jazz.config = {
			/**上下文路径 **/ 
			contextpath: '',
			
			/**生成组件元素动态生成ID时，所需要的计数 **/
			compNumber: 1000,
			
			/**提示框error warn提示的数量,默认0只能提示一次**/
			errorMessageNumber: 0,

			/**附件上传需要设置的上传路径 **/
			default_upload_url: '',
			
			/**附件下载需要设置的路径 **/
			default_download_url: '',
			
			/**附件预览需要设置的路径 **/
			default_preview_url: '',			
			
			/**附件加载的swfupload.swf文件 **/
			default_flash_url: '',
			
			/**附件加载的swfupload_fp9.swf文件 **/
			default_flash9_url: '',
			
			/**控制点击按钮点击时，是否置灰 **/
			delayButtonDisabled: false,
			 
			/**表单组件中下拉图标的所占宽度 **/
			fieldIconWidth: 20,
			
			/**表单字段中空白文本  **/
			fieldBlankText: "",

			/**表单字段中的默认宽度  **/
			fieldDefaultWidth: 150,
			
			/**表单字段中的默认高度, 包含margin border padding  **/
			fieldDefaultHeight: 46,
			
			/**表单字段中的label字段宽度  **/
			fieldLabelWidth: 80,
			
			/**请求代码集时,是否合并相同请求**/
			isGroupRequest: false,
			
			/**是否使用requireJS动态加载文件**/
			isUseRequireJS: false,
			
			jazzRoot: '/JAZZ/jazz/',
			
			/**vtype对应的组件所在的js文件路径(相对于当前目录) **/
			jsFilePath : {
			  /*'querypanel'         : '../lib/jazz.Adapter',
			  'accordion_qh'       : '../lib/jazz.Adapter',*/
			  'accordionpanel'     : 'jazz.AccordionPanel',		
			  'attachment'         : 'jazz.Attachment',
			  'button'             : 'jazz.Button',
			  'date'               : 'jazz.Date',
			  'DataAdapter'        : 'DataAdapter',
			  /*'form'               : 'jazz.Form',*/
			  'formpanel'          : 'jazz.FormPanel',
			  'gridtable'          : 'jazz.GridPanel',
			  'gridcolumn'         : 'jazz.GridPanel',
			  'gridpanel'          : 'jazz.GridPanel',
			  'icon'               : 'jazz.Icon',
			  'imageviewer'        : 'jazz.ImageView',
			  'layout'             : 'jazz.Layout',
			  'loading'            : 'jazz.Loading',
			  'menu'               : 'jazz.Menu',
			  'contextmenu'        : 'jazz.Menu',
			  'message'            : 'jazz.Message',
			  'paginator'          : 'jazz.Paginator',
			  'panel'              : 'jazz.Panel',
			  'tabpanel'           : 'jazz.TabPanel',
			  'toolbar'            : 'jazz.Toolbar',
			  'tooltip'            : 'jazz.Tooltip',
			  'tree'               : 'jazz.Tree',
			  'zTree'              : 'jazz.Tree',
			  'paginator'          : 'jazz.Paginator',
			  'window'             : 'jazz.Window',
			  /** 表单元素 **/
			  'autocompletecombox' : 'form/jazz.form.AutocompleteComboxField',
			  'checkboxfield'      : 'form/jazz.form.CheckboxField',
			  'colorfield'         : 'form/jazz.form.ColorField',
			  'comboxfield'        : 'form/jazz.form.ComboxField',
			  'comboxtreefield'    : 'form/jazz.form.ComboxTree',
			  'datefield'          : 'form/jazz.form.DateField',
			  'dropdownfield'      : 'form/jazz.form.DropdownField',
			  'hiddenfield'        : 'form/jazz.form.HiddenField',
			  'numberfield'        : 'form/jazz.form.NumberField',
			  'passwordfield'      : 'form/jazz.form.PasswordField',
			  'radiofield'         : 'form/jazz.form.RadioField',
			  'textareafield'      : 'form/jazz.form.TextareaField',
			  'textfield'          : 'form/jazz.form.TextField'
			},
			
			/**输出日志开关 **/
			logger: false,
			
			/**(sword 平台)  other(其他) **/
			platForm: 'other',
			
		    /**控制textareafield组件的前缀和后缀显示的位置  0 左右显示 1 上下显示**/
		    prefixPosition: 0,
		    
		    paginatorStyle: "text", //"icon"
			
			/**表单验证时，提示错误的图片在表单中所在用的宽度 **/
			ruleImgWidth: 20,
			
			/**grid定义滚动条所占用的宽度 **/
			scrollWidth: 17,
			
			/**点击提交按钮时，是否加载loading遮罩层 true 加载  false 不加载 **/
			submitShowLoading: false,
			
			/**表单组件的提示值 **/
			valuetip: "",
			
			/**遮罩层默认z-index值 **/
			zindex: 10000,
			
			/**
			 * @version 0.5
			 * @name i18n
			 * @description 参数类。
			 */	
		   i18n: {
		   	    chooseFile:"请选择文件"
		   	    ,password:"请输入密码"
		   	    ,wait:"请稍后..."
		   	    ,fileName:"文件名"
		   	    ,fileSize:"文件大小"
		   	    ,fileDelete:"删除"
		   	    ,fileDownload:"下载"
		   	    ,fileEdit:"编辑"
		   	    ,fileAffix:"附件"
		   	    ,fileAdd:"增加"
		   	    ,fileCancel:"取消"
		        ,number: '数字'
		        ,numberInt: '整数'
		        ,numberFloat: '浮点数'
		        ,numberPlusInt: '大于等于0的整数'		   	    	
		   	    ,months: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一','十二']
		   	    ,days: ['日','一', '二', '三', '四', '五', '六']
		   	    ,dateOrder:['年','月','日','时','分','秒']
		   	    ,bizSucMsg:"校验成功!"
		   	    ,bizFaiMsg:"校验失败!"
		   	    ,sysMsg:"系统错误!"
		   	    ,saveSuc:"保存成功!"
		   	    ,saveFai:"保存失败!"
		   	    ,save:"提交"
		   	    ,cancel:"取消"
		   	    ,button:"按钮"
		   	    ,firstPage:"首页"
		   	    ,endPage:"末页"
		   	    ,nextPage:"下一页"
		   	    ,previousPage:"上一页"
		   	    
		   	    ,titleName:"提示框"
		   	    ,okBtnName:"确定"
		   	    ,cancelBtnName:"取消"
		   	    ,defineBtnName:"自定义"
		   	    ,boxMin:"最小化"
		   	    ,boxMax:"最大化"
		   	    ,boxNatural:"正常化"
		   	    ,boxClose:"关闭"
	
		   	    ,selectLoading:"正在加载数据请稍后..."
	
		   	    ,toolEdit:"编辑"
		   	    ,toolNew:"新建"
		   	    ,toolDel:"删除"
		   	    ,toolFresh:"刷新"
		   	    ,toolOpen:"打开"
		   	    ,toolFind:"查找"
		   	    ,toolSave:"保存"
		   	    ,toolBack:"返回"
		   	    ,toolExport:"导出"
		   	    ,toolAddtime:"增加时间"
		   	    ,toolReducetime:"减少时间"
		   	    ,toolAddright:"授权"
		   	    ,toolReduceright:"撤权"
		   	    ,num:"数字"
		   	    ,numInt:"整数"
		   	    ,numFloat:"浮点数"
		   	    ,numScience:"科学计数法"
		   	    ,character:"字符"
		   	    ,chinese:"汉字"
		   	    ,twoBytes:"双字节"
		   	    ,english:"英文"
		   	    ,date:"日期格式不正确"
		   	    ,numChar:"数字或字符"
		   	    ,numEnglish:"数字英文字符"
		   	    ,qq:"QQ号码"
		   	    ,'telephone':'座机号码',
		   	    'cellphone':'移动电话',
		   	    'idcard':'请输入正确的身份证号码',
		   	    'postal':'邮政编码',
		   	    'currency':'美元',
		   	    'email':'邮箱地址',
		   	    'url':'URL地址',
		   	    'and1':',且',
		   	    'or':',或者',
		   	    'must':'不能为空',
		   	    'contrast':'数值',
		   	    'range':'数值范围为',
		   	    'customCheckStyle':'不允许输入以下字符',
		   	    'length1':'字符串长度为',
		   	    'customFunction':'自定义校验'
		   	    ,area:['','','','','','','','','','','','北京','天津','河北','山西','内蒙古','','','','','','辽宁','吉林','黑龙江','','','','','','','','上海','江苏','浙江','安微','福建','江西','山东','','','','河南','湖北','湖南','广东','广西','海南','','','','重庆','四川','贵州','云南','西藏','','','','','','','陕西','甘肃','青海','宁夏','新疆','','','','','','台湾','','','','','','','','','','香港','澳门','','','','','','','','','国外'],
		   	    'tabMenuFresh':'刷新',
		   	    'tabMenuClose':'关闭当前项',
		   	    'tabMenuCloseAll':'关闭所有项',
		   	    'tabMenuCloseOthers':'关闭其他项'
		   	}
	};
	
	var _config = window.JAZZUICONFIG || {};
	$.extend(jazz.config, _config);		

})(jQuery);

(function($){	

	/**
	 * @version 1.0
	 * @name jazz.util
	 * @description 工具类。
	 * @constructor
	 * @example jazz.util.xxx();
	 */
   jazz.util = {
		
		/** @lends jazz.util */
		   
		/**
		 * @desc 将div属性转换成options属性
		 * @param {obj} div对应的jquery对象
		 * @return Object
		 * @public
		 */
		attributeToOptions: function(obj){
	    	var att = null;
	    	if(obj instanceof jQuery){
	    		if(obj.size() < 1){
	    			jazz.log("未找到满足条件的对象，无法获取全部属性，直接返回空json");
	    			return {};
	    		}
	    		att = obj.get(0).attributes;
	    	}else{
	    		att = obj.attributes;
	    	}
	    	var j = 0;
	    	var attObj = '{';
	    	for(var i=0, len = att.length; i< len; i++){
	    		var o = att[i];
	    		if(o.specified){
	    			var _v = jQuery.trim(o.value+''), f = 0; 
	    			if(_v.length>0){
	    				_v = _v.replace(/[\r\n|\t]/g, " ");
	    				//if(/^\s*[\[|{](.*)[\]|}]\s*$/.test(_v)){ 
	    				if((_v+"").charAt(0) == "[" || (_v+"").charAt(0) == "{"){
	    					f = 1;
	    				}
	    			}else{
	    				_v = "";
	    			}
	    			if(j===0){
	    				attObj = attObj +'"'+ o.name +'": ';
	    			}else{
	    				attObj = attObj + ', "'+ o.name +'": ';
	    			}
	    			if(f==0){
	    				attObj = attObj + '"'+_v+'"';
	    			}else{
	    				attObj = attObj + _v;
	    			}
	    			j++;
	    		}
	    	}
	    	    attObj = attObj + '}';
	    		return jazz.stringToJson(attObj);
		},   

//		/**
//		 * @desc 清空选中
//		 * @example jazz.util.clearSelection();
//		 * @public
//		 */
//	    clearSelection: function() {
//	        if(window.getSelection) {
//	            if(window.getSelection().empty) {
//	                window.getSelection().empty();
//	            } else if(window.getSelection().removeAllRanges) {
//	                window.getSelection().removeAllRanges();
//	            }
//	        } else if(document.selection && document.selection.empty) {
//	                document.selection.empty();
//	        }
//	    },		
		
	   /**
		 * @desc 调用确认提示框
		 * @param {message} 提示的信息内容
		 * @param {sure} function 点击确定按钮的回调函数
		 * @param {cancel} function 点击取消按钮的回调函数
		 * @example jazz.util.confirm("提示信息内容！", function(){  });
		 */		
		confirm: function(message, sure, cancel){
			if(jazz.config.isUseRequireJS === true){
				require(['jazz.Message'], function(){				
					if(jazz.config.errorMessageNumber == 0){
						 $('<div>').appendTo(that.getBodyObject()).message({title: '确认信息', showtype: 3, detail: message, sure: sure, cancel: cancel});
					 }
				});
			}else{
				if(jazz.config.errorMessageNumber == 0){
					$('<div>').appendTo(this.getBodyObject()).message({title: '确认信息', showtype: 3, detail: message, sure: sure, cancel: cancel});
				}
			}
		},			   
		   
	    /**
         * @desc 表单验证出错时调用的提示信息
         * @param {$this} 组件类对象
         * @param {val} 输入框输入值
         * @param {rule} 验证规则
         * @param {regMsg} 自定义函数显示消息
         * @return Boolean
         */		
		doTooltip: function($this, val, rule, regMsg){
			var ruleImg = $this.ruleImg, ruleType = $this.options.ruletype;
			obj = jazz_validator.doValidator(val, rule, regMsg, $this);
			if($this.options.msg){
				obj['msg'] = $this.options.msg;
			}
			if(ruleType == 2){
				$this.parent.tooltip({
					isbindevent: false,
			    	content: obj.msg,
			    	iconclass: 'jazz-tooltip-default-icon',
			    	position: {
			    		at: 'left bottom',
                        my: 'left top',
                        collision: 'flipfit flipfit',
                        of: $this.parent			    		
			    	}
				});
			}
			if(!obj.state){
				$this.parent.addClass('jazz-validator-border'); 
				if(ruleType == 2){
					$this.parent.tooltip("show");
					$this.parent.off(".tooltip")
					  .on("mouseover.tooltip", function(){ $this.parent.tooltip("show"); })
					  .on("mouseout.tooltip",  function(){ $this.parent.tooltip("hide"); })
					  .on("blur.tooltip",  function(){ $this.parent.tooltip("hide"); });
				}else if(ruleType == 3){
					var val = $("#val_"+$this.options.name);
					if(val.length == 1){
						val.empty();
						val.append('<div class="jazz-validator-t3-img"></div><div class="jazz-validator-t3">'+$this.options.msg+'</div>');
						val.show();
					}
				}else{
					ruleImg.removeClass('jazz-helper-hidden');
					ruleImg.attr("title", obj.msg);		
				}
			}else{
				$this.parent.removeClass('jazz-validator-border');
				if(ruleType == 2) {
					$this.parent.off(".tooltip");
					$this.parent.tooltip("hide");
				}else if(ruleType == 3){
					var val = $("#val_"+$this.options.name);
					if(val.length == 1){
						val.hide();
					}
				}else if(ruleType != 2){
					ruleImg.addClass('jazz-helper-hidden');
				}
			}
			
			return obj.state;
		},		
		
//	    escapeHTML: function(value) {
//	        return value.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
//	    },
		
	    /**
         * @desc 转义字符串
         * @param {string} 要转义的字符串
         * @example 'animals.sheep[1]'.escapeRegExp();  //returns 'animals\.sheep\[1\]'
         * @return String
         * @public
         */				
	    escapeRegExp: function(string) {
	        return string.replace(/([.?*+^$[\]\\(){}|-])/g, "\\$1");
	    },		
		
	   /**
		 * @desc 调用错误提示框
		 * @param {message} 提示的信息内容
		 * @param {sure} function 点击确定按钮的回调函数
		 * @example jazz.util.error("提示信息内容！", function(){  });
		 */   
		error: function(message, sure){
			var that = this;
			if(jazz.config.isUseRequireJS === true){				
				if(jazz.config.errorMessageNumber == 0){
					$('<div>').appendTo(that.getBodyObject()).message({title: '错误信息', showtype: 1, detail: message, sure: sure});				 
				}
			}else{
				if(jazz.config.errorMessageNumber == 0){
					$('<div>').appendTo(this.getBodyObject()).message({title: '错误信息', showtype: 1, detail: message, sure: sure});				 
				}
			}
		},			   
		
    	/**
         * @desc 返回当前窗体的body象
		 * @return Object
         */		
		getBodyObject: function(){
			if(!this.$bodyobject){
				this.$bodyobject = $('body');
			}
			return this.$bodyobject;			
			
//			var top = this.getTop();
//			if(!top.jazz.$bodyobject){
//				top.jazz.$bodyobject = top.document.body;
//			}
//			return top.jazz.$bodyobject;
		},
		
    	/**
         * @desc 获取随机生成的ID值 comp: componnet简写 j: jazz
		 * @example jazz.util.getId();
		 * @return String
		 * @public
         */		
		getId: function(){
			return "comp_j_" + jazz.getRandom() + "_" + (++jazz.config.compNumber);			
		},		
		
    	/**
         * @desc 获取通过http://localhost:8080/JAZZ?params=123传入当前页面的参数
         * @param {name} 参数名
		 * @example var paramsValue = jazz.util.getParameter("params");
		 * @return String
		 * @public
         */ 		   
		getParameter: function(name){
			var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
			var r = window.location.search.substr(1).match(reg); 
			if (r!=null) { 
			   return unescape(r[2]); 
			} 
			return null; 
		},
		
    	/**
         * @desc 获取通过http://localhost:8080/JAZZ?params=123&params2=987"传入当前页面的参数
		 * @example var paramsValue = jazz.util.getParameters();
		 * @return Object {params: 123, params2: 987, ……}
		 * @public
         */ 		
		getParameters: function(){
			var url = window.location.search;
			var params = {};
			if(url.indexOf("?")!=-1){
				var str = url.substr(1);
				var strs = str.split("&");
				for(var i = 0, len=strs.length; i<len; i++){
					params[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
				}
			}
			return params;
		},
		
    	/**
         * @desc 获取八位随机数字
		 * @example jazz.util.getRandom();
		 * @return String
		 * @public
         */ 		
		getRandom: function() {
			return Math.random().toString().substr(2, 8);
		},	
		
		/**
		 * @desc 获得祖先级窗体对象
		 * @example jazz.util.getTop(); 
		 * @return Object
		 * @public
		 */
		getTop: function(){
		    var dom = window;
		    if(dom.top.jazz) {
		        dom = dom.top.window;
		    } else {
		        while(dom.parent) {
		            if(dom.parent == dom) {
		                break;
		            }
		            dom = dom.parent;
		        }
		    }
		    if(dom.jazz) {
		        return dom;
		    }
		    return dom;				
		},
		
		/**
		 * @desc 每次调用自加1
		 * @return Number
		 * @example jazz.util.getIndex();
		 */			
		getIndex: function(){
			return jazz.config.zindex++;
		},
		
		/**
		 * @desc 在指定的数组中，是否存在要查找的记录
		 * @param {arr} 指定查找的数组
		 * @param {item} 要查找的记录
		 * @return Boolean
		 * @example jazz.util.inArray([1,2,4,5,9,6], 5);
		 */	
	    inArray: function(arr, item) {
	        for(var i = 0, len = arr.length; i < len; i++) {
	            if(arr[i] === item) {
	                return true;
	            }
	        }
	        return false;
	    },        
        
		/**
		 * @desc 调用提示信息框
		 * @param {message} 提示的信息内容
		 * @param {sure} function 点击确定按钮的回调函数
		 * @example jazz.util.info("提示信息内容！", function(){  });
		 */
		info: function(message, sure){
			var that = this;
			if(jazz.config.isUseRequireJS === true){
				require(['jazz.Message'], function(){					
					$('<div>').appendTo(that.getBodyObject()).message({title: '提示信息', showtype: 0, detail: message, sure: sure});
				});
			}else{				
				$('<div>').appendTo(this.getBodyObject()).message({title: '提示信息', showtype: 0, detail: message, sure: sure});
			}
		},		
		
    	/**
         * @desc 判断是否为数组
		 * @param {v} 参数
		 * @return Boolean
		 * @example jazz.util.isArray([1,2,3,4,5,6]);
		 * @public
         */ 
		isArray: function(v) {
			return Object.prototype.toString.apply(v) === '[object Array]';
		},		
		
    	/**
         * @desc 判断是否为日期类型
		 * @param {v} 参数
		 * @return Boolean
		 * @example jazz.util.isDate("2010-11-10");
		 * @public
         */ 
        isDate: function(v){
            return Object.prototype.toString.apply(v) === '[object Date]';
        },			
		
		/**
		 * @desc 判断是ie浏览器的哪个版本
		 * @param {version} 数字
		 * @return Boolean
		 * @example jazz.util.isIE(7);
		 */		
	    isIE: function(version) {
	        return ($.browser.msie && parseInt($.browser.version, 10) === version) ? true : false;
	    },		
		
		/**
		 * @desc 判断是否为数字类型 数字返回true 否则返回false
		 * @param {n} 参数
		 * @return Boolean
		 * @example jazz.util.isNumber(2314);
		 */		
		isNumber: function(n){
			return $.isNumeric(n);
		},
		
		/**
		 * @desc 是否为标准值，用于判断设置的组件值是否符合规范  “20” “40%” 
		 * @param {n} 参数
		 * @return Boolean
		 * @example jazz.util.isNormalSize("23%");
		 */		
		isNormalSize: function(n){
			if(/^(\d+(\.\d+)?|\d+(\.\d+)?%)$/.test(n)){
				return true;
			}else{
				return false;
			}
		},
		
		/**
		 * @desc 把json对象转换成字符串
		 * @param {o} 参数
		 * @return String
		 * @example jazz.util.jsonToString(JSON);
		 */	
		jsonToString: function(o){
			if(typeof o == "undefined" || o === null){
				return "null";
			}else if(jazz.isArray(o)){//数组
	            var a = ["["], b, i, l = o.length, v;
				for (i = 0; i < l; i++) {
					v = o[i];
					switch (typeof v) {
						case "undefined":
						case "function":
						case "unknown":
							break;
						default:
							if (b) {
								a.push(',');
							}
							a.push(v === null ? "null" : jazz.jsonToString(v));
							b = true;
					}
				}
				a.push("]");
				return a.join("");
			}else if(jazz.isDate(o)){//日期对象
				return '"' + o.getFullYear() + "-" +
					jazz.pad(o.getMonth() + 1) + "-" +
					jazz.pad(o.getDate()) + " " +
					jazz.pad(o.getHours()) + ":" +
					jazz.pad(o.getMinutes()) + ":" +
					jazz.pad(o.getSeconds()) + '"';
			}else if(typeof o == "string"){//字符串,转义回车换行,双引号,反斜杠...等
				var m = {
						"\b": '\\b',
						"\t": '\\t',
						"\n": '\\n',
						"\f": '\\f',
						"\r": '\\r',
						'"' : '\\"',
						"\\": '\\\\'
				};
	            if (/["\\\x00-\x1f]/.test(o)) {
	                return '"' + o.replace(/([\x00-\x1f\\"])/g, function(a, b) {
	                    var c = m[b];
	                    if(c){
	                        return c;
	                    }
	                    c = b.charCodeAt();
	                    return "\\u00" +  Math.floor(c / 16).toString(16) + (c % 16).toString(16);
	                }) + '"';
	            }
	            return '"' + o + '"';
			}else if(typeof o == "number"){
				return isFinite(o) ? String(o) : "null";
			}else if(typeof o == "boolean"){
				return String(o);
			}else  {//json格式的对象
				var a = ["{"], b, i, v;
				for (i in o) {
					v = o[i];
					switch (typeof v) {
					case "undefined":
					case "function":
					case "unknown":
						break;
					default:
						if(b){
							a.push(',');
						}
						a.push(jazz.jsonToString(i), ":", v === null ? "null" : jazz.jsonToString(v));
						b = true;
					}
				}
				a.push("}");
				
				//a.join("").replace(/\s+/g," ");  /&nbsp;/ig
				
				return a.join("").replace(/&nbsp;/ig," ");
			}    
		},	
		
		/**
		 * @desc 根据传入的数字判断，如果字段为小于10的数字，则前补0  例如： 传入 5  得到  05
		 * @param {n} 判断的数字
	     * @return String
	     * @public
	     * @example jazz.util.pad(5);
	     */		
		pad : function(n) {
			return n < 10 ? "0" + n : n;
		},		
		
		/**
		 * @desc 根据datatype和dataformat转换数据格式，并返回转换后数据
		 * @param {formatParams} 
	     * {'cellvalue':'2014-09-12 00:00:00','datatype':'date','dataformat':'yyyy-MM-dd'}
	     * @return String
	     * @public
	     * @example jazz.util.parseDataByDataFormat(formatParams);
	     */
		parseDataByDataFormat: function(formatParams){
			
			if(!formatParams){
				return "";
			}
			var cellvalue = formatParams["cellvalue"];
			var datatype = formatParams["datatype"];
			var dataformat = formatParams["dataformat"];
			if(cellvalue=="" || cellvalue==undefined || cellvalue==null){
				return "";
			}
			if(!datatype){
				return cellvalue;
			}
			//分别不同datatype，解析cellvalue
			var showvalue="";
			if(datatype=="date" || datatype=="datefield"){
				cellvalue = cellvalue.replace(/\.0*$/i, '');
				dataformat = dataformat || "YYYY-MM-DD HH:mm:ss";
				showvalue = jazz.dataFormater(cellvalue,dataformat);
			}else if(datatype=="number" || datatype=="numberfield"){
				dataformat = dataformat || "";
				showvalue = jazz.dataFormater(cellvalue,dataformat);
			}else {
				showvalue = cellvalue;
			}
			return showvalue;
		},

		/**
		 * @desc 刷新全部组件大小
	     * @public
	     * @example jazz.util.refreshSize();
	     */		
		refreshSize: function(){
		    $.each($("BODY").getChildrenComponent(), function(i, obj){
				var element = $(this);
				var vtype = element.attr("vtype"), el = element.data(vtype) || "";
				if(el){
					el.resizeWH(true);
				}
			});						
		},
		
		/**
		 * @desc 把字符串转换成json对象
		 * @param {string} 
	     * @return Object
	     * @public
	     * @example jazz.util.stringToJson("{a: 1, b: 2}");
	     */		
		stringToJson: function(string){
			if(string){
			    if(string instanceof Object){
			    	return {};
			    }else{
			    	return eval("(" + string + ')');
			    }
			}else{
				return {};
			}
		},
		
		/**
		 * @desc 将传入的值“true”或true转换成true， 否则返回false
		 * @param {b} 判断指定值是否为boolean类型
		 */		
		transformBoolean: function(b){
		   return (b == "true" || b == true) ? true : false;
		},
		
		/**
		 * @desc 调用警告信息框
		 * @param {message} 提示的信息内容
		 * @param {sure} function 点击确定按钮的回调函数
		 * @example jazz.util.warn("提示信息内容！", function(){  });
		 */
		warn:function(message, sure){
			var that = this;
			if(jazz.config.isUseRequireJS === true){
				require(['jazz.Message'], function(){					
					if(jazz.config.errorMessageNumber == 0){
						 $('<div>').appendTo(that.getBodyObject()).message({title: '警告信息', showtype: 2, detail: message, sure: sure});
					 }
				});
			}else{				
				if(jazz.config.errorMessageNumber == 0){
					$('<div>').appendTo(this.getBodyObject()).message({title: '警告信息', showtype: 2, detail: message, sure: sure});
				}
			}
		},		
		
		/**
         * @desc 获取当前窗口的高度		 
         * @return Number
		 * @example jazz.util.windowHeight();
         */	
        windowHeight: function(){
			if($.browser.msie){
				return document.compatMode == "CSS1Compat"? document.documentElement["clientHeight"] : (document.body && document.body.clientHeight); 
			}else{ 
				return self.innerHeight;
			}			
		},

		/**
         * @desc 获取当前窗口的宽度		 
         * @return Number
		 * @example jazz.util.windowWidth();
         */		
        windowWidth: function(){ 
			if($.browser.msie){ 
				return document.compatMode == "CSS1Compat"? document.documentElement.clientWidth : (document.body && document.body.clientWidth); 
			}else{ 
				return self.innerWidth; 
			} 
		}	
   };
   
	/**
	 * @version 1.0
	 * @name jazz.window
	 * @description 工具类。
	 * @constructor
	 * @example jazz.window.xxx();
	 */	
   jazz.window = {
		/** @lends jazz.window */  
		/**
	     * @desc 关闭jazz组件的的弹出窗口
		 * @example jazz.window.closeWindow();
	     */
		close: function(e){
			var iframeNumber = 0;
			var target = e.target, $target = $(target);
			var tParent = $target.parent();
			
			function _findWindow(tParent) {
				var t = tParent;
				if(t.is("body")){ //如果为真，说明需要到父页面查找
				   var frameElement	= window.frameElement; //获取iframe对象
				   if(frameElement){
					   var _divObj = $(frameElement).parent();
					   var $div = $(_divObj);
					   iframeNumber++;
					   _findWindow($div);
				   }else{
					   jazz.log("jazz.window.close frame error.");
					   return;
				   }
				}else{
					if(t.hasClass("jazz-window")){
						var dom = window;
						try{
							if(dom.top.$(t[0]).data("window")){  //顶层页面是否存在
								dom.top.$(t[0]).data("window").close();
								return;
							}else{  //顶层页面不存在，使用向上递归的方法，逐级向上判断
								while(dom){
									if(dom.$(t[0]).data("window")){
										 dom.$(t[0]).data("window").close();
										 return;
									}else{
										dom = dom.parent;
									}
								}
							}
						}catch(ex){
							jazz.log("jazz.window.close top error.");
							return;
						}
					}else{
						if(t.parent()){
							_findWindow(t.parent());
						}
					}
				}
			}
			
			_findWindow(tParent);
		}
   };
   

   $.extend(jazz, jazz.util);
	    
   
})(jQuery);


(function($){

	// Private array of chars to use
  	var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');

  	Math.uuid = function (len, radix) {
	    var chars = CHARS, uuid = [], i;
	    radix = radix || chars.length;
	
	    if (len) {
	      // Compact form
	      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
	    } else {
	      // rfc4122, version 4 form
	      var r;
	
	      // rfc4122 requires these characters
	      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
	      uuid[14] = '4';
	
	      // Fill in random data.  At i==19 set the high bits of clock sequence as
	      // per rfc4122, sec. 4.1.5
	      for (i = 0; i < 36; i++) {
	        if (!uuid[i]) {
	          r = 0 | Math.random()*16;
	          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
	        }
	      }
	    }
	
	    return uuid.join('');
	};

  // A more performant, but slightly bulkier, RFC4122v4 solution.  We boost performance
  // by minimizing calls to random()
  Math.uuidFast = function() {
    var chars = CHARS, uuid = new Array(36), rnd=0, r;
    for (var i = 0; i < 36; i++) {
      if (i==8 || i==13 ||  i==18 || i==23) {
        uuid[i] = '-';
      } else if (i==14) {
        uuid[i] = '4';
      } else {
        if (rnd <= 0x02) rnd = 0x2000000 + (Math.random()*0x1000000)|0;
        r = rnd & 0xf;
        rnd = rnd >> 4;
        uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
      }
    }
    return uuid.join('');
  };

  // A more compact, but less performant, RFC4122v4 solution:
  Math.uuidCompact = function() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
      var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
      return v.toString(16);
    });
  };
})(jQuery);
/**
 * @desc 监听容器变化
 */
jazz.namespace("listener");

(function($){
	jazz.listener = {
	    //fit布局监听
		fitlistener: {
			pood: [],//缓存页面中fitlayout布局元素
			config: [],//缓存对应pood中fit布局元素的配置属性
			add: function(obj, config){
				this.pood.push(obj);
				this.config.push(config);
			},
			start: function(){
				var $this = this;
				var fitobject = null, parentobject = null, bodyobject = null, 
				    cacheFitHeight = [], parentHeight=0, siblingheight = 0, overflow_x = null, overflow_y = null;			
				if(this.cleartimeout){
					this.stop();
				}
				this.cleartimeout = setInterval(function(){
					for(var i=0, len=$this.pood.length; i<len; i++){
						fitobject = $this.pood[i];
						parentobject = fitobject.parent();
						if(parentobject[0]){
							if(parentobject[0].nodeName == "BODY"){
								bodyobject = $(document.body);
								//body通过outerHeight(winHeight)设置高度值，不会去除掉margin等高度，所以只能通过下边形式去除
								parentHeight = jazz.util.windowHeight() - (bodyobject.outerHeight(true) - bodyobject.height());
							}else{
								parentHeight = parentobject.height();
							}
							
							$.each(fitobject.siblings(), function(){
								//排除兄弟元素中display==none/attr("islayout") == "no"/
								//position == "fixed"/position == "absolute"这四种情况的，然后合计剩余兄弟元素总高度
								if($(this).css("display") !="none" && $(this).attr("islayout") != "no" && 
								   $(this).css("position") != "fixed" && $(this).css("position") != "absolute"){
			        				
			        				siblingheight += $(this).outerHeight(true);
			        			}
							});
							//计算fit布局元素高度，比较是否发生变化进行逻辑处理
							fitHeight = parentHeight - siblingheight;
							if(cacheFitHeight[i] != fitHeight){
								overflow_x = parentobject.css("overflow-x");  
								overflow_y = parentobject.css("overflow-y");
								
								parentobject.css("overflow-x", "hidden"); 
					            parentobject.css("overflow-y", "hidden");							
								
								fitobject.outerHeight(fitHeight, true);					
								cacheFitHeight[i] = fitHeight;
								//判断是否存在回调函数
								if($this.config[i] && $this.config[i].callback && ($.isFunction($this.config[i].callback))){
									$this.config[i].callback.call($this);
								}
								
								parentobject.css("overflow-x", overflow_x);
								parentobject.css("overflow-y", overflow_y);
							}
							
							siblingheight = 0;
						}
					}	
				}, 300);
			},
			stop: function(){
				if(this.cleartimeout){
					clearInterval(this.cleartimeout);
				}
			}
		}	
	};
	
})(jQuery);

(function($){
	if(!jazz.isIE(6)){
		var _winWidth = jazz.windowWidth(), _winHeight = jazz.windowHeight();
		
		var timerID = "timerLayout_" + jazz.getRandom();
		$(window).off("resize.jazzwindow").on("resize.jazzwindow", function(){
			if (window[timerID]){ clearTimeout(window[timerID]); }
				window[timerID] = null;
//			if ($.browser.msie){
				window[timerID] = setTimeout(function(){ resizeAll();}, 100);
//			}else{
//				resizeAll();
//			}
		});
		function resizeAll(){
		    var _width = jazz.windowWidth(), _height = jazz.windowHeight();
		    var body = $("body");
		    var overflow_x = body.css("overflow-x"); 
		    //overflow_y = body.css("overflow-y");
		    body.css("overflow-x", "hidden"); 
		    //body.css("overflow-y", "hidden"); 
		    $.each($("body").getChildrenComponent(), function(i, obj){
				var element = $(this);
				var vtype = element.attr("vtype"), el = element.data(vtype) || "";
				if(el){
					if(_winWidth != _width){
						el._resizeWidth(false);
					}
					if(_winHeight != _height){
						el._resizeHeight(false);
					}
				}
			});	    
			body.css("overflow-x", overflow_x); 
			//body.css("overflow-x", overflow_y); 

			_winWidth = _width;  _winHeight = _height;
		}	
	}
})(jQuery);

(function($){
	  $(document).off('mousedown.jazz').on('mousedown.jazz', function (e) {
		    var target = e.target, $target = $(target);
	  		var droppanels = $("div.jazz-dropdown-panel.jazz-widget-content.jazz-helper-hidden");
	  		$.each(droppanels,function(){
	  			  if($(this).is(":hidden")) {
	                  return;
	              }
	  			  var dropvtype = $(this).attr("type");
	  			  
	  			  if(dropvtype == 'datefield' || dropvtype == 'colorfield' || dropvtype == 'autocompletecomboxfield' || dropvtype == 'comboxfield' || dropvtype == 'comboxtreefield'){
	  				  var dropname = $(this).attr("name").substring(14);
		  			  var dropinput = $("div[name="+dropname+"]").data(dropvtype).inputtext;
		  			  var droptrigger = $("div[name="+dropname+"]").data(dropvtype).trigger;
		  			  if(target==dropinput.get(0) || target==droptrigger.get(0)){
		  				  return;
		  			  }
		  			  var dropprefix = $("div[name="+dropname+"]").data(dropvtype).prefix;
		  			  if(dropprefix){
		  				  if(target==dropprefix.get(0)){
			  				  return;
			  			  }
		  			  }
		  			  var dropsuffix = $("div[name="+dropname+"]").data(dropvtype).suffix;
		  			  if(dropsuffix){
		  				  if(target==dropsuffix.get(0)){
			  				  return;
			  			  }
		  			  }
	  			  }
	  			  
	              var offset = $(this).offset();
	              if (e.pageX < offset.left ||
	                  e.pageX > offset.left + $(this).width() ||
	                  e.pageY < offset.top ||
	                  e.pageY > offset.top + $(this).height()) {
	              	  $(this).hide();
		  			  if(dropvtype == 'datefield' || dropvtype == 'colorfield' || dropvtype == 'autocompletecomboxfield' || dropvtype == 'comboxfield' || dropvtype == 'comboxtreefield'){
		  				  var data = $("div[name="+dropname+"]").data(dropvtype)._changeData();  
		              	  $("div[name="+dropname+"]").data(dropvtype)._event("change", e, data);
//		              	  var oldvalue = $("div[name="+dropname+"]").data(dropvtype).getValue();
//		              	  $("div[name="+dropname+"]").data(dropvtype).oldchoices = oldvalue + "";
//		              	  if(dropvtype == 'colorfield'){
//		              		$("div[name="+dropname+"]").data(dropvtype).setValue(oldvalue);
//		              	  }
		  			  }
	              }
	           //针对日历插件,如果开启显示年份\月份下拉框,则隐藏输入框和下拉框
	           if($('.jazz-date-list-year').children().length > 0){
	        	   $('.jazz-date-list-year').hide();
	           }
	           if($('.jazz-date-list-month').children().length > 0){
	        	   $('.jazz-date-list-month').hide();
	           }
	  		});
    });	
})(jQuery);var G = window.G || {};
/**
 * { "cacheName":url, "cacheParam":"", "status":"success" "data":[ {"key":key,"value":value} ] }
 *
 *
 * status ---- 发送中ready  成功success  错误error
 *
 */
G.pageCache = [];

/**
 * @desc 是否将代码集请求合并
 * 一次性请求所有代码集
 * true 合并组件的代码集请求，统一发送请求
 * false 组件自己发送代码集数据请求
 * @type {boolean}
 */
G.isGetAll = jazz.config.isGroupRequest || false;

/**
 * @desc 统一请求代码集地址
 * 系统现在是做url前匹配
 * @type {string}
 */
G.groupUrl = {};

/**
 * @desc 处理代码集请求合并
 */
G.processData = function(links, type){
	
	if(G.isGetAll == false){
		return;
	}
	
    var $this = this,
        use = G.groupUrl;   //可以被合并的请求地址
    /**
     * 1. 分离请求地址前缀，去掉参数部分，保留等号
     * 	比如 http://localhsot/getData.do?id=
     * 2. 根据 1 中得到的前缀，匹配所有地址，找到具体的请求参数
     * 3. 根据 1 中得到的前缀，合并请求参数
     *  比如 {'http://localhost/getData.do?id=': '001,002,004'}
     */
    var counter = 0, tmpUse = [];
    for(var i= 0, len=links.length; i<len; i++){
    	var key = links[i].replace(/([\s|\S]+=)((\w+,)*\w+)$/g, "$1"),
    	    param = links[i].replace(/([\s|\S]+=)((\w+,)*\w+)$/g, "$2");
//    	if(/([\s|\S]+=)+((\w+,)*\w+)$/.test(links[i])){    		
   		if(/(\/JAZZ\/dictionary\/queryData\.do\?dicId=)((\w+,)*\w+)$/.test(links[i])){    		
    		if(use.hasOwnProperty(key)){
    			if(use[key].indexOf("@@") == -1){
    				tmpUse.push(links[i]);
    			}else{
    				tmpUse.push(key + param);
    			}
    			use[key] += ("@@" + param);
    		}else{
    			use[key] = param;
				tmpUse.push(key + param);
    		}
			counter++;
    	}/*else{
    		//发送无法合并的请求
    	}*/
    }

    if(counter > 1){
    	for(var i=0, len=tmpUse.length; i<len; i++){    		
    		$this._beforeRequestByGroup(tmpUse[i]);
    	}
    }
    
    //开始遍历刚才的对象，找到需要处理的请求
    for(var key in use){
    	use[key] = use[key].replace(/@@/g, ",");
    	$this.getPageDataSetCache(key + use[key]);
    }
};

/**
 * @desc  用于记录页面所有url数据
 * @param url
 * @param option
 */
G._setPageCacheByURL = function(cacheName, cacheParam){
    cacheParam = cacheParam || "";
    var i = 0, $this =  this;
    while(i < G.pageCache.length){
        if(cacheName == G.pageCache[i].cacheName
            && cacheParam == G.pageCache[i].cacheParam){
            return;
        }//if
        i++;
    }//while

    G.pageCache.push({
        "cacheName":cacheName,
        "cacheParam":cacheParam,
        "data":[],
        "status":"ready"
    });
    
    $.ajax({
        type: "POST",
        url:cacheName,
        dataType:"text",
        success:function(data){
            var i = 0, tmpdata;
            while(i < G.pageCache.length){
                if(cacheName == G.pageCache[i].cacheName){
                    tmpdata = eval("(" + data + ")");
                    if(jazz.config.platForm == 'sword' && !!tmpdata.data[0]){
                        tmpdata["data"] = tmpdata.data[0].data;
                    }
                    G.pageCache[i].data = tmpdata;
                    G.pageCache[i].status = "success";
                    break;
                }//if
                i++;
            }//while
        },
        error:function(data){
            var i = 0;
            while(i < G.pageCache.length){
                if(cacheName == G.pageCache[i].cacheName){
                    G.pageCache[i].status = "error";
                    break;
                }//if
                i++;
            }//while
        },
        complete:function(data){
        }
    });
};

/**
 * @desc 在发送分组全部请求数据之前
 *  <br>将各项的请求状态置为ready
 *  <br>避免重复请求
 * @private
 */
G._beforeRequestByGroup = function(cacheName, cacheParam){
	if(!G.isGetAll){
		return;
	}
    var i = 0, $this =  this;
    cacheParam = cacheParam || '';
    while(i < G.pageDataSetCache.length){
        if(cacheName == G.pageDataSetCache[i].cacheName
            && cacheParam == G.pageDataSetCache[i].cacheParam){
            return;
        }//if
        i++;
    }//while

    G.pageDataSetCache.push({
        "cacheName":cacheName,
        "cacheParam":cacheParam,
        "data":[],
        "status":"ready"
    });
};

/**
 * @desc  通过指定代码集的索引
 * @param cacheParam
 * @returns {Number}
 */
G._getCacheNameIndex = function(cacheParam){
    var $this = this;
    for(var i=0, len=$this.pageDataSetCache.length; i<len; i++){
        if($this.pageDataSetCache[i]['cacheName'].indexOf(cacheParam) > -1){
            return i;
        }
    }
};

/**
 * @desc  根据code获取代码集
 * @param cacheName
 * @param code
 * @param cacheParam
 * @returns
 */
G.getPageCacheByCode = function(cacheName, code, cacheParam){
    cacheParam = cacheParam || "";
    var i = 0;
    while(i < G.pageCache.length){
        if(cacheName == G.pageCache[i].cacheName
            && cacheParam == G.pageCache[i].cacheParam
            && G.pageCache[i].data.data != undefined){
            var j = 0;
            while(j < G.pageCache[i].data.data.length){
                // tree
                if(G.pageCache[i].data.data[j].id){
                    if(code == G.pageCache[i].data.data[j].id){

                        var d = {
                            "status": G.pageCache[i].status,
                            "data":  G.pageCache[i].data.data[j].name
                        };

                        return d;
                    }//if
                }//if
                // jazz-combox
                if(G.pageCache[i].data.data[j].value){
                    if(code == G.pageCache[i].data.data[j].value){

                        var d = {
                            "status": G.pageCache[i].status,
                            "data":  G.pageCache[i].data.data[j].label
                        };

                        return d;
                    }//if
                }//if
                j++;
            }//while
        }//if
        i++;
    }//while

    G._setPageCacheByURL(cacheName, cacheParam);

    var d = {
        "status": "ready",
        "data": undefined
    };
    return d;
},

/**
 * @desc  
 * @param cacheName
 * @param cacheParam
 * @returns
 */
G.getPageCache = function(cacheName, cacheParam){
    cacheParam = cacheParam || "";
    var i = 0;
    while(i < G.pageCache.length){
        if(cacheName == G.pageCache[i].cacheName
            && cacheParam == G.pageCache[i].cacheParam){
            var d = {
                "status": G.pageCache[i].status,
                "data": G.pageCache[i].data
            };
            return d;
        }//if
        i++;
    }//while

    //没有所需要的数据就在此发送请求

    G._setPageCacheByURL(cacheName, cacheParam);

    var d = {
        "status": "ready",
        "data": []
    };
    return d;

};

/**
 * @desc  通过父id获取代码集，针对树操作
 * @param cacheName
 * @param key
 * @returns
 */
G.getPageCacheByParentId = function(cacheName, key){
    var dataArray = [];
    var i = 0;
    while(i < G.pageCache.length){
        if(cacheName == G.pageCache[i].cacheName){
            if(key != undefined){
                var j = 0;
                while(j < G.pageCache[i].data.data.length){
                    if(key == G.pageCache[i].data.data[j].parentFloatId){
                        dataArray.push(G.pageCache[i].data.data[j]);
                    }
                    j++;
                }
                return dataArray;
            }else{
                return G.pageCache[i].data.data;
            }
            break;
        }
        i++;
    }
    return null;
};

/**
 * @description 页面代码集缓存
 * @type json
 * 格式：
 * {"sex":[{"text":"男","value":"1"}],"org":[{"text":"集成事业部","value":"3"}]}
 */
G.pageDataSetCache = [];
/**
 * @private
 * @description ajax请求获取并设置页面代码集
 * @param cacheName 代码集url或者代码集名称
 */
G._setPageDataSetCacheByURL = function(cacheName,cacheParams, flag){
    var i = 0, $this = this;
    while(i < G.pageDataSetCache.length){
        if(cacheName == G.pageDataSetCache[i].cacheName){
            if(!flag){
                return;
            }
        }
        i++;
    }

    G.pageDataSetCache.push({
        "cacheName":cacheName,
        "cacheParams":cacheParams,
        "data":{},
        "status":"ready"
    });
    
    this.r_jazzData = new Object();
    this.r_dataArray = new Array();
    this.r_jazzData["data"] = this.r_dataArray;
    
    var postData = null;
    
    if(jazz.config.platForm=='sword'){
        this.r_jazzData = new Object();
	    this.r_dataArray = new Array();
	    this.r_jazzData["data"] = this.r_dataArray;
	    
	    if(jazz.isIE(7) || jazz.isIE(6)){
	    	if(typeof(cacheParams) != 'object'){
	    		cacheParams = jazz.stringToJson(cacheParams);
	    	}
	    }
	    
	    if(!!cacheParams){
	        $.each(cacheParams,function(param,i){
	            var _newDataObj = {};
	            _newDataObj["sword"] = "attr";
	            _newDataObj["name"] = param;
	            _newDataObj["value"] = cacheParams[param]+"";
	            $this.r_dataArray.push(_newDataObj);
	        });
	    }else{
            var index = decodeURI(cacheName).indexOf("?");
	    	var params = decodeURI(cacheName).substring(index+1).split("&");
	    	$.each(params,function(i,param){
	    		var data = param.split("=");
	            var _newDataObj = {};
	            _newDataObj["sword"] = "attr";
	            _newDataObj["name"] = data[0];
	            _newDataObj["value"] = data[1]+"";
	            $this.r_dataArray.push(_newDataObj);
	        });
	    }
	    
	    this.r_jazzData["tid"] = decodeURI(cacheName);
    }else{
    	if(!!cacheParams){
	        $.each(cacheParams,function(param,i){
	            var _newDataObj = {};
	            _newDataObj["vtype"] = "attr";
	            _newDataObj["name"] = param;
	            _newDataObj["data"] = cacheParams[param];
	            $this.r_dataArray.push(_newDataObj);
	        });
	    }else{
	    	var index = decodeURI(cacheName).indexOf("?");
	    	var params = decodeURI(cacheName).substring(index+1).split("&");
	    	$.each(params,function(i,param){
	    		var data = param.split("=");
	            var _newDataObj = {};
	            _newDataObj["vtype"] = "attr";
	            _newDataObj["name"] = data[0];
	            _newDataObj["data"] = data[1];
	            if(!!data[1]){
	            	$this.r_dataArray.push(_newDataObj);
	            }
	        });
	    }
    }
    
    postData = {};
    postData["postData"] = jazz.jsonToString(this.r_jazzData);

    /**
     * 开启全部一次请求
     * 则当遇到单独请求的时候则返回
     */
    if($this.isGetAll){
    	var params = cacheName.split('=');
    	if(params.length == 2){
    		for(var key in G.groupUrl){
    			if(G.groupUrl[key] != params[1] 
    				&& G.groupUrl[key].indexOf(params[1]) > -1){
    				var index = $this._getCacheNameIndex(cacheName);
    	    		if($this.pageDataSetCache[index]['status'] != 'success'){
    	    			return;
    	    		}
    			}
    		}    		
    	}
    }
   
    $.ajax({
        type: "POST",
        url:cacheName,
        dataType:"text",
        data:postData,
        success:function(data){
            var datajson = null;
            if(jazz.config.platForm=='sword'){
                var newdata = data.replace(/label/g, "text");
                
                var newdatajson = jazz.stringToJson(newdata);
                datajson = {"data":{}};
                if(!!newdatajson.data[0]){
                    datajson["data"] = newdatajson.data[0].data;
                }
                /*if(!!newdatajson.data){
                    datajson["data"] = newdatajson.data;
                }*/
                datajson = jazz.jsonToString(datajson);
            }else{
                datajson = data;
            }
            var i = 0;
            while(i < G.pageDataSetCache.length){
            	//先找到当前返回的是哪一个请求的数据
            	if(cacheName == G.pageDataSetCache[i].cacheName){
            		//如果是合并请求的结果
            		//则分别分发返回结果到原始的数据请求
            		var requestParams = G._isInGroupUrl(cacheName);
            		if(requestParams){
                        //说明有分组请求，
                        //需要分发到具体的项目
                        var group = requestParams['params'].split(','),
                            groupData = jazz.stringToJson(datajson);
                        for(var j=0; j<group.length; j++){
                            var pi = $this._getCacheNameIndex(requestParams['url']+group[j]);
                            G.pageDataSetCache[pi].data = groupData['data'][j];
                            G.pageDataSetCache[pi].status = "success";
                        }
                    }
            		//设置当前请求的结果状态
            		datajson = jazz.stringToJson(datajson);
            		G.pageDataSetCache[i].data = datajson["data"];
                	G.pageDataSetCache[i].status = "success";    
                    break;
                }
                i++;
            }
        },
        error:function(data){
            var i = 0;
            while(i < G.pageDataSetCache.length){
                if(cacheName == G.pageDataSetCache[i].cacheName){
                    G.pageDataSetCache[i].status = "error";
                    break;
                }
                i++;
            }
        },
        complete:function(data){
        }
    });

};

/**
 * @desc  判断当前请求是否是合并请求中的一个
 *  ture  则返回对应的请求地址和解析后的参数
 *  false 返回
 * @param cacheName
 * @returns
 */
G._isInGroupUrl = function(cacheName){
	if(!G.isGetAll){
		return false;
	}
	for(var key in G.groupUrl){
		if(cacheName.indexOf(key + G.groupUrl[key]) > -1){
			return {
				url: key,
				params: G.groupUrl[key]
			};
		}
	}
	return false;
};

/**
 * @description 获取页面代码集缓存
 * @param {} cacheName
 * @param {} cacheParams
 * @param boolean flag 是否发起请求数据
 * @return {}
 */
G.getPageDataSetCache = function(cacheName,cacheParams, flag){
    var i = 0;
    while(i < G.pageDataSetCache.length){
        if(cacheName == G.pageDataSetCache[i].cacheName){
            if((flag == true && (G.pageDataSetCache[i]['status'] != 'ready'
                && G.pageDataSetCache[i]['status'] == 'success'))
                || (flag == true && G.pageDataSetCache[i]['status'] == 'success')
                || (G._isInGroupUrl(cacheName)) ){
                if(!!cacheName){
                    G._setPageDataSetCacheByURL(cacheName,cacheParams, flag);
                }
            }
            var d = {
                "cacheName":cacheName,
                "cacheParams":cacheParams,
                "status": G.pageDataSetCache[i].status,
                "data": G.pageDataSetCache[i].data
            };
            return d;
        }
        i++;
    }

    //没有所需要的数据就在此发送请求
    if(!!cacheName){
        G._setPageDataSetCacheByURL(cacheName,cacheParams);
    }

    var d = {
        "cacheName":cacheName,
        "cacheParams":cacheParams,
        "status": "ready",
        "data": {}
    };
    return d;

};/**
 * @version 1.0
 * @description 公共UI组件对象的函数接口
 */

jazz.namespace("widget");

jazz.widget = function(options, parent){
	if(typeof(options) == "object"){
		var vtype = options["vtype"] || "";
		if(!vtype){
			alert(" vtype 未定义！"); return false;
		}else{
			var obj = null;
		    if(!!parent){
		    	obj = $("<div>").appendTo(parent);
			}else{
				obj = $("<div>").appendTo("body");
			}
		    obj[vtype](options);
		    obj[vtype]("finish");
			return obj;
		}
	}
};