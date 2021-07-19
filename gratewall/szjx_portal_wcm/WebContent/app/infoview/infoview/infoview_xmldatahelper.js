var XmlDataHelper = {
	getDoc : function(){
		var xml = [
			'<?xml version="1.0" encoding="UTF-8"?>',
			'<my:myFields xmlns:my="http://schemas.microsoft.com/office/infopath/"/>'
		].join('\n');
		return loadXml(xml);
	},
	fetchData : function(){
//		window._databeforeSubmit = {};
//		if(exCenter._beforeSubmit(window._databeforeSubmit)===false)return;
		var doc = this.getDoc(), root = doc.documentElement;
		var json = EleHelper.jsonData();
		EleHelper.jsonIntoEle(doc, root, json);
		return doc.xml;
	},
	initData : function(xml, bFromExtranet) {
		var doc = loadXml(xml);
		if(doc == null)return null;
		var root = doc.documentElement;
		this._dealData(document.body, root, bFromExtranet);
	},
	_dealData : function(hNode, xNode, bFromExtranet) {
		if(!hNode || !xNode)return;
		var hChilds = hNode.childNodes;
		for(var i=0; i<hChilds.length; i++) {
			var hcn = hChilds[i];
			if(!hcn.tagName) continue;
			if(hcn.getAttribute('trs_cloned', 2)){
				hcn.removeAttribute('trs_cloned');
				continue;
			}
			var xct = hcn.getAttribute("xd:xctname", 2);
			var xp = hcn.getAttribute("trs_temp_id", 2);
			var eleType = hcn.getAttribute("element-type", 2);
			if(!xct || !xp || !eleType){
				this._dealData(hcn, xNode, bFromExtranet);
				continue;
			}
			if(EleHelper.isRepeatEle(hcn)){
				var xmlNodes = XmlHelper.queryNodes(xNode, xp);
				if(xmlNodes.length==0)continue;
				var tmp = hcn.nextSibling;
				for(var j=0; j<xmlNodes.length; j++) {
					var c1 = hcn;
					if(j != 0) {
						c1 = hcn.cloneNode(true);
						c1.setAttribute('trs_cloned', 1);
						c1.id = c1.id + "_" + new Date().getTime();
						var childEls = c1.getElementsByTagName("*");
						for(var childIndex=0;childIndex<childEls.length;childIndex++){
							var childEl = childEls[childIndex];
							if(!childEl.disabled){
								childEl.id = childEl.id + "_" +new Date().getTime();
								var currElXctName = childEl.getAttribute('xd:xctname',2);
								if(!currElXctName)continue;
								try{
								if(currElXctName == 'DTPicker_DTText' && makeDateCalendarRule){
									makeDateCalendarRule(childEl);
								}
								if(currElXctName == 'RichText' && makeTextEditorRule){
									makeTextEditorRule(childEl);
								}
								}catch(error){
									//do nothing
								}
							}
						}
						hNode.insertBefore(c1, tmp);
					}
					this._dealData(c1, xmlNodes[j], bFromExtranet);
				}
				continue;
			}
			if(EleHelper.isContainerEle(hcn)){
				this._dealData(hcn, xNode, bFromExtranet);
				continue;
			}
			var xmlNodes = XmlHelper.queryNodes(xNode, xp);
			if(xmlNodes.length==0)continue;
			var tmp = hcn.nextSibling;
			var bFirst = true;
			for(var j=0; j<xmlNodes.length; j++) {
				var c1 = hcn;
				//附件特殊处理一下
				if(eleType==5 && xmlNodes[j].getAttribute('blank_node')=='1')continue;
				if(!bFirst) {
					c1 = hcn.cloneNode(true);
					c1.id = c1.id + '_' + (j+1);
					c1.setAttribute('trs_cloned', 1);
					hNode.insertBefore(c1, tmp);
				}
				var v = (typeof xmlNodes[j]=='string') ? xmlNodes[j] : XmlHelper._xvalue(xmlNodes[j]);
				if(bFromExtranet){
					if(v != '')
					EleHelper.setValue(c1, xmlNodes[j]);
				}else EleHelper.setValue(c1, xmlNodes[j]);
				bFirst = false;
			}
		}
	}
}