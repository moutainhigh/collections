package com.gwssi.optimus.plugin.auth.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Role {

  

	private String roleId;
    private transient  List<String> urlRightList;
    private transient  List<String> widgetRightList;
    
    private Set<Map> urlSet;
    private Set<String> widgetCodeSet;
    
    public String getRoleId() {
        return roleId;
    }
    
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    
    public Set<Map> getUrlSet() {
        return urlSet;
    }

    public void setUrlSet(Set<Map> urlSet) {
        this.urlSet = urlSet;
    }

    public Set<String> getWidgetCodeSet() {
        return widgetCodeSet;
    }

    public void setWidgetCodeSet(Set<String> widgetCodeSet) {
        this.widgetCodeSet = widgetCodeSet;
    }

    public List<String> getUrlRightList() {
        return urlRightList;
    }

    public void setUrlRightList(List<String> urlRightList) {
        this.urlRightList = urlRightList;
    }

    public List<String> getWidgetRightList() {
        return widgetRightList;
    }

    public void setWidgetRightList(List<String> widgetRightList) {
        this.widgetRightList = widgetRightList;
    }

    protected void transferRight() {
//        if(funcRightList!=null && funcRightList.size()>0){
//            funcUrlSet = new HashSet<String>();
//            for(FuncRight funcRight : funcRightList){
//                funcUrlSet.add(funcRight.getUrl());
//            }
//        }
//        if(operRightList!=null && operRightList.size()>0){
//            operIdSet = new HashSet<String>();
//            operUrlSet = new HashSet<String>();
//            for(OperRight operRight : operRightList){
//                operIdSet.add(operRight.getOperId());
//                operUrlSet.add(operRight.getUrl());
//            }
//        }
    }
    /*
    public static void main(String[] args) {
        Role role = new Role();
        URLRight urlRight = new URLRight();
        urlRight.setId("sdf");
        urlRight.setUrl("fdsq");
        List urlList = new ArrayList();
        urlList.add(urlRight);
        WidgetRight widgetRight = new WidgetRight();
        widgetRight.setId("fsad");
        widgetRight.setWidgetCode("fdwwsss");
        List widgetList = new ArrayList();
        widgetList.add(widgetRight);
        role.setUrlRightList(urlList);
        role.setWidgetRightList(widgetList);
        byte[] data = SerializeUtil.serialize(role);
        Role role2 = SerializeUtil.unserialize(data, Role.class);
        System.out.println();
    }*/
    
    @Override
  	public String toString() {
  		return "Role [roleId=" + roleId + ", urlSet=" + urlSet
  				+ ", widgetCodeSet=" + widgetCodeSet + "]";
  	}
}
