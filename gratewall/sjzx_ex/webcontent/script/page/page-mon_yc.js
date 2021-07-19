/**
 * 针对个体
 * 说明:
 */
function mon_yc_ind(){
	var oraStr = "Is_Gowth（种植养殖）、Is_Food_Produ（食品生产加工）、Is_Food_Sell（食品、饮料批发零售）、Is_Reast（餐饮服务）、Is_Food_Other_Bussi（其他食品）、Is_Dance_Sing（歌舞服务）、Is_Chess_Card（棋牌服务）、Is_Game_NetBa（电子游艺及网吧）、Is_Hotel（旅馆）、Is_Agenc（中介服务）、Is_Bathe（洗浴服务）、Is_HairD（美容美发）、Is_Recyc（废旧回收）、Is_Dange_Chemi（危险化学品）、Is_Minin（采矿业（含非煤矿山））、Is_Dog_Bussi（涉犬经营）、Is_Agric_Mater（农业生产资料）、Other（其他）";
	oraStr = oraStr.substr(0, oraStr.length - 1);
	var transArray = oraStr.split(/\s*[）)]\s*、\s*/g);
	var regExp = /[（(]/;
	var totalStr = "";
	for (var i=0; i < transArray.length; i++){
		var item = transArray[i].replace(regExp, ",");
		transArray[i] = item.split(",");
		// 如果结果不为0，则取出来。与下面的判断类型稍微有些不同。
		if (getFormFieldValue("record:" + transArray[i][0].toLowerCase()).trim() != "0"){
			totalStr = totalStr + transArray[i][1] + ",";
		}
	}
	// 写入totalStr
	if (totalStr.length >= 1){
		document.getElementById("totalSort").innerHTML = totalStr.substr(0, totalStr.length - 1);
	}
}

/**
 * 除个体外的其他经济类型
 */
function mon_yc_other(bussi_type){
	var oraStr = "Is_Gowth（种植养殖类）、Is_Food_Produ（食品生产类）、Is_Food_Sell（食品销售类）、Is_Reast（餐饮类）、Is_Food_Other_Bussi（其它食品经营类）";
	oraStr = oraStr.substr(0, oraStr.length - 1);
	var transArray = oraStr.split(/\s*[）)]\s*、\s*/g);
	var regExp = /[（(]/;
	var totalStr = "";
	for (var i=0; i < transArray.length; i++){
		var item = transArray[i].replace(regExp, ",");
		transArray[i] = item.split(",");
		// 如果结果不为空，则取出来。与个体的判断类型稍微有些不同。
		if (getFormFieldValue("record:" + transArray[i][0].toLowerCase()).trim()){
			totalStr = totalStr + transArray[i][1] + ",";
		}
	}
	// 写入totalStr
	if (totalStr.length >= 1){
		document.getElementById("span_record:" + bussi_type.toLowerCase()).innerHTML = totalStr.substr(0, totalStr.length - 1);
	}
}