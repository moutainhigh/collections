package com.report.core;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.report.comon.TransferTime;
import com.report.service.DoQueryService;

@Component
public class Business {

	@Autowired
	private DoQueryService ss;

	public void doQuery(String timeString) {
		Map<String,String> map = TransferTime.getStartTimeAndEndTimeByStr(timeString);
		
		
		String quNianTongQi = map.get("选择查询截止时间的去年同期时间");
		
		String begTime = map.get("今年年初时间");
		String endTime = map.get("选择查询截止时间");
		
		List task01JinNian = ss.task01JinNian(endTime);
		List task01QuNian = ss.task01QuNian(quNianTongQi);

		List task02NeiZiYouShiYiLai = ss.task02NeiZiYouShiYiLai(begTime, endTime);
		List task02NeiZiBenQi = ss.task02NeiZiBenQi(begTime, endTime);

		List task02SiYingYouShiYiLai = ss.task02SiYingYouShiYiLai(begTime, endTime);
		List task02SiYingBenQi = ss.task02SiYingBenQi(begTime, endTime);

		List task02WaiZiBenQi = ss.task02WaiZiBenQi(begTime, endTime);
		List task02WaiZiYouShiYiLai = ss.task02WaiZiYouShiYiLai(begTime, endTime);

		List task03NianChuDaoBenYue = ss.task03NianChuDaoBenYue(begTime, endTime);

		List task03BenYue = ss.task03NianChuDaoBenYue(begTime, endTime);

		List task04NianChuDaoBenYue = ss.task04NianChuDaoBenYue(begTime, endTime);

		List task04BenYue = ss.task04NianChuDaoBenYue(begTime, endTime);

		List task05BenQi = ss.task05BenQi(begTime, endTime);
		List task05YouShiYiLai = ss.task05YouShiYiLai(begTime, endTime);

		
		
		
		
		
		//List taskForTaiWan = ss.taskForTaiWan(begTime, endTime);
	}
}
