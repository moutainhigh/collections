package cn.gwssi.etlschedule;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gwssi.etlschedule.module.EtlLogBO;
import cn.gwssi.etlschedule.module.EtlRwxxBO;
import cn.gwssi.etlschedule.service.ETLService;
import cn.gwssi.etlschedule.util.SSHHelper;

/**
 * Created by Administrator on 2016/6/20.
 */
public  class  RunJob implements Runnable{
	@Autowired
	public   ETLService service;
	private EtlRwxxBO bo;

	//  SSHHelper exe1=new SSHHelper("10.1.1.136","dsadm","Ds21Adm05",22);
	private  SSHHelper exe1=new SSHHelper("10.1.1.136","root","123456qwerty",22);

	public  RunJob(EtlRwxxBO bo){
		this.bo=bo;
	};

	public RunJob(EtlRwxxBO bo, String newflag) {

		this.bo=bo;
	}

	//	public void run(){
	//		try {
	//			String  runJobShell;
	//			resetJob(jobName,area);
	//			//执行脚本
	//			switch (type){
	//			case "2":
	//				this.runETLJob();
	//			case "1":
	//				//增量：
	//				//                    runJob();
	//
	//			case "3":
	//				//执行cube；
	//				//                    runModelJob();
	//			}
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}



	public    Map runETLJob(String jobName,String state,String flag,String area,String datasource,String type,String sourceFlag,String targetserver,String targetdb
			,String targetpassword,String targetusername,String jobType,String sourceUsername,String sourcePassword)throws  Exception{
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String curDtStr=df.format(new Date()).substring(0, 19);

		if(("nb".equals(jobType)||"qtgs".equals(jobType))&&flag!=null&&!"".equals(flag)){
			flag=flag.substring(0, 10);
		}

		StringBuffer shell=new StringBuffer("/opt/IBM/InformationServer/Server/DSEngine/bin/dsjob -run -mode NORMAL  "    );
		if(bo.getDatasource()!=null) shell .append( "  -param source_server="+datasource);
		if("sczt".equals(jobType)){
			if(area!=null) shell.append(" -param source_db="+area+"aicbiz");
		}else if ("qtgs".equals(jobType)||"nb".equals(jobType)){
			if(area!=null) shell.append("  -param source_db=aiceps");
		}else if("nz".equals(jobType)){
			shell.append("  -param source_db=aicmssw");
		}else if("12315".equals(jobType)){
			shell.append("  -param source_db=gs12315v5");
		}else if("sbzj".equals(jobType)){
			shell.append("  -param source_db=datacenter ");
			flag="'"+flag+"'";
		}else if("sdnb".equals(jobType)){
			shell.append("  -param source_db=sdaiccips ");
		}else if("zhnb".equals(jobType)){
			shell.append("  -param source_db=aiceps_zh ");
		}else if("gzsb".equals(jobType)){
			shell.append(" -param source_db=DB2_ZBK ");
		}else if("szsb".equals(jobType)){
			shell.append(" -param source_db=SQLSERVER_SZ_ZBK ");
		}
		else{
			if(area!=null) shell.append(" -param source_db="+area+"aicbiz");
		}

		if(sourceFlag!=null) shell.append("  -param SOURCEFLAG="+sourceFlag);

		if(type.equals("1")){
			if(flag!=null) shell.append(" -param LASTTIME=" +flag);
		}else{
			shell.append(" -param LASTTIME=\""+curDtStr+"\" " );
		}
		
		if(sourceUsername!=null)  shell.append(" -param source_username="+sourceUsername);
		if(sourcePassword!=null)  shell.append(" -param source_password="+sourcePassword);
		if(targetusername!=null)  shell.append(" -param target_username="+targetusername);
		if(targetpassword!=null) shell.append(" -param target_password="+targetpassword);
		if(targetserver!=null) shell.append(" -param target_server="+targetserver);
		if(targetdb!=null) shell.append(" -param target_db="+targetdb);
		shell.append( " -wait -jobstatus datacenter "+jobName);
		if(area!=null)shell.append("."+jobType+area );
		//执行脚本
		System.out.println("执行调度脚本开始"+shell.toString());
		String result=exe1.exec(shell.toString());
		Map map=new HashMap();
		map.put("lasttime", curDtStr);
		map.put("result", result);
		return map;

	}





	//	public static void main(String[] args) throws Exception {
	//		System.out.println(1);
	//		EtlRwxxBO bo=new EtlRwxxBO();
	//		bo.setJobname("E_DOMINFO");
	//		RunJob runJob=new RunJob(bo);
	//		runJob.runETLJob();
	//	}


	//    //执行中心库作业的子线程函数
	//    public void runJob() throws Exception{
	//        //定义函数参数
	//        String jobName=job.getJobName(),lastRunJobTime=null,jobId=job.getJobId(),tableName=job.getTableName(),areaIdx=job.getAreaIdx();
	//        String curTmStr=null,curAreaIdx=null;
	//        int jobStatus=0,areaNum=areas.size(),runJobNum=job.getJobRunNum(),tryRunJobNum=0,jobSucessCnt=0,excAreaNum=0;
	//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	//        Date lastRunJobTm=null,currentRunJobTm=null;
	//        long jobInterval=job.getJobInterval()*60*1000,lastTime=0,currentTime=0,timeInterval=0,runEndTime=0;
	//        String runJobShell=null;
	//        Area curArea=null;
	//        String areaCode=null,dataSource=null,sourceFlag=null,lastRunJobFlag=null,sourceJobFlag=null;
	//        Job updatedJob=null;
	//        String curDtStr=df.format(new Date()).substring(0, 9);
	//        List cmdList=null;
	//        String[] jobTypeIndexs=jobName.split("_"),excArea=areaIdx.split(",");
	//        String jobTypeIndex=jobTypeIndexs[jobTypeIndexs.length-1],jobLastTimeParam=null;
	//        String jobNameUpdate="";
	//        String url=null,user="gwssidata",pwd="gwssi0802data";
	//        //获取更新时间作业名
	//        if(jobTypeIndex.equals("Incremental")){
	//            for(int i=0;i<jobTypeIndexs.length-1;i++){
	//                jobNameUpdate+=jobTypeIndexs[i];
	//                if(i!=jobTypeIndexs.length-2)jobNameUpdate+="_";
	//            }
	//        }
	//        else{
	//            jobNameUpdate=jobName;
	//        }
	//        System.out.println(jobNameUpdate);
	//
	//        //查询
	//        //循环执行作业
	//        int j=0;
	//        while((runJobNum==0||j<runJobNum)&&curDtStr.equals(stDtStr)){
	//            if(runJobNum!=0)j++;
	//            else j=0;
	//
	//            timeInterval=0;
	//            //查询当前脚本上次运行时间
	//            lastRunJobTime=myDBconn.getRunJobTime(jobNameUpdate);
	//            if(lastRunJobTime!=null)curDtStr=lastRunJobTime.substring(0,9);
	//            //执行满足运行条件的脚本
	//            while(timeInterval<jobInterval){
	//
	//
	//                //计算上次运行时间到当前时间的时间间隔
	//                timeInterval=0;
	//                currentRunJobTm=new Date();
	//                curTmStr=df.format(currentRunJobTm);
	//                if(lastRunJobTime!=null){
	//                    lastRunJobTm=df.parse(lastRunJobTime);
	//                    lastTime=lastRunJobTm.getTime();
	//                    currentTime=currentRunJobTm.getTime();
	//                    timeInterval=currentTime-lastTime;
	//                }
	//                else{
	//                    Calendar cal=Calendar.getInstance();
	//                    cal.setTime(currentRunJobTm);
	//                    cal.add(Calendar.DAY_OF_MONTH, -1);
	//                    lastRunJobTm=cal.getTime();
	//                    lastTime=lastRunJobTm.getTime();
	//                    lastRunJobTime=df.format(lastRunJobTm);
	//                    timeInterval=1440*1000*60;
	//                }
	//				/*System.out.println("last run job time:"+df.format(lastRunJobTm));
	//				System.out.println("last run job time long:"+String.valueOf(lastTime));
	//				System.out.println("current time:"+df.format(currentRunJobTm));
	//				System.out.println("current run job time long:"+String.valueOf(currentTime));
	//				System.out.println("timeInterval:"+String.valueOf(timeInterval));
	//				System.out.println("jobInterval:"+String.valueOf(jobInterval));*/
	//
	//                //休眠间隔时间后再运行
	//                if(timeInterval<jobInterval){
	//                    Thread.sleep(jobInterval-timeInterval);
	//                }
	//            }
	//            //循环执行各个地市的作业
	//            jobSucessCnt=0;
	//            for(int i=0;i<areaNum;i++){
	//                //获取地区及数据源
	//                curArea=(Area)areas.get(i);
	//                areaCode=curArea.getAreaCode();
	//                for(excAreaNum=0;excAreaNum<excArea.length;excAreaNum++){
	//                    curAreaIdx=excArea[excAreaNum];
	//                    if(curAreaIdx.equals(areaCode))break;
	//                }
	//                if(excAreaNum==excArea.length)continue;
	//				/*if(jobName.equals("T_SCZT_LS_SCZTJBXX")){
	//					if(!areaCode.equals("qy")&&!areaCode.equals("yf")&&!areaCode.equals("sj")&&!areaCode.equals("jy")&&!areaCode.equals("yj")&&!areaCode.equals("dg")){
	//						continue;
	//					}
	//				}*/
	//                dataSource=curArea.getDataSource();
	//                sourceFlag=curArea.getSourceFlag();
	//                System.out.println("开始获取"+areaCode+"源表流水号...");
	//                sourceJobFlag=areaDBconn.getSourceRunJobFlag(tableName,areaCode);
	//                lastRunJobFlag=myDBconn.getLastRunJobFlag(jobNameUpdate,areaCode);
	//                System.out.println("sourceJobFlag("+areaCode+"):"+sourceJobFlag);
	//                System.out.println("lastRunJobFlag("+areaCode+"):"+lastRunJobFlag);
	//                //检查作业状态
	//                System.out.println("脚本"+jobName+"."+areaCode+"执行前检查脚本状态：");
	//                checkEtlStat(job,areaCode);
	//                jobStatus=job.getJobStatus();
	//                if(jobStatus==0)continue;
	//
	//                if(jobStatus==3)resetJob(job,areaCode);
	//
	//                //更新参数文件
	//                //updateParmFile(jobName,areaCode,dataSource,lastRunJobTime);
	//
	//                //设置作业运行命令
	//                if(jobTypeIndex.equals("Incremental"))jobLastTimeParam=lastRunJobFlag;
	//                else jobLastTimeParam=curTmStr;
	//                runJobShell="/home/dsadm/etlRun.sh "+jobName+" "+areaCode+" "+dataSource+" "+sourceFlag+" '"+jobLastTimeParam+"'";
	//                //System.out.println(runJobShell);
	//                //运行作业
	//                jobStatus=0;
	//                job.setJobStatus(jobStatus);
	//                //while(RmtShellExecutor.CONNCNT>RmtShellExecutor.MAXCONN)Thread.sleep(30000);
	//                //RmtShellExecutor.CONNCNT++;
	//                //exe=new RmtShellExecutor("10.1.1.136","dsadm","Ds21Adm05");
	//
	//                cmdList=null;
	//                tryRunJobNum=0;
	//                while(cmdList==null){
	//                    tryRunJobNum++;
	//                    System.out.println("第"+Integer.toString(tryRunJobNum)+"次尝试执行作业："+runJobShell);
	//                    cmdList=exe.exec(runJobShell);
	//                    //cmdList=new ArrayList();
	//                    Thread.sleep(5000);
	//                }
	//                System.out.println(Integer.toString(tryRunJobNum)+"次尝试后完成作业"+jobName+"."+areaCode);
	//                //exe.logOff();
	//
	//                //Thread.sleep(10000);*/
	//                //RmtShellExecutor.CONNCNT--;
	//                System.out.println("脚本"+jobName+"."+areaCode+"开始执行后检查脚本运行状态：");
	//                while(jobStatus==0){
	//                    checkEtlStat(job,areaCode);
	//                    jobStatus=job.getJobStatus();
	//                }
	//                if(jobStatus==2||jobStatus==1){
	//                    jobSucessCnt++;
	//                    myDBconn.updateRunJobTime(jobNameUpdate,curTmStr,sourceJobFlag,areaCode);
	//                }
	//                if(jobStatus==3){
	//                    myDBconn.updateRunJobLog(jobNameUpdate, areaCode, "3", lastRunJobTime);
	//                }
	//            }
	//            runEndTime=System.currentTimeMillis();
	//            System.out.println("程序运行脚本"+jobName+"获取所有地市数据时间："+(runEndTime-currentTime)+"ms");
	//            //设置当前作业运行时间
	//            if(jobSucessCnt==areaNum){
	//                //myDBconn.updateRunJobTime(jobNameUpdate,curTmStr,"sj");
	//                System.out.println("作业"+jobName+"运行成功！");
	//            }
	//
	//
	//            //更新作业信息
	//            updatedJob=myDBconn.getJob(jobId);
	//            if(updatedJob!=null){
	//                JobOperator myJobOptr=new JobOperator(jobs,myDBconn);
	//                myJobOptr.updateJob(jobId, updatedJob);
	//                jobInterval=job.getJobInterval()*60*1000;
	//                runJobNum=job.getJobRunNum();
	//                areaIdx=job.getAreaIdx();
	//            }
	//            else{
	//                break;
	//            }
	//        }
	//    }
	//
	//    //执行分析库作业的子线程函数
	//    public void runModelJob() throws Exception{
	//        //定义函数参数
	//        String jobName=job.getJobName(),cubeName=job.getCubeName(),lastRunJobTime=null,jobId=job.getJobId();
	//        String curTmStr=null,transDt="2016-01-01";
	//        int jobStatus=0,areaNum=areas.size(),runJobNum=job.getJobRunNum(),tryRunJobNum=0,jobSucessCnt=0;
	//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	//        Date lastRunJobTm=null,currentRunJobTm=null;
	//        long jobInterval=job.getJobInterval()*60*1000,lastTime=0,currentTime=0,timeInterval=0,updatedJobInterval=0;
	//        String runJobShell=null;
	//        Area curArea=null;
	//        String areaCode="sj",dataSource="sjy_1",sourceFlag="440000",lastRunJobFlag=null;
	//        Job updatedJob=null;
	//        String curDtStr=df.format(new Date()).substring(0, 9);
	//        List cmdList=null;
	//        String[] jobTypeIndexs=jobName.split("_");
	//        String jobTypeIndex=jobTypeIndexs[jobTypeIndexs.length-1];
	//        String jobNameUpdate="";
	//        Calendar cal=Calendar.getInstance();
	//        cal.set(2016, 3, 2);
	//
	//        //查询
	//        //循环执行作业
	//        //myDBconn.updateRunJobFlag(jobName);
	//        int j=0;
	//        //while(j<runJobNum&&curDtStr.equals(stDtStr)){
	//        while(j<runJobNum){
	//            //cal.set(Integer.valueOf(transDt.split("-")[0]).intValue(), Integer.valueOf(transDt.split("-")[1]).intValue()-1, Integer.valueOf(transDt.split("-")[2]).intValue());
	//            cal.add(Calendar.DATE, -1);
	//            transDt=df.format(cal.getTime()).substring(0, 10);
	//            //transDt="2015-04-02";
	//            System.out.println(transDt);
	//            j++;
	//
	//            timeInterval=0;
	//
	//            //执行作业
	//            System.out.println("脚本"+jobName+"."+transDt+"执行前检查脚本状态：");
	//            jobStatus=0;
	//            while(jobStatus==0){
	//                checkEtlStat(job,transDt);
	//                jobStatus=job.getJobStatus();
	//            }
	//            if(jobStatus==3){
	//                resetJob(job,transDt);
	//            }
	//            //设置作业运行命令
	//            runJobShell="/home/dsadm/etlRun.sh "+jobName+" "+transDt+" '"+transDt+"'";
	//            //System.out.println(runJobShell);
	//            //运行作业
	//            jobStatus=0;
	//            job.setJobStatus(jobStatus);
	//            //while(RmtShellExecutor.CONNCNT>RmtShellExecutor.MAXCONN)Thread.sleep(30000);
	//            //RmtShellExecutor.CONNCNT++;
	//            //exe=new RmtShellExecutor("10.1.1.136","dsadm","Ds21Adm05");
	//
	//            cmdList=null;
	//            tryRunJobNum=0;
	//            while(cmdList==null){
	//                tryRunJobNum++;
	//                System.out.println("第"+Integer.toString(tryRunJobNum)+"次尝试执行作业："+runJobShell);
	//                cmdList=exe.exec(runJobShell);
	//                //Thread.sleep(5000);
	//            }
	//            System.out.println(Integer.toString(tryRunJobNum)+"次尝试后完成作业"+jobName+"."+transDt);
	//            //exe.logOff();
	//
	//            //Thread.sleep(10000);*/
	//            //RmtShellExecutor.CONNCNT--;
	//            System.out.println("脚本"+jobName+"."+transDt+"执行结束后检查脚本状态：");
	//            while(jobStatus==0){
	//                checkEtlStat(job,transDt);
	//                jobStatus=job.getJobStatus();
	//            }
	//            if(jobStatus==2||jobStatus==1){
	//                //myDBconn.updateRunJobTime(jobName,curTmStr,"sj");
	//                buildCube(cubeName);
	//            }
	//            if(jobStatus==3){
	//                myDBconn.updateRunJobLog(jobNameUpdate, "CM", "3", transDt);
	//            }
	//
	//
	//            //更新作业信息
	//            updatedJob=myDBconn.getJob(jobId);
	//            if(updatedJob!=null){
	//                JobOperator myJobOptr=new JobOperator(jobs,myDBconn);
	//                myJobOptr.updateJob(jobId, updatedJob);
	//                jobInterval=job.getJobInterval()*60*1000;
	//                runJobNum=job.getJobRunNum();
	//            }
	//            else{
	//                break;
	//            }
	//            curDtStr=df.format(new Date()).substring(0, 9);
	//        }
	//        System.out.println("脚本"+jobName+"运行结束！");
	//    }
	//
	//    //build cube
	//    public void buildCube(String cubeName) throws IOException, InterruptedException{
	//        String cmd="cmd.exe /c D:\\buildCubeShell\\buildCube.bat "+cubeName;
	//        System.out.println("开始执行命令："+cmd);
	//        excuteCmd(cmd);
	//        System.out.println("结束命令："+cmd);
	//    }
	//
	//    //执行cmd命令
	//    public void excuteCmd(String cmd) throws IOException, InterruptedException{
	//        //String cmd="cmd.exe /k ipconfig -all";
	//        try{
	//            Process p;
	//            p = Runtime.getRuntime().exec(cmd);
	//            InputStream in = p.getInputStream();
	//            in.close();
	//            p.waitFor();
	//        }catch (Exception e) {
	//            e.printStackTrace();
	//        }
	//
	//        // TODO Auto-generated catch block
	//    }
	//
	//    //检查作业状态
	//    public int checkEtlStat(String  jobName,String area) throws Exception{
	//        String runJobToken="1",line=null,dChkStr=null;
	//        String cmdChkStr="/home/dsadm/etlCheck.sh "+jobName+"."+area;
	//        List cmdList=null;
	//        int cmdListNum=0,tryCheckJobNum=0;
	//        while(cmdList==null){
	//            tryCheckJobNum++;
	//            System.out.println("检查作业状态："+cmdChkStr);
	//            cmdList=exe.exec(cmdChkStr);
	//            Thread.sleep(5000);
	//        }
	//        //exe.logOff();
	//        //RmtShellExecutor.CONNCNT--;
	//
	//        cmdListNum=cmdList.size();
	//        for(int i=0;i<cmdListNum;i++){
	//            if(i==0){
	//                line=(String)cmdList.get(0);
	//                runJobToken=line.substring(line.length()-2, line.length()-1);
	//                break;
	//            }
	//        }
	//        return Integer.parseInt(runJobToken);
	//    }
	//
	//    //更新参数文件函数
	//    public void updateParmFile(String jobName,String area,String dataSource,String lastRunTime) throws Exception{
	//        String jobParamFileName="D:\\etl\\param\\"+jobName+".param";
	//        File jobParamFile = null;
	//        FileOutputStream fos = null;
	//        OutputStreamWriter osw = null;
	//        RmtShellExecutor myParamFtp = new RmtShellExecutor("10.1.1.136","dsadm","Ds21Adm05");
	//
	//        try{
	//            //BufferedReader br = new BufferedReader(new FileReader(paramInfoFileName));
	//            jobParamFile = new File(jobParamFileName);
	//            fos = new FileOutputStream(jobParamFile);
	//            osw = new OutputStreamWriter(fos);
	//            osw.write("source_server="+dataSource);
	//            osw.write("\r\n");
	//            osw.write("source_db="+area+"aicbiz");
	//            osw.write("\r\n");
	//            osw.write("LASTTIME="+lastRunTime);
	//            osw.write("\r\n");
	//            osw.close();
	//            fos.close();
	//        }catch (Exception e) {
	//            e.printStackTrace();
	//        }
	//
	//        myParamFtp.fileUpload(jobParamFileName);
	//    }

	//复位作业函数
	public void resetJob(String jobName,String area) throws Exception{
		exe1.exec("/opt/IBM/InformationServer/Server/DSEngine/bin/dsjob  -run -mode RESET -wait sjzx  "+jobName);
	}

