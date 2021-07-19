/*
* 保存flash图形查看页面,引入JS，使用Ajax查询数据
* 
*/

Ext.ns("Flash.Data.Render");
Flash.Data.Render ={
	options : {
		flash_render_el : "",
		data_url : "",
		/*
		*  当请求flash中的数据，发送ajax时需要带上的参数
		*/
		ajax_parame :{}
	},
	init : function(opt){
		Ext.apply(this.options,opt);
		this.initDefault();
	},
	initDefault : function(){
		var nWidth = window.screen.width/1440*800;
		var nHeight = window.screen.height/900*370;
		// 设置父元素的大小，以便出现滚动条
		/*var parent = $(this.options.flash_render_el).parentNode;
		if(parent.tagName!="TD"){
			parent.style.width = nWidth +"px";
			//parent.style.height = nHeight+20+"px";
		}*/
		// 载入flash
		swfobject.embedSWF("../../open-flash-chart.swf", this.options.flash_render_el, nWidth, nHeight, "9.0.0","../../expressInstall.swf",{save_image_message:"保存为图片"});
		if($(this.options.flash_render_el).tagName!="OBJECT"){
			$(this.options.flash_render_el).innerHTML = "<span style='color:red'>你的浏览器不支持flash的显示，可能是你在加载项中禁用了该控件或者没有安装该控制，请先启用或者下载flash插件。</span>"
		}
		$(this.options.flash_render_el).style.width = "100%";
		$(this.options.flash_render_el).style.height = "100%";
		//this.ajaxSWFData();
	},
	ajaxSWFData : function(container,sUrl){
		var caller=this;
		container = container || this.options.flash_render_el;
		sUrl = sUrl || this.options.data_url;
		var parames  =this.getAllQueryString();
		sUrl +="?"+parames;
		new Ajax.Request(sUrl,{
			onSuccess : function(transport){
				var data = eval('('+transport.responseText.trim()+')');
				var tmp = findSWF(container);
				//if(!tmp) alert("没有找到flash对象");
				tmp.load( JSON.stringify(data) );
				// 获取分页信息
				var CurrPage = transport.getResponseHeader("CurrPage");
				var PageSize = transport.getResponseHeader("PageSize");
				var Num = transport.getResponseHeader("Num");
				if(window.drawNavigator){
					drawNavigator({
						PageIndex :CurrPage,
						PageSize : PageSize,
						PageCount : Math.ceil(Num/PageSize),
						Num : Num
					});
				}
				var XlabelsNum = transport.getResponseHeader("XlabelsNum");
				var ChartType = transport.getResponseHeader("ChartType");
				caller.ajaxSuccessCallBack(ChartType,XlabelsNum);
			},
			onFailure : function(transport){
				alert("获取统计数据出现错误！");
			}
		});
	},
	ajaxSuccessCallBack : function(){
	},
	getAllQueryString : function(){
		var queryJson = window.location.search.parseQuery();
		//Ext.apply(queryJson,this.getUrlString().parseQuery());
		if(window.Stat && Stat.SearchBar.UI)
			Ext.apply(queryJson,Stat.SearchBar.UI.getUrlString().parseQuery());
		Ext.apply(queryJson,this.options.ajax_parame);
		return parseJsonToParams(queryJson);
	}
}

/*
*  找到flash对象
*/
function findSWF(movieName) {
  if (navigator.appName.indexOf("Microsoft")!= -1) {
    return window[movieName];
  } else {
    return document[movieName];
  }
}
/*
*  flash默认调用的绘制flash
*/
function open_flash_chart_data(){
	return JSON.stringify({"elements":[{"type":"pie"}]});
}
/*
*  flash准备好了之后会请求数据，修证在FF下刚开始不出现数据的问题
*/
function ofc_ready(){
	Flash.Data.Render.ajaxSWFData();
}
/*
* flash的另存为图片实现
*
*/
OFC = {};
OFC.jquery = {
	name: "jQuery",
	version: function(src) { return $(src).get_version() },
	rasterize: function (src, dst) { $(dst).replaceWith(OFC.jquery.image(src)) },
	image: function(src) { return "<img src='data:image/gif;base64," + $(src).get_img_binary() + "' />"},
	popup: function(src) {
		var img_win = window.open('', 'Charts')
		with(img_win.document) {
			write('<html><head><title>保存统计数据为图片<\/title><\/head><body>' + OFC.jquery.image(src) + '<\/body><\/html>') }
		// stop the 'loading...' message
		img_win.document.close();
	}
}

/*
*  右键保存flash为图片所调用的方法
*/
function save_image() {OFC.jquery.popup(Flash.Data.Render.options.flash_render_el)}
