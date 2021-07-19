package com.gwssi.ebaic.apply.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.rodimus.dao.DaoUtil;

/**
 * 公司设立。
 * 主要人员规则校验。
	
		需要两套规则：
		1、保存弹出窗口前验证，避免用户做了傻事儿而不自知。需要传入的参数包括：entid，新人的身份证号、职位。
		2、离开页面时验证。需要传入的参数包括：entid。
		为了确保用户直接关了浏览器，下次打开直接进了其他页签，提交前也应该都跑一遍。

		两套规则分别包括：
		 
		 增加或编辑主要人员保存时候校验：
		（1）董事和经理都不能兼任监事。
		（2）董事长只能有1人，执行董事只能有1人。监事会主席只能有1人。
		（3）经理只能有1人。外资总经理只能有1人。
		（4）董事中，人员不能重复；监事中，人员不能重复；经理中，人员不能重复；""
		（5）法人任职资格：公司法定代表人依照公司章程的规定，由董事长、执行董事或者经理担任
		（6）董事长、执行董事可以兼任公司经理"	董事会成员都可以兼任经理	

		
		页面提交时校验：
		（1）必须设定法人。
		（2）设立董事会，必须设立董事长。
		（3）有限责任公司设监事会，其成员不得少于三人"	
		（4）有限公司、国有独资：董事会中至少有1名职工代表。
		
		规则条数很多,而且将来会更多,如果每条规则都查数据库,效率超级差。
		
		从性能考虑,两条原则:
		(1)所有数据,从数据库只能取1次。
		(2)Web对后台发起请求,只能有1次。
		
		看看怎么做:
		(1)从数据库取数据: 
		select m.cer_no,j.postion from cp_wk_entmember m left join cp_wk_job j on m.entmemberid=j.entmember_id where m.ent_id = ?
		(2)数据结构Map<String,List>：分别以职位和身份证号码为key建map，得到两个:personMap,postionMap。
		构建过程：
		for(rs in db){
			personMap.add(cerNo,postion)
			postionMap.add(cerNo,postion)
		}
		put new record into maps
		(3)分析规则：
			（1）董事和经理都不能兼任监事: Set Person1 {a | a 属于经理 或 a属于监事}，Set Persion2 {a | a属于监事 } ， 
				Set U = intersection(Person1,Person2) 
				if U is not empty then
					cought!
				end if
			（2）董事长只能有1人，执行董事只能有1人。监事会主席只能有1人。 too easy
			（3）经理只能有1人。外资总经理只能有1人。 too easy
			（4）董事中，人员不能重复；监事中，人员不能重复；经理中，人员不能重复。
			a、if the new cerno exists in target set already
			do it before put them into map
			--- b、put list into set,see whether the size of collections changed
			（5）法人任职资格：公司法定代表人依照公司章程的规定，由董事长、执行董事或者经理担任.already done
			（6）董事长、执行董事可以兼任公司经理"	董事会成员都可以兼任经理	
				监事会中至少有1名职工代表	
		
		变更后的校验需求：
		
		
		页面校验是否满足：

			A、通用：
				1、董事、经理不得兼任监事。（校验）
				2、董事都可以兼任经理。（通过功能限制）
				3、法定代表人：1（通过功能限制）
				4、股份、国有独资，必须设立董事会，必须设立监事会。（通过功能限制）
			
			B、和是否设董事会、监事会相关：
				1、如果设立董事会，董事长：1（校验）
				2、如果设立监事会，监事会主席：1（校验）
			
				3、如果设立监事会，监事人数：3~无限
				4、如果设立监事会，职工监事比例不低于1/3
			
				5、如果不设董事会，执行董事人数：0-1
				6、如果不设监事会，监事人数：0-2
			
			C、和公司类型相关：
				1、有限，如果设立董事会，董事人数：3-13
				2、股份，必须设立董事会，董事人数：5-19

		
 * @author liuhailong
 */
public class SetupMemberRuleUtil extends BaseService {

