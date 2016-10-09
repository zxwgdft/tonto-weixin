package com.tonto.weixin.common.xml;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class ClassXmlParser implements XmlParser{
	
	private Class<?> objectClass;
	private List<XmlParser> filedParser;	
	
	public ClassXmlParser(Class<?> clazz) throws Exception
	{
		if(clazz==null)
			throw new Exception("class can't be null!");
		if(clazz.isInterface())
			throw new Exception("can't parse for interface!");
		if(ParserUtils.isBaseClass(clazz))
			throw new Exception("base class don't need parse!");
		
		filedParser=new ArrayList<XmlParser>();
		
		objectClass=clazz;
		
		
		while(clazz!=null&&!clazz.equals(Object.class))
		{
			Field[] fields=clazz.getDeclaredFields();
			for(Field field:fields)
			{
				field.setAccessible(true);
				Class<?> fieldClass=field.getType();
				if(ParserUtils.isBaseClass(fieldClass))
				{
					filedParser.add(new AttributeXmlParser(field));
				}
				else if(fieldClass.isArray())
				{
					filedParser.add(new ArrayXmlParser(field));
				}
				else if(Collection.class.isAssignableFrom(fieldClass))
				{
					filedParser.add(new CollectionXmlParser(field));
				}
				else
				{
					ClassXmlParser parser=ClassParserFactory.getClassXmlParser(fieldClass);
					if(parser!=null)
					{
						ClassXmlParserProxy parserProxy=new ClassXmlParserProxy();
						parserProxy.field=field;
						parserProxy.parser=parser;
						filedParser.add(parserProxy);
					}
				}
			}
			clazz=clazz.getSuperclass();
		}
		
	}

	@Override
	public String parse2xml(Object obj) {
		if(obj==null)
			return "";
		
		return parse2xml(obj,objectClass.getSimpleName());
	}

	@Override
	public String parse2xml(Object obj, String title) {
		if(obj==null)
			return "";
		StringBuilder sb=new StringBuilder();
		sb.append("<").append(title).append(">\n");
		for(XmlParser parser:filedParser)
			sb.append(parser.parse2xml(obj));
		sb.append("</").append(title).append(">\n");
		return sb.toString();
	}
	
	static class ClassXmlParserProxy implements XmlParser{

		Field field;
		ClassXmlParser parser;
		
		@Override
		public String parse2xml(Object obj) {		
			try {
				return parser.parse2xml(field.get(obj));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public String parse2xml(Object obj, String title) {
			try {
				return parser.parse2xml(field.get(obj),title);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
}
