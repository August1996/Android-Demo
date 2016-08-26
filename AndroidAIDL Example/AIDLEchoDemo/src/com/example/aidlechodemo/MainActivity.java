package com.example.aidlechodemo;

import com.example.aidlechoservice.aidl.IEchoService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	
	private IEchoService		mService;
	private ServiceConnection	mServiceConnection;
	private Intent				mServiceIntent	= new Intent();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mServiceIntent.setComponent(
				new ComponentName("com.example.aidlechoservice", "com.example.aidlechoservice.service.EchoService"));
		mServiceConnection = new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName name) {
				
			}
			
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				mService = IEchoService.Stub.asInterface(service);
			}
		};
	}
	
	public void bind(View v) {
		if (isBinded()) {
			return;
		}
		bindService(mServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
	}
	
	public void unbind(View v) {
		if (!isBinded()) {
			return;
		}
		unbindService(mServiceConnection);
	}
	
	public void echo(View v) {
		if (!isBinded()) {
			return;
		}
		try {
			String result = mService.echo("Hello world!!!");
			Log.i("TAG", result);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isBinded() {
		return mService != null;
	}
	
}
