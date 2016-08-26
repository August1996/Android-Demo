package com.example.remoteservice;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class MusicService extends Service {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public IBinder onBind(Intent intent) {
		return new MyBinder();
	}
	
	public class MyBinder extends Binder {
		@Override
		protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
			switch (code) {
				case 0:
					Log.i("TAG", data.readString());
					play();
					reply.writeString("Service play Music at " + sdf.format(new Date()));
					break;
				case 1:
					Log.i("TAG", data.readString());
					pause();
					reply.writeString("Service pause Music at " + sdf.format(new Date()));
					
					break;
				default:
					break;
			}
			
			return true;
		}
	}
	
	public void play() {
		Log.d("TAG", "play");
	}
	
	public void pause() {
		Log.d("TAG", "pause");
	}
	
}
