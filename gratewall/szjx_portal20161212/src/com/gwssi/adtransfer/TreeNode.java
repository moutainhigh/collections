package com.gwssi.adtransfer;

/**
 * 
 * @author Leezen
 * 
 *         树形结构
 */
public class TreeNode {
	private String nodeId;
	private String parentId;
	private String text;

	public TreeNode(String nodeId, String text, String parentId) {
		this.nodeId = nodeId;
		this.parentId = parentId;
		this.text = text;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
