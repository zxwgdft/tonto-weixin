package com.tonto.weixin.common.xml;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public class CollectionXmlParser implements XmlParser{
	
	private Field field;
	private Class<?> collClass;
	
	private boolean isComplexObjectArray;
	private boolean isInterface;
	
	private ClassXmlParser parser;
	
	public CollectionXmlParser(Field field)
	{
		if(field==null)
			throw new NullPointerException("parse field can't be null");
		
		this.field=field;
				
		collClass=(Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
		
		isInterface=collClass.isInterface();
		
		isComplexObjectArray=!ParserUtils.isBaseClass(collClass);
				
		
		if(!isInterface&&isComplexObjectArray)
		{
			parser=ClassParserFactory.getClassXmlParser(collClass);
		}
			
	}
	
	@Override
	public String parse2xml(Object obj) {
		return parse2xml(obj,collClass.getSimpleName());	
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String parse2xml(Object obj, String title) {
		StringBuilder sb=new StringBuilder();
		sb.append("<").append(title).append(">");
		
		Object value=null;
		try {
			value=field.get(obj);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		if(value!=null)
		{	
			sb.append("\n");
			Collection coll=(Collection) value;
			
			for(Object o:coll)
			{
				Class<?> itemClass=o.getClass();
				if(itemClass.equals(collClass))
				{
					if(isComplexObjectArray)
					{
						if(parser!=null)
						{
							sb.append(parser.parse2xml(o,coll_sub_title));
						}
						else
						{
							sb.append(string_modul.replaceFirst("*",o.toString()));
						}
					}
					else
					{
						if(ParserUtils.isNumClass(itemClass))			
							sb.append(num_modul.replaceFirst("*",o.toString()));
						else
							sb.append(string_modul.replaceFirst("*",o.toString()));
					}
				}
				else
				{
					ClassXmlParser classParser=ClassParserFactory.getClassXmlParser(itemClass);
					if(classParser!=null)
					{
						sb.append(parser.parse2xml(o,coll_sub_title));
					}
					else
					{
						if(ParserUtils.isNumClass(itemClass))			
							sb.append(num_modul.replaceFirst("*",o.toString()));
						else
							sb.append(string_modul.replaceFirst("*",o.toString()));
					}
					
				}					
			}
		}
		
		sb.append("</").append(title).append(">\n");
		return sb.toString();
	}
	
	private final static String coll_sub_title="item";
	private final static String num_modul="<"+coll_sub_title+">*</"+coll_sub_title+">\n";
	private final static String string_modul="<"+coll_sub_title+"><![CDATA[*]]></"+coll_sub_title+">\n";
}
