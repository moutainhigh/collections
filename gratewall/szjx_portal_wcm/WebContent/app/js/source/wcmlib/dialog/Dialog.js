/**
 * @class wcm.MessageBox
*/
wcm.MessageBox = function(){
	var dlg = null;
	var id = 'wcm-mb-dlg';
	var iconId = id + "-icon";
	var msgId = id + "-msg";
	var btnsId = id + "-buttons";

	var htmlTemplate = [
		'<table border=0 cellspacing=0 cellpadding=0 class="dlg-table">',
            '<tr>',
                '<td class="icon" id="{0}"></td>',
                '<td><div class="msg-wrapper">',
                    '<table class="dlg-table"><tr><td id="{1}" class="msg"></td></tr></table>',
                '</div></td>',
            '</tr>',
            '<tr>',
                '<td colspan="2" class="buttons" id="{2}"></td>',
            '</tr>',
        '</table>'
	].join("");

	var getDialog = function(){
		if(!dlg){
			dlg = new wcm.Panel({
				baseZIndex : 10000,
				baseCls : 'dlg ' + id,
				id : id,
				closeAction : 'hide',
				draggable : true,
				width : '328px',
				height : '169px'
			});
			dlg.render();
			new Insertion.Bottom(dlg.getElement(dlg.bodyId), String.format(htmlTemplate, iconId, msgId, btnsId));
		}
		return dlg;
	};

	return {
		getDlg : getDialog,
		alert : function(title, msg, fn, scope){
			this.show({
				title : title,
				icon : 'icon-info',
				msg : msg,
				btns : [
					{text : this.buttonText["ok"], cmd : fn}
				]
			});
			return this;
		},

		confirm : function(title, msg, fn, scope){
			this.show({
				title : title,
				icon : 'icon-question',
				msg : msg,
				btns : [
					{text : this.buttonText[fn["yes"]?"yes":"ok"], cmd : fn["yes"] || fn["ok"] || fn},
					{text : this.buttonText[fn["yes"]?"no":"cancel"], cmd : fn["no"] || fn["cancel"], extraCls : 'wcm-btn-close'}
				]
			});
			return this;
		},

		warn : function(title, msg, fn, scope){
			this.show({
				title : title,
				icon : 'icon-warning',
				msg : msg,
				btns : [
					{text : this.buttonText["ok"], cmd : fn}
				]
			});
			return this;
		},

		error : function(title, msg, fn, scope){
			this.show({
				title : title,
				icon : 'icon-error',
				msg : msg,
				btns : [
					{text : this.buttonText["ok"], cmd : fn}
				]
			});
			return this;
		},

		show : function(options){
			this.setTitle(options["title"]);
			this.setIcon(options["icon"]);
			this.setMsg(options["msg"]);
			this.setButtons(options["btns"]);
			getDialog().show();
			getDialog().center();
		},

		hide : function(){
			getDialog().hide();
		},

		setTitle : function(title){
			title = title || wcm.LANG.DIALOG_SYSTEM_ALERTION || "系统提示信息";
			getDialog().setTitle(title);
		},

		setIcon : function(icon){
			icon = icon || "icon-info";
			var dom = dlg.getElement(iconId);
			dom.className = "icon " + icon;
		},

		setMsg : function(msg){
			msg = msg || "";
			var dom = getDialog().getElement(msgId);
			Element.update(dom, msg);
		},

		setButtons : function(btns){
			var dlg = getDialog();
			//destroy the old buttons.
			var btnsDom = dlg.getElement(btnsId);
			var btnDom = Element.first(btnsDom);
			while(btnDom){
				var btn = wcmXCom.get(btnDom);
				btn ? dlg.remove(btn) : Element.remove(btnDom);
				btnDom = Element.first(btnsDom);
			}

			//add new buttons.
			for (var i = 0; i < btns.length; i++){
				btns[i]["cmd"] = btns[i]["cmd"] ? btns[i]["cmd"].createSequence(this.hide, this) : this.hide;
				var btn = new wcm.Button(Ext.applyIf(btns[i], {renderTo : btnsId, scope : this}));
				dlg.add(btn);
				btn.show();
			}
		},

		$ : function(id){
			return dlg.getElement(id);
		},

        buttonText : {
            ok : wcm.LANG.DIALOG_BTN_OK || "确定",
            cancel : wcm.LANG.DIALOG_BTN_CANCEL || "取消",
            yes : wcm.LANG.DIALOG_BTN_YES || "是",
            no : wcm.LANG.DIALOG_BTN_NO || "否"
        }
	};
}();


