package com.gwssi.application.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmCodeTableManagerBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;
import com.gwssi.optimus.util.UuidGenerator;

@Service(value = "dictionaryService")
public class DictionaryService extends BaseService{
	
	/**
	 * 通过系统集成表主键，获取所有的系统名称
	 * @return 系统名称集合
	 * @throws OptimusException 
	 */
	public List querySystemList() throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//编写sql语句
        String sql = "select PK_SYS_INTEGRATION as value, SYSTEM_NAME as text from SM_SYS_INTEGRATION";
       
        //封装结果集
        List systemList = dao.queryForList(sql, null);
        
        return systemList;
	}

	
	
	/**
	 * 通过系统名称、代码名、描述、缓存类型查询，获取代码表信息
	 * @param params
	 * @return 查询到的代码表信息集合
	 * @throws OptimusException 
	 */
	public List getDef(Map params) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取查询条件的值
		String codeTableEnName = StringUtil.getMapStr(params, "codeTableEnName").trim().toUpperCase();
		String codeTableChName = StringUtil.getMapStr(params, "codeTableChName").trim();
		
		//编写sql语句
		StringBuffer sql=new StringBuffer();
		sql.append("select a.PK_CODE_TABLE_MANAGER,a.CODE_TABLE_CH_NAME,a.CODE_TABLE_EN_NAME,b.SYSTEM_NAME,")
		.append("a.CACHE_TYPE,a.EFFECTIVE_MARKER from SM_CODE_TABLE_MANAGER a,SM_SYS_INTEGRATION b")
		.append(" where a.PK_SYS_INTEGRATION=b.PK_SYS_INTEGRATION ");
		
		//当codeTableEnName有数据时
		if(StringUtils.isNotEmpty(codeTableEnName)){
			sql.append("and a.CODE_TABLE_EN_NAME like ?");
			listParam.add("%"+codeTableEnName+"%");
		}
		
		//当codeTableChName有数据时
		if(StringUtils.isNotEmpty(codeTableChName)){
			sql.append("and a.CODE_TABLE_Ch_NAME like ?");
			listParam.add("%"+codeTableChName+"%");
		}
		
		//封装结果集
		return dao.pageQueryForList(sql.toString(), listParam);	
		
		
	}
	
	/**
	 * 向代码表中添加信息
	 * @param smCodeTableManagerBO
	 * @throws OptimusException 
	 */
	public void saveDef(SmCodeTableManagerBO smCodeTableManagerBO) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//给主键pkCodeTableManager赋值
		String pkCodeTableManager=UuidGenerator.getUUID();
		smCodeTableManagerBO.setPkCodeTableManager(pkCodeTableManager);
		
		
		/*//给有效标记effectiveMarker赋值
		smCodeTableManagerBO.setEffectiveMarker(AppConstants.EFFECTIVE_Y);*/
		
		//添加记录
		dao.insert(smCodeTableManagerBO);
		
	}
	
	/**
	 * 通过代码表主键，获取代码表中已有的信息记录
	 * @param codeTableEnName
	 * @return 代码表信息
	 * @throws OptimusException 
	 */
	public SmCodeTableManagerBO queryCodeTableByName(String codeTableEnName) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		if(StringUtils.isBlank(codeTableEnName))
			return null;
		SmCodeTableManagerBO smCodeTableManagerBO=null;
		
		//编写查询sql
		StringBuffer sql=new StringBuffer();
		sql.append("select * from SM_CODE_TABLE_MANAGER where CODE_TABLE_EN_NAME=?");
		listParam.add(codeTableEnName);
		
		//封装结果集
		List list=dao.queryForList(SmCodeTableManagerBO.class,sql.toString(),listParam);
		
		//当list有值时，将查询的记录返回
		if(list!=null&&!list.isEmpty())
			smCodeTableManagerBO=(SmCodeTableManagerBO) list.get(0);
		return smCodeTableManagerBO;
	}
	
	/**
	 * 通过代码表主键，删除所有选中的记录
	 * @param pkCodeTableManager
	 * @throws OptimusException 
	 */
	public void deleteDef(String pkCodeTableManager) throws OptimusException{
        
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql语言
		String sql= "delete from SM_CODE_TABLE_MANAGER where PK_CODE_TABLE_MANAGER=?";
		/*listParam.add(AppConstants.EFFECTIVE_N);*/
		listParam.add(pkCodeTableManager);
		
		//执行sql
		dao.execute(sql.toString(), listParam);	
		
	}
	
	/**
	 * 通过代码表主键，回显记录
	 * @param pkCodeTableManager
	 * @return 代码表信息
	 * @throws OptimusException 
	 */
	public SmCodeTableManagerBO getDisplayDef(String pkCodeTableManager) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//封装结果集
		return dao.queryByKey(SmCodeTableManagerBO.class, pkCodeTableManager);
	}
	
	/**
	 * 通过代码表主键，修改记录
	 * @param smCodeTableManagerBO
	 * @throws OptimusException 
	 */
	public void updateDef(SmCodeTableManagerBO smCodeTableManagerBO) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取修改的数据
		String pkCodeTableManager = smCodeTableManagerBO.getPkCodeTableManager();
		String codeTableChName = smCodeTableManagerBO.getCodeTableChName();
		String pkSysIntegration = smCodeTableManagerBO.getPkSysIntegration();
		String cacheType = smCodeTableManagerBO.getCacheType();
		String codeColumn = smCodeTableManagerBO.getCodeColumn();
		String codeTableDesc = smCodeTableManagerBO.getCodeTableDesc();
		String effectiveMarker = smCodeTableManagerBO.getEffectiveMarker();
		String valueColumn = smCodeTableManagerBO.getValueColumn();
		String modifierId = smCodeTableManagerBO.getModifierId();
		String modifierName = smCodeTableManagerBO.getModifierName();
		
		
		//编写sql
		String sql = "update SM_CODE_TABLE_MANAGER set PK_SYS_INTEGRATION=?,CODE_TABLE_CH_NAME=?,CODE_COLUMN=?,VALUE_COLUMN=?,CODE_TABLE_DESC=?, "
				+ "CACHE_TYPE=?,EFFECTIVE_MARKER=?,MODIFIER_ID=?,MODIFIER_NAME=?,MODIFIER_TIME=? where PK_CODE_TABLE_MANAGER=?";
		listParam.add(pkSysIntegration);
		listParam.add(codeTableChName);
		listParam.add(codeColumn);
		listParam.add(valueColumn);
		listParam.add(codeTableDesc);
		listParam.add(cacheType);
		listParam.add(effectiveMarker);
		listParam.add(modifierId);
		listParam.add(modifierName);
		listParam.add(smCodeTableManagerBO.getModifierTime());
		listParam.add(pkCodeTableManager);
		
		
		//执行sql
		dao.execute(sql.toString(), listParam);
	}


	/**
	 * 创建代码表
	 * @param smCodeTableManagerBO
	 * @throws OptimusException 
	 */
	public void createDef(SmCodeTableManagerBO smCodeTableManagerBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//编写查询sql
		StringBuffer sql=new StringBuffer();
		
		sql.append("CREATE TABLE DB_YYJC.").append(smCodeTableManagerBO.getCodeTableEnName())
		.append("( CODE VARCHAR2(32) NOT NULL PRIMARY KEY ,NAME VARCHAR2(50) ,NAME_SHORT VARCHAR2(50) ,F_CODE VARCHAR2(32) ,")
		.append("CHOOSE_MARK VARCHAR2(1) ,CREATER_ID VARCHAR2(32) ,CREATER_NAME VARCHAR2(100) ,CREATER_TIME TIMESTAMP(6),")
		.append("MODIFIER_ID VARCHAR2(32) ,MODIFIER_NAME VARCHAR2(100) ,MODIFIER_TIME TIMESTAMP(6) )");
		
		//执行sql
		dao.execute(sql.toString(), null);
		createComment(smCodeTableManagerBO);
		
	}

	/**
	 * 删除代码表
	 * @param pkCodeTableManager
	 * @throws OptimusException 
	 */

	public void dropDef(String pkCodeTableManager) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写查询sql
		StringBuffer sql=new StringBuffer();
		sql.append("drop table DB_YYJC.").append(getEnName(pkCodeTableManager));
		
		dao.execute(sql.toString(), null);
		
		
	}

	/**
	 * 通过主键查询代码表英文名
	 * @param pkCodeTableManager
	 * @throws OptimusException 
	 */

	private String getEnName(String pkCodeTableManager) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写查询sql
		StringBuffer sql=new StringBuffer();
		sql.append("select CODE_TABLE_EN_NAME from SM_CODE_TABLE_MANAGER where PK_CODE_TABLE_MANAGER=?");
		listParam.add(pkCodeTableManager);
		
		//封装结果集
        List<Map> list = dao.queryForList(sql.toString(), listParam);
        System.out.println(list);
        String tableName = (String) list.get(0).get("codeTableEnName");
        
		return tableName;
	}


	/**
	 * 给创建的代码表添加注释
	 * @param smCodeTableManagerBO
	 * @throws OptimusException 
	 */
	private void createComment(SmCodeTableManagerBO smCodeTableManagerBO) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//代码表中的字段
		List namelist = new ArrayList();
		namelist.add("CODE");
		namelist.add("NAME");
		namelist.add("NAME_SHORT");
		namelist.add("F_CODE");
		namelist.add("CHOOSE_MARK");
		namelist.add("CREATER_ID");
		namelist.add("CREATER_NAME");
		namelist.add("CREATER_TIME");
		namelist.add("MODIFIER_ID");
		namelist.add("MODIFIER_NAME");
		namelist.add("MODIFIER_TIME");
		
		//各字段的注释
		List valuelist = new ArrayList();
		valuelist.add("代码值");
		valuelist.add("名称");
		valuelist.add("简称");
		valuelist.add("父节点代码(可选)");
		valuelist.add("选用标记");
		valuelist.add("创建人ID");
		valuelist.add("创建人");
		valuelist.add("创建时间");
		valuelist.add("最后修改人ID");
		valuelist.add("最后修改人");
		valuelist.add("最后修改时间");
		
		//编写查询sql
		for(int i=0;i<namelist.size();i++){
			StringBuffer sql=new StringBuffer();
			sql.append("COMMENT ON COLUMN DB_YYJC.").append(smCodeTableManagerBO.getCodeTableEnName()).append(".").append(namelist.get(i).toString()).append(" is '").append(valuelist.get(i)).append("'");
			dao. execute(sql.toString(), null);
		}
	}


	/**
	 * 通过代码表的英文名查询代码表的中文名
	 * @param codeTableEnName
	 * @throws OptimusException 
	 */
	public String getCodeTableChName(String codeTableEnName) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写查询sql
		StringBuffer sql=new StringBuffer();
		sql.append("select CODE_TABLE_CH_NAME FROM SM_CODE_TABLE_MANAGER where CODE_TABLE_EN_NAME=?");
		listParam.add(codeTableEnName);
		
		//封装结果
        List<Map> list = dao.queryForList(sql.toString(), listParam);
        String codeTableChName = (String) list.get(0).get("codeTableChName");
        
		return codeTableChName;
       
	}
}
