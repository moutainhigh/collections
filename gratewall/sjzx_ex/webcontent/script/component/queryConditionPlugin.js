/**
 * ��ѯ�������
 */
 
 /**
  * 
  
 			   compCond_String     : [{text:'����', value:'=', prefix:'\'', postfix:'\''},
	                                  {text:'������', value:'<>', prefix:'\'', postfix:'\''},
	                                  {text:'��ʼ��', value:'like', prefix:'\'', postfix:'%\''},
	                                  {text:'������', value:'like', prefix:'\'%', postfix:'\''},
	                                  {text:'����', value:'like', prefix:'\'%', postfix:'%\''},
	                                  {text:'IN', value:'IN', prefix:'(\'', postfix:'\')'},
	                                  {text:'Ϊ��', value:'is null', prefix:'(\'', postfix:'\')'},
	                                  {text:'��Ϊ��', value:'is not null', prefix:'(\'', postfix:'\')'}
	                                  ],
               compCond_Number     : [{text:'����', value:'='},
                                      {text:'������', value:'='},
                                      {text:'����', value:'='},
                                      {text:'���ڵ���', value:'='},
                                      {text:'С��', value:'='},
                                      {text:'С�ڵ���', value:'='}],
               compCond_Date       : [{text:'����', value:'='},
                                      {text:'������', value:'='},
                                      {text:'��ʼ��', value:'='},
                                      {text:'������', value:'='}],
               compCond_Code       : [{text:'����', value:'='},
                                      {text:'������', value:'='},
                                      {text:'����', value:'='},
                                      {text:'���ڵ���', value:'='},
                                      {text:'С��', value:'='},
                                      {text:'С�ڵ���', value:'='}]
   
   * 
   * */                                      
