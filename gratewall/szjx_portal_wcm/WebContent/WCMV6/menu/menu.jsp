<%--@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" --%>
<%-- /* wenyh@2007-07-30 使用@include 指令的文件不需要再指定contentType */ --%>
<%@ page import="com.trs.service.IUserService" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<div class="menuBar" id="menuBar">
	<div class="menu">
		<div class="menuDesc">快速通道(<span class="hotKey">Q</span>)</div>
		<div class="menuContent" style="display:none;">
			<div class="menuItem">
				<div class="menuItemDesc" params="execCommand:'quickLocate'">快速定位</div>
			</div>
			<!--
			<div class="menuItem">
				<div class="menuItemDesc hasChild">切换站点区</div>
				
				<div class="menuItemContent" style="display:none;">
					<div class="subMenuItem">
						<div class="subMenuItemDesc">所有站点</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc">公司站点</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc">我管理的站点</div>
					</div>
				</div>
				
			</div>
			<div class="menuItem">
				<div class="menuItemDesc hasChild">我定制的栏目</div>
				<div class="menuItemContent" style="display:none;">
					<div class="subMenuItem">
						<div class="subMenuItemDesc">新闻中心</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc">财务快报</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc">技术交流</div>
					</div>
				</div>
			</div>
			//-->			
		</div>
	</div>
	<div class="menu">
		<div class="menuDesc">操作任务(<span class="hotKey">O</span>)</div>
		<div class="menuContent" style="display:none;">
			<div class="menuItem">
				<div class="menuItemDesc hasChild">新建</div>
				<div class="menuItemContent" style="display:none;">
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateNew',operKey:'new', mgr:'WebSiteMgr',identify:'newWebSite'">站点</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateNew',operKey:'editFromMenu', mgr:'ChannelMgr'">栏目</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateNew',operKey:'new',mgr:'DocumentMgr'">文档</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateNew',operKey:'new',mgr:'TemplateMgr'">模板</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateNew',operKey:'new',mgr:'ExtendFieldMgr'">扩展字段</div>
					</div>
					<!--<div class="subMenuItem"><div class="Desc moreItem"></div></div>//-->
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateNew',operKey:'add',mgr:'DistributionMgr'">站点分发</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateNew',operKey:'add',mgr:'ReplaceMgr'">替换内容</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateNew',operKey:'add',mgr:'DocSynDisMgr'">栏目分发</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateNew',operKey:'add',mgr:'DocSynColMgr'">栏目汇总</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateNew',operKey:'add',mgr:'WorkflowMgr'">工作流</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateNew',operKey:'upload',mgr:'PhotoMgr'">图片</div>
					</div>
				</div>
			</div>
			<div class="menuItem">
				<div class="menuItemDesc hasChild">导入</div>
				<div class="menuItemContent" style="display:none;">
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateImport',operKey:'import',mgr:'WebSiteMgr',identify:'importWebSite'">站点</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateImport',operKey:'import',mgr:'ChannelMgr'">栏目</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateImport',operKey:'import',mgr:'DocumentMgr'">文档</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateImport',operKey:'import',mgr:'TemplateMgr'">模板</div>
					</div>
				</div>
			</div>
			<div class="menuItem">
				<div class="menuItemDesc hasChild">导出</div>
				<div class="menuItemContent" style="display:none;">
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateExport',operKey:'export',mgr:'ChannelMgr'">所有子栏目</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateExport',operKey:'exportall',mgr:'ChnlDocMgr'">所有文档</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'execOperateExport',operKey:'export',mgr:'TemplateMgr'">所有模板</div>
					</div>
				</div>
			</div>
			<div class="menuItem" style="display:none;"><div class="separate"></div></div>
			<div loaded="false" loadTime="everyTime" getItems="getOperators" ignore="1" style="display:none;">
				<div></div>
			</div>
		</div>
	</div>
	<div class="menu">
		<div class="menuDesc">视图(<span class="hotKey">V</span>)</div>
		<div class="menuContent" style="display:none;">
			<div class="menuItem">
				<div class="menuItemDesc checkItem" params="execCommand:'checkMenu',identify:'navigate_bar'">导航栏</div>
			</div>
			<div class="menuItem">
				<div class="menuItemDesc checkItem" params="execCommand:'checkMenu',identify:'attribute_bar'">属性栏</div>
			</div>
			<div class="menuItem">
				<div class="menuItemDesc hasChild"  params="identify:'workListWin'">工作窗口</div>
				<div class="menuItemContent" style="display:none;">
					<div loaded="false" loadTime="everyTime" getItems="getWorkList" ignore="1" style="display:none;">
						<div></div>
					</div>	
				</div>
			</div>
		</div>
	</div>

	<div class="menu">
		<div class="menuDesc">协作服务(<span class="hotKey">C</span>)</div>
		<div class="menuContent" style="display:none;">
			<div class="menuItem">
				<div class="menuItemDesc hasChild">我的工作列表</div>
				<div class="menuItemContent" style="display:none;">
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'listMyworks',ViewType:'1'">待处理的文档</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'listMyworks',ViewType:'2'">已处理的文档</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="execCommand:'listMyworks',ViewType:'3'">我发起的文档</div>
					</div>
				</div>
			</div>
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'calendar,0'">日程安排</div>
			</div>
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'contact,0'">通讯录</div>
			</div>
			<div class="menuItem">
				<div class="menuItemDesc" params="execCommand:'showSMmList'">短消息</div>
			</div>
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'favorite,0'">收藏夹</div>
			</div>
		</div>
	</div>
	<div class="menu">
		<div class="menuDesc">个人服务(<span class="hotKey">P</span>)</div>
		<div class="menuContent" style="display:none;">
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'myInformation,0'">我的信息</div>
			</div>
			<div class="menuItem">
				<div class="menuItemDesc" params="execCommand:'viewMyRight'">我的权限</div>
			</div>
			<div class="menuItem"><div class="separate"></div></div>
			<div class="menuItem">
				<div class="menuItemDesc" params="execCommand:'individuate'">个性化定制</div>
			</div>
		</div>
	</div>
	<div class="menu">
		<div class="menuDesc">管理工具(<span class="hotKey">M</span>)</div>
		<div class="menuContent" style="display:none;">
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'publicMonitor,0'">发布监控</div>     
            </div>
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'statHome,0'">统计分析</div>     
            </div>
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'actionLog,0'">操作日志</div>     
            </div>
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'userControl,0'">用户管理</div>     
            </div>
		</div>
	</div>
	<div class="menu">
		<div class="menuDesc">系统配置(<span class="hotKey">S</span>)</div>
		<div class="menuContent" style="display:none;">
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'otherconfig,0'">属性配置</div>     
            </div>
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'systemconfig,0'">系统配置</div>     
            </div>
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'sechedual,0'">计划调度</div>     
            </div>
		</div>
	</div>
	<div class="menu">
		<div class="menuDesc">选件(<span class="hotKey">X</span>)</div>
		<div class="menuContent" style="display:none;">
			<div class="menuItem">
				<div class="menuItemDesc hasChild">内容互动</div>
				<div class="menuItemContent" style="display:none;">
					<div class="subMenuItem">
						<div class="subMenuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'questionnaire,0'">问卷调查</div>
					</div>
					<div class="subMenuItem">
						<div class="subMenuItemDesc"  params="identify:'skipTo',execCommand:'skipTo',Path:'commentonLine,0'">在线评论</div>
					</div>
				</div>
			</div>
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'adPluin,0'">广告选件</div>     
            </div>
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'autoInfor,0'">智能信息处理选件</div>     
            </div>
			<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'infoview_list,0'">自定义表单</div> 				
            </div>
			<div class="menuItem"><div class="separate"></div></div>
			<!--<div class="menuItem">
				<div class="menuItemDesc" params="identify:'skipTo',execCommand:'skipTo',Path:'9,1'">选件配置</div>     
            </div>-->
		</div>
	</div>
	<div class="menu">
		<div class="menuDesc">帮助(<span class="hotKey">H</span>)</div>
		<div class="menuContent" style="display:none;">
			<div class="menuItem">
				<div class="menuItemDesc" style="display:none;">帮助主题</div>     
            </div>
			<div class="menuItem"><div class="separate"></div></div>
			<div class="menuItem">
				<div class="menuItemDesc" params="execCommand:'backfeedOnline',link:'http://www.trs.com.cn'">在线反馈</div>     
            </div>
			<div class="menuItem">
				<div class="menuItemDesc" style="display:none;">检查新版本</div>     
            </div>
			<div class="menuItem"><div class="separate"></div></div>
			<div class="menuItem">
				<div class="menuItemDesc" params="execCommand:'contact',link:'mailto:support@trs.com.cn'">联系我们</div>    
            </div>
			<div class="menuItem">
				<div class="menuItemDesc" params="execCommand:'aboutWCM'">关于WCM</div>     
            </div>
		</div>
	</div>
