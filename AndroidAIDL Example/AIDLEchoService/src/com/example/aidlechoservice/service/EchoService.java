package com.example.aidlechoservice.service;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.aidlechoservice.aidl.IEchoService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class EchoService extends Service {
	
	private IEchoService.Stub	mBinder;
	private SimpleDateFormat	sdf	= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void onCreate() {
		super.onCreate();
		mBinder = new IEchoService.Stub() {
			
			@Override
			public String echo(String inStr) throws RemoteException {
				return "echo " + inStr + " at " + sdf.format(new Date());
			}
		};
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
}
