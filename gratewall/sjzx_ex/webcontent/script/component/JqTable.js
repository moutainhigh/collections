/************************************************************************************
 * Ajax 表格
 * jQuery.js
 * 
 * @version V1.0.1
 * @ 2007-6-6
 * @author zhyi_12
 ***********************************************************************************/

var HccTable = new Object();
/*************************************************************************************/
HccTable.resource = new Object();
HccTable.resource.title = "";
/*************************************************************************************/
/**
 * 
 * @param aProperties 
 * @param oObjectXml  xml
 */
HccTable.create = function(dParentDom,src,aTableCols){
	this.parentDom = dParentDom;
	this.src = src;
	this.tableCols = aTableCols;
	this.init();
}

HccTable.create.prototype = {
	captureContainer:null,//
	
	bodyContainer:null,//
	
	footContainer:null,
	
	objectsXml:null,
	
	totalCount:0,
	
	pageSize:0,
	
	startIndex:0,
	
	orderBy:null,
	
	params:"&pageSize=10&pageIndex=1",
	
	selectDBObjects:new Array(),//the selected objects
	
	keys:new Array(),//the key objects
	
	init:function(){
		this.parentDom.innerHTML="";
		this.parentDom.className = "HccTabl_container";
		this.queryConainer = $("<div class=\"HccTable_query\"></div>");
		this.captureContainer = $("<div class=\"HccTable_capture\"></div>");
		this.bodyContainer = $("<div class=\"HccTable_body\"></div>");
		this.footContainer = $("<div class=\"HccTable_footer\"></div>");
		this.resultContainer = $("<div></div>");
		
		this.resultContainer.append(this.captureContainer);
		this.resultContainer.append(this.bodyContainer);
		this.resultContainer.append(this.footContainer);
		
		$(this.parentDom).append(this.queryConainer);
		$(this.parentDom).append(this.resultContainer);
		
		this.initQueryForm();
		this.loadSrc(this.src,this.params);
	},
	
	loadSrc:function(src,params){
		var p = this;
		if(params)src+=params;
		$.get(src,function(xml){
			var pageSize = xml.documentElement.getAttribute("pageSize");
			var startIndex = xml.documentElement.getAttribute("startIndex");
			var totalCount = xml.documentElement.getAttribute("totalCount");
			p.initTableBody(xml);
			p.initFooter(pageSize,startIndex,totalCount);
		});
	},
	
	makeParams:function(pageSize,pageIndex,orderBy){
		//var params = &pageSize=10&pageIndex=1&orderBy="";
		var params = "";
		if(pageSize)params+=this.addParam("pageSize",pageSize);
		if(pageIndex)params+=this.addParam("pageIndex",pageIndex);
		orderBy =(!orderBy)?this.orderBy:orderBy;
		if(orderBy)params+=this.addParam("orderBy",orderBy);
		return params;
	},
	
	addParam:function(param,value){
		var paramStr = "&";
		paramStr+=param;
		paramStr+="=";
		paramStr+=value;
		return paramStr;
	},
	
	initQueryForm:function(){
		var p = this;
		var queryForm = $("<form id=\"form\" methd=\"post\" action=\"\"></form>");
		var conditionContainer = $("<div align=\"center\" class=\"HccTable_query_conditionContainer\"></div>");
		
		this.addFormWidget(conditionContainer,"","NAME : ","name");
		this.addFormWidget(conditionContainer,"","PHONE : ","value");
		this.addFormWidget(conditionContainer,"","MAIL : ","email");
		
		var submitContainer = $("<div align=\"center\" class=\"HccTable_query_submit\"></div>");
		submitContainer.append("<input type=\"submit\" value=\"query\"/>");
		submitContainer.append("<input type=\"reset\" value=\"cancel\"/>");
		
		queryForm.append(conditionContainer);
		queryForm.append(submitContainer);
		this.queryConainer.append(queryForm);
		var options = { 
	        target:        p.resultContainer,   // target element(s) to be updated with server response 
	        beforeSubmit:  showRequest,  // pre-submit callback 
	        success:       showResponse // post-submit callback 
	 		//url:       "login.jsp"
	        // other available options: 
	        //url:       url         // override for form's 'action' attribute 
	        //type:      type        // 'get' or 'post', override for form's 'method' attribute 
	        //dataType:  null        // 'xml', 'script', or 'json' (expected server response type) 
	        //clearForm: true        // clear all form fields after successful submit 
	        //resetForm: true        // reset the form after successful submit 
	 
	        // $.ajax options can be used here too, for example: 
	        //timeout:   3000 
	    }; 
		
		queryForm.ajaxForm(options);
		function showRequest(formData, jqForm, options) { 
		    // formData is an array; here we use $.param to convert it to a string to display it 
		    // but the form plugin does this for you automatically when it submits the data 
		   var queryString = $.param(formData); 
		 
		    // jqForm is a jQuery object encapsulating the form element.  To access the 
		    // DOM element for the form do this: 
		    // var formElement = jqForm[0]; 
		 
		    alert('About to submit: \n\n'+queryString); 
		 
		    // here we could return false to prevent the form from being submitted; 
		    // returning anything other than false will allow the form submit to continue 
		    return true; 
		}
		
		function showResponse(responseText, statusText){ 
		    // for normal html responses, the first argument to the success callback 
		    // is the XMLHttpRequest object's responseText property 
		 
		    // if the ajaxForm method was passed an Options Object with the dataType 
		    // property set to 'xml' then the first argument to the success callback 
		    // is the XMLHttpRequest object's responseXML property 
		 
		    // if the ajaxForm method was passed an Options Object with the dataType 
		    // property set to 'json' then the first argument to the success callback 
		    // is the json data object returned by the server 
		    //alert('status: ' + statusText + '\n\nresponseText: \n' + responseText + 
		    //    '\n\nThe output div should have already been updated with the responseText.'); 
		}
		
	},
	
	addFormWidget:function(conditionContainer,widgetType,description,widgetName){
		var queryCondition = $("<div class=\"HccTable_query_condition\"></div>");
		queryCondition.append("<div class=\"HccTable_query_description\">"+description+"</div>");
		queryCondition.append("<div class=\"HccTable_query_widget\"><input name=\""+widgetName+"\" type=\"text\"/></div>");
		conditionContainer.append(queryCondition);
	},
	
	initTableBody:function(xml){
		this.bodyContainer.html("");
		var p = this;
		var table = document.createElement("TABLE");
		table.width = "100%";
		table.cellSpacing = 0;
		table.cellPadding = 0;
		var property; 
		var propertyDescription;
		var headRow,tableRow;
		var cell;
		var dBObjectNodes; // 
		var dBObjectNode;//
		
		headRow = table.insertRow(0);
		
		cell = headRow.insertCell(0);
		cell.className = "HccTable_body_head";
		cell.style.width = "20px";
		var checkBox = $("<input type=\"checkbox\" class=\"HccTable_body_checkbox\"/>");
		checkBox.click(function(){
			if(this.checked==true){
				$("input.HccTable_body_checkbox",table).not(this).each(function(){
					this.setAttribute("checked",true);
					p.selectDBObjects[this.getAttribute("rowKey")] = p.rowToObject(this.parentElement.parentElement);
				});
			}else{
				$("input.HccTable_body_checkbox",table).not(this).each(function(){
					this.setAttribute("checked",false);
					p.selectDBObjects[this.getAttribute("rowKey")] = null;
				});
			}
		});
		$(cell).append(checkBox);
		
		var colCounts = this.tableCols.length;//
		for(var col=0;col<colCounts;col++){
			var colObject = this.tableCols[col];
			property = colObject.getProperty();
			propertyDescription = colObject.getPropertyDescription();
			var width = colObject.getWidth();
			
			if(colObject.isPKey()){
				this.keys[property] = property;
			}
			//keys
			propertyDescription = (propertyDescription==null||propertyDescription=="")?property:propertyDescription;
			cell = headRow.insertCell(col+1);
			cell.className = "HccTable_body_head";
			var cellSort = $("<a property=\""+property+"\" href=\"javascript:void(0);\">"+propertyDescription+"</a>"); 
			$(cell).append(cellSort);
			if(!this.tableCols[col].isShow()){
				cell.style.display = "none";
			}
			cellSort.click(function(){
				p.orderBy = this.getAttribute("property");
				p.loadSrc(p.src,p.makeParams(p.pageSize,p.pageIndex,p.orderBy));
			});
			if(width)cell.style.width = width;
			width = null;
		}
		var row = 1;
		$("DBObjects/DBObject",xml).each(function(){
			tableRow = table.insertRow(row);
			var checkBoxcell = tableRow.insertCell(0);
			checkBoxcell.className = "HccTable_body_evenCell";
			//if(p.keys.)

			var rowkey="";// the key of the row
			for(var col=0;col<colCounts;col++){
				cell = tableRow.insertCell(col+1);
				property = p.tableCols[col].getProperty();
				
				if(row%2==0){
					cell.className = "HccTable_body_evenCell";
				}else{
					cell.className = "HccTable_body_oldCell";
				}
				if(!p.tableCols[col].isShow()){
					cell.style.display = "none";
				}
				var cellText = $(property,this).text();
				cellText = (cellText=="")?"&nbsp;":cellText;
				if(p.keys[property]){
					rowkey+=cellText;
				}
				cell.innerHTML = cellText;
				property = null;
			}
			//if(rowKey==""){
			//	rowKey = "row"+row;
			//}
			var checkBox; 
			if(p.selectDBObjects[rowkey]){
				checkBox = $("<input rowKey = \""+rowkey+"\" checked=\"true\" type=\"checkbox\" class=\"HccTable_body_checkbox\"/>");
			}else{
				checkBox = $("<input rowKey = \""+rowkey+"\" type=\"checkbox\" class=\"HccTable_body_checkbox\"/>");
			}
			
			checkBox.click(function(){
				if(this.checked==true){
					if(!window.event.ctrlKey){
						$("input.HccTable_body_checkbox",table).not(this).attr("checked",false);
						for(iRowkey in p.selectDBObjects){
							p.selectDBObjects[iRowkey] = null;
						}
					}
					p.selectDBObjects[rowkey] = p.rowToObject(this.parentElement.parentElement);
				}else{
					p.selectDBObjects[rowkey] = null;
				}
			});

			$(checkBoxcell).append(checkBox);
			row++;
		});
		
		while(row<=this.pageSize){
			tableRow = table.insertRow(row);
			cell = tableRow.insertCell(0);
			cell.innerHTML = "&nbsp;"
			cell.className = "HccTable_body_evenCell";
			for(var col=0;col<colCounts;col++){
				cell = tableRow.insertCell(col+1);
				cell.innerHTML = "&nbsp;";
				if(row%2==0){
					cell.className = "HccTable_body_evenCell";
				}else{
					cell.className = "HccTable_body_oldCell";
				}
				if(!p.tableCols[col].isShow()){
					cell.style.display = "none";
				}
			}
			row++;
		}
		row = null;
		
		this.bodyContainer.append($(table));
	},
	
	initFooter:function(pageSize,startIndex,totalCount){
		var p = this;
		this.pageSize = parseInt(pageSize);
		this.pageIndex = Math.ceil((parseInt(startIndex)+1)/pageSize);
		var pages = Math.ceil(totalCount/pageSize);
		
		var pageSizeWidget;
		pageSizeWidget = document.createElement("input");
		pageSizeWidget.type = "text";
		pageSizeWidget.className = "HccTable_footer_rightInput";
		pageSizeWidget.value = pageSize;
		pageSizeWidget.onkeydown = function(){
			if(window.event.keyCode==13){
				var newPageSize = 10;
				if(this.value)newPageSize = parseInt(this.value);
				p.pageSize = newPageSize;
				p.pageIndex = Math.ceil((parseInt(startIndex)+1)/p.pageSize);
				p.loadSrc(p.src,p.makeParams(newPageSize,p.pageIndex));
			}
		}
		
			
		var pageIndexWidget;
		if(pages<10){
			var pageIndexSelect = document.createElement("select");
			for(var index=1;index<=pages;index++){
				var oOption = document.createElement("option");
				oOption.text = index;
				oOption.value = index;
				pageIndexSelect.add(oOption);
			}
			pageIndexSelect.selectedIndex = this.pageIndex-1;
			pageIndexSelect.onchange = function(){
				var goIndex = this.options[this.selectedIndex].value;
				p.loadSrc(p.src,p.makeParams(pageSize,goIndex));
			}
			pageIndexWidget = pageIndexSelect;
		}else{
			pageIndexWidget = document.createElement("input");
			pageIndexWidget.type = "text";
			pageIndexWidget.value = this.pageIndex;
			pageIndexWidget.onkeydown = function(){
				if(window.event.keyCode==13){
					if(this.value>pages)this.value = pages;
					if(this.value<1)this.value = 1;
					var goIndex = parseInt(this.value);
					p.loadSrc(p.src,p.makeParams(pageSize,goIndex));
				}
			} 
		}
		pageIndexWidget.className="HccTable_footer_rightInput";
		
		var firstPage = $("<input type=\"button\" value=\"|<\" class=\"HccTable_footer_rightBut\"/>");
		var prePage =   $("<input type=\"button\" value=\"<\" class=\"HccTable_footer_rightBut\"/>");
		var nextPage = $("<input type=\"button\" value=\">\" class=\"HccTable_footer_rightBut\"/>");
		var lastPage = $("<input type=\"button\" value=\">|\" class=\"HccTable_footer_rightBut\"/>");
		
		firstPage.click(function(){
			if(p.pageIndex!=1){
				p.loadSrc(p.src,p.makeParams(pageSize,1));
			}
		});
		
		prePage.click(function(){
			var goIndex = p.pageIndex-1;
			if(goIndex>0){
				p.loadSrc(p.src,p.makeParams(pageSize,goIndex));
			}
		});
		
		nextPage.click(function(){
			var goIndex = p.pageIndex+1;
			if(goIndex<=pages){
				p.loadSrc(p.src,p.makeParams(pageSize,goIndex));
			}
		});
		
		lastPage.click(function(){
			if(p.pageIndex!=pages){
				p.loadSrc(p.src,p.makeParams(pageSize,pages));
			}
		});
		
		if(this.pageIndex==1){
			firstPage.attr("disabled","true");
			prePage.attr("disabled","true");
		}
		
		if(this.pageIndex==pages){
			lastPage.attr("disabled","true");
			nextPage.attr("disabled","true");
		}
		
		this.footContainer.html("");
		var leftFoot = $("<div class=\"HccTable_footer_left\"></div>");
		var rightFoot = $("<div class=\"HccTable_footer_right\"></div>");
		
		leftFoot.append("<span>\u5f53\u524d\u7b2c\u3010<span class=\"HccTable_footer_number\">"+this.pageIndex+"</span>\u3011\u9875/</span");
		leftFoot.append("<span>\u5171\u3010<span class=\"HccTable_footer_number\">"+pages+"</span>\u3011\u9875 </span");
		leftFoot.append("<span>\u8bb0\u5f55\u603b\u6570\u3010<span class=\"HccTable_footer_number\">"+totalCount+"</span>\u3011  </span");
		leftFoot.append("<span>\u8f6c\u5230<span class=\"HccTable_footer_number\">");
		leftFoot.append(pageIndexWidget);
		leftFoot.append("</span>\u9875  </span><span>\u6bcf\u9875\u663e\u793a<span class=\"HccTable_footer_number\">");
		leftFoot.append(pageSizeWidget);
		leftFoot.append("</span>\u6761</span>");

	
		rightFoot.append(firstPage);
		rightFoot.append(prePage);
		rightFoot.append(nextPage);
		rightFoot.append(lastPage);
		
		this.footContainer.append(leftFoot);
		this.footContainer.append(rightFoot);
	},
	
	rowToObject:function(tableRow){
		var object = new Object();
		for(var col=0;col<this.tableCols.length;col++){
			var colObject = this.tableCols[col];
			var property = colObject.getProperty();
			object[property] = tableRow.cells(col).innerHTML;
		}
		return object;
	}
}

HccTable.TableCol = new Object();

HccTable.TableCol = function(sProperty,sPropertyDescription,sWidth,bIsKey,bNotShow,oAction){
	this.property = sProperty;
	this.propertyDescription =sPropertyDescription;
	this.width = sWidth;
	this.action = oAction;
	this.notShow = bNotShow;
	this.isKey = bIsKey;
}

HccTable.TableCol.prototype = {
	property:null,
	
	propertyDescription:null,
	
	width:0,
	
	action:null,
	
	getProperty:function(){
		return this.property;
	},
	
	getPropertyDescription:function(){
		return this.propertyDescription;
	},
	
	getWidth:function(){
		return this.width;
	},
	
	getAction:function(){
		return this.action;
	},
	
	isShow:function(){
		if(this.notShow==true){
			return false;
		}else{
			return true;
		}
	},
	
	isPKey:function(){
		if(this.isKey==true){
			return true;
		}else{
			return false;
		}
	}
}