/**
 * @class wcm.FaultDialog
*/
wcm.FaultDialog = function(){
	var dlg = null;
	var id = 'wcm-fault-dlg';
	var iconId = id + "-icon";
	var msgId = id + "-msg";
	var detailId = id + "-detail";
	var isDetailHidden = true;
	var customClose = null;

	var htmlTemplate = [
        '<table border=0 cellspacing=0 cellpadding=0 class="dlg-table">',
            '<tr>',
                '<td class="icon" id="{0}">',
                    '<div class="detail" key="detail">', wcm.LANG.DIALOG_SHOW_DETAIL || '显示详细信息', '</div>',
                    '<div class="copy"  key="copy">', wcm.LANG.DIALOG_COPY_DETAIL || '复制此信息', '</div>',
                    '<div class="close"  key="close">', wcm.LANG.DIALOG_CLOSE_FRAME || '关闭提示窗', '</div>',
				'</td>',
                '<td><div style="height:200px;overflow:auto;">',
                    '<table class="dlg-table">',
                        '<tr><td id="{1}" class="msg"></td></tr>',
                        '<tr><td>',
                            '<textarea id="{2}" name="{2}" style="display:none;"></textarea>',
                        '</td>',
                    '</tr></table>',
                '</div></td>',
            '</tr>',
        '</table>'
	].join("");

	var getDialog = function(){
		if(!dlg){
			dlg = new wcm.Panel({
				baseZIndex : 10000,
				baseCls : 'dlg ' + id,
				closeAction : 'hide',
				id : id
			});
			dlg.render();
			dlg.on('beforehide', function(){
				if(customClose){
					var customClose0  = customClose;
					customClose = null;
					customClose0();
				}
			});
			new Insertion.Bottom(dlg.getElement(dlg.bodyId), String.format(htmlTemplate, iconId, msgId, detailId));
			bindEvents();
		}
		return dlg;
	};

	var bindEvents = function(){
		Event.observe(iconId, 'click', function(event){
			var srcElement = Event.element(event);
			var key = srcElement.getAttribute("key");
			if(!key) return;
			try{
				eval(key + "(event)");
			}catch(error){
				//just skip it.
			}
		}, false);
	};

	var close = function(){
		getDialog().hide();
	};

	var copy = function(){
		window.setTimeout(function(){
			var dom = getDialog().getElement(detailId);
			try{
				Ext.copy(dom.value);
				alert(wcm.LANG.DIALOG_CLIPBOARD_COPYED || "已经复制到剪切板中!");
			}catch(ex){
				alert(wcm.LANG.DIALOG_COPY_NOT_SUPPORTED || '您的浏览器不支持自动复制操作,请手工拷贝!');
				dom.focus();
				dom.select();		
			}
		}, 10);
	};

	var detail = function(event){
		var dlg = getDialog();
		if(isDetailHidden){
			dlg.setWidth("700px");
		}else{
			dlg.setWidth("450px");
		}
		dlg.center();
		Element[isDetailHidden ? 'show' : 'hide'](dlg.getElement(detailId));
		var dom = Event.element(event || window.event);
		if(dom.getAttribute('key') == 'detail'){
			var sDetail = wcm.LANG.DIALOG_SHOW_DETAIL || '显示详细信息';
			if(isDetailHidden) sDetail = wcm.LANG.DIALOG_HIDE_DETAIL || '隐藏详细信息';
			Element.update(dom, sDetail);
		}
		isDetailHidden = !isDetailHidden;
	};
	var codeRegExp = /^\s*\[ERR-(\d+)\]\s*/;
	var getFaultCode = function(_fault){
		if(_fault && _fault.code) return _fault.code;
		codeRegExp.test(_fault.message||"");
		return RegExp.$1 || 0;
	}

	var format = function(sContent){
		sContent = sContent || "";
		return sContent.replace(/&nbsp;/g, " ");
	}

	return {
		show : function(_fault, _title, _fClose){
			//because the wcm_en version, add some codes for Exception tip.						
			var nCode = getFaultCode(_fault);
			if(nCode >= 200000){
				var message = (_fault.message||"").replace(codeRegExp, "");
				Ext.Msg.warn($transHtml(message), _fClose);
				return;
			}
			this.setTitle(_title);
			customClose = _fClose;
			var dlg = getDialog();
			if(_fault){
				var msg = $transHtml(_fault.message);
				if(msg.length > 250){
					msg = msg.substr(0, 250) + "...";
				}
				if(wcm.LANG.LOCALE == 'en')if(nCode != 11111) msg = "An error occurs in the system.";;
				Element.update(dlg.getElement(msgId), msg);
				dlg.getElement(detailId).value = format(_fault.detail);
			}
			dlg.show();
		},

		setTitle : function(title){
			title = title || wcm.LANG.DIALOG_SERVER_ERROR || "与服务器交互时出现错误";
			if(wcm.LANG.LOCALE == 'en') title = "Error";
			getDialog().setTitle(title);
		},

		setMsg : function(msg){
			msg = msg || "";
			var dom = getDialog().getElement(msgId);
			Element.update(dom, msg);
		},

		setDetail : function(detail){
			detail = detail || "";
			var dom = getDialog().getElement(detailId);
			Element.update(dom, detail);
		},

		close : function(){
			getDialog().hide();
		}
	};	
}();


