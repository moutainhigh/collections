package cn.gwssi.datachange.dataservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gwssi.datachange.dataservice.model.TPtFwdxjbxxBO;
import cn.gwssi.datachange.dataservice.model.TPtFwdxqxglbBO;
import cn.gwssi.datachange.dataservice.model.TPtFwdypzjbxxBO;
import cn.gwssi.datachange.dataservice.model.TPtFwjbxxBO;
import cn.gwssi.datachange.dataservice.model.TPtFwnrxxBO;
import cn.gwssi.datachange.msg_push.bus.FwdyServiceManager;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.sybase.jdbc3.jdbc.SybSQLException;

@Service
public class DataServiceService extends BaseService{
	
	@Autowired
	private FwdyServiceManager dyManager;

	/**
	 * 服务基本信息查询
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectServiceList(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO();
		StringBuffer sql = new StringBuffer("select * from T_PT_FWJBXX where 1=1");
		if (params != null && params.size() > 0) {
			String servicename=(String) params.get("servicename");
			String servicetype=(String) params.get("servicetype");
			String servicestate=(String) params.get("servicestate");
			String gnId=(String) params.get("gnId");
			String executetype = (String) params.get("executetype");
			//String servicecode=(String) params.get("servicecode");
			//String serviceoffername=(String) params.get("serviceoffername");
			if(StringUtils.isNotBlank(executetype)){
				sql.append(" and executetype='");
				sql.append("0".equals(executetype) ? "0'" : "1'");
			}
			if(StringUtils.isNotBlank(servicename)){
				sql.append(" and charindex('");
				sql.append(servicename);
				sql.append("',servicename)>0");
			}
			if(StringUtils.isNotBlank(servicetype)){
				sql.append(" and charindex('");
				sql.append(servicetype);
				sql.append("',servicetype)>0");
			}
			if(StringUtils.isNotBlank(servicestate)){
				sql.append(" and charindex('");
				sql.append(servicestate);
				sql.append("',servicestate)>0");
			}
			if(StringUtils.isNotBlank(gnId)){
				sql.append(" and charindex('");
				sql.append(gnId);
				sql.append("',region)>0");
			}
			/*if(StringUtils.isNotBlank(servicecode)){
				sql.append(" and charindex('");
				sql.append(servicecode);
				sql.append("',servicecode)>0");
			}
			if(StringUtils.isNotBlank(serviceoffername)){
				sql.append(" and charindex('");
				sql.append(serviceoffername);
				sql.append("',serviceoffername)>0");
			}*/
		}
		sql.append(" order by createtime desc");
		List l = dao.pageQueryForList(sql.toString(), null);
		/*IPersistenceDAO dao1 = this.getPersistenceDAO("iqDataSource");
		List aaa=dao1.queryForList("select top 100 * from t_reg_entry_exit", null);
		System.out.println(aaa);*/
		return l;
	}
	
	/**
	 * 根据内容id获取服务
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectServiceByContentId(String contentId) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer("select * from T_PT_FWJBXX  where serviceState = '0' ");
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotBlank(contentId)){
			sql.append(" and ServiceConentId = ?");
			list.add(contentId);
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 根据服务id获取服务对象，如果有就删除中间表记录
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public void delServiceObjectByServiceId(String serviceid) throws OptimusException{
		if(StringUtils.isNotBlank(serviceid)){
			IPersistenceDAO dao= this.getPersistenceDAO();
			List<String> list = new ArrayList<String>();
			StringBuffer sql=new StringBuffer("delete from T_PT_FWDXQXGLB ");
			sql.append(" where ServiceID = ?");
			list.add(serviceid);
			dao.execute(sql.toString(), list);
		}
	}
	
	/**
	 * 获取服务详细信息
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectServiceDetail(Map<String, String> params) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer("select * from T_PT_FWJBXX ");
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String serviceid = (String) params.get("serviceid");
			if(StringUtils.isNotBlank(serviceid)){
				sql.append(" where serviceid = ?");
				list.add(serviceid);
			}
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 服务树
	 * @return
	 * @throws OptimusException
	 */
	public List selectServiceTree(String fwdxjbid) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.ServiceId as id, '0' as pid, t.ServiceName as name from T_PT_FWJBXX t where t.servicestate='0'";
        String isempty="select fwdxjbId from T_PT_FWdxJBXX where fwdxjbId = '"+fwdxjbid+"' and state='0'";
        List list=dao.queryForList(isempty, null);
        if(list != null && list.size() > 0){
        	List funcList = dao.queryForList(sql, null);
        	return funcList;
        }
        return null;
    }
	
	/**
	 * 服务内容查询
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectServiceContentList(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO();
		StringBuffer sql = new StringBuffer("select * from T_PT_FWNRXX where 1=1");
		if (params != null && params.size() > 0) {
			String createperson=(String) params.get("createperson");
			String createtime=(String) params.get("createtime");
			String isenabled=(String) params.get("isenabled");
			if(StringUtils.isNotBlank(createperson)){
				sql.append(" and charindex('");
				sql.append(createperson);
				sql.append("',createperson)>0");
			}
			if(StringUtils.isNotBlank(createtime)){
				sql.append(" and charindex('");
				sql.append(createtime);
				sql.append("',createtime)>0");
			}
			if(StringUtils.isNotBlank(isenabled)){
				sql.append(" and charindex('");
				sql.append(isenabled);
				sql.append("',isenabled)>0");
			}
		}
		sql.append(" order by createtime desc");
		List l = dao.pageQueryForList(sql.toString(), null);
		return l;
	}
	
	/**
	 * 内容树
	 * @return
	 * @throws OptimusException
	 */
	public List selectServiceContentTree() throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.FWNRId as id, '0' as pid, t.ServiceContentShow as name from T_PT_FWNRXX t where t.isenabled='0'";
        List funcList = dao.queryForList(sql, null);
        return funcList;
    }
	
	public List selectServiceContent() throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.FWNRId as value, '0' as pid, t.ServiceContentShow as text from datacenter.dbo.T_PT_FWNRXX t where t.isenabled='0'";
        List funcList = dao.queryForList(sql, null);
        return funcList;
    }
	
	/**
	 * 服务对象（服务账户）查询
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectServiceObjectList(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO();
		StringBuffer sql = new StringBuffer("select * from T_PT_FWDXJBXX where 1=1");
		if (params != null && params.size() > 0) {
			String serviceobjectname=(String) params.get("serviceobjectname");
			String serviceobjectuser=(String) params.get("serviceobjectuser");
			String serviceobjectip=(String) params.get("serviceobjectip");
			String serviceobjectregorg=(String) params.get("serviceobjectregorg");
			String controlobjectstate = (String) params.get("controlobjectstate");
			System.out.println(serviceobjectregorg);
			String createtime=(String) params.get("createtime");
			String objectsectororsystem=(String) params.get("objectsectororsystem");
			String state=(String) params.get("state");
			
			if(StringUtils.isNotBlank(controlobjectstate)){
				sql.append(" and controlobjectstate='").append("0".equals(controlobjectstate) ? "0'" : "1'");
			}
			
			if(StringUtils.isNotBlank(createtime)){
				sql.append(" and charindex('");
				sql.append(createtime);
				sql.append("',createtime)>0");
			}
			if(StringUtils.isNotBlank(objectsectororsystem)){
				sql.append(" and charindex('");
				sql.append(objectsectororsystem);
				sql.append("',objectsectororsystem)>0");
			}
			if(StringUtils.isNotBlank(state)){
				sql.append(" and charindex('");
				sql.append(state);
				sql.append("',state)>0");
			}		
			if(StringUtils.isNotBlank(serviceobjectname)){
				sql.append(" and charindex('");
				sql.append(serviceobjectname);
				sql.append("',serviceobjectname)>0");
			}
			if(StringUtils.isNotBlank(serviceobjectuser)){
				sql.append(" and charindex('");
				sql.append(serviceobjectuser);
				sql.append("',serviceobjectuser)>0");
			}
			if(StringUtils.isNotBlank(serviceobjectip)){
				sql.append(" and charindex('");
				sql.append(serviceobjectip);
				sql.append("',serviceobjectip)>0");
			}
			if(StringUtils.isNotBlank(serviceobjectregorg)){
				sql.append(" and charindex('");
				sql.append(serviceobjectregorg);
				sql.append("',serviceobjectregion)>0");
			}
		}
		sql.append(" order by createtime desc");
		List l = dao.pageQueryForList(sql.toString(), null);
		return l;
	}
	
	public List<Map> selectAllServiceObjectList() throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO();
		StringBuffer sql = new StringBuffer("select * from T_PT_FWDXJBXX where 1=1");
		List l = dao.queryForList(sql.toString(), null);
		return l;
	}
	/**
	 * 获取服务对象提供的服务信息   和可供使用的服务信息
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectServiceObjectDetailsfw(Map<String, String> params) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer("select servicename,createtime,createperson,servicestate from T_PT_FWJBXX ");
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String fwdxjbid = (String) params.get("fwdxjbid");
			if(StringUtils.isNotBlank(fwdxjbid)){
				sql.append(" where serviceobjectid = ?");
				list.add(fwdxjbid);
			}
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 获取服务对象详细信息
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectServiceObjectDetail(Map<String, String> params) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer("select * from T_PT_FWDXJBXX ");
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String fwdxjbid = (String) params.get("fwdxjbid");
			if(StringUtils.isNotBlank(fwdxjbid)){
				sql.append(" where FWDXJBId = ?");
				list.add(fwdxjbid);
			}
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	
	public List<Map> selectServiceContentDetail(Map<String, String> params) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer("select * from T_PT_FWNRXX ");
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String fwnrid = (String) params.get("fwnrid");
			if(StringUtils.isNotBlank(fwnrid)){
				sql.append(" where fwnrid = ?");
				list.add(fwnrid);
			}
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}

	/**
	 * 保存服务对象
	 * @param tPtFwdxjbxxBO
	 * @throws OptimusException
	 */
	public int addServiceObject(TPtFwdxjbxxBO tPtFwdxjbxxBO) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        try{
        	dao.insert(tPtFwdxjbxxBO);
        	return 0;
        }catch(Exception e){
        	if(e.getCause() instanceof SybSQLException){
        		SybSQLException se = (SybSQLException) e.getCause();
        		if(se.getErrorCode() == 2601)
        			return -1;
        	}
        	throw e;
        }
    }
	
	/**
	 * 保存服务内容
	 * @param tPtFwnrxxBO
	 * @throws OptimusException
	 */
	public int addServiceContent(TPtFwnrxxBO tPtFwnrxxBO) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        try{
	        dao.insert(tPtFwnrxxBO);
	        return 0;
	    }catch(Exception e){
	    	if(e.getCause() instanceof SybSQLException){
	    		SybSQLException se = (SybSQLException) e.getCause();
	    		if(se.getErrorCode() == 2601)
	    			return -1;
	    	}
	    	throw e;
	    }
    }
	
	
	/**
	 * 保存服务内容之前预览或者校验情况
	 * @param req
	 * @param res
	 * @throws OptimusException
	 */
	public List<Map> previewServiceContent(String sql) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO("iqDataSource");
		if(sql!=null){
			return dao.queryForList(sql, null);
		}else{
			return null;
		}
	}
	
	
	/**
	 * 保存服务
	 * @param tPtFwdxjbxxBO
	 * @throws OptimusException
	 */
	public void addService(TPtFwjbxxBO tPtFwjbxxBO) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        dao.insert(tPtFwjbxxBO);
    }
	
	public void addService(TPtFwjbxxBO tPtFwjbxxBO,TPtFwdypzjbxxBO tb) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        UUID uuid  =  UUID.randomUUID(); 
        tPtFwjbxxBO.setServiceid(uuid.toString());
        tb.setServiceid(uuid.toString());
        dao.insert(tPtFwjbxxBO);
        dao.insert(tb);
        //订阅则增加 @wuyincheng
        //dyManager.addConfig(tb, tPtFwjbxxBO.getServicestate());
    }
	
	//新增一个订阅
	public int addService(TPtFwdypzjbxxBO tb) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        UUID uuid  =  UUID.randomUUID(); 
        tb.setFwdypzjbxxid(uuid.toString());
        TPtFwjbxxBO fw = null;
        int result = 0;
        if(tb.getServiceid() != null && !"".equals(tb.getServiceid().trim())) { //获取对应服务信息
        	List<String> params = new ArrayList<String>(4);
        	params.add(tb.getServiceid());
        	List<TPtFwjbxxBO> fws = dao.queryForList(TPtFwjbxxBO.class, "select * from T_PT_FWJBXX  where serviceid = ?", params);
        	if(fws.size() > 0){
        		fw = fws.get(0);
	        	//if(!"0".equals(config.getServicestate())){ //服务已经停用
	        	if("0".equals(fw.getServicestate())){//服务启用
	        		dao.insert(tb);
	        	}else{
	        		result = -1;
	        	}
        	}else{
        		result = -1;
        	}
        		
        }
        if(result != -1){ //增加成功
	        dyManager.addConfig(tb);
        }
        return result;
    }
	
	/**
	 * 更新服务对象
	 * @param tb
	 * @throws OptimusException
	 */
	public void updateServiceObject(TPtFwdxjbxxBO tb) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
        dao.update(tb);
	}
	
	/**
	 * 更新服务内容
	 * @param tb
	 * @throws OptimusException
	 */
	public void updateServiceContent(TPtFwnrxxBO tb) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
        dao.update(tb);
	}
	
	/**
	 * 更新服务
	 * @param tb
	 * @throws OptimusException
	 */
	public void updateService(TPtFwjbxxBO tb) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
        int r = dao.update(tb);
        //订阅服务这里需要
        //dyManager.updateConfig(tb);
	}
	
	public void updateService(TPtFwjbxxBO tb,TPtFwdypzjbxxBO tb1) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
		if(tb!=null && StringUtils.isNotEmpty(tb.getServiceid())){
			tb1.setServiceid(tb.getServiceid());
			dao.update(tb1);
			//服务订阅监听,订阅表里没有状态值，@wuyincheng
		}
		dao.update(tb);
		//dyManager.updateConfig(tb);
        
	}
	
	/**
	 * 删除对象并非物理删除，只是修改状态为不可用（0）
	 * @param fwdxjbid
	 * @throws OptimusException
	 */
	public void deleteServiceObject(String fwdxjbid) throws OptimusException{
		if(StringUtils.isBlank(fwdxjbid))
            return;
        IPersistenceDAO dao = getPersistenceDAO();
        //删除用户记录
        StringBuffer sqlSbf = new StringBuffer();
        sqlSbf.append("update T_PT_FWDXJBXX set State='0' where fwdxjbid='").append(fwdxjbid).append("'");
        dao.execute(sqlSbf.toString(), null);
	}
	
	/**
	 * 
	 * 启停服务对象---对应的主题跟着启停
	 * @param fwdxjbid
	 * @param state
	 * @throws OptimusException
	 */
	public void updateRunServiceObject(String fwdxjbid,String state) throws OptimusException{
		if(StringUtils.isBlank(fwdxjbid))
            return;
        IPersistenceDAO dao = getPersistenceDAO();
        StringBuffer sqlTheme = new StringBuffer();
        if("1".equals(state)){
        	sqlTheme.append("update T_PT_THEMEXX set isStart='1' where fwdxjbid='").append(fwdxjbid).append("'");
        	//对象停用，服务删除
        	dao.execute("delete from T_PT_FWDXQXGLB where ServiceobjectID='"+fwdxjbid+"'", null);
        	//增加停用该对象所属的服务
        	dao.execute("update T_PT_FWJBXX set servicestate='1' where serviceobjectid='"+fwdxjbid+"'", null);
        }else{
        	sqlTheme.append("update T_PT_THEMEXX set isStart='0' where fwdxjbid='").append(fwdxjbid).append("'");
        }
    	dao.execute(sqlTheme.toString(), null);
        StringBuffer sqlSbf = new StringBuffer();
        sqlSbf.append("update T_PT_FWDXJBXX set state='"+state+"' where fwdxjbid='").append(fwdxjbid).append("'");
        dao.execute(sqlSbf.toString(), null);
    }
	
	
	/**
	 * 
	 * 启停服务对象---查找服务对象
	 * @param fwdxjbid
	 * @param state
	 * @throws OptimusException
	 */
	public List<Map> findServiceObject(String fwdxjbid) throws OptimusException{
			IPersistenceDAO dao = getPersistenceDAO();
	        StringBuffer sqlSbf = new StringBuffer();
	        sqlSbf.append("select * from  T_PT_FWDXJBXX  where fwdxjbid='").append(fwdxjbid).append("'");
	       return  dao.queryForList(sqlSbf.toString(), null);
        }
	
	
	
	/**
	 * 启停服务内容
	 * @param fwnrid
	 * @param isenabled
	 * @throws OptimusException
	 */
	public void updateRunServiceContent(String fwnrid,String isenabled) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
        StringBuffer sqlSbf = new StringBuffer();
        sqlSbf.append("update T_PT_FWNRXX set IsEnabled='"+isenabled+"' where FWNRId='").append(fwnrid).append("'");
        dao.execute(sqlSbf.toString(), null);
	}
	
	/**
	 * 启停服务 --服务
	 * @param fwdxjbid
	 * @param state
	 * @throws OptimusException
	 */
	public void updateRunService(String serviceid,String state) throws OptimusException{
		if(StringUtils.isBlank(serviceid))
            return;
        IPersistenceDAO dao = getPersistenceDAO();
        if(state=="1"){
       //先删除和对象的中间表 
        StringBuffer sqlfwdx = new StringBuffer();
        sqlfwdx.append("delete from T_PT_FWDXQXGLB where serviceID = '").append(serviceid).append("'");
        dao.execute(sqlfwdx.toString(), null);
        }
        //再改变服务的状态
        StringBuffer sqlSbf = new StringBuffer();
        sqlSbf.append("update T_PT_FWJBXX set serviceState='"+state+"' where serviceid='").append(serviceid).append("'");
        dao.execute(sqlSbf.toString(), null);
        //@订阅服务需要对应更新
        //dyManager.updateConfig(serviceid, state);
	}

	/**
	 * 授权
	 * @param tPtFwdxqxglbBO
	 * @param serviceIds
	 * @throws OptimusException
	 */
	public void insertLicense(TPtFwdxqxglbBO tPtFwdxqxglbBO, List<String> serviceIds) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
        if(StringUtils.isNotEmpty(tPtFwdxqxglbBO.getServiceobjectid())){
            String sql = "delete from T_PT_FWDXQXGLB where serviceobjectid='"+tPtFwdxqxglbBO.getServiceobjectid()+"'";
            dao.execute(sql, null);
        }
        if(serviceIds.isEmpty()){
        }else{
	        List<TPtFwdxqxglbBO> tPtFwdxqxglbBOList = new ArrayList<TPtFwdxqxglbBO>();
	        TPtFwdxqxglbBO t = null;
	        for(String serviceId : serviceIds){
	        	t = new TPtFwdxqxglbBO();
	        	t.setPkid(UUID.randomUUID().toString());
	        	t.setServiceid(serviceId);
	        	t.setServiceobjectid(tPtFwdxqxglbBO.getServiceobjectid());
	        	tPtFwdxqxglbBOList.add(t);
	        }
	        dao.insert(tPtFwdxqxglbBOList);
        }
	}

	/**
	 * 获取服务树选择节点    ----替换成下面的
	 * @param fwdxjbid
	 * @return
	 * @throws OptimusException
	 */
	public List selectServicemakeCheckedTree(String fwdxjbid) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.serviceid from T_PT_FWDXQXGLB t where t.serviceobjectid=(select v.fwdxjbid from t_pt_fwdxjbxx v where v.fwdxjbid='"+fwdxjbid+"' and v.state='0')";
        List authFuncList = dao.queryForList(sql, null);
        return authFuncList;
	}
	
	
	/**
	 * 获取服务列表  
	 * @param fwdxjbid
	 * @return
	 * @throws OptimusException
	 */
	public List selectServiceTable() throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select a.serviceId,a.serviceName,a.description,a.servicestate,b.serviceObjectName from t_pt_fwjbxx a, t_pt_fwdxjbxx b  where a.serviceobjectid=b.fwdxjbid and  a.servicestate = '0' order by a.serviceobjectid,a.createTime ";
        List allList = dao.queryForList(sql, null);
        return allList;
	}
	

	/**
	 * 获取已选取服务列表  
	 * @param fwdxjbid
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectCheckedServiceTable(String fwdxid) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select b.serviceid sid　from T_PT_FWDXQXGLB a,t_pt_fwjbxx b where a.serviceid=b.serviceid and b.servicestate = '0' and a.serviceobjectid=?";
        List params=new ArrayList();
        params.add(fwdxid);
        return dao.queryForList(sql,params);
	}
	
	/**
	 * 获取服务内容树选择节点
	 * @param serviceid
	 * @return
	 * @throws OptimusException
	 */
	public List selectServiceContentmakeCheckedTree(String serviceid) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.serviceconentid from T_PT_FWJBXX t where t.serviceid='"+serviceid+"'";
        List authFuncList = dao.queryForList(sql, null);
        return authFuncList;
	}

	/**
	 * 订阅服务配置基本信息
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectSubscriptionList(Map<String, String> params) throws OptimusException {
		IPersistenceDAO dao = this.getPersistenceDAO();
		StringBuffer sql = new StringBuffer("select * from T_PT_FWDYPZJBXX where 1=1");
		if (params != null && params.size() > 0) {
			String subscriptionobject=(String) params.get("subscriptionobject");
			String startsubscribetime=(String) params.get("startsubscribetime");
			String frequency=(String) params.get("frequency");
			String acceptway=(String) params.get("acceptway");
			
			if(StringUtils.isNotBlank(subscriptionobject)){
				sql.append(" and charindex('");
				sql.append(subscriptionobject);
				sql.append("',subscriptionobject)>0");
			}
			if(StringUtils.isNotBlank(startsubscribetime)){
				sql.append(" and charindex('");
				sql.append(startsubscribetime);
				sql.append("',startsubscribetime)>0");
			}
			if(StringUtils.isNotBlank(frequency)){
				sql.append(" and charindex('");
				sql.append(frequency);
				sql.append("',frequency)>0");
			}
			if(StringUtils.isNotBlank(acceptway)){
				sql.append(" and charindex('");
				sql.append(acceptway);
				sql.append("',acceptway)>0");
			}
		}
		List l = dao.pageQueryForList(sql.toString(), null);
		return l;
	}
	
	/**
	 * 更新订阅服务配置
	 * @param tb
	 * @throws OptimusException
	 */
	public void updateSubscription(TPtFwdypzjbxxBO tb) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
        int t = dao.update(tb);
        dyManager.updateConfig(tb);
        //dyManager.updateConfig(tb);;
        //update @wuyincheng
