<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>查询范围定义</title>
<style type="text/css">
#totalTableDiv .leftTitle{
	background: url(/module/layout/layout-weiqiang/images_new/r_list_l.jpg) no-repeat !important;
}
#totalTableDiv .secTitle{
	background: #006699 !important;
}
#totalTableDiv .rightTitle{
	background: url(/module/layout/layout-weiqiang/images_new/r_list_r.jpg) no-repeat !important;
}
</style>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/stepHelp.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/connectConditionPluginJoin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/queryConditionPlugin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/generateTotalTable.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/page/insert-sys_svr_service2.js"></SCRIPT>
</head> 
<freeze:body>
	<freeze:title caption="新建"/>
	<form action="preview.jsp" method="post" target="pageList_frameX" style="margin:0;padding:0">
		<input  type="hidden" id="record:query_sql"  name="record:query_sql" />
		<input  type="hidden" id="record:columnsEnArray"  name="record:columnsEnArray" />
		<input  type="hidden" id="record:columnsCnArray" name="record:columnsCnArray" />
		<input  type="hidden" id="record:connStr"  name="record:connStr" />
	    <input  type="hidden"  id="record:sysParams"  name="record:sysParams" />
		<div id="stepsContainerDiv" ></div>
		<div id="step1DIV">
			<table width="95%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr><td >
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td class="secTitle">&nbsp;数据表查询条件</td><td class="rightTitle"></td></tr>
						</table>
					</td>
				</tr>
			    <tr class="framerow">
		          <td style="padding:0px;border:2px solid #006699;"><div id="div1" class="odd2" style="height:100%;width:100%;"></div></td>
		        </tr>
		    </table>	
		    <div id="div2" style="margin-top:10px;"></div>
	        <p><center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='toStep2Button' class="menu" value='下一步' />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='goBack0' class="menu" value=' 返 回 '
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			 </center>
		 </p>
        </div>
        
	    <div id="step2DIV" style="display:none"></div>
	    <div id="step2ButtonDiv" style="display:none">
	    	<select id="tempSelect" name="tempSelect" style="display:none"></select>
	    	<p>
	    	<center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type="button" id="preStep1Button" class="menu" value="上一步" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='toStep3Button' class="menu" value='下一步' />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type="button" id="goBack1" class="menu" value=" 返 回 "
								onclick="goBack();" />

						</td>
						<td class="btn_right"></td>
					</tr>
				</table>

				<input type='button' id='showQctConnectionStr' class="menu" value="获取连接条件" style="display:none;"/> 
	     
	     	</center>
	       </p>
	    </div>
        
        
        <div id="step3DIV" style="display:none">
        	<table width="95%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr><td >
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td class="secTitle">数据表查询条件</td><td class="rightTitle"></td></tr>
						</table>
					</td>
				</tr>
			    <tr class="framerow">
		          <td style="padding:0px;border:2px solid #006699;"><div class="odd2" id="columnsContainerDiv" style="height:100%;width:100%;"></div></td>
		        </tr>
		    </table>	
		</div>
        <div id="step3ButtonDiv" style="display:none">
        	<p><center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='preStep2Button' class="menu" value="上一步" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='preViewButton' class="menu" value="下一步" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='goBack2' class="menu" value=" 返 回 "
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			</center></p>
        </div>
         
        <div id="step4DIV" style="display:none">
        	<div id="totalTableDiv"></div>
        	<IFRAME id="pageList_frameX" name="pageList_frameX" frameBorder=0 width="95%" align="center" height="300"></IFRAME>
        </div>
        <div id="step4ButtonDiv" style="display:none">
        	<p><center>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='preStep3Button' class="menu" value="上一步" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<input type='button' id='saveButton' class="menu" value=" 保 存 " />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>

							<input type='button' id='goBack3' class="menu" value=" 返 回 "
								onclick="goBack();" />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
			</center></p>
        </div>
	</form>
</freeze:body>
<script type="text/javascript">
	window.rootPath = "<%=request.getContextPath()%>";
	var doUpdate = null;
	var ajaxXml = null;
//jufeng 调整step导航部分居总显示
	var s  = Math.round ( $('#step1DIV').width()*0.05/2 )
	var s2 = s+'px'
	$('#stepsContainerDiv').css('margin-left',s2);

var qcObj = null;
var qcObjTemp = new Array;
var qcObjTempOld = new Array;

//从selected_table获取已选择表信息
function setTempFromSelectedTable(){
	var selectedTable = document.getElementById("selected_table")
	if(selectedTable!=null&&selectedTable.options.length>0){
		for(var j=0; j<selectedTable.options.length; j++){
			var e  = selectedTable.options[j] ;
			//e.getAttribute('tblName') + " -- " +  e.value + " -- "+ e.text
			var tblNo = e.value;
		    var tblName =  e.getAttribute('tblName')
		    var tblNameCn = e.text;
		    qcObjTemp.push({"tblNo":tblNo,"tblNameCn":tblNameCn,"tblName":tblName})
		}
	} 
}

document.getElementById('showQctConnectionStr').onclick = function(){
		document.getElementById('record:connStr').value = qcObj.getQueryConditionStr() 
}
</script>
</freeze:html>
