package com.tonto.wx.exception;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.tonto.wx.web.response.CommonResponse;
import com.tonto.wx.web.util.WebUtil;

public class ExceptionHandler extends ExceptionHandlerExceptionResolver {

	private String defaultErrorView;

	public String getDefaultErrorView() {
		return defaultErrorView;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception ex) {

		Method method = handlerMethod.getMethod();

		ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method, ResponseBody.class);

		if (responseBodyAnn != null) {
			WebUtil.sendJson(response, CommonResponse.createErrorResponse(ex));			
		} else {
			return new ModelAndView("error/error", "error", ex.getMessage());
		}
		
		return null;

	}

}
