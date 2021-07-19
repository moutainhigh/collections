package com.gwssi.datacenter.dataSource.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmGrantAuthBO;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.application.model.SmServicesBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.datacenter.model.DcColumnBO;
import com.gwssi.datacenter.model.DcDataSourceBO;
import com.gwssi.datacenter.model.DcProcedureBO;
import com.gwssi.datacenter.model.DcTableBO;
import com.gwssi.datacenter.model.DcTriggerBO;
import com.gwssi.datacenter.model.DcViewBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.persistence.dao.OptimusPersistenceDAO;
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
	 * 鑾峰彇鎵�鏈夋暟鎹簮
	 * @return
	 * @throws OptimusException 
	 */
	public List findDataSourceMenu() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List<String> str = new ArrayList<String>(); // 锛熶紶鍊�
		 str.add(AppConstants.EFFECTIVE_Y);
		String sql = "select * from DC_DATA_SOURCE  where EFFECTIVE_MARKER= ? ORDER BY ORDER_NO";
		List list1 =dao.pageQueryForList(sql, str);
		//List list1 = dao.queryForList(sql, str);
		return list1;
	}
	/**
	 * 鑾峰彇鏌ヨ鐨勬暟鎹簮
	 * @param datasource
	 * @return
	 * @throws OptimusException 
	 */
	public List findDataSourceMenu(DcDataSourceBO datasource) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List<String> str = new ArrayList<String>(); // 锛熶紶鍊�
		StringBuffer sql = new StringBuffer();
		
		//鏁版嵁婧愮被鍨�
		String dataSourceType =datasource.getDataSourceType();
		
		//鏁版嵁婧愬悕瀛�
		String dataSourceName =datasource.getDataSourceName();
		
		//涓氬姟绯荤粺
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
	 * 鑾峰彇涓氬姟瀵硅薄琛ㄧ殑涓婚敭鍜� 鍚嶇О
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
	 * 鏁版嵁搴撲腑鍘熷鏁版嵁瀛樺湪 娴嬭瘯鏁版嵁搴撲腑鍔ㄦ�佹暟鎹簮鏄惁姝ｅ父
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
	 * 鏁版嵁搴撳瓨鍦� 娴嬭瘯鏁版嵁搴撲腑鍔ㄦ�佹暟鎹簮鏄惁姝ｅ父
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
	 * 鏂板鏁版嵁婧�
	 * @param datasource
	 * @throws OptimusException
	 */
	public void insertDcDataSourceBO(DcDataSourceBO datasource) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(datasource);
	}
	
	/**
	 * 鑾峰彇鏁版嵁婧愰�氳繃涓婚敭
	 * @param pkDcDataSource 鏁版嵁婧愪富閿�
	 * @return
	 * @throws OptimusException
	 */
	public DcDataSourceBO findDcDataSourceBOByPK(String pkDcDataSource) throws OptimusException {
        IPersistenceDAO dao = getPersistenceDAO();
        DcDataSourceBO sms = dao.queryByKey(DcDataSourceBO.class, pkDcDataSource);
        return sms;
	}
	/**
	 * 淇敼鏁版嵁婧�
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
	 * 楠岃瘉淇敼鍚庡鍔ㄦ�佹暟鎹簮鏄惁鏈夊奖鍝�
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
	 * 鑾峰彇鏁版嵁婧愮殑key-value
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
	 * 鑾峰彇鏁版嵁搴撶殑key-value
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
	 * 鍒犻櫎鍔ㄦ�佹暟鎹簮
	 * @param datasource
	 * @throws Exception 
	 */
	public void deleteDynamicDataSourceBO(DcDataSourceBO datasource) throws Exception {
		
		daomanager.deleteDataSource(datasource.getPkDcDataSource());
		//鍒犻櫎鎵�鏈夊叾浠栫殑
		deleteAllaboutDataSourceFields(datasource);
	
		//鍒犻櫎鏁版嵁婧�
		updateDcDataSourceBO(datasource);//鏇存柊
	}
	/**
	 * 鍒犻櫎 瀛楁绛�
	 * @param datasource
	 * @throws OptimusException 
	 */
	public void deleteAllaboutDataSourceFields(DcDataSourceBO datasource) throws OptimusException {
		updateDcDataSourceBO(datasource);
		
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);//璇诲彇闈欐�乁ser
		//鎵�鏈夊瓧娈�
		List<DcColumnBO> l1=findNativeFieldsBO(datasource);
		for(DcColumnBO bo:l1){
			bo.setModifierId(user.getUserId());
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());
			bo.setEffectiveMarker(AppConstants.EFFECTIVE_N);
			updateDcColumnBOList(bo);
		}
		
		
		//鍒犻櫎琛�
		List<DcTableBO> tableList =dataStructLoadBySqlService.findNativeTableBo(datasource);
		for(DcTableBO bo:tableList){
			bo.setEffectiveMarker(AppConstants.EFFECTIVE_N);			
			bo.setModifierId(user.getUserId());//淇敼浜篒D
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());	//淇敼鏃堕棿	
		}
		dataStructLoadBySqlService.updateNativeTable(tableList);
		
		//鍒犻櫎瑙嗗浘
		List<DcViewBO> views=dataStructLoadBySqlService.findNativeViews(datasource);
		for(DcViewBO bo:views){
			bo.setEffectiveMarker(AppConstants.EFFECTIVE_N);			
			bo.setModifierId(user.getUserId());//淇敼浜篒D
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());	//淇敼鏃堕棿				
		}
		dataStructLoadBySqlService.updateNativeViews(views);
		
		//鍒犻櫎瀛樺偍杩囩▼
		List<DcProcedureBO> procedurceList=dataStructLoadBySqlService.findNativeProcedure(datasource);
		for(DcProcedureBO bo:procedurceList){
			bo.setEffectiveMarker(AppConstants.EFFECTIVE_N);			
			bo.setModifierId(user.getUserId());//淇敼浜篒D
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());	//淇敼鏃堕棿				
		}
		
		dataStructLoadBySqlService.updateNativeProcedure(procedurceList);
		
		
		//瑙﹀彂鍣�
		List<DcTriggerBO>  triggers=dataStructLoadBySqlService.findNativeTrigger(datasource);
		for(DcTriggerBO bo:triggers){
			bo.setEffectiveMarker(AppConstants.EFFECTIVE_N);			
			bo.setModifierId(user.getUserId());//淇敼浜篒D
			bo.setModifierName(user.getUserName());
			bo.setModifierTime(Calendar.getInstance());	//淇敼鏃堕棿				
		}
		dataStructLoadBySqlService.updateNativeTriggers(triggers);		
	
		//琛ㄥ彉鏇存儏鍐�  鍒犻櫎 涓嶆槸缃负N
		dataStructLoadBySqlService.deleteDcChangeBO(datasource);
		
	}
	/**
	 * 鏇存柊 瀛楁list
	 * @param list
	 * @throws OptimusException 
	 */
	public void updateDcColumnBOList(DcColumnBO bo) throws OptimusException{

				IPersistenceDAO dao = getPersistenceDAO();
				dao.update(bo);
	
	}
	/**
	 * 鑾峰彇鏈湴鎵�瀛樺偍鐨勮繙绋嬪瓧娈�
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
	 * 鑾峰彇鏁版嵁婧愪唬鐮侀泦
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
	 * 鑾峰彇绯荤粺琛ㄧ殑
	 * @return 
	 * @throws OptimusException 
	 * 
	 */
	public List querySmSysIntegrationBOKeyValue() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		
		//缂栧啓sql璇彞
        String sql = "select PK_SYS_INTEGRATION as value, SYSTEM_NAME as text from SM_SYS_INTEGRATION where EFFECTIVE_MARKER= ?  ";
		
        List<String> str = new ArrayList<String>();
		str.add(AppConstants.EFFECTIVE_Y);
        //灏佽缁撴灉闆�
        List systemList = dao.queryForList(sql, str);
        
        return systemList;
	}
	
}
