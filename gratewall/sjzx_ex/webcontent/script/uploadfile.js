/******************
** У�鸽������
** ����1 var str:��ǰҳ��Form ����file��Ӧ�ı�ǩ��ֵ
** ����2 var captionName:��ǰҳ��Form����file��Ӧ�ı�ǩ��ʾ����,��"�ϴ�����"
** ����3 var extType:�����ϴ��ĸ������ͣ����������ö��Ÿ�������".txt,.doc,.pdf"
** ���÷�ʽʾ����<INPUT TYPE="button" Value="���Ӹ���" onclick="addNewRow('fileColName','�ϴ�����')" class="menu">
***********************/
function checkFileType(str,captionName,extType) {
	allowSubmit = false; 
	if (!str) {
		return false;
	}
	while (str.indexOf("\\") != -1){
		str = str.slice(str.indexOf("\\") + 1);
	}
	
	var typeArray = extType.split(",");
	
	if(typeArray == null || typeArray.length == 0){
		return true;
	}
	
	ext = str.slice(str.lastIndexOf(".")).toLowerCase();
	
	for (var i = 0; i < typeArray.length; i++) {
		if( typeArray[i] == ".*" || typeArray[i] == ext ){ 
			allowSubmit = true; 
			break; 
		}
	}

	if(!allowSubmit){
		alert("["+captionName+"]ֻ���ϴ�"+ extType + ",\n������ѡ����������!");
	}
	
	return allowSubmit;
}

/*****************************************
 ** �½������������
 ** ����1 var fileColName����ǰҳ��Form����file��Ӧ�ı�ǩ�������� ,�� record:file
 ** ����2 var captionName����ǰҳ��Form����file��Ӧ�ı�ǩ��ʾ���� ,�� ���ϴ�������
 ** ���÷���ʾ����<INPUT TYPE="button" Value="���Ӹ���" onclick="addNewRow('fileColName','�ϴ�����')" class="menu">
 ****************************************/
function addNewRow(fileColName,captionName,weight,notnull) {
    var colNum = 2;   //��(����ֵ1��ʼ��)ָ���кŵĵ�Ԫ�������'ɾ��'��ť
  
  	if(fileColName!=null)
  		fileColName = fileColName.trim();
  	if(weight == null || weight =="")
  		weight = "450px";
    
    var eObj = event.srcElement;
    var tr = eObj.parentElement.parentElement;
    var idx = tr.rowIndex;

    tr = tr.parentElement.insertRow(idx + 1);
    tr.bgColor = "white";
 
    var labeltd = document.createElement("td");
    labeltd.className = "framerow";
		labeltd.align = "right";
		labeltd.width = "15%";
		labeltd.height = "32";
    labeltd.vAlign = "center";
		var labelName = "label:"+fileColName;
		var redStyle =  " style='COLOR: red'";
		var redStar = "*";
		var labelInner = "<SPAN id="+labelName+" name='"+labelName+"'";

	if(notnull!=null && notnull)
		labelInner = labelInner+redStyle;		
	labelInner = labelInner +">"
	if(notnull!=null && notnull)
		labelInner = labelInner+redStar;	
	labelInner = labelInner+captionName+"&nbsp;</SPAN>";
	 
	labeltd.innerHTML = labelInner;
	
    var td = document.createElement("td");
    td.calssName = "framerow";
    td.width = "85%";
    td.colSpan = "3";
    td.align = "left";
    td.height = "32";
    td.style.paddingLeft = "4px";
    
    var file = document.createElement("input");
    file.type = "file";
    file.name = fileColName;
    file.id= fileColName;
    file.style.width = weight;
    file.contentEditable = "false";
    file.checkFlag="true";
    file.datatype = "file"
    file.fieldCaption = captionName;
    
    if(notnull!=null && notnull){
    	file.notnull = "true";
    	file.minvalue="1";
    }
  
    tr.appendChild(labeltd);
    td.appendChild(file);
    tr.appendChild(td);
    
    	//��ӱ����������ж�
	var list = eval(document.forms[0].name +'_itemList');
	list.push(new formFieldInfo(fileColName, 'file', 0, '', '', ''));
 
  	eObj.parentElement.removeChild(eObj);//��TD��ɾ��"�����еİ�ť"
   
    var tr2 = tr.parentElement.rows[idx];

   	var btn = document.createElement("input");
    btn.type = "button";
    btn.value = "ɾ ��";
    btn.style.width="60px";
    btn.className = "menu";
    btn.onclick = function() {
    	var eObj = event.srcElement;
    	var tr = eObj.parentElement.parentElement;
   		tr.parentElement.removeChild(tr);
    };
    
    tr2.cells[colNum-1].appendChild(btn); //��ָ���еĵ�Ԫ�������'ɾ��'��ť
    
    var l = document.createElement("label");
    l.innerHTML = "&nbsp;&nbsp;";
    td.appendChild(l);
    td.appendChild(eObj);//��"ɾ��"��ť��ӵ�����
}
/*****************************************
 ** �½������������ ������ǩ����Ĭ��Ϊ450px
 ** ����1 var fileColName����ǰҳ��Form����file��Ӧ�ı�ǩ�������� ,�� record:file
 ** ����2 var captionName����ǰҳ��Form����file��Ӧ�ı�ǩ��ʾ���� ,�� ���ϴ�������
 ** ���÷���ʾ����<INPUT TYPE="button" Value="���Ӹ���" onclick="addNewRow('fileColName','�ϴ�����')" class="menu">
 ****************************************/
