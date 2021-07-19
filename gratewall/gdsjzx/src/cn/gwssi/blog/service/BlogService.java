package cn.gwssi.blog.service;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.gwssi.blog.controller.BlogController;
import cn.gwssi.blog.model.TPtSysLogBO;

import com.gwssi.optimus.core.cache.CacheBlock;
import com.gwssi.optimus.core.cache.CacheManager;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import cn.gwssi.resource.Conts;

@Service
public class BlogService extends BaseService{
	
	private static  Logger log=Logger.getLogger(BlogController.class);
	
	/**
	 * 查询系统操作日志列表
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findSysLogList(Map<String, String> params) throws Exception {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_PT_SYS_LOG).append(" where 1=1 ");
		if(params!=null&&params.size()>0){
			String username = (String) params.get("username");
			String operatetime=(String) params.get("operatetime");
			String isfalg =(String) params.get("isfalg");
			
			if(StringUtils.isNotBlank(username)){
				sql.append(" and charindex('");
				sql.append(username);
				sql.append("',a.username)>0");
			}
			if(StringUtils.isNotBlank(operatetime)){
				sql.append(" and charindex('");
				sql.append(operatetime);
				sql.append("',a.operatetime)>0");
			}
			if(StringUtils.isNotBlank(isfalg)){
				sql.append(" and a.isfalg='");
				sql.append(isfalg);
				sql.append("'");
			}
		}
		sql.append(" order by operatetime desc");
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 保存系统操作日志
	 * @param logBO
	 * @throws OptimusException
	 */
    public void addUser(TPtSysLogBO logBO) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        dao.insert(logBO);
    }

    /**
     * 根据日志id获取详细
     * @param logid
     * @return
     * @throws OptimusException
     */
	public Map<String, String> findLog(String logid) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_PT_SYS_LOG);
	    sql.append(" where logid='").append(logid).append("'");
	    List<Map> list = dao.queryForList(sql.toString(), null);
	    Map<String,String> map = list.get(0);
	    return map;
	}

	/**
	 * 查询采集日志列表
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findCollectLogList(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_PT_SYS_LOG).append(" where 1=1");
		if(params!=null&&params.size()>0){
			String servicename = (String) params.get("servicename");
			String serviceobject = (String) params.get("serviceobject");
			String runstate=(String) params.get("runstate");
			String starttime=(String) params.get("starttime");
			String endtime=(String) params.get("endtime");
			String isfalg =(String) params.get("isfalg");
			
			if(StringUtils.isNotBlank(servicename)){
				sql.append(" and charindex('");
				sql.append(servicename);
				sql.append("',a.servicename)>0");
			}
			if(StringUtils.isNotBlank(serviceobject)){
				sql.append(" and charindex('");
				sql.append(serviceobject);
				sql.append("',a.serviceobject)>0");
			}
			if(StringUtils.isNotBlank(starttime)){
				sql.append(" and charindex('");
				sql.append(starttime);
				sql.append("',a.starttime)>0");
			}
			if(StringUtils.isNotBlank(endtime)){
				sql.append(" and charindex('");
				sql.append(endtime);
				sql.append("',a.endtime)>0");
			}
			if(StringUtils.isNotBlank(runstate)){
				sql.append(" and a.runstate ='");
				sql.append(runstate);
				sql.append("'");
			}
			if(StringUtils.isNotBlank(isfalg)){
				sql.append(" and isfalg='");
				sql.append(isfalg);
				sql.append("'");
			}
		}
		return dao.pageQueryForList(sql.toString(), null);
	}

	/**
	 * 查询分享日志信息列表
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findShareLogList(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_PT_FWRZJBXX).append(" where 1=1");
		if(params!=null&&params.size()>0){
			String callername = (String) params.get("callername");
			String callerip = (String) params.get("callerip");
			String executeresult=(String) params.get("executeresult");
			String executeway=(String) params.get("executeway");
			String callertime=(String) params.get("callertime");
			String executetime=(String) params.get("executetime");
			
			if(StringUtils.isNotBlank(callername)){
				sql.append(" and charindex('");
				sql.append(callername);
				sql.append("',callername)>0");
			}
			if(StringUtils.isNotBlank(callerip)){
				sql.append(" and charindex('");
				sql.append(callerip);
				sql.append("',callerip)>0");
			}
			if(StringUtils.isNotBlank(executetime)){
				sql.append(" and charindex('");
				sql.append(executetime);
				sql.append("',executetime)>0");
			}
			if(StringUtils.isNotBlank(callertime)){
				sql.append(" and charindex('");
				sql.append(callertime);
				sql.append("',callertime)>0");
			}
			if(StringUtils.isNotBlank(executeway)){
				sql.append(" and executeway ='");
				sql.append(executeway);
				sql.append("'");
			}
			if(StringUtils.isNotBlank(executeresult)){
				sql.append(" and executeresult ='");
				sql.append(executeresult);
				sql.append("'");
			}
		}
		sql.append(" order by convert(datetime,callertime) desc,convert(datetime,callerenttime) desc");
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * 查询分享日志详细信息列表
	 * @param id
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findShareLogDetailList(String id) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer(Conts.T_PT_FWRZXXXX).append(" where 1=1");
		if(StringUtils.isNotBlank(id)){
			sql.append(" and FWRZJBId='").append(id).append("'");
		}
		sql.append(" order by convert(datetime,starttime) asc,convert(datetime,endtime) asc");
		return dao.pageQueryForList(sql.toString(), null);
	}
	
	/**
	 * （定时）从缓存redis 14区块获取系统操作日志插入数据库
	 * @param params
	 * @throws OptimusException
	 */
	public void insertLogList(List<TPtSysLogBO> params) throws OptimusException {
		try{
			CacheBlock cacheBlock = CacheManager.getBlock(new Integer(ConfigManager.getProperty("logIndex")));
			Set<String> keys = cacheBlock.keys();
	        Iterator<String> it=keys.iterator();   
	        while(it.hasNext()){
	            String key = it.next();   
	            params.add((TPtSysLogBO)cacheBlock.get(key));
	        }
	        if(params!=null && params.size()>0){
        		IPersistenceDAO dao= this.getPersistenceDAO();
        		dao.insert(params);
        		Iterator<String> it1=keys.iterator();
        		while(it1.hasNext()){
                    cacheBlock.remove(it1.next());
                }
	        }
		}catch(Exception e){
    		throw new RuntimeException("日志写入错误，请联系数据中心运维人员!");
    	}
	}
	
	/**
	 * 服务日志
	 * @param params
	 * @throws OptimusException
	 */
	public void insertServiceLogList(List<TPtSysLogBO> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		dao.insert(params);
	}
	
	/**
	 * 服务日志详细
	 * @param params
	 * @throws OptimusException
	 */
	public void insertServiceLogDetailList(List<TPtSysLogBO> params) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		dao.insert(params);
	}
}
