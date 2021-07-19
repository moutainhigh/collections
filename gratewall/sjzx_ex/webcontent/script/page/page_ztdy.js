/**
* 时间对象的格式化;
*/
Date.prototype.format = function(format){
 /*
  * eg:format="YYYY-MM-dd hh:mm:ss";
  */
 var o = {
  "M+" :  this.getMonth()+1,  //month
  "d+" :  this.getDate(),     //day
  "h+" :  this.getHours(),    //hour
      "m+" :  this.getMinutes(),  //minute
      "s+" :  this.getSeconds(), //second
      "q+" :  Math.floor((this.getMonth()+3)/3),  //quarter
      "S"  :  this.getMilliseconds() //millisecond
   }
  
   if(/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
   }
 
   for(var k in o) {
    if(new RegExp("("+ k +")").test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    }
   }
 return format;
}

/**
 * 专题定义页面对象
 */
var ztdyPage = new Object();
/**
 * 资源对象
 */
ztdyPage.Resource={
	notSelect_indicator_errorMessage:"\u672a\u9009\u62e9\u6307\u6807!",
	notSelect_time_errorMessage:"\u672a\u9009\u62e9\u65f6\u95f4!",
	notSelect_area_errorMessage:"\u672a\u9009\u62e9\u5730\u533a!",
	move_rowToCol_errorMessage:"\u4e3b\u680f\u4e2d\u81f3\u5c11\u4fdd\u7559\u4e00\u9879!",
	move_colToRow_errorMessage:"\u5bbe\u680f\u4e2d\u81f3\u5c11\u4fdd\u7559\u4e00\u9879!",
	data_count_errorMessage:"\u6700\u591a\u53ef\u9009\u62e91000\u4e2a\u6570\u636e\u5355\u5143!",
	notCommon_group_errorMessage:"\u6ca1\u6709\u5171\u540c\u5206\u7ec4!",
	hasSelected_groupItem_message:"\u4e13\u9898\u6307\u6807\u5df2\u5173\u8054\u5206\u7ec4\uff0c\u589e\u52a0\u6307\u6807\u5c06\u6e05\u7a7a\u6240\u6709\u5df2\u5173\u8054\u5206\u7ec4\uff0c\n\u5e76\u9700\u8981\u91cd\u65b0\u5173\u8054\u5206\u7ec4\u3002\u589e\u52a0\u6307\u6807\u8bf7\u70b9\u51fb\u3010\u786e\u8ba4\u3011\u6309\u94ae\uff0c\n\u653e\u5f03\u589e\u52a0\u8bf7\u70b9\u51fb\u3010\u53d6\u6d88\u3011\u6309\u94ae",
	//专题指标已关联分组，增加指标将清空所有已关联分组，并需要重新关联分组。增加指标请点击【确认】按钮，放弃增加请点击【取消】按钮
	group_no_groupItemSelected:"\u590d\u5408\u5206\u7ec4\u4e2d\u6709\u5206\u7ec4\u6ca1\u6709\u9009\u62e9\u5206\u7ec4\u9879\uff0c\u4e0d\u80fd\u4fdd\u5b58!",
	//复合分组中有分组没有选择分组项，不能保存!
	delete_confirm_message:"\u786e\u8ba4\u5220\u9664\u9009\u62e9\u7684\u4e13\u9898!",//确认删除选择的专题
	edit_ztdy_title:"\u7f16\u8f91\u4e13\u9898",//编辑专题
	notSelect_group:"\u6ca1\u6709\u9009\u62e9\u5206\u7ec4\uff0c\u786e\u8ba4\u7ee7\u7eed\uff1f",
	goFirstImg:"images/table/button_begins.gif",
	goNextImg:"images/table/button_nexts.gif",
	goPrevImg:"images/table/button_prvs.gif",
	goLastImg:"images/table/button_ends.gif"
};
/**
 * 配置对象
 */
ztdyPage.Config = {
	fbzdTextAttributeName:'FBZD_MC',
	fbzdValueAttributeName:'FBZD_DM',
	groupPrefix:'group_',
	groupItemPrefix:'gitem_'
};
/**
 * 构造函数
 */
ztdyPage.create = function(config){
	this.fbzdSrc = config.fbzdSrc;
	this.dclbBgqbCombArray = config.dclbBgqbCombArray;
	this.timeType = (!config.timeType)?"year":config.timeType;
	this.init();
}
/**
 * 原型属性和方法
 */
