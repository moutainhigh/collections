(function($){
   /**********************silder*****************************/
   for(i in document.images)document.images[i].ondragstart=function(){return false;};	
	$.fn.tzSlider = function(options){
		var opts = $.extend({},$.fn.tzSlider.methods,$.fn.tzSlider.defaults,options);
		$("body").data("opts",opts);
		this.each(function(){
			var optss = $.extend({},$("body").data("opts"),$.fn.tzSlider.optionAttr($(this)));
			opts.init($(this),optss);
		});
	};

	$.fn.tzSlider.methods = {

		init : function($slider,opts){
			this.template($slider,opts);
			//参数初始化
			this.params($slider,opts);
			//事件的初始化
			this.events($slider,opts);
		},

		template:function($slider,opts){
			if(opts.horizontal){
				$slider.addClass("tzui-slider tzui-slider-uhorizontal");
				if(opts.height)$slider.height(opts.height);
			}else{
				$slider.addClass("tzui-slider tzui-slider-horizontal");
				if(opts.width)$slider.width(opts.width);
			}
			if(opts.bgcolor){
				$slider.append("<div class='tzui-slider-range'></div>");
			}
			$slider.append("<a class='tzui-slider-handle' draggable='false' href='javascript:void(0);'></a>");
		},

		params : function($slider,opts){
			if(!opts.horizontal){
				var maxWidth = $slider.width();
				$slider.find(".tzui-slider-handle").css("left",(maxWidth * opts.percent)/100);
				if(opts.bgcolor)$slider.find(".tzui-slider-range").width(opts.percent+"%");
			}

			if(opts.horizontal){
				var maxHeight = $slider.height();
				$slider.find(".tzui-slider-handle").css("top",(maxHeight * opts.percent)/100);
				if(opts.bgcolor)$slider.find(".tzui-slider-range").height(opts.percent+"%");
			}
			
			if(!opts.silderColor){
				if(opts.randomcolor && opts.bgcolor){
					$slider.find(".tzui-slider-range").css("background",tzUtil.getRandomColor());
				}
			}else{
				$slider.find(".tzui-slider-range").css("background","#"+opts.silderColor);
			}
			
			if(opts.load)opts.load($slider,opts.percent);
		},

		silderX:function($this,e){
			var x= e.clientX;//获取当前鼠标按下去的x轴坐标，
			//拿滑块的距离
			var left = $this.offset().left;
			var nleft = x-left;
			var maxWidth = $this.width();
			//赋予滑块的新的left
			$this.find(".tzui-slider-handle").css("left",nleft);
			//计算百分比
			var percent =  Math.ceil((nleft/maxWidth)*100);
			$this.find(".tzui-slider-range").width(percent+"%");
		},

		silderY:function($this,e){
			var y= e.clientY;//获取当前鼠标按下去的x轴坐标，
			//拿滑块的距离
			var top = $this.offset().top;
			var ntop = y-top;
			var maxHeight = $this.height();
			//赋予滑块的新的left
			$this.find(".tzui-slider-handle").css("top",ntop);
			//计算百分比
			var percent =  Math.ceil((ntop/maxHeight)*100);
			$this.find(".tzui-slider-range").height(percent+"%");
		},

		events : function($slider,opts){
			var flag = false;
			//滑块事件绑定
			var obj = this;
			$slider.mousedown(function(e){
				if(!opts.horizontal)obj.silderX($(this),e);
				if(opts.horizontal)obj.silderY($(this),e);
			});

			//滑块拖动事件绑定
			$slider.find(".tzui-slider-handle").on("mousedown",function(e){
				flag = true;
				var $this = $(this);
				var x = e.clientX;//获取当前鼠标按下去的x轴坐标，
				var y = e.clientY;//获取当前鼠标按下去的y轴坐标，
				var left = $this.position().left;//获取元素的绝对位置left
				var top = $this.position().top;//获取元素的绝对位置top
				var maxWidth = $this.parent().width();//获取滑块宽度
				var maxHeight = $this.parent().height();//获取滑块高度
				var sliderWidth = $this.width();//获取小滑块的宽度
				var sliderHeight= $this.height();//获取小滑块的高度
				var percent = 0;
				var horizontalMark = opts.horizontal;
				$(document).on("mousemove",function(ev){
					if(flag){
						if(!horizontalMark){
							var nx = ev.clientX;
							var nleft = nx+left-x+(sliderWidth/2)-4 ;
							if(nleft<=0)nleft=0;
							if(nleft > maxWidth)nleft = maxWidth;
							$this.css({left:nleft});
							percent =  Math.ceil((nleft/maxWidth)*100);
							$slider.find(".tzui-slider-range").width(percent+"%");
						}else{
							var ny = ev.clientY;
							var ntop = ny+top-y+(sliderHeight/2)-4 ;
							if(ntop<=0)ntop=0;
							if(ntop > maxHeight)ntop = maxHeight;
							$this.css({top:ntop});
							percent =  Math.ceil((ntop/maxHeight)*100);
							$slider.find(".tzui-slider-range").height(percent+"%");
						}
					}
				}).on("mouseup",function(){
					flag = false;
					if(opts.callback)opts.callback($slider,percent);
				});
			}).on("mouseup",function(){
				flag = false;	
			});
		}
	};

	$.fn.tzSlider.optionAttr = function($slider){
		return {
			width:$slider.data("width"),
			height:$slider.data("height"),
			percent:$slider.data("percent"),
			bgcolor:$slider.data("bgcolor"),
			silderColor:$slider.data("sildercolor"),
			horizontal:	$slider.data("horizontal")
		};
	};

	$.fn.tzSlider.defaults = {
		bgcolor :true,
		percent:20,
		width:0,
		height:0,
		horizontal:true,
		randomcolor:true,
		silderColor:"",
		load:function($silder,percent){

		},
		callback:function($silder,percent){
		
		}
	};



	/********************tab***************************/
	$.fn.tzTab = function(options){
		var opts = $.extend({},$.fn.tzTab.methods,$.fn.tzTab.defaults,options);
		this.each(function(){
			var optss = $.extend({},opts,$.fn.tzTab.parseOptions($(this)));
			opts.init($(this),optss);
		});
	};

	$.fn.tzTab.methods = {
		init : function($tab,opts){
			$tab.addClass("tzui-tabs");
			var $ul = $("<ul class='tzui-tabs-nav'></ul>");
			var liHtml = "";
			var contentHtml = "";
			var jdata = opts.tabDatas;
			var length = jdata.length;
			var className = "tzui-state-default";
			
			if(opts.index==0 || opts.index>length)opts.index=0;//边界判断
			for(var i=0;i<length;i++){
				var classStyle = "display:none";
				if(opts.index == i){
					className="tzui-tabs-active tzui-state-active";
					classStyle="";
				}
				liHtml+= "<li tab='tab-"+i+"' data-url='"+jdata[i].url+"' class='"+className+"'><a href='javascript:void(0)' class='tzui-tabs-anchor'>"+jdata[i].title+"</a></li>";
				contentHtml+="<div id='tab-"+i+"' class='tzui-tabs-panel' style='"+classStyle+"'>"+jdata[i].content+"</div>";
				className = "";					
			}
			$ul.html(liHtml);
			$tab.append($ul);
			//面板内容
			$tab.append(contentHtml);
			if(opts.width)$tab.width(opts.width);
			if(opts.height)$tab.height(opts.height);
			if(opts.background){
				$tab.css("border","2px solid "+opts.background);
				$ul.css({"background":opts.background});
				$ul.find("li>a").filter(function(){
					if(!$(this).parent().hasClass("tzui-state-active")){
						$(this).css({"background":opts.background,"color":"#fff"});
					}
				});
			}

			if(opts.linear && opts.border){
				$tab.css("border","2px solid "+opts.border);
				$ul.css({"background":"linear-gradient("+opts.linear+")"});
				$ul.find("li>a").filter(function(){
					if(!$(this).parent().hasClass("tzui-state-active")){
						$(this).css({"background":"linear-gradient("+opts.linear+")","color":"#fff"});
					}
				});
			}

			$tab.find(".tzui-tabs-nav > li").on(opts.event,function(){
				var markFlag = $(this).hasClass("tzui-tabs-active");
				if(markFlag)return;
				var tab = $(this).attr("tab");
				$(this).addClass("tzui-tabs-active tzui-state-active").siblings().removeClass("tzui-tabs-active tzui-state-active");
				$tab.find(".tzui-tabs-panel").hide();
				var $content = $tab.find("#"+tab);
				$content.show();
				$(this).find("a").removeAttr("style");
				if(opts.linear && opts.border){
					$(this).siblings().find("a").css({"background":"linear-gradient("+opts.linear+")","color":"#fff"});
				}else{
					$(this).siblings().find("a").css({"background":opts.background,"color":"#fff"});
				}
				if(opts.callback)opts.callback($(this),$content);
				//当前元素解绑事件
			});

			
		}
	};

	$.fn.tzTab.parseOptions = function($target){
		
		var datas = $target.find(".data").text();
		var json = {
			width:$target.data("width"),//选项卡的宽度
			height:$target.data("height"),//选项卡的高度
			event:$target.data("event"),//选项卡的事件类型
			background:$target.data("background"),
			linear:$target.data("linear"),
			border:$target.data("border"),
			index:$target.data("index")//默认选择哪一个
		};
		if(datas)json["tabDatas"]=eval("("+datas+")");
		return json;
	};

	$.fn.tzTab.defaults = {
		width:600,//选项卡的宽度
		height:300,//选项卡的高度
		event:"click",//选项卡的事件类型
		background:"#4684b2",
		linear:"",
		border:"",
		index:2,//默认选择哪一个
		callback:function($current,$content){
			
		},
		tabDatas:[
			{title:"选项卡1",content:"士大夫收到111","url":"user.action"},
			{title:"选项卡2",content:"士大夫收到22222","url":"delete.action"},
			{title:"选项卡3",content:"选项卡3333"}
		]
	};


	/**********************drag***********************/
	$.fn.tzDrag = function(options){
		var opts = $.extend({},$.fn.tzDrag.methods,$.fn.tzDrag.defaults,options);
		this.each(function(){
			var optss = $.extend({},opts,$.fn.tzDrag.parseOptions($(this)));
			opts.init($(this),optss);	
		});
	};

	$.fn.tzDrag.methods = {
		init:function($dialog,opts){//层拖动
			var thisObj = this;
			var mark = false;
//			$dialog.css("position","absolute");
			var $dialogClone  = null;
			$dialog.on("mousedown",opts.handler,function(e){
				//镜像
				tzUtil.forbiddenSelect();
				if(opts.ghost)$dialogClone= thisObj.ghsot($dialog,opts);
				var $this = $(this).parent();

				if(!opts.handler){
					$this=$(this);
				}

				var x = e.clientX;
				var y = e.clientY;
				var left = $this.offset().left;
				var top = $this.offset().top;
				var w = $this.width();
				var h = $this.height();
				var offsetHeight = $this.parent().height() - h-2;
				//var offsetHeight = $(document).height() - h-2;
				var offsetWidth= $this.parent(). width() - w-2;
				var stop= $(window).scrollTop();
				var jsonData = {};
				jsonData.width=w;
				jsonData.height=h;
				mark = true;
				$(document).on("mousemove",function(e){
					if(mark){
						var nx = e.clientX;
						var ny = e.clientY;
						var nl = nx + left - x;
						var nt = ny + top - y - stop;
						if(nl<=0)nl=1;
						if(nt<=0)nt=1;
						if(nl>=offsetWidth)nl = offsetWidth;
						if(nt>=offsetHeight)nt = offsetHeight;
						jsonData.left = nl;
						jsonData.top = nt;
						if(opts.ghost){
							$dialogClone.css({left:nl,top:nt});	
						}else{
							$this.css({left:nl,top:nt});	
						}
						if(opts.move)opts.move(jsonData,$this);
					}

				}).on("mouseup",function(){
					if(opts.ghost){
						$dialogClone.remove();
						$this.css({left:jsonData.left,top:jsonData.top});	
					}
					tzUtil.autoSelect();
					if(opts.up)opts.up($this);
					mark = false;
				});
			});
		},
		ghsot:function($dialog,opts){
			var $dialogClone = $dialog.clone();
			$dialogClone.css({"background":"#f9f9f9","opacity":0.5,"border":"1px dotted #ccc"}).find(".tzui-empty").empty();
			$("body").append($dialogClone);
			return $dialogClone;
		},	


	   };

	   $.fn.tzDrag.parseOptions = function($dialog){
			return {
				handler:$dialog.data("handler"),
				ghost:$dialog.data("ghost")
			};
	   };

	   $.fn.tzDrag.defaults = {
			handler:"",//拖动代理
			ghost:true,//是否产生镜像
			move:function(opts){
			},
			up:function(opts){
			}

	   };

})(jQuery);




