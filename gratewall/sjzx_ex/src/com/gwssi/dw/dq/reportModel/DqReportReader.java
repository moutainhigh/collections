/**
 * 
 */
package com.gwssi.dw.dq.reportModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * <p>Title: </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) Oct 6, 2008</p>
 * <p>Company: </p>
 * @author zhouyi
 * @version 1.0
 */
public class DqReportReader {
	private static DqReportReader dqReportReader= null;
	
	private DqReportReader(){
		
	}
	
	public static DqReportReader getInstance(){
		if(dqReportReader==null){
			dqReportReader = new DqReportReader();
		}
		return dqReportReader;
	}
	
	public DqReport readFromMap(Map reportMap){
		DqReport dqReport = new DqReport();
		
		String title;
		try {
			title = getMapValue(reportMap,"title").toString();
		} catch (RuntimeException e) {
			title = "查询结果";
		}

		Map zlMap = (Map)getMapValue(reportMap,"zl");
		Map blMap = (Map)getMapValue(reportMap,"bl");
		
		dqReport.setTitle(title);
		dqReport.setZlMetaArea(this.getMetaArea(zlMap));
		dqReport.setBlMetaArea(this.getMetaArea(blMap));
		
		return dqReport;
	}
	
	private DqMetaArea getMetaArea(Map areaMap){
		DqMetaArea dqMetaArea = new DqMetaArea();
		List blockList = (List)getMapValue(areaMap,"blocks");
		Map blockMap,itemMap,metaMap;
		int blockSize = blockList.size();
		DqBlock[] blocks = new DqBlock[blockSize];
		DqItem[] items;
		
		String type,id,text,cond,query,from,show;
		
		for(int i=0;i<blockSize;i++){
			blockMap = (Map)blockList.get(i);
			blocks[i] = new DqBlock();
			List itemsList = (List)getMapValue(blockMap,"items");
			items = new DqItem[itemsList.size()];
			
			for(int iItem=0;iItem<itemsList.size();iItem++){
				itemMap = (Map)itemsList.get(iItem);
				type = getMapValue(itemMap,"type").toString();
				
				if(type.equals("group")){
					show = getMapString(((Map)getMapValue(itemMap,"group")),"show");
					items[iItem] = DqItem.getGroupInstance();
					if(blockSize==1&&show!=null&&show.equals("mixed")){
						dqMetaArea.setShowCode(true);
					}
					List groupItems = (List)getMapValue(((Map)getMapValue(itemMap,"group")),"groupItems");
					for(int iGroupItem=0;iGroupItem<groupItems.size();iGroupItem++){
						metaMap = (Map)groupItems.get(iGroupItem);
						text = getMapValue(metaMap,"text").toString();
						cond = getMapValue(metaMap,"cond").toString();
						items[iItem].addMetadata(new DqGroupItem(text,text,cond));
						text = null;
						cond = null;
					}
					show = null;
				}else{
					items[iItem] = DqItem.getTargetsInstance();
					List targets = (List)getMapValue(itemMap,"targets");
					for(int iTarget=0;iTarget<targets.size();iTarget++){
						metaMap = (Map)targets.get(iTarget);
						id	 = getMapValue(metaMap,"id").toString();
						text = getMapValue(metaMap,"text").toString();
						query = getMapValue(metaMap,"query").toString();
						from = getMapValue(metaMap,"from").toString();
						
						items[iItem].addMetadata(new DqTarget(id,text,query,from));
						id = null;
						text = null;
						query = null;
						from = null;
					}
				}
			}
			blocks[i].setItems(items);
		}
		
		dqMetaArea.setBlocks(blocks);
		return dqMetaArea;
	}
	
	private Object getMapValue(Map map,String key){
		Object value =  map.get(key);
		if(value==null){
			System.out.println("");
		}
		return value;
	}
	
	private String getMapString(Map map,String key){
		Object value =  map.get(key);
		if(value==null){
			return null;
		}
		return value.toString();
	}
	
