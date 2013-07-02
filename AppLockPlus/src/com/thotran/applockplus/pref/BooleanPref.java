package com.thotran.applockplus.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BooleanPref {

	public static void setPreference(Context context, String key, boolean value) {
		SharedPreferences mPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor edit = mPreferences.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	public static boolean getPreference(Context context, String key,
			boolean defaultValue) {
		SharedPreferences mPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return mPreferences.getBoolean(key, defaultValue);
	}
}
