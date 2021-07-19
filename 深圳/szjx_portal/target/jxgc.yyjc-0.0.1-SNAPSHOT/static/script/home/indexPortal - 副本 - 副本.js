//初始化系统
$(function() {
	prefunc();

	$.ajax({
		url : rootPath + "/home/getAllApp.do?random=" + Math.random(),
		async : false,
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			var datastr = data.data[0].data;
			initDual(datastr);

		},
		error : function() {
			alert("初始化系统菜单错误！！");
		}
	});

});

// 获取所有代办
function getALLAppWait() {
	$.ajax({
		url : "../../home/getAppAllWait.do",
		async : false,
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			returnStr = data.waitlist;
			$(returnStr).each(function() {
				$("#" + this.pkSysIntegration + "NO").html(this.todoNum);
				$("#" + this.pkSysIntegration + "NO").addClass("listNumSeri");
				var n1 = $("#" + this.parentCode + "NO").html();
				var n11 = 0;
				if (n1.length < 1) {
					n11 = 0
				} else {
					n11 = parseInt(n1);
				}
				var n2 = parseInt(n11) + parseInt(this.todoNum);
				$("#" + this.parentCode + "NO").html(n2)
				$("#" + this.parentCode + "NO").addClass("raduisNum");
			});

		},
		error : function() {
			alert("获取代办事项出错，请确认人事系统中已录入您的信息！");
		}
	});
}

// 处理系统
function initDual(datastr) {
	// console.log(JSON.stringify(datastr));
	var isSm = false;
	for (var i = 0, l = datastr.length; i < l; i++) {
		var systemCode = datastr[i]["systemCode"];
		
		/*		 if(systemCode=="SM"){		
		var pkbus=datastr[i]["pkSysIntegration"];
		var pkcode="AP";
		var url=datastr[i]["integratedUrl"];
		var sysname=datastr[i]["systemName"];
			
			var t1='div[name="AP"]';
			if(list.contains(pkcode)){
				list.removeObj(pkcode);
				$(t1).empty();
			}

		 
		 
		var htm ='<div class="inline_block submenu_content">'
				+'<span class="inline_block icon_submenu"></span>'
				+'<a href="javascript:void(0);" title="" onclick="windowsopen(\''+url+'\');">'
				+sysname
				+'</a>'

				+'</div>';
	
			$(t1).append(htm);		
 }else{*/
		
		var pkbus = datastr[i]["pkSysIntegration"];
		var pkcode = datastr[i]["pid"];
		var url = datastr[i]["integratedUrl"];
		var sysname = datastr[i]["systemName"];
		var length = parseInt($.trim("" + sysname.length + ""));

		var t1 = 'div[name=' + pkcode + ']';
		var noname = datastr[i]["pkSysIntegration"] + "" + "NO";
		// alert("pkcode:"+pkcode+" url:"+url+" systemName:"+sysname);

		var t1 = 'div[name=' + pkcode + ']';
		if (list.contains(pkcode)) {
			list.removeObj(pkcode);
			$(t1).empty();
		}

		var left = 0;
		if (length == 2) {
			left = 90;
		} else if (length == 3) {
			left = 104;
		} else if (length == 4) {
			left = 114;
		} else if (length == 5) {
			left = 128;
		} else if (length == 6) {
			left = 144;
		} else if (length == 7) {
			left = 158;
		} else if (length == 8) {
			left = 168;
		} else if (length == 9) {
			left = 178;
		} else if (length == 10) {
			left = 197;
		} else if (length == 11) {
			left = 209;
		} else {
			left = 219;
		}
		var htm = '<div class="inline_block submenu_content">'
				+ '<span class="inline_block icon_submenu"></span>'
				+ '<a href="javascript:void(0);" title="" onclick="windowsopen(\''
				+ url + '\');">' + sysname + '</a><span id="' + noname
				+ '" class="" style="left:' + left + 'px"></span>' + '</div>';
		$(t1).append(htm);

	}
	getALLAppWait();
}

// 加载业务域和当前系统
var list = new ArrayList();

// 初始化6个业务域
function prefunc() {
	var pkcode = "RP";
	list.add(pkcode);
	var pkcode = "MS";
	list.add(pkcode);

	var pkcode = "PS";
	list.add(pkcode);

	var pkcode = "AM";
	list.add(pkcode);

	var pkcode = "LE";
	list.add(pkcode);

	var pkcode = "AP";
	list.add(pkcode);

	var pkcode = "FZXT";
	list.add(pkcode);
}

function getJsonLength(jsonData) {
	var jsonLength = 0;
	for ( var item in jsonData) {
		jsonLength++;
	}
	return jsonLength;
}

function windowsopen(url) {
	window.open(url);

}
// 单独处理系统管理 系统管理比较特殊
function dualSM(smjsondata) {
	$("div[name='SM']").bind('click', function() {
		window.open("" + smjsondata);
	});
}

// list
function ArrayList() {
	this.arr = [], this.size = function() {
		return this.arr.length;
	}, this.add = function() {
		if (arguments.length == 1) {
			this.arr.push(arguments[0]);
		} else if (arguments.length >= 2) {
			var deleteItem = this.arr[arguments[0]];
			this.arr.splice(arguments[0], 1, arguments[1], deleteItem)
		}
		return this;
	}, this.get = function(index) {
		return this.arr[index];
	}, this.removeIndex = function(index) {
		this.arr.splice(index, 1);
	}, this.removeObj = function(obj) {
		this.removeIndex(this.indexOf(obj));
	}, this.indexOf = function(obj) {
		for (var i = 0; i < this.arr.length; i++) {
			if (this.arr[i] === obj) {
				return i;
			}
			;
		}
		return -1;
	}, this.isEmpty = function() {
		return this.arr.length == 0;
	}, this.clear = function() {
		this.arr = [];
	}, this.contains = function(obj) {
		return this.indexOf(obj) != -1;
	}
}