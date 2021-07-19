(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 
		         'jazz.Toolbar', 
		         'jazz.Loading'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){

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
                'iconclass': 'jazz-btn-zoomin',
                'name': 'jazz-imageview-button-zoomin',
                'defaultview': 0,
                'click': function() {
                    $this.zoom('in');
                }
            }, {
                'vtype': 'button',
                'align': 'right',
                'iconclass': 'jazz-btn-zoomout',
                'name': 'jazz-imageview-button-zoomout',
                'defaultview': 0,
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
                    'iconclass': 'jazz-imageviewer-rotate-left',
                    'defaultview': 1,
                    'click': function() {
                        $this._rotate(90);
                    }
                }, {
                    'vtype': 'button',
                    'align': 'right',
                    'text': '右转',
                    'iconclass': 'jazz-imageviewer-rotate-right',
                    'defaultview': 1,
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
                this.imgWrap.find("img").attr("src",imgSrc);
            }else{
            	this.imgWrap = $("<image src='" + imgSrc + "' alt='' class='jazz-imageview-image jazz-imageview-minsize' />")
            	    .appendTo(this.element)
            	    .wrap("<a class='jazz-helper-link' href='javascript:;'></a>");
            }
			
            /* 记录原始尺寸,记录图片初始化显示尺寸 */
            this.imgWrap.load(function() {
                var width = $(this).width(),
                    height = $(this).height(),
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
                    }
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
        	 /**
        	  * Internet Explorer 10、Firefox 以及 Opera 支持 transform 属性。
			  *	Chrome 和 Safari 需要前缀 -webkit-
			  *注释：Internet Explorer 9 需要前缀 -ms-
        	  */
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
                    });
                    //return;
                }
            } else {
            	if (!transform) {
	            	//for IE6 IE7 IE8
	            	vwidth = 'width',
                	vheight = 'height';
	            }
            	if (imgSize[vwidth] + imgOffset['left'] < wrapOffset['left'] + wrapWidth) {
                    this.imgWrap.offset({
                        'left': (wrapWidth - imgSize[vwidth] + wrapOffset['left'])
                    });
                }
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
            this.imgWrap
                .height(imgSize['height'])
                .width(imgSize['width']);
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

});
