package com.gwssi.entSelect.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.entSelect.service.MyBatisEntSelectBatchService;
import com.gwssi.optimus.core.exception.OptimusException;


@Controller
public class MyController {

	
	@Autowired
	private MyBatisEntSelectBatchService entSelectService;
	
	//@RequestMapping("show")
	public void show() throws OptimusException, ParseException {
		/*
		entSelectService.insert();*/
		
		
		//String queryParams ="  where   t.altdate  >=to_date('2016','yyyy')  and t.altdate <=to_date('2019','yyyy') ";
		
	//	String queryParams = " where to_char(t.altdate,'yyyy') >= '2019' and t.ALTITEMCODE in   ('13','61','G7','41','45','12','31','51','53','54','56')";
		//String queryParams = "  where t.main_tb_id = '7e699a44e1664baf8c2902fb9370693c' ";
		
		
		
		//String queryParams = "  where t.id not in(select t2.altid from  e_alter_recoder_sj  t2) ";
		
		String queryParams = " WHERE TO_CHAR(T.ALTDATE, 'YYYY') = '2020'\n" +
					" AND NOT EXISTS\n" + 
					"(SELECT 1 FROM E_ALTER_RECODER_SJ S WHERE T.ID = S.ALTID)";

		
		
		String  totalStr = entSelectService.queryTotal(queryParams);
		int  total = Integer.valueOf(totalStr);
		int pageSize = 50000;
		int step = (int)total/pageSize +1;
	
		/*for (int i = 1; i < step; i++) {
			System.out.println("======== 总步数===> " + step + "  ,每页大小===> "+ pageSize + " , 总记录数===> " + total + "条,还有条没有爬虫到 "  + (total-i*pageSize));
			//entSelectService.queryBiangengDetail(pageSize,i);
			entSelectService.insert(pageSize, i,queryParams);
		}*/
		
		entSelectService.insert(pageSize, 1,queryParams);
	}
}
