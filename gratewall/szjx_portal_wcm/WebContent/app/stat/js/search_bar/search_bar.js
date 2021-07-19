/*
* 绩效考核系统中各子页面的搜索框定义JS
* 1.select_time 时间范围选择共有[{text:"不限",value:"0"},{text:"今日",value:"1"},{text:"本月",value:"2"},{text:"本季",value:"3"},{text:"本年",value:"4"},{text:"指定",value:"-1"}] 如果在页面中不需要今日选项，在配置对象时可以把{text:"今日",value:"1"}去掉即可,如果需要添加一些新的时间范围下拉菜单，则需要在页面的选项中定义一个对象并添加开始时间和结束时间信息，
  例如：我需要一个新的下拉菜单，统计上个星期的情况，添加如下对象
	{
		text:"上星期",
		value:"last_week",
		start_time:"2010-10-11 00:00",
		end_time:"2010-10-15 00:00"
	}
  上面这个对象中start_time和end_time都需要自己计算，如果没有start_time，则表示不限制开始时间，如果没有end_time，则表示统计到当前时间为止。select_time_defaultValue可以指定默认的时间选择项值。
*
* 2.start_time_defaultValue 和end_time_defaultValue 分别表示当需要指定时间时，输入框中的时间默认值
*
* 3.select_item 为需要检索的下拉项，同select_time，是一个对象数组，定义了text和value。其中select_item_defaultValue可以定义默认值。
*
* 4.search_cmd 和export_cmd 分别为组件的检索和导出按键命令，一般在检索命令中需要对时间格式进行校验。
*
* 5.getSearchInfo() 这是个函数返回检索所需要的信息，包括开始时间、结束时间、检索项、检索项值。可以在search_cmd中通过对象Stat.SearchBar.UI.getSearchInfo()获取检索信息。
*/
Ext.ns("Stat.SearchBar");
(function(){
	var DATE_VALUE = {
		NO_LIMIT:"0",
		TODAY:"1",
		MONTH:"2",
		SEASON:"3",
		YEAR:"4",
		SPECIFY:"-1"
	}
	/*
	*  搜索模块模板
	*/
	var HTML_TEMPLATE='<span class="select_time_desc">{3}</span><select id="select_time_" class="select_time">{0}</select><span class="time_set display-none" id="time_set_">{1}</span><select class="select_item" id="select_item_">{2}</select><input type="text" id="search_value_" name="" value=""><span class="btns"><input type="button" class="search_btn" id="search_btn_" name="" value="检索"/><input type="button" id="export_btn_" name="" class="export_btn" value="导出"/></span>';

	var HTML_TIME_SELECT = '从<input id="_search_start_time_" type="text" name="" value="" elname="开始时间"><button class="time_select" id="_start_time_select_btn_" onclick=""></button>至<input id="_search_end_time_" type="text" name="" value="" elname="结束时间"><button class="time_select" id="_end_time_select_btn_" onclick=""></button>';
	Stat.SearchBar = function(){};
	Ext.apply(Stat.SearchBar.prototype,{
		/*
		*  默认配置值
		*/
		options : {
			/*
			*  需要绘制的目标元素ID
			*/
			renderTo:"search_bar",
			/*
			*  时间选择下拉菜单值
			*/
			select_time:[{
				text:"不限",
				value:DATE_VALUE.NO_LIMIT
			},{
				text:"今日",
				value:DATE_VALUE.TODAY
			},{
				text:"本月",
				value:DATE_VALUE.MONTH
			},{
				text:"本季",
				value:DATE_VALUE.SEASON
			},{
				text:"本年",
				value:DATE_VALUE.YEAR
			},{
				text:"指定",
				value:DATE_VALUE.SPECIFY
			}],
			/*
			*  下拉时间选择默认值
			*/
			select_time_defaultValue:"",
			select_time_desc:"统计时间：",
			/*
			*  时间选择组件默认的值，如果没有，则开始时间选择今天的时间，结束时间为当前时间
			*/
			start_time_defaultValue:"",
			end_time_defaultValue:"",
			/*
			*  检索项，如发稿人、部门名称等
			*/
			select_item:[],
			/*
			*  设置检索项的默认值
			*/
			select_item_defaultValue:"",
			search_defaultValue:"",
			/*
			*  检索和导出按钮触发函数
			*/
			search_cmd:function(){
				var sUrl = Stat.SearchBar.UI .options.search_url;
				if(!sUrl)sUrl = window.location.href.match(/^[^?]*/)[0];
				var sUrlString = Stat.SearchBar.UI.getUrlString();
				if(!sUrlString){
					return;
				}
				window.location.href=sUrl+"?"+sUrlString;
			},
			ext_url_param:"",
			export_cmd:function(){},
			search_url:""
		},
		/*
		*  初始化搜索栏
		*/
		init : function(opts){
			//获取用户配置项
			Ext.apply(this.options,opts);
			Ext.apply(this.options,this.getOptFromHref());
			
			if(!$(this.options.renderTo)) return;
			//初始化时间选项
			var sSearch_HTML=String.format(HTML_TEMPLATE,this._init_select_time(),HTML_TIME_SELECT,this._init_select_item(),this.options.select_time_desc);
			//renderTo
			$(this.options.renderTo).innerHTML = sSearch_HTML;
			//初始化默认值
			this.initDefaultValue();
			//事件初始化
			this.initEvent();
		},
		/*
		* 从页面的链接中获取搜索组件的参数
		*/
		getOptFromHref : function(){
			var opt = {};
			queryJson = window.location.search.parseQuery();
			var startTime_value = null,endTime_value=null;
			if(queryJson["TimeItem"]){
				opt.select_time_defaultValue=queryJson["TimeItem"];
				if(queryJson["TimeItem"]==DATE_VALUE.SPECIFY){
					opt.start_time_defaultValue = queryJson["StartTime"] || "";
					opt.end_time_defaultValue = queryJson["EndTime"] || "";
				}
				
			}
			opt.select_item_defaultValue = queryJson["SelectItem"] || "";
			opt.search_defaultValue = queryJson["SearchValue"] || "";
			return opt;
		},
		/*
		*  初始化默认值
		*/
		initDefaultValue : function(){
			// 初始化下拉选择菜单的默认值
			if(this.options.select_time_defaultValue){
				$("select_time_").value = this.options.select_time_defaultValue;
				if($("select_time_").value == DATE_VALUE.SPECIFY)
					Element.removeClassName($("time_set_"),"display-none");
			}
			if(this.options.select_item_defaultValue){
				//如果当前检索栏中不存在该值，则默认取第一个检索项目
				for (var i = 0; i < this.options.select_item.length; i++){
					if(this.options.select_item[i].value != this.options.select_item_defaultValue)continue;
					$("select_item_").value = this.options.select_item_defaultValue;
				}
			}
			// 初始化时间选择框的默认值
			if(this.options.start_time_defaultValue)
				$("_search_start_time_").value = this.options.start_time_defaultValue;
			else
				$("_search_start_time_").value = new Date().format("yyyy-mm-dd 00:00");
			if(this.options.end_time_defaultValue)
				$("_search_end_time_").value = this.options.end_time_defaultValue;
			else
				$("_search_end_time_").value = new Date().format("yyyy-mm-dd HH:MM");
			//需要先判断该input是否是隐藏元素 先定位到输入框，再赋值可以把光标移到末尾
			var searchInput = $("search_value_");
			if(Element.getStyle(searchInput,"display")=="none")return;
			searchInput.focus();
			// 设置检索的默认值
			if(this.options.search_defaultValue){
				searchInput.value = this.options.search_defaultValue;
			}
		},
		/*
		*  事件初始化
		*/
		initEvent : function(){
			// 时间范围下拉菜单，当其选项为“指定”时，需要显示时间组件
			Event.observe($("select_time_"),"change",function(){
				if($("select_time_").value == DATE_VALUE.SPECIFY)
					Element.removeClassName($("time_set_"),"display-none");
				else{
					Element.addClassName($("time_set_"),"display-none");
				}
				
				// 如果已经显示出来了日历组件，则从"指定"修改成其它选项时需要隐藏该组件
				var cal_dd = $("cal_dd");
				if(!cal_dd) return;
				//if(cal_dd.)
				if(cal_dd.style.display=="none")return;
				cal_dd.style.display="none";
				$("cal_dd_shld").style.display="none";
			});
			// 当点击按钮时事件
			//var s_cmd = typeof(this.options.search_cmd)=="string"?e
			var caller = this;
			Event.observe($("search_btn_"),"click",this.options.search_cmd.bind(this));
			Event.observe($("export_btn_"),"click",this.options.export_cmd.bind(this));
			// 回车检索功能
			Event.observe(document,"keydown",function(ev){
				var e = ev || window.event;
				switch(e.keyCode){
					case 13:
						caller.options.search_cmd();
					break;
				}
			});
			// 时间组件点击显示日历
			if(window.wcm && wcm.TRSCalendar){
				wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
				wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
			}
		},
		/*
		*  初始化搜索时间下拉选择
		*/
		_init_select_time : function(){
			var select_time = this.options.select_time;
			var sHtml = "";
			for(var i=0;i<select_time.length;i++){
				var option = document.createElement("option");
				option.value = select_time[i].value;
				option.innerHTML = select_time[i].text;
				if(select_time[i].start_time)
					option.setAttribute("start_time",select_time[i].start_time);
				if(select_time[i].end_time)
					option.setAttribute("end_time",select_time[i].end_time);
				sHtml+=option.outerHTML;
			}
			return sHtml;
		},
		/*
		*  初始化搜索项下拉选择
		*/
		_init_select_item : function(){
			var select_item = this.options.select_item;
			var sHtml = "";
			for(var i=0;i<select_item.length;i++){
				var option = document.createElement("option");
				option.value = select_item[i].value;
				option.innerHTML = select_item[i].text;
				sHtml+=option.outerHTML;
			}
			return sHtml;
		},
		/*
		*  获取搜索信息，主要包括时间、搜索项和搜索值
		*/
		getSearchInfo : function(){
			// 1.获取时间
			var select_time = this._get_select_time();
			if(!select_time){
				return null;
			}
			return {
				start_time:select_time.start_time,
				end_time:select_time.end_time,
				select_item:$("select_item_").value,
				search_value:$("search_value_").value
			}
		},
		getUrlString : function(){
			var info=this.getSearchInfo();
			if(!info){
				return null;
			}
			var tempQueryJson = {
				StartTime:info.start_time,
				EndTime:info.end_time,
				SelectItem:info.select_item,
				SearchValue:info.search_value,
				TimeItem:$("select_time_").value
			};
			// 支持传字符串 和 json
			if(typeof(this.options.ext_url_param)=="string")
				Ext.apply(tempQueryJson,this.options.ext_url_param.parseQuery());
			else
				Ext.apply(tempQueryJson,this.options.ext_url_param);
			// 需要覆盖当前链接上的参数
			var queryJson = window.location.search.parseQuery();
			Ext.apply(queryJson,tempQueryJson);
			return parseJsonToParams(queryJson);
		},
		/*
		*  获取搜索的时间值，包括开始时间和结束时间，如果选择"不限"，则表现开始时间和结束时间都为null或者""
		*/
		_get_select_time : function(){
			var select_time_info ={};
			/* // 只有当是指定时才需要传入时间
			switch($("select_time_").value){
				case DATE_VALUE.NO_LIMIT:
					break;
				case DATE_VALUE.TODAY:
					select_time_info.start_time = new Date().format("yyyy-mm-dd 00:00");
					select_time_info.end_time = new Date().format("yyyy-mm-dd HH:MM");
					break;
				case DATE_VALUE.MONTH:
					select_time_info.start_time = new Date().format("yyyy-mm-01 00:00");
					select_time_info.end_time = new Date().format("yyyy-mm-dd HH:MM");
					break;
				case DATE_VALUE.SEASON:
					var month = new Date().getMonth()+1;
					if(month>=10)month=10;
					else if(month>=7)month=7;
					else if(month>=4)month=4
					else month=1;
					//var season_month = month-month%3;
					select_time_info.start_time = new Date().format("yyyy")+"-"+month+"-01 00:00";
					select_time_info.end_time = new Date().format("yyyy-mm-dd HH:MM");
					break;
				case DATE_VALUE.YEAR:
					select_time_info.start_time = new Date().format("yyyy-01-01 00:00");
					select_time_info.end_time = new Date().format("yyyy-mm-dd HH:MM");
					break;
				case DATE_VALUE.SPECIFY:
					select_time_info.start_time = $("_search_start_time_").value;
					select_time_info.end_time = $("_search_end_time_").value;
					break;
				default:
					var select = $("select_time_");
					var option = select.options[select.selectedIndex];
					select_time_info.start_time = option.getAttribute("start_time");
					select_time_info.end_time = option.getAttribute("end_time");
			}*/
			switch($("select_time_").value){
				case DATE_VALUE.SPECIFY:
					// 这里是针对点击回车键之后，没有进行日期格式的判断新增的，否则，会出现异常
					if(!this.checkDateTime($("_search_start_time_")) || !this.checkDateTime($("_search_end_time_"))){
						return null;
					}
					select_time_info.start_time = $("_search_start_time_").value;
					select_time_info.end_time = $("_search_end_time_").value;
					break;
			}
			
			return select_time_info;
		},
		checkDateTime : function(inputEl, sDateFormat){
			var dft = sDateFormat || "yyyy-mm-dd HH:MM";
			if(DateHelper.check(inputEl.value, dft)){
				inputEl.blur();
				return false;
			}
			return true;
		}
	});
	Stat.SearchBar.UI = new Stat.SearchBar();
})();