//	@Override
//	public String call() throws Exception {
//
//	}

	public Map runCognosBiuld(String jobName,String state,String flag,String area,String datasource,String type,String sourceFlag,String targetserver,String targetdb
			,String targetpassword,String targetusername,String jobType,String sourceUsername,String sourcePassword,String timestamp) throws Exception{

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String curDtStr=df.format(new Date()).substring(0, 19);
		StringBuffer shell=new StringBuffer("/opt/IBM/InformationServer/Server/DSEngine/bin/dsjob -run -mode NORMAL  "    );
		shell.append("  -param  allparamters.source_db=datacenter ");
		shell .append( "  -param allparamters.source_server=datacenter");
		shell.append(" -param allparamters.LASTTIME="+timestamp );
		if(sourceUsername!=null)  shell.append(" -param allparamters.source_username="+sourceUsername);
		if(sourcePassword!=null)  shell.append(" -param allparamters.source_password="+sourcePassword);
		if(targetusername!=null)  shell.append(" -param allparamters.target_username="+targetusername);
		if(targetpassword!=null) shell.append(" -param allparamters.target_password="+targetpassword);
		if(targetserver!=null) shell.append(" -param allparamters.target_server="+targetserver);
		if(targetdb!=null) shell.append(" -param allparamters.target_db="+targetdb);
		shell.append( " -wait -jobstatus datacenter "+jobName);
		
		//执行脚本
		System.out.println("执行建模脚本开始"+shell.toString());
		String result=exe1.exec(shell.toString());
		Map map=new HashMap();
		map.put("lasttime", curDtStr);
		map.put("result", result);
		return map;
	}


	
	
	public Map excuteJob(String jobName,String state,String flag,String area,String datasource,String type,String sourceFlag,String targetserver,String targetdb
			,String targetpassword,String targetusername,String jobType,String sourceUsername,String sourcePassword,String timestamp) throws Exception{
		Map map=new HashMap();
		
		
		System.out.println(bo.toString());
		//		resetJob(jobName,area);
		switch (type){
		case "1":
			if(flag!=null&&!"".equals(flag)&&!"null".equals(flag)){
				map=this.runETLJob(jobName, state, flag, area, datasource, type, sourceFlag, targetserver, targetdb, targetpassword, targetusername, jobType,sourceUsername,sourcePassword);
			}else{
				map.put("runState","无数据" );
			}
			break;
		case "2"://全量
			map=this.runETLJob(jobName, state, flag, area, datasource, type, sourceFlag, targetserver, targetdb, targetpassword, targetusername, jobType,sourceUsername,sourcePassword);
			break;
		case "3"://建模
			map=this.runCognosBiuld(jobName, state, flag, area, datasource, type, sourceFlag, targetserver, targetdb, targetpassword, targetusername, jobType,sourceUsername,sourcePassword, timestamp);

			break;
		}
		return map;
	}


	public  boolean isTrue(String result){
		return result==null||"".equals(result)||!result.contains("2");
	}

	@Override
	public void run() {

		String jobName=bo.getJobname();
		String state=bo.getState();
		String flag=bo.getFlag();
		String area=bo.getArea();
		String datasource=bo.getDatasource();
		String type=bo.getType();
		String sourceFlag=bo.getSourceflag();
		String targetserver=bo.getTargetserver();
		String targetdb=bo.getTargetdb();
		String targetpassword=bo.getTargetpassword();
		String targetusername=bo.getTargetusername();
		String sourceUser=bo.getDbuser();
		String sourcePwd=bo.getDbpassword();
		String jobType=bo.getJobtype();
		String newFlag=bo.getNewFlag();
		String result="";
		String lastTime="";
		String timestamp=bo.getTimestamp();//etl分析建模脚本参数

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String startTime=df.format(new Date()).substring(0, 19);
		long start=System.currentTimeMillis();
		String  runJobShell;
		if(service==null){
			service=new ETLService();
		}
		//执行脚本
		Map map = null;
		try {
			map = this.excuteJob(jobName, state, flag, area, datasource, type, sourceFlag, targetserver, targetdb, targetpassword, targetusername, jobType, sourceUser, sourcePwd, timestamp);
		
			lastTime=(String) map.get("lasttime");
			result=(String) map.get("result");

//
			//如果脚本执行失败，则尝试重新执行3次
			int i=0;
			while(this.isTrue(result)){
				i++;
				result=(String) map.get("result");
				lastTime=(String) map.get("lasttime");
				if(i==3||!this.isTrue(result)) break;
				map=new HashMap();//清空Map
				map=this.excuteJob(jobName, state, flag, area, datasource, type, sourceFlag, targetserver, targetdb, targetpassword, targetusername, jobType, sourceUser, sourcePwd, timestamp);
				System.out.println("执行失败脚本"+jobName+"第"+i+"遍");
			};

			long end=System.currentTimeMillis();
			long runtime=end-start;
			bo.setLastetlruntime(lastTime);
			bo.setFlag(newFlag);
			bo.setRunnum(bo.getRunnum()+1);
			bo.setRuntime(""+runtime);

			//最后、记录日志

			if(this.isTrue(result)){
				//记录执行错误日志
				end=System.currentTimeMillis();
				runtime=end-start;
				String endTime=df.format(new Date()).substring(0, 19);
				EtlLogBO bo=new EtlLogBO();
				bo.setId(UUID.randomUUID()+"");
				bo.setExcutetime(runtime+"");
				bo.setJobtype(jobType);
				bo.setStarttime(startTime);
				bo.setEndtime(endTime);
				bo.setFlag(flag);
				bo.setArea(area);
				bo.setJobname(jobName);
				String runState=(String) map.get("runState");
				if(runState==null||"".equals(runState)) runState="失败";
				bo.setState(runState);
//				System.out.println("执行脚本失败"+bo.getJobname()+"."+bo.getArea());
				service.saveEtlLog(bo);
			}else{
				service.updateETLRwxx(bo);
				//执行全量脚本时，需要更新增量脚本的时间戳
				if("2".equals(type)||type=="2"){
					service.updateETLIncrementalTime(" update etl_rwxx set flag='"+newFlag+"' where jobname like '"+bo.getJobname()+"%' and area='"+bo.getArea()+"'");
				}
				String endTime=df.format(new Date()).substring(0, 19);
				EtlLogBO bolog=new EtlLogBO();
				bolog.setId(UUID.randomUUID()+"");
				bolog.setExcutetime(runtime+"");
				bolog.setJobtype(jobType);
				bolog.setArea(area);
				bolog.setStarttime(startTime);
				bolog.setEndtime(endTime);
				bolog.setJobname(jobName);
				bolog.setFlag(flag);
				bolog.setState("成功");
				service.saveEtlLog(bolog);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