var queryCondition = function(config){	
    if (!config.id){
        alert("û�д��������ID������json���ò���id���ݹ�����");
        return;
    }
    
    if (!config.parentContainer){
        alert("û�ж��常������ID������json����parentContainer�������ݹ�����");
        return;
    }
    
    if (!config.getColumnSrcPrefix){
        alert("û�д����ȡ�ֶ��б��URLǰ׺������json���ò���getColumnSrcPrefix���ݹ�����");
        return;
    }
    
    this.id = config.id;
    this.src = config.getColumnSrcPrefix;
    this.getCodeSrc = config.getCodeSrcPrefix ? config.getCodeSrcPrefix : "txn60210012.ajax?select-key:jc_dm_id="
    
    if (document.getElementById(this.id + "Condition")){
        alert("���ơ�" + this.id + "���Ѿ����ڣ������");
        return;
    }
    
    this.queryConditionHtml =   '  <table width="95%" border="0" align="center" id="' + this.id + 'Container" cellpadding="0" cellspacing="0" class="frame-body">' +
                                    '     <tr class="title-row">' + 
                                    '       <td><table cellspacing="0" cellpadding="0" width="100%"><tr><td class="leftTitle"></td><td class="secTitle">���ݱ��ѯ����</td><td class="rightTitle"></td></tr></table></td>' + 
                                    '     </tr>' + 
                                    '     <tr><td width="100%"><table id="' + this.id + '_topTopTable" style="border:2px solid #006699;border-collapse:collapse;" cellspacing="0" cellpadding="0" width="100%">' +
                                    '     <tr class="grid-headrow" align="center">' + 
                                    '       <td width="7%">����</td>' + 
                                    '       <td width="6%">����</td>' + 
                                    '       <td width="20%">���ݱ�</td>' + 
                                    '       <td width="20%">���ֶ�</td>' + 
                                    '       <td width="10%">��ѯ����</td>' + 
                                    '       <td >����ֵ</td>' + 
                                    '       <td width="6%">����</td>' + 
                                    '       <td width="7%">����</td>' + 
                                    '     </tr>' + 
                                    '     <tr class="framerow selectRow">' + 
                                    '       <td><select name="' + this.id + 'Condition" id="' + this.id + 'Condition" class="queryConditionSelect" disabled="true">' + 
                                    '         <option selected="selected"></option>' + 
                                    '         <option value="AND">����</option>' + 
                                    '         <option value="OR">����</option>' + 
                                    '       </select></td>' + 
                                    '       <td><select name="' + this.id + 'PreParen" id="' + this.id + 'PreParen" class="queryConditionSelect">' + 
                                    '         <option selected="selected"></option>' + 
                                    '         <option value="(">(</option>' + 
                                    '         <option value="((">((</option>' + 
                                    '         <option value="(((">(((</option>' + 
                                    '         <option value="((((">((((</option>' + 
                                    '         <option value="(((((">(((((</option>' + 
                                    '       </select></td>' + 
                                    '       <td><select name="' + this.id + 'TableGhost" id="' + this.id + 'TableGhost" class="connectConditionSelect" style="display:none"></select>' + 
                                    '<select name="' + this.id + 'TableOne" id="' + this.id + 'TableOne" class="queryConditionSelect"></select></td>' + 
                                    '       <td><select name="' + this.id + 'ColumnOne" id="' + this.id + 'ColumnOne" class="queryConditionSelect"></select></td>' + 
                                    '       <td><select name="' + this.id + 'RelationString" id="' + this.id + 'RelationString" class="queryConditionSelect" style="display:none"><OPTION value="=" selected postfix="\'" prefix="\'">����</OPTION><OPTION value="<>" postfix="\'" prefix="\'">������</OPTION><OPTION value="like" postfix="%\'" prefix="\'">��ʼ��</OPTION><OPTION value="like" postfix="\'" prefix="\'%">������</OPTION><OPTION value="like" postfix="%\'" prefix="\'%">����</OPTION><OPTION value="not like" postfix="%\'" prefix="\'%">������</OPTION><OPTION value="IS" >Ϊ��</OPTION><OPTION value="IS NOT" >��Ϊ��</OPTION></select>' + 
                                    '<select name="' + this.id + 'RelationNumber" id="' + this.id + 'RelationNumber" class="queryConditionSelect" style="display:none"><OPTION value="=" selected>����</OPTION><OPTION value="<>">������</OPTION><OPTION value=">">����</OPTION><OPTION value=">=">���ڵ���</OPTION><OPTION value="<">С��</OPTION><OPTION value="<=">С�ڵ���</OPTION></select>' +
                                    '<select name="' + this.id + 'RelationCode" id="' + this.id + 'RelationCode" class="queryConditionSelect" style="display:none"><OPTION value="=" selected postfix="\'" prefix="\'">����</OPTION><OPTION value="<>" postfix="\'" prefix="\'">������</OPTION><OPTION value=">" postfix="\'" prefix="\'">����</OPTION><OPTION value=">=" postfix="\'" prefix="\'">���ڵ���</OPTION><OPTION value="<" postfix="\'" prefix="\'">С��</OPTION><OPTION value="<=" postfix="\'" prefix="\'">С�ڵ���</OPTION><OPTION value="IS" >Ϊ��</OPTION><OPTION value="IS NOT" >��Ϊ��</OPTION></select>' + 
                                    '<select name="' + this.id + 'RelationDate" id="' + this.id + 'RelationDate" class="queryConditionSelect" style="display:none"><OPTION value="=" selected postfix="\'" prefix="\'">����</OPTION><OPTION value="<>" postfix="\'" prefix="\'">������</OPTION><OPTION value=">" postfix="\'" prefix="\'">����</OPTION><OPTION value=">=" postfix="\'" prefix="\'">���ڵ���</OPTION><OPTION value="<" postfix="\'" prefix="\'">С��</OPTION><OPTION value="<=" postfix="\'" prefix="\'">С�ڵ���</OPTION><OPTION value="IS" >Ϊ��</OPTION><OPTION value="IS NOT" >��Ϊ��</OPTION></select>' +  
                                    '</td>' + 
                                    '       <td><select name="' + this.id + 'ParamValueSelect" id="' + this.id + 'ParamValueSelect" class="queryConditionSelect" style="display:none"></select>' + 
                                    '<input type="text" id="' + this.id + 'ParamValueInput" style="display:none" class="queryConditionInput" />' + 
                                    '<input type="text" id="' + this.id + 'ParamValueDateSelect" style="display:none" class="queryConditionInput" onclick="calendar(this, 1)"/></td>' + 
                                    '       <td><select name="' + this.id + 'PostParen" id="' + this.id + 'PostParen" class="queryConditionSelect">' + 
                                    '          <option selected="selected"></option>' + 
                                    '          <option value=")">)</option>' + 
                                    '          <option value="))">))</option>' + 
                                    '          <option value=")))">)))</option>' + 
                                    '          <option value="))))">))))</option>' + 
                                    '          <option value=")))))">)))))</option>' + 
                                    '       </select></td>' + 
                                    '       <td><table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td><input type="button" id="'+ this.id + 'AddButton" name="'+ this.id + 'AddButton" value="���" class="menu" /></td><td class="btn_right"></td></tr></table></td>' + 
                                    '     </tr>' + 
                                    '		</table></td></tr>' +  
                                    '     <tr id="'+ this.id +'hideRow" style="display:none;padding:0px;" class="framerow">'+
                                    '       <td>'+
                                    '           <table id="conditionTbl_'+ this.id +'" border="0" cellpadding="0" cellspacing="0" width="100%" style="border:2px solid #006699;border-top:none;"></table>'+
                                    '       </td>'+
                                    '     </tr>'+
                                    '   </table>';
    document.getElementById(config.parentContainer).innerHTML = this.queryConditionHtml;
    
    document.getElementById(this.id + "_topTopTable").onresize = function(){
    	if (this.offsetWidth < 600 && this.offsetWidth != 0){
    		this.style.width = 600;
    	}
    }
    
    setShowTitleSelectList(this.id + "TableOne", this.id + "ColumnOne", this.id + "ParamValueSelect", false);
    
    // ����������������޸�����ѡ������չ��ʱһ���Բ�����
    // �ַ���
    if (config.compCond_String){
    	var length = config.compCond_String.length;
    	if (length > 0){
    		var RelationString = document.getElementById(this.id + 'RelationString');
    		RelationString.innerHTML = "";
    		for (var i = 0; i < length; i++){
    			var tempOption = document.createElement("option");
                RelationString.appendChild(tempOption);
    			tempOption.text = config.compCond_String[i].text;
    			tempOption.value = config.compCond_String[i].value;
    			if(config.compCond_String[i].prefix){
    				tempOption.prefix = config.compCond_String[i].prefix;
    			}else{
    				tempOption.prefix = "";
    			}
    			if(config.compCond_String[i].postfix){
    				tempOption.postfix = config.compCond_String[i].postfix;
    			}else{
    				tempOption.postfix = "";
    			}
    			tempOption = null;
    		}
    		RelationString = null;
    	}
    }
    
    // ����
    if (config.compCond_Code){
        var length = config.compCond_Code.length;
        if (length > 0){
            var RelationCode = document.getElementById(this.id + 'RelationCode');
            RelationCode.innerHTML = "";
            for (var i = 0; i < length; i++){
                var tempOption = document.createElement("option");
                RelationCode.appendChild(tempOption);
                tempOption.text = config.compCond_Code[i].text;
                tempOption.value = config.compCond_Code[i].value;
                if(config.compCond_Code[i].prefix){
                    tempOption.prefix = config.compCond_Code[i].prefix;
                }else{
                    tempOption.prefix = "";
                }
                if(config.compCond_Code[i].postfix){
                    tempOption.postfix = config.compCond_Code[i].postfix;
                }else{
                    tempOption.postfix = "";
                }
                tempOption = null;
            }
            RelationCode = null;
        }
    }
    // ����
    if (config.compCond_Number){
        var length = config.compCond_Number.length;
        if (length > 0){
            var RelationNumber = document.getElementById(this.id + 'RelationNumber');
            RelationNumber.innerHTML = "";
            for (var i = 0; i < length; i++){
                var tempOption = document.createElement("option");
                RelationNumber.appendChild(tempOption);
                tempOption.text = config.compCond_Number[i].text;
                tempOption.value = config.compCond_Number[i].value;
                if(config.compCond_Number[i].prefix){
                    tempOption.prefix = config.compCond_Number[i].prefix;
                }else{
                    tempOption.prefix = "";
                }
                if(config.compCond_Number[i].postfix){
                    tempOption.postfix = config.compCond_Number[i].postfix;
                }else{
                    tempOption.postfix = "";
                }
                tempOption = null;
            }
            RelationNumber = null;
        }
    }
    // ����
    if (config.compCond_Date){
        var length = config.compCond_Date.length;
        if (length > 0){
            var RelationDate = document.getElementById(this.id + 'RelationDate');
            RelationDate.innerHTML = "";
            for (var i = 0; i < length; i++){
                var tempOption = document.createElement("option");
                RelationDate.appendChild(tempOption);
                tempOption.text = config.compCond_Date[i].text;
                tempOption.value = config.compCond_Date[i].value;
                if(config.compCond_Date[i].prefix){
                    tempOption.prefix = config.compCond_Date[i].prefix;
                }else{
                    tempOption.prefix = "";
                }
                if(config.compCond_Date[i].postfix){
                    tempOption.postfix = config.compCond_Date[i].postfix;
                }else{
                    tempOption.postfix = "";
                }
                tempOption = null;
            }
            RelationDate = null;
        }
    }
    // ���¼�
    this.addEvent();
    // ��ʼ��(û�����ݣ���ʱ����ʼ��)
    // this.init();
    
	this.connArray = new Array();
	this.connJSONArray = new Array();
}

