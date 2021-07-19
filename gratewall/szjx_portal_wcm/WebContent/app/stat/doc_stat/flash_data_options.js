/*
* 保存flash图形查看页面，右侧统计数据的页面结构
* 
*/
var SELECT_HTML_TEMPLATE = [
	'<select name="" id="_sql_index_select_">',
		'<option value="docNum" sqlindex="1">总发稿量</option>',
		'<option value="docStatus" sqlindex="2">文档状态统计</option>',
		'<option value="docModal" sqlindex="3">使用情况统计</option>',
		'<option value="docForm" sqlindex="5">文档类型统计</option>',
	'</select>'].join("");
var OPTIONS_HTML_TEMPLATE = [
	'<ul class="docNum-list display-none" >',
	'</ul>',
	'<ul class="docStatus-list display-none">',
		'<li><input type="radio" name="docStatus-radio" id="docStatus-pub" value="10" desc="已发"><label for="docStatus-pub">已发</label></li>',
		'<li><input type="radio" name="docStatus-radio" value="1" id="docStatus-new" desc="新稿"><label for="docStatus-new">新稿</label></li>',
		'<li><input type="radio" name="docStatus-radio" value="2" id="docStatus-edit" desc="已编"><label for="docStatus-edit">已编</label></li>',
		'<li><input type="radio" name="docStatus-radio" value="18" id="docStatus-verify" desc="正审"><label for="docStatus-verify">正审</label></li>',
		'<li><input type="radio" name="docStatus-radio" value="16" desc="已签" id="docStatus-sign"><label for="docStatus-sign">已签</label></li>',
		'<li><input type="radio" name="docStatus-radio" value="3" desc="返工" id="docStatus-again"><label for="docStatus-again">返工</label></li>',
	'</ul>',
	'<ul class="docModal-list display-none">',
		'<li><input type="radio" name="docModal-radio" id="docModal-entity" value="1" desc="原稿"><label for="docModal-entity">原稿</label></li>',
		'<li><input type="radio" name="docModal-radio" id="docModal-link" value="2" desc="引用"><label for="docModal-link">引用</label></li>',
		'<li><input type="radio" name="docModal-radio" id="docModal-copy" value="3" desc="复制"><label for="docModal-copy">复制</label></li>',
	'</ul>',
	'<ul class="docForm-list display-none">',
		'<li><input type="radio" name="docForm-radio" value="1" id="docForm-text" desc="文字型"><label for="docForm-text">文字型</label></li>',
		'<li><input type="radio" name="docForm-radio" value="2" id="docForm-pic" desc="图片型"><label for="docForm-pic">图片型</label></li>',
		'<li><input type="radio" name="docForm-radio" value="3" id="docForm-audio" desc="音频型"><label for="docForm-audio">音频型</label></li>',
		'<li><input type="radio" name="docForm-radio" value="4" id="docForm-video" desc="视频型"><label for="docForm-video">视频型</label></li>',
	'</ul>'].join("");
Ext.apply(Flash.Data.Render,{
	currId: null,
	options :{
		flash_render_el:"",
		select_el:"",
		options_el:"",
		data_url:"",
		time_step_inputName:'time-step',
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
		sValue = sValue || getParameter("value");
		sqlIndex = sqlIndex || getParameter("sqlIndex");
		var selectEl = $("_sql_index_select_");
		if(!selectEl)return;
		var options = selectEl.options;
		for(var i=0;i<options.length;i++){
			if(options[i].getAttribute("sqlindex")!=sqlIndex)continue;
			selectEl.value = options[i].value;
			break;
		}
		var ops = document.getElementsByClassName(selectEl.value+"-list");
		if(!ops[0])return;
		Element.removeClassName(ops[0],"display-none");
		Element.addClassName(ops[0],"ul-display-auto");
		var inputEls = ops[0].getElementsByTagName("INPUT");
		for(var i=0;i<inputEls.length;i++){
			if(inputEls[i].value!=sValue)continue;
			inputEls[i].checked=true;
			break;
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
	initHTML : function(){
		var sel = $(this.options.select_el);
		if(sel)sel.innerHTML = SELECT_HTML_TEMPLATE;
		var opl = $(this.options.options_el);
		if(opl)opl.innerHTML = OPTIONS_HTML_TEMPLATE;
	},
	initEvent : function(){
		var selectEl = Element.first($(this.options.select_el));
		var opl = $(this.options.options_el);
		if(!selectEl || selectEl.tagName != "SELECT") return;
		var caller = this;
		// 下拉菜单的事件绑定
		Event.observe(selectEl,"change",function(e){
			// 删除原来已选中的元素
			var ulEls = document.getElementsByClassName("ul-display-auto");
			if(ulEls[0]){
				Element.addClassName(ulEls[0],"display-none");
				Element.removeClassName(ulEls[0],"ul-display-auto");
			}
			e = window.event || e;
			var value = Event.element(e).value;
			ulEls = document.getElementsByClassName(value+"-list");
			if(!ulEls[0])return;
			Element.removeClassName(ulEls[0],"display-none");
			Element.addClassName(ulEls[0],"ul-display-auto");
			var first_li = Element.first(ulEls[0]);
			if(first_li){
				var input = first_li.getElementsByTagName("INPUT")[0];
				input.checked=true;
				caller.currId = input.getAttribute("id");
			}
			caller.ajaxSWFData();
		});
		// radio元素的事件绑定
		Event.observe(opl,"click",function(e){
			var inputEl=caller.getCheckedInput();
			if(inputEl == $(caller.currId)) return;
			caller.currId = inputEl.getAttribute("id");
			caller.ajaxSWFData();
		});
		
		/*
		*  对步长相关按钮进行事件处理
		*/
		Event.observe(document,"click",function(e){
			e = e || window.event;
			var dom = Event.element(e);
			var el = Element.find(dom,"name");
			if(!el || el.tagName!="INPUT" || 
				el.getAttribute("name")!=caller.options.time_step_inputName) return;
			caller.ajaxSWFData();
		});
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
	getCheckedInput : function(){
		var selectEl = Element.first($(this.options.select_el));
		var ulEl = document.getElementsByClassName(selectEl.value+"-list")[0];
		if(ulEl){
			var inputEls = ulEl.getElementsByTagName("INPUT");
			for(var i=0;i<inputEls.length;i++){
				if(!inputEls[i].checked)continue;
				return inputEls[i];
			}
		}
		return null;
	},
	getUrlString : function(){
		var selectEl = Element.first($(this.options.select_el));
		if(!selectEl) return "";
		var currOption = selectEl.options[selectEl.selectedIndex];
		var sqlIndex = currOption.getAttribute("sqlindex");
		var desc = currOption.text;
		var value =0;
		var selectValue = selectEl.value;
		var inputEl=this.getCheckedInput();
		if(inputEl){
			value = inputEl.value;
			desc+="-"+inputEl.getAttribute("desc");
		}
		// 查看是否有时间步长，用于绘制走势图
		var TimeStep = $F$F(this.options.time_step_inputName);
		return ["value="+value,
				"desc="+encodeURI(desc),
				"sqlIndex="+sqlIndex,
				"TimeStep="+TimeStep].join("&");
				
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
			if(flashEl.offsetWidth>parentWidth && Ext.isIE6)
				parent.style.paddingBottom="20px";
			else
				parent.style.paddingBottom="0px";
		}
		
		//alert(arguments.length)
		// 当当前分页数据大小超过10时，需要出现滚动条，设置flash的大小为当前分页大小*80 PX
		/*if(!PageNav) return;
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

