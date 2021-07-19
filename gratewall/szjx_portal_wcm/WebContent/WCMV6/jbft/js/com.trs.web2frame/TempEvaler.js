$package('com.trs.ajaxframe');
/**
 * 置标解析的工具类
 */
function getUpperCaseUrlQuery(){
	var urlQuery			= location.search.replace(/\?/g,'').parseQuery();
	var rUpperCaseUrlQuery			= {};
	for(var param in urlQuery){
		rUpperCaseUrlQuery['$'+param.toUpperCase()]	= urlQuery[param];
	}
	return rUpperCaseUrlQuery;
}
var TempEvaler = com.trs.ajaxframe.TempEvaler	= {
	supportedTagNames : ['for','record'],
	urlQuery : getUpperCaseUrlQuery(),
	/**
	 * 解析模板内容
	 * @param _eTextArea textarea的控件Id
	 * @param _DataSet 数据集,＃数据
	 * @param _ExtraParams 额外的参数,包括＄数据
	 */
	evaluateTemplater : function(_eTextArea,_DataSet,_ExtraParams){
		var eTemplate = $(_eTextArea);
		var ref = eTemplate.getAttribute('ref', 2);
		if(ref && $(ref)) {
			eTemplate = $(ref);
		}
		var sCurrScope = eTemplate.getAttribute("select",2)||'';
		var sValue = eTemplate.value;
		if(typeof _DataSet!='object')return sValue;
		sValue = this.evaluateTemplate(sValue , _DataSet , _ExtraParams , sCurrScope);
		return sValue;
	},
	getTagParserRegExp : function(){
		return new RegExp('<(('+this.supportedTagNames.join('|')+')\\d?)(\\s+[^>]*)?>((?:.|\n|\r)*?)</\\1>','img');
	},
	/**
	 * 检验模板内容是否含有待解析的置标
	 */
	isTemplateTagExist : function(_sTemplate){
		//置标解析正则表达式
		var reTagParser = this.getTagParserRegExp();
		return _sTemplate.match(reTagParser)!=null;
	},
	/**
	 * 解析模板内容
	 */
	evaluateTemplate : function(_sTemplate,_DataSet,_ExtraParams,_sCurrScope,_ExtraOptions){
		var sRetVal = '';
		//置标解析正则表达式
		var reTagParser = this.getTagParserRegExp();
		//置标属性解析正则表达式
		var reTagAttrParser = null;
		//匹配的置标数组
		var pTags = null;
		//当前置标匹配的属性数组
		var pCurrTagAttrs = null;
		//当前游标所处位置，用于字符串的分段串联
		var nLastIndex = 0;
		//临时变量
		var sTmpValue = '';
		_ExtraParams = _ExtraParams||{};
		while(true){
			//置标继续解析
			pTags = reTagParser.exec(_sTemplate);
			//没有匹配的置标时跳出循环
			if(pTags==null)break;
			//得到上次匹配式的最后一位到当前匹配式中最前一位之间的串
			sTmpValue = _sTemplate.substring(nLastIndex,pTags.index);
			//这段串同样需要经过模板的简单内容替换
			sRetVal += this.evaluateText(sTmpValue,_DataSet,_ExtraParams,_sCurrScope);
			//计算新的匹配式的最后一位并记录下来
			nLastIndex = pTags.index+pTags[0].length;
			//当前置标对象
			var oCurrTag = {"TagName":'',"InnerHTML":'',"sAttributes":'','Attributes':null};
			//当前置标的TagName
			oCurrTag.TagName = pTags[2];
			//当前置标的属性值
			oCurrTag.sAttributes = pTags[3];
			//当前置标的InnerHTML
			oCurrTag.InnerHTML = pTags[4];
			//构建属性解析的正则表达式
			//１,在循环中需要每次都new RegExp
			//２,在此处不能使用//形式,否则ＦＦ下会出错
			reTagAttrParser = new RegExp('\\s+([^=]*)="([^"\'>]*)"','g');
			//设置当前置标的Attributes
			oCurrTag.Attributes = {};
			while((pCurrTagAttrs=reTagAttrParser.exec(oCurrTag.sAttributes))!=null){
				oCurrTag.Attributes[(pCurrTagAttrs[1]||'').toLowerCase()] = pCurrTagAttrs[2];
			}
			//处理不同置标的逻辑,便于统一和扩展,定义同样的规范
			//evaluateFor,evaluateRecord等,evaluate+首字母大写其余字母小写的TagName
			var sCamelizeTagName = oCurrTag.TagName.charAt(0).toUpperCase()+oCurrTag.TagName.substring(1).toLowerCase();
			sRetVal += this["evaluate"+sCamelizeTagName](oCurrTag,_DataSet,_ExtraParams,_sCurrScope,_ExtraOptions);
			oCurrTag = null;
		}
		//最后一段模板内容
		sTmpValue = _sTemplate.substring(nLastIndex);
		if(sTmpValue){
			sRetVal += this.evaluateText(sTmpValue,_DataSet,_ExtraParams,_sCurrScope);
		}
		return sRetVal;
	},
	/**
	 * 解析文本
	 */
	evaluateText : function(_sTemplate,_DataSet,_ExtraParams,_sCurrScope){
		var sRetVal = '';
		//表达式正则匹配式
		var reExpression = new RegExp('\\{(\\$|#|@)([^\\}]{0,100})\\}','g');
		//匹配的表达式
		var pExpressions = null;
		//当前游标所处位置，用于字符串的分段串联
		var nLastIndex = 0;
		//临时变量
		var sTmpValue = '';
		//表达式类型
		var cExpType = '';
		//表达式
		var sExpression = '';
		_ExtraParams = _ExtraParams||{};

		while(true){
			//表达式继续解析
			pExpressions = reExpression.exec(_sTemplate);
			//没有匹配的表达式时跳出循环
			if(pExpressions==null)break;
			//得到上次匹配式的最后一位到当前匹配式中最前一位之间的串
			sTmpValue = _sTemplate.substring(nLastIndex,pExpressions.index);
			sRetVal += sTmpValue;
			//计算新的匹配式的最后一位并记录下来
			nLastIndex = pExpressions.index+pExpressions[0].length;
			//获得表达式类型
			cExpType = pExpressions[1];
			//获得表达式串
			sExpression = pExpressions[2];
			if(cExpType=='@'){//Function
				sRetVal += this.evaluateTextFunction(sExpression,_DataSet,_ExtraParams,_sCurrScope);
			}
			else{//Normal
				if(sExpression.indexOf(',')==-1){
					sRetVal += this.evaluateTextSimple(cExpType,sExpression,_DataSet,_ExtraParams,_sCurrScope);
				}
				else{
					sRetVal += this.evaluateTextNormal(cExpType+sExpression,_DataSet,_ExtraParams,_sCurrScope);
				}
			}
		}
		sRetVal	+= _sTemplate.substring(nLastIndex);
		return sRetVal;
	},
	evaluateTextFunction : function(_sExpression,_DataSet,_ExtraParams,_sCurrScope){
		var sEvalExpr = '';
		var reFunction = new RegExp('^([^\\(]*)(\\((.*)\\))?$','g');
		var pCaptures = null;
		var args = [];
		var reArgument = null;
		var pArguCaptures = null;
		var sUnitExpression = '';
		var cUnitType = '';
		var sDefaultValue = '';
		var sRetVal = '';
		//
		pCaptures = reFunction.exec(_sExpression);
		var sFunctionName = pCaptures[1];
		var sArguments = pCaptures[3];
		if(sArguments.trim()!=''){
			args = sArguments.split(',');
			for(var i=0;i<args.length;i++){
				reArgument = new RegExp('^\\s*(#|\\$)?([^\\|\\s]*)\\s*(\\|\\|(.*))?$','g');
				pArguCaptures = reArgument.exec(args[i]);
				if(pArguCaptures==null)continue;
				cUnitType = pArguCaptures[1];
				sUnitExpression = (pArguCaptures[2]||'').toUpperCase();
				sDefaultValue = (pArguCaptures[4]||'').trim();
				if(cUnitType=='$'){
					args[i] = this.urlQuery['$'+sUnitExpression]||_ExtraParams['$'+sUnitExpression]||sDefaultValue;
				}
				else if(cUnitType=='#'){
					if(_sCurrScope!=null&&_sCurrScope!=''){
						sUnitExpression = (sUnitExpression=='.')?_sCurrScope:_sCurrScope+'.'+sUnitExpression;
					}
					args[i] = this.value(sUnitExpression,_DataSet)||sDefaultValue;
				}
			}
		}
		try{
			sEvalExpr = 'sRetVal='+sFunctionName+'.apply('+sFunctionName.replace(/\.[^\.]*$/g,'')+',args);';
			eval(sEvalExpr);
			return (sRetVal==null)?'':sRetVal;
		}catch(err){
			throw new Error('表达式"{@'+_sExpression+'}" error occurs when eval "'+sEvalExpr+'"\n'+err.message);
		}
	},
	evaluateTextSimple : function(_cUnitType,_sExpression,_DataSet,_ExtraParams,_sCurrScope){
		var sUnitExpression = _sExpression;
		var cUnitType = _cUnitType;
		var sDefaultValue = '';
		if(cUnitType=='$'){
			sUnitExpression = sUnitExpression.toUpperCase();
			return this.urlQuery[cUnitType+sUnitExpression]||_ExtraParams[cUnitType+sUnitExpression]||sDefaultValue;
		}
		else if(cUnitType=='#'){
			if(_sCurrScope!=null&&_sCurrScope!=''){
				sUnitExpression = (sUnitExpression=='.')?_sCurrScope:_sCurrScope+'.'+sUnitExpression;
			}
			return this.value(sUnitExpression,_DataSet)||sDefaultValue;
		}
	},
	evaluateTextNormal : function(_sExpression,_DataSet,_ExtraParams,_sCurrScope){
		var sEvalExpr = '';
		var pParts = null;
		var sUnitExpression = '';
		var cUnitType = '';
		var sDefaultValue = '';
		var sRetVal = '';
		pParts = _sExpression.split(',');
		if(pParts.length<=2){
			sDefaultValue = (pParts[1]||'').trim();
			sUnitExpression = pParts[0].toUpperCase().trim();
			if(sUnitExpression.length>0){
				cUnitType = sUnitExpression.charAt(0);
			}
			if(cUnitType=='$'){
				return this.urlQuery[sUnitExpression]||_ExtraParams[sUnitExpression]||sDefaultValue;
			}
			else if(cUnitType=='#'){
				sUnitExpression = sUnitExpression.substring(1);
				if(_sCurrScope!=null&&_sCurrScope!=''){
					sUnitExpression = (sUnitExpression=='.')?_sCurrScope:_sCurrScope+'.'+sUnitExpression;
				}
				return this.value(sUnitExpression,_DataSet)||sDefaultValue;
			}
		}
		else if(pParts.length==3){
			var pUnitExpressions = null;
			var pDefaultValues = null;
			var sFunctionName = '';
			sUnitExpression	= pParts[0].toUpperCase();
			pUnitExpressions = sUnitExpression.split(';');
			sDefaultValue = (pParts[1]||'').trim();
			pDefaultValues = sDefaultValue.split(';');
			sFunctionName = pParts[2];
			var args = [];
			for(var i=0;i<pUnitExpressions.length;i++){
				sDefaultValue = (pDefaultValues[i]||'').trim();
				sUnitExpression = pUnitExpressions[i].trim();
				if(sUnitExpression.length>0){
					cUnitType = sUnitExpression.charAt(0);
				}
				if(cUnitType=='$'){
					args[i] = this.urlQuery[sUnitExpression]||_ExtraParams[sUnitExpression]||sDefaultValue;
				}
				else if(cUnitType=='#'){
					sUnitExpression = sUnitExpression.substring(1);
					if(_sCurrScope!=null&&_sCurrScope!=''){
						sUnitExpression = (sUnitExpression=='.')?_sCurrScope:_sCurrScope+'.'+sUnitExpression;
					}
					args[i] = this.value(sUnitExpression,_DataSet)||sDefaultValue;
				}
				else{
					args[i] = sUnitExpression;
				}
			}
			try{
				sEvalExpr = 'sRetVal = '+sFunctionName+'.apply('+sFunctionName.replace(/\.[^\.]*$/g,'')+',args);';
				eval(sEvalExpr);
				return (sRetVal==null)?'':sRetVal;
			}catch(err){
				throw new Error('表达式"{'+_sExpression+'}" error occurs when eval "'+sEvalExpr+'"\n'+err.message);
			}
		}
	},
	/**
	 * 解析Textarea模板中的for(\d)置标
	*/
	evaluateFor : function(_CurrTag,_DataSet,_ExtraParams,_sCurrScope,_ExtraOptions){
		//置标解析返回值
		var sRetVal = '';
		//获得置标当前的属性
		var oAttributes = _CurrTag.Attributes;
		//获得置标当前的InnerHTML
		var myInnerTemplate = _CurrTag.InnerHTML;
		//clone出当前置标自身的Extra参数对象
		var myExtraParams = Object.clone(_ExtraParams);
		//若params属性有值,则填充值以供内部结构使用
		//规范:以,号分隔,Xpath形式的串,在内部可以使用$+串.
		var caller = this;
		if(oAttributes['params']){
			oAttributes['params'].split(',').each(
				function(s){
					var s = s.trim();
					myExtraParams['$'+s.toUpperCase()] = 
						caller.evaluateTextSimple('#',s,_DataSet,_ExtraParams,_sCurrScope)||
						caller.evaluateTextSimple('$',s,_DataSet,_ExtraParams,_sCurrScope);
				}
			)
		}
		//验证性提示
		if(oAttributes['select']==null){
			alert('For置标未设置"select"属性.\nAttributes:'+_CurrTag.sAttributes+"\nInnerHTML:"+myInnerTemplate);
			return '';
		}
		//获得置标的数据集选择域
		var mySelection = (oAttributes['select']=='.')?_sCurrScope: _sCurrScope+'.'+oAttributes['select'];
		//获得数据集中数组对象
		var myData = this.array(mySelection,_DataSet)||[];
		var myCnt = myData.length;
		//数组对象数为0时,设置缺省引用对象的innerHTML或者缺省字符串
		if(myCnt==0){
			var sBlankRef = oAttributes['blankref'];
			var sBlankText = '';
			if($(sBlankRef)){
				sBlankText = $(sBlankRef).innerHTML;
			}
			else{
				sBlankText = oAttributes['blanktext']||'';
			}
			if(sBlankText!=''){
				sBlankText = this.evaluateText(sBlankText,_DataSet,_ExtraParams,_sCurrScope);
			}
			return sBlankText;
		}
		var isTemplateTagExist = oAttributes['justreplace']==null&&this.isTemplateTagExist(myInnerTemplate);
		var HasRecord = false;
		if(oAttributes['norecord']==null&&isTemplateTagExist&&myInnerTemplate.match(new RegExp('<record [^>]*>(.|\n|\r)*?</record>','img'))){
			HasRecord = true;
		}
		if(HasRecord){
			//Extra选择性参数,HasRecord需要由Record置标指定,CurrIndex可由Record或For来维护索引
			var ExtraOptions = {TagName:'for',CurrIndex:0,DataLength:myCnt};
			while(ExtraOptions.CurrIndex<myCnt){
				//最外层For置标的起始序号由外部控制
				myExtraParams['$$INDEX'] = (_ExtraParams['START_INDEX']||0)+(ExtraOptions.CurrIndex+1);
				//递归时若有For置标,其序号均从0开始
				myExtraParams['START_INDEX'] = 0;
				//递归获得置标内部的模板替换值
				sRetVal += this.evaluateTemplate(myInnerTemplate,myData,myExtraParams,ExtraOptions.CurrIndex,ExtraOptions)
				//若For置标中不含有Record置标,索引由自身维护
				//ExtraOptions.CurrIndex++;
			}
		}
		else{//not HasRecord
			for(var i=0;i<myCnt;i++){
				//最外层For置标的起始序号由外部控制
				myExtraParams['$$INDEX'] = (_ExtraParams['START_INDEX']||0)+(i+1);
				//递归时若有For置标,其序号均从0开始
				myExtraParams['START_INDEX'] = 0;
				if(isTemplateTagExist){
					//递归获得置标内部的模板替换值
					sRetVal += this.evaluateTemplate(myInnerTemplate,myData[i],myExtraParams,'');
				}
				else{
					sRetVal += this.evaluateText(myInnerTemplate,myData[i],myExtraParams,'');
				}
			}
		}
		return sRetVal;
	},
	/**
	 * 解析Textarea模板中的record置标
	*/
	evaluateRecord : function(_CurrTag,_DataSet,_ExtraParams,_sCurrScope,_ExtraOptions){
		//置标解析返回值
		var sRetVal = '';
		//获得置标当前的属性
		var oAttributes = _CurrTag.Attributes;
		//获得置标当前的InnerHTML
		var myInnerTemplate = _CurrTag.InnerHTML;
		//clone出当前置标自身的Extra参数对象
		var myExtraParams = Object.clone(_ExtraParams);
		//ExtraOptions的依赖性验证
		if(!_ExtraOptions||_ExtraOptions.TagName!='for'){
			alert('Record置标必须处在For置标中');
			return '';
		}
		//设置循环次数
		var oLoopNum = parseInt(oAttributes['num']||'1');
		
		for(var oLoopedNum = 0;oLoopedNum<oLoopNum;oLoopedNum++){
			//判断当前索引值是否已经超出外层For的数据长度
			//超出时作缺省值处理
			if(_ExtraOptions.CurrIndex>=_ExtraOptions.DataLength){
				var sBlankRef = oAttributes['blankref'];
				if($(sBlankRef)){
					sRetVal += $(sBlankRef).innerHTML;
				}
				var sBlankText = oAttributes['blanktext'];
				sRetVal += sBlankText || '';
				continue;
			}
			//设置一次循环中的$$INDEX变量
			myExtraParams['$$INDEX'] = _ExtraParams['$$INDEX']+oLoopedNum;
			//获取一次循环中的数据集
			var myInnerDataSet = _DataSet[_ExtraOptions.CurrIndex];
			//递归获得置标内部的模板替换值
			sRetVal += this.evaluateTemplate(myInnerTemplate,myInnerDataSet,myExtraParams,'',null)
			//递增当前索引值
			_ExtraOptions.CurrIndex++;
		}
		return sRetVal;
	},
	/**
	 * 通过表达式从JSON中取值
	 * @param _sExpression 表达式,如aa.bb.cc
	 * @param _oJson JSON对象
	 * @return 若选中范围为数组,则返回第一个值,若选中范围不在JSON对象内部,则返回null
	 */
	value : function(_sExpression,_oJson){
		var oValue	= this.valid(_sExpression,_oJson);
		if(oValue){
			if(Array.isArray(oValue)){
				oValue	= oValue[0];
			}
			else if(typeof oValue == 'object'){
				oValue	= oValue['NODEVALUE'];
			}
		}
		return oValue;
	},
	/**
	 * 通过表达式从JSON中取数组
	 * @param _sExpression 表达式,如aa.bb.cc
	 * @param _oJson JSON对象
	 * @return 若选中范围不为数据,则将值构造成数组返回,若选中范围不在JSON对象内部,则返回null
	 */
	array : function(_sExpression,_oJson){
		var oArray	= this.valid(_sExpression,_oJson);
		return ( !oArray || Array.isArray(oArray) ) ? oArray : [oArray] ;
	},
	/**
	 * 验证当前表达式是否可从当前JSON对象中获得值
	 * @param _sExpression 表达式,如aa.bb.cc
	 * @param _oJson JSON对象
	 * @return 若选中范围不在JSON对象内部,则返回null,否则返回表达式对应的对象
	 */
	valid : function(_sExpression,_oJson){
		if(_oJson==null||_sExpression.trim()==''){
			return _oJson;
		}
		_sExpression = _sExpression.toUpperCase();
		var cmds = _sExpression.split('.');
		var tmpObject = _oJson;
		var tmpObject2 = null;
		var cnt = cmds.length;
		for(var index=0,tmpCmd='';index<cnt;index++){
			tmpCmd = cmds[index];
			if(tmpCmd.trim()=='')continue;
			tmpObject2 = tmpObject[tmpCmd];
			if(tmpObject2 == null){
				if(tmpCmd=='0'&&!Array.isArray(tmpObject)){//适应for置标中只有一个元素的情况
				}
				else if(Array.isArray(tmpObject)&&tmpObject.length>0){//适应数组中取首元素不带index的情况
					tmpObject = tmpObject[0][tmpCmd];
				}
				else{
					return null;
				}
			}
			else{
				tmpObject = tmpObject2;
			}
			tmpObject2 = null;
		}
		return tmpObject;
	}
};
ClassName(com.trs.ajaxframe.TempEvaler,'ajaxframe.TempEvaler');