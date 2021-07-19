package com.gwssi.dw.aic.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.dw.runmgr.etl.vo.VoFirstPageQuery;

public class SearchServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5006966500047247520L;
	public static final String DB_CONFIG = "app";
	private static final String SEARCH_HOME = "searchHome";
	private static final String DB_CONNECT_TYPE = "dbConnectionType";
	
	private static String getConnectType(){
		return java.util.ResourceBundle.getBundle(DB_CONFIG).getString(DB_CONNECT_TYPE);
	}
	
	private static String getSearchHome(){
		return java.util.ResourceBundle.getBundle(DB_CONFIG).getString(SEARCH_HOME);
	}
	
	public static String dbType = getConnectType();
	public static String searchHome = getSearchHome();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try {
			//��¼������־
			VoUser user = (VoUser)request.getSession().getAttribute("oper-data");
			VoFirstPageQuery queryData = new VoFirstPageQuery();
			queryData.setFirst_cls("ȫ�ļ���");
			queryData.setSecond_cls("ȫ�ļ���");
			queryData.setCount("1");
			queryData.setNum("0");
			String date = CalendarUtil.getCalendarByFormat(CalendarUtil.FORMAT11);
			String times = CalendarUtil.getCalendarByFormat(CalendarUtil.FORMAT7);
			queryData.setQuery_date(date);
			queryData.setQuery_time(times);
			queryData.setUsername(user.getUserName());
			queryData.setOpername(user.getOperName());
			queryData.setOrgid(user.getOrgCode());
			queryData.setOrgname(user.getOrgName());
			queryData.setIpaddress(user.getValue("ipaddress"));
			StringBuffer sql = new StringBuffer("insert into first_page_query( first_page_query_id, first_cls, second_cls, count, num, query_date, query_time, username, opername, orgid, orgname, ipaddress) values(");
			sql.append("'").append(UuidGenerator.getUUID()).append("',")
			.append("'ȫ�ļ���',")
			.append("'ȫ�ļ���',")
			.append("1,")
			.append("0,")
			.append("'").append(CalendarUtil.getCalendarByFormat(CalendarUtil.FORMAT11)).append("',")
			.append("'").append(CalendarUtil.getCalendarByFormat(CalendarUtil.FORMAT7)).append("',")
			.append("'").append(user.getUserName()).append("',")
			.append("'").append(user.getOperName()).append("',")
			.append("'").append(user.getOrgCode()).append("',")
			.append("'").append(user.getOrgName()).append("',")
			.append("'").append(user.getValue("ipaddress")).append("')");
			System.out.println("SQL>>>>>>>>>>>"+sql);
//			TxnContext context = new TxnContext();
//			BaseTable table =  TableFactory.getInstance().getTableObject(this, "first_page_query");
//			table.executeFunction("insert into one first_page_query", context, queryData,"queryData");
			DBOperation operation = DBOperationFactory.createOperation();
			operation.execute(sql.toString(), false);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		long before = System.currentTimeMillis();
		String query = request.getParameter("query");
		String page = request.getParameter("page");
		if (page == null){
			page = "1";
		}
//		System.out.println("ֱ�ӵõ���query:" + query);
		
		String queryString = "collId=1,2,3,4,5,6,7,8,9,10,11,12&rows=10&nPage=" + page + 
			"&query=" + java.net.URLEncoder.encode(query, "gbk");
		
//		System.out.println("queryString:" + queryString);
		URL urlstr = new URL(searchHome + "search?" + queryString);
		URLConnection urlConnection = urlstr.openConnection();
		urlConnection.setRequestProperty("content-type",
				"application/x-www-form-urlencoded;charset=GBK");
		urlConnection.setDoOutput(true);

		urlConnection.connect();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream()));
		String html = null;
		response.setContentType("text/xml;charset=gbk");
		String strDoc = "";
		System.out.println("��ʼ��ȡSOCKETЭ����ʱ��" + (System.currentTimeMillis() - before) + "����!");
		while ((html = in.readLine()) != null) {
			if (html != null && html.trim().length() > 0){
				strDoc += html + "\n";
			}
		}
		System.out.println("��ȡSOCKETЭ����ʱ��" + (System.currentTimeMillis() - before) + "����!");
		strDoc = strDoc.replaceFirst("^\\s+", "");
