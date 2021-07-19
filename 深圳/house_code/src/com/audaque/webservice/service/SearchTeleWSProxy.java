package com.audaque.webservice.service;

public class SearchTeleWSProxy implements com.audaque.webservice.service.SearchTeleWS {
  private String _endpoint = null;
  private com.audaque.webservice.service.SearchTeleWS searchTeleWS = null;
  
  public SearchTeleWSProxy() {
    _initSearchTeleWSProxy();
  }
  
  public SearchTeleWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initSearchTeleWSProxy();
  }
  
  private void _initSearchTeleWSProxy() {
    try {
      searchTeleWS = (new com.audaque.webservice.service.SearchTeleWSImplServiceLocator()).getSearchTeleWSImplPort();
      if (searchTeleWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)searchTeleWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)searchTeleWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (searchTeleWS != null)
      ((javax.xml.rpc.Stub)searchTeleWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.audaque.webservice.service.SearchTeleWS getSearchTeleWS() {
    if (searchTeleWS == null)
      _initSearchTeleWSProxy();
    return searchTeleWS;
  }
  
  public java.lang.String searchTele_Houses(java.lang.String key, java.lang.String name, java.lang.String code, java.lang.Integer page, java.lang.Integer rows) throws java.rmi.RemoteException{
    if (searchTeleWS == null)
      _initSearchTeleWSProxy();
    return searchTeleWS.searchTele_Houses(key, name, code, page, rows);
  }
  
  public java.lang.String searchTele_Building(java.lang.String key, java.lang.String name, java.lang.String code, java.lang.Integer page, java.lang.Integer rows) throws java.rmi.RemoteException{
    if (searchTeleWS == null)
      _initSearchTeleWSProxy();
    return searchTeleWS.searchTele_Building(key, name, code, page, rows);
  }
  
  public java.lang.String building_Houses(java.lang.String key, java.lang.String building_id, java.lang.Integer page, java.lang.Integer rows) throws java.rmi.RemoteException{
    if (searchTeleWS == null)
      _initSearchTeleWSProxy();
    return searchTeleWS.building_Houses(key, building_id, page, rows);
  }
  
  public java.lang.String houses_MoreAddress(java.lang.String key, java.lang.String name, java.lang.String code, java.lang.Integer page, java.lang.Integer rows) throws java.rmi.RemoteException{
    if (searchTeleWS == null)
      _initSearchTeleWSProxy();
    return searchTeleWS.houses_MoreAddress(key, name, code, page, rows);
  }
  
  
}