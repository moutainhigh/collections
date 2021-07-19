
var CalClass = {
  create: function() {
    return function() {
      this.initialize.apply(this, arguments);
    }
  }
}
var Extend = function(destination, source) {
    for (var property in source) {
        destination[property] = source[property];
    }
    return destination;
}
var Calendar = CalClass.create();
/**
 * 将字符串形式的日期转化为date型的
 */
Calendar.parseDate = function(dateString){
	var dateArray = dateString.split("-");
  	return new Date(dateArray[0],dateArray[1] - 1,dateArray[2]);	
}
Calendar.prototype = {
  initialize: function(container, options) {
	this.Container = document.getElementById(container);//容器(table结构)
	this.Days = [];//日期对象列表
	this.SetOptions(options);
	this.Year = this.options.Year || new Date().getFullYear();
	this.Month = this.options.Month || new Date().getMonth() + 1;
	this.SelectDay = this.options.SelectDay ? new Date(this.options.SelectDay) : null;
	this.onSelectDayCallBack = this.options.onSelectDayCallBack;
	this.onFinish = this.options.onFinish;
	this.specialDays = this.options.specialDays;
	this.specialDaysDesc = this.options.specialDaysDesc;
	this.taskList = this.options.taskList;
	this.clean = this.options.clean;
	this.allowPrev = this.options.allowPrev;
	// this.Draw();
  },
  //设置默认属性
  SetOptions: function(options) {
	this.options = {//默认值
		Year:			0,//显示年
		Month:			0,//显示月
		SelectDay:		null,//选中的日期，默认为空
		onSelectDayCallBack:	function(){},//在选择日期触发
		onFinish:		function(){},//日历画完后触发
		specialDays:	[],//特殊日期，非工作日
		clean: false,
		allowPrev: true	//是否允许当日之前的日期添加点击事件
	};
	Extend(this.options, options || {});
  },
  //当前月
  NowMonth: function() {
	this.PreDraw(new Date());
  },
  //上一月
  PreMonth: function() {
	this.PreDraw(new Date(this.Year, this.Month - 2, 1));
	return this.Month;
  },
  //下一月
  NextMonth: function() {
	this.PreDraw(new Date(this.Year, this.Month, 1));
	return this.Month;
  },
  //上一年
  PreYear: function() {
	this.PreDraw(new Date(this.Year - 1, this.Month - 1, 1));
	return this.Year;
  },
  //下一年
  NextYear: function() {
	this.PreDraw(new Date(this.Year + 1, this.Month - 1, 1));
	return this.Year;
  },
  //根据日期画日历
  PreDraw: function(date) {
	//再设置属性
	this.Year = date.getFullYear(); this.Month = date.getMonth() + 1;
	//重新画日历
	this.Draw();
  },
  //画日历
  Draw: function() {
	//用来保存日期列表
	var arr = [];
	//用当月第一天在一周中的日期值作为当月离第一天的天数
	for(var i = 1, firstDay = new Date(this.Year, this.Month - 1, 1).getDay(); i <= firstDay; i++){ arr.push(0); }
	//用当月最后一天在一个月中的日期值作为当月的天数
	for(var i = 1, monthDay = new Date(this.Year, this.Month, 0).getDate(); i <= monthDay; i++){ arr.push(i); }
	//清空原来的日期对象列表
	this.Days = [];
	//插入日期
	var frag = document.createDocumentFragment();
	var p = this;
	while(arr.length){
		//每个星期插入一个tr
		var row = document.createElement("tr");
		//每个星期有7天 
		for(var i = 1; i <= 7; i++){
			var cell = document.createElement("td"); cell.innerHTML = "&nbsp;";
			cell.className = 'daybg';
			if(arr.length){
				var d = arr.shift();
				if(d){
					this.Days[d] = cell;
					var on = new Date(this.Year, this.Month - 1, d);
					//设置初始颜色
					if(!this.clean){
						cell.style.backgroundColor = (this.IsWorkDay(on)===true ? "#EDEDED" : "#ccffcc");
						cell.style.padding = '2px';
						cell.style.height = '24px';
						cell.style.textAlign = 'center';
						cell.style.verticalAlign = 'Middle';
					}else{
						cell.className = 'daybg';
					}
					
					if(!this.clean){
						cell.title = this.Year + '年' + this.Month + '月' + d + '日 ' + (this.IsWorkDay(on)===true ? "工作日" : "非工作日");
						var cName = 'normal';
						//判断是否今日
						if(this.IsSame(on, new Date())){
							cell.className = "today";
							cName = "today";
							cell.title = "今天是 "+ (this.IsWorkDay(on)===true ? "工作日" : "非工作日");
						}
						var id = this.Year+(this.Month.toString().length==1 ? '0'+this.Month : this.Month.toString()) + (d.toString().length==1 ? '0'+d : d.toString());
						cell.innerHTML = '<div style="height:20px;"><div class="'+cName+'">'+d+'</div><div title='+(this.IsWorkDay(on)===true ? "工作日" : (this.IsWorkDay(on).length==0 ? "非工作日" : this.IsWorkDay(on)))+' class="cworkday">'+ (this.IsWorkDay(on)===true ? "工作日" : (this.IsWorkDay(on).length==0 ? "非工作日" : this.IsWorkDay(on)))+'</div></div>';
						cell.innerHTML += '<input type="hidden" value='+d+' /><div id='+id+' class="tasklist"></div>';
					}else{
						if(this.IsSame(on, new Date())){
							cell.className = "today";
						}
						cell.innerHTML = d;
					}
					//var ond1 = on.getTime();
					var ond1 = on.getFullYear() + '' + (on.getMonth()+1) + '' + on.getDate();
					var ond2 = new Date();
					ond2 = ond2.getFullYear()+ '' + (ond2.getMonth()+1) + '' + ond2.getDate();
					var clickFun = function(){
						if(p.selectedDay){
							if(!p.clean){
								p.selectedDay.style.border = "1px solid #ccf";
							}else{
								p.selectedDay.className = 'daybg';
							}
						}
						p.selectedDay = this;
						if(!p.clean){
							this.style.border = "1px solid #6699ff";
						}else{
							this.className = "daynow";
						}
						// 执行回调
						p.onSelectDayCallBack(this);
						};
					if(ond1-ond2>=0){
						cell.onclick = clickFun;
					}else{
						if(this.allowPrev){
							cell.onclick = clickFun;
						}else{
							cell.ondblclick = clickFun;	
						}
					}
					cell.style.cursor = "hand";
				}
			}
			row.appendChild(cell);
		}
		frag.appendChild(row);
	}
	//先清空内容再插入(ie的table不能用innerHTML)
	while(this.Container.hasChildNodes()){ this.Container.removeChild(this.Container.firstChild); }
	this.Container.appendChild(frag);
	if(this.taskList){
		for(var ii=0; ii<this.taskList.length; ii++){
			var id = this.taskList[ii].key;
			var dataList = this.taskList[ii].data;
			if(document.getElementById(id)){
				var div = document.getElementById(id);
				var oUl = document.createElement('ul');
				for(var jj=0; jj<dataList.length; jj++){
					var oLi = document.createElement("li");
					oLi.innerHTML = dataList[jj];
					oUl.appendChild(oLi);
				}
				div.appendChild(oUl);
			}
		}
	}
	//附加程序
	this.onFinish();
  },
  //判断是否同一日
  IsSame: function(d1, d2) {
	return (d1.getFullYear() == d2.getFullYear() && d1.getMonth() == d2.getMonth() && d1.getDate() == d2.getDate());
  },
  // 判断是否是工作日
  IsWorkDay : function(d1){
  	var specialDayArray;
  	for(var i = 0; i < this.specialDays.length; i++){
  		var specialDay = Calendar.parseDate(this.specialDays[i]);
  		// 如果包含在特定日期中
  		var containFlag = this.IsSame(d1, specialDay);
  		if(containFlag){
  			// 只要在特殊日期列表,都是非工作日
  			return this.specialDaysDesc[i];
  		}
  	}
  	
  	// 不包含在特定日期中
  	return true;
  },
  // 设置特殊日期
  setSpecialDays : function(dataList){
  	this.specialDays = dataList;
  },
  setSpecialDaysDesc : function(dataList){
	this.specialDaysDesc = dataList;
  }//,
/*  PreYear : function(){
	  this.PreYear();
  },
  NextYear : function(){
	  this.NextYear();
  },
  PreMonth : function(){
	  this.PreMonth();
  },
  NextMonth : function(){
	  this.NextMonth();
  }*/
}
