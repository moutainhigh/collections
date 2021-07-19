(function ($, factory) {

    if (jazz.config.isUseRequireJS === true) {
        define(['jquery', 'jazz.BoxComponent'], factory);
    } else {
        factory($);
    }
})(jQuery, function ($) {
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
            width: 200,

            /**
             *@type String
             *@desc 日历组件高度
             *@default '200'
             */
            height: 200,

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

            /**
             *@type  boolean
             *@desc  是否显示年份下拉框
             *@default  false
             */
            isshowdatelist: false,

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
            finish: null,
            
            /**
             *@type function
             *@desc 选择上一年事件
             *@event
             *@example
             **<br/>$("XXX").date("option", "preyear", function(event){  <br/>} <br/>});
             *或:
             *<br/>$("XXX").on("preyear",function(event, ui){  <br/>} <br/>});
             *或：
             *function XXX(){……}
             *<div…… preyear="XXX()"></div> 或 <div…… preyear="XXX"></div>
             */            
            preyear: null,
            
            /**
             *@type function
             *@desc 选择上一月事件
             *@event
             *@example
             **<br/>$("XXX").date("option", "premonth", function(event){  <br/>} <br/>});
             *或:
             *<br/>$("XXX").on("premonth",function(event, ui){  <br/>} <br/>});
             *或：
             *function XXX(){……}
             *<div…… premonth="XXX()"></div> 或 <div…… premonth="XXX"></div>
             */             
            premonth: null,
            
            /**
             *@type function
             *@desc 选择下一年事件
             *@event
             *@example
             **<br/>$("XXX").date("option", "nextyear", function(event){  <br/>} <br/>});
             *或:
             *<br/>$("XXX").on("nextyear",function(event, ui){  <br/>} <br/>});
             *或：
             *function XXX(){……}
             *<div…… nextyear="XXX()"></div> 或 <div…… nextyear="XXX"></div>
             */            
            nextyear: null,
            
            /**
             *@type function
             *@desc 选择下一月事件
             *@event
             *@example
             **<br/>$("XXX").date("option", "nextyear", function(event){  <br/>} <br/>});
             *或:
             *<br/>$("XXX").on("nextyear",function(event, ui){  <br/>} <br/>});
             *或：
             *function XXX(){……}
             *<div…… nextmonth="XXX()"></div> 或 <div…… nextyear="XXX"></div>
             */                      
            nextmonth: null
        },

        /** @lends jazz.date*/

        /**
         * @desc 创建组件
         * @private
         */
        _create: function () {
            this._super();
            // 如果已经指定了默认日期，将默认日期的格式处理一下并转化为Date类型
            if (this.options.defaultvalue) {
                this.options.defaultvalue = new Date(this.options.defaultvalue.replace(/-/g, "/"));
            }

            // 创建dom元素
            this.element.addClass("jazz-date").css({
                "width": this.options.width,
                "height": this.options.height
            });

            // 上一年
            this.calendarPreYear = $('<div class="jazz-date-preyear"></div>').appendTo(this.element);
            // 上一月
            this.calendarPreMonth = $('<div class="jazz-date-premonth"></div>').appendTo(this.element);
            // 下一年
            this.calendarNextYear = $('<div class="jazz-date-nextyear"></div>').appendTo(this.element);
            // 下一月
            this.calendarNextMonth = $('<div class="jazz-date-nextmonth"></div>').appendTo(this.element);

            this.calendarText = $('<div class="jazz-date-text"><table align="center"><tr><td><div class="jazz-date-year-o">' + this.options.year + '</div></td><td> <div class="jazz-date-year-w">年</div> </td><td><div class="jazz-date-month-o">' + this.options.month + '</div></td><td> <div class="jazz-date-year-w">月</div> </td></tr></table></div>').appendTo(this.element);
            // 当前年
            this.calendarYear = this.calendarText.find('.jazz-date-year-o');
            //this.calendarYearText = this.calendarYear;
            // 当前月
            this.calendarMonth = this.calendarText.find('.jazz-date-month-o');
            //this.calendarMonthText = this.calendarMonth;
            if (this.options.isshowdatelist === true) {
                this.calendarYear.addClass("jazz-cursor-pointer");
                this.calendarMonth.addClass("jazz-cursor-pointer");
            }
            // 表头
            this.calendarHeader = $(
                '<table class="jazz-date-header" cellspacing="0" cellpadding="0" border="0" width="100%" '
                    + 'height="22" align="center" vAlign="middle">'
                    + '  <thead>'
                    + '    <tr>'
                    + '      <td style="height:22px" class="jazz-date-week">一</td>'
                    + '      <td class="jazz-date-week">二</td>'
                    + '      <td class="jazz-date-week">三</td>'
                    + '      <td class="jazz-date-week">四</td>'
                    + '      <td class="jazz-date-week">五</td>'
                    + '      <td class="jazz-date-week">六</td>'
                    + '      <td class="jazz-date-week">日</td>'
                    + '    </tr>'
                    + '  </thead>'
                    + '</table>').appendTo(this.element);
            // 表区
            this.items = $(
                '<table class="jazz-date-body" cellspacing="0" cellpadding="0" border="0" width="100%" '
                    + 'height="' + (this.options.height - this.calendarText.outerHeight(true) - this.calendarHeader.outerHeight(true) ) + '" '
                    + 'align="center" vAlign="middle">'
                    + '  </tbody>'
                    + '</table>').appendTo(this.element);


            this.yearnum = 5;
        },

        /**
         * @desc 初始化组件
         * @private
         */
        _init: function () {

            this._draw();

            this._bindEvent();
        },

        /**
         * @desc 绑定事件
         * @private
         */
        _bindEvent: function () {
            var $this = this;

            // 绑定事件
            this.calendarPreYear.off("mousedown.date").on("mousedown.date", function (e) {
                $this._listHide();
                $this.preYear();
                $this._event("preyear", e);
                e.stopPropagation();
            });
            this.calendarPreMonth.off("mousedown.date").on("mousedown.date", function (e) {
                $this._listHide();
                $this.preMonth();
                $this._event("premonth", e);
                e.stopPropagation();
            });
            this.calendarNextMonth.off("mousedown.date").on("mousedown.date", function (e) {
                $this._listHide();
                $this.nextMonth();
                $this._event("nextmonth", e);
                e.stopPropagation();
            });
            this.calendarNextYear.off("mousedown.date").on("mousedown.date", function (e) {
                $this._listHide();
                $this.nextYear();
                $this._event("nextyear", e);
                e.stopPropagation();
            });
            this.items.off("mousedown.date, mouseover.date, mouseout.date").on("mousedown.date",function (event) {
                var t = event.target, $t = $(t);
                // 如果选中的不是TD，或者选中的不是本月的TD，不做任何操作
                if (t.tagName != "TD" || $t.hasClass("jazz-date-pmonth") || $t.hasClass("jazz-date-nmonth")) {
                    return;
                }
                // 将已经选中的节点去掉
                $this.items.find(".jazz-date-selected").removeClass("jazz-date-selected");
                // 给当前节点添加样式
                $t.addClass("jazz-date-selected");
                // 抛出select事件
                var _day = t.innerHTML;
                if (_day && _day.length == 1) {
                    _day = "0" + _day;
                }
                var _month = $this.options.month;
                if (_month && (_month + "").length == 1) {
                    _month = "0" + _month;
                }
                $this._listHide();
                //$this._event("select", event, {"date": $this.options.year + "-" + _month + "-" + _day});
                var num = $t.prop("id").length;
                var newdate = $t.prop("id").substring(5,num);
                $this._event("select", event, {"date": newdate});
                event.stopPropagation();
            }).on("mouseover.date",function (event) {
                var t = event.target, $t = $(t);
                // 如果选中的不是TD，或者选中的不是本月的TD，不做任何操作
                if (t.tagName != "TD" || $t.hasClass("jazz-date-pmonth") || $t.hasClass("jazz-date-nmonth")) {
                    return;
                }
                $t.addClass("jazz-date-hover");
                event.stopPropagation();
            }).on("mouseout.date", function (event) {
                var t = event.target, $t = $(t);
                $t.removeClass("jazz-date-hover");
                event.stopPropagation();
            });

            $this.calendarYear.off("mousedown.date").on("mousedown.date", function (event) {
                if ($this.options.isshowdatelist === true) {
                    $this._listMonthHide();
                    $this._yearlist($this.searchyear);

                    if (!$this.listYear) {
                        $this._listyearShow();
                    }

                    $this.listYear.off("click.datelist").on("click.datelist", function (e) {
                        var target = $(e.target);
                        if (target.hasClass("jazz-date-yearlist-td")) {

                            var year = $.trim(target.text());
                            $this.calendarYear.text(year);
                            $this._listYearHide();
                            $this.options.year = parseInt(year || $this.options.year);
                            $this._preDraw(new Date($this.options.year, $this.options.month - 1, 1));
                        } else if (target.hasClass("jazz-date-yearlist-td-p")) {

                            $this.searchyear = $this.yearAfter[0] - $this.yearnum;
                            $this._yearlist($this.searchyear);
                            $this._listyearShow();
                        } else if (target.hasClass("jazz-date-yearlist-td-c")) {

                            $this._listYearHide();
                        } else if (target.hasClass("jazz-date-yearlist-td-n")) {

                            $this.searchyear = $this.yearBefore[$this.yearnum - 1] + $this.yearnum + 1;
                            $this._yearlist($this.searchyear);
                            $this._listyearShow();
                        }

                    });

                    //this.listMonth
                    event.stopPropagation();

                    $(document).off("mousedown.slistdate").on("mousedown.slistdate", function () {
                        $this._listHide();
                        $(this).off("mousedown.slistdate");
                    });
                }
            });

            $this.calendarMonth.off("click.date").on("click.date", function (event) {
                if ($this.options.isshowdatelist === true) {
                    $this._listYearHide();
                    if (!$this.listMonth) {
                        $this._monthlist();
                    }
                    /*else{
                     $this.listMonth.css("display", "block");
                     }*/
                    $this.listMonth.off("click.datelist").on("click.datelist", function (e) {
                        var target = $(e.target);
                        if (target.hasClass("jazz-date-monthlist-td")) {

                            var monthtext = $.trim(target.text());
                            var month = $this._monthTextToNumber(monthtext) || $this.options.month;
                            $this.calendarMonth.text(month);
                            $this._listMonthHide();
                            $this.options.month = month;
                            $this._preDraw(new Date($this.options.year, month - 1, 1));
                        }
                    });

                    event.stopPropagation();

                    $(document).off("mousedown.slistMdate").on("mousedown.slistMdate", function () {
                        $this._listHide();
                        $(this).off("mousedown.slistMdate");
                    });
                }
            });
        },

        /**
         * @todo 简化初始化年份月份列表预留
         * @param type year, month
         */
        _buildList: function (type) {

        },

        /**
         * @desc 绘制
         * @private
         */
        _draw: function () {
            //用来保存日期列表
            var arr = [];

            //用当月第一天在一周中的日期值作为当月离第一天的天数
            // firstday 为本月1号的星期值
            for (var i = 0, firstday = new Date(this.options.year, this.options.month - 1, 1).getDay() - 1; i < firstday; i++) {
                arr.push(0);
            }
            //用当月最后一天在一个月中的日期值作为当月的天数
            for (var i = 1, monthDay = new Date(this.options.year, this.options.month, 0).getDate(); i <= monthDay; i++) {
                arr.push(i);
            }

            //插入日期
            var frag = document.createDocumentFragment();
            while (arr.length) {
                //每个星期插入一个tr
                var row = document.createElement("tr");
                //每个星期有7天
                for (var i = 1; i <= 7; i++) {
                    var cell = document.createElement("td");
                    cell.innerHTML = "&nbsp;";
                    if (arr.length) {
                        var d = arr.shift();
                        if (d) {
                            cell.innerHTML = d;
                            var on = new Date(this.options.year, this.options.month - 1, d);
                            //判断是否今日
                            if (this._isSame(on, new Date())) {
                                $(cell).addClass("jazz-date-today");
                            }
                            //判断是否选择日期
                            if (this.options.defaultvalue && this._isSame(on, this.options.defaultvalue)) {
                                $(cell).addClass("jazz-date-selected");
                            }
                        } else {
                            $(cell).addClass("jazz-date-pmonth");
                        }
                        var cellmonth = this.options.month < 10 ? "0"+this.options.month : this.options.month;
                        var cellday = d < 10 ? "0"+d : d;
                        $(cell).attr("id", "date-"+this.options.year+"-"+cellmonth+"-"+cellday);
                    } else {
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
            this.calendarYear.text(this.options.year);
            this.calendarMonth.text(this.options.month);

            //触发完成事件
            this._event("finish");
        },

        /**
         * @desc 判断两个日期是否同一日
         * @private
         */
        _isSame: function (d1, d2) {
            return (d1.getFullYear() == d2.getFullYear() && d1.getMonth() == d2.getMonth() && d1.getDate() == d2.getDate());
        },

        /**
         * @desc 列表隐藏
         * @private
         */
        _listHide: function () {
            this._listYearHide();
            this._listMonthHide();
        },

        /**
         * @desc 年列表隐藏
         * @private
         */
        _listYearHide: function () {
            if (this.listYear) {
                this.listYear.remove();
                this.listYear = null;
            }
        },

        /**
         * @desc 月份列表隐藏
         * @private
         */
        _listMonthHide: function () {
            if (this.listMonth) {
                this.listMonth.remove();
                this.listMonth = null;
            }
        },

        /**
         * @desc 年份列表显示
         * @private
         */
        _listyearShow: function () {
            var buf = [];
            buf.push('<div class="jazz-date-yearlist" style="display: block;">');
            buf.push('	<table cellspacing="0" cellpadding="3" border="0" align="center">');
            buf.push('		<tbody>');
            for (var i = 0; i < this.yearnum; i++) {
                buf.push('			<tr nowrap="nowrap">');
                buf.push('				<td class="jazz-date-yearlist-td" >');
                buf.push(this.yearAfter[i]);
                buf.push('				</td>');
                buf.push('				<td class="jazz-date-yearlist-td" >');
                buf.push(this.yearBefore[i]);
                buf.push('				</td>');
                buf.push('			</tr>');
            }
            buf.push('		</tbody>');
            buf.push('	</table>');
            buf.push('	<table cellspacing="0" cellpadding="3" border="0" align="center">');
            buf.push('		<tbody>');
            buf.push('			<tr>');
            buf.push('				<td class="jazz-date-yearlist-td-p"> ← </td>');
            buf.push('				<td class="jazz-date-yearlist-td-c"> × </td>');
            buf.push('				<td class="jazz-date-yearlist-td-n"> → </td>');
            buf.push('			</tr>');
            buf.push('		</tbody>');
            buf.push('	</table>');
            buf.push('</div>');
            var str = buf.join("");

            this.calendarYear.children(".jazz-date-yearlist").remove();
            this.listYear = $(str).appendTo(this.calendarYear);

            this.listYear.css({top: this.calendarYear.height() + 1});
        },

        /**
         * @desc 月份列表显示
         * @private
         */
        _monthlist: function () {
            var buf = [];
            buf.push('<div class="jazz-date-monthlist" style="display: block;">');
            buf.push('	<table cellspacing="0" cellpadding="3" border="0" align="center">');
            buf.push('		<tbody>');
            buf.push('			<tr nowrap="nowrap"><td class="jazz-date-monthlist-td" >一月</td><td class="jazz-date-monthlist-td" >七月</td></tr>');
            buf.push('			<tr nowrap="nowrap"><td class="jazz-date-monthlist-td" >二月</td><td class="jazz-date-monthlist-td" >八月</td></tr>');
            buf.push('			<tr nowrap="nowrap"><td class="jazz-date-monthlist-td" >三月</td><td class="jazz-date-monthlist-td" >九月</td></tr>');
            buf.push('			<tr nowrap="nowrap"><td class="jazz-date-monthlist-td" >四月</td><td class="jazz-date-monthlist-td" >十月</td></tr>');
            buf.push('			<tr nowrap="nowrap"><td class="jazz-date-monthlist-td" >五月</td><td class="jazz-date-monthlist-td" >十一</td></tr>');
            buf.push('			<tr nowrap="nowrap"><td class="jazz-date-monthlist-td" >六月</td><td class="jazz-date-monthlist-td" >十二</td></tr>');
            buf.push('		</tbody>');
            buf.push('	</table>');
//				buf.push('	<table cellspacing="0" cellpadding="3" border="0" align="center">');
//				buf.push('		<tbody>');
//				buf.push('			<tr>');
//				buf.push('				<td class="jazz-date-yearlist-td-p"> ← </td>');
//				buf.push('				<td class="jazz-date-yearlist-td-c"> × </td>');
//				buf.push('				<td class="jazz-date-yearlist-td-n"> → </td>');
//				buf.push('			</tr>');
//				buf.push('		</tbody>');
//				buf.push('	</table>');
            buf.push('</div>');
            var str = buf.join("");
            this.listMonth = $(str).appendTo(this.calendarMonth);
            this.listMonth.css({top: this.calendarMonth.height() + 1, right: '20px'});
        },

        /**
         * @desc 月份列表显示
         * @private
         */
        _monthTextToNumber: function (text) {
            var yue = jazz.config.i18n.months;
            for (var i = 0; i < yue.length; i++) {
                if (text == yue[i]) {
                    return (i + 1);
                }
            }
            return "";
        },

        /**
         * @desc 根据日期画日历
         * @private
         */
        _preDraw: function (date) {
            //再设置属性
            this.options.year = date.getFullYear();
            this.options.month = date.getMonth() + 1;
            //重新画日历
            this._draw();
        },

        /**
         * @desc 年列表
         * @param {year} 定位的年份
         * @private
         */
        _yearlist: function (year) {
            var _year = year ? parseInt(year) : this.options.year;
            this.yearAfter = [], this.yearBefore = [];
            for (var i = this.yearnum; i > 0; i--) {
                this.yearAfter.push(_year - i);
                this.yearBefore.push(_year + (this.yearnum - i));
            }
        },

        /**
         * @desc 将日历跳转回当前月
         * @example  $(XXX).date("currentMonth");
         */
        currentMonth: function () {
            this._preDraw(new Date());
        },

        /**
         * @desc 将日历跳转回下一月
         * @example $(XXX).date("nextMonth");
         */
        nextMonth: function () {
            this._preDraw(new Date(this.options.year, this.options.month, 1));
        },

        /**
         * @desc 将日历跳转回下一年
         * @example $(XXX).date("nextYear");
         */
        nextYear: function () {
            this._preDraw(new Date(this.options.year + 1, this.options.month - 1, 1));
        },

        /**
         * @desc 将日历跳转回上一月
         * @example  $(XXX).date("preMonth");
         */
        preMonth: function () {
            this._preDraw(new Date(this.options.year, this.options.month - 2, 1));
        },

        /**
         * @desc 将日历跳转回上一年
         * @example  $(XXX).date("preYear");
         */
        preYear: function () {
            this._preDraw(new Date(this.options.year - 1, this.options.month - 1, 1));
        }

    });
});
