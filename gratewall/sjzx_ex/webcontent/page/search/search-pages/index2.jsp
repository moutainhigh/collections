<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/page/page-search.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
	<style>
	BODY {
	FONT-SIZE: 12px;FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif;WIDTH: 60%; PADDING-LEFT: 25px;
}


/*CSS Digg style pagination*/

DIV.digg {
	PADDING-RIGHT: 3px; PADDING-LEFT: 3px; PADDING-BOTTOM: 3px; MARGIN: 3px; PADDING-TOP: 3px; TEXT-ALIGN: center
}
DIV.digg A {
	BORDER-RIGHT: #aaaadd 1px solid; PADDING-RIGHT: 5px; BORDER-TOP: #aaaadd 1px solid; PADDING-LEFT: 5px; PADDING-BOTTOM: 2px; MARGIN: 2px; BORDER-LEFT: #aaaadd 1px solid; COLOR: #000099; PADDING-TOP: 2px; BORDER-BOTTOM: #aaaadd 1px solid; TEXT-DECORATION: none
}
DIV.digg A:hover {
	BORDER-RIGHT: #000099 1px solid; BORDER-TOP: #000099 1px solid; BORDER-LEFT: #000099 1px solid; COLOR: #000; BORDER-BOTTOM: #000099 1px solid
}
DIV.digg A:active {
	BORDER-RIGHT: #000099 1px solid; BORDER-TOP: #000099 1px solid; BORDER-LEFT: #000099 1px solid; COLOR: #000; BORDER-BOTTOM: #000099 1px solid
}
DIV.digg SPAN.current {
	BORDER-RIGHT: #000099 1px solid; PADDING-RIGHT: 5px; BORDER-TOP: #000099 1px solid; PADDING-LEFT: 5px; FONT-WEIGHT: bold; PADDING-BOTTOM: 2px; MARGIN: 2px; BORDER-LEFT: #000099 1px solid; COLOR: #fff; PADDING-TOP: 2px; BORDER-BOTTOM: #000099 1px solid; BACKGROUND-COLOR: #000099
}
DIV.digg SPAN.disabled {
	BORDER-RIGHT: #eee 1px solid; PADDING-RIGHT: 5px; BORDER-TOP: #eee 1px solid; PADDING-LEFT: 5px; PADDING-BOTTOM: 2px; MARGIN: 2px; BORDER-LEFT: #eee 1px solid; COLOR: #ddd; PADDING-TOP: 2px; BORDER-BOTTOM: #eee 1px solid;
}

.checkGroup {
	padding: 10px 0px;
}	
.checkTitle{
	float:left;
	display:block;
	line-height:150%;
	color:#1B6CAB;
}	
.resultCount{
	background-color:#ECECEC;
	line-height:150%;
	font-size:16px;
	height:24px;
	border-bottom: 1px solid #ddd;
}
.title {
	color:#1B6CAB;
}
#contentfield dl dd{
	margin-left:8px;
	padding-right:10px;
}
#contentfield dl dd p{
	margin-top:3px;
	margin-bottom:5px;
}
.hr{ 
	height:1px;
	border:none;
	border-top:1px dashed #dec;
}
#search-submit {
	width:69px;
	height:26px;
	background:url('../../images/search/search_btn.jpg') #EBEDDF no-repeat;
}
.checkBoxGroup{
	border:1px solid #CEC;
	text-align:left;
	width:100%;
	margin:0 auto;
	background:url('../../images/search/checkbox_bg.jpg');
	background-repeat: repeat-y;
	background-repeat: repeat-x;
}
#search {
	margin:0 auto;
	height:30px;
	background-color:white;
	padding:0px;
	border:2px solid #4AA0DC;
	overflow-y:hidden;
}
.qinput {
	height:26px;
	border:0px;
	margin:0px;
	line-height:26px;
	width:100%;
}

	</style>
	<script type="text/javascript">
    var searchurl="<%=request.getContextPath()%>/search-pages/searchResult2.jsp?url=";
	var listrul="http://172.30.7.250:890/select?q=";
	var navuurl="http://172.30.7.250:8080/vdp/navi.vdp";
	
	$(document).ready(function() { 
     

     //checkbox���ʱ
      $('#advanced-search input').click(function () { 
          this.blur();   
          this.focus(); 
          overwriteSumit(1);
       }); 
       
       //���������ťʱ
        $("#search-submit").click(function(){
        overwriteSumit(1);
      });
     /*
 
     
	$("#contentfield").load(searchurl+listrul,function() {
	Paging('1');
	reloadTitle();
	});
	
	 
	

    */
    
    }); 
    
    
