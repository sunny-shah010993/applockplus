package com.thotran.applockplus.db;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.thotran.applockplus.R;
import com.thotran.applockplus.model.ModelApp;

public class DbProvider {

	private static final String PATH_SYSTEM_APP = "/system/app";

	public static ArrayList<ModelApp> getAllApp(Context context) {
		ArrayList<ModelApp> mArrApps = new ArrayList<ModelApp>();
		PackageManager mPackageManager = context.getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		List<ResolveInfo> mArrApplicationInfos = mPackageManager
				.queryIntentActivities(mainIntent, 0);

		for (ResolveInfo appInfo : mArrApplicationInfos) {
			ModelApp item = new ModelApp();
			item.setImage(appInfo.loadIcon(context.getPackageManager()));
			item.setName(appInfo.loadLabel(context.getPackageManager())
					.toString());
			item.setPackgeName(appInfo.activityInfo.packageName);
			String pathApp = appInfo.activityInfo.applicationInfo.sourceDir;
			long time = new File(pathApp).lastModified();
			item.setTime(time);
			if (pathApp != null) {
				if (pathApp.indexOf(PATH_SYSTEM_APP) != -1)
					item.setType(context.getString(R.string.system_app));
				else
					item.setType(context.getString(R.string.dowload_app));
			}
			item.setChecked(false);
			mArrApps.add(item);
		}

		return mArrApps;
	}

	@SuppressWarnings("rawtypes")
	public static ArrayList<ModelApp> getSystemApp(Context context) {
		ArrayList<ModelApp> mArrAllApp = getAllApp(context);
		ArrayList<ModelApp> mArrDownloadApp = getDownloadApp(context);

		for (Iterator iterator = mArrAllApp.iterator(); iterator.hasNext();) {
			ModelApp itemAll = (ModelApp) iterator.next();
			for (ModelApp itemDown : mArrDownloadApp) {
				if (itemAll.getPackgeName().equals(itemDown.getPackgeName())) {
					iterator.remove();
				}
			}

		}
		return mArrAllApp;
	}

	public static ArrayList<ModelApp> getDownloadApp(Context context) {
		ArrayList<ModelApp> mArrApps = new ArrayList<ModelApp>();
		PackageManager mPackageManager = context.getPackageManager();
		List<ApplicationInfo> mArrApplicationInfos = mPackageManager
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (ApplicationInfo appInfo : mArrApplicationInfos) {
			if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 1) {
				ModelApp item = new ModelApp();
				item.setImage(appInfo.loadIcon(context.getPackageManager()));
				item.setName(appInfo.loadLabel(context.getPackageManager())
						.toString());
				item.setPackgeName(appInfo.packageName);
				item.setType(context.getString(R.string.dowload_app));
				item.setChecked(false);
				String pathApp = appInfo.sourceDir;
				long time = new File(pathApp).lastModified();
				item.setTime(time);
				mArrApps.add(item);
			}
		}

		return mArrApps;
	}

}
