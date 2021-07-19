package com.gwssi.adtransfer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Leezen
 * 
 *         生成dn
 * 
 */
public class TreeIterator {
	public static String ROOT_FLAG = "1";

	public Map iteratorNode(List<TreeNode> nodeLst) {
		Map strMap = new HashMap();
		Map<String, TreeNode> nodeMap = new HashMap<String, TreeNode>();
		for (TreeNode curNode : nodeLst) {
			nodeMap.put(curNode.getNodeId(), curNode);
		}

		for (TreeNode curNode : nodeLst) {
			if (curNode.getParentId() == ROOT_FLAG) {
				strMap.put(curNode.getNodeId(), curNode.getText());
			} else {
				StringBuffer str = new StringBuffer();
				str.append("OU=");
				str.append(curNode.getText());
				str.append(",");
				TreeNode tmpNode = nodeMap.get(curNode.getParentId());
				while ((tmpNode != null)
						&& !tmpNode.getNodeId().equals(ROOT_FLAG)) {
					str.append("OU=");
					str.append(tmpNode.getText());
					str.append(",");
					tmpNode = nodeMap.get(tmpNode.getParentId());
				}
				strMap.put(curNode.getNodeId(),
						str.append("DC=szaic,DC=gov,DC=cn").toString());
			}
		}
		return strMap;
	}

	public static void main(String[] args) {

	}
}