//ԭ�������滻���⣬����ֻ����ƴ����
function reloadTitle()
{
    var tablecolumn = {
     "REG_BUS_ENT":"ENT_NAME",
     "REG_BUS_ENT_HS":"ENT_NAME",
     "EXC_QUE_REG":"ENT_NAME",

     "REG_BUS_INV":"INV",

     "REG_BUS_ENT_MEM":"NAME",
     "REG_INDIV_OPERATOR":"NAME",
     "CASE_BUS_CASE":"NAME",

     "REG_INDIV_BASE":"INDI_NAME",
     "REG_INDIV_BASE_HS":"INDI_NAME",

     "ENTER_BLACK":"ENT_NAME",
     "ENTER_ENTERPRISEBASE":"ENT_NAME"
    };
	$("#s-results p").each(function(){
	
		var tableName = $(this).find(".SOURCE_TABLE").html();
		var reg_bus_ent_id = $(this).find(".REG_BUS_ENT_ID").html();
		var REG_INDIV_BASE_ID = $(this).find(".REG_INDIV_BASE_ID").html();
		var ent_sort = $(this).find(".REG_BUS_ENT_ID").html();
		var ent_name =  $(this).find(".ENT_NAME").html();
		var reg_no =  $(this).find(".REG_NO").html();
		var entid =  $(this).find(".PRIPID").html();
		
		var href="";
		
			if (tableName=="REG_INDIV_BASE" || tableName=="REG_INDIV_OPERATOR" || tableName=="Reg_Indiv_Base_Hs" ){ //����
				href += "/txn60110008.do?primary-key:reg_bus_ent_id=" + REG_INDIV_BASE_ID;
			}else if(tableName=="ENTER_BLACK"){    //���� ��������Ҫ�����ж� 
		    	 href +=  "txn60115010.do?select-key:nodes=HP&inner-flag:open-type=new-window&select-key:ent_sort=HP&select-key:entid=" + reg_bus_ent_id
					+ "&select-key:ent_name=" + ent_name 
					+ "&select-key:reg_no=" + reg_no
					+ "&select-key:ent_blk_one_id=" + reg_no
					+ "&inner-flag:flowno=" + reg_no
					+ "&inner-flag:flowno=" + reg_no;
					
					
			}else if(tableName=="EXC_QUE_REG"){ //����ҵ
				href +=  "/txn60114003.do?select-key:exc_que_reg_id=" + reg_bus_ent_id;
			}else if(tableName=="CASE_BUS_CASE"){ //����ǰ��������
				href +=  "/txn60130004.do?primary-key:case_bus_case_id=" + reg_bus_ent_id;
			}else if(tableName=="ENTER_ENTERPRISEBASE"){ //�Ǽ� ��ʵ�����ݲ���
				href +=  "/txn60119001.do?select-key:exc_que_reg_id=" + reg_bus_ent_id;
			}else{//��ҵ������Ͷ���ˣ���Ҫ��Ա�������� ����ʵ�����
				href +=  "/txn60110001.do?primary-key:reg_bus_ent_id=" + reg_bus_ent_id;
			}
		
		
		var ret =$(this).find("."+ tablecolumn[tableName]).html();
		
		
		$(this).prev().find(".s-title").html("<a href='"+href+"' target='_blank' >"+ ret +"</a>");
		
		$(this).find("span").each(function(){
		       if($(this).html()=="")
		       { 
		         $(this).parent().hide();
		       }
		});
		
	});
}


