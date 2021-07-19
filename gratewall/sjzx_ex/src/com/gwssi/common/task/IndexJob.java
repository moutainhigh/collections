package com.gwssi.common.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.context.TxnContext;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.JSONUtils;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�SimpleJob ��������ִ��������� �����ˣ�lizheng ����ʱ�䣺May 10, 2013
 * 4:03:57 PM �޸��ˣ�zhongxiaoqi �޸�ʱ�䣺May 10, 2013 4:03:57 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class IndexJob implements StatefulJob
{
	DBOperation	operation	= null;

	public IndexJob()
	{
		operation = DBOperationFactory.createOperation();
	}

	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(IndexJob.class
											.getName());

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
	
	public void execute(JobExecutionContext jobCtx)
	{
		long begin=System.currentTimeMillis();
		logger.info("��ҳ�������ɶ�ʱ����ʼִ��");
		System.out.println("��ҳ�������ɶ�ʱ����ʼִ��");
		ResourceBundle bundle = ResourceBundle.getBundle("index");
		String rootPath = bundle.getString("root_path");
		//��������ͼ-��ͼ
		this.bulidZwtIndexData(rootPath);
		
		
		logger.info("��ҳ�������ɶ�ʱ����ִ�н�������ʱ:"+(System.currentTimeMillis()-begin)+"����");
	}
	
	public void bulidForceIndexData(String rootPath,String strCon)
	{
		ResourceBundle bundle = ResourceBundle.getBundle("index");
		ZwtJsonCreat  zjc = new ZwtJsonCreat();
		logger.info("��ʼ��������ͼ����");
		StringBuffer sb= new StringBuffer();
		// ��ҳĬ����ʾ����ͼ������֯
		try {
			
			
			//start  ��������֮���ϵͼ add by dwn20151230
			sb.append("������ͼ-��ʼ\n");
			long begin=System.currentTimeMillis();
			String str_force = zjc.getForceContent("part",strCon,"");
			sb.append("������ͼ-��������ʱ��"+(System.currentTimeMillis()-begin)+"\n");
			//end  ��������֮���ϵͼ add by dwn20151230
			
			String  force_path = rootPath + bundle.getString("force_all_path");
			
			this.writeFile(force_path, str_force);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��������ͼ-��ͼ
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws DBException
	 */
	public void bulidZwtIndexData(String rootPath)
	{
		ResourceBundle bundle = ResourceBundle.getBundle("index");
		//��������ͼ
		bulidForceIndexData(rootPath,"");
		
		ZwtJsonCreat  zjc = new ZwtJsonCreat();
		logger.info("��ʼ��������ͼ����");
		StringBuffer sb= new StringBuffer();
		// ��ҳĬ����ʾ����ͼ������֯
		try {
			
			sb.append("��������ͼ���ݳ���ͼ-��ʼ\n");
			long begin=System.currentTimeMillis();
			String str_zwt = zjc.getZwtContent("part");
			sb.append("��������ͼ���ݳ���ͼ-��������ʱ��"+(System.currentTimeMillis()-begin)+"\n");
			
			//start  ��������֮���ϵͼ add by dwn20151230
			sb.append("������ͼ-��ʼ\n");
			begin=System.currentTimeMillis();
			String str_force = zjc.getForceContent("part","","");
			sb.append("������ͼ-��������ʱ��"+(System.currentTimeMillis()-begin)+"\n");
			//end  ��������֮���ϵͼ add by dwn20151230
			
			
			begin=System.currentTimeMillis();
			sb.append("��������ͼ����ȫ��ͼ-��ʼ"+"\n");
			// ����ͼȫ��������֯
			String str_all_zwt = zjc.getZwtContent("all");
			sb.append("��������ͼ����ȫ��ͼ-��������ʱ��"+(System.currentTimeMillis()-begin)+"\n");
			
			begin=System.currentTimeMillis();
			sb.append("ϵͳ-��-�ӿ�ͼ-��ʼ"+"\n");
			//ϵͳ-��-�ӿ�ȫ��������֯
			String str_sys_table_interface = zjc.bulidZwtTableDetail();
			sb.append("ϵͳ-��-�ӿ�ͼ��������ʱ��"+(System.currentTimeMillis()-begin)+"\n");
			
			begin=System.currentTimeMillis();
			sb.append("��-�ӿ�-�������-��ʼ"+"\n");
			//��-�ӿ�-�������ȫ��������֯
			String str_table_interface_object = zjc.bulidZwtInterfaceDetail();
			sb.append("��-�ӿ�-��������������ʱ��"+(System.currentTimeMillis()-begin)+"\n");
			
			
			// ��ȡ����ͼ����·��
			String zwt_path = rootPath + bundle.getString("zwt_path");
			sb.append("��ȡ����ͼ����·��:"+zwt_path+"\n");
			// ��ȡ����ͼȫ������·��
			String zwt_all_path = rootPath + bundle.getString("zwt_all_path");
			sb.append("��ȡ����ͼȫ��·��:"+zwt_all_path+"\n");
			//��ȡϵͳ-��-�ӿڵ�����·��
			String zwt_sys_table_interface_path = rootPath + bundle.getString("zwt_sys_table_interface_path");
			sb.append("��ȡϵͳ-��-�ӿ�·��:"+zwt_sys_table_interface_path+"\n");
			//��ȡ��-�ӿ�-������������·��
			String zwt_table_interface_object_path = rootPath + bundle.getString("zwt_table_interface_object_path");
			sb.append("��ȡ��-�ӿ�-�������·��:"+zwt_sys_table_interface_path+"\n");
			this.writeFile(rootPath+"/page/zwt/json/log.txt", sb.toString());
			
			this.writeFile("E:/workspace_bjdjgz/bjgs_exchange_fb/page/zwt/json/force.json", str_force);
			this.writeFile(zwt_path, str_zwt);
			this.writeFile(zwt_all_path, str_all_zwt);
			this.writeFile(zwt_sys_table_interface_path, str_sys_table_interface);
			this.writeFile(zwt_table_interface_object_path, str_table_interface_object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��׼���õ������ļ�д�뵽ָ���ļ��� ��ʽΪJson�ļ�
	 * 
	 * @param fileName
	 * @param content
	 */
	public void writeFile(String fileName, String content)
	{
		Writer writer;
		logger.info("fileName = " + fileName);
		File file = new File(fileName);
		if (StringUtils.isNotBlank(fileName)) {
			try {
				if (file.exists()) {
					file.delete();
				}
				writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file), "UTF-8"));
				writer.write(content);
				writer.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args)
	{
		// IndexJob job = new IndexJob();
		// job.writeFile("d:\\test.js", "����������������111");

		List all_list = new ArrayList();
		Map map = new HashMap();
		map.put("id", "abc");
		map.put("name", "ccc");
		List list = new ArrayList();
		String[] content = new String[] { "1380729600000", "0", "0", "0" };
		String[] content1 = new String[] { "1380729600000", "1", "0", "0" };
		list.add(content);
		list.add(content1);
		map.put("share_single", list);
		all_list.add(map);
		//System.out.println(JSONUtils.toJSONString(all_list));
	}

	
	/**
	 * ��״ͼ
	 * 
	 * @return
	 * @throws DBException
	 * @throws UnsupportedEncodingException
	 */
	private String getCrossContent() throws DBException, UnsupportedEncodingException
	{
		List crossList = getCrossSql();
	    StringBuffer jsonValue = new StringBuffer("date,delay,distance,origin,destination,ymd\r\n");
		String tempStr = "";
	    for (int i = 0; i < crossList.size(); i++) {
	    	Map t = (Map) crossList.get(i);
	    	tempStr = (String) t.get("UPDATETIME");
	    	
			jsonValue.append(tempStr.substring(5, 7))    //�·�
			.append(tempStr.substring(8, 10))            //����
			.append(tempStr.substring(11, 13))           //Сʱ
			.append("00")                                //����
			.append(",")
			.append(t.get("DELAY")).append(",")
			.append(t.get("DISTANCE")).append(",")
			.append(t.get("ORIGIN")).append(",")
			.append(t.get("DESTINATION")).append(",");
			//����ת��ʱ����Ϊ����״ͼ����������ͼ�У�����չ�ַ�Χ���������ӳ�һ��
			if(i==0){ //�������ݵ�һ��
				tempStr = DateUtil.getTomorrowDate(tempStr);
				//System.out.println("******��һ��****"+tempStr);
				jsonValue.append(tempStr.substring(0, 4)).append(tempStr.substring(5, 7)).append(tempStr.substring(8, 10)); //yyyyMMdd
			}else
			if((i+1)==crossList.size()){ //�����������һ��
				tempStr = DateUtil.getYesterdayDate(tempStr);
				//System.out.println("******�ڶ���****"+tempStr);
				jsonValue.append(tempStr.substring(0, 4)).append(tempStr.substring(5, 7)).append(tempStr.substring(8, 10)); //yyyyMMdd
			}else{
				jsonValue.append(tempStr.substring(0, 4)).append(tempStr.substring(5, 7)).append(tempStr.substring(8, 10)); //yyyyMMdd
			}
			jsonValue.append("\r\n");
			
		}
		
		return jsonValue.toString();
	}
	
	/**
	 * ���������״ͼ����
	 */
	private List getCrossSql() throws DBException
	{
		List crossInterface = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select a.year, a.month||'-'||a.day as md, a.hh, exec_count as delay, sum_record_amount as distance,")
		.append(" (select service_targets_name from res_service_targets where service_targets_id=a.service_targets_id) as origin,")
		.append(" (select service_name from share_service where service_id=a.service_id) as destination,")
		.append(" a.update_time as updatetime")
		.append(" from share_log_statistics a ")
		.append(" where substr(a.update_time,0,10) in ")
		.append( getSixty())
		.append("order by update_time desc");
		//System.out.println("---sql---"+sql.toString());
		crossInterface = operation.select(sql.toString());

		return crossInterface;
	}
	
	private String getSixty(){
		StringBuffer sql = new StringBuffer();
		sql.append("(select md from (select *")
		.append(" from (select to_char(sysdate - 180 + rownum, 'yyyy-mm-dd') md")
		.append(" from dual connect by rownum < 180) where md not in (select t.exception_date")
		.append(" from res_exception_date t) order by md desc)")
		.append(" where rownum <= 60)");
		//System.out.println("---60��---"+sql.toString());
		return sql.toString();
	}

	/**
	 * ����ͼ
	 * 
	 * @return
	 * @throws DBException
	 * @throws UnsupportedEncodingException
	 */

	/**
	 * �����ֲ�ͼ ��ͼ
	 * 
	 * @return
	 */
	private String getServiceSpread()
	{
		return "";
	}

	/**
	 * ����ֲ�ͼ ���� ����ͼ
	 * 
	 * @return
	 */
	private String getShareSpread()
	{
		return "";
	}

	/**
	 * ����ͼ
	 * 
	 * @return
	 */
	private String getHotMap()
	{
		return "";
	}

	/**
	 * �ɼ�����Ա�ͼ
	 * 
	 * @return
	 */
	private String getCollectShare()
	{
		return "";
	}

	

	/**
	 * 
	 * getShareAmount(����-��ȡ���30��Ĺ���������) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getShareAmount(String service_targets_id) throws DBException
	{
		List shareAmount = new ArrayList();
		String sql = "select t1.*from (select t.service_targets_id,t.log_date,sum(t.sum_record_amount) share_amount,count(sum_record_amount) count_num from share_log_statistics t, share_service t0 where t.service_targets_id = t0.service_targets_id and t0.is_single='0'  group by t.service_targets_id,t.log_date) t1,"
				+ " (select * from (select * from (select to_char(sysdate - 59 + rownum-1, 'yyyy-mm-dd') md from dual connect by rownum < 59) where md not in (select t.exception_date from res_exception_date t) order by md desc) where rownum <= 30) t2 "
				+ " where t1.log_date(+)=t2.md and t1.service_targets_id = '"
				+ service_targets_id + "' order by t1.log_date";
		shareAmount = operation.select(sql);

		return shareAmount;
	}

	/**
	 * 
	 * getShareAmountSingle(����-��ȡ���30��Ĺ���������) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param service_targets_id
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	@SuppressWarnings("rawtypes")
	private List getShareAmountSingle(int num, int isSingle) throws DBException
	{
		List shareAmount = new ArrayList();
		StringBuffer sb = new StringBuffer(
				"with a as( select md from (select * from (");
		sb.append("select to_char(sysdate - 180 + rownum, 'yyyy-mm-dd') md from dual ");
		sb.append("connect by rownum < 180) where md not in (select t.exception_date ");
		sb.append(
				"from res_exception_date t) order by md desc) where rownum <= ")
				.append(num);
		sb.append(") select t1.service_targets_id,r.service_targets_name,log_date,share_amount,");
		sb.append("count_num,avg_time from( select st.service_targets_id,st.log_date, ");
		sb.append("sum(st.sum_record_amount) share_amount, sum(st.exec_count) count_num, ");
		sb.append("round(avg(st.avg_consume_time),2) avg_time from share_log_statistics st,");
		sb.append("share_service s ,a where st.service_targets_id = s.service_targets_id and ");
		sb.append("s.is_single = '")
				.append(isSingle)
				.append("' and st.log_date=a.md group by st.service_targets_id, st.log_date)t1,");
		sb.append("res_service_targets r where t1.service_targets_id=r.service_targets_id ");
		sb.append("order by r.show_order,t1.log_date ");
		logger.debug("��ȡȫ����������n�칲������(����/����)sql:" + sb.toString());
		// System.out.println("��ѯ��������:" + sb.toString());
		shareAmount = operation.select(sb.toString());
		return shareAmount;
	}

	/**
	 * 
	 * getShareAmountSingle(����-��ȡ���30��Ĺ���������) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param service_targets_id
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	@SuppressWarnings("rawtypes")
	public List getShareStatByTime(String dateType, String beginTime,
			String endTime, int isSingle) throws DBException
	{
		List shareAmount = new ArrayList();
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isBlank(dateType)){
			dateType = "day";
		}
		if (dateType.equals("day")) {
			sb.append("with a as(select * from(select to_char(to_date('");
			sb.append(beginTime).append(
					"', 'yyyy-MM-dd')+ rownum - 1,'yyyy-mm-dd') md");
			sb.append(" from dual connect by rownum <=(to_date('").append(
					endTime);
			sb.append("', 'yyyy-MM-dd')+1-to_date('").append(beginTime)
					.append("', 'yyyy-MM-dd')))t");
			sb.append(" where t.md not in(select r.exception_date from res_exception_date r )) ");
			sb.append("select t1.service_targets_id,r.service_targets_name,log_date,share_amount,");
			sb.append("count_num,avg_time from( select st.service_targets_id,st.log_date, ");
			sb.append("sum(st.sum_record_amount) share_amount, sum(st.exec_count) count_num, ");
			sb.append("round(avg(st.avg_consume_time),2) avg_time from share_log_statistics st,");
			sb.append("share_service s ,a where st.service_targets_id = s.service_targets_id and ");
			sb.append("st.service_id = s.service_id and s.is_single = '").append(isSingle);
			sb.append("' and st.log_date=a.md group by st.service_targets_id, st.log_date)t1,");
			sb.append("res_service_targets r where t1.service_targets_id=r.service_targets_id ");
			sb.append("order by r.show_order,t1.log_date ");
		}else if("month".equals(dateType)){ 
			sb.append(" with a as( ");
			sb.append("select to_char(add_months(to_date('"+beginTime+"', 'yyyy-MM'), rownum - 1), 'yyyy') y, ");
			sb.append("to_char(add_months(to_date('"+beginTime+"', 'yyyy-MM'), rownum - 1), ");
			sb.append("'mm') m from dual connect by rownum <= ");
			sb.append("(months_between(to_date('"+endTime+"', 'yyyy-MM'), to_date('"+beginTime+"', 'yyyy-MM')) + 1)) ");
			sb.append("select t1.service_targets_id, r.service_targets_name, ");
			sb.append("year || '-' || lpad(month, 2, 0) log_date,  share_amount, ");
			sb.append("count_num,  avg_time ");
			sb.append("from (select st.service_targets_id, ");
			sb.append("st.year, st.month, sum(st.sum_record_amount) share_amount, ");
			sb.append("sum(st.exec_count) count_num, round(avg(st.avg_consume_time), 2) avg_time ");
			sb.append("from share_log_statistics st, share_service s, a ");
			sb.append("where st.service_targets_id = s.service_targets_id ");
			sb.append("and st.service_id = s.service_id and s.is_single = '"+isSingle+"' ");
			sb.append("and st.year = a.Y and st.month = a.m ");
			sb.append("group by st.service_targets_id, st.year, st.month) t1, ");
			sb.append("res_service_targets r ");
			sb.append("where t1.service_targets_id = r.service_targets_id order by show_order, log_date");
		}
		logger.debug("��ȡȫ���������ʱ����ڹ�������(����/����)sql:" + sb.toString());
//		System.out.println("��ѯ��������:" + sb.toString());
		shareAmount = operation.select(sb.toString());
		return shareAmount;
	}
	
	@SuppressWarnings("rawtypes")
	public List getShareStatByTime(Map<String, String> selectKeyMap, int isSingle) throws DBException
	{
		List shareAmount = new ArrayList();
		StringBuffer sb = new StringBuffer();
		String dateType = selectKeyMap.get("queryType");
		String beginTime = selectKeyMap.get("beginTime");
		String endTime = selectKeyMap.get("endTime");
		String service_targets_id = selectKeyMap.get("service_targets_id");
		if (dateType.equals("day")) {
			sb.append("with a as(select * from(select to_char(to_date('");
			sb.append(beginTime).append(
					"', 'yyyy-MM-dd')+ rownum - 1,'yyyy-mm-dd') md");
			sb.append(" from dual connect by rownum <=(to_date('").append(
					endTime);
			sb.append("', 'yyyy-MM-dd')+1-to_date('").append(beginTime)
					.append("', 'yyyy-MM-dd')))t");
			sb.append(" where t.md not in(select r.exception_date from res_exception_date r )) ");
			sb.append("select t1.service_targets_id,r.service_targets_name,log_date,share_amount,");
			sb.append("count_num,avg_time from( select st.service_targets_id,st.log_date, ");
			sb.append("sum(st.sum_record_amount) share_amount, sum(st.exec_count) count_num, ");
			sb.append("round(avg(st.avg_consume_time),2) avg_time from share_log_statistics st,");
			sb.append("share_service s ,a where st.service_targets_id = s.service_targets_id and ");
			sb.append("st.service_id = s.service_id and s.is_single = '").append(isSingle);
			sb.append("' and st.log_date=a.md and st.service_targets_id = '"+service_targets_id+"'" +
					" group by st.service_targets_id, st.log_date) t1, ");
			sb.append("res_service_targets r where t1.service_targets_id=r.service_targets_id ");
			sb.append("order by r.show_order,t1.log_date ");
		}else if("month".equals(dateType)){ 
			sb.append(" with a as( ");
			sb.append("select to_char(add_months(to_date('"+beginTime+"', 'yyyy-MM'), rownum - 1), 'yyyy') y, ");
			sb.append("to_char(add_months(to_date('"+beginTime+"', 'yyyy-MM'), rownum - 1), ");
			sb.append("'mm') m from dual connect by rownum <= ");
			sb.append("(months_between(to_date('"+endTime+"', 'yyyy-MM'), to_date('"+beginTime+"', 'yyyy-MM')) + 1)) ");
			sb.append("select t1.service_targets_id, r.service_targets_name, ");
			sb.append("year || '-' || lpad(month, 2, 0) log_date,  share_amount, ");
			sb.append("count_num,  avg_time ");
			sb.append("from (select st.service_targets_id, ");
			sb.append("st.year, st.month, sum(st.sum_record_amount) share_amount, ");
			sb.append("sum(st.exec_count) count_num, round(avg(st.avg_consume_time), 2) avg_time ");
			sb.append("from share_log_statistics st, share_service s, a ");
			sb.append("where st.service_targets_id = s.service_targets_id ");
			sb.append("and st.service_id = s.service_id and s.is_single = '"+isSingle+"' ");
			sb.append("and st.year = a.Y and st.month = a.m and st.service_targets_id = '"+service_targets_id+"' ");
			sb.append("group by st.service_targets_id, st.year, st.month) t1, ");
			sb.append("res_service_targets r ");
			sb.append("where t1.service_targets_id = r.service_targets_id order by show_order, log_date");
		}
		logger.debug("��ȡȫ���������ʱ����ڹ�������(����/����)sql:" + sb.toString());
		//System.out.println(isSingle + " ��ѯ��������:" + sb.toString());
		shareAmount = operation.select(sb.toString());
		return shareAmount;
	}

	/**
	 * 
	 * getCollectAmount(��ȡ���30��Ĳɼ�����) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������
	 * �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	@SuppressWarnings("rawtypes")
	private List getCollectDataByTime(String dateType, String beginTime,
			String endTime) throws DBException
	{
		long begin = System.currentTimeMillis();
		List collectAmount = new ArrayList();
		StringBuffer sb = new StringBuffer();
		if (dateType.equals("day")) {
			sb.append("with a as(select * from(select to_char(to_date('");
			sb.append(beginTime).append(
					"', 'yyyy-MM-dd')+ rownum,'yyyy-mm-dd') md");
			sb.append(" from dual connect by rownum <=(to_date('").append(
					endTime);
			sb.append("', 'yyyy-MM-dd')-to_date('").append(beginTime)
					.append("', 'yyyy-MM-dd')))t");
			sb.append(" where t.md not in(select r.exception_date from res_exception_date r )) ");
			sb.append("select t1.service_targets_id,r.service_targets_name,log_date,share_amount,");
			sb.append("count_num,avg_time from( select st.service_targets_id,substr(st.task_start_time, 0, 10) log_date, ");
			sb.append("sum(st.collect_data_amount) share_amount, count(st.collect_data_amount) count_num, ");
			sb.append("round(avg(st.task_consume_time),2) avg_time from collect_joumal st,");
			sb.append("a where substr(st.task_start_time, 0, 10)=a.md group by st.service_targets_id, substr(st.task_start_time, 0, 10))t1,");
			sb.append("res_service_targets r where t1.service_targets_id=r.service_targets_id ");
			sb.append("order by r.show_order,t1.log_date ");
		}else if("month".equals(dateType)){
			sb.append("with a as (select to_char(add_months(to_date('"+beginTime+"', 'yyyy-MM'), rownum - 1),'yyyy') ");
			sb.append("|| '-' || to_char(add_months(to_date('"+beginTime+"', 'yyyy-MM'), rownum - 1), ");
			sb.append("'mm') md from dual connect by rownum <= ");
			sb.append("(months_between(to_date('"+endTime+"', 'yyyy-MM'), to_date('"+beginTime+"', 'yyyy-MM')) + 1)) ");
			sb.append("select t1.service_targets_id, r.service_targets_name, log_date, ");
			sb.append("share_amount, count_num, avg_time from (select st.service_targets_id, ");
			sb.append("substr(st.task_start_time, 0, 7) log_date, sum(st.collect_data_amount) share_amount, ");
			sb.append("count(st.collect_data_amount) count_num, round(avg(st.task_consume_time), 2) avg_time ");
			sb.append("from collect_joumal st, a where substr(st.task_start_time, 0, 7) = a.md ");
			sb.append("group by st.service_targets_id, substr(st.task_start_time, 0, 7)) t1, ");
			sb.append("res_service_targets r where t1.service_targets_id = r.service_targets_id ");
			sb.append("order by r.show_order, t1.log_date ");
			System.out.println("month2="+sb.toString());
		}
		logger.debug("��ȡȫ���������ĳʱ��βɼ�����sql:" + sb.toString());
		collectAmount = operation.select(sb.toString());
		logger.debug("��ȡȫ���������ĳʱ��βɼ����ݺ�ʱ:"
				+ (System.currentTimeMillis() - begin));
		return collectAmount;
	}
	
	@SuppressWarnings("rawtypes")
	private List getCollectDataByTime(Map<String, String> selectKeyMap) throws DBException
	{
		long begin = System.currentTimeMillis();
		List collectAmount = new ArrayList();
		StringBuffer sb = new StringBuffer();
		String dateType = selectKeyMap.get("queryType");
		String beginTime = selectKeyMap.get("beginTime");
		String endTime = selectKeyMap.get("endTime");
		String service_targets_id = selectKeyMap.get("service_targets_id");
		if (dateType.equals("day")) {
			sb.append("with a as(select * from(select to_char(to_date('");
			sb.append(beginTime).append(
					"', 'yyyy-MM-dd')+ rownum,'yyyy-mm-dd') md");
			sb.append(" from dual connect by rownum <=(to_date('").append(
					endTime);
			sb.append("', 'yyyy-MM-dd')-to_date('").append(beginTime)
					.append("', 'yyyy-MM-dd')))t");
			sb.append(" where t.md not in(select r.exception_date from res_exception_date r )) ");
			sb.append("select t1.service_targets_id,r.service_targets_name,log_date,share_amount,");
			sb.append("count_num,avg_time from( select st.service_targets_id,substr(st.task_start_time, 0, 10) log_date, ");
			sb.append("sum(st.collect_data_amount) share_amount, count(st.collect_data_amount) count_num, ");
			sb.append("round(avg(st.task_consume_time),2) avg_time from collect_joumal st,");
			sb.append("a where substr(st.task_start_time, 0, 10)=a.md and st.service_targets_id = '"
					+service_targets_id+"' group by st.service_targets_id, substr(st.task_start_time, 0, 10))t1,");
			sb.append("res_service_targets r where t1.service_targets_id=r.service_targets_id ");
			sb.append("order by r.show_order,t1.log_date ");
		}else if("month".equals(dateType)){
			sb.append("with a as (select to_char(add_months(to_date('"+beginTime+"', 'yyyy-MM'), rownum - 1),'yyyy') ");
			sb.append("|| '-' || to_char(add_months(to_date('"+beginTime+"', 'yyyy-MM'), rownum - 1), ");
			sb.append("'mm') md from dual connect by rownum <= ");
			sb.append("(months_between(to_date('"+endTime+"', 'yyyy-MM'), to_date('"+beginTime+"', 'yyyy-MM')) + 1)) ");
			sb.append("select t1.service_targets_id, r.service_targets_name, log_date, ");
			sb.append("share_amount, count_num, avg_time from (select st.service_targets_id, ");
			sb.append("substr(st.task_start_time, 0, 7) log_date, sum(st.collect_data_amount) share_amount, ");
			sb.append("count(st.collect_data_amount) count_num, round(avg(st.task_consume_time), 2) avg_time ");
			sb.append("from collect_joumal st, a where substr(st.task_start_time, 0, 7) = a.md and st.service_targets_id='"
					+service_targets_id+"' ");
			sb.append("group by st.service_targets_id, substr(st.task_start_time, 0, 7)) t1, ");
			sb.append("res_service_targets r where t1.service_targets_id = r.service_targets_id ");
			sb.append("order by r.show_order, t1.log_date ");
		}
		//System.out.print(" collect_sql = " + sb.toString());
		logger.debug("��ȡȫ���������ĳʱ��βɼ�����sql:" + sb.toString());
		collectAmount = operation.select(sb.toString());
		logger.debug("��ȡȫ���������ĳʱ��βɼ����ݺ�ʱ:"
				+ (System.currentTimeMillis() - begin));
		return collectAmount;
	}

	private List getCollectAmount(int num) throws DBException
	{
		List collectAmount = new ArrayList();
		StringBuffer sb = new StringBuffer(
				"with a as( select md from (select * from (");
		sb.append("select to_char(sysdate - 180 + rownum, 'yyyy-mm-dd') md from dual ");
		sb.append("connect by rownum < 180) where md not in (select t.exception_date ");
		sb.append("from res_exception_date t) order by md desc) where rownum <= "
				+ num + ") ");
		sb.append("select t1.service_targets_id,r.service_targets_name,log_date,share_amount,");
		sb.append("count_num,avg_time from( select st.service_targets_id,substr(st.task_start_time, 0, 10) log_date, ");
		sb.append("sum(st.collect_data_amount) share_amount, count(st.collect_data_amount) count_num, ");
		sb.append("round(avg(st.task_consume_time),2) avg_time from collect_joumal st,");
		sb.append("share_service s ,a where st.service_targets_id = s.service_targets_id and ");
		sb.append("substr(st.task_start_time, 0, 10)=a.md group by st.service_targets_id, substr(st.task_start_time, 0, 10))t1,");
		sb.append("res_service_targets r where t1.service_targets_id=r.service_targets_id ");
		sb.append("order by r.show_order,t1.log_date ");
		logger.debug("��ȡȫ����������n��ɼ�����sql:" + sb.toString());
		collectAmount = operation.select(sb.toString());
		return collectAmount;
	}

	/**
	 * 
	 * getServiceObject(��ȡ���з��������Ϣ) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C
	 * ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param service_targets_id
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	private List getServiceObject() throws DBException
	{
		List serviceObject = new ArrayList();
		String sql = " select  m1.service_targets_id,m1.service_targets_no,"
				+ "m1.service_targets_name from res_service_targets m1 where m1.Is_Markup='Y' order by m1.show_order";
		serviceObject = operation.select(sql);

		return serviceObject;
	}
	
	private List getServiceObject(String id) throws DBException
	{
		List serviceObject = new ArrayList();
		String sql = " select  m1.service_targets_id,m1.service_targets_no,"
				+ "m1.service_targets_name from res_service_targets m1 where m1.Is_Markup='Y'" +
				" and m1.service_targets_id = '"+id+"' order by m1.show_order";
		serviceObject = operation.select(sql);

		return serviceObject;
	}

	private String[] getShareVal(Map serviceMap,Map map_time)
	{
		String time=serviceMap.get("LOG_DATE").toString();
		String id = map_time.get(time).toString();
		String share_amount = serviceMap.get("SHARE_AMOUNT").toString();
		String count_num = serviceMap.get("COUNT_NUM").toString();
		String avg_time = serviceMap.get("AVG_TIME").toString();
		String[] share_val = new String[] { id,time, share_amount, count_num,
				avg_time };
		return share_val;
	}

	/**
	 * 
	 * getShareCollectData(��ȡ�ɼ�����������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������
	 * �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @return String
	 * @throws ParseException
	 * @throws DBException 
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getShareCollectData() throws ParseException, DBException
	{
		logger.info("��ʼ������ҳ�ɼ�����ͳ�Ʒ�������");
		long begin = System.currentTimeMillis();
		String str = "";
		List serviceObject = new ArrayList();
		List lst_time=this.getTimeString("day", "", "");
		List list_temp = new ArrayList();
		Map map_time=new HashMap();
		for (int i = 0; i < lst_time.size(); i++) {
			Map time_map = new HashMap();
			Map map = (Map) lst_time.get(i);
			time_map.put("id", map.get("NID")+"");
			time_map.put("md", map.get("MD"));
			list_temp.add(time_map);
			map_time.put(map.get("MD"), map.get("NID"));
		}
		try {
			serviceObject = getServiceObject();
			List all_list = new ArrayList();
			// ��ȡ�������ݵ���
			List List_single = getShareAmountSingle(30, 1);
			// ��ȡ������������
			List List_group = getShareAmountSingle(30, 0);
			// ��ȡ�ɼ�����
			List list_collect = getCollectAmount(30);
			for (int i = 0; i < serviceObject.size(); i++) {
				Map objMap = (Map) serviceObject.get(i);
				String id = objMap.get("SERVICE_TARGETS_ID").toString();
				List share_single = new ArrayList();
				List share_group = new ArrayList();
				List collect = new ArrayList();
				String[] trend = new String[] {};
				Map map = new HashMap();
				map.put("id", id);
				map.put("name", objMap.get("SERVICE_TARGETS_NAME"));
				// �����������ݵ���
				for (int j = 0; j < List_single.size(); j++) {
					Map singleMap = (Map) List_single.get(j);
					if (id.equals(singleMap.get("SERVICE_TARGETS_ID")
							.toString())) {
						share_single.add(getShareVal(singleMap,map_time));
					}
				}
				// ����������������
				for (int j = 0; j < List_group.size(); j++) {
					Map groupMap = (Map) List_group.get(j);
					if (id.equals(groupMap.get("SERVICE_TARGETS_ID").toString())) {
						share_group.add(getShareVal(groupMap,map_time));
					}
				}
				// �����ɼ�����
				for (int j = 0; j < list_collect.size(); j++) {
					Map collectMap = (Map) list_collect.get(j);
					if (id.equals(collectMap.get("SERVICE_TARGETS_ID")
							.toString())) {
						collect.add(getShareVal(collectMap,map_time));
					}
				}
				map.put("share_single", share_single);
				map.put("share", share_group);
				map.put("collect", collect);
				map.put("trend", trend);
				all_list.add(map);
			}

			str = JSONUtils.toJSONString(all_list);
//			System.out.println("��ʱ:" + (System.currentTimeMillis() - begin));
			logger.info("������ҳ�ɼ�����ͳ�Ʒ������ݽ���,��ʱ:"
					+ (System.currentTimeMillis() - begin));

		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("11str=" + str);
		return str;
	}
	
	private String getMonth(String beginMonth, String endMonth){
		StringBuffer sql = new StringBuffer();
		sql.append("with a as (")
		.append(" select to_char(add_months(to_date('").append(beginMonth).append("', 'yyyy-MM'), rownum-1),'yyyy') y,")
		.append(" to_char(add_months(to_date('").append(beginMonth).append("', 'yyyy-MM'), rownum-1), 'mm') m  from dual ")
		.append(" connect by rownum <=(months_between(to_date('").append(endMonth).append("', 'yyyy-MM'),")
		.append("  to_date('").append(beginMonth).append("', 'yyyy-MM'))+1) )");
		//System.out.println("---�²�---"+sql.toString());
		return sql.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public List getShareByMonth(int isSingle) throws DBException
	{
		List shareAmount = new ArrayList();
		StringBuffer sb = new StringBuffer();

		sb.append("select t1.service_targets_id, r.service_targets_name, year, month, share_amount, count_num, avg_time ");
		sb.append("from (select st.service_targets_id, st.year, st.month, sum(st.sum_record_amount) share_amount, ");
		sb.append("sum(st.exec_count) count_num, round(avg(st.avg_consume_time), 2) avg_time ");
		sb.append("from share_log_statistics st, share_service s, a ");
		sb.append("where st.service_targets_id = s.service_targets_id  ");
		sb.append("and st.service_id = s.service_id and s.is_single = '").append(isSingle).append("'  ");
		sb.append("and st.year = a.Y and st.month = a.m ");
		sb.append("group by st.service_targets_id, st.year, st.month) t1,  ");
		sb.append("res_service_targets r  ");
		sb.append("where t1.service_targets_id = r.service_targets_id  ");
		sb.append("order by r.show_order, t1.year, t1.month  ");
		
		logger.debug("��ȡȫ���������ʱ����ڹ�������(����/����)sql:" + sb.toString());
//		System.out.println("��ѯ��������------------:" + sb.toString());
		shareAmount = operation.select(sb.toString());
		return shareAmount;
	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTimeString(String dateType, String beginTime,
			String endTime) throws DBException
	{
		StringBuffer sb = new StringBuffer();
		if ("day".equals(dateType)) {
			sb.append("select rownum nid,md from(select to_char(to_date('");
			sb.append(beginTime).append(
					"', 'yyyy-MM-dd')+ rownum -1,'yyyy-MM-dd') md");
			sb.append(" from dual connect by rownum <=(to_date('").append(
					endTime);
			sb.append("', 'yyyy-MM-dd')+1-to_date('").append(beginTime)
					.append("', 'yyyy-MM-dd')))t");
			sb.append(" where t.md not in(select r.exception_date from res_exception_date r )");
		} else if ("month".equals(dateType)) {
			sb.append("select rownum nid, to_char(add_months(to_date('"+beginTime+"', 'yyyy-MM'), rownum - 1), ");
			sb.append("'yyyy') || '-' ||to_char(add_months(to_date('"+beginTime+"', 'yyyy-MM'), rownum - 1), 'mm') md ");
			sb.append("from dual connect by rownum <= (months_between(to_date('"+endTime+"', 'yyyy-MM'), ");
			sb.append("to_date('"+beginTime+"', 'yyyy-MM')) + 1) ");
//			System.out.println("month\n"+sb.toString());
		}
		// return sb.toString();
		List lst_time = operation.select(sb.toString());
		return lst_time;
	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getShareCollectDataStr(String query_type, String beginTime,
			String endTime,Map map_time) 
	{
		logger.info("��ȡ������ҳ�ɼ�����ͳ�Ʒ�������");
		long begin = System.currentTimeMillis();
		String str = "";
		List serviceObject = new ArrayList();
		try {
			serviceObject = getServiceObject();
			List all_list = new ArrayList();
			// ��ȡ�������ݵ���
			List List_single = getShareStatByTime(query_type, beginTime,
					endTime, 1);
			// ��ȡ������������
			List List_group = getShareStatByTime(query_type, beginTime,
					endTime, 0);
			// ��ȡ�ɼ�����
			List list_collect = getCollectDataByTime(query_type, beginTime,
					endTime);
			for (int i = 0; i < serviceObject.size(); i++) {
				Map objMap = (Map) serviceObject.get(i);
				String id = objMap.get("SERVICE_TARGETS_ID").toString();
				List share_single = new ArrayList();
				List share_group = new ArrayList();
				List collect = new ArrayList();
				String[] trend = new String[] {};
				Map map = new HashMap();
				map.put("id", id);
				map.put("name", objMap.get("SERVICE_TARGETS_NAME"));
				// �����������ݵ���
				for (int j = 0; j < List_single.size(); j++) {
					Map singleMap = (Map) List_single.get(j);
					if (id.equals(singleMap.get("SERVICE_TARGETS_ID")
							.toString())) {
						share_single.add(getShareVal(singleMap,map_time));
					}
				}
				// ����������������
				for (int j = 0; j < List_group.size(); j++) {
					Map groupMap = (Map) List_group.get(j);
					if (id.equals(groupMap.get("SERVICE_TARGETS_ID").toString())) {
						share_group.add(getShareVal(groupMap,map_time));
					}
				}
				// �����ɼ�����
				for (int j = 0; j < list_collect.size(); j++) {
					Map collectMap = (Map) list_collect.get(j);
					if (id.equals(collectMap.get("SERVICE_TARGETS_ID")
							.toString())) {
						collect.add(getShareVal(collectMap,map_time));
					}
				}
				map.put("share_single", share_single);
				map.put("share", share_group);
				map.put("collect", collect);
				map.put("trend", trend);
				all_list.add(map);
			}

			str = JSONUtils.toJSONString(all_list);
			System.out.println("��ʱ:" + (System.currentTimeMillis() - begin));
			logger.info("��ȡ�ɼ�����ͳ�Ʒ������ݽ���,��ʱ:"
					+ (System.currentTimeMillis() - begin));

		} catch (DBException e) {
			e.printStackTrace();
		}
	//	System.out.println("str=" + str);
		return str;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getShareCollectDataStr(Map<String, String> selectKeyMap, Map map_time) 
	{
		logger.info("��ȡ������ҳ�ɼ�����ͳ�Ʒ�������");
		long begin = System.currentTimeMillis();
		String str = "";
		/**/
		System.out.println("KeyMap = "+selectKeyMap);
		List serviceObject = new ArrayList();
		try {
			String svrId=selectKeyMap.get("service_targets_id");
			serviceObject = getServiceObject(svrId);
			List all_list = new ArrayList();
			// ��ȡ�������ݵ���
			List List_single = getShareStatByTime(selectKeyMap, 1);
			// ��ȡ������������
			List List_group = getShareStatByTime(selectKeyMap, 0);
			// ��ȡ�ɼ�����
			List list_collect = getCollectDataByTime(selectKeyMap);
			for (int i = 0; i < serviceObject.size(); i++) {
				Map objMap = (Map) serviceObject.get(i);
				String id = objMap.get("SERVICE_TARGETS_ID").toString();
				List share_single = new ArrayList();
				List share_group = new ArrayList();
				List collect = new ArrayList();
				String[] trend = new String[] {};
				Map map = new HashMap();
				map.put("id", id);
				map.put("name", objMap.get("SERVICE_TARGETS_NAME"));
				// �����������ݵ���
				for (int j = 0; j < List_single.size(); j++) {
					Map singleMap = (Map) List_single.get(j);
					if (id.equals(singleMap.get("SERVICE_TARGETS_ID")
							.toString())) {
						share_single.add(getShareVal(singleMap,map_time));
					}
				}
				// ����������������
				for (int j = 0; j < List_group.size(); j++) {
					Map groupMap = (Map) List_group.get(j);
					if (id.equals(groupMap.get("SERVICE_TARGETS_ID").toString())) {
						share_group.add(getShareVal(groupMap,map_time));
					}
				}
				// �����ɼ�����
				for (int j = 0; j < list_collect.size(); j++) {
					Map collectMap = (Map) list_collect.get(j);
					if (id.equals(collectMap.get("SERVICE_TARGETS_ID")
							.toString())) {
						collect.add(getShareVal(collectMap,map_time));
					}
				}
				map.put("share_single", share_single);
				map.put("share", share_group);
				map.put("collect", collect);
				map.put("trend", trend);
				all_list.add(map);
			}

			str = JSONUtils.toJSONString(all_list);
			System.out.println("��ʱ:" + (System.currentTimeMillis() - begin));
			logger.info("��ȡ�ɼ�����ͳ�Ʒ������ݽ���,��ʱ:"
					+ (System.currentTimeMillis() - begin));

		} catch (DBException e) {
			e.printStackTrace();
		}
	//	System.out.println("str=" + str);
		return str;
	}
	
}