// ����¼�
queryCondition.prototype.addEvent = function(){
    var p = this;
    // ѡ���ʱ
    var tableOne = document.getElementById(this.id + "TableOne");
    if (tableOne){
        tableOne.onchange = function(){
            p.getColumns(this.options[this.selectedIndex].value, p.id + "ColumnOne");
        };
    }
    tableOne = null;
    
    // ѡ���ֶ�ʱ
    var ColumnOne = document.getElementById(p.id + "ColumnOne");
    
    if (ColumnOne){
        ColumnOne.onchange = function(){
            var columnType=1;
            //alert(this.options.length+'---'+this.selectedIndex);
            if(this.options.length>0){
              columnType = this.options[this.selectedIndex].type;
              var codeValue = this.options[this.selectedIndex].codeValue;
            }
            // alert(columnType + ", " + codeValue);
            var paramValueInput = document.getElementById(p.id + "ParamValueInput");
            var paramValueSelect = "";
            if(document.getElementById(p.id + "ParamValueSelect")){
              paramValueSelect=document.getElementById(p.id + "ParamValueSelect");
            }
            //var paramValueSelect = document.getElementById(p.id + "ParamValueSelect");
            var paramValueDateSelect = document.getElementById(p.id + "ParamValueDateSelect");
            
            paramValueInput.value = "";
            paramValueSelect.innerHTML = "";
            paramValueDateSelect.value = "";
            
            var RelationString = document.getElementById(p.id + "RelationString");
            var RelationNumber = document.getElementById(p.id + "RelationNumber");
            var RelationCode = document.getElementById(p.id + "RelationCode");
            var RelationDate = document.getElementById(p.id + "RelationDate");
            /**
             * columnType:
             * 1. �ַ���
             * 2. ����
             * 3. ����
             * codeValue:
             * ��columnTypeΪ1��codeValue��ֵʱ��Ϊ���
             */
            if (columnType == 1){ // �ַ���
            	if (codeValue){//���
            		paramValueInput.style.display = "none";
            		paramValueSelect.style.display = "block";
            		paramValueDateSelect.style.display = "none";
            		RelationString.style.display = "none";
            		RelationCode.style.display = "block";
            		RelationDate.style.display = "none";
            		RelationNumber.style.display = "none";
            		
            		paramValueSelect.innerHTML = "";
            		var option = document.createElement("option");
            		paramValueSelect.appendChild(option);
            		option.text = "���ݶ�ȡ��...";
            		option = null;
            		$.get(p.getCodeSrc + codeValue, function(xml){
            			fillXmlToSelect(xml, "record", p.id + "ParamValueSelect", "jcsjfx_dm", "jcsjfx_mc");
            		});
            	}else{// ��ͨ�ַ���
            		paramValueInput.style.display = "block";
                    paramValueSelect.style.display = "none";
                    paramValueDateSelect.style.display = "none";
                    RelationString.style.display = "block";
                    RelationCode.style.display = "none";
                    RelationDate.style.display = "none";
                    RelationNumber.style.display = "none";
            	}
            }else if (columnType == 2){ // ����
            	paramValueInput.style.display = "none";
                paramValueSelect.style.display = "none";
                paramValueDateSelect.style.display = "block";
                RelationString.style.display = "none";
                RelationCode.style.display = "none";
                RelationDate.style.display = "block";
                RelationNumber.style.display = "none";
            }else if (columnType == 3){ // ����
                paramValueInput.style.display = "block";
                paramValueSelect.style.display = "none";
                paramValueDateSelect.style.display = "none";
                RelationString.style.display = "none";
                RelationCode.style.display = "none";
                RelationDate.style.display = "none";
                RelationNumber.style.display = "block";
            }else{// ��������
            	paramValueInput.style.display = "none";
                paramValueSelect.style.display = "none";
                paramValueDateSelect.style.display = "none";
                RelationString.style.display = "none";
                RelationCode.style.display = "none";
                RelationDate.style.display = "none";
                RelationNumber.style.display = "none";
            }
            paramValueInput = null;
            paramValueSelect = null;
            paramValueDateSelect = null;
            RelationString = null;
            RelationCode = null;
            RelationNumber = null;
            RelationDate = null;
        };
    }
    ColumnOne = null;
    
    // ����¼�
    var addButton = document.getElementById(this.id + "AddButton");
    if (addButton){
        addButton.onclick = function(){
        	p.addQueryCondition();
        }
    }
};

