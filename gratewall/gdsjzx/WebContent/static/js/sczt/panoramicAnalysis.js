var oChart = null;
var marketflag=null;
var marketpriPid=null;
var marketjbxxid = null;
var marketsourceflag=null;
var marketenttype=null;
var marketeconomicproperty=null;
var marketenttypeName = null;
var markettype=null;
var marketentname = null;
var marketopen = null;
$(function(){
	$("#tab1").loading();
	$("#tab1").loading("show");
	$('#content').hide();
	marketflag=getUrlParam("flag");
	marketpriPid=getUrlParam("priPid");
	marketjbxxid = getUrlParam("jbxxid");
	marketsourceflag=getUrlParam("sourceflag");
	marketenttype=getUrlParam("enttype");
	marketeconomicproperty=getUrlParam("economicproperty");
	marketenttypeName = getUrlParam("enttypeName");
	markettype=getUrlParam("type");
	marketentname = getUrlParam("entname");
	marketopen = getUrlParam("open");
	marketregno = getUrlParam("regno");
	var url = rootPath+'/page/reg/regDetail.jsp?flag='+marketflag+'&priPid='+marketpriPid+'&jbxxid='+marketjbxxid+'&sourceflag='+marketsourceflag+'&enttype='+marketenttype+'&economicproperty='+marketeconomicproperty+'&enttypeName='+marketenttypeName+'&type='+markettype+'&regno='+marketregno+'&entname='+marketentname+'&open='+marketopen;
	//alert(url);
	//window.open(url,'contload','');
	var a = {
		tabid: 'detail',	
		tabtitle: '市场主体基本信息',	
		tabindex: '2',
		//tabContentHeight:'5000',
		taburl: url
	};
	$.ajax({
		url: rootPath+'/analysis/panoramicAnalysis.do',
		data:{
			priPid : marketpriPid,
			sourceflag : marketsourceflag,
			regno:marketregno,
			entname:marketentname
		},
	 	//async : false,
		dataType : 'json',
		type : "POST", //请求方式
		success : function(data) {
			//var str = "[";
			var flag = "[";
			var spline="[";
			for(var i=0;i<data.length;i++){
				//str+="["+data[i].estdate+","+data[i].value+"],";
				//id,pkid,pripid,sourceflag,type,tablename,title,name,describe,operationtime,timestamp,operationtime localtime
				/* var jsObj = {};  
				jsObj.testArray = [1,2,3,4,5];  
				jsObj.name = 'CSS3';  
				jsObj.date = '8 May, 2011'; */ 
				/*var str =  new Object();
				str.id=data[i].id;
				str.operationtime=data[i].operationtime;
				str.title=data[i].title;
				str.pkidname=data[i].pkidname;
				str.pripid=data[i].pripid;
				str.tablename=data[i].tablename;
				str.pkidvalue=data[i].pkidvalue;
				str.describe=data[i].describe;
				str.name=data[i].name;
				str.value=data[i].value;
				str.sourceflag=data[i].sourceflag;
				str.type=data[i].type;*/
				var params = JSON.stringify(data[i]); 
				if(data[i].type=='211' || data[i].type=='257'){//守重
					if(i!=data.length-1){//+data[i].localtime
						flag+="{x:"+data[i].operationtime+",align: 'left',marker:{radius: 7,fillColor: '#00FF00',lineColor: '#00FF00',states:{hover:{fillColor: '#00FF00',lineColor: '#00FF00'}}},title:'"+data[i].title+"<span style=\"display:none;\">zhongguo</span>',events:{click:function(e){getSignData(e,'"+params+"');}},text: '',name:'"+data[i].describe+"'},";
					}
					spline+="{x:"+data[i].operationtime+",y:"+data[i].value+",name:'"+data[i].describe+"',marker:{radius: 7,fillColor: '#00FF00',lineColor: '#00FF00',states:{hover:{fillColor: '#00FF00',lineColor: '#00FF00'}}},events:{click:function(e){getDotData(e,'"+params+"');}}},";
				}else if(data[i].type=='210' || data[i].type=='209' || data[i].type=='214' || data[i].type=='255' || data[i].type=='259'){
					if(i!=data.length-1){//+data[i].localtime
						flag+="{x:"+data[i].operationtime+",align: 'left',fillColor: '#EE2C2C',marker:{radius: 7,fillColor: '#EE2C2C',lineColor: '#EE2C2C',states:{hover:{fillColor: '#EE2C2C',lineColor: '#EE2C2C'}}},title:'"+data[i].title+"<span style=\"display:none;\">zhongguo</span>',events:{click:function(e){getSignData(e,'"+params+"');}},text: '',name:'"+data[i].describe+"'},";
					}
					spline+="{x:"+data[i].operationtime+",y:"+data[i].value+",name:'"+data[i].describe+"',marker:{radius: 7,fillColor: '#EE2C2C',lineColor: '#EE2C2C',states:{hover:{fillColor: '#EE2C2C',lineColor: '#EE2C2C'}}},events:{click:function(e){getDotData(e,'"+params+"');}}},";
				}else{
					if(i!=data.length-1){//+data[i].localtime
						flag+="{x:"+data[i].operationtime+",align: 'left',title:'"+data[i].title+"<span style=\"display:none;\">zhongguo</span>',events:{click:function(e){getSignData(e,'"+params+"');}},text: '',name:'"+data[i].describe+"'},";
					}
					spline+="{x:"+data[i].operationtime+",y:"+data[i].value+",name:'"+data[i].describe+"',events:{click:function(e){getDotData(e,'"+params+"');}}},";
				}
			}
			//str = str.substring(0,str.length-1);
			//str += "]";
			flag = flag.substring(0,flag.length-1);
			flag +="]";
			spline = spline.substring(0,spline.length-1);
			spline +="]";
			drawHighChart(marketentname+"(生命周期)","dd",flag,spline);
			$("#tab1").loading("hide");
		},
		error: function(e) { 
			$("#tab1").loading("hide");
		}
	});
	$("#tab_name").tabpanel("addTab",a);
	$('#tab_name').tabpanel('select',0); 
	
	$("#tab_name").tabpanel({
		change: function( event, index){ 
			if(index==1){
				$('#jazz_tabpanel_custom_detail_undefined').css('height','88.2%');
				
				//alert(index);
				//$('#jazz_tabpanel_custom_detail').css('height',2000);
				//alert($('#jazz_tabpanel_custom_detail').height());
				//$('#jazz_tabpanel_custom_detail').css('height',2000);
				//alert($('#jazz_tabpanel_custom_detail').height());
			}
		} 
	});
});
function drawHighChart(title,xName,flag,spline){
	var h = $('#tab1').width()-1;
	var returnStr="{chart: { renderTo: 'container',type: 'spline',height: 500,width: "+h+",},";
	returnStr +="title: {text: '"+title+"'},";
	returnStr +="height: 500,";
	returnStr +="width: "+h+",";
	returnStr +="exporting: {enabled: false},";
	returnStr +="credits:{enabled: false,href: 'http://www.msnui.tk/Admin',text: '微源网络科技'},";
	returnStr +="yAxis: [{title: {text: 'GOOGL'},visible:false,opposite:false}],";
	//returnStr +="tooltip:{xDateFormat: '%Y-%m-%d %H:%M:%S',formatter: cf,enabled:false},";
	returnStr +="tooltip:{xDateFormat: '%Y-%m-%d %H:%M:%S',enabled:true},";
	//returnStr +="tooltip:{shared: true,useHTML: true,headerFormat: '<table>',";
	//returnStr +="pointFormat: '<tr><td></td></tr>',footerFormat: '</table>'},";
	
	//returnStr +="tooltip:{shared: true,useHTML: true,headerFormat: '<table>',";,valueDecimals: 2
	//returnStr +="pointFormat: '<tr><td>{series.name}</td></tr>',footerFormat: '</table>'},";
	//tickPixelInterval:100,
	returnStr +="xAxis:{type:'datetime',tickColor: 'green',tickLength: 10,tickWidth: 3,tickPosition: 'inside',dateTimeLabelFormats:{second: '%Y-%m-%d<br/>%H:%M:%S',minute: '%Y-%m-%d<br/>%H:%M',hour: '%Y-%m-%d<br/>%H:%M',day: '%Y<br/>%m-%d',week: '%Y<br/>%m-%d',month: '%Y-%m',year: '%Y'},title:{text:''},labels: {formatter: function() {return getXNa(this.value);},align: 'center'}},";
	returnStr +="rangeSelector: {selected: 6,buttons: [{type: 'month',count: 1,text: '1月'}, {type: 'month',count: 3,text: '3月'}, {type: 'month',count: 6,text: '6月'}, {type: 'year',count: 1,text: '1年'},{type: 'year',count: 3,text: '3年'}, {type: 'all',text: '所有'}],inputEnabled: true,inputDateFormat:'%Y-%m-%d',inputEditDateFormat: '%Y-%m-%d',inputDateParser:function(value){return value;}},";
	//returnStr +="navigation: {menuItemStyle: {fontWeight: 'normal',background: 'none'},menuItemHoverStyle: {fontWeight: 'bold',background: 'none',color: 'black'}},";
	returnStr +="scrollbar:{enabled:true},";
	returnStr +="navigator:{enabled:true,maskFill:'rgba(128,179,236,0.3)',xAxis:{tickWidth: 0,lineWidth: 0,gridLineWidth: 1,tickPixelInterval: 200,labels: {format: '{value:%Y.%m.%d}',align: 'left',style: {color: 'red'},x: 3,y: -4}},series:{type: 'areaspline',color: '#4572A7',fillOpacity: 0.05,dataGrouping: {smoothed: true},lineWidth: 1,marker: {enabled: false}}},";
	returnStr +="series:[{type: 'spline',tooltip:{pointFormat:''},allowPointSelect:true,name:'曲线图',id: 'dataseries',marker : {enabled : true,radius : 5},data: "+spline+"},";//events:{click:function(e){getDotData();}},
	//returnStr +="{type: 'flags',shadow:true,plotOptions: {line: {marker: {enabled:true,radius: 1,states:{hover:{enabled:true,radius:4}}},states:{hover:{ enabled:true}}}},allowPointSelect:true,style: {color: 'white'},color: Highcharts.getOptions().colors[0], fillColor: Highcharts.getOptions().colors[0],states: {hover: {fillColor: 'red'}},name: '标记图',onSeries: 'dataseries',shape: 'flag',data: "+flag+"}]";
	returnStr +="{type: 'flags',name: '标记图',onSeries: 'dataseries',shape: 'flag',data: "+flag+"}]";
	returnStr +="}";//series.points  pointFormatter:flags  tooltip:{pointFormat:'sdf'},
	var dataObj=eval("("+returnStr+")");
	Highcharts.setOptions({
		lang: {
			//months: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],//['一月', '二月', '三月', '四月', '五月', '六月',  '七月', '八月', '九月', '十月', '十一月', '十二月'],
			//weekdays: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
			months:['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
			shortMonths : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			weekdays:['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
			rangeSelectorZoom:'时间维度',
			rangeSelectorFrom: '从',
			rangeSelectorTo: '到'
		}
    });
	oChart = new Highcharts.StockChart(dataObj);
}
function getXNa(value){
	 var vDate=new Date(value);
	 return vDate.getFullYear()+'-'+(vDate.getMonth()+1)+'-'+vDate.getDate();
}
function getDotData(e,params){
	//params = '{"localtime":"2016-06-20","operationtime":1466438400000,"pripid":"03f01d71-d91a-465a-aa5a-6b72be8d28fa","sourceflag":"440000","type":"207","id":13934737,"timestamp":"2016-08-11 10:29:48","title":"年报","pkidname":"ANCHEID","pkidvalue":"5a4982fd-4070-4fe1-b37c-5dd120208955","tablename":"T_NDBG_QYBSJBXX","name":"","describe":"","value":499}';
	var obj = eval('(' + params + ')');//JSON.parse(params);  
	obj.marketflag=marketflag;
	obj.marketpriPid=marketpriPid;
	obj.marketsourceflag=marketsourceflag;
	obj.markettype=markettype;
	obj.marketeconomicproperty=marketeconomicproperty;
	tableLoad(obj);
}
function getSignData(e,params) {
	var obj =  eval('(' + params + ')');//JSON.parse(params); 
	obj.marketflag=marketflag;
	obj.marketpriPid=marketpriPid;
	obj.marketsourceflag=marketsourceflag;
	obj.markettype=markettype;
	obj.marketeconomicproperty=marketeconomicproperty;
	tableLoad(obj);
}
function tableLoad(obj){
	$("#tab1").loading("show");
	$.ajax({
		url:rootPath+'/analysis/panoramicAnalysisDetail.do',
		data:obj,
		type:"post",
		dataType : 'json',
		success:function(data){
			$('#content').show();
			//var st = '<tr><td style="text-align: right;">企业(机构)名称:</td><td>佛山市高明区荷城谁见谁爱服饰店</td><td style="text-align: right;">企业(机构)名称:</td><td>佛山市高明区荷城谁见谁爱服饰店</td></tr><tr><td style="text-align: right;">企业(机构)名称:</td><td colspan="3">佛山市高明区荷城谁见谁爱服饰店</td></tr>';
			$('#tabs tbody').empty();
			$('#tabs tbody').append(data.returnString);
			//$('#hiddEnttype').val(data.enttype);
			$('#entname').empty();
			$('#entname').append("主体名称:"+marketentname);
			$('#regno').empty();
			$("#tab1").loading("hide");
			//$('#regno').append("注册号:"+data.regno);
			//var t = $('#content').height()+$('#tab1').height();
			//$('#tab1').css('height',t+500);container  //jazz_tabpanel_custom_detail
			/*alert($('#tab1').height());//608 400 821 1221
			alert($('#container').height());//400
			alert($('#content').height());//821
			$('#tab1').css('height',$('#tab1').height()+$('#content').height());
			alert($('#tab1').height());//1221*/	
			//alert($('div[name="tab_name"]').height());
			//$('div[name="tab_name"]').css('height',$('#content').height()+$('#content').height());
			//alert($('div[name="tab_name"]').height());//607 1343
		},
		error: function(e) { 
			$("#tab1").loading("hide");
		} 
	});
}
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(decodeURI(r[2])); return null; //返回参数值 
}
function getUrlParamName(name) {
    var reg = new RegExp(".*" + name + "\\s*=([^=&#]*)(?=&|#|).*","g"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.replace(reg, "$1");  //匹配目标参数
    if (r != null) return decodeURIComponent(r); return null; //返回参数值
}