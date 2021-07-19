//package com.gwssi.rodimus.sms.service.unicom;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.w3c.dom.Document;
//import org.w3c.dom.NamedNodeMap;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import com.gwssi.rodimus.sms.service.SmsSendService;
//import com.gwssi.rodimus.sms.service.unicom.support.SMSClient;
//import com.gwssi.rodimus.sms.service.unicom.support.SMSPortType;
//import com.gwssi.rodimus.sms.service.unicom.support.SMSUtils;
//import com.gwssi.optimus.core.exception.OptimusException;
//
///**
// * 实际执行短信发送的接口。
// * 
// * @author liuhailong
// */
//public class SmsSendServiceUnicomImpl implements SmsSendService{
//	
//	public void send(String mobile, String content) throws OptimusException {
//		Map<String,String> sms = new HashMap<String,String>();
//		send(sms);
//	}
//
//	public void send(Map<String, String> smsList) throws OptimusException {
//		//生成短信XML
//		String xmlContent = this.buildSendXML(smsList);
//		//发送短信
//		//String reportXML  = 
//		sendSMS(xmlContent);
//		
//		//解析短信回执
//		//Map<String,Map<String, String>> xmlMap = reportToMap(reportXML);
//	}
//	
//	
//	
//	protected static final Logger logger = Logger.getLogger(SMSUtils.class);
//	
//	protected static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
//	
//	protected final static String SMS_URL = null ;
//	protected static final String SMS_VALIDATION = null;
//	protected static final String SMS_BID = null;
//	
//	protected SimpleDateFormat sf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//	
//	static {
////    	Properties props = ConfigManager.getProperties("ems");
////        SMS_URL = props.getProperty("sms.url");
////        SMS_VALIDATION = props.getProperty("sms.validation");
////        SMS_BID = props.getProperty("sms.bid");
//    }
//	
//	/**
//	 * 按规则生成sms_id
//	 * @param i
//	 * @return
//	 */
//	private String getSmsId(int i){
//		return SMS_BID + i;
//	}
//	
//	/**
//	 * 生成发送XML。
//	 * 
//	 * Map的key为移动电话码，value为短信内容。
//	 * 
//	 * @param contentList
//	 * @return
//	 */
//	protected String buildSendXML(Map<String,String> smsList) {
//		if(smsList==null || smsList.size()==0){
//			logger.debug("获取到的短信内容为空");
//			return null;
//		}
//		
//		//String date = sf.format(new Date());
//		StringBuilder strXML = new StringBuilder();
//	    strXML.append(XML_HEAD);
//	    strXML.append("<SMS type=\"send\">");
//	    
//	    int idx = 1;
//	    String mobile,content;
//	    for(Map.Entry<String,String> sms : smsList.entrySet()){
//	    	mobile = sms.getKey();
//	    	content = sms.getValue();
//	    	if(StringUtils.isBlank(mobile)){
//	    		logger.debug("没有移动电话码。");
//	    		continue;
//	    	}
//	    	String smsId=getSmsId(idx);
//	    	strXML.append("<Message SmsID=\"" )
//				.append(smsId)//按规则生成
//				.append("\" Bid=\"" )
//				.append(SMS_BID)
//				.append("\" RecvNum=\"" )
//				.append(mobile)
//				.append("\" Content=\"" )
//				.append(content)
//				.append("\" />");
//	    	++idx;
//	    }
//	    strXML.append("</SMS>");
//	    logger.debug("短信XML:"+strXML.toString());
//		return strXML.toString();
//	}
//	
//	/**
//	 * 发送短信。
//	 * 
//	 * 发送指定格式XML，调用WebService发送短信。
//	 * @param msgXML
//	 * @return
//	 * @throws OptimusException
//	 */
//	protected String sendSMS(String msgXML)throws OptimusException{
//
//        SMSClient client = new SMSClient(SMS_URL);
//        SMSPortType service = client.getSMSHttpPort();
//                
//        String sms = null;
//        try {
//        	sms = service.addSMSList(SMS_VALIDATION, msgXML);
//            if("success".equals(sms)){
//            	//发送成功
//            }
//		} catch (Exception e) {
//			throw new OptimusException("发送短信失败"+e.getMessage());
//		}
//        String report=null;
//        try {
//        	report = service.getReportSMSList(SMS_VALIDATION, SMS_BID);
//		} catch (Exception e) {
//			throw new OptimusException("获取短信回执失败："+e.getMessage());
//		}
//        
//        return report;
//	}
//	
//	
//	/**
//	 * 解析短信回执xml。
//	 * 
//	 * 将xml转换成Map，key为smsId，value为Message内容的Map对象。
//	 * @param xml
//	 * @return
//	 * @throws OptimusException
//	 */
//	public Map<String,Map<String,String >> reportToMap(String xml)throws OptimusException{
//		Map<String,Map<String,String >> reportsMap = new HashMap<String,Map<String,String >>();
//		try {
//			
//			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//			InputStream in = new ByteArrayInputStream (xml.getBytes("utf-8"));
//			Document doc = docBuilder.parse(in);
//			Node root = doc.getFirstChild();
//			String strType = root.getAttributes().getNamedItem("type").getTextContent();
//			
//			if (!strType.equalsIgnoreCase("report")) {
//				reportsMap = null;
//			    return reportsMap;
//			}	
//			NodeList nodeList = root.getChildNodes();
//			for(int i = 0;i<nodeList.getLength();i++){
//				Node node = nodeList.item(i);
//				if(node.getNodeName().equalsIgnoreCase("Message")){
//					Map<String,String> tmp = new HashMap<String,String>();
//					NamedNodeMap attrList = node.getAttributes();
//					String strSmsID = attrList.getNamedItem("SmsID").getTextContent();
//					String strSendNum = attrList.getNamedItem("SendNum").getTextContent();
//					String bid = attrList.getNamedItem("Bid").getTextContent();
//					String strReportTime = attrList.getNamedItem("ReportTime").getTextContent();
//					String strStatus = attrList.getNamedItem("Status").getTextContent();
//					//tmp.put("SmsID", strSmsID);
//					tmp.put("SendNum", strSendNum);
//					tmp.put("bid", bid);
//					tmp.put("ReportTime", strReportTime);
//					tmp.put("Status", strStatus);
//					reportsMap.put(strSmsID, tmp);
//				}
//			}
//		
//			
//		} catch (Exception e) {
//			throw new OptimusException("解析回执出错："+e.getMessage());
//		}
//		return reportsMap;
//	}
//
//	/**
//	 * 将短信记录插入到短信数据表中
//	 * @param list
//	 * @param reportMap
//	 * @throws OptimusException
//	 */
////	@SuppressWarnings({ "rawtypes", "unchecked" })
////	public void insertSMS(List<Map> list,Map<String,Map<String, String>> reportMap)throws OptimusException{
////		if(list==null || list.size()==0){
////			return ;
////		}
////		List<SmsRecordBO> boList = new ArrayList<SmsRecordBO>();
////		int len = list.size();
////		for(int i=0;i<len;i++){
////			SmsRecordBO bo = new SmsRecordBO();
////			Map<String,String> tmp = list.get(i);
////			String smsId = tmp.get("smsId");
////			Map<String, String>report = reportMap.get(smsId);
////			if(report==null){
////				continue;
////			}
////			bo.setSmsId(smsId);
////			bo.setBid(report.get("bid"));
////			bo.setContent("");
////			bo.setGid(tmp.get("requisitionId"));
////			bo.setRecvNum(tmp.get("mobTel"));
////			bo.setRepeat("0");
////			bo.setReportTime(report.get("ReportTime"));
////			bo.setReqState(tmp.get("state"));
////			bo.setSendTime(tmp.get("sendTime"));
////			bo.setStatus(report.get("Status"));
////			boList.add(bo);
////		}
////		DAOManager.getPersistenceDAO().insert(boList);
////		logger.debug("插入"+len+"条短信记录");
////	}
//}