	public static Map exampleReportMap(){
		Map reportMap = new HashMap();
		Map zlMap = new HashMap();
		Map blMap = new HashMap();
		//主栏
		List zlBlocks = new ArrayList();
		Map zlBlockMap1 = new HashMap();
		
		List items = new ArrayList();
		Map item1 = new HashMap();
		Map item2 = new HashMap();
		
		List groupItems = new ArrayList();
		Map groupItem1 = new HashMap();
		groupItem1.put("text", "农业");
		groupItem1.put("cond", "cond1");
		
		Map groupItem2 = new HashMap();
		groupItem2.put("text", "工业");
		groupItem2.put("cond", "cond1");
		
		Map groupItem3 = new HashMap();
		groupItem3.put("text", "冶炼业");
		groupItem3.put("cond", "cond1");
		
		groupItems.add(groupItem1);
		groupItems.add(groupItem2);
		groupItems.add(groupItem3);
		for(int i=0;i<20;i++){
			Map groupItem = new HashMap();
			groupItem.put("text", "冶炼业"+i);
			groupItem.put("cond", "cond1"+i);
			groupItems.add(groupItem);
		}
		
		Map zlGroup1 = new HashMap();
		
		zlGroup1.put("id", "0001");
		zlGroup1.put("text", "行业分类");
		zlGroup1.put("groupItems", groupItems);
		
		Map zlGroup2 = new HashMap();
		
		List groupItems2 = new ArrayList();
		groupItems2.add(groupItem1);
		groupItems2.add(groupItem2);
		groupItems2.add(groupItem3);
		zlGroup2.put("id", "0003");
		zlGroup2.put("text", "行业分类3");
		zlGroup2.put("groupItems", groupItems2);
		
		item1.put("type", "group");
		item1.put("group",zlGroup1);
		
		item2.put("type", "group");
		item2.put("group",zlGroup2);
		
		items.add(item1);
		items.add(item2);
		zlBlockMap1.put("items", items);
		zlBlocks.add(zlBlockMap1);
		
		zlMap.put("blocks", zlBlocks);
		//宾栏
		List blBlocks = new ArrayList();
		
		Map blBlockMap1 = new HashMap();
		List blItems1 = new ArrayList();
		
		Map blitem11 = new HashMap();
		
		List targets1 = new ArrayList();
		Map target1 = new HashMap();
		target1.put("id", "1");
		target1.put("text", "户数");
		target1.put("query", "1");
		target1.put("from", "1");
		
		Map target2 = new HashMap();
		target2.put("id", "2");
		target2.put("text", "金额");
		target2.put("query", "1");
		target2.put("from", "1");
		
		targets1.add(target1);
		targets1.add(target2);
		blitem11.put("type", "targets");
		blitem11.put("targets", targets1);
		//
		Map blitem12 = new HashMap();
		List  groupItems11 = new ArrayList();
		Map groupItem111 = new HashMap();
		groupItem111.put("text", "国有企业");
		groupItem111.put("cond", "国有企业");
		
		Map groupItem112 = new HashMap();
		groupItem112.put("text", "集体企业");
		groupItem112.put("cond", "集体企业");
		
		Map groupItem113 = new HashMap();
		groupItem113.put("text", "私营企业");
		groupItem113.put("cond", "私营企业");
		
		groupItems11.add(groupItem111);
		groupItems11.add(groupItem112);
		groupItems11.add(groupItem113);
		
		Map group11 = new HashMap();
		
		group11.put("id", "12");
		group11.put("text", "企业分类");
		group11.put("groupItems", groupItems11);
		
		blitem12.put("type", "group");
		blitem12.put("group", group11);
		
		
		Map group12 = new HashMap();
		Map blitem13 = new HashMap();
		
		Map groupItem121 = new HashMap();
		groupItem121.put("text", "第一产业");
		groupItem121.put("cond", "第一产业");
		
		Map groupItem122 = new HashMap();
		groupItem122.put("text", "第二产业");
		groupItem122.put("cond", "第二产业");
		
		Map groupItem123 = new HashMap();
		groupItem123.put("text", "第三产业");
		groupItem123.put("cond", "第三产业");
		
		List  groupItems12 = new ArrayList();
		groupItems12.add(groupItem121);
		groupItems12.add(groupItem122);
		groupItems12.add(groupItem123);
		
		group12.put("id", "123");
		group12.put("text", "产业类型");
		group12.put("groupItems", groupItems12);
		
		blitem13.put("type", "group");
		blitem13.put("group", group12);
		
		blItems1.add(blitem11);
		blItems1.add(blitem12);
		blItems1.add(blitem13);
		
		blBlockMap1.put("items", blItems1);
		blBlocks.add(blBlockMap1);
		
		//block2
		Map blBlockMap2 = new HashMap();
		List blItems2 = new ArrayList();
		Map blItem21 = new HashMap();
		List targets2 = new ArrayList();
		
		targets2.add(target1);
		blItem21.put("type", "targets");
		blItem21.put("targets", targets2);
		
		blItems2.add(blItem21);
		blBlockMap2.put("items", blItems2);
		//blBlocks.add(blBlockMap2);
		blMap.put("blocks", blBlocks);
		
		
		reportMap.put("title", "测试查询用表");
		reportMap.put("zl", zlMap);
		reportMap.put("bl", blMap);
		return reportMap;
	}
	
	public static void main(String args[]){
		Map map = DqReportReader.exampleReportMap();
		
		System.out.println(JSONObject.fromObject(map).toString());
	}
}
