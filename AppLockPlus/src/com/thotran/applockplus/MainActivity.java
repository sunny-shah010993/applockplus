package com.thotran.applockplus;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.thotran.applockplus.adapter.AppAdapter;
import com.thotran.applockplus.db.DbProvider;
import com.thotran.applockplus.model.ModelApp;
import com.thotran.applockplus.pref.BooleanPref;
import com.thotran.applockplus.util.Config;
import com.thotran.applockplus.util.Util;

public class MainActivity extends SherlockActivity implements
		OnNavigationListener {

	private String[] listNavigation;

	private Context mContext;

	private ArrayList<ModelApp> mArrApps = new ArrayList<ModelApp>();

	private ActionMode mActionMode;

	private ActionBar mActionBar;

	private AppAdapter mAppAdapter;

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = getApplicationContext();
		listNavigation = getResources().getStringArray(R.array.listNavigation);
		Context context = getSupportActionBar().getThemedContext();
		ArrayAdapter<CharSequence> list = ArrayAdapter
				.createFromResource(context, R.array.listNavigation,
						R.layout.sherlock_spinner_item);
		list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setListNavigationCallbacks(list, this);

		// ----------
		mListView = (ListView) findViewById(R.id.listApp);
		mAppAdapter = new AppAdapter(mContext, mArrApps);
		mListView.setAdapter(mAppAdapter);

	}

	public void initArrApps() {
		if (BooleanPref.getPreference(mContext, Config.KEY_ALL_APP, true)) {
			mArrApps = DbProvider.getAllApp(mContext);
		}

		if (BooleanPref.getPreference(mContext, Config.KEY_DOWNLOAD_APP, false)) {
			mArrApps = DbProvider.getDownloadApp(mContext);
		}

		if (BooleanPref.getPreference(mContext, Config.KEY_SYSTEM_APP, false)) {
			mArrApps = DbProvider.getSystemApp(mContext);
		}
	}

	public void initView() {

	}

	public void sortAll() {
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
		mAppAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_search:
			mActionMode = startActionMode(new AnActionMode());
			break;
		case R.id.menu_sort_by_name:
			if (BooleanPref.getPreference(mContext,
					Config.KEY_SORT_BY_NAME_A_Z, true)) {
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_Z_A, true);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_A_Z, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_FIRST, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_LAST, false);
				Util.sortZA(mArrApps);
				mAppAdapter.notifyDataSetChanged();
				Toast.makeText(mContext, getString(R.string.sort_by_name_a_z),
						Toast.LENGTH_SHORT).show();

			} else {
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_A_Z, true);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_Z_A, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_FIRST, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_LAST, false);
				Util.sortAZ(mArrApps);
				mAppAdapter.notifyDataSetChanged();
				Toast.makeText(mContext, getString(R.string.sort_by_name_z_a),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.menu_sort_by_date:
			if (BooleanPref.getPreference(mContext,
					Config.KEY_SORT_BY_DATE_LAST, true)) {
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_Z_A, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_A_Z, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_FIRST, true);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_LAST, false);
				Util.sortFirstDate(mArrApps);
				mAppAdapter.notifyDataSetChanged();
				Toast.makeText(mContext,
						getString(R.string.sort_by_date_first),
						Toast.LENGTH_SHORT).show();
			} else {
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_Z_A, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_NAME_A_Z, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_FIRST, false);
				BooleanPref.setPreference(mContext,
						Config.KEY_SORT_BY_DATE_LAST, true);
				Util.sortLastDate(mArrApps);
				mAppAdapter.notifyDataSetChanged();
				Toast.makeText(mContext, getString(R.string.sort_by_date_last),
						Toast.LENGTH_SHORT).show();
			}

			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {

		if (listNavigation[itemPosition].equals(getString(R.string.all))) {
			mArrApps.clear();
			mArrApps.addAll(DbProvider.getAllApp(mContext));
			sortAll();
			mAppAdapter.notifyDataSetChanged();
			BooleanPref.setPreference(mContext, Config.KEY_ALL_APP, true);
			BooleanPref.setPreference(mContext, Config.KEY_DOWNLOAD_APP, false);
			BooleanPref.setPreference(mContext, Config.KEY_SYSTEM_APP, false);
		} else if (listNavigation[itemPosition]
				.equals(getString(R.string.download))) {
			mArrApps.clear();
			mArrApps.addAll(DbProvider.getDownloadApp(mContext));
			sortAll();
			mAppAdapter.notifyDataSetChanged();
			BooleanPref.setPreference(mContext, Config.KEY_ALL_APP, false);
			BooleanPref.setPreference(mContext, Config.KEY_DOWNLOAD_APP, true);
			BooleanPref.setPreference(mContext, Config.KEY_SYSTEM_APP, false);
		} else {
			mArrApps.clear();
			mArrApps.addAll(DbProvider.getSystemApp(mContext));
			sortAll();
			mAppAdapter.notifyDataSetChanged();
			BooleanPref.setPreference(mContext, Config.KEY_ALL_APP, false);
			BooleanPref.setPreference(mContext, Config.KEY_DOWNLOAD_APP, false);
			BooleanPref.setPreference(mContext, Config.KEY_SYSTEM_APP, true);
		}

		return false;
	}

	public class AnActionMode implements ActionMode.Callback {
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.collapsible_edittext, null);
			mode.setCustomView(view);
			EditText mEditText = (EditText) view.findViewById(R.id.etextSearch);
			mEditText.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					mAppAdapter.getFilter().filter(arg0.toString());
				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {

				}

				@Override
				public void afterTextChanged(Editable arg0) {

				}
			});
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// TODO Auto-generated method stub

		}

	}

}
