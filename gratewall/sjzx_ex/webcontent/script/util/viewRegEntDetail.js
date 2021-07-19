
function funcViewEntInfo(){
	var ent_sort = getFormFieldText("record-info:ent_sort");
	var reg_bus_ent_id = getFormFieldText("record-info:reg_bus_ent_id");
	var txnCode = "txn60110001.do";
	if('GT'==ent_sort){
		txnCode = "txn60110008.do";
	}else if('FQ'==ent_sort){
		txnCode = "txn60114003.do";
		/*var data_sou = getFormFieldText("record:industry_phy",index);
		if('3'==data_sou || '4'==data_sou){
			//txnCode = "<%=request.getContextPath()%>/dw/aic/bj/exc/detail-exc_que_reg_main.jsp?select-key:data_sou="+data_sou;
		}else{
			//alert("û��������Դ���޷��鿴��ϸ��Ϣ��");
			//return;
		}*/
		
	}else if('WB'==ent_sort){
	    txnCode = "txn60111002.do";
	}else if('HP'== ent_sort||'YR'== ent_sort){
		txnCode = "<%=request.getContextPath()%>/dw/aic/bj/reg/txy/main_txy.jsp";
	}else if('DJ'== ent_sort){
		txnCode = "txn60119001.do";
	}
 
  var page = new pageDefine(txnCode, "��ҵ��ϸ��Ϣ","_blank",screen.availWidth-30,screen.availHeight-60);    
  if(reg_bus_ent_id == "noneExist"){
  	alert("�޷����ӵ���ҵ!");
  	return false;
  }
  if('GT'==ent_sort ||'WZ'==ent_sort ||'SQ'==ent_sort ||'NZ'==ent_sort ||'JG'==ent_sort){
    page.addValue(reg_bus_ent_id,"primary-key:reg_bus_ent_id");
    page.addValue(ent_sort,"primary-key:ent_sort");	 	
  }
  if('' == ent_sort){
  	alert("��ҵ���ݲ�ȫ���޷��鿴��ϸ��Ϣ��");
  	clickFlag = 0;
	checkAllMenuItem();
  	return;
  }
  if('FQ'==ent_sort){
		page.addValue( reg_bus_ent_id, "select-key:exc_que_reg_id" );
		//page.addParameter( "record:organ_code", "select-key:organ_code" );
		page.addValue( getFormFieldText("record-info:ent_name"), "select-key:ent_name" );
  }
  if('WB' ==ent_sort){
  	page.addValue( reg_bus_ent_id, "select-key:reg_bus_ent_oc_id" );
	  page.addValue( getFormFieldText("record-info:ent_name"), "select-key:ent_name" );
	  page.addValue( getFormFieldText("record-info:reg_no"), "select-key:reg_no" );
  }
  if('HP' ==ent_sort||'YR' ==ent_sort){
  	  page.addValue( reg_bus_ent_id, "select-key:entid" );
	  page.addValue( getFormFieldText("record-info:ent_name"), "select-key:ent_name" );
	  page.addValue( getFormFieldText("record-info:reg_no"), "select-key:reg_no" );
	  page.addValue( ent_sort, "select-key:ent_sort" );
	  page.addValue( getFormFieldText("record-info:city_id"), "select-key:ent_blk_one_id" );
  }
  if('DJ' ==ent_sort){
	  page.addValue( getFormFieldText("record-info:ent_name"), "select-key:ent_name" );
	  page.addValue( getFormFieldText("record-info:reg_no"), "select-key:reg_no" );
	  page.addValue( getFormFieldText("record-info:entcat"), "select-key:entcat" );
  	  page.addValue(reg_bus_ent_id,"select-key:reg_bus_ent_id");
  } 
  page.goPage(null,window);
}

function toPrintContent(){
  var page = new pageDefine("txn60110102.do", "��ҵ��ϢԤ����ӡ","_blank",screen.availWidth-30,screen.availHeight-60);
  page.addValue(getFormFieldValue("record-info:reg_bus_ent_id"),"select-key:reg_bus_ent_id");
  page.addValue('0',"select-key:ent_sort");
  var printTZ=document.getElementById('printTZ');
  var printMem=document.getElementById('printMem');
  //var printYearCheck=document.getElementById('printYearCheck');
  var paramTz="0";
  var paramMem="0";
  var paramYearCheck="0";
  if(printTZ){
    if(printTZ.checked==true){
      paramTz="1";
    }
  }
  if(printMem){
    if(printMem.checked==true){
      paramMem="1";
    }
  }
  page.addValue(paramTz,"select-key:inv_columns");
  page.addValue(paramMem,"select-key:mem_columns");
  page.addValue(paramYearCheck,"select-key:njyz_columns");
  page.goPage(null,window);
}

