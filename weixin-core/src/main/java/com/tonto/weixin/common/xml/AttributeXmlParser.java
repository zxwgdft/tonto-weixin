package com.tonto.weixin.common.xml;

import java.lang.reflect.Field;

public class AttributeXmlParser implements XmlParser{
	private Field field;
	private String modul;
	
	public AttributeXmlParser(Field field)
	{
		if(field==null)
			throw new NullPointerException("parse field can't be null");
		this.field=field;
		
		Class<?> fieldClass=field.getType();
		
		String typename=field.getName();
		if(ParserUtils.isNumClass(fieldClass))				
			modul="<"+typename+">*</"+typename+">\n";
		else
			modul="<"+typename+"><![CDATA[*]]></"+typename+">\n";		
	}
	
	@Override
	public String parse2xml(Object obj) {
		
		if(obj!=null)		
		{
			try {
				Object value=field.get(obj);
				if(value==null)
					return "";
				return modul.replace("*", value.toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return modul.replace("*", "");
	}

	@Override
	public String parse2xml(Object obj, String title) {
		StringBuilder sb=new StringBuilder();
		sb.append("<").append(title).append(">");
		if(obj!=null)		
		{
			try {			
				Object value=field.get(obj);
				if(value==null)
					return "";
				sb.append(value.toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		sb.append("</").append(title).append(">\n");
		
		return sb.toString();
	}

}
