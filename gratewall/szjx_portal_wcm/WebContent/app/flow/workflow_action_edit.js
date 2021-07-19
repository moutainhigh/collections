PageContext = {
	loadPage : function(){
		//类型及其参数
		this.loadHandlers();
	},
	loadHandlers : function(){
		var hSysHandlers = PageContext.config;
		//alert(arHandlers);
		PageContext.currHandler = hSysHandlers[PageContext.action['handler']];
		this.loadCurrentHandlerParam();
	},
	loadCurrentHandlerParam : function(){
		//得到当前handler的系统配置
		var currHandler  = PageContext.currHandler;
		var params = currHandler['params'];
		
		//读取出参数
		var eContainer = $('divParamsContainer');
		Element.update(eContainer, '');
		var eTemplate = $('divParamsTemplate');
		var eParamName  = eTemplate.getElementsByTagName('span')[0];  //第一个span即是pname
		var eParamValue = eTemplate.getElementsByTagName('span')[1]; //第二个span即是pvalue
		eParamValue.style.position = 'relative';

		var nCount = 0;
		Element.show('divParamsContainer');
		for( var sName in params){
			var param = params[sName];
			//1.设置条目的样式
			eTemplate.children(0).className = (nCount++ % 2 != 0) ? 'alter_item' : 'normal_item';

			//2.设置ParamName的属性
			eParamName.id = 'sp_pname' + sName;
			eParamName.innerHTML = param['DESC'];
			eParamName.setAttribute('_value', sName);

			//3.动态生成ParamValue
			this.__appendParamValue(eParamValue, param, sName);

			//alert(eParamValue.outerHTML)
			//4.附加模板内容
			var sHtml = eTemplate.innerHTML;
			new Insertion.Bottom(eContainer, sHtml);
		}
		//清理工作
		eParamName.id = '';
		Element.update(eParamName, '');
		Element.update(eParamValue, '');
		delete eParamName;
		delete eParamValue;
		eParams = null;
		if($('txt_pvalue_nextnode')){
			$('txt_pvalue_nextnode').select();
		}
	},
	__appendParamValue : function(_ePVal, _param, _sName){
		Element.update(_ePVal, '');
		//TODO支持多选; 根据类型校验
		var type = $v(_param, 'type') || 'string';
		var values = $v(_param, 'values.value');
		var sInputId = 'txt_pvalue_' + _sName.toLowerCase();

		//展现参数值控件
		var ePValInput = null;
		//var bIsValueSet = false; TODO
		var bIsCombox = false;
		if(values != null && values.length > 0) { //如果具有多个value值的话
			var multiFlag = $v(_param, 'values.multi');
			var sHtml = '';
			if(multiFlag && multiFlag != '0' && multiFlag != 'false') {
				ePValInput = document.createElement('SPAN');
				//可以选择多个的checkbox-list
				sHtml = '<span>';
				for (var i = 0; i < values.length; i++){
					var val = values[i];
					sHtml += '<input type="checkbox" value="' + $v(val, 'name')
						+ '" name="' + $v(_param, 'name')
						+ '" ' + ($v(val, 'checked') ? 'checked' : '')
						+ '>';
					sHtml += $v(val, 'desc') + ' ';
				}
				//TODO 多选时，value怎么确定？
				sHtml += '<input type="hidden" value="'
					+ '" id="' + 'txt_pvalue_' + _sName.toLowerCase() + '">'

				sHtml += '</span>';
				ePValInput.innerHTML = sHtml;
				_ePVal.appendChild(ePValInput);
				delete _ePVal;
				return; //提前退出
			}else {
				//下拉列表 OR 组合框
				var sOptions = '';
				var hParams = {};
				for (var i = 0; i < values.length; i++){
					var val = values[i];
					var pVal = $v(val, 'name');
					var pName = $v(val, 'desc');
					sOptions += '<option value="' + pVal + '">' + pName + '</option>';
					hParams[pVal] = 100;
				}
				if(type != 'enum') {//组合框
					bIsCombox = true;
					var sSelectId = ('sel_' + sInputId);
					var sValidation = this.__getValidation({'DESC': ((wcm.LANG['FLOW_85'] || '') +  _param['DESC'].toLowerCase())});
					var sHtml = '<input id="' + sInputId + '" validation="' + sValidation +'" type="text" class="combox_input" _iscombox="1">';
					sHtml += '<span style="width: 18px">';
					sHtml += '<select onchange="$(\'' + sInputId + '\').value = this.value;" class="combox_select" id="' + sSelectId + '">' + sOptions + '</select>';
					sHtml += '</span>';
					
					Element.update(_ePVal, sHtml);
					var txt = $(sInputId);
					var sel = $(sSelectId);
					//设置默认值
					txt.setAttribute('_default', sel.value);
					if(PageContext.params.length != 0) {
						var sValue = this.__getParamValue(_sName)
						sel.value = sValue;
						txt.value = sValue;
						if(hParams[sValue] != 100) {//TODO 竟然无效？
							sel.selectedIndex = -1;
						}
					}else{
						txt.value = sel.value;
					}
					
					ePValInput = txt;
				}else{//下拉列表
					var sSelectId = ('sel_' + sInputId);
					var sHtml = '<select id="' + sSelectId + '">' + sOptions + '</select>'
					Element.update(_ePVal, sHtml);
					ePValInput = $(sSelectId);
				}
			}

		}else if(type == 'text') {
			var eText = document.createElement("textarea");
			eText.className = 'multi_text';
			ePValInput = eText;
			ePValInput.setAttribute('validation', this.__getValidation(_param));

			//ge gfc add @ 2007-6-22 14:13 文本框类型，支持弹出式编辑
			this.__appendTextEditorOption(sInputId, 'sp_pname' + _sName);
		}else{
			ePValInput = document.createElement("input");
			ePValInput.className = 'single_text';
			ePValInput.setAttribute('validation', this.__getValidation(_param));
			//ge gfc add @ 2007-6-22 14:13 文本框类型，支持弹出式编辑
			this.__appendTextEditorOption(sInputId, 'sp_pname' + _sName);
		}		
		if(ePValInput.getAttribute('validation', 2) != null) {
			ePValInput.setAttribute('onblur', 'validate(this, event)');
		}
		
		//开始附加
		if(!bIsCombox) {
			_ePVal.appendChild(ePValInput);
			ePValInput.id = sInputId;
			//设置参数值
			if(PageContext.params.length != 0) {
				ePValInput.value = this.__getParamValue(_sName);
			}else if(_param['DEFAULT-VALUE'] != null) {
				ePValInput.value = _param['DEFAULT-VALUE']['NODEVALUE'];
			}
		}

		
		delete _ePVal;
		
	},
	__appendTextEditorOption : function(_sInputId, _sParamNameId){
		$(_sParamNameId).innerHTML += '&nbsp;<span onclick="popupEditor('
			+ _sInputId + ', ' + _sParamNameId + ')" title=' + wcm.LANG['FLOW_73'] || '点击转到大的编辑区域'
			+ ' class="text_param">&nbsp;</span>';	
	},
	__getValidation : function(_param){
		//值校验
		var sValidate = "";
		var sType = _param["TYPE"] || "string";
		var nMaxLen = 50;
		if(sType == 'text') {
			sType = 'string';
			nMaxLen = 200;
		}
		sValidate += "type:'" + sType +"',";
		sValidate += _param["REQUIRED"] == "false" ? "" : "required:'',max_len:" + nMaxLen;
		sValidate += ",desc:'" + (wcm.LANG['FLOW_85'] || '') + _param['DESC'].toLowerCase() + "'";

		return sValidate;
	},
	__getParamValue : function(_sName){
		if(PageContext.params.length == 0) {
			return '';
		}
		//else
		var params = PageContext.params;
		for (var i = 0; i < params.length; i++){
			var param = params[i];
			if(param['name'] == _sName) {
				return param['value'];
			}
		}
		return '';
	},
	__prepareParams : function(){
		var params = PageContext.currHandler['params'];
		//alert(Object.parseSource(params))
		var result = '';
		for( var sName in params){
			var param = params[sName];
			var eParamVal = $('txt_pvalue_' + sName.toLowerCase());
			if(eParamVal == null) {
				continue;
			}
			result += '<' + sName + '><![CDATA[' + $transHtml($F(eParamVal)) + ']]></' + sName + '>';
		}
		return result;
	},
	getActionProp : function(){
		var result = {};
		Object.extend(result, {
			handler: PageContext.action['handler'],
			params: this.__prepareParams()
		});
		return result;
	}	
}

