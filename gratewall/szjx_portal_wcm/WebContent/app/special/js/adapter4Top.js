//adapter this only for load once.
Ext.ns('wcm.LANG', 'wcm.util.Observable', 'Ajax.Request', 'com.trs.web2frame.BasicDataHelper', 'com.trs.web2frame.PostData');

//get the actualTop
var win = top;

//const
WCMConstants = win.WCMConstants;
wcm.LANG = win.wcm.LANG;
WCMLANG = wcm.LANG;

//Observable
wcm.util.Observable = win.wcm.util.Observable;
wcm.ComponentMgr = win.wcm.ComponentMgr
wcmXCom = win.wcmXCom;
wcm.Component = win.wcm.Component;
wcm.BoxComponent = win.wcm.BoxComponent
wcm.Container = win.wcm.Container;
wcm.Panel = win.wcm.Panel;

//Crashboard;
wcm.CrashBoard = win.wcm.CrashBoard
wcm.CrashBoarder = win.wcm.CrashBoarder;

//dialog;
wcm.MessageBox = win.wcm.MessageBox;
wcm.ReportDialog = win.wcm.ReportDialog;
wcm.FaultDialog = win.wcm.FaultDialog;
Ext.Msg = win.Ext.Msg;

//ajax
Ajax.Request = win.Ajax.Request;
BasicDataHelper = win.BasicDataHelper;
com.trs.web2frame.BasicDataHelper = win.com.trs.web2frame.BasicDataHelper;
com.trs.web2frame.PostData = win.com.trs.web2frame.PostData;
