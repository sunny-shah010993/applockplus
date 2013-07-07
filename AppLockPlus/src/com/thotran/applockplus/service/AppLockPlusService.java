package com.thotran.applockplus.service;

import java.util.List;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AppLockPlusService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		Log.e("111", "22211111");
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("111", "222");
		getCurrentApp(getApplicationContext());

		return Service.START_STICKY;
	}

	private void getCurrentApp(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		List<ActivityManager.RunningTaskInfo> takeInfo = activityManager
				.getRunningTasks(1);
		ComponentName componentName = takeInfo.get(0).topActivity;
		String packageName = componentName.getPackageName();
		Log.e("packageName", packageName);
	}

}
