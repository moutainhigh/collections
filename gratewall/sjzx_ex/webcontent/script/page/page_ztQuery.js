var page_ztQuery = new Object();

page_ztQuery.create = function(config){
	this.ztSrc = config.ztSrc;
	this.areaSrc = config.areaSrc||"areaXml.do";
	this.timeType = config.timeType||"year";
	this.defaultZtnm = config.defaultZtnm;
	this.init();
}

page_ztQuery.create.prototype = {
	comboTimes:new Array(),
	
	pcBgqbs:new Array(),//pu cha bgqb
	
	init:function(){
		this.queryTypeInput = $("<input name=\"queryType\" type=\"hidden\"/>");
		this.timeTypeInput = $("<input name=\"timeType\" type=\"hidden\"/>");
		this.ztdyMcInput = $("<input name=\"ztMc\" type=\"hidden\"/>");
		
		$(document.forms["queryUtil"]).append(this.ztdyMcInput);
		$(document.forms["queryUtil"]).append(this.queryTypeInput);
		$(document.forms["queryUtil"]).append(this.timeTypeInput);
		
		this.initZtSelect();
		//this.initTime();
		//this.initArea();
	},
	
	initZtSelect:function(){
		var p = this;
		var ztSelect = $("select#page_ztQuery_ztSelect");
		
		$("input#page_ztQuery_submit").click(function(){
			p.querySubmit();
		});
		
		$.get(this.ztSrc,function(xml){
			var helpArray = new Array();
			$("Datas/data",xml).each(function(){
				//"ZT_NM","ZT_JS","ZT_MC"
				var ztNm = $("ZT_NM",this).text();
				var ztJs = $("ZT_JS",this).text();
				var ztMc = $("ZT_MC",this).text();
				var bgQb = $("BG_QB",this).text();
				
				helpArray["L"+ztNm]=ztJs;
				p.pcBgqbs["L"+ztNm]=bgQb;
				var oOption = $("<option value=\""+ztNm+"\">"+ztMc+"</option>");
				ztSelect.append(oOption);
				
				if(p.defaultZtnm!=null&&p.defaultZtnm==ztNm){
					//alert(ztNm);
				}else{
					oOption.removeAttr("selected");
				}
			});
			
			ztSelect.change(function(){
				var ztNm = this.value;
				//alert(ztNm);
				var ztJs = helpArray["L"+ztNm];
				var bgQb = p.pcBgqbs["L"+ztNm];
				if(p.timeType=="pc"){
					p.initPcTime(bgQb);
					p.initTimeAndArea(ztNm);
				}else{
					p.initTimeAndArea(ztNm,true);
				}
				
				//get indicator and groups
				
				$("span#show_zt_js").empty();
				$("span#show_zt_js").html(ztJs);
			});
			
			ztSelect.change();
		});
	},
	
	initArea:function(indicators,comboGroupId){
		var  p = this;
		var areaSelect = $("select#page_ztQuery_areaSelect");
		areaSelect.empty();
		var src = "indicatorYear.do?method=getXZQYXml&indicators="+indicators+"&comboGroupId="+comboGroupId;
		switch(this.timeType){
			case "year":
				src+='&dclbDm=1&bgqbDm=1';
				break;
			case "month":
				src+='&dclbDm=1&bgqbDm=4';
				break;
			case "quarter":
				src+='&dclbDm=1&bgqbDm=3';
				break;
			case "pc":
				break;
		}
		
		$.get(src,function(xml){
			$("Datas/data",xml).each(function(){
				var xzqyNm = $("XZQY_NM",this).text();
				var xzqyMc = $("XZQY_MC",this).text();
				var oOption = $("<option value=\""+xzqyNm+"\">"+xzqyMc+"</option>");
				areaSelect.append(oOption);
				oOption.removeAttr("selected");
			});
			
			if($("data",xml).size()==1){
				$("option",areaSelect).attr("selected","selected");
			}
		});
	},
	
	initTimeAndArea:function(zt_nm,initTime){
		//
		//alert(zt_nm);
		var p = this;
		var indicators="",comboGroupId="";
		var getZtdyZbsSrc = "getZtdyZbs.do?zt_nm="+zt_nm;
		var getZtdyFzsSrc  = "getZtdyFzs.do?zt_nm="+zt_nm;;
		
		$.get(getZtdyZbsSrc,function(zbXml){
			var fzid;
			var zbid;
			$('DYZB',zbXml).each(function(){
				zbid = $(this).text();
				if(zbid){
					indicators+=',';
					indicators+=zbid;
					zbid=null;
				}
			});
			
			if(indicators.length>1)indicators = indicators.substring(1);
			$.get(getZtdyFzsSrc,function(fzXml){
				var fzSet = {};
				$('DYFZ',fzXml).each(function(){
					fzid = $(this).text();
					
					//alert(fzXml.xml);
					if(fzid&&!fzSet[fzid]){
						fzSet[fzid] = fzid;
					}
				});
				
				for(var iSet in fzSet){
					comboGroupId+=',';
					comboGroupId+=iSet;
				}
				
				if(comboGroupId.length>1)comboGroupId = comboGroupId.substring(1);
				if(initTime==true)p.initTime(indicators,comboGroupId);
				p.initArea(indicators,comboGroupId);
			});
		});
		
	},
	
	initTime:function(indicators,comboGroupId){
		switch(this.timeType){
			case "year":
				this.initYearTime(indicators,comboGroupId);
				break;
			case "quarter":
				this.initQuarterTime(indicators,comboGroupId);
				break;
			case "month":
				this.initMonthTime(indicators,comboGroupId);
				break;
		}
	},
	
	addOption:function(select,value,text){
		var oOption = $("<option value=\""+value+"\">"+text+"</option>");
		var lastOption = $("option:last",select);
		var lastValue = lastOption.attr("value");
		//alert(value+":last:"+lastValue);
		var pre = lastOption;
		if(value&&value>lastValue){
			var insertFalg = true;
			$("option",select).each(function(){
				var iValue = this.value;
				if(iValue<value&&insertFalg==true){//
					oOption.insertBefore(this);
					insertFalg = false;
				}
			});
		}else{
			select.append(oOption);
		}
		oOption.removeAttr("selected");
		return oOption;
	},
	
	initYearTime:function(indicators,comboGroupId){
		var p = this;
		var timeSelect = $("select#page_ztQuery_timeSelect");
		timeSelect.empty();
		var src = "bgqXml.do?dclbDm=1&bgqbDm=1&indicators="+indicators+"&comboGroupId="+comboGroupId;
		//alert(src);
		$.get(src,function(xml){
			$("Datas/year",xml).each(function(){
				var yearItem = $("item",this);
				var timeMc = yearItem.attr("BGQ_MC");
				var timeId = yearItem.attr("BGQ_DM");
				p.addOption(timeSelect,timeId,timeMc);
			});
			
			timeSelect.change(function(){
				var count = 0;
				count = $("option[@selected]",this).size();
			});
		});
	},
	
	initMonthTime:function(indicators,comboGroupId){
		var p = this;
		this.disposeComboTimes();
		//this.initYear(begin,end);
		var monthSelect = $("select#page_ztQuery_monthSelect");
		var timeSelect = $("select#page_ztQuery_timeSelect");
		monthSelect.empty();
		timeSelect.empty();
		var src = "bgqXml.do?dclbDm=1&bgqbDm=4&indicators="+indicators+"&comboGroupId="+comboGroupId;
		
		$.get(src,function(xml){
			$("Datas/year",xml).each(function(){
				var id = this.getAttribute("id");
				p.addOption(timeSelect,id,id+"\u5e74");
				var itemArray = new Array();
				$("item",this).each(function(){
					var timeMc = $(this).attr("BGQ_MC");
					var timeId = $(this).attr("BGQ_DM");
					itemArray[itemArray.length] = {
						timeMc:timeMc,
						timeId:timeId
					};
				});
				p.comboTimes["L"+id] = itemArray;
			});	
			try{
				var initYear = $("option:first",timeSelect).attr("value");
				p.initTimeItemSelect(monthSelect,initYear);
				monthSelect.attr("year",initYear);
				$("option:first",timeSelect).attr("selected","selected");
			}catch(err){
				//alert();
			}
			timeSelect.change(function(){
				p.initTimeItemSelect(monthSelect,this.value);
				monthSelect.attr("year",this.value);
				var itemArray = new Array();
				itemArray = p.comboTimes["L"+this.value];
				
				for(var i=0;i<itemArray.length;i++){
					if(itemArray[i].selected==true){
						var timeId = itemArray[i].timeId;
						$("option",monthSelect).each(function(){
							if(this.value==timeId){
								try{
									this.selected = "selected";
								}catch(err){
									
								}
							}
						});
					}
				}
			});
			
			monthSelect.change(function(){
				var year = $(this).attr("year");
				var itemArray = new Array();
				
				itemArray = p.comboTimes["L"+year];
				for(var i=0;i<itemArray.length;i++){
					itemArray[i].selected = false;
					var timeId = itemArray[i].timeId;
					
					$("option[@selected]",this).each(function(){
						if(this.value==timeId){
							itemArray[i].selected = true;
						}
					});
				}
			});
		});
	},
	
	initQuarterTime:function(indicators,comboGroupId){
		var p = this;
		this.disposeComboTimes();
		var quarterSelect = $("select#page_ztQuery_quarterSelect")
		var timeSelect = $("select#page_ztQuery_timeSelect");
		quarterSelect.empty();
		timeSelect.empty();
		var src = "bgqXml.do?dclbDm=1&bgqbDm=3&indicators="+indicators+"&comboGroupId="+comboGroupId;
		
		$.get(src,function(xml){
			$("Datas/year",xml).each(function(){
				var id = this.getAttribute("id");
				p.addOption(timeSelect,id,id+"\u5e74");
				
				var itemArray = new Array();
				$("item",this).each(function(){
					var timeMc = $(this).attr("BGQ_MC");
					var timeId = $(this).attr("BGQ_DM");
					itemArray[itemArray.length] = {
						timeMc:timeMc,
						timeId:timeId
					};
				});
				p.comboTimes["L"+id] = itemArray;
			});
			
			try{
				var initYear = $("option:first",timeSelect).attr("value");
				p.initTimeItemSelect(quarterSelect,initYear);
				quarterSelect.attr("year",initYear);
				$("option:first",timeSelect).attr("selected","selected");
			}catch(err){
				//alert();
			}
			
			quarterSelect.change(function(){
				var year = $(this).attr("year");
				var itemArray = new Array();
				itemArray = p.comboTimes["L"+year];
				for(var i=0;i<itemArray.length;i++){
					itemArray[i].selected = false;
					var timeId = itemArray[i].timeId;
					
					$("option[@selected]",this).each(function(){
						if(this.value==timeId){
							itemArray[i].selected = true;
						}
					});
				}
			});
			
			timeSelect.change(function(){
				p.initTimeItemSelect(quarterSelect,this.value);
				quarterSelect.attr("year",this.value);
				var itemArray = new Array();
				itemArray = p.comboTimes["L"+this.value];
				for(var i=0;i<itemArray.length;i++){
					if(itemArray[i].selected==true){
						var timeId = itemArray[i].timeId;
						$("option",quarterSelect).each(function(){
							if(this.value==timeId){
								try{
									this.selected = "selected";
								}catch(err){
									
								}
							}
						});
					}
				}
			});
		});
	},
	
	initPcTime:function(pbBgqb){
		var p = this;
		var src = "getPcBgq.do?bgqbDm="+pbBgqb;
		
		var timeSelect = $("select#page_ztQuery_timeSelect");
		
		timeSelect.empty();
		$.get(src,function(xml){
			$("Datas/data",xml).each(function(){
				var bgqDm = $("BGQ_DM",this).text(); //"BGQ_DM","BGQ_MC","BGQ_SSND"
				var bgqMc = $("BGQ_MC",this).text();
				var bgqSsnd = $("BGQ_SSND",this).text();
				
				var showText = bgqMc+"("+bgqSsnd+")";
				var oOption = $("<option>"+showText+"</option>");
				oOption.attr("value",bgqDm);
				timeSelect.append(oOption);
				oOption.removeAttr("selected");
			});
		});
	},
	
	disposeComboTimes:function(){
		this.comboTimes = new Array();
	},
	
	initTimeItemSelect:function(select,year){
		select.empty();
		var itemArray = new Array();
		itemArray = this.comboTimes["L"+year];
		for(var i=0;i<itemArray.length;i++){
			var timeId = itemArray[i].timeId;
			var timeMc = itemArray[i].timeMc;
			this.addOption(select,timeId,timeMc);
		}
	},
	
	querySubmit:function(){
		var queryXml;
		queryXml = this.getQueryXml();
		var p = this;
		if(queryXml){
			$.ajax({
				type		: "POST",
	  			url			: "ztQueryXml.do",
	  			dataType	: "xml",
	  			success		:  function(data){
	  							 if($("error",data).size()>0){
	  							 	alert($("error",data).text());//print the error
	  							 }else{
	  							 	 $("input#page_queryXml").attr("value",data.xml);
	  							 	 p.queryTypeInput.attr("value","query");
									 p.timeTypeInput.attr("value",p.timeType);
									 //alert($("option[@selected]",$("select#page_ztQuery_ztSelect")).html());
									 p.ztdyMcInput.attr("value",$("option[@selected]",$("select#page_ztQuery_ztSelect")).html());
									 document.forms["queryUtil"].submit();
	  							 }
	  						   },
	  			data		:queryXml,
	  			processData :false
			});
		}
	},
	
	getQueryXml:function(){
		var queryXml;
		var ztNm = $("option[@selected]",$("select#page_ztQuery_ztSelect")).attr("value");
		if(!ztNm){
			return;
		}
		if(window.XMLHttpRequest){
			queryXml=new XMLDOM();
		}else if(window.ActiveXObject){
			queryXml=new ActiveXObject("Microsoft.XMLDOM");
		}
		
		var xmlHead = queryXml.createProcessingInstruction("xml"," version=\"1.0\"");
		queryXml.insertBefore(xmlHead,queryXml.firstChild);
		
		var rootElement = queryXml.createElement('query');
		queryXml.appendChild(rootElement);
		
		rootElement.setAttribute("ztNm",ztNm);
		
		var areaSelect = $("select#page_ztQuery_areaSelect");
		var areasElement = queryXml.createElement('areas');
		rootElement.appendChild(areasElement);
		if($("option[@selected]",areaSelect).size()==0){
			//notSelect_area_errorMessage:"\u672a\u9009\u62e9\u5730\u533a!",
			alert("\u672a\u9009\u62e9\u5730\u533a!");
			return;
		}else{
			$("option[@selected]",areaSelect).each(function(){
				var areaId = this.value;
				var areaMc = $(this).html();
				areaElement = queryXml.createElement('area');
				areaElement.setAttribute("areaId",areaId);
				areaElement.setAttribute("areaMc",areaMc);
				areasElement.appendChild(areaElement);
			});
		}
		
		var timesElement = queryXml.createElement('times');
		rootElement.appendChild(timesElement);
		var timeSelect = $("select#page_ztQuery_timeSelect")
		if(this.timeType=="year"||this.timeType=="pc"){
			if($("option[@selected]",timeSelect).size()==0){
				alert("\u672a\u9009\u62e9\u65f6\u95f4!");
				return;
			}else{
				$("option[@selected]",timeSelect).each(function(){
					var timeId = this.value;
					var timeMc = this.text;
					timeElement = queryXml.createElement('time');
					timeElement.setAttribute("timeId",timeId);
					timeElement.setAttribute("timeMc",timeMc);
					timesElement.appendChild(timeElement);
				});
			}
		}else{
			var timeExsitFlag = false;
			for(var iYear in this.comboTimes){
				if(iYear!="indexOf"){
					var year = iYear.substring(1);
					var itemArray = new Array();
					itemArray = this.comboTimes[iYear];
					for(var i=0;i<itemArray.length;i++){
						if(itemArray[i].selected==true){
							if(!timeExsitFlag)timeExsitFlag = true;
							var timeId = itemArray[i].timeId;
							var timeMc = year+"\u5e74"+itemArray[i].timeMc;
							var timeElement = queryXml.createElement('time');
							timeElement.setAttribute("timeId",timeId);
							timeElement.setAttribute("timeMc",timeMc);
							timesElement.appendChild(timeElement);
						}
					}
				}
			}
			
			if(!timeExsitFlag){
				//notSelect_time_errorMessage:"\u672a\u9009\u62e9\u65f6\u95f4!",
				alert("\u672a\u9009\u62e9\u65f6\u95f4!");
				return;
			}
		}
		
		return queryXml;
	}
}