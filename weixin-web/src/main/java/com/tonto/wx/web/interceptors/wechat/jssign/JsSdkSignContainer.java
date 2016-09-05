package com.tonto.wx.web.interceptors.wechat.jssign;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 微信JS SDK签名容器
 * 
 * @author TontoZhou
 *
 */
public class JsSdkSignContainer {

	private final Map<String, JsSdkSign> sign_cache = new HashMap<>();

	public JsSdkSign getJsSdkSign(String url) {

		JsSdkSign sign = sign_cache.get(url);
		
		if (sign == null) {
			sign = new JsSdkSign(url);
			sign_cache.put(url, sign);
		}
		
		return sign;

	}

}
