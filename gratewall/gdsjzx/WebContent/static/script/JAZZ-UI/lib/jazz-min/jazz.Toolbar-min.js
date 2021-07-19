(function($,factory){if(jazz.config.isUseRequireJS===true){define(["jquery","jazz.Button"],factory)}else{factory($)}})(jQuery,function($){$.widget("jazz.toolbar",$.jazz.boxComponent,{options:{vtype:"toolbar",items:null,overflowtype:"scroll",orientation:"horizontal"},_create:function(){this._super();var toolbardoms="<div class='jazz-toolbar-scroll-first'></div><div class='jazz-toolbar-content'><div class='jazz-toolbar-content-wrap'><div class='jazz-toolbar-contentarea-second'></div><div class='jazz-toolbar-contentarea-first'></div></div></div><div class='jazz-toolbar-scroll-second'></div>";var toolbarclass="jazz-toolbar jazz-toolbar-orientation-horizontal";if(this.options.orientation=="vertical"){toolbarclass="jazz-toolbar jazz-toolbar-orientation-vertical"}this.element.addClass(toolbarclass).append(toolbardoms);this.scollFirst=this.element.find(".jazz-toolbar-scroll-first");this.toolbarContent=this.element.find(".jazz-toolbar-content");this.toolbarContentWrap=this.element.find(".jazz-toolbar-content-wrap");this.toolbarFirst=this.element.find(".jazz-toolbar-contentarea-first");this.toolbarSecond=this.element.find(".jazz-toolbar-contentarea-second");this.scollSecond=this.element.find(".jazz-toolbar-scroll-second");if(this.options.orientation=="vertical"){if(this.options.height==-1){this.toolbarContent.addClass("toolbar-content-default-height")}if(this.options.width==-1){this.toolbarContent.addClass("toolbar-content-default-width");this.element.addClass("toolbar-default-width")}}},_init:function(){this._super();this._setToolbarWith();this._setToolbarHeight();if(this.options.items==null){this._vtypeCreateElements()}else{this._widgetCreateElements()}if(this.options.overflowtype=="wrap"){if(this.options.orientation=="horizontal"){this.toolbarContentWrap.css("width","100%");this.toolbarFirst.addClass("white-space-normal");this.toolbarSecond.addClass("white-space-normal")}}else{this._bindScrollEvent();if($.isArray(this.options.items)&&this.options.items.length>0){var that=this;setTimeout(function(){that._computeToolbarWidth()},150)}}},finish:function(){var that=this;if(this.options.overflowtype!="wrap"){setTimeout(function(){that._computeToolbarWidth()},150)}},_vtypeCreateElements:function(){var orientation=this.options.orientation;var childrens=this.element.children();var child=null,wrapHtml="<div class='jazz-toolbar-element'></div>";if(orientation=="vertical"){for(var i=0,m=childrens.length;i<m-3;i++){child=$(childrens[i]);child.wrap(wrapHtml);this.toolbarFirst.append(child.parent())}this.toolbarSecond.hide()}else{if(this.element.children("[align=center]").length==(childrens.length-3)){this.toolbarContent.css("text-align","center")}for(var i=0,m=childrens.length;i<m-3;i++){child=$(childrens[i]);child.wrap(wrapHtml);if(child.attr("align")=="right"){this.toolbarSecond.append(child.parent())}else{this.toolbarFirst.append(child.parent())}}}},_widgetCreateElements:function(){var items=this.options.items;if(!$.isArray(items)){return}var orientation=this.options.orientation;var child=null,wrapHtml="<div class='jazz-toolbar-element'></div>";if(orientation=="vertical"){for(var i=0,len=items.length;i<len;i++){if(items[i]["class"]=="separator"){child=$("<div class='separator'></div>")}else{child=jazz.widget(items[i],this.toolbarFirst)}if(items[i]["vtype"]=="button"){child=child.data("button")["container"]}child.wrap(wrapHtml);this.toolbarFirst.append(child.parent())}this.toolbarSecond.hide()}else{var isAllAlignCenter=true;for(var i=0,len=items.length;i<len;i++){if(items[i]["align"]!="center"){isAllAlignCenter=false}}if(isAllAlignCenter){this.toolbarContent.css("text-align","center")}for(var i=0,len=items.length;i<len;i++){if(items[i]["align"]=="right"){if(items[i]["class"]=="separator"){child=$("<div class='separator'></div>")}else{child=jazz.widget(items[i],this.toolbarSecond)}if(items[i]["vtype"]=="button"){child=child.data("button")["container"]}child.wrap(wrapHtml);this.toolbarSecond.append(child.parent())}else{if(items[i]["class"]=="separator"){child=$("<div class='separator'></div>")}else{child=jazz.widget(items[i],this.toolbarFirst)}if(items[i]["vtype"]=="button"){child=child.data("button")["container"]}child.wrap(wrapHtml);this.toolbarFirst.append(child.parent())}}}},_bindScrollEvent:function(){var that=this;this.element.on("contextmenu.jazz-toolbar-contextmenu",function(e){return false});var orientation=this.options.orientation;var scrollwidth=50;that.scollSecond.off("click").on("click",function(){if(orientation=="horizontal"){var scrollleft=that.toolbarContent.scrollLeft()+scrollwidth;var scrollTotalWidth=that.toolbarContentWrap.width()-that.toolbarContent.width();if(scrollleft>scrollTotalWidth){scrollleft=scrollTotalWidth}that.toolbarContent.scrollLeft(scrollleft)}else{if(orientation=="vertical"){var scrollheight=that.toolbarContent.scrollTop()+scrollwidth;var scrollTotalHeight=that.toolbarContentWrap.height()-that.toolbarContent.height();if(scrollheight>scrollTotalHeight){scrollheight=scrollTotalHeight}that.toolbarContent.scrollTop(scrollheight)}}});that.scollFirst.off("click").on("click",function(){if(orientation=="horizontal"){var scrollleft=that.toolbarContent.scrollLeft()-scrollwidth;that.toolbarContent.scrollLeft(scrollleft)}else{if(orientation=="vertical"){var scrollheight=that.toolbarContent.scrollTop()-scrollwidth;that.toolbarContent.scrollTop(scrollheight)}}})},_computeToolbarWidth:function(){var that=this;var orientation=this.options.orientation;if(orientation=="horizontal"){var tbw1=0;$.each(that.toolbarFirst.children(),function(index,obj){if($(obj).is(":visible")){tbw1+=$(obj).width()}});var tbw2=0;$.each(that.toolbarSecond.children(),function(index,obj){if($(obj).is(":visible")){tbw2+=$(obj).width()}});if(tbw1>0){tbw1+=1;that.toolbarFirst.width(tbw1)}else{}if(tbw2>0){tbw2+=1;that.toolbarSecond.width(tbw2)}else{}var wrapWidth=0;var contentWidth=that.toolbarContent.width();var toolbarWidth=that.element.width();if(toolbarWidth<(tbw1+tbw2)){that.toolbarContent.width(that.element.width()-36);that.toolbarContent.css({position:"relative",left:"18px"});wrapWidth=tbw1+tbw2+3;that.toolbarContentWrap.width(wrapWidth);that.scollFirst.show();that.scollSecond.show()}else{that.toolbarContent.css({width:"100%",position:"static",left:"0px"});that.toolbarContentWrap.css({width:"100%"});that.scollFirst.hide();that.scollSecond.hide()}}else{if(orientation=="vertical"){var toolbarHeight=that.element.height();var contentHeight=that.toolbarContent.height();var contentWrapHeight=that.toolbarContentWrap.height();if(jazz.isNormalSize(that.options.height)){if(toolbarHeight<contentWrapHeight){that.toolbarContent.height(toolbarHeight-36);that.toolbarContent.css({position:"relative",top:"18px"});that.scollFirst.show();that.scollSecond.show()}else{that.toolbarContent.css({height:"100%",position:"static",top:"0px"});that.scollFirst.hide();that.scollSecond.hide()}}}}},_width:function(){var orientation=this.options.orientation;var width=this.options.width;if(orientation=="vertical"&&width==-1){return false}this._super();if(this.iscalculatewidth){if(this.options.overflowtype=="wrap"){if(this.options.orientation=="horizontal"){var tbw1=this.toolbarFirst.width();var tbw2=this.toolbarSecond.width();var toolbarWidth=this.element.width();var leftchildsize=this.toolbarFirst.children().size();var rightchildsize=this.toolbarSecond.children().size();if(leftchildsize>0&&leftchildsize>0){if(toolbarWidth<(tbw1+tbw2)){this.toolbarFirst.css("width","70%");this.toolbarSecond.css("width","auto")}}else{if(leftchildsize===0){this.toolbarSecond.css("width","100%")}if(rightchildsize===0){this.toolbarFirst.css("width","100%")}}}}else{this._computeToolbarWidth()}}},_height:function(){this._super();if(this.iscalculatewidth){if(this.options.overflowtype=="wrap"){}else{this._computeToolbarWidth()}}},_setToolbarWith:function(){var orientation=this.options.orientation;var width=this.options.width;if(orientation=="vertical"&&width==-1){return false}if(this.options.width==-1){width="100%"}if(jazz.isNormalSize(width)){if(jazz.isNumber(width)){this.element.outerWidth(width)}else{if(/^\d+(\.\d+)?%$/.test(width)){var n=this._getCalculatePercentWidth(width);this.element.outerWidth(n)}}}},_setToolbarHeight:function(){var height=this.options.height;if(jazz.isNormalSize(height)){if(jazz.isNumber(height)&&parseFloat(height)>=0){this.element.outerHeight(height)}else{if(/^\d+(\.\d+)?%$/.test(height)){var n=this._getCalculatePercentHeight(height);this.element.outerHeight(n)}}}},addElement:function(items){this._insertSubElement(items);if(this.options.overflowtype!="wrap"){this._computeToolbarWidth()}},appendElement:function(items,name){this._insertSubElement(items,name,"after");if(this.options.overflowtype!="wrap"){this._computeToolbarWidth()}},prependElement:function(items,name){this._insertSubElement(items,name,"before");if(this.options.overflowtype!="wrap"){this._computeToolbarWidth()}},_insertSubElement:function(items,name,position){if(!$.isArray(items)){return}var orientation=this.options.orientation;var child=null,wrapHtml="<div class='jazz-toolbar-element'></div>";if(orientation=="vertical"){for(var i=0,len=items.length;i<len;i++){if(items[i]["class"]=="separator"){child=$("<div class='separator'></div>")}else{child=jazz.widget(items[i],this.toolbarFirst)}if(items[i]["vtype"]=="button"){child=child.data("button")["container"]}child.wrap(wrapHtml);this._insertSubElementByCondition(name,position,child.parent(),this.toolbarFirst)}}else{var isAllAlignCenter=false;var textAlign=this.toolbarContent.css("text-align");if(textAlign=="center"){isAllAlignCenter=true}else{var temp=true;for(var i=0,len=items.length;i<len;i++){if(items[i]["align"]!="center"){temp=false}}var leftnums=this.toolbarFirst.children().length;var rightnums=this.toolbarSecond.children().length;if(leftnums==0&&rightnums==0&&temp){isAllAlignCenter=true}if(isAllAlignCenter){this.toolbarContent.css("text-align","center")}}for(var i=0,len=items.length;i<len;i++){if(items[i]["align"]=="right"){if(items[i]["class"]=="separator"){child=$("<div class='separator'></div>")}else{child=jazz.widget(items[i],this.toolbarSecond)}if(items[i]["vtype"]=="button"){child=child.data("button")["container"]}child.wrap(wrapHtml);this._insertSubElementByCondition(name,position,child.parent(),this.toolbarSecond)}else{if(items[i]["class"]=="separator"){child=$("<div class='separator'></div>")}else{child=jazz.widget(items[i],this.toolbarFirst)}if(items[i]["vtype"]=="button"){child=child.data("button")["container"]}child.wrap(wrapHtml);this._insertSubElementByCondition(name,position,child.parent(),this.toolbarFirst)}}}},_insertSubElementByCondition:function(name,position,newEl,tbar){if(name){var target=this.element.find('div[name="'+name+'"]');if(target.length>0){if(position=="before"){$(target).parent().before(newEl)}else{$(target).parent().after(newEl)}}else{if(position=="before"){tbar.prepend(newEl)}else{tbar.append(newEl)}}}else{if(position=="before"){tbar.prepend(newEl)}else{tbar.append(newEl)}}},removeElement:function(name){if(!name){return}var $this=this;$this.element.find('div[name="'+name+'"]').each(function(i){$(this).parents(".jazz-toolbar-element").remove()});if(this.options.overflowtype!="wrap"){this._computeToolbarWidth()}},hideElement:function(name){if(!name){return}var $this=this;$this.element.find('div[name="'+name+'"]').each(function(i){$(this).parents(".jazz-toolbar-element").hide()});if(this.options.overflowtype!="wrap"){this._computeToolbarWidth()}},showElement:function(name){if(!name){return}var $this=this;$this.element.find('div[name="'+name+'"]').each(function(i){$(this).parents(".jazz-toolbar-element").show()});if(this.options.overflowtype!="wrap"){this._computeToolbarWidth()}},removeButton:function(name){this.removeElement(name)},hideButton:function(name){this.hideElement(name)},showButton:function(name){this.showElement(name)},disableButton:function(name){if(!name){return}var $this=this;$this.element.find('div[name="'+name+'"]').each(function(i){$(this).button("disable")})},enableButton:function(name){if(!name){return}var $this=this;$this.element.find('div[name="'+name+'"]').each(function(i){$(this).button("enable")})},highlightButton:function(name){if(!name){return}this.element.find('div[name="'+name+'"]').button("highlight")},unhighlightButton:function(name){if(!name){return}this.element.find('div[name="'+name+'"]').button("unhighlight")},hide:function(){var $this=this;$this.element.hide()},show:function(){var $this=this;$this.element.show()}})});