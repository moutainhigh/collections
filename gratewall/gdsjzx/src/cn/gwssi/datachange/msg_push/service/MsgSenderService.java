package cn.gwssi.datachange.msg_push.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.gwssi.datachange.dataservice.model.TPtFwdypzjbxxBO;
import cn.gwssi.datachange.dataservice.model.TPtFwjbxxBO;
import cn.gwssi.datachange.dataservice.service.DataServiceService;
import cn.gwssi.datachange.msg_push.api.DataFormat;
import cn.gwssi.datachange.msg_push.bus.FwdyServiceManager;
import cn.gwssi.datachange.msg_push.ftpImpl.FtpMsg;
import cn.gwssi.datachange.msg_push.ftpImpl.FtpReceiver;
import cn.gwssi.datachange.msg_push.ftpImpl.FtpServerConfig;
import cn.gwssi.resource.KeysUtil;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;

 /**
  * 获取服务内容，缓存文件形式返回
  * @author wuyincheng ,Jul 15, 2016
  */
@Service
public class MsgSenderService extends DataServiceService {

	private static  Logger log=Logger.getLogger(MsgSenderService.class);
	
	//文件暂存路径
	@Value("${upload.tempDir}")  
	private String tempPath ;
	
	//写文件时，每次从db中读取页面的页容量
	private static final int PAGE_COUNT = 200;
	
	private static final DataFormat XML_FORMAT = new XmlDataFormat();
	
	private static Executor singleThread = Executors.newSingleThreadExecutor();
	
	//设立文件缓存（路径）
	private static ConcurrentHashMap<CacheKey, FutureTask<String>> 
						fileCache = new ConcurrentHashMap<CacheKey, FutureTask<String>>(64);
	
	//缓存时效段(每个x时间内有效, 单位秒) @TODO
	private final long CACHE_TIME = 10 * 60;
	
	@Autowired
	private FwdyServiceManager manager;
	
