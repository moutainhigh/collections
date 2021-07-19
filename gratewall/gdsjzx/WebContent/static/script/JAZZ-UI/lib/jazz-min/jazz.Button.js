(function($, factory) {

    if (jazz.config.isUseRequireJS === true) {
        define(['jquery', 'jazz.BoxComponent'], factory);
    } else {
        factory($);
    }
})(jQuery, function($) {
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
             *@desc 组件类型
             */
            vtype: 'button',
            /**
             *@type String
             *@desc button组件元素name标识
             *（建议使用非中文字符命名，考虑增加正则校验，以便css选择器使用）
             *@default null
             */
            name: null,
            /**
             *@type String
             *@desc button组件元素名称显示值
             *@default null
             */
            text: null,
            /**
             *@type String
             *@desc 鼠标停留button时显示的按钮说明
             *@default null
             */
            title: null,
            /**
             * @type number
             * @desc button宽度设置
             * @default null
             */
            width: null,
            /**
             * @type number
             * @desc button高度设置
             * @default 23px
             */
            height: null,
            /**
             *@type String
             *@desc 按钮在toolbar组件上显示的位置 "left" 居左  "center" 居中  "right" 居右   
             *      注意该属性是针对button在toolbar组件中的位置使用的，button在其他组件中该属性不起作用。
             *@default 'left'
             */
            align: 'left',
            /**
             *@type Object
             *@desc button按钮图标显示位置（top/bottom/left/right）
             *@default left
             */
            iconalign: 'left',
            /**
             *@type boolean
             *@desc button组件是否为分离按钮splitButton（即带下拉箭头按钮），默认为false
             *@default false
             */
            issplitbutton: false,
            /**
             *@type String
             *@desc button组件中下拉箭头显示位置（top/bottom/left/right），默认值right。该属性配合issplitbutton=true使用
             *@default left
             */
            arrowalign: 'right',
            /**
             *@type Number
             *@desc button组件显示内容视图类型（0只有图标 1只有文字 2图标加文字）
             *@default 2
             */
            defaultview: 2,
            /**
             *@type String
             *@desc button按钮图标url路径，做为按钮的默认图片
             *@default null
             */
            iconurl: null,
            /**
             *@type String
             *@desc button按钮切换图标url路径，配合iconurl使用当鼠标按下时切换默认图标显示
             *@default null
             */
            toggleiconurl: null,
            /**
             *@type boolean
             *@desc toolbar组件是否可用状态，默认为false
             *@default false
             */
            disabled: false,
            /**
             *@type String
             *@desc button按钮划分所属共同组的名称
             *@default null
             */
            group: null,
            /**
             *@type String
             *@desc button按钮在共同组group中的click选中类型（ckeck复选,radio单选），该属性结合group使用
             *@default null
             */
            checktype: null,
            /**
             *@type boolean
             *@desc button组件是否延迟执行click事件，默认为false
             *      （该属性delay=true时，据延迟时间延迟click回调，解决表单短时多次点击重复提交问题）
             *@default false
             */
            delay: false,
            /**
             *@type Number
             *@desc button组件延迟执行click事件延时时间，默认为150ms
             *@default 150
             */
            delaytime: 150,
            /**
             *@type String
             *@desc button为复合下拉菜单的按钮组件时，下拉菜单menu显示方式，hover（鼠标滑过）/click（鼠标单击），
             *      该属性为显示下拉菜单menu使用。
             *@default 'hover'
             */
            showmenuevent: 'hover',
            /**
             *@type Object
             *@desc button组件元素下拉menu数据项数组，数据格式参考jazz.menu菜单组件数据格式
             *@default null
             */
            items: null,
            /**
             *@desc button组件click回调事件，实际为按钮mouseup时响应此回调
             *@param {event} 事件
             *@param {data} 数据 
             *@event click
             *@default null
             */
            click: null,
            /**
             *@desc button组件是否显示圆角，默认显示圆角
             *@type boolean
             *@default true
             */
            isfillet: true
                /**
                 *@desc button组件href指定，与click函数互斥
                 *（建议使用click函数实现相应其功能）
                 *（暂未提供该功能）
                 *@default null
                 */
                /*href:null*/
        },

        /** @lends jazz.button */
        /**
         * @desc 创建组件
         * @private
         */
        _create: function() {
            this._super();
            //1.创建button按钮准备
            //1.1 获取当前页面请求地址，计算button默认图片地址
            this._getButtonDefaultUrl();
            //1.2 在button创建前，保存原html,以待button组件销毁后重新创建使用，需要考虑组件销毁destroy方法，保留原html
            this.originHtml = this.element[0].outerHTML;
            var title = this.options.title || "";
            //1.3 考虑href，并考虑href与click事件的冲突
            this.element.addClass("button-main").wrap('<a class="jazz-button" href="javascript:;" title="' + title + '"></a>');
            //1.4 写入group属性，统一管理共同组按钮使用。
            var group = typeof this.options.group == "string" ? this.options.group : "";
            this.element.attr("group", group);
            this.container = this.element.parents('a:first');

            //2.create 按钮的外部包装部分
            //为了初始化table的hover事件缓存对象
            tempTable = this._createButtonOutterDom();
            this.container.append(tempTable);
            this.element.appendTo(this.container.find(".button-main-td"));
            //3.create 按钮的主体部分
            var main = this._createButtonInnerDom();
            this.element.append(main).attr("name", this.options.name || "");
            //4.处理按钮是否disabled样式
            if (this.options.disabled == true || this.options.disabled == "true") {
                this.options.disabled = true;
                this.container.addClass('jazz-state-disabled');
            } else {
                this.options.disabled = false;
            }
        },
        /**
         * @desc 初始化组件
         * @private
         */
        _init: function() {
            this._super();

            //1.button据options.width/height初始化时设置button宽度和高度，
            //2015-9-1，zys,制作自适应的高度宽度的button所以去掉设置的代码
            this._setButtonWidth();
            this._setButtonHeight();

            //2.绑定main按钮事件
            this._bindButtonEvents();

            //3.处理下拉菜单的情况
            if (this.options.items) {
                //items获取两种方式
                //一是直接为数组格式[{}]
                //二是通过createitems回调函数处理返回数组格式[{}]
                var items = eval(this.options.items);
                if (!items) {
                    var createitems = this.options.createitems;
                    if (createitems) {
                        items = this._customopration(this.options.createitems);
                    }
                }
                this.options.items = items || [];
                this._createButtonMenuDom(); //创建menu
                this._bindButtonMenuEvent(); //绑定下拉事件
            }
        },
        /**
         * @desc 通过正则获取插入body中元素的背景图url路径，得到当前页面的默认主目录路径，以此找到默认图标路径。
         * @private
         */
        _getButtonDefaultUrl: function() {
            try {
                var obj = $('<div id="div-img-src"  class="jazz-button-img-src"></div>').appendTo(document.body);
                var str = obj.css("background-image");
                //url("http://localhost:8082/JAZZ/lib/themes/default/images/tool-sprites-dark.png")
                var start = str.indexOf('(');
                var end = str.lastIndexOf('tool-sprites-dark.png');
                this.defaulturl = str.substring(start + 1, end).replace('"', "");
                obj.remove();
            } catch (e) {
                jazz.log(e);
            }
        },
        /**
         * @desc create 按钮this.element的外部包装部分（主要处理按钮下拉箭头时的排列为止）
         * @private
         */
        _createButtonOutterDom: function() {
            //1.确定下拉箭头arrow
            //如果issplitbutton=true，则显示下拉箭头，将items生成的menu绑定到下拉箭头上
            //若issplitbutton==false，则不显示下拉箭头，将items生成的menu绑定到element上
            var arrowclass = this.options.arrowalign ? "arrow-align-" + this.options.arrowalign : "arrow-align-right";
            var arrow = '<div class="button-arrow" style="display:none;margin:0px;"><img alt="" src="" /></div>';
            if (this.options.issplitbutton) {
                //arrow = '<div class="button-arrow '+arrowclass+'"><img alt="" src="../../../themes/default/button/images/jazz-button-arrow-down.png" /></div>'
                //arrow = '<div class="button-arrow '+arrowclass+'"><img alt="" src="'+this.defaulturl+'jazz-button-arrow-down.png" /></div>';
                arrow = '<div class="button-arrow ' + arrowclass + '"><img alt="" src="' + this.defaulturl + 'jazz-button-arrow-down.png"/></div>';
                if (this.options.showmenuevent == "hover") {
                    arrow = '<div class="button-arrow ' + arrowclass + '" style="width:13px;height:8px;"><img alt="" src="' + this.defaulturl + 'jazz-button-arrow-down.png" style="display:none;" /></div>';
                    this.container.css("margin", "0px");
                }
            }
            //2.拼接放着main和arrow的tr和td
            //table的td个数有2个，不管是否有下拉箭头都生成相应存放的td
            //但是table的行数是由arrow的上下top/bottom布局决定的1
            var table = [];
            table.push('<table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;"><thead>');
            var isFillet = this.options.isfillet ? "-fillet" : "";
            if (this.options.arrowalign == "top") {
                table.push('<tr><td class="button-top-left');
                table.push(isFillet);
                table.push('-corner button-corner"></td><td class="button-top button-border button-background"></td><td class="button-top-right');
                table.push(isFillet);
                table.push('-corner button-corner"></td></tr></thead><tbody><tr class="button-background"><td class="button-left button-border"></td><td class="button-arrow-td">');
                table.push(arrow);
                table.push('</td><td class="button-right button-border"></td></tr>');
                table.push('<tr class="button-background"><td class="button-left button-border"></td><td class="button-main-td"></td><td class="button-right button-border"></td></tr>');
                table.push('</tbody><tfoot><tr><td class="button-bottom-left');
                table.push(isFillet);
                table.push('-corner button-corner"></td><td class="button-bottom button-border button-background"></td><td class="button-bottom-right');
                table.push(isFillet);
                table.push('-corner button-corner"></td></tr>');
            } else if (this.options.arrowalign == "bottom") {
                table.push('<tr><td class="button-top-left');
                table.push(isFillet);
                table.push('-corner button-corner"></td><td class="button-top button-background button-border"></td><td class="button-top-right');
                table.push(isFillet);
                table.push('-corner button-corner"></td></tr></thead><tbody>');
                table.push('<tr class="button-background"><td class="button-left button-border"></td><td class="button-main-td"></td><td class="button-right button-border"></td></tr>');
                table.push('<tr class="button-background"><td class="button-left button-border"></td><td class="button-arrow-td">');
                table.push(arrow);
                table.push('</td><td class="button-right button-border"></td></tr>');
                table.push('</tbody><tfoot><tr><td class="button-bottom-left');
                table.push(isFillet);
                table.push('-corner button-corner"></td><td class="button-bottom button-background button-border"></td><td class="button-bottom-right');
                table.push(isFillet);
                table.push('-corner button-corner"></td></tr>');
            } else if (this.options.arrowalign == "left") {
                table.push('<tr><td class="button-top-left');
                table.push(isFillet);
                table.push('-corner button-corner"></td><td colspan="2" class="button-top button-background button-border"></td><td class="button-top-right');
                table.push(isFillet);
                table.push('-corner button-corner"></td></tr></thead><tbody>');
                table.push('<tr class="button-background"><td class="button-left button-border"></td><td class="button-arrow-td">');
                table.push(arrow);
                table.push('</td><td class="button-main-td"></td><td class="button-right button-border"></td></tr>');
                table.push('</tbody><tfoot><tr><td class="button-bottom-left');
                table.push(isFillet);
                table.push('-corner button-corner"></td><td colspan="2" class="button-bottom button-background button-border"></td><td class="button-bottom-right');
                table.push(isFillet);
                table.push('-corner button-corner"></td></tr>)');
            } else {
                table.push('<tr><td class="button-top-left');
                table.push(isFillet);
                table.push('-corner button-corner"></td><td colspan="2" class="button-top button-background button-border"></td><td class="button-top-right');
                table.push(isFillet);
                table.push('-corner button-corner"></td></tr></thead><tbody>');
                table.push('<tr class="button-background"><td class="button-left button-border"></td><td class="button-main-td"></td><td class="button-arrow-td">');
                table.push(arrow);
                table.push('</td><td class="button-right button-border"></td></tr>');
                table.push('</tbody><tfoot><tr><td class="button-bottom-left');
                table.push(isFillet);
                table.push('-corner button-corner"></td><td colspan="2" class="button-bottom button-background button-border"></td><td class="button-bottom-right');
                table.push(isFillet);
                table.push('-corner button-corner"></td></tr>');
            }
            table.push('</tfoot></table>');
            return table.join("");
        },
        /**
         * @desc create 按钮this.element的主体部分（主要处理按钮图标和文字排列位置）
         * @private
         */
        _createButtonInnerDom: function() {
            //create 按钮的主体部分
            //该部分既是创建按钮图片上下左右放置的div Dom结构
            var text = this.options.text || "";
            var src = this.options.iconurl || "";
            var defaultview = this.options.defaultview;
            var textviewclass = "";
            //根据defaultview决定button组件显示的内容
            if (defaultview == 0 || defaultview == "0") { //0只显示图标
                text = "";
                textviewclass = "button-text-hide";
                //src = src || "../../../themes/default/button/images/button-default-dark.png";
                src = src || this.defaulturl + "button-default-dark.png";
            } else if (defaultview == 1 || defaultview == "1") { //1只显示文字
                text = text || "jazz-button";
                textviewclass = "button-text-only";
                src = "";
            } else { //2图标加文字
                text = text || "jazz-button";
                //src = src || "../../../themes/default/button/images/button-default-dark.png";
                src = src || this.defaulturl + "button-default-dark.png";
            }

            var textdiv = '<div class="button-text ' + textviewclass + '">' + text + '</div>';
            var hiddenImgClass = src ? "" : "button-image-hide";
            var img = '<img alt="" src="' + src + '" />';

            var main = "";
            if (this.options.iconalign == "top") {
                main = '<div class="button-main-top ' + hiddenImgClass + '">' + img + '</div>' + textdiv;
            } else if (this.options.iconalign == "bottom") {
                main = textdiv + '<div class="button-main-bottom ' + hiddenImgClass + '">' + img + '</div>';
            } else if (this.options.iconalign == "right") {
                textdiv = '<div class="button-text text-align-left ' + textviewclass + '">' + text + '</div>';
                main = textdiv + '<div class="button-main-right ' + hiddenImgClass + '">' + img + '</div>';
            } else {
                textdiv = '<div class="button-text text-align-right ' + textviewclass + '">' + text + '</div>';
                main = '<div class="button-main-left ' + hiddenImgClass + '">' + img + '</div>' + textdiv;
            }
            return main;
        },
        /**
         * @desc 在button组件中通过jazz.widget(items)方式创建menu下拉菜单
         * @private
         */
        _createButtonMenuDom: function() {
            if (this.options.items.length == 0) {
                return;
            }
            //使用muenu组件组织items中的内容
            var items = this.options.items;
            var vtype = items[0]["vtype"] || "menu";
            var name = items[0]["name"] || "jazz-menu-" + (++jazz.zindex);
            //1.menu可以绑定到this.menuButton或者this.element上
            //2.menuButton可以有或者没有
            items[0]["target"] = this.container;
            this.menu = jazz.widget(items[0]);
            //this.menu = $("<div name='"+name+"' vtype='"+vtype+"'></div>").appendTo("body");
            //this.menu.parseComponent(items[0]);
        },
        /**
         * @desc button组件绑定响应事件
         * @return undefined
         * @private
         * @example  this._bindButtonEvents();
         */
        _bindButtonEvents: function() {
            var $this = this;
            //处理gridpanel中toolbar定义的button数据传递问题
            var gridpanel = this.element.parents("[vtype=gridpanel]");

            //绑定this.element事件
            //将按钮点击事件绑定到button-main-td上（具体到button-main-td上，是因为区别下拉箭头的绑定事件）
            this.mainButton = this.container.find(".button-main-td,.button-corner,.button-border");
            this.mainButton.on('mousedown.button', function(e) {
                if (e.which === 2 || e.which === 3) { //右键、滚轮
                    return false;
                }
                var checktype = $this.options.checktype;
                if (!$this.options.disabled) {
                    //1.鼠标按下down的时候无论按钮group或checktype的情况，都选中按钮
                    //即mousedown时，选中按钮状态，留待mouseup时处理按钮的显示样式
                    if (checktype == "radio" || checktype == "check") {

                    } else {
                        $this._toggleSelectedStyle("selected");
                    }
                }
            }).on('mouseup.button', function(e) {
                if (e.which === 2 || e.which === 3) {
                    return false;
                }
                //1.mouseup时消除mousedown样式
                $this._handleMouseupEvent();
                //2.mouseup时响应click回调函数
                if (!$this.options.disabled) {
                    if ($this.options.click) {
                        var data = null;
                        if (gridpanel['length']) { //将按钮组件与gridpanel组件结合，处理选中数据行数据的接口
                            data = gridpanel.gridpanel("getSelection");
                        }
                        if ($this.options.delay) {
                            $this._event("click", e, data);
                            //置灰按钮
                            $this.disable();
                            //经过delaytime之后，将按钮重新置为可用（延迟时间解决重复提交bug问题）
                            setTimeout(function() {
                                $this.enable();
                            }, $this.options.delaytime);
                        } else {
                            $this._event("click", e, data);
                        }
                    }
                }
            });
            this.container.on('contextmenu.jazz-button-contextmenu', function(e) {
                //屏蔽鼠标右键
                return false;
            }).on('click.button', function(e) {
                //对于具有下拉菜单的复合按钮，当按钮整体container被点击时，
                //若showmenuevent=="hover"则显示鼠标滑过效果

                //注意：container的click事件不影响button-main-td的绑定事件
                if ($this.option.showmenuevent == "hover") {
                    $(this).trigger("mouseenter.menuButton");
                }
            });
        },
        /**
         * @desc mouseup时消除mousedown样式
         * @private
         */
        _handleMouseupEvent: function() {
            var $this = this;
            var disabled = $this.options.disabled;
            if (!disabled) {
                //1.鼠标按下down和弹起up的时候首先要根据按钮的checktype类型决定按钮状态的改变
                //a.checktype为null的普通按钮，只是按下显示pressed状态，弹起时候去掉显示的pressed状态，而不对其他按钮状态进行区别改变
                //b.checktype=radio，mousedown不做选中或不选中操作，mouseup的时候做选中状态，并且要对其他同组或者不同组的按钮进行状态上的修改
                //c.checktype=check，mousedown不做选中或不选中操作，mouseup的时候做相反的操作，但是不影响同组或不同组的其他按钮状态
                var group = $this.options.group;
                var checktype = $this.options.checktype;
                var toggleiconurl = $this.options.toggleiconurl;
                var iconurl = $this.options.iconurl;
                var defaultview = this.options.defaultview;

                if (checktype == "radio" || checktype == "check") {
                    if (checktype == "radio") {
                        //a.选中当前按钮
                        $this._toggleSelectedStyle("selected");
                        if (group) {
                            //b.将该组其他按钮（无论checktype什么类型）的选中状态都去掉
                            $('[group="' + group + '"]').each(function(i, domEle) {
                                if ($this.element[0] != this) {
                                    var obj = $(this).data("button");
                                    if (obj) {
                                        dv = obj.options.defaultview;
                                        if (parseInt(dv) == 2 || parseInt(dv) == 0) {
                                            if (obj.options.toggleiconurl) {
                                                obj.element.find("img").attr("src", obj.options.iconurl);
                                            }
                                        }
                                        obj.container.removeClass("jazz-button-pressed");
                                    }
                                }
                            });
                        } else {
                            //若未分组，对其他按钮不进行选中状态处理
                        }
                    } else if (checktype == "check") {
                        //mouseup的时候做相反的操作，但是不影响同组或不同组的其他按钮状态
                        if ($this.container.hasClass("jazz-button-pressed")) {
                            $this._toggleSelectedStyle("unselected");
                        } else {
                            $this._toggleSelectedStyle("selected");
                        }
                    }
                } else {
                    $this._toggleSelectedStyle("unselected");
                }
            }
        },
        /**
         * @desc 根据参数值切换默认图标和鼠标按下样式
         * @paras "selected"/"unselected"
         * @private
         */
        _toggleSelectedStyle: function(style) {
            var $this = this;
            var toggleiconurl = $this.options.toggleiconurl;
            var iconurl = $this.options.iconurl;
            var defaultview = this.options.defaultview;
            if (style == "selected") {
                if (parseInt(defaultview) == 2 || parseInt(defaultview) == 0) {
                    if (toggleiconurl) {
                        $this.element.find("img").attr("src", toggleiconurl);
                    }
                }
                $this.container.addClass("jazz-button-pressed");
            } else if (style == "unselected") {
                if (parseInt(defaultview) == 2 || parseInt(defaultview) == 0) {
                    if (toggleiconurl) {
                        $this.element.find("img").attr("src", iconurl);
                    }
                }
                $this.container.removeClass("jazz-button-pressed");
            }
        },
        /**
         * @desc 根据showmenuevent属性绑定不同的按钮下拉事件
         * @event {mouseover}{mouseout}{mousedown}{mouseup}
         * @private
         */
        _bindButtonMenuEvent: function() {
            //showmenuevent控制打开显示下拉menu的方式
            if (this.options.showmenuevent == "click") {
                this._bindButtonMenuClickEvent();
            } else {
                this._bindButtonMenuHoverEvent();
            }
        },
        /**
         * @desc 鼠标滑过按钮方式打开下拉菜单menu处理
         * @event{mouseenter}{mouseleave}
         * @private
         */
        _bindButtonMenuHoverEvent: function() {
            if (this.options.items.length == 0) {
                return;
            }
            var that = this;
            //延时鼠标移入移出事件
            var disabled = that.options.disabled;
            this.container.on('mouseenter.menuButton', function(e) {
                disabled = that.options.disabled;
                if (!disabled) {
                    //that._toggleSelectedStyle("selected");
                    $(this).find(".button-arrow-td img").show();
                    $(this).addClass("jazz-button-pressed");
                    that._showMenu();
                    var menuname = that.menu.attr("name");
                    $(".jazz-menu-base:not([name='" + menuname + "'])").hide();
                    if ($(this).data("time2")) {
                        clearTimeout($(this).data("time2"));
                        $(this).data("time2", "");
                    }
                }
            }).on('mouseleave.menuButton', function(e) {
                if (!disabled) {
                    var current = $(this);
                    if (current.data("time2")) {
                        return;
                    }
                    var time2 = setTimeout(function() {
                        //that._toggleSelectedStyle("unselected");
                        var offset = current.offset();
                        if (e.pageX <= offset.left ||
                            Math.ceil(e.pageX) >= offset.left + current.width() ||
                            e.pageY <= offset.top) {
                            that._hideMenu();
                            current.find(".button-arrow-td img").hide();
                            current.removeClass("jazz-button-pressed");
                        }
                        current.data("time2", "");
                    }, 100);
                    $(this).data("time2", time2);
                }
            });
        },
        /**
         * @desc 根据items和issplitbutton属性绑定按钮下拉事件
         * @event{mousedown}{mouseup}
         * @private
         */
        _bindButtonMenuClickEvent: function() {
            if (this.options.items.length == 0) {
                return;
            }
            var that = this;
            var issplitbutton = that.options.issplitbutton;
            if (issplitbutton) {
                this.menuButton = this.container.find(".button-arrow-td");
            } else {
                //this.menuButton = this.element;
                this.menuButton = this.container.find(".button-main-td");
                that.menuButton.off('mousedown.button').off('mouseup.button');
            }

            this.menuButton.on('mousedown.menuButton', function(e) {
                if (e.which === 2 || e.which === 3) {
                    return false;
                }
                var disabled = that.options.disabled;
                var checktype = that.options.checktype;
                if (!disabled) {
                    //1.鼠标按下down的时候无论按钮group或checktype的情况，都选中按钮
                    //即，mousedown时，选中按钮状态
                    if (checktype == "radio" || checktype == "check") {

                    } else {
                        that._toggleSelectedStyle("selected");
                    }

                    if (that.menu.is(':hidden')) {
                        that._showMenu();
                    } else {
                        that._hideMenu();
                    }
                    var menuname = that.menu.attr("name");
                    $(".jazz-menu-base:not([name='" + menuname + "'])").hide();
                }
                e.preventDefault();
                e.stopPropagation();
            }).on('mouseup.menuButton', function(e) {
                if (e.which === 2 || e.which === 3) {
                    return false;
                }
                var disabled = that.options.disabled;
                if (!disabled) {
                    that._handleMouseupEvent();
                }
            });
        },
        /**
         * @desc document.body mousedown事件控制menubutton显示与隐藏
         * @private
         */
        _bindDocumentClickEvents: function() {
            var $this = this;
            $(document.body).bind('mousedown.buttonmenu', function(e) {
                if ($this.menu.is(":hidden")) {
                    return;
                }
                var target = $(e.target);
                if (target.is($this.element)) {
                    $this.menu.hide();
                    return;
                }
                if (target.is($this.menuButton) || target.is($this.menu) || $this.menu.has(target).length > 0) {
                    return;
                }

                var offset = $this.container.offset();
                if (e.pageX < offset.left ||
                    e.pageX > offset.left + $this.container.width() ||
                    e.pageY < offset.top ||
                    e.pageY > offset.top + $this.container.height()) {

                    $this.menu.hide();
                }
            });
        },
        /**
         * @desc 覆盖组件响应resize事件时，重新计算组件宽度的方法
         *      即按钮组件不予响应resize大小调整
         */
        _width: function() {

        },
        /**
         * @desc 覆盖组件响应resize事件时，重新计算组件高度的方法
         *      即按钮组件不予响应resize大小调整
         */
        _height: function() {

        },
        /**
         * @desc 设置button宽度，不予百分比设置（因为按钮无明确参照父对象）
         * @private
         */
        _setButtonWidth: function() {
            //按钮若是给定this.options.width 固定宽度，
            //则需要计算实际分配给this.element的宽度，
            //即为realwidth = this.options.width - this.container的边框边距 - (若是有.button-arrow-td下拉箭头的宽度）
            if (!this.options.width || /^\d+(\.\d+)?%$/.test(this.options.width)) {
                return;
            }
            var settingWidth = parseInt(this.options.width) || 0;
            var realWidth = 0,
                containerMargin = $(".button-corner", this.container).width(), //this.container.outerWidth(true)-this.container.width();
                arrowWidth = 0,
                issplitbutton = this.options.issplitbutton,
                arrowalign = this.options.arrowalign;
            if (settingWidth > 0) {
                if (issplitbutton && (arrowalign == "left" || arrowalign == "right")) {
                    arrowWidth = this.container.find("td.button-arrow-td").width();
                }
                var elmarginWidth = this.element.outerWidth(true) - this.element.outerWidth();
                realWidth = settingWidth - (containerMargin * 2) - arrowWidth - elmarginWidth; //圆角有两个
                if (realWidth > 0) {
                    this.element.width(realWidth);
                }
            }
        },
        /**
         * @desc 设置button高度，不予百分比设置（因为按钮无明确参照父对象）
         * @private
         */
        _setButtonHeight: function() {
            if (!this.options.height || /^\d+(\.\d+)?%$/.test(this.options.height)) {
                return;
            }
            var settingHeight = parseInt(this.options.height) || 0;
            var realHeight = 0,
                containerMargin = $(".button-corner", this.container).height(), //this.container.outerHeight(true)-this.container.height();
                arrowHeight = 0,
                issplitbutton = this.options.issplitbutton,
                arrowalign = this.options.arrowalign,
                el = this.container.find("td.button-main-td");
            if (settingHeight > 0) {
                if (issplitbutton && (arrowalign == "top" || arrowalign == "bottom")) {
                    arrowHeight = this.container.find("td.button-arrow-td").height();
                }
                var elmarginHeight = el.outerHeight(true) - el.outerHeight();
                realHeight = settingHeight - (containerMargin * 2) - arrowHeight - elmarginHeight; //圆角有两个
                if (realHeight > 0) {
                    //this.element.height(realHeight);
                    //this.element.css({"line-height":realHeight+"px"});
                    el.height(realHeight);
                    if (issplitbutton && (arrowalign == "left" || arrowalign == "right")) {
                        el.css({
                            "line-height": realHeight + "px"
                        });
                    }
                }
            }
        },
        /**
         * @desc 切换是否开启圆角
         * @private
         * */
        _setButtonFillet:function(value){
            value = (value == "true") ? true : value;
            value = (value == "false") ? false : value;
            
            if((typeof value != "boolean") || this.options.isfillet == value){
                return;
            }
            this.options.isfillet = value;
            if(this.options.isfillet){
                this.container.find(".button-top-left-corner")
                .removeClass("button-top-left-corner")
                .addClass("button-top-left-fillet-corner");
                
                this.container.find(".button-top-right-corner")
                .removeClass("button-top-right-corner")
                .addClass("button-top-right-fillet-corner");
                
                this.container.find(".button-bottom-left-corner")
                .removeClass("button-bottom-left-corner")
                .addClass("button-bottom-left-fillet-corner");
                
                this.container.find(".button-bottom-right-corner")
                .removeClass("button-bottom-right-corner")
                .addClass("button-bottom-right-fillet-corner");
            }else{
                this.container.find(".button-top-left-fillet-corner")
                .removeClass("button-top-left-fillet-corner")
                .addClass("button-top-left-corner");
                
                this.container.find(".button-top-right-fillet-corner")
                .removeClass("button-top-right-fillet-corner")
                .addClass("button-top-right-corner");
                
                this.container.find(".button-bottom-left-fillet-corner")
                .removeClass("button-bottom-left-fillet-corner")
                .addClass("button-bottom-left-corner");
                
                this.container.find(".button-bottom-right-fillet-corner")
                .removeClass("button-bottom-right-fillet-corner")
                .addClass("button-bottom-right-corner");
                
            }
        },
        /**
         * @desc 隐藏按钮下拉菜单
         * @private
         */
        _hideMenu: function() {
            this.menu.fadeOut('slow');
            //this._event('hide', null);
        },
        /**
         * @desc 显示按钮下拉菜单
         * @private
         */
        _showMenu: function() {
            this.menu.fadeIn('slow');
            this.menu.css({
                left: '',
                top: '',
                'z-index': ++jazz.zindex
            }).position({
                my: 'left top',
                at: 'left bottom',
                of: this.container
            });
            //this._event('show', null);
        },
        /**
         * @desc 动态改变属性
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
         * @private
         */
        _setOption: function(key, value) {
            switch (key) {
                case 'isfillet':
                    this._setButtonFillet(value);
                    break;
                case 'width':
                    this.options.width = value;
                    this._setButtonWidth();
                    break;
                case 'height':
                    this.options.height = value;
                    this._setButtonHeight();
                    break;
                case 'disabled':
                    if (value == true || value == "true") {
                        this.options.disabled = true;
                        this.container.addClass('jazz-state-disabled');
                    } else {
                        this.options.disabled = false;
                        this.container.removeClass('jazz-state-disabled');
                    }
                    break;
                case 'text':
                	if(value){
                		var textobj = this.element.find(".button-text");
                		this.options.text = value;
                		textobj.html(value);
                	}
                	break;
                case 'iconurl':
                    if (value == "") {
                        this.element.find("img").parent().addClass("button-image-hide");
                    } else {
                        this.element.find("img").parent().removeClass("button-image-hide");
                    }
                    this.element.find("img").attr("src", value);
            }
            this._super(key, value);
        },
        /**
         * @desc 关闭button，使不可用
         * @example  $('XXX').button('disable');
         */
        disable: function() {
            this._setOption("disabled", true);
        },
        /**
         * @desc 打开button，使可用
         * @example  $('XXX').button('enable');
         */
        enable: function() {
            this._setOption("disabled", false);
        },
        /**
         * @desc 隐藏按钮
         * @public
         * @example $('XXX').button('hide');
         */
        hide: function() {
            this.container.hide();
            if (this.menu && this.menu.is(":visible")) {
                this.menu.hide();
            }
            //this.menu.fadeOut('slow');
            //this._event('hide', null);
        },
        /**
         * @desc 显示按钮
         * @public
         * @example $('XXX').button('show');
         */
        show: function() {
            this.container.show();
        },
        /**
         * @desc button组件高亮按钮选中样式
         * @public
         * @example  $('XXX').button('highlight');
         */
        highlight: function() {
            if (!this.options.disabled) {
                if (this.options.toggleiconurl) {
                    this.element.find("img").attr("src", this.options.toggleiconurl);
                }
                this.container.addClass("jazz-button-pressed");
            }
        },
        /**
         * @desc buttton组件取消高亮按钮选中样式
         * @public
         * @example  $('XXX').button('unhighlight');
         */
        unhighlight: function() {
            if (!this.options.disabled) {
                if (this.options.toggleiconurl) {
                    this.element.find("img").attr("src", this.options.iconurl);
                }
                this.container.removeClass("jazz-button-pressed");
            }
        },
        /**
         * @desc 通过api模拟button组件点击click动作
         * @public
         * @example $('xxx').button('triggerClick');
         */
        triggerClick: function() {
                this.mainButton.trigger('mousedown.button');
                this.mainButton.trigger('mouseup.button');
            }
            /**
             * @desc 组件销毁（尚需统一销毁测试）
             */
            /*destroy: function() {
//          this.tempTable = null;
            this.container.remove();
            if (this.menu) {
                this.menu.remove();
            }
            this._super();
        }*/
    });
});
