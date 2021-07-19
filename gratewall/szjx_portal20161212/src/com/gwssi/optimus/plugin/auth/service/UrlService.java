package com.gwssi.optimus.plugin.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gwssi.application.model.SmWhiteFunctionBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class UrlService extends BaseService {

	/**
	 * @param tPtUrlzyBO
	 * @throws OptimusException
	 */
	public void saveUrl(SmWhiteFunctionBO bo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(bo);
	}

	/**
	 * @param urlId
	 * @return
	 * @throws OptimusException
	 */
	public SmWhiteFunctionBO getUrl(String urlId) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		SmWhiteFunctionBO whiteFunction = dao.queryByKey(
				SmWhiteFunctionBO.class, urlId);
		return whiteFunction;
	}

	/**
	 * @param urlMc
	 * @return
	 * @throws OptimusException
	 */
	public List<SmWhiteFunctionBO> queryUrl(String urlMc)
			throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SM_WHITE_FUNCTION t where 1=1 ");
		if (urlMc == null) {
		} else {
			sql.append(" and t.FUNCTION_NAME like '" + urlMc + "%'");
		}
		List<SmWhiteFunctionBO> userList = dao.pageQueryForList(
				SmWhiteFunctionBO.class, sql.toString(), null);
		return userList;
	}

	/**
	 * @param tPtUrlzyBO
	 * @throws OptimusException
	 */
	public void updateUrl(SmWhiteFunctionBO bo) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(bo);
	}

	/**
	 * @param tPtUrlzyBO
	 * @throws OptimusException
	 */
	public void deleteUrl(SmWhiteFunctionBO bo) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		dao.delete(bo);
	}

}
