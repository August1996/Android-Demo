package com.august1996.thread;

import java.util.HashMap;
import java.util.Map;

public class DispatcherHolder {

	private static final Map<Class<? extends ThreadDispatcher>, ThreadDispatcher> sMap;

	static {
		sMap = new HashMap<>();
		register(new DefaultDispatcher());
	}

	public static void register(ThreadDispatcher dispatcher) {
		sMap.put(dispatcher.getClass(), dispatcher);
	}

	public static void unregister(Class<? extends ThreadDispatcher> clazz) {
		sMap.remove(clazz);
	}

	public static ThreadDispatcher get(Class<? extends ThreadDispatcher> clazz) {
		return sMap.get(clazz);
	}

}
