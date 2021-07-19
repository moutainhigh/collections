<%
	IPublishFolder folder = (IPublishFolder)PublishElementFactory.makeElementFrom(channel);
	IPublishContent content =  PublishElementFactory.makeContentFrom(document, folder);
	//时间组件初值设置
	CMyDateTime dtScheduleTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
	String sScheduleTime = "";//dtScheduleTime.toString("yyyy-MM-dd HH:MM");
	CMyDateTime dtUnpubTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
	String sUnpubTime = "";//dtUnpubTime.toString("yyyy-MM-dd HH:MM");
	//定时发布时间
	Boolean bDefineSchedule = false;
	if(nObjectId > 0) {
		WCMContentPublishConfig currConfig = new WCMContentPublishConfig(loginUser, content);
		Schedule currSchedule = currConfig.getSchedule();
		bDefineSchedule = (currSchedule != null);
		if(bDefineSchedule) {
			sScheduleTime = currSchedule.getExeTime().toString("yyyy-MM-dd HH:mm");
		}
	}
	//定时撤稿时间
	Schedule unpubJob = null;
	Boolean bDefineUnpubJob = false;
	if(nObjectId > 0){
		unpubJob = getUnpubJob(loginUser,nObjectId);
		bDefineUnpubJob = (unpubJob != null);
		if(bDefineUnpubJob) {
			sUnpubTime = unpubJob.getExeTime().toString("yyyy-MM-dd HH:mm");
		}
	}
%>
<%!
	private static int m_nUnpubWorkerId = 0;
	private Schedule getUnpubJob(User loginUser,int _nDoucmentId) throws WCMException{
		Schedules unpubJobs = new Schedules(loginUser);
		WCMFilter unpubJobsFilter = new WCMFilter("", "SENDERID=? and SENDERTYPE=? and OPTYPE=?", "");
		unpubJobsFilter.setSelect("SCHID,ETIME");
		unpubJobsFilter.addSearchValues(_nDoucmentId);
		unpubJobsFilter.addSearchValues(Document.OBJ_TYPE);
		if(m_nUnpubWorkerId == 0){
			JobWorkerType worker = JobWorkerType.findByClassName(WithDrawJobWorker.class.getName());
			if(worker == null){
				return null;
			}
			m_nUnpubWorkerId = worker.getId();
		}
		unpubJobsFilter.addSearchValues(m_nUnpubWorkerId);
		unpubJobs.open(unpubJobsFilter);
		if(!unpubJobs.isEmpty()){
			return (Schedule)unpubJobs.getAt(0);
		}
		return null;
	}
%>
<div class="label">发布设置</div>
<div class="sep">：</div>
<div class="value">
	<div class="attr_row" style="height:20px;">
		<span class="attr_name" style="width:100px;white-space:nowrap;" _action="defineSchedule">
			<input type="checkbox" id="ip_DefineSchedule" name="DefineSchedule" onclick="onPubjobset()" >
			<label for="ip_DefineSchedule">定时发布:</label>
		</span>
		<span id="sp_PublishOnTime" style="width:200px;padding-left:25px;overflow:hidden;display:none">
			<span>
				<input class="calendarText" type="text" name="ScheduleTime" id="ScheduleTime" value="<%=sScheduleTime%>" elname="定时发布">
				<button type="button" class="DTImg" id="btnScheduleTime"><img src="../../images/icon/TRSCalendar.gif" border=0 alt="" class="img"></button>
			</span>
		</span>
		<span id="sp_NoPublish" style="width:50px;display:none">无</span>
	</div>
	<div class="attr_row" style="height:20px;">
		<span class="attr_name" style="width:100px;white-space:nowrap;">
			<input type="checkbox" name="unpubjob" id="unpubjob" value="" onclick="onPubNojobset();" >
			<label for="unpubjob">定时撤稿:</label>
		</span>
		<span id="unpubjobdatetime" style="width:200px;overflow:hidden;padding-left:25px;display:none">
			<input type="hidden" name="UnpubSchId" id="UnpubSchId" value="<%=String.valueOf((unpubJob==null?0:unpubJob.getId()))%>">
			<input type="text" name="UnpubTime" id="UnpubTime" value='<%=sUnpubTime%>' class="calendarText" elname="定时撤稿">
			<button id="btnCalunpubtime" type="button" class="DTImg"><IMG alt="" src="../../images/icon/TRSCalendar.gif" border=0 class="img"></button>
		</span>
	</div>
</div>
<script>
//时间组件
wcm.TRSCalendar.get({input:'ScheduleTime',handler:'btnScheduleTime',withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
wcm.TRSCalendar.get({input:'UnpubTime',handler:'btnCalunpubtime',withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	//显示组件
	var bSchedule = <%=bDefineSchedule%>;
	var bUnpubJob = <%=bDefineUnpubJob%>;
	if(bSchedule) {
		$("ip_DefineSchedule").click();
	}
	if(bUnpubJob) {
		$("unpubjob").click();
	}
</script>

