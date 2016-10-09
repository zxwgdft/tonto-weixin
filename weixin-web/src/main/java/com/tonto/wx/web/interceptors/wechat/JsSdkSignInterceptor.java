package com.tonto.wx.web.interceptors.wechat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tonto.wx.web.interceptors.wechat.jssign.JsSdkSign;
import com.tonto.wx.web.interceptors.wechat.jssign.JsSdkSignContainer;

public class JsSdkSignInterceptor implements HandlerInterceptor{
	
	JsSdkSignContainer jsSdkSignContainer = new JsSdkSignContainer();
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	    		
		String url = request.getScheme()+"://"+request.getServerName()+request.getRequestURI();
		
		JsSdkSign sign = jsSdkSignContainer.getJsSdkSign(url);
		
		if(modelAndView != null)
		{	
			modelAndView.addObject("jsSign", sign);		
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
}
