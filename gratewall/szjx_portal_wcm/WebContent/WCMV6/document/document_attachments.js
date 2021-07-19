/*!
 *	History				Who			What
 *	20060613			wenyh		添加文档图片附件的预览显示
 *
 */


	/*
		APPLINKALT	提示信息
		APPDESC		名称					
		APPFILE		文件
		APPSERN		序号
	*/
	function drawAppendixes(_nAppFlag, _elDiv){
		var arTemp = getAppendixArray(_nAppFlag);
		if(arTemp == null){
			CTRSAction_alert("Type["+_nAppFlag+"] InValid!");
			return;
		}

		var sFileDesc = "";
		switch(_nAppFlag){
		case FLAG_DOCPIC:
			sFileDesc = "文件名";
			break;
		case FLAG_DOCAPD:
			sFileDesc = "文件名";
			break;
		case FLAG_LINK:
			sFileDesc = "链接地址";
			break;
		}
	

		var sHTML = ""
		+"<TABLE width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"A6A6A6\">" 
		+"							<!--~-- ROW11 --~-->" 
		+"							<TR bgcolor=\"#BEE2FF\" class=\"list_th\">" 
		+"								<!--TD width=\"40\" height=\"20\" NOWRAP><a href=# onclick=\"javascript:TRSHTMLElement.selectAllByName('App"+_nAppFlag+"Ids');\">全选</a>序号</TD-->" 
		+"								<TD bgcolor=\"#BEE2FF\">"+sFileDesc+"</TD>";
		if(_nAppFlag != FLAG_LINK)
			sHTML += "					<TD bgcolor=\"#BEE2FF\">原文件名</TD>";
		sHTML = sHTML 
		+"								<TD bgcolor=\"#BEE2FF\">显示名称</TD>" 
		+"								<TD bgcolor=\"#BEE2FF\">提示信息</TD>" 
		+"								<TD bgcolor=\"#BEE2FF\">排序</TD>" 
		+"								<TD bgcolor=\"#BEE2FF\">操作</TD>" 
		+"							 </TR>" 
		+"							 <!--~ END ROW11 ~-->";

		var oTemp;
		var sAppDescEvent, sSerailNoEvent, sDelEvent, sSrcFileEvent, sAppAltEvent;
		for(var i=0; i<arTemp.length; i++){
			sSrcFileEvent = " onblur=\"updateProperty("+_nAppFlag+", "+i+", 'SrcFile', this.value, this);\" ";
			sAppDescEvent = " onblur=\"updateProperty("+_nAppFlag+", "+i+", 'AppDesc', this.value, this);\" ";
			sAppAltEvent = " onblur=\"updateProperty("+_nAppFlag+", "+i+", 'APPLINKALT', this.value, this);\" ";
			sSerailNoEvent = " onblur=\"updateProperty("+_nAppFlag+", "+i+", 'APPSERN', this.value, this);\" ";
			sDelEvent = " onclick=\"delAppendix("+_nAppFlag+", "+i+");\" ";

			oTemp = arTemp[i];
			sHTML += ""
			+"<TR class=\"list_tr\" onclick=\"TRSHTMLTr.onSelectedTR(this);\">" 
			+"	<!--TD width=\"40\" NOWRAP><INPUT TYPE=\"checkbox\" NAME=\"App"+_nAppFlag+"Ids\" VALUE=\""+oTemp.getProperty("AppFile")+"\">"+(i+1)+"</TD-->" 				
			+"	<TD><span onMouseOver=showPicAppendix(this," + _nAppFlag + ") onMouseOut=hidePicAppendix(this)>"+oTemp.getProperty("AppFile")+"</span></TD>";
			if(_nAppFlag != FLAG_LINK){
				sHTML = sHTML
			+"	<TD align=center>"
			+"<input size=10 "+sSrcFileEvent+" type=text name=\"App"+_nAppFlag+"SrcFile\" value=\""+oTemp.getProperty("SrcFile")+"\">"
			+"</TD>";
			}
			sHTML = sHTML
			+"	<TD align=center>"
			+"<input  "+sAppDescEvent+" size=20 type=text name=\"App"+_nAppFlag+"Desc\" value=\""+oTemp.getProperty("AppDesc")+"\">"
			+"</TD>"
			+"	<TD align=center>"
			+"<input "+sAppAltEvent+" type=text name=\"App"+_nAppFlag+"LinkAlt\" value=\""+oTemp.getProperty("AppLinkAlt")+"\">"
			+"</TD>"
			+"	<TD align=center>"
			+"<input "+sSerailNoEvent+" type=text size=1 name=\"App"+_nAppFlag+"Sern\" value=\""+oTemp.getProperty("AppSern")+"\">"
			+"</TD>"
			+"	<TD align=\"center\" nowrap><a href=\"#\" "+sDelEvent+">删除</a></TD>" 
			+"</TR>" 
			+"";
		}

		sHTML += "</TABLE>";
		
		if(_elDiv == null)
			document.write(sHTML);
		else
			_elDiv.innerHTML = sHTML;
	}
	
	//wenyh@20060613,添加文档图片附件的显示函数,方便预览
	function showPicAppendix(picAppendixSpan,nAppFlag){
		if(nAppFlag != FLAG_DOCPIC) return;//wenyh@20060809,如果不是图片附件,则直接返回
		var picName = picAppendixSpan.innerText;
		var picDiv = $(picName);
		if(!picDiv){
			picDiv = document.createElement("div");
			picDiv.id= picName;
			var sHtml = "<span style=''><image src='../file/read_image.jsp?FileName="+picName+"'></span>";
			picDiv.innerHTML = sHtml;
			picDiv.style.position="absolute";
			var offsets = Position.cumulativeOffset(picAppendixSpan);
			picDiv.style.top = offsets[1]  + 'px';
			picDiv.style.left = offsets[0] + 200 + 'px';		
			document.body.appendChild(picDiv);
		}else{
			picDiv.style.display = "inline";
		}
	}
	
	function hidePicAppendix(picAppendixSpan){
		var picDiv = $(picAppendixSpan.innerText);
		if(picDiv){
			picDiv.style.display = "none";
		}
	}

	//获取指定类型附件的Array
	function getAppendixArray(_nAppFlag){
		switch(_nAppFlag){
		case FLAG_DOCPIC:
			return m_arPics;				
		case FLAG_DOCAPD:
			return m_arFiles;
		case FLAG_LINK:
			return m_arLinks;	
		case FLAG_ALL:
			var arTemp = new Array();
			arTemp = arTemp.concat(m_arPics, m_arFiles, m_arLinks);
			return arTemp;			
		}
	}

	function getIndex(_nAppFlag){
		switch(_nAppFlag){
		case FLAG_DOCPIC:
			return 0;				
		case FLAG_DOCAPD:
			return 1;
			break;
		case FLAG_LINK:
			return 2;				
		}
	}

	//获取最大序号
	function getMaxAppSern(_nAppFlag){
		var arTemp = getAppendixArray(_nAppFlag);			
		if(arTemp == null){
			CTRSAction_alert("Type["+_nAppFlag+"] InValid!");
			return;
		}

		var max = Math.max;
		var nMaxSern = 0;
		for(var i=0; i<arTemp.length; i++){
			nMaxSern = max(nMaxSern, arTemp[i].getProperty("AppSern"));
		}
		return nMaxSern;
	}

	function clearValue(_oElement){
		if(_oElement != null) _oElement.value = "";
	}

	//验证参数有效性
	function validProperty(_sProperty, _sValue, _oElement){
		var sValue = _sValue || "";
		var sProperty = (_sProperty || "").toUpperCase();
		switch(sProperty){
		case "SRCFILE":
			if(sValue.length > 200){
				CTRSAction_alert("原文件名超过最大长度[200]！");
				clearValue(_oElement);
				return false;
			}
			return true;
		case "APPDESC":
			if(sValue.length > 255){
				CTRSAction_alert("显示名称超过最大长度[255]！");
				clearValue(_oElement);
				return false;
			}
			return true;
		case "APPLINKALT":
			if(sValue.length > 255){
				CTRSAction_alert("提示信息超过最大长度[255]！");
				clearValue(_oElement);
				return false;
			}
			return true;
		case "APPSERN":
			if(isNaN(sValue)){
				CTRSAction_alert("附件排序值不是一个数字！");
				clearValue(_oElement);
				return false;
			}
			if(sValue.length > 4){
				CTRSAction_alert("附件排序值超过最大长度[4]！");
				clearValue(_oElement);
				return false;
			}
			return true;
		default:
			CTRSAction_alert("传入无效的属性名！");
			return false;
		}
	}

	//更新指定类型指定附件的属性
	function updateProperty(_nAppFlag, _nIndex,  _sProperty, _sValue, _oElement){
		if(!validProperty(_sProperty, _sValue, _oElement)) return;
		var arTemp = getAppendixArray(_nAppFlag);			
		arTemp[_nIndex].setProperty(_sProperty, _sValue);			
	}

	//删除指定的附件
	function delAppendix(_nAppFlag, _nIndex){
		//删除数据
		var arTemp = getAppendixArray(_nAppFlag);	
		if(arTemp == null){
			CTRSAction_alert("Type["+_nAppFlag+"] InValid!");
			return;
		}
		TRSArray.splice(arTemp, _nIndex, 1);

		//刷新页面
		refresh(_nAppFlag);			
	}		

	//添加指定的附件
	function addAppendix(_nAppFlag, _sAppFile, _sAppDesc, _sAppLinkAlt, _sSrcFile){
		var arTemp = getAppendixArray(_nAppFlag);			
		if(arTemp == null){
			CTRSAction_alert("addAppendix:Type["+_nAppFlag+"] InValid!");
			return;
		}
		var sSrcFile = _sSrcFile || "";

		var oAppendix = new CWCMObj();
		oAppendix.setProperty("AppFile", _sAppFile);
		oAppendix.setProperty("SrcFile", sSrcFile);
		oAppendix.setProperty("AppFlag", _nAppFlag);
		oAppendix.setProperty("AppDesc", _sAppDesc);
		oAppendix.setProperty("AppLinkAlt", _sAppLinkAlt);
		oAppendix.setProperty("AppSern", getMaxAppSern(_nAppFlag)+1);

		arTemp[arTemp.length] = oAppendix;

		//刷新页面
		refresh(_nAppFlag);			
	}

	//刷新指定类型附件的页面
	function refresh(_nAppFlag){
		var elDiv = document.all("id_TRSSimpleTab"+getIndex(_nAppFlag));

		//刷新显示
		drawAppendixes(_nAppFlag, elDiv);
	}
	
	function getFlag(){
		switch(TRSSimpleTab.nCurrIndex){
			case 0:
				return FLAG_DOCPIC;
			case 1:
				return FLAG_DOCAPD;
			case 2:
				return FLAG_LINK;
			default:
				return 0;
		}

	}
	

	//上传组件所需要实现的Interface
	function addFile(_sFile, _sSrcFile){
		addAppendix(getFlag(), _sFile, _sSrcFile, "", _sSrcFile);
	}

	//Appendix数据转换为一个数组
	function toStringArray(_nAppFlag){
		var arTemp = getAppendixArray(_nAppFlag);			
		if(arTemp == null){
			CTRSAction_alert("toStringArray:Type["+_nAppFlag+"] InValid!");
			return;
		}
		return WCMObjHelper.toXMLString(arTemp, "WCMAppendixes", "WCMAppendix") ;			
	}

	

	//确定操作
	function onOK(){
		var bNeedDowithAppendix = false;
		var arReturnValue = [toStringArray(FLAG_ALL), bNeedDowithAppendix];
		window.returnValue = arReturnValue;
		window.close();
	}