function overwriteSumit(pagenum) {
	    var rows=10;
		var href = listrul;
		href += getFormDate(href);//ƴ��ѯsql
	    href += "&rows="+rows+"&start="+rows*(pagenum-1);
	    href += "&hl=true&hl.simple.pre=%3Cfont+color%3D'red'%3E&hl.simple.post=%3C/font%3E&hl.fl=title&hl.fl=text&hl.snippets=3&hl.fragsize=50&hl.usePhraseHighlighter=true";
		href=searchurl+escape(href);
	    getXmltoHtml(href,pagenum,rows);//����XMLƴҳ��
        reloadTitle();
	
}

function getXmltoHtml(href,pagenum,rows){
    $.get(href, function(d){
        
        
        d=d.replace(/[\r\n]/g,"");
        var numFound = $("<XML>"+d+"</XML>").find('result').attr("numFound");
		if (typeof numFound == "undefined")
        {
         numFound = 0;
        }
        Paging(pagenum,numFound,rows);//ƴҳ��
        $('#contentfield').html("");
        $('#contentfield').append('<div class="resultCount"> <b>���ҵ���ؽ��</b> <font color="red">'+numFound+'</font> <b>��</b></div>');
        $('#contentfield').append('<dl />');
        $("<XML>"+d+"</XML>").find('doc').each(function(){

            var $doc = $(this); 
            var id = $doc.find('str[name="id"]').text();
            var title="";
            var content = "";
            var $light = $("<XML>"+d+"</XML>").find('lst[name="'+id+'"]');
            var Ltitle = $light.find('arr[name="title"]').text();
            var Lcontent = $light.find('arr[name="text"]').text();
            var viewtext = $doc.find('arr[name="viewtext"] str').text();
            if(Ltitle=="")
            {
             title = $doc.find('str[name="title"]').text();
            }
            else
            {
             title = Ltitle;
            }
            
             if(Lcontent=="")
            {
             content = $doc.find('arr[name="text"]').text();
            }
            else
            {
             content = Lcontent;
            }
           // var title = $doc.find('str[name="title"]').text();
			//var content = $doc.find('arr[name="text"]').text();
				
			var html = '<dd>';
            html += '<p class="title">' + title + '</p>';
            html += '<p> ' + content + '</p>' ;
            html += '<p> ' + viewtext + '</p>' ;
			html += '<hr class="hr" />';
            html += '</dd>';
            $('dl').append($(html));
            
        });
     });
}

function getFormDate(href) {
	var inputMap = {};
	var form = $("#search, #advanced-search");
	$("input", form).each(function() {
	    if (this.type == "button") {
	    return;
	    }
		if (this.type == "checkbox") {
			if (this.checked) { putInputMap(inputMap, this); }
			return;
		}
        
        if ($.trim(this.value) != "") 
        {
            putInputMap(inputMap, this);
        }
	});
	var params = "";
	var paramsQ = "";
	var paramsState = "";
	var paramsType = "";
	var paramsDom = "";
	
	for (var key in inputMap) {
	     var strs= new Array();
	     var tmpStr="";
        if(inputMap[key] != "disabled")
        {
            if(key == "q")
            {
            paramsQ +=  inputMap[key];
            }
            if(key == "entState")
            {
              
              strs = inputMap[key].split(";");
              for (i=0;i<strs.length ;i++ ) 
              {
              tmpStr += key + ":" + strs[i] + " OR ";
              }
              paramsState = tmpStr;
            }
            if(key == "entType" || key == "table_name" )
            {
              strs = inputMap[key].split(";");
              for (i=0;i<strs.length ;i++ ) 
              {
              tmpStr += key + ":" + strs[i] + " OR ";
              }
            
              paramsType = tmpStr;
            }
            if(key == "Dom")
            {
              strs = inputMap[key].split(";");
              for (i=0;i<strs.length ;i++ ) 
              {
              tmpStr += key + ":" + strs[i] + " OR ";
              }
              paramsDom = tmpStr;
            }
            
        }
	}
	
	if(paramsQ!="")
	{
		params += paramsQ ;
	}
	if(paramsState!="")
	{
		paramsState=paramsState.substring(0, paramsState.length-4);
	    if(params!="")
	    params += " AND ";
		params += "(" + paramsState +")";
	}
	if(paramsType!="")
	{
		paramsType=paramsType.substring(0, paramsType.length-4);
		 if(params!="")
	    params += " AND ";
		params += "(" + paramsType +")";
	}
	if(paramsDom!="")
	{
		paramsDom=paramsDom.substring(0, paramsDom.length-4);
		 if(params!="")
	    params += " AND ";
		params += "(" + paramsDom +")";
	}
	
	
	return params;
}