//jquery插件的定义方式
var tzLoading = function(message,options){
	var opts = $.extend({},options);
	this.init(message,opts.timeout,opts);
};

tzLoading.prototype = {
	init : function(message,timeout,opts){
		var $loading = this.template(message,opts);
		//定位
		tzUtil._position($loading).resize($loading);
		//事件绑定
		this.events($loading);
		//时间关闭
		this.timeout($loading,timeout);
	},
	
	template:function(content,opts){
		var $loading = $("#tzloading");
		if($loading.length==0){
			if(content=="remove"){
				tzUtil.animates($loading,"slideUp");
				return;
			}
			$loading = $("<div id='tzloading' class='tzui-loading'></div>");
			var $loadingGif = $("<div class='tzui-loading-gif'></div>");
			var $loadingContent = $("<div class='tzui-loading-cnt'></div>");
			$loadingContent.html(content);
			$loading.append($loadingGif).append($loadingContent);
			$("body").append($loading);
			if(opts.overlay){
				$("body").append("<div class='tzui-loading-overlay'></div>");
				$loading.next().click(function(){
					$(this).remove();
					$loading.trigger("click");
				});
			}
		}else{
			if(content=="remove"){
				tzUtil.animates($loading,"slideUp");
				return;
			}
			$loading.find(".tzui-loading-cnt").html(content);
		}
		
		
		if(opts.height)$loading.height(opts.height);
		return $loading;
	},

	events :function($loading){
		$loading.click(function(){
			tzUtil.animates($(this),"slideUp");
		});
	},

	timeout:function($loading,timeout){
		var timr = null;
		if(timeout+"".isEmpty() && timeout >0){
			clearTimeout(timr);
			timr = setTimeout(function(){
				//事件的触发
				$loading.trigger("click");
			},timeout*500);
		}
	}
};

