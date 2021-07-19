<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/search/trs/js/simplePagination.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/search/trs/js/jquery.simplePagination.js"></script>
<style>
.trs-table {
	border-collapse: collapse;
	cellpadding:5px;
}
.trs-table td {
	padding: 5px 5px 12px 20px;
	
}
.trs-content {
	margin-top: 6px;
	line-height:150%;
}
.even {
	background-color: #fff;
}
.odd {
	background-color: #EEE;
}
.body-div{background-image:none;}
</style>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
Recordset rs = null;
Recordset stats =null;
int currentPage =0;
int countPerPage = 10;
int totalPage = 0;
long totalCount = 0l;
StringBuffer sb = new StringBuffer();

if(context!=null){
	DataBus dbPage = context.getRecord("page");
	if(dbPage!=null){
		totalCount = dbPage.getLong("totalCount");
		currentPage = dbPage.getInt("currentPage");
		countPerPage = dbPage.getInt("countPerPage");
		totalPage = dbPage.getInt("totalPage");
	}
	rs = context.getRecordset("record");
	
	stats = context.getRecordset("stats");
	
	if(stats!=null&&stats.size()>0){
		for(int j=0; j<stats.size();j++){
			//System.out.println("[�� "+(j+1)+"�� \n" +stats.get(j) +"]");
			DataBus  dbs = stats.get(j);
			String str = dbs.getValue("str");
			if(j==0){
				sb.append( str );
			}else{
				sb.append( "&" ).append(str);
			}
		}
	}
	
	
}
String str = sb.toString();
//System.out.println(str.replaceAll(" ",""));
%>

<style>

/*page loading*/

 div.loading-invisible{
    /*make invisible*/
    display:none;
  }

  /*this is what we want the div to look like
    when it IS showing*/
  div.loading-visible{
    /*make visible*/
    display:block;
    align:center;
    /*position it 200px down the screen*/
    position:absolute;
    top:200px;
    left:20%;
    width:70%;
    text-align:center;
    color:#ffffff;
    font-size:15px;
    height:50px;
    /*in supporting browsers, make it
      a little transparent*/
    background:#1A75C8;
    filter: alpha(opacity=75); /* internet explorer */
    -khtml-opacity: 0.75;      /* khtml, old safari */
    -moz-opacity: 0.75;       /* mozilla, netscape */
    opacity: 0.75;           /* fx, safari, opera */
    border-top:1px solid #ddd;
    border-bottom:1px solid #ddd;
  }
  
.trs-title a{
	font-size: 14px;
	text-decoration: underline;
}  
.keyword{
	height: 20px;
	float: left;
	color: #333;
	border: 1px solid #E6E6F6;
	margin: 2px;
	line-height: 20px;
	overflow-y: hidden;
	font-size: 12px;
	white-space: normal;
	max-width:670px;
	padding-left: 5px;
}
.keyword img{
	position:relative;
	top: 2px;
}
.keys{
	width:95%;
	font-size:14px;
	margin-left:40px;
	white-space: normal;
	margin-bottom:10px;
}
.selectCon{
	float:left;
	margin-left:0px;
	margin-top:4px;
	height:20px;
	line-height:20px;
	color: black;
	font-size:12px;
	font-weight: bold;
}
</style>
<script type="text/javascript">
//������Ϣ�ύ����ʾ�������ڽ�����
function showLoading(){
	 document.getElementById("loading").className = "loading-visible";
}

function setPageInfo(currentPage,countPerPage){
	parent.setPageInfo(currentPage,countPerPage);
}

function query(){
	parent.query();
}
function createStats(str){
	parent.createStats(str);
}
function setStats(str){
	parent.setStats(str);
}
function show(){
	parent.showFrame();
}
function hideStats(){
	parent.hideStats();
}

function toSub(url){
   //alert(url);
   parent.window.subQuery(url);
}

function __userInitPage(){
	$("#trs-result tr:even").addClass("even");
	$("#trs-result tr:odd").addClass("even");
//��ȡ�ϴεĲ�ѯ����

	var totalPage = "<%=totalPage%>";
	var totalCount ="<%=totalCount%>";
	var currentPage = "<%=currentPage%>";
	var countPerPage = "<%=countPerPage%>";
	var str ="<%=str%>";
	
	if(totalPage>0){
		//�������н����ʱ
		//���ɷ�ҳ��Ϣ
		$('#page').pagination({
	        items: totalCount,
	        itemsOnPage: countPerPage,
	        currentPage:currentPage,
	        cssStyle: 'light-theme' ,
	        prevText:'ǰһҳ',
	        nextText:'��һҳ'
	    });
	    //���ɷ�����Ϣ
	   //if(str!=""){
	  
	   	 createStats(str);
	   	// setStats(str);
	   //}
	}else{
	   	 hideStats();
	}	
	
	//�󶨷�ҳ����¼�
	$('.page-link').click(function(){
		var s = $(this).attr('href');
		var a = s.split('#page-');
		setPageInfo(a[1],countPerPage)
		query();
	})
}

_browse.execute( '__userInitPage()' );

</script>

<freeze:body>
<p style="font-size:14px;margin-left:40px;" > �����������<font color=red><%=totalCount%></font>��</p>
<% 
String strWhere=context.getRecord("select-key").getValue("strWhere");
if(null != strWhere && !strWhere.trim().equals("")){
	  //ȥ����һ��and
	  strWhere=strWhere.replaceFirst("and","").trim();
	  String[] stn= strWhere.split("and");
	  out.print("<div class=\"keys\"> <div class=\"selectCon\">����������:</div>");
	  System.out.println("strWhere====>>>>> "+request.getContextPath());
	  for(int i=0;i<stn.length;i++){
	    out.print("<div onmouseover=\"this.style.border='1px solid #333'\" onmouseout=\"this.style.border='1px solid #e6e6e6'\"");
	    String condition="";
	    if(stn[i].indexOf("(")==-1){
	    	condition=stn[i].replaceAll("=",":");
	    }else{
	        String orCond=stn[i].substring(1,stn[i].indexOf(")"));
	        String[] conds=orCond.trim().split("or");
	        for(int j=0;j<conds.length;j++){
	        	condition+=","+conds[j].trim().split("=")[1];
	        }
	        condition=condition.substring(1);
	    }
	    out.print(" class='keyword'>"+condition);
	    out.print("<a href='javascript:void(0);' onclick='toSub(\""+stn[i]+"\");'>");
	    out.print("<img src='/images/closeCond.jpg' style='border:0;'/></a></div>");
	  }
	  out.print("</div>");
}

if(rs!=null&&rs.size()>0){
							%>
						
							<table id="trs-result" class="trs-table" cellspacing="0" cellpadding="0" width="100%" border="0" align="center" >
							<%
							for(int j=0; j<rs.size(); j++){
								DataBus dbr = rs.get(j);
								%>
							<tr height="30px" ><td><%= dbr.getValue("resultStr")%></td></tr>
								<% 
							}%>
							</table>
							<%
						}
					%>
					
					<!-- ��ҳ��Ϣ -->
					<div id="page" style="margin-left: 20px;"></div>
				
</freeze:body>
<script language="javascript">

</script>
</freeze:html>
