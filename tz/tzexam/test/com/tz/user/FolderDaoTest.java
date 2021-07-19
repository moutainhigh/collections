/**
 * Project Name:tzexam
 * File Name:UserDaoTest.java
 * Package Name:com.tz.user
 * Date:2015年10月19日上午12:56:20
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.tz.user;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tz.dao.FolderDao;
import com.tz.dao.TzParams;
import com.tz.model.Folder;

/**
 * ClassName:UserDaoTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年10月19日 上午12:56:20 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class FolderDaoTest {
	
	@Autowired
	private FolderDao folderDao;
	
	@Test
	public void handler(){
		TzParams params = new TzParams();
		params.setSubjectId(1);
		params.setStatus(1);
		List<Folder> folders = folderDao.findFolders(params);
		for (Folder folder : folders) {
			System.out.println(folder.getId()+"==="+folder.getName());
		}
	}

}

