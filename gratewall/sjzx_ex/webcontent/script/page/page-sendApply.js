window.onload = send_Apply;
var uploadFileProcessBar;
function send_Apply(){
	uploadFileProcessBar = initUploadFileBar();
}
function sendApply(){
	if (!getFormFieldValue("record:apply_name")){
		alert("���������ơ�����Ϊ�գ�");
		return false;
	}
	if (!getFormFieldValue("record:apply_reason")){
		alert("����;������Ϊ�գ�");
		return false;
	}
	if (getMutilDownloadType() == 1 ){
		totalRecords = "";
	}
	//var post = "record:apply_count=" + totalRecords;
	//var post = "?record:apply_count=" + totalRecords;
	document.getElementById('query_condition').value=encodeURI(document.getElementById("totalTableDiv").outerHTML);
	//post += "&record:query_condition="+encodeURI(encodeURI(document.getElementById("totalTableDiv").outerHTML));

	var sql = dialogArguments.document.getElementById("record:query_sql").value;
	if (getMutilDownloadType() == 1 || getMutilDownloadType() == "1"){
		// �õ������ŵ�SQL���
		sql = dialogArguments.document.getElementById("record:query_dsql").value;
		if(isDownload && isDownload == "1"){// �������������
			var sqlSplitByAnswerArray = sql.split("?");
			var connJSONArray = dialogArguments.qcObj.connJSONArray;
			if (sqlSplitByAnswerArray.length != (connJSONArray.length + 1)){
				alert("�ύ����δ�ɹ���ԭ���ǲ�ѯ�����п��ܳ������쳣������뱣����ѯ������������Ϣ��лл");
				return;
			}
			var newSql = "";
			for (var index = 0; index < connJSONArray.length; index++){
				var ss_index = connJSONArray[index];
				var ss_value = "";
				var columnOneName = ss_index.columnOneName;
				if (columnOneName && columnOneName.toUpperCase() == "EST_DATE"){
					ss_value = "?";
				}else{
					ss_value = ss_index.paramValue.replace(/\'|%/g, "");
				}
				newSql += sqlSplitByAnswerArray[index] + ss_value;
			}
			newSql += sqlSplitByAnswerArray[sqlSplitByAnswerArray.length - 1];
//			alert("sql:" + sql);
			sql = newSql;
		}else{// ����Ǹ߼���ѯ����������
		}
	}
	var param_table, param_column, param_value, param_seque;
	param_table = "1";
	param_column = "1";
	param_value = "1";
	param_seque = "1";
	
	var columnsenarray = dialogArguments.document.getElementById("record:columnsEnArray").value;
	var columnscnarray = dialogArguments.document.getElementById("record:columnsCnArray").value;
	
	// ����ܼ�¼�����ڸ��˵�����¼������Ҫ����
	var status = 4;
	// ��������Ϊ ��������
	if ( (parseInt(totalRecords) <= parseInt(max_result) || max_result == "0" ) 
		&& getMutilDownloadType() == 0 ){
		status = 5;
	}
	sql = sql.replace(/\+/g, "%2B");
	fileName = encodeURI(encodeURI(getFormFieldValue("record:apply_name")));
	/*post = "?record:apply_date=" + getFormFieldValue("record:apply_date")
		+ "&record:display_name=" + fileName
		+ "&record:sql=" + encodeURI(encodeURI( sql ))
		+ "&record:status=" + status
		+ "&record:is_mutil_download=0" ;*/
	var apply_result="";
	if(status==4){
	  apply_result=1;
    }else{
      apply_result=0;
    }
	//post="?record:apply_result=" + getFormFieldValue("record:apply_date");
	post="?result:apply_result=" + apply_result;
    document.getElementById('columnsenarray').value=columnsenarray;
    document.getElementById('columnscnarray').value=columnscnarray;
    document.getElementById('display_name').value=fileName;
    document.getElementById('apply_sql').value=encodeURI( sql );
    document.getElementById('apply_status').value=status;
    
     var obj=document.getElementById("fileUploadFrame").contentWindow;
     if(obj.document.getElementById('fileName')){
        document.getElementById('fj_file_path').value=obj.document.getElementById('filePath').value;
        document.getElementById('fj_file_name').value=obj.document.getElementById('fileName').value;
     }
	var connJSONArray = dialogArguments.qcObj.connJSONArray;
	//alert(connJSONArray.length);
	for (var ind = 0; ind < connJSONArray.length; ind++){
		var ss = connJSONArray[ind];
		var columnOneName = ss.columnOneName;
		post = post	
			+ "&param:param_table=" + ss.tableOneId
			+ "&param:param_column=" + ss.columnOneId
			+ "&param:param_value=" + encodeURI(encodeURI(ss.paramValue.replace(/\'|%/g, "")))
			+ "&param:param_seque=" + ind
			+ "&param:param_text=" + encodeURI(encodeURI(ss.paramText));
		/*if(isDownload && isDownload == "1"){// ������������أ�ֻ��columnOneNameΪEST_DATEʱ�����param��Ϣ
			
			alert(columnOneName);
			if (columnOneName && columnOneName.toUpperCase() == "EST_DATE"){
				post = post	
				+ "&param:param_table=" + ss.tableOneId
				+ "&param:param_column=" + ss.columnOneId
				+ "&param:param_value=" + encodeURI(encodeURI(ss.paramValue.replace(/\'|%/g, "")))
				+ "&param:param_seque=" + ind
				+ "&param:param_text=" + encodeURI(encodeURI(ss.paramText));
			}
		}else{// �Ǹ߼���ѯ��ֱ������ֶβ���
			
		}*/
		ss = null;
	}
	//alert("�����Ѿ����ͳɹ�����ѡ������ĸ�ʽ�������أ�");
	var URL = "/txn60300009.do"+post;
	document.getElementById("record_record_saveRecord").style.display = "none";
	//alert(document.getElementsByName("record_record_saveRecord")[0].id);
	document.getElementById('downloadIFrame').style.display="block";
	document.getElementById("form_sq").action=URL;
	document.getElementById("form_sq").submit();
	
	
	/*var URL = rootPath + "/txn60300003.ajax";
	var downloadObj = new ActiveXObject("Microsoft.XMLHTTP");
	downloadObj.open ('post',URL, true);
	downloadObj.onreadystatechange = function(){
		doCallback(downloadObj);
	};
	downloadObj.setrequestheader("cache-control","no-cache"); 
	downloadObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
	downloadObj.send(post);*/
}

function doCallback(downloadObj){
	if (downloadObj.readyState == 4) { // �ж϶���״̬
		if (downloadObj.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
		    var xmlResult = downloadObj.responseXML;
		    if (xmlResult.selectSingleNode("//context/error-code").text != "000000"){
				alert(xmlResult.selectSingleNode("//context/error-desc").text);
			}else{
				var sid = _getXmlNodeValues(xmlResult, 'record:filepath');
				var dislay_name = _getXmlNodeValues(xmlResult, 'record:display_name');
				//alert((parseInt(totalRecords) <= parseInt(max_result) || max_result == "0" ) );
				//alert(getMutilDownloadType());
				if ( (parseInt(totalRecords) <= parseInt(max_result) || max_result == "0" ) 
					&& getMutilDownloadType() == 0 ){
					// ��ʾ����IFRAME
					alert("�����Ѿ����ͳɹ�����ѡ������ĸ�ʽ�������أ�");
					var download_status_id = xmlResult.selectSingleNode("//record/download_status_id").text;
					var downloadIFrame = document.getElementById("downloadIFrame");
					downloadIFrame.src = rootPath + "/txn60400006.do?select-key:download_status_id=" 
						+ download_status_id;
					downloadIFrame.style.display = "block";
				}else{
					alert("�����Ѿ��ύ�������������Ϳ��������ˣ�");
					window.close();
				}
			}
		}
	}
}

function isMutilDownloadRadio(){
    var is_mutil_download_Y = document.getElementById("is_mutil_download_Y");
    var is_mutil_download_N = document.getElementById("is_mutil_download_N");
    if (is_mutil_download_Y.checked == true){
    	// ���ж���û�����ò�ѯ����
    	var connJSONArray = dialogArguments.qcObj.connJSONArray;
    	if (connJSONArray.length <= 0){
    		alert("û���������ز�������������Ϊ�ظ�����!");
    		is_mutil_download_N.checked = true;
    		return;
    	}else{
    		if(isDownload && isDownload == "1"){// �������������
    			// ����ѯ�������Ƿ��Ѿ�ѡ���˳�������
    			var hasChoosedCLRQ = false;
    			for (var ind = 0; ind < connJSONArray.length; ind++){
					var ss = connJSONArray[ind];
					var columnOneName = ss.columnOneName;
					if (columnOneName && columnOneName.toUpperCase() == "EST_DATE"){
						hasChoosedCLRQ = true;
						break;
					}
				}
				// �Ѿ�û��ѡ����������ֶ���Ϊ��ѯ����֮һ
				if (!hasChoosedCLRQ){
					alert("ѡ���ظ����ر���ѡ�����������Ϊ��ѯ������");
					is_mutil_download_N.checked = true;
					return;
				}
    		}else if(isDownload && isDownload == "0"){// ����Ǹ߼���ѯ
    			// ��������
    		}
    	}
    	
        var rs = confirm("ѡ���ѡ�����ѡ��Ĳ�ѯ����Ϊ����������Ҫ������");
        if (!rs){
        	is_mutil_download_N.checked = true;
        }else{
        	
        }
    }
    is_mutil_download_Y = null;
    is_mutil_download_N = null;
}

/**
 * ��ȡѡ������
 */
function getMutilDownloadType(){
	var is_mutil_download = 0;
    var is_mutil_download_Y = document.getElementById("is_mutil_download_Y");
    var is_mutil_download_N = document.getElementById("is_mutil_download_N");
    if (is_mutil_download_Y.checked == true){
        is_mutil_download = 1;
    }
    is_mutil_download_Y = null;
    is_mutil_download_N = null;
    return is_mutil_download;
}

// ��ʼ���ļ��ϴ�������
function initUploadFileBar(){
	return new processBar({
		id:"uploadFileBar",
		parentContainer:"uploadFileProcessBarDiv"
	});
}

var process;
function genTempTableHandler(){
    //uploadFileProcessBar=initUploadFileBar();
	var uploadFileInput = document.getElementById("FJ_FLIE").value;
	if (!uploadFileInput){
		alert("��ѡ���ϴ��ļ�!");
		return false;
	}
	// ���������".xls"��β
	if ( uploadFileInput.search( /\.(xlsx?)|(txt)|(xls)|(docx)|(doc)|(zip)|(rar)$/i ) < 0){
		alert("ֻ���ϴ���չ��Ϊ.txt,doc,docx,xls,xlsx,zip,rar!");
		return false;
	}
	document.getElementById("FJ_FLIE").readOnly = "readonly";
	document.getElementById("genTempTable").disable = "true";
	document.getElementById("record_record_saveRecord").disabled = true;
	uploadFileProcessBar.show();
	uploadFileProcessBar.reset();
	uploadFileProcessBar.setInfo("(" + 20 + "%)�����ϴ��ļ�");
	uploadFileProcessBar.setPercent(20);
	//getUploadProcess();
	process=setInterval("getUploadProcess()",1000); 
	document.getElementById('uploadForm').submit();
}

function getUploadProcess(){
   //alert(document.getElementById("uploadFileProcessBarDiv").innerHTML);
   var obj=document.getElementById("fileUploadFrame").contentWindow;
   if(!obj.document.getElementById("fileName")){
      var pert=document.getElementById("uploadFileBar_processBarPassedDiv").style.width;
      var per=pert.replace("%","");
      if(parseInt(pert)< 90){
       uploadFileProcessBar.setPercent(parseInt(pert)+10);
	   uploadFileProcessBar.setInfo("(" + (parseInt(pert)+10)+ "%)�����ϴ��ļ�");
	  }else{
	   if(parseInt(pert)<=98){
		  uploadFileProcessBar.setPercent(parseInt(pert)+1);
		  uploadFileProcessBar.setInfo("(" + (parseInt(pert)+1)+ "%)�����ϴ��ļ�");
	   }
	 }
   }else{
     clearInterval(process);
   }
}

function createTempTable(pert){
    var obj=document.getElementById("fileUploadFrame").contentWindow;
    if(obj.document.getElementById("fileName")){
       var fileName=obj.document.getElementById("fileName");
       if(fileName!=""){
		    uploadFileProcessBar.setPercent(100);
		    uploadFileProcessBar.setInfo("(100%)�ѳɹ��ϴ�!");
		    document.getElementById("record_record_saveRecord").disabled = false;
		    clearInterval(process);
		}
	}
}