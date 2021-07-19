package cn.gwssi.dw.rd.metadata.txn;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.rd.metadata.vo.SysRdTableContext;
import cn.gwssi.dw.rd.metadata.vo.SysRdUnclaimTableContext;
import cn.gwssi.template.freemarker.FreemarkerUtil;

import com.gwssi.common.util.UuidGenerator;

public class TxnSysRdTable extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysRdTable.class, SysRdTableContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_rd_table";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select sys_rd_table list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one sys_rd_table";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one sys_rd_table";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one sys_rd_table";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one sys_rd_table";
	
	/**
	 * 构造函数
	 */
	public TxnSysRdTable()
	{

	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询已认领列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002201( SysRdTableContext context ) throws TxnException
	{
		DataBus db = context.getRecord(inputNode);
		String table_code = db.getValue("table_code");
		String column_code = db.getValue("column_code");
		if(table_code!=null && !"".equals(table_code)){
			table_code = table_code.toUpperCase();
			db.setValue("table_code", table_code);
		}
		if(column_code!=null && !"".equals(column_code)){
			column_code = column_code.toUpperCase();
			db.setValue("column_code", column_code);
		}
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String show_all = context.getRecord(inputNode).getValue("show_all");
		if(show_all!=null&&!show_all.equals("")){
			Attribute.setPageRow(context, outputNode, -1);
		}
		// 查询记录的条件 VoSysRdTableSelectKey selectKey = context.getSelectKey( inputNode );
		
		table.executeFunction( "querySysRdTable", context, inputNode, outputNode );
		//table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoSysRdTable result[] = context.getSysRdTables( outputNode );
		 
	}
	
	public void txn80004301( SysRdTableContext context )throws TxnException{
		SysRdTableContext context1 = new SysRdTableContext();
		String query_type = context.getRecord("select-key").getValue("query_type");
		String sys_simple = context.getRecord("select-key").getValue("sys_simple");
		if(query_type == null || StringUtils.isBlank(query_type)){
			context1.addRecord("select-key", context.getRecord("select-key"));
			callService("8000111", context1);
			Recordset rs = context1.getRecordset("record");
			//context.addRecord("sys_rd_system_ids", rs);
			String sys_rd_system_ids = "";
			DataBus db = new DataBus();
			if(rs!=null && rs.size()>0){
				for(int i=0;i<rs.size();i++){
					db = rs.get(i);
					sys_rd_system_ids += db.getValue("sys_rd_system_id")+",";
				}
				//sys_rd_system_ids = sys_rd_system_ids.substring(0, sys_rd_system_ids.length()-1);	//处理末尾的逗号
			}
			context.getRecord("select-key").setValue("sys_system_ids", sys_rd_system_ids);
		}
		context.getRecord("input-node").setValue("sys_simple", sys_simple);
		context.getRecord("input-node").setValue("sys_system_ids", context.getRecord("select-key").getValue("sys_system_ids"));
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, 5);
		table.executeFunction( "querySysRdTablePreview", context, inputNode, outputNode );
	}
	
	/** 修改已认领信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002202( SysRdTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoSysRdTable sys_rd_table = context.getSysRdTable( inputNode );
		//更新认领人、认领时间及时间戳字段
		//获取当前日期和时间
		/*String claim_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();
		
		VoUser user = context.getOperData();
		String userName = user.getOperName();
		
		context.getRecord("record").setValue("claim_date", claim_date);
		context.getRecord("record").setValue("timestamp", timestamp);
		context.getRecord("record").setValue("claim_operator", userName);*/
		//table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		table.executeFunction( "updateClaimTable", context, inputNode, outputNode );
	}
	
	/** 增加已认领信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002203( SysRdTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		context.getRecord("record").setValue("sys_rd_table_id",UuidGenerator.getUUID() );
		table.executeFunction("selectTableNo", context, inputNode, "record1");
		table.executeFunction( "insertClaimTable", context, inputNode, outputNode );
		table.executeFunction("insertColumnTable", context, inputNode, outputNode);
	}
	
	/** 查询已认领用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002204( SysRdTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoSysRdTablePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoSysRdTable result = context.getSysRdTable( outputNode );
	}
	
	/** 删除已认领信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002205( SysRdTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoSysRdTablePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		//table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		table.executeFunction( "deleteTable", context, inputNode, outputNode );
		table.executeFunction( "deleteColumnTable", context, inputNode, outputNode );
		
	}
	
	/**拷贝未认领字段表到已认领字段表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002206( SysRdUnclaimTableContext context ) throws TxnException
	{
		//System.out.println("txn80002206>>>>>>>>");
		Recordset rs = context.getRecordset(inputNode);
		for (int i = 0; i < rs.size(); i ++)
		{
			DataBus dataBus = rs.get(i);
			/*dataBus.setValue("table_name", dataBus.getValue("unclaim_table_name"));
			dataBus.setValue("table_code", dataBus.getValue("unclaim_table_code"));
			dataBus.setValue("first_record_count", dataBus.getValue("cur_record_count"));
			dataBus.setValue("table_index", dataBus.getValue("tb_index_columns"));
			dataBus.setValue("table_primary_key", dataBus.getValue("tb_pk_columns"));*/
		}
		
	}
	
	public void txn80002207( SysRdTableContext context ) throws TxnException
	{
		//System.out.println("TxnSysRdTable>>>>txn80002207");
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "selectStateOfClaimTable", context, inputNode, outputNode );
	}
	
	/** 查询表关联关系
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn8000801( SysRdTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );

		table.executeFunction( "querySysRdTableRelationShip", context, inputNode, outputNode );
		
	}

	
	
	/** 资源目录内容
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80008880( SysRdTableContext context ) throws TxnException
	{
		//System.out.println(context);
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String sys_rd_type = context.getRecord("primary-key")
		.getValue("sys_rd_type");
		System.out.println("sys_rd_type="+sys_rd_type);
		StringBuffer stringBuffer = new StringBuffer();
		
		if(sys_rd_type.equals("1")){
		stringBuffer.append("select count(1) as rd_nums from sys_rd_table");
		}
		else if(sys_rd_type.equals("2")){
		stringBuffer.append("select count(1) as rd_nums from sys_rd_unclaim_table");
		}
		System.out.println("sql="+stringBuffer.toString());
		table.executeSelect(stringBuffer.toString(), context, "record");
		//System.out.println(context);
	}
	
	/** 资源目录概览
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80008881( SysRdTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String sys_rd_type = context.getRecord("primary-key")
		.getValue("sys_rd_type");
		System.out.println("sys_rd_type="+sys_rd_type);
		
		StringBuffer stringBuffer = new StringBuffer();

/*		stringBuffer.append("select (case  when t.jc_dm_bzly = '1' then  '国际标准' when t.jc_dm_bzly = '2' then  " +
				"'国家标准'  when t.jc_dm_bzly = '3' then  '行业标准'  when t.jc_dm_bzly = '4' then  '自定义标准'  " +
				"else  '其他'  end) as names, count(*) as nums  ");
		stringBuffer.append("from gz_dm_jcdm t group by t.jc_dm_bzly ");
		stringBuffer.append("union all ");
		stringBuffer.append("select '基础代码集' as names,count(*) as nums from sys_rd_standard_codeindex  ");*/
		stringBuffer.append("select id,name,countnum ,time from first_page_rd where type='rd'");
		System.out.println("sql="+stringBuffer.toString());
		table.executeRowset(stringBuffer.toString(), context, "record");
		//System.out.println(context);
	}
	
	/** 应用日志概览
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80008882( SysRdTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		int year = cal.get(Calendar.YEAR);
		int month =cal.get(Calendar.MONTH)+1;
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select data_sum,month_use_sum,day_avg,to_char(use_add_rate,'fm999999990.999999999') use_add_rate,use_num,use_sum,interface_data from sys_log_total t where year=");
		stringBuffer.append(year);
		stringBuffer.append(" and month=");
		stringBuffer.append(month);
		//System.out.println("应用日志="+stringBuffer.toString());
		table.executeSelect(stringBuffer.toString(), context, "record");
		
		
		//取上个月的值算环比
		TxnContext contextlast = new TxnContext();
		cal.add(Calendar.MONTH, -1);
		year = cal.get(Calendar.YEAR);
		month =cal.get(Calendar.MONTH)+1;
		stringBuffer.setLength(0);
		stringBuffer.append("select data_sum,month_use_sum,day_avg,to_char(use_add_rate,'fm999999990.999999999') use_add_rate,use_num,use_sum,interface_data from sys_log_total t where year=");
		stringBuffer.append(year);
		stringBuffer.append(" and month=");
		stringBuffer.append(month);
		//System.out.println("====="+stringBuffer.toString());
		table.executeSelect(stringBuffer.toString(), contextlast, "record");
		
		
		String lastMonthNum = contextlast.getRecord("record").getValue("day_avg");
		String thisMonthNum = context.getRecord("record").getValue("day_avg");
		
		String result="";
		//LogReportManager lr = new LogReportManager();
		//result = lr.calMoM(thisMonthNum, lastMonthNum,"1","1");
		//context.getRecord("record").setValue("use_add_rate", result);
/*		if (Double.parseDouble(lastMonthNum) == 0) {
			result = MathUtils.divide(MathUtils.sub(thisMonthNum, "0"), "1", 2);
		} else {
			result = MathUtils.divide(MathUtils
					.mult(MathUtils.sub(thisMonthNum, lastMonthNum), "100"), lastMonthNum, 2);
		}*/
		
		//查询使用总数
		stringBuffer.setLength(0);
		stringBuffer.append("select t.first_page_total_num  from first_page_total_new t where  t.first_page_total_cls='query_total_num_show'");
		table.executeSelect(stringBuffer.toString(), contextlast, "record");
		
		context.getRecord("record").setValue("use_sum", contextlast.getRecord("record").getValue("first_page_total_num"));
		
		
		
		
		//System.out.println("============="+context);
		
		stringBuffer.setLength(0);
		//查询TOP5
		stringBuffer.append("select * from(");
		stringBuffer.append("select area_name,user_num from sys_log_use s where s.year=to_char(add_months(sysdate,-1),'yyyy') ");
		stringBuffer.append("and s.month=to_char(add_months(sysdate,-1),'mm') and s.use_type=3 ");
		stringBuffer.append("and s.user_num is not null and s.area_name!='合计' order by user_num desc)");
		stringBuffer.append("where rownum<=5");
		
		//System.out.println("查询TOP5="+stringBuffer.toString());
		table.executeRowset(stringBuffer.toString(), context, "recordStop5");
		
		stringBuffer.setLength(0);
		//使用TOP51
		stringBuffer.append("select * from( ");
		stringBuffer.append("select x.sjjgname||'_'||x.jgmc jgmc,a.c from( ");
		stringBuffer.append("select orgid, count(1) c from first_page_query t ");
		stringBuffer.append("where t.query_date >= to_char(sysdate - 6, 'yyyy-MM-dd') ");
		stringBuffer.append("group by orgid)a,xt_zzjg_jg x ");
		stringBuffer.append("where a.orgid=x.jgid_pk and x.jgmc<>'临时部门' order by c desc) ");
		stringBuffer.append("where rownum<=5");
		//System.out.println("使用TOP5="+stringBuffer.toString());
		table.executeRowset(stringBuffer.toString(), context, "recordUtop5");
		
		stringBuffer.setLength(0);
		//下载top5
		stringBuffer.append("select * from(  ");
		stringBuffer.append("select x.sjjgname||'_'||x.jgmc jgmc,a.c from(  ");
		stringBuffer.append("select operdept, count(1) c from download_log t ");
		stringBuffer.append("where t.operdate >= to_char(sysdate - 6, 'yyyy-MM-dd') ");
		stringBuffer.append("group by t.operdept)a,xt_zzjg_jg x ");
		stringBuffer.append("where a.operdept=x.jgid_pk and x.jgmc<>'临时部门' order by c desc) ");
		stringBuffer.append("where rownum<=5 ");
		//System.out.println("下载TOP5="+stringBuffer.toString());
		table.executeRowset(stringBuffer.toString(), context, "recordDtop5");
		
		TxnContext context2 = new TxnContext();
		FreemarkerUtil freeUtil=new FreemarkerUtil();		
		DataBus db = new DataBus();
		//今日在线人数
		stringBuffer.setLength(0);
		stringBuffer.append("select b.dt as day, nvl(a.c, 0) as num ");
		stringBuffer.append("from (select substr(scsj, 12, 2) d, max(t.online_num) c ");
		stringBuffer.append("from tj_online t ");
		stringBuffer.append("where t.scsj >= to_char(sysdate, 'yyyy-MM-dd') ");
		stringBuffer.append("group by substr(scsj, 12, 2) ");
		stringBuffer.append("order by 1) a, ");
		stringBuffer.append("(select 5 + rownum dt from dual connect by rownum <= 14) b ");
		stringBuffer.append("where a.d(+) = b.dt order by dt ");
		
		//System.out.println("今日在线人数="+stringBuffer.toString());
		table.executeRowset(stringBuffer.toString(), context2, "chart1");
		Recordset rsChart1 = context2.getRecordset("chart1");
		Map dataMap = new HashMap();
		for(int i=0;i<rsChart1.size();i++){
			DataBus chartdb = rsChart1.get(i);
			dataMap.put("day"+(i+1), chartdb.get("day"));
			dataMap.put("online"+(i+1), chartdb.get("num"));
		}
		String chart1XML = freeUtil.exportXmlString("jinri.xml", dataMap);
		db.setValue("chart1XML", chart1XML);
		
		
		
		//最近五天数据增长情况
		
		stringBuffer.setLength(0);
		stringBuffer.append("select t1.query_time count_date, nvl(count_incre, 0) count_incre ");
		stringBuffer.append("from (select v.count_date, sum(v.count_incre) count_incre ");
		stringBuffer.append("from view_sys_count_log v ");
		stringBuffer.append("where v.count_date >= to_char(sysdate - 5, 'yyyy-mm-dd') ");
		stringBuffer.append("and v.count_date < to_char(sysdate, 'yyyy-mm-dd') ");
		stringBuffer.append("group by v.count_date ");
		stringBuffer.append("order by v.count_date) t, ");
		stringBuffer.append("(select to_char(sysdate - 6 + rownum, 'yyyy-mm-dd') query_time ");
		stringBuffer.append("from dual ");
		stringBuffer.append("connect by rownum <=5) t1 ");
		stringBuffer.append("where t.count_date(+) = t1.query_time ");
		stringBuffer.append("order by t1.query_time asc ");
		
		//System.out.println("最近五天数据增长情况="+stringBuffer.toString());		
		table.executeRowset(stringBuffer.toString(), context2, "chart2");
		Recordset rsChart2 = context2.getRecordset("chart2");
	    dataMap = new HashMap();
		for(int i=0;i<rsChart2.size();i++){
			DataBus chartdb = rsChart2.get(i);
			dataMap.put("day"+(i+1), chartdb.get("count_date"));
			dataMap.put("online"+(i+1), chartdb.get("count_incre"));
			//System.out.println(chartdb.get("count_date"));
			//System.out.println(chartdb.get("count_incre"));
		}
		String chart2XML = freeUtil.exportXmlString("cunchu.xml", dataMap);
		db.setValue("chart2XML", chart2XML);
		
		
		
		
		context.addRecord("chart", db);
		//System.out.println(context);
		
	}
	
	
	/** 接口服务概览
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80008884( SysRdTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("select count(1) jk_num,sum(t.WS_NUM) in_sum,sum(t.CLT_NUM) out_sum,count(1)-sum(t.STATE)  yx_num     from view_sys_svr_user t where t.USER_NAME not like '%分局'  and t.USER_TYPE='1' ")
				.append("union all ")
				.append("select count(1) jk_num,sum(t.WS_NUM) in_sum,sum(t.CLT_NUM) out_sum ,count(1)-sum(t.STATE)  yx_num   from view_sys_svr_user t where t.USER_NAME not like '%分局'  and t.USER_TYPE='0' ")
				.append("union all ")
				.append("select count(1) jk_num,sum(t.WS_NUM) in_sum,sum(t.CLT_NUM)  out_sum ,count(1)-sum(t.STATE)  yx_num  from view_sys_svr_user t where t.USER_NAME  like '%分局' ");
		table.executeRowset(stringBuffer.toString(), context, "jk_nums");
		
		stringBuffer.setLength(0);
		stringBuffer.append("select t.USER_NAME,t.STATE,t.WS_NUM,t.CLT_NUM from view_sys_svr_user t where t.USER_NAME not like '%分局'  and t.USER_TYPE='1' ");
		table.executeRowset(stringBuffer.toString(), context, "sys_inside");
		
		stringBuffer.setLength(0);
		stringBuffer.append("select t.USER_NAME,t.STATE,t.WS_NUM,t.CLT_NUM from view_sys_svr_user t where t.USER_NAME not like '%分局'  and t.USER_TYPE='0' ");
		table.executeRowset(stringBuffer.toString(), context, "sys_outside");
		
		stringBuffer.setLength(0);
		stringBuffer.append("select t.USER_NAME,t.STATE,t.WS_NUM,t.CLT_NUM from view_sys_svr_user t where t.USER_NAME like '%分局'  ");
		table.executeRowset(stringBuffer.toString(), context, "sys_fj");
		
		System.out.println(context);
	}
	
	
	/** 查询数据表情况
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn80002209( SysRdTableContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
        
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" select count(1) wrlb from sys_rd_unclaim_table a, sys_rd_data_source d where a.data_object_type = 'T' ")
				.append(" and a.sys_rd_data_source_id = d.sys_rd_data_source_id and a.sys_rd_unclaim_table_id not in ")
				.append(" (select b.sys_rd_unclaim_table_id from sys_rd_unclaim_table b, sys_rd_table c ")
				.append(" where b.sys_rd_data_source_id = c.sys_rd_data_source_id and b.unclaim_table_code = c.table_code ")
				.append(" and b.object_schema = c.object_schema)");
		table.executeSelect(stringBuffer.toString(), context, "record");
		
		stringBuffer.setLength(0);
		
		stringBuffer.append("select nvl(sum(case when (table_type='1') then tn end),0) ywb,")
				.append("nvl(sum(case when (table_type='2') then tn end),0) dmb,")
				.append("nvl(sum(case when (table_type='3') then tn end),0) xtb")
				.append(" from(select table_type,count(*) tn  from sys_rd_table group by table_type)");
		table.executeSelect(stringBuffer.toString(), context, "record");
		//System.out.println(context);
	}
	
	
	/**
	 * 重载父类的方法，用于替换交易接口的输入变量
	 * 调用函数
	 * @param funcName 方法名称
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数"
			);
		}
		
		// 执行
		SysRdTableContext appContext = new SysRdTableContext( context );
		invoke( method, appContext );
	}
}
