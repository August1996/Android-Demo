package top.august1996.gifenginedemo;

import android.app.Application;
import top.august1996.gifengine.core.GIFLoader;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		GIFLoader.init(getApplicationContext());
	}
}
