$package('com.trs.ajaxframe');

com.trs.ajaxframe.TrsAjax	= {};
/**
 * AJAX DOM Element基类
 */
com.trs.ajaxframe.TrsAjax.Element = Class.create('ajaxframe.TrsAjax.Element');
com.trs.ajaxframe.TrsAjax.Element.prototype = {
	/**节点名称*/
	nodeName:'',
	/**对应的HTML DOM Element对象*/
	element:null,
	/**父级的AJAX DOM Element对象*/
	parent:null,
	/**需要处理的节点数,包括自身*/
	todo:1,
	/**已经处理完成的节点数,包括自身*/
	done:0,
	/**所有的子AJAX DOM Element对象*/
	childNodes:null,
	/**
	 * 构造函数
	 * @param _nodeName 节点名
	 * @param _element HTML DOM Element对象
	 * @param _parent 父级的AJAX DOM Element对象
	 */
	initialize:function(_nodeName,_element,_parent){
		this.nodeName	= _nodeName;
		this.element	= _element;
		this.parent		= _parent;
		this.todo		= 1;
		this.done		= 0;
		this.childNodes	= [];
	},
	/**
	 * 增加一个子AJAX DOM Element对象
	 * @param _trsAjaxElement 子AJAX DOM Element对象
	 */ 
	pushChild:function(_trsAjaxElement){
		this.todo++;
		this.childNodes.push(_trsAjaxElement);
	},
	/**
	 * 供自身解析和子解析完成后调用的方法,以更新done数,当done==todo时,表示当前Element已经解析完成,回调父节点的callBack方法
	 * @param _oParser 解析器对象
	 * @param _oDatasource 数据源对象
	 * @param _eDaton 数据源置标对象
	 * @param _oJson JSON对象
	 * @param _oParams 变量值集
	 * @param _sSelection 当前选择范围
	 * @return true 表示已经完成,false 表示未完成
	 */
	callBack:function(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		this.done++;
		if(this.todo == this.done){
			this.html	= this.toHtml();
//			$log().debug(this.done+'/'+this.todo);
//			$log().debug(this.nodeName+':'+this.html);
			this.done	= 0;
			this.parent.callBack(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection);
			return true;
		}
//		$log().debug(this.done+'/'+this.todo);
//		$log().debug(this.nodeName+':'+this.html);
		return false;
	},
	/**
	 * 当前AJAX DOM解析:<br>
	 * 1,解析自身,回调callBack<br>
	 * 2,若解析自身标志解析未完成,则解析所有的子<br>
	 * @param _oParser 解析器对象
	 * @param _oDatasource 数据源对象
	 * @param _eDaton 数据源置标对象
	 * @param _oJson JSON对象
	 * @param _oParams 变量值集
	 * @param _sSelection 当前选择范围
	 */
	parse:function(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		this._parseMe(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection);
		var bDone	= this.callBack(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection);
		if(!bDone){
			this._parseChilds(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection);
		}
	},
	/**
	 * 解析自身<br>
	 * --------------------------------<br>
	 * 根据nodeName不同,解析的逻辑也不同：<br>
	 * SCRIPT ==> 生成<script id=''></script>代码<br>
	 * #TEST ==> 生成相应的HTML代码<br>
	 * More Custom Tag ==> 通过扩展的接口获取HTML内容<br>
	 * justReplace ==> innerHTML仅用于替换,不处理更多逻辑<br>
	 * --------------------------------<br>
	 * @param _oParser 解析器对象
	 * @param _oDatasource 数据源对象
	 * @param _eDaton 数据源置标对象
	 * @param _oJson JSON对象
	 * @param _oParams 变量值集
	 * @param _sSelection 当前选择范围
	 */
	_parseMe:function(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		this.startTime = new Date().getTime();
		switch(this.nodeName){
			case 'DATASOURCE':
			case 'NAVIGATE':
			case 'FOR':
			case 'VALUE':
			case 'PARAM':
			case 'IF':
			case 'RECORD':
			case 'DS':
				this.html	= '#INNERHTML#';
				break;
			case 'SCRIPT':
				var text	= this.element.innerHTML;
				var id		= this.element.id;
				text		= _oParser._fillValue(text , _oJson , _oParams ,_sSelection);
				this.html	= text;
				this.html	= '<SCRIPT id="'+id+'">'+text+'</SCRIPT>';
				this.done	= this.todo-1;
				break;
			case '#TEXT':
				var text	= this.element.nodeValue;
				text		= _oParser._fillValue(text , _oJson , _oParams ,_sSelection);
				this.html	= text;
				this.done	= this.todo-1;
				break;
			default:
				if(_oParser['_parse_'+this.nodeName]){
					this.html	= _oParser['_parse_'+this.nodeName](this.element , _oDatasource , _eDatasource , _oJson , _oParams ,_sSelection);
					this.done	= this.todo-1;
				}
				else{
					if(!this.sAttrs){
						this.sAttrs	= com.trs.ajaxframe.TagHelper.getAttributes(this.element , this.nodeName);
					}
					var sAttrs		= _oParser._fillValue(this.sAttrs , _oJson , _oParams ,_sSelection);
					sAttrs			= (sAttrs=='')?'':(' '+sAttrs);
					var sTagName = document.resolveTagName(this.element);
					if(sTagName&&sTagName.indexOf(':')!=-1){
						if(sTagName.indexOf($parser.nameSpace.toLowerCase()+':')!=0){
							this.html	= '<'+sTagName+sAttrs+'>';
							if(this.element.getAttribute('justReplace')){
								var sHtml	= this.element.innerHTML;
								sHtml		= sHtml.replace(new RegExp($parser.nameSpace+':','ig'),'');
								sHtml		= _oParser._fillValue(sHtml , _oJson , _oParams ,_sSelection);
								this.html	+= sHtml;
								this.done	= this.todo-1;
							}
							else{
								this.html	+= '#INNERHTML#';
							}
							this.html	+= '</'+sTagName+'>';
							return;
						}
					}
					if(com.trs.ajaxframe.TagHelper.isNoEndTag(this.nodeName)){
						this.html	= '<'+this.nodeName+sAttrs+'/>';
						this.done	= this.todo-1;
					}
					else{
						this.html	= '<'+this.nodeName+sAttrs+'>';
						if(this.element.getAttribute('justReplace')){
							var sHtml	= this.element.innerHTML;
							//ge gfc add @ 2006-9-27 10:17 防止href/src的'{'和'}'被替换
							if(!_IE) {
								var ptn = /(%7B#)([a-z]+)(%7D)/ig;
								sHtml = sHtml.replace(ptn, '{#$2}');
							}
							
							sHtml		= sHtml.replace(new RegExp($parser.nameSpace+':','ig'),'');
							sHtml		= sHtml.replace(/(\s+)_(style|rowspan)(\s*=)/ig,'$1$2$3');
							sHtml		= _oParser._fillValue(sHtml , _oJson , _oParams ,_sSelection);
							this.html	+= sHtml;
							this.done	= this.todo-1;
						}
						else{
							this.html	+= '#INNERHTML#';
						}
						this.html	+= '</'+this.nodeName+'>';
					}
				}
				break;
		}
		this.endTime = new Date().getTime();
	},
	/**
	 * 私有函数
	 * 获得_element的选择范围
	 * @param _element
	 * @param _sSelection 当前范围
	 */
	_getSelection : function(_element , _sSelection){
		var selection	= _element.getAttribute("select");
		if(selection){
			_sSelection		= (_sSelection)?_sSelection+'.'+selection:selection;
			return _sSelection;
		}
		else if(_sSelection){
			return _sSelection;
		}
		return '';
	},
	/**
	 * 解析所有的子
	 * @param _oParser 解析器对象
	 * @param _oDatasource 数据源对象
	 * @param _eDaton 数据源置标对象
	 * @param _oJson JSON对象
	 * @param _oParams 变量值集
	 * @param _sSelection 当前选择范围
	 */
	_parseChilds:function(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		var selection	= this._getSelection(this.element , _sSelection);
		for(var i=0;i<this.childNodes.length;i++){
			var child	= this.childNodes[i];
//			$log().debug(this.nodeName+',child '+i+':'+child);
			child.parse(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , selection);
		}
	},
	/**
	 * 解析完成后,把自身和所有的子的html进行联合,得出当前DOM Element的html.
	 */
	toHtml:function(){
		if(this.html){
			var sHtml	= '';
			for(var i=0;i<this.childNodes.length;i++){
				var child	= this.childNodes[i];
				sHtml		+= child.html;
				child.html	= '';
			}
			return this.html.replace(/#INNERHTML#/i,sHtml);
		}
		else{
			return '';
		}
	}
};
/**
 * AJAX DOM Element的扩展类:独立的数据源
 * @see com.trs.ajaxframe.TrsAjax.Element
 */
com.trs.ajaxframe.TrsAjax.Ds = Class.create('ajaxframe.TrsAjax.Ds');
Object.extend(com.trs.ajaxframe.TrsAjax.Ds.prototype,com.trs.ajaxframe.TrsAjax.Element.prototype);
Object.extend(com.trs.ajaxframe.TrsAjax.Ds.prototype,{
	callBack : function (_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		this.done++;
		if(this.todo == this.done){
			this.html	= this.toHtml();
			this.done	= 0;
			var oldDiv	= $(this.element.id);
			var newDiv	= document.createElement('SPAN');
			var sAttrs		= this.sAttrs;
			sAttrs			= (sAttrs=='')?'':(' '+sAttrs);
			var outerHTML	= '<SPAN'+sAttrs+'></SPAN>';
			newDiv.innerHTML = outerHTML;
			oldDiv.parentNode.insertBefore(newDiv,oldDiv);
			$removeNode(oldDiv);
			if(oldDiv!=this.element){
				delete oldDiv;
			}
			var tmp		= newDiv.childNodes[0];
			newDiv.parentNode.insertBefore(tmp , newDiv);
			newDiv.parentNode.removeChild(newDiv);
			delete newDiv;
			Element.update(tmp , this.html);
			tmp.style.display	= '';
			if(this.fCallBack){
				(this.fCallBack)(this.oTransport , _oJson);
			}
			_oParser._onSuccess(this.element , this.oTransport , _oJson);
			this.oTransport	 = null;
			delete _oJson;
			delete _oParams;
			this.endTime = new Date().getTime();
			return true;
		}
		return false;
	},
	parse:function(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection , _oTransport , _fCallBack){
		this.startTime = new Date().getTime();
		this._parseMe(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection);
		this.oTransport	= _oTransport;
		this.fCallBack	= _fCallBack;
		var bDone		= this.callBack(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection);
		if(!bDone){
			this._parseChilds(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection);
		}
	},
	_parseMe:function(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		if(!this.sAttrs){
			this.sAttrs	= com.trs.ajaxframe.TagHelper.getAttributes(this.element , this.nodeName);
		}
		this.html	= '#INNERHTML#';
	}
});
/**
 * AJAX DOM Element的扩展类:For
 * @see com.trs.ajaxframe.TrsAjax.Element
 */
com.trs.ajaxframe.TrsAjax.For = Class.create('ajaxframe.TrsAjax.For');
Object.extend(com.trs.ajaxframe.TrsAjax.For.prototype,com.trs.ajaxframe.TrsAjax.Element.prototype);
Object.extend(com.trs.ajaxframe.TrsAjax.For.prototype,{
	callBack : function (_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		this.done++;
		if(this.todo == this.done){
			if(!this.hasRecord){
				this.currentPos++;
			}
//			$log().debug("In For:"+this.currentPos+'/'+this.endPos);
			if(this.currentPos < this.endPos){
				this.aHtmls.push(this.toHtml());
//				this.html	= this.aHtmls.join('');
//				$log().debug(this.done+'/'+this.todo);
//				$log().debug(this.nodeName+':'+this.html);
				this.done	= 0;
				this.parse(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection);
			}
			else{
				this.aHtmls.push(this.toHtml());
				this.html	= this.aHtmls.join('');
//				$log().debug(this.done+'/'+this.todo);
//				$log().debug(this.nodeName+':'+this.html);
				this.done	= 0;
				this._inited_parse	= false;
				this.hasRecord		= false;
				delete this.aHtmls;
				this.endTime = new Date().getTime();
				$log().error('for parse last time:'+(this.endTime - this.startTime));
				this.parent.callBack(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection);
			}
			return true;
		}
//		$log().debug(this.done+'/'+this.todo);
//		$log().debug(this.nodeName+':'+this.html);
		return false;
	},
	_parseMe : function (_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		this.startTime = new Date().getTime();
		var oElement	= this.element;
		if(!this._inited_parse){
			var selection	= oElement.getAttribute("select");
			if(selection == null){
				throw new Error('missed select attribute in tag "'+ _oParser.nameSpace +':for"');
			}
			selection		= (_sSelection)?_sSelection+'.'+selection:selection;
			this.selection	= selection;
			var tEnum		= com.trs.ajaxframe.TagHelper.array(selection , _oJson);
			if(tEnum==null){
				$log().warn('in tag "For" enumeration is null with the selection:"' + selection + '"');
				tEnum=[];
			}
			var iLength=tEnum.length;
			if(iLength>0){
				var iCurrentPage= _eDatasource.getAttribute("currentPage");
				iCurrentPage	= (iCurrentPage)?parseInt(iCurrentPage):1;
				if(iCurrentPage==1&&PageContext&&PageContext.params){
					iCurrentPage = PageContext.params["CurrPage"]||1;
				}
				var iPageSize	= iLength;
				try{
					iPageSize	= _oDatasource.pageSize(_oJson);
					if(isNaN(iPageSize)||iPageSize==0){
						iPageSize	= iLength;
					}
				}catch(err){}
				var iNum		= oElement.getAttribute("num");
				if(iNum!=null&&parseInt(iNum)<=0){
					iNum		= iLength;
				}
				iNum			= (iNum)?parseInt(iNum):iPageSize;
				var iOffset		= (iCurrentPage-1)*iPageSize;
				this.indexOffset= 0;
				if(!_oDatasource.isLocal){
					this.indexOffset= iOffset;
					iOffset		= 0;
				}
				var iStartPos	= oElement.getAttribute("startPos");
				iStartPos		= (iStartPos)?parseInt(iStartPos):0;
				iStartPos		= (iStartPos>0)?iStartPos:0;
				iStartPos		= iOffset + iStartPos;
				var iEndPos		= iStartPos + iNum;
				iEndPos			= (iEndPos<=iLength)?iEndPos:iLength;
				this.startPos	= iStartPos;
				this.endPos		= iEndPos;
				this.currentPos	= iStartPos;
				this.enumeration	= selection;
				_oParams['$$INDEX']	= this.indexOffset+this.currentPos+1;
				this.html		= '#INNERHTML#';
				this._inited_parse	= true;
			}
			else{
				this.currentPos	= 0;
				this.endPos		= 0;
				this.done		= this.todo-1;
				var sBlankHtml	= oElement.getAttribute("blankText");
				if(sBlankHtml == null){
					var sBlankRef	= oElement.getAttribute("blankRef");
					if(sBlankRef != null){
						sBlankRef	= $(sBlankRef);
						if(sBlankRef){
							sBlankHtml	= sBlankRef.innerHTML;
						}
					}
				}
				else{
					sBlankHtml = _oParser._fillValue(sBlankHtml , _oJson , _oParams ,_sSelection);
				}
				this.html		= sBlankHtml;
			}
			this.aHtmls			= [];
		}
		else{
			_oParams['$$INDEX']	= this.indexOffset+this.currentPos+1;
			this.html			= '#INNERHTML#';
		}
	},
	_getSelection : function(_element , _sSelection){
		return this.selection + '.' + this.currentPos;
	}
});
/**
 * AJAX DOM Element的扩展类:Record
 * @see com.trs.ajaxframe.TrsAjax.Element
 */
com.trs.ajaxframe.TrsAjax.Record = Class.create('ajaxframe.TrsAjax.Record');
Object.extend(com.trs.ajaxframe.TrsAjax.Record.prototype,com.trs.ajaxframe.TrsAjax.Element.prototype);
Object.extend(com.trs.ajaxframe.TrsAjax.Record.prototype,{
	/**
	 * 得到父一级的For Ajax DOM Element对象
	 */
	_parentFor : function(){
		var oParent	= this.parent;
		while(oParent){
			if(oParent.nodeName == 'FOR'){
				return oParent;
			}
			oParent	= oParent.parent;
		}
		return null;
	},
	callBack : function (_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		this.done++;
		if(this.todo == this.done){
			this.currentPos++;
			this.parentFor.currentPos++;
//			$log().debug("In Record:"+this.currentPos+'/'+this.endPos);
			if(this.currentPos < this.endPos){
				this.aHtmls.push(this.toHtml());
//				this.html	= this.aHtmls.join('');
//				$log().debug(this.done+'/'+this.todo);
//				$log().debug(this.nodeName+':'+this.html);
//				this.html	= '';
				this.done	= 0;
				this.parse(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection);
			}
			else{
				this.aHtmls.push(this.toHtml());
				this.html	= this.aHtmls.join('');
//				$log().debug(this.done+'/'+this.todo);
//				$log().debug(this.nodeName+':'+this.html);
				this.done	= 0;
				this._inited_parse	= false;
				delete this.aHtmls;
				this.endTime = new Date().getTime();
				$log().error('record parse last time:'+(this.endTime - this.startTime));
				this.parent.callBack(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection);
			}
			return true;
		}
//		$log().debug(this.done+'/'+this.todo);
//		$log().debug(this.nodeName+':'+this.html);
		return false;
	},
	_parseMe : function (_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		this.startTime = new Date().getTime();
		var oElement	= this.element;
		if(!this._inited_parse){
			this.parentFor	= this._parentFor();
			this.parentFor.hasRecord	= true;
			if(!this.parentFor){
				throw new Error('Tag "'+ _oParser.nameSpace +':record" must be placed in Tag "'+ _oParser.nameSpace +':for"');
			}
			var iNum		= oElement.getAttribute("num");
			iNum			= (iNum)?parseInt(iNum):1;
			this.currentPos	= this.parentFor.currentPos;
			this.endPos		= this.currentPos+iNum;
			_oParams['$$INDEX']	= this.parentFor.indexOffset+this.currentPos+1;
			this.selection	= this.parentFor.enumeration;
			var sBlankHtml	= oElement.getAttribute("blankText");
			if(sBlankHtml == null){
				var sBlankRef	= oElement.getAttribute("blankRef");
				if(sBlankRef != null){
					sBlankRef	= $(sBlankRef);
					if(sBlankRef){
						sBlankHtml	= sBlankRef.innerHTML;
					}
				}
			}
			else{
				sBlankHtml = _oParser._fillValue(sBlankHtml , _oJson , _oParams ,_sSelection);
			}
			this.blankHtml		= sBlankHtml;
			if(this.currentPos < this.parentFor.endPos){
				this.html			= '#INNERHTML#';
			}
			else{
				this.done			= this.todo-1;
				this.html			= this.blankHtml;
			}
			this.aHtmls			= [];
			this._inited_parse	= true;
		}
		else{
			if(this.currentPos < this.parentFor.endPos){
				_oParams['$$INDEX']	= this.parentFor.indexOffset+this.currentPos+1;
				this.html			= '#INNERHTML#';
			}
			else{
				this.done			= this.todo-1;
				this.html			= this.blankHtml;
			}
		}
	},
	_getSelection : function(_element , _sSelection){
		return this.selection + '.' + this.currentPos;
	}
});
/**
 * AJAX DOM Element的扩展类:Value
 * @see com.trs.ajaxframe.TrsAjax.Element
 */
com.trs.ajaxframe.TrsAjax.Value = Class.create('ajaxframe.TrsAjax.Value');
Object.extend(com.trs.ajaxframe.TrsAjax.Value.prototype,com.trs.ajaxframe.TrsAjax.Element.prototype);
Object.extend(com.trs.ajaxframe.TrsAjax.Value.prototype,{
	_parseMe : function (_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		var oElement	= this.element;
		var selection	= oElement.getAttribute("select");
		if(selection == null){
			throw new Error('missed select attribute in tag "'+ _oParser.nameSpace +':value"');
		}
		selection		= (_sSelection)?_sSelection+'.'+selection:selection;
		var sValue		= '';
		if(selection.startsWith('$$')){
			sValue		= com.trs.ajaxframe.TagHelper.value(selection , _oParams);
		}
		else{
			sValue		= com.trs.ajaxframe.TagHelper.value(selection , _oJson);
		}
		if(sValue==null){
			$log().warn('in tag "Value" value is null with the selection:"' + selection + '"');
			sValue		= '';
		}
		sValue			= ''+sValue;
		var evalScript	= oElement.getAttribute('evalScript');
		evalScript		= (evalScript)?evalScript.toLowerCase():'false';
		if(evalScript=='true'||evalScript=='yes'){
			this.html	= sValue;
		}
		else{
			this.html	= sValue.stripScripts();
		}
		this.done		= this.todo-1;
	}
});
/**
 * AJAX DOM Element的扩展类:Param
 * @see com.trs.ajaxframe.TrsAjax.Element
 */
com.trs.ajaxframe.TrsAjax.Param = Class.create('ajaxframe.TrsAjax.Param');
Object.extend(com.trs.ajaxframe.TrsAjax.Param.prototype,com.trs.ajaxframe.TrsAjax.Element.prototype);
Object.extend(com.trs.ajaxframe.TrsAjax.Param.prototype,{
	_parseMe : function (_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		var oElement	= this.element;
		var sId			= oElement.getAttribute("id");
		if( sId == null ){
			throw new Error('missed id attribute in tag "'+ _oParser.nameSpace +':param"');
		}
		sId				= sId.toUpperCase().trim();
		var selection	= oElement.getAttribute("select");
		var sValue		= null;
		var sDefault	= oElement.getAttribute("default");
		var sCmd		= oElement.getAttribute("cmd");
		if(selection == null){
			sValue		= oElement.getAttribute("value");
			if(!sValue){
				throw new Error('missed select/value attribute in tag "'+ _oParser.nameSpace +':param"');
			}
			sValue		= _oParser._fillValue(sValue , _oJson , _oParams ,_sSelection);
		}
		else{
			if(selection.startsWith('$$')){
				sValue	= com.trs.ajaxframe.TagHelper.value(selection , _oParams);
			}
			else{
				selection	= (_sSelection)?_sSelection+'.'+selection:selection;
				sValue	= com.trs.ajaxframe.TagHelper.value(selection , _oJson);
			}
			if(sValue==null){
				$log().warn('in tag "Param" value is null with the selection:"' + selection + '"');
				sValue	= sDefault || '';
			}
		}
		if(sCmd){
			try{
				sValue	= eval(sCmd+'("'+sValue+'")');
			}catch(err){
				throw new Error('error occurs when eval \''+sCmd+'("'+sValue+'")'+'\'');
			}
		}
		_oParams['$'+sId]	= sValue;
		this.html		= '';
		this.done		= this.todo-1;
	}
});
/**
 * AJAX DOM Element的扩展类:If
 * @see com.trs.ajaxframe.TrsAjax.Element
 */
com.trs.ajaxframe.TrsAjax.If = Class.create('ajaxframe.TrsAjax.If');
Object.extend(com.trs.ajaxframe.TrsAjax.If.prototype,com.trs.ajaxframe.TrsAjax.Element.prototype);
Object.extend(com.trs.ajaxframe.TrsAjax.If.prototype,{
	_parseMe : function (_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		var oElement	= this.element;
		var sTest	= oElement.getAttribute("test");
		if(sTest == null){
			throw new Error('missed test attribute in tag "'+ _oParser.nameSpace +':if"');
		}
		sTest		= _oParser._fillValue(sTest , _oJson , _oParams ,_sSelection);
		var bOutput	= false;
		try{
			bOutput	= eval(sTest);
		}catch(err){
			throw new Error("some error occurs when eval the test attribute:"+err.message+'\n------\n'+sTest+'\n------');
		}
		if(bOutput){
			this.html	= '#INNERHTML#';
		}
		else{
			this.html	= '';
			this.done	= this.todo-1;
		}
	}
});
/**
 * AJAX DOM Element的扩展类:Nav
 * @see com.trs.ajaxframe.TrsAjax.Element
 */
com.trs.ajaxframe.TrsAjax.Nav = Class.create('ajaxframe.TrsAjax.Nav');
Object.extend(com.trs.ajaxframe.TrsAjax.Nav.prototype,com.trs.ajaxframe.TrsAjax.Element.prototype);
Object.extend(com.trs.ajaxframe.TrsAjax.Nav.prototype,{
	_parseMe : function (_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		var oElement= this.element;
		var sId		= oElement.getAttribute("id");
		var sType	= oElement.getAttribute("type");
		var oNav	= com.trs.ajaxframe.Navigate(sId , sType);
		this.html	= _oParser._navigate(_eDatasource , _oDatasource , _oJson , oNav);
		this.done	= this.todo-1;
	}
});
/**
 * AJAX DOM Element的扩展类:InnerDs
 * @see com.trs.ajaxframe.TrsAjax.Element
 */
com.trs.ajaxframe.TrsAjax.InnerDs = Class.create('ajaxframe.TrsAjax.InnerDs');
Object.extend(com.trs.ajaxframe.TrsAjax.InnerDs.prototype,com.trs.ajaxframe.TrsAjax.Element.prototype);
Object.extend(com.trs.ajaxframe.TrsAjax.InnerDs.prototype,{
	callBack:function(_oParser){
		this.done++;
		if(this.todo == this.done){
			this.html	= this.toHtml();
//			$log().debug(this.done+'/'+this.todo);
//			$log().debug(this.nodeName+':'+this.html);
			this.done	= 0;
			if(this.successFlag){
				_oParser._onSuccess(this.element , this.transport , this.json);
			}
			else{
				if(this._500Flag){
					_oParser._on500(this.element , this.transport , this.json);
				}
				else{
					_oParser._onFailure(this.element , this.transport , this.json);
				}
			}
			this.parent.callBack(_oParser , this.oldDatasource , this.oldEDatasource , this.oldJson , this.oldParams , this.oldSelection);
			delete this.transport;
			delete this.json;
			this.successFlag	= false;
			this.oldDatasource	= null;
			this.oldEDatasource	= null;
			this.oldJson		= null;
			this.oldParam		= null;
			return true;
		}
//		$log().debug(this.done+'/'+this.todo);
//		$log().debug(this.nodeName+':'+this.html);
		return false;
	},
	/**
	 * 内部数据源加载成功的回调函数
	 */
	_onSuccess : function(_oParser , _oScenes , _oTransport , _oJson){
		this.successFlag	= true;
		this.transport		= _oTransport;
		this.json			= _oJson;
		var bDone			= this.callBack(_oParser);
		if(!bDone){
			this._parseChilds(_oParser , _oScenes.oDataSource , _oScenes.eDataSource , _oJson , _oScenes.oParams , '');
		}
	},
	/**
	 * 内部数据源加载失败的回调函数
	 */
	_onFailure : function(_oParser , _oScenes , _oTransport , _oJson){
		this.successFlag	= false;
		this._500Flag		= false;
		this.transport		= _oTransport;
		this.json			= _oJson;
		this.done			= this.todo-1;
		this.callBack(_oParser);
	},
	/**
	 * 内部数据源加载出现500错误时的回调函数
	 */
	_on500 : function(_oParser , _oScenes , _oTransport , _oJson){
		this.successFlag	= false;
		this._500Flag		= true;
		this.transport		= _oTransport;
		this.json			= _oJson;
		this.done			= this.todo-1;
		this.callBack(_oParser);
	},
	parse:function(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		this._parseMe(_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection);
	},
	_parseMe : function (_oParser , _oDatasource , _eDatasource , _oJson , _oParams , _sSelection){
		var oElement		= this.element;
		var sDatonId		= oElement.id;
		if(sDatonId){
			sDatonId		= _oParser._fillValue(sDatonId , _oJson , _oParams ,_sSelection);
			var oDs			= new com.trs.ajaxframe.TrsAjax.Ds('DS',oElement,null);
			for( var i=0 ; i<this.childNodes.length ; i++){
				oDs.pushChild(com.trs.ajaxframe.TagHelper.copyTrsAjaxDom(this.childNodes[i] , oDs));
			}
			_oParser.pageTrsAjaxDoms[sDatonId]	= oDs;
		}
		if(!this.sAttrs){
			this.sAttrs	= com.trs.ajaxframe.TagHelper.getAttributes(oElement , this.nodeName);
		}
		var sAttrs		= this.sAttrs;
		sAttrs			= (sAttrs=='')?'':(' '+sAttrs);
		this.html		= '<SPAN'+sAttrs+'>#INNERHTML#</SPAN>'
		_oParser._bindEvents(oElement , false);
		_oParser._onCreate(oElement);
		var oDataSource		= _oParser.$datasource(oElement);
		oElement.setAttribute('currentPage',1);
		var oParams			=Object.extend({},_oParams);
		var scenes			= {
			oDataSource : oDataSource,
			eDataSource : oElement,
			oParams : oParams
		};
		this.oldJson		= _oJson;
		this.oldDataSource	= _oDatasource;
		this.oldEDataSource	= _eDatasource;
		this.oldParams		= _oParams;
		this.oldSelection	= _sSelection;
		var bAsyn	= oElement.getAttribute("asyn") || false;
		var caller	= this;
		var options = {
			onSuccess : function(transport, vJson){caller._onSuccess(_oParser , this , transport , vJson);}.bind(scenes),
			onFailure : function(transport, vJson){caller._onFailure(_oParser , this , transport , vJson);}.bind(scenes),
			on500 : function(transport, vJson){caller._on500(_oParser , this , transport , vJson);}.bind(scenes),
			asynchronous : bAsyn
		};

		var dsParams = oElement.getAttribute("params");
		dsParams = _oParser._fillValue(dsParams , _oJson , _oParams ,_sSelection);
		setTimeout(function(){
			var sMethod = oElement.getAttribute("method");
			sMethod = (sMethod) ? sMethod : 'GET';
			switch ( sMethod.toUpperCase() ) {
				case 'GET':
					oDataSource.get(dsParams , options);
					break;
				case 'FINDBYIDS':
					oDataSource.findByIds(dsParams , dsParams , options);
					break;
				default:
					var oPostData = null;
					var bSend = false;
					oDataSource.call(sMethod , oPostData , dsParams , bSend , options);
			}
		},1);
	}
});

/**
 * 置标解析的工具类
 */
var TagHelper = com.trs.ajaxframe.TagHelper	= {
	supportedTagNames : ['for','record'],
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
					args[i] = $parser.urlQuery['$'+sUnitExpression]||_ExtraParams['$'+sUnitExpression]||sDefaultValue;
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
			return $parser.urlQuery[cUnitType+sUnitExpression]||_ExtraParams[cUnitType+sUnitExpression]||sDefaultValue;
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
				return $parser.urlQuery[sUnitExpression]||_ExtraParams[sUnitExpression]||sDefaultValue;
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
					args[i] = $parser.urlQuery[sUnitExpression]||_ExtraParams[sUnitExpression]||sDefaultValue;
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
			if($(sBlankRef)){
				return $(sBlankRef).innerHTML;
			}
			var sBlankText = oAttributes['blanktext'];
			return sBlankText || '';
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
		var oValue	= TagHelper.valid(_sExpression,_oJson);
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
		var oArray	= TagHelper.valid(_sExpression,_oJson);
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
	},
	/**
	 * 取得当前置标的所有属性
	 * @param _eElement HTML DOM置标
	 * @param _sNodeName 尚未用到,可能某些特定置标需要特殊逻辑处理时用到
	 * @return attrs
	 */
	getAttributes : function(_eElement , _sNodeName){
		var attributes		= _eElement.attributes;
		var tmpAttrs		= {};
		if(!_IE){
			for(var j=0;j<attributes.length;j++){
				var sAttrName	= attributes[j].name;
				var sValue	= '';
				if('href'==sAttrName){
					sValue	= _eElement.getAttribute('href',2);
				}
				else{
					sValue	= attributes[j].value;
				}
				tmpAttrs[attributes[j].name]	= sValue;
			}
		}
		else{
			for(var j=0;j<attributes.length;j++){
				if(attributes[j].specified){
					var sAttrName	= attributes[j].name;
					var sValue	= '';
					if('href'==sAttrName){
						sValue	= _eElement.getAttribute('href',2);
					}
					else{
						sValue	= attributes[j].value;
					}
					tmpAttrs[sAttrName]	= sValue;
				}
			}
			attributes = null;
			var sValue = _eElement.getAttribute('value');
			if(sValue){
				tmpAttrs['value'] = sValue;
			}
		}
		var aReturn			= [];
		for(var sName in tmpAttrs){
			if(sName.toLowerCase()=='style'){
				aReturn.push(sName+'="'+_eElement.style.cssText+'"');
			}
			else if(sName.toLowerCase()=='_style'){
				aReturn.push('style="'+tmpAttrs[sName]+'"');
			}
			else if(sName.toLowerCase()=='class'){
				aReturn.push(sName+'="'+Element.classNames(_eElement)+'"');
			}
			else if(typeof tmpAttrs[sName]!='string'){
				$log().error('error occurs when getAttributes because the attribute value is not string:\n'+sName+':'+tmpAttrs[sName]);
			}
			else{
				aReturn.push(sName+'="'+tmpAttrs[sName].replace(/\"/g,'\'')+'"');
			}
		}
		return aReturn.join(' ');
	},
	/**
	 * 判断当前置标是否属于不需要结束的置标
	 * @param _sNodeName 当前置标名
	 * @return return true if it is not need end tag, else return false.
	 */
	isNoEndTag : function(_sNodeName){
		return ['br','img','hr','input','link'].include(_sNodeName.toLowerCase());
	},
	/**
	 * TrsAjaxDom对象的深度拷贝,用于InnerDs需要充当Ds时的场景
	 * @param _oTrsAjaxDom 被拷贝的对象
	 * @param _oParent 为返回值指定的parent对象
	 * @return 深度拷贝
	 */
	copyTrsAjaxDom : function(_oTrsAjaxDom,_oParent){
		var oRetTrsAjaxDom	= Object.extend({},_oTrsAjaxDom);
		oRetTrsAjaxDom.todo	= 1;
		oRetTrsAjaxDom.done	= 0;
		oRetTrsAjaxDom.parent	= _oParent;
		oRetTrsAjaxDom.nodeName	= _oTrsAjaxDom.nodeName;
		oRetTrsAjaxDom.element	= _oTrsAjaxDom.element;
		oRetTrsAjaxDom.childNodes	= [];
		for( var i=0 ; i<_oTrsAjaxDom.childNodes.length ; i++){
			var oldChild	= _oTrsAjaxDom.childNodes[i];
			var newChild	= com.trs.ajaxframe.TagHelper.copyTrsAjaxDom(oldChild,oRetTrsAjaxDom);
			oRetTrsAjaxDom.pushChild(newChild);
		}
		return oRetTrsAjaxDom;
	}
};
ClassName(com.trs.ajaxframe.TagHelper,'ajaxframe.TagHelper');