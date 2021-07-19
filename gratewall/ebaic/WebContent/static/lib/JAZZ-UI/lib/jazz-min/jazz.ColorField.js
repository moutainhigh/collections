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
			var pageHeight = jazz.util.windowHeight(); 
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
})(jQuery);