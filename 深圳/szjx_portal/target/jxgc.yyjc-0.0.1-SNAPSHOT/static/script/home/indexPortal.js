//初始化系统
$(function() {
	prefunc();

	getAll();

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
			left = 55;
		} else if (length == 3) {
			left = 64;
		} else if (length == 4) {
			left = 82;
		} else if (length == 5) {
			left = 95;
		} else if (length == 6) {
			left = 111;
		} else if (length == 7) {
			left = 158;
		} else if (length == 8) {
			left = 168;
		} else if (length == 9) {
			left = 178;
		} else if (length == 10) {
			left = 192;
		} else if (length == 11) {
			left = 209;
		} else {
			left = 192;
		}
		var htm = '<div class="inline_block submenu_content">'
				+ '<span class="inline_block icon_submenu"></span>'
				+ '<a href="javascript:void(0);" title="" onclick="windowsopen(\''
				+ url + '\');">' + sysname + '</a><span id="' + noname
				+ '" class="" style="left:' + left + 'px"></span>' + '</div>';
		$(t1).append(htm);

	}
	//getALLAppWait();
	var map = null;
	if($("#OLD684NO").length>0||$("#8b80a6d286b14a5f8a2f344d66666po2NO").length>0||$("#23b1d7435d6e41b385da165991e4c8kENO").length>0){
		map = new Map();
		map.put("OLD684NO",$("#OLD684NO").length>0);
		map.put("8b80a6d286b14a5f8a2f344d66666po2NO",$("#8b80a6d286b14a5f8a2f344d66666po2NO").length>0);
		map.put("23b1d7435d6e41b385da165991e4c8kENO",$("#23b1d7435d6e41b385da165991e4c8kENO").length>0);
		//政务管理(财务待办/短信待办)
		getALLCWwait(map);
	}
	if($("#8b80a6d286b14a5f8a2f344d64446c56NO").length>0||$("#8b80a6d286b14a5f8a2f344d64446cf7NO").length>0){
		map = new Map();
		map.put("8b80a6d286b14a5f8a2f344d64446c56NO",$("#12b1d7435d6e41b385da165991e4c8fbNO").length>0);
		map.put("8b80a6d286b14a5f8a2f344d64446cf7NO",$("#8b80a6d286b14a5f8a2f344d64446cf7NO").length>0);
		//市场监管
		getALLSCJGwait(map);
	}
	if($("#12b1d7435d6e41b385da165991e4c8fbNO").length>0){
		map = new Map();
		map.put("12b1d7435d6e41b385da165991e4c8fbNO",$("#12b1d7435d6e41b385da165991e4c8fbNO").length>0);
		//执法办案
		getALLZFBAwait(map);
	}
	if($("#8b80a6d286b14a5f8a2f344d64445gh9NO").length>0){
		map = new Map();
		map.put("8b80a6d286b14a5f8a2f344d64445gh9NO",$("#8b80a6d286b14a5f8a2f344d64445gh9NO").length>0);
		//登记许可
		getALLDJXKwait(map);
	}
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


//获取财务代办(政务管理)
function getALLCWwait(map) {
	var cwCount = 0;
	try {
		$.ajax({
			url : "../../home/getCWwaitByjsonp.do",
			type: 'post',
			dataType:'jsonp',
			jsonp: "callback",
			data: {
				"type":'0'
			},
			success:function(data){
				var tempCount =data.waitno||0;
				if(tempCount>0&&tempCount!=null&&tempCount!='null'){
					cwCount=Number(tempCount)+Number(cwCount);
					if(map.get('OLD684NO')){
						$("#OLD684NO").html(tempCount);
						$("#OLD684NO").addClass("listNumSeri");
						$("#AMNO").html(cwCount)
						$("#AMNO").addClass("raduisNum");
						
					}
				}
			},
			error:function(){
			}
		});
	} catch (e) {
	}
	try {
		$.ajax({
			url : "../../home/getMessageWaitByWebService.do",
			type: 'post',
			dataType:'json',
			success:function(data){
				if(data.code!=0){
					return false;
				}
				var tempCount =data.message||0;
				cwCount=Number(tempCount)+Number(cwCount);
				if(map.get('8b80a6d286b14a5f8a2f344d66666po2NO')){
					$("#8b80a6d286b14a5f8a2f344d66666po2NO").html(tempCount);
					$("#8b80a6d286b14a5f8a2f344d66666po2NO").addClass("listNumSeri");
					$("#AMNO").html(cwCount)
					$("#AMNO").addClass("raduisNum");
					
				}
			},
			error:function(){
			}
			
		});
	} catch (e) {
	}
	try {
		$.ajax({
			url : "../../home/getPendingWaitByjson.do",
			type: 'post',
			dataType:'json',
			success:function(data){
				var tempCount =data.wcm_wait||0;
				if(tempCount==0){
					return false;
				}
				if(tempCount>0&&tempCount!=null&&tempCount!='null'){
					cwCount=Number(tempCount)+Number(cwCount);
					if(map.get('23b1d7435d6e41b385da165991e4c8kENO')){
						$("#23b1d7435d6e41b385da165991e4c8kENO").html(tempCount);
						$("#23b1d7435d6e41b385da165991e4c8kENO").addClass("listNumSeri");
						$("#AMNO").html(cwCount)
						$("#AMNO").addClass("raduisNum");
						
					}
				}
			},
			error:function(){
			}
		});
	} catch (e) {
	}
}

