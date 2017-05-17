package com.august1996.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ValueHolder {
	private static final Map<String, List<Object>> sMap = new HashMap<String, List<Object>>();
	private static final Random sRandom = new Random();

	public static void register(String type, Object... value) {
		List<Object> list = sMap.get(type);
		if (list == null) {
			list = new ArrayList<Object>();
			sMap.put(type, list);
		}
		list.addAll(Arrays.asList(value));
	}

	public static void unregister(String type) {
		sMap.remove(type);
	}

	public static void clear() {
		sMap.clear();
	}

	static Object get(String type) {
		List<Object> list = sMap.get(type);
		if (list != null && !list.isEmpty()) {
			return list.get(sRandom.nextInt(list.size()));
		}
		return null;
	}

}
