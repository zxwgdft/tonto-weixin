package com.tonto.wx.web.interceptors.wechat.jssign;

import com.tonto.common.util.RandomUtil;
import com.tonto.weixin.core.SignUtil;
import com.tonto.weixin.core.service.WeChatConfig;
import com.tonto.weixin.core.service.token.WeChatJsTicketService;

/**
 * 
 * 微信JS SDK所需签名
 * 
 * @author TontoZhou
 *
 */
public class JsSdkSign {

	String ticket;
	String noncestr;
	String timestamp;
	String url;

	String sign;

	public JsSdkSign(String url) {

		this.url = url;

		noncestr = RandomUtil.getRandomString(16);
		timestamp = String.valueOf(System.currentTimeMillis() / 1000);

	}

	public String getSign() {
		
		String jsTicket = WeChatJsTicketService.getJsTicket();

		if (jsTicket == null)
			return null;

		if (sign == null || !jsTicket.equals(ticket)) {
			ticket = jsTicket;
			sign = createSign();

		}

		return sign;

	}

	private String createSign() {

		/*
		 * 按参数名称字典排序，再拼接成URL请求格式，最后对该字符串SHA1加密
		 */
		String content = "jsapi_ticket=" + ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
		return SignUtil.getSHA1SignHexStr(content);
	}

	public String getAppid(){		
		return WeChatConfig.getConfig().getAppID();		
	}
	
	public String getTimestamp() {
		return timestamp;
	}

	public String getUrl() {
		return url;
	}

	public String getNoncestr() {
		return noncestr;
	}

}
