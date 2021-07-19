package cn.gwssi.datachange.datashare.util;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

/**接口工具类，对数据格式进行解析，及各类条件
 * cn.gwssi.datachange.datashare.util
 * ServiceUtil.java
 * 下午12:00:39
 * @author wuminghua
 */
@Service
public class WebServiceUtil extends BaseService{
	
	private static  Logger log=Logger.getLogger(WebServiceUtil.class);

	/**
	 * 根据服务代码和版本获取服务及其内容
	 * @param serviceCode
	 * @param serviceVersion
	 * @return
	 * @throws OptimusException
	 */
	public Map findServiceByCodeVersion(String serviceCode,String serviceVersion) throws OptimusException{
		String sql="select * from T_PT_FWJBXX a,T_PT_FWNRXX b where a.ServiceConentId=b.FWNRId and a.serviceCode=? and a.serviceVersion=?";
		IPersistenceDAO dao=this.getPersistenceDAO();
		List params = new ArrayList();
		params.add(serviceCode);
		params.add(serviceVersion);
		List list=dao.queryForList(sql, params);
		Map map=null;
		if(list!=null&&list.size()>0){
			map=(Map) list.get(0);
		}
		//获取服务内容，为之后拼接查询条件进行准备、服务内容分为两类，为调用外部服务用
		return map;
	}
	
	public int findDataBysql(String sql)throws OptimusException{
		IPersistenceDAO dao=this.getPersistenceDAO();
		return dao.queryForInt(sql, null);
	}
	
	/**
	 * 根据sql获取数据
	 * @param list 
	 * @param sql
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findDataBySql(String sql) throws OptimusException{
		IPersistenceDAO dao=this.getPersistenceDAO();
		log.info("======="+sql); 
		List  result=dao.queryForList(sql, null);
		return result;
	}
	
	/**
	 * 根据sql获取数据(分页)
	 * @param sql
	 * @param pageNum 每页记录数
	 * @param pageIndex 第几页
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findDataBySql(String sql,int pageNum,int pageIndex) throws OptimusException{
		IPersistenceDAO dao=this.getPersistenceDAO();
		log.info("======="+sql); 
		List  result = dao.pageQueryForList(sql, null, pageNum, pageIndex);
		return result;
	}
	
	/*public void updateNotice(RequestContext requestContext)throws OptimusException{
		IPersistenceDAO dao=this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("update t_pt_fwdxjbxx set state='0' where serviceobjectip=? and serviceobjectuser=?");
		List<String> param = new ArrayList<String>();
		param.add(requestContext.getRequestorIp());
		param.add(requestContext.getCallObject());
		dao.execute(sbf.toString(), param);
		StringBuffer sbf1 = new StringBuffer("update serviceState set state='0' where ServiceId in (select ServiceID from t_pt_fwdxqxglb where serviceobjectid in(select fwdxjbid from t_pt_fwdxjbxx where serviceobjectip=? and serviceobjectuser=?))");
		dao.execute(sbf1.toString(), param);
	}*/
}
