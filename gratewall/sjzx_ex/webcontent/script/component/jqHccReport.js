var HccReport = new Object();

HccReport.resource = new Object();

HccReport.create = function(composite){
	this.composite = $(composite);
	this.borderStyle = new HccReport.BorderStyle("1pt","solid","#000000");
	this.noBorderStyle = new HccReport.BorderStyle("0","","");
	this.init();
}

HccReport.create.prototype = {
	reportsDoc:null,
	
	composite:null,
	
	showRows:0,
	
	showCols:0,
	
	rows:0,
	
	cols:0,
	
	reportTableContainer:null,
	
	bodyContainer:null,
	
	downCell:null,
	
	clickCell:null,
	
	maxStyleId:0,
	
	cellStyles:new Array(),
	
	tableCells:new Array(),
	colWidthArray:new Array(),
	rowHeightArray:new Array(),
	
	selectSources:new Array(),
	
	borderStyle:null,
	noBorderStyle:null,
	

	init:function(){
		var p = this;
		this.composite.html('');
		this.composite.get(0).oncontextmenu = function(){
			event.returnValue = false;
    	event.cancelBubble=true;
		}
		
		this.composite.get(0).onselectstart = function(){
	  	event.returnValue = false;
			event.cancelBubble=true;
			if (event.stopPropagation) event.stopPropagation();
		}
	},

	loadReport:function(reportsDoc){
		var p = this;
		var maxRow = 0;
		var maxCol = 0;
		this.reportsDoc = reportsDoc;
		var maxStyleId = 0;
		var styleFactory = new HccReport.styleFactory();
		var cellStyle;
		var font;
		var fontName,fontSize,fontColor,bold,italic,underline;
		var alignment;
		var horizontal,vertical;
		
		var border;
		var borderLeftStyle,borderTopStyle,borderRightStyle,borderBottomStyle;
		var lineStyle,borderColor,borderWeight;

		var interior;
		var interiorColor,interiorPattern;
		
		var styles = new Array();
		
		$("Reports/Report/html",this.reportsDoc).each(function(){
			var table = $("table",this).get(0);
			maxRow = table.getAttribute("maxRow");
			maxCol = table.getAttribute("maxCol");
			sumWidth = table.getAttribute("sumWidth");
			
			p.initReportContainer(maxRow,maxCol,sumWidth);//tableWidth;
			p.bodyContainer.html(this.xml);
		});
		
		$("Reports/Report/Style/CellStyles/CellStyle",this.reportsDoc).each(function(){
			var styleId = this.getAttribute("ID");
			var id = styleId.substring(1);
			maxStyleId = Math.max(maxStyleId,id);
			
			var fontNode = $("Font",this).get(0);
			//<Font FontName="" Color="00808000" Size="-24" Bold="1" Italic="0" Underline="0"/>
			if(fontNode){
				fontName = fontNode.getAttribute("FontName");
				fontSize = fontNode.getAttribute("Size");
				fontColor = fontNode.getAttribute("Color");
				bold = fontNode.getAttribute("Bold");
				italic = fontNode.getAttribute("Italic");
				underline = fontNode.getAttribute("Underline");
				font = styleFactory.getFont(fontName,fontSize,fontColor,bold,italic,underline);
			}
		  try{
			  $("Borders/Border",this).each(function(){
			  	var borderNode = this;
			  	var position = borderNode.getAttribute("Position");//borderLeftStyle,borderTopStyle,borderRightStyle,borderBottomStyle;
			  	borderWeight = borderNode.getAttribute("Weight");
			  	lineStyle = borderNode.getAttribute("LineStyle");
			  	borderColor = borderNode.getAttribute("Color");
			  	switch(position){
			  		case "Left":
			  			borderLeftStyle = styleFactory.getBorderStyle(borderWeight,lineStyle,borderColor);
			  			break;
			  		case "Top":
			  			borderTopStyle = styleFactory.getBorderStyle(borderWeight,lineStyle,borderColor);
			  			break;
			  		case "Right":
			  			borderRightStyle = styleFactory.getBorderStyle(borderWeight,lineStyle,borderColor);
			  			break;
			  		case "Bottom":
			  			borderBottomStyle = styleFactory.getBorderStyle(borderWeight,lineStyle,borderColor);
			  			break;
			  	}
			  	borderWeight=null;
			  	lineStyle=null;
			  	borderColor=null;
			  });
			}catch(err){
			}
		  border = new HccReport.Border(borderLeftStyle,borderTopStyle,borderRightStyle,borderBottomStyle);
		  var alignmentNode = $("Alignment",this).get(0);
		  if(alignmentNode){
			  horizontal = alignmentNode.getAttribute("Horizontal");
			  vertical = alignmentNode.getAttribute("Vertical");
			  alignment = styleFactory.getAlignment(horizontal,vertical);
			}
		  cellStyle = new HccReport.CellStyle(styleId,border,font,alignment,interior);//sStyleId,oBorder,oFont,oAlignment,oInterior
		  borderLeftStyle = null;
			borderTopStyle = null;
			borderRightStyle = null;
			borderBottomStyle = null;
		  p.cellStyles[styleId] = cellStyle;
		  styles[styleId] = cellStyle.toStyle();
		});
		this.maxStyleId = maxStyleId+1;
		$("tr",p.bodyContainer).each(function(){
			var rowIndex = this.getAttribute("rowIndex");
			p.tableCells[rowIndex]=new Array();
			
			$("td",this).each(function(){
				var colIndex = this.getAttribute("colIndex");
				var styleId = this.getAttribute("styleId");
				var dataType = this.getAttribute("dataType");
				var spanRow = this.getAttribute("rowSpan");
				var spanCol = this.getAttribute("colSpan");
				this.innerText = this.getAttribute("title");
				if(styleId){
					if(styles[styleId]){
						this.style.cssText = this.style.cssText+styles[styleId];
					}
				}
				p.tableCells[rowIndex][colIndex]=this;
				p.wrapCell(this);
				var reportCell = null;
				if(rowIndex>0&&colIndex>0){
					if(this.innerHTML==""){
						this.innerHTML = "&nbsp;";
					}
				}
				colIndex = null;
				styleId = null;
				datyType = null;
				spanRow = null;
				spanCol = null;
			});
		});
	
		$("Reports/Report/Style/Table/Col",this.reportsDoc).each(function(){
			var colNo = this.getAttribute("NO");
			var colWidth = this.getAttribute("Width")||80;
			//alert(colWidth);
			p.colWidthArray[colNo-1] = parseInt(colWidth);
		});
		
		$("Reports/Report/Style/Table/Row",this.reportsDoc).each(function(){
			var rowNo = this.getAttribute("NO");
			var rowHeight = this.getAttribute("Height")||20;
			p.rowHeightArray[rowNo-1] = parseInt(rowHeight);
		});
	},
	
	initReportContainer:function(maxRow,maxCol,bodyWidth){
		var  p = this;
		this.composite.css("overflow","auto");
		this.clearReport();
		
		this.reportHeadContainer =  $('<div class="HccReport_headContainer"/>');
		this.reportHeadContainer.append(this.createTable());
		
		this.reportFootContainer = $('<div class="HccReport_footContainer"/>');
		this.reportFootContainer.append(this.createTable());
		
		this.reportTableContainer = $('<div class="HccReport_container"/>');
		this.reportTableContainer.css("width",parseInt(bodyWidth));
		this.rows = parseInt(maxRow);
		this.cols = parseInt(maxCol);
		
		this.pubContainer = this.createPub();
		this.topContainer = this.createTopHead();
		this.leftContainer = this.createLeftHead();
		this.bodyContainer = $("<div class=\"HccReport_body\"/>");
		this.reportTableContainer.append(this.bodyContainer);
		this.composite.before(this.reportHeadContainer);
		this.composite.append(this.reportTableContainer);
		this.composite.after(this.reportFootContainer);
		
		this.composite.scroll(function(){
			//var scrollTop  = this.scrollTop;
			//var scrollLeft = this.scrollLeft;
			
			//p.pubContainer.css("left",scrollLeft);
			//p.pubContainer.css("top",scrollTop);
			//p.leftContainer.css("left",scrollLeft);
			//p.topContainer.css("top",scrollTop);
		});
	},
	
	createPub:function(){
		var p = this;
		var pubContainer = $("<div class=\"HccReport_pub\"/>");
		pubContainer.append(this.createTable());
		this.reportTableContainer.append(pubContainer);
		return pubContainer;
	},
	
	createTopHead:function(){
		var p = this;
		var outWrap = $('<div class=\"HccReport_topHead\"><div style="overflow:hidden;"></div></div>');
		var topContainer = $("<div/>");
		outWrap.find('>div').append(topContainer);
		topContainer.append(this.createTable());
		this.reportTableContainer.append(outWrap);
		return topContainer;
	},

	createLeftHead:function(){
		var p = this;
		var outWrap = $('<div class=\"HccReport_leftHead\"><div style="overflow:hidden;"></div></div>');
		var leftContainer = $("<div/>");
		outWrap.find('>div').append(leftContainer);
		leftContainer.append(this.createTable());
		this.reportTableContainer.append(outWrap);
		return leftContainer;
	},
	
	createTable:function(){
		var table = document.createElement("table");
		table.style.tableLayout="fixed";
		table.style.position='relative';
		table.style.width = '100%';
		table.style.borderCollapse="collapse";
		table.cellSpacing=0;
		table.cellPandding=0;
		return table;
	},
	
	clearReport:function(){
		this.composite.html("");//
		if(this.reportHeadContainer)this.reportHeadContainer.remove();
		if(this.reportFootContainer)this.reportFootContainer.remove();
		this.tableCells = new Array();
		this.rowHeightArray = new Array();//
		this.colWidthArray = new Array();//

		for(iStyleId in this.cellStyles){
			if(iStyleId=="indexOf")continue;
			if(this.cellStyles[iStyleId]){
				this.cellStyles[iStyleId].dispose();
				this.cellStyles[iStyleId] = null;
			}
		}
		
		this.cellStyles = new Array();//
		this.maxStyleId = 0;
	},

	wrapCell:function(cell){
		var p = this;
		cell.onclick = function(){
			if(p.clickCell!=null){
				p.clickCell.style.backgroundColor="";
			}else{
				
			}
			p.clickCell = this;
			p.clickCell.style.backgroundColor="#93C6FF";
		}
		
		cell.onmousedown = function(){
			p.downCell = cell;
		}
		
		cell.onmouseup = function(){
			p.downCell = null;
		}
	},
	/**
	 * @return boolean
	 */
	freezeCheck:function(cell){
		var  p = this;
		//合并单元格不可冻结
		if(cell.rowSpan>1||cell.colSpan>1){
			return false;
		}
		
		if(!(checkRowMerge(cell.rowIndex,this.cols)||checkRowHide(cell.rowIndex,this.cols))){
			return false;
		}
		//检查
		for(var i=cell.rowIndex-1;i>=1;i--){
			var iCell;
			var njCell;
			iCell = p.tableCells[i][cell.colIndex];
			if(iCell.style.display=='none'){
				for(var nj=cell.colIndex-1;nj>0;nj--){
					njCell = p.tableCells[i][nj];
					if(parseInt(njCell.colIndex)+parseInt(njCell.colSpan)-1>nj){
						return checkRowMerge(njCell.rowIndex,this.cols);
					}
				}
			}
		}
		
		function checkRowHide(checkRow,endCol){
			var jCell;
			for(var j=0;j<=endCol;j++){
				jCell = p.tableCells[checkRow][j];
				if(jCell.style.display=='none'){
					return false;
				}
			}
			return true;
		}
		
		function checkRowMerge(checkRow,endCol){
			var jCell;
			for(var j=0;j<=endCol;j++){
				jCell = p.tableCells[checkRow][j];
				if(jCell.rowSpan>1){
					return false;
				}
			}
			return true;
		}
		
		return true;
		//for()
	},
	
	getMergeCell:function(cell){
		
		return cell;
	},
	
	freeze:function(){
		var p = this;
		if(!this.clickCell)return false;
		this.composite.css("overflow","hidden");
		if(this.freezeCheck(this.clickCell)){
			var clickRow = this.clickCell.rowIndex;
			var clickCol = this.clickCell.colIndex;
			
			var pub = this.freezePub(clickRow,clickCol);
			this.freezeTop(clickRow,clickCol,pub.height,pub.maxHeadIndex);
			var leftR = this.freezeLeft(clickRow,clickCol);
			
			for(var i = pub.maxHeadIndex;i<clickRow;i++){
				$('tr[@rowIndex='+(i)+']',this.bodyContainer).remove();
			}
			
			var footerHeight = 0;
			if(leftR.minFootIndex>0){
				for(var i = leftR.minFootIndex;i<=this.rows;i++){
					var $tr = $('tr[@rowIndex='+i+']',this.bodyContainer);
					footerHeight+=($tr.height()-2);
					$('>table',this.reportFootContainer).append($('tr[@rowIndex='+i+']',this.bodyContainer));
				}
			}
			if(footerHeight>0)footerHeight-=1;
			
			var compositeWidth = this.composite[0].offsetWidth;
			var compositeHeight = this.composite[0].offsetHeight;
			
			var reportWidth = this.reportTableContainer[0].offsetWidth;
			var reportHeight = this.reportTableContainer[0].offsetHeight;
			//top
			this.topContainer.parent().parent().css({
				left:pub.width,
				width:compositeWidth-pub.width-18,
				height:pub.height
			});
			
			this.topContainer.parent().width(compositeWidth-pub.width-18);
			this.topContainer.width(reportWidth-pub.width);
			this.topContainer.find('>table').css({
				height:pub.height
			});
			//left
			this.leftContainer.parent().parent().css({
				top:pub.height,
				width:pub.width,
				height:compositeHeight-pub.height-20
			});
			this.leftContainer.parent().css({height:compositeHeight-pub.height-20});
			this.leftContainer.css({
				height:reportHeight - pub.height-footerHeight
			});
			
			this.bodyContainer.css({
				position:'absolute',
				overflow:'scroll',
				left:pub.width,
				top:pub.height,
				width:compositeWidth-pub.width,
				height:compositeHeight-pub.height-footerHeight
			});
			this.bodyContainer.find('>table').css({
				width:(this.reportTableContainer[0].offsetWidth - pub.width),
				height:this.leftContainer.find('>table').height()//(this.reportTableContainer[0].offsetHeight - pub.height-footerHeight)
			});
			
			this.bodyContainer.scroll(function(){
				var scrollTop = this.scrollTop;
				var scrollLeft = this.scrollLeft;
				p.topContainer.parent()[0].scrollLeft = scrollLeft;
				p.leftContainer.parent()[0].scrollTop = scrollTop;
			});
			return true;
		}else{
			alert('\u8be5\u5355\u5143\u683c\u4e0d\u53ef\u51bb\u7ed3\uff01');//该单元格不可冻结！
			return false;
		}
	},
	
	freezePub:function(clickRow,clickCol){
		var cell;
		var pubWidth = 0;
		var pubHeight = 0;
		var maxHeadIndex = 0;
		for(var i=1;i<clickRow;i++){
			for(var j=1;j<clickCol;j++){
				if(j-this.tableCells[i][j].colSpan<0){
					maxHeadIndex = Math.max(maxHeadIndex,i);
				}
			}
		}
		
		for(var i=0;i<=maxHeadIndex;i++){
			$('>table',this.reportHeadContainer).append($('tr[@rowIndex='+i+']',this.bodyContainer));
			$('td',this.reportHeadContainer).css('border','');
		}
		for(var i=maxHeadIndex+1;i<clickRow;i++){
			var tr = document.createElement('tr');
			for(var j=1;j<clickCol;j++){
				cell = this.tableCells[i][j];
				if(j==1){
					tr.appendChild(this.tableCells[i][0]);
				}
				tr.appendChild(cell);
			}
			$('>table',this.pubContainer).append(tr);
		}
		
		
		for(var i=maxHeadIndex+1;i<clickRow;i++){
			pubHeight+=this.tableCells[i][0].offsetHeight;
		}
		
		for(var j=1;j<clickCol;j++){
			pubWidth+=this.colWidthArray[j-1];
		}
	
		$('>table',this.pubContainer).css({
			width :pubWidth,
			height:pubHeight
		});
		
		this.pubContainer.css({
			width :pubWidth,
			height:pubHeight+1
		});
		
		return {width:pubWidth,height:pubHeight,maxHeadIndex:maxHeadIndex};
	},
	
	freezeTop:function(clickRow,clickCol,topHeight,maxHeadIndex){
		var cell;
		var topWidth = 0;
		var widthArray = [];
		for(var j=clickCol;j<=this.cols;j++){
			topWidth+=this.colWidthArray[j-1];//tableCells[0][j].offsetWidth;
			$(this.tableCells[0][j]).remove();
		}
		for(var i=maxHeadIndex+1;i<clickRow;i++){
			var tr = document.createElement('tr');
			for(var j=clickCol;j<=this.cols;j++){
				cell = this.tableCells[i][j];
				if(j==clickCol){
					cell.style.borderLeft = '';
					cell.style.height = this.rowHeightArray[i-1];
				}
				tr.appendChild(cell);
				if(i==maxHeadIndex+1){
					cell.style.Width = this.colWidthArray[j-1];
				}
			}
			$('>table',this.topContainer).append(tr);
		}
	},
	
	freezeLeft:function(clickRow,clickCol){
		var cell;
		var leftWidth = 0;
		var leftHeight = 0;
		var minFootIndex = 0;
		var addFlag = true;

		for(var j=1;j<clickCol;j++){
			leftWidth+=this.tableCells[0][j].offsetWidth;
			$(this.tableCells[0][j]).remove();
		}
		
		var heightArray = [];
		for(var i=clickRow;i<=this.rows;i++){
			var tr = document.createElement('tr');
			var tTeight = this.tableCells[i][0].offsetHeight;
			heightArray[heightArray.length] = tTeight;
			leftHeight+=tTeight;
			for(var j=1;j<clickCol;j++){
				cell = this.tableCells[i][j];
				if(j==1){
					cell.style.height = tTeight;
				}
				//alert(cell.colSpan);
				if(j-cell.colSpan<0){
					if(addFlag){
						minFootIndex = i;
						addFlag = false;
					}
				}else{
					tr.appendChild(cell);
				}
			}
			if(addFlag){
				$('>table',this.leftContainer).append(tr);//.css({
				//	width :leftWidth,
				//	height:leftHeight
				//});
			}
		}
		
		return {minFootIndex:minFootIndex};
	},
	
	getCompositeWidth:function(){
		var compositeWidth = this.composite.get(0).offsetWidth;
		return compositeWidth;
	},
	
	getCompositeHeight:function(){
		var compositeHeight = this.composite.get(0).offsetHeight;
		return compositeHeight;
	}
}
HccReport.CellStyle = new Object();