function addNewRowExDefault(fileColName,captionName,notnull) {

	addNewRowEx(fileColName,captionName,"450px",notnull);
}

/*****************************************
 ** �½������������
 ** ����1 var fileColName����ǰҳ��Form����file��Ӧ�ı�ǩ�������� ,�� record:file
 ** ����2 var captionName����ǰҳ��Form����file��Ӧ�ı�ǩ��ʾ���� ,�� ���ϴ�������
 ** ����3 var weight����ǰҳ��Form����file��Ӧ�ı�ǩ���� ,�� ��450px��
 ** ���÷���ʾ����<INPUT TYPE="button" Value="���Ӹ���" onclick="addNewRow('fileColName','�ϴ�����')" class="menu">
 ****************************************/
 function addNewRowEx(fileColName,captionName,weight,notnull,accept) {
    var colNum = 2;   // ��(����ֵ1��ʼ��)ָ���кŵĵ�Ԫ�������'ɾ��'��ť
 	
	if (weight==null || weight=="")
	{
		weight = "450px";
	}
  	if(fileColName!=null)
  		fileColName = fileColName.trim();
    
    var eObj = event.srcElement;
    var tr = eObj.parentElement.parentElement;
    var idx = tr.rowIndex;
    var fileNum = window.document.getElementsByName(fileColName);

    tr = tr.parentElement.insertRow(idx + 1);
    tr.className = "oddrow"
    //tr.bgColor = "white";
    var labeltd = document.createElement("td");
    labeltd.className = "framerow";
	labeltd.align = "right";
	labeltd.width = "15%";
	labeltd.height = "32";
	labeltd.className="oddrow";
	var labelName = "label:"+fileColName;
	var redStyle =  " style='COLOR: red'";
	var redStar = "*";
	var labelInner = "<SPAN id="+labelName+" name='"+labelName+"'";

	if(notnull!=null && notnull)
		labelInner = labelInner+redStyle;		
	labelInner = labelInner +">"
	if(notnull!=null && notnull)
		labelInner = labelInner+redStar;	
	labelInner = labelInner+captionName+"</SPAN>";
	 
	labeltd.innerHTML = labelInner;
	
    var td = document.createElement("td");
    td.calssName = "framerow";
    td.width = "85%";
    td.colSpan = "3";
    td.align = "left";
    td.height = "32";
    td.style.paddingLeft = "4px";
    td.className="oddrow";

    var file = document.createElement("input");
    file.type = "file";
    file.name = fileColName;
    file.id= fileColName;
    file.style.width = weight;
    file.contentEditable = "false";
    file.checkFlag="true";
    file.datatype = "file"
    file.fieldCaption = captionName;
    
	if (accept!=null && accept!=""){
		file.accept = accept;
	}
    
    if(notnull!=null && notnull){
      file.validator = "Check()";
    }
  
    tr.appendChild(labeltd);
    td.appendChild(file);
    tr.appendChild(td);
    
    // ��ӱ����������ж�
	//var list = eval(document.forms[0].name +'_itemList');
	var list = eval(document.forms[0].name);
	//list.push(new formFieldInfo(fileColName, 'file', 0, '', '', ''));
 
  	eObj.parentElement.removeChild(eObj);//��TD��ɾ��"�����еİ�ť"
   
    var tr2 = tr.parentElement.rows[idx];

   	var btn = document.createElement("a");
    btn.type = "MIME";
    //btn.value = "\u5220 \u9664";
    //btn.value = "ɾ��";
    btn.style.width="28px";
    //btn.style.color="#0000FF";
    //btn.className = "t1";
    //alert("1fjmc="+document.getElementById("record:fjmc").value);
    btn.onclick = function() {
    	
    	var eObj = event.srcElement;
    	var tr = eObj.parentElement.parentElement;
   		tr.parentElement.removeChild(tr);
   		//var idx = tr.rowIndex;
   		//var table = eObj.parentElement.parentElement.parentElement;
   		//table.deleteRow(idx);
    };
    btn.onmouseover = function(){
    	this.style.background="green";
    	this.style.color = "white";
    };
    btn.onmouseout = function(){
    	this.style.background="none";
    	this.style.color = "#330099";
    };
    btn.innerHTML = "\u5220\u9664 </a>";
    
    //alert("2fjmc="+document.getElementById("record:fjmc").value);
    tr2.cells[colNum-1].appendChild(btn); //��ָ���еĵ�Ԫ�������'ɾ��'��ť  
    
    var l = document.createElement("label");
    l.innerHTML = "&nbsp;&nbsp;";
    td.appendChild(l);
    td.appendChild(eObj);//��"�����еİ�ť"��ӵ�����   
}

