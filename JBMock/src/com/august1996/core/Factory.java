package com.august1996.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.august1996.anno.Type;
import com.august1996.parser.DefaultParser;
import com.august1996.parser.HolderParser;

public class Factory {
	static {
		mParser = new HashMap<>();
		initDefautParser();
	}
	private static Map<Class<? extends TypeParser>, TypeParser> mParser;

	/**
	 * 获取多个Entity
	 * 
	 * @param cls
	 *            Entity的类
	 * @param size
	 *            Entity的数量
	 * @return
	 */
	static <T> ArrayList<T> getListEntity(Class<T> cls, int size) {
		ArrayList<T> result = new ArrayList<T>();
		for (int i = 0; i < size; i++) {
			result.add(getEntity(cls));
		}
		return result;
	}

	/**
	 * 获取单个Entity
	 * 
	 * @param cls
	 * @return
	 */
	static <T> T getEntity(Class<T> cls) {
		T obj = null;

		try {
			obj = cls.newInstance();

			Field[] declaredFields = cls.getDeclaredFields();
			for (Field field : declaredFields) { // 获取Entity类的所有属性
				field.setAccessible(true);
				Annotation[] annotations = field.getAnnotations(); // 获取属性的注解
				for (Annotation anno : annotations) {
					if (anno instanceof Type) {
						for (Map.Entry<Class<? extends TypeParser>, TypeParser> parser : mParser.entrySet()) {
							Object value = parser.getValue().parser(((Type) anno).value()); // 获取到Type注解，使用TypeParser去解析类型
							if (value != null) {
								field.set(obj, value);
							}
						}
					}
				}
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 初始化默认TypeParser
	 */
	private static void initDefautParser() {
		addParser(new DefaultParser());
		addParser(new HolderParser());
	}

	public static void addParser(TypeParser typeParser) {
		mParser.put(typeParser.getClass(), typeParser);
	}

	public static TypeParser removeParser(Class<? extends TypeParser> clazz) {
		return mParser.remove(clazz);
	}

	public static void clearParser() {
		mParser.clear();
		initDefautParser();
	}
}
