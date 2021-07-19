package com.gwssi.ad;

import java.util.Map;

public class PersonSearchUtil2 {

	public static void main(String[] args) {
		AdDao ad =new AdDao();
		Person p = new Person();
		//p.setDistinguishedName("CN=张炜健,OU=香蜜湖所,OU=福田局派出机构,OU=福田局,OU=委派出机构,OU=深圳市市场和质量监督管理委员会,DC=szaic,DC=gov,DC=cn");
		//p.setDistinguishedName("CN=黄海霞,OU=香蜜湖所,OU=福田局派出机构,OU=福田局,OU=委派出机构,OU=深圳市市场和质量监督管理委员会,DC=szaic,DC=gov,DC=cn");
		p.setDistinguishedName("香蜜湖所,福田局派出机构,福田局,委派出机构,深圳市市场和质量监督管理委员会");
		p.setCn("张三丰");
		p.setsAMAccountName("zhangsf1@szaic");
		p.setDisplayName("张三丰");
		p.setOu("CN=黄海霞,OU=香蜜湖所,OU=福田局派出机构,OU=福田局,OU=委派出机构,OU=深圳市市场和质量监督管理委员会,DC=szaic,DC=gov,DC=cn");
		ad.createOnePerson(p);
	}

}
