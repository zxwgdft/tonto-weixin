package com.tonto.wx.web.util;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class WebUtil implements ServletContextListener{
	
	private static String web_root_path;
	public static String getWebRootPath()
	{
		return web_root_path;
	}
	
	
	
	public static boolean isAjaxRequest(HttpServletRequest request){
		return "XMLHttpRequest".equals( request.getHeader("X-Requested-With"));
	}
	
	
	private static ObjectMapper objectMapper=new ObjectMapper();	
	public static void sendJson(HttpServletResponse response,Object obj){
		
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			
			try {
				objectMapper.writeValue(response.getWriter(), obj);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		
	}
	
	
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		web_root_path=sce.getServletContext().getRealPath("/");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
