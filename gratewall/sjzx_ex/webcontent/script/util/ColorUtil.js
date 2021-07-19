var ColorUtil = new Object();

ColorUtil.create = function(dComposite,fSelectAction){
	this.composite = dComposite;
	this.selectAction = fSelectAction;
	this.init();
}

ColorUtil.create.prototype = {
	init:function(){
		var curColorDiv = $("<div class=\"colorSelect_cell\"></div>");
		$(this.composite).html("");
		$(this.composite).css("border","1pt solid #000000");
		$(this.composite).css("width","160px");
		$(this.composite).css("font-size","9pt");
		$(this.composite).append(curColorDiv);
		$(this.composite).append("<div style=\"margin-top:4px;text-align:center;background-Color:EEEEEE;width:128px;\">µ±Ç°ÑÕÉ«</div>");
		var colorlist=new Array(40);
		colorlist[0]="#000000";	colorlist[1]="#993300";	colorlist[2]="#333300";	colorlist[3]="#003300";
		colorlist[4]="#003366";	colorlist[5]="#000080";	colorlist[6]="#333399";	colorlist[7]="#333333";
	
		colorlist[8]="#800000";	colorlist[9]="#FF6600";	colorlist[10]="#808000";colorlist[11]="#008000";
		colorlist[12]="#008080";colorlist[13]="#0000FF";colorlist[14]="#666699";colorlist[15]="#808080";
	
		colorlist[16]="#FF0000";colorlist[17]="#FF9900";colorlist[18]="#99CC00";colorlist[19]="#339966";
		colorlist[20]="#33CCCC";colorlist[21]="#3366FF";colorlist[22]="#800080";colorlist[23]="#999999";
	
		colorlist[24]="#FF00FF";colorlist[25]="#FFCC00";colorlist[26]="#FFFF00";colorlist[27]="#00FF00";
		colorlist[28]="#00FFFF";colorlist[29]="#00CCFF";colorlist[30]="#993366";colorlist[31]="#CCCCCC";
	
		colorlist[32]="#FF99CC";colorlist[33]="#FFCC99";colorlist[34]="#FFFF99";colorlist[35]="#CCFFCC";
		colorlist[36]="#CCFFFF";colorlist[37]="#99CCFF";colorlist[38]="#CC99FF";colorlist[39]="#FFFFFF";
		
		var colorIndex = 0;
		for(var i=0;i<5;i++){
			var row = $("<div class=\"colorSelect_row\"></div>");
			$(this.composite).append(row);
			for(var j=0;j<8;j++){
				colorIndex = i*8+j;
				var colorDiv  = $("<div class=\"colorSelect_cell\" color=\""+colorlist[colorIndex]+"\"></div>");
				colorDiv.css("backgroundColor",colorlist[colorIndex]);
				if(this.selectAction)colorDiv.click(this.selectAction);
				
				colorDiv.mouseover(function(){
					this.style.border = "1pt outset #3E3EFF";
				});
				
				colorDiv.mouseout(function(){
					this.style.border = "1pt solid #ECE9D8";
				});
				
				row.append(colorDiv);
			}
		}	
	}
}

