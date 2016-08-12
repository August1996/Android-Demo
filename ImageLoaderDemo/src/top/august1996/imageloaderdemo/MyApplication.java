package top.august1996.imageloaderdemo;

import android.app.Application;
import top.august1996.imageloader.core.ImageLoader;

public class MyApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		ImageLoader.init(getApplicationContext());
	}
}
