package com.report.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.dao.BaseDao;

@Transactional
@Service
public class TaiWaiGeTiService extends BaseDao{
	//private static Logger logger = Logger.getLogger(TaiWaiGeTiService.class); // 获取logger实例
	private static Logger logger = Logger.getLogger("taiwai"); // 获取logger实例
	
	
	public static void main(String[] args) throws ParseException {
		TaiWaiGeTiService s = new TaiWaiGeTiService() ;
		s.getList("2018-09-25");
		
		
	}
	
	public  List getList(String time) throws ParseException {
		
		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		
		
		//得到当前月的最后一天
		//Calendar ca = Calendar.getInstance();    
	    //ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
	    //String last = format.format(ca.getTime());
	   // System.out.println(last);
	   // logger.debug(last);
		/*String benQiEndStr = sdf.format(benQiEnd) + "-31 23:59:59";
		System.out.println(benQiEndStr);*/

		
	    
	    //得到选择日期的所在月的前三个月
	    String str = time;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		
		rightNow.set(Calendar.DAY_OF_MONTH, rightNow.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(rightNow.getTime());
		//String benQiEndStr = sdf.format(last) + " 23:59:59";
		String benQiEndStr = last+ " 23:59:59";
		System.out.println(benQiEndStr);
		
		
		rightNow.add(Calendar.MONTH, -2);// 日期减3个月

		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);

		//String benQiBegin = reStr + "-01 00:00:00"; //
		String benQiBegin = reStr + "-01"; //

		logger.debug(benQiBegin);
		
		
		String sql = "select b.traname \"个体工商户名称\",b.oploc \"经营场所\",b.compform_cn \"组成形式\",b.fundam \"注册资本\",b.opscope \"经营范围及方式\",b.regno \"工商注册号\",b.uniscid \"统一社会信用代码\",b.estdate \"工商注册日期\",b.regstate_cn \"存续状态\",b.empnum \"从业人数\",b.name \"法定代表人(经营者)姓名\",p.dom \"法定(经营者)户籍住址(台湾)\",p.tel \"电话号码\",p.cerno \"备注(证件号码)\"\n" +
						"from tm_updata.e_pb_baseinfo b\n" + 
						"     left join tm_updata.e_pb_operator p on b.pripid=p.pripid\n" + 
						"where b.enttype in ('9550')\n" + 
						"and b.estdate>=to_date('"+benQiBegin+"','yyyy-mm-dd') and b.estdate<=to_date('"+last+"','yyyy-mm-dd hh24:mi:ss')\n" + 
						"and b.regstate='1'";

		 logger.debug(sql);
		
		
		Query query = this.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List list = query.list();

		return list;
		

	}
	
}
