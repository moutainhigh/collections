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
 * 12315没有增量时间戳，数据较小，则全量抽取；
 * Created by Administrator on 2016/6/17.
 */
@Controller
@RequestMapping("/ETL12315Excuter")
public class ETL12315Excuter implements JobServer{
	@Autowired
	public ETLService service;
	@Autowired
	public ETLExcuterQueue queue;


	/**增量定时调度入口
	 * 参数说明 paramers固定参数格式  
	 * 类型，地市 如果地市为空，则写all
	 * @param jobtype
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("job")
	public  String job(String paramers) throws Exception{
		if(service==null)service=new ETLService();
		List<EtlRwxxBO> list=null;
		list=service.getETLRwxx12315();
		//通过jobname查找，查询具体ETL列表信息 ,可获取上次执行时间，ETL列表信息需要对ETL类型进行判断，是增量还是全量，数据源在sjy1/sjy2;、分实例执行，则需要确定属于那个地市
		this.excuteJob(list);
		return "true";
	}


	
	

	public void excuteJob(List<EtlRwxxBO> list){
		for(int i=0;i<list.size();i++){
			EtlRwxxBO rwxxBo=list.get(i);
			System.out.println("任务对象准备放入线程进行执行="+rwxxBo);
			String type=rwxxBo.getType();//全量脚本/增量脚本
			String flag=rwxxBo.getFlag();//ETL执行增量标记
			String datasource=rwxxBo.getDatasource();//数据源在ETL服务器上配置的severname
			String sourceFlag=rwxxBo.getSourceflag();//每张表都有的sourceflag，表名数据由哪里来
			String dbUser=rwxxBo.getDbuser();//源用户名
			String dbPassword=rwxxBo.getDbpassword();//源密码
			String tableName=rwxxBo.getTablename();//主表名
			String jobtype=rwxxBo.getJobtype();
			String area=rwxxBo.getArea();
			//无轮全量还是增量，取出执行之前最大flag信息，1链接数据源，获取最大标记信息
			ExecutorService ex=ETLExcuterQueue.getPool();
			RunJob job=new RunJob(rwxxBo);
			ex.execute(job);
		}
	}


	public static void main(String[] args) throws Exception {
		ETL12315Excuter ex=new ETL12315Excuter();
		System.out.println(ex.job("jobname=T_SCZT_CZXX,area=sj"));
	}
}
