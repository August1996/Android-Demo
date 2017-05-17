package com.august1996.thread;

import java.util.ArrayList;

import com.august1996.callback.OnMockedCallback;

public interface ThreadDispatcher {
	<T> void dispatcher(ArrayList<T> value, OnMockedCallback<T> callback);
}
