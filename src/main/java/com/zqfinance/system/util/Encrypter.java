package com.zqfinance.system.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class Encrypter {
	private static Cipher ecipher;

	private static Cipher dcipher;

	// 必须24个字符
	private static final String key = "*:@1$7!a*:@1$7!a*:@1$7!a";

	private static final String alg = "DESede";

	static {
		try {

			SecretKey skey = new SecretKeySpec(key.getBytes(), alg);

			ecipher = Cipher.getInstance(alg);
			dcipher = Cipher.getInstance(alg);
			ecipher.init(Cipher.ENCRYPT_MODE, skey);
			dcipher.init(Cipher.DECRYPT_MODE, skey);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密字符串
	 * 
	 * 
	 * @param str
	 * @return
	 */
	public static String encrypt(String str) {
		if (str == null)
			return "";
		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF-8");

			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);

			// Encode bytes to base64 to get a string
			return Base64Support.toUrlStr(enc);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public static String dotEncrypt(String str) {
		if (str == null)
			return "";
		try {
			byte[] utf8 = str.getBytes("UTF-8");
			byte[] enc = ecipher.doFinal(utf8);
			String result = Base64Support.toUrlStr(enc);
			if(".".equals(new String(new char[] {result.charAt(result.length()-1)}))) {
				result = result.substring(0, result.length()-1)+"_";
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}
	
	/**
	 * 解密字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String decrypt(String str) {
		if (str == null)
			return "";
		try {
			// Decode base64 to get bytes
			byte[] dec = Base64Support.fromUrlStr(str);

			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}
	
	public static String dotDecrypt(String str) {
		if (str == null)
			return "";
		try {
			if("_".equals(new String(new char[] {str.charAt(str.length()-1)}))) {
				str = str.substring(0, str.length()-1)+".";
			}
			byte[] dec = Base64Support.fromUrlStr(str);
			byte[] utf8 = dcipher.doFinal(dec);
			return new String(utf8, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static void main(String[] args) {
		 System.out.println(encrypt("qky123456"));
		 System.out.println(decrypt("IWXlvzPyAusSJQZ7Jv4nIg.."));
	}

}

