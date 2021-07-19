(function(){
	function getDocStausName(docStatusId){
		var sStatusName = '';
		var sAllStatus = com.trs.wcm.AllDocStatus || [];
		for(var i=0;i < sAllStatus.length; i++){
			if(docStatusId == sAllStatus[i].value){
				sStatusName = sAllStatus[i].label;
				break;
			}
		}
		return sStatusName;
	}
	function StatusShow(param){
		var temp = param.split(',');
		if(temp.length==0)return '';
		var result = ['<div style="color: #789;"><div>'];
		for(var i = 0;i < temp.length; i++){
			if(i % 3 == 0 && i != 0){
				result.push("</div><div>");
			}
			result.push('<span style="padding-left:15px;">');
			result.push(getDocStausName(temp[i]));
			result.push("</span>");
		}
		result.push('</div></div>');
		return result.join('');
	}
	function RegionShow(param){
		var temp = param.split(',');
		var realLength = temp.length;
		if(realLength==0)return '';
		var result = [''];
		if(realLength > 10 ) realLength = 10;
		for(var i = 0;i < realLength; i++){
			result.push('<div><span style="display:inline-block;width:20px;text-align:center">');
			result.push((i+1) + ".</span>" + temp[i]);
			result.push("</div>");
		}
		if(param.split(',').length > 10){
			result.push('<div style="padding-left:20px">......</div>');
		}
		return result.join('');
	}
	function TimeShow(param){
		switch(param){
			case '-1' : return "";
			case '5' : return wcm.LANG.DOCUMENT_PROCESS_62 || "三天内";
			case '6' : return wcm.LANG.DOCUMENT_PROCESS_63 || "一周内";
			case '7' : return wcm.LANG.DOCUMENT_PROCESS_64 || "一月内";
		}
		return wcm.LANG.DOCUMENT_PROCESS_65 || "未知";
	}
	function Subtring(param,fieldName){
		var result = '';
		var realResult = '';
		for(var i = 0; i < param.length; i++){
			if(param[i].indexOf(fieldName) != -1){
				result = param[i].substring(param[i].indexOf('=') + 1);				
				break;
			}
		}
		if(result=='true') result = wcm.LANG.DOCUMENT_PROCESS_66 || '是';
		if(result=='false') result = '';
		return result;
	}
	function Display(param,fieldName){
		if(fieldName=="CONTAINSCHILDREN")
			return Subtring(param, fieldName)=='true'?'':'none';
		for(var i = 0; i < param.length; i++){
			if(param[i].indexOf(fieldName) != -1)
				return "";
		}
		return "none" ;
	}
	var m_Template = {
		outer : '',
		item : [
			'<div class="attribute_row readonly">',
				'<span class="search_special {2}">{0}:</span>',
				'<span class="wcm_attr_value" title="{1}" style="margin-left:5px;margin-top:-3px;">{1}</span>',
			'</div>'
		].join(''),
		item_region : [
			'<div class="attribute_row readonly">',
				'<span class="search_special {2}">{0}:</span>',
			'</div>',
			'<div style="padding-left:10px;color:gray">{1}</div>'
		].join(''),
		item_status : [
			'<div class="attribute_row readonly">',
				'<span class="search_special {2}">{0}:</span>',
			'</div>',
			'<div style="padding-left:10px;color:gray;width:100%;">{1}</div>'
		].join(''),
		item_time : [
			'<div class="attribute_row readonly" style="height:{6}px">',
				'<div>',
					'<span class="search_special {4}">{5}:</span>',
					'<span class="wcm_attr_value" style="margin-left:5px;margin-top:-3px;" title="{0}">{0}</span>',
				'</div>',
				'<div style="display:{1};margin-left:35px;color:gray">{2}</div>' ,
				'<div style="display:{1};" class="timeTo">', wcm.LANG.DOCUMENT_PROCESS_188 || "至", '&nbsp;&nbsp;','{3}</div>',
			'</div>'
		].join(''),
		item_sep : '<div class="attribute_row_sep"></div>'
	};
	function doWithTimeTemp(v, name, sTitle, t1, t2){
		var value = Subtring(v, name);
		if(!value && Subtring(v, t1)=='')return false;
		value = TimeShow(value);
		var height = '';
		var displayChild = Subtring(v, t1)!='' ? '' : 'none';
		if(displayChild==''){
			value = '';
			height = '60';
		}
		return String.format(m_Template.item_time, value, displayChild,
				decodeURIComponent(Subtring(v, t1)), decodeURIComponent(Subtring(v, t2)),
				name.toLowerCase(), sTitle, height);
	}
	function m_sSearchTemplate(html){
		var v = html.split('&');
		var arr = ["DOCTITLE", "CRUSER", "-", "REGION", "CONTAINSCHILDREN", 
			"CRTIMEINTERVAL", "PUBTIMEINTERVAL", "-", "DOCSOURCENAME", "DOCSTATUS", "DOCKEYWORDS"];
		var arrTitle = [(wcm.LANG.DOCUMENT_PROCESS_67 || "标题包含"), (wcm.LANG.DOCUMENT_PROCESS_68 || "创建者"), "-", (wcm.LANG.DOCUMENT_PROCESS_69 || "检索范围"), (wcm.LANG.DOCUMENT_PROCESS_70 || "含子栏目"), 
			(wcm.LANG.DOCUMENT_PROCESS_71 || "创建时间"), (wcm.LANG.DOCUMENT_PROCESS_72 || "发布时间"), "-", (wcm.LANG.DOCUMENT_PROCESS_73 || "文档来源"), (wcm.LANG.DOCUMENT_PROCESS_74 || "文档状态"), (wcm.LANG.DOCUMENT_PROCESS_75 || "关键字")];
		var result = [];
		var lastIsSep = true;
		//var decode = Ext.isIE?decodeURIComponent:Ext.kaku;
		for(var i=0, n =arr.length; i<n; i++){
			var name = arr[i];
			if(name=='-'){
				if(lastIsSep==false) result.push(m_Template.item_sep);
				lastIsSep = true;
				continue;
			}
			var value = decodeURIComponent(Subtring(v, name));
			if(name=='REGION'){
				result.push(String.format(m_Template.item_region, 
					arrTitle[i], RegionShow(value), name.toLowerCase()));
				continue;
			}
			if(name=="CRTIMEINTERVAL"){
				var tmpVal = doWithTimeTemp(v, name, arrTitle[i], "STARTDATE", "ENDDATE");
				if(tmpVal!=false){
					lastIsSep = false;
					result.push(tmpVal);
				}
				continue;
			}
			if(name=="PUBTIMEINTERVAL"){
				var tmpVal = doWithTimeTemp(v, name, arrTitle[i], "STARTPUBDATE", "ENDPUBDATE");
				if(tmpVal!=false){
					lastIsSep = false;
					result.push(tmpVal);
				}
				continue;
			}
			if(!value)continue;
			lastIsSep = false;
			if(name=='DOCSTATUS'){
				result.push(String.format(m_Template.item_status, 
					arrTitle[i], StatusShow(value), name.toLowerCase()));
				continue;
			}
			result.push(String.format(m_Template.item, arrTitle[i], value.replace(/\"/g,"&quot;"), name.toLowerCase()));
		}
		return result.join('');
	}
	//注册面板
	wcm.PageOper.registerPanels({
		documentInSite : {
			isHost : true,
			title : wcm.LANG.DOCUMENT_PROCESS_76 || '站点文档操作任务',
			displayNum : 5
		},
		documentInChannel : {
			isHost : true,
			title : wcm.LANG.DOCUMENT_PROCESS_77 || '栏目文档操作任务',
			displayNum : 5
		},
		chnldoc : {
			title : wcm.LANG.DOCUMENT_PROCESS_78 || '文档操作任务',
			displayNum : 7,
			url : '?serviceid=wcm61_viewdocument&methodname=jFindbyid',
			detailMore : function(wcmEvent, opers, curDef){
				if(!wcm.AuthServer.hasRight(wcm.PageOper.panel1Info.right, 32)){
					wcm.domain.ChnlDocMgr.view(wcmEvent);
				}else{
					wcm.domain.DocumentMgr.edit(wcmEvent);
				}
			}
		},
		chnldocs : {
			title : wcm.LANG.DOCUMENT_PROCESS_78 || '文档操作任务',
			displayNum : 7
		},
		docSearchContext : {
			title : wcm.LANG.DOCUMENT_PROCESS_79 || '检索结果操作任务',
			displayNum : 5,
			url : WCMConstants.WCM6_PATH +
						'advsearch/show_search_detail.jsp',
			detailTitle : wcm.LANG.DOCUMENT_PROCESS_80 || '检索条件详细信息',
			detail : function(cmsobjs, info){
				return m_sSearchTemplate(cmsobjs.detail);
			}
		}
	});
})();
