//1 构造指定字段的拼音数据
    function buildPinYin(source) {
        var td = source;
        var len = td.length;
        for(var i=0, value; value=td[i++]; ){
            var col = value.title;
            var py = pinyin.getCamelChars(col);
            var fy = py.substr(0, 1);
            var pyin = pinyin.getFullChars(col);
            value.py = py;
            value.fy = fy;
            value.pinyin = pyin;
        }

        var keys = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'OTHER'];
        //var keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
        var data = {}; //用来存放已经有序的数据，按照字母表顺序A-->Z
        //初始化过程
        for (var i = 0; i < keys.length; i++) {
            data[keys[i]] = [];
        }
        for (var i = 0, value; value = source[i++];) {
            //console.log(n+" -- "+value.py + ' = ' + value.fy + ' - - ' + value.pinyin);
            var index = value.py.toUpperCase().charCodeAt() - 65;
            if (index > -1 && index < 26) {
                data[keys[index]].push(value);
            } else {
                data['OTHER'].push(value);
            }
        }
        return data;
    }

    //2 构造html
    function buildHtml(source,dbName,dbNamecn,searchFlag) {
    	
        var data = buildPinYin(source);
        var html = '<div class="cont">';
        
        if(searchFlag==true){
        	html += '<table width="100%"><tr class="grid-headrow"><td width="60%" class="item" align="center">&nbsp;<a onclick="toSelContent1(\''+dbName+'\',\''+dbNamecn+'\',\''+searchFlag+'\');" >[确认]</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="toCancel();" >[取消]</a></td><td class="item" valign="middle"><input type="checkbox" onclick="checkAllShow();"/><span id="checkAllShow">全选</span>&nbsp;&nbsp; '+
        	'<input type="checkbox" onclick="checkAllQuery();"/><span id="checkAllQuery">全选</span></td></td></tr></table>';
        }else{
        	html += '<table width="100%"><tr class="grid-headrow"><td width="60%" class="item" align="center">&nbsp;<a onclick="toSelContent1(\''+dbName+'\',\''+dbNamecn+'\',\''+searchFlag+'\');" >[确认]</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="toCancel();" >[取消]</a></td><td class="item" valign="middle"><input type="checkbox" onclick="checkAllShow();"/><span id="checkAllShow">全选</span>&nbsp;&nbsp; '+
        	'</td></td></tr></table>';
        }
        
        for (var at = 65; at < 91; at++) {
            var tmp = data[String.fromCharCode(at)];
            var len = tmp.length;
            if (len > 0) {
                html += '<div class="cont-area" id="'+String.fromCharCode(at)+'">' + String.fromCharCode(at) + '</div><div>';
                html += '<table width="100%">';
                for (var j = 0; j < len; j++) {
                    var td = tmp[j];
                    if(searchFlag==true){
                    	
                    	html+='<tr><td width="60%" id="'+td.key+'" py="'+td.py+'" fy="'+td.fy+'" pinyin="'+td.pinyin+'" class="item">' 
                    	+ td.title + '<span class="tnum">'+(td.amount ? '('+td.amount+')' : "")+'</span></td>'
                    	+'<td class="item" width="40%"><input type="checkbox" '+td.display+' name="check_show" value="0" />显示&nbsp;&nbsp; <input type="checkbox" '+td.search+' name="check_query" value="1"/>检索</td></tr>';
                    }else{
                    	
                    	html+='<tr><td width="60%" id="'+td.key+'" py="'+td.py+'" fy="'+td.fy+'" pinyin="'+td.pinyin+'" class="item">' 
                    	+ td.title + '<span class="tnum">'+(td.amount ? '('+td.amount+')' : "")+'</span></td>'
                    	+'<td class="item" width="40%"><input type="checkbox" '+td.display+' name="check_show" value="0" />显示&nbsp;&nbsp; </td></tr>';
                    }
                    
                    //html += '<div id="_'+td.key+'" py="'+td.py+'" fy="'+td.fy+'" pinyin="'+td.pinyin
                    //+'" class="item">' + td.title + '<span class="tnum">'+(td.amount ? '('+td.amount+')' : "")+'</span></div>';
                }
                html += "</table></div>";
            }else{
                html += '<div class="nodata" id="'+String.fromCharCode(at)+'">' + String.fromCharCode(at) + '</div>';
            }
            //delete data[String.fromCharCode(i)];
        }
        var tmp = data['OTHER'];
        var len = tmp.length;
        if (len > 0) {
            html += '<div class="cont-area" id="other">其他</div><div>';
            for (var j = 0; j < len; j++) {
                var td = tmp[j];
                html += '<div id="_'+td.key+'" py="' + td.py + '" fy="' + td.fy + '" pinyin="' + td.pinyin + '" class="item">' + td.title + '</div>';
            }
            html += "</div>";
        } else {
            html += '<div class="nodata" id="other">其他</div>';
        }
        html += '</div>';
        return html;
    }
    
    function checkAllShow(){
    	if($('#checkAllShow').html()=='全选'){
    	    $("input[name='check_show']").attr("checked",true);
    	    $('#checkAllShow').html("全不选");
    	}else{
    		$("input[name='check_show']").attr("checked",false);
    		$('#checkAllShow').html("全选");
    	}
    }
    
    function checkAllQuery(){
    	if($('#checkAllQuery').html()=='全选'){
    	    $("input[name='check_query']").attr("checked",true);
    	    $('#checkAllQuery').html("全不选");
    	}else{
    		$("input[name='check_query']").attr("checked",false);
    		$('#checkAllQuery').html("全选");
    	}
    }
    
   //将选择的字段信息展示在页面上
