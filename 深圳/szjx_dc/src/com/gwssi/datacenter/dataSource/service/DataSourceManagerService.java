package com.gwssi.datacenter.dataSource.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.datacenter.model.DcColumnBO;
import com.gwssi.datacenter.model.DcDataSourceBO;
import com.gwssi.datacenter.model.DcProcedureBO;
import com.gwssi.datacenter.model.DcTableBO;
import com.gwssi.datacenter.model.DcTriggerBO;
import com.gwssi.datacenter.model.DcViewBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.util.UuidGenerator;


@Service
public class DataSourceManagerService extends BaseService{
	
    @Autowired
    private DAOManager daomanager;
    @Autowired
    private DataStructLoadBySqlService dataStructLoadBySqlService;
	private Logger log = Logger.getLogger(this.getClass());	
	/**
	 * 获取所有数据源
	 * @return
	 * @throws OptimusException 
	 */
	public List findDataSourceMenu() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List<String> str = new ArrayList<String>(); // ？传值
		 str.add(AppConstants.EFFECTIVE_Y);
		String sql = "select * from DC_DATA_SOURCE  where EFFECTIVE_MARKER= ? ORDER BY ORDER_NO";
		List list1 =dao.pageQueryForList(sql, str);
		//List list1 = dao.queryForList(sql, str);
		return list1;
	}
	/**
	 * 获取查询的数据源
	 * @param datasource
	 * @return
	 * @throws OptimusException 
	 */
	public List findDataSourceMenu(DcDataSourceBO datasource) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List<String> str = new ArrayList<String>(); // ？传值
		StringBuffer sql = new StringBuffer();
		
		//数据源类型
		String dataSourceType =datasource.getDataSourceType();
		
		//数据源名字
		String dataSourceName =datasource.getDataSourceName();
		
		//业务系统
		String pkDcBusiObject =datasource.getPkDcBusiObject();
		
		 sql.append(  "select * from DC_DATA_SOURCE where EFFECTIVE_MARKER= ? ") ;
		 str.add(AppConstants.EFFECTIVE_Y);
		 
		if(StringUtils.isNotEmpty(dataSourceType)){
			sql.append(" AND  DATA_SOURCE_TYPE = ?  ");
			str.add(dataSourceType);
		}
		if(StringUtils.isNotEmpty(dataSourceName)){
			sql.append(" AND   LOWER(DATA_SOURCE_NAME) like ? ");
			str.add("%"+dataSourceName.toLowerCase()+"%");
		}
		if(StringUtils.isNotEmpty(pkDcBusiObject)){
			sql.append(" AND   PK_DC_BUSI_OBJECT = ? ");
			str.add(pkDcBusiObject);
		}		
		sql.append("  ORDER BY ORDER_NO");
		List list1 = dao.pageQueryForList(sql.toString(), str);
		return list1;
	}
	/**
	 * 获取业务对象表的主键和 名称
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> findKeyValueDcBusiObjectBO() throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();

		 sql.append("select PK_DC_BUSI_OBJECT as value, BUSI_OBJECT_NAME  as text from DC_BUSI_OBJECT where EFFECTIVE_MARKER= ? ");
		 str.add(AppConstants.EFFECTIVE_Y);

		
		List list1 =dao.queryForList(sql.toString(),str);
		return list1;
	}
	
	
	/**
	 * 数据库中原始数据存在 测试数据库中动态数据源是否正常
	 * @return
	 */
	public boolean dodataSourceConnectDbExists(DcDataSourceBO datasource){
		Boolean  exists=doTestDynmicIsExists(datasource.getPkDcDataSource());
		if(!exists){
			dataStructLoadBySqlService.addDynmicDataSource(datasource);
		}
		return doTestDynmicIsConnectAble(datasource);		
	}
	/**
	 * 数据库存在 测试数据库中动态数据源是否正常
	 * @return
	 */
	public boolean dodataSourceConnectDbNotExists(DcDataSourceBO datasource){
		String key=UuidGenerator.getUUID();
		datasource.setPkDcDataSource(key);
		dataStructLoadBySqlService.addDynmicDataSource(datasource);
		boolean connAble=doTestDynmicIsConnectAble(datasource);
		try {
			daomanager.deleteDataSource(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connAble;
	}
	
	public boolean doTestDynmicIsExists(String key){
		Boolean exists=true;
		IPersistenceDAO dao = getPersistenceDAO(key);
		if(dao==null){
			exists=false;
		}
		return exists;
	}
	public boolean doTestDynmicIsConnectAble(DcDataSourceBO datasource){
		Boolean able=false;
		Connection conn =null;
		try{
/*			IPersistenceDAO dao = getPersistenceDAO(datasource.getPkDcDataSource());
 			int x=dao.execute("select * from dual", null);
 			able=true;*/
			 conn=	daomanager.getConnection(datasource.getPkDcDataSource());
			able=true;
			daomanager.releaseConnection(conn, datasource.getPkDcDataSource());
 		}catch(Exception ex){
 			
 		}finally{
 			if(conn==null){
 				
 			}else{
 				daomanager.releaseConnection(conn, datasource.getPkDcDataSource());
 			}
 		}
		return able;
	}	
	
	/**
	 * 新增数据源
	 * @param datasource
	 * @throws OptimusException
	 */
	public void insertDcDataSourceBO(DcDataSourceBO datasource) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(datasource);
	}
	
	/**
	 * 获取数据源通过主键
	 * @param pkDcDataSource 数据源主键
	 * @return
	 * @throws OptimusException
	 */
	public DcDataSourceBO findDcDataSourceBOByPK(String pkDcDataSource) throws OptimusException {
        IPersistenceDAO dao = getPersistenceDAO();
        DcDataSourceBO sms = dao.queryByKey(DcDataSourceBO.class, pkDcDataSource);
        return sms;
	}
	/**
	 * 修改数据源
	 * @param datasource
	 * @throws OptimusException 
	 */
	public void updateDcDataSourceBO(DcDataSourceBO datasource) throws OptimusException {
		DcDataSourceBO oldsms=this.findDcDataSourceBOByPK(datasource.getPkDcDataSource());
		boolean isEqual=isEqualDcDataSourceBO(oldsms,datasource);
		if(!isEqual){
			dataStructLoadBySqlService.updateDynmicDataSource(datasource);
		}
		IPersistenceDAO dao = getPersistenceDAO();
		
		dao.update(datasource);
	}	
	/**
	 * 验证修改后对动态数据源是否有影响
	 * @param oldSource
	 * @param newSource
	 * @return
	 */
	public  boolean isEqualDcDataSourceBO(DcDataSourceBO oldSource,DcDataSourceBO newSource){
		String o1=oldSource.getDataSourceIp();
		String n1=newSource.getDataSourceIp();
		
		String o2=oldSource.getDataSourceName();
		String n2=newSource.getDataSourceName();

		String o3=oldSource.getDataSourceStatus();
		String n3=newSource.getDataSourceStatus();

		String o4=oldSource.getDataSourceType();
		String n4=newSource.getDataSourceType();		
		
		String o5=oldSource.getDbInstance();
		String n5=newSource.getDbInstance();			
		
		String o6=oldSource.getDbType();
		String n6=newSource.getDbType();		
		
		return StringUtils.equals(n1,o1)&&StringUtils.equals(n2,o2)&&StringUtils.equals(n3,o3)&&StringUtils.equals(n4,o4)&&StringUtils.equals(n5,o5)&&StringUtils.equals(n6,o6);

	}
	
	
	/**
	 * 获取数据源的key-value
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> findKeyValueDcDmDstypeBO() throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();

		 sql.append("select code as value, name  as text from DC_DM_DSTYPE");
	
		
		List list1 =dao.queryForList(sql.toString(),null);
		return list1;
	}
	/**
	 * 获取数据库的key-value
	 * @return
	 * @throws OptimusException
	 */
	public List<Map<String, Object>> findKeyValueDcDmDbtypeBO() throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();

		 sql.append("select code as value, name  as text from DC_DM_DBTYPE");
	
		
		List list1 =dao.queryForList(sql.toString(),null);
		return list1;
	}
	/**
	 * 删除动态数据源
	 * @param datasource
	 * @throws Exception 
	 */
	public void deleteDynamicDataSourceBO(DcDataSourceBO datasource) throws Exception {
		
		daomanager.deleteDataSource(datasource.getPkDcDataSource());
		//删除所有其他的
		deleteAllaboutDataSourceFields(datasource);
	
		//删除数据源
		updateDcDataSourceBO(datasource);//更新
	}
	/**
	 * 删除 字段等
	 * @param datasource
	 * @throws OptimusException 
	 */
	public void deleteAllaboutDataSourceFields(DcDataSourceBO datasource) throws OptimusException {
		updateDcDataSourceBO(datasource);
		
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);//读取静态User
		//所有字段
		List<DcColumnBO> l1=findNativeFieldsBO(datasource);
		for(DcColumnBO bo:l1){
			bo.setModifierId(user.getUserId());
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());
			bo.setEffectiveMarker(AppConstants.EFFECTIVE_N);
			updateDcColumnBOList(bo);
		}
		
		
		//删除表
		List<DcTableBO> tableList =dataStructLoadBySqlService.findNativeTableBo(datasource);
		for(DcTableBO bo:tableList){
			bo.setEffectiveMarker(AppConstants.EFFECTIVE_N);			
			bo.setModifierId(user.getUserId());//修改人ID
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());	//修改时间	
		}
		dataStructLoadBySqlService.updateNativeTable(tableList);
		
		//删除视图
		List<DcViewBO> views=dataStructLoadBySqlService.findNativeViews(datasource);
		for(DcViewBO bo:views){
			bo.setEffectiveMarker(AppConstants.EFFECTIVE_N);			
			bo.setModifierId(user.getUserId());//修改人ID
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());	//修改时间				
		}
		dataStructLoadBySqlService.updateNativeViews(views);
		
		//删除存储过程
		List<DcProcedureBO> procedurceList=dataStructLoadBySqlService.findNativeProcedure(datasource);
		for(DcProcedureBO bo:procedurceList){
			bo.setEffectiveMarker(AppConstants.EFFECTIVE_N);			
			bo.setModifierId(user.getUserId());//修改人ID
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());	//修改时间				
		}
		
		dataStructLoadBySqlService.updateNativeProcedure(procedurceList);
		
		
		//触发器
		List<DcTriggerBO>  triggers=dataStructLoadBySqlService.findNativeTrigger(datasource);
		for(DcTriggerBO bo:triggers){
			bo.setEffectiveMarker(AppConstants.EFFECTIVE_N);			
			bo.setModifierId(user.getUserId());//修改人ID
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());	//修改时间				
		}
		dataStructLoadBySqlService.updateNativeTriggers(triggers);		
	
		//表变更情况  删除 不是置为N
		dataStructLoadBySqlService.deleteDcChangeBO(datasource);
		
	}
	/**
	 * 更新 字段list
	 * @param list
	 * @throws OptimusException 
	 */
	public void updateDcColumnBOList(DcColumnBO bo) throws OptimusException{

				IPersistenceDAO dao = getPersistenceDAO();
				dao.update(bo);
	
	}
	/**
	 * 获取本地所存储的远程字段
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public List<DcColumnBO> findNativeFieldsBO(DcDataSourceBO bo) throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
		 StringBuffer sql = new StringBuffer();
		 List<String> str = new ArrayList<String>();
		 sql.append(" select * from dc_column t where t.effective_marker= ? and t.pk_dc_data_source= ? ");		
		 str.add(AppConstants.EFFECTIVE_Y);
		 str.add(bo.getPkDcDataSource());
		 List<DcColumnBO> l1 = dao.queryForList(DcColumnBO.class, sql.toString(), str);
		 return l1;	 
	}
	

	/**
	 * 获取数据源代码集
	 * @return
	 * @throws OptimusException 
	 */
	public List<Map<String, Object>> findKeyValueDataSourceBO() throws OptimusException {
		IPersistenceDAO dao=getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		sql.append("select PK_DC_DATA_SOURCE as value, DATA_SOURCE_NAME  as text from DC_DATA_SOURCE where EFFECTIVE_MARKER= ? ");
		str.add(AppConstants.EFFECTIVE_Y);
		List list1 =dao.queryForList(sql.toString(),str);
		return list1;
	}
	/**
	 * 获取系统表的
	 * @return 
	 * @throws OptimusException 
	 * 
	 */
	public List querySmSysIntegrationBOKeyValue() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		//编写sql语句
        String sql = "select PK_SYS_INTEGRATION as value, SYSTEM_NAME as text from SM_SYS_INTEGRATION where EFFECTIVE_MARKER= ?  ";
		
        List<String> str = new ArrayList<String>();
		str.add(AppConstants.EFFECTIVE_Y);
        //封装结果集
        List systemList = dao.queryForList(sql, str);
        
        return systemList;
	}
	
}