HccReport.CellStyle = function(sStyleId,oBorder,oFont,oAlignment,oInterior){
	this.styleId = sStyleId;
	this.border = oBorder;
	this.font = oFont;
	this.alignment = oAlignment;
	this.interior = oInterior;
} 

HccReport.CellStyle.prototype = {
	styleId:null,
	
	border:null, //HccReport.Border
	
	font:null, //HccReport.Font
	
	alignment:null, //HccReport.Alignment
	
	interior:null,  //HccReport.Interior
	
	toCssClass:function(){
		var cssTexts=".";
		cssTexts+=this.styleId;
		cssTexts+="{"
		cssTexts+=this.toStyle();
		cssTexts+="}"
	},
	
	toStyle:function(){
		var cssTexts="";
		var cssName,cssValue;
		var border = this.border;
		
		if(border){
			var borderLeftStyle,borderTopStyle,borderRightStyle,borderBottomStyle;
			var weight,lineStyle,borderColor;
			borderRightStyle = border.getRightStyle();
			borderBottomStyle = border.getBottomStyle();
			
			if(borderRightStyle){
				weight = borderRightStyle.getWeight();
				lineStyle = borderRightStyle.getLineStyle();
				borderColor = borderRightStyle.getColor();
				//"border-right:1pt solid #000000;"
				if(!weight)weight="1pt";
				cssName= "border-right";
				cssValue = weight+" "+lineStyle+" "+borderColor;
				cssTexts+=this.getCssText(cssName,cssValue);
			}
			
			if(borderBottomStyle){
				weight = borderBottomStyle.getWeight();
				lineStyle = borderBottomStyle.getLineStyle();
				borderColor = borderBottomStyle.getColor();
				//"border-right:1pt solid #000000;"
				if(!weight)weight="1pt";
				cssName= "border-bottom";
				cssValue = weight+" "+lineStyle+" "+borderColor;
				cssTexts+=this.getCssText(cssName,cssValue);
			}
			
			if(border.getLeftStyle()){
				cssName="border-left";
				cssValue="1pt solid #000000";
				cssTexts+=this.getCssText(cssName,cssValue);
			}
			
			if(border.getTopStyle()){
				cssName="border-top";
				cssValue="1pt solid #000000";
				cssTexts+=this.getCssText(cssName,cssValue);
			}
			
		}
		
		var font = this.font;
		if(font){
			var fontFamily,fontStyle,fontSize,color,fontWeight,textDecoration;
			 
			fontFamily = font.getFontFamily();
			fontStyle = font.getFontStyle();
			fontSize = font.getSize();
			color = font.getColor();
			fontWeight = font.getFontWeight();
			textDecoration = font.getTextDecoration();
			if(fontFamily)cssTexts+=this.getCssText("font-family",fontFamily);
			if(fontStyle)cssTexts+=this.getCssText("font-style",fontStyle);
			if(fontSize)cssTexts+=this.getCssText("font-size",cssValue);
			if(color)cssTexts+=this.getCssText("color",color);
			if(fontWeight)cssTexts+=this.getCssText("font-weight",fontWeight);
			if(textDecoration)cssTexts+=this.getCssText("text-decoration",textDecoration);
		}
		
		var alignment = this.alignment;
		if(alignment){
			var horizontal,vertical;
			horizontal = alignment.getHorizontal();
			vertical = alignment.getVertical();
			if(horizontal)cssTexts+=this.getCssText("text-align",horizontal);
			if(vertical)cssTexts+=this.getCssText("vertical-align",vertical);
		}
		
		var interior = this.interior;
		if(interior){
			var interiorColor = interior.getColor();
			if(interiorColor)if(interiorColor)cssTexts+=this.getCssText("background-color",interiorColor);
		}
		return cssTexts;
	},
	
	getStyleId:function(){
		return this.styleId;
	},
	
	setStyleId:function(styleId){
		this.styleId = styleId;
	},
	
	setBorder:function(border){
		this.border = border;
	},
	
	setFont:function(font){
		this.font = font;
	},
	
	setAlignment:function(alignment){
		this.alignment = alignment;
	},
	
	setInterior:function(interior){
		this.interior = interior;
	},
	
	getBorder:function(){
		return this.border;
	},
	
	getFont:function(){
		return this.font;
	},

	getAlignment:function(){
		return this.alignment;
	},
	
	getInterior:function(){
		return this.interior;
	},
	
	setBorderStyle:function(jCell,borderStyle,borderPositionPre){
		this.setCssStyle(jCell,borderPositionPre+"Color",borderStyle.getColor());
		this.setCssStyle(jCell,borderPositionPre+"Style",borderStyle.getLineStyle());
		this.setCssStyle(jCell,borderPositionPre+"Width",borderStyle.getWeight());
	},
	
	getBorderStyle:function(jCell,borderPositionPre){
		
	},
	
	
	setCssStyle:function(jCell,styleName,styleValue){
		try{
			if(styleValue)eval("jCell.getCell().style."+styleName+"='"+styleValue+"'");
		}catch(err){
			
		}
	},
	
	getCssText:function(cssName,cssValue){
		var cssText = "";
		cssText+=cssName;
		cssText+=":";
		cssText+=cssValue;
		cssText+=";";
		
		return cssText;
	},
	
	dispose:function(){
		if(this.border)this.border.dispose();
		this.font = null;
		this.alignment = null;
		this.interior = null;
	},
	
	toString:function(){
		var str="";
		if(this.border){
			str+=this.border.toString();
		}
		
		if(this.font){
			str+=this.font.toString();
		}
		
		if(this.alignment){
			str+=this.alignment.toString();
		}
		
		if(this.interior){
			str+=this.interior;
		}
		return str;
	},
	
	clone:function(){
		var border,font,alignment,interior;
		if(this.border){
			border = this.border.clone();
		}
		if(this.font){
			font = this.font.clone();
		}
	  if(this.alignment){
	  	alignment = this.alignment.clone();
	  }
		if(this.interior){
			interior = this.interior.clone();
		}
	
		var cellStyle = new HccReport.CellStyle("",border,font,alignment,interior);
		return cellStyle;
	}
	
}

