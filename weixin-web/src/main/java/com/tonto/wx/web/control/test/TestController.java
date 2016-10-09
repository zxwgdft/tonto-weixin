package com.tonto.wx.web.control.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/wx/test")
public class TestController {
	
    @RequestMapping(value = "/test")
	public Object test(){	
    	return new ModelAndView("test/test");
	}
	
}
