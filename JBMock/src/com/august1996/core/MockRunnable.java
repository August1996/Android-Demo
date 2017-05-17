package com.august1996.core;

import java.util.ArrayList;

import com.august1996.callback.OnMockedCallback;
import com.august1996.thread.DispatcherHolder;
import com.august1996.thread.ThreadDispatcher;

public class MockRunnable<T> implements Runnable {

	private Class<T> clazz; // 返回的类，如UserEntity.class
	private int size; // 返回数量
	private int delay; // 返回延迟
	private OnMockedCallback<T> callback; // 线程完成回调
	private Class<? extends ThreadDispatcher> threadDispatch;// 线程切换时用到

	public MockRunnable(Class<T> clazz, int size, int delay, Class<? extends ThreadDispatcher> threadDispatch,
			OnMockedCallback<T> callback) {
		super();
		this.delay = delay;
		this.clazz = clazz;
		this.callback = callback;
		this.size = size;
		this.threadDispatch = threadDispatch;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(delay);
			ArrayList<T> listEntity = Factory.getListEntity(clazz, size);
			DispatcherHolder.get(threadDispatch).dispatcher(listEntity, callback);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
