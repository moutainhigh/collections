(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',
		         'form/jazz.form.DropdownField'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	
    var _idx=0, isIE=!$.support.cssFloat, _ie=isIE?'-ie':'',
	//isMoz=isIE?false:/mozilla/.test(navigator.userAgent.toLowerCase()) && !/webkit/.test(navigator.userAgent.toLowerCase()),
	baseThemeColors=['ffffff','000000','eeece1','1f497d','4f81bd','c0504d','9bbb59','8064a2','4bacc6','f79646'],
	subThemeColors=['f2f2f2','7f7f7f','ddd9c3','c6d9f0','dbe5f1','f2dcdb','ebf1dd','e5e0ec','dbeef3','fdeada',
		'd8d8d8','595959','c4bd97','8db3e2','b8cce4','e5b9b7','d7e3bc','ccc1d9','b7dde8','fbd5b5',
		'bfbfbf','3f3f3f','938953','548dd4','95b3d7','d99694','c3d69b','b2a2c7','92cddc','fac08f',
		'a5a5a5','262626','494429','17365d','366092','953734','76923c','5f497a','31859b','e36c09',
		'7f7f7f','0c0c0c','1d1b10','0f243e','244061','632423','4f6128','3f3151','205867','974806'],
	standardColors=['c00000','ff0000','ffc000','ffff00','92d050','00b050','00b0f0','0070c0','002060','7030a0'],
//	moreColors=[
//    		['003366','336699','3366cc','003399','000099','0000cc','000066'],
//    		['006666','006699','0099cc','0066cc','0033cc','0000ff','3333ff','333399'],
//    		['669999','009999','33cccc','00ccff','0099ff','0066ff','3366ff','3333cc','666699'],
//    		['339966','00cc99','00ffcc','00ffff','33ccff','3399ff','6699ff','6666ff','6600ff','6600cc'],
//    		['339933','00cc66','00ff99','66ffcc','66ffff','66ccff','99ccff','9999ff','9966ff','9933ff','9900ff'],
//    		['006600','00cc00','00ff00','66ff99','99ffcc','ccffff','ccccff','cc99ff','cc66ff','cc33ff','cc00ff','9900cc'],
//    		['003300','009933','33cc33','66ff66','99ff99','ccffcc','ffffff','ffccff','ff99ff','ff66ff','ff00ff','cc00cc','660066'],
//    		['333300','009900','66ff33','99ff66','ccff99','ffffcc','ffcccc','ff99cc','ff66cc','ff33cc','cc0099','993399'],
//    		['336600','669900','99ff33','ccff66','ffff99','ffcc99','ff9999','ff6699','ff3399','cc3399','990099'],
//    		['666633','99cc00','ccff33','ffff66','ffcc66','ff9966','ff6666','ff0066','d60094','993366'],
//    		['a58800','cccc00','ffff00','ffcc00','ff9933','ff6600','ff0033','cc0066','660033'],
//    		['996633','cc9900','ff9900','cc6600','ff3300','ff0000','cc0000','990033'],
//    		['663300','996600','cc3300','993300','990000','800000','993333']
//	],
//	brightColors=[
//	    '00ff33','00ff99','00ffff','00cc33','00cc99','00ccff','009933','009999','0099ff',
//	    '66ff33','66ff99','66ffff','66cc33','66cc99','66ccff','669933','669999','6699ff',
//	    '99ff33','99ff99','99ffff','99cc33','99cc99','99ccff','999933','999999','9999ff',
//	    'ffff33','ffff99','ffffff','ffcc33','ffcc99','ffccff','ff9933','ff9999','ff99ff',
//	    '006633','006699','0066ff','003333','003399','0033ff','000033','000099','0000ff',
//	    '666633','666699','6666ff','663333','663399','6633ff','660033','660099','6600ff',
//	    '996633','996699','9966ff','993333','993399','9933ff','990033','990099','9900ff',
//	    'ff6633','ff6699','ff66ff','ff3333','ff3399','ff33ff','ff0033','ff0099','ff00ff'
//	],
//	grayColors=[
//	    'ffffff','dddddd','cococo','969696','808080','646464','4b4b4b','242424','000000'     
//	],
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
//	int2Hex3=function(i){
//		var h=int2Hex(i);
//		return h+h+h;
//	},
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
	
	/**
	 * @version 1.0
	 * @name jazz.colorfield
	 * @description 颜色选择器组件
	 * @constructor
	 * @extends jazz.dropdownfield
	 */	
    $.widget('jazz.colorfield', $.jazz.dropdownfield,{
    	
		options: /** @lends jazz.colorfield# */ {
        	/**
			 *@type String
			 *@desc 组件类型
			 *@default 'colorfield'
			 */ 
			vtype: 'colorfield',
			
			/**
			 *@type String
			 *@desc 颜色模板说明 可保留
			 *@default '全部颜色,标准颜色.'
			 */
			strings: '全部颜色,标准颜色',        	
			// callbacks
    		
    		/**
			 *@desc 鼠标焦点离开输入框时触发
			 *@param {event} 事件
			 *@param {ui.newValue} 新修改的值 
			 *@param {ui.oldValue} 旧值
			 *@event
			 *@example
			 *<br/>$("XXX").colorfield("option", "change", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("colorfieldchange",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… change="XXX()"></div> 或 <div…… change="XXX"></div>
			 */
			change: null
		},
		
		/** @lends jazz.colorfield */
		/**
		 * @desc 创建组件
		 * @private
		 */ 
		 _create: function(){
			this.options.downheight = 250;
			//创建组件
        	this._super();
		 },
		 
	 	/**
         * @desc 初始化组件
         * @private
         */ 
		_init: function(){
			this._super();
			
			this._paletteIdx=1;
			this._id='evo-cp'+_idx++;
			this._isPopup=true;
			this._palette=null;

			this._palette = this.itemsContainer.css({
        		"width": "205",
        		"height": "190"
        	}).addClass("evo-pop jazz-widget").append(this._paletteHTML());
			if(jazz.util.isIE(7)){
				this._palette.css("width","203");
				this._palette.css("height","220");
			}else if(jazz.util.isIE(8)){
				this._palette.css("width","224");
				this._palette.css("height","175");
			}
			
			this._palette.parent().addClass("jazz-panel").css({"background-color": "#fff"});
			
			this._bindEvent();
			
			this._validator();

		},
		
		//覆盖field中的_editable方法， color组件不可编辑
		_editable: function(){
			this.options.editable = false;
			this._super();
		},
		
		/**
         * @desc 生成颜色面板
         * @return 返回颜色面板
		 * @private
		 * @example this._paletteHTML();
         */
		_paletteHTML: function() {
			var h=[], pIdx=this._paletteIdx=Math.abs(this._paletteIdx),
				labels = this.options.strings.split(',');
			// palette
			h.push('<span>',this['_paletteHTML'+pIdx](),'</span>');
			// links
			h.push('<div class="evo-more"><a href="javascript:void(0)">', labels[1+pIdx],'</a>');
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
			oTRTH='<tr><th colspan="10" class="jazz-widget-content">';
			// base theme colors
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
			return h.join(''); 
		},
		
		/**
         * @desc 绑定事件
		 * @private
         */
		_bindEvent: function() {
			var es=this._palette.find('div.evo-color'), sel= "td"; //this.options.history?'td,.evo-cHist div':'td';
			this._cTxt1=es.eq(0).children().eq(0);
			this._cTxt2=es.eq(1).children().eq(0);
			var $this = this;
			this._palette.off("mouseover.colorfield").on('mouseover.colorfield', sel, function(evt){
				var c=toHex3($(this).attr('style').substring(17));
				$this.inputtext.css("background-color", c);
			});
			this.itemsContainer.off("mouseout.colorfield").on('mouseout.colorfield', function(evt){
				if($this._newValue){
					$this.inputtext.css("background-color", $this._newValue);
				}else{
					$this.inputtext.css("background-color", "#fff");
				}
			});
			this._palette.off("mousedown.colorfield").on('mousedown.colorfield', sel, function(e){
				$this._oldValue = $this._oldText = $this.getValue();
				var c=toHex3($(this).attr('style').substring(17));
        		$this._newValue = $this._newText = c;
				$this.setValue(c);
				$this.inputtext.css("background-color", c);			
				$this._hideDropdown();
				$this._event("change", e, $this._changeData());
			});			
        	this.inputtext.off("focus.colorfield blur.colorfield").on("focus.colorfield", function(){
        		if($this.input.val()=="" && $this.options.valuetip){
					$(this).val("");
					$(this).removeClass('jazz-field-comp-input-tip');
				}
        	}).on("blur.colorfield", function(){ 
        		if($this.input.val()=="" && $this.options.valuetip && !$this._newValue){
					$(this).val($this.options.valuetip);
					$(this).addClass('jazz-field-comp-input-tip');
				}
        	});			
		},
        
		/**
         * @desc 获取颜色值
		 * @param {v} 颜色值
		 * @example $('XXX').colorfield('getText');
         */
        getText: function(){
        	return this.getValue();
        },
        
		/**
         * @desc 获取颜色值
		 * @param {v} 颜色值
		 * @example $('XXX').colorfield('getValue');
         */
        getValue: function(){
        	return this.input.val() || "";
        },
        
		/**
         * @desc 获取颜色值
		 * @param {v} 颜色值
		 * @example $('XXX').colorfield('setValue','v');
         */
        setText: function(v){
        	this.setValue(v);
        },
        
		/**
         * @desc 设置颜色值  
		 * @param {value} 颜色值
		 * @example $('XXX').colorfield('setValue','#ff6633');
         */
		setValue: function(value) {
			if(value == "" || value == undefined){ value = ""; }
			this.inputtext.css("background-color", value);
			this.inputtext.val("");			
			this.inputview.html(value+"");
			this.input.val(value);
		}
		
	});
});