	private final static Logger logger = Logger.getLogger(SetupMemberRuleUtil.class);
	/**
	 * <p>在保存主要人员页面时校验规则。</p>
	 * <ul>
	 * <ol>1.必须设定法人。</ol>
	 * <ol>2.设立董事会，必须设立董事长；设立监事会，必须设立监事会主席。</ol>
	 * <ol>3.有限责任公司设监事会，其成员不得少于三人。</ol>
	 * <ol>4.有限公司、国有独资：董事会中至少有1名职工代表。
	 * <ol></ol>
	 * </ul>	
	 * @throws OptimusException 
	 */
	@SuppressWarnings({ "rawtypes"})
	public static List<String>  ruleCheckWhenSavingAll(String entId,String isBoard,String isSuped,String catId) throws OptimusException{
		long start = System.currentTimeMillis(),time;
		List<String> errorMsgs = new ArrayList<String>();
		HashMap<String,List> empSupervistorNum = new HashMap<String,List>();//职工监事数量
//		HashMap<String,List> personMap = new HashMap<String,List>();
		HashMap<String,List> positionMap = new HashMap<String,List>();
//		List<Map<String, Object>> members = buildCheckMapsWithEntId(entId,null,personMap,positionMap,empSupervistorNum);
		time = System.currentTimeMillis();
		logger.debug("db/map ok : " + (time-start));
		
		List directorSet = new ArrayList();
//		List managerSet = new ArrayList();
		List supervisorSet = new ArrayList();
//		buildPostionsSet(positionMap,directorSet,managerSet,supervisorSet);
		time = System.currentTimeMillis();
		logger.debug("set ok : " + (time-start));
		List _list ;
		managerMust(errorMsgs, catId, positionMap);
		//设立董事会，必须设立董事长
		if("1".equals(isBoard)){//设立董事会
			_list = positionMap.get("431A");//董事长
			if(_list==null || _list.size()<1){
				errorMsgs.add("设立董事会，必须录入董事长信息。");
			}
			
			int directorSetSize = directorSet.size();
			//有限责任公司董事不得少于三人
			if(("1100".equals(catId)||"5100".equals(catId)) && (directorSetSize<3 || directorSetSize > 13)){
				errorMsgs.add("设立董事会，董事人数不得少于3人，不多于13人。");
			}
			// 股份公司 董事5-19个
			if(("1200".equals(catId)||"5200".equals(catId)) && (directorSetSize<5 || directorSetSize>19)){
				errorMsgs.add("股份公司，董事人数不少于5人，不多于19人。");
			}
		}else{//不设董事会
			int directorSetSize = directorSet.size();
			if(directorSetSize!=1){
				errorMsgs.add("不设立董事会，必须设立一名执行董事。");
			}
		}
		//设立监事会，必须设立监事会主席
		if("1".equals(isSuped)){//设立监事会
			_list = positionMap.get("408B");//监事会主席
			if(_list==null || _list.size()<1){
				errorMsgs.add("设立监事会，必须录入监事会主席信息。");
			}
			// 有限责任公司设监事会，其成员不得少于三人
			if(supervisorSet.size()<3){
				errorMsgs.add("设立监事会，其成员不得少于3人。");
			}
			int num = empSupervistorNum.size()*3;
			if(num<supervisorSet.size()){
				errorMsgs.add("设立监事会，“职工监事”的比例不得低于三分之一。");
			}
		}else{ // 不设监事会
			if(supervisorSet.size()<1 || supervisorSet.size() >2 ){
				errorMsgs.add("不设立监事会，监事应为1~2人。");
			}
			
		}

		// 必须设置法人
//		if(members!=null && members.size()>0){
//			Iterator<Map<String,Object>>  it = members.iterator();
//			Map<String,Object> row ;
//			String leRepSign = "0"; //是否法人
//			while(it.hasNext()){
//				row = it.next();
//				leRepSign = (String)row.get("leRepSign");
//				if("1".equals(leRepSign)){
//					break;
//				}
//			}
//			if(!"1".equals(leRepSign)){
//				errorMsgs.add("您未设置法定代表人，不能保存。");
//			}
//		}else{
//			errorMsgs.add("您没有添加主要人员，不能保存。");
//		}
		
		time = System.currentTimeMillis();
		logger.debug("check done : " + (time-start));
		return errorMsgs;
	}
	
	/**
	 * 设立时候，内资必须设立经理，外资必须设立总经理。不含分公司。
	 * @param catId
	 * @param positionMap
	 * @throws OptimusException 
	 */
	@SuppressWarnings("rawtypes")
	private static void managerMust(List<String> errorMsgs, String catId, HashMap<String, List> positionMap) throws OptimusException {
		if("1100_1110_1150_1200".indexOf(catId) != -1){
			List<?> jlList = positionMap.get("436A");//经理
			if(jlList==null || jlList.size()<1){
				errorMsgs.add("内资必须设立经理。");
			}
		}
//		if(CpEntTypeUtils.isForeignEnt(catId)){
//			List<?> zjlList = positionMap.get("434Q");//总经理
//			if(zjlList==null || zjlList.size()<1){
//				errorMsgs.add("外资必须设立总经理。");
//			}
//		}
	}

