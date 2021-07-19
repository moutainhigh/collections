

<#assign cou=0>
<#if trslist??&&trslist?size gt 0>
  <#list trslist as trsbo>  
		           <#if cou%2==0>
					<div style="text-align: left;" class="sous_list">
				 <#else>
					
					<div style="text-align: left;background-color:#BEBEBE;" class="sous_list">
				 </#if> 
						<div >
							<h3>
								<a href="#"
									onclick="openWin('${trsbo.url?default("")}?priPid=${trsbo.pid?default("")}&caseType=${trsbo.w?default("")}') ">
									${trsbo.a?default("")} (案件) &nbsp;&nbsp;
								</a>
							</h3>
							<p>
								<b>统一社会信用代码:</b>${trsbo.j?default("")}&nbsp;&nbsp;&nbsp;&nbsp;<b>案件状态:</b>${trsbo.c?default("")}&nbsp;&nbsp;&nbsp;&nbsp;<b>当事人类型:</b>${trsbo.g?default("")}
								&nbsp;&nbsp;&nbsp;&nbsp;<b>当事人名称:</b>${trsbo.h?default("")}&nbsp;&nbsp;&nbsp;&nbsp;<b>立案日期:</b>${trsbo.e?default("")}
							</p>
							<p>
								<b>销案日期:</b>${trsbo.f?default("")}&nbsp;&nbsp;&nbsp;&nbsp<b>注册号:</b>${trsbo.i?default("")}&nbsp;&nbsp;&nbsp;&nbsp;<b>立案机关:</b>${trsbo.d?default("")}
							</p>

							<p>
								<b>案件编号代码:</b>${trsbo.b_?default("")}
							</p>
							<p>
								<b>证件号码:</b>${trsbo.k?default("")}
							</p>
							<p></p>
								<div style="display:none; width:299px; height:100px;">
								</div>
						</div>

					</div>
		
				<#--   
					其他主题在这里写
				--> 
		
			
	<#assign cou=cou+1 />
    </#list> 
</#if>