//		System.out.println(strDoc);
		String rootPath = request.getContextPath();
		Document doc = null;
		try {
			doc = parseXml(rootPath, DocumentHelper.parseText(strDoc));
		} catch (DocumentException e) {
			e.printStackTrace();
			doc = DocumentHelper.createDocument();
			doc.setRootElement(DocumentHelper.createElement("Results"));
			doc.setXMLEncoding("GBK");
//			System.out.println(doc.asXML());
		}
		PrintWriter out = response.getWriter();
		out.write(doc.asXML());
		out.close();
		in.close();
		System.out.println("ȫ��ִ����ʱ��" + (System.currentTimeMillis() - before) + "����!");
	}
	
	/**
	 * �����ɱ��⣬���Ӻ��������Ƶ�xml
	 * @param doc
	 * @return
	 */
	private Document parseXml(String rootPath, Document doc){
		long before = System.currentTimeMillis();
		String trimRegexp = "(\\<font\\s*color=red\\>)|(\\<\\/font\\>)";
		List items = doc.selectNodes("//Item");
		// �õ���ҵ�б�
		String[] regBusEntIdArray = new String[items.size()];
		for (int i = 0; i < items.size(); i++){
			Element el = (Element)items.get(i);
			String reg_bus_ent_id = el.selectSingleNode("reg_bus_ent_id").getText();
			if (reg_bus_ent_id != null && reg_bus_ent_id.trim().length() > 0){
				// ����ID�п����йؼ��ֵ�������滻"<font color=red>", "</font>" Ϊ��
				reg_bus_ent_id = reg_bus_ent_id.replaceAll(trimRegexp, "");
				regBusEntIdArray[i] = reg_bus_ent_id.trim();
			}
		}
		// �õ���ҵ��������Ϣ
		List dataList = getDataMap(regBusEntIdArray);
		System.out.println("������һ��ʱ�䣺" + (System.currentTimeMillis() - before) + "����");
		
		for (int i = 0; i < items.size(); i++){
			Element el = (Element)items.get(i);
			String reg_bus_ent_id = el.selectSingleNode("reg_bus_ent_id").getText();
			if (reg_bus_ent_id == null){
				continue;
			}
			reg_bus_ent_id = reg_bus_ent_id.replaceAll(trimRegexp, "");
			reg_bus_ent_id = reg_bus_ent_id.trim();
			// System.out.println("reg_bus_ent_id:" + reg_bus_ent_id);
			
			Element url = DocumentHelper.createElement("url");
			Element title = DocumentHelper.createElement("title");
			
			// �����Ƿ��Ѿ���ѯ������ҵ���ƣ����û�У�����������ȥ
			Element el_entName = (Element) el.selectSingleNode("ent_name");
			String ent_name = el_entName.getText();
			if (ent_name == null || (ent_name.trim().length() == 0)){
				String titleStr = getNameById(reg_bus_ent_id, dataList);
				title.setText(titleStr);
			}else{// ���ent_name��Ϊ�գ�����ent_name��Ϊtitle
				title.setText(ent_name);
			}
			
			
			
			StringBuffer sbContent = new StringBuffer();
			List itemContents = el.selectNodes("DetailInfo/*");
			for (int j = 0; j < itemContents.size(); j++){
				Element e = (Element)itemContents.get(j);
				String text = e.getText();
				if (text != null && text.trim().length() > 0){
					String attrValue = e.attribute("desc").getText();
					sbContent.append( attrValue + "��" + text + "\t");
				}
			}
			Element content = DocumentHelper.createElement("content");
			
			
			String txnCode = getUrl(dataList, reg_bus_ent_id);
			if (txnCode != null){
				url.setText(rootPath + txnCode);
				content.setText(sbContent.toString());
			}else{
				// ���ѹ�����ӵ���������ȥ
				// title.setText(title.getText() + "(�ѹ���)");
				content.setText("<font color=gray>�����ݽ��շ����仯������ղ�ѯ</font><br />" + sbContent.toString());
				url.setText("#");
			}
			
			el.add(url);
			el.add(title);
			el.add(content);
			el.remove(el.selectSingleNode("DetailInfo"));
		}
		System.out.println("����ִ��ʱ�䣺" + (System.currentTimeMillis() - before) + " ����");
		return doc;
	}
	
	/**
	 * �õ�����ǰ׺
	 * @return
	 */
	private static String getUrl(List dataList, String reg_bus_ent_id){
		String ent_sort = getEntSortById(reg_bus_ent_id, dataList);
		String ent_name = getNameById(reg_bus_ent_id, dataList);
		String reg_no = getRegNoById(reg_bus_ent_id, dataList);
		// ����Ϊ���壬���壬���ƣ��Ⲻ������
		if (ent_sort == null || ent_sort.trim().equals("")){
			return null;
		}
		ent_sort = ent_sort.trim();
		if (ent_sort.equals("GT")){
			return "/txn60110008.do?primary-key:reg_bus_ent_id=" + reg_bus_ent_id;
		}else if(ent_sort.equals("HP")){
			return "/dw/aic/bj/reg/txy/main_txy.jsp?select-key:entid=" + reg_bus_ent_id
				+ "&select-key:ent_name=" + ent_name 
				+ "&select-key:reg_no=" + reg_no;
		}else if(ent_sort.equals("FQ")){
			return "/txn60114003.do?select-key:exc_que_reg_id=" + reg_bus_ent_id;
		}else if(ent_sort.equals("WB")){
			return "/txn60111002.do?select-key:reg_bus_ent_oc_id=" + reg_bus_ent_id;
		}else if(ent_sort.equals("AJ")){//����ǰ��������
			return "/txn60130004.do?primary-key:case_bus_case_id=" + reg_bus_ent_id;
		}else{
			return "/txn60110001.do?primary-key:reg_bus_ent_id=" + reg_bus_ent_id;
		}
	}
	
	/**
	 * ����ID�õ�Name
	 * @param reg_indiv_base_id
	 * @param dataList
	 * @return
	 */
	private static String getNameById(String reg_indiv_base_id, List dataList){
		for (int i = 0 ; i < dataList.size(); i++){
			Map map = (Map) dataList.get(i);
			String id = (String) map.get("ID");
			if (id != null && id.equalsIgnoreCase(reg_indiv_base_id)){
				return (String) map.get("NAME");
			}
		}
		return "δ����";
	}
	
	/**
	 * ������ҵID�õ���ҵ����
	 * @param reg_indiv_base_id
	 * @param dataList
	 * @return
	 */
	private static String getEntSortById(String reg_indiv_base_id, List dataList){
		for (int i = 0 ; i < dataList.size(); i++){
			Map map = (Map) dataList.get(i);
			String id = (String) map.get("ID");
			if (id != null && id.equalsIgnoreCase(reg_indiv_base_id)){
				return (String) map.get("ENT_SORT");
			}
		}
		return null;
	}
	
	/**
	 * �õ���ҵע���
	 * @param reg_indiv_base_id
	 * @param dataList
	 * @return
	 */
	private static String getRegNoById(String reg_indiv_base_id, List dataList){
		for (int i = 0 ; i < dataList.size(); i++){
			Map map = (Map) dataList.get(i);
			String id = (String) map.get("ID");
			if (id != null && id.equalsIgnoreCase(reg_indiv_base_id)){
				return (String) map.get("REG_NO");
			}
		}
		return "";
	}
	
	/**
	 * �õ������������б�
	 * @param dataArray
	 * @return
	 */
	private static List getDataMap(String[] dataArray){
		String inString = arrayToSqlString(dataArray);
		if (inString.equals("''")){
			return new ArrayList();
		}
		
		String sql = "select reg_bus_ent_id as id, ent_name as name,reg_no,ent_sort from v_reg_bus_ent"
			+ " where reg_bus_ent_id in (" + inString + ")"
			+ " union all "
			+ "select case_bus_case_id as id, name, '' as reg_no,'AJ' as ent_sort from case_bus_case_new"
			+ " where case_bus_case_id in (" + inString + ")";
//		System.out.println("sql:" + sql);
		List list = null;
		try {
			list = DbUtils.select(sql, dbType);
		} catch (DBException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * ��������SQL����е�in�ַ���
	 * @param array
	 * @return
	 */
	private static String arrayToSqlString(String[] array){
		String result = "'";
		for (int i = 0; i < array.length; i++){
			if (array[i] == null || array[i].trim().length() == 0){
				continue;
			}
			result += array[i];
			if (i!=array.length-1){
				result += "','";
			}
		}
		result += "'";
		return result;
	}
}