var  loading = function(message,timeout,overlay){
	new tzLoading(message,{"timeout":timeout,overlay:overlay});
};

/*******************dialog**********************************/
//dialog的弹出层
$.tzConfirm = function(options){
	var opts = $.extend({},$.tzDialog.methods,$.tzDialog.defaults,options);
	opts.init(opts);
};

$.tzAlert = function(options){
	var opts = $.extend({},$.tzDialog.methods,$.tzDialog.defaults,options);
	opts.init(opts);
	//var $dialog = opts.init(opts);
//	$dialog.find(".tzdialog_cancel").remove();

};

$.tzDialog = {};

$.tzDialog.methods = {
	//初始化
	init:function(opts){
		var $dialog = this.template(opts);
		//弹出层事件初始化
		this.events($dialog,opts);
		this.params($dialog,opts);
		var btns = opts.buttons;
		for(var key in btns){
			$dialog.append("<a class='btns' href='javascript:void(0);'>"+key+"</a>&nbsp;&nbsp;");
		}
		$dialog.find("a.btns").click(function(){
			var text = $(this).text();
			btns[text].call($dialog);
		});	
		
		return $dialog;
	},
	params:function($dialog,opts){
		if(opts.width)$dialog.width(opts.width);
		if(opts.height){
			if(opts.height<=180)opts.height=180;
			$dialog.height(opts.height);
		}
		$dialog.find(".tzdialog_message").css({"textAlign":"center","lineHeight":opts.height-130+"px"}).height(opts.height-130);
		//弹出层居中
		tzUtil._position($dialog);	
		//拖动事件的绑定
		if(opts.drag)$dialog.tzDrag({handler:".tzdialog_title","ghost":opts.ghost});
	},
	
	//弹出层的模板
	template : function(opts){
		var $dialog = $("<div class='tzui-diaolog'>"+
		"		<h1 class='tzdialog_title'>"+opts.title+"</h1>" +
		"		<div class='tzdialog_content tzui-empty'>"+
		"			<div class='tzdialog_message'>"+opts.content+"</div>"+
		"			<div class='tzdialog_panel'>"+
		"				<input type='button' value='&nbsp;"+opts.sureText+"&nbsp;' class='tzdialog_ok'> "+
		"				<input type='button' value='&nbsp;"+opts.cancelText+"&nbsp;' class='tzdialog_cancel'>"+
		"			</div>"+
		"		</div>"+
		"	</div>");
		$("body").append($dialog).append("<div class='tmui-overlay'></div>");
		return $dialog;
	},
	events:function($dialog,opts){
		//这个确定按钮事件
		$dialog.find(".tzdialog_ok").on("click",function(){
			if(opts.callback)opts.callback(true);//回调方法
			tzUtil.animates($dialog.next(),opts.animate);
			tzUtil.animates($dialog,opts.animate);
		});

		//关闭按钮事件
		$dialog.find(".tzdialog_cancel").on("click",function(){
			if(opts.callback)opts.callback(false);//回调方法
			tzUtil.animates($dialog.next(),opts.animate);
			tzUtil.animates($dialog,opts.animate);
		});

		//响应事件
		var timer = null;
		$(window).resize(function(){
			clearTimeout(timer);
			timer = setTimeout(function(){tzUtil._position($dialog);},30);
		});
	}
};

//弹出层的默认参数
$.tzDialog.defaults = {
	width:320,
	handle:".tzdiaog_title",
	height:160,
	title:"标题",
	drag:true,
	ghost:true,
	animate:"top",
	cancelText:"取消",
	sureText:"确定",
	callback:function(ok){
	},
	content:"请输入内容.请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容.."
};










