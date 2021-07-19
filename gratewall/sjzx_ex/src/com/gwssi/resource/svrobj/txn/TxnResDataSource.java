package com.gwssi.resource.svrobj.txn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.dao.resource.code.CodeMap;
import cn.gwssi.common.txn.TxnService;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.task.IndexJob;
import com.gwssi.common.task.ZwtJsonCreat;
import com.gwssi.common.testconnection.ConnAccess;
import com.gwssi.common.testconnection.ConnDatabaseUtil;
import com.gwssi.common.testconnection.ConnFtpUtil;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.IpPingUtil;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.common.util.ValueSetCodeUtil;
import com.gwssi.resource.svrobj.vo.ResDataSourceContext;
import com.gwssi.webservice.client.AnalyzeWsdl;

public class TxnResDataSource extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap		txnMethods				= getAllMethod(
																TxnResDataSource.class,
																ResDataSourceContext.class);

	// ���ݱ�����
	private static final String	TABLE_NAME				= "res_data_source";

	// ���ݱ�����
	private static final String	TABLE_NAME_SERVICE		= "res_service_targets";

	// ��ѯ�б�
	private static final String	ROWSET_FUNCTION			= "select res_data_source list";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION			= "select one res_data_source";

	// ��ѯ��¼
	private static final String	SELECT_FUNCTION_SERVICE	= "select one res_service_targets";

	// �޸ļ�¼
	private static final String	UPDATE_FUNCTION			= "update one res_data_source";

	// ���Ӽ�¼
	private static final String	INSERT_FUNCTION			= "insert one res_data_source";

	// ɾ����¼
	private static final String	DELETE_FUNCTION			= "delete one res_data_source";

	private static final String	DELETE_FALSE			= "update markup";

	/**
	 * ���캯��
	 */
	public TxnResDataSource()
	{

	}

	/**
	 * ��ʼ������
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * ��ѯ����Դ���б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20102001(ResDataSourceContext context) throws TxnException
	{
		System.out.println("-----------start----------");
		//IndexJob jj = new IndexJob();
		//jj.getMLPieStr();
		//jj.getPie1TableData();
		//jj.getMLPieStr_eCharts();
		//try {
			// jj.bulidZwtIndexData("E:/workspace/bjgs_exchange_fb");
		ZwtJsonCreat zj = new ZwtJsonCreat();
		zj.getInterface_table();
			
			//System.out.println("ss11=="+ss);
		//} catch (DBException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//} 
		System.out.println("-----------end----------");
		
		
		// this.callService("20102001", context);
		ResDataSourceContext context1 = new ResDataSourceContext();
		Attribute.setPageRow(context1, outputNode, -1);

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		table.executeFunction("queryDSCountByType", context1, inputNode,
				outputNode);
		Recordset typeRs = context1.getRecordset("record");
		ValueSetCodeUtil valueUtil = new ValueSetCodeUtil();
		if (!typeRs.isEmpty()) {
			for (int i = 0; i < typeRs.size(); i++) {
				DataBus db = typeRs.get(i);
				db.setValue(
						"title",
						valueUtil.getCodeStr(context, "��Դ����_����Դ����",
								db.getValue("key")));
			}
		}
		context.setValue("dsType", JsonDataUtil.getJsonByRecordSet(typeRs));
		ResDataSourceContext dsCoutext = new ResDataSourceContext();
		Attribute.setPageRow(dsCoutext, outputNode, -1);
		table.executeFunction("queryDSCountBySvrObj", dsCoutext, inputNode,
				outputNode);
		Recordset targetRs = dsCoutext.getRecordset(outputNode);
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(context, "��Դ����_�����������");
		if (!rs.isEmpty()) {
			String[] keys = new String[rs.size()];
			String[] values = new String[rs.size()];
			for (int i = 0; i < rs.size(); i++) {
				DataBus db = rs.get(i);
				keys[i] = db.getValue("codename");
				values[i] = db.getValue("codevalue");
			}
			String groupValue = JsonDataUtil.getJsonGroupByRecordSet(targetRs,
					"service_targets_type", keys, values);
			// System.out.println("groupValue:" + groupValue);
			context.setValue("svrTarget", groupValue);
		}

		table.executeFunction("queryDataSource", context, inputNode, outputNode);

		//System.out.println(outputNode + "txn20102001======" + context);
	}

	/**
	 * ��ѯ����Դ���б�-���ҳ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20102020(ResDataSourceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���������� json����
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("queryDSCountByType", context, inputNode,
				outputNode);
		Recordset typeRs = context.getRecordset("record");
		ValueSetCodeUtil valueUtil = new ValueSetCodeUtil();
		if (!typeRs.isEmpty()) {
			for (int i = 0; i < typeRs.size(); i++) {
				DataBus db = typeRs.get(i);
				db.setValue(
						"title",
						valueUtil.getCodeStr(context, "��Դ����_����Դ����",
								db.getValue("key")));
			}
		}
		context.setValue("dsType", JsonDataUtil.getJsonByRecordSet(typeRs));
		ResDataSourceContext dsCoutext = new ResDataSourceContext();
		Attribute.setPageRow(dsCoutext, outputNode, -1);
		table.executeFunction("queryDSCountBySvrObj", dsCoutext, inputNode,
				outputNode);
		Recordset targetRs = dsCoutext.getRecordset(outputNode);
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(context, "��Դ����_�����������");
		if (!rs.isEmpty()) {
			String[] keys = new String[rs.size()];
			String[] values = new String[rs.size()];
			for (int i = 0; i < rs.size(); i++) {
				DataBus db = rs.get(i);
				keys[i] = db.getValue("codename");
				values[i] = db.getValue("codevalue");
			}
			String groupValue = JsonDataUtil.getJsonGroupByRecordSet(targetRs,
					"service_targets_type", keys, values);
			System.out.println("groupValue:" + groupValue);
			context.setValue("svrTarget", groupValue);
		}

	}

	/**
	 * �޸�����Դ����Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20102002(ResDataSourceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// �޸ļ�¼������ VoResDataSource res_data_source = context.getResDataSource(
		// inputNode );
		System.out.println("txn20102002 here");
		context.getRecord("record").setValue("last_modify_time",
				CalendarUtil.getCurrentDateTime());
		context.getRecord("record").setValue("last_modify_id",
				context.getRecord("oper-data").getValue("userID"));

		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * ��������Դ����Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20102003(ResDataSourceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���Ӽ�¼������ VoResDataSource res_data_source = context.getResDataSource(
		// inputNode );
		// ��������ID
		String id = UuidGenerator.getUUID();
		String data_source_type = context.getRecord("record").getValue(
				"data_source_type");

		if (data_source_type != null && !"".equals(data_source_type)) {
			if (data_source_type.equals("WebService"))
				context.getRecord("record").setValue("data_source_type", "00");
			else if (data_source_type.endsWith("���ݿ�"))
				context.getRecord("record").setValue("data_source_type", "01");
			else if (data_source_type.endsWith("FTP"))
				context.getRecord("record").setValue("data_source_type", "02");
			else if (data_source_type.endsWith("JMS��Ϣ"))
				context.getRecord("record").setValue("data_source_type", "03");
			else if (data_source_type.endsWith("SOCKET"))
				context.getRecord("record").setValue("data_source_type", "04");
		}

		context.getRecord("record").setValue("data_source_id", id);
		context.getRecord("record").setValue("created_time",
				CalendarUtil.getCurrentDateTime());
		context.getRecord("record").setValue("is_markup",
				ExConstant.IS_MARKUP_Y);// ���볣��
		context.getRecord("record").setValue("creator_id",
				context.getRecord("oper-data").getValue("userID"));

		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);

	}

	/**
	 * ��ѯ����Դ�������޸�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20102004(ResDataSourceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		
		
		String data_source_id = context.getRecord("primary-key").getValue("data_source_id");
		StringBuffer sql = new StringBuffer();
		
		
		sql.append("select t1.*,t1.created_time as cretime,nvl(t1.last_modify_time,t1.created_time)as modtime, yh1.yhxm as crename,nvl(yh2.yhxm,yh1.yhxm) as modname from res_data_source t1,xt_zzjg_yh_new yh1,xt_zzjg_yh_new yh2");
		sql.append(" where  t1.creator_id = yh1.yhid_pk(+)  and t1.last_modify_id = yh2.yhid_pk(+)  and t1.data_source_id = '");
		sql.append(data_source_id);
		sql.append("'");
		table.executeRowset(sql.toString(), context, outputNode);
	
	}

	/**
	 * ɾ������Դ����Ϣ
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20102005(ResDataSourceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ɾ����¼�������б� VoResDataSourcePrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		// table.executeFunction( DELETE_FUNCTION, context, inputNode,
		// outputNode );

		table.executeFunction("deleteDataSource", context, inputNode,
				outputNode);
	}

	/**
	 * ����ftp����
	 * 
	 * @param context
	 *            ����������
	 * @throws Exception 
	 */
	public void txn20102009(ResDataSourceContext context) throws Exception
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ���Ӽ�¼������ VoResDataSource res_data_source = context.getResDataSource(
		// inputNode );
		// ��������ID
		String url = context.getRecord("record").getValue("url");

		// if(IpPingUtil.ping(url)){
		String user = context.getRecord("record").getValue("db_username");
		String password = context.getRecord("record").getValue("db_password");
		int port = Integer.parseInt(context.getRecord("record").getValue(
				"access_port"));

		ConnFtpUtil ftp = new ConnFtpUtil();

		boolean fc = ftp.connect(url, port, user, password);

		if (fc) {
			context.getRecord("record").setValue("result", "1");
		} else {
			context.getRecord("record").setValue("result", "0");
		}
		// }else{
		// context.getRecord("record").setValue("result", "0");
		// }
		// table.executeFunction( INSERT_FUNCTION, context, inputNode,
		// outputNode );
	}

	/**
	 * �������ݿ�����
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20102016(ResDataSourceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		String db_type = context.getRecord("record").getValue("db_type");
		String data_source_ip = context.getRecord("record").getValue(
				"data_source_ip");
		String access_port = context.getRecord("record")
				.getValue("access_port");
		String db_instance = context.getRecord("record")
				.getValue("db_instance");
		String db_username = context.getRecord("record")
				.getValue("db_username");
		String db_password = context.getRecord("record")
				.getValue("db_password");
		if (IpPingUtil.ping(data_source_ip)) {
			if (db_type != null && !db_type.equals("")) {
				if ("00".equals(db_type))// oracle
				{
					String url = "jdbc:oracle:thin:@" + data_source_ip + ":"
							+ access_port + ":" + db_instance;
					ConnDatabaseUtil db = new ConnDatabaseUtil();
					boolean rs = db.testOracleConn(url, db_username,
							db_password);

					if (rs) {
						context.getRecord("record").setValue("result", "1");
					} else
						context.getRecord("record").setValue("result", "0");

				} else if ("01".equals(db_type))// db2
				{
					// jdbc:db2://172.30.7.189:50000/newxcdb
					String url = "jdbc:db2://" + data_source_ip + ":"
							+ access_port + "/" + db_instance;
					ConnDatabaseUtil db = new ConnDatabaseUtil();
					boolean rs = db.testDB2Conn(url, db_username, db_password);

					if (rs) {
						context.getRecord("record").setValue("result", "1");
					} else
						context.getRecord("record").setValue("result", "0");

				} else if ("02".equals(db_type))// sql server
				{
					// jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=mydb
					String url = "jdbc:sqlserver://" + data_source_ip + ":"
							+ access_port + ";DatabaseName=" + db_instance;
					ConnDatabaseUtil db = new ConnDatabaseUtil();
					boolean rs = db.testSqlServer2005Conn(url, db_username,
							db_password);

					if (rs) {
						context.getRecord("record").setValue("result", "1");
					} else
						context.getRecord("record").setValue("result", "0");
				} else if ("03".equals(db_type))// mysql
				{
					// jdbc:mysql://localhost:3306/myuser
					String url = "jdbc:mysql://" + data_source_ip + ":"
							+ access_port + "/" + db_instance;
					ConnDatabaseUtil db = new ConnDatabaseUtil();
					boolean rs = db
							.testMySqlConn(url, db_username, db_password);

					if (rs) {
						context.getRecord("record").setValue("result", "1");
					} else
						context.getRecord("record").setValue("result", "0");

				} else if ("04".equals(db_type))// access
				{
					ConnAccess db = new ConnAccess();
					boolean rs = db.testConnection(data_source_ip, db_instance,
							db_username, db_password);

					if (rs) {
						context.getRecord("record").setValue("result", "1");
					} else
						context.getRecord("record").setValue("result", "0");
				} else {

				}

			}
		} else {
			context.getRecord("record").setValue("result", "0");
		}

		// table.executeFunction( INSERT_FUNCTION, context, inputNode,
		// outputNode );
	}

	/**
	 * ����webservice����
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20102017(ResDataSourceContext context) throws TxnException
	{
		String ip = context.getRecord("record").getValue("data_source_ip");
		String port = context.getRecord("record").getValue("access_port");
		String access_url = context.getRecord("record").getValue("access_url");
		URL url = null;
		boolean flag = false;
		try {
			url = new URL("http://"+ip+":"+port);
		} catch (MalformedURLException e) {
			context.getRecord("record").setValue("result", "0");
		}
	    HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			context.getRecord("record").setValue("result", "0");
		}
	    connection.setRequestProperty("Connection", "close");
	    connection.setConnectTimeout(5000); // Timeout 10 seconds
	    try {
			connection.connect();
			flag = true;
		} catch (IOException e) {
			context.getRecord("record").setValue("result", "0");
		}

	    if (flag == true) {
			AnalyzeWsdl wc = new AnalyzeWsdl();
			Map result = wc.analyzeWsdl(access_url);
			String rs = "";
			if (result != null) {
				rs = (String) result.get("CLIENT_STATE");
				if (rs != null && !"".equals(rs)) {
					if ("Y".equals(rs)) {
						context.getRecord("record").setValue("result", "1");
					} else {
						context.getRecord("record").setValue("result", "0");
					}
				}
			} else {
				context.getRecord("record").setValue("result", "0");
			}
	    }
		// System.out.println("���ӳɹ�");
	}

	/**
	 * ��ѯ����Դ���б�
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20102018(ResDataSourceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME_SERVICE);
		// ��ѯ��¼������ VoResDataSourceSelectKey selectKey = context.getSelectKey(
		// inputNode );

		// table.executeFunction( "queryDataSource", context, inputNode,
		// outputNode );
		table.executeFunction(SELECT_FUNCTION_SERVICE, context, inputNode,
				outputNode);
		// ��ѯ���ļ�¼�� VoResDataSource result[] = context.getResDataSources(
		// outputNode );
	}

	/**
	 * У������Դ�Ƿ�����
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20102019(ResDataSourceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// ��ѯ��¼������ VoResDataSourceSelectKey selectKey = context.getSelectKey(
		// inputNode );

		// table.executeFunction( "queryDataSource", context, inputNode,
		// outputNode );
		table.executeFunction("queryDataSourceUse", context, inputNode,
				outputNode);
		// ��ѯ���ļ�¼�� VoResDataSource result[] = context.getResDataSources(
		// outputNode );
	}

	/**
	 * ����socket����
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20102023(ResDataSourceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		String data_source_ip = context.getRecord("record").getValue(
				"data_source_ip");
		String access_port = context.getRecord("record")
				.getValue("access_port");
		// String access_url =
		// context.getRecord("record").getValue("access_url");

		String result = "0";
		Socket socket = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		if (IpPingUtil.ping(data_source_ip)) {
			try {
				socket = new Socket(data_source_ip,
						Integer.parseInt(access_port));
				pw = new PrintWriter(new OutputStreamWriter(
						socket.getOutputStream()));
				pw.println("socket");
				pw.flush();
				br = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				result = br.readLine();
			} catch (Exception e) {
				result = "0";
				e.printStackTrace();
			} finally {
				context.getRecord("record").setValue("result", result);
			}
		} else {
			context.getRecord("record").setValue("0", result);
		}
	}

	/**
	 * ����JMS����
	 * 
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	public void txn20102030(ResDataSourceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		String data_source_ip = context.getRecord("record").getValue(
				"data_source_ip");
		String access_port = context.getRecord("record")
				.getValue("access_port");
		// String access_url =
		// context.getRecord("record").getValue("access_url");

		String result = "0";
		if (IpPingUtil.ping(data_source_ip)) {
			String url = "http://" + data_source_ip + ":" + access_port;// //////////////String
																		// url =
																		// "http://172.30.18.50:8089";
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					url);
			try {
				ActiveMQConnection connection = (ActiveMQConnection) connectionFactory
						.createConnection();
				// connection.start();
				// ActiveMQSession session =
				// (ActiveMQSession)connection.createSession(false,
				// Session.AUTO_ACKNOWLEDGE);
				// Destination destination =
				// session.createQueue("TestDatasource");
				// MessageProducer messageProducer =
				// session.createProducer(destination);
				// TextMessage message = session.createTextMessage();
				// message.setText("connect server");
				// messageProducer.send(message);
				// session.close();
				// connection.stop();
				connection.close();
				result = "1";
			} catch (JMSException e) {
				result = "0";
				e.printStackTrace();
			} finally {
				context.getRecord("record").setValue("result", result);
			}
		} else {
			context.getRecord("record").setValue("0", result);
		}
	}

	/**
	 * ���ظ���ķ����������滻���׽ӿڵ�������� ���ú���
	 * 
	 * @param funcName
	 *            ��������
	 * @param context
	 *            ����������
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����");
		}

		// ִ��
		ResDataSourceContext appContext = new ResDataSourceContext(context);
		invoke(method, appContext);
	}

}
