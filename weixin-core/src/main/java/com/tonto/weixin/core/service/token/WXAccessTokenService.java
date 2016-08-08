package com.tonto.weixin.core.service.token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.tonto.weixin.core.service.WeChatConfig;
import com.tonto.weixin.core.service.WeChatHttpClient;
import com.tonto.weixin.core.service.WeChatService;
import com.tonto.weixin.core.service.WeChatServiceException;

/**
 * 
 * 获取微信 access_token服务
 * 
 * @author TontoZhou
 *
 */
public class WXAccessTokenService implements WeChatService{

	// 访问微信票据
	private String accessToken;

	// 最近一次获取token的时间
	private long lastAccessTime;

	// access_token在微信服务器保持的时间为2小时，我们提前30秒作为保持票据的时间
	private int keepTime = 0;

	Pattern pattern1 = Pattern.compile("\"[^\"]*\"");
	Pattern pattern2 = Pattern.compile(":\\d+}");

	public void updateAccessToken() {

		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WeChatConfig.getConfig().getAppID()
				+ "&secret=" + WeChatConfig.getConfig().getAppSecret();

		try (CloseableHttpResponse response = WeChatHttpClient.sendRequest(new HttpGet(url));) {
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);

			Matcher matcher = pattern1.matcher(result);

			if (matcher.find()) {
				if ("\"access_token\"".equals(matcher.group())) {
					if (matcher.find()) {
						String token = matcher.group();
						token = token.substring(1, token.length() - 1);

						if (matcher.find()) {
							if ("\"expires_in\"".equals(matcher.group())) {
								matcher = pattern2.matcher(result);
								if (matcher.find()) {
									String timeStr = matcher.group();
									timeStr = timeStr.substring(1, timeStr.length() - 1);
									int time = Integer.valueOf(timeStr);

									// 提前30秒
									keepTime = (time - 30) * 1000;
									accessToken = token;
									lastAccessTime = System.currentTimeMillis();
									return;
								}
							}
						}
					}
				}
			}

			throw new WeChatServiceException("获取access_token错误，返回信息为：" + result);
		} catch (Exception e) {
			throw new WeChatServiceException("获取access_token错误！", e);
		}

	}

	public String getToken() {

		if (System.currentTimeMillis() - lastAccessTime > keepTime) {

			synchronized (WXAccessTokenService.class) {
				if (System.currentTimeMillis() - lastAccessTime > keepTime) {
					updateAccessToken();
				}
			}
		}

		return accessToken;
	}
	
	private final static WXAccessTokenService tokenService = new WXAccessTokenService();
	
	/**
	 * 
	 * 由于获取token的服务特殊，是微信对外接口的通用凭证，因此提供静态方法以便调用
	 * 
	 * @return
	 */
	public static String getAccessToken() {
		return tokenService.getToken();
	}
}
