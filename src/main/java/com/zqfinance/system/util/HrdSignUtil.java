package com.zqfinance.system.util;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class HrdSignUtil {
	
	private static final  Log log = LogFactory.getLog(HrdSignUtil.class);
	
	public static void GenerateKeyPair(String seedkey) {
		String priKey;
		String pubKey;

		try {
			java.security.KeyPairGenerator keygen = java.security.KeyPairGenerator
					.getInstance("RSA");
			SecureRandom secrand = new SecureRandom();
			secrand.setSeed(seedkey.getBytes()); // 初始化随机产生器
			keygen.initialize(1024, secrand);
			KeyPair keys = keygen.genKeyPair();

			PublicKey pubkey = keys.getPublic();
			PrivateKey prikey = keys.getPrivate();

			pubKey = bytesToHexStr(pubkey.getEncoded());
			priKey = bytesToHexStr(prikey.getEncoded());
			
			System.out.println(pubKey);
			System.out.println(priKey);
			log.debug("pubKey=" + pubKey);
			log.debug("priKey=" + priKey);

			log.debug("写入对象 pubkeys ok");
			log.debug("生成密钥对成功");
		} catch (java.lang.Exception e) {
//			e.printStackTrace();
			log.debug("生成密钥对失败");
		}
	}
	
	/*
	 * 签名
	 * @param prikeyvalue 私钥
	 * @param originfo 原始信息
	 * 
	 * @return String 签名信息
	 */
	public static String sign(String prikeyvalue, String originfo) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					hexStrToBytes(prikeyvalue));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey myprikey = keyf.generatePrivate(priPKCS8);

			// 用私钥对信息生成数字签名
			java.security.Signature signet = java.security.Signature
					.getInstance("MD5withRSA");
			signet.initSign(myprikey);
			signet.update(originfo.getBytes("UTF-8"));
			byte[] signed = signet.sign(); // 对信息的数字签名

			String signedStr = bytesToHexStr(signed);
			log.debug("signed(签名内容)原值=" + signedStr);
			log.debug("info（原值）=" + originfo);

			log.debug("签名成功");
			
			return signedStr;
		} catch (java.lang.Exception e) {
//			e.printStackTrace();
			log.warn(e.getMessage());
			log.warn("签名异常");
		}

		return null;
	}
	

	public static JSONObject sign(JSONObject jsonObject, String prikeyvalue, String signkey, String signparam) {

		String singinfo = HrdSignUtil.sign(jsonObject, prikeyvalue, signkey);
		
		jsonObject.put(signparam, singinfo);
		
		return jsonObject;
		
	}
	public static String encodeVal(String str){
		String result = null;
		try {
			result = java.net.URLEncoder.encode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 重定向时参数get请求 加签
	 * @param jsonObject
	 * @return
	 */
	public static String signForRedirect(JSONObject jsonObject, String prikeyvalue, String signkey, String signparam){
		StringBuffer returnStr = new StringBuffer();

		@SuppressWarnings("rawtypes")
		Iterator  iterator = jsonObject.keys();	
		while(iterator.hasNext()){
			String key = (String) iterator.next(); 
			String value = jsonObject.getString(key); 
			String envalue = encodeVal(value);
			returnStr.append(key).append("=").append(envalue).append("&");
        }
		
		String singinfo = sign(jsonObject, prikeyvalue, signkey);
	
		returnStr.append(signparam).append("=").append(singinfo);
		return returnStr.toString();
	}
	
	public static String sign(JSONObject jsonObject, String prikeyvalue, String signkey) {
		StringBuffer sb = new StringBuffer();

		@SuppressWarnings("unchecked")
		ArrayList<String> keyList = new ArrayList<String>(jsonObject.keySet());
		
		Collections.sort(keyList); 
		
		@SuppressWarnings("rawtypes")
		Iterator iterator = keyList.iterator();
		
		while(iterator.hasNext()){
			String key = (String) iterator.next(); 
			String value = jsonObject.getString(key); 
			sb.append(key).append("=").append(value).append("&");
        }

		// 最后加上签名密码
		sb.append(signkey);
		
		log.debug("sign data:" + sb.toString());
		
		return HrdSignUtil.sign(prikeyvalue, sb.toString());
		
	}

	
	public static boolean verify(HttpServletRequest request, String pubkeyvalue, String signparam, String signkey) {
		String singedinfo = request.getParameter(signparam);
		String originfo = getSignOrigInfo(request, signparam, signkey);

		return verify(pubkeyvalue,singedinfo, originfo);
	}
	
	/*
	 * 验签
	 * @param pubkeyvalue 公钥
	 * @param singedinfo 签名信息
	 * @param originfo 原始信息
	 * 
	 * @return boolean
	 */
	public static boolean verify(String pubkeyvalue, String singedinfo, String originfo) {
		try {
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
					hexStrToBytes(pubkeyvalue));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);

			byte[] signed = hexStrToBytes(singedinfo);// 这是SignatureData输出的数字签名
			java.security.Signature signetcheck = java.security.Signature
					.getInstance("MD5withRSA");
			signetcheck.initVerify(pubKey);
			signetcheck.update(originfo.getBytes("UTF-8"));
			if (signetcheck.verify(signed)) {
				log.debug("info=" + originfo);
				log.debug("验签成功");
				return true;
			} else {
				log.warn("验签失败");
			}
		} catch (java.lang.Exception e) {
//			e.printStackTrace();
			log.warn(e.getMessage());
			log.warn("验签异常");
		}
		
		return false;
	}
	

	private static String getSignOrigInfo(HttpServletRequest request, String signparam, String signkey) {

		StringBuffer sb = new StringBuffer();
		ArrayList<String> keyList = new ArrayList<String>();
		
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			if (signparam.equals(paraName)) {
				continue;
			}
			
			keyList.add(paraName);
		}
		
		Collections.sort(keyList); 
		
		Iterator<String> iterator = keyList.iterator();
		
		while(iterator.hasNext()){
			String key = (String) iterator.next(); 
			String value = request.getParameter(key); 
			
			sb.append(key).append("=").append(value).append("&");
        }

		sb.append(signkey);

		return sb.toString();
	}


	/**
	 * Transform the specified byte into a Hex String form.
	 */
	private static final String bytesToHexStr(byte[] bcd) {
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (int i = 0; i < bcd.length; i++) {
			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
			s.append(bcdLookup[bcd[i] & 0x0f]);
		}

		return s.toString();
	}

	/**
	 * Transform the specified Hex String into a byte array.
	 */
	private static final byte[] hexStrToBytes(String s) {
		byte[] bytes;

		bytes = new byte[s.length() / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
					16);
		}

		return bytes;
	}

	private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GenerateKeyPair("liCaiPay_HRD800");
		
		String prikeyvalue = "30820277020100300d06092a864886f70d0101010500048202613082025d02010002818100c44135e2f8dc957ace66a36efa4d8f069fb337fcb95f6cc4b39df67be496b4fcee3d857db0f18fd16ef785f7ae43ea9ecc7fd95ed4f02f50f113a4ec01dc3830598a635adb6a2ebd5a0434a856e301bdf99e6dcf92c8c94f996ae0da40da3fd69ba3ef44fe0348772f7eaa234adfb9989b00e1ae685849f67326112c4fc05dcd02030100010281800cd7fbafd3fb93a8cad33ac4eb8aa1de9c1afd82039bb4219763b21adf95e158603b147e6a5314c76f0cb1eec8afd72df75232a69b26f0e1ca352fe8e6224467004e19c533a57005c23bc6a420999f97b6afda94759684593410ff0ca12f1c505789c1380de26eba9f8ea575ceb3449a49b3082abce41b93c9d4794c40d9b8a1024100e28c42fbe2d53a4b3537744abea4128d82f69f47ba1e52f4eb2e9cb7a59a0d39c364979679f539317d8dc8d4df863f50503568a194aaef0d0651e2b739759b05024100ddc4c31ef6096cd2f11415e756d7d7a6fc8037cd03a4700b4578c84e48a89f09b94388febad1adcc9268e92872ce53ee06caa52c475c640cdaefcf27110a8229024100a0f01d81cd51c6f8b8946d83cfccc4f54ccd20b816cb0609c1ad3a5de841f91548ee8415bd0bed16706831fcafd231d63f34c1e0cfb962db30db29211aaba675024100982a7257c42a236979d176560dd87c382ff92a5099b732ca09191f17d7f31b6ce899d65e3281bce7296ea2cd06395c8d6e4b8d9a1c3ce0c991500aab9b9dc24102400db81e9cc65fcdb132dc4b19747a9c99cd504deeff89edee2a43ec4dae089c865f5f43af2c462b8278c1fddc423a3127907c540618f5e5ac412e9b66480ad090";// 这是GenerateKeyPair输出的私钥编码
		String pubkeyvalue = "30819f300d06092a864886f70d010101050003818d0030818902818100c44135e2f8dc957ace66a36efa4d8f069fb337fcb95f6cc4b39df67be496b4fcee3d857db0f18fd16ef785f7ae43ea9ecc7fd95ed4f02f50f113a4ec01dc3830598a635adb6a2ebd5a0434a856e301bdf99e6dcf92c8c94f996ae0da40da3fd69ba3ef44fe0348772f7eaa234adfb9989b00e1ae685849f67326112c4fc05dcd0203010001";// 这是GenerateKeyPair输出的公钥编码
		
		String originfo = "orderId=10dkfadsfksdkssdkd&amount=80&orderTime=20060509"; // 要签名的信息
//		String signedinfo = "562da8483c91c31c9fcd247e00754c9c315a7d7f145181b066b2fb72fa80778c8afce272074655633ab29b4e94a448a5ee18710e895621759dfc80e26231a2afe8414d5d6b8ed5ddceb980effbc38b9f35c0fb8c1ba4a170f95011b4d7296d4594c9d2829d8ec073618153824bd52c47f9b5083d330689fd0667385a4897393b";

		String signedinfo = sign(prikeyvalue, originfo);
		boolean rc = verify(pubkeyvalue, signedinfo, originfo);
		System.out.println("rc:"+rc);
	}

}
