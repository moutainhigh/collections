(function ($, factory) {

    if (jazz.config.isUseRequireJS === true) {
        define(['jquery', 'jazz.BoxComponent', 'form/jazz.form.ComboxField'], factory);
    } else {
        factory($);
    }
})(jQuery, function ($) {

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
    	/* 废弃不使用 */
        '{ComboPageSize}': {
            markup: '<span class="jazz-paginator-pagesize"><select class="jazz-paginator-pagesize-select"><option value="10">10</option><option value="15">15</option><option value="20">20</option><option value="25">25</option><option value="30">30</option></select></span>',
            create: function(paginator) {
                var element = $(this['markup']);
                element.find('select.jazz-paginator-pagesize-select').attr("value", paginator.options.pagerows);
                element.find('select.jazz-paginator-pagesize-select').off('change.pagesize').on('change.pagesize', function() {
                    paginator.gridtableObject.gridtable("clickGridTablePaginator", 0, parseInt(this.value));
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
                if (paginator.options.page == 0) {
                    element.addClass('jazz-state-disabled');
                    element.children().addClass("jazz-icon-disabled");
                }
                if (jazz.config.paginatorStyle === 'text') {
                    element.text('首页').addClass('jazz-paginator-text-btn');
                }
                element.on('click.paginator', function() {
                    if (!$(this).hasClass("jazz-state-disabled")) {
                        paginator.option('page', 0);
                    }
                });

                return element;
            },
            align: 'left',
            update: function(element, state) {
                if (state.page == 0) {
                    element.addClass('jazz-state-disabled').removeClass('jazz-state-hover jazz-state-active');
                    element.children().addClass("jazz-icon-disabled");
                } else {
                    element.removeClass('jazz-state-disabled');
                    element.children().removeClass("jazz-icon-disabled");
                }
            }
        },

        '{PreviousPageLink}': {
            markup: '<span title="上一页" class="jazz-paginator-prev jazz-paginator-element jazz-state-default jazz-corner-all"><span class="jazz-icon jazz-icon-seek-prev">p</span></span>',
            create: function(paginator) {
                var element = $(this["markup"]);
                if (paginator.options.page == 0) {
                    element.addClass('jazz-state-disabled');
                    element.children().addClass("jazz-icon-disabled");
                }
                if (jazz.config.paginatorStyle === 'text') {
                    element.text('上页').addClass('jazz-paginator-text-btn');
                }
                element.on('click.paginator', function() {
                    if (!$(this).hasClass("jazz-state-disabled")) {
                        paginator.option('page', paginator.options.page - 1);
                    }
                });

                return element;
            },
            align: 'left',
            update: function(element, state) {
                if (state.page == 0) {
                    element.addClass('jazz-state-disabled').removeClass('jazz-state-hover jazz-state-active');
                    element.children().addClass("jazz-icon-disabled");
                } else {
                    element.removeClass('jazz-state-disabled');
                    element.children().removeClass("jazz-icon-disabled");
                }
            }
        },

        '{NextPageLink}': {
            markup: '<span title="下一页" class="jazz-paginator-next jazz-paginator-element jazz-state-default jazz-corner-all"><span class="jazz-icon jazz-icon-seek-next">p</span></span>',
            create: function(paginator) {
                var element = $(this["markup"]);
                if (paginator.options.page == (paginator.getPageCount() - 1)) {
                    element.addClass('jazz-state-disabled').removeClass('jazz-state-hover jazz-state-active');
                    element.children().addClass("jazz-icon-disabled");
                }
                if (jazz.config.paginatorStyle === 'text') {
                    element.text('下页').addClass('jazz-paginator-text-btn');
                }
                element.on('click.paginator', function() {
                    if (!$(this).hasClass("jazz-state-disabled")) {
                        paginator.option('page', paginator.options.page + 1);
                    }
                });

                return element;
            },
            align: 'left',
            update: function(element, state) {
                if (state.page == (state.pagecount - 1)) {
                    element.addClass('jazz-state-disabled').removeClass('jazz-state-hover jazz-state-active');
                    element.children().addClass("jazz-icon-disabled");
                } else {
                    element.removeClass('jazz-state-disabled');
                    element.children().removeClass("jazz-icon-disabled");
                }
            }
        },

        '{LastPageLink}': {
            markup: '<span title="末页" class="jazz-paginator-last jazz-paginator-element jazz-state-default jazz-corner-all"><span class="jazz-icon jazz-icon-seek-end">p</span></span>',
            create: function(paginator) {
                var element = $(this["markup"]);

                if (paginator.options.page == (paginator.getPageCount() - 1)) {
                    element.addClass('jazz-state-disabled').removeClass('jazz-state-hover jazz-state-active');
                    element.children().addClass("jazz-icon-disabled");
                }

                if (jazz.config.paginatorStyle === 'text') {
                    element.text('末页').addClass('jazz-paginator-text-btn');
                }
                element.on('click.paginator', function() {
                    if (!$(this).hasClass("jazz-state-disabled")) {
                        paginator.option('page', paginator.getPageCount() - 1);
                    }
                });

                return element;
            },
            align: 'left',
            update: function(element, state) {
                if (state.page == (state.pagecount - 1)) {
                    element.addClass('jazz-state-disabled').removeClass('jazz-state-hover jazz-state-active');
                    element.children().addClass("jazz-icon-disabled");
                } else {
                    element.removeClass('jazz-state-disabled');
                    element.children().removeClass("jazz-icon-disabled");
                }
            }
        },

        '{PageLinks}': {
            markup: '<span class="jazz-paginator-pages" style="line-height:20px;"></span>',
            create: function(paginator) {
                var element = $(this.markup);
                element.append('<span class="jazz-state-default jazz-paginaotr-nowpage jazz-paginator-info">第</span>' + '<span class="jazz-state-default jazz-paginator-input">&nbsp;<input type="text" value="1" /></span>' + '<span class="jazz-state-default jazz-paginator-info">页，共' + paginator.getPageCount() + '页</span>');

                var inputw = element.find('input[type="text"]');

                //获取焦点事件
                inputw.focus(function() {
                    $(this).parents('.jazz-paginator').find('.jazz-paginaotr-hidden-button')
                        .css({
                            'left': "105px"
                        })
                        .show()
                        .animate({
                            'left': '123px',
                            'opacity': '1'
                        }, "slow");
                });
                //失去焦点事件
                inputw.blur(function() {
                    $(this).parents('.jazz-paginator').find('.jazz-paginaotr-hidden-button') //.hide()
                        .animate({
                            'left': '100px',
                            'opacity': '0'
                        }, "slow", function() {
                            $(this).hide();
                        });
                });

                return element;
            },
            align: 'left',

            update: function(element, state) {
                var input = element.find(".jazz-paginator-input input");
                input.val(parseInt(state.page) + 1);
            }
        },
        '{PageInfo}': {
            markup: '<span class="jazz-paginator-element jazz-paginator-info jazz-paginator-text"></span>',
            create: function(paginator) {
                var element = $(this.markup),
                    nowP = (paginator.options.pagerows * (paginator.options.page + 1)),
                    html = "当前显示" + (paginator.options.pagerows * paginator.options.page + 1) + "-" + (nowP > paginator.options.totalrecords ? paginator.options.totalrecords : nowP) + "，共" + paginator.options.totalrecords + "条";
                if (jazz.config.paginatorStyle === 'text') {
                    html = "共<span class='jazz-paginator-totalrow'>" + paginator.options.totalrecords + "</span>条记录, 每页显示<div class='jazz-paginator-pagerows-input'></div>条, 共<span class='jazz-paginator-num'>" + paginator.getPageCount() + "</span>页. " +
                        '<span class="jazz-paginator-goto-info">跳转到 ' + '<input type="text" name="p_num" class="jazz-paginator-goto-input" />' + '</span><span title=\'确定\' class=\'jazz-paginator-element jazz-state-default js-goto\'>确定</span>';
                }

                element.html(html);
                var that = paginator;
                element.find('.js-goto').on('click', function() {
                    var p_num = parseInt($(this).prev().children('input').val());
                    if (!p_num || !jazz.isNumber(p_num) || (p_num < 1) || (p_num > that.getPageCount())) {
                        $(this).prev().children('input').val('');
                        return;
                    }
                    $(this).parents('[vtype=paginator]').paginator('setPage', (p_num - 1));
                });
                return element;
            },
            align: 'right',
            update: function(element, state) {
                var nowP = state['first'] + parseInt(state['pagerows']),
                    html;
                if (jazz.config.paginatorStyle === 'text') {
                    element.find('.jazz-paginator-goto-input').val('');
                    element.find('.jazz-paginator-num').text(state.pagecount);
                    element.find('.jazz-paginator-totalrow').text(state.totalrows);
//                    setTimeout(function() {
                        element.find('.jazz-paginator-pagerows-input').comboxfield("setValue", state['pagerows']);
//                    }, 100);
                } else {
                    html = "当前显示" + (state.first + 1) + "-" + (nowP > state['totalrows'] ? state['totalrows'] : nowP) + "，共" + state['totalrows'] + "条";
                    element.text(html);
                }
            }
        },
        '{PageGoto}': {
            markup: '<span class="jazz-paginator-goto-info">跳转到 ' + '<input type="text" name="p_num" class="jazz-paginator-goto-input" />' + '</span><span class="jazz-paginator-page jazz-paginator-element' + ' jazz-state-default jazz-paginator-goto"></span>',
            create: function(paginator) {
                var element = $(this['markup']);
                element.on('click.paginator', function() {
                    var p_num = parseInt($(this).parent().find('input.jazz-paginator-goto-input').val());
                    if (!p_num || !jazz.isNumber(p_num) || (p_num < 1) || (p_num > paginator.getPageCount())) {
                        $(this).parent().find('input.jazz-paginator-goto-input').val('');
                        return;
                    }
                    paginator.setPage(p_num - 1);
                });

                return element;
            },
            update: function(element, state) {
                element[0].value = "";
            }
        },

        '{PageRefresh}': {
            markup: '<span title="刷新" class="jazz-paginator-refresh jazz-paginator-element' + ' jazz-state-default jazz-corner-all"><span class="jazz-icon">' + 'p</span></span>',
            align: 'left',
            create: function(paginator) {
                var element = $(this['markup']);
                element.on('click.paginator', function() {
                    paginator.setPage(0, 'refresh');
                });

                return element;
            },
            update: function(element, state) {

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
             *  //todo...
             * }
             */
            gopage: null
        },

        /**
         *@desc 创建组件
         *@private
         */
        _create: function() {
            this._super();

            this.element.addClass('jazz-paginator');
            this.element.addClass("jazz-paginator-ext"); //分页条样式在各主题中都是统一的
            this.element.append('<div class="jazz-paginator-content"><div class="jazz-paginator-left"></div><div class="jazz-paginator-right"></div><div style="clear:both;"></div></div>');
            this.id = this.element.attr('id');
            if (!this.id) {
                this.id = this.element.uniqueId().attr('id');
            }

            this.options.totalrecords = parseInt(this.options.totalrecords, 10) || 0;

            this.paginatorElements = [];
            this._initTemplate();

            var elementKeys = this.options.template.split(/[ ]+/),
                paginaotrLeft = this.element.find('.jazz-paginator-left'),
                paginaotrRight = this.element.find('.jazz-paginator-right');
            for (var i = 0, len = elementKeys.length; i < len; i++) {
                var elementKey = elementKeys[i],
                    handler = ElementHandlers[elementKey];
                if (handler) {
                    var paginatorElement = handler.create(this);
                    this.paginatorElements[elementKey] = paginatorElement;
                    if (handler['align'] == 'left') {
                        paginaotrLeft.append(paginatorElement);
                    } else {
                        paginaotrRight.append(paginatorElement);
                    }
                }
            }
            
            this._setPageRowBox();
            
            this.addButton();
            /**
             * 添加跳转按钮
             */
            if (jazz.config.paginatorStyle === 'text') {
                /*this.element.find(".jazz-paginator-content .jazz-paginator-goto-info")
                .after("<span title='跳转' class='jazz-paginator-element jazz-state-default js-goto'>跳转</span>");
                this.element.find('.js-goto').on('click', function(){
                  var p_num = parseInt($(this).prev().children('input').val());
                    if(!p_num || !jazz.isNumber(p_num) || (p_num < 1) || (p_num > that.getPageCount())){
                      $(this).prev().children('input').val('');
                        return;
                    }
                    $(this).parents('[vtype=paginator]').paginator('setPage', (p_num-1));
                });*/
            } else {
                this.element.find('.js-goto').remove();
                this.element.find(".jazz-paginator-content")
                    .append("<a title='跳转' href='javascript:;' class='jazz-paginaotr-hidden-button'>确定</a>");
            }
            /*
            //暂时由gridpanel与paginator的绑定
            var parent = this.getParentComponent();
            if(parent){
              if(parent.attr("vtype")=="gridpanel"){
                this.gridpanel = parent.data("gridpanel");
              }
            }
            
            this._bindEvents();*/
        },
       
        _init: function() {
            this._super();

            //暂时由gridpanel与paginator的绑定
            var parent = this.getParentComponent();
            if (parent) {
                if (parent.attr("vtype") == "gridpanel") {
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
            input.on("keypress", function(e) {
                var curKey = e.keyCode;
                if (curKey == 13) {
                    var inputVal = $this.element.find('input[type="text"]').val();
                    if ($this._isPageLegal(inputVal) && (inputVal - 1) != $this.options.page) {
                        $this.setPage(inputVal - 1);
                    }
                }
            });
            //}
            $this.element.find('.jazz-paginaotr-hidden-button')
                .off('click.paginator')
                .on('click.paginator', function() {
                    var inputVal = $this.element.find('input[type="text"]').val();
                    if ($this._isPageLegal(inputVal) && (inputVal - 1) != $this.options.page) {
                        $this.setPage(inputVal - 1);
                    }
                });
        },        
        
        /**
         * @desc 初始化分页条的html模板
         */
        _initTemplate: function() {
            this.options.template = '{FirstPageLink}  {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {PageRefresh} {PageInfo}';
            if (jazz.config.paginatorStyle === 'text') {
                this.options.template = '{FirstPageLink}  {PreviousPageLink} {NextPageLink} {LastPageLink} {PageInfo}';
            }
        },

        /**
         * @desc 检查要跳转的页码是否合法
         * @param page 要跳转的页码
         * @returns {Boolean} true 当前要跳转的页码合法 <br>false 不合法
         * @private 
         */
        _isPageLegal: function(page) {
            if (!/^\d+$/.test(page)) {
                this.element.find('input[type="text"]').val((this.options.page + 1));
                return false;
            }
            if (page < 0 || page > Math.ceil(this.options.totalrecords / this.options.pagerows)) {
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
            if (key == 'page') {
                this.setPage(value);
            } else {
                $.Widget.prototype._setOption.apply(this, arguments);
            }
        },

        /**
         * @desc 添加分页条数下拉列表
         * <br> 每页显示条数目前是确定的，可以考虑写成可配置的
         */
        _setPageRowBox: function(){
            var that = this;
            //缓存JAZZ配置项下拉图标宽度
            var tmp_paginator = {
                ar: jazz.config.fieldIconWidth
            };
            jazz.config.fieldIconWidth = 6;
            this.element.find('.jazz-paginator-pagerows-input')
                .comboxfield({
                    vtype: 'comboxfield',
                    name: 'jazz-paginator-pagerows-input-' + (jazz.getRandom()),
                    width: 30,
                    height: 20,
                    isshowblankitem: false,
                    dataurl: [{
                        text: "5",
                        value: 5
                    }, {
                        text: "10",
                        value: 10
                    }, {
                        text: "20",
                        value: 20
                    }, {
                        text: "50",
                        value: 50
                    }],
                    itemselect: function(e, ui) {
                    	that.options.pagerows = ui.value;
                        that.setPage(0, 'refresh');
                    }
                });
            this.element.find('.jazz-paginator-pagerows-input')
                .comboxfield('setValue', this.options.pagerows)
                .css({
                    margin: '3px 0 0',
                    padding: 0
                });
            //恢复下拉框input的默认宽度
            jazz.config.fieldIconWidth = tmp_paginator.ar;
        },
        
		/**
         * @desc 覆盖boxcomponent中的方法, 分页组件不需要刷新子组件的宽度
		 * @private
         */        
        _reflashChildWidth: function(){},       
        
        /**
         *@desc 修改分页组件的显示
         *@param {state.first} 当前页首条记录的索引值
         *@param {state.pagerows}  行数
         *@param {state.page}  当前显示的第几页页数
         *@param {state.pagecount} 总的页面数
         *@param 弃用 {state.pageLinks} 分页条显示页码的按键个数
         */
        _updateUI: function(state) {
            for (var paginatorElementKey in this.paginatorElements) {
                ElementHandlers[paginatorElementKey].update(this.paginatorElements[paginatorElementKey], state);
            }
        },

        /**
         * @desc 添加自定义按钮
         */
        addButton: function() {
            if (this.options.buttons === false) {
                return;
            }
            var $this = this,
                btns = $this.options.buttons;

            for (var i = 0, len = btns.length; i < len; i++) {
                var btn = btns[i],
                    id = btn.id || this.element.uniqueId(),
                    btnHtml = '';
                if ($.isFunction(btn.renderer)) {
                    btnHtml = btn.renderer.call(this);
                } else {
                    btnHtml = "<span>" + (btn.title ? btn.title : "") + "</span>";
                }
                $(btnHtml).appendTo(this.element.find('.jazz-paginator-left'));
                $this.element.find("> .jazz-paginator-refresh").attr('id', this.id + '_' + id)
                    .addClass('jazz-paginator-element jazz-paginator-custom ' +
                        (btn.classname ? btn.classname : ""));
                $this.element.find('.jazz-paginator-refresh')
                    .on('click', function() {
                        if ($.isFunction(btn['callback'])) {
                            btn.callback.call($this);
                        }
                    });
            }
        },        
        
        /**
         *@desc 获取页面数量
         *@return 页面数
         */
        getPageCount: function() {
            return Math.ceil(parseInt(this.options.totalrecords) / parseInt(this.options.pagerows)) || 1;
        },

        getPageFirstRowNum: function() {
            return parseInt(this.options.pagerows) * this.options.page;
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
            if (p >= 0 && p < pc && ($this.options.page != p || (silent === 'refresh' && p === 0))) {
                var newState = {
                    first: $this.options.pagerows * p,
                    pagerows: $this.options.pagerows,
                    page: p,
                    pagecount: pc,
                    totalrows: $this.options.totalrecords
                };

                this.options.page = p;

                if (!silent || silent === 'refresh') {
                    var gopage = $this.options.gopage;
                    if ($.isFunction(gopage)) {
                        $this._event('gopage', null, newState);
                    } else if ($.isFunction(window[gopage])) {
                        window[gopage].call(this, newState);
                    } else {
                        if ($this.gridpanel) {
                            $this.gridpanel.bindPaginatorClickEvent((p + 1), $this.options.pagerows);
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
        updatePage: function(paginationInfo) {
            this.options.page = paginationInfo["page"] > 0 ? (paginationInfo["page"] - 1) : 0;
            this.options.pagerows = paginationInfo["pagerows"];
            this.options.totalrecords = paginationInfo["totalrecords"];

            this.element.find('.jazz-paginator-left').children().remove();
            this.element.find('.jazz-paginator-right').children().remove();

            var elementKeys = this.options.template.split(/[ ]+/),
                paginaotrLeft = this.element.find('.jazz-paginator-left'),
                paginaotrRight = this.element.find('.jazz-paginator-right');
            for (var i = 0, len = elementKeys.length; i < len; i++) {
                var elementKey = elementKeys[i],
                    handler = ElementHandlers[elementKey];
                if (handler) {
                    var paginatorElement = handler.create(this);
                    this.paginatorElements[elementKey] = paginatorElement;
                    if (handler['align'] == 'left') {
                        paginaotrLeft.append(paginatorElement);
                    } else {
                        paginaotrRight.append(paginatorElement);
                    }
                }
            }
            
            this._setPageRowBox();
            this.addButton();
            this._bindEvents();

            //更新分页条后
            //默认导航到第一条
            var state = {
                first: this.getPageFirstRowNum(),
                pagerows: this.options.pagerows,
                page: this.options.page,
                pagecount: this.getPageCount(),
                totalrows: this.options.totalrecords
            };

            this._updateUI(state);
        }        
    });
});
