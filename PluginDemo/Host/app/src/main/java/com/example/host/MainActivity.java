package com.example.host;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.host.act.ActivityManager;
import com.example.host.res.LoadedResource;
import com.example.host.res.ResourceManager;


public class MainActivity extends Activity {

    ImageView imageView;

    ActivityManager mPluginManager = ActivityManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ResourceManager.init(this);
        mPluginManager.init(this);
        imageView = (ImageView) findViewById(R.id.imageView);

    }

    /**
     * 加载已安装APK资源
     *
     * @param v
     */
    public void loadInstalledBundle(View v) {
        Drawable drawable = ResourceManager.installed().getDrawable("com.example.resourcebundle", "image1");
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
    }

    /**
     * 加载未安装APK资源
     *
     * @param v
     */
    public void loadUninstalledBundle(View v) {
        LoadedResource loadResource = ResourceManager.unInstalled().loadResource("/storage/sdcard0/bundle.apk");
        Drawable drawable = ResourceManager.unInstalled().getDrawable(loadResource.packageName, "image");
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
    }


    /**
     * 加载未安装的APK的Activity
     *
     * @param v
     */
    public void loadUninstalledActivity(View v) {
        ActivityManager.getInstance()
                .startActivity("com.example.plugin.MainActivity", "/storage/sdcard0/plugin.apk");
    }
}
