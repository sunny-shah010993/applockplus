package com.thotran.applockplus.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.util.Log;

import com.thotran.applockplus.model.ModelApp;
import com.thotran.applockplus.pref.BooleanPref;

public class Util {

	public static void sortAZ(ArrayList<ModelApp> mArrApps) {
		Collections.sort(mArrApps, new Comparator<ModelApp>() {
			@Override
			public int compare(ModelApp lhs, ModelApp rhs) {
				return lhs.getName().compareToIgnoreCase(rhs.getName());
			}
		});
		Log.e("a-z", "a-z");
	}

	public static void sortZA(ArrayList<ModelApp> mArrApps) {
		Collections.sort(mArrApps, new Comparator<ModelApp>() {
			@Override
			public int compare(ModelApp lhs, ModelApp rhs) {
				return rhs.getName().compareToIgnoreCase(lhs.getName());
			}
		});
		Log.e("z-a", "z-a");
	}

	public static void sortFirstDate(ArrayList<ModelApp> mArrApps) {
		Collections.sort(mArrApps, new Comparator<ModelApp>() {
			@Override
			public int compare(ModelApp lhs, ModelApp rhs) {
				return Long.valueOf(lhs.getTime()).compareTo(
						Long.valueOf(rhs.getTime()));
			}
		});
		Log.e("first", "first");
	}

	public static void sortLastDate(ArrayList<ModelApp> mArrApps) {
		Collections.sort(mArrApps, new Comparator<ModelApp>() {
			@Override
			public int compare(ModelApp lhs, ModelApp rhs) {
				return Long.valueOf(rhs.getTime()).compareTo(
						Long.valueOf(lhs.getTime()));
			}
		});
		Log.e("last", "last");
	}
	
	public static void sortAll(Context mContext, ArrayList<ModelApp> mArrApps) {
		if (BooleanPref.getPreference(mContext, Config.KEY_SORT_BY_NAME_A_Z,
				true)) {
			Util.sortAZ(mArrApps);
		}

		if (BooleanPref.getPreference(mContext, Config.KEY_SORT_BY_NAME_Z_A,
				false)) {
			Util.sortZA(mArrApps);
		}

		if (BooleanPref.getPreference(mContext, Config.KEY_SORT_BY_DATE_FIRST,
				false)) {
			Util.sortFirstDate(mArrApps);
		}

		if (BooleanPref.getPreference(mContext, Config.KEY_SORT_BY_DATE_LAST,
				false)) {
			Util.sortLastDate(mArrApps);
		}
	}

}
