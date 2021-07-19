(function($,factory){if(jazz.config.isUseRequireJS===true){define(["jquery","jazz.Toolbar","jazz.Loading"],factory)}else{factory($)}})(jQuery,function($){$.widget("jazz.imageviewer",$.jazz.container,{options:{imagesrc:null,width:400,height:300,buttons:null,min_width:120,min_height:90,iscandrag:true,isrotate:false,isshowtoolbar:true,isshowcontextmenu:false,isshowwatermark:true,maxzoom:1,watermarkurl:null},_create:function(){this._super();this.options.width=parseInt(this.options.width);this.options.height=parseInt(this.options.height);this.toolbarWrap=$("<div class='jazz-imageview-toolbar'><div name='jazz-imageview-toolbar-"+(jazz.getRandom())+"'></div></div>").appendTo(this.element);if(!this.element.attr("id")){this.element.uniqueId()}if(!this.element.attr("name")){this.element.attr("name","jazz-imageviewer-"+jazz.getRandom())}this._setupContainer();this._initToolbar();this._initWatermark();this.loadImage(this.options.imagesrc);this._setupCSS();this._initContextMenu()},_init:function(){this._bindEvent()},_initToolbar:function(){var $this=this,btns;if(!$this.options.isshowtoolbar){return}btns=[{vtype:"button",align:"right",iconclass:"jazz-btn-zoomin",name:"jazz-imageview-button-zoomin",defaultview:0,click:function(){$this.zoom("in")}},{vtype:"button",align:"right",iconclass:"jazz-btn-zoomout",name:"jazz-imageview-button-zoomout",defaultview:0,click:function(){$this.zoom("out")}}];if($this.options.isrotate){btns=btns.concat([{vtype:"button",align:"right",text:"左转",iconclass:"jazz-imageviewer-rotate-left",defaultview:1,click:function(){$this._rotate(90)}},{vtype:"button",align:"right",text:"右转",iconclass:"jazz-imageviewer-rotate-right",defaultview:1,click:function(){$this._rotate(270)}}])}if(jazz.isArray(this.options.buttons)){btns=btns.concat(this.options.buttons)}$this.toolbarWrap.find(">div").toolbar({items:btns})},getToolbar:function(){if(this.options.isshowtoolbar){return this.toolbarWrap.find(">div")}},_initWatermark:function(){if(this.options.isshowwatermark){this.watermark=$("<div class='jazz-imageview-watermark'></div>").appendTo(this.element);this.addWatermark()}},_rotate:function(degree){var transform,styles=document.createElement("div").style,img=this.imgWrap[0],cosa,sina,matrix;$.each(["transform","MozTransform","webkitTransform","OTransform","msTransform"],function(i,t){if(t in styles){transform=t;return false}});this.imgWrap.data("degree")&&(degree+=this.imgWrap.data("degree"));degree=degree%360;this.imgWrap.data("degree",degree);cosa=Math.cos(degree*Math.PI/180);sina=Math.sin(degree*Math.PI/180);(degree==90||degree==270)&&(cosa=0);(degree==180)&&(sina=0);matrix={M11:cosa,M12:(-1*sina),M21:sina,M22:cosa};if(!transform){img.style.filter="progid:DXImageTransform.Microsoft.Matrix(M11="+matrix.M11+",M12="+(-1*matrix.M12)+",M21="+(-1*matrix.M21)+",M22="+matrix.M22+",SizingMethod='auto expand')"}else{img.style[transform]="matrix("+matrix.M11+", "+matrix.M12+", "+matrix.M21+", "+matrix.M22+", 0, 0)"}if(degree/90%2==1){this.trans=true}else{this.trans=false}this._checkRectCross()},_computeCenterPoint:function(){var img_width=this.imgWrap.width();var img_height=this.imgWrap.height();var ox=this.imgWrap.offset().left;var oy=this.imgWrap.offset().top;this.centerpoint_x=ox+img_width/2;this.centerpoint_y=oy+img_height/2},_checkRectCross:function(){var w=this.imgWrap.width();var h=this.imgWrap.height();var x=this.imgWrap.offset().left;var y=this.imgWrap.offset().top;var canvas_width=this.element.width();var canvas_height=this.element.height();var rect1={minx1:0,miny1:0,maxx1:canvas_width,maxy1:canvas_height};var rect2={minx2:x,miny2:y,maxx2:x+w,maxy2:y+h};var minx=Math.max(rect1.minx1,rect2.minx2);var miny=Math.max(rect1.miny1,rect2.miny2);var maxx=Math.min(rect1.maxx1,rect2.maxx2);var maxy=Math.min(rect1.maxy1,rect2.maxy2);if((minx===0)||((minx>maxx)||(miny>maxy))){var w2=canvas_width/2;var h2=canvas_width/2;this.imgWrap.css({left:w2-w/2,top:h2-h/2})}},_setupContainer:function(){this.element.addClass("jazz-imageview-wrap");this.element.css({width:(this.options.width+"px"),height:(this.options.height+"px")});this.toolbarWrap.css({width:(this.options.width+"px")})},loadImage:function(imgSrc){var loading=this.element.loading({text:"正在加载图片..."});if(this.imgWrap){this.imgWrap.removeClass();this.imgWrap.addClass("jazz-imageview-image jazz-imageview-minsize");this.imgWrap.find("img").attr("src",imgSrc)}else{this.imgWrap=$("<image src='"+imgSrc+"' alt='' class='jazz-imageview-image jazz-imageview-minsize' />").appendTo(this.element).wrap("<a class='jazz-helper-link' href='javascript:;'></a>")}this.imgWrap.load(function(){var width=$(this).width(),height=$(this).height(),$wrap=$(this).parents(".jazz-imageview-wrap"),wrapWidth=$wrap.width(),wrapHeight=$wrap.height(),minWidth=wrapWidth,minHeight=wrapHeight;$(this).data("size",{width:width,height:height});if(width>=height){minHeight=Math.floor(wrapWidth*height/width)}else{minWidth=Math.floor(wrapHeight*width/height)}if(width<=wrapWidth&&height<=wrapHeight){minWidth=width;minHeight=height;$wrap.data("maxZoom",(wrapWidth/width).toFixed(1))}$(this).data("minSize",{width:minWidth,height:minHeight});$(this).css({width:minWidth,height:minHeight});$wrap.data("minZoom",(minWidth/width).toFixed(1));if(minWidth<wrapWidth){$(this).css({left:(wrapWidth-minWidth)/2+"px"})}if(minHeight<wrapHeight){$(this).css({top:(wrapHeight-minHeight)/2+"px"})}$wrap.data("minPos",{left:(wrapWidth-minWidth)/2+"px",top:(wrapHeight-minHeight)/2+"px"});$wrap.loading("hide")})},_setupCSS:function(){this.imgWrap.css({position:"absolute",top:0,left:0});if(this.options.iscandrag){this.imgWrap.css({cursor:"move"})}},_bindEvent:function(){var $this=this,recoupLeft=0,recoupTop=0;if(this.options.iscandrag){this.imgWrap.draggable({start:function(e,ui){var left=parseInt($(this).css("left"),10),top=parseInt($(this).css("top"),10);left=isNaN(left)?0:left;top=isNaN(top)?0:top;recoupLeft=left-ui.position.left;recoupTop=top-ui.position.top;$this._hideContextMenu();$this.imgWrap.data("beforeOffset",$this.imgWrap.offset()).addClass("jazz-helper-draging")},drag:function(e,ui){ui.position.left+=recoupLeft;ui.position.top+=recoupTop},stop:function(){$this._checkPosition();$this.imgWrap.removeClass("jazz-helper-draging").addClass("jazz-helper-draged")}});if(this.options.isshowwatermark){}}this.element.off("dblclick.viewer").on("dblclick.viewer",function(e){var zoomSize=$this._calZoomSize();if($this.imgWrap.hasClass("jazz-helper-draging")){return}if($this._isInRange(e.pageX,e.pageY,"toolbar")){return}if($this.imgWrap.hasClass("jazz-imageview-minsize")){$this._setImgSize(zoomSize.max,$this.imgWrap.data("trans"));$this.imgWrap.removeClass("jazz-imageview-minsize")}else{$this._setImgSize(zoomSize.min,$this.imgWrap.data("trans"));$this.imgWrap.addClass("jazz-imageview-minsize");$this.imgWrap.offset($this.element.data("minPos"))}$this._checkPosition();$this._adjustImgPos();$this._hideContextMenu()});this.imgWrap.off("mousewheel.viewer DOMMouseScroll.viewer").on("mousewheel.viewer DOMMouseScroll.viewer",function(e){$this._onMouseWheel(e)});this._bindContextMenuEvent();if(!$this.options.isshowtoolbar){return}this.element.find(".jazz-helper-link").hover(function(){$this.toolbarWrap.show()},function(){});this.element.hover(function(){$this.toolbarWrap.show()});this.toolbarWrap.hover(function(){$this.toolbarWrap.show()})},_checkPosition:function(){var transform,styles=document.createElement("div").style;$.each(["transform","MozTransform","webkitTransform","OTransform","msTransform"],function(i,t){if(t in styles){transform=t;return false}});var wrapOffset=this.wrapPos||(this.wrapPos=this.element.offset()),imgOffset=this.imgWrap.offset(),imgSize=this._getImgSize(),wrapWidth=this.options.width,wrapHeight=this.options.height,vwidth=this.trans?"height":"width",vheight=this.trans?"width":"height";if(wrapWidth>=imgSize[vwidth]){if(wrapOffset.left>imgOffset.left){this.imgWrap.offset({left:this.imgWrap.data("beforeOffset")["left"]})}}else{if(!transform){vwidth="width",vheight="height"}if(imgSize[vwidth]+imgOffset.left<wrapOffset.left+wrapWidth){this.imgWrap.offset({left:(wrapWidth-imgSize[vwidth]+wrapOffset.left)})}}if(wrapWidth>=imgSize[vwidth]){if(imgOffset.left+imgSize[vwidth]>wrapOffset.left+wrapWidth){this.imgWrap.offset({left:this.imgWrap.data("beforeOffset")["left"]})}}else{if(imgOffset.left>wrapOffset.left){this.imgWrap.offset({left:wrapOffset.left})}}if(wrapHeight>=imgSize[vheight]){if(wrapOffset.top>imgOffset.top){this.imgWrap.offset({top:this.imgWrap.data("beforeOffset")["top"]})}}else{if(!transform){vwidth="width",vheight="height"}if(imgSize[vheight]+imgOffset.top<wrapHeight+wrapOffset.top){this.imgWrap.offset({top:(wrapHeight+wrapOffset.top-imgSize[vheight])})}}if(wrapHeight>=imgSize[vheight]){if(imgOffset.top+imgSize[vheight]>wrapOffset.top+wrapHeight){this.imgWrap.offset({top:this.imgWrap.data("beforeOffset")["top"]})}}else{if(imgOffset.top>wrapOffset.top){this.imgWrap.offset({top:wrapOffset.top})}}},_updatePosition:function(x,y){return;var wrapOffset,imgOffset,imgSize,oldImgSize,wrapWidth=this.options.width,wrapHeight=this.options.height,offsetX,offsetY,wrapCenter;if(this.imgWrap.hasClass("jazz-imageview-minsize")){this.imgWrap.css(this.element.data("minPos"));return}imgOffset=this.imgWrap.offset();wrapOffset=this.element.offset();imgSize=this._getImgSize(),oldImgSize=this.element.data("oldImgSize");wrapCenter=this.wrapCenter||(this.wrapCenter={x:(wrapOffset.left+wrapWidth/2),y:(wrapOffset.top+wrapHeight/2)});var rX=Math.floor((x-imgOffset.left)/oldImgSize.width*imgSize.width+imgOffset.left),rY=Math.floor((y-imgOffset.top)/oldImgSize.height*imgSize.height+imgOffset.top);offsetX=imgOffset.left+wrapCenter.x-rX;offsetY=imgOffset.top+wrapCenter.y-rX;this.imgWrap.offset({top:offsetX,left:offsetY})},zoom:function(type){type=type||"in";var zoomStep=1,zoom_step=this.zoom_step?this.zoom_step:(this.zoom_step=jazz.config.zoomStep),oldImgSize;if(!this._isZoom(type)){return false}this.imgWrap.data("beforeOffset",this.imgWrap.offset());type=="in"?(zoomStep+=zoom_step):(zoomStep-=zoom_step);oldImgSize=this._getImgSize();this.element.data("oldImgSize",oldImgSize);var imgSize={width:Math.ceil(oldImgSize.width*zoomStep),height:Math.ceil(oldImgSize.height*zoomStep)};this._setImgSize(imgSize);this._checkPosition();this._adjustImgPos()},_calZoomSize:function(){var imgSize,maxZoom=this.element.data("maxZoom")||jazz.config.maxZoom||1,minSize=this.imgWrap.data("minSize");if(this.zoomSize){return this.zoomSize}imgSize=this.originalSize||(this.originalSize=this.imgWrap.data("size"));this.zoomSize={max:{width:Math.ceil(imgSize.width*maxZoom),height:Math.ceil(imgSize.height*maxZoom)},min:{width:minSize.width,height:minSize.height}};return this.zoomSize},_isZoom:function(vtype){var zoomSize,imgSize,vwidth="width",vheight="height",trans=this.trans;zoomSize=this._calZoomSize();imgSize=this._getImgSize();if(trans){vwidth="height";vheight="width"}if(vtype==="in"&&(imgSize[vwidth]>=zoomSize.max["width"]||imgSize[vheight]>=zoomSize.max["height"])){return false}if(vtype==="out"&&(imgSize[vwidth]<=zoomSize.min["width"]||imgSize[vheight]<=zoomSize.min["height"])){this.imgWrap.addClass("jazz-imageview-minsize");this.imgWrap.css(this.element.data("minPos"));return false}this.imgWrap.removeClass("jazz-imageview-minsize");return true},_isInRange:function(x,y,who){var $el,startPoint,endPointX,endPointY;try{$el=this["get"+who.replace(/^\w/,function(m){return m.toUpperCase()})]()}catch(e){jazz.log(e);return false}startPoint=this[who+"Offset"]||(this[who+"Offset"]=$el.offset());endPointX=startPoint.left+$el.width();endPointY=startPoint.top+$el.height();if(x>=startPoint.left&&y>=startPoint.top&&x<=endPointX&&y<=endPointY){return true}return false},getImage:function(){return this.imgWrap},_getImgSize:function(){return{width:this.imgWrap.outerWidth(),height:this.imgWrap.outerHeight()}},_setImgSize:function(imgSize,trans){var minSize=this.imgWrap.data("minSize");imgSize=imgSize||this.originalSize||(this.originalSize=this.imgWrap.data("size"));this.imgWrap.height(imgSize.height).width(imgSize.width)},_adjustImgPos:function(){var imgSize=this._getImgSize(),wrapWidth=this.options.width,wrapHeight=this.options.height,trans=this.trans,vheight=trans?"width":"height",vwidth=trans?"height":"width";if(imgSize[vwidth]<=wrapWidth){this.imgWrap.css({left:(wrapWidth-imgSize[vwidth])/2+"px"})}if(imgSize[vheight]<=wrapHeight){this.imgWrap.css({top:(wrapHeight-imgSize[vheight])/2+"px"})}},_onMouseWheel:function(e){var $this=this,imgOffset=$this.imgWrap.offset(),delta,zoomFlag;e=e||window.event;e.cancelBubble=true;if(e.stopPropagation){e.stopPropagation()}if(e.preventDefault){e.preventDefault()}if(e.originalEvent){delta=e.originalEvent.detail||e.originalEvent["wheelDelta"];if(delta>0){zoomFlag=$this.zoom("in")}else{zoomFlag=$this.zoom("out")}$this._hideContextMenu()}},_initContextMenu:function(){var $menu,items=[];if(!this.options.isshowcontextmenu){return}if($("div.jazz-imageview-contextmenu").length>0){this.contextMenu=$("div.jazz-imageview-contextmenu");return}$menu=$("<div class='jazz-imageview-contextmenu jazz-helper-hidden'><ul></ul></div>").appendTo($("body")).children("ul");items.push("<li><a href='javascript:;' class='jazz-imageview-zoomin'>放大</a></li>");items.push("<li><a href='javascript:;' class='jazz-imageview-zoomout'>缩小</a></li>");if(this.options.isrotate){items.push("<li><a href='javascript:;' class='jazz-imageview-rotateleft'>左转</a></li>");items.push("<li><a href='javascript:;' class='jazz-imageview-rotateright'>右转</a></li>")}items.push("<li><a href='javascript:;' class='jazz-imageview-cancelMenu'>取消</a></li>");$menu.append(items.join(""));this.contextMenu=$menu.parent()},_showContextMenu:function(x,y){this.contextMenu.offset({left:x,top:y}).show()},_hideContextMenu:function(){$("div.jazz-imageview-dblclicked").removeClass("jazz-imageview-dblclicked");this.options.isshowcontextmenu&&this.contextMenu.offset({left:0,top:0}).hide()},_bindContextMenuEvent:function(){var $this=this;if(!this.options.isshowcontextmenu){return}this.imgWrap[0].oncontextmenu=function(e){e=e||window.event;e.cancelBubble=true;e.stopPropagation&&e.stopPropagation();event.preventDefault?event.preventDefault():event.returnValue=false;$(".jazz-imageview-dblclicked").removeClass("jazz-imageview-dblclicked");$this.element.addClass("jazz-imageview-dblclicked");$(".jazz-imageview-wrap").not(".jazz-imageview-dblclicked").find(".jazz-imageview-toolbar").hide();$this._showContextMenu(e.clientX,e.clientY)};if($._data(this.contextMenu[0],"events")){return}$("a.jazz-imageview-cancelMenu").off("click.viewer").on("click.viewer",function(){$this._hideContextMenu()});$("a.jazz-imageview-zoomin").off("click.viewer").on("click.viewer",function(){$(".jazz-imageview-dblclicked:first").imageviewer("zoom","in")});$("a.jazz-imageview-zoomout").off("click.viewer").on("click.viewer",function(){$(".jazz-imageview-dblclicked:first").imageviewer("zoom","out")})},reset:function(imgSrc){var imgSize,left=0,top=0;this._setImgSize();imgSize=this._getImgSize();if(imgSize.width<this.element.width()){left=(this.element.width()-imgSize.width)/2+"px"}if(imgSize.height<this.element.height()){top=(this.element.height()-imgSize.height)/2+"px"}this.imgWrap.css({top:top,left:left})},addButton:function(button){var btns=[];if(!jazz.isArray(button)){btns=[button]}else{btns=btns.concat(button)}this.toolbarWrap.find("> div").toolbar("addElement",btns)},addWatermark:function(src){src=src||this.options.watermarkurl||jazz.config.watermarkurl;if(!src){return}this.watermark.css({background:"url("+src+") right bottom no-repeat transparent"})},_setOption:function(key,value){switch(key){case"width":this.options.width=parseInt((value+"").replace(/px/ig,""));this._setupContainer();break;case"height":this.options.height=parseInt((value+"").replace(/px/ig,""));this._setupContainer();break;case"watermarkurl":this.options.watermarkurl=value;this.addWatermark(value);break;case"imagesrc":this.options.imagesrc=value;this.loadImage(value)}this._super(key,value)},destroy:function(){this.element.children().remove();this.element.remove();if($("div.jazz-imageview-wrap").length<=1){this.contextMenu.children().remove();this.contextMenu.remove()}this._super()}})});