// ��ʼ��
queryCondition.prototype.init = function(){
	var TableGhost = document.getElementById(this.id + "TableGhost");
	var options = TableGhost.options;
    if (options.length > 0){
    	document.getElementById(this.id + "Container").style.display = "block";
    	var TableOne = document.getElementById(this.id + "TableOne");
    	TableOne.innerHTML = "";
    	for (var i = 0; i < options.length; i++){
    		TableOne.appendChild(options[i].cloneNode(true));
    	}
    	document.getElementById(this.id + "TableOne").onchange();
    	TableOne = null;
    }else{
    	document.getElementById(this.id + "Container").style.display = "none";
    	document.getElementById(this.id + "TableOne").innerHTML = "";
    	document.getElementById(this.id + "ColumnOne").innerHTML = "";
    	document.getElementById(this.id + "ParamValueSelect").innerHTML = "";
    	document.getElementById(this.id + "ParamValueInput").value = "";
    	document.getElementById(this.id + "ParamValueDateSelect").value = "";
    }
    TableGhost = null;
}

// ����ѡ�����ݱ�
queryCondition.prototype.setDataFromSelect = function(selectId){
    var TableGhost = document.getElementById(this.id + "TableGhost");
    TableGhost.innerHTML = "";
    var options = document.getElementById(selectId).options;

    for (var i = 0; i < options.length; i++){
        TableGhost.appendChild(options[i].cloneNode(true));
    }
    
    // ִ����Ϻ��ٴ�ִ�г�ʼ��
    this.init();
}

// ����ѡ�����ݱ� jufeng 2012-06-28
queryCondition.prototype.setDataFromTemp = function(temp){
	if(temp!=null&&temp.length>0){
	    var TableGhost = document.getElementById(this.id + "TableGhost");
        TableGhost.innerHTML = "";
		for(var j=0;j<temp.length; j++){
			  var aOption = document.createElement('OPTION');
			  aOption.value=temp[j].tblNo;
			  aOption.innerText=temp[j].tblNameCn;
			  aOption.title=temp[j].tblNameCn;
			  aOption.tblName=temp[j].tblName;
			  TableGhost.appendChild(aOption);
		}
	}
    // ִ����Ϻ��ٴ�ִ�г�ʼ��
    this.init();
}

