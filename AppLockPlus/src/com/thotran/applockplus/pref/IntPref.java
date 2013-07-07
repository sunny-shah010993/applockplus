package com.thotran.applockplus.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class IntPref {

	public static void setPreference(Context context, String key, int value) {
		SharedPreferences mPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor edit = mPreferences.edit();
		edit.putInt(key, value);
		edit.commit();
	}

	public static int getPreference(Context context, String key,
			int defaultValue) {
		SharedPreferences mPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return mPreferences.getInt(key, defaultValue);
	}
	
}