	//注： 此serviceId为T_PT_FWDYPZJBXX表中的fwdypzjbxxid ,updete by @wuyincheng 2016/08/19
	public void  initContext(String serviceId, FtpMsg msg, FtpReceiver receiver) {
		if(serviceId == null || "".equals(serviceId.trim()) || msg == null || receiver == null)
			return ;
		//获取配置信息
		TPtFwdypzjbxxBO config = null;
		try {
			config = getTPtFwdypzjbxxBO(serviceId);
		} catch (OptimusException e1) {
			e1.printStackTrace();
			throw new NullPointerException("Can't find valid data from 'T_PT_FWDYPZJBXX' where id=" + serviceId);
		}
		serviceId = config.getServiceid();
		final FtpServerConfig ftpServerConfig = new FtpServerConfig(config.getPath());
		//填充接收方信息
		receiver.setIp(ftpServerConfig.getIp());
		receiver.setPort(ftpServerConfig.getPort());
		receiver.setPwd(ftpServerConfig.getPwd());
		receiver.setSavePath(ftpServerConfig.getFilePath());
		receiver.setUserName(ftpServerConfig.getUserName());
		//格式化
		DataFormat format = null;
		if(ftpServerConfig.getFilePath() != null && (ftpServerConfig.getFilePath().toLowerCase().endsWith("txt") ||
													 ftpServerConfig.getFilePath().toLowerCase().endsWith("csv"))){
			format = new TxtAndCsvDataFormat();
		}else{
			format = XML_FORMAT;
		}
		//初始化推送内容
		try {
			final List<Map<String, String>> temp = selectServiceContentmakeCheckedTree(serviceId);
			if(temp == null || temp.size() == 0) 
				throw new NullPointerException("Can't find valid data from 'T_PT_FWDYPZJBXX' where serviceId=" + serviceId);
			List<Map> temp2 = selectServiceContentDetail(new HashMap<String, String>(){
				{put("fwnrid", temp.get(0).get("serviceconentid"));
				 put("isenabled", "0");}//需要启动
			});
			if(temp2 == null || temp2.size() == 0)
				throw new NullPointerException("Can't find valid data from 'T_PT_FWNRXX' where fwnrId=" + (temp.get(0)));
			final String condition =  (String)(temp2.get(0).get("servicecontentcondition"));
			final String ql = (String)(temp2.get(0).get("servicecontent")) ;
//					+ 
//					((condition == null || "".equals(condition.trim())) ? "" : " where " + condition);
			//查看缓存文件是否存在 @TODO
//			long cacheTime  = System.currentTimeMillis() / CACHE_TIME * CACHE_TIME;
			//写文件
			String file;
			try {
				file = initContext(ql, format);
				msg.setFilePath(file);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
		} catch (OptimusException e) {
			e.printStackTrace();
		}
	}
	
	public TPtFwdypzjbxxBO getTPtFwdypzjbxxBO(String serviceId ) throws OptimusException{
		//查询所有定时任务
		IPersistenceDAO  dao=getPersistenceDAO();
		List<String> par = new ArrayList<String>();
		par.add(serviceId);
		List<TPtFwdypzjbxxBO> list=dao.queryForList(TPtFwdypzjbxxBO.class,"select * from T_PT_FWDYPZJBXX where  fwdypzjbxxid=? ", par);
		return (list.size() == 0 ? null : list.get(0));
	}
	
	public TPtFwjbxxBO getTPtFwjbxxBO(String serviceId ) throws OptimusException{
		//查询所有定时任务
		IPersistenceDAO  dao=getPersistenceDAO();
		List<String> par = new ArrayList<String>();
		par.add(serviceId);
		List<TPtFwjbxxBO> list=dao.queryForList(TPtFwjbxxBO.class,"select * from T_PT_FWJBXX where  serviceid =? ", par);
		return (list.size() == 0 ? null : list.get(0));
	}
	
	//查询内容分页写入文件,猜测执行方式
	private String initContext(final String sql, final DataFormat format) throws InterruptedException, ExecutionException {
		final String dataStru = (format instanceof XmlDataFormat ? ".xml" : ".txt");
		final String file = tempPath + File.separatorChar + KeysUtil.md5Encrypt(sql) + dataStru;
		CacheKey key = new CacheKey(sql + dataStru, System.currentTimeMillis()); 
		FutureTask<String> task = fileCache.get(key);
		String result = null;
		if(task != null){
			result = task.get();
			log.info("Cache Hit => " + result);
			return result;
		}else{
			task = new FutureTask<String>(new Callable<String>(){
				@Override
				public String call() throws Exception {
					MsgSenderService.this.log.info("Start init data file : " + file);
					int page = 0;
					PrintWriter pw = null;
					try {
						pw =  new PrintWriter(file);
						List<Map> temp = null;
						do{
							temp = MsgSenderService.this.getPersistenceDAO("dataSourceshare").pageQueryForList(sql, null, page ++, PAGE_COUNT);
							for(Map m : temp) {
//								formatData(m, pw);
								format.formatOut(m, pw);
							}
						}while(temp.size() != 0);
					} catch (OptimusException e) {
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} finally{
						if(pw != null)
							pw.close();
					}
					MsgSenderService.this.log.info("End init data file : " + file);
					return file;
				}
				
			});
			if(null == fileCache.putIfAbsent(key, task)){
				singleThread.execute(task);
				return task.get();
			}else{
				return fileCache.get(key).get();
			}
		}
	}
	
	
	//将map输出成xml  {hello=world, k1=k2} ==> <data><hello>world</hello><k1>k2</k1></data>
	public static void formatDataWithXML(Map map, PrintWriter pw) {
		
	}
	
	public static void formatDataWithTXT(Map map, PrintWriter pw) {
		if(map != null && map.size() > 0) {
			for(Object key : map.keySet()) {
				pw.write(key.toString());
				pw.write(',');
				pw.write(map.get(key) == null ? "" : map.get(key).toString());
				pw.write(key.toString());
			}
			pw.println();
		}
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		/*Map map = new HashMap();
		map.put("hello", "world");
		map.put("path", "ftp://uftp:123456@localhost:21/pub/{yyyy}{mm}{dd}/{time}{rand:8}");
		Map map1 = new HashMap();
		map1.put("hello", "world");
		map1.put("path", "ftp://uftp:123456@localhost:21/pub/{yyyy}{mm}{dd}/{time}{rand:8}");
		PrintWriter pw = new PrintWriter(new File("/tmp/tesdsfdf.txt"));
		formatData(map, pw);
		formatData(map1, pw);
		pw.close();*/
	}
	
	
	
	
	public synchronized void deleteCache(String path) {
		if(path != null) {
			File f = new File(path);
			if(f.exists() && f.isFile())
				f.delete();
		}
	}
	
	//获取服务内容信息表id
	@Override
	public List selectServiceContentmakeCheckedTree(String serviceid) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.serviceconentid from T_PT_FWJBXX t where t.serviceState='0' and t.serviceid='"+serviceid+"'";
        List t = dao.queryForList(sql, null);
        return t;
	}
	
	
	private static class CacheKey {
		String sql;
		long time;
		
		public CacheKey(String sql, long time) {
			this.sql = sql;
			this.time = time / 86400 * 86400;
		}

		@Override
		public int hashCode() {
			return this.sql.hashCode() + new Long(time).hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null)
				return false;
			CacheKey t = (CacheKey) obj;
			return this.sql.equals(t.sql) && this.time == t.time;
		}
	}
	
}