HccReport.Border = new Object();

HccReport.Border = function(oLeftStyle,oTopStyle,oRightStyle,oBottomStyle){
	this.leftStyle = oLeftStyle;
	this.topStyle = oTopStyle;
	this.rightStyle = oRightStyle;
	this.bottomStyle = oBottomStyle;
}

HccReport.Border.prototype = {
	getLeftStyle:function(){
		return this.leftStyle;
	},
	
	getTopStyle:function(){
		return this.topStyle;
	},
	
	getRightStyle:function(){
		return this.rightStyle;
	},
	
	getBottomStyle:function(){
		return this.bottomStyle;
	},
	
	setLeftStyle:function(borderStyle){
		this.leftStyle = borderStyle;
	},
	
	setTopStyle:function(borderStyle){
		this.topStyle = borderStyle;
	},
	
	setRightStyle:function(borderStyle){
		this.rightStyle = borderStyle;
	},
	
	setBottomStyle:function(borderStyle){
		this.bottomStyle = borderStyle;
	},
	
	/**
	 *@param bordersElement
	 *@param reportsDoc
	 *@param position
	 *@return 
	 */
	createBorderElement:function(bordersElement,reportsDoc,position,borderStyle){
		var borderElement = reportsDoc.createElement("Border");
		borderElement.setAttribute("Position",position);
		borderStyle.addElementAttribute(borderElement);
		bordersElement.appendChild(borderElement);
	},
	
	dispose:function(){
		if(this.leftStyle){
			this.leftStyle = null;
		}
		
		if(this.topStyle){
			this.topStyle = null;
		}
		
		if(this.rightStyle){
			this.rightStyle = null;
		}
		
		if(this.bottomStyle){
			this.bottomStyle = null;
		}
	},
	
	toString:function(){
		var str="";
		if(this.leftStyle){
			str+="L";
			str+=this.leftStyle.toString();
		}
		
		if(this.topStyle){
			str+="T";
			str+=this.topStyle.toString();
		}
		
		if(this.rightStyle){
			str+="R";
			str+=this.rightStyle.toString();
		}
		
		if(this.bottomStyle){
			str+="B";
			str+=this.bottomStyle.toString();
		}
		return str;
	},
	
	clone:function(){
		var border;
		var leftStyle,topStyle,rightStyle,bottomStyle;
		if(this.leftStyle){
			leftStyle = this.leftStyle.clone();
		} 
		if(this.topStyle){
			topStyle = this.topStyle.clone();
		}
		if(this.rightStyle){
			rightStyle = this.rightStyle.clone();
		}
		if(this.bottomStyle){
			bottomStyle = this.bottomStyle.clone();
		}
		
		border = new HccReport.Border(leftStyle,topStyle,rightStyle,bottomStyle);
		return border;
	}
}

