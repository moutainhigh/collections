

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
									onclick="openWin('${trsbo.url?default("")}?priPid=${trsbo.pid?default("")}') ">
									${trsbo.a?default("")} (12315消保) &nbsp;&nbsp;
								</a>
							</h3>
							<p>
								<b>信息来源:</b>${trsbo.b?default("")}&nbsp;&nbsp;&nbsp;&nbsp;<b>接收方式:</b>${trsbo.c?default("")}&nbsp;&nbsp;&nbsp;&nbsp;<b>登记部门:</b>${trsbo.d?default("")}
								&nbsp;&nbsp;&nbsp;&nbsp;<b>受理登记人:</b>${trsbo.e?default("")}&nbsp;&nbsp;&nbsp;&nbsp;<b>信息类别:</b>${trsbo.f?default("")}
							</p>
							<p>
								<b>事发地:</b>${trsbo.g?default("")}&nbsp;&nbsp;&nbsp;&nbsp<b>注册号:</b>${trsbo.l?default("")}&nbsp;&nbsp;&nbsp;&nbsp;<b>联系电话:</b>${trsbo.k?default("")}
							</p>

							<p>
								<b>地址:</b>${trsbo.i?default("")}
							</p>
							<p>
								<b>所在行业类别:</b>${trsbo.j?default("")}
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