// ����ѡ�����ݱ� jufeng 2012-06-28
queryCondition.prototype.updateDataFromTemp = function(temp,tempOld){

    var TableGhost = document.getElementById(this.id + "TableGhost");
    var addTemp = new Array;
    var delTemp = new Array;
    var temp2 = new Array;
    var tempOld2 = new Array;
    if(temp!=null&&temp.length>0&&tempOld!=null&&tempOld.length>0){
    	for(var j=0; j<temp.length; j++){
    		temp2.push( temp[j].tblNo );
    	}
    	for(var j=0; j<tempOld.length; j++){
    		tempOld2.push( tempOld[j].tblNo );
    	}
    //��������ǰ���Ҳ����� �Ȳ�������
       /*
        var tempOldStr = tempOld2.join('-');
	    for(var j=0;j<temp.length; j++){
	    	if(tempOldStr.indexOf(temp[j].tblNo)<0){
	    		addTemp.push(temp[j].tblNo);
	    	}	
	    }
	    */
	//��ǰ�����������Ҳ����� ��Ҫɾ�����ϵͳ����
	    var tempStr = temp2.join('-');
	    for(var j=0;j<tempOld.length; j++){
	    	if(tempStr.indexOf(tempOld[j].tblNo)<0){
	    		delTemp.push(tempOld[j].tblNo);
	    	}	
	    }
	//�������ڵ�seletedTable����ѯ��
	    TableGhost.innerHTML = "";
		for(var j=0;j<temp.length; j++){
			  var aOption = document.createElement('OPTION');
			  aOption.value=temp[j].tblNo;
			  aOption.innerText=temp[j].tblNameCn;
			  aOption.title=temp[j].tblNameCn;
			  aOption.tblName=temp[j].tblName;
			  TableGhost.appendChild(aOption);
		}
	
	if(delTemp.length>0){
		this.delConnConditionOfRemovedTables(delTemp);
	}
	}
	this.init();
}

// ��ȡ���µ��ֶ��б�
queryCondition.prototype.getColumns = function(tableNo, columnSelectId){
    var p = this;
    var columnSelect = document.getElementById(columnSelectId);
    columnSelect.innerHTML = "";
    var tempOption = document.createElement("option");
    columnSelect.appendChild(tempOption);
    tempOption.text = "���ݶ�ȡ��...";
    tempOption = null;
    columnSelect = null;
    
    $.get(p.src + tableNo, function(xml){
        fillXmlToSelect(xml, "record", columnSelectId, "column_no", "column_name_cn", {type: "edit_type", codeValue: "demo", colName:"column_name"});
        document.getElementById(p.id + "ColumnOne").onchange();
    });
}

// ��ò�ѯ��ϵ
queryCondition.prototype.getRelationSelect = function(){
	var RelationString		= document.getElementById(this.id + "RelationString");
	var RelationNumber		= document.getElementById(this.id + "RelationNumber");
	var RelationCode		= document.getElementById(this.id + "RelationCode");
	var RelationDate		= document.getElementById(this.id + "RelationDate");
	var relationDom = null;
	if (RelationString.style.display == 'block'){
		relationDom = RelationString;
	}else if (RelationNumber.style.display == 'block'){
		relationDom = RelationNumber;
	}else if(RelationCode.style.display == 'block'){
		relationDom = RelationCode;
	}else if(RelationDate.style.display == 'block'){
		relationDom = RelationDate;
	}
	
	RelationString = null;
	RelationNumber = null;
	RelationCode = null;
	RelationDate = null;
	return relationDom;
}