//        if(t > 0)
//        	dyManager.updateConfig(tb);
	}
	
	/**
	 * 保存订阅服务配置
	 * @param tPtFwdxjbxxBO
	 * @throws OptimusException
	 */
	public void addSubscription(TPtFwdypzjbxxBO tb) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        dao.insert(tb);
        //update @wuyincheng
        //dyManager.addConfig(tb, "0");
    }
	
	/**
	 * 获取订阅服务配置详细信息
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectSubscriptionDetail(Map<String, String> params) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer("select * from T_PT_FWDYPZJBXX ");
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String fwdypzjbxxid = (String) params.get("fwdypzjbxxid");
			if(StringUtils.isNotBlank(fwdypzjbxxid)){
				sql.append(" where FWDYPZJBXXId = ?");
				list.add(fwdypzjbxxid);
			}
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 订阅服务配置树
	 * @return
	 * @throws OptimusException
	 */
	public List selectSubscriptionTree() throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.FWDYPZJBXXId as id, '0' as pid, t.subscriptionObject as name from T_PT_FWDYPZJBXX t where t.serviceid is null";
        List funcList = dao.queryForList(sql, null);
        return funcList;
    }
	
	/**
	 * 获取服务内容树选择节点
	 * @param serviceid
	 * @return
	 * @throws OptimusException
	 */
	public List selectSubscriptionmakeCheckedTree(String serviceid) throws OptimusException{
        IPersistenceDAO dao = getPersistenceDAO();
        String sql = "select t.FWDYPZJBXXId from T_PT_FWDYPZJBXX t where t.serviceid='"+serviceid+"'";
        List authFuncList = dao.queryForList(sql, null);
        return authFuncList;
	}
	
	/**
	 * 根据服务id获取订阅服务配置信息
	 * @param params
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> selectSubscriptionDetailByServiceid(Map<String, String> params) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer("select * from T_PT_FWDYPZJBXX ");
		List<String> list = new ArrayList<String>();
		if(params!=null&&params.size()>0){
			String serviceid = (String) params.get("serviceid");
			if(StringUtils.isNotBlank(serviceid)){
				sql.append(" where ServiceId = ?");
				list.add(serviceid);
			}
		}else{
			return null;
		}
		return dao.queryForList(sql.toString(), list);
	}
	
	/**
	 * 
	 * 初始化区域的值 - 如：广东省/广州市/企管处/登记系统
	 * @param params
	 * @return
	 * @throws OptimusException
	 * 
	 */
	public List<Map> iniArea(String name,String type) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=null;
		if("belongArea".equals(name)){
		sql=new StringBuffer("select distinct qhcode,qhvalue from t_dm_xzqhdm where qhcode like '%00'");
		}else{
			if("belongOrg".equals(name)){
				if(type!=""){
				type=type.substring(0,type.length()-2);
				sql=new StringBuffer("select distinct jgcode,jgvalue from t_dm_xzqhdm where qhcode like '"+type+"%'");		
				}
			}
		}
		return dao.queryForList(sql.toString(), null);
	}
	/**
	 * 验证是否已存在服务名
	 * @param name
	 * @return
	 * @throws OptimusException
	 */
	public boolean checkServiceName(String name) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sql=new StringBuffer("select count(1) from t_pt_fwjbxx where servicename ='");
		sql.append(name);
		sql.append("'");
		int i=dao.queryForInt(sql.toString(), null);
		if(i==0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 服务树  选择项  和列表
	 * @param fwnrid
	 * @return
	 * @throws OptimusException 
	 */
	public List fromnrselectServicemakeCheckedTree(String fwnrid) throws OptimusException {
			IPersistenceDAO dao = getPersistenceDAO();
	        String sql = "select t.serviceId  from t_pt_fwjbxx t where t.serviceConentId='"+fwnrid+"' and t.serviceState='0'";
	        List authFuncList = dao.queryForList(sql, null);
	        return authFuncList;
	}
	
	/**
	 * 服务树  列表
	 * @param fwnrid
	 * @return
	 * @throws OptimusException 
	 */
	public List fromnrselectServiceTree(String fwnrid) throws OptimusException {
			IPersistenceDAO dao = getPersistenceDAO();
	        String sql = "select t.serviceId  as id, '0' as pid, t.ServiceName as name  from t_pt_fwjbxx t where t.serviceConentId='"+fwnrid+"' and t.serviceState='0'";
	        	List funcList = dao.queryForList(sql, null);
	        	return funcList;
	}	
	
	
	public List selectListenerInfo(String serviceId, String serviceObjectId) {
		List<String> params = new ArrayList<String>(8);
		if(serviceId != null)
			params.add(serviceId);
		if(serviceObjectId != null)
			params.add(serviceObjectId);
		List result = null;
		try {
			result = getPersistenceDAO().
					      queryForList("select * from T_PT_FWDYPZJBXX where 1=1 "
					    		      + (serviceId == null ? "" : " and serviceId = ?")
					    		      + (serviceObjectId == null ? "" : " and serviceObjectId = ?"), params);
		} catch (OptimusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
	}
	
	
	//获取服务内容sql
	public List queryFwnrsql(String id ) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
		String sql="select * from T_PT_FWNRXX where fwnrid='"+id+"' ";
		List list=dao.queryForList(sql, null);
		return list;
	}
}
