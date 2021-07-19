function export2Word(pid){
     if(pid){
     var printDiv = document.getElementById(pid);
     var wordApp 
		try{
			wordApp = new ActiveXObject("Word.Application");
		}catch(e){
				alert("导出WORD文件时错误,请确保您已安装Word软件,并且对浏览器进行了正确的设置!")
			 window.close();
		}
		try{
		    var wordDoc = wordApp.Documents.Add("",0,1);
			var range =wordDoc.Range(0,1);
			var sel = document.body.createTextRange();
			sel.moveToElementText(printDiv);
			sel.select();
			sel.execCommand("Copy");
			range.Paste();
			wordApp.Application.Visible = true;
			//设置word显示为页面视图
			wordApp.ActiveDocument.ActiveWindow.View.Type=3;
			//设置word窗口为活动窗口
			wordApp.Activate();
			
			
		}catch(e){
			alert("请允许网页访问剪切板内容，否则无法保存为WORD文件！")
		}
	}
}