function putInputMap(inputMap, input) {
	if (inputMap[input.name]) {
		inputMap[input.name] = inputMap[input.name] + ";" + input.value;
	} else {
		inputMap[input.name] = input.value;
	}
}



function Paging(num,countNum,rows)
{
	var PageSize = rows;
	var pagenums =  Math.ceil(countNum/PageSize);
	var PageNum = num;
	var str="";
	
	str+= "<a href='#' onclick=overwriteSumit("+1+")> ��ҳ </a>";
	if(PageNum==1)
	{
	str+= "<span class='disabled'>&lt; </span>";
	}
	else
	{
	str+= "<a href='#' onclick=overwriteSumit("+(PageNum-1)+")> &lt; </a>";
	}
	
	for(var i=1;i<pagenums+1;i++)
	{
	  if(i==PageNum)
	  {
	  str+= "<span class='current'>"+i+"</span>";
	  }
	  else if( -7<i-PageNum && i-PageNum<7 )
	  {
	  str+= "<a href='#' onclick=overwriteSumit("+i+")>"+i+"</span>";
	  }
	}
	str+= "<a href='#' onclick=overwriteSumit("+(PageNum+1)+")> &gt; </a>";
	str+= "<a href='#' onclick=overwriteSumit("+pagenums+")> βҳ </a>";
    $("#digg").html(str);
    
   // return "&PageNo="+(PageNum-1);
}
function setfocus(){
	document.getElementById("q").focus();
}
function __userInitPage()
{
	setfocus();
}

_browse.execute( '__userInitPage()' );

