/**
 * @author zhouyi
 * @version 1.0.0
 * @time 2007-12-17
 */
 
(function($) {
	$.youi.panel = function(el,o){
		$.youi.createObject(this,el,o);
	};
	
	$.extend($.youi.panel.prototype,$.youi.object.prototype,{
		attrs:function(){
			var el = this.element;
			return {
				id:el.getAttribute('id'),
				caption:el.getAttribute('caption')
			}
		},
		
		createElement:function(){
			this.$element     = $(this.element);
			this.$element.addClass('youi-panel');
			var footerContent = $('.youi-panel-footer-content',this.$element);
			var copyFooterContent = footerContent.clone();
			footerContent.remove();
			var  $copyElement = this.$element.clone();
			this.$element.empty();
			
			this.addHeader();
			this.addMiddle($copyElement);
			this.addFooter(copyFooterContent);
			
			
			if(jQuery.isFunction(this.options.after)){
				this.options.after.apply(this);
			}
		},
		
		addHeader:function(){
			var self = this;
			this.$element.prepend('<div class="youi-panel-tl"><div class="youi-panel-tr"><div class="youi-panel-tc"><div class="youi-panel-header youi-unselectable" style="-moz-user-select: none;"><span class="youi-panel-header-text"></span></div></div></div></div>');
			var titleSpan = $('.youi-panel-header-text:first',this.$element);
			
			if(this.options.expandable==true){
				var expandHandle = $('<div class="youi-panel-expandHandle"></div>');
				expandHandle.insertAfter(titleSpan);
				this.$element.find('div.youi-panel-tl').click(function(){
					$('.youi-panel-expandHandle:first',this).click();
				});
				
				expandHandle.click(function(event){
					if($.isFunction(self.options.beforeExpand)){
						if(self.options.beforeExpand.apply(expandHandle,[self.options.expand])==false){
							return false;
						}
					}
					var className = this.className;
					self.options.expand = false;
					$(this).toggleClass('youi-panel-expandHandle-expandable');
					if(className.indexOf('youi-panel-expandHandle-expandable')!='-1'){
						expand(self.$element,self.options);
					}else{
						collapse(self.$element,self.options);
					}
					
					if($.isFunction(self.options.afterExpand)){
						self.options.afterExpand.apply(expandHandle,[self.options.expand]);
					}
					event.stopPropagation();
				});
				
				if(this.options.expanded!=true){
					//expandHandle.click();
				}
			}
			
			titleSpan.text(this.options.caption||'');
		},
		
		addMiddle:function($copyElement){
			this.$element.append('<div class="youi-panel-bwrap"><div class="youi-panel-ml"><div class="youi-panel-mr"><div class="youi-panel-mc"><div class="youi-panel-body"></div></div></div></div>');
			var panelBody = $('.youi-panel-body',this.$element);
			
			panelBody.append($copyElement.html());
		},
		
		addFooter:function(footerContent){
			this.$element.append('<div class="youi-panel-bl"><div class="youi-panel-br"><div class="youi-panel-bc"><div class="youi-panel-footer"></div></div></div></div>');
			var panelFooter =  $('.youi-panel-footer',this.$element);
			if(footerContent){panelFooter.append(footerContent);}
		},
		
		print:function(){
			
		}
	});
	
	function expand($element,o){
		o.expand = true;
		$element.find('>div.youi-panel-bwrap').show();
		$element.find('>div.youi-panel-bl').show();
		$('select',$element).show();
	}
	
	function collapse($element,o){
		o.expand = false;
		$element.find('>div.youi-panel-bwrap').hide();
		$element.find('>div.youi-panel-bl').hide();
		$('select',$element).hide();
	}
	/**
	 * 接口方法
	 */
	$.fn.extend({
		panel:function(o){
			return this.each(function() {
				new $.youi.panel(this, o);
			});
		},
		
		trigglePanel:function(){
			$('.youi-panel-expandHandle:first',this).click();
			return this;
		},
		
		collapsePanel:function(){
			var className = $('.youi-panel-expandHandle:first',this)[0].className;
			if(className.indexOf('youi-panel-expandHandle-expandable')=='-1'){
				$('.youi-panel-expandHandle:first',this).click();
			}
			return this;
		}
	});
		
})(jQuery);