package com.gwssi.rodimus.bjca;

public interface BjcaEngine {

	public String getServerCertificate();
	
	public String genRandom(int cnt);
	
	public String signData(byte[] text);
	
	public String signData(String text);
	
	public String getCertInfo(String text,int cnt);
	
	public byte[] base64Decode(String text);
	
	public boolean verifySignedData(String text,byte[] btye0,byte[] byte1);
	
	public String priKeyDecrypt(String text);
	
	public int validateCert(String text);
	
	public String getCertInfoByOid(String text1,String text2);
	
	public byte[] hashData(byte[] text);

	public String base64Encode(byte[] text);

	public String getP7SignatureInfo(String text, int cnt);

	public boolean verifySignedDataByP7Attach(String text);

	public int verifySignatureByCertOrSN(String text1, String text2,
			String text3, String  text4);
}
