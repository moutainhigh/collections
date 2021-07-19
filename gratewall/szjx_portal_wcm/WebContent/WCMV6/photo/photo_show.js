Object.extend(PageContext,{
	init : function(){
		var docId = getParameter("PhotoId") || getParameter("DocumentId")|| 0;
		var channelId = getParameter("ChannelId") || 0;
		var siteId = getParameter("SiteId") || 0;
		var flowDocId = getParameter("FlowDocId") || 0;
		if(channelId > 0){
			this.params = {ObjectId:docId,ChannelId:channelId,ChannelIds:channelId,FlowDocId:flowDocId};		
		}else{
			this.params = {ObjectId:docId,SiteId:siteId,SiteIds:siteId,FlowDocId:flowDocId};		
		}
		this.m_nRecId = getParameter("RecId") || getParameter("ChnlDocId") || 0;		
		this.CurrPage = getParameter("CurrPage")||1;
	},
	loadImage : function(){
		BasicDataHelper.call("wcm6_photo","getSacledImages",this.params,true,function(_transport,_json){			
			var sValue = TempEvaler.evaluateTemplater('template_scaledimagnav', _json);			
			Element.update($("scaledimagnav"),sValue);
			
			//show.
			var spanEls = document.getElementsByClassName("navdesc");
			var spanEl = spanEls[spanEls.length-1];
			spanEl.nextSibling.className = "actived_size";
			$("currImage").src = mapWebFile(spanEl._file);
		});
	},	
	loadKinds : function(){
		Object.extend(this.params,{RecId:this.m_nRecId});
		BasicDataHelper.call("wcm6_photo","getQuoteKinds",this.params,true,function(_transport,_json){			
			var sValue = TempEvaler.evaluateTemplater('template_kinds', _json);			
			Element.update($("kinds"),sValue);	
		});		
	},
	loadQuoteDocs : function(){
		BasicDataHelper.call("wcm6_photo","getQuoteDocs",this.params,true,function(_transport,_json){			
			var sValue = TempEvaler.evaluateTemplater('template_quotes', _json);			
			Element.update($("quotes"),sValue);		
		});
	},
	loadExtendedProps : function(){
		BasicDataHelper.call("wcm6_photo","getExtendedProps",this.params,true,function(_transport,_json){			
			var sValue = TempEvaler.evaluateTemplater('template_imageextprops', _json);			
			Element.update($("imageextprops"),sValue);		
		});
	},
	loadPage : function(){
		this.loadImage();
		this.loadKinds();
		this.loadQuoteDocs();
		this.loadExtendedProps();

		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var aCombine = [];	
		//load images.				
		aCombine.push(oHelper.Combine('wcm6_photo', 'query', Object.extend(this.params,{PageSize:3,CurrPage:this.CurrPage})));

		//load image props. extfields.
		var oPostData = Object.extend({},this.params);
		Object.extend(oPostData,{ObjectId:this.m_nRecId,SelectFieldsOfDocument:"DocId,DocTitle,DocContent,DocType,DocSource,DocAbstract,DocRelWords,DocPeople,DocPlace,DocRelTime,Attribute,CrUser,DocAuthor,DocKeywords"});		
		
		aCombine.push(oHelper.Combine('wcm6_viewdocument', 'findById', oPostData));

		//process info.
		var postData = {
			ContentType: 605,
			ContentId: PageContext.params['ObjectId'],
			FlowDocId : getParameter('FlowDocId') || 0,
			PageSize:-1
		};
		aCombine.push(oHelper.Combine('wcm6_process', 'getFlowDocsOfContent', postData));
		aCombine.push(oHelper.Combine('wcm6_process', 'getProcessInfoOfContent', postData));
		oHelper.MultiCall(aCombine, this.pageLoaded);
	},
	pageLoaded : function(_transport,_json){		
		var json = _json['MULTIRESULT'];

		//images loaded		
		var sValue = TempEvaler.evaluateTemplater('template_imageroller', json['VIEWDOCUMENTS']);
		var CurrPage = parseInt($v(json['VIEWDOCUMENTS'],"CurrPageIndex"));	
		PageContext.CurrPage = CurrPage;		
		PageContext.PageCount = $v(json['VIEWDOCUMENTS'],"PageCount");	
		PageContext.ItemCount = $v(json['VIEWDOCUMENTS'],"Num");	
		//alert(sValue);
		Element.update($("imageroller"),sValue);
		showRollerTip();
		//showRollingImages();

		//props loaded
		var sValue = TempEvaler.evaluateTemplater('template_imageprops', json['VIEWDOCUMENT']);			
		Element.update($("imageprops"),sValue);		
		Element.update($("photodesc"),"<pre>"+$v(json,"ViewDocument.DocContent",false)+"</pre>");
		
		setTimeout(function(){
			PageContext.showProcessInfo(json);
		},500);

		Element.hide($("div_loading"));
		Element.show($("main"))		
	},
	showProcessInfo : function(_json){
		//1.决定显示哪些操作有关文档流转的按钮
		var pinfo = _json['PROCESSINFO'];
		//alert(pinfo)
		if(!pinfo || !$v(pinfo, 'FlowId') || $v(pinfo, 'FlowId') == '0') {//TODO
			return;
		}
		//else
		var actionId = "spStartFlow";
		var sFunc = "startFlow"
		PageContext.params['FlowId'] = $v(pinfo, 'FlowId');
		if($v(pinfo, 'InFlow') == 'true') { //尚未开始流转
			Element.hide('spReflow');
			Element.hide('spCease');
			Element.show('spStartFlow');
		}else{
			if($v(pinfo, 'ReInFlow') == 'true') { //流转已结束，显示“重新流转”操作
				Element.hide('spStartFlow');
				Element.hide('spCease');
				Element.show('spReflow');
				actionId = "spReflow";
				sFunc = "reflow";
			}else if($v(pinfo, 'StopFlow') == 'true') { //流转中，当前用户为发起人，可以强制结束
				Element.hide('spReflow');
				Element.hide('spStartFlow');
				Element.show('spCease');
				actionId = "spCease";
				sFunc = "ceaseFlow";
			}
		}
		
		PageContext.flowActionFunc = sFunc;
		if(!PageContext.flowAction){
			PageContext.flowAction = function(event){
				var params = {
					ContentType: 605,
					ContentId: PageContext.params["ObjectId"],
					FlowId: PageContext.params['FlowId'] || 0,
					DocTitle: $('divDocTitle').innerHTML,
					FlowDocId : getParameter('FlowDocId') || 0
				}	
				
				$workProcessor[PageContext.flowActionFunc](params);				
			}			
		}
		Event.stopObserving(actionId,"click",PageContext.flowAction);
		Event.observe(actionId,"click",PageContext.flowAction);		

		//2.1.获取数据		
		var json = $a(_json, 'FLOWDOCS.FLOWDOC');
		if(json == null || json.lenght == 0) {
			return;
		}
		//else
		Element.addClassName($("flowaction"),"flowaction");
		
		json = json.reverse();
		
		//2.2.显示流转甘特图
		sValue = TempEvaler.evaluateTemplater('gunter_template', _json);
		Element.update($('divGunter'), sValue);
		Element.show('divGunter');
		window.setTimeout(function(){
			var rows = $('divGunter').children;
			if(rows != null && rows.length > 0) {
				var curNode = rows(rows.length - 1);
				Element.addClassName(curNode, 'current_gunter_node');
				curNode.style.backgroundImage = 'url(../images/workflow/bg_tracing_'
					+ curNode.getAttribute('_flag', 2) + '.gif)';
			}
		}, 10);
	},
	refreshCurrRows : function(){//used for workflow	
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var aCombine = [];	
		//process info.
		var postData = {
			ContentType: 605,
			ContentId: PageContext.params['ObjectId'],
			FlowDocId : getParameter('FlowDocId') || 0,
			PageSize:-1
		};
		aCombine.push(oHelper.Combine('wcm6_process', 'getFlowDocsOfContent', postData));
		aCombine.push(oHelper.Combine('wcm6_process', 'getProcessInfoOfContent', postData));
		oHelper.MultiCall(aCombine, function(_transport,_json){
			PageContext.showProcessInfo(_json["MULTIRESULT"]);
		});
	}
});
PageContext.init();