function toSelContent1(dbName, dbNamecn,searchFlag){
	if(searchFlag=='true'){
		if($('input[name="check_query"]:checked').length==0 ){
			alert("至少选择一个检索字段！");
			return;
		}
	}
	if($('input[name="check_show"]:checked').length==0 ){
		alert("至少选择一个显示字段！");
		return;
	}
	var columnArr = columnMap[dbName+"_key"].split(',');
	var columnArrVal = columnMap[dbName+"_value"].split(',');
	var htm = '<td style="width: 12%; border: 1px solid #ddd;"><span> '
		+dbNamecn+'</span></td>';
	htm += '<td style="padding: 5px 0;border: 1px solid #ddd">';
	var checkedNum = 0;
	/* for(var i=0; i<columnArr.length;i++)
	{
		//console.log(i + ": " + columnArr[i]);
   //		htm += '<label style="width: 100px; line-height: 1.2;white-space: nowrap;"><input type="checkbox" name="'+dbName+'" value="'+columnArr[i]+'" checked />'+ columnArr[i]+'</label> ';
		htm += columnArr[i];
	} */
	$('.cont tr:gt(0)').each(function(index){
		htm += '<div style="white-space:nowrap; display:inline;width:180px;margin-right:10px;line-height:1.5;">'
			+'<span style="color:#069;">'+$(this).find('td:first').text()+'</span><input type="hidden" value="'+
			$(this).find('td:first').attr('id')+'" />&nbsp;&nbsp;';
			//$(this).find('td:first').text()+'" />&nbsp;&nbsp;';
		$(this).find('td:last input:checked').each(function(){
			//checkedNum++;
			//var tstr = '';
			if('0' == $(this).val()){
			//	htm += '显示<label style="white-space:nowrap;line-height: 1.2;white-space: nowrap; "><input disabled type="checkbox" checked /></label>&nbsp;&nbsp;';
				htm += '<span class="'+dbName+'_show">[显示]</span>'; 
			}
			if('1' == $(this).val()){
			//	htm += '检索<label style="white-space:nowrap; line-height: 1.2;white-space: nowrap;"><input disabled type="checkbox" checked /></label>';
				htm += '<span class="'+dbName+'_query">[检索]</span>';
			}
			//htm += '['+tstr+']';
		})
		htm += '<br/></div>'
	})
	
	htm += '</td><td width="40" align="center" valign="bottom" style="padding-bottom:3px;border: 1px solid #ddd">'
		+'<a href="javascript:bulidForm(\''+dbName+'\',\''+dbNamecn+'\')">编辑</a></td>';
	//alert($('#div'+dbName).html());
	if($('#div'+dbName).length == 0){
		if($('#cond_checkTable').html()=='无'){
			$('#cond_checkTable').html(htm);
		}else{
		  $('#cond_checkTable').append(htm);
		}
	}else{
		$('#div'+dbName).html(htm);
	}
	//$('#pre_sel tr:gt(0)').each(function(index){
	//	alert($(this).find('td:last input:checked').val());
	//})
	
	$('#mask').fadeOut(500);
    $('#modal_window').fadeOut(500);
    $('#searchDiv').fadeOut(500);
}

function toCancel(){
	$('#mask').fadeOut(500);
    $('#modal_window').fadeOut(500);
    $('#searchDiv').fadeOut(500);
}

