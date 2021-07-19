/******************
** 校验附件类型
** 参数1 var str:当前页面Form 表单中file对应的标签的值
** 参数2 var captionName:当前页面Form表单中file对应的标签显示名称,如"上传附件"
** 参数3 var extType:允许上传的附件类型，多种类型用逗号隔开，如".txt,.doc,.pdf"
** 调用方式示例：<INPUT TYPE="button" Value="增加附件" onclick="addNewRow('fileColName','上传附件')" class="menu">
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
		alert("["+captionName+"]只能上传"+ extType + ",\n请重新选择数据类型!");
	}
	
	return allowSubmit;
}

/*****************************************
 ** 新建多个附件方法
 ** 参数1 var fileColName：当前页面Form表单中file对应的标签属性名称 ,如 record:file
 ** 参数2 var captionName：当前页面Form表单中file对应的标签显示名称 ,如 “上传附件”
 ** 调用方法示例：<INPUT TYPE="button" Value="增加附件" onclick="addNewRow('fileColName','上传附件')" class="menu">
 ****************************************/
function addNewRow(fileColName,captionName,weight,notnull) {
    var colNum = 2;   //在(从数值1开始的)指定列号的单元格中添加'删除'按钮
  
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
    
    	//添加表单附件类型判断
	var list = eval(document.forms[0].name +'_itemList');
	list.push(new formFieldInfo(fileColName, 'file', 0, '', '', ''));
 
  	eObj.parentElement.removeChild(eObj);//从TD中删除"增加行的按钮"
   
    var tr2 = tr.parentElement.rows[idx];

   	var btn = document.createElement("input");
    btn.type = "button";
    btn.value = "删 除";
    btn.style.width="60px";
    btn.className = "menu";
    btn.onclick = function() {
    	var eObj = event.srcElement;
    	var tr = eObj.parentElement.parentElement;
   		tr.parentElement.removeChild(tr);
    };
    
    tr2.cells[colNum-1].appendChild(btn); //在指定列的单元格中添加'删除'按钮
    
    var l = document.createElement("label");
    l.innerHTML = "&nbsp;&nbsp;";
    td.appendChild(l);
    td.appendChild(eObj);//将"删除"按钮添加到新行
}
/*****************************************
 ** 新建多个附件方法 附件标签长度默认为450px
 ** 参数1 var fileColName：当前页面Form表单中file对应的标签属性名称 ,如 record:file
 ** 参数2 var captionName：当前页面Form表单中file对应的标签显示名称 ,如 “上传附件”
 ** 调用方法示例：<INPUT TYPE="button" Value="增加附件" onclick="addNewRow('fileColName','上传附件')" class="menu">
 ****************************************/
function addNewRowExDefault(fileColName,captionName,notnull) {

	addNewRowEx(fileColName,captionName,"450px",notnull);
}

/*****************************************
 ** 新建多个附件方法
 ** 参数1 var fileColName：当前页面Form表单中file对应的标签属性名称 ,如 record:file
 ** 参数2 var captionName：当前页面Form表单中file对应的标签显示名称 ,如 “上传附件”
 ** 参数3 var weight：当前页面Form表单中file对应的标签长度 ,如 “450px”
 ** 调用方法示例：<INPUT TYPE="button" Value="增加附件" onclick="addNewRow('fileColName','上传附件')" class="menu">
 ****************************************/
 function addNewRowEx(fileColName,captionName,weight,notnull,accept) {
    var colNum = 2;   // 在(从数值1开始的)指定列号的单元格中添加'删除'按钮
 	
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
    
    // 添加表单附件类型判断
	//var list = eval(document.forms[0].name +'_itemList');
	var list = eval(document.forms[0].name);
	//list.push(new formFieldInfo(fileColName, 'file', 0, '', '', ''));
 
  	eObj.parentElement.removeChild(eObj);//从TD中删除"增加行的按钮"
   
    var tr2 = tr.parentElement.rows[idx];

   	var btn = document.createElement("a");
    btn.type = "MIME";
    //btn.value = "\u5220 \u9664";
    //btn.value = "删除";
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
    tr2.cells[colNum-1].appendChild(btn); //在指定列的单元格中添加'删除'按钮  
    
    var l = document.createElement("label");
    l.innerHTML = "&nbsp;&nbsp;";
    td.appendChild(l);
    td.appendChild(eObj);//将"增加行的按钮"添加到新行   
}

/*****************************************
 ** 删除单附件方法 删除附件同时增加一个上传标签按钮
 ** 参数1 var fileId：待删除附件id值 
 ** 参数2 var recordfileID：当前页面提交表单存储删除附件id值的字段 如 record:deletefileID
 ** 参数3 var fileName：待删除附件名称值
 ** 参数4 var recordfileNAME：当前页面提交表单存储删除附件名称值的字段 如 record:deletefileNAME
 ** 参数5 var field：当前页面提交表单存储附件标签的字段 如 record:file
 ** 参数6 var fieldcaption：当前页面提交表单存储附件标签名称的字段 如 “上传附件”
 ** 调用方法示例：<A href="#" onclick="downFile('fileId')" title="查看附件">附件名称</A>
 ****************************************/
