package com.example.host.extra;

import android.content.pm.PackageInfo;
import android.content.res.Resources;

/**
 * 用来存放已经加载的插件APK信息
 */
public class PluginApk {
    public PackageInfo packageInfo;
    public Resources resources;
    public ClassLoader classLoader;

    public PluginApk(Resources resources) {
        this.resources = resources;
    }

}
