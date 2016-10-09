package com.tonto.weixin.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;


/**
 * 
 * 签名工具类
 * 
 * @author TontoZhou
 *
 */
public class SignUtil {
	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byte2hexStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equalsIgnoreCase(signature) : false;
	}

	/**
	 * 将字节数组转换为十六进制小写字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byte2hexStr(byte[] byteArray) {
		StringBuilder strDigest = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			byte b = byteArray[i];			
			strDigest.append(Digit[(b >>> 4) & 0x0F]).append(Digit[b & 0x0F]);			
		}
		return strDigest.toString();
	}

	private static char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


	/**
	 * 
	 * 获取sha1加密后返回的十六进制字符串
	 * 
	 * @param content
	 * @return
	 */
	public static String getSHA1SignHexStr(String content) {
		byte[] digest = DigestUtils.sha(content.getBytes());
		return byte2hexStr(digest);
	}


}
