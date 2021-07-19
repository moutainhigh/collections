package com.gwssi.application.home.service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



















import com.gwssi.application.common.AppConstants;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.persistence.jdbc.StoredProcParam;
import com.gwssi.optimus.core.service.BaseService;


@Service("oaService")
public class OAService extends BaseService {
    @Autowired
    private DAOManager daomanager;
	/**
	 * 多数据源
	 * 获取财务系统的数据源的key 该数据源在初始化的时候会自动加载
	 * @return 
	 */
	private static String getCW_datasourcekey(){
		Properties properties = ConfigManager.getProperties("optimus");
		
		String key= properties.getProperty("CwDataSource");


		return key;
	}
	
	private static String getDJXK_datasourcekey(){
		Properties properties = ConfigManager.getProperties("optimus");
		String key= properties.getProperty("DJXK_DataSource");
		return key;
	}
	private static String getPending_datasourcekey(){
		Properties properties = ConfigManager.getProperties("optimus");
		String key= properties.getProperty("wcm_DataSource");
		return key;
	}
	
	
	private static Logger logger = Logger.getLogger(OAService.class); // 日志

	public int dogetWaitOaNO(String userId) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();

		// 编写查询sql
		StringBuffer sql = new StringBuffer();
		sql.append(
	"SELECT count(*)" +
	"  FROM (SELECT ROW_.*, ROWNUM ROWNUM_" + 
	"          FROM (SELECT WF.ID                AS ASSINGMENT_ID," + 
	"                       WF.PROCESS_TYPE      AS PROCESS_TYPE," + 
	"                       WF.ORGAN_ID," + 
	"                       WF.ORGAN_NAME," + 
	"                       WF.CREATE_TIME," + 
	"                       WF.ACT_DEF_NAME," + 
	"                       WF.PROC_DEF_NAME," + 
	"                       WF.PROC_CREATE_TIME," + 
	"                       WF.PRE_ACT_DEF_NAMES," + 
	"                       WF.PRE_ORGAN_NAMES," + 
	"                       WF.PROCESS_ID," + 
	"                       WF.ACTIVITY_ID," + 
	"                       EW.SUBJECT," + 
	"                       EW.WORD_NUMBER," + 
	"                       EW.DO_SERI_NO," + 
	"                       EW.INSTANT_LEVEL," + 
	"                       EW.ID                AS EDOC_BASE_ID" + 
	"                  FROM WF_DAIBAN_TASK_VIEW@OA WF" + 
	"                 RIGHT JOIN EDOC_BASE_VIEW@OA EW" + 
	"                    ON WF.PROCESS_ID = EW.PROC_INSTANCE_ID" + 
	"                 WHERE WF.PROCESS_TYPE IN ('DCWORK_EDOC_SEND'," + 
	"                                           'DCWORK_EDOC_ACCEPT'," + 
	"                                           'DCWORK_TRANS'," + 
	"                                           'DCWORK_DUBAN')" + 
	"                   AND WF.IS_VISIBLE = '1'" + 
	"                   AND ORGAN_ID =" + 
	"                       (SELECT ORGAN_ID" + 
	"                          FROM PUB_ORGAN@OA" + 
	"                         WHERE UPPER(ORGAN_CODE) =?" + 
	"                             )" + 
	"                 ORDER BY DECODE(WF.PROCESS_TYPE," + 
	"                                 'DCWORK_EDOC_SEND'," + 
	"                                 0," + 
	"                                 'DCWORK_EDOC_ACCEPT'," + 
	"                                 1," + 
	"                                 'DCWORK_DUBAN'," + 
	"                                 2," + 
	"                                 'DCWORK_TRANS'," + 
	"                                 3)," + 
	"                          WF.CREATE_TIME DESC) ROW_" + 
	"         WHERE ROWNUM <= 15)" + 
	" WHERE ROWNUM_ > 0"
	);
		List paramList = new ArrayList();
		paramList.add(StringUtils.upperCase(userId));

