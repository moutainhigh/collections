(function($,factory){if(jazz.config.isUseRequireJS===true){define(["jquery","jazz.BoxComponent"],factory)}else{factory($)}})(jQuery,function($){$.widget("jazz.tabpanel",$.jazz.boxComponent,{options:{vtype:"tabpanel",activeindex:0,showborder:true,overflowtype:2,tabtitlewidth:null,tabalign:"left",orientation:"top",showtabclose:true,change:null,close:null},_create:function(){this._super();this._showborder();var element=this.element;this.options.selected=this.options.activeindex;element.addClass("jazz-tabview jazz-widget jazz-hidden-container").addClass("jazz-tabview-"+this.options.orientation);this.navContainer=element.find("ul:first");this.panelContainer=element.find(">div:first");this.navContainer.find("li").addClass("jazz-tabview-default").css({width:this.options.tabtitlewidth}).eq(this.options.activeindex).addClass("jazz-tabview-selected jazz-state-active").css({"z-index":"3"});this.navContainer.find("li:last").after('<div class="jazz-clearfix"></div>').end().addClass("jazz-tabview-nav jazz-helper-reset jazz-helper-clearfix").wrap('<div class="jazz-tabview-header"></div>').wrap('<div class="jazz-tabview-wrap"></div>');this.navContainer.find("li:eq(0)").children().before("<div class ='nav-content-left'></div><div class ='nav-content-right-first'></div>");this.navContainer.find("li:gt(0)").children().before("<div class ='nav-content-left'></div><div class ='nav-content-right'><span class='jazz-icon-close nav-close'></span></div>");this.templeft=this.navContainer.find("li .nav-content-left").width(),this.tempright=this.navContainer.find("li .nav-content-right").width();this.tempwidth=this.templeft+this.tempright;if(this.templeft!=0){this.tempwidth=this.navContainer.find("li").width()-this.tempwidth;this.navContainer.find("li a ").css({width:this.tempwidth}).addClass("nav-content-middle")}this.panelContainer.addClass("jazz-tabview-panels").children().addClass("jazz-tabview-panel jazz-widget-content").end().find("div.jazz-tabview-panel:not(:eq("+this.options.activeindex+"))").css({"margin-left":"-3000px","z-index":"-1000"}).end();this.tabswrapContainer=element.find(".jazz-tabview-wrap");this.tabsHeader=element.find(".jazz-tabview-header");if((this.options.orientation=="top"||this.options.orientation=="bottom")){this.navContainer.css({margin:"0 2px 0 0"}).find("li").addClass("jazz-tabview-tabalign-"+this.options.tabalign)}this.isInner=false},_init:function(){this._compSize();this.tablefttitleheight=this.navContainer.find("li").outerHeight(true);this.tablefttitlewidth=this.navContainer.find("li").outerWidth(true);this.isorientation=true;this._tabsPositionInit();this._innerWidth();this._innerHeight();this.isInner=true;this._bindEvents()},_compSize:function(){this._width();this._height()},_showborder:function(){if(this.options.showborder){this.element.addClass("jazz-panel-border")}else{this.element.removeClass("jazz-panel-border")}},_width:function(){this._super();if(this.isInner){this._innerWidth()}},_innerWidth:function(){if((this.options.orientation=="top"||this.options.orientation=="bottom")){if(this.isInner){this.tabswrapContainer.width(this.options.calculateinnerwidth)}this.panelContainer.outerWidth(this.options.calculateinnerwidth)}else{this.tabsHeaderWidth=this.tabsHeader.outerWidth(true);var tabwidth=this.options.calculateinnerwidth-this.tabsHeaderWidth;if(jazz.isIE(7)||jazz.isIE(6)){tabwidth=tabwidth-5}this.panelContainer.outerWidth(tabwidth)}},_height:function(){this._super();if(this.isInner){this._innerHeight()}},_innerHeight:function(){this.tabsHeaderHeight=this.tabsHeader.outerHeight(true);if((this.options.orientation=="top"||this.options.orientation=="bottom")){if(this.options.calculateinnerheight!="-1"){this.panelContainer.height(this.options.calculateinnerheight-this.tabsHeaderHeight)}}else{if(this.isInner){this.tabsHeader.outerHeight(this.options.calculateinnerheight);this.tabswrapContainer.outerHeight(this.tabsHeaderHeight)}if(this.options.calculateinnerheight=="-1"){this.options.calculateinnerheight=this.tabsHeader.outerHeight(true)}this.tabsHeader.find(".jazz-tabview-header-"+this.options.orientation).outerHeight(this.tabsHeaderHeight);this.panelContainer.height(this.options.calculateinnerheight)}},_tabsPositionInit:function(){var that=this;if(that.options.orientation=="top"||that.options.orientation=="bottom"){if(that.options.orientation=="bottom"){if(this.isorientation){that.element.append(that.tabsHeader);that.tabsHeader.prepend("<div class='jazz-tabview-header-"+that.options.orientation+"'></div>");this.isorientation=false}}else{if(this.isorientation){that.tabsHeader.append("<div class='jazz-tabview-header-"+that.options.orientation+"'></div>");this.isorientation=false}}if(that.options.overflowtype==1){that.tabs_more_horizon=$('<div class="tabs-more-horizon"></div>').appendTo(that.tabsHeader);that._initSelectableTopAndBottom()}else{if(that.options.overflowtype==2){that._computeTabsHeader()}}}else{if(that.options.orientation=="left"||that.options.orientation=="right"){if(this.isorientation){if(this.iscalculateheight){that.tabsHeader.outerHeight(this.options.calculateinnerheight);that.tabswrapContainer.outerHeight(that.tabsHeader.height())}that.tabsHeader.prepend("<div class='jazz-tabview-header-"+that.options.orientation+"' style='height:"+this.tabsHeader.height()+"px;'></div>");that.tabsHeader.width(that.tabswrapContainer.find("li:first").width()+that.tabsHeader.find(".jazz-tabview-header-"+that.options.orientation).outerWidth(true));this.isorientation=false}if(that.options.overflowtype==1){that.tabs_more_vertical=$('<div class="tabs-more-vertical"></div>').appendTo(that.tabsHeader);that._initSelectableLeftAndRight()}else{if(that.options.overflowtype==2){that._computeTabsHeader()}}}}},_computeTabsHeader:function(){var that=this,orientation=that.options.orientation,tempTabsWidth=0,tempTabsHeight=0;if(orientation=="top"||orientation=="bottom"){tempTabsWidth=that.navContainer.outerWidth(true)-that.navContainer.outerWidth();that.navContainer.find("li:visible").each(function(i){tempTabsWidth+=$(this).outerWidth(true)});that.tabswrapContainer.scrollLeft(0);if(tempTabsWidth>that.tabswrapContainer.width()){if(!this._isResetMoreTab()){return}that.navContainer.addClass("jazz-tabview-wrap-more");!that.tabs_scroller_left&&(that.tabs_scroller_left=$('<a class="tabs-scroller-left"></a>').appendTo(that.tabsHeader).css({height:that.tabswrapContainer.height()+"px",display:"block"}));!that.tabs_scroller_right&&(that.tabs_scroller_right=$('<a class="tabs-scroller-right"></a>').appendTo(that.tabsHeader).css({height:that.tabswrapContainer.height()+"px",display:"block"}));if(that.options.tabalign=="right"){that.tabswrapContainer.scrollLeft(that.navContainer.outerWidth(true))}if(orientation=="bottom"){that.tabs_scroller_left.css({bottom:0,top:"auto"});that.tabs_scroller_right.css({bottom:0,top:"auto"})}var wrapwidth=that.tabswrapContainer.outerWidth(true)-32;that.tabswrapContainer.css({width:wrapwidth,margin:"0px 16px"})}else{if(that.tabs_scroller_left){that.tabs_scroller_left.remove();that.tabs_scroller_left=null}if(that.tabs_scroller_right){that.tabs_scroller_right.remove();that.tabs_scroller_right=null}if(that.options.tabalign=="right"){that.tabswrapContainer.scrollLeft(that.navContainer.outerWidth(true))}that.tabswrapContainer.css({margin:"0px"})}}else{if(that.options.orientation=="left"||this.options.orientation=="right"){that.navContainer.find("li:visible").each(function(i){tempTabsHeight+=$(this).outerHeight(true)});that.tabswrapContainer.scrollTop(0);if(tempTabsHeight>that.tabswrapContainer.height()){if(!this._isResetMoreTab()){return}!that.tabs_scroller_top&&(that.tabs_scroller_top=$('<a class="tabs-scroller-top"></a>').prependTo(that.tabsHeader));!that.tabs_scroller_bottom&&(that.tabs_scroller_bottom=$('<a class="tabs-scroller-bottom"></a>').appendTo(that.tabsHeader));that.navContainer.addClass("jazz-tabview-wrap-hmore");that.tabswrapContainer.height(that.tabsHeader.height()-that.tabs_scroller_top.height()-that.tabs_scroller_bottom.height()).width(that.options.tabtitlewidth||(that.tabsHeader.width()-that.tabs_more_horizon.width()));that.tabs_scroller_top.css({width:(that.options.tabtitlewidth.replace(/px/g,"")+"px"||that.tabsHeader.width()),display:"block"});that.tabs_scroller_bottom.css({width:(that.options.tabtitlewidth.replace(/px/g,"")+"px"||that.tabsHeader.width()),display:"block"})}else{if(that.tabs_scroller_top){that.tabs_scroller_top.remove();that.tabs_scroller_top=null}if(that.tabs_scroller_bottom){that.tabs_scroller_bottom.remove();that.tabs_scroller_bottom=null}that.tabswrapContainer.height(that.tabsHeader.height())}}}},_computeTabsHeaderMore:function(){var that=this;if(that.options.orientation=="top"||that.options.orientation=="bottom"){var tempTabsWidth=that.navContainer.outerWidth(true)-that.navContainer.outerWidth();that.navContainer.find("li:visible").each(function(i){tempTabsWidth+=$(this).outerWidth(true)});if(tempTabsWidth>=that.tabswrapContainer.width()){that.navContainer.width(5000+"px");that.tabswrapContainer.width(that.options.tabtitlewidth||(that.tabsHeader.width()-that.tabs_more_horizon.width()));that.tabs_more_horizon.css({height:that.tabswrapContainer.height()+"px",display:"inline-block","*display":"inline",zoom:"1"});if(that.tabsMoreDisplayDiv&&that.tabsMoreDisplayDiv.length>1){that.tabsMoreDisplayDiv.children().remove()}else{that.tabsMoreDisplayDiv=$('<div style="width:70px;position:absolute;display:none;"></div>').appendTo(document.body)}that.navContainer.clone().css("width","auto").appendTo(that.tabsMoreDisplayDiv);this._bindMoreHorizonEvents()}else{that.navContainer.css("width","auto");that.tabswrapContainer.width(that.tabsHeader.width());that.tabs_scroller_right.css({display:"none"})}}else{if(that.options.orientation=="left"||this.options.orientation=="right"){var tempTabsHeight=0;that.navContainer.find("li").each(function(i){if($(this).css("display")!="none"){tempTabsHeight+=$(this).outerHeight()}});if(tempTabsHeight>that.tabswrapContainer.height()){that.navContainer.height(5000+"px");that.tabswrapContainer.width(that.options.tabtitlewidth||(that.tabsHeader.width()-that.tabs_more_horizon.width()));that.tabs_more_vertical.css({width:that.tabsHeader.width(),display:"block"});if(that.tabsMoreDisplayDiv&&that.tabsMoreDisplayDiv.length>1){that.tabsMoreDisplayDiv.children().remove()}else{that.tabsMoreDisplayDiv=$('<div style="width:70px;position:absolute;display:none;"></div>').appendTo(document.body)}that.navContainer.clone().css("height","auto").appendTo(that.tabsMoreDisplayDiv);this._bindMoreVerticalEvents()}else{that.navContainer.css("height","auto");that.tabswrapContainer.height(that.tabsHeader.height());that.tabs_more_vertical.css("display","none")}}}},_isResetMoreTab:function(){if(this.tabsHeader.children('div[class^="tabs-scroller"]').length){return false}return true},_initSelectableTopAndBottom:function(){var that=this;var tempTabsWidth=that.navContainer.outerWidth(true)-that.navContainer.outerWidth();that.navContainer.find("li").each(function(i){if($(this).css("display")!="none"){tempTabsWidth+=$(this).outerWidth(true)}});if(tempTabsWidth>=that.tabswrapContainer.width()){that.navContainer.width(5000+"px");that.tabswrapContainer.width(that.tabsHeader.width()-that.tabs_more_horizon.width());that.tabs_more_horizon.css({height:that.tabswrapContainer.height()+"px",display:"inline-block","*display":"inline",zoom:"1"});that.tabsMoreDisplayDiv=$('<div style="width:70px;position:absolute;display:none;"></div>').appendTo(document.body);that.navContainer.clone().css("width","auto").appendTo(that.tabsMoreDisplayDiv);this._bindMoreHorizonEvents();if(that.options.tabalign=="right"){that.tabswrapContainer.scrollLeft(that.navContainer.outerWidth(true))}}else{that.navContainer.css("width","auto");that.tabswrapContainer.width(that.tabsHeader.width());that.tabs_scroller_right.css({display:"none"});if(that.options.tabalign=="right"){that.tabswrapContainer.scrollLeft(that.navContainer.outerWidth(true))}}},_initSelectableLeftAndRight:function(){var that=this;var tempTabsHeight=0;that.navContainer.find("li").each(function(i){if($(this).css("display")!="none"){tempTabsHeight+=$(this).outerHeight()}});if(tempTabsHeight>that.tabswrapContainer.height()){that.navContainer.height(5000+"px");that.tabswrapContainer.height(that.tabsHeader.height()-that.tabs_more_vertical.height());that.tabs_more_vertical.css({width:that.tabsHeader.width(),display:"block"});that.tabsMoreDisplayDiv=$('<div style="width:70px;position:absolute;display:none;"></div>').appendTo(document.body);that.navContainer.clone().css("height","auto").appendTo(that.tabsMoreDisplayDiv);this._bindMoreVerticalEvents()}else{that.navContainer.css("height","auto");that.tabswrapContainer.height(that.tabsHeader.height());that.tabs_more_vertical.css("display","none")}},_bindMoreHorizonEvents:function(){var that=this;that.tabs_more_horizon.off("click.more").on("click.more",function(){that.tabsMoreDisplayDiv.css({left:"",top:""}).position({my:"left top",at:"right top",of:that.tabs_more_horizon}).show()});that.tabsMoreDisplayDiv.find("li").off("click.morerli").on("click.moreli",function(e){var element=$(this);if($(e.target).is(":not(.jazz-icon-close)")){var index=element.index();if(!element.hasClass("jazz-state-disabled")&&index!=that.options.selected){that._selectMoreLi(index);that.select(index)}var el=that.navContainer.find("li:eq("+index+")");var tabsTotalWidth=$(el).outerWidth(true);$(el).prevAll().each(function(i){if($(this).css("display")!="none"){tabsTotalWidth+=$(this).outerWidth(true)}});var scrollTotalWidth=tabsTotalWidth-that.tabswrapContainer.width();if(scrollTotalWidth>0){that.tabswrapContainer.scrollLeft(scrollTotalWidth)}else{that.tabswrapContainer.scrollLeft(0)}}e.preventDefault()});this.tabsMoreDisplayDiv.find("li .jazz-icon-close").off("click.tab").on("click.tab",function(e){var index=$(this).parent().parent().index();$this.remove(index);e.preventDefault()});$(document).off("click.jazz-tabpanel-other").on("click.jazz-tabpanel-other",function(e){if($(that.tabsMoreDisplayDiv).is(":hidden")){return}var target=$(e.target);if(target.is($(that.tabsMoreDisplayDiv))||$(that.tabsMoreDisplayDiv).has(target).length>0){return}if(target.is(that.tabs_more_horizon)||that.tabs_more_horizon.has(target).length>0){return}var offset=$(that.tabsMoreDisplayDiv).offset();if(e.pageX<offset.left||e.pageX>offset.left+$(that.tabsMoreDisplayDiv).width()||e.pageY<offset.top||e.pageY>offset.top+$(that.tabsMoreDisplayDiv).height()){$(that.tabsMoreDisplayDiv).hide()}})},_bindMoreVerticalEvents:function(){var that=this;that.tabs_more_vertical.off("click.more").on("click.more",function(){that.tabsMoreDisplayDiv.css({left:"",top:""}).position({my:"left top",at:"right top",of:that.tabs_more_vertical}).show()});that.tabsMoreDisplayDiv.find("li").off("click.moreli").on("click.moreli",function(e){var element=$(this);if($(e.target).is(":not(.jazz-icon-close)")){var index=element.index();if(!element.hasClass("jazz-state-disabled")&&index!=that.options.selected){that._selectMoreLi(index);that.select(index)}var el=that.navContainer.find("li:eq("+index+")");var tabsTotalWidth=$(el).outerHeight(true);$(el).prevAll().each(function(i){if($(this).css("display")!="none"){tabsTotalWidth+=$(this).outerHeight(true)}});var scrollTotalWidth=tabsTotalWidth-that.tabswrapContainer.height();if(scrollTotalWidth>0){that.tabswrapContainer.scrollTop(scrollTotalWidth)}else{that.tabswrapContainer.scrollTop(0)}}e.preventDefault()});this.tabsMoreDisplayDiv.find("li .jazz-icon-close").off("click.tab").on("click.tab",function(e){var index=$(this).parent().parent().index();$this.remove(index);e.preventDefault()});$(document.body).bind("mousedown.jazz-tabpanel-other",function(e){if($(that.tabsMoreDisplayDiv).is(":hidden")){return}var target=$(e.target);if(target.is($(that.tabsMoreDisplayDiv))||$(that.tabsMoreDisplayDiv).has(target).length>0){return}if(target.is(that.tabs_more_vertical)||that.tabs_more_vertical.has(target).length>0){return}var offset=$(that.tabsMoreDisplayDiv).offset();if(e.pageX<offset.left||e.pageX>offset.left+$(that.tabsMoreDisplayDiv).width()||e.pageY<offset.top||e.pageY>offset.top+$(that.tabsMoreDisplayDiv).height()){$(that.tabsMoreDisplayDiv).hide()}})},_selectMoreLi:function(index){var headers=this.tabsMoreDisplayDiv.find("li"),oldHeader=headers.filter(".jazz-state-active"),newHeader=headers.eq(index);oldHeader.attr("aria-expanded",false);newHeader.attr("aria-expanded",true);oldHeader.removeClass("jazz-tabview-selected jazz-state-active");newHeader.removeClass("jazz-tabview-hover").addClass("jazz-tabview-selected jazz-state-active")},_bindTopAndBottomScrollEvent:function(){var that=this,scrollwidth=this.tablefttitlewidth;that.tabsHeader.off("click.left",".tabs-scroller-left").on("click.left",".tabs-scroller-left",function(){var scrollleft=that.tabswrapContainer.scrollLeft()+scrollwidth;if(that.options.tabalign=="right"){that.tabswrapContainer.scrollLeft(scrollleft)}else{var tabsTotalWidth=0;that.navContainer.children("li:visible").each(function(i){tabsTotalWidth+=$(this).outerWidth()});var scrollTotalWidth=tabsTotalWidth-that.tabswrapContainer.width();if(scrollleft>scrollTotalWidth){that.tabswrapContainer.scrollLeft(scrollTotalWidth+24)}else{that.tabswrapContainer.scrollLeft(scrollleft)}}});that.tabsHeader.off("click.right").on("click.right",".tabs-scroller-right",function(){var wrapcontainerScrollWidth=that.tabswrapContainer[0].scrollWidth;var scrollleft=that.tabswrapContainer.scrollLeft()-scrollwidth;if(that.options.tabalign=="right"){var tabsTotalWidth=0;that.navContainer.find("li:visible").each(function(i){tabsTotalWidth+=$(this).outerWidth()});if(that.tabswrapContainer.scrollLeft()<(wrapcontainerScrollWidth-tabsTotalWidth)){scrollleft=wrapcontainerScrollWidth-tabsTotalWidth-24}}that.tabswrapContainer.scrollLeft(scrollleft)})},_bindLeftAndRightScrollEvent:function(){var that=this,scrollheight=this.tablefttitleheight,singleLiHeight=0;that.tabsHeader.off("click.top").on("click.top",".tabs-scroller-top",function(){var tabsTotalHeight=0;that.navContainer.children("li:visible").each(function(i){tabsTotalHeight+=$(this).outerHeight(true);singleLiHeight=$(this).outerHeight(true)});var scrollTotalHeight=tabsTotalHeight-that.tabswrapContainer.height();var scrolltop=that.tabswrapContainer.scrollTop()+scrollheight;if(scrolltop>scrollTotalHeight){that.tabswrapContainer.scrollTop(scrollTotalHeight+singleLiHeight)}else{that.tabswrapContainer.scrollTop(scrolltop)}});that.tabsHeader.off("click.bottom").on("click.bottom",".tabs-scroller-bottom",function(){var scrolltop=that.tabswrapContainer.scrollTop()-scrollheight;that.tabswrapContainer.scrollTop(scrolltop)})},_bindEvents:function(){var $this=this;this.navContainer.off("click.tab","li").on("click.tab","li",function(e){var element=$(this);if($(e.target).is(":not(.jazz-icon-close)")){var index=element.index();if(!element.hasClass("jazz-state-disabled")&&index!=$this.options.selected){$this.select(index)}}e.preventDefault()});this.navContainer.off("click.tabclose","li .jazz-icon-close").on("click.tabclose","li .jazz-icon-close",function(e){var index=$(this).parent().parent().index();$this.remove(index);e.preventDefault()});if(this.options.orientation=="top"||this.options.orientation=="bottom"){this._bindTopAndBottomScrollEvent()}else{if(this.options.orientation=="left"||this.options.orientation=="right"){this._bindLeftAndRightScrollEvent()}}},select:function(index){this.options.selected=index;var newPanel=this.panelContainer.children().eq(index),headers=this.navContainer.children(),oldHeader=headers.filter(".jazz-state-active"),newHeader=headers.eq(newPanel.index()),oldPanel=this.panelContainer.children(".jazz-tabview-panel:visible"),$this=this;oldHeader.removeClass("jazz-tabview-selected jazz-state-active");oldPanel.css({"margin-left":"-3000px","z-index":"-1000"});newHeader.removeClass("jazz-tabview-hover").addClass("jazz-tabview-selected jazz-state-active");newPanel.css({"margin-left":"0px","z-index":"0"});this._event("change",null,index);var nowPosition=this._isOnScreen(index);if(nowPosition!=true){this.tabswrapContainer[nowPosition.direction](nowPosition.scrollNum)}},remove:function(index){if(index<0||index>this.getLength()){return false}var header=this.navContainer.children().eq(index),panel=this.panelContainer.children().eq(index);this._event("close",null,index);header.remove();panel.remove();if(index==this.options.selected){var newIndex=this.options.selected==this.getLength()?(this.options.selected-1):this.options.selected;this.select(newIndex)}if(index==this.options.activeindex){if(index==0){this.select(0)}else{if(index==this.getLength()){this.select(index-1)}}}else{this.select(this.options.activeindex)}if(this.options.overflowtype==2){this._computeTabsHeader()}else{if(this.options.overflowtype==1){this._computeTabsHeaderMore()}}},_beforeAddTab:function(taboption){var prefix="jazz_tabpanel_custom_";if(!taboption.tabid){jazz.warn("参数中未找到属性[tabid]");return false}if(!taboption.tabtitle){jazz.warn("参数中未找到属性[tabtitle]");return false}if(this.element.find("#"+prefix+taboption.tabid).length){jazz.warn("参数中未找到属性[tabtitle]");return false}return true},addTab:function(taboption){var $this=this,prefix="jazz_tabpanel_custom_",tabLength=$this.getLength(),index=taboption.tabindex?taboption.tabindex:(tabLength+1),tabli="<li class='jazz-tabview-default'><div class ='nav-content-left'></div><div class ='nav-content-right'><span class='jazz-icon-close nav-close'></span></div><a style='width:"+this.tempwidth+"px;' class='nav-content-middle' href='"+prefix+taboption.tabid+"'>"+taboption.tabtitle+"</a><span style='display:none;' class='jazz-icon jazz-icon-close'></span></li>",tabdiv="<div class='jazz-tabview-panel jazz-widget-content' style='margin-left:-3000px;' id='"+prefix+taboption.tabid+"'>"+(taboption.tabcontent||"")+"</div>";if(taboption.taburl){tabdiv="<div class='jazz-tabview-panel jazz-widget-content' style='margin-left:-3000px;' id='"+prefix+taboption.tabid+"'><iframe src='"+taboption.taburl+"' id='"+prefix+taboption.tabid+"_"+$this.options.frameName+"' name='"+prefix+taboption.tabid+"_iframepage' frameBorder='0' scrolling='yes' width='100%' height='100%'></iframe></div>"}index=index>(tabLength+1)?(tabLength+1):index;var tabIndex=index-1;if(!this._beforeAddTab(taboption)){return}if(index>tabLength){$this.navContainer.children(":last").before(tabli);$this.panelContainer.append(tabdiv)}else{$this.navContainer.children(":eq("+tabIndex+")").before(tabli);$this.panelContainer.children(":eq("+tabIndex+")").before(tabdiv)}if((this.options.orientation=="top"||this.options.orientation=="bottom")){this.navContainer.children(":eq("+(index-1)+")").addClass("jazz-tabview-tabalign-"+this.options.tabalign);this.panelContainer.children(":eq("+(index-1)+")").css({width:this.options.calculateinnerwidth,height:this.options.calculateinnerheight-this.tabsHeaderHeight})}else{this.panelContainer.children(":eq("+(index-1)+")").css({width:this.options.calculateinnerwidth-this.tabsHeaderWidth,height:this.options.calculateinnerheight})}this.navContainer.children(":eq("+tabIndex+")").css({width:this.options.tabtitlewidth});$this.select(tabIndex);if(this.options.overflowtype==2){this._computeTabsHeader()}else{if(this.options.overflowtype==1){this._computeTabsHeaderMore()}}},addTabCustom:function(index,tabid,tabname,taburl){if($("#"+tabid).length>0){var tabindex=0;this.panelContainer.children().each(function(i){var id=$(this).attr("id");if(tabid==id){tabindex=i}});this.select(tabindex)}else{var tablength=this.getLength();if(index>tablength){index=tablength-1}if(index<0){index=0}var tabli="<li><div class ='nav-content-left'></div><div class ='nav-content-right'><span class='jazz-icon-close nav-close'></span></div><a style='width:"+this.tempwidth+"px;' class='nav-content-middle' href='#"+tabid+"'>"+tabname+"</a></li>";var tabdiv="<div id='"+tabid+"'>"+tabname+"</div>";if(taburl!=null&&taburl!=""){tabdiv="<div id='"+tabid+"'><iframe src='"+taburl+"' id='"+this.options.frameName+"' name='iframepage' frameBorder='0' scrolling='yes' width='100%' height='100%'></iframe></div>"}this.navContainer.children(":eq("+index+")").before(tabli);this.panelContainer.children(":eq("+index+")").before(tabdiv);tabli=this.navContainer.find("li:eq("+index+")");tabdiv=this.panelContainer.find("div:eq("+index+")");this.navContainer.children(":not(:eq("+index+"))").removeClass("jazz-tabview-selected  jazz-state-active");this.panelContainer.children(":not(:eq("+index+"))").css({"margin-left":"-3000px","z-index":"-1000"});tabli.addClass("jazz-tabview-default jazz-tabview-selected jazz-state-active").css("width",this.options.tabtitlewidth);if((this.options.orientation=="top"||this.options.orientation=="bottom")){this.navContainer.children(":eq("+(index)+")").addClass("jazz-tabview-tabalign-"+this.options.tabalign);tabdiv.addClass("jazz-tabview-panel jazz-widget-content").css({width:this.options.calculateinnerwidth,height:this.options.calculateinnerheight-this.tabsHeaderHeight})}else{tabdiv.addClass("jazz-tabview-panel jazz-widget-content").css({width:this.options.calculateinnerwidth-this.tabsHeaderWidth,height:this.options.calculateinnerheight})}var $this=this;tabli.off("click.tab").on("click.tab",function(e){var element=$(this);if($(e.target).is(":not(.jazz-icon-close)")){var index=element.index();if(!element.hasClass("jazz-state-disabled")&&index!=$this.options.selected){$this.select(index)}}e.preventDefault()});this.select(tablength);this._computeTabsHeader()}},_isOnScreen:function(index){var win=this.tabsHeader,viewport={top:win.scrollTop(),left:win.scrollLeft()},ele=this.navContainer.children(":eq("+index+")"),eleHeight=ele.outerHeight(true),eleWidth=ele.outerWidth(true),orientation=this.options.orientation,tabalign=this.options.tabalign,scrollNum=0;viewport.right=viewport.left+win.width();viewport.bottom=viewport.top+win.height();var bounds=ele.offset();bounds.right=bounds.left+ele.outerWidth();bounds.bottom=bounds.top+ele.outerHeight();if(/(left|right)/i.test(orientation)){if(viewport.bottom<bounds.bottom){scrollNum=this.tabswrapContainer.scrollTop()+bounds.bottom-viewport.bottom;return{scrollNum:scrollNum,direction:"scrollTop"}}if(viewport.top>bounds.top){scrollNum=viewport.top;return{scrollNum:scrollNum,direction:"scrollTop"}}}if(/(top|bottom)/i.test(orientation)){if(viewport.left>bounds.left){scrollNum=viewport.left;if(tabalign=="right"){scrollNum=(this.tabswrapContainer.scrollLeft()-Math.abs(viewport.left-bounds.left))}return{scrollNum:scrollNum,direction:"scrollLeft"}}if(viewport.right<bounds.right){scrollNum=(this.tabswrapContainer.scrollLeft()+bounds.right-viewport.right);if(tabalign=="right"){scrollNum=(this.tabswrapContainer.scrollLeft()+(bounds.right-viewport.right))}return{scrollNum:scrollNum,direction:"scrollLeft"}}}return true},_setOption:function(key,value){this._super(key,value);if("width"==key||"height"==key){this._tabsPositionInit()}},_setOptions:function(options){this._super(options)},getLength:function(){return this.navContainer.children().length-1},getActiveIndex:function(){return this.options.selected},_markAsLoaded:function(panel){panel.data("loaded",true)},_isLoaded:function(panel){return panel.data("loaded")===true},disable:function(index){this.navContainer.children().eq(index).addClass("jazz-state-disabled");this.navContainer.children().eq(index).find(".jazz-icon-close").off("click.tab").on("click.tab",function(e){e.stopPropagation()})},enable:function(index){this.navContainer.children().eq(index).removeClass("jazz-state-disabled")}})});