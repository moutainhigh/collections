function exportExcel(url){
	var oSearch = window.location.search
	new Ajax.Request(url,{
		contentType : 'application/x-www-form-urlencoded',
		method : 'post',
		parameters : oSearch.substring(1),
		onSuccess : function(_transport){
			result = _transport.responseText;
			if(result && result.indexOf("<excelfile>") != -1){
				var ix = result.indexOf("<excelfile>") + 11;
				var ixx = result.indexOf("</excelfile>");
				result = result.substring(ix,ixx);
				var frm = document.getElementById("ifrmDownload");
				if(frm == null) {
					frm = document.createElement('iframe');
					frm.style.height = 0;
					frm.style.width = 0;
					document.body.appendChild(frm);
				}
				var sUrl = "../../../file/read_file.jsp?FileName="+result; 	
				frm.src = sUrl;
			}else{
				alert("导出统计结果到Excel失败！");
			} 
		},
		onFailure : function(_transport){
			 alert("1导出统计结果到Excel失败！");
		}
	});
}