		return dao.queryForInt(sql.toString(), paramList);
	}

	public String dogetWaitCWNO(String userId) throws OptimusException, SQLException {
		IPersistenceDAO dao = getPersistenceDAO(getCW_datasourcekey());
		String count =null;
		Connection conn =null;
		ResultSet rs=null;
		PreparedStatement pstmt = null;
		try{
/*			IPersistenceDAO dao = getPersistenceDAO(datasource.getPkDcDataSource());
 			int x=dao.execute("select * from dual", null);
 			able=true;*/
			conn=	daomanager.getConnection(getCW_datasourcekey());
			pstmt= conn.prepareStatement("{call toAuditFromAllDatabase(?)}");
			pstmt.setString(1, StringUtils.replace(StringUtils.upperCase(userId), "@SZAIC", ""));
		    rs = pstmt.executeQuery();
		    if(rs.next()){
		    	count=rs.getString(1);
		    	System.out.println("获取代办数量："+count);
		    }
			daomanager.releaseConnection(conn, getCW_datasourcekey());
 		}catch(Exception ex){
 			
 		}finally{
 			if(rs!=null){
 				rs.close();
 			}
 			if(pstmt!=null){
 				pstmt.close();
 			}
 			if(conn==null){
 				
 			}else{
 				daomanager.releaseConnection(conn, getCW_datasourcekey());
 			}
 			
 		}
/*		List<StoredProcParam> params = new ArrayList<StoredProcParam>();
		StoredProcParam p1=new StoredProcParam();
		p1.setIndex(1);
		p1.setValue(StringUtils.replace(StringUtils.upperCase(userId), "@SZAIC", ""));
		System.out.println(StringUtils.replace(StringUtils.upperCase(userId), "@SZAIC", ""));
		p1.setParamType(StoredProcParam.INOUT);
		p1.setDataType(java.sql.Types.VARCHAR);
		params.add(p1);
		List result = dao.callStoreProcess("{call toAuditFromAllDatabase(?)}", params);
	    List<Map> userList =null;
		if(result.size()>0){
			userList= (List<Map>)result.get(0);
		    Map<String,Object> map =userList.get(0);
		    for(String s:map.keySet()){
		    	System.out.println("key:"+s+"value:"+map.get(s));
		    }
		    
		}else{
			System.out.println(result);
		}*/
		return count;

	  
		
	}
	
	
	/***
	 * 登记许可service
	 * @param userId
	 * @return count 待办数
	 */
	public int dogetWaitDJXKNO(String userId) throws OptimusException, SQLException {
		
		int count = 0;
		IPersistenceDAO dao = getPersistenceDAO(getDJXK_datasourcekey());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT "
				+ "	count(*) "
				+ "	FROM GCLOUD_GIAP.GIAP_APPLY_BASE BASE"
				+ "	LEFT JOIN  GCLOUD_GIAP.GIAP_APPLY_WORKFLOW WF    "
				+ "	ON BASE.SERIAL_NO = WF.SERIAL_NO "
				+ "	WHERE EXISTS "
				+ "	(SELECT 1  FROM  GCLOUD_GIAP.GIAP_APPLY_DAI_BAN DB         WHERE BASE.SERIAL_NO = DB.SERIAL_NO           AND DB.ORGAN_ID = ?)"
				+ "	AND BASE.IS_FINISH = \'0\'"
				+ "	AND WF.IS_FINISH = \'0\'"
				+ "	AND BASE.TASK_STATE = \'TODO\'"
				+ "	ORDER BY WF.TASK_CREATE_TIME DESC"
				);
			List<String> paramList = new ArrayList<String>();
			paramList.add(StringUtils.upperCase(userId));
			int  daibanCount =dao.queryForInt(sql.toString(), paramList);
			logger.debug("daiban>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+daibanCount);
			count += daibanCount;
			
 			sql = new StringBuffer();
 			sql.append(" SELECT count(*) FROM GCLOUD_NAME.NAME_ENTERPRISE_INFO EI JOIN GCLOUD_NAME.NAME_TASK_ALLOCATION TA ON EI.ID=TA.INFO_ID WHERE 1=1 AND ( EI.APPROVAL_STATE = \'10\' OR EI.APPROVAL_STATE = \'12\' OR EI.APPROVAL_STATE = \'13\' ) AND TA.ORGAN_ID = ? ORDER BY EI.APPLY_DATE DESC");
 			count+=dao.queryForInt(sql.toString(), paramList);
 			logger.debug("daiban>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+count);
 			
 			sql = new StringBuffer();
 			sql.append("SELECT count(*)  FROM LS_BPM.WF_DAI_BAN_TASK PROCTABLE WHERE PROCTABLE.IS_VISIBLE = \'1\'   AND PROCTABLE.PROCESS_TYPE = \'HR\' AND ORGAN_ID =? ORDER BY PROCTABLE.CREATE_TIME");
 			count+=dao.queryForInt(sql.toString(), paramList);
 			logger.debug("daiban>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+count);
		return count;
		
	}
	/***
	 * wcm待审批service
	 * @param userId
	 * @return count 待办数
	 */
	public int dogetWaitPending(String userName) throws OptimusException, SQLException {
		int count = 0;
		IPersistenceDAO dao = getPersistenceDAO(getPending_datasourcekey());
		
		StringBuffer sql = new StringBuffer();
		sql.append("select w.USERID from WCMUSER w  where w.USERNAME=?");
		List<String> paramList = new ArrayList<String>();
		paramList.add(StringUtils.upperCase(userName));
		int  userId =dao.queryForInt(sql.toString(), paramList);
		logger.debug("userId>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
		sql = new StringBuffer();
		sql.append("select count(1) from  WCMFLOWDOC w where w.worked = 0and w.worktime is null  and w.tousers is not null and( w.tousers =? or  w.tousers like concat(concat(?,\',\'),\'%\') or  w.tousers like concat(concat(\'%\',\',\'),?)  or   w.tousers like concat(concat(\'%\',\',\'),concat(?,concat(\',\',\'%\'))) )");
		paramList = new ArrayList<String>();
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(userId));
		paramList.add(String.valueOf(userId));
		count=dao.queryForInt(sql.toString(), paramList);
		logger.debug("wcm wait>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+count);
		return count;
		
	}

	
}
