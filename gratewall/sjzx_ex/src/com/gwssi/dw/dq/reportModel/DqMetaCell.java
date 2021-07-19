/**
 * 
 */
package com.gwssi.dw.dq.reportModel;

/**
 * <p>Title: </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) Sep 30, 2008</p>
 * <p>Company: </p>
 * @author zhouyi
 * @version 1.0
 */
public class DqMetaCell {
	private DqMetadata dqMetadata;

	/**
	 * @return the dqMetadata
	 */
	public DqMetadata getDqMetadata() {
		return dqMetadata;
	}

	/**
	 * @param dqMetadata the dqMetadata to set
	 */
	public void setDqMetadata(DqMetadata dqMetadata) {
		this.dqMetadata = dqMetadata;
	}
	
	public String getText(){
		String text ="";
		if(dqMetadata!=null){
			text = dqMetadata.getText();
		}
		return text;
	}
}
