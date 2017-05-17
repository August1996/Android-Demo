package com.august1996.core;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.august1996.callback.OnMockedCallback;
import com.august1996.thread.DefaultDispatcher;
import com.august1996.thread.ThreadDispatcher;

public class JBMock {
	private static final JBMock sInstance = new JBMock();

	public static JBMock getInstance() {
		return sInstance;
	}

	private JBMock() {
		mExecutors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}

	private ExecutorService mExecutors;

	public <T> ArrayList<T> syncGet(Class<T> cls, int delay, int size) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Factory.getListEntity(cls, size);
	}

	public <T> ArrayList<T> syncGet(Class<T> cls) {
		return syncGet(cls, 1500, 12);
	}

	public <T> void asyncGet(Class<T> cls, int delay, int size, Class<? extends ThreadDispatcher> threadDispatcherCls,
			OnMockedCallback<T> callback) {
		mExecutors.submit(new MockRunnable<T>(cls, size, delay, threadDispatcherCls, callback));
	}

	public <T> void asyncGet(Class<T> cls, int delay, int size, OnMockedCallback<T> callback) {
		asyncGet(cls, size, delay, DefaultDispatcher.class, callback);
	}

	public <T> void asyncGet(Class<T> cls, OnMockedCallback<T> callback) {
		asyncGet(cls, 20, 1500, DefaultDispatcher.class, callback);
	}

	public <T> void asyncGet(Class<T> cls, Class<? extends ThreadDispatcher> threadDispatcherCls,
			OnMockedCallback<T> callback) {
		asyncGet(cls, 20, 1500, threadDispatcherCls, callback);
	}

}
