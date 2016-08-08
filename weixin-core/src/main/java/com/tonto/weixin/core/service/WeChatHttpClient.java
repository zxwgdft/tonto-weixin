package com.tonto.weixin.core.service;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

/**
 * 
 * 微信服务
 * 
 * @author TontoZhou
 * 
 */
public class WeChatHttpClient {

	private static final CloseableHttpClient httpClient;

	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		// Increase max total connection to 200
		cm.setMaxTotal(300);
		// Increase default max connection per route to 30
		cm.setDefaultMaxPerRoute(30);


		httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}

	public static CloseableHttpResponse sendRequest(HttpUriRequest request) throws IOException {
		return httpClient.execute(request);
	}

	public static CloseableHttpResponse sendRequest(HttpUriRequest request, HttpContext context) throws IOException {
		return httpClient.execute(request, context);
	}

}
