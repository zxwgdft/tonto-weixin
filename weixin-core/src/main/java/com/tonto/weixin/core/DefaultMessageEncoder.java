package com.tonto.weixin.core;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;

import com.tonto.weixin.core.message.response.ResponseMessage;

/**
 * 
 * 
 * @author TontoZhou
 * 
 */
public class DefaultMessageEncoder implements MessageEncoder {

	@Override
	public String encode(ResponseMessage message) {
		if (message == null)
			return "";

		return parse2xml(message);
	}

	private String parse2xml(Object obj) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		parse2xml(obj, sb);
		sb.append("\n</xml>");
		return sb.toString();
	}

	private void parse2xml(Object obj, StringBuilder sb) {
		if (parseValue(obj, sb))
			return;

		Class<?> clazz = obj.getClass();

		Method[] methods = clazz.getMethods();

		for (Method method : methods) {
			String name = method.getName();

			if (name.startsWith("get") && !"getClass".equals(name) && name.length() > 3 && method.getParameterTypes().length == 0
					&& method.getReturnType() != void.class) {

				name = name.substring(3);

				try {

					Object value = method.invoke(obj);

					sb.append("\n<").append(name).append(">");

					if (value != null) {

						Class<?> valClass = value.getClass();

						if (valClass.isArray()) {
							int size = Array.getLength(value);

							for (int i = 0; i < size; i++) {
								Object item = Array.get(value, i);
								sb.append("\n<item>");
								parse2xml(item, sb);
								sb.append("\n</item>\n");
							}
						}

						if (Collection.class.isAssignableFrom(valClass)) {
							Collection<?> coll = (Collection<?>) value;

							for (Object item : coll) {
								sb.append("\n<item>");
								parse2xml(item, sb);
								sb.append("\n</item>\n");
							}
						}

						if (!parseValue(value, sb)) {
							parse2xml(value, sb);
						}
					}

					sb.append("</").append(name).append(">");

				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				}

			}

		}
	}

	private static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private boolean parseValue(Object value, StringBuilder sb) {
		Class<?> clazz = value.getClass();

		if (Number.class.isAssignableFrom(clazz)) {
			sb.append(value.toString());
			return true;
		} else {
			String str = null;

			if (clazz == String.class) {
				str = (String) value;
			} else if (clazz == Date.class) {
				str = date_format.format((Date) value);
			} else {
				return false;
			}

			sb.append("<![CDATA[").append(str).append("]]>");
			return true;
		}

	}

}
