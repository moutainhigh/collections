(function($, factory) {

    if (jazz.config.isUseRequireJS === true) {
        define(['jquery', 'layout/jazz.ContainerLayout'], factory);
    } else {
        factory($);
    }
})(jQuery, function($) {
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
         * @example
         */
        layout: function(cthis, container, config) {
            //1.磊哥讲解 没看懂！！！
            container.addClass("jazz-layout");
            var flag = false;
            var columnwidth = config.columnwidth || config.width;
            /*
             * oldcols标示原来的table的列数
             * 第一次加载的时候肯定是undefined，所以可以借此获得子对象
             */
            if (!this.oldcols) {
                //初始化时候执行一次
                this.oldcols = columnwidth.length;
                this.newcols = 1;
                this.olditems = container.children(); //获取全部的子对象;
            } else {
                //新列数的默认值根据第一次时候的初始化值，应该是1
                this.newcols = columnwidth.length || 1;
                flag = true;
            }
            this._align(this.olditems, container, config);
            //最小阀值超过总宽度时候必须出滚动条
//            $(container).css("overflow", "hidden");
            this.oldcols = this.newcols;
            if (flag) {
                cthis._reflashChildWidth();
            }

        },

        /**
         * @desc 设置布局
         * @param {items} 需要布局的组件集合
         * @param {container} 当前布局容器对象
         * @param {config} 组件布局配置对象
         * @private
         */
        _align: function(items, container, config) {
            /**
             * container 就是 this.element
             * container 就是 this.element
             * container 就是 this.element
             * 重要的话说三遍
             * items就是container里面的children对象集合
             * */
            //1.tablelayout中有子元素才进行布局。
            if (items && items.length > 0) {
                var columnwidth = config.columnwidth || config.width;
                var cols = columnwidth.length; //table行数不设定，列数设定
                // 一切改变之前先验证是否可以改
                /*
                 * 如果设置的width为2列[100px,*]则意味着当前的panel的宽度不能小于table所需要的最小阀值100px
                 * 但是如果巧了，确实小于100px，则会出现以下三种情况
                 * 1.第一次初始化这个组件；结果：连table都没有生成直接就是一个空白的界面（时候要提示错误？？）
                 * 2.初始化了这个组件，由于父级panel改变大小，小于最小阀值；结果：table中的宽度并没有变化依旧是原来的样子；出现滚动条
                 * 3.初始化了组件重新设置columnwidth属性导致新的最小阀值大于当前panel宽度，结果：强制变化，但是出现滚动条
                 * */
                if (columnwidth.length == 0) {
                    jazz.info("【table 布局】width未设置布局列宽，请确定重设。");
                    return false;
                }
                if (!this.validateColumnWidthFormat(columnwidth, tableWidth)) {
                    jazz.info("【table 布局】width设置数值格式不正确。");
                    return false;
                }
                
                //2 为重新计算布局做准备
                //2.1. 计算this.element布局元素的宽度
                var tableWidth = $(container).outerWidth(true) - $(container).scrollLeft();
                //2.2. 将全部items从table中转移到缓存池中
                var item;
                this.tempCachePool = $("<div style=\"display:none\"></div>");
                this.tempCachePool.appendTo(this.element);
                for(var i = 0, i_len = items.length; i < i_len; i++){
                    item = $(items[i]);
//                    if(item.attr("vtype") == "hiddenfield" || item.attr("vtype") == "toolbar") continue;
                	this.tempCachePool.append(item);
                }
                //3.生成布局的table表格
                if (!!this.table) {
                    //如果不是第一次生成则清空table
                    this.table.children().remove();
                }else{
                    //第一次的话默认生成一个table加载进去
                    this.table = $("<table cellspacing='0' cellpadding='0' style='border-collapse: collapse; table-layout: fixed; width: 100%;'></table>");
                    this.table.appendTo(container);
                }
//                this.table = null;
//                this.table = $("<table cellspacing='0' cellpadding='0' style='border-collapse: collapse; table-layout: fixed; width: " + tableWidth + "px;" + "'></table>");

                //4.tablelayout布局中若是存在toolbar组件进行位置预处理（赵永生改）
                //toolbar/hidefield不属于table内部的元素 所以单独处理
                if($(items[0]).attr("vtype") == "toolbar"){
                    //如果在前面则放在table前面
                    this.table.before($(items[0]));
                }else if($(items[i_len-1]).attr("vtype") == "toolbar"){
                    //i_len可用 局部变量问题
                    //如果是后面则放在table后面
                    this.table.after($(items[i_len-1]));
                }
//                var t = container.children("div:last-child");
//                if (t.attr("vtype") == "toolbar") {
//                    this.table.prependTo(container);
//                } else {
//                    this.table.appendTo(container);
//                }
                //5.根据layoutconfig的参数生成this.table dom结构

                var colgroup = "<colgroup>";
                for (var i = 0; i < cols; i++) {
                    colgroup += "<col />";
                }

                colgroup += "</colgroup>";
                colgroup = $(colgroup).appendTo(this.table);
                this._calculateTableWidth($(this.table), columnwidth);

                //6.缓存tablelayout布局容器宽度
                var $this = this;
                $this.options.cacheContainerWidth = tableWidth;
                //	        	$(container).on('resize.tableLayout', function(){
                //	        		var tableCurrentWidth = $(this).width();
                //	        		if($this.options.cacheContainerWidth != tableCurrentWidth){
                //	        			$this.options.cacheContainerWidth = tableCurrentWidth;
                //	        			$($this.table).width(tableCurrentWidth+"px");
                //	        			$this._calculateTableWidth(this,columnwidth);
                //	        		}
                //	        	});

                this.tbody = $("<tbody></tbody>").appendTo(this.table);

                var tr = null;
                if (!!config.rowheight) {
                    tr = $("<tr style='height:" + config.rowheight + "'></tr>");
                } else {
                    tr = $("<tr></tr>");
                }
                var td = $("<td style='vertical-align: top;'></td>");
                if (config.border) {
                    td.addClass("jazz-table-innerborder");
                }
                for (var i = 0; i < cols; i++) {
                    var newtd = td.clone();
                    newtd.appendTo(tr);
                }
                tr.clone().appendTo(this.tbody);

                //7.将待放入布局中的元素逐个放入tablelayout布局中
                var rowIndex = 0,
                    colIndex = 0;
                for (var i = 0; i < items.length; i++) {
                    var item = items[i];

                    //8.特殊处理元素中hiddenfield 和 toolbar组件
                    var vtype = $(item).attr('vtype'); //, style = $(item).css('display');
                    if (vtype == "hiddenfield" || vtype == "toolbar") {
                        this.table.after($(items[i]));
                        /*$(container).after($(item));
                        if($(item).attr('location')=="top"){
                        	$(item).insertBefore($(container).parent());
                        }*/
                        continue;
                    }

                    var rowspanNum = parseInt($(item).attr("rowspan")) || 1;
                    var colspanNum = parseInt($(item).attr("colspan")) || 1;
                    //9.校验该元素colspan是否超过了预设总列数
                    if (colspanNum > cols) {
                        jazz.info("【跨列布局错误】当前容器中第" + (i + 1) + "个元素colspan='" + colspanNum + "' 大于布局列数 cols='" + cols + "'。 ");
                        break;
                    } else {
                        //10.计算获取下个元素放入到tbody中的行列坐标
                        var nextIndex = this._combinedTdCell(item, tr, cols, rowIndex, colIndex, rowspanNum, colspanNum);
                        rowIndex = nextIndex.rowIndex;
                        colIndex = nextIndex.colIndex;
                    }
                }
                
                
                //12.所有过程完毕删除临时变量
                this.tempCachePool.remove();
                this.tempCachePool = null;
                
            }
        },
        /**
         * @desc 根据定义的columnWidth计算每一列的宽度
         * @param {table} this.element的表格，放置表格布局元素的table
         * @param {columnWidth} 列宽定义值 如['25%','20%','30%','25%']
         * @private
         */
        _calculateTableWidth: function(table, columnWidth) {
            var width_table = $(table).width();
            var re = /^[0-9]+.?[0-9]*$/;
            var fixedColumnWidth = 0;
            var exceptFixedColumnWidth = 0;
            var percentColumnWidth = 0;
            var hasPercentWidth = false;
            var autoWidthNums = 0;
            var tempColumnWidth = [];
            /*
             * 计算逻辑如下：
             * 1.首先计算固定值（纯数字或含px的字符值），据此合计fixedColumnWidth；
             * 2.其次除去固定值的剩余宽度，exceptFixedColumnWidth = width_table - fixedColumnWidth;
             * 3.再次合计百分比的总宽度percentColumnWidth
             * 5.最后计算除去固定值和百分比总宽度  leftColumnWidth = exceptFixedColumnWidth - percentColumnWidth;
             * 6.计算剩下的列数宽度，为等分剩余宽度。
             */

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
        validateColumnWidthFormat: function(colsWidth, tableWidth) {
            //先验证是不是*或者px/%百分比结尾的
            var re = /^[0-9]+.?[0-9]*[(px)%]?$/;
            var totalWidth = 0;
            var flag = true;
            for (var i = 0; i < colsWidth.length; i++) {
                var temp = colsWidth[i];
                if (re.test(temp) || $.trim(temp) == '*') {
                    /* 取消宽度效验
                     * 原因：tablewidth本来就小，改变columnwidth时候最小阀值大于tablewidth时候
                     * frompanel那级已经变了，如果这层没有变化就会出问题,数据前后不一致
                    //符合列宽定义标准
                    if (temp.indexOf("px") != -1) {
                        totalWidth += parseInt(temp.replace("px", ""));
                    } else if (temp.indexOf("%") != -1) {
                        totalWidth += tableWidth * parseInt(temp.replace("%", "") / 100);
                    }
                    */
                } else {
                    flag = false;
                    break;
                }
            }
            /* 取消宽度效验
            //在验证所有px的加起来是不是超过了table的宽度
            if (totalWidth > tableWidth) {
                flag = false;
            }
            */
            return flag;
        },

        _combinedTdCell: function(item, tr, cols, rowIndex, colIndex, rowspanNum, colspanNum) {
            //1.根据当前的colIndex校验该rowIndex行能否放得下colspanNum的元素
            var flag = true;
            while (flag) {
                //该行放不下的时候，另起一行
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

            if (rowspanNum > 1 || colspanNum > 1) {
                //根据rowspan和rowIndex确认是否添加tr
                var trCounts = this.tbody.children("tr").length;
                if (rowspanNum + rowIndex > trCounts) {
                    for (var r = 1; r <= rowspanNum + rowIndex - trCounts; r++) {
                        var newtr = tr.clone();
                        newtr.appendTo(this.tbody);
                    }
                }
                //根据rowIndex确认跨行范围，然后逐个td单元格进行跨列或者隐藏处理
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
                //此时，colIndex有可能是在display==none的td位置上，就需要下面程序的判断处理
            } else {
                colIndex = 0;
                rowIndex++;
                //a.此时rowIndex有可能会大于tbody中已有的总tr行数，因为是新的一行那么colIndex=0这
                //个新行首个单元格display!=none，所以colIndex可以确定为colIndex=0;
                //b.此时也不必再进行新增行的$(tr).clone().appendTo(this.tbody);，这个操作由combinedTdCell方法
                //开头while()循环中处理

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

});
