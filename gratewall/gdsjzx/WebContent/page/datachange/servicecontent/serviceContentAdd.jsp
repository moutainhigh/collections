<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/datachange/servicecontentAdd.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/datachange/servicecontent.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/static/css/datachange/servicecontent.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="33%" dataurl="">
		<div name='fwnrid' vtype="hiddenfield" label="ID" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='servicecontentname' vtype="textfield" label="内容名称" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='isenabled' vtype="radiofield" dataurl="[{checked:true,value: '0',text: '启用'},{value: '1',text: '停用'}]" label="状态" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='servicecontentshow' vtype="textareafield" label="内容描述" colspan="2" height="60" labelAlign="right" width="850" labelwidth="130" rule="must"></div>
		<div name='n' vtype="textfield" label="内容配置" labelAlign="left" width="850" labelwidth="130" readonly="true" colspan="2"></div>
	</div>
	<div name="tab_name" vtype="tabpanel" overflowtype="2" colspan="2" tabtitlewidth="130"  width="100%" height="250" 
			 orientation="top" id="tab_name">    
		    <ul>    
		        <li id="litab1"><a href="#tab1">配置数据表</a></li>    
		        <li id="litab2"><a href="#tab2">配置数据项</a></li>
		        <li id="litab3"><a href="#tab3">配置条件项</a></li>
		        <li id="litab4" ><a href="#tab4">预览</a></li>
		    </ul>    
		    <div>    
		        <div id="tab1" style="height: 100%">   
		        	<div id="column_id" width="98%" height="100%" vtype="panel" name="panel" 
				     	 layout="column" layoutconfig="{width: ['30%','30%','*','30%'],border:false}">
				    	 <div style="height: 100%">
				    		<div name="t1" vtype="panel" title="选择主题" titledisplay="true" height="222">
				    			<div name='titlename' id="titleid" vtype="boxlist" dataurl="<%=request.getContextPath()%>/shareResource/selectShareTitle.do" height="100%" width="100%"></div>
				    		</div>
				    	</div>
				    	<div style="height: 100%">
				    		<div name="t2" vtype="panel" title="选择数据表" titledisplay="true" height="222" style="height: 220px;">
				    			 <select size="4" name="listTLeft" id="listTLeft" class="normal" title="双击可实现右移"> </select>  
				    		</div>
				    	</div>
				    	<div style="height: 100%">
				    		<div name="t3" vtype="panel" title="" titledisplay="false" height="222">
				    			<div align="center" style="vertical-align: middle;padding:25px;">
						    		<input value=">" type="button" id="btnTableRight"  class="btn"/>
									<br/><br/>
		 							<input value=">>" type="button" id="btnTableRightAll"  class="btn"/>
		 							<br/><br/>
		 							<input value="<<" type="button" id="btnTableLeftAll" class="btn"/>
		 							<br/><br/>
		 							<input value="<" type="button" id="btnTableLeft" class="btn"/>
	 							</div>
				    		</div>
				    	</div>
				    	<div style="height: 100%">
				    		<div name="t4" vtype="panel" title="已选数据表" titledisplay="true" height="222" style="height: 220px;">
				    			<select size="4" name="listTRight" id="listTRight" class="normal" title="双击可实现左移" ></select> 
				    		</div>
				    	</div>
				    </div> 
		        </div>    
		        <div id="tab2">    
		            <div id="column_id1" width="98%" height="100%" vtype="panel"  
				     	 layout="column" layoutconfig="{width: ['30%','30%','*','30%'],border:false}">
				    	 <div>
				    		<div name="c1" vtype="panel" title="选择数据表" titledisplay="true" height="222" style="height: 220px;">
				    			<select size="4" name="listTable" id="listTable" class="normal"> </select>
				    		</div>
				    	</div>
				    	<div>
				    		<div name="c2" vtype="panel" title="选择数据项" titledisplay="true" height="222" style="height: 220px;">
				    			 <select size="4" name="listCLeft" id="listCLeft" class="normal" title="双击可实现右移" multiple=”multiple”> </select>  
				    		</div>
				    	</div>
				    	<div>
				    		<div name="c3" vtype="panel" title="" titledisplay="false" height="222">
				    			<div align="center" style="vertical-align: middle;padding:25px;">
						    		<input value=">" type="button" id="btnCRight"  class="btn"/>
									<br/><br/>
		 							<input value=">>" type="button" id="btnCRightAll"  class="btn"/>
		 							<br/><br/>
		 							<input value="<<" type="button" id="btnCLeftAll" class="btn"/>
		 							<br/><br/>
		 							<input value="<" type="button" id="btnCLeft" class="btn"/>
	 							</div>
				    		</div>
				    	</div>
				    	<div>
				    		<div name="c4" vtype="panel" title="已选数据项" titledisplay="true" height="222" style="height: 220px;">
				    			<select size="4" name="listCRight" id="listCRight" class="normal" title="双击可实现左移" multiple=”multiple”></select> 
				    		</div>
				    	</div>
				    </div> 
		        </div>  
		          <div id="tab3" style="background:#EEF5FD;border:1px solid #000;"> 
		          		<div style="margin-top:15px;width:100%;height:80%" >
						<p>数据表查询条件 </p>
						<table id="condition" class="gridtable" style="width:100%;">
								<tr><th width="7%">条件</th><th width="6%">括弧</th><th width="20%">数据表</th><th  width="25%">表字段</th><th width="11%">连接关系</th><th width="15%">值</th><th width="6%">括弧</th><th width="10%">操作</th></tr>
							<tr>
								<td><select style="width: 100%;"><option value=" "> </option><option value="and"> and </option><option value="or"> or </option></select></td>
								<td><select style="width: 100%;"><option value=" "> </option><option value="(">(</option><option value="((">((</option><option value="(((">(((</option></select></td>
								<td><p style="width: 100%;"></p></td>
								<td><select style="width: 100%;"></select></td>
								<td><select style="width: 100%;" onchange="changeCondition(this)">
										<option value='='>等于</option>
										<option value='>='>大于等于</option>
										<option value='<='>小于等于</option>
										<option value='!='>不等于</option>
										<option value=' is not null '>非空</option>
										<option value=' is null '>为空</option>
									</select></td>
								<td><input type="text" style="width:100%;height:22px"/></td>
								<td><select style="width: 100%;"><option value=" "> </option><option value=")">)</option><option value="))">))</option><option value=")))">)))</option></select></td>
								<td width="160px">
									<input type="button" onClick="addRow()" value="增加" style="background: #006599;color:#fff"/>
									<input style="display:none;" type="button" onClick="delRow(this)" value="删除" style="background: #006599;color:#fff"/>
								</td>
							</tr>
						</table>
					</div>
							<div id="sql"><p></p></div>
							<input type="hidden" id="tabSel"/>	
		       	   </div> 	
		          <div id="tab4" style="height: 100%">   
		       	  </div>    
		    </div>    
		</div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn3" name="btn3" vtype="button" text="上一步" icon="../../../style/default/images/fh.png" click="lastStep()"></div>
			<div id="btn4" name="btn4" vtype="button" text="下一步" icon="../../../style/default/images/fh.png" click="nextStep()"></div>
			<div id="btn1" name="btn1" vtype="button" text="保存" icon="../../../style/default/images/fh.png" click="save()"></div>
			<div id="btn2" name="btn2" vtype="button" text="关闭" icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
</body>
</html>