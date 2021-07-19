/** Title:			CTRSRequestParam.js
 *  Description:
 *		Define TRSRequestParam Object
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2004-11-24 08:38
 *  Vesion:			1.0
 *  Last EditTime:	2004-11-24/2004-11-24
 *	Update Logs:
 *		CH@2004-11-24 Created File
 *	Note:
 *		
 *	Depends:
 *		CTRSHashtable.js
 *
 *	Examples:
 *			
 */

function CTRSRequestParam(){
	this.hParameters = null;

	this.init				= function(){
		if(this.hParameters != null)return;

		try{
			this.hParameters = new CTRSHashtable();				
		}catch(e){
			CTRSAction_alert("Please include CTRSHashtable.js!");
		}		
	}
	
	this.setParameter		= function(_sParameterName, _sValue){
		this.init();
		return this.hParameters.put(_sParameterName, _sValue);
	}

	this.removeParameter	= function(_sParameterName){
		if(this.hParameters == null)
			return null;

		return this.hParameters.remove(_sParameterName);
	}	

	this.toURLParameters	= function(){
		var sURLParam = "trandom="+Math.random();

		if(this.hParameters == null || this.hParameters.size()<=0)
			return sURLParam;

		var nSize = this.hParameters.size();
		var arKeys = this.hParameters.keys();
		for(var i=0; i<nSize; i++){
			var sParamName = arKeys[i];
			if(sParamName == 'trandom') continue;
			var sValue = this.hParameters.get(sParamName);
			try{var arIds = sValue.split(",");}catch(e){}
			if(arIds && arIds.length && arIds.length > 200){
				CTRSAction_alert(wcm.LANG.WCM52_ALERT_18 || "执行批量操作时，一次性提交的数量不能超过200！");
				return null;
			}
			if(sValue.length < 500){
				sValue = encodeURIComponent(sValue);
			}
			sURLParam += "&"+sParamName+"="+sValue;
		}		
		return sURLParam;
	}

	this.setAllParameters	= function(_oTRSRequestParam){
		if(_oTRSRequestParam.hParameters == null || _oTRSRequestParam.hParameters.size()<=0)
			return;
		//初始化
		this.init();
		//复制
		this.hParameters.putAll(_oTRSRequestParam.hParameters);
	}
	
}

var TRSRequestParam = new CTRSRequestParam();