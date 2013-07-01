package com.thotran.applockplus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	public static DbHelper mDHelper;

	public static SQLiteDatabase mDB;

	public static final String DATABASE_NAME = "applockplus.db";

	public static final int DATABASE_VERSION = 1;

	public static final String TABLE_NAME = "applockplus";

	public static final String NAME_APP = "name";

	public static final String PACKAGE_APP = "package";

	public static final String CHECKED_APP = "checked";

	public static final String ID_APP = "id";

	public static synchronized DbHelper getInstance(Context context) {

		if (mDHelper == null) {
			mDHelper = new DbHelper(context);
		}

		return mDHelper;
	}

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mDB = mDHelper.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "  create tatle " + TABLE_NAME + " ( " + ID_APP
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME_APP + " TEXT, "
				+ PACKAGE_APP + " TEXT)  ";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void insertAppLock(String name, String package_app) {
		ContentValues mContentValues = new ContentValues();
		mContentValues.put(NAME_APP, name);
		mContentValues.put(PACKAGE_APP, package_app);
		mDB.insert(TABLE_NAME, null, mContentValues);
	}

}
