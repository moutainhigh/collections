package cn.gwssi.etlschedule;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gwssi.etlschedule.module.EtlRwxxBO;
import cn.gwssi.etlschedule.service.ETLService;
import cn.gwssi.quartz.inter.JobServer;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.datasource.DataSourceManager;

/**
 * ETL执行定时调度执行类
 * Created by Administrator on 2016/6/17.
 */
@Controller
@RequestMapping("/ETLExecuter")
public class ETLExcuter implements JobServer{
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
		String jobtype="",area="";
		if(paramers!=null&&!"".equals(paramers)){
			jobtype=paramers.split(",")[0];
			area=paramers.split(",")[1];
		}
		list=service.getETLRwxxByJobType(jobtype, area);
		//通过jobname查找，查询具体ETL列表信息 ,可获取上次执行时间，ETL列表信息需要对ETL类型进行判断，是增量还是全量，数据源在sjy1/sjy2;、分实例执行，则需要确定属于那个地市
		System.out.println("需要执行脚本数量==============="+list.size());
		this.excuteJob(list);
		return "true";
	}

	/**执行脚本类型为年报的调度
	 * 参数说明 type 全量 1/增量 2   
	 * @param type
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("excuteNBALL")
	public String excuteNBALL(String type) throws OptimusException{
		if(service==null)service=new ETLService();
		List<EtlRwxxBO> list=service.getETLRwxxForNb(type);
		this.excuteJob(list);
		return "true";
	}	


	/**执行全量脚本，按照jobname 执行所有地市全量、增量脚本
	 * 参数说明 type 全量 2/增量 1   
	 * @param type
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("excuteJobNameByAllArea")
	public String excuteJobNameByAllArea(String jobName,String type) throws OptimusException{
		if(service==null)service=new ETLService();
		List<EtlRwxxBO> list=service.getETLRwxxForJobNameByAllArea(jobName,type);
		this.excuteJob(list);
		return "true";
	}	



	/**执行脚本类型为农资的调度
	 * 参数说明 type 全量 2/增量 1  
	 * @param type
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("excuteNZALL")
	public String excuteNZALL(String type) throws OptimusException{
		if(service==null)service=new ETLService();
		List<EtlRwxxBO> list=service.getETLRwxxForNz(type);
		this.excuteJob(list);
		return "true";
	}	




	/**执行某所有在备点库为源按地市区分的 全量调度
	 * @param paramers
	 * 传参说明 脚本类型 ，地市
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("excuterAll")
	public String excuterAll(String paramers) throws OptimusException{
		String jobtype="",area="",jobname=null;
		if(paramers!=null&&!"".equals(paramers)){
			jobtype=paramers.split(",")[0];
			area=paramers.split(",")[1];
		}
		//执行paramers类型全量脚本调度
		List<EtlRwxxBO>  list=null;
		list =service.getETLRwxxByJobType2(jobtype,area);
		this.excuteJob(list);
		return "";
	}

	
	
	/**执行根据脚本类型执行调度 
	 * @param paramers
	 * 传参说明     脚本类型 ，全量2/增量1
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("excuterAllByJobtype")
	public String excuterAllByJobtype(String paramers) throws OptimusException{
		String jobtype="",type="";
		if(paramers!=null&&!"".equals(paramers)){
			jobtype=paramers.split(",")[0];
			type=paramers.split(",")[1];
		}
		//执行paramers类型全量脚本调度
		List<EtlRwxxBO>  list=null;
		list =service.getETLRwxxByType(jobtype,type);
		this.excuteJob(list);
		return "";
	}

	

	public void excuteJob(List<EtlRwxxBO> list){
		for(int i=0;i<list.size();i++){
			EtlRwxxBO rwxxBo=list.get(i);
			System.out.println(rwxxBo.toString());
			String type=rwxxBo.getType();//全量脚本/增量脚本
			String flag=rwxxBo.getFlag();//ETL执行增量标记
			String datasource=rwxxBo.getDatasource();//数据源在ETL服务器上配置的severname
			String sourceFlag=rwxxBo.getSourceflag();//每张表都有的sourceflag，表名数据由哪里来
			String dbUser=rwxxBo.getDbuser();//源用户名
			String dbPassword=rwxxBo.getDbpassword();//源密码
			String tableName=rwxxBo.getTablename();//主表名
			String jobtype=rwxxBo.getJobtype();
			String jobname=rwxxBo.getJobname();
			String area=rwxxBo.getArea();
			//无轮全量还是增量，取出执行之前最大flag信息，1链接数据源，获取最大标记信息
			String newflag=this.getFlag(jobname,datasource,tableName,sourceFlag,dbUser,dbPassword,area,jobtype);
			rwxxBo.setNewFlag(newflag);
			ExecutorService ex=ETLExcuterQueue.getPool();
			RunJob job=new RunJob(rwxxBo,newflag);	
			ex.execute(job);
		}
	}


		@RequestMapping("jobForNameAndArea")
		public  String jobForNameAndArea(String paramers) throws Exception{
	
			//传入参数地市
			long start=System.currentTimeMillis();
			System.out.println("开始执行时间="+start);
			String jobname="",area="";
			if(paramers!=null&&!"".equals(paramers)){
				jobname=paramers.split(",")[0];
				area=paramers.split(",")[1];
			}
	
			if(service==null){
				service=new ETLService();
				System.out.println("使用定时器有些时候spring注入的实例无效");
			}
			//通过jobname查找，查询具体ETL列表信息 ,可获取上次执行时间，ETL列表信息需要对ETL类型进行判断，是增量还是全量，数据源在sjy1/sjy2;、分实例执行，则需要确定属于那个地市
			EtlRwxxBO bo=service.getETLRwxxByOne(jobname,area);
			String jobName=bo.getJobname();
			String state=bo.getState();
			String flag=bo.getFlag();
			String datasource=bo.getDatasource();
			String type=bo.getType();
			String sourceFlag=bo.getSourceflag();
			String targetserver=bo.getTargetserver();
			String targetdb=bo.getTargetdb();
			String targetpassword=bo.getTargetpassword();
			String targetusername=bo.getTargetusername();
			String jobType=bo.getJobtype();
			String tableName=bo.getTablename();
			String dbUser=bo.getDbuser();
			String dbPassword=bo.getDbpassword();
	
			//无轮全量还是增量，取出执行之前最大flag信息，1链接数据源，获取最大标记信息
			String newflag=this.getFlag(jobName,datasource,tableName,sourceFlag,dbUser,dbPassword,area,jobType);
	
			RunJob jobexcute=new RunJob(bo);
			Map map=jobexcute.runETLJob(jobName, state, flag, area, datasource, type, sourceFlag, targetserver, targetdb, targetpassword, targetusername, jobType,bo.getDbuser(),bo.getDbpassword());
			
			String result=(String) map.get("result");
			if("".equals(result)||!result.contains("2")){
				System.out.println("执行脚本出错，当前jobname==="+jobname+",地市area="+area);
				
			}
			
			//执行之后，更新rwxx表中的flag和执行时间
	
			long end=System.currentTimeMillis();
			System.out.println("结束执行时间="+end);
			long runtime=start-end;
			System.out.println("执行时间="+runtime);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String lastTime=df.format(new Date()).substring(0, 19);
			bo.setLastetlruntime(lastTime);
			bo.setFlag(newflag);
			bo.setRunnum(bo.getRunnum()+1);
			bo.setRuntime(""+runtime);
			service.updateETLRwxx(bo);
			return "true";
		}



	@RequestMapping("ExcuteSbzjError")
	public String ExcuteSbzjError() throws OptimusException{
		List list=service.getSbzj();
		service.deleteETLErrorLog("sbzj");
		this.excuteJob(list);
		return "true";
	} 	
	
	

	@RequestMapping("ExcuteError")
	public String ExcuteError(String area) throws OptimusException{
		List list=service.getTimestampError(area);
		this.excuteJob(list);
		return "true";
	} 	
		
		
	//获取本次执行时间搓
	public String getFlag(String jobname,String datasource,String tablename, String sourceFlag, String dbuser, String password,String area,String jobtype ){
		String flag=null;
		ResultSet rs=null;
		Connection conn=null;
		String selSql=null;
		try{
			if(!jobtype.equals("sbzj")){
				conn=  ETLDataSourceConnect.getConnection(datasource,area,dbuser,password,jobtype);
			}else{
				conn=DataSourceManager.getConnection("defaultDataSource");
			}
			selSql="SELECT max(timestamp) FROM "+tablename +"  ";
			if(jobtype.equals("nb"))selSql="SELECT max(updateDate) FROM "+tablename +" ";
			if(jobtype.equals("sdnb"))selSql="SELECT max(updateDate) FROM "+tablename +" ";
			if(jobtype.equals("qtgs"))selSql="SELECT max(updateDate) FROM "+tablename;
			if(jobtype.equals("nz")) selSql="SELECT max(timestamp) FROM "+tablename;//农资时间戳待确定
			if(jobtype.equals("12315")) return null;//12315时间戳有问题；
			if(jobname!=null&&"T_SCZT_SCZTBJXX_UPDATE".equalsIgnoreCase(jobname))selSql="SELECT max(updateTime) FROM "+tablename;
			if(jobname!=null&&"T_SCZT_SCZTBJXX_UPDATE_Incremental".equalsIgnoreCase(jobname))selSql="SELECT max(updateTime) FROM "+tablename;
//			if(jobtype.equals("aj"))selSql="SELECT max(timestamp) FROM "+tablename +" where timestamp is not null ";
			if(jobtype.equals("gzsb"))selSql="SELECT max(S_EXT_DATATIME) FROM   "+tablename +" ";
			if(jobtype.equals("szsb"))selSql="SELECT max(S_EXT_DATATIME) FROM  "+tablename +" ";
			rs=conn.createStatement().executeQuery(selSql);
			while(rs.next()){
				flag=rs.getString(1);
				System.out.println(flag);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null){
					rs.close();
				}
				if(conn!=null){
					conn.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return flag;
	};



	/**给etl任务加上排序，防止多个脚本同时更新一个表，可能造成效率的影响
	 * @throws OptimusException
	 */
	@RequestMapping("initSeq")
	public void initSeq() throws OptimusException{
		List<EtlRwxxBO> list=service.getAllETLRwxx();
		for(EtlRwxxBO bo :list){
			String id=bo.getId();
			Random rand = new Random();
			int seq=rand.nextInt(1000);
			service.updateSeq(id, seq);
		}
	}


	
	

	public static void main(String[] args) throws Exception {
		ETLExcuter ex=new ETLExcuter();
		System.out.println(ex.job("jobname=T_SCZT_CZXX,area=sj"));
	}
}