</div>
<div style="float:right;">
<table width="21" height="24" border="0" cellpadding="0" cellspacing="0">
<tr> 
	<td width="100%">
		<img src="images/include/rdot.gif" width="21" height="27">
	</td>
</tr>
</table>
</div>
<DIV style="position: absolute; right: 20px; top: 2; line-height:29px; font-family: arial" class="user_welcome">
	<span style="width: 20px; height: 17px; background-repeat: no-repeat; background-image:url(./images/icon/icon_user_online.gif);" title="当前登录用户"></span>
	<span class="corner_span"><a href="#" onclick="PageContext.skipToUserInfo(); return false;" class="lnk_main_corner" title="点击转到'我的信息'"><%=loginUser.getName()%>(<%=(loginUser.getNickName() == null) ? loginUser.getName() : loginUser.getNickName()%>)</a></span>
	<span class="corner_delt">|</span>
	<span class="corner_span">
		<span id="spAlertionIcon" title="点击收取短消息" class="alertion_deactive_icon" style="cursor: pointer; width:28px; height:23px; margin-left:-5px; margin-top:-1px; line-height: 23px;" onclick="PageContext.notifyAlertions()"></span><span id="spAlertionNum" style="cursor: pointer; width:12px; height:17px; line-height: 17px;font-size:10pt;font-family:Courier New;" ></span>
	</span>	
	<span class="corner_delt">|</span>
	<span class="corner_span">
		<a href="#" onclick="PageContext.showOnliners(); return false;" class="lnk_main_corner" title="查看在线用户列表">当前在线</a>
	</span>
	<span class="corner_delt">|</span>
	<span class="corner_span">
		<a href="./logout.jsp" class="lnk_main_corner" title="注销此次登录">注销</a>
	</span>
	<span style="margin-left: -5px; display: none">
		<a id="lnkFirer" href="#" onclick="PageContext.showOrderTypes(event); return false;">选项</a>
	</span>
