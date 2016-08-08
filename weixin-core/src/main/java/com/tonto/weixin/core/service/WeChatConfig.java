package com.tonto.weixin.core.service;

import com.tonto.common.util.PropertyUtil;

/**
 * 
 * 微信应用上下文
 * 
 * @author TontoZhou
 * 
 */
public class WeChatConfig {

	// token 用于接入的token，非access token
	private String token;

	// app id
	private String appID;

	// app secret
	private String appSecret;

	public String getToken() {
		return token;
	}

	public String getAppID() {
		return appID;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public static WeChatConfig getConfig() {
		return config;
	}

	private final static WeChatConfig config;

	static {
		
		config = new WeChatConfig();
		
		config.token = PropertyUtil.getProperty("wechat.properties", "token");
		config.appID = PropertyUtil.getProperty("wechat.properties", "appID");
		config.appSecret = PropertyUtil.getProperty("wechat.properties", "appSecret");
	}

}
