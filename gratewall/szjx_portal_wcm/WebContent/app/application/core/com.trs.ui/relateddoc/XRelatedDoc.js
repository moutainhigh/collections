Ext.ns('com.trs.ui');
/**
*相关文档
*/
(function(){
	//private 
	var relateddocSelectBtn = "-select-btn";
	var relateddocAddBtn = "-add-btn";
	var relateddocBox = "-box";

	var template = {
		outer : [
			'<div class="XRelatedDoc">',
				'<input type="hidden" name="{0}" id="{0}" value="{1}" relationViewId = "{3}"/>',
				'<span class="relateddoc-select" id="{0}-select-btn">','点击选择{4}','</span>',
				'<span class="relateddoc-add" id="{0}-add-btn">','点击新增{4}','</span>',
				'<div class="relateddoc-box" id="{0}-box" relationInput={0}>{2}</div>',
			'</div>'
		].join(""),
		inner : [
			'<li class="relateddoc-item">',
				'<span class="removeRelationDoc" docId={0}></span>',
				'<a href="', WCMConstants.WCM6_PATH + 'document/document_show.jsp', '?DocumentId={0}&ChannelId={2}" title="{0}" channelId="{2}" target="_blank">{1}</a>',
			'</li>'
		].join("")
	};

	/**
	*获取某相关文档字段的json表示
	*/
	var jsonRelatedDocs = function(sName){

		var box = $(sName + "-box");
		var doms = box.getElementsByTagName("a");
		var json = {
			RELATIONS : {
				RELATION : []
			}
		};
		var relDocs = json['RELATIONS']['RELATION'];
		for (var i = 0; i < doms.length; i++){
			relDocs.push({
				RELDOC : {
					ID : doms[i].getAttribute("title"),
					CHANNELID : doms[i].getAttribute("channelId"),
					TITLE : doms[i].innerHTML
				}
			});
		}
		return json;
	};
	/**
	*将相关文档字段的xml串信息转成html.
	*/
	var htmlRelatedDocs = function(_relations){
		if(!_relations) return{ids:"",html:""};
		var rels = $v(_relations, "RELATIONS.RELATION");	
		if(rels==null || rels==false){
			_relations["RELATIONS"] = _relations["RELATIONS"] || {};
			rels = _relations["RELATIONS"]["RELATION"] = [];
		}else if(!Array.isArray(rels)){
			_relations["RELATIONS"] = _relations["RELATIONS"] || {};
			var tmpArr = _relations["RELATIONS"]["RELATION"] = [];
			tmpArr.push(rels);
			rels = tmpArr;
		}	
		var sHtml = [];
		var arId = [];
		var rel = null;
		for(var i=0,len=rels.length;i<len;i++){	
			rel = rels[i];	
			if(!rel) continue;
			sHtml.push(String.format(template.inner, $v(rel,"RELDOC.ID"), $transHtml($v(rel,"RELDOC.TITLE")), $v(rel,"RELDOC.CHANNELID")));
			arId.push($v(rel,"RELDOC.ID"));
		}	
		return {ids:arId.join(","),html:sHtml.join("")};		
	};

	var renderRelatedDocsByAjax = function(sName, sKey, sValue){
		if(sValue.trim() == "") return;
		new Ajax.Request(XConstants.BASE_PATH + "com.trs.ui/relateddoc/XRelatedDocHandler.jsp",
		{
			parameters : sKey + "=" + sValue,
			method : 'post',
			onSuccess : function(transport){
				var json = eval("(" + transport.responseText + ")");
				json = htmlRelatedDocs(json);
				Element.update(sName + relateddocBox, json['html']);
				$(sName).value = json['ids'];
			}
		});
	};

	var adapterRelatedDocs = function(items){
		var json = {RELATIONS : {RELATION : []}};
		var relations = json['RELATIONS']['RELATION'];
		for (var i = 0; i < items.length; i++){
			if(!items[i]) continue;
			relations.push({RELDOC : Ext.Json.toUpperCase(items[i])});
		}
		return json;
	};

	com.trs.ui.XRelatedDoc = Ext.extend(com.trs.ui.BaseComponent, {
		getHtml : function(){
			var config = this.initConfig;
			var sName = config['name'];	
			var innerHtml = "";
			if(config['items']){
				var json = adapterRelatedDocs(config['items']);
				json = htmlRelatedDocs(json);
				config['value'] = config['value'] || json['ids'];
				innerHtml = json['html'];
			}
			return String.format(template.outer, sName, config['value'], innerHtml, 0, config['anotherName']);
		},
		afterRender : function(){
			com.trs.ui.XRelatedDoc.superclass.afterRender.apply(this, arguments);
			var config = this.initConfig;
			if(config['items']) return;
			var args = config['objectId'] ? 
					[config['name'], 'objectId', config['objectId']] :
						[config['name'], 'objectIds', config['value']];
			renderRelatedDocsByAjax.apply(window, args);
		},
		selectRelatedDocs : function(relationViewId){
			var config = this.initConfig;
			config['params'] = Ext.applyIf(config['params']||{}, {docId:'',channelId:''});
			var sName = config['name'];
			var sAnotherName = config['anotherName'];
			var dom = $(sName);
			var caller = this;
			var sTitle = wcm.LANG.DOCUMENT_PROCESS_17 || '相关文档管理';
			var url = WCMConstants.WCM6_PATH + 'document/document_relations.html';
			if(relationViewId > 0){
				sTitle = String.format("{0}数据管理",sAnotherName);
				url = WCMConstants.WCM6_PATH + 'document/metaview_document_relations.jsp?ViewName=' + encodeURIComponent(sAnotherName);
			}
			FloatPanel.open({
				src : url,
				title : sTitle,
				callback : function(info){
					var formatedRels = htmlRelatedDocs.call(caller, Object.deepClone(info));
					Element.update($(sName+"-box"), formatedRels.html);
					$(sName).value = formatedRels.ids;
				},
				dialogArguments : {
					relations : jsonRelatedDocs.call(this, sName),
					CurrDocId : config['params']['docId'],
					CurrChannelId : config['params']['channelId'],
					relationViewId : relationViewId
				}
			});
		},
		addRelatedDocs : function(relationViewId){
			if(relationViewId == 0){
				alert("当前相关文档字段未设置相关视图！");
				return;
			}
			var config = this.initConfig;
			var sName = config['name'];
			$openMaxWin(WCMConstants.WCM6_PATH + 'application/' + relationViewId + '/metaviewdata_addedit.jsp?FromRelationDoc=1&RelationName=' + sName);
			return;
		},
		deleteRelDoc : function(event){
			event = window.event || event;
			var srcEl = Event.element(event);
			if(!Element.hasClassName(srcEl,"removeRelationDoc")){
				return;
			}
			var liEl = findItem(srcEl,'relateddoc-item');//srcEl.parentNode;
			if(liEl.tagName != 'LI')
				return;
			var relationDocBox = findItem(liEl,'relateddoc-box');
			if(!relationDocBox)
				return;
			//暂存待删除的元素的信息
			var nDeleteDocId = srcEl.getAttribute("docId");
			//修改id序列值
			var inputElId = relationDocBox.getAttribute("relationInput");
			//移除当前条目
			relationDocBox.removeChild(liEl);
			//从value值中将当前删除的id移除
			var inputEl = $(inputElId);
			var aDocIds = inputEl.value.split(",");
			for(var i=aDocIds.length-1;i>=0;i--){
				var docId = aDocIds[i];
				if(nDeleteDocId != docId){
					continue;
				}
				aDocIds.splice(i, 1);
				break;
			}
			inputEl.value = aDocIds.join(",");
			Event.stop(event);
			return false;
		},
		initActions : function(){
			if(this.initConfig['disabled']) return;
			var relationViewId = this.initConfig['relationViewId'] || 0;
			Event.observe(this.initConfig['name'] + relateddocSelectBtn, 'click', this.selectRelatedDocs.bind(this, relationViewId));
			Event.observe(this.initConfig['name'] + relateddocAddBtn, 'click', this.addRelatedDocs.bind(this, relationViewId));
			Event.observe(this.initConfig['name'] + relateddocBox, 'click', this.deleteRelDoc.bind(this));
		}
	});
})();