function highlightNav(_nav){
	_nav.className = "navdesc_mouseover";
}

function normalizeNav(_nav){
	_nav.className = "navdesc";
}

function showScaledImage(_nav){
	var fn = _nav._file;
	$("currImage").src = mapWebFile(fn);
	document.getElementsByClassName("actived_size")[0].className = "";
	_nav.nextSibling.className="actived_size";
}

function showPhoto(_photoId,_chnlId,_recId){	
	var sUrl = window.location.href;
	var sSearch = window.location.search;
	sUrl = sUrl.substr(0,sUrl.indexOf(sSearch));
	sUrl += "?PhotoId="+_photoId;	
	sUrl += "&CurrPage="+PageContext.CurrPage;
	if(PageContext.params.SiteId > 0){
		sUrl += "&SiteId="+PageContext.params.SiteId;
	}else{
		sUrl += "&ChannelId="+_chnlId;
	}
	sUrl += "&RecId="+_recId;
	
	window.location.assign(sUrl);
}

function getFileType(_fn){	
	if(!_fn) return "未知";
	var fn = _fn.split(",")[0];
	if(!fn) return "未知";

	return fn.split(".")[1];
}


function onNext(){	
	if(PageContext.CurrPage >= PageContext.PageCount){
		$alert("后面已经没有图片了！");
		return false;
	}
	BasicDataHelper.call('wcm6_photo', 'query', Object.extend(PageContext.params,{PageSize:3,CurrPage:(++PageContext.CurrPage)}),false,function(_transport,_json){
		var sValue = TempEvaler.evaluateTemplater('template_imageroller', _json['VIEWDOCUMENTS']);
		Element.update($("imageroller"),sValue);
		showRollerTip();
	});
	return true;
}

