package com.tonto.weixin.common.xml;

import java.util.HashMap;
import java.util.Map;

public class ClassParserFactory {
	private final static Map<Class<?>, ClassXmlParser> classParserMap = new HashMap<Class<?>, ClassXmlParser>();

	public static ClassXmlParser getClassXmlParser(Class<?> clazz) {
		ClassXmlParser parser=classParserMap.get(clazz);
		if(parser==null)
		{
			if(!classParserMap.containsKey(clazz))
			{
				try {
					parser=new ClassXmlParser(clazz);
				} catch (Exception e) {
					e.printStackTrace();
				}
				classParserMap.put(clazz, parser);
			}
		}
		return parser;
	}

}
