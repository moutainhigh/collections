
<#assign cou=0>
<#if trslist??&&trslist?size gt 0>
  <#list trslist as trsbo>  
		 <#if trsbo.m=="reg">
				 <#if cou%2==0>
					<div style="text-align: left;" class="sous_list">
				 <#else>
					
					<div style="text-align: left;background-color:#e1e1e1;" class="sous_list">
				 </#if> 
						<div >
							<h3>
								<a href="#"
									onclick="openWin('${trsbo.url?default("")}?enttype=${trsbo.r_enttype?default("")}&flag=0&economicproperty=${trsbo.changeType?default("")}&entstatus=${trsbo.d_regstate?default("")}&opetype=${trsbo.n_opetype?default("")}&priPid=${trsbo.pid_pripid?default("")}&regno=${trsbo.b_regno?default("")}&entid=${trsbo.s_entid?default("")}&entname=${trsbo.a_entName?default("")}') ">
									${trsbo.a_entName?default("")} (登记) &nbsp;&nbsp;
								</a>
							</h3>
							<p>
								<b>统一社会信用代码:</b>${trsbo.c_uniscid?default("")}&nbsp;&nbsp;&nbsp;&nbsp;<b>企业状态:</b>${trsbo.d_cn_regstate?default("")}&nbsp;&nbsp;&nbsp;&nbsp;<b>企业类型:</b>${trsbo.r_cn_enttype?default("")}
								&nbsp;&nbsp;&nbsp;&nbsp;<b>法定代表人:</b>${trsbo.g_name?default("")}&nbsp;&nbsp;&nbsp;&nbsp;<b>成立日期:</b>${trsbo.h_estdate?default("")}
							</p>
							<p>
								<b>行业类别:</b>${trsbo.e_cn_industryphy?default("")}&nbsp;&nbsp;&nbsp;&nbsp<b>行业:</b>${trsbo.f_cn_industryco?default("")}&nbsp;&nbsp;&nbsp;&nbsp;<b>登记机关:</b>${trsbo.i_cn_regorg?default("")}
							</p>

							<p>
								<b>经营范围:</b>${trsbo.k_opscope?default("")}
							</p>
							<p>
								<b>住所:</b>${trsbo.l_dom?default("")}
							</p>
							<p></p>
								<div style="display:none; width:299px; height:100px;">
								</div>
						</div>

					</div>
		<#else>
				<#--   
					其他主题在这里写
				--> 
		
		</#if>	
	<#assign cou=cou+1 />
    </#list> 
</#if>
