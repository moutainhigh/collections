function CWCMRecent_add(_nObjType, _nObjId){
	if(_nObjId == 0)return;

	var sURL = "../person/recent_save.jsp?ObjType="+_nObjType+"&ObjId="+_nObjId;
	//1.verify parameters
	var oHttp = new ActiveXObject("Microsoft.XMLHTTP"); //建立XMLHTTP对象
	
	oHttp.open("POST", sURL, true);
	
	//开始发送数据.............嘟嘟..
	oHttp.send("");

	delete oHttp;	
}

function CWCMRecent_addRecentDoc(_nChnlId, _nDocId){
	if(_nChnlId == 0 || _nDocId == 0)return;

	var sURL = "../person/recent_doc_save.jsp?ChannelId="+_nChnlId+"&DocumentId="+_nDocId;
	//1.verify parameters
	var oHttp = new ActiveXObject("Microsoft.XMLHTTP"); //建立XMLHTTP对象
	
	oHttp.open("POST", sURL, true);
	
	//开始发送数据.............嘟嘟..
	oHttp.send("");

	delete oHttp;	
}