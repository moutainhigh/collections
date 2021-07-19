package com.gwssi.datacenter.dataSource.service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpSession;

import oracle.jdbc.driver.OracleConnection;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmFunctionBO;
import com.gwssi.application.model.SmGrantAuthBO;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.application.model.SmServicesBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.datacenter.model.DcChangeBO;
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
public class DataStructLoadBySqlService extends BaseService {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private DAOManager daomanager;
	
	//数据结构装载oracle数据库
	@Autowired
	private DataStructLoadOracle dataStructLoadOracle;

	/**
	 * 设置数据源
	 * 
	 * @param smser
	 */
	protected void doSetDataSource(DcDataSourceBO smser) {
		IPersistenceDAO dao = getPersistenceDAO(smser.getPkDcDataSource());
		if (dao == null) {
			addDynmicDataSource(smser);
		}
	}

	/**
	 * 增加动态数据源
	 * 
	 * @param smser
	 */
	public void addDynmicDataSource(DcDataSourceBO smser) {
		String driverClass = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@" + smser.getDataSourceIp() + ":"
				+ smser.getAccessPort() + ":" + smser.getDbInstance();

		Properties properties = new Properties();
		properties.setProperty("username", smser.getUserName());
		properties.setProperty("password", smser.getPwd());
		properties.setProperty("driverClassName", driverClass);
		properties.setProperty("url", url);

		try {
			daomanager
					.addDataSource(
							smser.getPkDcDataSource(),
							"org.springframework.jdbc.datasource.DriverManagerDataSource",
							"oracle", properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改动态数据源 不是表
	 * 
	 * @param smser
	 *            数据源信息
	 */
	public void updateDynmicDataSource(DcDataSourceBO smser) {
		IPersistenceDAO dao = getPersistenceDAO(smser.getPkDcDataSource());
		if (dao == null) {

		} else {
			String driverClass = "oracle.jdbc.OracleDriver";
			String url = "jdbc:oracle:thin:@" + smser.getDataSourceIp() + ":"
					+ smser.getAccessPort() + ":" + smser.getDbInstance();

			Properties properties = new Properties();
			properties.setProperty("username", smser.getUserName());
			properties.setProperty("password", smser.getPwd());
			properties.setProperty("driverClassName", driverClass);
			properties.setProperty("url", url);

			try {
				daomanager
						.updateDataSource(
								smser.getPkDcDataSource(),
								"org.springframework.jdbc.datasource.DriverManagerDataSource",
								"oracle", properties);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取当前选择的数据源的Table数
	 * 
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public List<Map<String, Object>> findCurrUserTable(DcDataSourceBO smser)
			throws OptimusException {
		
		
	/*	if( smser.getDbType().equals("Oracle")){*/
		String dbtype= smser.getDbType();
		if(StringUtils.equalsIgnoreCase(dbtype, "Oracle")){
			return dataStructLoadOracle.findCurrUserTable(smser);
		}else{
			return null;
		}
	}

	/**
	 * 把查出来的table 转换为树
	 * 
	 * @param list
	 * @return
	 */
	public List<Map<String, Object>> changDcTableBOToTreeList(
			List<DcTableBO> list) {
		List system = new ArrayList();
		Map systemN = null;
		for (DcTableBO sm : list) {
			systemN = new HashMap();
			systemN.put("id", sm.getTableNameEn());
			if (StringUtils.isEmpty(sm.getTableNameCn())) {
				systemN.put("name",
						sm.getTableNameEn() + "(" + sm.getTableNameEn() + ")");
			} else {
				systemN.put("name",
						sm.getTableNameEn() + "(" + sm.getTableNameCn() + ")");
			}

			systemN.put("pid", "system");
			system.add(systemN);
		}

		return system;
	}

	/**
	 * 获取列单个表的列信息
	 * 
	 * @param smser
	 * @param table
	 * @return
	 * @throws OptimusException
	 */
	public List<DcColumnBO> findTableColumn(DcDataSourceBO smser,
			DcTableBO table) throws OptimusException {

		String dbtype= smser.getDbType();
		if(StringUtils.equalsIgnoreCase(dbtype, "Oracle")){
			return dataStructLoadOracle.findTableColumn(smser,table);
		}else{
			return null;
		}
	}

	/**
	 * 获取表中索引列
	 * 
	 * @param smser
	 *            数据源类
	 * @param table
	 *            表
	 * @return
	 * @throws OptimusException
	 */
	public List<String> findIndexInfo(DcDataSourceBO smser, DcTableBO table)
			throws OptimusException {
		String dbtype= smser.getDbType();
		if(StringUtils.equalsIgnoreCase(dbtype, "Oracle")){
			return dataStructLoadOracle.findIndexInfo(smser,table);
		}else{
			return null;
		}
	}

	/**
	 * 获取表中主键列名
	 * 
	 * @param smser
	 * @param table
	 * @return
	 * @throws OptimusException
	 */
	public List<String> findPrimaryKeysInfo(DcDataSourceBO smser,
			DcTableBO table) throws OptimusException {
		String dbtype= smser.getDbType();
		if(StringUtils.equalsIgnoreCase(dbtype, "Oracle")){
			return dataStructLoadOracle.findPrimaryKeysInfo(smser,table);
		}else{
			return null;
		}
	}

	/**
	 * 获取某个数据源下的表
	 * 
	 * @return
	 * @throws OptimusException
	 */
	public List<DcTableBO> findTables(DcDataSourceBO smser)
			throws OptimusException {
		String dbtype= smser.getDbType();
		if(StringUtils.equalsIgnoreCase(dbtype, "Oracle")){
			return dataStructLoadOracle.findTables(smser);
		}else{
			return null;
		}
	}

	/**
	 * 处理保存的 所加载的表 以及表中字段
	 * 
	 * @param tablenames
	 *            表名
	 * @param smser
	 *            数据源BO
	 * @return
	 * @throws OptimusException
	 */
	public boolean addTableAndFileds(List<String> tablenames,
			DcDataSourceBO smser) throws OptimusException {

		// 从缓存中获取当前登录用户信息
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		// 某个数据源当前用户下的所有表
		List<DcTableBO> tabls = findTables(smser);
		docompareTables(tabls, smser, tablenames);
		/*
		 * //要增加到记录的表 List<DcTableBO> needToInsertTables =new
		 * ArrayList<DcTableBO>(); for(DcTableBO bo:tabls){
		 * if(tablenames.contains(bo.getTableNameEn())){
		 * bo.setCreaterId(user.getUserId());
		 * bo.setCreaterName(user.getUserName());
		 * bo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
		 * bo.setCreaterTime(Calendar.getInstance());
		 * bo.setPkDcDataSource(smser.getPkDcDataSource());
		 * bo.setPkDcTable(UuidGenerator.getUUID()); needToInsertTables.add(bo);
		 * } }
		 * 
		 * //增加所需要增加的表 if(needToInsertTables.size()>0){ //判断是否为空
		 * insertDcTableBO(needToInsertTables); } //需要增加的字段 List<DcColumnBO>
		 * needToInsertFields=new ArrayList<DcColumnBO>(); for(DcTableBO
		 * bo:needToInsertTables){
		 * 
		 * List<DcColumnBO> needFields=new ArrayList<DcColumnBO>();
		 * needFields=getTableColumn(smser,bo); //设置索引列。。。
		 * needToInsertFields.addAll(setKeyIndex(smser,bo,needFields)); }
		 * 
		 * for(DcColumnBO bo:needToInsertFields){
		 * bo.setCreaterId(user.getUserId());
		 * bo.setCreaterName(user.getUserName());
		 * bo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
		 * bo.setCreaterTime(Calendar.getInstance());
		 * bo.setPkDcDataSource(smser.getPkDcDataSource());
		 * 
		 * if(bo.getColumnType().length()>10){
		 * System.out.println("---------------------"
		 * +bo.getColumnNameCn()+""+bo.getColumnNameEn()); }
		 * 
		 * }
		 * 
		 * 
		 * if(needToInsertFields.size()>0){
		 * insertDcColumnBO(needToInsertFields); }
		 */

		return true;
	}

	/**
	 * 比较表格 决定是增加 、修改、删除 以及后续操作
	 * 
	 * @param tabls
	 *            远程数据源获取的BO
	 * @param smser
	 *            数据源
	 * @param tablenames
	 *            当前用户选择的表
	 * @return
	 * @throws OptimusException
	 */
	public void docompareTables(List<DcTableBO> tabls, DcDataSourceBO smser,
			List<String> tablenames) throws OptimusException {
		// 从缓存中获取当前登录用户信息
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		// 获取当前系统的表BO
		List<DcTableBO> nativeTableBO = findNativeTableBo(smser);

		// 把当前系统转所存储的远程BO转为Map
		Map<String, DcTableBO> nativeTableMap = TablesListToMap(nativeTableBO);

		// 所需要修改的BO
		List<DcTableBO> needToUpdate = new ArrayList<DcTableBO>();

		// 所需要增加的BO
		List<DcTableBO> needToInsert = new ArrayList<DcTableBO>();

		// 远程表BO 循环遍历
		for (DcTableBO bo : tabls) {
			if (tablenames.contains(bo.getTableNameEn())) {
				if (nativeTableMap.get(bo.getTableNameEn()) == null) {
					// 需要增加的BO
					bo.setCreaterId(user.getUserId());
					bo.setCreaterName(user.getUserName());
					bo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
					bo.setCreaterTime(Calendar.getInstance());
					bo.setPkDcDataSource(smser.getPkDcDataSource());
					bo.setPkDcTable(UuidGenerator.getUUID());
					needToInsert.add(bo);
				} else {
					// 需要修改的BO
					needToUpdate.add(bo);
				}
			}

		}

		// 需要删除的BO
		List<DcTableBO> needToDelete = new ArrayList<DcTableBO>();
		for (DcTableBO bo : tabls) {
			// 需要删除的表
			DcTableBO nativeBO = nativeTableMap.get(bo.getTableNameEn());
			if (nativeBO == null) {
			} else {
				nativeTableMap.remove(bo.getTableNameEn());
			}
		}

		Set<Entry<String, DcTableBO>> entries = nativeTableMap.entrySet();
		for (Entry<String, DcTableBO> entry : entries) {
			needToDelete.add(entry.getValue());
			// System.out.println(entry.getKey()+":"+entry.getValue());
		}

		doNeedToInsertTableDual(needToInsert, smser);
		doNeedToUpdateTableDual(needToUpdate, smser);
		doNeedToDeleteTableDual(needToDelete, smser);

	}

	/**
	 * 需要删除的
	 * 
	 * @param needToInsert
	 * @throws OptimusException
	 */
	public void doNeedToDeleteTableDual(List<DcTableBO> needToDelete,
			DcDataSourceBO smser) throws OptimusException {
		// 从缓存中获取当前登录用户信息
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if (needToDelete.size() > 0) {
			for (DcTableBO add : needToDelete) {
				add.setModifierId(user.getUserId());
				add.setModifierTime(Calendar.getInstance());
				add.setModifierName(user.getUserId());
				add.setEffectiveMarker(AppConstants.EFFECTIVE_N);
			}
		}
		updateNativeTable(needToDelete);
		doNeedToDeleteFieldsFromTable(needToDelete, smser);

	}

	/**
	 * 删除表 对应所删除表的所有字段
	 * 
	 * @param needTodelete
	 * @param smser
	 * @throws OptimusException
	 */
	public void doNeedToDeleteFieldsFromTable(List<DcTableBO> needTodelete,
			DcDataSourceBO smser) throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		// 需要往变更表中填写的
		List<DcChangeBO> needtoInsertDcChange = new ArrayList<DcChangeBO>();

		if (needTodelete.size() > 0) {
			List<DcColumnBO> needTodeleteFields = new ArrayList<DcColumnBO>();
			for (DcTableBO bo : needTodelete) {
				List<DcColumnBO> needToInsertFields = findNativeFieldsBO(bo);
				needTodeleteFields.addAll(needToInsertFields);

				// 表删除记录
				needtoInsertDcChange.addAll(this.addChangbo(needToInsertFields,
						bo, AppConstants.CHANGE_TABLE_DELETE));
			}

			for (DcColumnBO bo : needTodeleteFields) {
				bo.setEffectiveMarker(AppConstants.EFFECTIVE_N);
				bo.setModifierId(user.getUserId());
				bo.setModifierName(user.getUserName());
				bo.setModifierTime(Calendar.getInstance());
			}

			updateDcColumnBO(needTodeleteFields);

			// 表删除记录
			this.insertDcChangeBO(needtoInsertDcChange);
		}
	}

	/**
	 * 表新增和表删除 增加
	 * 
	 * @param needToInsertFields
	 * @param bo
	 * @param type
	 * @return
	 */
	public List<DcChangeBO> addChangbo(List<DcColumnBO> needToInsertFields,
			DcTableBO bo, String type) {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		// 需要往变更表中填写的
		List<DcChangeBO> needtoInsertDcChange = new ArrayList<DcChangeBO>();
		// 往变更记录表中新增
		if (needToInsertFields.size() > 0) {
			for (DcColumnBO column : needToInsertFields) {
				DcChangeBO dcChangeBo = new DcChangeBO();
				dcChangeBo.setColumnNameCn(column.getColumnNameCn());
				dcChangeBo.setColumnNameEn(column.getColumnNameEn());
				dcChangeBo.setPkDcDataSource(bo.getPkDcDataSource());
				dcChangeBo.setTableNameCn(bo.getTableNameCn());
				dcChangeBo.setTableNameEn(bo.getTableNameEn());
				dcChangeBo.setChangeItem(type);
				dcChangeBo.setCreaterId(user.getUserId());
				dcChangeBo.setCreaterName(user.getUserName());
				dcChangeBo.setCreaterTime(Calendar.getInstance());
				dcChangeBo.setPkDcChange(UuidGenerator.getUUID());

				needtoInsertDcChange.add(dcChangeBo);
			}
		}
		return needtoInsertDcChange;
	}

	/**
	 * 字段存在时 变更记录处理
	 * 
	 * @param needToInsertFields
	 *            比较之后不同的
	 * @param nativemap
	 *            本地所保存的列
	 * @param bo
	 *            本地表
	 * @return
	 * @throws OptimusException
	 */
	public List<DcChangeBO> addChangboFields(
			List<DcColumnBO> needToInsertFields, DcTableBO bo)
			throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		// 需要往变更表中填写的
		List<DcChangeBO> needtoInsertDcChange = new ArrayList<DcChangeBO>();
		// 往变更记录表中新增
		if (needToInsertFields.size() > 0) {
			for (DcColumnBO column : needToInsertFields) {
				DcColumnBO nativeColumnBo = findDcColumnBOById(column
						.getPkDcColumn());
				List<DcChangeBO> changelist = new ArrayList<DcChangeBO>();
				if (StringUtils.isNotEmpty(column.getIsNull())) {
					DcChangeBO dcChangeBo = new DcChangeBO();
					dcChangeBo.setChangeItem(AppConstants.CHANGE_IS_NULL);
					String before = nativeColumnBo.getIsNull();
					dcChangeBo.setChangeBefore(before);
					dcChangeBo.setChangeAfter(column.getIsNull());
					changelist.add(dcChangeBo);
				}
				if (column.getColumnLength()!=null) {
					DcChangeBO dcChangeBo = new DcChangeBO();
					dcChangeBo.setChangeItem(AppConstants.CHANGE_COLUMN_LENGTH);
					BigDecimal before = nativeColumnBo.getColumnLength();
					dcChangeBo.setChangeBefore(before.toString());
					dcChangeBo.setChangeAfter(column.getColumnLength().toString());
					changelist.add(dcChangeBo);
				}
				if (StringUtils.isNotEmpty(column.getIsIndex())) {
					DcChangeBO dcChangeBo = new DcChangeBO();
					String before = nativeColumnBo.getIsIndex();
					dcChangeBo.setChangeBefore(before);
					dcChangeBo.setChangeItem(AppConstants.CHANGE_INDEX);
					dcChangeBo.setChangeAfter(column.getIsIndex());
					changelist.add(dcChangeBo);
				}
				if (StringUtils.isNotEmpty(column.getIsPrimaryKey())) {
					DcChangeBO dcChangeBo = new DcChangeBO();
					String before = nativeColumnBo.getIsPrimaryKey();
					dcChangeBo.setChangeBefore(before);
					dcChangeBo.setChangeItem(AppConstants.CHANGE_PRI_KEY);
					dcChangeBo.setChangeAfter(column.getIsPrimaryKey());
					changelist.add(dcChangeBo);
				}
				if (StringUtils.isNotEmpty(column.getColumnType())) {
					DcChangeBO dcChangeBo = new DcChangeBO();
					String before = nativeColumnBo.getColumnType();
					dcChangeBo.setChangeBefore(before);
					dcChangeBo.setChangeItem(AppConstants.CHANGE_COLUMN_TYPE);
					dcChangeBo.setChangeAfter(column.getColumnType());
					changelist.add(dcChangeBo);
				}
				if (StringUtils.isNotEmpty(column.getColumnNameCn())) {
					DcChangeBO dcChangeBo = new DcChangeBO();
					String before = nativeColumnBo.getColumnNameCn();
					dcChangeBo.setChangeBefore(before);
					dcChangeBo.setChangeItem(AppConstants.CHANGE_NAME_CN);
					dcChangeBo.setChangeAfter(column.getColumnNameCn());
					changelist.add(dcChangeBo);
				}
				
				//以下为置为空
				Set<String> set = column.getNullProps();
				if(set.contains("columnNameCn")){
					DcChangeBO dcChangeBo = new DcChangeBO();
					String before = nativeColumnBo.getColumnNameCn();
					dcChangeBo.setChangeBefore(before);
					dcChangeBo.setChangeItem(AppConstants.CHANGE_NAME_CN);
					dcChangeBo.setChangeAfter(column.getColumnNameCn());
					changelist.add(dcChangeBo);
				}
				if(set.contains("columnType")){
					DcChangeBO dcChangeBo = new DcChangeBO();
					String before = nativeColumnBo.getColumnType();
					dcChangeBo.setChangeBefore(before);
					dcChangeBo.setChangeItem(AppConstants.CHANGE_COLUMN_TYPE);
					dcChangeBo.setChangeAfter(column.getColumnType());
					changelist.add(dcChangeBo);
				}
				if(set.contains("columnLength")){
					DcChangeBO dcChangeBo = new DcChangeBO();
					dcChangeBo.setChangeItem(AppConstants.CHANGE_COLUMN_LENGTH);
					BigDecimal before = nativeColumnBo.getColumnLength();
					dcChangeBo.setChangeBefore(before.toString());
					dcChangeBo.setChangeAfter(column.getColumnLength().toString());
					changelist.add(dcChangeBo);
				}
				
				if (changelist.size() > 0) {
					for (DcChangeBO dcChangeBo : changelist) {

						if (StringUtils.isNotEmpty(column.getColumnNameCn())) {
							dcChangeBo
									.setColumnNameCn(column.getColumnNameCn());
						} else {
							dcChangeBo.setColumnNameCn(nativeColumnBo
									.getColumnNameCn());
						}
						dcChangeBo.setColumnNameEn(nativeColumnBo
								.getColumnNameEn());
						dcChangeBo.setPkDcDataSource(bo.getPkDcDataSource());
						dcChangeBo.setTableNameCn(bo.getTableNameCn());
						dcChangeBo.setTableNameEn(bo.getTableNameEn());
						dcChangeBo.setCreaterId(user.getUserId());
						dcChangeBo.setCreaterName(user.getUserName());
						dcChangeBo.setCreaterTime(Calendar.getInstance());
						dcChangeBo.setPkDcChange(UuidGenerator.getUUID());
					}

				}

				

				needtoInsertDcChange.addAll(changelist);

			}
		}
		return needtoInsertDcChange;
	}

	/**
	 * 更具主键查询DcColumnBO
	 * 
	 * @param pkCodeTableManager
	 * @return
	 * @throws OptimusException
	 */
	public DcColumnBO findDcColumnBOById(String pkCodeTableManager)
			throws OptimusException {

		IPersistenceDAO dao = getPersistenceDAO();

		// 封装结果集
		return dao.queryByKey(DcColumnBO.class, pkCodeTableManager);
	}

	/**
	 * 增加前对表进行处理
	 * 
	 * @param needToInsert
	 *            远程获取的BO
	 * @return
	 * @throws OptimusException
	 */
	public void doNeedToInsertTableDual(List<DcTableBO> needToInsert,
			DcDataSourceBO smser) throws OptimusException {
		for(DcTableBO b:needToInsert){
			b.setLastRecordCount(b.getFirstRecordCount());
		}
		
		if (needToInsert.size() > 0) {
			insertDcTableBO(needToInsert);
			// 新增的 消息推送 。。。。
			doNeedToInsertFieldsFromTable(needToInsert, smser);
		}

	}

	/**
	 * 需要新增的字段 没有写字段信息变更---字段信息变更 需要在这处理
	 * 
	 * @param needToInsert
	 * @param smser
	 * @throws OptimusException
	 */
	public void doNeedToInsertFieldsFromTable(List<DcTableBO> needToInsert,
			DcDataSourceBO smser) throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		// 需要往变更表中填写的
		List<DcChangeBO> needtoInsertDcChange = new ArrayList<DcChangeBO>();

		// 需要增加的字段
		List<DcColumnBO> needToInsertFields = new ArrayList<DcColumnBO>();
		if (needToInsert.size() > 0) {
			// 需要增加的字段

			for (DcTableBO bo : needToInsert) {

				List<DcColumnBO> needFields = new ArrayList<DcColumnBO>();
				needFields = findTableColumn(smser, bo);
				// 设置索引列。。。
				needToInsertFields.addAll(setKeyIndex(smser, bo, needFields));

				// 往变更记录表中新增
				needtoInsertDcChange.addAll(this.addChangbo(needToInsertFields,
						bo, AppConstants.CHANGE_TABLE_ADD));

			}
			if (needToInsertFields.size() > 0) {
				for (DcColumnBO bo : needToInsertFields) {
					bo.setCreaterId(user.getUserId());
					bo.setCreaterName(user.getUserName());
					bo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
					bo.setCreaterTime(Calendar.getInstance());
					bo.setPkDcDataSource(smser.getPkDcDataSource());
				}
				insertDcColumnBO(needToInsertFields);
			}

			if (needtoInsertDcChange.size() > 0) {
				insertDcChangeBO(needtoInsertDcChange);
			}

		}

	}

	/**
	 * 表变更记录增加
	 * 
	 * @param needToInsertFields
	 * @throws OptimusException
	 */
	public void insertDcChangeBO(List<DcChangeBO> dcChangeBOList)
			throws OptimusException {
		if (dcChangeBOList.size() > 0) {
			IPersistenceDAO dao = getPersistenceDAO();
			dao.insert(dcChangeBOList);
		}
	}

	/**
	 * 删除表变更情况 当删除数据源时执行
	 * 
	 * @param datasource
	 * @throws OptimusException
	 */
	public void deleteDcChangeBO(DcDataSourceBO datasource)
			throws OptimusException {

		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "delete from DC_CHANGE t where t.PK_DC_DATA_SOURCE=?";
		List<String> str = new ArrayList<String>();
		str.add(datasource.getPkDcDataSource());
		dao.execute(sql, str);

	}

	/**
	 * 
	 * @param needToUpdate
	 *            远程获取的bo
	 * @param smser
	 * @throws OptimusException
	 */
	public void doNeedToUpdateTableDual(List<DcTableBO> needToUpdate,
			DcDataSourceBO smser) throws OptimusException {
		// 从缓存中获取当前登录用户信息
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		// 修改项目：
		List<DcTableBO> needToUpdateChange = new ArrayList<DcTableBO>();
		if (needToUpdate.size() > 0) {
			// 获取当前系统的表BO
			List<DcTableBO> nativeTableBO = findNativeTableBo(smser);

			// 把当前系统转所存储的远程BO转为Map
			Map<String, DcTableBO> nativeTableMap = TablesListToMap(nativeTableBO);
			for (DcTableBO bo : needToUpdate) {
				DcTableBO add = new DcTableBO();
				DcTableBO nativebo = nativeTableMap.get(bo.getTableNameEn());

				// 需要更新的项目compareBO（.clss,需要改变的，最新的）返回为两者不同的BO

				Map<String, Object> mapboAndSet = this
						.compareBO(DcTableBO.class, nativebo, bo,
								this.getTablesMapColum());
				add = (DcTableBO) mapboAndSet.get("bo");
				Set<String> set = (Set<String>) mapboAndSet.get("set");


				add.setNullProps(set);

				add.setPkDcTable(nativebo.getPkDcTable());
				add.setModifierId(user.getUserId());
				add.setModifierTime(Calendar.getInstance());
				add.setModifierName(user.getUserId());
				add.setTableNameEn(bo.getTableNameEn());
				add.setFirstRecordCount(nativebo.getFirstRecordCount());
				add.setLastRecordCount(bo.getFirstRecordCount());
				needToUpdateChange.add(add);

			}
		}
		updateNativeTable(needToUpdateChange);
		doCompareFields(needToUpdateChange, smser);

	}

	/**
	 * 比较字段
	 * 
	 * @param needToUpdate
	 * @param smser
	 * @throws OptimusException
	 */
	public void doCompareFields(List<DcTableBO> needToUpdateChange,
			DcDataSourceBO smser) throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		// 需要往变更表中填写的
		List<DcChangeBO> needtoInsertDcChange = new ArrayList<DcChangeBO>();

		// 需要增加的字段
		List<DcColumnBO> needToInsertFields = new ArrayList<DcColumnBO>();
		// 需要修改的字段
		List<DcColumnBO> needToUpdateFields = new ArrayList<DcColumnBO>();
		// 需要删除的字段
		List<DcColumnBO> needToDeleteFields = new ArrayList<DcColumnBO>();

		// 获取当前系统的表BO
		List<DcTableBO> nativeTableBO = findNativeTableBo(smser);

		// 把当前系统转所存储的远程BO转为Map
		Map<String, DcTableBO> nativeTableMap = TablesListToMap(nativeTableBO);

		if (needToUpdateChange.size() > 0) {
			for (DcTableBO bo : needToUpdateChange) {
				// 需要增加的字段
				List<DcColumnBO> OneneedToInsertFields = new ArrayList<DcColumnBO>();
				// 需要修改的字段
				List<DcColumnBO> OneneedToUpdateFields = new ArrayList<DcColumnBO>();
				// 需要删除的字段
				List<DcColumnBO> OneneedToDeleteFields = new ArrayList<DcColumnBO>();

				List<DcColumnBO> needField = new ArrayList<DcColumnBO>();

				// 获取远程数据库字段
				needField = findTableColumn(smser, bo);

				// 远程数据库字段 主键以及索引判断
				List<DcColumnBO> needFields = setKeyIndex(smser, bo, needField);

				// 本地数据库中保存的远程字段BO
				List<DcColumnBO> nativeFieldsBO = findNativeFieldsBO(bo);

				// 本地数据库中保存字段BO转为Map<String ,BO>
				Map<String, DcColumnBO> map = FieldsListToMap(nativeFieldsBO);
				for (DcColumnBO fields : needFields) {
					DcColumnBO nativebo = map.get(fields.getColumnNameEn());
					if (nativebo == null) {
						// 新增字段
						fields.setPkDcDataSource(smser.getPkDcDataSource());
						fields.setPkDcTable(bo.getPkDcTable());
						fields.setCreaterId(user.getUserId());
						fields.setCreaterName(user.getUserName());
						fields.setCreaterTime(Calendar.getInstance());
						fields.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
						needToInsertFields.add(fields);

						// 新增
						OneneedToInsertFields.add(fields);

					} else {
						// 修改字段
						DcColumnBO changeFields = new DcColumnBO();

						// 比较并返回 新的bo所不同的值

						Map<String, Object> mapboAndSet = this.compareBO(
								DcColumnBO.class, nativebo, fields,
								this.getFiledsMapColum());
						changeFields = (DcColumnBO) mapboAndSet.get("bo");
						Set<String> set = (Set<String>) mapboAndSet.get("set");
						changeFields.setNullProps(set);
						System.out.println();
						System.out.println(changeFields.getColumnLength());
						changeFields.setPkDcColumn(nativebo.getPkDcColumn());

						changeFields.setModifierId(user.getUserId());
						changeFields.setModifierName(user.getUserName());
						changeFields.setModifierTime(Calendar.getInstance());
						needToUpdateFields.add(changeFields);

						OneneedToUpdateFields.add(changeFields);

					}

				}

				Map<String, DcColumnBO> mapold = FieldsListToMap(needFields);
				for (DcColumnBO nat : nativeFieldsBO) {
					// 删除本地多余的字段
					DcColumnBO longbo = mapold.get(nat.getColumnNameEn());
					if (longbo == null) {
						nat.setEffectiveMarker(AppConstants.EFFECTIVE_N);
						nat.setModifierId(user.getUserId());
						nat.setModifierName(user.getUserName());
						nat.setModifierTime(Calendar.getInstance());
						needToDeleteFields.add(nat);
						OneneedToDeleteFields.add(nat);
					}
				}

				needtoInsertDcChange.addAll(this.addChangbo(
						OneneedToInsertFields,
						nativeTableMap.get(bo.getTableNameEn()), "字段新增"));
				needtoInsertDcChange.addAll(this.addChangbo(
						OneneedToDeleteFields,
						nativeTableMap.get(bo.getTableNameEn()), "字段删除"));
				// 字段修改
				needtoInsertDcChange.addAll(this.addChangboFields(
						OneneedToUpdateFields,
						nativeTableMap.get(bo.getTableNameEn())));
			}
			insertDcColumnBO(needToInsertFields);
			updateDcColumnBO(needToUpdateFields);
			updateDcColumnBO(needToDeleteFields);
			if (needtoInsertDcChange.size() > 0) {
				this.insertDcChangeBO(needtoInsertDcChange);
			}
		}
	}

	/**
	 * 字段变为Map
	 * 
	 * @param tableBO
	 * @return
	 */
	public Map<String, DcColumnBO> FieldsListToMap(List<DcColumnBO> tableBO) {
		Map<String, DcColumnBO> map = new HashMap<String, DcColumnBO>();
		if (tableBO.size() > 0) {
			for (DcColumnBO bo : tableBO) {
				map.put(bo.getColumnNameEn(), bo);
			}
			return map;
		} else {
			return map;
		}

	}

	/**
	 * 更改本地表BO
	 */
	public void updateNativeTable(List<DcTableBO> needToUpdateChange)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		if (needToUpdateChange.size() > 0) {
			for (DcTableBO bo : needToUpdateChange) {

				dao.update(bo);
			}
		}

	}

	/**
	 * 表BO转为Map 返回为 <表名，BO>
	 * 
	 * @param tableBO
	 * @return
	 */
	public Map<String, DcTableBO> TablesListToMap(List<DcTableBO> tableBO) {
		Map<String, DcTableBO> map = new HashMap<String, DcTableBO>();
		if (tableBO.size() > 0) {
			for (DcTableBO bo : tableBO) {
				map.put(bo.getTableNameEn(), bo);
			}
			return map;
		} else {
			return map;
		}

	}

	/**
	 * 获取本地所存储的远程表BO
	 * 
	 * @return
	 * @throws OptimusException
	 */
	public List<DcTableBO> findNativeTableBo(DcDataSourceBO smser)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		sql.append(" select * from DC_TABLE t where t.pk_dc_data_source= ? And t.effective_marker= ? ");
		str.add(smser.getPkDcDataSource());
		str.add(AppConstants.EFFECTIVE_Y);
		List<DcTableBO> l1 = dao.queryForList(DcTableBO.class, sql.toString(),
				str);
		return l1;

	}

