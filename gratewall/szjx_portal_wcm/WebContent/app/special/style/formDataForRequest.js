//从表单中拼接页面风格基本属性的xml字符串
function getStyleXML(_oForm){
	if(!_oForm){
		Ext.Msg.alert("传入的表单不存在!");
	}
	//记录radio
	var radioJson = {};
	var styleItemList = [];//存放styleItem属性列表
	for(var i=0;i<_oForm.elements.length;i++){
		var eCurrElement = _oForm.elements[i];
		if(!eCurrElement.getAttribute("name") || !eCurrElement.getAttribute("ParamType") || eCurrElement.tagName=="button"){
			continue;
		}
		var currParamType = eCurrElement.getAttribute("ParamType") || "";
		if(eCurrElement.tagName=="INPUT" && eCurrElement.type=="checkbox"){
			//checkbox
			if(currParamType=="Style"){
				 continue;
			}else if(currParamType=="StyleItem"){
				var eCheckBoxName = eCurrElement.getAttribute("name");
				//是否选中
				var sCheckBoxValue = eCurrElement.value;
				if(eCurrElement.checked){
					var nObjectId = eCurrElement.getAttribute("StyleItemId") || 0;
					var sTemp = "<OBJECT ID=\"" + nObjectId + "\" ClassName=\"" + eCurrElement.getAttribute("name") + "\" ClassValue=\"" + sCheckBoxValue + "\"/>";
					styleItemList.push(sTemp);
				}else if(eCheckBoxName.indexOf("text_decoration")>-1){// 当前的checkbox是下划线时，未选中则使其值为none
					sCheckBoxValue = "none";
					var nObjectId = eCurrElement.getAttribute("StyleItemId") || 0;
					var sTemp = "<OBJECT ID=\"" + nObjectId + "\" ClassName=\"" + eCurrElement.getAttribute("name") + "\" ClassValue=\"" + sCheckBoxValue + "\"/>";
					styleItemList.push(sTemp);
				}
			}
		}else if(eCurrElement.tagName=="INPUT" && eCurrElement.type=="radio"){
			//radio
			//是否已经添加过
			var sHasAppenRadio = radioJson[eCurrElement.getAttribute("name")] || "";
			if(sHasAppenRadio!=""){
				//已经添加过
				continue;
			}
			
			if(currParamType=="Style"){
				 continue;
			}else if(currParamType=="StyleItem"){
				//是否选中
				if(eCurrElement.checked ==true){
					var nObjectId = eCurrElement.getAttribute("StyleItemId") || 0;
					var sTemp = "<OBJECT ID=\"" + nObjectId + "\" ClassName=\"" + eCurrElement.getAttribute("name") + "\" ClassValue=\"" + eCurrElement.value + "\"/>";
					styleItemList.push(sTemp);
					radioJson[eCurrElement.name] = eCurrElement.value; //记录此radio已经追加过
				}
			}
		}else{
			//其他
			if(currParamType=="Style") continue;
			else if(currParamType=="StyleItem"){
				//StyleItem属性
				var nObjectId = eCurrElement.getAttribute("StyleItemId") || 0;
				var sTemp = "<OBJECT ID=\"" + nObjectId + "\" ClassName=\"" + eCurrElement.getAttribute("name") + "\" ClassValue=\"" + eCurrElement.value + "\"/>";
				styleItemList.push(sTemp);
			}
		}//end else
	}//end for
	return "<OBJECTS>" + styleItemList.join("") + "</OBJECTS>";
}//end function

function getPostStyleJson(_oForm){
	if(!_oForm){
		Ext.Msg.alert("传入的表单不存在!");
	}
	//记录radio
	var radioJson = {};
	var styleList = {};//存放style属性
	for(var i=0;i<_oForm.elements.length;i++){
		var eCurrElement = _oForm.elements[i];
		if(!eCurrElement.getAttribute("name") || !eCurrElement.getAttribute("ParamType") || eCurrElement.tagName=="button"){
			continue;
		}
		var currParamType = eCurrElement.getAttribute("ParamType") || "";
		if(eCurrElement.tagName=="INPUT" && eCurrElement.type=="checkbox"){
			//checkbox
			if(currParamType=="StyleItem")continue;
			else if(currParamType=="Style"){
				if(eCurrElement.checked){
					//是否选中
					styleList[eCurrElement.getAttribute("name")] = eCurrElement.value; 
				}
			}
		}else if(eCurrElement.tagName=="INPUT" && eCurrElement.type=="radio"){
			//radio
			//是否已经添加过
			var sHasAppenRadio = radioJson[eCurrElement.getAttribute("name")] || "";
			if(sHasAppenRadio!=""){
				//已经添加过
				continue;
			}
			if(currParamType=="StyleItem")continue;
			else if(currParamType=="Style"){
				//是否选中
				if(eCurrElement.checked ==true){
					styleList[eCurrElement.getAttribute("name")] = eCurrElement.value;
					radioJson[eCurrElement.getAttribute("name")] = eCurrElement.value; //记录此radio已经追加过
				}
			}
		}else{
			//其他
			if(currParamType=="StyleItem")continue;
			else if(currParamType=="Style"){
				//PageStyle属性
				styleList[eCurrElement.getAttribute("name")] = eCurrElement.value;
			}
		}//end else
	}//end for
	return styleList;
}