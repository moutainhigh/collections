function export2Word(pid){
     if(pid){
     var printDiv = document.getElementById(pid);
     var wordApp 
		try{
			wordApp = new ActiveXObject("Word.Application");
		}catch(e){
				alert("����WORD�ļ�ʱ����,��ȷ�����Ѱ�װWord���,���Ҷ��������������ȷ������!")
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
			//����word��ʾΪҳ����ͼ
			wordApp.ActiveDocument.ActiveWindow.View.Type=3;
			//����word����Ϊ�����
			wordApp.Activate();
			
			
		}catch(e){
			alert("��������ҳ���ʼ��а����ݣ������޷�����ΪWORD�ļ���")
		}
	}
}