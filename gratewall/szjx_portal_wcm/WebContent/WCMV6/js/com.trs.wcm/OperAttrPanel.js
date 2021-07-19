$package('com.trs.wcm');
var _PAENL_ = com.trs.wcm.Panel = Class.create("wcm.Panel");
Object.extend(_PAENL_,{
	CSS_PANEL_EXPAND : 'oper_attr_panel_expand',
	CSS_PANEL_COLLAPSE : 'oper_attr_panel_collapse',
	ID_OPER_TOGGLE_SUF : '_toggle',
	ID_OPER_BODY_SUF : '_body',
	ID_OPER_CONTENT_SUF : '_content',
	ID_OPER_TITLE_SUF : '_title'
});
com.trs.wcm.Panel.prototype = {
	initialize: function(_sPanelId){
		this.panelId = _sPanelId;
		this.bindToggleEvent();
		this.contentId = this.panelId+_PAENL_['ID_OPER_CONTENT_SUF'];
		this.bodyId = this.panelId+_PAENL_['ID_OPER_BODY_SUF'];
	},
	_process : function(){
	},
	bindEvents : function(){
	},
	_bindEvents : function(){
	},
	hide : function(){
		Element.hide($(this.panelId));
	},
	show : function(){
		Element.show($(this.panelId));
	},
	setTitle : function(_sTitle){
		var eTitle = $(this.panelId+_PAENL_['ID_OPER_TITLE_SUF']);
		Element.update(eTitle,_sTitle);
	},
	setContent : function(_sHtml){
		var eContent = $(this.panelId+_PAENL_['ID_OPER_CONTENT_SUF']);
		Element.update(eContent,_sHtml);
	},
	_getContentElement_ : function(){
		var eContent = $(this.panelId+_PAENL_['ID_OPER_CONTENT_SUF']);
		return eContent;
	},
	loadData : function(){
		var args = $A(arguments);
		var sTitle = args.shift();
		this.setTitle(sTitle);
		//avoid memory leak
		this.destroy();
		this._process.apply(this,args);
	},
	bindToggleEvent : function(){
		var eToggle = $(this.panelId+_PAENL_['ID_OPER_TOGGLE_SUF']);
		Event.stopAllObserving(eToggle,'click');
		Event.observe(eToggle,'click',this.toggleColumn.bind(this));
	},
	toggleColumn : function(){
		var eContent = $(this.panelId+_PAENL_['ID_OPER_CONTENT_SUF']);
		var eToggle = $(this.panelId+_PAENL_['ID_OPER_TOGGLE_SUF']);
		if(eContent&&eContent.style.display!='none'){
			eToggle.className = _PAENL_['CSS_PANEL_EXPAND'];
			eContent.style.height = eContent.offsetHeight +'px';
			var iStep = parseInt(eContent.offsetHeight/20);
			this.collapse(iStep);
		}
		else{
			eToggle.className = _PAENL_['CSS_PANEL_COLLAPSE'];
			var iMaxHeight = parseInt(Element.getDimensions(eContent)['height']);
			var iStep = parseInt(iMaxHeight/20);
			eContent.style.height = '0px';
			eContent.style.display = '';
			this.expand(iStep,iMaxHeight);
		}
	},
	collapse : function(_iStep){
		var eContent = $(this.panelId+_PAENL_['ID_OPER_CONTENT_SUF']);
		if(parseInt(eContent.style.height)>_iStep){
			eContent.style.height = (parseInt(eContent.style.height)-_iStep)+'px';
			setTimeout(function(){
				this.collapse(_iStep);
			}.bind(this),1);
		}
		else{
			eContent.style.height = '';
			eContent.style.display = 'none';
		}
	},
	expand : function(_iStep,_iMaxHeight){
		var eContent = $(this.panelId+_PAENL_['ID_OPER_CONTENT_SUF']);
		if(parseInt(eContent.style.height)<(_iMaxHeight-_iStep)){
			eContent.style.height = (parseInt(eContent.style.height)+_iStep)+'px';
			setTimeout(function(){
				this.expand(_iStep,_iMaxHeight);
			}.bind(this),1);
		}
		else{
			eContent.style.height = '';
		}
	},
	destroy : function(){
		$destroy(this);
	}
};
var _OPER_PANEL_ = com.trs.wcm.OperatorPanel = Class.create("wcm.OperatorPanel");
Object.extend(_OPER_PANEL_,{
	/*特殊权限,在多个对象权限合并时按或操作,其他按与操作*/
	SPECIAL_RIGHT_INDEX : [38,39],
	CSS_OPERS : 'wcm_operators',
	CSS_OPERS_MORE : 'wcm_operators_more',
	CSS_OPER_ROW_OUTER : 'wcm_operator',
	CSS_OPER_ROW_INNER : 'wcm_operator_row',
	CSS_OPER_ROW_MORE_OUTER : 'wcm_operator_more',
	CSS_OPER_ROW_OUTER_ACTIVE : 'wcm_operator_active',
	CSS_OPER_SEP_OUTER : 'wcm_operator_seperate',
	ATT_OPER_KEY : 'operator_type',
	ATT_OPER_OBJECT : 'operator_object',
	TYPE_SEPERATE : 'seperate',
	ID_OPER_MORE_BTN_SUF : '_more_btn',
	ID_OPER_MORE_SUF : '_more'
});
Object.extend(com.trs.wcm.OperatorPanel.prototype,com.trs.wcm.Panel.prototype);
Object.extend(com.trs.wcm.OperatorPanel.prototype,{
	processOperators : function(_type,_sRight,_iDisplayNum,_OpersCanNotDo){
		var sHtml = '<div class="'+_OPER_PANEL_['CSS_OPERS']+'">';
		var sMoreHtml = '<div id="'+this.panelId+_OPER_PANEL_['ID_OPER_MORE_SUF']+'" class="'+_OPER_PANEL_['CSS_OPERS_MORE']+'" style="display:none">';
		var arrOper = com.trs.wcm.AllOperators[_type];	
		var cntDisplay = 0;
		var hasMore = false;
		_OpersCanNotDo = _OpersCanNotDo || {};
		var HasOper = false;
		this.Operators = [];
		for(var i=0,n=(arrOper)?arrOper.length:0;i<n;i++){
			var oper = arrOper[i];
			oper["operType"] = _type;
//			if(_type=='documentInChannel'){
//				alert(_sRight+","+oper["rightIndex"]+":"+isAccessable4WcmObject(_sRight,oper["rightIndex"]));
//			}
			if(isAccessable4WcmObject(_sRight,oper["rightIndex"])&&!_OpersCanNotDo[oper["operKey"]]){
				if(cntDisplay<_iDisplayNum){
					//在默认面板上不画出分隔线
					if(oper["operKey"].toLowerCase()==_OPER_PANEL_['TYPE_SEPERATE'])continue;
					HasOper = true;
					sHtml += this._processOperator_(oper);
				}
				else{
					//分隔线的逻辑判断
					if(oper["operKey"].toLowerCase()==_OPER_PANEL_['TYPE_SEPERATE']){
						//第一个分隔线不画出来
						if(!hasMore)continue;
						//连续两个分隔线不画出来
						if(this.lastOperKey==_OPER_PANEL_['TYPE_SEPERATE'])continue;
						//最后一个分隔线不画出来
						if(i==n-1)continue;
						//TODO最后连续两根
					}
					sMoreHtml += this._processOperator_(oper,true);
					this.lastOperKey = oper["operKey"].toLowerCase();
					hasMore = true;
				}
				cntDisplay++;
			}
		}
		sMoreHtml += '</div>';
		sHtml += '</div>';
		if(hasMore){
			sHtml += sMoreHtml;
		}
		return {"html":sHtml,"hasMore":hasMore,"HasOper":HasOper};
	},
	_processOperator_ : function(_oOper,_bMore){
		var sEvents = '';
		var sInnerClass = _OPER_PANEL_['CSS_OPER_ROW_INNER']+' '+_oOper["operType"]+'_'+_oOper["operKey"]+' '+_oOper["operKey"];
		var sOperId = _oOper["operType"]+'_'+_oOper["operKey"];
		if(_oOper["operKey"]!=_OPER_PANEL_['TYPE_SEPERATE']){
			sOuterClass = _OPER_PANEL_['CSS_OPER_ROW_OUTER'];
			sEvents = 'mousemove_fun="mousemove" mouseout_fun="mouseout" click_fun="fireEvent"';
		}
		else{
			sOuterClass = _OPER_PANEL_['CSS_OPER_SEP_OUTER'];
		}
		sOuterClass += (!_bMore)?'':' '+_OPER_PANEL_['CSS_OPER_ROW_MORE_OUTER'];
		this.Operators.push(_oOper);
		return '<div id="' + sOperId + '" class="' + sOuterClass + '" '+ sEvents 
			+ ' title="'+ _oOper["operDesc"] + '"'
			+ _OPER_PANEL_['ATT_OPER_KEY'] + '="' + _oOper["operKey"] + '" '
			+ _OPER_PANEL_['ATT_OPER_OBJECT'] + '="' + _oOper["operType"] + '">'
			+ '<div class="' +sInnerClass +'">'
			+ _oOper["operName"] + '</div></div>';
	},
	recycle : function(){
		var eOldMoreOperators = $(this.panelId+_OPER_PANEL_['ID_OPER_MORE_SUF']);
		if(eOldMoreOperators){
			eOldMoreOperators.parentNode.removeChild(eOldMoreOperators);
		}
		this.setContent('');
	},
	_process : function(_sType,_sRight,_iDisplayNum,_OpersCanNotDo){
		var o = this.processOperators(_sType,_sRight,_iDisplayNum,_OpersCanNotDo);
		this.recycle();
		this.setContent(o["html"]);
		if(!o["HasOper"]){
			this.hide();
		}
		var eMoreBtn = $(this.panelId+_OPER_PANEL_['ID_OPER_MORE_BTN_SUF']);
		if(o['hasMore']){
			Element.show(eMoreBtn);
			var eMoreOperators = $(this.panelId+_OPER_PANEL_['ID_OPER_MORE_SUF']);
			document.body.appendChild(eMoreOperators);
			eMoreBtn.bubbleMore = new com.trs.wcm.BubblePanel(eMoreOperators);
		}
		else{
			Element.hide(eMoreBtn);
		}
	},
	destroy : function(){
		var eMoreBtn = $(this.panelId+_OPER_PANEL_['ID_OPER_MORE_BTN_SUF']);
		if(eMoreBtn&&eMoreBtn.bubbleMore){
			eMoreBtn.bubbleMore.destroy();
			eMoreBtn.bubbleMore = null;
		}
	}
});
var _ATTR_PANEL_ = com.trs.wcm.AttributePanel = Class.create("wcm.AttributePanel");
Object.extend(_ATTR_PANEL_,{
	ID_ATTR_TEMPLATE_PRE : 'template_',
	ID_ATTR_MORE_BTN_SUF : '_more_btn',
	ID_ALL_TEMPLATES : 'object_attribute_templates'
});
Object.extend(com.trs.wcm.AttributePanel.prototype,com.trs.wcm.Panel.prototype);
Object.extend(com.trs.wcm.AttributePanel.prototype,{
	_process : function(_sObjectType,_sRight,_oJson){
		var eTemplate = $(_ATTR_PANEL_['ID_ATTR_TEMPLATE_PRE']+_sObjectType);
		if(!eTemplate){
			eTemplate = AttributeHelper.GetTemplate(_sObjectType);
			if(!eTemplate)return;
		}
		var sValue = TempEvaler.evaluateTemplater(eTemplate , _oJson , {});
		this.setContent(sValue);
		var eContent = this._getContentElement_();
		this.arrEvOb = [];
		this.arrRecycleOb = [];
		UITransformer.transformAll(eContent,_sRight,this.arrEvOb,this.arrRecycleOb);
		var eMoreBtn = $(this.panelId+_ATTR_PANEL_['ID_ATTR_MORE_BTN_SUF']);
		if(eMoreBtn&&this.onMore){
			Element.show(eMoreBtn);
			eMoreBtn.onMore = this.onMore;
		}
		else if(eMoreBtn){
			Element.hide(eMoreBtn);
			eMoreBtn.onMore = null;
		}
	},
	destroy : function(){
		var eMoreBtn = $(this.panelId+_OPER_PANEL_['ID_OPER_MORE_BTN_SUF']);
		if(eMoreBtn)eMoreBtn.onMore = null;
		if(this.arrEvOb){
			for(var i=0, n=this.arrEvOb.length;i<n;i++){
				Event.stopAllObserving(this.arrEvOb[i]);
			}
		}
		if(this.arrRecycleOb){
			for(var i=0, n=this.arrRecycleOb.length;i<n;i++){
				try{
					$destroy(this.arrRecycleOb[i]);
				}catch(err){
					//Just Skip It.
				}
			}
		}
		this.arrEvOb = null;
		this.arrRecycleOb = null;
	}
});
/*需要一步步补充*/
var CONST ={
	ObjectTitle : {
		"channel" : "栏目操作任务",
		"channels" : "栏目操作任务",
		"website" : "站点操作任务",
		"websites" : "站点操作任务",
		"channelMaster" : "栏目操作任务",
		"extendfield" : "扩展字段操作任务",
		"extendfields" : "扩展字段操作任务",
		"template" : "模板操作任务",
		"templates" : "模板操作任务",
		"photo"	:	"图片操作任务",
		"photos" :	"图片操作任务",
		"watermark" : "水印操作任务",
		"watermarks" : "水印操作任务",
		"workflow"	: "工作流操作任务",
		"workflows" : "工作流操作任务",
		"flowemployee" : "工作流操作任务",
		"flowemployees" : "工作流操作任务"
	},
	HostTitle : {
		"channelHost" : "子栏目操作任务",
		"websiteHost" : "站点栏目操作任务",
		"WebSiteRoot" : "系统操作任务",
		"extendFieldInChannel" : "栏目扩展字段创建任务",
		"extendFieldInSite" : "站点扩展字段创建任务",
		"extendFieldInSys" : "系统扩展字段创建任务",
		"templateInChannel" : "栏目模板操作任务",
		"templateInSite" : "站点模板操作任务",
		"contentlinkInChannel" : '栏目热词',
		"photoInSite"	:	"图片库操作任务",
		"photoInChannel" :	"图片分类操作任务",
		"watermarkInSite" : "图片库操作任务",
		"workflowInSite"  : "站点工作流操作任务",
		"workflowInSys"   : "系统工作流操作任务",
		"flowOfChannel" : "栏目工作流操作任务"
	},
	ObjectDisplayNum : (function(){
		OperTypeMapping = {//为设置操作面板操作个数而增加的一个影射
			"channel" : ['channel', 'channelMaster']
		};
		var obj = {
			"channel" : 6,
			"channels" : 6,
			"website" : 6,
			"websites" : 6,
			"channelMaster" : 6,
			"extendfield" : 6,
			"extendfields" : 6,
			"template" : 6,
			"templates" : 6,
			"photo"	:	6,
			"photos" :	6,
			"watermark" : 6,
			"watermarks" : 6,
			"workflow"   : 6,
			"workflows"	 : 6,
			"flowemployee" : 6,
			"flowemployees" : 6
		};
		for (var oprKey in top.$personalCon.operators){
			if(OperTypeMapping[oprKey]){
				var operTypeArray = OperTypeMapping[oprKey];
				for (var i = 0; i < operTypeArray.length; i++){					
					obj[operTypeArray[i]] = top.$personalCon.operators[oprKey];
				}			
			}else if(obj[oprKey]){
				obj[oprKey] = top.$personalCon.operators[oprKey];
			}
		}
		return obj;
	})(),
	HostDisplayNum : (function(){
		var host = {
			"channelHost" : 3,
			"channelInsite" : 3,
			"WebSiteRoot" : 3,
			"websiteHost" : 3,
			"extendFieldInChannel" : 3,
			"extendFieldInSite" : 3,
			"extendFieldInSys" : 3,
			"templateInChannel" : 3,
			"templateInSite" : 3,
			"contentlinkInChannel" : 3,
			"photoInSite" : 3,
			"photoInChannel" : 3,
			"watermarkInSite" : 3,
			"workflowInSite"  : 3,
			"workflowInSys"	  : 3,
			"flowOfChannel" : 3
		};
 		for (var oprKey in top.$personalCon.operators){
			if(host[oprKey]){
				host[oprKey] = top.$personalCon.operators[oprKey];
			}
		}
		return host;
	})(),
	ObjectAttribute : {
		"channel" : "栏目详细信息",
		"channels" : "栏目详细信息",
		"website" : "站点详细信息",
		"websites" : "站点详细信息",
		"WebSiteRoot" : "站点类型详细信息",
		"channelMaster" : "栏目详细信息",
		"extendfield" : "扩展字段详细信息",
		"extendfields" : "扩展字段详细信息",
		"template" : "模板详细信息",
		"templates" : "模板详细信息",
		"photo"	:	"图片详细信息",
		"photos" :	"图片详细信息",
		"watermark" : "水印详细信息",
		"watermarks" : "水印详细信息",
		"workflow"   : "工作流详细信息",
		"workflows"	 :	"工作流详细信息",
		"flowemployee" : "工作流详细信息",
		"flowemployees" : "工作流详细信息"
	},
	ServiceId : {
		"channel" : "wcm6_channel",
		"website" : "wcm6_website",
		"WebSiteRoot" : "wcm6_website",
		"publish" : "wcm6_publish",
		"channelMaster" : "wcm6_channel",
		"extendfield" : "wcm6_extendfield",
		"template" : 'wcm6_template',
		"photo"	: 'wcm6_photo',
		"watermark" : "wcm6_watermark",
		"workflow"  : "wcm6_process",
		"flowemployee"	: "wcm6_process"		
	}
};
var OperAttrPanel = {
	Path : {
	},
	destroy : function(){
		this.fOnFailure = this.getOnSuccess 
			= this.__lastBindSuccess = this.fOnSuccess = null;
		this.params = null;
		this.master = this.origParams = null;
	},
	init : function(oMaster){
		//avoid memory leak
		this.destroy();
		this.params = {};
		this.master = oMaster;
		if(!oMaster){
			alert('OperAttrPanel.master is null,please set it.');
			return;
		}
		this.fOnFailure = function(_transport,_json){
			window.DefaultAjax500CallBack(_transport,_json);
			this.callBack();
		}.bind(this);
		var caller = this;
		this.fOnSuccess = function(_transport,_json){
			if(this.isHostObjectAttribute){
				var sRawType = this.params["ObjectType"].toUpperCase();
				//修改@2008-06-12 mapType专门用于查找相应的Master，此处使用错误，用新接口屏蔽
				var sObjType = (this.master.mapResultType||this.master.mapType)(this.params["ObjectType"]).toUpperCase();
				this.ObjectRight = (_json[sObjType] || _json[sRawType] || _json["MULTIRESULT"][sObjType])["RIGHT"];
				var isVirtual = (_json[sObjType] || _json[sRawType] || _json["MULTIRESULT"][sObjType])["ISVIRTUAL"];
				if(isVirtual!=null&&this.ObjectRight!=null){
					if(isVirtual=='true'||isVirtual==true){
						var sTmpRight = '';
						var sTmpCnt = this.ObjectRight.length;
						for(var nK = 0;nK<sTmpCnt;nK++){
							var tmpChar = this.ObjectRight.charAt(nK);
							if(![31].include(sTmpCnt-1-nK)&&!(sTmpCnt==64&&nK==0)){
								sTmpRight += tmpChar;
							}
							else{
								sTmpRight += '0';
							}
						}
						this.OpersCanNotDo["documentInChannel"] = {
							'copy':true,
							'move':true
						}
						this.ObjectRight = sTmpRight;
					}
					else{
						this.OpersCanNotDo["documentInChannel"] = {
						}
					}
				}
				this.HostRight = this.ObjectRight;
				this.HostJson = _json;
				this.showHostOperators(true);
			}
			var sObjectType = this.params['ObjectType'];
			ObjectAttributePanel.show();
			ObjectAttributePanel.loadData(CONST.ObjectAttribute[sObjectType],sObjectType,
				this.ObjectRight,_json);
			this.callBack();
		}.bind(this);
		this.getOnSuccess = function(){
			caller.__lastBindSuccess = function(_transport,_json){
				if(caller.__lastBindSuccess!=arguments.callee){
					caller.callBack();
					return;
				}
				caller.fOnSuccess(_transport,_json);
			}
			return caller.__lastBindSuccess;
		}
	},
	setConst : function(_nIndex,_sOperType,_sTitle,_nDisplayNum){
		if(_nIndex==1){
			CONST.HostTitle[_sOperType] = _sTitle;
			CONST.HostDisplayNum[_sOperType] = top.$personalCon.operators[_sOperType] || _nDisplayNum;
		}
		else if(_nIndex==2){
			CONST.ObjectTitle[_sOperType] = _sTitle;
			CONST.ObjectDisplayNum[_sOperType] = top.$personalCon.operators[_sOperType] || _nDisplayNum;
		}
		else if(_nIndex==3){
			CONST.ObjectAttribute[_sOperType] = _sTitle;
		}
	},
	setServiceId : function(_sObjectType,_sServiceId){
		CONST.ServiceId[_sObjectType] = _sServiceId;
	},
	_getBasic4Get : function(){
		if(this.master.extraParams){
			return this.master.extraParams.apply(this);
		}
		return this.master.getBasic4Get.apply(this);
	},
	_getBasic4Save : function(){
		var extra = this.master.extraParams4Save || this.master.extraParams;
		if(extra){
			return extra.apply(this);
		}
		return this.master.getBasic4Save.apply(this);
	},
	_arrangeParams : function(_oParams){
		this.origParams = _oParams;
		if(this.master.initParams){
			this.params["ObjectIds"] = this.params["ObjectId"] = null;
			if(!_oParams["objectids"]){
				_oParams["objectids"] = [];
			}
			else if(typeof _oParams["objectids"]=='string'){
				_oParams["objectids"] = _oParams["objectids"].split(',');
			}
			if(!_oParams["objectrights"]){
				_oParams["objectrights"] = [];
			}
			else if(typeof _oParams["objectrights"]=='string'){
				_oParams["objectrights"] = _oParams["objectrights"].split(',');
			}
			this.master.initParams.call(this,_oParams);
			if(this.params["ObjectIds"]==null&&this.params["ObjectId"]==null){
				this.setObjectIds(this.getHostObjectId(),true);
			}
			//
			return this.params;
		}
		return this.master.arrangeParams.call(this,_oParams);
	},
	_saveSuccess : function(){
		this.master.saveSuccess.apply(this, arguments);
	},
	_saveFailure : function(_trans, _json){
		this.master.saveFailure.apply(this, [_trans, _json]);
//		$alert(transport.responseText);
	},
	callBack : function(){
		var oMaster = this.__getMapedMaster();
		if(oMaster&&oMaster.prototype.callBack) {
			try{
				oMaster.prototype.callBack.apply(this);
			}catch(err){}
		}
		PageContext.running = false;
		PageContext.notify();
	},
	updateAttribute : function(_oPostData, _eEditItem){		
		var oPost = Object.extend({"objectid":this.params["ObjectId"]}, _oPostData);
		Object.extend(oPost, this._getBasic4Save());

		this.lastPostData = oPost;
		var sFieldName = _eEditItem.getAttribute("_fieldName",2);
		this.RowColumnInfo = {
			"objectid" : this.params["ObjectId"],
			"fieldName" : sFieldName,
			"fieldValue" : _oPostData[sFieldName],
			"fieldLabel" : _eEditItem.getAttribute("label",2)
		};
		//TODO
		var sType = this.master.mapType(this.params["ObjectType"]);
		if(sType != null) {
			sType = ("" + sType.charAt(0)).toUpperCase() + sType.substring(1);
			eval("var oScope = com.trs.wcm." + sType + "Master;");
			if(oScope && oScope.prototype.updateAttr){
				//wenyh@2007-05-22 updateAttr方法保持原有的参数
				oScope.prototype.updateAttr.apply(this, [_oPostData,_eEditItem]);
				delete _eEditItem;
				return;
			}				
		}
		//else
		var sServiceId = CONST.ServiceId[this.params["ObjectType"]];		
		var sMethodName = 'save';

		var bIgnoreRU = null;
		if((bIgnoreRU = _eEditItem.getAttribute('_ignoreRowUpdate', 2)) != null) {
			bIgnoreRU = (bIgnoreRU + '').toLowerCase().trim();
			bIgnoreRU = (bIgnoreRU == 'true' || bIgnoreRU == '1');
		}else{
			bIgnoreRU = false;
		}
		
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.call(sServiceId,sMethodName,oPost,true,
			function(){
				this._saveSuccess(bIgnoreRU);
			}.bind(this),this._saveFailure.bind(this));
	
		delete _eEditItem;	
	},
	__getMapedMaster : function(){
		var sType = this.master.mapType(this.params["ObjectType"]);
		if(sType != null) {
			sType = ("" + sType.charAt(0)).toUpperCase() + sType.substring(1);
			eval("var oScope = com.trs.wcm." + sType + "Master;");
			return oScope;
		}
		//else
		return null;
	},
	_doAfterInitialize : function(){
		var master = this.__getMapedMaster();
		if(master && master.prototype.doAfterInitialize){
			master.prototype.doAfterInitialize.apply(this, [this.params]);
		}				
	},
	showHostOperators : function(_bHost){
		var sHostType = this.params['HostType'];
		HostOperatorPanel.show();
		HostOperatorPanel.loadData(CONST.HostTitle[sHostType],sHostType,
			this.HostRight,CONST.HostDisplayNum[sHostType],(this.OpersCanNotDo||{})[sHostType]);
		if(_bHost){
			var sObjectType = this.params["ObjectType"];
			var sObjectTitle = CONST.ObjectTitle[sObjectType];
			if(sObjectTitle){
				ObjectOperatorPanel.show();
				ObjectOperatorPanel.loadData(
					CONST.ObjectTitle[sObjectType],sObjectType,
					this.HostRight,CONST.ObjectDisplayNum[sObjectType],(this.OpersCanNotDo||{})[sObjectType]);
			}
		}
	},
	showOperators : function(oParams){
		var sObjectType = this.params["ObjectType"];
		ObjectOperatorPanel.show();
		ObjectOperatorPanel.loadData(
			CONST.ObjectTitle[sObjectType],sObjectType,
			this.ObjectRight,CONST.ObjectDisplayNum[sObjectType],(this.OpersCanNotDo||{})[sObjectType]);
	},
	loadData : function(_oParams){
		this.params = this._arrangeParams(_oParams);
		this._doAfterInitialize();
		HostOperatorPanel.hide();
		ObjectAttributePanel.hide();
		ObjectOperatorPanel.hide();		

		ObjectAttributePanel.onMore = this._getObjectAttributeMore_();
		if(!this.isHostObjectAttribute){
			this.showOperators();
		}		
		if(this.params["ObjectIds"]){
			this.showHostOperators(false);
			var sObjectType = this.params['ObjectType'];			
			ObjectAttributePanel.show();
			ObjectAttributePanel.loadData(CONST.ObjectAttribute[sObjectType],sObjectType,
				this.ObjectRight,{"NUM":this.params["ObjectIds"].length});
			this.callBack();
		}
		else if(!this.isHostObjectAttribute){
			this.showHostOperators(false);
			this.call();
		}
		else if(!this.cachedHostObject){
			this.call();
			this.cachedHostObject = true;
		}
		else{
			this.showHostOperators(true);
			var sObjectType = this.params["ObjectType"];
			ObjectOperatorPanel.show();
			ObjectAttributePanel.loadData(CONST.ObjectAttribute[sObjectType],sObjectType,
				this.HostRight,this.HostJson);
			this.callBack();
		}
	},
	_call : function(){
		return this.master.call.apply(this);
	},//*/
	__getMasterScope : function(_sType){
		try{
			var oScope = null;
			if(!this.Path[this.params["ObjectType"]]){
				this.Path[this.params["ObjectType"]] = {
					"templater":baseUrl+'/oper_attr_templater/',
					"js":baseUrl+'/oper_attr_js/'
				};
			}
			var base = this.Path[this.params["ObjectType"]]["js"];
			var url = base + _sType + 'Master.js';
			var r = new Ajax.Request(url,{
				asynchronous:false,
				method:'GET'
			});
			if(r.responseIsSuccess()){
				var script = document.createElement('SCRIPT');
				script.text = r.transport.responseText;
				document.body.appendChild(script);
				eval("oScope = com.trs.wcm." + _sType + "Master;");
			}
			return oScope;
		}catch(err){
			//Just Skip it.
		}
	},
	call : function(){
		var sType = this.master.mapType(this.params["ObjectType"]);
		if(sType != null) {
			sType = ("" + sType.charAt(0)).toUpperCase() + sType.substring(1);
			eval("var oScope = com.trs.wcm." + sType + "Master;");
			if(!oScope){
				oScope = this.__getMasterScope(sType);
			}
			if(oScope&&oScope.prototype.call){
				//set oDataHelper.call, multiCall to new value
				var _f500 = _fFailure = this.fOnFailure;
				var BDHP = com.trs.web2frame.BasicDataHelper.prototype;
				BDHP.Call0 = BDHP.Call;
				BDHP.Call = function(_sServiceId,_sMethodName,_oPost,_bSend,_fSuccess){
					this.Call0(_sServiceId,_sMethodName,_oPost,_bSend,_fSuccess,_f500,_fFailure);
				}
				BDHP.MultiCall0 = BDHP.MultiCall;
				BDHP.MultiCall = function(_arrCombined,_fSuccess){
					this.MultiCall0(_arrCombined,_fSuccess,_f500,_fFailure);
				}
				oScope.prototype.call.apply(this);

				//set oDataHelper.call, multiCall to old value
				BDHP.Call = BDHP.Call0;
				BDHP.Call0 = null;
				BDHP.MultiCall = BDHP.MultiCall0;
				BDHP.MultiCall0 = null;				
				return;
			}				
		}
		var sServiceId = CONST.ServiceId[this.params["ObjectType"]];
		var sMethodName = 'findbyid';
		var oPost = Object.extend(this._getBasic4Get(),{"ObjectId":this.params["ObjectId"],"DateTimeFormat":"yy-MM-dd HH:mm"});
		if(this.isHostObjectAttribute){
			Object.extend(oPost,{"ContainsRight":true});
		}
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.call(sServiceId,sMethodName,oPost,true,
			this.getOnSuccess(), this.fOnFailure, this.fOnFailure);
	},
	_getObjectAttributeMore_ : function(){
		var sType = this.master.mapType(this.params["ObjectType"]);
//		var sType = this.params["ObjectType"];
		if(sType != null) {
			sType = ("" + sType.charAt(0)).toUpperCase() + sType.substring(1);
			eval("var oScope = com.trs.wcm." + sType + "Master;");
			if(oScope&&oScope.prototype.onMore){
				return oScope.prototype.onMore.bind(this);
			}
		}
	},
	setObjectsRight : function(_ArrObjectRights,_sDefaultRight){
		_sDefaultRight = (_sDefaultRight!=null)?_sDefaultRight:'';
		//TODO _ArrObjectRights.join
		if(Array.isArray(_ArrObjectRights)){
			if(_ArrObjectRights.length==0){
				this.ObjectRight = _sDefaultRight;
			}
			else if(_ArrObjectRights.length==1){
				this.ObjectRight = _ArrObjectRights[0];
			}
			else{
				this.ObjectRight = mergeRights(_ArrObjectRights);
			}
		}
		else if(typeof _ArrObjectRights=="string"){
			this.ObjectRight = _ArrObjectRights;
		}
		else{
			this.ObjectRight = _sDefaultRight;
		}
	},
	setOperPanelType : function(_nIndex, _nOperType){
		this.params[(_nIndex==1)?"HostType":"ObjectType"] = _nOperType;
	},
	setHostObject : function(_sHostType, _sHostObjectId){
		this.params["HostObjectType"] = _sHostType;
		this.params["HostObjectId"] = _sHostObjectId;
	},
	setObjectIds : function(_ArrObjectIds, _bHostObject){
		this.isHostObjectAttribute = _bHostObject||false;
		if(_ArrObjectIds==null||_ArrObjectIds.length==0){
			this.isHostObjectAttribute = true;
		}
		if(typeof _ArrObjectIds!='object'){
			this.params["ObjectIds"] = null;
			this.params["ObjectId"] = _ArrObjectIds;
		}
		else if(_ArrObjectIds.length==1){
			this.params["ObjectIds"] = null;
			this.params["ObjectId"] = _ArrObjectIds[0];
		}
		else{
			this.params["ObjectId"] = null;
			this.params["ObjectIds"] = _ArrObjectIds;
		}
	},
	getHostObjectType : function(){
		return this.params["HostObjectType"];
	},
	getHostObjectId : function(){
		return this.params["HostObjectId"];
	},
	getHostParams : function(){
		var oHostParams = {};
		var sHostObjectType = this.getHostObjectType();
		var sHostId = this.getHostObjectId();
		if(sHostObjectType=="WebSiteRoot") {
			oHostParams['sitetype'] = sHostId;
		}
		else if(sHostObjectType=="website"){
			oHostParams['siteid'] = sHostId;
		}
		else{
			oHostParams[sHostObjectType+'id'] = sHostId;
		}
		return oHostParams;
	},
	getObjectIds : function(){
		return this.params["ObjectIds"]||this.params["ObjectId"];
	}
};
com.trs.wcm.AbstractMaster = Class.create("wcm.AbstractMaster");
com.trs.wcm.AbstractMaster.prototype = {
	initialize: function(_sPanelId){
		this.panelId = _sPanelId;
	},
	getBasic4Get : function(){
		return {};
	},
	getBasic4Save : function(){
		return {};
	},
	arrangeParams : function(_oParams){
		alert('must implements.');
		return {};
	},
	saveSuccess : function(){
		alert('must implements.');
	},
	saveFailure : function(_trans,  _json){
		if(window.DefaultAjax500CallBack) {
			window.DefaultAjax500CallBack(_trans,  _json);
			return;
		}
		$alert(_trans.responseText);
	},
	/*
	 * 便于查找Master的一种映射机制，
	 * 如ObjType为Channel和ChannelMaster都对应于ChannelMaster
	 */
	mapType : function(_sRawType){
		return _sRawType;
	}
	/* maybe define
	call : function(){
		return false;
	}//*/
};
var PanelMasterFactory = {
	create : function(_sType){
		var sType = (""+_sType.charAt(0)).toUpperCase()+_sType.substring(1);
		var a = "a=new com.trs.wcm."+sType+"Master();";
		try{
			eval(a);
		}catch(err){
			try{
				var base = OperAttrPanel.Path[_sType]["js"];
				var url = base+sType+'Master.js';
				var r = new Ajax.Request(url,{
					asynchronous:false,
					method:'GET'
				});
				if(r.responseIsSuccess()){
					var script = document.createElement('SCRIPT');
					script.text = r.transport.responseText;
					document.body.appendChild(script);
					eval(a);
				}
				else if(r.transport.status==404){
					alert('属性栏扩展类"'+url+'"不存在');
					return;
				}
			}catch(err){
				alert(err.message);
			}
		}
		return a;
	}
}