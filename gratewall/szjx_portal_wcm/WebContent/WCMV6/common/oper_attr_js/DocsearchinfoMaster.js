com.trs.wcm.DocsearchinfoMaster = Class.create("wcm.DocsearchinfoMaster");
Object.extend(com.trs.wcm.DocsearchinfoMaster.prototype,com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.DocsearchinfoMaster.prototype,{
	call : function(){
		var oSearchInfo = null;
		var aTop = (top.actualTop || top);
		var main = $MessageCenter.getMain();
		if(main && main.PageContext && main.PageContext.getQueryInfoDetail) {
			oSearchInfo = main.PageContext.getQueryInfoDetail();
		}
		if(oSearchInfo == null) {
			if(aTop.PageContext.QueryInfoDetail != null) {
				oSearchInfo = aTop.PageContext.QueryInfoDetail;
			}else{
				//callback操作是显示必须的
				OperAttrPanel.callBack();
				return;
			}
		}else{
			//在top页面上保存一下，以免丢失
			aTop.PageContext.QueryInfoDetail = oSearchInfo;
		}
		//else
		//alert($toQueryStr(oSearchInfo));
		var sObjectType = 'docsearchinfo';
		PageContext.params["type"] = 'docsearchinfo';

		if(oSearchInfo['CONTAINSCHILDREN']) {
			oSearchInfo['RECURSIVE'] = '是';
		}

		ObjectAttributePanel.show();
		ObjectAttributePanel.loadData(CONST.ObjectAttribute[sObjectType], sObjectType,
			this.ObjectRight, oSearchInfo);

		PageContext.displayInfo(oSearchInfo);

		OperAttrPanel.callBack();
	}
});

Object.extend(PageContext, {
	displayInfo : function(_info){
		var bTooMuchRegion = false;
		var regionLen = 0, othersHeight = 0;
		if(_info['REGION'] && (regionLen = _info['REGION'].length) > 5){
			//alert(Element.getDimensions(document.body)["height"])
			bTooMuchRegion = true;
		}
		for( var sName in _info){
			//alert(sName)
			var sName = 'div' + sName;
			var eDiv = $(sName);
			if(eDiv != null) {
				Element.show(eDiv);
				if(bTooMuchRegion && sName != 'divREGION') {
					othersHeight += Element.getDimensions(eDiv)["height"];
				}
			}
		}
		if(bTooMuchRegion) {
			var maxHeight = (Element.getDimensions(document.body)["height"] - othersHeight - 95);
			if(regionLen*20 > maxHeight) {
				$('divRegionContainer').style.height = maxHeight + 'px';
			}
		}

		if(_info['CRUSER'] || _info['DOCTITLE']) {
			Element.show('sep1');
		}
		if(_info['REGION']
			|| _info['STARTDATE'] || _info['CRTIMEINTERVAL'] 
			|| _info['STARTPUBDATE'] || _info['PUBTIMEINTERVAL']) {
			Element.show('sep2');
		}
		if(_info['DOCSOURCENAME'] || _info['DOCSTATUS']) {
			Element.show('sep3');
		}
	}
});