/*****************************************
 ** ɾ������������ ɾ������ͬʱ����һ���ϴ���ǩ��ť
 ** ����1 var fileId����ɾ������idֵ 
 ** ����2 var recordfileID����ǰҳ���ύ���洢ɾ������idֵ���ֶ� �� record:deletefileID
 ** ����3 var fileName����ɾ����������ֵ
 ** ����4 var recordfileNAME����ǰҳ���ύ���洢ɾ����������ֵ���ֶ� �� record:deletefileNAME
 ** ����5 var field����ǰҳ���ύ���洢������ǩ���ֶ� �� record:file
 ** ����6 var fieldcaption����ǰҳ���ύ���洢������ǩ���Ƶ��ֶ� �� ���ϴ�������
 ** ���÷���ʾ����<A href="#" onclick="downFile('fileId')" title="�鿴����">��������</A>
 ****************************************/
function delAndAddSingleFileDefault(fileId,recordfileID,fileName,recordfileNAME,fileColName,captionName,notnull){

	delAndAddSingleFile(fileId,recordfileID,fileName,recordfileNAME,fileColName,captionName,"450px",notnull);

}

/*****************************************
 ** ɾ������������ ɾ������ͬʱ����һ���ϴ���ǩ��ť
 ** ����1 var fileId����ɾ������idֵ 
 ** ����2 var recordfileID����ǰҳ���ύ���洢ɾ������idֵ���ֶ� �� record:deletefileID
 ** ����3 var fileName����ɾ����������ֵ
 ** ����4 var recordfileNAME����ǰҳ���ύ���洢ɾ����������ֵ���ֶ� �� record:deletefileNAME
 ** ����5 var field����ǰҳ���ύ���洢������ǩ���ֶ� �� record:file
 ** ����6 var fieldcaption����ǰҳ���ύ���洢������ǩ���Ƶ��ֶ� �� ���ϴ�������
 ** ����7 var weight����ǰҳ��Form����file��Ӧ�ı�ǩ���� ,�� ��450px��
 ** ���÷���ʾ����<A href="#" onclick="downFile('fileId')" title="�鿴����">��������</A>
 ****************************************/
