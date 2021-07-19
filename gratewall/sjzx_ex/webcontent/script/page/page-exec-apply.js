window.onload = send_Apply;
var uploadFileProcessBar;
function send_Apply(){
	uploadFileProcessBar = initUploadFileBar();
}
function sendApply(){
	if (!getFormFieldValue("record:apply_name")){
		alert("【申请名称】不能为空！");
		return false;
	}
	if (!getFormFieldValue("record:apply_reason")){
		alert("【用途】不能为空！");
		return false;
	}
	if (getMutilDownloadType() == 1 ){
		totalRecords = "";
	}
	
	var param_table, param_column, param_value, param_seque;
	param_table = "1";
	param_column = "1";
	param_value = "1";
	param_seque = "1";
	
	var columnsenarray = document.getElementById("columnsenarray").value;
	var columnscnarray = document.getElementById("columnscnarray").value;
	
	// 如果总记录数大于该人的最大记录数，需要申请
	var status = 4;
	// 否则，设置为 无需审批
	if ( (parseInt(totalRecords) <= parseInt(max_result) || max_result == "0" ) 
		&& getMutilDownloadType() == 0 ){
		status = 5;
	}
	//sql = sql.replace(/\+/g, "%2B");
	fileName = encodeURI(encodeURI(getFormFieldValue("record:apply_name")));
	var apply_result="";
	if(status==4){
	  apply_result=1;
    }else{
      apply_result=0;
    }
	post="?result:apply_result=" + apply_result;
    //document.getElementById('columnsenarray').value=columnsenarray;
    //document.getElementById('columnscnarray').value=columnscnarray;
    document.getElementById('display_name').value=fileName;
    document.getElementById('apply_sql').value=encodeURI( document.getElementById('apply_sql').value );
    document.getElementById('apply_status').value=status;
    document.getElementById('query_condition').value=encodeURI(document.getElementById('div_query_condition').innerHTML);
     var obj=document.getElementById("fileUploadFrame").contentWindow;
     if(obj.document.getElementById('fileName')){
        document.getElementById('fj_file_path').value=obj.document.getElementById('filePath').value;
        document.getElementById('fj_file_name').value=obj.document.getElementById('fileName').value;
     }
	
	//alert("申请已经发送成功，请选择下面的格式进行下载！");
	var URL = "/txn60300009.do"+post;
	document.getElementById('record_record_saveRecord').style.display="none";
	document.getElementById('downloadIFrame').style.display="block";
	document.getElementById("form_sq").action=URL;
	document.getElementById("form_sq").submit();
}


function isMutilDownloadRadio(){
    var is_mutil_download_Y = document.getElementById("is_mutil_download_Y");
    var is_mutil_download_N = document.getElementById("is_mutil_download_N");
    if (is_mutil_download_Y.checked == true){
    	// 先判断有没有设置查询条件
    	var connJSONArray = dialogArguments.qcObj.connJSONArray;
    	if (connJSONArray.length <= 0){
    		alert("没有设置下载参数，不能设置为重复下载!");
    		is_mutil_download_N.checked = true;
    		return;
    	}else{
    		if(isDownload && isDownload == "1"){// 如果是数据下载
    			// 检测查询条件中是否已经选择了成立日期
    			var hasChoosedCLRQ = false;
    			for (var ind = 0; ind < connJSONArray.length; ind++){
					var ss = connJSONArray[ind];
					var columnOneName = ss.columnOneName;
					if (columnOneName && columnOneName.toUpperCase() == "EST_DATE"){
						hasChoosedCLRQ = true;
						break;
					}
				}
				// 已经没有选择成立日期字段作为查询条件之一
				if (!hasChoosedCLRQ){
					alert("选择重复下载必须选择成立日期作为查询条件！");
					is_mutil_download_N.checked = true;
					return;
				}
    		}else if(isDownload && isDownload == "0"){// 如果是高级查询
    			// 不做处理
    		}
    	}
    	
        var rs = confirm("选择该选项将设置选择的查询条件为参数，你需要继续吗？");
        if (!rs){
        	is_mutil_download_N.checked = true;
        }else{
        	
        }
    }
    is_mutil_download_Y = null;
    is_mutil_download_N = null;
}

/**
 * 获取选项类型
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

// 初始化文件上传进度条
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
		alert("请选择上传文件!");
		return false;
	}
	// 如果不是以".xls"结尾
	if ( uploadFileInput.search( /\.(xlsx?)|(txt)|(xls)|(docx)|(doc)|(zip)|(rar)$/i ) < 0){
		alert("只能上传扩展名为.txt,doc,docx,xls,xlsx,zip,rar!");
		return false;
	}
	document.getElementById("FJ_FLIE").readOnly = "readonly";
	document.getElementById("genTempTable").disable = "true";
	document.getElementById("record_record_saveRecord").disabled = true;
	uploadFileProcessBar.show();
	uploadFileProcessBar.reset();
	uploadFileProcessBar.setInfo("(" + 20 + "%)正在上传文件");
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
	   uploadFileProcessBar.setInfo("(" + (parseInt(pert)+10)+ "%)正在上传文件");
	  }else{
	   if(parseInt(pert)<=98){
		  uploadFileProcessBar.setPercent(parseInt(pert)+1);
		  uploadFileProcessBar.setInfo("(" + (parseInt(pert)+1)+ "%)正在上传文件");
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
		    uploadFileProcessBar.setInfo("(100%)已成功上传!");
		    document.getElementById("record_record_saveRecord").disabled = false;
		    clearInterval(process);
		}
	}
}