function delAndAddSingleFileDefault(fileId,recordfileID,fileName,recordfileNAME,fileColName,captionName,notnull){

	delAndAddSingleFile(fileId,recordfileID,fileName,recordfileNAME,fileColName,captionName,"450px",notnull);

}

/*****************************************
 ** 删除单附件方法 删除附件同时增加一个上传标签按钮
 ** 参数1 var fileId：待删除附件id值 
 ** 参数2 var recordfileID：当前页面提交表单存储删除附件id值的字段 如 record:deletefileID
 ** 参数3 var fileName：待删除附件名称值
 ** 参数4 var recordfileNAME：当前页面提交表单存储删除附件名称值的字段 如 record:deletefileNAME
 ** 参数5 var field：当前页面提交表单存储附件标签的字段 如 record:file
 ** 参数6 var fieldcaption：当前页面提交表单存储附件标签名称的字段 如 “上传附件”
 ** 参数7 var weight：当前页面Form表单中file对应的标签长度 ,如 “450px”
 ** 调用方法示例：<A href="#" onclick="downFile('fileId')" title="查看附件">附件名称</A>
 ****************************************/
function delAndAddSingleFile(fileId,recordfileID,fileName,recordfileNAME,fileColName,captionName,weight ,notnull, acceptType){
	var eObj = event.srcElement;
    var tr = eObj.parentElement.parentElement;
    var idx = tr.rowIndex;

	//删除附件之后重新生成上传附件标签
    if(confirm("您确认删除此附件文件？\r\n\r\n[提示] 保存时执行删除操作。")){
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
	
		// 添加表单附件类型判断
		var list = eval(document.forms[0].name +'_itemList');
		list.push(new formFieldInfo(fileColName, 'file', 0, '', '', ''));
         
	}
}

/*****************************************
 ** 删除单附件方法 删除附件同时增加一个上传标签按钮(计划决策库专用)
 ** 参数1 var fileId：待删除附件id值 
 ** 参数2 var recordfileID：当前页面提交表单存储删除附件id值的字段 如 record:deletefileID
 ** 参数3 var fileName：待删除附件名称值
 ** 参数4 var recordfileNAME：当前页面提交表单存储删除附件名称值的字段 如 record:deletefileNAME
 ** 参数5 var field：当前页面提交表单存储附件标签的字段 如 record:file
 ** 参数6 var fieldcaption：当前页面提交表单存储附件标签名称的字段 如 “上传附件”
 ** 参数7 var weight：当前页面Form表单中file对应的标签长度 ,如 “450px”
 ** 
 ** 调用方法示例：<A href="#" onclick="downFile('fileId')" title="查看附件">附件名称</A>
 ****************************************/
function delAndAddSingleFileForJHJCK(fileId,recordfileID,fileName,recordfileNAME,fileColName,captionName,weight ,notnull, acceptType){
	var eObj = event.srcElement;
    var tr = eObj.parentElement.parentElement;
    var idx = tr.rowIndex;

	//删除附件之后重新生成上传附件标签
    if(confirm("您确认删除此附件文件？\r\n\r\n[提示] 保存时执行删除操作。")){
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
	
		// 添加表单附件类型判断
		var list = eval(document.forms[0].name +'_itemList');
		list.push(new formFieldInfo(fileColName, 'file', 0, '', '', ''));
         
	}
}

/*****************************************
 ** 删除附件方法
 ** 参数1 var fileId：待删除附件id值 
 ** 参数2 var record：当前页面提交表单块名称
 ** 参数3 var fileIds：块名称中保存待删除附件id值的属性名称
 ** 调用方法示例：<INPUT TYPE="button" VALUE=" 删除 " onclick="delFile('fileid','record','fileIds')" title="删除附件" class="menu">
 ****************************************/
