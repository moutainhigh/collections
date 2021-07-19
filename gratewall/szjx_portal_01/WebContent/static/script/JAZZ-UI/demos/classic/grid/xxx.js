
window.undefined = window.undefined;

jazz = {
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

//var dom = window;
//if(dom.top != self) {
//    dom = dom.top.window;
//} else {
//    while(dom.parent && dom.parent.jazz) {
//        if(dom.parent == dom) {
//            break;
//        }
//        dom = dom.parent;
//    }
//}
//if(dom.jazz) { 
//    jazz = dom.jazz;
//    window = dom;
//}

jazz.namespace("util");

jazz.namespace("config");


(function($){
    
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
    
    /**
     * 内部方法，建立当前组件与上级组件的关系
     */
	var _parse = function( element ){
        // 查找元素的parent节点
        var parent = element.parents("div[vtype]:first");
        // 如果没找到，设置为BODY
        if( parent.size() == 0 ){
            parent = $("BODY");
        }

        // 设置父节点的data中的child
        var parentData = parent.data("vtypetree") || {parent:"", child:{}};
        var childName = element.attr("name");
        parentData.child[childName] = element;
        parent.data("vtypetree", parentData);

        // 设置当前节点中的parent的值和vType的值
        var nodeData = element.data("vtypetree") || {parent:"", child:{}};
        nodeData["parent"] = parent;
        nodeData["vType"] = element.attr("vType");
        element.data("vtypetree", nodeData);

        /**
         * 检查元素的代码集
         */
        if(element.attr("dataurl")){
            return [element.attr("dataurl")];
        }

        /**
         * 处理gridcolumn中的代码集
         */
        var valueSet = [];
        if('gridcolumn' === element.attr("vtype")){
            element.children().children().each(function(){
                if($(this).attr("dataurl"))
                    valueSet.push($(this).attr("dataurl"));
            });
            return valueSet;
        }


    };
    
    /**
     * 初始化vtyle节点
     * （一次只初始化一个，先把之间初始化完成后，再初始化子节点）
     */
    var _initComponent = function( element, options ){
        var nodeData = element.data("vtypetree")||{};
        // 如果当前元素有vType属性，
        if(!!nodeData.vType){
            // 判断UI组件是否已经初始化，如果未初始化，才执行初始化。
            var isInited = $(element).isComponentInited();
            if( !isInited ){
                // 初始化UI组件之前，先判断相关JS文件是否已经引入，如果未引入，动态加载进来。
                // loadResource();暂不实现
                
                // 初始化UI组件。$(element).vType(options);
                var attrtooptions = jazz.attributeToOptions( $(element) );
                if(!!options){
                	attrtooptions = $.extend(attrtooptions, options);
                }
                ($(element)[nodeData.vType])(attrtooptions);

                jazz.log("初始化" + element.attr("name") + "(" + nodeData.vType + ")" + "组件");
            }
        };
        var childNodes = nodeData.child;
        // 如果存在childNodes，初始化子元素
        if(!!childNodes){
            for(var c in childNodes){
                _initComponent( childNodes[c] );
            }
        }   
    };
    
    $.fn.createVtypeTree = function(){
        var $this = $(this);
        
        // 1.解析生成vType树
        // 如果当前元素具有vtype属性，先解析当前的元素
        if($this.attr("vtype")){
            _parse($this);
        }
        // 解析元素的子元素
        $this.find("div[vtype]").each(function(){
            _parse($(this));
        });    	
    },
    
    $.fn.parse = function(options){
    	var $this = $(this);
    	_initComponent( $this, options);    	
    },
    
    /**
     * vtype树操作：解析当前元素及子元素的vType，形成vType树，并初始化相关组件
     */
    $.fn.parseComponent = function(options){
        var startTime = new Date();

        var $this = $(this);
        var links = [], ret;
        // 1.解析生成vType树
        // 如果当前元素具有vtype属性，先解析当前的元素
        if($this.attr("vtype")){
            ret = _parse($this);
            if(ret.length){
                links = links.concat(ret);
            }
        }

        // 解析元素的子元素
        $this.find("div[vtype]").each(function(){
            ret = _parse($(this));
            if(ret && ret.length){
                links = links.concat(ret);
            }
        });

        var endTime1 = new Date();
        jazz.log( "获取vType树耗时：" + (endTime1-startTime)+"ms" );

        // 2.根据vType树初始化相关组件（从vType树的根节点（BODY）开始，深度优先算法）
        _initComponent( $this, options );
        var endTime2 = new Date();
        jazz.log( "初始化组件耗时：" + (endTime2-endTime1)+"ms" );

        // 3. 发送请求代码集
        if(window.G){
            G.processData(links, true);
        }else{
            jazz.log("这是代码集组件还未初始化完成");
        }

    };
    
    /**
     * 初始化vType组件
     */
    $.fn.initComponent = function(){
    	$("BODY").parseComponent();
    };
    
    /**
     * 判断组件是否已经执行初始化
     */
    $.fn.isComponentInited = function(){
        var nodeData = $(this).data("vtypetree")||{};
        if(!!nodeData.vType){
            return !!$(this).data( nodeData.vType );
        }
        nodeData = null;
        return false;
    };
    
    /**
     * vtype树操作：获取当前节点的父节点
     */
    $.fn.getParentComponent = function(){
        var nodeData = $(this).data("vtypetree")||{};
        return nodeData.parent;
    };
    
    /**
     * vtype树操作：获取当前节点的子节点
     */
    $.fn.getChildrenComponent = function(){
        var nodeData = $(this).data("vtypetree")||{};
        return nodeData.child;
    };
    
    /**
     * vtype树操作：获取当前节点的指定vtype类型子节点
     */
    $.fn.getChildrenComponentByVtype = function(vtype){
        var element = null;
        var nodeData = $(this).data("vtypetree")||{};
        for(var childname in nodeData.child){
        	var data = nodeData.child[childname].data("vtypetree");
        	if(data.vType==vtype){
        		element = nodeData.child[childname];
        		break;
        	}
        }
        return element;
    };

    /**
     * 页面初始化完成后，执行initComponent操作
     */
    $(function(){
    	$("BODY").initComponent();
    });
    

})(jQuery);
(function($){
	
	jazz.config = {
			/**上下文路径 **/
			contextpath: '',
			
			/**生成组件元素动态生成ID时，所需要的计数 **/
			compNumber: 1000,

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
			
			/**表单字段中空白文本  **/
			fieldBlankText: "",

			/**表单字段中的默认宽度  **/
			fieldDefaultWidth: 150,
			
			/**表单字段中的默认高度  **/
			fieldDefaultHeight: 26,
			
			/**表单字段中的label字段宽度  **/
			fieldLabelWidth: 80,
			
			/**请求代码集时,是否合并相同请求**/
			isGroupRequest: false,
			
			/**输出日志开关 **/
			logger: true,
			
			/**(sword 平台)  other(其他) **/
			platForm: 'other',
			
			/**表单验证时，提示错误的图片在表单中所在用的宽度 **/
			ruleImgWidth: 20,
			
			/**grid定义滚动条所占用的宽度 **/
			scrollWidth: 17,
			
			/**遮罩层默认z-index值 **/
			zindex: 1000,
			
			
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
		   	    ,months: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
		   	    ,days: ['日','一', '二', '三', '四', '五', '六']
		   	    ,dateOrder:['年','月','日','时','分','秒']
		   	    ,bizSucMsg:"校验成功!"
		   	    ,bizFaiMsg:"校验失败!"
		   	    ,sysMsg:"系统错误!"
		   	    ,menuCaption:["刷新","关闭当前项","关闭其他项","关闭所有项"]
		   	    ,gridHide:"隐藏此列"
		   	    ,gridEsc:"按升序排列"
		   	    ,gridDesc:"按降序排列"
		   	    ,gridCtrl:"控制面板"
		   	    ,gridInsert:"请先选择一行,再执行此操作."
		   	    ,gridDelete:"请至少选择一行"
		   	    ,gridDelConfirmTree:"确认删除?其子节点也将被删除!"
		   	    ,gridDelConfirm:"确认删除?"
		   	    ,gridLoadPage:"表格中有没有被保存的项目!点击确定放弃这些信息,执行此动作;点击取消,取消动作"
		   	    ,gridTarNotExist1:"目标页["
		   	    ,gridTarNotExist2:"]不存在!"
		   	    ,gridInsertRowDel:"此行为新增行，执行此操作会直接物理删除，信息将不能恢复!您要执行此操作吗？"
		   	    ,gridSaveAlert:"该行数据没有保存，不能执行此操作!"
		   	    ,gridFocus:"没有焦点可以转移"
		   	    ,gridGo:"已经是当前页!"
		   	    ,gridDi:"第"
		   	    ,gridGoErr:"请输入一个大于0的整数"
		   	    ,gridYe:"页"
		   	    ,gridYeGong:"页/共"
		   	    ,gridMeiYeShow:"每页"//"每页显示"
		   	    ,gridTiao:"条"
		   	    ,gridJsd:"共"//"检索到"
		   	    ,gridJsdL:"条"//"条记录"
		   	    ,gridMeiYe:"每页"
		   	    ,gridGong:"共"
	
		   	    ,gridFirst:"已经是首页了!"
		   	    ,gridLast:"已经是末页了!"
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
		   	    'cellphone':'手机号码',
		   	    'idcard':'身份证',
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
         * @desc 关闭jazz组件的的弹出窗口
		 * @example jazz.util.closeWindow();
         */
		close: function(e){
			//jazz.window.js 组件内部调用该方法实现组件的关闭。
//			if(window.frameElement){
//				
//			}
//			var $element = window.frameElement.parent();
//			alert($element.attr("name"));
			
			var iframeNumber = 0;
			var target = e.target, $target = $(target);
			var tParent = $target.parent();
			
			function findWindow(tParent) {
				var t = tParent;
				if(t.is("body")){ 						   //如果为真，说明需要到父页面查找
				   var frameElement	= window.frameElement; //获取iframe对象
				   if(frameElement){
					   var _divObj = $(frameElement).parent();
					   var $div = $(_divObj);
					   iframeNumber++;
					   if($div.hasClass("jazz-window")){
						   var aa = $div.data("window");
						   alert("aa====="+aa);
					   }else{
						   findWindow($div);
					   }
				   }
				}else{
					if(t.is(".jazz-window")){
						var _$name = t.attr("name");
						var ac;
						alert(iframeNumber);
						for(var i = 0, len = iframeNumber; len<i; i++){
//							var bbb = top.$(window.parent.document).find("div[name='"+_$name+"']");
//							var ccc = top.$(bbb).data("window");
//							alert(ccc.options.name);
						}
						
//						var bbb = window.parent.$.find("div[name='"+_$name+"']");
//						var ccc = top.$(bbb).data("window");
//						alert("name="+ccc.options.name);
						
						alert("===="+$);
						
						
					}else{
						if(t.parent()){
							findWindow(t.parent());
						}
					}
				}
			}
			
			findWindow(tParent);
			
			
			
//			while(!tParent.hasClass("jazz-window")){
//				tParent = tParent.parent();
//				if(tParent.is("body")){
//					break;
//				}
//				if(tParent.is("window")){
//					break;
//				}
//			}
//			
//			if(tParent.hasClass("jazz-window")){
//				var _vtype = tParent.attr("vtype"), _name = tParent.attr("name"); 
//			    //创建的窗存在
//				if($["div[name='"+_name+"']"]){
//			    	
//			    }else{
//			    //创建的窗体不存在	
//			    	
//			    }	
//			}else{
//				alert("is not window");
//			}
			
		},
		   
    	/**
         * @desc 获取通过http://localhost:8080/JAZZ?params=123传入当前页面的参数
         * @param {name} 参数名
		 * @example var paramsValue = jazz.util.getParameter("params");
		 * @return 123
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
		 * @return {params: 123, params2: 987, ……}
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
		 * @desc 根据datatype和dataformat转换数据格式，并返回转换后数据
		 * @param {formatParams} 
	     * {'cellvalue':'2014-09-12 00:00:00','datatype':'date','dataformat':'yyyy-MM-dd'}
	     * @return showvalue {String}
	     * @public
	     * @example jazz.dataformatutil.parseDataByDataFormat(formatParams);
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
			var showvalue=""
			switch(datatype){
				case "date" :
					cellvalue = cellvalue.replace(/\.0000$/i, '');
					//showvalue = jazz.dateformat.formatDateToString(cellvalue,dataformat);
					showvalue = jazz.dateformat.formatStringToString(cellvalue,"yyyy-MM-dd HH:mm:ss",dataformat);
					break;
				case "number" :
					//showvalue = jazz.dateformat.formatDateToString(cellvalue,dataformat);
					break;
				default: 
					showvalue = cellvalue;
			}
			return showvalue;
		}
   };
   
   $.extend(jazz,  {
	   
		info: function(message,$sure){
			 $('<div></div>').appendTo($('body')).info({detail: message,sure:$sure});
		},
		
		error: function(message,$sure){
			 $('<div></div>').appendTo($('body')).error({detail: message,sure:$sure});
		},		
		
		warn:function(message,$sure){
			 $('<div></div>').appendTo($('body')).warn({detail: message,sure:$sure});
		},	
		
		confirm:function(message,$sure,$cancel){
			 $('<div></div>').appendTo($('body')).confirm({detail: message,sure:$sure,cancel:$cancel});
		},
		getWindowHeight:function(){
			var docElemProp = document.documentElement[ "clientHeight" ];
			return document.compatMode === "CSS1Compat" && docElemProp || document.body[ "clientHeight" ] || docElemProp ;			
		},
		getRandom: function() {
			return Math.random().toString().substr(2, 8);
		},
		isNumber: function(n){
			return $.isNumeric(n);
		},
	    scrollInView: function(container, item) {        
	        var borderTop = parseFloat(container.css('borderTopWidth')) || 0,
	        paddingTop = parseFloat(container.css('paddingTop')) || 0,
	        offset = item.offset().top - container.offset().top - borderTop - paddingTop,
	        scroll = container.scrollTop(),
	        elementHeight = container.height(),
	        itemHeight = item.outerHeight(true);

	        if(offset < 0) {
	            container.scrollTop(scroll + offset);
	        }
	        else if((offset + itemHeight) > elementHeight) {
	            container.scrollTop(scroll + offset - elementHeight + itemHeight);
	        }
	    },
	    
	    isIE: function(version) {
	        return ($.browser.msie && parseInt($.browser.version, 10) === version);
	    },
	    
	    escapeRegExp: function(text) {
	        return text.replace(/([.?*+^$[\]\\(){}|-])/g, "\\$1");
	    },

	    escapeHTML: function(value) {
	        return value.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
	    },
	    
		//判断是否为数组
		isArray: function(v) {
			return Object.prototype.toString.apply(v) === '[object Array]';
		},
		//判断是否为时间类型
        isDate: function(v){
            return Object.prototype.toString.apply(v) === '[object Date]';
        },	
		//格式化数字<10的前面补0
		pad : function(n) {
			return n < 10 ? "0" + n : n;
		},
		//把字符串转化为JSON格式
		stringToJson: function(json){
		    if(json instanceof Object){
		    	return {};
		    }else{
		    	return eval("(" + json + ')');
		    }
		},
		//把JSON对象转化为字符串
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
	                    return "\\u00" +
	                        Math.floor(c / 16).toString(16) +
	                        (c % 16).toString(16);
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
				return a.join("");
			}    
		},	    
	            
	    clearSelection: function() {
	        if(window.getSelection) {
	            if(window.getSelection().empty) {
	                window.getSelection().empty();
	            } else if(window.getSelection().removeAllRanges) {
	                window.getSelection().removeAllRanges();
	            }
	        } else if(document.selection && document.selection.empty) {
	                document.selection.empty();
	        }
	    },
	            
	    inArray: function(arr, item) {
	        for(var i = 0, len = arr.length; i < len; i++) {
	            if(arr[i] === item) {
	                return true;
	            }
	        }

	        return false;
	    },

	    /**
         * @desc 调用tooltip组件
         * @param {el} 容器对象
         * @param {val} 输入值
         * @param {rule} 验证规则
         * @param {regMsg} 自定义函数显示消息
         */			
		doTooltip: function(el, val, rule, regMsg){
			var input = el.input, comp = el.element, parent = el.parent, ruleImg = el.ruleImg;
			obj = jazz_validator.doValidator(val, rule, regMsg);
			if(!obj.state){
				//input.addClass('jazz_validator');
				parent.addClass('jazz_validator_border');
				ruleImg.removeClass('jazz-helper-hidden');
				ruleImg.attr("title",obj.msg);
				el.options.validationstate = false;
			}else{
				//input.removeClass('jazz_validator');
				parent.removeClass('jazz_validator_border');
				ruleImg.addClass('jazz-helper-hidden');
				el.options.validationstate = true;			
			}
			
			return obj.state;
		},
	    
		/**
		 * @desc div属性转options
		 * @param {obj} 
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
	    				if(_v[0]=='[' || _v[0]=='{'){
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
		
		/**
	     * @desc 光标末尾
		 * @param {inputobj} 文本框对象
	     */	    
	    setLastFocu: function(inputobj){
	    	var textNode=inputobj[0];
			var count=textNode.value.length;
			if($.browser.msie){
				var f = textNode.createTextRange();
				f.moveStart('character',count); 
				f.collapse(true); 
				f.select();
			}else{
				textNode.setSelectionRange(count,count);
				textNode.focus(); 
			}
	    },

		/**
	     * @desc 去重
		 * @param {oldtext} 就值
	     * @param {newtext} 新值
		 */	    
	    setNoRepeat: function(oldtext,newtext){
	    	var strArr = oldtext.split(",");
			strArr.sort();
            var result = [];
            var tempStr=newtext;  
            if(strArr.length > 1){            	
            	for(var i in strArr){  
            		if(!!strArr[i] && strArr[i] != tempStr){  
            			result.push(strArr[i]);  
            			tempStr=strArr[i];  
            		}  
            	}  
            }else{
            	result.push(strArr[0]);
            }
            return result.join(",");
	    },

		/**
	     * @desc 删重
		 * @param {oldtext} 就值
	     * @param {newtext} 新值
		 */	    
	    removeRepeat: function(oldtext,newtext){
	    	var strArr = oldtext.split(",");  
			strArr.sort();
            var tempStr=newtext;
			var count = -1;
            for(var i in strArr){  
                 if(strArr[i] == newtext){  
                     count =  i;
                 }  
            }
			strArr.splice(count,1);
            return strArr.join(",");
	    }
   });
   
   
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
(function($, window, undefined) {
	  var elems = $([]),
	    jq_resize = $.resize = $.extend($.resize, {}),
	    timeout_id,
	    str_setTimeout = 'setTimeout',
	    str_resize = 'resize',
	    str_data = str_resize + '-special-event',
	    str_delay = 'delay',
	    str_throttle = 'throttleWindow';
	  jq_resize[str_delay] = 250;
	  jq_resize[str_throttle] = true;
	  $.event.special[str_resize] = {
	    setup: function() {
	      if (!jq_resize[str_throttle] && this[str_setTimeout]) {
	        return false;
	      }
	      var elem = $(this);
	      elems = elems.add(elem);
	      $.data(this, str_data, {
	        w: elem.width(),
	        h: elem.height()
	      });
	      if (elems.length === 1) {
	        loopy();
	      }
	    },
	    teardown: function() {
	      if (!jq_resize[str_throttle] && this[str_setTimeout]) {
	        return false;
	      }
	      var elem = $(this);
	      elems = elems.not(elem);
	      elem.removeData(str_data);
	      if (!elems.length) {
	        clearTimeout(timeout_id);
	      }
	    },
	    add: function(handleObj) {
	      if (!jq_resize[str_throttle] && this[str_setTimeout]) {
	        return false;
	      }
	      var old_handler;
	      function new_handler(e, w, h) {
	        var elem = $(this),
	          data = $.data(this, str_data);
	        data.w = w !== undefined ? w : elem.width();
	        data.h = h !== undefined ? h : elem.height();
	        old_handler.apply(this, arguments);
	      }
	      if ($.isFunction(handleObj)) {
	          old_handler = handleObj;
	          return new_handler;
	      } else {
	        old_handler = handleObj.handler;
	        handleObj.handler = new_handler;
	      }
	    }
	  };
	  function loopy() {
	    timeout_id = window[str_setTimeout](function() {
	      elems.each(function() {
	        var elem = $(this),
	          width = elem.width(),
	          height = elem.height(),
	          data = $.data(this, str_data);
	        if (width !== data.w || height !== data.h) {
	          elem.trigger(str_resize, [data.w = width, data.h = height]);
	        }
	      });
	      loopy();
	    }, jq_resize[str_delay]);
	  }
	  
	  $(document).off('mousedown').on('mousedown', function (e) {
		    var target = e.target, $target = $(target);
	  		var droppanels = $("div[class='jazz-dropdown-panel jazz-widget-content jazz-helper-hidden']");
	  		$.each(droppanels,function(){
	  			  if($(this).is(":hidden")) {
	                  return;
	              }
	  			  var dropvtype = $(this).attr("vtype");
	  			  
	  			  if(dropvtype == 'datefield' || dropvtype == 'autocompletecomboxfield' || dropvtype == 'comboxfield' || dropvtype == 'comboxtreefield'){
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
		  			  if(dropvtype == 'datefield' || dropvtype == 'autocompletecomboxfield' || dropvtype == 'comboxfield' || dropvtype == 'comboxtreefield'){
		  				  var data = $("div[name="+dropname+"]").data(dropvtype)._getData();
		              	  $("div[name="+dropname+"]").data(dropvtype)._event("change", e, data);
		              	  var oldvalue = $("div[name="+dropname+"]").data(dropvtype).getValue();
		              	  $("div[name="+dropname+"]").data(dropvtype).oldchoices = oldvalue + "";
		  			  }
	              }
	  		});
      });
})(jQuery, this);;
var G = window.G || {};
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
    var $this = this,
        use = G.groupUrl;   //可以被合并的请求地址
    /**
     * 1. 分离请求地址前缀，去掉参数部分，保留等号
     * 	比如 http://localhsot/getData.do?id=
     * 2. 根据 1 中得到的前缀，匹配所有地址，找到具体的请求参数
     * 3. 根据 1 中得到的前缀，合并请求参数
     *  比如 {'http://localhost/getData.do?id=': '001,002,004'}
     */
    for(var i= 0, len=links.length; i<len; i++){
    	var key = links[i].replace(/([\s|\S]+=)((\w+,)*\w+)$/g, "$1"),
    	    param = links[i].replace(/([\s|\S]+=)((\w+,)*\w+)$/g, "$2");
    	if(/([\s|\S]+=)+((\w+,)*\w+)$/.test(links[i])){    		
    		if(use.hasOwnProperty(key)){
    			if(use[key].indexOf("@@") == -1){
    				$this._beforeRequestByGroup(links[i]);
    			}else{
    				$this._beforeRequestByGroup(key + param);
    			}
    			use[key] += ("@@" + param);
    		}else{
    			use[key] = param;
    			$this._beforeRequestByGroup(key + param);
    		}
    	}/*else{
    		//发送无法合并的请求
    	}*/
    }

    //开始遍历刚才的对象，找到需要处理的请求
    for(var key in use){
    	use[key] = use[key].replace(/@@/g, ",");
    	$this.getPageDataSetCache(key + use[key]);
    }
};

/**
 *
 * 用于记录页面所有url数据
 *
 * @param url
 * @param option
 *
 *
 *
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
            var i = 0;
            while(i < G.pageCache.length){
                if(cacheName == G.pageCache[i].cacheName){
                    G.pageCache[i].data = eval( "(" + data + ")" );
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
 *  将各项的请求状态置为ready
 *  避免重复请求
 * @private
 */
G._beforeRequestByGroup = function(cacheName, cacheParam){
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

G._getCacheNameIndex = function(cacheParam){
    var $this = this;
    for(var i=0, len=$this.pageDataSetCache.length; i<len; i++){
        if($this.pageDataSetCache[i]['cacheName'].indexOf(cacheParam) > -1){
            return i;
        }
    }
};

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

    var postData = null;
    if(jazz.config.platForm=='sword'){
        this.r_jazzData = new Object();
        this.r_dataArray = new Array();
        this.r_jazzData["data"] = this.r_dataArray;

        if(!!cacheParams){
            $.each(cacheParams,function(param,i){
                var _newDataObj = {};
                _newDataObj["sword"] = "attr";
                _newDataObj["name"] = param;
                _newDataObj["value"] = cacheParams[param];
                $this.r_dataArray.push(_newDataObj);
            });
            this.r_jazzData["tid"] = cacheName;

            postData = {};
            postData["postData"] = jazz.jsonToString(this.r_jazzData);
        }
    }

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
                    datajson["data"]["data"] = newdatajson.data[0].data;
                }
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
                            groupData = eval( "(" + datajson + ")" );
                        for(var j=0; j<group.length; j++){
                            var pi = $this._getCacheNameIndex(requestParams['url']+group[j]);
                            G.pageDataSetCache[pi].data = {data: groupData['data'][group[j]]};
                            G.pageDataSetCache[pi].status = "success";
                        }
                    }
            		//设置当前请求的结果状态
            		G.pageDataSetCache[i].data = eval( "(" + datajson + ")" )['data'];
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
            if(flag == true
                || (G.pageDataSetCache[i]['status'] != 'ready'
                && G.pageDataSetCache[i]['status'] != 'success') ){
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

};
(function($) {

	/**
	 * @version 0.5
	 * @name jazz.containerlayout
	 * @description 每个Container都委托布局管理器来渲染其子组件Component,通常不应该直接使用。
	 * @constructor
	 * @extends 
	 * @requires
	 * @example 
	 */	
	
    $.widget("jazz.containerlayout", {

        options: /** @lends jazz.containerlayout# */ {
        	
    		/**
			 *@type Object
			 *@desc 存储用来调用布局的容器对象 
			 *@default null
			 */        		
        	container: null,
        	
    		/**
			 *@type Array
			 *@desc 存储布局的类型
			 *@private
			 */             	
        	config: {
        		auto: 'autolayout', 
        		vbox: 'vboxlayout',
        		hbox: 'hboxlayout', 
        		table: 'tablelayout',
        		query: 'querylayout',
        		card: 'cardlayout',
        		column: 'columnlayout',
        		anchor: 'anchorlayout', 
        		border: 'borderlayout',
        		fit: 'fitlayout',
        		row: 'rowlayout'
        	},    	
        	        	
			/**
			 *@type Object
			 *@desc 设置好layout属性后，其他的布局配置属性项设置
			 *@default '{}'
			 */
			layoutconfig: {
				//列布局存放列的宽度 ['200px','300px','*'] 或  ['20%','400px','*']
				columnwidth:[],
				//默认显示边框
				border: false,   
				
				/* anchor布局  begin*/
				anchor: null,      //定义的锚点，默认为页面的中心点
				point: null,       //需要布局集合
				type: 0,           //0根据当前组件布局, 1根据页面布局
				/* anchor布局  begin*/
				
				/* vbox和hbox布局   begin*/
				interval: 10,      //组件间的间距
	        	align: 'left',     //对齐方式 
	        	/* vbox和hbox布局   end*/
	        	
	        	/* cardlayout */
	        	width: 400,           //组件默认宽度   
	        	buttondisplay: true,  //显示翻页按钮
	        	
	        	/* vbox和hbox布局    */
	        	/* columns布局    */
	        	margin: {
	        		top: 5,       //距上边框距离
	        		left: 5,      //距左边框距离
	        		right: 5,     //距右边框距离
	        		bottom: 5     //距底部距离
	        	}

			}

        },
        
        /** @lends jazz.containerlayout */
        
		/**
         * @desc 获取容器布局对象
         * @param {this} 组件的对象
         * @param {container} 需要渲染的容器对象
         * @return object 布局对象
		 * @throws
		 * @example $('container').containerlayout('containerLayout', this, $('container'));
         */
        containerLayout: function(cthis, container){
        	var p = 0, obj = null;
        	this.options.container = container;
        	if(typeof this.options.config[cthis.options.layout] == 'undefined'){
        		this.options.container.layout = this.options.config[0];
        	}else{
        		p = cthis.options.layout;
        	}
        	        	
        	if(jazz.isIE(7) || jazz.isIE(6)){
        		if(!(typeof(cthis.options.layoutconfig) == 'object')){
        			cthis.options.layoutconfig = jazz.stringToJson(cthis.options.layoutconfig);
        		}
        	}
        	
        	$.extend(this.options.layoutconfig, cthis.options.layoutconfig);
        	
        	if(cthis.options.layout == 'fit'){
        		cthis.element.layout({
            		layout: 'fit',
            		layoutconfig: {
            			fitCallback: function(){
            				cthis.panelFitCallback(cthis);            				
            			}
            		}    			
        		});
        	}else{
        		obj = eval('container.'+this.options.config[p]+'()');
        		obj.data(this.options.config[p]).layout(cthis, container, this.options.layoutconfig);
        	}
        },
        
		/**
         * @desc 容器默认布局
		 * @throws
		 * @example $('container').containerlayout('getDefaultLayout');
         */
        getDefaultLayout: function(){
        	return this.options.config[0];
        }

    });
    
    
})(jQuery);
(function($) {

	/**
	 * @version 0.5
	 * @name jazz.anchorlayout
	 * @description 锚布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 * @requires
	 * @example $('#panel_id').anchorlayout();
	 */
    $.widget("jazz.anchorlayout", $.jazz.containerlayout, {

    	/** @lends jazz.anchorlayout */        
        
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 当前组件对象
		 * @throws
		 * @example
         */
        layout: function(cthis, container, config) {
        	var anchor = config.anchor;
        	var points = config.point;
        	container.addClass("jazz-layout");
        	
        	container.css({border: '5px solid red'});
        	//config.type='0';
        	
        	if(anchor==null){
        		if(config.type=='1'){
	        		var page = this._getPageSize();
	        		var x  = page.windowWidth/2, y = page.windowHeight/2;
	        		anchor = config.anchor = {x: x, y: y};
	                //jazz.log('page.windowWidth='+page.windowWidth+'***page.windowHeight='+page.windowHeight+'x='+x+'***'+'y='+y);
        		}else{
        			container.css({width:1200, height: 400 });
        			var top = container.offset().top;
        			var left = container.offset().left;
        			var cw = container.width()/2;
        			var ch = container.height()/2;
        			
        			top = parseInt(top + ch);
        			left = parseInt(left + cw);
        			
        			anchor = config.anchor = {x: left, y: top};
        			//jazz.log('top='+top+'***left='+left+'***cw='+cw+'***ch='+ch);
        		}
        	}
        	
        	var cheight = 0;
        	$.each(points, function(i, p){
        		if(!!p.id){
        			var x  = parseInt(anchor.x) + parseInt(p.offset.x);
        			var y = parseInt(anchor.y) + parseInt(p.offset.y);
        			var n = $('#'+p.id).outerHeight(true) + y;   //计算DIV高度
        			if(n > cheight){
        				cheight = n;
        			}
        			$('#'+p.id).appendTo(container);
        			$('#'+p.id).css({
        				position: 'absolute',
        				top: y,
        				left: x
        			});
        		}
        	});
        	
        	container.height(cheight + 20);
        	
        },

		/**
         * @desc 计算页面大小
		 * @private
		 * @example  this._getPageSize();
         */
		_getPageSize: function () {
		    var xScroll, yScroll;
		    if (window.innerHeight && window.scrollMaxY) {
		        xScroll = window.innerWidth + window.scrollMaxX;
		        yScroll = window.innerHeight + window.scrollMaxY;
		    } else {
		        if (document.body.scrollHeight > document.body.offsetHeight) { // all but Explorer Mac    
		            xScroll = document.body.scrollWidth;
		            yScroll = document.body.scrollHeight;
		        } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari    
		            xScroll = document.body.offsetWidth;
		            yScroll = document.body.offsetHeight;
		        }
		    }
		    var windowWidth, windowHeight;
		    if (self.innerHeight) { // all except Explorer    
		        if (document.documentElement.clientWidth) {
		            windowWidth = document.documentElement.clientWidth;
		        } else {
		            windowWidth = self.innerWidth;
		        }
		        windowHeight = self.innerHeight;
		    } else {
		        if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode    
		            windowWidth = document.documentElement.clientWidth;
		            windowHeight = document.documentElement.clientHeight;
		        } else {
		            if (document.body) { // other Explorers    
		                windowWidth = document.body.clientWidth;
		                windowHeight = document.body.clientHeight;
		            }
		        }
		    }       
		    // for small pages with total height less then height of the viewport    
		    if (yScroll < windowHeight) {
		        pageHeight = windowHeight;
		    } else {
		        pageHeight = yScroll;
		    }   
		    // for small pages with total width less then width of the viewport    
		    if (xScroll < windowWidth) {
		        pageWidth = xScroll;
		    } else {
		        pageWidth = windowWidth;
		    }
		    
		    return {
		    	'pageWidth': pageWidth, 
		    	'pageHeight': pageHeight, 
		    	'windowWidth': windowWidth, 
		    	'windowHeight': windowHeight 
		    };
		}
        
    });
    
})(jQuery);
(function($) {

	/**
	 * @version 0.5
	 * @name jazz.autolayout
	 * @description 自动布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 * @requires
	 * @example $('#panel_id').autolayout();
	 */
    $.widget("jazz.autolayout", $.jazz.containerlayout, {

    	/** @lends jazz.autolayout */        
        
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 当前组件对象
		 * @throws
		 * @example
         */
        layout: function(cthis, container, config) {
//        	var config = cthis.options.layoutConfig;
//        	container.addClass("jazz-layout");
//        	//container.css({border: '1px solid red'});
//        	var items = container.children();   //获取全部的子对象
//        	var bc = {   //记录前一组件的值
//    			top: 0,
//    			left: 0,
//    			height: 0
//            };
        	
        	//对齐位置
        	//this._align(items, container, config, bc);
        	
        }

//		/**
//         * @desc 设置布局
//         * @param {items} 需要布局的组件集合
//         * @param {container} 当前布局容器对象
//         * @param {config} 组件布局配置对象
//         * @param {bc} 布局时记录上一个组件的对象
//		 * @throws {container} 所在布局容器的对象
//		 * @private
//		 * @example  this._align(items, container, config, bc);
//         */        
//        _align: function(items, container, config, bc){
//        	var contentHeight = parseInt(container.height()) + 1;   //需要布局组件的高度  
//        	var cHalfWidth = 0;
//        	if(config.align=='center') cHalfWidth = container.width()/2;
//        	for(var i = 0, len = items.length; i<len; i++){ 
//        		var top = 0;
//        		if(i==0){
//        			top = bc.top = parseInt(config.interval);
//        		}else{
//        			top = bc.top = parseInt(config.interval) + bc.height + bc.top;
//        		}
//        		bc.height = items.eq(i).outerHeight(true);
//        		
//        		if(config.align=='right')
//        			items.eq(i).css({position: 'absolute', top: top, right: parseInt(config.right) });
//        		else if(config.align=='center')
//        			items.eq(i).css({position: 'absolute', top: top, left: parseInt(cHalfWidth - items.eq(i).width()/2) });
//        		else
//        			items.eq(i).css({position: 'absolute', top: top, left: parseInt(config.left) });
//        	} 
//        	//重新计算容器高度
//        	container.height(contentHeight + parseInt(config.top) + config.bottom);  
//        }
        
    });
    
})(jQuery);
(function($) {

	/**
	 * @version 0.5
	 * @name jazz.boxlayout
	 * @description boxlayout是布局hboxLayout和vboxLayout基础类。通常不应该直接使用。
	 * @constructor
	 * @extends jazz.containerlayout
	 * @requires
	 * @example $('#panel_id').boxlayout();
	 */
    $.widget("jazz.boxlayout", $.jazz.containerlayout, {

        options: /** @lends jazz.boxlayout# */ {
			/**
			 *@type boolean
			 *@desc 组件间的间隔数值
			 *@default false
			 */
        	interval: 10,
        	
			/**
			 *@type number
			 *@desc 距离顶部边框的值
			 *@default 10
			 */        	
        	top: 20,
        	
			/**
			 *@type number
			 *@desc 距离边框的值
			 *@default false
			 */        	  	
        	left: 10
        },
        
        /** @lends jazz.boxlayout */        
        
		/**
         * @desc 创建组件
		 * @throws
		 * @example
         */        
        _create: function() {
        	
        }
        
        
        
        

    });
    
    
})(jQuery);
(function($) {
	
	/**
	 * @version 0.5
	 * @name jazz.hboxlayout
	 * @description 水平布局。
	 * @constructor
	 * @extends jazz.boxlayout
	 * @requires
	 * @example $('#panel_id').hboxlayout();
	 */	
    $.widget("jazz.hboxlayout", $.jazz.boxlayout, {

    	
        /** @lends jazz.hboxlayout */       

		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 当前组件对象
         */
        layout: function(cthis, container, config) {
        	cthis.content.addClass("jazz-layout");
        	//container.css({border: '1px solid red'});
        	var items = container.children();   //获取全部的子对象
        	var bc = {   //记录前一组件的值
    			top: 0,
    			left: 0,
    			width: 0
        	};
        	//对齐位置
        	this._align(cthis, items, container, config, bc);       	
        },

		/**
         * @desc 设置布局
         * @param {cthis} 当前对象
         * @param {items} 需要布局的组件集合
         * @param {container} 当前布局容器对象
         * @param {config} 组件布局配置对象
         * @param {bc} 布局时记录上一个组件的对象
		 * @private
         */
        _align: function(cthis, items, container, config, bc){

        	for(var i = 0, len = items.length; i<len; i++){
        		
            		var left = 0;
            		if(i==0){
            			left = bc.left = 0;   //0;
            		}else{
            			left = bc.left =  bc.width + bc.left;
            		}
            		bc.width = items.eq(i).outerWidth(true);

            		items.eq(i).css({position: 'absolute', top:0, left: left });
            	
        		
        	}
        	
        	//重新计算容器高度
        	if(cthis.options.height === -1){ //外部未指定容器高度，自适应
        		container.height(allheight + parseInt(config.margin.top) + parseInt(config.margin.bottom));
        	}
        	
        }    
  
    });
    
})(jQuery);
(function($) {
	/**
	 * @version 0.5
	 * @name jazz.vboxlayout
	 * @description 垂直排列的布局。
	 * @constructor
	 * @extends jazz.boxlayout
	 * @requires
	 * @example $('#panel_id').vboxlayout();
	 */	
    $.widget("jazz.vboxlayout", $.jazz.boxlayout, {

    	/** @lends jazz.vboxlayout */        
        
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 当前组件对象
		 * @throws
		 * @example
         */
        layout: function(cthis, container, config) {
        	container.addClass("jazz-layout");
        	//container.css({border: '1px solid red'});
        	var items = container.children();   //获取全部的子对象
        	var bc = {   //记录前一组件的值
    			top: 0,
    			left: 0,
    			height: 0
            };
        	
        	//对齐位置
        	this._align(cthis, items, container, config, bc);
        	
        },

		/**
         * @desc 设置布局
         * @param {cthis} 当前对象
         * @param {items} 需要布局的组件集合
         * @param {container} 当前布局容器对象
         * @param {config} 组件布局配置对象
         * @param {bc} 布局时记录上一个组件的对象
		 * @private
		 * @example  this._align(cthis, items, container, config, bc);
         */        
        _align: function(cthis, items, container, config, bc){

    		//计算左右偏移量
        	for(var i = 0, len = items.length; i<len; i++){
        		var top = 0;
        		if(i==0){
        			top = bc.top = 0;
        		}else{
        			top = bc.top =  bc.height + bc.top;
        		}
        		bc.height = items.eq(i).outerHeight(true);

        		items.eq(i).css({position: 'absolute', top: top, left: 0 });
        	}
        	//重新计算容器高度
        	if(cthis.options.height === -1){
        		container.height(bc.height + bc.top );
        	}
        	
        }
        
    });
    
})(jQuery);(function($) {

	/**
	 * @version 0.5
	 * @name jazz.rowlayout
	 * @description 行布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 * @requires
	 * @example $('#panel_id').rowlayout();
	 */	
    $.widget("jazz.rowlayout", $.jazz.containerlayout, {

    	/** @lends jazz.rowlayout */    
    	options: {
    		$this: null,
    		container: null
    	},
    	
    	reloadLayout: function(layoutConfig){
    		var el = this.element;
    		this.layout(el.$this, el.container, layoutConfig);
    	},
    	
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutConfig} configLayout配置信息
         */
        layout: function(cthis, container, layoutConfig) {
        	var el = this.element; 
        	el.$this = cthis;
        	el.container = container;
        	
        	this.rowheights = layoutConfig.rowheight;
        	var element = container.children();        	//列布局的列元素对象
        	var rows = this.rowheights.length;
        	
        	//列布局时，禁止容器出现滚动条
        	if(jazz.isIE(6) || jazz.isIE(7)){
        		container.css('overflow', 'hidden');
        	}
        	
        	this.fixRowHeight = 0; //计算全部固定列的宽度
        	this.noFixRow = [];
        	var border = layoutConfig.border; //是否显示边框
        	for(var i=0; i<rows; i++){
    			var tempObject = $(element[i]);
    			//去除margin
    			tempObject.css('margin', '0px');
        		//添加边框
        		if(border){
        			tempObject.addClass('jazz-layout-border');
	    		}else{
	    			tempObject.addClass('jazz-layout-no-border');
	    		}             		
        		
        		if(this.rowheights[i].indexOf('px')!=-1){
        			tempObject.outerHeight(this.rowheights[i]);
        			this.fixRowHeight = this.fixRowHeight + tempObject.outerHeight();     			
        		}else{
        			//非固定列
        			this.noFixRow.push(tempObject);
        		}
        	}

        	jazz.log('固定列高度======='+this.fixRowHeight);

           	//容器的总高度
        	var containerHeight = Math.min(container.height(), container.get(0).clientHeight);
        	
        	this.calculateHeight(containerHeight, element, rows);
        	jazz.log('容器高度======='+containerHeight);
        	//监听父容器大小改变
        	var $this = this;
        	cthis.element.children('.jazz-panel-content').off('resize.rowlayout').on('resize.rowlayout', function(){
        		var containerHeight = Math.min(container.height(), container.get(0).clientHeight);
        		if($this.options.cacheHeight != containerHeight){
        			$this.calculateHeight(containerHeight, element, rows);
        		}
        	});
        },
        
		/**
         * @desc 计算高度
         * @param {containerHeight} 容器高度
         * @param {element} 子容器集合
         * @param {cols} 子容器个数
         */    
        calculateHeight: function(containerHeight, element, rows){
        	//其他部分总宽度
        	var otherHeight = containerHeight - this.fixRowHeight;
        	
        	jazz.log('******其他区域高度*******'+otherHeight);
        	
        	//百分比总宽度
        	this.percentHeight = 0;
        	for(var i=0; i<rows; i++){
        		var rowheight = this.rowheights[i];
        		var tempObject = $(element[i]);
        		//判断是否为百分比列宽
        		if(!!rowheight && rowheight.indexOf('%')!= -1){
        			var n = parseInt(rowheight.substring(0, rowheight.indexOf('%')));
        			rowheight = (n/100)*otherHeight;
        			//设置百分比列的宽度
        			tempObject.outerHeight(rowheight);
        			this.percentHeight = this.percentHeight + tempObject.outerHeight();
        		}
        		//自适应宽度列
        		if($.trim(rowheight) == '*'){
        			this.emptyRow = tempObject;
        		}
        	}
        	
        	jazz.log('********百分比列高度********'+this.percentHeight);
        	
        	//去除固定列、百分比列高度后，剩余高度
        	this.autoHeight = containerHeight - this.fixRowHeight - this.percentHeight;
        	
        	jazz.log('********自动高度列*********'+this.autoHeight);
        	
        	//判断未指定宽度的列的数量，仅有1列时，计算这列的宽度
        	if(!!this.emptyRow){
	    			this.emptyRow.outerHeight(this.autoHeight - 1);
        	}
    		
    		//缓存容器高度,用于监听时进行比较窗体高度是否发生改变
        	this.options.cacheHeight = containerHeight;
        }

    });

})(jQuery);(function($) {

	/**
	 * @version 1.0
	 * @name jazz.columnlayout
	 * @description 列布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 */
    $.widget("jazz.columnlayout", $.jazz.containerlayout, {

    	/** @lends jazz.columnlayout */    
    	options: {
    		$this: null,
    		container: null
    	},
    	
		/**
         * @desc 设置布局
         * @param {layoutConfig} configLayout配置信息
         */    	
    	reloadLayout: function(layoutConfig) {
    		var el = this.element;
    		this.layout(el.$this, el.container, layoutConfig);
    	},
    	
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutConfig} configLayout配置信息
         */
        layout: function(cthis, container, layoutConfig) {
        	var el = this.element; 
        	el.$this = cthis;
        	el.container = container;
        	
        	this.colwidths = layoutConfig.columnwidth;
        	var element = container.children();        	//列布局的列元素对象
        	var cols = this.colwidths.length;


        	//列布局时，禁止容器出现滚动条
        	//if(jazz.isIE(6) || jazz.isIE(7)){
        		container.css('overflow', 'hidden');
        	//}
        	
        	this.fixColumnWidth = 0; //计算全部固定列的宽度
        	this.noFixColumn = [];
        	var border = layoutConfig.border; //是否显示边框
        	for(var i=0; i<cols; i++){
    			var tempObject = $(element[i]);
    			//去除margin
    			tempObject.removeAttr("style");
        		tempObject.addClass('jazz-column-element');  		
        		
        		if(this.colwidths[i].indexOf('px') != -1){
        			tempObject.outerWidth(this.colwidths[i]);
        			this.fixColumnWidth = this.fixColumnWidth + tempObject.outerWidth();     			
        		}else{
        			//非固定列
        			this.noFixColumn.push(tempObject);
        		}
        	}

           	//容器的总宽度
        	//var containerWidth = Math.min(container.width(), container.get(0).clientWidth);
        	var containerWidth = container.width();
        	
        	jazz.log("**********列布局 容器宽度************"+containerWidth);
        	
        	this.calculateWidth(containerWidth, element, cols);
        
        	//监听父容器大小改变
        	var $this = this;
        	cthis.element.children('.jazz-panel-content').off('resize.columnLayout').on('resize.columnLayout', function(){
//        		var containerWidth = Math.min(container.width(), container.get(0).clientWidth);
//        		if($this.options.cacheWidth != containerWidth){
//        			$this.calculateWidth(containerWidth, element, cols);
//        		}
        	});
        },
        
		/**
         * @desc 计算宽度
         * @param {containerWidth} 容器宽度
         * @param {element} 容器宽度
         * @param {cols} 容器宽度
         */    
        calculateWidth: function(containerWidth, element, cols){
        	//其他部分总宽度
        	var otherWidth = containerWidth - this.fixColumnWidth;
        	
        	jazz.log(containerWidth+'******其他区域宽度*******'+otherWidth);
        	
        	//百分比总宽度
        	this.percentWidth = 0;
        	for(var i=0; i<cols; i++){
        		var colwidth = this.colwidths[i];
        		var tempObject = $(element[i]);
        		//判断是否为百分比列宽
        		if(!!colwidth && colwidth.indexOf('%') != -1){
        			var n = parseInt(colwidth.substring(0, colwidth.indexOf('%')));
        			colwidth = (n/100)*otherWidth;
        			tempObject.outerWidth(colwidth);
        			
        			this.percentWidth = this.percentWidth + tempObject.outerWidth();
        		}
        		//自适应宽度列
        		if($.trim(colwidth) == '*'){
        			this.emptyColumn = tempObject;
        		}
        	}
        	
        	jazz.log('********百分比列宽度********'+this.percentWidth);
        	
        	//去除固定列、百分比列宽度后，剩余宽度
        	this.autoWidth = containerWidth - this.fixColumnWidth - this.percentWidth;
        	
        	//alert('*总宽度*'+containerWidth+"***固定列宽度*"+this.fixColumnWidth+"***百分比列宽度*"+this.percentWidth+"***自动列*"+this.autoWidth);
        	
        	//判断未指定宽度的列的数量，仅有1列时，计算这列的宽度
        	if(!!this.emptyColumn){
	    		this.emptyColumn.outerWidth(this.autoWidth - 1);
        	}
        	
        	jazz.log("********自适应列宽度********"+this.emptyColumn.outerWidth());
    		
    		//缓存容器宽度,用于监听时进行比较窗体宽度是否发生改变
        	this.options.cacheWidth = containerWidth;
        }

    });

})(jQuery);
(function($) {
	
	/**
	 * @version 0.5
	 * @name jazz.tablelayout
	 * @description 表格布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 * @requires
	 * @example $('#panel_id').tablelayout();
	 */	
    $.widget("jazz.tablelayout", $.jazz.containerlayout, {

    	/** @lends jazz.tablelayout */        
        
    	
    	reloadLayout: function(layoutconfig) {
    		var el = this.element; 
    		this.layout(el.$this, el.container, layoutconfig ,el.allitems);
    	},
    	
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 当前组件对象
		 * @throws
		 * @example
         */
        layout: function(cthis, container, config ,allitems) {
        	var el = this.element; 
        	el.$this = cthis;
        	el.container = container;
        	
        	container.addClass("jazz-layout");
        	//container.css({border: '1px solid red'});
        	var items = container.children();   //获取全部的子对象
        	if(!!allitems){
        		items = allitems;
        	}else{
        		el.allitems = items;
        	}
        	var bc = {   //记录前一组件的值
    			top: 0,
    			left: 0,
    			height: 0
            };
        	
        	//this._align(items, container, config, bc);
        	this._align(items, container, config);
        	$(container).css("overflow-x","hidden");
        },

		/**
         * @desc 设置布局
         * @param {items} 需要布局的组件集合
         * @param {container} 当前布局容器对象
         * @param {config} 组件布局配置对象
		 * @throws {container} 所在布局容器的对象
		 * @private
         */ 
        
        _align: function(items, container, config){
        	if(!!items&&items.length>0){
        		var tableWidth = $(container).outerWidth(true)-$(container).scrollLeft();
        		var border = config.border;
        		this.table=null;
        		if(border){
        			this.table=$("<table border='1px' cellspacing='0' cellpadding='0' style='border-collapse:collapse;table-layout:fixed;width:"+tableWidth+"px;"+"'></table>");
        		}else{
        			this.table=$("<table style='border-collapse:collapse;table-layout:fixed;width:"+tableWidth+"px;"+"'></table>");
        		}
		       	
        		this.table.appendTo(container);
		       	
		       	var cols = config.columnwidth.length;//table行数不设定，列数设定
		       	
		       	var colgroup = "<colgroup>";
		       	for(var i=0;i<cols;i++){
		       		colgroup += "<col />";
		       	}
		       	colgroup += "</colgroup>";
		       	colgroup = $(colgroup).appendTo(this.table);
		       	
		       	var columnwidth =  config.columnwidth;
		       	if(columnwidth.length==0){
		       		alert("【table 布局】columnwidth未设置布局列宽，请确定重设。");
		       		return false;
		       	}
		       	if(!this.validateColumnWidthFormat(columnwidth)){
	        		alert("【table 布局】columnWidth设置数值格式不正确。");
	        	}
	        	this.calculateTableWidth($(this.table),columnwidth);
	        	
	        	//2.绑定table布局的resize事件
	        	var $this = this;
	        	$this.options.cacheContainerWidth = tableWidth;
	        	$(container).on('resize.tableLayout', function(){
	        		var tableCurrentWidth = $(this).width();
	        		if($this.options.cacheContainerWidth != tableCurrentWidth){
	        			$this.options.cacheContainerWidth = tableCurrentWidth;
	        			$($this.table).width(tableCurrentWidth+"px");
	        			$this.calculateTableWidth(this,columnwidth);
	        		}
	        		
	        	});
	        	
	        	this.tbody = $("<tbody></tbody>").appendTo(this.table);
	        	
	        	var tr = null;
	        	if(!!config.rowheight){
	        		tr = $("<tr style='height:"+config.rowheight+"'></tr>");
	        	}else{
	        		tr = $("<tr></tr>");
	        	}
	        	var td = $("<td style='vertical-align: top;'></td>");
	        	for(var i=0; i<cols; i++){
	        		var newtd = td.clone();
	    			newtd.appendTo(tr);
	    		}
	    		tr.clone().appendTo(this.tbody);
	        	
	    		var rowIndex=0,colIndex=0;
	    		for(var i=0; i<items.length; i++){
	    			var item = items[i];
	    			
	    			if($(item).attr('vtype')=="hiddenfield"||$(item).attr('vtype')=="toolbar"){
	    				/*$(container).after($(item));
	    				if($(item).attr('location')=="top"){
	    					$(item).insertBefore($(container).parent());
	    				}*/
	    				continue;
	    			}
	    			
	        		var rowspanNum = parseInt($(item).attr("rowspan"))|| 1;
	        		var colspanNum = parseInt($(item).attr("colspan"))|| 1;
	        		
	        		if(colspanNum>cols){
	        			alert("【跨列布局错误】当前容器中第"+(i+1)+"个元素colspan='"+colspanNum+"' 大于布局列数 cols='"+cols+"'。 ");
	        			break;
	        		}else{
	        			
	        			var nextIndex = this.combinedTdCell(item,tr,cols,rowIndex,colIndex,rowspanNum,colspanNum);
	        			rowIndex = nextIndex.rowIndex;
	        			colIndex = nextIndex.colIndex;
	        		}
	    		}
        	}
        },
        calculateTableWidth: function(table,columnWidth){
        	var width_table = $(table).width();
        	var re = /^[0-9]+.?[0-9]*$/;
        	var fixedColumnWidth = 0;
        	var exceptFixedColumnWidth = 0;
        	var percentColumnWidth = 0;
        	var hasPercentWidth = false;
        	var autoWidthNums = 0;
        	var tempColumnWidth = [];
        	for(var i=0,m=columnWidth.length; i<m; i++){
        		var temp = columnWidth[i];
        		if(temp.indexOf('px')!=-1||re.test(temp)){
        			fixedColumnWidth += parseFloat(columnWidth[i]);
        		}
        		if(!hasPercentWidth&&temp.indexOf('%')!=-1){
        			hasPercentWidth = true; 
        		}
        		if($.trim(temp) == '*'){
        			autoWidthNums++;
        		}
        		tempColumnWidth[i] = temp;
        	}
        	exceptFixedColumnWidth = width_table-fixedColumnWidth;
        	if(hasPercentWidth){
        		//exceptFixedColumnWidth = width_table-fixedColumnWidth;
	        	for(var i=0,m=tempColumnWidth.length; i<m; i++){
	        		var temp = tempColumnWidth[i];
	        		if(temp.indexOf('%')!=-1){
	        			var a = temp.substring(0, temp.indexOf('%'));
	        			var b = Math.round((a/100)*exceptFixedColumnWidth);
		        		tempColumnWidth[i] = b+"px";//将原%数值改为px数值
		        		percentColumnWidth += b;
	        		}
	        	}
        	}
        	
        	if(autoWidthNums>0){
        		var leftColumnWidth = exceptFixedColumnWidth - percentColumnWidth;
	        	for(var i=0,m=tempColumnWidth.length; i<m; i++){
	        		if($.trim(tempColumnWidth[i]) == '*'){
	        			tempColumnWidth[i] = Math.round(leftColumnWidth/autoWidthNums) +"px";////将原*星号自适应标志改为px数值
	        		}
	        	}
        	}
        	
        	//只设置columnWidth中定义的值，对于没有设置列宽的列，不予赋值
        	for(var i=0; i<tempColumnWidth.length; i++){
        		$(table).find("colgroup").children().eq(i).attr("width",tempColumnWidth[i]);
        	}
        },
        validateColumnWidthFormat: function(colsWidth){
        	var re = /^[0-9]+.?[0-9]*[(px)%]?$/;
        	var flag=true;
        	for(var i=0; i<colsWidth.length; i++){
        		var temp = colsWidth[i];
        		if(re.test(temp)||$.trim(temp) == '*'){
        			//符合列宽定义标准
        		}else{
        			flag=false;
        			break;
        		}
        	}
        	return flag;
        },
        
		combinedTdCell: function(item,tr,cols,rowIndex,colIndex,rowspanNum,colspanNum){
			//1.根据当前的colIndex校验该rowIndex行能否放得下colspanNum的元素
			var flag = true;
			while(flag){
				
				if(colIndex+colspanNum>cols){
					colIndex=0;
					rowIndex++;
				}
				
	        	var trs = this.tbody.children("tr").length;
	        	if(rowIndex+1>trs){
	        		$(tr).clone().appendTo(this.tbody);
	        		colIndex = 0;
	        		flag = false;
	        	}else{
	        		this.tbody.children("tr:eq("+rowIndex+")").children("td").each(function(i){
						if(i>=colIndex&&i<cols){
							if($(this).css("display")!="none"){
								colIndex = i;
								return false;
							}
						}
					});
					if(colIndex+colspanNum>cols){
						//colIndex=i,折行，继续下一行校验
					}else{
						//校验是否有colspanNum个display的单元格
						var isContinus = true;
						this.tbody.children("tr:eq("+rowIndex+")").children("td").each(function(i){
							if(i>=colIndex&&i<colIndex+colspanNum){
								if($(this).css("display")=="none"){
									isContinus = false;
								}
							}
						});
						if(isContinus){
							flag = false;
						}else{
							colIndex++;//下一个单元格继续校验
						}
					}
	        	}
			}
			
			//2.放置数据，合并隐藏单元格
			var targetTD = this.tbody.children("tr:eq("+rowIndex+")").children("td:eq("+colIndex+")");

			$(item).appendTo(targetTD);
			
			jazz.log("宽度："+targetTD.width()+"-----"+$(item).parent().width());
			
			if(rowspanNum>1 || colspanNum>1){
				var trCounts = this.tbody.children("tr").length;
				if(rowspanNum+rowIndex>trCounts){
					for(var r=1;r<=rowspanNum+rowIndex-trCounts;r++){
						var newtr = tr.clone();
						newtr.appendTo(this.tbody);
					}
				}
				
				this.tbody.children("tr").each(function(n){
					if(n>=rowIndex&&n<rowIndex+rowspanNum){
						$(this).children("td").each(function(m){
							if(m>=colIndex&&m<colIndex+colspanNum){
								if(n==rowIndex&&m==colIndex){
									$(this).attr({"rowspan":rowspanNum,"colspan":colspanNum});
								}else{
									//$(this).hide();
									$(this).remove();
								}
							}
						});
					}
				});
			}
			//3.最后得到放置下一个item元素的预定位置
			//当前位置（rowIndex，colIndex，rowspanNum，colspanNum）
			if(colIndex+colspanNum<cols){
				colIndex += colspanNum;
			}else{
				colIndex=0;
				rowIndex++;
			}
			
			var next = true;
			var nextTdIndex = null;
			while(next){
				
	        	var trsum = this.tbody.children("tr").length;
	        	if(rowIndex+1>trsum){
	        		//$(tr).clone().appendTo(this.tbody);
	        		colIndex = 0;
	        		next = false;
	        	}else{
	        		this.tbody.children("tr:eq("+rowIndex+")").children("td").each(function(i){
						if(i>=colIndex&&i<cols){
							if($(this).css("display")!="none"){
								colIndex = i;
								next = false;
								return false;
							}
						}
					});
	        	}
	        	if(next){
					colIndex=0;
					rowIndex++;
				}else{
					nextTdIndex = {"rowIndex":rowIndex,"colIndex":colIndex};
				}
			}
			
			return nextTdIndex;
		},
		hiddenRowByRowIndex: function(rowIndex){
			var that = this;
			
			//$(that.element[0]).find("table tr").slice():eq("+(rowIndex-1)+")").css("display","none");
		},
		showRowByRowIndex: function(rowIndex){
			var that = this;
			$(that.element[0]).find("table tr:eq("+(rowIndex-1)+")").css("display","");
		}
    });
    
})(jQuery);
(function($) {

	/**
	 * @version 0.5
	 * @name jazz.cardlayout
	 * @description 卡片布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 * @requires
	 * @example $('#panel_id').cardlayout();
	 */
    $.widget("jazz.cardlayout", $.jazz.containerlayout, {

        /** @lends jazz.cardlayout */

		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {config} 需要渲染的容器对象
		 * @throws
		 * @example
         */
        layout: function(cthis, container, config) {
        	var buttondisplay = this.options.cardlayoutObject.buttondisplay = config.buttondisplay;
        	var cardlayoutObject = this.options.cardlayoutObject;
        	cardlayoutObject.container = container;
        	cardlayoutObject.$this = this;
        	
        	var obj = container.children();
        	if(!obj[0]){
        		jazz.error('card layout is error!'); return false;
        	}
        	
        	var w = obj[0].width || $(obj[0]).outerWidth(true);
        	
        	var num = obj.size();
        	var ulWidth = num * w;
        	cardlayoutObject.width = w;
        	
        	cthis.element.addClass('jazz-cardpanel');  
        	
        	
        	var box = $('<div class="jazz-cardpanel-box"></div>').appendTo(container);
        	var content = $('<div class="jazz-cardpanel-content"></div>').appendTo(box);
        	var ul = $('<div class="jazz-cardpanel-ul" style="width:'+ulWidth+'px"></div>').appendTo(content);
			$.each(obj, function(i, el){
        		var li = $('<div class="jazz-cardpanel-li" style="width:'+cardlayoutObject.width+'px" >').appendTo(ul);
        		li.append(el);
        	});
			
			var h = ul.height();
        	
			if(buttondisplay===true){
			    this.paginator = $('<div></div>').appendTo(box);
				this.paginator.paginator({
					template: '{PageLinks}',
					rows: 1,
					totalRecords: obj.size(),
					click: function(e, ui){
						cardlayoutObject.point(ui.page, num);
					}
				});
				this.paginator.removeClass('ui-widget-header');
				cardlayoutObject.paginator = this.paginator;
			}
			
			
			//重新计算高度
			$.each(obj, function(i, el){
				$(el).height(h - cardlayoutObject.height);
			});

			if(buttondisplay===true){
				this._buildButton(box, num);
			}

        	box.css({height: h});
        	content.css({width: cardlayoutObject.width, height: h - cardlayoutObject.height});
       	
        },
        
		/**
         * @desc 设置布局
         * @param {box} box容器
         * @param {num} 滚动页数
		 * @example
         */        
		_buildButton: function(box, num){
			var $this = this;
			var cardlayoutObject = this.options.cardlayoutObject;
        	this.leftButton = $('<a href="javascript:void(0);" target="_self"></a>').appendTo(box);
        	this.rightButton = $('<a href="javascript:void(0);" target="_self"></a>').appendTo(box);
        	this.leftButton.on('click', function(){
        		if(cardlayoutObject.activeIndex > 0){
        			cardlayoutObject.slide('l', num);
        		}
        	});
        	this.rightButton.on('click', function(){
        		if(cardlayoutObject.activeIndex < num-1){
        			cardlayoutObject.slide('r', num);
        		}
        	});
	        box.hover(function(){
	         	 if(cardlayoutObject.activeIndex > 0){
		        	 $this.leftButton.addClass('jazz-cardpanel-leftbtn');
	        	 }
	         	 if(cardlayoutObject.activeIndex < num-1){
	         		$this.rightButton.addClass('jazz-cardpanel-rightbtn');	         		 
	         	 }	        	 
            },function(){
          	     $this.leftButton.removeClass('jazz-cardpanel-leftbtn');
	        	 $this.rightButton.removeClass('jazz-cardpanel-rightbtn');
            });         	
		},
		
		/**
         * @desc 设置布局
         * @param {n} 页数
		 * @example
         */  		
		previousPage: function(n){
			return this.options.cardlayoutObject.slide('l', n);
		},
		
		/**
         * @desc 设置布局
         * @param {n} 页数
		 * @example
         */  		
		nextPage: function(n){
			return this.options.cardlayoutObject.slide('r', n);
		},
    
        options : {
    		/**
             * @desc 存储处理逻辑
             */        	
	        cardlayoutObject: {
	        	    height: 25,
		    		width: 400,
		    		activeIndex: 0,
		    		paginator: null,
		    		container: null,
		    		$this: null,
		    		buttondisplay: true,
		    	    point: function(index, num) {
		    	    	this.activeIndex = index;   
		    	    	this.switching(index, num);
		    	    },
		    	    slide: function(lr, num) {
		    	        var idx = this.index(lr, num);
		    	        if(idx<0) idx = 0;
		    	        else if(idx>=num) idx = 4;
		    	        this.switching(idx, num);
                        return idx;
		    	    },
		    	    index: function(lr, num) {
		    	        if (lr == "l") {
		    	        	this.activeIndex = this.activeIndex - 1;
		    	        	if(this.activeIndex < 0) this.activeIndex = 0; 
		    	        }
		    	        else {
		    	        	this.activeIndex = this.activeIndex + 1;
		    	        	if(this.activeIndex >= num) this.activeIndex = num - 1;
		    	        }
		    	        if(this.buttondisplay === true){
		    	        	this.paginator.paginator('option', 'page', this.activeIndex);
		    	        }
		    	        return this.activeIndex;
		    	    },
		    	    switching: function(index, num) {
		    	    	this.container.find(".jazz-cardpanel-ul").animate({marginLeft: (0 - this.width * index)}, 500);
		    	    	
		    	    	if(this.buttondisplay === true){
				         	if(this.activeIndex > 0){
					        	this.$this.leftButton.addClass('jazz-cardpanel-leftbtn');
				        	}else{
				        		this.$this.leftButton.removeClass('jazz-cardpanel-leftbtn');
				        	}
				        	if(this.activeIndex < num-1){
				        		this.$this.rightButton.addClass('jazz-cardpanel-rightbtn');
				        	}else{
				        		this.$this.rightButton.removeClass('jazz-cardpanel-rightbtn');
				        	}	
		    	    	}
		    	    }
	        }
        }
    
    });
    
})(jQuery);

(function($) {

	/**
	 * @version 0.5
	 * @name jazz.fitlayout
	 * @description 自适应高度布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 * @requires
	 * @example $('#panel_id').fitlayout();
	 */	
    $.widget("jazz.fitlayout", $.jazz.containerlayout, {

    	/** @lends jazz.fitlayout */        
        
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 当前组件对象
		 * @throws
		 * @example
         */
        layout: function(cthis, container, layoutConfig) {
        	container.addClass("jazz-layout");
        	
        	var that = this;
        	var $this = cthis.element;
        	var $parent = $this.parent();
        	
        	this.options.cacheParentHeight = 0;
        	this.options.cacheSiblingsHeight = 0;
        	this._align($this,$parent,container, layoutConfig,true);
        	
        	$($parent).on("resize.parentfitlayout",function(){
        		 setTimeout(function(){
        			 that._align($this,$parent,container, layoutConfig,false);
        		 }, 5);
			});
        	$.each($($this).siblings(), function(i, obj){
        		if($(obj).css("display")!="none" && $(this).css("position")!="fixed" && $(this).css("position")!="absolute"){
        			setTimeout(function(){
	        			$(obj).on("resize.siblingsfitlayout",function(){
	    					that._align($this,$parent,container, layoutConfig,false);
	        			});
        			}, 5);
        		}
        	});
			//窗口纵向变化未改变body的大小，所以未触发$($parent).on("resize.parentfitlayout"）事件
			//有两种处理方法：
			//1.监听document的height变化，改变body的高度，从而触发$($parent).on("resize.parentfitlayout"）事件
			//2.若fit布局元素的父元素是body，则将body的height设为100%；
			this.options.cacheWindowHeight = jazz.getWindowHeight();
//			$(document).on("resize",function(){
//				jazz.log("========== window resize =========");
//				if($($parent)[0].nodeName=="BODY"){
//					var winHeight = jazz.getWindowHeight();
//					if(that.options.cacheWindowHeight!=winHeight){
//						that.options.cacheWindowHeight=winHeight;
//						$(document.body).height(winHeight+"px");
//					}
//					jazz.log("***********winHeight****"+winHeight+"===docheight=="+$(document).height()+"===body height=="+$(document.body).height());
//				}
//			});
        },

		/**
         * @desc 设置布局
         * @param {cthis} 当前布局容器对象
         * @param {config} 组件布局配置对象
		 * @throws {container} 所在布局容器的对象
		 * @private
         */ 
        
        _align: function($this,$parent,container, layoutConfig,isTriggerResizeEvent){
        	
        	var elementHeights=0;var eleights=0;
        	if($($this).siblings().size()>0){
        		$($this).siblings().each(function(){
        			//fixed 和 absolute定位脱离文档流不予计算
        			//jazz.log("===sub element id=="+$(this).attr("id")+"===position=="+$(this).css("position")+"===width=="+$(this).outerHeight(true));
        			if($(this).css("display")!="none"&&$(this).css("position")!="fixed"&&$(this).css("position")!="absolute"){
        				elementHeights += $(this).outerHeight(true);
        			}
	        	});
        	}
        	var parentHeight =0;
        	if($($parent)[0].nodeName=="BODY"){
        		var winHeight = jazz.getWindowHeight();
        		parentHeight = winHeight-($(document.body).outerHeight(true)-$(document.body).height());
        		jazz.log("====winHeight=="+winHeight+"===body height=="+$(document.body).height());
        	}else{
        		parentHeight = $($parent).height();
        	}
        	//jazz.log("==this.options.cacheParentHeight=="+this.options.cacheParentHeight+"==this.options.cacheSiblingsHeight=="+this.options.cacheSiblingsHeight+"===$($parent).height()=="+$($parent).height()+"==parentHeight==="+parentHeight);
        	if(this.options.cacheParentHeight != parentHeight||this.options.cacheSiblingsHeight !=elementHeights){
        		this.options.cacheHeight = parentHeight;
        		this.options.cacheSiblingsHeight = elementHeights;
        		var panel_marginHeight = $($this).outerHeight(true)-$($this).height();
        		if(isTriggerResizeEvent){//只保证在初始化fit布局时候为容器及父元素设置overflow样式
        			if($($parent)[0].nodeName=="BODY"){
        				$($parent).css("height", "100%");
        			}
        			$($parent).css("overflow", "hidden");
        			$(container).css("overflow","auto");
        		}
        		$(container).height((parentHeight-elementHeights-panel_marginHeight));
        		
        		if(layoutConfig.fitCallback && ($.isFunction(layoutConfig.fitCallback))){
        			layoutConfig.fitCallback.call(this);
        		}
        	}
        }
    });
    
})(jQuery);(function($) {

	/**
	 * @version 0.5
	 * @name jazz.borderlayout
	 * @description 上中下左右可拖拽大小布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 * @requires
	 * @example $('#panel_id').borderlayout();
	 */
    $.widget("jazz.borderlayout", $.jazz.containerlayout, {

    	/** @lends jazz.borderlayout */    
    	options: {
    		$this: null,
    		container: null,
    		rowelement: null,
    		columnelement: null
    	},
    	
    	reloadLayout: function(layoutConfig){
    		var el = this.element;
    		this.layout(el.$this, el.container, layoutConfig);
    	},
    	
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutConfig} configLayout配置信息
         */
        layout: function(cthis, container, layoutConfig) {
        	if(cthis.options.border){
        		container.addClass("jazz-panel-border");
        	}
        	var el = this.element; 
        	el.$this = cthis;
        	el.container = container;
        	
        	this.layoutConfig = layoutConfig;
        	var rowobject = layoutConfig.rowobject;
        	if(!!rowobject){
        		this.calculateRow(cthis,rowobject,layoutConfig);
        	}
        	var columnobject = layoutConfig.columnobject;
        	if(!!columnobject){
        		this.rowid = columnobject.attr("id");
        		if(!!this.options.columnelement){
        			columnobject = this.options.columnelement;
        		}            		
        		columnobject = columnobject.children(".jazz-border-row-inner");
        		jazz.log(columnobject.html());
        		this.calculateColumn(cthis,columnobject,layoutConfig);
        	}
        	
        },
        
        /**
         * @desc 计算行
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutConfig} configLayout配置信息
         */
        calculateRow: function (cthis,container,layoutConfig){
        	this.rowheights = layoutConfig.rowheight;
        	if(!this.options.rowelement){
        		this.options.rowelement = container.clone(true);
        	}else{
        		this.element.empty();
        		this.element.html(this.options.rowelement.html());
    			this.options.columnelement = $("#"+this.rowid);
        	}
        	var element = container.children();        	//行布局的列元素对象
        	var rows = this.rowheights.length;
        	
        	//列布局时，禁止容器出现滚动条
        	if(jazz.isIE(6) || jazz.isIE(7)){
        		container.css('overflow', 'hidden');
        	}
        	
        	this.fixRowHeight = 0; //计算全部固定列的宽度
        	this.noFixRow = [];
        	var border = layoutConfig.border; //是否显示边框
        	var $that = this;
        	$(this.rowheights).each(function(i,data){
        		var tempObject = $(element[i]);
    			//去除margin
    			tempObject.css('margin', '0px');
        		//添加边框
        		if(border){
        			tempObject.addClass('jazz-layout-border');
	    		}else{
	    			tempObject.addClass('jazz-layout-no-border');
	    		}    
        		
        		if(!tempObject.hasClass("jazz-border-row-outter")){
        			tempObject.addClass("jazz-border-row-outter");
            		if(i==0){
            			tempObject.css("padding-top","0px");
            		}else if(i==rows-1){
            			tempObject.css("padding-bottom","0px");
            		}
            		var temphtml = $("<div class='jazz-border-row-inner'></div>").wrapInner($(tempObject.contents()));
            		tempObject.empty();
            		tempObject.html(temphtml);
            		if(i!=rows-1){
            			var splitdiv = $("<div id='"+i+"' class='jazz-border-row-split'></div>").appendTo(tempObject);
            			var start = 0 ,end = 0;
            			splitdiv.draggable({ 
            				addClasses: false,
            				containment: tempObject.parent(), 
            				start: function(event,ui) { 
            					start = ui.position.top ;
            				},
            				stop: function(event,ui) { 
            					var id = $(this).attr("id");
            					tempObject = $(element[id]);
            					end = ui.position.top ;
            					var cha = end - start;
            					var prevObject = tempObject.prev();
        						var nextObject = tempObject.next();
            					if(cha < 0){
            						if(prevObject.length>0){
            							tempObject.height(tempObject.height() + end - start );
            							nextObject.height(nextObject.height() + start - end );
            						}else{
            							tempObject.height(end);
            							nextObject.height(nextObject.height() + start - end);
            						}
            					}else{
            						tempObject.height(tempObject.height() + end - start );
        							nextObject.height(nextObject.height() - (end - start) );
            					}
    							var nexttop = nextObject.height()+2;
    							nextObject.children(".jazz-border-row-split").css("left","0px");
    							nextObject.children(".jazz-border-row-split").css("top",nexttop);
            				} 
            			});
            			
            		}
        		}
        		if(data.indexOf('px')!=-1){
        			tempObject.outerHeight(data);
        			$that.fixRowHeight = $that.fixRowHeight + tempObject.outerHeight();     			
        		}else{
        			//非固定列
        			$that.noFixRow.push(tempObject);
        		}
        	});
        	//jazz.log('固定列高度======='+this.fixRowHeight);

           	//容器的总高度
        	if(cthis.options.height){
        		container.css("height",cthis.options.height);
        	}
        	if(cthis.options.width){
        		container.css("width",cthis.options.width);
        	}
        	var containerHeight = Math.min(container.height(), container.get(0).clientHeight);
        	this.calculateHeight(containerHeight, element, rows);
        	//jazz.log('容器高度======='+containerHeight);
        	
        	var $this = this;
        	//监听父容器大小改变
        	cthis.element.off('resize.borderlayout').on('resize.borderlayout', function(){
        		var containerHeight = Math.min(container.height(), container.get(0).clientHeight);
        		if($this.options.cacheHeight != containerHeight){
        			$this.calculateHeight(containerHeight, element, rows);
        		}
        	});
        },
        
		/**
         * @desc 计算高度
         * @param {containerHeight} 容器高度
         * @param {element} 子容器集合
         * @param {cols} 子容器个数
         */    
        calculateHeight: function(containerHeight, element, rows){
        	var $this = this;
        	//其他部分总宽度
        	var otherHeight = containerHeight - this.fixRowHeight;
        	
        	//jazz.log('******其他区域高度*******'+otherHeight);
        	
        	//百分比总宽度
        	this.percentHeight = 0;
        	for(var i=0; i<rows; i++){
        		var rowheight = this.rowheights[i];
        		var tempObject = $(element[i]);
        		//判断是否为百分比列宽
        		if(!!rowheight && rowheight.indexOf('%')!= -1){
        			var n = parseInt(rowheight.substring(0, rowheight.indexOf('%')));
        			rowheight = (n/100)*otherHeight;
        			//设置百分比列的宽度
        			tempObject.outerHeight(rowheight);
        			this.percentHeight = this.percentHeight + tempObject.outerHeight();
        		}
        		//自适应宽度列
        		if($.trim(rowheight) == '*'){
        			this.emptyRow = tempObject;
        		}
        	}
        	
        	//jazz.log('********百分比列高度********'+this.percentHeight);
        	
        	//去除固定列、百分比列高度后，剩余高度
        	this.autoHeight = containerHeight - this.fixRowHeight - this.percentHeight;
        	
        	//jazz.log('********自动高度列*********'+this.autoHeight);
        	
        	//判断未指定宽度的列的数量，仅有1列时，计算这列的宽度
        	if(!!this.emptyRow){
	    			this.emptyRow.outerHeight(this.autoHeight - 1);
        	}
    		
    		//缓存容器高度,用于监听时进行比较窗体高度是否发生改变
        	this.options.cacheHeight = containerHeight;
        },
        
        /**
         * @desc 计算宽
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutConfig} configLayout配置信息
         */
        calculateColumn: function (cthis,container,layoutConfig){
        	this.colwidths = layoutConfig.columnwidth;
        	
        	var element = container.children();        	//列布局的列元素对象
        	var cols = this.colwidths.length;
        	
        	//列布局时，禁止容器出现滚动条
        	if(jazz.isIE(6) || jazz.isIE(7)){
        		container.css('overflow', 'hidden');
        	}
        	
        	this.fixColumnWidth = 0; //计算全部固定列的宽度
        	this.noFixColumn = [];
        	var border = layoutConfig.border; //是否显示边框
        	var $that = this;
        	$(this.colwidths).each(function(i,data){	
    			var tempObject = $(element[i]); 
    			//去除margin
    			tempObject.css('margin', '0px');
        		tempObject.addClass('jazz-column-element');
        		//添加边框
        		if(border){
        			tempObject.addClass('jazz-layout-border');
	    		}else{
	    			tempObject.addClass('jazz-layout-no-border');
	    		}    		
        		
        		if(!tempObject.hasClass("jazz-border-column-outter")){
        			tempObject.addClass("jazz-border-column-outter");
            		if(i==0){
            			tempObject.css("padding-left","0px");
            		}else if(i==cols-1){
            			tempObject.css("padding-right","0px");
            		}
            		var temphtml = $("<div class='jazz-border-column-inner'></div>").wrapInner($(tempObject.contents()));
            		tempObject.empty();
            		tempObject.html(temphtml);
            		if(i!=cols-1){
            			var splitdiv = $("<div id='"+i+"' class='jazz-border-column-split'></div>").appendTo(tempObject);
            			var start = 0 ,end = 0;
            			splitdiv.draggable({ 
            				addClasses: false,
            				containment: tempObject.parent(), 
            				start: function(event,ui) { 
            					start = ui.position.left ;
            				},
            				stop: function(event,ui) { 
            					var id = $(this).attr("id");
            					tempObject = $(element[id]);
            					end = ui.position.left ;
            					var cha = end - start;
            					var prevObject = tempObject.prev();
        						var nextObject = tempObject.next();
            					if(cha < 0){
            						if(prevObject.length>0){
            							tempObject.width(tempObject.width() + end - start );
            							nextObject.width(nextObject.width() + start - end );
            						}else{
            							tempObject.width(end);
            							nextObject.width(nextObject.width() + start - end);
            						}
            					}else{
            						tempObject.width(tempObject.width() + end - start );
        							nextObject.width(nextObject.width() - (end - start) );
            					}
    							var nexttop = nextObject.width()+2;
    							nextObject.children(".jazz-border-column-split").css("left",nexttop);
    							nextObject.children(".jazz-border-column-split").css("top","0px");
            				} 
            			});
            			
            		}
        		}
        		
        		if(data.indexOf('px') != -1){
        			tempObject.outerWidth(data);
        			$that.fixColumnWidth = $that.fixColumnWidth + tempObject.outerWidth();     			
        		}else{
        			//非固定列
        			$that.noFixColumn.push(tempObject);
        		}
        	});

        	jazz.log('*********固定列宽度*********'+this.fixColumnWidth);
           	//容器的总宽度
        	var containerWidth = Math.min(container.width(), container.get(0).clientWidth);
        	
        	this.calculateWidth(containerWidth, element, cols);
        
        	//监听父容器大小改变
        	var $this = this;
        	
        	cthis.element.off('resize.borderlayout').on('resize.borderlayout', function(){
            	var containerWidth = Math.min(container.width(), container.get(0).clientWidth);
        		if($this.options.cacheWidth != containerWidth){
        			$this.calculateWidth(containerWidth, element, cols);
        		}
        	});
        },
        
        /**
         * @desc 计算宽度
         * @param {containerWidth} 容器宽度
         * @param {element} 容器宽度
         * @param {cols} 容器宽度
         */    
        calculateWidth: function(containerWidth, element, cols){
        	//其他部分总宽度
        	var otherWidth = containerWidth - this.fixColumnWidth;
        	
        	jazz.log('******其他区域宽度*******'+otherWidth);
        	
        	//百分比总宽度
        	this.percentWidth = 0;
        	for(var i=0; i<cols; i++){
        		var colwidth = this.colwidths[i];
        		var tempObject = $(element[i]);
        		//判断是否为百分比列宽
        		if(!!colwidth && colwidth.indexOf('%') != -1){
        			var n = parseInt(colwidth.substring(0, colwidth.indexOf('%')));
        			colwidth = (n/100)*otherWidth;
        			tempObject.outerWidth(colwidth);
        			
        			this.percentWidth = this.percentWidth + tempObject.outerWidth();
        		}
        		//自适应宽度列
        		if($.trim(colwidth) == '*'){
        			this.emptyColumn = tempObject;
        		}
        	}
        	
        	jazz.log('********百分比列宽度********'+this.percentWidth);
        	
        	//去除固定列、百分比列宽度后，剩余宽度
        	this.autoWidth = containerWidth - this.fixColumnWidth - this.percentWidth;
        	
        	jazz.log('********自动宽度列*********'+this.autoWidth);
        	
        	//判断未指定宽度的列的数量，仅有1列时，计算这列的宽度
        	if(!!this.emptyColumn){
	    		this.emptyColumn.outerWidth(this.autoWidth - 1);
        	}
    		
    		//缓存容器宽度,用于监听时进行比较窗体宽度是否发生改变
        	this.options.cacheWidth = containerWidth;
        }

    });

})(jQuery);(function($) {

	/**
	 * @version 0.5
	 * @name jazz.layout
	 * @description 调用布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 * @requires
	 * @example $('#panel_id').layout();
	 */	
    $.widget("jazz.layout", $.jazz.containerlayout, {

    	/** @lends jazz.layout */    
    	options: {
    		layout: "auto"
    	},
    	
    	_init: function(){
    		this.layout(this, this.element);
    	},
        
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         */
        layout: function(cthis, container) {
        	var options = this.options;
            this.obj = container[this.options.config[options.layout]]();
            container.data(this.options.config[options.layout]).layout(cthis, container, this.options.layoutconfig);
        },
        
        reloadLayout: function(layoutConfig){
        	try{
        		this.obj.data(this.options.config[this.options.layout]).reloadLayout(layoutConfig);
        	}catch(e){
        		jazz.error("错误的调用布局的reloadLayout接口");
        	}
        }        

    });

})(jQuery); (function($){
/**
 * @version 1.0
 * @name jazz.component
 * @description 组件的基类，component下所有子类均按照统一组件生命周期执行动作，既创建、渲染和销毁，并具有隐藏./显示、启用/禁用的基本行为特性。
 * @constructor
 */
	$.widget('jazz.component', {
	    options: /** @lends jazz.component# */ {
			
		   /**
			*@type String
			*@desc 组件名称
			*@default ''
			*/
			name: ''		
		},

		
		/** @lends jazz.component */
		/**
		 * @desc 创建组件
		 * @private
		 */
		_create: function(){
			this._attrToOptions();

        	this.element.attr('vtype', this.options.vtype);
        	this.element.attr('name', this.options.name);
		},
		
		/**
		 * @desc 初始化
		 * @private
		 */
		_init: function(){
			
		},	
				
		/**
		 * @desc 将attr属性转换成options
		 * @private
		 */			
		_attrToOptions: function(){
			var c_a = jazz.attributeToOptions(this.element.get(0));
			for(var p in c_a){
				if(c_a[p]==="true"){
					c_a[p]=true;
				}else if(c_a[p]==="false"){
					c_a[p]=false;
				}
			}
			$.extend(this.options, c_a);
		},
		
		/**
		 * @desc 合并事件的处理this._trigger()、<div click="_click">、<div click="_click()">形式。
		 * @param {eventName} 所要触发的事件名称
		 * @param {event} 原事件中的event
		 * @param {data} 抛出的对象
		 * @private
		 */
		_event: function(eventName, event, data){
			var _ename = eventName;
			var callback = this.options[_ename];
			if(!$.isFunction(callback)){
				var reg=/\(/;
				if(reg.test(callback)){
					callback = callback.split("(")[0] || null;
					if(callback == null){
						return false;
					}
				}
				callback = eval(callback+"");
			}
			
			data = data || {}; 
			event = $.Event( event );
			event.type = ( eventName === this.options.vtype ?
					eventName :
					this.options.vtype + eventName ).toLowerCase();
			event.target = this.element[0];

			orig = event.originalEvent;
			if ( orig ) {
				for ( prop in orig ) {
					if ( !( prop in event ) ) {
						event[ prop ] = orig[ prop ];
					}
				}
			}				

			this.element.trigger(event, data);
			return !($.isFunction(callback) &&
				     callback.apply(this.element[0], [event].concat(data)) === false || event.isDefaultPrevented());
		},

//		createVtypeTree: function(){
//			var element = this.element, options = this.options;
//			// 查找元素的parent节点
//			var parent = element.parents("div[vtype]:first");
//			// 如果没找到，设置为BODY
//			if( parent.size() == 0 ){
//				parent = $("BODY");
//			}
//			
//			// 设置父节点的data中的child
//			var parentData = parent.data("vtypetree") || {parent:"", child:{}};
//			var childName = options["name"];
//			parentData.child[childName] = element;
//			parent.data("vtypetree", parentData);
//			
//			// 设置当前节点中的parent的值和vType的值
//			var nodeData = element.data("vtypetree") || {parent:"", child:{}};
//			nodeData["parent"] = parent;
//			nodeData["vtype"] = options["vtype"];
//			element.data("vtypetree", nodeData);
//		},
		
		/**
		 * @desc 对options中的items、content进行预处理
		 * @private
		 */
		_transformOptions: function(){
			if($.isArray(this.options.items)){
				var items = this.options.items;
				var vtype = this.options.vtype;
				for(var i=0, len=items.length; i<len; i++){
					var item = items[i];
					if(vtype=="panel" || vtype=="window" || vtype=="gridpanel"){
						jazz.widget(item,this.content);
					}else{
						var obj = jazz.widget(item);
						this.element.append(obj);
					}
				}
				delete this.options.items;
			}
		},
		
		/**
         * @desc 组件ID 由组件内部调用  参数描述： comp: componnet简写 j: jazz name: this.options.name  number: 编号自动+1
		 * @return ID编号
		 * @private
         */			
		getCompId: function(){
			return 'comp_j_'+this.options.name+'_'+(++jazz.config.compNumber);
		}
		
	});

	
})(jQuery);
 
(function($){
/** 
 * @version 0.5
 * @name jazz.boxComponent
 * @description 使用矩形容器组件的基类，提供自适应高度、宽度调节的功能，具备大小调节和定位的能力。
 * @constructor
 * @extends jazz.component
 * @requires
 * @example $('#abc').boxComponent({top:'120px', left:'35px'});
 */
	$.widget('jazz.boxComponent', $.jazz.component, {
	    options: /** @lends jazz.boxComponent# */ {
			/**
			 *@type number
			 *@desc 组件相对页面left坐标
			 *@default ''
			 */
			left: '',
			
			/**
			 *@type number
			 *@desc 组件相对页面top坐标
			 *@default ''
			 */
			top: '',			
			
			/**
			 *@type number
			 *@desc 鼠标相对于当前组件左边位置距离
			 *@default ''
			 */
			x: '',
			
			/**
			 *@type number
			 *@desc 鼠标相对于当前组件上边位置距离
			 *@default ''
			 */
			y: '',		

			/**
			 *@type number
			 *@desc 鼠标相对于页面左边位置距离
			 *@default ''
			 */
			pagex: '',
			
			/**
			 *@type number
			 *@desc 鼠标相对于页面上边位置距离
			 *@default ''
			 */
			pagey: '',			

			/**
			 *@type number
			 *@desc 组件的高度
			 *@default -1
			 */
			height: -1,
			
			/**
			 *@type number
			 *@desc 组件的宽度
			 *@default -1
			 */
			width: -1,			
			
			
			// callbacks
			
			/**
			 *@desc 当组件调整大小时触发
			 *@param {event} 事件
			 *@param {ui} 
			 *@event
			 *@example
			 */
			resize: null,
			
			/**
			 *@desc 当前组件被移动后触发
			 *@param {event} 事件
			 *@param {ui} 
			 *@event
			 *@example
			 */
			move: null			
		},
		
		/** @lends jazz.boxComponent */
		/**
		 * @desc 创建组件
		 * @private
		 */
		_create: function(){
			this._super();
		},
		
		/**
		 * @desc 初始化
		 * @private
		 */
		_init: function(){
			this._super();
		},
		
		/**
         * @desc 为组件提供初始化，此方法触发resize事件
		 * @param {width} number 宽度值
		 * @param {height} number 高度值
         * @return
		 * @throws
		 * @example
         */
		setSize: function(width, height){
		},
		
		/**
         * @desc 设置组件的宽度，此方法触发resize事件
		 * @param {width} number 宽度值
		 * @example
         */		
		setWidth: function(w){
		    var _w = 0;
		    if(jazz.isNumber(w)){
				_w = $('#'+this.options.id).width(w);
			}
			if(typeof(w)=='undefined'){
				_w = $('#'+this.options.id).outerWidth();
			}
			return w;		
		},
		
		/**
         * @desc 设置组件的高度，此方法触发resize事件
		 * @param {height} number 高度值
         * @return
		 * @throws
		 * @example
         */			
		setHeight: function(height){
		},
		
		/**
         * @desc 返回元素的大小 {width:12, height 21}
         * @return Object
		 * @throws
		 * @example
         */			
		getSize: function(){
		},
		
		/**
         * @desc 返回组件元素的高度
		 * @param {n} String 组件id/class
         * @return number
		 * @throws
		 * @example
         */			
		getHeight: function(n){
			var h = 0;
		    if(typeof(n)=='string'){
				if($.trim(n).substring(0,1)=='.'){
					h = $('.'+n).outerHeight();
				}else if($.trim(n)==''){
					h = $('#'+this.options.id).outerHeight();
				}else{
					h = $('#'+n).outerHeight();
				}
			}
			if(typeof(n)=='undefined'){
				h = $('#'+this.options.id).outerHeight();
			}			
			return h;			
		},

		/**
         * @desc 返回组件元素的宽度
		 * @param {n} String 组件id/class
         * @return number
		 * @throws
		 * @example
         */			
		getWidth: function(n){
		    var w = 0;
		    if(typeof(n)=='string'){   
				if($.trim(n).substring(0,1)=='.'){
					w = $('.'+n).outerWidth();  
				}else if($.trim(n)==''){
					w = $('#'+this.options.id).outerWidth();
				}else{
				    w = $('#'+n).outerWidth();
				}
			}
			if(typeof(n)=='undefined'){
				w = $('#'+this.options.id).outerWidth();
			}
			return w;			
		},			
		
		/**
         * @desc 返回当前组件所在元素的尺寸大小，包括其margin占据的空间位置
         * @return Object {width: 60, height: 75}
		 * @throws
		 * @example
         */			
		getOuterSize: function(){
		},			
		
		/**
         * @desc 返回当前鼠标相对于组件位置                                
         * @return {x: 30, y: 25}
		 * @throws
		 * @example
         */
		getXY: function(){
		},

		/**
         * @desc 返回当前鼠标相对页面位置                      
         * @return {pagex: 331, pagey: 225}
		 * @throws
		 * @example
         */
		getPageXY: function(){
		},
		
		/**
         * @desc 设置组件相对于父容器的位置
         * @param {left} number 距离左边位置
         * @param {top}  number 距离上边位置		 
         * @return
		 * @throws
		 * @example
         */
		setPosition: function(left, top){
		},
		
		/**
         * @desc 获取组件相对父容器的位置
         * @param {n} String 组件id/String		 
         * @return {left 435, top: 124} Object
		 * @throws
		 * @example
         */
		getPosition: function(n){
			var p = {top:0, left:0};
		    if(typeof(n)=='string'){
			    if($.trim(n).substring(0,1)=='.'){
					p.top = $('.'+n).offset().top;
					p.left = $('.'+n).offset().left;				
				}else if($.trim(n)==''){
					p.top = $('#'+this.options.id).offset().top;
					p.left = $('#'+this.options.id).offset().left;				
				}else{
					p.top = $('#'+n).offset().top;
					p.left = $('#'+n).offset().left;				
				}
			}
			if(typeof(n)=='undefined'){
				p.top = $('#'+this.options.id).offset().top;
				p.left = $('#'+this.options.id).offset().left;	
			}
			
			//jazz.log('boxComponent getPosition ');
			//this._trigger('resize', null, {name:'1444'});
			return p;
		}

		/**
         * @desc 获取当前页面的高度		 
         * @return Number
		 * @throws
		 * @example this.pageHeight();
         */
       ,pageHeight: function(){
			if($.browser.msie){ 
				return document.compatMode == "CSS1Compat"? document.documentElement.clientHeight : document.body.clientHeight; 
			}else{ 
				return self.innerHeight;
			} 
		},

		/**
         * @desc 获取当前页面的宽度		 
         * @return Number
		 * @throws
		 * @example this.pageWidth();
         */		
       pageWidth: function(){ 
			if($.browser.msie){ 
				return document.compatMode == "CSS1Compat"? document.documentElement.clientWidth : document.body.clientWidth; 
			}else{ 
				return self.innerWidth; 
			} 
		},
		
		/**
         * @desc 计算页面大小
         */
		getPageSize: function () {
		    var xScroll, yScroll;
		    if (window.innerHeight && window.scrollMaxY) {
		        xScroll = window.innerWidth + window.scrollMaxX;
		        yScroll = window.innerHeight + window.scrollMaxY;
		    } else {
		        if (document.body.scrollHeight > document.body.offsetHeight) { // all but Explorer Mac    
		            xScroll = document.body.scrollWidth;
		            yScroll = document.body.scrollHeight;
		        } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari    
		            xScroll = document.body.offsetWidth;
		            yScroll = document.body.offsetHeight;
		        }
		    }
		    var windowWidth, windowHeight;
		    if (self.innerHeight) { // all except Explorer    
		        if (document.documentElement.clientWidth) {
		            windowWidth = document.documentElement.clientWidth;
		        } else {
		            windowWidth = self.innerWidth;
		        }
		        windowHeight = self.innerHeight;
		    } else {
		        if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode    
		            windowWidth = document.documentElement.clientWidth;
		            windowHeight = document.documentElement.clientHeight;
		        } else {
		            if (document.body) { // other Explorers    
		                windowWidth = document.body.clientWidth;
		                windowHeight = document.body.clientHeight;
		            }
		        }
		    }       
		    // for small pages with total height less then height of the viewport    
		    if (yScroll < windowHeight) {
		        pageHeight = windowHeight;
		    } else {
		        pageHeight = yScroll;
		    }   
		    // for small pages with total width less then width of the viewport    
		    if (xScroll < windowWidth) {
		        pageWidth = xScroll;
		    } else {
		        pageWidth = windowWidth;
		    }
		    
		    return {
		    	'pageWidth': pageWidth, 
		    	'pageHeight': pageHeight, 
		    	'windowWidth': windowWidth, 
		    	'windowHeight': windowHeight 
		    };
		}		
		
	});

	
})(jQuery);
(function($){
/**
 * @version 0.5
 * @name jazz.container
 * @description 使用矩形容器组件的基类，提供自适应高度、宽度调节的功能，具备大小调节和定位的能力。
 * @constructor
 * @extends jazz.boxComponent
 * @requires
 */
	$.widget('jazz.container', $.jazz.boxComponent, {
	    options: /** @lends jazz.container# */ {
			/**
			 *@type String
			 *@desc 容器指定的布局类型
			 *@default 'auto'
			 */
			layout: 'auto',
			
			/**
			 *@type Object
			 *@desc 容器布局对象
			 *@default null
			 */
			containerlayout: null,
			
			/**
			 *@type Object
			 *@desc 布局的配置参数
			 *@default '{}'
			 */
			layoutconfig: {},	
			
			/**
			 *@type Array/Object
			 *@desc 存放子容器
			 *@default null
			 */
			items: null,
			
			/**
			 *@type String
			 *@desc 容器默认类型，如不指定则使用'panel'
			 *@default 'panel'
			 */
			defaulttype: 'panel'
			
			// callbacks
		    
		},
		
		/** @lends jazz.container */
		/**
		 * @desc 创建组件
		 * @private
		 */
		_create: function(){
			this._super();
		},
		
		/**
		 * @desc 初始化
		 * @private
		 */
		_init: function(){
			this._super();
		},	
		
		/**
         * @desc 设置组件的布局 当有新组件加入或组件改变大小/位置时，就需要执行此方法
         * @param {containerObject} 需要布局的容器对象
		 * @throws
		 * @example $('#container').container('doLayout', containerObject);
         */			
        doLayout: function(containerObject){
        	//初始化调用布局类
        	this.options.containerlayout = containerObject.containerlayout();
            //获取布局对象
        	containerObject.containerlayout('containerLayout', this, containerObject);
        },
        
        reloadLayout: function(layoutConfig){
        	try{
        		this.options.containerlayout.data(this.options.containerlayout
        				.data('containerlayout').options.config[this.options.layout]).reloadLayout(layoutConfig);
        	}catch(e){
        		$('<div></div>').error({detail: '错误的调用布局的reloadLayout接口'});
        	}
        },        
        
        previousPage: function(n){
        	if(this.options.layout==='card'){
        		var i = this.options.containerlayout.data(this.options.containerlayout
        				.data('containerlayout').options.config.card).previousPage(n);
        		return i;
        	}else{
        		$('<div></div>').error({detail: '错误的调用previousPage接口，当前非layout:"card";布局'});
        	}
        },
		
        nextPage: function(n){
        	if(this.options.layout==='card'){
        		var i = this.options.containerlayout.data(this.options.containerlayout
        				.data('containerlayout').options.config.card).nextPage(n);
        		return i;
        	}else{
        		$('<div></div>').error({detail: '错误的调用previousPage接口，当前非layout:"card";布局'});
        	}
        }
		

	});

})(jQuery);(function($) {
/** 
 * @version 0.5
 * @name jazz.panel
 * @description 是面板容器，是一特定的功能和结构化组件。
 * @constructor
 * @extends jazz.container
 */
    $.widget("jazz.panel", $.jazz.container, {
       
        options: /** @lends jazz.panel# */ {
        	
        	/**
        	 *@desc 组件类型
        	 */
        	vtype: 'panel',
        	
    		/**
			 *@type Boolean
			 *@desc 是否显示标题栏   true显示 false不显示
			 *@default true
			 */
    		titledisplay: false,        	
        	
			/**
			 *@type String
			 *@desc 标题 
			 *@default ''
			 */          	
        	title: '',
        	
    		/**
			 *@type String
			 *@desc 标题名称的显示位置 left center right
			 *@default 'left'
			 */
    		titlealign: 'left',
    		
    		/**
			 *@type String
			 *@desc 标题前的图片 例如：../../images/title.png
			 *@default null
			 */
    		titleicon: null,
    		
            /**
			 *@type Boolean
			 *@desc 控制标题框上操作按钮的位置   right 右侧  left 左侧
			 *@default false
			 */
    		titlebuttonalign: 'right',
    		
    		/**
			 *@type String
			 *@desc title标题的字体样式
			 *@default ''
			 */
    		titlefontclass: '',
    		
            /**
			 *@type Object
			 *@desc 自定义操作
			 *@default null
			 *@example 	
			 *<br>{       	
			 *<br>	id: 't_1',
	         *<br>	align: 'left',
	         *<br>	icon: 'test.png',
	         *<br>	click: function(e){
	         *<br>	}
	         *<br>}
			 */  
            customtitlebutton: null,
            
			/**
			 *@type array
			 *@desc 按钮集合，[{},{},{}], {}对应button的options.
			 *@default null
			 */            
            buttons: null,
        		
			/**
			 *@type boolean
			 *@desc 是否显示可触发窗体大小改变的按钮
			 *@default false
			 */
            toggleable: false,

//			/**
//			 *@type string
//			 *@desc 通过使用滑动效果，隐藏被选元素  slow normal fast 1500
//			 *@default normal
//			 */            
//            toggleduration: 'normal',
            
			/**
			 *@type string
			 *@desc 控制折叠方向   vertical   horizontal   未定入API  需要完善
			 *@default vertical
			 *@private
			 */
            toggleorientation : 'vertical',
            
			/**
			 *@type boolean
			 *@desc 触发窗体大小改变，true 折叠， false 非折叠，
			 *@default false
			 */              
            collapsable: false,
            
			/**
			 *@type boolean
			 *@desc 显示关闭面板的按钮
			 *@default false
			 */               
            closable: false,
            
			/**
			 *@type boolean
			 *@desc 判断是否显示边框
			 *@default true
			 */               
            showborder: true,
            
            /**
			 *@type string
			 *@desc 背景颜色
			 *@default white
			 */    
            bgcolor: null,
                    
			/**
			 *@type string
			 *@desc 使用淡出效果来隐藏被选元素 slow normal fast 1500
			 *@default normal
			 */
            closeduration: 'normal',
            
            /**
			 *@type String
			 *@desc 内容区域URL
			 *@default null
			 */
            frameurl: null, 
            
            /**
             * @type String
             * @desc 内容区域的html内容
             * @default null
             */
            content: null,

            
            //callback
			/**
			 *@desc 关闭窗体前触发
			 *@param {event} 事件		 
			 *@event
			 *@example
			 *初始化：
			 *<br/>$("#div_id").panel({beforeclose: function( event ){  <br/>} <br/>});
			 */	           
            beforeclose: null,
            
			/**
			 *@desc 关闭窗体后触发
			 *@param {event} 事件		 
			 *@event
			 *@example
			 *初始化：
			 *<br/>$("#div_id").panel({afterclose: function( event ){  <br/>} <br/>});
			 */	            
            afterclose: null,
            
			/**
			 *@desc 窗体收起前触发
			 *@param {event} 事件		 
			 *@event
			 *@example
			 *初始化：
			 *<br/>$("#div_id").panel({beforecollapse: function( event ){  <br/>} <br/>});
			 */	            
            beforecollapse: null,
            
			/**
			 *@desc 窗体收起后触发
			 *@param {event} 事件		 
			 *@event
			 *@example
			 *初始化：
			 *<br/>$("#div_id").panel({aftercollapse: function( event ){  <br/>} <br/>});
			 */	            
            aftercollapse: null,
            
			/**
			 *@desc 窗体展开前触发
			 *@param {event} 事件		 
			 *@event
			 *@example
			 *初始化：
			 *<br/>$("#div_id").panel({beforeexpand: function( event ){  <br/>} <br/>});
			 */	              
            beforeexpand: null,
            
			/**
			 *@desc 窗体展开后触发
			 *@param {event} 事件		 
			 *@event
			 *@example
			 *初始化：
			 *<br/>$("#div_id").panel({afterexpand: function( event ){  <br/>} <br/>});
			 */            
            afterexpand: null
            
        },
        
        /** @lends jazz.panel */        
        
		/**
         * @desc 创建组件
		 * @private
         */        
        _create: function() {
        	this._super();
        	
        	this.compId = this.getCompId();
        	
        	var title = this.element.attr('title');
        	if(title){
        		this.element.removeAttr('title');
        	}
        	
        	var el = this.element;
        	el.css('overflow', 'hidden').addClass('jazz-panel');
        	
        	//创建基本组件
       	    this._commonElement(this.compId, el);
       	    
        },
        
        /**
         * @desc 初始化
         * @private
         */
        _init: function(){
        	
        	var el = this.element;
        	//是否显示边框  边框未渲染任何子元素时加载
        	this._showBorder(el, this.options.showborder);
        	
			//标题栏信息
			this._titleInfo(el);
			
            this.title = $('#'+this.compId+'_title');
            
        	//定义标题栏操作按钮容器
        	this.handlerIcons = this._customButton(this.titlebarInner);
        	
        	//关闭
            this._closable();
            
            //自定义标题栏按钮
            this._customButtonhandler();  
            
            //是否可收起
            this._toggleable();       
            
            var titleHeight = 0;
            if(!this.options.titledisplay){  
            	this.titlebar.css('display', 'none');
            }else{
            	this.titlebar.css('display', '');
            	titleHeight = this.titlebar.outerHeight();
            }

            //是否显示颜色
            this._bgcolor(this.content);  
            
            //初始化内容区域, 并创建 this.content
            this._contentInfo(el);
            								
			//初始化容器大小
			this._compSize(el, titleHeight);
			
            this.panelelementHeight = this.element.height();
            this.panelcontentHeight = this.content.height();				
			
            //事件
            this._panelbindEvents();
            
        	//设置加载容器布局
            this.doLayout(this.content);

            var contents = this.options.content;
            if(contents != null && this.options.vtype != "gridpanel"){
            	this.content.parseComponent();
            }
        },
        
        /**
         * @desc 标题栏内容
         * @param {el}  titlebar对象
         * @private
         */
        _titleInfo: function(el){
            var title = this.options.title || "";
            //先移除
            this.titlebarInner.children().remove();
            
        	var titleicon = "";
        	if(this.options.titleicon){
        		titleicon = "style=\"background: url('"+this.options.titleicon+"') no-repeat; padding-left: 30px; \"";
        	}
        	
        	var tFontClass = 'jazz-panel-title-font';
        	if(this.options.vtype != 'panel'){
        		tFontClass += ' jazz-'+this.options.vtype+'-title-font';
        	}
        	if(this.options.titlefontclass){
        		tFontClass = this.options.titlefontclass;
        	}
	    	if(this.options.titlealign){
	        	this.titlebar.css({'text-align': this.options.titlealign});
	        }

	    	this.titlebarInner.append('<span id="'+this.compId+'_title" class="'+tFontClass+'" '+titleicon+' >' + title + '&nbsp;</span>');	
        },        
        
		/**
         * @desc 初始化容器大小
         * @private
         */
        _compSize: function(el, titleHeight){
        	var layout = this.options.layout;
        	if(layout != 'fit'){
	            var height = this.options.height;
	        	if(height && height != -1){
	        		if((height+"").indexOf("%") == -1){
	        			el.outerHeight(height);
	        		}
//	            	if(layout != 'row' && layout!='column'){
//	            		$(this.content).layout({layout:'fit'});
//	            	}else{
	            	    var h = 0;
	            	    if(this.toolbar && this.options.vtype!="gridpanel"){
	            	    	h = this.toolbar.outerHeight();
	            	    }
	            		this.content.outerHeight(el.height() - titleHeight - h);
//	            	}
	            }else{
	            	el.height('auto');
	            	this.content.height('auto');
	            }
	            
				if(this.options.frameurl != null){
					var h = this.content.height();
					this.frameObject.height(h);
					this.displayDiv.height(h);
					this.content.css({'overflow': 'hidden'});
				}   
        	}
            
        	var width = this.options.width;
            if(width && width != -1){
            	if((width+"").indexOf('%')==-1){
	            	el.outerWidth(width);
	            	this.content.outerWidth(el.width());
	            	if(this.options.titledisplay){
	            		this.titlebar.width('auto');
	            	}
            	}
            }
        }, 
        
        /**
         * @desc fit布局的回调函数
         * @param $this
         * @private
         */
        panelFitCallback: function($this){
        	var h = 0;
        	if($this.toolbar){
        		h = $this.toolbar.outerHeight();
        	}
        	if($this.options.titledisplay){
        		$this.content.outerHeight($this.element.height() - $this.titlebar.outerHeight() - h);
        	}else{
        		$this.content.outerHeight($this.element.height() - h);
        	}
        	if($this.options.frameurl){
        		$this.content.css({overflow: 'hidden'});
        		if($this.options.frameurl != null){
	        		var h = $this.content.height();
					$this.frameObject.outerHeight(h);
					$this.displayDiv.height(h);
        		}
        	}
        },

        /**
         * @desc 初始化内容区域
         * @param {el} toolbar对象
         * @private
         */
        _contentInfo: function(el) {
            if(this.options.frameurl != null){
            	this.content.children().remove();
            	var name = this.options.name;
            	if(name){
            		this.frameId = name;
            	}else{
            		this.frameId = 'frame_'+jazz.getRandom();            		
            	}
            	
        		this.frameObject = $('<iframe src="'+this.options.frameurl+'" name="'+this.frameId+'" id="' + this.frameId +'" frameBorder="0" style="width:100%; height:1px;"></iframe>');
	    		this.displayDiv = $('<div style="display:none; width:100%; height:1px; background:#FFFFFF;"></div>').appendTo(this.content);
	    		this.content.css({overflow: 'hidden'}).append(this.frameObject);
	    		this.pageParams = [];
	    		this.pageParams.push({url: this.frameObject.attr('src')});
        	}  	
        },
        
        /**
         * @desc 弹出框操作按钮
		 * @param {styleClass} 头样式
		 * @param {icon} 图片样式
		 * @param {id}
		 * @private
         */
        _renderTitleButton: function(styleClass, icon, id) {
        	var f = "float:"+this.options.titlebuttonalign;
        	var _id = id ? 'id="'+id+'"': '';
            var obj = $('<a '+_id+' class="jazz-panel-titlebar-icon ' + styleClass + '" style="'+f+'" >' +
              '<span class="jazz-titlebar-icon ' + icon + '"></span></a>').appendTo(this.handlerIcons);
            
            return obj;
        }, 
        
        /**
         * @desc 操作按钮
         * @param {titlebar} 标题栏目对象
         * @private
         */       
        _customButton: function(titlebar){
	    	var icon_width = "";
	    	if(jazz.isIE(7) || jazz.isIE(6)){
	    		icon_width += 'width:100px';
	    	}
	    	
	        //操作按钮集 左侧区域
	        this.handlerIcons1 = $('<span class="jazz-panel-rtl-left" style="'+icon_width+'"></span>').appendTo(titlebar);
	        //操作按钮集右侧区域
	        this.handlerIcons2 = $('<span class="jazz-panel-rtl-right" style="'+icon_width+'"></span>').appendTo(titlebar);
	        
	    	var handlerIcons = this.handlerIcons1;
	    	
	        if(this.options.titlebuttonalign == 'right') {
	        	handlerIcons = this.handlerIcons2;
	        }
	        
	        return handlerIcons;
        },

        /**
         * @desc 自定义操作按钮
         * @private
         */
        _customButtonhandler: function(){
        	var ctb = this.options.customtitlebutton;
        	if(ctb){
        		if(!$.isArray(ctb)){
        			ctb = jazz.stringToJson(ctb);
        		}
		    	var $this = this;
		    	var handlers = ctb;
		    	$.each(handlers, function(i, handler){
		        	var customIcons = $this.handlerIcons1;
		        	if(handler.align == 'right'){
		        		customIcons = $this.handlerIcons2;
		        	}
		        	$this._customIcon(handler, customIcons, handler.icon);
		    	});        			
        	}
        },
        
        /**
         * @desc 定义弹出框操作按钮
		 * @private
         */
        _customIcon: function(handler, handlerIcons, icon, iconClass) {
        	var $this = this;
        	var f = "float: left";
        	if(handler.align == "right"){
        		f = "float: right";
        	}
        	if(!iconClass){ iconClass = "";}
        	if(icon){ icon = 'style="background-image: url(\'' + icon + '\');"'; }else{icon=""; }
            var c = $('<a id="'+handler.id+'" class="jazz-panel-titlebar-icon jazz-panel-titlebar-custom" style="'+f+'">' +
                    '<span '+icon+' class="jazz-titlebar-icon jazz-titlebar-icon-custom '+iconClass+' " /></a>').appendTo(handlerIcons);
            c.on('click', function(e){
            	if($.isFunction(handler['click'])){
	            	handler.click.call(this, e, {
	            		object: $this,
	            		element: $this.element
	            	});
            	}
            });
        }, 
        
        /**
         * @desc 判断是否显示边框
         * @private
         */          
        _showBorder: function(el, showborder){
//        	var elWidth = el.outerWidth(), elHeight = el.outerHeight();
	        if (showborder) {
//	        	var tempWidth = elWidth-30, tempHeight = elHeight-30;
//	        	this.titlebar.width(tempWidth);
//	        	this.content.width(tempWidth);
//	        	el.css({width: tempWidth, height: tempHeight});
	        	el.addClass("jazz-panel-border");
			} else {
				el.removeClass("jazz-panel-border");
			}
//	        el.outerWidth(elWidth).outerHeight(elHeight);
//	        var nWidth = el.width();
//	        this.titlebar.outerWidth(nWidth);
//	        this.content.outerWidth(nWidth);
        },
        
        /**
         * @desc 背景颜色
         * @private
         */
        _bgcolor: function(el){
        	if(this.options.bgcolor != null){
        		el.css('background', this.options.bgcolor);        
        	}
        },
      
        /**
         * @param compId
         * @param el
         * @returns {String}
         * @private
         */
        _commonElement: function(compId, el){
            var div = '<div id = "'+compId+'_titlebar" class="jazz-panel-titlebar jazz-panel-header ';
            var vtype = this.options.vtype;
            var innerclass = "jazz-panel-titlebar-inner";
            if(vtype && vtype!="panel"){
            	div += ' jazz-'+vtype+'-titlebar';
            	innerclass = "jazz-"+vtype+"-titlebar-inner";
            }
                div += '" style="text-align: left;"><div id="'+compId+'_titlebar_inner" class="'+innerclass+'" style="width:100%; height:100%"></div></div>';
            	div += '<div id = "'+compId+'_content" class="jazz-panel-content"></div>';
            
            var c = "", contents = this.options.content;
            if(contents == null){
            	c = el.contents();            	
            }else{
            	c = contents;
            }
            el.append(div);
            
            this.titlebar = $('#'+compId+'_titlebar'); 
            this.titlebarInner = $('#'+compId+'_titlebar_inner');
            this.content = $('#'+compId+'_content');
            this.content.append(c);
              
            if(this.options.vtype != "gridpanel"){
	        	var a = this.content.children(':first-child');
	        	var b = this.content.children(':last-child');           
	            if(b.attr('vtype')=='toolbar'){
	            	b.appendTo(el);
	            	this.toolbar = b;
	            }else if(a.attr('vtype')=='toolbar'){
	            	this.titlebar.after(a);
	            	this.toolbar = a;
	            }
            }
            if(this.toolbar){
            	this.toolbar.addClass('jazz-panel-toolbar');
            }else if(this.options.vtype != "gridpanel"){
            	var buttons = this.options.buttons;
            	if($.isArray(buttons)){
            		this.toolbar = $('<div></div>');
            		el.append(this.toolbar);
            		for(var i=0, len=buttons.length; i<len; i++){
            			buttons[i]["vtype"] = "button";
            			//buttons[i]["name"] = "btn"+i;
            		}
            		this.toolbar.toolbar({items: buttons});
            		this.toolbar.addClass('jazz-panel-toolbar');
            	}
            }    
        },
        
        /**
         * @desc 关闭窗口
         * @private
         */
        _closable: function(){
        	if(this.options.closable === true){
        		var $this = this;
        		var a = this.titlebar.find('.jazz-titlebar-icon-close');
        		if(a.hasClass('jazz-titlebar-icon-close')){
        			a.remove();
        		}                
                var obj = this._renderTitleButton('jazz-panel-titlebar-close', 'jazz-titlebar-icon-close');
                obj.on('click.panelclose', function(e) {
                	$this.close();
	                e.preventDefault();
	            });
        	}else{
        		this.titlebar.find('.jazz-titlebar-icon-close').remove();
        	}
        },
        
		/**
         * @desc 绑定事件
		 * @private
         */            
        _panelbindEvents: function() {
            
        },
        
		/**
         * @desc 是否可收起
		 * @private
         */
        _toggleable: function(){   
        	var $this = this;
	        if(this.options.toggleable) {
	        	var icon = this.options.collapsable ? 'jazz-titlebar-icon-expand' : 'jazz-titlebar-icon-collapse';
	        	var a = this.titlebar.find('#toggleableId');
	        	if(a.attr('id')=='toggleableId'){
	        		this.titlebar.find('#toggleableId').remove();
	        	}
	        	this.toggler = this._renderTitleButton('', icon, 'toggleableId');
	            this.toggler.on('click.panel', function(e) {
	                $this.toggle();
	                e.preventDefault();
	            });	        	
	            								
	            if(this.options.collapsable) {
	                 this._slideUp();
	            }else{
	            	this._slideDown();
	            }
	        }	        
        },
        
		/**
         * @desc 动态改变属性
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         */
        _setOption: function(key, value){
        	if(key=='title'){
        		this.options[key] = value;
        		$('#'+this.compId+'_title').text(value);
        	}else if(key=='titlealign'){
	        	this.titlebar.css({'text-align': value});
	        	this.options[key] = value;
	        }else if(key=='titleicon'){
        		$('#'+this.compId+'_title').css({'background': 'url("'+value+'")', 'background-repeat': 'no-repeat', 'padding-left': '30px'});
        		this.options[key] = value;
	        }else if(key=='titlebuttonalign'){
	        	var rightobj = this.titlebarInner.children('.jazz-panel-rtl-right');
	        	var leftobj = this.titlebarInner.children('.jazz-panel-rtl-left');
	        	if(this.options.titlebuttonalign=='left'){
	        		if(value=='right'){
	        			rightobj.append(leftobj.contents());
	        			leftobj.children().remove();
	        		}
	        	}else if(this.options.titlebuttonalign=='right'){
	        		if(value=='left'){
	        			leftobj.append(rightobj.contents());
	        			rightobj.children().remove();
	        		}
	        	}	
	        	this.options[key] = value;
        	}else if(key=='showborder'){
        		this._showBorder(this.element, value);
        		this.options[key] = value;
        	}else if(key=='bgcolor'){
        		this.content.css('background-color', value);
        		this.options[key] = value;
        	}else if(key=='height' || key=='width'){
        		this.options[key] = value;
        		this._compSize(this.element, this.titlebar.outerHeight());
        	}else if(key=='customtitlebutton'){
        		this.options[key] = value;
        		this._customButtonhandler();
        	}else if(key=='toggleable'){
        		this.options[key] = value;
        		this._toggleable();
        	}else if(key=='collapsable'){
        		this.options[key] = value;
        	}else if(key=='closeduration'){
        		this.options[key] = value;
        	}else if(key=='closable'){
        		this.options[key] = value;
        		this._closable();
        	}else if(key=='titledisplay' || key=='frameurl'){
        		//titledisplay
				this.options[key] = value;
				this._init();
        	}else if(key=='items'){
        		return false;
        	}else{
        		this.options[key] = value;
        	}
        	
        	this._super("_setOption", key, value);
        },
        
		/**
         * @desc 滑动向上
		 * @private
         */         
        _slideUp: function() {
            this._event('beforecollapse');
            this.element.css({'height': 'auto'});
            this.content.height(0);
            this._event('aftercollapse');
            this.options.collapsable = !this.options.collapsable; 
        },

		/**
         * @desc 滑动向下
		 * @private
         */         
        _slideDown: function() {
	        this._event('beforeexpand');
	        this.element.css({'height': this.panelelementHeight});
	        this.content.height(this.panelcontentHeight);
	        this._event('afterexpand');
	        this.options.collapsable = !this.options.collapsable;
        },

		/**
         * @desc 滑动向左
		 * @private
         */          
        _slideLeft: function() {
            var $this = this;

            this.originalWidth = this.element.width();

            this.title.hide();
            this.toggler.hide();
            this.content.hide();

            this.element.animate({
                width: '42px'
            }, this.options.toggleSpeed, 'easeInOutCirc', function() {
                $this.toggler.show();
                $this.element.addClass('jazz-panel-collapsed-h');
                $this.options.collapsable = !$this.options.collapsable;
            });
        },

		/**
         * @desc 滑动向右
		 * @private
         */            
        _slideRight: function() {
            var $this = this,
            expandWidth = this.originalWidth||'100%';

            this.toggler.hide();

            this.element.animate({
                width: expandWidth
            }, this.options.toggleSpeed, 'easeInOutCirc', function() {
                $this.element.removeClass('jazz-panel-collapsable-h');
                $this.title.show();
                $this.toggler.show();
                $this.options.collapsable = !$this.options.collapsable;

                $this.content.css({
                    'visibility': 'visible',
                    'display': 'block',
                    'height': 'auto'
                });
            });
        },        
        
		/**
         * @desc 关闭窗口
         */        
        close: function() {
            var $this = this;
            
            this._event('beforeclose', null);
            
            this.element.fadeOut(this.options.closeduration,
                function() {
                    $this._event('afterclose', null);
                }
            );
        },
        
        /**
         * @desc 打开窗口
         */
        open: function(){
        	var $this=this;
        	this._event('beforeopen',null);
        	this.element.fadeIn(this.options.closeduration,
        	      function(){
        		       $this._event('afteropen',null);
        	      }		
        	);
        },
        
		/**
         * @desc 处理窗口的展开收起
		 * @private
         */          
        toggle: function() {
            if(this.options.collapsable) {
            	this.collapse();
            }else {
            	this.expand(); 
            }
        },
        
		/**
         * @desc 获得标题名称
         */  
        getTitle: function(){
        	return this.options.title;
        },
        
		/**
         * @desc 窗口的展开
		 * @private
         */         
        expand: function() {
            this.toggler.children('span.jazz-titlebar-icon').removeClass('jazz-titlebar-icon-expand').addClass('jazz-titlebar-icon-collapse');
            
            if(this.options.toggleorientation === 'vertical') {
                this._slideDown();
            } 
            else if(this.options.toggleorientation === 'horizontal') {
                this._slideRight();
            }
        },

		/**
         * @desc 窗口的收起
		 * @private
         */         
        collapse: function() {
            this.toggler.children('span.jazz-titlebar-icon').removeClass('jazz-titlebar-icon-collapse').addClass('jazz-titlebar-icon-expand');
            
            if(this.options.toggleorientation === 'vertical') {
                this._slideUp();
            } 
            else if(this.options.toggleorientation === 'horizontal') {
                this._slideLeft();
            }
        },
        
        /**
         * @desc 移除自定义按钮
         * @param {r} 移除自定义按钮对象
         */
        removeTitleButton: function(r){
        	$('#'+r).remove();
        },
          
        /**
         * @desc 隐藏自定义按钮
         * @param {r} 隐藏自定义按钮对象
         */
        hideTitleButton: function(r){
        	$('#'+r).hide();
        },
        
        /**
         * @desc 显示自定义按钮
         * @param {r} 显示自定义按钮对象
         */      
        showTitleButton: function(r){
        	$('#'+r).show();
        },
        
        /**
         * @desc 添加自定义按钮
         * @param {handlers} 添加自定义按钮数组对象
         */
        addTitleButton: function(handlers){
        	var $this = this;
        	var len = handlers.length;
        	if(len>0){
	        	$.each(handlers, function(i, handler){
	            	var customIcons = $this.handlerIcons1;
	            	if(handler.align == 'right'){
	            		customIcons = $this.handlerIcons2;
	            	}
	            	$this._customIcon(handler, customIcons, handler.icon, handler.iconClass);
	        	});
        	}
        },
        
        /**
         * @desc 动态改变iframe的src
         * @param {src} 设置iframe的src元素
         */
        iframeSrcHandler: function(src){
        	if(this.frameObject){
        		this.content.loading();
        		this.frameObject.attr('src', src);
        		var $this = this;
        		this.frameObject.load(function(){
        			$this.content.loading("hide");
                    
//自动根据子页面的高度确定iframe高度
//         			var ifm= $this.framehtml.get(0);
//        			var subWeb = document.frames ? document.frames[$this.iframeId].document : ifm.contentDocument; 
//        			if(ifm != null && subWeb != null) { 
//        				$this.framehtml.height(subWeb.body.scrollHeight); 	            	
//        			} 

			    });
        	}        
        }
        
    });
})(jQuery);(function($) {

	/**
	 * @version 0.5
	 * @name jazz.window
	 * @description 一种窗体容器，弹出页面。
	 * @constructor
	 * @extends jazz.panel
	 */
    $.widget("jazz.window", $.jazz.panel, {
       
    	options: /** @lends jazz.window# */ {
        	
    		vtype: 'window',
    		
    		//覆盖panel中的属性，因为panel默认不显示
    		/**
    		 * @type Boolean
    		 * @desc 是否显示标题
    		 * @default true
    		 */
    		titledisplay: true,
    		
			//*@desc 内容区域URL 未在子panel中写入API，在window中写入API，因为panel暂时没有使用iframe
            //frameurl: null,  		

    		/**
			 *@type Boolean
			 *@desc 窗口是否拖动
			 *@default false
			 */
            draggable: true,
            
            /**
			 *@type Boolean
			 *@desc 窗口是否可通过拖拽改变尺寸
			 *@default false
			 */
            resizable: false,
            
            /**
			 *@type Boolean
			 *@desc 窗口是否最大化展开
			 *@default false
			 */            
            maximize: false,
            
            /**
			 *@type String
			 *@desc 窗口位置
			 *@default '0,0'
			 */
            position: '0,0',
            
            /**
			 *@type Number
			 *@desc 窗口最小宽度
			 *@default 200
			 */
            minwidth: 200,
            
            /**
			 *@type Number
			 *@desc 窗口的最小高度
			 *@default 200
			 */
            minheight: 200,
            
            /**
			 *@type String
			 *@desc 窗口高度
			 *@default 230
			 */
            height: 230,
            
            /**
			 *@type String
			 *@desc 窗口宽度
			 *@default '300'
			 */
            width: 300,
            
            /**
			 *@type Boolean
			 *@desc 窗口是否可见
			 *@default false
			 */
            visible: false,
            
            /**
			 *@type Boolean
			 *@desc 窗口是否显示为模式窗体
			 *@default false
			 */
            modal: false,
            
            /**
			 *@type String
			 *@desc  是否采用Esc键关闭窗体， true 可以用Esc键关闭窗体， false不可以
			 *@default false
			 */
            closeonescape: false,
            
            /**
			 *@type Boolean
			 *@desc 显示关闭面板的按钮
			 *@default true
			 */
            closable: true,
            
            /**
			 *@type Boolean
			 *@desc 关闭状态  true 关闭   false 隐藏 
			 *@default false
			 */
            closestate: true,            
            
            /**
			 *@type Boolean
			 *@desc 显示最小化面板的按钮
			 *@default false
			 */
            minimizable: false,
            
            /**
			 *@type Boolean
			 *@desc 显示最大化面板的按钮
			 *@default false
			 */
            maximizable: false,
            
            /**
			 *@type Object
			 *@desc 任务栏
			 *@default null
			 *@private
			 */            
            taskbar: null,

            // callbacks
            
            /**  
			 *@desc 弹出框展示前触发
			 *@param {event} 事件
			 *@event
			 *@example 
			 *初始化：
			 *<br/>$("#div_id").window({beforeopen: function( event ){  <br/>} <br/>});
			 *<br/><br/><br/>
			 */
            beforeopen: null,
            
            /**  
			 *@desc 弹出框展示后触发
			 *@param {event} 事件
			 *@event
			 *@example 
			 *初始化：
			 *<br/>$("#div_id").window({afteropen: function( event ){  <br/>} <br/>});
			 *<br/><br/><br/>
			 */
            afteropen: null,
            
            /**  
			 *@desc 弹出框隐最大化触发
			 *@event
			 *@example 
			 *初始化：
			 *<br/>$("#div_id").window({maximize: function(  ){  <br/>} <br/>});
			 *<br/><br/><br/>
			 */
            maximize: null,
            
            /**  
			 *@desc 弹出框隐最小化触发
			 *@event
			 *@example 
			 *初始化：
			 *<br/>$("#div_id").window({minimize: function(  ){  <br/>} <br/>});
			 *<br/><br/><br/>
			 */
            minimize: null
        },
        
        /** @lends jazz.window */
		
		/**
         * @desc 创建组件
		 * @private
         */
        _create: function() {
        	this._super();
        },

        _init: function() {
        	this._super();

        	this.element.addClass('jazz-window jazz-helper-hidden');
        	
        	var pageSize = this.getPageSize();
        	this.winSize = {width: pageSize.windowWidth, height: pageSize.windowHeight};
        	
            //icons
            this.icons = this.titlebarInner.find('.jazz-panel-titlebar-icon');
            this.closeIcon = this.handlerIcons.children('.jazz-panel-titlebar-close');
            
            //最大化
            this._maximizable();
            
            //最小化
            this._minimizable(); 
            
            //是否采用esc关闭页面
            this._esc();            
            
            this.blockEvents = 'focus.window mousedown.window mouseup.window keydown.window keyup.window';          
            this.parent = this.element.parent();
            
            //事件
            this._winbindEvents();

            //拖动
            this._draggable();

            //改变窗体大小
            if(this.options.resizable){
            	this._setupResizable();
            }

            //默认最大化展开
            if(this.options.maximize){
            	this.toggleMaximize();
            }
          
            //docking zone
            if($(document.body).children('.jazz-window-docking-zone').length === 0) {
                $(document.body).append('<div class="jazz-window-docking-zone"></div>');
            }            
            
            //是否可见
            if(this.options.visible){
                this.open();
            }
            
            this.pageindex = 0;
        },
        
        _setOption: function(key, value){
        	if(key=='_draggable'){
        		this.options[key] = value;
        		this._draggable();
        	}else if(key=='resizable'){
        		this.options[key] = value;
        		this._setupResizable();
        	}else if(key=='maximize'){
        		this.options[key] = value;  
        		if(value === true){
        			this.toggleMaximize();
        		}
        	}else if(key=='minwidth' || key=='minheight' || key=='modal' || key=='closestate'){
        		this.options[key] = value;	
        	}else if(key=='visible'){
        		this.options[key] = value;
        		if(value === true){
        			this.element.show();
        		}else{
        			this.element.hide();
        		}
        	}else if(key=='closeonescape'){
        		this.options[key] = value;
        		this._esc();
        	}else if(key=='maximizable'){
        		this.options[key] = value;
        		this._maximizable();
        	}else if(key=='minimizable'){
        		this.options[key] = value;
        		this._minimizable();
        	}else if(key=='items'){
        		return false;
        	}
        	this._super(key, value);
        },

        /**
         * @desc 窗口拖动
         * @private
         */
        _draggable: function(){
            if(this.options.draggable) {
            	this.titlebar.addClass('jazz-panel-move-titlebar');
                if(this.options.maximizable){
                	this._setupDraggable();
                }else if(!this.options.maximize){
                	this._setupDraggable();
                }
            }else{
            	this.titlebar.removeClass('jazz-panel-move-titlebar');
            	//this.element.draggable("destroy");
            }     	
        },
        
        /**
         * @desc 显示遮罩层
		 * @private
		 * @example this._enableModality();
         */
        _enableModality: function() {
        	var $this = this;
        	var bodyHeight = $('body').height(),
        	    height = bodyHeight > $this.pageHeight() ? bodyHeight : $this.pageHeight();
        	
            this.modalId = "modal_"+this.getCompId()+"_"+jazz.getRandom();
            this.modality = $('<div id="'+this.modalId+'"></div>')
                            .css({
                                'width': $this.pageWidth(),
                                'height': height,
                                'z-index': $this.element.css('z-index') - 1
                            }).appendTo(document.body)
	            			.loading({blank: true});

            $(document).bind('keydown.window',
                    function(event) {
                        if(event.keyCode == $.ui.keyCode.TAB) {
                            var tabbables = $this.content.find(':tabbable'), 
                            first = tabbables.filter(':first'), 
                            last = tabbables.filter(':last');

                            if(event.target === last[0] && !event.shiftKey) {
                                first.focus(1);
                                return false;
                            } 
                            else if (event.target === first[0] && event.shiftKey) {
                                last.focus(1);
                                return false;
                            }
                        }
                    })
                    .bind(this.blockEvents, function(event) {
                        if ($(event.target).zIndex() < $this.element.zIndex()) {
                            return false;
                        }
                    });
        },
          
        /**
         * @desc 隐藏遮罩层
		 * @private
         */
        _disableModality: function(){
            $("#"+this.modalId)
            	.loading('destroy');
            this.modality = null;
            $(document).unbind(this.blockEvents).unbind('keydown.window');
        },
        
        /**
         * @desc 显示弹出框触发afteropen事件
         * @private
		 * @example this._postShow();
         */
        _postShow: function() {
            //execute user defined callback
            this._event('afteropen', null);

            this._applyFocus();
        },

        /**
         * @desc 弹出框获取焦点
         * @private
		 * @example this._applyFocus();
         */
        _applyFocus: function() {
            this.element.find(':not(:submit):not(:button):input:visible:enabled:first').focus();
        },

        /**
         * @desc 绑定事件
         * @privatef
         */
        _winbindEvents: function() {   
            var $this = this, el = this.element;    
            
            el.on('click.window', function(e){
            	el.css('z-index', ++jazz.config.zindex);
            });
            
        	if(this.options.frameurl!=null){
        		$('#'+this.frameId).load(function(){
        			$('#'+$this.frameId).contents().find('body').bind('mousedown.iframe', function(e){
           			 	$this.element.css('z-index', ++jazz.config.zindex);
            		});        			
        		});
        	}
            
            //模态窗口
//            if(this.options.modal) {
//                $(window).on('resize.window', function() {
//            		var winSize = $this.getPageSize();
//                    $this.windowWidth = winSize.windowWidth;
//                    $this.windowHeight = winSize.windowHeight;
//                    
//                    $(document.body).children('.jazz-modal-overlay').css({
//                        'width': $(document).width(),
//                        'height': $(document).height()
//                    });
//                });
//            }
        },
        
        /**
         * @desc 最大化按钮
         * @private
         */
        _maximizable: function(){
        	if(this.options.maximizable === true){
        		var $this = this;
        		var a = this.titlebarInner.find('.jazz-titlebar-icon-extlink');
        		if(a.hasClass('jazz-titlebar-icon-extlink')){
        			this.titlebarInner.find('.jazz-panel-titlebar-maximize').remove();
        		}
        		var obj = this._renderTitleButton('jazz-panel-titlebar-maximize', 'jazz-titlebar-icon-extlink');
	            this.titlebar.on('dblclick.window', function(e){
	                 $this.toggleMaximize();
	                 e.preventDefault();
	            }); 
                obj.on('click.panelmax', function(e) {
                    $this.toggleMaximize();
                    e.preventDefault();
                });	            
        	}else{
        		this.titlebarInner.find('.jazz-panel-titlebar-maximize').remove();
        	}
        },

        /**
         * @desc 最小化按钮
         * @private
         */        
        _minimizable: function(){
        	if(this.options.minimizable === true){
        		var $this = this;
        		var a = this.titlebarInner.find('.jazz-titlebar-icon-minus');
        		if(a.hasClass('jazz-titlebar-icon-minus')){
        			this.titlebarInner.find('.jazz-panel-titlebar-minimize').remove();
        		}
        		var obj = this._renderTitleButton('jazz-panel-titlebar-minimize', 'jazz-titlebar-icon-minus');
                obj.on('click.panelmin', function(e) {
                    $this.toggleMinimize();
                    e.preventDefault();
                });        		
        	}else{
        		this.titlebarInner.find('.jazz-panel-titlebar-minimize').remove();
        	}
        },        
        
        /**
         * @desc 是否用Esc键关闭窗口
         * @private
         */
        _esc: function(){
        	if(this.options.closeonescape) {
        		var $this = this;
	            $(document).on('keydown.window', function(e) {
	                var keyCode = $.ui.keyCode,
	                active = parseInt($this.element.css('z-index'), 10) === jazz.config.zindex;
	
	                if(e.which === keyCode.ESCAPE && $this.element.is(':visible') && active) {
	                    $this.close();
	                }
	            });  
        	}else{
        		$(document).off('keydown.window');
        	}
        },

        /**
         * @desc 设置拖拽
         * @private
         */
        _setupDraggable: function() {
        	var $this = this;

        	this.xWidth = this.winSize.width + $(document).scrollLeft() - $this.element.width();
        	this.yHeight = this.winSize.height + $(document).scrollTop() - $this.titlebar.outerHeight;

        	this.element.draggable({
                cancel: '.jazz-panel-content, .jazz-panel-titlebar-close',
                handle: '.jazz-panel-titlebar',
                containment : [0, 0, $this.xWidth, $this.yHeight],
                start: function(e, ui){
                	if($this.maximized){  //拖动开始时判断是否是最大化窗口，如果是最大化窗口，将窗口回复原状进行拖动
                		$this.toggleMaximize();
                	}
                	var el = $this.element;
                    
                    //x = e.pageX; y = e.pageY;
                    
                	if($this.frameObject != null){
                		$this.dragHeight = el.height() - $this.titlebar.outerHeight();
                		$this.frameObject.width('1px').height('1px');
                		//$this.frameObject.css('marginTop', '-2000px');
                		$this.displayDiv.css({
                   			display: 'block',
                			top: 0, left: 0, position: 'absolute',
                			height: $this.dragHeight
                		});
                	}
               	 	el.css('z-index', ++jazz.config.zindex);                	
                },
                drag: function(e, ui){
                },
                stop: function(e, ui){
                	if($this.frameObject != null){
            			$this.frameObject.width('100%').outerHeight($this.dragHeight);
                		//$this.frameObject.css('marginTop', '0px');
            			$this.displayDiv.css({display: 'none'});
                	}
                }
            });
        },

        /**
         * @desc 设置大小调整
         * @private
		 * @example this._setupResizable();
         */
        _setupResizable: function() {
        	if(this.options.resizable === true){
            	var $this = this;
            	this.element.resizable({
                    minwidth : this.options.minwidth,
                    minheight : this.options.minheight,
                    start: function(e, ui){
                    	if($this.options.frameurl!=null){
                    		$this.frameObject.height(1).width(1);
                    		$this.displayDiv.addClass('jazz-window-framemb');
                    	}
                    	$this.content.height('auto').width('100%');
                    },
                    resize: function(){
                    },
                    stop: function(e, ui){
                    	var width = ui.size.width;
                    	$this.content.outerWidth(width);
                    	$this.content.outerHeight(ui.size.height - $this.titlebar.outerHeight() - $this.toolbar.outerHeight());
                    	if($this.options.frameurl!=null){
                    		$this.displayDiv.removeClass('jazz-window-framemb');
                    		$this.frameObject.outerWidth(width);
                    		$this.frameObject.outerHeight($this.content.height()); 
                    	}
                    }
                });                    
	        	this.resizers = this.element.children('.ui-resizable-handle');
        	}else{
        		this.element.resizable("destroy");
        	}
        },

        /**
         * @desc 初始化位置
         * @private
		 * @example this._initPosition();
         */
        _initPosition: function() {
            //reset
            this.element.css({left:0,top:0});

            if(this.options.position != "0,0"){
                var coords = this.options.position.split(','),
                x = $.trim(coords[0]),
                y = $.trim(coords[1]);

                this.element.offset({
                    left: x,
                    top: y
                });        	
            }
            else {  //if(/(center|left|top|right|bottom)/.test('center')) {
                var location =  'center';  //'right,center'.replace(',', ' ');
                if(this.options.maximize){
                	location = 'top';
                }
                this.element.position({
                        my: 'center',
                        at: location,
                        collision: 'fit',
                        of: window,
                        //make sure dialog stays in viewport
                        using: function(pos) {
                            var l = pos.left < 0 ? 0 : pos.left,
                            t = pos.top < 0 ? 0 : pos.top;

                            $(this).css({
                                left: l,
                                top: t
                            });
                        }
                    });
            }

            this.positionInitialized = true;
        },

        /**
         * @desc 移动到最顶层
         * @private
		 * @example this._moveToTop();
         */
        _moveToTop: function() {
            this.element.css('z-index', ++jazz.config.zindex);
        },

        /**
         * @desc 锁定窗口
         * @private
         * @param zone
		 * @example this._dock();
         */
        _dock: function(zone) {
            this.element.appendTo(zone).css('position', 'static');
            this.element.css({'height':'auto', 'width':'auto', 'float': 'left'});
            this.content.hide();
            this.handlerIcons.find('.jazz-titlebar-icon-minus').removeClass('jazz-titlebar-icon-minus').addClass('jazz-titlebar-icon-plus');
            this.minimized = true;

            if(this.options.resizable) {
                this.resizers.hide();
            }

            zone.css('z-index',++jazz.config.zindex);

            this._event('minimize');
        },

        /**
         * @desc 保存窗体状态
         * @private
         */
        _saveState: function() {
        	var el = this.element, win = $(window);
            this.state = {
                width: el.width(),
                height: el.height(),
                offset: el.offset(),
                windowScrollLeft: win.scrollLeft(),
                windowScrollTop: win.scrollTop()
            };
        },

        /**
         * @desc 恢复原窗口大小
         * @private
		 * @example this._restoreState();
         */
        _restoreState: function() {
            this.element.width(this.state.width).height(this.state.height);
            this._winCompSize(this.state.height);         
            
            var win = $(window);
            this.element.offset({
                    top: this.state.offset.top + (win.scrollTop() - this.state.windowScrollTop),
                    left: this.state.offset.left + (win.scrollLeft() - this.state.windowScrollLeft)
            });
        },
        
        /**
         * @desc 计算内容区大小
         * @private
         */
        _winCompSize: function(height) {
        	var h = 0;
        	if(this.toolbar){
        		h = this.toolbar.outerHeight();
        	}
            this.content.width('auto').outerHeight(height - this.titlebar.outerHeight() - h);
            if(this.options.frameurl != null){
            	var h = this.content.height();
            	this.frameObject.width('100%').outerHeight(h).load(function(){
            		$(this).width('100%').outerHeight(h);
            	});
            }        	
        },
                
        /**
         * @desc 平滑效果
         * @param {align} 滑动方向 默认left
         * @param {url} 设置url
         */
        slider: function(align, url){
        	if(!!url){
    			var $this = this;
    			this.content.loading();
    			var margin = {marginLeft: (0 - parseInt(this.element.width()))};
                if(align=='right'){
        			margin = {marginLeft: parseInt(this.element.width())};
        		}
                if(this.options.frameurl!=null){
	    			this.frameObject.animate(margin, 200, null, function(){
    	        		$this.frameObject.attr('src', url);
    	        		$this.frameObject.load(function(){
    	        			 $this.content.loading("hide");
    	        			 $this.frameObject.css({marginLeft: 0});
    				    });
	    			});
                }
        	}
        },
        
        /**
         * @desc 当前页显示
         */
        currentPage: function(url, align){
        	var obj = {
        		url: url
        	};
        	this.pageParams.push(obj);
        	this.pageindex = this.pageindex + 1;   

        	this.slider('left', url);
        	
        	if(this.pageindex === 1){
        		this.customIconId = 'customIconId'+jazz.getRandom();
        		var $this = this;
        		this.addTitleButton([{
        			id: this.customIconId,
        			align: align,
        			iconClass: 'jazz-leftarrow-icon',
        			click: function(e, ui){
        				$this.previous();
        			}
        		}]);
        	}
        },
        
        /**
         * @desc 上一页
         */
        previous: function(){
        	var index = this.pageindex - 1;
        	if(index > -1){
	        	var obj = this.pageParams[index];
	        	if(!!obj){
		        	this.slider('left', obj["url"]);
		        	this.pageindex = this.pageindex - 1;
		        	if(this.pageindex === 0){
		        		this.removeTitleButton(this.customIconId);
		        	}
	        	}
        	}
        },

        /**
         * @desc 下一页
         */
        next: function(){
        	var index = this.pageindex + 1;
        	if(index!=this.pageParams.length){
	        	var obj = this.pageParams[index];
	        	if(!!obj){
	        		this.slider('right', obj["url"]);
	        		this.pageindex = this.pageindex + 1;
	        	}
        	}
        },

        /**
         * @desc 显示弹出框
		 * @example $("#div_id").window('open');
         */
        open: function() {
        	this.element.css('z-index', ++jazz.config.zindex);

        	if(this.element.is(':visible')) {
                return;
            }

            if(!this.positionInitialized) {
                this._initPosition();
            }
            
            this._event('beforeopen', null);

            this.element.show();             

            this._postShow();

            this._moveToTop();

            if(this.options.modal) {
                this._enableModality();
            }
            
            //为青海项目单独开发的，放在门户的托盘上的
            if(this.options.taskbar instanceof jQuery){
            	var s = "";
            	if(this.options.titleicon){
            		s = 'style="background: url('+this.options.titleicon+') no-repeat center center "';
            	}
            	this.liId = 'li_id_'+jazz.getRandom();
            	var id = this.options.id ? this.options.id: this.options.name;
            	$('<li id="'+this.liId+'" winid="'+id+'"><div><span class="jazz-portal-bottom-li-img" '+s+'></span>'
    		  	 +'<span class="jazz-portal-bottom-li-word">'+this.getTitle()+'</span>'
    		  	 +'<span class="jazz-portal-bottom-li-close"></span></div></li>').appendTo(this.options.taskbar); 
            }
        },

        /**
         * @desc 关闭弹出框
         * @param {trigger} 是否触发事件  true触发事件 false不触发事件 默认true
		 * @example $("#div_id").window('close');
         */
        close: function(trigger) {
             if(this.options.taskbar instanceof jQuery){
            	var id = $('#'+this.liId);
            	if(id instanceof jQuery){
            		id.remove();
            	}
             }
             
             if(this.options.closestate == true){
	             if(!trigger){
	            	 this._event('beforeclose', null);
	             }
				 if(this.options.frameurl!=null){
					  this.frameObject.attr('src', '');
					  this.frameObject.remove();
				 }
				 this.element.removeClass().children().remove();
				 this.element.addClass('jazz-helper-hidden');
				 this.element.remove();
				 this.destroy();
		         if(this.options.modal) {
		         	this._disableModality();
		         }
		         if(!trigger){
		        	 this._event('afterclose', null);
		         }       
             }else{
            	 this.element.hide();
             }
        },        
        
        /**
         * @desc 最大化
         */
        toggleMaximize: function() {
            if(this.minimized) {
                this.toggleMinimize();
            }

            if(this.maximized) {
                this.element.removeClass('jazz-window-maximized');
                this._restoreState();

                this.handlerIcons.find('.jazz-titlebar-icon-newwin').removeClass('jazz-titlebar-icon-newwin').addClass('jazz-titlebar-icon-extlink');
                this.maximized = false;
            
            }else {
                this._saveState();

                var page = this.winSize;
                var winTop = this.state.windowScrollTop;
                var winLeft = this.state.windowScrollLeft;
                var winHeight = page.height, winWidth = page.width;

                if(this.options.taskbar instanceof jQuery){
                	winHeight = winHeight - this.options.taskbar.outerHeight();
                }
                
                this.element.addClass('jazz-window-maximized').outerWidth(winWidth).outerHeight(winHeight)
                            .offset({top: winTop, left: winLeft});
                
                this._winCompSize(winHeight);

                this.handlerIcons.find('.jazz-titlebar-icon-extlink').removeClass('jazz-titlebar-icon-extlink').addClass('jazz-titlebar-icon-newwin');
                this.maximized = true;
                this._event('maximize');
            }
        },

        /**
         * @desc 最小化
         */
        toggleMinimize: function() {
            var animate = true,
            dockingZone = $(document.body).children('.jazz-window-docking-zone');

            if(this.maximized) {
            	if(this.options.maximizable){
            		this.toggleMaximize();
            	}
                animate = false;
            }

            var $this = this;
            if(this.minimized) {
                this.element.appendTo(this.parent).css({'position':'fixed', 'float':'none'});
                this._restoreState();
                this.content.show();
                this.handlerIcons.find('.jazz-titlebar-icon-plus').removeClass('jazz-titlebar-icon-plus').addClass('jazz-titlebar-icon-minus');
                this.minimized = false;

                if(this.options.resizable) {
                    this.resizers.show();
                }
                
            } else {
                this._saveState();
                
                if(this.options.taskbar instanceof jQuery){
                	this.element.css({'margin-left': '-3000px'});
                }else{
	                if(animate) {
	                    this.element.effect('transfer', {
	                        to: dockingZone,
	                        className: 'jazz-window-minimizing'
	                     }, 300,
	                     function() {
	                        $this._dock(dockingZone);
	                        var width = $this.handlerIcons1.width()*2;
	                        if($this.handlerIcons1.width() < $this.handlerIcons2.width()){
	                        	width = $this.handlerIcons2.width()*2;
	                        }
	                        $this.element.width(width + $this.titlebar.width() + 40);
	                     });
	                } else {
	                    this._dock(dockingZone);
	                    var width = $this.handlerIcons1.width()*2;
	                    if($this.handlerIcons1.width() < $this.handlerIcons2.width()){
	                    	width = $this.handlerIcons2.width()*2;
	                    }                 
	                    $this.element.width(width + $this.titlebar.width() + 40);
	                }
                
                }
            }
            
        }

    });

})(jQuery);
(function($) {
/**
 * @version 0.5
 * @name jazz.formpanel
 * @description 是一个承载表单组件，是一完成特定的功能和结构化组件。
 * @constructor
 * @extends jazz.panel
 * @requires
 * @param
 * @example $('#formpanel_id').formpanel(); //在id='frompanel_id'的元素上绑定表单容器
 */
    $.widget("jazz.formpanel", $.jazz.panel, {
       
        options: /** @lends jazz.formpanel# */ {
        	
        	/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default formpanel
    		 */
        	vtype: 'formpanel',
            
			/**
			 *@type String
			 *@desc 页面布局类型 
			 *@default table
			 */        	
        	layout: 'table',
        	
			/**
			 *@type String
			 *@desc table页面列布局
			 *@default 
			 */
        	layoutconfig: {
	        	cols:2, 
	        	columnwidth: ['50%','*']
	        },
        	
        	
        	/**
			 *@type JSON or String
			 *@desc JSON数据或url地址 
			 *@default null
			 */        	
            dataurl: null,
            
            /**
			 *@type Object
			 *@desc 参数
			 *@default null
			 */            
            dataurlparams: null,
            
            /**
			 *@type 布尔值
			 *@desc 表单元素是否全部显为文本  true显示 false 不显示
			 *@default false
			 */            
            dataview: false
        },
        
        /** @lends jazz.formpanel */        
        
        /**
         * @desc 创建组件
         * @private
         * @example  this._create();
         */
        _create: function() {
        	this._super();
        },
        
        /**
         * @desc 初始化组件
         * @private
         * @example  this._init();
         */
        _init:function(){
        	this._super();
        	if(this.options.dataurl){
        		this.reload();
        	}
        },
        
        /**
         * @desc 切换form表单元素展现形式（编辑或者不可编辑）
		 * @params {inputname} 表单中的子元素的name值
         * @example  $('#formpanel_id').formpanel('toggleEditable','inputname','username'); 单个name=username 的元素切换
         * @example  $('#formpanel_id').formpanel('toggleEditable'); 所有子元素都切换
		 */
        toggleEditable: function(inputname){
			$.each(this.element.getChildrenComponent(), function(i, obj){
        		var childname = $(obj).attr("name");
        		var childtype = $(obj).attr("vtype");
        		var isInited = $(obj).isComponentInited();
        		if(isInited && childtype!='hiddenfield' && childtype!='toolbar' && childtype!='button'){
        			if(!inputname || childname==inputname){
        				$(obj).data(childtype)._toggleLabel();
            		}
        		}
			});
		},
		
        /**
         * @desc ajax请求函数
         * @return 返回请求响应的数据
         * @private
         * @example  this._ajax();
         */
        _ajax: function(){
            var param = {
        		url: this.options.dataurl,
        		params: this.options.dataurlparams,
        		async: true,
	        	callback: this._callback  //回调函数
            };
        	$.DataAdapter.submit(param, this);        	
        },

        /**
         * @desc ajax请求回调函数
         * @params {data} ajax请求返回的值
         * @params {sourceThis} 当前类对象
         * @private
         * @example  this._callback();
         */
        _callback: function (data, sourceThis){
        	var jsonData = data;
        	var $this = sourceThis;
    		if(!!jsonData && !!jsonData.data){
	        	$.each($this.element.getChildrenComponent(), function(i, obj){
	        		var childtype = $(obj).attr("vtype");
	        		var childname = $(obj).attr("name");
	        		var isInited = $(obj).isComponentInited();
	    			$.each(jsonData.data, function(name,value){ 
	    				if(name==childname){
	    					if(isInited){
	    						if(childtype=='datefield'){
	            					value = value.substring(0,10);
	            				}
								var togglelabel = $(obj)[childtype]('option','readonly');
	    						$(obj)[childtype]('setValue',value ,togglelabel);
								if($this.options.dataview){
									$(obj).data(childtype)._toggleLabel();
								}
	    					}
	    				}
		        	});
				});
    		}
        },
        
        /**
         * @desc 表单重置
         * @params {flag} 布尔值，true时情况hiddenfield隐藏域的值，默认情况下不清除
         * @example  $('#formpanel_id').formpanel('reset'); $('#formpanel_id').formpanel('reset',true);
         */
        reset:function(flag){
        	$.each(this.element.getChildrenComponent(), function(i, obj){
        		var childtype = $(obj).attr("vtype");
        		if(childtype!='toolbar' && childtype!='button'){
        			if(!flag && childtype=='hiddenfield'){
        				return true;
            		}
        			var isInited = $(obj).isComponentInited();
        			if(isInited){
        				$(obj)[childtype]('reset');
					}
        		}
			});
        },
        
        /**
         * @desc 根据type获取元素值
         * @private
         * @example  this._getProperties(type,name);
         */
        _getProperties: function(type,name){
        	if(!name){
        		var fromJson = "{name: '"+this.options.name+"', vtype: 'formpanel', 'data':{";
            	var i = 0;
            	$.each(this.element.getChildrenComponent(), function(m,obj){
            		var childname = "'" + $(obj).attr("name") + "'";
            		var childtype = $(obj).attr("vtype");
            		var isInited = $(obj).isComponentInited();
            		var childvalue = "";
            		if(isInited && childtype!='toolbar' && childtype!='button'){  
            			childvalue = childname + ":'" + $(obj)[childtype](type) + "'"; 
            			if(i==0){
                			fromJson += childvalue;
                		}else{
                			fromJson += ", " + childvalue;
                		}
            			i += 1;
            		}
    			});
            	fromJson += "}}";
    	    	return jazz.stringToJson(fromJson);
        	}else{
        		var value = "";
        		$.each(this.element.getChildrenComponent(), function(i, obj){
	        		var childtype = $(obj).attr("vtype");
	        		var childname = $(obj).attr("name");
	        		var isInited = $(obj).isComponentInited();
	        		if(name==childname){
    					if(isInited){
    						value = $(obj)[childtype]("'"+type+"'");
    					}
    				}
				});
        		return value;
        	}
        },
        
        /**
         * @desc 获取表单元素数据
		 * @params {name} 表单中的子元素的name值
         * @return  String or Json
         * @example  $('#formpanel_id').formpanel('getValue','inputname','username'); 获取单个name=username 的元素值
         * @example  $('#formpanel_id').formpanel('getValue'); 获取整个表单的值
         */
        getValue: function(name){
        	return this._getProperties('getValue',name);
        },
        
        /**
         * @desc 设置from表单元素值
         * @params {name} 参数为空时，设置表单的json数据，name不为空时，value设置form下元素的值
         * @params {value} 表单元素值
         * @example  $('#formpanel_id').formpanel('setValue','inputname','username'); 设置单个name=username 的元素值
         * @example  $('#formpanel_id').formpanel('setValue','name'); 设置整个表单的值
		 */
		setValue: function(name,value){
			if(name instanceof Object){
				this.options.data = name;
				this._callback(this.options.data,this);
			}else{
				$.each(this.element.getChildrenComponent(), function(i, obj){
	        		var childname = $(obj).attr("name");
	        		var childtype = $(obj).attr("vtype");
	        		var isInited = $(obj).isComponentInited();
					var togglelabel = $(obj)[childtype]('option','readonly');
        			if(childname==name && isInited){
        				if(childtype=='datefield'){
        					value = value.substring(0,10);
        				}
        				$(obj)[childtype]('setValue',value,togglelabel);
            		}
				});
			}
		},
		
		/**
         * @desc 获取表单元素数据
		 * @params {name} 表单中的子元素的name值
         * @return  String or Json
         * @example  $('#formpanel_id').formpanel('getText','inputname','username'); 获取单个name=username 的元素值
         * @example  $('#formpanel_id').formpanel('getText'); 获取整个表单的值
         */
        getText: function(name){
        	return this._getProperties('getText',name);
        },
		
        /**
         * @desc 设置组件属性的值
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         * @example $('#formpanel_id').formpanel('option','width','400'); 设置width属性的值为400
         */
        _setOption: function(key, value){
        	switch(key){
	        	case 'dataurlparams':
	       		     this.options.dataurlparams=value;
	       		     break;
	        	case 'dataurl':
	        		 this.options.dataurl=value;
	        		 break;
	        	case 'dataview':
	        		 this.options.dataview=value;
	        		 this.toggleEditable();
	        		 break;
	        	default: 
					this.options[key] = value;
					this._init();
        	}
        },
        
        /**
         * @desc 初始化表单数据，依据dataurl
         * @private
         * @example $('#formpanel_id').formpanel('reload'); 
         */
        reload: function(){
        	if(this.options.dataurl){
        		this._ajax();
        	}
        },
        
        /**
         * @desc 销毁组件  
		 * @throws
		 * @private
		 * @example this._destroy();
         */
        _destroy: function() {
            
        }
        
    });
    
})(jQuery);(function($) {
	
	/**
	 * @version 0.5
	 * @name jazz.tabpanel
	 * @description 一种基础性的tab容器。
	 * @constructor
	 * @extends jazz.container
	 * @requires
	 * @example $('#div_id').tabpanel();
	 */
    $.widget("jazz.tabpanel", $.jazz.container, {
    	
    	options: /** @lends jazz.tabpanel# */ {
    		
    		/**
			 *@type Number
			 *@desc tab标题的总宽度或高度超出所在容器时的显示类型
			 *@default null
    		 */
    		overflowtype: 2,
    		
    		/**
			 *@type Number
			 *@desc tab标题内容的宽度
			 *@default null
			 */
    		tabtitlewidth: null,//'auto'
    		
    		/**
			 *@type Number
			 *@desc tab内容页的高度
			 *@default 420
			 */
    		tabcontentheight: null,
    		
    		/**
			 *@type Number
			 *@desc tab内容页的宽度
			 *@default null
			 */
    		tabcontentwidth: null,
    		
    		/**
			 *@type tab标题的最小宽度
			 *@desc tab内容页的宽度
			 *@default null
			 */    		
    		tabminwidth: null,
    		
    		/**
			 *@type tab标题的最在宽度
			 *@desc tab内容页的宽度
			 *@default null
			 */    		
    		tabmaxwidth: null,

    		
    		tabalign: 'left',
    		
    		/**
			 *@type Number
			 *@desc 当前活动的tab页
			 *@default 0
			 */
            activeindex:0,
            
            /**
			 *@type String
			 *@desc tab页方向
			 *@default 'top'
			 */
            orientation:'top',
            	
            // callbacks
            
            /**
			 *@desc 当切换tab页签触发
			 *@param {event,index} 事件,tab坐标
			 *@example $("#div_id").tabpanel({change: function( event, index ){  <br/>} <br/>});
			 */
    		change: null ,
    		
    		/**
			 *@desc 当关闭tab页签触发
			 *@param {event,index} 事件,tab坐标
			 *@example $("#div_id").tabpanel({close: function( event, index ){  <br/>} <br/>});
			 */
    		close: null
        },
        
        /** @lends jazz.tabpanel */
        
        /**
         * @desc 创建组件
		 * @private
         */
        _create: function() {
            var element = this.element;
            this.options.selected = this.options.activeindex;
            
            //1.页面依然由用户写明ul和tabs-panels的内容，然后由组件封装
            element.addClass('jazz-tabview jazz-widget jazz-hidden-container')
            	   .addClass('jazz-tabview-' + this.options.orientation);
            
            this.navContainer = element.find('>ul:first');
            this.panelContainer = element.find('>div:first');
            
            //包装ul
            this.navContainer.wrap('<div class="jazz-tabview-header"></div>').wrap('<div class="jazz-tabview-wrap"></div>');
            this.navContainer.addClass('jazz-tabview-nav jazz-helper-reset jazz-helper-clearfix');
            if(!!this.options.tabminwidth && !!this.options.tabmaxwidth){
            	this.navContainer.find("li").css({'min-width':this.options.tabminwidth,'max-width':this.options.tabmaxwidth});
            }else if(!!this.options.tabminwidth){
            	this.navContainer.find("li").css({'min-width':this.options.tabminwidth});
            }else if(!!this.options.tabmaxwidth){
            	this.navContainer.find("li").css({'max-width':this.options.tabmaxwidth});
            }
            
            this.navContainer.find("li").addClass('jazz-tabview-default').css({'width':this.options.tabtitlewidth})
            				 .eq(this.options.activeindex).addClass('jazz-tabview-selected jazz-state-active')
            				 .css({'z-index':'3'});
            
            this.panelContainer.addClass('jazz-tabview-panels').children().addClass('jazz-tabview-panel jazz-widget-content');
            this.panelContainer.find('div.jazz-tabview-panel:not(:eq(' + this.options.activeindex + '))').addClass('jazz-helper-hidden');
            this.panelContainer.find('div.jazz-tabview-panel').css({'width':this.options.tabcontentwidth,"height":this.options.tabcontentheight});
			
            this.tabswrapContainer = element.find('.jazz-tabview-wrap');
            this.tabsHeader = element.find('.jazz-tabview-header');
            
            if(this.options.tabalign=='right'&&(this.options.orientation=='top'||this.options.orientation=='bottom')){
            	this.navContainer.css({'margin':'0 2px 0 0'});
            	this.navContainer.find("li").css({'float':'right'});
            }
         	//tabs 超出宽度后展现形式 
            if(!!this.options.overflowtype){
            	this._tabsPositionInit();
            }
        },
        
        _init: function(){
			this._bindEvents();
			
			if(this.options.layout == "fit"){
				this.panelContainer.layout({layout: 'fit'});
				var containerHeight = this.panelContainer.height();
				this.panelContainer.children().outerHeight(containerHeight);
				this.panelContainer.children().width("100%");
			}
        },
        
        /** @lends jazz.tabpanel */
        
        /**
         * @desc 调整内容区宽度
		 * 
         */
        adjustTabs : function(){
        	var $e = this.element,
        		orientation = this.options.orientation;
        	if(orientation.toLowerCase() == 'left' || orientation.toLowerCase() == 'right'){
        		$e.find('.jazz-tabview-panels').width($e.outerWidth() - $e.find('.jazz-tabview-header').outerWidth() - 1);
        	}
        },
        
        _tabsPositionInit: function(){
        	var that = this;
        	if(that.options.orientation=='top'||that.options.orientation=='bottom'){
            	
            	if(that.options.overflowtype==1){
            		that.tabs_more_horizon = $('<div class="tabs-more-horizon"></div>').appendTo(that.tabsHeader);
	            	that._initSelectableTopAndBottom();
            	}else if(that.options.overflowtype==2){
            		that.tabs_scroller_left = $('<div class="tabs-scroller-left"></div>').prependTo(that.tabsHeader);
        			that.tabs_scroller_right = $('<div class="tabs-scroller-right"></div>').appendTo(that.tabsHeader);
        			that._computeTabsHeader();
            	}
            	
	       		if(that.options.orientation=='bottom'){
	       			that.element.append(that.tabsHeader);
	       		}
            	this.tabsHeader.append("<div class='jazz-tabview-header-"+that.options.orientation+"'></div>");
        	}else if(that.options.orientation=='left'||that.options.orientation=='right'){
        		
        		that.tabsHeader.outerHeight(that.element.height());
        		that.tabswrapContainer.outerHeight(that.tabsHeader.height());
        		//that.panelContainer.outerHeight(that.element.height());
        		
            	if(that.options.overflowtype==1){
            		that.tabs_more_vertical = $('<div class="tabs-more-vertical"></div>').appendTo(that.tabsHeader);
	            	that._initSelectableLeftAndRight();
            	}else if(that.options.overflowtype==2){
            		that.tabs_scroller_top = $('<div class="tabs-scroller-top"></div>').prependTo(that.tabsHeader);
        			that.tabs_scroller_bottom = $('<div class="tabs-scroller-bottom"></div>').appendTo(that.tabsHeader);
            		that._computeTabsHeader();
            	}
        		
            	if(that.options.orientation=='right'){
            		that.tabswrapContainer.css("float", "right");
	       			that.element.append(this.tabsHeader);
	       		}
            	this.tabsHeader.append("<div style='height:"+this.tabsHeader.height()+"px;' class='jazz-tabview-header-"+that.options.orientation+"'></div>");
        	}
        },
        _computeTabsHeader: function(){
        	var that=this;
        	if(that.options.orientation=='top'||that.options.orientation=='bottom'){
        		var tempTabsWidth = that.navContainer.outerWidth(true)-that.navContainer.outerWidth();
	        	that.navContainer.find('li').each(function(i){
	       			if($(this).css("display")!="none"){
	   					tempTabsWidth += $(this).outerWidth(true);
	   				}
	       		});
	       		if(tempTabsWidth>that.tabswrapContainer.width()){
	       			that.navContainer.width(5000+"px");//设置navContainer width5000px 实现不折行，滚动tabs
	       			that.tabswrapContainer.width(that.tabsHeader.width()-that.tabs_scroller_left.width()-that.tabs_scroller_right.width()-1);
	       			that.tabs_scroller_left.css({'height':that.tabswrapContainer.height()+'px', 'display': 'block'/*,'display':'inline-block','*display':'inline','zoom':'1'*/});
	       			that.tabs_scroller_right.css({'height':that.tabswrapContainer.height()+'px', 'display': 'block'/*,'display':'inline-block','*display':'inline','zoom':'1'*/});
	       			//that.tabswrapContainer.css({"margin-left": "30px", "margin-right": "30px"});
	       			if(that.options.tabalign=='right'){
	       				that.tabswrapContainer.scrollLeft(that.navContainer.outerWidth(true));
	       			}
	       		}else{
	       			that.navContainer.css("width","auto");;
	       			//that.tabswrapContainer.width(that.tabsHeader.width());
	       			that.tabs_scroller_left.hide();
	       			that.tabs_scroller_right.hide();
	       			
	       			if(that.options.tabalign=='right'){
	       				that.tabswrapContainer.scrollLeft(that.navContainer.outerWidth(true));
	       			}
	       		}
        	}else if(that.options.orientation=='left'||this.options.orientation=='right'){
        		var tempTabsHeight = 0;
	        	that.navContainer.find('li').each(function(i){
	       			if($(this).css("display")!="none"){
	   					tempTabsHeight += $(this).outerHeight();
	   				}
	       		});
	       		if(tempTabsHeight>that.tabswrapContainer.height()){
	       			that.navContainer.height(5000+"px");//设置navContainer height5000px 实现滚动tabs
	       			that.tabswrapContainer.height(that.tabsHeader.height()-that.tabs_scroller_top.height()-that.tabs_scroller_bottom.height());
	       			that.tabs_scroller_top.css({"width":that.tabsHeader.width(),"display":"block"});
	       			that.tabs_scroller_bottom.css({"width":that.tabsHeader.width(),"display":"block"});
	       			//that.tabswrapContainer.css({"margin-left": "30px", "margin-right": "30px"});
	       		}else{
	       			that.navContainer.css('height','auto');
	       			that.tabswrapContainer.height(that.tabsHeader.height());
	       			that.tabs_scroller_top.hide();
	       			that.tabs_scroller_bottom.hide();
	       		}
        	}
        },
        //////////////////////////////////////////////////////////////////////////////
        
        /**
         * 
         */
        _adjustTabWidth : function(minWidth, maxWidth){
        	if(!!this.options.tabminwidth && !!this.options.tabmaxwidth){
            	this.navContainer.find("li").css({'min-width':this.options.tabminwidth,'max-width':this.options.tabmaxwidth});
            }else if(!!this.options.tabminwidth){
            	this.navContainer.find("li").css({'min-width':this.options.tabminwidth});
            }else if(!!this.options.tabmaxwidth){
            	this.navContainer.find("li").css({'max-width':this.options.tabmaxwidth});
            }
        },
        
        /**
         * 
         * @param width
         */
        _adjustTabContentWidth : function(width){
        	this.panelContainer.find('div.jazz-tabview-panel').css({'width': width});
			
        },
        
        /**
         * 
         * @param height
         */
        _adjustTabContentHeight : function(height){
        	this.panelContainer.find('div.jazz-tabview-panel').css({"height": height});
			
        },
        
        _computeTabsHeaderMore: function(){
        	var that=this;
        	if(that.options.orientation=='top'||that.options.orientation=='bottom'){
	    		var tempTabsWidth = that.navContainer.outerWidth(true)-that.navContainer.outerWidth();
	        	that.navContainer.find('li').each(function(i){
	       			if($(this).css("display")!="none"){
	   					tempTabsWidth += $(this).outerWidth(true);
	   				}
	       		});
	       		if(tempTabsWidth>=that.tabswrapContainer.width()){
	       			that.navContainer.width(5000+"px");
	       			that.tabswrapContainer.width(that.tabsHeader.width()-that.tabs_more_horizon.width());
	       			that.tabs_more_horizon.css({'height':that.tabswrapContainer.height()+'px','display':'inline-block','*display':'inline','zoom':'1'});
	       			
	       			/******************弹出div 显示超过了的tab标签******************************/
	       			if(that.tabsMoreDisplayDiv&&that.tabsMoreDisplayDiv.length>1){
	       				that.tabsMoreDisplayDiv.children().remove();
	       			}else{
		       			that.tabsMoreDisplayDiv = $('<div style="width:70px;position:absolute;display:none;"></div>').appendTo(document.body);
	       			}
	       			that.navContainer.clone().css('width','auto').appendTo(that.tabsMoreDisplayDiv);
	       			
	       			//为li绑定选中事件
	       			this._bindMoreHorizonEvents();
	       		}else{
	       			that.navContainer.css("width","auto");;
	       			that.tabswrapContainer.width(that.tabsHeader.width());
	       			that.tabs_scroller_right.css({'display':'none'});
       			}
	        }else if(that.options.orientation=='left'||this.options.orientation=='right'){
	    		var tempTabsHeight = 0;
	        	that.navContainer.find('li').each(function(i){
	       			if($(this).css("display")!="none"){
	   					tempTabsHeight += $(this).outerHeight();
	   				}
	       		});
	       		if(tempTabsHeight>that.tabswrapContainer.height()){
	       			that.navContainer.height(5000+"px");
	       			that.tabswrapContainer.height(that.tabsHeader.height()-that.tabs_more_vertical.height());
	       			that.tabs_more_vertical.css({"width":that.tabsHeader.width(),"display":"block"});
	       			
	       			/******************弹出div 显示超过了的tab标签******************************/
	       			if(that.tabsMoreDisplayDiv&&that.tabsMoreDisplayDiv.length>1){
	       				that.tabsMoreDisplayDiv.children().remove();
	       			}else{
		       			that.tabsMoreDisplayDiv = $('<div style="width:70px;position:absolute;display:none;"></div>').appendTo(document.body);
	       			}
	       			that.navContainer.clone().css('height','auto').appendTo(that.tabsMoreDisplayDiv);
	       			
	       			//为li绑定选中事件
	       			this._bindMoreVerticalEvents();
	       		}else{
	       			that.navContainer.css('height','auto');
	       			that.tabswrapContainer.height(that.tabsHeader.height());
	       			that.tabs_more_vertical.css("display","none");
	       		}
	        }
        },
        
        
        
        
        
        //////////////////////////////////////////////////////////////////////////////
        _initSelectableTopAndBottom: function(){
        	var that=this;
        	
    		var tempTabsWidth = that.navContainer.outerWidth(true)-that.navContainer.outerWidth();
        	that.navContainer.find('li').each(function(i){
       			if($(this).css("display")!="none"){
   					tempTabsWidth += $(this).outerWidth(true);
   				}
       		});
       		if(tempTabsWidth>=that.tabswrapContainer.width()){
       			that.navContainer.width(5000+"px");
       			that.tabswrapContainer.width(that.tabsHeader.width()-that.tabs_more_horizon.width());
       			that.tabs_more_horizon.css({'height':that.tabswrapContainer.height()+'px','display':'inline-block','*display':'inline','zoom':'1'});
       			
       			/******************弹出div 显示超过了的tab标签******************************/
       			that.tabsMoreDisplayDiv = $('<div style="width:70px;position:absolute;display:none;"></div>').appendTo(document.body);
       			that.navContainer.clone().css('width','auto').appendTo(that.tabsMoreDisplayDiv);
       			
       			//为li绑定选中事件
       			this._bindMoreHorizonEvents();
       			/******************弹出div 显示超过了的tab标签******************************/
       			if(that.options.tabalign=='right'){
       				that.tabswrapContainer.scrollLeft(that.navContainer.outerWidth(true));
       			}
       		}else{
       			that.navContainer.css("width","auto");;
       			that.tabswrapContainer.width(that.tabsHeader.width());
       			that.tabs_scroller_right.css({'display':'none'});
       			
       			if(that.options.tabalign=='right'){
       				that.tabswrapContainer.scrollLeft(that.navContainer.outerWidth(true));
       			}
       		}
        },
        _initSelectableLeftAndRight: function(){
        	var that=this;
        	
    		var tempTabsHeight = 0;
        	that.navContainer.find('li').each(function(i){
       			if($(this).css("display")!="none"){
   					tempTabsHeight += $(this).outerHeight();
   				}
       		});
       		if(tempTabsHeight>that.tabswrapContainer.height()){
       			that.navContainer.height(5000+"px");
       			that.tabswrapContainer.height(that.tabsHeader.height()-that.tabs_more_vertical.height());
       			that.tabs_more_vertical.css({"width":that.tabsHeader.width(),"display":"block"});
       			
       			/******************弹出div 显示超过了的tab标签******************************/
       			that.tabsMoreDisplayDiv = $('<div style="width:70px;position:absolute;display:none;"></div>').appendTo(document.body);
       			that.navContainer.clone().css('height','auto').appendTo(that.tabsMoreDisplayDiv);
       			
       			//为li绑定选中事件
       			this._bindMoreVerticalEvents();
       			/******************弹出div 显示超过了的tab标签******************************/
       		}else{
       			that.navContainer.css('height','auto');
       			that.tabswrapContainer.height(that.tabsHeader.height());
       			that.tabs_more_vertical.css("display","none");
       		}
        	
        },
        /***************************************************************************************************/
        
        _bindMoreHorizonEvents: function(){
        	var that=this;
        	
        	that.tabs_more_horizon.off('click.more').on('click.more',function(){
        		that.tabsMoreDisplayDiv.css({left:'', top:''}).position({
			                my: 'left top',
			                at: 'right top',
			                of: that.tabs_more_horizon
			            }).show();
        	});
        	
        	that.tabsMoreDisplayDiv.find("li")
        		.on('mouseover.moreli', function(e) {
                    var element = $(this);
                    if(!element.hasClass('jazz-state-disabled')&&!element.hasClass('jazz-state-active')) {
                        element.addClass('jazz-tabview-hover');
                    }
                })
                .on('mouseout.moreli', function(e) {
                    var element = $(this);
                    if(!element.hasClass('jazz-state-disabled')&&!element.hasClass('jazz-state-active')) {
                        element.removeClass('jazz-tabview-hover');
                    }
                })
                .on('click.moreli', function(e) {
                    var element = $(this);

                    if($(e.target).is(':not(.jazz-icon-close)')) {
                        var index = element.index();
                        if(!element.hasClass('jazz-state-disabled') && index != that.options.selected) {
                            that.selectMoreLi(index);
                            that.select(index);
                        }
                        
                        //滚动并显示navContainer
                        var el = that.navContainer.find('li:eq('+index+')');
                        var tabsTotalWidth = $(el).outerWidth(true);
		            	$(el).prevAll().each(function(i){
			       			if($(this).css("display")!="none"){
		       					tabsTotalWidth += $(this).outerWidth(true);
		       				}
			       		});
			       		var scrollTotalWidth = tabsTotalWidth - that.tabswrapContainer.width();
			       		if(scrollTotalWidth>0){
			       			that.tabswrapContainer.scrollLeft(scrollTotalWidth);
			       		}else{
			       			that.tabswrapContainer.scrollLeft(0);
			       		}
                    }
                    e.preventDefault();
                });

            //Closable tabs
            this.tabsMoreDisplayDiv.find('li .jazz-icon-close').off('click.tab')
                .on('click.tab', function(e) {
                    var index = $(this).parent().index();
                    $this.remove(index);
                    e.preventDefault();
                });
                
           $(document).on('mousedown.jazz-tabpanel-other', function (e) {
                if($(that.tabsMoreDisplayDiv).is(":hidden")) {
                    return;
                }
                var target = $(e.target);
                if(target.is($(that.tabsMoreDisplayDiv))||$(that.tabsMoreDisplayDiv).has(target).length > 0) {
                    return;
                }
                if(target.is(that.tabs_more_horizon)||that.tabs_more_horizon.has(target).length > 0) {
                    return;
                }
                var offset = $(that.tabsMoreDisplayDiv).offset(); 
                if(e.pageX < offset.left ||
                    e.pageX > offset.left + $(that.tabsMoreDisplayDiv).width() ||
                    e.pageY < offset.top ||
                    e.pageY > offset.top + $(that.tabsMoreDisplayDiv).height()) {
                    $(that.tabsMoreDisplayDiv).hide();
                }
        	});
        	
        },
        
        _bindMoreVerticalEvents: function(){
        	var that=this;
        	
        	that.tabs_more_vertical.off('click.more').on('click.more',function(){
        		that.tabsMoreDisplayDiv.css({left:'', top:''}).position({
			                my: 'left top',
			                at: 'right top',
			                of: that.tabs_more_vertical
			            }).show();
        	});
        	
        	that.tabsMoreDisplayDiv.find("li")
        		.on('mouseover.moreli', function(e) {
                    var element = $(this);
                    if(!element.hasClass('jazz-state-disabled')&&!element.hasClass('jazz-state-active')) {
                        element.addClass('jazz-tabview-hover');
                    }
                })
                .on('mouseout.moreli', function(e) {
                    var element = $(this);
                    if(!element.hasClass('jazz-state-disabled')&&!element.hasClass('jazz-state-active')) {
                        element.removeClass('jazz-tabview-hover');
                    }
                })
                .on('click.moreli', function(e) {
                    var element = $(this);

                    if($(e.target).is(':not(.jazz-icon-close)')) {
                        var index = element.index();
                        if(!element.hasClass('jazz-state-disabled') && index != that.options.selected) {
                            that.selectMoreLi(index);
                            that.select(index);
                        }
                        
                        //滚动并显示navContainer
                        var el = that.navContainer.find('li:eq('+index+')');
                        var tabsTotalWidth = $(el).outerHeight(true);
		            	$(el).prevAll().each(function(i){
			       			if($(this).css("display")!="none"){
		       					tabsTotalWidth += $(this).outerHeight(true);
		       				}
			       		});
			       		//alert(tabsTotalWidth);
			       		var scrollTotalWidth = tabsTotalWidth - that.tabswrapContainer.height();
			       		if(scrollTotalWidth>0){
			       			that.tabswrapContainer.scrollTop(scrollTotalWidth);
			       		}else{
			       			that.tabswrapContainer.scrollTop(0);
			       		}
                    }
                    e.preventDefault();
                });

            //Closable tabs
            this.tabsMoreDisplayDiv.find('li .jazz-icon-close').off('click.tab')
                .on('click.tab', function(e) {
                    var index = $(this).parent().index();
                    $this.remove(index);
                    e.preventDefault();
                });
                
           $(document.body).bind('mousedown.jazz-tabpanel-other', function (e) {
                if($(that.tabsMoreDisplayDiv).is(":hidden")) {
                    return;
                }
                var target = $(e.target);
                if(target.is($(that.tabsMoreDisplayDiv))||$(that.tabsMoreDisplayDiv).has(target).length > 0) {
                    return;
                }
                if(target.is(that.tabs_more_vertical)||that.tabs_more_vertical.has(target).length > 0) {
                    return;
                }
                var offset = $(that.tabsMoreDisplayDiv).offset(); 
                if(e.pageX < offset.left ||
                    e.pageX > offset.left + $(that.tabsMoreDisplayDiv).width() ||
                    e.pageY < offset.top ||
                    e.pageY > offset.top + $(that.tabsMoreDisplayDiv).height()) {
                    $(that.tabsMoreDisplayDiv).hide();
                }
        	});
        	
        },
        
                /**
         * @desc tab页选择方法
		 * @param {index} tab页码数从0开始第1个tab 
		 * @example $('#div_id').tabpanel('select','index');
         */
        selectMoreLi: function(index) {
           var headers = this.tabsMoreDisplayDiv.find('li'),
           oldHeader = headers.filter('.jazz-state-active'),
           newHeader = headers.eq(index);
           //aria
           oldHeader.attr('aria-expanded', false);
           newHeader.attr('aria-expanded', true);

           oldHeader.removeClass('jazz-tabview-selected jazz-state-active');
           newHeader.removeClass('jazz-tabview-hover').addClass('jazz-tabview-selected jazz-state-active');
       },
        
        /***************************************************************************************************/
        _bindTopAndBottomScrollEvent: function(){
        	var that=this;
        	var scrollwidth = 50;
        	if(that.tabs_scroller_left&&that.tabs_scroller_right){
	        	that.tabs_scroller_left.off("click").on("click",function(){
	        		var scrollleft = that.tabswrapContainer.scrollLeft()+scrollwidth;
		       		if(that.options.tabalign=='right'){
		       			that.tabswrapContainer.scrollLeft(scrollleft);
		       		}else{
			       		var tabsTotalWidth = 0;
		            	that.navContainer.find('li').each(function(i){
			       			if($(this).css("display")!="none"){
		       					tabsTotalWidth += $(this).outerWidth();
		       				}
			       		});
			       		var scrollTotalWidth = tabsTotalWidth - that.tabswrapContainer.width();
			       		if(scrollleft>scrollTotalWidth){
		        			that.tabswrapContainer.scrollLeft(scrollTotalWidth+24);
		        		}else{
		        			that.tabswrapContainer.scrollLeft(scrollleft);
		        		}
		       		}
	        	});
	        	that.tabs_scroller_right.off("click").on("click",function(){
	        		var wrapcontainerScrollWidth = that.tabswrapContainer[0].scrollWidth;
	        		var scrollleft = that.tabswrapContainer.scrollLeft()-scrollwidth;
	        		if(that.options.tabalign=='right'){
		       			var tabsTotalWidth = 0;
		            	that.navContainer.find('li').each(function(i){
			       			if($(this).css("display")!="none"){
		       					tabsTotalWidth += $(this).outerWidth();
		       				}
			       		});
		        		//var scrollTotalWidth = tabsTotalWidth - that.tabswrapContainer.width();
		        		if(that.tabswrapContainer.scrollLeft()<(wrapcontainerScrollWidth-tabsTotalWidth)){
		        			scrollleft = wrapcontainerScrollWidth-tabsTotalWidth-24;
		        		}
		       		}
		       		that.tabswrapContainer.scrollLeft(scrollleft);
	        	});
        	}
        },
        _bindLeftAndRightScrollEvent: function(){
        	var that=this;
    		var scrollheight = 50;
       		var singleLiHeight = 0;
       		if(that.tabs_scroller_top&&that.tabs_scroller_bottom){
	            that.tabs_scroller_top.off("click").on("click",function(){
	        		var tabsTotalHeight = 0;
	            	that.navContainer.find('li').each(function(i){
		       			if($(this).css("display")!="none"){
	       					tabsTotalHeight += $(this).outerHeight();
	       					singleLiHeight = $(this).outerHeight();
	       				}
		       		});
		       		var scrollTotalHeight = tabsTotalHeight - that.tabswrapContainer.height();
	        		var scrolltop = that.tabswrapContainer.scrollTop()+scrollheight;
	        		if(scrolltop>scrollTotalHeight){
	        			that.tabswrapContainer.scrollTop(scrollTotalHeight+singleLiHeight+20);
	        		}else{
	        			that.tabswrapContainer.scrollTop(scrolltop);
	        		}
	        	});
	        	that.tabs_scroller_bottom.off("click").on("click",function(){
	        		var scrolltop = that.tabswrapContainer.scrollTop()-scrollheight;
	        		that.tabswrapContainer.scrollTop(scrolltop);
	        	});
       		}
        },
        /**
         * @desc 绑定事件
         * @private
		 * @example this._bindEvents();
         */
        _bindEvents: function() {
            var $this = this;

            //Tab header events
            this.navContainer.children('li').off('mouseover.tab mouseout.tab click.tab')
                    .on('mouseover.tab', function(e) {
                        var element = $(this);
                        if(!element.hasClass('jazz-state-disabled')&&!element.hasClass('jazz-state-active')) {
                            element.addClass('jazz-tabview-hover');
                        }
                    })
                    .on('mouseout.tab', function(e) {
                        var element = $(this);
                        if(!element.hasClass('jazz-state-disabled')&&!element.hasClass('jazz-state-active')) {
                            element.removeClass('jazz-tabview-hover');
                        }
                    })
                    .on('click.tab', function(e) {
                        var element = $(this);

                        if($(e.target).is(':not(.jazz-icon-close)')) {
                            var index = element.index();

                            if(!element.hasClass('jazz-state-disabled') && index != $this.options.selected) {
                                $this.select(index);
                            }
                        }

                        e.preventDefault();
                    });

            //Closable tabs
            this.navContainer.find('li .jazz-icon-close').off('click.tab')
                .on('click.tab', function(e) {
                    var index = $(this).parent().index();
                    $this.remove(index);
                    e.preventDefault();
                });
            this._bindTopAndBottomScrollEvent();
            this._bindLeftAndRightScrollEvent();
        
        },
        
        /**
         * @desc tab页选择方法
		 * @param {index} tab页码数从0开始第1个tab 
		 * @example $('#div_id').tabpanel('select','index');
         */
        select: function(index) {
           this.options.selected = index;

           var newPanel = this.panelContainer.children().eq(index),
           headers = this.navContainer.children(),
           oldHeader = headers.filter('.jazz-state-active'),
           newHeader = headers.eq(newPanel.index()),
           oldPanel = this.panelContainer.children('.jazz-tabview-panel:visible'),
           $this = this;

           //aria
           oldPanel.attr('aria-hidden', true);
           oldHeader.attr('aria-expanded', false);
           newPanel.attr('aria-hidden', false);
           newHeader.attr('aria-expanded', true);

           if(this.options.effect) {
                oldPanel.hide(this.options.effect.name, null, this.options.effect.duration, function() {
                   oldHeader.removeClass('jazz-tabview-selected jazz-state-active');

                   newHeader.removeClass('jazz-tabview-hover').addClass('jazz-tabview-selected jazz-state-active');
                   newPanel.show($this.options.name, null, $this.options.effect.duration, function() {
                       $this._trigger('change', null, index);
                   });
               });
           }
           else {
               oldHeader.removeClass('jazz-tabview-selected jazz-state-active');
               oldPanel.hide();

               newHeader.removeClass('jazz-tabview-hover').addClass('jazz-tabview-selected jazz-state-active');
               newPanel.show();

               this._trigger('change', null, index);
           }
       },

       /**
        * @desc tab页删除方法
		* @param {index} tab页码数从0开始第1个tab 
		* @example $('#div_id').tabpanel('remove','index');
        */
       remove: function(index) {
    	   
    	   if(index == -1 ) return false;
    	   
           var header = this.navContainer.children().eq(index),
           panel = this.panelContainer.children().eq(index);

           this._trigger('close', null, index);

           header.remove();
           panel.remove();

           //active next tab if active tab is removed
           if(index == this.options.selected) {
               var newIndex = this.options.selected == this.getLength() ? this.options.selected - 1: this.options.selected;
               this.select(newIndex);
           }
           if(index==this.options.activeindex){
        	   if(index==0){
        		   this.select(0);
        	   }else if(index==this.getLength()){
        		   this.select(index-1);
        	   }
           }else{
        	   this.select(this.options.activeindex); 
           }
           
           if(this.options.overflowtype==2){
			   this._computeTabsHeader();
		   }else if(this.options.overflowtype==1){
		   		this._computeTabsHeaderMore();
		   		//模拟触发moreDIV中li的click事件
		   }
       },
       
       /**
        * @desc tab页添加方法
		* @param {tabid} tab页id名称
		* @param {tabname} tab页name名称
		* @param {taburl} tab页url路径
		* @example $('#div_id').tabpanel('addTab','tabid','tabname','taburl');
        */
       addTab: function(tabid,tabname,taburl) {
    	   var tablength = this.getLength();
    	   var index = tablength-1;
		   var tabli = "<li><a href='#"+tabid+"'>"+tabname+"</a><span class='jazz-icon jazz-icon-close'></span></li>";
		   var tabdiv = "<div id='"+tabid+"'>"+tabname+"</div>";
		   if(taburl!=null&&taburl!=''){
			   tabdiv = "<div id='"+tabid+"'><iframe src='"+taburl+"' id='"+this.options.frameName+"' name='iframepage' frameBorder='0' scrolling='yes' width='100%' height='100%'></iframe></div>";
           }
		   if($("#"+tabid).length>0){
			   var tabindex = index;
			   this.panelContainer.children().each(function(i){
				   var id = $(this).attr("id");
				   if(tabid==id){
					   tabindex = i;
				   }
			   });
			   this.select(tabindex);
		   }else{
			   this.navContainer.children(':eq('+ index +')').after(tabli);
			   this.panelContainer.children(':eq('+ index +')').after(tabdiv);
			   this.panelContainer.children(':not(:eq(' + tablength + '))').addClass('jazz-helper-hidden');
			   this.navContainer.children(':eq('+ tablength +')').addClass('jazz-tabview-default jazz-tabview-selected jazz-state-active');
			   this.panelContainer.children(':eq('+ tablength +')').addClass('jazz-tabview-panel jazz-widget-content');
			   this.panelContainer.children(':eq('+ tablength +')').css('height',this.options.tabcontentheight);
			   this.panelContainer.children(':eq('+ tablength +')').css('width',this.options.tabcontentwidth);
	           this.navContainer.children(':eq('+ tablength +')').css('width',this.options.tabtitlewidth);
			   
			   var $this = this;
			   this.navContainer.children(':eq('+ tablength +')')
	               .on('mouseover.tab', function(e) {
	                   var element = $(this);
	                   if(!element.hasClass('jazz-state-disabled')&&!element.hasClass('jazz-state-active')) {
	                       element.addClass('jazz-tabview-hover');
	                   }
	               })
	               .on('mouseout.tab', function(e) {
	                   var element = $(this);
	                   if(!element.hasClass('jazz-state-disabled')&&!element.hasClass('jazz-state-active')) {
	                       element.removeClass('jazz-tabview-hover');
	                   }
	               })
	               .on('click.tab', function(e) {
	                   var element = $(this);
	
	                   if($(e.target).is(':not(.jazz-icon-close)')) {
	                       var index = element.index();
	
	                       if(!element.hasClass('jazz-state-disabled') && index != $this.options.selected) {
	                           $this.select(index);
	                       }
	                   }
	
	                e.preventDefault();
	           });

		       //Closable tabs
		       this.navContainer.children(':eq('+ tablength +')').find('.jazz-icon-close').off('click.tab')
		           .on('click.tab', function(e) {
		               var index = $(this).parent().index();
		               $this.remove(index);
		               e.preventDefault();
		           });
			   this.select(tablength);
			   if(this.options.overflowtype==2){
				   this._computeTabsHeader();
			   }else if(this.options.overflowtype==1){
			   		this._computeTabsHeaderMore();
			   		//模拟触发moreDIV中li的click事件
			   }
		   }
       },
       
       /**
        * @desc tab页添加方法
		* @param {index} 新增tab页位置 从0开始第1个tab
		* @param {tabid} tab页id名称
		* @param {tabname} tab页name名称
		* @param {taburl} tab页url路径
		* @example $('#div_id').tabpanel('addTabCustom','index','tabid','tabname','taburl');
        */
       addTabCustom: function(index,tabid,tabname,taburl) {
    	   
    	   if($("#"+tabid).length>0){
			   var tabindex = 0;
			   this.panelContainer.children().each(function(i){
				   var id = $(this).attr("id");
				   if(tabid==id){
					   tabindex = i;
				   }
			   });
			   this.select(tabindex);
		   }else{
	    	   var tablength = this.getLength();
	    	   if(index>tablength){
	    		   index = tablength-1;
	    	   }
	    	   if(index<0){
	    		   index = 0;
	    	   }
	    	   var element = this.element;
			   var tabli = "<li><a href='#"+tabid+"'>"+tabname+"</a></li>";
			   var tabdiv = "<div id='"+tabid+"'>"+tabname+"</div>";
			   if(taburl!=null&&taburl!=''){
				   tabdiv = "<div id='"+tabid+"'><iframe src='"+taburl+"' id='"+this.options.frameName+"' name='iframepage' frameBorder='0' scrolling='yes' width='100%' height='100%'></iframe></div>";
			   }
			   this.navContainer.children(':eq('+index+')').before(tabli);
			   this.panelContainer.children(':eq('+index+')').before(tabdiv);
			   
			   tabli = this.navContainer.find('li:eq('+ index +')');
			   tabdiv = this.panelContainer.find('div:eq('+ index +')');
			   
			   this.navContainer.children(':not(:eq(' + index + '))').removeClass('jazz-tabview-selected  jazz-state-active');
			   this.panelContainer.children(':not(:eq(' + index + '))').addClass('jazz-helper-hidden');
			   tabli.addClass('jazz-tabview-default jazz-tabview-selected jazz-state-active')
			   		.css('width',this.options.tabtitlewidth);
			   tabdiv.addClass('jazz-tabview-panel jazz-widget-content')
			   		 .css({'width':this.options.tabcontentwidth,'height':this.options.tabcontentheight});
			   
			   var $this = this;
			   tabli.off('mouseover.tab mouseout.tab')
	               .on('mouseover.tab', function(e) {
	                   var element = $(this);
	                   if(!element.hasClass('jazz-state-disabled')&&!element.hasClass('jazz-state-active')) {
	                       element.addClass('jazz-tabview-hover');
	                   }
	               })
	               .on('mouseout.tab', function(e) {
	                   var element = $(this);
	                   if(!element.hasClass('jazz-state-disabled')&&!element.hasClass('jazz-state-active')) {
	                       element.removeClass('jazz-tabview-hover');
	                   }
	               })
	               .on('click.tab', function(e) {
	                   var element = $(this);
	                   if($(e.target).is(':not(.jazz-icon-close)')) {
	                       var index = element.index();
	                       if(!element.hasClass('jazz-state-disabled') && index != $this.options.selected) {
	                           $this.select(index);
	                       }
	                   }
	
	                e.preventDefault();
	           });

			   this.select(tablength); 
			   this._computeTabsHeader();
		   }
       },

       /**
        * @desc 动态设置属性
        * @param key
        * @param value
        */
       _setOption: function( key, value ) {
           this._super( key, value );
       },
       
       _setOptions: function( options ) {
           this._super( options );
       },

       /**
        * @desc 获取tab页个数方法
        * @return 返回tab页个数
		* @example $('#div_id').tabpanel('getLength');
        */
       getLength: function() {
           return this.navContainer.children().length;
       },

       /**
        * @desc 获取当前活动的tab页
        * @return 返回当前所选tab页码
		* @example $('#div_id').tabpanel('getActiveIndex');
        */
       getActiveIndex: function() {
           return this.options.selected;
       },

       /**
        * @desc 
        * @param {panel}
        * @private
		* @example this._markAsLoaded('panel');
        */
       _markAsLoaded: function(panel) {
           panel.data('loaded', true);
       },

		/**
        * @desc 
        * @param {panel}
        * @private
		* @return 返回布尔值
		* @example this._isLoaded('panel');
        */ 
       _isLoaded: function(panel) {
           return panel.data('loaded') === true;
       },

       /**
        * @desc 关闭当前活动的tab页不可用
        * @param {index} tab页码数从0开始第1个tab
		* @example $('#div_id').tabpanel('disable','index');
        */
       disable: function(index) {
           this.navContainer.children().eq(index).addClass('jazz-state-disabled');
       },

       /**
        * @desc 打开当前活动的tab页可用
        * @param {index} tab页码数从0开始第1个tab 
	    * @example $('#div_id').tabpanel('enable','index');
        */
       enable: function(index) {
           this.navContainer.children().eq(index).removeClass('jazz-state-disabled');
       }

    });
})(jQuery); 
(function($){
/** 
 * @version 1.0
 * @name jazz.field
 * @description 表单元素的基类，提供基本的元素内容。
 * @constructor
 * @extends jazz.boxComponent
 */
	$.widget('jazz.field', $.jazz.boxComponent, {
		
	    options: /** @lends jazz.field# */ {
        	
        	/**
    		 *@type String
    		 *@desc 组件默认值
    		 *@default
    		 */
        	defaultvalue: '',
        	        	
        	/**
        	 *@type Boolean
        	 *@desc 是否禁用 true不可用 false可用 
        	 *@default true
        	 */ 
        	disabled: false,

        	/**
        	 *@type String
        	 *@desc 组件标签信息
        	 *@default 
        	 */
        	label: '',
        	
        	/**
        	 *@type String
        	 *@desc 标签的显示位置   left 居左  center 居中  right 居右
        	 *@default 'right'
        	 */
        	labelalign: 'right',
        	
        	/**
        	 * @type Number
        	 * @desc 默认宽度
        	 * @default 150
        	 */			
        	defaultwidth: jazz.config.fieldDefaultWidth,
        	
        	/**
        	 * @type Number
        	 * @desc 组件默认高度
        	 * @default 26
        	 */			
        	defaultheight: jazz.config.fieldDefaultHeight,
        	
        	/**
        	 *@type Number
        	 *@desc 组件标签的宽度
        	 *@default 80
        	 */
        	labelwidth: jazz.config.fieldLabelWidth,
        	
        	/**
        	 *@type String
        	 *@desc 组件标签信息和内容区的分隔符
        	 *@default ':'
        	 */
        	labelsplit: ':',
			
			/**
			 *@type String
			 *@desc 输入框内容区的前缀
			 *@default ''
			 */			
			prefix: '',
        	
        	/**
        	 *@type Boolean
        	 *@desc 是否是可读字段 true只读  false非只读
        	 *@default false
        	 */
        	readonly: false,
        	
        	/**
        	 * @type Number
        	 * @desc 数据有效性的验证类型
        	 * @default 0
        	 */
        	ruletype: 0,			
			
			/**
			 *@type String
			 *@desc 输入框内容区的后缀
			 *@default ''
			 */			
			suffix: '',
			
			/**
			 *@type String
			 *@desc 输入框的提示信息
			 *@default ''
			 */			
			tooltip: '',
			
			/**
			 *@type String
			 *@desc 输入框的提示信息位置（基于文本框右上角这个点来说）
			 *@default ''
			 */			
			toolpositon: '',
			
			/**
			 *@type String
			 *@desc 输入框默认提示文字
			 *@default ''
			 */		
			valuetip: '',

			/**
			 * @type Boolean
			 * @desc 提交数据时的验证状态，不提供外部使用，所以定义为私有方法  true需要验证  false不需要验证
			 * @default false
			 * @private
			 */
			validationstate: false
		},
		
		/** @lends jazz.field */
		
        /**
         * @desc 生成公用部分的dom元素
         * @private 
         */
		_create: function(){
			this._super();
			//组件名称, 必录项
			var name = this.options.name;
			var vtype = this.options.vtype;
			var label = this.options.label;
			
			var el = this.element;
			this.compId = this.getCompId();
			
			//设置组件必要属性
			el.addClass('jazz-field-comp');
			
			//添加label
			var div = "";
			if(label){
				var labelRule = this.options.rule;
				if(labelRule){
					if(labelRule.indexOf("must")>=0){
						label = "<font color='red'>*</font>"+label;
					}
				}
				div = '<label id="'+this.compId+'_label" class="jazz-field-comp-label jazz-form-text-label" for="'+this.compId+'_input">'+label+this.options.labelsplit+'</label>';
			}
			
			//添加DIV
			div = div + '<div id="'+this.compId+'_out" class="jazz-field-comp-out"><div id="'+this.compId+'_in" class="jazz-field-comp-in">';

			var prefixspan = this.options.prefix ? "<span id='"+this.compId+"_prefix' class='jazz-field-comp-prefix'>"+this.options.prefix+"</span>" : "";
			var suffixspan = this.options.suffix ? "<span id='"+this.compId+"_suffix' class='jazz-field-comp-suffix'>"+this.options.suffix+"</span>" : "";
			
			div = div + prefixspan + '<div id="'+this.compId+'_frame" class="jazz-field-input-frame"></div>' + suffixspan;
						
			div = div + '</div>';

			el.append(div);
			
			//创建对象
			if(label){
				this.label = $('#'+this.compId+'_label');
				this.label.css("text-align", this.options.labelalign);
				if(!!this.options.labelwidth){
					this.label.css("width", this.options.labelwidth);
				}
			}
			this.grandpa = $('#'+this.compId+'_out');  
			this.parent = $('#'+this.compId+'_in');
			this.inputFrame = $('#'+this.compId+'_frame');
			if(this.options.prefix){
				this.prefix = $('#'+this.compId+'_prefix');
			}
			if(this.options.suffix){
				this.suffix = $('#'+this.compId+'_suffix');
			}
			this.ruleImg = $('<div id="'+this.compId+'_r" class="jazz-field-comp-r jazz-helper-hidden"></div>').insertAfter(this.parent);
		},
		
        /**
         * @desc 初始化
         * @private
         */
		_init: function(){
			this._super();
			if(this.options.vtype=="hiddenfield"){
				this.element.hide();
			}
			this.filtervtype = this._filterVtype();
			//计算组件的尺寸
			this._fieldDimensionSize();
		},
		
		/**
		 * @desc 过滤掉没有输入框的组件 true有输入框  false没有输入框
		 * @returns {Boolean}
		 */
		_filterVtype: function(){
			var vtype = this.options.vtype;
			if(vtype !="radiofield" && vtype != "checkboxfield" 
				& vtype !="hiddenfield" && vtype != "attachment"){
				return true;
			}else{
				return false;
			}
		},
		
		/**
		 * @desc 计算组件大小
		 * @param {containerObject} 容器对象
		 * @private
		 */
		_fieldDimensionSize: function(containerObject){
			var el = this.element;
			var width = this.options.width;
			var height = this.options.height;
			
			var compWidth = width != -1 ? width : this.options.defaultwidth;
			var compHeight = height != -1 ? height : this.options.defaultheight;
			if(this.compWidth != compWidth){
				
				//根据给定的值计算组件宽度
				//compWidth = "";
				el.outerWidth(compWidth);           
				
				var labelWidth = 0;
				if(this.label){
					labelWidth = this.label.outerWidth();
				}
				
				var prefixWidth = 0;
				if(this.options.prefix){
					prefixWidth = this.prefix.outerWidth();
				}
				
				var suffixWidth = 0;
				if(this.options.suffix){
					suffixWidth = this.suffix.outerWidth();
				}
				
				//验证区
				this.ruleAreaWidth = jazz.config.ruleImgWidth;
				var ruletype = this.options.ruletype;
				
				this.grandpa.css('paddingLeft', labelWidth);
				this.grandpa.outerWidth(compWidth);
				//this.parent.css({"position": "absolute", "left": labelWidth});					
				
				var iDivWidth = 0;
				if(ruletype==1){
					iDivWidth = compWidth - labelWidth  - this.ruleAreaWidth;
				}else{
					iDivWidth = compWidth - labelWidth;
				}
				this.parent.outerWidth(iDivWidth);
				this.parentwidth = this.parent.width();
				
				if(this.inputtext){
					var arrow = 20;
					this.inputFrame.css({"padding-left": prefixWidth, "padding-right": suffixWidth});
					this.inputFrame.outerWidth(this.parentwidth - arrow);
					if(this.options.suffix){
						this.suffix.css("right", arrow+"px");
					}
				}else{
					this.inputFrame.css({"padding-left": prefixWidth, "padding-right": suffixWidth});
					this.inputFrame.outerWidth(this.parent.width);
				}
			
	   			this.framewidth = this.inputFrame.width();
	   			if(this.filtervtype){
	   				this.validatefield.outerWidth(this.framewidth);
	   			}
				this.compWidth = compWidth;
			}
			
			//计算组件的高度
			if(this.filtervtype){
				if(this.compHeight != compHeight){
//					if(jazz.isIE(7) || jazz.isIE(6)){
//						h = h - 1;
//					}
					var bw = this.parent.css("border-bottom-width");
					bw = bw.substring(0,1);
					this.validatefield.outerHeight(compHeight - parseInt(bw)*2);

					this.compHeight = compHeight;
				}
			}else{
				this.parent.css("border", "0px");
//				var iheight = this.parent.height();
//				this.grandpa.height(iheight);
//				el.height(iheight);
			}
		},		
		
		/**
         * @desc 动态改变属性
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         */
        _setOption: function(key, value){
        	switch(key){
	        	case 'width':
	        		this.options.width = value;
	        		this._fieldDimensionSize();
	        		break;
	        	case 'validationstate':
	        		this.options.validationstate = value;
	        		break;
	        	case 'tooltipurl':
	        		this.options.tooltipurl = value;
	        		break;
	        	case 'dataurl':
	        		 this.options.dataurl=value;
	        		 break;
	        	case 'disabled':
	        		this.options.disabled = value;
	        		if(value){
	        			this.validatefield.attr("disabled","false");
	        		}else{
	        			this.validatefield.removeAttr("disabled");
	        		}
	        		break;
        	}
        	this._super(key, value);
        },
		
		/**
         * @desc  设置元素值
		 * @param {value} 值
		 * @param {callback} 回调函数
		 * @private
         */		
		_setData: function(value,callback){
			var $this = this;
			var i = 1,delay = 300 ,delaycount = 10000;
			var timeout = setInterval(function(){
				var status = $this.datastatus;
				var text = ($this.getText()==$this.options.blankvalue ? "" : $this.getText()) == "" ? true : false;
				if(status && text){
					$this._seSontData(value,callback);
					status = false;
				}
				i++;
				if(status || delay*i > delaycount){
					clearInterval(timeout);
				}
			},delay);
		},
		
        /**
         * @desc 切换组件展现形式（编辑或者不可编辑）
         * @private
		 */
		_toggleLabel: function(){
			if(this.parent.is(":hidden")){
				this.parent.css("display","block");
				this.grandpa.css({"padding-top":"0px"});
				this.grandpa.children('label').remove();
			}else{
				var vtype = this.options.vtype;;
				var datavalue = this.getValue();
				if(vtype == "checkboxfield" || vtype == "radiofield" || vtype == "comboxfield" || vtype == "comboxtreefield"){
					datavalue = this.getText(); 
				}
				var content = "<label class='jazz-form-text-label'>"+datavalue+"</label>";
				this.parent.css("display","none");
				this.grandpa.css({"padding-top":"5px"});
				this.grandpa.append(content);
			}
		},
		
		/**
		 * @desc 验证显示风格
		 * @params {state} 验证状态 false未通过验证  true通过验证
		 * @private
		 */
		_validateStyle: function(state){
			//根据验证规则调整组件
			 if(this.options.ruletype == "0"){
				 if(state == false){
					 this.parent.css("width", this.parentwidth - this.ruleAreaWidth);
					 this.inputFrame.css("width", this.framewidth - this.ruleAreaWidth);
					 if(this.filtervtype){
						 this.validatefield.outerWidth(this.inputFrame.width());
					 }
				 }else{
					 this.parent.css("width", this.parentwidth);
					 this.inputFrame.css("width", this.framewidth);
					 if(this.filtervtype){
						 this.validatefield.outerWidth(this.framewidth);
					 }
				 }
			 }			
		},

        /**
         * @desc 验证方法
         * @private
         */			
        _validator: function(){
        	var $this = this;
        	var vtype = this.options.vtype, rule = this.options.rule, regMsg = this.options.msg;
        	if(rule){
        		if('textfield'===vtype || 'textareafield'===vtype  
					|| 'comboxfield'===vtype || 'datefield'===vtype 
					|| 'comboxtreefield'===vtype || 'numberfield'===vtype 
					|| 'passwordfield'===vtype || 'autocompletecomboxfield'===vtype ){ //|| 'colorfield'===vtype
        			var keyuptimeout, blurtimeout;
	        		this.validatefield.off("keyup.rule").on("keyup.rule", function(){
    			         if(keyuptimeout){
    			       	     window.clearTimeout(keyuptimeout);
    			         }
    			         keyuptimeout = setTimeout(function(){
	        				 var val = $this.getValue();
	        				 //验证
	        				 var state = jazz.doTooltip($this, val, rule, regMsg);
	        				 $this._validateStyle(state);
	        			 }, 500);
	        		}).off('blur.rule').on('blur.rule', function(){
	        			 if(blurtimeout){
	   			       	     window.clearTimeout(blurtimeout);
	   			         }
	        			 blurtimeout = setTimeout(function(){
						     var val = $this.getValue();
							 var state = jazz.doTooltip($this, val, rule, regMsg);
							 $this._validateStyle(state);
	        			 }, 500);
					});
        		}
        		
//				if('comboxfield'===vtype || 'comboxtreefield'===vtype || 'autocompletecomboxfield'===vtype){
//					 this.items.off('click.rule').on('click.rule', function() {
//					     var val = $this.getValue();
//					     //验证
//        				 var state = jazz.doTooltip($this, val, rule, regMsg);
//        				 $this._validateStyle(state);
//					 }); 
//				}
        		
        	}
        },

		/**
         * @desc 获取输入框文本值的值
		 * @return String
		 * @example $('XXX').field('getText');
		 */      
        getText: function(){
        	return this.getValue();
        },
		
		/**
         * @desc 获取输入框的值
         * @return String
		 * @example $('XXX').field('getValue');
         */				
		getValue: function(){
			var value = "";
        	var vtype = this.options.vtype;
        	if(vtype=="hiddenfield"){
        		value = this.input.val();
        	}else{
        		value = this.validatefield.val();
        	}
			
        	if(value == this.options.valuetip){
				value = "";
			}
			return value;
		},		
		
		/**
         * @desc  重置输入框值
         * @example $('XXX').field('reset');
         */
		reset: function(){
			if(this.options.valuetip){
				this.input.val(this.options.valuetip);
			}else{
				this.input.val("");
			}
		},
		
		/**
         * @desc 设置元素值  
         * @param {value} 设置的值
		 * @example $('XXX').field('setValue',value);
         */	
		setValue: function(value,flag) {
			this.input.val(value);
			this.options.value = value;
			if(flag){
				this._toggleLabel();
			}else{
				if(this.parent.is(":hidden")){
					this.parent.siblings(".jazz-form-text-label").text(value);
				}
			}
		}        
	});

})(jQuery);(function($) {
	/**
	 * @version 1.0
	 * @name jazz.textfield
	 * @description 文本输入类组件。
	 * @constructor
	 * @extends jazz.field
	 */
    $.widget("jazz.textfield", $.jazz.field, {
    	
    	options: /** @lends jazz.textfield# */ {

        	/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default textfield
    		 */
			vtype: 'textfield',

    		// callbacks
			
    		/**
			 *@desc 鼠标焦点进入输入框时触发
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").textfield("option", "enter", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("textfieldenter",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… enter="XXX()"></div> 或 <div…… enter="XXX"></div>
			 */			
			enter: null,
    		
    		/**
			 *@desc 鼠标焦点离开输入框时触发
			 *@param {event} 事件
			 *@param {ui.newValue} 新修改的值 
			 *@param {ui.oldValue} 旧值
			 *@event
			 *@example
			 *<br/>$("XXX").textfield("option", "change", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("textfieldchange",function(event, ui){  <br/>} <br/>});
			 */
    		change: null
    	},
    	
    	/** @lends jazz.textfield */
    	
        /**
         * @desc 创建组件
         * @private
         */ 
        _create: function() {
        	//创建组件
        	this._super();
        	
        	this.validatefield = this.input = $('<input id="'+this.compId+'_input" class="jazz-field-comp-input" type="text" name="'+this.options.name+'" autocomplete="off">')
        	.appendTo(this.inputFrame);
        },

        /**
         * @desc 初始化组件
         * @private
         */ 
		_init: function(){
			this._super();
			
        	var $this = this;
        	
        	this.input.off("focus.textfield blur.textfield").on("focus.textfield", function(){
        		if($(this).val()==$this.options.valuetip){
					$(this).val("");
					$(this).removeClass('jazz-field-comp-input-tip');
				}
        		
        		$this.inputValue = $this.input.val();
        		$this._event("enter", null);
        	}).on("blur.textfield", function(){
        		if($(this).val()=="" && !!$this.options.valuetip){
					$(this).val($this.options.valuetip);
					$(this).removeClass('jazz-field-comp-input-tip').addClass('jazz-field-comp-input-tip');
				}
        		
        		var ui = {
    				newValue: $this.input.val() || "",     //新值
    				oldValue: $this.inputValue || ""       //旧值
        		};
        		$this._event("change", null, ui);
        	});
			
            //设置默认值
            if(this.options.defaultvalue){
    			this.setValue(this.options.defaultvalue, this.options.readonly);
            }else{
            	if(this.options.valuetip){
            		this.setValue(this.options.valuetip);
                	this.input.addClass("jazz-field-comp-input-tip");
            	}
            }
            
            var tooltip = this.options.tooltip;
			if(!!tooltip){
				this._createToolpanel(tooltip);
			}
            
			//验证
			this._validator();            
		},
        
		/**
         * @desc 生成提示信息panel
		 * @private
		 * @example this._createToolpanel();
         */				
		_createToolpanel: function(tooltip){
			if(!!this.toolpanel){
				this.toolpanel.remove();
			}
			var name = this.options.name;
            var vtype = this.options.vtype;
			this.toolpanel = $('<div name="droptoolpanel_'+name+'" vtype="'+vtype+'" class="jazz-dropdown-panel jazz-widget-content jazz-helper-hidden"></div>').append('<div class="jazz-password-info">'+tooltip+'</div>').appendTo('body');
			var $this = this;
			this.input.on('focus.textfield', function() {
				$this._createToolpanel($this.options.tooltip);
				$this._align($this.options.toolpositon);
				$this.toolpanel.fadeIn();
            });
		},
		
		/**
		 *@desc 提示信息显示位置
		 *@private
		 *@example this._align();
		 */         
        _align: function(position) {
        	var left = 0,top = 0;
        	if(!!position){
        		if(position.split(",").length==2){
        			left = position.split(",")[0];
            		top = position.split(",")[1];
        		}else if(position.split(",").length==1){
        			left = top = position.split(",")[0];
        		}
        	}
            this.toolpanel.css({
                left:'', 
                top:'',
                height: this.parent.height(),
                'z-index': ++jazz.zindex
            })
            .position({
                my: 'left top',
                at: 'right+'+left+' top+'+top,
                of: this.parent
            });
        }
    });
    
})(jQuery);
(function($) {
	/** 
	 * @version 1.0
	 * @class jazz.textareafield
	 * @description 文本输入类组件。
	 * @constructor
	 * @extends jazz.field
	 */
    $.widget("jazz.textareafield", $.jazz.field, {
       
        options: /** @lends jazz.textareafield# */ {
        	
			/**
			 *@type String
			 *@desc 组件的效验类型
			 *@default 'textareafield'
			 */        	
        	vtype: 'textareafield',
        	
			/**
			 *@type Number
			 *@desc 最大输入长度
			 *@default null
			 */              
            maxlength: null, 
            
			/**
			 *@type Number
			 *@desc 数量
			 *@default null
			 */             
            counter: null,
            
			/**
			 *@type String
			 *@desc 数量模板值
			 *@default null
			 */             
            countertemplate: '{0}',
            	
    		// callbacks
			
    		/**
			 *@desc 鼠标焦点进入输入框时触发
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").textareafield("option", "enter", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("textareafieldenter",function(event, ui){  <br/>} <br/>});
			 */			
			enter: null,
    		
    		/**
			 *@desc 鼠标焦点离开输入框时触发
			 *@param {event} 事件
			 *@param {ui.newValue} 新修改的值 
			 *@param {ui.oldValue} 旧值
			 *@event
			 *@example
			 *<br/>$("XXX").textareafield("option", "change", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("textareafieldchange",function(event, ui){  <br/>} <br/>});
			 */
			change: null
          
        },

		/** @lends jazz.textareafield */
		
        /**
         * @desc 创建组件
         * @private
         */
        _create: function() {
        	this._super();
        	this.validatefield = this.input = $('<textarea id="'+this.compId+'_input" class="jazz-textarea-resize jazz-field-comp-input" type="text" name="'+this.options.name+'" autocomplete="off" ></textarea>')
        				.appendTo(this.inputFrame);
        },
        
        /**
         * @desc 初始化组件
         * @private
         */        
        _init: function(){
        	this._super();
        	
        	var $this = this;
            this.input.off('focus.textareafield blur.textareafield keyup.textareafield')
            .on('keyup.textareafield', function(e) {
            	if($this.options.maxlength){
	            	setTimeout(function(){
	            		var value = $this.input.val(), length = value.length;
	            		
	                    if(length > $this.options.maxlength) {
	                        $this.input.val(value.substr(0, $this.options.maxlength));
	                    }
	
	                    if($this.options.counter) {
	                        $this._updateCounter();
	                    }
	            	}, "300");
            	}
            })
            .on("focus.textareafield", function(){
            	if($(this).val()==$this.options.valuetip){
					$(this).val("");
					$(this).removeClass('jazz-field-comp-input-tip');
				}
            	
        		$this.inputValue = $this.input.val();
        		$this._event("enter", null);
        	})
        	.on("blur.textareafield", function(){
        		if($(this).val()=="" && !!$this.options.valuetip){
					$(this).val($this.options.valuetip);
					$(this).removeClass('jazz-field-comp-input-tip').addClass('jazz-field-comp-input-tip');
				}
        		
        		var ui = {
    				newValue: $this.input.val() || "",     //新值
    				oldValue: $this.inputValue || ""       //旧值
        		};
        		$this._event("change", null, ui);
        	});
            
            
            if(this.options.counter) {
                this._updateCounter();
            }
            
            //设置默认值
            if(this.options.defaultvalue){
    			this.setValue(this.options.defaultvalue, this.options.readonly);
            }else{
            	if(this.options.valuetip){
            		this.setValue(this.options.valuetip);
                	this.input.addClass("jazz-field-comp-input-tip");
            	}
            }
            
			//验证
			this._validator();
        },      
        
        /**
         * @desc 修改组件字符的数量
         * @private
         */
        _updateCounter: function() {
            var value = this.input.val(),
            length = value.length;

            if(this.options.counter) {
                var remaining = this.options.maxlength - length,
                remainingText = this.options.countertemplate.replace('{0}', remaining);

                this.options.counter.text(remainingText);
            }
        },
        
		/**
         * @desc 获得输入字符的数量
         * @example  $('XXX').textareafield('getCount');
         */        
        getCount: function(){
            var value = this.input.val();
            return value.length;       	
        }
		
    });
    
})(jQuery);(function($) {
	
	/**
	 * @version 1.0
	 * @name jazz.hiddenfield
	 * @description 隐藏域字段组件。
	 * @constructor
	 * @extends jazz.field
	 */
    $.widget("jazz.hiddenfield", $.jazz.field, {
        
    	options: /** @lends jazz.hiddenfield# */ {
    		
    		/**
			 *@type String
			 *@desc 组件类型
			 *@default hiddenfield
			 */
			vtype: 'hiddenfield',
			
			/**
			 *@type Number
			 *@desc 数字类型
			 *@default 延迟请求间隔时间
			 */
			delay: 300,
			
			/**
			 *@type Number
			 *@desc 数字类型
			 *@default 延迟间隔时间
			 */
			delaycount: 600000
				
    	},
    	
    	/** @lends jazz.hiddenfield */
    	
        /**
         * @desc 创建组件
         * @private
         */  
        _create: function() {
        	this._super();
        	this.input = $('<input type="hidden" id="'+this.compId+'_input" name="'+this.options.name+'" value="'+this.options.defaultvalue+'">')
        	             .appendTo(this.inputFrame);						
        },
    	
    	_init: function(){
    		this._super();
    	},
    	
        /**
         * @desc ajax请求函数
         * @return 返回请求响应的数据
         * @private
         * @example  this._ajax();
         */         
        _ajax: function(){
        	var $this = this;
        	var i = 1,delay = $this.options.delay ,delaycount = $this.options.delaycount;
			var timeout = setInterval(function(){
				var status = false;
            	data = G.getPageDataSetCache($this.options.dataurl,$this.options.dataurlparams);
            	data = $.extend(true,{},data);
                if(data && data.status === 'success'){
                	clearInterval(timeout);
                    var d = data["data"];
                    for(var p in d){
                    	d = d[p];
                    }
                    $this._callback(d);
                    status = true;
                }
                i++;
				if(status || delay*i > delaycount){
					clearInterval(timeout);
				}
			},delay);
        },
        
        /**
         * @desc 设置组件属性的值
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private 
         */
        _setOption: function(key, value){
        	this._super(key, value);
        }
    	
    });
})(jQuery);
(function($) {
	
	/**
	 * @version 1.0
	 * @name jazz.radiofield
	 * @description 表单元素的单选按钮。
	 * @constructor
	 * @extends jazz.hiddenfield
	 * @example $('XXX').radiofield();
	 */	
    
    $.widget("jazz.radiofield", $.jazz.hiddenfield, {
       
    	options: /** @lends jazz.radiofield# */ {
    		
    		/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default radiofield
    		 */
    		vtype: 'radiofield',
    		
    		/**
    		 *@type Array
    		 *@desc 获取数据项url地址
    		 *@default []
    		 *@example [{"checked": true, "text": "男", "value": "1"},{"text": "女", "value": "2"}] 
    		 */
    		dataurl: [],

    		/**
    		 *@type number
    		 *@desc 每一个数据项的宽度 
    		 *@default 100
    		 */    		
    		itemwidth: 100,
    		
            // callbacks
    		
    		/**
			 *@desc 当选择了某项时，触发itemselect事件  配合dataurl属性使用
			 *@param {event} 事件
			 *@param {value} 选中项的值 
			 *@event
			 *@example
			 *<br/>$("XXX").radiofield("option", "itemselect", function(event, value){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("radiofielditemselect",function(event, value){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… itemselect="XXX()"></div> 或 <div…… itemselect="XXX"></div>
			 */
			itemselect: null
    	},
    	
    	/** @lends jazz.radiofield */ 
    	
        /**
         * @desc 创建组件
         * @private
         */
        _create: function() {
        	//创建组件
        	this._super();
        },
        
        /**
         * @desc 初始化组件
         * @private
         */   
        _init: function(){
        	this._boxItems();
        	
        	this._super();
        	
        	this._bindEvents();   
        	
        	if(!!this.options.defaultvalue){
    			this.setValue(this.options.defaultvalue);
            }
        },
        
		/**
         * @desc 由field类调用，生成radio项
         * @private
         * @param {compId} 动态标记
         */
        _boxItems: function(){
        	this.inputFrame.empty();
        	var items = this.options.dataurl;
        	if(jazz.isIE(7) || jazz.isIE(6)){
        		items = jazz.stringToJson(items);
        	} 
        	if( !!items &&!(items instanceof Array)){
        		this._ajax(); 
        	}else{
        		var div = this._commonDom(items,this.compId);
        		this.inputFrame.append(div);
        	}
        },
        
    	/**
         * @desc 绑定事件
         * @private
		 * @example this._bindEvents();
         */ 
        _bindEvents: function() {
            var $this = this;
            if(!$this.options.disabled){
            	 this.parent.off('click.radiofield').on('click.radiofield', function(e) {
	               	 var target = e.target, $target = $(target);
	   				 if($target.is('span')){
	   					 var boxid = $target.attr("id");
	   	                 boxid = boxid.substring(0, boxid.length-5);
	   	                 var box = $('#'+boxid);
	   	                 if(!$this._isChecked(box)){
	                       	 $this._nocheckAllImg();
	   	                 }
	   	                 $this._toggle(box, $target,e);
	   				 }
	   				 if($target.is('label')){
		   			     var boxid = $target.attr("for");
		                 var box = $('#'+boxid), span = $('#'+boxid+'_span');
		                 if(!$this._isChecked(box)){
			               	 $this._nocheckAllImg();
			               	 $this._checkImg(span);
		                 }
	   					 $this._event("itemselect", e ,{checked: !$this._isChecked(box),text:box.attr("text"),value:box.val()});
	   				 }
               });
			}
        },        
        
        /**
         * @desc 生成数据项
		 * @private
         */
        _callback: function(data) {
        	if(!!data){
        		var div = this._commonDom(data,this.compId);
        		this.inputFrame.append(div);
        	}
        },
        
        /**
         * @desc 生成数据dom
         * @private
         */ 
        _commonDom: function(items,compId) {
			this.compIdspan = compId;
        	var name = this.options.name;
        	var div = "";
        	if(items && items.length>0){
        		var $this = this; this.checkItems = [];
        		var disabled = '';
				if($this.options.disabled){
					disabled = ' disabled="false" ';
				}
        		$.each(items, function(i, item){
        			var id = compId+'_radio_'+item.value;
        			$this.checkItems.push(id);
        			var chkedspan = " jazz-radio-nochecked " , chkedtext= "";
        			if(item.checked=="true"|| item.checked){
        				chkedspan = " jazz-radio-checked ";
        				chkedtext= " checked='true' ";
        			}
        			div = div + '<div index="'+i+'" class="jazz-field-comp2 jazz-checkbox-item" style="width:'+$this.options.itemwidth+'px;">'
        					+ '<div class="jazz-checkbox-hidden"><input type="radio" id="'+id+'" text="'+item.text+'" name="'+name+'" '+chkedtext+' value="'+item.value+'" '+disabled+'/></div>'
        					+ '<span id="'+id+'_span" class="jazz-checkbox '+chkedspan+'"></span>'
        					+ '<div style="padding-left: 25px"><label for="'+id+'" class="jazz-checkbox-label">'+item.text+'</label></div>'
        					+ '</div>';
        		});
        	}
        	return div;
        },
        
		/**
         * @desc 选中当前对象
		 * @param {box} radio对象
         * @private
		 */     
        _checked: function(box, spanObj){
    		box.prop('checked', true);
    		this._checkImg(spanObj);
        },        
        
		/**
         * @desc 选中当前对象
		 * @param {obj} radio对像中的span对象
         * @private
		 */ 
        _checkImg: function(obj){
        	obj.removeClass('jazz-radio-nochecked').addClass('jazz-radio-checked');
        },        
        
		/**
         * @desc 判断是否选中
		 * @param {box} radio对象
         * @private
		 * @example this._isChecked();
		 */ 
        _isChecked: function(box) {
            return box.prop('checked');
        },
        
		/**
         * @desc 取消当前对象
		 * @param {obj} radio对像中的span对象
         * @private
		 */ 
        _nocheckImg: function(obj){
        	obj.removeClass('jazz-radio-checked').addClass('jazz-radio-nochecked');
        },   

		/**
         * @desc 取消所有radio对象
         * @private
		 */ 
        _nocheckAllImg: function(){
        	for(var i=0, len=this.checkItems.length; i<len; i++){
        		var span = $('#'+this.checkItems[i]+'_span');
        		span.removeClass('jazz-radio-checked').addClass('jazz-radio-nochecked');
        	}         	
        },        
                
        /**
         * @desc 设置组件属性的值
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private 
         */
        _setOption: function(key, value){
        	switch(key){
	        	case 'disabled':
	        		if(value){
	        			this.parent.off('click.radiofield');
	        		}else{
	        			this._bindEvents(); 
	        		}
	        		break;	 
        	}
        	this._super(key, value);
        },   
        
		/**
         * @desc 切换是否被选中
		 * @param {box} radio对象
		 * @param {spanObj} radio对像中的span对象
         * @private
		 * @example this._toggle();
		 */  
        _toggle: function(box, spanObj, event) {
            if(!this._isChecked(box)) {
                this._checked(box, spanObj);
            } 
			this._event("itemselect", event ,{checked: this._isChecked(box),text:box.attr("text"),value:box.val()});
        },

		/**
         * @desc 解除勾选
		 * @param {box} radio对象
		 * @param {spanObj} radio对像中的span对象
         * @private
		 * @example this._unchecked();
		 */        
        _unchecked: function(box, spanObj) {
        	box.prop('checked', false);
        	this._nocheckImg(spanObj);        	
        },        

		/**
         * @desc 获取当前选中状态对象的文本值
		 * @return 所有选中文本值
		 * @example $('').radiofield('getText');
		 */
        getText: function(){
        	var name = this.options.name;
        	var chkvalue = new Array(); 
        	this.parent.find("input[name='"+name+"']:checked").each(function(){
        		chkvalue.push($(this).parent().siblings().text());
        	});
        	return chkvalue.join(',');
        },
        
		/**
         * @desc 获取当前选中状态对象的值
		 * @return 所有选中的值
		 * @example $('XXX').radiofield('getValue');
		 */
        getValue: function(){
        	var name = this.options.name;
        	var chkvalue = new Array(); 
        	this.parent.find("input[name='"+name+"']:checked").each(function(){
        		chkvalue.push($(this).val());	
        	});
        	return chkvalue.join(',');
        },        
        
		/**
         * @desc 取消当前选中状态对象
		 * @example $('XXX').radiofield('reset');
		 */
        reset: function(){
        	for(var i=0, len=this.checkItems.length; i<len; i++){
        		var obj = $('#'+this.checkItems[i]+'');
        		var span = $('#'+this.checkItems[i]+'_span');
        		span.removeClass('jazz-radio-checked').addClass('jazz-radio-nochecked');
        		obj.attr('checked', false);
        	}       	
        },  
        
        /**
         * @desc 动态添加组件下拉框中的内容
		 * @example $('XXX').radiofield('reload');
         */           
        reload: function() {
        	this._init();
        },        
        
		/**
         * @desc 设置当前状态对象选中
		 * @param {value} 选中对象的值
		 * @example $('XXX').radiofield('setValue','2');
		 */
        setValue: function(value){
        	this.reset();
        	var $this = this;
        	$.each(value.split(","),function(i,data){
        		$this.parent.find("input[value='"+data+"']").attr('checked','true');
        		var span = $('#'+$this.compIdspan+'_radio_'+data+'_span');
        		span.removeClass('jazz-radio-nochecked').addClass('jazz-radio-checked');
        	});
        }
        
    });
    
})(jQuery);(function($) {
	
	/**
	 * @version 1.0
	 * @name jazz.checkboxfield
	 * @description 表单元素的多选框。
	 * @constructor
	 * @extends jazz.hiddenfield
	 * @example $('XXX').checkboxfield();
	 */	

    $.widget("jazz.checkboxfield", $.jazz.hiddenfield, {
		
    	options: /** @lends jazz.checkboxfield# */ {
    		
    		/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default 'checkboxfield'
    		 */
    		vtype: 'checkboxfield',
    		
    		/**
    		 *@type Array
    		 *@desc 获取数据项url地址
    		 *@default []
    		 *@example [{"checked": true, "text": "男", "value": "1"},{"text": "女", "value": "2"}] 
    		 */
    		dataurl: [],

    		/**
    		 *@type number
    		 *@desc 每一个数据项的宽度 
    		 *@default 100
    		 */    		
    		itemwidth: 100,
    		
            //callbacks
    		/**
			 *@desc 当选择了某项时，触发itemselect事件  配合dataurl属性使用
			 *@param {event} 事件
			 *@param {value} 选中项的值 
			 *@event
			 *@example
			 *<br/>$("XXX").checkboxfield("option", "itemselect", function(event, value){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("checkboxfielditemselect",function(event, value){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… itemselect="XXX()"></div> 或 <div…… itemselect="XXX"></div>
			 */
			itemselect: null
    	},
    	
    	/** @lends jazz.checkboxfield */
    	
        /**
         * @desc 创建组件
         * @private
         */  
        _create: function() {
        	//创建组件
        	this._super();
        	this._boxItems(); 
        },
        
        /**
         * @desc 初始化组件
         * @private
         */ 
        _init: function(){
        	this._super();
        	
        	this._bindEvents();
        	
        	if(this.options.defaultvalue){
    			this.setValue(this.options.defaultvalue);
            }
        },
        
		/**
         * @desc 由field类调用，生成checkbox项
         * @private
         */
        _boxItems: function(){
        	this.inputFrame.empty();
        	var items = this.options.dataurl;
        	if(jazz.isIE(7) || jazz.isIE(6)){
        		items = jazz.stringToJson(items);
        	}
        	if( !!items &&!(items instanceof Array) ){
        		this._ajax(); 
        	}else{
        		var div = this._commonDom(items,this.compId);
        		this.inputFrame.append(div);
        	}
        },
    	
    	/**
         * @desc 绑定事件
         * @private
         */ 
        _bindEvents: function() {
            var $this = this;
            if(!$this.options.disabled){
            	 this.parent.off('click.checkboxfield').on('click.checkboxfield', function(e) {
	               	 var target = e.target, $target = $(target);
	   				 if($target.is('span')){
	   					  var boxid = $target.attr("id");
	   	                      boxid = boxid.substring(0, boxid.length-5);
	   	                  var boxObj = $('#'+boxid);
	   	                  $this._toggle(boxObj, $target ,e);			  
	   				 }
	   				 if($target.is('label')){
	   					  var boxid = $target.attr("for");
	   					  var box = $('#'+boxid), span = $('#'+boxid+'_span');
	   					  if(box.prop('checked')){
	   						  $this._nocheckImg(span);
	   					  }else{
	   						  $this._checkImg(span);
	   					  }
	   					  $this._event("itemselect", e ,{checked: !$this._isChecked(box),text:box.attr("text"),value:box.val()});
	   				 }
            	 });
			}
        },        
        
        /**
         * @desc 生成数据项
		 * @private
         */
        _callback: function(data) {
        	if(!!data){
        		var div = this._commonDom(data,this.compId);
        		this.inputFrame.append(div);
        	}
        },
        
		/**
         * @desc 选中当前对象
		 * @param {box} radio对象
         * @private
		 */   
        _checked: function(box, spanObj){
    		box.prop('checked', true);
    		this._checkImg(spanObj);
        },
        
		/**
         * @desc 选中当前对象
		 * @param {obj} radio对像中的span对象
         * @private
		 */ 
        _checkImg: function(obj){
        	obj.removeClass('jazz-checkbox-nochecked').addClass('jazz-checkbox-checked');
        },        
        
        /**
         * @desc 生成数据dom
         * @private
         */ 
        _commonDom: function(items,compId) {
			this.compIdspan = compId;
        	var name = this.options.name;
        	var div = "";
        	if(items && items.length>0){
        		var $this = this; this.checkItems = [];
        		var disabled = '';
				if($this.options.disabled){
					disabled = ' disabled="false" ';
				}
        		$.each(items, function(i, item){
        			var id = compId+'_box_'+item.value;
        			$this.checkItems.push(id);
        			var chkedspan = " jazz-checkbox-nochecked " , chkedtext= "";
        			if(item.checked=="true" || item.checked){
        				chkedspan = " jazz-checkbox-checked ";
        				chkedtext= " checked='true' ";
        			}
        			
        			div = div + '<div index="'+i+'" class="jazz-field-comp2 jazz-checkbox-item" style="width:'+$this.options.itemwidth+'px;">'
        					+ '<div class="jazz-checkbox-hidden"><input type="checkbox" id="'+id+'" text="'+item.text+'" name="'+name+'" '+chkedtext+' value="'+item.value+'" '+disabled+'/></div>'
        					+ '<span id="'+id+'_span" class="jazz-checkbox '+chkedspan+'"></span>'
        					+ '<div style="padding-left: 25px"><label for="'+id+'" class="jazz-checkbox-label">'+item.text+'</label></div>'
        					+ '</div>';
        		});
        	}
        	return div;
        },
        
		/**
         * @desc 判断是否选中
		 * @param {box} radio对象
         * @private
		 */ 
        _isChecked: function(box){
        	return box.prop('checked');
        },
        
		/**
         * @desc 取消当前对象
		 * @param {obj} radio对像中的span对象
         * @private
		 */
        _nocheckImg: function(obj){
        	obj.removeClass('jazz-checkbox-checked').addClass('jazz-checkbox-nochecked');
        },        
        
        /**
         * @desc 设置组件属性的值
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         */
        _setOption: function(key, value){
        	switch(key){
	        	case 'disabled':
	        		if(value){
	        			this.parent.off('click.checkboxfiel');
	        		}else{
	        			this._bindEvents(); 
	        		}
	        		break;	 
        	}
        	this._super(key, value);
        },     
        
		/**
         * @desc 切换是否被选中
		 * @param {box} radio对象
		 * @param {spanObj} radio对像中的span对象
         * @private
		 */ 
        _toggle: function(box, spanObj, event) {
            if(this._isChecked(box)) {
                this._unchecked(box, spanObj);
            } else {
                this._checked(box, spanObj);
            }
            
            this._event("itemselect", event ,{checked: this._isChecked(box),text:box.attr("text"),value:box.val()});
        },
        
		/**
         * @desc 解除勾选
		 * @param {box} radio对象
		 * @param {spanObj} radio对像中的span对象
         * @private
		 */ 
        _unchecked: function(box, spanObj) {
        	box.prop('checked', false);
        	this._nocheckImg(spanObj);        	
        },
        
		/**
         * @desc 获取当前选中状态对象的文本值
		 * @return 所有选中文本值
		 * @example $('XXX').checkboxfield('getText');
		 */
        getText: function(){
        	var name = this.options.name;
        	var chkvalue = new Array(); 
        	this.parent.find("input[name='"+name+"']:checked").each(function(){
        		chkvalue.push($(this).parent().siblings().text());
        	});
        	return chkvalue.join(',');
        },
        
        /**
         * @desc 获取当前选中状态对象的值
         * @return 所有选中的值
         * @example $('XXX').checkboxfield('getValue');
         */
        getValue: function(){
        	var name = this.options.name;
        	var chkvalue = new Array(); 
        	this.parent.find("input[name='"+name+"']:checked").each(function(){
        		chkvalue.push($(this).val());
        	});
        	return chkvalue.join(',');
        },
        
		/**
         * @desc 取消当前选中状态对象
		 * @example $('XXX').checkboxfield('reset');
		 */
        reset: function(){
        	for(var i=0, len=this.checkItems.length; i<len; i++){
        		var obj = $('#'+this.checkItems[i]+'');
        		var span = $('#'+this.checkItems[i]+'_span');
        		span.removeClass('jazz-checkbox-checked').addClass('jazz-checkbox-nochecked');
        		obj.attr('checked', false);
        	}
        },
        
        /**
         * @desc 动态添加组件下拉框中的内容
		 * @example $('XXX').radiofield('reload');
         */           
        reload: function() {
        	this._create();
        	this._init();
        },        
        
		/**
         * @desc 设置当前状态对象选中
		 * @param {value} 选中对象的值
		 * @example $('XXX').checkboxfield('setValue','2,4');
		 */
        setValue: function(value){
        	var $this = this;
        	if(!!value){
        		$.each(value.split(","),function(i,data){
            		$this.parent.find("input[value='"+data+"']").attr('checked','true');
            		var span = $('#'+$this.compIdspan+'_box_'+data+'_span');
            		span.removeClass('jazz-checkbox-nochecked').addClass('jazz-checkbox-checked');
            	});
        	}else{
        		$.each($this.checkItems,function(i,data){
            		$this.parent.find("input[id='"+data+"']").attr('checked','true');
            		var span = $('#'+data+'_span');
            		span.removeClass('jazz-checkbox-nochecked').addClass('jazz-checkbox-checked');
            	});
        	}
        }
        
    });
    
})(jQuery);
(function($) {

	/**
	 * @version 1.0
	 * @name jazz.passwordfield
	 * @description 基本的数字字段。
	 * @constructor
	 * @extends jazz.hiddenfield
	 * @example $('XXX').passwordfield();
	 */
    $.widget("jazz.passwordfield", $.jazz.hiddenfield, {
        
        options: /** @lends jazz.passwordfield# */ {
        	
    		/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default passwordfield
    		 */        	
        	vtype: 'passwordfield',
        	
    		/**
			 *@type String
			 *@desc 中等强度密码提示
			 *default '中'
			 */               
            goodlabel: '中',      	
        	
    		/**
			 *@type String
			 *@desc 逐行显示
			 */               
            inline: false, 	
        	
    		/**
			 *@type String
			 *@desc 提示信息
			 *@default ''
			 */           	
            promptlabel: jazz.config.i18n.password,
            
    		/**
			 *@type String
			 *@desc 高等强度密码提示
			 *@default '强'
			 */               
            stronglabel: '强',

    		/**
			 *@type String
			 *@desc 低等强度密码提示
			 *@default '弱'
			 */              
            weaklabel: '弱'
                        
        },
       
        /** @lends jazz.passwordfield */ 
        
        /**
         * @desc 创建组件
         * @private
         */         
        _create: function() {
        	//创建组件
        	this._super();
        	
        	if(!this.element.prop(':disabled')) {
                var panelMarkup = '<div class="jazz-password-panel jazz-state-highlight jazz-helper-hidden">';
                panelMarkup += '<div class="jazz-password-meter" style="background-position:0px 0px"></div>';
                panelMarkup += '<div class="jazz-password-info">' + this.options.promptlabel + '</div>';
                panelMarkup += '</div>';

                this.panel = $(panelMarkup).insertAfter(this.element);
                this.meter = this.panel.children('div.jazz-password-meter');
                this.infoText = this.panel.children('div.jazz-password-info');

                if(this.options.inline) {
                    this.panel.addClass('jazz-password-panel-inline');
                } else {
                    this.panel.addClass('jazz-password-panel-overlay').appendTo('body');
                }
            }
        	
        },
        
        /**
         * @desc 初始化组件
         * @private
         */                 
        _init: function(){
        	this._super();
        	
            this._bindEvents();
            
			//验证
			this._validator();
        },       
        
		/**
		 *@desc 提示信息显示位置
		 *@private
		 */         
        _align: function() {
            this.panel.css({
                left:'', 
                top:'',
                'z-index': ++jazz.zindex
            })
            .position({
                my: 'left top',
                at: 'right top',
                of: this.parent
            });
        },
        
    	/**
         * @desc 绑定事件
         * @private
         */     
        _bindEvents: function() {
            var $this = this;
            
            this.input.off('focus.passwordfield blur.passwordfield keyup.passwordfield').on('focus.passwordfield', function() {
                $this.showTip();
            })
            .on('blur.passwordfield', function() {
                $this.hideTip();
            })
            .on('keyup.passwordfield', function() {
                var value = $this.input.val(),
                label = null,
                meterPos = null;

                if(value.length === 0) {
                    label = $this.options.promptlabel;
                    meterPos = '0px 0px';
                }
                else {
                    var score = $this._testStrength($this.input.val());

                    if(score < 30) {
                        label = $this.options.weaklabel;
                        meterPos = '0px -10px';
                    }
                    else if(score >= 30 && score < 80) {
                        label = $this.options.goodlabel;
                        meterPos = '0px -20px';
                    } 
                    else if(score >= 80) {
                        label = $this.options.stronglabel;
                        meterPos = '0px -30px';
                    }
                }

                $this.meter.css('background-position', meterPos);
                $this.infoText.text(label);
            });

            if(!this.options.inline) {
                var resizeNS = 'resize.' + this.options.id;
                $(window).unbind(resizeNS).bind(resizeNS, function() {
                    if($this.panel.is(':visible')) {
                        $this._align();
                    }
                });
            }
        },

		/**
		 *@desc 规格化
		 *@param {x} 
		 *@param {y}
		 *@private
		 */         
        _normalize: function(x, y) {
            var diff = x - y;

            if(diff <= 0) {
                return x / y;
            }
            else {
                return 1 + 0.5 * (x / (x + y/4));
            }
        },
        
		/**
		 *@desc 效验密码的强弱
		 *@param {str} 输入的字符串
		 *@private
		 */         
        _testStrength: function(str) {
            var grade = 0, 
            val = 0, 
            $this = this;

            val = str.match('[0-9]');
            grade += $this._normalize(val ? val.length : 1/4, 1) * 25;

            val = str.match('[a-zA-Z]');
            grade += $this._normalize(val ? val.length : 1/2, 3) * 10;

            val = str.match('[!@#$%^&*?_~.,;=]');
            grade += $this._normalize(val ? val.length : 1/6, 1) * 35;

            val = str.match('[A-Z]');
            grade += $this._normalize(val ? val.length : 1/6, 1) * 30;

            grade *= str.length / 8;

            return grade > 100 ? 100 : grade;
        },

		/**
		 *@desc 隐藏提示
		 *@example $('XXX').passwordfield('hideTip');
		 */  		
        hideTip: function() {
            if(this.options.inline)
                this.panel.slideUp();
            else
                this.panel.fadeOut();
        },

		/**
		 *@desc 显示提示
		 *@example $('XXX').passwordfield('showTip');
		 */  
        showTip: function() {
            if(!this.options.inline) {
                this._align();

                this.panel.fadeIn();
            }
            else {
                this.panel.slideDown(); 
            }        
        }
        
    });
    
})(jQuery);(function($) {
	/**
	 * @version 1.0
	 * @name jazz.iconfield
	 * @description 选择输入组件的基类。
	 * @constructor
	 * @extends jazz.hiddenfield
	 */
    $.widget("jazz.iconfield", $.jazz.hiddenfield, {
    	
    	options: /** @lends jazz.iconfield# */ {

        	/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default textfield
    		 */
			vtype: 'iconfield'

    	},
    	
    	/** @lends jazz.iconfield */
    	
        /**
         * @desc 创建组件
         * @private
         */ 
        _create: function() {
        	//创建组件
        	this._super();
        	this.validatefield = this.inputtext = $('<input id="'+this.compId+'_text" class="jazz-field-comp-input" type="text" readonly autocomplete="off">').appendTo(this.inputFrame);
        },

        /**
         * @desc 初始化组件
         * @private
         */ 
		_init: function(){
			this._super();
		},
        
        /**
         * @desc 设置组件属性的值
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private 
         */
        _setOption: function(key, value){
        	this._super(key, value);
        }
    });
    
})(jQuery);(function($) {
	
	/**
	 * @version 1.0
	 * @name jazz.numberfield
	 * @description 基本的数字字段。
	 * @constructor
	 * @extends jazz.iconfield
	 */
    $.widget("jazz.numberfield", $.jazz.iconfield, {
    	
    	options: /** @lends jazz.numberfield# */ {
    		
    		/**
			 *@type String
			 *@desc 组件的效验类型
			 *@default 'numberfield'
			 */    		
    		vtype: 'numberfield',
            
            /**
			 *@type Number
			 *@desc 数值最大值
			 *@default 1.0
			 */
            max: null,
            
            /**
			 *@type Number
			 *@desc 数值最小值
			 *@default 1.0
			 */
            min: null,
            
            /**
			 *@type String
			 *@desc 数值前缀
			 *@default ''
			 */            
            prefix: '',
            
            /**
             *@type Number
             *@desc 数值间间隔的步长
             *@default 1.0
             */
            step: 1.0,
            
            /**
			 *@type String
			 *@desc 数值后缀
			 *@default ''
			 */            
            suffix: '',
            
            /**
			 *@type String
			 *@desc 输入框的值 
			 *@default null
			 */
    		oldvalue: null,  //（不写入API）
            
            // callbacks
    		
    		/**
			 *@desc 当文本框值变化时触发
			 *@param {event} 事件
			 *@param {ui.newvalue} 新修改的值
			 *@param {ui.oldvalue} 旧值
			 *@event
			 *@example
			 *初始化：
			 *<br/>$("#input_id").numberfield({change: function( event, ui ){  <br/>} <br/>});
			 *非初始化:
			 *<br/>$("#input_id").on("numberfieldchange",function( event, ui ){  <br/>} <br/>});
			 */
    		change: null
        },
        
        
        /** @lends jazz.numberfield */
        
        /**
         * @desc 创建组件
         * @private
         */  
        _create: function() {   
        	//创建组件
        	this._super();
        	this.parent.append('<a class="jazz-number-btn jazz-number-btn-up"><span id="'+this.compId+'_up" class="jazz-number-img jazz-number-upImg"></span></a>'
	                          +'<a class="jazz-number-btn jazz-number-btn-down"><span id="'+this.compId+'_down" class="jazz-number-img jazz-number-downImg"></span></a>');

			this.upArrows = $('#'+this.compId+'_up');
			this.downArrows = $('#'+this.compId+'_down');
			
			this.upArrows.hover(function() {
            }).focus(function() {
            	$(this).addClass('jazz-number-upImg2');
            }).blur(function() {
            	$(this).removeClass('jazz-number-upImg2');
            });
			this.downArrows.hover(function() {
            }).focus(function() {
            	$(this).addClass('jazz-number-downImg2');
            }).blur(function() {
            	$(this).removeClass('jazz-number-downImg2');
            });
        	
        },

        /**
         * @desc 初始化组件
         * @private
         */
        _init: function(){
        	this._super();
        	
        	this._initValue();
            this.options.step = this.options.step || 1;
            
            if(parseInt(this.options.step, 10) === 0) {
                this.options.precision = this.options.step.toString().split(/[,]|[.]/)[1].length;
            }
            
            this._bindEvents();
            
			//验证
			this._validator();            
        },
        
    	/**
         * @desc 绑定事件
         * @private
         */ 
        _bindEvents: function() {
            var $this = this;
            
            //visuals for spinner buttons
            this.parent.children('.jazz-number-btn').off('mouseout.number mouseup.number mousedown.number')
            	.on('mouseout.number', function() {
                    if($this.timer) {
                        window.clearInterval($this.timer);
                    }
                }).on('mouseup.number', function() {
                    window.clearInterval($this.timer);
                    $this.inputtext.focus();
                }).on('mousedown.number', function(e) {
                    var element = $(this),
                    dir = element.hasClass('jazz-number-btn-up') ? 1 : -1;

	                $this.inputtext.focus();
                    $this._repeat(null, dir);

                    e.preventDefault();
                });

            this.inputtext.keydown(function (e) {        
                var keyCode = $.ui.keyCode;

                switch(e.which) {         
                    case keyCode.UP:
                        $this._spin($this.options.step);
                    break;

                    case keyCode.DOWN:
                        $this._spin(-1 * $this.options.step);
                    break;

                    default:
                        //do nothing
                    break;
                }
            })
            .keyup(function () {
                $this._updateValue();              
            })
            .blur(function () {
                $this._format();
            })
            .focus(function () {
            	$this.options.oldvalue = $this.value;
                $this.inputtext.val($this.value);
            });

//            //mousewheel
//            this.element.bind('mousewheel', function(event, delta) {
//                if($this.element.is(':focus')) {
//                    if(delta > 0)
//                        $this._spin($this.options.step);
//                    else
//                        $this._spin(-1 * $this.options.step);
//
//                    return false;
//                }
//            });
        },

        /**
         * @desc 格式化值
		 * @private
         */
        _format: function() {
            var value = this.value;

            var newvalue;
            if(this.options.precision)
                newvalue = parseFloat(this._toFixed(value, this.options.precision));
            else
                newvalue = parseInt(value, 10);

            if((!!this.options.min || this.options.min===0) && newvalue < this.options.min) {
                newvalue = this.options.min;
            }

            if(!!this.options.max && newvalue > this.options.max) {
                newvalue = this.options.max;
            } 
          
            this.value = newvalue;
            
//            if(this.options.prefix)
//        	    newvalue = this.options.prefix + newvalue;
//
//            if(this.options.suffix)
//        	    newvalue = newvalue + this.options.suffix;   
          
            this.inputtext.val(newvalue);
                 
        },
        
        /**
         * @desc 初始值
		 * @private
         */
        _initValue: function() {
        	this.setValue(this.options.defaultvalue);
            var value = this.inputtext.val();

            if(value === '') {
                if(!!this.options.min || this.options.min===0)
                    this.value = this.options.min;
                else
                    this.value = 0;
            }
            else {
                if(this.options.prefix)
                    value = value.split(this.options.prefix)[1];

                if(this.options.suffix)
                    value = value.split(this.options.suffix)[0];

                if(this.options.step)
                    this.value = parseFloat(value);
                else
                    this.value = parseInt(value, 10);
            }
        },
        
        /**
         * @desc 加载值
		 * @param {interval}
		 * @param {dir}  
		 * @private
         */
        _repeat: function(interval, dir) {
            var $this = this, i = interval || 500;
            window.clearTimeout(this.timer);
            this.timer = window.setTimeout(function() {
                $this._repeat(40, dir);
            }, i);
            this._spin(this.options.step * dir);
        },
        
        /**
         * @desc 组件赋值
         * @param {step} 增长值
		 * @private
         */       
        _spin: function(step) {
            var newvalue,
                currentValue = this.value ? this.value : 0;
            if(this.options.precision)
                newvalue = parseFloat(this._toFixed(currentValue + step, this.options.precision));
            else
                newvalue = parseInt(currentValue + step, 10);
            if((!!this.options.min || this.options.min===0) && newvalue < this.options.min) {
                newvalue = this.options.min;
            }
            
            if(!!this.options.max && newvalue > this.options.max) {
                newvalue = this.options.max;
            }

            this.inputtext.val(newvalue);
            this.value = newvalue;
            
    		
            this.options.oldvalue = currentValue;
    		this._triggerChange();
        },
        
        /**
         * @desc 计算精度值
         * @param {value} 现有值
         * @param {precision}
         * @return 返回计算后的精度值
		 * @private
         */        
        _toFixed: function (value, precision) {
            var power = Math.pow(10, precision||0);
            return String(Math.round(value * power) / power);
        },

        /**
         * @desc change事件
         * @private
         */		
		_triggerChange: function(){
			var newvalue = this.value;
			var oldvalue = this.options.oldvalue;
			this.options.oldvalue = newvalue;
			var ui = {
				newvalue: newvalue,                   //新值
				oldvalue: oldvalue                    //旧值
			};
			this._trigger('change', null, ui);			
		},
                
        /**
         * @desc 更新值
		 * @private
         */
        _updateValue: function() {
            var value = this.inputtext.val();

            if(value === '') {
                if(!!this.options.min || this.options.min===0)
                    this.value = this.options.min;
                else
                    this.value = 0;
            }
            else {
                if(this.options.step)
                    value = parseFloat(value);
                else
                    value = parseInt(value, 10);

                if(!isNaN(value)) {
                    this.value = value;
                }

            }
            
        },
		
		/**
		 * @desc 获取当前选中状态对象的值
		 * @return 所有选中的值
		 * @example $('XXX').numberfield('getValue');
		 */    
		getValue: function(){
			return this.inputtext.val();
		},
		
		/**
		 * @desc 获取当前选中状态对象的值
		 * @return 所有选中的值
		 * @example $('XXX').numberfield('getValue');
		 */    
		getText: function(){
			return this.getValue();
		},

		/**
         * @desc 设置元素值  
         * @param {value} 设置的值
		 * @example $('XXX').numberfield('setValue',value);
         */
		setValue: function(value) {
			this.inputtext.val(value);
			this.options.oldvalue = Number(value);
			this.value = Number(value);
		}
    });
})(jQuery);(function($) {
	/**
	 * @version 1.0
	 * @name jazz.dropdownfield
	 * @description 选择下拉组件的基类。
	 * @constructor
	 * @extends jazz.iconfield
	 */
    $.widget("jazz.dropdownfield", $.jazz.iconfield, {
    	
    	options: /** @lends jazz.dropdownfield# */ {

        	/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default textfield
    		 */
			vtype: 'dropdownfield',
				
			/**
			 *@type String
			 *@desc 空白行，显示的默认文本内容 
			 *@default '请选择……'
			 */	    		
    		blankvalue: jazz.config.fieldBlankText,
    		
        	/**
			 *@type Number
			 *@desc 滚动条显示高度
			 *@default 200
			 */         
            downheight: 200,
            
            /**
             *@type String
             *@desc 请求数据的url地址
             *@default null
             */      
            dataurl: null,
            
            /**
             *@type Object
             *@desc 请求数据地址对应的参数 {}
             *@default null
             *@example {"key1":"value1", "key2": "value2"}
             */            
            dataurlparams: null,
            
        	/**
        	 *@type Boolean
        	 *@desc 是否是可读字段 true只读  false非只读
        	 *@default true
        	 */
        	readonly: true
    	},
    	
    	/** @lends jazz.dropdownfield */
    	
        /**
         * @desc 创建组件
         * @private
         */ 
        _create: function() {
        	//创建组件
        	this._super();
        	this.parent.append('<span id="'+this.compId+'_ar" class="jazz-field-comp-ar"></span>');
        	this.trigger = $('#'+this.compId+'_ar');
        	if(this.options.vtype=='datefield'){
        		this.trigger.removeClass("jazz-field-comp-ar").addClass("jazz-field-comp-date");
        		this.inputtext.removeAttr("readonly");
        	}else if(this.options.vtype=='autocompletecomboxfield'){
        		this.trigger.removeClass("jazz-field-comp-ar");
        		this.inputtext.removeAttr("readonly");
        	}
        	this.datastatus = false;
        },

        /**
         * @desc 初始化组件
         * @private
         */ 
		_init: function(){
			this._super();
        	this.oldchoices = "";
		},

		/**
         * @desc 调整下拉列表的展现位置
		 * @private
         */
        _alignPanel: function() {
            this.panel.css({ 
            	width: this.parent.width(), 
            	'z-index': ++jazz.zindex})
            .position({
                my: 'left top',
                at: 'left bottom',
                of: this.parent,
                collision:"flipfit"
            });      	
        },

		/**
         * @desc 绑定事件
		 * @private
         */         
        _bindConstantEvents: function(vtype,length) {
            var $this = this;
            this.parent.off('click').on('click', function(e) {
            	$this._event("enter", e);
            	var target = e.target, $target = $(target);
			    if($target.is('span') || target==$this.inputtext.get(0)){
			    	if(vtype== 'comboxfield'){
						$this._updateSelectItemStyle();
					}
					$this._showDropdown(vtype,length);
			    }
            });
        },        
				
		/**
         * @desc 生成panel
		 * @private
         */				
		_createDownpanel: function(vtype){
			var name = this.options.name;
            if(!name){
            	name = this.options.id;
            }
            var vtype = this.options.vtype;
			this.panel = $('<div name="dropdownpanel_'+name+'" vtype="'+vtype+'" class="jazz-dropdown-panel jazz-widget-content jazz-helper-hidden"/>').appendTo(document.body);
			this.itemsWrapper = $('<div class="jazz-dropdown-wrapper" />').appendTo(this.panel);
			if(vtype== 'comboxtreefield'){
				$('<div name="reset_comboxtree_'+name+'" class="jazz-comboxtree-reset">请选择</div>').appendTo(this.panel);
				this.resetcomboxtree = $("div[name='reset_comboxtree_"+name+"']");
				this.ulId = "zTree_"+name;
				this.itemsContainer = $('<ul id="'+this.ulId+'" class="ztree" style="margin-top:0; width:auto;"></ul>').appendTo(this.panel);
			}else if(vtype== 'datefield'){
				this.itemsContainer = this.itemsWrapper;
			}else{
				this.itemsContainer = $('<ul class="jazz-dropdown-list jazz-helper-reset"></ul>').appendTo(this.itemsWrapper);
			}    
		},

        /**
         * @desc 获取当前选中的数据
         * @private 
         * @returns {}
         */
        _getData: function(){
        	var data = {};
        	return data;
        },		

		/**
         * @desc 生成提示信息panel
		 * @private
         */				
		_hideDropdown: function(){
			this.panel.hide();
		},

		/**
         * @desc 生成提示信息panel
		 * @private
         */				
		_showDropdown: function(vtype,length){
			this._itemScroll();  
			if(length==0){
				this.panel.hide();
			}else{
				this.panel.css({position:"absolute",left:"0", top:"0"});
				if(this.options.effect)
	                this.panel.show(this.options.effect, {}, this.options.effectSpeed);
	            else
	                this.panel.show();
			}

			this._alignPanel();

			if(vtype == 'comboxfield'){
				this._dimensionList();
			}   
		},
        
        /**
         * @desc 设置组件属性的值
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         */
        _setOption: function(key, value){
        	this._super(key, value);
        },
        
		/**
         * @desc 列表框是否显示滚动条
		 * @private
		 * @example this._itemScroll();
         */        
        _itemScroll: function(){
        	 //当下拉组件的列表高度大于设定的高度时，显示滚动条
            var scrollh = this.options.downheight;
            if((scrollh+"").indexOf('px')>-1)  scrollh = scrollh.substring(0, scrollh.indexOf('px'));  
            
            if(this.options.downheight && this.panel.outerHeight() > scrollh) {
                this.itemsWrapper.height(scrollh);
                this.panel.height(scrollh);
            }        	
        }
    });
    
})(jQuery);(function($){
	
	/**
	 * @version 1.0
	 * @name jazz.comboxfield
	 * @description 表单元素的选择填报类。
	 * @constructor
	 * @extends jazz.dropdownfield
	 * @requires
	 * @example $('XXX').comboxfield();
	 */

    $.widget("jazz.comboxfield", $.jazz.dropdownfield, {
        
        options: /** @lends jazz.comboxfield# */ {
			
        	/**
			 *@type String
			 *@desc 组件类型
			 *@default 'comboxfield'
			 */        	        	
        	vtype: 'comboxfield',
        	
        	/**
        	 *@type Boolean
        	 *@desc 是否区分字母的大小写过滤  true 区分大小写
        	 *@default false
        	 */             
        	casesensitive: false,

            /**
             *@type Function
             *@desc 自定义combox下拉列表展现内容
             *@default null
             *@example
             *datarender: function(data){var a = "<div>"+data["text"]+"***"+data["value"]+"</div>" return a;}
             */            
            datarender: null,
            
            /**
			 *@type Boolean
			 *@desc 是否进行过滤
			 *@default false
			 */            
            filterable: false,
            
			/**
			 *@type Boolean
			 *@desc 过滤模式  startsWith  contains   endsWith   custom
			 *@default 'contains'
			 */            
            filtermatchmode: 'contains',
            
			/**
			 *@type Function
			 *@desc 自定义过滤函数
			 *@default null
			 *@private
			 */                
            filterfunction: null,   //目前没有实现， 以后实现 filtermatchmode: custom  0.5 以后版本在加
			
            /**
			 *@type String
			 *@desc 是否多选 true是复选，false是单选
			 *@default 'true'
			 */            
            multiple: false,
            
            
            /**
			 *@type String
			 *@desc 输入框是否可以编辑
			 *@default true
			 */            
            editor: false,
			
            // callbacks
			
    		/**
			 *@desc 鼠标焦点进入输入框时触发
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").comboxfield("option", "enter", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("comboxfieldenter",function(event, ui){  <br/>} <br/>});
			 */			
			enter: null,
    		
    		/**
			 *@desc 鼠标焦点离开输入框时触发
			 *@param {event} 事件
			 *@param {ui.newValue} 新修改的值 
			 *@param {ui.oldValue} 旧值
			 *@event
			 *@example
			 *<br/>$("XXX").comboxfield("option", "change", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("comboxfieldchange",function(event, ui){  <br/>} <br/>});
			 */
			change: null,
    		
    		/**
			 *@desc 当选择了某项时，触发itemselect事件  配合dataurl属性使用
			 *@param {event} 事件
			 *@param {value} 选中项的值 
			 *@event
			 *@example
			 *<br/>$("XXX").comboxfield("option", "itemselect", function(event, value){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("comboxfielditemselect",function(event, value){  <br/>} <br/>});
			 */
			itemselect: null
            
        },

		/** @lends jazz.comboxfield */
	
        /**
         * @desc 创建组件
         * @private
         */ 
        _create: function() {
        	//创建组件
        	this._super();
        	this._createDownpanel(this.options.vtype);
        }, 
        
        /**
         * @desc 初始化组件
         * @private
         */ 
        _init: function() {
        	this._super();
        	
        	this.choices = "";
            this.iData = [];

            //查询
            if(this.options.filterable) {
                this.filterContainer = $('<div class="jazz-dropdown-filter"></div>').prependTo(this.panel);
                this.filterInput = $('<input type="text" autocomplete="off" class="jazz-field-comp-input">')
                				.appendTo(this.filterContainer);
                this.filterContainer.append('<span class="jazz-dropdown-search"></span>');
            }
            
        	this.inputtext.val('');
        	this.input.val('');
        	
            if(typeof(this.options.dataurl) == 'object' && jazz.isArray(this.options.dataurl)){
            	this.iData = this.options.dataurl;
            	this._intItems();
			}else{
				this._ajax();
			}
            
            if(!!this.options.defaultvalue){
    			this.setValue(this.options.defaultvalue);
            }else{
            	if(this.options.vtype=='comboxfield'){
            		this.setText(this.options.blankvalue);
            	}
            }
            
            var $this = this;
            if(this.options.editor){
            	this.inputtext.removeAttr("readonly");
                /*this._setupFilterMatcher();

                this.inputtext.off('keyup.comboxfield').on('keyup.comboxfield', function() {
                    var value = $(this).val();
                	setTimeout(function(){
                    	$this._filter(value);
                    },200);
                });*/
            }
        },
        
		/**
         * @desc 绑定事件
		 * @private
         */         
        _bindEvents: function() {
            var $this = this;

            this.items.filter(':not(.jazz-state-disabled)').each(function(i, item) { //所有可用<li>
                $this._bindItemEvents($(item));  //为每个<li>绑定事件
            });
            
            $this.panel.hover(function() {
                $this.parent.toggleClass('jazz-field-comp-hover');
                $this.trigger.toggleClass('jazz-field-comp-ar2');
            });

            //绑定键盘事件
            this._bindKeyEvents();

            if(this.options.filterable) {
                this._setupFilterMatcher();

                this.filterInput.off('keyup.comboxfield').on('keyup.comboxfield', function() {
                    $this._filter($(this).val());
                });
            }
        },
        
		/**
         * @desc 绑定数据项事件
         * @param {item} 数据列表中数据项
		 * @private
         */             
        _bindItemEvents: function(item) {
            var $this = this;
            
            if(this.options.vtype == 'autocompletecomboxfield'){
            	var text = item.html(),
    			re = new RegExp(jazz.escapeRegExp($this.query), 'gi'),
    			highlighedText = text.replace(re, '<span class="jazz-textfield-query">$&</span>');
    			item.html(highlighedText);
            }
            
            item.off('mouseover.comboxfield mouseout.comboxfield click.comboxfield').on('mouseover.comboxfield', function() {
                var el = $(this);
                if(!el.hasClass('jazz-state-highlight'))
                    $(this).addClass('jazz-state-hover');
            }).on('mouseout.comboxfield', function() {
                $(this).removeClass('jazz-state-hover');
            }).on('click.comboxfield', function(event) {
            	var itemdata = {};
            	itemdata.text = item.data('text');
            	itemdata.value = item.data('value');
            	
				if($this.options.multiple){
					//$this.oldchoices = $this.choices;
					//update selected index info
					if($this.choices.indexOf(item.index()) > -1){
						//有被选中的记录
						$this.choices = $this.choices.replace(
								new RegExp("("+item.index()+",?)+", "g"), "");
					}else{
						$this.choices += "," + item.index();
					}
					//update item selected style
					$this._updateSelectItemStyle();
					
					var label = $this._getSelectedItem("text");
					$this.setText(label);
					
					$this._event("itemselect", event ,itemdata);
				}else{
					$this.choices = item.index()+"";
					//update item selected style
					$this._updateSelectItemStyle();
	                $this.setText(item.data('text'));
	                $this._hideDropdown();
	                
	                $this._event("itemselect", event ,itemdata);
	                var data = $this._getData();
	            	$this._event("change", event, data);
	            	$this.oldchoices = item.data('value');
				}
            });
        },
        
		/**
         * @desc 绑定键盘事件
		 * @private 
		 * @example this._bindKeyEvents();
         */           
        _bindKeyEvents: function() {
        	
        },
                
        /**
         * @desc 生成数据项
		 * @private
         */
        _callback: function(data) {
        	if(!!data){
        		this.iData = data;
        		this._intItems();
        	}
        },

		/**
         * @desc 包含内容过滤
         * @param {value} 实际值
         * @param {filter} 过滤值
		 * @private
         */         
        _containsFilter: function(value, filter) {
            return value.indexOf(filter) !== -1;
        },

		/**
         * @desc 计算下拉列表查询框的宽度
		 * @private
         */
        _dimensionList: function() {
            if(this.options.filterable) {
                this.filterInput.width(this.filterContainer.width() - 20);
            }
        },

		/**
         * @desc 从后匹配过滤
         * @param {value} 实际值
         * @param {filter} 过滤值
		 * @private
         */  
        _endsWithFilter: function(value, filter) {
            return value.indexOf(filter, value.length - filter.length) !== -1;
        },      

		/**
         * @desc 过滤
         * @param {value} 过滤值
		 * @private
         */        
        _filter: function(value) {
            this.initialHeight = this.initialHeight||this.itemsWrapper.height();        
            var filterValue = this.options.casesensitive ? $.trim(value) : $.trim(value).toLowerCase();
            for(var i = 0; i < this.iData.length; i++) {
                var option = this.iData[i],
                itemLabel = this.options.casesensitive ? option['text'] : option['text'].toLowerCase(),
                item = this.items.eq(i);

                if(this.filterMatcher(itemLabel, filterValue))
                    item.show();
                else
                    item.hide();
            }
            
            var h = this.itemsContainer.height();
            if(h < this.initialHeight) {
                this.itemsWrapper.css('height', 'auto');
                this.panel.height('auto');
            }
            else {
                this.itemsWrapper.height(this.initialHeight);
            }
        },

        /**
         * @desc 获取当前选中的列表项
		 * @private
         */        
        _getActiveItem: function() {
            return this.items.filter('.jazz-state-highlight');
        },
        
		/**
         * @desc 生成数据项
		 * @private
         */
        _generateItems: function(state) {
        	this.itemsContainer.empty();
        	var h = this.options.multiple;
        	if(this.options.vtype=='autocompletecomboxfield'){
                var data = [],emptyQuery = ($.trim(this.query) === '');
        		for(var i = 0 ; i < this.iData.length; i++) {
        			var option = this.iData[i],
                    optionValue = option['value'],
                    optionLabel = option['text'];
                    if(!this.options.casesensitive) {
                        itemLabel = optionLabel.toLowerCase();
                    }
                    if(emptyQuery && this.options.minquerylength == 0){
    					data.push(option);
    				}else{
    					if((this.filterMatcher(itemLabel, $.trim(this.query))) && (this.query.length >= this.options.minquerylength)) {
    						data.push(option);
    					}
    				}
                }
        		if(!this.panel.is(":visible") && data.length>0 && this.datastatus){
        			this._showDropdown(this.options.vtype,data.length);
            	}
            	if(data.length==0){
            		this.panel.hide();
            	}
            	this.iData = data;
        	}
        	if(!h && state && (this.options.vtype != 'autocompletecomboxfield')){
        		if(this.iData.length>0){
                	this.itemsContainer.append('<li data-value="" data-text="' + this.options.blankvalue + '" class="jazz-dropdown-list-item" >' + this.options.blankvalue + '</li>');
        		}
        	}
            for(var i = 0; i < this.iData.length; i++) {
                var option = this.iData[i],
                optionValue = option['value'],
                optionLabel = option['text'],
                content = this.options.datarender ? this.options.datarender.call(this, option) : optionLabel;
                
                //添加复选空，如果是多选的话，但是样式上不好看，暂时去掉 
                //update by lixy 2014-8-13
                /*if(h){
                	content = '<input class="jazz-combox-checkbox" type="checkbox" />' + content;
                }*/
            	this.itemsContainer.append('<li data-value="' + optionValue + '" data-text="' + optionLabel + '" class="jazz-dropdown-list-item" >' + content + '</li>');
            }
            
        	if(!h && state && (this.options.vtype != 'autocompletecomboxfield')){
        		if(this.iData.length>0){
        			this.iData.unshift({value:'',text:this.options.blankvalue}); 
        		}
        	}
            //this.itemsContainer.find('input.jazz-combox-checkbox').checkboxfield();
            //this.items所有<li>元素的对象集合
            this.items = this.itemsContainer.children('.jazz-dropdown-list-item');
            
            this.datastatus = true;
            
            this._itemScroll();
        },
        
		/**
         * @desc 获取当前选中状态对象的索引
		 * @param {value} 对象值
		 * @return 选中对象的索引
		 * @private 
		 */  
        _getDataIndex: function(value){
        	var $this = this,
        		iData = $this.iData;
        	for(var i=0, len=iData.length; i<len; i++){
        		if(iData[i]['value'] == value){
        			return i;
        		}
        	}
        },
        
        /**
         * @desc 获取当前选中的数据
         * @private 
         * @returns {Array}
         */
        _getSelectedData: function(){
        	var $this = this,
    		iData = $this.iData,
    		iSel = $this.oldchoices.toString().split(","),
    		oldData = [];
	    	for(var i=0, len=iSel.length; i<len; i++){
	    		var m = this._getDataIndex(iSel[i]);
	    		if(iSel[i] && iSel[i] !== 'undefined'){
	    			oldData.push(iData[m]);
	    		}
	    	}
        	return oldData;
        },
        
        
        /**
         * @desc 获取当前选中的数据
         * @private 
         * @returns {}
         */
        _getData: function(){
        	var newValue = this._getSelectedItem("value");
        	var newText = this._getSelectedItem("text");
        	var oldData = this._getSelectedData(); 
        	var oldValueData = [] ,oldTextData = [];
        	if(oldData.length>0){
        		$.each(oldData,function(i,data){
            		oldValueData.push(data.value); 
            		oldTextData.push(data.text);  
            	});
        	}
        	var oldValue = oldValueData.join(",");
        	var oldText = oldTextData.join(",");
        	var data = {};
        	data.newValue = newValue,data.newText = newText;
        	data.oldValue = oldValue,data.oldText = oldText;
        	return data;
        },
            
        /**
         * @desc 根据当前选中的条目，组织显示字符串
         * @params {type} 类型   String获取数据 text 获得显示文本  value 获得实际值 空 获得数据完整对象
         * @private
         * @returns
         */
        _getSelectedItem: function(type){
        	var $this = this,
        		iData = $this.iData,
        		iSel = $this.choices.split(","),
        		iSelLabel = [];
        	for(var i=0, len=iSel.length; i<len; i++){
        		if(iSel[i] && iSel[i] !== 'undefined'){
        			if(type){
        				iSelLabel.push(iData[iSel[i]][type]);        			        				
        			}else{
        				iSelLabel.push(iData[iSel[i]]);
        			}
        		}
        	}
        	
        	return iSelLabel.join(",");
        },

		/**
         * @desc 生成数据项
		 * @private
		 * @example this._intItems();
         */
        _intItems: function() {
        	var rule = this.options.rule;
        	var regMsg = this.options.msg;  //当采用正则验证时，需要页面传入
    		var val = this.inputtext.val()  == this.options.blankvalue ? "" : this.inputtext.val();
    		var objstate = jazz_validator.doValidator(val, rule, regMsg);
    		var state = true;
    		if(!!objstate){
    			state = objstate.state;
    		}
			//生成数据项
        	this._generateItems(state);

            //回显示高亮项，用于下拉列表选中项元素的高亮度回显
        	this._updateSelectItemStyle();
        	this._bindEvents();
        	if(!this.options.disabled){
            	this._bindConstantEvents(this.options.vtype,this.iData.length);  
    		}
        	
        	this._validator();
        },

        
        /**
		 * @desc 判断是否存在值
		 * @param {value} 对象的值
		 * @private
         */
        _isSelected: function(value){
        	var $this = this,
        		iChoices = $this.choices;
        	return (iChoices.indexOf(value) == -1 ) ? false : true;
        },
        
        /**
         * @desc 重新加载数据
		 * @private
         */
        _loadData: function(){
        	//this._init();
        	if(typeof(this.options.dataurl) == 'object' && jazz.isArray(this.options.dataurl)){
            	this.iData = this.options.dataurl;
            	this._intItems();
			}else{
				this._ajax();
			}
        },

        /**
         * @desc 得到列表框中被选中元素的索引值
         * @param {item} 选中元素对象
		 * @private
         */           
        _resolveItemIndex: function(item) {
             return item.index();      
        },
        
        /**
         * @desc 删除数据
         * @param {value} 对象key值
		 * @private 
         */ 
        _removeDatabyKey: function(value){
        	var $this = this,
        		iData = $this.iData;
        	for(var i=0,len=iData.length; i<len; i++){
        		if(iData[i]['value'] == value){
        			iData.splice(i, 1);
        		}
        	}
        },
        
		/**
         * @desc 设置过滤的匹配机制
		 * @private
         */        
        _setupFilterMatcher: function() {
            this.filterMatchers = {
                'startsWith': this._startsWithFilter,
                'contains': this._containsFilter,
                'endsWith': this._endsWithFilter,
                'custom': this.options.filterfunction
            };

            this.filterMatcher = this.filterMatchers[this.options.filtermatchmode];
        },

		/**
         * @desc 从前匹配过滤
         * @param {value} 实际值
         * @param {filter} 过滤值 
		 * @private
         */          
        _startsWithFilter: function(value, filter) {
            return value.indexOf(filter) === 0;
        },
        
		/**
         * @desc 查询
         * @param {text} 查询内容 
         * @param {start} 开始位置
         * @param {end}  结束位置
		 * @private
         */        
        _search: function(text, start, end) { 
            for(var i = start; i  < end; i++) {
                var option = this.iData[i];

                if(option['text'].indexOf(text) === 0) {
                    return this.items.eq(i);
                }
            }

            return null;
        },

		/**
         * @desc 设置字段值
         * @param {value} 设置的数值
         * @param {trigger} 是否触发事件
		 * @private 
         */         
        _selectValue : function(value, trigger) {  //0.5版本不加入API， 可能功能需要完善
        	var $this = this,
            	index = $this._getDataIndex(value);
            if($this.options.multiple){
				if($this.choices.indexOf(index) > -1){
					$this.choices = $this.choices.replace(
							new RegExp("("+index+",?)+", "g"), "");
				}else{
					$this.choices += "," + index;
				}
			}else{
				$this.choices = index+"";
			}
            $this._updateSelectItemStyle();
            var label = $this._getSelectedItem("text");
			$this.setText(label);
			//$this._selectItem($(this), true, trigger);
        	//var selectedOption = $this.iData[index]
        	//this._triggerChange(selectedOption);
			//jazz.setLastFocu($this.input);
        },

        /**
		 * @desc 设置当前状态对象选中
		 * @param {value} 选中对象的值
		 * @private 
         */
		_seSontData: function(value,callback) { 
			var $this = this;
			if(!$this._isSelected(value)){
				$this._selectValue(value,true);
				if(!!callback && $.isFunction(callback)){
					var index = $this._getDataIndex(value);
					if(index>-1){
						var obj = $this.iData[index];
						callback.call($this,null,$this._triggerChangeData(obj));
					}
				}
			}else{
				$this.options.defaultvalue = value;
			}
			$this.valueField = value;
        },
		
        /**
         * @desc 设置组件属性的值
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private 
         */
        _setOption: function(key, value){
        	switch(key){
	        	case 'dataurlparams':
		       		this.options.dataurlparams=value;
		       		break;
	        	case 'dataurl':
	        		 this.options.dataurl=value;
	        		 break;
	        	case 'disabled':
	        		if(value){
	        			this.parent.off('click');
	        		}else{
	        			this._bindConstantEvents(this.options.vtype,this.iData.length);
	        		}
	        		break;	 
        	}
        	this._super(key, value);
        },
        
		/**
         * @desc 触发事件后回调数据
         * @param {obj} 下拉列表选中项的对象
         * @param {edited} 判断是否为录入改变值还是选择改变值 true录入改变值 
		 * @private
         */          
        _triggerChangeData: function(obj, edited){
        	var _newText = obj['text'], 
        		_newValue = obj['value']; 
        	if(edited) { //如果可编辑，显示字段与值字段相同
        		_newText = _newValue;
        	}
			var data = {
				newValue: _newValue,           //新值
				newText: _newText,
				oldValue: this.valueField,     //旧值
				oldText: this.displayField,
				object: this.element
			};
        	this.valueField = _newValue;
        	this.displayField = _newText;		
			return data;
        },        
        
		/**
         * @desc 文本框内的键盘按下键时触发
		 * @private 
         */          
        _triggerKeyup: function(obj) {
        	var param = {kText: '', kValue: ''};
		    var data = {
				newValue: obj['value'],        //新值
				newText: obj['value'],
				oldValue: param.kValue,        //旧值
				oldText: param.kText					
			};        	
		    param.kValue = obj['value'];
		    param.kText = obj['value'];			    
        	this._trigger('keyup', null, data);
        },

        /**
         * @desc 更新下拉列表中当前选中的样式
         * @private
         */
        _updateSelectItemStyle: function(){
        	var $this = this,
    		iSel = $this.choices.split(","),
        	iChildren = $this.itemsContainer.children();
        	iChildren.removeClass("jazz-state-highlight");
	    	for(var i=0, len=iSel.length; i<len; i++){
	    		if(iSel[i]){
	    			$(iChildren.get(iSel[i])).addClass("jazz-state-highlight");      			
	    		}
	    	}
        },
                
        /**
         * @desc 动态添加组件下拉框中的内容
         * @param {text} 文本内容
         * @param {value} 文本对应的数值
		 * @example $('div_id').comboxfield('addOption', 'text', 'value');
         */           
        addOption: function(text, value) {            
            var item = $('<li data-value="' + value + '" data-text="' + text + '" class="jazz-dropdown-list-item">' + text + '</li>');
            this._bindItemEvents(item);
            item.appendTo(this.itemsContainer);
            this.items.push(item[0]);
            this.iData.push({'text': text, 'value': value});
        },
        
		/**
         * @desc 获取当前选中状态对象的文本值
		 * @return 选中的文本内容
		 * @example $('XXX').comboxfield('getText');
		 */      
        getText: function(){
        	return this._getSelectedItem('text');
        },
        
		/**
         * @desc 获取当前选中状态对象的值
		 * @return 所有选中的值
		 * @example $('XXX').comboxfield('getValue');
		 */        
        getValue: function() {   
        	return this._getSelectedItem('value');
        },
        
        /**
         * @desc 动态隐藏组件下拉框中的内容
         * @param {value} 文本对应的数值
		 * @example $('XXX').comboxfield('hideOption','value');
         */           
        hideOption: function(value) {   
        	this.itemsContainer.children("[data-value='"+value+"']").css("display","none");
        },
        
        /**
         * @desc 动态添加组件下拉框中的内容
         * @param {text} 文本内容
         * @param {value} 文本对应的数值
         */           
        putOptions: function(jsonData) { 
        	var $this = this;
        	if(!!jsonData){
        		$.each(jsonData.data,function(i,jsondata){
                    var _label = $.trim(jsondata.text), _value = $.trim(jsondata.value);
                    $this.addOption(_label,_value);
                });	
        	}
        },

        /**
         * @desc 动态添加组件下拉框中的内容
         * @param {text} 文本内容
         * @param {value} 文本对应的数值
		 * @example $('XXX').comboxfield('reload');
         */           
        reload: function() {
        	this._loadData();
        },
                
		/**
         * @desc 取消当前选中状态对象
		 * @example $('XXX').comboxfield('reset');
		 */
        reset: function() {
        	this.inputtext.val('');
        	this.setText('');
        	this.choices = "";
        },
        
        /**
         * @desc 动态移除组件下拉框中的内容
         * @param {value} 文本对应的数值
         */           
        removeOption: function(value) {   
        	this._removeDatabyKey(value);
        	this.itemsContainer.children("[data-value='"+value+"']").remove();
        },
        
        /**
         * @desc 动态显示组件下拉框中的内容
         * @param {value} 文本对应的数值
		 * @example $('XXX').comboxfield('showOption','value');
         */           
        showOption: function(value) {   
        	this.itemsContainer.children("[data-value='"+value+"']").css("display","block");
        },
        
		/**
         * @desc 设置输入框文本值
		 * @return 所有选中文本值
		 * @example $('XXX').comboxfield('setText','张三');
		 */      
        setText: function(value){
        	this.inputtext.val(value);
        },
        		        
		/**
         * @desc 设置当前状态对象选中
		 * @param {value} 选中对象的值
		 * @example $('XXX').comboxfield('setValue','2');
		 */
		setValue: function(value, callback) {
			var $this = this;
			if(typeof(value) == 'object' && jazz.isArray(value)){
				$this.reset();
				var data = $.extend(true,[],value);
				$.each(data, function(i,jsondata){
					var _label = $.trim(jsondata['text']||''), 
						_value = $.trim(jsondata['value']||'');
					$this.iData.push({'value': _value, 'text': _label});
				});
				$this._intItems();
			}else{
				$this._setData(value,callback);
			}
		}        
        
    });
    
})(jQuery); 
(function($){
/** 
 * @version 0.5
 * @name jazz.datefield
 * @description 时间类。
 * @constructor
 * @extends jazz.field
 * @requires
 * @example $('#input_id').datefield();
 */
	$.widget('jazz.datefield', $.jazz.dropdownfield, {
	    options: /** @lends jazz.datefield# */ {
			
			/**
			 *@type String
			 *@desc 组件的效验类型
			 *@default ''
			 */
			vtype: 'datefield',

	        /**
             *@type String
             *@desc 已经选择的日期（用于回填）
             *@default 'null'
             */
	        defaultvalue: null,			
	        
	        /**
	         *@type String
	         *@desc 要显示的月
	         *@default '当前月'
	         */
	        month: new Date().getMonth() + 1,
			
            /**
             *@type String
             *@desc 要显示的年
             *@default '当前年'
             */
            year: new Date().getFullYear(),
	        
	        //event
            /**
             *@desc 鼠标焦点离开输入框时触发
             *@param {event} 事件
             *@param {ui.newValue} 新修改的值 
             *@param {ui.oldValue} 旧值
             *@event
             *@example
             *<br/>$("XXX").datefield("option", "change", function(event, ui){  <br/>} <br/>});
             *或:
             *<br/>$("XXX").on("datefieldchange",function(event, ui){  <br/>} <br/>});
             *或：
             *function XXX(){……}
             *<div…… change="XXX()"></div> 或 <div…… change="XXX"></div>
             */
            change: null,

            /**
			 *@desc 鼠标焦点进入输入框时触发
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").datefield("option", "enter", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("datefieldenter",function(event){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… enter="XXX()"></div> 或 <div…… enter="XXX"></div>
			 */			
			enter: null,
    		
    		/**
			 *@desc 当选择了某项时，触发itemselect事件  
			 *@param {event} 事件
			 *@param {value} 选中项的值 
			 *@event
			 *@example
			 *<br/>$("XXX").datefield("option", "itemselect", function(event, value){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("datefielditemselect",function(event, value){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… itemselect="XXX()"></div> 或 <div…… itemselect="XXX"></div>
			 */
			itemselect: null

		},
		
		/** @lends jazz.datefield*/
		
        /**
         * @desc 创建组件
         * @private
         */  
		_create: function(){
			//创建组件
        	this._super();
        	this._createDownpanel(this.options.vtype); 
		},
		
        /**
         * @desc 初始化组件
         * @private
         */
        _init: function() {
        	this._super();
        	
        	var $this = this;
        	
        	var dropdatej = this.itemsContainer.date({downwidth:this.parent.width});
        	dropdatej.off("dateselect.datefield").on("dateselect.datefield",function(event, ui){ 
        		$this.setValue(ui.date);
                $this._event("itemselect", event ,{"value": ui.date});
                $this.panel.hide();
                var data = $this._getData();
            	$this._event("change", event, data);
            	$this.oldchoices = ui.date;
                
        	});
        	
        	if(this.options.defaultvalue){
    			this.setValue(this.options.defaultvalue);
            }
        	
        	if(!this.options.disabled){
        		this._bindConstantEvents(this.options.vtype);
    		}
        	
			//验证
			this._validator();         	
        },

        /**
         * @desc 获取当前选中的数据
         * @private 
         * @returns {}
         */
        _getData: function(){
        	var newValue = this.getValue();
        	var data = {};
        	data.newValue = newValue;
        	data.oldValue = this.oldchoices ? this.oldchoices : "";
        	return data;
        },
        
		/**
         * @desc 列表框是否显示滚动条
		 * @private
		 * @example this._itemScroll();
         */        
        _itemScroll: function(){
        	
        },        
		
		/**
         * @desc 获取当前选中状态对象的值
		 * @return 所有选中的值
		 * @example $('XXX').datefield('getValue');
		 */    
        getValue: function(){
        	return this.inputtext.val();
        },
		
		/**
         * @desc 获取当前选中状态对象的值
		 * @return 所有选中的值
		 * @example $('XXX').datefield('getText');
		 */    
        getText: function(){
        	return this.getValue();
        },
        
		/**
         * @desc 设置元素值  
         * @param {value} 设置的值
		 * @example $('XXX').datefield('setValue',value);
         */
		setValue: function(value) {
			this.inputtext.val(value);
		}
	});

})(jQuery);(function($){
	
	/**
	 * @version 0.5
	 * @name jazz.comboxtreefield
	 * @description 表单元素的选择填报类。
	 * @constructor
	 * @extends jazz.dropdownfield
	 * @requires
	 * @example
	 */	

    $.widget("jazz.comboxtreefield", $.jazz.dropdownfield, {
       
        options: /** @lends jazz.comboxtreefield# */ {
			
       		/**
			 *@type String
			 *@desc 组件类型
			 *@default fieldset
			 */
			vtype: 'comboxtreefield',

			/**
			 *@type Object
			 *@desc zTree的setting对象
			 *@default null
			 */              
            setting: {
				    data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "pId",
							rootPId: 0
						}
					},
					async: {
						enable: true,
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						dataType:"json", 
						autoParam:["id","level"]
					},							
					check: {
						enable: true,
						radioType: 'all'
					},
					view: {
						dblClickExpand: true
					},							
					callback: {

					}
            },
            
			/**
			 *@type Number
			 *@desc 滚动条显示高度
			 *@default 200
			 */         
            downheight: 200,   
           
			/**
			 *@type String
			 *@desc 是否多选 true是复选框，false是单选框
			 *@default 'true'
			 */            
            multiple: false,
			
            // callbacks
			
    		/**
			 *@desc 鼠标焦点进入输入框时触发
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").comboxtreefield("option", "enter", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("comboxtreefieldenter",function(event, ui){  <br/>} <br/>});
			 */			
			enter: null,
    		
    		/**
			 *@desc 鼠标焦点离开输入框时触发
			 *@param {event} 事件
			 *@param {ui.newValue} 新修改的值 
			 *@param {ui.oldValue} 旧值
			 *@event
			 *@example
			 *<br/>$("XXX").comboxtreefield("option", "leave", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("comboxtreefieldleave",function(event, ui){  <br/>} <br/>});
			 */
    		leave: null,
    		
    		/**
			 *@desc 当选择了某项时，触发itemselect事件  配合dataurl属性使用
			 *@param {event} 事件
			 *@param {value} 选中项的值 
			 *@event
			 *@example
			 *<br/>$("XXX").comboxtreefield("option", "itemselect", function(event, value){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("comboxtreefielditemselect",function(event, value){  <br/>} <br/>});
			 */
			itemselect: null
        },


        /** @lends jazz.comboxtreefield */
	
        /**
         * @desc 创建组件 
         * @private
         * @example  this._create();
         */  
        _create: function() {
        	//创建组件
        	this._super();
        	this._createDownpanel(this.options.vtype);       	
        },
        
        /**
         * @desc 初始化组件
         * @private
         * @example  this._init();
         */
        _init: function() {
        	this._super();
        	
        	this._treeSetting();
        	this._loadtree();
        	if(!!this.options.defaultvalue){
    			this.setValue(this.options.defaultvalue);
            }else{
            	this.setText(this.options.blankvalue);
            }
        },
        
        /**
         * @desc 初始化ztree的settting配置
         * @private
         * @example  this._treeSetting();
         */
        _treeSetting: function(){

    		this.options.setting.check.chkStyle = "checkbox";
    		if(this.options.check_chkboxtype){
        		this.options.setting.check.chkboxType = this.options.check_chkboxtype; 
        	}
    		if(!this.options.multiple){
    			this.options.setting.check.chkStyle = "radio";		
    		}else{
    			this.resetcomboxtree.remove();
    		}
    		if(!this.options.asyncenable){
				this.options.setting.async.enable = this.options.asyncenable; 
			}
    		
			if(!!this.options.autoparam){
				this.options.setting.async.autoParam = this.options.autoparam; 
			}
    		
			if(!!this.options.autochecktrigger){
				this.options.setting.check.autoCheckTrigger = this.options.autochecktrigger; 
			}
			if(!!this.options.radiotype){
				this.options.setting.check.radioType = this.options.radiotype; 
			}
			if(!!this.options.chkboxtype){
				this.options.setting.check.chkboxType = this.options.chkboxtype; 
			}
			
            if(!!this.options.setting.check){
            	this.funOnClick = this.options.setting.callback.onCheck;
			}else{
				this.funOnClick = this.options.setting.callback.onClick;
			}
        	   	
        },
        
        /**
         * @desc 实例化树，调取zTree
         * @private
         * @example  this._loadtree();
         */
        _loadtree: function(){
			if(!!this.options.dataurl){
				if(!!this.options.asyncurl){
					this.options.setting.async.url = this.options.asyncurl; 
				}else{
					this.options.setting.async.url = this.options.dataurl; 
				}
				if(typeof(this.options.dataurl) == 'object' && jazz.isArray(this.options.dataurl)){
					this._callback(this.options.dataurl);
				}else{
					this._ajax();
				}
			}
        },
        
        
        /**
         * @desc 生成数据项
		 * @private
		 * @example this._intItems();
         */
        _callback: function(data) {
        	if(!!data){
    			this._initTreeEvent();
    			$.fn.zTree.init(this.itemsContainer, this.options.setting, data);
    		}
        	if(!this.options.disabled){
        		this._bindConstantEvents(this.options.vtype);
    		}
        	this._validator(); 
    		
        	this.datastatus = true;
    		
    		if(this.options.defaultvalue){
    			this.setValue(this.options.defaultvalue);
    		}
        },

        /**
         * @desc 初始化树组件事件
         * @private
         * @example  this._initTreeEvent();
         */
        _initTreeEvent: function(){
        	var $this = this;      	
        	if(!!this.options.setting.check){
        		
        		this.options.setting.callback.onClick = function(event, treeId, treeNode) {
    				var z_tree_init_001 = $.fn.zTree.getZTreeObj(treeId);
    				z_tree_init_001.checkNode(treeNode,true,true,true);
    				if(!!$this.options.onclick){
    					eval($this.options.onclick).call(this, event, treeId, treeNode);
    				}
    		    }
        		
        		var tempClick = {click: null};
        		if(!!this.funOnClick){
        			$.extend(true, tempClick, {click: this.funOnClick});
        		}
        		
        		this.options.setting.callback.onCheck = function(e, treeId, treeNode){
    				var z_tree_init_001 = $.fn.zTree.getZTreeObj(treeId),
    				z_tree_init_nodes = z_tree_init_001.getCheckedNodes(true),
    				node_label = "",  node_code = '';
    				
    				if(!treeNode.checked){
    					z_tree_init_001.cancelSelectedNode(treeNode);
        			}
    				
    				z_tree_init_nodes.sort(function compare(a,b){return a.id-b.id;});
    				for (var i=0, l=z_tree_init_nodes.length; i<l; i++) {
    					node_label += z_tree_init_nodes[i].name + ",";
    					node_code += z_tree_init_nodes[i].id + ",";
    				}
    				if (node_code.length > 0 ){
    					node_label = node_label.substring(0, node_label.length-1);
    					node_code = node_code.substring(0, node_code.length-1);
    				}
    				
    				$this.inputtext.val(node_label);
    				$this.input.val(node_code);  
    				
    				$this._event("itemselect", e ,{"{text": node_label,"value":node_code});
    				
    				if(tempClick.click != null && $this.options.tempState!=0){
        				var func = eval(tempClick.click);
        				func(e, treeId, treeNode);
        			}
    				
    				$this.inputtext.focus();
    				
    				if(!!$this.options.oncheck){
    					eval($this.options.oncheck).call(this, e, treeId, treeNode);
    	        	}
        		};
        	} else {
        		var tempClick = {click: null};
        		if(!!this.funOnClick){
        			$.extend(true, tempClick, {click: this.funOnClick});
        		}
        		
        		this.options.setting.callback.onClick = function(e, treeId, treeNode){

    				var z_tree_init_001 = $.fn.zTree.getZTreeObj(treeId),
    				z_tree_init_nodes = z_tree_init_001.getSelectedNodes(),
    				node_label = "",  node_code = '';
    				
    				z_tree_init_nodes.sort(function compare(a,b){return a.id-b.id;});
    				for (var i=0, l=z_tree_init_nodes.length; i<l; i++) {
    					node_label += z_tree_init_nodes[i].name + ",";
    					node_code += z_tree_init_nodes[i].id + ",";
    				}
    				if (node_code.length > 0 ){
    					node_label = node_label.substring(0, node_label.length-1);
    					node_code = node_code.substring(0, node_code.length-1);
    				}
    				
    				$this.inputtext.val(node_label);
    				$this.input.val(node_code);
    				
        			if(tempClick.click != null){
        				var func = eval(tempClick.click);
        				func(e, treeId, treeNode);
        			}
        			
        			$this.inputtext.focus();
        		};       		
        	}
			 
			if(!!this.options.onasyncsuccess){
        		this.options.setting.callback.onAsyncSuccess = function(e, treeId, treeNode){
					eval($this.options.onasyncsuccess).call(this, e, treeId, treeNode);
			    } 
        	}
			if(!!this.options.onnodecreated){
				this.options.setting.callback.onNodeCreated = function(e, treeId, treeNode){
					eval($this.options.onnodecreated).call(this, e, treeId, treeNode);
			    }
        	}

			this.resetcomboxtree.off("click.comboxtreefield").on("click.comboxtreefield",function(){
				$this.reset();
				$this.setText($this.options.blankvalue);
         		var treeObj = $.fn.zTree.getZTreeObj($this.ulId);
         		if(!!treeObj){
         			var nodes = treeObj.getCheckedNodes(true);
             		for (var i=0, l=nodes.length; i < l; i++) {
             			treeObj.checkNode(nodes[i], false, true);
             		}
         		}
			});
        },
              
        
		/**
         * @desc 列表框是否显示滚动条
		 * @private
		 * @example this._itemScroll();
         */        
        _itemScroll: function(){
        	this.panel.height(this.options.downheight);       	
        },        

        
		/**
         * @desc 回显选中的数据
         * @param {codes} 节点集合
         * @param {aTree} 当前树对象
         * @example  this._setNodeData(codes,aTree);
         */        
         _setNodeData: function(codes,aTree){
        	var $this = this;
         	if(!!codes){
         		var state = 0;  //单选
         		if(!!this.options.setting.check){
         			state = 1;  //多选 
         		}
         		var codeArray = codes.split(',');
         		$.each(codeArray, function(i, code){
         			code = $.trim(code);
         			if(!!code){
         				var node = aTree.getNodeByParam('id', code, null);
         				if(!!node){
         					if(state==1){
         		        		aTree.checkNode(node, true, true);
         		        	}else{
         		        		aTree.selectNode(node);	
         		        	}
         				}
         			}
         		});
         		
 	        	var nodes = null;
 	        	if(state==1){
 	        		nodes = aTree.getCheckedNodes(true);
 	        	}else{
 	        		nodes = aTree.getSelectedNodes();
 	        	}
         		var label = "",  code = '';  
 	        	nodes.sort(function compare(a,b){return a.id-b.id;});
 	    		for (var i=0, l=nodes.length; i<l; i++) {
 	    			label += nodes[i].name + ",";
 	    			code += nodes[i].id + ",";
 	    		}
 	    		if (label.length > 0 ){ 
 	    			label = label.substring(0, label.length-1);
 	    			code = code.substring(0, code.length-1);
 	    		}
 	    		this.inputtext.val(label);
 	    		this.input.val(code);   
         	}
        },        
        
		/**
         * @desc 重新加载数据
         * 		 注： 在数据初始化时也调用了这个方法 jazz.SwordAdapter.js
         *       obj.find('select').comboxtreefield('loadData',);  目的是为了加载SwordPageData数据
		 * @private
		 * @example this._loadData();
         */
        _loadData: function(){
			this.itemsContainer.children().remove();
    		this._loadtree();
        },
        
        /**
         * @desc 初始化表单数据，依据dataurl
         * @private
         * @example $('XXX').comboxtreefield('reload'); 
         */         
        reload: function() {   
        	this._loadData();
        },
        
		/**
         * @desc 获取当前选中状态对象的文本值
		 * @return 所有选中文本值
		 * @example $('XXX').comboxtreefield('getText');
		 */      
        getText: function(){
        	return this.inputtext.val();
        },
        
		/**
         * @desc 设置输入框文本值
		 * @return 所有选中文本值
		 * @example $('XXX').comboxtreefield('setText','张三');
		 */      
        setText: function(value){
        	this.inputtext.val(value);
        },
        
		/**
         * @desc 重置
		 * @example $('XXX').comboxtreefield('reset');
		 */
        reset: function() {
        	this.input.val('');
        	this.inputtext.val('');
     		var aTree = $.fn.zTree.getZTreeObj(this.ulId);
     		aTree.cancelSelectedNode();
     		aTree.checkAllNodes(false);
     		var nodes = aTree.getCheckedNodes(true);
     		$.each(nodes,function(i,node){
     			aTree.checkNode(node, false);
     		});
        },
        
		/**
         * @desc 获取当前选中状态对象的值
		 * @return 所有选中的值
		 * @example $('XXX').comboxtreefield('getValue');
		 */    
        getValue: function(){
        	return this.input.val();
        },
        
        
		/**
         * @desc 设置当前状态对象选中
		 * @param {value} 选中对象的值
		 * @example $('XXX').comboxtreefield('setValue','2');
		 */
		setValue: function(value,callback) {
			var $this = this;
			if(typeof(value) == 'object' && jazz.isArray(value)){
				var data = $.extend(true,[],value);
				$this._initTreeEvent();
	        	$.fn.zTree.init($this.itemsContainer, $this.options.setting, data);
			}else{
				this._setData(value,callback);
			}
		},
		
        /**
		 * @desc 设置当前状态对象选中
		 * @param {value} 选中对象的值
		 * @private 
         */
		_seSontData: function(value,callback) { 
			if(!!value){
         		var aTree = $.fn.zTree.getZTreeObj(this.ulId);
         		if(!!aTree){
         			this._setNodeData(value,aTree);
         			if(!!callback && $.isFunction(callback)){
         				callback.call(this,this);
    				}
         		}else{
         			this.options.selectvalue = value;
         		}
        	}
		},
		
		/**
         * @desc 获取当前选中的数据
         * @private 
         * @returns {Array}
         */
        _getSelectedData: function(){
        	var $this = this ,oldData = [];;
        	var aTree = $.fn.zTree.getZTreeObj(this.ulId);
        	var codeArray = $this.oldchoices.split(',');
     		$.each(codeArray, function(i, code){
     			code = $.trim(code);
     			if(!!code){
     				var node = aTree.getNodeByParam('id', code, null);
     				if(!!node){
     					oldData.push(node);
     				}
     			}
     		});
        	return oldData;
        },
		
        /**
         * @desc 获取当前选中的数据
         * @private 
         * @returns {}
         */
        _getData: function(){
        	var newValue = this.getValue("value");
        	var newText = this.getText("text");
        	var oldData = this._getSelectedData(); 
        	var oldValueData = [] ,oldTextData = [];
        	if(oldData.length>0){
        		$.each(oldData,function(i,data){
            		oldValueData.push(data.id); 
            		oldTextData.push(data.name);  
            	});
        	}
        	var oldValue = oldValueData.join(",");
        	var oldText = oldTextData.join(",");
        	var data = {};
        	data.newValue = newValue,data.newText = newText;
        	data.oldValue = oldValue,data.oldText = oldText;
        	return data;
        },
		
        /**
         * @desc 设置组件属性的值
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         * @example $('XXX').comboxtreefield('option','width','400'); 设置width属性的值为400
         */
        _setOption: function(key, value){
        	switch(key){
	        	case 'dataurlparams':
		       		this.options.dataurlparams = value;
		       		break;
	        	case 'dataurl':
	        		this.options.dataurl = value;
	        		break;
	        	case 'disabled':
	        		if(value){
	        			this.parent.off('click');
	        		}else{
						this._bindConstantEvents(this.options.vtype);
	        		}
	        		break;	 
        	}
        	this._super(key, value);
        }
        
    });

})(jQuery);(function($){
	
	/**
	 * @version 1.0
	 * @name jazz.autocompletecomboxfield
	 * @description 表单元素的选择填报类。
	 * @constructor
	 * @extends jazz.comboxfield
	 * @example $('XXX').autocompletecomboxfield();
	 */

    $.widget("jazz.autocompletecomboxfield", $.jazz.comboxfield, {
       
        options: /** @lends jazz.autocompletecomboxfield# */ {
			
        	/**
			 *@type String
			 *@desc 组件类型
			 *@default 'autocompletecomboxfield'
			 */        	        	
        	vtype: 'autocompletecomboxfield',
        		
    		/**
			 *@type Number
			 *@desc 最小查询的长度 
			 *@default 1
			 */            
            minquerylength: 1 
            
        },

		/** @lends jazz.autocompletecomboxfield */
	
        /**
         * @desc 创建组件
         * @private
         */ 
        _create: function() {
        	//创建组件
        	this._super();
        }, 
        
        /**
         * @desc 初始化组件
         * @private
         */ 
        _init: function() {
        	this._super();
            //查询
        	this._setupFilterMatcher();
            this._AutoBindKeyEvents();
            this.query = "";
        },
        
        /**
         * @desc 绑定的键盘事件
         * @private
         * @example  this._bindKeyEvents();
         */	    
        _AutoBindKeyEvents: function() {
            var $this = this;
            var dataurl = $this.options.dataurl;
            this.flag = true;
            this.inputtext.off('keyup.autocompletecomboxfield').on('keyup.autocompletecomboxfield',function(e) {
            	var keyCode = $.ui.keyCode, key = e.which, shouldSearch = true;
                if(key == keyCode.UP || key == keyCode.LEFT || key == keyCode.DOWN || key == keyCode.RIGHT || key == keyCode.TAB || key == keyCode.SHIFT ||
                   key == keyCode.ENTER || key == keyCode.NUMPAD_ENTER) {
                        shouldSearch = false;
                }
                if(shouldSearch) {
                	if(!!$this.timeout) {
                        window.clearTimeout($this.timeout);
                    }
    				$this.timeout = window.setTimeout(function() {
    					var value = $this.getValue();
    					var text = $this.inputtext.val();
    					if(dataurl){
    						if($this.flag){ 
	    						if(dataurl.indexOf("?")!=-1){
	    							dataurl += "&text={text}";
	        					}else{
	        						dataurl += "?text={text}";
	        					}
	    						$this.flag = false;
    						}
    						var url = dataurl.replace('{text}', text);
    						$this.options.dataurl = url;
    					}
    					$this.options.dataurlparams = {"text":text,"value":value};
    					$this.reload();
    					$this._search(text);
    				},$this.options.delay);
                }

            });
        },	
        
        /**
         * @desc 查询
         * @param {q} 输入的查询条件
         * @private
         * @example  this._search();
         */
        _search: function(q) {
        	this.query = this.options.casesensitive ? q : q.toLowerCase();
            if(this.options.dataurl) {
                if($.isArray(this.options.dataurl)) {
                	this.iData = this.options.dataurl;
                	this._intItems();
                }
                else {
                    this._ajax();
                }
            }
        },		/**
         * @desc 获取输入框文本值的值
		 * @return String
		 * @example $('XXX').field('getText');
		 */      
        getText: function(){
        	return this.getValue();
        },
		
		/**
         * @desc 获取输入框的值
         * @return String
		 * @example $('XXX').field('getValue');
         */				
		getValue: function(){
			var value = this.inputtext.val();
        	if(value == this.options.valuetip){
				value = "";
			}
			return value;
		}
        
    });
    
})(jQuery); (function($){
/** 
 * @version 0.5
 * @name jazz.date
 * @description 日历组件类（非弹出日历，弹出日历请用DateField）。
 * @constructor
 * @example $('XXX').date();
 */
    $.widget('jazz.date', $.jazz.boxComponent, {
        options: /** @lends jazz.date# */ {
            
            /**
             *@type String
             *@desc 组件的效验类型
             *@default 'date'
             */
            vtype: 'date',
            
            /**
             *@type String
             *@desc 日历组件宽度
             *@default '200'
             */
            downwidth: 200,
            
            /**
             *@type String
             *@desc 日历组件高度
             *@default '200'
             */
            //downheight: 200,
            
            /**
             *@type String
             *@desc 要显示的年
             *@default '当前年'
             */
            year: new Date().getFullYear(),
	        
	        /**
             *@type String
             *@desc 要显示的月
             *@default '当前月'
             */
	        month: new Date().getMonth() + 1,
	        
	        /**
             *@type String
             *@desc 已经选择的日期（用于回填）
             *@default 'null'
             */
	        defaultvalue: null,
	        
	        //event
	        /**
             *@type function
             *@desc 选择日期时的回调函数
             *@event
             *@example
             **<br/>$("XXX").date("option", "select", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("dateselect",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… select="XXX()"></div> 或 <div…… select="XXX"></div>
             */
	        select: null,
	        
	        /**
             *@type function
             *@desc 日历画完后的回调函数
             *@event
             *@example
             **<br/>$("XXX").date("option", "finish", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("datefinish",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… finish="XXX()"></div> 或 <div…… finish="XXX"></div>
             */
	        finish: null
        },
        
        /** @lends jazz.date*/
        
        /**
         * @desc 创建组件
         * @private
         * @example  this._create();
         */  
        _create: function(){
        	// 如果已经指定了默认日期，将默认日期的格式处理一下并转化为Date类型
        	if(this.options.defaultvalue){
	        	this.options.defaultvalue = new Date( this.options.defaultvalue.replace(/-/g,"/") );
        	}
        	
            // 创建dom元素
        	this.element.addClass("jazz-date").css({
        		"width": this.options.downwidth,
        		"height": this.options.downheight
        	});
        	
        	// 上一年
        	this.calendarPreYear = $('<div class="jazz-date-preyear"></div>').appendTo( this.element );
        	// 上一月
        	this.calendarPreMonth = $('<div class="jazz-date-premonth"></div>').appendTo( this.element );
        	// 下一年
        	this.calendarNextYear = $('<div class="jazz-date-nextyear"></div>').appendTo( this.element );
        	// 下一月
        	this.calendarNextMonth = $('<div class="jazz-date-nextmonth"></div>').appendTo( this.element );
        	// 当前月
        	this.calendarText = $('<div class="jazz-date-text">&nbsp;</div>').appendTo( this.element );
        	// 表头
        	this.calendarHeader = $(
        	     '<table class="jazz-date-header" cellspacing="0" cellpadding="0" border="0" width="100%" '
        	   +     'height="22" align="center" vAlign="middle">'
               + '  <thead>'
               + '    <tr>'
               + '      <td style="height:22px">一</td>'
               + '      <td>二</td>'
               + '      <td>三</td>'
               + '      <td>四</td>'
               + '      <td>五</td>'
               + '      <td>六</td>'
               + '      <td>日</td>'
               + '    </tr>'
               + '  </thead>'
               + '</table>').appendTo( this.element );
            // 表区
        	this.items = $(
        	     '<table class="jazz-date-body" cellspacing="0" cellpadding="0" border="0" width="100%" '
        	   +     'height="' + (this.options.downheight - this.calendarText.outerHeight(true) - this.calendarHeader.outerHeight(true) ) + '" '
        	   +     'align="center" vAlign="middle">'
               + '  </tbody>'
               + '</table>').appendTo( this.element );
        	
        	this._bindEvent();
        },
        
        /**
         * @desc 绑定事件
         * @private
         * @example  this._bindEvent();
         */
        _bindEvent: function() {
            var $this = this;
            // 绑定事件
            this.calendarPreYear.off("mousedown.date").on("mousedown.date", function(){
                $this.preYear();
            });
            this.calendarPreMonth.off("mousedown.date").on("mousedown.date", function(){
                $this.preMonth();
            });
            this.calendarNextMonth.off("mousedown.date").on("mousedown.date", function(){
                $this.nextMonth();
            });
            this.calendarNextYear.off("mousedown.date").on("mousedown.date", function(){
                $this.nextYear();
            });
            this.items.off("click.date, mouseover.date, mouseout.date").on("click.date", function(event){
                var t = event.target,  $t = $(t);
                // 如果选中的不是TD，或者选中的不是本月的TD，不做任何操作
                if(t.tagName != "TD" || $t.hasClass("jazz-date-pmonth") || $t.hasClass("jazz-date-nmonth")){
                    return;
                }
                // 将已经选中的节点去掉
                $this.items.find(".jazz-date-selected").removeClass("jazz-date-selected");
                // 给当前节点添加样式
                $t.addClass("jazz-date-selected");
                // 抛出select事件
                $this._event("select", event, {"date": $this.options.year + "-" + $this.options.month + "-" + t.innerHTML});
                
            }).on("mouseover.date", function(event){
            	var t = event.target,  $t = $(t);
            	// 如果选中的不是TD，或者选中的不是本月的TD，不做任何操作
            	if(t.tagName != "TD" || $t.hasClass("jazz-date-pmonth") || $t.hasClass("jazz-date-nmonth")){
                    return;
                }
                $t.addClass("jazz-date-hover");
            }).on("mouseout.date",function(event){
            	var t = event.target,  $t = $(t);
                $t.removeClass("jazz-date-hover");
            });
        },
        
        /**
         * @desc 初始化组件
         * @private
         */
        _init: function() {
            this._draw();
        },
        
        /**
         * @desc 绘制
         * @private
         */
        _draw: function() {
		    //用来保存日期列表
		    var arr = [];
		    
		    //用当月第一天在一周中的日期值作为当月离第一天的天数
		    // firstday 为本月1号的星期值
		    for(var i = 0, firstday = new Date(this.options.year, this.options.month - 1, 1).getDay() - 1; i < firstday; i++){
		    	arr.push(0);
		    }
		    //用当月最后一天在一个月中的日期值作为当月的天数
		    for(var i = 1, monthDay = new Date(this.options.year, this.options.month, 0).getDate(); i <= monthDay; i++){ arr.push(i); }
		    
		    //插入日期
		    var frag = document.createDocumentFragment();
		    while(arr.length){
		        //每个星期插入一个tr
		        var row = document.createElement("tr");
		        //每个星期有7天
		        for(var i = 1; i <= 7; i++){
		            var cell = document.createElement("td"); cell.innerHTML = "&nbsp;";
		            if(arr.length){
		                var d = arr.shift();
		                if(d){
		                    cell.innerHTML = d;
		                    var on = new Date(this.options.year, this.options.month - 1, d);
		                    //判断是否今日
		                    if( this._isSame(on, new Date()) ){
		                    	$(cell).addClass("jazz-date-today");
		                    }
		                    //判断是否选择日期
		                    if( this.options.defaultvalue && this._isSame(on, this.options.defaultvalue ) ){
		                    	$(cell).addClass("jazz-date-selected");
		                    }
		                }else{
    		            	$(cell).addClass("jazz-date-pmonth");
		                }
		            }else{
		            	$(cell).addClass("jazz-date-nmonth");
		            }
		            row.appendChild(cell);
		        }
		        frag.appendChild(row);
		    }
		    //先清空内容再插入(ie的table不能用innerHTML)
		    this.items.children().remove();
		    this.items.append(frag);
		    
		    //将当前年月更新到界面上
		    this.calendarText.html(this.options.year + "年" + this.options.month + "月");
		    
		    //触发完成事件
		    this._event("finish");
	   },
	   
	   /**
        * @desc 判断两个日期是否同一日
        * @private
        */
	  _isSame: function(d1, d2) {
	    return (d1.getFullYear() == d2.getFullYear() && d1.getMonth() == d2.getMonth() && d1.getDate() == d2.getDate());
	  },
	  
	  /**
       * @desc 根据日期画日历
       * @private
       */
	  _preDraw: function(date) {
	    //再设置属性
	    this.options.year = date.getFullYear(); this.options.month = date.getMonth() + 1;
	    //重新画日历
	    this._draw();
	  },	  
	  
	  /**
        * @desc 将日历跳转回当前月
        * @example  $(XXX).date("currentMonth");
        */
	  currentMonth: function() {
	    this._preDraw(new Date());
	  },
	  
	  /**
       * @desc 将日历跳转回下一月
       * @example $(XXX).date("nextMonth");
       */
	  nextMonth: function() {
	    this._preDraw(new Date(this.options.year, this.options.month, 1));
	  },
	  
	  /**
       * @desc 将日历跳转回下一年
       * @example $(XXX).date("nextYear");
       */
	  nextYear: function() {
	    this._preDraw(new Date(this.options.year + 1, this.options.month - 1, 1));
	  },	  
	  
	  /**
       * @desc 将日历跳转回上一月
       * @example  $(XXX).date("preMonth");
       */
	  preMonth: function() {
	    this._preDraw(new Date(this.options.year, this.options.month - 2, 1));
	  },	  
	  
	  /**
       * @desc 将日历跳转回上一年
       * @example  $(XXX).date("preYear");
       */
	  preYear: function() {
	    this._preDraw(new Date(this.options.year - 1, this.options.month - 1, 1));
	  }	  

    });
})(jQuery);
(function($) {

	/**
	 * @version 0.5
	 * @name jazz.fieldset
	 * @description 对表单内组件进行分组。
	 * @constructor
	 * @extends jazz.panel
	 * @requires
	 * @example 
	 */	
    $.widget("jazz.fieldset", $.jazz.panel, {
       
        options: /** @lends jazz.fieldset# */ {
        	
    		/**
			 *@type String
			 *@desc 组件类型
			 *@default fieldset
			 */
			vtype: 'fieldset',
			
        	/**
			 *@type Boolean
			 *@desc 是否能收缩 true 可收缩
			 *@default false
			 */        	
            toggleable: false,
            
        	/**
			 *@type Boolean
			 *@desc 是否可收缩  true 可以收缩
			 *@default false
			 */            
            collapsed: false,
            
            //callback
			/**
			 *@desc 点击按钮前触发
			 *@param {event} 事件	 
			 *@event
			 *@example
			 *初始化：
			 *<br/>$("#input_id").fieldset({beforeToggle: function( event){  <br/>} <br/>});
			 *非初始化:
			 *<br/>$("#input_id").on("fieldsetbeforeToggle",function( event, ui ){  <br/>} <br/>});
			 */	            
            beforeToggle: null,
            
			/**
			 *@desc 点击按钮后触发
			 *@param {event} 事件	 
			 *@event
			 *@example
			 *初始化：
			 *<br/>$("#input_id").fieldset({afterToggle: function( event){  <br/>} <br/>});
			 *非初始化:
			 *<br/>$("#input_id").on("fieldsetafterToggle",function( event, ui ){  <br/>} <br/>});			 
			 */	            
            afterToggle: null
        },
        
        /** @lends jazz.fieldset */
        
        /**
         * @desc 创建组件
         * @private
         * @example  this._create();
         */      
        _create: function() {
            this.element.addClass('jazz-fieldset ui-widget ui-widget-content ui-corner-all').
                children('legend').addClass('jazz-fieldset-legend ui-corner-all ui-state-default');
            
            this.element.contents(':not(legend)').wrapAll('<div class="jazz-fieldset-content" />');
            
            this.legend = this.element.children('legend.jazz-fieldset-legend');
            this.content = this.element.children('div.jazz-fieldset-content');
            
            this.legend.prependTo(this.element);
            
            if(this.options.toggleable) {
                this.element.addClass('jazz-fieldset-toggleable');
                this.toggler = $('<span class="jazz-fieldset-toggler ui-icon" />').prependTo(this.legend);
                
                this._bindEvents();
                
                if(this.options.collapsed) {
                    this.content.hide();
                    this.toggler.addClass('ui-icon-plusthick');
                } else {
                    this.toggler.addClass('ui-icon-minusthick');
                }
            }
        },
        
    	/**
         * @desc 绑定事件
         * @private
		 * @example this._bindEvents();
         */       
        _bindEvents: function() {
            var $this = this;
            
            this.legend.on('click.fieldset', function(e) {$this.toggle(e);})
                            .on('mouseover.fieldset', function() {$this.legend.addClass('ui-state-hover');})
                            .on('mouseout.fieldset', function() {$this.legend.removeClass('ui-state-hover ui-state-active');})
                            .on('mousedown.fieldset', function() {$this.legend.removeClass('ui-state-hover').addClass('ui-state-active');})
                            .on('mouseup.fieldset', function() {$this.legend.removeClass('ui-state-active').addClass('ui-state-hover');});
        },
        
    	/**
         * @desc 开关方法，控制组件展开收起
		 * @example 
         */ 
        toggle: function(e) {
            var $this = this;
            
            this._trigger('beforeToggle', e);

            if(this.options.collapsed) {
                this.toggler.removeClass('ui-icon-plusthick').addClass('ui-icon-minusthick');
            } else {
                this.toggler.removeClass('ui-icon-minusthick').addClass('ui-icon-plusthick');
            }

            this.content.slideToggle(this.options.toggleSpeed, 'easeInOutCirc', function() {
                $this._trigger('afterToggle', e);
                $this.options.collapsed = !$this.options.collapsed;
            });
        }
        
    });

})(jQuery);(function($) {
	
	/**
	 * @version 0.5
	 * @name jazz.accordionpanel
	 * @description 
	 * @constructor
	 * @requires
	 * @example $('#div_id').accordionpanel();
	 */
    $.widget("jazz.accordionpanel", $.jazz.boxComponent,  {
       
    	options: /** @lends jazz.accordionpanel# */ {
    		
    		/**
        	 * *@desc 组件类型
        	 */
        	vtype: 'accordion',
        	
        	 /**
			 *@type Number
			 *@desc 当前活动的accordionpanel页
			 *@default 0
			 */
             activeindex: 0,
             
             /**
  			 *@type Boolean
  			 *@desc 是否多个accordionpanel页展开
  			 *@default false
  			 */
             multiple: false
        },
        
        /** @lends jazz.accordionpanel */
        
        /**
         * @desc 创建组件
		 * @private
         */
        _create: function() {
        	this._super();
        	if(this.options.multiple) {
            	var index = this.options.activeindex;
                this.options.activeindex = [];
                this._addToSelection(index);
            }
        
            this.element.attr("name",this.options.name)
            	.attr("vtype",this.options.vtype)
            	.addClass('jazz-accordion jazz-helper-reset');
            
            var $this = this;
            this.childrens = this.element.children();
            $.each(this.childrens,function(i,child){
            	$(child).addClass('jazz-accordion-content jazz-helper-reset jazz-widget-content');
            	$(child).attr("name", $this.getCompId()+"_content"+i)
            		.attr("id", $this.getCompId()+"_content"+i);

                var headerClass =  (i == $this.options.activeindex) 
                	? 'jazz-accordion-headericon-active' 
                		: 'jazz-accordion-headericon-unactive';
            	var headericon = $("<div class='"+headerClass+"'></div>");
            	var headertext = $("<div class='jazz-accordion-headertext'></div>");
            	
            	var iconurl = $(child).attr('iconurl');
            	if(iconurl){
            		headertext.append('<span style="background:url('+iconurl+') 50% 50% no-repeat;" class="jazz-accordion-header-icon jazz-icon">p</span>');
            	}else{
            		headertext.css('padding-left', '5px');
            	}
            	headertext.append("<span>" + $(child).attr("title")+"</span>");
            	var header = $("<div class='jazz-accordion-header jazz-helper-reset jazz-state-default'></div>");
            	header.attr("name",$this.getCompId()+"_header"+i)
            		.attr("id",$this.getCompId()+"_header"+i);
            	if($this.options.activeindex == i) {
            		header.addClass('jazz-state-active');
            		iconurl = $(child).attr('iconselect');
                	if(iconurl){
                		headertext.find('.jazz-accordion-header-icon')
                			.css('background-image', "url("+iconurl+")");
                	}
                }else{
                	$(child).addClass(' jazz-helper-hidden');
                }
            	header.append(headericon).append(headertext);
            	$(child).before(header);
            });
        },
        
        _init:function(){
        	if(!!this.options.width && this.options.width != -1) {
        		this.element.css("width",this.options.width);
            }
        	if(!!this.options.height && this.options.height != -1) {
        		this.element.css("height",this.options.height);
            }
        	this.headers = this.element.children('.jazz-accordion-header');
            this.panels = this.element.children('.jazz-accordion-content');
            this._bindEvents();
            /*this.panels.each(function(i, e){
            	e.style.height = 'auto';
            });*/
        },
        
        /**
         * @desc 绑定事件
         * @private
		 * @example this._bindEvents();
         */
        _bindEvents: function() {
            var $this = this;

            this.headers.off('mouseover.accordion mouseout.accordion click.accordion')
            .on('mouseover.accordion', function() {
                var element = $(this);
                if(!element.hasClass('jazz-state-disabled')) {
                    element.addClass('jazz-state-hover');
                }
            }).on('mouseout.accordion', function() {
                var element = $(this);
                if(!element.hasClass('jazz-state-disabled')) {
                    element.removeClass('jazz-state-hover');
                }
            }).on('click.accordion', function(e) {
                var element = $(this);
                if(!element.hasClass('jazz-state-disabled')) {
                    var tabIndex = element.index() / 2;
                    $this.options.activeLastIndex = tabIndex;
                    if(element.hasClass('jazz-state-active')) {
                        $this.unselect(tabIndex);
                    }
                    else {
                        $this.select(tabIndex);
                    }
                }

                e.preventDefault();
            });
        },

        /**
         * @desc accordionpanel显示方法
         * @private
         * @param {panel} accordionpanel页面对象 
		 * @example this._show('panel');
         */
        _show: function(panel) {
        	var iconurl;
            if(!this.options.multiple) {
                var oldHeader = this.headers.filter('.jazz-state-active');
                oldHeader.removeClass('jazz-state-active').next().slideUp();
                oldHeader.children(".jazz-accordion-headericon-active").removeClass("jazz-accordion-headericon-active")
                .addClass("jazz-accordion-headericon-unactive");
            	oldHeader.each(function(i, header){
            		iconurl = $(header).next().attr('iconurl');
            		$(header).find('.jazz-accordion-header-icon')
        				.css('background-image', "url("+iconurl+")");
            	});
            }
            //activate selected
            var newHeader = panel.prev();
            newHeader.addClass('jazz-state-active').children(".jazz-accordion-headericon-unactive")
            	     .removeClass("jazz-accordion-headericon-unactive")
            		 .addClass("jazz-accordion-headericon-active");
            iconurl = panel.attr('iconselect');
        	if(iconurl){
        		newHeader.find('.jazz-accordion-header-icon')
        			.css('background-image', "url("+iconurl+")");
        	}
            
            panel.slideDown('fast');
        },

        /**
         * @desc accordionpanel添加活动的页面方法
         * @private
         * @param {nodeId} accordionpanel页码数从0开始第1个accordionpanel  
		 * @example this._addToSelection('nodeId');
         */
        _addToSelection: function(nodeId) {
            this.options.activeindex.push(nodeId);
        },

        /**
         * @desc accordionpanel移除活动的页面方法
         * @private
         * @param {index} accordionpanel页码数从0开始第1个accordionpanel  
		 * @example this._removeFromSelection('index');
         */
        _removeFromSelection: function(index) {
            this.options.activeindex = $.grep(this.options.activeindex, function(r) {
                return r != index;
            });
        },
        
        /**
         * @desc accordionpanel页选择方法
		 * @param {index} accordionpanel页码数从0开始第1个accordionpanel 
		 * @example $('#div_id').accordionpanel('select','index');
         */
        select: function(index) {
            var panel = this.panels.eq(index);

            this._trigger('change', panel);
            
            //update state
            if(this.options.multiple){
            	this.options.activeindex = [];
                this._addToSelection(index);
            }else{
            	this.options.activeindex = index;
            }
            this._show(panel);
        },

        /**
         * @desc accordionpanel页不选择方法
 		 * @param {index} accordionpanel页码数从0开始第1个accordionpanel 
 		 * @example $('#div_id').accordionpanel('unselect','index');
         */
        unselect: function(index) {
            var panel = this.panels.eq(index),
            header = panel.prev();

            header.removeClass('jazz-state-active').children(".jazz-accordion-headericon-active")
            .removeClass("jazz-accordion-headericon-active").addClass("jazz-accordion-headericon-unactive");
            var iconurl = panel.attr('iconurl');
            header.find('.jazz-accordion-header-icon')
				.css('background-image', "url("+iconurl+")");
            
            panel.slideUp();

            this._removeFromSelection(index);
        },
        
        /**
         * @desc 销毁组件  
         * @throws
	  	   * @private
	  	   * @example this._destroy();
         */
        _destroy: function() {
            
        }
        
    });

})(jQuery);
(function($) {
	
	/**
	 * @version 1.0
	 * @name jazz.colorfield
	 * @description 颜色选择器组件
	 * @constructor
	 * @extends jazz.field
	 * @requires
	 * @example $('#input_id').colorfield();
	 */
    var _idx=0,
	isIE=!$.support.cssFloat,
	_ie=isIE?'-ie':'',
	isMoz=isIE?false:/mozilla/.test(navigator.userAgent.toLowerCase()) && !/webkit/.test(navigator.userAgent.toLowerCase()),	
	history=[],
	baseThemeColors=['ffffff','000000','eeece1','1f497d','4f81bd','c0504d','9bbb59','8064a2','4bacc6','f79646'],
	subThemeColors=['f2f2f2','7f7f7f','ddd9c3','c6d9f0','dbe5f1','f2dcdb','ebf1dd','e5e0ec','dbeef3','fdeada',
		'd8d8d8','595959','c4bd97','8db3e2','b8cce4','e5b9b7','d7e3bc','ccc1d9','b7dde8','fbd5b5',
		'bfbfbf','3f3f3f','938953','548dd4','95b3d7','d99694','c3d69b','b2a2c7','92cddc','fac08f',
		'a5a5a5','262626','494429','17365d','366092','953734','76923c','5f497a','31859b','e36c09',
		'7f7f7f','0c0c0c','1d1b10','0f243e','244061','632423','4f6128','3f3151','205867','974806'],
	standardColors=['c00000','ff0000','ffc000','ffff00','92d050','00b050','00b0f0','0070c0','002060','7030a0'],
	moreColors=[
		['003366','336699','3366cc','003399','000099','0000cc','000066'],
		['006666','006699','0099cc','0066cc','0033cc','0000ff','3333ff','333399'],
		['669999','009999','33cccc','00ccff','0099ff','0066ff','3366ff','3333cc','666699'],
		['339966','00cc99','00ffcc','00ffff','33ccff','3399ff','6699ff','6666ff','6600ff','6600cc'],
		['339933','00cc66','00ff99','66ffcc','66ffff','66ccff','99ccff','9999ff','9966ff','9933ff','9900ff'],
		['006600','00cc00','00ff00','66ff99','99ffcc','ccffff','ccccff','cc99ff','cc66ff','cc33ff','cc00ff','9900cc'],
		['003300','009933','33cc33','66ff66','99ff99','ccffcc','ffffff','ffccff','ff99ff','ff66ff','ff00ff','cc00cc','660066'],
		['333300','009900','66ff33','99ff66','ccff99','ffffcc','ffcccc','ff99cc','ff66cc','ff33cc','cc0099','993399'],
		['336600','669900','99ff33','ccff66','ffff99','ffcc99','ff9999','ff6699','ff3399','cc3399','990099'],
		['666633','99cc00','ccff33','ffff66','ffcc66','ff9966','ff6666','ff0066','d60094','993366'],
		['a58800','cccc00','ffff00','ffcc00','ff9933','ff6600','ff0033','cc0066','660033'],
		['996633','cc9900','ff9900','cc6600','ff3300','ff0000','cc0000','990033'],
		['663300','996600','cc3300','993300','990000','800000','993333']
	],
	brightColors=[
	    '00ff33','00ff99','00ffff','00cc33','00cc99','00ccff','009933','009999','0099ff',
	    '66ff33','66ff99','66ffff','66cc33','66cc99','66ccff','669933','669999','6699ff',
	    '99ff33','99ff99','99ffff','99cc33','99cc99','99ccff','999933','999999','9999ff',
	    'ffff33','ffff99','ffffff','ffcc33','ffcc99','ffccff','ff9933','ff9999','ff99ff',
	    '006633','006699','0066ff','003333','003399','0033ff','000033','000099','0000ff',
	    '666633','666699','6666ff','663333','663399','6633ff','660033','660099','6600ff',
	    '996633','996699','9966ff','993333','993399','9933ff','990033','990099','9900ff',
	    'ff6633','ff6699','ff66ff','ff3333','ff3399','ff33ff','ff0033','ff0099','ff00ff'
	],
	grayColors=[
	    'ffffff','dddddd','cococo','969696','808080','646464','4b4b4b','242424','000000'     
	],
	int2Hex=function(i){
		var h=i.toString(16);
		if(h.length==1){
			h='0'+h;
		}
		return h;
	},
	st2Hex=function(s){
		return int2Hex(Number(s));
	},
	int2Hex3=function(i){
		var h=int2Hex(i);
		return h+h+h;
	},
	toHex3=function(c){
		if(c.length>10){ // IE9
			var p1=1+c.indexOf('('),
				p2=c.indexOf(')'),
				cs=c.substring(p1,p2).split(',');
			return ['#',st2Hex(cs[0]),st2Hex(cs[1]),st2Hex(cs[2])].join('');
		}else{
			return c;
		}
	};
			
    $.widget('jazz.colorfield', $.jazz.field,{
    	
		options: /** @lends jazz.colorfield# */ {
        	/**
			 *@type String
			 *@desc 组件类型
			 *@default 'colorfield'
			 */ 
			vtype: 'colorfield',
			/**
			 *@type String
			 *@desc 当前颜色值
			 *@default null
			 */
			color: null,
			/**
			 *@type String
			 *@desc 展现方式
			 *@default 'both' 
			 *possible values 'focus','button','both'
			 *focus-焦点触发显示 button-按钮触发显示 both-两者都可触发显示
			 */
			showOn: 'both', 
			/**
			 *@type Boolean
			 *@desc 
			 *@default false
			 */
			displayIndicator: false,
			/**
			 *@type Boolean
			 *@desc 是否保留历时值 true 可保留
			 *@default false
			 */
			history: false,
			/**
			 *@type String
			 *@desc 颜色模板说明
			 *@default '全部颜色,标准颜色.'
			 */
			strings: '全部颜色,标准颜色.',
			/**
			 *@type String
			 *@desc 数据字段值
			 *@default ''
			 */
			valueField: '',
			
			// callbacks
			
			/**
			 *@desc 当输入框的值改变时触发
			 *@param {event} 事件
			 *@param {ui.options}  options配置项
			 *@param {ui.newValue} 改变后的新值
			 *@param {ui.oldValue} 改变前的旧值
			 *@event
			 *@example 
			 *初始化：
			 *<br/>$("#input_id").colorfield({change: function( event, ui ){  <br/>} <br/>});
			 *<br/><br/><br/>
			 */
			change: null
		},
		
			/** @lends jazz.colorfield */
		
			/**
			 * @desc 创建组件
			 * @private
			 * @example  this._create();
			 */ 
		 _create: function(){
			this.commonElement();
			
		 },
		 	/**
	         * @desc 初始化组件
	         * @private
	         * @example  this._init();
	         */ 
		_init: function(){
			this._paletteIdx=1;
			this._id='evo-cp'+_idx++;
			this._enabled=true;
			this.dimensionCommon();
			var color=this.options.color;
			this._isPopup=true;
			this._palette=null;
			var that = this;
			e = this.input;
			e.addClass("jazz_colorpicker_input colorPicker "+this._id);
			if(color!==null){
				e.val(color);
			}else{
				var v=e.val();
				if(v!==''){
					color=this.options.color=v;
				}
			}
			e.on('keyup onpaste', function(evt){
				var c=$(this).val();
				if(c!=that.options.color){
					that._setValue(c, true);
				}
			});
			var showOn=this.options.showOn;
			if(showOn==='both' || showOn==='focus'){
				e.on('focus', function(){
					
					that.showPalette();
				});
			}
			if(showOn==='both' || showOn==='button'){
				e.next().on('click', function(evt){
					evt.stopPropagation();
					that.showPalette();
				});
			}
			if(color!==null && this.options.history){					
				this._add2History(color);
			}
		},
		/**
         * @desc 生成颜色面板
         * @return 返回颜色面板
		 * @private
		 * @example this._paletteHTML();
         */
		_paletteHTML: function() {
			var e = this.input;
			var pageHeight = this.pageHeight(); 
			var _top = e.parent().offset().top;
			var inputDivHeight = e.parent().height();
			var scroll = document.documentElement.scrollTop;
			var b = pageHeight - (_top - scroll) - inputDivHeight -220;
			jazz.log(pageHeight+"-"+_top+"-"+inputDivHeight+"-"+scroll+"-"+b);
			var h=[], pIdx=this._paletteIdx=Math.abs(this._paletteIdx),
				opts=this.options,
				labels=opts.strings.split(',');
			/*h.push('<div class="evo-pop',_ie,' ui-widget ui-widget-content ui-corner-all"',
				this._isPopup?' style="position:absolute;top:'+b+';right:-30px;"':'', '>');*/
			
			if(b<0){
				h.push('<div class="evo-pop',_ie,' ui-widget ui-widget-content ui-corner-all"',
						this._isPopup?' style="position:absolute;bottom:19px;left:-1px;"':'', '>');
			}else{
				h.push('<div id="yanse'+pIdx+'" class="evo-pop',_ie,' ui-widget ui-widget-content ui-corner-all"',
						this._isPopup?' style="position:absolute;left:-1px;"':'', '>');
			}
			/*$("#yanse"+pIdx).css({left:'', top:''})
			           .position({
			                my: 'left top',
			                at: 'left bottom',
			                of: this.container
			            });*/ 
			
			/*h.push('<div class="evo-pop',_ie,' ui-widget ui-widget-content ui-corner-all"',
			    this._isPopup?' style="left:top;position:absolute;my:left top;at:left bottom;of:this.container"':'', '>');*/
			
			// palette
			h.push('<span>',this['_paletteHTML'+pIdx](),'</span>');
			// links
			h.push('<div class="evo-more"><a href="javascript:void(0)">', labels[1+pIdx],'</a>');
			if(opts.history){
				h.push('<a href="javascript:void(0)" class="evo-hist">', labels[5],'</a>');			
			}
			h.push('</div>');
			// indicator
			if(opts.displayIndicator){
				h.push(this._colorIndHTML(this.options.color,'left'), this._colorIndHTML('','right'));
			}
			h.push('</div>');
			return h.join('');
		},
		
		/**
         * @desc 生成颜色面板
         * @param {c} 颜色值
         * @param {fl} 方向值
         * @return 返回颜色面板
		 * @private
		 * @example this._colorIndHTML('c','fl');
         */
		_colorIndHTML: function(c, fl) {
			var h=[];
			h.push('<div class="evo-color" style="float:left"><div style="');
			h.push(c?'background-color:'+c:'display:none');
			if(isIE){
				h.push('" class="evo-colorbox-ie"></div><span class=".evo-colortxt-ie" ');
			}else{
				h.push('"></div><span ');
			}
			h.push(c?'>'+c+'</span>':'/>');
			h.push('</div>');
			return h.join('');
		},
		
		/**
         * @desc 生成颜色面板
         * @return 返回颜色面板
		 * @private
		 * @example this._paletteHTML1();
         */
		_paletteHTML1: function() {
			var h=[], labels=this.options.strings.split(','),
				oTD='<td style="background-color:#',
				cTD=isIE?'"><div style="width:2px;"></div></td>':'"><span/></td>',
				oTRTH='<tr><th colspan="10" class="ui-widget-content">';
				//bright colors
				h.push('<table class="evo-palette',_ie,'" border: 1px solid #ff0000 cellspacing="0" cellpadding="0" border="0">');
				if(!isIE){
					h.push('<tr><th colspan="10"></th></tr>');
				}
				h.push('<tr class="top">');
				for(i=0;i<9;i++){ 
					h.push(oTD, brightColors[i], cTD);
				}
				for(var r=1;r<7;r++){
					h.push('</tr><tr class="in">');
					for(i=0;i<9;i++){ 
						h.push(oTD, brightColors[r*9+i], cTD);
					}			
				}
				h.push('</tr><tr class="bottom">');
				for(i=63;i<72;i++){ 
					h.push(oTD, brightColors[i], cTD);
				}
				h.push('</tr><tr><th></th></tr><tr>');
				// gray colors
				for(i=0;i<9;i++){ 
					h.push(oTD, grayColors[i], cTD);
				}
				h.push('</tr></table>');
				return h.join('');
		/*	// base theme colors
			h.push('<table class="evo-palette',_ie,'">',oTRTH,labels[0],'</th></tr><tr>');
			for(var i=0;i<10;i++){ 
				h.push(oTD, baseThemeColors[i], cTD);
			}
			h.push('</tr>');
			if(!isIE){
				h.push('<tr><th colspan="10"></th></tr>');
			}
			h.push('<tr class="top">');
			// theme colors
			for(i=0;i<10;i++){ 
				h.push(oTD, subThemeColors[i], cTD);
			}
			for(var r=1;r<4;r++){
				h.push('</tr><tr class="in">');
				for(i=0;i<10;i++){ 
					h.push(oTD, subThemeColors[r*10+i], cTD);
				}			
			}
			h.push('</tr><tr class="bottom">');
			for(i=40;i<50;i++){ 
				h.push(oTD, subThemeColors[i], cTD);
			}
			h.push('</tr>',oTRTH,labels[1],'</th></tr><tr>');
			// standard colors
			for(i=0;i<10;i++){ 
				h.push(oTD, standardColors[i], cTD);
			}
			h.push('</tr></table>');
			return h.join(''); */
		},
		
		/**
         * @desc 判断颜色面板
		 * @param {link} 对象
		 * @private
		 * @example this._switchPalette('link');
         */
		_switchPalette: function(link) {
			if(this._enabled){
				var idx, content, label,
					labels=this.options.strings.split(',');
				if($(link).hasClass('evo-hist')){
					// history
					var h=['<table class="evo-palette"><tr><th class="ui-widget-content">',
						labels[5], '</th></tr></tr></table>',
						'<div class="evo-cHist">'];
					if(history.length===0){
						h.push('<p>&nbsp;',labels[6],'</p>');
					}else{
						for(var i=history.length-1;i>-1;i--){
							h.push('<div style="background-color:',history[i],'"></div>');
						}
					}
					h.push('</div>');
					idx=-this._paletteIdx;
					content=h.join('');
					label=labels[4];
				}else{
					// palette
					if(this._paletteIdx<0){
						idx=-this._paletteIdx;
						this._palette.find('.evo-hist').show();
					}else{
						idx=(this._paletteIdx==2)?1:2;
					}
					content=this['_paletteHTML'+idx]();
					label=labels[idx+1];
					this._paletteIdx=idx;
				}
				this._paletteIdx=idx;
				var e=this._palette.find('.evo-more')
					.prev().html(content).end()
					.children().eq(0).html(label);
				if(idx<0){
					e.next().hide();
				}
			}
		},
		
		/**
         * @desc 显示颜色面板
         * @return 返回颜色面板 
		 * @throws
		 * @example $('#input_id').colorfield('showPalette');
         */
		showPalette: function() {
		if(this._enabled){
				$('.colorPicker').not('.'+this._id).colorfield('hidePalette');
				if(this._palette===null){
					this._palette=this.input.next()
						.after(this._paletteHTML()).next()
						.on('click',function(evt){	
							evt.stopPropagation();
						});
					this._bindColors();
					var that=this;
				
					$(document.body).on('click',function(evt){
						if(evt.target!=that.input.get(0)){
							that.hidePalette();
						}
					});
					
				}
		}
			return this;
		},	
		/**
         * @desc 隐藏颜色面板
         * @return 返回颜色面板
		 * @example $('#input_id').colorfield('hidePalette');
         */
		hidePalette: function() {
			if(this._isPopup && this._palette){
				$(document.body).off('click.'+this._id);
				var that=this;
				this._palette.off('mouseover click', 'td')
					.fadeOut(function(){
						that._palette.remove();
						that._palette=that._cTxt=null;
					})
					.find('.evo-more a').off('click');
			}
		

		
			return this;
		},
		
		/**
         * @desc 绑定颜色   
		 * @private
		 * @example this._bindColors();
         */
		_bindColors: function() {
			var es=this._palette.find('div.evo-color'),
				sel=this.options.history?'td,.evo-cHist div':'td';
			this._cTxt1=es.eq(0).children().eq(0);
			this._cTxt2=es.eq(1).children().eq(0);
			var that=this; 
			this._palette
				.on('click', sel, function(evt){
					if(that._enabled){
						//alert(2);
						var c=toHex3($(this).attr('style').substring(17));
						that._setValue(c);
					}
				})
				.on('mouseover', sel, function(evt){
					if(that._enabled){
						var c=toHex3($(this).attr('style').substring(17));
						if(that.options.displayIndicator){
							that._setColorInd(c,2);
						}
						that.element.trigger('mouseover.color', c);
					}
				})
				.find('.evo-more a').on('click', function(){
					that._switchPalette(this);
				});
		},
	
		/**
         * @desc 返回颜色值   
		 * @param {value} 颜色值 
		 * @return 返回颜色值
		 * @example $('#input_id').colorfield('val','value');
         */
		val: function(value) {
			if (typeof value=='undefined') {
				return this.options.color;
			}else{
				this._setValue(value);
				return this;
			}
		},
		
		/**
         * @desc 设置颜色值  
		 * @param {c} 颜色值
		 * @param {noHide} 布尔值
		 * @private
		 * @example this._setValue('c','noHide');
         */
		_setValue: function(c, noHide) {
			c = c.replace(/ /g,'');
			this.options.color=c;
			if(this._isPopup){
				if(!noHide){
					this.hidePalette();

						this.parent.removeClass('jazz-field-comp-hover');
					
				}
				//this.element.val(c).attr('style', 'width:20px;background-color:'+c);
				$('#_co').attr('style', 'display:block;width:20px; position: absolute;height: 22px;background-color:'+c);
				var ui = {
						options: this.options,
						newValue: this.options.color,                       //新值
						oldValue: this.options.valueField                    //旧值
				};
				this._trigger('change', null,ui);
				this.options.valueField = c;
				
			}else{
				this._setColorInd(c,1);
			}
			if(this.options.history && this._paletteIdx>0){
				this._add2History(c);			
			}		
			this.input.trigger('change.color', c);
			$('#carryvalue-hide').val(c);
		},
		
		/**
         * @desc 设置颜色  
		 * @param {c} 颜色值
		 * @param {idx} 
		 * @private
		 * @example this._setColorInd('c','idx');
         */
		_setColorInd: function(c,idx) {
			this['_cTxt'+idx].attr('style','background-color:'+c)
				.next().html(c);
		},
		
		/**
         * @desc 设置组件options属性值  
		 * @param {key} options属性键值
		 * @param {value} 新值
		 * @private
		 * @example this._setOption('key','value');
         */
		_setOption: function(key, value) {
			if(key=='color'){
				this._setValue(value, true);
			}else{
				this.options[key]=value;
			}
		},
		
		/**
         * @desc 添加历史记录  
		 * @param {c} 颜色值
		 * @private
		 * @example his._add2History('c');
         */
		_add2History: function(c) {
			var iMax=history.length;
			// skip color if already in history
			for(var i=0;i<iMax;i++){
				if(c==history[i]){
					return;
				}
			}
			// limit of 28 colors in history
			if(iMax>27){
				history.shift();
			}
			// add to history
			history.push(c);
	
		},
		
		/**
         * @desc 设置为可用  
         * @return 返回当前对象
		 * @example $('#input_id').colorfield('enable');
         */
		enable: function() {
			var e=this.element;
			if(this._isPopup){
				e.removeAttr('disabled');
			}else{
				e.css({
					'opacity': '1', 
					'pointer-events': 'auto'
				});
			}
			e.removeAttr('aria-disabled');
			this._enabled=true;
			return this;
		},
		 
		/**
         * @desc 设置为不可用  
         * @return 返回当前对象
		 * @example $('#input_id').colorfield('disable');
         */
		disable: function() {
			var e=this.element;
			if(this._isPopup){
				e.attr('disabled', 'disabled');
			}else{
				this.hidePalette();
				e.css({
					'opacity': '0.3', 
					'pointer-events': 'none'
				});
			}
			e.attr('aria-disabled','true');
			this._enabled=false;
			return this;
		},
		
		/**
         * @desc 设置为不可用  
         * @return 返回当前对象
		 * @example $('#input_id').colorfield('isDisabled');
         */
		isDisabled: function() {
			return !this._enabled;
		},
		
		/**
         * @desc 销毁组件  
		 * @example $('#input_id').colorfield('destroy');
         */
		destroy: function() {
			$(document.body).off('click.'+this._id);
			if(this._palette){
				this._palette.off('mouseover click', 'td')
					.find('.evo-more a').off('click');
				if(this._isPopup){
					this._palette.remove();
				}
				this._palette=this._cTxt=null;
			}
			if(this._isPopup){
				this.element
					.next().off('click').remove()
					.end().off('focus').unwrap();						
			}
			this.element.removeClass('colorPicker '+this.id).empty();
			$.Widget.prototype.destroy.call(this);
		}
	    });
})(jQuery);(function($) {

	/**
	 * @version 1.0
	 * @name jazz.button
	 * @description 按钮组件。
	 * @constructor
	 * @extends jazz.boxComponent
	 */
    $.widget("jazz.button", $.jazz.boxComponent, {
       
        options: /** @lends jazz.button# */{
        	/**
			 *@type String
			 *@desc button组件元素name标识
			 *@default null
			 */
        	name: null,
        	
        	/**
        	 *@type String
        	 *@desc 按钮显示位置   left  center  right
        	 *@default 'left' 
        	 */
        	align: 'left',
        	
        	/**
			 *@type String
			 *@desc button组件元素显示值
			 *@default null
			 */
        	text: null,
        	
        	/**
			 *@type int
			 *@desc button组件是否可用（0任何时候可用 1选中一条记录时可用 2选中1条及以上记录时可用）---暂不可使用
			 *@default 0
			 */
        	enabletype: 0,
        	
        	/**
			 *@type String
			 *@desc button组件图片url
			 *@default null
			 */
        	iconurl: null,
        	/**
			 *@type String
			 *@desc button组件图片配合iconurl使用，切换图片使用
			 *@default null
			 */
        	toggleiconurl: null,
        	
        	/**
			 *@type Object
			 *@desc button组件图片左右位置，配合iconurl属性使用，默认值left
			 *@default left
			 */
        	iconalign: 'left',
        	
        	/**
			 *@type int
			 *@desc button组件元素显示内容（0只有图标1只有文字2图标加文字）
			 *@default 2
			 */
        	defaultview: 2,
        	
        	/**
			 *@type boolean
			 *@desc toolbar组件元素是否可用，默认为false
			 *@default false
			 */
        	disabled: false,
        	
        	//width: null, //从boxComponent中继承
        	//height: null, //从boxComponent中继承
            /**
			 *@type Object
			 *@desc button组件元素下拉menu数据项
			 *@default null
			 */
            items: null,
            
            /**
             * @type String
             * @desc 自定义按钮的样式类，用于整个按钮是一个大图片。
             * 例：.xxx { width: 32px; height: 32px; background: url("images/queding.gif") no-repeat;} 
             * @default ""
             */
            buttonclass: "",
            
        	/**
			 *@desc button组件click事件
			 *@param {event} 参数描述2
			 *@param {ui} 参数描述1
			 *@event click
			 *@example $('#id').button('option', click, function(){ });
			 */
        	click: null
        },
        
        /** @lends jazz.button */
        /**
         * @desc 创建组件
         * @private
         */ 
        _create: function() {
        	/*this.compId = this.getCompId();
        	this.element.wrap('<div class="jazz-splitbutton jazz-buttonset jazz-button-state-default"></div>');
            this.container = this.element.parent().attr("id",this.compId);
            if(this.options.disabled) {
                this.container.addClass('jazz-state-disabled');
            }
            this.element.addClass('jazz-button').text('');
            
        	this.vTypeparent = this.element.getParentComponent();
        	
        	this._rederToolbarButton();*/
        	
        	this.element.addClass("jazz-splitbutton jazz-buttonset jazz-button-state-default");
            //最外层容器
        	this.container = this.element;
            if(this.options.disabled) {
                this.container.addClass('jazz-state-disabled');
            }
            //按钮容器
            this.buttonWrap = $('<div class="jazz-button"></div>')
            	.appendTo(this.element);
            
        	this.vTypeparent = this.element.getParentComponent();
        	
        	this._rederToolbarButton();

        },
        
        /**
         * @desc 初始化组件
         * @private
         */ 
        _init: function(){
        	//渲染按钮dom
        	this.buttonWrap.children().remove();
        	
        	this._renderButtonDom();
        	//绑定按钮事件
        	if(!this.options.disabled){
        		this._bindButtonEvents();
        	}
        	//绑定下拉菜单项事件
        	if(this.options.items && this.options.items.length>0) {
	        	this._bindMenuButtonEvents();
        	}
        	if(this.vTypeparent&&this.vTypeparent.attr("vType")=="toolbar"){
        		this.vTypeparent.toolbar("computeToolbarWidthDW");
        	}
        },
        
        /**
         * @desc vtype形式初始化渲染button（当vtypeparent=toolbar时）
         * @return undefined
         * @private
         * @example  this._rederToolbarButton();
         */ 
        _rederToolbarButton: function(){
        	if(this.vTypeparent&&this.vTypeparent.attr("vType")=="toolbar"){
        		//如果jazz-toolbar-content-wrap包含有text-align:center,则说明当前是居中排序
				//此后再添加按钮都是直接放到leftToolbar中即可
        		var toolbarContentWrap = this.vTypeparent.find('div.jazz-toolbar-content-wrap');
        		var leftToolbarContent = this.vTypeparent.find("div.jazz-toolbar-left");
        		var rightToolbarContent = this.vTypeparent.find("div.jazz-toolbar-right");
				var textAlign = toolbarContentWrap.css("text-align");
				if(textAlign=="center"){
					this.container.appendTo(leftToolbarContent);
				}else{
					var align = this.options.align;
					var leftnums = leftToolbarContent.children().length;
					var rightnums = rightToolbarContent.children().length;
					if((leftnums>0||rightnums>0) &&align=="center"){
						alert("按钮左或右对齐后，不允许再居中对齐。");
					}else{
						if(align=="center"){
							toolbarContentWrap.css("text-align","center");
							this.container.appendTo(leftToolbarContent);
						}else{
							if(align=="right"){
			        			this.container.appendTo(rightToolbarContent);
			        		}else{
			        			this.container.appendTo(leftToolbarContent);
			        		}
						}
					}
				}
        	
        	}
        },
        
        /**
         * @desc 渲染button组件
         * @private
         */ 
        _renderButtonDom: function(){
        	
        	if(!this.options.buttonclass){
	        	var value = this.options.text||'jazz-button';
	            var styleClass = null;
	            if(this.options.defaultview==1){//0只有图标1只有文字2图标加文字
	            	styleClass = 'jazz-button-text-only';
	            }else if(this.options.defaultview==0){
		            styleClass = 'jazz-button-icon-only';
	            	this.buttonWrap.append('<span class="jazz-button-icon-left jazz-button-icon" style="background: url('+ this.options.iconurl +') no-repeat scroll 50% 50% ;"/>');
	            }else{
		            if(this.options.iconurl){
		            	styleClass = (value === 'jazz-button') ? 'jazz-button-icon-only' : 'jazz-button-text-icon-' + this.options.iconalign;
		            	if(this.options.iconalign){
		            		this.buttonWrap.append('<span class="jazz-button-icon-' + this.options.iconalign + ' jazz-button-icon" style="background: url('+ this.options.iconurl +') no-repeat scroll 50% 50% ;"/>');
		            	}else{
		            		this.buttonWrap.append('<span class="jazz-button-icon-left jazz-button-icon" style="background: url('+ this.options.iconurl +') no-repeat scroll 50% 50% ;"/>');
		            	}
		            }else{
		            	//styleClass ='jazz-button-text-only';
		            	styleClass ='jazz-button-text-icon-left';
	            		this.buttonWrap.append('<span class="jazz-button-icon-left jazz-button-icon jazz-button-icon-default" />');
		            }
	            }
	            
	            //this.element.addClass('jazz-button ui-widget ui-state-default ui-corner-all ' + styleClass).text('');
	            this.buttonWrap.addClass(styleClass);
	            this.buttonWrap.append('<span class="jazz-button-text">' + value + '</span>');
            
        	}else{
        		//通过样式类，整体控制button的显示风格
        		this.buttonWrap.append('<span class="jazz-button-custom-cls '+this.options.buttonclass+'"></span>');
        	}
            
            
            if(this.options.items && this.options.items.length>0) {
            	//this.element.removeClass('ui-corner-all').addClass('ui-corner-left');
	            this.menuButton = this.container.append('<div class="jazz-splitbutton-menubutton jazz-button jazz-button-icon-only"></div>').children('.jazz-splitbutton-menubutton');
	            this.menuButton.append('<span class="jazz-button-icon-left jazz-button-icon jazz-button-arrow-down"></span>').append('<span class="jazz-button-text">jazz-button</span>');
	            
                this._renderPanel();
                this.computeMenuPanel();
            }
        },
        /**
         * @desc button组合下拉按钮menu各items数据项渲染
         * @private
         */ 
        _renderPanel: function() {
            this.menu = $('<div class="jazz-menu jazz-menu-dynamic ui-widget ui-widget-content ui-corner-all ui-helper-clearfix jazz-shadow"></div>').
                    append('<ul class="jazz-menu-list ui-helper-reset"></ul>');
            this.menuList = this.menu.children('.jazz-menu-list');
            
            for(var i = 0; i < this.options.items.length; i++) {
                var item = this.options.items[i],
                menuitem = $('<li class="jazz-menuitem ui-widget ui-corner-all" role="menuitem"></li>');
                //link = $('<a class="jazz-menuitem-link ui-corner-all"><span class="jazz-menuitem-icon ui-icon ' + item.icon +'"></span><span class="ui-menuitem-text">' + item.text +'</span></a>');
                
                var link = null;
                if(!!item.icon){
	                link = $('<a class="jazz-menuitem-link ui-corner-all"><span class="jazz-menuitem-icon ui-icon" style="background: url('+ item.icon +') no-repeat scroll 50% 50% ;"></span><span class="jazz-menuitem-text">' + item.text +'</span></a>');
                }else{
                	link = $('<a class="jazz-menuitem-link ui-corner-all"><span class="jazz-menuitem-text">' + item.text +'</span></a>');
                }
                
                if(item.url) {
                    link.attr('href', item.url);
                }
                
                if(item.click) {
                    link.on('click.buttongroup', item.click);
                }
                
                menuitem.append(link).appendTo(this.menuList);
            }
            
            //可以将下拉菜单项添加到document.body上，然后再定位到container下方
            this.menu.appendTo(document.body);
        },
        
        /**
         * @desc button组件绑定响应事件
         * @return undefined
         * @private
         * @example  this._bindButtonEvents();
         */ 
        _bindButtonEvents: function(){
        	var element = this.element,
            $this = this;
            
            //处理gridpanel中toolbar定义的button数据传递问题
            var gridpanel = this.element.parents("[vtype=gridpanel]");
            
            this.element.off('mouseover.button mouseout.button')
            .on('mouseover.button', function(){
                if(!$(this).hasClass('jazz-state-disabled')) {
                    $(this).addClass('jazz-toolbar-button-hover');
                }
            }).on('mouseout.button', function() {
                $(this).removeClass('jazz-toolbar-button-hover');
            }).on('mousedown.button', function() {
                if(!$(this).hasClass('jazz-state-disabled')) {
                    //$(this).addClass('jazz-toolbar-button-pressed');
                    //1.实现iconurl与toggleiconurl切换2.字体高亮
                	if($this.options.toggleiconurl){
                		$(this).find("span.jazz-button-icon").css("background","url("+ $this.options.toggleiconurl +") no-repeat scroll 50% 50%")
                	}
                	$(this).addClass('jazz-toolbar-button-pressed');
                }
            }).on('mouseup.button', function(e) {
               	//$(this).removeClass('jazz-toolbar-button-pressed');
               	//1.实现iconurl与toggleiconurl切换2.字体取消高亮
            	if($this.options.toggleiconurl){
            		$(this).find("span.jazz-button-icon").css("background","url("+ $this.options.iconurl +") no-repeat scroll 50% 50%")
            	}
            	$(this).removeClass('jazz-toolbar-button-pressed');
            });
            
            this.buttonWrap.off('click.button')
            .on('click.button', function(e) {
                if($this.options.click){
                	var data=null;
                	if(gridpanel){
                		data = gridpanel.gridpanel("getSelection");
                	}
                	if($.isFunction($this.options.click)){
						$this.options.click.call(this,e,data,$this);
					}else{
						var callback = new Function($this.options.click);
                		callback.call(this,e,data,$this);
                    }
                }
            });
            /*.on('focus.button', function() {
                element.addClass('ui-state-focus');
            }).on('blur.button', function() {
                element.removeClass('ui-state-focus');
            }).on('keydown.button',function(e) {
                if(e.keyCode == $.ui.keyCode.SPACE || e.keyCode == $.ui.keyCode.ENTER || e.keyCode == $.ui.keyCode.NUMPAD_ENTER) {
                    element.addClass('ui-state-active');
                }
            }).on('keyup.button', function() {
                element.removeClass('ui-state-active');
            });*/
            
            if(this.menuButton&&this.menuButton.length>0){
	            this.menuButton.off('click.menuButton').on('click.menuButton', function() {
	                if($this.menu.is(':hidden'))
	                    $this.show();
	                else
	                    $this.hide();
	            });
            }
        },
        
        /**
         * @desc button组件解绑响应事件
         * @private
         */ 
        _unbindButtonEvents: function(){
            this.buttonWrap.off('click.button')
            if(this.menuButton&&this.menuButton.length>0){
	            this.menuButton.off('click.menuButton');
            }
        },
        
        /**
         * @desc button组合按钮menu各item绑定事件
         * @private
         */ 
        
        _bindMenuButtonEvents: function() {  
            var $this = this;

            this.menuList.children().on('mouseover.menuList', function(e) {
                $(this).addClass('ui-state-hover');
            }).on('mouseout.menuList', function(e) {
                $(this).removeClass('ui-state-hover');
            }).on('click.menuList', function() {
                $this.hide();
            });
            
            $(document.body).bind('mousedown.' + this.container.attr('id'), function (e) {
                if($this.menu.is(":hidden")) {
                    return;
                }

                var target = $(e.target);
                if(target.is($this.element)||$this.element.has(target).length > 0) {
                    return;
                }
                
                var offset = $this.menu.offset();
                if(e.pageX < offset.left ||
                    e.pageX > offset.left + $this.menu.width() ||
                    e.pageY < offset.top ||
                    e.pageY > offset.top + $this.menu.height()) {

                    $this.element.removeClass('ui-state-focus ui-state-hover');
                    $this.menu.hide();
                }
            });

            /*var resizeNS = 'resize.' + this.container.attr('id');
            $(window).unbind(resizeNS).bind(resizeNS, function() {
                if($this.menu.is(':visible')) {
                    $this._alignPanel();
                }
            });*/
        },
        
        /**
         * @desc 计算button组合按钮menu宽度
         * @example $("XXX").button("computeMenuPanel");
         */ 
        computeMenuPanel:function(){
        	
        	this.menu.show();
        	
        	var menuitemList = this.menuList.children('.jazz-menuitem');
        	var liContentMaxWidth=0;
        	var linkWidthDiff=0;
        	for(var i = 0; i < menuitemList.length; i++) {
        		if(i==0){
        			var w1 =  Math.round($(menuitemList[i]).children('.jazz-menuitem-link').eq(0).outerWidth());
        			var w2 =  Math.round($(menuitemList[i]).children('.jazz-menuitem-link').eq(0).width());
        			linkWidthDiff = w1-w2 ;
        		}
        		
        		var temp=0;
        		var linknodes = $(menuitemList[i]).children('.jazz-menuitem-link');
        		for(var j = 0;j<linknodes.length;j++){
        			var spanWidth=0;
        			$(linknodes[j]).children().each(function(){
        				spanWidth += parseInt($(this).width());
        			});
        			if(spanWidth>temp){
        				temp = spanWidth;
        			}
        		}
        		if(temp>liContentMaxWidth){
        			liContentMaxWidth = temp;
        		}
        	}
        	
        	if((this.element.outerWidth()+this.menuButton.outerWidth())<(liContentMaxWidth+linkWidthDiff)){
        		this.menu.width(liContentMaxWidth+linkWidthDiff+"px");
        	}else{
				this.menu.innerWidth((this.element.outerWidth()+this.menuButton.outerWidth())+"px");
        	}
        	this.menu.hide();
        },
        

        /**
         * @desc 动态改变属性
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         */
        _setOption: function(key, value){
        	switch(key){
	        	case 'width':
	        		this.options.width = value;
	        		this.buttonWrap.width(value);
	        		break;
	        	case 'height':
	        		this.options.height = value;
	        		this.buttonWrap.height(value);
	        		break;
        	}
        	this._super(key, value);
        },
        
        _destroy: function(){
        	
        },
        
        /**
         * @desc button组合按钮menu绝对定位位置
         * @private
         */
        _alignPanel: function() {
            this.menu.css({left:'', top:'','z-index': ++jazz.zindex}).position({
                my: 'left top',
                at: 'left bottom',
                of: this.container
            });
        },
        
        changeButtonOptions: function(opts){
        	this._setOptions(opts);
        },
        
        show: function() {
            this._alignPanel();
            //this.menuButton.trigger('focus');
            this.menu.show();
            //this._trigger('show', null);
        },

        hide: function() {
            this.menuButton.removeClass('ui-state-focus');
            this.menu.fadeOut('fast');
            //this._trigger('hide', null);
            this._event('hide', null);
        },
        
        /**
         * @desc 关闭button，使不可用
         * @example  $('XXX').button('disable');
         */ 
        disable: function(){
        	this.container.addClass('jazz-state-disabled');
        	this._unbindButtonEvents();
        },

        /**
         * @desc 打开button，使可用
         * @example  $('XXX').button('enable');
         */ 
        enable: function(){
        	this.container.removeClass('jazz-state-disabled');
        	this._bindButtonEvents();
        },
        /**
         * @desc button组件高亮按钮选中样式
         * @public
         * @example  $('XXX').button('highlight');
         */
		highlight: function(){
			this.element.trigger('mousedown.button');
		},
		/**
         * @desc buttton组件取消高亮按钮选中样式
         * @public
         * @example  $('XXX').button('unhighlight');
         */
		unhighlight: function(){
        	this.element.trigger('mouseup.button');
		}
    });
    
})(jQuery);
(function($) {
	/**
	 * 分页条模板元素
	 * 首页、下一页、上一页、末页、刷新按钮等
	 * 定义dom样式和事件
	 * markup html代码，定义dom结构
	 * create 生成当前元素地方法
	 * update 当分页条翻页或更新时，更新当前元素的状态的方法
	 * align  声明当前元素在分页条中的位置，左侧或右侧
	 */
    var ElementHandlers = {
    	'{ComboPageSize}': {
            markup: '<span class="jazz-paginator-pagesize"><select class="jazz-paginator-pagesize-select"><option value="10">10</option><option value="15">15</option><option value="20">20</option><option value="25">25</option><option value="30">30</option></select></span>',
            create: function(paginator) {
                var element = $(this['markup']);
                element.find('select.jazz-paginator-pagesize-select').attr("value",paginator.options.pagerows);
                element.find('select.jazz-paginator-pagesize-select').off('change.pagesize').on('change.pagesize', function() {
                	paginator.gridtableObject.gridtable("clickGridTablePaginator",0,parseInt(this.value));
                });
                
                return element;
            },
            align: 'left',       
            update: function(element, state) {
                
            }
        },
    	
        '{FirstPageLink}': {
            markup: '<span title="首页" class="jazz-paginator-first jazz-paginator-element jazz-state-default jazz-corner-all"><span class="jazz-icon jazz-icon-seek-first">p</span></span>',
            create: function(paginator) {
                var element = $(this["markup"]);
                if(paginator.options.page == 0) {
                    element.addClass('jazz-state-disabled');
                    element.children().addClass("jazz-icon-disabled");
                }
                
                element.on('click.paginator', function() {
                    if(!$(this).hasClass("jazz-state-disabled")) {
                        paginator.option('page', 0);
                    }
                });
                                
                return element;
            },
            align: 'left',       
            update: function(element, state) {
                if(state.page == 0){
                	element.addClass('jazz-state-disabled').removeClass('jazz-state-hover jazz-state-active');
                    element.children().addClass("jazz-icon-disabled");
                }else{
                	element.removeClass('jazz-state-disabled');
                	element.children().removeClass("jazz-icon-disabled");                	
                }
            }
        },
                
        '{PreviousPageLink}': {
            markup: '<span title="上一页" class="jazz-paginator-prev jazz-paginator-element jazz-state-default jazz-corner-all"><span class="jazz-icon jazz-icon-seek-prev">p</span></span>',
            create: function(paginator) {
                var element = $(this["markup"]);
                if(paginator.options.page == 0) {
                    element.addClass('jazz-state-disabled');
                    element.children().addClass("jazz-icon-disabled");
                }
                element.on('click.paginator', function() {
                    if(!$(this).hasClass("jazz-state-disabled")) {
                        paginator.option('page', paginator.options.page - 1);
                    }
                });
                
                return element;
            },
            align: 'left',       
            update: function(element, state) {
                if(state.page == 0){
                    element.addClass('jazz-state-disabled').removeClass('jazz-state-hover jazz-state-active');
                    element.children().addClass("jazz-icon-disabled");
                }else{
                    element.removeClass('jazz-state-disabled');
                    element.children().removeClass("jazz-icon-disabled");
                }
            }
        },
        
        '{NextPageLink}': {
            markup: '<span title="下一页" class="jazz-paginator-next jazz-paginator-element jazz-state-default jazz-corner-all"><span class="jazz-icon jazz-icon-seek-next">p</span></span>',
            create: function(paginator) {
                var element = $(this["markup"]);
                if(paginator.options.page == (paginator.getPageCount() - 1)) {
                    element.addClass('jazz-state-disabled').removeClass('jazz-state-hover jazz-state-active');
                    element.children().addClass("jazz-icon-disabled");
                }
                element.on('click.paginator', function() {
                    if(!$(this).hasClass("jazz-state-disabled")) {
                        paginator.option('page', paginator.options.page + 1);
                    }
                });
                
                return element;
            },
            align: 'left',    
            update: function(element, state) {
                if(state.page == (state.pagecount - 1)) {
                    element.addClass('jazz-state-disabled').removeClass('jazz-state-hover jazz-state-active');
                    element.children().addClass("jazz-icon-disabled");
                }else{
                    element.removeClass('jazz-state-disabled');
                    element.children().removeClass("jazz-icon-disabled");
                }
            }
        },
        
        '{LastPageLink}': {
            markup: '<span title="末页" class="jazz-paginator-last jazz-paginator-element jazz-state-default jazz-corner-all"><span class="jazz-icon jazz-icon-seek-end">p</span></span>',
            create: function(paginator) {
                var element = $(this["markup"]);
                
                if(paginator.options.page == (paginator.getPageCount() - 1)) {
                    element.addClass('jazz-state-disabled').removeClass('jazz-state-hover jazz-state-active');
                    element.children().addClass("jazz-icon-disabled");
                }
                
                element.on('click.paginator', function() {
                    if(!$(this).hasClass("jazz-state-disabled")) {
                        paginator.option('page', paginator.getPageCount() - 1);
                    }
                });
                
                return element;
            },
            align: 'left',       
            update: function(element, state) {
                if(state.page == (state.pagecount - 1)){
                    element.addClass('jazz-state-disabled').removeClass('jazz-state-hover jazz-state-active');
                    element.children().addClass("jazz-icon-disabled");
                }else{
                    element.removeClass('jazz-state-disabled');
                    element.children().removeClass("jazz-icon-disabled");
                }
            }
        },
                
        '{PageLinks}': {
            markup: '<span class="jazz-paginator-pages" style="line-height:20px;"></span>',
            create: function(paginator) {
                var element = $(this.markup);
            	element.append('<span class="jazz-state-default jazz-paginaotr-nowpage jazz-paginator-info">第</span>'
        			+ '<span class="jazz-state-default jazz-paginator-input">&nbsp;<input type="text" value="1" /></span>' 
        			+ '<span class="jazz-state-default jazz-paginator-info">页，共' 
        			+ paginator.getPageCount() + '页</span>');
                
                var inputw = element.find('input[type="text"]');
                
                //获取焦点事件
                inputw.focus(function(){
                	$(this).parents('.jazz-paginator').find('.jazz-paginaotr-hidden-button')
                		.css({'left':"105px"})
                		.show()
                		.animate({'left': '123px', 'opacity': '1'}, "slow");
                });
                //失去焦点事件
                inputw.blur(function(){
                	$(this).parents('.jazz-paginator').find('.jazz-paginaotr-hidden-button')//.hide()
                		.animate({'left': '100px', 'opacity': '0'}, "slow", function(){
                			$(this).hide();
                		});
                });
                
                return element;
            },
            align: 'left',  
            
            update: function(element, state) {
                var input = element.find(".jazz-paginator-input input");
                input.val(parseInt(state.page)+1);
            }
        },
        '{PageInfo}': {
            markup: '<span class="jazz-paginator-element jazz-paginator-info jazz-paginator-text"></span>',
            create: function(paginator) {
                var element = $(this.markup),
                	nowP = (paginator.options.pagerows * (paginator.options.page+1)),
                    html = "当前显示"+(paginator.options.pagerows * paginator.options.page + 1)
                    	+ "-"+(nowP > paginator.options.totalrecords 
                    		? paginator.options.totalrecords 
                    		: nowP)+"，共"
                    	+ paginator.options.totalrecords + "条";

                element.text(html);

                return element;
            },
            align: 'right',       
            update: function(element, state) {
            	var nowP = state['first'] + parseInt(state['pagerows']);
            	var html = "当前显示"+(state.first + 1)
                	+ "-"+(nowP > state['totalrows'] 
                		? state['totalrows'] 
                		: nowP)+"，共"
                	+ state['totalrows'] + "条";

            	element.text(html);
            }
        },
        '{PageGoto}': {
            markup: '<span class="jazz-paginator-goto-info">跳转到 '
            	+'<input type="text" name="p_num" class="jazz-paginator-goto-input" />'
            	+'</span><span class="jazz-paginator-page jazz-paginator-element'
            	+' jazz-state-default jazz-paginator-goto"></span>',
            create: function(paginator) {
                var element = $(this['markup']);
                element.on('click.paginator', function() {
                    var p_num = parseInt($(this).parent().find('input.jazz-paginator-goto-input').val());
                    if(!p_num || !jazz.isNumber(p_num) || (p_num < 1) || (p_num > paginator.getPageCount())){
                    	$(this).parent().find('input.jazz-paginator-goto-input').val('');
                        return;
                    }
                    paginator.setPage(p_num-1);
                });

                return element;
            },
            update: function(element, state) {
                element[0].value = "";
            }
        },
        
        '{PageRefresh}' : {
        	markup: '<span title="刷新" class="jazz-paginator-refresh jazz-paginator-element'
                +' jazz-state-default jazz-corner-all"><span class="jazz-icon">'
                +'p</span></span>',
        	align: 'left',
            create: function(paginator){
        		var element = $(this['markup']);
                element.on('click.paginator', function() {
                    paginator.setPage(0, 'refresh');
                });

                return element;
        	},
        	update: function(element, state){
        		
        	}
        }
    };

 
    /** 
     * @version 0.5
     * @name jazz.paginator
     * @description 分页组件。
     * @constructor
     * @extends jazz.boxComponent
     * @require
     * @example $('#div_id').paginator();
     */    
    $.widget("jazz.paginator", $.jazz.boxComponent, {
       
        options: /** @lends jazz.paginator# */ {
			
			/**
			 *@desc 记录总数
			 *@default 0
			 */
            totalrecords: 0,
            
			/**
			 *@desc 当前显示第几页
			 *@default 0
			 */            
            page: 0,
            
			/**
			 *@desc 每页的记录数
			 *@default 10
			 */              
            pagerows: 10,
            
			/**
			 *@desc 模板元素
			 */
            template: '{FirstPageLink} {PreviousPageLink} {PageLinks} {LastPageLink} {NextPageLink} {PageInfo}',
            
            /**
             * @desc 自定义扩展按钮
             * @default []
             */
            buttons: [],
            
            /**
             * @desc 跳转页码执行的回调函数
             * @default null
             * @example function(page){
             * 	//todo...
             * }
             */
            gopage: null
        },
        
		/**
		 *@desc 创建组件
		 *@private
		 */         
        _create: function() {
            this.element.addClass('jazz-paginator');
            this.element.addClass("jazz-paginator-ext");
            this.element.append('<div class="jazz-paginator-content"><div class="jazz-paginator-left"></div><div class="jazz-paginator-right"></div><div style="clear:both;"></div></div>');
            this.id = this.element.attr('id');
            if(!this.id){
            	this.id = this.element.uniqueId().attr('id');
            }
            
            this.options.totalrecords= this.options.totalrecords||0;
            
            this.paginatorElements = [];
            this.options.template = '{FirstPageLink}  {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {PageRefresh} {PageInfo}';

            var elementKeys = this.options.template.split(/[ ]+/),
            	paginaotrLeft = this.element.find('.jazz-paginator-left'),
            	paginaotrRight = this.element.find('.jazz-paginator-right');
            for(var i = 0, len = elementKeys.length; i < len; i++) {
                var elementKey = elementKeys[i],
                handler = ElementHandlers[elementKey];
                if(handler) {
                    var paginatorElement = handler.create(this);
                    this.paginatorElements[elementKey] = paginatorElement;
                    if(handler['align'] == 'left'){                    	
                    	paginaotrLeft.append(paginatorElement);
                    }else{
                    	paginaotrRight.append(paginatorElement);                    	
                    }
                }
            }
            this.addButton();
            
            /**
             * 添加隐藏按钮
             */
            this.element.find(".jazz-paginator-content")
            	.append("<a href='javascript:;' class='jazz-paginaotr-hidden-button'>确定</a>");
            
            //暂时由gridpanel与paginator的绑定
            var parent = this.element.getParentComponent();
            if(parent){
            	if(parent.attr("vtype")=="gridpanel"){
            		this.gridpanel = parent.data("gridpanel");
            	}
            }
            
            this._bindEvents();
        },
        
		/**
		 *@desc 绑定事件
		 *@private
		 */      
        _bindEvents: function() {
        	var $this = this,
        		input;

        	input = $this.element.find(".jazz-paginator-input input");
        	input.on("keypress", function(e){
        		var curKey = e.keyCode;
        		if(curKey == 13){
        			var inputVal = $this.element.find('input[type="text"]').val();
            		if($this._isPageLegal(inputVal) 
            				&& (inputVal-1) != $this.options.page){            			
            			$this.setPage(inputVal-1);
            		}
        		}
        	});
            //}
            $this.element.find('.jazz-paginaotr-hidden-button')
            	.off('click.paginator')
            	.on('click.paginator', function(){
            		var inputVal = $this.element.find('input[type="text"]').val();
            		if($this._isPageLegal(inputVal) 
            				&& (inputVal-1) != $this.options.page){            			
            			$this.setPage(inputVal-1);
            		}
            	});
        },
        
        /**
         * @desc 检查要跳转的页码是否合法
         * @param page 要跳转的页码
         * @returns {Boolean} true 当前要跳转的页码合法 <br>false 不合法
         * @private 
         */
        _isPageLegal: function(page){
        	if(!/\d+/.test(page)){
        		return false;
        	}
        	if(page < 0 || page > Math.ceil(this.options.totalrecords/this.options.pagerows)){
        		return false;
        	}
        	return true;
        },
        
		/**
		 *@desc 重置options属性值
		 *@param {key} options属性
		 *@param {value} options属性对应的值
		 *@private
		 */          
        _setOption: function(key, value) {
            if(key == 'page') {
                this.setPage(value);
            }
            else {
                $.Widget.prototype._setOption.apply(this, arguments);
            }
        },
            
		/**
		 *@desc 设置当前显示的页面
		 *@param {p}  当前的页面数
		 *@param {silent} 是否出触发paginate事件 true 不触发
		 */         
        setPage: function(p, silent) {
            var $this = this,
                pc = $this.getPageCount();
            //当前页面必须大于等于0并且小于总页数而且不能喝当前页码相等
//          排除强制刷新的情况，silent为refresh标志并且刷新页面到第一页
            if(p >= 0 && p < pc && ($this.options.page != p || (silent==='refresh' && p===0))) {
                var newState = {
                    first: $this.options.pagerows * p,
                    pagerows: $this.options.pagerows,
                    page: p,
                    pagecount: pc,
                    totalrows: $this.options.totalrecords
                };
                
                this.options.page = p;

                if(!silent || silent==='refresh') {
                    var gopage = $this.options.gopage;
        			if($.isFunction(gopage)){            				
        				$this._trigger('gopage', null, newState);
        			}else if($.isFunction(window[gopage])){            					
    					window[gopage].call(this, newState);
    				}else{
    					if($this.gridpanel){
                        	$this.gridpanel.bindPaginatorClickEvent((p+1), $this.options.pagerows);
                        }
    				}
                }
                
                this._updateUI(newState);
            }
        },
        
        /**
         * 刷新分页条
         * @param rows 一页数据条数
         * @param totalRows 数据总数
         */
        updatePage : function(rows, totalRows){
        	this.options.pagerows = rows;
        	this.options.totalrecords = totalRows;
        	this.element.find('.jazz-paginator-left').children().remove();
        	this.element.find('.jazz-paginator-right').children().remove();
        	
        	var elementKeys = this.options.template.split(/[ ]+/),
        	paginaotrLeft = this.element.find('.jazz-paginator-left'),
        	paginaotrRight = this.element.find('.jazz-paginator-right');
	        for(var i = 0, len = elementKeys.length; i < len; i++) {
	            var elementKey = elementKeys[i],
	            handler = ElementHandlers[elementKey];
	            if(handler) {
	                var paginatorElement = handler.create(this);
	                this.paginatorElements[elementKey] = paginatorElement;
	                if(handler['align'] == 'left'){                    	
	                	paginaotrLeft.append(paginatorElement);
	                }else{
	                	paginaotrRight.append(paginatorElement);                    	
	                }
	            }
	        }
            this.addButton();
            this._bindEvents();
            
            //更新分页条后
            //默认导航到第一条
            this.setPage(0);
            
        },
        
		/**
		 *@desc 修改分页组件的显示
		 *@param {state.first} 当前页首条记录的所引值
		 *@param {state.pagerows}  行数
		 *@param {state.page}  当前显示的第几页页数
		 *@param {state.pagecount} 总的页面数
		 *@param {state.pageLinks} 分页条显示页码的按键个数
		 */
        _updateUI: function(state) {
            for(var paginatorElementKey in this.paginatorElements) {
                ElementHandlers[paginatorElementKey].update(this.paginatorElements[paginatorElementKey], state);
            }
        },
                
		/**
		 *@desc 获取页面数量
		 *@return 页面数
		 */           
        getPageCount: function() {
            return Math.ceil(parseInt(this.options.totalrecords) / parseInt(this.options.pagerows))||1;
        },

        /**
         * @desc 添加自定义按钮
         */
        addButton : function(){
            if(this.options.buttons === false){
                return;
            }
            var $this = this,
            	btns = $this.options.buttons;
            
            for(var i = 0, len = btns.length; i < len; i++){
                var btn = btns[i],
                    id = btn.id || this.element.uniqueId(),
                    btnHtml = '';
                if($.isFunction(btn.renderer)){
                    btnHtml = btn.renderer.call(this);
                }else{
                    btnHtml = "<span>" + (btn.title ? btn.title : "") + "</span>";
                }
                $(btnHtml).appendTo(this.element.find('.jazz-paginator-left'));
                $this.element.find("> .jazz-paginator-refresh").attr('id', this.id+'_'+id)
                	.addClass('jazz-paginator-element jazz-paginator-custom ' +
                		(btn.classname ? btn.classname : ""));
                $this.element.find('.jazz-paginator-refresh')
                	.on('click', function(){
            		if($.isFunction(btn['callback'])){                			
            			btn.callback.call($this);
            		}
            	});
            }
        }
    });
})(jQuery);(function ($, undefined) {
	
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
             * @{rows} gridpanel组件加载的数据
             * @default null
             */
            datarender: null,
            /**
             * @function
             * @desc 初始化gridpanel组件加载数据完成后，执行回调
             * @{rows} gridpanel组件加载的数据
             * @{pagination} 数据分页信息
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
            items: null
        	
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
            
            //2.为gridpanel表明vtype类型，这个在this._super()中已经写入
            //this.element.attr('vtype', this.options.vtype);
            
            //3.设置gridpanel的样式
        	this.content.css('overflow', 'hidden');
        },
		/**
         * @desc 初始化gridpanel组件
         * @private
         */ 
        _init : function(){
        	//4.gridpanel初始化样式
        	this._super();
        	
        	//5.不同的组件创建方式：vtype/$()/jazz.widget
        	var el = this.element;
        	this.childrenVtypeObj = this.content.children('div[vtype]');
        	if(this.childrenVtypeObj.length==0){
	        	//$()/jazz.widget方式初始化gridpanel相关子组件
	        	this._transformOptions(this.options.items);
        	}
        	
            //6.缓存gridpanel子组件操作对象(注意：vtype形式时，子组件并未创建完成)
        	this.gtoolbar = el.getChildrenComponentByVtype("toolbar");
	        this.gcolumn = el.getChildrenComponentByVtype("gridcolumn");
	        this.gtable = el.getChildrenComponentByVtype("gridtable");
	        this.gcard = el.getChildrenComponentByVtype("gridcard");
	        this.gpaginator = el.getChildrenComponentByVtype("paginator");
        	
	        //8.设置子组件隐藏与显示、布局
	        this._settingGridpanelComponentView();
            
            //gridpanel持有gridtable和gridcard共享的数据
            //缓存四个数据，以便进行表格和卡片切换时进行同步
            //1.分页信息paginationinfo
            //2.查询条件信息queryparams
            //3.排序信息sortparas
            //4.原始数据rows
            
            this.paginationInfo = {};
            this.rows = [];
            
            //11.girdpenel获取数据，并且循环判断是否需要reload子组件数据渲染
            this.initLoadData();
        },
        
        /**
         * @desc 设置gridpanel子组件隐藏与显示、布局
         * @private
         * @example this._settingGridpanelComponentView();
         */
        _settingGridpanelComponentView: function(){
        	
        	//8.设置子组件隐藏与显示、布局
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
	        //9.gridpanel组件pagearea高度设置，分两种情况：
            //9.1 gridpanel设置layout：fit和height属性值时,pagearea使用fit布局
            //9.2 gridpanel非fit布局且无height值时，pagearea不fit布局，随卡片和表格内容扩充
            if(parseInt(this.options.height)>0||this.options.layout=="fit"){
	            var pagearea=null;
	            if(this.gtable){
	            	pagearea = this.gtable.parent();
	            }else if(this.gcard){
	            	pagearea = this.gcard.parent();
	            }
	            
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
	        	
	            if(this.options.defaultview=="card"){
	            	if(pagearea.hasClass("jazz-pagearea")){
		        		var h = this.content.height()-toolbarheight-paginatorheight;
			        	pagearea.height(h);
		        	}
	            }else{
	            	if(pagearea.hasClass("jazz-pagearea")){
		        		var h = this.content.height()-toolbarheight-gridcolumnheight-paginatorheight;
			        	pagearea.height(h);
		        	}
	            }
            }
            
            //10.gridpanel表格和卡片视图隐藏、显示
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
		 * @desc 添加行记录
		 * @param {data} 行数据数组 [{key1: value1, key2: value2, ……}]
		 * @param {addSuccessCallback} 新增记录成功后执行回调函数
		 * @public
		 * @example $("#gridpanel").gridpanel("addRow",data,function(data){...});
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
         * @desc 该方法被updateRowByIndex替代
         * @deprecated
         * @param {data} 更新数据
         * @param {rowIndex} 表格行或卡片位置坐标
         * @public
         * @example $("#gridpanel").gridpanel("updateRow",data,rowIndex);
         */
	    updateRow : function(data, rowIndex){
	    	//gridtable和gridcard各自updateRow方法
	    	this.updateRowByIndex(data, rowIndex);
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
	    _triggerGridpanelEvent: function(eventtype,arg1,arg2){
        	if(this.options[eventtype]){
            	if($.isFunction(this.options[eventtype])){
                    this.options[eventtype].call(this, arg1, arg2);
            	}else{
            		var eventtype = this.options[eventtype];
            		if(eventtype.indexOf("(")!=-1){
	            		eventtype = eventtype.substr(0,eventtype.indexOf("("));
            		}
            		eval(eventtype).call(this, arg1, arg2);
            	}
        	}
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
         * @desc 根据行号删除数据
         * @param {index} 行号
         */
        removeRow: function(index){
    		this.removeRowByIndex(index);
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
			alert(data["rowuuid"]);
    		//1.分别删除表格和卡片
			if(this.gtable){
	        	var gridtable = this.gtable.data('gridtable');
	            gridtable.removeRowById(data["rowuuid"]);
        	}
			if(this.gcard){
        		var gridcard = this.gcard.data('gridcard');
	            gridcard.removeCardById(data["rowuuid"]);
        	}
			return data;
        },
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
	            var params = {
	            	url: $this.options.dataurl,
	            	params: $this.options.dataurlparams,
	            	pageparams: $this.paginationInfo,
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
			this._triggerGridpanelEvent("datarender",rowsdata,null);
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
        			if(paginator){
	            		paginator.updatePage($this.paginationInfo['pagerows'], $this.paginationInfo['totalrows']);
        			}else{
        				loopReloadData($this.gpaginator,'paginator',0);
        			}
        		}
        	//}
        	
        	//三、执行回调函数
        	if($this.options["dataloadcomplete"]){
            	if($.isFunction($this.options["dataloadcomplete"])){
                    //$this._trigger("dataloadcomplete",$this.rows,$this.paginationInfo);
                    $this.options["dataloadcomplete"].call($this,$this.rows,$this.paginationInfo);
            	}else{
            		var dataloadcomplete = $this.options["dataloadcomplete"];
            		if(dataloadcomplete.indexOf("(")!=-1){
	            		dataloadcomplete = dataloadcomplete.substr(0,dataloadcomplete.indexOf("("));
            		}
            		eval(dataloadcomplete).call($this,$this.rows,$this.paginationInfo);
            	}
        	}
        	
        	
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
            			compObject.updatePage($this.paginationInfo['pagerows'], $this.paginationInfo['totalrows']);
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
			var tempArray = [];
			for(var i=0,len=codeColumn.length;i<len;i++){
				var temp = codeColumn[i];
				columnname = temp.columnname;
				valueset = temp.valueset;
				if(valueset){
					
					if(valueset.indexOf('[')==0&&valueset.lastIndexOf(']')==valueset.length-1){
						valueset = eval('('+valueset+')');
						tempArray.push({'columnname':columnname,'valueset':'','resultset':valueset,'datasettype':'static'});
	            	}else {
	            		if(valueset.indexOf('{')!=-1||valueset.indexOf('{')!=-1){
	            			//初步限制url获取数据集数据格式
	            		}else{
		            		tempArray.push({'columnname':columnname,'valueset':valueset,'resultset':null,'datasettype':'url'});
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
            var columnName="";
            var resultset=null;
            for (var i = 0; i < valuesetColumn.length; i++) {
            	var codeColumn = valuesetColumn[i];
            	columnName = codeColumn['columnname'];
            	resultset = codeColumn['resultset'];
            	if(resultset){
            		for(var j = 0, len = rowsData.length; j < len; j++ ){
	            		var row = rowsData[j];
	            		var oldText = row[columnName];
	            		for(var n=0;n<resultset.length;n++){
	            			if(oldText==resultset[n].value){
	            				row[columnName] = resultset[n].text;
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
		                	for(var key in data['data']){
			                    valuesetColumn[index]['resultset']=data['data'][key];
		                	}
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
        reload: function(fn) {
        	this._loadGirdpanelData("reload",fn);
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
        _loadGirdpanelData: function(t,fn){
        	this.rows = null;
        	this.resetQueryDataParams(t);
    		this.requestPageData(t,fn);
        },
        /**
         * @desc gridpanel统一分页请求数据
         * @param {falg} 不同逻辑数据获取（查询、重载、排序）的标记
         * @param {fn} 数据重载后执行回调
         * @private
         * @example this.requestPageData('query',flag,requestdatacomplete);
         */
        requestPageData: function (flag,fn) {
            var $this = this;
            var url = $this.options.dataurl,
            	urlparams = $this.options.dataurlparams,
            	qparams = $this.options.queryparams,
            	pginfo = $this.paginationInfo, 
            	sparams = $this.options.sortparams;
            if (url) {
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
        	//对待渲染数据进行datarender和数据类型（datatype、dataformat）转换进行处理
		    this._processGridpanelData(data);
        	if(this.gtable){
        		this.gtable.gridtable("renderGridtableData",data);
        	}
			if(this.gcard){
        		this.gcard.gridcard("renderGridcardData",data);
        	}
        	//reload/query/reloadcurrentpage/重新更新绑定分页条，更新状态
        	//paginator 不再重新绑定更新状态
        	var a = this.paginationInfo;
        	if(flag){
            	if(this.options.isshowpaginator && this.gpaginator){
            		this.gpaginator.paginator("updatePage",a['pagerows'], a['totalrows']);
            	}
        	}
        	if(fn && $.isFunction(fn)){
	        	fn.call(this,this.rows,a);
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
	        			this.gcolumn.gridcolumn('clearColumnSortedStyle');
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
	        			this.gcolumn.gridcolumn('clearColumnSortedStyle');
	        		}
        			break;
        		case 'paginator':
        			this.options.sortparams['sortName']='';
		       		this.options.sortparams['sortFlag']='';
	        		if(this.gcolumn){
	        			this.gcolumn.gridcolumn('clearColumnSortedStyle');
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
	             * @desc gridcolumn组件表格头列定义
	             * @default null
				 */
            	content: null
			},
	
			_create: function(){
				//1.设置gridcolumn样式
				this.compId  = this.getCompId();
				var el = this.element;
				el.attr("id",this.compId+'_gridcolumn').addClass("jazz-gridcolumn").css({'width':'100%', 'overflow': 'hidden'});
				
				//2.解析options，将gridpanel的options缓存
				var vtypeparent = this.element.getParentComponent();
				if(vtypeparent&&vtypeparent.attr("vtype")=="gridpanel"){
					this.gridpanel = $(vtypeparent).data('gridpanel');
					var gridoptions = this.gridpanel.options;
					delete gridoptions["content"];
					$.extend(this.options, gridoptions);//可以考虑有针对性的优化options
				}
				//3.解析gridcolumn表格头元素headers
            	var tempObj = null, headers = [];
				if(el.children()&&el.children().length>0){
	            	tempObj = el.children();
				}else{
					if(this.options["content"]){
						tempObj = $(this.options["content"]);
					}
				}
				//获取全部子元素,解析头部内容,解析成[[{},{}],[{},{},{}],……], 这样后边的程序的逻辑就不需要改变
				if(tempObj){
	            	$.each(tempObj, function(i, content){
	            		var t = [], str = '';
	            		$.each($(content).children(), function(j, objs){
	            			var obj = $(objs);
	            			str = {text: obj.attr("text"), textalign: obj.attr("textalign"), name: obj.attr("name")
	            				  ,visible: obj.attr("visible"), width: obj.attr("width")
	            				  ,datatype: obj.attr("datatype"), dataformat: obj.attr("dataformat")
	            				  ,textdisplaytype: obj.attr("textdisplaytype"),rowspan: obj.attr("rowspan")
	            				  ,valueset: obj.attr("dataurl"), key: obj.attr("key"), colspan: obj.attr("colspan")
	            				  ,sort: obj.attr("sort")};
	            			t.push(str);
	            		});
	            		headers.push(t);
	            	});
	            	this.options.header = headers;
	            	tempObj.remove();
				}
				
				var tableclass = "jazz-grid-column-table";
				if(this.options.wordbreak||this.options.wordbreak=="true"){
					tableclass += " jazz-grid-column-table-wordbreak";
				}
				var div = '<div id="'+this.compId+'_columns" class="jazz-grid-columns" style="display: block;">'
					    + '<table id="'+this.compId+'_table" class="'+tableclass+'" cellspacing="0" cellpadding="0" border="0">'
					    + '<colgroup></colgroup><tbody></tbody></table></div>';
					el.append(div);
					
				this.columns = $('#'+this.compId+'_columns');
				this.table = $('#'+this.compId+'_table');
				this.colgroup = this.table.children('colgroup');
				this.tbody = this.table.children('tbody');		
			},
			
			_init: function(){
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
				
				if(this.options.hideheader||this.options.hideheader==true){
					this.element.hide();
				}
			},
			/**
			 * @desc 重新计算设置splitter的高度，解决splitter不同浏览器下height：100%问题
			 * @private
			 */
			_calculateColumnSplitterHeight: function(){
				var splitterObj = this.tbody.find(".jazz-grid-column-splitter");
				$.each(splitterObj, function(i, obj) {
					var tdHeight = $(obj).parents('.jazz-grid-headerCell').height();
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
			    	header2 += '<tr>';
			    	
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
			                		'datatype':celldata.datatype,'dataformat':celldata.dataformat};
			            		//this.cols.push({'columnname':celldata.name,'sort':celldata.sort,'index':colindex,'columnrender':celldata.columnrender});
			                	//存储需要代码集列
			                	this.toCodeColumn.push({'columnname':celldata.name,'valueset':celldata.valueset});
			                	
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
			    //生成colgroup DOM结构,只生成dom暂时宽度不赋值
			    this._createColgroupDom();
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
				
				//除去序号和选择框列宽度，在进行分配表格列宽
				/*if((this.options.isshowselecthelper == true || this.options.isshowselecthelper == 'true') 
	            	&& (this.options.selecttype != 0 || this.options.selecttype != '0')){
                    width_table -= 24+17;
	            }	
				if(this.options.lineno == true || this.options.lineno == 'true'){
					var linowidth = this.options.linenowidth?parseInt(this.options.linenowidth):30;
					width_table -= linowidth;
				}*/
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
			_createColgroupDom: function(){
				//2.生成colgroup DOM结构
				var linowidth="";
				this.colgroup_col="";
	            if((this.options.isshowselecthelper == true || this.options.isshowselecthelper == 'true') 
	            	&& (this.options.selecttype != 0 || this.options.selecttype != '0')){
	            	this.colgroup_col += '<col class="jazz-grid-colgroup-col" style="width:24px;" name="selecthelper" />';
	            }	
				if(this.options.lineno == true || this.options.lineno == 'true'){
					linowidth = this.options.linenowidth?parseInt(this.options.linenowidth)+"px":"30px";
					this.colgroup_col += '<col class="jazz-grid-colgroup-col" style="width:'+linowidth+';" name="rowlineno" />';
				}
	        	for(var i=0, len=this.columnWidth.length; i<len; i++){
			    	var temp = this.columnWidth[i];
			    	//var visible = temp['visible'];
			    	var colname = temp['columnname'];
			    	
			    	this.colgroup_col += '<col class="jazz-grid-colgroup-col" name="'+colname+'" />';
			    }
			    this.colgroup.append(this.colgroup_col);
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
			    	this.colgroup.find('col[name="'+colname+'"]').css("width",width+"px");
			    }
			    this.table.width(this.tableWidth);
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
									
							        var obj = $this.colgroup.children().eq(index); //列col对象
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
							        //$this.header.width(newTableWidth);
							        $this.table.width(newTableWidth);

							        var tableObj = $this._getTableObj();
							        var childrenObj = tableObj.colgroup.children().eq(index);
							        childrenObj.width(newColWidth);
							        //tableObj.header.width(newTableWidth);
							        tableObj.table.width(newTableWidth);
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
							$this.gridpanel.sortparams["sortName"] = colname;
							$this.gridpanel.sortparams["sortFlag"] = sortFlag;
							//2.调用gridpanel请求新数据
							$this.gridpanel.requestPageData();
						}
					}
				});
			},
			
			_getTableObj: function(){
				if(!this.gridTableObj){
					var grid = this.element.getParentComponent();
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
				var el = this.colgroup.find('col[name="'+columnname+'"]');
				if(el.length>0){
					el.css('width','0px');
					var gridTableObj = this._getTableObj();
					if(gridTableObj){
						$(gridTableObj['colgroup']).find('col[name="'+columnname+'"]').css('width','0px');
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
			    		this.colgroup.find('col[name="'+colname+'"]').css("width",width+"px");
			    		var gridTableObj = this._getTableObj();
						if(gridTableObj){
							$(gridTableObj['colgroup']).find('col[name="'+colname+'"]').css("width",width+"px");
						}
			    	}
			    }
			},
			clearColumnSortedStyle: function(){
				var $this = this;
				//只为class包含jazz-grid-bottomCell
				var el = this.tbody.find('td.jazz-grid-bottomCell');
				el.removeClass('jazz-grid-asc jazz-grid-desc').find('span.jazz-grid-sortIcon').remove();
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
			 * @desc 自定义行渲染函数（暂时保留该功能）
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
			 * @type object
			 * @desc 行选择事件
			 * @default null
			 */
        	rowselect: null,
        	/**
			 * @type object
			 * @desc 行取消选择事件
			 * @default null
			 */
        	rowunselect: null,
        	/**
			 * @type object
			 * @desc 行双击事件
			 * @default null
			 */
        	rowdblclick: null
		},
		
		/**
		 * @desc 创建组件
		 * @private
		 */
		_create: function(){
			
			this.compId = this.getCompId();
			var el = this.element;
			el.attr('id', this.compId+'_gridtable').addClass('jazz-datatable');
			var div = '<div id="'+this.compId+'_tables" class="jazz-grid-tables" style="display: block;">'
				    + '<table id="'+this.compId+'_table" class="jazz-grid-data-table" cellspacing="0" cellpadding="0" border="0">'
				    + '<colgroup></colgroup><tbody></tbody></table></div>';
			el.append(div);
	
			this.columns = $('#'+this.compId+'_tables');
			this.table = $('#'+this.compId+'_table');
			this.colgroup = this.table.children('colgroup');
			this.tbody = this.table.children('tbody');
			
			//1.设置gridtable样式
			var parentobj = this.element.getParentComponent();
			if(parentobj&&parentobj.length>0){
				//将gridtable包装到pagearea中，并为pagearea进行fit布局
				var pagearea = parentobj.find(".jazz-pagearea");
				if(pagearea&&pagearea.length>0){
					el.appendTo(pagearea);
				}else{
					el.wrap('<div id="'+this.compId+'_pagearea" class="jazz-pagearea"></div>');
					pagearea = el.parent();
				}

				this.gridpanel = $(parentobj).data('gridpanel');
				//2.gridpanel设置layout：fit和height属性值时,需要改变pagearea的样式
				if(this.gridpanel){
					var t = this.gridpanel.options;
					$.extend(this.options, t);
					if(parseInt(t.height)>0||t.layout=="fit"){
		        		pagearea.layout({layout: 'fit'});
						pagearea.css({overflow: 'hidden'});
		        	}else{
		        		this.columns.css("height","auto");
		        	}
				}
				//3.获取gridcolumn对象
				this.gridcolumn = parentobj.find("div[vtype=gridcolumn]").data("gridcolumn");
			}
			
			//4.//设置表格线条样式
			if(this.options.linetype&&(this.options.linetype!=0||this.options.linetype!="0")){
				this.table.addClass("jazz-grid-table-linetype"+this.options.linetype);
			}
			if(this.options.linestyle&&(this.options.linestyle!=1||this.options.linestyle!="1")){
				this.table.addClass("jazz-grid-table-linestyle"+this.options.linestyle);
			}
		},
		
		/**
		 * @desc 初始化
		 * @private
		 */
		_init: function(){
            //this.toCodeColumn = [];//需要代码集翻译的字段
            this.cols = [];//保存显示列信息名称
            //this.keyCode = null;//保存key列的名称
            
            if(this.gridcolumn){
				this.cols = this.gridcolumn.cols; 
				//this.keyCode = this.gridcolumn.keyCode;
				//this.toCodeColumn = this.toCodeColumnTranslate(this.gridcolumn.toCodeColumn);
				
				this.calculateGridtableWidth();
            }
			//绑定表格所需要的事件
			this._bindTableEvent();
			//表格横向滚动事件
			this._bindTableHorizonScrollEvent();
		},
		
		/**
		 * @desc 绑定表格行操作事件（移入、移出、单击、双击）
		 * @private
		 * @example this._bindTableEvent();
		 */
		_bindTableEvent: function(){
			this._bindGridTableSelectionEvent();
		},
		/**
		 * @desc 设置gridtable的宽度
		 * @private
		 */
		calculateGridtableWidth: function(){
			if(this.gridcolumn){
				this.colgroup.children().remove();
				this.colgroup.append(this.gridcolumn.colgroup[0].innerHTML);
				this.table.width(this.gridcolumn.tableWidth);
            }
		},
		/**
		 * @desc 表格横向滚动条事件（表格和表头一致横向滚动）
		 * @private
		 * @example this._bindTableHorizonScrollEvent();
		 */
		_bindTableHorizonScrollEvent: function(){
			var $this = this;
			var el = this.element;
			var gridtables = el.find("div.jazz-grid-tables");
			var gridcolumns = $this.gridcolumn.columns;
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
	            if (this._isSelectHelper() && this._isSelectType()) {
	                 trHtml += "<td class='jazz-grid-cell'><div class='jazz-grid-cell-inner'>&nbsp;</div></td>";
	            }
                if (showLine) {
                    trHtml += "<td class='jazz-grid-cell'><div class='jazz-grid-cell-inner'>&nbsp;</div></td>";
                }
                
                for (var j=0, l=this.cols.length; j<l; j++) {
                    trHtml += "<td class='jazz-grid-cell'><div class='jazz-grid-cell-inner'>&nbsp;</div></td>";
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
            var rowHtml = '<tr index="'+(lineIndex-1)+'" class="'+cls+' " id="'+data["rowuuid"]+'">';
            
            //4.判断是否显示checkbox radio
            var b = '';
            if (this._isSelectHelper() && this._isSelectType()) {
                if ($this._isMultipleSelection()) {
                	b = 'checkbox';
                }else if ($this._isSingleSelection()) {
                	b = 'radio';
                }
                rowHtml += '<td class="jazz-grid-cell"><div class="jazz-grid-cell-box"><input type="'+b+'"/></div></td>';
            }
			//5.添加行号，根据当前页码数递增
            if(lineno){
            	rowHtml += '<td class="jazz-grid-cell jazz-grid-cell-no">' + lineIndex + '</td>';
            }
            //6.生成内容td
            var columnname="",stm="";
            for (var j = 0, len = this.cols.length; j < len; j++) {
            	columnname = this.cols[j]['columnname'];
        		if(columnname&&data[columnname]!=undefined){
        			stm = '<div class="jazz-grid-cell-inner">'+data[columnname]+'</div>';
        		}else{
        			stm = '<div class="jazz-grid-cell-inner"></div>';
        		}
	            rowHtml += '<td class="jazz-grid-cell">'+stm+'</td>';	
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
            
            if (rows) {
            	this._trObject().remove();
            	
                lineIndex = this._trNumber();
                for (var i = 0; i < rows.length; i++) {
                	rowHtml += this._insertRowHtml(rows[i], i, lineIndex);
                }
                this.tbody.append(rowHtml);
                
                //渲染数据后，执行rowrender(rowdata,rowElement),提供操作tr回调
                this._triggerGridtableEvent("rowrender",rows,this.tbody.find("tr"));
            }
        },
        _triggerGridtableEvent: function(eventtype,arg1,arg2){
        	if(this.options[eventtype]){
            	if($.isFunction(this.options[eventtype])){
                    this.options[eventtype].call(this, arg1, arg2);
            	}else{
            		var eventtype = this.options[eventtype];
            		if(eventtype.indexOf("(")!=-1){
	            		eventtype = eventtype.substr(0,eventtype.indexOf("("));
            		}
            		eval(eventtype).call(this, arg1, arg2);
            	}
        	}
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
			//1.绑定表格单击和双击点击事件
			//2.注意gridtable和gridcard选择数据及状态的交互
			var $this = this;
			this.tbody.on("click.tr", "tr", null, function(e) {
	        	if($(this).hasClass("nodata")) {return;}
	        	if($(e.target).is('a')){return;}
	        	//$this.options.selectable属性绑定行选中事件
	        	var targetrow = $(this);
	        	if($this.options.rowselectable || $this.options.rowselectable=="true"){
		        	setTimeout(function(){
	                    $this._onRowClick(e, targetrow, false);
	                },100);
	        	}else{
	        		if($(e.target).is('.jazz-grid-cell-box :input')){
	        			$this._onRowClick(e, targetrow, false);
	        		}
	        	}
	        }).on("dblclick.tr", "tr", null, function(e) {
	        	if ($(this).hasClass("nodata")) {return;}
	        	if($(e.target).is('a')){return;}
		        var targetrow = $(this);
		        $this._onRowClick(e, targetrow, true);
			});
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
        	this.tbody.find("tr").addClass('jazz-row-selected').attr('aria-selected', true);
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
        	this.tbody.find("tr").removeClass('jazz-row-selected').attr('aria-selected', false);
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
            	
                $.each(this._trObject(), function(i, obj) {
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
        	var trObj = null
        	if(rowHtml){
	        	//this.tbody.append(rowHtml);
	        	trObj = $(rowHtml).appendTo(this.tbody);
        	}
        	//渲染数据后，执行rowrender(rowdata,rowElement),提供操作tr回调
            this._triggerGridtableEvent("rowrender",data,trObj);
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
	        var rowIndex = trobj.find(".jazz-grid-cell-no").html();
    		var rowHtml = this._insertRowHtml(data, 0, (parseInt(rowIndex)-1));
    		trobj.replaceWith(rowHtml);
	        
    		//渲染数据后，执行rowrender(rowdata,rowElement),提供操作tr回调
            this._triggerGridtableEvent("rowrender",data,trobj);
	    },
        /**
	     * @desc 根据表格行ID删除数据行
	     * @param {id} 行id值
	     * @private 
	     * @example  $('#gridtable').gridtable('removeRowById',id);
	     */
        removeRowById: function(id) {
            this.tbody.find('#'+id).remove();
            this._updateIndex();
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
			$this.tbody.find("tr").addClass('jazz-row-selected').attr('aria-selected', true);
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
        	$this.tbody.find("tr").removeClass('jazz-row-selected').attr('aria-selected', false);
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
//        	/**
//			 * @type number
//			 * @desc 卡片宽度
//			 * @default 160
//			 */
//            //width: "160px",
//            /**
//			 * @type number
//			 * @desc 卡片高度
//			 * @default 90
//			 */
//            //height: "90px",
//            /**
//			 * @type number
//			 * @desc 卡片外边距
//			 * @default 20
//			 */
//            //margin: "20px",
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
			 * @default null
			 */
        	cardselect: null,
        	/**
			 * @type object
			 * @desc 卡片取消选择事件
			 * @default null
			 */
        	cardunselect: null,
        	/**
			 * @type object
			 * @desc 卡片双击事件
			 * @default null
			 */
        	carddblclick: null
        },
        _create: function(){
            this.compId = this.getCompId();
			var el = this.element;
			el.attr('id', this.compId+'_gridcard').addClass('jazz-gridcard');
            this.cardContent = $('<div id="'+ this.compId +'_cardcontent" style="height:100%;"></div>').appendTo(el);
            
			//1.设置gridtable样式
			var parentobj = this.element.getParentComponent();
			if(parentobj&&parentobj.length>0){
				//将gridtable包装到pagearea中，并为pagearea进行fit布局
				var pagearea = parentobj.find(".jazz-pagearea");
				if(pagearea&&pagearea.length>0){
					el.appendTo(pagearea);
				}else{
					el.wrap('<div id="'+this.compId+'_pagearea" class="jazz-pagearea" ></div>');
					pagearea = el.parent();
				}
				
				this.gridpanel = parentobj.data('gridpanel');
				this.gridcolumn = parentobj.find("div[vtype=gridcolumn]").data("gridcolumn");
				this.gridtable = parentobj.find("div[vtype=gridtable]").data("gridtable");
				
				//2.gridpanel设置layout：fit和height属性值时,需要改变pagearea的样式
				if(this.gridpanel){
					if(parseInt(this.gridpanel.options.height)>0||this.gridpanel.options.layout=="fit"){
		        		pagearea.layout({layout: 'fit'});
						pagearea.css({overflow: 'hidden'});
		        	}else{
		        		this.cardContent.css("height","auto");
		        	}
		        	if(this.gridpanel.options.defaultview=="card"){
		        		pagearea.css({overflow: 'auto'});
		        	}
				}
			}
            
            //保存key列的名称
            //this.keyCode = null;
        },
        _init: function(){
            if(this.gridpanel){
            	this.options.rowselectable = this.gridpanel.options.rowselectable;
	            this.options.selecttype = this.gridpanel.options.selecttype;
	            this.options.isshowselecthelper = this.gridpanel.options.isshowselecthelper;
	            
	            if(this.gridcolumn){
	            	this.cols = this.gridcolumn.cols; 
            		//this.keyCode = this.gridcolumn.keyCode;
	            }
            }
            
            this._bindGridcardSelectionEvent();
            //重排卡片视图的卡片布局
            this._calCardStyle();
            this._cardResize();
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
                /*if($this.options.html && $.isFunction($this.options.html)){
                    $this._createCard = $this.options.html;
                }*/
            	if($this.options.html){
                    $this._createCard = $this.options.html;
	            	if(!$.isFunction($this.options.html)){
	            		if($this._createCard.indexOf("(")!=-1){
		            		$this._createCard = $this._createCard.substr(0,$this._createCard.indexOf("("));
	            		}
	            	}
	        	}
               	
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
                
                var cardHtml = "",cellcontent="";
                for(var i = 0, len = rows.length; i < len; i++) {
                	
                	cardHtml = '<div class="jazz-grid-cardcell jazz-grid-qh-cardcell" id="'+rows[i]["rowuuid"]+'">';
                	
                    //cellcontent = $this._createCard(rows[i]);
                    if($.isFunction($this._createCard)){
	            		cellcontent = $this._createCard(rows[i]);
	            	}else{
	            		cellcontent = eval($this._createCard).call($this,rows[i]);
	            	}
	            	
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
            if(addcardrender && $.isFunction(addcardrender) ){
                cardHtml = addcardrender;
            }else{
                cardHtml = $this._createCardNewIcon();
            }
            if(addcardposition && addcardposition === "first" && ($cardContent.find('div.jazz-grid-cardcell:first').length > 0)){
                $cardContent.find('div.jazz-grid-cardcell:first').before(cardHtml);
            }else{
                $cardContent.append(cardHtml);
            }
            
            if(addcardclick && $.isFunction(addcardclick)){
                $cardContent.find('div.jazz-card-new').on('click', function(data){
                    addcardclick.call(this, data);
                });
            }
        },

        _createCardNewIcon : function(){
            var $this = this;
            if(!$this.cardCell){
                $this._calCardStyle();
            }
            
           var cardWidth = $this.cardCell.cardWidth,
           		cardHeight = $this.cardCell.cardHeight,
                cardLast = $this.cardContent.find('.jazz-grid-cardcell:last');
            if(cardLast.length > 0){
            	return "<div class='jazz-grid-cardcell jazz-grid-qh-cardcell '>" 
            		+ "<div class='jazz-card-new' style='height:" 
            		+ ((cardLast.height()-1)+"px") 
            		+ ";'></div></div>";
            }
            return "<div class='jazz-grid-cardcell jazz-grid-qh-cardcell '>" +
                "<div class='jazz-card-new'></div></div>";
                //"<div class='jazz-card-new' style='height:" + cardHeight + ";'></div></div>";
        },
        /**
         * @desc 计算卡片的位置
         * @private
         * @example this._calCardStyle();
         */
        _calCardStyle: function(){
            var $this = this;
            
            var card ="<div class='jazz-grid-cardcell jazz-grid-qh-cardcell '></div>";
            var cardWidth = $(card).width();
            var cardHeight = $(card).height();
            var cardMargin = $(card).outerWidth(true)-$(card).width();
           
            var cardContentWidth = this.element.parent().width();
            
            /*var cardWidth = $this.options.width,
                cardHeight = $this.options.height,
                cardMargin = $this.options.margin;
            cardWidth = parseInt( cardWidth.replace("px", "") );
            cardHeight = parseInt( cardHeight.replace("px", "") );
            cardMargin = parseInt( cardMargin.replace("px", "") );*/
            var total = Math.floor(cardContentWidth / (cardWidth + 10)),//一行放几个卡片
                cardContainerWidth = total * (cardWidth + 10),
                sideMargin = cardContentWidth - cardContainerWidth,
                cardConHeight = cardHeight - 10, // titleBar的高度  + (5*2)上下默认内边距
                imgWidth = (cardConHeight * 0.75).toFixed(),
                conWidth = (cardWidth - imgWidth - 20);
            
            /*var size = 1;
            if($this.rows){
                size = $this.rows.length;
            }
            if($this._isCardAddBtn()){
                size++;
            }
            var line = Math.ceil(size / total);
            if(line == 0){
                line = 1;
            }*/
            
            //计算后的卡片相关参数
            $this.cardCell = {
                //line: line,
                total: total, //一行放几个卡片
                cardWidth: cardWidth, //卡片在页面上的实际横向位移
                cardHeight: cardHeight, // + parseInt(cardMargin))卡片在页面上的实际纵向位移
                leftWidth: imgWidth, //卡片默认内容区域左边图片的宽度
                rightWidth: conWidth, //卡片默认内容区域右边的宽度
                conHeight: cardConHeight, //卡片内容区域的高度
                cardMargin: 10,  //卡片间距
                sideMargin: sideMargin,  //卡片容器左右间距
                cardContainerWidth: cardContainerWidth,
                wrapperWidth: cardContentWidth
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
                /*if($this.options.html && $.isFunction($this.options.html)){
                    $this._createCard = $this.options.html;
                }*/
            	if($this.options.html){
                    $this._createCard = $this.options.html;
	            	if(!$.isFunction($this.options.html)){
	            		if($this._createCard.indexOf("(")!=-1){
		            		$this._createCard = $this._createCard.substr(0,$this._createCard.indexOf("("));
	            		}
	            	}
	        	}
               	
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
                
                var cardHtml = "";
                for(var i = 0, len = rows.length; i < len; i++) {
                	
                	cardHtml += '<div class="jazz-grid-cardcell jazz-grid-qh-cardcell" id="'+rows[i]["rowuuid"]+'">';
                	
                    var cellcontent = "";
                    if($.isFunction($this._createCard)){
	            		cellcontent = $this._createCard(rows[i]) || "";
	            	}else{
	            		cellcontent = eval($this._createCard).call($this,rows[i]) || "";
	            	}
                    
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
(function($) {

	/**
	 * @version 0.5
	 * @name jazz.loading
	 * @description 加载动画组件
	 * @example $('div_id').loading();
	 * @example jazz.loading();
	 */
    $.widget("jazz.loading",  $.jazz.boxComponent, {
       
        options: /** @lends jazz.loading# */  {
        	
            /**
    		 *@type String
    		 *@desc 不显示任何信息， 默认false, 显示加载图片和文字说明， true，不显示任务内容
    		 *@default false
    		 */        	
        	blank: false,
        	
            /**
    		 *@type String
    		 *@desc 加载显示文字说明
    		 *@default ''
    		 */
            text: '正在加载...'
        },
       
		/**
         * @desc 创建组件
		 * @private
         */        
        _create: function() {
        	this.ele = $("<div class='jazz-loading-overlay'><div class='jazz-loading-img'></div><div class='jazz-loading-text'>" 
        			 + this.options.text + "</div></div>").appendTo(this.element);
        },
 
		/**
         * @desc 初始化组件
		 * @private
         */             
        _init: function(){
        	this.changePosition = false;
        	this.oldPosition = "";    
        	if(this.options.blank == true){
        		this.ele.children().hide();
        	}else{
        		this.ele.children().show();
        	}
        	this.show();     	
        },
        
        /**
         * @desc 检查this.element父元素的position属性
         * @private
         */
        _checkPosition: function(){
        	var parent = this.element.parent();
        	var position = parent.css("position");
        	this.oldPosition = this.element.css("position");
        	if(parent.get(0) == $("body").get(0)){
//        		this.element.css({position: 'fixed', top: 0, left: 0});
        		this.element.css({position: 'fixed', top: 0, left: 0});
        	}else if(position == 'inherit' || position == 'static'  || position == ''){        		
        		this.element.css('position', 'relative');
        	}
        	this.changePosition = true;
        },
        
        /**
         * @desc 还原this.element父元素的position属性
         * @private
         */        
        _reverPostion: function(){
        	var parent = this.element.parent();
        	if(this.changePosition){
        		//只针对放在body里的遮罩层position
        		if(parent[0].tagName.toUpperCase() === "BODY"){
        			parent.css('position', this.oldPosition);
        		}//else{
//        			parent.css('position', "absolute");        			
        		//}
        	}
        },               
        
        /**
         * @desc 隐藏loading动画
         * @example this.hide();
         */ 
        hide: function(){
            this._reverPostion();
        	this.ele.hide();
        },

        /**
         * @desc 显示loading动画
         * @example this.hide();
         */         
        show: function(){
        	this._checkPosition();
        	this.ele.show();
        },
        
        /**
         * @desc 组件销毁方法
         */
        destroy: function(){
        	this.element.children().remove();
        	this.element.remove();
        }
        
    });
    
})(jQuery);(function($) {

/**
 * @version 0.5
 * @name jazz.toolbar
 * @description 工具条组件
 * @constructor
 * @extends jazz.boxComponent
 * @requires
 * @example $('#div_id').toolbar();
 */
$.widget("jazz.toolbar", $.jazz.boxComponent, {

		options :/** @lends jazz.toolbar# */ {
			/**
			 *@type Object
			 *@desc toolbar组件元素数据
			 *@default null
			 */
			items: null,
			/**
			 *@type int
			 *@desc toolbar折行样式（0,换行;2,滚动）
			 *@default 0
			 */
			overflowtype: 0
		},

		/** @lends jazz.toolbar */
		/**
         * @desc 创建组件
         * @private
         */ 
		_create : function() {
			//this.comId = this.getCompId();
			//this.element.attr("id",this.comId+"_toolbar").addClass('jazz-toolbar');
			this.element.addClass('jazz-toolbar');
			var vTypeparent = this.element.getParentComponent();
			if(vTypeparent){
	        	if(vTypeparent.attr("vtype")=="gridpanel"){
	        		this.element.addClass('jazz-gridtable-toolbar').css('width', '100%');
	        	}else if(vTypeparent.attr("vtype")=="formpanel"){
	        		this.element.addClass('jazz-formpanel-toolbar');
	        	}
			}
			
			this.scollLeft = $("<div class='toolbar-scroll-left'></div>").appendTo(this.element);
			this.toolbarContent = $("<div class='jazz-toolbar-content'></div>").appendTo(this.element);
			this.toolbarContentWrap = $("<div class='jazz-toolbar-content-wrap'></div>").appendTo(this.toolbarContent);
			this.leftToolbar= $("<div class='jazz-toolbar-left'></div>").appendTo(this.toolbarContentWrap);
			this.rightToolbar= $("<div class='jazz-toolbar-right'></div>").appendTo(this.toolbarContentWrap);
			this.scollRight = $("<div class='toolbar-scroll-right'></div>").appendTo(this.element);
		},
		/**
         * @desc 初始化组件
         * @private
         */ 
		_init: function(){
			//this.transformOptions();
			//渲染toolbar按钮dom
            this._initToolbarButtons(this.options.items);
			
            this._bindScrollEvent();
		},
		/**
         * @desc 初始化渲染toolbar元素数据
         * @param {data} toolbars数据项，数组格式
         * @return undefined
         * @private
         * @example  this._initToolbarButtons();
         */ 
		_initToolbarButtons: function(data){
			var $this = this;
			
			if(data){
				//如果jazz-toolbar-content-wrap包含有text-align:center,则说明当前是居中排序
				//此后再添加按钮都是直接放到leftToolbar中即可
				var centerData = null,hasAlignCenterData=false;
				var textAlign = $this.leftToolbar.parent().css("text-align");
				if(textAlign=="center"){
					centerData = $.extend(true, [], data);
            		$.each(centerData, function(i, item) {
	        			var btn = $('<div name="'+item.name+'"></div>').appendTo($this.leftToolbar);
	        			$(btn).button(item);
	        		});
				}else{
					
					for(var i=0;i<data.length;i++){
	            		var d = data[i];
	            		if(d.align=='center'){
		            		hasAlignCenterData=true;
	            		}
	            	}
					var leftnums = $this.leftToolbar.children().length;
					var rightnums = $this.rightToolbar.children().length;
					if((leftnums>0||rightnums>0)&&hasAlignCenterData){
						alert("按钮左或右对齐后，不允许再居中对齐。");
					}else{
						if(hasAlignCenterData){
							centerData = $.extend(true, [], data);
		            		$this.leftToolbar.parent().css("text-align","center");
		            		$.each(centerData, function(i, item) {
			        			var btn = $('<div name="'+item.name+'"></div>').appendTo($this.leftToolbar);
			        			$(btn).button(item);
			        		});
						}else{
							
							var leftData=[],rightData=[];
			            	for(var i=0;i<data.length;i++){
			            		var d = data[i];
			            		if(d.align){
			            			if(d.align=='left'){
			            				leftData.push(d);
			            			}else if(d.align=='right'){
			            				rightData.push(d);
				            		}else {
				            		}
			            		}else {
			            			leftData.push(d);
			            		}
			            	}
			            	            	
							if(leftData.length>0){
				        		$.each(leftData, function(i, item) {
				        			var btn = $('<div name="'+item.name+'"></div>').appendTo($this.leftToolbar);
				        			$(btn).button(item);
				        		});
				        	}
				        	if(rightData.length>0){
				        		/*$.each(rightData, function(i, item) {
				        			var btn = $('<div name="'+item.name+'"></div>').appendTo($this.rightToolbar);
				        			$(btn).button(item);
				        		});*/
				        		for(var i=rightData.length-1;i>=0;i--){
				        			var item = rightData[i];
				        			var btn = $('<div name="'+item.name+'"></div>').appendTo($this.rightToolbar);
				        			$(btn).button(item);
				        		}
				        	}
						}
					}
					
				}
	        	$this._computeToolbarWidth();
			}
		},
		/**
         * @desc 计算toolbar组件填充数据项后宽度（为jazz.button类提供）
         * @return undefined
         * @example  this.computeToolbarWidthDW();
         */
		computeToolbarWidthDW: function(){
			this._computeToolbarWidth();
		},
	    /**
         * @desc 计算toolbar组件填充数据项后宽度
         * @return undefined
         * @private
         * @example  this._computeToolbarWidth();
         */
		_computeToolbarWidth: function(){
			var that = this;
			var toolbarWidth = that.element.width();
			var leftToolbarWidth = that.leftToolbar.width();
			var rightToolbarWidth = that.rightToolbar.width();
			
			if($.browser.msie&&($.browser.version == "7.0"||$.browser.version == "6.0")){
				var tempWidth = 0;
				that.rightToolbar.children().each(function(){
					tempWidth += $(this).outerWidth(true);
				});
				rightToolbarWidth = tempWidth;
				that.rightToolbar.width(rightToolbarWidth+"px");
				that.leftToolbar.css('float','left');
			}
			//显示左右滚动条
			if((leftToolbarWidth+rightToolbarWidth)>toolbarWidth){
				that.toolbarContent.width("100%");//toolbarWidth-that.scollLeft.outerWidth(true)-that.scollRight.outerWidth(true)
				that.toolbarContentWrap.width(leftToolbarWidth+rightToolbarWidth+2);
				//alert(that.toolbarContentWrap.height());
				
				that.scollLeft.css({'height':that.toolbarContentWrap.height()}).removeClass('toolbar-scroll-hidden').addClass('toolbar-scroll-display');
				that.scollRight.css({'height':that.toolbarContentWrap.height()}).removeClass('toolbar-scroll-hidden').addClass('toolbar-scroll-display');
			}else{
				that.scollLeft.removeClass('toolbar-scroll-display').addClass('toolbar-scroll-hidden');
				that.scollRight.removeClass('toolbar-scroll-display').addClass('toolbar-scroll-hidden');
				that.toolbarContent.width("100%");
				that.toolbarContentWrap.css("width","auto");
			}
		},
		/**
         * @desc 绑定toolbar组件横向滚动事件
         * @return undefined
         * @private
         * @example  this._bindScrollEvent();
         */
		_bindScrollEvent: function(){
			var that = this;
        	var scrollwidth = 50;
        	
        	that.scollRight.off("click").on("click",function(){
        		var scrollleft = that.toolbarContent.scrollLeft()+scrollwidth;
	       		var scrollTotalWidth = that.toolbarContentWrap.width() - that.toolbarContent.width();
	       		if(scrollleft>scrollTotalWidth){
        			scrollleft = scrollTotalWidth;
        		}
        		that.toolbarContent.scrollLeft(scrollleft);
        	});
        	that.scollLeft.off("click").on("click",function(){
        		var scrollleft = that.toolbarContent.scrollLeft()-scrollwidth;
	       		that.toolbarContent.scrollLeft(scrollleft);
        	});
		},
		/**
         * @desc toolbar按钮不可用
         * @param {name} 工具条按钮name名称值
         * @example  $('#div_id').toolbar('disableButton','name');
         */
        disableButton: function(name) {
        	if(!name){
        		return;
        	}
        	var $this = this;
        	$this.element.find('.jazz-button[name="'+name+'"]').each(function(i){
    			$(this).button("disable");
        	});
        },
        /**
         * @desc toolbar按钮使可用
         * @param {name} 工具条按钮name名称值
         * @example  $('#div_id').toolbar('enableButton','name');
         */
        enableButton: function(name) {
        	if(!name){
        		return;
        	}
        	var $this = this;
        	$this.element.find('.jazz-button[name="'+name+'"]').each(function(i){
    			$(this).button("enable");
        	});
        },
        /**
         * @desc 增加toolbar按钮数据项
         * @param {data} 工具条按钮数据项data，格式为数组
         * @example  $('#div_id').toolbar('addButton',data);
         */
		addButton : function(data) {
			this._initToolbarButtons(data);
		},
		/**
		 * @desc 移除toolbar按钮
		 * @param {name} 工具条按钮name名称值
		 * @example  $('#div_id').toolbar('removeButton','name');
		 */
		removeButton : function(name) {
        	if(!name){
        		return;
        	}
        	var $this = this;
        	$this.element.find('.jazz-button[name="'+name+'"]').each(function(i){
    			$(this).parent().remove();
        	});
			$this._computeToolbarWidth();
		},
		/**
		 * @desc 隐藏toolbar按钮
		 * @param {name} 工具条按钮name名称值
		 * @example  $('#div_id').toolbar('hideButton','name');
		 */
		hideButton: function(name){
        	if(!name){
        		return;
        	}
        	var $this = this;
        	$this.element.find('.jazz-button[name="'+name+'"]').each(function(i){
    			$(this).parent().hide();
        	});
			$this._computeToolbarWidth();
		},
		/**
		 * @desc 显示toolbar按钮
		 * @param {name} 工具条按钮name名称值
		 * @example  $('#div_id').toolbar('showButton','name');
		 */
		showButton: function(name){
        	if(!name){
        		return;
        	}
        	var $this = this;
        	$this.element.find('.jazz-button[name="'+name+'"]').each(function(i){
    			$(this).parent().show();
        	});
			$this._computeToolbarWidth();
		},
		/**
		 * @desc 重新加载toolbar组件按钮options属性
		 * @param {name} 工具条按钮name名称值
		 * @param {opts} 工具条按钮options属性值
		 * @example  $('#div_id').toolbar('changeButtonOptions','name'，opts);
		 */
		changeButtonOptions: function(name,opts){
        	if(!name){
        		return;
        	}
        	var $this = this;
        	$this.element.find('.jazz-button[name="'+name+'"]').each(function(i){
    			$(this).button('changeButtonOptions',opts);
        	});
		},
		/**
         * @desc toolbar组件高亮按钮选中样式
         * @param {name} 工具条按钮name名称值
         * @public
         * @example  $('#div_id').toolbar('highlightButton',name);
         */
		highlightButton: function(name){
        	if(!name){
        		return;
        	}
        	this.element.find('div[name="'+name+'"]').button("highlight");
		},
		/**
         * @desc toolbar组件取消高亮按钮选中样式
         * @param {name} 工具条按钮name名称值
         * @public
         * @example  $('#div_id').toolbar('unhighlightButton',name);
         */
		unhighlightButton: function(name){
        	if(!name){
        		return;
        	}
        	this.element.find('div[name="'+name+'"]').button("unhighlight");
		}
		/*hide: function(){
			var $this = this;
			$this.element.hide();
		},
		show:function(){
			var $this = this;
			$this.element.show();
		},*/

	});

})(jQuery);(function($) {

/** 
 * @version 0.5
 * @name jazz.tooltip
 * @description 提示信息组件。
 * @constructor
 * @extends jazz.BoxContainer
 * @requires
 * @example $('#input_id').tooltip({content: '提示信息内容'});
 */	
    $.widget("jazz.tooltip", $.jazz.boxComponent, {
       
        options:  /** @lends jazz.tooltip# */{
			
        	icon: null, 
        	
        	/**
			 *@type String
			 *@desc 显示提示内容要由哪种事件触发
			 *@default 'mouseover'
			 */        	
            showevent: 'mouseover',
            
        	/**
			 *@type String
			 *@desc 隐藏提示内容要由哪种事件触发
			 *@default 'mouseout'
			 */             
            hideevent: 'mouseout',
                          
        	/**
			 *@type String
			 *@desc 提示内容
			 *@default ''
			 */            
            content: ''
        },
        
    	/** @lends jazz.tooltip */
  
		/**
         *@desc 创建组件
         */	        
        _create: function() {
            this.options.showevent = this.options.showevent + '.tooltip';
            this.options.hideevent = this.options.hideevent + '.tooltip';
            
            this.container = $('<div class="jazz-tooltip" />').appendTo(document.body);
            //this.globalSelector = 'a,:input,:button,img'; 
            this.globalSelector = null;
            var styleClass = "";
            if(this.options.icon){
            	styleClass = '<span class="jazz-tooltip-img" style="background: url('+this.options.icon+') no-repeat"></span>';
            }else{
            	this.arrow = $('<div class="jazz-tooltip-arrow"></div>').appendTo(this.container);
            	this.container.css({"padding-left": "10px"});
            }
            
            var content = '<div class="jazz-tooltip-div"> ' + styleClass + '<span class="jazz-tooltip-label"></span></div>';
            this.container.append(content);
            
            this.contentobj = this.container.children(".jazz-tooltip-div");
        },      

        /**
         *@desc 初始化组件
         *@private
         */
        _init: function(){
        	 this._bindEvent();
        	 if(this.options.width != -1){
        		 this.contentobj.outerWidth(this.options.width);
        	 }else{
        		 this.contentobj.outerWidth(200);
        	 }
        	 if(this.options.content){
        		 var obj = this.contentobj.children(".jazz-tooltip-label");
        		 obj.html(this.options.content);      		 
        	 }
        },
        
		/**
         * @desc 目标范围内
		 * @private
         */	        
        _bindEvent: function() {
            var $this = this;
            this.element.off(this.options.showevent + ' ' + this.options.hideevent)
                        .on(this.options.showevent, this.globalSelector, function() {
                            $this.show();
                        }).on(this.options.hideevent, this.globalSelector, function() {
                            $this.hide();
                        });
            this.element.removeAttr('title');
        },
        
		/**
         * @desc 显示位置
		 * @private
         */	         
        _align: function() {
        	var $this = this;
            this.container.css({
                left:'', 
                top:'',
                'z-index': ++jazz.zindex
            }).position({
                my: 'left top',
                at: 'right top',
                collision: 'flipfit none',
                of: this.element,
                using: function(pos) {
                	var lp = 0;
                	if(!$this.options.icon){
	                	//根据tooltip高度, 确定tooltip的显示位置
	                	var tipHeight = $this.container.outerHeight();
	                	var eleHeight = $this.element.height();
	                	if(eleHeight > 50){
		                	if(tipHeight <= 50){  
		                		lp=0; 
		                		$this.arrow.css({"top": "7px"});
		                	}else {
		                		if(eleHeight - tipHeight > 20) {
			                		lp = 20; 
			                		$this.arrow.css({"top": "35px"});
		                		}else{
			                		lp = 0; 
			                		$this.arrow.css({"top": "25px"});		                			
		                		}
		                	}	                		
	                	}else{
		                	if(tipHeight <= 50){  
		                		lp=0; 
		                		$this.arrow.css({"top": "7px"});
		                	}else {
		                		lp = 18; 
		                		$this.arrow.css({"top": "25px"});
		                	}
	                	}
                	}
                    var l = pos.left < 0 ? 0 : pos.left + 5,
                    t = pos.top < 0 ? 0 : pos.top - lp;

                    $(this).css({
                        left: l,
                        top: t
                    });
                }
            });
        },

        /**
         * @desc 隐藏提示信息
         * @example $("XXX").tooltip("hide");
         */          
        hide: function() {
        	this.container.hide();
        	this.container.css('z-index', '');
        },

        /**
         * @desc 显示提示信息
		 * @example $("XXX").tooltip("show");
         */           
        show: function() {
            this._align();
            this.container.show();
        }

    });
})(jQuery);(function($) {
	
	/**
	 * @version 0.5
	 * @name jazz.message
	 * @description 一种弹出页面。
	 * @constructor
	 * @example $('#div_id').message();
	 * @extends jazz.window
	 */
    $.widget("jazz.message", $.jazz.window, {
       
    		options: /** @lends jazz.message#  */ {
    			
                /**
    			 *@type String
    			 *@desc 组件的类型
    			 */
    			vtype: "message",
                
                /**
    			 *@type Number
    			 *@desc 窗口宽度
    			 *@default 320
    			 */
    			width:320,
                
                /**
    			 *@type Number
    			 *@desc 窗口高度
    			 *@default 180
    			 */
    			height:180,
                
    			/**
    			 *@type string
    			 *@desc 显示文字
    			 *@default ''
    			 */
   		   		text: '',
   		   		
                /**
    			 *@type Boolean
    			 *@desc 窗口是否显示
    			 *@default true
    			 */
   		   		visible: true,
   		   		
   	            /**
   				 *@type Boolean
   				 *@desc 窗口是否显示为模式窗体
   				 *@default true
   				 */
   		   		modal: true,
   	         
   	            /**
   				 *@type Boolean
   				 *@desc 确认按钮的点击事件
   				 *@event
   				 *@default true
   				 */
   		   		sure: null,
   	             
   	            /**
   				 *@type Boolean
   				 *@desc 取消按钮的点击事件
   				 *@event
   				 *@default true
   				 */
   		   		cancel: null   		   		
        },
          
        /** @lends jazz.message */
		
		/**
         * @desc 创建组件
		 * @private
         */
        _create: function() {
        	
            this._super();
        	var div = '<div id = "'+this.compId+'_icon" ></div>';
         	div += '<div id = "'+this.compId+'_text" ></div>';
         	div += '<div id = "'+this.compId+'_enter" ></div>';
         	this.element.addClass("jazz-message");
         	this.content.append(div).addClass('jazz-massage-content');
         	
         	this.icon=$('#'+this.compId+'_icon');
         	this.text=$('#'+this.compId+'_text');
         	this.enter=$('#'+this.compId+'_enter');
         	
        },
        
        
        /** @lends jazz.message */
        /**
         * @desc 初始化组件
		 * @private
         */
        _init: function() {
        	
        	this._super();

        	//窗口位置
        	this.element.css({"top":100});

        	//放置图片
        	this.chooseIcon();
        	
        	//放置文字
        	this.text.html('<div class="jazz-sub-text"><div class="jazz-content-text">'+this.options.text+'</div></div>')
        			 .addClass('jazz-massage-text');
        },
        
        
		/**
         * @desc 传递massage属性
         * @private
         */
        passMessage:function(msg){
        	this.options.title= msg.summary;
        	this.options.text=msg.detail;   
        },
        
  
        
		/**
         * @desc 放置图片
         * @private
         */
        chooseIcon:function(){
        	switch(this.options.title){
        	case '提示信息':this.icon.addClass('icon-massage-info');
           				break;
        	case '警告信息':this.icon.addClass('icon-massage-warning');
						break;
        	case '错误信息':this.icon.addClass('icon-massage-error');
						break;
        	case '确认信息':this.icon.addClass('icon-massage-confirm');
        				break;
        	}
        }
    });
    
    
	/**
	 * @version 0.5
	 * @name jazz.info
	 * @description 提示信息
	 */    
    $.widget("jazz.info", $.jazz.message, {
    	 options:{
             /**
  			 *@type string
  			 *@desc 
  			 *@default info
  			 */
 	   		 severity: 'info',
              
              /**
  			 *@type string
  			 *@desc
  			 */
 	   		 summary: '提示信息',
              
              /**
  			 *@type string
  			 *@desc 详细信息
  			 *@default ''
  			 */
 	   		 detail: ''

    	 },
    	    
    	     /**
	         * @desc 创建组件
			 * @private
	         */
    	 _create:function(){
    		this._super();
    		
    		//添加按钮
    		var $this = this;
         	var buttons = [{buttonclass: 'jazz-message-queding', 'name':'sure','align':'center','vtype':'button','click':function(){
         		$this.close(true);
				var a = $this.options.sure;
				if($.isFunction(a)){
					a();
				}         	
         	}}];
        	if($.isArray(buttons)){
        		this.enter.toolbar({items: buttons});
        	}
        	this.enter.addClass('jazz-massage-enter');
//        	if($.browser.msie&&($.browser.version == "7.0"||$.browser.version == "6.0")){
//        		this.enter.addClass('jazz-massage-enter-ie7').removeClass('jazz-toolbar');	}	
//        	else
//        		this.enter.addClass('jazz-massage-enter').removeClass('jazz-toolbar');
    	 },
    	 
    	 	/**
	         * @desc 初始化组件
			 * @private
	         */
    	 _init: function(){
    		 this.passMessage(this.options);
    		 this._super();
    	 }
    });
    
	/**
	 * @version 0.5
	 * @name jazz.warn
	 * @description 警告信息
	 */      
    $.widget("jazz.warn", $.jazz.message, {
	   	 options:{
             /**
	 			 *@type string
	 			 *@desc 
	 			 *@default warning
	 			 */
		   		 severity: 'warning',
	             
	             /**
	 			 *@type string
	 			 *@desc
	 			 */
		   		 summary: '警告信息',
	             
	             /**
	 			 *@type string
	 			 *@desc 详细信息
	 			 *@default ''
	 			 */
		   		 detail: ''

	   	 },
  		
			/**
	         * @desc 创建组件
			 * @private
	         */
    	 _create:function(){
    		 this._super();
    		 
    		 //添加按钮
    		 var $this = this;
    		 var buttons = [{buttonclass: 'jazz-message-queding', 'name':'sure','align':'center','vtype':'button','click':function(){
    			 $this.close(true);
				 var a = $this.options.sure;
				 if($.isFunction(a)){
					a();
				 }    		 
    		 }}];
         	 if($.isArray(buttons)){
         		 this.enter.toolbar({items: buttons});
         	 }
         	 this.enter.addClass('jazz-massage-enter').removeClass('jazz-toolbar');
    	 },
    	 	/**
	         * @desc 初始化组件
			 * @private
	         */
	   	 _init: function(){
	   		 this.passMessage(this.options);
	   		 this._super('init');
	   	 }
   });
    
	/**
	 * @version 0.5
	 * @name jazz.error
	 * @description 错误信息
	 */     
   $.widget("jazz.error", $.jazz.message, {
	   	 options:{
             
             /**
 			 *@type string
 			 *@desc 
 			 *@default error
 			 */
	   		 severity: 'error',
             
             /**
 			 *@type string
 			 *@desc
 			 */
	   		 summary: '错误信息',
             
             /**
 			 *@type string
 			 *@desc 详细信息
 			 *@default ''
 			 */
	   		 detail: ''

	   	 },
	   	 	/**
	         * @desc 创建组件
			 * @private
	         */
    	 _create:function(){
    		 this._super();
    		 
    		 //添加按钮
    		 var $this = this;
    		 var buttons = [{buttonclass: 'jazz-message-queding', 'name':'sure','align':'center','vtype':'button','click':function(){
    			 $this.close(true);
				 var a = $this.options.sure;
				 if($.isFunction(a)){
					a();
				 }    		
    		 }}];
          	 if($.isArray(buttons)){
         		this.enter.toolbar({items: buttons});
          	}
          	this.enter.addClass('jazz-massage-enter').removeClass('jazz-toolbar');
    	 },
    	 
    	 	/**
	         * @desc 初始化组件
			 * @private
	         */
	   	 _init: function(){
	   		 this.passMessage(this.options);
	   		 this._super();
	   	 }
  }); 
   
	/**
	 * @version 0.5
	 * @name jazz.confirm
	 * @description 确认信息
	 */     
  $.widget("jazz.confirm", $.jazz.message, {
	   	 options:{
             
             /**
 			 *@type string
 			 *@desc 
 			 *@default confirm
 			 */
	   		 severity: 'confirm',
             
             /**
 			 *@type string
 			 *@desc
 			 */
	   		 summary: '确认信息',
             
             /**
 			 *@type string
 			 *@desc 详细信息
 			 *@default ''
 			 */
	   		 detail: ''
	   	 },
	   	 
   	 	/**
         * @desc 创建组件
		 * @private
         */
    	 _create:function(){
    		 this._super();

    		 //添加按钮
    		 var $this = this;
    		 var buttons = [{
	 			name: 'sure',
	 			vtype: 'button',
	 			align: 'center',
	 			buttonclass: 'jazz-message-queding',
	 			click:function(){
	 						$this.close(true);
	 						var a = $this.options.sure;
	 						if($.isFunction(a)){
	 							a();
	 						}
	 					}
					},{
					'name':'cancel',
					'vtype':'button',
					'align':'center',
					buttonclass: 'jazz-message-quxiao',
					'click':function(){
								$this.close(true);
								var a = $this.options.cancel;
								if($.isFunction(a)){
									a();
								}
		 					}
				}];
          	if($.isArray(buttons)){
         		this.enter.toolbar({items: buttons});
          	}
          	this.enter.addClass('jazz-massage-enter').removeClass('jazz-toolbar');
    	 },
    	 
    	 	/**
	         * @desc 初始化组件
			 * @private
	         */
	   	 _init: function(){
	   		 this.passMessage(this.options);
	   		 this._super();
	   	 }
  });   
   
    
})(jQuery);
(function($) {
	
	/**
	 * @version 0.5
	 * @name jazz_validator
	 * @description 效验类。
	 */    
    jazz_validator = {
		/**
		 *@type Object
		 *@desc 正则参数
		 */		    		
	    reg: {
	        number:/^[-]?\d+(\.\d+)?([Ee][-+]?[1-9]+)?$/i,
	        numberInt:/^[-]?\d+$/i,
	        numberFloat:/^[-]?\d+\.\d+$/i,
	        //numberScience:/^[+|-]?\d+\.?\d*[E|e]{1}[+]{1}\d+$/,
	        character:/^[\u4e00-\u9fa5A-Za-z]+$/i,
	        chinese:/^[\u4e00-\u9fa5]+$/i,
	        twoBytes:/^[^\x00-\xff]+$/i,
	        english:/^[A-Za-z]+$/i,
	        number$character:/^[\u4e00-\u9fa5A-Za-z0-9]+$/i,
	        number$english:/^[\w]+$/i,
	        qq:/^[1-9]\d{4,9}$/i,
	        telephone:/^((\(0\d{2,3}\))|(0\d{2,3}-))?[1-9]\d{6,7}(-\d{1,4})?$/i,
	        cellphone:/^0?1\d{10}$/i,
	        postal:/^\d{6}$/i,
	        currency:/^\$[-+]?\d+(\.\d+)?([Ee][-+]?[1-9]+)?$/i,
	        email:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/i,
	        url:/^(http|https|ftp):\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/i
	    },
	    
		/**
		 *@type Object
		 *@desc 提示信息
		 */		    
	    msg:{
	        't':'',
	        'number':jazz.config.i18n.num,
	        'numberInt':jazz.config.i18n.numInt,
	        'numberFloat':jazz.config.i18n.numFloat,
	        'numberScience':jazz.config.i18n.numScience,
	        'character':jazz.config.i18n.character,
	        'chinese':jazz.config.i18n.chinese,
	        'twoBytes':jazz.config.i18n.twoBytes,
	        'english':jazz.config.i18n.english,
	        'date':jazz.config.i18n.date,
	        'number$character':jazz.config.i18n.numChar,
	        'number$english':jazz.config.i18n.numEnglish,
	        'qq':jazz.config.i18n.qq,
	        'telephone':jazz.config.i18n.telephone,
	        'cellphone':jazz.config.i18n.cellphone,
	        'idcard':jazz.config.i18n.idcard,
	        'postal':jazz.config.i18n.postal,
	        'currency':jazz.config.i18n.currency,
	        'email':jazz.config.i18n.email,
	        'url':jazz.config.i18n.url,
	        'and':jazz.config.i18n.and1,
	        'or':jazz.config.i18n.or,
	        'solo':'',
	        'must':jazz.config.i18n.must,
	        'contrast':jazz.config.i18n.contrast,
	        'range':jazz.config.i18n.range,
	        'customCheckStyle':jazz.config.i18n.customCheckStyle,
	        'length':jazz.config.i18n.length1,
	        'customFunction':jazz.config.i18n.customFunction
	    },
	    
		/**
         * @desc 身份证信息
         * @param {idcard} 需要验证的字符串
         * @return boolean
		 * @example this.checkIdcard(idcard)
         */
        checkIdcard: function(idcard){
        	var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}; 
        	var idcard,Y,JYM; 
        	var S,M; 
        	var idcard_array = new Array(); 
        	idcard_array = idcard.split(""); 

        	//地区检验 
        	if(area[parseInt(idcard.substr(0,2))]==null) return false; 

        	//身份号码位数及格式检验 
        	switch(idcard.length){ 
	        	case 15: 
		        	if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){ 
		        	ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性 
		        	} else { 
		        	ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性 
		        	} 

		        	if(ereg.test(idcard)) return true;
		        	else return false; 
		        	break;
        	
	        	case 18: 
	        	//18位身份号码检测 
	        	//出生日期的合法性检查 
	        	//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9])) 
	        	//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8])) 
	        		if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){ 
	        			ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式 
	        		} else { 
	        			ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式 
	        		} 
	        		if(ereg.test(idcard)){//测试出生日期的合法性 
			        	//计算校验位 
			        	S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 
			        	+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 
			        	+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 
			        	+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 
			        	+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 
			        	+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 
			        	+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 
			        	+ parseInt(idcard_array[7]) * 1 
			        	+ parseInt(idcard_array[8]) * 6 
			        	+ parseInt(idcard_array[9]) * 3 ; 
			        	Y = S % 11; 
			        	M = "F"; 
			        	JYM = "10X98765432"; 
			        	M = JYM.substr(Y,1);//判断校验位 
			        	if(M == idcard_array[17]) return true; //检测ID的校验位 
			        	else return false; 
	        		} 
	        		else return false; 
	        		break; 
	        	
	        	default: 
	        		return false; 
	        		break; 
        	} 
        },
	 
		/**
         * @desc 效验身份证信息
         * @param {val} 需要验证的字符串
         * @return boolean
		 * @example this.idcard(val)
         */        
        idcard: function(val) {
            var value = val;
            if (value == "") return true;
            return this.checkIdcard(value);
        },

		/**
         * @desc 数字定义
         * @param {val} 输入需要验证的数值
         * @param {zl}  区间范围第一个参数值
         * @param {xl}  区间范围第二个参数值
         * @return boolean
		 * @example this.numberDefine(val, zl, xl)
         */         
        numberDefine: function(val, zl, xl){
        	var value = val;
        	if(value=="")return true;
            if(!this.reg.number.test(value))return false;
            var valueStrArray=value.split(".");
            var zlength= zl;
            if(valueStrArray.length==2)
            	 return valueStrArray[0].length<=zlength && valueStrArray[1].length<=xl;
            else return valueStrArray[0].length <= zlength;
        },

		/**
         * @desc 排除字符
         * @param {val}  输入需要排除的字符
         * @param {value}  可以排除的字符
         * @return boolean
		 * @example this.customCheckStyle(val, value)
         */        
        customCheckStyle: function(val, value) {
        	//var text = this.getElValue(e);
        	var text = val;
        	var reList=value;
        	for(var i=0;i<text.length;i++){
        		var c=text.charAt(i);
        		if(reList.indexOf(c)>=0){
                   return false;
        		}
        	}
        	  return true;
        },

		/**
         * @desc 执行自定义函数时使用
         * @param {str} 函数名称
         * @return array
		 * @example this.getFunc(str)
         */         
        getFunc: function(str) {
            if (!str) {
                str = '';
            }
            var res = [];
//            if (str != "") {
//                var method = str.substring(0, str.indexOf("(")) || undefined;
//                var arg = str.substring(str.indexOf("(") + 1, str.indexOf(")")) || undefined;
//                if ((arg && arg.trim() == "") || arg == undefined) {
//                    arg = "";
//                } else {
//                    arg = "," + arg;
//                }
//
//          }
            res.push(eval(str));
            return res;
        },   

		/**
         * @desc 数字值比较
         * @param {val}  输入需要比较的数字
         * @param {v1}   比较范围第一个参数
         * @param {v2}   比较范围第二个参数
         * @return boolean
		 * @example this.contrast(val, v1, v2)
         */        
        contrast: function(val, v1, v2) {
        	var value = val;
            if (value == "")
                return true;
            if (this.reg.number.test(value)) {
                var flag = eval(value + v1);
                if (v2!='')
                    flag = flag ? eval(value + v2) : false;
                return flag;
            } else {
                return false;
            }
        },

		/**
         * @desc 字符长度
         * @param {item} 设定的参数
         * @param {val}  输入需要效验长度的字符
         * @param {begin} 开始位置
         * @param {end}  结束位置
         * @return boolean
		 * @example this.length(item, val, v1, v2)
         */        
        length: function(item, val, begin, end) {
            var value = val;
            if (value == "")
                return true;
            var len = this.getLen(value);
            if(item.indexOf(",")>=0){
            	return (len >= begin && len <= end);
            }else{
                if(end!="")
                return (len > begin && len < end);
                else return len == begin;
            }
        },

		/**
         * @desc 判定中文、字符长度
         * @param {str} 需要效验的字符
         * @return number
		 * @example this.getLen(item, val, v1, v2)
         */         
        getLen: function(str) {
            var len = 0;
            for (var i = 0; i < str.length; i++) {
                var strCode = str.charCodeAt(i);
                var strChar = str.charAt(i);
                if ((strCode > 65248) || (strCode == 12288) || this.reg.chinese.test(strChar))
                    len = len + 2;
                else
                    len = len + 1;
            }
            return len;
        },

		/**
         * @desc 测试正则表达式
         * @param {val} 输入需要效验的字符
         * @param {value} 效验的正则表达式
         * @return boolean
		 * @example this.testRegexp(val, rule)
         */          
        testRegexp: function(val, rule) {
            if(typeof(rule)!='regexp'){
        		//转成RegExp对象
        		if(this.testRegex('\\/\\^', rule)){
                    rule = rule.substring(1, rule.length);
        		}
                if(this.testRegex('\\/\\i', rule)){
        			rule = rule.substring(0, rule.length-2);
                }
        	    rule = new RegExp(rule); 
        	}
            var value = val;
            if (value == "")
                return true;
            return rule.test(value);
        },
        
		/**
         * @desc 测试正则是否匹配
         * @param {regex} 效验的正则表达式
         * @param {rule} 效验的正则表达式
         * @return boolean
		 * @example this.testRegex(val, rule)
         */          
        testRegex: function(regex, rule) {
            return ((typeof regex == 'string') ? new RegExp(regex) : regex).test(rule);
        },
        
		/**
         * @desc 外部调用的效验方法
         * @param {val} 输入需要验证的字符串
         * @param {rule} 效验的正则表达式
         * @param {regMsg} 自定义函数时的提示消息
         * @return boolean
		 * @example this.element.doValidator(val, rule)
         */         
		doValidator: function(val, rule, regMsg){
			var msg = "";
		    var customFunctionReturnObj = null; //自定义函数使用 
			var ruleArray = [], type='solo';
			if(!!rule){
				if(rule.indexOf('_')>=0){             //与关系
					ruleArray = rule.split('_');
					type='and';
				}else if(rule.indexOf('||')>=0){      //或关系
					ruleArray = rule.split('||');
					type='or';
				}else{                                //单个校验
					ruleArray[0] = rule;
				}
				var flag=(type!='or')?true:false;
	            var state=flag, stateArray = [];

	            $.each(ruleArray, function(index, item) {
	            	var tempMSG = "";
	                if(item.indexOf('must') >= 0){
	                	if($.trim(val)=='') state = false;
	                	else state = true;
	                	if(typeof(regMsg) == 'undefined' || $.trim(regMsg)=='')
	                		tempMSG = jazz_validator.msg.t + jazz_validator.msg.must;
	                	else
	                		tempMSG = regMsg+'';
	                	
	                }else if(item.indexOf("number") >=0 && item.indexOf(",") >=0){
	                	var defineArray = item.substring(item.indexOf("(")+1,item.indexOf(")")).split(",");
	                	var zl = defineArray[0]-defineArray[1];
	                	var xl = defineArray[1];
	                	state = jazz_validator.numberDefine(val, zl, xl);
	                	if(typeof(regMsg) == 'undefined' || $.trim(regMsg)=='')
	                		tempMSG = jazz_validator.msg.t + "数字,且整数部分最多"+ zl +"位,且小数部分最多" + xl + "位";
	                	else
	                		tempMSG = regMsg+'';
	                	item = 'numberDefine';
	            	
	            	}else if (jazz_validator.reg[item]) {  
	            		//postal url telephone number$character数字汉字加英文字母    qq  email cellphone currency
	            		//numberInt numberFloat numberScience
	            		//chinese english
	                	state = (state == flag) ? jazz_validator.testRegexp(val, jazz_validator.reg[item]+"") : state;
	                	if(typeof(regMsg) == 'undefined' || $.trim(regMsg)=='')
	                		tempMSG = jazz_validator.msg.t + jazz_validator.msg[item];
	                	else
	                		tempMSG = regMsg+'';
	                	
	                }else if(item.indexOf('regexp')>=0){ //正则
		            	//自定义正则表达式。例如：rule="regexp;^\d{1}$" 第一个参数为regexp是固定的，第二个参数为自定义的正则表达式，中间使用;分割
                    	state = (state == flag) ? jazz_validator.testRegexp(val, item.split(";")[1]) : state;
                    	tempMSG = jazz_validator.msg.t + ((!!regMsg)? regMsg: '');
                    	item = 'regexp';

	                }else if(item.indexOf('customCheckStyle')>=0){
	            	    //校验特殊字符的，例如，rule="customCheckStyle;*,"不允许出现*和,
	                	state = jazz_validator.customCheckStyle(val, item.split(";")[1]);
	                	if(typeof(regMsg) == 'undefined' || $.trim(regMsg)=='')
	                		tempMSG = jazz_validator.msg.t + jazz_validator.msg.customCheckStyle + item.split(";")[1];
	                	else
	                		tempMSG = regMsg+'';
	                	
	                }else if(item.indexOf('customFunction')>=0){   //自定义
	            		var items = item.split(';');
	            		customFunctionReturnObj = jazz_validator.getFunc(items[1]);
	            		state = customFunctionReturnObj[0].state;
	            		tempMSG = jazz_validator.msg.t + customFunctionReturnObj[0].msg;
	            		item = 'customFunction';
	            		if(state == undefined) state = true;
	            		
	            	}else if(item.indexOf('contrast')>=0){   //数字值比较
	            		//用户输入的数字值比较。
	            		//支持单范围比较 例如：rule="contrast;>=5" 第一个参数为contrast是固定的，第二个参数为比较范围及比较值，中间使用;分割
                        //支持双范围比较 例如：rule="contrast;>=5;<=6" 第一个参数为contrast是固定的，第二、三个参数为比较范围及比较值，中间使用;分割
                        //比较范围支持常用的所有类型 例如：> >= == < <= !=
	                    var contrastValue1 = item.split(";")[1], contrastValue2 = "";
	                    if (item.split(";").length == 3) {
	                    	contrastValue2 = item.split(";")[2];
	                    }
	                    state = jazz_validator.contrast(val, contrastValue1, contrastValue2);
	                    if(typeof(regMsg) == 'undefined' || $.trim(regMsg)=='')
	                    	tempMSG = jazz_validator.msg.t + jazz_validator.msg.contrast + contrastValue1 +",  "+ contrastValue2;
	                    else
	                    	tempMSG = regMsg+'';
	                    item = 'contrast';
	                    
		            }else if(item.indexOf('length')>=0){  //输入长度比较
		            	//用户输入的字符串长度必须在某个范围之内。
		            	//例如1：rule="length;2;4" 第一个参数为length是固定的，第二个参数为最小值，第三个参数为最大值，中间使用;分割相当于"用户输入的字符串长度在2,4之间，不包括边界值"
		            	//例如2：rule="length;2,4" 第一个参数为length是固定的，第二个参数为最小值，第三个参数为最大值，中间使用;分割相当于"用户输入的字符串长度在2,4之间，包括边界值"
		            	//例如3：rule="length;5" 第一个参数为length是固定的，第二个参数为长度值，相当于"用户输入的字符串长度只能为5"
		            	//汉字算两个字符
	                	var tempItemArray=item.split(";"), begin="", end="";
	                	if(tempItemArray.length==3){
		                    begin = item.split(";")[1];
		                    end = item.split(";")[2];
	                	}else{
	                		if(tempItemArray[1].indexOf(",")>=0){
	                			begin = tempItemArray[1].split(",")[0];
	    	                    end = tempItemArray[1].split(",")[1];
	                		}else{
	                			begin = tempItemArray[1];
	                		}
	                	}
	                    state = jazz_validator.length(tempItemArray[1], val, begin, end);
	                    if(typeof(regMsg) == 'undefined' || $.trim(regMsg)=='')
	                    	tempMSG = jazz_validator.msg.t + jazz_validator.msg['length'] + begin + (!!end ? ("和" + end  + "之间"):"");
	                    else
	                    	tempMSG = regMsg+'';
	                    item = 'length';
	                    
		            }else if(item.indexOf('idcard')>=0){
		            	state = jazz_validator.idcard(val);
		            	if(typeof(regMsg) == 'undefined' || $.trim(regMsg)=='')
		            		tempMSG = jazz_validator.msg.t + jazz_validator.msg.idcard;
		            	else
	                    	tempMSG = regMsg+'';
		            	item = 'idcard';
		            }
	                
	                if (index == 0) {
	                    msg += tempMSG;
	                } else {
	                    msg += (!!tempMSG)?(jazz_validator.msg[type] + tempMSG):"";
	                }
	                
	                stateArray[index] = state;
	            });
	            
	            var rs = true;
	            if(type=='and')
	            	rs = this._rAndState(stateArray, type);
	            else if(type=='or') 
	            	rs = this._rOrState(stateArray, type);
	            else
	            	rs = state;
	            
	            return {'state':rs, 'msg':(!!msg) ? msg : ''};
	            
			}			
			
		},
		
		/**
         * @desc 确定返回状态
         * @param {stateArray} 状态数组
         * @param {type}  
         * @return boolean
		 * @example this._rAndState(val, rule)
         */   		
        _rAndState: function(stateArray, type){
        	var r = true;
        	$.each(stateArray, function(i, state){
               	if(state==false)
               		r = false;
			});
        	return r;
		},
		/**
         * @desc 确定返回状态
         * @param {stateArray} 状态数组
         * @param {type}  
         * @return boolean
		 * @example this._rOrState(val, rule)
         */
		_rOrState: function(stateArray, type){
			var r = false;
        	$.each(stateArray, function(i, state){
               	if(state==true)
               		r = true;
			});
        	return r;
		}
	    
    }
     
})(jQuery); 
(function($){
/** 
 * @version 0.5
 * @name jazz.icon
 * @description 图标式展现组件。
 * @constructor
 * @extends jazz.panel
 * @requires
 * @example $('XXX').icon();
 */
	$.widget('jazz.icon', $.jazz.panel, {
	    options: /** @lends jazz.icon# */ {
	    	
	    	/**
        	 *@desc 组件类型
	    	 */
	    	vtype: "icon",
	    	
			/**
			 *@desc 翻页所需要时间
			 *@default 500
			 */  	    	
	    	scrolltime: 500,
	    	
    		/**
			 *@type Number
			 *@desc 当前活动的页面
			 *@default 0
			 */
            activeindex: 0,

    		/**
			 *@type boolean
			 *@desc 是否显示分页条
			 *@default true
			 */            
            isshowpaginator: true,
            
    		/**
			 *@type Number
			 *@desc 显示主题
			 *@default 0
			 */            
            theme: 0,
            
            /**
			 *@type String
			 *@desc 链接
			 *@default null
			 */      
            dataurl: null,
            
            /**
			 *@type Object
			 *@desc dataurl需要的参数 {a: '123', b: '345'}
			 *@default null
			 */            
            dataurlparams: null,           
            
            //paramicons: {width: 120, height: 120},
            /**
			 *@desc 参数 适用于模式2
			 *@default {width: 140, height: 145}
			 *@private
             */
            paramicons: {width: 140, height: 145},
            
            /**
			 *@type Number
			 *@desc 显示行数量
			 *@default 2
			 */             
            rows: 2,
            
            /**
			 *@type Number
			 *@desc 显示列数量
			 *@default null
			 */                         
            cols: -1,
            
            /**
			 *@type Number
			 *@desc 
			 *@default null
             */
            iconrender: null,
	    	
			// callbacks
			/**
			 *@desc 点击图标后触发
			 *@param {event} 事件
			 *@param {ui} 
			 *@event
			 *@example
			 *<br/>$("XXX").icon("option", "click", function( event, ui ){  <br/><br/>});
			 */
	    	click: null
		},
		
		/** @lends jazz.icon */
		
		/**
         * @desc 创建组件
		 * @private
         */
		_create: function(){
			this._super();

			this.innerPanel = $('<div style="width: 100%; height: 100%"></div>').appendTo(this.content);
			this.paginator = $('<div></div>');
		},
		
		_init: function(){
			this._super();

			this.content.css("overflow", "hidden");  
			
			this.paramIconData = [];
			this.contentWidth = this.content.width();
			this.contentHeight = this.content.height();
				
			this._build(this.innerPanel);
			
			this._bindDrag(this.innerPanel);
			if(this.options.isshowpaginator){
				this._bindPaginator(this.innerPanel); //绑定翻页组件
			}			
		},

//		/**
//         * @desc 创建面板的大小
//		 * @private
//         */			
//		_panelSize: function(){  
//			var w = this.options.width,  h = this.options.height;
//			if(w.indexOf('%')==-1){
//				if(w=='')
//					w = this.element.parent().width();
//			}else{
//				w = (parseInt(w.substring(0, w.indexOf('%')))/100) * this.element.parent().outerWidth();
//			}
//			this.options.winSize = {'w': parseInt(w), 'h': parseInt(h)};
//		},
		
	   /**
        * @desc ajax请求
        */
       _ajax: function(){
           var params = {
       		   url: this.options.dataurl,
       		   params: this.options.dataurlparams,
	           callback: this._callback  //回调函数
           };
       	   $.DataAdapter.submit(params, this);        	
       },
       
       _callback: function (data, sourceThis){
	       	var jsonData = null;
	       	var $this = null;
           if(data == '1'){
	            jsonData = this.options.data;
	            $this = this;
           }else{
	           	jsonData = data;
	           	$this = sourceThis;
           }
           var icondata = jsonData["data"];
           if(jsonData != null && icondata){
        		   var theme = $this.options.theme;
        		   var k = 0;
//					var arrayLi = [];
//					if(template == "1"){
//						$.each(icondata, function(i, obj){
//							var li = $('<li class="jazz-iconLi" index="'+k+'"></li>');
//							li.append('<span class="icon"><img src="'+obj.imageurl+'"/></span><div class="text">'+obj.label+'<s></s></div>');
//							arrayLi[i] = li;
//							if(i===0){
//								$.extend($this.options.paramicons, {width: li.width(), height: li.height(), iconLiObject: arrayLi});
//							}else{
//								$.extend($this.options.paramicons, {iconLiObject: arrayLi});
//							}
//							$this.paramIconData[i] = obj; 
//							k += 1;
//						});
//					}else
        		   
					if(theme == "2"){  //给青海项目做的
						var gHeight = 0, rows = $this.options.rows;

						var innerHeight = $this.innerPanel.height();
						var all = $this.options.paramicons.width * icondata.length;  
						if(all > $this.innerPanel.width() && innerHeight > 190 && rows==1){
							$this.options.rows = 2;  rows = 2;
						}
						gHeight = parseInt(innerHeight/parseInt($this.options.rows))-1;					
						var paramicons = $this.options.paramicons;
						var iconLiObject = paramicons["iconLiObject"] = []; 
						$.each(icondata, function(i, obj){
							var li = $('<li class="jazz-iconLi2" index="'+k+'"></li>');
								li.css({height: gHeight});						
							var imgClass = '';
							if(obj.count == '0' || obj.count == undefined){
								imgClass = '';
								obj.count = '';
							}else{
								imgClass = 's-icon-img';
							}
							li.append('<span id="img_'+obj.id+'" class="'+imgClass+'">'+obj.count+'</span>');
							li.append('<div><span class="icon2" style="background: url('+obj.imageurl+')"></span><div class="text">'+obj.label+'</div></div>');
							
							var c = li.children('div');
							c.css('top', parseInt((li.height()-100)/2) + 5);
							li.hover(function(){
								c.children('span').css('background', 'url("'+obj.imageurl3+'")');
								c.children('div').addClass('jazz-icon-text2');
							},function(){
								c.children('span').css('background', 'url("'+obj.imageurl+'")');
								c.children('div').removeClass('jazz-icon-text2');
							});
							
							if(i===0){
								paramicons["height"] = li.height();
							}
							iconLiObject.push(li);
							$this.paramIconData[i] = obj; 
							k += 1;
						});
					}else{
						var gHeight = 0, gWidth = 0;
						var rows = $this.options.rows, cols = $this.options.cols;
						var innerHeight = $this.innerPanel.height();
						gHeight = parseInt(innerHeight/parseInt(rows))-2;
						if(cols != -1){
							var innerWidth = $this.innerPanel.width();
							gWidth = parseInt(innerWidth/parseInt(cols))-0;
						}
						var paramicons = $this.options.paramicons;
						var iconLiObject = paramicons["iconLiObject"] = [];
						$.each(icondata, function(i, obj){
							var li = $('<li class="jazz-iconLi2" index="'+k+'"></li>');
								li.css({height: gHeight});
								if(cols != -1){
									li.css({width: gWidth});
								}
							var imgClass = '';
							if(obj.count == '0' || obj.count == undefined){
								imgClass = '';
								obj.count = '';
							}else{
								imgClass = 's-icon-img';
							}
							
							li.append('<span id="img_'+obj.id+'" class="'+imgClass+'">'+obj.count+'</span>');
							
							var iconrender = $this.options.iconrender;
							var flag = true;
							if($.isFunction(iconrender)){
								var str = iconrender.call(this, li, obj);
								li.append(str);
								flag = false;
							}else{
								li.append('<div><span class="icon2" style="background: url('+obj.imageurl+')"></span><div class="text">'+obj.label+'</div></div>');								
							}
							if(flag){
								var c = li.children('div');
								c.css('top', parseInt((li.height()-100)/2) + 5);
								li.hover(function(){
									c.children('span').css('background', 'url("'+obj.imageurl3+'")');
									c.children('div').addClass('jazz-icon-text2');
								},function(){
									c.children('span').css('background', 'url("'+obj.imageurl+'")');
									c.children('div').removeClass('jazz-icon-text2');
								});
							}
							if(i===0){
								paramicons["width"] = li.width();
								paramicons["height"] = li.height();
							}
							iconLiObject.push(li);
							
							$this.paramIconData[i] = obj; 
							k += 1;
						});
					}
					$this.iconallnumber = k;
					
					var innerPanel = $this.innerPanel;
				
					$this._buildPage(innerPanel, $this.iconallnumber);
					
					$this.ul = innerPanel.children('ul');
					
					//绑定图标点击事件
					$this._bindIconEvent($this.ul);
					
					//添加左右按钮
					$this._buildButton(innerPanel, $this.ul.size());
			        
			        $this._updatePosition(innerPanel);   //修改各元素位置
					
//					$(window).resize(function(){
//						//$this._panelSize();
//						$this._updatePosition(innerPanel);
//			   		});					
					
   	       }
       },       

		/**
         * @desc 创建组件元素
         * @param {innerPanel} 内层div容器对象
		 * @private
         */		
		_build: function(innerPanel){
			this.iconallnumber = 0;
			
			if(this.options.data){
				this._callback("1");
			}else if(this.options.dataurl){
            	this._ajax();
            }
		},
		
		/**
         * @desc 创建页面元素
         * @param {innerPanel} 内层div容器对象
         * @param {k} 图片总数
		 * @private
         */			
		_buildPage: function(innerPanel, k){
			//每个页面摆放多少个图标
			var navBarHeight = 0;
			var gH = this.options.paramicons.height;  //一个图标总高度，包括上下margin
			var gW = this.options.paramicons.width;   //图标总宽度,包括左右margin
			
			var rows = Math.floor((this.contentHeight-navBarHeight)/gH);     //页面图标行数
			var cols = Math.floor(this.contentWidth/gW);                     //页面图标列数
			
			rows = this.options.rows;
			gH = parseInt(innerPanel.height()/parseInt(rows))-1;
			
			//计算出有多少个页面
			var perIconNum = rows*cols;
			if(perIconNum==0) perIconNum = 1;
			var pageNumber = Math.ceil(k / perIconNum);
			
			$.extend(this.options.paramicons, {rows: rows, cols: rows, pageNumber: pageNumber});
			for(var n=0; n<pageNumber; n++){
				var ul = $('<ul class="jazz-iconUl currDesktop"></ul>').appendTo(innerPanel);
				for(var j=n*perIconNum; j<(n+1)*perIconNum; j++){
					ul.append(this.options.paramicons.iconLiObject[j]);
				}
			}
			if(this.options.isshowpaginator){
				this._paginator(k, perIconNum);
			}
		},	
		
		/**
         * @desc 分页
         * @param {total} 总记录数量
         * @param {rows} 每页显示记录数量
		 * @private
         */			
		_paginator: function(total, rows){
			this.paginator.paginator({
				template: ' {PageLinks} ',
				pagerows: rows,
				totalrecords: total,
				pagelinks: 20,
				theme: '1',
				name: 'icon_paginator' + Math.random()
			});
		},
		
		/**
         * @desc 修改各元素的位置
         * @param {innerPanel} 内层div容器对象
		 * @private
         */			
		_updatePosition: function(innerPanel) {   
			var w = this.contentWidth
			    ,h = this.content.height()
			    ,$this=this
				,ulNumber = this.ul.size();
			    this.moveWidth = w;

				//设置桌面图标容器元素区域大小
				innerPanel.css({width:((w) * ulNumber), height: h});
				innerPanel.children('ul').css({width: w, height: h, 'margin-right':0});
				 
				//添加翻页
				if(this.options.isshowpaginator){
					 this.paginator.appendTo(this.content).css({
						 'position': 'absolute',
						 'left': '45%',
						 'bottom': '10',
						 'padding': '0'
					 });
				}
				this.ul.each(function(){
					  $this._iconsArrange($(this));
				});
				 
				//设置面板的偏移
				$this._panelMove(innerPanel, $this.ul, w, $this.options.activeindex);				 
		},		
			
		/**
         * @desc 绑定元素的拖拽事件
         * @param {innerPanel} 内层div容器对象
		 * @private
         */			
		_bindDrag: function(innerPanel){
			//var $ul = this.ul;
			var $this = this;
			
			//桌面可使用鼠标拖动切换
			var dxStart, dxEnd;  //, beginTime, endTime;
			innerPanel.draggable({
				axis: 'x',
				start: function(event,ui) {
					$(this).css("cursor", "move");
					beginTime = new Date().getTime();
					dxStart = event.pageX;
				},
				stop: function(event,ui) {
					var $ul = $this.ul;
					$(this).css("cursor", "inherit");
					//endTime = new Date().getTime();
					dxEnd = event.pageX;
					//var timeCha = endTime - beginTime
					var dxCha = dxEnd - dxStart  //鼠标的拖动距离，根据拖动距离判断是否翻页
						,currDesktop = $(this).find("ul.currDesktop")
						,deskIndex = $ul.index(currDesktop);
					var moveWidth = $this.contentWidth;

					//左移
					if(dxCha < -150 && deskIndex < ($ul.size()-1)) {
						$this._panelMove($(this), $ul, moveWidth, deskIndex+1);
					//右移
					}else if(dxCha > 150 && deskIndex > 0) {
						$this._panelMove($(this), $ul, moveWidth, deskIndex-1);
					}else{
						$(this).animate({
							left: - (deskIndex) * moveWidth
						}, $this.options.scrolltime);
					}
				}	
			});			 
		},
		
		/**
         * @desc 绑定图标事件 
         * @param {ul} ul对象
		 * @private
         */			
		_bindIconEvent: function(ul){
			var $this = this;
			this.ul.children('li').on('click', function(e, i){
				$this._trigger('click', e, $this.paramIconData[$(this).attr('index')]);
			});
		},
		
		/**
         * @desc 绑定翻页组件 
         * @param {innerPanel} 内层div容器对象
		 * @private 
         */			
		_bindPaginator: function(innerPanel){
			var $this = this;
			$this.paginator.paginator({
				click: function(e, ui){
					$this._panelMove(innerPanel, $this.ul, $this.contentWidth, ui.page);
				}
			});
		},
		
		/**
         * @desc 控制面板的移动
         * @param {innerPanel} 内层div容器对象
         * @param {ul} ul元素对象
         * @param {moveWidth} 翻页时页面的宽度
         * @param {nextIndex} 页面的所引值
		 * @private
         */			
		_panelMove: function(innerPanel, ul, moveWidth, nextIndex) {
			var $this = this;
			innerPanel.stop().animate({
				 left: -(nextIndex) * moveWidth
			}, $this.options.scrolltime, function() {
				 ul.removeClass("currDesktop").eq(nextIndex).addClass("currDesktop");
				 if($this.options.isshowpaginator){
					 $this.paginator.paginator('option', 'page', nextIndex);
				 }
	         	 if(nextIndex > 0){
		        	 $this.leftButton.addClass('jazz-iconpanel-leftbtn');
	        	 }else{
	        		 $this.leftButton.removeClass('jazz-iconpanel-leftbtn');
	        	 }

//	        	 if(nextIndex < ul.size()-1){
//	        		 $this.rightButton.addClass('jazz-iconpanel-rightbtn');
//	        	 }else{
//	        		 $this.rightButton.removeClass('jazz-iconpanel-rightbtn');
//	        	 }			 
			 			 
	 		});
			this.options.activeindex = nextIndex;
	
		},		
		
		/**
         * @desc 图标的排列
         * @param {ul} ul元素对象
		 * @private
         */			
		_iconsArrange: function(ul) {
			 var $ul = ul;
			
			 var navBarHeight = 0
			     //计算一共有多少图标
			     ,iconNum = $ul.find("li").size();
				
			 	 //存储当前总共有多少桌面图标
			 	 $ul.data('iconNum', iconNum);
			 
			 var gH = this.options.paramicons.height;  //一个图标总高度，包括上下margin
			 var gW = this.options.paramicons.width;   //图标总宽度,包括左右margin
			 
			 gH = parseInt(this.innerPanel.height()/parseInt(this.options.rows))-1;
			 
			 var rows=Math.floor((this.contentHeight-navBarHeight)/gH);     //页面图标行数
			 var cols=Math.floor(this.contentWidth/gW);                    //页面图标列数
			 var curcol=0, currow=0;
			 var mW = 0; mH = 0;
			 if(this.options.theme === 1){
				 mW = 30; mH = 20;
			 }
			 
			 $ul.find("li").css({
			     "position":"absolute",
			     "margin": 0,
			     "left": function(index, value){
				             /* 行排列  */
				   		     var v = (index - cols*currow)*gW + mW;
				   		     if((index+1)%cols == 0){
				   			    currow = currow + 1;                                                
				   		     }
				   		     return v;
//	                         /* 列排列 */				   
//					           var v = curcol*gW + 30;
//					           if((index+1)%rows==0){
//							       curcol=curcol+1;
//					           }
//						       return v;	 
					    },
				 "top": function(index, value){
					         /* 行排列  */
							 var v = curcol*gH + mH;
							 if((index+1)%cols == 0){
								curcol = curcol + 1;
							 }
						     return v;
//	                         /* 行排列 */					       
//							   var v=(index-rows*currow)*gH+20;
//							   if((index+1)%rows==0){
//									currow=currow+1;
//							   }
//						       return v;
				        }
			});
		},

		/**
         * @desc 添加左右按钮滚动
         * @param {innerPanel} 内层div容器对象
         * @param {number}     窗体数量
         */	
		_buildButton: function(innerPanel, number){
			var $this = this;
	        this.leftButton = $('<a href="javascript:void(0);" target="_self"></a>').appendTo(innerPanel.parent());
	        this.rightButton = $('<a href="javascript:void(0);" target="_self"></a>').appendTo(innerPanel.parent());	
	        this.leftButton.on('click.icon', function(){
	        	if($this.options.activeindex > 0){
	        		$this._panelMove(innerPanel, $this.ul, $this.contentWidth, $this.options.activeindex-1);
	        	}
	       	});
	       	this.rightButton.on('click.icon', function(){
	       		if($this.options.activeindex < $this.ul.size()-1){
	       			$this._panelMove(innerPanel, $this.ul, $this.contentWidth, $this.options.activeindex+1);
	       		}
//2014-3-19
//加入else @desc最后页时移动到首页
	       		else{
	       			$this._panelMove(innerPanel, $this.ul, $this.contentWidth, 0);
	       		}
//2014-3-19	       		
	       	});
	       	
	       	innerPanel.parent().hover(function(){
	         	 if($this.options.activeindex > 0){
		        	 $this.leftButton.addClass('jazz-iconpanel-leftbtn');
	        	 }
//2014-3-19 @ 注释掉了if 当鼠标放到面板上时，显示按键	         	 
//	         	 if($this.options.activeindex < $this.ul.size()-1){
	         		$this.rightButton.addClass('jazz-iconpanel-rightbtn');	         		 
//	         	 }
//2014-3-19
           },function(){
           	     $this.leftButton.removeClass('jazz-iconpanel-leftbtn');
           	     $this.rightButton.removeClass('jazz-iconpanel-rightbtn');
           }); 
		},
		
	   /**
        * @desc 重新加载数据
        * @param {url} 数据URL
        * @param {params} URL所要带的参数
        * @param {flag} 标记
		* @example $('XXX').icon('loadData', url, params, flag);
        */
       loadData: function(url, params, flag){ 
	       if(!url){
	       		if(this.options.dataurl != null){
	       			if(!!params){
	       				this.options.params = params;
	       			}
	       			this.innerPanel.children().remove();
	       			if(this.options.isshowpaginator){
	       				this.paginator.remove();
	       				this.paginator = $('<div></div>');
	       			}
	    			this._build(this.innerPanel);
	    			this._bindDrag(this.innerPanel);
	    			if(this.options.isshowpaginator){
	    				this._bindPaginator(this.innerPanel); //绑定翻页组件
	    			}
	       		}
	        }else{
	        	this.innerPanel.children().remove();
	        	if(this.options.isshowpaginator){
	        		this.paginator.remove();
	        		this.paginator = $('<div></div>');	        	
	        	}
       			if(flag == 'static'){   //static  目的是为了加载SwordPageData数据
            		this.options.data = url;          		
            	}else{
            		if(!!url){
            			this.options.dataurl = url;
            			this.options.data = null;
            		}
            		if(!!params){
            			this.options.params = params;
            		}
            	}	
            	
    			this._build(this.innerPanel);
    			this._bindDrag(this.innerPanel);
    			if(this.options.isshowpaginator){
    				this._bindPaginator(this.innerPanel); //绑定翻页组件            	
    			}
	       	}
        },	
        
        getCount: function(id){
        	var img = $('#img_'+id);
	    		img.addClass('s-icon-img');
	    		
	    		if($.trim(img.html())){
	    			return img.html();
	    		}else{
	        		return 0;
	        	}
        },

        updateCount: function(url, params){
            var param = {
    		   url: url,
    		   params: params,
    		   async: false,
     	       callback: this._callbackCount  //回调函数
           };
           $.DataAdapter.submit2(param, this);          	
        },
        
        _callbackCount: function (data, sourceThis){
            	var jsonData = data;
    			
    			if(!!jsonData && !!jsonData.data){
    				 for(var i = 0; i < jsonData.data.length; i++) {
    					 var obj = jsonData.data[i];
    					 if(obj.count=='0'){
    						 $('#img_'+obj.id).hide();
    					 }else{
    						 $('#img_'+obj.id).show();
    						 $('#img_'+obj.id).html(obj.count);
    					 }
    				 }
    			}
          }       	
		

	});
	
})(jQuery);

(function($){
	var settings = {}, roots = {}, caches = {},
	//default consts of core
	_consts = {
		className: {
			BUTTON: "button",
			LEVEL: "level",
			ICO_LOADING: "ico_loading",
			SWITCH: "switch"
		},
		event: {
			NODECREATED: "ztree_nodeCreated",
			CLICK: "ztree_click",
			EXPAND: "ztree_expand",
			COLLAPSE: "ztree_collapse",
			ASYNC_SUCCESS: "ztree_async_success",
			ASYNC_ERROR: "ztree_async_error"
		},
		id: {
			A: "_a",
			ICON: "_ico",
			SPAN: "_span",
			SWITCH: "_switch",
			UL: "_ul"
		},
		line: {
			ROOT: "root",
			ROOTS: "roots",
			CENTER: "center",
			BOTTOM: "bottom",
			NOLINE: "noline",
			LINE: "line"
		},
		folder: {
			OPEN: "open",
			CLOSE: "close",
			DOCU: "docu"
		},
		node: {
			CURSELECTED: "curSelectedNode"
		}
	},

	_setting = {
		treeId: "",
		treeObj: null,
		view: {
			addDiyDom: null,
			autoCancelSelected: true,
			dblClickExpand: true,
			expandSpeed: "fast",
			fontCss: {},
			nameIsHTML: false,
			selectedMulti: true,
			showIcon: true,
			showLine: true,
			showTitle: true,
			txtSelectedEnable: false
		},
		data: {
			key: {
				children: "children",
				name: "name",
				title: "",
				url: "url"
			},
			simpleData: {
				enable: false,
				idKey: "id",
				pIdKey: "pId",
				rootPId: null
			},
			keep: {
				parent: false,
				leaf: false
			}
		},
		async: {
			enable: false,
			contentType: "application/x-www-form-urlencoded",
			type: "post",
			dataType: "text",
			url: "",
			autoParam: [],
			otherParam: [],
			dataFilter: null
		},
		callback: {
			beforeAsync:null,
			beforeClick:null,
			beforeDblClick:null,
			beforeRightClick:null,
			beforeMouseDown:null,
			beforeMouseUp:null,
			beforeExpand:null,
			beforeCollapse:null,
			beforeRemove:null,

			onAsyncError:null,
			onAsyncSuccess:null,
			onNodeCreated:null,
			onClick:null,
			onDblClick:null,
			onRightClick:null,
			onMouseDown:null,
			onMouseUp:null,
			onExpand:null,
			onCollapse:null,
			onRemove:null
		}
	},

	_initRoot = function (setting) {                                             
		var r = data.getRoot(setting);
		if (!r) {
			r = {};
			data.setRoot(setting, r);
		}
		r[setting.data.key.children] = [];
		r.expandTriggerFlag = false;
		r.curSelectedList = [];
		r.noSelection = true;
		r.createdNodes = [];
		r.zId = 0;
		r._ver = (new Date()).getTime();
	},
	//default cache of core
	_initCache = function(setting) {
		var c = data.getCache(setting);
		if (!c) {
			c = {};
			data.setCache(setting, c);
		}
		c.nodes = [];
		c.doms = [];
	},
	//default bindEvent of core
	_bindEvent = function(setting) {
		var o = setting.treeObj,
		c = consts.event;
		o.bind(c.NODECREATED, function (event, treeId, node) {
			tools.apply(setting.callback.onNodeCreated, [event, treeId, node]);
		});

		o.bind(c.CLICK, function (event, srcEvent, treeId, node, clickFlag) {
			tools.apply(setting.callback.onClick, [srcEvent, treeId, node, clickFlag]);
		});

		o.bind(c.EXPAND, function (event, treeId, node) {
			tools.apply(setting.callback.onExpand, [event, treeId, node]);
		});

		o.bind(c.COLLAPSE, function (event, treeId, node) {
			tools.apply(setting.callback.onCollapse, [event, treeId, node]);
		});

		o.bind(c.ASYNC_SUCCESS, function (event, treeId, node, msg) {
			tools.apply(setting.callback.onAsyncSuccess, [event, treeId, node, msg]);
		});

		o.bind(c.ASYNC_ERROR, function (event, treeId, node, XMLHttpRequest, textStatus, errorThrown) {
			tools.apply(setting.callback.onAsyncError, [event, treeId, node, XMLHttpRequest, textStatus, errorThrown]);
		});
	},
	_unbindEvent = function(setting) {
		var o = setting.treeObj,
		c = consts.event;
		o.unbind(c.NODECREATED)
		.unbind(c.CLICK)
		.unbind(c.EXPAND)
		.unbind(c.COLLAPSE)
		.unbind(c.ASYNC_SUCCESS)
		.unbind(c.ASYNC_ERROR);                              
	},
	//default event proxy of core
	_eventProxy = function(event) {
		var target = event.target,
		setting = data.getSetting(event.data.treeId),
		tId = "", node = null,
		nodeEventType = "", treeEventType = "",
		nodeEventCallback = null, treeEventCallback = null,
		tmp = null;

		if (tools.eqs(event.type, "mousedown")) {
			treeEventType = "mousedown";
		} else if (tools.eqs(event.type, "mouseup")) {
			treeEventType = "mouseup";
		} else if (tools.eqs(event.type, "contextmenu")) {
			treeEventType = "contextmenu";
		} else if (tools.eqs(event.type, "click")) {
			if (tools.eqs(target.tagName, "span") && target.getAttribute("treeNode"+ consts.id.SWITCH) !== null) {
				tId = tools.getNodeMainDom(target).id;
				nodeEventType = "switchNode";
			} else {
				tmp = tools.getMDom(setting, target, [{tagName:"a", attrName:"treeNode"+consts.id.A}]);
				if (tmp) {
					tId = tools.getNodeMainDom(tmp).id;
					nodeEventType = "clickNode";
				}
			}
		} else if (tools.eqs(event.type, "dblclick")) {
			treeEventType = "dblclick";
			tmp = tools.getMDom(setting, target, [{tagName:"a", attrName:"treeNode"+consts.id.A}]);
			if (tmp) {
				tId = tools.getNodeMainDom(tmp).id;
				nodeEventType = "switchNode";
			}
		}
		if (treeEventType.length > 0 && tId.length == 0) {
			tmp = tools.getMDom(setting, target, [{tagName:"a", attrName:"treeNode"+consts.id.A}]);
			if (tmp) {tId = tools.getNodeMainDom(tmp).id;}
		}
		// event to node
		if (tId.length>0) {
			node = data.getNodeCache(setting, tId);
			switch (nodeEventType) {
				case "switchNode" :
					if (!node.isparent) {
						nodeEventType = "";
					} else if (tools.eqs(event.type, "click")
						|| (tools.eqs(event.type, "dblclick") && tools.apply(setting.view.dblClickExpand, [setting.treeId, node], setting.view.dblClickExpand))) {
						nodeEventCallback = handler.onSwitchNode;
					} else {
						nodeEventType = "";
					}
					break;
				case "clickNode" :
					nodeEventCallback = handler.onClickNode;
					break;
			}
		}
		// event to zTree
		switch (treeEventType) {
			case "mousedown" :
				treeEventCallback = handler.onZTreeMousedown;
				break;
			case "mouseup" :
				treeEventCallback = handler.onZTreeMouseup;
				break;
			case "dblclick" :
				treeEventCallback = handler.onZTreeDblclick;
				break;
			case "contextmenu" :
				treeEventCallback = handler.onZTreeContextmenu;
				break;
		}
		var proxyResult = {
			stop: false,
			node: node,
			nodeEventType: nodeEventType,
			nodeEventCallback: nodeEventCallback,
			treeEventType: treeEventType,
			treeEventCallback: treeEventCallback
		};
		return proxyResult;
	},
	//default init node of core
	_initNode = function(setting, level, n, parentNode, isFirstNode, isLastNode, openFlag) {
		if (!n) return;
		var r = data.getRoot(setting),
		childKey = setting.data.key.children;
		n.level = level;
		n.tId = setting.treeId + "_" + (++r.zId);
		n.parentTId = parentNode ? parentNode.tId : null;
		n.open = (typeof n.open == "string") ? tools.eqs(n.open, "true") : !!n.open;
		if (n[childKey] && n[childKey].length > 0) {
			n.isparent = true;
			n.zAsync = true;
		} else {
			n.isparent = (typeof n.isparent == "string") ? tools.eqs(n.isparent, "true") : !!n.isparent;
			n.open = (n.isparent && !setting.async.enable) ? n.open : false;
			n.zAsync = !n.isparent;
		}
		n.isFirstNode = isFirstNode;
		n.isLastNode = isLastNode;
		n.getParentNode = function() {return data.getNodeCache(setting, n.parentTId);};
		n.getPreNode = function() {return data.getPreNode(setting, n);};
		n.getNextNode = function() {return data.getNextNode(setting, n);};
		n.isAjaxing = false;
		data.fixPIdKeyValue(setting, n);
	},
	_init = {
		bind: [_bindEvent],
		unbind: [_unbindEvent],
		caches: [_initCache],
		nodes: [_initNode],
		proxys: [_eventProxy],
		roots: [_initRoot],
		beforeA: [],
		afterA: [],
		innerBeforeA: [],
		innerAfterA: [],
		zTreeTools: []
	},
	//method of operate data
	data = {
		addNodeCache: function(setting, node) {
			data.getCache(setting).nodes[data.getNodeCacheId(node.tId)] = node;
		},
		getNodeCacheId: function(tId) {
			return tId.substring(tId.lastIndexOf("_")+1);
		},
		addAfterA: function(afterA) {
			_init.afterA.push(afterA);
		},
		addBeforeA: function(beforeA) {
			_init.beforeA.push(beforeA);
		},
		addInnerAfterA: function(innerAfterA) {
			_init.innerAfterA.push(innerAfterA);
		},
		addInnerBeforeA: function(innerBeforeA) {
			_init.innerBeforeA.push(innerBeforeA);
		},
		addInitBind: function(bindEvent) {
			_init.bind.push(bindEvent);
		},
		addInitUnBind: function(unbindEvent) {
			_init.unbind.push(unbindEvent);
		},
		addInitCache: function(initCache) {
			_init.caches.push(initCache);
		},
		addInitNode: function(initNode) {
			_init.nodes.push(initNode);
		},
		addInitProxy: function(initProxy, isFirst) {
			if (!!isFirst) {
				_init.proxys.splice(0,0,initProxy);
			} else {
				_init.proxys.push(initProxy);
			}
		},
		addInitRoot: function(initRoot) {
			_init.roots.push(initRoot);
		},
		addNodesData: function(setting, parentNode, nodes) {
			var childKey = setting.data.key.children;
			if (!parentNode[childKey]) parentNode[childKey] = [];
			if (parentNode[childKey].length > 0) {
				parentNode[childKey][parentNode[childKey].length - 1].isLastNode = false;
				view.setNodeLineIcos(setting, parentNode[childKey][parentNode[childKey].length - 1]);
			}
			parentNode.isparent = true;
			parentNode[childKey] = parentNode[childKey].concat(nodes);
		},
		addSelectedNode: function(setting, node) {
			var root = data.getRoot(setting);
			if (!data.isSelectedNode(setting, node)) {
				root.curSelectedList.push(node);
			}
		},
		addCreatedNode: function(setting, node) {
			if (!!setting.callback.onNodeCreated || !!setting.view.addDiyDom) {
				var root = data.getRoot(setting);
				root.createdNodes.push(node);
			}
		},
		addZTreeTools: function(zTreeTools) {
			_init.zTreeTools.push(zTreeTools);
		},
		exSetting: function(s) {
			$.extend(true, _setting, s);
		},
		fixPIdKeyValue: function(setting, node) {
			if (setting.data.simpleData.enable) {
				node[setting.data.simpleData.pIdKey] = node.parentTId ? node.getParentNode()[setting.data.simpleData.idKey] : setting.data.simpleData.rootPId;
			}
		},
		getAfterA: function(setting, node, array) {
			for (var i=0, j=_init.afterA.length; i<j; i++) {
				_init.afterA[i].apply(this, arguments);
			}
		},
		getBeforeA: function(setting, node, array) {
			for (var i=0, j=_init.beforeA.length; i<j; i++) {
				_init.beforeA[i].apply(this, arguments);
			}
		},
		getInnerAfterA: function(setting, node, array) {
			for (var i=0, j=_init.innerAfterA.length; i<j; i++) {
				_init.innerAfterA[i].apply(this, arguments);
			}
		},
		getInnerBeforeA: function(setting, node, array) {
			for (var i=0, j=_init.innerBeforeA.length; i<j; i++) {
				_init.innerBeforeA[i].apply(this, arguments);
			}
		},
		getCache: function(setting) {
			return caches[setting.treeId];
		},
		getNextNode: function(setting, node) {
			if (!node) return null;
			var childKey = setting.data.key.children,
			p = node.parentTId ? node.getParentNode() : data.getRoot(setting);
			for (var i=0, l=p[childKey].length-1; i<=l; i++) {
				if (p[childKey][i] === node) {
					return (i==l ? null : p[childKey][i+1]);
				}
			}
			return null;
		},
		getNodeByParam: function(setting, nodes, key, value) {
			if (!nodes || !key) return null;
			var childKey = setting.data.key.children;
			for (var i = 0, l = nodes.length; i < l; i++) {
				if (nodes[i][key] == value) {
					return nodes[i];
				}
				var tmp = data.getNodeByParam(setting, nodes[i][childKey], key, value);
				if (tmp) return tmp;
			}
			return null;
		},
		getNodeCache: function(setting, tId) {
			if (!tId) return null;
			var n = caches[setting.treeId].nodes[data.getNodeCacheId(tId)];
			return n ? n : null;
		},
		getNodeName: function(setting, node) {
			var nameKey = setting.data.key.name;
			return "" + node[nameKey];
		},
		getNodeTitle: function(setting, node) {
			var t = setting.data.key.title === "" ? setting.data.key.name : setting.data.key.title;
			return "" + node[t];
		},
		getNodes: function(setting) {
			return data.getRoot(setting)[setting.data.key.children];
		},
		getNodesByParam: function(setting, nodes, key, value) {
			if (!nodes || !key) return [];
			var childKey = setting.data.key.children,
			result = [];
			for (var i = 0, l = nodes.length; i < l; i++) {
				if (nodes[i][key] == value) {
					result.push(nodes[i]);
				}
				result = result.concat(data.getNodesByParam(setting, nodes[i][childKey], key, value));
			}
			return result;
		},
		getNodesByParamFuzzy: function(setting, nodes, key, value) {
			if (!nodes || !key) return [];
			var childKey = setting.data.key.children,
			result = [];
			value = value.toLowerCase();
			for (var i = 0, l = nodes.length; i < l; i++) {
				if (typeof nodes[i][key] == "string" && nodes[i][key].toLowerCase().indexOf(value)>-1) {
					result.push(nodes[i]);
				}
				result = result.concat(data.getNodesByParamFuzzy(setting, nodes[i][childKey], key, value));
			}
			return result;
		},
		getNodesByFilter: function(setting, nodes, filter, isSingle, invokeParam) {
			if (!nodes) return (isSingle ? null : []);
			var childKey = setting.data.key.children,
			result = isSingle ? null : [];
			for (var i = 0, l = nodes.length; i < l; i++) {
				if (tools.apply(filter, [nodes[i], invokeParam], false)) {
					if (isSingle) {return nodes[i];}
					result.push(nodes[i]);
				}
				var tmpResult = data.getNodesByFilter(setting, nodes[i][childKey], filter, isSingle, invokeParam);
				if (isSingle && !!tmpResult) {return tmpResult;}
				result = isSingle ? tmpResult : result.concat(tmpResult);
			}
			return result;
		},
		getPreNode: function(setting, node) {
			if (!node) return null;
			var childKey = setting.data.key.children,
			p = node.parentTId ? node.getParentNode() : data.getRoot(setting);
			for (var i=0, l=p[childKey].length; i<l; i++) {
				if (p[childKey][i] === node) {
					return (i==0 ? null : p[childKey][i-1]);
				}
			}
			return null;
		}, 
		getRoot: function(setting) {
			return setting ? roots[setting.treeId] : null;
		},
		getRoots: function() {
			return roots;
		},
		getSetting: function(treeId) {
			return settings[treeId];
		},
		getSettings: function() {
			return settings;
		},
		getZTreeTools: function(treeId) {
			var r = this.getRoot(this.getSetting(treeId));
			return r ? r.treeTools : null;
		},
		initCache: function(setting) {
			for (var i=0, j=_init.caches.length; i<j; i++) {
				_init.caches[i].apply(this, arguments);
			}
		},
		initNode: function(setting, level, node, parentNode, preNode, nextNode) {
			for (var i=0, j=_init.nodes.length; i<j; i++) {
				_init.nodes[i].apply(this, arguments);
			}
		},
		initRoot: function(setting) {
			for (var i=0, j=_init.roots.length; i<j; i++) {
				_init.roots[i].apply(this, arguments);
			}
		},
		isSelectedNode: function(setting, node) {
			var root = data.getRoot(setting);
			for (var i=0, j=root.curSelectedList.length; i<j; i++) {
				if(node === root.curSelectedList[i]) return true;
			}
			return false;
		},
		removeNodeCache: function(setting, node) {
			var childKey = setting.data.key.children;
			if (node[childKey]) {
				for (var i=0, l=node[childKey].length; i<l; i++) {
					arguments.callee(setting, node[childKey][i]);
				}
			}
			data.getCache(setting).nodes[data.getNodeCacheId(node.tId)] = null;
		},
		removeSelectedNode: function(setting, node) {
			var root = data.getRoot(setting);
			for (var i=0, j=root.curSelectedList.length; i<j; i++) {
				if(node === root.curSelectedList[i] || !data.getNodeCache(setting, root.curSelectedList[i].tId)) {
					root.curSelectedList.splice(i, 1);
					i--;j--;
				}
			}
		},
		setCache: function(setting, cache) {
			caches[setting.treeId] = cache;
		},
		setRoot: function(setting, root) {
			roots[setting.treeId] = root;
		},
		setZTreeTools: function(setting, zTreeTools) {
			for (var i=0, j=_init.zTreeTools.length; i<j; i++) {
				_init.zTreeTools[i].apply(this, arguments);
			}
		},
		transformToArrayFormat: function (setting, nodes) {
			if (!nodes) return [];
			var childKey = setting.data.key.children,
			r = [];
			if (tools.isArray(nodes)) {
				for (var i=0, l=nodes.length; i<l; i++) {
					r.push(nodes[i]);
					if (nodes[i][childKey])
						r = r.concat(data.transformToArrayFormat(setting, nodes[i][childKey]));
				}
			} else {
				r.push(nodes);
				if (nodes[childKey])
					r = r.concat(data.transformToArrayFormat(setting, nodes[childKey]));
			}
			return r;
		},
		transformTozTreeFormat: function(setting, sNodes) {
			var i,l,
			key = setting.data.simpleData.idKey,
			parentKey = setting.data.simpleData.pIdKey,
			childKey = setting.data.key.children;
			if (!key || key=="" || !sNodes) return [];

			if (tools.isArray(sNodes)) {
				var r = [];
				var tmpMap = [];
				for (i=0, l=sNodes.length; i<l; i++) {
					tmpMap[sNodes[i][key]] = sNodes[i];
				}
				for (i=0, l=sNodes.length; i<l; i++) {
					if (tmpMap[sNodes[i][parentKey]] && sNodes[i][key] != sNodes[i][parentKey]) {
						if (!tmpMap[sNodes[i][parentKey]][childKey])
							tmpMap[sNodes[i][parentKey]][childKey] = [];
						tmpMap[sNodes[i][parentKey]][childKey].push(sNodes[i]);
					} else {
						r.push(sNodes[i]);
					}
				}
				return r;
			}else {
				return [sNodes];
			}
		}
	},
	//method of event proxy
	event = {
		bindEvent: function(setting) {
			for (var i=0, j=_init.bind.length; i<j; i++) {
				_init.bind[i].apply(this, arguments);
			}
		},
		unbindEvent: function(setting) {
			for (var i=0, j=_init.unbind.length; i<j; i++) {
				_init.unbind[i].apply(this, arguments);
			}
		},
		bindTree: function(setting) {
			var eventParam = {
				treeId: setting.treeId
			},
			o = setting.treeObj;
			if (!setting.view.txtSelectedEnable) {
				// for can't select text
				o.bind('selectstart', function(e){
					var node
					var n = e.originalEvent.srcElement.nodeName.toLowerCase();
					return (n === "input" || n === "textarea" );
				}).css({
					"-moz-user-select":"-moz-none"
				});
			}
			o.bind('click', eventParam, event.proxy);
			o.bind('dblclick', eventParam, event.proxy);
			o.bind('mouseover', eventParam, event.proxy);
			o.bind('mouseout', eventParam, event.proxy);
			o.bind('mousedown', eventParam, event.proxy);
			o.bind('mouseup', eventParam, event.proxy);
			o.bind('contextmenu', eventParam, event.proxy);
		},
		unbindTree: function(setting) {
			var o = setting.treeObj;
			o.unbind('click', event.proxy)
			.unbind('dblclick', event.proxy)
			.unbind('mouseover', event.proxy)
			.unbind('mouseout', event.proxy)
			.unbind('mousedown', event.proxy)
			.unbind('mouseup', event.proxy)
			.unbind('contextmenu', event.proxy);
		},
		doProxy: function(e) {
			var results = [];
			for (var i=0, j=_init.proxys.length; i<j; i++) {
				var proxyResult = _init.proxys[i].apply(this, arguments);
				results.push(proxyResult);
				if (proxyResult.stop) {
					break;
				}
			}
			return results;
		},
		proxy: function(e) {
			var setting = data.getSetting(e.data.treeId);
			if (!tools.uCanDo(setting, e)) return true;
			var results = event.doProxy(e),
			r = true, x = false;
			for (var i=0, l=results.length; i<l; i++) {
				var proxyResult = results[i];
				if (proxyResult.nodeEventCallback) {
					x = true;
					r = proxyResult.nodeEventCallback.apply(proxyResult, [e, proxyResult.node]) && r;
				}
				if (proxyResult.treeEventCallback) {
					x = true;
					r = proxyResult.treeEventCallback.apply(proxyResult, [e, proxyResult.node]) && r;
				}
			}
			return r;
		}
	},
	//method of event handler
	handler = {
		onSwitchNode: function (event, node) {
			var setting = data.getSetting(event.data.treeId);
			if (node.open) {
				if (tools.apply(setting.callback.beforeCollapse, [setting.treeId, node], true) == false) return true;
				data.getRoot(setting).expandTriggerFlag = true;
				view.switchNode(setting, node);
			} else {
				if (tools.apply(setting.callback.beforeExpand, [setting.treeId, node], true) == false) return true;
				data.getRoot(setting).expandTriggerFlag = true;
				view.switchNode(setting, node);
			}
			return true;
		},
		onClickNode: function (event, node) {
			var setting = data.getSetting(event.data.treeId),
			clickFlag = ( (setting.view.autoCancelSelected && event.ctrlKey) && data.isSelectedNode(setting, node)) ? 0 : (setting.view.autoCancelSelected && event.ctrlKey && setting.view.selectedMulti) ? 2 : 1;
			if (tools.apply(setting.callback.beforeClick, [setting.treeId, node, clickFlag], true) == false) return true;
			if (clickFlag === 0) {
				view.cancelPreSelectedNode(setting, node);
			} else {
				view.selectNode(setting, node, clickFlag === 2);
			}
			setting.treeObj.trigger(consts.event.CLICK, [event, setting.treeId, node, clickFlag]);
			return true;
		},
		onZTreeMousedown: function(event, node) {
			var setting = data.getSetting(event.data.treeId);
			if (tools.apply(setting.callback.beforeMouseDown, [setting.treeId, node], true)) {
				tools.apply(setting.callback.onMouseDown, [event, setting.treeId, node]);
			}
			return true;
		},
		onZTreeMouseup: function(event, node) {
			var setting = data.getSetting(event.data.treeId);
			if (tools.apply(setting.callback.beforeMouseUp, [setting.treeId, node], true)) {
				tools.apply(setting.callback.onMouseUp, [event, setting.treeId, node]);
			}
			return true;
		},
		onZTreeDblclick: function(event, node) {
			var setting = data.getSetting(event.data.treeId);
			if (tools.apply(setting.callback.beforeDblClick, [setting.treeId, node], true)) {
				tools.apply(setting.callback.onDblClick, [event, setting.treeId, node]);
			}
			return true;
		},
		onZTreeContextmenu: function(event, node) {
			var setting = data.getSetting(event.data.treeId);
			if (tools.apply(setting.callback.beforeRightClick, [setting.treeId, node], true)) {
				tools.apply(setting.callback.onRightClick, [event, setting.treeId, node]);
			}
			return (typeof setting.callback.onRightClick) != "function";
		}
	},
	//method of tools for zTree
	tools = {
		apply: function(fun, param, defaultValue) {
			if ((typeof fun) == "function") {
				return fun.apply(zt, param?param:[]);
			}
			return defaultValue;
		},
		canAsync: function(setting, node) {
			var childKey = setting.data.key.children;
			return (setting.async.enable && node && node.isparent && !(node.zAsync || (node[childKey] && node[childKey].length > 0)));
		},
		clone: function (obj){
			if (obj === null) return null;
			var o = tools.isArray(obj) ? [] : {};
			for(var i in obj){
				o[i] = (obj[i] instanceof Date) ? new Date(obj[i].getTime()) : (typeof obj[i] === "object" ? arguments.callee(obj[i]) : obj[i]);
			}
			return o;
		},
		eqs: function(str1, str2) {
			return str1.toLowerCase() === str2.toLowerCase();
		},
		isArray: function(arr) {
			return Object.prototype.toString.apply(arr) === "[object Array]";
		},
		$: function(node, exp, setting) {
			if (!!exp && typeof exp != "string") {
				setting = exp;
				exp = "";
			}
			if (typeof node == "string") {
				return $(node, setting ? setting.treeObj.get(0).ownerDocument : null);
			} else {
				return $("#" + node.tId + exp, setting ? setting.treeObj : null);
			}
		},
		getMDom: function (setting, curDom, targetExpr) {
			if (!curDom) return null;
			while (curDom && curDom.id !== setting.treeId) {
				for (var i=0, l=targetExpr.length; curDom.tagName && i<l; i++) {
					if (tools.eqs(curDom.tagName, targetExpr[i].tagName) && curDom.getAttribute(targetExpr[i].attrName) !== null) {
						return curDom;
					}
				}
				curDom = curDom.parentNode;
			}
			return null;
		},
		getNodeMainDom:function(target) {
			return ($(target).parent("li").get(0) || $(target).parentsUntil("li").parent().get(0));
		},
		isChildOrSelf: function(dom, parentId) {
			return ( $(dom).closest("#" + parentId).length> 0 );
		},
		uCanDo: function(setting, e) {
			return true;
		}
	},
	//method of operate ztree dom
	view = {
		addNodes: function(setting, parentNode, newNodes, isSilent) {
			if (setting.data.keep.leaf && parentNode && !parentNode.isparent) {
				return;
			}
			if (!tools.isArray(newNodes)) {
				newNodes = [newNodes];
			}
			if (setting.data.simpleData.enable) {
				newNodes = data.transformTozTreeFormat(setting, newNodes);
			}
			if (parentNode) {
				var target_switchObj = $$(parentNode, consts.id.SWITCH, setting),
				target_icoObj = $$(parentNode, consts.id.ICON, setting),
				target_ulObj = $$(parentNode, consts.id.UL, setting);

				if (!parentNode.open) {
					view.replaceSwitchClass(parentNode, target_switchObj, consts.folder.CLOSE);
					view.replaceIcoClass(parentNode, target_icoObj, consts.folder.CLOSE);
					parentNode.open = false;
					target_ulObj.css({
						"display": "none"
					});
				}

				data.addNodesData(setting, parentNode, newNodes);
				view.createNodes(setting, parentNode.level + 1, newNodes, parentNode);
				if (!isSilent) {
					view.expandCollapseParentNode(setting, parentNode, true);
				}
			} else {
				data.addNodesData(setting, data.getRoot(setting), newNodes);
				view.createNodes(setting, 0, newNodes, null);
			}
		},
		appendNodes: function(setting, level, nodes, parentNode, initFlag, openFlag) {
			if (!nodes) return [];
			var html = [],
			childKey = setting.data.key.children;
			for (var i = 0, l = nodes.length; i < l; i++) {
				var node = nodes[i];
				node.id = $.trim(node.id);
				if (initFlag) {
					var tmpPNode = (parentNode) ? parentNode: data.getRoot(setting),
					tmpPChild = tmpPNode[childKey],
					isFirstNode = ((tmpPChild.length == nodes.length) && (i == 0)),
					isLastNode = (i == (nodes.length - 1));
					data.initNode(setting, level, node, parentNode, isFirstNode, isLastNode, openFlag);
					data.addNodeCache(setting, node);
				}

				var childHtml = [];
				if (node[childKey] && node[childKey].length > 0) {
					//make child html first, because checkType
					childHtml = view.appendNodes(setting, level + 1, node[childKey], node, initFlag, openFlag && node.open);
				}
				if (openFlag) {

					view.makeDOMNodeMainBefore(html, setting, node);
					view.makeDOMNodeLine(html, setting, node);
					data.getBeforeA(setting, node, html);
					view.makeDOMNodeNameBefore(html, setting, node);
					data.getInnerBeforeA(setting, node, html);
					view.makeDOMNodeIcon(html, setting, node);
					data.getInnerAfterA(setting, node, html);
					view.makeDOMNodeNameAfter(html, setting, node);
					data.getAfterA(setting, node, html);
					if (node.isparent && node.open) {
						view.makeUlHtml(setting, node, html, childHtml.join(''));
					}
					view.makeDOMNodeMainAfter(html, setting, node);
					data.addCreatedNode(setting, node);
				}
			}
			return html;
		},
		appendParentULDom: function(setting, node) {
			var html = [],
			nObj = $$(node, setting);
			if (!nObj.get(0) && !!node.parentTId) {
				view.appendParentULDom(setting, node.getParentNode());
				nObj = $$(node, setting);
			}
			var ulObj = $$(node, consts.id.UL, setting);
			if (ulObj.get(0)) {
				ulObj.remove();
			}
			var childKey = setting.data.key.children,
			childHtml = view.appendNodes(setting, node.level+1, node[childKey], node, false, true);
			view.makeUlHtml(setting, node, html, childHtml.join(''));
			nObj.append(html.join(''));
		},
		asyncNode: function(setting, node, isSilent, callback) {
			var i, l;
			if (node && !node.isparent) {
				tools.apply(callback);
				return false;
			} else if (node && node.isAjaxing) {
				return false;
			} else if (tools.apply(setting.callback.beforeAsync, [setting.treeId, node], true) == false) {
				tools.apply(callback);
				return false;
			}
			if (node) {
				node.isAjaxing = true;
				var icoObj = $$(node, consts.id.ICON, setting);
				icoObj.attr({"style":"", "class":consts.className.BUTTON + " " + consts.className.ICO_LOADING});
			}

			var tmpParam = {};
			for (i = 0, l = setting.async.autoParam.length; node && i < l; i++) {
				var pKey = setting.async.autoParam[i].split("="), spKey = pKey;
				if (pKey.length>1) {
					spKey = pKey[1];
					pKey = pKey[0];
				}
				tmpParam[spKey] = node[pKey];
			}
			if (tools.isArray(setting.async.otherParam)) {
				for (i = 0, l = setting.async.otherParam.length; i < l; i += 2) {
					tmpParam[setting.async.otherParam[i]] = setting.async.otherParam[i + 1];
				}
			} else {
				for (var p in setting.async.otherParam) {
					tmpParam[p] = setting.async.otherParam[p];
				}
			}

			var _tmpV = data.getRoot(setting)._ver;
			
			/**
			 *@desc 重新封装zTree中的ajax提交方法中的data参数，符合sword的参数格式
			 *@author hanzhiwei
			 */
			//--开始--
			var swordData = new Object();
			var dataArray = new Array();
			if(!!tmpParam){
				$.each(tmpParam, function(k, v){
					var str = {'name': k, 'value': v +'', 'sword': 'attr'};
					dataArray.push(eval(str));
				});
				swordData["data"] = dataArray;
			}
			var treeurl = tools.apply(setting.async.url, [setting.treeId, node], setting.async.url);
			if(!!treeurl){
				swordData["tid"] = treeurl;
			}
			var obj = new Object();
	 		obj["postData"] = JSON.stringify(swordData);
	 		tmpParam = obj;
	 		//--结束--
	 		
			$.ajax({
				contentType: setting.async.contentType,
				type: setting.async.type,
				url: tools.apply(setting.async.url, [setting.treeId, node], setting.async.url),
				data: tmpParam,
				dataType: setting.async.dataType,
				success: function(msg) {
					 /**
					 *@desc 重新封装zTree中的ajax提交方法中返回回调函数的msg参数，符合sword的参数格式
					 *@author hanzhiwei
					 */
					//--开始--
					if(msg.data.length>0){
						msg = msg.data[0].data;
					}
					//--结束--
					if (_tmpV != data.getRoot(setting)._ver) {
						return;
					}
					var newNodes = [];
					try {
						if (!msg || msg.length == 0) {
							newNodes = [];
						} else if (typeof msg == "string") {
							newNodes = eval("(" + msg + ")");
						} else {
							newNodes = msg;
						}
					} catch(err) {
						newNodes = msg;
					}

					if (node) {
						node.isAjaxing = null;
						node.zAsync = true;
					}
					view.setNodeLineIcos(setting, node);
					if (newNodes && newNodes !== "") {
						newNodes = tools.apply(setting.async.dataFilter, [setting.treeId, node, newNodes], newNodes);
						view.addNodes(setting, node, !!newNodes ? tools.clone(newNodes) : [], !!isSilent);
					} else {
						view.addNodes(setting, node, [], !!isSilent);
					}
					setting.treeObj.trigger(consts.event.ASYNC_SUCCESS, [setting.treeId, node, msg]);
					tools.apply(callback);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					if (_tmpV != data.getRoot(setting)._ver) {
						return;
					}
					if (node) node.isAjaxing = null;
					view.setNodeLineIcos(setting, node);
					setting.treeObj.trigger(consts.event.ASYNC_ERROR, [setting.treeId, node, XMLHttpRequest, textStatus, errorThrown]);
				}
			});
			return true;
		},
		cancelPreSelectedNode: function (setting, node) {
			var list = data.getRoot(setting).curSelectedList;
			for (var i=0, j=list.length-1; j>=i; j--) {
				if (!node || node === list[j]) {
					$$(list[j], consts.id.A, setting).removeClass(consts.node.CURSELECTED);
					if (node) {
						data.removeSelectedNode(setting, node);
						break;
					}
				}
			}
			if (!node) data.getRoot(setting).curSelectedList = [];
		},
		createNodeCallback: function(setting) {
			if (!!setting.callback.onNodeCreated || !!setting.view.addDiyDom) {
				var root = data.getRoot(setting);
				while (root.createdNodes.length>0) {
					var node = root.createdNodes.shift();
					tools.apply(setting.view.addDiyDom, [setting.treeId, node]);
					if (!!setting.callback.onNodeCreated) {
						setting.treeObj.trigger(consts.event.NODECREATED, [setting.treeId, node]);
					}
				}
			}
		},
		createNodes: function(setting, level, nodes, parentNode) {
			if (!nodes || nodes.length == 0) return;
			var root = data.getRoot(setting),
			childKey = setting.data.key.children,
			openFlag = !parentNode || parentNode.open || !!$$(parentNode[childKey][0], setting).get(0);
			root.createdNodes = [];
			var zTreeHtml = view.appendNodes(setting, level, nodes, parentNode, true, openFlag);
			if (!parentNode) {
				setting.treeObj.append(zTreeHtml.join(''));
			} else {
				var ulObj = $$(parentNode, consts.id.UL, setting);
				if (ulObj.get(0)) {
					ulObj.append(zTreeHtml.join(''));
				}
			}
			view.createNodeCallback(setting);
		},
		destroy: function(setting) {
			if (!setting) return;
			data.initCache(setting);
			data.initRoot(setting);
			event.unbindTree(setting);
			event.unbindEvent(setting);
			setting.treeObj.empty();
		},
		expandCollapseNode: function(setting, node, expandFlag, animateFlag, callback) {
			var root = data.getRoot(setting),
			childKey = setting.data.key.children;
			if (!node) {
				tools.apply(callback, []);
				return;
			}
			if (root.expandTriggerFlag) {
				var _callback = callback;
				callback = function(){
					if (_callback) _callback();
					if (node.open) {
						setting.treeObj.trigger(consts.event.EXPAND, [setting.treeId, node]);
					} else {
						setting.treeObj.trigger(consts.event.COLLAPSE, [setting.treeId, node]);
					}
				};
				root.expandTriggerFlag = false;
			}
			if (!node.open && node.isparent && ((!$$(node, consts.id.UL, setting).get(0)) || (node[childKey] && node[childKey].length>0 && !$$(node[childKey][0], setting).get(0)))) {
				view.appendParentULDom(setting, node);
				view.createNodeCallback(setting);
			}
			if (node.open == expandFlag) {
				tools.apply(callback, []);
				return;
			}
			var ulObj = $$(node, consts.id.UL, setting),
			switchObj = $$(node, consts.id.SWITCH, setting),
			icoObj = $$(node, consts.id.ICON, setting);

			if (node.isparent) {
				node.open = !node.open;
				if (node.iconOpen && node.iconClose) {
					icoObj.attr("style", view.makeNodeIcoStyle(setting, node));
				}

				if (node.open) {
					view.replaceSwitchClass(node, switchObj, consts.folder.OPEN);
					view.replaceIcoClass(node, icoObj, consts.folder.OPEN);
					if (animateFlag == false || setting.view.expandSpeed == "") {
						ulObj.show();
						tools.apply(callback, []);
					} else {
						if (node[childKey] && node[childKey].length > 0) {
							ulObj.slideDown(setting.view.expandSpeed, callback);
						} else {
							ulObj.show();
							tools.apply(callback, []);
						}
					}
				} else {
					view.replaceSwitchClass(node, switchObj, consts.folder.CLOSE);
					view.replaceIcoClass(node, icoObj, consts.folder.CLOSE);
					if (animateFlag == false || setting.view.expandSpeed == "" || !(node[childKey] && node[childKey].length > 0)) {
						ulObj.hide();
						tools.apply(callback, []);
					} else {
						ulObj.slideUp(setting.view.expandSpeed, callback);
					}
				}
			} else {
				tools.apply(callback, []);
			}
		},
		expandCollapseParentNode: function(setting, node, expandFlag, animateFlag, callback) {
			if (!node) return;
			if (!node.parentTId) {
				view.expandCollapseNode(setting, node, expandFlag, animateFlag, callback);
				return;
			} else {
				view.expandCollapseNode(setting, node, expandFlag, animateFlag);
			}
			if (node.parentTId) {
				view.expandCollapseParentNode(setting, node.getParentNode(), expandFlag, animateFlag, callback);
			}
		},
		expandCollapseSonNode: function(setting, node, expandFlag, animateFlag, callback) {
			var root = data.getRoot(setting),
			childKey = setting.data.key.children,
			treeNodes = (node) ? node[childKey]: root[childKey],
			selfAnimateSign = (node) ? false : animateFlag,
			expandTriggerFlag = data.getRoot(setting).expandTriggerFlag;
			data.getRoot(setting).expandTriggerFlag = false;
			if (treeNodes) {
				for (var i = 0, l = treeNodes.length; i < l; i++) {
					if (treeNodes[i]) view.expandCollapseSonNode(setting, treeNodes[i], expandFlag, selfAnimateSign);
				}
			}
			data.getRoot(setting).expandTriggerFlag = expandTriggerFlag;
			view.expandCollapseNode(setting, node, expandFlag, animateFlag, callback );
		},
		makeDOMNodeIcon: function(html, setting, node) {
			var nameStr = data.getNodeName(setting, node),
			name = setting.view.nameIsHTML ? nameStr : nameStr.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
			html.push("<span id='", node.tId, consts.id.ICON,
				"' title='' treeNode", consts.id.ICON," class='", view.makeNodeIcoClass(setting, node),
				"' style='", view.makeNodeIcoStyle(setting, node), "'></span><span id='", node.tId, consts.id.SPAN,
				"'>",name,"</span>");
		},
		makeDOMNodeLine: function(html, setting, node) {
			html.push("<span id='", node.tId, consts.id.SWITCH,	"' title='' class='", view.makeNodeLineClass(setting, node), "' treeNode", consts.id.SWITCH,"></span>");
		},
		makeDOMNodeMainAfter: function(html, setting, node) {
			html.push("</li>");
		},
		makeDOMNodeMainBefore: function(html, setting, node) {
			html.push("<li id='", node.tId, "' class='", consts.className.LEVEL, node.level,"' tabindex='0' hidefocus='true' treenode>");
		},
		makeDOMNodeNameAfter: function(html, setting, node) {
			html.push("</a>");
		},
		makeDOMNodeNameBefore: function(html, setting, node) {
			var title = data.getNodeTitle(setting, node),
			url = view.makeNodeUrl(setting, node),
			fontcss = view.makeNodeFontCss(setting, node),
			fontStyle = [];
			for (var f in fontcss) {
				fontStyle.push(f, ":", fontcss[f], ";");
			}
			html.push("<a id='", node.tId, consts.id.A, "' class='", consts.className.LEVEL, node.level,"' treeNode", consts.id.A," onclick=\"", (node.click || ''),
				"\" ", ((url != null && url.length > 0) ? "href='" + url + "'" : ""), " target='",view.makeNodeTarget(node),"' style='", fontStyle.join(''),
				"'");
			if (tools.apply(setting.view.showTitle, [setting.treeId, node], setting.view.showTitle) && title) {html.push("title='", title.replace(/'/g,"&#39;").replace(/</g,'&lt;').replace(/>/g,'&gt;'),"'");}
			html.push(">");
		},
		makeNodeFontCss: function(setting, node) {
			var fontCss = tools.apply(setting.view.fontCss, [setting.treeId, node], setting.view.fontCss);
			return (fontCss && ((typeof fontCss) != "function")) ? fontCss : {};
		},
		makeNodeIcoClass: function(setting, node) {
			var icoCss = ["ico"];
			if (!node.isAjaxing) {
				icoCss[0] = (node.iconskin ? node.iconskin + "_" : "") + icoCss[0];
				if (node.isparent) {
					icoCss.push(node.open ? consts.folder.OPEN : consts.folder.CLOSE);
				} else {
					icoCss.push(consts.folder.DOCU);
				}
			}
			return consts.className.BUTTON + " " + icoCss.join('_');
		},
		makeNodeIcoStyle: function(setting, node) {
			var icoStyle = [];
			if (!node.isAjaxing) {
				var icon = (node.isparent && node.iconOpen && node.iconClose) ? (node.open ? node.iconOpen : node.iconClose) : node.icon;
				if (icon) icoStyle.push("background:url(", icon, ") 0 0 no-repeat;");
				if (setting.view.showIcon == false || !tools.apply(setting.view.showIcon, [setting.treeId, node], true)) {
					icoStyle.push("width:0px;height:0px;");
				}
			}
			return icoStyle.join('');
		},
		makeNodeLineClass: function(setting, node) {
			var lineClass = [];
			if (setting.view.showLine) {
				if (node.level == 0 && node.isFirstNode && node.isLastNode) {
					lineClass.push(consts.line.ROOT);
				} else if (node.level == 0 && node.isFirstNode) {
					lineClass.push(consts.line.ROOTS);
				} else if (node.isLastNode) {
					lineClass.push(consts.line.BOTTOM);
				} else {
					lineClass.push(consts.line.CENTER);
				}
			} else {
				lineClass.push(consts.line.NOLINE);
			}
			if (node.isparent) {
				lineClass.push(node.open ? consts.folder.OPEN : consts.folder.CLOSE);
			} else {
				lineClass.push(consts.folder.DOCU);
			}
			return view.makeNodeLineClassEx(node) + lineClass.join('_');
		},
		makeNodeLineClassEx: function(node) {
			return consts.className.BUTTON + " " + consts.className.LEVEL + node.level + " " + consts.className.SWITCH + " ";
		},
		makeNodeTarget: function(node) {
			return (node.target || "_blank");
		},
		makeNodeUrl: function(setting, node) {
			var urlKey = setting.data.key.url;
			return node[urlKey] ? node[urlKey] : null;
		},
		makeUlHtml: function(setting, node, html, content) {
			html.push("<ul id='", node.tId, consts.id.UL, "' class='", consts.className.LEVEL, node.level, " ", view.makeUlLineClass(setting, node), "' style='display:", (node.open ? "block": "none"),"'>");
			html.push(content);
			html.push("</ul>");
		},
		makeUlLineClass: function(setting, node) {
			return ((setting.view.showLine && !node.isLastNode) ? consts.line.LINE : "");
		},
		removeChildNodes: function(setting, node) {
			if (!node) return;
			var childKey = setting.data.key.children,
			nodes = node[childKey];
			if (!nodes) return;

			for (var i = 0, l = nodes.length; i < l; i++) {
				data.removeNodeCache(setting, nodes[i]);
			}
			data.removeSelectedNode(setting);
			delete node[childKey];

			if (!setting.data.keep.parent) {
				node.isparent = false;
				node.open = false;
				var tmp_switchObj = $$(node, consts.id.SWITCH, setting),
				tmp_icoObj = $$(node, consts.id.ICON, setting);
				view.replaceSwitchClass(node, tmp_switchObj, consts.folder.DOCU);
				view.replaceIcoClass(node, tmp_icoObj, consts.folder.DOCU);
				$$(node, consts.id.UL, setting).remove();
			} else {
				$$(node, consts.id.UL, setting).empty();
			}
		},
		setFirstNode: function(setting, parentNode) {
			var childKey = setting.data.key.children, childLength = parentNode[childKey].length;
			if ( childLength > 0) {
				parentNode[childKey][0].isFirstNode = true;
			}
		},
		setLastNode: function(setting, parentNode) {
			var childKey = setting.data.key.children, childLength = parentNode[childKey].length;
			if ( childLength > 0) {
				parentNode[childKey][childLength - 1].isLastNode = true;
			}
		},
		removeNode: function(setting, node) {
			var root = data.getRoot(setting),
			childKey = setting.data.key.children,
			parentNode = (node.parentTId) ? node.getParentNode() : root;

			node.isFirstNode = false;
			node.isLastNode = false;
			node.getPreNode = function() {return null;};
			node.getNextNode = function() {return null;};

			if (!data.getNodeCache(setting, node.tId)) {
				return;
			}

			$$(node, setting).remove();
			data.removeNodeCache(setting, node);
			data.removeSelectedNode(setting, node);

			for (var i = 0, l = parentNode[childKey].length; i < l; i++) {
				if (parentNode[childKey][i].tId == node.tId) {
					parentNode[childKey].splice(i, 1);
					break;
				}
			}
			view.setFirstNode(setting, parentNode);
			view.setLastNode(setting, parentNode);

			var tmp_ulObj,tmp_switchObj,tmp_icoObj,
			childLength = parentNode[childKey].length;

			//repair nodes old parent
			if (!setting.data.keep.parent && childLength == 0) {
				//old parentNode has no child nodes
				parentNode.isparent = false;
				parentNode.open = false;
				tmp_ulObj = $$(parentNode, consts.id.UL, setting);
				tmp_switchObj = $$(parentNode, consts.id.SWITCH, setting);
				tmp_icoObj = $$(parentNode, consts.id.ICON, setting);
				view.replaceSwitchClass(parentNode, tmp_switchObj, consts.folder.DOCU);
				view.replaceIcoClass(parentNode, tmp_icoObj, consts.folder.DOCU);
				tmp_ulObj.css("display", "none");

			} else if (setting.view.showLine && childLength > 0) {
				//old parentNode has child nodes
				var newLast = parentNode[childKey][childLength - 1];
				tmp_ulObj = $$(newLast, consts.id.UL, setting);
				tmp_switchObj = $$(newLast, consts.id.SWITCH, setting);
				tmp_icoObj = $$(newLast, consts.id.ICON, setting);
				if (parentNode == root) {
					if (parentNode[childKey].length == 1) {
						//node was root, and ztree has only one root after move node
						view.replaceSwitchClass(newLast, tmp_switchObj, consts.line.ROOT);
					} else {
						var tmp_first_switchObj = $$(parentNode[childKey][0], consts.id.SWITCH, setting);
						view.replaceSwitchClass(parentNode[childKey][0], tmp_first_switchObj, consts.line.ROOTS);
						view.replaceSwitchClass(newLast, tmp_switchObj, consts.line.BOTTOM);
					}
				} else {
					view.replaceSwitchClass(newLast, tmp_switchObj, consts.line.BOTTOM);
				}
				tmp_ulObj.removeClass(consts.line.LINE);
			}
		},
		replaceIcoClass: function(node, obj, newName) {
			if (!obj || node.isAjaxing) return;
			var tmpName = obj.attr("class");
			if (tmpName == undefined) return;
			var tmpList = tmpName.split("_");
			switch (newName) {
				case consts.folder.OPEN:
				case consts.folder.CLOSE:
				case consts.folder.DOCU:
					tmpList[tmpList.length-1] = newName;
					break;
			}
			obj.attr("class", tmpList.join("_"));
		},
		replaceSwitchClass: function(node, obj, newName) {
			if (!obj) return;
			var tmpName = obj.attr("class");
			if (tmpName == undefined) return;
			var tmpList = tmpName.split("_");
			switch (newName) {
				case consts.line.ROOT:
				case consts.line.ROOTS:
				case consts.line.CENTER:
				case consts.line.BOTTOM:
				case consts.line.NOLINE:
					tmpList[0] = view.makeNodeLineClassEx(node) + newName;
					break;
				case consts.folder.OPEN:
				case consts.folder.CLOSE:
				case consts.folder.DOCU:
					tmpList[1] = newName;
					break;
			}
			obj.attr("class", tmpList.join("_"));
			if (newName !== consts.folder.DOCU) {
				obj.removeAttr("disabled");
			} else {
				obj.attr("disabled", "disabled");
			}
		},
		selectNode: function(setting, node, addFlag) {
			if (!addFlag) {
				view.cancelPreSelectedNode(setting);
			}
			$$(node, consts.id.A, setting).addClass(consts.node.CURSELECTED);
			data.addSelectedNode(setting, node);
		},
		setNodeFontCss: function(setting, treeNode) {
			var aObj = $$(treeNode, consts.id.A, setting),
			fontCss = view.makeNodeFontCss(setting, treeNode);
			if (fontCss) {
				aObj.css(fontCss);
			}
		},
		setNodeLineIcos: function(setting, node) {
			if (!node) return;
			var switchObj = $$(node, consts.id.SWITCH, setting),
			ulObj = $$(node, consts.id.UL, setting),
			icoObj = $$(node, consts.id.ICON, setting),
			ulLine = view.makeUlLineClass(setting, node);
			if (ulLine.length==0) {
				ulObj.removeClass(consts.line.LINE);
			} else {
				ulObj.addClass(ulLine);
			}
			switchObj.attr("class", view.makeNodeLineClass(setting, node));
			if (node.isparent) {
				switchObj.removeAttr("disabled");
			} else {
				switchObj.attr("disabled", "disabled");
			}
			icoObj.removeAttr("style");
			icoObj.attr("style", view.makeNodeIcoStyle(setting, node));
			icoObj.attr("class", view.makeNodeIcoClass(setting, node));
		},
		setNodeName: function(setting, node) {
			var title = data.getNodeTitle(setting, node),
			nObj = $$(node, consts.id.SPAN, setting);
			nObj.empty();
			if (setting.view.nameIsHTML) {
				nObj.html(data.getNodeName(setting, node));
			} else {
				nObj.text(data.getNodeName(setting, node));
			}
			if (tools.apply(setting.view.showTitle, [setting.treeId, node], setting.view.showTitle)) {
				var aObj = $$(node, consts.id.A, setting);
				aObj.attr("title", !title ? "" : title);
			}
		},
		setNodeTarget: function(setting, node) {
			var aObj = $$(node, consts.id.A, setting);
			aObj.attr("target", view.makeNodeTarget(node));
		},
		setNodeUrl: function(setting, node) {
			var aObj = $$(node, consts.id.A, setting),
			url = view.makeNodeUrl(setting, node);
			if (url == null || url.length == 0) {
				aObj.removeAttr("href");
			} else {
				aObj.attr("href", url);
			}
		},
		switchNode: function(setting, node) {
			if (node.open || !tools.canAsync(setting, node)) {
				view.expandCollapseNode(setting, node, !node.open);
			} else if (setting.async.enable) {
				if (!view.asyncNode(setting, node)) {
					view.expandCollapseNode(setting, node, !node.open);
					return;
				}
			} else if (node) {
				view.expandCollapseNode(setting, node, !node.open);
			}
		}
	};
	// zTree defind
	$.fn.zTree = {
		consts : _consts,
		_z : {
			tools: tools,
			view: view,
			event: event,
			data: data
		},
		getZTreeObj: function(treeId) {
			var o = data.getZTreeTools(treeId);
			return o ? o : null;
		},
		destroy: function(treeId) {
			if (!!treeId && treeId.length > 0) {
				view.destroy(data.getSetting(treeId));
			} else {
				for(var s in settings) {
					view.destroy(settings[s]);
				}
			}
		},
		init: function(obj, zSetting, zNodes) {
			var setting = tools.clone(_setting);
			$.extend(true, setting, zSetting);
			setting.treeId = obj.attr("id");
			setting.treeObj = obj;
			setting.treeObj.empty();
			settings[setting.treeId] = setting;
			//For some older browser,(e.g., ie6)
			if(typeof document.body.style.maxHeight === "undefined") {
				setting.view.expandSpeed = "";
			}
			data.initRoot(setting);
			var root = data.getRoot(setting),
			childKey = setting.data.key.children;
			zNodes = zNodes ? tools.clone(tools.isArray(zNodes)? zNodes : [zNodes]) : [];
			if (setting.data.simpleData.enable) {
				root[childKey] = data.transformTozTreeFormat(setting, zNodes);
			} else {
				root[childKey] = zNodes;
			}

			data.initCache(setting);
			event.unbindTree(setting);
			event.bindTree(setting);
			event.unbindEvent(setting);
			event.bindEvent(setting);

			var zTreeTools = {
				setting : setting,
				addNodes : function(parentNode, newNodes, isSilent) {
					if (!newNodes) return null;
					if (!parentNode) parentNode = null;
					if (parentNode && !parentNode.isparent && setting.data.keep.leaf) return null;
					var xNewNodes = tools.clone(tools.isArray(newNodes)? newNodes: [newNodes]);
					function addCallback() {
						view.addNodes(setting, parentNode, xNewNodes, (isSilent==true));
					}

					if (tools.canAsync(setting, parentNode)) {
						view.asyncNode(setting, parentNode, isSilent, addCallback);
					} else {
						addCallback();
					}
					return xNewNodes;
				},
				cancelSelectedNode : function(node) {
					view.cancelPreSelectedNode(setting, node);
				},
				destroy : function() {
					view.destroy(setting);
				},
				expandAll : function(expandFlag) {
					expandFlag = !!expandFlag;
					view.expandCollapseSonNode(setting, null, expandFlag, true);
					return expandFlag;
				},
				expandNode : function(node, expandFlag, sonSign, focus, callbackFlag) {
					if (!node || !node.isparent) return null;
					if (expandFlag !== true && expandFlag !== false) {
						expandFlag = !node.open;
					}
					callbackFlag = !!callbackFlag;

					if (callbackFlag && expandFlag && (tools.apply(setting.callback.beforeExpand, [setting.treeId, node], true) == false)) {
						return null;
					} else if (callbackFlag && !expandFlag && (tools.apply(setting.callback.beforeCollapse, [setting.treeId, node], true) == false)) {
						return null;
					}
					if (expandFlag && node.parentTId) {
						view.expandCollapseParentNode(setting, node.getParentNode(), expandFlag, false);
					}
					if (expandFlag === node.open && !sonSign) {
						return null;
					}

					data.getRoot(setting).expandTriggerFlag = callbackFlag;
					if (!tools.canAsync(setting, node) && sonSign) {
						view.expandCollapseSonNode(setting, node, expandFlag, true, function() {
							if (focus !== false) {try{$$(node, setting).focus().blur();}catch(e){}}
						});
					} else {
						node.open = !expandFlag;
						view.switchNode(this.setting, node);
						if (focus !== false) {try{$$(node, setting).focus().blur();}catch(e){}}
					}
					return expandFlag;
				},
				getNodes : function() {
					return data.getNodes(setting);
				},
				getNodeByParam : function(key, value, parentNode) {
					if (!key) return null;
					return data.getNodeByParam(setting, parentNode?parentNode[setting.data.key.children]:data.getNodes(setting), key, value);
				},
				getNodeByTId : function(tId) {
					return data.getNodeCache(setting, tId);
				},
				getNodesByParam : function(key, value, parentNode) {
					if (!key) return null;
					return data.getNodesByParam(setting, parentNode?parentNode[setting.data.key.children]:data.getNodes(setting), key, value);
				},
				getNodesByParamFuzzy : function(key, value, parentNode) {
					if (!key) return null;
					return data.getNodesByParamFuzzy(setting, parentNode?parentNode[setting.data.key.children]:data.getNodes(setting), key, value);
				},
				getNodesByFilter: function(filter, isSingle, parentNode, invokeParam) {
					isSingle = !!isSingle;
					if (!filter || (typeof filter != "function")) return (isSingle ? null : []);
					return data.getNodesByFilter(setting, parentNode?parentNode[setting.data.key.children]:data.getNodes(setting), filter, isSingle, invokeParam);
				},
				getNodeIndex : function(node) {
					if (!node) return null;
					var childKey = setting.data.key.children,
					parentNode = (node.parentTId) ? node.getParentNode() : data.getRoot(setting);
					for (var i=0, l = parentNode[childKey].length; i < l; i++) {
						if (parentNode[childKey][i] == node) return i;
					}
					return -1;
				},
				getSelectedNodes : function() {
					var r = [], list = data.getRoot(setting).curSelectedList;
					for (var i=0, l=list.length; i<l; i++) {
						r.push(list[i]);
					}
					return r;
				},
				isSelectedNode : function(node) {
					return data.isSelectedNode(setting, node);
				},
				reAsyncChildNodes : function(parentNode, reloadType, isSilent) {
					if (!this.setting.async.enable) return;
					var isRoot = !parentNode;
					if (isRoot) {
						parentNode = data.getRoot(setting);
					}
					if (reloadType=="refresh") {
						var childKey = this.setting.data.key.children;
						for (var i = 0, l = parentNode[childKey] ? parentNode[childKey].length : 0; i < l; i++) {
							data.removeNodeCache(setting, parentNode[childKey][i]);
						}
						data.removeSelectedNode(setting);
						parentNode[childKey] = [];
						if (isRoot) {
							this.setting.treeObj.empty();
						} else {
							var ulObj = $$(parentNode, consts.id.UL, setting);
							ulObj.empty();
						}
					}
					view.asyncNode(this.setting, isRoot? null:parentNode, !!isSilent);
				},
				refresh : function() {
					this.setting.treeObj.empty();
					var root = data.getRoot(setting),
					nodes = root[setting.data.key.children]
					data.initRoot(setting);
					root[setting.data.key.children] = nodes
					data.initCache(setting);
					view.createNodes(setting, 0, root[setting.data.key.children]);
				},
				removeChildNodes : function(node) {
					if (!node) return null;
					var childKey = setting.data.key.children,
					nodes = node[childKey];
					view.removeChildNodes(setting, node);
					return nodes ? nodes : null;
				},
				removeNode : function(node, callbackFlag) {
					if (!node) return;
					callbackFlag = !!callbackFlag;
					if (callbackFlag && tools.apply(setting.callback.beforeRemove, [setting.treeId, node], true) == false) return;
					view.removeNode(setting, node);
					if (callbackFlag) {
						this.setting.treeObj.trigger(consts.event.REMOVE, [setting.treeId, node]);
					}
				},
				selectNode : function(node, addFlag) {
					if (!node) return;
					if (tools.uCanDo(setting)) {
						addFlag = setting.view.selectedMulti && addFlag;
						if (node.parentTId) {
							view.expandCollapseParentNode(setting, node.getParentNode(), true, false, function() {
								try{$$(node, setting).focus().blur();}catch(e){}
							});
						} else {
							try{$$(node, setting).focus().blur();}catch(e){}
						}
						view.selectNode(setting, node, addFlag);
					}
				},
				transformTozTreeNodes : function(simpleNodes) {
					return data.transformTozTreeFormat(setting, simpleNodes);
				},
				transformToArray : function(nodes) {
					return data.transformToArrayFormat(setting, nodes);
				},
				updateNode : function(node, checkTypeFlag) {
					if (!node) return;
					var nObj = $$(node, setting);
					if (nObj.get(0) && tools.uCanDo(setting)) {
						view.setNodeName(setting, node);
						view.setNodeTarget(setting, node);
						view.setNodeUrl(setting, node);
						view.setNodeLineIcos(setting, node);
						view.setNodeFontCss(setting, node);
					}
				}
			}
			root.treeTools = zTreeTools;
			data.setZTreeTools(setting, zTreeTools);

			if (root[childKey] && root[childKey].length > 0) {
				view.createNodes(setting, 0, root[childKey]);
			} else if (setting.async.enable && setting.async.url && setting.async.url !== '') {
				view.asyncNode(setting);
			}
			return zTreeTools;
		}
	};

	var zt = $.fn.zTree,
	$$ = tools.$,
	consts = zt.consts;
})(jQuery);
/*
 * JQuery zTree excheck v3.5.15
 * http://zTree.me/
 *
 * Copyright (c) 2010 Hunter.z
 *
 * Licensed same as jquery - MIT License
 * http://www.opensource.org/licenses/mit-license.php
 *
 * email: hunter.z@263.net
 * Date: 2013-10-15
 */
(function($){
	//default consts of excheck
	var _consts = {
		event: {
			CHECK: "ztree_check"
		},
		id: {
			CHECK: "_check"
		},
		checkbox: {
			STYLE: "checkbox",
			DEFAULT: "chk",
			DISABLED: "disable",
			FALSE: "false",
			TRUE: "true",
			FULL: "full",
			PART: "part",
			FOCUS: "focus"
		},
		radio: {
			STYLE: "radio",
			TYPE_ALL: "all",
			TYPE_LEVEL: "level"
		}
	},
	//default setting of excheck
	_setting = {
		check: {
			enable: false,
			autoCheckTrigger: false,
			chkStyle: _consts.checkbox.STYLE,
			nocheckInherit: false,
			chkDisabledInherit: false,
			radioType: _consts.radio.TYPE_LEVEL,
			chkboxType: {
				"Y": "ps",
				"N": "ps"
			}
		},
		data: {
			key: {
				checked: "checked"
			}
		},
		callback: {
			beforeCheck:null,
			onCheck:null
		}
	},
	//default root of excheck
	_initRoot = function (setting) {
		var r = data.getRoot(setting);
		r.radioCheckedList = [];
	},
	//default cache of excheck
	_initCache = function(treeId) {},
	//default bind event of excheck
	_bindEvent = function(setting) {
		var o = setting.treeObj,
		c = consts.event;
		o.bind(c.CHECK, function (event, srcEvent, treeId, node) {
			tools.apply(setting.callback.onCheck, [!!srcEvent?srcEvent : event, treeId, node]);
		});
	},
	_unbindEvent = function(setting) {
		var o = setting.treeObj,
		c = consts.event;
		o.unbind(c.CHECK);
	},
	//default event proxy of excheck
	_eventProxy = function(e) {
		var target = e.target,
		setting = data.getSetting(e.data.treeId),
		tId = "", node = null,
		nodeEventType = "", treeEventType = "",
		nodeEventCallback = null, treeEventCallback = null;

		if (tools.eqs(e.type, "mouseover")) {
			if (setting.check.enable && tools.eqs(target.tagName, "span") && target.getAttribute("treeNode"+ consts.id.CHECK) !== null) {
				tId = tools.getNodeMainDom(target).id;
				nodeEventType = "mouseoverCheck";
			}
		} else if (tools.eqs(e.type, "mouseout")) {
			if (setting.check.enable && tools.eqs(target.tagName, "span") && target.getAttribute("treeNode"+ consts.id.CHECK) !== null) {
				tId = tools.getNodeMainDom(target).id;
				nodeEventType = "mouseoutCheck";
			}
		} else if (tools.eqs(e.type, "click")) {
			if (setting.check.enable && tools.eqs(target.tagName, "span") && target.getAttribute("treeNode"+ consts.id.CHECK) !== null) {
				tId = tools.getNodeMainDom(target).id;
				nodeEventType = "checkNode";
			}
		}
		if (tId.length>0) {
			node = data.getNodeCache(setting, tId);
			switch (nodeEventType) {
				case "checkNode" :
					nodeEventCallback = _handler.onCheckNode;
					break;
				case "mouseoverCheck" :
					nodeEventCallback = _handler.onMouseoverCheck;
					break;
				case "mouseoutCheck" :
					nodeEventCallback = _handler.onMouseoutCheck;
					break;
			}
		}
		var proxyResult = {
			stop: nodeEventType === "checkNode",
			node: node,
			nodeEventType: nodeEventType,
			nodeEventCallback: nodeEventCallback,
			treeEventType: treeEventType,
			treeEventCallback: treeEventCallback
		};
		return proxyResult
	},
	//default init node of excheck
	_initNode = function(setting, level, n, parentNode, isFirstNode, isLastNode, openFlag) {
		if (!n) return;
		var checkedKey = setting.data.key.checked;
		if (typeof n[checkedKey] == "string") n[checkedKey] = tools.eqs(n[checkedKey], "true");
		n[checkedKey] = !!n[checkedKey];
		n.checkedOld = n[checkedKey];
		if (typeof n.nocheck == "string") n.nocheck = tools.eqs(n.nocheck, "true");
		n.nocheck = !!n.nocheck || (setting.check.nocheckInherit && parentNode && !!parentNode.nocheck);
		if (typeof n.chkDisabled == "string") n.chkDisabled = tools.eqs(n.chkDisabled, "true");
		n.chkDisabled = !!n.chkDisabled || (setting.check.chkDisabledInherit && parentNode && !!parentNode.chkDisabled);
		if (typeof n.halfCheck == "string") n.halfCheck = tools.eqs(n.halfCheck, "true");
		n.halfCheck = !!n.halfCheck;
		n.check_Child_State = -1;
		n.check_Focus = false;
		n.getCheckStatus = function() {return data.getCheckStatus(setting, n);};

		if (setting.check.chkStyle == consts.radio.STYLE && setting.check.radioType == consts.radio.TYPE_ALL && n[checkedKey] ) {
			var r = data.getRoot(setting);
			r.radioCheckedList.push(n);
		}
	},
	//add dom for check
	_beforeA = function(setting, node, html) {
		var checkedKey = setting.data.key.checked;
		if (setting.check.enable) {
			data.makeChkFlag(setting, node);
			html.push("<span ID='", node.tId, consts.id.CHECK, "' class='", view.makeChkClass(setting, node), "' treeNode", consts.id.CHECK, (node.nocheck === true?" style='display:none;'":""),"></span>");
		}
	},
	//update zTreeObj, add method of check
	_zTreeTools = function(setting, zTreeTools) {
		zTreeTools.checkNode = function(node, checked, checkTypeFlag, callbackFlag) {
			var checkedKey = setting.data.key.checked;
			if (!node) return;
			if (node.chkDisabled === true) return;
			if (checked !== true && checked !== false) {
				checked = !node[checkedKey];
			}
			callbackFlag = !!callbackFlag;

			if (node[checkedKey] === checked && !checkTypeFlag) {
				return;
			} else if (callbackFlag && tools.apply(this.setting.callback.beforeCheck, [setting.treeId, node], true) == false) {
				return;
			}
			if (tools.uCanDo(this.setting) && setting.check.enable && node.nocheck !== true) {
				node[checkedKey] = checked;
				var checkObj = $$(node, consts.id.CHECK, setting);
				if (checkTypeFlag || setting.check.chkStyle === consts.radio.STYLE) view.checkNodeRelation(setting, node);
				view.setChkClass(setting, checkObj, node);
				view.repairParentChkClassWithSelf(setting, node);
				if (callbackFlag) {
					setting.treeObj.trigger(consts.event.CHECK, [null, setting.treeId, node]);
				}
			}
		}

		zTreeTools.checkAllNodes = function(checked) {
			view.repairAllChk(setting, !!checked);
		}

		zTreeTools.getCheckedNodes = function(checked) {
			var childKey = setting.data.key.children;
			checked = (checked !== false);
			return data.getTreeCheckedNodes(setting, data.getRoot(setting)[childKey], checked);
		}

		zTreeTools.getChangeCheckedNodes = function() {
			var childKey = setting.data.key.children;
			return data.getTreeChangeCheckedNodes(setting, data.getRoot(setting)[childKey]);
		}

		zTreeTools.setChkDisabled = function(node, disabled, inheritParent, inheritChildren) {
			disabled = !!disabled;
			inheritParent = !!inheritParent;
			inheritChildren = !!inheritChildren;
			view.repairSonChkDisabled(setting, node, disabled, inheritChildren);
			view.repairParentChkDisabled(setting, node.getParentNode(), disabled, inheritParent);
		}

		var _updateNode = zTreeTools.updateNode;
		zTreeTools.updateNode = function(node, checkTypeFlag) {
			if (_updateNode) _updateNode.apply(zTreeTools, arguments);
			if (!node || !setting.check.enable) return;
			var nObj = $$(node, setting);
			if (nObj.get(0) && tools.uCanDo(setting)) {
				var checkObj = $$(node, consts.id.CHECK, setting);
				if (checkTypeFlag == true || setting.check.chkStyle === consts.radio.STYLE) view.checkNodeRelation(setting, node);
				view.setChkClass(setting, checkObj, node);
				view.repairParentChkClassWithSelf(setting, node);
			}
		}
	},
	//method of operate data
	_data = {
		getRadioCheckedList: function(setting) {
			var checkedList = data.getRoot(setting).radioCheckedList;
			for (var i=0, j=checkedList.length; i<j; i++) {
				if(!data.getNodeCache(setting, checkedList[i].tId)) {
					checkedList.splice(i, 1);
					i--; j--;
				}
			}
			return checkedList;
		},
		getCheckStatus: function(setting, node) {
			if (!setting.check.enable || node.nocheck || node.chkDisabled) return null;
			var checkedKey = setting.data.key.checked,
			r = {
				checked: node[checkedKey],
				half: node.halfCheck ? node.halfCheck : (setting.check.chkStyle == consts.radio.STYLE ? (node.check_Child_State === 2) : (node[checkedKey] ? (node.check_Child_State > -1 && node.check_Child_State < 2) : (node.check_Child_State > 0)))
			};
			return r;
		},
		getTreeCheckedNodes: function(setting, nodes, checked, results) {
			if (!nodes) return [];
			var childKey = setting.data.key.children,
			checkedKey = setting.data.key.checked,
			onlyOne = (checked && setting.check.chkStyle == consts.radio.STYLE && setting.check.radioType == consts.radio.TYPE_ALL);
			results = !results ? [] : results;
			for (var i = 0, l = nodes.length; i < l; i++) {
				if (nodes[i].nocheck !== true && nodes[i].chkDisabled !== true && nodes[i][checkedKey] == checked) {
					results.push(nodes[i]);
					if(onlyOne) {
						break;
					}
				}
				data.getTreeCheckedNodes(setting, nodes[i][childKey], checked, results);
				if(onlyOne && results.length > 0) {
					break;
				}
			}
			return results;
		},
		getTreeChangeCheckedNodes: function(setting, nodes, results) {
			if (!nodes) return [];
			var childKey = setting.data.key.children,
			checkedKey = setting.data.key.checked;
			results = !results ? [] : results;
			for (var i = 0, l = nodes.length; i < l; i++) {
				if (nodes[i].nocheck !== true && nodes[i].chkDisabled !== true && nodes[i][checkedKey] != nodes[i].checkedOld) {
					results.push(nodes[i]);
				}
				data.getTreeChangeCheckedNodes(setting, nodes[i][childKey], results);
			}
			return results;
		},
		makeChkFlag: function(setting, node) {
			if (!node) return;
			var childKey = setting.data.key.children,
			checkedKey = setting.data.key.checked,
			chkFlag = -1;
			if (node[childKey]) {
				for (var i = 0, l = node[childKey].length; i < l; i++) {
					var cNode = node[childKey][i];
					var tmp = -1;
					if (setting.check.chkStyle == consts.radio.STYLE) {
						if (cNode.nocheck === true || cNode.chkDisabled === true) {
							tmp = cNode.check_Child_State;
						} else if (cNode.halfCheck === true) {
							tmp = 2;
						} else if (cNode[checkedKey]) {
							tmp = 2;
						} else {
							tmp = cNode.check_Child_State > 0 ? 2:0;
						}
						if (tmp == 2) {
							chkFlag = 2; break;
						} else if (tmp == 0){
							chkFlag = 0;
						}
					} else if (setting.check.chkStyle == consts.checkbox.STYLE) {
						if (cNode.nocheck === true || cNode.chkDisabled === true) {
							tmp = cNode.check_Child_State;
						} else if (cNode.halfCheck === true) {
							tmp = 1;
						} else if (cNode[checkedKey] ) {
							tmp = (cNode.check_Child_State === -1 || cNode.check_Child_State === 2) ? 2 : 1;
						} else {
							tmp = (cNode.check_Child_State > 0) ? 1 : 0;
						}
						if (tmp === 1) {
							chkFlag = 1; break;
						} else if (tmp === 2 && chkFlag > -1 && i > 0 && tmp !== chkFlag) {
							chkFlag = 1; break;
						} else if (chkFlag === 2 && tmp > -1 && tmp < 2) {
							chkFlag = 1; break;
						} else if (tmp > -1) {
							chkFlag = tmp;
						}
					}
				}
			}
			node.check_Child_State = chkFlag;
		}
	},
	//method of event proxy
	_event = {

	},
	//method of event handler
	_handler = {
		onCheckNode: function (event, node) {
			if (node.chkDisabled === true) return false;
			var setting = data.getSetting(event.data.treeId),
			checkedKey = setting.data.key.checked;
			if (tools.apply(setting.callback.beforeCheck, [setting.treeId, node], true) == false) return true;
			node[checkedKey] = !node[checkedKey];
			view.checkNodeRelation(setting, node);
			var checkObj = $$(node, consts.id.CHECK, setting);
			view.setChkClass(setting, checkObj, node);
			view.repairParentChkClassWithSelf(setting, node);
			setting.treeObj.trigger(consts.event.CHECK, [event, setting.treeId, node]);
			return true;
		},
		onMouseoverCheck: function(event, node) {
			if (node.chkDisabled === true) return false;
			var setting = data.getSetting(event.data.treeId),
			checkObj = $$(node, consts.id.CHECK, setting);
			node.check_Focus = true;
			view.setChkClass(setting, checkObj, node);
			return true;
		},
		onMouseoutCheck: function(event, node) {
			if (node.chkDisabled === true) return false;
			var setting = data.getSetting(event.data.treeId),
			checkObj = $$(node, consts.id.CHECK, setting);
			node.check_Focus = false;
			view.setChkClass(setting, checkObj, node);
			return true;
		}
	},
	//method of tools for zTree
	_tools = {

	},
	//method of operate ztree dom
	_view = {
		checkNodeRelation: function(setting, node) {
			var pNode, i, l,
			childKey = setting.data.key.children,
			checkedKey = setting.data.key.checked,
			r = consts.radio;
			if (setting.check.chkStyle == r.STYLE) {
				var checkedList = data.getRadioCheckedList(setting);
				if (node[checkedKey]) {
					if (setting.check.radioType == r.TYPE_ALL) {
						for (i = checkedList.length-1; i >= 0; i--) {
							pNode = checkedList[i];
							pNode[checkedKey] = false;
							checkedList.splice(i, 1);

							view.setChkClass(setting, $$(pNode, consts.id.CHECK, setting), pNode);
							if (pNode.parentTId != node.parentTId) {
								view.repairParentChkClassWithSelf(setting, pNode);
							}
						}
						checkedList.push(node);
					} else {
						var parentNode = (node.parentTId) ? node.getParentNode() : data.getRoot(setting);
						for (i = 0, l = parentNode[childKey].length; i < l; i++) {
							pNode = parentNode[childKey][i];
							if (pNode[checkedKey] && pNode != node) {
								pNode[checkedKey] = false;
								view.setChkClass(setting, $$(pNode, consts.id.CHECK, setting), pNode);
							}
						}
					}
				} else if (setting.check.radioType == r.TYPE_ALL) {
					for (i = 0, l = checkedList.length; i < l; i++) {
						if (node == checkedList[i]) {
							checkedList.splice(i, 1);
							break;
						}
					}
				}

			} else {
				if (node[checkedKey] && (!node[childKey] || node[childKey].length==0 || setting.check.chkboxType.Y.indexOf("s") > -1)) {
					view.setSonNodeCheckBox(setting, node, true);
				}
				if (!node[checkedKey] && (!node[childKey] || node[childKey].length==0 || setting.check.chkboxType.N.indexOf("s") > -1)) {
					view.setSonNodeCheckBox(setting, node, false);
				}
				if (node[checkedKey] && setting.check.chkboxType.Y.indexOf("p") > -1) {
					view.setParentNodeCheckBox(setting, node, true);
				}
				if (!node[checkedKey] && setting.check.chkboxType.N.indexOf("p") > -1) {
					view.setParentNodeCheckBox(setting, node, false);
				}
			}
		},
		makeChkClass: function(setting, node) {
			var checkedKey = setting.data.key.checked,
			c = consts.checkbox, r = consts.radio,
			fullStyle = "";
			if (node.chkDisabled === true) {
				fullStyle = c.DISABLED;
			} else if (node.halfCheck) {
				fullStyle = c.PART;
			} else if (setting.check.chkStyle == r.STYLE) {
				fullStyle = (node.check_Child_State < 1)? c.FULL:c.PART;
			} else {
				fullStyle = node[checkedKey] ? ((node.check_Child_State === 2 || node.check_Child_State === -1) ? c.FULL:c.PART) : ((node.check_Child_State < 1)? c.FULL:c.PART);
			}
			var chkName = setting.check.chkStyle + "_" + (node[checkedKey] ? c.TRUE : c.FALSE) + "_" + fullStyle;
			chkName = (node.check_Focus && node.chkDisabled !== true) ? chkName + "_" + c.FOCUS : chkName;
			return consts.className.BUTTON + " " + c.DEFAULT + " " + chkName;
		},
		repairAllChk: function(setting, checked) {
			if (setting.check.enable && setting.check.chkStyle === consts.checkbox.STYLE) {
				var checkedKey = setting.data.key.checked,
				childKey = setting.data.key.children,
				root = data.getRoot(setting);
				for (var i = 0, l = root[childKey].length; i<l ; i++) {
					var node = root[childKey][i];
					if (node.nocheck !== true && node.chkDisabled !== true) {
						node[checkedKey] = checked;
					}
					view.setSonNodeCheckBox(setting, node, checked);
				}
			}
		},
		repairChkClass: function(setting, node) {
			if (!node) return;
			data.makeChkFlag(setting, node);
			if (node.nocheck !== true) {
				var checkObj = $$(node, consts.id.CHECK, setting);
				view.setChkClass(setting, checkObj, node);
			}
		},
		repairParentChkClass: function(setting, node) {
			if (!node || !node.parentTId) return;
			var pNode = node.getParentNode();
			view.repairChkClass(setting, pNode);
			view.repairParentChkClass(setting, pNode);
		},
		repairParentChkClassWithSelf: function(setting, node) {
			if (!node) return;
			var childKey = setting.data.key.children;
			if (node[childKey] && node[childKey].length > 0) {
				view.repairParentChkClass(setting, node[childKey][0]);
			} else {
				view.repairParentChkClass(setting, node);
			}
		},
		repairSonChkDisabled: function(setting, node, chkDisabled, inherit) {
			if (!node) return;
			var childKey = setting.data.key.children;
			if (node.chkDisabled != chkDisabled) {
				node.chkDisabled = chkDisabled;
			}
			view.repairChkClass(setting, node);
			if (node[childKey] && inherit) {
				for (var i = 0, l = node[childKey].length; i < l; i++) {
					var sNode = node[childKey][i];
					view.repairSonChkDisabled(setting, sNode, chkDisabled, inherit);
				}
			}
		},
		repairParentChkDisabled: function(setting, node, chkDisabled, inherit) {
			if (!node) return;
			if (node.chkDisabled != chkDisabled && inherit) {
				node.chkDisabled = chkDisabled;
			}
			view.repairChkClass(setting, node);
			view.repairParentChkDisabled(setting, node.getParentNode(), chkDisabled, inherit);
		},
		setChkClass: function(setting, obj, node) {
			if (!obj) return;
			if (node.nocheck === true) {
				obj.hide();
			} else {
				obj.show();
			}
			obj.removeClass();
			obj.addClass(view.makeChkClass(setting, node));
		},
		setParentNodeCheckBox: function(setting, node, value, srcNode) {
			var childKey = setting.data.key.children,
			checkedKey = setting.data.key.checked,
			checkObj = $$(node, consts.id.CHECK, setting);
			if (!srcNode) srcNode = node;
			data.makeChkFlag(setting, node);
			if (node.nocheck !== true && node.chkDisabled !== true) {
				node[checkedKey] = value;
				view.setChkClass(setting, checkObj, node);
				if (setting.check.autoCheckTrigger && node != srcNode) {
					setting.treeObj.trigger(consts.event.CHECK, [null, setting.treeId, node]);
				}
			}
			if (node.parentTId) {
				var pSign = true;
				if (!value) {
					var pNodes = node.getParentNode()[childKey];
					for (var i = 0, l = pNodes.length; i < l; i++) {
						if ((pNodes[i].nocheck !== true && pNodes[i].chkDisabled !== true && pNodes[i][checkedKey])
						|| ((pNodes[i].nocheck === true || pNodes[i].chkDisabled === true) && pNodes[i].check_Child_State > 0)) {
							pSign = false;
							break;
						}
					}
				}
				if (pSign) {
					view.setParentNodeCheckBox(setting, node.getParentNode(), value, srcNode);
				}
			}
		},
		setSonNodeCheckBox: function(setting, node, value, srcNode) {
			if (!node) return;
			var childKey = setting.data.key.children,
			checkedKey = setting.data.key.checked,
			checkObj = $$(node, consts.id.CHECK, setting);
			if (!srcNode) srcNode = node;

			var hasDisable = false;
			if (node[childKey]) {
				for (var i = 0, l = node[childKey].length; i < l && node.chkDisabled !== true; i++) {
					var sNode = node[childKey][i];
					view.setSonNodeCheckBox(setting, sNode, value, srcNode);
					if (sNode.chkDisabled === true) hasDisable = true;
				}
			}

			if (node != data.getRoot(setting) && node.chkDisabled !== true) {
				if (hasDisable && node.nocheck !== true) {
					data.makeChkFlag(setting, node);
				}
				if (node.nocheck !== true && node.chkDisabled !== true) {
					node[checkedKey] = value;
					if (!hasDisable) node.check_Child_State = (node[childKey] && node[childKey].length > 0) ? (value ? 2 : 0) : -1;
				} else {
					node.check_Child_State = -1;
				}
				view.setChkClass(setting, checkObj, node);
				if (setting.check.autoCheckTrigger && node != srcNode && node.nocheck !== true && node.chkDisabled !== true) {
					setting.treeObj.trigger(consts.event.CHECK, [null, setting.treeId, node]);
				}
			}

		}
	},

	_z = {
		tools: _tools,
		view: _view,
		event: _event,
		data: _data
	};
	$.extend(true, $.fn.zTree.consts, _consts);
	$.extend(true, $.fn.zTree._z, _z);

	var zt = $.fn.zTree,
	tools = zt._z.tools,
	consts = zt.consts,
	view = zt._z.view,
	data = zt._z.data,
	event = zt._z.event,
	$$ = tools.$;

	data.exSetting(_setting);
	data.addInitBind(_bindEvent);
	data.addInitUnBind(_unbindEvent);
	data.addInitCache(_initCache);
	data.addInitNode(_initNode);
	data.addInitProxy(_eventProxy, true);
	data.addInitRoot(_initRoot);
	data.addBeforeA(_beforeA);
	data.addZTreeTools(_zTreeTools);

	var _createNodes = view.createNodes;
	view.createNodes = function(setting, level, nodes, parentNode) {
		if (_createNodes) _createNodes.apply(view, arguments);
		if (!nodes) return;
		view.repairParentChkClassWithSelf(setting, parentNode);
	}
	var _removeNode = view.removeNode;
	view.removeNode = function(setting, node) {
		var parentNode = node.getParentNode();
		if (_removeNode) _removeNode.apply(view, arguments);
		if (!node || !parentNode) return;
		view.repairChkClass(setting, parentNode);
		view.repairParentChkClass(setting, parentNode);
	}

	var _appendNodes = view.appendNodes;
	view.appendNodes = function(setting, level, nodes, parentNode, initFlag, openFlag) {
		var html = "";
		if (_appendNodes) {
			html = _appendNodes.apply(view, arguments);
		}
		if (parentNode) {
			data.makeChkFlag(setting, parentNode);
		}
		return html;
	}
})(jQuery);
/*
 * JQuery zTree exedit v3.5.15
 * http://zTree.me/
 *
 * Copyright (c) 2010 Hunter.z
 *
 * Licensed same as jquery - MIT License
 * http://www.opensource.org/licenses/mit-license.php
 *
 * email: hunter.z@263.net
 * Date: 2013-10-15
 */
(function($){
	//default consts of exedit
	var _consts = {
		event: {
			DRAG: "ztree_drag",
			DROP: "ztree_drop",
			REMOVE: "ztree_remove",
			RENAME: "ztree_rename"
		},
		id: {
			EDIT: "_edit",
			INPUT: "_input",
			REMOVE: "_remove"
		},
		move: {
			TYPE_INNER: "inner",
			TYPE_PREV: "prev",
			TYPE_NEXT: "next"
		},
		node: {
			CURSELECTED_EDIT: "curSelectedNode_Edit",
			TMPTARGET_TREE: "tmpTargetzTree",
			TMPTARGET_NODE: "tmpTargetNode"
		}
	},
	//default setting of exedit
	_setting = {
		edit: {
			enable: false,
			editNameSelectAll: false,
			showRemoveBtn: true,
			showRenameBtn: true,
			removeTitle: "remove",
			renameTitle: "rename",
			drag: {
				autoExpandTrigger: false,
				isCopy: true,
				isMove: true,
				prev: true,
				next: true,
				inner: true,
				minMoveSize: 5,
				borderMax: 10,
				borderMin: -5,
				maxShowNodeNum: 5,
				autoOpenTime: 500
			}
		},
		view: {
			addHoverDom: null,
			removeHoverDom: null
		},
		callback: {
			beforeDrag:null,
			beforeDragOpen:null,
			beforeDrop:null,
			beforeEditName:null,
			beforeRename:null,
			onDrag:null,
			onDrop:null,
			onRename:null
		}
	},
	//default root of exedit
	_initRoot = function (setting) {
		var r = data.getRoot(setting), rs = data.getRoots();
		r.curEditNode = null;
		r.curEditInput = null;
		r.curHoverNode = null;
		r.dragFlag = 0;
		r.dragNodeShowBefore = [];
		r.dragMaskList = new Array();
		rs.showHoverDom = true;
	},
	//default cache of exedit
	_initCache = function(treeId) {},
	//default bind event of exedit
	_bindEvent = function(setting) {
		var o = setting.treeObj;
		var c = consts.event;
		o.bind(c.RENAME, function (event, treeId, treeNode, isCancel) {
			tools.apply(setting.callback.onRename, [event, treeId, treeNode, isCancel]);
		});

		o.bind(c.REMOVE, function (event, treeId, treeNode) {
			tools.apply(setting.callback.onRemove, [event, treeId, treeNode]);
		});

		o.bind(c.DRAG, function (event, srcEvent, treeId, treeNodes) {
			tools.apply(setting.callback.onDrag, [srcEvent, treeId, treeNodes]);
		});

		o.bind(c.DROP, function (event, srcEvent, treeId, treeNodes, targetNode, moveType, isCopy) {
			tools.apply(setting.callback.onDrop, [srcEvent, treeId, treeNodes, targetNode, moveType, isCopy]);
		});
	},
	_unbindEvent = function(setting) {
		var o = setting.treeObj;
		var c = consts.event;
		o.unbind(c.RENAME);
		o.unbind(c.REMOVE);
		o.unbind(c.DRAG);
		o.unbind(c.DROP);
	},
	//default event proxy of exedit
	_eventProxy = function(e) {
		var target = e.target,
		setting = data.getSetting(e.data.treeId),
		relatedTarget = e.relatedTarget,
		tId = "", node = null,
		nodeEventType = "", treeEventType = "",
		nodeEventCallback = null, treeEventCallback = null,
		tmp = null;

		if (tools.eqs(e.type, "mouseover")) {
			tmp = tools.getMDom(setting, target, [{tagName:"a", attrName:"treeNode"+consts.id.A}]);
			if (tmp) {
				tId = tools.getNodeMainDom(tmp).id;
				nodeEventType = "hoverOverNode";
			}
		} else if (tools.eqs(e.type, "mouseout")) {
			tmp = tools.getMDom(setting, relatedTarget, [{tagName:"a", attrName:"treeNode"+consts.id.A}]);
			if (!tmp) {
				tId = "remove";
				nodeEventType = "hoverOutNode";
			}
		} else if (tools.eqs(e.type, "mousedown")) {
			tmp = tools.getMDom(setting, target, [{tagName:"a", attrName:"treeNode"+consts.id.A}]);
			if (tmp) {
				tId = tools.getNodeMainDom(tmp).id;
				nodeEventType = "mousedownNode";
			}
		}
		if (tId.length>0) {
			node = data.getNodeCache(setting, tId);
			switch (nodeEventType) {
				case "mousedownNode" :
					nodeEventCallback = _handler.onMousedownNode;
					break;
				case "hoverOverNode" :
					nodeEventCallback = _handler.onHoverOverNode;
					break;
				case "hoverOutNode" :
					nodeEventCallback = _handler.onHoverOutNode;
					break;
			}
		}
		var proxyResult = {
			stop: false,
			node: node,
			nodeEventType: nodeEventType,
			nodeEventCallback: nodeEventCallback,
			treeEventType: treeEventType,
			treeEventCallback: treeEventCallback
		};
		return proxyResult
	},
	//default init node of exedit
	_initNode = function(setting, level, n, parentNode, isFirstNode, isLastNode, openFlag) {
		if (!n) return;
		n.isHover = false;
		n.editNameFlag = false;
	},
	//update zTreeObj, add method of edit
	_zTreeTools = function(setting, zTreeTools) {
		zTreeTools.cancelEditName = function(newName) {
			var root = data.getRoot(setting);
			if (!root.curEditNode) return;
			view.cancelCurEditNode(setting, newName?newName:null, true);
		}
		zTreeTools.copyNode = function(targetNode, node, moveType, isSilent) {
			if (!node) return null;
			if (targetNode && !targetNode.isparent && setting.data.keep.leaf && moveType === consts.move.TYPE_INNER) return null;
			var newNode = tools.clone(node);
			if (!targetNode) {
				targetNode = null;
				moveType = consts.move.TYPE_INNER;
			}
			if (moveType == consts.move.TYPE_INNER) {
				function copyCallback() {
					view.addNodes(setting, targetNode, [newNode], isSilent);
				}

				if (tools.canAsync(setting, targetNode)) {
					view.asyncNode(setting, targetNode, isSilent, copyCallback);
				} else {
					copyCallback();
				}
			} else {
				view.addNodes(setting, targetNode.parentNode, [newNode], isSilent);
				view.moveNode(setting, targetNode, newNode, moveType, false, isSilent);
			}
			return newNode;
		}
		zTreeTools.editName = function(node) {
			if (!node || !node.tId || node !== data.getNodeCache(setting, node.tId)) return;
			if (node.parentTId) view.expandCollapseParentNode(setting, node.getParentNode(), true);
			view.editNode(setting, node)
		}
		zTreeTools.moveNode = function(targetNode, node, moveType, isSilent) {
			if (!node) return node;
			if (targetNode && !targetNode.isparent && setting.data.keep.leaf && moveType === consts.move.TYPE_INNER) {
				return null;
			} else if (targetNode && ((node.parentTId == targetNode.tId && moveType == consts.move.TYPE_INNER) || $$(node, setting).find("#" + targetNode.tId).length > 0)) {
				return null;
			} else if (!targetNode) {
				targetNode = null;
			}
			function moveCallback() {
				view.moveNode(setting, targetNode, node, moveType, false, isSilent);
			}
			if (tools.canAsync(setting, targetNode) && moveType === consts.move.TYPE_INNER) {
				view.asyncNode(setting, targetNode, isSilent, moveCallback);
			} else {
				moveCallback();
			}
			return node;
		}
		zTreeTools.setEditable = function(editable) {
			setting.edit.enable = editable;
			return this.refresh();
		}
	},
	//method of operate data
	_data = {
		setSonNodeLevel: function(setting, parentNode, node) {
			if (!node) return;
			var childKey = setting.data.key.children;
			node.level = (parentNode)? parentNode.level + 1 : 0;
			if (!node[childKey]) return;
			for (var i = 0, l = node[childKey].length; i < l; i++) {
				if (node[childKey][i]) data.setSonNodeLevel(setting, node, node[childKey][i]);
			}
		}
	},
	//method of event proxy
	_event = {

	},
	//method of event handler
	_handler = {
		onHoverOverNode: function(event, node) {
			var setting = data.getSetting(event.data.treeId),
			root = data.getRoot(setting);
			if (root.curHoverNode != node) {
				_handler.onHoverOutNode(event);
			}
			root.curHoverNode = node;
			view.addHoverDom(setting, node);
		},
		onHoverOutNode: function(event, node) {
			var setting = data.getSetting(event.data.treeId),
			root = data.getRoot(setting);
			if (root.curHoverNode && !data.isSelectedNode(setting, root.curHoverNode)) {
				view.removeTreeDom(setting, root.curHoverNode);
				root.curHoverNode = null;
			}
		},
		onMousedownNode: function(eventMouseDown, _node) {
			var i,l,
			setting = data.getSetting(eventMouseDown.data.treeId),
			root = data.getRoot(setting), roots = data.getRoots();
			//right click can't drag & drop
			if (eventMouseDown.button == 2 || !setting.edit.enable || (!setting.edit.drag.isCopy && !setting.edit.drag.isMove)) return true;

			//input of edit node name can't drag & drop
			var target = eventMouseDown.target,
			_nodes = data.getRoot(setting).curSelectedList,
			nodes = [];
			if (!data.isSelectedNode(setting, _node)) {
				nodes = [_node];
			} else {
				for (i=0, l=_nodes.length; i<l; i++) {
					if (_nodes[i].editNameFlag && tools.eqs(target.tagName, "input") && target.getAttribute("treeNode"+consts.id.INPUT) !== null) {
						return true;
					}
					nodes.push(_nodes[i]);
					if (nodes[0].parentTId !== _nodes[i].parentTId) {
						nodes = [_node];
						break;
					}
				}
			}

			view.editNodeBlur = true;
			view.cancelCurEditNode(setting);

			var doc = $(setting.treeObj.get(0).ownerDocument),
			body = $(setting.treeObj.get(0).ownerDocument.body), curNode, tmpArrow, tmpTarget,
			isOtherTree = false,
			targetSetting = setting,
			sourceSetting = setting,
			preNode, nextNode,
			preTmpTargetNodeId = null,
			preTmpMoveType = null,
			tmpTargetNodeId = null,
			moveType = consts.move.TYPE_INNER,
			mouseDownX = eventMouseDown.clientX,
			mouseDownY = eventMouseDown.clientY,
			startTime = (new Date()).getTime();

			if (tools.uCanDo(setting)) {
				doc.bind("mousemove", _docMouseMove);
			}
			function _docMouseMove(event) {
				//avoid start drag after click node
				if (root.dragFlag == 0 && Math.abs(mouseDownX - event.clientX) < setting.edit.drag.minMoveSize
					&& Math.abs(mouseDownY - event.clientY) < setting.edit.drag.minMoveSize) {
					return true;
				}
				var i, l, tmpNode, tmpDom, tmpNodes,
				childKey = setting.data.key.children;
				body.css("cursor", "pointer");

				if (root.dragFlag == 0) {
					if (tools.apply(setting.callback.beforeDrag, [setting.treeId, nodes], true) == false) {
						_docMouseUp(event);
						return true;
					}

					for (i=0, l=nodes.length; i<l; i++) {
						if (i==0) {
							root.dragNodeShowBefore = [];
						}
						tmpNode = nodes[i];
						if (tmpNode.isparent && tmpNode.open) {
							view.expandCollapseNode(setting, tmpNode, !tmpNode.open);
							root.dragNodeShowBefore[tmpNode.tId] = true;
						} else {
							root.dragNodeShowBefore[tmpNode.tId] = false;
						}
					}

					root.dragFlag = 1;
					roots.showHoverDom = false;
					tools.showIfameMask(setting, true);

					//sort
					var isOrder = true, lastIndex = -1;
					if (nodes.length>1) {
						var pNodes = nodes[0].parentTId ? nodes[0].getParentNode()[childKey] : data.getNodes(setting);
						tmpNodes = [];
						for (i=0, l=pNodes.length; i<l; i++) {
							if (root.dragNodeShowBefore[pNodes[i].tId] !== undefined) {
								if (isOrder && lastIndex > -1 && (lastIndex+1) !== i) {
									isOrder = false;
								}
								tmpNodes.push(pNodes[i]);
								lastIndex = i;
							}
							if (nodes.length === tmpNodes.length) {
								nodes = tmpNodes;
								break;
							}
						}
					}
					if (isOrder) {
						preNode = nodes[0].getPreNode();
						nextNode = nodes[nodes.length-1].getNextNode();
					}

					//set node in selected
					curNode = $$("<ul class='zTreeDragUL'></ul>", setting);
					for (i=0, l=nodes.length; i<l; i++) {
						tmpNode = nodes[i];
						tmpNode.editNameFlag = false;
						view.selectNode(setting, tmpNode, i>0);
						view.removeTreeDom(setting, tmpNode);

						tmpDom = $$("<li id='"+ tmpNode.tId +"_tmp'></li>", setting);
						tmpDom.append($$(tmpNode, consts.id.A, setting).clone());
						tmpDom.css("padding", "0");
						tmpDom.children("#" + tmpNode.tId + consts.id.A).removeClass(consts.node.CURSELECTED);
						curNode.append(tmpDom);
						if (i == setting.edit.drag.maxShowNodeNum-1) {
							tmpDom = $$("<li id='"+ tmpNode.tId +"_moretmp'><a>  ...  </a></li>", setting);
							curNode.append(tmpDom);
							break;
						}
					}
					curNode.attr("id", nodes[0].tId + consts.id.UL + "_tmp");
					curNode.addClass(setting.treeObj.attr("class"));
					curNode.appendTo(body);

					tmpArrow = $$("<span class='tmpzTreeMove_arrow'></span>", setting);
					tmpArrow.attr("id", "zTreeMove_arrow_tmp");
					tmpArrow.appendTo(body);

					setting.treeObj.trigger(consts.event.DRAG, [event, setting.treeId, nodes]);
				}

				if (root.dragFlag == 1) {
					if (tmpTarget && tmpArrow.attr("id") == event.target.id && tmpTargetNodeId && (event.clientX + doc.scrollLeft()+2) > ($("#" + tmpTargetNodeId + consts.id.A, tmpTarget).offset().left)) {
						var xT = $("#" + tmpTargetNodeId + consts.id.A, tmpTarget);
						event.target = (xT.length > 0) ? xT.get(0) : event.target;
					} else if (tmpTarget) {
						tmpTarget.removeClass(consts.node.TMPTARGET_TREE);
						if (tmpTargetNodeId) $("#" + tmpTargetNodeId + consts.id.A, tmpTarget).removeClass(consts.node.TMPTARGET_NODE + "_" + consts.move.TYPE_PREV)
							.removeClass(consts.node.TMPTARGET_NODE + "_" + _consts.move.TYPE_NEXT).removeClass(consts.node.TMPTARGET_NODE + "_" + _consts.move.TYPE_INNER);
					}
					tmpTarget = null;
					tmpTargetNodeId = null;

					//judge drag & drop in multi ztree
					isOtherTree = false;
					targetSetting = setting;
					var settings = data.getSettings();
					for (var s in settings) {
						if (settings[s].treeId && settings[s].edit.enable && settings[s].treeId != setting.treeId
							&& (event.target.id == settings[s].treeId || $(event.target).parents("#" + settings[s].treeId).length>0)) {
							isOtherTree = true;
							targetSetting = settings[s];
						}
					}

					var docScrollTop = doc.scrollTop(),
					docScrollLeft = doc.scrollLeft(),
					treeOffset = targetSetting.treeObj.offset(),
					scrollHeight = targetSetting.treeObj.get(0).scrollHeight,
					scrollWidth = targetSetting.treeObj.get(0).scrollWidth,
					dTop = (event.clientY + docScrollTop - treeOffset.top),
					dBottom = (targetSetting.treeObj.height() + treeOffset.top - event.clientY - docScrollTop),
					dLeft = (event.clientX + docScrollLeft - treeOffset.left),
					dRight = (targetSetting.treeObj.width() + treeOffset.left - event.clientX - docScrollLeft),
					isTop = (dTop < setting.edit.drag.borderMax && dTop > setting.edit.drag.borderMin),
					isBottom = (dBottom < setting.edit.drag.borderMax && dBottom > setting.edit.drag.borderMin),
					isLeft = (dLeft < setting.edit.drag.borderMax && dLeft > setting.edit.drag.borderMin),
					isRight = (dRight < setting.edit.drag.borderMax && dRight > setting.edit.drag.borderMin),
					isTreeInner = dTop > setting.edit.drag.borderMin && dBottom > setting.edit.drag.borderMin && dLeft > setting.edit.drag.borderMin && dRight > setting.edit.drag.borderMin,
					isTreeTop = (isTop && targetSetting.treeObj.scrollTop() <= 0),
					isTreeBottom = (isBottom && (targetSetting.treeObj.scrollTop() + targetSetting.treeObj.height()+10) >= scrollHeight),
					isTreeLeft = (isLeft && targetSetting.treeObj.scrollLeft() <= 0),
					isTreeRight = (isRight && (targetSetting.treeObj.scrollLeft() + targetSetting.treeObj.width()+10) >= scrollWidth);

					if (event.target && tools.isChildOrSelf(event.target, targetSetting.treeId)) {
						//get node <li> dom
						var targetObj = event.target;
						while (targetObj && targetObj.tagName && !tools.eqs(targetObj.tagName, "li") && targetObj.id != targetSetting.treeId) {
							targetObj = targetObj.parentNode;
						}

						var canMove = true;
						//don't move to self or children of self
						for (i=0, l=nodes.length; i<l; i++) {
							tmpNode = nodes[i];
							if (targetObj.id === tmpNode.tId) {
								canMove = false;
								break;
							} else if ($$(tmpNode, setting).find("#" + targetObj.id).length > 0) {
								canMove = false;
								break;
							}
						}
						if (canMove && event.target && tools.isChildOrSelf(event.target, targetObj.id + consts.id.A)) {
							tmpTarget = $(targetObj);
							tmpTargetNodeId = targetObj.id;
						}
					}

					//the mouse must be in zTree
					tmpNode = nodes[0];
					if (isTreeInner && tools.isChildOrSelf(event.target, targetSetting.treeId)) {
						//judge mouse move in root of ztree
						if (!tmpTarget && (event.target.id == targetSetting.treeId || isTreeTop || isTreeBottom || isTreeLeft || isTreeRight) && (isOtherTree || (!isOtherTree && tmpNode.parentTId))) {
							tmpTarget = targetSetting.treeObj;
						}
						//auto scroll top
						if (isTop) {
							targetSetting.treeObj.scrollTop(targetSetting.treeObj.scrollTop()-10);
						} else if (isBottom)  {
							targetSetting.treeObj.scrollTop(targetSetting.treeObj.scrollTop()+10);
						}
						if (isLeft) {
							targetSetting.treeObj.scrollLeft(targetSetting.treeObj.scrollLeft()-10);
						} else if (isRight) {
							targetSetting.treeObj.scrollLeft(targetSetting.treeObj.scrollLeft()+10);
						}
						//auto scroll left
						if (tmpTarget && tmpTarget != targetSetting.treeObj && tmpTarget.offset().left < targetSetting.treeObj.offset().left) {
							targetSetting.treeObj.scrollLeft(targetSetting.treeObj.scrollLeft()+ tmpTarget.offset().left - targetSetting.treeObj.offset().left);
						}
					}

					curNode.css({
						"top": (event.clientY + docScrollTop + 3) + "px",
						"left": (event.clientX + docScrollLeft + 3) + "px"
					});

					var dX = 0;
					var dY = 0;
					if (tmpTarget && tmpTarget.attr("id")!=targetSetting.treeId) {
						var tmpTargetNode = tmpTargetNodeId == null ? null: data.getNodeCache(targetSetting, tmpTargetNodeId),
						isCopy = (event.ctrlKey && setting.edit.drag.isMove && setting.edit.drag.isCopy) || (!setting.edit.drag.isMove && setting.edit.drag.isCopy),
						isPrev = !!(preNode && tmpTargetNodeId === preNode.tId),
						isNext = !!(nextNode && tmpTargetNodeId === nextNode.tId),
						isInner = (tmpNode.parentTId && tmpNode.parentTId == tmpTargetNodeId),
						canPrev = (isCopy || !isNext) && tools.apply(targetSetting.edit.drag.prev, [targetSetting.treeId, nodes, tmpTargetNode], !!targetSetting.edit.drag.prev),
						canNext = (isCopy || !isPrev) && tools.apply(targetSetting.edit.drag.next, [targetSetting.treeId, nodes, tmpTargetNode], !!targetSetting.edit.drag.next),
						canInner = (isCopy || !isInner) && !(targetSetting.data.keep.leaf && !tmpTargetNode.isparent) && tools.apply(targetSetting.edit.drag.inner, [targetSetting.treeId, nodes, tmpTargetNode], !!targetSetting.edit.drag.inner);
						if (!canPrev && !canNext && !canInner) {
							tmpTarget = null;
							tmpTargetNodeId = "";
							moveType = consts.move.TYPE_INNER;
							tmpArrow.css({
								"display":"none"
							});
							if (window.zTreeMoveTimer) {
								clearTimeout(window.zTreeMoveTimer);
								window.zTreeMoveTargetNodeTId = null
							}
						} else {
							var tmpTargetA = $("#" + tmpTargetNodeId + consts.id.A, tmpTarget),
							tmpNextA = tmpTargetNode.isLastNode ? null : $("#" + tmpTargetNode.getNextNode().tId + consts.id.A, tmpTarget.next()),
							tmpTop = tmpTargetA.offset().top,
							tmpLeft = tmpTargetA.offset().left,
							prevPercent = canPrev ? (canInner ? 0.25 : (canNext ? 0.5 : 1) ) : -1,
							nextPercent = canNext ? (canInner ? 0.75 : (canPrev ? 0.5 : 0) ) : -1,
							dY_percent = (event.clientY + docScrollTop - tmpTop)/tmpTargetA.height();
							if ((prevPercent==1 ||dY_percent<=prevPercent && dY_percent>=-.2) && canPrev) {
								dX = 1 - tmpArrow.width();
								dY = tmpTop - tmpArrow.height()/2;
								moveType = consts.move.TYPE_PREV;
							} else if ((nextPercent==0 || dY_percent>=nextPercent && dY_percent<=1.2) && canNext) {
								dX = 1 - tmpArrow.width();
								dY = (tmpNextA == null || (tmpTargetNode.isparent && tmpTargetNode.open)) ? (tmpTop + tmpTargetA.height() - tmpArrow.height()/2) : (tmpNextA.offset().top - tmpArrow.height()/2);
								moveType = consts.move.TYPE_NEXT;
							}else {
								dX = 5 - tmpArrow.width();
								dY = tmpTop;
								moveType = consts.move.TYPE_INNER;
							}
							tmpArrow.css({
								"display":"block",
								"top": dY + "px",
								"left": (tmpLeft + dX) + "px"
							});
							tmpTargetA.addClass(consts.node.TMPTARGET_NODE + "_" + moveType);

							if (preTmpTargetNodeId != tmpTargetNodeId || preTmpMoveType != moveType) {
								startTime = (new Date()).getTime();
							}
							if (tmpTargetNode && tmpTargetNode.isparent && moveType == consts.move.TYPE_INNER) {
								var startTimer = true;
								if (window.zTreeMoveTimer && window.zTreeMoveTargetNodeTId !== tmpTargetNode.tId) {
									clearTimeout(window.zTreeMoveTimer);
									window.zTreeMoveTargetNodeTId = null;
								}else if (window.zTreeMoveTimer && window.zTreeMoveTargetNodeTId === tmpTargetNode.tId) {
									startTimer = false;
								}
								if (startTimer) {
									window.zTreeMoveTimer = setTimeout(function() {
										if (moveType != consts.move.TYPE_INNER) return;
										if (tmpTargetNode && tmpTargetNode.isparent && !tmpTargetNode.open && (new Date()).getTime() - startTime > targetSetting.edit.drag.autoOpenTime
											&& tools.apply(targetSetting.callback.beforeDragOpen, [targetSetting.treeId, tmpTargetNode], true)) {
											view.switchNode(targetSetting, tmpTargetNode);
											if (targetSetting.edit.drag.autoExpandTrigger) {
												targetSetting.treeObj.trigger(consts.event.EXPAND, [targetSetting.treeId, tmpTargetNode]);
											}
										}
									}, targetSetting.edit.drag.autoOpenTime+50);
									window.zTreeMoveTargetNodeTId = tmpTargetNode.tId;
								}
							}
						}
					} else {
						moveType = consts.move.TYPE_INNER;
						if (tmpTarget && tools.apply(targetSetting.edit.drag.inner, [targetSetting.treeId, nodes, null], !!targetSetting.edit.drag.inner)) {
							tmpTarget.addClass(consts.node.TMPTARGET_TREE);
						} else {
							tmpTarget = null;
						}
						tmpArrow.css({
							"display":"none"
						});
						if (window.zTreeMoveTimer) {
							clearTimeout(window.zTreeMoveTimer);
							window.zTreeMoveTargetNodeTId = null;
						}
					}
					preTmpTargetNodeId = tmpTargetNodeId;
					preTmpMoveType = moveType;
				}
				return false;
			}

			doc.bind("mouseup", _docMouseUp);
			function _docMouseUp(event) {
				if (window.zTreeMoveTimer) {
					clearTimeout(window.zTreeMoveTimer);
					window.zTreeMoveTargetNodeTId = null;
				}
				preTmpTargetNodeId = null;
				preTmpMoveType = null;
				doc.unbind("mousemove", _docMouseMove);
				doc.unbind("mouseup", _docMouseUp);
				doc.unbind("selectstart", _docSelect);
				body.css("cursor", "auto");
				if (tmpTarget) {
					tmpTarget.removeClass(consts.node.TMPTARGET_TREE);
					if (tmpTargetNodeId) $("#" + tmpTargetNodeId + consts.id.A, tmpTarget).removeClass(consts.node.TMPTARGET_NODE + "_" + consts.move.TYPE_PREV)
							.removeClass(consts.node.TMPTARGET_NODE + "_" + _consts.move.TYPE_NEXT).removeClass(consts.node.TMPTARGET_NODE + "_" + _consts.move.TYPE_INNER);
				}
				tools.showIfameMask(setting, false);

				roots.showHoverDom = true;
				if (root.dragFlag == 0) return;
				root.dragFlag = 0;

				var i, l, tmpNode;
				for (i=0, l=nodes.length; i<l; i++) {
					tmpNode = nodes[i];
					if (tmpNode.isparent && root.dragNodeShowBefore[tmpNode.tId] && !tmpNode.open) {
						view.expandCollapseNode(setting, tmpNode, !tmpNode.open);
						delete root.dragNodeShowBefore[tmpNode.tId];
					}
				}

				if (curNode) curNode.remove();
				if (tmpArrow) tmpArrow.remove();

				var isCopy = (event.ctrlKey && setting.edit.drag.isMove && setting.edit.drag.isCopy) || (!setting.edit.drag.isMove && setting.edit.drag.isCopy);
				if (!isCopy && tmpTarget && tmpTargetNodeId && nodes[0].parentTId && tmpTargetNodeId==nodes[0].parentTId && moveType == consts.move.TYPE_INNER) {
					tmpTarget = null;
				}
				if (tmpTarget) {
					var dragTargetNode = tmpTargetNodeId == null ? null: data.getNodeCache(targetSetting, tmpTargetNodeId);
					if (tools.apply(setting.callback.beforeDrop, [targetSetting.treeId, nodes, dragTargetNode, moveType, isCopy], true) == false) {
						view.selectNodes(sourceSetting, nodes);
						return;
					}
					var newNodes = isCopy ? tools.clone(nodes) : nodes;

					function dropCallback() {
						if (isOtherTree) {
							if (!isCopy) {
								for(var i=0, l=nodes.length; i<l; i++) {
									view.removeNode(setting, nodes[i]);
								}
							}
							if (moveType == consts.move.TYPE_INNER) {
								view.addNodes(targetSetting, dragTargetNode, newNodes);
							} else {
								view.addNodes(targetSetting, dragTargetNode.getParentNode(), newNodes);
								if (moveType == consts.move.TYPE_PREV) {
									for (i=0, l=newNodes.length; i<l; i++) {
										view.moveNode(targetSetting, dragTargetNode, newNodes[i], moveType, false);
									}
								} else {
									for (i=-1, l=newNodes.length-1; i<l; l--) {
										view.moveNode(targetSetting, dragTargetNode, newNodes[l], moveType, false);
									}
								}
							}
						} else {
							if (isCopy && moveType == consts.move.TYPE_INNER) {
								view.addNodes(targetSetting, dragTargetNode, newNodes);
							} else {
								if (isCopy) {
									view.addNodes(targetSetting, dragTargetNode.getParentNode(), newNodes);
								}
								if (moveType != consts.move.TYPE_NEXT) {
									for (i=0, l=newNodes.length; i<l; i++) {
										view.moveNode(targetSetting, dragTargetNode, newNodes[i], moveType, false);
									}
								} else {
									for (i=-1, l=newNodes.length-1; i<l; l--) {
										view.moveNode(targetSetting, dragTargetNode, newNodes[l], moveType, false);
									}
								}
							}
						}
						view.selectNodes(targetSetting, newNodes);
						$$(newNodes[0], setting).focus().blur();

						setting.treeObj.trigger(consts.event.DROP, [event, targetSetting.treeId, newNodes, dragTargetNode, moveType, isCopy]);
					}

					if (moveType == consts.move.TYPE_INNER && tools.canAsync(targetSetting, dragTargetNode)) {
						view.asyncNode(targetSetting, dragTargetNode, false, dropCallback);
					} else {
						dropCallback();
					}

				} else {
					view.selectNodes(sourceSetting, nodes);
					setting.treeObj.trigger(consts.event.DROP, [event, setting.treeId, nodes, null, null, null]);
				}
			}

			doc.bind("selectstart", _docSelect);
			function _docSelect() {
				return false;
			}

			//Avoid FireFox's Bug
			//If zTree Div CSS set 'overflow', so drag node outside of zTree, and event.target is error.
			if(eventMouseDown.preventDefault) {
				eventMouseDown.preventDefault();
			}
			return true;
		}
	},
	//method of tools for zTree
	_tools = {
		getAbs: function (obj) {
			var oRect = obj.getBoundingClientRect(),
			scrollTop = document.body.scrollTop+document.documentElement.scrollTop,
			scrollLeft = document.body.scrollLeft+document.documentElement.scrollLeft;
			return [oRect.left+scrollLeft,oRect.top+scrollTop];
		},
		inputFocus: function(inputObj) {
			if (inputObj.get(0)) {
				inputObj.focus();
				tools.setCursorPosition(inputObj.get(0), inputObj.val().length);
			}
		},
		inputSelect: function(inputObj) {
			if (inputObj.get(0)) {
				inputObj.focus();
				inputObj.select();
			}
		},
		setCursorPosition: function(obj, pos){
			if(obj.setSelectionRange) {
				obj.focus();
				obj.setSelectionRange(pos,pos);
			} else if (obj.createTextRange) {
				var range = obj.createTextRange();
				range.collapse(true);
				range.moveEnd('character', pos);
				range.moveStart('character', pos);
				range.select();
			}
		},
		showIfameMask: function(setting, showSign) {
			var root = data.getRoot(setting);
			//clear full mask
			while (root.dragMaskList.length > 0) {
				root.dragMaskList[0].remove();
				root.dragMaskList.shift();
			}
			if (showSign) {
				//show mask
				var iframeList = $$("iframe", setting);
				for (var i = 0, l = iframeList.length; i < l; i++) {
					var obj = iframeList.get(i),
					r = tools.getAbs(obj),
					dragMask = $$("<div id='zTreeMask_" + i + "' class='zTreeMask' style='top:" + r[1] + "px; left:" + r[0] + "px; width:" + obj.offsetWidth + "px; height:" + obj.offsetHeight + "px;'></div>", setting);
					dragMask.appendTo($$("body", setting));
					root.dragMaskList.push(dragMask);
				}
			}
		}
	},
	//method of operate ztree dom
	_view = {
		addEditBtn: function(setting, node) {
			if (node.editNameFlag || $$(node, consts.id.EDIT, setting).length > 0) {
				return;
			}
			if (!tools.apply(setting.edit.showRenameBtn, [setting.treeId, node], setting.edit.showRenameBtn)) {
				return;
			}
			var aObj = $$(node, consts.id.A, setting),
			editStr = "<span class='" + consts.className.BUTTON + " edit' id='" + node.tId + consts.id.EDIT + "' title='"+tools.apply(setting.edit.renameTitle, [setting.treeId, node], setting.edit.renameTitle)+"' treeNode"+consts.id.EDIT+" style='display:none;'></span>";
			aObj.append(editStr);

			$$(node, consts.id.EDIT, setting).bind('click',
				function() {
					if (!tools.uCanDo(setting) || tools.apply(setting.callback.beforeEditName, [setting.treeId, node], true) == false) return false;
					view.editNode(setting, node);
					return false;
				}
				).show();
		},
		addRemoveBtn: function(setting, node) {
			if (node.editNameFlag || $$(node, consts.id.REMOVE, setting).length > 0) {
				return;
			}
			if (!tools.apply(setting.edit.showRemoveBtn, [setting.treeId, node], setting.edit.showRemoveBtn)) {
				return;
			}
			var aObj = $$(node, consts.id.A, setting),
			removeStr = "<span class='" + consts.className.BUTTON + " remove' id='" + node.tId + consts.id.REMOVE + "' title='"+tools.apply(setting.edit.removeTitle, [setting.treeId, node], setting.edit.removeTitle)+"' treeNode"+consts.id.REMOVE+" style='display:none;'></span>";
			aObj.append(removeStr);

			$$(node, consts.id.REMOVE, setting).bind('click',
				function() {
					if (!tools.uCanDo(setting) || tools.apply(setting.callback.beforeRemove, [setting.treeId, node], true) == false) return false;
					view.removeNode(setting, node);
					setting.treeObj.trigger(consts.event.REMOVE, [setting.treeId, node]);
					return false;
				}
				).bind('mousedown',
				function(eventMouseDown) {
					return true;
				}
				).show();
		},
		addHoverDom: function(setting, node) {
			if (data.getRoots().showHoverDom) {
				node.isHover = true;
				if (setting.edit.enable) {
					view.addEditBtn(setting, node);
					view.addRemoveBtn(setting, node);
				}
				tools.apply(setting.view.addHoverDom, [setting.treeId, node]);
			}
		},
		cancelCurEditNode: function (setting, forceName, isCancel) {
			var root = data.getRoot(setting),
			nameKey = setting.data.key.name,
			node = root.curEditNode;

			if (node) {
				var inputObj = root.curEditInput,
				newName = forceName ? forceName:(isCancel ? node[nameKey]: inputObj.val());
				if (tools.apply(setting.callback.beforeRename, [setting.treeId, node, newName, isCancel], true) === false) {
					return false;
				} else {
					node[nameKey] = newName;
					setting.treeObj.trigger(consts.event.RENAME, [setting.treeId, node, isCancel]);
				}
				var aObj = $$(node, consts.id.A, setting);
				aObj.removeClass(consts.node.CURSELECTED_EDIT);
				inputObj.unbind();
				view.setNodeName(setting, node);
				node.editNameFlag = false;
				root.curEditNode = null;
				root.curEditInput = null;
				view.selectNode(setting, node, false);
			}
			root.noSelection = true;
			return true;
		},
		editNode: function(setting, node) {
			var root = data.getRoot(setting);
			view.editNodeBlur = false;
			if (data.isSelectedNode(setting, node) && root.curEditNode == node && node.editNameFlag) {
				setTimeout(function() {tools.inputFocus(root.curEditInput);}, 0);
				return;
			}
			var nameKey = setting.data.key.name;
			node.editNameFlag = true;
			view.removeTreeDom(setting, node);
			view.cancelCurEditNode(setting);
			view.selectNode(setting, node, false);
			$$(node, consts.id.SPAN, setting).html("<input type=text class='rename' id='" + node.tId + consts.id.INPUT + "' treeNode" + consts.id.INPUT + " >");
			var inputObj = $$(node, consts.id.INPUT, setting);
			inputObj.attr("value", node[nameKey]);
			if (setting.edit.editNameSelectAll) {
				tools.inputSelect(inputObj);
			} else {
				tools.inputFocus(inputObj);
			}

			inputObj.bind('blur', function(event) {
				if (!view.editNodeBlur) {
					view.cancelCurEditNode(setting);
				}
			}).bind('keydown', function(event) {
				if (event.keyCode=="13") {
					view.editNodeBlur = true;
					view.cancelCurEditNode(setting);
				} else if (event.keyCode=="27") {
					view.cancelCurEditNode(setting, null, true);
				}
			}).bind('click', function(event) {
				return false;
			}).bind('dblclick', function(event) {
				return false;
			});

			$$(node, consts.id.A, setting).addClass(consts.node.CURSELECTED_EDIT);
			root.curEditInput = inputObj;
			root.noSelection = false;
			root.curEditNode = node;
		},
		moveNode: function(setting, targetNode, node, moveType, animateFlag, isSilent) {
			var root = data.getRoot(setting),
			childKey = setting.data.key.children;
			if (targetNode == node) return;
			if (setting.data.keep.leaf && targetNode && !targetNode.isparent && moveType == consts.move.TYPE_INNER) return;
			var oldParentNode = (node.parentTId ? node.getParentNode(): root),
			targetNodeIsRoot = (targetNode === null || targetNode == root);
			if (targetNodeIsRoot && targetNode === null) targetNode = root;
			if (targetNodeIsRoot) moveType = consts.move.TYPE_INNER;
			var targetParentNode = (targetNode.parentTId ? targetNode.getParentNode() : root);

			if (moveType != consts.move.TYPE_PREV && moveType != consts.move.TYPE_NEXT) {
				moveType = consts.move.TYPE_INNER;
			}

			if (moveType == consts.move.TYPE_INNER) {
				if (targetNodeIsRoot) {
					//parentTId of root node is null
					node.parentTId = null;
				} else {
					if (!targetNode.isparent) {
						targetNode.isparent = true;
						targetNode.open = !!targetNode.open;
						view.setNodeLineIcos(setting, targetNode);
					}
					node.parentTId = targetNode.tId;
				}
			}

			//move node Dom
			var targetObj, target_ulObj;
			if (targetNodeIsRoot) {
				targetObj = setting.treeObj;
				target_ulObj = targetObj;
			} else {
				if (!isSilent && moveType == consts.move.TYPE_INNER) {
					view.expandCollapseNode(setting, targetNode, true, false);
				} else if (!isSilent) {
					view.expandCollapseNode(setting, targetNode.getParentNode(), true, false);
				}
				targetObj = $$(targetNode, setting);
				target_ulObj = $$(targetNode, consts.id.UL, setting);
				if (!!targetObj.get(0) && !target_ulObj.get(0)) {
					var ulstr = [];
					view.makeUlHtml(setting, targetNode, ulstr, '');
					targetObj.append(ulstr.join(''));
				}
				target_ulObj = $$(targetNode, consts.id.UL, setting);
			}
			var nodeDom = $$(node, setting);
			if (!nodeDom.get(0)) {
				nodeDom = view.appendNodes(setting, node.level, [node], null, false, true).join('');
			} else if (!targetObj.get(0)) {
				nodeDom.remove();
			}
			if (target_ulObj.get(0) && moveType == consts.move.TYPE_INNER) {
				target_ulObj.append(nodeDom);
			} else if (targetObj.get(0) && moveType == consts.move.TYPE_PREV) {
				targetObj.before(nodeDom);
			} else if (targetObj.get(0) && moveType == consts.move.TYPE_NEXT) {
				targetObj.after(nodeDom);
			}

			//repair the data after move
			var i,l,
			tmpSrcIndex = -1,
			tmpTargetIndex = 0,
			oldNeighbor = null,
			newNeighbor = null,
			oldLevel = node.level;
			if (node.isFirstNode) {
				tmpSrcIndex = 0;
				if (oldParentNode[childKey].length > 1 ) {
					oldNeighbor = oldParentNode[childKey][1];
					oldNeighbor.isFirstNode = true;
				}
			} else if (node.isLastNode) {
				tmpSrcIndex = oldParentNode[childKey].length -1;
				oldNeighbor = oldParentNode[childKey][tmpSrcIndex - 1];
				oldNeighbor.isLastNode = true;
			} else {
				for (i = 0, l = oldParentNode[childKey].length; i < l; i++) {
					if (oldParentNode[childKey][i].tId == node.tId) {
						tmpSrcIndex = i;
						break;
					}
				}
			}
			if (tmpSrcIndex >= 0) {
				oldParentNode[childKey].splice(tmpSrcIndex, 1);
			}
			if (moveType != consts.move.TYPE_INNER) {
				for (i = 0, l = targetParentNode[childKey].length; i < l; i++) {
					if (targetParentNode[childKey][i].tId == targetNode.tId) tmpTargetIndex = i;
				}
			}
			if (moveType == consts.move.TYPE_INNER) {
				if (!targetNode[childKey]) targetNode[childKey] = new Array();
				if (targetNode[childKey].length > 0) {
					newNeighbor = targetNode[childKey][targetNode[childKey].length - 1];
					newNeighbor.isLastNode = false;
				}
				targetNode[childKey].splice(targetNode[childKey].length, 0, node);
				node.isLastNode = true;
				node.isFirstNode = (targetNode[childKey].length == 1);
			} else if (targetNode.isFirstNode && moveType == consts.move.TYPE_PREV) {
				targetParentNode[childKey].splice(tmpTargetIndex, 0, node);
				newNeighbor = targetNode;
				newNeighbor.isFirstNode = false;
				node.parentTId = targetNode.parentTId;
				node.isFirstNode = true;
				node.isLastNode = false;

			} else if (targetNode.isLastNode && moveType == consts.move.TYPE_NEXT) {
				targetParentNode[childKey].splice(tmpTargetIndex + 1, 0, node);
				newNeighbor = targetNode;
				newNeighbor.isLastNode = false;
				node.parentTId = targetNode.parentTId;
				node.isFirstNode = false;
				node.isLastNode = true;

			} else {
				if (moveType == consts.move.TYPE_PREV) {
					targetParentNode[childKey].splice(tmpTargetIndex, 0, node);
				} else {
					targetParentNode[childKey].splice(tmpTargetIndex + 1, 0, node);
				}
				node.parentTId = targetNode.parentTId;
				node.isFirstNode = false;
				node.isLastNode = false;
			}
			data.fixPIdKeyValue(setting, node);
			data.setSonNodeLevel(setting, node.getParentNode(), node);

			//repair node what been moved
			view.setNodeLineIcos(setting, node);
			view.repairNodeLevelClass(setting, node, oldLevel)

			//repair node's old parentNode dom
			if (!setting.data.keep.parent && oldParentNode[childKey].length < 1) {
				//old parentNode has no child nodes
				oldParentNode.isparent = false;
				oldParentNode.open = false;
				var tmp_ulObj = $$(oldParentNode, consts.id.UL, setting),
				tmp_switchObj = $$(oldParentNode, consts.id.SWITCH, setting),
				tmp_icoObj = $$(oldParentNode, consts.id.ICON, setting);
				view.replaceSwitchClass(oldParentNode, tmp_switchObj, consts.folder.DOCU);
				view.replaceIcoClass(oldParentNode, tmp_icoObj, consts.folder.DOCU);
				tmp_ulObj.css("display", "none");

			} else if (oldNeighbor) {
				//old neigbor node
				view.setNodeLineIcos(setting, oldNeighbor);
			}

			//new neigbor node
			if (newNeighbor) {
				view.setNodeLineIcos(setting, newNeighbor);
			}

			//repair checkbox / radio
			if (!!setting.check && setting.check.enable && view.repairChkClass) {
				view.repairChkClass(setting, oldParentNode);
				view.repairParentChkClassWithSelf(setting, oldParentNode);
				if (oldParentNode != node.parent)
					view.repairParentChkClassWithSelf(setting, node);
			}

			//expand parents after move
			if (!isSilent) {
				view.expandCollapseParentNode(setting, node.getParentNode(), true, animateFlag);
			}
		},
		removeEditBtn: function(setting, node) {
			$$(node, consts.id.EDIT, setting).unbind().remove();
		},
		removeRemoveBtn: function(setting, node) {
			$$(node, consts.id.REMOVE, setting).unbind().remove();
		},
		removeTreeDom: function(setting, node) {
			node.isHover = false;
			view.removeEditBtn(setting, node);
			view.removeRemoveBtn(setting, node);
			tools.apply(setting.view.removeHoverDom, [setting.treeId, node]);
		},
		repairNodeLevelClass: function(setting, node, oldLevel) {
			if (oldLevel === node.level) return;
			var liObj = $$(node, setting),
			aObj = $$(node, consts.id.A, setting),
			ulObj = $$(node, consts.id.UL, setting),
			oldClass = consts.className.LEVEL + oldLevel,
			newClass = consts.className.LEVEL + node.level;
			liObj.removeClass(oldClass);
			liObj.addClass(newClass);
			aObj.removeClass(oldClass);
			aObj.addClass(newClass);
			ulObj.removeClass(oldClass);
			ulObj.addClass(newClass);
		},
		selectNodes : function(setting, nodes) {
			for (var i=0, l=nodes.length; i<l; i++) {
				view.selectNode(setting, nodes[i], i>0);
			}
		}
	},

	_z = {
		tools: _tools,
		view: _view,
		event: _event,
		data: _data
	};
	$.extend(true, $.fn.zTree.consts, _consts);
	$.extend(true, $.fn.zTree._z, _z);

	var zt = $.fn.zTree,
	tools = zt._z.tools,
	consts = zt.consts,
	view = zt._z.view,
	data = zt._z.data,
	event = zt._z.event,
	$$ = tools.$;

	data.exSetting(_setting);
	data.addInitBind(_bindEvent);
	data.addInitUnBind(_unbindEvent);
	data.addInitCache(_initCache);
	data.addInitNode(_initNode);
	data.addInitProxy(_eventProxy);
	data.addInitRoot(_initRoot);
	data.addZTreeTools(_zTreeTools);

	var _cancelPreSelectedNode = view.cancelPreSelectedNode;
	view.cancelPreSelectedNode = function (setting, node) {
		var list = data.getRoot(setting).curSelectedList;
		for (var i=0, j=list.length; i<j; i++) {
			if (!node || node === list[i]) {
				view.removeTreeDom(setting, list[i]);
				if (node) break;
			}
		}
		if (_cancelPreSelectedNode) _cancelPreSelectedNode.apply(view, arguments);
	}

	var _createNodes = view.createNodes;
	view.createNodes = function(setting, level, nodes, parentNode) {
		if (_createNodes) {
			_createNodes.apply(view, arguments);
		}
		if (!nodes) return;
		if (view.repairParentChkClassWithSelf) {
			view.repairParentChkClassWithSelf(setting, parentNode);
		}
	}

	var _makeNodeUrl = view.makeNodeUrl;
	view.makeNodeUrl = function(setting, node) {
		return setting.edit.enable ? null : (_makeNodeUrl.apply(view, arguments));
	}

	var _removeNode = view.removeNode;
	view.removeNode = function(setting, node) {
		var root = data.getRoot(setting);
		if (root.curEditNode === node) root.curEditNode = null;
		if (_removeNode) {
			_removeNode.apply(view, arguments);
		}
	}

	var _selectNode = view.selectNode;
	view.selectNode = function(setting, node, addFlag) {
		var root = data.getRoot(setting);
		if (data.isSelectedNode(setting, node) && root.curEditNode == node && node.editNameFlag) {
			return false;
		}
		if (_selectNode) _selectNode.apply(view, arguments);
		view.addHoverDom(setting, node);
		return true;
	}

	var _uCanDo = tools.uCanDo;
	tools.uCanDo = function(setting, e) {
		var root = data.getRoot(setting);
		if (e && (tools.eqs(e.type, "mouseover") || tools.eqs(e.type, "mouseout") || tools.eqs(e.type, "mousedown") || tools.eqs(e.type, "mouseup"))) {
			return true;
		}
		if (root.curEditNode) {
			view.editNodeBlur = false;
			root.curEditInput.focus();
		}
		return (!root.curEditNode) && (_uCanDo ? _uCanDo.apply(view, arguments) : true);
	};

		
/**
 * @version 0.5
 * @name jazz.tree
 * @description 表单元素的选择填报类。
 */
   $.widget("jazz.tree", $.jazz.boxComponent, {
       
        options: /** @lends jazz.tree# */ {
			
            /**
			 *@type String
			 *@desc 链接
			 *@default null
			 */      
            url: null,
            
            /**
			 *@type Object
			 *@desc 参数
			 *@default null
			 */            
            params: null,

			/**
			 *@type JSON
			 *@desc 初始化数据
			 *@default null
			 */
            data: null,
            
			/**
			 *@type Object
			 *@desc zTree的setting对象
			 *@default null
			 */              
            setting: null,
            
			/**
			 *@type Number
			 *@desc 滚动条显示高度
			 *@default 300
			 */         
            height: 300           
        },


        /** @lends jazz.comboxtree */
	
		/**
         * @desc 创建组件
		 * @private
         */
        _create: function() {
            this._super();
        	
        	this.element.addClass('jazz-panel-content jazz-widget-content').attr('vtype', 'tree');
        	var name = this.options.name;
        	if(!name){
        		name = this.options.id;
        	}
        	this.element.attr('name', 'jazz_'+name);
        	this.element.css({'overflow': 'hidden'});
        	
        	var height = this.element.height();
        	if(height<30){
        		this.element.css({height: this.options.height});
        	}
        	
            this.ulId = "tree_"+name;
            this.itemsContainer = $('<ul id="'+this.ulId+'" class="ztree" style="margin-top:0; width:auto; padding-top: 0px; padding-bottom: 0px; height:100%; overflow:auto;"></ul>').appendTo(this.element);
        	
            if(!!this.options.data) {
            	this._callback('1');
            }else if(!!this.options.url){
            	this._ajax();
            }else{
            	this._initTreeEvent();
            	$.fn.zTree.init(this.itemsContainer, this.options.setting);
            }
        },
        
		/**
         * @desc ajax请求
         */
        _ajax: function(){
            var param = {
        		url: this.options.url,
        		params: this.options.params,
        		async: true,
	        	callback: this._callback  //回调函数
            };
        	$.DataAdapter.submit(param, this);        	
        },

        _callback: function (data, sourceThis){
        	var jsonData = null;
        	var $this = null;
            if(data == '1'){
	            jsonData = this.options.data;   
	            $this = this;
            }else{
				var newdata = data["data"];
            	jsonData = newdata;
            	$this = sourceThis;
            }
    		if(!!jsonData){
    			$.fn.zTree.init($this.itemsContainer, $this.options.setting, jsonData);
    		}
        },
        
        _initTreeEvent: function(){

        },
        
		/**
         * @desc 重新加载数据
         * 		 注： 在数据初始化时也调用了这个方法 jazz.SwordAdapter.js
         *       obj.find('select').tree('loadData', data, null, 'static');  目的是为了加载SwordPageData数据
		 * 
		 * @example $('div_id').tree('loadData', url, params);
         */
        loadData: function(url, params, flag){
        	//this.reset();
        	if(!url){
        		if(this.options.url != null){
        			if(!!params){
        				this.options.params = params;
        			}
        			this.itemsContainer.children().remove();
        			this._ajax();
        		}
        	}else {
        		this.itemsContainer.children().remove();
            	if(flag == 'static'){
            		this.options.data = url;
            		this._callback(1);            		
            	}else{
            		if(!!url){
            			this.options.url = url;
            		}
            		if(!!params){
            			this.options.params = params;
            		}
	        		this._ajax();            		
            	}
        		
        	}
        	
        }
        
    });

	
})(jQuery);
/**SWFUpload */
var SWFUpload;var swfobject;if(SWFUpload==undefined){SWFUpload=function(settings){this.initSWFUpload(settings)}}SWFUpload.prototype.initSWFUpload=function(userSettings){try{this.customSettings={};this.settings={};this.eventQueue=[];this.movieName="SWFUpload_"+SWFUpload.movieCount++;this.movieElement=null;SWFUpload.instances[this.movieName]=this;this.initSettings(userSettings);this.loadSupport();if(this.swfuploadPreload()){this.loadFlash()}this.displayDebugInfo()}catch(ex){delete SWFUpload.instances[this.movieName];throw ex;}};SWFUpload.instances={};SWFUpload.movieCount=0;SWFUpload.version="2.5.0 2010-01-15 Beta 2";SWFUpload.QUEUE_ERROR={QUEUE_LIMIT_EXCEEDED:-100,FILE_EXCEEDS_SIZE_LIMIT:-110,ZERO_BYTE_FILE:-120,INVALID_FILETYPE:-130};SWFUpload.UPLOAD_ERROR={HTTP_ERROR:-200,MISSING_UPLOAD_URL:-210,IO_ERROR:-220,SECURITY_ERROR:-230,UPLOAD_LIMIT_EXCEEDED:-240,UPLOAD_FAILED:-250,SPECIFIED_FILE_ID_NOT_FOUND:-260,FILE_VALIDATION_FAILED:-270,FILE_CANCELLED:-280,UPLOAD_STOPPED:-290,RESIZE:-300,USER_DEFINED:-500};SWFUpload.FILE_STATUS={QUEUED:-1,IN_PROGRESS:-2,ERROR:-3,COMPLETE:-4,CANCELLED:-5};SWFUpload.UPLOAD_TYPE={NORMAL:-1,RESIZED:-2};SWFUpload.BUTTON_ACTION={SELECT_FILE:-100,SELECT_FILES:-110,START_UPLOAD:-120,JAVASCRIPT:-130,NONE:-130};SWFUpload.CURSOR={ARROW:-1,HAND:-2};SWFUpload.WINDOW_MODE={WINDOW:"window",TRANSPARENT:"transparent",OPAQUE:"opaque"};SWFUpload.RESIZE_ENCODING={JPEG:-1,PNG:-2};SWFUpload.completeURL=function(url){try{var path="",indexSlash=-1;if(typeof(url)!=="string"||url.match(/^https?:\/\//i)||url.match(/^\//)||url===""){return url}indexSlash=window.location.pathname.lastIndexOf("/");if(indexSlash<=0){path="/"}else{path=window.location.pathname.substr(0,indexSlash)+"/"}return path+url}catch(ex){return url}};SWFUpload.onload=function(){};SWFUpload.prototype.initSettings=function(userSettings){this.ensureDefault=function(settingName,defaultValue){var setting=userSettings[settingName];if(setting!=undefined){this.settings[settingName]=setting}else{this.settings[settingName]=defaultValue}};this.ensureDefault("upload_url","");this.ensureDefault("preserve_relative_urls",false);this.ensureDefault("file_post_name","Filedata");this.ensureDefault("post_params",{});this.ensureDefault("use_query_string",false);this.ensureDefault("requeue_on_error",false);this.ensureDefault("http_success",[]);this.ensureDefault("assume_success_timeout",0);this.ensureDefault("file_types","*.*");this.ensureDefault("file_types_description","All Files");this.ensureDefault("file_size_limit",0);this.ensureDefault("file_upload_limit",0);this.ensureDefault("file_queue_limit",0);this.ensureDefault("flash_url","swfupload.swf");this.ensureDefault("flash9_url","swfupload_fp9.swf");this.ensureDefault("prevent_swf_caching",true);this.ensureDefault("button_image_url","");this.ensureDefault("button_width",1);this.ensureDefault("button_height",1);this.ensureDefault("button_text","");this.ensureDefault("button_text_style","color: #000000;font-size: 16pt;");this.ensureDefault("button_text_top_padding",0);this.ensureDefault("button_text_left_padding",0);this.ensureDefault("button_action",SWFUpload.BUTTON_ACTION.SELECT_FILES);this.ensureDefault("button_disabled",false);this.ensureDefault("button_placeholder_id","");this.ensureDefault("button_placeholder",null);this.ensureDefault("button_cursor",SWFUpload.CURSOR.ARROW);this.ensureDefault("button_window_mode",SWFUpload.WINDOW_MODE.WINDOW);this.ensureDefault("debug",false);this.settings.debug_enabled=this.settings.debug;this.settings.return_upload_start_handler=this.returnUploadStart;this.ensureDefault("swfupload_preload_handler",null);this.ensureDefault("swfupload_load_failed_handler",null);this.ensureDefault("swfupload_loaded_handler",null);this.ensureDefault("file_dialog_start_handler",null);this.ensureDefault("file_queued_handler",null);this.ensureDefault("file_queue_error_handler",null);this.ensureDefault("file_dialog_complete_handler",null);this.ensureDefault("upload_resize_start_handler",null);this.ensureDefault("upload_start_handler",null);this.ensureDefault("upload_progress_handler",null);this.ensureDefault("upload_error_handler",null);this.ensureDefault("upload_success_handler",null);this.ensureDefault("upload_complete_handler",null);this.ensureDefault("mouse_click_handler",null);this.ensureDefault("mouse_out_handler",null);this.ensureDefault("mouse_over_handler",null);this.ensureDefault("debug_handler",this.debugMessage);this.ensureDefault("custom_settings",{});this.customSettings=this.settings.custom_settings;if(!!this.settings.prevent_swf_caching){this.settings.flash_url=this.settings.flash_url+(this.settings.flash_url.indexOf("?")<0?"?":"&")+"preventswfcaching="+new Date().getTime();this.settings.flash9_url=this.settings.flash9_url+(this.settings.flash9_url.indexOf("?")<0?"?":"&")+"preventswfcaching="+new Date().getTime()}if(!this.settings.preserve_relative_urls){this.settings.upload_url=SWFUpload.completeURL(this.settings.upload_url);this.settings.button_image_url=SWFUpload.completeURL(this.settings.button_image_url)}delete this.ensureDefault};SWFUpload.prototype.loadSupport=function(){this.support={loading:swfobject.hasFlashPlayerVersion("9.0.28"),imageResize:swfobject.hasFlashPlayerVersion("10.0.0")}};SWFUpload.prototype.loadFlash=function(){var targetElement,tempParent,wrapperType,flashHTML,els;if(!this.support.loading){this.queueEvent("swfupload_load_failed_handler",["Flash Player doesn't support SWFUpload"]);return}if(document.getElementById(this.movieName)!==null){this.support.loading=false;this.queueEvent("swfupload_load_failed_handler",["Element ID already in use"]);return}targetElement=document.getElementById(this.settings.button_placeholder_id)||this.settings.button_placeholder;if(targetElement==undefined){this.support.loading=false;this.queueEvent("swfupload_load_failed_handler",["button place holder not found"]);return}wrapperType=(targetElement.currentStyle&&targetElement.currentStyle["display"]||window.getComputedStyle)!=="block"?"span":"div";tempParent=document.createElement(wrapperType);flashHTML=this.getFlashHTML();try{tempParent.innerHTML=flashHTML}catch(ex){this.support.loading=false;this.queueEvent("swfupload_load_failed_handler",["Exception loading Flash HTML into placeholder"]);return}els=tempParent.getElementsByTagName("object");if(!els||els.length>1||els.length===0){this.support.loading=false;this.queueEvent("swfupload_load_failed_handler",["Unable to find movie after adding to DOM"]);return}else if(els.length===1){this.movieElement=els[0]}targetElement.parentNode.replaceChild(tempParent.firstChild,targetElement);if(window[this.movieName]==undefined){window[this.movieName]=this.getMovieElement()}};SWFUpload.prototype.getFlashHTML=function(flashVersion){return['<object id="',this.movieName,'" type="application/x-shockwave-flash" data="',(this.support.imageResize?this.settings.flash_url:this.settings.flash9_url),'" width="',this.settings.button_width,'" height="',this.settings.button_height,'" class="swfupload">','<param name="wmode" value="',this.settings.button_window_mode,'" />','<param name="movie" value="',(this.support.imageResize?this.settings.flash_url:this.settings.flash9_url),'" />','<param name="quality" value="high" />','<param name="allowScriptAccess" value="always" />','<param name="flashvars" value="'+this.getFlashVars()+'" />','</object>'].join("")};SWFUpload.prototype.getFlashVars=function(){var httpSuccessString,paramString;paramString=this.buildParamString();httpSuccessString=this.settings.http_success.join(",");return["movieName=",encodeURIComponent(this.movieName),"&amp;uploadURL=",encodeURIComponent(this.settings.upload_url),"&amp;useQueryString=",encodeURIComponent(this.settings.use_query_string),"&amp;requeueOnError=",encodeURIComponent(this.settings.requeue_on_error),"&amp;httpSuccess=",encodeURIComponent(httpSuccessString),"&amp;assumeSuccessTimeout=",encodeURIComponent(this.settings.assume_success_timeout),"&amp;params=",encodeURIComponent(paramString),"&amp;filePostName=",encodeURIComponent(this.settings.file_post_name),"&amp;fileTypes=",encodeURIComponent(this.settings.file_types),"&amp;fileTypesDescription=",encodeURIComponent(this.settings.file_types_description),"&amp;fileSizeLimit=",encodeURIComponent(this.settings.file_size_limit),"&amp;fileUploadLimit=",encodeURIComponent(this.settings.file_upload_limit),"&amp;fileQueueLimit=",encodeURIComponent(this.settings.file_queue_limit),"&amp;debugEnabled=",encodeURIComponent(this.settings.debug_enabled),"&amp;buttonImageURL=",encodeURIComponent(this.settings.button_image_url),"&amp;buttonWidth=",encodeURIComponent(this.settings.button_width),"&amp;buttonHeight=",encodeURIComponent(this.settings.button_height),"&amp;buttonText=",encodeURIComponent(this.settings.button_text),"&amp;buttonTextTopPadding=",encodeURIComponent(this.settings.button_text_top_padding),"&amp;buttonTextLeftPadding=",encodeURIComponent(this.settings.button_text_left_padding),"&amp;buttonTextStyle=",encodeURIComponent(this.settings.button_text_style),"&amp;buttonAction=",encodeURIComponent(this.settings.button_action),"&amp;buttonDisabled=",encodeURIComponent(this.settings.button_disabled),"&amp;buttonCursor=",encodeURIComponent(this.settings.button_cursor)].join("")};SWFUpload.prototype.getMovieElement=function(){if(this.movieElement==undefined){this.movieElement=document.getElementById(this.movieName)}if(this.movieElement===null){throw"Could not find Flash element";}return this.movieElement};SWFUpload.prototype.buildParamString=function(){var postParams,paramStringPairs=[];postParams=this.settings.post_params;if(typeof(postParams)==="object"){for(var name in postParams){if(postParams.hasOwnProperty(name)){paramStringPairs.push(encodeURIComponent(name.toString())+"="+encodeURIComponent(postParams[name].toString()))}}}return paramStringPairs.join("&amp;")};SWFUpload.prototype.destroy=function(){var movieElement;try{this.cancelUpload(null,false);movieElement=this.cleanUp();if(movieElement){try{movieElement.parentNode.removeChild(movieElement)}catch(ex){}}window[this.movieName]=null;SWFUpload.instances[this.movieName]=null;delete SWFUpload.instances[this.movieName];this.movieElement=null;this.settings=null;this.customSettings=null;this.eventQueue=null;this.movieName=null;return true}catch(ex2){return false}};SWFUpload.prototype.displayDebugInfo=function(){this.debug(["---SWFUpload Instance Info---\n","Version: ",SWFUpload.version,"\n","Movie Name: ",this.movieName,"\n","Settings:\n","\t","upload_url:               ",this.settings.upload_url,"\n","\t","flash_url:                ",this.settings.flash_url,"\n","\t","flash9_url:                ",this.settings.flash9_url,"\n","\t","use_query_string:         ",this.settings.use_query_string.toString(),"\n","\t","requeue_on_error:         ",this.settings.requeue_on_error.toString(),"\n","\t","http_success:             ",this.settings.http_success.join(", "),"\n","\t","assume_success_timeout:   ",this.settings.assume_success_timeout,"\n","\t","file_post_name:           ",this.settings.file_post_name,"\n","\t","post_params:              ",this.settings.post_params.toString(),"\n","\t","file_types:               ",this.settings.file_types,"\n","\t","file_types_description:   ",this.settings.file_types_description,"\n","\t","file_size_limit:          ",this.settings.file_size_limit,"\n","\t","file_upload_limit:        ",this.settings.file_upload_limit,"\n","\t","file_queue_limit:         ",this.settings.file_queue_limit,"\n","\t","debug:                    ",this.settings.debug.toString(),"\n","\t","prevent_swf_caching:      ",this.settings.prevent_swf_caching.toString(),"\n","\t","button_placeholder_id:    ",this.settings.button_placeholder_id.toString(),"\n","\t","button_placeholder:       ",(this.settings.button_placeholder?"Set":"Not Set"),"\n","\t","button_image_url:         ",this.settings.button_image_url.toString(),"\n","\t","button_width:             ",this.settings.button_width.toString(),"\n","\t","button_height:            ",this.settings.button_height.toString(),"\n","\t","button_text:              ",this.settings.button_text.toString(),"\n","\t","button_text_style:        ",this.settings.button_text_style.toString(),"\n","\t","button_text_top_padding:  ",this.settings.button_text_top_padding.toString(),"\n","\t","button_text_left_padding: ",this.settings.button_text_left_padding.toString(),"\n","\t","button_action:            ",this.settings.button_action.toString(),"\n","\t","button_cursor:            ",this.settings.button_cursor.toString(),"\n","\t","button_disabled:          ",this.settings.button_disabled.toString(),"\n","\t","custom_settings:          ",this.settings.custom_settings.toString(),"\n","Event Handlers:\n","\t","swfupload_preload_handler assigned:  ",(typeof this.settings.swfupload_preload_handler==="function").toString(),"\n","\t","swfupload_load_failed_handler assigned:  ",(typeof this.settings.swfupload_load_failed_handler==="function").toString(),"\n","\t","swfupload_loaded_handler assigned:  ",(typeof this.settings.swfupload_loaded_handler==="function").toString(),"\n","\t","mouse_click_handler assigned:       ",(typeof this.settings.mouse_click_handler==="function").toString(),"\n","\t","mouse_over_handler assigned:        ",(typeof this.settings.mouse_over_handler==="function").toString(),"\n","\t","mouse_out_handler assigned:         ",(typeof this.settings.mouse_out_handler==="function").toString(),"\n","\t","file_dialog_start_handler assigned: ",(typeof this.settings.file_dialog_start_handler==="function").toString(),"\n","\t","file_queued_handler assigned:       ",(typeof this.settings.file_queued_handler==="function").toString(),"\n","\t","file_queue_error_handler assigned:  ",(typeof this.settings.file_queue_error_handler==="function").toString(),"\n","\t","upload_resize_start_handler assigned:      ",(typeof this.settings.upload_resize_start_handler==="function").toString(),"\n","\t","upload_start_handler assigned:      ",(typeof this.settings.upload_start_handler==="function").toString(),"\n","\t","upload_progress_handler assigned:   ",(typeof this.settings.upload_progress_handler==="function").toString(),"\n","\t","upload_error_handler assigned:      ",(typeof this.settings.upload_error_handler==="function").toString(),"\n","\t","upload_success_handler assigned:    ",(typeof this.settings.upload_success_handler==="function").toString(),"\n","\t","upload_complete_handler assigned:   ",(typeof this.settings.upload_complete_handler==="function").toString(),"\n","\t","debug_handler assigned:             ",(typeof this.settings.debug_handler==="function").toString(),"\n","Support:\n","\t","Load:                     ",(this.support.loading?"Yes":"No"),"\n","\t","Image Resize:             ",(this.support.imageResize?"Yes":"No"),"\n"].join(""))};SWFUpload.prototype.addSetting=function(name,value,default_value){if(value==undefined){return(this.settings[name]=default_value)}else{return(this.settings[name]=value)}};SWFUpload.prototype.getSetting=function(name){if(this.settings[name]!=undefined){return this.settings[name]}return""};SWFUpload.prototype.callFlash=function(functionName,argumentArray){var movieElement,returnValue,returnString;argumentArray=argumentArray||[];movieElement=this.getMovieElement();try{if(movieElement!=undefined){returnString=movieElement.CallFunction('<invoke name="'+functionName+'" returntype="javascript">'+__flash__argumentsToXML(argumentArray,0)+'</invoke>');returnValue=eval(returnString)}else{this.debug("Can't call flash because the movie wasn't found.")}}catch(ex){this.debug("Exception calling flash function '"+functionName+"': "+ex.message)}if(returnValue!=undefined&&typeof returnValue.post==="object"){returnValue=this.unescapeFilePostParams(returnValue)}return returnValue};SWFUpload.prototype.selectFile=function(){this.callFlash("SelectFile")};SWFUpload.prototype.selectFiles=function(){this.callFlash("SelectFiles")};SWFUpload.prototype.startUpload=function(fileID){this.callFlash("StartUpload",[fileID])};SWFUpload.prototype.startResizedUpload=function(fileID,width,height,encoding,quality,allowEnlarging){this.callFlash("StartUpload",[fileID,{"width":width,"height":height,"encoding":encoding,"quality":quality,"allowEnlarging":allowEnlarging}])};SWFUpload.prototype.cancelUpload=function(fileID,triggerErrorEvent){if(triggerErrorEvent!==false){triggerErrorEvent=true}this.callFlash("CancelUpload",[fileID,triggerErrorEvent])};SWFUpload.prototype.stopUpload=function(){this.callFlash("StopUpload")};SWFUpload.prototype.requeueUpload=function(indexOrFileID){return this.callFlash("RequeueUpload",[indexOrFileID])};SWFUpload.prototype.getStats=function(){return this.callFlash("GetStats")};SWFUpload.prototype.setStats=function(statsObject){this.callFlash("SetStats",[statsObject])};SWFUpload.prototype.getFile=function(fileID){if(typeof(fileID)==="number"){return this.callFlash("GetFileByIndex",[fileID])}else{return this.callFlash("GetFile",[fileID])}};SWFUpload.prototype.getQueueFile=function(fileID){if(typeof(fileID)==="number"){return this.callFlash("GetFileByQueueIndex",[fileID])}else{return this.callFlash("GetFile",[fileID])}};SWFUpload.prototype.addFileParam=function(fileID,name,value){return this.callFlash("AddFileParam",[fileID,name,value])};SWFUpload.prototype.removeFileParam=function(fileID,name){this.callFlash("RemoveFileParam",[fileID,name])};SWFUpload.prototype.setUploadURL=function(url){this.settings.upload_url=url.toString();this.callFlash("SetUploadURL",[url])};SWFUpload.prototype.setPostParams=function(paramsObject){this.settings.post_params=paramsObject;this.callFlash("SetPostParams",[paramsObject])};SWFUpload.prototype.addPostParam=function(name,value){this.settings.post_params[name]=value;this.callFlash("SetPostParams",[this.settings.post_params])};SWFUpload.prototype.removePostParam=function(name){delete this.settings.post_params[name];this.callFlash("SetPostParams",[this.settings.post_params])};SWFUpload.prototype.setFileTypes=function(types,description){this.settings.file_types=types;this.settings.file_types_description=description;this.callFlash("SetFileTypes",[types,description])};SWFUpload.prototype.setFileSizeLimit=function(filesizelimit){this.settings.file_size_limit=filesizelimit;this.callFlash("SetFileSizeLimit",[filesizelimit])};SWFUpload.prototype.setFileUploadLimit=function(fileuploadlimit){this.settings.file_upload_limit=fileuploadlimit;this.callFlash("SetFileUploadLimit",[fileuploadlimit])};SWFUpload.prototype.setFileQueueLimit=function(fileQueueLimit){this.settings.file_queue_limit=fileQueueLimit;this.callFlash("SetFileQueueLimit",[fileQueueLimit])};SWFUpload.prototype.setFilePostName=function(filePostName){this.settings.file_post_name=filePostName;this.callFlash("SetFilePostName",[filePostName])};SWFUpload.prototype.setUseQueryString=function(useQueryString){this.settings.use_query_string=useQueryString;this.callFlash("SetUseQueryString",[useQueryString])};SWFUpload.prototype.setRequeueOnError=function(requeueOnError){this.settings.requeue_on_error=requeueOnError;this.callFlash("SetRequeueOnError",[requeueOnError])};SWFUpload.prototype.setHTTPSuccess=function(http_status_codes){if(typeof http_status_codes==="string"){http_status_codes=http_status_codes.replace(" ","").split(",")}this.settings.http_success=http_status_codes;this.callFlash("SetHTTPSuccess",[http_status_codes])};SWFUpload.prototype.setAssumeSuccessTimeout=function(timeout_seconds){this.settings.assume_success_timeout=timeout_seconds;this.callFlash("SetAssumeSuccessTimeout",[timeout_seconds])};SWFUpload.prototype.setDebugEnabled=function(debugEnabled){this.settings.debug_enabled=debugEnabled;this.callFlash("SetDebugEnabled",[debugEnabled])};SWFUpload.prototype.setButtonImageURL=function(buttonImageURL){if(buttonImageURL==undefined){buttonImageURL=""}this.settings.button_image_url=buttonImageURL;this.callFlash("SetButtonImageURL",[buttonImageURL])};SWFUpload.prototype.setButtonDimensions=function(width,height){this.settings.button_width=width;this.settings.button_height=height;var movie=this.getMovieElement();if(movie!=undefined){movie.style.width=width+"px";movie.style.height=height+"px"}this.callFlash("SetButtonDimensions",[width,height])};SWFUpload.prototype.setButtonText=function(html){this.settings.button_text=html;this.callFlash("SetButtonText",[html])};SWFUpload.prototype.setButtonTextPadding=function(left,top){this.settings.button_text_top_padding=top;this.settings.button_text_left_padding=left;this.callFlash("SetButtonTextPadding",[left,top])};SWFUpload.prototype.setButtonTextStyle=function(css){this.settings.button_text_style=css;this.callFlash("SetButtonTextStyle",[css])};SWFUpload.prototype.setButtonDisabled=function(isDisabled){this.settings.button_disabled=isDisabled;this.callFlash("SetButtonDisabled",[isDisabled])};SWFUpload.prototype.setButtonAction=function(buttonAction){this.settings.button_action=buttonAction;this.callFlash("SetButtonAction",[buttonAction])};SWFUpload.prototype.setButtonCursor=function(cursor){this.settings.button_cursor=cursor;this.callFlash("SetButtonCursor",[cursor])};SWFUpload.prototype.queueEvent=function(handlerName,argumentArray){var self=this;if(argumentArray==undefined){argumentArray=[]}else if(!(argumentArray instanceof Array)){argumentArray=[argumentArray]}if(typeof this.settings[handlerName]==="function"){this.eventQueue.push(function(){this.settings[handlerName].apply(this,argumentArray)});setTimeout(function(){self.executeNextEvent()},0)}else if(this.settings[handlerName]!==null){throw"Event handler "+handlerName+" is unknown or is not a function";}};SWFUpload.prototype.executeNextEvent=function(){var f=this.eventQueue?this.eventQueue.shift():null;if(typeof(f)==="function"){f.apply(this)}};SWFUpload.prototype.unescapeFilePostParams=function(file){var reg=/[$]([0-9a-f]{4})/i,unescapedPost={},uk,match;if(file!=undefined){for(var k in file.post){if(file.post.hasOwnProperty(k)){uk=k;while((match=reg.exec(uk))!==null){uk=uk.replace(match[0],String.fromCharCode(parseInt("0x"+match[1],16)))}unescapedPost[uk]=file.post[k]}}file.post=unescapedPost}return file};SWFUpload.prototype.swfuploadPreload=function(){var returnValue;if(typeof this.settings.swfupload_preload_handler==="function"){returnValue=this.settings.swfupload_preload_handler.call(this)}else if(this.settings.swfupload_preload_handler!=undefined){throw"upload_start_handler must be a function";}if(returnValue===undefined){returnValue=true}return!!returnValue};SWFUpload.prototype.flashReady=function(){var movieElement=this.cleanUp();if(!movieElement){this.debug("Flash called back ready but the flash movie can't be found.");return}this.queueEvent("swfupload_loaded_handler")};SWFUpload.prototype.cleanUp=function(){var movieElement=this.getMovieElement();try{if(movieElement&&typeof(movieElement.CallFunction)==="unknown"){this.debug("Removing Flash functions hooks (this should only run in IE and should prevent memory leaks)");for(var key in movieElement){try{if(typeof(movieElement[key])==="function"){movieElement[key]=null}}catch(ex){}}}}catch(ex1){}window["__flash__removeCallback"]=function(instance,name){try{if(instance){instance[name]=null}}catch(flashEx){}};return movieElement};SWFUpload.prototype.mouseClick=function(){this.queueEvent("mouse_click_handler")};SWFUpload.prototype.mouseOver=function(){this.queueEvent("mouse_over_handler")};SWFUpload.prototype.mouseOut=function(){this.queueEvent("mouse_out_handler")};SWFUpload.prototype.fileDialogStart=function(){this.queueEvent("file_dialog_start_handler")};SWFUpload.prototype.fileQueued=function(file){file=this.unescapeFilePostParams(file);this.queueEvent("file_queued_handler",file)};SWFUpload.prototype.fileQueueError=function(file,errorCode,message){file=this.unescapeFilePostParams(file);this.queueEvent("file_queue_error_handler",[file,errorCode,message])};SWFUpload.prototype.fileDialogComplete=function(numFilesSelected,numFilesQueued,numFilesInQueue){this.queueEvent("file_dialog_complete_handler",[numFilesSelected,numFilesQueued,numFilesInQueue])};SWFUpload.prototype.uploadResizeStart=function(file,resizeSettings){file=this.unescapeFilePostParams(file);this.queueEvent("upload_resize_start_handler",[file,resizeSettings.width,resizeSettings.height,resizeSettings.encoding,resizeSettings.quality])};SWFUpload.prototype.uploadStart=function(file){file=this.unescapeFilePostParams(file);this.queueEvent("return_upload_start_handler",file)};SWFUpload.prototype.returnUploadStart=function(file){var returnValue;if(typeof this.settings.upload_start_handler==="function"){file=this.unescapeFilePostParams(file);returnValue=this.settings.upload_start_handler.call(this,file)}else if(this.settings.upload_start_handler!=undefined){throw"upload_start_handler must be a function";}if(returnValue===undefined){returnValue=true}returnValue=!!returnValue;this.callFlash("ReturnUploadStart",[returnValue])};SWFUpload.prototype.uploadProgress=function(file,bytesComplete,bytesTotal){file=this.unescapeFilePostParams(file);this.queueEvent("upload_progress_handler",[file,bytesComplete,bytesTotal])};SWFUpload.prototype.uploadError=function(file,errorCode,message){file=this.unescapeFilePostParams(file);this.queueEvent("upload_error_handler",[file,errorCode,message])};SWFUpload.prototype.uploadSuccess=function(file,serverData,responseReceived){file=this.unescapeFilePostParams(file);this.queueEvent("upload_success_handler",[file,serverData,responseReceived])};SWFUpload.prototype.uploadComplete=function(file){file=this.unescapeFilePostParams(file);this.queueEvent("upload_complete_handler",file)};SWFUpload.prototype.debug=function(message){this.queueEvent("debug_handler",message)};SWFUpload.prototype.debugMessage=function(message){var exceptionMessage,exceptionValues;if(this.settings.debug){exceptionValues=[];if(typeof message==="object"&&typeof message.name==="string"&&typeof message.message==="string"){for(var key in message){if(message.hasOwnProperty(key)){exceptionValues.push(key+": "+message[key])}}exceptionMessage=exceptionValues.join("\n")||"";exceptionValues=exceptionMessage.split("\n");exceptionMessage="EXCEPTION: "+exceptionValues.join("\nEXCEPTION: ");SWFUpload.Console.writeLine(exceptionMessage)}else{SWFUpload.Console.writeLine(message)}}};SWFUpload.Console={};SWFUpload.Console.writeLine=function(message){var console,documentForm;try{console=document.getElementById("SWFUpload_Console");if(!console){documentForm=document.createElement("form");document.getElementsByTagName("body")[0].appendChild(documentForm);console=document.createElement("textarea");console.id="SWFUpload_Console";console.style.fontFamily="monospace";console.setAttribute("wrap","off");console.wrap="off";console.style.overflow="auto";console.style.width="700px";console.style.height="350px";console.style.margin="5px";documentForm.appendChild(console)}console.value+=message+"\n";console.scrollTop=console.scrollHeight-console.clientHeight}catch(ex){alert("Exception: "+ex.name+" Message: "+ex.message)}};swfobject=function(){var D="undefined",r="object",S="Shockwave Flash",W="ShockwaveFlash.ShockwaveFlash",q="application/x-shockwave-flash",R="SWFObjectExprInst",x="onreadystatechange",O=window,j=document,t=navigator,T=false,U=[h],o=[],N=[],I=[],l,Q,E,B,J=false,a=false,n,G,m=true,M=function(){var aa=typeof j.getElementById!=D&&typeof j.getElementsByTagName!=D&&typeof j.createElement!=D,ah=t.userAgent.toLowerCase(),Y=t.platform.toLowerCase(),ae=Y?/win/.test(Y):/win/.test(ah),ac=Y?/mac/.test(Y):/mac/.test(ah),af=/webkit/.test(ah)?parseFloat(ah.replace(/^.*webkit\/(\d+(\.\d+)?).*$/,"$1")):false,X=!+"\v1",ag=[0,0,0],ab=null;if(typeof t.plugins!=D&&typeof t.plugins[S]==r){ab=t.plugins[S].description;if(ab&&!(typeof t.mimeTypes!=D&&t.mimeTypes[q]&&!t.mimeTypes[q].enabledPlugin)){T=true;X=false;ab=ab.replace(/^.*\s+(\S+\s+\S+$)/,"$1");ag[0]=parseInt(ab.replace(/^(.*)\..*$/,"$1"),10);ag[1]=parseInt(ab.replace(/^.*\.(.*)\s.*$/,"$1"),10);ag[2]=/[a-zA-Z]/.test(ab)?parseInt(ab.replace(/^.*[a-zA-Z]+(.*)$/,"$1"),10):0}}else{if(typeof O.ActiveXObject!=D){try{var ad=new ActiveXObject(W);if(ad){ab=ad.GetVariable("$version");if(ab){X=true;ab=ab.split(" ")[1].split(",");ag=[parseInt(ab[0],10),parseInt(ab[1],10),parseInt(ab[2],10)]}}}catch(Z){}}}return{w3:aa,pv:ag,wk:af,ie:X,win:ae,mac:ac}}(),k=function(){if(!M.w3){return}if((typeof j.readyState!=D&&j.readyState=="complete")||(typeof j.readyState==D&&(j.getElementsByTagName("body")[0]||j.body))){f()}if(!J){if(typeof j.addEventListener!=D){j.addEventListener("DOMContentLoaded",f,false)}if(M.ie&&M.win){j.attachEvent(x,function(){if(j.readyState=="complete"){j.detachEvent(x,arguments.callee);f()}});if(O==top){(function(){if(J){return}try{j.documentElement.doScroll("left")}catch(X){setTimeout(arguments.callee,0);return}f()})()}}if(M.wk){(function(){if(J){return}if(!/loaded|complete/.test(j.readyState)){setTimeout(arguments.callee,0);return}f()})()}s(f)}}();function f(){if(J){return}try{var Z=j.getElementsByTagName("body")[0].appendChild(C("span"));Z.parentNode.removeChild(Z)}catch(aa){return}J=true;var X=U.length;for(var Y=0;Y<X;Y++){U[Y]()}}function K(X){if(J){X()}else{U[U.length]=X}}function s(Y){if(typeof O.addEventListener!=D){O.addEventListener("load",Y,false)}else{if(typeof j.addEventListener!=D){j.addEventListener("load",Y,false)}else{if(typeof O.attachEvent!=D){i(O,"onload",Y)}else{if(typeof O.onload=="function"){var X=O.onload;O.onload=function(){X();Y()}}else{O.onload=Y}}}}}function h(){if(T){V()}else{H()}}function V(){var X=j.getElementsByTagName("body")[0];var aa=C(r);aa.setAttribute("type",q);var Z=X.appendChild(aa);if(Z){var Y=0;(function(){if(typeof Z.GetVariable!=D){var ab=Z.GetVariable("$version");if(ab){ab=ab.split(" ")[1].split(",");M.pv=[parseInt(ab[0],10),parseInt(ab[1],10),parseInt(ab[2],10)]}}else{if(Y<10){Y++;setTimeout(arguments.callee,10);return}}X.removeChild(aa);Z=null;H()})()}else{H()}}function H(){var ag=o.length;if(ag>0){for(var af=0;af<ag;af++){var Y=o[af].id;var ab=o[af].callbackFn;var aa={success:false,id:Y};if(M.pv[0]>0){var ae=c(Y);if(ae){if(F(o[af].swfVersion)&&!(M.wk&&M.wk<312)){w(Y,true);if(ab){aa.success=true;aa.ref=z(Y);ab(aa)}}else{if(o[af].expressInstall&&A()){var ai={};ai.data=o[af].expressInstall;ai.width=ae.getAttribute("width")||"0";ai.height=ae.getAttribute("height")||"0";if(ae.getAttribute("class")){ai.styleclass=ae.getAttribute("class");}if(ae.getAttribute("align")){ai.align=ae.getAttribute("align")}var ah={};var X=ae.getElementsByTagName("param");var ac=X.length;for(var ad=0;ad<ac;ad++){if(X[ad].getAttribute("name").toLowerCase()!="movie"){ah[X[ad].getAttribute("name")]=X[ad].getAttribute("value")}}P(ai,ah,Y,ab)}else{p(ae);if(ab){ab(aa)}}}}}else{w(Y,true);if(ab){var Z=z(Y);if(Z&&typeof Z.SetVariable!=D){aa.success=true;aa.ref=Z}ab(aa)}}}}}function z(aa){var X=null;var Y=c(aa);if(Y&&Y.nodeName=="OBJECT"){if(typeof Y.SetVariable!=D){X=Y}else{var Z=Y.getElementsByTagName(r)[0];if(Z){X=Z}}}return X}function A(){return!a&&F("6.0.65")&&(M.win||M.mac)&&!(M.wk&&M.wk<312)}function P(aa,ab,X,Z){a=true;E=Z||null;B={success:false,id:X};var ae=c(X);if(ae){if(ae.nodeName=="OBJECT"){l=g(ae);Q=null}else{l=ae;Q=X}aa.id=R;if(typeof aa.width==D||(!/%$/.test(aa.width)&&parseInt(aa.width,10)<310)){aa.width="310"}if(typeof aa.height==D||(!/%$/.test(aa.height)&&parseInt(aa.height,10)<137)){aa.height="137"}j.title=j.title.slice(0,47)+" - Flash Player Installation";var ad=M.ie&&M.win?"ActiveX":"PlugIn",ac="MMredirectURL="+O.location.toString().replace(/&/g,"%26")+"&MMplayerType="+ad+"&MMdoctitle="+j.title;if(typeof ab.flashvars!=D){ab.flashvars+="&"+ac}else{ab.flashvars=ac}if(M.ie&&M.win&&ae.readyState!=4){var Y=C("div");X+="SWFObjectNew";Y.setAttribute("id",X);ae.parentNode.insertBefore(Y,ae);ae.style.display="none";(function(){if(ae.readyState==4){ae.parentNode.removeChild(ae)}else{setTimeout(arguments.callee,10)}})()}u(aa,ab,X)}}function p(Y){if(M.ie&&M.win&&Y.readyState!=4){var X=C("div");Y.parentNode.insertBefore(X,Y);X.parentNode.replaceChild(g(Y),X);Y.style.display="none";(function(){if(Y.readyState==4){Y.parentNode.removeChild(Y)}else{setTimeout(arguments.callee,10)}})()}else{Y.parentNode.replaceChild(g(Y),Y)}}function g(ab){var aa=C("div");if(M.win&&M.ie){aa.innerHTML=ab.innerHTML}else{var Y=ab.getElementsByTagName(r)[0];if(Y){var ad=Y.childNodes;if(ad){var X=ad.length;for(var Z=0;Z<X;Z++){if(!(ad[Z].nodeType==1&&ad[Z].nodeName=="PARAM")&&!(ad[Z].nodeType==8)){aa.appendChild(ad[Z].cloneNode(true))}}}}}return aa}function u(ai,ag,Y){var X,aa=c(Y);if(M.wk&&M.wk<312){return X}if(aa){if(typeof ai.id==D){ai.id=Y}if(M.ie&&M.win){var ah="";for(var ae in ai){if(ai[ae]!=Object.prototype[ae]){if(ae.toLowerCase()=="data"){ag.movie=ai[ae]}else{if(ae.toLowerCase()=="styleclass"){ah+=' class="'+ai[ae]+'"'}else{if(ae.toLowerCase()!="classid"){ah+=" "+ae+'="'+ai[ae]+'"'}}}}}var af="";for(var ad in ag){if(ag[ad]!=Object.prototype[ad]){af+='<param name="'+ad+'" value="'+ag[ad]+'" />'}}aa.outerHTML='<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"'+ah+">"+af+"</object>";N[N.length]=ai.id;X=c(ai.id)}else{var Z=C(r);Z.setAttribute("type",q);for(var ac in ai){if(ai[ac]!=Object.prototype[ac]){if(ac.toLowerCase()=="styleclass"){Z.setAttribute("class",ai[ac])}else{if(ac.toLowerCase()!="classid"){Z.setAttribute(ac,ai[ac])}}}}for(var ab in ag){if(ag[ab]!=Object.prototype[ab]&&ab.toLowerCase()!="movie"){e(Z,ab,ag[ab])}}aa.parentNode.replaceChild(Z,aa);X=Z}}return X}function e(Z,X,Y){var aa=C("param");aa.setAttribute("name",X);aa.setAttribute("value",Y);Z.appendChild(aa)}function y(Y){var X=c(Y);if(X&&X.nodeName=="OBJECT"){if(M.ie&&M.win){X.style.display="none";(function(){if(X.readyState==4){b(Y)}else{setTimeout(arguments.callee,10)}})()}else{X.parentNode.removeChild(X)}}}function b(Z){var Y=c(Z);if(Y){for(var X in Y){if(typeof Y[X]=="function"){Y[X]=null}}Y.parentNode.removeChild(Y)}}function c(Z){var X=null;try{X=j.getElementById(Z)}catch(Y){}return X;}function C(X){return j.createElement(X)}function i(Z,X,Y){Z.attachEvent(X,Y);I[I.length]=[Z,X,Y]}function F(Z){var Y=M.pv,X=Z.split(".");X[0]=parseInt(X[0],10);X[1]=parseInt(X[1],10)||0;X[2]=parseInt(X[2],10)||0;return(Y[0]>X[0]||(Y[0]==X[0]&&Y[1]>X[1])||(Y[0]==X[0]&&Y[1]==X[1]&&Y[2]>=X[2]))?true:false}function v(ac,Y,ad,ab){if(M.ie&&M.mac){return}var aa=j.getElementsByTagName("head")[0];if(!aa){return}var X=(ad&&typeof ad=="string")?ad:"screen";if(ab){n=null;G=null}if(!n||G!=X){var Z=C("style");Z.setAttribute("type","text/css");Z.setAttribute("media",X);n=aa.appendChild(Z);if(M.ie&&M.win&&typeof j.styleSheets!=D&&j.styleSheets.length>0){n=j.styleSheets[j.styleSheets.length-1]}G=X}if(M.ie&&M.win){if(n&&typeof n.addRule==r){n.addRule(ac,Y)}}else{if(n&&typeof j.createTextNode!=D){n.appendChild(j.createTextNode(ac+" {"+Y+"}"))}}}function w(Z,X){if(!m){return}var Y=X?"visible":"hidden";if(J&&c(Z)){c(Z).style.visibility=Y}else{v("#"+Z,"visibility:"+Y)}}function L(Y){var Z=/[\\\"<>\.;]/;var X=Z.exec(Y)!=null;return X&&typeof encodeURIComponent!=D?encodeURIComponent(Y):Y}var d=function(){if(M.ie&&M.win){window.attachEvent("onunload",function(){var ac=I.length;for(var ab=0;ab<ac;ab++){I[ab][0].detachEvent(I[ab][1],I[ab][2])}var Z=N.length;for(var aa=0;aa<Z;aa++){y(N[aa])}for(var Y in M){M[Y]=null}M=null;for(var X in swfobject){swfobject[X]=null}swfobject=null})}}();return{registerObject:function(ab,X,aa,Z){if(M.w3&&ab&&X){var Y={};Y.id=ab;Y.swfVersion=X;Y.expressInstall=aa;Y.callbackFn=Z;o[o.length]=Y;w(ab,false)}else{if(Z){Z({success:false,id:ab})}}},getObjectById:function(X){if(M.w3){return z(X)}},embedSWF:function(ab,ah,ae,ag,Y,aa,Z,ad,af,ac){var X={success:false,id:ah};if(M.w3&&!(M.wk&&M.wk<312)&&ab&&ah&&ae&&ag&&Y){w(ah,false);K(function(){ae+="";ag+="";var aj={};if(af&&typeof af===r){for(var al in af){aj[al]=af[al]}}aj.data=ab;aj.width=ae;aj.height=ag;var am={};if(ad&&typeof ad===r){for(var ak in ad){am[ak]=ad[ak]}}if(Z&&typeof Z===r){for(var ai in Z){if(typeof am.flashvars!=D){am.flashvars+="&"+ai+"="+Z[ai]}else{am.flashvars=ai+"="+Z[ai]}}}if(F(Y)){var an=u(aj,am,ah);if(aj.id==ah){w(ah,true)}X.success=true;X.ref=an}else{if(aa&&A()){aj.data=aa;P(aj,am,ah,ac);return}else{w(ah,true)}}if(ac){ac(X)}})}else{if(ac){ac(X)}}},switchOffAutoHideShow:function(){m=false},ua:M,getFlashPlayerVersion:function(){return{major:M.pv[0],minor:M.pv[1],release:M.pv[2]}},hasFlashPlayerVersion:F,createSWF:function(Z,Y,X){if(M.w3){return u(Z,Y,X)}else{return undefined}},showExpressInstall:function(Z,aa,X,Y){if(M.w3&&A()){P(Z,aa,X,Y)}},removeSWF:function(X){if(M.w3){y(X)}},createCSS:function(aa,Z,Y,X){if(M.w3){v(aa,Z,Y,X)}},addDomLoadEvent:K,addLoadEvent:s,getQueryParamValue:function(aa){var Z=j.location.search||j.location.hash;if(Z){if(/\?/.test(Z)){Z=Z.split("?")[1]}if(aa==null){return L(Z)}var Y=Z.split("&");for(var X=0;X<Y.length;X++){if(Y[X].substring(0,Y[X].indexOf("="))==aa){return L(Y[X].substring((Y[X].indexOf("=")+1)))}}}return""},expressInstallCallback:function(){if(a){var X=c(R);if(X&&l){X.parentNode.replaceChild(l,X);if(Q){w(Q,true);if(M.ie&&M.win){l.style.display="block"}}if(E){E(B)}}a=false}}}}();swfobject.addDomLoadEvent(function(){if(typeof(SWFUpload.onload)==="function"){SWFUpload.onload.call(window)}});var SWFUpload;if(typeof(SWFUpload)==="function"){SWFUpload.queue={};SWFUpload.prototype.initSettings=(function(oldInitSettings){return function(userSettings){if(typeof(oldInitSettings)==="function"){oldInitSettings.call(this,userSettings)}this.queueSettings={};this.queueSettings.queue_cancelled_flag=false;this.queueSettings.queue_upload_count=0;this.queueSettings.user_upload_complete_handler=this.settings.upload_complete_handler;this.queueSettings.user_upload_start_handler=this.settings.upload_start_handler;this.settings.upload_complete_handler=SWFUpload.queue.uploadCompleteHandler;this.settings.upload_start_handler=SWFUpload.queue.uploadStartHandler;this.settings.queue_complete_handler=userSettings.queue_complete_handler||null}})(SWFUpload.prototype.initSettings);SWFUpload.prototype.startUpload=function(fileID){this.queueSettings.queue_cancelled_flag=false;this.callFlash("StartUpload",[fileID])};SWFUpload.prototype.cancelQueue=function(){this.queueSettings.queue_cancelled_flag=true;this.stopUpload();var stats=this.getStats();while(stats.files_queued>0){this.cancelUpload();stats=this.getStats()}};SWFUpload.queue.uploadStartHandler=function(file){var returnValue;if(typeof(this.queueSettings.user_upload_start_handler)==="function"){returnValue=this.queueSettings.user_upload_start_handler.call(this,file)}returnValue=(returnValue===false)?false:true;this.queueSettings.queue_cancelled_flag=!returnValue;return returnValue};SWFUpload.queue.uploadCompleteHandler=function(file){var user_upload_complete_handler=this.queueSettings.user_upload_complete_handler;var continueUpload;if(file.filestatus===SWFUpload.FILE_STATUS.COMPLETE){this.queueSettings.queue_upload_count++}if(typeof(user_upload_complete_handler)==="function"){continueUpload=(user_upload_complete_handler.call(this,file)===false)?false:true}else if(file.filestatus===SWFUpload.FILE_STATUS.QUEUED){continueUpload=false}else{continueUpload=true}if(continueUpload){var stats=this.getStats();if(stats.files_queued>0&&this.queueSettings.queue_cancelled_flag===false){this.startUpload()}else if(this.queueSettings.queue_cancelled_flag===false){this.queueEvent("queue_complete_handler",[this.queueSettings.queue_upload_count]);this.queueSettings.queue_upload_count=0}else{this.queueSettings.queue_cancelled_flag=false;this.queueSettings.queue_upload_count=0}}}};

(function($){
/**
 * @version 0.5
 * @name jazz.Attachment
 * @description 附件上传。
 * @constructor
 * @extends jazz.boxComponent
 */
$.widget('jazz.attachment', $.jazz.field, {
	
    options: /** @lends jazz.Attachment# */ {
    	
    	vtype: "attachment",
    
		/**
		 *@type String
		 *@desc 允许上传的文件类型 "*.*" 全部类型  "*.jpg;*.png" 只允许上传jpg和png的图片
		 *@default '全部'
		 */		    	
    	allowfiletypes: '*.*',
		
    	/**
		 *@type String
		 *@desc 允许上传的文件类型的描述
		 *@default '所有文件'
		 */
    	allowfiletypesdesc: '所有文件', 
    	
    	/**
    	 * @type String
    	 * @desc 附件的描述信息
    	 * @default ''
    	 */
    	description: '',
    	
    	/**
    	 * @type String
    	 * @desc 下载附件的url地址
    	 * @default ''
    	 */    	
    	downloadurl: '',
    	
		/**
		 *@type String
		 *@desc 标题显示位置
		 *@default 'right'  left center right
		 */
		labelalign: 'right',
		
		/**
		 *@type String
		 *@desc 上传文件大小的最小值
		 *@default '50MB'
		 */
		filesizelimit: '50MB',
		
		/**
		 *@type Number
		 *@desc 上传文件最小数量
		 *@default 20
		 */	    	
		fileuploadlimit: 20,
    	
		/**
		 *@type String
		 *@desc 图片预览路径
		 *@default ''
		 */
		previewurl: '', 
		
		/**
		 *@type String
		 *@desc 点击上传附件时的标题名称
		 *@default '上传附件'
		 */  		
		title: '上传附件',
		
		/**
		 *@type Boolean
		 *@desc 是否只允许传单个文件, true 只允许传单个文件， false 同时可传多个文件
		 *@default true
		 */    	
    	single: false,
    	
		/**
		 *@type Number
		 *@desc 显示不同的样式主题
		 *@default 0
		 */    	
    	theme: 0,
    	
		/**
		 *@type String
		 *@desc 附件的上传路径
		 *@default ''
		 */    	
    	uploadurl: '',
    	
    	//event
		/**
		 *@desc 点击上传完附件的事件
		 *@param {event} 事件
		 *@param {data} 通过调用 jazz.jsonToString(data) 查看具体对象内的属性;
		 *@event
		 *@example
		 *<br/>$("XXX").attachment("option", "click", function(event, ui){  <br/>} <br/>});
		 *或:
		 *<br/>$("XXX").on("attachmentclick",function(event, ui){  <br/>} <br/>});
		 *或：
		 *function XXX(){……}
		 *<div…… click="XXX()"></div> 或 <div…… click="XXX"></div>
		 **/      	
    	click: null,    	
    	
		/**
		 *@desc 预览图片的关闭事件
		 *@param {event} 事件
		 *@param {data} 通过调用 jazz.jsonToString(data) 查看具体对象内的属性;
		 *@event
		 *@example
		 *<br/>$("XXX").attachment("option", "close", function(event, ui){  <br/>} <br/>});
		 *或:
		 *<br/>$("XXX").on("attachmentclose",function(event, ui){  <br/>} <br/>});
		 *或：
		 *function XXX(){……}
		 *<div…… close="XXX()"></div> 或 <div…… close="XXX"></div>
		 **/      	
    	close: null,
    	
		/**
		 *@desc 鼠标移入附件时的事件
		 *@param {event} 事件
		 *@param {data} 通过调用 jazz.jsonToString(data) 查看具体对象内的属性;
		 *@event
		 *@example
		 *<br/>$("XXX").attachment("option", "enter", function(event, ui){  <br/>} <br/>});
		 *或:
		 *<br/>$("XXX").on("attachmententer",function(event, ui){  <br/>} <br/>});
		 *或：
		 *function XXX(){……}
		 *<div…… enter="XXX()"></div> 或 <div…… enter="XXX"></div>
		 **/      	
    	enter: null,    	
    	
		/**
		 *@desc 鼠标移出附件时的事件
		 *@param {event} 事件
		 *@param {data} 通过调用 jazz.jsonToString(data) 查看具体对象内的属性;
		 *@event
		 *@example
		 *<br/>$("XXX").attachment("option", "leave", function(event, ui){  <br/>} <br/>});
		 *或:
		 *<br/>$("XXX").on("attachmentleave",function(event, ui){  <br/>} <br/>});
		 *或：
		 *function XXX(){……}
		 *<div…… leave="XXX()"></div> 或 <div…… leave="XXX"></div>
		 **/      	
    	leave: null,

		/**
		 *@desc 上传附件成功后的回调函数
		 *@param {event} 事件
		 *@param {data} 通过调用 jazz.jsonToString(data) 查看具体对象内的属性; 
		 *@event
		 *@example
		 *<br/>$("XXX").attachment("option", "uploadsuccess", function(event, ui){  <br/>} <br/>});
		 *或:
		 *<br/>$("XXX").on("attachmentuploadsuccess",function(event, ui){  <br/>} <br/>});
		 *或：
		 *function XXX(){……}
		 *<div…… uploadsuccess="XXX()"></div> 或 <div…… uploadsuccess="XXX"></div>
		 **/   	
    	uploadsuccess: null

	},
	
	/** @lends jazz.Attachment */
	_create: function(){
		
		this._super();
		
		this.id = this.options.name;
		if(!this.id){
			return false;
		}

		var str = '<div class="jazz-att-panel" id="'+this.id+'Panel">'
		  	    + '<div class="jazz-att-add">'
			    + '<span class="iconTrans14 jazz-att-icon"></span><a href="#" tabindex="-1">'+this.options.title+'</a>'
			    + '</div>';
		   
		    str += '<div class="jazz-att-theme" id="'+this.id+'ProgressContainer"></div>';
		   
			if(this.options.description){
			   str += '<div class="jazz-att-shuoming">'+this.options.description+'</div>';
			}
			   str += '<div class="swfContainer" style="width:80px; height: 20px;left: 5px; top: 5px; position: absolute;">' 
			   		+'<input type="hidden" name="uploadSession" value="'+jazz.getRandom()+jazz.getRandom()+'" />'
			   		+'<input type="hidden" name="filesizelimit" value="'+this.options.filesizelimit+'" />'
			   		+'<input type="hidden" name="fileuploadlimit" value="'+this.options.fileuploadlimit+'" />'
			   		+'<input type="hidden" name="allowfiletypes" value="'+this.options.allowfiletypes+'" />'
			   		+'<input type="hidden" name="allowfiletypesdesc" value="'+this.options.allowfiletypesdesc+'" />'
			   		+'<input type="hidden" name="uploadedfilescount" value="0"/>'
			   		+'<input type="hidden" name="disableButtonId" value="btnSave" />'					   		
			   		+'<span id="'+this.id+'SwfHolder"></span>'
			   +'</div>'
  		       //+'<span class="tips">提示：您最多可以添加this.fileCount个附件。</span>'
	   +'</div>';

	   this.parent.addClass("jazz-att-no-border");
	   this.inputFrame.addClass('jazz-att-panel');
	   this.inputFrame.append(str);
	  
	   this._loadSwfupload();
	},
	
	/**
	 * @desc 初始化
	 * @private
	 */
	_init: function(){
		this._super();
	},
	
	/**
	 * @desc 获取上传配置内容
	 * @private
	 */	
	_getConfig: function(){
		var id = this.id;
		if (null == id || "" == id){
			throw "加载文件上传控件错误！控件id参数不能为空！";
		}
		var config = {id: id};
		var elements = this.inputFrame.find('input[type=hidden]');
		$.each(elements, function(i, item){
			config[item.name] = item.value;  
		});
		//业务主键id
		config.masterId = config.masterId || '';
		//允许上传的文件大小，默认为50M
		config.filesizelimit = config.filesizelimit || '50 MB';
		//允许上传的文件个数
		config.fileuploadlimit = parseInt(config.fileuploadlimit || 20);
		//允许上传的文件类型
		config.allowfiletypes = config.allowfiletypes || '*.*;';
		//上传的文件类型描述
		config.allowfiletypesdesc = config.allowfiletypesdesc || '所有文件;';
		//初始已上传的文件数量
		config.uploadedfilescount = parseInt(config.uploadedfilescount || 0);
		return config;
	},
		
	/**
	 * @desc 加载swfupload组件
	 * @private
	 */
	_loadSwfupload: function(){
		SWFUpload.onload = this._swf_upload();
	},
	
	_swf_upload: function(){
		var config = this._getConfig();
		this._swfInit(config);
	},

	/**
	 * @desc 初始化组件
	 * @param {config} 初始化配置项
	 * @private
	 */	
    _swfInit: function(config){
    	var $this = this;
  		var DEFAULT_UPLOAD_URL = jazz.config.contextpath + ($this.options.uploadurl || jazz.config.default_upload_url),
  			DEFAULT_FLASH_URL = jazz.config.contextpath + jazz.config.default_flash_url,
  			DEFAULT_FLASH9_URL = jazz.config.contextpath + jazz.config.default_flash9_url;

	  		var single = this.options.single, uploadSuccessHandler = uploadSuccess;

			this.swfInstance = new SWFUpload({
					upload_url: $.trim(DEFAULT_UPLOAD_URL),
					post_params: {
						type: 'upload',
						uploadSession: config.uploadSession
					},
					file_size_limit: config.filesizelimit,
					file_types : config.allowfileTypes,
					file_types_description : config.allowfiletypesdesc,
					file_upload_limit : config.fileuploadlimit,
					
					button_action: single?SWFUpload.BUTTON_ACTION.SELECT_FILE:SWFUpload.BUTTON_ACTION.SELECT_FILES,
					
					//Event handlers
					swfupload_loaded_handler: loaded,
					swfupload_preload_handler: preLoad,
					swfupload_load_failed_handler: loadFailed,
					file_queued_handler: fileQueued,
					file_queue_error_handler : fileQueueError,
					file_dialog_complete_handler : fileDialogComplete,
					upload_start_handler : uploadStart,
					upload_progress_handler : uploadProgress,
					upload_error_handler : uploadError,
					upload_success_handler : uploadSuccessHandler,
					upload_complete_handler : uploadComplete,
					//设置flash控件
					button_placeholder_id : $this.id + "SwfHolder",
					button_width: 70,
					button_height: 20,
					button_text_top_padding: 0,
					button_text_left_padding: 0,
					button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
					button_cursor: SWFUpload.CURSOR.HAND,
					flash_url :DEFAULT_FLASH_URL,
					flash9_url:DEFAULT_FLASH9_URL,
					custom_settings:{
						progressTarget: $this.id+"ProgressContainer",
						uploadedfilescount: config.uploadedfilescount,
						disableButtonId: config.disableButtonId,
						type: config.type
	  				},
					debug: false
			});
			this._onLoad(this.swfInstance);
			
			
			function preLoad() {
				if (!this.support.loading) {
					alert("加载附件上传控件失败,请安装FLASH9 或以上系统环境！");
					return false;
				}
			}

			function loaded() {
				var fileUploadLimt = this.settings.file_upload_limit;
			    var uploadedfilescount = this.customSettings.uploadedfilescount;
				if (fileUploadLimt > 0) {   // && uploadedFilesCount > 0
					var stats = this.getStats();
						stats.successful_uploads = uploadedfilescount; 
						this.setStats(stats);
				}
			}

			function loadFailed() {
				alert("加载附件上传控件失败,请安装FLASH9 或以上系统环境！");
			}

			function fileQueued(file) {      
				try {
					var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings);
					//是否允许取消任务
					progress.toggleCancel(true, this);
				} catch (ex) {
					this.debug(ex);
				}
			}

			function fileQueueError(file, errorCode, message) {
				try {
					if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
						alert('您最多可以添加'+this.settings.file_upload_limit+'个附件！');
						return;
					}
					switch (errorCode) {
					case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
						alert('您所选择的文件超过了'+this.settings.file_size_limit+'，请重新选择上传的文件。');
						break;
					case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
						//alert('系统不允许上传0字节的文件！'+file.name);
						break;
					case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
						alert('您所选择的文件类型非法，请重新选择上传的文件。');
						break;
					default:
						if (file !== null) {
							alert('您所选择的文件有误，请尝试重新选择上传的文件。');
						}
						break;
					}
				} catch (ex) {
			        this.debug(ex);
			    }
			}

			function fileDialogComplete(numFilesSelected, numFilesQueued) {
				try {
					if (numFilesSelected == 0) {
						return false;
					}
					this.startUpload();
				} catch (ex)  {
			        this.debug(ex);
				}
			}

			function uploadStart(file) {
				try {
					var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings);
					progress.toggleCancel(true, this);
				}
				catch (ex) {
					this.debug(ex);
				}
				return true;
			}

			function uploadProgress(file, bytesLoaded, bytesTotal) {
				try {
					//根据控件不同类型，设置不同进度显示
					var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
					var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings);
					progress.setProgress(percent);
					
				} catch (ex) {
					this.debug(ex);
				}
			}

			function uploadSuccess(file, serverData) {
				try {
					var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings);
					var jsonData = jazz.stringToJson(serverData);   

					if (jsonData.success===true){
						progress.setComplete(jsonData);
						progress.toggleCancel(false, this);
						
						//当options.theme==1时，调用朱杨的数据，回显图片
						if($this.options.theme == 1){
							serverData = jazz.stringToJson(serverData);
							var _attachId = serverData["id"];
							var _fileId = file["id"];
							if(_attachId){
								var _url = ($this.options.previewurl || jazz.config.previewurl)+"?fileId="+_attachId;
								//回显图片
								var obj = $("#"+$this.id+"ProgressContainer").find("#"+_fileId+"_img");
									obj.children().remove();
								var imgType = file["type"];
								if(imgType == ".doc" || imgType == ".docx"){
									obj.addClass("jazz-att-theme1-size").addClass("jazz-att-word");
								}else if(imgType == ".pdf"){
									obj.addClass("jazz-att-theme1-size").addClass("jazz-att-pdf");
								}
								//else if(){
							    //}
							    else{
									obj.append('<img src="'+_url+'" class="jazz-att-theme1-size" />');									
								}
								//添加关闭图片
								var $close = $('<div class="jazz-att-theme1-close"></div>').appendTo(obj);
								var data = $.extend(file, serverData, {previewurl: _url});
								obj.on("mouseenter", function(e){
									$close.css("display", "block");
									$this._event("enter", e, data);
								}).on("mouseleave", function(e){
									$close.css("display", "none");
									$this._event("leave", e, data);
								}).on("mousedown", function(e){
									  var target = e.target, $target = $(target);
									  if($target.is('.jazz-att-theme1-close')){
										  $this._event("close", e, data);
									  }else{
										  $this._event("click", e, data);									  
									  }
								});
								
							}
						}
						
						$this._event("uploadsuccess", null, serverData);						
					}else{
						this.uploadError(file, SWFUpload.UPLOAD_ERROR.USER_DEFINED, jsonData.msg);
					}
				} catch (ex) {
					this.debug(ex);
				}
			}

			function uploadError(file, errorCode, message) {

				try {	
					var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings);
					progress.setError();
					progress.toggleCancel(false, this);
			    
					switch (errorCode) {
					case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
						progress.setStatus("上传失败: " + message);
						break;
					case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
						progress.setStatus("上传失败.");
						break;
					case SWFUpload.UPLOAD_ERROR.IO_ERROR:
						progress.setStatus("服务器发生错误，上传失败.");
						break;
					case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
						progress.setStatus("上传失败.");
						break;
					case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
						progress.setStatus("上传超过限制.");
						break;
					case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
						progress.setStatus("上传失败.");
						break;
					case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
						progress.setStatus("被取消.");
						progress.setCancelled(this);
						break;
					case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
						progress.setStatus("被停止.");
						break;
					case SWFUpload.UPLOAD_ERROR.USER_DEFINED:
						progress.setStatus(message);
						break;
					default:
						progress.setStatus("上传文件失败: " + errorCode);
						break;
					}
				} catch (ex) {
			        this.debug(ex);
			    }
			}

			function uploadComplete(file) {
				if (this.getStats().files_queued === 0) {
					var progress = new FileProgress(file, this.customSettings.progressTarget, this.customSettings);
					progress.toggleCancel(false, this);
				}
			}

			function FileProgress(file, contentTargetId, customSettings) {
				var cache = FileProgress.instances[file.id];

				if (cache){
					return cache;
				}
				this.file = file;

				var contentTargetEl = $('#'+contentTargetId);

				if (contentTargetEl){
					var _none = '', _theme = $this.options.theme;
					if(_theme == 1){
						_none = 'style="display: none;"';
					}
					
					var str = '<div class="jazz-att-list-p" id="'+file.id+'" _fileindex="'+file.index+'">'
							+'<span class="jazz-att-normal" '+_none+'>'
							+'<cite><a tabindex="-1"  href="#" _act="download_file">'+file.name+'</a><em style="margin-left:5px;">('+this.countFileDisplaySize(file.size)+')</em></cite>'
							+'<input type="hidden" id="hiddenFileId" value="'+file.hiddenFileId+'"/>';
							if(_theme != 1){
								str += '<b class="jazz-att-progress"><i id="att_prog_'+file.id+'"></i></b>';
							}
							str += '<a tabindex="-1" href="#" _act="att_remove_swf">删除</a>'
							    +'<em class="status" style="color: red; margin-left: 6px;display: none;">上传失败.</em>'
						    +'</span>';
							if(_theme == 1){
								str+='<div id="'+file.id+'_img" class="jazz-att-theme1-imgbg jazz-att-theme1-size"><b class="jazz-att-progress jazz-att-progress-t1"><i id="att_prog_'+file.id+'" class="jazz-att-progress-i"></i></b></div>';
							}						    
						str+='</div>';
						
					contentTargetEl.append(str);
				}
				FileProgress.instances[file.id] = this;
			};

			FileProgress.ONE_KB = 1024;

			FileProgress.instances = {};

			FileProgress.ONE_MB = FileProgress.ONE_KB * FileProgress.ONE_KB;

			FileProgress.ONE_GB = FileProgress.ONE_KB * FileProgress.ONE_MB;

			/**
			 *格式化文件字节数
			 *@param value 字节数
			 *@return 返回文件字节数 
			 */
			FileProgress.prototype.countFileDisplaySize = function(size) {
					if (typeof size == "string"){
						return size;
					}
					var displaySize;
					if (Math.floor(size / FileProgress.ONE_GB) > 0) {
						displaySize = this.format(Math.floor(size * 100.0 / FileProgress.ONE_GB) / 100,2)+ "G";
					} else if (Math.floor(size / FileProgress.ONE_MB) > 0) {
						displaySize = this.format(Math.floor(size * 100.0 / FileProgress.ONE_MB) / 100,2)+ "M";
					} else if (Math.floor(size / FileProgress.ONE_KB) > 0) {
						displaySize = this.format(Math.floor(size * 100.0 / FileProgress.ONE_KB) / 100,2) + "K";
					} else {
						displaySize = size + "Bytes";
					}
					return displaySize;
			};
			/**
			 *格式化文件字节数
			 *@param value 字节数
			 *@param decimalLength 小数位数
			 *@return 返回格式化后文本 
			 */
			FileProgress.prototype.format = function(value,decimalLength) {
					value = Math.round(value * Math.pow(10, decimalLength));
					value = value / Math.pow(10, decimalLength);
					var v = value.toString().split(".");
					if (v[0]==""){
						v[0]="0";
					}
					if (v.length > 1) {
						var len = v[1].length;
						if (len > decimalLength) {
							v[1] = v[1].substring(0, decimalLength);
						}
						return v[0] + "." + v[1];
					}	
					return v[0];
			};

			/**
			 *更新进度
			 */
			FileProgress.prototype.setProgress = function (percentage) {
					var fileEl = $('#'+this.file.id);
					if (fileEl){
						fileEl.find('#att_prog_'+this.file.id).width(percentage + "%");
					}
			};

			FileProgress.prototype.setComplete = function (result) {
					var el = $('#'+this.file.id);
					if (el){
						   setTimeout(function(){
							   el.find(".jazz-att-progress").hide();						   
						   }, 800);
						   //根据服务器返回的fileid, 更新当前progress状态
						   el.find("input[id=hiddenFileId]").val(result.id);
					}
			};
			FileProgress.prototype.setError = function () {
					var el = $('#'+this.file.id);
					if (el){
						el.find(".jazz-att-progress-i").css({background: "red", width: "100%"});
						
					}
			};
			FileProgress.prototype.setCancelled = function (swfuploadInstance) {
			};
			FileProgress.prototype.setStatus = function (status) {
					var el = $('#'+this.file.id);
					if (el){
						el.find(".status").get(0).innerHTML=status;
					}
			};

			FileProgress.prototype.toggleCancel = function (show, swfuploadInstance) {
					var buttonId = swfuploadInstance.customSettings.disableButtonId;
					var fileEl = $('#'+this.file.id);
					if (fileEl){
						if (! buttonId){
							var btnEl = $('#'+buttonId).get(0);
							if (!btnEl){
								btnEl.disabled = show;
							}
						}
					}else{
						if (!buttonId){
							var btnEl = $('#'+buttonId).get(0);
							if (btnEl){
								btnEl.disabled = false;
							}
						}
					}
			};			
			
	  },
	  
	/**
	 * @desc 加载
	 * @param {swfInstance} 加载控件
	 * @private
	 */
	  _onLoad: function(swfInstance){
		  	var $this = this;
		  	if (swfInstance){
		  		$this._swfEvent();
		  		return;
		  	}
		  	throw '加载flash控件出错！';
	  },
	  
	/**
	 * @desc 加载swf事件
	 * @private
	 */		  
	  _swfEvent: function() {
		  	var $this = this;
		  	//注册删除事件
			this.inputFrame.on('mousedown.attachment', function(event) {
				  var target = event.target, $target = $(target);
				  
				  if($target.is('a')){
					  var action = $target.attr("_act");
					  var fileObj = $target.parent().parent();
					  if ("att_remove_swf" === action){
						    var file = $this.getFile(fileObj);
							setTimeout(function(){
								 fileObj.remove();
				            }, 300);
							$this.deleteFile(file);
							return;
					  
					  }else if ("download_file" === action){
					  	    var file = $this.getFile(fileObj);
							if (!!file){
								var _url = jazz.config.contextpath + ($this.options.downloadurl || jazz.config.default_download_url) + '?name='+encodeURI(file.fileName)+'&fileId='+file.hiddenFileId; 
								
								//模拟form表单提交
								var form=$("<form>"); //定义一个form表单
								form.attr("style", "display:none");
								form.attr("target", "");
								form.attr("method", "post");
								form.attr("action", _url);

								$("body").append(form); //将表单放置在web中

								form.submit(); //表单提交						
							}
							return;
					  }
				  }
			});
	  },
	  
	/**
	 * @desc 获取上传控件对应的jquery对象
	 * @private
	 */		  
	  getControl:function(){
		  return $('div[name="'+this.id+'"]');
	  },
	  
	/**
	 * @desc 删除文件
	 * @private
	 */		  
	  deleteFile: function(file){
	  		var swf = this.swfInstance,	
	  			fileIndex = file.index, fileId = file.hiddenFileId;
	  		if (!swf){
	  			return;
			}
			if (!!fileIndex){
				var cacheFile = swf.getFile(parseInt(fileIndex));
				if (cacheFile){//更新SWF组件状态
					if (SWFUpload.FILE_STATUS.QUEUED===cacheFile.filestatus || SWFUpload.FILE_STATUS.IN_PROGRESS===cacheFile.filestatus ){
						swf.cancelUpload(cacheFile.id,false);
						var progress = new FileProgress(cacheFile, swf.customSettings.progressTarget);
						progress.toggleCancel(false, swf);
						return ;
					}else if (SWFUpload.FILE_STATUS.ERROR===cacheFile.filestatus || SWFUpload.FILE_STATUS.CANCELLED===cacheFile.filestatus){
						return ;
					}
				}
			}
			
//			//从服务器端删除文件
//			if (!!fileId) {
//	            var params = {
//	        		url: jazz.config.contextpath + jazz.config.default_upload_url,
//	        		params: {
//						type: 'delete',
//						id: fileId
//						//callback: this._callback  //回调函数
//						//_callback: function (data, sourceThis){
//					},
//	        		async: true
//	            };
//	        	$.DataAdapter.submit(params, this);			
//			}
			
			//更新SWF状态
			var stats = swf.getStats();
			stats.successful_uploads--;
			swf.setStats(stats);
	  },

	/**
	 * @desc 获取文件
	 * @private
	 */		  
	  getFile: function(fileEl){
			 var hiddenFileId = fileEl.find('input[id="hiddenFileId"]').val();
			 var fileName = fileEl.find('a[_act="download_file"]').text();
			 return {
	 			id: fileEl.attr('id'),
			 	index: fileEl.attr("_fileIndex"),
			 	hiddenFileId: hiddenFileId,
			 	fileName: fileName
			 };
	  },
	  
	/**
	 * @desc 返回所有文件
	 * @private
	 */		  
	  getFiles: function(){
		  var $this = this;
	  	  var fileContainer = this.getControl(), files=[],
  		  fileEls = fileContainer.find('div.jazz-att-list-p');
	  	  $.each(fileEls, function(index, item){
	  		  var file = $this.getFile($(item)), fileId = file.hiddenFileId;
	  		  if (!!fileId){
	  			 files.push(file);
	  		  };
	  	  });
		  return files;
	  },
	  
	/**
	 * @desc 返回所有成功上传的文件id
	 * @private
	 */		  
	  getFileIds: function(){
		var $this = this;
	  	var fileContainer = this.getControl(), fileIds=[],
	  		fileEls = fileContainer.find('div.jazz-att-list-p');
		  	$.each(fileEls, function(index, item){
		  		var file = $this.getFile($(item)), fileId = file.hiddenFileId;
		  		if (!!fileId){
		  			fileIds.push(fileId);
		  		};
		  	});
	  	return fileIds.join("##");
	  },
	  
	  /**
	   * @desc 获取上传附件的结果集
	   * @return [{name: '', fileId: ''},{}]
	   */
	  getValue: function(){
		  var files = this.getFiles();
		  var attach = [];
		  if(files){
			  for(var i=0, len=files.length; i<len; i++){
				  var obj = {}, f = files[i];
				  obj['name'] = f.fileName;
				  obj['fileId'] = f.hiddenFileId;
				  attach.push(obj);
			  }
		  }
		  return jazz.jsonToString(attach);
	  },
	  
	  /**
	   * @desc 获取上传附件的结果集
	   * @return {}
	   */
	  getText: function(){
		  var files = this.getFiles();
		  var attach = [];
		  if(files){
			  for(var i=0, len=files.length; i<len; i++){
				  var obj = {}, f = files[i];
				  attach.push(f.fileName);
			  }
		  }
		  return attach.join(",");
	  },
	  
	  /**
	   * @desc 取消当前选中状态对象
	   * @example $('XXX').attachment('reset');
	  */
	  reset: function() {

	  },	  
	  
	  /**
	   * @desc 设置附件的回显
	   * @param {data} 附件需要回显的数据集
	   */
	  setValue: function(data){
		    if(!data) return false; 
		    
	 		var $this = this, swf = this.swfInstance;
	 		if (!swf){
				return;
			}
			var contentTargetEl = $('#'+this.id + 'ProgressContainer');
			
			if(typeof(data) != 'object'){
				data = jazz.stringToJson(data);
			}
			
			for(var i=0, len=data.length; i<len; i++){
				this._backShowAttach(swf, contentTargetEl, data[i]);
			}
			//$this.masterId = masterId;
			swf.setPostParams({
				//masterId: $this.masterId,
				uploadSession: $this.uploadSession
	 		});
	  },

	  /**
	   * @desc 取消当前选中状态对象
	   * @example $('XXX').attachment('toggleLabel');
	  */
	  toggleLabel: function() {

	  },

//	  /**
//	   * @desc 获取上传附件的结果集
//	   * @param {masterId}  附件关联主键ID
//	   */
//	  associate: function(masterId){
//	 		var $this = this, swf = this.swfInstance;
//	 			//fileIds = this.getFileIds();
//	 		if (!swf){
//				return;
//			}
//			if (!!masterId){
//			    var params = {
//		    		url: jazz.config.contextpath + jazz.config.default_upload_url,
//		    		params: {
//						type: 'associate',
//						uploadSession: $this.uploadSession,
//						//ids: fileIds,
//						masterId: masterId
//					},
//		    		async: false,
//		    		callback: this._backShowAttach
//			    };
//		    	$.DataAdapter.submit(params, this);	
//			}
//			$this.masterId = masterId;
//			swf.setPostParams({
//				masterId: $this.masterId,
//				uploadSession: $this.uploadSession
//	 		});
//	  },
	  
		/**
		 * @desc 回显附件
		 * @private
		 */		  
	  _backShowAttach: function(swf, contentTargetEl, file){
			contentTargetEl.append(
				'<p class="jazz-att-list-p" id="'+file.hiddenFileId+'" _fileindex="'+file.index+'">'
					+'<span class="jazz-att-normal">'
						+'<cite><a tabindex="-1" href="#" _act="download_file">'+file.name+'</a><em style="margin-left:5px;">('+file.size+')</em></cite>'
						+'<input type="hidden" id="hiddenFileId" value="'+file.fileId+'"/>'
						+'<b><i id="att_prog_'+file.fileId+'"></i></b>'
						+'<a tabindex="-1"  href="#" _act="att_remove_swf">删除</a>'
						+'<em class="status" style="color: red; margin-left: 6px;display: none;">上传失败.</em>'
					+'</span>'
				+'</p>'
			);
			
			//更新SWF状态
//			var stats = swf.getStats();
//			stats.successful_uploads++;
//			swf.setStats(stats);			
	  }
});


})(jQuery);/**
 * @version 1.0
 * @description 公共UI组件对象的函数接口
 */

jazz.namespace("widget");

jazz.widget = function(options, parent){
	if(typeof(options) == "object"){
		var vtype = options["vtype"];
		var name = options["name"];
		if(!vtype || !name){
			alert("name 或 vtype 未定义！"); return false;
		}else{
			var obj = $("<div name='"+name+"' vtype='"+vtype+"'></div>");
					
		    if(!!parent){
				obj.appendTo(parent);
			}else{
				obj.appendTo("body");
			}
		    obj.parseComponent(options);
		    
			var _type = jQuery.trim(vtype);
		    //对于$()和widget初始化的组件，若是外层组件使用了transformOptions方式，
		    //则需要将外层组件的options的items删除掉，即可以直接返回obj,不再继续渲染items
			if(_type== "gridpanel"){
			    return obj;
			}
		    
			if(_type== "window" || _type== "panel" || _type== "formpanel"){// || _type== "gridpanel"
				if(_type!= "formpanel" && !options["frameurl"]){
					_create$Child(options, obj.children(".jazz-panel-content"));
				}
			}else{
				if(_type !="toolbar"){
					_create$Child(options, obj);
				}
			}
			return obj;
		}
	}
};

var _create$Child = function(options, parent){
    if(options["items"]==undefined){
		return false;
	}
    else{
    	var items = options["items"];
    	if(jQuery.isArray(items)){
			for(var i=0, len=items.length; i<len; i++){
				var item = items[i];
				var vtype = item["vtype"];
				var name = item["name"];
				if(vtype && name){
					var obj = $("<div name='"+name+"' vtype='"+vtype+"'></div>");
					obj.appendTo(parent);
					
					obj.parseComponent(item);
					
					//判断item对象中是否还存在items属性
					if(item["items"]){
						//判断vtype类型
						if(vtype=="panel" || vtype=="window" || vtype== "formpanel" || vtype== "gridpanel"){
							if(_type!= "formpanel" && !item["frameurl"]){
								obj = obj.children(".jazz-panel-content");
							}
						}
						if(vtype !="toolbar"){
							_create$Child(item, obj);
						}
					}
				}else{
					alert("解析items子项中，name或vtype未定义！");
				}
			}
    	}else{
    		alert("解析items格式错误，请按[{},{}]格式输写！");
    	}
    }
};(function($) {
	/**
	 * @version 0.5
	 * @name jazz.boxlist
	 * @description 表单元素的选择填报类。
	 * @constructor
	 * @extends jazz.field
	 * @requires
	 * @example $('input_id').boxlist();
	 */
    $.widget("jazz.boxlist", $.jazz.field, {
       
        options: /** @lends jazz.boxlist# */ {
        	
        	/**
			 *@type String
			 *@desc 组件类型
			 *@default 'boxlist'
			 */	
        	vtype: 'boxlist',

        	/**
			 *@type Number
			 *@desc 组件的宽度
			 *@default 130
			 */        	
        	width:150,
        	
        	/**
			 *@type Number
			 *@desc 组件的高度
			 *@default 180
			 */        	
        	height:180,
       
            /**
			 *@type String或Json
			 *@desc 数据链接或者初始化数据 {"data":[{"text":"11","val":"22"},{"text":"11","val":"22"}]}
			 *@default null
			 */      
            dataurl: null,

            /**
			 *@type Object
			 *@desc 参数
			 *@default null
			 */            
            params: null,            
            
            
			/**
			 *@type Boolean
			 *@desc 选择多条记录  true多选  false单选
			 *@default true
			 */            
            multiple: false,
            
             
			/**
			 *@desc 用户自定义选项行结构
			 *@param {event}  事件
			 *@param {data}  选项数据	
			 *@return 用户自定义li内部div结构  如：'<img src="box1.png"/>'
			 *@event
			 *@example
			 *<br/>$("#input_id").boxlist({"option","datarender"，function( data ){  <br/>} <br/>});
			 */		
            datarender: null,
            
             
			/**
			 *@desc 点击事件
			 *@param {event} 事件
			 *@param {ui} 	选中项数据	 
			 *@event
			 *@example
			 *<br/>$("#input_id").boxlist({"option","click"，function( event, ui ){  <br/>} <br/>});
			 */		
            click: null,
            
             
			/**
			 *@desc 改变事件
			 *@param {event} 事件
			 *@param {ui.newObject} 	当前选中项数据
		     *@param {ui.oldObject}  之前选中项数据		 	
			 *@event
			 *@example
			 *<br/>$("#input_id").boxlist({"option","change"，function( event, ui ){  <br/>} <br/>});
			 */		
            change:null,
             
			/**
			 *@desc 取消事件
			 *@param {event} 事件
			 *@param {ui} 	取消项数据		 
			 *@event
			 *@example
			 *<br/>$("#input_id").boxlist({"option","unclick"，function( event, ui ){  <br/>} <br/>});
			 */		
            unclick: null,
              
			/**
			 *@desc 双击事件
			 *@param {event} 事件
			 *@param {ui} 	选中项数据	 
			 *@event
			 *@example
			 *<br/>$("#input_id").boxlist({"option", "dblclick", function( event, ui ){  <br/>} <br/>});
			**/
            dblclick: null
        },

        /** @lends jazz.comboxfield */
        /**
         * @desc 创建组件
         * @private
         */      
        _create: function() {
        	this._super();
			/*  var divOpts = jazz.attributeToOptions(this.element);
            this.options = $.extend({}, this.options, divOpts);
            */
        	//生成框架
            var width = this.options.width;
            var height = this.options.height;      
            this.element.addClass("jazz-boxlist-ul jazz-boxlist-content ").css({"width":width,"height":height});
            this.listContainer = $('<ul class="jazz-listbox-list"></ul>').appendTo(this.element);
        },
        
         /**
         * @desc 初始化组件
         * @private
         */     
         _init: function(){
            
        	 //加载数据
        	 if(typeof(this.options.dataurl) == 'object'){
            	this._callback('1',this);
            }else if(!!this.options.dataurl){
            
            	this._ajax();
            }else{
            	this._callback({}, this);
            }
        },
        
        /**
         * @desc ajax请求函数
         * @return 返回请求响应的数据
         * @private
         */      
        _ajax: function(){
             param = {
        		url: this.options.dataurl,
        		params: this.options.params,
	        	callback: this._callback  //回调函数
            };
        	$.DataAdapter.submit(param, this);        	
        }, 
        
        /**
         * @desc ajax请求回调函数
         * @params {data} ajax请求返回的值
         * @params {sourceThis} 当前类对象
         * @private
         */
        _callback: function (data, sourceThis){
        	var jsonData = null;
        	var $this = null;
            if(data == '1'){
	            jsonData = this.options.dataurl;  
	            $this = sourceThis;
            }else{
            	jsonData =data ;
            	$this = sourceThis;
            }
            $this.arrayOption=[];	
			if(!!jsonData && !!jsonData.data){
				$this.arrayOption = jsonData.data;
			}
			
			var optHtml = '';
			var render = '';
            for(var i = 0; i < $this.arrayOption.length; i++) {
                var choice = $this.arrayOption[i];
                var _text = $.trim(choice.text);
                if(!choice.text) _text = ''; 
			
                if(!!$this.options.datarender){
	                render = eval($this.options.datarender).call(this,choice);
					optHtml += '<li class="jazz-boxlist-item jazz-boxlist-li">' + render + '</li>';
                }else{
					optHtml += '<li class="jazz-boxlist-item jazz-boxlist-li">' + _text + '</li>';
				}      
            }
			
            $this.listContainer.append(optHtml);
            $this.items = $this.listContainer.find('.jazz-boxlist-item');
            $this._bindEvents();
        },    
                
		/**
         * @desc 绑定事件
		 * @private
         */        
        _bindEvents: function() {
            var time = null;
            var $this=this;
            
            this.element.off()
            .on('dblclick.boxlist', function(event) {   
				var target = event.target;
				var $target=$(target);
	        	if($target.is("li")){  
					//清除延时触发的事件
					clearTimeout(time);
					
					var item=$target;
					if($this.options.multiple){
						item=$this._clickMultiple(event, item);
						$this._trigger('dblclick',null, item); 
					}else{         		
						item=$this._clickSingle(event, item);
						$this._trigger('dblclick',null, item); 
					}
				}
            })           
            .on('click.boxlist', function(event) {
	          	var target = event.target;
				var $target=$(target);
	        	if($target.is("li")){  
		            //清除延时触发的事件
		            clearTimeout(time);
					
					var item=$target;
					//单击事件延时300ms触发
					time = setTimeout(function(){
						if($this.options.multiple){
							item=$this._clickMultiple(event, item);
							$this._trigger('click',null, item);
						}else{         		
							item=$this._clickSingle(event, item);
							$this._trigger('click',null, item);
						}
					}, 200);
				}
            });
        },
           
		/**
         * @desc 点击选择一条记录
		 * @params {event} click事件对象
		 * @params {item} 触发click事件的元素
		 * @return 选中项数据
		 * @private	
         */        
        _clickSingle: function(event, item) {
            var selectedItem = this.items.filter('.jazz-boxlist-highlight');
			var itemIndex=item.index();
			var	selectedIndex=selectedItem.index();
            if(itemIndex !== selectedIndex) {
                if(selectedItem.length) {
                    this._unselectItem(selectedItem);
                }
                this._selectItem(item);

                //触发change事件

                if(selectedItem.length){
                	this._changeTrigger(selectedItem,item);
                }else{
                	this._changeTrigger(null,item);
                }
                
            }

            return this.arrayOption[itemIndex];
        },

		/**
         * @desc 点击选择多条记录
		 * @params {event} click事件对象
		 * @params {item} 触发click事件的元素
		 * @return 返回所有已选项数据
		 * @private
         */
        _clickMultiple: function(event, item) { 
        	var itemIndex=item.index();
        	if(this.items.eq(itemIndex).hasClass('jazz-boxlist-highlight')){
	       		this._unselectItem(item);
				//触发unclick事件 
				this._trigger('unclick',null, item);
				//触发change事件
	       		this._changeTrigger(item,null);
				    }else{
		        this._selectItem(item);
				//触发change事件
		        this._changeTrigger(null,item);
				}
        	
        	//返回所选项		
            return this.getSelected();
        },


		/**
         * @desc change事件触发
		 * @params {oldItem} 之前选项元素
		 * @params {newItem} 新选项元素
		 * @private
         */    
        _changeTrigger : function(oldItem,newItem){

			var changeItem = {};
			if(!oldItem){
				var newIndex=newItem.index();
				changeItem['newObject'] = this.arrayOption[newIndex];
				}
			else	
				if(!newItem){
				var oldIndex=oldItem.index();
					changeItem['oldObject']=this.arrayOption[oldIndex];
			}
			else{
					var newIndex=newItem.index();
					var oldIndex=oldItem.index();
					changeItem['newObject']=this.arrayOption[newIndex];
					changeItem['oldObject']=this.arrayOption[oldIndex];
				}
			this._trigger('change',null,changeItem);
           
	     },


    	/**
         * @desc 获得选中的项记录
		 * @params {value} 所选项元素或者索引
		 * @private
         */          
        _selectItem: function(value) {
            var item = null;
            if($.type(value) === 'number')
                item = this.items.eq(value);
            else
                item = value;
            item.addClass('jazz-boxlist-highlight').removeClass('jazz-boxlist-li');

        },

    	/**
         * @desc 取消获得选中的项记录
		 * @params {value} 取消项元素或者索引
		 * @private
         */        
        _unselectItem: function(value) {
            var item = null;
            if($.type(value) === 'number')
                item = this.items.eq(value);
            else
                item = value;
            
            item.addClass('jazz-boxlist-li').removeClass('jazz-boxlist-highlight');	           

        },
        
        /**
         * @desc 动态添加组件下拉框中的内容
         * @public
		 * @example $('div_id').boxlist('reload');
         */           
        reload: function() {
        	this._init();
        },
        
    	/**
         * @desc 取消选择的全部记录
         * @public
		 * @example $('#boxlist_id').boxlist('unselectAll');
         */           
        unselectAll: function() {
            this.items.removeClass('jazz-boxlist-highlight');
            //触发change事件
	    	this._trigger('change',null);    	 
        },

		/**
         * @desc 获取已选项数据
		 * @public
		 * @return 所选项的数据
		 * @example $('#boxlist_id').boxlist('getSelected');
         */       
        getSelected: function(){
        	var selected = this.items.filter('.jazz-boxlist-highlight');
        	var allSelected = [];
			var selectedIndex = null;
        	for(var i=0;i<selected.length;i++){
				selectedIndex = selected.index();
        		allSelected.push(this.arrayOption[selectedIndex]);
        	}
        	return allSelected;
        }
      
    });
        
})(jQuery);
(function($) {

	/**
	 * @version 0.5
	 * @name jazz.basemenu
	 * @description 菜单组件。
	 * @constructor
	 * @extends jazz.boxComponent
	 * @requires
	 * @example 
	 */		
    $.widget("jazz.basemenu", {
       
        options: /** @lends jazz.basemenu# */ {
        	/**
			 *@type Boolean
			 *@desc 判断组件是否弹出  true 弹出
			 *@default false
			 */        	
             popup: false,
         
         	/**
 			 *@type Object
 			 *@desc 触发menu组件的对象
 			 *@default null
 			 */             
             trigger: null,
             
           	/**
   			 *@type String
   			 *@desc 控制位置的配置
   			 *@default 'left top'
   			 */                  
             my: 'left top',
             
           	/**
   			 *@type String
   			 *@desc 控制位置的配置
   			 *@default 'left bottom'
   			 */             
             at: 'left bottom',
             
        	/**
			 *@type JSON
			 *@desc 加载的数据项
			 *@default null
			 */              
             data: null,
             
             /**
 			 *@type jquery Object
 			 *@desc 绑定的数据目标
 			 *@default null
 			 */             
             dataTarget: null,
             
             /**
 			 *@type String
 			 *@desc 链接
 			 *@default null
 			 */      
             url: null,
             
             /**
 			 *@type Object
 			 *@desc 参数
 			 *@default null
 			 */            
             params: null,            
             
          	/**
  			 *@type String
  			 *@desc 触发的事件
  			 *@default 'click'
  			 */                      
             triggerEvent: 'click'
        },
        
		/** @lends jazz.basemenu */
    	
		/**
         * @desc 创建组件
		 * @private
         */       
        _create: function() {
            if(this.options.popup) {
                this._initPopup();
            }
        },
        
		/**
         * @desc 实现组件构建
         * @example this.render();   //不写入API
		 * @private
         */          
        render: function() {
        	
            if(this.options.vtype=='slide'){
                this.element.addClass('jazz-menu-list ui-helper-reset').  
                wrap('<div vtype="menu" name="jazz_'+this.element.attr('name')+'" class="jazz-menu jazz-slidemenu ui-widget ui-widget-content ui-corner-all ui-helper-clearfix"/>').
                wrap('<div class="jazz-slidemenu-wrapper" />').
                after('<div class="jazz-slidemenu-backward ui-widget-header ui-corner-all ui-helper-clearfix">\n\
                <span class="ui-icon ui-icon-triangle-1-w"></span>返回</div>').
                wrap('<div class="jazz-slidemenu-content" />');               
            }else{
                this.element.addClass('jazz-menuitem ui-helper-reset').
                wrap('<div vtype="menu" name="jazz_'+this.element.attr('name')+'" class="jazz-tieredmenu jazz-menu ui-widget ui-widget-content ui-corner-all ui-helper-clearfix" />');                    	
            }

            this.element.parent().uniqueId();
    		
    		this.options.id = this.element.parent().attr('id');
    		
            this.element.find('li').each(function() {
                var listItem = $(this),
                menuitemLink = listItem.children('a'),
                icon = menuitemLink.data('icon');
                
                menuitemLink.addClass('jazz-menuitem-link ui-corner-all').contents().wrap('<span class="ui-menuitem-text" />');
                
                if(icon) {
                    menuitemLink.prepend('<span class="jazz-menuitem-icon" style="background: url('+icon+') no-repeat center center; width: 18px; height: 18px;"></span>');
                }
                
                listItem.addClass('jazz-menuitem ui-widget ui-corner-all');
                if(listItem.children('ul').length > 0) {
                    listItem.addClass('jazz-menu-parent');
                    listItem.children('ul').addClass('ui-widget-content jazz-menu-list ui-corner-all ui-helper-clearfix jazz-menu-child jazz-shadow');
                    menuitemLink.prepend('<span class="ui-icon ui-icon-triangle-1-e"></span>');
                }
            });    		
    		
//data数据存在时    
            if(!!this.options.data) {
            	this._callback('1');
            }else if(!!this.options.url){
            	this._ajax();
            }
        	
        }, 
        
		/**
        * @desc ajax请求
        */        
       _ajax: function(){
           var param = {
       		   url: this.options.url,
       		   params: this.options.params,
       		   async: false,
	           callback: this._callback  //回调函数
           };
       	   $.DataAdapter.submit2(param, this);        	
       }, 
       
       _callback: function (data, sourceThis){
	       	var jsonData = null;
	       	var $this = null;
            if(data == '1'){   
	            jsonData = this.options.data;
	            $this = this;
            }else{
	           	jsonData = data;
	           	$this = sourceThis;
            }
            if(!!jsonData && !!jsonData.data){
            	$this.options._arrayCode = new Array();
        		$.each(jsonData.data, function(i, data){
        			$this._createContent(data, $this);
        		});
        		
    	     }
        },        
        
        /**
         * @desc 初始化菜单内容
         * @example this._createContent(data, this);
		 * @private
         */ 
        _createContent : function(data, $this) {
        	var element = $this.options.dataTarget || $this.element;
        	if(data.pcode == null || $.trim(data.pcode)==''){  //一级菜单
				var li = $('<li id='+$this.options.id+data.code+' class="jazz-menuitem ui-widget ui-corner-all jazz-menu-parent"></li>').appendTo(element);
	  	      	var li_a = $('<a class="jazz-menuitem-link ui-corner-all" ></a>').appendTo(li);
	  	        if(!data.leaf){
	  	        	li_a.append('<span class="ui-icon ui-icon-triangle-1-e"></span>');
	  	        }
	  	        if(!!data.icon){
	  	        	li_a.append('<span class="jazz-menuitem-icon" style="background: url('+data.icon+') no-repeat center center; width: 18px; height: 18px;"></span>');
	  	        }
	  	        li_a.append('<span class="ui-menuitem-text">'+data.label+'</span>');      
	  	        
			}else{
				
				var pcode = $this.options.id+data.pcode;
				var code = $this.options.id+data.code;
				
	           	if(!jazz.inArray($this.options._arrayCode, pcode)){
	           		$('<ul id="'+pcode+'_ul" class="ui-widget-content jazz-menu-list ui-corner-all ui-helper-clearfix jazz-menu-child jazz-shadow"></ul>').appendTo($('#'+pcode));
	           		$this.options._arrayCode.push(pcode);
	           	}
	           	var li_ul_li = $('<li id='+code+' class="jazz-menuitem ui-widget ui-corner-all jazz-menu-parent"></li>').appendTo($('#'+pcode+'_ul'));
	           	var li_ul_li_a = $('<a class="jazz-menuitem-link ui-corner-all" ></a>').appendTo(li_ul_li);
	           	if(!data.leaf){
	           		li_ul_li_a.append('<span class="ui-icon ui-icon-triangle-1-e"></span>');
	           	}
	            if(!!data.icon){
	            	li_ul_li_a.append('<span class="jazz-menuitem-icon" style="background: url('+data.icon+') no-repeat center center; width: 18px; height: 18px;"></span>');
	            }
	           	li_ul_li_a.append('<span class="ui-menuitem-text">'+data.label+'</span>');     
	           	// 叶子节点增加点击事件 返回data
	           	if(!!data.leaf){
	           		$(li_ul_li_a).on('click', function() {
	           			$this._trigger('click', null, data);
	           		});
	           	}
			}
        },
        
        /**
		 * @desc 增加菜单
		 * @param {data} 添加菜单
		 */
        addMenu: function(addData) {
        	var $this = this;
    		$.each(addData.data, function(i, data){
    			$this._createContent(data, $this);
    		});
    		
    		this.links = this.element.find('.jazz-menuitem-link:not(.ui-state-disabled)');
    		this._bindBaseItemEvents();
    		this._bindBaseDocumentHandler();
		},
		
		/**
        * @desc 重新加载数据
		 * @example $('div_id').menu('loadData', url, params, flag);
        */
       loadData: function(url, params, flag){
	       if(!url){
	       		if(this.options.url != null){
	       			if(!!params){
	       				this.options.params = params;
	       			}
	       			this.element.children().remove();
	       			this._ajax();
	        		this.links = this.element.find('.jazz-menuitem-link:not(.ui-state-disabled)');
	        		this._bindBaseItemEvents();
	        		this._bindBaseDocumentHandler();	       			
	       		}
	        }else{
	        	this.element.children().remove();
            	if(flag == 'static'){   //static  目的是为了加载SwordPageData数据
            		this.options.data = url;
            		this._callback(1);            		
            	}else{
            		if(!!url){
            			this.options.url = url;
            		}
            		if(!!params){
            			this.options.params = params;
            		}
            		this._ajax();
            	}	
        		this.links = this.element.find('.jazz-menuitem-link:not(.ui-state-disabled)');
        		this._bindBaseItemEvents();
        		this._bindBaseDocumentHandler();	            	
	       	}
        },
		
		/**
         * @desc 绑定菜单项的事件
         * @example this._bindBaseItemEvents();
		 * @private
         */        
        _bindBaseItemEvents: function() {
            var $this = this;

            this.links.on('mouseenter.jazz-menu',function() {
                var link = $(this),
                menuitem = link.parent(),
                autoDisplay = $this.options.autoDisplay;

                var activeSibling = menuitem.siblings('.jazz-menuitem-active');
                if(activeSibling.length === 1) {
                    $this._deactivate(activeSibling);
                }

                if(autoDisplay||$this.active) {
                    if(menuitem.hasClass('jazz-menuitem-active')) {
                        $this._reactivate(menuitem);
                    }
                    else {
                        $this._activate(menuitem);
                    }  
                }
                else {
                    $this._highlight(menuitem);
                }
            });

            if(this.options.autoDisplay === false) {
                this.rootLinks = this.element.find('> .jazz-menuitem > .jazz-menuitem-link');
                this.rootLinks.data('jazz-tieredmenu-rootlink', this.options.id).find('*').data('jazz-tieredmenu-rootlink', this.options.id);

                this.rootLinks.on('click.jazz-menu', function(e) {
                    var link = $(this),
                    menuitem = link.parent(),
                    submenu = menuitem.children('ul.jazz-menu-child');

                    if(submenu.length === 1) {
                        if(submenu.is(':visible')) {
                            $this.active = false;
                            $this._deactivate(menuitem);
                        }
                        else {                             
                            $this.active = true;
                            $this._highlight(menuitem);
                            $this._showSubmenu(menuitem, submenu);
                        }
                    }
                });
            }
            
            this.element.parent().find('ul.jazz-menu-list').on('mouseleave.jazz-menu', function(e) {
                if($this.activeitem) {
                    $this._deactivate($this.activeitem);
                }
           
                e.stopPropagation();
            });
        },
       
		/**
         * @desc 绑定DOM元素的事件
         * @example this._bindBaseDocumentHandler();
		 * @private
         */
        _bindBaseDocumentHandler: function() {
            var $this = this;

            $(document.body).on('click.jazz-menu', function(e) {
                var target = $(e.target);
                if(target.data('jazz-tieredmenu-rootlink') === $this.options.id) {
                    return;
                }
                    
                $this.active = false;

                $this.element.find('li.jazz-menuitem-active').each(function() {
                    $this._deactivate($(this), true);
                });
            });
        },

           
		/**
         * @desc 初始化弹出的菜单组件
         * @example this._initPopup();
		 * @private
         */          
        _initPopup: function() {
            var $this = this;

            this.element.closest('.jazz-menu').addClass('jazz-menu-dynamic jazz-shadow').appendTo(document.body);

            this.positionConfig = {
                my: this.options.my,
                at: this.options.at,
                of: this.options.trigger
            };

            //trigger触发menu组件的对象
            this.options.trigger.on(this.options.triggerEvent + '.jazz-menu', function(e) {
            	
                if($this.element.is(':visible')) {
                	$this.hide();
                }
                else {
                	$this.show();
                }
                
                e.preventDefault();
            });

            //hide overlay on document click
            $(document.body).on('click.jazz-menu', function (e) {
            	
                var popup = $this.element.closest('.jazz-menu');
                if(popup.is(":hidden")) {
                    return;
                }

                //do nothing if mousedown is on trigger
                var target = $(e.target);
                if(target.is($this.options.trigger.get(0))||$this.options.trigger.has(target).length > 0) {
                    return;
                }

                //hide if mouse is outside of overlay except trigger
                var offset = popup.offset();
                if(e.pageX < offset.left ||
                    e.pageX > offset.left + popup.width() ||
                    e.pageY < offset.top ||
                    e.pageY > offset.top + popup.height()) {

                    $this.hide(e);
                }
            });

            //Hide overlay on resize
            $(window).on('resize.jazz-menu', function() {
                if($this.element.closest('.jazz-menu').is(':visible')) {
                    $this.align();
                }
            });
        },
            
		/**
         * @desc 显示组件
		 * @example $('div_id').basemenu('show');
         */        
        show: function() {
            this.align();
            this.element.closest('.jazz-menu').css('z-index', ++jazz.zindex).show();
        },

		/**
         * @desc 隐藏组件
		 * @example $('div_id').basemenu('hide');
         */        
        hide: function() {
            this.element.closest('.jazz-menu').fadeOut('fast');
        },

		/**
         * @desc 控制菜单组件显示位置
		 * @example $('div_id').basemenu('align');
         */        
        align: function() {
            this.element.closest('.jazz-menu').css({left:'', top:''}).position(this.positionConfig);
        }
    });

    
	/**
	 * @version 0.5
	 * @name jazz.tieredmenu
	 * @description 菜单组件。
	 * @constructor
	 * @extends jazz.basemenu
	 * @requires
	 * @example $('#tm3').tieredmenu({ <br>  popup: true, <br> trigger: $('#button_id') <br> });  
	 */	
    $.widget("jazz.tieredmenu", $.jazz.basemenu, {
        
        options: /** @lends jazz.tieredmenu# */ {
           	/**
   			 *@type Boolean
   			 *@desc 是否自动显示
   			 *@default false
   			 */          	
            autoDisplay: true
            
        },
        
        /** @lends jazz.tieredmenu */
        
		/**
         * @desc 创建组件
		 * @private
         */        
        _create: function() {
        	
            this.render();
        
            this.links = this.element.find('.jazz-menuitem-link:not(.ui-state-disabled)');
 
            this._bindItemEvents();
            this._bindDocumentHandler();
            
            this._super();
        },
            
        /**
         * @desc 绑定菜单项的事件
         * @example this._bindItemEvents();
		 * @private
         */        
        _bindItemEvents: function() {
            var $this = this;

            this.links.on('mouseenter.jazz-menu',function() {
                var link = $(this),
                menuitem = link.parent(),
                autoDisplay = $this.options.autoDisplay;

                var activeSibling = menuitem.siblings('.jazz-menuitem-active');
                if(activeSibling.length === 1) {
                    $this._deactivate(activeSibling);
                }

                if(autoDisplay||$this.active) {
                    if(menuitem.hasClass('jazz-menuitem-active')) {
                        $this._reactivate(menuitem);
                    }
                    else {
                        $this._activate(menuitem);
                    }  
                }
                else {
                    $this._highlight(menuitem);
                }
            });

            if(this.options.autoDisplay === false) {
                this.rootLinks = this.element.find('> .jazz-menuitem > .jazz-menuitem-link');
                this.rootLinks.data('jazz-tieredmenu-rootlink', this.options.id).find('*').data('jazz-tieredmenu-rootlink', this.options.id);

                this.rootLinks.on('click.jazz-menu', function(e) {
                    var link = $(this),
                    menuitem = link.parent(),
                    submenu = menuitem.children('ul.jazz-menu-child');

                    if(submenu.length === 1) {
                        if(submenu.is(':visible')) {
                            $this.active = false;
                            $this._deactivate(menuitem);
                        }
                        else {                             
                            $this.active = true;
                            $this._highlight(menuitem);
                            $this._showSubmenu(menuitem, submenu);
                        }
                    }
                });
            }
            
            this.element.parent().find('ul.jazz-menu-list').on('mouseleave.jazz-menu', function(e) {
                if($this.activeitem) {
                    $this._deactivate($this.activeitem);
                }
           
                e.stopPropagation();
            });
        },
       
		/**
         * @desc 绑定DOM元素的事件
         * @example this._bindDocumentHandler();
		 * @private
         */
        _bindDocumentHandler: function() {
            var $this = this;

            $(document.body).on('click.jazz-menu', function(e) {
                var target = $(e.target);
                if(target.data('jazz-tieredmenu-rootlink') === $this.options.id) {
                    return;
                }
                    
                $this.active = false;

                $this.element.find('li.jazz-menuitem-active').each(function() {
                    $this._deactivate($(this), true);
                });
            });
        },
        
		/**
         * @desc 使菜单失效
         * @param {menuitem} 菜单项对象
         * @param {animate} 动画效果  true false
         * @example this._deactivate();
		 * @private
         */          
        _deactivate: function(menuitem, animate) {
            this.activeitem = null;
            menuitem.children('a.jazz-menuitem-link').removeClass('ui-state-hover');
            menuitem.removeClass('jazz-menuitem-active');

            if(animate)
                menuitem.children('ul.jazz-menu-child:visible').fadeOut('fast');
            else
                menuitem.children('ul.jazz-menu-child:visible').hide();
        },

		/**
         * @desc 使菜单生效
         * @param {menuitem} 菜单项对象
         * @example this._activate(menuitem);
		 * @private
         */          
        _activate: function(menuitem) {
            this._highlight(menuitem);

            var submenu = menuitem.children('ul.jazz-menu-child');
            if(submenu.length === 1) {
                this._showSubmenu(menuitem, submenu);
            }
        },

		/**
         * @desc 使菜单恢复有效
         * @param {menuitem} 菜单项对象
         * @example this._reactivate(menuitem);
		 * @private
         */        
        _reactivate: function(menuitem) {
            this.activeitem = menuitem;
            var submenu = menuitem.children('ul.jazz-menu-child'),
            activeChilditem = submenu.children('li.jazz-menuitem-active:first'),
            _self = this;

            if(activeChilditem.length === 1) {
                _self._deactivate(activeChilditem);
            }
        },

		/**
         * @desc 高亮度显示
         * @param {menuitem} 菜单项对象
         * @example this._highlight(menuitem);
		 * @private
         */
        _highlight: function(menuitem) {
            this.activeitem = menuitem;
            menuitem.children('a.jazz-menuitem-link').addClass('ui-state-hover');
            menuitem.addClass('jazz-menuitem-active');
        },
          
		/**
         * @desc 显示子菜单
         * @param {menuitem} 菜单对象
         * @param {submenu} 子菜单对象
         * @example this._showSubmenu(menuitem, submenu);
		 * @private
         */        
        _showSubmenu: function(menuitem, submenu) {
            submenu.css({
                'left': menuitem.outerWidth(),
                'top': 0,
                'z-index': ++jazz.zindex
            });

            submenu.show();
        }
            
    });

	/**
	 * @version 0.5
	 * @name jazz.menubar
	 * @description 菜单组件。
	 * @constructor
	 * @extends jazz.tieredmenu
	 * @requires
	 * @example $('#ul_id').menubar({ <br>  autoDisplay: true, <br> });  
	 */
    $.widget("jazz.menubar", $.jazz.tieredmenu, {
        
        options: /** @lends jazz.menubar# */ {
 
           	/**
   			 *@type Boolean
   			 *@desc 是否自动显示 true显示  false不显示
   			 *@default false
   			 */        	
            autoDisplay: true         
        },

        /** @lends jazz.menubar */
        
		/**
         * @desc 创建组件
		 * @private
         */        
        _create: function() {
           
          	this._super();
          	 
          	this.element.parent().removeClass('jazz-tieredmenu').addClass('jazz-menubar');   
        },
           
		/**
         * @desc 显示子菜单
         * @param {menuitem} 菜单对象
         * @param {submenu}  子菜单对象
         * @example this._showSubmenu(menuitem, submenu);
		 * @private
         */            
        _showSubmenu: function(menuitem, submenu) {
            var win = $(window),
            submenuOffsetTop = null,
            submenuCSS = {
                'z-index': ++jazz.zindex
            };

            if(menuitem.parent().hasClass('jazz-menu-child')) {
                submenuCSS.left = menuitem.outerWidth();
                submenuCSS.top = 0; 
                submenuOffsetTop = menuitem.offset().top - win.scrollTop();
            } 
            else {
                submenuCSS.left = 0;
                submenuCSS.top = menuitem.outerHeight(); 
                submenuOffsetTop = menuitem.offset().top + submenuCSS.top - win.scrollTop();
            }

            //adjust height within viewport
            submenu.css('height', 'auto');
            if((submenuOffsetTop + submenu.outerHeight()) > win.height()) {
                submenuCSS.overflow = 'auto';
                submenuCSS.height = win.height() - (submenuOffsetTop + 20);
            }

            submenu.css(submenuCSS).show();
        }       
    });

	/**
	 * @version 0.5
	 * @name jazz.slidemenu
	 * @description 菜单组件。
	 * @constructor
	 * @extends jazz.basemenu
	 * @requires
	 * @example $('#slide').slidemenu();  
	 */
    $.widget("jazz.slidemenu", $.jazz.basemenu, {

    	/** @lends jazz.slidemenu */
    	
		/**
         * @desc 创建组件
		 * @private
         */                   
        _create: function() {

        	this.options.vtype = 'slide';
        	this.render();
        
            //elements
            this.rootList = this.element;
            this.content = this.element.parent();
            this.wrapper = this.content.parent();
            this.container = this.wrapper.parent();
            this.submenus = this.container.find('ul.jazz-menu-list');
            
            this.links = this.element.find('a.jazz-menuitem-link:not(.ui-state-disabled)');
            this.backward = this.wrapper.children('div.jazz-slidemenu-backward');


            //config
            this.stack = [];
            this.jqWidth = this.container.width();

            if(!this.element.hasClass('jazz-menu-dynamic')) {
                this._applyDimensions();
            }
           
            this._super();

            this._bindEvents();
        },
            
		/**
         * @desc 绑定事件
         * @example this._bindEvents();
		 * @private
         */        
        _bindEvents: function() {
            var $this = this;

            this.links.on('mouseenter.jazz-menu',function() {
               $(this).addClass('ui-state-hover'); 
            })
            .on('mouseleave.jazz-menu',function() {
               $(this).removeClass('ui-state-hover'); 
            })
            .on('click.jazz-menu',function() {
               var link = $(this),
               submenu = link.next();

               if(submenu.length == 1) {
                   $this._forward(submenu);
               }
            });

            this.backward.on('click.jazz-menu',function() {
                $this._back();
            });
       },

		/**
        * @desc 展示前一页菜单
        * @param {submenu} 子菜单对象
        * @example this._forward(submenu);
		* @private
        */         
       _forward: function(submenu) {
            var $this = this;

            this._push(submenu);

            var rootLeft = -1 * (this._depth() * this.jqWidth);

            submenu.show().css({
                left: this.jqWidth
            });

            this.rootList.animate({
                left: rootLeft
            }, 500, 'easeInOutCirc', function() {
                if($this.backward.is(':hidden')) {
                    $this.backward.fadeIn('fast');
                }
            });
       },

		/**
        * @desc 展示后一页菜单
        * @example this._back();
		* @private
        */           
       _back: function() {
            var $this = this,
            last = this._pop(),
            depth = this._depth();

            var rootLeft = -1 * (depth * this.jqWidth);

            this.rootList.animate({
                left: rootLeft
            }, 500, 'easeInOutCirc', function() {
                last.hide();

                if(depth === 0) {
                    $this.backward.fadeOut('fast');
                }
            });
       },

		/**
        * @desc 向栈中存入子项
        * @param 
        * @example this._push(submenu);
		* @private
        */        
       _push: function(submenu) {
             this.stack.push(submenu);
       },
    
		/**
        * @desc 向栈中取出
        * @example this._pop();
		* @private
        */           
       _pop: function() {
             return this.stack.pop();
       },

		/**
        * @desc 向栈中取出
        * @example this._last();
		* @private
        */         
       _last: function() {
            return this.stack[this.stack.length - 1];
        },

		/**
         * @desc 栈的深度
         * @example this._depth();
 		 * @private
         */            
       _depth: function() {
            return this.stack.length;
        },

		/**
         * @desc 控制菜单组件的大小
         * @example this._applyDimensions();
 		 * @private
         */        
       _applyDimensions: function() {
            this.submenus.width(this.container.width());
            this.wrapper.height(this.rootList.outerHeight(true) + this.backward.outerHeight(true));
            this.content.height(this.rootList.outerHeight(true));
            this.rendered = true;
        },

		/**
         * @desc 菜单展现
         * @example $('div_id').slidemenu('show');
         */           
       show: function() {                
            this.align();
            this.container.css('z-index', ++jazz.zindex).show();

            if(!this.rendered) {
                this._applyDimensions();
            }
        }        
    });

	/**
	 * @version 0.5
	 * @name jazz.contextmenu
	 * @description 菜单组件。
	 * @constructor
	 * @extends jazz.tieredmenu
	 * @requires
	 * @example $('#context').contextmenu();  
	 */
    $.widget("jazz.contextmenu", $.jazz.tieredmenu, {
        
        options: /** @lends jazz.contextmenu# */ {
        	 
           	/**
   			 *@type Boolean
   			 *@desc 是否自动显示 true显示  false不显示
   			 *@default false
   			 */        	
            autoDisplay: true,
            
        	/**
			 *@type Object
			 *@desc 绑定对象，将在绑定的对象内部运行
			 *@default null
			 */               
            target: null,
            
        	/**
			 *@type String
			 *@desc 绑定的事件，默认contextmenu
			 *@default 'contextmenu'
			 */               
            event: 'contextmenu'            
        },
        
		/** @lends jazz.contextmenu */
    	
		/**
         * @desc 创建组件
		 * @private
         */        
        _create: function() {
            this._super();
            
            this.element.parent().removeClass('jazz-tieredmenu').addClass('jazz-contextmenu jazz-menu-dynamic jazz-shadow');
            
            var $this = this;

            this.options.target = this.options.target||$(document);

            if(!this.element.parent().parent().is(document.body)) {
                this.element.parent().appendTo('body');
            }
            
            this.options.target.on(this.options.event + '.jazz-contextmenu' , function(e){
                   $this.show(e);
            });
        },        

		/**
         * @desc 绑定菜单数据项事件
		 * @throws
		 * @example this._bindItemEvents();
		 * @private
         */        
        _bindItemEvents: function() {
            this._super();

            var $this = this;

            //hide menu on item click
            this.links.bind('click', function() {
                $this._hide();
            });
        },


		/**
         * @desc 绑定DOM元素事件
		 * @throws
		 * @example this._bindDocumentHandler();
		 * @private
         */            
        _bindDocumentHandler: function() {
            var $this = this;

            //hide overlay when document is clicked
            $(document.body).bind('click.jazz-contextmenu', function (e) {
                if($this.element.parent().is(":hidden")) {
                    return;
                }

                $this._hide();
            });
        },

		/**
         * @desc 菜单展现
         * @param {e} 事件
         * @example $('div_id').contextmenu('show');
         */           
        show: function(e) {  
            //hide other contextmenus if any
            $(document.body).children('.jazz-contextmenu:visible').hide();

            var win = $(window),
            left = e.pageX,
            top = e.pageY,
            width = this.element.parent().outerWidth(),
            height = this.element.parent().outerHeight();

            //collision detection for window boundaries
            if((left + width) > (win.width())+ win.scrollLeft()) {
                left = left - width;
            }
            if((top + height ) > (win.height() + win.scrollTop())) {
                top = top - height;
            }

            if(this.options.beforeShow) {
                this.options.beforeShow.call(this);
            }

            this.element.parent().css({
                'left': left,
                'top': top,
                'z-index': ++jazz.zindex
            }).show();

            e.preventDefault();
            e.stopPropagation();
        },

		/**
         * @desc 隐藏组件
		 * @throws
		 * @example this._hide();
		 * @private
         */           
        _hide: function() {
            var $this = this;

            //hide submenus
            this.element.parent().find('li.jazz-menuitem-active').each(function() {
                $this._deactivate($(this), true);
            });

            this.element.parent().fadeOut('fast');
        },

		/**
         * @desc 判断组件是否可见
         * @return true/false
         * @example $('div_id').contextmenu('isVisible');
         */          
        isVisible: function() {
            return this.element.parent().is(':visible');
        },

		/**
         * @desc 获取组件所绑定的对象
         * @return Object 得到组件绑定的对象
         * @example $('div_id').contextmenu('getTarget');
         */          
        getTarget: function() {
            return this.jqTarget;
        }              
              
    });

})(jQuery);(function($){
	/**
	 * @description jazzUI date日期format的格式转换规则
	 * jazz.dateformat
	 */
	
	// 下面是日期的format的格式转换的规则
	/*
	Full Form和Short Form之间可以实现笛卡尔积式的搭配
	
	 Field        | Full Form          | Short Form                            中文日期，所有Full Form 不处理(除了年)
	 -------------+--------------------+-----------------------
	 Year         | yyyy (4 digits)    | yy (2 digits), y (2 or 4 digits)      NNNN  NN  N
	 Month        | MMM (name or abbr.)| MM (2 digits), M (1 or 2 digits)      Y
	              | NNN (abbr.)        |
	 Day of Month | dd (2 digits)      | d (1 or 2 digits)                     R
	 Day of Week  | EE (name)          | E (abbr)
	 Hour (1-12)  | hh (2 digits)      | h (1 or 2 digits)                     S
	 Hour (0-23)  | HH (2 digits)      | H (1 or 2 digits)                     T
	 Hour (0-11)  | KK (2 digits)      | K (1 or 2 digits)                     U
	 Hour (1-24)  | kk (2 digits)      | k (1 or 2 digits)                     V
	 Minute       | mm (2 digits)      | m (1 or 2 digits)                     F
	 Second       | ss (2 digits)      | s (1 or 2 digits)                     W
	 AM/PM        | a                  |
	
	 */
	//
	//例子
	//SwordDataFormat.formatDateToString(new Date(),"yyyymmdd");//
	//SwordDataFormat.formatStringToDate("1992_09_22","yyyy_mm_dd");
	//SwordDataFormat.formatStringToString("1992_09_22","yyyy_mm_dd","yy______mmdd");
	//SwordDataFormat.compareDates("1992_09_22","yyyy_mm_dd","1992_09_23","yyyy_mm_dd");//返回false
	//SwordDataFormat.isDate("1992_09_________22","yyyy_mm_________dd");//返回true
	//
	
	jazz.dateformat = {
		MONTH_NAMES : new Array('January','February','March','April','May','June','July','August','September','October','November','December',
											'Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'
											),
		DAY_NAMES : new Array('Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday',
										'Sun','Mon','Tue','Wed','Thu','Fri','Sat'
										),
	//    chi : [ '一', '二', '三', '四', '五', '六', '七', '八', '九','十'],
	    toChi:function(year){ //转换中文，不进位   //todo 需要优化为一条正则表达式
	        return year.replace(/0/g,'零').replace(/1/g,'一').
	                replace(/2/g,'二').replace(/3/g,'三').replace(/4/g,'四')
	                .replace(/5/g,'五').replace(/6/g,'六').replace(/7/g,'七')
	                .replace(/8/g,'八').replace(/9/g,'九');
	    },
	
	    toChiNum:function(n) {//转换中文，进位,只支持到十位数
	        var num = n / 1;
	        var num_s=num+'';
	        if (num > 9 && num < 20) {
	           num_s='十'+num_s.charAt(1);
	         } else if (num > 19) {
	          num_s=num_s.charAt(0)+'十'+num_s.charAt(1);
	        }
	        num_s = this.toChi(num_s);
	        if(num!=0)num_s=num_s.replace(/零/g,'');
	        return num_s;
	    },
	    toNum:function(year){//中文转换阿拉伯数字，纯替换  //todo 需要优化为一条正则表达式
	        return year.replace(/零/g,'0').replace(/一/g,'1').
	                replace(/二/g,'2').replace(/三/g,'3').replace(/四/g,'4')
	                .replace(/五/g,'5').replace(/六/g,'6').replace(/七/g,'7')
	                .replace(/八/g,'8').replace(/九/g,'9');
	    },
	    toNum2:function(year){ //中文转换阿拉伯数字，带进位，支持2位数 //todo 需要优化为一条正则表达式
	        var l = year.length;
	        if (year == '十')return 10;
	        if (l == 1 && '十' != year)return this.toNum(year);//零 到 九
	        if (l == 2&& '十' != year.charAt(l-1))return this.toNum(year.replace(/十/g,'一'));//十一 到 十九
	        if (l == 2&& '十' == year.charAt(l-1)) return this.toNum(year.replace(/十/g,'零'));//二十 到 九十 的整数
	        if(l==3)return this.toNum(year.replace(/十/g,''));//二十一 到 九十九 的三位中文数字
	    },
	
		LZ : function(x){
				return(x < 0 || x > 9 ? "" : "0") + x;
			 },
		isDate : function(val,format){//看看给定的字符串是否为Date类型
					var date = this.formatStringToDate(val,format);
					if (date == 0) { return false; }
					return true;
				 },
		compareDates :  function(date1,dateformat1,date2,dateformat2){//比较大小
							var d1 = this.formatStringToDate(date1,dateformat1);
							var d2 = this.formatStringToDate(date2,dateformat2);
							if (d1==0 || d2==0) {
								alert("format格式转换有问题");
								return;
							}else if (d1 > d2) {
								return true;
							}
							return false;
						},
		formatDateToString : function (date,format) {//将日期转化为Str
						//赋值
						format = format+"";
						var result = "";//返回的结果字符串
						var i_format = 0;//format字符串的位置指针
						var c = "";
						var token = "";//format字符串中的子串
						var y = date.getFullYear() + "";
						var M = date.getMonth() + 1;
						var d = date.getDate();
						var E = date.getDay();
						var H = date.getHours();
						var m = date.getMinutes();
						var s = date.getSeconds();
						var yyyy,yy,MMM,MM,dd,hh,h,mm,ss,ampm,HH,H,KK,K,kk,k;
						
						//将所有的规则的key加入到value对象的key中,将传入的date取出来的值加入到value对象的value中
						var value = new Object();
						value["y"] = "" + y;
						value["yyyy"] = y;
						value["yy"] = y.substring(2,4);
	
						value["M"] = M;
						value["MM"] = this.LZ(M);
						value["MMM"] = this.MONTH_NAMES[M-1];
						value["NNN"] = this.MONTH_NAMES[M+11];
	        
						value["d"] = d;
						value["dd"] = this.LZ(d);
						value["E"] = this.DAY_NAMES[E+7];
						value["EE"] = this.DAY_NAMES[E];
						
						value["H"] = H;
						value["HH"] = this.LZ(H);
						if (H==0){
							value["h"] = 12;
						}else if (H>12){
							value["h"] = H-12;
						}else {
							value["h"] = H;
						}
						value["hh"] = this.LZ(value["h"]);
						if (H>11){
							value["K"]=H-12;
						}else{
							value["K"]=H;
						}
						value["k"] = H+1;
						value["KK"] = this.LZ(value["K"]);
						value["kk"] = this.LZ(value["k"]);
						
						if (H > 11) { 
							value["a"]="PM"; 
						}else { 
							value["a"]="AM"; 
						}
						
						value["m"] = m;
						value["mm"] = this.LZ(m);
	        
						value["s"] = s;
						value["ss"] = this.LZ(s);
						
	                    value["NNNN"] = this.toChi(value["yyyy"]);
	                    value["NN"] = this.toChi(value["yy"]);
	                    value["N"] = this.toChi(value["y"]);
	                    value["Y"] = this.toChiNum(M);
	                    value["R"] = this.toChiNum(d);
	                    value["S"] = this.toChiNum(value["h"]);
	                    value["T"] = this.toChiNum(value["H"]);
	                    value["U"] = this.toChiNum(value["K"]);
	                    value["V"] = this.toChiNum(value["k"]);
	                    value["F"] = this.toChiNum(m);
	                    value["W"] = this.toChiNum(s);
	
	
						//开始进行校验
						while (i_format < format.length) {//以i_format为记录解析format的指针,进行遍历
							c = format.charAt(i_format);
							token="";
							while ((format.charAt(i_format)==c) && (i_format < format.length)) {//当进行遍历的字符相同的时候,token取的就是当前遍历的相同字符,例如yyyy mm dd,这里就是三个循环yyyy mm dd
								token += format.charAt(i_format++);
							}
							if (value[token] != null) {
								result = result + value[token];//循环叠加value
							}else { 
								result = result + token; 
							}
						}
						return result;//最后返回格式化的字符串
					},
		_isInteger : function(val){
			//return ['0','1','2','3','4','5','6','7','8','9'].contains(val);
			var digits="1234567890";
			for (var i=0; i < val.length; i++) {
				if (digits.indexOf(val.charAt(i))==-1) { return false; }
			}
			return true;
		},
	    _isInteger_chi : function(val){
			//return ['0','1','2','3','4','5','6','7','8','9'].contains(val);
			var digits="零一二三四五六七八九十";
			for (var i=0; i < val.length; i++) {
				if (digits.indexOf(val.charAt(i))==-1) { return false; }
			}
			return true;
		},
		_getInt : function(str,i,minlength,maxlength){
			for (var x = maxlength; x >= minlength; x--) {
				var token=str.substring(i,i+x);
				if (token.length < minlength) { return null; }
				if (this._isInteger(token)) { return token; }
			}
			return null;
		},
		_getInt2 : function(str,i,minlength,maxlength){
			for (var x = maxlength; x >= minlength; x--) {
				var token=str.substring(i,i+x);
				if (token.length < minlength) { return null; }
				if (token) { return token; }
			}
			return null;
		},
	
	     _getInt_month: function(str,i){
			for (var x = 2; x >= 1; x--) {
				var token=str.substring(i,i+x);
				if (token.length < 1) { return null; }
	            if (token.length == 1) { return token; }
				if (['十一','十二'].contains(token)) { return token; }
			}
			return null;
		},
	
	     _getInt_date: function(str,i){
			for (var x = 3; x >= 1; x--) {
	 			var token=str.substring(i,i+x);
				if (token.length < 1) { return null; }
	            if(token.length ==1)return token;
				if(this._isInteger_chi(token))return token;
			}
			return null;
		},
		formatStringToDate : function(val,format){//将字符串转化为Date
			//赋值
			val = val + "";
			format = format + "";
			var i_val=0;//val字符串的指针
			var i_format=0;//format字符串的指针
			var c="";
			var token="";
			var token2="";
			var x,y;
			var now=new Date();
			var year=now.getFullYear();
			var month=now.getMonth()+1;
			var date=1;
			var hh=now.getHours();
			var mm=now.getMinutes();
			var ss=now.getSeconds();
			var ampm="";
			
			while (i_format < format.length) {
				//根据类似yyyy,mm同名的字符串的规则,可以取得yyyy或者mm等format字符串
				c = format.charAt(i_format);
				token = "";
				while ((format.charAt(i_format)==c) && (i_format < format.length)) {
					token += format.charAt(i_format++);
				}
				//从val中通过format中的token解析,进行规则转换
	            if ( token=="NNNN" || token=="NN" || token=="N") {//年 中文
					if ( token=="NNNN") { x=4;y=4; }
					if (  token=="NN")   { x=2;y=2; }
					if ( token=="N")    { x=2;y=4; }
					year=this._getInt2(val,i_val,x,y);//从val字符串中根据val的指针i_val,定义的最小/大长度(如上面的y,它所允许的最大长度就是4,最小长度就是2,例如2009,09等)
	                if (year==null) { return 0; }
	                year=this.toNum(year);
					i_val += year.length;//修改val的指针i_val,使其指向当前的变量
					if (year.length==2) {//年输入两位数的作用
						if (year > 70) {
							year = 1900 + (year-0); //如果>70年的话,肯定不是现在了,加1900就行了
						}else {
							year=2000 + (year-0);
						}
					}
				}else if (token=="Y") {//月_数字_大写
	
	                month = this._getInt_month(val, i_val);
	                i_val+=month.length;
	                month=this.toNum2(month);
	                if (month == null || (month < 1) || (month > 12)) {
	                    return 0;
	                }
	
	            }else if (token=="R") {  //日 数字 中文
	                
					date=this._getInt_date(val,i_val);
	                i_val+=date.length;
	                date=this.toNum2(date);
					if(date==null||(date<1)||(date>31)){return 0;}
	
				}else if (token=="S") { //小时 数字 中文 h
					hh=this._getInt_date(val,i_val);
	                i_val+=hh.length;
	                hh=this.toNum2(hh);
					if(hh==null||(hh<1)||(hh>12)){return 0;}
				}else if (token=="T") { //小时 数字 中文  H
					hh=this._getInt_date(val,i_val);
	                i_val+=hh.length;
	                hh=this.toNum2(hh);
					if(hh==null||(hh<0)||(hh>23)){return 0;}
				}else if (token=="U") {   //小时 数字 中文 K
					hh=this._getInt_date(val,i_val);
	                i_val+=hh.length;
	                hh=this.toNum2(hh);
					if(hh==null||(hh<0)||(hh>11)){return 0;}
				}else if (token=="V") {   //小时 数字 中文 k
					hh=this._getInt_date(val,i_val);
	                i_val+=hh.length;
	                hh=this.toNum2(hh);hh--;
					if(hh==null||(hh<1)||(hh>24)){return 0;}
				}else if (token=="F" ) {   //分 数字 中文
					mm=this._getInt_date(val,i_val);  
	                i_val+=mm.length;
	                mm=this.toNum2(mm);
					if(mm==null||(mm<0)||(mm>59)){return 0;}
				}else if (token=="W" ) {   //秒 数字 中文
					ss=this._getInt_date(val,i_val);
	                i_val+=ss.length;
	                ss=this.toNum2(ss);
					if(ss==null||(ss<0)||(ss>59)){return 0;}
				}else if (token=="yyyy" || token=="yy" || token=="y" ) {//年
					if (token=="yyyy") { x=4;y=4; }
					if (token=="yy")   { x=2;y=2; }
					if (token=="y")    { x=2;y=4; }
					year=this._getInt(val,i_val,x,y);//从val字符串中根据val的指针i_val,定义的最小/大长度(如上面的y,它所允许的最大长度就是4,最小长度就是2,例如2009,09等)
					if (year==null) { return 0; }
					i_val += year.length;//修改val的指针i_val,使其指向当前的变量
					if (year.length==2) {//年输入两位数的作用
						if (year > 70) { 
							year = 1900 + (year-0); //如果>70年的话,肯定不是现在了,加1900就行了
						}else { 
							year=2000 + (year-0); 
						}
					}
				}else if (token=="MMM"||token=="NNN"){//月_name
					month=0;
					for (var i=0; i < this.MONTH_NAMES.length; i++) {
						var month_name = this.MONTH_NAMES[i];
						if (val.substring(i_val,i_val + month_name.length).toLowerCase() == month_name.toLowerCase()) {//如果指针指向的长度与this.MONTH_NAMES中的任何一项都相同
							if (token=="MMM"||(token=="NNN" && i>11)) {
								month = i+1;//将month转换为数字,+1是js中的month比实际的小1
								if (month>12) { month -= 12; }
								i_val += month_name.length;
								break;
							}
						}
					}
					if ((month < 1)||(month>12)){return 0;}//不符合规则返回0
				}else if (token=="EE"||token=="E"){//日
					for (var i=0; i < this.DAY_NAMES.length; i++) {
						var day_name = this.DAY_NAMES[i];
						if (val.substring(i_val,i_val + day_name.length).toLowerCase() == day_name.toLowerCase()) {
							i_val += day_name.length;
							break;
						}
					}
				}else if (token=="MM"||token=="M") {//月_数字
					month=this._getInt(val,i_val,token.length,2);
					if(month==null||(month<1)||(month>12)){return 0;}
					i_val+=month.length;
				}else if (token=="dd"||token=="d") {
					date=this._getInt(val,i_val,token.length,2);
					if(date==null||(date<1)||(date>31)){return 0;}
					i_val+=date.length;
				}else if (token=="hh"||token=="h") {
					hh=this._getInt(val,i_val,token.length,2);
					if(hh==null||(hh<1)||(hh>12)){return 0;}
					i_val+=hh.length;
				}else if (token=="HH"||token=="H") {
					hh=this._getInt(val,i_val,token.length,2);
					if(hh==null||(hh<0)||(hh>23)){return 0;}
					i_val+=hh.length;
				}else if (token=="KK"||token=="K") {
					hh=this._getInt(val,i_val,token.length,2);
					if(hh==null||(hh<0)||(hh>11)){return 0;}
					i_val+=hh.length;
				}else if (token=="kk"||token=="k") {
					hh=this._getInt(val,i_val,token.length,2);
					if(hh==null||(hh<1)||(hh>24)){return 0;}
					i_val+=hh.length;hh--;
				}else if (token=="mm"||token=="m") {
					mm=this._getInt(val,i_val,token.length,2);
					if(mm==null||(mm<0)||(mm>59)){return 0;}
					i_val+=mm.length;
				}else if (token=="ss"||token=="s") {
					ss=this._getInt(val,i_val,token.length,2);
					if(ss==null||(ss<0)||(ss>59)){return 0;}
					i_val+=ss.length;
				}else if (token=="a") {//上午下午
					if (val.substring(i_val,i_val+2).toLowerCase()=="am") {ampm="AM";}
					else if (val.substring(i_val,i_val+2).toLowerCase()=="pm") {ampm="PM";}
					else {return 0;}
					i_val+=2;
				}else {//最后,没有提供关键字的,将指针继续往下移
					if (val.substring(i_val,i_val+token.length)!=token) {return 0;}
					else {i_val+=token.length;}
				}
			}
			//如果有其他的尾随字符导致字符串解析不下去了,那么返回0
			if (i_val != val.length) { return 0; }
			//对于特殊月份:2月,偶数月的天数进行校验
			if (month==2) {
				if ( ( (year%4==0)&&(year%100 != 0) ) || (year%400==0) ) { //测试是否是闰年
					if (date > 29){ return 0; }
				}else { 
					if (date > 28) { return 0; } 
				}
			}
			if ((month==4)||(month==6)||(month==9)||(month==11)) {
				if (date > 30) { return 0; }
			}
			//对于上午下午的具体显示小时数,进行加减
			if (hh<12 && ampm=="PM") {
				hh=hh-0+12;
			}else if (hh>11 && ampm=="AM") {
				hh-=12;
			}
			
			//将给定的字符串解析成Data
			var newdate=new Date(year,month-1,date,hh,mm,ss);
			return newdate;	
		},
		formatStringToString : function(val,format1,format2){//将一个字符串从原来的format1的字符串格式输出到format2字符格式
			var tempDate = this.formatStringToDate(val,format1);
			if(tempDate == 0){
				return val;
			}
			var returnVal = this.formatDateToString(tempDate,format2);
			if(returnVal == 0){
				return val;
			}
			return returnVal;
		}
		
	};
	
})(jQuery);

(function($){
	/**
	 * @description jazzUI number数字format的格式转换规则
	 * jazz.numberformat
	 */
	jazz.numberformat = {
		
	};
	
})(jQuery);


if (typeof JSON !== 'object') {
    JSON = {};
}

(function () {
    'use strict';

    function f(n) {
        
        return n < 10 ? '0' + n : n;
    }

    if (typeof Date.prototype.toJSON !== 'function') {

        Date.prototype.toJSON = function () {

            return isFinite(this.valueOf())
                ? this.getUTCFullYear()     + '-' +
                    f(this.getUTCMonth() + 1) + '-' +
                    f(this.getUTCDate())      + 'T' +
                    f(this.getUTCHours())     + ':' +
                    f(this.getUTCMinutes())   + ':' +
                    f(this.getUTCSeconds())   + 'Z'
                : null;
        };

        String.prototype.toJSON      =
            Number.prototype.toJSON  =
            Boolean.prototype.toJSON = function () {
                return this.valueOf();
            };
    }

    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        gap,
        indent,
        meta = {    // table of character substitutions
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"' : '\\"',
            '\\': '\\\\'
        },
        rep;


    function quote(string) {

        escapable.lastIndex = 0;
        return escapable.test(string) ? '"' + string.replace(escapable, function (a) {
            var c = meta[a];
            return typeof c === 'string'
                ? c
                : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
        }) + '"' : '"' + string + '"';
    }


    function str(key, holder) {


        var i,      
            k,
            v,
            length,
            mind = gap,
            partial,
            value = holder[key];

        if (value && typeof value === 'object' &&
                typeof value.toJSON === 'function') {
            value = value.toJSON(key);
        }


        if (typeof rep === 'function') {
            value = rep.call(holder, key, value);
        }


        switch (typeof value) {
        case 'string':
            return quote(value);

        case 'number':


            return isFinite(value) ? String(value) : 'null';

        case 'boolean':
        case 'null':

            return String(value);


        case 'object':


            if (!value) {
                return 'null';
            }

            gap += indent;
            partial = [];


            if (Object.prototype.toString.apply(value) === '[object Array]') {

                length = value.length;
                for (i = 0; i < length; i += 1) {
                    partial[i] = str(i, value) || 'null';
                }

                v = partial.length === 0
                    ? '[]'
                    : gap
                    ? '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']'
                    : '[' + partial.join(',') + ']';
                gap = mind;
                return v;
            }

            if (rep && typeof rep === 'object') {
                length = rep.length;
                for (i = 0; i < length; i += 1) {
                    if (typeof rep[i] === 'string') {
                        k = rep[i];
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            } else {

                for (k in value) {
                    if (Object.prototype.hasOwnProperty.call(value, k)) {
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            }

            v = partial.length === 0
                ? '{}'
                : gap
                ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}'
                : '{' + partial.join(',') + '}';
            gap = mind;
            return v;
        }
    }

    if (typeof JSON.stringify !== 'function') {
        JSON.stringify = function (value, replacer, space) {

            var i;
            gap = '';
            indent = '';

            if (typeof space === 'number') {
                for (i = 0; i < space; i += 1) {
                    indent += ' ';
                }

            } else if (typeof space === 'string') {
                indent = space;
            }

            rep = replacer;
            if (replacer && typeof replacer !== 'function' &&
                    (typeof replacer !== 'object' ||
                    typeof replacer.length !== 'number')) {
                throw new Error('JSON.stringify');
            }

            return str('', {'': value});
        };
    }

    if (typeof JSON.parse !== 'function') {
        JSON.parse = function (text, reviver) {

            var j;

            function walk(holder, key) {

                var k, v, value = holder[key];
                if (value && typeof value === 'object') {
                    for (k in value) {
                        if (Object.prototype.hasOwnProperty.call(value, k)) {
                            v = walk(value, k);
                            if (v !== undefined) {
                                value[k] = v;
                            } else {
                                delete value[k];
                            }
                        }
                    }
                }
                return reviver.call(holder, key, value);
            }

            text = String(text);
            cx.lastIndex = 0;
            if (cx.test(text)) {
                text = text.replace(cx, function (a) {
                    return '\\u' +
                        ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
                });
            }


            if (/^[\],:{}\s]*$/
                    .test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
                        .replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
                        .replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {


                j = eval('(' + text + ')');


                return typeof reviver === 'function'
                    ? walk({'': j}, '')
                    : j;
            }

            throw new SyntaxError('JSON.parse');
        };
    }
}());
(function($) {
	
	/**
	 * @version 1.0
	 * @name jazz.DataAdapter
	 * @description 
	 */
	$.DataAdapter = {
		submit: function(params, $this){
	 		var adapter = new $.dataAdapterObject();
	 		adapter.submit(params, $this);
		},
//		query: function(params){
//	 		var adapter = new $.dataAdapterObject();
//	 		adapter.query(params);
//		},	
		query: function(params){
	 		var adapter = new $.dataAdapterObject();
	 		adapter.createAdapter();
	 		adapter.options.components = params['components'];
	 		adapter.componentsData2();
	 		return adapter.dataArray;
		},
		vtypeset: ['gridpanel', 'formpanel', 'querypanel', 'comboxtreefield', 'comboxfield', 'icon' , 'attachment', 'boxlist', 'tree']
	};

	$.dataAdapterObject = function() {
		
		 this.options = {
			 sourceobject: null,   //组件对象   
		     contextpath: '',
			
			 name: null,   //组件对应的名称
			 vtype: null,  //vtype类型
			 
			 components: null, 
			
			 url: null,
			 type: 'post',
	    	 data: null,        //数据
			 //dataType: 'json',  //数据类型

			 success: null,    
		     error: null,
		     callback: null,    //回调函数
		     params: null,      //参数{}
		     pageparams: null,  //分页所需要的必要参数
		     queryparams: null  //查询条件必要参数
	     };
	 };
	 
	 $.dataAdapterObject.prototype = {
	    
		//UI调用
	    createAdapter: function(){
			this.jazzData = new Object();
			this.dataArray = new Array();
			this.jazzData["data"] = this.dataArray;
	 	},
	 	
	 	/**
	 	 * @desc 添加参数
	 	 * @param {name}  key值 
	 	 * @param {value} value值
	 	 */
	 	addAttr: function(name, value) {
	 		if (!!name && !!vtype) {
	 			var temp = {};
	 			temp['name'] = name;
	 			temp['vtype'] = 'attr';
	 			temp['value'] = value;
	 			this.dataArray[name] = value;
	 		}
	 	},	 	
	 	
	 	/**
	 	 * 添加form格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 */		 	
	 	addForm: function(name, vtype, formData) {
	 		this.dataArray.push(formData);
	 	},

	 	/**
	 	 * 添加grid格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 * @returns {Object}
	 	 */	 	
	 	addGrid: function(name, vtype, gridData) {
	 		var obj = {};
	 		obj["name"] = name;
	 		obj["vtype"] = vtype;
	 		obj["data"] = {};
	 		obj["data"]["rows"] = gridData || [];
	 		this.dataArray.push(obj);
	 	},
	 	
	 	/**
	 	 * 添加select格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 * @returns {Object}
	 	 */	 	
	 	addSelect: function(name, vtype, selectData){
	 		this.dataArray.push(selectData);
	 	},
	 	
	 	/**
	 	 * 添加tree格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 * @returns {Object}
	 	 */
	 	addTree: function(name, vtype, treeData) {
	 		this.dataArray.push(treeData);
	 	},	 	
	 	
	 	/**
	 	 * 添加icon格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param iconData
	 	 * @returns {Object}
	 	 */
	 	addIcon: function(name, vtype, iconData){
	 		var obj = new Object();
	 		this.dataArray.push(iconData);
	 		return obj;	 		
	 	},	 	

	 	/**
	 	 * @desc 获取提交数据的string格式
	 	 * @returns string
	 	 */
	 	getJazzDataString: function() {
	 		return jazz.jsonToString(this.jazzData);
	 	},
	 	
	 	/**
	 	 * @desc 获取提交的JSON数据
	 	 * @returns {}
	 	 */
	 	getSubmitJsonData: function() {
	 		return this.jazzData;
	 	},
	 	
	 	/**
	 	 * @desc ajax提交方法
	 	 */
	 	ajaxSubmit: function(){
	 		var $this = this;
	 		
	 		//返回参数attr
	 		var initRes = {
 				obj: {},
 				setAttr: function(key, value){
 					this.obj[key] = value;
 				},
 				getAttr: function(key){
 					return this.obj[key];
 				}
	 		};	 		

	 		var data = this.getJazzDataString(); //this.getSubmitJsonData();
	 		
	 		jazz.log("====提交数据格式====="+data);
	 		
			$.ajax({
				url: $this.options.url,
				type: $this.options.type,
				dataType: 'json',
				data: 'postData='+data,
				async: true,
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				error: $this.options.error || function(responseObj) {
					jazz.error("与服务器连接断开，请尝试重新登录或与管理员联系!");
				},
				success: function(responseObj){
					if(responseObj['exception']){
						jazz.error('<font color="blue" >错误名称</font> : ' + responseObj['exception']
		                        + '<br><font color="blue" >错误信息</font> : ' + responseObj['exceptionMes']);
						return false;
					}
					//返回给回调函数的数据
					var callbackdata = null;
					
					var jsonData = responseObj['data'];
				    if(typeof(jsonData) == "object"){
				    	if(jsonData != null && $.isArray(jsonData)){
				    		var newJsonData = [];
				    		$.each(jsonData, function(i, jsonObj){
				    			var vtype = jsonObj['vtype'];
								if(vtype=='attr'){
									initRes.setAttr(jsonObj['name'], jsonObj['data']);
								}
								if($.inArray(vtype, $.DataAdapter.vtypeset) >= 0){
									newJsonData.push(jsonData[i]);
								}
				    		});
				    		//数组中只有一个对象时，返回对象
				    		if(newJsonData.length == 1){
				    			callbackdata = newJsonData[0];
				    		}
				    	}
				    }else{
				    	//与定义格式不符时，原样返回
				    	callbackdata = jsonData;
				    }
					//jazz.log("callbackdata===="+JSON.stringify(callbackdata));
					if($.isFunction($this.options.callback)){
						$this.options.callback.call(this, callbackdata, $this.options.sourceobject, initRes);
					}				
				}
			}); 		
	 	},
		
	 	/**
	 	 * @desc 创建UI组件的JSON数据
	 	 */
		componentsData: function () {
			var flag=true;
			//组件集合
			var components = this.options.components;
			if($.isArray(components)){
				for(var i=0, len=components.length; i<len; i++){
					var compJson = $('div[name="'+components[i]+'"]');
					var vtype = compJson.attr('vtype');
					var name = compJson.attr('name');
					//验证
					if(vtype=='formpanel')
					{
						$('div[name="'+components[i]+'"]').find('div[vtype]').each(function(index,element){
							var el = $(element);
							var vtype=el.attr('vtype');
							var rule=el.attr('rule');
							if(rule){
								var $field = el.data(vtype), options = $field.options;
								var msg = options.msg;
								var validationstate = options.validationstate;
								if(!validationstate){
									var f = jazz.doTooltip($field, $field.getValue(), rule, msg);
								    if(f==false){
								    	flag=false;
								    	$field._validateStyle(f);
								    }
								    options.validationstate = f;
								}
							}
							
						}); 
					}
					if(!flag)
					{return false;}

					//是否在vtype集合中定义，如果不存在则不解析
					if($.inArray(vtype, $.DataAdapter.vtypeset) >= 0){
						if(vtype=='formpanel' || vtype=='querypanel'){
							var data = compJson[vtype]('getValue');
							this.addForm(name, vtype, data);
						}else if(vtype=='gridpanel'){
							var data = compJson[vtype]('getSelection');
							this.addGrid(name, vtype, data);
						}
						
//						else if(vtype=='icon'){
//							this.addIcon(this.options.name, vtype, data);
//						}else if(vtype=='comboxtreefield'){
//							var data = compJson[vtype]('getValue');
//							this.addTree(this.options.name, vtype, data);
//						}					
						
					}else{
						alert('解析的vtype类型错误！！！ ');
						return false;
					}
			
				}
			}
			
			var qparams = this.options.queryparams; 
			if(qparams){
				var $this = this;
				$.each(qparams, function(){
					$this.dataArray.push(this);
				});
			};
			
			//分页提交的必要参数
			var params = this.options.pageparams;
			if(params && typeof(params)=='object'){
				for(var p in params){
					var param = {};
					param['vtype']='pagination';
					param['name']=p;
					param['data']=params[p];
					this.dataArray.push(param);
			    }
			}			
			
			//解析需要提交的参数
			var params = this.options.params;
			if(params && typeof(params)=='object'){
				for(var p in params){
					var param = {};
					param['vtype']='attr';
					param['name']=p;
					param['data']=params[p];
					this.dataArray.push(param);
			    }
			}
			return flag;
		},
		
	 	/**
	 	 * @desc 创建UI组件的JSON数据
	 	 */
		componentsData2: function () {
			
			var params = this.options.params;
			if(params && typeof(params)=='object'){
				for(var p in params){
					var param = {};
					param['vtype']='attr';
					param['name']=p;
					param['data']=params[p];
					this.dataArray.push(param);
			    }
			}			
			
			//组件集合
			var components = this.options.components;
			if($.isArray(components)){
				for(var i=0, len=components.length; i<len; i++){
					var name = components[i];
					var compJson = $('div[name="'+name+'"]');
					var vtype = compJson.attr('vtype');

					//是否在vtype集合中定义，如果不存在则不解析
					if(vtype=='formpanel' || vtype=='querypanel'){
						var data = compJson[vtype]('getValue');
						this.addForm(this.options.name, vtype, data);
					}else
					if(vtype=='textfield' || vtype=='comboxfield' || vtype=='datefield' || vtype=='comboxtreefield'
					|| vtype=='checkboxfield' || vtype=='radiofield' || vtype=='autofield' || vtype=='numberfield'
					|| vtype=='passwordfield'){
							var data = compJson[vtype]('getValue');
							var param = {};
							param['vtype']='attr';
							param['name']=name;
							param['data']=data;
							this.dataArray.push(param);
					}
			
				}
			}
		},		
		
//		/**
//		 * @desc 查询方法
//		 * @param {params} ajax提交的参数对象
//		 * @param {$sourcethis} 该参数会通过回调函数原样返回
//		 */		
//		query: function(params){
//			 this.createAdapter();
//			 $.extend(this.options, params);
//			 this.componentsData2();
//			 //this.ajaxSubmit();
//		},
		
		/**
		 * @desc 提交方法
		 * @param {params} ajax提交的参数对象
		 * @param {$sourcethis} 该参数会通过回调函数原样返回
		 */
		submit: function(params, $sourcethis){
			//数据提交
			var flag=true;
            this.createAdapter();
            $.extend(this.options, params);
            this.options.sourceobject = $sourcethis;
        	flag=this.componentsData();
        	if(String(flag)=='false'){
        		return false;
        	}
        	this.ajaxSubmit();
		}
	 
	 };
	 
     $(function(){
		  //返回参数attr
		  var initRes = {
			  obj: {},
			  setAttr: function(key, value){
				  this.obj[key] = value;
			  },
			  getAttr: function(key){
				  return this.obj[key];
			  }
		 };
		 var initPageData = $('#OptimusData').attr('data');
		 if(typeof(initPageData) != 'object'){
			 if(initPageData){
				 initPageData = jazz.stringToJson(initPageData);
			 }
		 }
		 if(initPageData){
			 var jsonData = initPageData['data'];
			 if($.isArray(jsonData)){
					$.each(jsonData, function(i, jsonObj){
						var vtype = jsonObj['vtype'], name = jsonObj['name'];
						if(vtype=='attr'){
							initRes.setAttr(name, jsonObj['data']);
						}
//						if($.inArray(vtype, $.DataAdapter.vtypeset) >= 0){
//							var componentObj = $('div[name="'+name+'"]').data(vtype);
//							if(componentObj){
//								if(vtype=="comboxfield"){
//									componentObj.setOptions(jsonObj);
//								}else{
//									componentObj.setValue(jsonObj);
//								}
//							}
//						}
					});			 
			 }
			 if($.isFunction(window.initData)){
			     //页面初始化数据
			     initData(initRes);
		     }
		 }
     });

})(jQuery);