function onPrevious(){	
	if(PageContext.CurrPage == 1){
		$alert("前面已经没有图片了！");
		return false;
	}
	BasicDataHelper.call('wcm6_photo', 'query', Object.extend(PageContext.params,{PageSize:3,CurrPage:(--PageContext.CurrPage)}),false,function(_transport,_json){
		var sValue = TempEvaler.evaluateTemplater('template_imageroller', _json['VIEWDOCUMENTS']);
		Element.update($("imageroller"),sValue);
		showRollerTip();
	});
	
	return true;
}

function showRollerTip(){
	var curr = PageContext.CurrPage*3
	var all = PageContext.ItemCount;
	var start = curr-2;
	if(start <= 0) start = 1;
	if(curr > all) curr = all;
	var tip = start + "-" + curr + "/" + all;
	Element.update($("tiptext"),tip);
}

function mapRollImage(_fns){
	var fn = _fns.split(",")[0];
	return mapWebFile(fn);
}

function mapWebFile(_fn){
	if(_fn.indexOf("W0") == 0){
		return "/webpic/"+_fn.substr(0,8)+"/"+_fn.substr(0,10)+"/"+_fn;
	}else{
		return "../../file/read_file.jsp?FileName=" + _fn;
	}
}

Event.observe(window,"load",function(){
	try{		
		PageContext.loadPage();
	}catch(e){
		//TODO logger
		//alert(e.message)
	}
});

function breakLine(_value,_lineLength){
	var lineLength = _lineLength || 30;
	if(!_value || _value.length <= lineLength) return _value;	
	var result = "";
	var source = _value;	
	var len=0;
	var schar;
	for(var i=0;schar=source.charAt(i);i++){
		result+=schar;
		len+=(schar.match(/[^\x00-\xff]/)!=null?2:1);
		if(len>= lineLength){
			result+="<br />";
			len=0;
		}
	}

	return result;
}