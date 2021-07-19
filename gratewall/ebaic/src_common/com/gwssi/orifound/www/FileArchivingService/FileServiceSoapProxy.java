package com.gwssi.orifound.www.FileArchivingService;

public class FileServiceSoapProxy implements com.gwssi.orifound.www.FileArchivingService.FileServiceSoap {
  private String _endpoint = null;
  private com.gwssi.orifound.www.FileArchivingService.FileServiceSoap fileServiceSoap = null;
  
  public FileServiceSoapProxy() {
    _initFileServiceSoapProxy();
  }
  
  public FileServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initFileServiceSoapProxy();
  }
  
  private void _initFileServiceSoapProxy() {
    try {
      fileServiceSoap = (new com.gwssi.orifound.www.FileArchivingService.FileServiceLocator()).getFileServiceSoap();
      if (fileServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)fileServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)fileServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (fileServiceSoap != null)
      ((javax.xml.rpc.Stub)fileServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.gwssi.orifound.www.FileArchivingService.FileServiceSoap getFileServiceSoap() {
    if (fileServiceSoap == null)
      _initFileServiceSoapProxy();
    return fileServiceSoap;
  }
  
  public java.lang.String fileArchiving(java.lang.String userName, java.lang.String userPwd, java.lang.String admCode, java.lang.String ftpPDFFile, java.lang.String ftpArchiveXMLFile) throws java.rmi.RemoteException{
    if (fileServiceSoap == null)
      _initFileServiceSoapProxy();
    return fileServiceSoap.fileArchiving(userName, userPwd, admCode, ftpPDFFile, ftpArchiveXMLFile);
  }
  
  
}