package cn.gwssi.datachange.datashare.util;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class CommUtil  extends BaseService{

	private static  Logger log=Logger.getLogger(CommUtil.class);
	/**
	 * 根据服务和ip获取权限，及其对象需要缓存的数据
	 * @param serviceCode
	 * @param ip
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findCheckUser(String serviceobjectuser,String ip)throws OptimusException{
		List<Map> resultData = null;
		if (StringUtils.isNotBlank(serviceobjectuser) 
				&& StringUtils.isNotBlank(ip)){
			StringBuffer sbf = new StringBuffer("select * from t_pt_fwdxjbxx a,t_pt_fwdxqxglb b,t_pt_fwjbxx c where a.fwdxjbid=b.serviceobjectid and c.ServiceId=b.ServiceID and a.state='1' and c.ServiceState='1' and");
			sbf.append(" a.serviceobjectip=? and").append(" a.serviceobjectuser=?");
			List<String> param = new ArrayList<String>();
			param.add(ip);
			param.add(serviceobjectuser);
			IPersistenceDAO dao = this.getPersistenceDAO();
			resultData = dao.queryForList(sbf.toString(), param);
		}
		return resultData;
	}
	
	/**
	 * 获取所有对象的权限，定时发送到个对象去缓存
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findCheckUser()throws OptimusException{
		List<Map> resultData = null;
		StringBuffer sbf = new StringBuffer("select * from t_pt_fwdxjbxx a,t_pt_fwdxqxglb b,t_pt_fwjbxx c where a.fwdxjbid=b.serviceobjectid and c.ServiceId=b.ServiceID and a.state='1' and c.ServiceState='1' and");
		List<String> param = new ArrayList<String>();
		IPersistenceDAO dao = this.getPersistenceDAO();
		resultData = dao.queryForList(sbf.toString(), param);
		return resultData;
	}

}