function delFile(fileId,record,fileIds){
    if(confirm("您确认删除此附件文件？\r\n\r\n[提示] 保存时执行删除操作。")){
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
 ** 多附件删除附件方法 只删除附件，不生成上传标签。间隔符为分号“;”
 ** 参数1 var fileId：待删除附件id值 
 ** 参数2 var recordfileId：当前页面提交表单存储删除附件id值的字段 如 record:deletefileID
 ** 参数3 var fileName：待删除附件名称值
 ** 参数4 var recordfileName：当前页面提交表单存储删除附件名称值的字段 如 record:deletefileNAME
 ** 调用方法示例：<INPUT TYPE="button" VALUE=" 删除 " onclick="delFile('fileid','record','fileIds')" title="删除附件" class="menu">
 ****************************************/
function delMultiFile(fileId,recordfileId,fileName,recordfileName){
    //if(confirm("1111\u60A8\u786E\u8BA4\u5220\u9664\u6B64\u9644\u4EF6\u6587\u4EF6\uFF1F[\u63D0\u793A] \u4FDD\u5B58\u65F6\u6267\u884C\u5220\u9664\u64CD\u4F5C\u3002")){
    if(confirm("您确认删除此附件文件?\r\n\r\n[提示]保存时执行删除操作。")){
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

////////////// 2007-09-11yumg增加多附件必输校验:true-有值;false-无值
////////////// 2007-12-05yumg增加多附件上传类型校验
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
    alert("["+captionName+"]不能为空！");
  }
  
  return allowSubmit;  
}

/////////2007-09-08yumg增加
/*****************************************
 ** 必输多附件删除附件方法 只删除附件，不生成上传标签。间隔符为分号“;”
 ** 参数1 var fileId：待删除附件id值 
 ** 参数2 var recordfileId：当前页面提交表单存储删除附件id值的字段 如 record:deletefileID
 ** 参数3 var fileName：待删除附件名称值
 ** 参数4 var recordfileName：当前页面提交表单存储删除附件名称值的字段 如 record:deletefileNAME
 ** 参数5 var mulfileName：删除按钮的name 如 mulfile
 ** 参数6 var fileColName：附件标签id     如 record:file
 ** 参数7 var captionName：附件标签名称   如 附件
 ** 参数8 var btnId：增加附件按钮id       如 addfile
 ** 参数9 var weight：待增加附件的宽度    如 82%
 ** 调用方法示例：<INPUT TYPE="button" VALUE=" 删除 " onclick="delFile('fileid','record','fileIds')" title="删除附件" class="menu">
 ****************************************/
function delMultiNotnullFile(fileId,recordfileId,fileName,recordfileName,mulfileName,fileColName,captionName,btnId,weight){
    if(confirm("您确认删除此附件文件？\r\n\r\n[提示] 保存时执行删除操作。")){
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
        
        // 查看是否还有附件,若没有,则把附件改为必输项
        var mulfile = window.document.getElementsByName(mulfileName);
  
        if(mulfile.length == 0){
            window.document.getElementById("label:"+fileColName).innerHTML="<font color='red'>*" + captionName +"&nbsp;</font>";
        }
    }
}

/////////2007-01-30yumg增加
/*****************************************
 ** 删除单附件方法 删除附件同时增加一个必选的上传标签按钮
 ** 参数1 var fileId：待删除附件id值 
 ** 参数2 var recordfileID：当前页面提交表单存储删除附件id值的字段 如 record:deletefileID
 ** 参数3 var fileName：待删除附件名称值
 ** 参数4 var recordfileNAME：当前页面提交表单存储删除附件名称值的字段 如 record:deletefileNAME
 ** 参数5 var field：当前页面提交表单存储附件标签的字段 如 record:file
 ** 参数6 var fieldcaption：当前页面提交表单存储附件标签名称的字段 如 “上传附件”
 ** 参数7 var weight：当前页面Form表单中file对应的标签长度 ,如 “450px”
 ****************************************/
function delAndAddNeedSingleFile(fileId,recordfileID,fileName,recordfileNAME,field,fieldcaption,weight,colspan){
  var eObj = event.srcElement;
  var tr = eObj.parentElement.parentElement;
  var idx = tr.rowIndex;
  if(colspan == null || colspan=="")
  	colspan = "3";

  //删除附件之后重新生成上传附件标签
  if(confirm("您确认删除此附件文件？\r\n\r\n[提示] 保存时执行删除操作。")){
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
 ** 下载附件方法 调整公共交易码后下载方法（待用）
 ** 参数1 var fileId：待删除附件id值 
 ** 调用方法示例：<A href="#" onclick="downFile('fileId')" title="查看附件">附件名称</A>
 ****************************************/
function downFile(fileId){
	  var page = new pageDefine( "/txn604050209.do?primary-key:ysbh_pk=" + fileId, "下载文件", "window", 650, 400);
    page.downFile();
}

/*******
 * zhongxiaoqi 修改的 新增、删除行 的方法
 * @param fileColName
 */
function addNewRow(fileColName){
	var eObj = document.getElementById('moreFile');
	var row_length=eObj.rows.length;
	var htm='<input name="record:fjmc1" id="record:fjmc1" style="width: 80%;" '+
	'contentEditable="false" type="file" maxLength="100" notnull="false" checkFlag="true" '+
	'fieldCaption="上传文件" datatype="file" maxvalue="100"/>&nbsp;&nbsp;<span class="delete" title="删除" onclick="deleteRow(this)">&nbsp;&nbsp;</span>';
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
      if(confirm("您确认删除此附件文件?\r\n\r\n[提示]保存时执行删除操作。")){
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
