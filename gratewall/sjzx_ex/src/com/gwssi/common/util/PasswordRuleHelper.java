/**
 * 
 */
package com.gwssi.common.util;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;

import cn.gwssi.common.component.Config;

/**
 * @author zhyi
 *
 */
public class PasswordRuleHelper
{
	//Config
	public static Document getPasswordRuleXml(){
		SAXReader sax = new SAXReader();
		Document doc = null;
		String passwordRulePath = Config.getRootPath()+"/WEB-INF/password.xml";
		try {
			doc = sax.read(new File(passwordRulePath));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return doc;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
