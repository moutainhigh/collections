package cn.gwssi.trs.service;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.gwssi.resource.CacheUtile;
import cn.gwssi.resource.Conts;
import cn.gwssi.resource.DateUtil;
import cn.gwssi.resource.PageDataUtil;
import cn.gwssi.trs.model.Node;
import cn.gwssi.trs.model.TTaskMeasuretohtmlBO;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class TrsService extends BaseService{
	
	private static  Logger log=Logger.getLogger(TrsService.class);
	
	/**
	 * 市场主体 检索
	 * @param pages
	 * @param queryKeyWord
	 * @param labelFlag
	 * @param arg
	 * @return
	 * @throws OptimusException
	 */
	public Map querydata(String pages,String queryKeyWord,String labelFlag,String... arg) throws OptimusException{
		Map map=null;
		PageDataUtil pageData=new PageDataUtil();
		if(pages!=null){
			map=pageData.pagesData(Integer.parseInt(pages),Conts.pageSize,queryKeyWord,labelFlag);
		}
		return map;
	}
	
	
	/**
	 * 12315 检索
	 * @param pages
	 * @param queryKeyWord
	 * @param labelFlag
	 * @param arg
	 * @return
	 * @throws OptimusException
	 */
	public Map queryAjdata(String pages,String queryKeyWord,String labelFlag,String... arg) throws OptimusException{
		Map map=null;
		PageDataUtil pageData=new PageDataUtil();
		if(pages!=null){
			map=pageData.pagesAjData(Integer.parseInt(pages),Conts.pageSize,queryKeyWord,labelFlag);
		}
		return map;
	}
	
	
	/**
	 * 年度报告 检索
	 * @param pages
	 * @param queryKeyWord
	 * @param labelFlag
	 * @param arg
	 * @return
	 * @throws OptimusException
	 */
	public Map queryAnnualdata(String pages,String queryKeyWord,String labelFlag,String... arg) throws OptimusException{
		Map map=null;
		PageDataUtil pageData=new PageDataUtil();
		if(pages!=null){
			map=pageData.pagesAnnualData(Integer.parseInt(pages),Conts.pageSize,queryKeyWord,labelFlag);
		}
		return map;
	}
	
	
	/**
	 * 案件信息 检索
	 * @param pages
	 * @param queryKeyWord
	 * @param labelFlag
	 * @param arg
	 * @return
	 * @throws OptimusException
	 */
	public Map queryCaseData(String pages,String queryKeyWord,String labelFlag,String... arg) throws OptimusException{
		Map map=null;
		PageDataUtil pageData=new PageDataUtil();
		if(pages!=null){
			map=pageData.pagesCaseData(Integer.parseInt(pages),Conts.pageSize,queryKeyWord,labelFlag);
		}
		return map;
	}
	
	//@PostConstruct
	public void updatetaskmesure() throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		Date date=new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
	//	StringBuffer sb=new StringBuffer("select t2.valuesum,t2.regorg as regorg,t1.valueioc from (select distinct sum(value) valuesum,a.regorg,a.measure from T_TASK_MEASURE a  group by a.regorg,a.MEASURE  having a.MEASURE='2' ) t2 left join (select distinct sum(value) valueioc,a.regorg,a.measure from T_TASK_MEASURE a  group by a.regorg,a.MEASURE  having a.MEASURE='1') t1 on t2.regorg=t1.regorg where t1.TRANSDT='"+date+"'");
		StringBuffer sb=new StringBuffer("select t2.valuesum as valuesum,t2.regorg as regorg,t1.valueioc as valueioc from (select distinct sum(value) valuesum,a.regorg,a.measure from T_TASK_MEASURE a  group by a.regorg,a.MEASURE  having a.MEASURE='2' ) t2 left join (select distinct sum(value) valueioc,a.regorg,a.measure from T_TASK_MEASURE a  group by a.regorg,a.MEASURE  having a.MEASURE='1') t1 on t2.regorg=t1.regorg");
		//List<Map> taskmesuredata=dao.queryForList(sb.toString(), null);
		List<TTaskMeasuretohtmlBO> taskmesuredata = dao.queryForList(TTaskMeasuretohtmlBO.class, sb.toString(), null);
		for(int i=0;i<taskmesuredata.size();i++){
				/*String valuesum=(String) taskmesuredata.get(i).get("valuesum");
				String regorg=(String) taskmesuredata.get(i).get("regorg");
				String valueioc=(String) taskmesuredata.get(i).get("valueioc");*/
				//TRANSDT,REGORG ,VALUEIOC,VALUEMISSION, VALUESUM
				//StringBuffer sbf=new StringBuffer("insert into T_TASK_MEASURETOHTML(TRANSDT,REGORG,VALUEIOC,VALUESUM) values('");
				/*	sbf.append("'"+dateString+"',");
					sbf.append("'"+regorg+"',");
					sbf.append("'"+valueioc+"',");
					sbf.append("'"+valuesum+"')");*/
					//dao.execute(sbf.toString(), null);
			taskmesuredata.get(i).setTransdt(dateString);
			dao.update(taskmesuredata.get(i));
			
		}
	}
			
	//@PostConstruct
	public List<Map> selectMapData() throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO("iqDataSource");
		Date date=new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		//dateString= "2016-11-28";
		List<String> params = new ArrayList<String>();
		params.add(dateString);
		/*String sql=new String();
		sql="select mapname,mapdata from t_pt_provincemap";
		List<Map> listprovince=dao.queryForList(sql, null);*/
		List<Map> listprovince=CacheUtile.getMapSpotLinkedList();
		
		List<Map<String,Object>> objList = new ArrayList<Map<String,Object>>();
		for (Map.Entry<String, String> entry : CacheUtile.getCityCodeLinkedHashMap().entrySet()) {
			Map<String,Object> objMap = new HashMap<String,Object>();
			objMap.put("transdt", dateString);
			objMap.put("regorg", entry.getKey());
			objMap.put("valueioc", 0L);
			objMap.put("valuesum", 0L);
			objMap.put("valuemission", 0L);
			objList.add(objMap);
		}
		Map<String,Object> getSumSJMap = new HashMap<String,Object>();
		getSumSJMap.put("transdt", dateString);
		getSumSJMap.put("regorg", "440000");
		getSumSJMap.put("valueioc", 0L);
		getSumSJMap.put("valuesum", 0L);
		getSumSJMap.put("valuemission", 0L);
		
		//获取 期末户数 //transdt 时间、REGORG登记机关，value 值
		StringBuffer sb = new StringBuffer("select convert(varchar(30),transdt) transdt,regorg,value from T_REG_ENTRY_EXIT where MEASURE='001001' and transdt=?");//"+dateString+"
		List<Map> list=dao.queryForList(sb.toString(), params);
		
		objList = getEndDaus(objList,list,1);
		//统计省局
		getSumSJMap = getSumSJ(getSumSJMap,list,1);
		//StringBuffer sb=new StringBuffer("select distinct TRANSDT as transdt,REGORG as  regorg,convert(varchar(20),VALUEIOC) valueioc,convert(varchar(20),VALUEMISSION) valuemission, convert(varchar(20),VALUESUM) valuesum from T_TASK_MEASURETOHTML where transdt=?");//"+dateString+"
		//在办=期末业务量 
		sb = new StringBuffer("select transdt,regorg,accepttype,measure,value  from T_TASK_MEASURE where measure='2' and transdt=?");//"+dateString+"
		list=dao.queryForList(sb.toString(), params);
		objList = getEndDaus(objList,list,2);
		//统计省局
		getSumSJMap = getSumSJ(getSumSJMap,list,2);
		
		// 昨日=本期业务量
		sb = new StringBuffer("select transdt,regorg,accepttype,measure,value  from T_TASK_MEASURE where measure='1' and accepttype='03' and transdt=?");//"+dateString+"
		list=dao.queryForList(sb.toString(), params);
		objList = getEndDaus(objList,list,3);
		//统计省局
		getSumSJMap = getSumSJ(getSumSJMap,list,3);
		
		listprovince.add(getSumSJMap);
		for(int i=0;i<objList.size();i++){
			listprovince.add(objList.get(i));
		}
		/*Map<String,Object> objMap = new HashMap<String,Object>();
		objMap.put("transdt", dateString);
		objMap.put("regorg", "440100");
		objMap.put("valueioc", 10L);
		objMap.put("valuesum", 10L);
		objMap.put("valuemission", 10L);
		objList.add(objMap);
		listprovince.add(objMap);
		
		objMap = new HashMap<String,Object>();
		objMap.put("transdt", dateString);
		objMap.put("regorg", "440300");
		objMap.put("valueioc", 20L);
		objMap.put("valuesum", 20L);
		objMap.put("valuemission", 20L);
		objList.add(objMap);
		listprovince.add(objMap);*/
		return listprovince;
	}
	
	private Map getSumSJ(Map objMap,List<Map> list, int type) {
		if(list!=null && list.size()>0){
			switch (type) {
			case 1:
				for(Map<String,Object> resultMap : list){
					String regorg = (String) resultMap.get("regorg");
					String regorgSub = regorg!=null?regorg.substring(0,4):regorg;
					if("4400".equals(regorgSub)){//省局
						if(!"440003".equals(regorg)){
							objMap.put("valuesum", (Long)objMap.get("valuesum")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
						}
					}
				}
				break;
			case 2:
				for(Map<String,Object> resultMap : list){
					String regorg = (String) resultMap.get("regorg");
					String regorgSub = regorg.substring(0,4);
					if("4400".equals(regorgSub)){//省局
						if(!"440003".equals(regorg)){
							objMap.put("valuemission", (Long)objMap.get("valuemission")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
						}
					}
				}
				break;
			case 3:
				for(Map<String,Object> resultMap : list){
					String regorg = (String) resultMap.get("regorg");
					String regorgSub = regorg.substring(0,4);
					if("4400".equals(regorgSub)){//省局
						if(!"440003".equals(regorg)){
							objMap.put("valueioc", (Long)objMap.get("valueioc")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
						}
					}
				}
				break;
			default:
				break;
			}
		}
		return objMap;
	}

	/**
	 * 
	 * @param result key
	 * @param list  
	 * @param type
	 * @return
	 */
	private List<Map<String,Object>> getEndDaus(List<Map<String,Object>> result,List<Map> list,int type) {
		if(list!=null && list.size()>0){
			for(Map<String,Object> objMap : result){
				String key = (String) objMap.get("regorg");
				switch (type) {
				case 1://regorg,value  //期末实有户数 valuesum
					String keySub = key.substring(0,4);
					for(Map<String,Object> resultMap : list){
						String regorg = (String) resultMap.get("regorg");
						String regorgSub = regorg!=null?regorg.substring(0,4):regorg;
						if(keySub.equals(regorgSub)){//存在
							//sortMap.put("440606","顺德区");440003","横琴新区/","珠海市/440400","佛山市 440600
							objMap.put("valuesum", (Long)objMap.get("valuesum")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
							/*if("440400".equals(key) || "440600".equals(key)){//如果是 珠海市或者佛山市，
								if(!"440003".equals(key) && !"440606".equals(key)){//排除顺德区和横琴新区
									objMap.put("valuesum", (Long)objMap.get("valuesum")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
								}//setBigDecimal
							}else{
								objMap.put("valuesum", (Long)objMap.get("valuesum")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
							}*/
						}
						if("440400".equals(key)){//如果是珠海
							if("440003".equals(regorg)){//如果是横琴新区 加入珠海
								objMap.put("valuesum", (Long)objMap.get("valuesum")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
							}
						}
					}
					break;
				case 2://在办业务量 valuemission
					for(Map<String,Object> resultMap : list){
						String regorg = (String) resultMap.get("regorg");
						if(key.equals(regorg)){//存在
							//sortMap.put("440606","顺德区");440003","横琴新区/440400","珠海市/440600","佛山市
							/*if("440400".equals(key) || "440600".equals(key)){//如果是 珠海市或者佛山市，
								if(!"440003".equals(key) && !"440606".equals(key)){//排除顺德区和横琴新区
									objMap.put("valuemission", (Long)objMap.get("valuemission")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
								}
							}else{
								objMap.put("valuemission", (Long)objMap.get("valuemission")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
							}*/
							objMap.put("valuemission", (Long)objMap.get("valuemission")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
						}
						if("440400".equals(key)){//如果是珠海
							if("440003".equals(regorg)){//如果是横琴新区 加入珠海
								objMap.put("valuemission", (Long)objMap.get("valuemission")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
							}
						}
					}
					break;
				case 3://昨日申办量 valueioc
					for(Map<String,Object> resultMap : list){
						String regorg = (String) resultMap.get("regorg");
						if(key.equals(regorg)){//存在
							//sortMap.put("440606","顺德区");440003","横琴新区/440400","珠海市/440600","佛山市
							/*if("440400".equals(key) || "440600".equals(key)){//如果是 珠海市或者佛山市，
								if(!"440003".equals(key) && !"440606".equals(key)){//排除顺德区和横琴新区
									objMap.put("valueioc", (Long)objMap.get("valueioc")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
								}
							}else{
								objMap.put("valueioc", (Long)objMap.get("valueioc")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
							}*/
							objMap.put("valueioc", (Long)objMap.get("valueioc")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
						}
						if("440400".equals(key)){//如果是珠海
							if("440003".equals(regorg)){//如果是横琴新区 加入珠海
								objMap.put("valueioc", (Long)objMap.get("valueioc")+Long.parseLong(DateUtil.df.format((BigDecimal)resultMap.get("value"))));
							}
						}
					}
					break;	
				default:
					break;
				}
			}
		}
		return result;
	}
		
	// 把数据库中blob类型转换成String类型
	public String convertBlobToString(Blob blob) {
		String result = "";
		try {
			ByteArrayInputStream msgContent = (ByteArrayInputStream) blob
					.getBinaryStream();
			byte[] byte_data = new byte[msgContent.available()];
			msgContent.read(byte_data, 0, byte_data.length);
			result = new String(byte_data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 高级人员
	 * @param pripid
	 * @param sourceflag
	 * @param type
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findGG(String pripid,String sourceflag,String type) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("select a.gjcyxxid pripid,a.sourceflag,b.name from T_SCZT_GJCYXX a, t_sczt_ryjbxx b where a.personid = b.ryjbxxid and a.SOURCEFLAG=b.SOURCEFLAG");
		sbf.append(" and a.pripid='").append(pripid).append("'");
		sbf.append(" and a.sourceflag='").append(sourceflag).append("'");
		List<Map> datalist=dao.queryForList(sbf.toString(),null);
		return datalist;
	}
	
	/**
	 * 法人
	 * @param pripid
	 * @param sourceflag
	 * @param type
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findFR(String pripid,String sourceflag,String type) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("select a.pripid,a.sourceflag,a.inv from t_sczt_frtzrjczxx a where 1=1");
		sbf.append(" and a.pripid='").append(pripid).append("'");
		sbf.append(" and a.sourceflag='").append(sourceflag).append("'");
		List<Map> datalist=dao.queryForList(sbf.toString(),null);
		return datalist;
	}
	
	/**
	 * 自然人
	 * @param pripid
	 * @param sourceflag
	 * @param type
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findZR(String pripid,String sourceflag,String type) throws OptimusException{
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("select a.pripid,a.sourceflag,a.inv from t_sczt_zrrtzrjczxx a where 1=1");
		sbf.append(" and a.pripid='").append(pripid).append("'");
		//sbf.append(" and a.sourceflag='").append(sourceflag).append("'");
		List<Map> datalist=dao.queryForList(sbf.toString(),null);
		return datalist;
	}
	
	/**
	 * 族谱查询
	 * CREATE TABLE dbo.T_PANORAMIC_ANALYSIS_ZPJSON
(
    pripid VARCHAR(50) , --节点编码
    dataformat TEXT, --节点名称
    CONSTRAINT PK_T_PANORAMIC_ANALYSIS_ZPJSON
    PRIMARY KEY CLUSTERED  (pripid)
)
	 * @param pripid
	 * @param sourceflag
	 * @param entname
	 * @param type
	 * @return
	 * @throws OptimusException
	 */
	public String selectGenealogyData(String pripid, String sourceflag,String entname, String enttype,int findGen) throws OptimusException {
		StringBuffer sbf = new StringBuffer("select pripid,dataformat from T_PANORAMIC_ANALYSIS_ZPJSON where pripid=?");
		List<String> params = new ArrayList<String>();
		if(StringUtils.isNotBlank(pripid)){
			params.add(pripid);
		}
		IPersistenceDAO dao= this.getPersistenceDAO();
		List<Map> result=dao.queryForList(sbf.toString(), params);
		String resultStr = "";
		if(result!=null && result.size()>0){
			resultStr = (String)(result.get(0).get("dataformat"));
		}
		return resultStr;
	}
	
	public void insertGenealogyData(String pripid, String resultStr) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		List<String> params = new ArrayList<String>();
		params.add(pripid);
		dao.execute("delete from T_PANORAMIC_ANALYSIS_ZPJSON where pripid=?", params);
		params.add(resultStr);
		dao.execute("insert into T_PANORAMIC_ANALYSIS_ZPJSON(pripid,dataformat)values(?,?)", params);
	}
	
	/**
	 * 族谱查询
	 * @param pripid
	 * @param sourceflag
	 * @param entname
	 * @param type
	 * @return外接节点类型1:法人;3:自然人;2:当前企业;4:高级人员
	 * @throws OptimusException
	 */
	public String selectGenealogyDdata(String pripid, String sourceflag1,String entname, String enttype,int findGen) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO("iqDataSource");
		Stack<Node> nodeStack = new Stack<Node>();
		List<String> drawedNode = new ArrayList<String>();
		List<String> drawedLink = new ArrayList<String>();
		List<String> nodeQueue = new ArrayList<String>();
		List<String> linkQueue = new ArrayList<String>();
		String curPripid = null, curName = null, curType = null, curEntType = null, curNodeGen = null, outPripid = null, outName = null, outType = null, isSon = null, nodeEntType = null, nodeGen = null, sql = null, dest = null;
		//int findGen = 4; 查找的族谱代数
		Node queryNode = new Node(pripid, entname, "2", enttype, "0");// 节点id 节点名称 节点类型 节点企业类型 节点代数
		Node curNode = null, newNode = null;
		nodeStack.add(queryNode);
		int i =0;
		Random random = new Random();
		long startTime = new Date().getTime();
		while (!nodeStack.isEmpty()) {
			i=i+5;
			curNode = (Node) nodeStack.pop();
			curPripid = curNode.getNodeId();
			curName = curNode.getNodeName();
			curType = curNode.getNodeType();
			curEntType = curNode.getNodeEntType();
			curNodeGen = curNode.getNodeGen();
			nodeGen = String.valueOf(Integer.parseInt(curNodeGen, 10) + 1);
			sql = "select outpripid,outname,outtype,isson,enttype from T_PANORAMIC_ANALYSIS_ZP where pripid='"+ curPripid + "'";
			System.out.println(sql);
			List<Map> resultData = dao.queryForList(sql, null);
			if (resultData != null && resultData.size() > 0) {
				for (Map m : resultData) {
					outPripid = (String) m.get("outpripid");
					outName = (String) m.get("outname");
					outType = (String) m.get("outtype");
					isSon = (String) m.get("isson");
					nodeEntType = (String) m.get("enttype");
					if (Integer.parseInt(curNodeGen, 10) < findGen&&drawedNode.indexOf(outPripid)==-1){
						newNode = new Node(outPripid, outName, outType,nodeEntType, nodeGen);
						nodeStack.add(newNode);
					}
					if (isSon.equals("0")) {
						if (drawedLink.indexOf(curPripid + outPripid) == -1) {
							linkQueue.add("{\"source\":\"" + curPripid
									+ "\",\"target\":\"" + outPripid +"\",\"weight\":\""+random.nextInt(5)+ "\"}");
							drawedLink.add(curPripid + outPripid);
						}
					} else {
						if (drawedLink.indexOf(outPripid + curPripid) == -1) {
							linkQueue.add("{\"source\":\"" + outPripid
									+ "\",\"target\":\"" + curPripid +"\",\"weight\":\""+random.nextInt(5)+ "\"}");
							drawedLink.add(outPripid + curPripid);
						}
					}
				}
			}
			if (drawedNode.indexOf(curPripid) == -1) {
				if (curName != null) {
					Pattern p = Pattern.compile("\\s*|\t|\r|\n");
					Matcher m = p.matcher(curName);
					dest = m.replaceAll("");
				} else
					dest = null;
				nodeQueue.add("{\"pripid\":\"" + curPripid + "\",\"name\":\""
						+ dest + "\",\"category\":\"" + curType + "\",\"value\":\"" + random.nextInt(50)+ 
								"\"}");
				drawedNode.add(curPripid);
			}
		}
		log.info("查询iq花费时间："+(new Date().getTime()-startTime));
		/** 打印json文件 **/
		StringBuffer resultStr = new StringBuffer("{");
		resultStr.append("    \"nodes\":");
		resultStr.append("        [");
		System.out.println(nodeQueue.size());
		log.info("查询iq花费大小："+nodeQueue.size());
		while (!nodeQueue.isEmpty()) {
			resultStr.append("            " + nodeQueue.get(0));
			nodeQueue.remove(0);
			if (!nodeQueue.isEmpty()){
				resultStr.append(",");
			}else{
				resultStr.append("");
			}
		}
		resultStr.append("        ],");
		resultStr.append("    \"links\":");
		resultStr.append("        [");
		log.info("查询iq花费大小："+linkQueue.size());
		//log.info(linkQueue.size());
		while (!linkQueue.isEmpty()) {
			resultStr.append("            " + linkQueue.get(0));
			linkQueue.remove(0);
			if (!linkQueue.isEmpty()){
				resultStr.append(",");
			}else{
				resultStr.append("");
			}
		}
		resultStr.append("        ]");
		resultStr.append("}");
		log.info(resultStr.toString());
		return resultStr.toString();
	}
	
	public String selectGenealogyDdataNew1(String pripid,String entname, String allPripid,int findGen) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO("iqDataSource");
		if(StringUtils.isBlank(allPripid)){
			return "{    \"nodes\":        [        ],   \"links\":        [        ]}";
		}
		if(allPripid.contains(",")){
			allPripid = allPripid.substring(0,allPripid.length()-1);
		}
		//findGen = findGen+4;
		int tab  = (findGen+4)*10;
		StringBuffer sbf = new StringBuffer("select gid,pkidname,pripid,name,outpripid,outname,outtype,isson,enttype from T_PANORAMIC_ANALYSIS_ZP where pripid in (").append(allPripid);//.append("); and outpripid not in(").append(allPripid).append("");
		sbf.append(")");
		System.out.println(sbf.toString());
		List<Map> resultData = dao.queryForList(sbf.toString(), null);
		
		Map<String,String> chartNmae = new  HashMap<String,String>();
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher mc = null;
		String dest="";
		Map<String,String> arrowNmae = new  HashMap<String,String>();
		if(resultData!=null && resultData.size()>0){
			Random random = new Random();
			StringBuffer sbfArrow = new StringBuffer();
			for(Map<String,String> m : resultData){
				if (m.get("outname") != null) {
					mc = p.matcher(m.get("outname"));
					dest = mc.replaceAll("");
				} else {
					dest = null;
				}
				sbf = new StringBuffer("{\"pripid\":\"").append(m.get("outpripid"));
				sbf.append("\",\"name\":\"").append(dest);
				sbf.append("\",\"category\":\"").append(m.get("outtype"));
				sbf.append("\",\"value\":\"").append(random.nextInt(tab)).append("\"}");
				chartNmae.put(m.get("outpripid"), sbf.toString());
				
				/*if(m.get("pripid").equals(pripid)){
					sbfArrow = new StringBuffer("{\"source\":\"").append(m.get("outpripid"));
					sbfArrow.append("\",\"target\":\"").append(m.get("pripid"));
					sbfArrow.append("\",\"weight\":\"").append(random.nextInt(findGen)).append("\"}");
				}else{
					sbfArrow = new StringBuffer("{\"source\":\"").append(m.get("pripid"));
					sbfArrow.append("\",\"target\":\"").append(m.get("outpripid"));
					sbfArrow.append("\",\"weight\":\"").append(random.nextInt(findGen)).append("\"}");
				}*/
				sbfArrow = new StringBuffer("{\"source\":\"").append(m.get("outpripid"));
				sbfArrow.append("\",\"target\":\"").append(m.get("pripid"));
				sbfArrow.append("\",\"weight\":\"").append(random.nextInt(findGen)).append("\"},");
				
				sbfArrow.append("{\"source\":\"").append(m.get("pripid"));
				sbfArrow.append("\",\"target\":\"").append(m.get("outpripid"));
				sbfArrow.append("\",\"weight\":\"").append(random.nextInt(findGen)).append("\"}");
				
				/*sbfArrow = new StringBuffer("{\"source\":\"").append(m.get("pripid"));
				sbfArrow.append("\",\"target\":\"").append(m.get("outpripid"));
				sbfArrow.append("\",\"weight\":\"").append(random.nextInt(5)).append("\"}");*/
				
				arrowNmae.put(m.get("outpripid")+m.get("pripid"), sbfArrow.toString());
				
			}
			if(allPripid.replaceAll("'", "").equals(pripid)){
				sbf = new StringBuffer("{\"pripid\":\"").append(pripid);
				sbf.append("\",\"name\":\"").append(entname);
				sbf.append("\",\"category\":\"").append(2);
				sbf.append("\",\"value\":\"").append(random.nextInt(50)).append("\"}");
				chartNmae.put(pripid, sbf.toString());
			}
			//去除双线
			/*sbf = new StringBuffer("select pripid,outpripid from T_PANORAMIC_ANALYSIS_ZP where pripid ='").append(pripid).append("'");
			System.out.println(sbf.toString());
			resultData = dao.queryForList(sbf.toString(), null);
			if(resultData!=null && resultData.size()>0){
				for(Map<String,String> m : resultData){
					//arrowNmae.remove(m.get("pripid")+m.get("outpripid"));
				}
			}*/
		}
		
		/** 打印json文件 **/
		StringBuffer resultStr = new StringBuffer("{");
		resultStr.append("    \"nodes\":");
		resultStr.append("        [");
		int i =0;
		System.out.println(chartNmae.size());
		for (Map.Entry<String, String> entry : chartNmae.entrySet()) { 
			i++;
			if(i==chartNmae.size()){
				resultStr.append(entry.getValue()).append("");
			}else{
				resultStr.append(entry.getValue()).append(",");
			}
		}  
		
		resultStr.append("        ],");
		resultStr.append("    \"links\":");
		resultStr.append("        [");
		i=0;
		for (Map.Entry<String, String> entry : arrowNmae.entrySet()) { 
			i++;
			if(i==arrowNmae.size()){
				resultStr.append(entry.getValue()).append("");
			}else{
				resultStr.append(entry.getValue()).append(",");
			}
		} 
		resultStr.append("        ]");
		resultStr.append("}");
		log.info(resultStr.toString());
		return resultStr.toString();
	}
	
	public String selectGenealogyDdataNew(String pripid,String entname, String allPripid) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO("iqDataSource");
		StringBuffer sbf = new StringBuffer("select gid,pkidname,pripid,name,outpripid,outname,outtype,isson,enttype from T_PANORAMIC_ANALYSIS_ZP where pripid in (").append(allPripid);//.append("); and outpripid not in(").append(allPripid).append("");
		sbf.append(")");
		System.out.println(sbf.toString());
		List<Map> resultData = dao.queryForList(sbf.toString(), null);
		
		Map<String,String> chartNmae = new  HashMap<String,String>();
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher mc = null;
		String dest="";
		Map<String,String> arrowNmae = new  HashMap<String,String>();
		if(resultData!=null && resultData.size()>0){
			Random random = new Random();
			StringBuffer sbfArrow = new StringBuffer();
			for(Map<String,String> m : resultData){
				if (m.get("outname") != null) {
					mc = p.matcher(m.get("outname"));
					dest = mc.replaceAll("");
				} else {
					dest = null;
				}
				sbf = new StringBuffer("{\"pripid\":\"").append(m.get("outpripid"));
				sbf.append("\",\"name\":\"").append(dest);
				sbf.append("\",\"category\":\"").append(m.get("outtype"));
				sbf.append("\",\"value\":\"").append(random.nextInt(50)).append("\"}");
				chartNmae.put(m.get("outpripid"), sbf.toString());
				
				if(m.get("pripid").equals(pripid)){
					sbfArrow = new StringBuffer("{\"source\":\"").append(m.get("outpripid"));
					sbfArrow.append("\",\"target\":\"").append(m.get("pripid"));
					sbfArrow.append("\",\"weight\":\"").append(random.nextInt(5)).append("\"}");
				}else{
					sbfArrow = new StringBuffer("{\"source\":\"").append(m.get("pripid"));
					sbfArrow.append("\",\"target\":\"").append(m.get("outpripid"));
					sbfArrow.append("\",\"weight\":\"").append(random.nextInt(5)).append("\"}");
				}
				
				
				arrowNmae.put(m.get("outpripid")+m.get("pripid"), sbfArrow.toString());
				
			}
			sbf = new StringBuffer("{\"pripid\":\"").append(pripid);
			sbf.append("\",\"name\":\"").append(entname);
			sbf.append("\",\"category\":\"").append(2);
			sbf.append("\",\"value\":\"").append(random.nextInt(50)).append("\"}");
			chartNmae.put(pripid, sbf.toString());
			
			//去除双线
			sbf = new StringBuffer("select pripid,outpripid from T_PANORAMIC_ANALYSIS_ZP where pripid ='").append(pripid).append("'");
			System.out.println(sbf.toString());
			resultData = dao.queryForList(sbf.toString(), null);
			if(resultData!=null && resultData.size()>0){
				for(Map<String,String> m : resultData){
					arrowNmae.remove(m.get("pripid")+m.get("outpripid"));
				}
			}
		}
		
		/** 打印json文件 **/
		StringBuffer resultStr = new StringBuffer("{");
		resultStr.append("    \"nodes\":");
		resultStr.append("        [");
		int i =0;
		for (Map.Entry<String, String> entry : chartNmae.entrySet()) { 
			i++;
			if(i==chartNmae.size()){
				resultStr.append(entry.getValue()).append("");
			}else{
				resultStr.append(entry.getValue()).append(",");
			}
		}  
		
		resultStr.append("        ],");
		resultStr.append("    \"links\":");
		resultStr.append("        [");
		i=0;
		for (Map.Entry<String, String> entry : arrowNmae.entrySet()) { 
			i++;
			if(i==arrowNmae.size()){
				resultStr.append(entry.getValue()).append("");
			}else{
				resultStr.append(entry.getValue()).append(",");
			}
		} 
		resultStr.append("        ]");
		resultStr.append("}");
		log.info(resultStr.toString());
		return resultStr.toString();
	}


	public List<Map> selectScztDetail(String pripid,String name,String category,boolean falg) throws OptimusException {
		StringBuffer sql=new StringBuffer("");//1:法人;3:自然人;2:当前企业;4:高级人员
		if(falg){
			if("0".equals(category)){//法人
				sql.append(Conts.T_SCZT_FRTZRJCZXX).append(" where a.tzrjczxxid='").append(pripid).append("'");
			}else if("2".equals(category)){//自然人
				sql.append(Conts.T_SCZT_ZRTZRJCZXX).append(" where c.cerno='").append(pripid).append("'");
			}
		}else{
			switch (category) {
			case "0"://法人企业
				sql.append(Conts.SCZTJBXXSQLWZ);
				sql.append(" where a.pripid='").append(pripid).append("'");
				break;
			case "1"://当前企业
				sql.append(Conts.SCZTJBXXSQLWZ).append(" where a.pripid='").append(pripid).append("'");
				break;
			case "2"://高级人员
				sql.append(Conts.T_SCZT_RYXX).append(" where b.cerno='").append(pripid).append("'");
				break;
			default:
				break;
			}
		}
		IPersistenceDAO dao= this.getPersistenceDAO();
		log.info("查询语句："+sql.toString());
		return dao.queryForList(sql.toString(), null);
	}

	public Map<String, Object> selectShowTab(String type) throws OptimusException {
		Map map=new LinkedHashMap();
		String sql=null;
		IPersistenceDAO dao= this.getPersistenceDAO();
		if(type!=null && type.trim()!=""){
			if("2".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '3'";
			}else if("3".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '5' order by sort";
			}else if("1".equals(type) || "4".equals(type) || "5".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '1' order by sort";
			}else if("6".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '6' order by sort";
			}else if( "7".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '7' order by sort";
			}else if( "9".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '4'  order by sort";
			}else if( "0".equals(type)){
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '2' order by sort";
			}else if( "50".equals(type)){//高级人员
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '50' order by sort";
			}else if( "51".equals(type)){//法人
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '51' order by sort";
			}else if( "52".equals(type)){//自然人
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '52' order by sort";
			}else{
				sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '1' order by sort";
			}
		}else{
			sql="select * from T_SCZT_JBXXINHTML where state='1' and type = '1' order by sort";
		}
		List<Map> datalist=dao.queryForList(sql.toString(), null);
		for(int i=0;i<datalist.size();i++){
			Map<String,String> m=datalist.get(i);
			map.put(m.get("fieldcn"), m.get("fieldeng"));
		}
		return map;
	}
	
	public String findTime() throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		List<Map> params = dao.queryForList("select timestamp from T_AJ_XZCFJDS_TIME",null);
		Map<String,String> m = new HashMap<String,String>();
		String o = "";
		if(params!=null &&  params.size()>0){
			m = params.get(0);
			if(m!=null && m.size()>0){
				o = m.get("timestamp");
			}
		}
		return o;
	}
	
	public List<Map> findPunish(String timestamp,String nowTime) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		
		List<String> params =new ArrayList<String>();
		StringBuffer sbf = new StringBuffer("select a.penorg,a.Pendecno,a.IllegPt,c.regno,c.enttype,c.dom,c.Lerep,c.regcap,c.estdate,c.opfrom,c.opto,c.opscope,a.Illegact,b.quabasis,a.illegacttype,a.penbasis,a.Penresult from T_AJ_GSXZCFJDS a left join T_AJ_XZCFJDS b on  a.caseid=b.caseno left join t_sczt_scztjbxx c on a.entno=c.Pripid");
		if(StringUtils.isNotBlank(timestamp)){//如果不为空，就找这个时间以后的
			sbf.append(" where b.timestamp>=? and b.timestamp<?");
			params.add(timestamp);
		}else{//如果不为空，就找所有数据
			sbf.append(" where b.timestamp<?");
		}
		params.add(nowTime);
		
		System.out.println("sql语句："+sbf.toString());
		return dao.queryForList(sbf.toString(), params);
		//2016-11-11 09:56:12Date date=new Date();
		/*params.add("2016-11-11");//昨天
		params.add("2016-11-12");//今天
*/		/*params.add(DateUtil.getYesterday(date));//昨天
		params.add(DateUtil.dateToString(date));*///今天where a.timestamp>=? and a.timestamp<?
		//select top 100 estdate from t_sczt_scztjbxx where convert(varchar(10),estdate,105)='2003-11-14'//t_dm_qylxdm
		/*StringBuffer sbf = new StringBuffer("select top 1 a.penorg,a.Pendecno,a.IllegPt,c.regno,c.enttype,c.dom,c.Lerep,c.regcap,c.estdate,c.opfrom,c.opto,c.opscope,a.Illegact,b.quabasis,a.illegacttype,a.penbasis,a.Penresult from T_AJ_GSXZCFJDS a left join T_AJ_XZCFJDS b on  a.caseid=b.caseno left join t_sczt_scztjbxx c on a.entno=c.Pripid where a.penaltyno='015bbdfc-0157-1000-e000-95830a0c0115'");
		return dao.queryForList(sbf.toString(), null);*/
	}
	
	public List<Map> findPunish() throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		StringBuffer sbf = new StringBuffer("select a.caseid,a.penorg,a.Pendecno,a.IllegPt,c.regno,c.enttype,c.dom,c.Lerep,convert(numeric(14,2),c.regcap) regcap,substring(c.estdate,1,10),c.opfrom,c.opto,c.opscope,a.Illegact,b.quabasis,a.illegacttype,a.penbasis,a.Penresult from T_AJ_GSXZCFJDS a(INDEX T_AJ_GSXZCFJDS_INDEX) left join T_AJ_XZCFJDS b on  a.caseid=b.caseno left join t_sczt_scztjbxx c on a.entno=c.Pripid");
		System.out.println("sql语句："+sbf.toString());
		return dao.queryForList(sbf.toString(), null);
	}
	
	public void update(String timestamp,String nowTime) throws OptimusException {
		IPersistenceDAO dao= this.getPersistenceDAO();
		if(StringUtils.isNotBlank(timestamp)){
			dao.execute("update T_AJ_XZCFJDS_TIME set timestamp='"+nowTime+"'", null);
		}else{
			dao.execute("insert into T_AJ_XZCFJDS_TIME(timestamp)values('"+nowTime+"')", null);
		}
	}
}
