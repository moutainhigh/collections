//--------------------------------时间控件----------------------
    /**
        选择时间的输入框被选中时，创建datepicker控件
        根据设置不同dateFmt 格式，使空间支持选择日，周，月，季，年
    */
    function createWdatePickerStart() {
        var type = $('#Header input[name="gender"]:checked ').val();
        if (type == 0) {//按日
            WdatePicker({
                readOnly : true,
                dateFmt : 'yyyy-MM-dd',
                isShowWeek : true,
                maxDate : '%y-%M-%d'
            });
        } else if (type == 1) {//按月
            WdatePicker({
                readOnly : true,
                dateFmt : 'yyyy-MM-dd',
                onpicked : pickTimeStart,
                isShowWeek : true,
                maxDate : '%y-%M-%d'
            });
        } 
  
    };
    
    function createWdatePickerEnd() {
    	  
    	var type = $('#Header input[name="gender"]:checked ').val();
  
        if (type == 0) {//按日
            WdatePicker({
                readOnly : true,
                dateFmt : 'yyyy-MM-dd',
                isShowWeek : true,
                maxDate : '%y-%M-%d'
            });
        } else if (type == 1) {//按月
            WdatePicker({
                readOnly : true,
                dateFmt : 'yyyy-MM-dd',
                onpicked : pickTimeEnd,
                isShowWeek : true,
                maxDate : '%y-%M-%d'
            });
        } 
  
    };
   
    
    function pickTimeStart() {
            var y = $dp.cal.getP('y', 'yyyy');
            var M = $dp.cal.getP('M', 'M');
            
   		 	
            var d = new Date(y, M, 0);
  
            var datestart = {};
            datestart.y = y;
            datestart.M = M;
            datestart.d = 1;
  
            $("#showstarttime").val(dateToString(datestart));
  
        }
    
    
    function pickTimeEnd() {
            var y = $dp.cal.getP('y', 'yyyy');
            var M = $dp.cal.getP('M', 'M');
            
            
            var date=new Date;
   		 	var month=date.getMonth()+1;
   		 	
   		 	
            var d = new Date(y, M, 0);
            
            
            var dateend = {};
            dateend.y = y;
            dateend.M = M;
            if(M==month){
            dateend.d =date.getDate();
            }else{
            //获取月的最后一天
            dateend.d = d.getDate();
            }
            $("#showendtime").val(dateToString(dateend));
  
        }
    /**
        控件返回的时间对象转换成字符串输出
        控件的时间对象有y,M,d,H,m,s属性，分别返回年，月，日，时，分，秒
    */
    function dateToString(date) {
        var strdate = "";
        strdate = strdate + date.y + "-";
        var M = date.M >= 10 ? date.M : ("0" + date.M);
        strdate = strdate + M;
        var d = date.d >= 10 ? date.d : ("0" + date.d);
        strdate = strdate + "-" + d;
        return strdate;
    };
    
   /* //报表统计单位 换算
	Highcharts.setOptions({
		lang: {
			numericSymbols: null
		}
	});
    */