//for cb
function initPage(_actionInfo){
	PageContext.action = _actionInfo.action;
	try{
		if(PageContext.action) {
			PageContext.params = PageContext.action.inline.params;
		}
	}catch(err){
		Ext.Msg.alert(wcm.LANG['FLOW_44'] || '[action]没有被初始化！\n' + err.message, function(){});
		return;
	}
	
	PageContext.index = _actionInfo.index;
	PageContext.actions = _actionInfo.actions;

	var config = $configurator.getWorkflowConfig()['action'];
	if(config == null) {
		Ext.Msg.alert(wcm.LANG['FLOW_43'] || '没有找到关于[action]的配置定义！', function(){});
		return;
	}
	//else
	PageContext.config = config;
	PageContext.loadPage();
}
function submitData(){
	if (window.parent){
		//准备参数
		doAddEdit();
		//重置inline属性
		PageContext.action['inline'] = null;

		toggleGotoLink(true);
		var actions = PageContext.actions;
		var nCurrItemId = PageContext.action['flowactionid'];
		window.parent.notifyParent2CloseFrame(document.FRAME_NAME);
		window.parent.notifyParent2Finish(document.FRAME_NAME, 
			{'actions': actions, 'itemid': nCurrItemId});
	}
}
function doAddEdit(){
	var actions = PageContext.actions;
	var actionProp = PageContext.getActionProp();
	Object.extend(PageContext.action, actionProp);;
	PageContext.action['edited'] = true;
	nCurrItemId = PageContext.action['flowactionid'];
	actions[PageContext.index - 1] = PageContext.action;
}

