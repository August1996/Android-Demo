package com.august1996.thread;

import java.util.ArrayList;

import com.august1996.callback.OnMockedCallback;

public class DefaultDispatcher implements ThreadDispatcher {

	@Override
	public <T> void dispatcher(ArrayList<T> value, OnMockedCallback<T> callback) {
		if (callback != null) {
			callback.onMocked(value);
		}
	}

}