</DIV>
<%
	int nUserId			= loginUser.getId();
	String sUserName	= loginUser.getName();
	String sUserNick	= loginUser.getNickName();
	if(sUserNick == null) {
		sUserNick = sUserName;
	}
%>
<script language="javascript">
<!--
	var m_sUserId		= userId = <%=nUserId%>;
	var m_sUserName		= '<%=sUserName%>';
	var m_sUserNickName = '<%=sUserNick%>';
	var userObjType = 204;
//-->
</script>
<a href="" target="blank" style="display:none;" id="mailToA"></a>


<%
// 根据用户权限，控制菜单的显示
IUserService aUserService = (IUserService)DreamFactory.createObjectById("IUserService");
%>
<script language="javascript">
<!--
	var tabArray = new Array();
	tabArray.push('bSiteTabDisable', false);
	tabArray.push('bDocTabDisable', false);
	tabArray.push('bPubTabDisable', false);
	tabArray.push('bTeamTabDisable', false);
	tabArray.push('bPersonTabDisable', false);
	tabArray.push('bStatTabDisable', <%=!loginUser.isAdministrator()%>);
	tabArray.push('bUserTabDisable', <%=!(loginUser.isAdministrator()|| aUserService.isAdminOfGroup(loginUser))%>);
	tabArray.push('bSysTabDisable', <%=!AuthServer.hasAttributeRight(loginUser)%>);
	tabArray.push('bExtTabDisable', <%=!AuthServer.hasExtendedRight(loginUser)%>);
	// ge gfc add@2008-4-10 20:45 对各个选件进行条件判断
	//评论
	tabArray.push('bExtTabDisable_Comment'	, <%=!AuthServer.hasExtendedRight(loginUser, WCMRightTypes.EXT_COMMENT)%>);
	//调查
	tabArray.push('bExtTabDisable_Poll'		, <%=!AuthServer.hasExtendedRight(loginUser, WCMRightTypes.EXT_INVESTMENT)%>);
	//广告
	tabArray.push('bExtTabDisable_Addintrs'	, <%=!AuthServer.hasExtendedRight(loginUser, WCMRightTypes.EXT_ADMANAGE)%>);
	//智能信息处理
	tabArray.push('bExtTabDisable_Aptitude'	, <%=!AuthServer.hasExtendedRight(loginUser, WCMRightTypes.EXT_APTITUDE)%>);
	//表单
	tabArray.push('bExtTabDisable_Infoview'	, <%=!AuthServer.hasExtendedRight(loginUser, WCMRightTypes.EXT_INFOVIEW_MGR)%>);

	
	var globalTabDisabled = new Array();
	var index = 1;
	for(var i = 0; i < tabArray.length; i+=2){
		globalTabDisabled[tabArray[i]] = tabArray[i+1];
		globalTabDisabled[index++] = tabArray[i+1];
	}
	delete tabArray;

	//mapping
	var v6To52 = {
		calendar		: 'bTeamTabDisable',
		contact			: 'bPersonTabDisable',
		favorite		: 'bPersonTabDisable',
		myInformation	: 'bPersonTabDisable',
		publicMonitor	: 'bPubTabDisable',
		statHome		: 'bStatTabDisable',
		actionLog		: 'bStatTabDisable',
		userControl		: 'bUserTabDisable',
		otherconfig		: 'bSysTabDisable',
		systemconfig	: 'bSysTabDisable',
		sechedual		: 'bSysTabDisable',
		questionnaire	: 'bExtTabDisable_Poll',
		commentonLine	: 'bExtTabDisable_Comment',
		adPluin			: 'bExtTabDisable_Addintrs',
		autoInfor		: 'bExtTabDisable_Aptitude',
		"9"				: "bExtTabDisable",
		infoview_list	: 'bExtTabDisable_Infoview'
	};
//-->
</script>