function closeframe(_eInput){
	if (!window.parent) return;
	if((PageContext.action['edited'] != true)
		&& !(PageContext.action['flowactionid'] >= 1)) {
		var data = {
			title: wcm.LANG['FLOW_45'] || '由于该[操作]尚未提交，此举将把您刚刚新建的这个[操作]移除！\n\n(提示：点击”取消“可以回到[操作]编辑页)', 
			index: PageContext.index
		};
		if(!window.parent.PageContext.deleteAction(null, data)) {
			if(_eInput) {
				_eInput.focus();
				_eInput.select();
				delete _eInput;
			}
			return;
		}
	}
	
	window.parent.notifyParent2CloseFrame(document.FRAME_NAME);
}

function popupEditor(_sInputId, _sParamNameId){
	var sTitle = (wcm.LANG['FLOW_74'] || '编辑参数')+ '"' + $(_sParamNameId).innerText.trim() + '"';
	var sUrl = WCMConstants.WCM6_PATH + 'flow/workflow_rule_texteditor.html';
			wcm.CrashBoarder.get('Text_Editor').show({
				title : sTitle,
				src : sUrl,
				left:'100px',
				top:'80px',
				width: '300px',
				height: '200px',
				reloadable : true,
				params : {text: $(_sInputId).value},
				maskable : true,
				callback : function(_args){
					$(_sInputId).value = _args['text'];
				}
			});
}

function validate(_eInput, event){
	if(_eInput.tagName != 'INPUT' && _eInput.tagName != 'TEXTAREA') {
		return;
	}
	var event = window.event;
	if(Position.within($('btnCancel'),
			Event.pointerX(event),
			Event.pointerY(event)) || Event.pointerY(event) < 0){
		closeframe(_eInput);
		toggleGotoLink(true);
		return;
	}
	if(_eInput.getAttribute('_iscombox') == '1'
		&& _eInput.value.trim() == '') {
		_eInput.value = _eInput.getAttribute('_default', 2);
		_eInput.select();
		return;
	}
	var result = ValidationHelper.validAndDisplay(_eInput);
	toggleGotoLink(result);
	delete _eInput;
}

//让flownode_property.html页面中的“返回属性页面”的按钮可用/不可用
function toggleGotoLink(_bEnable){
	try{
		var win = window.parent.parent;
		win.document.getElementById('lnkToggle').disabled = !_bEnable;		
	}catch(err){
		//just skip it
	}

}

function doBeforeHide(){
	doAddEdit();
}