//获取市场监管待办数量 TODO 
function getALLSCJGwait(map) {
	$.ajax({
		url : "../../home/getSCJGWaitByWebService.do",
	    type: 'post',
	    dataType:'json',
	    success:function(data){
	    	if(data!=null){
	    		var cwCount =data.count||0;
		        if(cwCount>0&&cwCount!=null&&cwCount!='null'){
		        	var zxtsCount = data.xbwqNum;
		        	var scjgCount = data.szjxjgTaskNum;
		        	
		        	if(zxtsCount>0&&zxtsCount!=null&&zxtsCount!='null'){
		        		if(map.get('8b80a6d286b14a5f8a2f344d64446c56NO')){
			        		//8b80a6d286b14a5f8a2f344d64446c56NO咨询投诉ID
			        		$("#8b80a6d286b14a5f8a2f344d64446c56NO").html(zxtsCount);
			        		$("#8b80a6d286b14a5f8a2f344d64446c56NO").addClass("listNumSeri");
			        	
		        		}
		        	}
		        	
		        	if(scjgCount>0&&scjgCount!=null&&scjgCount!='null'){
		        		if(map.get('8b80a6d286b14a5f8a2f344d64446cf7NO')){
			        		//8b80a6d286b14a5f8a2f344d64446cf7NO市场监管ID
			        		$("#8b80a6d286b14a5f8a2f344d64446cf7NO").html(scjgCount);
			        		$("#8b80a6d286b14a5f8a2f344d64446cf7NO").addClass("listNumSeri");
			        	
		        		}
		        	}
					$("#MSNO").html(cwCount)
					$("#MSNO").addClass("raduisNum");
				}
	    	}
	    },
	    error:function(){
	    }
	});
}
//获取执法办案待办数量
function getALLZFBAwait(map) {
	$.ajax({
		url : "../../home/getZFBAWaitByWebService.do",
		type: 'post',
		dataType:'json',
		success:function(data){
			if(data!=null){
				var cwCount =data.count||0;
				if(cwCount>0&&cwCount!=null&&cwCount!='null'){
					if(map.get('12b1d7435d6e41b385da165991e4c8fbNO')){
						//12b1d7435d6e41b385da165991e4c8fbNO执法办案ID
						$("#12b1d7435d6e41b385da165991e4c8fbNO").html(cwCount);
						$("#12b1d7435d6e41b385da165991e4c8fbNO").addClass("listNumSeri");
						$("#LENO").html(cwCount)
						$("#LENO").addClass("raduisNum");
					
					}
				}
			}
		},
		error:function(){
		}
	});
	
}

//获取登记许可案待办数量
function getALLDJXKwait(map) {
	$.ajax({
		url : "../../home/getDJXKwaitByjsonp.do",
		type: 'post',
		dataType:'json',
		success:function(data){
			if(data!=null){
				var cwCount =data.djxk_wait||0;
				if(cwCount>0&&cwCount!=null&&cwCount!='null'){
					if(map.get('8b80a6d286b14a5f8a2f344d64445gh9NO')){
						//8b80a6d286b14a5f8a2f344d64445gh9NO登记许可ID
						$("#8b80a6d286b14a5f8a2f344d64445gh9NO").html(cwCount);
						$("#8b80a6d286b14a5f8a2f344d64445gh9NO").addClass("listNumSeri");
						$("#RPNO").html(cwCount);
						$("#RPNO").addClass("raduisNum");
					
					}
				}
			}
		},
		error:function(){
		}
	});
}


function Map() {
    /** 存放键的数组(遍历用到) */
    this.keys = new Array();
    /** 存放数据 */
    this.data = new Object();
    
    /**
     * 放入一个键值对
     * @param {String} key
     * @param {Object} value
     */
    this.put = function(key, value) {
        if(this.data[key] == null){
            this.keys.push(key);
        }
        this.data[key] = value;
    };
    
    /**
     * 获取某键对应的值
     * @param {String} key
     * @return {Object} value
     */
    this.get = function(key) {
        return this.data[key];
    };
}

function getAll(){
	
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
			getAll();
		}
	});
}

    

/***************************************待办拆分****************************************************/