	/**
	 * 获取本地所保存的远程视图
	 * 
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public List<DcViewBO> findNativeViews(DcDataSourceBO smser)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		sql.append(" select * from DC_VIEW t where t.pk_dc_data_source= ? And t.effective_marker= ? ");
		str.add(smser.getPkDcDataSource());
		str.add(AppConstants.EFFECTIVE_Y);
		List<DcViewBO> l1 = dao.queryForList(DcViewBO.class, sql.toString(),
				str);
		return l1;

	}

	/**
	 * 获取本地保存的远程存储过程
	 * 
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public List<DcProcedureBO> findNativeProcedure(DcDataSourceBO smser)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		sql.append(" select * from DC_PROCEDURE t where t.pk_dc_data_source= ? And t.effective_marker= ? ");
		str.add(smser.getPkDcDataSource());
		str.add(AppConstants.EFFECTIVE_Y);
		List<DcProcedureBO> l1 = dao.queryForList(DcProcedureBO.class,
				sql.toString(), str);
		return l1;

	}

	public List<DcTriggerBO> findNativeTrigger(DcDataSourceBO smser)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		sql.append(" select * from DC_TRIGGER t where t.pk_dc_data_source= ? And t.effective_marker= ? ");
		str.add(smser.getPkDcDataSource());
		str.add(AppConstants.EFFECTIVE_Y);
		List<DcTriggerBO> l1 = dao.queryForList(DcTriggerBO.class,
				sql.toString(), str);
		return l1;

	}

	/**
	 * 获取本地所存储的远程字段
	 * 
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public List<DcColumnBO> findNativeFieldsBO(DcTableBO bo)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		sql.append(" select * from dc_column t where t.effective_marker= ? and t.pk_dc_table= ? ");
		str.add(AppConstants.EFFECTIVE_Y);
		str.add(bo.getPkDcTable());
		List<DcColumnBO> l1 = dao.queryForList(DcColumnBO.class,
				sql.toString(), str);
		return l1;
	}

	/**
	 * 处理主键和索引等
	 * 
	 * @param smser
	 * @param table
	 * @param needFields
	 * @return
	 * @throws OptimusException
	 */
	public List<DcColumnBO> setKeyIndex(DcDataSourceBO smser, DcTableBO table,
			List<DcColumnBO> needFields) throws OptimusException {
		List<String> indexList = findIndexInfo(smser, table);
		// 获取表中主键
		List<String> primaryList = findPrimaryKeysInfo(smser, table);

		for (DcColumnBO co : needFields) {

			// 设置为为那个表
			co.setPkDcTable(table.getPkDcTable());
			// 是否索引
			if (indexList.contains(co.getColumnNameEn())) {
				co.setIsIndex(AppConstants.IS_INDEX_Y);
			} else {
				co.setIsIndex(AppConstants.IS_INDEX_N);
			}

			// 是否为主键
			if (primaryList.contains(co.getColumnNameEn())) {
				co.setIsPrimaryKey(AppConstants.IS_PRIMARY_KEY_Y);
			} else {
				co.setIsPrimaryKey(AppConstants.IS_PRIMARY_KEY_N);
			}

		}
		return needFields;

	}