// ��ò���ֵ
queryCondition.prototype.getParam = function(){
	var ParamValueSelect		= document.getElementById(this.id + "ParamValueSelect");
	var ParamValueInput			= document.getElementById(this.id + "ParamValueInput");
	var ParamValueDateSelect	= document.getElementById(this.id + "ParamValueDateSelect");
	
	var relationSelect = this.getRelationSelect();
	if (!relationSelect){
		return null;
	}
	
	var selectedOption = relationSelect.options[relationSelect.selectedIndex];
	
	var paramString		= selectedOption.prefix ? selectedOption.prefix : "";
	var displayString	= "";
	var userInputString = selectedOption.prefix ? selectedOption.prefix : "";
	
	if (ParamValueSelect.selectedIndex >= 0){
		paramString		+= ParamValueSelect.options[ParamValueSelect.selectedIndex].value;
		displayString	+= ParamValueSelect.options[ParamValueSelect.selectedIndex].text;
	}else{
		paramString		+= ParamValueInput.value + ParamValueDateSelect.value;
		displayString	+= ParamValueInput.value + ParamValueDateSelect.value; 
	}
	
	userInputString += "?";
	
	paramString			+= selectedOption.postfix ? selectedOption.postfix : "";
	displayString		+= "";
	userInputString		+= selectedOption.postfix ? selectedOption.postfix : "";
	
	relationSelect = null;
	ParamValueSelect = null;
	ParamValueInput = null;
	ParamValueDateSelect = null;
	var regExp = /[~\\\?'"\^\*]/;
	if(regExp.test(displayString)){
		alert("������ֵ���к��в��Ϸ����ַ���" + displayString.match(regExp) + "�������������룡");
		return false;
	}

	var jsonObject = '({value:"' + paramString + '", displayValue:"' + displayString + 
		'", userInputString :"' + userInputString + '"})';
	return eval(jsonObject);
}

// ���һ����������
queryCondition.prototype.addQueryCondition = function(){ 
	document.getElementById("qct_topTopTable").style.borderBottomWidth = '0px';
	var p = this;
    var conditionSelect		= document.getElementById(this.id + "Condition");	// ����������������� AND
    var preParenSelect		= document.getElementById(this.id + "PreParen");	// ���������������
    var tableOneSelect		= document.getElementById(this.id + "TableOne");	// ���ݱ����������
    var columnOneSelect		= document.getElementById(this.id + "ColumnOne");	// �ֶ����������
    var relationSelect		= this.getRelationSelect();							// �Ƚ��������������
    var postParenSelect		= document.getElementById(this.id + "PostParen");	// ���������������
    // �Ƚϲ����������µõ�, ��ʾ�ı�paramJson.displayValue, ƴ�õ��ַ���paramJson.value
    
    var paramJson = this.getParam();
    if (!paramJson){
    	return;
    }
    if(relationSelect.value=='IS' || relationSelect.value=='IS NOT'){
       paramJson.displayValue="";
       paramJson.value=" NULL";
    }else{
	    if(paramJson.displayValue.trim() == ""){
			alert("�����롾����ֵ����");
			return;
		}
	}
	
	// ��������ַ�
	/*var expStrArray = new Array("����","������","�Ƽ�","����","���޹�","���޹�˾","�޹�˾","��˾",
			"����","����","����","����","����","����","��̨","ʯ��ɽ","��ͷ��",
			"��ɽ","ͨ��","˳��","����","��ƽ","ƽ��","����","����","����","������","������",
			"������","������","������","������","��̨��","ʯ��ɽ��","��ͷ����","��ɽ��","ͨ����",
			"˳����","������","��ƽ��","ƽ����","������","������","������","����","����","����",
			"����","����","̨��","��ɽ��","ɽ��","ͷ����","����","ɽ��","����","����","����",
			"ƽ��","����","����","����","����","�ӱ�ʡ","ɽ��ʡ","����ʡ","����ʡ","������ʡ",
			"����ʡ","�㽭ʡ","����ʡ","����ʡ","����ʡ","ɽ��ʡ","����ʡ","����ʡ","����ʡ",
			"�㶫ʡ","����ʡ","�Ĵ�ʡ","����ʡ","����ʡ","����ʡ","����ʡ","�ຣʡ","̨��ʡ",
			"���ɹ�","����","����","����","�½�","�����","�Ϻ���","������","�ӱ�","ɽ��","����",
			"����","������","����","�㽭","����","����","����","ɽ��","����","����","����","�㶫",
			"����","�Ĵ�","����","����","����","����","�ຣ","̨��","���","�Ϻ�","����","%","����");
	*/
	var expStrArray = new Array("%");
	var temp;
	for(temp in expStrArray){
		if(paramJson.displayValue.trim() == expStrArray[temp]){
			alert("��ʾ����������ȷ����Ϣ���ѯ");
			return false;
		}
	}
	
	var content = "<font color=red>"+conditionSelect.options[conditionSelect.selectedIndex].text + " </font>" 
			    + "<font color=red>" + preParenSelect.options[preParenSelect.selectedIndex].text + " </font>" 
			    + tableOneSelect.options[tableOneSelect.selectedIndex].text + " �� "
			    + columnOneSelect.options[columnOneSelect.selectedIndex].text + " "
			    + "<font color=red>" + relationSelect.options[relationSelect.selectedIndex].text + " </font>"
				+ paramJson.displayValue + " "
				+ "<font color=red>" + postParenSelect.options[postParenSelect.selectedIndex].text + " </font>";
	
	
	//����������	      
	var connJSONObj = {condition    :conditionSelect.options[conditionSelect.selectedIndex].value,
					   preParen     :preParenSelect.options[preParenSelect.selectedIndex].value,
					   tableOneId   :tableOneSelect.options[tableOneSelect.selectedIndex].value,
					   tableOneName :tableOneSelect.options[tableOneSelect.selectedIndex].tblName,
					   tableOneNameCn :tableOneSelect.options[tableOneSelect.selectedIndex].text,
					   columnOneId  :columnOneSelect.options[columnOneSelect.selectedIndex].value,
					   columnOneName:columnOneSelect.options[columnOneSelect.selectedIndex].colName,
					   columnOneNameCn:columnOneSelect.options[columnOneSelect.selectedIndex].text,
					   relation     :relationSelect.options[relationSelect.selectedIndex].value,
					   paramValue   :paramJson.value,
					   userInputString :paramJson.userInputString,
					   postParen    :postParenSelect.options[postParenSelect.selectedIndex].value,
					   paramText    :content};
	//return;     
	//�ж��Ƿ�Ϊ�ظ�����
	if(p.exist(connJSONObj)){
		return;
	}else{
		addToArray(p.connJSONArray, connJSONObj);//��ӵ�"����"
	}
	
	var rowIndex = 0;
	var objTable = document.getElementById("conditionTbl_" + p.id);
	var newTr = objTable.insertRow();
	newTr.height = 30;

	//�����
	var newTd0 = newTr.insertCell();
	var newTd1 = newTr.insertCell();
	
	//��������������������������ø��л�ɫ
	var rows = document.getElementById("conditionTbl_qct").rows.length;
	if(rows%2 == 0){
		newTd0.className = "odd1";
		newTd1.className = "odd1";
	}else{
		newTd0.className = "odd2";
		newTd1.className = "odd2";
	}

	//���������ݺ�����
	newTd0.innerHTML = content; 
	newTd0.width = "90%";
	newTd0.align = "left";
	newTd1.innerHTML= "<table cellspacing='0' cellpadding='0' class='button_table'><tr><td class='btn_left'></td><td><input type='button' id='deleteConnCondition_" + p.id + "' value=' ɾ�� ' class='menu' tblNo='"+connJSONObj.tableOneId+"' /></td><td class='btn_right'></td></tr></table>";
	newTd1.align = "center";
	
	document.getElementById(p.id + "hideRow").style.display = "block";
	
	rowIndex ++;
	
	var delButton = document.getElementsByName("deleteConnCondition_"+p.id);
    var obj = delButton[delButton.length - 1];
    if (obj){
    	obj.onclick = function(){
    		if(confirm("ȷ��ɾ����������")){
	    		p.deleteCondition(this.parentNode.parentNode.rowIndex);
    		}
    	};
    }
    
    if(p.connJSONArray.length != 0){
    	conditionSelect.options.length = 0;
    	conditionSelect.options.add(createOption('AND','����'));
    	conditionSelect.options.add(createOption('OR','����'));
    	conditionSelect.disabled = false;
    }
    
    obj             = null;
    delButton       = null;
    conditionSelect = null;
    preParenSelect  = null;
    tableOneSelec   = null;
    columnOneSelect = null;
    relationSelect  = null;
    postParenSelect = null;
}

queryCondition.prototype.deleteCondition = function(idx){
	var p = this;
	deleteOneRow("conditionTbl_" + p.id, idx);
	var objTable = document.getElementById("conditionTbl_" + p.id);
	if(objTable){
		if(objTable.rows.length == 0){//�����ʾ�����ı��û����ʱ�����������
			objTable.parentNode.parentNode.style.display = "none";
			document.getElementById("qct_topTopTable").style.borderBottomWidth = '2px';
		}
	}
	this.connJSONArray.splice(idx, 1);
	
	var con1 = document.getElementById(p.id + "Condition");
	if(p.connJSONArray.length == 0){
    	con1.options.length = 0;
    	con1.appendChild(createOption('',''));
    	con1.className = "connectConditionSelect";
    	//con1.options[0].selected = true;
    	con1.disabled = true;
    }
    
     //ȥ����һ��������AND����OR
    var regExp = /^<FONT color=[\"\']?red[\"\']?>(����||����) <\/FONT>/;
    if(p.connJSONArray[0] && regExp.test(objTable.rows[0].cells[0].innerHTML)){
		p.connJSONArray[0].condition = "";
		var newContent = p.connJSONArray[0].paramText.replace(/^<FONT color=[\"\']?red[\"\']?>\s*(����||����) <\/FONT>/i, "");
		p.connJSONArray[0].paramText = newContent;
		objTable.rows[0].cells[0].innerHTML = newContent;
    }
    
	objTable = null;
}

// ɾ��һ����������
queryCondition.prototype.delConnCondition = function(idx){
	var p = this;
	this.connJSONArray.splice(idx, 1);
}


queryCondition.prototype.getQueryConditionStr = function(idx){
	var p = this;
	var paramStr = "";
	
	if(p.connJSONArray && p.connJSONArray.length > 0){
		var connArray = p.connJSONArray;
		
		for(var i = 0; i < connArray.length; i++){
			var jsonObj = connArray[i];
			//alert("jsonObj.condition - "+ jsonObj.condition + " jsonObj.preParen "+ jsonObj.preParen);
			paramStr += jsonObj.condition +" "
					 +  jsonObj.preParen
					 +  jsonObj.tableOneName +"."
					 +  jsonObj.columnOneName + " "
					 +  jsonObj.relation + " "
					 +  jsonObj.paramValue
					 +  jsonObj.postParen +" ";
		}
	}
	return paramStr;
}

queryCondition.prototype.getQueryConditionStrWithoutValue = function(idx){
	var p = this;
	var paramStr = "";
	
	if(p.connJSONArray && p.connJSONArray.length > 0){
		var connArray = p.connJSONArray;
		
		for(var i = 0; i < connArray.length; i++){
			var jsonObj = connArray[i];
			paramStr += jsonObj.condition +" "
					 +  jsonObj.preParen
					 +  jsonObj.tableOneName +"."
					 +  jsonObj.columnOneName + " "
					 +  jsonObj.relation + " "
					 +  jsonObj.userInputString
					 +  jsonObj.postParen +" ";
		}
	}
	return paramStr;
}

/** 
 *  ������������б��id
 *  @param regex  : �����Ӳ����ַ����ķָ��ַ����硰~��
 *  @return tblIdArray: �����漰���ı��ID
 */
queryCondition.prototype.getTableId = function(){
	var p = this;
	var tblIdArray = new Array();
	if(p.connJSONArray){
		for(var i = 0; i < p.connJSONArray.length; i++){
			var connObj = p.connJSONArray[i];
			addToArray(tblIdArray, connObj.tableOneId, true);
		}
	}
	
	return tblIdArray;
}

/**
 * �ж��Ƿ��Ѿ�����ͬ������������
 * @param �����Ƚϵ�JSON����
 */
queryCondition.prototype.exist = function(obj){
	if(!obj){
		return false;
	}
	var p = this;
	for(var i = 0; i < p.connJSONArray.length; i++ ){
		if((p.connJSONArray[i].condition == obj.condition) 
		    && (p.connJSONArray[i].preParen == obj.preParen)
		    && (p.connJSONArray[i].tableOneName == obj.tableOneName) 
		    && (p.connJSONArray[i].columnOneName == obj.columnOneName) 
		    && (p.connJSONArray[i].relation == obj.relation) 
		    && (p.connJSONArray[i].paramValue.trim() == obj.paramValue.trim()) 
		    && (p.connJSONArray[i].postParen == obj.postParen) ){
			return true;
		}
	}
	
	return false;
}

/**
 * ���������������
 */
queryCondition.prototype.Clear = function(){
	var p = this;
	var condition = document.getElementById(p.id + "Condition");
	condition.innerHTML = "";
	condition.appendChild(createOption("", ""));
	condition = null;
	var rows = document.getElementById("conditionTbl_" + p.id).rows;
	for (var i = rows.length - 1; i >= 0; i--){
		document.getElementById("conditionTbl_" + p.id).deleteRow();
	}
	document.getElementById(p.id + "hideRow").style.display = "none";
	
	p.connJSONArray = new Array;
}

/**
 * ���������ȥ�����ϵͳ������
 */
queryCondition.prototype.delConnConditionOfRemovedTables = function(delTemp){
	var delA = new Array;
	var delTempStr = delTemp.join('-');
	var p = this;
	var objTable = document.getElementById("conditionTbl_" + p.id);
	var tbodyNodes = objTable.childNodes;
	
	if(delTemp!=null&&delTemp.length>0){
		if(tbodyNodes!=null&&tbodyNodes.length>0){
			var trNodes = tbodyNodes[0].childNodes
			if(trNodes!=null&&trNodes.length>0){
				for(var j=0; j<trNodes.length; j++){
					var aInput = trNodes[j].childNodes[1].childNodes[0];
					var tblNo = aInput.getAttribute("tblNo");
					if(delTempStr.indexOf(tblNo)>=0){
						delA.push(j);
					}
				}
			}
			
		}
	}
	//ɾ��ҳ����ʾ��Ϣ
	if(delA!=null&&delA.length>0){
		var a = new Array;
		for(var j=0; j<delA.length; j++){
			a.push( tbodyNodes[0].childNodes[delA[j]]);
			//alert( tbodyNodes[0].childNodes[delA[j]] )
		}
		if(a!=null&&a.length>0){
			for(var j=0; j<a.length; j++){
				tbodyNodes[0].removeChild(a[j]);
			}
		}
	}
	//alert( delA.join('-') +" \n "+ delTempStr )
	
	//ɾ������������������Ϣ
	this.removeQueryConditionFromconnJSONArray(delA);
	
	/*
	if(delA!=null&&delA.length>0){
		for(var j=0; j<delA.length; j++){
			this.delConnCondition(delA[j]);
		}
	}	
	*/
	//alert(p.connJSONArray.length)
}

//�������������������Ϣ jufeng 2012-06-27
queryCondition.prototype.removeQueryConditionFromconnJSONArray = function(temp){
	var p = this;
	if(temp!=null&&temp.length>0){
		var tempStr = '-'+temp.join('-')+'-';
		if(p.connJSONArray && p.connJSONArray.length > 0){
			var connArray = p.connJSONArray;
			var connArrayTemp = [];
			
			for(var i = 0; i < connArray.length; i++){
				if(tempStr.indexOf('-'+i+'-')<0){
					connArrayTemp.push( connArray[i] );
				}
			}
			p.connJSONArray = [];
			p.connJSONArray = connArrayTemp;
		}
	}
	//���������������ʱ���ܳ��ֵ���ǰ��AND
	if(p.connJSONArray!=null&&p.connJSONArray.length>0){
	   p.connJSONArray[0].condition = "";
	}
}
