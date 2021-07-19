function changeStyleTab(_eAObj,_nType,_nPageStyleId){
	_nType= _nType || 0;
	_nPageStyleId = _nPageStyleId || 0;

	//根据不同参数，构造不同的iframe地址
	var sIframeUrl = "./style_name_addedit.jsp";
	if(_nType==1){
		//基本特性
		sIframeUrl = "./style_basic_info_addedit.jsp";
	}else if(_nType==2){
		//资源可用风格
		sIframeUrl = "./resourcestyle_list.html";
	}else if(_nType==3){
		//内容可用风格
		sIframeUrl = "./contentstyle_list.html";
	}else if(_nType==4){
		//自定义
		sIframeUrl = "./style_customize.jsp";
	}else if(_nType==5){
		//自定义
		sIframeUrl = "./style_other_dom_addedit.jsp";
	}

	//拼接参数
	sIframeUrl += "?PageStyleId=" +_nPageStyleId;
	
	var eIframe = document.getElementById("iframe_trswidget_list");
	eIframe.src = sIframeUrl;

	//修改样式
	changeLastClickA(_eAObj);
}

//修改上次单击的元素的样式
function changeLastClickA(_eAObj){
	//去掉上次单击的元素样式
	if(M_LastClick_A){
		if(Element.hasClassName(M_LastClick_A,"a_selected")){
			Element.removeClassName(M_LastClick_A,"a_selected");
		}
	}
	
	//添加当前元素的样式
	if(!Element.hasClassName(_eAObj,"a_selected")){
		Element.addClassName(_eAObj,"a_selected");
	}

	//记录标记
	M_LastClick_A = _eAObj;
}