	/**
	 * <p>在将要保存单个主要人员时校验规则。</p>
	 * <ul>
	 * <ol>1.董事和经理都不能兼任监事。</ol>
	 * <ol>2.董事长只能有1人，执行董事只能有1人。监事会主席只能有1人。</ol>
	 * <ol>3.经理只能有1人。外资总经理只能有1人。</ol>
	 * <ol>4.董事中，人员不能重复；监事中，人员不能重复；经理中，人员不能重复。</ol>
	 * </ul>		
	 * @param entId
	 * @param newCerNo
	 * @param newPosition
	 * @return
	 * @throws OptimusException
	 */
	public static List<String> ruleCheckWhenSavingSingle(Map<String,String> map,String positionType,String entId) throws OptimusException{
		String newCerNo = map.get("cerNo");
		List<String> errorMsgs = new ArrayList<String>();
		List<String> directorList = new ArrayList<String>();
		List<String> managerList = new ArrayList<String>();
		List<String> supervisorList = new ArrayList<String>();
		
		buildCheckMapsWithEntId(entId,directorList,managerList,supervisorList);
		
		//新添加一个董事，董事长只能有1人，执行董事只能有1人
		if("1".equals(positionType)){
			if(directorList.contains(newCerNo)){
				errorMsgs.add("董事中人员不能重复任职，请核对姓名与证件号码是否相符。");
			}
			if(supervisorList.contains(newCerNo)){
				errorMsgs.add("该人员已经是监事，不能再担任董事。");
			}
		}
		
		// 新添加一个经理
		if("2".equals(positionType)){
			if(managerList.contains(newCerNo)){
				errorMsgs.add("经理中人员不能重复任职，请核对姓名与证件号码是否相符。");
			}
			if(supervisorList.contains(newCerNo)){
				errorMsgs.add("该人员已经是监事，不能再担任经理。");
			}
		}
		// 新添加一个监事，董事和经理都不能兼任监事，监事会主席只能有1人。
		if("3".equals(positionType)){
			if(supervisorList.contains(newCerNo)){
				errorMsgs.add("监事中人员不能重复任职，请核对姓名与证件号码是否相符。");
			}
			if(directorList.contains(newCerNo)){
				errorMsgs.add("董事不能兼任监事。");
			}
			if(managerList.contains(newCerNo)){
				errorMsgs.add("经理不能兼任监事。");
			}
		}
		return errorMsgs;
	}
	
	

	/**
	 * 
	 * 功能：董经监集合List<cer_no>
	 * @param positionMap
	 * @param directorSet
	 * @param managerSet
	 * @param supervisorSet
	 */
	protected static void buildPostionsSet(List<String> list,List<String> directorSet,List<String> managerSet,List<String> supervisorSet){
		if(list!=null && list.size()>0){
			Iterator<String> iterator = list.iterator();
			String position = null;
			while(iterator.hasNext()){
				position = iterator.next();
				//董事长、副董事长、董事、执行董事、独立董事
				if("431A".equals(position)||"431B".equals(position)||
						"432A".equals(position)||"432L".equals(position)){
					directorSet.add(position);
				}
				//总经理、副总经理、经理
				if("434Q".equals(position)||"434R".equals(position)||"436A".equals(position)){
					managerSet.add(position);
				}
				//监事、监事会主席
				if("408A".equals(position)||"408B".equals(position)){
					supervisorSet.add(position);
				}
			}
		}
	}
	
	private static void buildCheckMapsWithEntId(String entId,List<String> directorList, List<String> managerList,List<String> supervisorList) throws OptimusException{
		
		StringBuffer sql = new StringBuffer();
		List<String> param = new ArrayList<String>();
		sql.append(" select m.cer_no,j.position,j.position_type from be_wk_entmember m left join be_wk_job j on m.entmember_id=j.entmember_id where m.ent_id = ? ");
		param.add(entId);
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql.toString(), param);
		
		if(list!=null && list.size()>0){
			logger.debug("除当前将要保存的人员，还有："+list.size());
			Iterator<Map<String, Object>> iterator = list.iterator();
			String _cerNo = null;
			String _positionType = null;
			while(iterator.hasNext()){
				Map<String,Object> row = iterator.next();
				_cerNo = (String)row.get("cerNo");
				_positionType = (String)row.get("positionType");
				if("1".equals(_positionType)){//董事
					directorList.add(_cerNo);
				}
				if("2".equals(_positionType)){//经理
					managerList.add(_cerNo);
				}
				if("3".equals(_positionType)){//监事
					supervisorList.add(_cerNo);
				}
			}
		}
	}
	
}

