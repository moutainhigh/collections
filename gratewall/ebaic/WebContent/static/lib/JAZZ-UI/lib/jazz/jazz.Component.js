(function($, factory) {

    if (jazz.config.isUseRequireJS === true) {
        define(['jquery'], factory);
    } else {
        factory($);
    }
})(jQuery, function($) {
    /**
     * @version 1.0
     * @name jazz.component
     * @description 组件的基类，component下所有子类均按照统一组件生命周期执行动作，既创建、渲染和销毁，并具有隐藏./显示、启用/禁用的基本行为特性。
     * @constructor
     */
    $.widget('jazz.component', {

        options: /** @lends jazz.component# */ {

            /**
             * @type String
             * @desc 组件名称
             * @default ""
             */
            name: "",

            /**
             * @type String
             * @desc 内容区域的html内容
             * @default null
             */
            content: null,

            /**
             * @type Object
             * @desc 子组件
             * @default null
             */
            items: null
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

            var $this = this;
            this.options.create = function() {
                //控制content、items的执行次数
                $this.content_number = 1;
                $this.items_number = 1;

                // 创建vtype树结构
                $this._vtypetree();
            };
        },

        /**
         * @desc 初始化
         * @private
         */
        _init: function() {},

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
         * @desc 创建内容区中包含的组件
         * @param {obj} 
         * @private
         */
        _createContent: function(obj) {
            if (obj instanceof $ && this.content_number === 1) {
                $.each(obj.find("div[vtype]"), function() {
                    $(this).parseComponent();
                });
                this.content_number++;
            }
        },

        /**
         * @desc 创建包含子项的组件
         * @param {obj} 
         * @private
         */
        _createItems: function(obj) {
            var items = this.options.items;
            if ($.isArray(items)) {
                if (obj instanceof $ && this.items_number === 1) {
                    $.each(items, function(i, item) {
                        var vtype = item["vtype"];
                        if (vtype) {
                            var v = $("<div>").appendTo(obj);
                            v[vtype](item);
                        }
                    });
                    this.items_number++;
                }
            }
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
            event.type = (eventName === this.options.vtype ? eventName : this.options.vtype + eventName).toLowerCase();
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
            return !($.isFunction(callback) && callback.apply(this.element[0], [event].concat(data)) === false || event.isDefaultPrevented());
        },

        /**
         * @desc 创建vtypetree树形结构
         * @private
         */
        _vtypetree: function() {
            // 调用全局函数，实现树形结构的创建
            if (this.options.createtype != "0") {
                //"1" $ 方式调用   "0" vtype方式调用
                jazz.vtypetree(this.element, "1");
            }
        },

        finish: function() {},

        /**
         * @desc 组件ID 由组件内部调用
         * @return ID编号
         * @private
         */
        getCompId: function() {
            var id = jazz.getId();
            return id;
        },

        /**
         * @desc vtype树操作：获取当前节点的父节点
         * @returns
         */
        getParentComponent: function() {
            var nodeData = this.element.data("vtypetree") || {};
            return nodeData.parent || {};
        },

        /**
         * @desc vtype树操作：获取当前节点的指定vtype类型子节点
         * @returns
         */
        getChildrenComponent: function() {
            var nodeData = this.element.data("vtypetree") || {};
            return nodeData.child || {};
        },

        /**
         * @desc vtype树操作：获取当前节点的指定vtype类型子节点
         * @param vtype
         * @returns
         */
        getChildrenComponentByVtype: function(vtype) {
            var element = null;
            var nodeData = this.element.data("vtypetree") || {};
            for (var childname in nodeData.child) {
                var data = nodeData.child[childname].data("vtypetree");
                if (data.vtype == vtype) {
                    element = nodeData.child[childname];
                    break;
                }
            }
            return element;
        }

    });

});
