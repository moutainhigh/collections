package com.gwssi.rodimus.bjca;

import cn.org.bjca.client.exceptions.ParameterInvalidException;
import cn.org.bjca.client.exceptions.ParameterOutRangeException;
import cn.org.bjca.client.exceptions.ParameterTooLongException;
import cn.org.bjca.client.exceptions.SVSConnectException;
import cn.org.bjca.client.exceptions.UnkownException;
import cn.org.bjca.client.security.SecurityEngineDeal;

public class BjcaEngineImpl implements BjcaEngine {
	
	private SecurityEngineDeal sed = null;
	
	public BjcaEngineImpl(){
		try {
	  		String webappName = "SVSDefault";
			this.sed = SecurityEngineDeal.getInstance(webappName);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	
	public String signData(String text){
		try {
			return this.sed.signData(text);
		} catch (SVSConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParameterTooLongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	
	
	public String getServerCertificate() {
		try {
			return this.sed.getServerCertificate();
		} catch (SVSConnectException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String genRandom(int cnt) {
		try {
			return this.sed.genRandom(cnt);
		} catch (SVSConnectException e) {
			e.printStackTrace();
		} catch (ParameterOutRangeException e) {
			e.printStackTrace();
		}
		return null;
	}


	public String signData(byte[] text) {
		try {
			return this.sed.signData(text);
		} catch (SVSConnectException e) {
			e.printStackTrace();
		} catch (ParameterTooLongException e) {
			e.printStackTrace();
		}
		return null;
	}


	public String getCertInfo(String text, int cnt) {
		try {
			return this.sed.getCertInfo(text, cnt);
		} catch (SVSConnectException e) {
			e.printStackTrace();
		} catch (ParameterTooLongException e) {
			e.printStackTrace();
		} catch (ParameterOutRangeException e) {
			e.printStackTrace();
		} catch (ParameterInvalidException e) {
			e.printStackTrace();
		}
		return null;
	}


	public byte[] base64Decode(String text) {
		return this.sed.base64Decode(text);
	}


	public boolean verifySignedData(String text, byte[] btye0, byte[] byte1) {
		try {
			return this.sed.verifySignedData(text, btye0, byte1);
		} catch (SVSConnectException e) {
			e.printStackTrace();
		} catch (ParameterTooLongException e) {
			e.printStackTrace();
		} catch (ParameterInvalidException e) {
			e.printStackTrace();
		} catch (UnkownException e) {
			e.printStackTrace();
		}
		return false;
	}


	public String priKeyDecrypt(String text) {
		try {
			return this.sed.priKeyDecrypt(text);
		} catch (SVSConnectException e) {
			e.printStackTrace();
		} catch (ParameterTooLongException e) {
			e.printStackTrace();
		}
		return null;
	}


	public int validateCert(String text) {
		try {
			return this.sed.validateCert(text);
		} catch (SVSConnectException e) {
			e.printStackTrace();
		} catch (ParameterTooLongException e) {
			e.printStackTrace();
		} catch (ParameterInvalidException e) {
			e.printStackTrace();
		} catch (ParameterOutRangeException e) {
			e.printStackTrace();
		}
		return 0;
	}


	public String getCertInfoByOid(String text1, String text2) {
		try {
			return this.sed.getCertInfoByOid(text1, text2);
		} catch (SVSConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParameterTooLongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParameterOutRangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParameterInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public byte[] hashData(byte[] text) {
		return this.sed.hashData(text);
	}


	public String base64Encode(byte[] text) {
		return this.sed.base64Encode(text);
	}


	public String getP7SignatureInfo(String text, int cnt) {
		try {
			return this.sed.getP7SignatureInfo(text, cnt);
		} catch (SVSConnectException e) {
			e.printStackTrace();
		} catch (ParameterTooLongException e) {
			e.printStackTrace();
		} catch (ParameterOutRangeException e) {
			e.printStackTrace();
		} catch (ParameterInvalidException e) {
			e.printStackTrace();
		}
		return null;
	}


	public boolean verifySignedDataByP7Attach(String text) {
		try {
			return this.sed.verifySignedDataByP7Attach(text);
		} catch (SVSConnectException e) {
			e.printStackTrace();
		} catch (ParameterTooLongException e) {
			e.printStackTrace();
		} catch (ParameterInvalidException e) {
			e.printStackTrace();
		}
		return false;
	}


	public int verifySignatureByCertOrSN(String text1, String text2,
			String text3, String text4) {
		try {
			return this.sed.verifySignatureByCertOrSN(text1, text2, text3, text4);
		} catch (ParameterTooLongException e) {
			e.printStackTrace();
		} catch (SVSConnectException e) {
			e.printStackTrace();
		} catch (ParameterOutRangeException e) {
			e.printStackTrace();
		} catch (ParameterInvalidException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