//start by dwn 20140225
function editData(dbName,dbNamecn){
	var columnArr = columnMap[dbName+'_key'].split(',');
	var columnArrVal = columnMap[dbName+'_value'].split(',');
	var data = "";
	for(var i=0;i<columnArr.length;i++){
			if(i==0){
				var checked1 = '';
				var checked2 = '';
				
				if($('#div'+dbName+' td:eq(1) div:eq('+i+') span.'+dbName+"_show").text().indexOf('显示') > -1){
					checked1 = 'checked';
				}
					
				if($('#div'+dbName+' td:eq(1) div:eq('+i+') span.'+dbName+"_query").text().indexOf('检索') > -1){
					checked2 = 'checked';
				}
				
				data = '{"title":"'+columnArrVal[i]+'","key":"'+columnArr[i]+'","display":"'+checked1+'","search":"'+checked2+'"}';
			}else{
				var checked1 = '';
				var checked2 = '';
				
				if($('#div'+dbName+' td:eq(1) div:eq('+i+') span.'+dbName+"_show").text().indexOf('显示') > -1){
					checked1 = 'checked';
				}
					
				if($('#div'+dbName+' td:eq(1) div:eq('+i+') span.'+dbName+"_query").text().indexOf('检索') > -1){
					checked2 = 'checked';
				}
				
				data += ',{"title":"'+columnArrVal[i]+'","key":"'+columnArr[i]+'","display":"'+checked1+'","search":"'+checked2+'"}';
			}
			
	}
	
	data = '{"data":['+data+']}';
	return data;
}

function bulidForm(dbName,dbNamecn){

	if($('.first a.nSelected').length>1){
		searchFlag = false;
	}else{
		searchFlag = true;
	}

	data = editData(dbName,dbNamecn);
	var itfName_str = '['+data+']';
	var itfName = eval('('+itfName_str+')');
	var datahtml = itfName[0].data;
    var $e = $(this).parent();
    $('#mask').css({
        'display': 'inline',
         opacity: 0
    });
    //fade in the mask to opacity 0.8 
    $('#mask').fadeTo(500, 0.4);
    $('#mask').height(document.body.availHeight);
    $('#mask').width(document.body.availWidth);
    //show the modal window
    $('#modal_window').html('');

    if ($('#searchDiv').length == 0) {
        $('#modal_window').before('<div id="searchDiv" class="albar" style="text-align:center;font-size:14px; width: 400px;" >'
            //+ '<input value="" style="" type="text" id="searchBar" />' + '<span style="">(输入关键词进行检索)</span>' + '<span style="" id="windowClose">[关闭]</span>'+
            + '<ul class="letter-list"><li class="on"><a href="#A">A-E</a></li><li><a href="#F">F-J</a></li><li><a href="#K">K-O</a></a></li><li><a href="#P">P-T</a></li><li><a href="#U">U-Z</a></li> <li><a href="#other">其他</a></li> </ul><div id="windowClose">[关闭]</div></div>');
    } else {
        $('#searchDiv').show();
    }
    //alert($('#modal_window').css('top')+ " - - - - " +$('#modal_window').height());
    //$('#searchDiv').css('top', ($('#modal_window').css('top')-$('#modal_window').height()));
    $('#modal_window').html('');
    var datahtml = buildHtml(itfName[0].data,dbName,dbNamecn,searchFlag);
    if (datahtml) {
        $('#modal_window').html(datahtml);
    }
    $('#searchDiv a').bind('click', function() {
        $(this).parent().addClass('on');
        $(this).parent().siblings().removeClass('on');
    })
    //if($('#searchDiv').length==0){
    $('#searchBar').keyup(
        function() {
            $("#modal_window .pack-list li:gt(1)").not('.pull-out').hide().filter(
                ":contains('" + ($(this).val()) + "')").show();
        }).keyup();
    $('#windowClose').click(function() {
    	$('#mask').fadeOut(500);
        $('#modal_window').fadeOut(500);
        $('#searchDiv').fadeOut(500);
    })
    //}

    //$('#modal_window .pack-list li:lt('+(2+options.maxsize)+')').hide();
    if ($('#modal_window').outerHeight() >= $('body').height() * 0.6) {
        $('#modal_window').css("overflow-y", "hidden");
        $('#modal_window').height("400");
    }
    var left = ($(window).width() - $('#modal_window').outerWidth()) / 2;
    var top = ($(window).height() - $('#modal_window').outerHeight()) / 2;
    //$('#searchDiv').css('top', ($('#modal_window').css('top').replace('px', '')-$('#modal_window').height()));
    //top = top + 20;
    if (top <= 0) {
        top = 25;
    };
    $('#modal_window').css({
        "top": top,
        "left": left,
        "position":"absolute",
        "background-color":"#fff",
        "overflow-x":"hidden"
    });

    $('#searchDiv').css('top', (top - $('#searchDiv').height() - 10));
    $('#searchDiv').css('left', left);
    $('#windowConfirm').css('left', $('#modal_window').width() - $('#windowConfirm').width() + 10);
    $('#windowClose').css('left', $('#modal_window').width() - $('#windowClose').width() - 5);
    $('#modal_window').fadeIn("fast");
    //$el.height(32);   
}

//end by dwn 20140225

  