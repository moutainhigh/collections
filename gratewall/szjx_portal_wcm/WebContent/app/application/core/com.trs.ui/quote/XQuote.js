Ext.ns('com.trs.ui');
/**
*文档引用到...
*/
(function(){
	//private 
	var channelTreeSelectBtn = "-select-btn";
	var chnlSelectedWrap = "-wrap";
	var template = [
		'<div class="XQuote">',
			'<input type="hidden" name="{0}" id="{0}" value="" _initValue=""/>',
			'<div class="channelTree-select" id="{0}-select-btn" title="', '另存为', '..."></div>',
			'<div class="chnl-selected-wrap" id="{0}-wrap" quoteChnlsInput="{0}"></div>',
		'</div>'
	].join("");

	var itemTemplate = [
		'<div class="quote-chnl-row">',
			'<span class="removeQuoteChnls" chnlId={1}></span>',
			'<div class="chnl-text">{0}</div>',
			'<select name="quote-chnl-{2}" id="quote-chnl-{1}" chnlid="{1}">',
				'<option value="2">', '链接引用', '</option>',
				'<option value="3">', '镜像引用', '</option>',
			'</select>',
		'</div>'
	].join("");

	/**
	*将引用栏目的xml串信息转成html.
	*/
	var htmlQuoteChnls = function(_relations){
		if(!_relations) return{ids:"",html:""};
		var rels = $v(_relations, "QUOTECHNLS.QUOTECHNL");	
		if(rels==null || rels==false){
			_relations["QUOTECHNLS"] = _relations["QUOTECHNLS"] || {};
			rels = _relations["QUOTECHNLS"]["QUOTECHNL"] = [];
		}else if(!Array.isArray(rels)){
			_relations["QUOTECHNLS"] = _relations["QUOTECHNLS"] || {};
			var tmpArr = _relations["QUOTECHNLS"]["QUOTECHNL"] = [];
			tmpArr.push(rels);
			rels = tmpArr;
		}	
		var sHtml = [];
		var arId = [];
		var sModal = [];
		var rel = null;
		for(var i=0,len=rels.length;i<len;i++){	
			rel = rels[i];	
			if(!rel) continue;
			sHtml.push(String.format(itemTemplate, $transHtml($v(rel,"CHNL.CHNLNAME")), $v(rel,"CHNL.ID"), arguments[1]));
			arId.push($v(rel,"CHNL.ID"));
			sModal.push($v(rel,"CHNL.DOCMODAL"));

		}	
		return {ids:arId.join(","),html:sHtml.join(""),smodal:sModal};		
	};

	com.trs.ui.XQuote = Ext.extend(com.trs.ui.BaseComponent, {
		/**
		*name,value,desc,rootId,treeType
		*/
		getHtml : function(){
			var config = this.initConfig;
			var sName = config['name'];
			return String.format(template, config['name']);
		},
		afterRender : function(){
			// by CC 20120423 在初始化完成后，在这边获取所有的引用栏目
			var config = this.initConfig;
			var objectId = config['objectId'];
			var sName = config['name'];
			
			//2 发送请求 获取所有的引用栏目及文档MODAL
			if(objectId == 0) return;
			new Ajax.Request(XConstants.BASE_PATH + "com.trs.ui/quote/XQuoteChnlHandler.jsp",
			{
				parameters : "ObjectId=" + objectId,
				method : 'post',
				onSuccess : function(transport){
					var json = eval("(" + transport.responseText + ")");
					json = htmlQuoteChnls(json,sName);
					Element.update(sName + chnlSelectedWrap, json['html']);
					$(sName).value = json['ids'];
					$(sName).setAttribute("_initValue",json['ids']);
					var sModal = json['smodal'];
					var doms = document.getElementsByName('quote-chnl-' + sName);
					for(var i=0; i<doms.length; i++){
						var dom = doms[i];
						for(var j=0; j< dom.length; j++){
							if(sModal[i] == dom[j].value){
								dom[j].selected = "selected";
							}
						}
					}
				}
			});
		},
		/**
		*获取引用及镜像信息[array of quote, array of mirror]
		*获取删除的引用信息[array of sDelChannelIds]
		*/
		getQuotes : function(){
			var config = this.initConfig;
			var sName = config['name'];
			var doms = document.getElementsByName('quote-chnl-' + sName);
			var quote = [], mirror = [];
			for(var i = 0; i < doms.length; i++){
				if(doms[i].value == 2){
					quote.push(doms[i].getAttribute('chnlid'));
				}else{
					mirror.push(doms[i].getAttribute('chnlid'));
				}
			}
			// 获取到删除的引用栏目IDS
			var initValue = $(sName).getAttribute("_initValue");
			var lastValue = $(sName).value;
			var delChnlIds = queryDelChnlIds(initValue, lastValue);
			return [quote, mirror, delChnlIds.split(',')];
		},
		selectChannelTree : function(){
			var config = this.initConfig;
			var sName = config['name'];
			var chnlIds = $(sName).value;
			var params = {
				MultiSiteType:1,
				SiteTypes:'0,4',
				treeType : config["treeType"] == null ? 0 : 1,//1表示radio
				selectedValue:chnlIds,
				ExcludeSelf:1,
				RightIndex:31,
				CurrChannelId:config['currchnlid']||0
			};

			wcm.ChannelTreeSelector.selectChannelTreeTree(params, function(_args){
				//save the old value.
				var doms = document.getElementsByName('quote-chnl-' + sName);
				var data = {};
				for(var i = 0; i < doms.length; i++){
					data[doms[i].id] =  doms[i].value;
				}

				var arIds = _args[0]|| [];			
				var arNames = _args[1]||[];				
				var aHtml = [];

				for(var i = 0; i < arIds.length; i++){
					aHtml.push(String.format(itemTemplate, $transHtml(arNames[i]), arIds[i], sName));
				}

				Element.update(config['name'] + chnlSelectedWrap, aHtml.join(""));

				//restore the old value.
				var doms = document.getElementsByName('quote-chnl-' + sName);
				for(var i = 0; i < doms.length; i++){
					if(data[doms[i].id] != null){
						doms[i].value = data[doms[i].id];
					}					
				}

				$(sName).value = arIds.join(",");
			});			
		},
		deleteQuoteChnl : function(event){
			event = window.event || event;
			var srcEl = Event.element(event);
			if(!Element.hasClassName(srcEl,"removeQuoteChnls")){
				return;
			}
			var divEl = findItem(srcEl,'quote-chnl-row');//srcEl.parentNode;
			if(divEl.tagName != 'DIV')
				return;
			var quoteChnlBox = findItem(divEl,'chnl-selected-wrap');
			if(!quoteChnlBox)
				return;
			//暂存待删除的元素的信息
			var nDeleteChnlId = srcEl.getAttribute("chnlId");
			//修改id序列值
			var inputElId = quoteChnlBox.getAttribute("quoteChnlsInput");
			//移除当前条目
			quoteChnlBox.removeChild(divEl);
			//从value值中将当前删除的id移除
			var inputEl = $(inputElId);
			var aChnlIds = inputEl.value.split(",");
			for(var i=aChnlIds.length-1;i>=0;i--){
				var chnlId = aChnlIds[i];
				if(nDeleteChnlId != chnlId){
					continue;
				}
				aChnlIds.splice(i, 1);
				break;
			}
			inputEl.value = aChnlIds.join(",");
			Event.stop(event);
			return false;
		},
		initActions : function(){
			if(this.initConfig['disabled']) return;
			Event.observe(this.initConfig['name'] + channelTreeSelectBtn, 'click', this.selectChannelTree.bind(this));
			Event.observe(this.initConfig['name'] + chnlSelectedWrap, 'click', this.deleteQuoteChnl.bind(this));
		}
	});
})();

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

function queryDelChnlIds(_initValue, _lastValue){
	var bIsIn = false;
	var sDelChannelIds = "-1";
	//初始化时，如果获取到的引用栏目为-1,则直接不进行其他操作了;
	if(_initValue != ""){
		var arrayInitChannelIds = _initValue.split(",");
		var arraysToChannelIds = _lastValue.split(",");
		for(var i=0; i<arrayInitChannelIds.length; i++){
			for(var j=0; j<arraysToChannelIds.length; j++){
				if(arrayInitChannelIds[i] == arraysToChannelIds[j]){
					bIsIn = true;
					continue;
				}
			}
			if(!bIsIn){
				if(sDelChannelIds == "-1"){ sDelChannelIds = arrayInitChannelIds[i];} else{
					sDelChannelIds = sDelChannelIds + "," + arrayInitChannelIds[i];
				}
			} else{
				bIsIn = false;
			}
		}
	}
	return sDelChannelIds;
}