package com.report.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.report.dao.BaseDao;

@Service
public class Data05Service extends BaseDao {

	public List getList(String time) {
		String sql = "";


		//sql = "select * from ye_test_rec";
		Query query = this.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List list = query.list();

		return list;

	}
	
	
	
	public List getList2(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = time;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		System.out.println(reStr);
		String lastYear = reStr; // 去年这个日期
		
		
		String sql = " ";


		
		//sql = "select * from ye_test_rec";
		
		Query query = this.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List list = query.list();

		return list;

	}

}
