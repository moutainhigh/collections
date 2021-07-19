Ext.ns('com.trs.ui');
/**
*相关文档
*/
(function(){
	//private 
	var relateddocSelectBtn = "-select-btn";
	var relateddocBox = "-box";

	var template = {
		outer : [
			'<div class="XRelatedDoc">',
				'<input type="hidden" name="{0}" id="{0}" value="{1}" />',
				'<div class="relateddoc-select" id="{0}-select-btn"></div>',
				'<div class="relateddoc-box" id="{0}-box">{2}</div>',
			'</div>'
		].join(""),
		inner : [
			'<li class="relateddoc-item">',
				'<a href="', XConstants.BASE_PATH + 'com.trs.ui/relateddoc/DocumentShow.jsp', '?DocumentId={0}&ChannelId={2}" title="{0}" channelId="{2}" target="_blank">{1}</a>',
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
			return String.format(template.outer, sName, config['value'], innerHtml);
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
		selectRelatedDocs : function(){
			var config = this.initConfig;
			config['params'] = Ext.applyIf(config['params']||{}, {docId:'',channelId:''});
			var sName = config['name'];
			var dom = $(sName);
			var caller = this;
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'document/document_relations.html',
				title : wcm.LANG.DOCUMENT_PROCESS_17 || '相关文档管理',
				callback : function(info){
					var formatedRels = htmlRelatedDocs.call(caller, Object.deepClone(info));
					Element.update($(sName+"-box"), formatedRels.html);
					$(sName).value = formatedRels.ids;
				},
				dialogArguments : {
					relations : jsonRelatedDocs.call(this, sName),
					CurrDocId : config['params']['docId'],
					CurrChannelId : config['params']['channelId'],
					SelectType : config['params']['selectType']||'checkbox'
				}
			});
		},
		initActions : function(){
			if(this.initConfig['disabled']) return;
			Event.observe(this.initConfig['name'] + relateddocSelectBtn, 'click', this.selectRelatedDocs.bind(this));
		}
	});
})();