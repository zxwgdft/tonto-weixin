package com.tonto.weixin.core;

import com.tonto.weixin.common.xml.ClassParserFactory;
import com.tonto.weixin.common.xml.ClassXmlParser;
import com.tonto.weixin.core.message.response.ResponseMessage;


public class DefaultMessageEncoder implements MessageEncoder{

	@Override
	public String encode(ResponseMessage message) {
		if(message==null)
			return "";
		try {
			ClassXmlParser parser=ClassParserFactory.getClassXmlParser(message.getClass());
			if(parser!=null)
				return parser.parse2xml(message,"xml");
			return "";
		} catch (Exception e) {
			e.printStackTrace();
		} 		
		
		return "";
	}
	
	
}
