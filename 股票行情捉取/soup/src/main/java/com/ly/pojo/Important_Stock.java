package com.ly.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;


/*DROP TRIGGER
IF EXISTS UPDATESTS;
CREATE TRIGGER UPDATESTS BEFORE UPDATE ON BUY FOR EACH ROW
BEGIN
IF NEW.`CODE` LIKE '000%' THEN
SET NEW.MARKETTYPE = '4609';
SET NEW.EXCHANGE_TYPE = '2';

ELSEIF NEW.`CODE` LIKE '002%' THEN
SET NEW.MARKETTYPE = '4614';
SET NEW.EXCHANGE_TYPE = '2';

ELSEIF NEW.`CODE` LIKE '300%' THEN
SET NEW.MARKETTYPE = '4621';
SET NEW.EXCHANGE_TYPE = '2';

ELSEIF NEW.`CODE` LIKE '60%' THEN
SET NEW.MARKETTYPE = '4353';
SET NEW.EXCHANGE_TYPE = '1';
END
IF;
END
*/
//http://www.cnblogs.com/liangxinxinbo/p/6092664.html
//https://www.sojson.com/sql.html
//https://www.cnblogs.com/qlqwjy/p/9545453.html
//https://blog.csdn.net/qq_37782076/article/details/83753076
@Entity
@DynamicUpdate(true)
@Table(indexes={@Index(name="code",columnList="code"),@Index(name="name",columnList="name")})
public class Important_Stock implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private String addtime;
	
	
	@Column
	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	@Id
	public String getCode() {
		return code;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
