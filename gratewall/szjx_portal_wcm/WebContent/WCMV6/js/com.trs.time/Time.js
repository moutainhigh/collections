$package('com.trs.time');
$importCSS('com.trs.time.resource.time');

com.trs.time.Time = Class.create();
com.trs.time.Time.Count = 0;
com.trs.time.Time.prototype = {
	initialize : function(timeDesc, hourId, minuteId, secondId){
		this.index = ++com.trs.time.Time.Count;
		this.timeDesc = timeDesc || '时间';
		this.hourId = hourId || 'hour' + this.index;
		this.minuteId = minuteId || 'minute' + this.index;
		this.secondId = secondId || 'second' + this.index;
		
		if(hourId == undefined && minuteId == undefined && secondId == undefined){
			this.needHour = this.needMinute = this.needSecond = true;
		}else{
			this.needHour = (hourId ? true : false);
			this.needMinute = (minuteId ? true : false);
			this.needSecond = (secondId ? true : false);
			this.setHTML();
		}
	},

	setTimeDesc : function(timeDesc){
		$('timeDesc' + this.index).innerHTML = timeDesc + ':';
	},

	setValue : function(type, value){
		type = type.toLowerCase();
		var inputArray = $('timeComponent' + this.index).getElementsByTagName("input");
		if(type == 'h'){
			inputArray[0].value = value;
		}else if(type == 'm'){
			inputArray[1].value = value;
		}else{
			inputArray[2].value = value;
		}
	},

	setId : function(type, id){
		type = type.toLowerCase();
		if(!$('timeComponent' + this.index)) return;
		var inputArray = $('timeComponent' + this.index).getElementsByTagName("input");
		if(type == 'h' && this.needHour){
			inputArray[0].setAttribute("id", id);
		}else if(type == 'm' && this.needMinute){
			inputArray[1].setAttribute("id", id);
		}else if(this.needSecond){
			inputArray[2].setAttribute("id", id);
		}	
	},

	setHTML : function(){
		var tempStr = '<table border="0" cellspacing="0" cellpadding="0" id="timeComponent' + this.index + '" class="timeComponent">'
			+ '<tr valign="middle">'
			+ 	'<td class="Condition" align="right" id="timeDesc' + this.index + '">'
			+		this.timeDesc + ':'
			+	'</td>';

		if(this.needHour)
			tempStr += 
				'<td class="Condition" align="right" width="2">'
			+		'<input id="' + this.hourId + '" class="Combobox" maxlength=2 size=2 value="00" onkeyup="if (!$time.dataIsRight(this.value,\'H\')) this.value=this.defaultValue;">'
			+	'</td>'
			+	'<td class="Condition" align="left" width="2">'
			+		'<label UNSELECTABLE onmousedown="$time.setTime(\'up\',\'H\', ' + this.index + ', this);" onmouseup="$time.clearHandler(this);"><center>5</center></label>'
			+		'<label UNSELECTABLE onmousedown="$time.setTime(\'down\',\'H\', ' + this.index + ', this);" onmouseup="$time.clearHandler(this);"><center>6</center></label>'
			+	'</td>';
		if(this.needMinute)
			tempStr += 
				'<td class="Condition" align="right" width="1">:</td>'
			+	'<td class="Condition" align="right" width="2">'
			+		'<input id="' + this.minuteId + '" class="Combobox" maxlength=2 size=2 value="00" onkeyup=" if (!$time.dataIsRight(this.value,\'M\')) this.value=this.defaultValue;">'
			+	'</td>'
			+	'<td class="Condition" align="right" width="2">'
			+		'<label UNSELECTABLE  onmousedown="$time.setTime(\'up\',\'M\', ' + this.index + ', this);" onmouseup="$time.clearHandler(this);"><center>5</center></label>'
			+		'<label UNSELECTABLE  onmousedown="$time.setTime(\'down\',\'M\', ' + this.index + ', this);" onmouseup="$time.clearHandler(this);"><center>6</center></label>'
			+	'</td>';
		if(this.needSecond)
			tempStr += 
				'<td class="Condition" align="right" width="1">:</td>'
			+	'<td class="Condition" align="right" width="2">'
			+		'<input id="' + this.secondId + '" class="Combobox" maxlength=2 size=2 value="00" onkeyup="if (!$time.dataIsRight(this.value,\'S\')) this.value=this.defaultValue;">'
			+	'</td>'
			+	'<td class="Condition" align="right" width="2">'
			+		'<label UNSELECTABLE  onmousedown="$time.setTime(\'up\',\'S\', ' + this.index + ', this);" onmouseup="$time.clearHandler(this);"><center>5</center></label>'
			+		'<label UNSELECTABLE  onmousedown="$time.setTime(\'down\',\'S\', ' + this.index + ', this);" onmouseup="$time.clearHandler(this);"><center>6</center></label>'
			+	'</td>';

		tempStr += '</tr></table>';
		this.html = tempStr;
	},
	draw : function(){
		document.write(this.getHTML());
	},
	getHTML : function(){
		return this.html;	
	}
};

com.trs.time.Time.dataIsRight = function(theData){
    if (isNaN(theData)){
        return false;
    }else{
        if (theData.indexOf(".")!=-1)
         return false;
        if (parseInt(theData)<0)
        return false;
        if (arguments[1]!=null){
            if (arguments[1]=="H"){
                if (parseInt(theData)>23)
                    return false;
            }else if (arguments[1]=="M" || arguments[1]=="S"){
                if (parseInt(theData)>59)
                    return false;
            }
        }
    }
    return true;
};	

com.trs.time.Time.setTime = function(direction, type, index, labelObj){
	if(event.button == 2)
		return;
	labelObj.setCapture();
	com.trs.time.Time.setTimeAction(direction, type, index);
	onIntervalHandle=window.setInterval("com.trs.time.Time.setTimeAction('" + direction + "', '" + type + "', " + index + ")", 150);
};

com.trs.time.Time.setTimeAction = function (){
    var inputArray = $('timeComponent' + arguments[2]).getElementsByTagName("input");
    var oTemp;
    try{
        if (arguments[1]=="H"){
            theValue=parseFloat(inputArray[0].value);
            oTemp=inputArray[0];
        }else if (arguments[1]=="M"){
            theValue=parseFloat(inputArray[1].value);
            oTemp=inputArray[1];
        }else{
            theValue=parseFloat(inputArray[2].value);
            oTemp=inputArray[2];
        }
        if (arguments[0]=="up"){
            theValue=theValue+1;
            if (arguments[1]=="H")
                theValue=(theValue>23)?00:theValue;
            else
                theValue=(theValue>59)?00:theValue;
        }else{
            theValue=theValue-1;
            if (arguments[1]=="H")
                theValue=(theValue<0)?23:theValue;
            else
                theValue=(theValue<0)?59:theValue;
        }
        oTemp.value=(theValue<10)?("0"+theValue):theValue;	
    }catch (ex){}
};

com.trs.time.Time.clearHandler = function(labelObj){
	labelObj.releaseCapture();
	window.clearInterval(onIntervalHandle);
};

var $time = com.trs.time.Time;