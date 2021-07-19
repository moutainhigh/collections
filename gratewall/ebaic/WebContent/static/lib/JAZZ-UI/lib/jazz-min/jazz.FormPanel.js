(function($, factory) {

    if (jazz.config.isUseRequireJS === true) {
        define(['jquery',
            'jazz.Panel',
            'form/jazz.form.TextField',
            'form/jazz.form.TextareaField',
            'form/jazz.form.ComboxField',
            'form/jazz.form.ComboxTree',
            'form/jazz.form.AutocompletecomboxField',
            'form/jazz.form.ColorField',
            'form/jazz.form.DateField',
            'form/jazz.form.CheckboxField',
            'form/jazz.form.RadioField',
            'form/jazz.form.NumberField'
        ], factory);
    } else {
        factory($);
    }
})(jQuery, function($) {
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
                cols: 2,
                columnwidth: ['50%', '*']
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
             *@desc 表单元素是否全部显为文本, 功能同readonly, 建议使用readonly属性实现  true显示 false 不显示
             *@default false
             */
            dataview: false,

            /**
             *@type Boolean
             *@desc 是否是可读字段 true只读  false非只读
             *@default false
             */
            readonly: false,
            
            /**
             *@type Boolean
             *@desc 是否可以显示查询条件 true显示  false不显示
             *@default false
             */
            iscondition: false,
            
            /**
             *@type Boolean
             *@desc 当属性iscondition=true时, 查询条件的label名称
             *@default "查询条件"
             */
            searchlabel: "查询条件",
            
    		/**
			 *@desc 当iscondition=true时，增加节点时，触发事件
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").formpanel("option", "conditionaddevent", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("formpanelconditionaddevent",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… conditionaddevent="XXX()"></div> 或 <div…… conditionaddevent="XXX"></div>
			 */	            
            conditionaddevent: null,
            
    		/**
			 *@desc 当iscondition=true时，删除节点时，触发事件
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").formpanel("option", "conditiondeleteevent", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("formpanelconditiondeleteevent",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… conditiondeleteevent="XXX()"></div> 或 <div…… conditiondeleteevent="XXX"></div>
			 */
            conditiondeleteevent: null,
            
    		/**
			 *@desc 当iscondition=true时，点击全部撤销节点时，触发事件
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").formpanel("option", "conditionreseteevent", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("formpanelconditionreseteevent",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… conditionreseteevent="XXX()"></div> 或 <div…… conditionreseteevent="XXX"></div>
			 */            
            conditionreseteevent: null
        },

        /** @lends jazz.formpanel */

        /**
         * @desc 创建组件
         * @private
         */
        _create: function() {
            this._super();
            this.element.addClass("jazz-formpanel");
        },

        /**
         * @desc 初始化组件
         * @private
         */
        _init: function() {
            this._super();
            
            this.content.addClass("jazz-formpanel-content");
            
            if(this.options.iscondition){
            	this.content.prepend("<div class='jazz-formpanel-condition'>" +
            			"<div class='jazz-formpanel-title'>" +
            			"<div class='jazz-formpanel-title-img'></div><div class='jazz-formpanel-title-name'>" + this.options.searchlabel + "</div>" +
            			"</div>" +
            			"<div class='jazz-formpanel-con-c'></div>" +
            			"<div class='jazz-formpanel-con-btn jazz-formpanel-con-btn2'><div class='jazz-formpanel-btn-span'></div></div><div style='clear:both'></div>"+
            			"</div>");
            	this.isconobj = this.content.find(".jazz-formpanel-con-c");
            	var $this = this;
            	$.each(this.getChildrenComponent(), function() {
            		var name = $(this).attr("name");
            		$this.isconobj.append("<div class='jazz-formpanel-con' name='"+name+"_con' id='"+name+"_con'></div>");
            	});
            }

            if (this.options.dataurl) {
                this.reload();
            }

            if (this.options.layout == "auto") {
                this.content.css("overflow", "hidden");
            }
        },

        /**
         * @desc ajax请求函数
         * @return 返回请求响应的数据
         * @private
         */
        _ajax: function() {
            //this.element.loading();
            var param = {
                url: this.options.dataurl,
                params: this.options.dataurlparams,
                async: true,
                showloading: false,
                callback: this._callback //回调函数
            };
            $.DataAdapter.submit(param, this);
        },

        /**
         * @desc ajax请求回调函数
         * @params {data} ajax请求返回的值
         * @params {sourceThis} 当前类对象
         * @private
         */
        _callback: function(data, sourceThis) {
            var jsonData = data;
            var $this = sourceThis;
            if (jsonData && jsonData["data"]) {
                $.each($this.getChildrenComponent(), function(name, obj) {
                    var $obj = $(obj);
                    var childtype = $obj.attr("vtype");
                    var isInited = jazz._isComponentInited($obj),
                        f = $this._filterVtype(childtype);
                    var value = jsonData.data[name];
                    if (isInited && f) {
                        $this._runChildSetValue(childtype, value, obj);
                    }
                });
            }

            if ($.isFunction($this.callbackfunction)) {
                $this.callbackfunction.call($this);
            }
        },

        /**
         * @desc 设置dataview属性
         * @private
         */
        _dataview: function() {
            var $this = this;
            $.each(this.getChildrenComponent(), function(name, obj) {
                var $obj = $(obj);
                var childtype = $obj.attr("vtype");
                var isInited = jazz._isComponentInited($obj),
                    f = $this._filterVtype(childtype);
                if (isInited && f) {
                    $this._readonly($obj.data(childtype));
                }
            });
        },

        /**
         * @desc 过滤掉formpanel中不是表单组件的其他组件
         * @params {vtype} vtype类型
         * @private
         * @returns {Boolean}
         */
        _filterVtype: function(vtype) {
            if (vtype == 'toolbar' || vtype == 'button' || vtype == 'gridpanel') {
                return false;
            } else {
                return true;
            }
        },

        /**
         * @desc 根据type获取元素值
         * @params {name} 表单中的子元素的name值
         * @params {name} 表单中的子元素的name值  
         * @private
         */
        _getProperties: function(type, name) {
            if (!name) {
                var formJson = {};
                formJson["name"] = this.options.name;
                formJson["vtype"] = "formpanel";

                var formdata = {};
                var $this = this;
                $.each(this.getChildrenComponent(), function(childname, obj) {
                    var $obj = $(obj);
                    var childtype = $obj.attr("vtype");
                    var isInited = jazz._isComponentInited($obj),
                        f = $this._filterVtype(childtype);
                    if (isInited && f) {
                        formdata[childname] = $obj[childtype](type);
                    }
                });
                formJson["data"] = formdata;
                return formJson;
            } else {
                var value = "";
                $.each(this.getChildrenComponent(), function(childname, obj) {
                    var $obj = $(obj);
                    var childtype = $obj.attr("vtype");
                    var isInited = jazz._isComponentInited($obj);
                    if (isInited && name == childname) {
                        value = $obj[childtype](type);
                    }
                });
                return value;
            }
        },

        /**
         * @desc 执行子组件的readonly方法
         * @params {comp} 组件对象
         * @private
         */
        _readonly: function(comp) {
            if (this.options.dataview == true || this.options.readonly == true) {
                comp.options.readonly = true;
                comp._readonly();
            } else {
                comp.options.readonly = false;
                comp._readonly();
            }
        },

        /**
         * @desc 执行子组件的setValue方法
         * @params {vtype} 子组件的vtype类型
         * @params {value} 子组件的值   
         * @params {obj} 组件对象
         * @private
         */
        _runChildSetValue: function(vtype, value, obj) {
            if (vtype == 'datefield' && (value + "").length > 10) {
                value = value.substring(0, 10);
            }
            var comp = $(obj).data(vtype);
            comp.setValue(value);
        },

        /**
         * @desc 设置组件属性的值
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
         * @private
         */
        _setOption: function(key, value) {
            switch (key) {
                case 'dataurlparams':
                    this.options.dataurlparams = value;
                    break;
                case 'dataurl':
                    this.options.dataurl = value;
                    break;
                case 'dataview':
                case 'readonly':
                    if (value == true || value == "true") {
                        this.options.dataview = true;
                        this.options.readonly = true;
                    } else {
                        this.options.dataview = false;
                        this.options.readonly = false;
                    }
                    this._dataview();
                    break;
            }
            this._super(key, value);
        },

        /**
         * 设置condition查询条件
         * @param {obj} obj.vtype obj.name obj.text obj.value
         * @private
         */
        _setCondition: function(obj){
        	if(this.isconobj){
        		var that = this;
        		var conobj = this.isconobj.find("div[name='"+obj.options.name+"_con']");
        		if(conobj.length > 0){
        			var v = obj.getValue(), t = obj.getText();
        			if(!obj.options.multiple){//单选,移除其他兄弟
        				conobj.empty();
        				if(v){
        					conobj.addClass("jazz-formpanel-con2");
        					conobj.removeClass("jazz-formpanel-con-height");
        					if(obj.options.label){
        						conobj.append("&nbsp;"+obj.options.label+"：<div class='jazz-formpanel-con-t' value='"+v+"'>"+t+"<div class='jazz-formpanel-con-img'>&nbsp;&nbsp;</div></div>");        						
        					}else{
        						conobj.append("<div class='jazz-formpanel-con-t' value='"+v+"'>"+t+"<div class='jazz-formpanel-con-img'>&nbsp;&nbsp;</div></div>");
        					}
        					this._conditionclick();
        					//obj.element.hide();
        					this._event("conditionaddevent", null);
        				}
        			}else{
        				conobj.empty();
        				var vj = v.split(","), tj = t.split(",");
	        			for(var i=0, len=vj.length; i<len; i++){
	        				if(vj[i]){
	        					if(i==0){
	        						conobj.addClass("jazz-formpanel-con2");
	        						conobj.removeClass("jazz-formpanel-con-height");
	        						if(obj.options.label){
	        							conobj.append("&nbsp;"+obj.options.label+"：<div class='jazz-formpanel-con-t' value='"+vj[i]+"'>"+tj[i]+"<div class='jazz-formpanel-con-img'>&nbsp;&nbsp;</div></div>");	        							
	        						}else{
	        							conobj.append("<div class='jazz-formpanel-con-t' value='"+vj[i]+"'>"+tj[i]+"<div class='jazz-formpanel-con-img'>&nbsp;&nbsp;</div></div>");
	        						}
	        						this._conditionclick();
	        					}else{
	        						conobj.append("<div class='jazz-formpanel-con-t' value='"+vj[i]+"'>"+tj[i]+"<div class='jazz-formpanel-con-img'>&nbsp;&nbsp;</div></div>");	        						
	        					}
	        				}
	        				if((i+1)==len){
	        					this._event("conditionaddevent", null);
	        				}
	        			}
        			}
        			$.each(conobj.find(".jazz-formpanel-con-img"), function(){
        				var $this = $(this);
        				$(this).on("click", function(){
        					$this.parent().remove("");
        					var cn = conobj.children().length;
        					if(cn==0){
        						conobj.removeClass("jazz-formpanel-con2");
        						conobj.addClass("jazz-formpanel-con-height");
        						conobj[0].innerHTML = "";
        						if(that.isconobj.find(".jazz-formpanel-con-t").length==0){
        							var nobj = that.isconobj.next();
        							nobj.off("click");
        							nobj.addClass("jazz-formpanel-con-btn2");
        						};
        						obj.element.show();
        					};
        					var _value = "";
        					$.each(conobj.children(".jazz-formpanel-con-t"), function(i){
        						if(i==0){
        							_value = $(this).attr("value");
        						}else{
        							_value += ","+ $(this).attr("value");
        						}
        					});

        					//重新设置值
        					obj.setValue(_value);
        					
        					that._event("conditiondeleteevent", null, {name: obj.options.name, num: cn});

        				});
        			});
        			
        		}
        		
        		
        	}
        },
        
        _conditionclick: function(){
			var $this = this;
        	var nobj = this.isconobj.next();
        	nobj.removeClass("jazz-formpanel-con-btn2");
			nobj.off("click").on("click", function(){
				$this.reset();
				$this._conditionrest(nobj);
                $.each($this.getChildrenComponent(), function(childname, obj) {
                	var $obj = $(obj);
                    var childtype = $obj.attr("vtype");
                    if(childtype && childtype!="hiddenfield"){
                    	$(this).show();
                    }
                });
				$this._event("conditionreseteevent", null);
			});
        },
        
        _conditionrest: function(nobj){
			$.each(this.isconobj.find(".jazz-formpanel-con"), function(){
				$(this).addClass("jazz-formpanel-con-height");
				$(this).empty();
				$(this).removeClass("jazz-formpanel-con2");
			});
			nobj.addClass("jazz-formpanel-con-btn2");
        },
        
        /**
         * @desc 获取表单元素数据
         * @params {name} 表单中的子元素的name值
         * @return  String or Json
         * @example  $('#formpanel_id').formpanel('getValue','inputname','username'); 获取单个name=username 的元素值
         * @example  $('#formpanel_id').formpanel('getValue'); 获取整个表单的值
         */
        getValue: function(name) {
            return this._getProperties('getValue', name);
        },

        /**
         * @desc 获取表单元素数据
         * @params {name} 表单中的子元素的name值
         * @return  String or Json
         * @example  $('#formpanel_id').formpanel('getText','inputname','username'); 获取单个name=username 的元素值
         * @example  $('#formpanel_id').formpanel('getText'); 获取整个表单的值
         */
        getText: function(name) {
            return this._getProperties('getText', name);
        },

        /**
         * @desc 初始化表单数据，依据dataurl
         * @params {data} 加载的JSON数据，暂时不支持
         * @params {callbackfunction} 回调函数 
         * @example $('#formpanel_id').formpanel('reload', null, function(){ …… }); 
         */
        reload: function(data, callbackfunction) {
            this.callbackfunction = callbackfunction;
            if (this.options.dataurl) {
                this._ajax();
            }
        },

        /**
         * @desc 表单重置
         * @params {flag} 布尔值，true时情况hiddenfield隐藏域的值，默认情况下不清除
         * @example  $('#formpanel_id').formpanel('reset'); $('#formpanel_id').formpanel('reset',true);
         */
        reset: function(flag) {
            $.each(this.getChildrenComponent(), function(i, obj) {
                var $obj = $(obj);
                var childtype = $obj.attr("vtype");
                if (childtype != 'toolbar' && childtype != 'button') {
                    if (!flag && childtype == 'hiddenfield') {
                        return true;
                    }
                    var isInited = jazz._isComponentInited($obj);
                    if (isInited) {
                        $obj[childtype]('reset');
                    }
                }
            });
        },

        /**
         * @desc 设置from表单元素值
         * @params {name} 参数为空时，设置表单的json数据，name不为空时，value设置form下元素的值
         * @params {value} 表单元素值
         * @example  $('#formpanel_id').formpanel('setValue','inputname','username'); 设置单个name=username 的元素值
         * @example  $('#formpanel_id').formpanel('setValue','name'); 设置整个表单的值
         */
        setValue: function(name, value) {
            if (name instanceof Object) {
                this.options.data = name;
                this._callback(this.options.data, this);
            } else {
                $.each(this.getChildrenComponent(), function(childname, obj) {
                    var $obj = $(obj);
                    var childtype = $obj.attr("vtype");
                    var isInited = jazz._isComponentInited($obj);
                    if (childname == name && isInited) {
                        $this._runChildSetValue(childtype, value, obj);
                    }
                });
            }
        },

        /**
         * @desc 校验formpanel中表单元素的rule规则
         * @param {[exceptElementName]} formpanel中排除校验的元素名称
         * @return {boolean} 通过校验返回true，否则为false
         * @public
         */
        validate: function() {
            var flag = true;

            //校验当前formpanel中的子元素
            //通过(div[vtype])找出所有子元素
            //避免通过this.getChildrenComponent()丢失子元素
            var subElements = this.element.find('div[vtype]');
            if (subElements.length == 0) {
                //无子元素的时候，默认校验通过

            } else {
                subElements.each(function(index, element) {
                    var el = $(element);
                    var vtype = el.attr('vtype'),
                        $field = el.data(vtype);
                    var options = $field.options;
                    var rule = options.rule;
                    if (rule && vtype != "gridpanel" && vtype != "hiddenfield" && vtype != "checkboxfield" && vtype != "radiofield" && options.isrule) { //过滤掉formpanel中嵌套的gridpanel
                        var msg = options.msg;
                        var f = jazz.doTooltip($field, $field.getText(), rule, msg);
                        if (f == false) {
                            flag = false;
                            $field._validateStyle(f);
                        }
                    }
                });
            }

            return flag;
        }

    });

});
