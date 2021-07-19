/**
 * 
 */
package com.gwssi.dw.dq.reportModel;

/**
 * <p>Title: </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) Sep 25, 2008</p>
 * <p>Company: 长城软件</p>
 * @author zhouyi
 * @version 1.0
 */
public class DqReport
{
	private String title;
	
	private DqMetaArea zlMetaArea;
	
	private DqMetaArea blMetaArea;
	//
	private String	jsons;
	
	private int datas[][];

	/**
	 * @return the datas
	 */
	public int[][] getDatas()
	{
		return datas;
	}

	/**
	 * @param datas the datas to set
	 */
	public void setDatas(int[][] datas)
	{
		this.datas = datas;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the zlMetaArea
	 */
	public DqMetaArea getZlMetaArea()
	{
		return zlMetaArea;
	}

	/**
	 * @param zlMetaArea the zlMetaArea to set
	 */
	public void setZlMetaArea(DqMetaArea zlMetaArea)
	{
		this.zlMetaArea = zlMetaArea;
	}

	/**
	 * @return the blMetaArea
	 */
	public DqMetaArea getBlMetaArea()
	{
		return blMetaArea;
	}

	/**
	 * @param blMetaArea the blMetaArea to set
	 */
	public void setBlMetaArea(DqMetaArea blMetaArea)
	{
		this.blMetaArea = blMetaArea;
	}
	
	public void calculate(){
		this.zlMetaArea.calculate();
		this.blMetaArea.calculate();
	}
	
	public static DqReport exampleDqReport(){
		DqReport dqReport = new DqReport();
		dqReport.setTitle("企业情况查询");
		
		DqMetaArea _zlMetaArea = new DqMetaArea();
		DqBlock[] zlBlocks = new DqBlock[1];
		zlBlocks[0] = new DqBlock();
		DqItem[] zlItems0 = new DqItem[1];
		zlItems0[0] = DqItem.getGroupInstance();
		
		zlItems0[0].addMetadata(new DqGroupItem("0001","农业"));
		zlItems0[0].addMetadata(new DqGroupItem("0002","工业"));
		zlItems0[0].addMetadata(new DqGroupItem("0003","冶炼业"));
		zlBlocks[0].setItems(zlItems0);

		_zlMetaArea.setBlocks(zlBlocks);
		dqReport.setZlMetaArea(_zlMetaArea);
		//宾栏
		DqMetaArea _blMetaArea = new DqMetaArea();
		DqBlock[] blBlocks = new DqBlock[2];
		
		blBlocks[0] = new DqBlock();
		DqItem[] blItems0 = new DqItem[2];
		//宾栏区块0的指标条
		blItems0[0] = DqItem.getTargetsInstance();
		blItems0[0].addMetadata(new DqTarget("1","户数"));
		blItems0[0].addMetadata(new DqTarget("2","注册资金"));
		
		blItems0[1] = DqItem.getGroupInstance();
		blItems0[1].addMetadata(new DqGroupItem("1001","国有企业"));
		blItems0[1].addMetadata(new DqGroupItem("2001","集体企业"));
		blItems0[1].addMetadata(new DqGroupItem("3001","私营企业"));
		
		blBlocks[0].setItems(blItems0);
		//宾栏区块1的指标条
		
		blBlocks[1] = new DqBlock();
		DqItem[] blItems10 = new DqItem[1];
		blItems10[0] = DqItem.getTargetsInstance();
		blItems10[0].addMetadata(new DqTarget("2","户数"));
		
		blBlocks[1].setItems(blItems10);
		_blMetaArea.setBlocks(blBlocks);
		dqReport.setBlMetaArea(_blMetaArea);
		
		return dqReport;
	}

	public void setJsons(String jsons)
	{
		this.jsons = jsons;
	}
	
	public String getJsons(){
		return this.jsons;
	}
}
