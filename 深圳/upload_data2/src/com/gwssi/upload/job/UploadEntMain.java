package com.gwssi.upload.job;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwssi.upload.mapper.MaxTableTimeMapper;
import com.gwssi.upload.pojo.AfficheSpotcheck;
import com.gwssi.upload.pojo.AnBaseinfo;
import com.gwssi.upload.pojo.AoOpaDetail;
import com.gwssi.upload.pojo.AoOpanomalyInv;
import com.gwssi.upload.pojo.CaseCfBaseinfo;
import com.gwssi.upload.pojo.CaseCfIrregpunishinfo;
import com.gwssi.upload.pojo.CaseCfPartyinfo;
import com.gwssi.upload.pojo.CaseCfTrans;
import com.gwssi.upload.pojo.EAlterRecoder;
import com.gwssi.upload.pojo.EBaseinfo;
import com.gwssi.upload.pojo.EContact;
import com.gwssi.upload.pojo.EFiSupl;
import com.gwssi.upload.pojo.EFinLeader;
import com.gwssi.upload.pojo.EImInvestment;
import com.gwssi.upload.pojo.EImInvprodetail;
import com.gwssi.upload.pojo.EImInvsralt;
import com.gwssi.upload.pojo.EImPermit;
import com.gwssi.upload.pojo.EImPrmtalt;
import com.gwssi.upload.pojo.EInvInvestment;
import com.gwssi.upload.pojo.ELicCertifica;
import com.gwssi.upload.pojo.ELpHstleref;
import com.gwssi.upload.pojo.ELpHstname;
import com.gwssi.upload.pojo.EMoveIn;
import com.gwssi.upload.pojo.EMoveOut;
import com.gwssi.upload.pojo.ENameInfo;
import com.gwssi.upload.pojo.EOtCase;
import com.gwssi.upload.pojo.EPriPerson;
import com.gwssi.upload.pojo.ESmBaseinfo;
import com.gwssi.upload.pojo.MaxTableTime;
import com.gwssi.upload.service.FindReaderService;
import com.gwssi.upload.service.InsertEntInfoService;
import com.gwssi.upload.utils.HttpClientService;

import sun.util.logging.resources.logging;

@Component
@Transactional
public class UploadEntMain {
	private static final Logger logger = Logger.getLogger(UploadEntMain.class);

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Autowired
	private MaxTableTimeMapper maxTableTimeMapper;

	@Autowired
	private InsertEntInfoService insertEntInfoService; // 将数据写入到数据库中

	@Autowired
	private HttpClientService httpClientService; // 采用httpClient请求

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String URL = "http://10.1.2.114:8080/gdsjzx/shareResource/entAllInfo.do";
	private int succeCount = 0; // 数据插入成功计数

