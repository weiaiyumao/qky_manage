package com.zqfinance.system.util;

import java.io.IOException;

/**
 * 
 * @author: oliver
 * @time: 2013-01-09
 */
public class Base64Support {

	/**
	 * 将base64种的敏感字符+,/,=转化为_,-,. 以及base64会在编码串中产生换行符，虽然解码会不管
	 * 
	 * @author: Oliver
	 * @param str
	 * @return
	 */
	public static String toUrlStr(byte[] bytes) {

		String str = new sun.misc.BASE64Encoder().encode(bytes);

		if (str == null)
			return "";

		str = str.replaceAll("\\+", "_");
		str = str.replaceAll("/", "-");
		str = str.replaceAll("=", ".");
		str = str.replaceAll("\\s", "");

		return str;
	}

	/**
	 * 是toUrlStr逆过程
	 * 
	 * @author: Oliver
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static byte[] fromUrlStr(String str) throws IOException {

		if (str == null)
			return null;

		str = str.replaceAll("_", "+");
		str = str.replaceAll("-", "/");
		str = str.replaceAll("\\.", "=");

		byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

		return dec;
	}

}
