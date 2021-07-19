package cn.gwssi.etlschedule;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gwssi.etlschedule.module.EtlLogBO;
import cn.gwssi.etlschedule.module.EtlRwxxBO;
import cn.gwssi.etlschedule.service.ETLService;
import cn.gwssi.quartz.inter.JobServer;

import com.gwssi.optimus.core.exception.OptimusException;

/**
 * ETL建模定时执行类
 * Created by Administrator on 2016/6/17.
 */
@Controller
@RequestMapping("/ETLModelExcuter")
public class ETLModelExcuter implements JobServer{
	@Autowired
	public ETLService service;
	@Autowired
	public ETLExcuterQueue queue;


	/**
	 * 建模脚本定时调度入口
	 * 传递参数为jobname
	 */
	@RequestMapping("job")
	public  String job(String paramers) throws Exception{
		
		if(service==null)service=new ETLService();
		List<EtlRwxxBO> list=null;
		list=service.getETLRwxxByModel(paramers);
		//通过jobname查找，查询具体ETL列表信息 ,可获取上次执行时间，ETL列表信息需要对ETL类型进行判断，是增量还是全量，数据源在sjy1/sjy2;、分实例执行，则需要确定属于那个地市
		this.excuteJob(list);
		return "true";
	}

	
	public void excuteJob(List<EtlRwxxBO> list){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String curDtStr=df.format(new Date()).substring(0, 11);
		
		for(int i=0;i<list.size();i++){
			
			EtlRwxxBO rwxxBo=list.get(i);
			System.out.println("建模任务================"+rwxxBo);
			String type=rwxxBo.getType();//全量脚本/增量脚本
			String flag=rwxxBo.getFlag();//ETL执行增量标记
			String datasource=rwxxBo.getDatasource();//数据源在ETL服务器上配置的severname
			String sourceFlag=rwxxBo.getSourceflag();//每张表都有的sourceflag，表名数据由哪里来
			String dbUser=rwxxBo.getDbuser();//源用户名
			String dbPassword=rwxxBo.getDbpassword();//源密码
			String tableName=rwxxBo.getTablename();//主表名
			String jobtype=rwxxBo.getJobtype();
			String area=rwxxBo.getArea();
			rwxxBo.setTimestamp(curDtStr);//建模时间节点；
			//无轮全量还是增量，取出执行之前最大flag信息，1链接数据源，获取最大标记信息
			ExecutorService ex=ETLExcuterQueue.getPool();
			RunJob job=new RunJob(rwxxBo);
			ex.execute(job);
		}
	}


	
	
//	public int getDayOfMonth(){
//		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
//		int day=aCalendar.getActualMaximum(Calendar.DATE);
//		return day;
//	}
	
}
