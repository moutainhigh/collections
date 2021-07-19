var DateUtil = {
	makeYearSelect:function(select,begin,end){
		var jSelect = $(select);
		jSelect.empty();
		var date = new Date();
		if(!end){
			end = date.getFullYear();
		}
		for(var i=begin;i<=end;i++){
			var oOption = $("<option>"+i+"</option>");
			oOption.attr("value",i);
			select.prepend(oOption);
			oOption.removeAttr("selected");
		}
	},
	
	makeMonthSelect:function(select,addText){
		var jSelect = $(select);
		for(var i=1;i<13;i++){
			var oOption = $("<option>"+i+"</option>");
			if(addText)oOption.append(addText);
			if(i<10){
				oOption.attr("value","00"+i+"0");
			}else{
				oOption.attr("value","0"+i+"0");
			}
			select.append(oOption);
			
			oOption.removeAttr("selected");
			oOption.attr("month",i);
		}
	},
	
	makeQuarterSelect:function(select,addText){
		var jSelect = $(select);
		for(var i=1;i<5;i++){
			var oOption = $("<option>"+i+"</option>");
			if(addText)oOption.append(addText);
			oOption.attr("value",i+"000");
			oOption.attr("quarter",i);
			select.append(oOption);
			oOption.removeAttr("selected");
		}
	},
	
	makeTimes:function(bgqBegin,bgqEnd,timeType){
		switch(timeType){
			case "month":
				return DateUtil.makeMonthTimes(bgqBegin,bgqEnd);
				break;
			case "quarter":
				return DateUtil.makeQuarterTimes(bgqBegin,bgqEnd);
				break;
		}
	},
	
	makeMonthTimes:function(bgqBegin,bgqEnd){
		var times = new Array();
		var beginYear = parseInt(bgqBegin.substring(0,4));
		var endYear = parseInt(bgqEnd.substring(0,4));
		var beginMonth = parseInt(bgqBegin.substring(4));
		var endMonth = parseInt(bgqEnd.substring(4));
		//alert(beginYear+","+endYear+","+beginMonth+","+endMonth);
		if(beginYear==endYear){
			if(beginMonth>endMonth){
				var temp = beginMonth;
				beginMonth = endMonth;
				endMonth = temp;
			}
			
			for(var iMonth=beginMonth;iMonth<=endMonth;iMonth++){
				DateUtil.makeMonthObject(beginYear,times,iMonth);
			}
		}else{
			for(var iYear = beginYear;iYear<=endYear;iYear++){
				if(iYear==beginYear){
					for(var iMonth=beginMonth;iMonth<=12;iMonth++){
						DateUtil.makeMonthObject(iYear,times,iMonth);
					}
				}else if(iYear==endYear){
					for(var iMonth=1;iMonth<=endMonth;iMonth++){
						DateUtil.makeMonthObject(iYear,times,iMonth);
					}
				}else{
					for(var iMonth=1;iMonth<=12;iMonth++){
						DateUtil.makeMonthObject(iYear,times,iMonth);
					}
				}
			}
		}
		
		return times;
	},
	
	makeMonthObject:function(year,times,iMonth){
		var timeId="";
		var timeMc="";
		timeId+=year;
		if(iMonth<10){
			timeId+="00";
			timeId+=iMonth;
		}else{
			timeId+="0";
			timeId+=iMonth;
		}
		
		timeId+="0";
		timeMc = year+"\u5e74"+iMonth+"\u6708";
		times[times.length] = {
			timeId:timeId,
			timeMc:timeMc
		}
	},
	
	makeQuarterTimes:function(bgqBegin,bgqEnd){
		var times = new Array();
		var beginYear = parseInt(bgqBegin.substring(0,4));
		var endYear = parseInt(bgqEnd.substring(0,4));
		var beginQuarter = parseInt(bgqBegin.substring(4));
		var endQuarter = parseInt(bgqEnd.substring(4));
		//alert(beginYear+","+endYear+","+beginMonth+","+endMonth);
		if(beginYear==endYear){
			if(beginQuarter>endQuarter){
				var temp = beginQuarter;
				beginQuarter = endQuarter;
				endQuarter = temp;
			}
			
			for(var iQuarter=beginQuarter;iQuarter<=endQuarter;iQuarter++){
				DateUtil.makeQuarterObject(beginYear,times,iQuarter);
			}
		}else{
			for(var iYear = beginYear;iYear<=endYear;iYear++){
				if(iYear==beginYear){
					for(var iQuarter=beginQuarter;iQuarter<=4;iQuarter++){
						DateUtil.makeQuarterObject(iYear,times,iQuarter);
					}
				}else if(iYear==endYear){
					for(var iQuarter=1;iQuarter<=endQuarter;iQuarter++){
						DateUtil.makeQuarterObject(iYear,times,iQuarter);
					}
				}else{
					for(var iQuarter=1;iQuarter<=4;iQuarter++){
						DateUtil.makeQuarterObject(iYear,times,iQuarter);
					}
				}
			}
		}
		
		return times;
	},
	
	makeQuarterObject:function(year,times,iQuarter){
		var timeId="";
		var timeMc="";
		timeId = year+""+iQuarter+"000";
		timeMc = year+"\u5e74"+iQuarter+"\u5b63\u5ea6";
		times[times.length] = {
			timeId:timeId,
			timeMc:timeMc
		}
		
	}
}