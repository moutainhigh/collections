package com.ly.vuecontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ly.dao.impl.Stock_K_line_Day_Data_List_Dao;
import com.ly.pojo.Stock;
import com.ly.vo.StockVo;

//@RestController
//@RequestMapping("api")
public class MyStockQuShiController {
	
	
	@Autowired
	private Stock_K_line_Day_Data_List_Dao datasDao;
	public String getTime(){
		List times =  datasDao.find("select max(date) from  Stock_K_line_Day_DataList");
		String time = (String) times.get(0);
		return time;// new Date()为获取当前系统时间
	}
	
	/*public void task() throws Exception{
		StockVo vo = null;
		String sql = " select t.code,t1.name,t.closePrice,t.ma5,t.ma10,t.ma20,t.ma30,t1.totalhand from Stock_K_line_Day_DataList t inner join Stock t1 on t.code = t1.code ";
		StringBuffer sb = new StringBuffer();
		List<StockVo> retList = new ArrayList<StockVo>();
		sb.append(" where t1.code= ? ");
		sb.append(" and t.closePrice - t.ma30 ");
		List<Object []>  list2 = datasDao.getStockVo(sql,null);
		for (int i = 0; i < list2.size(); i++) {
			Object [] obj  = list2.get(i);
			for (int j = 0; j < obj.length; j++) {
					vo = new StockVo();
					vo.setCode(obj[0].toString());
					vo.setName(obj[1].toString());
					vo.setClosePrice(obj[2].toString());
					vo.setMa5(obj[3].toString());
					vo.setMa10(obj[4].toString());
					vo.setMa20(obj[5].toString());
					vo.setMa30(obj[6].toString());
					vo.setTotalHand(obj[7].toString());

					retList.add(vo);
				
			}
		}
		
		String title = "趋势上30日线，趋势慢向上的,共"+retList.size()+"条";
	}*/
}
