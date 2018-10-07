package com.mine.App.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.HashMap;
import java.util.Map;

public class Encrypt
{
  public static void main(String[] args)
    throws Exception
  {
	 /*
    long a = System.currentTimeMillis();
    for (int i = 0; i < 1; ++i) {
      System.out.println(encrypt("0123456789123456", ""));
    }

    long b = System.currentTimeMillis();

    System.out.println(b - a);
    */
	String encryptPwd = "BCFC5967D714FB80";
	String keyValue = "A7849203ECBC99A9";
	String desEncryptPwd = Encrypt.encrypt1(encryptPwd, keyValue);
	System.out.println("PWD IS:"+desEncryptPwd);
  }

  public static String encrypt(String messageValue, String keyValue)
    throws Exception
  {
    if ((keyValue == null) || (keyValue.equals(""))) {
      keyValue = "B794025339EC98E7";
    }

    return Tools.BcdToAsc(desEncrypt(messageValue, keyValue));
  }

  public static byte[] desEncrypt(String message, String key)
    throws Exception
  {
    Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
    //System.out.println("keye is"+Tools.AscToBcd(key).toString());
    //System.out.println("keyelength is"+Tools.AscToBcd(key).length);
    DESKeySpec desKeySpec = new DESKeySpec(Tools.AscToBcd(key));
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
    cipher.init(1, secretKey);
    //System.out.println("oldPWd is"+new String(Tools.AscToBcd(message)));
    byte[] as = cipher.doFinal(Tools.AscToBcd(message));
    return cipher.doFinal(Tools.AscToBcd(message));
  }
  
  public static String encrypt1(String messageValue, String keyValue)
		    throws Exception
		  {
		    if ((keyValue == null) || (keyValue.equals(""))) {
		      keyValue = "B794025339EC98E7";
		    }

		    return Tools.BcdToAsc(desEncrypt1(messageValue, keyValue));
		  }
  
  public static byte[] desEncrypt1(String message, String key)
		    throws Exception
		  {
		    Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
		    //System.out.println("keye is"+Tools.AscToBcd(key).toString());
		    //System.out.println("keyelength is"+Tools.AscToBcd(key).length);
		    DESKeySpec desKeySpec = new DESKeySpec(Tools.AscToBcd(key));
		    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		    cipher.init(2, secretKey);
		    //System.out.println("oldPWd is"+new String(Tools.AscToBcd(message)));
		    byte[] as = cipher.doFinal(Tools.AscToBcd(message));
		    return cipher.doFinal(Tools.AscToBcd(message));
		  }
  
  public Map<String,Object> pPubDoBCP(Map<String,Object> inParam){
	  
	  Map<String,Object> rpMap = new HashMap<String, Object>();
	 /* PassWordUtils passWordUtils = new PassWordUtils();
	  if(StringUtils.equals((String)inParam.get("FLAG"), "1")){
		  
		  Map<String,Object> uPubDecryptInMap = new HashMap<String, Object>();
		  Map<String,Object> uDecryptInMap = new HashMap<String, Object>();
		  
		  uPubDecryptInMap.put("OP_FLAG", 1);//加密标识
		  uDecryptInMap.put("DECRYPT_NAME", ((String)inParam.get("DEAL_VALUE")).trim());
		  if(inParam.get("DECRYPT_TYPE") != null){
			  
			  uDecryptInMap.put("DECRYPT_TYPE", inParam.get("DECRYPT_TYPE"));
		  }
		  if(inParam.get("DECRYPT_ADDTYPE") != null){
			  
			  uDecryptInMap.put("DECRYPT_ADDTYPE", inParam.get("DECRYPT_ADDTYPE"));
		  }
		  uPubDecryptInMap.put("DECRYPT_INFO", uDecryptInMap);
		  
		  String uPubDecryptOut = passWordUtils.pubDecrypt((String)uPubDecryptInMap.get("OP_FLAG"), (String)uPubDecryptInMap.get("DECRYPT_INFO"));
		  
		  if(uPubDecryptOut != null){
			  
			  rpMap.put("MH_WEN", uPubDecryptOut);//取模糊化名称
			  rpMap.put("MI_WEN", uPubDecryptOut);//加密名称
			  rpMap.put("MW_WEN", inParam.get("DEAL_VALUE"));//明文
		  
		  }
	  }else{//解密
		  
		  Map<String,Object> pPubDecryptInMap = new HashMap<String, Object>();
		  Map<String,Object> uEncryptInfoMap = new HashMap<String, Object>();
		  
		  	pPubDecryptInMap.put("OP_FLAG","0");

			uEncryptInfoMap.put("ENCRYPT_NAME",inParam.get("DEAL_VALUE"));
			uEncryptInfoMap.put("FUNC_NAME",inParam.get("FUNC_NAME"));
			uEncryptInfoMap.put("DECRYPT_TYPE",inParam.get("NAME_ENCRYPT"));
			uEncryptInfoMap.put("IP_ADDRESS","1.1.1.1");
			uEncryptInfoMap.put("LOGIN_NO","M00000SYS");
			uEncryptInfoMap.put("OP_CODE","1000");
			uEncryptInfoMap.put("REMARK1","地址解密");
			uEncryptInfoMap.put("REMARK2","");
			uEncryptInfoMap.put("REMARK3","");
			uEncryptInfoMap.put("PHONE_NO","");
			uEncryptInfoMap.put("CUST_ID",0);
			uEncryptInfoMap.put("ID_NO",0);
			uEncryptInfoMap.put("CONTRACT_NO",0);
			pPubDecryptInMap.put("ENCRYPT_INFO",uEncryptInfoMap);
			
			String pPubDecryptOut = passWordUtils.pubDecrypt((String)pPubDecryptInMap.get("OP_FLAG"), (String)pPubDecryptInMap.get("ENCRYPT_INFO"));
			
			if(pPubDecryptOut != null){
				
				if(pPubDecryptOut.indexOf("＊") != -1  || pPubDecryptOut.indexOf("*") != -1){
					
					rpMap.put("MH_WEN", pPubDecryptOut);
					rpMap.put("MI_WEN", inParam.get("DEAL_VALUE"));
					rpMap.put("MW_WEN", pPubDecryptOut.trim());
				}else{
					
					Map<String,Object> uDecryptInMap = new HashMap<String, Object>();
					Map<String,Object> uPubDecryptInMap = new HashMap<String, Object>();
					 uPubDecryptInMap.put("OP_FLAG", 1);//加密标识
					 uDecryptInMap.put("DECRYPT_NAME", pPubDecryptOut);//加密
					 if(inParam.get("DECRYPT_TYPE") != null){
						 
						 uDecryptInMap.put("DECRYPT_TYPE", inParam.get("DECRYPT_TYPE"));
					 }
					 
					 if(inParam.get("DECRYPT_ADDTYPE") != null){
						
						 uDecryptInMap.put("DECRYPT_ADDTYPE", inParam.get("DECRYPT_ADDTYPE"));
					 }
					 uPubDecryptInMap.put("DECRYPT_INFO", uDecryptInMap);
					 
					  String str3 = passWordUtils.pubDecrypt((String)uPubDecryptInMap.get("OP_FLAG"), (String)uPubDecryptInMap.get("DECRYPT_INFO"));
					  if(str3!= null){
						  
						  rpMap.put("MH_WEN", str3);//取模糊化名称
						  rpMap.put("MI_WEN", str3);//加密名称
						  rpMap.put("MW_WEN", pPubDecryptOut);//明文
					  }
				}
			}
	  }*/
	  return rpMap;
  }
}
