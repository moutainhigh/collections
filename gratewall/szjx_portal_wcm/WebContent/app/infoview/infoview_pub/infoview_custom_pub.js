	function $toQueryStr(_oParams){
		var arrParams = _oParams || {};
		var rst = [], value;
		for (var param in arrParams){
			value = arrParams[param];
			rst.push(param + '=' + value);
		}
		return rst.join('&');
	}
	function validFileExt(_strPath, _strExts) {
		var arrayTemp = _strPath.split(".");
		if(arrayTemp.length<2) {
			return false;
		}
		var sFileExt = arrayTemp[arrayTemp.length-1].toLowerCase();
		arrayTemp = _strExts.split(",");
		var bResult = false;
		for(var i=0; i<arrayTemp.length; i++) {
			if(arrayTemp[i].toLowerCase() == sFileExt) {
				bResult = true;
			}
		}
		return bResult;
	}

	function uploadFile(inputElName){
		var inputEl = $(inputElName);
		if(!inputEl) return;
		var fileName = 	inputEl.value;
		if(fileName == null || fileName.length<=0){
			alert("请选择需要上传的文件!");
			return;
		}
		var sAllowExt = inputEl.getAttribute('allowext') || '';
		if(sAllowExt.length > 0 && !validFileExt(fileName, sAllowExt)) {
			alert('只支持上传' + sAllowExt+ '格式的文件！');
			return;
		}

		var sParams = "fileNameParam=" + (inputElName + '_browser_btn') + "&fileNameValue=" + encodeURI($(inputElName).value);
		YUIConnect.setForm(inputElName + '_frm', true, Ext.isSecure);
		YUIConnect.asyncRequest('POST',
			'file_server_upload_for_custom.jsp?'+sParams, {
				"upload" : function(_transport){
					var sResponseText = _transport.responseText;
					eval("var result="+sResponseText);
					if(result["Error"]){
						alert(result["Error"]);
						return;
					}
					$(inputElName).value = result["Message"];
				}
			}
		);
	}
	
	var _mbaseUrl = '';
	Event.observe(window, 'load', function(){
		_mbaseUrl = $('infogatePath').value || '';
		if(_mbaseUrl == '')
			alert("必须在页面中添加元素[infogatePath]");
		var formEl = document.getElementById('frmAction');
		if(formEl)
			formEl.action = _mbaseUrl + '/CustomInfoViewDataAdapt';
		loginDealWith();
	});
	function verifycodevalid(){
		var sInfogateVerifyCodeURL=_mbaseUrl + '/verifycode';
		var sValidInfogateVerifyCodeURL = _mbaseUrl + '/customer/system/get_infoview_verify_code.jsp';
		var param = {
			VerifyURL:sInfogateVerifyCodeURL,
			ValidVerifycodeURL:sValidInfogateVerifyCodeURL
		};
		var cb = wcm.CrashBoard.get({
			id : 'VerifyCodeDialog',
			title : '\u8BF7\u586B\u5199\u6821\u9A8C\u7801',
			url : '../images/infoview/infoview_verify_code.htm',
			params : param,
			width : '235px',
			height : '180px',
			callback : function(args){
				var sVerifyCode = args || "";
				document.getElementById('verifycode').value = sVerifyCode;
				var frmAction = document.getElementById('frmAction');
				frmAction.onsubmit = "return true;";
				frmAction.submit();
			}
		});
		cb.show();
		return false;
	}

	function upload(el){
		var params = {
			AllowExt :  el.getAttribute("AllowExt"),
			InlineImg : false,
			DowithUrl : _mbaseUrl + '/file/file_server_upload.jsp'
		};
		var cb = wcm.CrashBoard.get({
			id : 'FileUploadDialog',
			title: wcm.LANG.infoview_custom_pub_2000 || '上传文件',
			url : '../images/infoview/file_upload.html',
			params : params,
			width:'420px',
			height:'140px',
			callback : function(_args){
				var file = _args[0], fileDesc = _args[1] || file;
				el.value = file;
			},
			btns : false
		});
		cb.show();
	}

	function loginDealWith(){
		window.GateWayCallBack = function(t, mode){
			switch(mode){
				case 'NEED_LOGIN':
					try{
 
						var cb = wcm.CrashBoard.get({
							id : 'Trs_Gateway_Login',
							title : '该表单需要登录',
							params : {
								src : _mbaseUrl + '/customer/demo_login.html'
							},
							url : '../images/infoview/login.html',
							callback : function(_args){
							},
							width:'240px',
							height:'305px',
							btns : false
						});
						cb.show();
					}catch(err){
						alert('该表单需要登录后才能填报！请先登录。');
					}
					break;
				case 'EXCEPTION' :
					break;
			}
		}
		var el = document.createElement("SCRIPT");
		el.src = _mbaseUrl + '/public/initpage2.jsp?channelid='+ $('ChannelId').value + '&infoviewid=' + $('InfoViewId').value;
		document.body.appendChild(el);
	}