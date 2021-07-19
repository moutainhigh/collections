/**
 * @package
 * @功能描述 特殊字符过滤
 * @author zhouyi
 * @time 2007-10-25
 * @since V1.0.0
 * @requires jquery.js 1.2.1
 */
(function($) {
	/**
	 * 构造函数
	 */
	$.filterInput = function(el,options){
		this.element = el;
		this.options = options;
		this.create();
	};
	
	$.extend($.filterInput.prototype,{
		attrs:function(){
			var el = this.element;
			return {
				id		   	:el.getAttribute('id'),
				filterReg	:el.getAttribute('filterReg')||"\\?|>|<|\\/|\\\\",
				filterChar	:el.getAttribute('filterChar')
			};
		},
		/**
		 * 构造节点为YOUI对象
		 */
		create:function(){
			if(this.element){
				this.options = (!this.options)?{}:this.options;
				$.extend(this.options,this.attrs());
				var $element = $(this.element);
				var id = this.options.id;
				this.createElement();
			}
		},
		
		createElement:function(){
			var $element = $(this.element);
			var self = this;
			$element.keypress(function(){
				
			});
			
			$element.keyup(function(event){
				var value = $(this).attr("value");
				var keyCode = event.keyCode;
				switch(keyCode){
					case 37:
						break;
					case 38:
						break;
					case 39:
						break;
					case 40:
						break;
					default:
						var filterReg = self.options.filterReg;
						var regExp = new RegExp(filterReg,'g');
						if(value){
							if(regExp.test(value)){
								var rng = this.createTextRange();
								var s=document.selection.createRange(); 
								s.setEndPoint("StartToStart",rng);
								var sIndex = s.text.length;
									
								value = value.replace(regExp,'');
								$(this).attr("value",value);
								/* IE 下起作用*/
								if($.browser.msie==true){
									rng.moveEnd("character",-this.value.length); 
									rng.moveStart("character",-this.value.length);
									rng.collapse(true); 
									rng.moveEnd("character",sIndex-2);
									rng.moveStart("character",sIndex-1);
									rng.select(); 
								}
							}
						}
				}
				
			});
		}
	});
	
	$.fn.extend({
		filterInput:function(o){
			return this.each(function(){
				new $.filterInput(this,o);
			});
		}
	});
})($);
	