function delAndAddSingleFile(fileId,recordfileID,fileName,recordfileNAME,fileColName,captionName,weight ,notnull, acceptType){
	var eObj = event.srcElement;
    var tr = eObj.parentElement.parentElement;
    var idx = tr.rowIndex;

	//ɾ������֮�����������ϴ�������ǩ
    if(confirm("��ȷ��ɾ���˸����ļ���\r\n\r\n[��ʾ] ����ʱִ��ɾ��������")){
		var IDelementName = recordfileID;
		var NAMEelementName = recordfileNAME;

        var idValue = document.getElementsByName(IDelementName)[0];
        idValue.value = fileId;
		
		var nameValue = document.getElementsByName(NAMEelementName)[0];
        nameValue.value = fileName;     

		tr1 =  tr.parentElement.insertRow(idx);
		tr1.parentElement.removeChild(tr);
      
		tr1.bgColor = "white";

		var labeltd = document.createElement("td");
	    labeltd.className = "framerow";
		labeltd.align = "right";
		labeltd.width = "15%";
		labeltd.height = "32";
		var labelName = "label:"+fileColName;
		var redStyle =  " style='COLOR: red'";
		var redStar = "*";
		var labelInner = "<SPAN id="+labelName+" name='"+labelName+"'";
	
		if(notnull!=null && notnull)
			labelInner = labelInner+redStyle;		
		labelInner = labelInner +">"
		if(notnull!=null && notnull)
			labelInner = labelInner+redStar;	
		labelInner = labelInner+captionName+"&nbsp;</SPAN>";
		 
		labeltd.innerHTML = labelInner;
		
	    var td = document.createElement("td");
	    td.calssName = "framerow";
	    td.width = "85%";
	    td.colSpan = "3";
	    td.align = "left";
	    td.height = "32";
        td.style.paddingLeft = "4px"; 
	
	    var file = document.createElement("input");
	    file.type = "file";
	    file.name = fileColName;
	    file.id= fileColName;
	    file.style.width = weight;
	    file.contentEditable = "false";
	    file.checkFlag="true";
	    file.datatype = "file"
	    file.fieldCaption = captionName;
  
	    if(notnull!=null && notnull){
	    	file.notnull = "true";
	    	file.minvalue="1";
    	}
    	
    	if(acceptType !=null ){
    		file.accept=acceptType;
    	}

	    td.appendChild(file);
	    tr1.appendChild(labeltd);
	    tr1.appendChild(td);
	
		// ��ӱ����������ж�
		var list = eval(document.forms[0].name +'_itemList');
		list.push(new formFieldInfo(fileColName, 'file', 0, '', '', ''));
         
	}
}

/*****************************************
 ** ɾ������������ ɾ������ͬʱ����һ���ϴ���ǩ��ť(�ƻ����߿�ר��)
 ** ����1 var fileId����ɾ������idֵ 
 ** ����2 var recordfileID����ǰҳ���ύ���洢ɾ������idֵ���ֶ� �� record:deletefileID
 ** ����3 var fileName����ɾ����������ֵ
 ** ����4 var recordfileNAME����ǰҳ���ύ���洢ɾ����������ֵ���ֶ� �� record:deletefileNAME
 ** ����5 var field����ǰҳ���ύ���洢������ǩ���ֶ� �� record:file
 ** ����6 var fieldcaption����ǰҳ���ύ���洢������ǩ���Ƶ��ֶ� �� ���ϴ�������
 ** ����7 var weight����ǰҳ��Form����file��Ӧ�ı�ǩ���� ,�� ��450px��
 ** 
 ** ���÷���ʾ����<A href="#" onclick="downFile('fileId')" title="�鿴����">��������</A>
 ****************************************/
