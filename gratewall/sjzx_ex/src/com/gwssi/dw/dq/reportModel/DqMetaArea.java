/**
 * 
 */
package com.gwssi.dw.dq.reportModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>Title: 元数据条件区域</p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) Sep 25, 2008</p>
 * <p>Company: 长城软件</p>
 * @author zhouyi
 * @version 1.0
 */
public class DqMetaArea
{
	private DqBlock[] blocks;//一个元数据区域可以有多个块
	//解析后的属性
	private String[][] textArray;//二维文本数组
	
	private int counts;//区域列计数
	
	private int levels;//区域层次数
	
	private int[] levelsMaxLength;
	private int[] countsMaxLength;
	
	private boolean showCode = false;
	/**
	 * @return the showCode
	 */
	public boolean isShowCode()
	{
		return showCode;
	}

	/**
	 * @param showCode the showCode to set
	 */
	public void setShowCode(boolean showCode)
	{
		this.showCode = showCode;
	}

	/**
	 * @return the blocks
	 */
	public DqBlock[] getBlocks()
	{
		return blocks;
	}

	/**
	 * @param blocks the blocks to set
	 */
	public void setBlocks(DqBlock[] blocks)
	{
		this.blocks = blocks;
	}
	/**
	 * 计算后可用
	 * 获得区域的二维文本数组
	 * @return
	 */
	public String[][] getTextArray(){
		return textArray;
	}
	
	public int getAreaLevels(){
		return levels;
	}
	
	public int getCounts(){
		return this.counts;
	}
	
	/**
	 * @return the levelsMaxLength
	 */
	public int[] getLevelsMaxLength() {
		return levelsMaxLength;
	}

	/**
	 * @return the countsMaxLength
	 */
	public int[] getCountsMaxLength() {
		return countsMaxLength;
	}

	/**
	 * 区域计算
	 */
	public void calculate(){
		DqBlock block;
		int maxLevels = 0;// 块里面的最大层树
		int blockCount = 0;

		DqItem[] items;
		DqItem item;
		int itemCounts, // 记录一个块中的元数据组合数目，为每一个item里面的元数据数量和的乘积
		itemLength,//block中item的数量
		metaCount, // 记录item中元数据数量
		levelDiv;// 记录
		int[] levelSizes, // 存储每一层的余数
		levelDivs;// 存储每一层的除数

		Map textsMap = new HashMap();
		List textList = null;
		String text = null;
		for (int iBlock = 0; iBlock < blocks.length; iBlock++) {
			itemCounts = 1;
			levelDiv = 1;
			metaCount = 0;
			
			block = blocks[iBlock];
			items = block.getItems();
			
			itemLength = items.length;
			maxLevels = Math.max(maxLevels,itemLength);
			levelDivs = new int[itemLength+1];
			levelSizes = new int[itemLength+1];
			
			levelDivs[itemLength] = 1;
			levelSizes[itemLength] = 1;
			
			for (int i = itemLength ; i > 0; i--) {
				item = items[i - 1];
				metaCount = item.getMetadatas().size();// item里面的元数据数目
				itemCounts *= metaCount;
				if(item.isShowCode()&&blocks.length==1){//
					this.setShowCode(true);
				}
				levelSizes[i-1] = metaCount;
				levelDiv = levelDivs[i]*levelSizes[i];
				levelDivs[i-1] = levelDiv;
				levelDiv = 1;
				item = null;
			}
			int index = 0;
			int colIndex = 0;
			
			
			for(int row=0;row<itemLength;row++){
				for(int col=0;col<itemCounts;col++){
					index = new Double(Math.floor(col/levelDivs[row])%levelSizes[row]).intValue();
					colIndex = col+blockCount;
					if(!textsMap.containsKey("_"+colIndex)){
						textList = new ArrayList();
						textsMap.put("_"+colIndex, textList);
					}else{
						textList = (List)textsMap.get("_"+colIndex);
					}
					if(items[row].getMetadatas().size()>index){
						text = items[row].getMetadata(index).getText();
					}else {
						text = "";
					}
//					System.out.println("("+row+"-"+col+")text:"+text);
					textList.add(text);
				}
			}
			
			blockCount += itemCounts;
		}
		//设置属性值
		this.textArray = new String[maxLevels][blockCount];
		this.counts = blockCount;
		this.levels = maxLevels;
		this.countsMaxLength = new int[blockCount];
		this.levelsMaxLength = new int[maxLevels];
		
		for(int col = 0;col<blockCount;col++){
			textList = (List)textsMap.get("_"+col);
			for(int row=0;row<maxLevels;row++){
				try {
					text = (String)textList.get(row);
				} catch (RuntimeException e) {
					text = "";
				}
				//1个中文替换为两个空格来计算宽度
				int textLength = Pattern.compile("[\u4e00-\u9fa5]").
						matcher(text.trim().replaceAll("&nbsp;"," ")).replaceAll("  ").length()+1;
				this.countsMaxLength[col] = Math.max(this.countsMaxLength[col], textLength);
				this.levelsMaxLength[row] = Math.max(this.levelsMaxLength[row], textLength);
				this.textArray[row][col] = text;
				text = null;
				//System.out.println("("+row+"-"+col+")text:"+textArray[row][col]);
			}
		}
	}
}
