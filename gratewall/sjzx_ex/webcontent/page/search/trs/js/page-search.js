// 辨别特殊字符
	var expStrArray = new Array("北京","北京市","科技","有限","有限公","有限公司","限公司","公司","中心",
		"东城","西城","崇文","宣武","朝阳","海淀","丰台","石景山","门头沟",
		"房山","通州","顺义","大兴","昌平","平谷","怀柔","密云","延庆","东城区","西城区",
		"崇文区","宣武区","朝阳区","海淀区","丰台区","石景山区","门头沟区","房山区","通州区",
		"顺义区","大兴区","昌平区","平谷区","怀柔区","密云县","延庆县","城区","文区","武区",
		"阳区","淀区","台区","景山区","山区","头沟区","沟区","山区","州区","义区","兴区",
		"平区","谷区","柔区","云县","庆县","河北省","山西省","辽宁省","吉林省","黑龙江省",
		"江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省",
		"广东省","海南省","四川省","贵州省","云南省","陕西省","甘肃省","青海省","台湾省",
		"内蒙古","广西","西藏","宁夏","新疆","天津市","上海市","重庆市","河北","山西","辽宁",
		"吉林","黑龙江","江苏","浙江","安徽","福建","江西","山东","河南","湖北","湖南","广东",
		"海南","四川","贵州","云南","陕西","甘肃","青海","台湾","天津","上海","重庆");
	
	// 检查用户输入是否合法
	function checkInput(){
		var query = document.getElementById("search").value;
		query = query.replace(/\s+/g, " ");
		query = query.replace(/^\s+|\s+$/g, "");
		query = query.replace(/[\'\\\"]/gi, "");
		document.getElementById("search").value = query;
		if (!query){
			alert("请输入关键字！");
			return false;
		}
		var hasFound = false;
		for (var i =0 ; i < expStrArray.length; i++){
			if ((query || "").length <= 1 || expStrArray[i] == query){
				alert("请输入更详细的关键字后搜索！");
				return false;
			}
		}
		return true;
	}