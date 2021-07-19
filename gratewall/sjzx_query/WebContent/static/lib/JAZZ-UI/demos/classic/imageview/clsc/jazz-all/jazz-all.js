/**
 * jazz.core
 */
(function($) {
    window.undefined = window.undefined;

    var jazz = {
        /**
         * The version of the framework
         * @type String
         */
        version: '1.0',

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
            for (var i = (levels[0] == "jazz") ? 1 : 0; i < levels.length; ++i) {
                nsobj[levels[i]] = nsobj[levels[i]] || {};
                nsobj = nsobj[levels[i]];
            }
            return nsobj;
        },

        /**
         * 日志
         * @param {info} 打印的日志内容
         */
        log: function(info) {
            if (this.config.logger) {
                return console.log('***JAZZ-UI LOG.INFO***\r\n', info);
            } else {
                return false;
            }
        }

    };

    jazz.namespace("layout");

    jazz.namespace("config");

    jazz.namespace("util");

    jazz.namespace("window");

    window.jazz = jazz;

})(jQuery);

/**
 * jazz.core
 * jazz.util
 * jazz.config
 */
(function($) {
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
     * 内部方法，建立当前组件与上级组件的关系
     */
    var _parse = function(element) {
        // 查找元素的parent节点
        var parent = element.parents("div[vtype]:first");
        // 如果没找到，设置为BODY
        if (parent.size() == 0) {
            parent = $("BODY");
        }

        // 设置父节点的data中的child
        var parentData = parent.data("vtypetree") || {
            parent: "",
            child: {}
        };
        var childName = element.attr("name");
        parentData.child[childName] = element;
        parent.data("vtypetree", parentData);

        // 设置当前节点中的parent的值和vType的值
        var nodeData = element.data("vtypetree") || {
            parent: "",
            child: {}
        };
        nodeData["parent"] = parent;
        nodeData["vType"] = element.attr("vType");
        element.data("vtypetree", nodeData);
        _vtype.push(nodeData['vType']);
        /**
         * 检查元素的代码集
         */
        if (element.attr("dataurl")) {
            return [element.attr("dataurl")];
        }

        /**
         * 处理gridcolumn中的代码集
         */
        var valueSet = [];
        if ('gridcolumn' === element.attr("vtype")) {
            element.children().children().each(function() {
                if ($(this).attr("dataurl"))
                    valueSet.push($(this).attr("dataurl"));
            });
            return valueSet;
        }


    };

    /**
     * 初始化vtyle节点
     * （一次只初始化一个，先把之间初始化完成后，再初始化子节点）
     */
    var _initComponent = function(element, options) {
        var nodeData = element.data("vtypetree") || {};
        // 如果当前元素有vType属性，
        if (!!nodeData.vType) {
            // 判断UI组件是否已经初始化，如果未初始化，才执行初始化。
            var isInited = $(element).isComponentInited();
            if (!isInited) {
                // 初始化UI组件之前，先判断相关JS文件是否已经引入，如果未引入，动态加载进来。
                // loadResource();暂不实现

                // 初始化UI组件。$(element).vType(options);
                var attrtooptions = jazz.attributeToOptions($(element));
                if (!!options) {
                    attrtooptions = $.extend(attrtooptions, options);
                }
                ($(element)[nodeData.vType])(attrtooptions);

                //jazz.log("初始化" + element.attr("name") + "(" + nodeData.vType + ")" + "组件");
            }
        };
        var childNodes = nodeData.child;
        // 如果存在childNodes，初始化子元素
        if (!!childNodes) {
            for (var c in childNodes) {
                _initComponent(childNodes[c]);
            }
        }
    };

    $.fn.parse = function(options) {
        var $this = $(this);
        _initComponent($this, options);
    },

    /**
     * vtype树操作：解析当前元素及子元素的vType，形成vType树，并初始化相关组件
     */
        $.fn.parseComponent = function(options) {

            var $this = $(this);
            var links = [],
                ret;
            // 1.解析生成vType树
            // 如果当前元素具有vtype属性，先解析当前的元素
            if ($this.attr("vtype")) {
                ret = _parse($this);
                if (ret && ret.length) {
                    links = links.concat(ret);
                }
            }

            // 解析元素的子元素
            $this.find("div[vtype]").each(function() {
                ret = _parse($(this));
                if (ret && ret.length) {
                    links = links.concat(ret);
                }
            });

            //jazz.log( "获取vType树耗时：" + (endTime1-startTime)+"ms" );

            // 2.根据vType树初始化相关组件（从vType树的根节点（BODY）开始，深度优先算法）
            _initComponent($this, options);

            // 3. 发送请求代码集
            if (window.G) {
                G.processData(links, true);
            } else {
                //jazz.log("这是代码集组件还未初始化完成");
            }
        };

    /**
     * 初始化vType组件
     */
    $.fn.initComponent = function() {
        $("BODY").parseComponent();
    };

    /**
     * 判断组件是否已经执行初始化
     */
    $.fn.isComponentInited = function() {
        var nodeData = $(this).data("vtypetree") || {};
        if (!!nodeData.vType) {
            return !!$(this).data(nodeData.vType);
        }
        nodeData = null;
        return false;
    };

    /**
     * vtype树操作：获取当前节点的父节点
     */
    $.fn.getParentComponent = function() {
        var nodeData = $(this).data("vtypetree") || {};
        return nodeData.parent;
    };

    /**
     * vtype树操作：获取当前节点的子节点
     */
    $.fn.getChildrenComponent = function() {
        var nodeData = $(this).data("vtypetree") || {};
        return nodeData.child;
    };

    /**
     * vtype树操作：获取当前节点的指定vtype类型子节点
     */
    $.fn.getChildrenComponentByVtype = function(vtype) {
        var element = null;
        var nodeData = $(this).data("vtypetree") || {};
        for (var childname in nodeData.child) {
            var data = nodeData.child[childname].data("vtypetree");
            if (data.vType == vtype) {
                element = nodeData.child[childname];
                break;
            }
        }
        return element;
    };

    /**
     * 页面初始化完成后，执行initComponent操作
     */
    $(function() {
        $("BODY").initComponent();
    });


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
        attributeToOptions: function(obj) {
            var att = null;
            if (obj instanceof jQuery) {
                if (obj.size() < 1) {
                    jazz.log("未找到满足条件的对象，无法获取全部属性，直接返回空json");
                    return {};
                }
                att = obj.get(0).attributes;
            } else {
                att = obj.attributes;
            }
            var j = 0;
            var attObj = '{';
            for (var i = 0, len = att.length; i < len; i++) {
                var o = att[i];
                if (o.specified) {
                    var _v = jQuery.trim(o.value + ''),
                        f = 0;
                    if (_v.length > 0) {
                        _v = _v.replace(/[\r\n|\t]/g, " ");
                        //if(/^\s*[\[|{](.*)[\]|}]\s*$/.test(_v)){
                        if ((_v + "").charAt(0) == "[" || (_v + "").charAt(0) == "{") {
                            f = 1;
                        }
                    } else {
                        _v = "";
                    }
                    if (j === 0) {
                        attObj = attObj + '"' + o.name + '": ';
                    } else {
                        attObj = attObj + ', "' + o.name + '": ';
                    }
                    if (f == 0) {
                        attObj = attObj + '"' + _v + '"';
                    } else {
                        attObj = attObj + _v;
                    }
                    j++;
                }
            }
            attObj = attObj + '}';
            return jazz.stringToJson(attObj);
        },


        /**
         * @desc 调用确认提示框
         * @param {message} 提示的信息内容
         * @param {sure} function 点击确定按钮的回调函数
         * @param {cancel} function 点击取消按钮的回调函数
         * @example jazz.util.info("提示信息内容！", function(){  });
         */
        confirm: function(message, sure, cancel) {
            var that = this;
            $('<div>').appendTo(that.getBodyObject()).confirm({
                detail: message,
                sure: sure,
                cancel: cancel
            });

        },

        /**
         * @desc 表单验证出错时调用的提示信息
         * @param {$this} 组件类对象
         * @param {val} 输入框输入值
         * @param {rule} 验证规则
         * @param {regMsg} 自定义函数显示消息
         * @return Boolean
         */
        doTooltip: function($this, val, rule, regMsg) {
            var ruleImg = $this.ruleImg,
                ruleType = $this.options.ruletype;
            obj = jazz_validator.doValidator(val, rule, regMsg);
            if ($this.options.msg) {
                obj['msg'] = $this.options.msg;
            }
            if (ruleType == 2) {
                $this.inputtext.tooltip({
                    isbindevent: false,
                    content: obj.msg,
                    iconclass: 'jazz-tooltip-default-icon',
                    position: {
                        at: 'left bottom',
                        my: 'left top',
                        collision: 'flipfit flipfit',
                        of: $this.inputtext
                    }
                });
            }
            if (!obj.state) {
                //$this.inputtext.addClass('jazz_validator');
                $this.parent.addClass('jazz_validator_border');
                if (ruleType != 2) {
                    ruleImg.removeClass('jazz-helper-hidden');
                    ruleImg.attr("title", obj.msg);
                } else if (ruleType == 2) {
                    $this.inputtext.tooltip("show");
                    $this.inputtext.off("mouseover.tooltip mouseout.tooltip blur.tooltip")
                        .on("mouseover.tooltip", function() {
                            $this.inputtext.tooltip("show");
                        })
                        .on("mouseout.tooltip", function() {
                            $this.inputtext.tooltip("hide");
                        })
                        .on("blur.tooltip", function() {
                            $this.inputtext.tooltip("hide");
                        });
                }
                $this.options.validationstate = false;
            } else {
                //$this.inputtext.removeClass('jazz_validator');
                $this.parent.removeClass('jazz_validator_border');
                if (ruleType != 2) {
                    ruleImg.addClass('jazz-helper-hidden');
                } else if (ruleType == 2) {
                    $this.inputtext.off("mouseover.tooltip mouseout.tooltip blur.tooltip");
                    $this.inputtext.tooltip("hide");
                }
                $this.options.validationstate = true;
            }

            return obj.state;
        },

        //      escapeHTML: function(value) {
        //          return value.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
        //      },

        /**
         * @desc 转义正则字符串
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
         * @example jazz.util.info("提示信息内容！", function(){  });
         */
        error: function(message, sure) {
            var that = this;
            if (jazz.config.errorMessageNumber == 0) {
                $('<div>').appendTo(that.getBodyObject()).error({
                    detail: message,
                    sure: sure
                });
            }
        },

        /**
         * @desc 返回当前窗体的body象
         * @return Object
         */
        getBodyObject: function() {
            if (!this.$bodyobject) {
                this.$bodyobject = $('body');
            }
            return this.$bodyobject;

            //      var top = this.getTop();
            //      if(!top.jazz.$bodyobject){
            //        top.jazz.$bodyobject = top.document.body;
            //      }
            //      return top.jazz.$bodyobject;
        },

        /**
         * @desc 获取通过http://localhost:8080/JAZZ?params=123传入当前页面的参数
         * @param {name} 参数名
         * @example var paramsValue = jazz.util.getParameter("params");
         * @return String
         * @public
         */
        getParameter: function(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) {
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
        getParameters: function() {
            var url = window.location.search;
            var params = {};
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                var strs = str.split("&");
                for (var i = 0, len = strs.length; i < len; i++) {
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
        getTop: function() {
            var dom = window;
            if (dom.top.jazz) {
                dom = dom.top.window;
            } else {
                while (dom.parent) {
                    if (dom.parent == dom) {
                        break;
                    }
                    dom = dom.parent;
                }
            }
            if (dom.jazz) {
                return dom;
            }
            return dom;
        },

        /**
         * @desc 在指定的数组中，是否存在要查找的记录
         * @param {arr} 指定查找的数组
         * @param {item} 要查找的记录
         * @return Boolean
         * @example jazz.util.inArray([1,2,4,5,9,6], 5);
         */
        inArray: function(arr, item) {
            for (var i = 0, len = arr.length; i < len; i++) {
                if (arr[i] === item) {
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
        info: function(message, sure) {
            var that = this;
            $('<div>').appendTo(that.getBodyObject()).info({
                detail: message,
                sure: sure
            });
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
        isDate: function(v) {
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
        isNumber: function(n) {
            return $.isNumeric(n);
        },

        /**
         * @desc 把json对象转换成字符串
         * @param {o} 参数
         * @return String
         * @example jazz.util.jsonToString(JSON);
         */
        jsonToString: function(o) {
            if (typeof o == "undefined" || o === null) {
                return "null";
            } else if (jazz.isArray(o)) { //数组
                var a = ["["],
                    b, i, l = o.length,
                    v;
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
            } else if (jazz.isDate(o)) { //日期对象
                return '"' + o.getFullYear() + "-" +
                    jazz.pad(o.getMonth() + 1) + "-" +
                    jazz.pad(o.getDate()) + " " +
                    jazz.pad(o.getHours()) + ":" +
                    jazz.pad(o.getMinutes()) + ":" +
                    jazz.pad(o.getSeconds()) + '"';
            } else if (typeof o == "string") { //字符串,转义回车换行,双引号,反斜杠...等
                var m = {
                    "\b": '\\b',
                    "\t": '\\t',
                    "\n": '\\n',
                    "\f": '\\f',
                    "\r": '\\r',
                    '"': '\\"',
                    "\\": '\\\\'
                };
                if (/["\\\x00-\x1f]/.test(o)) {
                    return '"' + o.replace(/([\x00-\x1f\\"])/g, function(a, b) {
                        var c = m[b];
                        if (c) {
                            return c;
                        }
                        c = b.charCodeAt();
                        return "\\u00" + Math.floor(c / 16).toString(16) + (c % 16).toString(16);
                    }) + '"';
                }
                return '"' + o + '"';
            } else if (typeof o == "number") {
                return isFinite(o) ? String(o) : "null";
            } else if (typeof o == "boolean") {
                return String(o);
            } else { //json格式的对象
                var a = ["{"],
                    b, i, v;
                for (i in o) {
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
                            a.push(jazz.jsonToString(i), ":", v === null ? "null" : jazz.jsonToString(v));
                            b = true;
                    }
                }
                a.push("}");

                //a.join("").replace(/\s+/g," ");  /&nbsp;/ig

                return a.join("").replace(/&nbsp;/ig, " ");
            }
        },

        /**
         * @desc 根据传入的数字判断，如果字段为小于10的数字，则前补0  例如： 传入 5  得到  05
         * @param {n} 判断的数字
         * @return String
         * @public
         * @example jazz.util.pad(5);
         */
        pad: function(n) {
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
        parseDataByDataFormat: function(formatParams) {

            if (!formatParams) {
                return "";
            }
            var cellvalue = formatParams["cellvalue"];
            var datatype = formatParams["datatype"];
            var dataformat = formatParams["dataformat"];
            if (cellvalue == "" || cellvalue == undefined || cellvalue == null) {
                return "";
            }
            if (!datatype) {
                return cellvalue;
            }
            //分别不同datatype，解析cellvalue
            var showvalue = "";
            switch (datatype) {
                case "date":
                    cellvalue = cellvalue.replace(/\.0*$/i, '');
                    //showvalue = jazz.dateformat.formatDateToString(cellvalue,dataformat);
                    showvalue = jazz.dateformat.formatStringToString(cellvalue, "yyyy-MM-dd HH:mm:ss", dataformat);
                    break;
                case "number":
                    //showvalue = jazz.dateformat.formatDateToString(cellvalue,dataformat);
                    showvalue = cellvalue;
                    break;
                default:
                    showvalue = cellvalue;
            }
            return showvalue;
        },

        /**
         * @desc 把字符串转换成json对象
         * @param {string}
         * @return Object
         * @public
         * @example jazz.util.stringToJson("{a: 1, b: 2}");
         */
        stringToJson: function(string) {
            if (string) {
                if (string instanceof Object) {
                    return {};
                } else {
                    return eval("(" + string + ")");
                }
            } else {
                return {};
            }
        },

        /**
         * @desc 调用警告信息框
         * @param {message} 提示的信息内容
         * @param {sure} function 点击确定按钮的回调函数
         * @example jazz.util.info("提示信息内容！", function(){  });
         */
        warn: function(message, sure) {
            var that = this;
            if (jazz.config.errorMessageNumber == 0) {
                $('<div>').appendTo(that.getBodyObject()).warn({
                    detail: message,
                    sure: sure
                });
            }
        },

        /**
         * @desc 获取当前窗口的高度
         * @return Number
         * @example jazz.util.windowHeight();
         */
        windowHeight: function() {
            if ($.browser.msie) {
                return document.compatMode == "CSS1Compat" ? document.documentElement["clientHeight"] : document.body.clientHeight;
            } else {
                return self.innerHeight;
            }
            //      var docElemProp = document.documentElement[ "clientHeight" ];
            //      return document.compatMode === "CSS1Compat" && docElemProp || document.body[ "clientHeight" ] || docElemProp ;
        },

        /**
         * @desc 获取当前窗口的宽度
         * @return Number
         * @example jazz.util.windowWidth();
         */
        windowWidth: function() {
            if ($.browser.msie) {
                return document.compatMode == "CSS1Compat" ? document.documentElement.clientWidth : document.body.clientWidth;
            } else {
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
        close: function(e) {
            var iframeNumber = 0;
            var target = e.target,
                $target = $(target);
            var tParent = $target.parent();

            function _findWindow(tParent) {
                var t = tParent;
                if (t.is("body")) { //如果为真，说明需要到父页面查找
                    var frameElement = window.frameElement; //获取iframe对象
                    if (frameElement) {
                        var _divObj = $(frameElement).parent();
                        var $div = $(_divObj);
                        iframeNumber++;
                        _findWindow($div);
                    } else {
                        jazz.log("jazz.window.close frame error.");
                        return;
                    }
                } else {
                    if (t.hasClass("jazz-window")) {
                        var dom = window;
                        try {
                            if (dom.top.$(t[0]).data("window")) { //顶层页面是否存在
                                dom.top.$(t[0]).data("window").close();
                                return;
                            } else { //顶层页面不存在，使用向上递归的方法，逐级向上判断
                                while (dom) {
                                    if (dom.$(t[0]).data("window")) {
                                        dom.$(t[0]).data("window").close();
                                        return;
                                    } else {
                                        dom = dom.parent;
                                    }
                                }
                            }
                        } catch (ex) {
                            jazz.log("jazz.window.close top error.");
                            return;
                        }
                    } else {
                        if (t.parent()) {
                            _findWindow(t.parent());
                        }
                    }
                }
            }

            _findWindow(tParent);
        }
    };

    $.extend(jazz, jazz.util);

    /**
     * jazz.config
     */

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

        /**表单字段中的默认高度  **/
        fieldDefaultHeight: 26,

        /**表单字段中的label字段宽度  **/
        fieldLabelWidth: 80,

        /**请求代码集时,是否合并相同请求**/
        isGroupRequest: false,
        /**输出日志开关 **/
        logger: true,

        /****/
        maxZoom: 1,

        /**(sword 平台)  other(其他) **/
        platForm: 'other',

        /**控制textareafield组件的前缀和后缀显示的位置  0 左右显示 1 上下显示**/
        prefixPosition: 0,

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

        /**图片缩放是递增的差值**/
        zoomStep: 0.1,

        /**
         * @version 0.5
         * @name i18n
         * @description 参数类。
         */
        i18n: {
            chooseFile: "请选择文件",
            password: "请输入密码",
            wait: "请稍后...",
            fileName: "文件名",
            fileSize: "文件大小",
            fileDelete: "删除",
            fileDownload: "下载",
            fileEdit: "编辑",
            fileAffix: "附件",
            fileAdd: "增加",
            fileCancel: "取消",
            number: '数字',
            numberInt: '整数',
            numberFloat: '浮点数',
            numberPlusInt: '大于等于0的整数',
            months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一', '十二'],
            days: ['日', '一', '二', '三', '四', '五', '六'],
            dateOrder: ['年', '月', '日', '时', '分', '秒'],
            bizSucMsg: "校验成功!",
            bizFaiMsg: "校验失败!",
            sysMsg: "系统错误!",
            saveSuc: "保存成功!",
            saveFai: "保存失败!",
            save: "提交",
            cancel: "取消",
            button: "按钮",
            firstPage: "首页",
            endPage: "末页",
            nextPage: "下一页",
            previousPage: "上一页"

            ,
            titleName: "提示框",
            okBtnName: "确定",
            cancelBtnName: "取消",
            defineBtnName: "自定义",
            boxMin: "最小化",
            boxMax: "最大化",
            boxNatural: "正常化",
            boxClose: "关闭"

            ,
            selectLoading: "正在加载数据请稍后..."

            ,
            toolEdit: "编辑",
            toolNew: "新建",
            toolDel: "删除",
            toolFresh: "刷新",
            toolOpen: "打开",
            toolFind: "查找",
            toolSave: "保存",
            toolBack: "返回",
            toolExport: "导出",
            toolAddtime: "增加时间",
            toolReducetime: "减少时间",
            toolAddright: "授权",
            toolReduceright: "撤权",
            num: "数字",
            numInt: "整数",
            numFloat: "浮点数",
            numScience: "科学计数法",
            character: "字符",
            chinese: "汉字",
            twoBytes: "双字节",
            english: "英文",
            date: "日期格式不正确",
            numChar: "数字或字符",
            numEnglish: "数字英文字符",
            qq: "QQ号码",
            'telephone': '座机号码',
            'cellphone': '移动电话码',
            'idcard': '请输入正确的身份证号码',
            'postal': '邮政编码',
            'currency': '美元',
            'email': '邮箱地址',
            'url': 'URL地址',
            'and1': ',且',
            'or': ',或者',
            'must': '不能为空',
            'contrast': '数值',
            'range': '数值范围为',
            'customCheckStyle': '不允许输入以下字符',
            'length1': '字符串长度为',
            'customFunction': '自定义校验',
            area: ['', '', '', '', '', '', '', '', '', '', '', '北京', '天津', '河北', '山西', '内蒙古', '', '', '', '', '', '辽宁', '吉林', '黑龙江', '', '', '', '', '', '', '', '上海', '江苏', '浙江', '安微', '福建', '江西', '山东', '', '', '', '河南', '湖北', '湖南', '广东', '广西', '海南', '', '', '', '重庆', '四川', '贵州', '云南', '西藏', '', '', '', '', '', '', '陕西', '甘肃', '青海', '宁夏', '新疆', '', '', '', '', '', '台湾', '', '', '', '', '', '', '', '', '', '香港', '澳门', '', '', '', '', '', '', '', '', '国外'],
            'tabMenuFresh': '刷新',
            'tabMenuClose': '关闭当前项',
            'tabMenuCloseAll': '关闭所有项',
            'tabMenuCloseOthers': '关闭其他项'
        }
    };

    var _config = window.JAZZUICONFIG || {};
    $.extend(jazz.config, _config);

})(jQuery);

(function($) {
    // Private array of chars to use
    var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');

    Math.uuid = function(len, radix) {
        var chars = CHARS,
            uuid = [],
            i;
        radix = radix || chars.length;

        if (len) {
            // Compact form
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
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
                    r = 0 | Math.random() * 16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }

        return uuid.join('');
    };

    // A more performant, but slightly bulkier, RFC4122v4 solution.  We boost performance
    // by minimizing calls to random()
    Math.uuidFast = function() {
        var chars = CHARS,
            uuid = new Array(36),
            rnd = 0,
            r;
        for (var i = 0; i < 36; i++) {
            if (i == 8 || i == 13 || i == 18 || i == 23) {
                uuid[i] = '-';
            } else if (i == 14) {
                uuid[i] = '4';
            } else {
                if (rnd <= 0x02) rnd = 0x2000000 + (Math.random() * 0x1000000) | 0;
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
            var r = Math.random() * 16 | 0,
                v = c == 'x' ? r : (r & 0x3 | 0x8);
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
                /*if (width !== data.w || height !== data.h) {
                 elem.trigger(str_resize, [data.w = width, data.h = height]);
                 }*/
                if (data) { //&& data.w && data.h
                    if (width !== data.w || height !== data.h) {
                        elem.trigger(str_resize, [data.w = width, data.h = height]);
                    }
                }
            });
            loopy();
        }, jq_resize[str_delay]);
    }

})(jQuery, this);
(function() {
    $(document).off('mousedown').on('mousedown', function(e) {
        var target = e.target,
            $target = $(target);
        var droppanels = $("div.jazz-dropdown-panel.jazz-widget-content.jazz-helper-hidden");
        $.each(droppanels, function() {
            if ($(this).is(":hidden")) {
                return;
            }
            var dropvtype = $(this).attr("type");

            if (dropvtype == 'datefield' || dropvtype == 'colorfield' || dropvtype == 'autocompletecomboxfield' || dropvtype == 'comboxfield' || dropvtype == 'comboxtreefield') {
                var dropname = $(this).attr("name").substring(14);
                var dropinput = $("div[name=" + dropname + "]").data(dropvtype).inputtext;
                var droptrigger = $("div[name=" + dropname + "]").data(dropvtype).trigger;
                if (target == dropinput.get(0) || target == droptrigger.get(0)) {
                    return;
                }
                var dropprefix = $("div[name=" + dropname + "]").data(dropvtype).prefix;
                if (dropprefix) {
                    if (target == dropprefix.get(0)) {
                        return;
                    }
                }
                var dropsuffix = $("div[name=" + dropname + "]").data(dropvtype).suffix;
                if (dropsuffix) {
                    if (target == dropsuffix.get(0)) {
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
                if (dropvtype == 'datefield' || dropvtype == 'colorfield' || dropvtype == 'autocompletecomboxfield' || dropvtype == 'comboxfield' || dropvtype == 'comboxtreefield') {
                    var data = $("div[name=" + dropname + "]").data(dropvtype)._getData();
                    $("div[name=" + dropname + "]").data(dropvtype)._event("change", e, data);
                    var oldvalue = $("div[name=" + dropname + "]").data(dropvtype).getValue();
                    $("div[name=" + dropname + "]").data(dropvtype).oldchoices = oldvalue + "";
                    if (dropvtype == 'colorfield') {
                        $("div[name=" + dropname + "]").data(dropvtype).setValue(oldvalue);
                    }
                }
            }
        });
    });
})();

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
            number: /^[-]?\d+(\.\d+)?([Ee][-+]?[1-9]+)?$/i,
            numberInt: /^[-]?\d+$/i,
            numberFloat: /^[-]?\d+\.\d+$/i,
            numberPlusInt: /^\d+$/i,
            //numberScience:/^[+|-]?\d+\.?\d*[E|e]{1}[+]{1}\d+$/,
            character: /^[\u4e00-\u9fa5A-Za-z]+$/i,
            chinese: /^[\u4e00-\u9fa5]+$/i,
            twoBytes: /^[^\x00-\xff]+$/i,
            english: /^[A-Za-z]+$/i,
            number$character: /^[\u4e00-\u9fa5A-Za-z0-9]+$/i,
            number$english: /^[\w]+$/i,
            qq: /^[1-9]\d{5,11}$/i,
            telephone: /^((\(0\d{2,3}\))|(0\d{2,3}-))?[1-9]\d{6,7}(-\d{1,4})?$/i,
            cellphone: /^0?1\d{10}$/i,
            postal: /^\d{6}$/i,
            currency: /^\$[-+]?\d+(\.\d+)?([Ee][-+]?[1-9]+)?$/i,
            email: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/i,
            url: /^(http|https|ftp):\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/i
        },

        /**
         *@type Object
         *@desc 提示信息
         */
        msg: {
            't': '',
            'number': jazz.config.i18n.num,
            'numberInt': jazz.config.i18n.numInt,
            'numberFloat': jazz.config.i18n.numFloat,
            'numberPlusInt': jazz.config.i18n.numberPlusInt,
            'numberScience': jazz.config.i18n.numScience,
            'character': jazz.config.i18n.character,
            'chinese': jazz.config.i18n.chinese,
            'twoBytes': jazz.config.i18n.twoBytes,
            'english': jazz.config.i18n.english,
            'date': jazz.config.i18n.date,
            'number$character': jazz.config.i18n.numChar,
            'number$english': jazz.config.i18n.numEnglish,
            'qq': jazz.config.i18n.qq,
            'telephone': jazz.config.i18n.telephone,
            'cellphone': jazz.config.i18n.cellphone,
            'idcard': jazz.config.i18n.idcard,
            'postal': jazz.config.i18n.postal,
            'currency': jazz.config.i18n.currency,
            'email': jazz.config.i18n.email,
            'url': jazz.config.i18n.url,
            'and': jazz.config.i18n.and1,
            'or': jazz.config.i18n.or,
            'solo': '',
            'must': jazz.config.i18n.must,
            'contrast': jazz.config.i18n.contrast,
            'range': jazz.config.i18n.range,
            'customCheckStyle': jazz.config.i18n.customCheckStyle,
            'length': jazz.config.i18n.length1,
            'customFunction': jazz.config.i18n.customFunction
        },

        /**
         * @desc 身份证信息
         * @param {idcard} 需要验证的字符串
         * @return boolean
         * @example this.checkIdcard(idcard)
         */
        checkIdcard: function(idcard) {
            var area = {
                11: "北京",
                12: "天津",
                13: "河北",
                14: "山西",
                15: "内蒙古",
                21: "辽宁",
                22: "吉林",
                23: "黑龙江",
                31: "上海",
                32: "江苏",
                33: "浙江",
                34: "安徽",
                35: "福建",
                36: "江西",
                37: "山东",
                41: "河南",
                42: "湖北",
                43: "湖南",
                44: "广东",
                45: "广西",
                46: "海南",
                50: "重庆",
                51: "四川",
                52: "贵州",
                53: "云南",
                54: "西藏",
                61: "陕西",
                62: "甘肃",
                63: "青海",
                64: "宁夏",
                65: "新疆",
                71: "台湾",
                81: "香港",
                82: "澳门",
                91: "国外"
            };
            var idcard, Y, JYM;
            var S, M;
            var idcard_array = new Array();
            idcard_array = idcard.split("");

            //地区检验
            if (area[parseInt(idcard.substr(0, 2))] == null) return false;

            //身份号码位数及格式检验
            switch (idcard.length) {
                case 15:
                    if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0)) {
                        ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //测试出生日期的合法性
                    } else {
                        ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; //测试出生日期的合法性
                    }

                    if (ereg.test(idcard)) return true;
                    else return false;
                    break;

                case 18:
                    //18位身份号码检测
                    //出生日期的合法性检查
                    //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
                    //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
                    if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
                        ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; //闰年出生日期的合法性正则表达式
                    } else {
                        ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; //平年出生日期的合法性正则表达式
                    }
                    if (ereg.test(idcard)) { //测试出生日期的合法性
                        //计算校验位
                        S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 + parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9]) * 3;
                        Y = S % 11;
                        M = "F";
                        JYM = "10X98765432";
                        M = JYM.substr(Y, 1); //判断校验位
                        if (M == idcard_array[17]) return true; //检测ID的校验位
                        else return false;
                    } else return false;
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
        numberDefine: function(val, zl, xl) {
            var value = val;
            if (value == "") return true;
            if (!this.reg.number.test(value)) return false;
            var valueStrArray = value.split(".");
            var zlength = zl;
            if (valueStrArray.length == 2)
                return valueStrArray[0].length <= zlength && valueStrArray[1].length <= xl;
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
            var reList = value;
            for (var i = 0; i < text.length; i++) {
                var c = text.charAt(i);
                if (reList.indexOf(c) >= 0) {
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
                if (v2 != '')
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
            if (item.indexOf(",") >= 0) {
                return (len >= begin && len <= end);
            } else {
                if (end != "")
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
            if (typeof(rule) != 'regexp') {
                //转成RegExp对象
                if (this.testRegex('\\/\\^', rule)) {
                    rule = rule.substring(1, rule.length);
                }
                if (this.testRegex('\\/\\i', rule)) {
                    rule = rule.substring(0, rule.length - 2);
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
        doValidator: function(val, rule, regMsg) {
            var msg = [];
            var customFunctionReturnObj = null; //自定义函数使用
            var ruleArray = [],
                type = 'solo';
            if (!!rule) {
                if (rule.indexOf('_') >= 0) { //与关系
                    ruleArray = rule.split('_');
                    type = 'and';
                } else if (rule.indexOf('||') >= 0) { //或关系
                    ruleArray = rule.split('||');
                    type = 'or';
                } else { //单个校验
                    ruleArray[0] = rule;
                }
                var flag = (type != 'or') ? true : false;
                var state = flag,
                    stateArray = [];

                $.each(ruleArray, function(index, item) {
                    var tempMSG = "";
                    if (item.indexOf('must') >= 0) {
                        if ($.trim(val) == '') state = false;
                        else state = true;
                        if (typeof(regMsg) == 'undefined' || $.trim(regMsg) == '') {
                            tempMSG = jazz_validator.msg.t + jazz_validator.msg.must;
                        } else {
                            tempMSG = regMsg + '';
                        }
                    } else if (/^(\w+_)?date;.+/.test(item)) {
                        var items = item.split(';');
                        var d = items[1] || "yyyy-MM-dd";
                        //var f = jazz.util.parseDataByDataFormat({"cellvalue": $.trim(val), "datatype": "date", "dataformat": d});
                        var c = jazz.dateformat.isDate($.trim(val), d);
                        if (c == false) {
                            if (typeof(regMsg) == 'undefined' || $.trim(regMsg) == '') {
                                tempMSG = jazz_validator.msg.t + "时间格式输入有误，请输入（" + d + "）格式！";
                            } else {
                                tempMSG = regMsg + '';
                            }
                            state = false;
                        } else {
                            state = true;
                        }
                    } else if (item.indexOf("number") >= 0 && item.indexOf(",") >= 0) {
                        var defineArray = item.substring(item.indexOf("(") + 1, item.indexOf(")")).split(",");
                        var zl = defineArray[0] - defineArray[1];
                        var xl = defineArray[1];
                        state = jazz_validator.numberDefine(val, zl, xl);
                        if (typeof(regMsg) == 'undefined' || $.trim(regMsg) == '') {
                            tempMSG = jazz_validator.msg.t + "数字,且整数部分最多" + zl + "位,且小数部分最多" + xl + "位";
                        } else {
                            tempMSG = regMsg + '';
                        }
                        item = 'numberDefine';

                    } else if (jazz_validator.reg[item]) {
                        //postal url telephone number$character数字汉字加英文字母    qq  email cellphone currency
                        //numberInt numberFloat numberScience
                        //chinese english
                        state = (state == flag) ? jazz_validator.testRegexp(val, jazz_validator.reg[item] + "") : state;
                        if (typeof(regMsg) == 'undefined' || $.trim(regMsg) == '') {
                            tempMSG = jazz_validator.msg.t + (jazz_validator.msg[item] || '');
                        } else {
                            tempMSG = regMsg + '';
                        }
                    } else if (item.indexOf('regexp') >= 0) { //正则
                        //自定义正则表达式。例如：rule="regexp;^\d{1}$" 第一个参数为regexp是固定的，第二个参数为自定义的正则表达式，中间使用;分割
                        state = (state == flag) ? jazz_validator.testRegexp(val, item.split(";")[1]) : state;
                        tempMSG = jazz_validator.msg.t + ((!!regMsg) ? regMsg : '');
                        item = 'regexp';

                    } else if (item.indexOf('customCheckStyle') >= 0) {
                        //校验特殊字符的，例如，rule="customCheckStyle;*,"不允许出现*和,
                        state = jazz_validator.customCheckStyle(val, item.split(";")[1]);
                        if (typeof(regMsg) == 'undefined' || $.trim(regMsg) == '') {
                            tempMSG = jazz_validator.msg.t + jazz_validator.msg.customCheckStyle + item.split(";")[1];
                        } else {
                            tempMSG = regMsg + '';
                        }
                    } else if (item.indexOf('customFunction') >= 0) { //自定义
                        var items = item.split(';');
                        customFunctionReturnObj = jazz_validator.getFunc(items[1]);
                        state = customFunctionReturnObj[0].state;
                        tempMSG = jazz_validator.msg.t + customFunctionReturnObj[0].msg;
                        item = 'customFunction';
                        if (state == undefined || state == "undefined" || state == true) {
                            state = true;
                            tempMSG = "";
                        }

                    } else if (item.indexOf('contrast') >= 0) { //数字值比较
                        //用户输入的数字值比较。
                        //支持单范围比较 例如：rule="contrast;>=5" 第一个参数为contrast是固定的，第二个参数为比较范围及比较值，中间使用;分割
                        //支持双范围比较 例如：rule="contrast;>=5;<=6" 第一个参数为contrast是固定的，第二、三个参数为比较范围及比较值，中间使用;分割
                        //比较范围支持常用的所有类型 例如：> >= == < <= !=
                        var contrastValue1 = item.split(";")[1],
                            contrastValue2 = "";
                        if (item.split(";").length == 3) {
                            contrastValue2 = item.split(";")[2];
                        }
                        state = jazz_validator.contrast(val, contrastValue1, contrastValue2);
                        if (typeof(regMsg) == 'undefined' || $.trim(regMsg) == '') {
                            tempMSG = jazz_validator.msg.t + jazz_validator.msg.contrast + contrastValue1 + ",  " + contrastValue2;
                        } else {
                            tempMSG = regMsg + '';
                        }
                        item = 'contrast';

                    } else if (item.indexOf('length') >= 0) { //输入长度比较
                        //用户输入的字符串长度必须在某个范围之内。
                        //例如1：rule="length;2;4" 第一个参数为length是固定的，第二个参数为最小值，第三个参数为最大值，中间使用;分割相当于"用户输入的字符串长度在2,4之间，不包括边界值"
                        //例如2：rule="length;2,4" 第一个参数为length是固定的，第二个参数为最小值，第三个参数为最大值，中间使用;分割相当于"用户输入的字符串长度在2,4之间，包括边界值"
                        //例如3：rule="length;5" 第一个参数为length是固定的，第二个参数为长度值，相当于"用户输入的字符串长度只能为5"
                        //汉字算两个字符
                        var tempItemArray = item.split(";"),
                            begin = "",
                            end = "";
                        if (tempItemArray.length == 3) {
                            begin = item.split(";")[1];
                            end = item.split(";")[2];
                        } else {
                            if (tempItemArray[1].indexOf(",") >= 0) {
                                begin = tempItemArray[1].split(",")[0];
                                end = tempItemArray[1].split(",")[1];
                            } else {
                                begin = tempItemArray[1];
                            }
                        }
                        state = jazz_validator.length(tempItemArray[1], val, begin, end);
                        if (typeof(regMsg) == 'undefined' || $.trim(regMsg) == '') {
                            tempMSG = jazz_validator.msg.t + jazz_validator.msg['length'] + begin + (!!end ? ("和" + end + "之间") : "");
                        } else {
                            tempMSG = regMsg + '';
                        }
                        item = 'length';

                    } else if (item.indexOf('idcard') >= 0) {
                        state = jazz_validator.idcard(val);
                        if (typeof(regMsg) == 'undefined' || $.trim(regMsg) == '') {
                            tempMSG = jazz_validator.msg.t + jazz_validator.msg.idcard;
                        } else {
                            tempMSG = regMsg + '';
                        }
                        item = 'idcard';
                    }

                    if (!state && tempMSG) {
                        msg.push(tempMSG);
                    }

                    stateArray[index] = state;
                });

                var rs = true;
                if (type == 'and') {
                    rs = this._rAndState(stateArray, type);
                } else if (type == 'or') {
                    rs = this._rOrState(stateArray, type);
                } else {
                    rs = state;
                }
                return {
                    'state': rs,
                    'msg': msg.join(";")
                };
            } else {
                return {
                    'state': true,
                    'msg': msg.join()
                };
            }

        },

        /**
         * @desc 确定返回状态
         * @param {stateArray} 状态数组
         * @param {type}
         * @return boolean
         * @example this._rAndState(val, rule)
         */
        _rAndState: function(stateArray, type) {
            var r = true;
            $.each(stateArray, function(i, state) {
                if (state == false)
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
        _rOrState: function(stateArray, type) {
            var r = false;
            $.each(stateArray, function(i, state) {
                if (state == true)
                    r = true;
            });
            return r;
        }

    };
})(jQuery);

(function($) {

    /**
     * @version 1.0
     * @description 公共UI组件对象的函数接口
     */

    jazz.namespace("widget");

    jazz.widget = function(options, parent) {
        if (typeof(options) == "object") {
            var vtype = options["vtype"];
            var name = options["name"] || "";
            if (!vtype) { // || !name
                alert(" vtype 未定义！");
                return false;
            } else {
                var obj = $("<div name='" + name + "' vtype='" + vtype + "'></div>");

                if (!!parent) {
                    obj.appendTo(parent);
                } else {
                    obj.appendTo("body");
                }

                obj.parseComponent(options);

                var _type = jQuery.trim(vtype);
                //对于$()和widget初始化的组件，若是外层组件使用了transformOptions方式，
                //则需要将外层组件的options的items删除掉，即可以直接返回obj,不再继续渲染items
                if (_type == "gridpanel") {
                    return obj;
                }

                if (_type == "window" || _type == "panel" || _type == "formpanel") { // || _type== "gridpanel"
                    if (_type != "formpanel" && !options["frameurl"]) {
                        _create$Child(options, obj.children(".jazz-panel-content"));
                    }
                } else {
                    if (_type != "toolbar" && _type != "button") {
                        _create$Child(options, obj);
                    }
                }
                return obj;
            }
        }
    };

    var _create$Child = function(options, parent) {
        if (options["items"] == undefined) {
            return false;
        } else {
            var items = options["items"];
            if (jQuery.isArray(items)) {
                for (var i = 0, len = items.length; i < len; i++) {
                    var item = items[i];
                    var vtype = item["vtype"];
                    var name = item["name"];
                    if (vtype && name) {
                        var obj = $("<div name='" + name + "' vtype='" + vtype + "'></div>");
                        obj.appendTo(parent);

                        obj.parseComponent(item);

                        //判断item对象中是否还存在items属性
                        if (item["items"]) {
                            //判断vtype类型
                            if (vtype == "panel" || vtype == "window" || vtype == "formpanel" || vtype == "gridpanel") {
                                if (_type != "formpanel" && !item["frameurl"]) {
                                    obj = obj.children(".jazz-panel-content");
                                }
                            }
                            if (_type != "toolbar" && _type != "button") {
                                _create$Child(item, obj);
                            }
                        }
                    } else {
                        alert("解析items子项中，name或vtype未定义！");
                    }
                }
            } else {
                alert("解析items格式错误，请按[{},{}]格式输写！");
            }
        }
    };

    /**
     * @desc 封装给开发者,工厂总代理
     */

    /**
     * 插件列表
     */
    var plugins = (function() {
        var ary = [],
            p;
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
    /*$.each(plugins, function (i, pluginName) {
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
     console.log(' something unreachable unless error');
     }
     };
     });
     */
})(jQuery);

(function($) {
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
        _create: function() {
            this._attrToOptions();

            this.element.attr('vtype', this.options.vtype);
            var name = this.options.name;
            if (!name) {
                this.options.name = name = this.options.id || this.getCompId();
            }
            this.element.attr('name', name);
        },

        /**
         * @desc 初始化
         * @private
         */
        _init: function() {

        },

        /**
         * @desc 将attr属性转换成options
         * @private
         */
        _attrToOptions: function() {
            var c_a = jazz.attributeToOptions(this.element.get(0));
            for (var p in c_a) {
                if (c_a[p] === "true") {
                    c_a[p] = true;
                } else if (c_a[p] === "false") {
                    c_a[p] = false;
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
        _event: function(eventName, event, data) {
            var _ename = eventName;
            var callback = this.options[_ename];
            if (!$.isFunction(callback)) {
                var reg = /\(/;
                if (reg.test(callback)) {
                    callback = callback.split("(")[0] || null;
                    if (callback == null) {
                        return false;
                    }
                }
                callback = eval(callback + "");
            }

            data = data || {};
            event = $.Event(event);
            event.type = (eventName === this.options.vtype ?
                eventName :
                this.options.vtype + eventName).toLowerCase();
            event.currentClass = this;
            event.target = this.element[0];

            orig = event.originalEvent;
            if (orig) {
                for (prop in orig) {
                    if (!(prop in event)) {
                        event[prop] = orig[prop];
                    }
                }
            }
            this.element.trigger(event, data);
            return !($.isFunction(callback) &&
                callback.apply(this.element[0], [event].concat(data)) === false || event.isDefaultPrevented());
        },
        /**
         * @desc 合并vtype和$()/$.widget()方式在div中定义接收方法的处理，如：<div dataloadcomplete="loaddata()">形式。
         * @param 接收任意可变参数，注意第一个参数必须为“在div中定义接收方法”的的引用
         * @return 定义回调函数返回值
         * @private
         */
        _customopration: function() {
            if (arguments.length == 0) {
                return false;
            }
            var cb = arguments[0];
            var params = [];
            if (cb) {
                for (var i = 1; i < arguments.length; i++) {
                    params.push(arguments[i]);
                }
                if ($.isFunction(cb)) {
                    return cb.apply(this, params);
                } else {
                    if (cb.indexOf("(") != -1) {
                        cb = cb.substr(0, cb.indexOf("("));
                    }
                    return eval(cb).apply(this, params);
                }
            }
        },

        //    createVtypeTree: function(){
        //      var element = this.element, options = this.options;
        //      // 查找元素的parent节点
        //      var parent = element.parents("div[vtype]:first");
        //      // 如果没找到，设置为BODY
        //      if( parent.size() == 0 ){
        //        parent = $("BODY");
        //      }
        //      
        //      // 设置父节点的data中的child
        //      var parentData = parent.data("vtypetree") || {parent:"", child:{}};
        //      var childName = options["name"];
        //      parentData.child[childName] = element;
        //      parent.data("vtypetree", parentData);
        //      
        //      // 设置当前节点中的parent的值和vType的值
        //      var nodeData = element.data("vtypetree") || {parent:"", child:{}};
        //      nodeData["parent"] = parent;
        //      nodeData["vtype"] = options["vtype"];
        //      element.data("vtypetree", nodeData);
        //    },

        /**
         * @desc 对options中的items、content进行预处理
         * @private
         */
        _transformOptions: function() {
            if ($.isArray(this.options.items)) {
                var items = this.options.items;
                var vtype = this.options.vtype;
                for (var i = 0, len = items.length; i < len; i++) {
                    var item = items[i];
                    if (vtype == "panel" || vtype == "window" || vtype == "gridpanel") {
                        jazz.widget(item, this.content);
                    } else {
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
        getCompId: function() {
            return 'comp_j_' + this.options.name + '_' + (++jazz.config.compNumber);
        }

    });


})(jQuery);


(function($) {
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
        _create: function() {
            this._super();
        },

        /**
         * @desc 初始化
         * @private
         */
        _init: function() {
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
        setSize: function(width, height) {},

        /**
         * @desc 设置组件的宽度，此方法触发resize事件
         * @param {width} number 宽度值
         * @example
         */
        setWidth: function(w) {
            var _w = 0;
            if (jazz.isNumber(w)) {
                _w = $('#' + this.options.id).width(w);
            }
            if (typeof(w) == 'undefined') {
                _w = $('#' + this.options.id).outerWidth();
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
        setHeight: function(height) {},

        /**
         * @desc 返回元素的大小 {width:12, height 21}
         * @return Object
         * @throws
         * @example
         */
        getSize: function() {},

        /**
         * @desc 返回组件元素的高度
         * @param {n} String 组件id/class
         * @return number
         * @throws
         * @example
         */
        getHeight: function(n) {
            var h = 0;
            if (typeof(n) == 'string') {
                if ($.trim(n).substring(0, 1) == '.') {
                    h = $('.' + n).outerHeight();
                } else if ($.trim(n) == '') {
                    h = $('#' + this.options.id).outerHeight();
                } else {
                    h = $('#' + n).outerHeight();
                }
            }
            if (typeof(n) == 'undefined') {
                h = $('#' + this.options.id).outerHeight();
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
        getWidth: function(n) {
            var w = 0;
            if (typeof(n) == 'string') {
                if ($.trim(n).substring(0, 1) == '.') {
                    w = $('.' + n).outerWidth();
                } else if ($.trim(n) == '') {
                    w = $('#' + this.options.id).outerWidth();
                } else {
                    w = $('#' + n).outerWidth();
                }
            }
            if (typeof(n) == 'undefined') {
                w = $('#' + this.options.id).outerWidth();
            }
            return w;
        },

        /**
         * @desc 返回当前组件所在元素的尺寸大小，包括其margin占据的空间位置
         * @return Object {width: 60, height: 75}
         * @throws
         * @example
         */
        getOuterSize: function() {},

        /**
         * @desc 返回当前鼠标相对于组件位置
         * @return {x: 30, y: 25}
         * @throws
         * @example
         */
        getXY: function() {},

        /**
         * @desc 返回当前鼠标相对页面位置
         * @return {pagex: 331, pagey: 225}
         * @throws
         * @example
         */
        getPageXY: function() {},

        /**
         * @desc 设置组件相对于父容器的位置
         * @param {left} number 距离左边位置
         * @param {top}  number 距离上边位置
         * @return
         * @throws
         * @example
         */
        setPosition: function(left, top) {},

        /**
         * @desc 获取组件相对父容器的位置
         * @param {n} String 组件id/String
         * @return {left 435, top: 124} Object
         * @throws
         * @example
         */
        getPosition: function(n) {
            var p = {
                top: 0,
                left: 0
            };
            if (typeof(n) == 'string') {
                if ($.trim(n).substring(0, 1) == '.') {
                    p.top = $('.' + n).offset().top;
                    p.left = $('.' + n).offset().left;
                } else if ($.trim(n) == '') {
                    p.top = $('#' + this.options.id).offset().top;
                    p.left = $('#' + this.options.id).offset().left;
                } else {
                    p.top = $('#' + n).offset().top;
                    p.left = $('#' + n).offset().left;
                }
            }
            if (typeof(n) == 'undefined') {
                p.top = $('#' + this.options.id).offset().top;
                p.left = $('#' + this.options.id).offset().left;
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
        ,
        pageHeight: function() {
            if ($.browser.msie) {
                return document.compatMode == "CSS1Compat" ? document.documentElement.clientHeight : document.body.clientHeight;
            } else {
                return self.innerHeight;
            }
        },

        /**
         * @desc 获取当前页面的宽度
         * @return Number
         * @throws
         * @example this.pageWidth();
         */
        pageWidth: function() {
            if ($.browser.msie) {
                return document.compatMode == "CSS1Compat" ? document.documentElement.clientWidth : document.body.clientWidth;
            } else {
                return self.innerWidth;
            }
        },

        /**
         * @desc 计算页面大小
         */
        getPageSize: function() {
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
     * @name jazz.container
     * @description 使用矩形容器组件的基类，为子组件的提供布局服务。
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
             *@desc 布局容器的目标对象
             *@default null
             */
            layoutcontainer: null,

            /**
             *@type Object
             *@desc 布局类的对象（布局类的this对象）
             *@default null
             */
            layoutobject: null,

            /**
             *@type Array/Object
             *@desc 存放子容器
             *@default null
             */
            items: null
        },

        /** @lends jazz.container */
        /**
         * @desc 创建组件
         * @private
         */
        _create: function() {
            this._super();
        },

        /**
         * @desc 初始化
         * @private
         */
        _init: function() {
            this._super();
        },

        /**
         * @desc 设置组件的布局 当有新组件加入或组件改变大小/位置时，就需要执行此方法
         * @param {container} 需要布局的容器对象
         * @throws
         * @example $('#container').container('doLayout', container);
         */
        doLayout: function(container) {
            //var T1 = new Date();
            this.options.layoutcontainer = container;

            var _layout = this.options.layout;

            if (typeof jazz.layout[_layout] == 'undefined') {
                _layout = jazz.layout.auto;
            }

            if (!(typeof(this.options.layoutconfig) == 'object')) {
                this.options.layoutconfig = jazz.stringToJson(this.options.layoutconfig);
            }

            if (_layout == 'fit') {
                var $this = this;
                this.element.layout({
                    layout: 'fit',
                    layoutconfig: {
                        callback: function() {
                            $this._panelFitCallback($this);
                        }
                    }
                });
            } else {

                var comp = jazz.layout[_layout];
                var obj = container[comp]();
                this.options.layoutobject = obj.data(comp);
                this.options.layoutobject.layout(this, container, this.options.layoutconfig);
            }
            //var T2 = new Date();
            //jazz.log("***调用组件="+this.options.vtype+"***组件名称="+(this.options.name || this.options.id)+"***时间差="+(T2-T1));
        },

        /**
         * @desc 重新装载布局
         * @param {layoutConfig} 布局的配置参数
         * @throws
         * @example $('#container').container('reloadLayout', layoutConfig);
         */
        reloadLayout: function(layoutConfig) {
            try {
                this.options.layoutobject.layout(this, this.options.layoutcontainer, layoutConfig);
            } catch (e) {
                jazz.error('错误的调用布局的reloadLayout接口！！！');
            }
        }

    });

})(jQuery);

(function() {

    /**
     * @version 1.0
     * @name jazz.layout
     * @description 布局参数定义。
     * @constructor
     * @example jazz.layout.auto;
     */
    jazz.layout = {
        auto: 'autolayout',
        border: 'borderlayout',
        column: 'columnlayout',
        fit: 'fitlayout',
        row: 'rowlayout',
        vbox: 'vboxlayout',
        hbox: 'hboxlayout',
        table: 'tablelayout',
        query: 'querylayout',
        card: 'cardlayout',
        anchor: 'anchorlayout'
    };

    /**
     * @version 1.0
     * @name jazz.containerlayout
     * @description 每个Container都委托布局管理器来渲染其子组件Component,通常不应该直接使用。
     * @constructor
     * @extends jazz.container
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
             *@type Object
             *@desc 布局的配置参数
             *@default '{}'
             */
            layoutconfig: {}

        },

        /** @lends jazz.containerlayout */
        /**
         * @desc 获取默认的布局
         * @example $('XXX').containerlayout('getDefaultLayout');
         */
        getDefaultLayout: function() {
            return jazz.layout.auto;
        }

    });


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

            container.css({
                border: '5px solid red'
            });
            //config.type='0';

            if (anchor == null) {
                if (config.type == '1') {
                    var page = this._getPageSize();
                    var x = page.windowWidth / 2,
                        y = page.windowHeight / 2;
                    anchor = config.anchor = {
                        x: x,
                        y: y
                    };
                    //jazz.log('page.windowWidth='+page.windowWidth+'***page.windowHeight='+page.windowHeight+'x='+x+'***'+'y='+y);
                } else {
                    container.css({
                        width: 1200,
                        height: 400
                    });
                    var top = container.offset().top;
                    var left = container.offset().left;
                    var cw = container.width() / 2;
                    var ch = container.height() / 2;

                    top = parseInt(top + ch);
                    left = parseInt(left + cw);

                    anchor = config.anchor = {
                        x: left,
                        y: top
                    };
                    //jazz.log('top='+top+'***left='+left+'***cw='+cw+'***ch='+ch);
                }
            }

            var cheight = 0;
            $.each(points, function(i, p) {
                if (!!p.id) {
                    var x = parseInt(anchor.x) + parseInt(p.offset.x);
                    var y = parseInt(anchor.y) + parseInt(p.offset.y);
                    var n = $('#' + p.id).outerHeight(true) + y; //计算DIV高度
                    if (n > cheight) {
                        cheight = n;
                    }
                    $('#' + p.id).appendTo(container);
                    $('#' + p.id).css({
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
        _getPageSize: function() {
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


    /**
     * @version 1.0
     * @name jazz.autolayout
     * @description 自动布局。
     * @constructor
     * @extends jazz.containerlayout
     */
    $.widget("jazz.autolayout", $.jazz.containerlayout, {

        /** @lends jazz.autolayout */

        /**
         * @desc 设置布局
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutconfig} 布局需要的配置参数
         */
        layout: function(cthis, container, layoutconfig) {

        }

    });


    /**
     * @version 1.0
     * @name jazz.borderlayout
     * @description 行布局。
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
            this.cwidth = container.width();
            this.cheight = container.height();

            //获取border布局下的全部子元素
            var childs = container.find("div[region]");

            if (childs.length > 5) {
                jazz.error("border布局定义错误, 只能定义 east south west north center 五个部分，且只能定义一次！");
                return false;
            }

            //布局刷新时，重新初始化对象
            //存储布局对象
            this.bobject = {
                north: "",
                west: "",
                center: "",
                east: "",
                south: ""
            };
            //布局中的region设置, 只能为north west center east south中的一个, 且不能重复出现
            //this.regionnumber用于记录数量，避免重复出现
            this.regionnumber = {
                north: 0,
                west: 0,
                center: 0,
                east: 0,
                south: 0
            };

            //纵向  north center south, 对应行布局
            //this.by 存放行布局用的高度，例：  ["20%", "*", "300"], yn 记录纵向元素的数量
            this.by = [];
            var yn = 0;
            //横向  west center east, 对应列布局
            //this.bx 存放列布局用的宽度，例： ["200", "*", "200"], xn 记录横向元素的数量
            this.bx = [];
            var xn = 0;

            var $this = this,
                f = false;

            $.each(childs, function(i, obj) {
                var $obj = $(obj);
                var region = $obj.attr("region");

                if (region == "center") {
                    f = true;
                }

                if (region) {
                    region = $.trim(region);
                    $this.regionnumber[region] += 1;
                    if ($this.regionnumber[region] > 1) {
                        jazz.error("border布局的region属性设置错误, region=\"" + region + "\", 重复设置！");
                        return false;
                    }

                    if (" east west ".indexOf(region) >= 0) {
                        $this.bobject[region] = {
                            index: i,
                            object: $obj,
                            width: $obj.attr("width")
                        };
                        xn += 1;
                    } else if (" north south ".indexOf(region) >= 0) {
                        $this.bobject[region] = {
                            index: i,
                            object: $obj,
                            height: $obj.attr("height")
                        };
                        yn += 1;
                    } else if (" center ".indexOf(region) >= 0) {
                        //自适应宽度和高度，不需要设置width和height
                        $this.bobject[region] = {
                            index: i,
                            object: $obj
                        };
                        yn += 1;
                        xn += 1;
                    }
                } else {
                    jazz.error("border布局的region属性设置错误, region=\"east\" region=\"south\" region=\"west\" region=\"north\" region=\"center\"");
                    return false;
                }
            });

            if (!f) {
                jazz.error("border布局的regsion属性设置错误, 必须要有region=\"center\"");
                return false;
            }

            //将各部分region数量放入layoutconfig中，在调用列、行布局时，可用于判断各部分是否被定义
            layoutconfig["regionnumber"] = this.regionnumber;
            //将region对象放入layoutconfig中，在调用列、行布局时使用
            layoutconfig["bobject"] = this.bobject;

            //在border布局中，north和south只需要设置高度即可，宽度会自适应容器大小
            //west和east只需要设置宽度即可，高度会自适应
            //如果不设置north south高度   west east宽度，取默认的宽度和高度
            var defaultheight = "150",
                defaultwidth = "200";

            var centerObj = this.bobject.center["object"];
            //只存在center时
            if (yn == 1 && xn == 1) {
                centerObj.css({
                    border: 0,
                    margin: 0,
                    padding: 0,
                    width: this.cwidth,
                    height: this.cheight
                });
            }
            //如果大于1，说明存在列布局, 列布局控制 west center east
            if (xn > 1) {
                //在center区域上部追加一个div， 用于存放 west center east，并按这个顺序存放。
                this.colcontainerObj = $('<div style="overflow: hidden; height: 100%;">');
                centerObj.before(this.colcontainerObj);
                var westObj = this.bobject.west["object"],
                    eastObj = this.bobject.east["object"];
                if (westObj) {
                    this.colcontainerObj.append(westObj);
                    var _width = this.bobject.west["width"];
                    if (!_width) {
                        _width = defaultwidth;
                    }
                    $this.bx.push(_width);
                }

                this.colcontainerObj.append(centerObj);
                //center区域的宽度
                this.bx.push("*");

                if (eastObj) {
                    this.colcontainerObj.append(eastObj);
                    var _width = this.bobject.east["width"];
                    if (!_width) {
                        _width = defaultwidth;
                    }
                    $this.bx.push(_width);
                }
            }
            if (yn > 1) {
                var northObj = this.bobject.north["object"],
                    southObj = this.bobject.south["object"];
                if (northObj) {
                    //通过索引值判断north是否为0，也就是是否在最上边，如果不在最上边则需要移动north区域的位置
                    if (this.bobject.north["index"] != 0) {
                        container.prepend(northObj);
                    }
                    var _height = this.bobject.north["height"];
                    if (!_height) {
                        _height = defaultheight;
                    }
                    $this.by.push(_height);
                }

                //center区域的高度
                $this.by.push("*");

                if (southObj) {
                    //判断当前south是否为最后一个元素，如果不是则将其移动到最后位置
                    var t = container.children("div:last-child");
                    if (" south ".indexOf(t.attr("region")) < 0) {
                        container.append(northObj);
                    }
                    var _height = this.bobject.south["height"];
                    if (!_height) {
                        _height = defaultheight;
                    }
                    $this.by.push(_height);
                }

                //调用行布局
                this._rowLayout(cthis, container, layoutconfig);
            }
            if (xn > 1) {
                //调用列布局
                this._columnLayout(cthis, container, layoutconfig);
            }

            //监听父容器大小改变
            var $this = this,
                t = null;
            container.off('resize.borderlayout').on('resize.borderlayout', function() {
                cthis._borderLayoutWidth();
                cthis._borderLayoutHeight();

                $this._rowLayout(cthis, container, layoutconfig);
                if (xn > 1) {
                    $this._columnLayout(cthis, container, layoutconfig);
                }

                if (t) {
                    window.clearTimeout(t);
                }
                t = setTimeout(function() {
                    var ui = {};
                    if ($this.regionnumber["north"] == 1) {
                        ui["north"] = {
                            "height": $this.bobject.north.object.height(),
                            "width": $this.bobject.north.object.width()
                        };
                    }
                    if ($this.regionnumber["south"] == 1) {
                        ui["south"] = {
                            "height": $this.bobject.south.object.height(),
                            "width": $this.bobject.south.object.width()
                        };
                    }
                    if ($this.regionnumber["center"] == 1) {
                        ui["center"] = {
                            "height": $this.bobject.center.object.height(),
                            "width": $this.bobject.center.object.width()
                        };
                    }
                    if ($this.regionnumber["west"] == 1) {
                        ui["west"] = {
                            "height": $this.bobject.west.object.height(),
                            "width": $this.bobject.west.object.width()
                        };
                    }
                    if ($this.regionnumber["east"] == 1) {
                        ui["east"] = {
                            "height": $this.bobject.east.object.height(),
                            "width": $this.bobject.east.object.width()
                        };
                    }
                    cthis._event("borderresize", null, ui);
                }, 300);
            });
        },

        /**
         * @desc 调用列布局
         */
        _columnLayout: function(cthis, container, layoutconfig) {
            var _col = jazz.layout.column;
            container[_col]();
            layoutconfig["width"] = this.bx;
            layoutconfig["isborderlayout"] = true;
            container.data(_col).layout(cthis, this.colcontainerObj, layoutconfig);
        },

        /**
         * @desc 调用行布局
         */
        _rowLayout: function(cthis, container, layoutconfig) {
            var _row = jazz.layout.row;
            container[_row]();
            layoutconfig["height"] = this.by;
            layoutconfig["isborderlayout"] = true;
            //中间区域包裹，列布局的对象
            layoutconfig["centerobject"] = this.colcontainerObj;
            container.data(_row).layout(cthis, container, layoutconfig);
        }
    });


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
            if (!obj[0]) {
                jazz.error('card layout is error!');
                return false;
            }

            var w = obj[0].width || $(obj[0]).outerWidth(true);

            var num = obj.size();
            var ulWidth = num * w;
            cardlayoutObject.width = w;

            cthis.element.addClass('jazz-cardpanel');


            var box = $('<div class="jazz-cardpanel-box"></div>').appendTo(container);
            var content = $('<div class="jazz-cardpanel-content"></div>').appendTo(box);
            var ul = $('<div class="jazz-cardpanel-ul" style="width:' + ulWidth + 'px"></div>').appendTo(content);
            $.each(obj, function(i, el) {
                var li = $('<div class="jazz-cardpanel-li" style="width:' + cardlayoutObject.width + 'px" >').appendTo(ul);
                li.append(el);
            });

            var h = ul.height();

            if (buttondisplay === true) {
                this.paginator = $('<div></div>').appendTo(box);
                this.paginator.paginator({
                    template: '{PageLinks}',
                    rows: 1,
                    totalRecords: obj.size(),
                    click: function(e, ui) {
                        cardlayoutObject.point(ui.page, num);
                    }
                });
                this.paginator.removeClass('jazz-widget-header');
                cardlayoutObject.paginator = this.paginator;
            }


            //重新计算高度
            $.each(obj, function(i, el) {
                $(el).height(h - cardlayoutObject.height);
            });

            if (buttondisplay === true) {
                this._buildButton(box, num);
            }

            box.css({
                height: h
            });
            content.css({
                width: cardlayoutObject.width,
                height: h - cardlayoutObject.height
            });

        },

        /**
         * @desc 设置布局
         * @param {box} box容器
         * @param {num} 滚动页数
         * @example
         */
        _buildButton: function(box, num) {
            var $this = this;
            var cardlayoutObject = this.options.cardlayoutObject;
            this.leftButton = $('<a href="javascript:void(0);" target="_self"></a>').appendTo(box);
            this.rightButton = $('<a href="javascript:void(0);" target="_self"></a>').appendTo(box);
            this.leftButton.on('click', function() {
                if (cardlayoutObject.activeIndex > 0) {
                    cardlayoutObject.slide('l', num);
                }
            });
            this.rightButton.on('click', function() {
                if (cardlayoutObject.activeIndex < num - 1) {
                    cardlayoutObject.slide('r', num);
                }
            });
            box.hover(function() {
                if (cardlayoutObject.activeIndex > 0) {
                    $this.leftButton.addClass('jazz-cardpanel-leftbtn');
                }
                if (cardlayoutObject.activeIndex < num - 1) {
                    $this.rightButton.addClass('jazz-cardpanel-rightbtn');
                }
            }, function() {
                $this.leftButton.removeClass('jazz-cardpanel-leftbtn');
                $this.rightButton.removeClass('jazz-cardpanel-rightbtn');
            });
        },

        /**
         * @desc 设置布局
         * @param {n} 页数
         * @example
         */
        previousPage: function(n) {
            return this.options.cardlayoutObject.slide('l', n);
        },

        /**
         * @desc 设置布局
         * @param {n} 页数
         * @example
         */
        nextPage: function(n) {
            return this.options.cardlayoutObject.slide('r', n);
        },

        options: {
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
                    if (idx < 0) idx = 0;
                    else if (idx >= num) idx = 4;
                    this.switching(idx, num);
                    return idx;
                },
                index: function(lr, num) {
                    if (lr == "l") {
                        this.activeIndex = this.activeIndex - 1;
                        if (this.activeIndex < 0) this.activeIndex = 0;
                    } else {
                        this.activeIndex = this.activeIndex + 1;
                        if (this.activeIndex >= num) this.activeIndex = num - 1;
                    }
                    if (this.buttondisplay === true) {
                        this.paginator.paginator('option', 'page', this.activeIndex);
                    }
                    return this.activeIndex;
                },
                switching: function(index, num) {
                    this.container.find(".jazz-cardpanel-ul").animate({
                        marginLeft: (0 - this.width * index)
                    }, 500);

                    if (this.buttondisplay === true) {
                        if (this.activeIndex > 0) {
                            this.$this.leftButton.addClass('jazz-cardpanel-leftbtn');
                        } else {
                            this.$this.leftButton.removeClass('jazz-cardpanel-leftbtn');
                        }
                        if (this.activeIndex < num - 1) {
                            this.$this.rightButton.addClass('jazz-cardpanel-rightbtn');
                        } else {
                            this.$this.rightButton.removeClass('jazz-cardpanel-rightbtn');
                        }
                    }
                }
            }
        }

    });


    /**
     * @version 1.0
     * @name jazz.columnlayout
     * @description 列布局。
     * @constructor
     * @extends jazz.containerlayout
     */
    $.widget("jazz.columnlayout", $.jazz.containerlayout, {

        /** @lends jazz.columnlayout */
        /**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutconfig} 布局需要的配置参数
         */
        layout: function(cthis, container, layoutconfig) {

            //列布局时，禁止容器出现滚动条
            container.css('overflow', 'hidden');
            var containerWidth = container.width();
            var $this = this;
            //是否允许有边框
            this.border = layoutconfig.border || false;
            //是否可以拖动
            this.isdrag = layoutconfig.isdrag || false;
            //layoutconfig.columnwidth 0.5版本属性 || layoutconfig.width 1.0版本属性， 同事支持两种写法
            this.colwidths = layoutconfig.columnwidth || layoutconfig.width;

            //用于判断是否为 border布局调用isborderlayout=true
            var isborderlayout = layoutconfig.isborderlayout || false;

            //设置border布局参数的默认值, 当不是border布局时，这些参数就取默认值，确保column布局正常使用
            var west_show_border = layoutconfig.west_show_border == false ? false : true;
            var east_show_border = layoutconfig.east_show_border == false ? false : true;

            var west_show_switch = layoutconfig.west_show_switch == false ? false : true;
            var east_show_switch = layoutconfig.east_show_switch == false ? false : true;

            var west_drag = layoutconfig.west_drag == false ? false : true;
            var east_drag = layoutconfig.east_drag == false ? false : true;

            var west_drag_min = layoutconfig.west_drag_min ? parseInt(layoutconfig.west_drag_min) : 0;
            var west_drag_max = layoutconfig.west_drag_max ? parseInt(layoutconfig.west_drag_max) : 0;
            var east_drag_min = layoutconfig.east_drag_min ? parseInt(layoutconfig.east_drag_min) : 0;
            var east_drag_max = layoutconfig.east_drag_max ? parseInt(layoutconfig.east_drag_max) : 0;

            this.west_open = layoutconfig.west_open == false ? false : true;
            this.east_open = layoutconfig.east_open == false ? false : true;

            //isborderlayout == true 为border布局调用
            //broder布局内置了column布局, 所以要通过下边设置border布局所要的参数。
            if (isborderlayout == true) {
                this.iscolumnlayout = false;
                this.border = true;
                //在border布局中定义的，存储各区域的对象
                this.bobject = layoutconfig.bobject;
                this.iswest = layoutconfig.regionnumber.west == 1 ? true : false;
                this.iseast = layoutconfig.regionnumber.east == 1 ? true : false;
            } else {
                //用于判断是否为列布局使用，而不是border布局
                this.iscolumnlayout = true;
            }

            var cols = this.colwidths.length || 0; //定义的列数
            if (cols == 0) {
                jazz.error("行布局的rowheight设置错误, 例： ['200px', '30%', '*']");
            }

            this.borderWidth = 0;

            //计算全部固定列的宽(固定列 px 非固定列 % *)
            this.fixColumnWidth = 0;

            //存储非固定列的对象
            this.noFixColumn = [];

            //当有收缩按钮时，通过点击收缩按钮后，记录上一次的值, 保证能通过这些值还原上一次操作的状态
            this.leftval = 0;
            this.rightval = 0;

            //如果刷新布局时，先判断上一次布局是否存在边框，如果存在则把边框销毁

            if (this.border == true && cols > 1) {
                this.bordernumber = 0;
                if (!this.borderObj) {
                    //用于存放边框对象
                    this.borderObj = [];
                }
                $.each(this.borderObj, function(i, obj) {
                    $(obj).remove();
                });
                this.borderObj = [];
            }

            var element = container.children(); //列布局的列元素对象

            for (var i = 0; i < cols; i++) {
                var tempObject = $(element[i]);

                tempObject.removeAttr("style"); //去除原有样式
                tempObject.addClass('jazz-column-element');

                if (this.border == true && cols > 1 && (i + 1) != cols) {
                    //border布局时判断是否存在边框
                    if ((isborderlayout == true && ((west_show_border && this.iswest && i == 0) || (east_show_border == true && this.iseast && (i + 2) == cols))) || this.iscolumnlayout) {

                        var div = '<div index="' + i + '" class="jazz-column-border">';
                        if ((isborderlayout == true && ((west_show_switch && this.iswest && i == 0) || (east_show_switch && this.iseast && (i + 2) == cols))) || (this.iscolumnlayout && (i == 0 || (i + 2) == cols))) {
                            div += '<div class="jazz-column-btn"></div>';
                        }
                        div += '</div>';
                        var $b = $(div);
                        tempObject.after($b);
                        this.borderObj.push($b);
                        this.bordernumber += 1;

                        if ((isborderlayout == true && ((west_drag && this.iswest && i == 0) || (east_drag && this.iseast && (i + 2) == cols))) || this.iscolumnlayout) {
                            $b.addClass("jazz-column-border-cursor");
                        }

                        //获取边框的宽度
                        this.borderWidth = $b.outerWidth() || 0;

                        if (isborderlayout == true && ((west_show_switch && this.iswest && i == 0) || (east_show_switch && this.iseast && (i + 2) == cols))) {
                            //只允许第一列与最后一列，通过点击按钮进行收缩
                            $b.children(".jazz-column-btn").off("click.ct").on("click.ct", function() {
                                var p = $(this).parent();
                                var $pre = p.prev(),
                                    $next = p.next();
                                var index = p.attr("index");
                                //layoutconfig["width"][index] = 0;
                                //$this.layout(cthis, container, layoutconfig);

                                $this._switch(cthis, $pre, $next, index, cols, true);
                            });
                        }
                    }
                }

                var _cw = this.colwidths[i] + "";
                if (_cw != "*" && _cw.indexOf('%') == -1) { //固定宽度的列
                    tempObject.outerWidth(_cw);
                    this.fixColumnWidth = this.fixColumnWidth + tempObject.outerWidth();
                } else { //非固定列
                    this.noFixColumn.push(tempObject);
                }
            }

            container.off('mousedown.column').on('mousedown.column', function(event) {
                var target = event.target,
                    $target = $(target);

                //用于存放位置坐标移动范围, leftborder存放拖动时，左侧边界值  
                //areawidth存放各区域的宽度，通过leftborder和areawidth的相加，确定能拖动的右侧边界值
                //areaobject不同区域的对象
                $this.leftborder = [], $this.areawidth = [], $this.areaobject = [];

                if ($target.is('.jazz-column-border')) {
                    //获取被点击边框的索引值
                    var index = parseInt($target.attr("index"));

                    //if判断是否允许在border布局和column布局中，是否允许拖动分割线
                    //border布局时
                    if ((isborderlayout == true && ((west_drag && index == 0 && $this.iswest) || (east_drag && (index + 2) == cols && $this.iseast)))
                        //可拖拽的列布局
                        || ($this.isdrag == true && $this.iscolumnlayout)) {
                        //点击border边框时的坐标点
                        $this.clo_x = $target.offset().left;

                        //点击到拖动区域时，出现拖动线
                        $this.dragcol = $('<div class="jazz-column-drag" style="display:none" islayout="no"></div>').appendTo(document.body);

                        $this.dragcol.css({
                            display: 'block',
                            top: $target.offset().top,
                            left: $this.clo_x,
                            height: $target.height()
                        });

                        //去除在拖动过程中的选中状态
                        if (!$.browser.mozilla) {
                            $(document).on("selectstart", function() {
                                return false;
                            });
                        } else {
                            $("body").css("-moz-user-select", "none");
                        }

                        $.each(container.children(".jazz-column-element"), function(i, obj) {
                            //确定每一列移动的范围
                            $this.leftborder.push($(obj).offset().left);
                            $this.areawidth.push($(obj).width());
                            $this.areaobject.push($(obj));
                        });

                        //获取拖动的原始距离值
                        var ldragwidth = $this.areawidth[index];
                        var rdragwidth = $this.areawidth[index + 1];

                        //抛出border布局时，开始拖动的事件
                        if (isborderlayout == true) {
                            if (index == 0 && $this.iswest) {
                                if (!$this.west_open) {
                                    ldragwidth = 0;
                                }
                                cthis._event("westdragstart", null, {
                                    "west": {
                                        "width": ldragwidth
                                    },
                                    "center": {
                                        "width": rdragwidth
                                    }
                                });
                            } else {
                                if (!$this.east_open) {
                                    rdragwidth = 0;
                                }
                                cthis._event("eastdragstart", null, {
                                    "center": {
                                        "width": ldragwidth
                                    },
                                    "east": {
                                        "width": rdragwidth
                                    }
                                });
                            }
                        } else {
                            //抛出column布局时，开始拖动时的事件
                            cthis._event("columndragstart", null, {
                                index: index,
                                left: ldragwidth,
                                right: rdragwidth
                            });
                        }

                        $(document).off('mousemove.column mouseup.column').on('mousemove.column', function(e) {
                            if ($this.dragcol) {
                                //左侧边界值
                                var leftborder = $this.leftborder[index];
                                //右侧边界值
                                var rightborder = $this.leftborder[index + 1] + $this.areawidth[index + 1] - $this.dragcol.outerWidth();

                                if ($this.iseast && !$this.east_open && (index + 2) == cols) {
                                    rightborder = $target.offset().left;
                                }
                                var eX = e.pageX;

                                //确定拖动的范围
                                if (isborderlayout == true && (leftborder < eX && eX < rightborder)) {
                                    if (index == 0 && $this.iswest) {
                                        if (west_drag_min < eX && eX < west_drag_max) {
                                            $this.moving = eX;
                                            $this.dragcol.css({
                                                left: $this.moving
                                            });
                                        }
                                    } else {
                                        if ((containerWidth - east_drag_max) < eX && eX < (containerWidth - east_drag_min)) {
                                            $this.moving = eX;
                                            $this.dragcol.css({
                                                left: $this.moving
                                            });
                                        }
                                    }
                                } else if (leftborder < eX && eX < rightborder) {
                                    $this.moving = eX;
                                    $this.dragcol.css({
                                        left: $this.moving
                                    });
                                }
                            }
                        }).on('mouseup.column', function(e) {
                            var _rw = $this.areawidth[index + 1];
                            var _lw = $this.areawidth[index];

                            //通过this.moving移动的距离判断是否移动
                            if ($this.moving) {
                                var _x = $this.dragcol.offset().left - $this.clo_x; //移动的距离
                                var r = $this.areaobject[index + 1];
                                var l = $this.areaobject[index];

                                //border布局
                                if (isborderlayout == true) {
                                    //拖动west
                                    if (index == 0 && $this.iswest) {
                                        if (!$this.west_open) {
                                            _lw = 0;
                                            if (_x > 0) {
                                                $this.west_open = true;
                                            }
                                        }
                                        _rw = _rw - _x;
                                        _lw = _lw + _x;
                                        r.outerWidth(_rw);
                                        l.show().outerWidth(_lw);
                                        //拖动east  
                                    } else if ($this.iseast) {
                                        if (!$this.east_open) {
                                            _rw = 0;
                                            if (_x < 0) {
                                                $this.east_open = true;
                                            }
                                        }
                                        _rw = _rw - _x;
                                        _lw = _lw + _x;
                                        l.outerWidth(_lw);
                                        r.show().outerWidth(_rw);
                                    }
                                } else {
                                    //列布局
                                    _rw = _rw - _x;
                                    _lw = _lw + _x;
                                    r.outerWidth(_rw);
                                    l.outerWidth(_lw);
                                }

                                //抛出border布局时，结束拖动的事件
                                if (isborderlayout == true) {
                                    if (index == 0 && $this.iswest) {
                                        cthis._event("westdragstop", null, {
                                            "west": {
                                                "width": _lw
                                            },
                                            "center": {
                                                "width": _rw
                                            }
                                        });
                                    } else {
                                        cthis._event("eastdragstop", null, {
                                            "center": {
                                                "width": _lw
                                            },
                                            "east": {
                                                "width": _rw
                                            }
                                        });
                                    }
                                } else {
                                    cthis._event("columndragstop", null, {
                                        index: index,
                                        left: _lw,
                                        right: _rw
                                    });
                                }
                            } else {
                                if (ldragwidth != _lw && rdragwidth != _rw) {
                                    //抛出border布局时，结束拖动的事件
                                    if (isborderlayout == true) {
                                        if (index == 0 && $this.iswest) {
                                            cthis._event("westdragstop", null, {
                                                "west": {
                                                    "width": _lw
                                                },
                                                "center": {
                                                    "width": _rw
                                                }
                                            });
                                        } else {
                                            cthis._event("eastdragstop", null, {
                                                "center": {
                                                    "width": _lw
                                                },
                                                "east": {
                                                    "width": _rw
                                                }
                                            });
                                        }
                                    } else {
                                        cthis._event("columndragstop", null, {
                                            index: index,
                                            left: _lw,
                                            right: _rw
                                        });
                                    }
                                }
                            }

                            $this.dragcol.remove();
                            //将移动的距离清空
                            $this.moving = "";

                            //拖动结束后，还原状态
                            if (!$.browser.mozilla) {
                                $(document).off("selectstart");
                            } else {
                                $("body").css("-moz-user-select", "auto");
                            }

                            $(document).off('mousemove.column mouseup.column');
                        });
                    }

                }

            });

            //容器的总宽度
            //var containerWidth = Math.min(container.width(), container.get(0).clientWidth);

            this._calculateWidth(containerWidth, element, cols);

            if (isborderlayout == true) {
                if (this.west_open == false && this.iswest) {
                    this.west_open = true;
                    this._switch(cthis, this.bobject.west.object, this.bobject.center.object, 0, cols, false);
                }
                if (this.east_open == false && this.iseast) {
                    this.east_open = true;
                    this._switch(cthis, this.bobject.center.object, this.bobject.east.object, 1, cols, false);
                }
            }
        },

        _switch: function(cthis, $pre, $next, index, cols, e) {
            if ((index == 0 && this.iswest) || (this.iscolumnlayout && index == 0)) {
                var pre = $pre,
                    center = $next,
                    lwidth = 0,
                    cwidth = 0;
                var leftwidth = pre.width(),
                    centerwidth = center.width();
                if (this.west_open) {
                    cwidth = leftwidth + centerwidth;
                    pre.hide();
                    center.width(cwidth);
                    this.leftval = leftwidth;
                    this.west_open = false;
                } else {
                    var tval = centerwidth - this.leftval;
                    if (tval < 0) {
                        lwidth = centerwidth;
                        center.width(cwidth);
                        pre.width(lwidth);
                        pre.show();
                    } else {
                        lwidth = this.leftval;
                        cwidth = centerwidth - this.leftval;
                        center.width(cwidth);
                        pre.show();
                    }
                    this.west_open = true;
                }
                if (e) {
                    cthis._event("westswitch", null, {
                        "west": {
                            "width": lwidth
                        },
                        "center": {
                            "width": cwidth
                        }
                    });
                }
            } else if (((parseInt(index) + 2) == cols && this.iseast) || (this.iscolumnlayout && index == (parseInt(index) + 2) == cols)) {
                var center = $pre,
                    next = $next,
                    rwidth = 0,
                    cwidth = 0;
                var centerwidth = center.width(),
                    rightwidth = next.width();
                if (this.east_open) {
                    cwidth = centerwidth + rightwidth;
                    next.hide();
                    center.width(cwidth);
                    this.rightval = rightwidth;
                    this.east_open = false;
                } else {
                    var tval = centerwidth - this.rightval;
                    if (tval < 0) {
                        rwidth = centerwidth;
                        center.width(cwidth);
                        next.width(rwidth);
                        next.show();
                    } else {
                        rwidth = this.rightval;
                        cwidth = centerwidth - this.rightval;
                        center.width(cwidth);
                        next.show();
                    }
                    this.east_open = true;
                }
                if (e) {
                    cthis._event("eastswitch", null, {
                        "east": {
                            "width": rwidth
                        },
                        "center": {
                            "width": cwidth
                        }
                    });
                }
            }
        },

        /**
         * @desc 计算宽度
         * @param {containerWidth} 容器的宽度
         * @param {element} 列布局的元素对象
         * @param {cols} 列数
         * @private
         */
        _calculateWidth: function(containerWidth, element, cols) {
            //其他部分的宽度
            var otherWidth = 0;
            if (this.border == true && cols > 1) {
                containerWidth = containerWidth - this.borderWidth * this.bordernumber;
                otherWidth = containerWidth - this.fixColumnWidth;
            }

            otherWidth = containerWidth - this.fixColumnWidth;

            //          jazz.log("*** column *******列布局 容器宽度************"+containerWidth);
            //          jazz.log("*** column *******其他区域宽度**************"+otherWidth);

            //百分比总宽度
            this.percentWidth = 0;
            for (var i = 0; i < cols; i++) {
                var colwidth = this.colwidths[i];
                var tempObject = $(element[i]);
                //判断是否为百分比列宽
                if (!!colwidth && colwidth.indexOf('%') != -1) {
                    var n = parseInt(colwidth.substring(0, colwidth.indexOf('%')));
                    colwidth = (n / 100) * otherWidth;
                    //向上取整数
                    colwidth = Math.ceil(colwidth);
                    tempObject.outerWidth(colwidth);

                    this.percentWidth += tempObject.outerWidth();
                }
                //自适应宽度列
                if ($.trim(colwidth) == '*') {
                    this.emptyColumn = tempObject;
                }
            }

            //          jazz.log("*** column ******百分比列宽度**************"+this.percentWidth);

            //去除固定列、百分比列宽度后，剩余宽度
            this.autoWidth = containerWidth - this.fixColumnWidth - this.percentWidth;

            //判断未指定宽度的列的数量，仅有1列时，计算这列的宽度
            if (this.emptyColumn) {
                this.emptyColumn.outerWidth(this.autoWidth);
            }

            //          jazz.log("*** column ******自适应列的宽度**************"+this.autoWidth);

            //缓存容器宽度,用于监听时进行比较窗体宽度是否发生改变
            this.cacheWidth = containerWidth;
        }

    });


    /**
     * @version 1.0
     * @name jazz.fitlayout
     * @description 自适应高度布局。
     * @constructor
     * @extends jazz.containerlayout
     */
    $.widget("jazz.fitlayout", $.jazz.containerlayout, {

        /** @lends jazz.fitlayout */
        /**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutconfig} layoutconfig配置信息
         */
        layout: function(cthis, container, layoutconfig) {

            var element = cthis.element,
                parent = element.parent();
            element = $(element);
            parent = $(parent);

            //fit布局只监听高度的变化，把高度记录下来做比较
            //因为现在用的resize事件是监听容器大小的变化，在宽度发生变化时，也会触发事件
            //所以一定要用高度去作比较，这样才不会在宽度发生变化时，重新计算
            //缓存父容器对象的高度
            this.cacheParentHeight = 0;
            //当前fit布局对象的高度
            this.cacheElementHeight = 0;
            //缓存多个各兄弟节点的高度
            this.cacheSiblingsHeights = [];

            this._align(element, parent, layoutconfig);

            var $this = this;
            parent.off("resize.parentfitlayout").on("resize.parentfitlayout", function() {
                //jazz.log("resize.parent====");
                //如果父元素是body时，窗体大小的改变都会触发事件，目前没有先不考虑窗体大小的变化对布局的影响, 待后续……
                if ($this.cacheParentHeight != parent.height()) {
                    jazz.log("========fit parent resize===============");
                    $this._align(element, parent, layoutconfig);
                }
            });

            $.each(element.siblings(), function(i, obj) {
                var $obj = $(obj);
                if ($this._isEx($obj)) {
                    $obj.off("resize.siblingsfitlayout").on("resize.siblingsfitlayout", function() {
                        //jazz.log("resize.siblings");
                        //比较兄弟元素高度是否发生了变化
                        if ($this.cacheSiblingsHeights[i] != $obj.outerHeight(true)) {
                            jazz.log("========fit siblings resize===============");
                            $this._align(element, parent, layoutconfig);
                        }
                    });
                }
            });

        },

        /**
         * @desc 设置布局
         * @param {element} 布局类的this.element对象
         * @param {parent}  布局类的父对象
         * @param {layoutconfig} layoutconfig配置信息
         * @param {isfirstrun} 判断是否为首次运行， true 首次运行
         * @private
         */
        _align: function(element, parent, layoutconfig, isfirstrun) {

            //parentHeight是this.element父元素高度
            //elementHeight是当前fit布局对象的高度
            //siblingHeights是this.element的兄弟元素的高度之和(剔除掉不需要fit布局的兄弟节点)
            var parentHeight = 0,
                elementHeight = 0,
                siblingHeights = 0;
            //获取父元素节点名称
            var nodeName = parent[0].nodeName;

            if (nodeName == "BODY") {
                var $body = $(document.body),
                    winHeight = jazz.util.windowHeight();
                //body通过outerHeight(winHeight)设置高度值，不会去除掉margin等高度，所以只能通过下边形式去除
                parentHeight = winHeight - ($body.outerHeight(true) - $body.height());
            } else {
                parentHeight = parent.height();
            }

            //获取被绑定fit元素的全部兄弟节点
            var sibobject = element.siblings();

            //当存在兄弟节点时执行
            if (sibobject.size() > 0) {
                //先将缓存多个兄弟节点的高度数组清空，其后在重新设置值
                this.cacheSiblingsHeights = [];

                var $this = this;
                $.each(sibobject, function(i, obj) {
                    var $obj = $(obj);
                    if ($this._isEx($obj)) {
                        var _h = $obj.outerHeight(true);
                        siblingHeights += _h;
                        $this.cacheSiblingsHeights.push(_h);
                    }
                });
            }

            elementHeight = parentHeight - siblingHeights;

            if (this.cacheParentHeight != parentHeight) {
                //对父容器设置高度值
                parent.outerHeight(parentHeight);
                this.cacheParentHeight = parentHeight;
            }

            //当前fit布局对象的高度发生变化时，才调用回调函数
            if (this.cacheElementHeight != elementHeight) {
                //对fit布局对象设置高度
                element.outerHeight(elementHeight);
                //判断是否存在回调函数
                if (layoutconfig.callback && ($.isFunction(layoutconfig.callback))) {
                    layoutconfig.callback.call(this);
                }
                this.cacheElementHeight = elementHeight;
            }

        },

        /**
         * @desc 设置布局
         * @param {$obj} this.element对象的兄弟对象
         * @private
         */
        _isEx: function($obj) {
            if ($obj.css("display") != "none" && $obj.attr("islayout") != "no" && $obj.css("position") != "fixed" && $obj.css("position") != "absolute") {
                return true;
            } else {
                return false;
            }
        }
    });

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
            var items = container.children(); //获取全部的子对象
            var bc = { //记录前一组件的值
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
        _align: function(cthis, items, container, config, bc) {

            for (var i = 0, len = items.length; i < len; i++) {

                var left = 0;
                if (i == 0) {
                    left = bc.left = 0; //0;
                } else {
                    left = bc.left = bc.width + bc.left;
                }
                bc.width = items.eq(i).outerWidth(true);

                items.eq(i).css({
                    position: 'absolute',
                    top: 0,
                    left: left
                });


            }

            //重新计算容器高度
            if (cthis.options.height === -1) { //外部未指定容器高度，自适应
                container.height(allheight + parseInt(config.margin.top) + parseInt(config.margin.bottom));
            }

        }

    });


    /**
     * @version 1.0
     * @name jazz.rowlayout
     * @description 行布局。
     * @constructor
     * @extends jazz.containerlayout
     */
    $.widget("jazz.rowlayout", $.jazz.containerlayout, {

        /** @lends jazz.rowlayout */
        /**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutconfig} 布局需要的配置参数
         */
        layout: function(cthis, container, layoutconfig) {
            //列布局时，禁止容器出现滚动条
            container.css('overflow', 'hidden');
            var containerHeight = container.height();

            var $this = this;

            this.border = layoutconfig.border || false;
            //layoutconfig.rowheight 0.5版本属性 || layoutconfig.height 1.0版本属性， 同事支持两种写法
            this.rowheights = layoutconfig.rowheight || layoutconfig.height;
            //用于判断是否为 border布局调用isborderlayout=true
            var isborderlayout = layoutconfig.isborderlayout || false;

            //设置border布局参数的默认值， 当不是border布局时，这些参数就取默认值，确保row布局正常使用
            var north_show_border = layoutconfig.north_show_border == false ? false : true;
            var south_show_border = layoutconfig.south_show_border == false ? false : true;

            var north_show_switch = layoutconfig.north_show_switch == false ? false : true;
            var south_show_switch = layoutconfig.south_show_switch == false ? false : true;

            var north_drag = layoutconfig.north_drag == false ? false : true;
            var south_drag = layoutconfig.south_drag == false ? false : true;

            var north_drag_min = layoutconfig.north_drag_min ? parseInt(layoutconfig.north_drag_min) : 0;
            var north_drag_max = layoutconfig.north_drag_max ? parseInt(layoutconfig.north_drag_max) : 0;
            var south_drag_min = layoutconfig.south_drag_min ? parseInt(layoutconfig.south_drag_min) : 0;
            var south_drag_max = layoutconfig.south_drag_max ? parseInt(layoutconfig.south_drag_max) : 0;

            this.north_open = layoutconfig.north_open == false ? false : true;
            this.south_open = layoutconfig.south_open == false ? false : true;

            //isborderlayout == true 为border布局调用
            //broder布局内置了column布局, 所以要通过下边设置border布局所要的参数。
            if (isborderlayout == true) {
                this.isrowlayout = false;
                this.border = true;
                //在border布局中定义的，存储各区域的对象
                this.bobject = layoutconfig.bobject;
                this.isnorth = layoutconfig.regionnumber.north == 1 ? true : false;
                this.issouth = layoutconfig.regionnumber.south == 1 ? true : false;
            } else {
                //用于判断是否为列布局使用，而不是border布局
                this.isrowlayout = true;
            }

            var rows = this.rowheights.length || 0;
            if (rows == 0) {
                jazz.error("行布局的rowheight设置错误, 例： ['200px', '30%', '*']");
            }

            this.borderHeight = 0;

            //计算全部固行的高度 (固定行 px 非固定行 % *)
            this.fixRowHeight = 0;

            //存储非固定行的对象
            this.noFixRow = [];

            //当有收缩按钮时，通过点击收缩按钮后， 记录原始值， 保证能通过这些值还原上一次操作的状态
            this.topval = 0;
            this.bottomval = 0;

            //如果刷新布局时，先判断上一次布局是否存在边框，如果存在则把边框销毁
            if (this.border == true && rows > 1) {
                this.bordernumber = 0;
                if (!this.borderObj) {
                    //用于存放边框对象
                    this.borderObj = [];
                }
                //将上一次生成的dom元素，边框清除
                $.each(this.borderObj, function(i, obj) {
                    $(obj).remove();
                });
                this.borderObj = [];
            }
            var element = container.children(); //行布局的行元素对象

            for (var i = 0; i < rows; i++) {
                var tempObject = $(element[i]);

                tempObject.removeAttr("style"); //去除原有样式
                tempObject.addClass("jazz-row-element");

                if (this.border == true && rows > 1 && (i + 1) != rows) {
                    //border布局时判断是否存在边框
                    if ((isborderlayout == true && ((north_show_border && this.isnorth && i == 0) || (south_show_border && this.issouth && (i + 2) == rows))) || this.isrowlayout) {
                        var div = '<div index="' + i + '" class="jazz-row-border">';
                        if ((isborderlayout == true && ((north_show_switch && this.isnorth && i == 0) || (south_show_switch && this.issouth && (i + 2) == rows))) || (this.isrowlayout && (i == 0 || (i + 2) == rows))) {
                            div += '<div class="jazz-row-btn"></div>';
                        }
                        div += '</div>';
                        var $b = $(div);
                        tempObject.after($b);
                        this.borderObj.push($b);
                        this.bordernumber += 1;

                        if ((isborderlayout == true && ((north_drag == true && this.isnorth && i == 0) || (south_drag == true && this.issouth && (i + 2) == rows))) || this.isrowlayout) {
                            $b.addClass("jazz-row-border-cursor");
                        }

                        //获取边框的高度
                        this.borderHeight = $b.outerHeight() || 0;

                        if (isborderlayout == true && ((north_show_switch && this.isnorth && i == 0) || (south_show_switch && this.issouth && (i + 2) == rows))) {
                            //只允许第一列与最后一列，通过点击按钮进行收缩
                            $b.children(".jazz-row-btn").off("click.rt").on("click.rt", function() {
                                var p = $(this).parent();
                                var $pre = p.prev(),
                                    $next = p.next();
                                var index = p.attr("index");
                                //layoutconfig["height"][index] = 0;
                                //$this.layout(cthis, container, layoutconfig);

                                $this._switch(cthis, $pre, $next, index, rows, true);

                            });
                        }
                    }
                }

                var _rh = this.rowheights[i] + "";
                if (_rh != "*" && _rh.indexOf('%') == -1) { //固定宽度的行
                    tempObject.outerHeight(_rh);
                    this.fixRowHeight = this.fixRowHeight + tempObject.outerHeight();
                } else {
                    //非固定列
                    this.noFixRow.push(tempObject);
                }
            }

            var $this = this;

            container.off('mousedown.row').on('mousedown.row', function(event) {
                var target = event.target,
                    $target = $(target);

                //用于存放位置坐标移动范围, topborder存放拖动时，上边界值  
                //areaheight存放各区域的宽度，通过topborder和areaheight的相加，确定能拖动的底边界值
                //areaobject不同区域的对象
                $this.topborder = [], $this.areaheight = [], $this.areaobject = [];

                if ($target.is('.jazz-row-border')) {
                    //获取被点击边框的索引值
                    var index = parseInt($target.attr("index"));

                    //if判断是否允许在border布局和column布局中，是否允许拖动分割线
                    if ((isborderlayout == true && ((north_drag && index == 0 && $this.isnorth) || (south_drag && (index + 2) == rows && $this.issouth))) || ($this.isdrag == true && $this.isrowlayout)) {

                        //点击border边框时的坐标点
                        $this.row_y = $target.offset().top;

                        //点击到拖动区域时，出现拖动线
                        $this.dragrow = $('<div class="jazz-row-drag" style="display:none" islayout="no"></div>').appendTo(document.body);
                        $this.dragrow.css({
                            display: 'block',
                            top: $this.row_y,
                            left: $target.offset().left,
                            width: $target.width()
                        });

                        //去除在拖动过程中的选中状态
                        if (!$.browser.mozilla) {
                            $(document).on("selectstart", function() {
                                return false;
                            });
                        } else {
                            $("body").css("-moz-user-select", "none");
                        }

                        $.each(container.children(".jazz-row-element"), function(i, obj) {
                            //确定每一列移动的范围
                            $this.topborder.push($(obj).offset().top);
                            $this.areaheight.push($(obj).height());
                            $this.areaobject.push($(obj));
                        });

                        //获取拖动的原始距离值
                        var tdragwidth = $this.areaheight[index];
                        var bdragwidth = $this.areaheight[index + 1];

                        //抛出border布局时，开始拖动的事件
                        if (isborderlayout == true) {
                            if (index == 0 && $this.isnorth) {
                                if (!$this.north_open) {
                                    tdragwidth = 0;
                                }
                                cthis._event("northdragstart", null, {
                                    "north": {
                                        "height": tdragwidth
                                    },
                                    "center": {
                                        "height": bdragwidth
                                    }
                                });
                            } else {
                                if (!$this.south_open) {
                                    bdragwidth = 0;
                                }
                                cthis._event("southdragstart", null, {
                                    "center": {
                                        "height": tdragwidth
                                    },
                                    "south": {
                                        "height": bdragwidth
                                    }
                                });
                            }
                        } else {
                            //抛出开始拖动时的事件
                            cthis._event("rowdragstart", null, {
                                index: index,
                                top: tdragwidth,
                                bottom: bdragwidth
                            });
                        }

                        $(document).off('mousemove.row mouseup.row').on('mousemove.row', function(e) {
                            if ($this.dragrow) {
                                //上侧边界值
                                var topborder = $this.topborder[index];
                                //下侧边界值
                                var bottomborder = $this.topborder[index + 1] + $this.areaheight[index + 1] - $this.dragrow.outerHeight();

                                if ($this.issouth && !$this.south_open && (index + 2) == rows) {
                                    bottomborder = $target.offset().top;
                                }

                                var eY = e.pageY;
                                //确定拖动的范围
                                if (isborderlayout == true && (topborder < eY && eY < bottomborder)) {
                                    if (index == 0 && $this.isnorth) {
                                        if (north_drag_min < eY && eY < north_drag_max) {
                                            $this.moving = eY;
                                            $this.dragrow.css({
                                                top: $this.moving
                                            });
                                        }
                                    } else {
                                        if ((containerHeight - south_drag_max) < eY && eY < (containerHeight - south_drag_min)) {
                                            $this.moving = eY;
                                            $this.dragrow.css({
                                                top: $this.moving
                                            });
                                        }
                                    }
                                } else {
                                    if (topborder < eY && eY < bottomborder) { //确定移动的范围
                                        $this.moving = eY;
                                        $this.dragrow.css({
                                            top: $this.moving
                                        });
                                    }
                                }
                            }
                        }).on('mouseup.row', function(e) {
                            var _bh = $this.areaheight[index + 1];
                            var _th = $this.areaheight[index];
                            //通过this.moving移动的距离判断是否移动
                            if ($this.moving) {
                                var _y = $this.dragrow.offset().top - $this.row_y; //移动的距离
                                var b = $this.areaobject[index + 1];
                                var t = $this.areaobject[index];

                                //border布局
                                if (isborderlayout == true) {
                                    //拖动north
                                    if (index == 0 && $this.isnorth) {
                                        if (!$this.north_open) {
                                            _th = 0;
                                            if (_y > 0) {
                                                $this.north_open = true;
                                            }
                                        }
                                        _bh = _bh - _y;
                                        _th = _th + _y;
                                        b.outerHeight(_bh);
                                        t.show().outerHeight(_th);

                                        //拖动south
                                    } else if ($this.issouth) {
                                        if (!$this.south_open) {
                                            _bh = 0;
                                            if (_y < 0) {
                                                $this.south_open = true;
                                            }
                                        }
                                        _bh = _bh - _y;
                                        _th = _th + _y;
                                        t.outerHeight(_th);
                                        b.show().outerHeight(_bh);
                                    }
                                } else {
                                    //行布局
                                    _bh = _bh - _y;
                                    _th = _th + _y;
                                    b.outerHeight(_bh);
                                    t.outerHeight(_th);
                                }

                                //抛出border布局时，结束拖动的事件
                                if (isborderlayout == true) {
                                    if (index == 0 && $this.isnorth) {
                                        cthis._event("northdragstop", null, {
                                            "north": {
                                                "height": _th
                                            },
                                            "center": {
                                                "height": _bh
                                            }
                                        });
                                    } else {
                                        cthis._event("southdragstop", null, {
                                            "center": {
                                                "height": _th
                                            },
                                            "south": {
                                                "height": _bh
                                            }
                                        });
                                    }
                                } else {
                                    cthis._event("rowdragstop", null, {
                                        index: index,
                                        top: _th,
                                        bottom: _bh
                                    });
                                }
                            } else {
                                if (tdragwidth != _th && bdragwidth != _bh) {
                                    if (isborderlayout == true) {
                                        if (index == 0 && $this.isnorth) {
                                            cthis._event("northdragstop", null, {
                                                "north": {
                                                    "height": _th
                                                },
                                                "center": {
                                                    "height": _bh
                                                }
                                            });
                                        } else {
                                            cthis._event("southdragstop", null, {
                                                "center": {
                                                    "height": _th
                                                },
                                                "south": {
                                                    "height": _bh
                                                }
                                            });
                                        }
                                    } else {
                                        cthis._event("rowdragstop", null, {
                                            index: index,
                                            top: _th,
                                            bottom: _bh
                                        });
                                    }
                                }
                            }

                            $this.dragrow.remove();
                            //将移动的距离清空
                            $this.moving = "";

                            //拖动结束后，还原状态
                            if (!$.browser.mozilla) {
                                $(document).off("selectstart");
                            } else {
                                $("body").css("-moz-user-select", "auto");
                            }

                            $(document).off('mousemove.row mouseup.row');
                        });
                    }
                }
            });

            //容器的总高度
            //var containerHeight = Math.min(container.height(), container.get(0).clientHeight);

            this._calculateHeight(containerHeight, element, rows);

            if (isborderlayout == true) {
                if (this.north_open == false && this.isnorth) { //layoutconfig["centerobject"] broder布局中定义的，中间区域外围的包裹层对象
                    this.north_open = true;
                    this._switch(cthis, this.bobject.north.object, layoutconfig["centerobject"], 0, rows);
                }
                if (this.south_open == false && this.issouth) {
                    this.south_open = true;
                    this._switch(cthis, layoutconfig["centerobject"], this.bobject.south.object, 1, rows);
                }
            }
        },

        _switch: function(cthis, $pre, $next, index, rows, e) {
            if ((index == 0 && this.isnorth) || (this.isrowlayout && index == 0)) {
                var pre = $pre,
                    center = $next,
                    theight = 0,
                    cheight = 0;
                var topheight = pre.height(),
                    centerheight = center.height();
                if (this.north_open) {
                    cheight = topheight + centerheight;
                    pre.hide();
                    center.height(cheight);
                    this.topval = topheight;
                    this.north_open = false;
                } else {
                    var tval = centerheight - this.topval;
                    if (tval < 0) {
                        theight = centerheight;
                        center.height(cheight);
                        pre.height(theight);
                        pre.show();
                    } else {
                        theight = this.topval;
                        cheight = centerheight - this.topval;
                        center.height(cheight);
                        pre.show();
                    }
                    this.north_open = true;
                }
                if (e) {
                    cthis._event("northswitch", null, {
                        "north": {
                            "height": theight
                        },
                        "center": {
                            "height": cheight
                        }
                    });
                }
            } else if (((parseInt(index) + 2) == rows && this.issouth) || (this.isrowlayout && (parseInt(index) + 2) == rows)) {
                var center = $pre,
                    next = $next,
                    bheight = 0,
                    cheight = 0;
                var centerheight = center.height(),
                    bottomheight = next.height();
                if (this.south_open) {
                    cheight = centerheight + bottomheight;
                    next.hide();
                    center.height(cheight);
                    this.bottomval = bottomheight;
                    this.south_open = false;
                } else {
                    var tval = centerheight - this.bottomval;
                    if (tval < 0) {
                        bheight = centerheight;
                        center.height(cheight);
                        next.height(bheight);
                        next.show();
                    } else {
                        bheight = this.bottomval;
                        cheight = centerheight - this.bottomval;
                        center.height(cheight);
                        next.show();
                    }
                    this.south_open = true;
                }
                if (e) {
                    cthis._event("southswitch", null, {
                        "south": {
                            "height": bheight
                        },
                        "center": {
                            "height": cheight
                        }
                    });
                }
            }
        },

        /**
         * @desc 计算宽度
         * @param {containerWidth} 容器的宽度
         * @param {element} 列布局的元素对象
         * @param {rows} 行数
         * @private
         */
        _calculateHeight: function(containerHeight, element, rows) {
            //其他部分的高度
            var otherHeight = 0;
            if (this.border == true && rows > 1) {
                containerHeight = containerHeight - this.borderHeight * this.bordernumber;
                otherHeight = containerHeight - this.fixRowHeight;
            }

            otherHeight = containerHeight - this.fixRowHeight;

            //          jazz.log("*** row *******行布局 容器高度************"+containerHeight);
            //          jazz.log("*** row *******其他区域高度**************"+otherHeight);

            //百分比总宽度
            this.percentHeight = 0;
            for (var i = 0; i < rows; i++) {
                var rowheight = this.rowheights[i];
                var tempObject = $(element[i]);
                //判断是否为百分比列宽
                if (!!rowheight && rowheight.indexOf('%') != -1) {
                    var n = parseInt(rowheight.substring(0, rowheight.indexOf('%')));
                    rowheight = (n / 100) * otherHeight;
                    //向上取整
                    rowheight = Math.ceil(rowheight);
                    tempObject.outerHeight(rowheight);

                    this.percentHeight += tempObject.outerHeight();
                }
                //自适应高度行
                if ($.trim(rowheight) == '*') {
                    this.emptyRow = tempObject;
                }
            }

            //          jazz.log("*** row ******百分比行宽度**************"+this.percentHeight);

            //去除固定列、百分比列高度后，剩余高度
            this.autoHeight = containerHeight - this.fixRowHeight - this.percentHeight;

            //判断未指定宽度的列的数量，仅有1列时，计算这列的宽度
            if (this.emptyRow) {
                this.emptyRow.outerHeight(this.autoHeight);
            }

            //          jazz.log("*** row ******自适应行宽度**************"+this.autoHeight);

            //缓存容器高度,用于监听时进行比较窗体高度是否发生改变
            this.cacheHeight = containerHeight;
        }

    });


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
        /**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 当前组件对象
         * @param {config} 布局需要的配置参数
         * @throws
         * @example
         */
        layout: function(cthis, container, config) {

            container.addClass("jazz-layout");

            var items = container.children(); //获取全部的子对象

            this._align(items, container, config);
            $(container).css("overflow", "hidden");
        },

        /**
         * @desc 设置布局
         * @param {items} 需要布局的组件集合
         * @param {container} 当前布局容器对象
         * @param {config} 组件布局配置对象
         * @private
         */

        _align: function(items, container, config) {
            if (!!items && items.length > 0) {
                var tableWidth = $(container).outerWidth(true) - $(container).scrollLeft();
                var border = config.border;
                this.table = null;
                if (border) {
                    this.table = $("<table border='1px' cellspacing='0' cellpadding='0' style='border-collapse:collapse;table-layout:fixed;width:" + tableWidth + "px;" + "'></table>");
                } else {
                    this.table = $("<table style='border-collapse:collapse;table-layout:fixed;width:" + tableWidth + "px;" + "'></table>");
                }

                this.table.appendTo(container);

                var cols = config.columnwidth.length; //table行数不设定，列数设定

                var colgroup = "<colgroup>";
                for (var i = 0; i < cols; i++) {
                    colgroup += "<col />";
                }
                colgroup += "</colgroup>";
                colgroup = $(colgroup).appendTo(this.table);

                var columnwidth = config.columnwidth;
                if (columnwidth.length == 0) {
                    alert("【table 布局】columnwidth未设置布局列宽，请确定重设。");
                    return false;
                }
                if (!this.validateColumnWidthFormat(columnwidth)) {
                    alert("【table 布局】columnwidth设置数值格式不正确。");
                }
                this.calculateTableWidth($(this.table), columnwidth);

                //2.绑定table布局的resize事件
                var $this = this;
                $this.options.cacheContainerWidth = tableWidth;
                //            $(container).on('resize.tableLayout', function(){
                //              var tableCurrentWidth = $(this).width();
                //              if($this.options.cacheContainerWidth != tableCurrentWidth){
                //                $this.options.cacheContainerWidth = tableCurrentWidth;
                //                $($this.table).width(tableCurrentWidth+"px");
                //                $this.calculateTableWidth(this,columnwidth);
                //              }
                //            });

                this.tbody = $("<tbody></tbody>").appendTo(this.table);

                var tr = null;
                if (!!config.rowheight) {
                    tr = $("<tr style='height:" + config.rowheight + "'></tr>");
                } else {
                    tr = $("<tr></tr>");
                }
                var td = $("<td style='vertical-align: top;'></td>");
                for (var i = 0; i < cols; i++) {
                    var newtd = td.clone();
                    newtd.appendTo(tr);
                }
                tr.clone().appendTo(this.tbody);

                var rowIndex = 0,
                    colIndex = 0;
                for (var i = 0; i < items.length; i++) {
                    var item = items[i];

                    if ($(item).attr('vtype') == "hiddenfield" || $(item).attr('vtype') == "toolbar") {
                        /*$(container).after($(item));
                         if($(item).attr('location')=="top"){
                         $(item).insertBefore($(container).parent());
                         }*/
                        continue;
                    }

                    var rowspanNum = parseInt($(item).attr("rowspan")) || 1;
                    var colspanNum = parseInt($(item).attr("colspan")) || 1;

                    if (colspanNum > cols) {
                        alert("【跨列布局错误】当前容器中第" + (i + 1) + "个元素colspan='" + colspanNum + "' 大于布局列数 cols='" + cols + "'。 ");
                        break;
                    } else {

                        var nextIndex = this.combinedTdCell(item, tr, cols, rowIndex, colIndex, rowspanNum, colspanNum);
                        rowIndex = nextIndex.rowIndex;
                        colIndex = nextIndex.colIndex;
                    }
                }
            }
        },
        calculateTableWidth: function(table, columnWidth) {
            var width_table = $(table).width();
            var re = /^[0-9]+.?[0-9]*$/;
            var fixedColumnWidth = 0;
            var exceptFixedColumnWidth = 0;
            var percentColumnWidth = 0;
            var hasPercentWidth = false;
            var autoWidthNums = 0;
            var tempColumnWidth = [];
            for (var i = 0, m = columnWidth.length; i < m; i++) {
                var temp = columnWidth[i];
                if (temp.indexOf('px') != -1 || re.test(temp)) {
                    fixedColumnWidth += parseFloat(columnWidth[i]);
                }
                if (!hasPercentWidth && temp.indexOf('%') != -1) {
                    hasPercentWidth = true;
                }
                if ($.trim(temp) == '*') {
                    autoWidthNums++;
                }
                tempColumnWidth[i] = temp;
            }
            exceptFixedColumnWidth = width_table - fixedColumnWidth;
            if (hasPercentWidth) {
                //exceptFixedColumnWidth = width_table-fixedColumnWidth;
                for (var i = 0, m = tempColumnWidth.length; i < m; i++) {
                    var temp = tempColumnWidth[i];
                    if (temp.indexOf('%') != -1) {
                        var a = temp.substring(0, temp.indexOf('%'));
                        var b = Math.round((a / 100) * exceptFixedColumnWidth);
                        tempColumnWidth[i] = b + "px"; //将原%数值改为px数值
                        percentColumnWidth += b;
                    }
                }
            }

            if (autoWidthNums > 0) {
                var leftColumnWidth = exceptFixedColumnWidth - percentColumnWidth;
                for (var i = 0, m = tempColumnWidth.length; i < m; i++) {
                    if ($.trim(tempColumnWidth[i]) == '*') {
                        tempColumnWidth[i] = Math.round(leftColumnWidth / autoWidthNums) + "px"; ////将原*星号自适应标志改为px数值
                    }
                }
            }

            //只设置columnWidth中定义的值，对于没有设置列宽的列，不予赋值
            for (var i = 0; i < tempColumnWidth.length; i++) {
                $(table).find("colgroup").children().eq(i).attr("width", tempColumnWidth[i]);
            }
        },
        validateColumnWidthFormat: function(colsWidth) {
            var re = /^[0-9]+.?[0-9]*[(px)%]?$/;
            var flag = true;
            for (var i = 0; i < colsWidth.length; i++) {
                var temp = colsWidth[i];
                if (re.test(temp) || $.trim(temp) == '*') {
                    //符合列宽定义标准
                } else {
                    flag = false;
                    break;
                }
            }
            return flag;
        },

        combinedTdCell: function(item, tr, cols, rowIndex, colIndex, rowspanNum, colspanNum) {
            //1.根据当前的colIndex校验该rowIndex行能否放得下colspanNum的元素
            var flag = true;
            while (flag) {

                if (colIndex + colspanNum > cols) {
                    colIndex = 0;
                    rowIndex++;
                }

                var trs = this.tbody.children("tr").length;
                if (rowIndex + 1 > trs) {
                    $(tr).clone().appendTo(this.tbody);
                    colIndex = 0;
                    flag = false;
                } else {
                    this.tbody.children("tr:eq(" + rowIndex + ")").children("td").each(function(i) {
                        if (i >= colIndex && i < cols) {
                            if ($(this).css("display") != "none") {
                                colIndex = i;
                                return false;
                            }
                        }
                    });
                    if (colIndex + colspanNum > cols) {
                        //colIndex=i,折行，继续下一行校验
                    } else {
                        //校验是否有colspanNum个display的单元格
                        var isContinus = true;
                        this.tbody.children("tr:eq(" + rowIndex + ")").children("td").each(function(i) {
                            if (i >= colIndex && i < colIndex + colspanNum) {
                                if ($(this).css("display") == "none") {
                                    isContinus = false;
                                }
                            }
                        });
                        if (isContinus) {
                            flag = false;
                        } else {
                            colIndex++; //下一个单元格继续校验
                        }
                    }
                }
            }

            //2.放置数据，合并隐藏单元格
            var targetTD = this.tbody.children("tr:eq(" + rowIndex + ")").children("td:eq(" + colIndex + ")");

            $(item).appendTo(targetTD);

            //jazz.log("宽度："+targetTD.width()+"-----"+$(item).parent().width());

            if (rowspanNum > 1 || colspanNum > 1) {
                var trCounts = this.tbody.children("tr").length;
                if (rowspanNum + rowIndex > trCounts) {
                    for (var r = 1; r <= rowspanNum + rowIndex - trCounts; r++) {
                        var newtr = tr.clone();
                        newtr.appendTo(this.tbody);
                    }
                }

                this.tbody.children("tr").each(function(n) {
                    if (n >= rowIndex && n < rowIndex + rowspanNum) {
                        $(this).children("td").each(function(m) {
                            if (m >= colIndex && m < colIndex + colspanNum) {
                                if (n == rowIndex && m == colIndex) {
                                    $(this).attr({
                                        "rowspan": rowspanNum,
                                        "colspan": colspanNum
                                    });
                                } else {
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
            if (colIndex + colspanNum < cols) {
                colIndex += colspanNum;
            } else {
                colIndex = 0;
                rowIndex++;
            }

            var next = true;
            var nextTdIndex = null;
            while (next) {

                var trsum = this.tbody.children("tr").length;
                if (rowIndex + 1 > trsum) {
                    //$(tr).clone().appendTo(this.tbody);
                    colIndex = 0;
                    next = false;
                } else {
                    this.tbody.children("tr:eq(" + rowIndex + ")").children("td").each(function(i) {
                        if (i >= colIndex && i < cols) {
                            if ($(this).css("display") != "none") {
                                colIndex = i;
                                next = false;
                                return false;
                            }
                        }
                    });
                }
                if (next) {
                    colIndex = 0;
                    rowIndex++;
                } else {
                    nextTdIndex = {
                        "rowIndex": rowIndex,
                        "colIndex": colIndex
                    };
                }
            }

            return nextTdIndex;
        },
        hiddenRowByRowIndex: function(rowIndex) {
            var that = this;

            //$(that.element[0]).find("table tr").slice():eq("+(rowIndex-1)+")").css("display","none");
        },
        showRowByRowIndex: function(rowIndex) {
            var that = this;
            $(that.element[0]).find("table tr:eq(" + (rowIndex - 1) + ")").css("display", "");
        }
    });

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
            var items = container.children(); //获取全部的子对象
            var bc = { //记录前一组件的值
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
        _align: function(cthis, items, container, config, bc) {

            //计算左右偏移量
            for (var i = 0, len = items.length; i < len; i++) {
                var top = 0;
                if (i == 0) {
                    top = bc.top = 0;
                } else {
                    top = bc.top = bc.height + bc.top;
                }
                bc.height = items.eq(i).outerHeight(true);

                items.eq(i).css({
                    position: 'absolute',
                    top: top,
                    left: 0
                });
            }
            //重新计算容器高度
            if (cthis.options.height === -1) {
                container.height(bc.height + bc.top);
            }

        }

    });


    /**
     * @version 1.0
     * @name jazz.layout
     * @description 调用布局的工具类, 可以脱离容器直接调用布局。
     * @constructor
     * @extends jazz.component
     */
    $.widget("jazz.layout", $.jazz.component, {

        /** @lends jazz.layout */
        options: {

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

        _init: function() {
            this.layout(this, this.element);
        },

        /**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         */
        layout: function(cthis, container) {
            var options = this.options;
            this.obj = container[jazz.layout[options.layout]]();
            container.data(jazz.layout[options.layout]).layout(cthis, container, this.options.layoutconfig);
        },

        /**
         * @desc 重新装载布局
         * @param {layoutConfig} 布局的配置参数
         * @throws
         * @example $('XXX').layout('reloadLayout', layoutConfig);
         */
        reloadLayout: function(layoutConfig) {
            try {
                this.obj.data(jazz.layout[this.options.layout]).reloadLayout(layoutConfig);
            } catch (e) {
                jazz.error("错误的调用布局的reloadLayout接口");
            }
        }

    });

})(jQuery);

(function($) {

    /**
     * @version 1.0
     * @name jazz.button
     * @description 按钮组件。
     * @constructor
     * @extends jazz.boxComponent
     */
    $.widget("jazz.button", $.jazz.boxComponent, {

        options: /** @lends jazz.button# */ {
            /**
             *@type String
             *@desc 按钮在toolbar组件上显示的位置 "left" 居左  "center" 居中  "right" 居右
             *@default 'left'
             */
            align: 'left',

            /**
             * @type String
             * @desc 自定义按钮的样式类，试用于整个按钮是一个大图片, 如果设置该属性则，优先执行。
             * 例：.xxx { width: 32px; height: 32px; background: url("images/queding.gif") no-repeat;}
             * @default ""
             */
            buttonclass: "",

            /**
             *@type Number
             *@desc button组件元素显示内容（0只有图标 1只有文字 2图标加文字）
             *@default 2
             */
            defaultview: 2,

            /**
             *@type boolean
             *@desc toolbar组件元素是否可用，默认为false
             *@default false
             */
            disabled: false,

            /**
             *@type Number
             *@desc button组件是否可用（0任何时候可用 1选中一条记录时可用 2选中1条及以上记录时可用）---暂不可使用
             *@default 0
             */
            enabletype: 0,

            /**
             *@type String
             *@desc button按钮的图片样式，适用于小图片样式，与iconurl图片路径互斥，优先扶持iconclass
             *@default null
             */
            iconclass: null,

            /**
             *@type String
             *@desc button组件图片url，与iconclass互斥，优先支持iconclass
             *@default null
             */
            iconurl: null,

            /**
             *@type Object
             *@desc button组件图片左右位置，配合iconurl属性使用，默认值left
             *@default left
             */
            iconalign: 'left',

            /**
             *@type Object
             *@desc button组件元素下拉menu数据项
             *@default null
             */
            items: null,

            /**
             *@type String
             *@desc button组件元素name标识
             *@default null
             */
            name: null,

            /**
             *@type String
             *@desc button组件元素显示值
             *@default null
             */
            text: null,
            /**
             *@type String
             *@desc button显示按钮说明
             *@default null
             */
            title: null,

            /**
             *@type String
             *@desc button组件图片配合iconurl使用，切换图片使用
             *@default null
             */
            toggleiconurl: null,

            delay: false,

            delaytime: 150,

            //event
            /**
             *@desc button组件click事件
             *@param {event} 事件
             *@param {data} 数据
             *@event click
             *@example $('#id').button('option', click, function(){ });
             *或:
             *<br/>$("XXX").on("buttonchange",function(event, ui){  <br/>} <br/>});
             *或：
             *function XXX(){……}
             *<div…… click="XXX()"></div> 或 <div…… click="XXX"></div>
             */
            click: null
        },

        /** @lends jazz.button */
        /**
         * @desc 创建组件
         * @private
         */
        _create: function() {
            this.element.addClass("jazz-splitbutton jazz-buttonset jazz-button-state-default");
            if (this.options.title) {
                this.element.attr("title", this.options.title);
            }
            //最外层容器
            this.container = this.element;
            if (this.options.disabled) {
                this.container.addClass('jazz-state-disabled');
            }
            //按钮容器
            this.buttonWrap = $('<div class="jazz-button"></div>').appendTo(this.element);

            //this.vTypeparent = this.element.getParentComponent();
            //this._rederToolbarButton();

            if (jazz.config.delayButtonDisabled == true) {
                this.options.delay = true;
            }
        },

        /**
         * @desc 初始化组件
         * @private
         */
        _init: function() {
            //如果按钮结构已经存在，则删除
            this.buttonWrap.children().remove();

            this._renderButtonDom();
            //绑定按钮事件
            if (!this.options.disabled) {
                this._bindButtonEvents();
            }
            //绑定下拉菜单项事件
            if (this.options.items && this.options.items.length > 0) {
                this._bindMenuButtonEvents();
            }
            /*if(this.vTypeparent && this.vTypeparent.attr("vType")=="toolbar"){
             this.vTypeparent.toolbar("computeToolbarWidthDW");
             }*/
        },

        /**
         * @desc button组合按钮menu绝对定位位置
         * @private
         */
        _alignPanel: function() {
            this.menu.css({
                left: '',
                top: '',
                'z-index': ++jazz.zindex
            }).position({
                my: 'left top',
                at: 'left bottom',
                of: this.container
            });
        },

        /**
         * @desc button组合按钮menu各item绑定事件
         * @private
         */
        _bindMenuButtonEvents: function() {
            var $this = this;

            this.menuList.children().on('mouseover.menuList', function(e) {
                $(this).addClass('jazz-state-hover');
            }).on('mouseout.menuList', function(e) {
                $(this).removeClass('jazz-state-hover');
            }).on('click.menuList', function() {
                $this.hide();
            });

            $(document.body).bind('mousedown.' + this.container.attr('id'), function(e) {
                if ($this.menu.is(":hidden")) {
                    return;
                }

                var target = $(e.target);
                if (target.is($this.element) || $this.element.has(target).length > 0) {
                    return;
                }

                var offset = $this.menu.offset();
                if (e.pageX < offset.left ||
                    e.pageX > offset.left + $this.menu.width() ||
                    e.pageY < offset.top ||
                    e.pageY > offset.top + $this.menu.height()) {

                    $this.element.removeClass('jazz-state-focus jazz-state-hover');
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
         * @desc button组件绑定响应事件
         * @return undefined
         * @private
         * @example  this._bindButtonEvents();
         */
        _bindButtonEvents: function() {
            var element = this.element,
                $this = this;

            //处理gridpanel中toolbar定义的button数据传递问题
            var gridpanel = this.element.parents("[vtype=gridpanel]");

            this.element.off('mouseover.button mouseout.button')
                .on('mouseover.button', function() {
                    if (!$(this).hasClass('jazz-state-disabled')) {
                        $(this).addClass('jazz-toolbar-button-hover');
                    }
                }).on('mouseout.button', function() {
                    $(this).removeClass('jazz-toolbar-button-hover');
                }).on('mousedown.button', function() {
                    if (!$(this).hasClass('jazz-state-disabled')) {
                        //$(this).addClass('jazz-toolbar-button-pressed');
                        //1.实现iconurl与toggleiconurl切换2.字体高亮
                        if ($this.options.toggleiconurl) {
                            $(this).find("span.jazz-button-icon").css("background", "url(" + $this.options.toggleiconurl + ") no-repeat scroll 50% 50%");
                        }
                        $(this).addClass('jazz-toolbar-button-pressed');
                    }
                }).on('mouseup.button', function(e) {
                    //$(this).removeClass('jazz-toolbar-button-pressed');
                    //1.实现iconurl与toggleiconurl切换2.字体取消高亮
                    if ($this.options.toggleiconurl) {
                        $(this).find("span.jazz-button-icon").css("background", "url(" + $this.options.iconurl + ") no-repeat scroll 50% 50%");
                    }
                    $(this).removeClass('jazz-toolbar-button-pressed');
                });

            this.buttonWrap.off('click.button')
                .on('click.button', function(e) {
                    if ($this.options.click) {
                        var data = null;
                        if (gridpanel.length) {
                            data = gridpanel.gridpanel("getSelection");
                        }
                        if ($this.options.delay) {
                            $this._event("click", e, data);
                            //置灰按钮
                            $this.disable();
                            //经过delaytime之后，将按钮重新置为可用
                            setTimeout(function() {
                                $this.enable();
                            }, $this.options.delaytime);
                        } else {
                            $this._event("click", e, data);
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

            if (this.menuButton && this.menuButton.length > 0) {
                this.menuButton.off('click.menuButton').on('click.menuButton', function() {
                    if ($this.menu.is(':hidden'))
                        $this.show();
                    else
                        $this.hide();
                });
            }
        },

        /**
         * @desc vtype形式初始化渲染button（当vtypeparent=toolbar时）
         * @private
         * @example this._rederToolbarButton();
         */
        _rederToolbarButton: function() {
            if (this.vTypeparent && this.vTypeparent.attr("vType") == "toolbar") {
                //如果jazz-toolbar-content-wrap包含有text-align:center,则说明当前是居中排序
                //此后再添加按钮都是直接放到leftToolbar中即可
                var toolbarContentWrap = this.vTypeparent.find('div.jazz-toolbar-content-wrap');
                var leftToolbarContent = this.vTypeparent.find("div.jazz-toolbar-left");
                var rightToolbarContent = this.vTypeparent.find("div.jazz-toolbar-right");
                var textAlign = toolbarContentWrap.css("text-align");
                if (textAlign == "center") {
                    this.container.appendTo(leftToolbarContent);
                } else {
                    var align = this.options.align;
                    var leftnums = leftToolbarContent.children().length;
                    var rightnums = rightToolbarContent.children().length;
                    if ((leftnums > 0 || rightnums > 0) && align == "center") {
                        alert("按钮左或右对齐后，不允许再居中对齐。");
                    } else {
                        if (align == "center") {
                            toolbarContentWrap.css("text-align", "center");
                            this.container.appendTo(leftToolbarContent);
                        } else {
                            if (align == "right") {
                                this.container.appendTo(rightToolbarContent);
                            } else {
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
        _renderButtonDom: function() {

            if (!this.options.buttonclass) {
                var value = this.options.text || 'jazz-button';
                var iconurl = this.options.iconurl,
                    iconclass = this.options.iconclass,
                    iconalign = this.options.iconalign;
                var defaultview = this.options.defaultview;
                var iconurlstyle = 'style="background: url(' + iconurl + ') no-repeat scroll 50% 50% ;"';
                var styleClass = null;

                //defaultview==0  只显示图标    defaultview==1  只有文字     defaultview==2  图标加文字
                if (defaultview == 1) {
                    styleClass = 'jazz-button-text-only';
                } else if (defaultview == 0) {
                    styleClass = 'jazz-button-icon-only';
                    if (iconclass) {
                        this.buttonWrap.append('<span class="jazz-button-icon-left jazz-button-icon ' + iconclass + '" />');
                    } else {
                        this.buttonWrap.append('<span class="jazz-button-icon-left jazz-button-icon" ' + iconurlstyle + '/>');
                    }
                } else {
                    if (iconurl || iconclass) {
                        styleClass = (value === 'jazz-button') ? 'jazz-button-icon-only' : 'jazz-button-text-icon-' + iconalign;
                        if (this.options.iconalign) {
                            if (iconclass) {
                                this.buttonWrap.append('<span class="jazz-button-icon-' + iconalign + ' jazz-button-icon ' + iconclass + '" />');
                            } else {
                                this.buttonWrap.append('<span class="jazz-button-icon-' + iconalign + ' jazz-button-icon" ' + iconurlstyle + '/>');
                            }
                        } else {
                            if (iconclass) {
                                this.buttonWrap.append('<span class="jazz-button-icon-left jazz-button-icon ' + iconclass + '" />');
                            } else {
                                this.buttonWrap.append('<span class="jazz-button-icon-left jazz-button-icon" ' + iconurlstyle + '/>');
                            }
                        }
                    } else {
                        styleClass = 'jazz-button-text-icon-left';
                        this.buttonWrap.append('<span class="jazz-button-icon-left jazz-button-icon jazz-button-icon-default" />');
                    }
                }

                this.buttonWrap.addClass(styleClass);
                this.buttonWrap.append('<span class="jazz-button-text">' + value + '</span>');

            } else {
                //通过样式类，整体控制button的显示风格
                this.buttonWrap.append('<span class="jazz-button-custom-cls ' + this.options.buttonclass + '"></span>');
            }

            var items = this.options.items;
            if (items && items.length > 0) {
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
            this.menu = $('<div class="jazz-menu jazz-menu-dynamic jazz-widget jazz-widget-content jazz-corner-all jazz-helper-clearfix jazz-shadow"></div>').
                append('<ul class="jazz-menu-list jazz-helper-reset"></ul>');
            this.menuList = this.menu.children('.jazz-menu-list');
            var items = this.options.items;
            for (var i = 0, len = items.length; i < len; i++) {
                var item = items[i],
                    menuitem = $('<li class="jazz-menuitem jazz-widget jazz-corner-all" role="menuitem"></li>');
                //link = $('<a class="jazz-menuitem-link ui-corner-all"><span class="jazz-menuitem-icon ui-icon ' + item.icon +'"></span><span class="ui-menuitem-text">' + item.text +'</span></a>');

                var link = null;
                if (!item) {
                    continue;
                }
                if (item['icon']) {
                    link = $('<a class="jazz-menuitem-link jazz-corner-all"><span class="jazz-menuitem-icon jazz-icon" style="background: url(' + item.icon + ') no-repeat scroll 50% 50% ;"></span><span class="jazz-menuitem-text">' + item.text + '</span></a>');
                } else {
                    link = $('<a class="jazz-menuitem-link jazz-corner-all"><span class="jazz-menuitem-text">' + item.text + '</span></a>');
                }

                if (item['url']) {
                    link.attr('href', item.url);
                }

                if (item['click']) {
                    link.on('click.buttongroup', item.click);
                }

                menuitem.append(link).appendTo(this.menuList);
            }

            //可以将下拉菜单项添加到document.body上，然后再定位到container下方
            this.menu.appendTo($(document.body));
        },

        /**
         * @desc 动态改变属性
         * @param {key} 对象的属性名称
         * @param {value} 对象的属性值
         * @private
         */
        _setOption: function(key, value) {
            switch (key) {
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

        /**
         * @desc button组件解绑响应事件
         * @private
         */
        _unbindButtonEvents: function() {
            this.buttonWrap.off('click.button')
            if (this.menuButton && this.menuButton.length > 0) {
                this.menuButton.off('click.menuButton');
            }
        },

        changeButtonOptions: function(opts) {
            this._setOptions(opts);
        },

        /**
         * @desc 计算button组合按钮menu宽度
         * @example $("XXX").button("computeMenuPanel");
         */
        computeMenuPanel: function() {

            this.menu.show();

            var menuitemList = this.menuList.children('.jazz-menuitem');
            var liContentMaxWidth = 0;
            var linkWidthDiff = 0;
            for (var i = 0; i < menuitemList.length; i++) {
                if (i == 0) {
                    var w1 = Math.round($(menuitemList[i]).children('.jazz-menuitem-link').eq(0).outerWidth());
                    var w2 = Math.round($(menuitemList[i]).children('.jazz-menuitem-link').eq(0).width());
                    linkWidthDiff = w1 - w2;
                }

                var temp = 0;
                var linknodes = $(menuitemList[i]).children('.jazz-menuitem-link');
                for (var j = 0; j < linknodes.length; j++) {
                    var spanWidth = 0;
                    $(linknodes[j]).children().each(function() {
                        spanWidth += parseInt($(this).width());
                    });
                    if (spanWidth > temp) {
                        temp = spanWidth;
                    }
                }
                if (temp > liContentMaxWidth) {
                    liContentMaxWidth = temp;
                }
            }

            if ((this.element.outerWidth() + this.menuButton.outerWidth()) < (liContentMaxWidth + linkWidthDiff)) {
                this.menu.width(liContentMaxWidth + linkWidthDiff + "px");
            } else {
                this.menu.innerWidth((this.element.outerWidth() + this.menuButton.outerWidth()) + "px");
            }
            this.menu.hide();
        },

        /**
         * @desc 关闭button，使不可用
         * @example  $('XXX').button('disable');
         */
        disable: function() {
            //alert("disable");
            this.container.addClass('jazz-state-disabled');
            this._unbindButtonEvents();
        },

        /**
         * @desc 打开button，使可用
         * @example  $('XXX').button('enable');
         */
        enable: function() {
            //alert("enable");
            this.container.removeClass('jazz-state-disabled');
            this._bindButtonEvents();
        },

        /**
         * @desc 隐藏按钮
         * @public
         * @example $('XXX').button('show');
         */
        hide: function() {
            this.menuButton.removeClass('jazz-state-focus');
            this.menu.fadeOut('fast');
            //this._trigger('hide', null);
            this._event('hide', null);
        },

        /**
         * @desc button组件高亮按钮选中样式
         * @public
         * @example  $('XXX').button('highlight');
         */
        highlight: function() {
            this.element.trigger('mousedown.button');
        },

        /**
         * @desc 显示按钮
         * @public
         * @example $('XXX').button('show');
         */
        show: function() {
            this._alignPanel();
            this.menu.show();
        },

        /**
         * @desc buttton组件取消高亮按钮选中样式
         * @public
         * @example  $('XXX').button('unhighlight');
         */
        unhighlight: function() {
            this.element.trigger('mouseup.button');
        }
    });

})(jQuery);

(function($) {

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

        options: /** @lends jazz.toolbar# */ {
            /**
             *@type Object
             *@desc toolbar组件元素数据
             *@default null
             */
            items: null,
            /**
             *@type int
             *@desc toolbar折行样式（wrap,换行;scroll,滚动）
             *@default 0
             */
            overflowtype: "scroll",
            /**
             * @desc toolbar排列方向（横向或纵向）,(horizontal/vertical)
             * @type string
             * @default "horizontal"
             */
            orientation: "horizontal"
        },

        /** @lends jazz.toolbar */
        /**
         * @desc 创建组件
         * @private
         */
        _create: function() {
            //生成toolbar基本dom结构，横向和纵向布局都是这个结构
            var toolbardoms = "<div class='jazz-toolbar-scroll-first'></div>" +
                "<div class='jazz-toolbar-content'><div class='jazz-toolbar-content-wrap'>" +
                "<div class='jazz-toolbar-contentarea-first'></div><div class='jazz-toolbar-contentarea-second'></div>" +
                "</div></div>" +
                "<div class='jazz-toolbar-scroll-second'></div>";
            var toolbarclass = 'jazz-toolbar jazz-toolbar-orientation-horizontal';
            if (this.options.orientation == "vertical") {
                toolbarclass = 'jazz-toolbar jazz-toolbar-orientation-vertical';
            }
            this.element.addClass(toolbarclass).append(toolbardoms);

            //缓存toolbar基本dom结构
            this.scollFirst = this.element.find(".jazz-toolbar-scroll-first");
            this.toolbarContent = this.element.find(".jazz-toolbar-content");
            this.toolbarContentWrap = this.element.find(".jazz-toolbar-content-wrap");
            this.toolbarFirst = this.element.find(".jazz-toolbar-contentarea-first");
            this.toolbarSecond = this.element.find(".jazz-toolbar-contentarea-second");
            this.scollSecond = this.element.find(".jazz-toolbar-scroll-second");

            //toolbar纵向排列时，需要根据width或者height预设值定义样式
            if (this.options.orientation == "vertical") {
                if (this.options.height == "auto") {
                    this.toolbarContent.addClass("toolbar-content-default-height");
                }
                if (this.options.width == "auto") {
                    this.toolbarContent.addClass("toolbar-content-default-width");
                    this.toolbarContent.addClass("toolbar-content-default-width");
                    this.element.addClass("toolbar-default-width");
                }
            }
            var vTypeparent = this.element.getParentComponent();
            if (vTypeparent) {
                if (vTypeparent.attr("vtype") == "formpanel") {
                    this.element.addClass('jazz-formpanel-toolbar');
                }
            }
        },
        /**
         * @desc 初始化组件
         * @private
         */
        _init: function() {
            //绑定resize响应事件和滚动事件
            if (this.options.overflowtype != "wrap" || this.options.orientation != "horizontal") {
                this._bindScrollEvent();
            } else {
                this.toolbarFirst.addClass("white-space-normal");
                this.toolbarSecond.addClass("white-space-normal");
            }
            //处理toolbar子元素,不同的组件创建方式：vtype/$()/jazz.widget
            if (this.options.items == null) {
                this._vtypeCreateElements();
            } else {
                this._widgetCreateElements();
            }
        },
        /**
         * @desc vtype形式创建toolbar组件
         * @private
         */
        _vtypeCreateElements: function() {
            var orientation = this.options.orientation;
            var childrens = this.element.children();
            var child = null,
                wrapHtml = "<div class='jazz-toolbar-element'></div>";
            if (orientation == "vertical") {
                //现在纵向对齐，只支持顶部对齐自上往下排列，不支持上中下对齐
                //1.顶部对齐，将所有子元素都放到this.toolbarFirst中
                for (var i = 0, m = childrens.length; i < m - 3; i++) {
                    child = $(childrens[i]);
                    child.wrap(wrapHtml);
                    this.toolbarFirst.append(child.parent());
                }
            } else {
                //现在横向toolbar布局分为左中右对齐，需要分别处理
                //1.居中与（靠左，靠右对齐）不可同时存在，若同时存在，则居中按靠左对齐处理
                if (this.element.children("[align=center]").length == (childrens.length - 3)) {
                    this.toolbarContent.css("text-align", "center");
                }
                for (var i = 0, m = childrens.length; i < m - 3; i++) {
                    child = $(childrens[i]);
                    child.wrap(wrapHtml);
                    if (child.attr("align") == "right") {
                        this.toolbarSecond.append(child.parent());
                    } else {
                        this.toolbarFirst.append(child.parent());
                    }
                }
            }
        },
        /**
         * @desc $()/$.widget形式创建toolbar组件
         * @private
         */
        _widgetCreateElements: function() {
            var items = this.options.items;
            if (!$.isArray(items)) {
                return;
            }
            var orientation = this.options.orientation;
            var child = null,
                wrapHtml = "<div class='jazz-toolbar-element'></div>";
            if (orientation == "vertical") {
                //现在纵向对齐，只支持顶部对齐自上往下排列，不支持上中下对齐
                //1.顶部对齐，将所有子元素都放到this.toolbarFirst中
                for (var i = 0, len = items.length; i < len; i++) {
                    if (items[i]["class"] == "separator") {
                        child = $("<div class='separator'></div>");
                    } else {
                        child = jazz.widget(items[i], this.toolbarFirst);
                    }
                    child.wrap(wrapHtml);
                    this.toolbarFirst.append(child.parent());
                }
            } else {
                //现在横向toolbar布局分为左中右对齐，需要分别处理
                //1.居中与（靠左，靠右对齐）不可同时存在，若同时存在，则居中按靠左对齐处理
                var isAllAlignCenter = true;
                for (var i = 0, len = items.length; i < len; i++) {
                    if (items[i]["align"] != "center") {
                        isAllAlignCenter = false;
                    }
                }
                if (isAllAlignCenter) {
                    this.toolbarContent.css("text-align", "center");
                }
                for (var i = 0, len = items.length; i < len; i++) {
                    if (items[i]["align"] == "right") {
                        if (items[i]["class"] == "separator") {
                            child = $("<div class='separator'></div>");
                        } else {
                            child = jazz.widget(items[i], this.toolbarSecond);
                        }
                        child.wrap(wrapHtml);
                        this.toolbarSecond.append(child.parent());
                    } else {
                        if (items[i]["class"] == "separator") {
                            child = $("<div class='separator'></div>");
                        } else {
                            child = jazz.widget(items[i], this.toolbarFirst);
                        }
                        child.wrap(wrapHtml);
                        this.toolbarFirst.append(child.parent());
                    }
                }
            }
        },
        /**
         * @desc 绑定toolbar组件横向或纵向滚动事件
         * @return undefined
         * @private
         * @example  this._bindScrollEvent();
         */
        _bindScrollEvent: function() {
            var that = this;
            //为this.toolbarFirst和this.toolbarSecond绑定resize事件，根据横向还是纵向
            //宽度或者高度的变化显示滚动箭头
            that.element.on("resize.toolbar", function() {
                that._computeToolbarWidth();
            });
            that.toolbarFirst.on("resize.toolbarFirst", function() {
                that._computeToolbarWidth();
            });
            that.toolbarSecond.on("resize.toolbarSecond", function() {
                that._computeToolbarWidth();
            });

            //绑定滚动箭头滚动事件
            var orientation = this.options.orientation;
            var scrollwidth = 50;
            that.scollSecond.off("click").on("click", function() {
                if (orientation == "horizontal") {
                    var scrollleft = that.toolbarContent.scrollLeft() + scrollwidth;
                    var scrollTotalWidth = that.toolbarContentWrap.width() - that.toolbarContent.width();
                    if (scrollleft > scrollTotalWidth) {
                        scrollleft = scrollTotalWidth;
                    }
                    that.toolbarContent.scrollLeft(scrollleft);
                } else if (orientation == "vertical") {
                    var scrollheight = that.toolbarContent.scrollTop() + scrollwidth;
                    var scrollTotalHeight = that.toolbarContentWrap.height() - that.toolbarContent.height();
                    if (scrollheight > scrollTotalHeight) {
                        scrollheight = scrollTotalHeight;
                    }
                    that.toolbarContent.scrollTop(scrollheight);
                }
            });
            that.scollFirst.off("click").on("click", function() {
                if (orientation == "horizontal") {
                    var scrollleft = that.toolbarContent.scrollLeft() - scrollwidth;
                    that.toolbarContent.scrollLeft(scrollleft);
                } else if (orientation == "vertical") {
                    var scrollheight = that.toolbarContent.scrollTop() - scrollwidth;
                    that.toolbarContent.scrollTop(scrollheight);
                }
            });
        },
        /**
         * @desc 计算toolbar的宽度或者高度，以显示滚动箭头
         * @private
         */
        _computeToolbarWidth: function() {
            var that = this;
            var orientation = this.options.orientation;
            if (orientation == "horizontal") {
                //一、横向布局
                //1.当this.toolbarContentWrap宽度大于this.toolbarContent显示滚动箭头
                var tbw1 = that.toolbarFirst.width();
                var tbw2 = that.toolbarSecond.width();
                var contentWidth = that.toolbarContent.width();
                if (contentWidth < (tbw1 + tbw2)) {
                    that.toolbarContentWrap.width(tbw1 + tbw2);
                    that.scollFirst.show();
                    that.scollSecond.show();
                } else {
                    that.toolbarContentWrap.css({
                        "width": "100%"
                    });
                    that.scollFirst.hide();
                    that.scollSecond.hide();
                }
            } else if (orientation == "vertical") {
                //二、纵向布局
                //1.当this.toolbarContentWrap高度大于this.toolbarContent显示滚动箭头
                var contentHeight = that.toolbarContent.height();
                var contentWrapHeight = that.toolbarContentWrap.height();
                if (contentHeight < contentWrapHeight) {
                    that.scollFirst.show();
                    that.scollSecond.show();
                } else {
                    that.scollFirst.hide();
                    that.scollSecond.hide();
                }
            }
        },
        /**
         * @desc 增加toolbar子元素数据项,相当于调用$('#div_id').toolbar('appendElement',items);
         * 建议使用appendElement接口方法
         * @param {items} 工具条子组件数据项items，格式为数组
         * @example  $('#div_id').toolbar('addElement',items);
         */
        addElement: function(items) {
            this._insertSubElement(items);
        },
        /**
         * @desc 在对应的子组件后部插入toolbar子元素，无相应子元素时，插入到工具条最后面。
         * @param {items} 工具条子组件数据项items，格式为数组
         * @param {name} 匹配toolbar子元素名称name值，可选参数
         * @example  $('#div_id').toolbar('appendElement',items,[name]);
         */
        appendElement: function(items, name) {
            this._insertSubElement(items, name, "after");
        },
        /**
         * @desc 在对应的子组件前部插入toolbar子元素，无相应子元素时，插入到工具条最前面。
         * @param {items} 工具条子组件数据项items，格式为数组
         * @param {name} 匹配toolbar子元素名称name值，可选参数
         * @example  $('#div_id').toolbar('preappendElement',items,[name]);
         */
        prependElement: function(items, name) {
            this._insertSubElement(items, name, "before");
        },
        _insertSubElement: function(items, name, position) {
            if (!$.isArray(items)) {
                return;
            }
            var orientation = this.options.orientation;
            var child = null,
                wrapHtml = "<div class='jazz-toolbar-element'></div>";
            if (orientation == "vertical") {
                //现在纵向对齐，只支持顶部对齐自上往下排列，不支持上中下对齐
                //1.顶部对齐，将所有子元素都放到this.toolbarFirst中
                for (var i = 0, len = items.length; i < len; i++) {
                    if (items[i]["class"] == "separator") {
                        child = $("<div class='separator'></div>");
                    } else {
                        child = jazz.widget(items[i], this.toolbarFirst);
                    }
                    child.wrap(wrapHtml);
                    //this.toolbarFirst.append(child.parent());
                    this._insertSubElementByCondition(name, position, child.parent(), this.toolbarFirst);
                }
            } else {
                //现在横向toolbar布局分为左中右对齐，需要分别处理
                //1.居中与（靠左，靠右对齐）不可同时存在，若同时存在，则居中按靠左对齐处理
                var isAllAlignCenter = false;
                var textAlign = this.toolbarContent.css("text-align");
                if (textAlign == "center") {
                    isAllAlignCenter = true;
                } else {
                    var temp = true;
                    for (var i = 0, len = items.length; i < len; i++) {
                        if (items[i]["align"] != "center") {
                            temp = false;
                        }
                    }
                    var leftnums = this.toolbarFirst.children().length;
                    var rightnums = this.toolbarSecond.children().length;
                    if (leftnums == 0 && rightnums == 0 && temp) {
                        isAllAlignCenter = true;
                    }
                    if (isAllAlignCenter) {
                        this.toolbarContent.css("text-align", "center");
                    }
                }
                for (var i = 0, len = items.length; i < len; i++) {
                    if (items[i]["align"] == "right") {
                        if (items[i]["class"] == "separator") {
                            child = $("<div class='separator'></div>");
                        } else {
                            child = jazz.widget(items[i], this.toolbarSecond);
                        }
                        child.wrap(wrapHtml);
                        //this.toolbarSecond.append(child.parent());
                        this._insertSubElementByCondition(name, position, child.parent(), this.toolbarSecond);
                    } else {
                        if (items[i]["class"] == "separator") {
                            child = $("<div class='separator'></div>");
                        } else {
                            child = jazz.widget(items[i], this.toolbarFirst);
                        }
                        child.wrap(wrapHtml);
                        //this.toolbarFirst.append(child.parent());
                        this._insertSubElementByCondition(name, position, child.parent(), this.toolbarFirst);
                    }
                }
            }
        },
        _insertSubElementByCondition: function(name, position, newEl, tbar) {
            if (name) {
                var target = this.element.find('div[name="' + name + '"]');
                if (target.length > 0) {
                    if (position == "before") {
                        $(target).parent().before(newEl);
                    } else {
                        $(target).parent().after(newEl);
                    }
                } else {
                    if (position == "before") {
                        tbar.prepend(newEl);
                    } else {
                        tbar.append(newEl);
                    }
                }
            } else {
                if (position == "before") {
                    tbar.prepend(newEl);
                } else {
                    tbar.append(newEl);
                }
            }
        },
        /**
         * @desc 移除toolbar子元素
         * @param {name} 工具条子组件name名称值
         * @example  $('#div_id').toolbar('removeElement','name');
         */
        removeElement: function(name) {
            if (!name) {
                return;
            }
            var $this = this;
            $this.element.find('div[name="' + name + '"]').each(function(i) {
                $(this).parent().remove();
            });
        },
        /**
         * @desc 隐藏toolbar子元素
         * @param {name} 工具条子组件name名称值
         * @example  $('#div_id').toolbar('hideElement','name');
         */
        hideElement: function(name) {
            if (!name) {
                return;
            }
            var $this = this;
            $this.element.find('div[name="' + name + '"]').each(function(i) {
                $(this).parent().hide();
            });
        },
        /**
         * @desc 显示toolbar子元素
         * @param {name} 工具条子组件name名称值
         * @example  $('#div_id').toolbar('showElement','name');
         */
        showElement: function(name) {
            if (!name) {
                return;
            }
            var $this = this;
            $this.element.find('div[name="' + name + '"]').each(function(i) {
                $(this).parent().show();
            });
        },
        /**
         * @desc 移除toolbar按钮
         * @param {name} 工具条按钮name名称值
         * @example  $('#div_id').toolbar('removeButton','name');
         */
        removeButton: function(name) {
            this.removeElement(name);
        },
        /**
         * @desc 隐藏toolbar按钮
         * @param {name} 工具条按钮name名称值
         * @example  $('#div_id').toolbar('hideButton','name');
         */
        hideButton: function(name) {
            this.hideElement(name);
        },
        /**
         * @desc 显示toolbar按钮
         * @param {name} 工具条按钮name名称值
         * @example  $('#div_id').toolbar('showButton','name');
         */
        showButton: function(name) {
            this.showElement(name);
        },
        /**
         * @desc toolbar按钮不可用
         * @param {name} 工具条按钮name名称值
         * @example  $('#div_id').toolbar('disableButton','name');
         */
        disableButton: function(name) {
            if (!name) {
                return;
            }
            var $this = this;
            $this.element.find('div[name="' + name + '"]').each(function(i) {
                $(this).button("disable");
            });
        },
        /**
         * @desc toolbar按钮使可用
         * @param {name} 工具条按钮name名称值
         * @example  $('#div_id').toolbar('enableButton','name');
         */
        enableButton: function(name) {
            if (!name) {
                return;
            }
            var $this = this;
            $this.element.find('div[name="' + name + '"]').each(function(i) {
                $(this).button("enable");
            });
        },
        /**
         * @desc toolbar组件高亮按钮选中样式
         * @param {name} 工具条按钮name名称值
         * @public
         * @example  $('#div_id').toolbar('highlightButton',name);
         */
        highlightButton: function(name) {
            if (!name) {
                return;
            }
            this.element.find('div[name="' + name + '"]').button("highlight");
        },
        /**
         * @desc toolbar组件取消高亮按钮选中样式
         * @param {name} 工具条按钮name名称值
         * @public
         * @example  $('#div_id').toolbar('unhighlightButton',name);
         */
        unhighlightButton: function(name) {
            if (!name) {
                return;
            }
            this.element.find('div[name="' + name + '"]').button("unhighlight");
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

})(jQuery);

(function($) {

    /**
     * @description 图片查看器，支持缩放、拖动、旋转操作
     * <br>缩放支持鼠标双击和鼠标滚轴缩放两种方式
     * <br>旋转操作支持IE9+、chrome、Firefox浏览器
     * @version 1.0
     * @name jazz.ImageView
     * @constructor
     * @extends jazz.container
     */
    $.widget('jazz.imageviewer', $.jazz.container, {
        options: /** @lends jazz.ImageView# */ {

            /**
             *@type String
             *@desc 图片地址
             *@default null
             */
            imagesrc: null,

            /**
             *@type Number
             *@desc 图片宽度
             *@default 400
             */
            width: 400,

            /**
             *@type Number
             *@desc 图片高度
             *@default 300
             */
            height: 300,

            /**
             * @type Array
             * @desc 用户自定义按钮
             * @default null
             */
            buttons: null,

            /**
             *@type Number
             *@desc 图片显示的最小宽度
             *@default 120
             */
            min_width: 120,

            /**
             *@type Number
             *@desc 图片显示的最小高度
             *@default 90
             */
            min_height: 90,

            /**
             *@type Boolean
             *@desc 是否支持拖拽事件
             *@default true
             */
            iscandrag: true, //rename

            /**
             *@type Boolean
             *@desc 是否支持旋转
             *@default true
             */
            isrotate: false,

            /**
             *@type Boolean
             *@desc 是否显示工具条
             *todo
             *@default true
             */
            isshowtoolbar: true,

            /**
             *@type Boolean
             *@desc 是否显示右键菜单
             *@default false
             */
            isshowcontextmenu: false,

            /**
             *@type Boolean
             *@desc 是否显示水印
             *@default true
             */
            isshowwatermark: true,

            /**
             *@type Number
             *@desc 图片被放大的最大倍率
             *@default 1.5
             */
            maxzoom: 1,

            /**
             * @type String
             * @desc 水印图片的地址
             * @default null
             */
            watermarkurl: null

        },

        /** @lends jazz.ImageView */

        /**
         * @desc 创建组件
         * @private
         */
        '_create': function() {
            //确保传入的宽高是数字
            this._super();
            this.options.width = parseInt(this.options.width);
            this.options.height = parseInt(this.options.height);
            //创建工具条容器
            this.toolbarWrap = $("<div class='jazz-imageview-toolbar'><div name='jazz-imageview-toolbar-" + (jazz.getRandom()) + "'></div></div>")
                .appendTo(this.element);

            if (!this.element.attr('id')) {
                this.element.uniqueId();
            }

            if (!this.element.attr('name')) {
                this.element.attr('name', 'jazz-imageviewer-' + jazz.getRandom());
            }

            this._setupContainer();
            this._initToolbar();
            this._initWatermark();
            this.loadImage(this.options.imagesrc);
            this._setupCSS();
            this._initContextMenu();
        },

        /**
         * @desc 初始化
         * @private
         */
        '_init': function() {
            this._bindEvent();
        },

        /**
         * @desc 初始化工具条
         * @private
         */
        '_initToolbar': function() {
            var $this = this,
                btns;
            if (!$this.options.isshowtoolbar) {
                return;
            }
            //添加放大、缩小按钮
            btns = [{
                'vtype': 'button',
                'align': 'right',
                'text': '放大',
                'iconclass': 'jazz-btn-zoomin',
                'name': 'jazz-imageview-button-zoomin',
                'defaultview': 2,
                'click': function() {
                    $this.zoom('in');
                }
            }, {
                'vtype': 'button',
                'align': 'right',
                'text': '缩小',
                'iconclass': 'jazz-btn-zoomout',
                'name': 'jazz-imageview-button-zoomout',
                'defaultview': 2,
                'click': function() {
                    $this.zoom('out');
                }
            }];
            //添加左转、右转按钮
            if ($this.options.isrotate) {
                btns = btns.concat([{
                    'vtype': 'button',
                    'align': 'right',
                    'text': '左转',
                    'iconclass': 'jazz-btn-rotate-left',
                    'defaultview': 2,
                    'click': function() {
                        $this._rotate(90);
                    }
                }, {
                    'vtype': 'button',
                    'align': 'right',
                    'text': '右转',
                    'iconclass': 'jazz-btn-rotate-right',
                    'defaultview': 2,
                    'click': function() {
                        $this._rotate(270);
                    }
                }]);
            }
            if (jazz.isArray(this.options.buttons)) {
                btns = btns.concat(this.options.buttons);
            }

            //初始化工具条
            $this.toolbarWrap.find('>div').toolbar({
                'items': btns
            });
        },

        /**
         * @desc 获取工具条对象
         * @returns
         */
        'getToolbar': function() {
            if (this.options.isshowtoolbar) {
                return this.toolbarWrap.find('>div');
            }
        },

        '_initWatermark': function() {
            if (this.options.isshowwatermark) {
                this.watermark = $("<div class='jazz-imageview-watermark'></div>")
                    .appendTo(this.element);
                this.addWatermark();
            }
        },

        /**
         * @desc 图片旋转
         * 〈br> 旋转使用两种方式实现，优先考虑css3的旋转，其次兼容ie的滤镜方式
         * @param degree 旋转角度
         * @private
         */
        '_rotate': function(degree) {
        	
            var transform,
                styles = document.createElement('div').style,
                img = this.imgWrap[0],
                cosa, sina, matrix;
            $.each(['transform', 'MozTransform',
                'webkitTransform', 'OTransform',
                'msTransform'
            ], function(i, t) {
                if (t in styles) {
                    transform = t;
                    return false;
                }
            });
            this.imgWrap.data('degree') && (degree += this.imgWrap.data('degree'));
            degree = degree % 360;
            this.imgWrap.data('degree', degree);

            cosa = Math.cos(degree * Math.PI / 180);
            sina = Math.sin(degree * Math.PI / 180);
            (degree == 90 || degree == 270) && (cosa = 0);
            (degree == 180) && (sina = 0);

            matrix = {
                M11: cosa,
                M12: (-1 * sina),
                M21: sina,
                M22: cosa
            };
            //for IE6 IE7 IE8 但是不支持IE10仿真出来的低版本IE
            if (!transform) {
            	//for IE6 IE7 IE8
                img.style.filter = "progid:DXImageTransform.Microsoft.Matrix(M11=" + matrix.M11 + ",M12=" + (-1 * matrix.M12) + ",M21=" + (-1 * matrix.M21) + ",M22=" + matrix.M22 + ",SizingMethod='auto expand')";
            } else {
                //for IE9+ standard, firefox, chrome
                img.style[transform] = "matrix(" + matrix.M11 + ", " + matrix.M12 + ", " + matrix.M21 + ", " + matrix.M22 + ", 0, 0)";
            }
            //图片被旋转90度后，设置宽高互换标志方便 _getImgSize() 方法调用
            if (degree / 90 % 2 == 1) {
                this.trans = true;
            } else {
                this.trans = false;
            }
            //当图片旋转后不在画布内部区域时，进行调整
            this._checkRectCross();
            
            //this._checkPosition();
            // jazz-helper-draged
        },
        '_computeCenterPoint': function(){
        	var img_width = this.imgWrap.width();
        	var img_height = this.imgWrap.height();
        	var ox = this.imgWrap.offset().left;
        	var oy = this.imgWrap.offset().top;
        	this.centerpoint_x = ox+img_width/2;
        	this.centerpoint_y = oy+img_height/2;
        },
        '_checkRectCross': function(){
        	var w = this.imgWrap.width();
        	var h = this.imgWrap.height();
        	var x = this.imgWrap.offset().left;
        	var y = this.imgWrap.offset().top;
        	
        	var canvas_width = this.element.width();
        	var canvas_height = this.element.height();
        	
        	var rect1 = {"minx1":0,"miny1":0,"maxx1":canvas_width,"maxy1":canvas_height};
        	var rect2 = {"minx2":x,"miny2":y,"maxx2":x+w,"maxy2":y+h};
        	//若是出现负数就做平移
        	var minx = Math.max(rect1.minx1, rect2.minx2);
			var miny = Math.max(rect1.miny1, rect2.miny2);
			var maxx = Math.min(rect1.maxx1, rect2.maxx2);
			var maxy = Math.min(rect1.maxy1, rect2.maxy2);
        	if((minx===0)||((minx > maxx)||(miny > maxy))){
        		var w2 = canvas_width/2;
        		var h2 = canvas_width/2;
        		this.imgWrap.css({"left":w2-w/2,"top":h2-h/2});
        	}
        },

        /**
         * @desc 设置容器的大小
         * @private
         */
        '_setupContainer': function() {
            this.element.addClass('jazz-imageview-wrap');
            this.element.css({
                width: (this.options.width + 'px'),
                height: (this.options.height + 'px')
            });
            this.toolbarWrap.css({
                width: (this.options.width + 'px')
            });
        },

        /**
         * @desc 加载图片
         * @param imgSrc 图片地址
         */
        'loadImage': function(imgSrc) {
        	
            var loading = this.element.loading({
                'text': '正在加载图片...'
            });
            //创建图片容器
            if(this.imgWrap){
            	this.imgWrap.removeClass();
                this.imgWrap.addClass("jazz-imageview-image jazz-imageview-minsize");
                this.imgWrap.attr("src",imgSrc);
            }else{
            	this.imgWrap = $("<img src='" + imgSrc + "' alt='' class='jazz-imageview-image jazz-imageview-minsize' />")
            	    .appendTo(this.element)
            	    .wrap("<a class='jazz-helper-link' href='javascript:;'></a>");
            }
            
            /* 记录原始尺寸,记录图片初始化显示尺寸 */
            this.imgWrap.load(function() {
                var width = this.naturalWidth || width,
                    height = this.naturalHeight || height,
                    $wrap = $(this).parents(".jazz-imageview-wrap"),
                    wrapWidth = $wrap.width(),
                    wrapHeight = $wrap.height(),
                    minWidth = wrapWidth,
                    minHeight = wrapHeight;

                $(this).data('size', {
                    'width': width,
                    'height': height
                });
                //计算图片显示的初始化尺寸
                if (width >= height) {
                    minHeight = Math.floor(wrapWidth * height / width);
                } else {
                    minWidth = Math.floor(wrapHeight * width / height);
                }
                if (width <= wrapWidth && height <= wrapHeight) {
                    minWidth = width;
                    minHeight = height;
                    //缓存最大缩放倍率
                    $wrap.data('maxZoom', (wrapWidth / width).toFixed(1));
                }
                //设置图片初始化尺寸显示
                $(this).data('minSize', {
                    'width': minWidth,
                    'height': minHeight
                });
                $(this).css({
                    'width': minWidth,
                    'height': minHeight
                });
                //debugger;
                //缓存最小缩放倍率
                $wrap.data('minZoom', (minWidth / width).toFixed(1));

                //保证小图片水平居中显示
                if (minWidth < wrapWidth) {
                    $(this).css({
                        'left': (wrapWidth - minWidth) / 2 + 'px'
                    });
                }
                //保证小图片垂直居中显示
                if (minHeight < wrapHeight) {
                    $(this).css({
                        'top': (wrapHeight - minHeight) / 2 + 'px'
                    });
                }
                $wrap.data("minPos", {
                    'left': (wrapWidth - minWidth) / 2 + 'px',
                    'top': (wrapHeight - minHeight) / 2 + 'px'
                });
                //隐藏加载动画
                $wrap.loading('hide');
            });
        },

        /**
         * @desc 图片装配样式
         * @private
         */
        '_setupCSS': function() {
            this.imgWrap.css({
                'position': 'absolute',
                'top': 0,
                'left': 0
            });
            if (this.options.iscandrag) {
                this.imgWrap.css({
                    'cursor': 'move'
                });
            }
        },

        /**
         * @desc 绑定事件
         * @private
         */
        '_bindEvent': function() {
            var $this = this,
                recoupLeft = 0,
                recoupTop = 0;
            //绑定拖动事件
            if (this.options.iscandrag) {
                this.imgWrap.draggable({
                    'start': function(e, ui) {
                        var left = parseInt($(this).css('left'), 10),
                            top = parseInt($(this).css('top'), 10);
                        left = isNaN(left) ? 0 : left;
                        top = isNaN(top) ? 0 : top;
                        recoupLeft = left - ui.position.left;
                        recoupTop = top - ui.position.top;

                        $this._hideContextMenu();
                        $this.imgWrap.data('beforeOffset', $this.imgWrap.offset()).addClass("jazz-helper-draging");
                    },
                    'drag': function(e, ui) {
                        //修复旋转90度之后拖动时位置偏移的问题
                        ui.position.left += recoupLeft;
                        ui.position.top += recoupTop;
                    },
                    'stop': function() {
                        /**
                         * 拖动释放的时候需要判断
                         * 图片是否被拖动到容器外面
                         */
                        $this._checkPosition();
                        $this.imgWrap.removeClass("jazz-helper-draging").addClass("jazz-helper-draged");
                        //当鼠标拖动移出当前document时，是不会触发draggable组件的mouseup事件，出现再次无法拖动bug
                        //但是当鼠标再次移回至当前document时，便会触发stop
                        //jquery.ui.draggable中利用document.mouseup事件监听同一个window下的可拖动组件
                        //解决方法：stop时触发当前同一document的mouseup事件
                        $(document).trigger("mouseup");
                        
                    }
                });
                this.imgWrap.on("mousedown",function(){
                	$(this).focus();
                });
                if (this.options.isshowwatermark) {
                    //          && (jazz.isIE(6) || jazz.isIE(7) || jazz.isIE(8))){
                    /*this.watermark.draggable({
                     'cursor': 'pointer',
                     'start': function(e, ui){
                     $this._hideContextMenu();
                     $this.imgWrap.data('beforeOffset', $this.imgWrap.offset());
                     $this.watermark.data('beforeOffset', $this.watermark.offset());
                     },
                     'drag': function(e, ui){

                     },
                     'stop': function() {
                     */
                    /**
                     * 拖动释放的时候需要判断
                     * 图片是否被拖动到容器外面
                     */
                    /*
                     $this.watermark.offset($this.watermark.data('beforeOffset'));
                     $this._checkPosition();
                     }
                     });
                     */
                    /*this.watermark.on('mousedown', function(e){
                     this.imageOffset = this.imgWrap.offset();
                     if(!$this._isInRange(e.pageX, e.pageY, 'image')){
                     return;
                     }

                     });*/
                }
            }
            //绑定鼠标双击放大事件
            this.element.off('dblclick.viewer')
                .on('dblclick.viewer', function(e) {
                    var zoomSize = $this._calZoomSize();
                    if ($this.imgWrap.hasClass("jazz-helper-draging")) {
                        return;
                    }
                    if ($this._isInRange(e.pageX, e.pageY, 'toolbar')) {
                        return;
                    }
                    if ($this.imgWrap.hasClass('jazz-imageview-minsize')) {
                        $this._setImgSize(zoomSize['max'], $this.imgWrap.data('trans'));
                        $this.imgWrap.removeClass('jazz-imageview-minsize');
                    } else {
                        $this._setImgSize(zoomSize['min'], $this.imgWrap.data('trans'));
                        $this.imgWrap.addClass('jazz-imageview-minsize');
                        $this.imgWrap.offset($this.element.data('minPos'));
                    }

                    $this._checkPosition();
                    $this._adjustImgPos();
                    //$this._updatePosition(e.pageX, e.pageY);
                    $this._hideContextMenu();
                });
            //DOMMouseScroll 兼容Firefox
            this.imgWrap.off('mousewheel.viewer DOMMouseScroll.viewer')
                .on('mousewheel.viewer DOMMouseScroll.viewer', function(e) {
                    $this._onMouseWheel(e);
                });

            //绑定右键菜单事件
            this._bindContextMenuEvent();

            //绑定工具条事件
            if (!$this.options.isshowtoolbar) {
                return;
            }
            //使用hover事件，确保工具条能够正确显示和隐藏
            this.element.find('.jazz-helper-link').hover(function() {
                $this.toolbarWrap.show();
            }, function() {});
            this.element.hover(function() {
                    $this.toolbarWrap.show();
                }
                /*, function() {
                 //如果当前鼠标右键被点击，则离开容器区域，工具条不隐藏
                 if ($this.element.hasClass("jazz-imageview-dblclicked")) {
                 return;
                 }
                 $this.toolbarWrap.hide();
                 }*/
            );
            this.toolbarWrap.hover(function() {
                $this.toolbarWrap.show();
            });
        },

        /**
         * @desc 不允许容器留有空白
         * @param imgPos
         * @private
         */
        '_checkPosition': function() {
        	
        	var transform,
                styles = document.createElement('div').style;
            $.each(['transform', 'MozTransform',
                'webkitTransform', 'OTransform',
                'msTransform'
            ], function(i, t) {
                if (t in styles) {
                    transform = t;
                    return false;
                }
            });
        	
            var wrapOffset = this.wrapPos || (this.wrapPos = this.element.offset()), //容器的相对位置
                imgOffset = this.imgWrap.offset(), //图片的相对位置
                imgSize = this._getImgSize(), //图片的当前尺寸
                wrapWidth = this.options.width, //容器宽度
                wrapHeight = this.options.height, //容器高度
                vwidth = this.trans ? 'height' : 'width',
                vheight = this.trans ? 'width' : 'height';

            //1. 左边拖出去
            if (wrapWidth >= imgSize[vwidth]) {
               
            	if (wrapOffset['left'] > imgOffset['left']) {
                    this.imgWrap.offset({
                        'left': this.imgWrap.data('beforeOffset')['left']
                        //'left': 0
                    });
                    //return;
                }
            	
            	/*if (wrapOffset['left'] > imgOffset['left']) {
                	console.log("wrapOffset['left']====="+wrapOffset['left']+"=====imgOffset['left']===="+imgOffset['left'])
                    this.imgWrap.offset({
                        //'left': this.imgWrap.data('beforeOffset')['left']
                        'left': 0
                    });
                    //return;
                }*/
                /*if (imgOffset['left']<0) {
                    this.imgWrap.offset({
                        'left': this.imgWrap.data('beforeOffset')['left']
                        //'left': 0
                    });
                    //return;
                }*/
            } else {
            	
            	if (!transform) {
	            	//for IE6 IE7 IE8
	            	vwidth = 'width',
                	vheight = 'height';
                	//alert("ie8....");
	            }else{
	            	//alert("ie9+....");
	            	//alert("wrapWidth+Math.abs(imgOffset['left'])> imgSize[vwidth]===="+(imgSize[vwidth] + imgOffset['left'] < wrapOffset['left'] + wrapWidth))
	            	
	            }
            	if (imgSize[vwidth] + imgOffset['left'] < wrapOffset['left'] + wrapWidth) {
                    this.imgWrap.offset({
                        'left': (wrapWidth - imgSize[vwidth] + wrapOffset['left'])
                    });
                }
            	/*if (!transform) {
	            	//for IE6 IE7 IE8
	            	vwidth = 'width',
                	vheight = 'height';
                	//alert("ie8....");
                	if (imgSize[vwidth] + imgOffset['left'] < wrapOffset['left'] + wrapWidth) {
	                	this.imgWrap.offset({
	                        //'left': (wrapWidth - imgSize['width'] + wrapOffset['left'])
	                    	'left': (wrapWidth - imgSize[vwidth] + wrapOffset['left'])
	                    });
	                }
	            }else{
	            	//alert("ie9+....");
	            	//alert("wrapWidth+Math.abs(imgOffset['left'])> imgSize[vwidth]===="+(imgSize[vwidth] + imgOffset['left'] < wrapOffset['left'] + wrapWidth))
	            	if (imgSize[vwidth] + imgOffset['left'] < wrapOffset['left'] + wrapWidth) {
	            		alert("reset img offset....");
	                    this.imgWrap.offset({
	                        'left': (wrapWidth - imgSize[vwidth] + wrapOffset['left'])
	                    });
	                }
	            }*/
	            
            	
            	
            	
            	/*if (!transform) {
	            	//for IE6 IE7 IE8
	            	vwidth = 'width',
                	vheight = 'height';
	            }
	            if (imgSize[vwidth] + imgOffset['left'] < wrapOffset['left'] + wrapWidth) {
                    this.imgWrap.offset({
                        //'left': (wrapWidth - imgSize['width'] + wrapOffset['left'])
                    	'left': (wrapWidth - imgSize[vwidth] + wrapOffset['left'])
                    });
                }*/
            	/*if (wrapWidth+Math.abs(imgOffset['left'])> imgSize[vwidth]) {
                    this.imgWrap.offset({
                        'left': (wrapWidth - imgSize[vwidth] + wrapOffset['left'])
                    });
                }*/
            }

            //2. 右侧拖出去
            if (wrapWidth >= imgSize[vwidth]) {
                if (imgOffset['left'] + imgSize[vwidth] > wrapOffset['left'] + wrapWidth) {
                    this.imgWrap.offset({
                        'left': this.imgWrap.data('beforeOffset')['left']
                    });
                    //return;
                }
            } else {
                if (imgOffset['left'] > wrapOffset['left']) {
                    this.imgWrap.offset({
                        'left': wrapOffset['left']
                    });
                }
            }
            //3. 上方拖出去
            if (wrapHeight >= imgSize[vheight]) {
                if (wrapOffset['top'] > imgOffset['top']) {
                    this.imgWrap.offset({
                        'top': this.imgWrap.data('beforeOffset')['top']
                    });
                    //return;
                }
            } else {
            	//for IE6 IE7 IE8 但是不支持IE10仿真出来的低版本IE
	            if (!transform) {
	            	//for IE6 IE7 IE8
	            	vwidth = 'width',
                	vheight = 'height';
	            }
	            if (imgSize[vheight] + imgOffset['top'] < wrapHeight + wrapOffset['top']) {
                    this.imgWrap.offset({
                        'top': (wrapHeight + wrapOffset['top'] - imgSize[vheight])
                    });
                }
                /*if (wrapHeight+Math.abs(imgOffset['top'])> imgSize[vheight]) {
                    this.imgWrap.offset({
                        'top': (wrapHeight + wrapOffset['top'] - imgSize[vheight])
                    });
                }*/
            }

            //4. 下面拖出去
            if (wrapHeight >= imgSize[vheight]) {
                if (imgOffset['top'] + imgSize[vheight] > wrapOffset['top'] + wrapHeight) {
                    this.imgWrap.offset({
                        'top': this.imgWrap.data('beforeOffset')['top']
                    });
                    //return;
                }
            } else {
                if (imgOffset['top'] > wrapOffset['top']) {
                    this.imgWrap.offset({
                        'top': wrapOffset['top']
                    });
                }
            }
        },

        /**
         * @desc 双击之后, 将焦点移动到容器中心
         * @private
         */
        '_updatePosition': function(x, y) {
            return;
            var wrapOffset, imgOffset, imgSize, oldImgSize,
                wrapWidth = this.options.width,
                wrapHeight = this.options.height,
                offsetX, offsetY,
                wrapCenter;

            //恢复图片最小显示
            if (this.imgWrap.hasClass('jazz-imageview-minsize')) {
                this.imgWrap.css(this.element.data('minPos'));
                return;
            }
            imgOffset = this.imgWrap.offset(); //图片原始位移
            wrapOffset = this.element.offset(); //容器的位移
            imgSize = this._getImgSize(), //图片当前的尺寸
                oldImgSize = this.element.data('oldImgSize'); //图片的旧尺寸
            wrapCenter = this.wrapCenter || (this.wrapCenter = {
                x: (wrapOffset['left'] + wrapWidth / 2),
                y: (wrapOffset['top'] + wrapHeight / 2)
            });
            //1. 找到(x, y)在图片上的位置
            var rX = Math.floor((x - imgOffset['left']) / oldImgSize['width'] * imgSize['width'] + imgOffset['left']),
                rY = Math.floor((y - imgOffset['top']) / oldImgSize['height'] * imgSize['height'] + imgOffset['top']);
            offsetX = imgOffset['left'] + wrapCenter['x'] - rX;
            offsetY = imgOffset['top'] + wrapCenter['y'] - rX;

            //设置新的
            this.imgWrap.offset({
                'top': offsetX,
                'left': offsetY
            });
            /*if(this.imgWrap.data('trans')){
             this.imgWrap.offset({
             'top': offsetX,
             'left': offsetY
             });
             }else{
             this.imgWrap.offset({
             'top': offsetX,
             'left': offsetY
             });
             }*/

        },

        /**
         * @desc 缩放方法
         * @param type 放大|缩小 ==> in|out
         */
        'zoom': function(type) {
            type = type || 'in';
            var zoomStep = 1,
                zoom_step = this.zoom_step ? this.zoom_step : (this.zoom_step = jazz.config.zoomStep),
                oldImgSize;

            if (!this._isZoom(type)) {
                return false;
            }
            this.imgWrap.data('beforeOffset', this.imgWrap.offset());
            //计算本次缩放倍率
            type == 'in' ? (zoomStep += zoom_step) : (zoomStep -= zoom_step);
            oldImgSize = this._getImgSize();
            this.element.data('oldImgSize', oldImgSize);
            //设置缩放后的尺寸
            var imgSize = {
                width: Math.ceil(oldImgSize['width'] * zoomStep),
                height: Math.ceil(oldImgSize['height'] * zoomStep)
            };

            this._setImgSize(imgSize);
            this._checkPosition();
            this._adjustImgPos();

            //return true;
        },

        /**
         * @desc 根据设置的放大缩小倍率<br>
         *   计算图片可以被放大和缩小的极值尺寸
         * @returns {{max: {width: number, height: number}, min: {width: number, height: number}}|*}
         */
        '_calZoomSize': function() {
            var imgSize,
                maxZoom = this.element.data('maxZoom') || jazz.config.maxZoom || 1,
                minSize = this.imgWrap.data('minSize');

            if (this.zoomSize) {
                return this.zoomSize;
            }
            imgSize = this.originalSize || (this.originalSize = this.imgWrap.data('size'));
            this.zoomSize = {
                'max': {
                    'width': Math.ceil(imgSize['width'] * maxZoom),
                    'height': Math.ceil(imgSize['height'] * maxZoom)
                },
                'min': {
                    'width': minSize['width'],
                    'height': minSize['height']
                }
            };
            return this.zoomSize;
        },

        /**
         * @desc 检测是否符合缩放条件<br>
         *   当前图片尺寸宽或高任意大于设定的最大尺寸,则不缩放<br>
         *   当前图片尺寸宽或高任意小于设定的做小尺寸,则不缩放
         * @param vtype
         * @returns {boolean}
         */
        '_isZoom': function(vtype) {
            var zoomSize, imgSize,
                vwidth = 'width',
                vheight = 'height',
                trans = this.trans;

            zoomSize = this._calZoomSize();
            imgSize = this._getImgSize();
            if (trans) {
                vwidth = 'height';
                vheight = 'width';
            }
            //从这里开始比较当前缩放的尺寸和原始尺寸
            if (vtype === 'in' && (imgSize[vwidth] >= zoomSize['max']['width'] || imgSize[vheight] >= zoomSize['max']['height'])) {
                return false;
            }
            if (vtype === 'out' &&
                (imgSize[vwidth] <= zoomSize['min']['width'] || imgSize[vheight] <= zoomSize['min']['height'])) {
                this.imgWrap.addClass('jazz-imageview-minsize');
                this.imgWrap.css(this.element.data('minPos'));
                return false;
            }
            this.imgWrap.removeClass('jazz-imageview-minsize');
            return true;
        },

        '_isInRange': function(x, y, who) {
            var $el, startPoint, endPointX, endPointY;
            try {
                $el = this['get' + who.replace(/^\w/, function(m) {
                    return m.toUpperCase();
                })]();
            } catch (e) {
                jazz.log(e);
                return false;
            }

            startPoint = this[who + 'Offset'] || (this[who + 'Offset'] = $el.offset());
            endPointX = startPoint['left'] + $el.width();
            endPointY = startPoint['top'] + $el.height();

            if (x >= startPoint['left'] && y >= startPoint['top'] && x <= endPointX && y <= endPointY) {
                return true;
            }

            return false;
        },

        'getImage': function() {
            return this.imgWrap;
        },

        /**
         * @desc 获取图片大小
         * @returns {{width: *, height: *}|*}
         */
        '_getImgSize': function() {
            /**
             * 图片被旋转90度或-90度
             */
            /*if (this.imgWrap.data('trans')) {
             return {
             'height': this.imgWrap.outerWidth(),
             'width': this.imgWrap.outerHeight()
             };
             }*/
            return {
                'width': this.imgWrap.outerWidth(),
                'height': this.imgWrap.outerHeight()
            };
        },

        /**
         * @设置图片大小
         * @param imgSize 图片尺寸
         */
        '_setImgSize': function(imgSize, trans) {
            var minSize = this.imgWrap.data('minSize');
            imgSize = imgSize || this.originalSize || (this.originalSize = this.imgWrap.data('size'));

            /*if (imgSize['width'] < minSize['width'] || imgSize['height'] < minSize['height']) {
             imgSize = minSize;
             }
             if (imgSize['width'] < minSize['width'] || imgSize['height'] < minSize['height']) {
             imgSize = minSize;
             }*/
            if(imgSize){
            	this.imgWrap
                .height(imgSize['height'])
                .width(imgSize['width']);
            }
            
        },

        '_adjustImgPos': function() {
            var imgSize = this._getImgSize(),
                wrapWidth = this.options.width,
                wrapHeight = this.options.height,
                trans = this.trans,
                vheight = trans ? 'width' : 'height',
                vwidth = trans ? 'height' : 'width';

            //保证小图片水平居中显示
            if (imgSize[vwidth] <= wrapWidth) {
                this.imgWrap.css({
                    'left': (wrapWidth - imgSize[vwidth]) / 2 + 'px'
                });
            }
            //保证小图片垂直居中显示
            if (imgSize[vheight] <= wrapHeight) {
                this.imgWrap.css({
                    'top': (wrapHeight - imgSize[vheight]) / 2 + 'px'
                });
            }
        },

        /**
         * @desc 鼠标滚轮滚动响应事件
         * <br> 实现图片缩放功能
         * @param e
         * @private
         */
        '_onMouseWheel': function(e) {
            var $this = this,
                imgOffset = $this.imgWrap.offset(),
                delta, zoomFlag;
            e = e || window.event;
            //阻止冒泡
            e.cancelBubble = true;
            if (e.stopPropagation) {
                e.stopPropagation();
            }
            //组织默认事件
            if (e.preventDefault) {
                e.preventDefault();
            }
            if (e['originalEvent']) {
                //这里要注意兼容浏览器
                delta = e['originalEvent'].detail || e['originalEvent']['wheelDelta'];
                if (delta > 0) {
                    zoomFlag = $this.zoom('in');
                } else {
                    zoomFlag = $this.zoom('out');
                }
                /*$this._checkPosition();
                 $this._adjustImgPos();*/
                $this._hideContextMenu();
                /*        if (zoomFlag) {
                 //$this._updatePosition(e['originalEvent']['pageX'], e['originalEvent']['pageY'], imgOffset);
                 $this._checkPosition();
                 $this._adjustImgPos();
                 }*/
            }
        },

        /**
         * @desc 初始化右键菜单
         * @private
         */
        '_initContextMenu': function() {
            var $menu, items = [];
            if (!this.options.isshowcontextmenu) {
                return;
            }
            if ($('div.jazz-imageview-contextmenu').length > 0) {
                this.contextMenu = $('div.jazz-imageview-contextmenu');
                return;
            }
            /**
             * 这里的右键菜单项应该和tollbar的一致
             * 这里的做法太机械,不够好
             */
            $menu = $("<div class='jazz-imageview-contextmenu jazz-helper-hidden'><ul></ul></div>")
                .appendTo($('body'))
                .children('ul');

            items.push("<li><a href='javascript:;' class='jazz-imageview-zoomin'>放大</a></li>");
            items.push("<li><a href='javascript:;' class='jazz-imageview-zoomout'>缩小</a></li>");

            //添加左转、右转菜单
            if (this.options.isrotate) {
                items.push("<li><a href='javascript:;' class='jazz-imageview-rotateleft'>左转</a></li>");
                items.push("<li><a href='javascript:;' class='jazz-imageview-rotateright'>右转</a></li>");
            }
            items.push("<li><a href='javascript:;' class='jazz-imageview-cancelMenu'>取消</a></li>");
            $menu.append(items.join(""));

            this.contextMenu = $menu.parent();
        },

        /**
         * @desc 显示右键菜单
         * @param x 右键点击位置横坐标
         * @param y 右键点击位置纵坐标
         * @private
         */
        '_showContextMenu': function(x, y) {
            this.contextMenu.offset({
                'left': x,
                'top': y
            }).show();
        },

        /**
         * @desc 隐藏右键菜单
         * @private
         */
        '_hideContextMenu': function() {
            $('div.jazz-imageview-dblclicked')
                .removeClass("jazz-imageview-dblclicked");
            this.options.isshowcontextmenu && this.contextMenu
                .offset({
                    'left': 0,
                    'top': 0
                })
                .hide();
        },

        /**
         * @desc 绑定右键菜单事件
         * @private
         */
        '_bindContextMenuEvent': function() {
            var $this = this;

            if (!this.options.isshowcontextmenu) {
                return;
            }

            this.imgWrap[0].oncontextmenu = function(e) {
                e = e || window.event;
                //阻止冒泡
                e.cancelBubble = true;
                e.stopPropagation && e.stopPropagation();
                //阻止默认事件
                event.preventDefault ? event.preventDefault() : event.returnValue = false;
                //查找当前鼠标右击的组件，添加右键点击样式标记
                $('.jazz-imageview-dblclicked')
                    .removeClass('jazz-imageview-dblclicked');
                $this.element.addClass('jazz-imageview-dblclicked');
                //隐藏当前点击之外的组件的工具条
                $('.jazz-imageview-wrap')
                    .not('.jazz-imageview-dblclicked')
                    .find('.jazz-imageview-toolbar')
                    .hide();
                //显示右键菜单
                $this._showContextMenu(e.clientX, e.clientY);
            };
            //确保一个页面只维护一份右键菜单
            if ($._data(this.contextMenu[0], 'events')) {
                return;
            }
            //绑定右键菜单关闭显示事件
            $('a.jazz-imageview-cancelMenu').off('click.viewer')
                .on('click.viewer', function() {
                    $this._hideContextMenu();
                });
            //绑定右键菜单图片放大事件
            $('a.jazz-imageview-zoomin').off('click.viewer')
                .on('click.viewer', function() {
                    $('.jazz-imageview-dblclicked:first')
                        .imageviewer('zoom', 'in');
                });
            //绑定右键菜单图片缩小事件
            $('a.jazz-imageview-zoomout').off('click.viewer')
                .on('click.viewer', function() {
                    $('.jazz-imageview-dblclicked:first')
                        .imageviewer('zoom', 'out');
                });
        },

        /**
         * @desc 恢复图片原始状态,包括尺寸\位置
         */
        'reset': function(imgSrc) {
            var imgSize,
                left = 0,
                top = 0;

            //重新请求图片
            //this.loadImage(imgSrc || this.options.imagesrc);
            //设置图片大小为原始尺寸
            this._setImgSize();
            imgSize = this._getImgSize();

            //保证小图片水平居中显示
            if (imgSize['width'] < this.element.width()) {
                left = (this.element.width() - imgSize['width']) / 2 + 'px';
            }
            //保证小图片垂直居中显示
            if (imgSize['height'] < this.element.height()) {
                top = (this.element.height() - imgSize['height']) / 2 + 'px';
            }
            this.imgWrap.css({
                'top': top,
                'left': left
            });
            //this._hideContextMenu();
        },

        /**
         * @desc 添加按钮
         * @param button
         */
        'addButton': function(button) {
            var btns = [];
            if (!jazz.isArray(button)) {
                btns = [button];
            } else {
                btns = btns.concat(button);
            }
            this.toolbarWrap.find('> div').toolbar('addElement', btns);
        },

        'addWatermark': function(src) {
            src = src || this.options.watermarkurl || jazz.config.watermarkurl;
            if (!src) {
                return;
            }
            this.watermark.css({
                'background': 'url(' + src + ') right bottom no-repeat transparent'
            });
        },

        /**
         * @desc 动态改变属性
         * @param {key} 对象的属性名称
         * @param {value} 对象的属性值
         * @private
         */
        '_setOption': function(key, value) {
            switch (key) {
                case 'width':
                    this.options.width = parseInt((value + "").replace(/px/ig, ""));
                    this._setupContainer();
                    break;
                case 'height':
                    this.options.height = parseInt((value + "").replace(/px/ig, ""));
                    this._setupContainer();
                    break;
                case 'watermarkurl':
                    this.options.watermarkurl = value;
                    this.addWatermark(value);
                    break;
                case 'imagesrc':
                    this.options.imagesrc = value;
                    this.loadImage(value);
            }

            this._super(key, value);
        },

        /**
         * @desc 组件销毁方法
         */
        'destroy': function() {
            //this.toolbarWrap.toolbar('destroy');
            this.element.children().remove();
            this.element.remove();
            if ($('div.jazz-imageview-wrap').length <= 1) {
                this.contextMenu.children().remove();
                this.contextMenu.remove();
            }

            this._super();
        }
    });

})(jQuery);

(function($) {

    /**
     * @version 1.0
     * @name jazz.tooltip
     * @description 提示信息组件。
     * @constructor
     * @extends jazz.BoxContainer
     * @requires
     */
    $.widget("jazz.tooltip", $.jazz.boxComponent, {

        options: /** @lends jazz.tooltip# */ {

            /**
             *@type String
             *@desc 提示内容
             *@default ''
             */
            content: '',

            /**
             *@type String
             *@desc 隐藏提示内容要由哪种事件触发
             *@default 'mouseout'
             */
            hideevent: 'mouseout',

            /**
             *@type String
             *@desc 提示信息前边所要添加的图片的样式类, 默认无图片
             *@default null
             */
            iconclass: null,

            /**
             *@type Boolean
             *@desc 内部绑定事件
             *@default true
             */
            isbindevent: true,

            /**
             *@type Object
             *@desc 显示位置
             *@default null
             *@example
             *{
       *   my: 定义被定位元素上对准目标元素的位置, 例： "left, top"
       *   at: 目录元素, "right top",
       *   collision: 当被定位元素在某些方向上溢出窗口，则移动它到另一个位置   例如： 'flipfit none',
       *   of: 要定位的元素， 例如： this.element 或  #id  或  .class,
       *   using: function(pos) { } 描述：当指定了该选项，实际属性设置则委托给该回调
       *   within（默认值：window）类型：Selector 或 Element 或 jQuery 描述：元素定位为 within，会影响 collision 检测。如果您提供了一个选择器（Selector）或 jQuery 对象，则使用第一个匹配的元素。 
       *}
             */
            position: null,

            /**
             *@type String
             *@desc 显示提示内容要由哪种事件触发
             *@default 'mouseover'
             */
            showevent: 'mouseover'
        },

        /** @lends jazz.tooltip */

        /**
         *@desc 创建组件
         */
        _create: function() {
            this.container = $('<div class="jazz-tooltip" />').appendTo(document.body);

            var styleClass = "",
                _iconclass = this.options.iconclass;
            if (_iconclass) {
                //styleClass = '<span class="jazz-tooltip-img" style="background: url('+this.options.icon+') no-repeat"></span>';
                styleClass = '<span class="jazz-tooltip-img ' + _iconclass + '"></span>';
            } else {
                this.arrow = $('<div class="jazz-tooltip-arrow"></div>').appendTo(this.container);
                this.container.css({
                    "padding-left": "10px"
                });
            }

            var content = '<div class="jazz-tooltip-div"> ' + styleClass + '<span class="jazz-tooltip-label"></span></div>';
            this.container.append(content);

            this.contentobj = this.container.children(".jazz-tooltip-div");
        },

        /**
         *@desc 初始化组件
         *@private
         */
        _init: function() {
            if (this.options.isbindevent) {
                this._bindEvent();
            }
            if (this.options.width != -1) {
                this.contentobj.outerWidth(this.options.width);
            } else {
                this.contentobj.outerWidth(200);
            }
            if (this.options.content) {
                var obj = this.contentobj.children(".jazz-tooltip-label");
                obj.html(this.options.content);
            }
        },

        _setOption: function(key, value) {
            switch (key) {
                case 'content':
                    this.options.content = value;
                    var obj = this.contentobj.children(".jazz-tooltip-label");
                    obj.html(value);
                    break;
                case 'position':
                    this._align();
                    break;
            }
            this._super(key, value);
        },

        /**
         * @desc 显示位置
         * @private
         */
        _align: function() {
            var $this = this;
            var pos = {
                my: 'left top',
                at: 'right top',
                collision: 'flipfit none',
                of: $this.element,
                using: function(pos) {
                    var lp = 0;
                    if (!$this.options.iconclass) {
                        //根据tooltip高度, 确定tooltip的显示位置
                        var tipHeight = $this.container.outerHeight();
                        var eleHeight = $this.element.height();
                        if (eleHeight > 50) {
                            if (tipHeight <= 50) {
                                lp = 0;
                                $this.arrow.css({
                                    "top": "7px"
                                });
                            } else {
                                if (eleHeight - tipHeight > 20) {
                                    lp = 20;
                                    $this.arrow.css({
                                        "top": "35px"
                                    });
                                } else {
                                    lp = 0;
                                    $this.arrow.css({
                                        "top": "25px"
                                    });
                                }
                            }
                        } else {
                            if (tipHeight <= 50) {
                                lp = 0;
                                $this.arrow.css({
                                    "top": "7px"
                                });
                            } else {
                                lp = 18;
                                $this.arrow.css({
                                    "top": "25px"
                                });
                            }
                        }
                    }

                    var n = 0;
                    if ($this.options.position == 1) {
                        n = $this.element.height();
                    }
                    var l = pos.left < 0 ? 0 : pos.left + 5,
                        t = pos.top < 0 ? 0 : pos.top - lp + n;

                    $(this).css({
                        left: l,
                        top: t
                    });
                }
            };

            this.container.css({
                left: '',
                top: '',
                'z-index': ++jazz.zindex
            }).position(pos);
        },

        /**
         * @desc 目标范围内
         * @private
         */
        _bindEvent: function() {
            var $this = this;
            var hideevent = this.options.hideevent,
                showevent = this.options.showevent;

            if (hideevent == "mouseout") {
                //处理gridpanel卡片调用
                this.globalSelector = null;
            } else {
                this.globalSelector = 'a,:input,:button,img';
            }

            //处理多事件绑定
            var _sevent = "",
                _hevent = "";
            if (hideevent) {
                var _h = hideevent.split(";");
                for (var i = 0, len = _h.length; i < len; i++) {
                    if (_h[i]) {
                        _hevent = _h[i] + ".tooltip " + _hevent;
                    }
                }
            }
            if (showevent) {
                var _s = showevent.split(";");
                for (var i = 0, len = _s.length; i < len; i++) {
                    if (_s[i]) {
                        _sevent = _s[i] + ".tooltip " + _sevent;
                    }
                }
            }

            this.element.off(_sevent + " " + _hevent)
                .on(_sevent, this.globalSelector, function() {
                    $this.show();
                }).on(_hevent, this.globalSelector, function() {
                    $this.hide();
                });
            this.element.removeAttr('title');
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
})(jQuery);

(function($) {

    /**
     * @version 0.5
     * @name jazz.loading
     * @description 加载动画组件
     * @example $('div_id').loading();
     * @example jazz.loading();
     */
    $.widget("jazz.loading", $.jazz.boxComponent, {

        options: /** @lends jazz.loading# */ {

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
            this.ele = $("<div class='jazz-loading-overlay'><div class='jazz-loading-img'><div class='jazz-loading-text'>" + this.options.text + "</div></div></div>").appendTo(this.element);
        },

        /**
         * @desc 初始化组件
         * @private
         */
        _init: function() {
            this.changePosition = false;
            this.oldPosition = "";
            if (this.options.blank == true) {
                this.ele.children().hide();
            } else {
                this.ele.children().show();
            }
            this.show();
        },

        /**
         * @desc 检查this.element父元素的position属性
         * @private
         */
        _checkPosition: function() {
            var parent = this.element.parent();
            var position = parent.css("position");
            this.oldPosition = this.element.css("position");
            if (this.element[0].tagName == "BODY" || parent.get(0) == $("body").get(0)) {
                //            this.element.css({position: 'fixed', top: 0, left: 0});
                this.ele.css({
                    position: 'fixed',
                    top: 0,
                    left: 0,
                    'height': $(window).height(),
                    'width': $(window).width()
                });
                this.ele.addClass("jazz-ie6-position-fixed");
            } else if (position == 'inherit' || position == 'static' || position == '') {
                this.element.css({
                    'position': 'relative',
                    'height': this.ele.outerHeight(true)
                });
            }
            this.changePosition = true;
        },

        //        /**
        //         * @desc 还原this.element父元素的position属性
        //         * @private
        //         */        
        //        _reverPostion: function(){
        //          var parent = this.element.parent();
        //          if(this.changePosition){
        //            //只针对放在body里的遮罩层position
        //            if(parent[0].tagName.toUpperCase() === "BODY"){
        //              parent.css('position', this.oldPosition);
        //            }//else{
        ////              parent.css('position', "absolute");             
        //            //}
        //          }
        //        },               

        /**
         * @desc 隐藏loading动画
         * @example this.hide();
         */
        hide: function() {
            //            this._reverPostion();
            this.ele.hide();
        },

        /**
         * @desc 显示loading动画
         * @example this.hide();
         */
        show: function() {
            this._checkPosition();
            this.ele.show();
        },

        /**
         * @desc 组件销毁方法
         */
        destroy: function() {
            this.element.children().remove();
            this.element.remove();
        }

    });

})(jQuery);
