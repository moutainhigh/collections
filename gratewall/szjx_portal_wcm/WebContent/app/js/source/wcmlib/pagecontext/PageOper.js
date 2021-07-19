Ext.ns('wcm.PageOper');
//操作面板
(function(){
	var m_sDetailTitle = (wcm.LANG.ABSLIST_OPER_DETAIL||'详细信息');
	var myTemplate = {
		operpanel : [
			'<div class="pageoper" id="{0}">',
				'<div class="pageoper_header">',
					'<div class="pageoper_title" id="{0}_title">', (wcm.LANG.ABSLIST_OPER_LOADING||'正在加载...'), '</div>',
					'<div class="pageoper_collapse" id="{0}_toggle"></div>',
				'</div>',
				'<div class="pageoper_body" id="{0}_body">',
					'<div class="pageoper_content" id="{0}_content">',
						'<div class="oper_items" id="{0}_items"></div>',
					'</div>',
					'<div class="pageoper_more_btn {0}_more_btn" id="{0}_more_btn" title="',
						(wcm.LANG.ABSLIST_OPER_MORE || '更多操作'), '" style="display:"></div>',
				'</div>',
				'<div class="pageoper_underside"></div>',
				'<div class="{0}_sep"></div>',
			'</div>',
			'<div class="oper_items_more" id="{0}_more" style="display: none"></div>'
		].join(''),
		detailpanel : [
			'<div class="pageoper" id="{0}">',
				'<div class="pageoper_header">',
					'<div class="pageoper_title" id="{0}_title">', m_sDetailTitle, '</div>',
					'<div class="pageoper_collapse" id="{0}_toggle"></div>',
				'</div>',
				'<div class="pageoper_body" id="{0}_body">',
					'<div class="pageoper_content pageoper_flexible_content" id="{0}_content">',
					'</div>',
					'<div class="pageoper_more_btn {0}_more_btn" id="{0}_more_btn" title="',
					(wcm.LANG.ABSLIST_OPER_DETAILMORE || '更多属性'), '" style="display:none">',
					(wcm.LANG.ABSLIST_OPER_DETAILMORE || '更多属性'),
					'</div>',
				'</div>',
				'<div class="pageoper_underside"></div>',
				'<div class="{0}_sep"></div>',
			'</div>'
		].join(''),
		operitem : [
			'<div class="oper_item {4}" {3} _type="{0}" _key="{1}">',
					'<div class="oper_item_row">',
						'<div class="oper_item_row_icon {1}"></div>',
						'<div class="oper_item_row_desc">{2}&nbsp;{5}</div>',
					'</div>',
			'</div>'
		].join(''),
		seperator : [
			'<div class="oper_item_seperate oper_item_more" title="分隔线" _type="{0}" _key="seperate">',
				'<div class="oper_item_row {0} seperate">&nbsp;</div>',
			'</div>'
		].join('')
	};
	var m_TransHelper = {
		inputDomHtml : [
			'<input type="text" name="{0}" value="{1}"/>'
		].join(''),
		validElement : function(input, sValidation, info){
			if(sValidation) {
				input.setAttribute("validation", sValidation);
			}
			info = info || {};
			var validInfo = ValidatorHelper.asynValid(input, {
				success : info["success"],
				fail : function(warning){
					Ext.Msg.warn(warning, function(){
						if(Element.visible(input)){
							input.value = info["oldValue"] || "";
							input.select();
						}
					});
				}
			});
		},
		basicAction : function(valueDom, wcmEvent, info){
			var sServiceId = valueDom.getAttribute('_serviceId', 2) || PageContext.serviceId;
			var sMethodName = valueDom.getAttribute('_methodName', 2) || 'save';
			var inputDom = this;
			var obj = wcmEvent.getObjs().getAt(0) || wcmEvent.getHost();
			var sFieldName = valueDom.getAttribute("_fieldName", 2);
			if(!sFieldName)return;
			var oPost = Ext.apply({
				OBJECTID : obj.getId()
			}, info);
			oPost[sFieldName.trim().toUpperCase()] = inputDom.value || valueDom.getAttribute("_fieldValue", 2);
			var pageParams = Ext.Json.toUpperCase(PageContext._buildParams(wcmEvent, sMethodName, valueDom));
			if(pageParams!=null && pageParams.FORCE){
				oPost = Ext.apply(oPost, pageParams.FORCE);
				delete pageParams.FORCE;
				oPost = Ext.applyIf(oPost, pageParams);
			}else{
				oPost = Ext.applyIf(oPost, pageParams);
			}
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.call(sServiceId, sMethodName, oPost, false,
				function(wcmEvent){
					var valueDom = this;
					var sActionCallback = valueDom.getAttribute('_callback', 2) || '';
					var fActionCallback = PageContext[sActionCallback] || window[sActionCallback];
					if(Ext.isFunction(fActionCallback)){
						fActionCallback(valueDom, wcmEvent);
					}else{
						wcmEvent.getObj().afteredit();
					}
				}.bind(valueDom, wcmEvent),
				function(transport, json){					
					var lastValue =  valueDom.getAttribute('_lastFieldValue');
					inputDom.value = lastValue;	
					valueDom.setAttribute('_fieldValue', lastValue);	
					if(inputDom.tagName == 'INPUT'){
						Element.update(valueDom, $transHtml(inputDom.value));
					}else{
						var optionEl = inputDom.options[inputDom.selectedIndex];
						var spanEl = valueDom.getElementsByTagName('SPAN')[0];
						if(spanEl){
							Element.update(spanEl, optionEl.innerHTML);
						}
						(window.$render500Err||emptyFn)(transport, json);
					}
				}
			);
		},
		inputKeyPress : function(valueDom, wcmEvent, event){
			var inputDom = this;
			event = event || window.event;
			if(event.keyCode==13){//Enter
				inputDom.blur();
			}
		},
		suggestionKeyPress : function(valueDom, wcmEvent, event){
			var inputDom = this;
			event = event || window.event;
			if(event.keyCode==13){//Enter
				inputDom.blur();
				return;
			}
			var suggest = valueDom.getAttribute('_suggestionFn', 2);
			var fSuggest = (PageContext[suggest] || window[suggest]);
			if(!fSuggest)return;
			fSuggest.apply(inputDom, [event, valueDom, wcmEvent]);
		},
		inputBlurEffect : function(inputDom, valueDom, wcmEvent, event){
			inputDom.onblur = inputDom.onkeypress = null;
			event = event || window.event;
			valueDom.removeAttribute('active');
			Element.removeClassName(valueDom, 'wcm_attr_value_edit');
			var lastValue = valueDom.getAttribute('_fieldValue', 2);
			valueDom.setAttribute('_fieldValue', inputDom.value);
			valueDom.setAttribute('_lastFieldValue', lastValue);
			//这种方式不太好，需再想办法
			var sDomType = valueDom.className
				.replace(/wcm_attr_value(_withborder)?/ig, '').trim();
			if(sDomType != "suggestionType"){
				Element.update(valueDom, $transHtml(inputDom.value));
			}
			var sAction = valueDom.getAttribute('_action', 2);
			var fAction = PageContext[sAction] || window[sAction] || m_TransHelper.basicAction;
			if(inputDom.value!=lastValue){
				fAction.apply(inputDom, [valueDom, wcmEvent]);
			}
		},
		inputBlur : function(valueDom, wcmEvent, event){
			var inputDom = this;
			var lastValue = valueDom.getAttribute('_fieldValue', 2);
			if(inputDom.value == lastValue ||  
				!ValidationHelper.hasValid(valueDom, m_TransHelper.getName(valueDom))){
				m_TransHelper.inputBlurEffect(inputDom, valueDom, wcmEvent, event);
				return;
			}
			var sValidation = valueDom.getAttribute("validation", 2);
			var sValidDesc = valueDom.getAttribute("validation_desc", 2);
			if(sValidDesc) inputDom.setAttribute("validation_desc", sValidDesc);			
			var info = {
				oldValue : m_TransHelper.getValue(valueDom),
				success : function(){
					m_TransHelper.inputBlurEffect(inputDom, valueDom, wcmEvent, event);
				}	
			};
			m_TransHelper.validElement(inputDom, sValidation, info);
		},
		selectChange : function(valueDom, wcmEvent, event){
			var selector = this;
			selector.blur();
		},
		selectBlur : function(valueDom, wcmEvent, event){
			var selector = this;
			//var sValidation = valueDom.getAttribute("validation", 2);
			//if(!m_TransHelper.validElement(selector, sValidation))return;
			event = event || window.event;
			selector.onblur = selector.onchange = null;
			valueDom.removeAttribute('active');
			var lastValue = valueDom.getAttribute('_fieldValue', 2);
			if(selector.selectedIndex==-1)selector.selectedIndex=0;
			valueDom.setAttribute('_fieldValue', selector.value);
			valueDom.setAttribute('_lastFieldValue', lastValue);
			var optionEl = selector.options[selector.selectedIndex];
			var spanEl = valueDom.getElementsByTagName('SPAN')[0];
			if(spanEl){
				Element.update(spanEl, optionEl.innerHTML);
				Element.hide(selector);
				Element.show(spanEl);
			}
			var sAction = valueDom.getAttribute('action', 2);
			var fAction = PageContext[sAction] || window[sAction] || m_TransHelper.basicAction;
			if(selector.value!=lastValue){
				fAction.apply(selector, [valueDom, wcmEvent]);
			}
		},
		getValueDomInput : function(valueDom){
			return String.format(m_TransHelper.inputDomHtml, $transHtml(valueDom.getAttribute('_fieldName')),
				$transHtml(valueDom.getAttribute('_fieldValue')));
		},
		getName : function(valueDom){
			return valueDom.getAttribute('_fieldName');
		},
		getValue : function(valueDom){
			return valueDom.getAttribute('_fieldValue');
		},
		inputDomEvent : function(input, valueDom, pageoper){
			input.onkeypress = m_TransHelper.inputKeyPress.bind(input, valueDom, pageoper.event);
			input.onblur = m_TransHelper.inputBlur.bind(input, valueDom, pageoper.event);
			input.select();
		},
		selectDomEvent : function(selector, valueDom, pageoper){
			selector.onchange = m_TransHelper.selectChange.bind(selector, valueDom, pageoper.event);
			selector.onblur = m_TransHelper.selectBlur.bind(selector, valueDom, pageoper.event);
			selector.focus();
		},
		suggestionDomEvent : function(input, valueDom, pageoper){
			input.onkeypress = m_TransHelper.suggestionKeyPress.bind(input, valueDom, pageoper.event);
			input.onblur = m_TransHelper.inputBlur.bind(input, valueDom, pageoper.event);
			input.select();
		},
		setCalendarValue : function(valueDom, inputDom){
			var sValidation = valueDom.getAttribute("validation", 2);
			var info = {
				oldValue : m_TransHelper.getValue(valueDom),
				success : function(){
					var lastValue = valueDom.getAttribute('_fieldValue', 2);
					valueDom.setAttribute('_fieldValue', inputDom.value);
					Element.update(valueDom, $transHtml(inputDom.value));
					var sAction = valueDom.getAttribute('action', 2);
					var fAction = PageContext[sAction] || window[sAction] || m_TransHelper.basicAction;
					if(inputDom.value!=lastValue){
						fAction.apply(inputDom, [valueDom, PageContext.event]);
					}
				}	
			};
			m_TransHelper.validElement(inputDom, sValidation, info);
		}
	}
	var m_oValueDomTrans = {
		input : function(valueDom){
			Element.addClassName(valueDom, 'wcm_attr_value_edit');
			Element.update(valueDom, m_TransHelper.getValueDomInput(valueDom));
			var inputDom = valueDom.getElementsByTagName('input')[0];
			if(inputDom!=null){
				m_TransHelper.inputDomEvent(inputDom, valueDom, this);
			}
		},
		combox : function(valueDom){
			//TODO
		},
		suggestionType : function(valueDom){
			//如果是suggestin类型，就不用再构造input了，就用自己的suggestion input
			//Element.update(valueDom, m_TransHelper.getValueDomInput(valueDom));
			var valueDomId = valueDom.id;
			var inputDom = $(valueDomId).getElementsByTagName('input')[0];
			if(inputDom!=null){
				m_TransHelper.suggestionDomEvent(inputDom, valueDom, this);
			}
		},
		select : function(valueDom){
			var selectEl = $(valueDom.getAttribute('_selectEl', 2));
			if(!selectEl)return;
			var spanEl = valueDom.getElementsByTagName('SPAN')[0];
			if(!spanEl){
				Element.update(valueDom, '');
				spanEl = document.createElement('SPAN');
				valueDom.appendChild(spanEl);
			}
			Element.hide(spanEl);
			valueDom.appendChild(selectEl);
			selectEl.value = valueDom.getAttribute('_fieldValue', 2);
			Element.show(selectEl);
			m_TransHelper.selectDomEvent(selectEl, valueDom, this);
		}
	}
	Ext.apply(wcm.PageOper, {
		defaults : {},
		transHelper : m_TransHelper,
		registerPanels : function(infos){
			for(type in infos){
				this.defaults[type.toLowerCase()] = infos[type];
			}
			return this;
		},
		init : function(info){
			this.enable = info.enable;
			if(!info.enable){
				wcm.Layout.collapseByChild('east', 'east_opers');
				return;
			}
			this.drawConstructor(info);
		},
		drawConstructor : function(info){
			var constructor = [];
			constructor.push(String.format(myTemplate.operpanel, 'pageoper_1'));
			constructor.push(String.format(myTemplate.operpanel, 'pageoper_2'));
			constructor.push(String.format(myTemplate.detailpanel, 'pageoper_3'));
			Ext.get('east_opers').update(constructor.join(''), false, function(){
				document.body.appendChild($('pageoper_1_more'));
				document.body.appendChild($('pageoper_2_more'));
			});
			Ext.get('east_opers').on('click', function(event, target){
				var sClassName = ','+target.className.replace(/\s/g, ',')+',';
				if(sClassName.indexOf(',pageoper_collapse,')!=-1
						|| sClassName.indexOf(',pageoper_expand,')!=-1){
					return this.togglePanel(target);
				}
				if(sClassName.indexOf(',pageoper_more_btn,')!=-1){
					return this.showMorePanel(event, target);
				}
			}, this);
			Ext.getBody().on('click', function(event, target){
				var sClassName = ','+target.className.replace(/\s/g, ',')+',';
				if(sClassName.indexOf(',oper_item,')!=-1
					|| sClassName.indexOf(',oper_item_row,')!=-1){
					return this.doClickOperItem(event, target);
				}
				if(sClassName.indexOf(',oper_item_row_icon,')!=-1
					|| sClassName.indexOf(',oper_item_row_desc,')!=-1){
					return this.doClickOperItem(event, target.parentNode);
				}
			}, this);
			Ext.getBody().on('mouseover', function(event, target){
				var sClassName = ','+target.className.replace(/\s/g, ',')+',';
				if(sClassName.indexOf(',oper_item_row,')!=-1){
					return this.doMouseOverOperItem(event, target);
				}
				if(sClassName.indexOf(',oper_item_row_icon,')!=-1
					|| sClassName.indexOf(',oper_item_row_desc,')!=-1){
					return this.doMouseOverOperItem(event, target.parentNode);
				}
			}, this);
			Ext.getBody().on('mouseout', function(event, target){
				var sClassName = ','+target.className.replace(/\s/g, ',')+',';
				if(sClassName.indexOf(',oper_item_row,')!=-1){
					return this.doMouseOutOperItem(event, target);
				}
				if(sClassName.indexOf(',oper_item_row_icon,')!=-1
					|| sClassName.indexOf(',oper_item_row_desc,')!=-1){
					return this.doMouseOutOperItem(event, target.parentNode);
				}
			}, this);
			Ext.get('pageoper_3').on('click', function(event, target){
				var eventinfo = this.getValue3EventInfo(target);
				this.clickValueDom(eventinfo);
			}, this);
			Ext.get('pageoper_3').on('mousemove', function(event, target){
				var mouseinfo = this.getValue3EventInfo(target);
				this.unhoverLastValueDom(mouseinfo);
				this.hoverValueDom(mouseinfo);
			}, this);
		},
		clickValueDom : function(eventInfo){
			var valueDom = eventInfo.valueDom;
			var rowDom = eventInfo.rowDom;
			if(!rowDom || !valueDom)return;
			if(!Ext.get(rowDom).hasClass('editable'))return;
			if(valueDom.getAttribute('active'))return;
			var extValueDom = Ext.get(valueDom);
			valueDom.setAttribute('active', '1');
			this.renderValueDom(valueDom);
			this.last3eventinfo = eventInfo;
		},
		renderValueDom : function(valueDom){
			var sDomType = valueDom.className
				.replace(/wcm_attr_value(_withborder)?/ig, '').trim();
			sDomType = sDomType || 'input';
			var fn = m_oValueDomTrans[sDomType];
			if(!fn)return;
			fn.call(this, valueDom);
		},
		getValue3EventInfo : function(srcElement){
			var valueDom = null, rowDom = null;
			while(srcElement!=null && srcElement!=$('pageoper_3')){
				if(Element.hasClassName(srcElement, 'wcm_attr_value')){
					valueDom = srcElement;
				}
				else if(Element.hasClassName(srcElement, 'attribute_row')){
					rowDom = srcElement;
				}
				srcElement = srcElement.parentNode;
			}
			return {
				valueDom : valueDom,
				rowDom : rowDom
			};
		},
		unhoverLastValueDom : function(mouseinfo){
			if(!this.last3mouseinfo)return;
			var valueDom = this.last3mouseinfo.valueDom;
			if(mouseinfo.valueDom==valueDom)return;
			var rowDom = this.last3mouseinfo.rowDom;
			if(!rowDom || !valueDom)return;
			if(!Ext.get(rowDom).hasClass('editable'))return;
			Ext.get(valueDom).removeClass('wcm_attr_value_withborder');
		},
		hoverValueDom : function(mouseinfo){
			var valueDom = mouseinfo.valueDom;
			var rowDom = mouseinfo.rowDom;
			if(!rowDom || !valueDom)return;
			if(!Ext.get(rowDom).hasClass('editable'))return;
			Ext.get(valueDom).addClass('wcm_attr_value_withborder');
			this.last3mouseinfo = mouseinfo;
		},
		doClickOperItem : function(event, target){
			var flyTarget = Ext.fly(target);
			target = flyTarget.hasClass('oper_item')?target:target.parentNode;
			var type = target.getAttribute('_type', 2);
			var key = target.getAttribute('_key', 2);
			wcm.SysOpers.exec(type, key, this.event);
			return false;
		},
		doMouseOverOperItem : function(event, target){
			var flyTarget = Ext.fly(target);
			flyTarget.addClass('oper_item_active');
		},
		doMouseOutOperItem : function(event, target){
			var flyTarget = Ext.fly(target);
			flyTarget.removeClass('oper_item_active');
		},
		_prepare : function(event){
			this.event = event;
			var context = event.getContext();
			this.host = context.getHost();
			this.relateType = PageContext.relateType || context.relateType;
			if(!this.relateType){
				Ext.Msg.d$alert(wcm.LANG.AbsList_1002 || '页面PageContext.getContext()方法没有指定relateType.');
				return false;
			}
			//个性化定制面板显示个数
			var bCustomizeInfo = false;
			var defaultNum4Panel1 = 5;
			var defaultNum4Panel2 = 7;
			if(top.m_CustomizeInfo && top.m_CustomizeInfo.operator){
				for(var name in top.m_CustomizeInfo.operator){
					if(name.toLowerCase()  == this.relateType.toLowerCase()){
						bCustomizeInfo = true;
						defaultNum4Panel1 = top.m_CustomizeInfo.operator[name].split(";")[0].split(":")[1] || 5;
						break;
					}
				}
			}
			this.objs = event.getObjs();
			var def1 = this.defaults[this.relateType.toLowerCase()];
			this.panel1Info = {
				isHost : true,
				type : this.relateType.toLowerCase(),
				right : this.host.right,
				objs : this.host,
				displayNum : bCustomizeInfo ? defaultNum4Panel1 : (def1?def1.displayNum:defaultNum4Panel1),
				frompanel : true
			};
			var panel2Type = this.objs.objType;
			var panel2Right = '';
			var data = this.objs;
			if(this.objs.length()==0){
				panel2Type = this.host.getType();
				panel2Right = this.host.right;
				data = this.host;
			}else if(this.objs.length()>1){
				panel2Type = panel2Type + 's';
				panel2Right = this._mergeRight(this.objs);
			}else{
				panel2Right = this.objs.getAt(0).right;
			}
			var def2 = this.defaults[panel2Type.toLowerCase()];
			if(top.m_CustomizeInfo && top.m_CustomizeInfo.operator){
				for(var name in top.m_CustomizeInfo.operator){
					if(name.toLowerCase()  == panel2Type.toLowerCase()){
						bCustomizeInfo = true;
						defaultNum4Panel2 = top.m_CustomizeInfo.operator[name].split(";")[0].split(":")[1] || 7;
						break;
					}
				}
			}
			this.panel3Info = this.panel2Info = {
				isHost : false,
				type : panel2Type.toLowerCase(),
				right : panel2Right,
				objs : data,
				displayNum : bCustomizeInfo ? defaultNum4Panel2 : (def2?def2.displayNum: defaultNum4Panel2),
				frompanel : true,
				isHidden : def2? def2.isHidden : false
			}
		},
		_mergeRight : function(objs){
			var arrRight = [];
			for (var i=0,n=objs.length(); i<n; i++){
				arrRight.push(objs.getAt(i).right);
			}
			return wcm.AuthServer.mergeRights(arrRight);
		},
		render : function(event){
			if(!this.enable)return;
			if(this._prepare(event)===false)return;
			//wcm.Layout.expandByChild('east', 'east_opers');
			var type = event.type;
			var arrOpers = wcm.SysOpers.getOpersByInfo(this.panel1Info, this.event);
			var values = this._getHtml(arrOpers, this.panel1Info);
			Ext.get('pageoper_1_items').update(values[0]);
			Ext.get('pageoper_1_more').update(values[1]);
			if(arrOpers[0]==null || arrOpers[0].length==0){
				Element.hide('pageoper_1');
			}else{
				Element.show('pageoper_1');
			}
			if(arrOpers[1]==null || arrOpers[1].length==0){
				Element.hide('pageoper_1_more_btn');
			}else{
				Element.show('pageoper_1_more_btn');
			}
			arrOpers = wcm.SysOpers.getOpersByInfo(this.panel2Info, this.event)
			values = this._getHtml(arrOpers, this.panel2Info);
			Ext.get('pageoper_2_items').update(values[0]);
			Ext.get('pageoper_2_more').update(values[1]);
			if(this.panel2Info.isHidden || arrOpers[0]==null || arrOpers[0].length==0){
				Element.hide('pageoper_2');
			}else{
				Element.show('pageoper_2');
			}
			if(arrOpers[1]==null || arrOpers[1].length==0){
				Element.hide('pageoper_2_more_btn');
			}else{
				Element.show('pageoper_2_more_btn');
			}
			var def = this.defaults[this.panel1Info.type];
			if(def){
				Ext.get('pageoper_1_title').update(def.title);
				$('pageoper_1_title').setAttribute("title", def.title);
			}
			def = this.defaults[this.panel2Info.type];
			if(def){
				Ext.get('pageoper_2_title').update(def.title);
				$('pageoper_2_title').setAttribute("title", def.title);
			}
			var caller = this;
			Ext.get('pageoper_3_content').update('');
			Element.hide('pageoper_3_more_btn');
			this._generateDetail(function(trans){
				var html = '';
				if(Ext.isString(trans)){
					html = trans;
				}else{
					html = trans.responseText;
				}
				Ext.get('pageoper_3_content').update(html, true, function(){
					//TODO绑定详细信息面板的事件
					this.last3mouseinfo = null;
					this.last3clickinfo = null;
				}.bind(caller));
				var cmsobjs = this.panel3Info.objs;
				var type = this.panel3Info.type;
				var def = this.defaults[type];
				if(!(def==null || def.detailMore==null || 
					(!def.detailMore && cmsobjs.length()>1))){
					Element.show('pageoper_3_more_btn');
				}
			}.bind(this));
		},
		getQuickKeyTip : function(keys){
			var tip = this.getQuickKeyTipText(keys);
			return '(<span class="quickkey_tip">' + tip + '</span>)';
		},
		getQuickKeyTipText : function(keys){
			var tip = '';
			if(Ext.isArray(keys)){
				if(keys[0].equalsI('Delete')||keys[0].equalsI('ShiftDelete')){
					tip = 'Del';
				}else{
					tip = keys[0].charAt(0);
				}
			}else{
				tip = keys.charAt(0);
			}
			return tip;
		},
		_getOperHtml : function(oper, bInMore){
			if(wcm.SysOpers.isSeparator(oper)){
				return String.format(myTemplate.seperator, oper.type, oper.key);
			}
			var quickKeyTip = '';
			var quickKeyTipText = '';
			if(oper.quickKey){
				quickKeyTipText = "("+this.getQuickKeyTipText(oper.quickKey)+")";
				quickKeyTip = this.getQuickKeyTip(oper.quickKey);
			}
			return String.format(myTemplate.operitem, oper.type, oper.key,
									oper.desc, "title='"+(oper.title||oper.desc)+quickKeyTipText+"'",
									(bInMore?'oper_item_more':''),
									quickKeyTip
							);
		},
		_getHtml : function(arrOpers, info){
			var value1 = [], value2 = [];
			for(var i=0,n=arrOpers[0].length;i<n;i++){
				value1.push(this._getOperHtml(arrOpers[0][i]));
			}
			for(var i=0,n=arrOpers[1].length;i<n;i++){
				value2.push(this._getOperHtml(arrOpers[1][i], true));
			}
			return [value1.join('\n'), value2.join('\n')];
		},
		_generateDetail : function(callBack){
			var cmsobjs = this.panel3Info.objs;
			var type = this.panel3Info.type;
			var def = this.defaults[type];
			if(def==null || def.detail===false){
				Ext.fly('pageoper_3').hide();
				return;
			}
			var fn = def.detail;
			fn = typeof fn=="function" ? fn : this._detail;
			Ext.get('pageoper_3_title').update(def.detailTitle || m_sDetailTitle);
			$('pageoper_3_title').setAttribute("title",def.detailTitle || m_sDetailTitle);
			Ext.fly('pageoper_3').show();
			var html = fn.apply(this, [cmsobjs, {
											def : def,
											event : this.event
										}, callBack]);
			if(Ext.isString(html)){
				callBack(html);
			}
		},
		_detail : function(cmsobjs, opt, callBack){
			if(cmsobjs.length()>1){
				return String.format("当前共选中<span class=\"num\">{0}</span>{1}. ", 
					(WCMLANG.LOCALE == 'en' ? ' ' : '') + cmsobjs.length() + (WCMLANG.LOCALE == 'en' ? ' ' : ''), 
					PageContext.PageNav.UnitName + (WCMLANG.LOCALE == 'en' ? PageContext.PageNav.TypeName.toLowerCase() : PageContext.PageNav.TypeName) + (WCMLANG.LOCALE == 'en' ? 's' : ''));
			}
			def = opt.def;
			if(!def.url){
				Ext.Msg.d$alert('url未设置:\n'+Ext.toSource(def));
				return '';
			}
			var extraParams = "";
			if(def.params){
				extraParams = "&" + def.params.call(def, opt);
			}
			var url = def.url.startsWith('?')?(WCMConstants.WCM_ROMOTE_URL+def.url) : def.url;
			new Ajax.Request(url, {
				method : 'GET',
				parameters : ['ObjectIds=', cmsobjs.getIds(), '&ObjectId=', cmsobjs.getIds(), extraParams].join(''),
				onSuccess : callBack
			});
		},
		showMorePanel : function(event, target){
			var flyTarget = Ext.fly(target);
			var sPanelId = target.id.substring(0, target.id.length-'_more_btn'.length);
			if(sPanelId=='pageoper_3'){
				return this.onDetailMore(target);
			}
			var point = event.getPoint();
			var x = point.x + 4;
			var y = point.y + 4;
			var d = Element.getDimensions(sPanelId + '_more');
			if(d.height + y > document.body.offsetHeight) y = Math.max(y - d.height, 0);
			var oBubbler = new wcm.BubblePanel(sPanelId + '_more');
			oBubbler.bubble([x,y], null, function(_Point){
				this.style.right = '20px';
				this.style.top = _Point[1] + 'px';
			});
		},
		onDetailMore : function(target){
			var fn = def.detailMore;
			fn = typeof fn=="function" ? fn : this._detailMore;
			Ext.fly(target).show();
			fn.apply(this, [this.event, wcm.SysOpers.getOpersByType(type), def]);
		},
		_detailMore : function(event, opers){
			var oper = opers['edit'] || opers['addedit'];
			if(!oper || oper.fn)return;
			(oper.fn)(event);
		},
		togglePanel : function(target){
			var flyTarget = Ext.fly(target);
			var sPanelId = target.id.substring(0, target.id.length-'_toggle'.length);
			if(flyTarget.hasClass('pageoper_collapse')){
				var height = target._offsetHeight = target.offsetHeight;
				Ext.get(sPanelId + '_content').hide();
				flyTarget.replaceClass('pageoper_collapse', 'pageoper_expand');
			}
			else if(flyTarget.hasClass('pageoper_expand')){
				var height = target._offsetHeight || target.offsetHeight;
				Ext.get(sPanelId + '_content').show();
				flyTarget.replaceClass('pageoper_expand', 'pageoper_collapse');
			}
		}
	});
})();