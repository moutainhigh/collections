package com.gwssi.dw.runmgr.services;

import java.util.Map;

import com.genersoft.frame.base.database.DBException;


public interface GeneralService
{
	Map query(Map params) throws DBException;
}
