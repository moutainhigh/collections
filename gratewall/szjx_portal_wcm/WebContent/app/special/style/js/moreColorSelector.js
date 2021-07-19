var oMoreColorPopup = window;
/***********--archer--******--begin*************/
	var SelRGB = '#000000';
	var DrRGB = '';
	var SelGRAY = '120';
	var hexch = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F');
	// 声明一个全局对象Namespace，用来注册命名空间
	Namespace = new Object();
	// 全局对象仅仅存在register函数，参数为名称空间全路径，如"Grandsoft.GEA"
	Namespace.register = function(fullNS){
		// 将命名空间切成N部分, 比如Grandsoft、GEA等
		var nsArray = fullNS.split('.');
		var sEval = "";
		var sNS = "";   for (var i = 0; i < nsArray.length; i++){
			if (i != 0) sNS += ".";
			sNS += nsArray[i];
			// 依次创建构造命名空间对象（假如不存在的话）的语句
			// 比如先创建Grandsoft，然后创建Grandsoft.GEA，依次下去
			sEval += "if (typeof(" + sNS + ") == 'undefined') " + sNS + " = new Object();"
		}
		if (sEval != "") eval(sEval);
	}
	// 注册命名空间Grandsoft.GEA, Grandsoft.GCM
	Namespace.register("SelectColor");
	SelectColor.Obj = function(){}
	SelectColor.Obj.prototype = {
		getWindow : function (){
			return oMoreColorPopup;
		},
		getRGB  :function (){    return this.getWindow().document.getElementById("RGB");},
		getGRAY :function (){    return this.getWindow().document.getElementById("GRAY");},
		getGrayTable    :function (){    return this.getWindow().document.getElementById("GrayTable");},
		getSelColor     :function (){    return this.getWindow().document.getElementById("SelColor");},
		getShowColor    :function (){    return this.getWindow().document.getElementById("ShowColor");}
	}
	var colorObj = new SelectColor.Obj;
	//设置颜色
	SelectColor.setColor = function (color){
		if(color==""){
			color = "#000000";
		}
		colorObj.getGRAY().innerHTML = "120";
		colorObj.getRGB().innerHTML = color;
		SelRGB = color;
		this.EndColor();
	}
	SelectColor.ToHex = function (n){
		if(n>=0&&n<=255){
			var h, l;
			n = Math.round(n);
			l = n % 16;
			h = Math.floor((n / 16)) % 16;
			return (hexch[h] + hexch[l]);
		}else{
			return 00;
		}
	}
	SelectColor.DoColor = function (c, l){
		var r, g, b;
		r = '0x' + c.substring(1, 3);
		g = '0x' + c.substring(3, 5);
		b = '0x' + c.substring(5, 7);

		if(l > 120){
			l = l - 120;
			r = (r * (120 - l) + 255 * l) / 120;
			g = (g * (120 - l) + 255 * l) / 120;
			b = (b * (120 - l) + 255 * l) / 120;
		}else{
			r = (r * l) / 120;
			g = (g * l) / 120;
			b = (b * l) / 120;
		}
		return '#' + this.ToHex(r) + this.ToHex(g) + this.ToHex(b);
	}
	SelectColor.EndColor = function (){
		var i;
		var reg = "^#[A-Fa-f0-9]{6}$";
		if(DrRGB != SelRGB){
			DrRGB = SelRGB;
			if(DrRGB.match(reg)){
				for(i = 0; i <= 30; i ++){
					colorObj.getGrayTable().rows[i].style.backgroundColor = this.DoColor(SelRGB, 240 - i * 8);
				}
			} else {
				alert("您输入的颜色代码["+ DrRGB +"]格式不正确");
				return false;
			}
			
		}
		
		colorObj.getSelColor().value = this.DoColor(colorObj.getRGB().innerHTML, colorObj.getGRAY().innerHTML );
		if(colorObj.getSelColor().value.match(reg)){
			colorObj.getShowColor().style.backgroundColor = colorObj.getSelColor().value;
		} else {
			colorObj.getShowColor().style.backgroundColor="#000000";
		}
	}
	SelectColor.GetFirefoxColorValue = function (c){
		var r, g, b;
		var ch = c.substring(4, c.length -1);
		var chs = new Array();
		chs = ch.split(",");
		r = chs [0];//'0x' + ch.substring(1, 3);
		g = chs [1];//'0x' + ch.substring(3, 5);
		b = chs [2];//'0x' + ch.substring(5, 7);
		return '#' + this.ToHex(r) + this.ToHex(g) + this.ToHex(b);
	}
	//事件
	SelectColor.clickColorTable = function (event){
		if(event.srcElement!=null ){
			eventObj = event.srcElement;
			var currColor = eventObj.style.backgroundColor.toUpperCase();
			//ie9兼容
			if(currColor.indexOf('RGB(') >= 0){
				SelRGB = this.GetFirefoxColorValue(currColor);
			}else{
				SelRGB = currColor;
			}
		}else{
			eventObj = event.target
			SelRGB = this.GetFirefoxColorValue(eventObj.style.backgroundColor.toUpperCase());
		}
		this.EndColor();
	}
	SelectColor.mouseoverColorTable = function (event){
		if(event.srcElement!=null ){
			eventObj = event.srcElement;
			var currColor = eventObj.style.backgroundColor.toUpperCase();
			//兼容ie9
			if(currColor.indexOf('RGB(') >= 0){
				colorObj.getRGB().innerHTML = this.GetFirefoxColorValue(currColor);
			}else{
				colorObj.getRGB().innerHTML = currColor;
			}
		}else{
			eventObj = event.target
			colorObj.getRGB().innerHTML = this.GetFirefoxColorValue(eventObj.style.backgroundColor.toUpperCase());
		}
		this.EndColor();
	}
	SelectColor.mouseoutColorTable = function (event){
		colorObj.getRGB().innerHTML = SelRGB;
		this.EndColor();
	}
	SelectColor.clickGrayTable = function (event){
		var eventObj;
		if(event.srcElement!=null ){
			eventObj = event.srcElement;
		}else{
			eventObj = event.target
		}
		SelGRAY = eventObj.title;
		this.EndColor();
	}
	SelectColor.mouseoverGrayTable = function (event){
		var eventObj;
		if(event.srcElement!=null ){
			eventObj = event.srcElement;
		}else{
			eventObj = event.target
		}
		colorObj.getGRAY().innerHTML = eventObj.title;
		this.EndColor();
	}
	SelectColor.mouseoutGrayTable = function (event){
		colorObj.getGRAY().innerHTML = SelGRAY;
		this.EndColor();
	}
	function wc(r, g, b, n){
		var sHTML = ""
		r = ((r * 16 + r) * 3 * (15 - n) + 0x80 * n) / 15;
		g = ((g * 16 + g) * 3 * (15 - n) + 0x80 * n) / 15;
		b = ((b * 16 + b) * 3 * (15 - n) + 0x80 * n) / 15;
		sHTML +='<td unselectable="on" style="background-color:#' + SelectColor.ToHex(r) + SelectColor.ToHex(g) + SelectColor.ToHex(b) + '; height:8px; width:8px;"></td>';
		return sHTML;
	}