HccReport.BorderStyle = new Object();

HccReport.BorderStyle = function(sWeight,sLineStyle,sColor){
	this.lineStyle = sLineStyle;
	this.color = sColor;
	this.weight = sWeight;
}

HccReport.BorderStyle.prototype = {
	getLineStyle:function(){
		return this.lineStyle;
	},
	
	getColor:function(){
		return this.color;
	},
	
	getWeight:function(){
		return this.weight;
	},
	
	toString:function(){
		return this.weight+this.color+this.lineStyle;
	},
	
	clone:function(){
		var borderStyle;
		borderStyle = new HccReport.BorderStyle(this.weight,this.lineStyle,this.color);
		return borderStyle;
	}
}


HccReport.styleFactory = new Object();

HccReport.styleFactory = function(){
}

HccReport.styleFactory.prototype = {
	getFont:function(fontName,size,color,bold,italic,underline){
		var fontFamily,fontstyle,fontSize,fontColor,textDecoration,fontWeight;//sFontFamily,sFontStyle,sColor,sSize,sTextDecoration,sFontWeight
		
		if(fontName)fontFamily = fontName;//
		
		if(size)fontSize = Math.abs(size)+"pt";//
		
		if(color)fontColor = "#"+color.substring(2);
		
		if(italic==1){//0
			fontstyle = "italic";
		}else{
			fontstyle = "";
		}
		
		if(bold==1){
			fontWeight = "bold";
		}else{
			fontWeight = "";
		}
		
		if(underline==1){
			textDecoration = "underline";
		}else{
			textDecoration = "";
		}
		
		var font = new HccReport.Font(fontFamily,fontstyle,fontSize,fontColor,textDecoration,fontWeight);
	},
	
	getBorderStyle:function(weight,lineStyle,color){
		var borderWeight,borderLineStyle,borderColor;
		
		borderColor = "#"+color.substring(2);
		var borderStyle = new HccReport.BorderStyle(weight,lineStyle,borderColor);
		return borderStyle;
	},
	
	getAlignment:function(horizontal,vertical){
		if(vertical =="Center"){
			vertical = "middle";
		}
		var alignment = new HccReport.Alignment(horizontal,vertical);
		return alignment;
	},
	
	getInterior:function(color,pattern){
		var interiorColor,interiorPattern;
		if(color)interiorColor = "#"+color.substring(2);
		interiorPattern = pattern;
		var interior = new HccReport.Interior(interiorColor,interiorPattern);
		return interior;
	}

}
HccReport.Color = new Object();

