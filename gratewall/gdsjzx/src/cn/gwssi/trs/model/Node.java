package cn.gwssi.trs.model;

public class Node {
	private String nodeId=null;
	private String nodeName=null;
	private String nodeType=null;
	private String nodeEntType=null;
	private String nodeGen=null;
	
	public Node(String nodeId,String nodeName,String nodeType,String nodeEntType,String nodeGen){
		this.nodeId=nodeId;//outPripid
		this.nodeName=nodeName;//outName
		this.nodeType=nodeType;//outType
		this.nodeEntType=nodeEntType;//nodeEntType
		this.nodeGen=nodeGen;//nodeGen
	}
	
	public String getNodeId(){
		return nodeId;
	}
	
	public String getNodeName(){
		return nodeName;
	}
	
	public String getNodeType(){
		return nodeType;
	}
	
	public String getNodeEntType(){
		return nodeEntType;
	}
	
	public String getNodeGen(){
		return nodeGen;
	}
}
