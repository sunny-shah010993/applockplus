package com.thotran.applockplus.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.thotran.applockplus.R;
import com.thotran.applockplus.model.ModelApp;
import com.thotran.applockplus.util.Config;

public class DbProvider {

	public static ArrayList<ModelApp> getAllApp(Context context) {
		ArrayList<ModelApp> mArrApps = new ArrayList<ModelApp>();

		PackageManager mPackageManager = context.getPackageManager();
		List<ApplicationInfo> mArrApplicationInfos = mPackageManager
				.getInstalledApplications(PackageManager.GET_META_DATA);

		for (ApplicationInfo appInfo : mArrApplicationInfos) {
			ModelApp item = new ModelApp();
			item.setImage(appInfo.loadIcon(context.getPackageManager()));
			item.setName(appInfo.loadLabel(context.getPackageManager())
					.toString());
			item.setType("");
			String sourceDir = appInfo.sourceDir;
			Log.e("sourceDir", sourceDir);
			if (sourceDir != null) {
				if (sourceDir.indexOf(Config.SYSTEM_APP) != -1)
					item.setType(context.getString(R.string.system_app));
				else
					item.setType(context.getString(R.string.dowload_app));
			} else
				item.setType(context.getString(R.string.unknow));
			item.setPackgeName(appInfo.packageName);
			item.setChecked(false);
			mArrApps.add(item);
		}

		return mArrApps;
	}

	public static ArrayList<ModelApp> getSystemApp(Context context) {
		ArrayList<ModelApp> mArrApps = new ArrayList<ModelApp>();

		PackageManager mPackageManager = context.getPackageManager();
		List<ApplicationInfo> mArrApplicationInfos = mPackageManager
				.getInstalledApplications(ApplicationInfo.FLAG_SYSTEM
						| ApplicationInfo.FLAG_UPDATED_SYSTEM_APP);
		int mask = ApplicationInfo.FLAG_SYSTEM
				| ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
		for (ApplicationInfo appInfo : mArrApplicationInfos) {
			if ((appInfo.flags & mask) != 0) {
				ModelApp item = new ModelApp();
				Drawable mDrawable = appInfo.loadIcon(context
						.getPackageManager());
				Log.e("mDrawable", mDrawable.toString());
				item.setImage(appInfo.loadIcon(context.getPackageManager()));
				item.setName(appInfo.loadLabel(context.getPackageManager())
						.toString());
				item.setPackgeName(appInfo.packageName);
				item.setType(context.getString(R.string.system_app));
				item.setChecked(false);
				mArrApps.add(item);
			}
		}

		return mArrApps;
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
				mArrApps.add(item);
			}
		}

		return mArrApps;
	}

}