	public String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updatetime = sdf.format(new Date());
		return updatetime;
	}
	
	
	/**
	 * 获取数据，并写入数据到临时表中
	 * 
	 * @throws Exception
	 */

	///@Scheduled(cron="0/50 * * * * ? ")
	//@Scheduled(cron = "0 0 0/1 * * ? ")
	@Scheduled(cron="0/50 * * * * ? ")
	public void toTempTable() throws Exception {
		System.out.println("当前系统时间====》 " + new Date().toLocaleString());
		int count = 0; // 获取数据计数
		StringBuffer sql = new StringBuffer();
		//最近15天的数据【可选】
		//sql.append(	"select  t.zhucehao as regno,t.qiyemingchenquanchen as entname,t.tongyishehuixinyongdaima as unifsocicrediden,t.submit_time as apprdate from GIAP_AICMER_MOVEIN t where  t.submit_time>=sysdate-15");
		sql.append("select t.zhucehao as regno,t.qiyemingchenquanchen as entname,t.tongyishehuixinyongdaima as unifsocicrediden,t.submit_time as apprdate from GIAP_AICMER_MOVEIN t where t.submit_time>=sysdate-60");
		logger.info("\n查询的sql  ===\n" + sql.toString() + "\n");
		List<?> fromDataBaseMqsdb02 = jdbcTemplate.queryForList(sql.toString());
		try {
			if (fromDataBaseMqsdb02 != null && fromDataBaseMqsdb02.size() > 0) {
				Map map = null;
				logger.info("待同步记录总数======" + fromDataBaseMqsdb02.size());
				for (int i = 0; i < fromDataBaseMqsdb02.size(); i++) {
					map = (Map) fromDataBaseMqsdb02.get(i);
					String regnoDb = (String) map.get("regno");
					String entnameDb = (String) map.get("entname");
					String unifsocicredidenDb = (String) map.get("unifsocicrediden");
					Timestamp apprdateDb = (Timestamp) map.get("apprdate");
					HashMap mapParam = new HashMap();
					mapParam.put("entname", entnameDb);
					//因为来源注册号或者统一信用代码是有时候是乱来的
					mapParam.put("regno", regnoDb);
					mapParam.put("unifsocicrediden", unifsocicredidenDb);
					List<MaxTableTime> list =  maxTableTimeMapper.getEntByParams(mapParam);
					System.out.println("=========> " +list.size());
					String words = null;
					boolean isExist =  list.isEmpty(); 
					if(isExist) {
						words ="临时表中不存在  =====>" + entnameDb;
						logger.info(words);
						System.out.println("存在需要插入的数据记录总数======" + fromDataBaseMqsdb02.size());
						System.out.println("需要插入数据     ====> 【当前第" + (i + 1) + " 】 需要往临时表插入");
						System.out.println(map.get("regno") + "   ======   " + map.get("entname"));
						mapParam.put("flag","0");
						maxTableTimeMapper.save(mapParam);
						logger.info(">>>>>>>>>>>>>>>这是第--" + (i + 1) + "--条写入临时表的数据");
					}else {
						words ="临时表中存在  =====>" +entnameDb;
						logger.info(words);
						logger.info("==========> 临时表中已经存在当前数据，不必再往同步表中写入。");
					}
				}
			} else {
				logger.info("来源库中，暂无数据更新");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.toString());
		}

	}

	//@Scheduled(cron="0/20 * * * * ? ") //手动用的，如果不直接修改数据库的数据的话
	@Scheduled(cron = "0 12 0/1  * * ? ") // 用于全局部更新，每60分更新记录
	public void queryToDb() throws Exception {
		HashMap mapParam = new HashMap();
		mapParam.put("flag", "0");
		List<MaxTableTime> lists = (List<MaxTableTime>) maxTableTimeMapper.getEntByParams(mapParam);
		System.out.println("========================================****************************************=============================================");
		if (lists != null && lists.size() > 0) {
			for (int i = 0; i < lists.size(); i++) {
				String unifsocicrediden = lists.get(i).getUnifsocicrediden();
				String regno = lists.get(i).getRegno();
				String entName = lists.get(i).getEntname();
				System.out.println("当前正在请求从省局拷贝第" + (i + 1) + "条数据，数据详情为=======【regno】==" + regno+ "   【unifsocicrediden】===" + unifsocicrediden + "【企业名称】 == " + entName);
				logger.info("当前正在请求从省局拷贝第" + (i + 1) + "条数据，数据详情为=======【regno】==" + regno + "   【unifsocicrediden】==="	+ unifsocicrediden + "【企业名称】 == " + entName);
				if (unifsocicrediden != null) {
					urlHttpClient(null, unifsocicrediden, entName);
				} else if (unifsocicrediden == null && regno != null) {
					urlHttpClient(regno, null, entName);
				} else {
					urlHttpClient(regno, unifsocicrediden, entName);
				}
			}
		} else {
			System.out.print("\n当前无待写记录,时间===> "+  getTime() + "\n");
		}
		System.out.println("========================================****************************************=============================================");
	}

	
	
	
	

	///////////////////////////////////////////////// 以下是请求方法，无需改动//////
	/**
	 * httpClient请求
	 * 
	 * @param regno
	 *            注册号
	 * @param uniscid
	 *            统一社会信用代码
	 * @throws Exception
	 */
	public void urlHttpClient(String regno, String uniscid, String entname) throws Exception {

		Map<String, String> params = new HashMap<String, String>();

		if (!StringUtils.isEmpty(regno)) {
			params.put("regno", regno);
		}

		if (!StringUtils.isEmpty(uniscid)) {
			params.put("uniscid", uniscid);
		}

		String doGet = httpClientService.doGet(URL, params);
		System.out.println("从接口处获取到的数据成功返回来的=====》  " + doGet);
		logger.info("从接口处获取到的数据成功返回来的=====》  " + doGet);
		JSONObject json = JSON.parseObject(doGet); // Stirng转JSON
		// 判断返回的数据是否为error
		if (json.containsKey("error")) {
			JsonNode jsonNode = MAPPER.readTree(doGet);
			JsonNode jsonNode2 = jsonNode.get("error").get("code");
			String code = jsonNode2.toString();
			if (!StringUtils.isEmpty(code) && code.equals("1000")) {
				logger.info(">>>>>>>>>>>>注册号（regno）为：" + regno + "的数据未定义");
			} else if (!StringUtils.isEmpty(code) && code.equals("-32700")) {
				logger.info(">>>>>>>>>>>>注册号（regno）为：" + regno + "的请求json格式错误");
			} else if (!StringUtils.isEmpty(code) && code.equals("-32602")) {
				logger.info(">>>>>>>>>>>>注册号（regno）为：" + regno + "是无效的请求参数");
			}
			if (!(regno == null && uniscid == null)) {
				logger.info(">>>>>>>>>>>非本省企业：企业注册号为\t" + regno + "统一信用码为 ===> " + uniscid + "当前系统更新时间 ===> " + getTime());
				HashMap mapParams = new HashMap();
				mapParams.put("regno", regno);
				mapParams.put("unifsocicrediden", uniscid);
				mapParams.put("entname", entname);
				mapParams.put("flag", "3");// 外省的迁入地数据，省局没有对应的数据
			  //	mapParams.put("updatetime", updatetime);
				maxTableTimeMapper.save(mapParams);
			}
			return;
		}
		System.out.println(">>>>>>>>>>>>注册号（regno）为：" + regno + "的数据获取成功！！！");
		logger.info(">>>>>>>>>>>>注册号（regno）为：" + regno + "的数据获取成功！！！");
		// 1、将json转换为Map，获取所有的json串的key值
		Map<String, Object> map = json;
		// 存储表名
		List<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			if (entry.getValue() != null && !"".equals(entry.getValue())) {
				list.add(key);
			}
		}

		// 2、将所有的json串转换为数组
		for (String str : list) {
			System.out.println("==============================>  迭代返回的数据详情\t" + str);
			logger.info("迭代返回的数据详情=========》 \t  " + str);
			if (!"E_BASEINFO".equals(str)) {
				JSONArray arr = json.getJSONArray(str);
				// 剔除空的数组
				if (!arr.isEmpty() && arr.size() > 0) {
					if ("AFFICHE_SPOTCHECK".equals(str)) {
						List<AfficheSpotcheck> objList = returnObj(AfficheSpotcheck.class, arr);
						insertEntInfoService.insertAfficSpot(objList);
					} else if ("AN_BASEINFO".equals(str)) {
						System.out.println("写入年报数据=========》 " + str);
						logger.info("写入年报数据==============================> \t " + str);
						List<AnBaseinfo> returnObj = returnObj(AnBaseinfo.class, arr);
						insertEntInfoService.insertAnBaseInfo(returnObj);
					} else if ("AO_OPANOMALY_INV".equals(str)) {
						List<AoOpanomalyInv> objList = returnObj(AoOpanomalyInv.class, arr);
						insertEntInfoService.insertAoOpanomalyInv(objList);
					} else if ("AO_OPA_DETAIL".equals(str)) {
						List<AoOpaDetail> objList = returnObj(AoOpaDetail.class, arr);
						insertEntInfoService.insertAoOpaDetail(objList);
					} else if ("CASE_CF_BASEINFO".equals(str)) {
						List<CaseCfBaseinfo> objList = returnObj(CaseCfBaseinfo.class, arr);
						insertEntInfoService.insertCaseCfBaseinfo(objList);
					} else if ("CASE_CF_IRREGPUNISHINFO".equals(str)) {
						List<CaseCfIrregpunishinfo> objList = returnObj(CaseCfIrregpunishinfo.class, arr);
						insertEntInfoService.insertCaseCfIrregpunishinfo(objList);
					} else if ("CASE_CF_PARTYINFO".equals(str)) {
						List<CaseCfPartyinfo> objList = returnObj(CaseCfPartyinfo.class, arr);
						insertEntInfoService.insertCaseCfPartyInfo(objList);
					} else if ("CASE_CF_TRANS".equals(str)) {
						List<CaseCfTrans> objList = returnObj(CaseCfTrans.class, arr);
						insertEntInfoService.insertCaseCfTrans(objList);
					} else if ("E_ALTER_RECODER".equals(str)) {
						List<EAlterRecoder> objList = returnObj(EAlterRecoder.class, arr);
						insertEntInfoService.insertEAlterRecoder(objList);
					} else if ("E_CONTACT".equals(str)) {
						List<EContact> objList = returnObj(EContact.class, arr);
						insertEntInfoService.insertEContact(objList);
					} else if ("E_FIN_LEADER".equals(str)) {
						List<EFinLeader> objList = returnObj(EFinLeader.class, arr);
						insertEntInfoService.insertEFinLeader(objList);
					} else if ("E_FI_SUPL".equals(str)) {
						List<EFiSupl> objList = returnObj(EFiSupl.class, arr);
						insertEntInfoService.insertEFiSupl(objList);
					} else if ("E_IM_INVESTMENT".equals(str)) {
						List<EImInvestment> objList = returnObj(EImInvestment.class, arr);
						insertEntInfoService.insertEImInvestment(objList);
					} else if ("E_IM_INVPRODETAIL".equals(str)) {
						List<EImInvprodetail> objList = returnObj(EImInvprodetail.class, arr);
						insertEntInfoService.insertEImInvprodetail(objList);
					} else if ("E_IM_INVSRALT".equals(str)) {
						List<EImInvsralt> objList = returnObj(EImInvsralt.class, arr);
						insertEntInfoService.insertEImInvsralt(objList);
					} else if ("E_IM_PERMIT".equals(str)) {
						List<EImPermit> objList = returnObj(EImPermit.class, arr);
						insertEntInfoService.insertEImPermit(objList);
					} else if ("E_IM_PRMTALT".equals(str)) {
						List<EImPrmtalt> objList = returnObj(EImPrmtalt.class, arr);
						insertEntInfoService.insertEImPrmtalt(objList);
					} else if ("E_INV_INVESTMENT".equals(str)) {
						List<EInvInvestment> objList = returnObj(EInvInvestment.class, arr);
						insertEntInfoService.insertEInvInvestment(objList);
					} else if ("E_LIC_CERTIFICATE".equals(str)) {
						List<ELicCertifica> objList = returnObj(ELicCertifica.class, arr);
						insertEntInfoService.insertELicCertificate(objList);
					} else if ("E_LP_HSTLEREF".equals(str)) {
						List<ELpHstleref> objList = returnObj(ELpHstleref.class, arr);
						insertEntInfoService.insertELpHstleref(objList);
					} else if ("E_LP_HSTNAME".equals(str)) {
						List<ELpHstname> objList = returnObj(ELpHstname.class, arr);
						insertEntInfoService.insertELpHstname(objList);
					} else if ("E_MOVE_IN".equals(str)) {
						List<EMoveIn> objList = returnObj(EMoveIn.class, arr);
						insertEntInfoService.insertEMoveIn(objList);
					} else if ("E_MOVE_OUT".equals(str)) {
						List<EMoveOut> objList = returnObj(EMoveOut.class, arr);
						insertEntInfoService.insertEMoveOut(objList);
					} else if ("E_NAME_INFO".equals(str)) {
						List<ENameInfo> objList = returnObj(ENameInfo.class, arr);
						insertEntInfoService.insertENameInfo(objList);
					} else if ("E_OT_CASE".equals(str)) {
						List<EOtCase> objList = returnObj(EOtCase.class, arr);
						insertEntInfoService.insertEOtCase(objList);
					} else if ("E_PRI_PERSON".equals(str)) {
						List<EPriPerson> objList = returnObj(EPriPerson.class, arr);
						insertEntInfoService.insertEPriPerson(objList);
					} else if ("E_SM_BASEINFO".equals(str)) {
						List<ESmBaseinfo> objList = returnObj(ESmBaseinfo.class, arr);
						insertEntInfoService.insertESmBaseinfo(objList);
					}
				}
			} else {
				EBaseinfo eBaseInfo = json.getObject("E_BASEINFO", EBaseinfo.class);
				if (eBaseInfo != null) {
					insertEntInfoService.insertEBaseinfo(eBaseInfo);
				}
			}
		}
		succeCount++;
		if (!(regno == null && uniscid == null)) {
			System.out.println("\n====当前系统更新时间" + getTime() + "\n");
			HashMap mapParams = new HashMap();
			mapParams.put("regno", regno);
			mapParams.put("unifsocicrediden", uniscid);
			mapParams.put("entname", entname);
			mapParams.put("flag", "1");
			maxTableTimeMapper.save(mapParams);
			logger.info(">>>>>>>>>>>>注册号（regno）为：【 " + regno + " 】，统一信用代码: 【 " + uniscid + " 】企业名称为【"+  entname + "】的数据写入成功！！！");
			logger.info(">>>>>>>>>>>>数据插入成功数：" + succeCount + "当前系统插入时间 ===> " + getTime());
			System.out.println(">>>>>>>>>>>>数据插入成功数：" + succeCount + " ，当前系统插入时间 ===> " + getTime());
		}
	}

	/**
	 * 返回Bean集合对象
	 * 
	 * @param <T>
	 * @param obj
	 * @param list
	 * @return
	 * @return
	 */
	public <T> List<T> returnObj(Class<T> clazz, JSONArray arr) {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < arr.size(); i++) {
			Object obj2 = arr.get(i);
			if (obj2 != null) {
				String objStr = obj2.toString();
				T parseObject = JSON.parseObject(objStr, clazz);
				list.add(parseObject);
			}
		}
		return list;
	}

}