/**
 * @class wcm.ReportDialog
*/
wcm.ReportDialog = function(){
	var dlg = null;
	var id = 'wcm-report-dlg';
	var iconId = id + "-icon";
	var msgId = id + "-msg";
	var contentId = id + "-content";
	var info = null;
	var customClose = null;

	var htmlTemplate = [
        '<table border=0 cellspacing=0 cellpadding=0 class="dlg-table">',
            '<tr>',
                '<td class="icon" id="{0}">',
                    '<div class="copy"  key="copy">', wcm.LANG.DIALOG_COPY_DETAIL || '复制此信息', '</div>',
                    '<div class="close"  key="close">', wcm.LANG.DIALOG_CLOSE_FRAME || '关闭提示窗', ' </div>',
				'</td>',
                '<td><div style="height:200px;overflow:auto;">',
                    '<table class="dlg-table">',
                        '<tr><td id="{1}" class="msg"></td></tr>',
                        '<tr><td><div id="{2}" class="content"></div></td></tr>',
					'</table>',
                '</div></td>',
            '</tr>',
        '</table>'
	].join("");

	var getDialog = function(){
		if(!dlg){
			dlg = new wcm.Panel({
				baseZIndex : 10000,
				baseCls : 'dlg ' + id,
				closeAction : 'hide',
				id : id,
				width : '550px'
			});
			dlg.render();
			dlg.on('beforehide', function(){
				if(customClose){
					var customClose0  = customClose;
					customClose = null;
					customClose0();
				}
			});
			new Insertion.Bottom(dlg.getElement(dlg.bodyId), String.format(htmlTemplate, iconId, msgId, contentId));
			bindEvents();
		}
		return dlg;
	};

	var bindEvents = function(){
		Event.observe(iconId, 'click', function(event){
			var srcElement = Event.element(event);
			var key = srcElement.getAttribute("key");
			if(!key) return;
			try{
				eval(key + "()");
			}catch(error){
				//just skip it.
			}
		}, false);
	};

	var close = function(){
		getDialog().hide();
	};

	var copy = function(){
		try{
			Ext.copy(info.Summary);
			alert(wcm.LANG.DIALOG_CLIPBOARD_COPYED || "已经复制到剪切板中!");
		}catch(ex){
			alert(wcm.LANG.DIALOG_COPY_NOT_SUPPORTED || '您的浏览器不支持自动复制操作,请手工拷贝!');
		}
	};

	var m_arrTypeNames = ['UNKNOWN', 'SUCCESS', 'WARN', 'ERROR'];
	function getTypeName(_nType){
		_nType = parseInt(_nType);
		if(isNaN(_nType) || _nType < 3 || _nType > 5) {
			_nType = 2;
		}
		return m_arrTypeNames[_nType - 2];
	}

	var transTitle = function(_sTitle, _sOption, report, index){
		if(Ext.isFunction(_sOption)) _sOption = _sOption(report, index);
		var str = _sTitle;
		//var reg = new RegExp('\~' + _sOption + '\-([0-9]+)\~', 'i');
		var reg = new RegExp('\~[^-]+\-([0-9]+)\~', 'i');
		var matches = str.match(reg);
		var nId = 0;
		if(matches != null && (nId = parseInt(matches[1])) > 0) {
			str = '<span class="option_' + _sOption + '" onclick="wcm.ReportDialog.renderOption('
				+ nId + ')">&nbsp;&nbsp;</span>' + str.replace(reg, '');
		}
		return str;
	}

	var titleTransOptions = null;

	var build = function(_report){
		info = {
			isSuccess: $v(_report, 'reports.is_success') === 'true',
			title: $v(_report, 'reports.title'),
			reports: $a(_report, 'reports.report') || []
		};		
		var sSummary = info.title + '\n';
		var sContent  = '';
		var options = titleTransOptions;
		var bNeedTransTitle = (options != null && options.option != null);
		for (var i = 0; i < info.reports.length;){
			var report = info.reports[i++];
			var sTitle = $v(report, 'title');
			var sDetails = $v(report, 'ERROR_INFO');
			var sType = $v(report, 'TYPE');
			if(sType == '') {
				sType = '3';
			}
			var bHasDetail = (sDetails != null && sDetails.trim() != '');
			sTitle = bNeedTransTitle ? transTitle(sTitle, options.option, report, i-1) : $transHtml(sTitle);
			sContent += '<div class="' + (bHasDetail ? 'innerTitle_withTip' : 'innerTitle') + '"><b>' + i + '.</b> \
							<b class="rpdlg-type' + sType + '">&nbsp;&nbsp;&nbsp;</b>\ ' + sTitle + '</div>';
					
			if(bHasDetail) {
				sDetails = sDetails.replace(/</g, "&lt;");
				sDetails = sDetails.replace(/>/g, "&gt;");
				sDetails = sDetails.replace(/\n/g, "<br />");
				/*
				sContent += '<div class="tip">(<a href="#" onclick="wcm.ReportDialog.switchDetail(' + i + ', this);return false;">'+(wcm.LANG.DIALOG_DETAIL_INFO||'详细信息') + '</a>)</div>\
							<div id="divDetails_' + i + '" style="margin-top:-13px; display:none">\
							<div class="commentHeader"></div>\
								<textarea class="commentbox" readonly style="overflow:visible;">' + sDetails + '</textarea>\
							</div>';
				*/
				sContent += '<div class="tip">(<a href="#" onclick="wcm.ReportDialog.switchDetail(' + i + ', this);return false;">'+(wcm.LANG.DIALOG_DETAIL_INFO||'详细信息') + '</a>)</div>\
							<div id="divDetails_' + i + '" style="margin-top:-13px; display:none">\
							<div class="commentHeader"></div>\
								<div class="commentbox" readonly>' + sDetails + '</div>\
							</div>';
			}
			sContent += '<div class="sep">&nbsp;</div>';
			sSummary += i + '. [' + getTypeName(sType) + ']' + sTitle + '\n';
			sSummary += '[' + (wcm.LANG.DIALOG_DETAIL_INFO||'详细信息') + '] ' + (sDetails || '') + '\n\n';
		}
		info.Summary = sSummary;
		info.content = sContent;
	};

	return {
		show : function(_report, _title, _fClose, _titleTransOptions){
			customClose = _fClose;
			titleTransOptions = _titleTransOptions;
			build(_report);
			this.setTitle(_title);
			this.setIcon(info.isSuccess ? 'success' : 'error');
			this.setMsg(info.title);
			this.setContent(info.content);
			var dlg = getDialog();
			dlg.show();
		},

		setIcon : function(icon){
			var dlg = getDialog();
			dlg.getElement(iconId).className = "icon " + icon;
		},

		setTitle : function(title){
			title = title || wcm.LANG.DIALOG_SYSTEM_ALERTION || "系统提示信息";
			if(wcm.LANG.LOCALE == 'en') title = "Confirm";
			getDialog().setTitle(title);
		},

		setMsg : function(msg){
			msg = msg || "";
			var dom = getDialog().getElement(msgId);
			Element.update(dom, msg);
		},

		setContent : function(content){
			content = content || "";
			var dom = getDialog().getElement(contentId);
			Element.update(dom, content);
		},

		close : function(){
			getDialog().hide();
		},

		renderOption : function(_sObjId){
			var options = titleTransOptions;
			if(options.renderOption) {
				options.renderOption(_sObjId);
			}
		},
		
		switchDetail : function(_sId, _oLnk){
			var dlg = getDialog();
			var detail = dlg.getElement('divDetails_' + _sId);
			if(_oLnk._bDispay === true ) {
				_oLnk.innerHTML = wcm.LANG.DIALOG_DETAIL_INFO || '详细信息';
				_oLnk._bDispay = false;
				Element.hide(detail);
			}else{
				_oLnk.innerHTML = wcm.LANG.DIALOG_HIDE_INFO || '隐藏显示';
				_oLnk._bDispay = true;
				Element.show(detail);
			}
			dlg.center();
		}
	};
}();


