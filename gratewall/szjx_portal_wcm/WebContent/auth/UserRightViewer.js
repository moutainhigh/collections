/*!
 * 查看权限的相关类
 * wenyh@2006-03-10 create.
 * wenyh@2006-04-30 修正权限的显示的Bug:不应该在站点类权限显示频道权限
 */
 
function RightDef(name,index){	
	this.name = name;
	this.index = index;
};

function Right(target,value,type){
	this.target = target;
	this.values = new Array();
	
	var nLen = value.length;
	for(var i=nLen; i>0; i--){
		this.values[nLen-i] = value.charAt(i-1);
	};
	
	this.range = this.values.length;
	this.type = type;
};

Right.prototype.hasRight = function(index) {
	if(this.range < index+1) return false;
    return (this.values[index] == 1);
};

function UserRightViewer(){
	this.RightDefs = [];
	this.Rights = [];
	this.HeadRowIds = [];
};

UserRightViewer.prototype.addRightDef = function(name,index){	
	var rightdef = new RightDef(name,index);

	if((index+1) > this.RightDefs.length){
		this.RightDefs.length = index+1;
	};

	this.RightDefs[index] = rightdef;	
};

UserRightViewer.prototype.addRight = function(target,value,divId){	
	var right = new Right(target,value,divId);	
	this.Rights.push(right);
};

UserRightViewer.prototype.drawRightTabs = function(_divId,_nStartIndex,_nEndIndex){	
	var sHtml = '<TABLE width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="A6A6A6">'
				+'	<TR bgcolor="#BEE2FF" class="list_th">'				
				+'		<TD>授权对象</TD>';	
	
	//right def	
	var rightdefs = new Array();
   
	for(var i=_nStartIndex; i < this.RightDefs.length && i <= _nEndIndex; i++){
		var temp = this.RightDefs[i];
		if(temp == null) continue;

		sHtml += "<TD>" + temp.name + "</TD>";
		rightdefs.push(temp);
	};
	
	sHtml += '</TR>';

	var right,rightdef;
	var sBgColor="white";	

	for(var i=0; i < this.Rights.length; i++){
		right = this.Rights[i];
		if("id_TRSSimpleTab3" != _divId && right.type == "DOCUMENT") continue;
		
		var sTrHtml  = '<TR style="background-color:' + sBgColor + ';font-size:9pt;">\n'				
					  +'	<TD>' + right.target + '</TD>';
		
		for(var j=0; j < rightdefs.length; j++){
			rightdef = rightdefs[j];			
			sTrHtml += '<TD align=center title='+right.target+':'+rightdef.name+'>'
					  +'	<input type="checkbox" disabled ' +(right.hasRight(rightdef.index)?"checked":"") + '>'
					  +'</TD>';			
		};

		sTrHtml += '</TR>';

		if(sTrHtml.indexOf("checked") >0){			
			sHtml += sTrHtml;
			if("white" == sBgColor){
				sBgColor = "F5F5F5";
			}else{
				sBgColor = "white";
			};			
		};

		if("id_TRSSimpleTab0" == _divId) break;
	};

    sHtml+='</TABLE>';
	
	var tabdiv = document.getElementById(_divId);
	tabdiv.innerHTML = sHtml;	
};

UserRightViewer.prototype.displayRightsOnTab = function(_nStartIndex,_nEndIndex,_type,_divId){	
	var sHtml ="";

	var right,rightdef;
	if(_divId == "id_TRSSimpleTab0"){
		var rowNum = 1;

		sHtml += '<table width="98%" border="0" cellpadding="4" cellspacing="1"><tr><td bgcolor="#B4D1ED" style="color:#000000;font-size:12px;padding:2px;border-bottom:1px solid #92bce4">';
		sHtml +='&nbsp;&nbsp;在当前站点拥有以下站点类操作权限</td></tr><tr><td bgcolor="#f1f1f1" style="padding:4px;line-height:110%;color:blue;font-size:14px;">';

		for(var i=_nStartIndex; i < this.RightDefs.length && i <= _nEndIndex; i++){
			rightdef = this.RightDefs[i];
			if(rightdef == null) continue;			
			
			for(var j=0; j<this.Rights.length; j++){
				right = this.Rights[j];
				//wenyh@20060430 only show the right set on site.
				if(right == null || right.type != "SITE") continue;
				if(right.hasRight(rightdef.index)){
					sHtml += rightdef.name + '&nbsp;&nbsp;&nbsp;&nbsp;';
					rowNum ++;
				};
			};
		};

		if(rowNum > 1){
			sHtml += '</td></tr></table><br>';
		}else{
			sHtml = "";
		};
	}else{
		for(var i=_nStartIndex; i < this.RightDefs.length && i <= _nEndIndex; i++){
			rightdef = this.RightDefs[i];
			if(rightdef == null) continue;		
			
			
			var trid = _divId+i;

			var righthostdesc = '<table width="98%" border="0" cellpadding="1" cellspacing="0"><tr id="' + trid + '"><td bgcolor="#B4D1ED" style="color:#000000;font-size:14px;padding:2px;border-bottom:1px solid #92bce4">';
			righthostdesc += '&nbsp;&nbsp;可以在以下对象进行<font color=red>[' + rightdef.name  + ']</font>操作</td></tr><tr><td bgcolor="#f1f1f1" style="padding:4px;line-height:90%;"><div id="div_' + trid + '" style="display:inline;height:110;width:100%;overflow-y:auto"><table cellpadding="4" cellspacing="2">';	
			
			var rowNum = 1;
			for(var j=0; j<this.Rights.length; j++){			
				right = this.Rights[j];
				//if(right.type == "DOCUMENT") continue;

				if(right.hasRight(rightdef.index)){				
					if((rowNum-1)%3 == 0){
						righthostdesc += '<tr>';
					};

					righthostdesc +='<td bgcolor="#f1f1f1" style="color:blue;font-size:12px">' + right.target + '&nbsp;&nbsp;</td>';
					
					if(rowNum%3 == 0){
						righthostdesc += '</tr>';
					};

					rowNum++;
				};
			};
			
			if(rowNum > 1){
				if(rowNum%3 == 0){
					righthostdesc += '<td bgcolor="#f1f1f1" /></tr>';
				}else if((rowNum+1)%3 == 0 ){
					righthostdesc += '<td bgcolor="#f1f1f1" /><td bgcolor="#f1f1f1" /></tr>';
				};

				sHtml += righthostdesc + '</table></div></td></tr></table><br>';
				this.HeadRowIds.push(trid);
			};
		};
	};

	var tabdiv = document.getElementById(_divId);
	tabdiv.innerHTML = sHtml;
	
	for(var i=0;i<this.HeadRowIds.length;i++){
		var el = document.getElementById(this.HeadRowIds[i]);						
		el.onclick = this.showHide;
	};
};

UserRightViewer.prototype.showHide = function(){	
	var id = "div_" + this.id
	var divel = document.getElementById(id);	
	if(divel.style.display == "none"){
		divel.style.display = "inline";
	}else{
		divel.style.display = "none";
	};
};

var RightViewer = new UserRightViewer();