function toExportContent(){
  var page = new pageDefine("txn60110102.do", "��ҵ��ϢԤ������");
  page.addValue(getFormFieldValue("record-info:reg_bus_ent_id"),"select-key:reg_bus_ent_id");
  page.addValue('1',"select-key:ent_sort");
  var printTZ=document.getElementById('printTZ');
  var printMem=document.getElementById('printMem');
  //var printYearCheck=document.getElementById('printYearCheck');
  var paramTz="0";
  var paramMem="0";
  var paramYearCheck="0";
  if(printTZ){
    if(printTZ.checked==true){
      paramTz="1";
    }
  }
  if(printMem){
    if(printMem.checked==true){
      paramMem="1";
    }
  }
  page.addValue(paramTz,"select-key:inv_columns");
  page.addValue(paramMem,"select-key:mem_columns");
  page.addValue(paramYearCheck,"select-key:njyz_columns");
  page.goPage();
}

function setOpDate(flag){
  //���þ�Ӫ����
  var op_from = getFormFieldValue("record-info:op_from");
  var op_to = getFormFieldValue("record-info:op_to");
  var from="";
  var to="";
  if(op_from){
    if(op_from!=''){
        if(op_from.indexOf("-")!=-1){
          from+="�� "+changeTimeFormate(op_from)+" �� ";
        }else{
          from+="�� "+op_from+" �� ";
        }
    }
  }
  if(op_to){
    if(op_to!=''){
        if(op_to.indexOf("-")!=-1){
          from+=changeTimeFormate(op_to);
        }else{
          from+=op_to;
        }
    }
  }
  setFormFieldValue("record-info:op_date",from+"  "+to);
  
  //���ý�λ
  if(flag){
      var unit = getFormFieldValue("record-info:cap_cur");
	  var reg = new RegExp("^[0-9]+$");
	  if(unit=='' || unit=='�����'){
	    unit = "Ԫ";
	  }
	  //���á�ע���ʱ�����ʾ��ʽ
	  var cur = getFormFieldValue("record-info:reg_cap");
	  if(cur != ''){
		setFormFieldValue("record-info:reg_cap",cur+" ��"+unit);
	  }
	  //Ͷ���˵ĳ��ʽ��
	  var sub_con_am=document.getElementById('label:record:sub_con_am');
	  if(sub_con_am){
		 var htm=sub_con_am.innerText;
		 sub_con_am.innerHTML=htm.replace("��Ԫ","��"+unit);
	   }
   }
  
}

function changeTimeFormate(day){
    var time = day.split('-');
    return time[0]+"��"+time[1]+"��"+time[2]+"��";
}
function toPrint(){
  var title="��ѡ���ӡ��Χ��<br/><Table width='95%' style='margin:0 auto;border-collapse:collapse;'><tr><td style='border-bottom:1px solid #ddd;'><input type='checkbox' name='printScope' checked='checked' disabled='disabled'/>������Ϣ</td></tr>";
  var ini=document.getElementById('tab_ini');
  var mem=document.getElementById('tab_mem');
  if(ini || mem){
	  if(ini){
		title+="<tr><td style='border-bottom:1px solid #ddd;'><input type='checkbox' id='printTZ' name='printTZ'/>Ͷ����</td></tr>";
	  }
	  if(mem){
	    title+="<tr><td style='border-bottom:1px solid #ddd;'><input type='checkbox' id='printMem' name='printMem'/>��Ҫ��Ա</td></tr>";
	  }
	  title+="<table>";
	  Boxy.confirm(title, function() {
		  toPrintContent();
		}
	);
  }else{
     toPrintContent();
  }
}

function toExport(){
  var title="��ѡ�񵼳���Χ��<br/><Table width='95%' style='margin:0 auto;border-collapse:collapse;'><tr><td style='border-bottom:1px solid #ddd;'><input type='checkbox' name='printScope' checked='checked' disabled='disabled'/>������Ϣ</td></tr>";
  var ini=document.getElementById('tab_ini');
  var mem=document.getElementById('tab_mem');
  if(ini || mem){
	  if(ini){
		title+="<tr><td style='border-bottom:1px solid #ddd;'><input type='checkbox' id='printTZ' name='printTZ'/>Ͷ����</td></tr>";
	  }
	  if(mem){
	    title+="<tr><td style='border-bottom:1px solid #ddd;'><input type='checkbox' id='printMem' name='printMem'/>��Ҫ��Ա</td></tr>";
	  }
	  title+="<table>";
	  Boxy.confirm(title, function() {
		  toExportContent();
		}
	);
  }else{
     toExportContent();
  }
}