ztdyPage.create.prototype = {
	fbzdSelect:null,//发布制度下拉框
	
	dclbSelect:null,//调查类别下拉框
	
	bgdbSelect:null,//报告期别下拉框
	
	indicatorTree:null,//指标树
	
	indicatorTreeContainer:null,//树容器
	
	indicatorCounts:0,//选择的指标数
	
	dclbBgqbCombArray:new Array(),//报告期别和调查类别的复合信息对象数组
	
	comboGroups:new Array(),//分组分组项的复合信息对象数组
	
	initComboGroupsFlag:true,//是否更新分组和分组项
	
	pcBgqbs :new Array(),
	
	columns:new Array(),
	
	dataArray:new Array(),
	
	isUpdate:false,//是否是更新专题状态
	
	editGroupArray:new Array(),//用于存储修改的专题对应的分组和分组项，通过该数组来反显示专题对应的分组
	
	/**
	 * 初始化
	 */
	init:function(){
		this.initFormDclbSelect();
		this.initTableHead();
		this.initTableFoot();
		this.initForm();
		this.fbzdSelect = $("select#indicator_dczd_select");
		this.dclbSelect = $("select#select_dclb");
		this.bgqbSelect = $("select#select_bgqb");
		
		this.indicatorTreeContainer = $("div#indicator_tree_container");
		this.indicatorSelectedSelect = $("select#indicator_selected_select");
		this.groupSelect = $("select#group_selected_select");
		//this.initFbzdSelect();
		this.initDclbSelect();//
		this.indicatorCounts = 0;
		var p = this;
		/**
		$("img#group_panel_bodyExpand").click(function(){
			if($("div#group_panel_body").css("display")=="none"){
				p.expandGroup();
			}else{
				p.collapseGroup();
			}
		});
		*/
		
		$("div#div_group_panel_bodyExpand").click(function(){
			if($("div#group_panel_body").css("display")=="none"){
				p.expandGroup();
			}else{
				p.collapseGroup();
			}
		});			
		
		this.groupSelect.change(function(){
			p.initGroupSelects(this.value);
		});
		
		$("input#page_save").click(function(){
			if(window.confirm("\u786e\u8ba4\u4fdd\u5b58\u4e13\u9898?")){
				if(p.postSubmitData()){
					$("iframe#coverIframe").css("display","none");
					$("div#editWindow").hide();
					$("div#dp-popup").hide();
					$("form[@name=ztdyForm]").submit();//再次查询数据
				}
			}
		});
		
		$("input#page_reset").click(function(){
			p.resetSubmitForm();
		});
		
		$("input#add_ztdy[@type=button]").click(function(){
			p.showEditZtdy();
		});
		
		$("input#modify_ztdy[@type=button]").click(function(){
			try{
				p.showEditZtdy("modify");
			}catch(err){
				$("iframe#coverIframe").css("display","none");
				$("div#editWindow").hide();
				$("div#dp-popup").hide();
				$("form[@name=ztdyForm]").submit();//再次查询数据
				$("form[@name=ztdyForm]").submit();
			}
		});
		
		$("input#delete_ztdy[@type=button]").click(function(){
			if(window.confirm(ztdyPage.Resource.delete_confirm_message)){//确认删除选择的专题
				p.deleteZtdy();
			}
		});
		
		$("div#editWindow").PopWindow({
			caption:ztdyPage.Resource.edit_ztdy_title,
			content:$("div#editZtdy"),
			dragHandleId:"ztdy",
			onClose:function(){
				$("div#dp-popup").hide();
				$("iframe#coverIframe").css("display","none");
				var handleValue = $("input[@type=hidden][@name=changeHandle]").attr("value");
				handleValue = parseInt(handleValue)+1;
				$("input[@type=hidden][@name=changeHandle]").attr("value",handleValue);
				$("form[@name=ztdyForm]").submit();//再次查询数据
			}
		});
	},
	/**
	 * 加载专题信息
	 */
	loadZtdy:function(){
		
	},
	/**
	 * 打开专题定义面板
	 */
	showEditZtdy:function(type){
		var p = this;
		$("iframe#coverIframe").css("display","block");
		$("div#editWindow").show();
		$("div#editWindow").css("width","800px");
		$("div#editWindow").css("height","600px");
		$("div#editWindow").css("left","25px");
		$("div#editWindow").css("top","10px");
		$("div#editWindow").css("zIndex","2");
		this.editGroupArray = new Array();
		this.initComboGroupsFlag = true;
		$("div#groupItem_select_panel").empty();
		if(type=="modify"){
			this.isUpdate = true;
			var selectedRowKey = $("input[@type=checkbox][@checked]").attr("rowKey");
			var ztdyData = this.dataArray[selectedRowKey];
			var zt_nm = selectedRowKey.substring(1);
			var zt_xh = ztdyData.ZT_XH;
			var zt_mc = ztdyData.ZT_MC;
			var ztDclb = ztdyData.DC_LB;
			var ztBgqb = ztdyData.BG_QB;
			var zt_zdr = ztdyData.ZT_ZDR;
			var zt_zxfbsj = ztdyData.ZT_ZXFBSJ;
			var zt_js = ztdyData.ZT_JS;
			var dy_jg = ztdyData.DY_JG;
			
			var parentForm = $("div#baseInfo_panel");
			this.setTextFiled("input","zt_xh",zt_xh,parentForm);
			this.setTextFiled("input","zt_mc",zt_mc,parentForm);
			this.setTextFiled("input","zt_zdr",zt_zdr,parentForm);
			this.setTextFiled("input","zt_zxfbsj",zt_zxfbsj,parentForm);
			this.setTextFiled("input","dy_jg",dy_jg,parentForm);
			this.setTextFiled("textarea","zt_js",zt_js,parentForm);
			
			$("option[@value="+ztDclb+"]",$("select#select_dclb")).attr("selected","selected");
			$("select#select_dclb").change();
			
			$("option[@value="+ztBgqb+"]",$("select#select_bgqb")).attr("selected","selected");

			//根据专题内码查找对应的指标
			var getDyZbSrc = "getZtdyZbs.do?zt_nm="+zt_nm;
			var fbzdDm = null;
			
			$.get(getDyZbSrc,function(xml){
				var fbzdDm = $("Datas/data/FBZD_FJD:first",xml).text();
				
				var fbzdMc = $("option[@value="+fbzdDm+"]",p.fbzdSelect).html();
				//alert("\u76f8\u5e94\u7684\u53d1\u5e03\u5236\u5ea6:"+fbzdMc);
				
				p.indicatorSelectedSelect.empty();
				$("Datas/data",xml).each(function(){
					fbzdDm = $("FBZD_FJD",this).text();//指标对应的制度为子制度，需要的是制度
					var zb_bb = $("ZB_BB",this).text();
					var zb_id = $("DYZB",this).text();
					var zb_cn_qc = $("ZB_CN_QC",this).text();
					p.addOption(p.indicatorSelectedSelect,zb_bb+"/"+zb_id,zb_cn_qc);
				});
				
				p.setIndicatorCount();
				p.collapseGroup();
				
				p.initIndicatorTree(fbzdDm,fbzdMc,true);//初始化指标树，不清空已经选择的指标
				p.disableRelative();
				$("option[@value="+fbzdDm+"]",p.fbzdSelect).attr("selected","selected");
			})
			//根据专题查询分组和分组项
			var getDyFzSrc = "getZtdyFzs.do?zt_nm="+zt_nm;
			//alert(getDyZbSrc);
			$.get(getDyFzSrc,function(xml){
				//"DYFZ","DYFZX"
				//alert("..."+xml.xml);
				$("Datas/data",xml).each(function(){
					var dyFz = $("DYFZ",this).text();
					var dyFzx = $("DYFZX",this).text();
					var groupItems = new Array();
					if(p.editGroupArray["L"+dyFz]){
						groupItems = p.editGroupArray["L"+dyFz];
					}else{
						p.editGroupArray["L"+dyFz] = groupItems;
					}
					groupItems["L"+dyFzx] = dyFzx;
					
				});
				window.setTimeout(function(){p.expandGroup();},1);
				
			}); 
			
		}else{
			this.isUpdate = false;
			this.indicatorCounts = 0;
			this.resetSubmitForm();
			$.ajax({
			  type: "GET",
			  url: "genUuid.do",			  
			  success: function(msg){
			    // $("input[@name=zt_xh], div#editZtdy").val(msg);	
			    $("div#editZtdy input[@name=zt_xh]").val(msg);
			    //$("input[@name=zt_xh]").attr("value",msg);
			    // $("input#add_zt_xh").val(msg);			    	    
			  },
			  error: function(msg)
			  {
			  	alert(" Error: " + msg);
			  }
			});
		}
	},
	/**
	 * 删除专题
	 */
	deleteZtdy:function(){
		var p = this;
		var deleteZtnms = new Array();
		$("input[@type=checkbox][@checked]").each(function(){
			var rowKey = this.rowKey;
			if(p.dataArray[rowKey]){
				deleteZtnms[deleteZtnms.length] = rowKey.substring(1);
			}
		});
		var deleteSrc = "deleteZtdy.do?deleteZtnms="+deleteZtnms.join();
		$.get(deleteSrc,function(xml){
			if($("error",xml).size()>0){
				alert($("error",xml).text());
			}else{
				$("input[@type=checkbox][@checked]").each(function(){
					var rowKey = this.rowKey;
					if(p.dataArray[rowKey]){
						$(this.parentNode.parentNode).remove();//input->td->tr
						p.dataArray[rowKey] = null;
					}
				});
				$("form[@name=ztdyForm]").submit();
			}
		});
		deleteZtnms = null;
	},
	
	initFormDclbSelect:function(){
		var p = this;
		for(var i=0;i<this.dclbBgqbCombArray.length;i++){
			var dclbId = this.dclbBgqbCombArray[i].dclbId;
			var dclbMc = this.dclbBgqbCombArray[i].dclbMc;
			this.addOption($("select[@name=dc_lb]"),dclbId,dclbMc);
		}
		
		$("select[@name=dc_lb]").change(function(){
			if(this.value!=""){
				p.initFormBgqbSelect(this.selectedIndex-1);
			}else{
				$("select[@name=bg_qb]").empty();
			}
		});
	},
	
	initFormBgqbSelect:function(index){
		var bgqbs = this.dclbBgqbCombArray[index].bgqbs;
		$("select[@name=bg_qb]").empty();
		this.addOption($("select[@name=bg_qb]"),"","\u9009\u62e9...");
		for(var i=0;i<bgqbs.length;i++){
			var bgqbId = bgqbs[i].bgqbId;
			var bgqbMc = bgqbs[i].bgqbMc;
			this.addOption($("select[@name=bg_qb]"),bgqbId,bgqbMc);
		}
	},
	/*
	 * 增加后修改之后设定查询条件
	 */
	setEditReturn:function(zt_xh,dclbDm,bgqbDm){
		$("input[@type=text][@name=zt_xh]",$("div#search_filed")).attr("value",zt_xh);
		$("input[@type=text][@name=zt_mc]",$("div#search_filed")).attr("value","");
		$("option[@value="+dclbDm+"]",$("select[@name=dc_lb]")).attr("selected","selected");
		$("select[@name=dc_lb]").change();
	},
	
	/*
	 * 构建form
	 */
	initForm:function(){
		var p = this;
		
		var jForm = $("form[@name=ztdyForm]");
		
		var options = {
	        target:        $("div#updateForm"),   // target element(s) to be updated with server response 
	        beforeSubmit:  showRequest,  // pre-submit callback 
	        success:       showResponse, // post-submit callback 
	        dataType:  "xml"//指定返回数据的类型为XML
	    }; 
		
		jForm.ajaxForm(options);
		
		function showRequest(formData, jqForm, options) {
			p.dealTableButton();
			var queryString = $.param(formData);
			if(!p.queryString){
				if(p.queryString==queryString){//不重复提交
					return false;
				}
			}
			
		    var pageSize = parseInt($("input[@name=pageSize]").attr("value"));
			var pageIndex = parseInt($("input[@name=pageIndex]").attr("value"));
			var totalCount = parseInt($("span#result_total_count").html());
			var totalPage = parseInt($("span#result_total_page").html());
			var currentPage = parseInt($("span#result_current_page").html());
			
			if(pageIndex>totalPage){//如果
				$("input[@name=pageIndex]").attr("value",totalPage);
				return false;
			}
			
			if(pageIndex<0||(pageIndex-1)*pageSize>totalCount){
				$("input[@name=pageIndex]").attr("value",1);
				jqForm.submit();
				return false;
			} 
		    return true;
		}
		
		function showResponse(responseXML, statusText){ 
			p.showTable(responseXML);
			p.sizeChangeFlag = false;
		}
	},
	
	dealTableButton:function(){
		if($("input.jqTable_select_one").size()==0){
			$("input[@type=button][@enablerule=1]").attr("disabled","disabled");
			$("input[@type=button][@enablerule=2]").attr("disabled","disabled");
		}else{
			if($("input.jqTable_select_one[@checked]").size()==1){
				$("input[@type=button][@enablerule=1]").removeAttr("disabled");
			}else{
				$("input[@type=button][@enablerule=1]").attr("disabled","disabled");
			}
		}
	},

	initTableHead:function(){
		var p = this;
		var table = $("<table cellspacing=\"0\" class=\"jqTable_table\" cellpadding=\"0\" width=\"100%\"></table>");
		
		var headRow = $("<tr></tr>");
		var ztdyTable = $("div#ztdy_table");
		
		headRow.append("<td class=\"jqTable_checkbox\" width=\"25px\"><input class=\"select_all\" type=\"checkbox\"/></td>");
		$("column",ztdyTable).each(function(){
			var property = this.getAttribute("property");
			var caption = this.getAttribute("caption");
			var width = this.getAttribute("width");
			p.columns[p.columns.length]= {
				property:property,
				width   :width
			};
			var td = $("<td class=\"jqTable_table_headCell\">"+caption+"</td>");
			td.css("width",width);
			width="";
			headRow.append(td);
			table.append(headRow);
		});
		
		var dataTableRow = $("<tr></tr>");
		var footRow = $("<tr></tr>");
		var footCell = $("<td style=\"border-bottom:1pt solid #B2D5FF;border-right:1pt solid #B2D5FF;\" colSpan=\""+this.columns.length+1+"\"></td>");
		var dataTableCell =  $("<td height=\"210px;\" valign=\"top\" colSpan=\""+this.columns.length+1+"\"></td>");
		this.dataTable = $("<table style=\"font-size:9pt;\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\"></table>");
		this.footTable = $("<div class=\"jqTable_table_footer\"></div>");
		
		dataTableCell.append(this.dataTable);
		dataTableRow.append(dataTableCell);
		table.append(dataTableRow);
		table.append(footRow);
		footRow.append(footCell);
		footCell.append(this.footTable);
		
		ztdyTable.append(table);
		
		$("input.select_all",headRow).click(function(){
			if(this.checked){
				$("input.jqTable_select_one",dataTableCell).attr("checked","checked");
				$("input[@type=button][@enablerule=1]").attr("disabled","disabled");
				$("input[@type=button][@enablerule=2]").removeAttr("disabled");
			}else{
				$("input.jqTable_select_one",dataTableCell).removeAttr("checked");
				$("input[@type=button][@enablerule=1]").attr("disabled","disabled");
				$("input[@type=button][@enablerule=2]").attr("disabled","disabled");
			}
		});
	},
	
	initTableFoot:function(){
		var p = this;
		//this.footTable
		//当前第【5】页/共【5】页 记录总数【48】 转到12345页 每页显示10条
		//1234510
		var currentSpan = $("<span id=\"result_current_page\">0</span>");
		var totalPageSpan = $("<span id=\"result_total_page\">0</span>");
		var totalCountSpan = $("<span id=\"result_total_count\">0</span>");
		var pageIndexInput = $("<input value=\"0\" class=\"smallInput\" name=\"pageIndex\" type=\"text\" size=\"3\"/>");
		var pageSizeInput = $("<input value=\"10\" class=\"smallInput\" name=\"pageSize\" type=\"text\" size=\"3\"/>");
		/**
		 * 首页  \u9996\u9875
		 * 上一页\u4e0a\u4e00\u9875
		 * 下一页\u4e0b\u4e00\u9875
		 * 尾页  \u5c3e\u9875
		 */
		var goFirst = $("<img alt=\"\u9996\u9875\" class=\"jqTable_foot_image\" src=\""+ztdyPage.Resource.goFirstImg+"\"/>");
		var goPrev = $("<img alt=\"\u4e0a\u4e00\u9875\" class=\"jqTable_foot_image\" src=\""+ztdyPage.Resource.goPrevImg+"\"/>");
		var goNext = $("<img alt=\"\u4e0b\u4e00\u9875\" class=\"jqTable_foot_image\" src=\""+ztdyPage.Resource.goNextImg+"\"/>");
		var goLast = $("<img alt=\"\u5c3e\u9875\" class=\"jqTable_foot_image\" src=\""+ztdyPage.Resource.goLastImg+"\"/>");
		
		var footLeftDiv = $("<div style=\"float:left;\"></div>");
		footLeftDiv.append("<span>\u5f53\u524d\u7b2c</span>");//当前第【
		footLeftDiv.append(currentSpan);
		footLeftDiv.append("<span>\u9875/\u5171</span>");//】页/共【
		footLeftDiv.append(totalPageSpan);
		footLeftDiv.append("<span>\u9875 \u8bb0\u5f55\u603b\u6570</span>");//】页 记录总数【
		footLeftDiv.append(totalCountSpan);
		footLeftDiv.append("<span> \u8f6c\u5230</span>");//】 转到
		footLeftDiv.append(pageIndexInput);
		footLeftDiv.append("<span>\u9875 \u6bcf\u9875\u663e\u793a</span>");//页 每页显示
		footLeftDiv.append(pageSizeInput);
		footLeftDiv.append("\u6761</span>");//条
		
		var footRightDiv = $("<div style=\"float:right;\"></div>");
		footRightDiv.append(goFirst);
		footRightDiv.append(goPrev);
		footRightDiv.append(goNext);
		footRightDiv.append(goLast);
		
		this.footTable.append(footLeftDiv);
		this.footTable.append(footRightDiv);
		
		var jForm = $("form[@name=ztdyForm]");
		goFirst.click(function(){
			var pageIndex = parseInt($("span#result_current_page").html());
			if(pageIndex>1){
				$("input[@name=pageIndex]").attr("value","1");
				pageSubmit();
			}
		});
		goPrev.click(function(){
			var pageIndex = parseInt($("span#result_current_page").html());
			if(pageIndex>1){
				pageIndex--;
				$("input[@name=pageIndex]").attr("value",pageIndex);
				pageSubmit();
			}
		});
		goNext.click(function(){
			var pageIndex = parseInt($("span#result_current_page").html());
			var totalPage = parseInt($("span#result_total_page").html());
			if(pageIndex<totalPage){
				pageIndex++;
				$("input[@name=pageIndex]").attr("value",pageIndex);
				pageSubmit();
			}
		});
		goLast.click(function(){
			var pageIndex = $("span#result_current_page").html();
			var totalPage = parseInt($("span#result_total_page").html());
			if(pageIndex<totalPage){
				$("input[@name=pageIndex]").attr("value",totalPage);
				pageSubmit();
			}
		});
		
		function pageSubmit(){
			jForm.submit();
		}
		
		pageSizeInput.change(function(){
			p.sizeChangeFlag = true;
		});
	},
	
	
	showTable:function(xml){
		var p = this;
		//this.dataTable.empty();
		$("tr",this.dataTable).remove();
		var row = 0;
		
		var totalCount = $("Datas",xml).attr("totalCounts");
		var pageSize = parseInt($("input[@name=pageSize]").attr("value"));
		var currentPage = 1;
		var pageIndex = parseInt($("input[@name=pageIndex]").attr("value"));
		
		pageSize = (!pageSize)?10:pageSize;
		pageIndex = (!pageIndex)?1:pageIndex;
		currentPage = pageIndex;
		
		var pages = Math.ceil(totalCount/pageSize);
		
		$("span#result_total_count").html(totalCount);
		$("span#result_total_page").html(pages);
		$("input[@name=pageIndex]").attr("value",currentPage);
		$("span#result_current_page").html(currentPage);
		
		//result_total_count
		if($("Datas/data",xml).size()==0){
			
		}else{
			$("Datas/data",xml).each(function(){
				var rowTr = $("<tr></tr>");
				var rowData = new Array();
				var key = "L"+$("ZT_NM",this).text();//主键
				var checked = false;
				
				rowTr.mouseover(function(){
					$(this).addClass("jqTable_table_selectedRow");
				});
				
				rowTr.mouseout(function(){
					$(this).removeClass("jqTable_table_selectedRow");
				});
				
				if(row%2==0){
					rowTr.addClass("jqTable_table_evenRow");
				}else{
					rowTr.addClass("jqTable_table_oldRow");
				}
				
				if(p.dataArray[key]){
					checked = p.dataArray[key].checked;
					if(checked==true){
						rowTr.append("<td class=\"jqTable_checkbox\" width=\"25px\"><input checked=\"true\" rowKey=\""+key+"\" class=\"jqTable_select_one\" type=\"checkbox\"/></td>");
					}else{
						rowTr.append("<td class=\"jqTable_checkbox\" width=\"25px\"><input rowKey=\""+key+"\" class=\"jqTable_select_one\" type=\"checkbox\"/></td>");
					}
				}else{
					rowTr.append("<td class=\"jqTable_checkbox\" width=\"25px\"><input rowKey=\""+key+"\" class=\"jqTable_select_one\" type=\"checkbox\"/></td>");
				}
				$("input.jqTable_select_one",rowTr).click(function(){
					var rowKey = $(this).attr("rowKey");
					var relativeRowData = p.dataArray[rowKey];
					if(this.checked){
						relativeRowData.checked = true;
					}else{
						relativeRowData.checked = false;
					}
					
					var checkedCount = $("input.jqTable_select_one[@checked]",p.dataTable).size();
					if(checkedCount==0){
						$("input[@type=button][@enablerule=1]").attr("disabled","disabled");
						$("input[@type=button][@enablerule=2]").attr("disabled","disabled");
					}else if(checkedCount==1){
						$("input[@type=button][@enablerule=1]").removeAttr("disabled");
						$("input[@type=button][@enablerule=2]").removeAttr("disabled");
					}else{
						$("input[@type=button][@enablerule=1]").attr("disabled","disabled");
						$("input[@type=button][@enablerule=2]").removeAttr("disabled");
					}
				});
				p.dataArray[key] = rowData;
				rowData["row"] = row+1;//数据行定位，从1开始
				var bgqbs = new Array();
				rowData["ZT_JS"] = $("ZT_JS",this).text();
				rowData["DY_JG"] = $("DY_JG",this).text();
				for(var i=0;i<p.columns.length;i++){
					var property = p.columns[i].property.toUpperCase();
					var width = p.columns[i].width;
					var cellText = $(property,this).text();
					rowData[property] = cellText;
					cellText = (!cellText)?"&nbsp;":cellText;
										
					if(property=="DC_LB"){//处理调查类别
						for(var iDclb=0;iDclb<p.dclbBgqbCombArray.length;iDclb++){
							if(cellText==p.dclbBgqbCombArray[iDclb].dclbId){
								cellText = p.dclbBgqbCombArray[iDclb].dclbMc;
								bgqbs = p.dclbBgqbCombArray[iDclb].bgqbs;
								break;
							}
						}
					}
					
					if(property=="BG_QB"){//处理报告期别
						for(var iBgqb=0;iBgqb<bgqbs.length;iBgqb++){
							if(cellText==bgqbs[iBgqb].bgqbId){
								cellText = bgqbs[iBgqb].bgqbMc;
								break;
							}
						}
					}
					var cell = $("<td class=\"jqTable_table_cell\">"+cellText+"</td>");
					
					cell.css("width",width);
					
					
					rowTr.append(cell);
				}
				p.dataTable.append(rowTr);
				row++;
			});
		}
	},
	
	initDclbSelect:function(){
		//this.dclbSelect
		var p = this;
		for(var i=0;i<this.dclbBgqbCombArray.length;i++){
			var dclbId = this.dclbBgqbCombArray[i].dclbId;
			var dclbMc = this.dclbBgqbCombArray[i].dclbMc;
			this.addOption(this.dclbSelect,dclbId,dclbMc);
		}
		/*
		 * 
		 */
		this.dclbSelect.change(function(){
			if(this.value!=""){
				p.initBgqbSelect(this.selectedIndex-1);
			}else{
				p.bgqbSelect.empty();
			}
		});
		
		this.bgqbSelect.change(function(){
			//经常性调查 在选年、月、季等报告期报表时初始化制度
			if(this.value!=""&&$("option[@selected]",p.dclbSelect).attr("value")=="1"){
				ztdyPage.Config.fbzdTextAttributeName = 'FBZD_MC';
				ztdyPage.Config.fbzdValueAttributeName = 'FBZD_DM';
				p.fbzdSrc="indicatorYear.do?method=getDczdsXml";
				p.initFbzdSelect();
			}else if(this.value!=""&&$("option[@selected]",p.dclbSelect).attr("value")=="3"){//普查
				ztdyPage.Config.fbzdTextAttributeName = 'LB_MC';
				ztdyPage.Config.fbzdValueAttributeName = 'LB_DYZT';
				p.fbzdSrc="pcZtXml.do?dclbDm=3&bgqbDm="+this.value;//
				p.initFbzdSelect();
			}else{
				p.fbzdSelect.empty();
				p.clearFbzdRelative();
			}
		});
	},
	
	initBgqbSelect:function(index){
		var bgqbs = this.dclbBgqbCombArray[index].bgqbs;
		this.bgqbSelect.empty();
		for(var i=0;i<bgqbs.length;i++){
			var bgqbId = bgqbs[i].bgqbId;
			var bgqbMc = bgqbs[i].bgqbMc;
			this.addOption(this.bgqbSelect,bgqbId,bgqbMc);
		}
		this.bgqbSelect.change();
	},
	
	expandGroup:function(){
		if(this.indicatorCounts==0){
			alert(ztdyPage.Resource.notSelect_indicator_errorMessage);
			return;
		}
		
		this.groupSelect.css("display","block");
		$("div#group_panel_body").css("display","block");
		$("img#group_panel_bodyExpand").attr("src","images/div_collapsable.jpg");
		//alert(this.initComboGroupsFlag);
		if(this.initComboGroupsFlag==true){
			this.initGroups();
			this.initComboGroupsFlag = false;
		}else{
			if(this.comboGroups.length==0){
				alert(ztdyPage.Resource.notCommon_group_errorMessage);
				this.collapseGroup();
			}
		}
	},
	
	collapseGroup:function(){
		$("div#group_panel_body").css("display","none");
		$("select#group_selected_select").css("display","block");
		
		$("img#group_panel_bodyExpand").attr("src","images/div_expandable.jpg");
		$("select#group_selected_select").get(0).style.display = "none";
	},
	
	initFbzdSelect:function(){
		var oValue,text;
		var p = this;
		
		this.fbzdSelect.empty();
		this.clearFbzdRelative();
		var bgqbDm = $("option[@selected]",this.bgqbSelect).attr("value");
		if(bgqbDm){
			this.fbzdSrc+=('&bgqbDm='+bgqbDm);
		}
		var oOption = $("<option>\u8bf7\u9009\u62e9...</option>");
		oOption.attr("value","");
		this.fbzdSelect.append(oOption);
		
		$.get(this.fbzdSrc,function(xml){
			$("data",xml).each(function(){
				oValue = $(ztdyPage.Config.fbzdValueAttributeName,this).text();
				text = $(ztdyPage.Config.fbzdTextAttributeName,this).text();
				var oOption = $("<option>"+text+"</option>");
				oOption.attr("value",oValue);
				
				p.fbzdSelect.append(oOption);
				oOption.removeAttr("selected");
				
				p.pcBgqbs["L"+oValue] = $("LB_DYBGQB",this).text();
				
				oValue = null;
				text = null;
			});
			
		});
		
		this.fbzdSelect.change(function(){
			var fbzdDm,fbzdMc;
			
			fbzdDm = this.options[this.selectedIndex].value;
			fbzdMc = this.options[this.selectedIndex].text;
			
			if(fbzdDm==""){
				p.clearFbzdRelative();
			}else{
				p.initIndicatorTree(fbzdDm,fbzdMc);//
			}
			p.collapseGroup();
			p.setIndicatorCount(0);
		});
	},
	/**
	 * 
	 */
	initTimeType:function(){
		var dclbDm = $("option[@selected]",this.dclbSelect).attr("value");
		var bgqbDm = $("option[@selected]",this.bgqbSelect).attr("value");
		switch(dclbDm){
			case "1":
				switch(bgqbDm){
					case "1":
						this.timeType = "year";
						break;
					case "3":
						this.timeType = "quarter";
						break;
					case "4":
						this.timeType = "month";
						break;
				}
				break;
			case "2":
				break;
			case "3":
				this.timeType = "pc";
				break;
		}
	},
	
	initIndicatorTree:function(fbzdDm,fbzdMc,notClearSelectedZbs){
		var p = this;
		
		if(notClearSelectedZbs!=true){
			this.indicatorCounts = 0;
			this.indicatorSelectedSelect.empty();//
		}
		//&bgqbDm=1
		//if()
		this.initTimeType();
		var pageUrl = "indicatorYear.do?method=getDczdsXml&FBZD_FJD="+fbzdDm+"&timeType="+this.timeType;
		var nodePageUrl = "indicatorYear.do?method=getIndicatorsXml&timeType="+this.timeType;
		
		var bgqbDm = $("option[@selected]",this.bgqbSelect).attr("value");
		if(bgqbDm){
			pageUrl+=('&bgqbDm='+bgqbDm);
		}
		
		var $tree = $('<div style="width:1000"/>');
		$tree.append('<ul><li expanded="true" id="'+fbzdDm+'"><a><span>'+fbzdMc+'</span></a><ul/></li></ul>');
		var $treeRootUl = $tree.find('li>ul');
		this.indicatorTreeContainer.empty();
		this.indicatorTreeContainer.append($tree);
		$.get(pageUrl,function(xml){
			var fbzdNodeSourceArray;
			var fbzd_dm;
			var fbzd_mc;
			
			$('data',xml).each(function(){
				fbzdNodeSourceArray = [];
				fbzd_dm = $('FBZD_DM',this).text();
				fbzd_mc = $('FBZD_MC',this).text();
				fbzdNodeSourceArray.push('<li src="');
				fbzdNodeSourceArray.push(nodePageUrl+'&FBZD_FJD='+fbzd_dm);
				fbzdNodeSourceArray.push('"><a><span>');
				fbzdNodeSourceArray.push(fbzd_mc);
				fbzdNodeSourceArray.push('</span></a></li>');
				$treeRootUl.append(fbzdNodeSourceArray.join(''));
				fbzd_dm = null;
				fbzd_mc = null;
			}) ;
			
			p.indicatorTree = $tree.youiTree({
				treeMapObject:{
					mapId	:'id',
					mapText	:'text',
					mapPid	:'pid'
				}
			});
		});
		
		$("img#select_checked_indicators").unbind("click");
		$("img#select_checked_indicators").click(function(){
			/*
			 * 选择分组项后，增加指标时提示
			 */
			if(p.hasSelectGroupItems()==true){
				if(window.confirm(ztdyPage.Resource.hasSelected_groupItem_message)){
					p.clearSelectGroupItems();//清除选择的分组项
				}else{
					return;
				}
			}
			
			var checkedJqTreeNodes  = p.indicatorTree.getCheckedJqTreeNodes();//checked nodes
			var addIndicatorIdArray = new Array();//add to select node
			//alert(checkedJqTreeNodes.length);
			var checkedIndicatorText;
			var checkedIndicatorValue;
			for(var i=0;i<checkedJqTreeNodes.length;i++){
				var jqTreeNode = checkedJqTreeNodes[i];
				checkedIndicatorText = jqTreeNode.getText();
				checkedIndicatorValue = jqTreeNode.getId();
				addIndicatorIdArray[addIndicatorIdArray.length] = checkedIndicatorValue;//
				if($("option[@value="+checkedIndicatorValue+"]",p.indicatorSelectedSelect).size()>0){
					continue;
				}
				
				var oOption = $("<option>"+checkedIndicatorText+"</option>");
				oOption.attr("value",checkedIndicatorValue);
				
				p.indicatorSelectedSelect.append(oOption);
				checkedIndicatorValue = null;
				checkedIndicatorText = null;
			}
			
			if(addIndicatorIdArray.length>0){//如果选择的指标树大于0
				p.initComboGroupsFlag = true;
				p.collapseGroup();
				p.disposeGroups();
				/**
				 * 灰显调查类别和报告期别
				 */
				p.disableRelative();
				//p.initGroups(addIndicatorIdArray);//set groups
			}
			p.setIndicatorCount();
		});
		
		$("img#delete_checked_indicators").unbind("click");
		$("img#delete_checked_indicators").click(function(){
			if(p.hasSelectGroupItems()==true){
				if(window.confirm(ztdyPage.Resource.hasSelected_groupItem_message)){
					p.clearSelectGroupItems();//清除选择的分组项
				}else{
					return;
				}
			}
			if($("option[@selected]",p.indicatorSelectedSelect).size()==0)return;
			var checkedJqTreeNodes  = p.indicatorTree.getCheckedJqTreeNodes();
			var indicatorIdArray = new Array();
			$("option",p.indicatorSelectedSelect).each(function(){
				if(this.selected){
					for(var i=0;i<checkedJqTreeNodes.length;i++){
						var jqTreeNode = checkedJqTreeNodes[i];
						if(this.value==jqTreeNode.getId()){
							$("input[@type=checkbox]:first",jqTreeNode.node).click();
							$("span.jTree_item_text:first",jqTreeNode.node).css("color","black");
						}
					}
					$(this).remove();
				}else{
					indicatorIdArray[indicatorIdArray.length]=this.value;
				}
			});
			p.setIndicatorCount();
			if(indicatorIdArray.length>0){
				//p.initGroups(indicatorIdArray);
				p.collapseGroup();
				p.disposeGroups();
				p.initComboGroupsFlag = true;
				
			}else{
				//p.expandGroup();
				p.collapseGroup();//
				p.disposeGroups();
				p.activeRelative();
			}
		});
	},
	
	disableRelative:function(){
		this.fbzdSelect.attr("disabled","disabled");
		this.dclbSelect.attr("disabled","disabled");
		this.bgqbSelect.attr("disabled","disabled");
	},
	
	activeRelative:function(){
		this.fbzdSelect.removeAttr("disabled");
		this.dclbSelect.removeAttr("disabled");
		this.bgqbSelect.removeAttr("disabled");
	},
	/**
	 * 
	 */
	initGroups:function(){
		var p = this;
		var addIndicatorIdArray = new Array();
		var indicatorArray = new Array();//remove zb_bb //  1/33
		$("option",this.indicatorSelectedSelect).each(function(){
			addIndicatorIdArray[addIndicatorIdArray.length] = this.value;
		});
		for(var i=0;i<addIndicatorIdArray.length;i++){
			var bIndex = addIndicatorIdArray[i].indexOf("/");
			if(bIndex!=-1){
				indicatorArray[i] = addIndicatorIdArray[i].substring(bIndex+1);
			}
		}
		var indicators = indicatorArray.join();
		
		var groupId;
		var groupMc;
		var comboGroupText;
		this.disposeGroups();
		if(!indicators)return;
		
		var searchGroupsSrc = "indicatorYear.do?method=getGroupAndGroupItemXml&indicators="+indicators;
		switch(this.timeType){
			case "year":
				searchGroupsSrc+="&dclbDm=1&bgqbDm=1";
				break;
			case "quarter":
				searchGroupsSrc+="&dclbDm=1&bgqbDm=3";
				break;
			case "month":
				searchGroupsSrc+="&dclbDm=1&bgqbDm=4";
				break;
			case "pc":
				var pcZtdm = $("option[@selected]",this.fbzdSelect).attr("value");
				var pcBgqbDm = p.pcBgqbs["L"+pcZtdm];
				searchGroupsSrc+="&dclbDm=3&bgqbDm="+pcBgqbDm;
				break;
		}
		$.get(searchGroupsSrc,function(xml){
			$("Groups/comboGroup",xml).each(function(){
				comboGroupText = this.getAttribute("comboGroupText");
				var comboGroupValue = p.comboGroups.length;
				p.comboGroups[comboGroupValue] = {
					comboGroupValue:comboGroupValue,
					comboGroupText:comboGroupText,
					groups:new Array()
				};

				$("group",this).each(function(){
					groupId = this.getAttribute("FZ_ID");
					groupMc = this.getAttribute("FZ_CN_QC");
					var groupKey = "L"+groupId;
					
					p.comboGroups[comboGroupValue].groups[groupKey] = {
						groupId:groupId,
						groupMc:groupMc,
						groupItems:new Array()
					};
					
					$("groupItem",this).each(function(){
						groupItemId = this.getAttribute("FZX_ID");
						groupItemMc = this.getAttribute("FZX_CN_QC");
						groupItemFjd = this.getAttribute("FZX_FJD");
						groupItemCjm = this.getAttribute("FZX_CJM");
						var groupItemKey = "L"+groupItemId;
						
						p.comboGroups[comboGroupValue].groups[groupKey].groupItems[groupItemKey]={
							groupItemId:groupItemId,
							groupItemMc:groupItemMc,
							groupItemFjd:groupItemFjd,
							groupItemCjm:groupItemCjm
						}
						groupItemId = null;
						groupItemMc = null;
						groupItemFjd = null;
						groupItemCjm = null;
					});
				});
				groupId = null;
				groupMc = null;
			});
			
			p.initComboGroupsSelect();
			
		});
	},
	
	initComboGroupsSelect:function(){
		var comboGroup;
		var group;
		var comboGroupValue;
		var comboGroupText;
		
		var groupItemId;
		var groupItemMc;
		this.groupSelect.empty();
		if(this.comboGroups.length==0){
			this.collapseGroup();
			return;
		}
		for(var i=0;i<this.comboGroups.length;i++){
			comboGroup = this.comboGroups[i];
			comboGroupValue = comboGroup.comboGroupValue;
			comboGroupText = comboGroup.comboGroupText;	
			var oOption = $("<option>"+comboGroupText+"</option>");
			oOption.attr("value",comboGroupValue);
			this.groupSelect.append(oOption);
			oOption.removeAttr("selected");
		}
	
		if(this.comboGroups.length!=0){
			if(this.isUpdate==true){
				var selectedComboGroupIndex = null;
				
				for(var i=0;i<this.comboGroups.length;i++){
					var pFlag = true;
					for(var groupKey in this.editGroupArray){
						if(groupKey!="indexOf"){
							//alert(i+"..."+groupKey+".."+this.comboGroups[i].groups[groupKey]);
							if(!this.comboGroups[i].groups[groupKey]){
								pFlag = false;
								break;
							}
						}
					}
					if(pFlag==true){
						selectedComboGroupIndex = i;
						break;
					}
				}
				//this.selectedComboGroupIndex = selectedComboGroupIndex;//记录对应选择的复合分组
				//this.groupSelect.get(0).selectedIndex = selectedComboGroupIndex;
				try{
					this.initGroupSelects(selectedComboGroupIndex);
					//groupItem_count
					$("span.groupItem_count").each(function(){
						var groupId = this.groupId;
						$("select[@groupId="+groupId+"]").change();
					});
					$("option[@value="+selectedComboGroupIndex+"]",this.groupSelect).attr("selected","selected");
				}catch(err){
					
				}
				
			}else{
				this.groupSelect.change();
			}
		}
		
		this.editGroupArray = new Array();
	},
	
	initGroupSelects:function(comboGroupValue){
		var p = this;
		var comboGroup = this.comboGroups[comboGroupValue];
		var groupItemSelectsPanel = $("div#groupItem_select_panel");
		groupItemSelectsPanel.empty();
		
		var groups = comboGroup.groups;
		var group;
		var groupId;
		var groupMc;
		
		var selectWidth = 0;
		for(var iGroup in groups){
			group = groups[iGroup];
			if(group&&iGroup!="indexOf"){
				var groupItemSelectPanel = $("<span style=\"margin-left:10px;float:left;\" class=\"groupItem_select_panel\"></span>");
				var groupItmeSelect = $("<select multiple=\"multiple\" style=\"width:120px;\" size=\"5\" class=\"groupItem_select\"></select>");
				groupId = group.groupId;
				groupMc = group.groupMc;
				
				groupItmeSelect.attr("groupId",groupId);
				groupItmeSelect.attr("groupMc",groupMc);
				
				var groupCountPanel = $("<div align=\"center\" groupId=\""+groupId+"\"></div>");
				var groupCountSpan = $("<span class=\"groupItem_count count_show\" groupId=\""+groupId+"\">0</span>");
				groupCountPanel.append("\u5171\u9009\u4e2d");
				groupCountPanel.append(groupCountSpan);
				groupCountPanel.append("\u4e2a");
				
				groupItmeSelect.attr("groupId",groupId);
				groupItmeSelect.attr("groupMc",groupMc);
				this.initGroupItemsSelect(group,groupItmeSelect);
				if($("option",groupItmeSelect).size()==0){
					groupItemSelectPanel.empty();//no common groupItem
					return;
				}
				groupItemSelectPanel.append(groupItmeSelect);
				groupItemSelectPanel.append(groupCountPanel);
				
				groupItemSelectsPanel.append(groupItemSelectPanel);
				
				groupItmeSelect.change(function(){
					var groupId = this.value;
					
					var groupItemCount = 0;
					var thisGroupCountSpan = $("span",this.parentNode);
				
					groupItemCount = $("option[@selected]",this).size()
					
					thisGroupCountSpan.html(groupItemCount);
				});
				
				selectWidth+=130;
			}
		}
		groupItemSelectsPanel.css("width",selectWidth+1);
		var left = (740-selectWidth-1)/2;

		groupItemSelectsPanel.css("margin-left",left);
	},
	
	initGroupItemsSelect:function(group,groupItmeSelect){
		var groupItemId;
		var groupItemMc;
		var groupItemFjd;
		var groupItemCjm;
		if(group){
			var groupItems = group.groupItems;
			var groupId = group.groupId;
			this.sortGroupItem(groupItems,groupItmeSelect,"");
			
			if(this.isUpdate==true){
				var editGroupItems = this.editGroupArray["L"+groupId];
				if(editGroupItems){
					for(var iGroupItem in editGroupItems){
						if(iGroupItem!="indexOf"){
							var groupItemId = editGroupItems[iGroupItem];
							$("option[@value="+groupItemId+"]",groupItmeSelect).attr("selected","selected");
						}
					}
				}
			}
		}
	},
	
	hasSelectGroupItems:function(){
		var hasSelected = false;
		$("select.groupItem_select").each(function(){
			if($("option[@selected]",this).size()>0){
				hasSelected = true;
			}
		});
		return hasSelected;
	},
	
	clearSelectGroupItems:function(){
		$("select.groupItem_select").each(function(){
			$("option[@selected]",this).removeAttr("selected");
		});
	},
	/**
	 * 
	 */
	disposeGroups:function(){
		var comboGroup;
		$("span.groupItem_count").html("0");
		for(var i=0;i<this.comboGroups.length;i++){
			comboGroup = this.comboGroups[i];
			
			comboGroup = null;
		}
		this.comboGroups = new Array();
		$("span.groupItem_select_panel").remove();
	},
	
	sortGroupItem:function(groupItems,groupItemSelect,sGroupItemFjd){
		var groupItemId;
		var groupItemMc;
		var groupItemFjd;
		var groupItemCjm;
		var level = 0;
		
		for(var iGroupItem in groupItems){
			if(iGroupItem&&iGroupItem!="indexOf"){
				groupItemId = groupItems[iGroupItem].groupItemId;
				groupItemMc = groupItems[iGroupItem].groupItemMc;
				groupItemFjd = groupItems[iGroupItem].groupItemFjd;
				groupItemCjm = groupItems[iGroupItem].groupItemCjm;
				
				
				if((!sGroupItemFjd&&!groupItemFjd)||sGroupItemFjd==groupItemFjd){
					//if(!groupItemCjm)level = 1;
					var space = "";
					//level = groupItemCjm/4
					//for(var iSpace=1;iSpace<level;iSpace++){
					//	space+="&nbsp;&nbsp";
					//}
					
					var oOption = $("<option>"+space+groupItemMc+"</option>");
					oOption.attr("value",groupItemId);
					groupItemSelect.append(oOption);
					this.sortGroupItem(groupItems,groupItemSelect,groupItemId);
				}
				
				groupItemId = null;
				groupItemMc = null;
				groupItemFjd = null;
				groupItemCjm = null;
			}
		}
		$("option",groupItemSelect).removeAttr("selected");
	},
	
	setIndicatorCount:function(){
		this.indicatorCounts = $("option",this.indicatorSelectedSelect).size();
		if(!this.indicatorCounts)this.indicatorCounts="0";
		//alert(this.indicatorCounts);
		$("span#selected_Indicator_count").html(this.indicatorCounts);
	},
	/**
	 * 清空发布制度管理信息
	 */
	clearFbzdRelative:function(){
		this.indicatorTreeContainer.empty();
		this.indicatorSelectedSelect.empty();
		
	},
	
	addOption:function(select,value,text){
		var oOption = $("<option value=\""+value+"\">"+text+"</option>");
		select.append(oOption);
		oOption.removeAttr("selected");
		return oOption;
	},
	
	resetSubmitForm:function(){
	    
	    var msg = $("div#editZtdy input[@name=zt_xh]").val();
		$("input[@type=text]",$("div#editZtdy")).attr("value","");//
		$("textarea",$("div#editZtdy")).attr("value","");		
		$("div#editZtdy input[@name=zt_xh]").val(msg);		
		var curDate = new Date();
		$("input[@name=zt_zxfbsj]").attr("value",curDate.format("yyyyMMdd"));
		this.clearSelectGroupItems();
		//$("select#groupItem_select").empty();
		this.fbzdSelect.empty();
		this.clearFbzdRelative();
		
		this.collapseGroup();
		this.activeRelative();
		
		this.dclbSelect.get(0).selectedIndex=0;
		try{
			$("option:gt(1)",this.bgqbSelect).remove();
		}catch(err){
			
		}
		this.setIndicatorCount();
	},
	
	postSubmitData:function(){
		var postXml;
		var isUpdate = this.isUpdate;
		postXml = this.makePostXml();
		if(postXml){
			$.ajax({
				type		: "POST",
	  			url			: "saveZtdy.do?isUpdate="+isUpdate,
	  			dataType	: "xml",
	  			success		:  function(data){
	  							 if($("error",data).size()>0){
	  							 	alert($("error",data).text());//print the error
	  							 }else{
	  							 	alert($("message",data).text());
	  							 }
	  						   },
	  			data		:postXml,
	  			processData :false
			});
			
		}else{
			return false;
		}
		return true;
	},
	
	makePostXml:function(){
		var postXml;
		if(window.XMLHttpRequest){
			postXml=new XMLDOM();
		}else if(window.ActiveXObject){
			postXml=new ActiveXObject("Microsoft.XMLDOM");
		}
		
		var xmlHead = postXml.createProcessingInstruction("xml"," version=\"1.0\"");
		postXml.insertBefore(xmlHead,postXml.firstChild);
		
		var rootElement = postXml.createElement('insert');
		postXml.appendChild(rootElement);
		//tableName-FBK_ZTDY 
		
		if(this.isUpdate==true){
			var selectedRowKey = $("input[@type=checkbox][@checked]").attr("rowKey");
			var ztdyData = this.dataArray[selectedRowKey];
			var zt_nm = selectedRowKey.substring(1);
			var zt_nmElement = postXml.createElement("zt_nm");
			zt_nmElement.appendChild(postXml.createTextNode(zt_nm));
			rootElement.appendChild(zt_nmElement);
		}
		/*
		 * 基本信息
		 */
		var zt_xh  = this.getTextFiled("input","zt_xh",$("div#baseInfo_panel"));
		var zt_mc  = this.getTextFiled("input","zt_mc",$("div#baseInfo_panel"));
		var zt_zdr =this.getTextFiled("input","zt_zdr",$("div#baseInfo_panel"));
		var zt_zxfbsj = this.getTextFiled("input","zt_zxfbsj",$("div#baseInfo_panel"));
		var dy_jg =  this.getTextFiled("input","dy_jg",$("div#baseInfo_panel"));

		var zt_js =  this.getTextFiled("textarea","zt_js",$("div#baseInfo_panel"));
		if(zt_xh==false)return;
		if(zt_mc==false)return;
		if(zt_zxfbsj==false)return;
		if(zt_js==false)return;
		if(dy_jg==false)return;
		
		var dclbDm;
		var bgqbDm;
	
		var zt_xhElement = postXml.createElement("zt_xh");
		var zt_mcElement = postXml.createElement("zt_mc");
		var zt_zdrElement = postXml.createElement("zt_zdr");
		var zt_zxfbsjElement = postXml.createElement("zt_zxfbsj");
		var dy_jgElement = postXml.createElement("dy_jg");
		var zt_jsElement = postXml.createElement("zt_js");
		var dclbElement = postXml.createElement("dc_lb");
		var bgqbElement = postXml.createElement("bg_qb");
		
		
		dclbDm = $("option[@selected]",this.dclbSelect).attr("value");
		bgqbDm = $("option[@selected]",this.bgqbSelect).attr("value");
		
		this.setEditReturn(zt_xh,dclbDm,bgqbDm);
		
		if(dclbDm==""||dclbDm==null){
			return;
		}
		
		if(bgqbDm==""||bgqbDm==null){
			return;
		}
		
		zt_xhElement.appendChild(postXml.createTextNode(zt_xh));
		zt_mcElement.appendChild(postXml.createTextNode(zt_mc));
		zt_zdrElement.appendChild(postXml.createTextNode(zt_zdr));
		zt_zxfbsjElement.appendChild(postXml.createTextNode(zt_zxfbsj));
		dy_jgElement.appendChild(postXml.createTextNode(dy_jg));
		zt_jsElement.appendChild(postXml.createTextNode(zt_js));
		dclbElement.appendChild(postXml.createTextNode(dclbDm));
		bgqbElement.appendChild(postXml.createTextNode(bgqbDm));
		
		rootElement.appendChild(zt_xhElement);
		rootElement.appendChild(zt_mcElement);
		rootElement.appendChild(zt_zdrElement);
		rootElement.appendChild(zt_zxfbsjElement);
		rootElement.appendChild(zt_jsElement);
		rootElement.appendChild(dy_jgElement);
		rootElement.appendChild(dclbElement);
		rootElement.appendChild(bgqbElement);
		/*
		 * 主题--发布制度
		 */
		var fbzdDm;
		fbzdDm = $("option[@selected]",this.fbzdSelect).attr("value");
		if(fbzdDm==""||fbzdDm==null){
			return;
		}
		/*
		 * 指标
		 */ 
		var indicatorSetElement = postXml.createElement("indicatorSet");
		rootElement.appendChild(indicatorSetElement);
		if($("option",this.indicatorSelectedSelect).size()==0){
			alert(ztdyPage.Resource.notSelect_indicator_errorMessage);
			return;
		}else{
			$("option",this.indicatorSelectedSelect).each(function(){
				var indicatorElement = postXml.createElement("indicator");
				indicatorElement.appendChild(postXml.createTextNode(this.value));// zb_bb/zb_id
				indicatorSetElement.appendChild(indicatorElement);
			});
		}
		
		/*
		 * 分组和分组项
		 */
		var groupsElement = postXml.createElement('groups');
		rootElement.appendChild(groupsElement);
		var groupElement;
		var groupItemElement;
		
		var groupIntegrality = true;//分组完整性的标志，如果没有选择分组项，提示用户是否继续保存
		var groupSelectedItemCount = 0;
		$("select.groupItem_select").each(function(){
			if($("option[@selected]",this).size()>0){//
				var groupId = $(this).attr("groupId");
				groupElement = postXml.createElement('group');
				groupElement.setAttribute("groupId",groupId);
				groupsElement.appendChild(groupElement);
				if($("option[@selected]",this).size()==0){
					return;
				}else{
					$("option[@selected]",this).each(function(){
						var groupItemId = this.value;
						var groupItemMc = $(this).html();
						groupItemElement = postXml.createElement('groupItem');
						groupItemElement.setAttribute("groupItemId",groupItemId);
						groupElement.appendChild(groupItemElement);
						groupSelectedItemCount++;
					});
				}
				
			}else{
				groupIntegrality = false;//只有有一个分组项未选择，就置为不完整状态
			}
		});

		if(groupSelectedItemCount==0){
			if(window.confirm(ztdyPage.Resource.notSelect_group)){//没有选择分组，确认继续？
				
			}else{
				return;
			}
		}else{
			if(groupIntegrality==false){
				alert(ztdyPage.Resource.group_no_groupItemSelected);//不能存在未选择分组项的分组。
				return;
			}
		}
		
		return postXml;
	},
	
	setTextFiled:function(tag,filedName,text,parentForm){
		var jFiled = $(tag+"[@name="+filedName+"]",parentForm);
		jFiled.attr("value",text);
	},
	
	getTextFiled:function(tag,filedName,parentForm){
		var jFiled = $(tag+"[@name="+filedName+"]",parentForm);
		if(jFiled.size()==0)return false;
		var maxLength = jFiled.attr("maxLength");
		var text = jFiled.attr("value");
		var caption = jFiled.attr("caption");
		 
		if(jFiled.attr("notNull")=="true"){
			if(text==null||text==""){
				alert(caption+"\u4e0d\u80fd\u4e3a\u7a7a!");
				return false;
			}
		}

		if(typeof(text)!='undefined'&&text.length>maxLength){
			alert(caption+"\u957f\u5ea6\u4e0d\u5e94\u5927\u4e8e"+maxLength);
			return false;
		}
		return text;
	}
}