function delAndAddSingleFileForJHJCK(fileId,recordfileID,fileName,recordfileNAME,fileColName,captionName,weight ,notnull, acceptType){
	var eObj = event.srcElement;
    var tr = eObj.parentElement.parentElement;
    var idx = tr.rowIndex;

	//ɾ������֮�����������ϴ�������ǩ
    if(confirm("��ȷ��ɾ���˸����ļ���\r\n\r\n[��ʾ] ����ʱִ��ɾ��������")){
		var IDelementName = recordfileID;
		var NAMEelementName = recordfileNAME;

        var idValue = document.getElementsByName(IDelementName)[0];
        idValue.value = fileId;
		
		var nameValue = document.getElementsByName(NAMEelementName)[0];
        nameValue.value = fileName;     

		tr1 =  tr.parentElement.insertRow(idx);
		tr1.parentElement.removeChild(tr);
      
		tr1.bgColor = "white";

		var labeltd = document.createElement("td");
	    labeltd.className = "framerow";
		labeltd.align = "right";
		labeltd.width = "15%";
		labeltd.height = "32";
		var labelName = "label:"+fileColName;
		var redStyle =  " style='COLOR: red'";
		var redStar = "*";
		var labelInner = "<SPAN id="+labelName+" name='"+labelName+"'";
	
		if(notnull!=null && notnull)
			labelInner = labelInner+redStyle;		
		labelInner = labelInner +">"
		if(notnull!=null && notnull)
			labelInner = labelInner+redStar;	
		labelInner = labelInner+captionName+"&nbsp;</SPAN>";
		 
		labeltd.innerHTML = labelInner;
		
	    var td = document.createElement("td");
	    td.calssName = "framerow";
	    td.width = "85%";
	    td.colSpan = "3";
	    td.align = "left";
	    td.height = "32";
        td.style.paddingLeft = "4px"; 
	
	    var file = document.createElement("input");
	    file.type = "file";
	    file.name = fileColName;
	    file.id= fileColName;
	    file.style.width = weight;
	    file.contentEditable = "false";
	    file.checkFlag="true";
	    file.datatype = "file"
	    
	    file.attachEvent("onchange", getContext);
	    file.fieldCaption = captionName;
  
	    if(notnull!=null && notnull){
	    	file.notnull = "true";
	    	file.minvalue="1";
    	}
    	
    	if(acceptType !=null ){
    		file.accept=acceptType;
    	}

	    td.appendChild(file);
	    tr1.appendChild(labeltd);
	    tr1.appendChild(td);
	
		// ��ӱ����������ж�
		var list = eval(document.forms[0].name +'_itemList');
		list.push(new formFieldInfo(fileColName, 'file', 0, '', '', ''));
         
	}
}

/*****************************************
 ** ɾ����������
 ** ����1 var fileId����ɾ������idֵ 
 ** ����2 var record����ǰҳ���ύ��������
 ** ����3 var fileIds���������б����ɾ������idֵ����������
 ** ���÷���ʾ����<INPUT TYPE="button" VALUE=" ɾ�� " onclick="delFile('fileid','record','fileIds')" title="ɾ������" class="menu">
 ****************************************/
function delFile(fileId,record,fileIds){
    if(confirm("��ȷ��ɾ���˸����ļ���\r\n\r\n[��ʾ] ����ʱִ��ɾ��������")){
		var elementName = record+":"+fileIds;

        var fileIds = document.getElementsByName(elementName)[0];
        if(fileIds.value.length>0){
            fileIds.value = fileIds.value + "," + fileId;
        }else{
            fileIds.value = fileId;
        }
        
        var eObj = event.srcElement;
        var tr = eObj.parentElement.parentElement;

        tr.parentElement.removeChild(tr);
    }
}


/*****************************************
 ** �฽��ɾ���������� ֻɾ���������������ϴ���ǩ�������Ϊ�ֺš�;��
 ** ����1 var fileId����ɾ������idֵ 
 ** ����2 var recordfileId����ǰҳ���ύ���洢ɾ������idֵ���ֶ� �� record:deletefileID
 ** ����3 var fileName����ɾ����������ֵ
 ** ����4 var recordfileName����ǰҳ���ύ���洢ɾ����������ֵ���ֶ� �� record:deletefileNAME
 ** ���÷���ʾ����<INPUT TYPE="button" VALUE=" ɾ�� " onclick="delFile('fileid','record','fileIds')" title="ɾ������" class="menu">
 ****************************************/
function delMultiFile(fileId,recordfileId,fileName,recordfileName){
    //if(confirm("1111\u60A8\u786E\u8BA4\u5220\u9664\u6B64\u9644\u4EF6\u6587\u4EF6\uFF1F[\u63D0\u793A] \u4FDD\u5B58\u65F6\u6267\u884C\u5220\u9664\u64CD\u4F5C\u3002")){
    if(confirm("��ȷ��ɾ���˸����ļ�?\r\n\r\n[��ʾ]����ʱִ��ɾ��������")){
		var IDelementName = recordfileId;
		var NAMEelementName = recordfileName;

        var fileIds = document.getElementsByName(IDelementName)[0];
        
        
        //alert("fileIds="+fileIds);
        if(fileIds.value.length>0){
        //var id = getFormFieldValue(recordfileId);
        //id = id + "," + fileId;
        //setFormFieldValue(recordfileId,id);
            fileIds.value = fileIds.value + "," + fileId;
            //alert("1-iddel="+fileIds.value);
        }else{
        
        	//setFormFieldValue(recordfileId,fileId);
            fileIds.value = fileId;
           // alert("2-iddel="+fileIds.value);
        }

		var fileNames = document.getElementsByName(NAMEelementName)[0];
		if(fileNames.value.length>0){
            fileNames.value = fileNames.value + "," + fileName;
        }else{
            fileNames.value = fileName;
        }
        var eObj = event.srcElement;
        var tr = eObj.parentElement.parentElement;
   
        tr.parentElement.removeChild(tr);
        if(document.getElementById(fileId)){
        	document.getElementById(fileId).style.display="none";
        }
        
    }
}