var relationItemTemplate = ['<li class="relateddoc-item">','<span class="removeRelationDoc" docId={0}></span>',
	'<a href="', WCMConstants.WCM6_PATH + 'document/document_show.jsp', '?DocumentId={0}&ChannelId={2}" title="{0}" channelId="{2}" target="_blank">{1}</a>',
	'</li>'
].join("");

function refreshAddRelatedDocs(infos){
	var relationName = infos.RelationName;
	var relationDocBox = $(relationName +"-box");
	var currInnerHTML = relationDocBox.innerHTML;
	var addHtml = String.format(relationItemTemplate, infos.docId, $transHtml(infos.docTitle), infos.channelId);
	var newHtml = currInnerHTML == null ? addHtml : currInnerHTML + addHtml;
	Element.update(relationDocBox, newHtml);
	var relationIdsEl = $(relationName);
	var currIds = relationIdsEl.value;
	if(currIds != ''){
		relationIdsEl.value = currIds + "," + infos.docId;
	}else{
		relationIdsEl.value = infos.docId;
	}
}
function findItem(t, cls, attr, aPAttr){
	aPAttr = aPAttr || [];
	while(t!=null&&t.tagName!='BODY'&& t.nodeType==1){
		for (var i = 0; i < aPAttr.length; i++){
			if(dom.getAttribute(aPAttr[i]) != null) return 0;
		}
		if(cls && Element.hasClassName(t, cls))return t;
		if(attr && t.getAttribute(attr, 2)!=null)return t;
		t = t.parentNode;
	}
	return null;
}