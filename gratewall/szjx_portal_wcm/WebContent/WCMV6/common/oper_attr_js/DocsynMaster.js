/*
*文档分发，汇总的基类
*/
com.trs.wcm.DocSynMaster = {
	validDate : function(sDate,sSep){
		sSep = sSep || '-';
		try{
			var d=new Date();
			var tmp = sDate.split(sSep);
			d.setYear(parseInt(tmp[0],10));
			d.setMonth((parseInt(tmp[1],10)-1));
			d.setDate(parseInt(tmp[2],10));
			if(d.getDate()!=tmp[2]){
				d = null
			}
		}catch(err){
			d = null;
		}
		return d;
	},
	isValidDate : function(fieldValue){
		if(fieldValue.indexOf("-") == 2){
			fieldValue = (new Date().getYear()+"").substr(0,2) + fieldValue;
		}
		return this.validDate(fieldValue);
	},
	checkSQLValid : function(servicesName,methodName,postData,validCallBack,invalidCallBack,hasProcessBar,txtId){
		try{
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.call(servicesName,methodName,postData,true,function(_trans, _json){
				if(hasProcessBar){
					ProcessBar.next();
				}
				var sSql = $v(_json, 'sql');
				var sError = $v(_json, 'error');
				if(sError == null) {
					(validCallBack||Prototype.emptyFunction)();
					return true;
				}else{
					if(hasProcessBar) {
						ProcessBar.exit();
					}
					var sStyle = 'font-size: 12px;	color: #010101;	'
						+ 'padding-left: 10px; padding-righ: 5px; ' 
						+ 'font-family: Courier New; text-overflow: ellipsis; overflow: hidden;';
					var sInfo = '<div style="' + sStyle + '"><div style="font-size: 14px; border-bottom: 1px silver solid; margin-bottom:10px;" title="'
							+ sSql + '">'
							+ '<span style="font-weight: bold;">校验语句</span>: ' + sSql + "</div>";
					sInfo += sError == null ? '' : '<div style="margin-bottom:5px;" title="' + sError + '"><span style="font-weight: bold;">'
							+ '校验结果</span>: ' + sError + '</div>';
					sInfo += '</div></div>';
					$fail(sInfo, function(){
						$dialog().hide();
						try{
							if(invalidCallBack){
								invalidCallBack();
							}else if(txtId != null) {
								$(txtId).focus();
								$(txtId).select();
							}
						}catch(error){}
					});
					return false;
				}
			});	
		}catch(error){}
	},
	updateAttr : function(_oPostData, _eEditItem){
		if(!_oPostData["fieldName"]){
			for (var fieldName in _oPostData){
				_oPostData["fieldName"] = fieldName;
				_oPostData["fieldValue"] = _oPostData[fieldName];
				break;
			}
		}
		this.master.checkValid(_oPostData["fieldName"], _oPostData["fieldValue"], function(){
			this.execUpdateAttr(_oPostData);
		}.bind(this.master));
	},
	execUpdateAttr : function(_oPostData){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var oPostData = {
			channelId : PageContext.params["channelid"],
			objectId : PageContext.params["objectids"]
		};
		oPostData[_oPostData["fieldName"]] = _oPostData["fieldValue"];
		oHelper.Call('wcm6_documentSyn', 'save', oPostData, true, function(){
			$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', [PageContext.params["objectids"]]);
		});		
	},
	checkValid : function(fieldName, fieldValue, validCallBack){
		switch(fieldName){
			case 'SDATE':
				var sDate = this.isValidDate(fieldValue);
				var eDate = this.isValidDate($('EDATE').title);
				break;
			case 'EDATE':
				var sDate = this.isValidDate($('SDATE').title);
				var eDate = this.isValidDate(fieldValue);
				break;
			case 'DOCSDATE':
				var sDate = this.isValidDate(fieldValue);
				var eDate = this.isValidDate($('DOCEDATE').title);
				break;
			case 'DOCEDATE':
				var sDate = this.isValidDate($('DOCSDATE').title);
				var eDate = this.isValidDate(fieldValue);
				break;
			case 'WHERESQL':
				this.checkSQLValid('wcm6_channel', 'checkSQLValid', {
					channelId : PageContext.params["channelid"],
					queryby : fieldValue
				}, validCallBack, function(){
					setTimeout(function(){
						UIEditPanel.setValue(fieldName, $(fieldName).value);	
						UIEditPanel.edit(fieldName);
					}, 100);
				});
				return;
		}
		var errMsg = "";
		if(!sDate || !eDate){
			errMsg = "不是合法日期!";
		}else if(sDate.getTime() > eDate.getTime()){
			errMsg = "日期范围不对!";
		}
		if(errMsg.length != 0){
			$(fieldName).innerHTML = fieldValue;
			$alert(errMsg, function(){
				$dialog().hide();
				setTimeout(function(){
					UIEditPanel.setValue(fieldName, $(fieldName).value);	
					UIEditPanel.edit(fieldName);
				},100);
			});
		}else{
			validCallBack();
		}
	}
}