/***********--archer--*******--end--************/
var colorSelector = {
	eSel : null,
	eColorSelectorDiv : null,
	eMoreColorSelectorDiv : null,
	eMaskIframe : null,
	defColor : "",
	init : function (_eSel,_sColorSelectorDivId,_sMoreColorSelectorDivId,_sMaskIframeId,_afterColorSelectedFn){
		var eSel = _eSel;
		var sColorSelectorDivId =_sColorSelectorDivId;
		var sMoreColorSelectorDivId = _sMoreColorSelectorDivId;
		var sMaskIframeId = _sMaskIframeId;

		this.eSel = eSel;
		this.eColorSelectorDiv = document.getElementById(sColorSelectorDivId);
		this.eMoreColorSelectorDiv = document.getElementById(sMoreColorSelectorDivId);
		this.eMaskIframe = document.getElementById(sMaskIframeId);
		this.defColor = eSel.value;
		this.afterColorSelected = _afterColorSelectedFn||function() {};
	},
	// 显示颜色选择器
	showColorSeletor : function (_eSel,_sColorSelectorDivId,_sMoreColorSelectorDivId,_sMaskIframeId,_afterColorSelectedFn){
		// 初始化颜色选择器需要的一些容器
		this.init(_eSel,_sColorSelectorDivId,_sMoreColorSelectorDivId,_sMaskIframeId,_afterColorSelectedFn);
		// 将颜色选择器入口select的disabled设置为true,使其不出现下拉框
		this.eSel.disabled = true;
		var sColorSelectorHTML = this.drawColorSelector();
		this.eColorSelectorDiv.innerHTML=sColorSelectorHTML;
		// 设置颜色选择器出现的位置，使用了portotype中的方法Position.cumulativeOffset()
		var selOffsets = Position.cumulativeOffset(this.eSel);
		this.eColorSelectorDiv.style.display="";
		var nColorSelectorDivLeft = selOffsets[0];
		var nColorSelectorDivRight = parseInt(nColorSelectorDivLeft) + parseInt(this.eColorSelectorDiv.offsetWidth);
		if(nColorSelectorDivRight>document.body.offsetWidth){
			nColorSelectorDivLeft = ( parseInt(document.body.offsetWidth )- parseInt(this.eColorSelectorDiv.offsetWidth) )
		}
		this.eColorSelectorDiv.style.left = nColorSelectorDivLeft + 'px';
		

		var nColorSelectorDivTop = selOffsets[1]+this.eSel.offsetHeight;
		var nColorSelectorDivBottom = parseInt(nColorSelectorDivTop) + parseInt(this.eColorSelectorDiv.offsetHeight);
		if(nColorSelectorDivBottom>document.body.offsetHeight){
			nColorSelectorDivTop = ( parseInt(document.body.offsetHeight )- parseInt(this.eColorSelectorDiv.offsetHeight) ) 
		}
		this.eColorSelectorDiv.style.top = nColorSelectorDivTop + 'px';
		this.eColorSelectorDiv.style.display="";
		this.eMaskIframe.style.left = nColorSelectorDivLeft + 'px';
		this.eMaskIframe.style.top = nColorSelectorDivTop + 'px';
		this.eMaskIframe.style.display = "";
		this.eMaskIframe.width = this.eColorSelectorDiv.offsetWidth  + 'px';
		this.eMaskIframe.height = this.eColorSelectorDiv.offsetHeight  + 'px';
		// 将颜色选择器入口select的disabled还原为false，使其可以正常使用
		this.eSel.disabled = false;
		var el = this.eColorSelectorDiv.focus();
		Event.observe(document.body, 'mousedown',hideFun); 
	},

	// 隐藏颜色选择器
	hideColorSelector : function (){
		this.eColorSelectorDiv.style.display = "none";
		Event.stopObserving(document.body, 'mousedown', hideFun);
	},
	colorTdMouseout : function (_eColorTd){
		var eColorTd = _eColorTd;
		eColorTd.style.borderColor="";
		eColorTd.bgColor="";
	},
	colorTdMouseover : function (_eColorTd){
		var eColorTd = _eColorTd;
		eColorTd.style.borderColor="#0A66EE";
		eColorTd.bgColor="#EEEEEE";
	},
	colorTdMousedown : function (_color){
		var color = _color;
		// 更新当前颜色
		var eCurrColorSpan = this.eColorSelectorDiv.getElementsByTagName("span")[0];
		eCurrColorSpan.firstChild.style.backgroundColor = color;
		// 更新select上的表现
		this.changeSelOptColor(color);
		// 隐藏颜色选择器
		this.hideColorSelector();
		// 隐藏颜色选择器遮布
		this.eMaskIframe.style.display = "none";
	},
	// 更新select上的表现
	changeSelOptColor : function (_color){
		var color = _color;
		var eColorOpt = this.eSel.options[0];
		eColorOpt.style.backgroundColor = color;
		eColorOpt.value = color;
		eColorOpt.innerHTML = "&nbsp;";
		eColorOpt.selected = true;
		this.eSel.style.backgroundColor = color;
		this.afterColorSelected();
	},
	// 绘制颜色选择器的内容
	drawColorSelector : function (){
		var colorlist=new Array(40);
		colorlist[0]="#000000"; colorlist[1]="#993300"; colorlist[2]="#333300"; colorlist[3]="#003300";
		colorlist[4]="#003366"; colorlist[5]="#000080"; colorlist[6]="#333399"; colorlist[7]="#333333";
		colorlist[8]="#800000"; colorlist[9]="#FF6600"; colorlist[10]="#808000";colorlist[11]="#008000";
		colorlist[12]="#008080";colorlist[13]="#0000FF";colorlist[14]="#666699";colorlist[15]="#808080";
		colorlist[16]="#FF0000";colorlist[17]="#FF9900";colorlist[18]="#99CC00";colorlist[19]="#339966";
		colorlist[20]="#33CCCC";colorlist[21]="#3366FF";colorlist[22]="#800080";colorlist[23]="#999999";
		colorlist[24]="#FF00FF";colorlist[25]="#FFCC00";colorlist[26]="#FFFF00";colorlist[27]="#00FF00";
		colorlist[28]="#00FFFF";colorlist[29]="#00CCFF";colorlist[30]="#993366";colorlist[31]="#CCCCCC";
		colorlist[32]="#FF99CC";colorlist[33]="#FFCC99";colorlist[34]="#FFFF99";colorlist[35]="#CCFFCC";
		colorlist[36]="#CCFFFF";colorlist[37]="#99CCFF";colorlist[38]="#CC99FF";colorlist[39]="#FFFFFF";

		var sColorHtml = "";
		sColorHtml += "<table CELLPADDING=0 CELLSPACING=3 unselectable='on'>";
		sColorHtml += "<tr height=\"20\" unselectable='on' width=\"20\"><td align=\"center\"><span unselectable='on'><table style=\"border:1px solid #808080;background-color:"+this.defColor+" !important;\" width=\"12\" height=\"12\" bgcolor=\""+this.defColor+"\"><tr><td></td></tr></table></span></td><td bgcolor=\"eeeeee\" colspan=\"7\" style=\"font-size:12px;\" align=\"center\">当前颜色</td></tr>";
		for(var i=0;i<colorlist.length;i++){
			if(i%8==0)
			sColorHtml += "<tr unselectable='on'>";
			sColorHtml += "<td width=\"14\" unselectable='on' height=\"16\" style=\"border:1px solid;\" onMouseOut=\"colorSelector.colorTdMouseout(this);\" onMouseOver=\"colorSelector.colorTdMouseover(this);\" onMouseDown=\"colorSelector.colorTdMousedown('"+colorlist[i]+"')\" align=\"center\" valign=\"middle\"><table style=\"border:1px solid #808080;background-color:"+colorlist[i]+" !important;\" width=\"12\" height=\"12\" bgcolor=\""+colorlist[i]+"\"><tr><td></td></tr></table></td>";
			if(i%8==7)
			sColorHtml += "</tr>";
		}
		sColorHtml += "<tr unselectable='on'><td align=\"center\" unselectable='on' height=\"22\" colspan=\"8\" onMouseOut=\"colorSelector.colorTdMouseout(this);\" onMouseOver=\"colorSelector.colorTdMouseover(this);\" style=\"border:1px solid;font-size:12px;cursor:default;\" onMouseDown=\"Event.stopObserving(document.body, 'mousedown', hideFun);colorSelector.showMoreColorSelector();\" >其它颜色...</td></tr>";
		sColorHtml += "</table>";
		return sColorHtml;
	},
	hideMoreColorSelector : function (){
		this.eMoreColorSelectorDiv.style.display = "none";
		this.eMaskIframe.style.display = "none";
	},
	showMoreColorSelector : function (){
		// 隐藏颜色选择器
		this.eColorSelectorDiv.style.display = "none";
		// 显示更多颜色选择器
		var sMoreColorHTML = this.drawMoreColorSelector();
		this.eMoreColorSelectorDiv.innerHTML=sMoreColorHTML;
		// 设置颜色选择器出现的位置，使用了portotype中的方法Position.cumulativeOffset()
		var selOffsets = Position.cumulativeOffset(this.eSel);
		nMoreColorSelectorDivLeft = selOffsets[0];
		var nMoreColorSelectorDivTop = selOffsets[1]-100;
		this.eMoreColorSelectorDiv.style.display="";
		var nMoreColorSelectorDivRight = parseInt(nMoreColorSelectorDivLeft) + parseInt(this.eMoreColorSelectorDiv.offsetWidth);
		if(nMoreColorSelectorDivRight>document.body.offsetWidth){
			nMoreColorSelectorDivLeft = ( parseInt(document.body.offsetWidth )- parseInt(this.eMoreColorSelectorDiv.offsetWidth) );
		}
		this.eMoreColorSelectorDiv.style.left = nMoreColorSelectorDivLeft + "px";

		var nMoreColorSelectorDivBottom = parseInt(nMoreColorSelectorDivTop) + parseInt(this.eMoreColorSelectorDiv.offsetHeight);
		if(nMoreColorSelectorDivBottom>document.body.offsetHeight){
			nMoreColorSelectorDivTop = ( parseInt(document.body.offsetHeight )- parseInt(this.eMoreColorSelectorDiv.offsetHeight) );
		}
		this.eMoreColorSelectorDiv.style.top = nMoreColorSelectorDivTop  + "px";
		this.eMaskIframe.style.display = "";
		this.eMaskIframe.style.left = nMoreColorSelectorDivLeft  + "px";
		this.eMaskIframe.style.top = nMoreColorSelectorDivTop  + "px";
		this.eMaskIframe.width = this.eMoreColorSelectorDiv.offsetWidth  + "px";
		this.eMaskIframe.height = this.eMoreColorSelectorDiv.offsetHeight  + "px";
		this.eMoreColorSelectorDiv.focus();
		// 设置默认颜色
		SelectColor.setColor(this.defColor);
	},
	drawMoreColorSelector : function(){
		var sBodyHTML = "<center unselectable='on'>"+
			"<table unselectable='on' style='border: 0;' cellspacing='10' cellpadding='0'>"+
				"<tr unselectable='on'><td unselectable='on' id='ColorTd'>"+this.drawColorTable()+"</td><td unselectable='on' id='GrayTd'>"+this.drawGrayTable()+"</td></tr>"+
				"<tr unselectable='on'><td unselectable='on' colspan=2 align='center'>"+
					"<table unselectable='on' border=0 cellspacing=0 cellpadding=0 width='280'>"+
					"<tr unselectable='on'>"+
						"<td unselectable='on' valign='top' width='80px' align='center'>"+
							"<div unselectable='on' class='text'>预览</div>"+
							"<div unselectable='on' id='ShowColor' style='background-color:#000000;border:1;height:40px;width:50px;'></div>"+
						"</td>"+
						"<td unselectable='on' valign='top' width='120px' align='center'>"+
							"<table unselectable='on' border=0 cellspacing=0 cellpadding=0 >"+
								"<tr unselectable='on'>"+
									"<td unselectable='on' class='text' nowrap>基色：</td><td unselectable='on' align='left' nowrap><span unselectable='on' id='RGB' title='title'>#000000</span></td>"+
								"</tr>"+
								"<tr unselectable='on'>"+
									"<td unselectable='on' class='text'>亮度：</td><td unselectable='on' align='left'><span id='GRAY' title='title'>120</span></td>"+
								"</tr>"+
								"<tr unselectable='on'>"+
									"<td unselectable='on' class='text'>代码：</td><td unselectable='on' align='left'><input type='text' size='7' id='SelColor' onblur='SelectColor.setColor(value)' value='#000000' onclick='this.focus();'/></td>"+
								"</tr>"+
							"</table>"+
						"</td>"+
						"<td unselectable='on' valign='middle' width='80px' align='center'>"+
							"<div unselectable='on'><button unselectable='on' onclick='colorSelector.okButtonClick();'>确定</button></div>"+
							"<div unselectable='on' style='padding-top:10px'><button unselectable='on' onclick='colorSelector.hideMoreColorSelector();'>取消</button></div>"+
						"</td>"+
					"</tr>"+
				"</table>"+
				"</td></tr>"+
			"</table>"+
		"</center>";
		return sBodyHTML;
	},
	drawColorTable : function(){
		var sHTML = '<table unselectable="on" id="ColorTable" style="border: 0; cursor: pointer;" cellspacing="0" cellpadding="0" onclick="SelectColor.clickColorTable(event)" onmouseover="SelectColor.mouseoverColorTable(event)" onmouseout="SelectColor.mouseoutColorTable(event)">';
		var cnum = new Array(1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0);
		for(i = 0; i < 16; i ++){
			sHTML += '<tr>';
			for(j = 0; j < 30; j ++){
				n1 = j % 5;
				n2 = Math.floor(j / 5) * 3;
				n3 = n2 + 3;

				sHTML += wc((cnum[n3] * n1 + cnum[n2] * (5 - n1)),
				(cnum[n3 + 1] * n1 + cnum[n2 + 1] * (5 - n1)),
				(cnum[n3 + 2] * n1 + cnum[n2 + 2] * (5 - n1)), i);
			}
			sHTML +='</tr>';
		}
		sHTML += "</table>";
		return sHTML;
	},
	drawGrayTable : function(){
		var sHTML = '<table unselectable="on" id="GrayTable" style="border: 0; cursor: pointer;" cellspacing="0" cellpadding="0" onclick="SelectColor.clickGrayTable(event)" onmouseover="SelectColor.mouseoverGrayTable(event)" onmouseout="SelectColor.mouseoutGrayTable(event)">';
		for(i = 255; i >= 0; i -= 8.5){
			sHTML += '<tr unselectable="on" style="background-color:#' + SelectColor.ToHex(i) + SelectColor.ToHex(i) + SelectColor.ToHex(i) + ';"><td title="' + Math.floor(i * 16 / 17) + '" style="height:4px;width:20px;" ></td></tr>';
		}
		sHTML += '</table>';
		return sHTML;
	},
	okButtonClick : function (){
		document.getElementById("SelColor").onblur();
		var reg = "^#[A-Fa-f0-9]{6}$";
		if(SelRGB.match(reg)){
			this.changeSelOptColor(SelRGB);
			// 隐藏更多颜色选择器
			this.hideMoreColorSelector();
		}
	}
}
var hideFun = function(){
	colorSelector.hideColorSelector();
	// 隐藏颜色选择器遮布
	colorSelector.eMaskIframe.style.display = "none";
}.bind(this);