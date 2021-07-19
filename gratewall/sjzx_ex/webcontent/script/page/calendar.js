
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
 * ���ַ�����ʽ������ת��Ϊdate�͵�
 */
Calendar.parseDate = function(dateString){
	var dateArray = dateString.split("-");
  	return new Date(dateArray[0],dateArray[1] - 1,dateArray[2]);	
}
Calendar.prototype = {
  initialize: function(container, options) {
	this.Container = document.getElementById(container);//����(table�ṹ)
	this.Days = [];//���ڶ����б�
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
  //����Ĭ������
  SetOptions: function(options) {
	this.options = {//Ĭ��ֵ
		Year:			0,//��ʾ��
		Month:			0,//��ʾ��
		SelectDay:		null,//ѡ�е����ڣ�Ĭ��Ϊ��
		onSelectDayCallBack:	function(){},//��ѡ�����ڴ���
		onFinish:		function(){},//��������󴥷�
		specialDays:	[],//�������ڣ��ǹ�����
		clean: false,
		allowPrev: true	//�Ƿ�������֮ǰ��������ӵ���¼�
	};
	Extend(this.options, options || {});
  },
  //��ǰ��
  NowMonth: function() {
	this.PreDraw(new Date());
  },
  //��һ��
  PreMonth: function() {
	this.PreDraw(new Date(this.Year, this.Month - 2, 1));
	return this.Month;
  },
  //��һ��
  NextMonth: function() {
	this.PreDraw(new Date(this.Year, this.Month, 1));
	return this.Month;
  },
  //��һ��
  PreYear: function() {
	this.PreDraw(new Date(this.Year - 1, this.Month - 1, 1));
	return this.Year;
  },
  //��һ��
  NextYear: function() {
	this.PreDraw(new Date(this.Year + 1, this.Month - 1, 1));
	return this.Year;
  },
  //�������ڻ�����
  PreDraw: function(date) {
	//����������
	this.Year = date.getFullYear(); this.Month = date.getMonth() + 1;
	//���»�����
	this.Draw();
  },
  //������
  Draw: function() {
	//�������������б�
	var arr = [];
	//�õ��µ�һ����һ���е�����ֵ��Ϊ�������һ�������
	for(var i = 1, firstDay = new Date(this.Year, this.Month - 1, 1).getDay(); i <= firstDay; i++){ arr.push(0); }
	//�õ������һ����һ�����е�����ֵ��Ϊ���µ�����
	for(var i = 1, monthDay = new Date(this.Year, this.Month, 0).getDate(); i <= monthDay; i++){ arr.push(i); }
	//���ԭ�������ڶ����б�
	this.Days = [];
	//��������
	var frag = document.createDocumentFragment();
	var p = this;
	while(arr.length){
		//ÿ�����ڲ���һ��tr
		var row = document.createElement("tr");
		//ÿ��������7�� 
		for(var i = 1; i <= 7; i++){
			var cell = document.createElement("td"); cell.innerHTML = "&nbsp;";
			cell.className = 'daybg';
			if(arr.length){
				var d = arr.shift();
				if(d){
					this.Days[d] = cell;
					var on = new Date(this.Year, this.Month - 1, d);
					//���ó�ʼ��ɫ
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
						cell.title = this.Year + '��' + this.Month + '��' + d + '�� ' + (this.IsWorkDay(on)===true ? "������" : "�ǹ�����");
						var cName = 'normal';
						//�ж��Ƿ����
						if(this.IsSame(on, new Date())){
							cell.className = "today";
							cName = "today";
							cell.title = "������ "+ (this.IsWorkDay(on)===true ? "������" : "�ǹ�����");
						}
						var id = this.Year+(this.Month.toString().length==1 ? '0'+this.Month : this.Month.toString()) + (d.toString().length==1 ? '0'+d : d.toString());
						cell.innerHTML = '<div style="height:20px;"><div class="'+cName+'">'+d+'</div><div title='+(this.IsWorkDay(on)===true ? "������" : (this.IsWorkDay(on).length==0 ? "�ǹ�����" : this.IsWorkDay(on)))+' class="cworkday">'+ (this.IsWorkDay(on)===true ? "������" : (this.IsWorkDay(on).length==0 ? "�ǹ�����" : this.IsWorkDay(on)))+'</div></div>';
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
						// ִ�лص�
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
	//����������ٲ���(ie��table������innerHTML)
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
	//���ӳ���
	this.onFinish();
  },
  //�ж��Ƿ�ͬһ��
  IsSame: function(d1, d2) {
	return (d1.getFullYear() == d2.getFullYear() && d1.getMonth() == d2.getMonth() && d1.getDate() == d2.getDate());
  },
  // �ж��Ƿ��ǹ�����
  IsWorkDay : function(d1){
  	var specialDayArray;
  	for(var i = 0; i < this.specialDays.length; i++){
  		var specialDay = Calendar.parseDate(this.specialDays[i]);
  		// ����������ض�������
  		var containFlag = this.IsSame(d1, specialDay);
  		if(containFlag){
  			// ֻҪ�����������б�,���Ƿǹ�����
  			return this.specialDaysDesc[i];
  		}
  	}
  	
  	// ���������ض�������
  	return true;
  },
  // ������������
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