	/**
	 * 新增字段
	 * 
	 * @param needToInsertTables
	 * @throws OptimusException
	 */
	public void insertDcColumnBO(List<DcColumnBO> needToInsertFields)
			throws OptimusException {
		if (needToInsertFields.size() > 0) {
			IPersistenceDAO dao = getPersistenceDAO();
			dao.insert(needToInsertFields);
		}
	}

	/**
	 * 更新列
	 * 
	 * @param needToUpdateFields
	 * @throws OptimusException
	 */
	public void updateDcColumnBO(List<DcColumnBO> needToUpdateFields)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		if (needToUpdateFields.size() > 0) {
			for (DcColumnBO bo : needToUpdateFields) {
/*				System.out.println("changdu");
				System.out.println(bo.getColumnLength());*/
				dao.update(bo);
			}

		}
	}

	/**
	 * 新增表
	 * 
	 * @param needToInsertTables
	 * @throws OptimusException
	 */
	public void insertDcTableBO(List<DcTableBO> needToInsertTables)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(needToInsertTables);
	}

	/**
	 * 保存 视图
	 * 
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public boolean addViews(DcDataSourceBO smser) throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		// 获取远程数据库的所有视图
		List<DcViewBO> l1 = findViews(smser);

		// 应该在这里加对比处理过程
		doCompareViews(l1, smser);

		/*
		 * //处理视图 for(DcViewBO bo:l1){ bo.setCreaterId(user.getUserId());
		 * bo.setCreaterName(user.getUserName());
		 * bo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
		 * bo.setCreaterTime(Calendar.getInstance());
		 * bo.setPkDcDataSource(smser.getPkDcDataSource()); } if(l1.size()>0){
		 * insertViews(l1); }
		 */

		return true;
	}

	/**
	 * 比较views
	 * 
	 * @param newViews
	 *            远程视图
	 * @param smser
	 *            数据源
	 * @throws OptimusException
	 */
	public void doCompareViews(List<DcViewBO> newViews, DcDataSourceBO smser)
			throws OptimusException {
		// 从缓存中获取当前登录用户信息
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		List<DcViewBO> nativeListBO = this.findNativeViews(smser);

		// 把当前本地所保存远程的视图转换为Map
		Map<String, DcViewBO> nativeViewsMap = MapsListToMap(nativeListBO);

		// 所需要修改的BO
		List<DcViewBO> needToUpdate = new ArrayList<DcViewBO>();

		// 所需要增加的BO
		List<DcViewBO> needToInsert = new ArrayList<DcViewBO>();

		// 需要删除的
		List<DcViewBO> needToDelete = new ArrayList<DcViewBO>();

		for (DcViewBO bo : newViews) {
			DcViewBO nativeBO = nativeViewsMap.get(bo.getViewName());
			if (nativeBO == null) {
				// 新增的
				needToInsert.add(bo);
			} else {
				// 修改的
				Map<String, Object> mapboAndSet = this.compareBO(
						DcProcedureBO.class, nativeBO, bo,
						this.getProMapColum());
				DcViewBO changeviews = (DcViewBO) mapboAndSet.get("bo");
				Set<String> set = (Set<String>) mapboAndSet.get("set");
				changeviews.setNullProps(set);

				changeviews.setPkDcView(nativeBO.getPkDcView());
				changeviews.setModifierId(user.getUserId());
				changeviews.setModifierName(user.getUserName());
				changeviews.setModifierTime(Calendar.getInstance());
				needToUpdate.add(changeviews);
			}
		}
		if (needToInsert.size() > 0) {
			for (DcViewBO bo : needToInsert) {
				// 新增的
				bo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
				bo.setPkDcDataSource(smser.getPkDcDataSource());
				bo.setCreaterId(user.getUserId());
				bo.setCreaterName(user.getUserName());
				bo.setCreaterTime(Calendar.getInstance());
				bo.setPkDcDataSource(smser.getPkDcDataSource());

			}
		}

		// 新的视图
		Map<String, DcViewBO> newViewsMap = MapsListToMap(newViews);
		for (DcViewBO bo : nativeListBO) {
			DcViewBO newbo = newViewsMap.get(bo.getViewName());
			if (newbo == null) {
				// 删除的
				needToDelete.add(bo);
			}
		}
		if (needToDelete.size() > 0) {
			for (DcViewBO bo : needToDelete) {
				bo.setModifierId(user.getUserId());
				bo.setModifierName(user.getUserName());
				bo.setModifierTime(Calendar.getInstance());
				bo.setEffectiveMarker(AppConstants.EFFECTIVE_N);
			}
		}

		needToDelete.addAll(needToUpdate);
		if (needToDelete.size() > 0) {
			this.updateNativeViews(needToDelete);
		}

		if (needToInsert.size() > 0) {
			this.insertViews(needToInsert);
		}

	}

	/**
	 * 视图转换为Map
	 * 
	 * @param tableBO
	 * @return
	 */
	public Map<String, DcViewBO> MapsListToMap(List<DcViewBO> tableBO) {
		Map<String, DcViewBO> map = new HashMap<String, DcViewBO>();
		if (tableBO.size() > 0) {
			for (DcViewBO bo : tableBO) {
				map.put(bo.getViewName(), bo);
			}
			return map;
		} else {
			return map;
		}

	}

	/**
	 * 保存视图
	 * 
	 * @param needToInsertTables
	 * @throws OptimusException
	 */
	public void insertViews(List<DcViewBO> views) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(views);
	}

	/**
	 * 获取视图
	 * 
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public List<DcViewBO> findViews(DcDataSourceBO smser)
			throws OptimusException {
		String dbtype= smser.getDbType();
		if(StringUtils.equalsIgnoreCase(dbtype, "Oracle")){
			return dataStructLoadOracle.findViews(smser);
		}else{
			return null;
		}
	}

	/**
	 * 保存触发器
	 * 
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public boolean insertTrigger(DcDataSourceBO smser) throws OptimusException {

		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		// 获取远程数据库的触发器
		List<DcTriggerBO> l1 = findTrigger(smser);

		List<String> st1 = new ArrayList<String>();

		doCompareTrigger(l1, smser);
		/*
		 * st1=null; if(l1.size()>0){ IPersistenceDAO dao = getPersistenceDAO();
		 * dao.insert(l1); }else{
		 * 
		 * }
		 */

		return true;
	}

	/**
	 * 触发器比较
	 * 
	 * @param Procedures
	 * @param smser
	 * @throws OptimusException
	 */
	public void doCompareTrigger(List<DcTriggerBO> Procedures,
			DcDataSourceBO smser) throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		// 应该在这里加对比
		List<DcTriggerBO> nativeBOList = this.findNativeTrigger(smser);

		List<DcTriggerBO> needtoUpdate = new ArrayList<DcTriggerBO>();
		List<DcTriggerBO> needtoInsert = new ArrayList<DcTriggerBO>();
		List<DcTriggerBO> needtoDelete = new ArrayList<DcTriggerBO>();

		Map<String, DcTriggerBO> nativeMap = TriggerListToMap(nativeBOList);
		for (DcTriggerBO bo : Procedures) {
			DcTriggerBO nativePro = nativeMap.get(bo.getTriggerName());
			if (nativePro == null) {
				bo.setCreaterId(user.getUserId());
				bo.setCreaterName(user.getUserName());
				bo.setCreaterTime(Calendar.getInstance());
				bo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
				bo.setPkDcDataSource(smser.getPkDcDataSource());
				needtoInsert.add(bo);
			} else {
				Map<String, Object> mapboAndSet = this.compareBO(
						DcProcedureBO.class, nativePro, bo,
						this.getTriMapColum());
				DcTriggerBO changebo = (DcTriggerBO) mapboAndSet.get("bo");
				Set<String> set = (Set<String>) mapboAndSet.get("set");
				changebo.setNullProps(set);
				changebo.setPkDcTrigger(nativePro.getPkDcTrigger());
				changebo.setModifierId(user.getUserId());
				changebo.setModifierName(user.getUserName());
				changebo.setModifierTime(Calendar.getInstance());
				needtoUpdate.add(changebo);
			}
		}

		Map<String, DcTriggerBO> newMap = TriggerListToMap(Procedures);
		for (DcTriggerBO changebo : nativeBOList) {
			DcTriggerBO nativePro = newMap.get(changebo.getTriggerName());
			if (nativePro == null) {
				changebo.setModifierId(user.getUserId());
				changebo.setModifierName(user.getUserName());
				changebo.setModifierTime(Calendar.getInstance());
				changebo.setEffectiveMarker(AppConstants.EFFECTIVE_N);
				needtoDelete.add(changebo);
			}
		}
		needtoDelete.addAll(needtoUpdate);
		if (needtoDelete.size() > 0) {
			this.updateNativeTriggers(needtoDelete);
			// this.updateNativeProcedure(needtoDelete);
		}

		if (needtoInsert.size() > 0) {
			insertNativeTrigger(needtoInsert);
		}

	}

	/**
	 * 新增触发器
	 * 
	 * @param needToInsertFields
	 * @throws OptimusException
	 */
	public void insertNativeTrigger(List<DcTriggerBO> needToInsertFields)
			throws OptimusException {
		if (needToInsertFields.size() > 0) {
			IPersistenceDAO dao = getPersistenceDAO();
			dao.insert(needToInsertFields);
		}
	}

	/**
	 * 触发器 bo转map<string,bo>
	 * 
	 * @param tableBO
	 * @return
	 */
	public Map<String, DcTriggerBO> TriggerListToMap(List<DcTriggerBO> tableBO) {
		Map<String, DcTriggerBO> map = new HashMap<String, DcTriggerBO>();
		if (tableBO.size() > 0) {
			for (DcTriggerBO bo : tableBO) {
				map.put(bo.getTriggerName(), bo);
			}
			return map;
		} else {
			return map;
		}

	}

	/**
	 * 查找触发器
	 * 
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public List<DcTriggerBO> findTrigger(DcDataSourceBO smser)
			throws OptimusException {
		doSetDataSource(smser);
		IPersistenceDAO dao = getPersistenceDAO(smser.getPkDcDataSource());
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>(); // ？传值
		sql.append("SELECT * FROM USER_SOURCE WHERE TYPE= ? order by name,line");
		String s1 = "TRIGGER";
		str.add(s1);
		List<Map> maplist = dao.queryForList(sql.toString(), str);
		List<DcTriggerBO> l1 = new ArrayList<DcTriggerBO>();

		List<String> st1 = new ArrayList<String>();
		Map<String, DcTriggerBO> d1 = new HashMap<String, DcTriggerBO>();

		for (Map map : maplist) {
			if (st1.contains(map.get("name").toString())) {
				DcTriggerBO bo = d1.get(map.get("name"));

				// 留下脚本位置
				// String proSql= bo.getProcSql()+"  "+map.get("text");
				// bo.setProcSql(proSql);
			} else {
				st1.add((String) map.get("name"));
				DcTriggerBO bo = new DcTriggerBO();
				bo.setTriggerName((String) map.get("name"));
				// bo.setProcSql(map.get("text").toString());
				d1.put((String) map.get("name"), bo);
			}
		}
		for (String key : d1.keySet()) {
			l1.add(d1.get(key));
		}
		return l1;

	}

	/**
	 * 增加存储过程
	 * 
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public boolean insertProcedure(DcDataSourceBO smser)
			throws OptimusException {
		// 查找触发器
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		// 获取所有视图
		List<DcProcedureBO> l1 = findProcedure(smser);

		List<String> st1 = new ArrayList<String>();

		/*
		 * //新的 List<DcProcedureBO> newli = new ArrayList<DcProcedureBO>();
		 * 
		 * 
		 * //去除重复结果 并添加创建人等信息 for(DcProcedureBO bo:l1){
		 * if(st1.contains(bo.getProcName())){
		 * 
		 * }else{ st1.add(bo.getProcName());
		 * 
		 * bo.setCreaterId(user.getUserId());
		 * bo.setCreaterName(user.getUserName());
		 * bo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
		 * bo.setCreaterTime(Calendar.getInstance());
		 * bo.setPkDcDataSource(smser.getPkDcDataSource()); newli.add(bo); } }
		 */
		this.doCompareProcedure(l1, smser);

		/*
		 * st1=null; if(l1.size()>0){ IPersistenceDAO dao = getPersistenceDAO();
		 * dao.insert(newli); }else{
		 * 
		 * }
		 */

		return true;
	}

	/**
	 * 比较存储过程 并保存更新
	 * 
	 * @param Procedures
	 *            远程的存储过程
	 * @param smser
	 *            数据源
	 * @throws OptimusException
	 */
	public void doCompareProcedure(List<DcProcedureBO> Procedures,
			DcDataSourceBO smser) throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		// 应该在这里加对比
		List<DcProcedureBO> nativeBOList = this.findNativeProcedure(smser);

		List<DcProcedureBO> needtoUpdate = new ArrayList<DcProcedureBO>();
		List<DcProcedureBO> needtoInsert = new ArrayList<DcProcedureBO>();
		List<DcProcedureBO> needtoDelete = new ArrayList<DcProcedureBO>();

		Map<String, DcProcedureBO> nativeMap = ProcedureListToMap(nativeBOList);
		for (DcProcedureBO bo : Procedures) {
			DcProcedureBO nativePro = nativeMap.get(bo.getProcName());
			if (nativePro == null) {
				bo.setCreaterId(user.getUserId());
				bo.setCreaterName(user.getUserName());
				bo.setCreaterTime(Calendar.getInstance());
				bo.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
				bo.setPkDcDataSource(smser.getPkDcDataSource());
				needtoInsert.add(bo);
			} else {

				Map<String, Object> mapboAndSet = this.compareBO(
						DcProcedureBO.class, nativePro, bo,
						this.getProMapColum());
				DcProcedureBO changebo = (DcProcedureBO) mapboAndSet.get("bo");
				Set<String> set = (Set<String>) mapboAndSet.get("set");
				changebo.setNullProps(set);

				changebo.setPkDcProcedure(nativePro.getPkDcProcedure());
				changebo.setModifierId(user.getUserId());
				changebo.setModifierName(user.getUserName());
				changebo.setModifierTime(Calendar.getInstance());
				needtoUpdate.add(changebo);
			}
		}

		Map<String, DcProcedureBO> newMap = ProcedureListToMap(Procedures);
		for (DcProcedureBO changebo : nativeBOList) {
			DcProcedureBO nativePro = newMap.get(changebo.getProcName());
			if (nativePro == null) {
				changebo.setModifierId(user.getUserId());
				changebo.setModifierName(user.getUserName());
				changebo.setModifierTime(Calendar.getInstance());
				changebo.setEffectiveMarker(AppConstants.EFFECTIVE_N);
				needtoDelete.add(changebo);
			}
		}
		needtoDelete.addAll(needtoUpdate);
		if (needtoDelete.size() > 0) {
			this.updateNativeProcedure(needtoDelete);
		}

		if (needtoInsert.size() > 0) {
			insertNativeProcedure(needtoInsert);
		}

	}

	/**
	 * 新增存储过程
	 * 
	 * @param needToInsertFields
	 * @throws OptimusException
	 */
	public void insertNativeProcedure(List<DcProcedureBO> needToInsertFields)
			throws OptimusException {
		if (needToInsertFields.size() > 0) {
			IPersistenceDAO dao = getPersistenceDAO();
			dao.insert(needToInsertFields);
		}
	}

	/**
	 * 存过过程LIst转为Map
	 * 
	 * @param tableBO
	 * @return
	 */
	public Map<String, DcProcedureBO> ProcedureListToMap(
			List<DcProcedureBO> tableBO) {
		Map<String, DcProcedureBO> map = new HashMap<String, DcProcedureBO>();
		if (tableBO.size() > 0) {
			for (DcProcedureBO bo : tableBO) {
				map.put(bo.getProcName(), bo);
			}
			return map;
		} else {
			return map;
		}

	}

	/**
	 * 远程数据源的 存储过程
	 * 
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public List<DcProcedureBO> findProcedure(DcDataSourceBO smser)
			throws OptimusException {
		String dbtype= smser.getDbType();
		if(StringUtils.equalsIgnoreCase(dbtype, "Oracle")){
			return dataStructLoadOracle.findProcedure(smser);
		}else{
			return null;
		}

	}

	/**
	 * 加载全部
	 * 
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public Map<String,Boolean> addAll(DcDataSourceBO smser) throws OptimusException {



		Map<String,Boolean> status = new HashMap<String,Boolean>();
		
		//table数
		List<String> tablenames = new ArrayList<String>();	
		List<Map<String,Object>> alltables = findCurrUserTable(smser);
		for(Map map1: alltables){
			tablenames.add(map1.get("id").toString());
		} 
		//加载所有表和字段
		boolean saveTableAnd =	addTableAndFileds(tablenames,smser);
		status.put("table",saveTableAnd);
		
		//加载存储过程
		boolean   procedure= insertProcedure(smser);
		status.put("procedure", procedure);
		
		//触发器
		boolean trigger =insertTrigger(smser);
		status.put("trigger", trigger);

		//加载视图
		boolean view =addViews(smser);
		status.put("view", view);
		
		return status;
	}

	/**
	 * 两个相同类型的BO类进行比较 不同的则返回第二个参数
	 * 
	 * @param oldClass
	 *            类型
	 * @param oldbo
	 *            旧的
	 * @param newbo
	 *            新的
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IntrospectionException
	 */
	public Map<String, Object> compareBO(Class oldClass, Object oldbo,
			Object newbo, List<String> l1) {
		Map<String, Object> map = new HashMap<String, Object>();
		Class tClass = oldbo.getClass();
		Object bo = null;
		Set<String> set = new HashSet<String>();
		try {

			bo = tClass.newInstance();
			Field[] fields = tClass.getDeclaredFields();
			for (Field field : fields) {
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
						tClass);
				Method method = pd.getWriteMethod();
				Method get = pd.getReadMethod();
				Object getValue = get.invoke(oldbo, new Object[] {});
				System.out.println();
				Object newValue = get.invoke(newbo, new Object[] {});
				Boolean eq = true;
				if (newValue != null) { // 只有新的值存在并且不相同的时候才改变
					eq = isEqual(field.getType(), getValue, newValue);
					if (!eq) {
						method.invoke(bo, newValue);
					}
				}

				if (getValue != null && newValue == null) {
					set.add(field.getName());
					Field field1 = newbo.getClass().getDeclaredField(
							field.getName());
					field1.setAccessible(true);
					field1.set(newbo, null);
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Set<String> set1 = new HashSet<String>();
		for (String s : set) {
			if (l1.contains(s)) {
				set1.add(s);
			}
		}

		map.put("bo", bo);
		map.put("set", set1);

		return map;
	}

	/**
	 * 判断是否相同
	 * 
	 * @param typeClass
	 * @param old
	 * @param newone
	 * @return 相同 返回True
	 */
	private Boolean isEqual(Class<?> typeClass, Object old, Object newone) {
		if (old == null) {
			return false;
		}
		if (typeClass == int.class || typeClass == Integer.class) {

			return ((Integer) old == (Integer) newone);

		}else if(typeClass==BigDecimal.class){
		    BigDecimal ret1 = null;
		    ret1 = (BigDecimal) old;
		    BigDecimal ret2 = null;
		    ret2 = (BigDecimal) newone;
			int i=ret1.compareTo(ret2);
			if(i==0){
				return true;
			}else{
				return false;
			}
	

		}else if (typeClass == short.class || typeClass == Short.class) {
			return (Short) old == (Short) newone;
		} else if (typeClass == byte.class || typeClass == Byte.class) {

			return (Byte) old == (Byte) newone;
		} else if (typeClass == double.class || typeClass == Double.class) {
			return (Double) old == (Double) newone;
		} else if (typeClass == boolean.class || typeClass == Boolean.class) {
			return (Boolean) old == (Boolean) newone;
		} else if (typeClass == float.class || typeClass == Float.class) {
			return (Float) old == (Float) newone;
		} else if (typeClass == long.class || typeClass == Long.class) {
			return (Long) old == (Long) newone;
		} else if (typeClass == String.class) {
			String a = (String) old;
			String b = (String) newone;
			return a.equals(b);
		} else if (typeClass == Calendar.class) {
			Calendar a = (Calendar) old;
			Calendar b = (Calendar) newone;
			if (a.compareTo(b) == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * 更改视图
	 * 
	 * @param views
	 * @throws OptimusException
	 */
	public void updateNativeViews(List<DcViewBO> views) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		if (views.size() > 0) {
			for (DcViewBO bo : views) {
				dao.update(bo);
			}
		}

	}

	/**
	 * 更改存储过程
	 * 
	 * @param procedurceList
	 * @throws OptimusException
	 */
	public void updateNativeProcedure(List<DcProcedureBO> procedurceList)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		if (procedurceList.size() > 0) {
			for (DcProcedureBO bo : procedurceList) {
				dao.update(bo);
			}
		}
	}

	/**
	 * 更改触发器
	 * 
	 * @param procedurceList
	 * @throws OptimusException
	 */
	public void updateNativeTriggers(List<DcTriggerBO> procedurceList)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		if (procedurceList.size() > 0) {
			for (DcTriggerBO bo : procedurceList) {
				dao.update(bo);
			}
		}
	}

	/**
	 * 获取当前系统所保存的数据源的表结构
	 * 
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public List findTableTreeCurrDataSource(DcDataSourceBO smser)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select t.table_name_en as id from dc_table t where  t.effective_marker= ? and t.pk_dc_data_source= ? ";
		List<String> str = new ArrayList<String>(); // ？传值
		str.add(AppConstants.EFFECTIVE_Y);
		str.add(smser.getPkDcDataSource());
		List authFuncList = dao.queryForList(sql, str);
		return authFuncList;
	}

	/**
	 * 更新最后加载人 和加载时间
	 * 
	 * @param smser
	 * @throws OptimusException
	 */
	public void updateDataSourceTimeAndUser(DcDataSourceBO smser)
			throws OptimusException {
		// 从缓存中获取当前登录用户信息
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if (StringUtils.isEmpty(smser.getFirstLoaderId())) {
			smser.setFirstLoaderId(user.getUserId());
			smser.setFirstLoaderName(user.getUserName());
			smser.setFirstLoadTime(Calendar.getInstance());
			smser.setLastLoadTime(Calendar.getInstance());
			smser.setLastModifierId(user.getUserId());
			smser.setLastModifierName(user.getUserName());
		} else {
			smser.setLastLoadTime(Calendar.getInstance());
			smser.setLastModifierId(user.getUserId());
			smser.setLastModifierName(user.getUserName());

		}

		IPersistenceDAO dao = getPersistenceDAO();

		dao.update(smser);

	}

	/**
	 * 获取远程触发器的字段
	 * 
	 * @return
	 */
	public List<String> getTriMapColum() {
		List<String> str = new ArrayList<String>();
		str.add("triggerName");

		return str;
	}

	/**
	 * 获取远程存储过程的字段
	 * 
	 * @return
	 */
	public List<String> getProMapColum() {

		List<String> str = new ArrayList<String>();
		str.add("procName");
		str.add("procSql");
		return str;
	}

	/**
	 * 视图
	 * 
	 * @return
	 */
	public List<String> getViewMapColum() {

		List<String> str = new ArrayList<String>();
		str.add("viewName");
		str.add("viewSql");
		str.add("viewUseDesc");
		return str;
	}

	/**
	 * 字段
	 * 
	 * @return
	 */
	public List<String> getFiledsMapColum() {

		List<String> str = new ArrayList<String>();
		str.add("columnNameEn");
		str.add("columnNameCn");
		str.add("columnType");
		str.add("columnLength");
		str.add("isNull");
		str.add("isPrimaryKey");
		return str;
	}

	/**
	 * 表
	 * 
	 * @return
	 */
	public List<String> getTablesMapColum() {

		List<String> str = new ArrayList<String>();
		str.add("tableNameEn");
		str.add("tableNameCn");
		str.add("firstRecordCount");
		return str;
	}

}