HccReport.LineStyle = new Object();

HccReport.Font = new Object();

HccReport.Font = function(sFontFamily,sFontStyle,sColor,sSize,sTextDecoration,sFontWeight){
	this.fontFamily = sFontFamily;
	this.fontStyle = sFontStyle;
	this.color = sColor;
	this.size = sSize;
	this.textDecoration = sTextDecoration;
	this.fontWeight = sFontWeight;
}

HccReport.Font.prototype = {
	getFontFamily:function(){
		return this.fontFamily;
	},
	
	setFontFamily:function(){
		this.fontFamily = fontFamily;
	},
	
	getFontStyle:function(){
		return this.fontStyle;
	},
	
	setFontStyle:function(fontStyle){
		this.fontStyle = fontStyle;
	},
	
	getColor:function(){
		return this.color;
	},
	
	setColor:function(color){
		this.color = color;
	},
	
	getSize:function(){
		return this.size;
	},
	
	setSize:function(size){
		this.size = size;
	},
	
	getTextDecoration:function(){
		return this.textDecoration;
	},
	
	setTextDecoration:function(textDecoration){
		this.textDecoration = textDecoration;
	},
	
	getFontWeight:function(){
		return this.fontWeight;
	},
	
	setFontWeight:function(fontWeight){
		this.fontWeight = fontWeight;
	},
	
	toString:function(){
		var str = "";
		if(this.fontFamily){
			str+=this.fontFamily;
		}
		
		if(this.fontStyle){
			str+=this.fontStyle;
		}
		
		if(this.color){
			str+=this.color;
		}
		
		if(this.size){
			str+=this.size;
		}
		
		if(this.textDecoration){
			str+=this.textDecoration;
		}
		
		if(this.fontWeight){
			str+=this.fontWeight;
		}
		return str;
	},
	
	clone:function(){
		var font = new HccReport.Font(this.fontFamily,this.fontStyle,this.color,this.size,this.textDecoration,this.fontWeight);
		return font;
	}
}

