var borderStyleSelector = {
	eSel : null,
	eSelectorValueDom : null,
	eStyleListDom : null,
	eListMaseIframe : null,
	init : function (_sSelId,_sSelectorValueDomId,_sStyleListDomId,_sListMaskIframeId){
		this.eSel = document.getElementById(_sSelId);
		this.eSelectorValueDom = document.getElementById(_sSelectorValueDomId);
		this.eStyleListDom = document.getElementById(_sStyleListDomId);
		this.eListMaseIframe = document.getElementById(_sListMaskIframeId);
	},
	showStyleList : function (_sSelId,_sSelectorValueDomId,_sStyleListDomId,_sListMaskIframeId){
		this.init(_sSelId,_sSelectorValueDomId,_sStyleListDomId,_sListMaskIframeId);
		// 将 选择器 入口select的disabled设置为true,使其不出现下拉框
		this.eSel.disabled=true;
		var selOffsets = Position.cumulativeOffset(this.eSel);
		this.eStyleListDom.style.display="";
		var nStyleListDomLeft = selOffsets[0];
		var eStyleListDomRight = parseInt(nStyleListDomLeft) + parseInt(this.eStyleListDom.offsetWidth);
		if(eStyleListDomRight>document.body.offsetWidth){
			nStyleListDomLeft = (parseInt(document.body.offsetWidth) - parseInt(this.eStyleListDom.offsetWidth) );
		}
		this.eStyleListDom.style.left = nStyleListDomLeft + 'px';

		var nStyleListDomTop = selOffsets[1]+this.eSel.offsetHeight;
		var eStyleListDomBottom = parseInt(nStyleListDomTop) + parseInt(this.eStyleListDom.offsetHeight);
		if(eStyleListDomBottom>document.body.offsetHeight){
			nStyleListDomTop = (parseInt(document.body.offsetHeight) - parseInt(this.eStyleListDom.offsetHeight) );
		}
		this.eStyleListDom.style.top = nStyleListDomTop + 'px';

		this.eListMaseIframe.style.left = nStyleListDomLeft  + 'px';
		this.eListMaseIframe.style.top = nStyleListDomTop  + 'px';
		this.eListMaseIframe.style.display = "";
		this.eListMaseIframe.width = this.eStyleListDom.offsetWidth  + 'px';
		this.eListMaseIframe.height = this.eStyleListDom.offsetHeight  + 'px';
		this.eStyleListDom.focus();

		// 将 选择器 入口select的disabled还原为false，使其可以正常使用
		this.eSel.disabled = false;
	},
	hideStyleList : function (){
		this.eStyleListDom.style.display = "none";
		this.eListMaseIframe.style.display = "none";
	},
	selectStyle : function (_eDiv){
		var style = _eDiv.getElementsByTagName("span")[0].getAttribute("value");
		var eStyleOpt = this.eSel.options[0];
		eStyleOpt.value = style;
		eStyleOpt.selected = true;
		
		this.eSelectorValueDom.innerHTML = _eDiv.innerHTML;
		this.hideStyleList();
	},
	initSelected : function (_sSelectorValueDomId,_defSelected){
		if(_defSelected==""){
			document.getElementById(_sSelectorValueDomId).innerHTML = '<span class="border_style_opt" value="" style="line-height:18px;font-size:13px;color:#000000;padding-left:3px;padding-top:0px;">请选择</span>';
		}else if(_defSelected!="none"){
			document.getElementById(_sSelectorValueDomId).innerHTML = '<span class="border_style_opt" style="border-bottom-style:'+_defSelected+';" value="'+_defSelected+'"></span>';
		}else{
			document.getElementById(_sSelectorValueDomId).innerHTML = '<span style="width:100%;height:10px;border-bottom-style:none;text-align:center;" value="none">无</span>';
		}
	}
}