////////////// 2007-09-11yumg���Ӷ฽������У��:true-��ֵ;false-��ֵ
////////////// 2007-12-05yumg���Ӷ฽���ϴ�����У��
function checkNotnull(fileColName,captionName){
  var mulfile = window.document.getElementsByName(fileColName);
  var allowSubmit = false;
  
  for(var i = 0; i < mulfile.length;i++){
    if(mulfile[i].value.trim() != ""){
	  if (mulfile[i].accept!=null && mulfile[i].accept!=""){
		if(checkFileType(mulfile[i].value.trim(),captionName,mulfile[i].accept)==false){
		   return false;
		}
	  }
	  
      allowSubmit = true;
    }
    
  }
  
  if(!allowSubmit){
    alert("["+captionName+"]����Ϊ�գ�");
  }
  
  return allowSubmit;  
}

/////////2007-09-08yumg����
/*****************************************
 ** ����฽��ɾ���������� ֻɾ���������������ϴ���ǩ�������Ϊ�ֺš�;��
 ** ����1 var fileId����ɾ������idֵ 
 ** ����2 var recordfileId����ǰҳ���ύ���洢ɾ������idֵ���ֶ� �� record:deletefileID
 ** ����3 var fileName����ɾ����������ֵ
 ** ����4 var recordfileName����ǰҳ���ύ���洢ɾ����������ֵ���ֶ� �� record:deletefileNAME
 ** ����5 var mulfileName��ɾ����ť��name �� mulfile
 ** ����6 var fileColName��������ǩid     �� record:file
 ** ����7 var captionName��������ǩ����   �� ����
 ** ����8 var btnId�����Ӹ�����ťid       �� addfile
 ** ����9 var weight�������Ӹ����Ŀ��    �� 82%
 ** ���÷���ʾ����<INPUT TYPE="button" VALUE=" ɾ�� " onclick="delFile('fileid','record','fileIds')" title="ɾ������" class="menu">
 ****************************************/
function delMultiNotnullFile(fileId,recordfileId,fileName,recordfileName,mulfileName,fileColName,captionName,btnId,weight){
    if(confirm("��ȷ��ɾ���˸����ļ���\r\n\r\n[��ʾ] ����ʱִ��ɾ��������")){
		var IDelementName = recordfileId;
		var NAMEelementName = recordfileName;

        var fileIds = document.getElementsByName(IDelementName)[0];
        if(fileIds.value.length>0){
            fileIds.value = fileIds.value + "," + fileId;
        }else{
            fileIds.value = fileId;
        }

		var fileNames = document.getElementsByName(NAMEelementName)[0];
		if(fileNames.value.length>0){
            fileNames.value = fileNames.value + "," + fileName;
        }else{
            fileNames.value = fileName;
        }
        var eObj = event.srcElement;
        var tr = eObj.parentElement.parentElement;
   
        tr.parentElement.removeChild(tr);
        
        // �鿴�Ƿ��и���,��û��,��Ѹ�����Ϊ������
        var mulfile = window.document.getElementsByName(mulfileName);
  
        if(mulfile.length == 0){
            window.document.getElementById("label:"+fileColName).innerHTML="<font color='red'>*" + captionName +"&nbsp;</font>";
        }
    }
}

/////////2007-01-30yumg����
/*****************************************
 ** ɾ������������ ɾ������ͬʱ����һ����ѡ���ϴ���ǩ��ť
 ** ����1 var fileId����ɾ������idֵ 
 ** ����2 var recordfileID����ǰҳ���ύ���洢ɾ������idֵ���ֶ� �� record:deletefileID
 ** ����3 var fileName����ɾ����������ֵ
 ** ����4 var recordfileNAME����ǰҳ���ύ���洢ɾ����������ֵ���ֶ� �� record:deletefileNAME
 ** ����5 var field����ǰҳ���ύ���洢������ǩ���ֶ� �� record:file
 ** ����6 var fieldcaption����ǰҳ���ύ���洢������ǩ���Ƶ��ֶ� �� ���ϴ�������
 ** ����7 var weight����ǰҳ��Form����file��Ӧ�ı�ǩ���� ,�� ��450px��
 ****************************************/
