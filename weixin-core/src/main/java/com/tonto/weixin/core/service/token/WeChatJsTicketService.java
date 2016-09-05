package com.tonto.weixin.core.service.token;

import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import com.tonto.common.util.JsonUtil;
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
public class WeChatJsTicketService implements WeChatService {

	// 访问微信JS票据
	private String jsapi_ticket;

	// 最近一次获取token的时间
	private long lastAccessTime;

	// access_token在微信服务器保持的时间为2小时，我们提前30秒作为保持票据的时间
	private int keepTime = 0;

	public void updateTicket() {

		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
				+ WeChatAccessTokenService.getAccessToken() + "&type=jsapi";

		try (CloseableHttpResponse response = WeChatHttpClient.sendRequest(new HttpGet(url));) {

			HttpEntity entity = response.getEntity();
			String jsonStr = EntityUtils.toString(entity);

			Map<String, Object> result = JsonUtil.json2map(jsonStr);

			String ticket = (String) result.get("ticket");

			if (!StringUtils.isEmpty(ticket)) {
				// 提前30秒
				keepTime = (((Number) result.get("expires_in")).intValue() - 30) * 1000;
				jsapi_ticket = ticket;
				lastAccessTime = System.currentTimeMillis();
			}
			else
				throw new WeChatServiceException("获取jsapi_ticket错误，返回信息为：" + result);
			
		} catch (Exception e) {
			throw new WeChatServiceException("获取jsapi_ticket错误！", e);
		}

	}

	public String getTicket() {

		if (System.currentTimeMillis() - lastAccessTime > keepTime) {

			synchronized (WeChatJsTicketService.class) {
				if (System.currentTimeMillis() - lastAccessTime > keepTime) {
					updateTicket();
				}
			}
		}

		return jsapi_ticket;
	}

	private final static WeChatJsTicketService tokenService = new WeChatJsTicketService();

	/**
	 * 
	 * 由于获取ticket的服务特殊，是微信JS对外接口的通用凭证，因此提供静态方法以便调用
	 * 
	 * @return
	 */
	public static String getJsTicket() {
		return tokenService.getTicket();
	}

}
