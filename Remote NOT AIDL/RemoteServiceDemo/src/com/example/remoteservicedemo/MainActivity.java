package com.example.remoteservicedemo;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	
	private ServiceConnection	mServiceConnection	= new ServiceConnection() {
														
														@Override
														public void onServiceDisconnected(ComponentName name) {
															
														}
														
														@Override
														public void onServiceConnected(ComponentName name,
																IBinder service) {
															mBinder = service;
														}
													};
	
	private IBinder				mBinder				= null;
	private Parcel				data				= Parcel.obtain();
	private Parcel				reply				= Parcel.obtain();
	private SimpleDateFormat	sdf					= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void bind(View v) {
		if (isBinded()) {
			return;
		}
		/**
		 * 通过ComponentName去定位意图
		 * 第一个参数是应用包名
		 * 第二个参数是Service或者Activity所在的包名+类名
		 */
		bindService(
				new Intent().setComponent(
						new ComponentName("com.example.remoteservice", "com.example.remoteservice.MusicService")),
				mServiceConnection, Context.BIND_AUTO_CREATE);
	}
	
	public void unbind(View v) {
		if (!isBinded()) {
			return;
		}
		unbindService(mServiceConnection);
		mBinder = null;
	}
	
	public void play(View v) {
		if (!isBinded()) {
			return;
		}
		try {
			data.writeString("Activity request to play music at " + sdf.format(new Date()));
			mBinder.transact(0, data, reply, 0);
			Log.i("TAG", reply.readString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void pause(View v) {
		try {
			data.writeString("Activity request to pause music at " + sdf.format(new Date()));
			mBinder.transact(1, data, reply, 0);
			Log.i("TAG", reply.readString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isBinded() {
		return mBinder != null;
	}
	
}