/*override the Ext.Msg*/
Ext.Msg = Ext.Msg || {};
Ext.apply(Ext.Msg, {
	$success : function(msg, fn, scope){
		wcm.MessageBox.show({
			icon : 'icon-success',
			msg : msg,
			btns : [
				{text : wcm.MessageBox.buttonText["ok"], cmd : fn}
			]
		});
	},
	$fail : function(msg, fn, scope){
		wcm.MessageBox.show({
			icon : 'icon-fail',
			msg : msg,
			btns : [
				{text : wcm.MessageBox.buttonText["ok"], cmd : fn}
			]
		});
	},
	$timeAlert : function(msg,iSeconds,fn,title,icon){
		iSeconds = iSeconds || 5;
		var msg = msg+'<br><br><div style="font-size:12px;color:gray;">' + String.format("本窗口在<span id=\'_dialog_timecounter_\' style=\'font-size:11px;padding:0 2px;color:red\'>{0}</span>秒内将自动消失",iSeconds) + '.</div>';
		var dlg = wcm.MessageBox.getDlg();
		var $timeAlertHandler = null;
		dlg.on('beforehide', function(){
			if($timeAlertHandler) clearTimeout($timeAlertHandler);
			(fn || Ext.emptyFn).call(dlg);
			dlg.un('beforehide', arguments.callee);
		});
		wcm.MessageBox.show({
			title : title,
			icon : icon || 'icon-info',
			msg : msg,
			btns : [
				{text : wcm.MessageBox.buttonText["ok"]}
			]
		});
		var cnt = 0;
		$timeAlertHandler = setTimeout(
			function(){
				var tc = wcm.MessageBox.$('_dialog_timecounter_');
				if(!tc) return;
				if((++cnt)>=iSeconds){
					var dlg = wcm.MessageBox.getDlg();
					if(dlg.hidden) return;
					wcm.MessageBox.hide();
				}
				else{
					tc.innerHTML = iSeconds-cnt;
					$timeAlertHandler = setTimeout(arguments.callee,1000);
				}
			}
		,1000);		
	},
	alert : function(msg, fn, scope){
		wcm.MessageBox.alert("", msg, fn, scope);
	},
	confirm : function(msg, fn, scope){
		wcm.MessageBox.confirm("", msg, fn, scope);
	},
	show : function(options){
		wcm.MessageBox.show(options);
	},
	report : function(_report, _title, _fClose, _titleTransOptions){
		wcm.ReportDialog.show.apply(wcm.ReportDialog, arguments);
	},
	fault : function(_report, _title, _fClose){
		wcm.FaultDialog.show.apply(wcm.FaultDialog, arguments);
	},
	warn : function(msg, fn, scope, opt){
		opt = opt || {};
		wcm.MessageBox.warn(opt.title || "", msg, fn, scope);
	},
	error : function(msg, fn, scope, opt){
		opt = opt || {};
		wcm.MessageBox.error(opt.title || "", msg, fn, scope);
	}
});
Ext.copy = function(_sTxt){
	if(Ext.isIE){
		clipboardData.setData('Text',_sTxt);
	}
	else if(Ext.isGecko){
		try{
			netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
			var clipboard = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
			if (!clipboard) return;
			var transferable = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
			if (!transferable) return;
			transferable.addDataFlavor('text/unicode');
			var supportsString 	= Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
			supportsString.data	= _sTxt;
			transferable.setTransferData("text/unicode",supportsString,_sTxt.length*2);
			clipboard.setData(transferable,null,Components.interfaces.nsIClipboard.kGlobalClipboard);
		}catch(err){}
	}
};