/**
 * ��Ը���
 * ˵��:
 */
function mon_yc_ind(){
	var oraStr = "Is_Gowth����ֲ��ֳ����Is_Food_Produ��ʳƷ�����ӹ�����Is_Food_Sell��ʳƷ�������������ۣ���Is_Reast���������񣩡�Is_Food_Other_Bussi������ʳƷ����Is_Dance_Sing��������񣩡�Is_Chess_Card�����Ʒ��񣩡�Is_Game_NetBa���������ռ����ɣ���Is_Hotel���ùݣ���Is_Agenc���н���񣩡�Is_Bathe��ϴԡ���񣩡�Is_HairD��������������Is_Recyc���Ͼɻ��գ���Is_Dange_Chemi��Σ�ջ�ѧƷ����Is_Minin���ɿ�ҵ������ú��ɽ������Is_Dog_Bussi����Ȯ��Ӫ����Is_Agric_Mater��ũҵ�������ϣ���Other��������";
	oraStr = oraStr.substr(0, oraStr.length - 1);
	var transArray = oraStr.split(/\s*[��)]\s*��\s*/g);
	var regExp = /[��(]/;
	var totalStr = "";
	for (var i=0; i < transArray.length; i++){
		var item = transArray[i].replace(regExp, ",");
		transArray[i] = item.split(",");
		// ��������Ϊ0����ȡ��������������ж�������΢��Щ��ͬ��
		if (getFormFieldValue("record:" + transArray[i][0].toLowerCase()).trim() != "0"){
			totalStr = totalStr + transArray[i][1] + ",";
		}
	}
	// д��totalStr
	if (totalStr.length >= 1){
		document.getElementById("totalSort").innerHTML = totalStr.substr(0, totalStr.length - 1);
	}
}

/**
 * ���������������������
 */
function mon_yc_other(bussi_type){
	var oraStr = "Is_Gowth����ֲ��ֳ�ࣩ��Is_Food_Produ��ʳƷ�����ࣩ��Is_Food_Sell��ʳƷ�����ࣩ��Is_Reast�������ࣩ��Is_Food_Other_Bussi������ʳƷ��Ӫ�ࣩ";
	oraStr = oraStr.substr(0, oraStr.length - 1);
	var transArray = oraStr.split(/\s*[��)]\s*��\s*/g);
	var regExp = /[��(]/;
	var totalStr = "";
	for (var i=0; i < transArray.length; i++){
		var item = transArray[i].replace(regExp, ",");
		transArray[i] = item.split(",");
		// ��������Ϊ�գ���ȡ�������������ж�������΢��Щ��ͬ��
		if (getFormFieldValue("record:" + transArray[i][0].toLowerCase()).trim()){
			totalStr = totalStr + transArray[i][1] + ",";
		}
	}
	// д��totalStr
	if (totalStr.length >= 1){
		document.getElementById("span_record:" + bussi_type.toLowerCase()).innerHTML = totalStr.substr(0, totalStr.length - 1);
	}
}