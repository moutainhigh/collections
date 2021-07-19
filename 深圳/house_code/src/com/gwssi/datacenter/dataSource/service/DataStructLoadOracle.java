package com.gwssi.datacenter.dataSource.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.datacenter.model.DcColumnBO;
import com.gwssi.datacenter.model.DcDataSourceBO;
import com.gwssi.datacenter.model.DcProcedureBO;
import com.gwssi.datacenter.model.DcTableBO;
import com.gwssi.datacenter.model.DcViewBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

/**
 * oracle数据库 结构获取
 * 
 * @ClassName: DataStructLoadOracle
 * @author chaihaowei
 * @date 2016年1月8日 下午4:31:02
 */
@Service
public class DataStructLoadOracle extends BaseService {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private DAOManager daomanager;

	@Autowired
	private DataStructLoadBySqlService dataStructLoadBySqlService;

	/**
	 * oracle获取当前数据源表
	 * 
	 * @param smser
	 * @return
	 * @throws OptimusException
	 */
	public List<Map<String, Object>> findCurrUserTable(DcDataSourceBO smser)
			throws OptimusException {
		doSetDataSource(smser);
		IPersistenceDAO dao = getPersistenceDAO(smser.getPkDcDataSource());
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT t.tABLE_NAME AS TABLE_NAME_EN,T.COMMENTS  AS TABLE_NAME_CN "
				+ ",B.num_rows AS FIRST_LOAD_TIME "
				+ " FROM user_tab_comments t,user_tables B "
				+ "WHERE t.TABLE_NAME=B.TABLE_NAME and t.TABLE_NAME not like '_$%'");

		System.out.println("sql:"+sql);
		List<DcTableBO> l1 = dao.queryForList(DcTableBO.class, sql.toString(),
				null);
		System.out.println("shuliang:"+l1.size());
		List<Map<String, Object>> a2 = changDcTableBOToTreeList(l1);
		System.out.println("表的数量："+a2.size());
		return a2;
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
		doSetDataSource(smser);
		IPersistenceDAO dao = getPersistenceDAO(smser.getPkDcDataSource());
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>(); // ？传值
		sql.append("select 	T .column_name AS COLUMN_NAME_EN,"
				+ "T.data_type as COLUMN_TYPE,"
				+ "T.data_length	as COLUMN_LENGTH,"
				+ "	T.NULLABLE  AS IS_NULL,"
				+ "	B.COMMENTS AS COLUMN_NAME_CN  "
				+ " from user_tab_columns t,user_col_comments b  "
				+ "where b.table_name=t.table_name(+) "
				+ "AND t.COLUMN_name=b.column_name" + " and  B.table_name = ?");

		String s1 = table.getTableNameEn().toUpperCase();
		str.add(s1);
		List<DcColumnBO> l1 = dao.queryForList(DcColumnBO.class,
				sql.toString(), str);

		return l1;
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
		doSetDataSource(smser);
		IPersistenceDAO dao = getPersistenceDAO(smser.getPkDcDataSource());
		List<String> strList = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>(); // ？传值
		sql.append("select t.*,i.index_type from user_ind_columns t,user_indexes i "
				+ " where t.index_name = i.index_name and " + "t.table_name= ?");
		str.add(table.getTableNameEn().toUpperCase());
		List<Map> li = dao.queryForList(sql.toString(), str);
		for (Map map1 : li) {
			strList.add(map1.get("columnName").toString());
		}
		return strList;
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
		doSetDataSource(smser);
		IPersistenceDAO dao = getPersistenceDAO(smser.getPkDcDataSource());
		List<String> strList = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>(); // ？传值
		sql.append("SELECT A .column_name FROM	user_cons_columns A, user_constraints b"
				+ " WHERE A .constraint_name = b.constraint_name "
				+ "AND b.constraint_type = 'P' AND A .table_name = ? ");
		str.add(table.getTableNameEn().toUpperCase());
		List<Map> li = dao.queryForList(sql.toString(), str);
		for (Map map1 : li) {
			strList.add(map1.get("columnName").toString());
		}
		return strList;
	}

	/**
	 * 获取某个数据源下的表
	 * 
	 * @return
	 * @throws OptimusException
	 */
	public List<DcTableBO> findTables(DcDataSourceBO smser)
			throws OptimusException {
		doSetDataSource(smser);
		IPersistenceDAO dao = getPersistenceDAO(smser.getPkDcDataSource());
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t.tABLE_NAME AS TABLE_NAME_EN,T.COMMENTS  AS TABLE_NAME_CN ,B.num_rows AS FIRST_RECORD_COUNT  FROM user_tab_comments t,user_tables B WHERE t.TABLE_NAME=B.TABLE_NAME ");
		List<DcTableBO> l1 = dao.queryForList(DcTableBO.class, sql.toString(),
				null);
		return l1;
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
		doSetDataSource(smser);
		IPersistenceDAO dao = getPersistenceDAO(smser.getPkDcDataSource());
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>(); // ？传值
		sql.append("SELECT * FROM USER_SOURCE WHERE TYPE= ? order by name,line");
		String s1 = "PROCEDURE";
		str.add(s1);
		List<Map> maplist = dao.queryForList(sql.toString(), str);
		List<DcProcedureBO> l1 = new ArrayList<DcProcedureBO>();

		List<String> st1 = new ArrayList<String>();
		Map<String, DcProcedureBO> d1 = new HashMap<String, DcProcedureBO>();

		for (Map map : maplist) {
			if (st1.contains(map.get("name").toString())) {
				DcProcedureBO bo = d1.get(map.get("name"));
				String proSql = bo.getProcSql() + "  " + map.get("text");
				bo.setProcSql(proSql);
			} else {
				st1.add((String) map.get("name"));
				DcProcedureBO bo = new DcProcedureBO();
				bo.setProcName((String) map.get("name"));
				bo.setProcSql(map.get("text").toString());
				d1.put((String) map.get("name"), bo);
			}
		}
		for (String key : d1.keySet()) {
			l1.add(d1.get(key));
		}
		return l1;

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
		doSetDataSource(smser);
		IPersistenceDAO dao = getPersistenceDAO(smser.getPkDcDataSource());
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>(); // ？传值
		sql.append("SELECT	K.view_name, K.text AS VIEW_SQL, U .COMMENTS AS VIEW_USE_DESC  FROM USER_VIEWS K, 	user_tab_comments U WHERE 	U .table_name = K.view_name   AND U .table_type = ?");
		String s1 = "VIEW";
		str.add(s1);

		List<DcViewBO> l1 = dao.queryForList(DcViewBO.class, sql.toString(),
				str);
		return l1;
	}

	private List<Map<String, Object>> changDcTableBOToTreeList(
			List<DcTableBO> l1) {
	
		return 	dataStructLoadBySqlService.changDcTableBOToTreeList(l1);
	}

	private void doSetDataSource(DcDataSourceBO smser) {
		// TODO Auto-generated method stub
		dataStructLoadBySqlService.doSetDataSource(smser);
	}

}