</script>
<freeze:body>
<div style="width:100%;margin:0 auto;text-align:center;">
	<div style="width:95%;margin:10px auto;text-align:left;">
	<div onmouseover="setfocus();" id="search">
	<table style="margin:0 auto;" border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr style="padding:0px;">
		<td height="26px;">
			<input type="hidden" name="rele"  id="rele" value="0" />
			<input type="hidden" name="sort" id="sort" value="disabled" />
			<input type="text" name="q" id="q" class="qinput" value=""/></td>
		<td bgcolor="#FA961A" width="69px" height="26px"><span id="search-submit"></span></td></tr>
	</table>
	</div>	
	<br />
	<div class="checkBoxGroup" id="advanced-search"> 
	<div class="checkGroup">
        <div class="checkTitle"><b>&nbsp;&nbsp;����״̬��</b></div>
		<div style="float:left;display:block;">
		<span><input type="checkbox" value="1;4;5" name="entState" />��ҵ</span>
		<span><input type="checkbox" value="2" name="entState" />����</span>
		<span><input type="checkbox" value="3" name="entState" />ע��</span>
		<span><input type="checkbox" value="6" name="entState" />����</span>
		<span><input type="checkbox" value="7" name="entState" />����ת��ҵ</span>
		</div>
    </div>
	<br />
	<div class="checkGroup">
        <div class="checkTitle"><b>&nbsp;&nbsp;������ࣺ</b></div>
		<div style="float:left;display:block;">
		<span><input type="checkbox" value="ENTER_ENTERPRISEBASE" name="table_name" />�ֵܾǼ�</span>
		<span><input type="checkbox" value="Exc_Que_Reg" name="table_name" />����ҵ</span>
		<span><input type="checkbox" value="Reg_Indiv_Base" name="table_name" />����</span>
		<span><input type="checkbox" value="Enter_Black" name="table_name" />�ֺܾ�����ҵ</span>
		<span><input type="checkbox" value="JG" name="entType" />�������</span>
		<span><input type="checkbox" value="NZ" name="entType" />����</span>
		<span><input type="checkbox" value="SQ" name="entType" />˽Ӫ</span>
		<span><input type="checkbox" value="WZ" name="entType" />����</span>
		<span><input type="checkbox" value="Case_Bus_Case" name="table_name" />������Ϣ</span>
		</div>
    </div>
	<br />
	<div style="margin-bottom:10px;" class="checkGroup">
		<div class="checkTitle"><b>&nbsp;&nbsp;�������ࣺ</b></div>
		<div style="float:left;display:block;">
		<span><input type="checkbox" value="110101" name="Dom" />������</span>
		<span><input type="checkbox" value="110102" name="Dom" />������</span>
		<span><input type="checkbox" value="110103" name="Dom" />������</span>
		<span><input type="checkbox" value="110104" name="Dom" />������</span>
		<span><input type="checkbox" value="110105" name="Dom" />������</span>
		<span><input type="checkbox" value="110106" name="Dom" />��̨��</span>
		<span><input type="checkbox" value="110107" name="Dom" />ʯ��ɽ��</span>
		<span><input type="checkbox" value="110108" name="Dom" />������</span>
		<span><input type="checkbox" value="110109" name="Dom" />��ͷ����</span>
		<span><input type="checkbox" value="110111" name="Dom" />��ɽ��</span>
		<span><input type="checkbox" value="110112" name="Dom" />ͨ����</span>
		<span><input type="checkbox" value="110113" name="Dom" />˳����</span>
		<span><input type="checkbox" value="110114" name="Dom" />��ƽ��</span>
		<span><input type="checkbox" value="110115" name="Dom" />������</span>
		<span><input type="checkbox" value="110116" name="Dom" />������</span>
		<span><input type="checkbox" value="110117" name="Dom" />ƽ����</span>
		<span><input type="checkbox" value="110228" name="Dom" />������</span>
		<span><input type="checkbox" value="110229" name="Dom" />������</span>
		<span><input type="checkbox" value="110302" name="Dom" />���ھ��ü���������</span>
		</div>
	    </div>
	</div>
	</div>
	
	<div id="resultfield" style="text-align:left;width:95%;border:1px solid #ECECEC;margin:0 auto;background-color:white;">
	<div id="contentfield"></div>
	</div>
	<div id="queryHelpDiv" style="display:block">
	
	<div class="digg" id="digg">
	
	</div>
	<div style="display:none">
  	<h3>ʹ��˵����22</h3>
  	<ul>
		<li>1��������Ӧ������������������������������������ĵǼ���Ϣ��ϵͳ�����������ʡ����ʡ�˽Ӫ�����塢����ҵ�����ơ�����������������Ϣ��</li>
		<li>2������������Ҫ��
			<ul>
				<li>a)	�������һ�����֣�������������(��'������"��)��б��(��\��)�������ַ�������ֻ���롰�����С������й���������������������˾�������֡�</li>
				<li>b)	���������������ϴ󣬽�����ý�Ϊ�����Ĺؼ��ֽ��������Ի��������������</li>
				<li>c)	ϵͳ��������������������������������ָ�������Ϣ��������������й����������������������(�й�)���޹�˾���ڼ��������ֹ�˾���������м����������ŵĽ����</li>
				<li>d)	�����Ҫ����ؼ���������ʱ�򣬿��Բ��ÿո�ָ��ؼ��ֵ���ʽ���硰�й� ���������������</li>
				<li>e)	���ÿո�ָ����Ĺؼ��ֲ����Ⱥ�˳���硰�й� ������͡���� �й������������һ��</li>
			</ul>
		</li>
		<li>3��ע��������������������ʱ����֡����ݽ��շ����仯������ղ�ѯ������ʾ���������ڽ��ղ����˱�������ڴ��������²�ѯ��</li>
	</ul>
	</div>
  </div>
  </div>
</freeze:body>
</freeze:html>
