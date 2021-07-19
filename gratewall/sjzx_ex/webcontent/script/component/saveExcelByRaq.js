/**
 * Parameter List
 * excelFilePath:The file path of Excel in WJG 
 * raqFilePath:  The file path of Raq defined in GZK
 * isIndicator:  boolean parameter, value true or false
 */ 
 

function saveExcelByRaq(excelFilePath, raqFilePath, processorClass, callBack, params)
{		
	// The default JSP page
	if (!contextPath){
		alert("call page must define contextPath!");
		return;
	}
	var targetPage = contextPath + "/sjjzzj/saveExcelByRaq.jsp";
		
	var fullUrl = targetPage + "?excelFilePath=" + excelFilePath 
					+ "&raqFilePath=" + raqFilePath
					+ "&processorClass=" + processorClass
					+ "&"+params;
					
	$.ajax({
			type:"POST",
			url: fullUrl,
			async: true,
			dataType:"json",
			error:function(){
				var defaultMsg = "{retFlag:false,retMsg:'\u52a0\u8f7d\u8fc7\u7a0b\u4e2d\u9047\u5230\u9519\u8bef'}";
				var jsonMsg = eval('(' + defaultMsg + ')');
			    callBack(jsonMsg);
			},
			success: function(msg){
			    var jsonMsg = msg;
			    callBack(jsonMsg);
			}
		});
}

