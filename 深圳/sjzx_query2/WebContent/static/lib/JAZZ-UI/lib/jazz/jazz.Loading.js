(function ($, factory) {

    if (jazz.config.isUseRequireJS === true) {
        define(['jquery',
            'jazz.BoxComponent' ], factory);
    } else {
        factory($);
    }
})(jQuery, function ($) {
    /**
     * @version 0.5
     * @name jazz.loading
     * @description 加载动画组件
     * @example $('div_id').loading();
     * @example jazz.loading();
     */
    $.widget("jazz.loading", $.jazz.boxComponent, {

        options: /** @lends jazz.loading# */  {

            /**
             *@type String
             *@desc 不显示任何信息， 默认false, 显示加载图片和文字说明， true，不显示任何内容
             *@default false
             *@Deprecated 仅兼容旧版本接口，不再维护
             */
            blank: false,

            /**
             * @desc 图片的路径
             * @default true 使用默认加载图片，用户可自定义图片路径<br>
             *     如果为 ""\undefined\null\false 则不显示加载图片
             */
            imgsrc: true,

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
        _create: function () {
            this.ele = $("<div class='jazz-loading-wrap'>"
                + "<div class='jazz-loading-overlay'></div>"
                + "<div class='jazz-loading-img'><div class='jazz-loading-text'>"
                + this.options.text + "</div></div></div>")
                .appendTo(this.element);
            //imgsrc为 "",undefined, null, false
            if(!this.options.imgsrc){
                this.ele.children(".jazz-loading-img").css("background", "none");
            }
            //用户自定义了图片路径
            if(this.options.imgsrc !== true && this.options.imgsrc){
                this.ele.children(".jazz-loading-img")
                    .css({
                        'background-image': 'url(' + this.options.imgsrc + ')'
                    });
            }
        },

        /**
         * @desc 初始化组件
         * @private
         */
        _init: function () {
            this.changePosition = false;
            this.oldPosition = "";
            if (this.options.blank === true) {
                this.ele.find(".jazz-loading-img").hide();
            } else {
                this.ele.find(".jazz-loading-img").show();
            }
            this.show();
        },

        /**
         * @desc 检查this.element父元素的position属性
         * @private
         */
        _checkPosition: function () {
            var parent = this.element.parent(),
                position = parent.css("position");
            this.oldPosition = this.element.css("position");
            if (this.element[0].tagName == "BODY"/* || parent.get(0) == $("body").get(0)*/) {
                this.ele.css({
                    position: 'fixed'
                });

                if (jazz.isIE(6)) {
                    this.ele.css({
                        'height': $(window).height(),
                        'width': $(window).width()
                    });
                }
                this.ele.addClass("jazz-ie6-position-fixed");
            } else if (position == 'inherit' || position == 'static' || position == '') {
                if (!this.element.hasClass("jazz-window-modal")) {
                    this.element.css({
                        'position': 'relative'
                    });
                }
                this.ele.children(".jazz-loading-overlay").css("height", "100%");

                this.changePosition = true;
            }
        },

        /**
         * @desc 还原this.element元素的position属性
         * @private
         */
        _reverPostion: function () {
            if (this.changePosition) {
                this.element.css("position", this.oldPosition);
                this.changePosition = false;
            }
        },

        /**
         * @desc 隐藏loading动画
         * @example this.hide();
         */
        hide: function () {
            this._reverPostion();
            this.ele.hide();
        },

        /**
         * @desc 显示loading动画
         * @example this.hide();
         */
        show: function () {
            this._checkPosition();
            this.ele.show();
        },

        /**
         * @desc 组件销毁方法
         */
        destroy: function () {
            this.element.children().remove();
            this.element.remove();
        }
    });
});
