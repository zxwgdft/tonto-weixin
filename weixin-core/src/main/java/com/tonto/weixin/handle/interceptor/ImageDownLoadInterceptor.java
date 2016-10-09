package com.tonto.weixin.handle.interceptor;

import org.apache.http.client.methods.HttpGet;

import com.tonto.common.util.http.HttpClientManager;
import com.tonto.weixin.common.http.ImageDownloadHandler;
import com.tonto.weixin.core.handle.interceptor.MessageInterceptor;
import com.tonto.weixin.core.handle.interceptor.ProcessInvocation;
import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.request.common.ImageRequestMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;

/**
 * 图片请求存储拦截
 * 
 * @author TontoZhou
 * @date 2015-5-13
 */
public class ImageDownLoadInterceptor implements MessageInterceptor {

	private String imageBasePath;

	@Override
	public ResponseMessage intercept(ProcessInvocation processInvocation) {
		RequestMessage request = processInvocation.getRequest();
		if (request instanceof ImageRequestMessage) {
			ImageRequestMessage imageRequest = (ImageRequestMessage) request;
			String imageUrl = imageRequest.getPicUrl();
			if (imageUrl != null) {
				String imagePath = imageBasePath + imageRequest.getMediaId() + ".jpg";
				HttpClientManager.asynchronousHttpRequest(new HttpGet(imageUrl), new ImageDownloadHandler(imagePath));
			}
		}
		return processInvocation.invoke();
	}

	@Override
	public int getInterceptorLocation() {
		return 1;
	}

	public String getImageBasePath() {
		return imageBasePath;
	}

	public void setImageBasePath(String imageBasePath) {
		this.imageBasePath = imageBasePath;
	}

}
