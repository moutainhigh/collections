package cn.gwssi.etlschedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gwssi.datachange.dataservice.controller.DataServiceController;
import cn.gwssi.etlschedule.util.SSHHelper;
import cn.gwssi.quartz.inter.JobServer;

/**报表脚本定时处理程序
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/ReportETLExcuter")
public class ReportETLExcuter implements JobServer{
	private static  Logger log=Logger.getLogger(ReportETLExcuter.class);

	/* (non-Javadoc)定时调度入口，其别分月报，季报，年报，半年报参数不一致需要进行设置
	 * paramers(jobname,reportQb（报告期别）)
	 * @see cn.gwssi.quartz.inter.JobServer#job(java.lang.String)
	 */
	@RequestMapping("job")
	public String job(String paramers) throws Exception {
		//1、根据传入参数paramers（脚本名称），驱动报表etl脚本执行
		String jobname,reportQb,curDtStr;
		String[] strings=paramers.split(",");
		jobname=strings[0];
		reportQb=strings[1];
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String dataStr=df.format(new Date()).substring(0, 7);
		String year=dataStr.substring(0, 4);
		String mouth=dataStr.substring(5, 7);
//		String mouth="11";
		curDtStr=year+"-"+mouth+"-"+reportQb;
		
		
		//获取ETL服务器链接
		SSHHelper exe1=new SSHHelper("10.1.1.136","root","123456qwerty",22);
		StringBuffer shell=new StringBuffer("/opt/IBM/InformationServer/Server/DSEngine/bin/dsjob -run -mode NORMAL  "    );
		shell.append(" -param LASTTIME=\""+curDtStr+"\" " );
		shell.append( " -wait -jobstatus datacenter "+jobname);
		System.out.println("执行调度脚本开始"+shell.toString());
		String result=exe1.exec(shell.toString());
		if(result==null||"".equals(result)||!result.contains("2")){
			log.info("报表ETL脚本"+jobname+"执行失败！");
		}else{
			log.info("报表ETL脚本"+jobname+"执行成功！");
		}
		return null;
	}

}
