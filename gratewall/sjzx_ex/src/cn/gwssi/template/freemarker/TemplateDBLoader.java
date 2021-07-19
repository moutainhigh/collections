package cn.gwssi.template.freemarker;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

import freemarker.cache.TemplateLoader;

public class TemplateDBLoader implements TemplateLoader
{
	public void closeTemplateSource(Object templateSource) throws IOException
	{

	}

	public Object findTemplateSource(String param) throws IOException
	{
		System.out.println("º”‘ÿ¡Àƒ£∞Ê");
		try {
			DBOperation operation = DBOperationFactory.createOperation();
			param=param.split("_")[0];
			Map map = operation.selectOne("select * from sys_search_svr where svr_name='"
					+ param + "'");
			return map.get("SVR_TEMPLATE").toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public long getLastModified(Object arg0)
	{
		return -1l;
	}

	public Reader getReader(Object templateSource, String encodeType)
			throws IOException
	{
		String c = (String) templateSource;
		StringReader stringReader=new StringReader(c);
		return stringReader;
	}

}
