/*
* 保存flash图形查看页面，右侧统计数据的页面结构
* 
*/
var OPTIONS_HTML_TEMPLATE = [
	'<LI><input type="radio" name="HitsTimeItem" value="0" id="HitsTimeItem_0" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_0">不限</LABEL></LI>',
	'<LI><input type="radio" name="HitsTimeItem" value="1" id="HitsTimeItem_1" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_1">昨天</LABEL></LI>',
	'<LI><input type="radio" name="HitsTimeItem" value="2" id="HitsTimeItem_2" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_2">本月</LABEL></LI>',
	'<LI><input type="radio" name="HitsTimeItem" value="3" id="HitsTimeItem_3" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_3">本季</LABEL></LI>',
	'<LI><input type="radio" name="HitsTimeItem" value="4" id="HitsTimeItem_4" onclick="hitsTimeOptionClick(this.value)"/><LABEL for="HitsTimeItem_4">本年</LABEL></LI>'
	].join("");
Ext.apply(Flash.Data.Render,{
	currId: null,
	options :{
		flash_render_el:"",
		time_step_inputName:'time-step',
		data_url:"",
		default_time_step:1
	},
	init : function(opt){
		Ext.apply(this.options,opt);
		this.initHTML();
		this.initEvent();
		//  设置右边选项的默认值
		this.setDefaultValue();
		// 调用Flash.Data.Render中的画flash,加载数据等
		this.initDefault();
	},
	/*
	*  在发送ajax之前，我先设置选项的默认值,当检索点击检索回来时，可以ajax到正确的数据
	*/
	setDefaultValue : function(sqlIndex,sValue){
		var hitsTimeParam = getParameter("HitsTimeItem");
		if(!hitsTimeParam){
			$('HitsTimeItem_0').checked = true;
		}else{
			$('HitsTimeItem_' + hitsTimeParam).checked = true;
		}
		// 设置时间步长默认值
		var TimeStep = this.options.default_time_step;
		if(!TimeStep) return;
		var stepInputEls = document.getElementsByName(this.options.time_step_inputName);
		for (var i = 0; i < stepInputEls.length; i++){
			if(stepInputEls[i].value != TimeStep)continue;
			stepInputEls[i].checked=true;
			break;
		}
	},
	initEvent : function(){
		var caller = this;
		//对步长相关按钮进行事件处理
		Event.observe(document,"click",function(e){
			e = e || window.event;
			var dom = Event.element(e);
			var el = Element.find(dom,"name");
			if(!el || el.tagName!="INPUT" || 
				el.getAttribute("name")!=caller.options.time_step_inputName) return;
			caller.ajaxSWFData();
		});
	},
		initHTML : function(){
		var opl = $(this.options.options_el);
		if(opl)opl.innerHTML = OPTIONS_HTML_TEMPLATE;
	},
	/*
	*  设置检索组件额外的检索值
	*/
	setSearchExtParames : function(){
		if(!window.Stat || !window.Stat.SearchBar) return;
		// TODO---如果其它地方已经对Stat.SearchBar.UI.options.ext_url_param赋值，则会出现其它问题
		//if(getParameter(Stat.SearchBar.UI.options.ext_url_param)
		Stat.SearchBar.UI.options.ext_url_param=this.getUrlString();
	},
	getUrlString : function(){
		var hitsTimeItem = getHitsTimeOptionValue();
		var TimeStep = $F$F(this.options.time_step_inputName);
		return ["TimeStep="+TimeStep,
				"HitsTimeItem="+hitsTimeItem].join("&");
	},
	ajaxSuccessCallBack : function(){
		// 设置检索的额外参数
		this.setSearchExtParames();
		if(!arguments[0])return;
		// 设置flash的大小，如果数据较多，允许出现滚动条,只有柱状图才需要出现滚动条
		// 需要设置父元素的宽度,如果是柱状图，若数据较多，需要出现滚动条
		var flashEl = $(this.options.flash_render_el); 
		var parent = flashEl.parentNode;
		var parentWidth = parent.offsetWidth;
		//if(parent.tagName == "TD") return;
		if(arguments[0]=="barchart"){
			if(parentWidth/arguments[1]<60)
				flashEl.style.width = arguments[1]*60+"px";
			else flashEl.style.width = "100%";
			// IE6下出现下标被遮盖的问题
			if(flashEl.offsetWidth>parentWidth && Ext.isIE6)
				parent.style.paddingBottom="20px";
			else
				parent.style.paddingBottom="0px";
		}else if(arguments[0]=="trendchart"){
			if(parentWidth/arguments[1]<20)
				flashEl.style.width = arguments[1]*20+"px";
			else flashEl.style.width = "100%";
			// IE6下出现下标被遮盖的问题
			if(flashEl.offsetWidth>parentWidth && Ext.isIE6)
				parent.style.paddingBottom="20px";
			else
				parent.style.paddingBottom="0px";
		}
		/*
		// 设置检索的额外参数
		this.setSearchExtParames();
		// 设置flash的大小，如果数据较多，允许出现滚动条,只有柱状图才需要出现滚动条
		// 需要设置父元素的宽度,如果是柱状图，若数据较多，需要出现滚动条
		var flashEl = $(this.options.flash_render_el); 
		var parent = flashEl.parentNode;
		if(parent.tagName == "TD") return;
		// 当当前分页数据大小超过10时，需要出现滚动条，设置flash的大小为当前分页大小*80 PX
		if(!PageNav) return;
		// 如果没有数据，则返回
		if(PageNav["PageCount"]==0) return;
		var currPageNum=0;
		if(PageNav["PageIndex"]==PageNav["PageCount"]){
			currPageNum = PageNav["Num"]-(PageNav["PageCount"]-1)*PageNav["PageSize"];
		}else	currPageNum = PageNav["PageSize"];
		if(currPageNum>10)
			flashEl.style.width = currPageNum*80+"px";*/
	},
	getAllQueryString : function(){
		var queryJson = window.location.search.parseQuery();
		
		if(window.Stat && Stat.SearchBar.UI)
			Ext.apply(queryJson,Stat.SearchBar.UI.getUrlString().parseQuery());
		Ext.apply(queryJson,this.getUrlString().parseQuery());
		// 添加分页信息
		return parseJsonToParams(queryJson);
	}
})

function hitsTimeOptionClick(){
	Flash.Data.Render.ajaxSWFData();
}
function getHitsTimeOptionValue(){
	var OptionEls = document.getElementsByName("HitsTimeItem");
	if(OptionEls){
		for(var k=0; k<OptionEls.length;k++){
			var el = OptionEls[k];
			if(el.checked){
				return el.value;
			}
		}
	}
	return 0;
}
