package com.tonto.weixin.common.xml;

import java.util.Date;

public class ParserUtils {
	
	public static boolean isBaseClass(Class<?> clazz)
	{
		return clazz.isPrimitive()||clazz.isEnum()||clazz.equals(String.class)||clazz.equals(Integer.class)
				||clazz.equals(Double.class)||clazz.equals(Float.class)||clazz.equals(Date.class)
				||clazz.equals(Long.class)||clazz.equals(Object.class);	
	}
	
	public static boolean isNumClass(Class<?> clazz)
	{
		return clazz.equals(int.class)||clazz.equals(Integer.class)||clazz.equals(double.class)
				||clazz.equals(Double.class)||clazz.equals(float.class)||clazz.equals(Float.class)
				||clazz.equals(long.class)||clazz.equals(Long.class);
	}
}