function delAndAddNeedSingleFile(fileId,recordfileID,fileName,recordfileNAME,field,fieldcaption,weight,colspan){
  var eObj = event.srcElement;
  var tr = eObj.parentElement.parentElement;
  var idx = tr.rowIndex;
  if(colspan == null || colspan=="")
  	colspan = "3";

  //ɾ������֮�����������ϴ�������ǩ
  if(confirm("��ȷ��ɾ���˸����ļ���\r\n\r\n[��ʾ] ����ʱִ��ɾ��������")){
    var IDelementName = recordfileID;
    var NAMEelementName = recordfileNAME;
    var idValue = document.getElementsByName(IDelementName)[0];
    idValue.value = fileId;
    var nameValue = document.getElementsByName(NAMEelementName)[0];
    nameValue.value = fileName;     

    tr1 =  tr.parentElement.insertRow(idx);
    tr1.parentElement.removeChild(tr);
    
    tr1.bgColor = "white";
    
    var td = document.createElement("td");
    td.innerHTML = "<font color='red'>*"+fieldcaption+"&nbsp;</font>";
    td.height = "32";
    td.vAlign = "center";
    td.align = "right";
    td.style.paddingLeft = "4px";
    tr1.appendChild(td);
    
    td = document.createElement("td");
    td.colSpan = colspan;
    td.vAlign = "center";
    tr1.appendChild(td);
    
    var file = document.createElement("input");
    file.type = "file";
    file.name = field;
    td.height = "32";
    td.style.paddingLeft = "4px";
    td.appendChild(file);
  }
}



/*****************************************
 ** ���ظ������� ������������������ط��������ã�
 ** ����1 var fileId����ɾ������idֵ 
 ** ���÷���ʾ����<A href="#" onclick="downFile('fileId')" title="�鿴����">��������</A>
 ****************************************/
function downFile(fileId){
	  var page = new pageDefine( "/txn604050209.do?primary-key:ysbh_pk=" + fileId, "�����ļ�", "window", 650, 400);
    page.downFile();
}

/*******
 * zhongxiaoqi �޸ĵ� ������ɾ���� �ķ���
 * @param fileColName
 */
function addNewRow(fileColName){
	var eObj = document.getElementById('moreFile');
	var row_length=eObj.rows.length;
	var htm='<input name="record:fjmc1" id="record:fjmc1" style="width: 80%;" '+
	'contentEditable="false" type="file" maxLength="100" notnull="false" checkFlag="true" '+
	'fieldCaption="�ϴ��ļ�" datatype="file" maxvalue="100"/>&nbsp;&nbsp;<span class="delete" title="ɾ��" onclick="deleteRow(this)">&nbsp;&nbsp;</span>';
	var tr=eObj.insertRow();
	var td=tr.insertCell();
	td.height="30px";
	td.innerHTML=htm;
}
function deleteRow(obj){
	obj.parentElement.parentElement.parentElement.removeChild(obj.parentElement.parentElement);
	/*alert(index);
	var eObj = document.getElementById('moreFile');
	eObj.deleteRow(index);*/
}

function delChooseFile(fileId,recordfileId,fileName,recordfileName){
      if(confirm("��ȷ��ɾ���˸����ļ�?\r\n\r\n[��ʾ]����ʱִ��ɾ��������")){
		var IDelementName = recordfileId;
		var NAMEelementName = recordfileName;
        var fileIds = document.getElementsByName(IDelementName)[0];
        if(fileIds.value.length>0){
            fileIds.value = fileIds.value + "," + fileId;
        }else{
            fileIds.value = fileId;
        }

		var fileNames = document.getElementsByName(NAMEelementName)[0];
		if(fileNames.value.length>0){
            fileNames.value = fileNames.value + "," + fileName;
        }else{
            fileNames.value = fileName;
        }
        var eObj = event.srcElement;
        var tr = eObj.parentElement.parentElement.parentElement;
        tr.parentElement.removeChild(tr);
    }
}
