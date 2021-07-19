(function($,factory){if(jazz.config.isUseRequireJS===true){define(["jquery","jazz.BoxComponent"],factory)}else{factory($)}})(jQuery,function($){$.widget("jazz.button",$.jazz.boxComponent,{options:{vtype:"button",name:null,text:null,title:null,width:null,height:null,align:"left",iconalign:"left",issplitbutton:false,arrowalign:"right",defaultview:2,iconurl:null,toggleiconurl:null,disabled:false,group:null,checktype:null,delay:false,delaytime:150,showmenuevent:"hover",items:null,click:null,isfillet:true},_create:function(){this._super();this._getButtonDefaultUrl();this.originHtml=this.element[0].outerHTML;var title=this.options.title||"";this.element.addClass("button-main").wrap('<a class="jazz-button" href="javascript:;" title="'+title+'"></a>');var group=typeof this.options.group=="string"?this.options.group:"";this.element.attr("group",group);this.container=this.element.parents("a:first");tempTable=this._createButtonOutterDom();this.container.append(tempTable);this.element.appendTo(this.container.find(".button-main-td"));var main=this._createButtonInnerDom();this.element.append(main).attr("name",this.options.name||"");if(this.options.disabled==true||this.options.disabled=="true"){this.options.disabled=true;this.container.addClass("jazz-state-disabled")}else{this.options.disabled=false}},_init:function(){this._super();this._setButtonWidth();this._setButtonHeight();this._bindButtonEvents();if(this.options.items){var items=eval(this.options.items);if(!items){var createitems=this.options.createitems;if(createitems){items=this._customopration(this.options.createitems)}}this.options.items=items||[];this._createButtonMenuDom();this._bindButtonMenuEvent()}},_getButtonDefaultUrl:function(){try{var obj=$('<div id="div-img-src"  class="jazz-button-img-src"></div>').appendTo(document.body);var str=obj.css("background-image");var start=str.indexOf("(");var end=str.lastIndexOf("tool-sprites-dark.png");this.defaulturl=str.substring(start+1,end).replace('"',"");obj.remove()}catch(e){jazz.log(e)}},_createButtonOutterDom:function(){var arrowclass=this.options.arrowalign?"arrow-align-"+this.options.arrowalign:"arrow-align-right";var arrow='<div class="button-arrow" style="display:none;margin:0px;"><img alt="" src="" /></div>';if(this.options.issplitbutton){arrow='<div class="button-arrow '+arrowclass+'"><img alt="" src="'+this.defaulturl+'jazz-button-arrow-down.png"/></div>';if(this.options.showmenuevent=="hover"){arrow='<div class="button-arrow '+arrowclass+'" style="width:13px;height:8px;"><img alt="" src="'+this.defaulturl+'jazz-button-arrow-down.png" style="display:none;" /></div>';this.container.css("margin","0px")}}var table=[];table.push('<table cellspacing="0" cellpadding="0" border="0" style="border-collapse:collapse;"><thead>');var isFillet=this.options.isfillet?"-fillet":"";if(this.options.arrowalign=="top"){table.push('<tr><td class="button-top-left');table.push(isFillet);table.push('-corner button-corner"></td><td class="button-top button-border button-background"></td><td class="button-top-right');table.push(isFillet);table.push('-corner button-corner"></td></tr></thead><tbody><tr class="button-background"><td class="button-left button-border"></td><td class="button-arrow-td">');table.push(arrow);table.push('</td><td class="button-right button-border"></td></tr>');table.push('<tr class="button-background"><td class="button-left button-border"></td><td class="button-main-td"></td><td class="button-right button-border"></td></tr>');table.push('</tbody><tfoot><tr><td class="button-bottom-left');table.push(isFillet);table.push('-corner button-corner"></td><td class="button-bottom button-border button-background"></td><td class="button-bottom-right');table.push(isFillet);table.push('-corner button-corner"></td></tr>')}else{if(this.options.arrowalign=="bottom"){table.push('<tr><td class="button-top-left');table.push(isFillet);table.push('-corner button-corner"></td><td class="button-top button-background button-border"></td><td class="button-top-right');table.push(isFillet);table.push('-corner button-corner"></td></tr></thead><tbody>');table.push('<tr class="button-background"><td class="button-left button-border"></td><td class="button-main-td"></td><td class="button-right button-border"></td></tr>');table.push('<tr class="button-background"><td class="button-left button-border"></td><td class="button-arrow-td">');table.push(arrow);table.push('</td><td class="button-right button-border"></td></tr>');table.push('</tbody><tfoot><tr><td class="button-bottom-left');table.push(isFillet);table.push('-corner button-corner"></td><td class="button-bottom button-background button-border"></td><td class="button-bottom-right');table.push(isFillet);table.push('-corner button-corner"></td></tr>')}else{if(this.options.arrowalign=="left"){table.push('<tr><td class="button-top-left');table.push(isFillet);table.push('-corner button-corner"></td><td colspan="2" class="button-top button-background button-border"></td><td class="button-top-right');table.push(isFillet);table.push('-corner button-corner"></td></tr></thead><tbody>');table.push('<tr class="button-background"><td class="button-left button-border"></td><td class="button-arrow-td">');table.push(arrow);table.push('</td><td class="button-main-td"></td><td class="button-right button-border"></td></tr>');table.push('</tbody><tfoot><tr><td class="button-bottom-left');table.push(isFillet);table.push('-corner button-corner"></td><td colspan="2" class="button-bottom button-background button-border"></td><td class="button-bottom-right');table.push(isFillet);table.push('-corner button-corner"></td></tr>)')}else{table.push('<tr><td class="button-top-left');table.push(isFillet);table.push('-corner button-corner"></td><td colspan="2" class="button-top button-background button-border"></td><td class="button-top-right');table.push(isFillet);table.push('-corner button-corner"></td></tr></thead><tbody>');table.push('<tr class="button-background"><td class="button-left button-border"></td><td class="button-main-td"></td><td class="button-arrow-td">');table.push(arrow);table.push('</td><td class="button-right button-border"></td></tr>');table.push('</tbody><tfoot><tr><td class="button-bottom-left');table.push(isFillet);table.push('-corner button-corner"></td><td colspan="2" class="button-bottom button-background button-border"></td><td class="button-bottom-right');table.push(isFillet);table.push('-corner button-corner"></td></tr>')}}}table.push("</tfoot></table>");return table.join("")},_createButtonInnerDom:function(){var text=this.options.text||"";var src=this.options.iconurl||"";var defaultview=this.options.defaultview;var textviewclass="";if(defaultview==0||defaultview=="0"){text="";textviewclass="button-text-hide";src=src||this.defaulturl+"button-default-dark.png"}else{if(defaultview==1||defaultview=="1"){text=text||"jazz-button";textviewclass="button-text-only";src=""}else{text=text||"jazz-button";src=src||this.defaulturl+"button-default-dark.png"}}var textdiv='<div class="button-text '+textviewclass+'">'+text+"</div>";var hiddenImgClass=src?"":"button-image-hide";var img='<img alt="" src="'+src+'" />';var main="";if(this.options.iconalign=="top"){main='<div class="button-main-top '+hiddenImgClass+'">'+img+"</div>"+textdiv}else{if(this.options.iconalign=="bottom"){main=textdiv+'<div class="button-main-bottom '+hiddenImgClass+'">'+img+"</div>"}else{if(this.options.iconalign=="right"){textdiv='<div class="button-text text-align-left '+textviewclass+'">'+text+"</div>";main=textdiv+'<div class="button-main-right '+hiddenImgClass+'">'+img+"</div>"}else{textdiv='<div class="button-text text-align-right '+textviewclass+'">'+text+"</div>";main='<div class="button-main-left '+hiddenImgClass+'">'+img+"</div>"+textdiv}}}return main},_createButtonMenuDom:function(){if(this.options.items.length==0){return}var items=this.options.items;var vtype=items[0]["vtype"]||"menu";var name=items[0]["name"]||"jazz-menu-"+(++jazz.zindex);items[0]["target"]=this.container;this.menu=jazz.widget(items[0])},_bindButtonEvents:function(){var $this=this;var gridpanel=this.element.parents("[vtype=gridpanel]");this.mainButton=this.container.find(".button-main-td,.button-corner,.button-border");this.mainButton.on("mousedown.button",function(e){if(e.which===2||e.which===3){return false}var checktype=$this.options.checktype;if(!$this.options.disabled){if(checktype=="radio"||checktype=="check"){}else{$this._toggleSelectedStyle("selected")}}}).on("mouseup.button",function(e){if(e.which===2||e.which===3){return false}$this._handleMouseupEvent();if(!$this.options.disabled){if($this.options.click){var data=null;if(gridpanel.length){data=gridpanel.gridpanel("getSelection")}if($this.options.delay){$this._event("click",e,data);$this.disable();setTimeout(function(){$this.enable()},$this.options.delaytime)}else{$this._event("click",e,data)}}}});this.container.on("contextmenu.jazz-button-contextmenu",function(e){return false}).on("click.button",function(e){if($this.option.showmenuevent=="hover"){$(this).trigger("mouseenter.menuButton")}})},_handleMouseupEvent:function(){var $this=this;var disabled=$this.options.disabled;if(!disabled){var group=$this.options.group;var checktype=$this.options.checktype;var toggleiconurl=$this.options.toggleiconurl;var iconurl=$this.options.iconurl;var defaultview=this.options.defaultview;if(checktype=="radio"||checktype=="check"){if(checktype=="radio"){$this._toggleSelectedStyle("selected");if(group){$('[group="'+group+'"]').each(function(i,domEle){if($this.element[0]!=this){var obj=$(this).data("button");if(obj){dv=obj.options.defaultview;if(parseInt(dv)==2||parseInt(dv)==0){if(obj.options.toggleiconurl){obj.element.find("img").attr("src",obj.options.iconurl)}}obj.container.removeClass("jazz-button-pressed")}}})}else{}}else{if(checktype=="check"){if($this.container.hasClass("jazz-button-pressed")){$this._toggleSelectedStyle("unselected")}else{$this._toggleSelectedStyle("selected")}}}}else{$this._toggleSelectedStyle("unselected")}}},_toggleSelectedStyle:function(style){var $this=this;var toggleiconurl=$this.options.toggleiconurl;var iconurl=$this.options.iconurl;var defaultview=this.options.defaultview;if(style=="selected"){if(parseInt(defaultview)==2||parseInt(defaultview)==0){if(toggleiconurl){$this.element.find("img").attr("src",toggleiconurl)}}$this.container.addClass("jazz-button-pressed")}else{if(style=="unselected"){if(parseInt(defaultview)==2||parseInt(defaultview)==0){if(toggleiconurl){$this.element.find("img").attr("src",iconurl)}}$this.container.removeClass("jazz-button-pressed")}}},_bindButtonMenuEvent:function(){if(this.options.showmenuevent=="click"){this._bindButtonMenuClickEvent()}else{this._bindButtonMenuHoverEvent()}},_bindButtonMenuHoverEvent:function(){if(this.options.items.length==0){return}var that=this;var disabled=that.options.disabled;this.container.on("mouseenter.menuButton",function(e){disabled=that.options.disabled;if(!disabled){$(this).find(".button-arrow-td img").show();$(this).addClass("jazz-button-pressed");that._showMenu();var menuname=that.menu.attr("name");$(".jazz-menu-base:not([name='"+menuname+"'])").hide();if($(this).data("time2")){clearTimeout($(this).data("time2"));$(this).data("time2","")}}}).on("mouseleave.menuButton",function(e){if(!disabled){var current=$(this);if(current.data("time2")){return}var time2=setTimeout(function(){var offset=current.offset();if(e.pageX<=offset.left||Math.ceil(e.pageX)>=offset.left+current.width()||e.pageY<=offset.top){that._hideMenu();current.find(".button-arrow-td img").hide();current.removeClass("jazz-button-pressed")}current.data("time2","")},100);$(this).data("time2",time2)}})},_bindButtonMenuClickEvent:function(){if(this.options.items.length==0){return}var that=this;var issplitbutton=that.options.issplitbutton;if(issplitbutton){this.menuButton=this.container.find(".button-arrow-td")}else{this.menuButton=this.container.find(".button-main-td");that.menuButton.off("mousedown.button").off("mouseup.button")}this.menuButton.on("mousedown.menuButton",function(e){if(e.which===2||e.which===3){return false}var disabled=that.options.disabled;var checktype=that.options.checktype;if(!disabled){if(checktype=="radio"||checktype=="check"){}else{that._toggleSelectedStyle("selected")}if(that.menu.is(":hidden")){that._showMenu()}else{that._hideMenu()}var menuname=that.menu.attr("name");$(".jazz-menu-base:not([name='"+menuname+"'])").hide()}e.preventDefault();e.stopPropagation()}).on("mouseup.menuButton",function(e){if(e.which===2||e.which===3){return false}var disabled=that.options.disabled;if(!disabled){that._handleMouseupEvent()}})},_bindDocumentClickEvents:function(){var $this=this;$(document.body).bind("mousedown.buttonmenu",function(e){if($this.menu.is(":hidden")){return}var target=$(e.target);if(target.is($this.element)){$this.menu.hide();return}if(target.is($this.menuButton)||target.is($this.menu)||$this.menu.has(target).length>0){return}var offset=$this.container.offset();if(e.pageX<offset.left||e.pageX>offset.left+$this.container.width()||e.pageY<offset.top||e.pageY>offset.top+$this.container.height()){$this.menu.hide()}})},_width:function(){},_height:function(){},_setButtonWidth:function(){if(!this.options.width||/^\d+(\.\d+)?%$/.test(this.options.width)){return}var settingWidth=parseInt(this.options.width)||0;var realWidth=0,containerMargin=$(".button-corner",this.container).width(),arrowWidth=0,issplitbutton=this.options.issplitbutton,arrowalign=this.options.arrowalign;if(settingWidth>0){if(issplitbutton&&(arrowalign=="left"||arrowalign=="right")){arrowWidth=this.container.find("td.button-arrow-td").width()}var elmarginWidth=this.element.outerWidth(true)-this.element.outerWidth();realWidth=settingWidth-(containerMargin*2)-arrowWidth-elmarginWidth;if(realWidth>0){this.element.width(realWidth)}}},_setButtonHeight:function(){if(!this.options.height||/^\d+(\.\d+)?%$/.test(this.options.height)){return}var settingHeight=parseInt(this.options.height)||0;var realHeight=0,containerMargin=$(".button-corner",this.container).height(),arrowHeight=0,issplitbutton=this.options.issplitbutton,arrowalign=this.options.arrowalign,el=this.container.find("td.button-main-td");if(settingHeight>0){if(issplitbutton&&(arrowalign=="top"||arrowalign=="bottom")){arrowHeight=this.container.find("td.button-arrow-td").height()}var elmarginHeight=el.outerHeight(true)-el.outerHeight();realHeight=settingHeight-(containerMargin*2)-arrowHeight-elmarginHeight;if(realHeight>0){el.height(realHeight);if(issplitbutton&&(arrowalign=="left"||arrowalign=="right")){el.css({"line-height":realHeight+"px"})}}}},_setButtonFillet:function(value){value=(value=="true")?true:value;value=(value=="false")?false:value;if((typeof value!="boolean")||this.options.isfillet==value){return}this.options.isfillet=value;if(this.options.isfillet){this.container.find(".button-top-left-corner").removeClass("button-top-left-corner").addClass("button-top-left-fillet-corner");this.container.find(".button-top-right-corner").removeClass("button-top-right-corner").addClass("button-top-right-fillet-corner");this.container.find(".button-bottom-left-corner").removeClass("button-bottom-left-corner").addClass("button-bottom-left-fillet-corner");this.container.find(".button-bottom-right-corner").removeClass("button-bottom-right-corner").addClass("button-bottom-right-fillet-corner")}else{this.container.find(".button-top-left-fillet-corner").removeClass("button-top-left-fillet-corner").addClass("button-top-left-corner");this.container.find(".button-top-right-fillet-corner").removeClass("button-top-right-fillet-corner").addClass("button-top-right-corner");this.container.find(".button-bottom-left-fillet-corner").removeClass("button-bottom-left-fillet-corner").addClass("button-bottom-left-corner");this.container.find(".button-bottom-right-fillet-corner").removeClass("button-bottom-right-fillet-corner").addClass("button-bottom-right-corner")}},_hideMenu:function(){this.menu.fadeOut("slow")},_showMenu:function(){this.menu.fadeIn("slow");this.menu.css({left:"",top:"","z-index":++jazz.zindex}).position({my:"left top",at:"left bottom",of:this.container})},_setOption:function(key,value){switch(key){case"isfillet":this._setButtonFillet(value);break;case"width":this.options.width=value;this._setButtonWidth();break;case"height":this.options.height=value;this._setButtonHeight();break;case"disabled":if(value==true||value=="true"){this.options.disabled=true;this.container.addClass("jazz-state-disabled")}else{this.options.disabled=false;this.container.removeClass("jazz-state-disabled")}break;case"text":if(value){var textobj=this.element.find(".button-text");this.options.text=value;textobj.html(value)}break;case"iconurl":if(value==""){this.element.find("img").parent().addClass("button-image-hide")}else{this.element.find("img").parent().removeClass("button-image-hide")}this.element.find("img").attr("src",value)}this._super(key,value)},disable:function(){this._setOption("disabled",true)},enable:function(){this._setOption("disabled",false)},hide:function(){this.container.hide();if(this.menu&&this.menu.is(":visible")){this.menu.hide()}},show:function(){this.container.show()},highlight:function(){if(!this.options.disabled){if(this.options.toggleiconurl){this.element.find("img").attr("src",this.options.toggleiconurl)}this.container.addClass("jazz-button-pressed")}},unhighlight:function(){if(!this.options.disabled){if(this.options.toggleiconurl){this.element.find("img").attr("src",this.options.iconurl)}this.container.removeClass("jazz-button-pressed")}},triggerClick:function(){this.mainButton.trigger("mousedown.button");this.mainButton.trigger("mouseup.button")}})});