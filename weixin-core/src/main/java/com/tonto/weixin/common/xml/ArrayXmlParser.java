package com.tonto.weixin.common.xml;

import java.lang.reflect.Field;

public class ArrayXmlParser implements XmlParser{
	
	private Field field;
	private Class<?> fieldClass;
	private Class<?> arrayClass;
	
	private boolean isComplexObjectArray;
	private boolean isInterface;
	
	private ClassXmlParser parser;
	
	public ArrayXmlParser(Field field){
		if(field==null)
			throw new NullPointerException("parse field can't be null");
		
		this.field=field;
		
		fieldClass=field.getType();
		
		arrayClass=fieldClass.getComponentType();
		
		isInterface=arrayClass.isInterface();
		
		isComplexObjectArray=!ParserUtils.isBaseClass(arrayClass);
		
		
		
		if(!isInterface&&isComplexObjectArray)
		{
			parser=ClassParserFactory.getClassXmlParser(arrayClass);
		}		
		
	}
	
	@Override
	public String parse2xml(Object obj) {
				
		return parse2xml(obj,arrayClass.getSimpleName());
	}

	@Override
	public String parse2xml(Object obj, String title) {
		
		
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
			StringBuilder sb=new StringBuilder();
			sb.append("<").append(title).append(">\n");
			
			if(value instanceof int[])
			{
				int[] arrayValue=(int[]) value;
				for(int i:arrayValue)
					sb.append(num_modul.replaceFirst("*",String.valueOf(i)));
			}
			else if(value instanceof double[])
			{
				double[] arrayValue=(double[]) value;
				for(double i:arrayValue)
					sb.append(num_modul.replaceFirst("*",String.valueOf(i)));
			}
			else if(value instanceof char[])
			{
				char[] arrayValue=(char[]) value;
				for(char i:arrayValue)
					sb.append(string_modul.replaceFirst("*",String.valueOf(i)));
			}
			else if(value instanceof long[])
			{
				long[] arrayValue=(long[]) value;
				for(long i:arrayValue)
					sb.append(num_modul.replaceFirst("*",String.valueOf(i)));
			}
			else if(value instanceof float[])
			{
				float[] arrayValue=(float[]) value;
				for(float i:arrayValue)
					sb.append(num_modul.replaceFirst("*",String.valueOf(i)));
			}
			else
			{
				Object[] arrayValue=(Object[]) value;
				
				for(Object o:arrayValue)
				{		
					if(o==null)
						continue;
					Class<?> itemClass=o.getClass();
					if(itemClass.equals(arrayClass))
					{
						if(isComplexObjectArray)
						{
							if(parser!=null)
							{
								sb.append(parser.parse2xml(o,array_sub_title));
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
							sb.append(classParser.parse2xml(o,array_sub_title));
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
		
		return "";
	}
	
	private final static String array_sub_title="item";
	private final static String num_modul="<"+array_sub_title+">*</"+array_sub_title+">\n";
	private final static String string_modul="<"+array_sub_title+"><![CDATA[*]]></"+array_sub_title+">\n";
}