HccReport.Alignment = new Object();
HccReport.Horizontal = new Object();
HccReport.Vertical = new Object();

HccReport.Alignment = function(sHorizontal,sVertical){
	this.horizontal = sHorizontal;
	this.vertical = sVertical;
}
HccReport.Alignment.prototype = {
	getHorizontal:function(){
		return this.horizontal;
	},
	
	getVertical:function(){
		return this.vertical;
	},
	
	setHorizontal:function(horizontal){
		this.horizontal = horizontal;
	},
	
	setVertical:function(vertical){
		this.vertical = vertical;
	},
	
	toString:function(){
		var str = "";
		if(this.horizontal){
			str+=this.horizontal;
		}
		
		if(this.vertical){
			str+=this.vertical;
		}
		return str;
	},
	
	clone:function(){
		var alignment = new HccReport.Alignment(this.horizontal,this.vertical);
		return alignment;
	}
}

HccReport.Interior = new Object();

HccReport.Interior = function(sColor,sPattern){
	this.color = sColor;
	this.pattern = sPattern;
}

HccReport.Interior.prototype = {
	getColor:function(){
		return this.color;
	},
	
	getPattern:function(){
		return this.pattern;
	},
	
	setColor:function(color){
		this.color = color;
	},
	
	setPattern:function(pattern){
		this.pattern = pattern;
	},
	
	toString:function(){
		var str="";
		if(this.color){
			str+=this.color;
		}
		
		if(this.pattern){
			str+=this.pattern;
		}
		return str;
	},
	
	clone:function(){
		var interior = new HccReport.Interior(this.color,this.pattern);
		return interior;
	}
}